package com.example.gofu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.gofu.ui.fragments.AccountFragment;
import com.example.gofu.ui.fragments.HomeFragment;
import com.example.gofu.ui.fragments.MoreFragment;
import com.example.gofu.ui.fragments.ReportIncidentFragment;
import com.example.gofu.ui.fragments.UserMoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage2);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_account:
                        fragment = new AccountFragment();
                        break;
                    case R.id.nav_more:
                        fragment = new UserMoreFragment();
                    default:
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
    //function to get selected issuses
    public void SelectIssues(View ch){

        //get idof checked box
        int id = ch.getId();

        boolean isChecked = ((CheckBox)ch).isChecked();

        switch(id){

            case R.id.cbAccident:
                if(isChecked) {
                    list.add("Accident");
                }else {
                    list.remove("Accident");
                }
                break;
            case R.id.cbBroke:
                if(isChecked) {
                    list.add("Broken Headlight");
                }else {
                    list.remove("Broken Headlight");
                }
                break;
            case R.id.cbDead:
                if(isChecked) {
                    list.add("Dead Battery");
                }else {
                    list.remove("Dead Battery");
                }
                break;
            case R.id.cbFaulty:
                if(isChecked) {
                    list.add("Faulty Direction Switch");
                }else {
                    list.remove("Faulty Direction Switch");
                }
                break;
            case R.id.cbFlat:
                if(isChecked) {
                    list.add("Flat Tire");
                }else {
                    list.remove("Flat Tire");
                }
                break;
            case R.id.cbMotor:
                if(isChecked) {
                    list.add("Motor Issues");
                }else{
                    list.remove("Motor Issues");
                }
                break;
            case R.id.cbSpeed:
                if(isChecked) {
                    list.add("No Speed Controls/ Brakes");
                }else{
                    list.remove("No Speed Controls/ Brakes");
                }
                break;

        }
    }
}
