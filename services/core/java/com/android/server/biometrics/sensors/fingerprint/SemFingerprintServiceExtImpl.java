package com.android.server.biometrics.sensors.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Slog;
import android.widget.Toast;

import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda1;
import com.android.server.biometrics.SemBiometricDisplayStateMonitor;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;

import com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemFingerprintServiceExtImpl
        implements SemFpHalLifecycleListener, SemFpAuthenticationListener {
    public static final boolean DEBUG = Utils.DEBUG;
    SemFpAodController mAodController;
    public long mCaptureStartedTime;
    public final Context mContext;
    public final SemBiometricDisplayStateMonitor mDisplayStateMonitor;
    public final SemFpEnrollSessionMonitor mEnrollSessionMonitor;
    public SemFpProtectiveFilmGuide mFpProtectiveFilmGuide;
    public final Handler mHandler;
    public Pair mIFAAFlag;
    public long mIconDrawnTime;
    public final Injector mInjector;
    public boolean mIsScreenOn;
    public int mLatestSensorStatus;
    public final SemFpLocalHbmOpticalController mLocalHbmController;
    BroadcastReceiver mPersistentBroadCastReceiver;
    public final FingerprintServiceRegistry mRegistry;
    public ISemBiometricSysUiDisplayBrightnessCallback mSysUiDisplayBrightnessCallback;
    public ISemBiometricSysUiDisplayStateCallback mSysUiDisplayStateCallback;
    public final AtomicBoolean mIsBouncer = new AtomicBoolean(false);
    public final Object mLockForAodCtrl = new Object();
    public boolean mHasUltrasonicUdfps = SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC;
    public boolean mHasOpticalUdfps = SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL;
    public boolean mHasUdfps = SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$1, reason: invalid class name */
    public final class AnonymousClass1
            implements SemBiometricDisplayStateMonitor.DisplayStateCallback {
        public AnonymousClass1() {}

        @Override // com.android.server.biometrics.SemBiometricDisplayStateMonitor.DisplayStateCallback
        public final void onDisplayOn() {
            SemFingerprintServiceExtImpl.this.mHandler.post(
                    new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda6(4, this));
        }

        @Override // com.android.server.biometrics.SemBiometricDisplayStateMonitor.DisplayStateCallback
        public final void onFinishDisplayState(int i, int i2, int i3) {
            SemFingerprintServiceExtImpl.this.mHandler.post(
                    new SemFingerprintServiceExtImpl$1$$ExternalSyntheticLambda0(
                            this, i, i2, i3, 1));
        }

        @Override // com.android.server.biometrics.SemBiometricDisplayStateMonitor.DisplayStateCallback
        public final void onStartDisplayState(int i, int i2, int i3) {
            SemFingerprintServiceExtImpl.this.mHandler.post(
                    new SemFingerprintServiceExtImpl$1$$ExternalSyntheticLambda0(
                            this, i, i2, i3, 0));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public AnonymousClass2() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            SemFingerprintServiceExtImpl.this.mHandler.post(
                    new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda2(4, this, intent));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Injector {
        public static SemFpAodController createAodController() {
            return new SemFpAodController(
                    new Handler(
                            BiometricHandlerProvider.sBiometricHandlerProvider
                                    .getFingerprintHandler()
                                    .getLooper()));
        }
    }

    public SemFingerprintServiceExtImpl(
            Context context,
            Looper looper,
            FingerprintServiceRegistry fingerprintServiceRegistry,
            Injector injector) {
        this.mContext = context;
        this.mHandler = new Handler(looper);
        this.mRegistry = fingerprintServiceRegistry;
        this.mInjector = injector;
        injector.getClass();
        SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor =
                SemBiometricDisplayStateMonitor.InstanceHolder.INSTANCE;
        this.mDisplayStateMonitor = semBiometricDisplayStateMonitor;
        injector.getClass();
        this.mEnrollSessionMonitor = new SemFpEnrollSessionMonitor(semBiometricDisplayStateMonitor);
        if (SemBiometricFeature.FP_FEATURE_LOCAL_HBM) {
            injector.getClass();
            this.mLocalHbmController =
                    new SemFpLocalHbmOpticalController(
                            BiometricHandlerProvider.sBiometricHandlerProvider
                                    .getFingerprintHandler(),
                            semBiometricDisplayStateMonitor,
                            new SemFpLocalHbmOpticalController.AnonymousClass1(),
                            SemBiometricFeature.FP_FEATURE_SUPPORT_LOCAL_HBM_IN_HOUSE);
        }
    }

    public final Pair canUseFingerprint(boolean z, boolean z2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (z && !z2) {
            try {
                ServiceProvider serviceProvider = getServiceProvider();
                if (serviceProvider != null) {
                    BaseClientMonitor semGetCurrentClient =
                            ((FingerprintProvider) serviceProvider).semGetCurrentClient();
                    if (semGetCurrentClient instanceof AuthenticationClient) {
                        AuthenticationClient authenticationClient =
                                (AuthenticationClient) semGetCurrentClient;
                        if (authenticationClient.isBiometricPrompt()
                                && authenticationClient.semHasPromptPrivilegedAttr()
                                && Utils.isBackground(authenticationClient.mOwner)) {
                            return new Pair(5, 0);
                        }
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
            boolean z3 = Utils.getIntDb(this.mContext, 0, -2, "any_screen_running", false) == 1;
            Handler handler = this.mHandler;
            if (z3) {
                if (z && !z2) {
                    Toast.makeText(
                                    this.mContext,
                                    handler.getLooper(),
                                    this.mContext.getResources().getText(17042916),
                                    0)
                            .show();
                }
                return new Pair(8, 5001);
            }
            DisplayManager displayManager =
                    (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
            if (displayManager != null && displayManager.semIsFitToActiveDisplay()) {
                if (z && !z2) {
                    Context context = this.mContext;
                    Toast.makeText(
                                    this.mContext,
                                    handler.getLooper(),
                                    context.getString(17042917, context.getString(17042912)),
                                    0)
                            .show();
                }
                return new Pair(8, 5003);
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return new Pair(0, 0);
    }

    public final void dump(PrintWriter printWriter) {
        FingerprintServiceRegistry fingerprintServiceRegistry = this.mRegistry;
        try {
            printWriter.println(" latest sensor status : " + this.mLatestSensorStatus);
            Iterator it = fingerprintServiceRegistry.getAllProperties().iterator();
            while (it.hasNext()) {
                printWriter.println(
                        " Max Template : "
                                + ((FingerprintSensorPropertiesInternal) it.next())
                                        .maxEnrollmentsPerUser);
            }
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                this.mInjector.getClass();
                boolean z = SemUdfpsHelper.DEBUG;
                SemUdfpsHelper.InstanceHolder.INSTANCE.dump(
                        printWriter, fingerprintServiceRegistry.getSingleProvider());
            }
            SemBioLoggingManager.get().fpDump(printWriter);
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("dump: "), "FingerprintService.Ext");
        }
    }

    public Handler getBiometricHandler() {
        return BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler();
    }

    public Handler getHandler() {
        return BiometricHandlerProvider.sBiometricHandlerProvider.getFingerprintHandler();
    }

    public final ServiceProvider getServiceProvider() {
        Pair singleProvider = this.mRegistry.getSingleProvider();
        if (singleProvider != null) {
            return (ServiceProvider) singleProvider.second;
        }
        return null;
    }

    public final void handleBioSysOpticalControl(int i, long j) {
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
            Injector injector = this.mInjector;
            if (i == 1) {
                injector.getClass();
                boolean z = SemUdfpsHelper.DEBUG;
                SemUdfpsOpticalHelper semUdfpsOpticalHelper =
                        SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl;
                if (semUdfpsOpticalHelper.mIsSupportHwLightSource) {
                    return;
                }
                semUdfpsOpticalHelper
                        .getBgHandler()
                        .removeCallbacks(
                                semUdfpsOpticalHelper.mRunnableDisableFunctionForLightSource);
                semUdfpsOpticalHelper
                        .getBgHandler()
                        .postDelayed(
                                semUdfpsOpticalHelper.mRunnableRestoreFunctionForLightSource, j);
                return;
            }
            if (i == 0) {
                injector.getClass();
                boolean z2 = SemUdfpsHelper.DEBUG;
                SemUdfpsOpticalHelper semUdfpsOpticalHelper2 =
                        SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl;
                if (semUdfpsOpticalHelper2.mIsSupportHwLightSource) {
                    return;
                }
                semUdfpsOpticalHelper2
                        .getBgHandler()
                        .removeCallbacks(
                                semUdfpsOpticalHelper2.mRunnableRestoreFunctionForLightSource);
                semUdfpsOpticalHelper2
                        .getBgHandler()
                        .post(semUdfpsOpticalHelper2.mRunnableDisableFunctionForLightSource);
            }
        }
    }

    public final void handleBioSysTspControl(int i, String str) {
        if (i != 1) {
            if (i == 2) {
                this.mInjector.getClass();
                SemUdfpsTspManager.get().setHalfMode(true);
                return;
            }
            return;
        }
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC && TextUtils.equals(str, "BSS")) {
            this.mInjector.getClass();
            SemUdfpsTspManager semUdfpsTspManager = SemUdfpsTspManager.get();
            synchronized (semUdfpsTspManager) {
                semUdfpsTspManager.mIsHalfModeBlocked = true;
            }
        }
        this.mInjector.getClass();
        SemUdfpsTspManager.get().setHalfMode(false);
    }

    public final void handleQcomForceQDB() {
        Pair singleProvider;
        if (!this.mHasUltrasonicUdfps
                || (singleProvider = this.mRegistry.getSingleProvider()) == null) {
            return;
        }
        ((FingerprintProvider) ((ServiceProvider) singleProvider.second))
                .semRequest(((Integer) singleProvider.first).intValue(), 34, 0, null, null);
    }

    public final void handleTouchEvent(int i, long j) {
        SemFpLocalHbmOpticalController semFpLocalHbmOpticalController;
        Pair singleProvider = this.mRegistry.getSingleProvider();
        if (singleProvider == null) {
            return;
        }
        if (SemBiometricFeature.FP_FEATURE_LOCAL_HBM
                && (semFpLocalHbmOpticalController = this.mLocalHbmController) != null) {
            semFpLocalHbmOpticalController.handleTouchEventInLhbm(singleProvider, i, j);
            return;
        }
        BaseClientMonitor semGetCurrentClient =
                ((FingerprintProvider) ((ServiceProvider) singleProvider.second))
                        .semGetCurrentClient();
        long j2 = semGetCurrentClient == null ? 0L : semGetCurrentClient.mRequestId;
        if (i != 2) {
            if (i == 1) {
                ((FingerprintProvider) ((ServiceProvider) singleProvider.second))
                        .onPointerUp(
                                j2,
                                ((Integer) singleProvider.first).intValue(),
                                new PointerContext());
            }
        } else {
            ((FingerprintProvider) ((ServiceProvider) singleProvider.second))
                    .onPointerDown(
                            j2, ((Integer) singleProvider.first).intValue(), new PointerContext());
            if (semGetCurrentClient instanceof AuthenticationClient) {
                SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.get();
                semBioLoggingManager
                        .getFpHandler()
                        .post(
                                new SemBioLoggingManager$$ExternalSyntheticLambda1(
                                        semBioLoggingManager,
                                        (int) j2,
                                        (int) (j >> 16),
                                        (int) (j & 65535)));
            }
        }
    }

    public final void handleTpaCommand(PrintWriter printWriter, String[] strArr) {
        if ("mode".equals(strArr[1])) {
            boolean equals = "1".equals(strArr[2]);
            getHandler()
                    .post(
                            new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda1(
                                    this, equals, 1));
            printWriter.println("tpa mode: " + equals);
            return;
        }
        if ("update".equals(strArr[1])) {
            getHandler().post(new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda6(1, this));
            printWriter.println("tpa mode: update action");
        } else if ("command".equals(strArr[1])) {
            getHandler()
                    .post(
                            new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda2(
                                    1, this, strArr));
            printWriter.println("tpa mode: setTpaRequestCommands");
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public final void onAuthenticationAcquire(int i, int i2, int i3) {
        if (i2 == 6 && i3 == 10002) {
            this.mCaptureStartedTime = SystemClock.elapsedRealtime();
        }
    }

    public final void requestDozeHLPM(boolean z) {
        synchronized (this.mLockForAodCtrl) {
            try {
                if (this.mAodController == null) {
                    this.mInjector.getClass();
                    this.mAodController = Injector.createAodController();
                }
                if (z) {
                    SemFpAodController semFpAodController = this.mAodController;
                    semFpAodController.getClass();
                    semFpAodController.mH.post(
                            new SemFpAodController$$ExternalSyntheticLambda2(
                                    semFpAodController,
                                    new SemFpAodController$$ExternalSyntheticLambda0(
                                            semFpAodController, 4),
                                    (byte) 0));
                } else {
                    SemFpAodController semFpAodController2 = this.mAodController;
                    semFpAodController2.getClass();
                    semFpAodController2.mH.post(
                            new SemFpAodController$$ExternalSyntheticLambda2(
                                    semFpAodController2,
                                    new SemFpAodController$$ExternalSyntheticLambda0(
                                            semFpAodController2, 2),
                                    0));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void requestDozeMode(boolean z) {
        synchronized (this.mLockForAodCtrl) {
            try {
                if (this.mAodController == null) {
                    this.mInjector.getClass();
                    this.mAodController = Injector.createAodController();
                }
                if (z) {
                    SemFpAodController semFpAodController = this.mAodController;
                    semFpAodController.getClass();
                    semFpAodController.mH.post(
                            new SemFpAodController$$ExternalSyntheticLambda2(
                                    semFpAodController,
                                    new SemFpAodController$$ExternalSyntheticLambda0(
                                            semFpAodController, 5),
                                    (char) 0));
                } else {
                    SemFpAodController semFpAodController2 = this.mAodController;
                    semFpAodController2.getClass();
                    semFpAodController2.mH.post(
                            new SemFpAodController$$ExternalSyntheticLambda2(
                                    semFpAodController2,
                                    new SemFpAodController$$ExternalSyntheticLambda0(
                                            semFpAodController2, 3)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterAodController() {
        Slog.d("FingerprintService.Ext", "unregisterAodController: " + Binder.getCallingPid());
        synchronized (this.mLockForAodCtrl) {
            try {
                SemFpAodController semFpAodController = this.mAodController;
                if (semFpAodController != null) {
                    semFpAodController.mH.removeCallbacksAndMessages(null);
                    semFpAodController.mH.post(
                            new SemFpAodController$$ExternalSyntheticLambda0(
                                    semFpAodController, 0));
                    this.mAodController = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
