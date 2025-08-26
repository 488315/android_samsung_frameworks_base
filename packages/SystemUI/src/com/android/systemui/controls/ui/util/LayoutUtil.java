package com.android.systemui.controls.ui.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class LayoutUtil {
    public final Context context;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        return this.context.getResources().getConfiguration().screenWidthDp >= 960 ? 840.0f / this.context.getResources().getConfiguration().screenWidthDp : f;
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
