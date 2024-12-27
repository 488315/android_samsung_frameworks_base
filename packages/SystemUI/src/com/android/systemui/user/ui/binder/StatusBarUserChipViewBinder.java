package com.android.systemui.user.ui.binder;

import com.android.systemui.BasicRune;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
