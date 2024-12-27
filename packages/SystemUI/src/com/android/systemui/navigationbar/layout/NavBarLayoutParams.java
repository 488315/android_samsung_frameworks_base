package com.android.systemui.navigationbar.layout;

import android.R;
import android.content.Context;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;

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
        if (!z) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.resolver_empty_state_container_padding_top);
        }
        if (i == -1 || i == 0 || i == 2) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.resolver_empty_state_container_padding_top);
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetHeight(boolean z, int i) {
        if (((NavBarStateManagerImpl) this.navBarStateManager).shouldShowSUWStyle()) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.resolver_empty_state_container_padding_top);
        }
        if (!z) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.resolver_max_collapsed_height);
        }
        if (i == -1 || i == 0 || i == 2) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.resolver_max_collapsed_height);
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetWidth(boolean z, int i) {
        if ((!z) || i == -1 || i == 0 || i == 2) {
            return -1;
        }
        return this.context.getResources().getDimensionPixelSize(R.dimen.resolver_profile_tab_margin);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarWidth(boolean z, int i) {
        if (!z) {
            return -1;
        }
        if (i == 1 || i == 3) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.resolver_empty_state_container_padding_top);
        }
        return -1;
    }
}
