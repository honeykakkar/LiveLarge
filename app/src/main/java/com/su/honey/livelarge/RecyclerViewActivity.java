package com.su.honey.livelarge;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.Serializable;
import java.util.List;

/**
 * Created by honey on 2/13/2016.
 */
public class RecyclerViewActivity extends AppCompatActivity implements OnClickIListener
{
    ActionBar MyActionBar;
    Toolbar MyToolBar;
    List<Serializable_PropData> Results;
    Spinner States;
    Spinner Cities;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.AppThemeNoAB);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toast.makeText(RecyclerViewActivity.this, "Recycler View", Toast.LENGTH_SHORT).show();
        RecyclerView_Fragment fragment;
        MyToolBar = (Toolbar)findViewById(R.id.action_toolbar);
        setSupportActionBar(MyToolBar);
        MyActionBar = getSupportActionBar();
        if(MyActionBar!=null)
            MyActionBar.setDisplayHomeAsUpEnabled(true);
        Intent PrevActivity = getIntent();
        States = (Spinner)findViewById(R.id.state_spinner);
        Cities = (Spinner)findViewById(R.id.city_spinner);
        if(States!=null)
            States.setSelection(getIntent().getIntExtra("state",0));
        if(Cities!=null)
            Cities.setSelection(getIntent().getIntExtra("city",0));
        if( getIntent().getStringExtra("from").equalsIgnoreCase("SearchActivity"))
            Results = (List<Serializable_PropData>) getIntent().getSerializableExtra("resultobject");

        if (savedInstanceState != null)
        {
            fragment = (RecyclerView_Fragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
        }
        else
        {
            RecyclerViewActivity.this.FragmentSelected(0);
        }
    }

    @Override
    public void FragmentSelected(int Section) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Coverpage, RecyclerView_Fragment.FragmentFactory(Section, Results))
                .commit();
    }

    @Override
    public void StartIntent(SearchParams searchParams) {

    }

    @Override
    public void GetPropDetails(Serializable_PropData SP) {
        Intent GetDetails = new Intent(RecyclerViewActivity.this, ListingDetails.class);
        GetDetails.putExtra("Prop_Data", (Serializable) SP);
        GetDetails.putExtra("from", "RecyclerViewActivity");
        RecyclerViewActivity.this.startActivity(GetDetails);
    }
}
