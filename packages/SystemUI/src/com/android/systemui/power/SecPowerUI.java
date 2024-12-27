package com.android.systemui.power;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
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
import android.view.SemBlurInfo;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.CoreStartable;
import com.android.systemui.LsRune;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.keyguardstatusview.NowBarItem;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.events.SystemEventCoordinator;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.android.view.SemWindowManager;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.Future;

public final class SecPowerUI implements CoreStartable, ConfigurationController.ConfigurationListener, CommandQueue.Callbacks, ChargerAnimationListener, WirelessMisalignListener {
    public static final boolean DEBUG = Log.isLoggable("PowerUI", 3);
    public PowerManager.WakeLock mBatteryHealthInterruptionPartialWakeLock;
    public PowerManager.WakeLock mBatteryHealthInterruptionScreenDimWakeLock;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public ChargerAnimationView mChargerAnimationView;
    public WindowManager.LayoutParams mChargerAnimationWindowLp;
    public WindowManager mChargerAnimationWindowManager;
    public ChargerNowBarView mChargerNowBarView;
    public String mChargingStartTime;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public BatteryStateData mCurrentBatteryStateData;
    public final DozeChargingHelper mDozeChargingHelper;
    public final FaceWidgetNotificationControllerWrapper mFaceWidgetNotificationControllerWrapper;
    public BatteryStateData mLastBatteryStateData;
    public Future mLastShowWarningTask;
    public int mLowBatteryAlertCloseLevel;
    public int mLtcHighSocThreshold;
    public int mLtcReleaseThreshold;
    public final PowerManager mPowerManager;
    public String mSkipChargingUiMsg;
    public String mSleepChargingEvent;
    public int mSuperFastCharger;
    public final SystemEventCoordinator mSystemEventCoordinator;
    public final UserTracker mUserTracker;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final SecWarningsUI mWarnings;
    public WirelessMisalignView mWirelessMisalignView;
    public PowerManager.WakeLock mWirelessMisalignWakeLock;
    public WindowManager.LayoutParams mWirelessMisalignWindowLp;
    public WindowManager mWirelessMisalignWindowManager;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final int[] mLowBatteryReminderLevels = new int[2];
    public final Receiver mReceiver = new Receiver();
    public int mBatteryLevel = 100;
    public int mBatteryStatus = 1;
    public int mPlugType = -1;
    public long mScreenOffTime = -1;
    public boolean mIsRunningLowBatteryTask = false;
    public boolean mIsRunningStopPowerSoundTask = false;
    public int mBatterySwellingMode = 0;
    public boolean mBatteryHighVoltageCharger = false;
    public boolean mFullyConnected = true;
    public boolean mBatterySlowCharger = false;
    public boolean mIsChangedStringAfterCharging = false;
    public int mBatteryChargingType = 0;
    public int mBatteryOnline = -1;
    public String mCurrentChargingAnimation = null;
    public boolean mIsWirelessMisalignTask = false;
    public SContextManager mSContextManager = null;
    public boolean mIsMotionDetectionSupported = false;
    public boolean mIsSContextEnabled = false;
    public boolean mIsSContextListenerRegistered = false;
    public boolean mIsDeviceMoving = true;
    public boolean mWirelessFodState = false;
    public boolean mBatteryWaterConnector = false;
    public boolean mIsHiccupState = false;
    public boolean mTemperatureHiccupState = false;
    public boolean mDismissBatteryHealthInterruptionWarning = false;
    public int mBatteryHealth = 1;
    public boolean mIsShutdownTaskDelayed = false;
    public int mBatteryOverheatLevel = 0;
    public int mCallState = 0;
    public boolean mBootCompleted = false;
    public int mBatteryCurrentEvent = 0;
    public boolean mIsChangedBatteryProtectionOnCharging = false;
    public int mProtectBatteryValue = -1;
    public boolean mIsAfterAdaptiveProtection = false;
    public int mBatteryMiscEvent = 0;
    public int mProtectionEvent = 0;
    public int mTurnOffPsmLevel = -1;
    public final AnonymousClass1 mLowBatteryWarningTask = new Runnable() { // from class: com.android.systemui.power.SecPowerUI.1
        @Override // java.lang.Runnable
        public final void run() {
            Log.d("PowerUI", "mLowBatteryWarningTask");
            SecPowerUI secPowerUI = SecPowerUI.this;
            secPowerUI.mIsRunningLowBatteryTask = false;
            ((SecPowerNotificationWarnings) secPowerUI.mWarnings).showLowBatteryWarning(true);
        }
    };
    public final AnonymousClass2 mOverheatShutdownWarningTask = new Runnable() { // from class: com.android.systemui.power.SecPowerUI.2
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
    public final AnonymousClass3 mPhoneStateListener = new PhoneStateListener() { // from class: com.android.systemui.power.SecPowerUI.3
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
    public final AnonymousClass4 mAfterChargingNoticeTask = new Runnable() { // from class: com.android.systemui.power.SecPowerUI.4
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
            SecWarningsUI secWarningsUI = secPowerUI.mWarnings;
            int i2 = secPowerUI.mSuperFastCharger;
            SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secWarningsUI;
            secPowerNotificationWarnings.mOldChargingType = secPowerNotificationWarnings.mChargingType;
            secPowerNotificationWarnings.mChargingType = i;
            secPowerNotificationWarnings.mSuperFastCharger = i2;
            ((SecPowerNotificationWarnings) secWarningsUI).showChargingNotice();
        }
    };
    public final AnonymousClass5 mStopPowerSoundTask = new Runnable() { // from class: com.android.systemui.power.SecPowerUI.5
        @Override // java.lang.Runnable
        public final void run() {
            Log.d("PowerUI", "mStopPowerSoundTask");
            SecPowerUI secPowerUI = SecPowerUI.this;
            secPowerUI.mIsRunningStopPowerSoundTask = false;
            ((SecPowerNotificationWarnings) secPowerUI.mWarnings).stopPowerSound(1);
        }
    };
    public final AnonymousClass6 mSContextListener = new SContextListener() { // from class: com.android.systemui.power.SecPowerUI.6
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
    public final SecPowerUI$$ExternalSyntheticLambda0 mWirelessMisalignTimeoutTask = new SecPowerUI$$ExternalSyntheticLambda0(this, 0);
    public final AnonymousClass8 mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.power.SecPowerUI.8
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            SecPowerUI secPowerUI = SecPowerUI.this;
            secPowerUI.mScreenOffTime = elapsedRealtime;
            SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secPowerUI.mWarnings;
            secPowerNotificationWarnings.getClass();
            Log.d("PowerUI.Notification", "dismissUnintentionallyLcdOnNotice");
            secPowerNotificationWarnings.dismissUnintentionalLcdOnWindow();
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            SecPowerUI.this.mScreenOffTime = -1L;
        }
    };
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.power.SecPowerUI.9
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            Slog.i("PowerUI", "onUserChanged : " + i);
            SecPowerUI secPowerUI = SecPowerUI.this;
            ((SecPowerNotificationWarnings) secPowerUI.mWarnings).updateNotification();
            if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION && secPowerUI.mProtectBatteryValue == 4 && i != 0) {
                Slog.i("PowerUI", "start battery protection InitService at user " + i);
                Intent intent = new Intent();
                intent.setPackage(PowerUiConstants.DC_PACKAGE_NAME);
                intent.setAction("com.samsung.android.sm.service.action.ACTION_BATTERY_PROTECTION_INIT_SERVICE");
                context.startService(intent);
            }
        }
    };

    public final class Receiver extends BroadcastReceiver {
        public boolean mHasReceivedBattery = false;

        public Receiver() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:144:0x06cc, code lost:
        
            if (r3 == 6) goto L280;
         */
        /* JADX WARN: Removed duplicated region for block: B:102:0x056c  */
        /* JADX WARN: Removed duplicated region for block: B:105:0x05ac  */
        /* JADX WARN: Removed duplicated region for block: B:108:0x05b4  */
        /* JADX WARN: Removed duplicated region for block: B:111:0x05c2  */
        /* JADX WARN: Removed duplicated region for block: B:118:0x0614  */
        /* JADX WARN: Removed duplicated region for block: B:139:0x06c3  */
        /* JADX WARN: Removed duplicated region for block: B:151:0x0833  */
        /* JADX WARN: Removed duplicated region for block: B:163:0x085d  */
        /* JADX WARN: Removed duplicated region for block: B:174:0x088a  */
        /* JADX WARN: Removed duplicated region for block: B:188:0x08bf  */
        /* JADX WARN: Removed duplicated region for block: B:207:0x0926  */
        /* JADX WARN: Removed duplicated region for block: B:212:0x0956  */
        /* JADX WARN: Removed duplicated region for block: B:215:0x095f  */
        /* JADX WARN: Removed duplicated region for block: B:228:0x09b4  */
        /* JADX WARN: Removed duplicated region for block: B:231:0x09c7  */
        /* JADX WARN: Removed duplicated region for block: B:234:0x09d0  */
        /* JADX WARN: Removed duplicated region for block: B:237:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:238:0x09ba  */
        /* JADX WARN: Removed duplicated region for block: B:242:0x093e  */
        /* JADX WARN: Removed duplicated region for block: B:254:0x070e  */
        /* JADX WARN: Removed duplicated region for block: B:261:0x07d9  */
        /* JADX WARN: Removed duplicated region for block: B:265:0x07f4  */
        /* JADX WARN: Removed duplicated region for block: B:269:0x07fe  */
        /* JADX WARN: Removed duplicated region for block: B:290:0x0736  */
        /* JADX WARN: Removed duplicated region for block: B:310:0x0785  */
        /* JADX WARN: Removed duplicated region for block: B:345:0x060a  */
        /* JADX WARN: Removed duplicated region for block: B:346:0x05b6  */
        /* JADX WARN: Removed duplicated region for block: B:347:0x05ae  */
        /* JADX WARN: Removed duplicated region for block: B:348:0x0581  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x052e  */
        /* JADX WARN: Type inference failed for: r5v28 */
        /* JADX WARN: Type inference failed for: r5v29, types: [com.android.systemui.power.ChargerAnimationListener, java.util.ArrayList] */
        /* JADX WARN: Type inference failed for: r5v30 */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r27, android.content.Intent r28) {
            /*
                Method dump skipped, instructions count: 2980
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.SecPowerUI.Receiver.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* renamed from: -$$Nest$mcheckBatteryProtectionTipsNotification, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m2047$$Nest$mcheckBatteryProtectionTipsNotification(com.android.systemui.power.SecPowerUI r7, int r8) {
        /*
            r7.getClass()
            com.android.systemui.power.tips.TipsManager r0 = new com.android.systemui.power.tips.TipsManager
            android.content.Context r1 = r7.mContext
            r0.<init>(r1)
            int r7 = r7.mPlugType
            android.content.Context r1 = r0.context
            java.lang.String r2 = "com.android.systemui.power_tips_notification"
            r3 = 0
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r3)
            if (r1 == 0) goto L95
            java.lang.String r4 = "tipsNotiFirstTime"
            r5 = 1
            boolean r1 = r1.getBoolean(r4, r5)
            if (r8 != 0) goto L95
            if (r7 == r5) goto L26
            r8 = 4
            if (r7 != r8) goto L95
        L26:
            if (r1 == 0) goto L95
            android.content.Intent r7 = new android.content.Intent
            r7.<init>()
            java.lang.String r8 = "com.samsung.android.app.tips"
            java.lang.String r1 = "com.samsung.android.app.tips.TipsIntentService"
            r7.setClassName(r8, r1)
            java.lang.String r8 = "tips_extras"
            r1 = 8
            r7.putExtra(r8, r1)
            java.lang.String r8 = "tips_extras2"
            java.lang.String r1 = "BATT_0003"
            r7.putExtra(r8, r1)
            android.content.Context r8 = r0.context
            r1 = 2131952144(0x7f130210, float:1.9540722E38)
            java.lang.String r8 = r8.getString(r1)
            java.lang.String r1 = "tips_extras3"
            r7.putExtra(r1, r8)
            android.content.Context r8 = r0.context
            r1 = 2131952145(0x7f130211, float:1.9540724E38)
            java.lang.String r8 = r8.getString(r1)
            java.lang.String r1 = "tips_extras4"
            r7.putExtra(r1, r8)
            java.lang.String r8 = "All condition is OK, show battery protection tips"
            java.lang.String r1 = "PowerUI.TipsManager"
            android.util.Slog.d(r1, r8)
            java.lang.String r8 = "Exception occur"
            r5 = 0
            android.content.Context r6 = r0.context     // Catch: android.app.ForegroundServiceStartNotAllowedException -> L73 java.lang.SecurityException -> L75
            android.content.ComponentName r7 = r6.startForegroundService(r7)     // Catch: android.app.ForegroundServiceStartNotAllowedException -> L73 java.lang.SecurityException -> L75
            goto L7f
        L73:
            r7 = move-exception
            goto L77
        L75:
            r7 = move-exception
            goto L7b
        L77:
            android.util.Log.e(r1, r8, r7)
            goto L7e
        L7b:
            android.util.Log.e(r1, r8, r7)
        L7e:
            r7 = r5
        L7f:
            if (r7 == 0) goto L95
            android.content.Context r7 = r0.context
            android.content.SharedPreferences r7 = r7.getSharedPreferences(r2, r3)
            if (r7 == 0) goto L8d
            android.content.SharedPreferences$Editor r5 = r7.edit()
        L8d:
            if (r5 == 0) goto L95
            r5.putBoolean(r4, r3)
            r5.commit()
        L95:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.SecPowerUI.m2047$$Nest$mcheckBatteryProtectionTipsNotification(com.android.systemui.power.SecPowerUI, int):void");
    }

    /* renamed from: -$$Nest$mcheckChargingNotification, reason: not valid java name */
    public static void m2048$$Nest$mcheckChargingNotification(SecPowerUI secPowerUI, int i, int i2, int i3) {
        int i4 = secPowerUI.mBatteryStatus;
        AnonymousClass6 anonymousClass6 = secPowerUI.mSContextListener;
        SecWarningsUI secWarningsUI = secPowerUI.mWarnings;
        if (i4 == 2) {
            int i5 = secPowerUI.mPlugType;
            if (i5 == 1 || i5 == 2) {
                if (i == 4) {
                    ((SecPowerNotificationWarnings) secWarningsUI).showChargingTypeSwitchedNotice(true);
                }
            } else if (i5 == 4) {
                if (i == 1 || i == 2) {
                    ((SecPowerNotificationWarnings) secWarningsUI).showChargingTypeSwitchedNotice(false);
                }
                if (secPowerUI.mIsMotionDetectionSupported && secPowerUI.mIsSContextEnabled && !secPowerUI.mIsSContextListenerRegistered) {
                    Log.d("PowerUI", "Register SContextListener");
                    secPowerUI.mSContextManager.registerListener(anonymousClass6, 46);
                    secPowerUI.mIsSContextListenerRegistered = true;
                }
                if (!secPowerUI.mWirelessFodState && 4 != i) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secWarningsUI;
                    secPowerNotificationWarnings.getClass();
                    Log.d("PowerUI.Notification", "dismissWirelessFodAlertDialog");
                    AlertDialog alertDialog = secPowerNotificationWarnings.mWirelessFodAlertDialog;
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                }
            } else {
                secPowerUI.dismissChargingNotice();
            }
            if (secPowerUI.mBatteryChargingType != 0 && secPowerUI.mBatterySwellingMode != 1) {
                ((SecPowerNotificationWarnings) secWarningsUI).showChargingNotice();
                boolean z = PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION;
                Handler handler = secPowerUI.mHandler;
                if (z) {
                    if (secPowerUI.mIsChangedStringAfterCharging || secPowerUI.mIsAfterAdaptiveProtection) {
                        handler.removeCallbacks(secPowerUI.mAfterChargingNoticeTask);
                    }
                    if (!secPowerUI.mIsAfterAdaptiveProtection) {
                        handler.postDelayed(secPowerUI.mAfterChargingNoticeTask, 5000L);
                    }
                } else {
                    if (secPowerUI.mIsChangedStringAfterCharging) {
                        handler.removeCallbacks(secPowerUI.mAfterChargingNoticeTask);
                    }
                    handler.postDelayed(secPowerUI.mAfterChargingNoticeTask, 5000L);
                }
            }
        } else {
            secPowerUI.dismissChargingNotice();
            if (secPowerUI.mIsMotionDetectionSupported && secPowerUI.mIsSContextListenerRegistered && secPowerUI.mIsDeviceMoving && i == 4) {
                Log.d("PowerUI", "Unregister SContextListener - From Check charging type");
                secPowerUI.mSContextManager.unregisterListener(anonymousClass6, 46);
                secPowerUI.mIsSContextListenerRegistered = false;
            }
        }
        if (i == 4 && secPowerUI.mPlugType == 0) {
            boolean z2 = i2 == 4 && i3 == 3;
            if (!z2 && !secPowerUI.mIsDeviceMoving) {
                Log.e("PowerUI", "Wireless charger has been disconnected but this is no move case, so we do nothing !!");
                return;
            }
            SecPowerNotificationWarnings secPowerNotificationWarnings2 = (SecPowerNotificationWarnings) secWarningsUI;
            if (secPowerNotificationWarnings2.mFTAMode) {
                Log.d("PowerUI.Notification", "FTA Mode is ON so don't show Wireless charging disconnect warning");
                return;
            }
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("showWirelessChargerDisconnectToast() - byHighTemp = ", "PowerUI.Notification", z2);
            if (z2) {
                Toast.makeText(secPowerNotificationWarnings2.mContext, DeviceType.isTablet() ? secPowerNotificationWarnings2.mContext.getString(R.string.battery_wireless_charging_disconnect_by_high_temperature_text_tablet) : secPowerNotificationWarnings2.mContext.getString(R.string.battery_wireless_charging_disconnect_by_high_temperature_text), 0).show();
            }
        }
    }

    /* renamed from: -$$Nest$mcheckCoolDownStatus, reason: not valid java name */
    public static void m2049$$Nest$mcheckCoolDownStatus(SecPowerUI secPowerUI, Intent intent) {
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

    /* JADX WARN: Removed duplicated region for block: B:38:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* renamed from: -$$Nest$mcheckTipsNotification, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m2050$$Nest$mcheckTipsNotification(com.android.systemui.power.SecPowerUI r12, int r13) {
        /*
            Method dump skipped, instructions count: 412
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.SecPowerUI.m2050$$Nest$mcheckTipsNotification(com.android.systemui.power.SecPowerUI, int):void");
    }

    /* renamed from: -$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge, reason: not valid java name */
    public static void m2051$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge(SecPowerUI secPowerUI) {
        if (secPowerUI.mBatteryLevel >= secPowerUI.mLtcHighSocThreshold && secPowerUI.mProtectBatteryValue != 2) {
            secPowerUI.startScheduling();
            return;
        }
        Context context = secPowerUI.mContext;
        BatteryProtectionUtil batteryProtectionUtil = BatteryProtectionUtil.INSTANCE;
        boolean z = context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0).getBoolean("auto_on_protect_battery_timer_started", false);
        if (!z && secPowerUI.mBatteryLevel < secPowerUI.mLtcReleaseThreshold && secPowerUI.mProtectBatteryValue == 2) {
            Log.i("PowerUI", "send intent to DC, restore battery protection");
            PowerUtils.sendIntentToDc(secPowerUI.mContext, "com.samsung.android.sm.action.TURN_OFF_PROTECT_BATTERY_BY_LONG_TERM_TA");
        } else if (!z && secPowerUI.mBatteryLevel < secPowerUI.mLtcHighSocThreshold && Settings.Global.getInt(secPowerUI.mContext.getContentResolver(), "key_ltc_state", 0) == 1) {
            Log.i("PowerUI", "send intent to DC, turn off LTC soft notification");
            PowerUtils.sendIntentToDc(secPowerUI.mContext, "com.samsung.android.sm.action.TURN_OFF_SOFT_NOTI_BY_LONG_TERM_TA");
        }
        secPowerUI.clearScheduling();
    }

    /* renamed from: -$$Nest$mcheckTurnOnProtectBatteryByLongTa, reason: not valid java name */
    public static void m2052$$Nest$mcheckTurnOnProtectBatteryByLongTa(SecPowerUI secPowerUI) {
        if (secPowerUI.mPlugType != 0 && !BatteryProtectionUtil.isMaximumProtectionEnabled(secPowerUI.mContext)) {
            secPowerUI.startScheduling();
            return;
        }
        Context context = secPowerUI.mContext;
        BatteryProtectionUtil batteryProtectionUtil = BatteryProtectionUtil.INSTANCE;
        boolean z = context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0).getBoolean("auto_on_protect_battery_timer_started", false);
        int i = Settings.Global.getInt(secPowerUI.mContext.getContentResolver(), "auto_on_protect_battery", -1);
        if (!z && secPowerUI.mPlugType == 0 && i == 1) {
            Log.i("PowerUI", "send intent to DC, turn off protect battery");
            PowerUtils.sendIntentToDc(secPowerUI.mContext, "com.samsung.android.sm.action.TURN_OFF_PROTECT_BATTERY_BY_LONG_TERM_TA");
        }
        secPowerUI.clearScheduling();
    }

    /* renamed from: -$$Nest$mcheckWaterDetectionStatus, reason: not valid java name */
    public static void m2053$$Nest$mcheckWaterDetectionStatus(SecPowerUI secPowerUI, Intent intent) {
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
    public static void m2054$$Nest$mcheckWirelessChargingFodStatus(SecPowerUI secPowerUI, Intent intent) {
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

    /* renamed from: -$$Nest$mupdateBatteryNotificationLanguage, reason: not valid java name */
    public static void m2055$$Nest$mupdateBatteryNotificationLanguage(SecPowerUI secPowerUI) {
        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secPowerUI.mWarnings;
        if (secPowerNotificationWarnings.mWarning) {
            secPowerNotificationWarnings.updateNotification();
            Log.d("PowerUI", "Language is changed so notify LowBatteryNotification again");
        }
        if (secPowerUI.mBatteryChargingType > 0 && secPowerUI.mBatterySwellingMode == 0) {
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
            Log.d("PowerUI", "Language is changed so notify ChargingNotification again");
            int i = secPowerUI.mBatteryChargingType;
            int i2 = secPowerUI.mSuperFastCharger;
            secPowerNotificationWarnings.mOldChargingType = secPowerNotificationWarnings.mChargingType;
            secPowerNotificationWarnings.mChargingType = i;
            secPowerNotificationWarnings.mSuperFastCharger = i2;
            secPowerNotificationWarnings.showChargingNotice();
        }
        if (PowerUiRune.INCOMPATIBLE_CHARGER_CHECK && secPowerUI.mBatteryOnline == 0) {
            AlertDialog alertDialog2 = secPowerNotificationWarnings.mIncompatibleChargerDialog;
            if (alertDialog2 != null) {
                alertDialog2.dismiss();
            }
            Log.d("PowerUI.Notification", "dismissing incompatible charger notification");
            secPowerNotificationWarnings.cancelNotification(3);
            Log.d("PowerUI", "Language is changed so notify incompatible charger again");
            secPowerNotificationWarnings.showIncompatibleChargerNotice();
        }
        if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION && BatteryProtectionUtil.isSleepChargingOn(secPowerUI.mContext)) {
            secPowerUI.checkAdaptiveProtectionNotification(secPowerUI.mSleepChargingEvent, secPowerUI.mChargingStartTime);
        }
        if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
            secPowerUI.checkBatteryProtectionNotification();
        }
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.power.SecPowerUI$5] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.power.SecPowerUI$6] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.systemui.power.SecPowerUI$8] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.power.SecPowerUI$1] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.power.SecPowerUI$2] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.power.SecPowerUI$3] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.power.SecPowerUI$4] */
    public SecPowerUI(Context context, BroadcastDispatcher broadcastDispatcher, CommandQueue commandQueue, SecWarningsUI secWarningsUI, WakefulnessLifecycle wakefulnessLifecycle, PowerManager powerManager, UserTracker userTracker, DozeChargingHelper dozeChargingHelper, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper, SystemEventCoordinator systemEventCoordinator) {
        this.mContext = context;
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

    public final void checkAdaptiveProtectionNotification(String str, String str2) {
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("checkAdaptiveProtectionNotification : ", str, ", chargingStartTime : ", str2, ", lev : ");
        m.append(this.mBatteryLevel);
        Slog.i("PowerUI", m.toString());
        boolean equalsIgnoreCase = "on".equalsIgnoreCase(str);
        SecWarningsUI secWarningsUI = this.mWarnings;
        if (equalsIgnoreCase && this.mProtectBatteryValue == 4) {
            if (this.mBatteryLevel != 100) {
                Slog.i("PowerUI", "show AdaptiveProtectionNotification");
                ((SecPowerNotificationWarnings) secWarningsUI).showAdaptiveProtectionNotification(str2);
            }
            BatteryProtectionUtil.setSleepChargingStatus(1, this.mContext);
            Settings.Global.putString(this.mContext.getContentResolver(), "sleep_charging_finish_time", str2);
            return;
        }
        if ("update".equalsIgnoreCase(str) && this.mProtectBatteryValue == 4) {
            if (this.mBatteryLevel != 100) {
                Slog.i("PowerUI", "update AdaptiveProtectionNotification");
                ((SecPowerNotificationWarnings) secWarningsUI).showAdaptiveProtectionNotification(str2);
            }
            BatteryProtectionUtil.setSleepChargingStatus(2, this.mContext);
            Settings.Global.putString(this.mContext.getContentResolver(), "sleep_charging_finish_time", str2);
            return;
        }
        if (!"off".equalsIgnoreCase(str)) {
            Slog.i("PowerUI", "dismiss AdaptiveProtectionNotification");
            setSleepChargingOff();
        } else {
            Slog.i("PowerUI", "off AdaptiveProtectionNotification");
            setSleepChargingOff();
            this.mIsAfterAdaptiveProtection = true;
        }
    }

    public final void checkBatteryProtectionNotification() {
        boolean isSleepChargingOn = BatteryProtectionUtil.isSleepChargingOn(this.mContext);
        SecWarningsUI secWarningsUI = this.mWarnings;
        if (!isSleepChargingOn && this.mBatteryStatus >= 4 && this.mBatteryHealth == 2 && this.mBatteryOverheatLevel == 0) {
            int i = this.mProtectionEvent;
            int i2 = this.mBatteryMiscEvent;
            if (i == 0 && !BatteryProtectionUtil.isProtectedFullyByMaximum(i2)) {
                return;
            }
            ((SecPowerNotificationWarnings) secWarningsUI).showNotification(9);
            return;
        }
        if (!BatteryProtectionUtil.isSleepChargingOn(this.mContext) && this.mProtectBatteryValue != 0 && this.mPlugType != 0 && this.mBatteryStatus != 2 && this.mBatteryHealth == 2 && this.mBatteryOverheatLevel == 0) {
            int i3 = this.mProtectionEvent;
            int i4 = this.mBatteryMiscEvent;
            if (i3 != 0 || BatteryProtectionUtil.isProtectedFullyByMaximum(i4)) {
                return;
            }
        }
        ((SecPowerNotificationWarnings) secWarningsUI).cancelNotification(9);
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

    public final void clearScheduling() {
        Context context = this.mContext;
        BatteryProtectionUtil batteryProtectionUtil = BatteryProtectionUtil.INSTANCE;
        boolean z = PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE;
        BatteryProtectionUtil batteryProtectionUtil2 = BatteryProtectionUtil.INSTANCE;
        if (z) {
            batteryProtectionUtil2.getClass();
            Settings.Global.putLong(context.getContentResolver(), "ltc_highsoc_exceed_time", 0L);
        } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
            batteryProtectionUtil2.getClass();
            Settings.Global.putString(context.getContentResolver(), "charger_connected_time", "");
        }
        batteryProtectionUtil2.getClass();
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
        printWriter.println("LowBatteryReminderLevels = " + Arrays.toString(this.mLowBatteryReminderLevels));
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("BatteryLevel = "), this.mBatteryLevel, printWriter, "BatteryStatus = "), this.mBatteryStatus, printWriter, "PlugType = "), this.mPlugType, printWriter, "BatteryHealth = "), this.mBatteryHealth, printWriter, "ScreenOffTime = ");
        m.append(this.mScreenOffTime);
        printWriter.print(m.toString());
        if (this.mScreenOffTime >= 0) {
            printWriter.print(" (");
            printWriter.print(SystemClock.elapsedRealtime() - this.mScreenOffTime);
            printWriter.print(" ago)");
        }
        printWriter.println();
        printWriter.println("bucket = " + findBatteryLevelBucket$1(this.mBatteryLevel));
        final SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) this.mWarnings;
        secPowerNotificationWarnings.getClass();
        if (PowerUiRune.CHN_SMART_MANAGER) {
            return;
        }
        HandlerWrapper handlerWrapper = new HandlerWrapper();
        secPowerNotificationWarnings.mHandlerWrapper = handlerWrapper;
        handlerWrapper.mWorker.post(new Runnable() { // from class: com.android.systemui.power.SecPowerNotificationWarnings$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SecPowerNotificationWarnings secPowerNotificationWarnings2 = SecPowerNotificationWarnings.this;
                secPowerNotificationWarnings2.getClass();
                Log.i("PowerUI.Notification", "dumpsAdditionalBatteryInfo call DC service in worker thread");
                Intent intent = new Intent();
                intent.setPackage(PowerUiConstants.DC_PACKAGE_NAME);
                intent.setAction("com.samsung.android.sm.DUMP");
                try {
                    if (secPowerNotificationWarnings2.mContext.getPackageManager().queryIntentServices(intent, 0).isEmpty()) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("dump", (Integer) 1);
                        Log.i("PowerUI.Notification", "update dc dump provider");
                        secPowerNotificationWarnings2.mContext.getContentResolver().update(PowerUiConstants.SMART_MGR_VERIFY_FORCED_APP_STANDBY_URI, contentValues, null, null);
                    } else {
                        Log.i("PowerUI.Notification", "start dc dump service");
                        secPowerNotificationWarnings2.mContext.startService(intent);
                        Log.w("PowerUI.Notification", "quitBgThread");
                        secPowerNotificationWarnings2.mHandlerWrapper.mWorkerThread.quitSafely();
                        secPowerNotificationWarnings2.mHandlerWrapper = null;
                    }
                } catch (Error | Exception e) {
                    Log.w("PowerUI.Notification", "err", e);
                }
            }
        });
    }

    public final int findBatteryLevelBucket$1(int i) {
        if (i >= this.mLowBatteryAlertCloseLevel) {
            return 1;
        }
        int[] iArr = this.mLowBatteryReminderLevels;
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

    public final void removeChargerView() {
        WindowManager windowManager;
        String str = this.mCurrentChargingAnimation;
        if (str != null) {
            if (str.equals("now_bar_charging_vi")) {
                NowBarItem nowBarItem = new NowBarItem();
                nowBarItem.setNowBarKey("charging_vi_now_bar_key");
                nowBarItem.setNowBarPackage("com.android.systemui");
                nowBarItem.setNowBarViewStyle(6);
                this.mFaceWidgetNotificationControllerWrapper.removeItem(nowBarItem);
                this.mChargerNowBarView = null;
            } else if (this.mCurrentChargingAnimation.equals("old_charging_vi")) {
                ChargerAnimationView chargerAnimationView = this.mChargerAnimationView;
                if (chargerAnimationView != null && (windowManager = this.mChargerAnimationWindowManager) != null) {
                    windowManager.removeView(chargerAnimationView);
                    this.mChargerAnimationView = null;
                }
                this.mChargerAnimationWindowManager = null;
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

    public final void requestNowBarChargingInfo(int i) {
        if (this.mCurrentChargingAnimation != null || this.mBatteryStatus == 3 || i != 0 || this.mPlugType <= 0) {
            return;
        }
        NowBarItem nowBarItem = new NowBarItem();
        nowBarItem.setNowBarKey("charging_vi_now_bar_key");
        nowBarItem.setNowBarPackage("com.android.systemui");
        nowBarItem.setNowBarViewStyle(6);
        this.mFaceWidgetNotificationControllerWrapper.removeItem(nowBarItem);
    }

    public final void setChargerAnimationView() {
        ObjectAnimator duration;
        if (this.mChargerAnimationWindowLp == null) {
            WindowManager.LayoutParams layoutParam = getLayoutParam("PowerUI.ChargerAnimationViewLp");
            this.mChargerAnimationWindowLp = layoutParam;
            layoutParam.type = 2021;
            layoutParam.flags |= 24;
        }
        boolean isFolded = SemWindowManager.getInstance().isFolded();
        if (this.mChargerAnimationWindowManager == null) {
            if (PowerUtils.isFlipSubDisplayOn(isFolded)) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("folder state : ", "PowerUI", isFolded);
                Context subDisplayContext = PowerUtils.getSubDisplayContext(this.mContext);
                if (PowerUiRune.COVER_DISPLAY_LARGE_SCREEN) {
                    this.mChargerAnimationWindowLp.layoutInDisplayCutoutMode = 3;
                }
                this.mChargerAnimationWindowManager = (WindowManager) subDisplayContext.getSystemService("window");
                if (SystemProperties.get("ro.product.vendor.name").toLowerCase().contains("bloom")) {
                    this.mChargerAnimationView = (ChargerAnimationView) View.inflate(subDisplayContext, R.layout.battery_charger_animation_bloom, null);
                } else {
                    this.mChargerAnimationView = (ChargerAnimationView) View.inflate(subDisplayContext, R.layout.battery_charger_animation, null);
                }
            } else {
                this.mChargerAnimationWindowManager = (WindowManager) this.mContext.getSystemService("window");
                this.mChargerAnimationView = (ChargerAnimationView) View.inflate(this.mContext, R.layout.battery_charger_animation, null);
            }
        }
        this.mChargerAnimationWindowManager.addView(this.mChargerAnimationView, this.mChargerAnimationWindowLp);
        ChargerAnimationView chargerAnimationView = this.mChargerAnimationView;
        chargerAnimationView.getClass();
        chargerAnimationView.mAnimationPlaying = false;
        chargerAnimationView.setVisibility(8);
        ChargerAnimationView chargerAnimationView2 = this.mChargerAnimationView;
        chargerAnimationView2.mAnimationListener = this;
        chargerAnimationView2.mDozeChargingHelper = this.mDozeChargingHelper;
        boolean isFolded2 = SemWindowManager.getInstance().isFolded();
        chargerAnimationView2.mIsSubscreenOff = PowerUtils.isFlipSubDisplayOn(isFolded2) && ((DisplayManager) chargerAnimationView2.mContext.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN")[1].getState() == 1;
        chargerAnimationView2.mNeedFullScreenBlur = ChargerAnimationUtil.isAodOrLockScreen() || PowerUtils.isFlipSubDisplayOn(isFolded2);
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
        final ChargerAnimationView chargerAnimationView3 = this.mChargerAnimationView;
        int i = this.mBatteryLevel;
        chargerAnimationView3.mSuperFastChargingType = this.mSuperFastCharger;
        chargerAnimationView3.mCurrentBatteryLevel = i;
        if (chargerAnimationView3.mAnimationPlaying) {
            Log.d("PowerUI.ChargerAnimationView", "Animation is playing, return");
            return;
        }
        chargerAnimationView3.mAnimationPlaying = true;
        chargerAnimationView3.setVisibility(0);
        chargerAnimationView3.mDozeChargingHelper.handleDisplayStateWhenDozeCharging();
        int i2 = chargerAnimationView3.mSuperFastChargingType;
        String str = i2 != 3 ? i2 != 4 ? i2 != 5 ? "charging_l1" : "charging_l4" : "charging_l3" : "charging_l2";
        if (ChargerAnimationUtil.isAodOrLockScreen()) {
            str = str.concat("_lock");
        }
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ".json");
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Animation applied : ", m, "PowerUI.ChargerAnimationView");
        chargerAnimationView3.mChargerAnimationView.setAnimation(m);
        if (chargerAnimationView3.mIsSubscreenOff) {
            chargerAnimationView3.mFadeInAnimation = ObjectAnimator.ofFloat(chargerAnimationView3.mChargerContainer, "alpha", 0.0f, 1.0f).setDuration(chargerAnimationView3.mNeedFullScreenBlur ? 400L : 200L);
            duration = ObjectAnimator.ofFloat(chargerAnimationView3.mChargerContainer, "alpha", 1.0f, 0.0f).setDuration(chargerAnimationView3.mNeedFullScreenBlur ? 400L : 100L);
        } else {
            chargerAnimationView3.mFadeInAnimation = ObjectAnimator.ofFloat(chargerAnimationView3, "alpha", 0.0f, 1.0f).setDuration(chargerAnimationView3.mNeedFullScreenBlur ? 400L : 200L);
            duration = ObjectAnimator.ofFloat(chargerAnimationView3, "alpha", 1.0f, 0.0f).setDuration(chargerAnimationView3.mNeedFullScreenBlur ? 400L : 100L);
        }
        duration.setStartDelay(chargerAnimationView3.mNeedFullScreenBlur ? 1600L : 1350L);
        duration.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.power.ChargerAnimationView.1
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
                SecPowerUI secPowerUI = (SecPowerUI) ChargerAnimationView.this.mAnimationListener;
                secPowerUI.getClass();
                Slog.i("PowerUI", "onChargerAnimationEnd");
                secPowerUI.removeChargerView();
                secPowerUI.mDozeChargingHelper.restoreDisplayStateWhenDozeCharging();
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
        Context context = chargerAnimationView3.mContext;
        int i3 = chargerAnimationView3.mSuperFastChargingType;
        imageView.setImageDrawable(context.getDrawable((i3 == 3 || i3 == 4 || i3 == 5) ? R.drawable.ic_icon_superfast : R.drawable.ic_icon_charging));
        PathInterpolator pathInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        PathInterpolator pathInterpolator2 = new PathInterpolator(0.33f, 0.0f, 0.4f, 1.0f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleX", 0.5f, 1.05f);
        ofFloat.setDuration(233L);
        ofFloat.setStartDelay(chargerAnimationView3.mNeedFullScreenBlur ? 100L : 0L);
        ofFloat.setInterpolator(pathInterpolator2);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleY", 0.5f, 1.05f);
        ofFloat2.setDuration(233L);
        ofFloat2.setInterpolator(pathInterpolator2);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleX", 1.05f, 0.96f);
        ofFloat3.setDuration(267L);
        ofFloat3.setInterpolator(pathInterpolator);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleY", 1.05f, 0.96f);
        ofFloat4.setDuration(267L);
        ofFloat4.setInterpolator(pathInterpolator);
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleX", 0.96f, 1.0f);
        ofFloat5.setDuration(183L);
        ofFloat5.setInterpolator(pathInterpolator2);
        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(chargerAnimationView3.mChargingIconView, "scaleY", 0.96f, 1.0f);
        ofFloat6.setDuration(183L);
        ofFloat6.setInterpolator(pathInterpolator2);
        chargerAnimationView3.mAlphaAnimatorSet.play(chargerAnimationView3.mFadeInAnimation).with(ofFloat);
        chargerAnimationView3.mAlphaAnimatorSet.play(ofFloat).with(ofFloat2).before(ofFloat3);
        chargerAnimationView3.mAlphaAnimatorSet.play(ofFloat3).with(ofFloat4).before(ofFloat5);
        chargerAnimationView3.mAlphaAnimatorSet.play(ofFloat5).with(ofFloat6);
        chargerAnimationView3.mChargerAnimationView.playAnimation();
        chargerAnimationView3.mAlphaAnimatorSet.start();
        chargerAnimationView3.setBatteryLevelText();
        Log.d("PowerUI.ChargerAnimationView", "Animation Started");
    }

    public final void setSleepChargingOff() {
        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) this.mWarnings;
        secPowerNotificationWarnings.cancelNotification(10);
        secPowerNotificationWarnings.mOptimizationChargingFinishTime = "";
        BatteryProtectionUtil.setSleepChargingStatus(0, this.mContext);
        this.mChargingStartTime = "";
        this.mSleepChargingEvent = "off";
    }

    public final void setWirelessMisalignView(int i) {
        if (this.mWirelessMisalignWindowLp == null) {
            WindowManager.LayoutParams layoutParam = getLayoutParam("PowerUI.WirelessMisalignViewLp");
            this.mWirelessMisalignWindowLp = layoutParam;
            layoutParam.type = 2009;
        }
        if (PowerUtils.isFlipSubDisplayOn(SemWindowManager.getInstance().isFolded()) && PowerUiRune.COVER_DISPLAY_LARGE_SCREEN) {
            this.mWirelessMisalignWindowLp.layoutInDisplayCutoutMode = 3;
        }
        if (this.mWirelessMisalignWindowManager == null) {
            Context context = this.mContext;
            this.mWirelessMisalignWindowManager = PowerUtils.isFlipSubDisplayOn(SemWindowManager.getInstance().isFolded()) ? (WindowManager) PowerUtils.getSubDisplayContext(context).getSystemService("window") : (WindowManager) context.getSystemService("window");
        }
        if (this.mWirelessMisalignView == null) {
            Context context2 = this.mContext;
            boolean isFolded = SemWindowManager.getInstance().isFolded();
            this.mWirelessMisalignView = PowerUtils.isFlipSubDisplayOn(isFolded) ? (WirelessMisalignView) View.inflate(PowerUtils.getSubDisplayContext(context2), R.layout.battery_misalign_subdisplay_layout, null) : (isFolded && BasicRune.BASIC_FOLDABLE_TYPE_FOLD) ? (WirelessMisalignView) View.inflate(context2, R.layout.battery_misalign_normal_layout, null) : BasicRune.BASIC_FOLDABLE_TYPE_FOLD ? (WirelessMisalignView) View.inflate(context2, R.layout.battery_misalign_fold_layout, null) : BasicRune.BASIC_FOLDABLE_TYPE_FLIP ? (WirelessMisalignView) View.inflate(context2, R.layout.battery_misalign_flip_layout, null) : (WirelessMisalignView) View.inflate(context2, R.layout.battery_misalign_normal_layout, null);
        }
        this.mWirelessMisalignWindowManager.addView(this.mWirelessMisalignView, this.mWirelessMisalignWindowLp);
        WirelessMisalignView wirelessMisalignView = this.mWirelessMisalignView;
        wirelessMisalignView.mListener = this;
        if (i == 0) {
            wirelessMisalignView.mTextContainerLayout.setVisibility(0);
            wirelessMisalignView.mButton.setVisibility(0);
            wirelessMisalignView.mCenterImageView.setImageResource(R.drawable.overlay_center_alignment);
        } else if (i == 1) {
            wirelessMisalignView.mTextContainerLayout.setVisibility(8);
            wirelessMisalignView.mButton.setVisibility(8);
            wirelessMisalignView.mCenterImageView.setImageResource(R.drawable.overlay_center_alignment_ok);
        }
        if (this.mPowerManager != null) {
            Log.i("PowerUI", "turn on screen - misalign view");
            this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), this.mContext.getOpPackageName());
            if (this.mWirelessMisalignWakeLock == null) {
                this.mWirelessMisalignWakeLock = this.mPowerManager.newWakeLock(268435462, "PowerUI");
            }
            this.mWirelessMisalignWakeLock.acquire(30000L);
        }
        this.mWirelessMisalignView.setWirelessMisalignViewVisibility(0);
    }

    public final boolean skipChargingUi(int i, int i2, int i3, boolean z) {
        int i4;
        int i5;
        if (BatteryProtectionUtil.isProtectedFullyByMaximum(this.mBatteryMiscEvent)) {
            this.mSkipChargingUiMsg = "protect battery cut off";
            return true;
        }
        if (i == -1 || (i4 = this.mPlugType) == 0 || (i5 = this.mBatteryStatus) != 2 || !this.mFullyConnected || ((i2 == 5 || i2 == i5) && i == i4 && z)) {
            this.mSkipChargingUiMsg = "Plug reason";
        } else {
            int i6 = this.mBatteryMiscEvent;
            BatteryProtectionUtil.INSTANCE.getClass();
            if (i2 == 4 && i5 == 2) {
                boolean isProtectedFullyByMaximum = BatteryProtectionUtil.isProtectedFullyByMaximum(i6);
                boolean isProtectedFullyByMaximum2 = BatteryProtectionUtil.isProtectedFullyByMaximum(i3);
                EmergencyButtonController$$ExternalSyntheticOutline0.m("protectFully prior : ", ", current : ", "PowerUI.BatteryProtectionUtil", isProtectedFullyByMaximum2, isProtectedFullyByMaximum);
                if (isProtectedFullyByMaximum2 && !isProtectedFullyByMaximum && !this.mIsChangedBatteryProtectionOnCharging) {
                    this.mSkipChargingUiMsg = "Recharge from battery protection, value : " + this.mProtectBatteryValue + " / Sleep charging : " + this.mSleepChargingEvent;
                }
            }
            if (this.mBatteryOnline == 99) {
                this.mSkipChargingUiMsg = "AFC retry case";
            } else {
                int i7 = this.mPlugType;
                if ((i7 != 1 || i != 2) && (i7 != 2 || i != 1)) {
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
                this.mSkipChargingUiMsg = "Only cable charger type changed";
            }
        }
        return true;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Intent registerReceiver;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int integer = this.mContext.getResources().getInteger(android.R.integer.config_defaultRefreshRate);
        int integer2 = this.mContext.getResources().getInteger(android.R.integer.config_nightDisplayColorTemperatureMin);
        if (integer2 < integer) {
            integer2 = integer;
        }
        int[] iArr = this.mLowBatteryReminderLevels;
        iArr[0] = integer2;
        iArr[1] = integer;
        this.mLowBatteryAlertCloseLevel = this.mContext.getResources().getInteger(android.R.integer.config_nightDisplayColorTemperatureMax) + integer2;
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
        boolean z = PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION;
        if (z) {
            intentFilter.addAction("com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING");
        }
        intentFilter.addAction("com.samsung.server.BatteryService.action.SEC_BATTERY_REMAINING_CHARGING_TIME_CHANGED");
        SecPowerUI secPowerUI2 = SecPowerUI.this;
        secPowerUI2.mBroadcastDispatcher.registerReceiverWithHandler(receiver, intentFilter, secPowerUI2.mHandler);
        if (!receiver.mHasReceivedBattery && (registerReceiver = SecPowerUI.this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"), 2)) != null) {
            receiver.onReceive(SecPowerUI.this.mContext, registerReceiver);
        }
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        boolean equals = "1".equals(SystemProperties.get("sys.boot_completed"));
        this.mBootCompleted = equals;
        if (equals) {
            checkOverheatShutdownHappened();
            if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                int protectBatteryValue = BatteryProtectionUtil.getProtectBatteryValue(this.mContext);
                int i = Settings.Global.getInt(this.mContext.getContentResolver(), "prev_protect_battery_ltc", -1);
                if (protectBatteryValue == 2) {
                    Settings.Global.putInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_PROTECT_BATTERY, i);
                    Settings.Global.putInt(this.mContext.getContentResolver(), "prev_protect_battery_ltc", -1);
                }
                clearScheduling();
            }
            if (z) {
                setSleepChargingOff();
                this.mIsAfterAdaptiveProtection = false;
            }
        }
        if (PowerUiRune.PROTECT_BATTERY_CUTOFF) {
            Handler handler = this.mHandler;
            contentResolver.registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_PROTECT_BATTERY), false, new ContentObserver(handler) { // from class: com.android.systemui.power.SecPowerUI.10
                @Override // android.database.ContentObserver
                public final void onChange(boolean z2) {
                    SecPowerUI secPowerUI3 = SecPowerUI.this;
                    secPowerUI3.mIsChangedBatteryProtectionOnCharging = true;
                    int i2 = secPowerUI3.mProtectBatteryValue;
                    secPowerUI3.mProtectBatteryValue = BatteryProtectionUtil.getProtectBatteryValue(secPowerUI3.mContext);
                    if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
                        ((SecPowerNotificationWarnings) SecPowerUI.this.mWarnings).cancelNotification(9);
                        SecPowerUI secPowerUI4 = SecPowerUI.this;
                        int i3 = secPowerUI4.mProtectBatteryValue;
                        if ((i2 == 0 && i3 != 0) || (i2 != 0 && i3 == 0)) {
                            secPowerUI4.checkBatteryProtectionNotification();
                        }
                    }
                    if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION && i2 == 4) {
                        SecPowerUI secPowerUI5 = SecPowerUI.this;
                        if (secPowerUI5.mProtectBatteryValue != 4) {
                            secPowerUI5.setSleepChargingOff();
                            SecPowerUI.this.mIsAfterAdaptiveProtection = false;
                        }
                    }
                    if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                        SecPowerUI.m2051$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge(SecPowerUI.this);
                    } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
                        SecPowerUI.m2052$$Nest$mcheckTurnOnProtectBatteryByLongTa(SecPowerUI.this);
                    }
                    SecPowerUI secPowerUI6 = SecPowerUI.this;
                    int i4 = secPowerUI6.mProtectBatteryValue;
                    if ((i2 != 1 || i4 == 1) && (i2 == 1 || i4 != 1)) {
                        return;
                    }
                    SecPowerUI.m2048$$Nest$mcheckChargingNotification(secPowerUI6, secPowerUI6.mPlugType, secPowerUI6.mBatteryStatus, secPowerUI6.mBatteryHealth);
                }
            }, -1);
            this.mProtectBatteryValue = BatteryProtectionUtil.getProtectBatteryValue(this.mContext);
            if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                this.mLtcHighSocThreshold = Settings.Global.getInt(this.mContext.getContentResolver(), "ltc_highsoc_threshold", 95);
                this.mLtcReleaseThreshold = Settings.Global.getInt(this.mContext.getContentResolver(), "ltc_release_threshold", 75);
            }
            contentResolver.registerContentObserver(Settings.Global.getUriFor("battery_protection_threshold"), false, new ContentObserver(handler) { // from class: com.android.systemui.power.SecPowerUI.11
                @Override // android.database.ContentObserver
                public final void onChange(boolean z2) {
                    super.onChange(z2);
                    SecPowerUI secPowerUI3 = SecPowerUI.this;
                    SecPowerUI.m2048$$Nest$mcheckChargingNotification(secPowerUI3, secPowerUI3.mPlugType, secPowerUI3.mBatteryStatus, secPowerUI3.mBatteryHealth);
                    SecPowerUI.this.checkBatteryProtectionNotification();
                    SecPowerUI secPowerUI4 = SecPowerUI.this;
                    secPowerUI4.mIsChangedBatteryProtectionOnCharging = true;
                    if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                        SecPowerUI.m2051$$Nest$mcheckTurnOnBatteryProtectionByLongTermCharge(secPowerUI4);
                    }
                }
            }, -1);
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
    }

    public final void startScheduling() {
        Context context = this.mContext;
        BatteryProtectionUtil batteryProtectionUtil = BatteryProtectionUtil.INSTANCE;
        if (context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0).getBoolean("auto_on_protect_battery_timer_started", false)) {
            return;
        }
        Log.i("PowerUI", "Meet soc conditions, start scheduling");
        Context context2 = this.mContext;
        boolean z = PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE;
        BatteryProtectionUtil batteryProtectionUtil2 = BatteryProtectionUtil.INSTANCE;
        if (z) {
            long currentTimeMillis = System.currentTimeMillis();
            batteryProtectionUtil2.getClass();
            Settings.Global.putLong(context2.getContentResolver(), "ltc_highsoc_exceed_time", currentTimeMillis);
        } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
            String valueOf = String.valueOf(System.currentTimeMillis());
            batteryProtectionUtil2.getClass();
            Settings.Global.putString(context2.getContentResolver(), "charger_connected_time", valueOf);
        }
        batteryProtectionUtil2.getClass();
        SharedPreferences sharedPreferences = context2.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("auto_on_protect_battery_timer_started", true);
            edit.commit();
        }
    }
}
