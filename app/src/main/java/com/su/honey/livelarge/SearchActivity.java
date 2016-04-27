package com.su.honey.livelarge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity{

    SearchParams SearchParameters;
    Toolbar MyToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoAB);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_searchpage);
        Toast.makeText(SearchActivity.this, "Search Page", Toast.LENGTH_SHORT).show();
        MyToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(MyToolBar);
        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.search_background);
        int nh = (int) (bitmapImage.getHeight() * (1080.0 / bitmapImage.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 1080, nh, true);
        ImageView HomeImage = (ImageView) findViewById(R.id.search_image);
        HomeImage.setImageBitmap(scaled);
        HomeImage.setAlpha(0.6f);
        Button SearchButton = (Button)findViewById(R.id.search_button);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String PropertyType = "Apartment";
                String Furnished = "Any";
                int Bedrooms = 1;
                int MinArea = -1;
                int MaxArea = -1;
                int MinBudget = -1;
                int MaxBudget = -1;
                Boolean Rent = true;
                String Locality = "";
                Spinner PropertyTypeSpinner = (Spinner)findViewById(R.id.prop_type_spinner);
                Spinner BedroomSpinner = (Spinner)findViewById(R.id.bedrooms_spinner);
                EditText MinAreaText = (EditText)findViewById(R.id.text_min_area);
                EditText MaxAreaText = (EditText)findViewById(R.id.text_max_area);
                RadioGroup BuyRent = (RadioGroup)findViewById(R.id.radio_buy_rent);
                EditText MinBudText = (EditText)findViewById(R.id.text_min_budget);
                EditText MaxBudText = (EditText)findViewById(R.id.text_max_budget);
                android.widget.SearchView LocalitySearch = (android.widget.SearchView) findViewById(R.id.searchView);
                if(LocalitySearch!=null)
                    Locality = LocalitySearch.getQuery().toString();
                if(PropertyTypeSpinner!=null)
                    PropertyType = PropertyTypeSpinner.getSelectedItem().toString();
                if(BedroomSpinner!=null)
                {
                    Bedrooms = Integer.parseInt(BedroomSpinner.getSelectedItem().toString().substring(0, 1));
                    if(Bedrooms == 4 && BedroomSpinner.getSelectedItemPosition() == 4)
                        Bedrooms = 5;
                }
                if(MinAreaText!=null)
                    MinArea = Integer.parseInt(MinAreaText.getText().toString());
                if(MaxAreaText!=null)
                    MaxArea = Integer.parseInt(MaxAreaText.getText().toString());
                if(MinBudText!=null)
                    MinBudget = Integer.parseInt(MinBudText.getText().toString());
                if(MaxBudText!=null)
                    MaxBudget = Integer.parseInt(MaxBudText.getText().toString());
                if(BuyRent != null)
                {
                    RadioButton SelectedRB = (RadioButton)findViewById(BuyRent.getCheckedRadioButtonId());
                    if(SelectedRB.getText().equals("BUY"))
                        Rent = false;
                }
                SearchParameters = new SearchParams(PropertyType,Furnished,Bedrooms,MinArea,MaxArea,MinBudget,MaxBudget,Rent, Locality);
                Intent Results = new Intent(SearchActivity.this, FBRecyclerViewActivity.class);
                Results.putExtra("searchobject", SearchParameters);
                Results.putExtra("from", "SearchActivity");
                SearchActivity.this.startActivity(Results);
            }
        });
    }
}
