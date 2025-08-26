package com.android.systemui.shade;

import android.content.Context;
import android.graphics.Rect;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.util.DeviceState;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class LargeScreenHeaderHelper {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final SecQSPanelResourcePicker qsPanelResourcePicker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public LargeScreenHeaderHelper(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        this.context = context;
        this.qsPanelResourcePicker = secQSPanelResourcePicker;
    }

    public final int getLargeScreenHeaderHeight() {
        if (DeviceState.isShowingPopOverStatusBar(this.context)) {
            return SystemBarUtils.getStatusBarHeight(this.context);
        }
        return this.qsPanelResourcePicker.resourcePickHelper.getTargetPicker().getShadeHeaderHeight(this.context);
    }

    public final int getTopMargin(WindowInsets windowInsets) {
        Rect boundingRectTop;
        int iIntValue = 0;
        if (DeviceState.isShowingPopOverStatusBar(this.context)) {
            return 0;
        }
        if (windowInsets != null) {
            IndicatorCutoutUtil.Companion.getClass();
            DisplayCutout displayCutout = IndicatorCutoutUtil.Companion.getHidWindowInsetsFromUDC(windowInsets).getDisplayCutout();
            Integer numValueOf = (displayCutout == null || (boundingRectTop = displayCutout.getBoundingRectTop()) == null) ? null : Integer.valueOf(boundingRectTop.bottom);
            if (numValueOf != null) {
                iIntValue = numValueOf.intValue();
            }
        }
        return iIntValue == 0 ? this.context.getResources().getDimensionPixelSize(R.dimen.shade_header_no_cutout_top_margin) : iIntValue;
    }

    public static final int getLargeScreenHeaderHeight(Context context) {
        Companion.getClass();
        return Math.max(context.getResources().getDimensionPixelSize(R.dimen.large_screen_shade_header_height), SystemBarUtils.getStatusBarHeight(context));
    }
}
