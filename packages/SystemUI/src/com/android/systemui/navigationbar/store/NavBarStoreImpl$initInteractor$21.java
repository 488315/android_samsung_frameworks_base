package com.android.systemui.navigationbar.store;

import android.content.res.Resources;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationModeController;

/* loaded from: classes2.dex */
public final class NavBarStoreImpl$initInteractor$21 implements Runnable {
    public static final NavBarStoreImpl$initInteractor$21 INSTANCE = new NavBarStoreImpl$initInteractor$21();

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        ((NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class)).updateCurrentInteractionMode(true);
    }
}
