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
public class RecyclerViewActivity extends AppCompatActivity implements OnClickIListener
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
        Toast.makeText(RecyclerViewActivity.this, "Recycler View", Toast.LENGTH_SHORT).show();
        RecyclerView_Fragment fragment;
        MyToolBar = (Toolbar)findViewById(R.id.action_toolbar);
        setSupportActionBar(MyToolBar);
        MyActionBar = getSupportActionBar();
        if(MyActionBar!=null)
            MyActionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null)
        {
            fragment = (RecyclerView_Fragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
        }
        else
        {
            RecyclerViewActivity.this.FragmentSelected(0);
        }
        Intent Search = getIntent();
        if( getIntent().getStringExtra("from").equalsIgnoreCase("SearchActivity"))
            SearchObject = (SearchParams) getIntent().getSerializableExtra("searchobject");
    }

    @Override
    public void FragmentSelected(int Section) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Coverpage, RecyclerView_Fragment.FragmentFactory(Section))
                .commit();
    }
}
