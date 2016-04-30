package com.su.honey.livelarge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Sravanya on 4/17/2016.
 */
public class ListingDetailsPagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;
    Serializable_PropData propData;

    public ListingDetailsPagerAdapter(FragmentManager fm, int tabs, Serializable_PropData propData){
        super(fm);
        numOfTabs = tabs;
        this.propData = propData;
    }

    @Override
    public Fragment getItem(int pos){

        switch (pos){
            case 0: DetailsFragment detailsTab = DetailsFragment.newInstance(propData);
                return detailsTab;
            case 1: ImagesFragment imagesTab = ImagesFragment.newInstance(propData);
                return imagesTab;
            case 2: LocationFragment locationTab = LocationFragment.newInstance(propData);
                return locationTab;
            default: return DetailsFragment.newInstance(propData);
        }
    }

    @Override
    public int getCount(){
        return numOfTabs;
    }
}
