package com.su.honey.livelarge;

import android.view.View;

import java.util.List;
import java.util.Map;

/**
 * Created by honey on 2/20/2016.
 */
public interface OnClickIListener
{
    public void FragmentSelected(int Section);
    public void StartIntent(SearchParams searchParams);
    public void GetPropDetails(Serializable_PropData SP);
}
