package org.moonila.mycalendar.app.view.internal;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.moonila.mycalendar.app.view.widget.CustomDatePicker;
import org.moonila.mycalendar.app.view.widget.CustomNumberPicker;
import org.moonila.mycalendar.app.view.widget.CustomNumberPickerEditText;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomLayoutInflater extends android.view.LayoutInflater implements Cloneable, android.view.LayoutInflater.Factory {
    private final class CustomFactoryMerger extends ArrayList<Factory> implements Factory {
        private static final long serialVersionUID = -851134244408815411L;

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            for (Factory factory : this) {
                try {
                    View view = factory.onCreateView(name, context, attrs);
                    if (view != null) {
                        return view;
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }
    }

    public static interface OnInitInflaterListener {
        public void onInitInflater(CustomLayoutInflater inflater);
    }

    private static final Map<Context, WeakReference<CustomLayoutInflater>> INSTANCES_MAP = new WeakHashMap<Context, WeakReference<CustomLayoutInflater>>();
    private static OnInitInflaterListener listener;
    private static final Map<String, String> VIEWS_MAP = new HashMap<String, String>();

    static {

        remap(CustomNumberPicker.class);
        remap(CustomDatePicker.class);
        remapInternal(CustomNumberPickerEditText.class);
    }

    public static void clearInstances() {
        CustomLayoutInflater.INSTANCES_MAP.clear();
    }

    public static CustomLayoutInflater from(android.view.LayoutInflater inflater) {
        if (inflater instanceof CustomLayoutInflater) {
            return (CustomLayoutInflater) inflater;
        }
        return new CustomLayoutInflater(inflater, inflater.getContext());
    }

    public static CustomLayoutInflater from(Context context) {
        CustomLayoutInflater inflater = null;
        WeakReference<CustomLayoutInflater> reference = INSTANCES_MAP.get(context);
        if (reference != null) {
            inflater = reference.get();
        }
        if (inflater == null) {
            inflater = new CustomLayoutInflater(context);
            reference = new WeakReference<CustomLayoutInflater>(inflater);
            INSTANCES_MAP.put(context, reference);
        }
        return inflater;
    }

    public static CustomLayoutInflater from(View view) {
        return CustomLayoutInflater.from(view.getContext());
    }

    public static View inflate(Context context, int resource) {
        return CustomLayoutInflater.from(context).inflate(resource, null);
    }

    public static View inflate(Context context, int resource, ViewGroup root) {
        return CustomLayoutInflater.from(context).inflate(resource, root);
    }

    public static View inflate(Context context, int resource, ViewGroup root, boolean attachToRoot) {
        return CustomLayoutInflater.from(context).inflate(resource, root, attachToRoot);
    }

    public static View inflate(View view, int resource) {
        return CustomLayoutInflater.from(view).inflate(resource, null);
    }

    public static View inflate(View view, int resource, ViewGroup root) {
        return CustomLayoutInflater.from(view).inflate(resource, root);
    }

    public static View inflate(View view, int resource, ViewGroup root, boolean attachToRoot) {
        return CustomLayoutInflater.from(view).inflate(resource, root, attachToRoot);
    }

    public static void onDestroy(Context context) {
        CustomLayoutInflater.INSTANCES_MAP.remove(context);
    }

    public static void remap(Class<? extends View>... classes) {
        for (Class<? extends View> clazz : classes) {
            remap(clazz);
        }
    }

    public static void remap(Class<? extends View> clazz) {
        if (clazz != null) {
            CustomLayoutInflater.VIEWS_MAP.put(clazz.getSimpleName(), clazz.getName());
        }
    }

    @Deprecated
    public static void remap(String prefix, String... classess) {
        for (String clazz : classess) {
            CustomLayoutInflater.VIEWS_MAP.put(clazz, prefix + "." + clazz);
        }
    }

    public static void remapHard(String from, String to) {
        CustomLayoutInflater.VIEWS_MAP.put(from, to);
    }

    private static void remapInternal(Class<?>... classess) {
        for (Class<?> clazz : classess) {
            remapHard("Internal." + clazz.getSimpleName(), clazz.getName());
        }
    }

    public static void setOnInitInflaterListener(OnInitInflaterListener listener) {
        CustomLayoutInflater.listener = listener;
    }

    private final CustomFactoryMerger factoryMerger = new CustomFactoryMerger();

    protected CustomLayoutInflater(android.view.LayoutInflater original, Context newContext) {
        super(original, newContext);
        init();
    }

    protected CustomLayoutInflater(Context context) {
        super(context);
        init();
    }

    public void addFactory(Factory factory) {
        checkFactoryOnNull(factory);
        factoryMerger.add(factory);
    }

    public void addFactory(Factory factory, int index) {
        checkFactoryOnNull(factory);
        factoryMerger.add(index, factory);
    }

    private void checkFactoryOnNull(Factory factory) {
        if (factory == null) {
            throw new NullPointerException("Given factory can not be null");
        }
    }

    @Override
    public CustomLayoutInflater cloneInContext(Context newContext) {
        return new CustomLayoutInflater(this, newContext);
    }

    public View inflate(int resource) {
        return inflate(resource, null);
    }

    @Override
    public View inflate(XmlPullParser parser, ViewGroup root) {
        return inflate(parser, root, root != null);
    }

    private void init() {
        super.setFactory(factoryMerger);
        factoryMerger.add(this);
        if (CustomLayoutInflater.listener != null) {
            CustomLayoutInflater.listener.onInitInflater(this);
        }
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        try {
            return onCreateView(name, attrs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setFactory(Factory factory) {
        addFactory(factory, 0);
    }

    protected View tryCreateView(String name, String prefix, AttributeSet attrs) {
        String newName = prefix == null ? "" : prefix;
        newName += name;
        try {
            if (Class.forName(newName) != null) {
                return createView(newName, null, attrs);
            }
        } catch (ClassNotFoundException e) {
        }
        return null;
    }
}
