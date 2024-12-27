package com.android.systemui.shade.domain.interactor;

import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecHideNotificationShadeInMirrorInteractor$notify$1 implements Runnable {
    public final /* synthetic */ SecHideNotificationShadeInMirrorInteractor this$0;

    public SecHideNotificationShadeInMirrorInteractor$notify$1(SecHideNotificationShadeInMirrorInteractor secHideNotificationShadeInMirrorInteractor) {
        this.this$0 = secHideNotificationShadeInMirrorInteractor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SettingsHelper settingsHelper;
        settingsHelper = this.this$0.settingsHelper;
        boolean shouldHideNotificationShadeInMirror = settingsHelper.shouldHideNotificationShadeInMirror();
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("notify(): ", "SecHideNotificationShadeInMirrorInteractor", shouldHideNotificationShadeInMirror);
        SecHideNotificationShadeInMirrorInteractor secHideNotificationShadeInMirrorInteractor = this.this$0;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = ((NotificationShadeWindowControllerImpl) secHideNotificationShadeInMirrorInteractor.notificationShadeWindowController).mHelper;
        NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
        if (currentState.shouldHideNotificationShadeInMirror != shouldHideNotificationShadeInMirror) {
            currentState.shouldHideNotificationShadeInMirror = shouldHideNotificationShadeInMirror;
            secNotificationShadeWindowControllerHelperImpl.apply(currentState);
        }
        StatusBarWindowController statusBarWindowController = secHideNotificationShadeInMirrorInteractor.statusBarWindowController;
        StatusBarWindowController.State state = statusBarWindowController.mCurrentState;
        if (state.mShouldHideNotificationShadeInMirror == shouldHideNotificationShadeInMirror) {
            return;
        }
        state.mShouldHideNotificationShadeInMirror = shouldHideNotificationShadeInMirror;
        statusBarWindowController.apply(state);
    }
}
