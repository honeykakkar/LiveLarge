package com.su.honey.livelarge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * Created by honey on 3/26/2016.
 */
public class FBRecyclerViewAdapter extends FirebaseRecyclerAdapter<SerializablePropData, FBRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    static EventHandler EHandler;
    SearchParams ASearchObject;

    public FBRecyclerViewAdapter(Class<SerializablePropData> modelClass, int modelLayout,
                                 Class<MyViewHolder> viewHolderClass,
                                 Query ref, Context mcontext, SearchParams searchParams)
    {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = mcontext;
        this.ASearchObject = searchParams;
        Log.d("Honey", "adapter const");
    }

    public void SetEventHandler(EventHandler MyEventHandler)
    {
        this.EHandler = MyEventHandler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        MyViewHolder Current = new MyViewHolder(V);
        Log.d("Honey", "Kakkar");
        return Current;
    }

    @Override
    protected void populateViewHolder(MyViewHolder myViewHolder, SerializablePropData serializablepropdata, int i)
    {
        Log.d("Honey", "Kakkar");
        myViewHolder.PropertyBeds.setText(serializablepropdata.getProp_bed().concat(" BHK"));
        myViewHolder.PropertyName.setText(serializablepropdata.getProp_name());
        String Price = "Price $";
        myViewHolder.PropertyPrice.setText(Price.concat(serializablepropdata.getProp_price()));
        myViewHolder.PropertyType.setText(serializablepropdata.getProp_type());
        //Picasso.with(context).load(serializablepropdata.getImageURLs()).into(myViewHolder.PropertyImage);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView PropertyPrice;
        public ImageView PropertyImage;
        public TextView PropertyName;
        public TextView PropertyType;
        public TextView PropertyBeds;

        public MyViewHolder(final View V) {
            super(V);
            PropertyType = (TextView) V.findViewById(R.id.card_type);
            PropertyBeds = (TextView) V.findViewById(R.id.card_bed);
            //PropertyImage = (ImageView) V.findViewById(R.id.card_image);
            PropertyName = (TextView) V.findViewById(R.id.card_name);
            PropertyPrice = (TextView) V.findViewById(R.id.card_price);
            Log.d("Honey", "Kakkar view const");
        }
    }
}
