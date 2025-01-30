package com.android.systemui.qs;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.util.SecPanelOpaqueBgHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSContainerOpaqueBgHelper extends SecPanelOpaqueBgHelper {
    public View mBackground;
    public GradientDrawable mBackgroundDrawable;
    public int mBackgroundDrawableColor;
    public int mBottomRadius;
    public final Context mContext;
    public final Runnable mUpdateClippingPathRunnable;

    public SecQSContainerOpaqueBgHelper(Context context, Runnable runnable) {
        this.mContext = context;
        this.mUpdateClippingPathRunnable = runnable;
    }

    public final void updateBackgroundResources() {
        Context context = this.mContext;
        this.mBottomRadius = context.getResources().getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_start);
        int i = context.getResources().getConfiguration().orientation;
        context.getResources().getFloat(R.dimen.qs_panel_height_ratio_tablet);
        context.getResources().getDimensionPixelSize(R.dimen.sec_style_qs_header_status_bar_height);
        int color = context.getColor(R.color.sec_panel_background_color_tablet);
        this.mBackgroundDrawableColor = color;
        GradientDrawable gradientDrawable = this.mBackgroundDrawable;
        if (gradientDrawable == null || this.mBackground == null) {
            return;
        }
        gradientDrawable.setColor(color);
        this.mBackground.setBackground(this.mBackgroundDrawable);
    }

    public final void updateBackgroundRound(float f, float[] fArr, boolean z) {
        float f2 = 1.0f;
        if (!z && ((StatusBarStateController) Dependency.get(StatusBarStateController.class)).getState() != 1 && ((NotificationsController) Dependency.get(NotificationsController.class)).getActiveNotificationsCount() > 0) {
            f2 = Math.min(1.0f, Math.max(0.0f, (f - 0.5f) / Math.max(0.0f, 0.15f)));
        }
        GradientDrawable gradientDrawable = this.mBackgroundDrawable;
        if (gradientDrawable == null) {
            GradientDrawable gradientDrawable2 = new GradientDrawable();
            this.mBackgroundDrawable = gradientDrawable2;
            if (this.mBackground != null) {
                gradientDrawable2.setColor(this.mBackgroundDrawableColor);
                this.mBackground.setBackground(this.mBackgroundDrawable);
                return;
            }
            return;
        }
        float f3 = this.mBottomRadius;
        float f4 = f2 * f3;
        gradientDrawable.setCornerRadii(new float[]{f3, f3, f3, f3, f4, f4, f4, f4});
        for (int i = 0; i < 8; i++) {
            fArr[i] = f4;
        }
        this.mUpdateClippingPathRunnable.run();
    }
}
