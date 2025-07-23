package com.android.systemui.power;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.hardware.scontext.SContextEvent;
import android.hardware.scontext.SContextListener;
import android.hardware.scontext.SContextManager;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.LsRune;
import com.android.systemui.PowerUiRune;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.power.constants.PowerUiConstants;
import com.android.systemui.power.listener.ChargerAnimationListener;
import com.android.systemui.power.utils.BatteryProtectionUtils;
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
import com.android.systemui.statusbar.events.SystemEventCoordinator;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.google.common.util.concurrent.ListenableFuture;
import com.samsung.android.knox.custom.SettingsManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import java.io.PrintWriter;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Receiver extends BroadcastReceiver {
        public boolean mHasReceivedBattery = false;

        public Receiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:143:0x0ba3, code lost:
        
            if (r3 == (-2)) goto L421;
         */
        /* JADX WARN: Code restructure failed: missing block: B:145:0x0ba7, code lost:
        
            if (r0.mBootCompleted == false) goto L425;
         */
        /* JADX WARN: Code restructure failed: missing block: B:146:0x0ba9, code lost:
        
            android.util.Log.d(r14, "Low battery dump");
            r2 = new android.content.Intent("com.samsung.systemui.power.action.LOW_BATTERY_DUMP");
            r2.addFlags(16777216);
            r0.mContext.sendBroadcast(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:171:0x0b9d, code lost:
        
            if (r7 == (-1)) goto L415;
         */
        /* JADX WARN: Removed duplicated region for block: B:102:0x0ac7  */
        /* JADX WARN: Removed duplicated region for block: B:109:0x0b1b  */
        /* JADX WARN: Removed duplicated region for block: B:112:0x0b2d  */
        /* JADX WARN: Removed duplicated region for block: B:124:0x0b59  */
        /* JADX WARN: Removed duplicated region for block: B:135:0x0b86  */
        /* JADX WARN: Removed duplicated region for block: B:148:0x0bc4  */
        /* JADX WARN: Removed duplicated region for block: B:151:0x0bd4  */
        /* JADX WARN: Removed duplicated region for block: B:154:0x0bdd  */
        /* JADX WARN: Removed duplicated region for block: B:157:0x0be6  */
        /* JADX WARN: Removed duplicated region for block: B:160:0x0bf9  */
        /* JADX WARN: Removed duplicated region for block: B:163:0x0c02  */
        /* JADX WARN: Removed duplicated region for block: B:166:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:167:0x0bec  */
        /* JADX WARN: Removed duplicated region for block: B:172:0x0bc0  */
        /* JADX WARN: Removed duplicated region for block: B:180:0x0b0f  */
        /* JADX WARN: Removed duplicated region for block: B:181:0x0abb  */
        /* JADX WARN: Removed duplicated region for block: B:182:0x0ab3  */
        /* JADX WARN: Removed duplicated region for block: B:183:0x0a86  */
        /* JADX WARN: Removed duplicated region for block: B:87:0x0a53 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:93:0x0a71  */
        /* JADX WARN: Removed duplicated region for block: B:96:0x0ab1  */
        /* JADX WARN: Removed duplicated region for block: B:99:0x0ab9  */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r42, android.content.Intent r43) {
            /*
                Method dump skipped, instructions count: 3636
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.SecPowerUI.Receiver.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    /* renamed from: -$$Nest$mcheckAbnormalChargingPad, reason: not valid java name */
    public static void m2860$$Nest$mcheckAbnormalChargingPad(SecPowerUI secPowerUI, int i) {
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
    public static void m2861$$Nest$mcheckBatteryHealthInterruptionStatus(SecPowerUI secPowerUI, int i, boolean z) {
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
                PowerManager.WakeLock newWakeLock = secPowerUI.mPowerManager.newWakeLock(268435462, "PowerUI");
                secPowerUI.mBatteryHealthInterruptionScreenDimWakeLock = newWakeLock;
                if (PowerUiRune.KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION && secPowerUI.mBatteryHealth == 6) {
                    newWakeLock.acquire();
                } else {
                    newWakeLock.acquire(60000L);
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
                PowerManager.WakeLock newWakeLock2 = secPowerUI.mPowerManager.newWakeLock(268435462, "PowerUI");
                secPowerUI.mBatteryHealthInterruptionScreenDimWakeLock = newWakeLock2;
                if (PowerUiRune.KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION) {
                    newWakeLock2.acquire();
                } else {
                    newWakeLock2.acquire(60000L);
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /* renamed from: -$$Nest$mcheckBatteryProtectionTipsNotification, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m2862$$Nest$mcheckBatteryProtectionTipsNotification(com.android.systemui.power.SecPowerUI r9, int r10) {
        /*
            r9.getClass()
            com.android.systemui.power.tips.TipsManager r0 = new com.android.systemui.power.tips.TipsManager
            android.content.Context r1 = r9.mContext
            r0.<init>(r1)
            int r9 = r9.mPlugType
            android.content.Context r1 = r0.context
            java.lang.String r2 = "com.android.systemui.power_tips_notification"
            r3 = 0
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r3)
            if (r1 == 0) goto Lc0
            java.lang.String r4 = "tipsNotiFirstTime"
            r5 = 1
            boolean r1 = r1.getBoolean(r4, r5)
            android.content.Context r6 = r0.context
            android.content.ContentResolver r6 = r6.getContentResolver()
            java.lang.String r7 = "user_setup_complete"
            r8 = -2
            int r6 = android.provider.Settings.Secure.getIntForUser(r6, r7, r3, r8)
            if (r6 != r5) goto L31
            r6 = r5
            goto L32
        L31:
            r6 = r3
        L32:
            if (r10 != 0) goto Lc0
            if (r9 == r5) goto L39
            r10 = 4
            if (r9 != r10) goto Lc0
        L39:
            if (r1 == 0) goto Lc0
            android.content.Context r9 = r0.context
            java.lang.String r10 = "connectivity"
            java.lang.Object r9 = r9.getSystemService(r10)
            android.net.ConnectivityManager r9 = (android.net.ConnectivityManager) r9
            android.net.NetworkInfo r9 = r9.getActiveNetworkInfo()
            if (r9 == 0) goto Lc0
            boolean r9 = r9.isConnected()
            if (r9 != r5) goto Lc0
            if (r6 == 0) goto Lc0
            android.content.Intent r9 = new android.content.Intent
            r9.<init>()
            java.lang.String r10 = "com.samsung.android.app.tips"
            java.lang.String r1 = "com.samsung.android.app.tips.TipsIntentService"
            r9.setClassName(r10, r1)
            java.lang.String r10 = "tips_extras"
            r1 = 8
            r9.putExtra(r10, r1)
            java.lang.String r10 = "tips_extras2"
            java.lang.String r1 = "BATT_0003"
            r9.putExtra(r10, r1)
            android.content.Context r10 = r0.context
            r1 = 2131952197(0x7f130245, float:1.954083E38)
            java.lang.String r10 = r10.getString(r1)
            java.lang.String r1 = "tips_extras3"
            r9.putExtra(r1, r10)
            android.content.Context r10 = r0.context
            r1 = 2131952198(0x7f130246, float:1.9540832E38)
            java.lang.String r10 = r10.getString(r1)
            java.lang.String r1 = "tips_extras4"
            r9.putExtra(r1, r10)
            java.lang.String r10 = "All condition is OK, Try to show battery protection tips"
            java.lang.String r1 = "PowerUI.TipsManager"
            android.util.Slog.d(r1, r10)
            java.lang.String r10 = "Exception occur"
            r5 = 0
            android.content.Context r6 = r0.context     // Catch: android.app.ForegroundServiceStartNotAllowedException -> L9e java.lang.SecurityException -> La0
            android.content.ComponentName r9 = r6.startForegroundService(r9)     // Catch: android.app.ForegroundServiceStartNotAllowedException -> L9e java.lang.SecurityException -> La0
            goto Laa
        L9e:
            r9 = move-exception
            goto La2
        La0:
            r9 = move-exception
            goto La6
        La2:
            android.util.Log.e(r1, r10, r9)
            goto La9
        La6:
            android.util.Log.e(r1, r10, r9)
        La9:
            r9 = r5
        Laa:
            if (r9 == 0) goto Lc0
            android.content.Context r9 = r0.context
            android.content.SharedPreferences r9 = r9.getSharedPreferences(r2, r3)
            if (r9 == 0) goto Lb8
            android.content.SharedPreferences$Editor r5 = r9.edit()
        Lb8:
            if (r5 == 0) goto Lc0
            r5.putBoolean(r4, r3)
            r5.commit()
        Lc0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.SecPowerUI.m2862$$Nest$mcheckBatteryProtectionTipsNotification(com.android.systemui.power.SecPowerUI, int):void");
    }

    /* renamed from: -$$Nest$mcheckBatterySwellingStatus, reason: not valid java name */
    public static void m2863$$Nest$mcheckBatterySwellingStatus(SecPowerUI secPowerUI, int i, int i2) {
        if (i == secPowerUI.mBatterySwellingMode && i2 == secPowerUI.mBatteryStatus) {
            return;
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Battery swelling mode - priorBatterySwellingMode = ", " mBatterySwellingMode = ");
        m.append(secPowerUI.mBatterySwellingMode);
        m.append(" mBatteryStatus = ");
        m.append(secPowerUI.mBatteryStatus);
        Slog.d("PowerUI", m.toString());
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
    public static void m2864$$Nest$mcheckCoolDownStatus(SecPowerUI secPowerUI, Intent intent) {
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
    public static void m2865$$Nest$mcheckHVchargerEnableConnection(SecPowerUI secPowerUI, int i) {
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

    /* JADX WARN: Removed duplicated region for block: B:38:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* renamed from: -$$Nest$mcheckTipsNotification, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m2866$$Nest$mcheckTipsNotification(com.android.systemui.power.SecPowerUI r12, int r13) {
        /*
            Method dump skipped, instructions count: 403
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.SecPowerUI.m2866$$Nest$mcheckTipsNotification(com.android.systemui.power.SecPowerUI, int):void");
    }

    /* renamed from: -$$Nest$mcheckTurnOffPsmNotification, reason: not valid java name */
    public static void m2867$$Nest$mcheckTurnOffPsmNotification(SecPowerUI secPowerUI, int i) {
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
    public static void m2868$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge(SecPowerUI secPowerUI) {
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
    public static void m2869$$Nest$mcheckTurnOnProtectBatteryByLongTa(SecPowerUI secPowerUI) {
        if (secPowerUI.mPlugType != 0 && !BatteryProtectionUtils.isMaximumProtectionEnabled(secPowerUI.mContext)) {
            secPowerUI.startScheduling();
            return;
        }
        Context context = secPowerUI.mContext;
        BatteryProtectionUtils batteryProtectionUtils = BatteryProtectionUtils.INSTANCE;
        boolean z = context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0).getBoolean("auto_on_protect_battery_timer_started", false);
        int globalGetInt = SettingsUtils.globalGetInt(secPowerUI.mContext, "auto_on_protect_battery", -1);
        if (!z && secPowerUI.mPlugType == 0 && globalGetInt == 1) {
            Log.i("PowerUI", "send intent to DC, turn off protect battery");
            PowerUtils.sendIntentToDc(secPowerUI.mContext, "com.samsung.android.sm.action.TURN_OFF_PROTECT_BATTERY_BY_LONG_TERM_TA");
        }
        secPowerUI.clearScheduling();
    }

    /* renamed from: -$$Nest$mcheckUsbDamageDetectionStatus, reason: not valid java name */
    public static void m2870$$Nest$mcheckUsbDamageDetectionStatus(SecPowerUI secPowerUI, Intent intent) {
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
    public static void m2871$$Nest$mcheckWaterDetectionStatus(SecPowerUI secPowerUI, Intent intent) {
        boolean z = secPowerUI.mBatteryWaterConnector;
        boolean z2 = secPowerUI.mIsHiccupState;
        secPowerUI.mBatteryWaterConnector = (intent.getIntExtra("misc_event", 0) & 1) == 1;
        boolean z3 = (intent.getIntExtra("misc_event", 0) & 32) == 32;
        secPowerUI.mIsHiccupState = z3;
        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secPowerUI.mWarnings;
        secPowerNotificationWarnings.mIsHiccupState = z3;
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("SUPPORT_WATER_PROTECTION_POPUP - oldBatteryWaterConnector : ", ", mBatteryWaterConnector : ", z);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, secPowerUI.mBatteryWaterConnector, ", oldHiccupState : ", z2, ", mIsHiccupState : ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(m, secPowerUI.mIsHiccupState, "PowerUI");
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
    public static void m2872$$Nest$mcheckWirelessChargingFodStatus(SecPowerUI secPowerUI, Intent intent) {
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
    public static int m2874$$Nest$mfindBatteryLevelBucket(SecPowerUI secPowerUI, int i) {
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
    public static void m2875$$Nest$mupdateBatteryNotificationLanguage(SecPowerUI secPowerUI) {
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
                    SecPowerUI.m2868$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge(SecPowerUI.this);
                } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
                    SecPowerUI.m2869$$Nest$mcheckTurnOnProtectBatteryByLongTa(SecPowerUI.this);
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
                    SecPowerUI.m2868$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge(secPowerUI2);
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
        boolean equalsIgnoreCase = "on".equalsIgnoreCase(this.mSleepChargingEvent);
        SecWarningsUI secWarningsUI = this.mWarnings;
        if (equalsIgnoreCase && this.mProtectBatteryValue == 4) {
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
        boolean isSleepChargingOn = BatteryProtectionUtils.isSleepChargingOn(this.mContext);
        SecWarningsUI secWarningsUI = this.mWarnings;
        if (!isSleepChargingOn && this.mProtectBatteryValue > 0 && this.mBatteryStatus >= 4 && this.mBatteryHealth == 2 && this.mBatteryOverheatLevel == 0) {
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
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("OverheatShutdownHappened", false);
            edit.commit();
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
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("auto_on_protect_battery_timer_started", false);
            edit.commit();
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
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("BatteryProtection value : "), this.mProtectBatteryValue, printWriter, "Maximum threshold value : ");
        m.append(SettingsUtils.globalGetInt(this.mContext, "battery_protection_threshold", Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE));
        printWriter.println(m.toString());
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
        StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("Nowbar Supported : "), PowerUiRune.CHARGING_VI_NOW_BAR, printWriter, "Blur feature supported : "), PowerUiRune.WINDOW_BLUR_SUPPORTED, printWriter, "Reduce blur transparency enabled : "), Settings.System.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY, 0) == 1, printWriter, "Animator duration scale : ");
        m2.append(SettingsUtils.globalGetInt(this.mContext, SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1));
        m2.append("x");
        printWriter.println(m2.toString());
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

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0167, code lost:
    
        if (r0.equals("FLIP_SMALL_COVER") == false) goto L112;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setWirelessMisalignView(int r22) {
        /*
            Method dump skipped, instructions count: 668
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.SecPowerUI.setWirelessMisalignView(int):void");
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
            boolean isProtectedFullyByMaximum = BatteryProtectionUtils.isProtectedFullyByMaximum(i6);
            boolean isProtectedFullyByMaximum2 = BatteryProtectionUtils.isProtectedFullyByMaximum(i3);
            KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("protectFully prior : ", ", current : ", "PowerUI.BatteryProtectionUtil", isProtectedFullyByMaximum2, isProtectedFullyByMaximum);
            if (isProtectedFullyByMaximum2 && !isProtectedFullyByMaximum && !this.mIsChangedBatteryProtectionOnCharging) {
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
    public final void start() {
        Intent registerReceiver;
        String string;
        int integer = this.mContext.getResources().getInteger(R.integer.config_displayWhiteBalanceBrightnessFilterHorizon);
        int integer2 = this.mContext.getResources().getInteger(R.integer.config_pinnerHomePinBytes);
        if (integer2 < integer) {
            integer2 = integer;
        }
        int[] iArr = this.mLowBatteryReminderLevels;
        iArr[0] = integer2;
        iArr[1] = integer;
        this.mLowBatteryAlertCloseLevel = this.mContext.getResources().getInteger(R.integer.config_pictureInPictureMaxNumberOfActions) + integer2;
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        boolean equals = "1".equals(SystemProperties.get("sys.boot_completed"));
        this.mBootCompleted = equals;
        if (equals) {
            checkOverheatShutdownHappened();
            if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                int protectBatteryValue = BatteryProtectionUtils.getProtectBatteryValue(this.mContext);
                int globalGetInt = SettingsUtils.globalGetInt(this.mContext, "prev_protect_battery_ltc", -1);
                if (protectBatteryValue == 2) {
                    Settings.Global.putInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_PROTECT_BATTERY, globalGetInt);
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
                    int globalGetInt2 = SettingsUtils.globalGetInt(context2, "key_sleep_charging", 0);
                    this.mSleepChargingEvent = globalGetInt2 != 1 ? globalGetInt2 != 2 ? "off" : "update" : "on";
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
        if (receiver.mHasReceivedBattery || (registerReceiver = SecPowerUI.this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"), 2)) == null) {
            return;
        }
        receiver.onReceive(SecPowerUI.this.mContext, registerReceiver);
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
            long currentTimeMillis = System.currentTimeMillis();
            batteryProtectionUtils2.getClass();
            Settings.Global.putLong(context2.getContentResolver(), "ltc_highsoc_exceed_time", currentTimeMillis);
        } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
            String valueOf = String.valueOf(System.currentTimeMillis());
            batteryProtectionUtils2.getClass();
            Settings.Global.putString(context2.getContentResolver(), "charger_connected_time", valueOf);
        }
        batteryProtectionUtils2.getClass();
        SharedPreferences sharedPreferences = context2.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("auto_on_protect_battery_timer_started", true);
            edit.commit();
        }
    }
}
