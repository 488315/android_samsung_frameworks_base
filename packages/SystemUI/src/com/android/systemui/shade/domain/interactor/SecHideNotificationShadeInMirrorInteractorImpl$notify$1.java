package com.android.systemui.shade.domain.interactor;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecHideNotificationShadeInMirrorInteractorImpl$notify$1 implements Runnable {
    public final /* synthetic */ SecHideNotificationShadeInMirrorInteractorImpl this$0;

    public SecHideNotificationShadeInMirrorInteractorImpl$notify$1(SecHideNotificationShadeInMirrorInteractorImpl secHideNotificationShadeInMirrorInteractorImpl) {
        this.this$0 = secHideNotificationShadeInMirrorInteractorImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SettingsHelper settingsHelper;
        settingsHelper = this.this$0.settingsHelper;
        boolean shouldHideNotificationShadeInMirror = settingsHelper.shouldHideNotificationShadeInMirror();
        EmergencyButtonController$$ExternalSyntheticOutline0.m("notify(): ", "SecHideNotificationShadeInMirrorInteractor", shouldHideNotificationShadeInMirror);
        SecHideNotificationShadeInMirrorInteractorImpl secHideNotificationShadeInMirrorInteractorImpl = this.this$0;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = ((NotificationShadeWindowControllerImpl) secHideNotificationShadeInMirrorInteractorImpl.notificationShadeWindowController).mHelper;
        NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
        if (currentState.shouldHideNotificationShadeInMirror != shouldHideNotificationShadeInMirror) {
            currentState.shouldHideNotificationShadeInMirror = shouldHideNotificationShadeInMirror;
            secNotificationShadeWindowControllerHelperImpl.apply(currentState);
        }
        StatusBarWindowControllerImpl statusBarWindowControllerImpl = (StatusBarWindowControllerImpl) secHideNotificationShadeInMirrorInteractorImpl.statusBarWindowController;
        StatusBarWindowControllerImpl.State state = statusBarWindowControllerImpl.mCurrentState;
        if (state.mIsHideInformationMirroring != shouldHideNotificationShadeInMirror) {
            state.mIsHideInformationMirroring = shouldHideNotificationShadeInMirror;
            statusBarWindowControllerImpl.apply(state);
        }
    }
}
