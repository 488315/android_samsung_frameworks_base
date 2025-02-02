package com.samsung.android.biometrics.app.setting;

import android.app.ActivityTaskManager;
import android.app.TaskStackListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.biometrics.PromptInfo;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IFingerprintService;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.SomeArgs;
import com.android.internal.widget.LockPatternUtils;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiService;
import com.samsung.android.biometrics.SemBiometricConstants;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.FpCheckToEnrollClient;
import com.samsung.android.biometrics.app.setting.fingerprint.FpGestureCalibrationClient;
import com.samsung.android.biometrics.app.setting.fingerprint.FpGestureConsumer;
import com.samsung.android.biometrics.app.setting.fingerprint.FpServiceProvider;
import com.samsung.android.biometrics.app.setting.fingerprint.LhbmOpticalController;
import com.samsung.android.biometrics.app.setting.fingerprint.OpticalController;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsEnrollClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsIconOptionMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsInfo;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthSensorWindow;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsUiCustom;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback;
import com.samsung.android.biometrics.app.setting.knox.SecureFolderClient;
import com.samsung.android.biometrics.app.setting.knox.UCMUtils;
import com.samsung.android.biometrics.app.setting.knox.WorkProfileClient;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCoverClient;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.view.SemWindowManager;
import java.io.FileDescriptor;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: classes.dex */
public final class SysUiManager implements SemBiometricConstants, FpServiceProvider, DisplayStateManager.Callback, AodStatusMonitor.Callback {

    @VisibleForTesting
    AodStatusMonitor mAodStatusMonitor;

    @VisibleForTesting
    BroadcastReceiver mBroadCastReceiverForTSP;
    private final Context mContext;

    @VisibleForTesting
    SysUiClient mCurrentClient;
    private DisplayStateManager mDisplayStateManager;

    /* renamed from: mH */
    private final SysUiHandler f21mH;
    private boolean mHasPrepareRequest;
    private final IFingerprintService mIFingerprintService;

    @VisibleForTesting
    UdfpsIconOptionMonitor mIconOptionMonitor;
    private final Injector mInjector;
    private boolean mIsTouchDown;

    @VisibleForTesting
    OpticalController mOpticalController;
    private TaskStackObserver mTaskStackObserver;

    @VisibleForTesting
    UdfpsInfo mUdfpsInfo;
    UdfpsSensorWindow mUdfpsSensorWindow;
    private boolean mLastTspVisibilityCommand = false;
    private final SysUiServiceWrapper mSysUiServiceWrapper = new SysUiServiceWrapper();

    @VisibleForTesting
    public static class Injector {
        private final PowerManager.WakeLock mDrawWakeLock;
        private final PowerManager.WakeLock mPartialWakeLock;

        public Injector(Context context) {
            PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
            PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "BSS_SysUiManager:P");
            this.mPartialWakeLock = newWakeLock;
            newWakeLock.setReferenceCounted(false);
            PowerManager.WakeLock newWakeLock2 = powerManager.newWakeLock(128, "BSS_SysUiManager:D");
            this.mDrawWakeLock = newWakeLock2;
            newWakeLock2.setReferenceCounted(false);
        }

        public final void acquireWakeLock(long j) {
            try {
                this.mPartialWakeLock.acquire(j);
            } catch (Exception e) {
                Log.e("BSS_SysUiManager", "acquireWakeLock: p=" + e.getMessage());
            }
            try {
                this.mDrawWakeLock.acquire(j);
            } catch (Exception e2) {
                Log.e("BSS_SysUiManager", "acquireWakeLock: d=" + e2.getMessage());
            }
        }

