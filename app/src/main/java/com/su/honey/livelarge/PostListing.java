package com.su.honey.livelarge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.URIResolver;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostListing extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActionBar MyActionBar;
    Toolbar MyToolBar;
    Spinner States;
    Spinner Cities;
    NavigationView MyNavView;
    DrawerLayout MyDrawLayout;
    Firebase QueryRef;
    Firebase QueryRefLogin;
    static CircleImageView Logo;
    static TextView Usertitle;
    static MenuItem LoginItem;
    public static final int CAM_REQUEST_CODE = 10;
    public static final int GAL_REQUEST_CODE = 20;
    ImageView uploadimagecam, uploadimagegal, images, uploadImage;
    Bitmap camImage;
    Uri galImage;
    String encodedImageString;
    String street, apt, city, state, proptype, area, bedrooms, desc, amenities, furn, avail, price, buy_rent;
    EditText et_street, et_apt, et_city, et_state, et_proptype, et_area, et_bedrooms, et_desc, et_amenities, et_furn, et_avail, et_price;
    RadioButton rb_buy_rent;
    RadioGroup rg_buy_rent;
    Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        QueryRef = new Firebase("https://livelarge.firebaseio.com/Listings");
        QueryRefLogin = new Firebase("https://livelarge.firebaseio.com");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_listing);
        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.upload);
        int nh = (int) (bitmapImage.getHeight() * (1080.0 / bitmapImage.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 1080, nh, true);
        ImageView HomeImage = (ImageView)findViewById(R.id.upload_image);
        HomeImage.setImageBitmap(scaled);
        HomeImage.setAlpha(0.6f);
        MyToolBar = (Toolbar)findViewById(R.id.action_toolbar);
        setSupportActionBar(MyToolBar);
        MyActionBar = getSupportActionBar();
        if(MyActionBar!=null)
            MyActionBar.setDisplayHomeAsUpEnabled(true);
        MyNavView = (NavigationView)findViewById(R.id.NavigationView);
        MyNavView.setNavigationItemSelectedListener(this);
        MyDrawLayout = (DrawerLayout)findViewById(R.id.NavigationDrawer);

        if(CurrentUser.getUserName() != null && QueryRefLogin.getAuth() != null) {
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

        uploadImage = (ImageView)findViewById(R.id.uploadimage);
        uploadImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PopupMenu popup = new PopupMenu(PostListing.this, uploadImage);
                popup.getMenuInflater()
                        .inflate(R.menu.popupmenu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item){
                        switch (item.getItemId()){
                            case R.id.Camera:Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(camIntent, CAM_REQUEST_CODE);
                                return true;

                            case R.id.Gallery: Intent galIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galIntent, GAL_REQUEST_CODE);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        images = (ImageView)findViewById(R.id.images);
        rg_buy_rent = (RadioGroup)findViewById(R.id.buy_rent);
        et_street = (EditText)findViewById(R.id.addr_line1);
        et_apt = (EditText)findViewById(R.id.apt_num);
        et_city = (EditText)findViewById(R.id.city);
        et_state = (EditText)findViewById(R.id.state);
        et_proptype = (EditText)findViewById(R.id.propertytype);
        et_area = (EditText)findViewById(R.id.area);
        et_bedrooms = (EditText)findViewById(R.id.prop_bedrooms);
        et_desc = (EditText)findViewById(R.id.prop_desc);
        et_amenities = (EditText)findViewById(R.id.prop_amenities);
        et_furn = (EditText)findViewById(R.id.prop_furn);
        et_avail = (EditText)findViewById(R.id.prop_avail);
        et_price = (EditText)findViewById(R.id.prop_price);

        uploadButton = (Button)findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                uploadData();
            }
        });

    }

    private void uploadData(){

        Serializable_PropData Prop_Data = new Serializable_PropData();
        Boolean flag_street = false, flag_city = false, flag_state = false, flag_price = false, flag_listtype = false;
        if(rg_buy_rent != null)
        {
            rb_buy_rent = (RadioButton)findViewById(rg_buy_rent.getCheckedRadioButtonId());
        }
        if(rb_buy_rent.getText().equals("BUY")){
            buy_rent = "BUY";
            flag_listtype = true;
        }
        if(rb_buy_rent.getText().equals("RENT")){
            buy_rent = "RENT";
            flag_listtype = true;
        }

        street = et_street.getText().toString();
        apt = et_apt.getText().toString();
        city = et_city.getText().toString();
        state = et_state.getText().toString();
        proptype = et_proptype.getText().toString();
        area = et_area.getText().toString();
        bedrooms = et_bedrooms.getText().toString();
        desc = et_desc.getText().toString();
        amenities = et_amenities.getText().toString();
        furn = et_furn.getText().toString();
        avail = et_avail.getText().toString();
        price = et_price.getText().toString();

        Prop_Data.setProp_type(proptype);
        Prop_Data.setProp_area(area);
        Prop_Data.setProp_bed(bedrooms);
        Prop_Data.setProp_description(desc);
        Prop_Data.setProp_amenities(amenities);
        Prop_Data.setProp_furnished(furn);
        Prop_Data.setProp_AvailDate(avail);
        Prop_Data.setList_type(buy_rent);
        Prop_Data.setImageURLs(encodedImageString);
        if(street != ""){
            String s = "";
            if (apt != "")
            {
                s = ", Apt #"+apt;
            }
            else if (apt == ""){
                s = "";
            }
            Prop_Data.setProp_address(street+s);
            flag_street = true;
        }
        if(city != ""){
            Prop_Data.setProp_city(city);
            flag_city = true;
        }
        if(state != ""){
            Prop_Data.setProp_state(state);
            flag_state = true;
        }
        if(price != ""){
            Prop_Data.setProp_price(price);
            flag_price = true;
        }

        if(flag_city && flag_price && flag_state && flag_street && flag_listtype){
            QueryRef.child(city+"_"+street).setValue(Prop_Data);
            Intent i = new Intent(PostListing.this, ListingDetails.class);
            i.putExtra("Prop_Data", Prop_Data);
            startActivity(i);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data){
        super.onActivityResult(requestCode, resultcode, data);

        if(resultcode == RESULT_OK && data !=null){
            if (requestCode == CAM_REQUEST_CODE){
                camImage = (Bitmap)data.getExtras().get("data");
                images.setImageBitmap(camImage);
                bitMapToBase64(camImage);
            }
            if (requestCode == GAL_REQUEST_CODE){
                galImage = data.getData();
                images.setImageURI(galImage);
                Bitmap bmp = ((BitmapDrawable) images.getDrawable()).getBitmap();
                bitMapToBase64(bmp);
            }
        }

    }

    private void bitMapToBase64(Bitmap img){
        if (img != null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            encodedImageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int ID = item.getItemId();
        switch (ID)
        {
            case R.id.Home:
            {
                Intent intent = new Intent(PostListing.this, MainActivity.class);
                PostListing.this.startActivity(intent);
                break;
            }
            case R.id.Login:
            {
                if(QueryRefLogin.getAuth() == null) {
                    Intent myIntent = new Intent(PostListing.this, LoginActivity.class);
                    PostListing.this.startActivity(myIntent);
                }
                else
                {
                    View HeaderView = (View) MyNavView.getHeaderView(0);
                    CircleImageView Logo = (CircleImageView) HeaderView.findViewById(R.id.navheader_image);
                    Usertitle = (TextView) HeaderView.findViewById(R.id.navheader_label);
                    LoginItem = MyNavView.getMenu().getItem(1);
                    CurrentUser.setUserImageURL("");
                    CurrentUser.setUserImageURL("");
                    CurrentUser.setUserEmail("");
                    Logo.setImageResource(R.mipmap.applogo);
                    Usertitle.setText("LiveLarge");
                    LoginItem.setTitle("Login");
                    QueryRefLogin.unauth();
                    Intent myIntent = new Intent(PostListing.this, LoginActivity.class);
                    PostListing.this.startActivity(myIntent);
                }
                break;
            }
            case R.id.Search:
            {
                Intent myIntent = new Intent(PostListing.this, SearchActivity.class);
                PostListing.this.startActivity(myIntent);
                break;
            }
            case R.id.SubmitAd:
            {
                if(QueryRefLogin.getAuth() != null && CurrentUser.getUserName() != null) {
                    Intent myIntent = new Intent(PostListing.this, PostListing.class);
                    PostListing.this.startActivity(myIntent);
                }
                else
                {
                    Toast.makeText(PostListing.this, "Submit ad needs user to be logged in", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.AboutUs:
            {
                Intent myIntent = new Intent(PostListing.this, AboutUs.class);
                PostListing.this.startActivity(myIntent);
                break;
            }
            default:break;
        }
        MyDrawLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
