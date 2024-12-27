package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.DisplayCutout;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.util.DeviceType;

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
        return MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.context) == 1;
    }

    public final void updatePaddingValues() {
        this.defaultCenterPadding = (int) (getDimenSize(R.dimen.notification_icon_view_width) * 0.25f);
        this.defaultStartPadding = getDimenSize(R.dimen.status_bar_padding_start);
        this.cornerPaddingC = getDimenSize(17106292);
        this.cutoutInnerPaddingD = getDimenSize(17106281);
        this.cutoutSidePaddingD = getDimenSize(17106282);
        this.cutoutTopMarginB = getDimenSize(17106283);
    }

    public final void updateWindowMetrics() {
        int i;
        Rect appBounds = this.context.getResources().getConfiguration().windowConfiguration.getAppBounds();
        this.statusBarWidth = appBounds != null ? appBounds.width() : 0;
        this.density = this.context.getResources().getDisplayMetrics().density;
        int rotation = this.context.getResources().getConfiguration().windowConfiguration.getRotation();
        if (this.debugMode && (i = this.rotation) != rotation) {
            RecyclerView$$ExternalSyntheticOutline0.m(this.statusBarWidth, "IndicatorGardenInputProperties", RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, rotation, "updateWindowMetrics rotation: ", " >> ", " w="));
        }
        this.rotation = rotation;
    }
}
