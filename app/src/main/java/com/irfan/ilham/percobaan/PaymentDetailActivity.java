package com.irfan.ilham.percobaan;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class PaymentDetailActivity extends AppCompatActivity {

    private LineChartView IncomeChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        IncomeChart = findViewById(R.id.IncomePaymentDetailChart);

        IncomeChart.setInteractive(false);

        List<PointValue> values = new ArrayList<PointValue>();
        values.add(new PointValue(0, 10));
        values.add(new PointValue(1, 35));
        values.add(new PointValue(2, 20));
        values.add(new PointValue(3, 50));
        values.add(new PointValue(4, 45));
        values.add(new PointValue(5, 75));
        values.add(new PointValue(6, 60));

        Line line = new Line(values).setColor(R.attr.orange).setCubic(false).setHasPoints(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        IncomeChart.setViewportCalculationEnabled(false);

        IncomeChart.setLineChartData(data);

        Viewport v = new Viewport(IncomeChart.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.right = 6;
        IncomeChart.setMaximumViewport(v);
        IncomeChart.setCurrentViewportWithAnimation(v);
        Axis axisY = new Axis().setHasLines(true).setHasTiltedLabels(false).setLineColor(R.attr.mediumColor).setTextColor(R.attr.mediumColor).setHasSeparationLine(false);
        data.setAxisXBottom(null);
        data.setAxisYLeft(axisY);
    }
}
