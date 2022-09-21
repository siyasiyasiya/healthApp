    package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

    /**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseDisplayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

        String name = "";
        String muscle = "";
        String type = "";
        String equipment = "";
        String difficulty = "";
        String instructions = "";

    private Button clickBtn;

    public ExerciseDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseDisplayFragment newInstance(String param1, String param2) {
        ExerciseDisplayFragment fragment = new ExerciseDisplayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_display, container, false);
        clickBtn = view.findViewById(R.id.viewExerciseBtn);
        TextView exerciseLbl = view.findViewById(R.id.exerciseLbl);
        TextView muscleGroup = view.findViewById(R.id.exerciseType);
        TextView exerciseType = view.findViewById(R.id.targetMuscle);

        ImageView easyDumbell = view.findViewById(R.id.easyDumbell);
        ImageView mediumDumbell = view.findViewById(R.id.mediumDumbell);
        ImageView hardDumbell = view.findViewById(R.id.hardDumbell);

        Bundle bundle = getArguments();
        name = bundle.getString("name");
        type = bundle.getString("type");
        muscle = bundle.getString("muscle");
        equipment = bundle.getString("equipment");
        difficulty = bundle.getString("difficulty");
        instructions = bundle.getString("instructions");

        exerciseLbl.setText(name);

        String typeDisplay = type;
        if(type.contains("_")){
            typeDisplay = type.substring(0, type.indexOf("_"))+" "+type.substring(type.indexOf("_")+1, type.indexOf("_")+2).toUpperCase()+type.substring(type.indexOf("_")+2);
        }

        muscleGroup.setText("Target Muscle: "+muscle.substring(0,1).toUpperCase() + muscle.substring(1));
        exerciseType.setText("Exercise Type: "+typeDisplay.substring(0,1).toUpperCase() + typeDisplay.substring(1));

        if(difficulty.equals("beginner")){
            easyDumbell.setAlpha(1.0F);
            mediumDumbell.setAlpha(0.25F);
            hardDumbell.setAlpha(0.25F);
        }
        if(difficulty.equals("intermediate")){
            easyDumbell.setAlpha(1.0F);
            mediumDumbell.setAlpha(1.0F);
            hardDumbell.setAlpha(0.25F);
        }
        if(difficulty.equals("extreme")){
            easyDumbell.setAlpha(1.0F);
            mediumDumbell.setAlpha(1.0F);
            hardDumbell.setAlpha(1.0F);
        }


        clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpecificExercises.class);
                intent.putExtra("name", name);
                intent.putExtra("type", type);
                intent.putExtra("muscle", muscle);
                intent.putExtra("equipment", equipment);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("instructions", instructions);
                startActivity(intent);
            }
        });

        return view;
    }
}