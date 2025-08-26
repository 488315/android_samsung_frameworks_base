package com.android.systemui.statusbar.notification.interruption;

import android.app.ActivityTaskManager;
import android.app.INotificationManager;
import android.app.Notification;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerLogger;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.EventLog;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* loaded from: classes3.dex */
public class NotificationInterruptStateProviderImpl implements NotificationInterruptStateProvider {
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
    public final SecQSExpansionStateInteractor mSecQSExpansionStateInteractor;
    public final StatusBarStateController mStatusBarStateController;
    public final SystemClock mSystemClock;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker mUserTracker;
    public final List mSuppressors = new ArrayList();
    protected boolean mUseHeadsUp = false;

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

    public NotificationInterruptStateProviderImpl(PowerManager powerManager, AmbientDisplayConfiguration ambientDisplayConfiguration, BatteryController batteryController, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, HeadsUpManager headsUpManager, NotificationInterruptLogger notificationInterruptLogger, Handler handler, NotifPipelineFlags notifPipelineFlags, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, UiEventLogger uiEventLogger, UserTracker userTracker, DeviceProvisionedController deviceProvisionedController, SystemClock systemClock, GlobalSettings globalSettings, EventLog eventLog, Context context, Optional<Bubbles> optional, SecQSExpansionStateInteractor secQSExpansionStateInteractor) {
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
        this.mSecQSExpansionStateInteractor = secQSExpansionStateInteractor;
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
                NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda0 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(14);
                LogBuffer logBuffer = notificationInterruptLogger2.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).bool1 = z3;
                logBuffer.commit(logMessageObtain);
                NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl3 = NotificationInterruptStateProviderImpl.this;
                boolean z4 = notificationInterruptStateProviderImpl3.mUseHeadsUp;
                if (z2 == z4 || z4) {
                    return;
                }
                NotificationInterruptLogger notificationInterruptLogger3 = notificationInterruptStateProviderImpl3.mLogger;
                notificationInterruptLogger3.getClass();
                NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda02 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(13);
                LogBuffer logBuffer2 = notificationInterruptLogger3.buffer;
                logBuffer2.commit(logBuffer2.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda02, null));
                ((HeadsUpManagerImpl) NotificationInterruptStateProviderImpl.this.mHeadsUpManager).releaseAllImmediately();
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
                    this.mLogger.logNoAlertingSuppressedBy(notificationEntry, (NotificationInterruptSuppressor) ((ArrayList) this.mSuppressors).get(i), true);
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
            if (i < size) {
                if ((((DisableFlagsModel) StatusBarNotificationPresenter.this.mNotificationAlertsInteractor.disableFlagsInteractor.disableFlags.$$delegate_0.getValue()).disable1 & 262144) == 0) {
                    i++;
                } else if (z) {
                    notificationInterruptLogger.logNoAlertingSuppressedBy(notificationEntry, (NotificationInterruptSuppressor) ((ArrayList) this.mSuppressors).get(i), false);
                    return false;
                }
            } else if (notificationEntry.mRanking.isSuspended()) {
                if (z) {
                    notificationInterruptLogger.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda0 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(22);
                    LogBuffer logBuffer = notificationInterruptLogger.buffer;
                    LogMessage logMessageObtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda0, null);
                    ((LogMessageImpl) logMessageObtain).str1 = NotificationUtils.logKey(notificationEntry);
                    logBuffer.commit(logMessageObtain);
                    return false;
                }
            } else {
                if (!((KeyguardNotificationVisibilityProviderImpl) this.mKeyguardNotificationVisibilityProvider).shouldHideNotification(notificationEntry)) {
                    return true;
                }
                if (z) {
                    notificationInterruptLogger.getClass();
                    LogLevel logLevel2 = LogLevel.DEBUG;
                    NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda02 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(10);
                    LogBuffer logBuffer2 = notificationInterruptLogger.buffer;
                    LogMessage logMessageObtain2 = logBuffer2.obtain("InterruptionStateProvider", logLevel2, notificationInterruptLogger$$ExternalSyntheticLambda02, null);
                    ((LogMessageImpl) logMessageObtain2).str1 = NotificationUtils.logKey(notificationEntry);
                    logBuffer2.commit(logMessageObtain2);
                }
            }
        }
        return false;
    }

    public final boolean canHeadsUpCommonForFrontCoverScreen(NotificationEntry notificationEntry) {
        if (notificationEntry.mRanking.getImportance() >= 4) {
            int i = 0;
            while (true) {
                if (i < ((ArrayList) this.mSuppressors).size()) {
                    if (!((((DisableFlagsModel) StatusBarNotificationPresenter.this.mNotificationAlertsInteractor.disableFlagsInteractor.disableFlags.$$delegate_0.getValue()).disable1 & 262144) == 0)) {
                        break;
                    }
                    i++;
                } else if (this.mUseHeadsUp && !notificationEntry.shouldSuppressVisualEffect(16)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03ca  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x03db  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0255 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0280  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x029a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean checkHeadsUp(NotificationEntry notificationEntry, boolean z) {
        boolean z2;
        boolean z3;
        NotificationEntry notificationEntry2 = notificationEntry;
        StatusBarNotification statusBarNotification = notificationEntry2.mSbn;
        boolean z4 = notificationEntry2.mIsHeadsUpByBriefExpanding;
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        if (!z4 && ((statusBarNotification.getNotification().semFlags & 8192) == 0 || !statusBarStateController.isExpanded() || ((StatusBarNotificationPresenter) ((NotificationPresenter) ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class))).mPresenterLazy.get())).isCollapsing() || (statusBarStateController.getState() == 1 && !"com.android.cts.verifier".equals(notificationEntry2.mSbn.getPackageName())))) {
            if ((statusBarNotification.getNotification().semFlags & 8) != 0) {
                Log.d("InterruptionStateProvider", "No  Heads up : DISABLE_HEADS_UP " + statusBarNotification.getKey());
                return false;
            }
            if ((statusBarNotification.getNotification().semFlags & 4096) == 0 && isEdgeLightingAllowed(statusBarNotification)) {
                Log.d("InterruptionStateProvider", " no Heads up : edgelighting enabled app. " + statusBarNotification.getKey());
                return false;
            }
            if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON && ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel.isSubScreen()) {
                Log.d("InterruptionStateProvider", " no Heads up : subscreen mode." + statusBarNotification.getKey());
                return false;
            }
            if (this.mKeyguardUpdateMonitor.isSimPinSecure()) {
                Log.d("InterruptionStateProvider", "No  Heads up : remote lock state: " + statusBarNotification.getKey());
                return false;
            }
            if (notificationEntry2.mRanking.isSuspended()) {
                Log.d("InterruptionStateProvider", "No  Heads up : suspended: " + statusBarNotification.getKey());
                return false;
            }
            if (!notificationEntry2.isOngoingActivity() || !notificationEntry2.isPromotedState()) {
            }
        } else if (!statusBarStateController.isDozing()) {
            StatusBarNotification statusBarNotification2 = notificationEntry2.mSbn;
            boolean z5 = this.mUseHeadsUp;
            NotificationInterruptLogger notificationInterruptLogger = this.mLogger;
            if (z5) {
                if (canAlertCommon(notificationEntry, z)) {
                    StatusBarNotification statusBarNotification3 = notificationEntry2.mSbn;
                    if (statusBarNotification3.getNotification().isSilent()) {
                        if (z) {
                            notificationInterruptLogger.getClass();
                            LogLevel logLevel = LogLevel.DEBUG;
                            NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda0 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(11);
                            LogBuffer logBuffer = notificationInterruptLogger.buffer;
                            LogMessage logMessageObtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda0, null);
                            ((LogMessageImpl) logMessageObtain).str1 = NotificationUtils.logKey(notificationEntry2);
                            logBuffer.commit(logMessageObtain);
                            return false;
                        }
                    } else if (statusBarNotification3.isGroup() && statusBarNotification3.getNotification().suppressAlertingDueToGrouping()) {
                        if (z) {
                            notificationInterruptLogger.getClass();
                            LogLevel logLevel2 = LogLevel.DEBUG;
                            NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda02 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(8);
                            LogBuffer logBuffer2 = notificationInterruptLogger.buffer;
                            LogMessage logMessageObtain2 = logBuffer2.obtain("InterruptionStateProvider", logLevel2, notificationInterruptLogger$$ExternalSyntheticLambda02, null);
                            ((LogMessageImpl) logMessageObtain2).str1 = NotificationUtils.logKey(notificationEntry2);
                            logBuffer2.commit(logMessageObtain2);
                            return false;
                        }
                    } else if (android.os.SystemClock.elapsedRealtime() < notificationEntry2.lastFullScreenIntentLaunchTime + DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY) {
                        if (z) {
                            notificationInterruptLogger.getClass();
                            LogLevel logLevel3 = LogLevel.DEBUG;
                            NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda03 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(20);
                            LogBuffer logBuffer3 = notificationInterruptLogger.buffer;
                            LogMessage logMessageObtain3 = logBuffer3.obtain("InterruptionStateProvider", logLevel3, notificationInterruptLogger$$ExternalSyntheticLambda03, null);
                            ((LogMessageImpl) logMessageObtain3).str1 = NotificationUtils.logKey(notificationEntry2);
                            logBuffer3.commit(logMessageObtain3);
                            return false;
                        }
                    } else if (canAlertAwakeCommon(notificationEntry, z)) {
                        String packageName = statusBarNotification2.getPackageName();
                        HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) this.mHeadsUpManager;
                        String str = headsUpManagerImpl.mUser + "," + packageName;
                        Long l = (Long) headsUpManagerImpl.mSnoozedPackages.get(str);
                        if (l != null) {
                            long jLongValue = l.longValue();
                            long jElapsedRealtime = headsUpManagerImpl.mSystemClock.elapsedRealtime();
                            HeadsUpManagerLogger headsUpManagerLogger = headsUpManagerImpl.mLogger;
                            if (jLongValue > jElapsedRealtime) {
                                headsUpManagerLogger.getClass();
                                LogLevel logLevel4 = LogLevel.INFO;
                                HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(25);
                                LogBuffer logBuffer4 = headsUpManagerLogger.buffer;
                                LogMessage logMessageObtain4 = logBuffer4.obtain("HeadsUpManager", logLevel4, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
                                ((LogMessageImpl) logMessageObtain4).str1 = str;
                                logBuffer4.commit(logMessageObtain4);
                                z2 = true;
                                boolean z6 = statusBarNotification2.getNotification().fullScreenIntent != null;
                                if (!z2 || z6) {
                                    boolean z7 = statusBarStateController.getState() != 0;
                                    if (this.mBubbles.isPresent()) {
                                        z3 = false;
                                    } else {
                                        BubbleController.this.getClass();
                                        z3 = true;
                                    }
                                    if (!notificationEntry2.isBubble() && z7 && z3) {
                                        if (z) {
                                            notificationInterruptLogger.getClass();
                                            LogLevel logLevel5 = LogLevel.DEBUG;
                                            NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda04 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(7);
                                            LogBuffer logBuffer5 = notificationInterruptLogger.buffer;
                                            LogMessage logMessageObtain5 = logBuffer5.obtain("InterruptionStateProvider", logLevel5, notificationInterruptLogger$$ExternalSyntheticLambda04, null);
                                            ((LogMessageImpl) logMessageObtain5).str1 = NotificationUtils.logKey(notificationEntry2);
                                            logBuffer5.commit(logMessageObtain5);
                                            return false;
                                        }
                                    } else if (notificationEntry2.shouldSuppressVisualEffect(16)) {
                                        if (notificationEntry2.mRanking.getImportance() >= 4) {
                                            if (this.mPowerManager.isScreenOn() && !statusBarStateController.isDreaming()) {
                                                Notification notification2 = notificationEntry2.mSbn.getNotification();
                                                if (notification2 != null) {
                                                    long when = notification2.getWhen();
                                                    long jCurrentTimeMillis = this.mSystemClock.currentTimeMillis() - when;
                                                    if (jCurrentTimeMillis >= 86400000) {
                                                        if (when <= 0) {
                                                            if (z) {
                                                                this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry2, when, jCurrentTimeMillis, "when <= 0");
                                                            }
                                                        } else if (notification2.fullScreenIntent != null) {
                                                            if (z) {
                                                                this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry, when, jCurrentTimeMillis, "full-screen intent");
                                                            }
                                                        } else if (!notification2.isForegroundService()) {
                                                            if (!notification2.isUserInitiatedJob()) {
                                                                if (z) {
                                                                    notificationInterruptLogger.getClass();
                                                                    LogLevel logLevel6 = LogLevel.DEBUG;
                                                                    NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda05 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(17);
                                                                    LogBuffer logBuffer6 = notificationInterruptLogger.buffer;
                                                                    LogMessage logMessageObtain6 = logBuffer6.obtain("InterruptionStateProvider", logLevel6, notificationInterruptLogger$$ExternalSyntheticLambda05, null);
                                                                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain6;
                                                                    logMessageImpl.str1 = NotificationUtils.logKey(notificationEntry);
                                                                    logMessageImpl.long1 = when;
                                                                    logMessageImpl.long2 = jCurrentTimeMillis;
                                                                    logBuffer6.commit(logMessageObtain6);
                                                                }
                                                                this.mUiEventLogger.log(NotificationInterruptEvent.HUN_SUPPRESSED_OLD_WHEN, notificationEntry.mSbn.getUid(), notificationEntry.mSbn.getPackageName());
                                                                return false;
                                                            }
                                                            if (z) {
                                                                notificationEntry2 = notificationEntry;
                                                                this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry2, when, jCurrentTimeMillis, "user initiated job");
                                                            }
                                                        } else if (z) {
                                                            this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry, when, jCurrentTimeMillis, "foreground service");
                                                        }
                                                        notificationEntry2 = notificationEntry;
                                                    }
                                                }
                                                for (int i = 0; i < ((ArrayList) this.mSuppressors).size(); i++) {
                                                    StatusBarNotificationPresenter.AnonymousClass3 anonymousClass3 = (StatusBarNotificationPresenter.AnonymousClass3) ((NotificationInterruptSuppressor) ((ArrayList) this.mSuppressors).get(i));
                                                    anonymousClass3.getClass();
                                                    StatusBarNotification statusBarNotification4 = notificationEntry2.mSbn;
                                                    StatusBarNotificationPresenter statusBarNotificationPresenter = StatusBarNotificationPresenter.this;
                                                    if (((KeyguardStateControllerImpl) statusBarNotificationPresenter.mKeyguardStateController).mOccluded) {
                                                        NotificationLockscreenUserManager notificationLockscreenUserManager = statusBarNotificationPresenter.mLockscreenUserManager;
                                                        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager;
                                                        if (!notificationLockscreenUserManagerImpl.isLockscreenPublicMode(notificationLockscreenUserManagerImpl.mCurrentUserId)) {
                                                            boolean z8 = ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).isLockscreenPublicMode(statusBarNotification4.getUserId());
                                                            boolean z9 = ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).getRedactionType(notificationEntry2) != 0;
                                                            if (!z8 || !z9) {
                                                                if (statusBarNotificationPresenter.mCommandQueue.panelsEnabled()) {
                                                                }
                                                            }
                                                            if (z) {
                                                                NotificationInterruptSuppressor notificationInterruptSuppressor = (NotificationInterruptSuppressor) ((ArrayList) this.mSuppressors).get(i);
                                                                notificationInterruptLogger.getClass();
                                                                LogLevel logLevel7 = LogLevel.DEBUG;
                                                                NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda06 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(12);
                                                                LogBuffer logBuffer7 = notificationInterruptLogger.buffer;
                                                                LogMessage logMessageObtain7 = logBuffer7.obtain("InterruptionStateProvider", logLevel7, notificationInterruptLogger$$ExternalSyntheticLambda06, null);
                                                                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain7;
                                                                logMessageImpl2.str1 = NotificationUtils.logKey(notificationEntry2);
                                                                notificationInterruptSuppressor.getClass();
                                                                logMessageImpl2.str2 = "StatusBarNotificationPresenter";
                                                                logBuffer7.commit(logMessageObtain7);
                                                                return false;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (((Boolean) this.mSecQSExpansionStateInteractor.getRepository().expanded.$$delegate_0.getValue()).booleanValue()) {
                                                    if (z) {
                                                        notificationInterruptLogger.getClass();
                                                        LogLevel logLevel8 = LogLevel.DEBUG;
                                                        NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda07 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(16);
                                                        LogBuffer logBuffer8 = notificationInterruptLogger.buffer;
                                                        LogMessage logMessageObtain8 = logBuffer8.obtain("InterruptionStateProvider", logLevel8, notificationInterruptLogger$$ExternalSyntheticLambda07, null);
                                                        ((LogMessageImpl) logMessageObtain8).str1 = NotificationUtils.logKey(notificationEntry2);
                                                        logBuffer8.commit(logMessageObtain8);
                                                    }
                                                    notificationEntry2.mWillBeHUN = true;
                                                    return false;
                                                }
                                                if (z2) {
                                                    if (z) {
                                                        notificationInterruptLogger.getClass();
                                                        LogLevel logLevel9 = LogLevel.DEBUG;
                                                        NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda08 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(6);
                                                        LogBuffer logBuffer9 = notificationInterruptLogger.buffer;
                                                        LogMessage logMessageObtain9 = logBuffer9.obtain("InterruptionStateProvider", logLevel9, notificationInterruptLogger$$ExternalSyntheticLambda08, null);
                                                        ((LogMessageImpl) logMessageObtain9).str1 = NotificationUtils.logKey(notificationEntry2);
                                                        logBuffer9.commit(logMessageObtain9);
                                                        this.mUiEventLogger.log(NotificationInterruptEvent.HUN_SNOOZE_BYPASSED_POTENTIALLY_SUPPRESSED_FSI, notificationEntry2.mSbn.getUid(), notificationEntry2.mSbn.getPackageName());
                                                        return true;
                                                    }
                                                } else if (z) {
                                                    notificationInterruptLogger.getClass();
                                                    LogLevel logLevel10 = LogLevel.DEBUG;
                                                    NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda09 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(18);
                                                    LogBuffer logBuffer10 = notificationInterruptLogger.buffer;
                                                    LogMessage logMessageObtain10 = logBuffer10.obtain("InterruptionStateProvider", logLevel10, notificationInterruptLogger$$ExternalSyntheticLambda09, null);
                                                    ((LogMessageImpl) logMessageObtain10).str1 = NotificationUtils.logKey(notificationEntry2);
                                                    logBuffer10.commit(logMessageObtain10);
                                                }
                                                return true;
                                            }
                                            if (z) {
                                                notificationInterruptLogger.getClass();
                                                LogLevel logLevel11 = LogLevel.DEBUG;
                                                NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda010 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(19);
                                                LogBuffer logBuffer11 = notificationInterruptLogger.buffer;
                                                LogMessage logMessageObtain11 = logBuffer11.obtain("InterruptionStateProvider", logLevel11, notificationInterruptLogger$$ExternalSyntheticLambda010, null);
                                                ((LogMessageImpl) logMessageObtain11).str1 = NotificationUtils.logKey(notificationEntry2);
                                                logBuffer11.commit(logMessageObtain11);
                                            }
                                        } else if (z) {
                                            notificationInterruptLogger.getClass();
                                            LogLevel logLevel12 = LogLevel.DEBUG;
                                            NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda011 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(3);
                                            LogBuffer logBuffer12 = notificationInterruptLogger.buffer;
                                            LogMessage logMessageObtain12 = logBuffer12.obtain("InterruptionStateProvider", logLevel12, notificationInterruptLogger$$ExternalSyntheticLambda011, null);
                                            ((LogMessageImpl) logMessageObtain12).str1 = NotificationUtils.logKey(notificationEntry2);
                                            logBuffer12.commit(logMessageObtain12);
                                            return false;
                                        }
                                    } else if (z) {
                                        notificationInterruptLogger.getClass();
                                        LogLevel logLevel13 = LogLevel.DEBUG;
                                        NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda012 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(1);
                                        LogBuffer logBuffer13 = notificationInterruptLogger.buffer;
                                        LogMessage logMessageObtain13 = logBuffer13.obtain("InterruptionStateProvider", logLevel13, notificationInterruptLogger$$ExternalSyntheticLambda012, null);
                                        ((LogMessageImpl) logMessageObtain13).str1 = NotificationUtils.logKey(notificationEntry2);
                                        logBuffer13.commit(logMessageObtain13);
                                        return false;
                                    }
                                } else if (z) {
                                    notificationInterruptLogger.getClass();
                                    LogLevel logLevel14 = LogLevel.DEBUG;
                                    NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda013 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(23);
                                    LogBuffer logBuffer14 = notificationInterruptLogger.buffer;
                                    LogMessage logMessageObtain14 = logBuffer14.obtain("InterruptionStateProvider", logLevel14, notificationInterruptLogger$$ExternalSyntheticLambda013, null);
                                    ((LogMessageImpl) logMessageObtain14).str1 = NotificationUtils.logKey(notificationEntry2);
                                    logBuffer14.commit(logMessageObtain14);
                                    return false;
                                }
                            } else {
                                headsUpManagerLogger.getClass();
                                LogLevel logLevel15 = LogLevel.INFO;
                                HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda02 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(23);
                                LogBuffer logBuffer15 = headsUpManagerLogger.buffer;
                                LogMessage logMessageObtain15 = logBuffer15.obtain("HeadsUpManager", logLevel15, headsUpManagerLogger$$ExternalSyntheticLambda02, null);
                                ((LogMessageImpl) logMessageObtain15).str1 = str;
                                logBuffer15.commit(logMessageObtain15);
                                headsUpManagerImpl.mSnoozedPackages.remove(str);
                                z2 = false;
                                if (statusBarNotification2.getNotification().fullScreenIntent != null) {
                                }
                                if (z2) {
                                    if (statusBarStateController.getState() != 0) {
                                    }
                                    if (this.mBubbles.isPresent()) {
                                    }
                                    if (!notificationEntry2.isBubble()) {
                                        if (notificationEntry2.shouldSuppressVisualEffect(16)) {
                                        }
                                    }
                                }
                            }
                        } else {
                            z2 = false;
                            if (statusBarNotification2.getNotification().fullScreenIntent != null) {
                            }
                            if (z2) {
                            }
                        }
                    }
                }
            } else if (z) {
                notificationInterruptLogger.getClass();
                LogLevel logLevel16 = LogLevel.DEBUG;
                NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda014 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(0);
                LogBuffer logBuffer16 = notificationInterruptLogger.buffer;
                logBuffer16.commit(logBuffer16.obtain("InterruptionStateProvider", logLevel16, notificationInterruptLogger$$ExternalSyntheticLambda014, null));
                return false;
            }
        }
        return false;
    }

    public final NotificationInterruptStateProvider.FullScreenIntentDecision getFullScreenIntentDecision(NotificationEntry notificationEntry) {
        if (notificationEntry.mSbn.getNotification().fullScreenIntent == null) {
            return notificationEntry.isStickyAndNotDemoted() ? NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SHOW_STICKY_HUN : NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FULL_SCREEN_INTENT;
        }
        boolean zShouldSuppressVisualEffect = notificationEntry.shouldSuppressVisualEffect(4);
        if (notificationEntry.mRanking.getImportance() < 4) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NOT_IMPORTANT_ENOUGH, zShouldSuppressVisualEffect);
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        if (statusBarNotification.isGroup() && statusBarNotification.getNotification().suppressAlertingDueToGrouping()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR, zShouldSuppressVisualEffect);
        }
        if (statusBarNotification.getNotification().isSilent()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_SILENT_NOTIFICATION, zShouldSuppressVisualEffect);
        }
        Notification.BubbleMetadata bubbleMetadata = statusBarNotification.getNotification().getBubbleMetadata();
        if (bubbleMetadata != null && bubbleMetadata.isNotificationSuppressed()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_BUBBLE_METADATA, zShouldSuppressVisualEffect);
        }
        if (notificationEntry.mRanking.isSuspended()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUSPENDED, zShouldSuppressVisualEffect);
        }
        if (!this.mPowerManager.isInteractive()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_DEVICE_NOT_INTERACTIVE, zShouldSuppressVisualEffect);
        }
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        if (statusBarStateController.isDreaming()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_DEVICE_IS_DREAMING, zShouldSuppressVisualEffect);
        }
        if (statusBarStateController.getState() == 1) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_KEYGUARD_SHOWING, zShouldSuppressVisualEffect);
        }
        if (checkHeadsUp(notificationEntry, false)) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_EXPECTED_TO_HUN, zShouldSuppressVisualEffect);
        }
        if ((notificationEntry.mSbn.getNotification().semFlags & 8192) != 0 || (notificationEntry.mSbn.getNotification().semFlags & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_EXPECTED_TO_BRIEF, zShouldSuppressVisualEffect);
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing) {
            return keyguardStateControllerImpl.mOccluded ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_KEYGUARD_OCCLUDED, zShouldSuppressVisualEffect) : getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_LOCKED_SHADE, zShouldSuppressVisualEffect);
        }
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.mDeviceProvisionedController;
        return !deviceProvisionedControllerImpl.deviceProvisioned.get() ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_NOT_PROVISIONED, zShouldSuppressVisualEffect) : !deviceProvisionedControllerImpl.isCurrentUserSetup() ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_USER_SETUP_INCOMPLETE, zShouldSuppressVisualEffect) : (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel.isSubScreen()) ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_LARGE_COVER_SCREEN, zShouldSuppressVisualEffect) : getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NO_HUN_OR_KEYGUARD, zShouldSuppressVisualEffect);
    }

    public final boolean isEdgeLightingAllowed(StatusBarNotification statusBarNotification) {
        if (!SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION") || !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isPopStyleBrief()) {
            return false;
        }
        try {
            return ((INotificationManager) Dependency.sDependency.getDependencyInner(INotificationManager.class)).isEdgeLightingAllowed(statusBarNotification.getPackageName(), statusBarNotification.getUid());
        } catch (RemoteException e) {
            Log.d("InterruptionStateProvider", " shouldHeadsUp.isEdgeLightingAllowed " + e.toString());
            return false;
        }
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
                String strName = fullScreenIntentDecision.name();
                notificationInterruptLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda0 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(5);
                LogBuffer logBuffer = notificationInterruptLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                logMessageImpl.str2 = strName;
                logBuffer.commit(logMessageObtain);
                return;
            }
            String strName2 = fullScreenIntentDecision.name();
            notificationInterruptLogger.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda02 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer2 = notificationInterruptLogger.buffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("InterruptionStateProvider", logLevel2, notificationInterruptLogger$$ExternalSyntheticLambda02, null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
            logMessageImpl2.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logMessageImpl2.str2 = strName2;
            logBuffer2.commit(logMessageObtain2);
        }
    }

    public final boolean shouldBubbleUp(NotificationEntry notificationEntry) {
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        if (canAlertCommon(notificationEntry, false) && canAlertAwakeCommon(notificationEntry, false)) {
            boolean zCanBubble = notificationEntry.mRanking.canBubble();
            NotificationInterruptLogger notificationInterruptLogger = this.mLogger;
            if (!zCanBubble) {
                notificationInterruptLogger.getClass();
                return false;
            }
            Notification.BubbleMetadata bubbleMetadata = notificationEntry.mBubbleMetadata;
            if (bubbleMetadata == null || (bubbleMetadata.getShortcutId() == null && notificationEntry.mBubbleMetadata.getIntent() == null)) {
                notificationInterruptLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda0 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(15);
                LogBuffer logBuffer = notificationInterruptLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = NotificationUtils.logKey(notificationEntry);
                logBuffer.commit(logMessageObtain);
                return false;
            }
            if (!ActivityTaskManager.supportsMultiWindow(this.mContext)) {
                Log.d("InterruptionStateProvider", "No bubble up: notification: " + statusBarNotification.getKey() + " doesn't mw");
                return false;
            }
            if (!MultiWindowManager.getInstance().isMultiWindowBlockListApp(statusBarNotification.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
