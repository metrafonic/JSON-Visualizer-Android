package com.metrafonic.jsonvisualizer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Iterator;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.metrafonic.jsonvisualizer.FragmentJSONObject.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.metrafonic.jsonvisualizer.FragmentJSONObject#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FragmentJSONObject extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentJSONObject.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentJSONObject newInstance(String param1, String param2) {
        FragmentJSONObject fragment = new FragmentJSONObject();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public FragmentJSONObject() {
        // Required empty public constructor
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

        View view = inflater.inflate(R.layout.fragment_jsonobject, container, false);
        if (savedInstanceState==null) {

            final LinearLayout layoutForObjectList = (LinearLayout) view.findViewById(R.id.layoutForObjectList);
            String toBeJSONObject;
            if (getArguments() != null) {
                toBeJSONObject = this.getArguments().getString("jsonObject");

                JSONObject jsonResponse = null;
                try {
                    jsonResponse = new JSONObject(toBeJSONObject);
                    Iterator<String> iter = jsonResponse.keys();
                    mListener.addToLog("JSON Object Content");
                    while (iter.hasNext()) {
                        String key = iter.next();
                        View cell = inflater.inflate(R.layout.cell, container, false);
                        TextView cellTitle = (TextView) cell.findViewById(R.id.textView);
                        TextView cellType = (TextView) cell.findViewById(R.id.textView2);

                        try {
                            Object value = jsonResponse.get(key);
                            if (value instanceof JSONArray) {
                                // It's an array
                                value = (JSONArray) value;
                                mListener.addToLog("Array: " + key.toString() + "\nContents:" + value.toString());
                                cellTitle.setText(key.toString());
                                cellType.setText("(JSON Array)");
                                final Object finalValue = value;
                                cell.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mListener.onArrayClicked((JSONArray) finalValue);
                                    }
                                });
                            } else if (value instanceof JSONObject) {
                                // It's an object
                                value = (JSONObject) value;
                                mListener.addToLog("Object: " + key.toString() + "\nContents:" + value.toString());
                                cellTitle.setText(key.toString());
                                cellType.setText("(JSON Object)");
                                final Object finalValue = value;
                                cell.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mListener.onObjectClicked((JSONObject) finalValue);
                                    }
                                });
                            } else {
                                // It's something else, like a string or number

                                value = value.toString();

                                mListener.addToLog("String or number: " + key.toString() + "\nContents: " + value.toString());

                                cellTitle.setText(key.toString());
                                cellType.setText("(String or int)");
                                final Object finalValue = value;
                                cell.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mListener.onStringClicked((String) finalValue);
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            // Something went wrong!
                        }

                        layoutForObjectList.addView(cell);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
                mListener.addToLog("Failed to get data");
            }
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onArrayClicked(JSONArray JSONArray);
        public void onObjectClicked(JSONObject JSONObject);
        public void onStringClicked(String String);
        public void onIntClicked(int Integer);
        public void addToLog(String log);
    }

}
