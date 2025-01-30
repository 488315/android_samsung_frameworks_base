package com.android.systemui.statusbar.notification.interruption;

import android.app.INotificationManager;
import android.app.Notification;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationInterruptStateProviderImpl implements NotificationInterruptStateProvider {
    public final CommandQueue mCommandQueue;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final NotifPipelineFlags mFlags;
    public final HeadsUpManager mHeadsUpManager;
    public final KeyguardNotificationVisibilityProvider mKeyguardNotificationVisibilityProvider;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final NotificationInterruptLogger mLogger;
    public final CommonNotifCollection mNotifCollection;
    public final PowerManager mPowerManager;
    public final StatusBarStateController mStatusBarStateController;
    public final UiEventLogger mUiEventLogger;
    public final List mSuppressors = new ArrayList();
    protected boolean mUseHeadsUp = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl$2 */
    public abstract /* synthetic */ class AbstractC28542 {

        /* renamed from: $SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision */
        public static final /* synthetic */ int[] f352x15c19f3b;

        static {
            int[] iArr = new int[NotificationInterruptStateProvider.FullScreenIntentDecision.values().length];
            f352x15c19f3b = iArr;
            try {
                iArr[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FULL_SCREEN_INTENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f352x15c19f3b[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f352x15c19f3b[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_BUBBLE_METADATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f352x15c19f3b[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NO_HUN_OR_KEYGUARD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public NotificationInterruptStateProviderImpl(ContentResolver contentResolver, PowerManager powerManager, AmbientDisplayConfiguration ambientDisplayConfiguration, BatteryController batteryController, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, HeadsUpManager headsUpManager, NotificationInterruptLogger notificationInterruptLogger, Handler handler, NotifPipelineFlags notifPipelineFlags, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, UiEventLogger uiEventLogger, UserTracker userTracker, Context context, CommandQueue commandQueue, CommonNotifCollection commonNotifCollection) {
        this.mContentResolver = contentResolver;
        this.mPowerManager = powerManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mHeadsUpManager = headsUpManager;
        this.mLogger = notificationInterruptLogger;
        this.mFlags = notifPipelineFlags;
        this.mKeyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.mUiEventLogger = uiEventLogger;
        this.mCommandQueue = commandQueue;
        this.mNotifCollection = commonNotifCollection;
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl = NotificationInterruptStateProviderImpl.this;
                boolean z2 = notificationInterruptStateProviderImpl.mUseHeadsUp;
                notificationInterruptStateProviderImpl.mUseHeadsUp = Settings.Global.getInt(notificationInterruptStateProviderImpl.mContentResolver, "heads_up_notifications_enabled", 0) != 0;
                NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl2 = NotificationInterruptStateProviderImpl.this;
                NotificationInterruptLogger notificationInterruptLogger2 = notificationInterruptStateProviderImpl2.mLogger;
                boolean z3 = notificationInterruptStateProviderImpl2.mUseHeadsUp;
                notificationInterruptLogger2.getClass();
                LogLevel logLevel = LogLevel.INFO;
                NotificationInterruptLogger$logHeadsUpFeatureChanged$2 notificationInterruptLogger$logHeadsUpFeatureChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logHeadsUpFeatureChanged$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AbstractC0866xb1ce8deb.m86m("heads up is enabled=", ((LogMessage) obj).getBool1());
                    }
                };
                LogBuffer logBuffer = notificationInterruptLogger2.buffer;
                LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logHeadsUpFeatureChanged$2, null);
                obtain.setBool1(z3);
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
                NotificationInterruptStateProviderImpl.this.mHeadsUpManager.releaseAllImmediately();
            }
        };
        contentResolver.registerContentObserver(Settings.Global.getUriFor("heads_up_notifications_enabled"), true, contentObserver);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("ticker_gets_heads_up"), true, contentObserver);
        contentObserver.onChange(true);
        this.mContext = context;
        this.mKeyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
    }

    public static NotificationInterruptStateProvider.FullScreenIntentDecision getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision fullScreenIntentDecision, boolean z) {
        return z ? fullScreenIntentDecision.shouldLaunch ? NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSED_ONLY_BY_DND : NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSED_BY_DND : fullScreenIntentDecision;
    }

    public final boolean canAlertAwakeCommon(NotificationEntry notificationEntry, boolean z) {
        notificationEntry.getClass();
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) this.mSuppressors;
            if (i >= arrayList.size()) {
                return true;
            }
            if (StatusBarNotificationPresenter.this.mVrMode) {
                if (z) {
                    this.mLogger.logNoAlertingSuppressedBy(notificationEntry, true);
                }
                return false;
            }
            i++;
        }
    }

    public final boolean canAlertCommon(NotificationEntry notificationEntry, boolean z) {
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) this.mSuppressors;
            int size = arrayList.size();
            NotificationInterruptLogger notificationInterruptLogger = this.mLogger;
            if (i >= size) {
                if (!((KeyguardNotificationVisibilityProviderImpl) this.mKeyguardNotificationVisibilityProvider).shouldHideNotification(notificationEntry)) {
                    return true;
                }
                if (z) {
                    notificationInterruptLogger.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    NotificationInterruptLogger$keyguardHideNotification$2 notificationInterruptLogger$keyguardHideNotification$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$keyguardHideNotification$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("Keyguard Hide Notification: ", ((LogMessage) obj).getStr1());
                        }
                    };
                    LogBuffer logBuffer = notificationInterruptLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$keyguardHideNotification$2, null);
                    NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain, logBuffer, obtain);
                }
                return false;
            }
            if ((((CentralSurfacesImpl) StatusBarNotificationPresenter.this.mCentralSurfaces).mDisabled1 & 262144) != 0) {
                if (z) {
                    notificationInterruptLogger.logNoAlertingSuppressedBy(notificationEntry, false);
                }
                return false;
            }
            i++;
        }
    }

    public final boolean canHeadsUpCommonForFrontCoverScreen(NotificationEntry notificationEntry) {
        if (notificationEntry.getImportance() < 4) {
            return false;
        }
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) this.mSuppressors;
            if (i >= arrayList.size()) {
                return this.mUseHeadsUp && !notificationEntry.shouldSuppressVisualEffect(16);
            }
            if ((((CentralSurfacesImpl) StatusBarNotificationPresenter.this.mCentralSurfaces).mDisabled1 & 262144) != 0) {
                return false;
            }
            i++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:154:0x037e, code lost:
    
        if (r5 != false) goto L169;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x033c  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x03b4 A[LOOP:0: B:143:0x033d->B:157:0x03b4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x038e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0236  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean checkHeadsUp(NotificationEntry notificationEntry, boolean z) {
        boolean z2;
        boolean isSnoozed;
        boolean z3;
        boolean z4;
        Notification notification2;
        boolean z5;
        if (((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isStandalone()) {
            return false;
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        boolean z6 = notificationEntry.mIsHeadsUpByBriefExpanding;
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        if (!z6 && ((statusBarNotification.getNotification().semFlags & 8192) == 0 || !statusBarStateController.isExpanded() || ((StatusBarNotificationPresenter) ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.get(CentralSurfaces.class))).mPresenter).isCollapsing() || (statusBarStateController.getState() == 1 && !"com.android.cts.verifier".equals(notificationEntry.mSbn.getPackageName())))) {
            if ((statusBarNotification.getNotification().semFlags & 8) != 0) {
                Log.d("InterruptionStateProvider", "No  Heads up : DISABLE_HEADS_UP " + statusBarNotification.getKey());
                return false;
            }
            if ((statusBarNotification.getNotification().semFlags & 4096) == 0) {
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                    if (((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("edge_lighting").getIntValue() == 1) {
                        try {
                            z5 = ((INotificationManager) Dependency.get(INotificationManager.class)).isEdgeLightingAllowed(statusBarNotification.getPackageName(), statusBarNotification.getUid());
                        } catch (RemoteException e) {
                            Log.d("InterruptionStateProvider", " shouldHeadsUp.isEdgeLightingAllowed " + e.toString());
                        }
                        if (z5) {
                            Log.d("InterruptionStateProvider", " no Heads up : edgelighting enabled app. " + statusBarNotification.getKey());
                            return false;
                        }
                    }
                }
                z5 = false;
                if (z5) {
                }
            }
            if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON && ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).mDeviceModel.isSubScreen()) {
                Log.d("InterruptionStateProvider", " no Heads up : subscreen mode." + statusBarNotification.getKey());
                return false;
            }
            if (this.mKeyguardUpdateMonitor.isSimPinSecure()) {
                Log.d("InterruptionStateProvider", "No  Heads up : remote lock state: " + statusBarNotification.getKey());
                return false;
            }
            if (notificationEntry.mRanking.isSuspended()) {
                Log.d("InterruptionStateProvider", "No  Heads up : suspended: " + statusBarNotification.getKey());
                return false;
            }
        }
        if (statusBarStateController.isDozing()) {
            return false;
        }
        StatusBarNotification statusBarNotification2 = notificationEntry.mSbn;
        boolean z7 = this.mUseHeadsUp;
        NotificationInterruptLogger notificationInterruptLogger = this.mLogger;
        if (!z7) {
            if (!z) {
                return false;
            }
            notificationInterruptLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationInterruptLogger$logNoHeadsUpFeatureDisabled$2 notificationInterruptLogger$logNoHeadsUpFeatureDisabled$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoHeadsUpFeatureDisabled$2
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return "No heads up: no huns";
                }
            };
            LogBuffer logBuffer = notificationInterruptLogger.buffer;
            logBuffer.commit(logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logNoHeadsUpFeatureDisabled$2, null));
            return false;
        }
        if (!canAlertCommon(notificationEntry, z)) {
            return false;
        }
        StatusBarNotification statusBarNotification3 = notificationEntry.mSbn;
        if (!statusBarNotification3.isGroup() || !statusBarNotification3.getNotification().suppressAlertingDueToGrouping()) {
            if (!(SystemClock.elapsedRealtime() < notificationEntry.lastFullScreenIntentLaunchTime + 2000)) {
                z2 = true;
                if (z2 || !canAlertAwakeCommon(notificationEntry, z)) {
                    return false;
                }
                isSnoozed = this.mHeadsUpManager.isSnoozed(statusBarNotification2.getPackageName());
                boolean z8 = statusBarNotification2.getNotification().fullScreenIntent == null;
                if (!isSnoozed && !z8) {
                    if (!z) {
                        return false;
                    }
                    notificationInterruptLogger.getClass();
                    LogLevel logLevel2 = LogLevel.DEBUG;
                    NotificationInterruptLogger$logNoHeadsUpPackageSnoozed$2 notificationInterruptLogger$logNoHeadsUpPackageSnoozed$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoHeadsUpPackageSnoozed$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("No heads up: snoozed package: ", ((LogMessage) obj).getStr1());
                        }
                    };
                    LogBuffer logBuffer2 = notificationInterruptLogger.buffer;
                    LogMessage obtain = logBuffer2.obtain("InterruptionStateProvider", logLevel2, notificationInterruptLogger$logNoHeadsUpPackageSnoozed$2, null);
                    NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain, logBuffer2, obtain);
                    return false;
                }
                boolean z9 = statusBarStateController.getState() != 0;
                if (!notificationEntry.isBubble() && z9) {
                    if (!z) {
                        return false;
                    }
                    notificationInterruptLogger.getClass();
                    LogLevel logLevel3 = LogLevel.DEBUG;
                    NotificationInterruptLogger$logNoHeadsUpAlreadyBubbled$2 notificationInterruptLogger$logNoHeadsUpAlreadyBubbled$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoHeadsUpAlreadyBubbled$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("No heads up: in unlocked shade where notification is shown as a bubble: ", ((LogMessage) obj).getStr1());
                        }
                    };
                    LogBuffer logBuffer3 = notificationInterruptLogger.buffer;
                    LogMessage obtain2 = logBuffer3.obtain("InterruptionStateProvider", logLevel3, notificationInterruptLogger$logNoHeadsUpAlreadyBubbled$2, null);
                    NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain2, logBuffer3, obtain2);
                    return false;
                }
                if (!notificationEntry.shouldSuppressVisualEffect(16)) {
                    if (!z) {
                        return false;
                    }
                    notificationInterruptLogger.getClass();
                    LogLevel logLevel4 = LogLevel.DEBUG;
                    NotificationInterruptLogger$logNoHeadsUpSuppressedByDnd$2 notificationInterruptLogger$logNoHeadsUpSuppressedByDnd$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoHeadsUpSuppressedByDnd$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("No heads up: suppressed by DND: ", ((LogMessage) obj).getStr1());
                        }
                    };
                    LogBuffer logBuffer4 = notificationInterruptLogger.buffer;
                    LogMessage obtain3 = logBuffer4.obtain("InterruptionStateProvider", logLevel4, notificationInterruptLogger$logNoHeadsUpSuppressedByDnd$2, null);
                    NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain3, logBuffer4, obtain3);
                    return false;
                }
                if (notificationEntry.getImportance() < 4) {
                    if (!z) {
                        return false;
                    }
                    notificationInterruptLogger.getClass();
                    LogLevel logLevel5 = LogLevel.DEBUG;
                    NotificationInterruptLogger$logNoHeadsUpNotImportant$2 notificationInterruptLogger$logNoHeadsUpNotImportant$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoHeadsUpNotImportant$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("No heads up: unimportant notification: ", ((LogMessage) obj).getStr1());
                        }
                    };
                    LogBuffer logBuffer5 = notificationInterruptLogger.buffer;
                    LogMessage obtain4 = logBuffer5.obtain("InterruptionStateProvider", logLevel5, notificationInterruptLogger$logNoHeadsUpNotImportant$2, null);
                    NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain4, logBuffer5, obtain4);
                    return false;
                }
                if (!(this.mPowerManager.isScreenOn() && !statusBarStateController.isDreaming())) {
                    if (!z) {
                        return false;
                    }
                    notificationInterruptLogger.getClass();
                    LogLevel logLevel6 = LogLevel.DEBUG;
                    NotificationInterruptLogger$logNoHeadsUpNotInUse$2 notificationInterruptLogger$logNoHeadsUpNotInUse$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoHeadsUpNotInUse$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("No heads up: not in use: ", ((LogMessage) obj).getStr1());
                        }
                    };
                    LogBuffer logBuffer6 = notificationInterruptLogger.buffer;
                    LogMessage obtain5 = logBuffer6.obtain("InterruptionStateProvider", logLevel6, notificationInterruptLogger$logNoHeadsUpNotInUse$2, null);
                    NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain5, logBuffer6, obtain5);
                    return false;
                }
                NotifPipelineFlags notifPipelineFlags = this.mFlags;
                notifPipelineFlags.getClass();
                Flags.INSTANCE.getClass();
                boolean isEnabled = ((FeatureFlagsRelease) notifPipelineFlags.featureFlags).isEnabled(Flags.NO_HUN_FOR_OLD_WHEN);
                UiEventLogger uiEventLogger = this.mUiEventLogger;
                if (isEnabled && (notification2 = notificationEntry.mSbn.getNotification()) != null) {
                    long j = notification2.when;
                    long currentTimeMillis = System.currentTimeMillis() - j;
                    if (currentTimeMillis >= 86400000) {
                        if (j <= 0) {
                            if (z) {
                                this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry, j, currentTimeMillis, "when <= 0");
                            }
                        } else if (notification2.fullScreenIntent != null) {
                            if (z) {
                                this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry, j, currentTimeMillis, "full-screen intent");
                            }
                        } else if (notification2.isForegroundService()) {
                            if (z) {
                                this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry, j, currentTimeMillis, "foreground service");
                            }
                        } else {
                            if (!notification2.isUserInitiatedJob()) {
                                if (z) {
                                    notificationInterruptLogger.getClass();
                                    LogLevel logLevel7 = LogLevel.DEBUG;
                                    NotificationInterruptLogger$logNoHeadsUpOldWhen$2 notificationInterruptLogger$logNoHeadsUpOldWhen$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoHeadsUpOldWhen$2
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            LogMessage logMessage = (LogMessage) obj;
                                            long long1 = logMessage.getLong1();
                                            long long2 = logMessage.getLong2();
                                            String str1 = logMessage.getStr1();
                                            StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("No heads up: old when ", long1, " (age=");
                                            m17m.append(long2);
                                            m17m.append(" ms): ");
                                            m17m.append(str1);
                                            return m17m.toString();
                                        }
                                    };
                                    LogBuffer logBuffer7 = notificationInterruptLogger.buffer;
                                    LogMessage obtain6 = logBuffer7.obtain("InterruptionStateProvider", logLevel7, notificationInterruptLogger$logNoHeadsUpOldWhen$2, null);
                                    obtain6.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
                                    obtain6.setLong1(j);
                                    obtain6.setLong2(currentTimeMillis);
                                    logBuffer7.commit(obtain6);
                                }
                                uiEventLogger.log(NotificationInterruptEvent.HUN_SUPPRESSED_OLD_WHEN, notificationEntry.mSbn.getUid(), notificationEntry.mSbn.getPackageName());
                                z3 = true;
                                if (!z3) {
                                    int i = 0;
                                    while (true) {
                                        ArrayList arrayList = (ArrayList) this.mSuppressors;
                                        if (i >= arrayList.size()) {
                                            if (isSnoozed) {
                                                if (z) {
                                                    notificationInterruptLogger.getClass();
                                                    LogLevel logLevel8 = LogLevel.DEBUG;
                                                    C2852x85e096fd c2852x85e096fd = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logHeadsUpPackageSnoozeBypassedHasFsi$2
                                                        @Override // kotlin.jvm.functions.Function1
                                                        public final Object invoke(Object obj) {
                                                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("Heads up: package snooze bypassed because notification has full-screen intent: ", ((LogMessage) obj).getStr1());
                                                        }
                                                    };
                                                    LogBuffer logBuffer8 = notificationInterruptLogger.buffer;
                                                    LogMessage obtain7 = logBuffer8.obtain("InterruptionStateProvider", logLevel8, c2852x85e096fd, null);
                                                    NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain7, logBuffer8, obtain7);
                                                    uiEventLogger.log(NotificationInterruptEvent.HUN_SNOOZE_BYPASSED_POTENTIALLY_SUPPRESSED_FSI, notificationEntry.mSbn.getUid(), notificationEntry.mSbn.getPackageName());
                                                }
                                            } else if (z) {
                                                notificationInterruptLogger.getClass();
                                                LogLevel logLevel9 = LogLevel.DEBUG;
                                                NotificationInterruptLogger$logHeadsUp$2 notificationInterruptLogger$logHeadsUp$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logHeadsUp$2
                                                    @Override // kotlin.jvm.functions.Function1
                                                    public final Object invoke(Object obj) {
                                                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("Heads up: ", ((LogMessage) obj).getStr1());
                                                    }
                                                };
                                                LogBuffer logBuffer9 = notificationInterruptLogger.buffer;
                                                LogMessage obtain8 = logBuffer9.obtain("InterruptionStateProvider", logLevel9, notificationInterruptLogger$logHeadsUp$2, null);
                                                NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain8, logBuffer9, obtain8);
                                            }
                                            return true;
                                        }
                                        StatusBarNotificationPresenter.C31584 c31584 = (StatusBarNotificationPresenter.C31584) arrayList.get(i);
                                        c31584.getClass();
                                        StatusBarNotification statusBarNotification4 = notificationEntry.mSbn;
                                        StatusBarNotificationPresenter statusBarNotificationPresenter = StatusBarNotificationPresenter.this;
                                        if (((CentralSurfacesImpl) statusBarNotificationPresenter.mCentralSurfaces).isOccluded()) {
                                            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) statusBarNotificationPresenter.mLockscreenUserManager;
                                            boolean z10 = notificationLockscreenUserManagerImpl.isLockscreenPublicMode(notificationLockscreenUserManagerImpl.mCurrentUserId) || notificationLockscreenUserManagerImpl.isLockscreenPublicMode(statusBarNotification4.getUserId());
                                            boolean needsRedaction = notificationLockscreenUserManagerImpl.needsRedaction(notificationEntry);
                                            if (z10) {
                                            }
                                        }
                                        if (statusBarNotificationPresenter.mCommandQueue.panelsEnabled()) {
                                            z4 = false;
                                            if (z4) {
                                                i++;
                                            } else if (z) {
                                                notificationInterruptLogger.getClass();
                                                LogLevel logLevel10 = LogLevel.DEBUG;
                                                NotificationInterruptLogger$logNoHeadsUpSuppressedBy$2 notificationInterruptLogger$logNoHeadsUpSuppressedBy$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoHeadsUpSuppressedBy$2
                                                    @Override // kotlin.jvm.functions.Function1
                                                    public final Object invoke(Object obj) {
                                                        LogMessage logMessage = (LogMessage) obj;
                                                        return FontProvider$$ExternalSyntheticOutline0.m32m("No heads up: aborted by suppressor: ", logMessage.getStr2(), " sbnKey=", logMessage.getStr1());
                                                    }
                                                };
                                                LogBuffer logBuffer10 = notificationInterruptLogger.buffer;
                                                LogMessage obtain9 = logBuffer10.obtain("InterruptionStateProvider", logLevel10, notificationInterruptLogger$logNoHeadsUpSuppressedBy$2, null);
                                                obtain9.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
                                                obtain9.setStr2("StatusBarNotificationPresenter");
                                                logBuffer10.commit(obtain9);
                                            }
                                        }
                                        z4 = true;
                                        if (z4) {
                                        }
                                    }
                                }
                                return false;
                            }
                            if (z) {
                                this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry, j, currentTimeMillis, "user initiated job");
                            }
                        }
                    }
                }
                z3 = false;
                if (!z3) {
                }
                return false;
            }
            if (z) {
                notificationInterruptLogger.getClass();
                LogLevel logLevel11 = LogLevel.DEBUG;
                NotificationInterruptLogger$logNoAlertingRecentFullscreen$2 notificationInterruptLogger$logNoAlertingRecentFullscreen$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoAlertingRecentFullscreen$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("No alerting: recent fullscreen: ", ((LogMessage) obj).getStr1());
                    }
                };
                LogBuffer logBuffer11 = notificationInterruptLogger.buffer;
                LogMessage obtain10 = logBuffer11.obtain("InterruptionStateProvider", logLevel11, notificationInterruptLogger$logNoAlertingRecentFullscreen$2, null);
                NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain10, logBuffer11, obtain10);
            }
        } else if (z) {
            notificationInterruptLogger.getClass();
            LogLevel logLevel12 = LogLevel.DEBUG;
            NotificationInterruptLogger$logNoAlertingGroupAlertBehavior$2 notificationInterruptLogger$logNoAlertingGroupAlertBehavior$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoAlertingGroupAlertBehavior$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("No alerting: suppressed due to group alert behavior: ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer12 = notificationInterruptLogger.buffer;
            LogMessage obtain11 = logBuffer12.obtain("InterruptionStateProvider", logLevel12, notificationInterruptLogger$logNoAlertingGroupAlertBehavior$2, null);
            NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain11, logBuffer12, obtain11);
        }
        z2 = false;
        if (z2) {
            return false;
        }
        isSnoozed = this.mHeadsUpManager.isSnoozed(statusBarNotification2.getPackageName());
        if (statusBarNotification2.getNotification().fullScreenIntent == null) {
        }
        if (!isSnoozed) {
        }
        if (statusBarStateController.getState() != 0) {
        }
        if (!notificationEntry.isBubble()) {
        }
        if (!notificationEntry.shouldSuppressVisualEffect(16)) {
        }
    }
}
