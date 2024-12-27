package com.android.systemui.power;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Temperature;
import android.provider.Settings;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.Log;
import android.util.Slog;
import android.view.WindowManager;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.settingslib.fuelgauge.Estimate;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.sec.ims.IMSParameter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PowerUI implements CoreStartable, ConfigurationController.ConfigurationListener, CommandQueue.Callbacks {
    public static final boolean DEBUG = Log.isLoggable("PowerUI", 3);
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    BatteryStateSnapshot mCurrentBatteryStateSnapshot;
    public boolean mEnableSkinTemperatureWarning;
    public boolean mEnableUsbTemperatureAlarm;
    public final EnhancedEstimates mEnhancedEstimates;
    public boolean mInVrMode;
    BatteryStateSnapshot mLastBatteryStateSnapshot;
    public int mLowBatteryAlertCloseLevel;
    boolean mLowWarningShownThisChargeCycle;
    public InattentiveSleepWarningView mOverlayView;
    public final PowerManager mPowerManager;
    boolean mSevereWarningShownThisChargeCycle;
    public IThermalEventListener mSkinThermalEventListener;
    IThermalService mThermalService;
    public IThermalEventListener mUsbThermalEventListener;
    public final UserTracker mUserTracker;
    public final IVrManager mVrManager;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final Handler mHandler = new Handler();
    final Receiver mReceiver = new Receiver();
    public final Configuration mLastConfiguration = new Configuration();
    public int mPlugType = 0;
    public int mInvalidCharger = 0;
    public final int[] mLowBatteryReminderLevels = new int[2];
    public long mScreenOffTime = -1;
    int mBatteryLevel = 100;
    int mBatteryStatus = 1;
    public final AnonymousClass1 mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.power.PowerUI.1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            PowerUI.this.mScreenOffTime = SystemClock.elapsedRealtime();
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            PowerUI.this.mScreenOffTime = -1L;
        }
    };
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.power.PowerUI.2
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            boolean z = PowerUI.DEBUG;
            PowerUI.this.getClass();
            throw null;
        }
    };
    public final AnonymousClass3 mVrStateCallbacks = new IVrStateCallbacks.Stub() { // from class: com.android.systemui.power.PowerUI.3
        public final void onVrStateChanged(boolean z) {
            PowerUI.this.mInVrMode = z;
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class Receiver extends BroadcastReceiver {
        public boolean mHasReceivedBattery = false;

        public Receiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.os.action.POWER_SAVE_MODE_CHANGED".equals(action)) {
                ThreadUtils.postOnBackgroundThread(new PowerUI$$ExternalSyntheticLambda0(this, 1));
                return;
            }
            if (!"android.intent.action.BATTERY_CHANGED".equals(action)) {
                Slog.w("PowerUI", "unknown intent: " + intent);
                return;
            }
            this.mHasReceivedBattery = true;
            PowerUI powerUI = PowerUI.this;
            int i = powerUI.mBatteryLevel;
            powerUI.mBatteryLevel = intent.getIntExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, 100);
            PowerUI powerUI2 = PowerUI.this;
            int i2 = powerUI2.mBatteryStatus;
            powerUI2.mBatteryStatus = intent.getIntExtra(IMSParameter.CALL.STATUS, 1);
            PowerUI powerUI3 = PowerUI.this;
            int i3 = powerUI3.mPlugType;
            powerUI3.mPlugType = intent.getIntExtra("plugged", 1);
            PowerUI powerUI4 = PowerUI.this;
            int i4 = powerUI4.mInvalidCharger;
            powerUI4.mInvalidCharger = intent.getIntExtra("invalid_charger", 0);
            PowerUI powerUI5 = PowerUI.this;
            powerUI5.mLastBatteryStateSnapshot = powerUI5.mCurrentBatteryStateSnapshot;
            boolean z = powerUI5.mPlugType != 0;
            boolean z2 = i3 != 0;
            int findBatteryLevelBucket = powerUI5.findBatteryLevelBucket(i);
            PowerUI powerUI6 = PowerUI.this;
            int findBatteryLevelBucket2 = powerUI6.findBatteryLevelBucket(powerUI6.mBatteryLevel);
            if (PowerUI.DEBUG) {
                Slog.d("PowerUI", "buckets   ....." + PowerUI.this.mLowBatteryAlertCloseLevel + " .. " + PowerUI.this.mLowBatteryReminderLevels[0] + " .. " + PowerUI.this.mLowBatteryReminderLevels[1]);
                StringBuilder sb = new StringBuilder("level          ");
                sb.append(i);
                sb.append(" --> ");
                sb.append(PowerUI.this.mBatteryLevel);
                Slog.d("PowerUI", sb.toString());
                Slog.d("PowerUI", "status         " + i2 + " --> " + PowerUI.this.mBatteryStatus);
                Slog.d("PowerUI", "plugType       " + i3 + " --> " + PowerUI.this.mPlugType);
                Slog.d("PowerUI", "invalidCharger " + i4 + " --> " + PowerUI.this.mInvalidCharger);
                Slog.d("PowerUI", "bucket         " + findBatteryLevelBucket + " --> " + findBatteryLevelBucket2);
                Slog.d("PowerUI", "plugged        " + z2 + " --> " + z);
            }
            PowerUI.this.getClass();
            PowerUI powerUI7 = PowerUI.this;
            int i5 = powerUI7.mBatteryLevel;
            long j = powerUI7.mScreenOffTime;
            throw null;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class SkinThermalEventListener extends IThermalEventListener.Stub {
        public SkinThermalEventListener() {
        }

        public final void notifyThrottling(Temperature temperature) {
            if (temperature.getStatus() < 5) {
                PowerUI powerUI = PowerUI.this;
                boolean z = PowerUI.DEBUG;
                powerUI.getClass();
                throw null;
            }
            PowerUI powerUI2 = PowerUI.this;
            if (powerUI2.mInVrMode) {
                return;
            }
            powerUI2.getClass();
            throw null;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class UsbThermalEventListener extends IThermalEventListener.Stub {
        public UsbThermalEventListener() {
        }

        public final void notifyThrottling(Temperature temperature) {
            if (temperature.getStatus() < 5) {
                return;
            }
            PowerUI powerUI = PowerUI.this;
            boolean z = PowerUI.DEBUG;
            powerUI.getClass();
            throw null;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface WarningsUI {
    }

    /* JADX WARN: Type inference failed for: r7v11, types: [com.android.systemui.power.PowerUI$3] */
    /* JADX WARN: Type inference failed for: r7v9, types: [com.android.systemui.power.PowerUI$1] */
    public PowerUI(Context context, BroadcastDispatcher broadcastDispatcher, CommandQueue commandQueue, IVrManager iVrManager, WarningsUI warningsUI, EnhancedEstimates enhancedEstimates, WakefulnessLifecycle wakefulnessLifecycle, PowerManager powerManager, UserTracker userTracker) {
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mCommandQueue = commandQueue;
        this.mVrManager = iVrManager;
        this.mEnhancedEstimates = enhancedEstimates;
        this.mPowerManager = powerManager;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mUserTracker = userTracker;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void dismissInattentiveSleepWarning(boolean z) {
        InattentiveSleepWarningView inattentiveSleepWarningView = this.mOverlayView;
        if (inattentiveSleepWarningView == null || inattentiveSleepWarningView.getParent() == null) {
            return;
        }
        inattentiveSleepWarningView.mDismissing = true;
        if (!z) {
            inattentiveSleepWarningView.setVisibility(4);
            inattentiveSleepWarningView.mWindowManager.removeView(inattentiveSleepWarningView);
        } else {
            final Animator animator = inattentiveSleepWarningView.mFadeOutAnimator;
            Objects.requireNonNull(animator);
            inattentiveSleepWarningView.postOnAnimation(new Runnable() { // from class: com.android.systemui.power.InattentiveSleepWarningView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    animator.start();
                }
            });
        }
    }

    public synchronized void doSkinThermalEventListenerRegistration() {
        try {
            boolean z = this.mEnableSkinTemperatureWarning;
            boolean z2 = false;
            boolean z3 = Settings.Global.getInt(this.mContext.getContentResolver(), "show_temperature_warning", this.mContext.getResources().getInteger(R.integer.config_showTemperatureWarning)) != 0;
            this.mEnableSkinTemperatureWarning = z3;
            if (z3 != z) {
                try {
                    if (this.mSkinThermalEventListener == null) {
                        this.mSkinThermalEventListener = new SkinThermalEventListener();
                    }
                    if (this.mThermalService == null) {
                        this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
                    }
                    z2 = this.mEnableSkinTemperatureWarning ? this.mThermalService.registerThermalEventListenerWithType(this.mSkinThermalEventListener, 3) : this.mThermalService.unregisterThermalEventListener(this.mSkinThermalEventListener);
                } catch (RemoteException e) {
                    Slog.e("PowerUI", "Exception while (un)registering skin thermal event listener.", e);
                }
                if (!z2) {
                    this.mEnableSkinTemperatureWarning = !this.mEnableSkinTemperatureWarning;
                    Slog.e("PowerUI", "Failed to register or unregister skin thermal event listener.");
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void doUsbThermalEventListenerRegistration() {
        try {
            boolean z = this.mEnableUsbTemperatureAlarm;
            boolean z2 = false;
            boolean z3 = Settings.Global.getInt(this.mContext.getContentResolver(), "show_usb_temperature_alarm", this.mContext.getResources().getInteger(R.integer.config_showUsbPortAlarm)) != 0;
            this.mEnableUsbTemperatureAlarm = z3;
            if (z3 != z) {
                try {
                    if (this.mUsbThermalEventListener == null) {
                        this.mUsbThermalEventListener = new UsbThermalEventListener();
                    }
                    if (this.mThermalService == null) {
                        this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
                    }
                    z2 = this.mEnableUsbTemperatureAlarm ? this.mThermalService.registerThermalEventListenerWithType(this.mUsbThermalEventListener, 4) : this.mThermalService.unregisterThermalEventListener(this.mUsbThermalEventListener);
                } catch (RemoteException e) {
                    Slog.e("PowerUI", "Exception while (un)registering usb thermal event listener.", e);
                }
                if (!z2) {
                    this.mEnableUsbTemperatureAlarm = !this.mEnableUsbTemperatureAlarm;
                    Slog.e("PowerUI", "Failed to register or unregister usb thermal event listener.");
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("mLowBatteryAlertCloseLevel=");
        printWriter.println(this.mLowBatteryAlertCloseLevel);
        printWriter.print("mLowBatteryReminderLevels=");
        printWriter.println(Arrays.toString(this.mLowBatteryReminderLevels));
        printWriter.print("mBatteryLevel=");
        printWriter.println(Integer.toString(this.mBatteryLevel));
        printWriter.print("mBatteryStatus=");
        printWriter.println(Integer.toString(this.mBatteryStatus));
        printWriter.print("mPlugType=");
        printWriter.println(Integer.toString(this.mPlugType));
        printWriter.print("mInvalidCharger=");
        printWriter.println(Integer.toString(this.mInvalidCharger));
        printWriter.print("mScreenOffTime=");
        printWriter.print(this.mScreenOffTime);
        if (this.mScreenOffTime >= 0) {
            printWriter.print(" (");
            printWriter.print(SystemClock.elapsedRealtime() - this.mScreenOffTime);
            printWriter.print(" ago)");
        }
        printWriter.println();
        printWriter.print("soundTimeout=");
        printWriter.println(Settings.Global.getInt(this.mContext.getContentResolver(), "low_battery_sound_timeout", 0));
        printWriter.print("bucket: ");
        printWriter.println(Integer.toString(findBatteryLevelBucket(this.mBatteryLevel)));
        printWriter.print("mEnableSkinTemperatureWarning=");
        printWriter.println(this.mEnableSkinTemperatureWarning);
        printWriter.print("mEnableUsbTemperatureAlarm=");
        printWriter.println(this.mEnableUsbTemperatureAlarm);
        throw null;
    }

    public final int findBatteryLevelBucket(int i) {
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

    public void maybeShowHybridWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        int i = batteryStateSnapshot.batteryLevel;
        boolean z = DEBUG;
        if (i >= 30) {
            this.mLowWarningShownThisChargeCycle = false;
            this.mSevereWarningShownThisChargeCycle = false;
            if (z) {
                Slog.d("PowerUI", "Charge cycle reset! Can show warnings again");
            }
        }
        if (batteryStateSnapshot.bucket == batteryStateSnapshot2.bucket) {
            boolean z2 = batteryStateSnapshot2.plugged;
        }
        if (shouldShowHybridWarning(batteryStateSnapshot)) {
            throw null;
        }
        if (shouldDismissHybridWarning(batteryStateSnapshot)) {
            if (!z) {
                throw null;
            }
            Slog.d("PowerUI", "Dismissing warning");
            throw null;
        }
        if (!z) {
            throw null;
        }
        Slog.d("PowerUI", "Updating warning");
        throw null;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        if ((this.mLastConfiguration.updateFrom(configuration) & 3) != 0) {
            this.mHandler.post(new PowerUI$$ExternalSyntheticLambda0(this, 0));
        }
    }

    public Estimate refreshEstimateIfNeeded() {
        BatteryStateSnapshot batteryStateSnapshot = this.mLastBatteryStateSnapshot;
        if (batteryStateSnapshot != null && batteryStateSnapshot.timeRemainingMillis != -1 && this.mBatteryLevel == batteryStateSnapshot.batteryLevel) {
            BatteryStateSnapshot batteryStateSnapshot2 = this.mLastBatteryStateSnapshot;
            return new Estimate(batteryStateSnapshot2.timeRemainingMillis, batteryStateSnapshot2.isBasedOnUsage, batteryStateSnapshot2.averageTimeToDischargeMillis);
        }
        ((EnhancedEstimatesImpl) this.mEnhancedEstimates).getClass();
        Estimate estimate = new Estimate(-1L, false, -1L);
        if (DEBUG) {
            Slog.d("PowerUI", "updated estimate: " + estimate.estimateMillis);
        }
        return estimate;
    }

    public boolean shouldDismissHybridWarning(BatteryStateSnapshot batteryStateSnapshot) {
        return batteryStateSnapshot.plugged || batteryStateSnapshot.batteryLevel > batteryStateSnapshot.lowLevelThreshold;
    }

    public boolean shouldDismissLowBatteryWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        if (!batteryStateSnapshot.isPowerSaver && !batteryStateSnapshot.plugged) {
            int i = batteryStateSnapshot2.bucket;
            int i2 = batteryStateSnapshot.bucket;
            if (i2 <= i || i2 <= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean shouldShowHybridWarning(BatteryStateSnapshot batteryStateSnapshot) {
        boolean z = batteryStateSnapshot.plugged;
        int i = batteryStateSnapshot.batteryStatus;
        if (z || i == 1) {
            StringBuilder sb = new StringBuilder("can't show warning due to - plugged: ");
            sb.append(batteryStateSnapshot.plugged);
            sb.append(" status unknown: ");
            sb.append(i == 1);
            Slog.d("PowerUI", sb.toString());
            return false;
        }
        boolean z2 = this.mLowWarningShownThisChargeCycle;
        int i2 = batteryStateSnapshot.batteryLevel;
        boolean z3 = (!z2 && !batteryStateSnapshot.isPowerSaver && i2 <= batteryStateSnapshot.lowLevelThreshold) || (!this.mSevereWarningShownThisChargeCycle && i2 <= batteryStateSnapshot.severeLevelThreshold);
        if (DEBUG) {
            StringBuilder m = RowView$$ExternalSyntheticOutline0.m("Enhanced trigger is: ", "\nwith battery snapshot: mLowWarningShownThisChargeCycle: ", z3);
            m.append(this.mLowWarningShownThisChargeCycle);
            m.append(" mSevereWarningShownThisChargeCycle: ");
            m.append(this.mSevereWarningShownThisChargeCycle);
            m.append("\n");
            m.append(batteryStateSnapshot.toString());
            Slog.d("PowerUI", m.toString());
        }
        return z3;
    }

    public boolean shouldShowLowBatteryWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        if (!batteryStateSnapshot.plugged && !batteryStateSnapshot.isPowerSaver) {
            int i = batteryStateSnapshot2.bucket;
            int i2 = batteryStateSnapshot.bucket;
            if ((i2 < i || batteryStateSnapshot2.plugged) && i2 < 0 && batteryStateSnapshot.batteryStatus != 1) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showInattentiveSleepWarning() {
        if (this.mOverlayView == null) {
            this.mOverlayView = new InattentiveSleepWarningView(this.mContext);
        }
        InattentiveSleepWarningView inattentiveSleepWarningView = this.mOverlayView;
        if (inattentiveSleepWarningView.getParent() != null) {
            if (inattentiveSleepWarningView.mFadeOutAnimator.isStarted()) {
                inattentiveSleepWarningView.mFadeOutAnimator.cancel();
                return;
            }
            return;
        }
        inattentiveSleepWarningView.setAlpha(1.0f);
        inattentiveSleepWarningView.setVisibility(0);
        WindowManager windowManager = inattentiveSleepWarningView.mWindowManager;
        IBinder iBinder = inattentiveSleepWarningView.mWindowToken;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2006, 256, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.setTitle("InattentiveSleepWarning");
        layoutParams.token = iBinder;
        windowManager.addView(inattentiveSleepWarningView, layoutParams);
        inattentiveSleepWarningView.announceForAccessibility(inattentiveSleepWarningView.getContext().getString(R.string.inattentive_sleep_warning_message));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Intent registerReceiver;
        this.mScreenOffTime = this.mPowerManager.isScreenOn() ? -1L : SystemClock.elapsedRealtime();
        this.mLastConfiguration.setTo(this.mContext.getResources().getConfiguration());
        Handler handler = this.mHandler;
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.power.PowerUI.4
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                PowerUI.this.updateBatteryWarningLevels();
            }
        };
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = -1;
        contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_trigger_level"), false, contentObserver, -1);
        updateBatteryWarningLevels();
        Receiver receiver = this.mReceiver;
        receiver.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        PowerUI powerUI = PowerUI.this;
        powerUI.mBroadcastDispatcher.registerReceiverWithHandler(receiver, intentFilter, powerUI.mHandler);
        if (!receiver.mHasReceivedBattery && (registerReceiver = PowerUI.this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) != null) {
            receiver.onReceive(PowerUI.this.mContext, registerReceiver);
        }
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        int i2 = this.mContext.getSharedPreferences("powerui_prefs", 0).getInt("boot_count", -1);
        try {
            i = Settings.Global.getInt(this.mContext.getContentResolver(), "boot_count");
        } catch (Settings.SettingNotFoundException unused) {
            Slog.e("PowerUI", "Failed to read system boot count from Settings.Global.BOOT_COUNT");
        }
        if (i > i2) {
            this.mContext.getSharedPreferences("powerui_prefs", 0).edit().putInt("boot_count", i).apply();
            if (this.mPowerManager.getLastShutdownReason() == 4) {
                throw null;
            }
        }
        contentResolver.registerContentObserver(Settings.Global.getUriFor("show_temperature_warning"), false, new ContentObserver(handler) { // from class: com.android.systemui.power.PowerUI.5
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                PowerUI.this.doSkinThermalEventListenerRegistration();
            }
        });
        contentResolver.registerContentObserver(Settings.Global.getUriFor("show_usb_temperature_alarm"), false, new ContentObserver(handler) { // from class: com.android.systemui.power.PowerUI.6
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                PowerUI.this.doUsbThermalEventListenerRegistration();
            }
        });
        doSkinThermalEventListenerRegistration();
        doUsbThermalEventListenerRegistration();
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        IVrManager iVrManager = this.mVrManager;
        if (iVrManager != null) {
            try {
                iVrManager.registerListener(this.mVrStateCallbacks);
            } catch (RemoteException e) {
                Slog.e("PowerUI", "Failed to register VR mode state listener: " + e);
            }
        }
    }

    public final void updateBatteryWarningLevels() {
        int integer = this.mContext.getResources().getInteger(android.R.integer.config_defaultRefreshRate);
        int integer2 = this.mContext.getResources().getInteger(android.R.integer.config_nightDisplayColorTemperatureMin);
        if (integer2 < integer) {
            integer2 = integer;
        }
        int[] iArr = this.mLowBatteryReminderLevels;
        iArr[0] = integer2;
        iArr[1] = integer;
        this.mLowBatteryAlertCloseLevel = this.mContext.getResources().getInteger(android.R.integer.config_nightDisplayColorTemperatureMax) + integer2;
    }
}
