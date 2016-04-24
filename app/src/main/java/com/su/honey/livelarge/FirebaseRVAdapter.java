package com.su.honey.livelarge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by honey on 3/26/2016.
 */
public class FirebaseRVAdapter extends FirebaseRecyclerAdapter<SerializablePropData, FirebaseRVAdapter.MyViewHolder> {

    private Context context;
    static EventHandler EHandler;

    public FirebaseRVAdapter(Class<SerializablePropData> modelClass, int modelLayout,
                             Class<MyViewHolder> viewHolderClass,
                             Query ref, Context mcontext)
    {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = mcontext;
    }

    public void SetEventHandler(EventHandler MyEventHandler)
    {
        this.EHandler = MyEventHandler;
    }

    @Override
    protected void populateViewHolder(MyViewHolder myViewHolder, SerializablePropData serializablepropdata, int i)
    {
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

        public MyViewHolder(final View V)
        {
            super(V);
            PropertyType = (TextView) V.findViewById(R.id.card_type);
            PropertyBeds = (TextView) V.findViewById(R.id.card_bed);
            //PropertyImage = (ImageView) V.findViewById(R.id.card_image);
            PropertyName = (TextView) V.findViewById(R.id.card_name);
            PropertyPrice = (TextView) V.findViewById(R.id.card_price);
        }
    }
}
