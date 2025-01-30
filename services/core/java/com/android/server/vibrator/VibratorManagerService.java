package com.android.server.vibrator;

import android.R;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.CombinedVibration;
import android.os.ExternalVibration;
import android.os.Handler;
import android.os.IBinder;
import android.os.IExternalVibratorService;
import android.os.IVibratorManagerService;
import android.os.IVibratorStateListener;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.Trace;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.SemHapticSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.IBatteryStats;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.vibrator.Vibration;
import com.android.server.vibrator.VibrationSettings;
import com.android.server.vibrator.VibrationStats;
import com.android.server.vibrator.VibrationThread;
import com.android.server.vibrator.VibratorController;
import com.android.server.vibrator.VibratorManagerService;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.audio.GoodCatchManager;
import com.samsung.android.server.vibrator.VibratorHelper;
import com.samsung.android.server.vibrator.VibratorHqmData;
import com.samsung.android.server.vibrator.VibratorHqmHelper;
import com.samsung.android.vibrator.VibRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes3.dex */
public class VibratorManagerService extends IVibratorManagerService.Stub {
    public static final VibrationAttributes DEFAULT_ATTRIBUTES = new VibrationAttributes.Builder().build();
    public final SparseArray mAlwaysOnEffects;
    public final AppOpsManager mAppOps;
    public final IBatteryStats mBatteryStatsService;
    public final long mCapabilities;
    public VibrationEffect.Composed mComposed;
    public final Context mContext;
    public ExternalVibrationHolder mCurrentExternalVibration;
    public VibrationStepConductor mCurrentVibration;
    public final DeviceVibrationEffectAdapter mDeviceVibrationEffectAdapter;
    public final VibratorFrameworkStatsLogger mFrameworkStatsLogger;
    public GoodCatchManager mGoodCatchManager;
    public final Handler mHandler;
    public VibratorHqmHelper mHqmHelper;
    public VibratorHqmData mHqmLoggingData;
    public final InputDeviceDelegate mInputDeviceDelegate;
    public BroadcastReceiver mIntentReceiver;
    public final Object mLock = new Object();
    public final NativeWrapper mNativeWrapper;
    public VibrationStepConductor mNextVibration;
    public INotificationManager mNotificationManager;
    public BroadcastReceiver mSamsungReceiver;
    public SemHapticSegment mSemHapticSegment;
    public boolean mServiceReady;
    public final VibrationScaler mVibrationScaler;
    public final VibrationSettings mVibrationSettings;
    public final VibrationThread mVibrationThread;
    public final VibrationThreadCallbacks mVibrationThreadCallbacks;
    public VibratorHelper mVibratorHelper;
    public final int[] mVibratorIds;
    public final VibratorManagerRecords mVibratorManagerRecords;
    public final SparseArray mVibrators;
    public final PowerManager.WakeLock mWakeLock;

    interface OnSyncedVibrationCompleteListener {
        void onComplete(long j);
    }

    public static /* synthetic */ VibrationEffect lambda$fixupAlwaysOnEffectsLocked$2(VibrationEffect vibrationEffect, VibratorController vibratorController) {
        return vibrationEffect;
    }

    public static native void nativeCancelSynced(long j);

    public static native long nativeGetCapabilities(long j);

    public static native long nativeGetFinalizer();

    public static native int[] nativeGetVibratorIds(long j);

    public static native long nativeInit(OnSyncedVibrationCompleteListener onSyncedVibrationCompleteListener);

    public static native boolean nativePrepareSynced(long j, int[] iArr);

    public static native boolean nativeTriggerSynced(long j, long j2);

    public boolean isAllowedUsage(int i) {
        return i == 49 || i == 0 || i == 33;
    }

    public class Lifecycle extends SystemService {
        public VibratorManagerService mService;

        public Lifecycle(Context context) {
            super(context);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.vibrator.VibratorManagerService] */
        @Override // com.android.server.SystemService
        public void onStart() {
            ?? vibratorManagerService = new VibratorManagerService(getContext(), new Injector());
            this.mService = vibratorManagerService;
            publishBinderService("vibrator_manager", vibratorManagerService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 500) {
                this.mService.systemReady();
            } else if (i == 1000) {
                this.mService.bootCompleteReady();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public VibratorManagerService(Context context, Injector injector) {
        VibrationThreadCallbacks vibrationThreadCallbacks = new VibrationThreadCallbacks();
        this.mVibrationThreadCallbacks = vibrationThreadCallbacks;
        this.mAlwaysOnEffects = new SparseArray();
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.vibrator.VibratorManagerService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                    synchronized (VibratorManagerService.this.mLock) {
                        if (intent.getIntExtra("reason", -1) == 19) {
                            Log.d("VibratorManagerService", "Keep screen turned off");
                            return;
                        }
                        VibratorManagerService vibratorManagerService = VibratorManagerService.this;
                        if (vibratorManagerService.ignoreCancellingCurrentRingVibration(vibratorManagerService.mCurrentVibration)) {
                            return;
                        }
                        VibratorManagerService vibratorManagerService2 = VibratorManagerService.this;
                        if (vibratorManagerService2.shouldCancelOnScreenOffLocked(vibratorManagerService2.mNextVibration)) {
                            VibratorManagerService.this.clearNextVibrationLocked(new Vibration.EndInfo(Vibration.Status.CANCELLED_BY_SCREEN_OFF));
                        }
                        VibratorManagerService vibratorManagerService3 = VibratorManagerService.this;
                        if (vibratorManagerService3.shouldCancelOnScreenOffLocked(vibratorManagerService3.mCurrentVibration)) {
                            VibratorManagerService.this.mCurrentVibration.notifyCancelled(new Vibration.EndInfo(Vibration.Status.CANCELLED_BY_SCREEN_OFF), false);
                        }
                    }
                }
            }
        };
        this.mHqmLoggingData = new VibratorHqmData();
        this.mSamsungReceiver = new SamsungBroadcastReceiver();
        this.mContext = context;
        Handler createHandler = injector.createHandler(Looper.myLooper());
        this.mHandler = createHandler;
        VibrationSettings vibrationSettings = new VibrationSettings(context, createHandler, this);
        this.mVibrationSettings = vibrationSettings;
        this.mVibratorHelper = VibratorHelper.getInstance();
        registerCustomIntent(context);
        LocalServices.addService(VibratorManagerInternal.class, new VibratorManagerInternal(this));
        this.mVibrationScaler = new VibrationScaler(context, vibrationSettings);
        this.mInputDeviceDelegate = new InputDeviceDelegate(context, createHandler);
        this.mDeviceVibrationEffectAdapter = new DeviceVibrationEffectAdapter(vibrationSettings);
        VibrationCompleteListener vibrationCompleteListener = new VibrationCompleteListener(this);
        NativeWrapper nativeWrapper = injector.getNativeWrapper();
        this.mNativeWrapper = nativeWrapper;
        nativeWrapper.init(vibrationCompleteListener);
        this.mVibratorManagerRecords = new VibratorManagerRecords(context.getResources().getInteger(R.integer.config_respectsActivityMinWidthHeightMultiWindow));
        this.mBatteryStatsService = injector.getBatteryStatsService();
        this.mFrameworkStatsLogger = injector.getFrameworkStatsLogger(createHandler);
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, "*vibrator*");
        this.mWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(true);
        VibrationThread vibrationThread = new VibrationThread(newWakeLock, vibrationThreadCallbacks);
        this.mVibrationThread = vibrationThread;
        vibrationThread.start();
        this.mCapabilities = nativeWrapper.getCapabilities();
        int[] vibratorIds = nativeWrapper.getVibratorIds();
        if (vibratorIds == null) {
            this.mVibratorIds = new int[0];
            this.mVibrators = new SparseArray(0);
        } else {
            this.mVibratorIds = vibratorIds;
            this.mVibrators = new SparseArray(vibratorIds.length);
            for (int i : vibratorIds) {
                this.mVibrators.put(i, injector.createVibratorController(i, vibrationCompleteListener));
            }
        }
        this.mNativeWrapper.cancelSynced();
        for (int i2 = 0; i2 < this.mVibrators.size(); i2++) {
            ((VibratorController) this.mVibrators.valueAt(i2)).reset();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(this.mIntentReceiver, intentFilter, 4);
        injector.addService("external_vibrator_service", new ExternalVibratorService());
    }

    public void systemReady() {
        Slog.v("VibratorManagerService", "Initializing VibratorManager service...");
        Trace.traceBegin(8388608L, "systemReady");
        for (int i = 0; i < this.mVibrators.size(); i++) {
            try {
                ((VibratorController) this.mVibrators.valueAt(i)).reloadVibratorInfoIfNeeded();
            } catch (Throwable th) {
                synchronized (this.mLock) {
                    this.mServiceReady = true;
                    Slog.v("VibratorManagerService", "VibratorManager service initialized");
                    Trace.traceEnd(8388608L);
                    throw th;
                }
            }
        }
        this.mVibrationSettings.onSystemReady();
        this.mInputDeviceDelegate.onSystemReady();
        this.mVibrationSettings.addListener(new VibrationSettings.OnVibratorSettingsChanged() { // from class: com.android.server.vibrator.VibratorManagerService$$ExternalSyntheticLambda0
            @Override // com.android.server.vibrator.VibrationSettings.OnVibratorSettingsChanged
            public final void onChange() {
                VibratorManagerService.this.updateServiceState();
            }
        });
        updateServiceState();
        onCustomSystemReady();
        synchronized (this.mLock) {
            this.mServiceReady = true;
        }
        Slog.v("VibratorManagerService", "VibratorManager service initialized");
        Trace.traceEnd(8388608L);
    }

    public int[] getVibratorIds() {
        int[] iArr = this.mVibratorIds;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public VibratorInfo getVibratorInfo(int i) {
        VibratorController vibratorController = (VibratorController) this.mVibrators.get(i);
        if (vibratorController == null) {
            return null;
        }
        VibratorInfo vibratorInfo = vibratorController.getVibratorInfo();
        synchronized (this.mLock) {
            if (this.mServiceReady) {
                return vibratorInfo;
            }
            if (vibratorController.isVibratorInfoLoadSuccessful()) {
                return vibratorInfo;
            }
            return null;
        }
    }

    public boolean isVibrating(int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_VIBRATOR_STATE", "isVibrating");
        VibratorController vibratorController = (VibratorController) this.mVibrators.get(i);
        return vibratorController != null && vibratorController.isVibrating();
    }

    public String executeVibrationDebugCommand(int i) {
        return i != 0 ? "" : addCustomDump();
    }

    public boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_VIBRATOR_STATE", "registerVibratorStateListener");
        VibratorController vibratorController = (VibratorController) this.mVibrators.get(i);
        if (vibratorController == null) {
            return false;
        }
        return vibratorController.registerVibratorStateListener(iVibratorStateListener);
    }

