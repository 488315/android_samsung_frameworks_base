package com.android.systemui.volume.util;

import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;

/* loaded from: classes3.dex */
public final class StatusBarStateControllerWrapper {
    public final SysuiStatusBarStateController statusBarStateController = (SysuiStatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
}
