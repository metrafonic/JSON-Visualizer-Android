package com.metrafonic.jsonvisualizer;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


public class ActivityJSON extends ActionBarActivity implements LoadingFragment.OnFragmentInteractionListener,FragmentJSONObject.OnFragmentInteractionListener, FragmentJSONArray.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_json);
        if (savedInstanceState==null) {
            Bundle b = getIntent().getExtras();
            String jsonString = b.getString("jsonstring");
            FragmentJSONObject newFragment = new FragmentJSONObject();
            replaceFragment(jsonString, "jsonObject", false, newFragment, "");
            /*
            FragmentJSONObject newFragment = new FragmentJSONObject();
            Bundle args = new Bundle();
            args.putString("jsonObject", jsonString);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enterfromright, FragmentTransaction.TRANSIT_NONE, FragmentTransaction.TRANSIT_NONE, R.anim.exitfromright);
            transaction.replace(R.id.fragment_place, newFragment);
            //transaction.addToBackStack(null);
            transaction.commit();
            */
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.json, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onArrayClicked(JSONArray TheJSONArray, String title){
        //Toast.makeText(this, TheJSONArray.toString(), Toast.LENGTH_SHORT).show();
        FragmentJSONArray newFragment = new FragmentJSONArray();
        replaceFragment(TheJSONArray.toString(), "jsonArray", true, newFragment, title);
    }
    @Override
    public void onObjectClicked(JSONObject TheJSONObject, String title){
        //Toast.makeText(this, TheJSONObject.toString(), Toast.LENGTH_SHORT).show();
        FragmentJSONObject newFragment = new FragmentJSONObject();
        replaceFragment(TheJSONObject.toString(), "jsonObject", true, newFragment, title);

    }
    @Override
    public void onStringClicked(String String){
        Toast.makeText(this, String.toString(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onIntClicked(int Integer){
        Toast.makeText(this, Integer + "hi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentLoading() {

    }

    void poo (){

    }
    public void replaceFragment(String data, String type, Boolean keepbackstack, android.support.v4.app.Fragment frag, String title){


        Bundle args = new Bundle();
        args.putString(type, data);
        args.putString("title", title);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.anim.enterfromright, R.anim.exitfromleft, R.anim.enterfromleft, R.anim.exitfromright);
        transaction.replace(R.id.fragment_place, frag);
        frag.setArguments(args);
        if (keepbackstack)transaction.addToBackStack(null);
        transaction.commit();

    }
    public void addToLog(String log){
        TextView text = (TextView) findViewById(R.id.textView);
        text.append("\n\n" + log);
        final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollView));
        scrollview.post(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void updateBack(String title) {
        getSupportActionBar().setTitle(title);
    }
}
