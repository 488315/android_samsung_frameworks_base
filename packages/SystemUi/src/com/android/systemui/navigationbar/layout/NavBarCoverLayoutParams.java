package com.android.systemui.navigationbar.layout;

import android.R;
import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavBarCoverLayoutParams implements BarLayoutParams {
    public final int b5CutoutHeight = 66;
    public final Context context;
    public final NavBarStateManager navBarStateManager;

    public NavBarCoverLayoutParams(Context context, NavBarStateManager navBarStateManager) {
        this.context = context;
        this.navBarStateManager = navBarStateManager;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarGravity(boolean z, int i) {
        NavBarStateManager navBarStateManager = this.navBarStateManager;
        if (!navBarStateManager.supportLargeCoverScreenNavBar() || navBarStateManager.isGestureMode()) {
            return 80;
        }
        if (i == 1) {
            return 5;
        }
        if (i != 2) {
            return i != 3 ? 80 : 3;
        }
        return 48;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarHeight(boolean z, int i) {
        if (!BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end);
        }
        boolean isGestureMode = this.navBarStateManager.isGestureMode();
        int i2 = this.b5CutoutHeight;
        if (isGestureMode || i == 0 || i == 2) {
            return i2;
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetHeight(boolean z, int i) {
        boolean z2 = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
        Context context = this.context;
        if (!z2) {
            return context.getResources().getDimensionPixelSize(R.dimen.notification_custom_view_max_image_height_low_ram);
        }
        boolean isGestureMode = this.navBarStateManager.isGestureMode();
        int i2 = this.b5CutoutHeight;
        if (isGestureMode) {
            return i == 0 ? i2 : context.getResources().getDimensionPixelSize(R.dimen.notification_custom_view_max_image_height_low_ram);
        }
        if (i == 0 || i == 2) {
            return i2;
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetWidth(boolean z, int i) {
        NavBarStateManager navBarStateManager = this.navBarStateManager;
        if (!navBarStateManager.supportLargeCoverScreenNavBar() || navBarStateManager.isGestureMode() || i == 0 || i == 2) {
            return -1;
        }
        return this.b5CutoutHeight;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarWidth(boolean z, int i) {
        if (!BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || this.navBarStateManager.isGestureMode() || i == 0 || i == 2) {
            return -1;
        }
        return this.b5CutoutHeight;
    }
}
