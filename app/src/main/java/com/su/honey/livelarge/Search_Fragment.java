package com.su.honey.livelarge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by honey on 4/18/2016.
 */
public class Search_Fragment extends android.support.v4.app.Fragment{
    public Search_Fragment()
    {

    }
    private static final String SECTION = "SECTION";

    public static Search_Fragment FragmentFactory(int SectionID)
    {
        Search_Fragment NewFragment = new Search_Fragment();
        Bundle NewBundle = new Bundle();
        NewBundle.putInt(SECTION,SectionID);
        NewFragment.setArguments(NewBundle);
        return NewFragment;
    }

    View RootView = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RootView = null;
        int Choose = getArguments().getInt(SECTION);
        switch (Choose)
        {
            case 0:
                RootView = inflater.inflate(R.layout.main_searchpage, container, false); break;
            default:break;
        }
        return RootView;
    }
}
