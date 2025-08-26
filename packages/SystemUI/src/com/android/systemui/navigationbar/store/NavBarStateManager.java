package com.android.systemui.navigationbar.store;

/* loaded from: classes2.dex */
public interface NavBarStateManager {
    static boolean isIMEShowing$default(NavBarStateManager navBarStateManager) {
        NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navBarStateManager;
        boolean z = (navBarStateManagerImpl.states.iconHint & 1) != 0;
        navBarStateManagerImpl.logNavBarStates(Boolean.valueOf(z), "isIMEShowing");
        return z;
    }

    static boolean isSideAndBottomGestureMode$default(NavBarStateManager navBarStateManager) {
        return ((NavBarStateManagerImpl) navBarStateManager).states.navigationMode == 2;
    }
}