        public final void releaseWakeLock() {
            PowerManager.WakeLock wakeLock = this.mDrawWakeLock;
            PowerManager.WakeLock wakeLock2 = this.mPartialWakeLock;
            try {
                if (wakeLock2.isHeld()) {
                    wakeLock2.release();
                }
            } catch (Exception e) {
                Log.e("BSS_SysUiManager", "releaseWakeLock: p=" + e.getMessage());
            }
            try {
                if (wakeLock.isHeld()) {
                    wakeLock.release();
                }
            } catch (Exception e2) {
                Log.e("BSS_SysUiManager", "releaseWakeLock: d=" + e2.getMessage());
            }
        }
    }

    public final class SysUiHandler extends Handler {
        SysUiHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Log.i("BSS_SysUiManager", Utils.getLogFormat(message));
            switch (message.what) {
                case 1:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    SysUiManager.m38$$Nest$mhandleShow(SysUiManager.this, message.arg1, message.arg2, (Bundle) someArgs.arg1, (ISemBiometricSysUiCallback) someArgs.arg2, ((Boolean) someArgs.arg3).booleanValue(), someArgs.argi1, (String) someArgs.arg4, ((Long) someArgs.arg5).longValue(), (PromptInfo) someArgs.arg6);
                    someArgs.recycle();
                    break;
                case 2:
                    SysUiManager sysUiManager = SysUiManager.this;
                    int i = message.arg1;
                    SysUiClient sysUiClient = sysUiManager.mCurrentClient;
                    if (sysUiClient != null && sysUiClient.getSessionId() == i) {
                        sysUiManager.mCurrentClient.stop();
                        break;
                    }
                    break;
                case 3:
                    SysUiManager.m36$$Nest$mhandleAuthenticationSucceeded(SysUiManager.this, (String) message.obj);
                    break;
                case 4:
                    SysUiManager sysUiManager2 = SysUiManager.this;
                    String str = (String) message.obj;
                    Handler.Callback callback = sysUiManager2.mCurrentClient;
                    if (callback instanceof AuthenticationConsumer) {
                        ((AuthenticationConsumer) callback).onAuthenticationFailed(str);
                        break;
                    }
                    break;
                case 5:
                    SysUiManager sysUiManager3 = SysUiManager.this;
                    int i2 = message.arg1;
                    int i3 = message.arg2;
                    String str2 = (String) message.obj;
                    Handler.Callback callback2 = sysUiManager3.mCurrentClient;
                    if (callback2 instanceof AuthenticationConsumer) {
                        AuthenticationConsumer authenticationConsumer = (AuthenticationConsumer) callback2;
                        if (i2 == 6) {
                            i2 = i3;
                        }
                        authenticationConsumer.onAuthenticationHelp(i2, str2);
                        break;
                    }
                    break;
                case 6:
                    SysUiManager sysUiManager4 = SysUiManager.this;
                    int i4 = message.arg1;
                    int i5 = message.arg2;
                    String str3 = (String) message.obj;
                    Handler.Callback callback3 = sysUiManager4.mCurrentClient;
                    if (callback3 instanceof AuthenticationConsumer) {
                        ((AuthenticationConsumer) callback3).onAuthenticationError(i4, i5, str3);
                        break;
                    }
                    break;
                case 7:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    SysUiManager.m37$$Nest$mhandleCommand(SysUiManager.this, someArgs2.argi1, message.arg1, message.arg2, (Bundle) someArgs2.arg1);
                    someArgs2.recycle();
                    break;
                case 9:
                    SysUiManager sysUiManager5 = SysUiManager.this;
                    SysUiClient sysUiClient2 = sysUiManager5.mCurrentClient;
                    if (sysUiClient2 != null) {
                        sysUiClient2.handleOnTaskStackListener();
                    }
                    OpticalController opticalController = sysUiManager5.mOpticalController;
                    if (opticalController != null) {
                        opticalController.handleOnTaskStackChanged();
                        break;
                    }
                    break;
                case 10:
                    SysUiManager.this.onFodTouchEvent(16, 0.0f, 0.0f);
                    break;
            }
        }
    }

    @VisibleForTesting
    final class SysUiServiceWrapper extends ISemBiometricSysUiService.Stub {
        SysUiServiceWrapper() {
        }

        private static boolean isRestricted() {
            return Binder.getCallingUid() != 1000;
        }

        public final void hideBiometricDialog(int i, int i2, int i3) {
            if (isRestricted()) {
                return;
            }
            SysUiManager.this.f21mH.obtainMessage(2, i, i2, Integer.valueOf(i3)).sendToTarget();
        }

        public final void onBiometricAuthenticated(int i, int i2, boolean z, String str) {
            if (isRestricted()) {
                return;
            }
            if (z) {
                SysUiManager.this.f21mH.obtainMessage(3, str).sendToTarget();
            } else {
                SysUiManager.this.f21mH.obtainMessage(4, str).sendToTarget();
            }
        }

        public final void onBiometricError(int i, int i2, int i3, int i4, String str) {
            if (isRestricted()) {
                return;
            }
            SysUiManager.this.f21mH.obtainMessage(6, i3, i4, str).sendToTarget();
        }

        public final void onBiometricHelp(int i, int i2, int i3, int i4, String str) {
            if (isRestricted()) {
                return;
            }
            SysUiManager.this.f21mH.obtainMessage(5, i3, i4, str).sendToTarget();
        }

        public final void sendCommand(int i, int i2, int i3, Bundle bundle) {
            if (isRestricted()) {
                return;
            }
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            if (bundle == null) {
                bundle = new Bundle();
            }
            obtain.arg1 = bundle;
            SysUiManager.this.f21mH.obtainMessage(7, i2, i3, obtain).sendToTarget();
        }

        public final void setBiometricTheme(int i, String str, byte[] bArr, FileDescriptor fileDescriptor) {
            if (i == 10000) {
                UdfpsUiCustom.setCustomTouchEffect(SysUiManager.this.mContext, str, fileDescriptor);
            } else if (i == 10001) {
                UdfpsUiCustom.setCustomFingerIcon(SysUiManager.this.mContext, str, fileDescriptor);
            }
        }

        public final void showBiometricDialog(int i, int i2, Bundle bundle, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, boolean z, int i3, String str, long j, PromptInfo promptInfo) {
            if (isRestricted()) {
                return;
            }
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i3;
            obtain.arg1 = bundle;
            obtain.arg2 = iSemBiometricSysUiCallback;
            obtain.arg3 = Boolean.valueOf(z);
            obtain.arg4 = str;
            obtain.arg5 = Long.valueOf(j);
            obtain.arg6 = promptInfo;
            SysUiManager.this.f21mH.obtainMessage(1, i, i2, obtain).sendToTarget();
        }
    }

    public class TaskStackObserver extends TaskStackListener {
        private boolean mIsWatching;

        public TaskStackObserver() {
        }

        public final void observe(boolean z) {
            if (this.mIsWatching == z) {
                return;
            }
            try {
                if (z) {
                    ActivityTaskManager.getService().registerTaskStackListener(this);
                    this.mIsWatching = true;
                } else {
                    ActivityTaskManager.getService().unregisterTaskStackListener(this);
                    this.mIsWatching = false;
                }
            } catch (Exception e) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("observe: "), "BSS_SysUiManager");
            }
        }

        public final void onTaskFocusChanged(int i, boolean z) {
            SysUiManager.this.f21mH.removeMessages(9);
            SysUiManager.this.f21mH.sendEmptyMessageDelayed(9, 100L);
        }

        public final void onTaskStackChanged() {
            SysUiManager.this.f21mH.removeMessages(9);
            SysUiManager.this.f21mH.sendEmptyMessageDelayed(9, 100L);
        }
    }

    /* renamed from: $r8$lambda$l3H5s4_NgC9-TiLIu72rmVBnrkU, reason: not valid java name */
    public static /* synthetic */ void m30$r8$lambda$l3H5s4_NgC9TiLIu72rmVBnrkU(SysUiManager sysUiManager, boolean z) {
        if (z == sysUiManager.mHasPrepareRequest) {
            return;
        }
        if (z) {
            sysUiManager.request(12, 1, 0L);
            sysUiManager.mHasPrepareRequest = true;
        } else {
            sysUiManager.request(12, 0, 0L);
            sysUiManager.mHasPrepareRequest = false;
        }
    }

    /* renamed from: $r8$lambda$nSeLkg2kR-riJWPIiXkWwUuGVHs, reason: not valid java name */
    public static void m31$r8$lambda$nSeLkg2kRriJWPIiXkWwUuGVHs(SysUiManager sysUiManager, boolean z) {
        if (sysUiManager.mLastTspVisibilityCommand == z) {
            return;
        }
        sysUiManager.mLastTspVisibilityCommand = z;
        Injector injector = sysUiManager.mInjector;
        Context context = sysUiManager.mContext;
        injector.getClass();
        SemInputDeviceManager semInputDeviceManager = (SemInputDeviceManager) context.getSystemService(SemInputDeviceManager.class);
        if (semInputDeviceManager != null) {
            semInputDeviceManager.setFodIconVisible(z ? 1 : 0);
            if (Utils.DEBUG) {
                Log.d("BSS_SysUiManager", "sendUdfpsIconVisibilityToTsp: " + z);
            }
        }
    }

    public static void $r8$lambda$nUdBqKsBc2BLgiXkXeBOwWKHMAo(SysUiManager sysUiManager, float f, float f2) {
        DisplayStateManager displayStateManager;
        sysUiManager.getClass();
        if (f == 0.0f || f2 == 0.0f || sysUiManager.mUdfpsInfo == null || (displayStateManager = sysUiManager.mDisplayStateManager) == null) {
            return;
        }
        int currentRotation = displayStateManager.getCurrentRotation();
        UdfpsInfo udfpsInfo = sysUiManager.mUdfpsInfo;
        boolean z = true;
        if (currentRotation != 1 && currentRotation != 3) {
            z = false;
        }
        Point centerPointInPortraitMode = udfpsInfo.getCenterPointInPortraitMode(z);
        int i = centerPointInPortraitMode.x - ((int) f);
        int i2 = centerPointInPortraitMode.y - ((int) f2);
        StringBuilder sb = new StringBuilder("onFodTouchEvent: distance = ");
        float f3 = centerPointInPortraitMode.x;
        sb.append((((float) Math.sqrt(Math.pow(centerPointInPortraitMode.y - f2, 2.0d) + Math.pow(f3 - f, 2.0d))) / Utils.getDisplayMetrics(sysUiManager.mContext).xdpi) * 25.4f);
        sb.append(", diff = ");
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        Log.i("BSS_SysUiManager", sb.toString());
    }

    /* renamed from: $r8$lambda$v-lAwftBEVxNmjPjEaZlziQbGck, reason: not valid java name */
    public static void m32$r8$lambda$vlAwftBEVxNmjPjEaZlziQbGck(SysUiManager sysUiManager) {
        OpticalController opticalController;
        if (sysUiManager.mIsTouchDown || sysUiManager.mDisplayStateManager.isOnState()) {
            return;
        }
        UdfpsIconOptionMonitor udfpsIconOptionMonitor = sysUiManager.mIconOptionMonitor;
        if ((udfpsIconOptionMonitor.isEnabledTapToShow() || udfpsIconOptionMonitor.isEnabledOnAod()) ? false : true) {
            return;
        }
        sysUiManager.mUdfpsInfo.getClass();
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL && (opticalController = sysUiManager.mOpticalController) != null) {
            opticalController.handleSingleTapEvent();
        }
        SysUiClient sysUiClient = sysUiManager.mCurrentClient;
        if (sysUiClient instanceof UdfpsKeyguardClient) {
            ((UdfpsKeyguardClient) sysUiClient).handleSingleTapEvent();
        }
    }

    /* renamed from: -$$Nest$mhandleAuthenticationSucceeded, reason: not valid java name */
    static void m36$$Nest$mhandleAuthenticationSucceeded(SysUiManager sysUiManager, String str) {
        OpticalController opticalController = sysUiManager.mOpticalController;
        if (opticalController != null) {
            if (sysUiManager.mCurrentClient instanceof UdfpsKeyguardClient) {
                if (Utils.Config.FEATURE_SUPPORT_AOD_TRANSITION_ANIMATION) {
                    sysUiManager.f21mH.postAtFrontOfQueue(new SysUiManager$$ExternalSyntheticLambda1(sysUiManager, 1));
                }
                sysUiManager.mOpticalController.handleAuthenticationSucceeded(sysUiManager.mCurrentClient.getSessionId());
            } else {
                opticalController.handleAuthenticationSucceeded(0);
            }
        }
        Handler.Callback callback = sysUiManager.mCurrentClient;
        if (callback instanceof AuthenticationConsumer) {
            ((AuthenticationConsumer) callback).onAuthenticationSucceeded(str);
            return;
        }
        Log.w("BSS_SysUiManager", "handleAuthenticationSucceeded: for non-authentication consumer: " + sysUiManager.mCurrentClient);
    }

    /* renamed from: -$$Nest$mhandleCommand, reason: not valid java name */
    static void m37$$Nest$mhandleCommand(SysUiManager sysUiManager, int i, int i2, int i3, Bundle bundle) {
        SysUiClient sysUiClient = sysUiManager.mCurrentClient;
        Object obj = sysUiClient;
        obj = sysUiClient;
        if (sysUiClient != null && i != 0) {
            int sessionId = sysUiClient.getSessionId();
            obj = sysUiClient;
            if (sessionId != i) {
                obj = null;
            }
        }
        if (i2 == 117) {
            if (obj instanceof UdfpsKeyguardClient) {
                ((UdfpsKeyguardClient) obj).onBouncerScreen();
            }
            return;
        }
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = true;
        if (i2 == 118) {
            OpticalController opticalController = sysUiManager.mOpticalController;
            if (opticalController == null || opticalController.hasMaskClient()) {
                return;
            }
            BackgroundThread backgroundThread = BackgroundThread.get();
            SysUiManager$$ExternalSyntheticLambda2 sysUiManager$$ExternalSyntheticLambda2 = new SysUiManager$$ExternalSyntheticLambda2(sysUiManager, z4, z2 ? 1 : 0);
            backgroundThread.getClass();
            BackgroundThread.post(sysUiManager$$ExternalSyntheticLambda2);
            return;
        }
        if (i2 == 500) {
            boolean z5 = bundle.getBoolean("KEY_KEYGUARD", false);
            bundle.getString("KEY_PACKAGE_NAME", "Unknown");
            OpticalController.MaskClient maskClient = new OpticalController.MaskClient(i, z5);
            if (i3 != 1) {
                sysUiManager.mOpticalController.removeMaskClient(i);
                return;
            }
            if (z5) {
                sysUiManager.mDisplayStateManager.updateDisplayState();
            }
            sysUiManager.mOpticalController.addMaskClient(maskClient);
            BackgroundThread backgroundThread2 = BackgroundThread.get();
            SysUiManager$$ExternalSyntheticLambda2 sysUiManager$$ExternalSyntheticLambda22 = new SysUiManager$$ExternalSyntheticLambda2(sysUiManager, z, z3 ? 1 : 0);
            backgroundThread2.getClass();
            BackgroundThread.post(sysUiManager$$ExternalSyntheticLambda22);
            return;
        }
        if (i2 == 501) {
            SysUiClient sysUiClient2 = sysUiManager.mCurrentClient;
            if (sysUiClient2 != null) {
                sysUiClient2.stop();
            }
            if (i3 != 1) {
                sysUiManager.mOpticalController.turnOffCalibrationLightSource();
                sysUiManager.mOpticalController.removeMaskClient(i);
                return;
            } else {
                sysUiManager.mUdfpsInfo.setCalibrationLightColor(bundle.getString("nits", ""));
                bundle.getString("KEY_PACKAGE_NAME", "UnknownCalibrationClient");
                sysUiManager.mOpticalController.addMaskClient(new OpticalController.MaskClient(i, false));
                sysUiManager.f21mH.post(new SysUiManager$$ExternalSyntheticLambda1(sysUiManager, 2));
                return;
            }
        }
        if (i2 == 600) {
            if (obj instanceof FpGestureConsumer) {
                ((FpGestureConsumer) obj).onGestureEvent();
                return;
            }
            return;
        }
        switch (i2) {
            case 112:
                if (obj instanceof UdfpsClient) {
                    ((UdfpsClient) obj).pause();
                    break;
                }
                break;
            case 113:
                if (obj instanceof UdfpsClient) {
                    ((UdfpsClient) obj).resume();
                    break;
                }
                break;
            case 114:
                if (obj instanceof UdfpsKeyguardClient) {
                    ((UdfpsKeyguardClient) obj).moveSensorIcon(bundle.getInt("x"), bundle.getInt("y"));
                    break;
                }
                break;
            case 115:
                if (i3 != 1) {
                    sysUiManager.mDisplayStateManager.onScreenOffFromKeyguard();
                    break;
                } else {
                    sysUiManager.mDisplayStateManager.onScreenOnFromKeyguard();
                    break;
                }
            default:
                switch (i2) {
                    case 201:
                        if (obj instanceof AuthenticationConsumer) {
                            ((AuthenticationConsumer) obj).onAuthenticationHelp(i3, FingerprintManager.getAcquiredString(sysUiManager.mContext, i3, 0));
                            break;
                        }
                        break;
                    case 202:
                        if (i3 == 1004) {
                            if (Utils.Config.FP_FEATURE_TSP_BLOCK && sysUiManager.mUdfpsSensorWindow.isVisible()) {
                                sysUiManager.mUdfpsSensorWindow.hideSensorIcon();
                                SysUiClient sysUiClient3 = sysUiManager.mCurrentClient;
                                if (sysUiClient3 instanceof UdfpsAuthClient) {
                                    ((UdfpsAuthClient) sysUiClient3).handleTspBlock(true);
                                    break;
                                }
                            }
                        } else if (i3 == 1005) {
                            if (Utils.Config.FP_FEATURE_TSP_BLOCK && sysUiManager.mUdfpsSensorWindow.isVisible()) {
                                sysUiManager.mUdfpsSensorWindow.showSensorIcon();
                                SysUiClient sysUiClient4 = sysUiManager.mCurrentClient;
                                if (sysUiClient4 instanceof UdfpsAuthClient) {
                                    ((UdfpsAuthClient) sysUiClient4).handleTspBlock(false);
                                    break;
                                }
                            }
                        } else if (i3 == 10002) {
                            SysUiClient sysUiClient5 = sysUiManager.mCurrentClient;
                            if ((sysUiClient5 instanceof UdfpsAuthClient) && i == sysUiClient5.getSessionId()) {
                                ((UdfpsAuthClient) sysUiManager.mCurrentClient).onCaptureStart();
                                break;
                            }
                        } else if (i3 == 10003) {
                            OpticalController opticalController2 = sysUiManager.mOpticalController;
                            if (opticalController2 != null) {
                                opticalController2.handleCaptureCompleted();
                            }
                            SysUiClient sysUiClient6 = sysUiManager.mCurrentClient;
                            if ((sysUiClient6 instanceof UdfpsAuthClient) && i == sysUiClient6.getSessionId()) {
                                if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                                    sysUiManager.f21mH.sendEmptyMessageDelayed(10, 1000L);
                                }
                                ((UdfpsAuthClient) sysUiManager.mCurrentClient).onCaptureComplete();
                                break;
                            }
                        }
                        break;
                    case 203:
                        if (obj instanceof AuthenticationConsumer) {
                            ((AuthenticationConsumer) obj).onAuthenticationError(i3, 0, null);
                            break;
                        }
                        break;
                    case 204:
                        if (obj instanceof AuthenticationConsumer) {
                            ((AuthenticationConsumer) obj).onAuthenticationError(8, i3, null);
                            break;
                        }
                        break;
                }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0218  */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.samsung.android.biometrics.app.setting.SysUiManager$Injector$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r3v17, types: [com.samsung.android.biometrics.app.setting.SysUiManager$$ExternalSyntheticLambda3] */
    /* renamed from: -$$Nest$mhandleShow, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static void m38$$Nest$mhandleShow(final SysUiManager sysUiManager, int i, int i2, Bundle bundle, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, boolean z, int i3, String str, long j, PromptInfo promptInfo) {
        boolean z2;
        String str2;
        String str3;
        SysUiClient fpGestureCalibrationClient;
        SysUiClient biometricPromptClient;
        Display display;
        BiometricPromptClient biometricPromptClient2;
        SysUiClient sysUiClient;
        boolean z3;
        final SysUiManager sysUiManager2;
        int i4 = i2;
        if (i4 == 0) {
            sysUiManager.getClass();
            if (iSemBiometricSysUiCallback != null) {
                try {
                    iSemBiometricSysUiCallback.onError(i, 3, -1);
                    return;
                } catch (RemoteException e) {
                    Log.w("BSS_SysUiManager", "handleShow: " + e.getMessage());
                    return;
                }
            }
            return;
        }
        SysUiClient sysUiClient2 = sysUiManager.mCurrentClient;
        if (sysUiClient2 != null) {
            if (sysUiClient2.getSessionId() == i) {
                return;
            }
            SysUiClient sysUiClient3 = sysUiManager.mCurrentClient;
            if (sysUiClient3 instanceof UdfpsKeyguardClient) {
                ((UdfpsKeyguardClient) sysUiClient3).stopImmediate();
            } else {
                sysUiClient3.stop();
            }
        }
        final Injector injector = sysUiManager.mInjector;
        final Context context = sysUiManager.mContext;
        SysUiHandler sysUiHandler = sysUiManager.f21mH;
        DisplayStateManager displayStateManager = sysUiManager.mDisplayStateManager;
        UdfpsInfo udfpsInfo = sysUiManager.mUdfpsInfo;
        UdfpsSensorWindow udfpsSensorWindow = sysUiManager.mUdfpsSensorWindow;
        injector.getClass();
        Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        ?? r11 = new Consumer() { // from class: com.samsung.android.biometrics.app.setting.SysUiManager$Injector$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FpServiceProvider fpServiceProvider = FpServiceProvider.this;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                SysUiManager sysUiManager3 = (SysUiManager) fpServiceProvider;
                sysUiManager3.getClass();
                BackgroundThread backgroundThread = BackgroundThread.get();
                SysUiManager$$ExternalSyntheticLambda2 sysUiManager$$ExternalSyntheticLambda2 = new SysUiManager$$ExternalSyntheticLambda2(sysUiManager3, booleanValue, 1);
                backgroundThread.getClass();
                BackgroundThread.post(sysUiManager$$ExternalSyntheticLambda2);
            }
        };
        if ((i4 & 64) == 0) {
            if ((i4 & 4) != 0) {
                z2 = true;
                str2 = "BSS_SysUiManager";
                sysUiClient = new UdfpsKeyguardClient(context, i3, i, iSemBiometricSysUiCallback, bundle2, str, displayStateManager, udfpsSensorWindow, udfpsInfo, r11, sysUiManager, new PowerServiceProvider() { // from class: com.samsung.android.biometrics.app.setting.SysUiManager.Injector.1
                    @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
                    public final void acquireWakeLock(long j2) {
                        injector.acquireWakeLock(j2);
                    }

                    @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
                    public final boolean isPowerSaveMode() {
                        injector.getClass();
                        return ((PowerManager) context.getSystemService(PowerManager.class)).isPowerSaveMode();
                    }

                    @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
                    public final void releaseWakeLock() {
                        injector.releaseWakeLock();
                    }
                });
            } else {
                z2 = true;
                str2 = "BSS_SysUiManager";
                if ((i4 & 2) != 0) {
                    sysUiClient = new UdfpsPrivilegedAuthClient(context, i3, i, iSemBiometricSysUiCallback, bundle2, str, displayStateManager, udfpsSensorWindow, udfpsInfo, r11, z);
                } else if ((i4 & 1) != 0) {
                    sysUiClient = new UdfpsAuthClient(context, i3, i, iSemBiometricSysUiCallback, bundle2, str, displayStateManager, udfpsSensorWindow, udfpsInfo, r11);
                } else {
                    boolean z4 = Utils.DEBUG;
                    if (((i4 & 8) == 0 && (i4 & 4096) == 0 && (i4 & 8192) == 0 && (i4 & 16384) == 0 && (32768 & i4) == 0) ? false : true) {
                        PromptConfig promptConfig = new PromptConfig(context, promptInfo, bundle2, i2, i3, j, new LockPatternUtils(context));
                        if (!promptConfig.isCheckToEnrollMode()) {
                            if (promptConfig.isKnoxProfile()) {
                                if (promptConfig.isKnoxManagedProfile()) {
                                    biometricPromptClient2 = new WorkProfileClient(context, i, iSemBiometricSysUiCallback, sysUiHandler.getLooper(), bundle2, str, promptConfig);
                                } else if (promptConfig.isSecureFolder()) {
                                    biometricPromptClient2 = new SecureFolderClient(context, i, iSemBiometricSysUiCallback, sysUiHandler.getLooper(), bundle2, str, promptConfig);
                                }
                            }
                            if (Utils.Config.FEATURE_SUPPORT_BP_IN_COVER_SCREEN && SemWindowManager.getInstance().isFolded()) {
                                DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
                                if (displayManager != null) {
                                    Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
                                    int length = displays.length;
                                    for (int i5 = 0; i5 < length; i5++) {
                                        display = displays[i5];
                                        if (display.getDisplayId() == 1) {
                                            str3 = str2;
                                            break;
                                        }
                                    }
                                }
                                str3 = str2;
                                Log.w(str3, "getCoverDisplay: No built in display");
                                display = context.getDisplay();
                                biometricPromptClient = new BiometricPromptCoverClient(context.createDisplayContext(display), i, iSemBiometricSysUiCallback, sysUiHandler.getLooper(), bundle2, str, promptConfig);
                            } else {
                                str3 = str2;
                                biometricPromptClient = new BiometricPromptClient(context, i, iSemBiometricSysUiCallback, sysUiHandler.getLooper(), bundle2, str, promptConfig);
                            }
                            if (biometricPromptClient instanceof UdfpsKeyguardClient) {
                                UdfpsKeyguardClient udfpsKeyguardClient = (UdfpsKeyguardClient) biometricPromptClient;
                                z3 = z2;
                                sysUiManager2 = sysUiManager;
                                udfpsKeyguardClient.setAodStatusMonitor(sysUiManager2.mAodStatusMonitor);
                                udfpsKeyguardClient.setUdfpsIconOptionMonitor(sysUiManager2.mIconOptionMonitor);
                            } else {
                                z3 = z2;
                                sysUiManager2 = sysUiManager;
                                if ((biometricPromptClient instanceof BiometricPromptClient) && Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                                    final BiometricPromptClient biometricPromptClient3 = (BiometricPromptClient) biometricPromptClient;
                                    if (biometricPromptClient3.getPromptConfig().canUseFingerprint() && (!UCMUtils.isUCMKeyguardEnabled() || UCMUtils.isSupportBiometricForUCM())) {
                                        UdfpsPrivilegedAuthSensorWindow udfpsPrivilegedAuthSensorWindow = new UdfpsPrivilegedAuthSensorWindow(sysUiManager2.mContext, new UdfpsWindowCallback() { // from class: com.samsung.android.biometrics.app.setting.SysUiManager.1
                                            @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback
                                            public final void onSensorIconVisibilityChanged(int i6) {
                                                int i7 = 1;
                                                boolean z5 = i6 == 0;
                                                SysUiManager sysUiManager3 = SysUiManager.this;
                                                sysUiManager3.getClass();
                                                BackgroundThread backgroundThread = BackgroundThread.get();
                                                SysUiManager$$ExternalSyntheticLambda2 sysUiManager$$ExternalSyntheticLambda2 = new SysUiManager$$ExternalSyntheticLambda2(sysUiManager3, z5, i7);
                                                backgroundThread.getClass();
                                                BackgroundThread.post(sysUiManager$$ExternalSyntheticLambda2);
                                            }

                                            @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback
                                            public final void onUserCancel(int i6) {
                                                biometricPromptClient3.onUserCancel(i6);
                                            }
                                        }, sysUiManager2.mUdfpsInfo, sysUiManager2.mDisplayStateManager, null, null, false);
                                        udfpsPrivilegedAuthSensorWindow.initFromBaseWindow(sysUiManager2.mUdfpsSensorWindow);
                                        biometricPromptClient3.setUdfpsAuthSensorWindow(udfpsPrivilegedAuthSensorWindow);
                                        biometricPromptClient3.setUdfpsSensorInfo(sysUiManager2.mUdfpsInfo);
                                        sysUiManager2.mUdfpsInfo.getClass();
                                        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
                                            biometricPromptClient3.setRemoveUdfpsClient(new IntConsumer() { // from class: com.samsung.android.biometrics.app.setting.SysUiManager$$ExternalSyntheticLambda3
                                                @Override // java.util.function.IntConsumer
                                                public final void accept(int i6) {
                                                    SysUiManager.this.mOpticalController.removeMaskClient(i6);
                                                }
                                            });
                                        }
                                        i4 |= 8;
                                    }
                                }
                            }
                            Log.d(str3, "new Client from " + biometricPromptClient.getPackageName());
                            boolean z5 = Utils.DEBUG;
                            if (((Utils.Config.FEATURE_SUPPORT_FINGERPRINT || ((i4 & 1) == 0 && (i4 & 2) == 0 && (i4 & 4) == 0 && (i4 & 8) == 0 && (i4 & 64) == 0)) ? false : z3) && sysUiManager2.mUdfpsInfo != null && Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
                                sysUiManager2.mOpticalController.addMaskClient(new OpticalController.MaskClient(biometricPromptClient));
                            }
                            sysUiManager2.mCurrentClient = biometricPromptClient;
                            biometricPromptClient.start(new ClientCallback() { // from class: com.samsung.android.biometrics.app.setting.SysUiManager.2
                                @Override // com.samsung.android.biometrics.app.setting.ClientCallback
                                public final void onClientFinished(SysUiClient sysUiClient4) {
                                    SysUiManager sysUiManager3 = SysUiManager.this;
                                    if (sysUiManager3.mCurrentClient == sysUiClient4) {
                                        sysUiManager3.mCurrentClient = null;
                                    }
                                    OpticalController opticalController = sysUiManager3.mOpticalController;
                                    if (opticalController != null) {
                                        opticalController.removeMaskClient(sysUiClient4.getSessionId());
                                    }
                                    if (sysUiClient4 instanceof UdfpsKeyguardClient) {
                                        sysUiManager3.mDisplayStateManager.onScreenUnknownFromKeyguard();
                                    }
                                    if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                                        sysUiManager3.onFodTouchEvent(16, 0.0f, 0.0f);
                                    }
                                }

                                @Override // com.samsung.android.biometrics.app.setting.ClientCallback
                                public final void onClientStarted() {
                                }
                            });
                            BackgroundThread backgroundThread = BackgroundThread.get();
                            SysUiManager$$ExternalSyntheticLambda2 sysUiManager$$ExternalSyntheticLambda2 = new SysUiManager$$ExternalSyntheticLambda2(sysUiManager2, false, false ? 1 : 0);
                            backgroundThread.getClass();
                            BackgroundThread.post(sysUiManager$$ExternalSyntheticLambda2);
                        }
                        biometricPromptClient2 = new FpCheckToEnrollClient(context, i, iSemBiometricSysUiCallback, sysUiHandler.getLooper(), bundle2, str, promptConfig);
                        sysUiClient = biometricPromptClient2;
                    } else {
                        str3 = str2;
                        if (!((65536 & i4) != 0)) {
                            throw new IllegalArgumentException("Unknown client type");
                        }
                        fpGestureCalibrationClient = new FpGestureCalibrationClient(context, i3, i, iSemBiometricSysUiCallback, sysUiHandler.getLooper(), bundle2, str);
                    }
                }
            }
            biometricPromptClient = sysUiClient;
            str3 = str2;
            if (biometricPromptClient instanceof UdfpsKeyguardClient) {
            }
            Log.d(str3, "new Client from " + biometricPromptClient.getPackageName());
            boolean z52 = Utils.DEBUG;
            if ((Utils.Config.FEATURE_SUPPORT_FINGERPRINT || ((i4 & 1) == 0 && (i4 & 2) == 0 && (i4 & 4) == 0 && (i4 & 8) == 0 && (i4 & 64) == 0)) ? false : z3) {
                sysUiManager2.mOpticalController.addMaskClient(new OpticalController.MaskClient(biometricPromptClient));
            }
            sysUiManager2.mCurrentClient = biometricPromptClient;
            biometricPromptClient.start(new ClientCallback() { // from class: com.samsung.android.biometrics.app.setting.SysUiManager.2
                @Override // com.samsung.android.biometrics.app.setting.ClientCallback
                public final void onClientFinished(SysUiClient sysUiClient4) {
                    SysUiManager sysUiManager3 = SysUiManager.this;
                    if (sysUiManager3.mCurrentClient == sysUiClient4) {
                        sysUiManager3.mCurrentClient = null;
                    }
                    OpticalController opticalController = sysUiManager3.mOpticalController;
                    if (opticalController != null) {
                        opticalController.removeMaskClient(sysUiClient4.getSessionId());
                    }
                    if (sysUiClient4 instanceof UdfpsKeyguardClient) {
                        sysUiManager3.mDisplayStateManager.onScreenUnknownFromKeyguard();
                    }
                    if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                        sysUiManager3.onFodTouchEvent(16, 0.0f, 0.0f);
                    }
                }

                @Override // com.samsung.android.biometrics.app.setting.ClientCallback
                public final void onClientStarted() {
                }
            });
            BackgroundThread backgroundThread2 = BackgroundThread.get();
            SysUiManager$$ExternalSyntheticLambda2 sysUiManager$$ExternalSyntheticLambda22 = new SysUiManager$$ExternalSyntheticLambda2(sysUiManager2, false, false ? 1 : 0);
            backgroundThread2.getClass();
            BackgroundThread.post(sysUiManager$$ExternalSyntheticLambda22);
        }
        fpGestureCalibrationClient = new UdfpsEnrollClient(context, i3, i, iSemBiometricSysUiCallback, bundle2, str, displayStateManager, udfpsSensorWindow, udfpsInfo, r11);
        z2 = true;
        str3 = "BSS_SysUiManager";
        biometricPromptClient = fpGestureCalibrationClient;
        if (biometricPromptClient instanceof UdfpsKeyguardClient) {
        }
        Log.d(str3, "new Client from " + biometricPromptClient.getPackageName());
        boolean z522 = Utils.DEBUG;
        if ((Utils.Config.FEATURE_SUPPORT_FINGERPRINT || ((i4 & 1) == 0 && (i4 & 2) == 0 && (i4 & 4) == 0 && (i4 & 8) == 0 && (i4 & 64) == 0)) ? false : z3) {
        }
        sysUiManager2.mCurrentClient = biometricPromptClient;
        biometricPromptClient.start(new ClientCallback() { // from class: com.samsung.android.biometrics.app.setting.SysUiManager.2
            @Override // com.samsung.android.biometrics.app.setting.ClientCallback
            public final void onClientFinished(SysUiClient sysUiClient4) {
                SysUiManager sysUiManager3 = SysUiManager.this;
                if (sysUiManager3.mCurrentClient == sysUiClient4) {
                    sysUiManager3.mCurrentClient = null;
                }
                OpticalController opticalController = sysUiManager3.mOpticalController;
                if (opticalController != null) {
                    opticalController.removeMaskClient(sysUiClient4.getSessionId());
                }
                if (sysUiClient4 instanceof UdfpsKeyguardClient) {
                    sysUiManager3.mDisplayStateManager.onScreenUnknownFromKeyguard();
                }
                if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                    sysUiManager3.onFodTouchEvent(16, 0.0f, 0.0f);
                }
            }

            @Override // com.samsung.android.biometrics.app.setting.ClientCallback
            public final void onClientStarted() {
            }
        });
        BackgroundThread backgroundThread22 = BackgroundThread.get();
        SysUiManager$$ExternalSyntheticLambda2 sysUiManager$$ExternalSyntheticLambda222 = new SysUiManager$$ExternalSyntheticLambda2(sysUiManager2, false, false ? 1 : 0);
        backgroundThread22.getClass();
        BackgroundThread.post(sysUiManager$$ExternalSyntheticLambda222);
    }

    @VisibleForTesting
    SysUiManager(Context context, Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
        this.f21mH = new SysUiHandler(context.getMainLooper());
        injector.getClass();
        this.mIFingerprintService = IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
    }

    private int requestToFpSvc(int i, int i2, long j, String str) {
        try {
            return this.mIFingerprintService.semBioSysUiRequest(i, i2, j, str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public final void acquireDVFS() {
        requestToFpSvc(8, 1, 0L, null);
    }

    public final void deliverTouchEvent(int i, int i2, int i3) {
        long j;
        if (i == 2) {
            j = i3 | (i2 << 16);
        } else {
            j = 0;
        }
        requestToFpSvc(9, i, j, null);
    }

    public final void destroy() {
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient != null) {
            sysUiClient.stopImmediate();
            this.mCurrentClient = null;
        }
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        if (displayStateManager != null) {
            displayStateManager.stop();
            this.mDisplayStateManager = null;
        }
        TaskStackObserver taskStackObserver = this.mTaskStackObserver;
        if (taskStackObserver != null) {
            taskStackObserver.observe(false);
        }
        BroadcastReceiver broadcastReceiver = this.mBroadCastReceiverForTSP;
        if (broadcastReceiver != null) {
            try {
                this.mContext.unregisterReceiver(broadcastReceiver);
                this.mBroadCastReceiverForTSP = null;
            } catch (Exception e) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("unregisterBroadcastReceiver: "), "BSS_SysUiManager");
            }
        }
        UdfpsIconOptionMonitor udfpsIconOptionMonitor = this.mIconOptionMonitor;
        if (udfpsIconOptionMonitor != null) {
            udfpsIconOptionMonitor.stop();
        }
        AodStatusMonitor aodStatusMonitor = this.mAodStatusMonitor;
        if (aodStatusMonitor != null) {
            aodStatusMonitor.removeCallback(this);
            this.mAodStatusMonitor.stop();
        }
        UdfpsSensorWindow udfpsSensorWindow = this.mUdfpsSensorWindow;
        if (udfpsSensorWindow != null) {
            udfpsSensorWindow.removeView();
        }
        OpticalController opticalController = this.mOpticalController;
        if (opticalController != null) {
            opticalController.stop();
        }
        DisplayBrightnessMonitor.getInstance().stop();
        this.f21mH.removeCallbacksAndMessages(null);
    }

    public final void disableDisplayColorFunction() {
        requestToFpSvc(7, 0, 0L, null);
    }

    public final void enableDisplayColorFunction() {
        requestToFpSvc(7, 1, 0L, null);
    }

    public final void forceQDB() {
        requestToFpSvc(100, 0, 0L, null);
    }

    public final SysUiServiceWrapper getSysUiServiceWrapper() {
        return this.mSysUiServiceWrapper;
    }

    public final void init() {
        OpticalController opticalController;
        Injector injector = this.mInjector;
        Context context = this.mContext;
        SysUiHandler sysUiHandler = this.f21mH;
        injector.getClass();
        DisplayStateManager displayStateManager = new DisplayStateManager(context, sysUiHandler, BackgroundThread.get().getLooper(), new DisplayStateManager.Injector(context));
        this.mDisplayStateManager = displayStateManager;
        displayStateManager.start();
        this.mDisplayStateManager.registerCallback(this);
        TaskStackObserver taskStackObserver = new TaskStackObserver();
        this.mTaskStackObserver = taskStackObserver;
        taskStackObserver.observe(true);
        if (Utils.IS_DEBUG_LEVEL_MID_OR_HIGH) {
            Looper.myLooper().setPerfLogEnable();
            Looper.myLooper().setSlowLogThresholdMs(100L, 300L);
        }
        if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE && this.mBroadCastReceiverForTSP == null) {
            this.mBroadCastReceiverForTSP = new BroadcastReceiver() { // from class: com.samsung.android.biometrics.app.setting.SysUiManager.3
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    float f;
                    float f2;
                    String action = intent.getAction();
                    if ("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE".equals(action) || "com.samsung.android.fingerprint.action.FINGER_ON_DISPLAY".equals(action)) {
                        int intExtra = intent.getIntExtra("info", -1);
                        float[] floatArrayExtra = intent.getFloatArrayExtra("location");
                        Log.i("BSS_SysUiManager", action + ", " + intExtra);
                        if (floatArrayExtra == null || floatArrayExtra.length != 2) {
                            f = 0.0f;
                            f2 = 0.0f;
                        } else {
                            f = floatArrayExtra[0];
                            f2 = floatArrayExtra[1];
                        }
                        if (intExtra != 8) {
                            switch (intExtra) {
                                case 15:
                                case 16:
                                case 17:
                                    SysUiManager.this.onFodTouchEvent(intExtra, f, f2);
                                    break;
                            }
                        } else if (Utils.Config.FEATURE_SUPPORT_AOD || Utils.Config.FP_FEATURE_FAKE_AOD) {
                            SysUiManager.this.onFodSingleTap();
                        }
                    }
                }
            };
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE");
                this.mContext.registerReceiverAsUser(this.mBroadCastReceiverForTSP, UserHandle.ALL, intentFilter, "com.samsung.android.app.aodservice.permission.BROADCAST_RECEIVER", this.f21mH);
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("com.samsung.android.fingerprint.action.FINGER_ON_DISPLAY");
                this.mContext.registerReceiverAsUser(this.mBroadCastReceiverForTSP, UserHandle.ALL, intentFilter2, "com.samsung.android.permission.BROADCAST_QUICKACCESS", this.f21mH);
            } catch (Exception e) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("registerBroadcastReceiver: "), "BSS_SysUiManager");
            }
        }
        if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
            Injector injector2 = this.mInjector;
            Context context2 = this.mContext;
            injector2.getClass();
            Bundle bundle = new Bundle();
            try {
                IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint")).semGetSensorData(bundle);
            } catch (RemoteException e2) {
                Log.w("BSS_SysUiManager", "SysUiManager: " + e2.getMessage());
            }
            this.mUdfpsInfo = new UdfpsInfo(context2, bundle);
            Injector injector3 = this.mInjector;
            Context context3 = this.mContext;
            injector3.getClass();
            AodStatusMonitor aodStatusMonitor = new AodStatusMonitor(context3);
            this.mAodStatusMonitor = aodStatusMonitor;
            aodStatusMonitor.start();
            this.mAodStatusMonitor.addCallback(this);
            Injector injector4 = this.mInjector;
            Context context4 = this.mContext;
            injector4.getClass();
            UdfpsIconOptionMonitor udfpsIconOptionMonitor = new UdfpsIconOptionMonitor(context4);
            this.mIconOptionMonitor = udfpsIconOptionMonitor;
            udfpsIconOptionMonitor.start();
            if (this.mUdfpsSensorWindow == null) {
                Injector injector5 = this.mInjector;
                Context context5 = this.mContext;
                UdfpsInfo udfpsInfo = this.mUdfpsInfo;
                DisplayStateManager displayStateManager2 = this.mDisplayStateManager;
                injector5.getClass();
                UdfpsSensorWindow udfpsSensorWindow = new UdfpsSensorWindow(context5, null, udfpsInfo, displayStateManager2);
                this.mUdfpsSensorWindow = udfpsSensorWindow;
                udfpsSensorWindow.init();
                this.mUdfpsSensorWindow.setVisibility(4);
                this.mUdfpsSensorWindow.addView();
            }
            this.mUdfpsInfo.getClass();
            if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL && this.mOpticalController == null) {
                Injector injector6 = this.mInjector;
                Context context6 = this.mContext;
                UdfpsInfo udfpsInfo2 = this.mUdfpsInfo;
                DisplayStateManager displayStateManager3 = this.mDisplayStateManager;
                UdfpsIconOptionMonitor udfpsIconOptionMonitor2 = this.mIconOptionMonitor;
                AodStatusMonitor aodStatusMonitor2 = this.mAodStatusMonitor;
                injector6.getClass();
                if (Utils.Config.FP_FEATURE_LOCAL_HBM) {
                    DisplayBrightnessMonitor.getInstance();
                    opticalController = new LhbmOpticalController(context6, udfpsInfo2, this, displayStateManager3, udfpsIconOptionMonitor2, aodStatusMonitor2);
                } else {
                    opticalController = new OpticalController(context6, udfpsInfo2, this, DisplayBrightnessMonitor.getInstance(), displayStateManager3, udfpsIconOptionMonitor2, aodStatusMonitor2);
                }
                this.mOpticalController = opticalController;
                opticalController.start();
            }
        }
    }

    public final boolean isKeyguardBouncerShowing() {
        return requestToFpSvc(6, 0, 0L, null) == 1;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.Callback
    public final void onAodStart() {
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        if (displayStateManager != null) {
            displayStateManager.onAodStart();
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        if (displayStateManager != null) {
            displayStateManager.onConfigurationChanged();
        }
        OpticalController opticalController = this.mOpticalController;
        if (opticalController != null) {
            opticalController.handleConfigurationChanged(configuration);
        }
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient != null) {
            Iterator<SysUiWindow> it = sysUiClient.mWindows.iterator();
            while (it.hasNext()) {
                it.next().onConfigurationChanged(configuration);
            }
        }
        UdfpsInfo udfpsInfo = this.mUdfpsInfo;
        if (udfpsInfo != null) {
            udfpsInfo.updateSensorInfo();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.DisplayStateManager.Callback
    public final void onDisplayStateChanged(int i) {
        OpticalController opticalController = this.mOpticalController;
        if (opticalController != null) {
            opticalController.handleDisplayStateChanged(i);
        }
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient != null) {
            sysUiClient.onDisplayStateChanged(i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002c  */
    @VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void onFodSingleTap() {
        boolean z;
        UdfpsInfo udfpsInfo = this.mUdfpsInfo;
        if (udfpsInfo == null) {
            return;
        }
        int i = 0;
        if (!Utils.Config.FP_FEATURE_SENSOR_IS_ULTRASONIC) {
            udfpsInfo.getClass();
            if (!Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL || this.mAodStatusMonitor.isEnabled()) {
                z = false;
                SysUiManager$$ExternalSyntheticLambda1 sysUiManager$$ExternalSyntheticLambda1 = new SysUiManager$$ExternalSyntheticLambda1(this, i);
                if (z) {
                    sysUiManager$$ExternalSyntheticLambda1.run();
                    return;
                } else {
                    this.f21mH.postDelayed(sysUiManager$$ExternalSyntheticLambda1, 300L);
                    return;
                }
            }
        }
        z = true;
        SysUiManager$$ExternalSyntheticLambda1 sysUiManager$$ExternalSyntheticLambda12 = new SysUiManager$$ExternalSyntheticLambda1(this, i);
        if (z) {
        }
    }

    @VisibleForTesting
    void onFodTouchEvent(int i, final float f, final float f2) {
        if (this.mUdfpsInfo == null) {
            return;
        }
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
            this.f21mH.removeMessages(10);
            if (i == 15) {
                this.mIsTouchDown = true;
                OpticalController opticalController = this.mOpticalController;
                if (opticalController != null) {
                    opticalController.onTouchDown();
                }
            } else if (this.mIsTouchDown) {
                this.mIsTouchDown = false;
                OpticalController opticalController2 = this.mOpticalController;
                if (opticalController2 != null) {
                    opticalController2.onTouchUp();
                }
            }
        }
        if (i == 15) {
            BackgroundThread backgroundThread = BackgroundThread.get();
            Runnable runnable = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.SysUiManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SysUiManager.$r8$lambda$nUdBqKsBc2BLgiXkXeBOwWKHMAo(SysUiManager.this, f, f2);
                }
            };
            backgroundThread.getClass();
            BackgroundThread.post(runnable);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.DisplayStateManager.Callback
    public final void onRotationStateChanged(int i) {
        OpticalController opticalController = this.mOpticalController;
        if (opticalController != null) {
            opticalController.handleRotationInfoChanged(i);
        }
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient != null) {
            Iterator<SysUiWindow> it = sysUiClient.mWindows.iterator();
            while (it.hasNext()) {
                it.next().onRotationInfoChanged(i);
            }
        }
    }

    public final void request(int i, int i2, long j) {
        requestToFpSvc(i, i2, j, null);
    }

    public final void turnOffHwLightSource() {
        requestToFpSvc(5, 0, 0L, null);
    }

    public final void turnOnHwLightSource() {
        requestToFpSvc(5, 1, 0L, null);
    }

    public final void turnOnTsp() {
        this.mUdfpsInfo.getClass();
        if (Utils.Config.FP_FEATURE_SENSOR_IS_ULTRASONIC && this.mAodStatusMonitor.isAodTransitionAnimationEnabled()) {
            requestToFpSvc(4, 1, 0L, "BSS");
        } else {
            requestToFpSvc(4, 1, 0L, null);
        }
    }

    public final void turnOnTspHalfMode() {
        requestToFpSvc(4, 2, 0L, null);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.Callback
    public final void onAodStop() {
    }
}
