package com.android.systemui.controls.p005ui.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LayoutUtil {
    public final Context context;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        Context context = this.context;
        int dimensionPixelSize = (i - ((context.getResources().getDimensionPixelSize(R.dimen.controls_list_margin_horizontal) - context.getResources().getDimensionPixelSize(R.dimen.control_base_item_side_margin)) * 2)) / i2;
        if (1 < dimensionPixelSize) {
            return dimensionPixelSize;
        }
        return 1;
    }

    public final void setLayoutWeightWidthPercentBasic(View view, float f) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 != null) {
            layoutParams2.width = 0;
            if (this.context.getResources().getConfiguration().screenHeightDp <= 411) {
                f = 1.0f;
            }
            layoutParams2.weight = f;
        }
    }
}
