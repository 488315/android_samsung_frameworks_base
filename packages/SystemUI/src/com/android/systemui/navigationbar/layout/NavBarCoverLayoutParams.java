package com.android.systemui.navigationbar.layout;

import android.R;
import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;

/* loaded from: classes2.dex */
public final class NavBarCoverLayoutParams implements BarLayoutParams {
    public final Context context;
    public final NavBarStateManager navBarStateManager;

    public NavBarCoverLayoutParams(Context context, NavBarStateManager navBarStateManager) {
        this.context = context;
        this.navBarStateManager = navBarStateManager;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarGravity(boolean z, int i) {
        NavBarStateManager navBarStateManager = this.navBarStateManager;
        if (!((NavBarStateManagerImpl) navBarStateManager).supportLargeCoverScreenNavBar() || ((NavBarStateManagerImpl) navBarStateManager).isGestureMode()) {
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
            return this.context.getResources().getDimensionPixelSize(R.dimen.secondary_waterfall_display_right_edge_size);
        }
        if (((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode()) {
            return this.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.large_cover_button_inset_height);
        }
        if (i == 0 || i == 2) {
            return this.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.large_cover_button_height);
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetHeight(boolean z, int i) {
        if (!BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.select_dialog_drawable_padding_start_material);
        }
        if (((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode()) {
            return i == 0 ? this.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.large_cover_button_inset_height) : this.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.large_cover_gesture_height);
        }
        if (i == 0 || i == 2) {
            return this.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.large_cover_button_inset_height);
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetWidth(boolean z, int i) {
        NavBarStateManager navBarStateManager = this.navBarStateManager;
        if (!((NavBarStateManagerImpl) navBarStateManager).supportLargeCoverScreenNavBar() || ((NavBarStateManagerImpl) navBarStateManager).isGestureMode() || i == 0 || i == 2) {
            return -1;
        }
        return this.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.large_cover_button_inset_height);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarWidth(boolean z, int i) {
        if (!BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode() || i == 0 || i == 2) {
            return -1;
        }
        return this.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.large_cover_button_height);
    }
}
