package com.android.systemui.navigationbar.layout;

import android.R;
import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
            return this.context.getResources().getDimensionPixelSize(R.dimen.resolver_empty_state_container_padding_top);
        }
        boolean isGestureMode = ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode();
        int i2 = this.b5CutoutHeight;
        if (isGestureMode || i == 0 || i == 2) {
            return i2;
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetHeight(boolean z, int i) {
        if (!BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.resolver_max_collapsed_height);
        }
        boolean isGestureMode = ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode();
        int i2 = this.b5CutoutHeight;
        if (isGestureMode) {
            return i == 0 ? i2 : this.context.getResources().getDimensionPixelSize(R.dimen.resolver_max_collapsed_height);
        }
        if (i == 0 || i == 2) {
            return i2;
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetWidth(boolean z, int i) {
        NavBarStateManager navBarStateManager = this.navBarStateManager;
        if (!((NavBarStateManagerImpl) navBarStateManager).supportLargeCoverScreenNavBar() || ((NavBarStateManagerImpl) navBarStateManager).isGestureMode() || i == 0 || i == 2) {
            return -1;
        }
        return this.b5CutoutHeight;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarWidth(boolean z, int i) {
        if (!BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode() || i == 0 || i == 2) {
            return -1;
        }
        return this.b5CutoutHeight;
    }
}
