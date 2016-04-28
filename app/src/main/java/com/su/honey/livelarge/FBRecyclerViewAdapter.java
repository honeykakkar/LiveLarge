package com.su.honey.livelarge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * Created by honey on 3/26/2016.
 */
public class FBRecyclerViewAdapter extends FirebaseRecyclerAdapter<Serializable_PropData, FBRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    static EventHandler EHandler;
    SearchParams ASearchObject;
    static View CurrentCardView;
    static ViewGroup Parent;

    public FBRecyclerViewAdapter(Class<Serializable_PropData> modelClass, int modelLayout,
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
        Parent = parent;
        CurrentCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        MyViewHolder Current = new MyViewHolder(CurrentCardView);
        return Current;
    }
    @Override
    protected void populateViewHolder(MyViewHolder myViewHolder, Serializable_PropData serializablepropdata, int i) {
        if (Integer.parseInt(serializablepropdata.getProp_price()) <= ASearchObject.getMaxBudget())
        {
            myViewHolder.PropertyBeds.setText(serializablepropdata.getProp_bed().concat(" BHK"));
            myViewHolder.PropertyName.setText(serializablepropdata.getProp_name());
            String Price = "Price $";
            myViewHolder.PropertyPrice.setText(Price.concat(serializablepropdata.getProp_price()));
            myViewHolder.PropertyType.setText(serializablepropdata.getProp_type());
            //Picasso.with(context).load(serializablepropdata.getImageURLs()).into(myViewHolder.PropertyImage);
        }
        else
        {
            ViewGroup A = (ViewGroup) CurrentCardView.getParent();
            ViewManager Vm = (ViewManager) CurrentCardView.getParent();
            //ViewGroup P = Parent;
            Parent.removeView(CurrentCardView);
            CurrentCardView.setVisibility(View.GONE);
        }
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
        }
    }
}
