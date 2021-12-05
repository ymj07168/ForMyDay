package smu_it.formyday;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    LineChart monthlyChart;
    private Integer[] checkRates = {50, 20, 40, 60, 70, 80, 55, 40, 68, 90,
            80, 44};
    private Integer[] studyTimes = {10, 4, 5, 6, 7, 8, 5, 4, 6, 9,
            8, 12};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1, container, false);
        LineChart monthlyChart = (LineChart) view.findViewById(R.id.monthlyChart);

        monthlyChart.setEnabled(true);

        ArrayList<Entry> entriesRate = new ArrayList<Entry>();
        ArrayList<Entry> entriesTime = new ArrayList<Entry>();

        for (int i = 0; i < checkRates.length; i++)
            entriesRate.add(new Entry(i, checkRates[i]));

        for (int i = 0; i < studyTimes.length; i++)
            entriesTime.add(new Entry(i, studyTimes[i]));

        LineDataSet setRates = new LineDataSet(entriesRate, "달성률");
        setRates.setAxisDependency(YAxis.AxisDependency.LEFT);
        setRates.setColor(Color.BLUE);
        setRates.setCircleColor(Color.BLUE);

        LineDataSet setTimes = new LineDataSet(entriesTime, "공부시간");
        setTimes.setAxisDependency(YAxis.AxisDependency.RIGHT);
        setTimes.setColor(Color.GREEN);
        setTimes.setCircleColor(Color.GREEN);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setRates);
        dataSets.add(setTimes);

        LineData data = new LineData(dataSets);
        monthlyChart.setData(data);
        monthlyChart.invalidate();

        // the labels that should be drawn on the XAxis --> 필요 없을 수도 있음
        final Integer[] days = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return days[(int) value].toString();
            }
        };
        XAxis xAxis = monthlyChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        // Inflate the layout for this fragment
        return view;
    }
}