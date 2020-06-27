package com.example.project3_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<HashMap<String,String>> vehiclesList;     // arraylist of vehicles
    //private TextView textView;
    private TextView makeModelTextView;
    private TextView priceTextView;
    private TextView descriptionTextView;
    private TextView vinTextView;
    private TextView lastUpdateTextView;
    private ImageView carImageView;

    private int position;   // position in list to get vehicle data for

    public CarDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarDetailFragment newInstance(String param1, int param2) {
        CarDetailFragment fragment = new CarDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, String.valueOf(param2));
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
        View view;
        view = inflater.inflate(R.layout.fragment_car_detail,container,false);

        //vehiclesList = (ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("vehiclesListData");
        //String result = savedInstanceState.getString("key");
        Bundle bundle = getArguments();
        //String result = bundle.getString("key");
        position = Integer.parseInt(Objects.requireNonNull(bundle.getString("positionKey")));
        //position = Integer.parseInt(mParam2);    // get passed in string position and cast to int
        vehiclesList = (ArrayList<HashMap<String, String>>) bundle.getSerializable("vehiclesListData"); // get passed  in data

        // init UI components below
        //String result = vehiclesList.get(position).get("vin_number");
        //textView = view.findViewById(R.id.textViewTest);
        //textView.setText(result);
        makeModelTextView = view.findViewById(R.id.makeModelTextView);
        priceTextView = view.findViewById(R.id.priceTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        vinTextView = view.findViewById(R.id.vinTextView);
        lastUpdateTextView = view.findViewById(R.id.lastUpdateTextView);
        carImageView = view.findViewById(R.id.carImageView);

        // extract needed car info
        String vin = vehiclesList.get(position).get("vin_number");
        String price = vehiclesList.get(position).get("price");
        String created_at = vehiclesList.get(position).get("created_at");
        String veh_description = vehiclesList.get(position).get("veh_description");
        String image_url = vehiclesList.get(position).get("image_url");
        String mileage = vehiclesList.get(position).get("mileage");
        String make = vehiclesList.get(position).get("vehicle_make");
        String model = vehiclesList.get(position).get("model");

        // set UI components with passed in data
        String temp = make + " | " + model;
        makeModelTextView.setText(temp);
        String temp2 = "$ " + price;
        priceTextView.setText(temp2);
        descriptionTextView.setText("Vehicle Info:\n\n" + veh_description);
        vinTextView.setText("VIN: " + vin + "  |  " + "Mileage: " + mileage);
        lastUpdateTextView.setText("Last Update: " + created_at);

        renderCarInUI(model);

        // Inflate the layout for this fragment
        return view;
    }



    /**
     * Method renders car image for inputted car model in UI
     * @param model The car model to find image of
     */
    private void renderCarInUI(String model) {
        assert model != null;
        if(model.equals("Model S")) {                   // Tesla's
            setCarImage("tesla_model_s");

        } else if (model.equals("Model X")) {
            setCarImage("tesla_model_x");
        }

        if(model.equals("DB11")) {                      // Aston Martin's
            setCarImage("db11");
        } else if(model.equals("V12 Vantage")) {
            setCarImage("v12vantage");
        }

        if(model.equals("Continental")) {               // Bentley's
            setCarImage("continental");
        }

        if(model.equals("M6")) {                        // BMW's
            setCarImage("m6");
        }

        if(model.equals("360")) {                       // Ferrari's
            setCarImage("f360");
        } else if(model.equals("F430")) {
            setCarImage("f430");
        }

        if(model.equals("XJ")) {                        // Jaguars
            setCarImage("xj");
        }

        if(model.equals("Aventador")) {                 // Lamborghini's
            setCarImage("aventador");
        } else if (model.equals("Huracan")) {
            setCarImage("huracan");
        } else if (model.equals("Urus")) {
            setCarImage("urus");
        }

        if(model.equals("GranTurismo")) {               // Maserati's
            setCarImage("gran_turismo");
        } else if(model.equals("Levante")) {
            setCarImage("levante");
        }

        if(model.equals("Boxter")) {                    // Porsche's
            setCarImage("boxter");
        } else if(model.equals("Cayman")) {
            setCarImage("cayman");
        }

    }


    /**
     * Method finds res id of desired car image for app rendering
     * @param modelName string of car name
     */
    private void setCarImage(String modelName) {
        int drawableResourceId = getResources().getIdentifier(modelName, "drawable",getContext().getPackageName());
        carImageView.setImageResource(drawableResourceId);
    }
}