package com.example.project3_v2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project3_v2.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Consider this code file the main start point for this project
 *
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //----------------------------------------------------------------------------------------------
    // UI components for the main activity
    private String TAG = ItemListActivity.class.getSimpleName();    // get simple name of the class in the source code

    private ProgressDialog pDialog;
    private Spinner makeSpinner;
    private Spinner modelSpinner;
    private RecyclerView itemList;


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    //----------------------------------------------------------------------------------------------
    // URL's for connection to specified remote servers
    //
    private static String carMakes = "https://thawing-beach-68207.herokuapp.com/carmakes";  // link to car makes(car brand)
    private static String url = carMakes;

    // proper model makes are retrieved by appending the id of that car model to the end of this url
    StringBuilder carModelsURLString = new StringBuilder("https://thawing-beach-68207.herokuapp.com/carmodelmakes/");   // using String builder to exploit mutability
    private static String carModelsURL = "https://thawing-beach-68207.herokuapp.com/carmodelmakes/";   // invalid link without makeID appended to the end

    //----------------------------------------------------------------------------------------------
    // local storage
    //ArrayList<HashMap<String, String>> contactList;
    private ArrayList<HashMap<String, String>> carMakesList;
    private ArrayList<String>  makeArray;
    private ArrayList<HashMap<String,String>> carModelsList;
    private ArrayList<String> modelArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        carMakesList = new ArrayList<>();   // init arraylist
        makeArray = new ArrayList<>();
        carModelsList = new ArrayList<>();
        modelArray = new ArrayList<>();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        makeSpinner = findViewById(R.id.make_spinner);
        makeSpinner.setOnItemSelectedListener(this);
        modelSpinner = findViewById(R.id.model_spinner);
        modelSpinner.setOnItemSelectedListener(this);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        new GetMake().execute();    // create a new thread to acquire contacts as JSON from a remote server
        //new GetModel().execute();

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }


    //----------------------------------------------------------------------------------------------
    // methods implemented to add item selection abilities to the spinners
    // method is passed in parent spinner, and uses position to identify the position selected by the user
    // and the corresponding data held by spinner at that location
    // parent param. lets this listener work on multiple spinners and distinguish between them
    //TODO:refactor case statements with a local method that encapsulates the replicated code
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        //String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        // case items below checking for user selection of a car make
        String item = parent.getItemAtPosition(position).toString();    // get string of selected car make( vehicle brand)
        switch (item) {      // find which make was selected to display the related models in that car brand
            case "Jaguar":  // id 2
                System.out.println("************");
                System.out.println(position);
                System.out.println("************");
                carModelsURLString.replace(0, carModelsURLString.length(), carModelsURL);
                carModelsURLString.append("2");
                new GetModel().execute();
                break;

            case "Tesla":   // id 3
                carModelsURLString.replace(0, carModelsURLString.length(), carModelsURL);
                carModelsURLString.append("3");
                new GetModel().execute();
                //lv.clearChoices();
                //new GetAvailableVehicles().execute();
                break;

            case "Lamborghini":
                carModelsURLString.replace(0, carModelsURLString.length(), carModelsURL);
                carModelsURLString.append("4");
                new GetModel().execute();
                break;

            case "Ferrari":
                carModelsURLString.replace(0, carModelsURLString.length(), carModelsURL);
                carModelsURLString.append("5");
                new GetModel().execute();
                break;

            case "Porsche":
                carModelsURLString.replace(0, carModelsURLString.length(), carModelsURL);
                carModelsURLString.append("6");
                new GetModel().execute();
                break;

            case "Bugatti":
                carModelsURLString.replace(0, carModelsURLString.length(), carModelsURL);
                carModelsURLString.append("7");
                //System.out.println("Test");
                //System.out.println(carModelsURLString.toString());
                new GetModel().execute();
                break;

            case "Maserati":
                carModelsURLString.replace(0, carModelsURLString.length(), carModelsURL);
                carModelsURLString.append("8");
                new GetModel().execute();
                break;

            case "BMW":
                carModelsURLString.replace(0, carModelsURLString.length(), carModelsURL);
                carModelsURLString.append("9");
                new GetModel().execute();
                break;

            case "Aston Martin":
                carModelsURLString.replace(0, carModelsURLString.length(), carModelsURL);
                carModelsURLString.append("10");
                new GetModel().execute();
                break;

            case "Bentley":     // id 11
                carModelsURLString.replace(0, carModelsURLString.length(), carModelsURL);
                carModelsURLString.append("11");
                new GetModel().execute();
                break;

            default:
                //System.out.println("Invalid input");

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent, List<DummyContent.DummyItem> items, boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }


    //----------------------------------------------------------------------------------------------------------------------
    /**
     * Async task class to get json by making HTTP call
     * http call is made on a background thread by using this class extension
     *  retrieves the Make car information stored on the remote server
     */
    private class GetMake extends AsyncTask<Void, Void, Void> {

        /**
         * actions to execute before invoking background thread
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ItemListActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        /**
         * Actions to execute in the background thread
         */
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();     // create new httphandler instance

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);    // log the response from url to the terminal


            if (jsonStr != null) {  // if not null, then connection made, and data was passed from service call to the url
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray carMakesArray = new JSONArray(jsonStr);    // getting a JSONArray from the website, so use inbound jsonStr as input param. to create it

                    // Getting JSON Array node
                    //JSONArray carmakes = jsonObj.getJSONArray("carmakes");

                    // looping through All carMakes
                    for (int i = 0; i < carMakesArray.length(); i++) {
                        JSONObject c = carMakesArray.getJSONObject(i);   // create a temp json object c, and set it the ith json object in carMakes


                        String id = c.getString("id");
                        System.out.println(id);
                        String vehicle_make = c.getString("vehicle_make");
                        System.out.println(vehicle_make);

                        HashMap<String,String>  carMakes = new HashMap<>();

                        carMakes.put("id",id);
                        carMakes.put("vehicle_make",vehicle_make);

                        carMakesList.add(carMakes);

                        makeArray.add(vehicle_make);    // create an arraylist of only the makes

                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());    // invalid json received from url
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() { // execute code snippet inside the main thread
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show(); // create notific. toast
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");   // connection was not sucesfully established
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });

            }

            return null;
        }

        /**
         * Actions to perform after background thread has finished
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            //ListAdapter adapter = new SimpleAdapter(MainActivity.this, carMakesList, R.layout.list_item, new String[]{"id", "vehicle_make"},
            //        new int[]{R.id.name, R.id.email});

            //ListAdapter adapter2 = new SimpleAdapter(MainActivity.this,carMakesList,R.layout.support_simple_spinner_dropdown_item,
            //       new String [] {"vehicle_make"}, new int[] {R.id.make_spinner});
            //spinner.setAdapter(adapter2);
            String colors[] = {"Red","Blue","White","Yellow","Black", "Green","Purple","Orange","Grey"};

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, makeArray); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            makeSpinner.setAdapter(spinnerArrayAdapter);

            // Create an ArrayAdapter using the string array and a default spinner layout
            //ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.planets_array, android.R.layout.simple_spinner_item);
            //.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            //spinner.setAdapter(adapter2);

            //lv.setAdapter(adapter);
        }

    }

    //----------------------------------------------------------------------------------------------------------------------
    /**
     * Retrieves the car model information stored on the remote server
     */
    private class GetModel extends AsyncTask<Void,Void,Void> {

        /**
         * actions to execute before invoking background thread
         */
        /*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

         */


        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();     // create new httphandler instance

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(carModelsURLString.toString());

            Log.e(TAG, "Response from url: " + jsonStr);    // log the response from url to the terminal


            if (jsonStr != null) {  // if not null, then connection made, and data was passed from service call to the url
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray carModelsArray = new JSONArray(jsonStr);    // getting a JSONArray from the website, so use inbound jsonStr as input param. to create it

                    // Getting JSON Array node
                    //JSONArray carmakes = jsonObj.getJSONArray("carmakes");
                    modelArray.clear(); // clear oout the model array to prevent the spinner from being over populated

                    // looping through All carMakes
                    for (int i = 0; i < carModelsArray.length(); i++) {
                        JSONObject c = carModelsArray.getJSONObject(i);   // create a temp json object c, and set it the ith json object in carModels



                        String id = c.getString("id");
                        //System.out.println(id);
                        String model = c.getString("model");
                        //System.out.println(model);
                        String vehicle_make_id = c.getString("vehicle_make_id");
                        //System.out.println(vehicle_make_id);

                        HashMap<String,String>  carModels = new HashMap<>();

                        carModels.put("vehicle_make_id",vehicle_make_id);
                        carModels.put("model",model);

                        carModelsList.add(carModels);

                        modelArray.add(model);    // create an arraylist of only the models

                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());    // invalid json received from url
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() { // execute code snippet inside the main thread
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show(); // create notific. toast
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");   // connection was not sucesfully established
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });

            }

            return null;
        }


        /**
         * Actions to perform after background thread has finished
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            /**
             * Updating parsed JSON data into ListView
             * */


            //modelSpinner.setAdapter(null);

            ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, modelArray); //selected item will look like a spinner set from XML
            spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            modelSpinner.setAdapter(spinnerArrayAdapter2);

        }
    }

    //----------------------------------------------------------------------------------------------------------------------


}