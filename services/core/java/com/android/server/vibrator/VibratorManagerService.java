package com.android.server.vibrator;

import android.R;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.INotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.frameworks.vibrator.IVibratorController;
import android.hardware.input.InputManager;
import android.media.AudioAttributes;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.util.NetworkConstants;
import android.os.Binder;
import android.os.Build;
import android.os.CombinedVibration;
import android.os.ExternalVibration;
import android.os.ExternalVibrationScale;
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
import android.os.SemHqmManager;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.Trace;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import android.os.VibratorManager;
import android.os.vibrator.Flags;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.SemHapticSegment;
import android.os.vibrator.VibrationConfig;
import android.os.vibrator.VibrationEffectSegment;
import android.os.vibrator.VibratorInfoFactory;
import android.os.vibrator.persistence.ParsedVibration;
import android.os.vibrator.persistence.VibrationXmlParser;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.IBatteryStats;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.vibrator.GroupedAggregatedLogRecords;
import com.android.server.vibrator.Vibration;
import com.android.server.vibrator.VibrationSettings;
import com.android.server.vibrator.VibrationStats;
import com.android.server.vibrator.VibratorControlService;
import com.android.server.vibrator.VibratorController;
import com.android.server.vibrator.VibratorManagerService;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.audio.GoodCatchManager;
import com.samsung.android.server.vibrator.PatternInfo;
import com.samsung.android.server.vibrator.VibratorHelper;
import com.samsung.android.server.vibrator.VibratorHqmData;
import com.samsung.android.server.vibrator.VibratorHqmHelper;
import com.samsung.android.vibrator.SemHapticFeedbackConstants;
import com.samsung.android.vibrator.VibRune;
import com.samsung.android.vibrator.VibrationDebugInfo;
import com.samsung.android.view.SemWindowManager;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import libcore.util.NativeAllocationRegistry;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class VibratorManagerService extends IVibratorManagerService.Stub {
    public static final VibrationAttributes DEFAULT_ATTRIBUTES = new VibrationAttributes.Builder().build();
    public final SparseArray mAlwaysOnEffects;
    public final AppOpsManager mAppOps;
    public final IBatteryStats mBatteryStatsService;
    public final long mCapabilities;
    public VibratorInfo mCombinedVibratorInfo;
    public VibrationEffect.Composed mComposed;
    public final Context mContext;
    public ExternalVibrationHolder mCurrentExternalVibration;
    public VibrationStepConductor mCurrentVibration;
    public final DeviceAdapter mDeviceAdapter;
    public final VibratorFrameworkStatsLogger mFrameworkStatsLogger;
    public GoodCatchManager mGoodCatchManager;
    public final Handler mHandler;
    public HapticFeedbackVibrationProvider mHapticFeedbackVibrationProvider;
    public VibratorHqmHelper mHqmHelper;
    public final VibratorHqmData mHqmLoggingData;
    public final Injector mInjector;
    public final InputDeviceDelegate mInputDeviceDelegate;
    public final AnonymousClass1 mIntentReceiver;
    public final Object mLock;
    public boolean mMotorTypeSetFlag;
    public final NativeWrapper mNativeWrapper;
    public VibrationStepConductor mNextVibration;
    public final AnonymousClass1 mSamsungReceiver;
    public boolean mServiceReady;
    public final VibrationScaler mVibrationScaler;
    public final VibrationSettings mVibrationSettings;
    public final VibrationThread mVibrationThread;
    public final VibrationThreadCallbacks mVibrationThreadCallbacks;
    public final VibratorControlService mVibratorControlService;
    public final VibratorHelper mVibratorHelper;
    public final int[] mVibratorIds;
    public final VibratorManagerRecords mVibratorManagerRecords;
    public final SparseArray mVibrators;
    public VirtualVibSoundHelper mVirtualVibSoundHelper;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.vibrator.VibratorManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ VibratorManagerService this$0;

        public /* synthetic */ AnonymousClass1(VibratorManagerService vibratorManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = vibratorManagerService;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String str;
            switch (this.$r8$classId) {
                case 0:
                    if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                        synchronized (this.this$0.mLock) {
                            try {
                                if (intent.getIntExtra("reason", -1) == 19) {
                                    Log.d("VibratorManagerService", "Keep screen turned off");
                                    return;
                                }
                                VibrationStepConductor vibrationStepConductor = this.this$0.mCurrentVibration;
                                boolean z = false;
                                if (vibrationStepConductor != null) {
                                    Vibration.CallerInfo callerInfo = vibrationStepConductor.mVibration.callerInfo;
                                    String str2 = callerInfo.opPkg;
                                    boolean z2 = callerInfo.attrs.getUsage() == 33;
                                    if ("com.android.server.telecom".equals(str2) && z2) {
                                        DualAppManagerService$$ExternalSyntheticOutline0.m("ignore cancelling current vibration: ", str2, "VibratorManagerService");
                                        z = true;
                                    }
                                }
                                if (z) {
                                    return;
                                }
                                VibratorManagerService vibratorManagerService = this.this$0;
                                if (VibratorManagerService.m1033$$Nest$mshouldCancelOnScreenOffLocked(vibratorManagerService, vibratorManagerService.mNextVibration)) {
                                    this.this$0.clearNextVibrationLocked(new Vibration.EndInfo(Vibration.Status.CANCELLED_BY_SCREEN_OFF, null));
                                }
                                VibratorManagerService vibratorManagerService2 = this.this$0;
                                if (VibratorManagerService.m1033$$Nest$mshouldCancelOnScreenOffLocked(vibratorManagerService2, vibratorManagerService2.mCurrentVibration)) {
                                    this.this$0.mCurrentVibration.notifyCancelled(new Vibration.EndInfo(Vibration.Status.CANCELLED_BY_SCREEN_OFF, null), false);
                                    VirtualVibSoundHelper virtualVibSoundHelper = this.this$0.mVirtualVibSoundHelper;
                                    if (virtualVibSoundHelper != null) {
                                        virtualVibSoundHelper.stopVirtualSound("cancel");
                                    }
                                }
                                return;
                            } finally {
                            }
                        }
                    }
                    return;
                default:
                    String action = intent.getAction();
                    if ("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED".equals(action)) {
                        VibratorManagerService vibratorManagerService3 = this.this$0;
                        if (vibratorManagerService3.mGoodCatchManager == null) {
                            vibratorManagerService3.mGoodCatchManager = new GoodCatchManager(vibratorManagerService3.mContext, "VibratorService");
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                        VibratorManagerService vibratorManagerService4 = this.this$0;
                        Context context2 = vibratorManagerService4.mContext;
                        if (VibratorHqmHelper.sInstance == null) {
                            VibratorHqmHelper vibratorHqmHelper = new VibratorHqmHelper();
                            vibratorHqmHelper.mSemHqmManager = (SemHqmManager) context2.getSystemService("HqmManagerService");
                            vibratorHqmHelper.mAlarmManager = (AlarmManager) context2.getSystemService("alarm");
                            VibratorHqmHelper.sInstance = vibratorHqmHelper;
                        }
                        vibratorManagerService4.mHqmHelper = VibratorHqmHelper.sInstance;
                        VibratorManagerService vibratorManagerService5 = this.this$0;
                        VibratorHqmHelper vibratorHqmHelper2 = vibratorManagerService5.mHqmHelper;
                        Context context3 = vibratorManagerService5.mContext;
                        vibratorHqmHelper2.getClass();
                        vibratorHqmHelper2.mAlarmManager.setRepeating(3, SystemClock.elapsedRealtime() + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, PendingIntent.getBroadcast(context3, 0, new Intent("com.sec.media.action.VIBRTOR_LOGGING"), 67108864));
                        return;
                    }
                    if (!"android.intent.action.ACTION_SHUTDOWN".equals(action) && !"com.sec.media.action.VIBRTOR_LOGGING".equals(action)) {
                        if ("android.media.STREAM_DEVICES_CHANGED_ACTION".equals(action)) {
                            int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                            int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_DEVICES", -1);
                            if (intExtra == 3 && (intExtra2 & 2) == 0) {
                                final String str3 = "Detected device changed";
                                this.this$0.mHandler.post(new Runnable() { // from class: com.android.server.vibrator.VibratorManagerService$SamsungBroadcastReceiver$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        VibratorManagerService.AnonymousClass1 anonymousClass1 = VibratorManagerService.AnonymousClass1.this;
                                        String str4 = str3;
                                        synchronized (anonymousClass1.this$0.mLock) {
                                            try {
                                                VirtualVibSoundHelper virtualVibSoundHelper2 = anonymousClass1.this$0.mVirtualVibSoundHelper;
                                                if (virtualVibSoundHelper2 != null) {
                                                    virtualVibSoundHelper2.stopVirtualSound(str4);
                                                }
                                            } catch (Throwable th) {
                                                throw th;
                                            }
                                        }
                                    }
                                });
                                return;
                            }
                            return;
                        }
                        if ("android.intent.action.HEADSET_PLUG".equals(action)) {
                            final String str4 = "headset plug";
                            this.this$0.mHandler.post(new Runnable() { // from class: com.android.server.vibrator.VibratorManagerService$SamsungBroadcastReceiver$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    VibratorManagerService.AnonymousClass1 anonymousClass1 = VibratorManagerService.AnonymousClass1.this;
                                    String str42 = str4;
                                    synchronized (anonymousClass1.this$0.mLock) {
                                        try {
                                            VirtualVibSoundHelper virtualVibSoundHelper2 = anonymousClass1.this$0.mVirtualVibSoundHelper;
                                            if (virtualVibSoundHelper2 != null) {
                                                virtualVibSoundHelper2.stopVirtualSound(str42);
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            });
                            return;
                        } else if (!"android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
                            if ("com.android.launcher3.quickstep.closeall".equals(action)) {
                                this.this$0.mHandler.postDelayed(new Runnable() { // from class: com.android.server.vibrator.VibratorManagerService$SamsungBroadcastReceiver$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        VibratorManagerService.AnonymousClass1 anonymousClass1 = VibratorManagerService.AnonymousClass1.this;
                                        synchronized (anonymousClass1.this$0.mLock) {
                                            anonymousClass1.this$0.cancelCurrentVibration();
                                        }
                                    }
                                }, 500L);
                                return;
                            }
                            return;
                        } else {
                            if (intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0) == 2) {
                                final String str5 = "a2dp device connected";
                                this.this$0.mHandler.post(new Runnable() { // from class: com.android.server.vibrator.VibratorManagerService$SamsungBroadcastReceiver$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        VibratorManagerService.AnonymousClass1 anonymousClass1 = VibratorManagerService.AnonymousClass1.this;
                                        String str42 = str5;
                                        synchronized (anonymousClass1.this$0.mLock) {
                                            try {
                                                VirtualVibSoundHelper virtualVibSoundHelper2 = anonymousClass1.this$0.mVirtualVibSoundHelper;
                                                if (virtualVibSoundHelper2 != null) {
                                                    virtualVibSoundHelper2.stopVirtualSound(str42);
                                                }
                                            } catch (Throwable th) {
                                                throw th;
                                            }
                                        }
                                    }
                                });
                                return;
                            }
                            return;
                        }
                    }
                    VibratorManagerService vibratorManagerService6 = this.this$0;
                    VibratorHqmHelper vibratorHqmHelper3 = vibratorManagerService6.mHqmHelper;
                    if (vibratorHqmHelper3 != null) {
                        VibratorHqmData vibratorHqmData = vibratorManagerService6.mHqmLoggingData;
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        for (int i = 0; i < 5; i++) {
                            String[] strArr = VibratorHqmHelper.BIG_DATA;
                            String num = Integer.toString(((Integer) vibratorHqmData.mLoggingData.getOrDefault(strArr[i], 0)).intValue());
                            if (num != null) {
                                arrayList.add(strArr[i]);
                                arrayList2.add(num);
                            }
                        }
                        int size = arrayList.size();
                        try {
                            JSONObject jSONObject = new JSONObject();
                            for (int i2 = 0; i2 < size; i2++) {
                                jSONObject.put((String) arrayList.get(i2), arrayList2.get(i2));
                            }
                            str = jSONObject.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            str = null;
                        }
                        if (str != null) {
                            String replaceAll = str.replaceAll("\\{", "").replaceAll("\\}", "");
                            if (vibratorHqmHelper3.mSemHqmManager != null) {
                                Log.v("VibratorHqmHelper", "sendLoggingDataToHQM() Server update !!!");
                                vibratorHqmHelper3.mSemHqmManager.sendHWParamToHQM(0, "Audio", "VIBE", "sm", "0.0", "sec", "", replaceAll, "");
                            }
                        }
                        VibratorHqmData vibratorHqmData2 = vibratorManagerService6.mHqmLoggingData;
                        vibratorHqmData2.mRingCount = 0;
                        vibratorHqmData2.mAlarmCount = 0;
                        vibratorHqmData2.mNotificationCount = 0;
                        vibratorHqmData2.mTouchCount = 0;
                        vibratorHqmData2.mExtraCount = 0;
                        return;
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.vibrator.VibratorManagerService$2, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass2 {
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
                iArr2[16] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[11] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[19] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[20] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[21] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[23] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[26] = 7;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ExternalVibrationHolder extends Vibration implements IBinder.DeathRecipient {
        public final ExternalVibration externalVibration;
        public Vibration.Status mStatus;
        public ExternalVibrationScale scale;

        public ExternalVibrationHolder(ExternalVibration externalVibration) {
            super(externalVibration.getToken(), new Vibration.CallerInfo(externalVibration.getVibrationAttributes(), externalVibration.getUid(), -1, externalVibration.getPackage(), null));
            this.scale = new ExternalVibrationScale();
            this.externalVibration = externalVibration;
            this.mStatus = Vibration.Status.RUNNING;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (VibratorManagerService.this.mLock) {
                try {
                    if (VibratorManagerService.this.mCurrentExternalVibration != null) {
                        Slog.d("VibratorManagerService", "External vibration finished because binder died");
                        VibratorManagerService.this.endExternalVibrateLocked(new Vibration.EndInfo(Vibration.Status.CANCELLED_BINDER_DIED, null), false);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final Vibration.DebugInfo getDebugInfo() {
            Vibration.Status status = this.mStatus;
            ExternalVibrationScale externalVibrationScale = this.scale;
            return new Vibration.DebugInfo(status, this.stats, null, null, externalVibrationScale.scaleLevel, externalVibrationScale.adaptiveHapticsScale, this.callerInfo);
        }

        @Override // com.android.server.vibrator.Vibration
        public final boolean isRepeating() {
            int usage = this.externalVibration.getVibrationAttributes().getUsage();
            return usage == 33 || usage == 17;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class ExternalVibratorService extends IExternalVibratorService.Stub {
        public ExternalVibratorService() {
        }

        public final ExternalVibrationScale onExternalVibrationStart(ExternalVibration externalVibration) {
            boolean z;
            boolean z2;
            VirtualVibSoundHelper virtualVibSoundHelper;
            ExternalVibrationScale externalVibrationScale;
            ExternalVibrationHolder externalVibrationHolder;
            if (!VibRune.SUPPORT_ACH) {
                Slog.w("VibratorManagerService", "onExternalVibrationStart() -  SCALE_MUTE");
                ExternalVibrationScale externalVibrationScale2 = new ExternalVibrationScale();
                externalVibrationScale2.scaleLevel = 0;
                return externalVibrationScale2;
            }
            ExternalVibrationHolder externalVibrationHolder2 = VibratorManagerService.this.new ExternalVibrationHolder(externalVibration);
            externalVibrationHolder2.scale.scaleLevel = -100;
            synchronized (VibratorManagerService.this.mLock) {
                for (int i = 0; i < VibratorManagerService.this.mVibrators.size(); i++) {
                    try {
                        if (((VibratorController) VibratorManagerService.this.mVibrators.valueAt(i)).mVibratorInfo.hasCapability(8L)) {
                            if (ActivityManager.checkComponentPermission("android.permission.VIBRATE", externalVibration.getUid(), -1, true) != 0) {
                                Slog.w("VibratorManagerService", "pkg=" + externalVibration.getPackage() + ", uid=" + externalVibration.getUid() + " tried to play externally controlled vibration without VIBRATE permission, ignoring.");
                                VibratorManagerService.this.endVibrationAndWriteStatsLocked(externalVibrationHolder2, new Vibration.EndInfo(Vibration.Status.IGNORED_MISSING_PERMISSION, null));
                                return externalVibrationHolder2.scale;
                            }
                            Vibration.EndInfo shouldIgnoreVibrationLocked = VibratorManagerService.this.shouldIgnoreVibrationLocked(externalVibrationHolder2.callerInfo);
                            if (shouldIgnoreVibrationLocked == null && (externalVibrationHolder = VibratorManagerService.this.mCurrentExternalVibration) != null && externalVibrationHolder.externalVibration.equals(externalVibration)) {
                                return VibratorManagerService.this.mCurrentExternalVibration.scale;
                            }
                            if (shouldIgnoreVibrationLocked == null) {
                                shouldIgnoreVibrationLocked = VibratorManagerService.this.shouldIgnoreVibrationForOngoingLocked(externalVibrationHolder2);
                            }
                            if (shouldIgnoreVibrationLocked != null) {
                                VibratorManagerService.this.endVibrationAndWriteStatsLocked(externalVibrationHolder2, shouldIgnoreVibrationLocked);
                                return externalVibrationHolder2.scale;
                            }
                            VibratorManagerService vibratorManagerService = VibratorManagerService.this;
                            ExternalVibrationHolder externalVibrationHolder3 = vibratorManagerService.mCurrentExternalVibration;
                            if (externalVibrationHolder3 == null) {
                                VibrationStepConductor vibrationStepConductor = vibratorManagerService.mCurrentVibration;
                                if (vibrationStepConductor != null) {
                                    externalVibrationHolder2.stats.reportInterruptedAnotherVibration(vibrationStepConductor.mVibration.callerInfo);
                                    VibratorManagerService.this.clearNextVibrationLocked(new Vibration.EndInfo(Vibration.Status.IGNORED_FOR_EXTERNAL, externalVibrationHolder2.callerInfo));
                                    VibratorManagerService.this.mCurrentVibration.notifyCancelled(new Vibration.EndInfo(Vibration.Status.CANCELLED_SUPERSEDED, externalVibrationHolder2.callerInfo), true);
                                    VirtualVibSoundHelper virtualVibSoundHelper2 = VibratorManagerService.this.mVirtualVibSoundHelper;
                                    if (virtualVibSoundHelper2 != null) {
                                        virtualVibSoundHelper2.stopVirtualSound("cancel");
                                    }
                                    z2 = false;
                                    z = true;
                                } else {
                                    z = false;
                                    z2 = false;
                                }
                            } else {
                                externalVibrationHolder3.externalVibration.mute();
                                externalVibrationHolder2.stats.reportInterruptedAnotherVibration(VibratorManagerService.this.mCurrentExternalVibration.callerInfo);
                                VibratorManagerService.this.endExternalVibrateLocked(new Vibration.EndInfo(Vibration.Status.CANCELLED_SUPERSEDED, externalVibrationHolder2.callerInfo), true);
                                VirtualVibSoundHelper virtualVibSoundHelper3 = VibratorManagerService.this.mVirtualVibSoundHelper;
                                if (virtualVibSoundHelper3 != null) {
                                    virtualVibSoundHelper3.stopVirtualSound("mute");
                                }
                                z = false;
                                z2 = true;
                            }
                            VibrationAttributes fixupVibrationAttributes = VibratorManagerService.this.fixupVibrationAttributes(externalVibration.getVibrationAttributes(), null);
                            if (fixupVibrationAttributes.isFlagSet(4)) {
                                VibratorManagerService.this.mVibrationSettings.update();
                            }
                            VibratorManagerService.this.mCurrentExternalVibration = externalVibrationHolder2;
                            externalVibrationHolder2.externalVibration.linkToDeath(externalVibrationHolder2);
                            VibrationScaler vibrationScaler = VibratorManagerService.this.mVibrationScaler;
                            int usage = fixupVibrationAttributes.getUsage();
                            externalVibrationHolder2.scale.scaleLevel = vibrationScaler.getScaleLevel(usage);
                            externalVibrationHolder2.scale.adaptiveHapticsScale = vibrationScaler.getAdaptiveHapticsScale(usage);
                            float f = externalVibrationHolder2.scale.adaptiveHapticsScale;
                            VibrationStats vibrationStats = externalVibrationHolder2.stats;
                            vibrationStats.getClass();
                            if (Float.compare(f, 1.0f) != 0) {
                                vibrationStats.mAdaptiveScale = f;
                            }
                            if (z && !VibratorManagerService.this.mVibrationThread.waitForThreadIdle(5000L)) {
                                Slog.e("VibratorManagerService", "Timed out waiting for vibration to cancel");
                                synchronized (VibratorManagerService.this.mLock) {
                                    VibratorManagerService.this.endExternalVibrateLocked(new Vibration.EndInfo(Vibration.Status.IGNORED_ERROR_CANCELLING, null), false);
                                    externalVibrationScale = externalVibrationHolder2.scale;
                                    externalVibrationScale.scaleLevel = -100;
                                }
                                return externalVibrationScale;
                            }
                            if (!z2) {
                                Slog.d("VibratorManagerService", "Vibrator going under external control.");
                                VibratorManagerService.this.setExternalControl(true, externalVibrationHolder2.stats);
                            }
                            if (VibRune.SUPPORT_ACH) {
                                int currentMagnitude = VibratorManagerService.this.mVibrationSettings.getCurrentMagnitude(externalVibration.getVibrationAttributes().getUsage());
                                ExternalVibrationScale externalVibrationScale3 = new ExternalVibrationScale();
                                externalVibrationScale3.scaleLevel = 0;
                                for (String str : externalVibrationHolder2.externalVibration.getVibrationAttributesWithTags().getTags()) {
                                    if (str.contains("HAPTIC_INTENSITY")) {
                                        String[] split = str.split("=");
                                        if (split[1] != null) {
                                            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(new StringBuilder("ach intensity : "), split[1], "VibratorManagerService");
                                            currentMagnitude = Integer.parseInt(split[1]);
                                        }
                                    }
                                }
                                AnyMotionDetector$$ExternalSyntheticOutline0.m(currentMagnitude, "getCurrentMagnitude : ", "VibratorManagerService");
                                if (currentMagnitude == 0) {
                                    externalVibrationHolder2.scale = externalVibrationScale3;
                                } else {
                                    VibratorController vibratorController = (VibratorController) VibratorManagerService.this.mVibrators.get(0);
                                    if (vibratorController != null) {
                                        vibratorController.mNativeWrapper.setIntensity(currentMagnitude);
                                    }
                                    externalVibrationHolder2.scale = externalVibrationScale3;
                                }
                                VibratorManagerService vibratorManagerService2 = VibratorManagerService.this;
                                GoodCatchManager goodCatchManager = vibratorManagerService2.mGoodCatchManager;
                                if (goodCatchManager != null) {
                                    if ((TextUtils.equals("VibratorService", goodCatchManager.mModule) ? goodCatchManager.mVibrationFunc : false) && !TextUtils.isEmpty(externalVibration.getPackage())) {
                                        String str2 = externalVibration.getPackage();
                                        if ("android".equals(str2) && externalVibration.isRepeating()) {
                                            str2 = "com.android.server.telecom";
                                        }
                                        vibratorManagerService2.mGoodCatchManager.mSemGoodCatchManager.update("vibration", str2, 0, "", "");
                                    }
                                }
                            }
                            Slog.d("VibratorManagerService", "Playing external vibration: " + externalVibration);
                            VibrationStats vibrationStats2 = externalVibrationHolder2.stats;
                            if (vibrationStats2.mEndUptimeMillis <= 0 && vibrationStats2.mStartUptimeMillis == 0) {
                                vibrationStats2.mStartUptimeMillis = SystemClock.uptimeMillis();
                                vibrationStats2.mStartTimeDebug = System.currentTimeMillis();
                            }
                            if (VibRune.SUPPORT_ACH && externalVibrationHolder2.scale.scaleLevel != -100 && (virtualVibSoundHelper = VibratorManagerService.this.mVirtualVibSoundHelper) != null) {
                                virtualVibSoundHelper.playVirtualSoundIfNeeded(externalVibration.getPackage(), externalVibration.getVibrationAttributesWithTags(), externalVibration.isRepeating());
                            }
                            VibratorManagerService vibratorManagerService3 = VibratorManagerService.this;
                            if (vibratorManagerService3.getDefaultVibratorController() != null) {
                                VibratorController defaultVibratorController = vibratorManagerService3.getDefaultVibratorController();
                                synchronized (defaultVibratorController.mLock) {
                                    defaultVibratorController.notifyListenerOnVibrating(true);
                                }
                            }
                            return externalVibrationHolder2.scale;
                        }
                    } finally {
                    }
                }
                VibratorManagerService.this.endVibrationAndWriteStatsLocked(externalVibrationHolder2, new Vibration.EndInfo(Vibration.Status.IGNORED_UNSUPPORTED, null));
                return externalVibrationHolder2.scale;
            }
        }

        public final void onExternalVibrationStop(ExternalVibration externalVibration) {
            synchronized (VibratorManagerService.this.mLock) {
                try {
                    ExternalVibrationHolder externalVibrationHolder = VibratorManagerService.this.mCurrentExternalVibration;
                    if (externalVibrationHolder != null && externalVibrationHolder.externalVibration.equals(externalVibration)) {
                        Slog.d("VibratorManagerService", "Stopping external vibration: " + externalVibration);
                        if (externalVibration.isRepeating()) {
                            Log.d("VibratorManagerService", "Ach repeat vibration ended");
                            VirtualVibSoundHelper virtualVibSoundHelper = VibratorManagerService.this.mVirtualVibSoundHelper;
                            if (virtualVibSoundHelper != null) {
                                virtualVibSoundHelper.stopVirtualSound("stop");
                            }
                        }
                        VibratorManagerService.this.endExternalVibrateLocked(new Vibration.EndInfo(Vibration.Status.FINISHED, null), false);
                        VibratorManagerService vibratorManagerService = VibratorManagerService.this;
                        if (vibratorManagerService.getDefaultVibratorController() != null) {
                            VibratorController defaultVibratorController = vibratorManagerService.getDefaultVibratorController();
                            synchronized (defaultVibratorController.mLock) {
                                defaultVibratorController.notifyListenerOnVibrating(false);
                            }
                        }
                    }
                } finally {
                }
            }
        }

        public final boolean shouldIgnoreExternalVibrationLocked(int i, int i2, int i3, int i4) {
            return false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public VibratorManagerService mService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 500) {
                this.mService.systemReady();
                return;
            }
            if (i == 1000) {
                VibratorManagerService vibratorManagerService = this.mService;
                final VibrationCustomSettings vibrationCustomSettings = vibratorManagerService.mVibrationSettings.mCustomSettings;
                VibratorManagerService vibratorManagerService2 = vibrationCustomSettings.mService;
                if (vibratorManagerService2.getDefaultVibratorController() != null) {
                    if (vibratorManagerService2.getDefaultVibratorController().mNativeWrapper.supportsFoldState()) {
                        SemWindowManager.getInstance().registerFoldStateListener(new SemWindowManager.FoldStateListener() { // from class: com.android.server.vibrator.VibrationCustomSettings.1
                            public AnonymousClass1() {
                            }

                            public final void onFoldStateChanged(boolean z) {
                                AccessibilityManagerService$$ExternalSyntheticOutline0.m("onFoldStateChanged isFolded = ", "VibratorManagerService", z);
                                if (VibrationCustomSettings.this.mService.getDefaultVibratorController() != null) {
                                    VibrationCustomSettings.this.mService.getDefaultVibratorController().mNativeWrapper.setFoldState(z);
                                }
                            }

                            public final void onTableModeChanged(boolean z) {
                                AccessibilityManagerService$$ExternalSyntheticOutline0.m("onTableModeChanged. isTableMode=", "VibratorManagerService", z);
                            }
                        }, (Handler) null);
                    } else {
                        Log.d("VibratorManagerService", "Fold mode is not supported");
                    }
                }
                vibratorManagerService.mVirtualVibSoundHelper = new VirtualVibSoundHelper(vibratorManagerService.mContext, vibratorManagerService.mVibrationSettings);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.vibrator.VibratorManagerService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? vibratorManagerService = new VibratorManagerService(getContext(), new Injector());
            this.mService = vibratorManagerService;
            publishBinderService("vibrator_manager", vibratorManagerService);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class NativeWrapper {
        public long mNativeServicePtr = 0;

        public final void cancelSynced() {
            VibratorManagerService.nativeCancelSynced(this.mNativeServicePtr);
        }

        public final long getCapabilities() {
            return VibratorManagerService.nativeGetCapabilities(this.mNativeServicePtr);
        }

        public final int[] getVibratorIds() {
            return VibratorManagerService.nativeGetVibratorIds(this.mNativeServicePtr);
        }

        public final void init(OnSyncedVibrationCompleteListener onSyncedVibrationCompleteListener) {
            this.mNativeServicePtr = VibratorManagerService.nativeInit(onSyncedVibrationCompleteListener);
            long nativeGetFinalizer = VibratorManagerService.nativeGetFinalizer();
            if (nativeGetFinalizer != 0) {
                NativeAllocationRegistry.createMalloced(VibratorManagerService.class.getClassLoader(), nativeGetFinalizer).registerNativeAllocation(this, this.mNativeServicePtr);
            }
        }

        public final boolean prepareSynced(int[] iArr) {
            return VibratorManagerService.nativePrepareSynced(this.mNativeServicePtr, iArr);
        }

        public final boolean triggerSynced(long j) {
            return VibratorManagerService.nativeTriggerSynced(this.mNativeServicePtr, j);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface OnSyncedVibrationCompleteListener {
        void onComplete(long j);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VibrationRecord implements GroupedAggregatedLogRecords.SingleLogRecord {
        public final Vibration.DebugInfo mInfo;

        public VibrationRecord(Vibration.DebugInfo debugInfo) {
            this.mInfo = debugInfo;
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            String str;
            Vibration.DebugInfo debugInfo = this.mInfo;
            boolean z = debugInfo.mPlayedEffect == null;
            Locale locale = Locale.ROOT;
            String formatTime = Vibration.DebugInfo.formatTime(debugInfo.mCreateTime, true);
            String str2 = z ? "external" : "effect";
            String lowerCase = debugInfo.mStatus.name().toLowerCase(locale);
            Long valueOf = Long.valueOf(debugInfo.mDurationMs);
            long j = debugInfo.mStartTime;
            String str3 = "";
            String formatTime2 = j == 0 ? "" : Vibration.DebugInfo.formatTime(j, false);
            long j2 = debugInfo.mEndTime;
            String format = String.format(locale, "%s | %8s | %20s | duration: %5dms | start: %12s | end: %12s", formatTime, str2, lowerCase, valueOf, formatTime2, j2 == 0 ? "" : Vibration.DebugInfo.formatTime(j2, false));
            String scaleLevelToString = VibrationScaler.scaleLevelToString(debugInfo.mScaleLevel);
            Float valueOf2 = Float.valueOf(debugInfo.mAdaptiveScale);
            Vibration.CallerInfo callerInfo = debugInfo.mCallerInfo;
            String format2 = String.format(locale, " | scale: %8s (adaptive=%.2f) | flags: %4s | usage: %s", scaleLevelToString, valueOf2, Long.toBinaryString(callerInfo.attrs.getFlags()), callerInfo.attrs.usageToString());
            if (callerInfo.attrs.getCategory() != 0) {
                str = " | category=" + VibrationAttributes.categoryToString(callerInfo.attrs.getCategory());
            } else {
                str = "";
            }
            if (callerInfo.attrs.getOriginalAudioUsage() != 0) {
                str3 = " | audioUsage=" + AudioAttributes.usageToString(callerInfo.attrs.getOriginalAudioUsage());
            }
            String str4 = " | " + callerInfo.opPkg + " (uid=" + callerInfo.uid + ", deviceId=" + callerInfo.deviceId + ") | reason: " + callerInfo.reason;
            CombinedVibration combinedVibration = debugInfo.mPlayedEffect;
            String debugString = combinedVibration == null ? null : combinedVibration.toDebugString();
            CombinedVibration combinedVibration2 = debugInfo.mOriginalEffect;
            indentingPrintWriter.println(format + format2 + str + str3 + str4 + BootReceiver$$ExternalSyntheticOutline0.m(" | played: ", debugString, " | original: ", combinedVibration2 != null ? combinedVibration2.toDebugString() : null));
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public final void dump(ProtoOutputStream protoOutputStream, long j) {
            this.mInfo.dump(protoOutputStream, j);
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public final long getCreateUptimeMs() {
            return this.mInfo.mCreateTime;
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public final int getGroupKey() {
            return this.mInfo.mCallerInfo.attrs.getUsage();
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public final boolean mayAggregate(GroupedAggregatedLogRecords.SingleLogRecord singleLogRecord) {
            if (!(singleLogRecord instanceof VibrationRecord)) {
                return false;
            }
            Vibration.DebugInfo debugInfo = ((VibrationRecord) singleLogRecord).mInfo;
            Vibration.DebugInfo debugInfo2 = this.mInfo;
            Vibration.CallerInfo callerInfo = debugInfo2.mCallerInfo;
            int i = callerInfo.uid;
            Vibration.CallerInfo callerInfo2 = debugInfo.mCallerInfo;
            return i == callerInfo2.uid && Objects.equals(callerInfo.attrs, callerInfo2.attrs) && Objects.equals(debugInfo2.mPlayedEffect, debugInfo.mPlayedEffect);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VibrationRecords extends GroupedAggregatedLogRecords {
        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords
        public final void dumpGroupHeader(IndentingPrintWriter indentingPrintWriter, int i) {
            indentingPrintWriter.println(VibrationAttributes.usageToString(i) + ":");
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords
        public final long findGroupKeyProtoFieldId(int i) {
            if (i == 17) {
                return 2246267895823L;
            }
            if (i != 33) {
                return i != 49 ? 2246267895824L : 2246267895822L;
            }
            return 2246267895821L;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VibrationThreadCallbacks implements VibratorController.OnVibrationCompleteListener, OnSyncedVibrationCompleteListener {
        public Object this$0;

        public void noteVibratorOff(final int i) {
            VibratorManagerService vibratorManagerService = (VibratorManagerService) this.this$0;
            try {
                vibratorManagerService.mBatteryStatsService.noteVibratorOff(i);
                VibratorFrameworkStatsLogger vibratorFrameworkStatsLogger = vibratorManagerService.mFrameworkStatsLogger;
                vibratorFrameworkStatsLogger.getClass();
                vibratorFrameworkStatsLogger.mHandler.post(new Runnable() { // from class: com.android.server.vibrator.VibratorFrameworkStatsLogger$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        FrameworkStatsLog.write_non_chained(84, i, (String) null, 0, 0);
                    }
                });
            } catch (RemoteException e) {
                Slog.e("VibratorManagerService", "Error logging VibratorStateChanged to OFF", e);
            }
        }

        @Override // com.android.server.vibrator.VibratorController.OnVibrationCompleteListener
        public void onComplete(int i, long j) {
            VibratorManagerService vibratorManagerService = (VibratorManagerService) ((WeakReference) this.this$0).get();
            if (vibratorManagerService != null) {
                synchronized (vibratorManagerService.mLock) {
                    try {
                        VibrationStepConductor vibrationStepConductor = vibratorManagerService.mCurrentVibration;
                        if (vibrationStepConductor != null && vibrationStepConductor.mVibration.id == j) {
                            Slog.d("VibratorManagerService", "Vibration " + j + " on vibrator " + i + " complete, notifying thread");
                            VibrationStepConductor vibrationStepConductor2 = vibratorManagerService.mCurrentVibration;
                            vibrationStepConductor2.getClass();
                            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "Vibration complete reported by vibrator ", "VibrationThread");
                            synchronized (vibrationStepConductor2.mLock) {
                                vibrationStepConductor2.mSignalVibratorsComplete.add(i);
                                vibrationStepConductor2.mLock.notify();
                            }
                        }
                    } finally {
                    }
                }
            }
        }

        @Override // com.android.server.vibrator.VibratorManagerService.OnSyncedVibrationCompleteListener
        public void onComplete(long j) {
            VibratorManagerService vibratorManagerService = (VibratorManagerService) ((WeakReference) this.this$0).get();
            if (vibratorManagerService != null) {
                synchronized (vibratorManagerService.mLock) {
                    try {
                        VibrationStepConductor vibrationStepConductor = vibratorManagerService.mCurrentVibration;
                        if (vibrationStepConductor != null && vibrationStepConductor.mVibration.id == j) {
                            Slog.d("VibratorManagerService", "Synced vibration " + j + " complete, notifying thread");
                            vibratorManagerService.mCurrentVibration.notifySyncedVibrationComplete();
                        }
                    } finally {
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VibratorManagerRecords {
        public final VibrationRecords mAggregatedVibrationHistory;
        public final VibrationRecords mRecentVibrations;

        public VibratorManagerRecords(int i, int i2, int i3) {
            this.mAggregatedVibrationHistory = new VibrationRecords(i2, i3);
            this.mRecentVibrations = new VibrationRecords(i, 0);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VibratorManagerShellCommand extends ShellCommand {
        public final IBinder mShellCallbacksToken;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class CommonOptions {
            public final boolean background;
            public final String description;
            public final boolean force;
            public final String pkgName;

            public CommonOptions(VibratorManagerShellCommand vibratorManagerShellCommand) {
                this.force = false;
                this.description = "Shell command";
                this.background = false;
                this.pkgName = "com.android.shell";
                while (true) {
                    String peekNextArg = vibratorManagerShellCommand.peekNextArg();
                    if (peekNextArg != null) {
                        switch (peekNextArg) {
                            case "-B":
                                vibratorManagerShellCommand.getNextArgRequired();
                                this.background = true;
                                break;
                            case "-d":
                                vibratorManagerShellCommand.getNextArgRequired();
                                this.description = vibratorManagerShellCommand.getNextArgRequired();
                                break;
                            case "-f":
                                vibratorManagerShellCommand.getNextArgRequired();
                                this.force = true;
                                break;
                            case "-p":
                                vibratorManagerShellCommand.getNextArgRequired();
                                this.pkgName = vibratorManagerShellCommand.getNextArgRequired();
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

        public final VibrationEffect nextEffect() {
            int i;
            VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
            while (true) {
                String peekNextArg = peekNextArg();
                if (peekNextArg == null) {
                    break;
                }
                int i2 = 0;
                if (!"oneshot".equals(peekNextArg)) {
                    if (!"waveform".equals(peekNextArg)) {
                        if (!"prebaked".equals(peekNextArg)) {
                            if (!"primitives".equals(peekNextArg)) {
                                break;
                            }
                            getNextArgRequired();
                            while (true) {
                                String peekNextArg2 = peekNextArg();
                                if (peekNextArg2 != null) {
                                    if ("-w".equals(peekNextArg2)) {
                                        getNextArgRequired();
                                        i = Integer.parseInt(getNextArgRequired());
                                        peekNextArg2 = peekNextArg();
                                    } else {
                                        i = 0;
                                    }
                                    try {
                                        startComposition.addPrimitive(Integer.parseInt(peekNextArg2), 1.0f, i);
                                        getNextArgRequired();
                                    } catch (NullPointerException | NumberFormatException unused) {
                                    }
                                }
                            }
                        } else {
                            getNextArgRequired();
                            boolean z = false;
                            while (true) {
                                String nextOption = getNextOption();
                                if (nextOption == null) {
                                    break;
                                }
                                if ("-b".equals(nextOption)) {
                                    z = true;
                                } else if ("-w".equals(nextOption)) {
                                    i2 = Integer.parseInt(getNextArgRequired());
                                }
                            }
                            int parseInt = Integer.parseInt(getNextArgRequired());
                            startComposition.addOffDuration(Duration.ofMillis(i2));
                            startComposition.addEffect(VibrationEffect.get(parseInt, z));
                        }
                    } else {
                        getNextArgRequired();
                        boolean z2 = false;
                        boolean z3 = false;
                        int i3 = 0;
                        boolean z4 = false;
                        while (true) {
                            String nextOption2 = getNextOption();
                            if (nextOption2 == null) {
                                break;
                            }
                            if ("-a".equals(nextOption2)) {
                                z2 = true;
                            } else if ("-r".equals(nextOption2)) {
                                r3 = Integer.parseInt(getNextArgRequired());
                            } else if ("-w".equals(nextOption2)) {
                                i3 = Integer.parseInt(getNextArgRequired());
                            } else if ("-f".equals(nextOption2)) {
                                z3 = true;
                            } else if ("-c".equals(nextOption2)) {
                                z4 = true;
                            }
                        }
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        ArrayList arrayList3 = new ArrayList();
                        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
                        while (true) {
                            String peekNextArg3 = peekNextArg();
                            if (peekNextArg3 == null) {
                                break;
                            }
                            try {
                                arrayList.add(Integer.valueOf(Integer.parseInt(peekNextArg3)));
                                getNextArgRequired();
                                if (z2) {
                                    arrayList2.add(Float.valueOf(Float.parseFloat(getNextArgRequired()) / 255.0f));
                                } else {
                                    arrayList2.add(Float.valueOf(f));
                                    f = 1.0f - f;
                                }
                                if (z3) {
                                    arrayList3.add(Float.valueOf(Float.parseFloat(getNextArgRequired())));
                                }
                            } catch (NumberFormatException unused2) {
                            }
                        }
                        startComposition.addOffDuration(Duration.ofMillis(i3));
                        VibrationEffect.WaveformBuilder startWaveform = VibrationEffect.startWaveform();
                        while (i2 < arrayList.size()) {
                            Duration ofMillis = z4 ? Duration.ofMillis(((Integer) arrayList.get(i2)).intValue()) : Duration.ZERO;
                            Duration ofMillis2 = z4 ? Duration.ZERO : Duration.ofMillis(((Integer) arrayList.get(i2)).intValue());
                            if (z3) {
                                startWaveform.addTransition(ofMillis, VibrationEffect.VibrationParameter.targetAmplitude(((Float) arrayList2.get(i2)).floatValue()), VibrationEffect.VibrationParameter.targetFrequency(((Float) arrayList3.get(i2)).floatValue()));
                            } else {
                                startWaveform.addTransition(ofMillis, VibrationEffect.VibrationParameter.targetAmplitude(((Float) arrayList2.get(i2)).floatValue()));
                            }
                            if (!ofMillis2.isZero()) {
                                startWaveform.addSustain(ofMillis2);
                            }
                            if (i2 > 0 && i2 == r3) {
                                startComposition.addEffect(startWaveform.build());
                                startWaveform = z3 ? VibrationEffect.startWaveform(VibrationEffect.VibrationParameter.targetAmplitude(((Float) arrayList2.get(i2)).floatValue()), VibrationEffect.VibrationParameter.targetFrequency(((Float) arrayList3.get(i2)).floatValue())) : VibrationEffect.startWaveform(VibrationEffect.VibrationParameter.targetAmplitude(((Float) arrayList2.get(i2)).floatValue()));
                            }
                            i2++;
                        }
                        if (r3 < 0) {
                            startComposition.addEffect(startWaveform.build());
                        } else {
                            startComposition.repeatEffectIndefinitely(startWaveform.build());
                        }
                    }
                } else {
                    getNextArgRequired();
                    int i4 = 0;
                    while (true) {
                        String nextOption3 = getNextOption();
                        if (nextOption3 == null) {
                            break;
                        }
                        if ("-a".equals(nextOption3)) {
                            i2 = 1;
                        } else if ("-w".equals(nextOption3)) {
                            i4 = Integer.parseInt(getNextArgRequired());
                        }
                    }
                    long parseLong = Long.parseLong(getNextArgRequired());
                    r3 = i2 != 0 ? Integer.parseInt(getNextArgRequired()) : -1;
                    startComposition.addOffDuration(Duration.ofMillis(i4));
                    startComposition.addEffect(VibrationEffect.createOneShot(parseLong, r3));
                }
            }
            return startComposition.compose();
        }

        /* JADX WARN: Type inference failed for: r7v3, types: [android.os.IBinder, com.android.server.vibrator.VibratorManagerService] */
        public final int onCommand(String str) {
            Trace.traceBegin(8388608L, "onCommand " + str);
            try {
                if ("list".equals(str)) {
                    runListVibrators();
                    return 0;
                }
                if ("synced".equals(str)) {
                    runVibrate(new CommonOptions(this), CombinedVibration.createParallel(nextEffect()));
                    return 0;
                }
                if ("combined".equals(str)) {
                    CommonOptions commonOptions = new CommonOptions(this);
                    CombinedVibration.ParallelCombination startParallel = CombinedVibration.startParallel();
                    while ("-v".equals(getNextOption())) {
                        startParallel.addVibrator(Integer.parseInt(getNextArgRequired()), nextEffect());
                    }
                    runVibrate(commonOptions, startParallel.combine());
                    return 0;
                }
                if ("sequential".equals(str)) {
                    CommonOptions commonOptions2 = new CommonOptions(this);
                    CombinedVibration.SequentialCombination startSequential = CombinedVibration.startSequential();
                    while ("-v".equals(getNextOption())) {
                        startSequential.addNext(Integer.parseInt(getNextArgRequired()), nextEffect());
                    }
                    runVibrate(commonOptions2, startSequential.combine());
                    return 0;
                }
                if ("xml".equals(str)) {
                    runXml();
                    return 0;
                }
                if ("cancel".equals(str)) {
                    ?? r7 = VibratorManagerService.this;
                    r7.cancelVibrate(-1, r7);
                    return 0;
                }
                if (!"feedback".equals(str)) {
                    return handleDefaultCommands(str);
                }
                runHapticFeedback();
                return 0;
            } finally {
                Trace.traceEnd(8388608L);
            }
        }

        public final void onHelp() {
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
                outPrintWriter.println("  xml [options] <xml>");
                outPrintWriter.println("    Vibrates using combined vibration described in given XML string");
                outPrintWriter.println("    on all vibrators in sync. The XML could be:");
                outPrintWriter.println("        XML containing a single effect, or");
                outPrintWriter.println("        A vibration select XML containing multiple effects.");
                outPrintWriter.println("    Vibrates using combined vibration described in given XML string.");
                outPrintWriter.println("    XML containing a single effect it runs on all vibrators in sync.");
                outPrintWriter.println("  cancel");
                outPrintWriter.println("    Cancels any active vibration");
                outPrintWriter.println("  feedback [-f] [-d <description>] <constant>");
                outPrintWriter.println("    Performs a haptic feedback with the given constant.");
                outPrintWriter.println("    The force (-f) option enables the `always` configuration, which");
                outPrintWriter.println("    plays the haptic irrespective of the vibration intensity settings");
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

        public final void runHapticFeedback() {
            boolean z = false;
            String str = "Shell command";
            boolean z2 = false;
            while (true) {
                String peekNextArg = peekNextArg();
                if (peekNextArg != null) {
                    switch (peekNextArg) {
                        case "-B":
                            getNextArgRequired();
                            z2 = true;
                            continue;
                        case "-d":
                            getNextArgRequired();
                            str = getNextArgRequired();
                            continue;
                        case "-f":
                            getNextArgRequired();
                            z = true;
                            continue;
                        case "-p":
                            getNextArgRequired();
                            getNextArgRequired();
                            continue;
                    }
                }
            }
            HalVibration performHapticFeedbackInternal = VibratorManagerService.this.performHapticFeedbackInternal(Binder.getCallingUid(), 0, "com.android.shell", Integer.parseInt(getNextArgRequired()), z, str, z2 ? VibratorManagerService.this : this.mShellCallbacksToken, false);
            if (performHapticFeedbackInternal == null || z2) {
                return;
            }
            try {
                performHapticFeedbackInternal.mCompletionLatch.await();
                VibratorManagerService.this.mVibrationThread.waitForThreadIdle(r12.mVibrationSettings.mVibrationConfig.getRampDownDurationMs() + 500);
            } catch (InterruptedException unused) {
            }
        }

        public final void runListVibrators() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            try {
                int[] iArr = VibratorManagerService.this.mVibratorIds;
                if (iArr.length == 0) {
                    outPrintWriter.println("No vibrator found");
                } else {
                    for (int i : iArr) {
                        outPrintWriter.println(i);
                    }
                }
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

        public final void runVibrate(CommonOptions commonOptions, CombinedVibration combinedVibration) {
            VibrationAttributes build = new VibrationAttributes.Builder().setFlags(commonOptions.force ? 19 : 0).setUsage(65).build();
            IVibratorManagerService.Stub stub = commonOptions.background ? VibratorManagerService.this : this.mShellCallbacksToken;
            int callingUid = Binder.getCallingUid();
            HalVibration vibrateWithPermissionCheck = VibratorManagerService.this.vibrateWithPermissionCheck(callingUid, 0, AppOpsManager.resolvePackageName(callingUid, commonOptions.pkgName), combinedVibration, build, commonOptions.description, stub);
            if (vibrateWithPermissionCheck == null || commonOptions.background) {
                return;
            }
            try {
                vibrateWithPermissionCheck.mCompletionLatch.await();
                VibratorManagerService.this.mVibrationThread.waitForThreadIdle(r9.mVibrationSettings.mVibrationConfig.getRampDownDurationMs() + 500);
            } catch (InterruptedException unused) {
            }
        }

        public final void runXml() {
            CommonOptions commonOptions = new CommonOptions(this);
            String nextArgRequired = getNextArgRequired();
            try {
                ParsedVibration parseDocument = VibrationXmlParser.parseDocument(new StringReader(nextArgRequired));
                if (parseDocument == null) {
                    throw new IllegalArgumentException("Error parsing vibration XML " + nextArgRequired);
                }
                VibratorManagerService vibratorManagerService = VibratorManagerService.this;
                VibrationAttributes vibrationAttributes = VibratorManagerService.DEFAULT_ATTRIBUTES;
                VibratorInfo combinedVibratorInfo = vibratorManagerService.getCombinedVibratorInfo();
                if (combinedVibratorInfo == null) {
                    throw new IllegalStateException("No combined vibrator info to parse vibration XML " + nextArgRequired);
                }
                VibrationEffect resolve = parseDocument.resolve(combinedVibratorInfo);
                if (resolve != null) {
                    runVibrate(commonOptions, CombinedVibration.createParallel(resolve));
                } else {
                    throw new IllegalArgumentException("Parsed vibration cannot be resolved for vibration XML " + nextArgRequired);
                }
            } catch (IOException e) {
                throw new RuntimeException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Error parsing vibration XML ", nextArgRequired), e);
            }
        }
    }

    /* renamed from: -$$Nest$mreportFinishedVibrationLocked, reason: not valid java name */
    public static void m1032$$Nest$mreportFinishedVibrationLocked(VibratorManagerService vibratorManagerService, Vibration.EndInfo endInfo) {
        vibratorManagerService.getClass();
        Trace.traceBegin(8388608L, "reportFinishVibrationLocked");
        Trace.asyncTraceEnd(8388608L, "vibration", 0);
        try {
            HalVibration halVibration = vibratorManagerService.mCurrentVibration.mVibration;
            Slog.d("VibratorManagerService", "Reporting vibration " + halVibration.id + " finished with " + endInfo);
            vibratorManagerService.endVibrationLocked(halVibration, endInfo, false);
            Vibration.CallerInfo callerInfo = halVibration.callerInfo;
            vibratorManagerService.mAppOps.finishOp(3, callerInfo.uid, callerInfo.opPkg);
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0032, code lost:
    
        if (((java.util.HashSet) com.android.server.vibrator.VibrationSettings.POWER_MANAGER_SLEEP_REASON_ALLOWLIST).contains(java.lang.Integer.valueOf(r7.goToSleepReason)) != false) goto L14;
     */
    /* renamed from: -$$Nest$mshouldCancelOnScreenOffLocked, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean m1033$$Nest$mshouldCancelOnScreenOffLocked(com.android.server.vibrator.VibratorManagerService r6, com.android.server.vibrator.VibrationStepConductor r7) {
        /*
            r6.getClass()
            r0 = 0
            if (r7 != 0) goto L8
            goto L81
        L8:
            com.android.server.vibrator.HalVibration r7 = r7.mVibration
            com.android.server.vibrator.VibrationSettings r6 = r6.mVibrationSettings
            com.android.server.vibrator.Vibration$CallerInfo r1 = r7.callerInfo
            com.android.server.vibrator.VibrationStats r7 = r7.stats
            long r2 = r7.mCreateUptimeMillis
            java.lang.Object r7 = r6.mLock
            monitor-enter(r7)
            android.os.PowerManagerInternal r4 = r6.mPowerManagerInternal     // Catch: java.lang.Throwable -> L82
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L82
            if (r4 == 0) goto L58
            android.os.PowerManager$SleepData r7 = r4.getLastGoToSleep()
            long r4 = r7.goToSleepUptimeMillis
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 < 0) goto L34
            java.util.Set r2 = com.android.server.vibrator.VibrationSettings.POWER_MANAGER_SLEEP_REASON_ALLOWLIST
            int r3 = r7.goToSleepReason
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.util.HashSet r2 = (java.util.HashSet) r2
            boolean r2 = r2.contains(r3)
            if (r2 == 0) goto L58
        L34:
            java.lang.String r6 = "VibrationSettings"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Ignoring screen off event triggered at uptime "
            r1.<init>(r2)
            long r2 = r7.goToSleepUptimeMillis
            r1.append(r2)
            java.lang.String r2 = " for reason "
            r1.append(r2)
            int r7 = r7.goToSleepReason
            java.lang.String r7 = android.os.PowerManager.sleepReasonToString(r7)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            android.util.Slog.d(r6, r7)
            goto L81
        L58:
            java.util.Set r7 = com.android.server.vibrator.VibrationSettings.SYSTEM_VIBRATION_SCREEN_OFF_USAGE_ALLOWLIST
            android.os.VibrationAttributes r2 = r1.attrs
            int r2 = r2.getUsage()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.util.HashSet r7 = (java.util.HashSet) r7
            boolean r7 = r7.contains(r2)
            r2 = 1
            if (r7 != 0) goto L6e
            goto L80
        L6e:
            int r7 = r1.uid
            r3 = 1000(0x3e8, float:1.401E-42)
            if (r7 == r3) goto L81
            if (r7 == 0) goto L81
            java.lang.String r6 = r6.mSystemUiPackage
            java.lang.String r7 = r1.opPkg
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L81
        L80:
            r0 = r2
        L81:
            return r0
        L82:
            r6 = move-exception
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L82
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.vibrator.VibratorManagerService.m1033$$Nest$mshouldCancelOnScreenOffLocked(com.android.server.vibrator.VibratorManagerService, com.android.server.vibrator.VibrationStepConductor):boolean");
    }

    public VibratorManagerService(Context context, Injector injector) {
        Object obj = new Object();
        this.mLock = obj;
        VibrationThreadCallbacks vibrationThreadCallbacks = new VibrationThreadCallbacks();
        vibrationThreadCallbacks.this$0 = this;
        this.mVibrationThreadCallbacks = vibrationThreadCallbacks;
        this.mAlwaysOnEffects = new SparseArray();
        this.mIntentReceiver = new AnonymousClass1(this, 0);
        VibratorHqmData vibratorHqmData = new VibratorHqmData();
        vibratorHqmData.mLoggingData = new HashMap();
        this.mHqmLoggingData = vibratorHqmData;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, 1);
        this.mMotorTypeSetFlag = false;
        this.mContext = context;
        this.mInjector = injector;
        Looper myLooper = Looper.myLooper();
        injector.getClass();
        Handler handler = new Handler(myLooper);
        this.mHandler = handler;
        VibratorFrameworkStatsLogger vibratorFrameworkStatsLogger = new VibratorFrameworkStatsLogger(handler, 10, 300);
        this.mFrameworkStatsLogger = vibratorFrameworkStatsLogger;
        VibrationSettings vibrationSettings = new VibrationSettings(context, handler, new VibrationConfig(context.getResources()), this);
        this.mVibrationSettings = vibrationSettings;
        if (VibratorHelper.sInstance == null) {
            VibratorHelper.sInstance = new VibratorHelper();
        }
        this.mVibratorHelper = VibratorHelper.sInstance;
        IntentFilter m = VcnManagementService$$ExternalSyntheticOutline0.m("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED", "android.intent.action.BOOT_COMPLETED", "android.intent.action.ACTION_SHUTDOWN", "com.sec.media.action.VIBRTOR_LOGGING", "android.media.STREAM_DEVICES_CHANGED_ACTION");
        m.addAction("android.intent.action.HEADSET_PLUG");
        m.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        m.addAction("com.android.launcher3.quickstep.closeall");
        context.registerReceiver(anonymousClass1, m, 4);
        VibratorManagerInternal vibratorManagerInternal = new VibratorManagerInternal();
        vibratorManagerInternal.mServiceWeakReference = new WeakReference(this);
        LocalServices.addService(VibratorManagerInternal.class, vibratorManagerInternal);
        VibrationScaler vibrationScaler = new VibrationScaler(context, vibrationSettings);
        this.mVibrationScaler = vibrationScaler;
        this.mVibratorControlService = new VibratorControlService(context, new VibratorControllerHolder(), vibrationScaler, vibrationSettings, vibratorFrameworkStatsLogger, obj);
        this.mInputDeviceDelegate = new InputDeviceDelegate(context, handler);
        VibrationThreadCallbacks vibrationThreadCallbacks2 = new VibrationThreadCallbacks();
        vibrationThreadCallbacks2.this$0 = new WeakReference(this);
        NativeWrapper nativeWrapper = new NativeWrapper();
        this.mNativeWrapper = nativeWrapper;
        nativeWrapper.init(vibrationThreadCallbacks2);
        this.mVibratorManagerRecords = new VibratorManagerRecords(context.getResources().getInteger(R.integer.config_sidefpsBpPowerPressWindow), context.getResources().getInteger(R.integer.config_shortPressOnSleepBehavior), context.getResources().getInteger(R.integer.config_shortPressOnSettingsBehavior));
        this.mBatteryStatsService = IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, "*vibrator*");
        newWakeLock.setReferenceCounted(true);
        VibrationThread vibrationThread = new VibrationThread(newWakeLock, vibrationThreadCallbacks);
        this.mVibrationThread = vibrationThread;
        vibrationThread.start();
        this.mCapabilities = nativeGetCapabilities(nativeWrapper.mNativeServicePtr);
        int[] nativeGetVibratorIds = nativeGetVibratorIds(nativeWrapper.mNativeServicePtr);
        if (nativeGetVibratorIds == null) {
            this.mVibratorIds = new int[0];
            this.mVibrators = new SparseArray(0);
        } else {
            this.mVibratorIds = nativeGetVibratorIds;
            this.mVibrators = new SparseArray(nativeGetVibratorIds.length);
            for (int i : nativeGetVibratorIds) {
                this.mVibrators.put(i, new VibratorController(i, vibrationThreadCallbacks2, new VibratorController.NativeWrapper()));
            }
        }
        this.mDeviceAdapter = new DeviceAdapter(this.mVibrationSettings, this.mVibrators);
        this.mNativeWrapper.cancelSynced();
        for (int i2 = 0; i2 < this.mVibrators.size(); i2++) {
            VibratorController vibratorController = (VibratorController) this.mVibrators.valueAt(i2);
            vibratorController.setExternalControl(false);
            vibratorController.off();
        }
        context.registerReceiver(this.mIntentReceiver, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_OFF"), 4);
        ServiceManager.addService("external_vibrator_service", new ExternalVibratorService());
        if (ServiceManager.isDeclared("android.frameworks.vibrator.IVibratorControlService/default")) {
            ServiceManager.addService("android.frameworks.vibrator.IVibratorControlService/default", this.mVibratorControlService);
        }
    }

    public static int getVibrationImportance(Vibration vibration) {
        int usage = vibration.callerInfo.attrs.getUsage();
        if (usage == 0) {
            usage = vibration.isRepeating() ? 33 : 18;
        }
        if (vibration.isRepeating()) {
            return 6;
        }
        Vibration.CallerInfo callerInfo = vibration.callerInfo;
        if (usage != 33) {
            if (usage != 34) {
                if (usage == 49) {
                    String str = callerInfo.opPkg;
                    VibratorHelper vibratorHelper = VibratorHelper.sInstance;
                    if (TextUtils.equals(str, "com.nttdocomo.android.phonemotion")) {
                        return 7;
                    }
                } else if (usage != 50 && usage != 65 && usage != 66) {
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
            }
        } else if (!"android".equals(callerInfo.opPkg) || !callerInfo.attrs.hasTag("VIBRATE_CALL")) {
            return 5;
        }
        return 3;
    }

    public static native void nativeCancelSynced(long j);

    public static native long nativeGetCapabilities(long j);

    public static native long nativeGetFinalizer();

    public static native int[] nativeGetVibratorIds(long j);

    public static native long nativeInit(OnSyncedVibrationCompleteListener onSyncedVibrationCompleteListener);

    public static native boolean nativePrepareSynced(long j, int[] iArr);

    public static native boolean nativeTriggerSynced(long j, long j2);

    public static boolean shouldCancelVibration(VibrationAttributes vibrationAttributes, int i) {
        return vibrationAttributes.getUsage() == 0 ? i == 0 || i == -1 : (i & vibrationAttributes.getUsage()) == vibrationAttributes.getUsage();
    }

    public static Vibration.EndInfo shouldIgnoreVibrationForOngoing(Vibration vibration, Vibration vibration2) {
        int vibrationImportance = getVibrationImportance(vibration);
        int vibrationImportance2 = getVibrationImportance(vibration2);
        if (vibrationImportance > vibrationImportance2) {
            return null;
        }
        Vibration.CallerInfo callerInfo = vibration2.callerInfo;
        if (vibrationImportance2 > vibrationImportance) {
            return new Vibration.EndInfo(Vibration.Status.IGNORED_FOR_HIGHER_IMPORTANCE, callerInfo);
        }
        if (!vibration2.isRepeating() || vibration.isRepeating()) {
            return null;
        }
        return new Vibration.EndInfo(Vibration.Status.IGNORED_FOR_ONGOING, callerInfo);
    }

    public final String addCustomDump() {
        StringBuilder sb = new StringBuilder("\nVibrator information\n  Motor type : ");
        sb.append(VibratorHelper.getMotorTypeToString(getSupportedMotorType()));
        sb.append("\n  Motor group : ");
        int supportedVibratorGroup = getSupportedVibratorGroup();
        sb.append(supportedVibratorGroup != 1 ? supportedVibratorGroup != 2 ? supportedVibratorGroup != 3 ? supportedVibratorGroup != 4 ? "SEM_SUPPORTED_VIBRATION_NONE" : "SEM_SUPPORTED_VIBRATION_TYPE_D" : "SEM_SUPPORTED_VIBRATION_TYPE_C" : "SEM_SUPPORTED_VIBRATION_TYPE_B" : "SEM_SUPPORTED_VIBRATION_TYPE_A");
        sb.append("\n    prebakedHapticPattern : ");
        sb.append(this.mVibrators.get(0) != null ? ((VibratorController) this.mVibrators.get(0)).mSupportPrebakedHapticPattern : false);
        sb.append("\n    hapticEngine : ");
        sb.append(this.mVibrators.get(0) != null ? ((VibratorController) this.mVibrators.get(0)).mSupportHapticEngine : false);
        sb.append("\n    enhancedSamsungHapticPattern : ");
        sb.append(this.mVibrators.get(0) != null ? ((VibratorController) this.mVibrators.get(0)).mSupportEnhancedSamsungHapticPattern : false);
        sb.append("\n    intensityControl : ");
        sb.append(this.mVibrators.get(0) != null ? ((VibratorController) this.mVibrators.get(0)).mSupportIntensityControl : false);
        sb.append("\n    indexWideBand : ");
        sb.append(this.mVibrators.get(0) != null ? ((VibratorController) this.mVibrators.get(0)).mSupportIndexWideBand : false);
        sb.append("\n  SUPPORT_ACH : ");
        sb.append(VibRune.SUPPORT_ACH);
        sb.append("\n\n");
        VibrationSettings vibrationSettings = this.mVibrationSettings;
        StringBuilder sb2 = new StringBuilder("VibrationSettings information\n  mIsHapticSupported = ");
        VibrationCustomSettings vibrationCustomSettings = vibrationSettings.mCustomSettings;
        sb2.append(vibrationCustomSettings.mIsHapticSupported);
        sb2.append("\n  mIsEnableIntensity = ");
        sb2.append(vibrationCustomSettings.mIsEnableIntensity);
        sb2.append("\n  mCallMagnitude = ");
        sb2.append(vibrationCustomSettings.mCallMagnitude);
        sb2.append("\n  mNotiMagnitude = ");
        sb2.append(vibrationCustomSettings.mNotiMagnitude);
        sb2.append("\n  mTouchMagnitude = ");
        sb2.append(vibrationCustomSettings.mTouchMagnitude);
        sb2.append("\n  mMediaMagnitude = ");
        sb2.append(vibrationCustomSettings.mMediaMagnitude);
        sb2.append("\n  mForceMagnitude = ");
        sb2.append(vibrationCustomSettings.mForceMagnitude);
        sb2.append("\n  LEVEL_TO_MAGNITUDE = ");
        sb2.append(Arrays.toString(vibrationCustomSettings.LEVEL_TO_MAGNITUDE));
        sb2.append("\n  LEVEL_TO_TOUCH_MAGNITUDE = ");
        sb2.append(Arrays.toString(vibrationCustomSettings.LEVEL_TO_TOUCH_MAGNITUDE));
        sb2.append("\n");
        sb.append(sb2.toString());
        return sb.toString();
    }

    public final void cancelCurrentVibration() {
        Vibration.Status status = Vibration.Status.CANCELLED_SERVICE_RECOVERED;
        ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
        if (externalVibrationHolder != null) {
            endVibrationAndWriteStatsLocked(externalVibrationHolder, new Vibration.EndInfo(status, null));
            this.mCurrentExternalVibration.externalVibration.mute();
            setExternalControl(false, this.mCurrentExternalVibration.stats);
            this.mCurrentExternalVibration = null;
        }
        VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
        if (vibrationStepConductor != null) {
            vibrationStepConductor.notifyCancelled(new Vibration.EndInfo(status, null), true);
        }
        VirtualVibSoundHelper virtualVibSoundHelper = this.mVirtualVibSoundHelper;
        if (virtualVibSoundHelper != null) {
            virtualVibSoundHelper.stopVirtualSound("recovery");
        }
    }

    public final void cancelVibrate(int i, IBinder iBinder) {
        boolean z;
        ExternalVibrationHolder externalVibrationHolder;
        VirtualVibSoundHelper virtualVibSoundHelper;
        Trace.traceBegin(8388608L, "cancelVibrate");
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.VIBRATE", "cancelVibrate");
            synchronized (this.mLock) {
                Slog.d("VibratorManagerService", "Canceling vibration");
                Vibration.EndInfo endInfo = new Vibration.EndInfo(Vibration.Status.CANCELLED_BY_USER, null);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    VibrationStepConductor vibrationStepConductor = this.mNextVibration;
                    if (vibrationStepConductor != null) {
                        HalVibration halVibration = vibrationStepConductor.mVibration;
                        if (halVibration.callerToken == iBinder && shouldCancelVibration(halVibration.callerInfo.attrs, i)) {
                            clearNextVibrationLocked(endInfo);
                        }
                    }
                    VibrationStepConductor vibrationStepConductor2 = this.mCurrentVibration;
                    boolean z2 = true;
                    if (vibrationStepConductor2 != null) {
                        HalVibration halVibration2 = vibrationStepConductor2.mVibration;
                        if (halVibration2.callerToken == iBinder && shouldCancelVibration(halVibration2.callerInfo.attrs, i)) {
                            this.mCurrentVibration.notifyCancelled(endInfo, false);
                            z = true;
                            externalVibrationHolder = this.mCurrentExternalVibration;
                            if (externalVibrationHolder != null || !shouldCancelVibration(externalVibrationHolder.externalVibration.getVibrationAttributes(), i)) {
                                z2 = z;
                            } else if (this.mCurrentExternalVibration.externalVibration.isRepeating()) {
                                Log.d("VibratorManagerService", "Keeping repeating external vibration");
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            } else {
                                this.mCurrentExternalVibration.externalVibration.mute();
                                endExternalVibrateLocked(endInfo, false);
                            }
                            if (z2 && (virtualVibSoundHelper = this.mVirtualVibSoundHelper) != null) {
                                virtualVibSoundHelper.stopVirtualSound("cancel");
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    z = false;
                    externalVibrationHolder = this.mCurrentExternalVibration;
                    if (externalVibrationHolder != null) {
                    }
                    z2 = z;
                    if (z2) {
                        virtualVibSoundHelper.stopVirtualSound("cancel");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public final void clearNextVibrationLocked(Vibration.EndInfo endInfo) {
        if (this.mNextVibration != null) {
            Slog.d("VibratorManagerService", "Dropping pending vibration " + this.mNextVibration.mVibration.id + " with end info: " + endInfo);
            endVibrationLocked(this.mNextVibration.mVibration, endInfo, true);
            this.mNextVibration = null;
        }
    }

    public final VibrationStepConductor createVibrationStepConductor(HalVibration halVibration) {
        boolean contains;
        CompletableFuture completableFuture = null;
        if (Flags.adaptiveHapticsEnabled()) {
            int i = 16;
            if (!halVibration.callerInfo.attrs.isFlagSet(16)) {
                VibratorControlService vibratorControlService = this.mVibratorControlService;
                int usage = halVibration.callerInfo.attrs.getUsage();
                synchronized (vibratorControlService.mLock) {
                    try {
                        contains = vibratorControlService.mVibratorControllerHolder.mVibratorController == null ? false : ArrayUtils.contains(vibratorControlService.mRequestVibrationParamsForUsages, usage);
                    } finally {
                    }
                }
                if (contains) {
                    VibratorControlService vibratorControlService2 = this.mVibratorControlService;
                    Vibration.CallerInfo callerInfo = halVibration.callerInfo;
                    int i2 = callerInfo.uid;
                    int usage2 = callerInfo.attrs.getUsage();
                    int requestVibrationParamsTimeoutMs = this.mVibrationSettings.mVibrationConfig.getRequestVibrationParamsTimeoutMs();
                    synchronized (vibratorControlService2.mLock) {
                        try {
                            IVibratorController iVibratorController = vibratorControlService2.mVibratorControllerHolder.mVibratorController;
                            if (iVibratorController == null) {
                                Slog.d("VibratorControlService", "Unable to request vibration params. There is no registered IVibrationController.");
                            } else {
                                if (usage2 != 0) {
                                    if (usage2 != 33) {
                                        if (usage2 != 34) {
                                            if (usage2 != 49) {
                                                if (usage2 != 50) {
                                                    if (usage2 != 65) {
                                                        if (usage2 != 66) {
                                                            switch (usage2) {
                                                                case 17:
                                                                    i = 1;
                                                                    break;
                                                                case 18:
                                                                    break;
                                                                case 19:
                                                                    break;
                                                                default:
                                                                    Slog.w("VibratorControlService", "Unrecognized vibration usage " + usage2);
                                                                    i = -1;
                                                                    break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            i = 2;
                                        }
                                        i = 8;
                                    } else {
                                        i = 4;
                                    }
                                }
                                if (i == -1) {
                                    Slog.d("VibratorControlService", "Unable to request vibration params. The provided usage " + usage2 + " is unrecognized.");
                                } else {
                                    try {
                                        vibratorControlService2.endOngoingRequestVibrationParamsLocked(true);
                                        VibratorControlService.VibrationParamRequest vibrationParamRequest = new VibratorControlService.VibrationParamRequest(i2);
                                        vibratorControlService2.mVibrationParamRequest = vibrationParamRequest;
                                        ((IVibratorController.Stub.Proxy) iVibratorController).requestVibrationParams(vibrationParamRequest.token, requestVibrationParamsTimeoutMs, i);
                                        completableFuture = vibratorControlService2.mVibrationParamRequest.future;
                                    } catch (RemoteException e) {
                                        Slog.e("VibratorControlService", "Failed to request vibration params.", e);
                                        vibratorControlService2.endOngoingRequestVibrationParamsLocked(true);
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                }
            }
        }
        return new VibrationStepConductor(halVibration, this.mVibrationSettings, this.mDeviceAdapter, this.mVibrationScaler, this.mFrameworkStatsLogger, completableFuture, this.mVibrationThreadCallbacks);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
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
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void dumpProto(FileDescriptor fileDescriptor) {
        boolean z;
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        Slog.d("VibratorManagerService", "Dumping vibrator manager service to proto...");
        synchronized (this.mLock) {
            try {
                this.mVibrationSettings.dump(protoOutputStream);
                protoOutputStream.write(1120986464282L, this.mVibrationScaler.mDefaultVibrationAmplitude);
                VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
                if (vibrationStepConductor != null) {
                    vibrationStepConductor.mVibration.getDebugInfo().dump(protoOutputStream, 1146756268034L);
                }
                ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
                if (externalVibrationHolder != null) {
                    externalVibrationHolder.getDebugInfo().dump(protoOutputStream, 1146756268036L);
                }
                boolean z2 = false;
                boolean z3 = false;
                for (int i = 0; i < this.mVibrators.size(); i++) {
                    protoOutputStream.write(2220498092033L, this.mVibrators.keyAt(i));
                    z2 |= ((VibratorController) this.mVibrators.valueAt(i)).mIsVibrating;
                    z3 |= ((VibratorController) this.mVibrators.valueAt(i)).mIsUnderExternalControl;
                }
                protoOutputStream.write(1133871366147L, z2);
                protoOutputStream.write(1133871366149L, z3);
            } catch (Throwable th) {
                throw th;
            }
        }
        VibratorManagerRecords vibratorManagerRecords = this.mVibratorManagerRecords;
        synchronized (vibratorManagerRecords) {
            vibratorManagerRecords.mRecentVibrations.dump(protoOutputStream);
        }
        VibratorControlService vibratorControlService = this.mVibratorControlService;
        synchronized (vibratorControlService.mLock) {
            z = vibratorControlService.mVibratorControllerHolder.mVibratorController != null;
        }
        protoOutputStream.write(1120986464283L, z);
        vibratorControlService.mVibrationParamsRecords.dump(protoOutputStream);
        protoOutputStream.flush();
    }

    public final void dumpText(PrintWriter printWriter) {
        boolean z;
        boolean z2;
        Slog.d("VibratorManagerService", "Dumping vibrator manager service to text...");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("VibratorManagerService:");
                indentingPrintWriter.increaseIndent();
                this.mVibrationSettings.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                this.mVibrationScaler.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Vibrators:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mVibrators.size(); i++) {
                    ((VibratorController) this.mVibrators.valueAt(i)).dump(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("CurrentVibration:");
                indentingPrintWriter.increaseIndent();
                VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
                if (vibrationStepConductor != null) {
                    vibrationStepConductor.mVibration.getDebugInfo().dump(indentingPrintWriter);
                } else {
                    indentingPrintWriter.println("null");
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("NextVibration:");
                indentingPrintWriter.increaseIndent();
                VibrationStepConductor vibrationStepConductor2 = this.mNextVibration;
                if (vibrationStepConductor2 != null) {
                    vibrationStepConductor2.mVibration.getDebugInfo().dump(indentingPrintWriter);
                } else {
                    indentingPrintWriter.println("null");
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("CurrentExternalVibration:");
                indentingPrintWriter.increaseIndent();
                ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
                if (externalVibrationHolder != null) {
                    externalVibrationHolder.getDebugInfo().dump(indentingPrintWriter);
                } else {
                    indentingPrintWriter.println("null");
                }
                indentingPrintWriter.decreaseIndent();
            } finally {
            }
        }
        indentingPrintWriter.println();
        indentingPrintWriter.println();
        VibratorManagerRecords vibratorManagerRecords = this.mVibratorManagerRecords;
        synchronized (vibratorManagerRecords) {
            indentingPrintWriter.println("Recent vibrations:");
            indentingPrintWriter.increaseIndent();
            vibratorManagerRecords.mRecentVibrations.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println();
            indentingPrintWriter.println("Aggregated vibration history:");
            indentingPrintWriter.increaseIndent();
            vibratorManagerRecords.mAggregatedVibrationHistory.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.println();
        indentingPrintWriter.println();
        VibratorControlService vibratorControlService = this.mVibratorControlService;
        synchronized (vibratorControlService.mLock) {
            z = vibratorControlService.mVibratorControllerHolder.mVibratorController != null;
            z2 = vibratorControlService.mVibrationParamRequest != null;
        }
        indentingPrintWriter.println("VibratorControlService:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("isVibratorControllerRegistered = " + z);
        indentingPrintWriter.println("hasPendingVibrationParamsRequest = " + z2);
        indentingPrintWriter.println();
        indentingPrintWriter.println("Vibration parameters update history:");
        indentingPrintWriter.increaseIndent();
        vibratorControlService.mVibrationParamsRecords.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println(addCustomDump());
    }

    public final void endExternalVibrateLocked(Vibration.EndInfo endInfo, boolean z) {
        Trace.traceBegin(8388608L, "endExternalVibrateLocked");
        try {
            ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
            if (externalVibrationHolder == null) {
                Trace.traceEnd(8388608L);
                return;
            }
            externalVibrationHolder.externalVibration.unlinkToDeath(externalVibrationHolder);
            if (!z) {
                setExternalControl(false, this.mCurrentExternalVibration.stats);
            }
            endVibrationAndWriteStatsLocked(this.mCurrentExternalVibration, endInfo);
            this.mCurrentExternalVibration = null;
            Trace.traceEnd(8388608L);
        } catch (Throwable th) {
            Trace.traceEnd(8388608L);
            throw th;
        }
    }

    public final void endVibrationAndWriteStatsLocked(ExternalVibrationHolder externalVibrationHolder, Vibration.EndInfo endInfo) {
        if (externalVibrationHolder.mStatus == Vibration.Status.RUNNING) {
            externalVibrationHolder.mStatus = endInfo.status;
            Vibration.CallerInfo callerInfo = endInfo.endedBy;
            VibrationStats vibrationStats = externalVibrationHolder.stats;
            vibrationStats.reportEnded(callerInfo);
            long j = vibrationStats.mStartUptimeMillis;
            if (j > 0) {
                long j2 = vibrationStats.mEndUptimeMillis - j;
                vibrationStats.mVibratorOnCount++;
                if (j2 > 0) {
                    vibrationStats.mVibratorOnTotalDurationMillis += (int) j2;
                }
            }
        }
        logAndRecordVibration(externalVibrationHolder.getDebugInfo());
        this.mFrameworkStatsLogger.writeVibrationReportedAsync(new VibrationStats.StatsInfo(externalVibrationHolder.externalVibration.getUid(), 3, externalVibrationHolder.externalVibration.getVibrationAttributes().getUsage(), externalVibrationHolder.mStatus, externalVibrationHolder.stats, SystemClock.uptimeMillis()));
    }

    public final void endVibrationLocked(HalVibration halVibration, Vibration.EndInfo endInfo, boolean z) {
        if (halVibration.mStatus == Vibration.Status.RUNNING) {
            halVibration.mStatus = endInfo.status;
            halVibration.stats.reportEnded(endInfo.endedBy);
            halVibration.mCompletionLatch.countDown();
        }
        logAndRecordVibration(halVibration.getDebugInfo());
        if (z) {
            VibratorFrameworkStatsLogger vibratorFrameworkStatsLogger = this.mFrameworkStatsLogger;
            long uptimeMillis = SystemClock.uptimeMillis();
            int i = halVibration.isRepeating() ? 2 : 1;
            Vibration.CallerInfo callerInfo = halVibration.callerInfo;
            vibratorFrameworkStatsLogger.writeVibrationReportedAsync(new VibrationStats.StatsInfo(callerInfo.uid, i, callerInfo.attrs.getUsage(), halVibration.mStatus, halVibration.stats, uptimeMillis));
        }
    }

    public final String executeVibrationDebugCommand(VibrationDebugInfo vibrationDebugInfo) {
        PatternInfo copyPatternInfo;
        int command = vibrationDebugInfo.getCommand();
        if (command == 0) {
            return addCustomDump();
        }
        if (command == 1) {
            if (!SemHapticFeedbackConstants.isValidatedVibeIndex(vibrationDebugInfo.getIndex() + 50024)) {
                return "fail";
            }
            this.mVibratorHelper.mSepIndex = vibrationDebugInfo.getIndex();
            VibratorHelper vibratorHelper = this.mVibratorHelper;
            int i = vibratorHelper.mSepIndex;
            if (i == -1) {
                return "fail";
            }
            return "Sep index : " + SemHapticFeedbackConstants.getPatternTitle(vibratorHelper.mSepIndex + 50024) + "(" + vibratorHelper.mSepIndex + ")\nduration : " + (vibratorHelper.mVibePatternHash.containsKey(Integer.valueOf(i)) ? ((PatternInfo) vibratorHelper.mVibePatternHash.get(Integer.valueOf(vibratorHelper.mSepIndex))).duration : 5000);
        }
        if (command != 2) {
            if (command != 3) {
                return null;
            }
            VibratorHelper vibratorHelper2 = this.mVibratorHelper;
            for (Map.Entry entry : vibratorHelper2.mChangedPatternHash.entrySet()) {
                vibratorHelper2.mVibePatternHash.replace((Integer) entry.getKey(), (PatternInfo) entry.getValue());
            }
            vibratorHelper2.mChangedPatternHash.clear();
            return "success";
        }
        VibratorHelper vibratorHelper3 = this.mVibratorHelper;
        int duration = vibrationDebugInfo.getDuration();
        int i2 = vibratorHelper3.mSepIndex;
        if (i2 == -1 || (copyPatternInfo = vibratorHelper3.copyPatternInfo(i2)) == null) {
            return "fail";
        }
        if (vibratorHelper3.mChangedPatternHash.get(Integer.valueOf(vibratorHelper3.mSepIndex)) == null) {
            vibratorHelper3.mChangedPatternHash.put(Integer.valueOf(vibratorHelper3.mSepIndex), copyPatternInfo);
        }
        PatternInfo copyPatternInfo2 = vibratorHelper3.copyPatternInfo(vibratorHelper3.mSepIndex);
        copyPatternInfo2.duration = duration;
        vibratorHelper3.mVibePatternHash.replace(Integer.valueOf(vibratorHelper3.mSepIndex), copyPatternInfo2);
        return "success";
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
                VibrationSettings vibrationSettings = this.mVibrationSettings;
                VibrationEffect vibrationEffect2 = (VibrationEffect) vibrationSettings.mFallbackEffects.get(prebakedSegment2.getEffectId());
                if (prebakedSegment2.shouldFallback() && vibrationEffect2 != null) {
                    halVibration.mFallbacks.put(prebakedSegment2.getEffectId(), vibrationEffect2);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x008b A[Catch: all -> 0x003c, TRY_ENTER, TryCatch #0 {all -> 0x003c, blocks: (B:3:0x0008, B:5:0x000e, B:6:0x0020, B:8:0x0028, B:10:0x0049, B:11:0x004f, B:13:0x0055, B:15:0x005f, B:17:0x006c, B:19:0x007a, B:31:0x0080, B:21:0x008b, B:23:0x0099, B:25:0x00a3, B:27:0x00a6, B:37:0x00a9, B:43:0x003f, B:45:0x0043), top: B:2:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0080 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.util.SparseArray fixupAlwaysOnEffectsLocked(android.os.CombinedVibration r12) {
        /*
            r11 = this;
            java.lang.String r0 = "fixupAlwaysOnEffectsLocked"
            r1 = 8388608(0x800000, double:4.144523E-317)
            android.os.Trace.traceBegin(r1, r0)
            boolean r0 = r12 instanceof android.os.CombinedVibration.Mono     // Catch: java.lang.Throwable -> L3c
            r3 = 0
            r4 = 0
            if (r0 == 0) goto L3f
            android.os.CombinedVibration$Mono r12 = (android.os.CombinedVibration.Mono) r12     // Catch: java.lang.Throwable -> L3c
            android.os.VibrationEffect r12 = r12.getEffect()     // Catch: java.lang.Throwable -> L3c
            android.util.SparseArray r0 = new android.util.SparseArray     // Catch: java.lang.Throwable -> L3c
            android.util.SparseArray r5 = r11.mVibrators     // Catch: java.lang.Throwable -> L3c
            int r5 = r5.size()     // Catch: java.lang.Throwable -> L3c
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L3c
            r5 = r3
        L20:
            android.util.SparseArray r6 = r11.mVibrators     // Catch: java.lang.Throwable -> L3c
            int r6 = r6.size()     // Catch: java.lang.Throwable -> L3c
            if (r5 >= r6) goto L49
            android.util.SparseArray r6 = r11.mVibrators     // Catch: java.lang.Throwable -> L3c
            int r6 = r6.keyAt(r5)     // Catch: java.lang.Throwable -> L3c
            android.util.SparseArray r7 = r11.mVibrators     // Catch: java.lang.Throwable -> L3c
            java.lang.Object r7 = r7.valueAt(r5)     // Catch: java.lang.Throwable -> L3c
            com.android.server.vibrator.VibratorController r7 = (com.android.server.vibrator.VibratorController) r7     // Catch: java.lang.Throwable -> L3c
            r0.put(r6, r12)     // Catch: java.lang.Throwable -> L3c
            int r5 = r5 + 1
            goto L20
        L3c:
            r11 = move-exception
            goto Lbb
        L3f:
            boolean r0 = r12 instanceof android.os.CombinedVibration.Stereo     // Catch: java.lang.Throwable -> L3c
            if (r0 == 0) goto Lb7
            android.os.CombinedVibration$Stereo r12 = (android.os.CombinedVibration.Stereo) r12     // Catch: java.lang.Throwable -> L3c
            android.util.SparseArray r0 = r12.getEffects()     // Catch: java.lang.Throwable -> L3c
        L49:
            android.util.SparseArray r12 = new android.util.SparseArray     // Catch: java.lang.Throwable -> L3c
            r12.<init>()     // Catch: java.lang.Throwable -> L3c
            r5 = r3
        L4f:
            int r6 = r0.size()     // Catch: java.lang.Throwable -> L3c
            if (r5 >= r6) goto La9
            java.lang.Object r6 = r0.valueAt(r5)     // Catch: java.lang.Throwable -> L3c
            android.os.VibrationEffect r6 = (android.os.VibrationEffect) r6     // Catch: java.lang.Throwable -> L3c
            boolean r7 = r6 instanceof android.os.VibrationEffect.Composed     // Catch: java.lang.Throwable -> L3c
            if (r7 == 0) goto L7d
            android.os.VibrationEffect$Composed r6 = (android.os.VibrationEffect.Composed) r6     // Catch: java.lang.Throwable -> L3c
            java.util.List r7 = r6.getSegments()     // Catch: java.lang.Throwable -> L3c
            int r7 = r7.size()     // Catch: java.lang.Throwable -> L3c
            r8 = 1
            if (r7 != r8) goto L7d
            java.util.List r6 = r6.getSegments()     // Catch: java.lang.Throwable -> L3c
            java.lang.Object r6 = r6.get(r3)     // Catch: java.lang.Throwable -> L3c
            android.os.vibrator.VibrationEffectSegment r6 = (android.os.vibrator.VibrationEffectSegment) r6     // Catch: java.lang.Throwable -> L3c
            boolean r7 = r6 instanceof android.os.vibrator.PrebakedSegment     // Catch: java.lang.Throwable -> L3c
            if (r7 == 0) goto L7d
            android.os.vibrator.PrebakedSegment r6 = (android.os.vibrator.PrebakedSegment) r6     // Catch: java.lang.Throwable -> L3c
            goto L7e
        L7d:
            r6 = r4
        L7e:
            if (r6 != 0) goto L8b
            java.lang.String r11 = "VibratorManagerService"
            java.lang.String r12 = "Only prebaked effects supported for always-on."
            android.util.Slog.e(r11, r12)     // Catch: java.lang.Throwable -> L3c
            android.os.Trace.traceEnd(r1)
            return r4
        L8b:
            int r7 = r0.keyAt(r5)     // Catch: java.lang.Throwable -> L3c
            android.util.SparseArray r8 = r11.mVibrators     // Catch: java.lang.Throwable -> L3c
            java.lang.Object r8 = r8.get(r7)     // Catch: java.lang.Throwable -> L3c
            com.android.server.vibrator.VibratorController r8 = (com.android.server.vibrator.VibratorController) r8     // Catch: java.lang.Throwable -> L3c
            if (r8 == 0) goto La6
            android.os.VibratorInfo r8 = r8.mVibratorInfo     // Catch: java.lang.Throwable -> L3c
            r9 = 64
            boolean r8 = r8.hasCapability(r9)     // Catch: java.lang.Throwable -> L3c
            if (r8 == 0) goto La6
            r12.put(r7, r6)     // Catch: java.lang.Throwable -> L3c
        La6:
            int r5 = r5 + 1
            goto L4f
        La9:
            int r11 = r12.size()     // Catch: java.lang.Throwable -> L3c
            if (r11 != 0) goto Lb3
            android.os.Trace.traceEnd(r1)
            return r4
        Lb3:
            android.os.Trace.traceEnd(r1)
            return r12
        Lb7:
            android.os.Trace.traceEnd(r1)
            return r4
        Lbb:
            android.os.Trace.traceEnd(r1)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.vibrator.VibratorManagerService.fixupAlwaysOnEffectsLocked(android.os.CombinedVibration):android.util.SparseArray");
    }

    public final VibrationAttributes fixupVibrationAttributes(VibrationAttributes vibrationAttributes, CombinedVibration combinedVibration) {
        if (vibrationAttributes == null) {
            vibrationAttributes = DEFAULT_ATTRIBUTES;
        }
        int usage = vibrationAttributes.getUsage();
        if (usage == 0 && combinedVibration != null && combinedVibration.isHapticFeedbackCandidate()) {
            usage = 18;
        }
        int flags = vibrationAttributes.getFlags();
        if ((flags & 19) != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            flags &= -20;
        }
        return (usage == vibrationAttributes.getUsage() && flags == vibrationAttributes.getFlags()) ? vibrationAttributes : new VibrationAttributes.Builder(vibrationAttributes).setUsage(usage).setFlags(flags, vibrationAttributes.getFlags()).build();
    }

    public final VibratorInfo getCombinedVibratorInfo() {
        synchronized (this.mLock) {
            try {
                VibratorInfo vibratorInfo = this.mCombinedVibratorInfo;
                if (vibratorInfo != null) {
                    return vibratorInfo;
                }
                int[] iArr = this.mVibratorIds;
                if (iArr.length == 0) {
                    VibratorInfo vibratorInfo2 = VibratorInfo.EMPTY_VIBRATOR_INFO;
                    this.mCombinedVibratorInfo = vibratorInfo2;
                    return vibratorInfo2;
                }
                VibratorInfo[] vibratorInfoArr = new VibratorInfo[iArr.length];
                int i = 0;
                while (true) {
                    int[] iArr2 = this.mVibratorIds;
                    if (i >= iArr2.length) {
                        VibratorInfo create = VibratorInfoFactory.create(-1, vibratorInfoArr);
                        this.mCombinedVibratorInfo = create;
                        return create;
                    }
                    VibratorInfo vibratorInfo3 = getVibratorInfo(iArr2[i]);
                    if (vibratorInfo3 == null) {
                        return null;
                    }
                    vibratorInfoArr[i] = vibratorInfo3;
                    i++;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final VibratorController getDefaultVibratorController() {
        return (VibratorController) this.mVibrators.get(0);
    }

    public final int getMagnitudeByUsage(VibrationAttributes vibrationAttributes) {
        if (vibrationAttributes.hasTag("INTENSITY_MAX")) {
            return this.mVibrationSettings.mCustomSettings.mMaxMagnitude;
        }
        if (vibrationAttributes.hasTag("INTENSITY_MIN")) {
            return this.mVibrationSettings.mCustomSettings.mMinMagnitude;
        }
        if (this.mComposed.semGetMagnitude() != -1) {
            return this.mComposed.semGetMagnitude();
        }
        return this.mVibrationSettings.getCurrentMagnitude(vibrationAttributes.getUsage());
    }

    public final int getSupportedMotorType() {
        if (this.mVibrators.get(0) != null) {
            return ((VibratorController) this.mVibrators.get(0)).getMotorType();
        }
        return 0;
    }

    public final int getSupportedVibratorGroup() {
        if (this.mVibrators.get(0) != null) {
            return ((VibratorController) this.mVibrators.get(0)).mVibratorGroup;
        }
        return 0;
    }

    public final int[] getVibratorIds() {
        int[] iArr = this.mVibratorIds;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public final VibratorInfo getVibratorInfo(int i) {
        VibratorController vibratorController = (VibratorController) this.mVibrators.get(i);
        if (vibratorController == null) {
            return null;
        }
        VibratorInfo vibratorInfo = vibratorController.mVibratorInfo;
        synchronized (this.mLock) {
            try {
                if (this.mServiceReady) {
                    return vibratorInfo;
                }
                if (vibratorController.mVibratorInfoLoadSuccessful) {
                    return vibratorInfo;
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isVibrating(int i) {
        isVibrating_enforcePermission();
        VibratorController vibratorController = (VibratorController) this.mVibrators.get(i);
        return vibratorController != null && vibratorController.mIsVibrating;
    }

    public final void logAndRecordVibration(Vibration.DebugInfo debugInfo) {
        VibratorFrameworkStatsLogger vibratorFrameworkStatsLogger = this.mFrameworkStatsLogger;
        int i = debugInfo.mCallerInfo.uid;
        vibratorFrameworkStatsLogger.getClass();
        float f = debugInfo.mAdaptiveScale;
        if (Float.compare(f, 1.0f) != 0) {
            VibratorFrameworkStatsLogger.sAdaptiveHapticScaleHistogram.logSampleWithUid(i, f);
        }
        Vibration.CallerInfo callerInfo = debugInfo.mCallerInfo;
        int i2 = callerInfo.uid;
        VibrationAttributes vibrationAttributes = callerInfo.attrs;
        Vibration.Status status = debugInfo.mStatus;
        int ordinal = status.ordinal();
        if (ordinal == 11) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i2, "Would be an error: vibrate from uid ", "VibratorManagerService");
        } else if (ordinal == 16) {
            Slog.e("VibratorManagerService", "Ignoring incoming vibration as process with uid= " + i2 + " is background, attrs= " + vibrationAttributes);
        } else if (ordinal == 23) {
            Slog.d("VibratorManagerService", "Ignoring incoming vibration because of ringer mode, attrs=" + vibrationAttributes);
        } else if (ordinal != 26) {
            switch (ordinal) {
                case 19:
                    Slog.d("VibratorManagerService", "Ignoring incoming vibration for current external vibration");
                    break;
                case 20:
                    Slog.d("VibratorManagerService", "Ignoring incoming vibration in favor of ongoing vibration with higher importance");
                    break;
                case 21:
                    Slog.d("VibratorManagerService", "Ignoring incoming vibration in favor of repeating vibration");
                    break;
                default:
                    Slog.d("VibratorManagerService", "Vibration for uid=" + i2 + " and with attrs=" + vibrationAttributes + " ended with status " + status);
                    break;
            }
        } else {
            Slog.d("VibratorManagerService", "Ignoring incoming vibration because it came from a virtual device, attrs= " + vibrationAttributes);
        }
        VibratorManagerRecords vibratorManagerRecords = this.mVibratorManagerRecords;
        synchronized (vibratorManagerRecords) {
            GroupedAggregatedLogRecords.AggregatedLogRecord add = vibratorManagerRecords.mRecentVibrations.add(new VibrationRecord(debugInfo));
            if (add != null) {
                vibratorManagerRecords.mAggregatedVibrationHistory.add((VibrationRecord) add.mLatest);
            }
        }
    }

    public final void onCustomSystemReady() {
        VibratorHelper vibratorHelper = this.mVibratorHelper;
        Context context = this.mContext;
        vibratorHelper.getClass();
        Resources resources = context.getResources();
        vibratorHelper.createPatternInfo(1, resources, 17236351, 4, 20);
        vibratorHelper.createPatternInfo(2, resources, 17236352, 4, 30);
        vibratorHelper.createPatternInfo(3, resources, 17236353, 2, 100);
        vibratorHelper.createPatternInfo(4, resources, 17236354, 3, 180);
        vibratorHelper.createPatternInfo(5, resources, 17236355, 1, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_ORGANIZATION_ID);
        vibratorHelper.createPatternInfo(6, resources, 17236356, 4, 20);
        vibratorHelper.createPatternInfo(7, resources, 17236357, 2, FrameworkStatsLog.BROADCAST_DELIVERY_EVENT_REPORTED);
        vibratorHelper.createPatternInfo(8, resources, 17236358, 0, FrameworkStatsLog.CREDENTIAL_MANAGER_AUTH_CLICK_REPORTED);
        vibratorHelper.createPatternInfo(9, resources, 17236359, 0, NetworkConstants.ETHER_MTU);
        vibratorHelper.createPatternInfo(10, resources, 17236360, 0, 1000);
        vibratorHelper.createPatternInfo(11, resources, 17236361, 0, 3500);
        vibratorHelper.createPatternInfo(12, resources, 17236362, 0, 2000);
        vibratorHelper.createPatternInfo(13, resources, 17236363, 0, 1600);
        vibratorHelper.createPatternInfo(14, resources, 17236364, 0, 30);
        vibratorHelper.createPatternInfo(15, resources, 17236365, 0, 30);
        vibratorHelper.createPatternInfo(16, resources, 17236366, 0, 30);
        vibratorHelper.createPatternInfo(17, resources, 17236367, 0, 3000);
        vibratorHelper.createPatternInfo(18, resources, 17236368, 0, 3100);
        vibratorHelper.createPatternInfo(19, resources, 17236369, 0, 4700);
        vibratorHelper.createPatternInfo(20, resources, 17236370, 0, 3100);
        vibratorHelper.createPatternInfo(21, resources, 17236371, 0, 3260);
        vibratorHelper.createPatternInfo(22, resources, 17236383, 0, 25);
        vibratorHelper.createPatternInfo(23, resources, 17236384, 0, 20);
        vibratorHelper.createPatternInfo(24, resources, 17236385, 0, 20);
        vibratorHelper.createPatternInfo(25, resources, 17236386, 0, 0);
        vibratorHelper.createPatternInfo(26, resources, 17236387, 0, 20);
        vibratorHelper.createPatternInfo(27, resources, 17236388, 0, 140);
        vibratorHelper.createPatternInfo(28, resources, 17236389, 0, 0);
        vibratorHelper.createPatternInfo(29, resources, 17236390, 0, 0);
        vibratorHelper.createPatternInfo(30, resources, 17236391, 0, 0);
        vibratorHelper.createPatternInfo(31, resources, 17236392, 0, 0);
        vibratorHelper.createPatternInfo(32, resources, 17236393, 0, 25);
        vibratorHelper.createPatternInfo(33, resources, 17236394, 0, 20);
        vibratorHelper.createPatternInfo(34, resources, 17236395, 0, 200);
        vibratorHelper.createPatternInfo(35, resources, 17236396, 0, 0);
        vibratorHelper.createPatternInfo(36, resources, 17236397, 0, 0);
        vibratorHelper.createPatternInfo(37, resources, 17236398, 0, 20);
        vibratorHelper.createPatternInfo(38, resources, 17236399, 0, 20);
        vibratorHelper.createPatternInfo(39, resources, 17236400, 0, 30);
        vibratorHelper.createPatternInfo(40, resources, 17236401, 0, 20);
        vibratorHelper.createPatternInfo(41, resources, 17236402, 0, 10);
        vibratorHelper.createPatternInfo(42, resources, 17236403, 0, 20);
        vibratorHelper.createPatternInfo(43, resources, 17236404, 0, 20);
        vibratorHelper.createPatternInfo(44, resources, 17236405, 0, 20);
        vibratorHelper.createPatternInfo(45, resources, 17236406, 0, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_CROSS_PROFILE_TARGET_OPENED);
        vibratorHelper.createPatternInfo(46, resources, 17236407, 0, 190);
        vibratorHelper.createPatternInfo(47, resources, 17236408, 0, 140);
        vibratorHelper.createPatternInfo(48, resources, 17236409, 0, 500);
        vibratorHelper.createPatternInfo(49, resources, 17236410, 0, 20);
        vibratorHelper.createPatternInfo(50, resources, 17236411, 0, 10);
        vibratorHelper.createPatternInfo(51, resources, 17236412, 0, 30);
        vibratorHelper.createPatternInfo(52, resources, 17236413, 0, 30);
        vibratorHelper.createPatternInfo(56, resources, 17236414, 0, 2000);
        vibratorHelper.createPatternInfo(57, resources, 17236415, 0, 2000);
        vibratorHelper.createPatternInfo(58, resources, 17236416, 0, 2000);
        vibratorHelper.createPatternInfo(59, resources, 17236417, 0, 2000);
        vibratorHelper.createPatternInfo(60, resources, 17236418, 0, 0);
        vibratorHelper.createPatternInfo(84, resources, 17236419, 0, 800);
        vibratorHelper.createPatternInfo(85, resources, 17236420, 0, 800);
        vibratorHelper.createPatternInfo(86, resources, 17236421, 0, 800);
        vibratorHelper.createPatternInfo(87, resources, 17236422, 0, 600);
        vibratorHelper.createPatternInfo(88, resources, 17236423, 0, 320);
        vibratorHelper.createPatternInfo(89, resources, 17236424, 0, 650);
        vibratorHelper.createPatternInfo(90, resources, 17236425, 0, 750);
        vibratorHelper.createPatternInfo(91, resources, 17236426, 0, 3050);
        vibratorHelper.createPatternInfo(92, resources, 17236427, 0, 3250);
        vibratorHelper.createPatternInfo(108, resources, 17236372, 0, 25);
        vibratorHelper.createPatternInfo(109, resources, 17236373, 0, 25);
        vibratorHelper.createPatternInfo(110, resources, 17236374, 0, 140);
        vibratorHelper.createPatternInfo(111, resources, 17236375, 0, 285);
        vibratorHelper.createPatternInfo(112, resources, 17236376, 0, 525);
        vibratorHelper.createPatternInfo(113, resources, 17236377, 0, 190);
        vibratorHelper.createPatternInfo(114, resources, 17236378, 0, SystemService.PHASE_LOCK_SETTINGS_READY);
        vibratorHelper.createPatternInfo(119, resources, 17236379, 0, 20);
        vibratorHelper.createPatternInfo(125, resources, 17236380, 0, SystemService.PHASE_ACTIVITY_MANAGER_READY);
        vibratorHelper.createPatternInfo(126, resources, 17236381, 0, 200);
        vibratorHelper.createPatternInfo(127, resources, 17236382, 0, 200);
        vibratorHelper.createIndexDurationInfo(64, 1600);
        vibratorHelper.createIndexDurationInfo(65, 1600);
        vibratorHelper.createIndexDurationInfo(66, 30);
        vibratorHelper.createIndexDurationInfo(68, 50);
        vibratorHelper.createIndexDurationInfo(69, 125);
        vibratorHelper.createIndexDurationInfo(70, FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE);
        vibratorHelper.createIndexDurationInfo(71, 500);
        vibratorHelper.createIndexDurationInfo(72, 22);
        vibratorHelper.createIndexDurationInfo(73, 325);
        vibratorHelper.createIndexDurationInfo(74, 225);
        vibratorHelper.createIndexDurationInfo(75, 125);
        vibratorHelper.createIndexDurationInfo(76, 325);
        vibratorHelper.createIndexDurationInfo(77, 225);
        vibratorHelper.createIndexDurationInfo(78, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_LAUNCH_OTHER_APP);
        vibratorHelper.createIndexDurationInfo(79, 30);
        vibratorHelper.createIndexDurationInfo(80, 45);
        vibratorHelper.createIndexDurationInfo(81, 170);
        vibratorHelper.createIndexDurationInfo(82, 1000);
        vibratorHelper.createIndexDurationInfo(83, 30);
        vibratorHelper.createIndexDurationInfo(93, 660);
        vibratorHelper.createIndexDurationInfo(94, FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_ASTRO);
        vibratorHelper.createIndexDurationInfo(95, 750);
        vibratorHelper.createIndexDurationInfo(96, FrameworkStatsLog.AUTH_ENROLL_ACTION_INVOKED);
        vibratorHelper.createIndexDurationInfo(97, 720);
        vibratorHelper.createIndexDurationInfo(98, 1015);
        vibratorHelper.createIndexDurationInfo(99, 500);
        vibratorHelper.createIndexDurationInfo(101, 3565);
        vibratorHelper.createIndexDurationInfo(102, 3525);
        vibratorHelper.createIndexDurationInfo(103, 3610);
        vibratorHelper.createIndexDurationInfo(104, 3760);
        vibratorHelper.createIndexDurationInfo(105, 5440);
        vibratorHelper.createIndexDurationInfo(106, 3910);
        vibratorHelper.createIndexDurationInfo(107, 3525);
        vibratorHelper.createIndexDurationInfo(115, 1700);
        vibratorHelper.createIndexDurationInfo(116, 1300);
        vibratorHelper.createIndexDurationInfo(117, 2500);
        vibratorHelper.createIndexDurationInfo(118, 3000);
        vibratorHelper.createIndexDurationInfo(120, 100);
        vibratorHelper.createIndexDurationInfo(121, 150);
        vibratorHelper.createIndexDurationInfo(122, 500);
        vibratorHelper.createIndexDurationInfo(123, 130);
        vibratorHelper.createIndexDurationInfo(124, 300);
        VibRune.SET_HYBRID_HAPTIC(this.mVibrators.get(0) != null ? ((VibratorController) this.mVibrators.get(0)).mNativeWrapper.hasFeature("HYBRID_PATTERN_COMMON_INPUTFF") : false);
        VibratorHelper.sIsFrequencySupported = this.mVibrators.get(0) != null ? ((VibratorController) this.mVibrators.get(0)).mSupportFrequencyControl : false;
        AnyMotionDetector$$ExternalSyntheticOutline0.m("VibratorManagerService", new StringBuilder("frequency supported is : "), VibratorHelper.sIsFrequencySupported);
        VibratorHelper.sIsHapticEngineSupported = this.mVibrators.get(0) != null ? ((VibratorController) this.mVibrators.get(0)).mSupportHapticEngine : false;
        Slog.d("VibratorManagerService", "haptic engine supported is : " + VibratorHelper.sIsHapticEngineSupported);
        VibratorHelper.sMotorType = getSupportedMotorType();
        Slog.d("VibratorManagerService", "vibrator motor type is : " + VibratorHelper.getMotorTypeToString(VibratorHelper.sMotorType));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new VibratorManagerShellCommand(shellCallback.getShellCallbackBinder()).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void performHapticFeedback(int i, int i2, String str, int i3, boolean z, String str2, boolean z2) {
        performHapticFeedbackInternal(i, i2, str, i3, z, str2, this, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00c3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.server.vibrator.HalVibration performHapticFeedbackInternal(int r12, int r13, java.lang.String r14, int r15, boolean r16, java.lang.String r17, android.os.IBinder r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.vibrator.VibratorManagerService.performHapticFeedbackInternal(int, int, java.lang.String, int, boolean, java.lang.String, android.os.IBinder, boolean):com.android.server.vibrator.HalVibration");
    }

    public final boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) {
        registerVibratorStateListener_enforcePermission();
        VibratorController vibratorController = (VibratorController) this.mVibrators.get(i);
        if (vibratorController == null) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (vibratorController.mLock) {
                if (!vibratorController.mVibratorStateListeners.register(iVibratorStateListener)) {
                    return false;
                }
                try {
                    iVibratorStateListener.onVibrating(vibratorController.mIsVibrating);
                } catch (RemoteException | RuntimeException e) {
                    Slog.e("VibratorController", "Vibrator state listener failed to call", e);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void reportVibrationInfo(HalVibration halVibration, VibrationAttributes vibrationAttributes) {
        GoodCatchManager goodCatchManager;
        if (18 == vibrationAttributes.getUsage() || (goodCatchManager = this.mGoodCatchManager) == null) {
            return;
        }
        if (TextUtils.equals("VibratorService", goodCatchManager.mModule) ? goodCatchManager.mVibrationFunc : false) {
            GoodCatchManager goodCatchManager2 = this.mGoodCatchManager;
            Vibration.CallerInfo callerInfo = halVibration.callerInfo;
            int usage = callerInfo.attrs.getUsage();
            String emptyIfNull = TextUtils.emptyIfNull(callerInfo.reason);
            goodCatchManager2.mSemGoodCatchManager.update("vibration", ((usage == 49 || usage == 0 || usage == 33) && emptyIfNull.contains("reason: Notification (")) ? emptyIfNull.substring(emptyIfNull.indexOf(40) + 1, emptyIfNull.indexOf(41)).split(" ")[0] : callerInfo.opPkg, vibrationAttributes.getUsage(), "", callerInfo.reason);
        }
    }

    public final int semGetNumberOfSupportedPatterns() {
        return this.mVibratorHelper.mVibePatternHash.size();
    }

    public final boolean setAlwaysOnEffect(int i, String str, int i2, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes) {
        Trace.traceBegin(8388608L, "setAlwaysOnEffect");
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.VIBRATE_ALWAYS_ON", "setAlwaysOnEffect");
            if (combinedVibration == null) {
                synchronized (this.mLock) {
                    this.mAlwaysOnEffects.delete(i2);
                    for (int i3 = 0; i3 < this.mVibrators.size(); i3++) {
                        VibratorController vibratorController = (VibratorController) this.mVibrators.valueAt(i3);
                        if (vibratorController.mVibratorInfo.hasCapability(64L)) {
                            vibratorController.updateAlwaysOn(i2, null);
                        }
                    }
                }
                Trace.traceEnd(8388608L);
                return true;
            }
            try {
                combinedVibration.validate();
                VibrationAttributes fixupVibrationAttributes = fixupVibrationAttributes(vibrationAttributes, combinedVibration);
                synchronized (this.mLock) {
                    SparseArray fixupAlwaysOnEffectsLocked = fixupAlwaysOnEffectsLocked(combinedVibration);
                    if (fixupAlwaysOnEffectsLocked == null) {
                        Trace.traceEnd(8388608L);
                        return false;
                    }
                    AlwaysOnVibration alwaysOnVibration = new AlwaysOnVibration(i2, new Vibration.CallerInfo(fixupVibrationAttributes, i, 0, str, null), fixupAlwaysOnEffectsLocked);
                    this.mAlwaysOnEffects.put(i2, alwaysOnVibration);
                    updateAlwaysOnLocked(alwaysOnVibration);
                    Trace.traceEnd(8388608L);
                    return true;
                }
            } catch (Exception e) {
                Slog.wtf("VibratorManagerService", "Encountered issue when verifying CombinedVibrationEffect.", e);
                Trace.traceEnd(8388608L);
                return false;
            }
        } catch (Throwable th) {
            Trace.traceEnd(8388608L);
            throw th;
        }
        Trace.traceEnd(8388608L);
        throw th;
    }

    public final void setExternalControl(boolean z, VibrationStats vibrationStats) {
        for (int i = 0; i < this.mVibrators.size(); i++) {
            ((VibratorController) this.mVibrators.valueAt(i)).setExternalControl(z);
            vibrationStats.mVibratorSetExternalControlCount++;
        }
    }

    public final Vibration.EndInfo shouldIgnoreVibrationForOngoingLocked(Vibration vibration) {
        boolean z;
        Vibration.EndInfo shouldIgnoreVibrationForOngoing;
        ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
        if (externalVibrationHolder != null) {
            return shouldIgnoreVibrationForOngoing(vibration, externalVibrationHolder);
        }
        VibrationStepConductor vibrationStepConductor = this.mNextVibration;
        if (vibrationStepConductor != null && (shouldIgnoreVibrationForOngoing = shouldIgnoreVibrationForOngoing(vibration, vibrationStepConductor.mVibration)) != null) {
            return shouldIgnoreVibrationForOngoing;
        }
        VibrationStepConductor vibrationStepConductor2 = this.mCurrentVibration;
        if (vibrationStepConductor2 == null) {
            return null;
        }
        HalVibration halVibration = vibrationStepConductor2.mVibration;
        if (halVibration.mStatus == Vibration.Status.RUNNING) {
            vibrationStepConductor2.getClass();
            if (Build.IS_DEBUGGABLE) {
                VibrationStepConductor.expectIsVibrationThread(false);
            }
            synchronized (vibrationStepConductor2.mLock) {
                z = vibrationStepConductor2.mSignalCancel != null;
            }
            if (!z) {
                return shouldIgnoreVibrationForOngoing(vibration, halVibration);
            }
        }
        return null;
    }

    public final Vibration.EndInfo shouldIgnoreVibrationLocked(Vibration.CallerInfo callerInfo) {
        boolean z;
        Vibration.Status status;
        PackageManager packageManager;
        if (CoreRune.SYSFW_APP_SPEG && (packageManager = this.mContext.getPackageManager()) != null && packageManager.isSpeg(callerInfo.uid)) {
            Slog.d("SPEG", "Vibration is ignored for uid " + callerInfo.uid);
            return new Vibration.EndInfo(Vibration.Status.IGNORED_APP_OPS, null);
        }
        VibrationSettings vibrationSettings = this.mVibrationSettings;
        vibrationSettings.getClass();
        int usage = callerInfo.attrs.getUsage();
        synchronized (vibrationSettings.mLock) {
            try {
                VibrationSettings.VibrationUidObserver vibrationUidObserver = vibrationSettings.mUidObserver;
                int i = callerInfo.uid;
                synchronized (vibrationUidObserver) {
                    z = ((Integer) vibrationUidObserver.mProcStatesCache.get(i, 6)).intValue() <= 6;
                }
                if (!z) {
                    if (!((HashSet) VibrationSettings.BACKGROUND_PROCESS_USAGE_ALLOWLIST).contains(Integer.valueOf(usage))) {
                        status = callerInfo.attrs.hasTag("ALLOWED_IN_BACKGROUND_PROCESS") ? null : Vibration.Status.IGNORED_BACKGROUND;
                    }
                }
                int i2 = callerInfo.deviceId;
                if (i2 == 0 || i2 == -1) {
                    if (i2 == -1) {
                        int i3 = callerInfo.uid;
                        if (vibrationSettings.mVirtualDeviceManagerInternal == null) {
                            vibrationSettings.mVirtualDeviceManagerInternal = (VirtualDeviceManagerInternal) LocalServices.getService(VirtualDeviceManagerInternal.class);
                        }
                        VirtualDeviceManagerInternal virtualDeviceManagerInternal = vibrationSettings.mVirtualDeviceManagerInternal;
                        if (virtualDeviceManagerInternal != null && virtualDeviceManagerInternal.isAppRunningOnAnyVirtualDevice(i3)) {
                            status = Vibration.Status.IGNORED_FROM_VIRTUAL_DEVICE;
                        }
                    }
                    if (vibrationSettings.mBatterySaverMode) {
                        if (!((HashSet) VibrationSettings.BATTERY_SAVER_USAGE_ALLOWLIST).contains(Integer.valueOf(usage))) {
                            status = Vibration.Status.IGNORED_FOR_POWER;
                        }
                    }
                    if (callerInfo.attrs.isFlagSet(2) || vibrationSettings.shouldVibrateForUserSetting(callerInfo)) {
                        if (!callerInfo.attrs.isFlagSet(1) && ((usage == 33 || usage == 49) && vibrationSettings.mRingerMode == 0)) {
                            if (!callerInfo.attrs.hasTag("VIBRATE_CALL")) {
                                status = Vibration.Status.IGNORED_FOR_RINGER_MODE;
                            }
                        }
                        if (vibrationSettings.mVibrationConfig.ignoreVibrationsOnWirelessCharger() && vibrationSettings.mOnWirelessCharger) {
                            status = Vibration.Status.IGNORED_ON_WIRELESS_CHARGER;
                        } else {
                            try {
                                INotificationManager iNotificationManager = vibrationSettings.mNotificationManager;
                                if (iNotificationManager != null && usage == 49 && !iNotificationManager.areNotificationsEnabledForPackage(callerInfo.opPkg, callerInfo.uid)) {
                                    status = Vibration.Status.CANCELLED_BY_DISABLED_NOTIFICATION;
                                }
                            } catch (RemoteException e) {
                                Slog.e("VibrationSettings", "Failed to call NotificationManager : " + e);
                            }
                        }
                    } else {
                        status = Vibration.Status.IGNORED_FOR_SETTINGS;
                    }
                } else {
                    status = Vibration.Status.IGNORED_FROM_VIRTUAL_DEVICE;
                }
            } finally {
            }
        }
        if (status != null) {
            return new Vibration.EndInfo(status, null);
        }
        AppOpsManager appOpsManager = this.mAppOps;
        int audioUsage = callerInfo.attrs.getAudioUsage();
        String str = callerInfo.opPkg;
        int i4 = callerInfo.uid;
        int checkAudioOpNoThrow = appOpsManager.checkAudioOpNoThrow(3, audioUsage, i4, str);
        int i5 = (checkAudioOpNoThrow == 1 && callerInfo.attrs.isFlagSet(1)) ? 0 : checkAudioOpNoThrow;
        if (checkAudioOpNoThrow != i5 && i5 == 0) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i4, "Bypassing DND for vibrate from uid ", "VibratorManagerService");
        }
        if (i5 != 0) {
            return i5 == 2 ? new Vibration.EndInfo(Vibration.Status.IGNORED_ERROR_APP_OPS, null) : new Vibration.EndInfo(Vibration.Status.IGNORED_APP_OPS, null);
        }
        return null;
    }

    public final Vibration.EndInfo startVibrationLocked(HalVibration halVibration) {
        boolean z;
        Trace.traceBegin(8388608L, "startVibrationLocked");
        try {
            InputDeviceDelegate inputDeviceDelegate = this.mInputDeviceDelegate;
            synchronized (inputDeviceDelegate.mLock) {
                z = inputDeviceDelegate.mInputDeviceVibrators.size() > 0;
            }
            if (z) {
                return startVibrationOnInputDevicesLocked(halVibration);
            }
            VibrationStepConductor createVibrationStepConductor = createVibrationStepConductor(halVibration);
            if (this.mCurrentVibration == null) {
                return startVibrationOnThreadLocked(createVibrationStepConductor);
            }
            clearNextVibrationLocked(new Vibration.EndInfo(Vibration.Status.IGNORED_SUPERSEDED, halVibration.callerInfo));
            this.mNextVibration = createVibrationStepConductor;
            Trace.traceEnd(8388608L);
            return null;
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public final Vibration.EndInfo startVibrationOnInputDevicesLocked(HalVibration halVibration) {
        if (halVibration.callerInfo.attrs.isFlagSet(16)) {
            halVibration.resolveEffects(this.mVibrationScaler.mDefaultVibrationAmplitude);
        }
        InputDeviceDelegate inputDeviceDelegate = this.mInputDeviceDelegate;
        Vibration.CallerInfo callerInfo = halVibration.callerInfo;
        CombinedVibration combinedVibration = halVibration.mEffectToPlay;
        synchronized (inputDeviceDelegate.mLock) {
            for (int i = 0; i < inputDeviceDelegate.mInputDeviceVibrators.size(); i++) {
                try {
                    ((VibratorManager) inputDeviceDelegate.mInputDeviceVibrators.valueAt(i)).vibrate(callerInfo.uid, callerInfo.opPkg, combinedVibration, callerInfo.reason, callerInfo.attrs);
                } catch (Throwable th) {
                    throw th;
                }
            }
            inputDeviceDelegate.mInputDeviceVibrators.size();
        }
        return new Vibration.EndInfo(Vibration.Status.FORWARDED_TO_INPUT_DEVICES, null);
    }

    public final Vibration.EndInfo startVibrationOnThreadLocked(VibrationStepConductor vibrationStepConductor) {
        Trace.traceBegin(8388608L, "startVibrationThreadLocked");
        try {
            HalVibration halVibration = vibrationStepConductor.mVibration;
            Vibration.CallerInfo callerInfo = halVibration.callerInfo;
            int startOpNoThrow = this.mAppOps.startOpNoThrow(3, callerInfo.uid, callerInfo.opPkg);
            VibrationAttributes vibrationAttributes = callerInfo.attrs;
            if (startOpNoThrow == 1 && vibrationAttributes.isFlagSet(1)) {
                startOpNoThrow = 0;
            }
            if (startOpNoThrow != 0) {
                if (startOpNoThrow != 2) {
                    return new Vibration.EndInfo(Vibration.Status.IGNORED_APP_OPS, null);
                }
                Slog.w("VibratorManagerService", "Start AppOpsManager operation errored for uid " + halVibration.callerInfo.uid);
                return new Vibration.EndInfo(Vibration.Status.IGNORED_ERROR_APP_OPS, null);
            }
            Trace.asyncTraceBegin(8388608L, "vibration", 0);
            this.mCurrentVibration = vibrationStepConductor;
            VibrationThread vibrationThread = this.mVibrationThread;
            synchronized (vibrationThread.mLock) {
                if (vibrationThread.mRequestedActiveConductor != null) {
                    Slog.wtf("VibrationThread", "Attempt to start vibration when one already running");
                    this.mCurrentVibration = null;
                    return new Vibration.EndInfo(Vibration.Status.IGNORED_ERROR_SCHEDULING, null);
                }
                vibrationThread.mRequestedActiveConductor = vibrationStepConductor;
                vibrationThread.mLock.notifyAll();
                VirtualVibSoundHelper virtualVibSoundHelper = this.mVirtualVibSoundHelper;
                if (virtualVibSoundHelper != null) {
                    Vibration.CallerInfo callerInfo2 = halVibration.callerInfo;
                    virtualVibSoundHelper.playVirtualSoundIfNeeded(callerInfo2.opPkg, callerInfo2.attrs, halVibration.isRepeating());
                }
                return null;
            }
        } finally {
            Trace.traceEnd(8388608L);
        }
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
        InputDeviceDelegate inputDeviceDelegate = this.mInputDeviceDelegate;
        synchronized (inputDeviceDelegate.mLock) {
            inputDeviceDelegate.mInputManager = (InputManager) inputDeviceDelegate.mContext.getSystemService(InputManager.class);
        }
        VibrationSettings vibrationSettings = this.mVibrationSettings;
        VibratorManagerService$$ExternalSyntheticLambda0 vibratorManagerService$$ExternalSyntheticLambda0 = new VibratorManagerService$$ExternalSyntheticLambda0(this);
        synchronized (vibrationSettings.mLock) {
            try {
                if (!((ArrayList) vibrationSettings.mListeners).contains(vibratorManagerService$$ExternalSyntheticLambda0)) {
                    ((ArrayList) vibrationSettings.mListeners).add(vibratorManagerService$$ExternalSyntheticLambda0);
                }
            } finally {
            }
        }
        updateServiceState();
        onCustomSystemReady();
        synchronized (this.mLock) {
            this.mServiceReady = true;
        }
        Slog.v("VibratorManagerService", "VibratorManager service initialized");
        Trace.traceEnd(8388608L);
    }

    public final boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) {
        unregisterVibratorStateListener_enforcePermission();
        VibratorController vibratorController = (VibratorController) this.mVibrators.get(i);
        if (vibratorController == null) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return vibratorController.mVibratorStateListeners.unregister(iVibratorStateListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateAlwaysOnLocked(AlwaysOnVibration alwaysOnVibration) {
        for (int i = 0; i < alwaysOnVibration.effects.size(); i++) {
            VibratorController vibratorController = (VibratorController) this.mVibrators.get(alwaysOnVibration.effects.keyAt(i));
            PrebakedSegment prebakedSegment = (PrebakedSegment) alwaysOnVibration.effects.valueAt(i);
            if (vibratorController != null) {
                Vibration.CallerInfo callerInfo = alwaysOnVibration.callerInfo;
                vibratorController.updateAlwaysOn(alwaysOnVibration.alwaysOnId, shouldIgnoreVibrationLocked(callerInfo) == null ? prebakedSegment.applyEffectStrength(this.mVibrationScaler.getEffectStrength(callerInfo.attrs.getUsage())) : null);
            }
        }
    }

    public void updateServiceState() {
        synchronized (this.mLock) {
            try {
                Slog.d("VibratorManagerService", "Updating device state...");
                boolean updateInputDeviceVibrators = this.mInputDeviceDelegate.updateInputDeviceVibrators(this.mVibrationSettings.mVibrateInputDevices);
                for (int i = 0; i < this.mAlwaysOnEffects.size(); i++) {
                    updateAlwaysOnLocked((AlwaysOnVibration) this.mAlwaysOnEffects.valueAt(i));
                }
                VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
                if (vibrationStepConductor == null) {
                    return;
                }
                Vibration.EndInfo shouldIgnoreVibrationLocked = shouldIgnoreVibrationLocked(vibrationStepConductor.mVibration.callerInfo);
                if (updateInputDeviceVibrators || shouldIgnoreVibrationLocked != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Canceling vibration because settings changed: ");
                    sb.append(updateInputDeviceVibrators ? "input devices changed" : shouldIgnoreVibrationLocked.status);
                    Slog.d("VibratorManagerService", sb.toString());
                    this.mCurrentVibration.notifyCancelled(new Vibration.EndInfo(Vibration.Status.CANCELLED_BY_SETTINGS_UPDATE, null), false);
                    VirtualVibSoundHelper virtualVibSoundHelper = this.mVirtualVibSoundHelper;
                    if (virtualVibSoundHelper != null) {
                        virtualVibSoundHelper.stopVirtualSound("cancel");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void vibrate(int i, int i2, String str, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes, String str2, IBinder iBinder) {
        vibrateWithPermissionCheck(i, i2, str, combinedVibration, vibrationAttributes, str2, iBinder);
    }

    public final HalVibration vibrateInternal(int i, int i2, String str, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes, String str2, IBinder iBinder) {
        boolean z;
        HalVibration halVibration;
        SemVibration semVibration;
        if (iBinder == null) {
            Slog.e("VibratorManagerService", "token must not be null");
            return null;
        }
        if (i != Binder.getCallingUid() && Binder.getCallingPid() != Process.myPid()) {
            this.mContext.enforcePermission("android.permission.UPDATE_APP_OPS_STATS", Binder.getCallingPid(), Binder.getCallingUid(), null);
        }
        if (combinedVibration == null) {
            Slog.wtf("VibratorManagerService", "effect must not be null");
            return null;
        }
        try {
            combinedVibration.validate();
            StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i, "vibrate - uid: ", ", opPkg: ", str, ", effect: ");
            m.append(combinedVibration);
            m.append(", attrs: ");
            m.append(vibrationAttributes);
            m.append(", reason: ");
            m.append(str2);
            m.append(", token: ");
            m.append(iBinder);
            Slog.d("VibratorManagerService", m.toString());
            VibrationEffect.Composed composed = combinedVibration instanceof CombinedVibration.Mono ? (VibrationEffect.Composed) ((CombinedVibration.Mono) combinedVibration).getEffect() : ((combinedVibration instanceof CombinedVibration.Stereo) || (combinedVibration instanceof CombinedVibration.Sequential)) ? (VibrationEffect.Composed) ((CombinedVibration.Stereo) combinedVibration).getEffects().get(0) : null;
            this.mComposed = composed;
            if (composed == null) {
                cancelVibrate(-1, iBinder);
                return null;
            }
            int usage = vibrationAttributes.getUsage();
            int i3 = AnonymousClass2.$SwitchMap$android$os$VibrationEffect$SemMagnitudeType[this.mComposed.semGetMagnitudeType().ordinal()];
            if (i3 == 1) {
                usage = 18;
            } else if (i3 == 2) {
                usage = 49;
            } else if (i3 == 3) {
                usage = 33;
            } else if (i3 == 4 && usage == 0) {
                usage = 19;
            }
            Slog.d("VibratorManagerService", "converted usage = " + VibrationAttributes.usageToString(usage) + "(" + usage + ")");
            String str3 = this.mComposed.semGetMagnitudeType() == VibrationEffect.SemMagnitudeType.TYPE_MAX ? "INTENSITY_MAX" : this.mComposed.semGetMagnitudeType() == VibrationEffect.SemMagnitudeType.TYPE_MIN ? "INTENSITY_MIN" : null;
            VibrationAttributes build = str3 != null ? new VibrationAttributes.Builder(vibrationAttributes).setUsage(usage).semAddTag(str3).build() : new VibrationAttributes.Builder(vibrationAttributes).setUsage(usage).build();
            if (this.mComposed.getSegments().get(0) instanceof SemHapticSegment) {
                SemHapticSegment semHapticSegment = (SemHapticSegment) this.mComposed.getSegments().get(0);
                int type = semHapticSegment.getType();
                int repeatIndex = this.mComposed.getRepeatIndex();
                int magnitudeByUsage = getMagnitudeByUsage(build);
                SemVibrationBundle semVibrationBundle = new SemVibrationBundle();
                semVibrationBundle.mToken = iBinder;
                semVibrationBundle.mEffect = combinedVibration;
                semVibrationBundle.mIndex = type;
                semVibrationBundle.mRepeat = repeatIndex;
                semVibrationBundle.mMagnitude = magnitudeByUsage;
                semVibrationBundle.mAttrs = build;
                semVibrationBundle.mUid = i;
                semVibrationBundle.mDeviceId = i2;
                semVibrationBundle.mOpPkg = str;
                semVibrationBundle.mReason = str2;
                Context context = this.mContext;
                VibrationSettings vibrationSettings = this.mVibrationSettings;
                if (semHapticSegment.getMagnitude() != -1) {
                    semVibrationBundle.mMagnitude = semHapticSegment.getMagnitude();
                    semVibrationBundle.mAttrs = new VibrationAttributes.Builder(semVibrationBundle.mAttrs).semAddTag("INDIVIDUAL_INTENSITY").build();
                }
                if (VibRune.SUPPORT_CUSTOM_PATTERN && semHapticSegment.isCustomIndexValid()) {
                    semVibration = new SemCustomVibration(context, semVibrationBundle, semHapticSegment, vibrationSettings);
                } else if (VibratorHelper.sMotorType == 1 && type == 50124 && VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR) {
                    semVibrationBundle.mMagnitude = vibrationSettings.getCurrentMagnitude(18);
                    semVibration = new SemDcVibration(semVibrationBundle);
                } else if (VibRune.SUPPORT_CIRRUS_HAPTIC() || VibRune.SUPPORT_HYBRID_HAPTIC()) {
                    semVibration = new SemIndexVibration(semVibrationBundle);
                } else {
                    SemPatternVibration semPatternVibration = new SemPatternVibration(semVibrationBundle);
                    semPatternVibration.mIsExecutablePkg = "";
                    semPatternVibration.mHasEngineData = false;
                    semVibration = semPatternVibration;
                }
                halVibration = semVibration.getVibration();
                if (halVibration == null) {
                    cancelVibrate(-1, iBinder);
                    Slog.d("VibratorManagerService", "The vibration was not generated normally.");
                    return null;
                }
                Slog.d("VibratorManagerService", semVibration.toString());
                z = true;
            } else {
                z = true;
                HalVibration halVibration2 = new HalVibration(iBinder, combinedVibration, new Vibration.CallerInfo(build, i, i2, str, str2));
                halVibration2.mMagnitude = getMagnitudeByUsage(build);
                halVibration = halVibration2;
            }
            fillVibrationFallbacks(halVibration, combinedVibration);
            if (build.isFlagSet(4)) {
                this.mVibrationSettings.update();
            }
            synchronized (this.mLock) {
                try {
                    Slog.d("VibratorManagerService", "Starting vibrate for vibration " + halVibration.id);
                    Vibration.EndInfo shouldIgnoreVibrationLocked = shouldIgnoreVibrationLocked(halVibration.callerInfo);
                    if (shouldIgnoreVibrationLocked == null) {
                        shouldIgnoreVibrationLocked = shouldIgnoreVibrationForOngoingLocked(halVibration);
                    }
                    if (shouldIgnoreVibrationLocked == null) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            ExternalVibrationHolder externalVibrationHolder = this.mCurrentExternalVibration;
                            if (externalVibrationHolder != null) {
                                externalVibrationHolder.externalVibration.mute();
                                halVibration.stats.reportInterruptedAnotherVibration(this.mCurrentExternalVibration.callerInfo);
                                endExternalVibrateLocked(new Vibration.EndInfo(Vibration.Status.CANCELLED_SUPERSEDED, halVibration.callerInfo), false);
                            } else {
                                VibrationStepConductor vibrationStepConductor = this.mCurrentVibration;
                                if (vibrationStepConductor != null) {
                                    HalVibration halVibration3 = vibrationStepConductor.mVibration;
                                    Vibration.CallerInfo callerInfo = halVibration3.callerInfo;
                                    if (callerInfo.uid == halVibration.callerInfo.uid && callerInfo.attrs.isFlagSet(8) && halVibration.callerInfo.attrs.isFlagSet(8) && !halVibration3.isRepeating()) {
                                        Slog.d("VibratorManagerService", "Pipelining vibration " + halVibration.id);
                                    } else {
                                        halVibration.stats.reportInterruptedAnotherVibration(this.mCurrentVibration.mVibration.callerInfo);
                                        this.mCurrentVibration.notifyCancelled(new Vibration.EndInfo(Vibration.Status.CANCELLED_SUPERSEDED, halVibration.callerInfo), false);
                                    }
                                }
                            }
                            VirtualVibSoundHelper virtualVibSoundHelper = this.mVirtualVibSoundHelper;
                            if (virtualVibSoundHelper != null) {
                                virtualVibSoundHelper.stopVirtualSound("cancel");
                            }
                            Vibration.EndInfo startVibrationLocked = startVibrationLocked(halVibration);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            shouldIgnoreVibrationLocked = startVibrationLocked;
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    if (shouldIgnoreVibrationLocked != null) {
                        endVibrationLocked(halVibration, shouldIgnoreVibrationLocked, z);
                    }
                    reportVibrationInfo(halVibration, build);
                    this.mHqmLoggingData.increaseCount(halVibration.callerInfo.attrs.getUsage());
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            return halVibration;
        } catch (Exception e) {
            Slog.wtf("VibratorManagerService", "Encountered issue when verifying CombinedVibrationEffect.", e);
            return null;
        }
    }

    public HalVibration vibrateWithPermissionCheck(int i, int i2, String str, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes, String str2, IBinder iBinder) {
        Trace.traceBegin(8388608L, "vibrate, reason = " + str2);
        try {
            VibrationAttributes fixupVibrationAttributes = fixupVibrationAttributes(vibrationAttributes, combinedVibration);
            this.mContext.enforceCallingOrSelfPermission("android.permission.VIBRATE", "vibrate");
            return vibrateInternal(i, i2, str, combinedVibration, fixupVibrationAttributes, str2, iBinder);
        } finally {
            Trace.traceEnd(8388608L);
        }
    }
}
