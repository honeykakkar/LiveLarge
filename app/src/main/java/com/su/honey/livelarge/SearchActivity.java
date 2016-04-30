package com.su.honey.livelarge;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnClickIListener{

    ActionBar MyActionBar;
    Toolbar MyToolBar;
    Spinner States;
    Spinner Cities;
    NavigationView MyNavView;
    DrawerLayout MyDrawLayout;
    Firebase QueryRef;
    static CircleImageView Logo;
    static TextView Usertitle;
    static MenuItem LoginItem;
    List<Serializable_PropData> ResultProps = new ArrayList<Serializable_PropData>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        QueryRef = new Firebase("https://livelarge.firebaseio.com/");
        setTheme(R.style.AppThemeNoAB);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        MyToolBar = (Toolbar)findViewById(R.id.action_toolbar);
        setSupportActionBar(MyToolBar);
        MyActionBar = getSupportActionBar();
        if(MyActionBar!=null)
            MyActionBar.setDisplayHomeAsUpEnabled(true);
        MyNavView = (NavigationView)findViewById(R.id.NavigationView);
        MyNavView.setNavigationItemSelectedListener(this);
        MyDrawLayout = (DrawerLayout)findViewById(R.id.NavigationDrawer);

        if(CurrentUser.getUserName() != null) {
            View HeaderView = (View) MyNavView.getHeaderView(0);
            CircleImageView Logo = (CircleImageView) HeaderView.findViewById(R.id.navheader_image);
            Picasso.with(getApplicationContext()).load(CurrentUser.getUserImageURL()).into(Logo);
            Usertitle = (TextView) HeaderView.findViewById(R.id.navheader_label);
            Usertitle.setText(CurrentUser.getUserName());
            LoginItem = MyNavView.getMenu().getItem(1);
            LoginItem.setTitle("Logout");
        }
        ActionBarDrawerToggle ABDT = new ActionBarDrawerToggle(this, MyDrawLayout,MyToolBar,R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        MyDrawLayout.setDrawerListener(ABDT);
        ABDT.syncState();
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int ID = item.getItemId();
        switch (ID)
        {
            case R.id.Home:
            {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                SearchActivity.this.startActivity(intent);
                break;
            }
            case R.id.Login:
            {
                if(QueryRef.getAuth() == null) {
                    Intent myIntent = new Intent(SearchActivity.this, LoginActivity.class);
                    SearchActivity.this.startActivity(myIntent);
                }
                else
                {
                    View HeaderView = (View) MyNavView.getHeaderView(0);
                    CircleImageView Logo = (CircleImageView) HeaderView.findViewById(R.id.navheader_image);
                    Usertitle = (TextView) HeaderView.findViewById(R.id.navheader_label);
                    CurrentUser.setUserImageURL("");
                    CurrentUser.setUserImageURL("");
                    CurrentUser.setUserEmail("");
                    LoginItem = MyNavView.getMenu().getItem(1);
                    Logo.setImageResource(R.mipmap.applogo);
                    Usertitle.setText("LiveLarge");
                    LoginItem.setTitle("Login");
                    QueryRef.unauth();
                    Intent myIntent = new Intent(SearchActivity.this, LoginActivity.class);
                    SearchActivity.this.startActivity(myIntent);
                }
                break;
            }
            case R.id.Search:
            {
                Intent myIntent = new Intent(SearchActivity.this, SearchActivity.class);
                SearchActivity.this.startActivity(myIntent);
                break;
            }
            case R.id.SubmitAd:
            {
                if(QueryRef.getAuth() != null && CurrentUser.getUserName() != null) {
                    Intent myIntent = new Intent(SearchActivity.this, PostListing.class);
                    SearchActivity.this.startActivity(myIntent);
                }
                else
                {
                    Toast.makeText(SearchActivity.this, "Submit ad needs user to be logged in", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.AboutUs:
            {
                Intent myIntent = new Intent(SearchActivity.this, AboutUs.class);
                SearchActivity.this.startActivity(myIntent);
                break;
            }
            default:break;
        }
        MyDrawLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
