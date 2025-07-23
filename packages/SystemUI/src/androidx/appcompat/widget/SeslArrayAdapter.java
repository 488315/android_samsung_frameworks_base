package androidx.appcompat.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SeslArrayAdapter extends ArrayAdapter {
    public int mInitPaddingBottom;
    public int mInitPaddingTop;

    public SeslArrayAdapter(Context context, int i) {
        super(context, i);
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        View dropDownView = super.getDropDownView(i, view, viewGroup);
        if (view == null) {
            this.mInitPaddingTop = dropDownView.getPaddingTop();
            this.mInitPaddingBottom = dropDownView.getPaddingBottom();
        }
        int dimensionPixelSize = dropDownView.getResources().getDimensionPixelSize(R.dimen.sesl_popup_menu_first_last_item_vertical_edge_padding);
        int i2 = this.mInitPaddingTop + dimensionPixelSize;
        int i3 = this.mInitPaddingBottom + dimensionPixelSize;
        int paddingLeft = dropDownView.getPaddingLeft();
        if (i != 0) {
            i2 = this.mInitPaddingTop;
        }
        int paddingRight = dropDownView.getPaddingRight();
        if (i != getCount() - 1) {
            i3 = this.mInitPaddingBottom;
        }
        dropDownView.setPadding(paddingLeft, i2, paddingRight, i3);
        return dropDownView;
    }
}
