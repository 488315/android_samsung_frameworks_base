package com.android.systemui.volume.util;

import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarStateControllerWrapper {
    public final SysuiStatusBarStateController statusBarStateController = (SysuiStatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
}
