package com.android.systemui.statusbar.notification.interruption;

import android.app.ActivityTaskManager;
import android.app.Notification;
import android.service.notification.StatusBarNotification;
import android.util.EventLog;
import android.util.Log;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationInterruptStateProviderWrapper implements VisualInterruptionDecisionProvider {
    public final NotificationInterruptStateProvider wrapped;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum DecisionImpl {
        SHOULD_INTERRUPT(true),
        SHOULD_NOT_INTERRUPT(false);

        public static final Companion Companion = new Companion(null);
        private final String logReason = "unknown";
        private final boolean shouldInterrupt;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        DecisionImpl(boolean z) {
            this.shouldInterrupt = z;
        }

        public final String getLogReason() {
            return this.logReason;
        }

        public final boolean getShouldInterrupt() {
            return this.shouldInterrupt;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FullScreenIntentDecisionImpl {
        public final String logReason;
        public final NotificationInterruptStateProvider.FullScreenIntentDecision originalDecision;
        public final NotificationEntry originalEntry;
        public final boolean shouldInterrupt;
        public final boolean wouldInterruptWithoutDnd;

        public FullScreenIntentDecisionImpl(NotificationEntry notificationEntry, NotificationInterruptStateProvider.FullScreenIntentDecision fullScreenIntentDecision) {
            this.originalEntry = notificationEntry;
            this.originalDecision = fullScreenIntentDecision;
            this.shouldInterrupt = fullScreenIntentDecision.shouldLaunch;
            this.wouldInterruptWithoutDnd = fullScreenIntentDecision == NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSED_ONLY_BY_DND;
            this.logReason = fullScreenIntentDecision.name();
        }
    }

    public NotificationInterruptStateProviderWrapper(NotificationInterruptStateProvider notificationInterruptStateProvider) {
        this.wrapped = notificationInterruptStateProvider;
    }

    public final void logFullScreenIntentDecision(FullScreenIntentDecisionImpl fullScreenIntentDecisionImpl) {
        NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl = (NotificationInterruptStateProviderImpl) this.wrapped;
        notificationInterruptStateProviderImpl.getClass();
        NotificationEntry notificationEntry = fullScreenIntentDecisionImpl.originalEntry;
        int uid = notificationEntry.mSbn.getUid();
        String packageName = notificationEntry.mSbn.getPackageName();
        int[] iArr = NotificationInterruptStateProviderImpl.AbstractC28542.f352x15c19f3b;
        NotificationInterruptStateProvider.FullScreenIntentDecision fullScreenIntentDecision = fullScreenIntentDecisionImpl.originalDecision;
        int i = iArr[fullScreenIntentDecision.ordinal()];
        if (i != 1) {
            UiEventLogger uiEventLogger = notificationInterruptStateProviderImpl.mUiEventLogger;
            NotificationInterruptLogger notificationInterruptLogger = notificationInterruptStateProviderImpl.mLogger;
            if (i == 2) {
                EventLog.writeEvent(1397638484, "231322873", Integer.valueOf(uid), "groupAlertBehavior");
                uiEventLogger.log(NotificationInterruptStateProviderImpl.NotificationInterruptEvent.FSI_SUPPRESSED_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR, uid, packageName);
                notificationInterruptLogger.logNoFullscreenWarning(notificationEntry, fullScreenIntentDecision + ": GroupAlertBehavior will prevent HUN");
                return;
            }
            if (i == 3) {
                EventLog.writeEvent(1397638484, "274759612", Integer.valueOf(uid), "bubbleMetadata");
                uiEventLogger.log(NotificationInterruptStateProviderImpl.NotificationInterruptEvent.FSI_SUPPRESSED_SUPPRESSIVE_BUBBLE_METADATA, uid, packageName);
                notificationInterruptLogger.logNoFullscreenWarning(notificationEntry, fullScreenIntentDecision + ": BubbleMetadata may prevent HUN");
                return;
            }
            if (i == 4) {
                EventLog.writeEvent(1397638484, "231322873", Integer.valueOf(uid), "no hun or keyguard");
                uiEventLogger.log(NotificationInterruptStateProviderImpl.NotificationInterruptEvent.FSI_SUPPRESSED_NO_HUN_OR_KEYGUARD, uid, packageName);
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
                        return FontProvider$$ExternalSyntheticOutline0.m32m("FullScreenIntent: ", logMessage.getStr2(), ": ", logMessage.getStr1());
                    }
                };
                LogBuffer logBuffer = notificationInterruptLogger.buffer;
                LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logFullscreen$2, null);
                obtain.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
                obtain.setStr2(name);
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
                    return FontProvider$$ExternalSyntheticOutline0.m32m("No FullScreenIntent: ", logMessage.getStr2(), ": ", logMessage.getStr1());
                }
            };
            LogBuffer logBuffer2 = notificationInterruptLogger.buffer;
            LogMessage obtain2 = logBuffer2.obtain("InterruptionStateProvider", logLevel2, notificationInterruptLogger$logNoFullscreen$2, null);
            obtain2.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
            obtain2.setStr2(name2);
            logBuffer2.commit(obtain2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x007a, code lost:
    
        if (com.samsung.android.multiwindow.MultiWindowManager.getInstance().isMultiWindowBlockListApp(r0.getPackageName()) != false) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final DecisionImpl makeAndLogBubbleDecision(NotificationEntry notificationEntry) {
        boolean z;
        NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl = (NotificationInterruptStateProviderImpl) this.wrapped;
        notificationInterruptStateProviderImpl.getClass();
        if (!((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isStandalone()) {
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            z = true;
            if (notificationInterruptStateProviderImpl.canAlertCommon(notificationEntry, true) && notificationInterruptStateProviderImpl.canAlertAwakeCommon(notificationEntry, true)) {
                boolean canBubble = notificationEntry.canBubble();
                NotificationInterruptLogger notificationInterruptLogger = notificationInterruptStateProviderImpl.mLogger;
                if (canBubble) {
                    Notification.BubbleMetadata bubbleMetadata = notificationEntry.mBubbleMetadata;
                    if (bubbleMetadata == null || (bubbleMetadata.getShortcutId() == null && notificationEntry.mBubbleMetadata.getIntent() == null)) {
                        notificationInterruptLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        NotificationInterruptLogger$logNoBubbleNoMetadata$2 notificationInterruptLogger$logNoBubbleNoMetadata$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoBubbleNoMetadata$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return PathParser$$ExternalSyntheticOutline0.m29m("No bubble up: notification: ", ((LogMessage) obj).getStr1(), " doesn't have valid metadata");
                            }
                        };
                        LogBuffer logBuffer = notificationInterruptLogger.buffer;
                        LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logNoBubbleNoMetadata$2, null);
                        NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain, logBuffer, obtain);
                    } else if (!ActivityTaskManager.supportsMultiWindow(notificationInterruptStateProviderImpl.mContext)) {
                        Log.d("InterruptionStateProvider", "No bubble up: notification: " + statusBarNotification.getKey() + " doesn't mw");
                    }
                } else {
                    notificationInterruptLogger.getClass();
                }
            }
        }
        z = false;
        DecisionImpl.Companion.getClass();
        return z ? DecisionImpl.SHOULD_INTERRUPT : DecisionImpl.SHOULD_NOT_INTERRUPT;
    }

    public final FullScreenIntentDecisionImpl makeUnloggedFullScreenIntentDecision(NotificationEntry notificationEntry) {
        NotificationInterruptStateProvider.FullScreenIntentDecision decisionGivenSuppression;
        NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl = (NotificationInterruptStateProviderImpl) this.wrapped;
        notificationInterruptStateProviderImpl.getClass();
        if (notificationEntry.mSbn.getNotification().fullScreenIntent == null) {
            decisionGivenSuppression = notificationEntry.isStickyAndNotDemoted() ? NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SHOW_STICKY_HUN : NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FULL_SCREEN_INTENT;
        } else {
            boolean shouldSuppressVisualEffect = notificationEntry.shouldSuppressVisualEffect(4);
            if (notificationEntry.getImportance() < 4) {
                decisionGivenSuppression = NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NOT_IMPORTANT_ENOUGH, shouldSuppressVisualEffect);
            } else {
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                if (statusBarNotification.isGroup() && statusBarNotification.getNotification().suppressAlertingDueToGrouping()) {
                    decisionGivenSuppression = NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR, shouldSuppressVisualEffect);
                } else {
                    Notification.BubbleMetadata bubbleMetadata = statusBarNotification.getNotification().getBubbleMetadata();
                    if (bubbleMetadata != null && bubbleMetadata.isNotificationSuppressed()) {
                        decisionGivenSuppression = NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_BUBBLE_METADATA, shouldSuppressVisualEffect);
                    } else if (notificationEntry.mRanking.isSuspended()) {
                        decisionGivenSuppression = NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUSPENDED, shouldSuppressVisualEffect);
                    } else if (notificationInterruptStateProviderImpl.mPowerManager.isInteractive()) {
                        StatusBarStateController statusBarStateController = notificationInterruptStateProviderImpl.mStatusBarStateController;
                        if (statusBarStateController.isDreaming()) {
                            decisionGivenSuppression = NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_DEVICE_IS_DREAMING, shouldSuppressVisualEffect);
                        } else {
                            boolean z = true;
                            if (statusBarStateController.getState() == 1) {
                                decisionGivenSuppression = NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_KEYGUARD_SHOWING, shouldSuppressVisualEffect);
                            } else if (notificationInterruptStateProviderImpl.checkHeadsUp(notificationEntry, false)) {
                                decisionGivenSuppression = NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_EXPECTED_TO_HUN, shouldSuppressVisualEffect);
                            } else if ((notificationEntry.mSbn.getNotification().semFlags & 8192) == 0 && (notificationEntry.mSbn.getNotification().semFlags & 16384) == 0) {
                                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) notificationInterruptStateProviderImpl.mKeyguardStateController;
                                if (keyguardStateControllerImpl.mShowing) {
                                    if (!notificationInterruptStateProviderImpl.mCommandQueue.panelsEnabled()) {
                                        Iterator it = ((NotifPipeline) notificationInterruptStateProviderImpl.mNotifCollection).getAllNotifs().iterator();
                                        while (true) {
                                            if (!it.hasNext()) {
                                                z = false;
                                                break;
                                            }
                                            NotificationEntry notificationEntry2 = (NotificationEntry) it.next();
                                            if (notificationEntry2 != null) {
                                                StatusBarNotification statusBarNotification2 = notificationEntry2.mSbn;
                                                if ("com.samsung.android.incallui".equals(statusBarNotification2.getPackageName()) && statusBarNotification2.getId() == 1 && statusBarNotification2.getTag() == null) {
                                                    break;
                                                }
                                            }
                                        }
                                        if (z) {
                                            decisionGivenSuppression = NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NO_HUN_BY_PANEL_DISABLED, shouldSuppressVisualEffect);
                                        }
                                    }
                                    decisionGivenSuppression = keyguardStateControllerImpl.mOccluded ? NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_KEYGUARD_OCCLUDED, shouldSuppressVisualEffect) : NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_LOCKED_SHADE, shouldSuppressVisualEffect);
                                } else {
                                    decisionGivenSuppression = NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NO_HUN_OR_KEYGUARD, shouldSuppressVisualEffect);
                                }
                            } else {
                                decisionGivenSuppression = NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_EXPECTED_TO_BRIEF, shouldSuppressVisualEffect);
                            }
                        }
                    } else {
                        decisionGivenSuppression = NotificationInterruptStateProviderImpl.getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_DEVICE_NOT_INTERACTIVE, shouldSuppressVisualEffect);
                    }
                }
            }
        }
        return new FullScreenIntentDecisionImpl(notificationEntry, decisionGivenSuppression);
    }
}
