package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SecQSDetailContentView extends LinearLayout {
    public boolean hasMinHeight;
    public final Context mContext;
    public SecQSPanelController mQsPanelController;
    public final SecQSPanelResourcePicker mResourcePicker;

    public SecQSDetailContentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.hasMinHeight = false;
        this.mContext = context;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        String str;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = this.mContext;
        secQSPanelResourcePicker.getClass();
        Resources resources = context.getResources();
        float dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.qs_detail_content_maxheight);
        boolean z = QpRune.QUICK_TABLET;
        if (z) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.qs_detail_content_maxheight_tablet);
        }
        int i3 = (int) dimensionPixelSize;
        SecQSPanelResourcePicker secQSPanelResourcePicker2 = this.mResourcePicker;
        Context context2 = this.mContext;
        secQSPanelResourcePicker2.getClass();
        Resources resources2 = context2.getResources();
        float dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.qs_detail_content_minheight);
        if (z) {
            dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.qs_detail_content_minheight_tablet);
        }
        int i4 = (int) dimensionPixelSize2;
        if (z) {
            if (this.hasMinHeight) {
                i3 = i4;
            }
            setMinimumHeight(i3);
        } else {
            SecQSPanelController secQSPanelController = this.mQsPanelController;
            if (secQSPanelController.mDetailRecord == null || (str = secQSPanelController.mDetailTileSpec) == null) {
                str = "";
            }
            if (str.equals("Wifi") || str.equals("Bluetooth")) {
                setMinimumHeight(i3);
            } else {
                setMinimumHeight(i4);
            }
        }
        if (View.MeasureSpec.getSize(i2) > i3) {
            i2 = View.MeasureSpec.makeMeasureSpec(i3, VideoPlayer.MEDIA_ERROR_SYSTEM);
        }
        super.onMeasure(i, i2);
    }
}
