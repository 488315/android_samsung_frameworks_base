package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.view.DisplayCutout;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IndicatorGardenInputProperties {
    public final Context context;
    public int cornerPaddingC;
    public int cutoutInnerPaddingD;
    public int cutoutSidePaddingD;
    public int cutoutTopMarginB;
    public final boolean debugMode = DeviceType.isEngOrUTBinary();
    public int defaultCenterPadding;
    public int defaultStartPadding;
    public float density;
    public DisplayCutout displayCutout;
    public int rotation;
    public int statusBarWidth;

    public IndicatorGardenInputProperties(Context context) {
        this.context = context;
        updateWindowMetrics();
        updatePaddingValues();
        this.rotation = -1;
    }

    public final int getDimenSize(int i) {
        Resources resources = this.context.getResources();
        if (resources != null) {
            return resources.getDimensionPixelSize(i);
        }
        return 0;
    }

    public final boolean isRTL() {
        return MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(this.context) == 1;
    }

    public final void updatePaddingValues() {
        this.defaultCenterPadding = (int) (getDimenSize(R.dimen.notification_icon_view_width) * 0.25f);
        this.defaultStartPadding = getDimenSize(R.dimen.status_bar_padding_start);
        this.cornerPaddingC = getDimenSize(17106182);
        this.cutoutInnerPaddingD = getDimenSize(17106172);
        this.cutoutSidePaddingD = getDimenSize(17106173);
        this.cutoutTopMarginB = getDimenSize(17106174);
    }

    public final void updateWindowMetrics() {
        int i;
        Context context = this.context;
        this.statusBarWidth = context.getResources().getConfiguration().windowConfiguration.getAppBounds().width();
        this.density = context.getResources().getDisplayMetrics().density;
        int rotation = context.getResources().getConfiguration().windowConfiguration.getRotation();
        if (this.debugMode && (i = this.rotation) != rotation) {
            RecyclerView$$ExternalSyntheticOutline0.m46m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("updateWindowMetrics rotation: ", i, " >> ", rotation, " w="), this.statusBarWidth, "IndicatorGardenInputProperties");
        }
        this.rotation = rotation;
    }
}
