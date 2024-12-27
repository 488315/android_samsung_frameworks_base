package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.qs.SecQSPanelControllerBase;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SecQSDetailContentView extends LinearLayout {
    public final Context mContext;
    public SecQSPanelControllerBase.TileRecord mRecord;
    public final SecQSPanelResourcePicker mResourcePicker;

    public SecQSDetailContentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int detailContentViewMaxHeight = this.mResourcePicker.resourcePickHelper.getTargetPicker().getDetailContentViewMaxHeight(this.mContext);
        int detailContentViewMinHeight = this.mResourcePicker.resourcePickHelper.getTargetPicker().getDetailContentViewMinHeight(this.mContext);
        SecQSPanelControllerBase.TileRecord tileRecord = this.mRecord;
        String tileSpec = tileRecord != null ? tileRecord.tile.getTileSpec() : "";
        if (tileSpec.equals("Wifi") || tileSpec.equals("Bluetooth")) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(detailContentViewMaxHeight, 1073741824));
            return;
        }
        setMinimumHeight(detailContentViewMinHeight);
        if (View.MeasureSpec.getSize(i2) > detailContentViewMaxHeight) {
            i2 = View.MeasureSpec.makeMeasureSpec(detailContentViewMaxHeight, Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
    }
}
