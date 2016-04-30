package com.su.honey.livelarge;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnClickIListener{

    ActionBar MyActionBar;
    Toolbar MyToolBar;
    Spinner States;
    Spinner Cities;
    List<Serializable_PropData> ResultProps = new ArrayList<Serializable_PropData>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppThemeNoAB);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        MyToolBar = (Toolbar)findViewById(R.id.action_toolbar);
        setSupportActionBar(MyToolBar);
        MyActionBar = getSupportActionBar();
        if(MyActionBar!=null)
        {MyActionBar.setDisplayHomeAsUpEnabled(true);
        MyActionBar.setDisplayShowTitleEnabled(false);}
        Toast.makeText(SearchActivity.this, "Search Page", Toast.LENGTH_SHORT).show();
        Search_Fragment searchFragment;
        States = (Spinner)findViewById(R.id.state_spinner);
        States.setSelection(34, true);
        if (savedInstanceState != null)
            searchFragment = (Search_Fragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
        else
            SearchActivity.this.FragmentSelected(0);
    }

    @Override
    public void FragmentSelected(int Section) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Coverpage, Search_Fragment.FragmentFactory(Section))
                .commit();
    }

    @Override
    public void StartIntent(SearchParams SearchParameters) {
        for(int i =0; i<AllListings.All_Listings.size(); ++i)
        {
            Serializable_PropData Current = AllListings.All_Listings.get(i);
            if(SearchParameters.getCity().equalsIgnoreCase(Current.getProp_city())
                    && SearchParameters.getState().equalsIgnoreCase(Current.getProp_state())
                    && SearchParameters.getPropertyType().equalsIgnoreCase(Current.getProp_type())
                    && SearchParameters.getBedrooms() == Integer.parseInt(Current.getProp_bed())
                    && SearchParameters.getListType().equalsIgnoreCase(Current.getList_type()))
                ResultProps.add(Current);
        }
        for(int i=0; i<ResultProps.size(); ++i)
        {
            Serializable_PropData Current = ResultProps.get(i);
            if(SearchParameters.getMinArea() !=0
                    && Integer.parseInt(Current.getProp_area()) < SearchParameters.getMinArea())
                ResultProps.remove(i);
            if(SearchParameters.getMaxArea() !=0
                    && Integer.parseInt(Current.getProp_area()) > SearchParameters.getMaxArea())
                ResultProps.remove(i);
            if(SearchParameters.getMinBudget() !=0
                    && Integer.parseInt(Current.getProp_price()) < SearchParameters.getMinBudget())
                ResultProps.remove(i);
            if(SearchParameters.getMaxBudget() !=0
                    && Integer.parseInt(Current.getProp_area()) > SearchParameters.getMaxBudget())
                ResultProps.remove(i);
            if(SearchParameters.getLocality()!= "" && !SearchParameters.getLocality().contains(Current.getProp_address()))
                ResultProps.remove(i);
        }
        Intent Results = new Intent(SearchActivity.this, RecyclerViewActivity.class);
        States = (Spinner)findViewById(R.id.state_spinner);
        Cities = (Spinner)findViewById(R.id.city_spinner);
        if(States!=null)
            Results.putExtra("state", States.getSelectedItemPosition());
        if(Cities!=null)
            Results.putExtra("city", Cities.getSelectedItemPosition());
        Results.putExtra("resultobject", (Serializable) ResultProps);
        Results.putExtra("from", "SearchActivity");
        SearchActivity.this.startActivity(Results);
    }

    @Override
    public void GetPropDetails(Serializable_PropData SP) {

    }
}
