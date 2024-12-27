package com.android.server.power;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.trust.TrustManager;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.hardware.display.DisplayManagerInternal;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IWakeLockCallback;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.WorkSource;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.EventLog;
import android.util.IndentingPrintWriter;
import android.util.SparseArray;
import android.view.WindowManagerPolicyConstants;

import com.android.internal.app.IBatteryStats;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.input.InputManagerService;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.PhoneWindowManagerExt;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.power.WakefulnessSessionObserver.WakefulnessSessionPowerGroup;
import com.android.server.power.feature.PowerManagerFlags;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wm.DisplayPolicy;
import com.android.server.wm.WindowState;

import com.samsung.android.edge.EdgeManagerInternal;

import java.io.PrintWriter;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class Notifier {
    public static final VibrationEffect CHARGING_VIBRATION_EFFECT =
            VibrationEffect.createWaveform(
                    new long[] {40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40},
                    new int[] {1, 4, 11, 25, 44, 67, 91, 114, 123, 103, 79, 55, 34, 17, 7, 2},
                    -1);
    public static final VibrationAttributes HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES =
            VibrationAttributes.createForUsage(50);
    public final AppOpsManager mAppOps;
    public final Executor mBackgroundExecutor;
    public final IBatteryStats mBatteryStats;
    public boolean mBroadcastInProgress;
    public long mBroadcastStartTime;
    public int mBroadcastedInteractiveState;
    public final Context mContext;
    public final EdgeManagerInternal mEdgeInternal;
    public final PowerManagerFlags mFlags;
    public final AnonymousClass3 mGoToSleepBroadcastDone;
    public final NotifierHandler mHandler;
    public final RealInjector mInjector;
    public boolean mPendingGoToSleepBroadcast;
    public int mPendingInteractiveState;
    public boolean mPendingWakeUpBroadcast;
    public final WindowManagerPolicy mPolicy;
    public boolean mProximity;
    public final Intent mScreenOffByProximityIntent;
    public final Intent mScreenOffIntent;
    public final Intent mScreenOnByProximityIntent;
    public final Intent mScreenOnIntent;
    public final Bundle mScreenOnOffOptions;
    public final boolean mShowWirelessChargingAnimationConfig;
    public final PowerManagerService.SuspendBlockerImpl mSuspendBlocker;
    public final boolean mSuspendWhenScreenOffDueToProximityConfig;
    public final TrustManager mTrustManager;
    public boolean mUserActivityPending;
    public final Vibrator mVibrator;
    public final WakeLockLog mWakeLockLog;
    public final AnonymousClass3 mWakeUpBroadcastDone;
    public final WakefulnessSessionObserver mWakefulnessSessionObserver;
    public final Object mLock = new Object();
    public final SparseArray mInteractivityByGroupId = new SparseArray();
    public final Interactivity mGlobalInteractivity = new Interactivity();
    public final AtomicBoolean mIsPlayingChargingStartedFeedback = new AtomicBoolean(false);
    public final ActivityManagerInternal mActivityManagerInternal =
            (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
    public final InputManagerService.LocalService mInputManagerInternal =
            (InputManagerService.LocalService)
                    LocalServices.getService(InputManagerService.LocalService.class);
    public final InputMethodManagerInternal mInputMethodManagerInternal =
            (InputMethodManagerInternal) LocalServices.getService(InputMethodManagerInternal.class);
    public final StatusBarManagerInternal mStatusBarManagerInternal =
            (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
    public final DisplayManagerInternal mDisplayManagerInternal =
            (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Interactivity {
        public int changeOffReason;
        public int changeOnReason;
        public long changeStartTime;
        public boolean isChanging;
        public boolean isInteractive = true;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotifierHandler extends Handler {
        public NotifierHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            StatusBarManagerInternal statusBarManagerInternal;
            IStatusBar iStatusBar;
            switch (message.what) {
                case 1:
                    Notifier notifier = Notifier.this;
                    int i = message.arg1;
                    int i2 = message.arg2;
                    synchronized (notifier.mLock) {
                        try {
                            if (notifier.mUserActivityPending) {
                                notifier.mUserActivityPending = false;
                                ((TelephonyManager)
                                                notifier.mContext.getSystemService(
                                                        TelephonyManager.class))
                                        .notifyUserActivity();
                                InputManagerService.this.mKeyboardBacklightController
                                        .notifyUserActivity();
                                PhoneWindowManager phoneWindowManager =
                                        (PhoneWindowManager) notifier.mPolicy;
                                phoneWindowManager.getClass();
                                if (i == 0 && i2 == 2) {
                                    DisplayPolicy displayPolicy =
                                            phoneWindowManager.mDefaultDisplayPolicy;
                                    if (!displayPolicy.mAwake) {
                                        WindowState windowState = displayPolicy.mNotificationShade;
                                        displayPolicy.mService.mAtmService
                                                .setProcessAnimatingWhileDozing(
                                                        windowState != null
                                                                ? windowState.mSession.mProcess
                                                                : null);
                                    }
                                }
                                synchronized (phoneWindowManager.mScreenLockTimeout) {
                                    try {
                                        if (phoneWindowManager.mLockScreenTimerActive) {
                                            phoneWindowManager.mHandler.removeCallbacks(
                                                    phoneWindowManager.mScreenLockTimeout);
                                            phoneWindowManager.mHandler.postDelayed(
                                                    phoneWindowManager.mScreenLockTimeout,
                                                    phoneWindowManager.mLockScreenTimeout);
                                        }
                                    } finally {
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                case 2:
                    Notifier notifier2 = Notifier.this;
                    VibrationEffect vibrationEffect = Notifier.CHARGING_VIBRATION_EFFECT;
                    notifier2.sendNextBroadcast();
                    return;
                case 3:
                    Notifier notifier3 = Notifier.this;
                    int i3 = message.arg1;
                    int i4 = message.arg2;
                    VibrationEffect vibrationEffect2 = Notifier.CHARGING_VIBRATION_EFFECT;
                    notifier3.playChargingStartedFeedback(i4, true);
                    if (notifier3.mShowWirelessChargingAnimationConfig
                            && (statusBarManagerInternal = notifier3.mStatusBarManagerInternal)
                                    != null
                            && (iStatusBar = StatusBarManagerService.this.mBar) != null) {
                        try {
                            iStatusBar.showWirelessChargingAnimation(i3);
                        } catch (RemoteException unused) {
                        }
                    }
                    notifier3.mSuspendBlocker.release();
                    return;
                case 4:
                    removeMessages(4);
                    Notifier notifier4 = Notifier.this;
                    VibrationEffect vibrationEffect3 = Notifier.CHARGING_VIBRATION_EFFECT;
                    notifier4.getClass();
                    notifier4.mContext.sendBroadcastAsUser(
                            new Intent("android.os.action.ENHANCED_DISCHARGE_PREDICTION_CHANGED")
                                    .addFlags(1073741824),
                            UserHandle.ALL);
                    return;
                case 5:
                    Notifier.this.mTrustManager.setDeviceLockedForUser(message.arg1, true);
                    return;
                case 6:
                    Notifier notifier5 = Notifier.this;
                    int i5 = message.arg1;
                    VibrationEffect vibrationEffect4 = Notifier.CHARGING_VIBRATION_EFFECT;
                    notifier5.playChargingStartedFeedback(i5, false);
                    notifier5.mSuspendBlocker.release();
                    return;
                case 7:
                    Notifier notifier6 = Notifier.this;
                    VibrationEffect vibrationEffect5 = Notifier.CHARGING_VIBRATION_EFFECT;
                    notifier6.getClass();
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RealInjector {}

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.power.Notifier$3] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.power.Notifier$3] */
    public Notifier(
            Looper looper,
            Context context,
            IBatteryStats iBatteryStats,
            PowerManagerService.SuspendBlockerImpl suspendBlockerImpl,
            WindowManagerPolicy windowManagerPolicy,
            Executor executor,
            PowerManagerFlags powerManagerFlags) {
        this.mEdgeInternal = null;
        final int i = 0;
        this.mWakeUpBroadcastDone =
                new IIntentReceiver.Stub(this) { // from class: com.android.server.power.Notifier.3
                    public final /* synthetic */ Notifier this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void performReceive(
                            Intent intent,
                            int i2,
                            String str,
                            Bundle bundle,
                            boolean z,
                            boolean z2,
                            int i3) {
                        switch (i) {
                            case 0:
                                EventLog.writeEvent(
                                        2726,
                                        1,
                                        Long.valueOf(
                                                SystemClock.uptimeMillis()
                                                        - this.this$0.mBroadcastStartTime),
                                        1);
                                this.this$0.sendNextBroadcast();
                                break;
                            default:
                                EventLog.writeEvent(
                                        2726,
                                        0,
                                        Long.valueOf(
                                                SystemClock.uptimeMillis()
                                                        - this.this$0.mBroadcastStartTime),
                                        1);
                                this.this$0.sendNextBroadcast();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mGoToSleepBroadcastDone =
                new IIntentReceiver.Stub(this) { // from class: com.android.server.power.Notifier.3
                    public final /* synthetic */ Notifier this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void performReceive(
                            Intent intent,
                            int i22,
                            String str,
                            Bundle bundle,
                            boolean z,
                            boolean z2,
                            int i3) {
                        switch (i2) {
                            case 0:
                                EventLog.writeEvent(
                                        2726,
                                        1,
                                        Long.valueOf(
                                                SystemClock.uptimeMillis()
                                                        - this.this$0.mBroadcastStartTime),
                                        1);
                                this.this$0.sendNextBroadcast();
                                break;
                            default:
                                EventLog.writeEvent(
                                        2726,
                                        0,
                                        Long.valueOf(
                                                SystemClock.uptimeMillis()
                                                        - this.this$0.mBroadcastStartTime),
                                        1);
                                this.this$0.sendNextBroadcast();
                                break;
                        }
                    }
                };
        this.mContext = context;
        this.mFlags = powerManagerFlags;
        this.mBatteryStats = iBatteryStats;
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mSuspendBlocker = suspendBlockerImpl;
        this.mPolicy = windowManagerPolicy;
        this.mWakefulnessSessionObserver = new WakefulnessSessionObserver(context);
        this.mTrustManager = (TrustManager) context.getSystemService(TrustManager.class);
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
        this.mHandler = new NotifierHandler(looper);
        this.mBackgroundExecutor = executor;
        Intent intent = new Intent("android.intent.action.SCREEN_ON");
        this.mScreenOnIntent = intent;
        intent.addFlags(1344274432);
        Intent intent2 = new Intent("android.intent.action.SCREEN_OFF");
        this.mScreenOffIntent = intent2;
        intent2.addFlags(1344274432);
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setDeliveryGroupPolicy(1);
        makeBasic.setDeliveryGroupMatchingKey(
                UUID.randomUUID().toString(), "android.intent.action.SCREEN_ON");
        makeBasic.setDeferralPolicy(2);
        this.mScreenOnOffOptions = makeBasic.toBundle();
        this.mSuspendWhenScreenOffDueToProximityConfig = true;
        this.mShowWirelessChargingAnimationConfig =
                context.getResources().getBoolean(R.bool.config_smppsim_response_via_ims);
        this.mInjector = new RealInjector();
        this.mWakeLockLog = new WakeLockLog(new WakeLockLog.Injector(), context);
        try {
            iBatteryStats.noteInteractive(true);
        } catch (RemoteException unused) {
        }
        FrameworkStatsLog.write(33, 1);
        Intent intent3 = new Intent("android.intent.action.ACTION_SCREEN_ON_BY_PROXIMITY");
        this.mScreenOnByProximityIntent = intent3;
        intent3.addFlags(1342177280);
        Intent intent4 = new Intent("android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY");
        this.mScreenOffByProximityIntent = intent4;
        intent4.addFlags(1342177280);
        this.mInteractivityByGroupId.put(0, new Interactivity());
        this.mEdgeInternal =
                (EdgeManagerInternal) LocalServices.getService(EdgeManagerInternal.class);
    }

    public final void dump(PrintWriter printWriter) {
        WakeLockLog wakeLockLog = this.mWakeLockLog;
        if (wakeLockLog != null) {
            wakeLockLog.dump(printWriter, false);
        }
        WakefulnessSessionObserver wakefulnessSessionObserver = this.mWakefulnessSessionObserver;
        wakefulnessSessionObserver.getClass();
        printWriter.println();
        printWriter.println("Wakefulness Session Observer:");
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("default timeout: "),
                        wakefulnessSessionObserver.mScreenOffTimeoutMs,
                        printWriter,
                        "override timeout: ");
        m.append(wakefulnessSessionObserver.mOverrideTimeoutMs);
        printWriter.println(m.toString());
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < wakefulnessSessionObserver.mPowerGroups.size(); i++) {
            WakefulnessSessionObserver.WakefulnessSessionPowerGroup wakefulnessSessionPowerGroup =
                    (WakefulnessSessionObserver.WakefulnessSessionPowerGroup)
                            wakefulnessSessionObserver.mPowerGroups.valueAt(i);
            WakefulnessSessionObserver.this.mClock.getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            indentingPrintWriter.println(
                    "Wakefulness Session Power Group powerGroupId: "
                            + wakefulnessSessionPowerGroup.mPowerGroupId);
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(
                    "current wakefulness: " + wakefulnessSessionPowerGroup.mCurrentWakefulness);
            indentingPrintWriter.println(
                    "current user activity event: "
                            + wakefulnessSessionPowerGroup.mCurrentUserActivityEvent);
            indentingPrintWriter.println(
                    "current user activity duration: "
                            + (uptimeMillis
                                    - wakefulnessSessionPowerGroup.mCurrentUserActivityTimestamp));
            indentingPrintWriter.println(
                    "previous user activity event: "
                            + wakefulnessSessionPowerGroup.mPrevUserActivityEvent);
            indentingPrintWriter.println(
                    "previous user activity duration: "
                            + (uptimeMillis
                                    - wakefulnessSessionPowerGroup.mPrevUserActivityTimestamp));
            indentingPrintWriter.println(
                    "is in override timeout: "
                            + wakefulnessSessionPowerGroup.isInOverrideTimeout());
            indentingPrintWriter.decreaseIndent();
        }
        printWriter.println();
    }

    public final int getBatteryStatsWakeLockMonitorType(int i) {
        int i2 = i & GnssNative.GNSS_AIDING_TYPE_ALL;
        if (i2 == 1) {
            return 0;
        }
        if (i2 == 6 || i2 == 10) {
            return 1;
        }
        return i2 != 32
                ? i2 != 128 ? -1 : 18
                : this.mSuspendWhenScreenOffDueToProximityConfig ? -1 : 0;
    }

    public final void handleLateGlobalInteractiveChange() {
        synchronized (this.mLock) {
            try {
                long uptimeMillis = SystemClock.uptimeMillis();
                Interactivity interactivity = this.mGlobalInteractivity;
                final int i = (int) (uptimeMillis - interactivity.changeStartTime);
                if (interactivity.isInteractive) {
                    this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.power.Notifier$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Notifier notifier = Notifier.this;
                                    int i2 = i;
                                    VibrationEffect vibrationEffect =
                                            Notifier.CHARGING_VIBRATION_EFFECT;
                                    notifier.getClass();
                                    LogMaker logMaker = new LogMaker(198);
                                    logMaker.setType(1);
                                    Notifier.Interactivity interactivity2 =
                                            notifier.mGlobalInteractivity;
                                    logMaker.setSubtype(
                                            WindowManagerPolicyConstants
                                                    .translateWakeReasonToOnReason(
                                                            interactivity2.changeOnReason));
                                    logMaker.setLatency(i2);
                                    logMaker.addTaggedData(
                                            1694, Integer.valueOf(interactivity2.changeOnReason));
                                    MetricsLogger.action(logMaker);
                                    EventLog.writeEvent(2728, 1, 0, 0L, 0, Integer.valueOf(i2));
                                    int i3 = interactivity2.changeOnReason;
                                    PhoneWindowManagerExt phoneWindowManagerExt =
                                            ((PhoneWindowManager) notifier.mPolicy).mExt;
                                    Objects.requireNonNull(phoneWindowManagerExt);
                                    phoneWindowManagerExt.handleGlobalInteractiveIfNeeded(2, i3);
                                }
                            });
                } else {
                    if (this.mUserActivityPending) {
                        this.mUserActivityPending = false;
                        this.mHandler.removeMessages(1);
                    }
                    final int translateSleepReasonToOffReason =
                            WindowManagerPolicyConstants.translateSleepReasonToOffReason(
                                    this.mGlobalInteractivity.changeOffReason);
                    this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.power.Notifier$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Notifier notifier = Notifier.this;
                                    int i2 = translateSleepReasonToOffReason;
                                    int i3 = i;
                                    VibrationEffect vibrationEffect =
                                            Notifier.CHARGING_VIBRATION_EFFECT;
                                    notifier.getClass();
                                    LogMaker logMaker = new LogMaker(198);
                                    logMaker.setType(2);
                                    logMaker.setSubtype(i2);
                                    logMaker.setLatency(i3);
                                    logMaker.addTaggedData(
                                            1695,
                                            Integer.valueOf(
                                                    notifier.mGlobalInteractivity.changeOffReason));
                                    MetricsLogger.action(logMaker);
                                    EventLog.writeEvent(
                                            2728,
                                            0,
                                            Integer.valueOf(i2),
                                            0L,
                                            0,
                                            Integer.valueOf(i3));
                                    WindowManagerPolicy windowManagerPolicy = notifier.mPolicy;
                                    int i4 = notifier.mGlobalInteractivity.changeOffReason;
                                    PhoneWindowManager phoneWindowManager =
                                            (PhoneWindowManager) windowManagerPolicy;
                                    phoneWindowManager.mDeviceGoingToSleep = false;
                                    PhoneWindowManagerExt phoneWindowManagerExt =
                                            phoneWindowManager.mExt;
                                    Objects.requireNonNull(phoneWindowManagerExt);
                                    phoneWindowManagerExt.handleGlobalInteractiveIfNeeded(8, i4);
                                    notifier.mScreenOffIntent.putExtra("why", i2);
                                    notifier.mScreenOffIntent.putExtra(
                                            "reason",
                                            notifier.mGlobalInteractivity.changeOffReason);
                                }
                            });
                    this.mPendingInteractiveState = 2;
                    this.mPendingGoToSleepBroadcast = true;
                    updatePendingBroadcastLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void handleLateInteractiveChange(int i) {
        synchronized (this.mLock) {
            try {
                Interactivity interactivity = (Interactivity) this.mInteractivityByGroupId.get(i);
                if (interactivity == null) {
                    android.util.Slog.e(
                            "PowerManagerNotifier", "no Interactivity entry for groupId:" + i);
                } else {
                    if (interactivity.isInteractive) {
                        this.mHandler.post(
                                new Notifier$$ExternalSyntheticLambda3(this, i, interactivity, 0));
                    } else {
                        this.mHandler.post(
                                new Notifier$$ExternalSyntheticLambda3(this, i, interactivity, 1));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyWakeLockListener(
            final IWakeLockCallback iWakeLockCallback,
            final String str,
            final boolean z,
            final int i,
            final int i2) {
        if (iWakeLockCallback == null || !iWakeLockCallback.asBinder().pingBinder()) {
            return;
        }
        this.mInjector.getClass();
        final long currentTimeMillis = System.currentTimeMillis();
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.power.Notifier$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Notifier notifier = Notifier.this;
                        boolean z2 = z;
                        String str2 = str;
                        int i3 = i;
                        int i4 = i2;
                        long j = currentTimeMillis;
                        IWakeLockCallback iWakeLockCallback2 = iWakeLockCallback;
                        VibrationEffect vibrationEffect = Notifier.CHARGING_VIBRATION_EFFECT;
                        notifier.getClass();
                        try {
                            if (notifier.mFlags.mImproveWakelockLatency.isEnabled()) {
                                if (z2) {
                                    notifier.mWakeLockLog.onWakeLockEvent(1, i3, str2, i4, j);
                                } else {
                                    notifier.mWakeLockLog.onWakeLockEvent(2, i3, str2, 0, j);
                                }
                            }
                            iWakeLockCallback2.onStateChanged(z2);
                        } catch (RemoteException e) {
                            android.util.Slog.e(
                                    "PowerManagerNotifier",
                                    "Wakelock.mCallback [" + str2 + "] is already dead.",
                                    e);
                        }
                    }
                });
    }

    public final void onGlobalWakefulnessChangeStarted(final int i, int i2, long j) {
        boolean isInteractive = PowerManagerInternal.isInteractive(i);
        this.mHandler.post(
                new Runnable() { // from class: com.android.server.power.Notifier.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Notifier.this.mActivityManagerInternal.onWakefulnessChanged(i);
                    }
                });
        Interactivity interactivity = this.mGlobalInteractivity;
        if (interactivity.isInteractive != isInteractive) {
            if (interactivity.isChanging) {
                handleLateGlobalInteractiveChange();
            }
            this.mInputManagerInternal.setInteractive(isInteractive);
            this.mInputMethodManagerInternal.setInteractive(isInteractive);
            try {
                this.mBatteryStats.noteInteractive(isInteractive);
            } catch (RemoteException unused) {
            }
            FrameworkStatsLog.write(33, isInteractive ? 1 : 0);
            Interactivity interactivity2 = this.mGlobalInteractivity;
            interactivity2.isInteractive = isInteractive;
            interactivity2.isChanging = true;
            if (isInteractive) {
                interactivity2.changeOnReason = i2;
            } else {
                interactivity2.changeOffReason = i2;
            }
            interactivity2.changeStartTime = j;
            synchronized (this.mLock) {
                try {
                    if (this.mGlobalInteractivity.isInteractive) {
                        final int i3 = 0;
                        this.mHandler.post(
                                new Runnable(this) { // from class:
                                    // com.android.server.power.Notifier$$ExternalSyntheticLambda8
                                    public final /* synthetic */ Notifier f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i4 = i3;
                                        Notifier notifier = this.f$0;
                                        switch (i4) {
                                            case 0:
                                                notifier.mDisplayManagerInternal
                                                        .onEarlyInteractivityChange(true);
                                                Notifier.Interactivity interactivity3 =
                                                        notifier.mGlobalInteractivity;
                                                int i5 = interactivity3.changeOnReason;
                                                PhoneWindowManagerExt phoneWindowManagerExt =
                                                        ((PhoneWindowManager) notifier.mPolicy)
                                                                .mExt;
                                                Objects.requireNonNull(phoneWindowManagerExt);
                                                phoneWindowManagerExt
                                                        .handleGlobalInteractiveIfNeeded(1, i5);
                                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                                        new StringBuilder(
                                                                "handleEarlyGlobalInteractiveChange"
                                                                    + " reason = "),
                                                        interactivity3.changeOnReason,
                                                        "PowerManagerNotifier");
                                                break;
                                            default:
                                                notifier.mDisplayManagerInternal
                                                        .onEarlyInteractivityChange(false);
                                                WindowManagerPolicy windowManagerPolicy =
                                                        notifier.mPolicy;
                                                int i6 =
                                                        notifier.mGlobalInteractivity
                                                                .changeOffReason;
                                                PhoneWindowManager phoneWindowManager =
                                                        (PhoneWindowManager) windowManagerPolicy;
                                                phoneWindowManager.mDeviceGoingToSleep = true;
                                                PhoneWindowManagerExt phoneWindowManagerExt2 =
                                                        phoneWindowManager.mExt;
                                                Objects.requireNonNull(phoneWindowManagerExt2);
                                                phoneWindowManagerExt2
                                                        .handleGlobalInteractiveIfNeeded(4, i6);
                                                break;
                                        }
                                    }
                                });
                        this.mPendingInteractiveState = 1;
                        this.mPendingWakeUpBroadcast = true;
                        updatePendingBroadcastLocked();
                    } else {
                        final int i4 = 1;
                        this.mHandler.post(
                                new Runnable(this) { // from class:
                                    // com.android.server.power.Notifier$$ExternalSyntheticLambda8
                                    public final /* synthetic */ Notifier f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i42 = i4;
                                        Notifier notifier = this.f$0;
                                        switch (i42) {
                                            case 0:
                                                notifier.mDisplayManagerInternal
                                                        .onEarlyInteractivityChange(true);
                                                Notifier.Interactivity interactivity3 =
                                                        notifier.mGlobalInteractivity;
                                                int i5 = interactivity3.changeOnReason;
                                                PhoneWindowManagerExt phoneWindowManagerExt =
                                                        ((PhoneWindowManager) notifier.mPolicy)
                                                                .mExt;
                                                Objects.requireNonNull(phoneWindowManagerExt);
                                                phoneWindowManagerExt
                                                        .handleGlobalInteractiveIfNeeded(1, i5);
                                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                                        new StringBuilder(
                                                                "handleEarlyGlobalInteractiveChange"
                                                                    + " reason = "),
                                                        interactivity3.changeOnReason,
                                                        "PowerManagerNotifier");
                                                break;
                                            default:
                                                notifier.mDisplayManagerInternal
                                                        .onEarlyInteractivityChange(false);
                                                WindowManagerPolicy windowManagerPolicy =
                                                        notifier.mPolicy;
                                                int i6 =
                                                        notifier.mGlobalInteractivity
                                                                .changeOffReason;
                                                PhoneWindowManager phoneWindowManager =
                                                        (PhoneWindowManager) windowManagerPolicy;
                                                phoneWindowManager.mDeviceGoingToSleep = true;
                                                PhoneWindowManagerExt phoneWindowManagerExt2 =
                                                        phoneWindowManager.mExt;
                                                Objects.requireNonNull(phoneWindowManagerExt2);
                                                phoneWindowManagerExt2
                                                        .handleGlobalInteractiveIfNeeded(4, i6);
                                                break;
                                        }
                                    }
                                });
                    }
                } finally {
                }
            }
        }
    }

    public final void onGroupWakefulnessChangeStarted(int i, int i2, int i3, long j) {
        boolean z;
        int i4;
        int i5;
        WakefulnessSessionObserver wakefulnessSessionObserver;
        int i6;
        int i7;
        long j2;
        int i8;
        int i9;
        int i10;
        int i11;
        boolean isInteractive = PowerManagerInternal.isInteractive(i2);
        Interactivity interactivity = (Interactivity) this.mInteractivityByGroupId.get(i);
        if (interactivity == null) {
            interactivity = new Interactivity();
            this.mInteractivityByGroupId.put(i, interactivity);
            z = true;
        } else {
            z = false;
        }
        if (z || interactivity.isInteractive != isInteractive) {
            if (interactivity.isChanging) {
                handleLateInteractiveChange(i);
            }
            interactivity.isInteractive = isInteractive;
            if (isInteractive) {
                interactivity.changeOnReason = i3;
            } else {
                interactivity.changeOffReason = i3;
            }
            interactivity.changeStartTime = j;
            interactivity.isChanging = true;
            synchronized (this.mLock) {
                try {
                    Interactivity interactivity2 =
                            (Interactivity) this.mInteractivityByGroupId.get(i);
                    if (interactivity2 == null) {
                        android.util.Slog.e(
                                "PowerManagerNotifier", "no Interactivity entry for groupId:" + i);
                    } else {
                        if (i == 0 && interactivity2.isInteractive) {
                            android.util.Slog.d(
                                    "PowerManagerNotifier",
                                    "startedEarlyWakingUp: interactive: "
                                            + interactivity2.isInteractive
                                            + " reason: "
                                            + interactivity2.changeOnReason);
                            ((PhoneWindowManager) this.mPolicy)
                                    .startedEarlyWakingUp(interactivity2.changeOnReason);
                        }
                        if (interactivity2.isInteractive) {
                            this.mHandler.post(
                                    new Notifier$$ExternalSyntheticLambda3(
                                            this, i, interactivity2, 2));
                        } else {
                            this.mHandler.post(
                                    new Notifier$$ExternalSyntheticLambda3(
                                            this, i, interactivity2, 3));
                        }
                    }
                } finally {
                }
            }
            WakefulnessSessionObserver wakefulnessSessionObserver2 =
                    this.mWakefulnessSessionObserver;
            if (!wakefulnessSessionObserver2.mPowerGroups.contains(i)) {
                wakefulnessSessionObserver2.mPowerGroups.append(
                        i, wakefulnessSessionObserver2.new WakefulnessSessionPowerGroup(i));
            }
            WakefulnessSessionObserver.WakefulnessSessionPowerGroup wakefulnessSessionPowerGroup =
                    (WakefulnessSessionObserver.WakefulnessSessionPowerGroup)
                            wakefulnessSessionObserver2.mPowerGroups.get(i);
            wakefulnessSessionPowerGroup.mCurrentWakefulness = i2;
            if (wakefulnessSessionPowerGroup.mIsInteractive
                    == PowerManagerInternal.isInteractive(i2)) {
                return;
            }
            boolean isInteractive2 = PowerManagerInternal.isInteractive(i2);
            wakefulnessSessionPowerGroup.mIsInteractive = isInteractive2;
            int i12 = wakefulnessSessionPowerGroup.mPowerGroupId;
            WakefulnessSessionObserver wakefulnessSessionObserver3 =
                    WakefulnessSessionObserver.this;
            if (isInteractive2) {
                wakefulnessSessionPowerGroup.mInteractiveStateOnStartTimestamp = j;
                long j3 = wakefulnessSessionPowerGroup.mTimeoutOffTimestamp;
                if (j3 != -1) {
                    if (j - j3 < 5000) {
                        WakefulnessSessionObserver.WakefulnessSessionFrameworkStatsLogger
                                wakefulnessSessionFrameworkStatsLogger =
                                        wakefulnessSessionObserver3
                                                .mWakefulnessSessionFrameworkStatsLogger;
                        int m827$$Nest$mgetScreenOffTimeout =
                                WakefulnessSessionObserver.m827$$Nest$mgetScreenOffTimeout(
                                        wakefulnessSessionObserver3);
                        int i13 = wakefulnessSessionObserver3.mOverrideTimeoutMs;
                        wakefulnessSessionFrameworkStatsLogger.getClass();
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.SCREEN_TIMEOUT_OVERRIDE_REPORTED,
                                i12,
                                2,
                                i13,
                                m827$$Nest$mgetScreenOffTimeout);
                        wakefulnessSessionPowerGroup.mSendOverrideTimeoutLogTimestamp = j;
                    }
                    wakefulnessSessionPowerGroup.mTimeoutOffTimestamp = -1L;
                    return;
                }
                return;
            }
            int i14 = wakefulnessSessionPowerGroup.mCurrentUserActivityEvent;
            long j4 = j - wakefulnessSessionPowerGroup.mCurrentUserActivityTimestamp;
            if (i3 == 4) {
                i14 = wakefulnessSessionPowerGroup.mPrevUserActivityEvent;
                long j5 = j - wakefulnessSessionPowerGroup.mPrevUserActivityTimestamp;
                if (wakefulnessSessionPowerGroup.isInOverrideTimeout()
                        || wakefulnessSessionPowerGroup.mTimeoutOverrideReleaseReason == 5) {
                    WakefulnessSessionObserver.WakefulnessSessionFrameworkStatsLogger
                            wakefulnessSessionFrameworkStatsLogger2 =
                                    wakefulnessSessionObserver3
                                            .mWakefulnessSessionFrameworkStatsLogger;
                    int m827$$Nest$mgetScreenOffTimeout2 =
                            WakefulnessSessionObserver.m827$$Nest$mgetScreenOffTimeout(
                                    wakefulnessSessionObserver3);
                    int i15 = wakefulnessSessionObserver3.mOverrideTimeoutMs;
                    wakefulnessSessionFrameworkStatsLogger2.getClass();
                    i4 = 4;
                    i5 = 5;
                    FrameworkStatsLog.write(
                            FrameworkStatsLog.SCREEN_TIMEOUT_OVERRIDE_REPORTED,
                            i12,
                            5,
                            i15,
                            m827$$Nest$mgetScreenOffTimeout2);
                    wakefulnessSessionPowerGroup.mSendOverrideTimeoutLogTimestamp = j;
                    wakefulnessSessionPowerGroup.mTimeoutOverrideReleaseReason = -1;
                } else {
                    i4 = 4;
                    i5 = 5;
                }
                wakefulnessSessionObserver = wakefulnessSessionObserver3;
                i9 = 2;
                i6 = 2;
                j2 = j5;
                i8 = 0;
                i7 = 1;
            } else {
                i4 = 4;
                i5 = 5;
                if (i3 != 2) {
                    wakefulnessSessionObserver = wakefulnessSessionObserver3;
                    i6 = 2;
                    i7 = 1;
                    j2 = j4;
                    i8 = 0;
                    i9 = 0;
                } else if (wakefulnessSessionPowerGroup.isInOverrideTimeout()) {
                    int m827$$Nest$mgetScreenOffTimeout3 =
                            WakefulnessSessionObserver.m827$$Nest$mgetScreenOffTimeout(
                                    wakefulnessSessionObserver3);
                    int i16 = wakefulnessSessionObserver3.mOverrideTimeoutMs;
                    i8 = m827$$Nest$mgetScreenOffTimeout3 - i16;
                    WakefulnessSessionObserver.WakefulnessSessionFrameworkStatsLogger
                            wakefulnessSessionFrameworkStatsLogger3 =
                                    wakefulnessSessionObserver3
                                            .mWakefulnessSessionFrameworkStatsLogger;
                    int m827$$Nest$mgetScreenOffTimeout4 =
                            WakefulnessSessionObserver.m827$$Nest$mgetScreenOffTimeout(
                                    wakefulnessSessionObserver3);
                    wakefulnessSessionFrameworkStatsLogger3.getClass();
                    i7 = 1;
                    wakefulnessSessionObserver = wakefulnessSessionObserver3;
                    i6 = 2;
                    FrameworkStatsLog.write(
                            FrameworkStatsLog.SCREEN_TIMEOUT_OVERRIDE_REPORTED,
                            i12,
                            1,
                            i16,
                            m827$$Nest$mgetScreenOffTimeout4);
                    wakefulnessSessionPowerGroup.mSendOverrideTimeoutLogTimestamp = j;
                    wakefulnessSessionPowerGroup.mTimeoutOffTimestamp = j;
                    i9 = 1;
                    j2 = j4;
                } else {
                    wakefulnessSessionObserver = wakefulnessSessionObserver3;
                    i6 = 2;
                    i7 = 1;
                    i9 = 1;
                    j2 = j4;
                    i8 = 0;
                }
            }
            long j6 = j - wakefulnessSessionPowerGroup.mInteractiveStateOnStartTimestamp;
            wakefulnessSessionObserver.mWakefulnessSessionFrameworkStatsLogger.getClass();
            switch (i14) {
                case 1:
                    i10 = i7;
                    break;
                case 2:
                    i10 = i6;
                    break;
                case 3:
                    i11 = 3;
                    i10 = i11;
                    break;
                case 4:
                    i10 = i4;
                    break;
                case 5:
                    i10 = i5;
                    break;
                case 6:
                    i11 = 6;
                    i10 = i11;
                    break;
                default:
                    i10 = 0;
                    break;
            }
            FrameworkStatsLog.write(
                    FrameworkStatsLog.SCREEN_INTERACTIVE_SESSION_REPORTED,
                    wakefulnessSessionPowerGroup.mPowerGroupId,
                    i9,
                    j6,
                    i10,
                    j2,
                    i8);
        }
    }

    public final void onScreenStateChangeStartedByProximity(final boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mProximity != z) {
                    this.mHandler.post(
                            new Runnable() { // from class: com.android.server.power.Notifier.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                            "PowerManagerNotifier",
                                            new StringBuilder(
                                                    "onScreenStateChangeStartedByProximity : "),
                                            z);
                                    Notifier notifier = Notifier.this;
                                    notifier.mContext.sendBroadcastAsUser(
                                            z
                                                    ? notifier.mScreenOffByProximityIntent
                                                    : notifier.mScreenOnByProximityIntent,
                                            UserHandle.ALL);
                                }
                            });
                    this.mProximity = z;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onUserActivity(int i, int i2, int i3) {
        try {
            this.mBatteryStats.noteUserActivity(i3, i2);
            this.mWakefulnessSessionObserver.notifyUserActivity(i, i2, SystemClock.uptimeMillis());
        } catch (RemoteException unused) {
        }
        synchronized (this.mLock) {
            try {
                if (!this.mUserActivityPending) {
                    this.mUserActivityPending = true;
                    Message obtainMessage = this.mHandler.obtainMessage(1);
                    obtainMessage.arg1 = i;
                    obtainMessage.arg2 = i2;
                    obtainMessage.setAsynchronous(true);
                    this.mHandler.sendMessage(obtainMessage);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onWakeLockAcquired(
            int i,
            int i2,
            int i3,
            IWakeLockCallback iWakeLockCallback,
            WorkSource workSource,
            String str,
            String str2,
            String str3) {
        notifyWakeLockListener(iWakeLockCallback, str, true, i2, i);
        int batteryStatsWakeLockMonitorType = getBatteryStatsWakeLockMonitorType(i);
        if (batteryStatsWakeLockMonitorType >= 0) {
            boolean z = i2 == 1000 && (i & 1073741824) != 0;
            try {
                if (workSource != null) {
                    this.mBatteryStats.noteStartWakelockFromSource(
                            workSource, i3, str, str3, batteryStatsWakeLockMonitorType, z);
                } else {
                    this.mBatteryStats.noteStartWakelock(
                            i2, i3, str, str3, batteryStatsWakeLockMonitorType, z);
                    this.mAppOps.startOpNoThrow(40, i2, str2);
                }
            } catch (RemoteException unused) {
            }
        }
        if (!this.mFlags.mImproveWakelockLatency.isEnabled()) {
            this.mWakeLockLog.onWakeLockEvent(1, i2, str, i, -1L);
        }
        WakefulnessSessionObserver wakefulnessSessionObserver = this.mWakefulnessSessionObserver;
        if ((i & GnssNative.GNSS_AIDING_TYPE_ALL) != 256) {
            wakefulnessSessionObserver.getClass();
            return;
        }
        for (int i4 = 0; i4 < wakefulnessSessionObserver.mPowerGroups.size(); i4++) {
            WakefulnessSessionObserver.WakefulnessSessionPowerGroup wakefulnessSessionPowerGroup =
                    (WakefulnessSessionObserver.WakefulnessSessionPowerGroup)
                            wakefulnessSessionObserver.mPowerGroups.valueAt(i4);
            synchronized (WakefulnessSessionObserver.this.mLock) {
                wakefulnessSessionPowerGroup.mTimeoutOverrideWakeLockCounter++;
            }
        }
    }

    public final void onWakeLockReleased(
            int i,
            String str,
            String str2,
            int i2,
            int i3,
            WorkSource workSource,
            String str3,
            IWakeLockCallback iWakeLockCallback,
            int i4) {
        int i5;
        EdgeManagerInternal edgeManagerInternal;
        int i6;
        notifyWakeLockListener(iWakeLockCallback, str, false, i2, i);
        int batteryStatsWakeLockMonitorType = getBatteryStatsWakeLockMonitorType(i);
        if (batteryStatsWakeLockMonitorType >= 0) {
            try {
                if (workSource != null) {
                    this.mBatteryStats.noteStopWakelockFromSource(
                            workSource, i3, str, str3, batteryStatsWakeLockMonitorType);
                } else {
                    this.mBatteryStats.noteStopWakelock(
                            i2, i3, str, str3, batteryStatsWakeLockMonitorType);
                    this.mAppOps.finishOp(40, i2, str2);
                }
            } catch (RemoteException unused) {
            }
        }
        if (this.mFlags.mImproveWakelockLatency.isEnabled()) {
            i5 = i4;
        } else {
            i5 = i4;
            this.mWakeLockLog.onWakeLockEvent(2, i2, str, 0, -1L);
        }
        WakefulnessSessionObserver wakefulnessSessionObserver = this.mWakefulnessSessionObserver;
        int i7 = i & GnssNative.GNSS_AIDING_TYPE_ALL;
        if (i7 == 256) {
            for (int i8 = 0; i8 < wakefulnessSessionObserver.mPowerGroups.size(); i8++) {
                WakefulnessSessionObserver.WakefulnessSessionPowerGroup
                        wakefulnessSessionPowerGroup =
                                (WakefulnessSessionObserver.WakefulnessSessionPowerGroup)
                                        wakefulnessSessionObserver.mPowerGroups.valueAt(i8);
                synchronized (WakefulnessSessionObserver.this.mLock) {
                    wakefulnessSessionPowerGroup.mTimeoutOverrideWakeLockCounter--;
                }
                if (!wakefulnessSessionPowerGroup.isInOverrideTimeout()) {
                    wakefulnessSessionPowerGroup.mTimeoutOverrideReleaseReason = i5;
                    WakefulnessSessionObserver.this.mClock.getClass();
                    if (SystemClock.uptimeMillis()
                                    - wakefulnessSessionPowerGroup.mSendOverrideTimeoutLogTimestamp
                            >= 1000) {
                        switch (i5) {
                            case 1:
                            case 2:
                                i6 = 6;
                                break;
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                                i6 = 4;
                                break;
                            default:
                                i6 = 0;
                                break;
                        }
                        WakefulnessSessionObserver wakefulnessSessionObserver2 =
                                WakefulnessSessionObserver.this;
                        WakefulnessSessionObserver.WakefulnessSessionFrameworkStatsLogger
                                wakefulnessSessionFrameworkStatsLogger =
                                        wakefulnessSessionObserver2
                                                .mWakefulnessSessionFrameworkStatsLogger;
                        int i9 = wakefulnessSessionPowerGroup.mPowerGroupId;
                        int i10 = wakefulnessSessionObserver2.mOverrideTimeoutMs;
                        int m827$$Nest$mgetScreenOffTimeout =
                                WakefulnessSessionObserver.m827$$Nest$mgetScreenOffTimeout(
                                        wakefulnessSessionObserver2);
                        wakefulnessSessionFrameworkStatsLogger.getClass();
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.SCREEN_TIMEOUT_OVERRIDE_REPORTED,
                                i9,
                                i6,
                                i10,
                                m827$$Nest$mgetScreenOffTimeout);
                    }
                }
            }
        } else {
            wakefulnessSessionObserver.getClass();
        }
        if ((i7 == 6 || i7 == 10 || i7 == 26)
                && (edgeManagerInternal = this.mEdgeInternal) != null) {
            edgeManagerInternal.hideForWakeLock(str2, i2);
        }
    }

    public final void playChargingStartedFeedback(final int i, final boolean z) {
        boolean z2 =
                Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(), "charging_sounds_enabled", 1, i)
                        != 0;
        boolean z3 = Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode", 1) == 0;
        if (z2 && z3 && this.mIsPlayingChargingStartedFeedback.compareAndSet(false, true)) {
            this.mBackgroundExecutor.execute(
                    new Runnable() { // from class:
                        // com.android.server.power.Notifier$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            Ringtone ringtone;
                            Notifier notifier = Notifier.this;
                            int i2 = i;
                            boolean z4 = z;
                            if (Settings.Secure.getIntForUser(
                                            notifier.mContext.getContentResolver(),
                                            "charging_vibration_enabled",
                                            1,
                                            i2)
                                    != 0) {
                                notifier.mVibrator.vibrate(
                                        1000,
                                        notifier.mContext.getOpPackageName(),
                                        Notifier.CHARGING_VIBRATION_EFFECT,
                                        "Charging started",
                                        Notifier.HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES);
                            }
                            Uri parse =
                                    Uri.parse(
                                            "file://"
                                                    + Settings.Global.getString(
                                                            notifier.mContext.getContentResolver(),
                                                            z4
                                                                    ? "wireless_charging_started_sound"
                                                                    : "charging_started_sound"));
                            if (parse != null
                                    && (ringtone =
                                                    RingtoneManager.getRingtone(
                                                            notifier.mContext, parse))
                                            != null) {
                                ringtone.setStreamType(1);
                                ringtone.play();
                            }
                            notifier.mIsPlayingChargingStartedFeedback.set(false);
                        }
                    });
        }
    }

    public final void sendNextBroadcast() {
        synchronized (this.mLock) {
            try {
                int i = this.mBroadcastedInteractiveState;
                if (i == 0) {
                    if (this.mPendingInteractiveState != 2) {
                        this.mPendingWakeUpBroadcast = false;
                        this.mBroadcastedInteractiveState = 1;
                    } else {
                        this.mPendingGoToSleepBroadcast = false;
                        this.mBroadcastedInteractiveState = 2;
                    }
                } else if (i == 1) {
                    if (!this.mPendingWakeUpBroadcast
                            && !this.mPendingGoToSleepBroadcast
                            && this.mPendingInteractiveState != 2) {
                        this.mBroadcastInProgress = false;
                        this.mSuspendBlocker.release();
                        return;
                    }
                    this.mPendingGoToSleepBroadcast = false;
                    this.mBroadcastedInteractiveState = 2;
                } else {
                    if (!this.mPendingWakeUpBroadcast
                            && !this.mPendingGoToSleepBroadcast
                            && this.mPendingInteractiveState != 1) {
                        this.mBroadcastInProgress = false;
                        this.mSuspendBlocker.release();
                        return;
                    }
                    this.mPendingWakeUpBroadcast = false;
                    this.mBroadcastedInteractiveState = 1;
                }
                this.mBroadcastStartTime = SystemClock.uptimeMillis();
                int i2 = this.mBroadcastedInteractiveState;
                EventLog.writeEvent(2725, 1);
                if (i2 != 1) {
                    if (this.mActivityManagerInternal.isSystemReady()) {
                        this.mActivityManagerInternal.broadcastIntentWithCallback(
                                this.mScreenOffIntent,
                                this.mGoToSleepBroadcastDone,
                                (String[]) null,
                                -1,
                                (int[]) null,
                                (BiFunction) null,
                                this.mScreenOnOffOptions);
                        return;
                    } else {
                        EventLog.writeEvent(2727, 3, 1);
                        sendNextBroadcast();
                        return;
                    }
                }
                if (this.mActivityManagerInternal.isSystemReady()) {
                    this.mScreenOnIntent.putExtra("why", this.mGlobalInteractivity.changeOnReason);
                    this.mActivityManagerInternal.broadcastIntentWithCallback(
                            this.mScreenOnIntent,
                            this.mWakeUpBroadcastDone,
                            (String[]) null,
                            -1,
                            (int[]) null,
                            (BiFunction) null,
                            this.mScreenOnOffOptions);
                } else {
                    EventLog.writeEvent(2727, 2, 1);
                    sendNextBroadcast();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updatePendingBroadcastLocked() {
        int i;
        if (this.mBroadcastInProgress || (i = this.mPendingInteractiveState) == 0) {
            return;
        }
        if (this.mPendingWakeUpBroadcast
                || this.mPendingGoToSleepBroadcast
                || i != this.mBroadcastedInteractiveState) {
            this.mBroadcastInProgress = true;
            this.mSuspendBlocker.acquire("unknown");
            NotifierHandler notifierHandler = this.mHandler;
            Message obtainMessage = notifierHandler.obtainMessage(2);
            obtainMessage.setAsynchronous(true);
            notifierHandler.sendMessage(obtainMessage);
        }
    }
}
