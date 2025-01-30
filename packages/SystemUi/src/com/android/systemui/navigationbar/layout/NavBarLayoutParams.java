package com.android.systemui.navigationbar.layout;

import android.R;
import android.content.Context;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavBarLayoutParams implements BarLayoutParams {
    public final Context context;
    public final NavBarStateManager navBarStateManager;

    public NavBarLayoutParams(Context context, NavBarStateManager navBarStateManager) {
        this.context = context;
        this.navBarStateManager = navBarStateManager;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarGravity(boolean z, int i) {
        if (!z) {
            return 80;
        }
        if (i != 1) {
            return i != 3 ? 80 : 3;
        }
        return 5;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarHeight(boolean z, int i) {
        boolean z2 = !z;
        Context context = this.context;
        if (z2) {
            return context.getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end);
        }
        if (i == -1 || i == 0 || i == 2) {
            return context.getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end);
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetHeight(boolean z, int i) {
        boolean shouldShowSUWStyle = this.navBarStateManager.shouldShowSUWStyle();
        Context context = this.context;
        if (shouldShowSUWStyle) {
            return context.getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end);
        }
        if (!z) {
            return context.getResources().getDimensionPixelSize(R.dimen.notification_custom_view_max_image_height_low_ram);
        }
        if (i == -1 || i == 0 || i == 2) {
            return context.getResources().getDimensionPixelSize(R.dimen.notification_custom_view_max_image_height_low_ram);
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetWidth(boolean z, int i) {
        if ((!z) || i == -1 || i == 0 || i == 2) {
            return -1;
        }
        return this.context.getResources().getDimensionPixelSize(R.dimen.notification_feedback_size);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarWidth(boolean z, int i) {
        if (!z) {
            return -1;
        }
        if (i == 1 || i == 3) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end);
        }
        return -1;
    }
}
