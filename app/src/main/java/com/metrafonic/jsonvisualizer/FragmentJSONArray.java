package com.metrafonic.jsonvisualizer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.metrafonic.jsonvisualizer.FragmentJSONArray.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.metrafonic.jsonvisualizer.FragmentJSONArray#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FragmentJSONArray extends Fragment {
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
     * @return A new instance of fragment FragmentJSONArray.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentJSONArray newInstance(String param1, String param2) {
        FragmentJSONArray fragment = new FragmentJSONArray();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public FragmentJSONArray() {
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
        View view = inflater.inflate(R.layout.fragment_jsonarray, container, false);

        String toBeJSONArray = this.getArguments().getString("jsonArray");
        final String oldtitle = this.getArguments().getString("title");
        JSONArray jsonResponse = null;
        final LinearLayout layoutForArrayList = (LinearLayout) view.findViewById(R.id.layoutForArrayList);
        try{
            jsonResponse = new JSONArray(toBeJSONArray);
            for (int i = 0; i< jsonResponse.length(); i++){
                //Toast.makeText(getActivity(), jsonResponse.get(i).toString(), Toast.LENGTH_SHORT).show();
                View cell = inflater.inflate(R.layout.cell, container, false);
                TextView cellTitle = (TextView) cell.findViewById(R.id.textView);
                TextView cellType = (TextView) cell.findViewById(R.id.textView2);
                try{
                    if (jsonResponse.get(i) instanceof JSONArray) {
                        // It's an array
                        cellTitle.setText("[" + i + "]");
                        cellType.setText("(JSON Array)");
                        final JSONArray finalJsonResponse1 = jsonResponse;
                        final int finalI1 = i;
                        cell.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    mListener.onArrayClicked(finalJsonResponse1.getJSONArray(finalI1), oldtitle + "" + ("["+ finalI1 + "]"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }else if (jsonResponse.get(i) instanceof JSONObject) {
                        // It's an object
                        cellTitle.setText("[" + i + "]");
                        cellType.setText("(JSON Object)");
                        final JSONArray finalJsonResponse = jsonResponse;
                        final int finalI = i;
                        cell.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    mListener.onObjectClicked(finalJsonResponse.getJSONObject(finalI), oldtitle + "" + ("["+ finalI + "]"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }else {
                        cellTitle.setText("[" + i + "]");
                        cellType.setText("(String or int)");
                        final JSONArray finalJsonResponse = jsonResponse;
                        final int finalI = i;
                        cell.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    mListener.onStringClicked(finalJsonResponse.getString(finalI));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }catch (JSONException e) {
                    // Something went wrong!
                }
                layoutForArrayList.addView(cell);
            }
        } catch (JSONException e) {
            // Something went wrong!
        }

        return view;
    }



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
    public void onResume() {
        super.onResume();
        // Set title
        mListener.updateBack(this.getArguments().getString("title"));
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
        public void onArrayClicked(JSONArray JSONArray, String title);
        public void onObjectClicked(JSONObject JSONObject, String title);
        public void onStringClicked(String String);
        public void onIntClicked(int Integer);
        public void addToLog(String log);
        public void updateBack(String title);
    }

}
