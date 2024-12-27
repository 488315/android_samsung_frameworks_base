package com.android.systemui.volume.util;

import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class StatusBarStateControllerWrapper {
    public final SysuiStatusBarStateController statusBarStateController = (SysuiStatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
}
