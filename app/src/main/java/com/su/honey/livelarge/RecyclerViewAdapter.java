package com.su.honey.livelarge;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by honey on 2/20/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements EventHandler{
    OnClickIListener IListener;
    EventHandler EHandler;
    public Context MyContext;
    public List<Serializable_PropData> ResultProps;

    public RecyclerViewAdapter(Context context, List<Serializable_PropData> resultprops) {
        this.MyContext = context;
        ResultProps = resultprops;
        Log.d("Initial",String.valueOf(ResultProps.size()));

    }
    public void SetEventHandler(EventHandler MyEventHandler) {
        this.EHandler = MyEventHandler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View MyView;
        MyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MyViewHolder(MyView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Serializable_PropData serializablepropdata = ResultProps.get(position);
        holder.PropertyBeds.setText(serializablepropdata.getProp_bed().concat(" BHK"));
        holder.PropertyName.setText(serializablepropdata.getProp_name());
        String Price = "Price $";
        holder.PropertyPrice.setText(Price.concat(serializablepropdata.getProp_price()));
        holder.PropertyType.setText(serializablepropdata.getProp_type());
    }

    @Override
    public int getItemCount() {
        return ResultProps.size();
    }

    @Override
    public void GetDetails(Serializable_PropData SP) {

        IListener = (OnClickIListener)this.MyContext;
        IListener.GetPropDetails(SP);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
            V.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Serializable_PropData Current = ResultProps.get(getAdapterPosition());
                    EHandler.GetDetails(Current);
                }
            });
        }
    }
}