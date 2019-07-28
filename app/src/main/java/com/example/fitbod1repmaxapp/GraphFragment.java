package com.example.fitbod1repmaxapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitbod1repmaxapp.types.Exercise;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GraphFragment extends Fragment {

    final int NUM_DATA_POINTS = 5;
    final int NUM_HORIZONTAL_LABELS = 4;

    ArrayList<Exercise> data;
    int repMax;
    HashMap<Date, Integer> cleanDataMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        data = (ArrayList<Exercise>) getArguments().getSerializable("dataArray");
        repMax = (int) getArguments().getInt("repMax");

        // clean up data: only need best repMax per date
        for (Exercise each : data) {
            if (!cleanDataMap.containsKey(each.date)) {
                cleanDataMap.put(each.date, each.repMax);
            } else if (each.repMax > cleanDataMap.get(each.date)) {
                cleanDataMap.put(each.date, each.repMax);
            }
        }

        return inflater.inflate(R.layout.fragment_graph, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView nameView = getActivity().findViewById(R.id.exercise_name);
        nameView.setText(data.get(0).name);

        TextView repMaxView = getActivity().findViewById(R.id.rep_max);
        repMaxView.setText(String.format("%d", repMax));

        GraphView graph = (GraphView) getActivity().findViewById(R.id.graph);
        graph.setVisibility(View.VISIBLE);

        try {
            ArrayList<Date> dates = new ArrayList<>();
            for (Map.Entry<Date, Integer> entry : cleanDataMap.entrySet()) {
                dates.add(entry.getKey());
            }
            Collections.sort(dates);

            // handle <= 5 data points
            DataPoint[] dataPoints;
            if (cleanDataMap.size() <= NUM_DATA_POINTS) {
                dataPoints = new DataPoint[cleanDataMap.size()];
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(dates.get(i), cleanDataMap.get(dates.get(i)));
                }
            } else { // handle > 5 data points
                dataPoints = new DataPoint[NUM_DATA_POINTS];
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(dates.get(dates.size() - NUM_DATA_POINTS + i),
                            cleanDataMap.get(dates.get(dates.size() - 5 + i)));
                }
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

            // colors
            series.setColor(getResources().getColor(R.color.colorAccent));
            graph.getGridLabelRenderer().setGridColor(getResources().getColor(R.color.textSecondary));
            graph.getGridLabelRenderer().setHorizontalLabelsColor(getResources().getColor(R.color.textPrimary));
            graph.getGridLabelRenderer().setVerticalLabelsColor(getResources().getColor(R.color.textPrimary));

            // label formatting
            graph.getGridLabelRenderer().setNumHorizontalLabels(NUM_HORIZONTAL_LABELS);
            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        long newLong = (long) value;
                        Date newDate = new Date(newLong);
                        return new SimpleDateFormat("MMM dd").format(newDate);
                    } else {
                        return super.formatLabel(value, isValueX) + " lbs";
                    }
                }
            });

            graph.addSeries(series);

        } catch (IllegalArgumentException e) {
            Log.e("GraphFragment", e.toString());
        }
    }
}
