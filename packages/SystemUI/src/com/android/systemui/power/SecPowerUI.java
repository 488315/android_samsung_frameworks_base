package com.android.systemui.power;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ForegroundServiceStartNotAllowedException;
import android.app.SemStatusBarManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.hardware.scontext.SContextEvent;
import android.hardware.scontext.SContextListener;
import android.hardware.scontext.SContextManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Slog;
import android.view.IWindowManager;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.keyguardstatusview.NowBarItem;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.power.SecPowerUI;
import com.android.systemui.power.constants.PowerUiConstants;
import com.android.systemui.power.listener.ChargerAnimationListener;
import com.android.systemui.power.tips.TipsManager;
import com.android.systemui.power.utils.BatteryProtectionUtils;
import com.android.systemui.power.utils.ChargerAnimationUtils;
import com.android.systemui.power.utils.DisplayUtils;
import com.android.systemui.power.utils.PowerUtils;
import com.android.systemui.power.utils.SettingsUtils;
import com.android.systemui.power.view.ChargerAnimationView;
import com.android.systemui.power.view.ChargerCoverNowBarView;
import com.android.systemui.power.view.ChargerFlipCoverView;
import com.android.systemui.power.view.ChargerNowBarView;
import com.android.systemui.power.view.WirelessMisalignView;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.events.BatteryEvent;
import com.android.systemui.statusbar.events.SystemEventCoordinator;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCounterCoordinator;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.google.common.util.concurrent.ListenableFuture;
import com.samsung.android.cover.CoverState;
import com.samsung.android.hardware.SemBatteryUtils;
import com.samsung.android.knox.custom.SettingsManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.IMSParameter;
import com.sec.ims.settings.ImsProfile;
import java.io.PrintWriter;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public class SecPowerUI implements CoreStartable, ConfigurationController.ConfigurationListener, CommandQueue.Callbacks, ChargerAnimationListener {
    public static final boolean DEBUG = Log.isLoggable("PowerUI", 3);
    public final AnonymousClass4 mAfterChargingNoticeTask;
    public int mBatteryChargingType;
    public int mBatteryCurrentEvent;
    public int mBatteryHealth;
    public PowerManager.WakeLock mBatteryHealthInterruptionPartialWakeLock;
    public PowerManager.WakeLock mBatteryHealthInterruptionScreenDimWakeLock;
    public boolean mBatteryHighVoltageCharger;
    public int mBatteryLevel;
    public int mBatteryMiscEvent;
    public int mBatteryOnline;
    public int mBatteryOverheatLevel;
    public boolean mBatterySlowCharger;
    public int mBatteryStatus;
    public int mBatterySwellingMode;
    public boolean mBatteryWaterConnector;
    public boolean mBootCompleted;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public int mCallState;
    public ChargerAnimationView mChargerAnimationView;
    public WindowManager.LayoutParams mChargerAnimationWindowLp;
    public WindowManager mChargerAnimationWindowManager;
    public ChargerCoverNowBarView mChargerCoverExpandSubScreenView;
    public ChargerCoverNowBarView mChargerCoverNormalSubScreenView;
    public ChargerFlipCoverView mChargerFlipCoverView;
    public ChargerNowBarView mChargerNowBarView;
    public String mChargingStartTime;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public BatteryStateData mCurrentBatteryStateData;
    public String mCurrentChargingAnimation;
    public boolean mDismissBatteryHealthInterruptionWarning;
    public final DozeChargingHelper mDozeChargingHelper;
    public final FaceWidgetNotificationControllerWrapper mFaceWidgetNotificationControllerWrapper;
    public boolean mFullyConnected;
    public final Handler mHandler;
    public boolean mIsAfterAdaptiveProtection;
    public boolean mIsChangedBatteryProtectionOnCharging;
    public boolean mIsChangedStringAfterCharging;
    public boolean mIsDeviceMoving;
    public boolean mIsHiccupState;
    public boolean mIsMotionDetectionSupported;
    public boolean mIsRunningLowBatteryTask;
    public boolean mIsRunningStopPowerSoundTask;
    public boolean mIsSContextEnabled;
    public boolean mIsSContextListenerRegistered;
    public boolean mIsShutdownTaskDelayed;
    public boolean mIsWirelessMisalignTask;
    public boolean mIsWirelessOverheat;
    public BatteryStateData mLastBatteryStateData;
    public ListenableFuture mLastShowWarningTask;
    public int mLowBatteryAlertCloseLevel;
    public final int[] mLowBatteryReminderLevels;
    public final AnonymousClass1 mLowBatteryWarningTask;
    public int mLtcHighSocThreshold;
    public int mLtcReleaseThreshold;
    public final AnonymousClass2 mOverheatShutdownWarningTask;
    public final AnonymousClass3 mPhoneStateListener;
    public int mPlugType;
    public final PowerManager mPowerManager;
    public int mProtectBatteryValue;
    public int mProtectionEvent;
    public final Receiver mReceiver;
    public final ContentResolver mResolver;
    public final AnonymousClass5 mRestoreDisplayStateTask;
    public final AnonymousClass7 mSContextListener;
    public SContextManager mSContextManager;
    public String mSkipChargingUiMsg;
    public String mSleepChargingEvent;
    public final AnonymousClass6 mStopPowerSoundTask;
    public int mSuperFastCharger;
    public final SystemEventCoordinator mSystemEventCoordinator;
    public boolean mTemperatureHiccupState;
    public int mTurnOffPsmLevel;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final AnonymousClass9 mWakefulnessObserver;
    public final SecWarningsUI mWarnings;
    public boolean mWirelessFodState;
    public final SecPowerUI$$ExternalSyntheticLambda0 mWirelessMisalignTimeoutTask;
    public WirelessMisalignView mWirelessMisalignView;
    public PowerManager.WakeLock mWirelessMisalignWakeLock;
    public WindowManager mWirelessMisalignWindowManager;
    public final AnonymousClass12 maximumThresholdObserver;
    public final AnonymousClass13 ownerSetupWizardObserver;
    public final AnonymousClass11 protectBatteryObserver;

    public final class Receiver extends BroadcastReceiver {
        public boolean mHasReceivedBattery = false;

        public Receiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:178:0x0488  */
        /* JADX WARN: Removed duplicated region for block: B:194:0x04eb  */
        /* JADX WARN: Removed duplicated region for block: B:346:0x0a53 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:352:0x0a71  */
        /* JADX WARN: Removed duplicated region for block: B:353:0x0a86  */
        /* JADX WARN: Removed duplicated region for block: B:360:0x0ab1  */
        /* JADX WARN: Removed duplicated region for block: B:361:0x0ab3  */
        /* JADX WARN: Removed duplicated region for block: B:364:0x0ab9  */
        /* JADX WARN: Removed duplicated region for block: B:365:0x0abb  */
        /* JADX WARN: Removed duplicated region for block: B:368:0x0ac7  */
        /* JADX WARN: Removed duplicated region for block: B:380:0x0b0f  */
        /* JADX WARN: Removed duplicated region for block: B:383:0x0b1b  */
        /* JADX WARN: Removed duplicated region for block: B:386:0x0b2d  */
        /* JADX WARN: Removed duplicated region for block: B:398:0x0b59  */
        /* JADX WARN: Removed duplicated region for block: B:409:0x0b86  */
        /* JADX WARN: Removed duplicated region for block: B:421:0x0ba5 A[PHI: r7
          0x0ba5: PHI (r7v43 int) = (r7v42 int), (r7v44 int) binds: [B:416:0x0b9d, B:420:0x0ba3] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:424:0x0bc0  */
        /* JADX WARN: Removed duplicated region for block: B:426:0x0bc4  */
        /* JADX WARN: Removed duplicated region for block: B:429:0x0bd4  */
        /* JADX WARN: Removed duplicated region for block: B:432:0x0bdd  */
        /* JADX WARN: Removed duplicated region for block: B:435:0x0be6  */
        /* JADX WARN: Removed duplicated region for block: B:436:0x0bec  */
        /* JADX WARN: Removed duplicated region for block: B:441:0x0bf9  */
        /* JADX WARN: Removed duplicated region for block: B:444:0x0c02  */
        /* JADX WARN: Removed duplicated region for block: B:522:? A[RETURN, SYNTHETIC] */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onReceive(Context context, Intent intent) throws Resources.NotFoundException, NumberFormatException {
            String str;
            boolean z;
            boolean z2;
            int i;
            String str2;
            int i2;
            int i3;
            char c;
            int i4;
            int i5;
            boolean z3;
            int i6;
            int i7;
            int i8;
            int i9;
            String str3;
            ObjectAnimator duration;
            SecPowerUI secPowerUI;
            SecPowerUI secPowerUI2;
            int i10;
            int i11;
            int i12;
            boolean z4;
            SecPowerUI secPowerUI3;
            SecPowerUI secPowerUI4;
            int i13;
            SecPowerUI secPowerUI5;
            int i14;
            int i15;
            String action = intent.getAction();
            if (!"android.intent.action.BATTERY_CHANGED".equals(action)) {
                if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
                    SecPowerUI.m2890$$Nest$mupdateBatteryNotificationLanguage(SecPowerUI.this);
                    return;
                }
                if ("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT".equals(action)) {
                    SecPowerUI.m2887$$Nest$mcheckWirelessChargingFodStatus(SecPowerUI.this, intent);
                    SecPowerUI.m2886$$Nest$mcheckWaterDetectionStatus(SecPowerUI.this, intent);
                    SecPowerUI.m2885$$Nest$mcheckUsbDamageDetectionStatus(SecPowerUI.this, intent);
                    return;
                }
                if ("com.samsung.intent.action.KSO_SHOW_POPUP".equals(action)) {
                    ((SecPowerNotificationWarnings) SecPowerUI.this.mWarnings).showUnintentionalLcdOnNotice();
                    return;
                }
                if ("com.samsung.intent.action.KSO_CLOSE_POPUP".equals(action)) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) SecPowerUI.this.mWarnings;
                    secPowerNotificationWarnings.getClass();
                    Log.d("PowerUI.Notification", "dismissUnintentionallyLcdOnNotice");
                    secPowerNotificationWarnings.dismissUnintentionalLcdOnWindow();
                    return;
                }
                if ("com.samsung.systemui.power.action.WATER_ALERT_SOUND_TEST".equals(action)) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings2 = (SecPowerNotificationWarnings) SecPowerUI.this.mWarnings;
                    if (secPowerNotificationWarnings2.mIsInCall) {
                        return;
                    }
                    secPowerNotificationWarnings2.playPowerSound(1600);
                    return;
                }
                if ("com.samsung.CHECK_COOLDOWN_LEVEL".equals(action)) {
                    SecPowerUI.m2879$$Nest$mcheckCoolDownStatus(SecPowerUI.this, intent);
                    return;
                }
                if (PopupUIUtil.ACTION_BOOT_COMPLETED.equals(action) || "com.sec.android.intent.action.SAFEMODE_ENABLE".equals(action)) {
                    if (!SecPowerUI.this.mBootCompleted) {
                        SecPowerUI.this.checkOverheatShutdownHappened();
                    }
                    try {
                        boolean zIsSafeModeEnabled = IWindowManager.Stub.asInterface(ServiceManager.getService("window")).isSafeModeEnabled();
                        Log.i("PowerUI", "safe mode case : " + zIsSafeModeEnabled);
                        if (zIsSafeModeEnabled && !ImsProfile.PDN_EMERGENCY.equals(SystemProperties.get("persist.sys.emergency_reset"))) {
                            SecPowerNotificationWarnings secPowerNotificationWarnings3 = (SecPowerNotificationWarnings) SecPowerUI.this.mWarnings;
                            secPowerNotificationWarnings3.getClass();
                            Log.d("PowerUI.Notification", "showSafeModeNotice()");
                            secPowerNotificationWarnings3.showNotification(7);
                        }
                    } catch (RemoteException e) {
                        Log.e("PowerUI", "Safe mode exception occur", e);
                    }
                    if (PopupUIUtil.ACTION_BOOT_COMPLETED.equals(action)) {
                        SecPowerUI.this.mBootCompleted = true;
                        SecPowerUI.this.setSleepChargingOff();
                        return;
                    }
                    return;
                }
                if ("com.samsung.android.sm.IGNORE_RUT_TIPS_NOTI".equals(action) || "com.samsung.android.sm.CLEAR_TIPS_NOTI".equals(action) || "android.intent.action.tips.noti.confirmed".equals(action)) {
                    new TipsManager(SecPowerUI.this.mContext).checkIntentAction(intent);
                    return;
                }
                if ("android.intent.action.TIMEZONE_CHANGED".equals(action) || "android.intent.action.TIME_SET".equals(action)) {
                    Slog.i("PowerUI", "Time is changed, so we need to init LTC time");
                    SecPowerUI.this.clearScheduling();
                    return;
                }
                if ("com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING".equals(action)) {
                    SecPowerUI.this.mSleepChargingEvent = intent.getStringExtra("sleep_charging_event");
                    SecPowerUI.this.mChargingStartTime = intent.getStringExtra("sleep_charging_finish_time");
                    SecPowerUI.this.checkAdaptiveProtectionNotification();
                    return;
                } else {
                    if (!"com.samsung.server.BatteryService.action.SEC_BATTERY_REMAINING_CHARGING_TIME_CHANGED".equals(action)) {
                        Slog.w("PowerUI", "unknown intent: " + intent);
                        return;
                    }
                    long longExtra = intent.getLongExtra("remaining_charging_time", -1L);
                    Slog.d("PowerUI", "Remaining charging time : " + longExtra);
                    if (longExtra > 0) {
                        Slog.d("PowerUI", "Charging time is changed, check charging notification");
                        SecPowerUI secPowerUI6 = SecPowerUI.this;
                        secPowerUI6.checkChargingNotification(secPowerUI6.mPlugType, secPowerUI6.mBatteryStatus, secPowerUI6.mBatteryHealth);
                        return;
                    }
                    return;
                }
            }
            this.mHasReceivedBattery = true;
            SecPowerUI secPowerUI7 = SecPowerUI.this;
            int i16 = secPowerUI7.mBatteryLevel;
            secPowerUI7.mBatteryLevel = intent.getIntExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, 100);
            SecPowerUI secPowerUI8 = SecPowerUI.this;
            int i17 = secPowerUI8.mBatteryStatus;
            secPowerUI8.mBatteryStatus = intent.getIntExtra(IMSParameter.CALL.STATUS, 1);
            SecPowerUI secPowerUI9 = SecPowerUI.this;
            int i18 = secPowerUI9.mPlugType;
            secPowerUI9.mPlugType = intent.getIntExtra("plugged", 1);
            SecPowerUI secPowerUI10 = SecPowerUI.this;
            if (i18 != secPowerUI10.mPlugType) {
                secPowerUI10.mHandler.removeCallbacks(secPowerUI10.mAfterChargingNoticeTask);
                SecPowerUI.this.mIsChangedStringAfterCharging = false;
            }
            SecPowerUI secPowerUI11 = SecPowerUI.this;
            boolean z5 = secPowerUI11.mPlugType != 0;
            boolean z6 = i18 != 0;
            int iM2889$$Nest$mfindBatteryLevelBucket = SecPowerUI.m2889$$Nest$mfindBatteryLevelBucket(secPowerUI11, i16);
            SecPowerUI secPowerUI12 = SecPowerUI.this;
            int iM2889$$Nest$mfindBatteryLevelBucket2 = SecPowerUI.m2889$$Nest$mfindBatteryLevelBucket(secPowerUI12, secPowerUI12.mBatteryLevel);
            SecPowerUI secPowerUI13 = SecPowerUI.this;
            int i19 = secPowerUI13.mBatteryOnline;
            boolean z7 = secPowerUI13.mFullyConnected;
            secPowerUI13.mBatteryOnline = intent.getIntExtra("online", 1);
            SecPowerUI.this.mBatteryHighVoltageCharger = intent.getBooleanExtra("hv_charger", false);
            SecPowerUI secPowerUI14 = SecPowerUI.this;
            if (secPowerUI14.mBootCompleted) {
                secPowerUI14.mFullyConnected = (intent.getIntExtra("misc_event", 0) & 4) == 0;
                str = "INDICATOR_CHARGING";
                SecPowerUI.this.mBatterySlowCharger = intent.getIntExtra("charge_type", 0) == 2;
            } else {
                str = "INDICATOR_CHARGING";
            }
            StringBuilder sb = new StringBuilder("mBootCompleted = ");
            sb.append(SecPowerUI.this.mBootCompleted);
            sb.append(" |  mFullyConnected = ");
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(sb, SecPowerUI.this.mFullyConnected, "PowerUI");
            SecPowerUI secPowerUI15 = SecPowerUI.this;
            int i20 = secPowerUI15.mBatteryHealth;
            int i21 = secPowerUI15.mBatteryMiscEvent;
            boolean z8 = secPowerUI15.mIsWirelessOverheat;
            secPowerUI15.mBatteryMiscEvent = intent.getIntExtra("misc_event", 0);
            SecPowerUI secPowerUI16 = SecPowerUI.this;
            int i22 = secPowerUI16.mBatteryMiscEvent;
            int intExtra = intent.getIntExtra("health", 1);
            if (intExtra == 3 && (i22 & 1048576) != 0) {
                intExtra = 8;
            }
            secPowerUI16.mBatteryHealth = intExtra;
            SecPowerUI secPowerUI17 = SecPowerUI.this;
            secPowerUI17.mIsWirelessOverheat = (secPowerUI17.mBatteryMiscEvent & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0;
            Slog.i("PowerUI", "BATTERY_HEALTH_CHECK / batteryHealth : " + SecPowerUI.this.mBatteryHealth + " / wirelessOverheat : " + SecPowerUI.this.mIsWirelessOverheat);
            SecPowerUI secPowerUI18 = SecPowerUI.this;
            SecWarningsUI secWarningsUI = secPowerUI18.mWarnings;
            int i23 = secPowerUI18.mBatteryStatus;
            int i24 = secPowerUI18.mBatteryHealth;
            SecPowerNotificationWarnings secPowerNotificationWarnings4 = (SecPowerNotificationWarnings) secWarningsUI;
            secPowerNotificationWarnings4.mBatteryStatus = i23;
            secPowerNotificationWarnings4.mBatteryHealth = i24;
            int i25 = secPowerUI18.mBatteryCurrentEvent;
            boolean z9 = PowerUiRune.HV_CHARGER_ENABLE_POPUP;
            if (z9 || PowerUiRune.BATTERY_SWELLING_NOTICE) {
                z = z9;
                secPowerUI18.mBatteryCurrentEvent = intent.getIntExtra("current_event", 0);
            } else {
                z = z9;
            }
            SecPowerUI secPowerUI19 = SecPowerUI.this;
            int i26 = secPowerUI19.mBatterySwellingMode;
            boolean z10 = PowerUiRune.BATTERY_SWELLING_NOTICE;
            if (z10) {
                int i27 = secPowerUI19.mBatteryCurrentEvent;
                secPowerUI19.mBatterySwellingMode = (i27 & 16) != 0 ? 1 : (i27 & 32) != 0 ? 2 : 0;
            }
            secPowerUI19.mSuperFastCharger = intent.getIntExtra("charger_type", 0);
            boolean z11 = PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION;
            if (z11) {
                z2 = z11;
                SecPowerUI.this.mProtectionEvent = intent.getIntExtra("protection", 0);
                SecPowerUI secPowerUI20 = SecPowerUI.this;
                int i28 = secPowerUI20.mPlugType;
                if (i28 == 0) {
                    secPowerUI20.setSleepChargingOff();
                    SecPowerUI.this.mIsAfterAdaptiveProtection = false;
                } else {
                    if (i28 > 0) {
                        i15 = 100;
                        if (secPowerUI20.mBatteryLevel == 100) {
                            secPowerUI20.mIsAfterAdaptiveProtection = false;
                            if (BatteryProtectionUtils.isSleepChargingOn(secPowerUI20.mContext)) {
                                SecPowerNotificationWarnings secPowerNotificationWarnings5 = (SecPowerNotificationWarnings) SecPowerUI.this.mWarnings;
                                secPowerNotificationWarnings5.cancelNotification(10);
                                secPowerNotificationWarnings5.mOptimizationChargingFinishTime = "";
                            }
                        }
                    } else {
                        i15 = 100;
                    }
                    if (i16 == i15 && i16 != secPowerUI20.mBatteryLevel && BatteryProtectionUtils.isSleepChargingOn(secPowerUI20.mContext)) {
                        Slog.d("PowerUI", "show again AdaptiveProtection Notification");
                        SecPowerUI.this.checkAdaptiveProtectionNotification();
                    }
                }
            } else {
                z2 = z11;
            }
            boolean z12 = SecPowerUI.DEBUG;
            if (z12) {
                Slog.d("PowerUI", "buckets   ....." + SecPowerUI.this.mLowBatteryAlertCloseLevel + " .. " + SecPowerUI.this.mLowBatteryReminderLevels[0] + " .. " + SecPowerUI.this.mLowBatteryReminderLevels[1]);
                StringBuilder sb2 = new StringBuilder("level          ");
                sb2.append(i16);
                sb2.append(" --> ");
                sb2.append(SecPowerUI.this.mBatteryLevel);
                Slog.d("PowerUI", sb2.toString());
                Slog.d("PowerUI", "status         " + i17 + " --> " + SecPowerUI.this.mBatteryStatus);
                Slog.d("PowerUI", "plugType       " + i18 + " --> " + SecPowerUI.this.mPlugType);
                Slog.d("PowerUI", "bucket         " + iM2889$$Nest$mfindBatteryLevelBucket + " --> " + iM2889$$Nest$mfindBatteryLevelBucket2);
                Slog.d("PowerUI", "plugged        " + z6 + " --> " + z5);
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i25, "current_Event  ", " ---> ");
                sbM.append(SecPowerUI.this.mBatteryCurrentEvent);
                Slog.d("PowerUI", sbM.toString());
                Slog.d("PowerUI", "health  " + i20 + " ---> " + SecPowerUI.this.mBatteryHealth);
            }
            SecPowerUI secPowerUI21 = SecPowerUI.this;
            SecWarningsUI secWarningsUI2 = secPowerUI21.mWarnings;
            int i29 = secPowerUI21.mBatteryLevel;
            SecPowerNotificationWarnings secPowerNotificationWarnings6 = (SecPowerNotificationWarnings) secWarningsUI2;
            secPowerNotificationWarnings6.mOldBatteryLevel = secPowerNotificationWarnings6.mBatteryLevel;
            secPowerNotificationWarnings6.mBatteryLevel = i29;
            if (iM2889$$Nest$mfindBatteryLevelBucket2 >= 0) {
                i = i25;
                secPowerNotificationWarnings6.mWarningTriggerTimeMs = 0L;
            } else {
                i = i25;
                if (iM2889$$Nest$mfindBatteryLevelBucket2 < secPowerNotificationWarnings6.mBucket) {
                    secPowerNotificationWarnings6.mWarningTriggerTimeMs = System.currentTimeMillis();
                }
            }
            secPowerNotificationWarnings6.mBucket = iM2889$$Nest$mfindBatteryLevelBucket2;
            ListenableFuture listenableFuture = SecPowerUI.this.mLastShowWarningTask;
            if (listenableFuture != null) {
                listenableFuture.cancel(true);
                if (z12) {
                    Slog.d("PowerUI", "cancelled task");
                }
            }
            SecPowerUI secPowerUI22 = SecPowerUI.this;
            secPowerUI22.mLastBatteryStateData = secPowerUI22.mCurrentBatteryStateData;
            secPowerUI22.mCurrentBatteryStateData = new BatteryStateData(secPowerUI22.mBatteryLevel, z5, iM2889$$Nest$mfindBatteryLevelBucket2, secPowerUI22.mBatteryStatus);
            SecPowerUI.this.mLastShowWarningTask = ThreadUtils.postOnBackgroundThread(new SecPowerUI$$ExternalSyntheticLambda0(this, 1));
            SecPowerUI secPowerUI23 = SecPowerUI.this;
            if (secPowerUI23.mBatteryStatus == 2) {
                int i30 = secPowerUI23.mPlugType;
                if (i30 == 1 || i30 == 2) {
                    if (PowerUiRune.SPECIFIC_POWER_REQUEST_BY_VZW) {
                        if (secPowerUI23.mBatterySlowCharger) {
                            secPowerUI23.mBatteryChargingType = 8;
                        } else if (secPowerUI23.mFullyConnected) {
                            int i31 = secPowerUI23.mSuperFastCharger;
                            if (i31 == 3) {
                                secPowerUI23.mBatteryChargingType = 3;
                            } else if (i31 == 4) {
                                secPowerUI23.mBatteryChargingType = 4;
                            } else if (secPowerUI23.mBatteryHighVoltageCharger) {
                                secPowerUI23.mBatteryChargingType = 2;
                            } else {
                                secPowerUI23.mBatteryChargingType = 1;
                            }
                        } else {
                            secPowerUI23.mBatteryChargingType = 9;
                        }
                    } else if (!secPowerUI23.mFullyConnected) {
                        secPowerUI23.mBatteryChargingType = 9;
                    } else if (secPowerUI23.mBatterySlowCharger) {
                        secPowerUI23.mBatteryChargingType = 8;
                    } else {
                        int i32 = secPowerUI23.mSuperFastCharger;
                        if (i32 == 3) {
                            secPowerUI23.mBatteryChargingType = 3;
                        } else if (i32 == 4) {
                            secPowerUI23.mBatteryChargingType = 4;
                        } else if (secPowerUI23.mBatteryHighVoltageCharger) {
                            secPowerUI23.mBatteryChargingType = 2;
                        } else {
                            secPowerUI23.mBatteryChargingType = 1;
                        }
                    }
                } else if (i30 != 4) {
                    secPowerUI23.mBatteryChargingType = 0;
                } else if (secPowerUI23.mBatteryOnline == 100) {
                    secPowerUI23.mBatteryChargingType = 7;
                } else {
                    secPowerUI23.mBatteryChargingType = 6;
                }
                if (i30 == i18 && secPowerUI23.mIsChangedStringAfterCharging) {
                    int i33 = secPowerUI23.mBatteryChargingType;
                    switch (i33) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            i33 = 11;
                            break;
                        case 6:
                        case 7:
                            i33 = 10;
                            break;
                    }
                    secPowerUI23.mBatteryChargingType = i33;
                }
                if (z2 && secPowerUI23.mIsAfterAdaptiveProtection) {
                    secPowerUI23.mBatteryChargingType = 12;
                }
            } else {
                secPowerUI23.mBatteryChargingType = 0;
            }
            int i34 = secPowerUI23.mBatteryChargingType;
            int i35 = secPowerUI23.mSuperFastCharger;
            SecPowerNotificationWarnings secPowerNotificationWarnings7 = (SecPowerNotificationWarnings) secPowerUI23.mWarnings;
            secPowerNotificationWarnings7.mOldChargingType = secPowerNotificationWarnings7.mChargingType;
            secPowerNotificationWarnings7.mChargingType = i34;
            secPowerNotificationWarnings7.mSuperFastCharger = i35;
            if (secPowerUI23.mCurrentChargingAnimation != null) {
                Slog.d("PowerUI", "Skip charging animation - already playing");
            } else if (secPowerUI23.skipChargingUi(i18, i17, i21, z7)) {
                Slog.d("PowerUI", "Skip charging animation - by " + secPowerUI23.mSkipChargingUiMsg);
            } else {
                boolean zIsCoverClosed = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isCoverClosed();
                CoverState coverState = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).getCoverState();
                int type = coverState != null ? coverState.getType() : 2;
                DozeChargingHelper dozeChargingHelper = secPowerUI23.mDozeChargingHelper;
                if (!zIsCoverClosed || type == 8) {
                    Context context2 = secPowerUI23.mContext;
                    ChargerAnimationUtils chargerAnimationUtils = ChargerAnimationUtils.INSTANCE;
                    if (PowerUiRune.CHARGING_VI_NOW_BAR) {
                        if (PowerUiRune.COVER_DISPLAY_LARGE_SCREEN) {
                            str2 = (ChargerAnimationUtils.isAodOrLockScreen() || DisplayUtils.isFlipSubDisplayOn(SemWindowManager.getInstance().isFolded())) ? "LARGE_COVER_SCREEN_NOW_BAR" : str;
                        } else if (ChargerAnimationUtils.isAodOrLockScreen()) {
                            SemStatusBarManager semStatusBarManager = (SemStatusBarManager) context2.getSystemService(SemStatusBarManager.class);
                            if (!(semStatusBarManager != null ? semStatusBarManager.isPanelExpanded() : false)) {
                                str2 = DisplayUtils.isFlipSubDisplayOn(SemWindowManager.getInstance().isFolded()) ? "SMALL_COVER_SCREEN_NOW_BAR" : "NORMAL_NOW_BAR";
                            }
                        }
                        secPowerUI = SecPowerUI.this;
                        if (secPowerUI.mCurrentChargingAnimation != null && i7 > 0 && secPowerUI.mPlugType == 0) {
                            secPowerUI.removeChargerView();
                            secPowerUI.mDozeChargingHelper.restoreDisplayStateWhenDozeCharging();
                        }
                        secPowerUI2 = SecPowerUI.this;
                        i10 = i7;
                        i11 = i9;
                        i12 = i6;
                        if (secPowerUI2.skipChargingUi(i10, i12, i11, z3)) {
                            Slog.d(str3, "Skip charging sound & vibrate - by " + secPowerUI2.mSkipChargingUiMsg);
                            z4 = false;
                        } else {
                            boolean z13 = secPowerUI2.mIsRunningStopPowerSoundTask;
                            Handler handler = secPowerUI2.mHandler;
                            if (z13) {
                                handler.removeCallbacks(secPowerUI2.mStopPowerSoundTask);
                                z4 = false;
                                secPowerUI2.mIsRunningStopPowerSoundTask = false;
                            } else {
                                z4 = false;
                            }
                            ((SecPowerNotificationWarnings) secPowerUI2.mWarnings).playPowerSound(1);
                            handler.postDelayed(secPowerUI2.mStopPowerSoundTask, 3000L);
                            secPowerUI2.mIsRunningStopPowerSoundTask = true;
                        }
                        secPowerUI2.mIsChangedBatteryProtectionOnCharging = z4;
                        secPowerUI3 = SecPowerUI.this;
                        boolean z14 = (i11 & 4194304) == 4194304;
                        boolean z15 = (secPowerUI3.mBatteryMiscEvent & 4194304) == 4194304;
                        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("oldMisalign : ", ", curMisalign : ", str3, z14, z15);
                        if (secPowerUI3.mPlugType <= 0) {
                            secPowerUI3.removeMisalignView();
                        } else if (z14 || !z15) {
                            if (z14 && !z15) {
                                Log.i(str3, "Show align view");
                                secPowerUI3.removeChargerView();
                                secPowerUI3.removeMisalignView();
                                secPowerUI3.setWirelessMisalignView(1);
                            } else if (!z14 || !z15) {
                                secPowerUI3.removeMisalignView();
                            }
                        } else if (DisplayUtils.isViewCoverClosed()) {
                            Log.d(str3, "View cover closed, so we doesn't show misalign view");
                        } else {
                            Log.i(str3, "Show wireless misalign");
                            secPowerUI3.removeChargerView();
                            secPowerUI3.removeMisalignView();
                            secPowerUI3.setWirelessMisalignView(0);
                            secPowerUI3.mIsWirelessMisalignTask = true;
                            secPowerUI3.mHandler.postDelayed(secPowerUI3.mWirelessMisalignTimeoutTask, 30000L);
                        }
                        int i36 = i3;
                        SecPowerUI.this.checkChargingNotification(i10, i12, i36);
                        if (z10) {
                            SecPowerUI.m2878$$Nest$mcheckBatterySwellingStatus(SecPowerUI.this, i26, i12);
                        }
                        SecPowerUI.m2876$$Nest$mcheckBatteryHealthInterruptionStatus(SecPowerUI.this, i36, z8);
                        if (PowerUiRune.INCOMPATIBLE_CHARGER_CHECK) {
                            SecPowerUI secPowerUI24 = SecPowerUI.this;
                            int i37 = secPowerUI24.mBatteryOnline;
                            SecWarningsUI secWarningsUI3 = secPowerUI24.mWarnings;
                            if (i37 == 0 && i19 != 0) {
                                ((SecPowerNotificationWarnings) secWarningsUI3).showIncompatibleChargerNotice();
                            } else if (i19 == 0 && i37 != 0) {
                                SecPowerNotificationWarnings secPowerNotificationWarnings8 = (SecPowerNotificationWarnings) secWarningsUI3;
                                AlertDialog alertDialog = secPowerNotificationWarnings8.mIncompatibleChargerDialog;
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                Log.d("PowerUI.Notification", "dismissing incompatible charger notification");
                                secPowerNotificationWarnings8.cancelNotification(3);
                            }
                        }
                        if (PowerUiRune.FULL_BATTERY_CHECK && i12 != (i14 = (secPowerUI5 = SecPowerUI.this).mBatteryStatus) && ((i14 == 5 && i12 != 2) || BatteryProtectionUtils.isProtectedFullyByMaximum(secPowerUI5.mBatteryMiscEvent))) {
                            secPowerUI5.mDozeChargingHelper.handlePluginAodCharging("checkFullBatteryStatus, change chargingAnimStarted to false");
                        }
                        secPowerUI4 = SecPowerUI.this;
                        if (Settings.System.getInt(secPowerUI4.mContext.getContentResolver(), "LOW_BATTERY_DUMP", 0) == 1) {
                            int i38 = secPowerUI4.mBatteryLevel;
                            if (i5 - i38 >= 10 || i38 - i5 >= 10) {
                                i13 = i5;
                                if (i13 != -1) {
                                    if (secPowerUI4.mBootCompleted) {
                                        Log.d(str3, "Low battery dump");
                                        Intent intent2 = new Intent("com.samsung.systemui.power.action.LOW_BATTERY_DUMP");
                                        intent2.addFlags(16777216);
                                        secPowerUI4.mContext.sendBroadcast(intent2);
                                    }
                                }
                            } else {
                                i13 = i5;
                            }
                            int i39 = i8;
                            if (i39 < i4 && i39 == -2) {
                            }
                        } else {
                            i13 = i5;
                        }
                        if (z) {
                            SecPowerUI.m2880$$Nest$mcheckHVchargerEnableConnection(SecPowerUI.this, i2);
                        }
                        SecPowerUI.m2875$$Nest$mcheckAbnormalChargingPad(SecPowerUI.this, i11);
                        if (PowerUiRune.TIPS_NOTIFICATION) {
                            SecPowerUI.m2881$$Nest$mcheckTipsNotification(SecPowerUI.this, i13);
                        }
                        if (PowerUiRune.CHN_SMART_MANAGER) {
                            SecPowerUI.m2882$$Nest$mcheckTurnOffPsmNotification(SecPowerUI.this, i13);
                        }
                        if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                            SecPowerUI.m2883$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge(SecPowerUI.this);
                        } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
                            SecPowerUI.m2884$$Nest$mcheckTurnOnProtectBatteryByLongTa(SecPowerUI.this);
                        }
                        if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
                            SecPowerUI.this.checkBatteryProtectionNotification();
                        }
                        if (PowerUiRune.BATTERY_PROTECTION_TIPS_NOTIFICATION) {
                            SecPowerUI.m2877$$Nest$mcheckBatteryProtectionTipsNotification(SecPowerUI.this, i10);
                            return;
                        }
                        return;
                    }
                    str2 = "OLD_CHARGING_ANIMATION";
                    Slog.d("PowerUI", "Charger connected, charger : " + secPowerUI23.mSuperFastCharger);
                    Slog.d("PowerUI", "Charging VI style : ".concat(str2));
                    Handler handler2 = secPowerUI23.mHandler;
                    FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = secPowerUI23.mFaceWidgetNotificationControllerWrapper;
                    i2 = i;
                    i3 = i20;
                    switch (str2.hashCode()) {
                        case -316370030:
                            if (!str2.equals("NORMAL_NOW_BAR")) {
                                c = 65535;
                                break;
                            } else {
                                c = 0;
                                break;
                            }
                        case -313509865:
                            if (str2.equals("SMALL_COVER_SCREEN_NOW_BAR")) {
                                c = 1;
                                break;
                            }
                            break;
                        case 436466369:
                            if (str2.equals(str)) {
                                c = 2;
                                break;
                            }
                            break;
                        case 631017550:
                            if (str2.equals("OLD_CHARGING_ANIMATION")) {
                                c = 3;
                                break;
                            }
                            break;
                        case 1234549347:
                            if (str2.equals("LARGE_COVER_SCREEN_NOW_BAR")) {
                                c = 4;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            i4 = iM2889$$Nest$mfindBatteryLevelBucket;
                            i5 = i16;
                            z3 = z7;
                            i6 = i17;
                            i7 = i18;
                            i8 = iM2889$$Nest$mfindBatteryLevelBucket2;
                            i9 = i21;
                            str3 = "PowerUI";
                            NowBarItem nowBarItem = PowerUtils.getNowBarItem();
                            ChargerNowBarView chargerNowBarView = (ChargerNowBarView) View.inflate(secPowerUI23.mContext, R.layout.charger_nowbar_view, null);
                            int i40 = secPowerUI23.mBatteryLevel;
                            int i41 = secPowerUI23.mSuperFastCharger;
                            chargerNowBarView.currentBatteryLevel = i40;
                            chargerNowBarView.chargingType = i41;
                            chargerNowBarView.animationListener = secPowerUI23;
                            secPowerUI23.mChargerNowBarView = chargerNowBarView;
                            nowBarItem.setContentViewForNormalCard(chargerNowBarView);
                            dozeChargingHelper.handleDisplayStateWhenDozeCharging();
                            handler2.postDelayed(secPowerUI23.mRestoreDisplayStateTask, 4500L);
                            faceWidgetNotificationControllerWrapper.updateItem(nowBarItem);
                            secPowerUI23.mCurrentChargingAnimation = "now_bar_charging_vi";
                            break;
                        case 1:
                            i4 = iM2889$$Nest$mfindBatteryLevelBucket;
                            i5 = i16;
                            z3 = z7;
                            i6 = i17;
                            i7 = i18;
                            i9 = i21;
                            i8 = iM2889$$Nest$mfindBatteryLevelBucket2;
                            str3 = "PowerUI";
                            if (secPowerUI23.mChargerAnimationWindowLp == null) {
                                WindowManager.LayoutParams layoutParam = SecPowerUI.getLayoutParam("PowerUI.ChargerAnimationViewLp");
                                secPowerUI23.mChargerAnimationWindowLp = layoutParam;
                                layoutParam.type = 2021;
                                layoutParam.flags |= 24;
                            }
                            if (secPowerUI23.mChargerAnimationWindowManager == null) {
                                Context subDisplayContext = DisplayUtils.getSubDisplayContext(secPowerUI23.mContext);
                                secPowerUI23.mChargerAnimationWindowLp.layoutInDisplayCutoutMode = 3;
                                secPowerUI23.mChargerAnimationWindowManager = (WindowManager) subDisplayContext.getSystemService("window");
                                ChargerFlipCoverView chargerFlipCoverView = (ChargerFlipCoverView) View.inflate(subDisplayContext, R.layout.charger_flip_cover_view, null);
                                secPowerUI23.mChargerFlipCoverView = chargerFlipCoverView;
                                int i42 = secPowerUI23.mBatteryLevel;
                                int i43 = secPowerUI23.mSuperFastCharger;
                                chargerFlipCoverView.currentBatteryLevel = i42;
                                chargerFlipCoverView.chargingType = i43;
                                chargerFlipCoverView.animationListener = secPowerUI23;
                            }
                            dozeChargingHelper.handleDisplayStateWhenDozeCharging();
                            handler2.postDelayed(secPowerUI23.mRestoreDisplayStateTask, 4500L);
                            secPowerUI23.mChargerAnimationWindowManager.addView(secPowerUI23.mChargerFlipCoverView, secPowerUI23.mChargerAnimationWindowLp);
                            secPowerUI23.mCurrentChargingAnimation = "flip_cover_charging_vi";
                            break;
                        case 2:
                            i4 = iM2889$$Nest$mfindBatteryLevelBucket;
                            i5 = i16;
                            z3 = z7;
                            i6 = i17;
                            i7 = i18;
                            i8 = iM2889$$Nest$mfindBatteryLevelBucket2;
                            i9 = i21;
                            str3 = "PowerUI";
                            int i44 = secPowerUI23.mBatteryLevel;
                            int i45 = secPowerUI23.mSuperFastCharger;
                            SystemEventCoordinator systemEventCoordinator = secPowerUI23.mSystemEventCoordinator;
                            if (systemEventCoordinator.shadeExpansionStateManager.isClosed()) {
                                SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = systemEventCoordinator.scheduler;
                                if (systemStatusAnimationSchedulerImpl == null) {
                                    systemStatusAnimationSchedulerImpl = null;
                                }
                                systemStatusAnimationSchedulerImpl.onStatusEvent(new BatteryEvent(i44, i45));
                                break;
                            }
                            break;
                        case 3:
                            i4 = iM2889$$Nest$mfindBatteryLevelBucket;
                            if (secPowerUI23.mChargerAnimationWindowLp == null) {
                                WindowManager.LayoutParams layoutParam2 = SecPowerUI.getLayoutParam("PowerUI.ChargerAnimationViewLp");
                                secPowerUI23.mChargerAnimationWindowLp = layoutParam2;
                                layoutParam2.type = 2021;
                                layoutParam2.flags |= 24;
                            }
                            boolean zIsFolded = SemWindowManager.getInstance().isFolded();
                            if (secPowerUI23.mChargerAnimationWindowManager == null) {
                                if (DisplayUtils.isFlipSubDisplayOn(zIsFolded)) {
                                    EmergencyButtonController$$ExternalSyntheticOutline0.m("folder state : ", "PowerUI", zIsFolded);
                                    Context subDisplayContext2 = DisplayUtils.getSubDisplayContext(secPowerUI23.mContext);
                                    if (PowerUiRune.COVER_DISPLAY_LARGE_SCREEN) {
                                        secPowerUI23.mChargerAnimationWindowLp.layoutInDisplayCutoutMode = 3;
                                    }
                                    secPowerUI23.mChargerAnimationWindowManager = (WindowManager) subDisplayContext2.getSystemService("window");
                                    if (SystemProperties.get("ro.product.vendor.name").toLowerCase().contains("bloom")) {
                                        secPowerUI23.mChargerAnimationView = (ChargerAnimationView) View.inflate(subDisplayContext2, R.layout.battery_charger_animation_bloom, null);
                                    } else {
                                        secPowerUI23.mChargerAnimationView = (ChargerAnimationView) View.inflate(subDisplayContext2, R.layout.battery_charger_animation, null);
                                    }
                                } else {
                                    secPowerUI23.mChargerAnimationWindowManager = (WindowManager) secPowerUI23.mContext.getSystemService("window");
                                    secPowerUI23.mChargerAnimationView = (ChargerAnimationView) View.inflate(secPowerUI23.mContext, R.layout.battery_charger_animation, null);
                                }
                            }
                            secPowerUI23.mChargerAnimationWindowManager.addView(secPowerUI23.mChargerAnimationView, secPowerUI23.mChargerAnimationWindowLp);
                            ChargerAnimationView chargerAnimationView = secPowerUI23.mChargerAnimationView;
                            chargerAnimationView.getClass();
                            chargerAnimationView.mAnimationPlaying = false;
                            chargerAnimationView.setVisibility(8);
                            ChargerAnimationView chargerAnimationView2 = secPowerUI23.mChargerAnimationView;
                            chargerAnimationView2.mAnimationListener = secPowerUI23;
                            chargerAnimationView2.mDozeChargingHelper = dozeChargingHelper;
                            boolean zIsFolded2 = SemWindowManager.getInstance().isFolded();
                            chargerAnimationView2.mIsSubscreenOff = DisplayUtils.isFlipSubDisplayOn(zIsFolded2) && ((DisplayManager) chargerAnimationView2.mContext.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN")[1].getState() == 1;
                            chargerAnimationView2.mNeedFullScreenBlur = ChargerAnimationUtils.isAodOrLockScreen() || DisplayUtils.isFlipSubDisplayOn(zIsFolded2);
                            if (chargerAnimationView2.mIsSubscreenOff) {
                                chargerAnimationView2.mBackGroundView.setBackgroundColor(chargerAnimationView2.mContext.getColor(R.color.charger_anim_normal_bg_color_lock_screen_off));
                            } else if (chargerAnimationView2.mDozeChargingHelper.isDozeChargingCondition()) {
                                chargerAnimationView2.mBackGroundView.setBackgroundColor(chargerAnimationView2.mContext.getColor(R.color.charger_anim_normal_bg_color_lock));
                            } else if (PowerUiRune.WINDOW_BLUR_SUPPORTED && PowerUiRune.GPU_BLUR_SUPPORTED) {
                                SemBlurInfo.Builder colorCurvePreset = new SemBlurInfo.Builder(0).setColorCurvePreset(11);
                                if (chargerAnimationView2.mNeedFullScreenBlur) {
                                    chargerAnimationView2.mBackGroundView.setBackgroundColor(chargerAnimationView2.mContext.getColor(R.color.charger_anim_blur_bg_color_lock));
                                    colorCurvePreset.setBackgroundColor(chargerAnimationView2.mContext.getColor(R.color.charger_anim_blur_bg_color_lock));
                                    chargerAnimationView2.mCircleBackgroundView.semSetBlurInfo(null);
                                    chargerAnimationView2.mBackGroundView.semSetBlurInfo(colorCurvePreset.build());
                                } else {
                                    colorCurvePreset.setBackgroundColor(chargerAnimationView2.mContext.getColor(R.color.charger_anim_blur_bg_color));
                                    chargerAnimationView2.mBackGroundView.semSetBlurInfo(null);
                                    chargerAnimationView2.mCircleBackgroundView.semSetBlurInfo(colorCurvePreset.setBackgroundCornerRadius(chargerAnimationView2.mContext.getResources().getDimension(R.dimen.charger_anim_blur_corner_radius)).build());
                                }
                            } else if (chargerAnimationView2.mNeedFullScreenBlur) {
                                chargerAnimationView2.mBackGroundView.setBackgroundColor(chargerAnimationView2.mContext.getColor(R.color.charger_anim_normal_bg_color_lock));
                            } else {
                                chargerAnimationView2.mCircleBackgroundView.setBackgroundResource(R.drawable.charging_app_screen_background);
                            }
                            final ChargerAnimationView chargerAnimationView3 = secPowerUI23.mChargerAnimationView;
                            int i46 = secPowerUI23.mBatteryLevel;
                            chargerAnimationView3.mSuperFastChargingType = secPowerUI23.mSuperFastCharger;
                            chargerAnimationView3.mCurrentBatteryLevel = i46;
                            if (chargerAnimationView3.mAnimationPlaying) {
                                Log.d("PowerUI.ChargerAnimationView", "Animation is playing, return");
                                i5 = i16;
                                z3 = z7;
                                i6 = i17;
                                i7 = i18;
                                i8 = iM2889$$Nest$mfindBatteryLevelBucket2;
                                i9 = i21;
                                str3 = "PowerUI";
                            } else {
                                chargerAnimationView3.mAnimationPlaying = true;
                                chargerAnimationView3.setVisibility(0);
                                chargerAnimationView3.mDozeChargingHelper.handleDisplayStateWhenDozeCharging();
                                int i47 = chargerAnimationView3.mSuperFastChargingType;
                                String strConcat = i47 != 3 ? i47 != 4 ? i47 != 5 ? "charging_l1" : "charging_l4" : "charging_l3" : "charging_l2";
                                if (ChargerAnimationUtils.isAodOrLockScreen()) {
                                    strConcat = strConcat.concat("_lock");
                                }
                                String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strConcat, ".json");
                                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Animation applied : ", strM, "PowerUI.ChargerAnimationView");
                                chargerAnimationView3.mChargerAnimationView.setAnimation(strM);
                                if (chargerAnimationView3.mIsSubscreenOff) {
                                    chargerAnimationView3.mFadeInAnimation = ObjectAnimator.ofFloat(chargerAnimationView3.mChargerContainer, "alpha", 0.0f, 1.0f).setDuration(chargerAnimationView3.mNeedFullScreenBlur ? 400L : 200L);
                                    duration = ObjectAnimator.ofFloat(chargerAnimationView3.mChargerContainer, "alpha", 1.0f, 0.0f).setDuration(chargerAnimationView3.mNeedFullScreenBlur ? 400L : 100L);
                                } else {
                                    chargerAnimationView3.mFadeInAnimation = ObjectAnimator.ofFloat(chargerAnimationView3, "alpha", 0.0f, 1.0f).setDuration(chargerAnimationView3.mNeedFullScreenBlur ? 400L : 200L);
                                    duration = ObjectAnimator.ofFloat(chargerAnimationView3, "alpha", 1.0f, 0.0f).setDuration(chargerAnimationView3.mNeedFullScreenBlur ? 400L : 100L);
                                }
                                duration.setStartDelay(chargerAnimationView3.mNeedFullScreenBlur ? 1600L : 1350L);
                                duration.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.power.view.ChargerAnimationView.1
                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationCancel(Animator animator) {
                                        Log.i("PowerUI.ChargerAnimationView", "Animation Cancel");
                                        ChargerAnimationView.this.mDozeChargingHelper.restoreDisplayStateWhenDozeCharging();
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                        Log.i("PowerUI.ChargerAnimationView", "Animation Ended");
                                        ChargerAnimationView chargerAnimationView4 = ChargerAnimationView.this;
                                        chargerAnimationView4.getClass();
                                        chargerAnimationView4.mAnimationPlaying = false;
                                        chargerAnimationView4.setVisibility(8);
                                        ((SecPowerUI) ChargerAnimationView.this.mAnimationListener).onChargerAnimationEnd();
                                        ChargerAnimationView.this.mDozeChargingHelper.restoreDisplayStateWhenDozeCharging();
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationRepeat(Animator animator) {
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationStart(Animator animator) {
                                    }
                                });
                                chargerAnimationView3.mAlphaAnimatorSet.play(duration).after(chargerAnimationView3.mFadeInAnimation);
                                if (chargerAnimationView3.mNeedFullScreenBlur) {
                                    chargerAnimationView3.mBackGroundView.setVisibility(0);
                                    chargerAnimationView3.mCircleBackgroundView.setVisibility(8);
                                } else {
                                    chargerAnimationView3.mBackGroundView.setVisibility(8);
                                    chargerAnimationView3.mCircleBackgroundView.setVisibility(0);
                                }
                                ImageView imageView = chargerAnimationView3.mChargingIconView;
                                Context context3 = chargerAnimationView3.mContext;
                                int i48 = chargerAnimationView3.mSuperFastChargingType;
                                imageView.setImageDrawable(context3.getDrawable((i48 == 3 || i48 == 4 || i48 == 5) ? R.drawable.ic_icon_superfast : R.drawable.ic_icon_charging));
                                PathInterpolator pathInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
                                i8 = iM2889$$Nest$mfindBatteryLevelBucket2;
                                PathInterpolator pathInterpolator2 = new PathInterpolator(0.33f, 0.0f, 0.4f, 1.0f);
                                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleX", 0.5f, 1.05f);
                                objectAnimatorOfFloat.setDuration(233L);
                                objectAnimatorOfFloat.setStartDelay(chargerAnimationView3.mNeedFullScreenBlur ? 100L : 0L);
                                objectAnimatorOfFloat.setInterpolator(pathInterpolator2);
                                i5 = i16;
                                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleY", 0.5f, 1.05f);
                                z3 = z7;
                                objectAnimatorOfFloat2.setDuration(233L);
                                objectAnimatorOfFloat2.setInterpolator(pathInterpolator2);
                                i7 = i18;
                                ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleX", 1.05f, 0.96f);
                                objectAnimatorOfFloat3.setDuration(267L);
                                objectAnimatorOfFloat3.setInterpolator(pathInterpolator);
                                i9 = i21;
                                ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleY", 1.05f, 0.96f);
                                i6 = i17;
                                str3 = "PowerUI";
                                objectAnimatorOfFloat4.setDuration(267L);
                                objectAnimatorOfFloat4.setInterpolator(pathInterpolator);
                                ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleX", 0.96f, 1.0f);
                                objectAnimatorOfFloat5.setDuration(183L);
                                objectAnimatorOfFloat5.setInterpolator(pathInterpolator2);
                                ObjectAnimator objectAnimatorOfFloat6 = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleY", 0.96f, 1.0f);
                                objectAnimatorOfFloat6.setDuration(183L);
                                objectAnimatorOfFloat6.setInterpolator(pathInterpolator2);
                                chargerAnimationView3.mAlphaAnimatorSet.play(chargerAnimationView3.mFadeInAnimation).with(objectAnimatorOfFloat);
                                chargerAnimationView3.mAlphaAnimatorSet.play(objectAnimatorOfFloat).with(objectAnimatorOfFloat2).before(objectAnimatorOfFloat3);
                                chargerAnimationView3.mAlphaAnimatorSet.play(objectAnimatorOfFloat3).with(objectAnimatorOfFloat4).before(objectAnimatorOfFloat5);
                                chargerAnimationView3.mAlphaAnimatorSet.play(objectAnimatorOfFloat5).with(objectAnimatorOfFloat6);
                                chargerAnimationView3.mChargerAnimationView.playAnimation();
                                chargerAnimationView3.mAlphaAnimatorSet.start();
                                chargerAnimationView3.setBatteryLevelText();
                                Log.d("PowerUI.ChargerAnimationView", "Animation Started");
                            }
                            secPowerUI23.mCurrentChargingAnimation = "old_charging_vi";
                            break;
                        case 4:
                            NowBarItem nowBarItem2 = PowerUtils.getNowBarItem();
                            i4 = iM2889$$Nest$mfindBatteryLevelBucket;
                            ChargerNowBarView chargerNowBarView2 = (ChargerNowBarView) View.inflate(secPowerUI23.mContext, R.layout.charger_nowbar_view, null);
                            int i49 = secPowerUI23.mBatteryLevel;
                            int i50 = secPowerUI23.mSuperFastCharger;
                            chargerNowBarView2.currentBatteryLevel = i49;
                            chargerNowBarView2.chargingType = i50;
                            chargerNowBarView2.animationListener = secPowerUI23;
                            secPowerUI23.mChargerNowBarView = chargerNowBarView2;
                            ChargerCoverNowBarView chargerCoverNowBarView = (ChargerCoverNowBarView) View.inflate(DisplayUtils.getSubDisplayContext(secPowerUI23.mContext), R.layout.charger_cover_nowbar_view, null);
                            int i51 = secPowerUI23.mBatteryLevel;
                            int i52 = secPowerUI23.mSuperFastCharger;
                            chargerCoverNowBarView.currentBatteryLevel = i51;
                            chargerCoverNowBarView.chargingType = i52;
                            chargerCoverNowBarView.animationListener = secPowerUI23;
                            secPowerUI23.mChargerCoverNormalSubScreenView = chargerCoverNowBarView;
                            ChargerCoverNowBarView chargerCoverNowBarView2 = (ChargerCoverNowBarView) View.inflate(DisplayUtils.getSubDisplayContext(secPowerUI23.mContext), R.layout.charger_cover_nowbar_view, null);
                            int i53 = secPowerUI23.mBatteryLevel;
                            int i54 = secPowerUI23.mSuperFastCharger;
                            chargerCoverNowBarView2.currentBatteryLevel = i53;
                            chargerCoverNowBarView2.chargingType = i54;
                            chargerCoverNowBarView2.animationListener = secPowerUI23;
                            secPowerUI23.mChargerCoverExpandSubScreenView = chargerCoverNowBarView2;
                            nowBarItem2.setContentViewForNormalCard(secPowerUI23.mChargerNowBarView);
                            nowBarItem2.setContentViewForNormalCardOnSubScreen(secPowerUI23.mChargerCoverNormalSubScreenView);
                            nowBarItem2.setContentViewForExpandCardOnSubScreen(secPowerUI23.mChargerCoverExpandSubScreenView);
                            dozeChargingHelper.handleDisplayStateWhenDozeCharging();
                            handler2.postDelayed(secPowerUI23.mRestoreDisplayStateTask, 4500L);
                            faceWidgetNotificationControllerWrapper.updateItem(nowBarItem2);
                            secPowerUI23.mCurrentChargingAnimation = "large_cover_flip_now_bar_charging_vi";
                            i5 = i16;
                            z3 = z7;
                            i6 = i17;
                            i7 = i18;
                            i8 = iM2889$$Nest$mfindBatteryLevelBucket2;
                            i9 = i21;
                            str3 = "PowerUI";
                            break;
                        default:
                            i4 = iM2889$$Nest$mfindBatteryLevelBucket;
                            i5 = i16;
                            z3 = z7;
                            i6 = i17;
                            i7 = i18;
                            i8 = iM2889$$Nest$mfindBatteryLevelBucket2;
                            i9 = i21;
                            str3 = "PowerUI";
                            break;
                    }
                    if (secPowerUI23.mIsMotionDetectionSupported && !secPowerUI23.mIsDeviceMoving && secPowerUI23.mPlugType != 4) {
                        Slog.i(str3, "Plug type is not wireless, but mIsDeviceMoving is not still released. We should release mIsDeviceMoving to true !!");
                        secPowerUI23.mIsDeviceMoving = true;
                    }
                    secPowerUI = SecPowerUI.this;
                    if (secPowerUI.mCurrentChargingAnimation != null) {
                        secPowerUI.removeChargerView();
                        secPowerUI.mDozeChargingHelper.restoreDisplayStateWhenDozeCharging();
                    }
                    secPowerUI2 = SecPowerUI.this;
                    i10 = i7;
                    i11 = i9;
                    i12 = i6;
                    if (secPowerUI2.skipChargingUi(i10, i12, i11, z3)) {
                    }
                    secPowerUI2.mIsChangedBatteryProtectionOnCharging = z4;
                    secPowerUI3 = SecPowerUI.this;
                    if ((i11 & 4194304) == 4194304) {
                    }
                    if ((secPowerUI3.mBatteryMiscEvent & 4194304) == 4194304) {
                    }
                    KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("oldMisalign : ", ", curMisalign : ", str3, z14, z15);
                    if (secPowerUI3.mPlugType <= 0) {
                    }
                    int i362 = i3;
                    SecPowerUI.this.checkChargingNotification(i10, i12, i362);
                    if (z10) {
                    }
                    SecPowerUI.m2876$$Nest$mcheckBatteryHealthInterruptionStatus(SecPowerUI.this, i362, z8);
                    if (PowerUiRune.INCOMPATIBLE_CHARGER_CHECK) {
                    }
                    if (PowerUiRune.FULL_BATTERY_CHECK) {
                        secPowerUI5.mDozeChargingHelper.handlePluginAodCharging("checkFullBatteryStatus, change chargingAnimStarted to false");
                    }
                    secPowerUI4 = SecPowerUI.this;
                    if (Settings.System.getInt(secPowerUI4.mContext.getContentResolver(), "LOW_BATTERY_DUMP", 0) == 1) {
                    }
                    if (z) {
                    }
                    SecPowerUI.m2875$$Nest$mcheckAbnormalChargingPad(SecPowerUI.this, i11);
                    if (PowerUiRune.TIPS_NOTIFICATION) {
                    }
                    if (PowerUiRune.CHN_SMART_MANAGER) {
                    }
                    if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                    }
                    if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
                    }
                    if (PowerUiRune.BATTERY_PROTECTION_TIPS_NOTIFICATION) {
                    }
                } else {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(type, "View Cover is covered and closed, so don't play charging animation but turn on AOD, cover type : ", "PowerUI");
                    if (type == 15 || type == 16 || type == 17) {
                        dozeChargingHelper.handlePluginAodCharging("Supported view cover && cover closed, so we should call PluginAODManager");
                    }
                    Slog.d("PowerUI", "Skip charging animation - by cover state");
                }
            }
            i4 = iM2889$$Nest$mfindBatteryLevelBucket;
            i2 = i;
            i3 = i20;
            i5 = i16;
            z3 = z7;
            i6 = i17;
            i7 = i18;
            i8 = iM2889$$Nest$mfindBatteryLevelBucket2;
            i9 = i21;
            str3 = "PowerUI";
            secPowerUI = SecPowerUI.this;
            if (secPowerUI.mCurrentChargingAnimation != null) {
            }
            secPowerUI2 = SecPowerUI.this;
            i10 = i7;
            i11 = i9;
            i12 = i6;
            if (secPowerUI2.skipChargingUi(i10, i12, i11, z3)) {
            }
            secPowerUI2.mIsChangedBatteryProtectionOnCharging = z4;
            secPowerUI3 = SecPowerUI.this;
            if ((i11 & 4194304) == 4194304) {
            }
            if ((secPowerUI3.mBatteryMiscEvent & 4194304) == 4194304) {
            }
            KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("oldMisalign : ", ", curMisalign : ", str3, z14, z15);
            if (secPowerUI3.mPlugType <= 0) {
            }
            int i3622 = i3;
            SecPowerUI.this.checkChargingNotification(i10, i12, i3622);
            if (z10) {
            }
            SecPowerUI.m2876$$Nest$mcheckBatteryHealthInterruptionStatus(SecPowerUI.this, i3622, z8);
            if (PowerUiRune.INCOMPATIBLE_CHARGER_CHECK) {
            }
            if (PowerUiRune.FULL_BATTERY_CHECK) {
            }
            secPowerUI4 = SecPowerUI.this;
            if (Settings.System.getInt(secPowerUI4.mContext.getContentResolver(), "LOW_BATTERY_DUMP", 0) == 1) {
            }
            if (z) {
            }
            SecPowerUI.m2875$$Nest$mcheckAbnormalChargingPad(SecPowerUI.this, i11);
            if (PowerUiRune.TIPS_NOTIFICATION) {
            }
            if (PowerUiRune.CHN_SMART_MANAGER) {
            }
            if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
            }
            if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
            }
            if (PowerUiRune.BATTERY_PROTECTION_TIPS_NOTIFICATION) {
            }
        }
    }

    /* renamed from: -$$Nest$mcheckAbnormalChargingPad, reason: not valid java name */
    public static void m2875$$Nest$mcheckAbnormalChargingPad(SecPowerUI secPowerUI, int i) {
        int i2 = i & 2097152;
        SecWarningsUI secWarningsUI = secPowerUI.mWarnings;
        if (i2 == 0 && (secPowerUI.mBatteryMiscEvent & 2097152) != 0) {
            SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secWarningsUI;
            secPowerNotificationWarnings.getClass();
            Log.i("PowerUI.Notification", "showAbnormalPadNotification");
            secPowerNotificationWarnings.showNotification(8);
            return;
        }
        if (i2 == 0 || (secPowerUI.mBatteryMiscEvent & 2097152) != 0) {
            return;
        }
        SecPowerNotificationWarnings secPowerNotificationWarnings2 = (SecPowerNotificationWarnings) secWarningsUI;
        secPowerNotificationWarnings2.getClass();
        Log.i("PowerUI.Notification", "dismissAbnormalPadNotification");
        secPowerNotificationWarnings2.cancelNotification(8);
    }

    /* renamed from: -$$Nest$mcheckBatteryHealthInterruptionStatus, reason: not valid java name */
    public static void m2876$$Nest$mcheckBatteryHealthInterruptionStatus(SecPowerUI secPowerUI, int i, boolean z) {
        int i2;
        int i3 = secPowerUI.mBatteryStatus;
        SecWarningsUI secWarningsUI = secPowerUI.mWarnings;
        if (i3 == 4 && ((i2 = secPowerUI.mBatteryHealth) == 3 || i2 == 7 || i2 == 6)) {
            if (i2 == 6 && (secPowerUI.mBatteryMiscEvent & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                Log.i("PowerUI", "Direct mode, so skip this logic");
                return;
            }
            Log.i("PowerUI", "Unhealthy state");
            if (secPowerUI.mBatteryHealthInterruptionScreenDimWakeLock == null) {
                PowerManager.WakeLock wakeLockNewWakeLock = secPowerUI.mPowerManager.newWakeLock(268435462, "PowerUI");
                secPowerUI.mBatteryHealthInterruptionScreenDimWakeLock = wakeLockNewWakeLock;
                if (PowerUiRune.KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION && secPowerUI.mBatteryHealth == 6) {
                    wakeLockNewWakeLock.acquire();
                } else {
                    wakeLockNewWakeLock.acquire(60000L);
                }
                if (secPowerUI.mBatteryHealth == 6) {
                    if (secPowerUI.mBatteryHealthInterruptionPartialWakeLock == null) {
                        secPowerUI.mBatteryHealthInterruptionPartialWakeLock = secPowerUI.mPowerManager.newWakeLock(1, "PowerUI");
                    }
                    secPowerUI.mBatteryHealthInterruptionPartialWakeLock.acquire();
                }
                ((SecPowerNotificationWarnings) secWarningsUI).showBatteryHealthInterruptionWarning();
            } else if (i == 8) {
                ((SecPowerNotificationWarnings) secWarningsUI).showBatteryHealthInterruptionWarning();
            }
        } else if (4 == i3 && 8 == secPowerUI.mBatteryHealth) {
            PowerManager.WakeLock wakeLock = secPowerUI.mBatteryHealthInterruptionScreenDimWakeLock;
            if (wakeLock == null) {
                PowerManager.WakeLock wakeLockNewWakeLock2 = secPowerUI.mPowerManager.newWakeLock(268435462, "PowerUI");
                secPowerUI.mBatteryHealthInterruptionScreenDimWakeLock = wakeLockNewWakeLock2;
                if (PowerUiRune.KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION) {
                    wakeLockNewWakeLock2.acquire();
                } else {
                    wakeLockNewWakeLock2.acquire(60000L);
                }
                if (secPowerUI.mBatteryHealthInterruptionPartialWakeLock == null) {
                    secPowerUI.mBatteryHealthInterruptionPartialWakeLock = secPowerUI.mPowerManager.newWakeLock(1, "PowerUI");
                }
                secPowerUI.mBatteryHealthInterruptionPartialWakeLock.acquire();
                ((SecPowerNotificationWarnings) secWarningsUI).showBatteryHealthInterruptionWarning();
            } else if (i == 3) {
                if (!PowerUiRune.KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION) {
                    wakeLock.acquire(60000L);
                }
                ((SecPowerNotificationWarnings) secWarningsUI).showBatteryHealthInterruptionWarning();
            }
        } else if (!z && secPowerUI.mIsWirelessOverheat) {
            ((SecPowerNotificationWarnings) secWarningsUI).showWirelessChargerDisconnectNotice();
        } else if (secPowerUI.mBatteryHealthInterruptionScreenDimWakeLock != null) {
            if (secPowerUI.mPlugType == 4 && i3 == 3 && secPowerUI.mBatteryHealth == 3) {
                secPowerUI.mDismissBatteryHealthInterruptionWarning = true;
            } else {
                SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secWarningsUI;
                Handler handler = secPowerNotificationWarnings.mHandler;
                handler.removeCallbacks(secPowerNotificationWarnings.mBatteryHealthInterruptionTask);
                handler.removeCallbacks(secPowerNotificationWarnings.mTemperatureLimitAlertTask);
                secPowerNotificationWarnings.cancelNotification(5);
                AlertDialog alertDialog = secPowerNotificationWarnings.mBatteryHealthInterruptionDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                secPowerUI.mDismissBatteryHealthInterruptionWarning = false;
            }
            if (PowerUiRune.KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION && secPowerUI.mBatteryHealthInterruptionScreenDimWakeLock.isHeld()) {
                secPowerUI.mBatteryHealthInterruptionScreenDimWakeLock.release();
            }
            PowerManager.WakeLock wakeLock2 = secPowerUI.mBatteryHealthInterruptionPartialWakeLock;
            if (wakeLock2 != null && wakeLock2.isHeld()) {
                secPowerUI.mBatteryHealthInterruptionPartialWakeLock.release();
            }
            secPowerUI.mBatteryHealthInterruptionScreenDimWakeLock = null;
        }
        if (secPowerUI.mDismissBatteryHealthInterruptionWarning && secPowerUI.mBatteryStatus == 2) {
            SecPowerNotificationWarnings secPowerNotificationWarnings2 = (SecPowerNotificationWarnings) secWarningsUI;
            Handler handler2 = secPowerNotificationWarnings2.mHandler;
            handler2.removeCallbacks(secPowerNotificationWarnings2.mBatteryHealthInterruptionTask);
            handler2.removeCallbacks(secPowerNotificationWarnings2.mTemperatureLimitAlertTask);
            secPowerNotificationWarnings2.cancelNotification(5);
            AlertDialog alertDialog2 = secPowerNotificationWarnings2.mBatteryHealthInterruptionDialog;
            if (alertDialog2 != null) {
                alertDialog2.dismiss();
            }
            secPowerUI.mDismissBatteryHealthInterruptionWarning = false;
        }
        int i4 = secPowerUI.mBatteryHealth;
        if (i != i4) {
            if (i == 5 || i4 == 5 || i == 9 || i4 == 9) {
                SettingsManager settingsManager = SettingsManager.getInstance();
                if (settingsManager != null && !settingsManager.getScreenWakeupOnPowerState()) {
                    Log.d("PowerUI", "Knox Customization: shouldWakeUp: not waking when battery health is changed");
                } else {
                    Log.d("PowerUI", "Overvoltage/Undervoltage status is changed so turn on the screen.");
                    secPowerUI.mPowerManager.wakeUp(SystemClock.uptimeMillis(), secPowerUI.mContext.getOpPackageName());
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* renamed from: -$$Nest$mcheckBatteryProtectionTipsNotification, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m2877$$Nest$mcheckBatteryProtectionTipsNotification(SecPowerUI secPowerUI, int i) {
        NetworkInfo activeNetworkInfo;
        ComponentName componentNameStartForegroundService;
        secPowerUI.getClass();
        TipsManager tipsManager = new TipsManager(secPowerUI.mContext);
        int i2 = secPowerUI.mPlugType;
        SharedPreferences sharedPreferences = tipsManager.context.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
        if (sharedPreferences != null) {
            boolean z = sharedPreferences.getBoolean("tipsNotiFirstTime", true);
            boolean z2 = Settings.Secure.getIntForUser(tipsManager.context.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, -2) == 1;
            if (i == 0) {
                if ((i2 == 1 || i2 == 4) && z && (activeNetworkInfo = ((ConnectivityManager) tipsManager.context.getSystemService("connectivity")).getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected() && z2) {
                    Intent intent = new Intent();
                    intent.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                    intent.putExtra("tips_extras", 8);
                    intent.putExtra("tips_extras2", "BATT_0003");
                    intent.putExtra("tips_extras3", tipsManager.context.getString(R.string.battery_protection_tips_noti_content));
                    intent.putExtra("tips_extras4", tipsManager.context.getString(R.string.battery_protection_tips_noti_title));
                    Slog.d("PowerUI.TipsManager", "All condition is OK, Try to show battery protection tips");
                    try {
                        componentNameStartForegroundService = tipsManager.context.startForegroundService(intent);
                    } catch (ForegroundServiceStartNotAllowedException e) {
                        Log.e("PowerUI.TipsManager", "Exception occur", e);
                        componentNameStartForegroundService = null;
                        if (componentNameStartForegroundService != null) {
                        }
                    } catch (SecurityException e2) {
                        Log.e("PowerUI.TipsManager", "Exception occur", e2);
                        componentNameStartForegroundService = null;
                        if (componentNameStartForegroundService != null) {
                        }
                    }
                    if (componentNameStartForegroundService != null) {
                        SharedPreferences sharedPreferences2 = tipsManager.context.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
                        SharedPreferences.Editor editorEdit = sharedPreferences2 != null ? sharedPreferences2.edit() : null;
                        if (editorEdit != null) {
                            editorEdit.putBoolean("tipsNotiFirstTime", false);
                            editorEdit.commit();
                        }
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$mcheckBatterySwellingStatus, reason: not valid java name */
    public static void m2878$$Nest$mcheckBatterySwellingStatus(SecPowerUI secPowerUI, int i, int i2) {
        if (i == secPowerUI.mBatterySwellingMode && i2 == secPowerUI.mBatteryStatus) {
            return;
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Battery swelling mode - priorBatterySwellingMode = ", " mBatterySwellingMode = ");
        sbM.append(secPowerUI.mBatterySwellingMode);
        sbM.append(" mBatteryStatus = ");
        sbM.append(secPowerUI.mBatteryStatus);
        Slog.d("PowerUI", sbM.toString());
        int i3 = secPowerUI.mBatterySwellingMode;
        SecWarningsUI secWarningsUI = secPowerUI.mWarnings;
        if (i3 <= 0 || secPowerUI.mBatteryStatus != 2) {
            SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secWarningsUI;
            secPowerNotificationWarnings.cancelNotification(4);
            if (secPowerNotificationWarnings.mSwellingDialog != null) {
                Log.d("PowerUI.Notification", "dismissBatterySwellingPopup()");
                secPowerNotificationWarnings.mSwellingDialog.dismiss();
                return;
            }
            return;
        }
        SecPowerNotificationWarnings secPowerNotificationWarnings2 = (SecPowerNotificationWarnings) secWarningsUI;
        secPowerNotificationWarnings2.getClass();
        if (i3 == 1) {
            Log.d("PowerUI.Notification", "showBatterySwellingNotice()");
            secPowerNotificationWarnings2.showNotification(4);
        } else {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i3, "Not battery low swelling mode, (ignore high swelling mode) so return ", "PowerUI.Notification");
        }
        if (secPowerUI.mBatterySwellingMode == 1) {
            Log.d("PowerUI.Notification", "showBatterySwellingPopup for low temp");
            Log.d("PowerUI.Notification", "showBatterySwellingLowTempPopup()");
            if (secPowerNotificationWarnings2.mSwellingDialog == null) {
                AlertDialog popupDialog = secPowerNotificationWarnings2.getPopupDialog(3);
                secPowerNotificationWarnings2.mSwellingDialog = popupDialog;
                if (popupDialog != null) {
                    popupDialog.setOnDismissListener(new SecPowerNotificationWarnings$$ExternalSyntheticLambda3(secPowerNotificationWarnings2, 2));
                    secPowerNotificationWarnings2.mSwellingDialog.show();
                    secPowerNotificationWarnings2.turnOnScreen();
                }
            }
        } else {
            Log.d("PowerUI.Notification", "Neither battery swelling mode nor low temp, so no popup is shown");
        }
        secPowerUI.dismissChargingNotice();
    }

    /* renamed from: -$$Nest$mcheckCoolDownStatus, reason: not valid java name */
    public static void m2879$$Nest$mcheckCoolDownStatus(SecPowerUI secPowerUI, Intent intent) {
        int i = secPowerUI.mBatteryOverheatLevel;
        secPowerUI.mBatteryOverheatLevel = intent.getIntExtra("battery_overheat_level", 0);
        TooltipPopup$$ExternalSyntheticOutline0.m(secPowerUI.mBatteryOverheatLevel, "PowerUI", new StringBuilder("Battery overheat Level = "));
        int i2 = secPowerUI.mBatteryOverheatLevel;
        if (i != i2) {
            Handler handler = secPowerUI.mHandler;
            SecWarningsUI secWarningsUI = secPowerUI.mWarnings;
            if (i2 == 2) {
                SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secWarningsUI;
                secPowerNotificationWarnings.getClass();
                Log.d("PowerUI.Notification", "showOverheatWarning()");
                secPowerNotificationWarnings.showNotification(6);
                handler.postDelayed(secPowerUI.mOverheatShutdownWarningTask, 30000L);
            } else if (i2 < 2 && 2 <= i) {
                SecPowerNotificationWarnings secPowerNotificationWarnings2 = (SecPowerNotificationWarnings) secWarningsUI;
                secPowerNotificationWarnings2.getClass();
                Log.d("PowerUI.Notification", "dismissWillOverheatShutdownWarning");
                secPowerNotificationWarnings2.mHandler.removeCallbacks(secPowerNotificationWarnings2.mOverheatShutdownTask);
                AlertDialog alertDialog = secPowerNotificationWarnings2.mWillOverheatShutdownWarningDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                handler.removeCallbacks(secPowerUI.mOverheatShutdownWarningTask);
                Log.d("PowerUI", "Battery overheat level recovered from shutdown");
            }
            int i3 = secPowerUI.mBatteryOverheatLevel;
            if (i3 != 0) {
                if (i3 == 1) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings3 = (SecPowerNotificationWarnings) secWarningsUI;
                    secPowerNotificationWarnings3.getClass();
                    Log.d("PowerUI.Notification", "showOverheatWarning()");
                    secPowerNotificationWarnings3.showNotification(6);
                    return;
                }
                return;
            }
            SecPowerNotificationWarnings secPowerNotificationWarnings4 = (SecPowerNotificationWarnings) secWarningsUI;
            secPowerNotificationWarnings4.getClass();
            Log.d("PowerUI.Notification", "dismissOverheatWarning()");
            AlertDialog alertDialog2 = secPowerNotificationWarnings4.mOverheatNoticeDialog;
            if (alertDialog2 != null) {
                alertDialog2.dismiss();
            }
            secPowerNotificationWarnings4.cancelNotification(6);
        }
    }

    /* renamed from: -$$Nest$mcheckHVchargerEnableConnection, reason: not valid java name */
    public static void m2880$$Nest$mcheckHVchargerEnableConnection(SecPowerUI secPowerUI, int i) {
        int i2 = secPowerUI.mBatteryCurrentEvent & 67108864;
        SecWarningsUI secWarningsUI = secPowerUI.mWarnings;
        if (i2 == 0 || (i & 67108864) != 0) {
            if ((i & 67108864) == 0 || i2 != 0) {
                return;
            }
            SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secWarningsUI;
            secPowerNotificationWarnings.getClass();
            Log.d("PowerUI.Notification", "dismissHVchargerEnableAlertDialog()");
            AlertDialog alertDialog = secPowerNotificationWarnings.mHVchargerEnablePopupDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                Log.d("PowerUI.Notification", "afcDisableChargerDialog is dimissed");
                return;
            }
            return;
        }
        if (DisplayUtils.isViewCoverClosed()) {
            return;
        }
        SecPowerNotificationWarnings secPowerNotificationWarnings2 = (SecPowerNotificationWarnings) secWarningsUI;
        secPowerNotificationWarnings2.getClass();
        Log.d("PowerUI.Notification", "showHVchargerEnableAlertDialog()");
        if (secPowerNotificationWarnings2.mHVchargerEnablePopupDialog == null) {
            AlertDialog popupDialog = secPowerNotificationWarnings2.getPopupDialog(14);
            secPowerNotificationWarnings2.mHVchargerEnablePopupDialog = popupDialog;
            if (popupDialog == null) {
                return;
            }
            popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.16
                public AnonymousClass16() {
                }

                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    SecPowerNotificationWarnings.this.mHVchargerEnablePopupDialog = null;
                }
            });
            secPowerNotificationWarnings2.turnOnScreen();
            secPowerNotificationWarnings2.mHVchargerEnablePopupDialog.show();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
    /* renamed from: -$$Nest$mcheckTipsNotification, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m2881$$Nest$mcheckTipsNotification(SecPowerUI secPowerUI, int i) {
        int i2;
        ComponentName componentNameStartForegroundService;
        secPowerUI.getClass();
        TipsManager tipsManager = new TipsManager(secPowerUI.mContext);
        int i3 = secPowerUI.mPlugType;
        int i4 = secPowerUI.mBatteryLevel;
        boolean z = i3 == 0;
        boolean z2 = i4 <= 30 && 30 < i;
        SharedPreferences sharedPreferences = tipsManager.context.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
        if (sharedPreferences == null) {
            Slog.w("PowerUI.TipsManager", "sharedPref is null");
            return;
        }
        if (!z || !z2) {
            Slog.d("PowerUI.TipsManager", "Not enough battery condition");
            return;
        }
        if (sharedPreferences.getBoolean("tipsNotiConfirmed", false)) {
            Slog.w("PowerUI.TipsManager", "User confirmed already");
            return;
        }
        if (sharedPreferences.getInt("tipsNotiRegisteredCount", 0) >= 3) {
            Slog.d("PowerUI.TipsManager", "Displayed refresh rate tip notification more then 3 times");
            return;
        }
        if (Settings.Secure.getInt(tipsManager.context.getContentResolver(), SettingsHelper.INDEX_REFRESH_RATE_MODE, 0) <= 0) {
            Slog.d("PowerUI.TipsManager", "Refresh rate are not 120hz");
            return;
        }
        if (System.currentTimeMillis() - sharedPreferences.getLong("tipsNotiLastTime", 0L) <= NotifCounterCoordinator.MINIMUM_STATUS_UPDATE_PERIOD_MS) {
            Slog.w("PowerUI.TipsManager", "Last tip notification has been registered within 1 week");
            return;
        }
        if (sharedPreferences.getBoolean("ignoreRUT", false)) {
            Slog.d("PowerUI.TipsManager", "TEST_IGNORE_RUT is true");
        } else {
            Context context = tipsManager.context;
            if (SettingsUtils.globalGetInt(context, SettingsHelper.INDEX_LOW_POWER_MODE, 0) == 1) {
                i2 = -1;
            } else {
                long batteryRemainingUsageTime = SemBatteryUtils.getBatteryRemainingUsageTime(context, 39);
                if (batteryRemainingUsageTime < 0) {
                    batteryRemainingUsageTime = SemBatteryUtils.getBatteryRemainingUsageTime(context, 5);
                }
                i2 = (int) batteryRemainingUsageTime;
            }
            if (i2 <= 0 || i2 >= 780) {
                Slog.d("PowerUI.TipsManager", "Ignored by RUT :" + i2);
                return;
            }
        }
        Intent intent = new Intent();
        intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$HighRefreshRatesSettingsActivity");
        intent.setFlags(268468224);
        Intent intent2 = new Intent("com.samsung.android.sm.TIPS_DELETED");
        intent2.setClassName("com.android.systemui", "com.android.systemui.power.tips.TipsNotificationService");
        Intent intent3 = new Intent();
        intent3.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent3.putExtra("tips_extras", 9);
        intent3.putExtra("tips_id", "120999");
        intent3.putExtra("tips_app_name", tipsManager.context.getPackageName());
        intent3.putExtra("tips_title", tipsManager.context.getString(R.string.motion_smoothness_tips_noti_title));
        intent3.putExtra("tips_text", tipsManager.context.getString(R.string.motion_smoothness_tips_noti_content));
        intent3.putExtra("tips_noti_category", "CATEGORY_RECOMMENDATION");
        intent3.putExtra("tips_action", intent);
        intent3.putExtra("tips_delete_action", intent2);
        intent3.putExtra("tips_delete_action_type", 1);
        intent3.putExtra("tips_condition", 1);
        intent3.putExtra("tips_noti_skip_add_action", 1);
        Slog.d("PowerUI.TipsManager", "All condition is OK, Show refresh rate tips notification");
        try {
            componentNameStartForegroundService = tipsManager.context.startForegroundService(intent3);
        } catch (ForegroundServiceStartNotAllowedException e) {
            Log.e("PowerUI.TipsManager", "Exception occur", e);
            componentNameStartForegroundService = null;
            if (componentNameStartForegroundService != null) {
            }
        } catch (SecurityException e2) {
            Log.e("PowerUI.TipsManager", "Exception occur", e2);
            componentNameStartForegroundService = null;
            if (componentNameStartForegroundService != null) {
            }
        }
        if (componentNameStartForegroundService != null) {
            SharedPreferences sharedPreferences2 = tipsManager.context.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
            SharedPreferences.Editor editorEdit = sharedPreferences2 != null ? sharedPreferences2.edit() : null;
            if (editorEdit != null) {
                editorEdit.putLong("tipsNotiLastTime", System.currentTimeMillis());
                editorEdit.commit();
            }
        }
    }

    /* renamed from: -$$Nest$mcheckTurnOffPsmNotification, reason: not valid java name */
    public static void m2882$$Nest$mcheckTurnOffPsmNotification(SecPowerUI secPowerUI, int i) {
        if (secPowerUI.mTurnOffPsmLevel == -1) {
            secPowerUI.mTurnOffPsmLevel = SettingsUtils.globalGetInt(secPowerUI.mContext, "turn_off_psm_trigger_level", 50);
            secPowerUI.mResolver.registerContentObserver(Settings.Global.getUriFor("turn_off_psm_trigger_level"), false, new ContentObserver(secPowerUI.mHandler) { // from class: com.android.systemui.power.SecPowerUI.8
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    SecPowerUI secPowerUI2 = SecPowerUI.this;
                    secPowerUI2.mTurnOffPsmLevel = SettingsUtils.globalGetInt(secPowerUI2.mContext, "turn_off_psm_trigger_level", 50);
                }
            }, -1);
        }
        int i2 = secPowerUI.mTurnOffPsmLevel;
        if (i >= i2 || secPowerUI.mBatteryLevel < i2 || SettingsUtils.globalGetInt(secPowerUI.mContext, SettingsHelper.INDEX_LOW_POWER_MODE, 0) != 1) {
            return;
        }
        Intent intent = new Intent("com.samsung.android.sm.ACTION_TURN_OFF_PSM_NOTI");
        intent.setComponent(PowerUiConstants.TURN_OFF_PSM_COMPONENT_NAME);
        secPowerUI.mContext.sendBroadcast(intent);
    }

    /* renamed from: -$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge, reason: not valid java name */
    public static void m2883$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge(SecPowerUI secPowerUI) {
        if (secPowerUI.mBatteryLevel >= secPowerUI.mLtcHighSocThreshold && secPowerUI.mProtectBatteryValue != 2) {
            secPowerUI.startScheduling();
            return;
        }
        Context context = secPowerUI.mContext;
        BatteryProtectionUtils batteryProtectionUtils = BatteryProtectionUtils.INSTANCE;
        boolean z = context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0).getBoolean("auto_on_protect_battery_timer_started", false);
        if (!z && secPowerUI.mBatteryLevel < secPowerUI.mLtcReleaseThreshold && secPowerUI.mProtectBatteryValue == 2) {
            Log.i("PowerUI", "send intent to DC, restore battery protection");
            PowerUtils.sendIntentToDc(secPowerUI.mContext, "com.samsung.android.sm.action.TURN_OFF_PROTECT_BATTERY_BY_LONG_TERM_TA");
        } else if (!z && secPowerUI.mBatteryLevel < secPowerUI.mLtcHighSocThreshold && SettingsUtils.globalGetInt(secPowerUI.mContext, "key_ltc_state", 0) == 1) {
            Log.i("PowerUI", "send intent to DC, turn off LTC soft notification");
            PowerUtils.sendIntentToDc(secPowerUI.mContext, "com.samsung.android.sm.action.TURN_OFF_SOFT_NOTI_BY_LONG_TERM_TA");
        }
        secPowerUI.clearScheduling();
    }

    /* renamed from: -$$Nest$mcheckTurnOnProtectBatteryByLongTa, reason: not valid java name */
    public static void m2884$$Nest$mcheckTurnOnProtectBatteryByLongTa(SecPowerUI secPowerUI) {
        if (secPowerUI.mPlugType != 0 && !BatteryProtectionUtils.isMaximumProtectionEnabled(secPowerUI.mContext)) {
            secPowerUI.startScheduling();
            return;
        }
        Context context = secPowerUI.mContext;
        BatteryProtectionUtils batteryProtectionUtils = BatteryProtectionUtils.INSTANCE;
        boolean z = context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0).getBoolean("auto_on_protect_battery_timer_started", false);
        int iGlobalGetInt = SettingsUtils.globalGetInt(secPowerUI.mContext, "auto_on_protect_battery", -1);
        if (!z && secPowerUI.mPlugType == 0 && iGlobalGetInt == 1) {
            Log.i("PowerUI", "send intent to DC, turn off protect battery");
            PowerUtils.sendIntentToDc(secPowerUI.mContext, "com.samsung.android.sm.action.TURN_OFF_PROTECT_BATTERY_BY_LONG_TERM_TA");
        }
        secPowerUI.clearScheduling();
    }

    /* renamed from: -$$Nest$mcheckUsbDamageDetectionStatus, reason: not valid java name */
    public static void m2885$$Nest$mcheckUsbDamageDetectionStatus(SecPowerUI secPowerUI, Intent intent) {
        boolean z = secPowerUI.mTemperatureHiccupState;
        secPowerUI.mTemperatureHiccupState = (intent.getIntExtra("misc_event", 0) & 8192) == 8192;
        ActionBarContextView$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("USB damage detection - oldTemperatureHiccupState : ", ", mTemperatureHiccupState : ", z), secPowerUI.mTemperatureHiccupState, "PowerUI");
        boolean z2 = secPowerUI.mTemperatureHiccupState;
        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secPowerUI.mWarnings;
        secPowerNotificationWarnings.mIsTemperatureHiccupState = z2;
        if (z == z2 || !z2) {
            return;
        }
        secPowerNotificationWarnings.showUsbDamageProtectionAlertDialog();
        secPowerUI.mContext.sendBroadcast(new Intent("com.samsung.systemui.power.action.USB_DAMAGE_POPUP_SHOW"));
    }

    /* renamed from: -$$Nest$mcheckWaterDetectionStatus, reason: not valid java name */
    public static void m2886$$Nest$mcheckWaterDetectionStatus(SecPowerUI secPowerUI, Intent intent) {
        boolean z = secPowerUI.mBatteryWaterConnector;
        boolean z2 = secPowerUI.mIsHiccupState;
        secPowerUI.mBatteryWaterConnector = (intent.getIntExtra("misc_event", 0) & 1) == 1;
        boolean z3 = (intent.getIntExtra("misc_event", 0) & 32) == 32;
        secPowerUI.mIsHiccupState = z3;
        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secPowerUI.mWarnings;
        secPowerNotificationWarnings.mIsHiccupState = z3;
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("SUPPORT_WATER_PROTECTION_POPUP - oldBatteryWaterConnector : ", ", mBatteryWaterConnector : ", z);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, secPowerUI.mBatteryWaterConnector, ", oldHiccupState : ", z2, ", mIsHiccupState : ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, secPowerUI.mIsHiccupState, "PowerUI");
        boolean z4 = secPowerUI.mIsHiccupState;
        if (z2 != z4) {
            if (z4) {
                secPowerNotificationWarnings.showWaterProtectionAlertDialog(secPowerUI.mBatteryWaterConnector);
                Log.i("PowerUI", "showWaterProtectionAlertDialog by hiccup state : show and sending intent ACTION_USB_DAMAGE_PROTECTION_POPUP_SHOW");
                secPowerUI.mContext.sendBroadcast(new Intent("com.samsung.systemui.power.action.USB_DAMAGE_POPUP_SHOW"));
                return;
            }
            return;
        }
        boolean z5 = secPowerUI.mBatteryWaterConnector;
        if (z != z5) {
            if (z5) {
                secPowerNotificationWarnings.showWaterProtectionAlertDialog(true);
                Log.i("PowerUI", "showWaterProtectionAlertDialog by mBatteryWaterConnector : show and sending intent ACTION_USB_DAMAGE_PROTECTION_POPUP_SHOW");
                secPowerUI.mContext.sendBroadcast(new Intent("com.samsung.systemui.power.action.USB_DAMAGE_POPUP_SHOW"));
                return;
            }
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("dismiss WaterProtectionAlertDialog - isWaterDetected = false mIsWaterDetected = "), secPowerNotificationWarnings.mIsWaterDetected, "PowerUI.Notification");
            secPowerNotificationWarnings.mIsWaterDetected = false;
            secPowerNotificationWarnings.mHandler.removeCallbacks(secPowerNotificationWarnings.mWaterProtectionAlertTask);
            PowerManager.WakeLock wakeLock = secPowerNotificationWarnings.mWaterProtectionPartialWakeLock;
            if (wakeLock != null) {
                wakeLock.release();
                secPowerNotificationWarnings.mWaterProtectionPartialWakeLock = null;
            }
            AlertDialog alertDialog = secPowerNotificationWarnings.mWaterProtectionAlertDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
        }
    }

    /* renamed from: -$$Nest$mcheckWirelessChargingFodStatus, reason: not valid java name */
    public static void m2887$$Nest$mcheckWirelessChargingFodStatus(SecPowerUI secPowerUI, Intent intent) {
        boolean z = secPowerUI.mWirelessFodState;
        secPowerUI.mWirelessFodState = (intent.getIntExtra("misc_event", 0) & 256) == 256;
        ActionBarContextView$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("SUPPORT_WIRELESS_CHARGER_FOD_POPUP - oldWirelessFodState : ", ", mWirelessFodState : ", z), secPowerUI.mWirelessFodState, "PowerUI");
        boolean z2 = secPowerUI.mWirelessFodState;
        if (z == z2 || !z2) {
            return;
        }
        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secPowerUI.mWarnings;
        secPowerNotificationWarnings.getClass();
        Log.d("PowerUI.Notification", "showWirelessFodAlertDialog");
        if (secPowerNotificationWarnings.mWirelessFodAlertDialog == null) {
            AlertDialog popupDialog = secPowerNotificationWarnings.getPopupDialog(11);
            secPowerNotificationWarnings.mWirelessFodAlertDialog = popupDialog;
            if (popupDialog == null) {
                return;
            }
            popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.14
                public AnonymousClass14() {
                }

                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    SecPowerNotificationWarnings.this.mWirelessFodAlertDialog = null;
                }
            });
            secPowerNotificationWarnings.mWirelessFodAlertDialog.show();
        }
    }

    /* renamed from: -$$Nest$mfindBatteryLevelBucket, reason: not valid java name */
    public static int m2889$$Nest$mfindBatteryLevelBucket(SecPowerUI secPowerUI, int i) {
        if (i >= secPowerUI.mLowBatteryAlertCloseLevel) {
            return 1;
        }
        int[] iArr = secPowerUI.mLowBatteryReminderLevels;
        if (i > iArr[0]) {
            return 0;
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            if (i <= iArr[length]) {
                return (-1) - length;
            }
        }
        throw new RuntimeException("not possible!");
    }

    /* renamed from: -$$Nest$mupdateBatteryNotificationLanguage, reason: not valid java name */
    public static void m2890$$Nest$mupdateBatteryNotificationLanguage(SecPowerUI secPowerUI) throws NumberFormatException {
        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secPowerUI.mWarnings;
        if (secPowerNotificationWarnings.mWarning) {
            secPowerNotificationWarnings.updateNotification();
            Log.d("PowerUI", "Language is changed so notify LowBatteryNotification again");
        }
        Log.d("PowerUI", "Language is changed so notify ChargingNotification again");
        secPowerUI.checkChargingNotification(secPowerUI.mPlugType, secPowerUI.mBatteryStatus, secPowerUI.mBatteryHealth);
        if (PowerUiRune.INCOMPATIBLE_CHARGER_CHECK && secPowerUI.mBatteryOnline == 0) {
            AlertDialog alertDialog = secPowerNotificationWarnings.mIncompatibleChargerDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            Log.d("PowerUI.Notification", "dismissing incompatible charger notification");
            secPowerNotificationWarnings.cancelNotification(3);
            Log.d("PowerUI", "Language is changed so notify incompatible charger again");
            secPowerNotificationWarnings.showIncompatibleChargerNotice();
        }
        if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION && BatteryProtectionUtils.isSleepChargingOn(secPowerUI.mContext)) {
            secPowerUI.checkAdaptiveProtectionNotification();
        }
        if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
            secPowerUI.checkBatteryProtectionNotification();
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.power.SecPowerUI$5] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.power.SecPowerUI$6] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.power.SecPowerUI$7] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.android.systemui.power.SecPowerUI$9] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.systemui.power.SecPowerUI$11] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.systemui.power.SecPowerUI$12] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.systemui.power.SecPowerUI$13] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.power.SecPowerUI$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.power.SecPowerUI$2] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.power.SecPowerUI$3] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.power.SecPowerUI$4] */
    public SecPowerUI(Context context, BroadcastDispatcher broadcastDispatcher, CommandQueue commandQueue, SecWarningsUI secWarningsUI, WakefulnessLifecycle wakefulnessLifecycle, PowerManager powerManager, UserTracker userTracker, DozeChargingHelper dozeChargingHelper, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper, SystemEventCoordinator systemEventCoordinator) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mLowBatteryReminderLevels = new int[2];
        this.mReceiver = new Receiver();
        this.mBatteryLevel = 100;
        this.mBatteryStatus = 1;
        this.mPlugType = -1;
        this.mIsRunningLowBatteryTask = false;
        this.mIsRunningStopPowerSoundTask = false;
        this.mBatterySwellingMode = 0;
        this.mBatteryHighVoltageCharger = false;
        this.mFullyConnected = true;
        this.mBatterySlowCharger = false;
        this.mIsChangedStringAfterCharging = false;
        this.mBatteryChargingType = 0;
        this.mBatteryOnline = -1;
        this.mCurrentChargingAnimation = null;
        this.mIsWirelessMisalignTask = false;
        this.mSContextManager = null;
        this.mIsMotionDetectionSupported = false;
        this.mIsSContextEnabled = false;
        this.mIsSContextListenerRegistered = false;
        this.mIsDeviceMoving = true;
        this.mWirelessFodState = false;
        this.mBatteryWaterConnector = false;
        this.mIsHiccupState = false;
        this.mTemperatureHiccupState = false;
        this.mDismissBatteryHealthInterruptionWarning = false;
        this.mBatteryHealth = 1;
        this.mIsWirelessOverheat = false;
        this.mIsShutdownTaskDelayed = false;
        this.mBatteryOverheatLevel = 0;
        this.mCallState = 0;
        this.mBootCompleted = false;
        this.mBatteryCurrentEvent = 0;
        this.mIsChangedBatteryProtectionOnCharging = false;
        this.mProtectBatteryValue = -1;
        this.mIsAfterAdaptiveProtection = false;
        this.mBatteryMiscEvent = 0;
        this.mProtectionEvent = 0;
        this.mTurnOffPsmLevel = -1;
        this.mLowBatteryWarningTask = new Runnable() { // from class: com.android.systemui.power.SecPowerUI.1
            @Override // java.lang.Runnable
            public final void run() {
                Log.d("PowerUI", "mLowBatteryWarningTask");
                SecPowerUI secPowerUI = SecPowerUI.this;
                secPowerUI.mIsRunningLowBatteryTask = false;
                ((SecPowerNotificationWarnings) secPowerUI.mWarnings).showLowBatteryWarning(true);
            }
        };
        this.mOverheatShutdownWarningTask = new Runnable() { // from class: com.android.systemui.power.SecPowerUI.2
            @Override // java.lang.Runnable
            public final void run() {
                SecPowerUI secPowerUI = SecPowerUI.this;
                if (secPowerUI.mCallState != 0) {
                    Log.d("PowerUI", "Battery overheat but on call, so delayed power off");
                    SecPowerUI.this.mIsShutdownTaskDelayed = true;
                    return;
                }
                SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secPowerUI.mWarnings;
                secPowerNotificationWarnings.getClass();
                Log.d("PowerUI.Notification", "runOverheatShutdownTask - Delay time = 10000");
                secPowerNotificationWarnings.mHandler.postDelayed(secPowerNotificationWarnings.mOverheatShutdownTask, 10000);
                ((SecPowerNotificationWarnings) SecPowerUI.this.mWarnings).showWillOverheatShutdownWarning();
            }
        };
        this.mPhoneStateListener = new PhoneStateListener() { // from class: com.android.systemui.power.SecPowerUI.3
            @Override // android.telephony.PhoneStateListener
            public final void onCallStateChanged(int i, String str) {
                ActionBarContextView$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "mPhoneStateListener onCallStateChanged(): state= ", " mIsShutdownTaskDelayed = "), SecPowerUI.this.mIsShutdownTaskDelayed, "PowerUI");
                SecPowerUI secPowerUI = SecPowerUI.this;
                secPowerUI.mCallState = i;
                if (i == 0 && secPowerUI.mIsShutdownTaskDelayed) {
                    secPowerUI.mIsShutdownTaskDelayed = false;
                    if (secPowerUI.mBatteryOverheatLevel == 2) {
                        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secPowerUI.mWarnings;
                        secPowerNotificationWarnings.getClass();
                        Log.d("PowerUI.Notification", "runOverheatShutdownTask - Delay time = 10000");
                        secPowerNotificationWarnings.mHandler.postDelayed(secPowerNotificationWarnings.mOverheatShutdownTask, 10000);
                        ((SecPowerNotificationWarnings) SecPowerUI.this.mWarnings).showWillOverheatShutdownWarning();
                    }
                }
                ((SecPowerNotificationWarnings) SecPowerUI.this.mWarnings).mIsInCall = i != 0;
            }
        };
        this.mAfterChargingNoticeTask = new Runnable() { // from class: com.android.systemui.power.SecPowerUI.4
            @Override // java.lang.Runnable
            public final void run() {
                SecPowerUI secPowerUI = SecPowerUI.this;
                secPowerUI.mIsChangedStringAfterCharging = true;
                int i = secPowerUI.mBatteryChargingType;
                switch (i) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        i = 11;
                        break;
                    case 6:
                    case 7:
                        i = 10;
                        break;
                }
                secPowerUI.mBatteryChargingType = i;
                SecWarningsUI secWarningsUI2 = secPowerUI.mWarnings;
                int i2 = secPowerUI.mSuperFastCharger;
                SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secWarningsUI2;
                secPowerNotificationWarnings.mOldChargingType = secPowerNotificationWarnings.mChargingType;
                secPowerNotificationWarnings.mChargingType = i;
                secPowerNotificationWarnings.mSuperFastCharger = i2;
                ((SecPowerNotificationWarnings) secWarningsUI2).showChargingNotice();
            }
        };
        this.mRestoreDisplayStateTask = new Runnable() { // from class: com.android.systemui.power.SecPowerUI.5
            @Override // java.lang.Runnable
            public final void run() {
                SecPowerUI.this.mDozeChargingHelper.restoreDisplayStateWhenDozeCharging();
            }
        };
        this.mStopPowerSoundTask = new Runnable() { // from class: com.android.systemui.power.SecPowerUI.6
            @Override // java.lang.Runnable
            public final void run() {
                Log.d("PowerUI", "mStopPowerSoundTask");
                SecPowerUI secPowerUI = SecPowerUI.this;
                secPowerUI.mIsRunningStopPowerSoundTask = false;
                ((SecPowerNotificationWarnings) secPowerUI.mWarnings).stopPowerSound(1);
            }
        };
        this.mSContextListener = new SContextListener() { // from class: com.android.systemui.power.SecPowerUI.7
            public final void onSContextChanged(SContextEvent sContextEvent) {
                if (sContextEvent.scontext.getType() == 46) {
                    int action = sContextEvent.getWirelessChargingDetectionContext().getAction();
                    if (action == 0) {
                        Log.d("PowerUI", "SContextListener - No Move");
                        SecPowerUI.this.mIsDeviceMoving = false;
                        return;
                    }
                    if (action != 1) {
                        return;
                    }
                    Log.d("PowerUI", "SContextListener - Move");
                    SecPowerUI secPowerUI = SecPowerUI.this;
                    secPowerUI.mIsDeviceMoving = true;
                    if (secPowerUI.mIsSContextListenerRegistered) {
                        if (secPowerUI.mPlugType == 4 && secPowerUI.mBatteryStatus == 2) {
                            return;
                        }
                        Log.d("PowerUI", "Unregister SContextListener - From Listener");
                        SecPowerUI secPowerUI2 = SecPowerUI.this;
                        secPowerUI2.mSContextManager.unregisterListener(secPowerUI2.mSContextListener, 46);
                        SecPowerUI.this.mIsSContextListenerRegistered = false;
                    }
                }
            }
        };
        this.mWirelessMisalignTimeoutTask = new SecPowerUI$$ExternalSyntheticLambda0(this, 0);
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.power.SecPowerUI.9
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                SystemClock.elapsedRealtime();
                SecPowerUI secPowerUI = SecPowerUI.this;
                secPowerUI.getClass();
                SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secPowerUI.mWarnings;
                secPowerNotificationWarnings.getClass();
                Log.d("PowerUI.Notification", "dismissUnintentionallyLcdOnNotice");
                secPowerNotificationWarnings.dismissUnintentionalLcdOnWindow();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                SecPowerUI.this.getClass();
            }
        };
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.power.SecPowerUI.10
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                Slog.i("PowerUI", "onUserChanged : " + i);
                SecPowerUI secPowerUI = SecPowerUI.this;
                ((SecPowerNotificationWarnings) secPowerUI.mWarnings).updateNotification();
                if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION && secPowerUI.mProtectBatteryValue == 4 && i != 0) {
                    Slog.i("PowerUI", "start battery protection InitService at user " + i);
                    Intent intent = new Intent();
                    intent.setPackage(PowerUiConstants.DC_PACKAGE_NAME);
                    intent.setAction("com.samsung.android.sm.service.action.ACTION_BATTERY_PROTECTION_INIT_SERVICE");
                    context2.startService(intent);
                }
            }
        };
        this.protectBatteryObserver = new ContentObserver(handler) { // from class: com.android.systemui.power.SecPowerUI.11
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                SecPowerUI secPowerUI = SecPowerUI.this;
                secPowerUI.mIsChangedBatteryProtectionOnCharging = true;
                int i = secPowerUI.mProtectBatteryValue;
                secPowerUI.mProtectBatteryValue = BatteryProtectionUtils.getProtectBatteryValue(secPowerUI.mContext);
                if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
                    ((SecPowerNotificationWarnings) SecPowerUI.this.mWarnings).cancelNotification(9);
                    SecPowerUI secPowerUI2 = SecPowerUI.this;
                    int i2 = secPowerUI2.mProtectBatteryValue;
                    if ((i == 0 && i2 != 0) || (i != 0 && i2 == 0)) {
                        secPowerUI2.checkBatteryProtectionNotification();
                    }
                }
                if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION && i == 4) {
                    SecPowerUI secPowerUI3 = SecPowerUI.this;
                    if (secPowerUI3.mProtectBatteryValue != 4) {
                        secPowerUI3.setSleepChargingOff();
                        SecPowerUI.this.mIsAfterAdaptiveProtection = false;
                    }
                }
                if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                    SecPowerUI.m2883$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge(SecPowerUI.this);
                } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
                    SecPowerUI.m2884$$Nest$mcheckTurnOnProtectBatteryByLongTa(SecPowerUI.this);
                }
                SecPowerUI secPowerUI4 = SecPowerUI.this;
                int i3 = secPowerUI4.mProtectBatteryValue;
                if ((i != 1 || i3 == 1) && (i == 1 || i3 != 1)) {
                    return;
                }
                secPowerUI4.checkChargingNotification(secPowerUI4.mPlugType, secPowerUI4.mBatteryStatus, secPowerUI4.mBatteryHealth);
            }
        };
        this.maximumThresholdObserver = new ContentObserver(handler) { // from class: com.android.systemui.power.SecPowerUI.12
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                super.onChange(z);
                SecPowerUI secPowerUI = SecPowerUI.this;
                secPowerUI.checkChargingNotification(secPowerUI.mPlugType, secPowerUI.mBatteryStatus, secPowerUI.mBatteryHealth);
                SecPowerUI.this.checkBatteryProtectionNotification();
                SecPowerUI secPowerUI2 = SecPowerUI.this;
                secPowerUI2.mIsChangedBatteryProtectionOnCharging = true;
                if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                    SecPowerUI.m2883$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge(secPowerUI2);
                }
            }
        };
        this.ownerSetupWizardObserver = new ContentObserver(handler) { // from class: com.android.systemui.power.SecPowerUI.13
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                super.onChange(z);
                if (Settings.Secure.getIntForUser(SecPowerUI.this.mContext.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, 0) != 0) {
                    Slog.d("PowerUI", "Setup wizard is completed");
                    SecPowerUI secPowerUI = SecPowerUI.this;
                    secPowerUI.checkChargingNotification(secPowerUI.mPlugType, secPowerUI.mBatteryStatus, secPowerUI.mBatteryHealth);
                }
            }
        };
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mCommandQueue = commandQueue;
        this.mWarnings = secWarningsUI;
        this.mPowerManager = powerManager;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mUserTracker = userTracker;
        this.mDozeChargingHelper = dozeChargingHelper;
        this.mFaceWidgetNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
        this.mSystemEventCoordinator = systemEventCoordinator;
    }

    public static WindowManager.LayoutParams getLayoutParam(String str) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2, UcmAgentService.ERROR_APDU_CREATION, -3);
        if (LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
            layoutParams.semAddExtensionFlags(262144);
        }
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTrustedOverlay();
        layoutParams.setTitle(str);
        return layoutParams;
    }

    public final void checkAdaptiveProtectionNotification() {
        Slog.i("PowerUI", "checkAdaptiveProtectionNotification : " + this.mSleepChargingEvent + ", chargingStartTime : " + this.mChargingStartTime + ", lev : " + this.mBatteryLevel);
        boolean zEqualsIgnoreCase = "on".equalsIgnoreCase(this.mSleepChargingEvent);
        SecWarningsUI secWarningsUI = this.mWarnings;
        if (zEqualsIgnoreCase && this.mProtectBatteryValue == 4) {
            if (this.mBatteryLevel != 100) {
                Slog.i("PowerUI", "show AdaptiveProtectionNotification");
                ((SecPowerNotificationWarnings) secWarningsUI).showAdaptiveProtectionNotification(this.mChargingStartTime);
            }
            BatteryProtectionUtils.setSleepChargingStatus(1, this.mContext);
            Settings.Global.putString(this.mContext.getContentResolver(), "sleep_charging_finish_time", this.mChargingStartTime);
            return;
        }
        if (!"update".equalsIgnoreCase(this.mSleepChargingEvent) || this.mProtectBatteryValue != 4) {
            if (!"off".equalsIgnoreCase(this.mSleepChargingEvent)) {
                Slog.i("PowerUI", "dismiss AdaptiveProtectionNotification");
                setSleepChargingOff();
                return;
            } else {
                Slog.i("PowerUI", "off AdaptiveProtectionNotification");
                setSleepChargingOff();
                this.mIsAfterAdaptiveProtection = true;
                return;
            }
        }
        if (this.mBatteryLevel != 100) {
            Slog.i("PowerUI", "update AdaptiveProtectionNotification");
            ((SecPowerNotificationWarnings) secWarningsUI).showAdaptiveProtectionNotification(this.mChargingStartTime);
        }
        BatteryProtectionUtils.setSleepChargingStatus(2, this.mContext);
        Settings.Global.putString(this.mContext.getContentResolver(), "sleep_charging_finish_time", this.mChargingStartTime);
    }

    public final void checkBatteryProtectionNotification() {
        boolean zIsSleepChargingOn = BatteryProtectionUtils.isSleepChargingOn(this.mContext);
        SecWarningsUI secWarningsUI = this.mWarnings;
        if (!zIsSleepChargingOn && this.mProtectBatteryValue > 0 && this.mBatteryStatus >= 4 && this.mBatteryHealth == 2 && this.mBatteryOverheatLevel == 0) {
            int i = this.mProtectionEvent;
            int i2 = this.mBatteryMiscEvent;
            if (i != 0 || BatteryProtectionUtils.isProtectedFullyByMaximum(i2)) {
                ((SecPowerNotificationWarnings) secWarningsUI).showNotification(9);
                return;
            }
        }
        if (!BatteryProtectionUtils.isSleepChargingOn(this.mContext) && this.mProtectBatteryValue != 0 && this.mPlugType != 0 && this.mBatteryStatus != 2 && this.mBatteryHealth == 2 && this.mBatteryOverheatLevel == 0) {
            int i3 = this.mProtectionEvent;
            int i4 = this.mBatteryMiscEvent;
            if (i3 != 0 || BatteryProtectionUtils.isProtectedFullyByMaximum(i4)) {
                return;
            }
        }
        ((SecPowerNotificationWarnings) secWarningsUI).cancelNotification(9);
    }

    public final void checkChargingNotification(int i, int i2, int i3) {
        int i4 = this.mBatteryStatus;
        boolean z = false;
        SecWarningsUI secWarningsUI = this.mWarnings;
        if (i4 == 2) {
            int i5 = this.mPlugType;
            if (i5 == 1 || i5 == 2) {
                if (i == 4) {
                    ((SecPowerNotificationWarnings) secWarningsUI).showChargingTypeSwitchedNotice(true);
                }
            } else if (i5 == 4) {
                if (i == 1 || i == 2) {
                    ((SecPowerNotificationWarnings) secWarningsUI).showChargingTypeSwitchedNotice(false);
                }
                if (this.mIsMotionDetectionSupported && this.mIsSContextEnabled && !this.mIsSContextListenerRegistered) {
                    Log.d("PowerUI", "Register SContextListener");
                    this.mSContextManager.registerListener(this.mSContextListener, 46);
                    this.mIsSContextListenerRegistered = true;
                }
                if (!this.mWirelessFodState && 4 != i) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secWarningsUI;
                    secPowerNotificationWarnings.getClass();
                    Log.d("PowerUI.Notification", "dismissWirelessFodAlertDialog");
                    AlertDialog alertDialog = secPowerNotificationWarnings.mWirelessFodAlertDialog;
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                }
            } else {
                dismissChargingNotice();
            }
            if (this.mBatteryChargingType != 0 && this.mBatterySwellingMode != 1) {
                ((SecPowerNotificationWarnings) secWarningsUI).showChargingNotice();
                boolean z2 = PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION;
                Handler handler = this.mHandler;
                if (z2) {
                    if (this.mIsChangedStringAfterCharging || this.mIsAfterAdaptiveProtection) {
                        handler.removeCallbacks(this.mAfterChargingNoticeTask);
                    } else if (handler.hasCallbacks(this.mAfterChargingNoticeTask)) {
                        Slog.d("PowerUI", "Already registered mAfterChargingNoticeTask, so skip");
                    } else {
                        handler.postDelayed(this.mAfterChargingNoticeTask, 5000L);
                    }
                } else if (this.mIsChangedStringAfterCharging) {
                    handler.removeCallbacks(this.mAfterChargingNoticeTask);
                } else if (handler.hasCallbacks(this.mAfterChargingNoticeTask)) {
                    Slog.d("PowerUI", "Already registered mAfterChargingNoticeTask, so skip");
                } else {
                    handler.postDelayed(this.mAfterChargingNoticeTask, 5000L);
                }
            }
        } else {
            dismissChargingNotice();
            checkWirelessChargerMotionDetection(i);
        }
        if (i == 4 && this.mPlugType == 0) {
            if (i2 == 4 && i3 == 3) {
                z = true;
            }
            checkWirelessChargerMotionDetection(i);
            if (!z && !this.mIsDeviceMoving) {
                Log.e("PowerUI", "Wireless charger has been disconnected but this is no move case, so we do nothing !!");
            } else if (z) {
                ((SecPowerNotificationWarnings) secWarningsUI).showWirelessChargerDisconnectNotice();
            }
        }
    }

    public final void checkOverheatShutdownHappened() {
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("checkOverheatShutdownHappened, boot completed : "), this.mBootCompleted, "PowerUI");
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("com.android.systemui.power_overheat_shutdown_happened", 0);
        if (sharedPreferences != null) {
            if (!sharedPreferences.getBoolean("OverheatShutdownHappened", false)) {
                Log.d("PowerUI", "Not an overheat shutdown case");
                return;
            }
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putBoolean("OverheatShutdownHappened", false);
            editorEdit.commit();
            this.mContext.sendBroadcast(new Intent("com.android.systemui.power.action.ACTION_CLEAR_SHUTDOWN"));
            SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) this.mWarnings;
            if (PowerUtils.isShutdownOn(secPowerNotificationWarnings.mContext)) {
                Log.d("PowerUI.Notification", "don't show Overheat shutdown notice while Shutdown is ON");
                return;
            }
            if (secPowerNotificationWarnings.mWillOverheatShutdownWarningDialog != null) {
                Log.d("PowerUI.Notification", "don't show Overheat shutdown notice while Over heat shutdown warning");
                return;
            }
            Log.d("PowerUI.Notification", "showOverheatShutdownHappenedNotice()");
            Log.d("PowerUI.Notification", "showOverheatShutdownHappenedPopUp()");
            if (secPowerNotificationWarnings.mOverheatShutdownHappenedDialog == null) {
                AlertDialog popupDialog = secPowerNotificationWarnings.getPopupDialog(9);
                secPowerNotificationWarnings.mOverheatShutdownHappenedDialog = popupDialog;
                if (popupDialog == null) {
                    return;
                }
                popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.10
                    public AnonymousClass10() {
                    }

                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        SecPowerNotificationWarnings.this.mOverheatShutdownHappenedDialog = null;
                    }
                });
                secPowerNotificationWarnings.mOverheatShutdownHappenedDialog.show();
                secPowerNotificationWarnings.mOverheatShutdownHappenedDialog.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.11
                    public AnonymousClass11() {
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SecPowerNotificationWarnings secPowerNotificationWarnings2 = SecPowerNotificationWarnings.this;
                        secPowerNotificationWarnings2.getClass();
                        Log.d("PowerUI.Notification", "dismissOverheatShutdownHappenedPopUp()");
                        AlertDialog alertDialog = secPowerNotificationWarnings2.mOverheatShutdownHappenedDialog;
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                    }
                });
                secPowerNotificationWarnings.turnOnScreen();
            }
        }
    }

    public final void checkWirelessChargerMotionDetection(int i) {
        if (this.mIsMotionDetectionSupported && this.mIsSContextListenerRegistered && this.mIsDeviceMoving && i == 4) {
            Log.d("PowerUI", "Unregister SContextListener - From Check charging type");
            this.mSContextManager.unregisterListener(this.mSContextListener, 46);
            this.mIsSContextListenerRegistered = false;
        }
    }

    public final void clearScheduling() {
        Context context = this.mContext;
        BatteryProtectionUtils batteryProtectionUtils = BatteryProtectionUtils.INSTANCE;
        boolean z = PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE;
        BatteryProtectionUtils batteryProtectionUtils2 = BatteryProtectionUtils.INSTANCE;
        if (z) {
            batteryProtectionUtils2.getClass();
            Settings.Global.putLong(context.getContentResolver(), "ltc_highsoc_exceed_time", 0L);
        } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
            batteryProtectionUtils2.getClass();
            Settings.Global.putString(context.getContentResolver(), "charger_connected_time", "");
        }
        batteryProtectionUtils2.getClass();
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putBoolean("auto_on_protect_battery_timer_started", false);
            editorEdit.commit();
        }
    }

    public final void dismissChargingNotice() {
        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) this.mWarnings;
        secPowerNotificationWarnings.mDoNotShowChargingNotice = false;
        secPowerNotificationWarnings.mChargingType = 0;
        secPowerNotificationWarnings.mOldChargingType = 0;
        secPowerNotificationWarnings.mChargingTime = 0L;
        secPowerNotificationWarnings.mShowChargingNotice = false;
        AlertDialog alertDialog = secPowerNotificationWarnings.mSlowByChargerConnectionInfoDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            secPowerNotificationWarnings.mSlowByChargerConnectionInfoDialog = null;
        }
        secPowerNotificationWarnings.cancelNotification(2);
        this.mHandler.removeCallbacks(this.mAfterChargingNoticeTask);
        this.mIsChangedStringAfterCharging = false;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("OneUI version : " + SystemProperties.getInt("ro.build.version.oneui", 0));
        printWriter.println("Debug level : " + DeviceType.getDebugLevel());
        printWriter.println("CountryISO : " + SystemProperties.get("ro.csc.countryiso_code", ""));
        StringBuilder sb = new StringBuilder("BatteryProtection default value : ");
        Context context = this.mContext;
        BatteryProtectionUtils batteryProtectionUtils = BatteryProtectionUtils.INSTANCE;
        sb.append(SettingsUtils.globalGetInt(context, "battery_protection_default_value", 0));
        printWriter.println(sb.toString());
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("BatteryProtection value : "), this.mProtectBatteryValue, printWriter, "Maximum threshold value : ");
        sbM.append(SettingsUtils.globalGetInt(this.mContext, "battery_protection_threshold", Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE));
        printWriter.println(sbM.toString());
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
        if (sharedPreferences != null) {
            if (PowerUiRune.BATTERY_PROTECTION_TIPS_NOTIFICATION) {
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("BatteryProtectionTips showed : "), !sharedPreferences.getBoolean("tipsNotiFirstTime", true), printWriter);
            }
            if (PowerUiRune.TIPS_NOTIFICATION) {
                printWriter.println("Max tips notification count : " + sharedPreferences.getInt("tipsNotiRegisteredCount", 0));
            }
        }
        SharedPreferences sharedPreferences2 = this.mContext.getSharedPreferences("charging_shared_pref", 0);
        if (sharedPreferences2 != null) {
            printWriter.println("EU charge content showed : " + sharedPreferences2.getBoolean("key_first_charge_content_added", false));
        }
        StringBuilder sbM2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("Nowbar Supported : "), PowerUiRune.CHARGING_VI_NOW_BAR, printWriter, "Blur feature supported : "), PowerUiRune.WINDOW_BLUR_SUPPORTED, printWriter, "Reduce blur transparency enabled : "), Settings.System.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY, 0) == 1, printWriter, "Animator duration scale : ");
        sbM2.append(SettingsUtils.globalGetInt(this.mContext, SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1));
        sbM2.append("x");
        printWriter.println(sbM2.toString());
        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) this.mWarnings;
        secPowerNotificationWarnings.getClass();
        if (PowerUiRune.CHN_SMART_MANAGER) {
            return;
        }
        HandlerWrapper handlerWrapper = new HandlerWrapper();
        secPowerNotificationWarnings.mHandlerWrapper = handlerWrapper;
        handlerWrapper.mWorker.post(new SecPowerNotificationWarnings$$ExternalSyntheticLambda0(secPowerNotificationWarnings, 2));
    }

    public final void onChargerAnimationEnd() {
        Slog.i("PowerUI", "onChargerAnimationEnd");
        removeChargerView();
        this.mHandler.postDelayed(this.mRestoreDisplayStateTask, 1000L);
    }

    public final void removeChargerView() {
        WindowManager windowManager;
        WindowManager windowManager2;
        String str = this.mCurrentChargingAnimation;
        if (str != null) {
            switch (str) {
                case "old_charging_vi":
                    ChargerAnimationView chargerAnimationView = this.mChargerAnimationView;
                    if (chargerAnimationView != null && (windowManager = this.mChargerAnimationWindowManager) != null) {
                        windowManager.removeView(chargerAnimationView);
                        this.mChargerAnimationView = null;
                    }
                    this.mChargerAnimationWindowManager = null;
                    break;
                case "flip_cover_charging_vi":
                    ChargerFlipCoverView chargerFlipCoverView = this.mChargerFlipCoverView;
                    if (chargerFlipCoverView != null && (windowManager2 = this.mChargerAnimationWindowManager) != null) {
                        windowManager2.removeView(chargerFlipCoverView);
                        ChargerFlipCoverView chargerFlipCoverView2 = this.mChargerFlipCoverView;
                        chargerFlipCoverView2.getBatteryProgressbar().cancelAnimation();
                        chargerFlipCoverView2.chargerAnimationSet.removeAllListeners();
                        chargerFlipCoverView2.chargerAnimationSet.cancel();
                        chargerFlipCoverView2.animationListener = null;
                        Log.d("PowerUI.ChargerFlipCoverView", "Animation Stopped");
                        this.mChargerFlipCoverView = null;
                    }
                    this.mChargerAnimationWindowManager = null;
                    break;
                case "now_bar_charging_vi":
                    removeNowBarView();
                    break;
                case "large_cover_flip_now_bar_charging_vi":
                    removeNowBarView();
                    this.mFaceWidgetNotificationControllerWrapper.removeItem(PowerUtils.getNowBarItem());
                    ChargerCoverNowBarView chargerCoverNowBarView = this.mChargerCoverNormalSubScreenView;
                    if (chargerCoverNowBarView != null) {
                        chargerCoverNowBarView.getBatteryProgressbar().cancelAnimation();
                        chargerCoverNowBarView.chargerAnimationSet.removeAllListeners();
                        chargerCoverNowBarView.chargerAnimationSet.cancel();
                        chargerCoverNowBarView.animationListener = null;
                        Log.d("PowerUI.ChargerCoverNowBarView", "Animation Stopped");
                        this.mChargerCoverNormalSubScreenView = null;
                    }
                    ChargerCoverNowBarView chargerCoverNowBarView2 = this.mChargerCoverExpandSubScreenView;
                    if (chargerCoverNowBarView2 != null) {
                        chargerCoverNowBarView2.getBatteryProgressbar().cancelAnimation();
                        chargerCoverNowBarView2.chargerAnimationSet.removeAllListeners();
                        chargerCoverNowBarView2.chargerAnimationSet.cancel();
                        chargerCoverNowBarView2.animationListener = null;
                        Log.d("PowerUI.ChargerCoverNowBarView", "Animation Stopped");
                        this.mChargerCoverExpandSubScreenView = null;
                        break;
                    }
                    break;
            }
            this.mCurrentChargingAnimation = null;
        }
    }

    public final void removeMisalignView() {
        WindowManager windowManager;
        PowerManager.WakeLock wakeLock = this.mWirelessMisalignWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            this.mWirelessMisalignWakeLock.release();
            this.mWirelessMisalignWakeLock = null;
        }
        WirelessMisalignView wirelessMisalignView = this.mWirelessMisalignView;
        if (wirelessMisalignView != null && (windowManager = this.mWirelessMisalignWindowManager) != null) {
            windowManager.removeView(wirelessMisalignView);
            this.mWirelessMisalignView = null;
        }
        this.mWirelessMisalignWindowManager = null;
        this.mIsWirelessMisalignTask = false;
        this.mHandler.removeCallbacks(this.mWirelessMisalignTimeoutTask);
    }

    public final void removeNowBarView() {
        this.mFaceWidgetNotificationControllerWrapper.removeItem(PowerUtils.getNowBarItem());
        ChargerNowBarView chargerNowBarView = this.mChargerNowBarView;
        if (chargerNowBarView != null) {
            chargerNowBarView.getBatteryProgressbar().cancelAnimation();
            chargerNowBarView.chargerAnimationSet.removeAllListeners();
            chargerNowBarView.chargerAnimationSet.cancel();
            chargerNowBarView.animationListener = null;
            Log.d("PowerUI.ChargerNowBarView", "Animation Stopped");
            this.mChargerNowBarView = null;
        }
    }

    public final void setSleepChargingOff() {
        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) this.mWarnings;
        secPowerNotificationWarnings.cancelNotification(10);
        secPowerNotificationWarnings.mOptimizationChargingFinishTime = "";
        BatteryProtectionUtils.setSleepChargingStatus(0, this.mContext);
        this.mChargingStartTime = "";
        this.mSleepChargingEvent = "off";
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x016a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setWirelessMisalignView(int i) {
        SecPowerUI secPowerUI;
        WindowManager.LayoutParams layoutParams;
        char c;
        String str;
        char c2;
        int i2;
        WindowManager.LayoutParams layoutParam = getLayoutParam("PowerUI.WirelessMisalignViewLp");
        layoutParam.type = 2009;
        layoutParam.screenOrientation = 14;
        if (DisplayUtils.isFlipSubDisplayOn(SemWindowManager.getInstance().isFolded()) && PowerUiRune.COVER_DISPLAY_LARGE_SCREEN) {
            layoutParam.layoutInDisplayCutoutMode = 3;
        }
        if (this.mWirelessMisalignWindowManager == null) {
            Context context = this.mContext;
            this.mWirelessMisalignWindowManager = DisplayUtils.isFlipSubDisplayOn(SemWindowManager.getInstance().isFolded()) ? (WindowManager) DisplayUtils.getSubDisplayContext(context).getSystemService("window") : (WindowManager) context.getSystemService("window");
        }
        if (this.mWirelessMisalignView == null) {
            Context context2 = this.mContext;
            boolean zIsFolded = SemWindowManager.getInstance().isFolded();
            WindowManager windowManager = (WindowManager) context2.getSystemService("window");
            if (windowManager != null) {
                int rotation = windowManager.getDefaultDisplay().getRotation();
                layoutParams = layoutParam;
                boolean z = rotation == 1;
                if (DisplayUtils.isFlipSubDisplayOn(zIsFolded)) {
                    boolean z2 = z;
                    if (!SemSystemProperties.get("ro.product.device").contains("q7q") && !SemSystemProperties.get("ro.product.device").contains("q7mq")) {
                        str = (zIsFolded && BasicRune.BASIC_FOLDABLE_TYPE_FOLD) ? "FOLD_COVER" : BasicRune.BASIC_FOLDABLE_TYPE_FOLD ? z2 ? "FOLD_REVERSE_MAIN" : "FOLD_MAIN" : BasicRune.BASIC_FOLDABLE_TYPE_FLIP ? "FLIP_MAIN" : "NORMAL";
                    } else if (zIsFolded) {
                        str = "FOLD7_MULTIFOLD7_COVER";
                    } else if (SemSystemProperties.get("ro.product.device").contains("q7q")) {
                        str = z2 ? "FOLD7_REVERSE_MAIN" : "FOLD7_MAIN";
                    } else {
                        WindowManager windowManager2 = (WindowManager) context2.getSystemService("window");
                        if (windowManager2 != null) {
                            int rotation2 = windowManager2.getDefaultDisplay().getRotation();
                            c = 3;
                            if (rotation2 == 3) {
                                str = "MULTIFOLD7_VERTICAL_MAIN";
                            } else if (rotation2 == 1) {
                                str = "MULTIFOLD7_VERTICAL_REVERSE_MAIN";
                            }
                            Log.d("PowerUI.MisalignUtils", "misalign view type: ".concat(str));
                            c2 = 65535;
                            switch (str.hashCode()) {
                                case -1760781151:
                                    if (str.equals("FLIP_LARGE_COVER")) {
                                        c2 = 0;
                                        break;
                                    }
                                    break;
                                case -1695541411:
                                    if (str.equals("MULTIFOLD7_VERTICAL_REVERSE_MAIN")) {
                                        c2 = 1;
                                        break;
                                    }
                                    break;
                                case -1223125102:
                                    if (str.equals("MULTIFOLD7_HORIZONTAL_MAIN")) {
                                        c = 2;
                                        c2 = c;
                                        break;
                                    }
                                    break;
                                case -1109070483:
                                    if (str.equals("FLIP_SMALL_COVER")) {
                                        c2 = c;
                                        break;
                                    }
                                    break;
                                case -957019337:
                                    if (str.equals("FOLD_MAIN")) {
                                        c = 4;
                                        c2 = c;
                                        break;
                                    }
                                    break;
                                case -731021310:
                                    if (str.equals("FOLD7_MAIN")) {
                                        c = 5;
                                        c2 = c;
                                        break;
                                    }
                                    break;
                                case -378893568:
                                    if (str.equals("MULTIFOLD7_VERTICAL_MAIN")) {
                                        c = 6;
                                        c2 = c;
                                        break;
                                    }
                                    break;
                                case 85523019:
                                    if (str.equals("FLIP_MAIN")) {
                                        c = 7;
                                        c2 = c;
                                        break;
                                    }
                                    break;
                                case 179759711:
                                    if (str.equals("FOLD7_REVERSE_MAIN")) {
                                        c2 = '\b';
                                        break;
                                    }
                                    break;
                                case 627550334:
                                    if (str.equals("FOLD7_MULTIFOLD7_COVER")) {
                                        c = '\t';
                                        c2 = c;
                                        break;
                                    }
                                    break;
                                case 1622044820:
                                    if (str.equals("FOLD_REVERSE_MAIN")) {
                                        c = '\n';
                                        c2 = c;
                                        break;
                                    }
                                    break;
                            }
                            switch (c2) {
                                case 0:
                                    i2 = R.layout.battery_misalign_large_subdisplay_layout;
                                    break;
                                case 1:
                                    i2 = R.layout.battery_misalign_multi_fold7_vertical_reverse_layout;
                                    break;
                                case 2:
                                    i2 = R.layout.battery_misalign_multi_fold7_horizontal_layout;
                                    break;
                                case 3:
                                    i2 = R.layout.battery_misalign_subdisplay_layout;
                                    break;
                                case 4:
                                    i2 = R.layout.battery_misalign_fold_layout;
                                    break;
                                case 5:
                                    i2 = R.layout.battery_misalign_fold7_layout;
                                    break;
                                case 6:
                                    i2 = R.layout.battery_misalign_multi_fold7_vertical_layout;
                                    break;
                                case 7:
                                    i2 = R.layout.battery_misalign_flip_layout;
                                    break;
                                case '\b':
                                    i2 = R.layout.battery_misalign_fold7_reverse_layout;
                                    break;
                                case '\t':
                                    i2 = R.layout.battery_misalign_normal_fold7_layout;
                                    break;
                                case '\n':
                                    i2 = R.layout.battery_misalign_fold_reverse_layout;
                                    break;
                                default:
                                    i2 = R.layout.battery_misalign_normal_layout;
                                    break;
                            }
                            WirelessMisalignView wirelessMisalignView = (!str.equals("FLIP_LARGE_COVER") || str.equals("FLIP_SMALL_COVER")) ? (WirelessMisalignView) View.inflate(DisplayUtils.getSubDisplayContext(context2), i2, null) : (WirelessMisalignView) View.inflate(context2, i2, null);
                            secPowerUI = this;
                            secPowerUI.mWirelessMisalignView = wirelessMisalignView;
                        } else {
                            c = 3;
                        }
                        str = "MULTIFOLD7_HORIZONTAL_MAIN";
                        Log.d("PowerUI.MisalignUtils", "misalign view type: ".concat(str));
                        c2 = 65535;
                        switch (str.hashCode()) {
                            case -1760781151:
                                break;
                            case -1695541411:
                                break;
                            case -1223125102:
                                break;
                            case -1109070483:
                                break;
                            case -957019337:
                                break;
                            case -731021310:
                                break;
                            case -378893568:
                                break;
                            case 85523019:
                                break;
                            case 179759711:
                                break;
                            case 627550334:
                                break;
                            case 1622044820:
                                break;
                        }
                        switch (c2) {
                        }
                        if (str.equals("FLIP_LARGE_COVER")) {
                            secPowerUI = this;
                            secPowerUI.mWirelessMisalignView = wirelessMisalignView;
                        }
                    }
                } else {
                    str = PowerUiRune.COVER_DISPLAY_LARGE_SCREEN ? "FLIP_LARGE_COVER" : "FLIP_SMALL_COVER";
                }
                c = 3;
                Log.d("PowerUI.MisalignUtils", "misalign view type: ".concat(str));
                c2 = 65535;
                switch (str.hashCode()) {
                    case -1760781151:
                        break;
                    case -1695541411:
                        break;
                    case -1223125102:
                        break;
                    case -1109070483:
                        break;
                    case -957019337:
                        break;
                    case -731021310:
                        break;
                    case -378893568:
                        break;
                    case 85523019:
                        break;
                    case 179759711:
                        break;
                    case 627550334:
                        break;
                    case 1622044820:
                        break;
                }
                switch (c2) {
                }
                if (str.equals("FLIP_LARGE_COVER")) {
                }
            } else {
                layoutParams = layoutParam;
            }
            if (DisplayUtils.isFlipSubDisplayOn(zIsFolded)) {
            }
            c = 3;
            Log.d("PowerUI.MisalignUtils", "misalign view type: ".concat(str));
            c2 = 65535;
            switch (str.hashCode()) {
                case -1760781151:
                    break;
                case -1695541411:
                    break;
                case -1223125102:
                    break;
                case -1109070483:
                    break;
                case -957019337:
                    break;
                case -731021310:
                    break;
                case -378893568:
                    break;
                case 85523019:
                    break;
                case 179759711:
                    break;
                case 627550334:
                    break;
                case 1622044820:
                    break;
            }
            switch (c2) {
            }
            if (str.equals("FLIP_LARGE_COVER")) {
            }
        } else {
            secPowerUI = this;
            layoutParams = layoutParam;
        }
        secPowerUI.mWirelessMisalignWindowManager.addView(secPowerUI.mWirelessMisalignView, layoutParams);
        WirelessMisalignView wirelessMisalignView2 = secPowerUI.mWirelessMisalignView;
        wirelessMisalignView2.mListener = secPowerUI;
        if (i == 0) {
            wirelessMisalignView2.mTextContainerLayout.setVisibility(0);
            wirelessMisalignView2.mButton.setVisibility(0);
            wirelessMisalignView2.mCenterImageView.setImageResource(R.drawable.overlay_center_alignment);
        } else if (i == 1) {
            wirelessMisalignView2.mTextContainerLayout.setVisibility(8);
            wirelessMisalignView2.mButton.setVisibility(8);
            wirelessMisalignView2.mCenterImageView.setImageResource(R.drawable.overlay_center_alignment_ok);
        }
        if (secPowerUI.mPowerManager != null) {
            Log.i("PowerUI", "turn on screen - misalign view");
            secPowerUI.mPowerManager.wakeUp(SystemClock.uptimeMillis(), secPowerUI.mContext.getOpPackageName());
            if (secPowerUI.mWirelessMisalignWakeLock == null) {
                secPowerUI.mWirelessMisalignWakeLock = secPowerUI.mPowerManager.newWakeLock(268435462, "PowerUI");
            }
            secPowerUI.mWirelessMisalignWakeLock.acquire(30000L);
        }
        secPowerUI.mWirelessMisalignView.setWirelessMisalignViewVisibility(0);
    }

    public final boolean skipChargingUi(int i, int i2, int i3, boolean z) {
        int i4;
        int i5;
        if (BatteryProtectionUtils.isProtectedFullyByMaximum(this.mBatteryMiscEvent)) {
            this.mSkipChargingUiMsg = "protect battery cut off";
            return true;
        }
        if (i == -1 || (i4 = this.mPlugType) == 0 || (i5 = this.mBatteryStatus) != 2 || !this.mFullyConnected || ((i2 == 5 || i2 == i5) && i == i4 && z)) {
            this.mSkipChargingUiMsg = "Plug reason";
            return true;
        }
        int i6 = this.mBatteryMiscEvent;
        BatteryProtectionUtils.INSTANCE.getClass();
        if (i2 == 4 && i5 == 2) {
            boolean zIsProtectedFullyByMaximum = BatteryProtectionUtils.isProtectedFullyByMaximum(i6);
            boolean zIsProtectedFullyByMaximum2 = BatteryProtectionUtils.isProtectedFullyByMaximum(i3);
            KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("protectFully prior : ", ", current : ", "PowerUI.BatteryProtectionUtil", zIsProtectedFullyByMaximum2, zIsProtectedFullyByMaximum);
            if (zIsProtectedFullyByMaximum2 && !zIsProtectedFullyByMaximum && !this.mIsChangedBatteryProtectionOnCharging) {
                this.mSkipChargingUiMsg = "Recharge from battery protection, value : " + this.mProtectBatteryValue + " / Sleep charging : " + this.mSleepChargingEvent;
                return true;
            }
        }
        if (this.mBatteryOnline == 99) {
            this.mSkipChargingUiMsg = "AFC retry case";
            return true;
        }
        int i7 = this.mPlugType;
        if ((i7 == 1 && i == 2) || (i7 == 2 && i == 1)) {
            this.mSkipChargingUiMsg = "Only cable charger type changed";
            return true;
        }
        if (this.mIsMotionDetectionSupported && !this.mIsDeviceMoving && i7 == 4) {
            this.mDozeChargingHelper.handlePluginAodCharging("Charger connected but device had no move detection and screen off => trigger AOD");
            this.mSkipChargingUiMsg = "No motion detected during wireless charging";
            return true;
        }
        if (!this.mIsAfterAdaptiveProtection) {
            return false;
        }
        this.mSkipChargingUiMsg = "After adaptive protection";
        return true;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() throws Resources.NotFoundException, NumberFormatException {
        Intent intentRegisterReceiver;
        String string;
        int integer = this.mContext.getResources().getInteger(android.R.integer.config_displayWhiteBalanceBrightnessFilterHorizon);
        int integer2 = this.mContext.getResources().getInteger(android.R.integer.config_pinnerHomePinBytes);
        if (integer2 < integer) {
            integer2 = integer;
        }
        int[] iArr = this.mLowBatteryReminderLevels;
        iArr[0] = integer2;
        iArr[1] = integer;
        this.mLowBatteryAlertCloseLevel = this.mContext.getResources().getInteger(android.R.integer.config_pictureInPictureMaxNumberOfActions) + integer2;
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        boolean zEquals = "1".equals(SystemProperties.get("sys.boot_completed"));
        this.mBootCompleted = zEquals;
        if (zEquals) {
            checkOverheatShutdownHappened();
            if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                int protectBatteryValue = BatteryProtectionUtils.getProtectBatteryValue(this.mContext);
                int iGlobalGetInt = SettingsUtils.globalGetInt(this.mContext, "prev_protect_battery_ltc", -1);
                if (protectBatteryValue == 2) {
                    Settings.Global.putInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_PROTECT_BATTERY, iGlobalGetInt);
                    Settings.Global.putInt(this.mContext.getContentResolver(), "prev_protect_battery_ltc", -1);
                }
                clearScheduling();
            }
            if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION) {
                this.mIsAfterAdaptiveProtection = false;
            }
        }
        if (PowerUiRune.PROTECT_BATTERY_CUTOFF) {
            this.mResolver.registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_PROTECT_BATTERY), false, this.protectBatteryObserver, -1);
            this.mProtectBatteryValue = BatteryProtectionUtils.getProtectBatteryValue(this.mContext);
            if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                this.mLtcHighSocThreshold = SettingsUtils.globalGetInt(this.mContext, "ltc_highsoc_threshold", 95);
                this.mLtcReleaseThreshold = SettingsUtils.globalGetInt(this.mContext, "ltc_release_threshold", 75);
            }
            if (PowerUiRune.BATTERY_PROTECTION) {
                this.mResolver.registerContentObserver(Settings.Global.getUriFor("battery_protection_threshold"), false, this.maximumThresholdObserver, -1);
                this.mResolver.registerContentObserver(Settings.Secure.getUriFor(SettingsHelper.INDEX_USER_SETUP_COMPLETE), false, this.ownerSetupWizardObserver, 0);
            }
            if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION) {
                Context context = this.mContext;
                String str = this.mSleepChargingEvent;
                String str2 = this.mChargingStartTime;
                if ((str == null || StringsKt__StringsKt.isBlank(str)) && ((str2 == null || StringsKt__StringsKt.isBlank(str2)) && BatteryProtectionUtils.isSleepChargingOn(context) && (string = Settings.Global.getString(context.getContentResolver(), "sleep_charging_finish_time")) != null && !StringsKt__StringsKt.isBlank(string))) {
                    Context context2 = this.mContext;
                    BatteryProtectionUtils.INSTANCE.getClass();
                    int iGlobalGetInt2 = SettingsUtils.globalGetInt(context2, "key_sleep_charging", 0);
                    this.mSleepChargingEvent = iGlobalGetInt2 != 1 ? iGlobalGetInt2 != 2 ? "off" : "update" : "on";
                    this.mChargingStartTime = Settings.Global.getString(this.mContext.getContentResolver(), "sleep_charging_finish_time");
                }
            }
        }
        ((SecPowerNotificationWarnings) this.mWarnings).restoreScreenTimeOutIfNeeded();
        if (this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")) {
            Log.d("PowerUI", "start : hasSystemFeature(com.sec.feature.sensorhub)");
            this.mIsMotionDetectionSupported = true;
            SContextManager sContextManager = (SContextManager) this.mContext.getSystemService("scontext");
            this.mSContextManager = sContextManager;
            if (sContextManager != null) {
                this.mIsSContextEnabled = sContextManager.isAvailableService(46);
                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("start : (mSContextManager != null - mIsSContextEnabled = "), this.mIsSContextEnabled, "PowerUI");
            }
        }
        ((TelephonyManager) this.mContext.getSystemService("phone")).listen(this.mPhoneStateListener, 32);
        Receiver receiver = this.mReceiver;
        receiver.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT");
        intentFilter.addAction("com.samsung.systemui.power.action.WATER_ALERT_SOUND_TEST");
        intentFilter.addAction("com.samsung.intent.action.KSO_SHOW_POPUP");
        intentFilter.addAction("com.samsung.intent.action.KSO_CLOSE_POPUP");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction(PopupUIUtil.ACTION_BOOT_COMPLETED);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.samsung.CHECK_COOLDOWN_LEVEL");
        intentFilter2.addAction("com.sec.android.intent.action.SAFEMODE_ENABLE");
        SecPowerUI secPowerUI = SecPowerUI.this;
        secPowerUI.mContext.registerReceiver(receiver, intentFilter2, "com.samsung.android.permission.SSRM_NOTIFICATION_PERMISSION", secPowerUI.mHandler, 2);
        if (PowerUiRune.TIPS_NOTIFICATION) {
            intentFilter.addAction("com.samsung.android.sm.IGNORE_RUT_TIPS_NOTI");
            intentFilter.addAction("com.samsung.android.sm.CLEAR_TIPS_NOTI");
            intentFilter.addAction("android.intent.action.tips.noti.confirmed");
        }
        if (PowerUiRune.INIT_LTC_TIME_CHANGED) {
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_SET");
        }
        if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION) {
            intentFilter.addAction("com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING");
        }
        intentFilter.addAction("com.samsung.server.BatteryService.action.SEC_BATTERY_REMAINING_CHARGING_TIME_CHANGED");
        SecPowerUI secPowerUI2 = SecPowerUI.this;
        secPowerUI2.mBroadcastDispatcher.registerReceiverWithHandler(receiver, intentFilter, secPowerUI2.mHandler);
        if (receiver.mHasReceivedBattery || (intentRegisterReceiver = SecPowerUI.this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"), 2)) == null) {
            return;
        }
        receiver.onReceive(SecPowerUI.this.mContext, intentRegisterReceiver);
    }

    public final void startScheduling() {
        Context context = this.mContext;
        BatteryProtectionUtils batteryProtectionUtils = BatteryProtectionUtils.INSTANCE;
        if (context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0).getBoolean("auto_on_protect_battery_timer_started", false)) {
            return;
        }
        Log.i("PowerUI", "Meet soc conditions, start scheduling");
        Context context2 = this.mContext;
        boolean z = PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE;
        BatteryProtectionUtils batteryProtectionUtils2 = BatteryProtectionUtils.INSTANCE;
        if (z) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            batteryProtectionUtils2.getClass();
            Settings.Global.putLong(context2.getContentResolver(), "ltc_highsoc_exceed_time", jCurrentTimeMillis);
        } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
            String strValueOf = String.valueOf(System.currentTimeMillis());
            batteryProtectionUtils2.getClass();
            Settings.Global.putString(context2.getContentResolver(), "charger_connected_time", strValueOf);
        }
        batteryProtectionUtils2.getClass();
        SharedPreferences sharedPreferences = context2.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putBoolean("auto_on_protect_battery_timer_started", true);
            editorEdit.commit();
        }
    }
}
