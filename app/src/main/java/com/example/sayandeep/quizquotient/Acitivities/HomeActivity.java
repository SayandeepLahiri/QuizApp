package com.example.sayandeep.quizquotient.Acitivities;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.sayandeep.quizquotient.R;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView=findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment=null;
                switch(item.getItemId())
                {
                    case R.id.action_category: selectedFragment=CategoryFragment.newInstance();break;
                    case R.id.action_ranking:selectedFragment=RankingFragment.newInstance();break;
                }
                return true;
            }
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();


        });
    }
}
