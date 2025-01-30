package com.android.systemui.user.ui.binder;

import com.android.systemui.BasicRune;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarUserChipViewBinder {
    public static final StatusBarUserChipViewBinder INSTANCE = new StatusBarUserChipViewBinder();

    private StatusBarUserChipViewBinder() {
    }

    public static final void bind(StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, StatusBarUserChipViewModel statusBarUserChipViewModel, KeyguardStatusBarView$$ExternalSyntheticLambda0 keyguardStatusBarView$$ExternalSyntheticLambda0) {
        RepeatWhenAttachedKt.repeatWhenAttached(statusBarUserSwitcherContainer, EmptyCoroutineContext.INSTANCE, new StatusBarUserChipViewBinder$bind$1(statusBarUserSwitcherContainer, statusBarUserChipViewModel, null));
        if (BasicRune.STATUS_LAYOUT_MUM_ICON) {
            RepeatWhenAttachedKt.repeatWhenAttached(statusBarUserSwitcherContainer, EmptyCoroutineContext.INSTANCE, new StatusBarUserChipViewBinder$bind$2(statusBarUserChipViewModel, keyguardStatusBarView$$ExternalSyntheticLambda0, null));
        }
    }
}
