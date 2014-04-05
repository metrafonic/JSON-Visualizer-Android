package com.metrafonic.jsonvisualizer.android;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


public class ActivityJSON extends ActionBarActivity implements LoadingFragment.OnFragmentInteractionListener,FragmentJSONObject.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState==null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_json);
            Bundle b = getIntent().getExtras();
            String jsonString = b.getString("jsonstring");
            replaceFragment(jsonString, "jsonObject", false);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onArrayClicked(JSONArray JSONArray){
        Toast.makeText(this, JSONArray.toString(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onObjectClicked(JSONObject TheJSONObject){
        //Toast.makeText(this, TheJSONObject.toString(), Toast.LENGTH_SHORT).show();
        replaceFragment(TheJSONObject.toString(), "jsonObject", true);

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

    public void replaceFragment(String data, String type, Boolean keepbackstack){

        FragmentJSONObject newFragment = new FragmentJSONObject();
        Bundle args = new Bundle();
        args.putString(type, data);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.anim.enterfromright, FragmentTransaction.TRANSIT_NONE, FragmentTransaction.TRANSIT_NONE, R.anim.exitfromright);
        transaction.replace(R.id.fragment_place, newFragment);
        newFragment.setArguments(args);
        if (keepbackstack)transaction.addToBackStack(null);
        transaction.commit();

    }
}
