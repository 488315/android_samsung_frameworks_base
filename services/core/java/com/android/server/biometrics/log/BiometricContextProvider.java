package com.android.server.biometrics.log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.AuthenticateOptions;
import android.hardware.biometrics.IBiometricContextListener;
import android.hardware.biometrics.common.AuthenticateReason;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Slog;
import android.view.WindowManager;

import com.android.internal.logging.InstanceId;
import com.android.internal.statusbar.ISessionListener;
import com.android.internal.statusbar.IStatusBarService;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BiometricContextProvider implements BiometricContext {
    public static BiometricContextProvider sInstance;
    public final AuthSessionCoordinator mAuthSessionCoordinator;
    public final Handler mHandler;
    public final AnonymousClass2 mIBiometricContextListener;
    public final AnonymousClass3 mISessionListener;
    public final WindowManager mWindowManager;
    public final Map mSubscribers = new ConcurrentHashMap();
    public final Map mSession = new ConcurrentHashMap();
    public int mDockState = 0;
    public int mFoldState = 0;
    public int mDisplayState = 0;
    public boolean mIsHardwareIgnoringTouches = false;
    final BroadcastReceiver mDockStateReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.biometrics.log.BiometricContextProvider.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    BiometricContextProvider.this.mDockState =
                            intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.log.BiometricContextProvider$2, reason: invalid class name */
    public final class AnonymousClass2 extends IBiometricContextListener.Stub {
        public AnonymousClass2() {}

        public final void onDisplayStateChanged(int i) {
            Slog.i(
                    "BiometricContextProvider",
                    "onDisplayStateChanged: "
                            .concat(
                                    i != 0
                                            ? i != 1
                                                    ? i != 2
                                                            ? i != 3
                                                                    ? i != 4
                                                                            ? ""
                                                                            : "DISPLAY_STATE_AOD"
                                                                    : "DISPLAY_STATE_SCREENSAVER"
                                                            : "DISPLAY_STATE_NO_UI"
                                                    : "DISPLAY_STATE_LOCKSCREEN"
                                            : "DISPLAY_STATE_UNKNOWN"));
            BiometricContextProvider biometricContextProvider = BiometricContextProvider.this;
            if (i != biometricContextProvider.mDisplayState) {
                biometricContextProvider.mDisplayState = i;
                Handler handler = biometricContextProvider.mHandler;
                if (handler != null) {
                    handler.post(
                            new BiometricContextProvider$$ExternalSyntheticLambda0(
                                    biometricContextProvider));
                } else {
                    ((ConcurrentHashMap) biometricContextProvider.mSubscribers)
                            .forEach(
                                    new BiometricContextProvider$$ExternalSyntheticLambda1(
                                            biometricContextProvider));
                }
            }
        }

        public final void onFoldChanged(int i) {
            BiometricContextProvider biometricContextProvider = BiometricContextProvider.this;
            if (biometricContextProvider.mFoldState != i) {
                biometricContextProvider.mFoldState = i;
                Handler handler = biometricContextProvider.mHandler;
                if (handler != null) {
                    handler.post(
                            new BiometricContextProvider$$ExternalSyntheticLambda0(
                                    biometricContextProvider));
                    return;
                }
                ((ConcurrentHashMap) biometricContextProvider.mSubscribers)
                        .forEach(
                                new BiometricContextProvider$$ExternalSyntheticLambda1(
                                        biometricContextProvider));
            }
        }

        public final void onHardwareIgnoreTouchesChanged(boolean z) {
            BiometricContextProvider biometricContextProvider = BiometricContextProvider.this;
            if (biometricContextProvider.mIsHardwareIgnoringTouches != z) {
                biometricContextProvider.mIsHardwareIgnoringTouches = z;
                Handler handler = biometricContextProvider.mHandler;
                if (handler != null) {
                    handler.post(
                            new BiometricContextProvider$$ExternalSyntheticLambda0(
                                    biometricContextProvider));
                    return;
                }
                ((ConcurrentHashMap) biometricContextProvider.mSubscribers)
                        .forEach(
                                new BiometricContextProvider$$ExternalSyntheticLambda1(
                                        biometricContextProvider));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.log.BiometricContextProvider$3, reason: invalid class name */
    public final class AnonymousClass3 extends ISessionListener.Stub {
        public AnonymousClass3() {}

        public final void onSessionEnded(int i, InstanceId instanceId) {
            if (Utils.DEBUG) {
                Slog.d("BiometricContextProvider", "onSessionEnded: " + i + ", " + instanceId);
            }
            BiometricContextSessionInfo biometricContextSessionInfo =
                    (BiometricContextSessionInfo)
                            ((ConcurrentHashMap) BiometricContextProvider.this.mSession)
                                    .remove(Integer.valueOf(i));
            if (biometricContextSessionInfo == null
                    || instanceId == null
                    || biometricContextSessionInfo.mId.getId() == instanceId.getId()) {
                return;
            }
            Slog.w("BiometricContextProvider", "session id mismatch");
        }

        public final void onSessionStarted(int i, InstanceId instanceId) {
            if (Utils.DEBUG) {
                Slog.d("BiometricContextProvider", "onSessionStarted: " + i + ", " + instanceId);
            }
            ((ConcurrentHashMap) BiometricContextProvider.this.mSession)
                    .put(Integer.valueOf(i), new BiometricContextSessionInfo(instanceId));
        }
    }

    public BiometricContextProvider(
            Context context,
            WindowManager windowManager,
            IStatusBarService iStatusBarService,
            Handler handler,
            AuthSessionCoordinator authSessionCoordinator) {
        this.mWindowManager = windowManager;
        this.mAuthSessionCoordinator = authSessionCoordinator;
        this.mHandler = handler;
        try {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2();
            this.mIBiometricContextListener = anonymousClass2;
            iStatusBarService.setBiometicContextListener(anonymousClass2);
            AnonymousClass3 anonymousClass3 = new AnonymousClass3();
            this.mISessionListener = anonymousClass3;
            iStatusBarService.registerSessionListener(3, anonymousClass3);
        } catch (RemoteException e) {
            Slog.e("BiometricContextProvider", "Unable to register biometric context listener", e);
        }
        try {
            context.registerReceiver(
                    this.mDockStateReceiver,
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "android.intent.action.DOCK_EVENT"));
        } catch (SecurityException e2) {
            e2.printStackTrace();
        }
    }

    public final boolean isAwake() {
        int i = this.mDisplayState;
        return i == 0 || i == 1 || i == 3;
    }

    public final void subscribe(
            OperationContextExt operationContextExt,
            Consumer consumer,
            Consumer consumer2,
            AuthenticateOptions authenticateOptions) {
        OperationContext operationContext;
        int i;
        Map map = this.mSubscribers;
        operationContextExt.update(this, operationContextExt.mAidlContext.isCrypto);
        ((ConcurrentHashMap) map).put(operationContextExt, consumer2);
        if (authenticateOptions == null) {
            consumer.accept(operationContextExt.mAidlContext);
            return;
        }
        int i2 = 0;
        if (authenticateOptions instanceof FaceAuthenticateOptions) {
            FaceAuthenticateOptions faceAuthenticateOptions =
                    (FaceAuthenticateOptions) authenticateOptions;
            OperationContext operationContext2 = operationContextExt.mAidlContext;
            switch (faceAuthenticateOptions.getAuthenticateReason()) {
                case 1:
                    i = 1;
                    break;
                case 2:
                    i = 2;
                    break;
                case 3:
                    i = 3;
                    break;
                case 4:
                    i = 4;
                    break;
                case 5:
                    i = 5;
                    break;
                case 6:
                    i = 6;
                    break;
                case 7:
                    i = 7;
                    break;
                case 8:
                    i = 8;
                    break;
                case 9:
                    i = 9;
                    break;
                case 10:
                    i = 10;
                    break;
                default:
                    i = 0;
                    break;
            }
            operationContext2.authenticateReason = AuthenticateReason.faceAuthenticateReason(i);
            OperationContext operationContext3 = operationContextExt.mAidlContext;
            int wakeReason = faceAuthenticateOptions.getWakeReason();
            if (wakeReason == 1) {
                i2 = 1;
            } else if (wakeReason == 4) {
                i2 = 2;
            } else if (wakeReason == 10) {
                i2 = 6;
            } else if (wakeReason == 6) {
                i2 = 3;
            } else if (wakeReason != 7) {
                switch (wakeReason) {
                    case 15:
                        i2 = 7;
                        break;
                    case 16:
                        i2 = 8;
                        break;
                    case 17:
                        i2 = 9;
                        break;
                }
            } else {
                i2 = 4;
            }
            operationContext3.wakeReason = i2;
            operationContext = operationContextExt.mAidlContext;
        } else {
            if (!(authenticateOptions instanceof FingerprintAuthenticateOptions)) {
                throw new IllegalStateException("Authenticate options are invalid.");
            }
            FingerprintAuthenticateOptions fingerprintAuthenticateOptions =
                    (FingerprintAuthenticateOptions) authenticateOptions;
            if (fingerprintAuthenticateOptions.getVendorReason() != null) {
                operationContextExt.mAidlContext.authenticateReason =
                        AuthenticateReason.vendorAuthenticateReason(
                                fingerprintAuthenticateOptions.getVendorReason());
            } else {
                operationContextExt.mAidlContext.authenticateReason =
                        AuthenticateReason.fingerprintAuthenticateReason(0);
            }
            operationContext = operationContextExt.mAidlContext;
            operationContext.wakeReason = 0;
        }
        consumer.accept(operationContext);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[keyguard session: ");
        sb.append((BiometricContextSessionInfo) ((ConcurrentHashMap) this.mSession).get(1));
        sb.append(", bp session: ");
        sb.append((BiometricContextSessionInfo) ((ConcurrentHashMap) this.mSession).get(2));
        sb.append(", displayState: ");
        sb.append(this.mDisplayState);
        sb.append(", isAwake: ");
        sb.append(isAwake());
        sb.append(", isHardwareIgnoring: ");
        sb.append(this.mIsHardwareIgnoringTouches);
        sb.append(", isDisplayOn: ");
        sb.append(this.mWindowManager.getDefaultDisplay().getState() == 2);
        sb.append(", dock: ");
        sb.append(this.mDockState);
        sb.append(", rotation: ");
        sb.append(this.mWindowManager.getDefaultDisplay().getRotation());
        sb.append(", foldState: ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mFoldState, sb, "]");
    }
}
