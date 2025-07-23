package com.android.systemui.accessibility.hearingaid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class HearingDevicesSpinnerAdapter extends ArrayAdapter {
    public final Context mContext;
    public int mSelectedPosition;

    public HearingDevicesSpinnerAdapter(Context context) {
        super(context, R.layout.hearing_devices_spinner_view, R.id.hearing_devices_spinner_text);
        setDropDownViewResource(R.layout.hearing_devices_spinner_dropdown_view);
        this.mContext = context;
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        View dropDownView = super.getDropDownView(i, view, viewGroup);
        boolean z = i == this.mSelectedPosition;
        dropDownView.setBackgroundResource(z ? R.drawable.hearing_devices_spinner_selected_background : R.drawable.bluetooth_tile_dialog_bg_off);
        View findViewById = dropDownView.findViewById(R.id.hearing_devices_spinner_check_icon);
        if (findViewById != null) {
            findViewById.setVisibility(z ? 0 : 8);
        }
        TextView textView = (TextView) dropDownView.findViewById(R.id.hearing_devices_spinner_text);
        if (textView != null) {
            textView.setTextColor(this.mContext.getColor(z ? android.R.color.resolver_text_color_secondary_dark : android.R.color.search_url_text_material_light));
        }
        return dropDownView;
    }
}
