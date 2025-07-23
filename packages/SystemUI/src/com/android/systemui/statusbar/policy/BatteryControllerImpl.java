package com.android.systemui.statusbar.policy;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import com.android.settingslib.fuelgauge.Estimate;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.Expandable;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.power.EnhancedEstimates;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.DumpUtilsKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.IMSParameter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BatteryControllerImpl extends BroadcastReceiver implements BatteryController, Dumpable {
    public boolean mAodPowerSave;
    public final Handler mBgHandler;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public boolean mCharged;
    public boolean mCharging;
    public final Context mContext;
    public final DemoModeController mDemoModeController;
    public final DumpManager mDumpManager;
    public Estimate mEstimate;
    public final EnhancedEstimates mEstimates;
    public int mLevel;
    public final BatteryControllerLogger mLogger;
    public final Handler mMainHandler;
    public int mPluggedChargingSource;
    public boolean mPluggedIn;
    public final PowerManager mPowerManager;
    public boolean mPowerSave;
    public boolean mWirelessCharging;
    public final ArrayList mChangeCallbacks = new ArrayList();
    public final ArrayList mFetchCallbacks = new ArrayList();
    public int mBatteryStatus = 1;
    public int mBatteryHealth = 1;
    public int mBatteryOnline = 1;
    public boolean mIsDirectPowerMode = false;
    public boolean mStateUnknown = false;
    public boolean mIsBatteryDefender = false;
    public boolean mIsIncompatibleCharging = false;
    public boolean mTestMode = false;
    boolean mHasReceivedBattery = false;
    public final Object mEstimateLock = new Object();
    public boolean mFetchingEstimate = false;
    public int mMiscEvent = 0;
    public final AtomicReference mPowerSaverStartExpandable = new AtomicReference();

    public BatteryControllerImpl(Context context, EnhancedEstimates enhancedEstimates, PowerManager powerManager, BroadcastDispatcher broadcastDispatcher, DemoModeController demoModeController, DumpManager dumpManager, BatteryControllerLogger batteryControllerLogger, Handler handler, Handler handler2) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mBgHandler = handler2;
        this.mPowerManager = powerManager;
        this.mEstimates = enhancedEstimates;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDemoModeController = demoModeController;
        this.mDumpManager = dumpManager;
        this.mLogger = batteryControllerLogger;
        batteryControllerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BatteryControllerLogger$$ExternalSyntheticLambda1 batteryControllerLogger$$ExternalSyntheticLambda1 = new BatteryControllerLogger$$ExternalSyntheticLambda1(4);
        LogBuffer logBuffer = batteryControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("BatteryControllerLog", logLevel, batteryControllerLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) obtain).int1 = System.identityHashCode(this);
        logBuffer.commit(obtain);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        BatteryController.BatteryStateChangeCallback batteryStateChangeCallback = (BatteryController.BatteryStateChangeCallback) obj;
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.add(batteryStateChangeCallback);
        }
        if (this.mHasReceivedBattery) {
            batteryStateChangeCallback.onBatteryLevelChanged(this.mLevel, this.mPluggedIn, this.mCharging);
            batteryStateChangeCallback.onBatteryLevelChanged(this.mLevel, this.mPluggedIn, this.mCharging, this.mBatteryStatus, this.mBatteryHealth, this.mBatteryOnline, this.mIsDirectPowerMode, this.mMiscEvent);
            batteryStateChangeCallback.onPowerSaveChanged(this.mPowerSave);
            batteryStateChangeCallback.onBatteryUnknownStateChanged(this.mStateUnknown);
            batteryStateChangeCallback.onWirelessChargingChanged(this.mWirelessCharging);
            batteryStateChangeCallback.onIsBatteryDefenderChanged(this.mIsBatteryDefender);
            batteryStateChangeCallback.onIsIncompatibleChargingChanged(this.mIsIncompatibleCharging);
        }
    }

    @Override // com.android.systemui.demomode.DemoMode
    public final List demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("battery");
        return arrayList;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void dispatchDemoCommand(Bundle bundle, String str) {
        this.mDemoModeController.getClass();
    }

    public final void dispatchSafeChange(Consumer consumer) {
        ArrayList arrayList;
        synchronized (this.mChangeCallbacks) {
            arrayList = new ArrayList(this.mChangeCallbacks);
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            consumer.accept((BatteryController.BatteryStateChangeCallback) arrayList.get(i));
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("BatteryController state:");
        asIndenting.increaseIndent();
        asIndenting.print("mHasReceivedBattery=");
        asIndenting.println(this.mHasReceivedBattery);
        asIndenting.print("mLevel=");
        asIndenting.println(this.mLevel);
        asIndenting.print("mPluggedIn=");
        asIndenting.println(this.mPluggedIn);
        asIndenting.print("mCharging=");
        asIndenting.println(this.mCharging);
        asIndenting.print("mCharged=");
        asIndenting.println(this.mCharged);
        asIndenting.print("mIsBatteryDefender=");
        asIndenting.println(this.mIsBatteryDefender);
        asIndenting.print("mIsIncompatibleCharging=");
        asIndenting.println(this.mIsIncompatibleCharging);
        asIndenting.print("mPowerSave=");
        asIndenting.println(this.mPowerSave);
        asIndenting.print("mStateUnknown=");
        asIndenting.println(this.mStateUnknown);
        asIndenting.print("  mBatteryOnline=");
        asIndenting.println(this.mBatteryOnline);
        asIndenting.print("  mBatteryStatus=");
        asIndenting.println(this.mBatteryStatus);
        asIndenting.print("  mBatteryHealth=");
        asIndenting.println(this.mBatteryHealth);
        asIndenting.print("  mMiscEvent=");
        asIndenting.println(this.mMiscEvent);
        asIndenting.print("  mIsDirectPowerMode=");
        asIndenting.println(this.mIsDirectPowerMode);
        asIndenting.println("Callbacks:------------------");
        asIndenting.increaseIndent();
        synchronized (this.mChangeCallbacks) {
            try {
                int size = this.mChangeCallbacks.size();
                for (int i = 0; i < size; i++) {
                    ((BatteryController.BatteryStateChangeCallback) this.mChangeCallbacks.get(i)).dump(asIndenting, strArr);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        asIndenting.decreaseIndent();
        asIndenting.println("------------------");
    }

    public boolean isBatteryDefender() {
        return this.mIsBatteryDefender;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void onDemoModeFinished() {
        registerReceiver$1();
        updatePowerSave();
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, Intent intent) {
        List<UsbPort> ports;
        UsbPortStatus status;
        int[] complianceWarnings;
        String action = intent.getAction();
        BatteryControllerLogger batteryControllerLogger = this.mLogger;
        batteryControllerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BatteryControllerLogger$$ExternalSyntheticLambda1 batteryControllerLogger$$ExternalSyntheticLambda1 = new BatteryControllerLogger$$ExternalSyntheticLambda1(0);
        LogBuffer logBuffer = batteryControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("BatteryControllerLog", logLevel, batteryControllerLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) obtain).str1 = action;
        logBuffer.commit(obtain);
        if (!action.equals("android.intent.action.BATTERY_CHANGED")) {
            if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) {
                updatePowerSave();
                return;
            }
            if (!action.equals("android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED")) {
                if (action.equals("com.android.systemui.BATTERY_LEVEL_TEST")) {
                    BatteryControllerLogger batteryControllerLogger2 = this.mLogger;
                    batteryControllerLogger2.getClass();
                    BatteryControllerLogger$$ExternalSyntheticLambda1 batteryControllerLogger$$ExternalSyntheticLambda12 = new BatteryControllerLogger$$ExternalSyntheticLambda1(2);
                    LogBuffer logBuffer2 = batteryControllerLogger2.logBuffer;
                    logBuffer2.commit(logBuffer2.obtain("BatteryControllerLog", logLevel, batteryControllerLogger$$ExternalSyntheticLambda12, null));
                    this.mTestMode = true;
                    this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.BatteryControllerImpl.1
                        public final int mSavedLevel;
                        public final boolean mSavedPluggedIn;
                        public int mCurrentLevel = 0;
                        public int mIncrement = 1;
                        public final Intent mTestIntent = new Intent("android.intent.action.BATTERY_CHANGED");

                        {
                            this.mSavedLevel = BatteryControllerImpl.this.mLevel;
                            this.mSavedPluggedIn = BatteryControllerImpl.this.mPluggedIn;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i = this.mCurrentLevel;
                            if (i < 0) {
                                BatteryControllerLogger batteryControllerLogger3 = BatteryControllerImpl.this.mLogger;
                                batteryControllerLogger3.getClass();
                                LogLevel logLevel2 = LogLevel.DEBUG;
                                BatteryControllerLogger$$ExternalSyntheticLambda1 batteryControllerLogger$$ExternalSyntheticLambda13 = new BatteryControllerLogger$$ExternalSyntheticLambda1(7);
                                LogBuffer logBuffer3 = batteryControllerLogger3.logBuffer;
                                logBuffer3.commit(logBuffer3.obtain("BatteryControllerLog", logLevel2, batteryControllerLogger$$ExternalSyntheticLambda13, null));
                                BatteryControllerImpl.this.mTestMode = false;
                                this.mTestIntent.putExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, this.mSavedLevel);
                                this.mTestIntent.putExtra("plugged", this.mSavedPluggedIn);
                                this.mTestIntent.putExtra("testmode", false);
                            } else {
                                this.mTestIntent.putExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, i);
                                this.mTestIntent.putExtra("plugged", this.mIncrement > 0 ? 1 : 0);
                                this.mTestIntent.putExtra("testmode", true);
                            }
                            context.sendBroadcast(this.mTestIntent);
                            BatteryControllerImpl batteryControllerImpl = BatteryControllerImpl.this;
                            if (batteryControllerImpl.mTestMode) {
                                int i2 = this.mCurrentLevel;
                                int i3 = this.mIncrement;
                                int i4 = i2 + i3;
                                this.mCurrentLevel = i4;
                                if (i4 == 100) {
                                    this.mIncrement = i3 * (-1);
                                }
                                batteryControllerImpl.mMainHandler.postDelayed(this, 200L);
                            }
                        }
                    });
                    return;
                }
                return;
            }
            Context context2 = this.mContext;
            try {
                if (Settings.Secure.getInt(context2.getContentResolver(), "incompatible_charger_warning_disabled", 0) != 1) {
                    UsbManager usbManager = (UsbManager) context2.getSystemService(UsbManager.class);
                    if (usbManager != null && (ports = usbManager.getPorts()) != null && !ports.isEmpty()) {
                        loop0: for (UsbPort usbPort : ports) {
                            Log.d("BatteryController", "usbPort: " + usbPort);
                            if (usbPort.supportsComplianceWarnings() && (status = usbPort.getStatus()) != null && status.isConnected() && (complianceWarnings = status.getComplianceWarnings()) != null && complianceWarnings.length != 0) {
                                for (int i : complianceWarnings) {
                                    if (i == 2 || i == 5) {
                                        break loop0;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Log.d("BatteryController", "containsIncompatibleChargers: disabled");
                }
            } catch (Exception e) {
                Log.e("BatteryController", "containsIncompatibleChargers()", e);
            }
            r3 = false;
            if (r3 != this.mIsIncompatibleCharging) {
                this.mIsIncompatibleCharging = r3;
                dispatchSafeChange(new BatteryControllerImpl$$ExternalSyntheticLambda1(this, 3));
                return;
            }
            return;
        }
        final BatteryControllerLogger batteryControllerLogger3 = this.mLogger;
        batteryControllerLogger3.getClass();
        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.policy.BatteryControllerLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int i2 = BatteryControllerLogger.$r8$clinit;
                int int1 = logMessage.getInt1();
                BatteryControllerLogger.this.getClass();
                String valueOf = int1 == -11 ? "(missing)" : String.valueOf(int1);
                int int2 = logMessage.getInt2();
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Processing BATTERY_CHANGED intent. level=", valueOf, " scale=", int2 != -11 ? String.valueOf(int2) : "(missing)");
            }
        };
        LogBuffer logBuffer3 = batteryControllerLogger3.logBuffer;
        LogMessage obtain2 = logBuffer3.obtain("BatteryControllerLog", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain2;
        logMessageImpl.int1 = intent.getIntExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, -11);
        logMessageImpl.int2 = intent.getIntExtra("scale", -11);
        logBuffer3.commit(obtain2);
        if (this.mTestMode && !intent.getBooleanExtra("testmode", false)) {
            BatteryControllerLogger batteryControllerLogger4 = this.mLogger;
            batteryControllerLogger4.getClass();
            BatteryControllerLogger$$ExternalSyntheticLambda1 batteryControllerLogger$$ExternalSyntheticLambda13 = new BatteryControllerLogger$$ExternalSyntheticLambda1(1);
            LogBuffer logBuffer4 = batteryControllerLogger4.logBuffer;
            logBuffer4.commit(logBuffer4.obtain("BatteryControllerLog", logLevel, batteryControllerLogger$$ExternalSyntheticLambda13, null));
            return;
        }
        this.mHasReceivedBattery = true;
        this.mLevel = (int) ((intent.getIntExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, 0) * 100.0f) / intent.getIntExtra("scale", 100));
        int i2 = this.mPluggedChargingSource;
        int intExtra = intent.getIntExtra("plugged", 0);
        this.mPluggedChargingSource = intExtra;
        this.mPluggedIn = intExtra != 0;
        int intExtra2 = intent.getIntExtra(IMSParameter.CALL.STATUS, 1);
        boolean z = intExtra2 == 5;
        this.mCharged = z;
        boolean z2 = z || intExtra2 == 2;
        this.mCharging = z2;
        if (this.mWirelessCharging != (z2 && intent.getIntExtra("plugged", 0) == 4)) {
            this.mWirelessCharging = !this.mWirelessCharging;
            synchronized (this.mChangeCallbacks) {
                this.mChangeCallbacks.forEach(new BatteryControllerImpl$$ExternalSyntheticLambda1(this, 2));
            }
        }
        boolean z3 = !intent.getBooleanExtra("present", true);
        if (z3 != this.mStateUnknown) {
            this.mStateUnknown = z3;
            dispatchSafeChange(new BatteryControllerImpl$$ExternalSyntheticLambda1(this, 0));
        }
        boolean z4 = intent.getIntExtra("android.os.extra.CHARGING_STATUS", 1) == 4;
        if (z4 != this.mIsBatteryDefender) {
            this.mIsBatteryDefender = z4;
            dispatchSafeChange(new BatteryControllerImpl$$ExternalSyntheticLambda1(this, 6));
        }
        if (this.mPluggedChargingSource != i2) {
            updatePowerSave();
        }
        int intExtra3 = intent.getIntExtra("health", 1);
        this.mBatteryStatus = intExtra2;
        this.mBatteryHealth = intExtra3;
        this.mBatteryOnline = intent.getIntExtra("online", 1);
        int intExtra4 = intent.getIntExtra("misc_event", 0);
        this.mMiscEvent = intExtra4;
        this.mIsDirectPowerMode = (intExtra4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 16384;
        BatteryControllerLogger batteryControllerLogger5 = this.mLogger;
        int i3 = this.mLevel;
        boolean z5 = this.mPluggedIn;
        boolean z6 = this.mCharging;
        batteryControllerLogger5.getClass();
        LogLevel logLevel2 = LogLevel.DEBUG;
        BatteryControllerLogger$$ExternalSyntheticLambda1 batteryControllerLogger$$ExternalSyntheticLambda14 = new BatteryControllerLogger$$ExternalSyntheticLambda1(6);
        LogBuffer logBuffer5 = batteryControllerLogger5.logBuffer;
        LogMessage obtain3 = logBuffer5.obtain("BatteryControllerLog", logLevel2, batteryControllerLogger$$ExternalSyntheticLambda14, null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain3;
        logMessageImpl2.int1 = i3;
        logMessageImpl2.bool1 = z5;
        logMessageImpl2.bool2 = z6;
        logBuffer5.commit(obtain3);
        dispatchSafeChange(new BatteryControllerImpl$$ExternalSyntheticLambda1(this, 4));
        dispatchSafeChange(new BatteryControllerImpl$$ExternalSyntheticLambda1(this, 5));
    }

    public final void registerReceiver$1() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("com.android.systemui.BATTERY_LEVEL_TEST");
        intentFilter.addAction("android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED");
        this.mBroadcastDispatcher.registerReceiver(intentFilter, this);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        BatteryController.BatteryStateChangeCallback batteryStateChangeCallback = (BatteryController.BatteryStateChangeCallback) obj;
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.remove(batteryStateChangeCallback);
        }
    }

    public final void setPowerSaveMode(boolean z, Expandable expandable) {
        if (z) {
            this.mPowerSaverStartExpandable.set(new WeakReference(expandable));
        }
        Context context = this.mContext;
        synchronized (BatterySaverUtils.class) {
            ContentResolver contentResolver = context.getContentResolver();
            PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
            if (z && keyguardManager != null && keyguardManager.isDeviceLocked()) {
                Log.d("BatterySaverUtils", "Device is locked, setPowerSaveModeEnabled by default. " + powerManager.setPowerSaveModeEnabled(true));
                return;
            }
            Bundle bundle = new Bundle(1);
            bundle.putBoolean("extra_confirm_only", false);
            if (z && (Settings.Secure.getInt(context.getContentResolver(), "low_power_warning_acknowledged", 0) == 0 || Settings.Secure.getInt(context.getContentResolver(), "extra_low_power_warning_acknowledged", 0) == 0)) {
                BatterySaverUtils.sendSystemUiBroadcast(context, "PNW.startSaverConfirmation", bundle);
                return;
            }
            if (powerManager.setPowerSaveModeEnabled(z)) {
                if (z) {
                    int i = Settings.Secure.getInt(contentResolver, "low_power_manual_activation_count", 0) + 1;
                    Settings.Secure.putInt(contentResolver, "low_power_manual_activation_count", i);
                    BatterySaverUtils.Parameters parameters = new BatterySaverUtils.Parameters(context);
                    if (i >= parameters.startNth && i <= parameters.endNth && Settings.Global.getInt(contentResolver, "low_power_trigger_level", 0) == 0 && Settings.Secure.getInt(contentResolver, "suppress_auto_battery_saver_suggestion", 0) == 0) {
                        BatterySaverUtils.sendSystemUiBroadcast(context, "PNW.autoSaverSuggestion", bundle);
                    }
                }
                Bundle bundle2 = new Bundle(2);
                bundle2.putInt("extra_power_save_mode_manual_enabled_reason", 4);
                bundle2.putBoolean("extra_power_save_mode_manual_enabled", z);
                BatterySaverUtils.sendSystemUiBroadcast(context, "com.android.settingslib.fuelgauge.ACTION_SAVER_STATE_MANUAL_UPDATE", bundle2);
            }
        }
    }

    public final void updatePowerSave() {
        boolean isPowerSaveMode = this.mPowerManager.isPowerSaveMode();
        if (isPowerSaveMode == this.mPowerSave) {
            return;
        }
        this.mPowerSave = isPowerSaveMode;
        this.mAodPowerSave = this.mPowerManager.getPowerSaveState(14).batterySaverEnabled;
        BatteryControllerLogger batteryControllerLogger = this.mLogger;
        boolean z = this.mPowerSave;
        batteryControllerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BatteryControllerLogger$$ExternalSyntheticLambda1 batteryControllerLogger$$ExternalSyntheticLambda1 = new BatteryControllerLogger$$ExternalSyntheticLambda1(5);
        LogBuffer logBuffer = batteryControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("BatteryControllerLog", logLevel, batteryControllerLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
        dispatchSafeChange(new BatteryControllerImpl$$ExternalSyntheticLambda1(this, 1));
    }
}
