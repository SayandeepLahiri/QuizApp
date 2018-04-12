package com.example.sayandeep.quizquotient.Acitivities;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sayandeep.quizquotient.R;



public class RankingFragment extends Fragment {
    View myFrag;
    public static RankingFragment newInstance()
    {
    RankingFragment rankingFragment=new RankingFragment();
    return rankingFragment;}

   /* @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();}*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFrag=inflater.inflate(R.layout.fragment_ranking,container,false);
        return myFrag;

    }
}