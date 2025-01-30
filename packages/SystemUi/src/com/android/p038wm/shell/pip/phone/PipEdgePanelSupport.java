package com.android.p038wm.shell.pip.phone;

import android.content.Context;
import android.os.SemSystemProperties;
import android.provider.Settings;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.WindowManager;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipEdgePanelSupport {
    public final Context mContext;
    public final SparseIntArray mDisplayWidthPerDpi = new SparseIntArray();
    public final SparseIntArray mDisplayHeightPerDpi = new SparseIntArray();

    public PipEdgePanelSupport(Context context) {
        this.mContext = context;
    }

    public final int getEdgeHandlePixelSize() {
        Context context = this.mContext;
        if (context.getResources().getConfiguration().orientation == 2) {
            MultiWindowUtils.isInSubDisplay(context);
            String str = SemSystemProperties.get("ro.build.characteristics");
            if (!(str != null && str.contains("tablet"))) {
                return 0;
            }
        }
        return percentToPixel(Settings.Secure.getFloatForUser(context.getContentResolver(), "edge_handle_size_percent", 0.0f, -2));
    }

    public final int getUpperMostPosition() {
        TypedValue typedValue = new TypedValue();
        Context context = this.mContext;
        return context.getResources().getDimensionPixelSize(R.dimen.settings_handle_bottom_margin) + SystemBarUtils.getStatusBarHeight(context) + (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true) ? 0 + TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics()) : 0);
    }

    public final int percentToPixel(float f) {
        Context context = this.mContext;
        SparseIntArray sparseIntArray = context.getResources().getConfiguration().orientation == 2 ? this.mDisplayWidthPerDpi : this.mDisplayHeightPerDpi;
        int i = sparseIntArray.get(context.getResources().getConfiguration().densityDpi);
        if (i == 0) {
            i = ((WindowManager) context.getSystemService("window")).getMaximumWindowMetrics().getBounds().height();
            sparseIntArray.put(context.getResources().getConfiguration().densityDpi, i);
        }
        return (int) (((f * ((i - (context.getResources().getBoolean(android.R.bool.config_predictShowStartingSurface) ? context.getResources().getDimensionPixelSize(android.R.dimen.notification_custom_view_max_image_height_low_ram) : 0)) - getUpperMostPosition())) / 100.0f) + 0.5f);
    }
}
