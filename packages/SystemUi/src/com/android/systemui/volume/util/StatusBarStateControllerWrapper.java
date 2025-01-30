package com.android.systemui.volume.util;

import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarStateControllerWrapper {
    public final SysuiStatusBarStateController statusBarStateController = (SysuiStatusBarStateController) Dependency.get(StatusBarStateController.class);
}
