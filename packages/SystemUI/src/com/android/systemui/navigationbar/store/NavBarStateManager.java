package com.android.systemui.navigationbar.store;

public interface NavBarStateManager {
    static boolean isSideAndBottomGestureMode$default(NavBarStateManager navBarStateManager) {
        return ((NavBarStateManagerImpl) navBarStateManager).states.navigationMode == 2;
    }
}
