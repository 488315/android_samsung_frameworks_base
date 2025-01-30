package com.android.systemui.shade;

import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecHideInformationMirroringController {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "SecHideInformationMirroringController";
    private final Handler mainHandler = (Handler) Dependency.get(Dependency.MAIN_HANDLER);
    private final SecHideInformationMirroringModel model;
    private final NotificationShadeWindowController notificationShadeWindowController;
    private SmartViewSettingListener settingListener;
    private final SettingsHelper settingsHelper;
    private final StatusBarWindowController statusBarWindowController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SmartViewSettingListener implements SettingsHelper.OnChangedCallback {
        public SmartViewSettingListener() {
            SecHideInformationMirroringController.this.settingsHelper.registerCallback(this, Settings.Global.getUriFor("smart_view_show_notification_on"));
        }

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            SecHideInformationMirroringController secHideInformationMirroringController = SecHideInformationMirroringController.this;
            secHideInformationMirroringController.printLogLine("onChanged()");
            secHideInformationMirroringController.handleHideInformationMirroringWindowFlag();
        }
    }

    public SecHideInformationMirroringController(NotificationShadeWindowController notificationShadeWindowController, StatusBarWindowController statusBarWindowController, SecHideInformationMirroringModel secHideInformationMirroringModel, SettingsHelper settingsHelper) {
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.statusBarWindowController = statusBarWindowController;
        this.model = secHideInformationMirroringModel;
        this.settingsHelper = settingsHelper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleHideInformationMirroringWindowFlag() {
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.shade.SecHideInformationMirroringController$handleHideInformationMirroringWindowFlag$1
            @Override // java.lang.Runnable
            public final void run() {
                SecHideInformationMirroringModel secHideInformationMirroringModel;
                NotificationShadeWindowController notificationShadeWindowController;
                StatusBarWindowController statusBarWindowController;
                secHideInformationMirroringModel = SecHideInformationMirroringController.this.model;
                boolean shouldHideInformation = secHideInformationMirroringModel.shouldHideInformation();
                SecHideInformationMirroringController secHideInformationMirroringController = SecHideInformationMirroringController.this;
                notificationShadeWindowController = secHideInformationMirroringController.notificationShadeWindowController;
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mHelper;
                NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
                if (currentState.isHideInformationMirroring != shouldHideInformation) {
                    currentState.isHideInformationMirroring = shouldHideInformation;
                    secNotificationShadeWindowControllerHelperImpl.apply(currentState);
                }
                statusBarWindowController = secHideInformationMirroringController.statusBarWindowController;
                StatusBarWindowController.State state = statusBarWindowController.mCurrentState;
                if (state.mIsHideInformationMirroring != shouldHideInformation) {
                    state.mIsHideInformationMirroring = shouldHideInformation;
                    statusBarWindowController.apply(state, false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void printLogLine(String str) {
        Log.d(TAG, str + " SMART_VIEW_SHOW_NOTIFICATION_ON hide? " + this.model.shouldHideInformation());
    }

    public final void init() {
        printLogLine("init()");
        this.settingListener = new SmartViewSettingListener();
        handleHideInformationMirroringWindowFlag();
    }
}
