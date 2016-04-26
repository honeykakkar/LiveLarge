package com.su.honey.livelarge;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.firebase.client.Firebase;

/**
 * Created by honey on 2/13/2016.
 */
public class FBRecyclerViewActivity extends AppCompatActivity implements OnClickIListener
{
    ActionBar MyActionBar;
    Toolbar MyToolBar;
    SearchParams SearchObject;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Firebase.setAndroidContext(this);
        setTheme(R.style.AppThemeNoAB);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toast.makeText(FBRecyclerViewActivity.this, "Recycler View", Toast.LENGTH_SHORT).show();
        RecyclerViewFB_Fragment fragment;
        MyToolBar = (Toolbar)findViewById(R.id.action_toolbar);
        setSupportActionBar(MyToolBar);
        MyActionBar = getSupportActionBar();
        if(MyActionBar!=null)
            MyActionBar.setDisplayHomeAsUpEnabled(true);
        Intent Search = getIntent();
        if( getIntent().getStringExtra("from").equalsIgnoreCase("SearchActivity"))
            SearchObject = (SearchParams) getIntent().getSerializableExtra("searchobject");
        if (savedInstanceState != null)
        {
            fragment = (RecyclerViewFB_Fragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
        }
        else
        {
            FBRecyclerViewActivity.this.FragmentSelected(0);
        }

    }

    @Override
    public void FragmentSelected(int Section) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Coverpage, RecyclerViewFB_Fragment.FragmentFactory(Section, SearchObject))
                .commit();
    }
}
