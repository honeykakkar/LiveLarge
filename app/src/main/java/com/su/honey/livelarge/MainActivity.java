package com.su.honey.livelarge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar MyToolBar;
    private NavigationView MyNavView;
    private DrawerLayout MyDrawLayout;
    private Firebase QueryRef;
    static CircleImageView Logo;
    private static TextView Usertitle;
    private static MenuItem LoginItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.AppThemeNoAB);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        Toast.makeText(MainActivity.this, "Welcome Page", Toast.LENGTH_SHORT).show();
        MyToolBar = (Toolbar)findViewById(R.id.nav_toolbar);
        setSupportActionBar(MyToolBar);
        QueryRef = new Firebase("https://livelarge.firebaseio.com/");
        MyNavView = (NavigationView)findViewById(R.id.NavigationView);
        MyNavView.setNavigationItemSelectedListener(this);
        MyDrawLayout = (DrawerLayout)findViewById(R.id.NavigationDrawer);

        if(QueryRef.getAuth() != null && CurrentUser.getUserName() != null) {
            View HeaderView = MyNavView.getHeaderView(0);
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
        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.homepage);
        int nh = (int) ( bitmapImage.getHeight() * (1440.0 / bitmapImage.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 1440, nh, true);
        ImageView HomeImage = (ImageView)findViewById(R.id.home_image);
        HomeImage.setImageBitmap(scaled);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int ID = item.getItemId();
        switch (ID)
        {
            case R.id.Home:
            {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            }
            case R.id.Login:
            {
                if(QueryRef.getAuth() == null && CurrentUser.getUserName() == null) {
                    Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(myIntent);
                }
                else
                {
                    CurrentUser.setUserImageURL("");
                    CurrentUser.setUserImageURL("");
                    CurrentUser.setUserEmail("");
                    View HeaderView = MyNavView.getHeaderView(0);
                    CircleImageView Logo = (CircleImageView) HeaderView.findViewById(R.id.navheader_image);
                    Usertitle = (TextView) HeaderView.findViewById(R.id.navheader_label);
                    Logo.setImageResource(R.mipmap.applogo);
                    Usertitle.setText("LiveLarge");
                    LoginItem = MyNavView.getMenu().getItem(1);
                    LoginItem.setTitle("Login");
                    Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(myIntent);
                    QueryRef.unauth();
                }
                break;
            }
            case R.id.Search:
            {
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
                MainActivity.this.startActivity(myIntent);
                break;
            }
            case R.id.SubmitAd:
            {

                if(QueryRef.getAuth() != null && CurrentUser.getUserName() != null) {
                    Intent myIntent = new Intent(MainActivity.this, PostListing.class);
                    MainActivity.this.startActivity(myIntent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Submit ad needs user to be logged in", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.AboutUs:
            {
                Intent myIntent = new Intent(MainActivity.this, AboutUs.class);
                MainActivity.this.startActivity(myIntent);
                break;
            }
            default:break;
        }
        MyDrawLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
