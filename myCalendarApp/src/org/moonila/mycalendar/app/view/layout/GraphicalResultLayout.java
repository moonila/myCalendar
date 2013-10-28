package org.moonila.mycalendar.app.view.layout;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.moonila.mycalendar.app.bean.FirstDay;
import org.moonila.mycalendar.app.core.ManageData;
import org.moonila.mycalendar.app.view.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class GraphicalResultLayout extends CustomLayout {

    private static class GraphicalResultLayoutHolder {
        private final static GraphicalResultLayout instance = new GraphicalResultLayout();
    }

    public static GraphicalResultLayout getInstance(Context context, View pageView, ManageData manageData) {
        init(context, pageView, manageData);
        return GraphicalResultLayoutHolder.instance;
    }

    public void createGraphicalResultLayout() {

        List<FirstDay> allDates = manageData.getAllDateForCurrentYear();

        LinearLayout layout = (LinearLayout) pageView.findViewById(R.id.chart);

        layout.addView(execute(allDates), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public GraphicalView execute(List<FirstDay> allDates) {

        XYMultipleSeriesRenderer renderer = createRenderer();

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        XYSeriesRenderer r = createSeriesRenderer();
        renderer.addSeriesRenderer(r);

        XYSeries series = new XYSeries(context.getString(R.string.graphs_legendary));
        for (int i = 0; i < allDates.size(); i++) {

            // On indique le style de la serie
            String[] values = allDates.get(i).getDateformated().split("/");

            String dayValue = values[0];

            String monthValue = values[1];

            series.add(i, Double.valueOf(monthValue), Double.valueOf(dayValue));
        }

        dataset.addSeries(series);
        renderer.setYAxisMax(31);

        return ChartFactory.getLineChartView(context, dataset, renderer);
    }

    private XYSeriesRenderer createSeriesRenderer() {
        XYSeriesRenderer r = new XYSeriesRenderer();
        // r.setColor(Color.MAGENTA);
        r.setColor(context.getResources().getColor(R.color.magenta));
        r.setPointStyle(PointStyle.DIAMOND);
        r.setGradientEnabled(true);
        r.setGradientStart(1, Color.BLACK);
        r.setGradientStop(31, Color.BLACK);
        r.setGradientEnabled(true);
        return r;
    }

    private XYMultipleSeriesRenderer createRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        renderer.setBarSpacing(0.5);
        renderer.setXLabels(0);
        renderer.setYLabels(10);
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setYLabelsColor(0, Color.BLACK);
        renderer.setMargins(new int[] { 30, 40, 10, 10 });
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setMarginsColor(Color.WHITE);
        String[] allMonths = context.getResources().getStringArray(R.array.all_months);
        for (int i = 0; i < allMonths.length; i++) {
            renderer.addXTextLabel((i + 1), allMonths[i]);
        }

        int[] allDays = context.getResources().getIntArray(R.array.all_days);
        for (int day : allDays) {
            renderer.addYTextLabel(day, String.valueOf(day));
        }

        renderer.setChartTitle(context.getString(R.string.graphs_year_actual));
        renderer.setXTitle(context.getString(R.string.graphs_x_label));
        renderer.setYTitle(context.getString(R.string.graphs_y_label));
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(12.5);
        renderer.setYAxisMin(0.5);
        renderer.setYAxisMax(31.5);

        renderer.setAxesColor(Color.GRAY);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setApplyBackgroundColor(true);
        // renderer.setGridColor(Color.RED);
        // renderer.setShowGrid(true);
        // renderer.setShowLegend(true);
        renderer.setShowLabels(true);
        renderer.setZoomEnabled(false, false);
        renderer.setPanEnabled(false);
        // renderer.setShowGridX(true);
        // renderer.setShowGridY(true);
        // renderer.setGridColor(Color.BLUE);
        return renderer;
    }
}