    public boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_VIBRATOR_STATE", "unregisterVibratorStateListener");
        VibratorController vibratorController = (VibratorController) this.mVibrators.get(i);
        if (vibratorController == null) {
            return false;
        }
        return vibratorController.unregisterVibratorStateListener(iVibratorStateListener);
    }

    public boolean setAlwaysOnEffect(int i, String str, final int i2, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes) {
        Trace.traceBegin(8388608L, "setAlwaysOnEffect");
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.VIBRATE_ALWAYS_ON", "setAlwaysOnEffect");
            if (combinedVibration == null) {
                synchronized (this.mLock) {
                    this.mAlwaysOnEffects.delete(i2);
                    onAllVibratorsLocked(new Consumer() { // from class: com.android.server.vibrator.VibratorManagerService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            VibratorManagerService.lambda$setAlwaysOnEffect$0(i2, (VibratorController) obj);
                        }
                    });
                }
                return true;
            }
            if (!isEffectValid(combinedVibration)) {
                return false;
            }
            VibrationAttributes fixupVibrationAttributes = fixupVibrationAttributes(vibrationAttributes, combinedVibration);
            synchronized (this.mLock) {
                SparseArray fixupAlwaysOnEffectsLocked = fixupAlwaysOnEffectsLocked(combinedVibration);
                if (fixupAlwaysOnEffectsLocked == null) {
                    return false;
                }
                AlwaysOnVibration alwaysOnVibration = new AlwaysOnVibration(i2, new Vibration.CallerInfo(fixupVibrationAttributes, i, 0, str, null), fixupAlwaysOnEffectsLocked);
                this.mAlwaysOnEffects.put(i2, alwaysOnVibration);
                updateAlwaysOnLocked(alwaysOnVibration);
                return true;
            }
        } finally {
        }
        Trace.traceEnd(8388608L);
    }

    public static /* synthetic */ void lambda$setAlwaysOnEffect$0(int i, VibratorController vibratorController) {
        if (vibratorController.hasCapability(64L)) {
            vibratorController.updateAlwaysOn(i, null);
        }
    }

    public void vibrate(int i, int i2, String str, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes, String str2, IBinder iBinder) {
        vibrateInternal(i, i2, str, combinedVibration, vibrationAttributes, str2, iBinder);
    }

    public HalVibration vibrateInternal(int i, int i2, String str, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes, String str2, IBinder iBinder) {
        HalVibration halVibration;
        int magnitude;
        Trace.traceBegin(8388608L, "vibrate, reason = " + str2);
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.VIBRATE", "vibrate");
            if (iBinder == null) {
                Slog.e("VibratorManagerService", "token must not be null");
                return null;
            }
            enforceUpdateAppOpsStatsPermission(i);
            if (!isEffectValid(combinedVibration)) {
                return null;
            }
            Slog.d("VibratorManagerService", "vibrate - uid: " + i + ", opPkg: " + str + ", effect: " + combinedVibration + ", attrs: " + vibrationAttributes + ", reason: " + str2 + ", token: " + iBinder);
            VibrationEffect.Composed composedEffect = getComposedEffect(combinedVibration);
            this.mComposed = composedEffect;
            if (composedEffect == null) {
                cancelVibrate(-1, iBinder);
                return null;
            }
            VibrationAttributes fixupVibrationAttributes = fixupVibrationAttributes(vibrationAttributes, combinedVibration);
            try {
                INotificationManager notificationService = getNotificationService();
                if (fixupVibrationAttributes.getUsage() == 49 && !notificationService.areNotificationsEnabledForPackage(str, i)) {
                    Log.v("VibratorManagerService", "Vibrator Cancel, notifications are disabled : " + str);
                    cancelVibrate(-1, iBinder);
                    return null;
                }
            } catch (RemoteException e) {
                Log.e("VibratorManagerService", "Failed to call NotificationManager : " + e);
            }
            SemHapticSegment semHapticSegment = (VibrationEffectSegment) this.mComposed.getSegments().get(0);
            if (semHapticSegment instanceof SemHapticSegment) {
                SemHapticSegment semHapticSegment2 = semHapticSegment;
                this.mSemHapticSegment = semHapticSegment2;
                if (semHapticSegment2.getMagnitude() == -1) {
                    magnitude = getMagnitudeByUsage(fixupVibrationAttributes);
                } else {
                    magnitude = this.mSemHapticSegment.getMagnitude();
                    fixupVibrationAttributes = new VibrationAttributes.Builder(fixupVibrationAttributes).semAddTag("INDIVIDUAL_INTENSITY").build();
                }
                VibrationAttributes vibrationAttributes2 = fixupVibrationAttributes;
                SemVibration createSemVibration = new SemVibrationFactory().createSemVibration(this.mContext, new SemVibrationBundle(iBinder, combinedVibration, this.mSemHapticSegment.getType(), this.mComposed.getRepeatIndex(), magnitude, new Vibration.CallerInfo(vibrationAttributes2, i, i2, str, str2)), this.mSemHapticSegment, this.mVibrationSettings);
                halVibration = createSemVibration.getVibration();
                if (halVibration == null) {
                    cancelVibrate(-1, iBinder);
                    Slog.d("VibratorManagerService", "The vibration was not generated normally.");
                    return null;
                }
                Slog.d("VibratorManagerService", createSemVibration.toString());
                fixupVibrationAttributes = vibrationAttributes2;
            } else {
                HalVibration halVibration2 = new HalVibration(iBinder, combinedVibration, new Vibration.CallerInfo(fixupVibrationAttributes, i, i2, str, str2));
                halVibration2.setMagnitude(getMagnitudeByUsage(fixupVibrationAttributes));
                halVibration = halVibration2;
            }
            fillVibrationFallbacks(halVibration, combinedVibration);
            if (fixupVibrationAttributes.isFlagSet(4)) {
                this.mVibrationSettings.mSettingObserver.onChange(false);
            }
            synchronized (this.mLock) {
                Slog.d("VibratorManagerService", "Starting vibrate for vibration " + halVibration.f1733id);
                Vibration.EndInfo shouldIgnoreVibrationLocked = shouldIgnoreVibrationLocked(halVibration.callerInfo);
                if (shouldIgnoreVibrationLocked == null) {
                    shouldIgnoreVibrationLocked = shouldIgnoreVibrationForOngoingLocked(halVibration);
                }
                if (shouldIgnoreVibrationLocked == null) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
                        if (externalVibrationHolder != null) {
                            externalVibrationHolder.mute();
                            halVibration.stats.reportInterruptedAnotherVibration(this.mCurrentExternalVibration.callerInfo);
                            endExternalVibrateLocked(new Vibration.EndInfo(Vibration.Status.CANCELLED_SUPERSEDED, halVibration.callerInfo), false);
                        } else {
                            VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
                            if (vibrationStepConductor != null) {
                                if (vibrationStepConductor.getVibration().canPipelineWith(halVibration)) {
                                    Slog.d("VibratorManagerService", "Pipelining vibration " + halVibration.f1733id);
                                } else {
                                    halVibration.stats.reportInterruptedAnotherVibration(this.mCurrentVibration.getVibration().callerInfo);
                                    this.mCurrentVibration.notifyCancelled(new Vibration.EndInfo(Vibration.Status.CANCELLED_SUPERSEDED, halVibration.callerInfo), false);
                                }
                            }
                        }
                        shouldIgnoreVibrationLocked = startVibrationLocked(halVibration);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                if (shouldIgnoreVibrationLocked != null) {
                    endVibrationLocked(halVibration, shouldIgnoreVibrationLocked, true);
                }
                reportVibrationInfo(halVibration, fixupVibrationAttributes);
                this.mHqmLoggingData.increaseCount(halVibration.callerInfo.attrs.getUsage());
            }
            return halVibration;
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public void cancelVibrate(int i, IBinder iBinder) {
        Trace.traceBegin(8388608L, "cancelVibrate");
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.VIBRATE", "cancelVibrate");
            synchronized (this.mLock) {
                Slog.d("VibratorManagerService", "Canceling vibration");
                Vibration.EndInfo endInfo = new Vibration.EndInfo(Vibration.Status.CANCELLED_BY_USER);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    VibrationStepConductor vibrationStepConductor = this.mNextVibration;
                    if (vibrationStepConductor != null && shouldCancelVibration(vibrationStepConductor.getVibration(), i, iBinder)) {
                        clearNextVibrationLocked(endInfo);
                    }
                    VibrationStepConductor vibrationStepConductor2 = this.mCurrentVibration;
                    if (vibrationStepConductor2 != null && shouldCancelVibration(vibrationStepConductor2.getVibration(), i, iBinder)) {
                        this.mCurrentVibration.notifyCancelled(endInfo, false);
                    }
                    ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
                    if (externalVibrationHolder != null && shouldCancelVibration(externalVibrationHolder.externalVibration.getVibrationAttributes(), i)) {
                        if (this.mCurrentExternalVibration.externalVibration.isRepeating()) {
                            Log.secD("VibratorManagerService", "Keeping repeating external vibration");
                        } else {
                            this.mCurrentExternalVibration.mute();
                            endExternalVibrateLocked(endInfo, false);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "VibratorManagerService", printWriter)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            boolean z = false;
            for (String str : strArr) {
                if (str.equals("--proto")) {
                    z = true;
                }
            }
            try {
                if (z) {
                    dumpProto(fileDescriptor);
                } else {
                    dumpText(printWriter);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void dumpText(PrintWriter printWriter) {
        Slog.d("VibratorManagerService", "Dumping vibrator manager service to text...");
        synchronized (this.mLock) {
            printWriter.println("Vibrator Manager Service:");
            printWriter.println("  mVibrationSettings:");
            printWriter.println("    " + this.mVibrationSettings);
            printWriter.println();
            printWriter.println("  mVibratorControllers:");
            for (int i = 0; i < this.mVibrators.size(); i++) {
                printWriter.println("    " + this.mVibrators.valueAt(i));
            }
            printWriter.println();
            printWriter.println("  mCurrentVibration:");
            StringBuilder sb = new StringBuilder();
            sb.append("    ");
            VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
            Vibration.DebugInfo debugInfo = null;
            sb.append(vibrationStepConductor == null ? null : vibrationStepConductor.getVibration().getDebugInfo());
            printWriter.println(sb.toString());
            printWriter.println();
            printWriter.println("  mNextVibration:");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("    ");
            VibrationStepConductor vibrationStepConductor2 = this.mNextVibration;
            sb2.append(vibrationStepConductor2 == null ? null : vibrationStepConductor2.getVibration().getDebugInfo());
            printWriter.println(sb2.toString());
            printWriter.println();
            printWriter.println("  mCurrentExternalVibration:");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("    ");
            ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
            if (externalVibrationHolder != null) {
                debugInfo = externalVibrationHolder.getDebugInfo();
            }
            sb3.append(debugInfo);
            printWriter.println(sb3.toString());
            printWriter.println();
        }
        this.mVibratorManagerRecords.dumpText(printWriter);
        printWriter.println(addCustomDump());
    }

    public synchronized void dumpProto(FileDescriptor fileDescriptor) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        Slog.d("VibratorManagerService", "Dumping vibrator manager service to proto...");
        synchronized (this.mLock) {
            this.mVibrationSettings.dumpProto(protoOutputStream);
            VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
            if (vibrationStepConductor != null) {
                vibrationStepConductor.getVibration().getDebugInfo().dumpProto(protoOutputStream, 1146756268034L);
            }
            ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
            if (externalVibrationHolder != null) {
                externalVibrationHolder.getDebugInfo().dumpProto(protoOutputStream, 1146756268036L);
            }
            boolean z = false;
            boolean z2 = false;
            for (int i = 0; i < this.mVibrators.size(); i++) {
                protoOutputStream.write(2220498092033L, this.mVibrators.keyAt(i));
                z |= ((VibratorController) this.mVibrators.valueAt(i)).isVibrating();
                z2 |= ((VibratorController) this.mVibrators.valueAt(i)).isUnderExternalControl();
            }
            protoOutputStream.write(1133871366147L, z);
            protoOutputStream.write(1133871366149L, z2);
        }
        this.mVibratorManagerRecords.dumpProto(protoOutputStream);
        protoOutputStream.flush();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new VibratorManagerShellCommand(shellCallback.getShellCallbackBinder()).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public void updateServiceState() {
        synchronized (this.mLock) {
            Slog.d("VibratorManagerService", "Updating device state...");
            boolean updateInputDeviceVibrators = this.mInputDeviceDelegate.updateInputDeviceVibrators(this.mVibrationSettings.shouldVibrateInputDevices());
            for (int i = 0; i < this.mAlwaysOnEffects.size(); i++) {
                updateAlwaysOnLocked((AlwaysOnVibration) this.mAlwaysOnEffects.valueAt(i));
            }
            VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
            if (vibrationStepConductor == null) {
                return;
            }
            Vibration.EndInfo shouldIgnoreVibrationLocked = shouldIgnoreVibrationLocked(vibrationStepConductor.getVibration().callerInfo);
            if (updateInputDeviceVibrators || shouldIgnoreVibrationLocked != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Canceling vibration because settings changed: ");
                sb.append(updateInputDeviceVibrators ? "input devices changed" : shouldIgnoreVibrationLocked.status);
                Slog.d("VibratorManagerService", sb.toString());
                this.mCurrentVibration.notifyCancelled(new Vibration.EndInfo(Vibration.Status.CANCELLED_BY_SETTINGS_UPDATE), false);
            }
        }
    }

    public final void setExternalControl(boolean z, VibrationStats vibrationStats) {
        for (int i = 0; i < this.mVibrators.size(); i++) {
            ((VibratorController) this.mVibrators.valueAt(i)).setExternalControl(z);
            vibrationStats.reportSetExternalControl();
        }
    }

    public final void updateAlwaysOnLocked(AlwaysOnVibration alwaysOnVibration) {
        for (int i = 0; i < alwaysOnVibration.effects.size(); i++) {
            VibratorController vibratorController = (VibratorController) this.mVibrators.get(alwaysOnVibration.effects.keyAt(i));
            PrebakedSegment prebakedSegment = (PrebakedSegment) alwaysOnVibration.effects.valueAt(i);
            if (vibratorController != null) {
                vibratorController.updateAlwaysOn(alwaysOnVibration.alwaysOnId, shouldIgnoreVibrationLocked(alwaysOnVibration.callerInfo) == null ? this.mVibrationScaler.scale(prebakedSegment, alwaysOnVibration.callerInfo.attrs.getUsage()) : null);
            }
        }
    }

    public final Vibration.EndInfo startVibrationLocked(HalVibration halVibration) {
        Trace.traceBegin(8388608L, "startVibrationLocked");
        try {
            if (this.mInputDeviceDelegate.vibrateIfAvailable(halVibration.callerInfo, halVibration.getEffect())) {
                return new Vibration.EndInfo(Vibration.Status.FORWARDED_TO_INPUT_DEVICES);
            }
            VibrationStepConductor vibrationStepConductor = new VibrationStepConductor(halVibration, this.mVibrationSettings, this.mDeviceVibrationEffectAdapter, this.mVibrators, this.mVibrationThreadCallbacks);
            if (this.mCurrentVibration == null) {
                return startVibrationOnThreadLocked(vibrationStepConductor);
            }
            clearNextVibrationLocked(new Vibration.EndInfo(Vibration.Status.IGNORED_SUPERSEDED, halVibration.callerInfo));
            this.mNextVibration = vibrationStepConductor;
            Trace.traceEnd(8388608L);
            return null;
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public final Vibration.EndInfo startVibrationOnThreadLocked(VibrationStepConductor vibrationStepConductor) {
        Trace.traceBegin(8388608L, "startVibrationThreadLocked");
        try {
            HalVibration vibration = vibrationStepConductor.getVibration();
            int startAppOpModeLocked = startAppOpModeLocked(vibration.callerInfo);
            if (startAppOpModeLocked == 0) {
                Trace.asyncTraceBegin(8388608L, "vibration", 0);
                this.mCurrentVibration = vibrationStepConductor;
                if (this.mVibrationThread.runVibrationOnVibrationThread(vibrationStepConductor)) {
                    return null;
                }
                this.mCurrentVibration = null;
                return new Vibration.EndInfo(Vibration.Status.IGNORED_ERROR_SCHEDULING);
            }
            if (startAppOpModeLocked == 2) {
                Slog.w("VibratorManagerService", "Start AppOpsManager operation errored for uid " + vibration.callerInfo.uid);
                return new Vibration.EndInfo(Vibration.Status.IGNORED_ERROR_APP_OPS);
            }
            return new Vibration.EndInfo(Vibration.Status.IGNORED_APP_OPS);
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public final void endVibrationLocked(HalVibration halVibration, Vibration.EndInfo endInfo, boolean z) {
        halVibration.end(endInfo);
        Vibration.CallerInfo callerInfo = halVibration.callerInfo;
        logVibrationStatus(callerInfo.uid, callerInfo.attrs, endInfo.status);
        this.mVibratorManagerRecords.record(halVibration);
        if (z) {
            this.mFrameworkStatsLogger.writeVibrationReportedAsync(halVibration.getStatsInfo(SystemClock.uptimeMillis()));
        }
    }

    public final void endVibrationAndWriteStatsLocked(ExternalVibrationHolder externalVibrationHolder, Vibration.EndInfo endInfo) {
        externalVibrationHolder.end(endInfo);
        logVibrationStatus(externalVibrationHolder.externalVibration.getUid(), externalVibrationHolder.externalVibration.getVibrationAttributes(), endInfo.status);
        this.mVibratorManagerRecords.record(externalVibrationHolder);
        this.mFrameworkStatsLogger.writeVibrationReportedAsync(externalVibrationHolder.getStatsInfo(SystemClock.uptimeMillis()));
    }

    public final void logVibrationStatus(int i, VibrationAttributes vibrationAttributes, Vibration.Status status) {
        switch (AbstractC27852.$SwitchMap$com$android$server$vibrator$Vibration$Status[status.ordinal()]) {
            case 1:
                Slog.e("VibratorManagerService", "Ignoring incoming vibration as process with uid= " + i + " is background, attrs= " + vibrationAttributes);
                break;
            case 2:
                Slog.w("VibratorManagerService", "Would be an error: vibrate from uid " + i);
                break;
            case 3:
                Slog.d("VibratorManagerService", "Ignoring incoming vibration for current external vibration");
                break;
            case 4:
                Slog.d("VibratorManagerService", "Ignoring incoming vibration in favor of ongoing vibration with higher importance");
                break;
            case 5:
                Slog.d("VibratorManagerService", "Ignoring incoming vibration in favor of repeating vibration");
                break;
            case 6:
                Slog.d("VibratorManagerService", "Ignoring incoming vibration because of ringer mode, attrs=" + vibrationAttributes);
                break;
            case 7:
                Slog.d("VibratorManagerService", "Ignoring incoming vibration because it came from a virtual device, attrs= " + vibrationAttributes);
                break;
            default:
                Slog.d("VibratorManagerService", "Vibration for uid=" + i + " and with attrs=" + vibrationAttributes + " ended with status " + status);
                break;
        }
    }

    public final void reportFinishedVibrationLocked(Vibration.EndInfo endInfo) {
        Trace.traceBegin(8388608L, "reportFinishVibrationLocked");
        Trace.asyncTraceEnd(8388608L, "vibration", 0);
        try {
            HalVibration vibration = this.mCurrentVibration.getVibration();
            Slog.d("VibratorManagerService", "Reporting vibration " + vibration.f1733id + " finished with " + endInfo);
            endVibrationLocked(vibration, endInfo, false);
            finishAppOpModeLocked(vibration.callerInfo);
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public final void onSyncedVibrationComplete(long j) {
        synchronized (this.mLock) {
            VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
            if (vibrationStepConductor != null && vibrationStepConductor.getVibration().f1733id == j) {
                Slog.d("VibratorManagerService", "Synced vibration " + j + " complete, notifying thread");
                this.mCurrentVibration.notifySyncedVibrationComplete();
            }
        }
    }

    public final void onVibrationComplete(int i, long j) {
        synchronized (this.mLock) {
            VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
            if (vibrationStepConductor != null && vibrationStepConductor.getVibration().f1733id == j) {
                Slog.d("VibratorManagerService", "Vibration " + j + " on vibrator " + i + " complete, notifying thread");
                this.mCurrentVibration.notifyVibratorComplete(i);
            }
        }
    }

    public final Vibration.EndInfo shouldIgnoreVibrationForOngoingLocked(Vibration vibration) {
        Vibration.EndInfo shouldIgnoreVibrationForOngoing;
        ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
        if (externalVibrationHolder != null) {
            return shouldIgnoreVibrationForOngoing(vibration, externalVibrationHolder);
        }
        VibrationStepConductor vibrationStepConductor = this.mNextVibration;
        if (vibrationStepConductor != null && (shouldIgnoreVibrationForOngoing = shouldIgnoreVibrationForOngoing(vibration, vibrationStepConductor.getVibration())) != null) {
            return shouldIgnoreVibrationForOngoing;
        }
        VibrationStepConductor vibrationStepConductor2 = this.mCurrentVibration;
        if (vibrationStepConductor2 != null) {
            HalVibration vibration2 = vibrationStepConductor2.getVibration();
            if (!vibration2.hasEnded() && !this.mCurrentVibration.wasNotifiedToCancel()) {
                return shouldIgnoreVibrationForOngoing(vibration, vibration2);
            }
        }
        return null;
    }

    public static Vibration.EndInfo shouldIgnoreVibrationForOngoing(Vibration vibration, Vibration vibration2) {
        int vibrationImportance = getVibrationImportance(vibration);
        int vibrationImportance2 = getVibrationImportance(vibration2);
        if (vibrationImportance > vibrationImportance2) {
            return null;
        }
        if (vibrationImportance2 > vibrationImportance) {
            return new Vibration.EndInfo(Vibration.Status.IGNORED_FOR_HIGHER_IMPORTANCE, vibration2.callerInfo);
        }
        if (!vibration2.isRepeating() || vibration.isRepeating()) {
            return null;
        }
        return new Vibration.EndInfo(Vibration.Status.IGNORED_FOR_ONGOING, vibration2.callerInfo);
    }

    public static int getVibrationImportance(Vibration vibration) {
        return getSepVibrationImportance(vibration);
    }

    public final Vibration.EndInfo shouldIgnoreVibrationLocked(Vibration.CallerInfo callerInfo) {
        PackageManager packageManager;
        if (CoreRune.SYSFW_APP_SPEG && (packageManager = this.mContext.getPackageManager()) != null && packageManager.isSpeg(callerInfo.uid)) {
            Slog.i("SPEG", "Vibration is ignored for uid " + callerInfo.uid);
            return new Vibration.EndInfo(Vibration.Status.IGNORED_APP_OPS);
        }
        Vibration.Status shouldIgnoreVibration = this.mVibrationSettings.shouldIgnoreVibration(callerInfo);
        if (shouldIgnoreVibration != null) {
            return new Vibration.EndInfo(shouldIgnoreVibration);
        }
        int checkAppOpModeLocked = checkAppOpModeLocked(callerInfo);
        if (checkAppOpModeLocked == 0) {
            return null;
        }
        if (checkAppOpModeLocked == 2) {
            return new Vibration.EndInfo(Vibration.Status.IGNORED_ERROR_APP_OPS);
        }
        return new Vibration.EndInfo(Vibration.Status.IGNORED_APP_OPS);
    }

    public final boolean shouldCancelVibration(HalVibration halVibration, int i, IBinder iBinder) {
        return halVibration.callerToken == iBinder && shouldCancelVibration(halVibration.callerInfo.attrs, i);
    }

    public final boolean shouldCancelVibration(VibrationAttributes vibrationAttributes, int i) {
        return vibrationAttributes.getUsage() == 0 ? i == 0 || i == -1 : (vibrationAttributes.getUsage() & i) == vibrationAttributes.getUsage();
    }

    public final int checkAppOpModeLocked(Vibration.CallerInfo callerInfo) {
        int checkAudioOpNoThrow = this.mAppOps.checkAudioOpNoThrow(3, callerInfo.attrs.getAudioUsage(), callerInfo.uid, callerInfo.opPkg);
        int fixupAppOpModeLocked = fixupAppOpModeLocked(checkAudioOpNoThrow, callerInfo.attrs);
        if (checkAudioOpNoThrow != fixupAppOpModeLocked && fixupAppOpModeLocked == 0) {
            Slog.d("VibratorManagerService", "Bypassing DND for vibrate from uid " + callerInfo.uid);
        }
        return fixupAppOpModeLocked;
    }

    public final int startAppOpModeLocked(Vibration.CallerInfo callerInfo) {
        return fixupAppOpModeLocked(this.mAppOps.startOpNoThrow(3, callerInfo.uid, callerInfo.opPkg), callerInfo.attrs);
    }

    public final void finishAppOpModeLocked(Vibration.CallerInfo callerInfo) {
        this.mAppOps.finishOp(3, callerInfo.uid, callerInfo.opPkg);
    }

    public final void enforceUpdateAppOpsStatsPermission(int i) {
        if (i == Binder.getCallingUid() || Binder.getCallingPid() == Process.myPid()) {
            return;
        }
        this.mContext.enforcePermission("android.permission.UPDATE_APP_OPS_STATS", Binder.getCallingPid(), Binder.getCallingUid(), null);
    }

    public static boolean isEffectValid(CombinedVibration combinedVibration) {
        if (combinedVibration == null) {
            Slog.wtf("VibratorManagerService", "effect must not be null");
            return false;
        }
        try {
            combinedVibration.validate();
            return true;
        } catch (Exception e) {
            Slog.wtf("VibratorManagerService", "Encountered issue when verifying CombinedVibrationEffect.", e);
            return false;
        }
    }

    public final void fillVibrationFallbacks(HalVibration halVibration, CombinedVibration combinedVibration) {
        if (combinedVibration instanceof CombinedVibration.Mono) {
            fillVibrationFallbacks(halVibration, ((CombinedVibration.Mono) combinedVibration).getEffect());
            return;
        }
        int i = 0;
        if (combinedVibration instanceof CombinedVibration.Stereo) {
            SparseArray effects = ((CombinedVibration.Stereo) combinedVibration).getEffects();
            while (i < effects.size()) {
                fillVibrationFallbacks(halVibration, (VibrationEffect) effects.valueAt(i));
                i++;
            }
            return;
        }
        if (combinedVibration instanceof CombinedVibration.Sequential) {
            List effects2 = ((CombinedVibration.Sequential) combinedVibration).getEffects();
            while (i < effects2.size()) {
                fillVibrationFallbacks(halVibration, (CombinedVibration) effects2.get(i));
                i++;
            }
        }
    }

    public final void fillVibrationFallbacks(HalVibration halVibration, VibrationEffect vibrationEffect) {
        VibrationEffect.Composed composed = (VibrationEffect.Composed) vibrationEffect;
        int size = composed.getSegments().size();
        for (int i = 0; i < size; i++) {
            PrebakedSegment prebakedSegment = (VibrationEffectSegment) composed.getSegments().get(i);
            if (prebakedSegment instanceof PrebakedSegment) {
                PrebakedSegment prebakedSegment2 = prebakedSegment;
                VibrationEffect fallbackEffect = this.mVibrationSettings.getFallbackEffect(prebakedSegment2.getEffectId());
                if (prebakedSegment2.shouldFallback() && fallbackEffect != null) {
                    halVibration.addFallback(prebakedSegment2.getEffectId(), fallbackEffect);
                }
            }
        }
    }

    public final VibrationAttributes fixupVibrationAttributes(VibrationAttributes vibrationAttributes, CombinedVibration combinedVibration) {
        String str;
        if (vibrationAttributes == null) {
            vibrationAttributes = DEFAULT_ATTRIBUTES;
        }
        int usage = vibrationAttributes.getUsage();
        if (usage == 0 && combinedVibration != null && combinedVibration.isHapticFeedbackCandidate()) {
            usage = 18;
        }
        if (combinedVibration != null) {
            usage = fixupVibrationUsages(usage);
            Slog.d("VibratorManagerService", "converted usage = " + VibrationAttributes.usageToString(usage) + "(" + usage + ")");
            VibrationEffect.Composed composed = this.mComposed;
            if (composed != null) {
                if (composed.semGetMagnitudeType() == VibrationEffect.SemMagnitudeType.TYPE_MAX) {
                    str = "INTENSITY_MAX";
                } else {
                    str = this.mComposed.semGetMagnitudeType() == VibrationEffect.SemMagnitudeType.TYPE_MIN ? "INTENSITY_MIN" : null;
                }
                if (str != null) {
                    vibrationAttributes = new VibrationAttributes.Builder(vibrationAttributes).semAddTag(str).build();
                }
            }
        }
        int flags = vibrationAttributes.getFlags();
        if ((flags & 3) != 0 && !hasPermission("android.permission.WRITE_SECURE_SETTINGS") && !hasPermission("android.permission.MODIFY_PHONE_STATE") && !hasPermission("android.permission.MODIFY_AUDIO_ROUTING")) {
            flags &= -4;
        }
        return (usage == vibrationAttributes.getUsage() && flags == vibrationAttributes.getFlags()) ? vibrationAttributes : new VibrationAttributes.Builder(vibrationAttributes).setUsage(usage).setFlags(flags, vibrationAttributes.getFlags()).build();
    }

    public final SparseArray fixupAlwaysOnEffectsLocked(CombinedVibration combinedVibration) {
        SparseArray effects;
        Trace.traceBegin(8388608L, "fixupAlwaysOnEffectsLocked");
        try {
            if (combinedVibration instanceof CombinedVibration.Mono) {
                final VibrationEffect effect = ((CombinedVibration.Mono) combinedVibration).getEffect();
                effects = transformAllVibratorsLocked(new Function() { // from class: com.android.server.vibrator.VibratorManagerService$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        VibrationEffect lambda$fixupAlwaysOnEffectsLocked$2;
                        lambda$fixupAlwaysOnEffectsLocked$2 = VibratorManagerService.lambda$fixupAlwaysOnEffectsLocked$2(effect, (VibratorController) obj);
                        return lambda$fixupAlwaysOnEffectsLocked$2;
                    }
                });
            } else {
                if (!(combinedVibration instanceof CombinedVibration.Stereo)) {
                    return null;
                }
                effects = ((CombinedVibration.Stereo) combinedVibration).getEffects();
            }
            SparseArray sparseArray = new SparseArray();
            for (int i = 0; i < effects.size(); i++) {
                PrebakedSegment extractPrebakedSegment = extractPrebakedSegment((VibrationEffect) effects.valueAt(i));
                if (extractPrebakedSegment == null) {
                    Slog.e("VibratorManagerService", "Only prebaked effects supported for always-on.");
                    return null;
                }
                int keyAt = effects.keyAt(i);
                VibratorController vibratorController = (VibratorController) this.mVibrators.get(keyAt);
                if (vibratorController != null && vibratorController.hasCapability(64L)) {
                    sparseArray.put(keyAt, extractPrebakedSegment);
                }
            }
            if (sparseArray.size() == 0) {
                return null;
            }
            return sparseArray;
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public static PrebakedSegment extractPrebakedSegment(VibrationEffect vibrationEffect) {
        if (!(vibrationEffect instanceof VibrationEffect.Composed)) {
            return null;
        }
        VibrationEffect.Composed composed = (VibrationEffect.Composed) vibrationEffect;
        if (composed.getSegments().size() != 1) {
            return null;
        }
        PrebakedSegment prebakedSegment = (VibrationEffectSegment) composed.getSegments().get(0);
        if (prebakedSegment instanceof PrebakedSegment) {
            return prebakedSegment;
        }
        return null;
    }

    public final int fixupAppOpModeLocked(int i, VibrationAttributes vibrationAttributes) {
        if (i == 1 && vibrationAttributes.isFlagSet(1)) {
            return 0;
        }
        return i;
    }

    public final boolean hasPermission(String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    public final boolean shouldCancelOnScreenOffLocked(VibrationStepConductor vibrationStepConductor) {
        if (vibrationStepConductor == null) {
            return false;
        }
        HalVibration vibration = vibrationStepConductor.getVibration();
        return this.mVibrationSettings.shouldCancelVibrationOnScreenOff(vibration.callerInfo, vibration.stats.getCreateUptimeMillis());
    }

    public final void onAllVibratorsLocked(Consumer consumer) {
        for (int i = 0; i < this.mVibrators.size(); i++) {
            consumer.accept((VibratorController) this.mVibrators.valueAt(i));
        }
    }

    public final SparseArray transformAllVibratorsLocked(Function function) {
        SparseArray sparseArray = new SparseArray(this.mVibrators.size());
        for (int i = 0; i < this.mVibrators.size(); i++) {
            sparseArray.put(this.mVibrators.keyAt(i), function.apply((VibratorController) this.mVibrators.valueAt(i)));
        }
        return sparseArray;
    }

    class Injector {
        public NativeWrapper getNativeWrapper() {
            return new NativeWrapper();
        }

        public Handler createHandler(Looper looper) {
            return new Handler(looper);
        }

        public IBatteryStats getBatteryStatsService() {
            return IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
        }

        public VibratorFrameworkStatsLogger getFrameworkStatsLogger(Handler handler) {
            return new VibratorFrameworkStatsLogger(handler);
        }

        public VibratorController createVibratorController(int i, VibratorController.OnVibrationCompleteListener onVibrationCompleteListener) {
            return new VibratorController(i, onVibrationCompleteListener);
        }

        public void addService(String str, IBinder iBinder) {
            ServiceManager.addService(str, iBinder);
        }
    }

    public final class VibrationThreadCallbacks implements VibrationThread.VibratorManagerHooks {
        public VibrationThreadCallbacks() {
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public boolean prepareSyncedVibration(long j, int[] iArr) {
            if ((VibratorManagerService.this.mCapabilities & j) != j) {
                return false;
            }
            return VibratorManagerService.this.mNativeWrapper.prepareSynced(iArr);
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public boolean triggerSyncedVibration(long j) {
            return VibratorManagerService.this.mNativeWrapper.triggerSynced(j);
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public void cancelSyncedVibration() {
            VibratorManagerService.this.mNativeWrapper.cancelSynced();
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public void noteVibratorOn(int i, long j) {
            if (j <= 0) {
                return;
            }
            if (j == Long.MAX_VALUE) {
                j = 5000;
            }
            try {
                VibratorManagerService.this.mBatteryStatsService.noteVibratorOn(i, j);
                VibratorManagerService.this.mFrameworkStatsLogger.writeVibratorStateOnAsync(i, j);
            } catch (RemoteException e) {
                Slog.e("VibratorManagerService", "Error logging VibratorStateChanged to ON", e);
            }
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public void noteVibratorOff(int i) {
            try {
                VibratorManagerService.this.mBatteryStatsService.noteVibratorOff(i);
                VibratorManagerService.this.mFrameworkStatsLogger.writeVibratorStateOffAsync(i);
            } catch (RemoteException e) {
                Slog.e("VibratorManagerService", "Error logging VibratorStateChanged to OFF", e);
            }
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public void onVibrationCompleted(long j, Vibration.EndInfo endInfo) {
            Slog.d("VibratorManagerService", "Vibration " + j + " finished with " + endInfo);
            synchronized (VibratorManagerService.this.mLock) {
                if (VibratorManagerService.this.mCurrentVibration != null && VibratorManagerService.this.mCurrentVibration.getVibration().f1733id == j) {
                    VibratorManagerService.this.reportFinishedVibrationLocked(endInfo);
                }
            }
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public void onVibrationThreadReleased(long j) {
            Slog.d("VibratorManagerService", "VibrationThread released after finished vibration");
            synchronized (VibratorManagerService.this.mLock) {
                Slog.d("VibratorManagerService", "Processing VibrationThread released callback");
                if (Build.IS_DEBUGGABLE && VibratorManagerService.this.mCurrentVibration != null && VibratorManagerService.this.mCurrentVibration.getVibration().f1733id != j) {
                    Slog.wtf("VibratorManagerService", TextUtils.formatSimple("VibrationId mismatch on release. expected=%d, released=%d", new Object[]{Long.valueOf(VibratorManagerService.this.mCurrentVibration.getVibration().f1733id), Long.valueOf(j)}));
                }
                if (VibratorManagerService.this.mCurrentVibration != null) {
                    VibratorManagerService.this.mFrameworkStatsLogger.writeVibrationReportedAsync(VibratorManagerService.this.mCurrentVibration.getVibration().getStatsInfo(SystemClock.uptimeMillis()));
                    VibratorManagerService.this.mCurrentVibration = null;
                }
                if (VibratorManagerService.this.mNextVibration != null) {
                    VibrationStepConductor vibrationStepConductor = VibratorManagerService.this.mNextVibration;
                    VibratorManagerService.this.mNextVibration = null;
                    Vibration.EndInfo startVibrationOnThreadLocked = VibratorManagerService.this.startVibrationOnThreadLocked(vibrationStepConductor);
                    if (startVibrationOnThreadLocked != null) {
                        VibratorManagerService.this.endVibrationLocked(vibrationStepConductor.getVibration(), startVibrationOnThreadLocked, true);
                    }
                }
            }
        }
    }

    public final class VibrationCompleteListener implements VibratorController.OnVibrationCompleteListener, OnSyncedVibrationCompleteListener {
        public WeakReference mServiceRef;

        public VibrationCompleteListener(VibratorManagerService vibratorManagerService) {
            this.mServiceRef = new WeakReference(vibratorManagerService);
        }

        @Override // com.android.server.vibrator.VibratorManagerService.OnSyncedVibrationCompleteListener
        public void onComplete(long j) {
            VibratorManagerService vibratorManagerService = (VibratorManagerService) this.mServiceRef.get();
            if (vibratorManagerService != null) {
                vibratorManagerService.onSyncedVibrationComplete(j);
            }
        }

        @Override // com.android.server.vibrator.VibratorController.OnVibrationCompleteListener
        public void onComplete(int i, long j) {
            VibratorManagerService vibratorManagerService = (VibratorManagerService) this.mServiceRef.get();
            if (vibratorManagerService != null) {
                vibratorManagerService.onVibrationComplete(i, j);
            }
        }
    }

    public final class AlwaysOnVibration {
        public final int alwaysOnId;
        public final Vibration.CallerInfo callerInfo;
        public final SparseArray effects;

        public AlwaysOnVibration(int i, Vibration.CallerInfo callerInfo, SparseArray sparseArray) {
            this.alwaysOnId = i;
            this.callerInfo = callerInfo;
            this.effects = sparseArray;
        }
    }

    public final class ExternalVibrationHolder extends Vibration implements IBinder.DeathRecipient {
        public final ExternalVibration externalVibration;
        public Vibration.Status mStatus;
        public int scale;

        public ExternalVibrationHolder(ExternalVibration externalVibration) {
            super(externalVibration.getToken(), new Vibration.CallerInfo(externalVibration.getVibrationAttributes(), externalVibration.getUid(), -1, externalVibration.getPackage(), null));
            this.externalVibration = externalVibration;
            this.scale = 0;
            this.mStatus = Vibration.Status.RUNNING;
        }

        public void mute() {
            this.externalVibration.mute();
        }

        public void linkToDeath() {
            this.externalVibration.linkToDeath(this);
        }

        public void unlinkToDeath() {
            this.externalVibration.unlinkToDeath(this);
        }

        public boolean isHoldingSameVibration(ExternalVibration externalVibration) {
            return this.externalVibration.equals(externalVibration);
        }

        public void end(Vibration.EndInfo endInfo) {
            if (this.mStatus != Vibration.Status.RUNNING) {
                return;
            }
            this.mStatus = endInfo.status;
            this.stats.reportEnded(endInfo.endedBy);
            if (this.stats.hasStarted()) {
                VibrationStats vibrationStats = this.stats;
                vibrationStats.reportVibratorOn(vibrationStats.getEndUptimeMillis() - this.stats.getStartUptimeMillis());
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (VibratorManagerService.this.mLock) {
                if (VibratorManagerService.this.mCurrentExternalVibration != null) {
                    Slog.d("VibratorManagerService", "External vibration finished because binder died");
                    VibratorManagerService.this.endExternalVibrateLocked(new Vibration.EndInfo(Vibration.Status.CANCELLED_BINDER_DIED), false);
                }
            }
        }

        public Vibration.DebugInfo getDebugInfo() {
            return new Vibration.DebugInfo(this.mStatus, this.stats, null, null, this.scale, this.callerInfo);
        }

        public VibrationStats.StatsInfo getStatsInfo(long j) {
            return new VibrationStats.StatsInfo(this.externalVibration.getUid(), 3, this.externalVibration.getVibrationAttributes().getUsage(), this.mStatus, this.stats, j);
        }

        @Override // com.android.server.vibrator.Vibration
        public boolean isRepeating() {
            int usage = this.externalVibration.getVibrationAttributes().getUsage();
            return usage == 33 || usage == 17;
        }
    }

    public class NativeWrapper {
        public long mNativeServicePtr = 0;

        public void init(OnSyncedVibrationCompleteListener onSyncedVibrationCompleteListener) {
            this.mNativeServicePtr = VibratorManagerService.nativeInit(onSyncedVibrationCompleteListener);
            long nativeGetFinalizer = VibratorManagerService.nativeGetFinalizer();
            if (nativeGetFinalizer != 0) {
                NativeAllocationRegistry.createMalloced(VibratorManagerService.class.getClassLoader(), nativeGetFinalizer).registerNativeAllocation(this, this.mNativeServicePtr);
            }
        }

        public long getCapabilities() {
            return VibratorManagerService.nativeGetCapabilities(this.mNativeServicePtr);
        }

        public int[] getVibratorIds() {
            return VibratorManagerService.nativeGetVibratorIds(this.mNativeServicePtr);
        }

        public boolean prepareSynced(int[] iArr) {
            return VibratorManagerService.nativePrepareSynced(this.mNativeServicePtr, iArr);
        }

        public boolean triggerSynced(long j) {
            return VibratorManagerService.nativeTriggerSynced(this.mNativeServicePtr, j);
        }

        public void cancelSynced() {
            VibratorManagerService.nativeCancelSynced(this.mNativeServicePtr);
        }
    }

    public final class VibratorManagerRecords {
        public final int mPreviousVibrationsLimit;
        public final SparseArray mPreviousVibrations = new SparseArray();
        public final LinkedList mPreviousExternalVibrations = new LinkedList();

        public VibratorManagerRecords(int i) {
            this.mPreviousVibrationsLimit = i;
        }

        public synchronized void record(HalVibration halVibration) {
            int usage = halVibration.callerInfo.attrs.getUsage();
            if (!this.mPreviousVibrations.contains(usage)) {
                this.mPreviousVibrations.put(usage, new LinkedList());
            }
            record((LinkedList) this.mPreviousVibrations.get(usage), halVibration.getDebugInfo());
        }

        public synchronized void record(ExternalVibrationHolder externalVibrationHolder) {
            record(this.mPreviousExternalVibrations, externalVibrationHolder.getDebugInfo());
        }

        public synchronized void record(LinkedList linkedList, Vibration.DebugInfo debugInfo) {
            if (linkedList.size() > this.mPreviousVibrationsLimit) {
                linkedList.removeFirst();
            }
            linkedList.addLast(debugInfo);
        }

        public synchronized void dumpText(PrintWriter printWriter) {
            for (int i = 0; i < this.mPreviousVibrations.size(); i++) {
                printWriter.println();
                printWriter.print("  Previous vibrations for usage ");
                printWriter.print(VibrationAttributes.usageToString(this.mPreviousVibrations.keyAt(i)));
                printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                Iterator it = ((LinkedList) this.mPreviousVibrations.valueAt(i)).iterator();
                while (it.hasNext()) {
                    printWriter.println("    " + ((Vibration.DebugInfo) it.next()));
                }
            }
            printWriter.println();
            printWriter.println("  Previous external vibrations:");
            Iterator it2 = this.mPreviousExternalVibrations.iterator();
            while (it2.hasNext()) {
                printWriter.println("    " + ((Vibration.DebugInfo) it2.next()));
            }
        }

        public synchronized void dumpProto(ProtoOutputStream protoOutputStream) {
            for (int i = 0; i < this.mPreviousVibrations.size(); i++) {
                int keyAt = this.mPreviousVibrations.keyAt(i);
                long j = keyAt != 17 ? keyAt != 33 ? keyAt != 49 ? 2246267895824L : 2246267895822L : 2246267895821L : 2246267895823L;
                Iterator it = ((LinkedList) this.mPreviousVibrations.valueAt(i)).iterator();
                while (it.hasNext()) {
                    ((Vibration.DebugInfo) it.next()).dumpProto(protoOutputStream, j);
                }
            }
            Iterator it2 = this.mPreviousExternalVibrations.iterator();
            while (it2.hasNext()) {
                ((Vibration.DebugInfo) it2.next()).dumpProto(protoOutputStream, 2246267895825L);
            }
        }
    }

    public final void clearNextVibrationLocked(Vibration.EndInfo endInfo) {
        if (this.mNextVibration != null) {
            Slog.d("VibratorManagerService", "Dropping pending vibration " + this.mNextVibration.getVibration().f1733id + " with end info: " + endInfo);
            endVibrationLocked(this.mNextVibration.getVibration(), endInfo, true);
            this.mNextVibration = null;
        }
    }

    public final void endExternalVibrateLocked(Vibration.EndInfo endInfo, boolean z) {
        Trace.traceBegin(8388608L, "endExternalVibrateLocked");
        try {
            ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
            if (externalVibrationHolder == null) {
                return;
            }
            externalVibrationHolder.unlinkToDeath();
            if (!z) {
                setExternalControl(false, this.mCurrentExternalVibration.stats);
            }
            endVibrationAndWriteStatsLocked(this.mCurrentExternalVibration, endInfo);
            this.mCurrentExternalVibration = null;
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    final class ExternalVibratorService extends IExternalVibratorService.Stub {
        public boolean shouldIgnoreExternalVibrationLocked(int i, int i2, int i3, int i4) {
            return false;
        }

        public ExternalVibratorService() {
        }

        public int onExternalVibrationStart(ExternalVibration externalVibration) {
            boolean z;
            boolean z2;
            if (!VibRune.SUPPORT_ACH) {
                return -100;
            }
            if (ActivityManager.checkComponentPermission("android.permission.VIBRATE", externalVibration.getUid(), -1, true) != 0) {
                Slog.w("VibratorManagerService", "pkg=" + externalVibration.getPackage() + ", uid=" + externalVibration.getUid() + " tried to play externally controlled vibration without VIBRATE permission, ignoring.");
                return -100;
            }
            ExternalVibrationHolder externalVibrationHolder = new ExternalVibrationHolder(externalVibration);
            VibrationAttributes fixupVibrationAttributes = VibratorManagerService.this.fixupVibrationAttributes(externalVibration.getVibrationAttributes(), null);
            if (fixupVibrationAttributes.isFlagSet(4)) {
                VibratorManagerService.this.mVibrationSettings.update();
            }
            synchronized (VibratorManagerService.this.mLock) {
                Vibration.EndInfo shouldIgnoreVibrationLocked = VibratorManagerService.this.shouldIgnoreVibrationLocked(externalVibrationHolder.callerInfo);
                if (shouldIgnoreVibrationLocked == null && VibratorManagerService.this.mCurrentExternalVibration != null && VibratorManagerService.this.mCurrentExternalVibration.isHoldingSameVibration(externalVibration)) {
                    return VibratorManagerService.this.mCurrentExternalVibration.scale;
                }
                if (shouldIgnoreVibrationLocked == null) {
                    shouldIgnoreVibrationLocked = VibratorManagerService.this.shouldIgnoreVibrationForOngoingLocked(externalVibrationHolder);
                }
                if (shouldIgnoreVibrationLocked != null) {
                    externalVibrationHolder.scale = -100;
                    VibratorManagerService.this.endVibrationAndWriteStatsLocked(externalVibrationHolder, shouldIgnoreVibrationLocked);
                    return externalVibrationHolder.scale;
                }
                if (VibratorManagerService.this.mCurrentExternalVibration == null) {
                    if (VibratorManagerService.this.mCurrentVibration != null) {
                        externalVibrationHolder.stats.reportInterruptedAnotherVibration(VibratorManagerService.this.mCurrentVibration.getVibration().callerInfo);
                        VibratorManagerService.this.clearNextVibrationLocked(new Vibration.EndInfo(Vibration.Status.IGNORED_FOR_EXTERNAL, externalVibrationHolder.callerInfo));
                        VibratorManagerService.this.mCurrentVibration.notifyCancelled(new Vibration.EndInfo(Vibration.Status.CANCELLED_SUPERSEDED, externalVibrationHolder.callerInfo), true);
                        z2 = true;
                        z = false;
                    } else {
                        z2 = false;
                        z = false;
                    }
                } else {
                    VibratorManagerService.this.mCurrentExternalVibration.mute();
                    externalVibrationHolder.stats.reportInterruptedAnotherVibration(VibratorManagerService.this.mCurrentExternalVibration.callerInfo);
                    VibratorManagerService.this.endExternalVibrateLocked(new Vibration.EndInfo(Vibration.Status.CANCELLED_SUPERSEDED, externalVibrationHolder.callerInfo), true);
                    z = true;
                    z2 = false;
                }
                VibratorManagerService.this.mCurrentExternalVibration = externalVibrationHolder;
                externalVibrationHolder.linkToDeath();
                externalVibrationHolder.scale = VibratorManagerService.this.mVibrationScaler.getExternalVibrationScale(fixupVibrationAttributes.getUsage());
                if (z2 && !VibratorManagerService.this.mVibrationThread.waitForThreadIdle(5000L)) {
                    Slog.e("VibratorManagerService", "Timed out waiting for vibration to cancel");
                    synchronized (VibratorManagerService.this.mLock) {
                        VibratorManagerService.this.endExternalVibrateLocked(new Vibration.EndInfo(Vibration.Status.IGNORED_ERROR_CANCELLING), false);
                    }
                    return -100;
                }
                if (!z) {
                    Slog.d("VibratorManagerService", "Vibrator going under external control.");
                    VibratorManagerService.this.setExternalControl(true, externalVibrationHolder.stats);
                }
                if (VibRune.SUPPORT_ACH) {
                    int currentMagnitude = VibratorManagerService.this.mVibrationSettings.getCurrentMagnitude(externalVibration.getVibrationAttributes().getUsage());
                    Slog.d("VibratorManagerService", "getCurrentMagnitude : " + currentMagnitude);
                    if (currentMagnitude == 0) {
                        externalVibrationHolder.scale = -100;
                    } else {
                        VibratorController vibratorController = (VibratorController) VibratorManagerService.this.mVibrators.get(0);
                        if (vibratorController != null) {
                            vibratorController.setIntensity(currentMagnitude);
                        }
                        externalVibrationHolder.scale = 0;
                    }
                    VibratorManagerService.this.reportVibrationInfo(externalVibration);
                }
                Slog.d("VibratorManagerService", "Playing external vibration: " + externalVibration);
                externalVibrationHolder.stats.reportStarted();
                boolean z3 = VibRune.SUPPORT_ACH;
                return externalVibrationHolder.scale;
            }
        }

        public void onExternalVibrationStop(ExternalVibration externalVibration) {
            synchronized (VibratorManagerService.this.mLock) {
                if (VibratorManagerService.this.mCurrentExternalVibration != null && VibratorManagerService.this.mCurrentExternalVibration.isHoldingSameVibration(externalVibration)) {
                    Slog.d("VibratorManagerService", "Stopping external vibration: " + externalVibration);
                    if (externalVibration.isRepeating()) {
                        Log.secD("VibratorManagerService", "Ach repeat vibration ended");
                    }
                    VibratorManagerService.this.endExternalVibrateLocked(new Vibration.EndInfo(Vibration.Status.FINISHED), false);
                }
            }
        }
    }

    public final class VibratorManagerShellCommand extends ShellCommand {
        public final IBinder mShellCallbacksToken;

        public final class CommonOptions {
            public boolean background;
            public String description;
            public boolean force;
            public String pkgName;

            public CommonOptions() {
                this.force = false;
                this.description = "Shell command";
                this.background = false;
                this.pkgName = "com.android.shell";
                while (true) {
                    String peekNextArg = VibratorManagerShellCommand.this.peekNextArg();
                    if (peekNextArg != null) {
                        switch (peekNextArg) {
                            case "-B":
                                VibratorManagerShellCommand.this.getNextArgRequired();
                                this.background = true;
                                break;
                            case "-d":
                                VibratorManagerShellCommand.this.getNextArgRequired();
                                this.description = VibratorManagerShellCommand.this.getNextArgRequired();
                                break;
                            case "-f":
                                VibratorManagerShellCommand.this.getNextArgRequired();
                                this.force = true;
                                break;
                            case "-p":
                                VibratorManagerShellCommand.this.getNextArgRequired();
                                this.pkgName = VibratorManagerShellCommand.this.getNextArgRequired();
                                break;
                            default:
                                return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        public VibratorManagerShellCommand(IBinder iBinder) {
            this.mShellCallbacksToken = iBinder;
        }

        public int onCommand(String str) {
            Trace.traceBegin(8388608L, "onCommand " + str);
            try {
                return "list".equals(str) ? runListVibrators() : "synced".equals(str) ? runMono() : "combined".equals(str) ? runStereo() : "sequential".equals(str) ? runSequential() : "cancel".equals(str) ? runCancel() : handleDefaultCommands(str);
            } finally {
                Trace.traceEnd(8388608L);
            }
        }

        public final int runListVibrators() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            try {
                if (VibratorManagerService.this.mVibratorIds.length == 0) {
                    outPrintWriter.println("No vibrator found");
                } else {
                    for (int i : VibratorManagerService.this.mVibratorIds) {
                        outPrintWriter.println(i);
                    }
                }
                outPrintWriter.println("");
                outPrintWriter.close();
                return 0;
            } catch (Throwable th) {
                if (outPrintWriter != null) {
                    try {
                        outPrintWriter.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final void runVibrate(CommonOptions commonOptions, CombinedVibration combinedVibration) {
            HalVibration vibrateInternal = VibratorManagerService.this.vibrateInternal(Binder.getCallingUid(), 0, commonOptions.pkgName, combinedVibration, createVibrationAttributes(commonOptions), commonOptions.description, commonOptions.background ? VibratorManagerService.this : this.mShellCallbacksToken);
            if (vibrateInternal == null || commonOptions.background) {
                return;
            }
            try {
                vibrateInternal.waitForEnd();
            } catch (InterruptedException unused) {
            }
        }

        public final int runMono() {
            runVibrate(new CommonOptions(), CombinedVibration.createParallel(nextEffect()));
            return 0;
        }

        public final int runStereo() {
            CommonOptions commonOptions = new CommonOptions();
            CombinedVibration.ParallelCombination startParallel = CombinedVibration.startParallel();
            while ("-v".equals(getNextOption())) {
                startParallel.addVibrator(Integer.parseInt(getNextArgRequired()), nextEffect());
            }
            runVibrate(commonOptions, startParallel.combine());
            return 0;
        }

        public final int runSequential() {
            CommonOptions commonOptions = new CommonOptions();
            CombinedVibration.SequentialCombination startSequential = CombinedVibration.startSequential();
            while ("-v".equals(getNextOption())) {
                startSequential.addNext(Integer.parseInt(getNextArgRequired()), nextEffect());
            }
            runVibrate(commonOptions, startSequential.combine());
            return 0;
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.IBinder, com.android.server.vibrator.VibratorManagerService] */
        public final int runCancel() {
            ?? r1 = VibratorManagerService.this;
            r1.cancelVibrate(-1, r1);
            return 0;
        }

        public final VibrationEffect nextEffect() {
            VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
            while (true) {
                String peekNextArg = peekNextArg();
                if (peekNextArg != null) {
                    if ("oneshot".equals(peekNextArg)) {
                        addOneShotToComposition(startComposition);
                    } else if ("waveform".equals(peekNextArg)) {
                        addWaveformToComposition(startComposition);
                    } else if ("prebaked".equals(peekNextArg)) {
                        addPrebakedToComposition(startComposition);
                    } else {
                        if (!"primitives".equals(peekNextArg)) {
                            break;
                        }
                        addPrimitivesToComposition(startComposition);
                    }
                } else {
                    break;
                }
            }
            return startComposition.compose();
        }

        public final void addOneShotToComposition(VibrationEffect.Composition composition) {
            getNextArgRequired();
            boolean z = false;
            int i = 0;
            while (true) {
                String nextOption = getNextOption();
                if (nextOption == null) {
                    break;
                }
                if ("-a".equals(nextOption)) {
                    z = true;
                } else if ("-w".equals(nextOption)) {
                    i = Integer.parseInt(getNextArgRequired());
                }
            }
            long parseLong = Long.parseLong(getNextArgRequired());
            int parseInt = z ? Integer.parseInt(getNextArgRequired()) : -1;
            composition.addOffDuration(Duration.ofMillis(i));
            composition.addEffect(VibrationEffect.createOneShot(parseLong, parseInt));
        }

        public final void addWaveformToComposition(VibrationEffect.Composition composition) {
            Duration duration;
            Duration ofMillis;
            getNextArgRequired();
            int i = -1;
            boolean z = false;
            boolean z2 = false;
            int i2 = 0;
            boolean z3 = false;
            while (true) {
                String nextOption = getNextOption();
                if (nextOption == null) {
                    break;
                }
                if ("-a".equals(nextOption)) {
                    z = true;
                } else if ("-r".equals(nextOption)) {
                    i = Integer.parseInt(getNextArgRequired());
                } else if ("-w".equals(nextOption)) {
                    i2 = Integer.parseInt(getNextArgRequired());
                } else if ("-f".equals(nextOption)) {
                    z2 = true;
                } else if ("-c".equals(nextOption)) {
                    z3 = true;
                }
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            while (true) {
                String peekNextArg = peekNextArg();
                if (peekNextArg == null) {
                    break;
                }
                try {
                    arrayList.add(Integer.valueOf(Integer.parseInt(peekNextArg)));
                    getNextArgRequired();
                    if (z) {
                        arrayList2.add(Float.valueOf(Float.parseFloat(getNextArgRequired()) / 255.0f));
                    } else {
                        arrayList2.add(Float.valueOf(f));
                        f = 1.0f - f;
                    }
                    if (z2) {
                        arrayList3.add(Float.valueOf(Float.parseFloat(getNextArgRequired())));
                    }
                } catch (NumberFormatException unused) {
                }
            }
            composition.addOffDuration(Duration.ofMillis(i2));
            VibrationEffect.WaveformBuilder startWaveform = VibrationEffect.startWaveform();
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                if (z3) {
                    duration = Duration.ofMillis(((Integer) arrayList.get(i3)).intValue());
                } else {
                    duration = Duration.ZERO;
                }
                if (z3) {
                    ofMillis = Duration.ZERO;
                } else {
                    ofMillis = Duration.ofMillis(((Integer) arrayList.get(i3)).intValue());
                }
                if (z2) {
                    startWaveform.addTransition(duration, VibrationEffect.VibrationParameter.targetAmplitude(((Float) arrayList2.get(i3)).floatValue()), VibrationEffect.VibrationParameter.targetFrequency(((Float) arrayList3.get(i3)).floatValue()));
                } else {
                    startWaveform.addTransition(duration, VibrationEffect.VibrationParameter.targetAmplitude(((Float) arrayList2.get(i3)).floatValue()));
                }
                if (!ofMillis.isZero()) {
                    startWaveform.addSustain(ofMillis);
                }
                if (i3 > 0 && i3 == i) {
                    composition.addEffect(startWaveform.build());
                    if (z2) {
                        startWaveform = VibrationEffect.startWaveform(VibrationEffect.VibrationParameter.targetAmplitude(((Float) arrayList2.get(i3)).floatValue()), VibrationEffect.VibrationParameter.targetFrequency(((Float) arrayList3.get(i3)).floatValue()));
                    } else {
                        startWaveform = VibrationEffect.startWaveform(VibrationEffect.VibrationParameter.targetAmplitude(((Float) arrayList2.get(i3)).floatValue()));
                    }
                }
            }
            if (i < 0) {
                composition.addEffect(startWaveform.build());
            } else {
                composition.repeatEffectIndefinitely(startWaveform.build());
            }
        }

        public final void addPrebakedToComposition(VibrationEffect.Composition composition) {
            getNextArgRequired();
            int i = 0;
            boolean z = false;
            while (true) {
                String nextOption = getNextOption();
                if (nextOption != null) {
                    if ("-b".equals(nextOption)) {
                        z = true;
                    } else if ("-w".equals(nextOption)) {
                        i = Integer.parseInt(getNextArgRequired());
                    }
                } else {
                    int parseInt = Integer.parseInt(getNextArgRequired());
                    composition.addOffDuration(Duration.ofMillis(i));
                    composition.addEffect(VibrationEffect.get(parseInt, z));
                    return;
                }
            }
        }

        public final void addPrimitivesToComposition(VibrationEffect.Composition composition) {
            int i;
            getNextArgRequired();
            while (true) {
                String peekNextArg = peekNextArg();
                if (peekNextArg == null) {
                    return;
                }
                if ("-w".equals(peekNextArg)) {
                    getNextArgRequired();
                    i = Integer.parseInt(getNextArgRequired());
                    peekNextArg = peekNextArg();
                } else {
                    i = 0;
                }
                try {
                    composition.addPrimitive(Integer.parseInt(peekNextArg), 1.0f, i);
                    getNextArgRequired();
                } catch (NullPointerException | NumberFormatException unused) {
                    return;
                }
            }
        }

        public final VibrationAttributes createVibrationAttributes(CommonOptions commonOptions) {
            return new VibrationAttributes.Builder().setFlags(commonOptions.force ? 1 : 0).setUsage(18).build();
        }

        public void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            try {
                outPrintWriter.println("Vibrator Manager commands:");
                outPrintWriter.println("  help");
                outPrintWriter.println("    Prints this help text.");
                outPrintWriter.println("");
                outPrintWriter.println("  list");
                outPrintWriter.println("    Prints the id of device vibrators. This does not include any ");
                outPrintWriter.println("    connected input device.");
                outPrintWriter.println("  synced [options] <effect>...");
                outPrintWriter.println("    Vibrates effect on all vibrators in sync.");
                outPrintWriter.println("  combined [options] (-v <vibrator-id> <effect>...)...");
                outPrintWriter.println("    Vibrates different effects on each vibrator in sync.");
                outPrintWriter.println("  sequential [options] (-v <vibrator-id> <effect>...)...");
                outPrintWriter.println("    Vibrates different effects on each vibrator in sequence.");
                outPrintWriter.println("  cancel");
                outPrintWriter.println("    Cancels any active vibration");
                outPrintWriter.println("");
                outPrintWriter.println("Effect commands:");
                outPrintWriter.println("  oneshot [-w delay] [-a] <duration> [<amplitude>]");
                outPrintWriter.println("    Vibrates for duration milliseconds; ignored when device is on ");
                outPrintWriter.println("    DND (Do Not Disturb) mode; touch feedback strength user setting ");
                outPrintWriter.println("    will be used to scale amplitude.");
                outPrintWriter.println("    If -w is provided, the effect will be played after the specified");
                outPrintWriter.println("    wait time in milliseconds.");
                outPrintWriter.println("    If -a is provided, the command accepts a second argument for ");
                outPrintWriter.println("    amplitude, in a scale of 1-255.");
                outPrintWriter.print("  waveform [-w delay] [-r index] [-a] [-f] [-c] ");
                outPrintWriter.println("(<duration> [<amplitude>] [<frequency>])...");
                outPrintWriter.println("    Vibrates for durations and amplitudes in list; ignored when ");
                outPrintWriter.println("    device is on DND (Do Not Disturb) mode; touch feedback strength ");
                outPrintWriter.println("    user setting will be used to scale amplitude.");
                outPrintWriter.println("    If -w is provided, the effect will be played after the specified");
                outPrintWriter.println("    wait time in milliseconds.");
                outPrintWriter.println("    If -r is provided, the waveform loops back to the specified");
                outPrintWriter.println("    index (e.g. 0 loops from the beginning)");
                outPrintWriter.println("    If -a is provided, the command expects amplitude to follow each");
                outPrintWriter.println("    duration; otherwise, it accepts durations only and alternates");
                outPrintWriter.println("    off/on");
                outPrintWriter.println("    If -f is provided, the command expects frequency to follow each");
                outPrintWriter.println("    amplitude or duration; otherwise, it uses resonant frequency");
                outPrintWriter.println("    If -c is provided, the waveform is continuous and will ramp");
                outPrintWriter.println("    between values; otherwise each entry is a fixed step.");
                outPrintWriter.println("    Duration is in milliseconds; amplitude is a scale of 1-255;");
                outPrintWriter.println("    frequency is an absolute value in hertz;");
                outPrintWriter.println("  prebaked [-w delay] [-b] <effect-id>");
                outPrintWriter.println("    Vibrates with prebaked effect; ignored when device is on DND ");
                outPrintWriter.println("    (Do Not Disturb) mode; touch feedback strength user setting ");
                outPrintWriter.println("    will be used to scale amplitude.");
                outPrintWriter.println("    If -w is provided, the effect will be played after the specified");
                outPrintWriter.println("    wait time in milliseconds.");
                outPrintWriter.println("    If -b is provided, the prebaked fallback effect will be played if");
                outPrintWriter.println("    the device doesn't support the given effect-id.");
                outPrintWriter.println("  primitives ([-w delay] <primitive-id>)...");
                outPrintWriter.println("    Vibrates with a composed effect; ignored when device is on DND ");
                outPrintWriter.println("    (Do Not Disturb) mode; touch feedback strength user setting ");
                outPrintWriter.println("    will be used to scale primitive intensities.");
                outPrintWriter.println("    If -w is provided, the next primitive will be played after the ");
                outPrintWriter.println("    specified wait time in milliseconds.");
                outPrintWriter.println("");
                outPrintWriter.println("Common Options:");
                outPrintWriter.println("  -f");
                outPrintWriter.println("    Force. Ignore Do Not Disturb setting.");
                outPrintWriter.println("  -B");
                outPrintWriter.println("    Run in the background; without this option the shell cmd will");
                outPrintWriter.println("    block until the vibration has completed.");
                outPrintWriter.println("  -d <description>");
                outPrintWriter.println("    Add description to the vibration.");
                outPrintWriter.println("  -P <package>");
                outPrintWriter.println("    Add calling package to the vibration.");
                outPrintWriter.println("");
                outPrintWriter.close();
            } catch (Throwable th) {
                if (outPrintWriter != null) {
                    try {
                        outPrintWriter.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    public final void onCustomSystemReady() {
        this.mVibratorHelper.loadPatternInfo(this.mContext);
        this.mVibratorHelper.loadIndexDurationInfo();
        VibRune.SET_HYBRID_HAPTIC(hasFeature("HYBRID_PATTERN_COMMON_INPUTFF"));
        VibratorHelper.setIsFrequencySupported(isSupportFrequencyControl());
        VibratorHelper.setIsHapticEngineSupported(isSupportHapticEngine());
        VibratorHelper.setMotorType(getSupportedMotorType());
    }

    public final void bootCompleteReady() {
        this.mVibrationSettings.bootCompleteReady();
    }

    public VibratorController getDefaultVibratorController() {
        return (VibratorController) this.mVibrators.get(0);
    }

    public final void registerCustomIntent(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("com.sec.media.action.VIBRTOR_LOGGING");
        intentFilter.addAction("com.android.launcher3.quickstep.closeall");
        context.registerReceiver(this.mSamsungReceiver, intentFilter);
    }

    public class SamsungBroadcastReceiver extends BroadcastReceiver {
        public SamsungBroadcastReceiver() {
        }

        public void recoverService() {
            VibratorManagerService.this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.vibrator.VibratorManagerService$SamsungBroadcastReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VibratorManagerService.SamsungBroadcastReceiver.this.lambda$recoverService$1();
                }
            }, 500L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$recoverService$1() {
            synchronized (VibratorManagerService.this.mLock) {
                VibratorManagerService.this.cancelCurrentVibration(Vibration.Status.CANCELLED_SERVICE_RECOVERED);
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED".equals(action)) {
                if (VibratorManagerService.this.mGoodCatchManager == null) {
                    VibratorManagerService.this.mGoodCatchManager = new GoodCatchManager(VibratorManagerService.this.mContext, "VibratorService");
                    return;
                }
                return;
            }
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                VibratorManagerService vibratorManagerService = VibratorManagerService.this;
                vibratorManagerService.mHqmHelper = VibratorHqmHelper.getInstance(vibratorManagerService.mContext);
                VibratorManagerService.this.mHqmHelper.startLogging(VibratorManagerService.this.mContext);
            } else if ("android.intent.action.ACTION_SHUTDOWN".equals(action) || "com.sec.media.action.VIBRTOR_LOGGING".equals(action)) {
                VibratorManagerService.this.sendDataToHqm();
            } else if ("com.android.launcher3.quickstep.closeall".equals(action)) {
                recoverService();
            }
        }
    }

    public int semGetNumberOfSupportedPatterns() {
        return this.mVibratorHelper.getSupportedPatternSize();
    }

    public int getSupportedVibratorGroup() {
        if (this.mVibrators.get(0) != null) {
            return ((VibratorController) this.mVibrators.get(0)).getVibratorGroup();
        }
        return 0;
    }

    public int getSupportedMotorType() {
        if (this.mVibrators.get(0) != null) {
            return ((VibratorController) this.mVibrators.get(0)).getMotorType();
        }
        return 0;
    }

    public final boolean isSupportHapticEngine() {
        if (this.mVibrators.get(0) != null) {
            return ((VibratorController) this.mVibrators.get(0)).isSupportHapticEngine();
        }
        return false;
    }

    public final boolean isSupportFrequencyControl() {
        if (this.mVibrators.get(0) != null) {
            return ((VibratorController) this.mVibrators.get(0)).isSupportFrequencyControl();
        }
        return false;
    }

    public final boolean isSupportIntensityControl() {
        if (this.mVibrators.get(0) != null) {
            return ((VibratorController) this.mVibrators.get(0)).isSupportIntensityControl();
        }
        return false;
    }

    public final boolean isSupportPrebakedHapticPattern() {
        if (this.mVibrators.get(0) != null) {
            return ((VibratorController) this.mVibrators.get(0)).isSupportPrebakedHapticPattern();
        }
        return false;
    }

    public final boolean isSupportEnhancedSamsungHapticPattern() {
        if (this.mVibrators.get(0) != null) {
            return ((VibratorController) this.mVibrators.get(0)).isSupportEnhancedSamsungHapticPattern();
        }
        return false;
    }

    public final boolean isSupportIndexWideBand() {
        if (this.mVibrators.get(0) != null) {
            return ((VibratorController) this.mVibrators.get(0)).isSupportIndexWideBand();
        }
        return false;
    }

    public final boolean hasFeature(String str) {
        if (this.mVibrators.get(0) != null) {
            return ((VibratorController) this.mVibrators.get(0)).hasFeature(str);
        }
        return false;
    }

    public String addCustomDump() {
        return KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "Vibrator information" + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "  Motor type : " + VibratorHelper.getMotorTypeToString(getSupportedMotorType()) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "  Motor group : " + VibratorHelper.getVibrationTypeToString(getSupportedVibratorGroup()) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "    prebakedHapticPattern : " + isSupportPrebakedHapticPattern() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "    hapticEngine : " + isSupportHapticEngine() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "    enhancedSamsungHapticPattern : " + isSupportEnhancedSamsungHapticPattern() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "    intensityControl : " + isSupportIntensityControl() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "    indexWideBand : " + isSupportIndexWideBand() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "  SUPPORT_ACH : " + VibRune.SUPPORT_ACH + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + this.mVibrationSettings.addCustomDump();
    }

    public String getActualPackageName(HalVibration halVibration) {
        int usage = halVibration.callerInfo.attrs.getUsage();
        String emptyIfNull = TextUtils.emptyIfNull(halVibration.callerInfo.reason);
        if (isAllowedUsage(usage) && emptyIfNull.contains("reason: Notification (")) {
            return emptyIfNull.substring(emptyIfNull.indexOf(40) + 1, emptyIfNull.indexOf(41)).split(" ")[0];
        }
        return halVibration.callerInfo.opPkg;
    }

    public final void reportVibrationInfo(HalVibration halVibration, VibrationAttributes vibrationAttributes) {
        GoodCatchManager goodCatchManager;
        if (18 == vibrationAttributes.getUsage() || (goodCatchManager = this.mGoodCatchManager) == null || !goodCatchManager.isVibrateCatchEnabled()) {
            return;
        }
        this.mGoodCatchManager.updateVibrateMode(getActualPackageName(halVibration), vibrationAttributes.getUsage(), halVibration.callerInfo.reason);
    }

    public final void reportVibrationInfo(ExternalVibration externalVibration) {
        GoodCatchManager goodCatchManager = this.mGoodCatchManager;
        if (goodCatchManager == null || !goodCatchManager.isVibrateCatchEnabled() || TextUtils.isEmpty(externalVibration.getPackage())) {
            return;
        }
        String str = externalVibration.getPackage();
        if ("android".equals(str) && externalVibration.isRepeating()) {
            str = "com.android.server.telecom";
        }
        this.mGoodCatchManager.updateVibrateMode(str);
    }

    public final void sendDataToHqm() {
        VibratorHqmHelper vibratorHqmHelper = this.mHqmHelper;
        if (vibratorHqmHelper != null) {
            vibratorHqmHelper.sendHqmVibratorData(this.mHqmLoggingData);
            this.mHqmLoggingData.clear();
        }
    }

    public int getCurrentMagnitude(int i) {
        return this.mVibrationSettings.getCurrentMagnitude(i);
    }

    public final INotificationManager getNotificationService() {
        INotificationManager iNotificationManager = this.mNotificationManager;
        if (iNotificationManager != null) {
            return iNotificationManager;
        }
        INotificationManager service = NotificationManager.getService();
        this.mNotificationManager = service;
        return service;
    }

    public final int fixupVibrationUsages(int i) {
        VibrationEffect.Composed composed = this.mComposed;
        if (composed == null) {
            return i;
        }
        int i2 = AbstractC27852.$SwitchMap$android$os$VibrationEffect$SemMagnitudeType[composed.semGetMagnitudeType().ordinal()];
        if (i2 == 1) {
            return 18;
        }
        if (i2 == 2) {
            return 49;
        }
        if (i2 == 3) {
            return 33;
        }
        if (i2 == 4 && i == 0) {
            return 19;
        }
        return i;
    }

    /* renamed from: com.android.server.vibrator.VibratorManagerService$2 */
    public abstract /* synthetic */ class AbstractC27852 {
        public static final /* synthetic */ int[] $SwitchMap$android$os$VibrationEffect$SemMagnitudeType;
        public static final /* synthetic */ int[] $SwitchMap$com$android$server$vibrator$Vibration$Status;

        static {
            int[] iArr = new int[VibrationEffect.SemMagnitudeType.values().length];
            $SwitchMap$android$os$VibrationEffect$SemMagnitudeType = iArr;
            try {
                iArr[VibrationEffect.SemMagnitudeType.TYPE_TOUCH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$os$VibrationEffect$SemMagnitudeType[VibrationEffect.SemMagnitudeType.TYPE_NOTIFICATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$os$VibrationEffect$SemMagnitudeType[VibrationEffect.SemMagnitudeType.TYPE_CALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$os$VibrationEffect$SemMagnitudeType[VibrationEffect.SemMagnitudeType.TYPE_EXTRA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[Vibration.Status.values().length];
            $SwitchMap$com$android$server$vibrator$Vibration$Status = iArr2;
            try {
                iArr2[Vibration.Status.IGNORED_BACKGROUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[Vibration.Status.IGNORED_ERROR_APP_OPS.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[Vibration.Status.IGNORED_FOR_EXTERNAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[Vibration.Status.IGNORED_FOR_HIGHER_IMPORTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[Vibration.Status.IGNORED_FOR_ONGOING.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[Vibration.Status.IGNORED_FOR_RINGER_MODE.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[Vibration.Status.IGNORED_FROM_VIRTUAL_DEVICE.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    public final VibrationEffect.Composed getComposedEffect(CombinedVibration combinedVibration) {
        if (combinedVibration instanceof CombinedVibration.Mono) {
            return ((CombinedVibration.Mono) combinedVibration).getEffect();
        }
        if ((combinedVibration instanceof CombinedVibration.Stereo) || (combinedVibration instanceof CombinedVibration.Sequential)) {
            return (VibrationEffect.Composed) ((CombinedVibration.Stereo) combinedVibration).getEffects().get(0);
        }
        return null;
    }

    public final int getMagnitudeByUsage(VibrationAttributes vibrationAttributes) {
        if (vibrationAttributes.hasTag("INTENSITY_MAX")) {
            return this.mVibrationSettings.getMaxMagnitude();
        }
        if (vibrationAttributes.hasTag("INTENSITY_MIN")) {
            return this.mVibrationSettings.getMinMagnitude();
        }
        if (this.mComposed.semGetMagnitude() == -1) {
            return getCurrentMagnitude(vibrationAttributes.getUsage());
        }
        return this.mComposed.semGetMagnitude();
    }

    public static int getSepVibrationImportance(Vibration vibration) {
        int usage = vibration.callerInfo.attrs.getUsage();
        if (usage == 0) {
            usage = vibration.isRepeating() ? 33 : 18;
        }
        if (vibration.isRepeating()) {
            return 6;
        }
        if (usage == 33) {
            return ("android".equals(vibration.callerInfo.opPkg) && vibration.callerInfo.attrs.hasTag("VIBRATE_CALL")) ? 3 : 5;
        }
        if (usage != 34) {
            if (usage != 49) {
                if (usage != 50 && usage != 65 && usage != 66) {
                    switch (usage) {
                        case 17:
                            return 4;
                        case 18:
                        case 19:
                            break;
                        default:
                            return 0;
                    }
                }
            } else if (VibratorHelper.isDocomoVibration(vibration.callerInfo.opPkg)) {
                return 7;
            }
        }
        return 3;
    }

    public boolean ignoreCancellingCurrentRingVibration(VibrationStepConductor vibrationStepConductor) {
        if (vibrationStepConductor != null) {
            Vibration.CallerInfo callerInfo = vibrationStepConductor.getVibration().callerInfo;
            String str = callerInfo.opPkg;
            boolean z = callerInfo.attrs.getUsage() == 33;
            if ("com.android.server.telecom".equals(str) && z) {
                Log.d("VibratorManagerService", "ignore cancelling current vibration: " + str);
                return true;
            }
        }
        return false;
    }

    public void cancelCurrentVibration(Vibration.Status status) {
        ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
        if (externalVibrationHolder != null) {
            endVibrationAndWriteStatsLocked(externalVibrationHolder, new Vibration.EndInfo(status));
            this.mCurrentExternalVibration.externalVibration.mute();
            setExternalControl(false, this.mCurrentExternalVibration.stats);
            this.mCurrentExternalVibration = null;
        }
        VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
        if (vibrationStepConductor != null) {
            vibrationStepConductor.notifyCancelled(new Vibration.EndInfo(status), true);
        }
    }
}
