package com.android.systemui.navigationbar.interactor;

import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverDisplayWidgetInteractor$displayReadyRunnable$1 implements Runnable {
    public final /* synthetic */ CoverDisplayWidgetInteractor this$0;

    public CoverDisplayWidgetInteractor$displayReadyRunnable$1(CoverDisplayWidgetInteractor coverDisplayWidgetInteractor) {
        this.this$0 = coverDisplayWidgetInteractor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NavigationBarController navigationBarController = (NavigationBarController) Dependency.get(NavigationBarController.class);
        if (navigationBarController != null) {
            SettingsHelper settingsHelper = this.this$0.settingsHelper;
            settingsHelper.getClass();
            boolean z = false;
            if ((LsRune.SUBSCREEN_WATCHFACE && settingsHelper.mItemLists.get("show_navigation_for_subscreen").getIntValue() != 0) && SemWindowManager.getInstance().isFolded()) {
                z = true;
            }
            if (z) {
                navigationBarController.onDisplayReady(1);
            } else {
                navigationBarController.removeNavigationBar(1);
            }
        }
    }
}
