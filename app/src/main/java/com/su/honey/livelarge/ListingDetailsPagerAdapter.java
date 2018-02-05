package com.su.honey.livelarge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Sravanya on 4/17/2016.
 */
class ListingDetailsPagerAdapter extends FragmentStatePagerAdapter {

    private final int numOfTabs;
    private final Serializable_PropData propData;

    public ListingDetailsPagerAdapter(FragmentManager fm, int tabs, Serializable_PropData propData){
        super(fm);
        numOfTabs = tabs;
        this.propData = propData;
    }

    @Override
    public Fragment getItem(int pos){

        switch (pos){
            case 0:
                return DetailsFragment.newInstance(propData);
            case 1:
                return ImagesFragment.newInstance(propData);
            case 2:
                return LocationFragment.newInstance(propData);
            default: return DetailsFragment.newInstance(propData);
        }
    }

    @Override
    public int getCount(){
        return numOfTabs;
    }
}
