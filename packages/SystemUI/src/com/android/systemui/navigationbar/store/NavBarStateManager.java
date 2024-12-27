package com.android.systemui.navigationbar.store;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface NavBarStateManager {
    static boolean isSideAndBottomGestureMode$default(NavBarStateManager navBarStateManager) {
        return ((NavBarStateManagerImpl) navBarStateManager).states.navigationMode == 2;
    }
}
