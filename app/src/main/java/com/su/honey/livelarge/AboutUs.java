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
import android.widget.ViewAnimator;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutUs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ActionBar MyActionBar;
    Toolbar MyToolBar;
    NavigationView MyNavView;
    DrawerLayout MyDrawLayout;
    static CircleImageView Logo;
    static TextView Usertitle;
    static MenuItem LoginItem;
    Firebase QueryRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        QueryRef = new Firebase("https://livelarge.firebaseio.com/");
        final ViewAnimator viewAnimator1 = (ViewAnimator)this.findViewById(R.id.viewFlipper);
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
        Toast.makeText(AboutUs.this, "Search Page", Toast.LENGTH_SHORT).show();
        viewAnimator1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationFactory.flipTransition(viewAnimator1, AnimationFactory.FlipDirection.LEFT_RIGHT);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int ID = item.getItemId();
        switch (ID)
        {
            case R.id.Home:
            {
                Intent intent = new Intent(AboutUs.this, MainActivity.class);
                AboutUs.this.startActivity(intent);
                break;
            }
            case R.id.Login:
            {
                if(QueryRef.getAuth() == null) {
                    Intent myIntent = new Intent(AboutUs.this, LoginActivity.class);
                    AboutUs.this.startActivity(myIntent);
                }
                else
                {
                    CurrentUser.setUserImageURL("");
                    CurrentUser.setUserImageURL("");
                    CurrentUser.setUserEmail("");
                    Logo.setImageResource(R.mipmap.applogo);
                    Usertitle.setText("LiveLarge");
                    LoginItem.setTitle("Login");
                    Intent myIntent = new Intent(AboutUs.this, LoginActivity.class);
                    AboutUs.this.startActivity(myIntent);
                }
                break;
            }
            case R.id.Search:
            {
                Intent myIntent = new Intent(AboutUs.this, SearchActivity.class);
                AboutUs.this.startActivity(myIntent);
                break;
            }
            case R.id.SubmitAd:
            {
                Intent myIntent = new Intent(AboutUs.this, PostListing.class);
                AboutUs.this.startActivity(myIntent);
                break;
            }
            case R.id.AboutUs:
            {
                Intent myIntent = new Intent(AboutUs.this, AboutUs.class);
                AboutUs.this.startActivity(myIntent);
                break;
            }
            default:break;
        }
        MyDrawLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
