package com.android.systemui.shade.domain.interactor;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;

/* loaded from: classes3.dex */
public final class SecHideNotificationShadeInMirrorInteractorImpl$notify$1 implements Runnable {
    public final /* synthetic */ SecHideNotificationShadeInMirrorInteractorImpl this$0;

    public SecHideNotificationShadeInMirrorInteractorImpl$notify$1(SecHideNotificationShadeInMirrorInteractorImpl secHideNotificationShadeInMirrorInteractorImpl) {
        this.this$0 = secHideNotificationShadeInMirrorInteractorImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean zShouldHideNotificationShadeInMirror = this.this$0.settingsHelper.shouldHideNotificationShadeInMirror();
        EmergencyButtonController$$ExternalSyntheticOutline0.m("notify(): ", "SecHideNotificationShadeInMirrorInteractor", zShouldHideNotificationShadeInMirror);
        SecHideNotificationShadeInMirrorInteractorImpl secHideNotificationShadeInMirrorInteractorImpl = this.this$0;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = ((NotificationShadeWindowControllerImpl) secHideNotificationShadeInMirrorInteractorImpl.notificationShadeWindowController).mHelper;
        NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
        if (currentState.shouldHideNotificationShadeInMirror != zShouldHideNotificationShadeInMirror) {
            currentState.shouldHideNotificationShadeInMirror = zShouldHideNotificationShadeInMirror;
            secNotificationShadeWindowControllerHelperImpl.apply(currentState);
        }
        StatusBarWindowControllerImpl statusBarWindowControllerImpl = (StatusBarWindowControllerImpl) secHideNotificationShadeInMirrorInteractorImpl.statusBarWindowController;
        StatusBarWindowControllerImpl.State state = statusBarWindowControllerImpl.mCurrentState;
        if (state.mIsHideInformationMirroring != zShouldHideNotificationShadeInMirror) {
            state.mIsHideInformationMirroring = zShouldHideNotificationShadeInMirror;
            statusBarWindowControllerImpl.apply(state);
        }
    }
}
