package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.os.SemSystemProperties;
import android.provider.Settings;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.WindowManager;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipEdgePanelSupport {
    public final Context mContext;
    public final SparseIntArray mDisplayWidthPerDpi = new SparseIntArray();
    public final SparseIntArray mDisplayHeightPerDpi = new SparseIntArray();

    public PipEdgePanelSupport(Context context) {
        this.mContext = context;
    }

    public final int getEdgeHandlePixelSize() {
        if (this.mContext.getResources().getConfiguration().orientation == 2) {
            MultiWindowUtils.isInSubDisplay(this.mContext);
            String str = SemSystemProperties.get("ro.build.characteristics");
            if (str == null || !str.contains("tablet")) {
                return 0;
            }
        }
        return percentToPixel(Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), "edge_handle_size_percent", 0.0f, -2));
    }

    public final int getUpperMostPosition() {
        TypedValue typedValue = new TypedValue();
        return StrongAuthPopup$$ExternalSyntheticOutline0.m(this.mContext, R.dimen.settings_handle_bottom_margin, SystemBarUtils.getStatusBarHeight(this.mContext) + (this.mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true) ? TypedValue.complexToDimensionPixelSize(typedValue.data, this.mContext.getResources().getDisplayMetrics()) : 0));
    }

    public final int percentToPixel(float f) {
        SparseIntArray sparseIntArray = this.mContext.getResources().getConfiguration().orientation == 2 ? this.mDisplayWidthPerDpi : this.mDisplayHeightPerDpi;
        int i = sparseIntArray.get(this.mContext.getResources().getConfiguration().densityDpi);
        if (i == 0) {
            i = ((WindowManager) this.mContext.getSystemService("window")).getMaximumWindowMetrics().getBounds().height();
            sparseIntArray.put(this.mContext.getResources().getConfiguration().densityDpi, i);
        }
        return (int) (((f * ((i - (this.mContext.getResources().getBoolean(android.R.bool.config_swipeDisambiguation) ? this.mContext.getResources().getDimensionPixelSize(android.R.dimen.seekbar_track_progress_height_material) : 0)) - getUpperMostPosition())) / 100.0f) + 0.5f);
    }
}
