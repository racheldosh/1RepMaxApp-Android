package com.example.fitbod1repmaxapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fitbod1repmaxapp.types.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ExerciseListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use linear layout
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // get data set
        HashMap<String, ArrayList<Exercise>> data = getData();

        mAdapter = new ExerciseAdapter(data);
        recyclerView.setAdapter(mAdapter);
    }

    private HashMap<String, ArrayList<Exercise>> getData() {
        InputStream inputStream = getContext().getResources().openRawResource(R.raw.data);
        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);

        String line;
        HashMap<String, ArrayList<Exercise>> exerciseMap = new HashMap<>();

        try {
            while ((line = buffreader.readLine()) != null) {
                Exercise newExercise = new Exercise(line);
                String name = newExercise.name;
                if (!exerciseMap.containsKey(newExercise.name)) {
                    // exercise is new, create new and add to map
                    ArrayList<Exercise> newList = new ArrayList<>();
                    newList.add(newExercise);
                    exerciseMap.put(name, newList);
                } else {
                    // exercise already exists, add
                    exerciseMap.get(name).add(newExercise);
                }
            }
        } catch (IOException e) {
            return null;
        }
        return exerciseMap;
    }

    public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
        private HashMap<String, ArrayList<Exercise>> mDataset;

        ExerciseAdapter(HashMap<String, ArrayList<Exercise>> data) {
            mDataset = data;
        }

        @Override
        public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exercise_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Object[] exercises = mDataset.keySet().toArray();
            final String exerciseName = exercises[position].toString();

            TextView nameView = holder.layout.findViewById(R.id.exercise_name);
            nameView.setText(exerciseName);

            ArrayList<Exercise> list = mDataset.get(exerciseName);
            int repMax = list.get(0).repMax;
            Date currentDate = list.get(0).date;

            for (int i = 1; i < list.size(); i++) {
                Date newDate = list.get(i).date;
                int newRepMax = list.get(i).repMax;

                if (newDate.compareTo(currentDate) > 0) {
                    // newer date; update date and repMax
                    currentDate = newDate;
                    repMax = newRepMax;
                } else if (newDate.compareTo(currentDate) == 0 && newRepMax > repMax) {
                    // same date, update if repmax is greater
                    repMax = newRepMax;
                }
            }

            TextView repMaxView = holder.layout.findViewById(R.id.rep_max);
            repMaxView.setText(String.format(Locale.US, "%d", repMax));

            RelativeLayout container = holder.layout.findViewById(R.id.exercise_view);
            final int finalRepMax = repMax;
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // pass data to new fragment
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dataArray", mDataset.get(exerciseName));
                    bundle.putInt("repMax", finalRepMax);

                    Fragment nextFrag= new GraphFragment();
                    nextFrag.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataset.keySet().size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout layout;

            ViewHolder(RelativeLayout v) {
                super(v);
                layout = v;
            }
        }
    }
}
