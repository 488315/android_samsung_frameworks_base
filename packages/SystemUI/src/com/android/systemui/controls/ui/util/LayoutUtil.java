package com.android.systemui.controls.ui.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class LayoutUtil {
    public final Context context;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public LayoutUtil(Context context) {
        this.context = context;
    }

    public final int getAvailableSpanCount(int i, int i2) {
        int dimensionPixelSize = i - ((this.context.getResources().getDimensionPixelSize(R.dimen.control_list_horizontal_margin) - this.context.getResources().getDimensionPixelSize(R.dimen.control_base_item_side_margin)) * 2);
        Log.d("LayoutUtil", "getAvailableSpanCount layoutWidth = " + i + ", layoutSize = " + dimensionPixelSize);
        int i3 = dimensionPixelSize / i2;
        if (1 < i3) {
            return i3;
        }
        return 1;
    }

    public final float getWidthPercentBasic(float f) {
        if (this.context.getResources().getConfiguration().screenHeightDp >= 960) {
            return 840.0f / this.context.getResources().getConfiguration().screenHeightDp;
        }
        if (this.context.getResources().getConfiguration().screenHeightDp >= 589) {
            return f;
        }
        return 1.0f;
    }

    public final void setLayoutWeightWidthPercentBasic(float f, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 != null) {
            layoutParams2.width = 0;
            layoutParams2.weight = getWidthPercentBasic(f);
        }
    }
}
