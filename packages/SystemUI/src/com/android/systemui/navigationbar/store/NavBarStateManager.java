package com.android.systemui.navigationbar.store;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface NavBarStateManager {
    static boolean isSideAndBottomGestureMode$default(NavBarStateManager navBarStateManager) {
        return ((NavBarStateManagerImpl) navBarStateManager).states.navigationMode == 2;
    }
}
