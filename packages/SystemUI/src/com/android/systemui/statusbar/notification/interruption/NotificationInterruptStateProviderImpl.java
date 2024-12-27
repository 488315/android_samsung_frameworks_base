package com.android.systemui.statusbar.notification.interruption;

import android.app.ActivityTaskManager;
import android.app.Notification;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import android.os.PowerManager;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.disableflags.data.model.DisableFlagsModel;
import com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepositoryImpl;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.EventLog;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import com.android.wm.shell.bubbles.Bubbles;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationInterruptStateProviderImpl implements NotificationInterruptStateProvider {
    public final Optional mBubbles;
    public final Context mContext;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final EventLog mEventLog;
    public final GlobalSettings mGlobalSettings;
    public final HeadsUpManager mHeadsUpManager;
    public final KeyguardNotificationVisibilityProvider mKeyguardNotificationVisibilityProvider;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final NotificationInterruptLogger mLogger;
    public final PowerManager mPowerManager;
    public final StatusBarStateController mStatusBarStateController;
    public final SystemClock mSystemClock;
    public final UiEventLogger mUiEventLogger;
    public final List mSuppressors = new ArrayList();
    protected boolean mUseHeadsUp = false;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl$2, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision;

        static {
            int[] iArr = new int[NotificationInterruptStateProvider.FullScreenIntentDecision.values().length];
            $SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision = iArr;
            try {
                iArr[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FULL_SCREEN_INTENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_BUBBLE_METADATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NO_HUN_OR_KEYGUARD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public enum NotificationInterruptEvent implements UiEventLogger.UiEventEnum {
        FSI_SUPPRESSED_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR(1235),
        FSI_SUPPRESSED_SUPPRESSIVE_BUBBLE_METADATA(1353),
        FSI_SUPPRESSED_NO_HUN_OR_KEYGUARD(1236),
        HUN_SUPPRESSED_OLD_WHEN(1237),
        HUN_SNOOZE_BYPASSED_POTENTIALLY_SUPPRESSED_FSI(1269);

        private final int mId;

        NotificationInterruptEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public NotificationInterruptStateProviderImpl(PowerManager powerManager, AmbientDisplayConfiguration ambientDisplayConfiguration, BatteryController batteryController, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, HeadsUpManager headsUpManager, NotificationInterruptLogger notificationInterruptLogger, Handler handler, NotifPipelineFlags notifPipelineFlags, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, UiEventLogger uiEventLogger, UserTracker userTracker, DeviceProvisionedController deviceProvisionedController, SystemClock systemClock, GlobalSettings globalSettings, EventLog eventLog, Context context, Optional<Bubbles> optional) {
        this.mPowerManager = powerManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mHeadsUpManager = headsUpManager;
        this.mLogger = notificationInterruptLogger;
        this.mKeyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.mUiEventLogger = uiEventLogger;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mSystemClock = systemClock;
        this.mGlobalSettings = globalSettings;
        this.mEventLog = eventLog;
        this.mBubbles = optional;
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl = NotificationInterruptStateProviderImpl.this;
                boolean z2 = notificationInterruptStateProviderImpl.mUseHeadsUp;
                boolean z3 = notificationInterruptStateProviderImpl.mGlobalSettings.getInt("heads_up_notifications_enabled", 0) != 0;
                NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl2 = NotificationInterruptStateProviderImpl.this;
                notificationInterruptStateProviderImpl2.mUseHeadsUp = z3;
                NotificationInterruptLogger notificationInterruptLogger2 = notificationInterruptStateProviderImpl2.mLogger;
                notificationInterruptLogger2.getClass();
                LogLevel logLevel = LogLevel.INFO;
                NotificationInterruptLogger$logHeadsUpFeatureChanged$2 notificationInterruptLogger$logHeadsUpFeatureChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logHeadsUpFeatureChanged$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("heads up is enabled=", ((LogMessage) obj).getBool1());
                    }
                };
                LogBuffer logBuffer = notificationInterruptLogger2.buffer;
                LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logHeadsUpFeatureChanged$2, null);
                ((LogMessageImpl) obtain).bool1 = z3;
                logBuffer.commit(obtain);
                NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl3 = NotificationInterruptStateProviderImpl.this;
                boolean z4 = notificationInterruptStateProviderImpl3.mUseHeadsUp;
                if (z2 == z4 || z4) {
                    return;
                }
                NotificationInterruptLogger notificationInterruptLogger3 = notificationInterruptStateProviderImpl3.mLogger;
                notificationInterruptLogger3.getClass();
                NotificationInterruptLogger$logWillDismissAll$2 notificationInterruptLogger$logWillDismissAll$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logWillDismissAll$2
                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return "dismissing any existing heads up notification on disable event";
                    }
                };
                LogBuffer logBuffer2 = notificationInterruptLogger3.buffer;
                logBuffer2.commit(logBuffer2.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logWillDismissAll$2, null));
                ((BaseHeadsUpManager) NotificationInterruptStateProviderImpl.this.mHeadsUpManager).releaseAllImmediately();
            }
        };
        globalSettings.registerContentObserverSync(globalSettings.getUriFor("heads_up_notifications_enabled"), true, contentObserver);
        globalSettings.registerContentObserverSync(globalSettings.getUriFor("ticker_gets_heads_up"), true, contentObserver);
        contentObserver.onChange(true);
        this.mContext = context;
        this.mKeyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
    }

    public static NotificationInterruptStateProvider.FullScreenIntentDecision getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision fullScreenIntentDecision, boolean z) {
        return z ? fullScreenIntentDecision.shouldLaunch ? NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSED_ONLY_BY_DND : NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSED_BY_DND : fullScreenIntentDecision;
    }

    public final boolean canAlertAwakeCommon(NotificationEntry notificationEntry, boolean z) {
        notificationEntry.getClass();
        for (int i = 0; i < ((ArrayList) this.mSuppressors).size(); i++) {
            if (StatusBarNotificationPresenter.this.mVrMode) {
                if (z) {
                    this.mLogger.logNoAlertingSuppressedBy(notificationEntry, true);
                }
                return false;
            }
        }
        return true;
    }

    public final boolean canAlertCommon(NotificationEntry notificationEntry, boolean z) {
        int i = 0;
        while (true) {
            int size = ((ArrayList) this.mSuppressors).size();
            NotificationInterruptLogger notificationInterruptLogger = this.mLogger;
            if (i >= size) {
                if (notificationEntry.mRanking.isSuspended()) {
                    if (z) {
                        notificationInterruptLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        NotificationInterruptLogger$logNoAlertingAppSuspended$2 notificationInterruptLogger$logNoAlertingAppSuspended$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoAlertingAppSuspended$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No alerting: app is suspended: ", ((LogMessage) obj).getStr1());
                            }
                        };
                        LogBuffer logBuffer = notificationInterruptLogger.buffer;
                        LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logNoAlertingAppSuspended$2, null);
                        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                        logBuffer.commit(obtain);
                    }
                    return false;
                }
                if (!((KeyguardNotificationVisibilityProviderImpl) this.mKeyguardNotificationVisibilityProvider).shouldHideNotification(notificationEntry)) {
                    return true;
                }
                if (z) {
                    notificationInterruptLogger.getClass();
                    LogLevel logLevel2 = LogLevel.DEBUG;
                    NotificationInterruptLogger$logNoAlertingNotificationHidden$2 notificationInterruptLogger$logNoAlertingNotificationHidden$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoAlertingNotificationHidden$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No alerting: notification hidden on lock screen: ", ((LogMessage) obj).getStr1());
                        }
                    };
                    LogBuffer logBuffer2 = notificationInterruptLogger.buffer;
                    LogMessage obtain2 = logBuffer2.obtain("InterruptionStateProvider", logLevel2, notificationInterruptLogger$logNoAlertingNotificationHidden$2, null);
                    ((LogMessageImpl) obtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                    logBuffer2.commit(obtain2);
                }
                return false;
            }
            if (!((((DisableFlagsModel) ((DisableFlagsRepositoryImpl) StatusBarNotificationPresenter.this.mNotificationAlertsInteractor.disableFlagsRepository).disableFlags.$$delegate_0.getValue()).disable1 & 262144) == 0)) {
                if (z) {
                    notificationInterruptLogger.logNoAlertingSuppressedBy(notificationEntry, false);
                }
                return false;
            }
            i++;
        }
    }

    public final boolean canHeadsUpCommonForFrontCoverScreen(NotificationEntry notificationEntry) {
        if (notificationEntry.mRanking.getImportance() < 4) {
            return false;
        }
        for (int i = 0; i < ((ArrayList) this.mSuppressors).size(); i++) {
            if (!((((DisableFlagsModel) ((DisableFlagsRepositoryImpl) StatusBarNotificationPresenter.this.mNotificationAlertsInteractor.disableFlagsRepository).disableFlags.$$delegate_0.getValue()).disable1 & 262144) == 0)) {
                return false;
            }
        }
        return this.mUseHeadsUp && !notificationEntry.shouldSuppressVisualEffect(16);
    }

    /* JADX WARN: Code restructure failed: missing block: B:162:0x038a, code lost:
    
        if (r4 != false) goto L157;
     */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x038a  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x03c5 A[LOOP:0: B:151:0x033f->B:165:0x03c5, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x039a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00dc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean checkHeadsUp(com.android.systemui.statusbar.notification.collection.NotificationEntry r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 1126
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl.checkHeadsUp(com.android.systemui.statusbar.notification.collection.NotificationEntry, boolean):boolean");
    }

    public final NotificationInterruptStateProvider.FullScreenIntentDecision getFullScreenIntentDecision(NotificationEntry notificationEntry) {
        if (notificationEntry.mSbn.getNotification().fullScreenIntent == null) {
            return notificationEntry.isStickyAndNotDemoted() ? NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SHOW_STICKY_HUN : NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FULL_SCREEN_INTENT;
        }
        boolean shouldSuppressVisualEffect = notificationEntry.shouldSuppressVisualEffect(4);
        if (notificationEntry.mRanking.getImportance() < 4) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NOT_IMPORTANT_ENOUGH, shouldSuppressVisualEffect);
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        if (statusBarNotification.isGroup() && statusBarNotification.getNotification().suppressAlertingDueToGrouping()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR, shouldSuppressVisualEffect);
        }
        Notification.BubbleMetadata bubbleMetadata = statusBarNotification.getNotification().getBubbleMetadata();
        if (bubbleMetadata != null && bubbleMetadata.isNotificationSuppressed()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_BUBBLE_METADATA, shouldSuppressVisualEffect);
        }
        if (notificationEntry.mRanking.isSuspended()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUSPENDED, shouldSuppressVisualEffect);
        }
        if (!this.mPowerManager.isInteractive()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_DEVICE_NOT_INTERACTIVE, shouldSuppressVisualEffect);
        }
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        if (statusBarStateController.isDreaming()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_DEVICE_IS_DREAMING, shouldSuppressVisualEffect);
        }
        if (statusBarStateController.getState() == 1) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_KEYGUARD_SHOWING, shouldSuppressVisualEffect);
        }
        if (checkHeadsUp(notificationEntry, false)) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_EXPECTED_TO_HUN, shouldSuppressVisualEffect);
        }
        if ((notificationEntry.mSbn.getNotification().semFlags & 8192) != 0 || (notificationEntry.mSbn.getNotification().semFlags & 16384) != 0) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_EXPECTED_TO_BRIEF, shouldSuppressVisualEffect);
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing) {
            return keyguardStateControllerImpl.mOccluded ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_KEYGUARD_OCCLUDED, shouldSuppressVisualEffect) : getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_LOCKED_SHADE, shouldSuppressVisualEffect);
        }
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.mDeviceProvisionedController;
        return !deviceProvisionedControllerImpl.deviceProvisioned.get() ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_NOT_PROVISIONED, shouldSuppressVisualEffect) : !deviceProvisionedControllerImpl.isCurrentUserSetup() ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_USER_SETUP_INCOMPLETE, shouldSuppressVisualEffect) : getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NO_HUN_OR_KEYGUARD, shouldSuppressVisualEffect);
    }

    public final void logFullScreenIntentDecision(NotificationEntry notificationEntry, NotificationInterruptStateProvider.FullScreenIntentDecision fullScreenIntentDecision) {
        int uid = notificationEntry.mSbn.getUid();
        String packageName = notificationEntry.mSbn.getPackageName();
        int i = AnonymousClass2.$SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision[fullScreenIntentDecision.ordinal()];
        if (i != 1) {
            EventLog eventLog = this.mEventLog;
            NotificationInterruptLogger notificationInterruptLogger = this.mLogger;
            if (i == 2) {
                eventLog.writeEvent(1397638484, "231322873", Integer.valueOf(uid), "groupAlertBehavior");
                this.mUiEventLogger.log(NotificationInterruptEvent.FSI_SUPPRESSED_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR, uid, packageName);
                notificationInterruptLogger.logNoFullscreenWarning(notificationEntry, fullScreenIntentDecision + ": GroupAlertBehavior will prevent HUN");
                return;
            }
            if (i == 3) {
                eventLog.writeEvent(1397638484, "274759612", Integer.valueOf(uid), "bubbleMetadata");
                this.mUiEventLogger.log(NotificationInterruptEvent.FSI_SUPPRESSED_SUPPRESSIVE_BUBBLE_METADATA, uid, packageName);
                notificationInterruptLogger.logNoFullscreenWarning(notificationEntry, fullScreenIntentDecision + ": BubbleMetadata may prevent HUN");
                return;
            }
            if (i == 4) {
                eventLog.writeEvent(1397638484, "231322873", Integer.valueOf(uid), "no hun or keyguard");
                this.mUiEventLogger.log(NotificationInterruptEvent.FSI_SUPPRESSED_NO_HUN_OR_KEYGUARD, uid, packageName);
                notificationInterruptLogger.logNoFullscreenWarning(notificationEntry, fullScreenIntentDecision + ": Expected not to HUN while not on keyguard");
                return;
            }
            if (fullScreenIntentDecision.shouldLaunch) {
                String name = fullScreenIntentDecision.name();
                notificationInterruptLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                NotificationInterruptLogger$logFullscreen$2 notificationInterruptLogger$logFullscreen$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logFullscreen$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return FontProvider$$ExternalSyntheticOutline0.m("FullScreenIntent: ", logMessage.getStr2(), ": ", logMessage.getStr1());
                    }
                };
                LogBuffer logBuffer = notificationInterruptLogger.buffer;
                LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logFullscreen$2, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                logMessageImpl.str2 = name;
                logBuffer.commit(obtain);
                return;
            }
            String name2 = fullScreenIntentDecision.name();
            notificationInterruptLogger.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            NotificationInterruptLogger$logNoFullscreen$2 notificationInterruptLogger$logNoFullscreen$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoFullscreen$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return FontProvider$$ExternalSyntheticOutline0.m("No FullScreenIntent: ", logMessage.getStr2(), ": ", logMessage.getStr1());
                }
            };
            LogBuffer logBuffer2 = notificationInterruptLogger.buffer;
            LogMessage obtain2 = logBuffer2.obtain("InterruptionStateProvider", logLevel2, notificationInterruptLogger$logNoFullscreen$2, null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
            logMessageImpl2.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logMessageImpl2.str2 = name2;
            logBuffer2.commit(obtain2);
        }
    }

    public final boolean shouldBubbleUp(NotificationEntry notificationEntry) {
        if (((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isStandalone()) {
            return false;
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        if (!canAlertCommon(notificationEntry, false) || !canAlertAwakeCommon(notificationEntry, false)) {
            return false;
        }
        boolean canBubble = notificationEntry.mRanking.canBubble();
        NotificationInterruptLogger notificationInterruptLogger = this.mLogger;
        if (!canBubble) {
            notificationInterruptLogger.getClass();
            return false;
        }
        Notification.BubbleMetadata bubbleMetadata = notificationEntry.mBubbleMetadata;
        if (bubbleMetadata != null && (bubbleMetadata.getShortcutId() != null || notificationEntry.mBubbleMetadata.getIntent() != null)) {
            if (ActivityTaskManager.supportsMultiWindow(this.mContext)) {
                return !MultiWindowManager.getInstance().isMultiWindowBlockListApp(statusBarNotification.getPackageName());
            }
            Log.d("InterruptionStateProvider", "No bubble up: notification: " + statusBarNotification.getKey() + " doesn't mw");
            return false;
        }
        notificationInterruptLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationInterruptLogger$logNoBubbleNoMetadata$2 notificationInterruptLogger$logNoBubbleNoMetadata$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoBubbleNoMetadata$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("No bubble up: notification: ", ((LogMessage) obj).getStr1(), " doesn't have valid metadata");
            }
        };
        LogBuffer logBuffer = notificationInterruptLogger.buffer;
        LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logNoBubbleNoMetadata$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
        return false;
    }
}
