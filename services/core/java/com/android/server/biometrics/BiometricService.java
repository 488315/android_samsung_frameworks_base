package com.android.server.biometrics;

import android.app.ActivityManager;
import android.app.UserSwitchObserver;
import android.app.admin.DevicePolicyManager;
import android.app.trust.ITrustManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricStateListener;
import android.hardware.biometrics.IBiometricAuthenticator;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.security.GateKeeper;
import android.security.KeyStoreAuthorization;
import android.service.gatekeeper.IGateKeeperService;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.logging.InstanceId;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.SomeArgs;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.biometrics.AuthSession;
import com.android.server.biometrics.BiometricService;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBiometricDisplayStateMonitor;
import com.android.server.biometrics.SemBiometricSysUiManager;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.log.BiometricFrameworkStatsLogger;
import com.android.server.biometrics.log.OperationContextExt;
import com.android.server.utils.Slogf;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BiometricService extends SystemService {
    AuthSession mAuthSession;
    public final BiometricCameraManagerImpl mBiometricCameraManager;
    public final BiometricContextProvider mBiometricContext;
    public final BiometricNotificationLogger mBiometricNotificationLogger;
    BiometricStrengthController mBiometricStrengthController;
    public final DevicePolicyManager mDevicePolicyManager;
    public final List mEnabledOnKeyguardCallbacks;
    IGateKeeperService mGateKeeper;
    public final Handler mHandler;
    final IBiometricService.Stub mImpl;
    public final Injector mInjector;
    KeyStoreAuthorization mKeyStoreAuthorization;
    public final Random mRandom;
    public final BiometricService$Injector$$ExternalSyntheticLambda0 mRequestCounter;
    public final ArrayList mSensors;
    final SettingObserver mSettingObserver;
    SemBiometricSysUiWrapper mStatusBarService;
    ITrustManager mTrustManager;
    public final UserManager mUserManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.BiometricService$1, reason: invalid class name */
    public final class AnonymousClass1 extends IBiometricSensorReceiver.Stub {
        public final /* synthetic */ long val$requestId;

        public AnonymousClass1(long j) {
            this.val$requestId = j;
        }

        public final void onAcquired(final int i, final int i2, final int i3) {
            Handler handler = BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new Runnable() { // from class: com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricService.AnonymousClass1 anonymousClass1 = BiometricService.AnonymousClass1.this;
                    long j2 = j;
                    int i4 = i;
                    final int i5 = i2;
                    final int i6 = i3;
                    AuthSession authSessionIfCurrent = BiometricService.this.getAuthSessionIfCurrent(j2);
                    if (authSessionIfCurrent == null) {
                        Slog.w("BiometricService", "onAcquired: AuthSession is not current");
                        return;
                    }
                    if (authSessionIfCurrent.hasAuthenticatedAndConfirmed()) {
                        Slog.d("BiometricService/AuthSession", "onAcquired after successful auth");
                        return;
                    }
                    int sensorIdToModality = authSessionIfCurrent.sensorIdToModality(i4);
                    String authHelpMessage = sensorIdToModality != 2 ? sensorIdToModality != 8 ? null : FaceManager.getAuthHelpMessage(authSessionIfCurrent.mContext, i5, i6) : FingerprintManager.getAcquiredString(authSessionIfCurrent.mContext, i5, i6);
                    StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i4, i5, "sensorId: ", " acquiredInfo: ", " vendor: ");
                    m.append(i6);
                    m.append(" message: ");
                    m.append(authHelpMessage);
                    Slog.d("BiometricService/AuthSession", m.toString());
                    if (authHelpMessage == null || TextUtils.isEmpty(authHelpMessage)) {
                        return;
                    }
                    try {
                        final SemBiometricSysUiWrapper semBiometricSysUiWrapper = authSessionIfCurrent.mStatusBarService;
                        final int sensorIdToModality2 = authSessionIfCurrent.sensorIdToModality(i4);
                        final String str = authHelpMessage;
                        ((HashMap) semBiometricSysUiWrapper.mSessions).forEach(new BiConsumer() { // from class: com.android.server.biometrics.SemBiometricSysUiWrapper$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                SemBiometricSysUiWrapper semBiometricSysUiWrapper2 = SemBiometricSysUiWrapper.this;
                                int i7 = sensorIdToModality2;
                                int i8 = i5;
                                int i9 = i6;
                                String str2 = str;
                                semBiometricSysUiWrapper2.getClass();
                                int intValue = ((Integer) ((Pair) obj2).first).intValue();
                                SemBiometricSysUiManager semBiometricSysUiManager = semBiometricSysUiWrapper2.mSysUiManager;
                                semBiometricSysUiManager.getClass();
                                semBiometricSysUiManager.handleRequest(new SemBiometricSysUiManager$$ExternalSyntheticLambda11(semBiometricSysUiManager, intValue, i7, i8, i9, str2));
                            }
                        });
                        if (i5 == 6) {
                            i5 = i6;
                        }
                        authSessionIfCurrent.mClientReceiver.onAcquired(i5, authHelpMessage);
                    } catch (RemoteException e) {
                        Slog.e("BiometricService/AuthSession", "Remote exception", e);
                    }
                }
            });
        }

        public final void onAuthenticationFailed(int i) {
            Slog.v("BiometricService", "onAuthenticationFailed");
            BiometricService.this.mHandler.post(new BiometricService$2$$ExternalSyntheticLambda0(this, this.val$requestId, i));
        }

        public final void onAuthenticationSucceeded(int i, byte[] bArr) {
            BiometricService.this.mHandler.post(new BiometricService$1$$ExternalSyntheticLambda0(this, this.val$requestId, i, bArr));
        }

        public final void onError(final int i, final int i2, final int i3, final int i4) {
            if (i3 == 3) {
                Handler handler = BiometricService.this.mHandler;
                final long j = this.val$requestId;
                final int i5 = 1;
                handler.post(new Runnable(this) { // from class: com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda3
                    public final /* synthetic */ BiometricService.AnonymousClass1 f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i5) {
                            case 0:
                                BiometricService.AnonymousClass1 anonymousClass1 = this.f$0;
                                long j2 = j;
                                int i6 = i;
                                int i7 = i2;
                                int i8 = i3;
                                int i9 = i4;
                                BiometricService biometricService = BiometricService.this;
                                biometricService.getClass();
                                Slog.d("BiometricService", "handleOnError() sensorId: " + i6 + ", cookie: " + i7 + ", error: " + i8 + ", vendorCode: " + i9);
                                AuthSession authSessionIfCurrent = biometricService.getAuthSessionIfCurrent(j2);
                                if (authSessionIfCurrent == null) {
                                    Slog.w("BiometricService", "handleOnError: AuthSession is not current");
                                    break;
                                } else {
                                    try {
                                        if (authSessionIfCurrent.onErrorReceived(i6, i7, i8, i9)) {
                                            Slog.d("BiometricService", "handleOnError: AuthSession finished");
                                            AuthSession authSession = biometricService.mAuthSession;
                                            if (authSession != null) {
                                                authSession.destroy();
                                            }
                                            biometricService.mAuthSession = null;
                                            break;
                                        }
                                    } catch (RemoteException e) {
                                        Slog.e("BiometricService", "RemoteException", e);
                                        return;
                                    }
                                }
                                break;
                            default:
                                BiometricService.AnonymousClass1 anonymousClass12 = this.f$0;
                                long j3 = j;
                                int i10 = i;
                                int i11 = i2;
                                int i12 = i3;
                                int i13 = i4;
                                BiometricService biometricService2 = BiometricService.this;
                                biometricService2.getClass();
                                Slog.v("BiometricService", "handleAuthenticationTimedOut(), sensorId: " + i10 + ", cookie: " + i11 + ", error: " + i12 + ", vendorCode: " + i13);
                                AuthSession authSessionIfCurrent2 = biometricService2.getAuthSessionIfCurrent(j3);
                                if (authSessionIfCurrent2 == null) {
                                    Slog.w("BiometricService", "handleAuthenticationTimedOut: AuthSession is not current");
                                    break;
                                } else if (authSessionIfCurrent2.hasAuthenticatedAndConfirmed()) {
                                    Slog.d("BiometricService/AuthSession", "onAuthenticationTimedOut after successful auth");
                                    break;
                                } else {
                                    try {
                                        authSessionIfCurrent2.mStatusBarService.onBiometricError(authSessionIfCurrent2.sensorIdToModality(i10), i12, i13);
                                        authSessionIfCurrent2.pauseSensorIfSupported(i10);
                                        authSessionIfCurrent2.mState = 4;
                                        break;
                                    } catch (RemoteException e2) {
                                        Slog.e("BiometricService/AuthSession", "RemoteException", e2);
                                    }
                                }
                        }
                    }
                });
                return;
            }
            Handler handler2 = BiometricService.this.mHandler;
            final long j2 = this.val$requestId;
            final int i6 = 0;
            handler2.post(new Runnable(this) { // from class: com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda3
                public final /* synthetic */ BiometricService.AnonymousClass1 f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i6) {
                        case 0:
                            BiometricService.AnonymousClass1 anonymousClass1 = this.f$0;
                            long j22 = j2;
                            int i62 = i;
                            int i7 = i2;
                            int i8 = i3;
                            int i9 = i4;
                            BiometricService biometricService = BiometricService.this;
                            biometricService.getClass();
                            Slog.d("BiometricService", "handleOnError() sensorId: " + i62 + ", cookie: " + i7 + ", error: " + i8 + ", vendorCode: " + i9);
                            AuthSession authSessionIfCurrent = biometricService.getAuthSessionIfCurrent(j22);
                            if (authSessionIfCurrent == null) {
                                Slog.w("BiometricService", "handleOnError: AuthSession is not current");
                                break;
                            } else {
                                try {
                                    if (authSessionIfCurrent.onErrorReceived(i62, i7, i8, i9)) {
                                        Slog.d("BiometricService", "handleOnError: AuthSession finished");
                                        AuthSession authSession = biometricService.mAuthSession;
                                        if (authSession != null) {
                                            authSession.destroy();
                                        }
                                        biometricService.mAuthSession = null;
                                        break;
                                    }
                                } catch (RemoteException e) {
                                    Slog.e("BiometricService", "RemoteException", e);
                                    return;
                                }
                            }
                            break;
                        default:
                            BiometricService.AnonymousClass1 anonymousClass12 = this.f$0;
                            long j3 = j2;
                            int i10 = i;
                            int i11 = i2;
                            int i12 = i3;
                            int i13 = i4;
                            BiometricService biometricService2 = BiometricService.this;
                            biometricService2.getClass();
                            Slog.v("BiometricService", "handleAuthenticationTimedOut(), sensorId: " + i10 + ", cookie: " + i11 + ", error: " + i12 + ", vendorCode: " + i13);
                            AuthSession authSessionIfCurrent2 = biometricService2.getAuthSessionIfCurrent(j3);
                            if (authSessionIfCurrent2 == null) {
                                Slog.w("BiometricService", "handleAuthenticationTimedOut: AuthSession is not current");
                                break;
                            } else if (authSessionIfCurrent2.hasAuthenticatedAndConfirmed()) {
                                Slog.d("BiometricService/AuthSession", "onAuthenticationTimedOut after successful auth");
                                break;
                            } else {
                                try {
                                    authSessionIfCurrent2.mStatusBarService.onBiometricError(authSessionIfCurrent2.sensorIdToModality(i10), i12, i13);
                                    authSessionIfCurrent2.pauseSensorIfSupported(i10);
                                    authSessionIfCurrent2.mState = 4;
                                    break;
                                } catch (RemoteException e2) {
                                    Slog.e("BiometricService/AuthSession", "RemoteException", e2);
                                }
                            }
                    }
                }
            });
        }

        public final void onSemAuthenticationSucceeded(final int i, final byte[] bArr, final Bundle bundle) {
            Handler handler = BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new Runnable() { // from class: com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricService.AnonymousClass1 anonymousClass1 = BiometricService.AnonymousClass1.this;
                    BiometricService.this.handleAuthenticationSucceeded(j, i, bArr, bundle);
                }
            });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.BiometricService$2, reason: invalid class name */
    public final class AnonymousClass2 implements IBiometricSysuiReceiver {
        public final SemBiometricSysUiReceiver$1 mSysUiListener = new SemBiometricSysUiManager.SysUiListener() { // from class: com.android.server.biometrics.SemBiometricSysUiReceiver$1
            @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
            public final void onDismissed(int i, byte[] bArr) {
                BiometricService.AnonymousClass2.this.onDialogDismissed(i, bArr);
            }

            @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
            public final void onError(int i, int i2) {
                BiometricService.AnonymousClass2 anonymousClass2 = BiometricService.AnonymousClass2.this;
                BiometricService.this.mHandler.post(new BiometricService$2$$ExternalSyntheticLambda4(anonymousClass2, anonymousClass2.val$requestId, i, i2));
            }

            @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
            public final void onEvent(int i, int i2) {
                BiometricService.AnonymousClass2 anonymousClass2 = BiometricService.AnonymousClass2.this;
                switch (i) {
                    case 1002:
                        anonymousClass2.onDeviceCredentialPressed();
                        break;
                    case 1003:
                        anonymousClass2.onSystemEvent(i2);
                        break;
                    case 1004:
                        anonymousClass2.onDialogAnimatedIn(true);
                        break;
                }
            }

            @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
            public final void onTryAgainPressed(int i) {
                BiometricService.AnonymousClass2 anonymousClass2 = BiometricService.AnonymousClass2.this;
                if (i == 0) {
                    anonymousClass2.onTryAgainPressed();
                } else {
                    BiometricService.this.mHandler.post(new BiometricService$2$$ExternalSyntheticLambda7(anonymousClass2, i));
                }
            }
        };
        public final /* synthetic */ long val$requestId;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.biometrics.SemBiometricSysUiReceiver$1] */
        public AnonymousClass2(long j) {
            this.val$requestId = j;
        }

        public final IBinder asBinder() {
            return null;
        }

        public final void onDeviceCredentialPressed() {
            BiometricService.this.mHandler.post(new BiometricService$2$$ExternalSyntheticLambda6(this, this.val$requestId, 2));
        }

        public final void onDialogAnimatedIn(final boolean z) {
            SemBiometricSysUiWrapper semBiometricSysUiWrapper = BiometricService.this.mStatusBarService;
            synchronized (semBiometricSysUiWrapper) {
                semBiometricSysUiWrapper.mHandler.removeCallbacks(semBiometricSysUiWrapper.mShowDialogWatchdog);
                BiometricContextProvider.AnonymousClass3 anonymousClass3 = ((BiometricContextProvider) semBiometricSysUiWrapper.mBiometricContext).mISessionListener;
                if (anonymousClass3 != null) {
                    if (((HashMap) semBiometricSysUiWrapper.mSessionToInstanceId).getOrDefault(2, null) != null) {
                        Slog.e("SemBiometricSysUiWrapper", "session [BIOMETRIC_PROMPT] was already started");
                    } else {
                        InstanceId newInstanceId = semBiometricSysUiWrapper.mInstanceIdGenerator.newInstanceId();
                        ((HashMap) semBiometricSysUiWrapper.mSessionToInstanceId).put(2, newInstanceId);
                        try {
                            if (Utils.DEBUG) {
                                Slog.d("SemBiometricSysUiWrapper", "Session start for [BIOMETRIC_PROMPT] id=" + newInstanceId);
                            }
                            anonymousClass3.onSessionStarted(2, newInstanceId);
                        } catch (RemoteException e) {
                            Slog.e("SemBiometricSysUiWrapper", "Unable to send onSessionStarted for session=[BIOMETRIC_PROMPT]", e);
                        }
                    }
                }
            }
            Handler handler = BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new Runnable() { // from class: com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricService.AnonymousClass2 anonymousClass2 = BiometricService.AnonymousClass2.this;
                    long j2 = j;
                    boolean z2 = z;
                    BiometricService biometricService = BiometricService.this;
                    biometricService.getClass();
                    Slog.d("BiometricService", "handleOnDialogAnimatedIn");
                    AuthSession authSessionIfCurrent = biometricService.getAuthSessionIfCurrent(j2);
                    if (authSessionIfCurrent == null) {
                        Slog.w("BiometricService", "handleOnDialogAnimatedIn: AuthSession is not current");
                        return;
                    }
                    int i = authSessionIfCurrent.mState;
                    if (i != 2 && i != 8 && i != 4 && i != 6) {
                        VaultKeeperService$$ExternalSyntheticOutline0.m(new StringBuilder("onDialogAnimatedIn, unexpected state: "), authSessionIfCurrent.mState, "BiometricService/AuthSession");
                        return;
                    }
                    if (i != 6) {
                        authSessionIfCurrent.mState = 3;
                    }
                    if (z2) {
                        authSessionIfCurrent.startAllPreparedSensors(new AuthSession$$ExternalSyntheticLambda1(4));
                    } else {
                        Slog.d("BiometricService/AuthSession", "delaying fingerprint sensor start");
                    }
                    BiometricContext biometricContext = authSessionIfCurrent.mBiometricContext;
                    OperationContextExt operationContextExt = authSessionIfCurrent.mOperationContext;
                    boolean isCrypto = authSessionIfCurrent.isCrypto();
                    BiometricContextProvider biometricContextProvider = (BiometricContextProvider) biometricContext;
                    biometricContextProvider.getClass();
                    operationContextExt.update(biometricContextProvider, isCrypto);
                }
            });
        }

        public final void onDialogDismissed(int i, byte[] bArr) {
            SemBiometricSysUiWrapper semBiometricSysUiWrapper = BiometricService.this.mStatusBarService;
            synchronized (semBiometricSysUiWrapper) {
                BiometricContextProvider.AnonymousClass3 anonymousClass3 = ((BiometricContextProvider) semBiometricSysUiWrapper.mBiometricContext).mISessionListener;
                if (anonymousClass3 != null) {
                    if (((HashMap) semBiometricSysUiWrapper.mSessionToInstanceId).getOrDefault(2, null) == null) {
                        Slog.e("SemBiometricSysUiWrapper", "session [BIOMETRIC_PROMPT] was not started");
                    } else {
                        InstanceId instanceId = (InstanceId) ((HashMap) semBiometricSysUiWrapper.mSessionToInstanceId).get(2);
                        ((HashMap) semBiometricSysUiWrapper.mSessionToInstanceId).put(2, null);
                        try {
                            if (Utils.DEBUG) {
                                Slog.d("SemBiometricSysUiWrapper", "Session end for [BIOMETRIC_PROMPT] id=" + instanceId);
                            }
                            anonymousClass3.onSessionEnded(2, instanceId);
                        } catch (RemoteException e) {
                            Slog.e("SemBiometricSysUiWrapper", "Unable to send onSessionEnded for session=[BIOMETRIC_PROMPT]", e);
                        }
                    }
                }
            }
            BiometricService.this.mHandler.post(new BiometricService$1$$ExternalSyntheticLambda0(this, this.val$requestId, i, bArr));
        }

        public final void onStartFingerprintNow() {
            BiometricService.this.mHandler.post(new BiometricService$2$$ExternalSyntheticLambda6(this, this.val$requestId, 1));
        }

        public final void onSystemEvent(int i) {
            BiometricService.this.mHandler.post(new BiometricService$2$$ExternalSyntheticLambda0(this, this.val$requestId, i));
        }

        public final void onTryAgainPressed() {
            BiometricService.this.mHandler.post(new BiometricService$2$$ExternalSyntheticLambda6(this, this.val$requestId, 0));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BiometricServiceWrapper extends IBiometricService.Stub {

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.biometrics.BiometricService$BiometricServiceWrapper$1, reason: invalid class name */
        public final class AnonymousClass1 extends BiometricSensor {
            public AnonymousClass1(int i, int i2, int i3, IBiometricAuthenticator iBiometricAuthenticator) {
                super(i, i2, i3, iBiometricAuthenticator);
            }
        }

        public BiometricServiceWrapper() {
        }

        public final long authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo) {
            authenticate_enforcePermission();
            if (iBinder == null || iBiometricServiceReceiver == null || str == null || promptInfo == null) {
                Slog.e("BiometricService", "Unable to authenticate, one or more null arguments");
                return -1L;
            }
            Context context = BiometricService.this.getContext();
            boolean z = Utils.DEBUG;
            if (!Utils.isValidAuthenticatorConfig(context, promptInfo.getAuthenticators())) {
                throw new SecurityException("Invalid authenticator configuration");
            }
            boolean isDeviceCredentialAllowed = promptInfo.isDeviceCredentialAllowed();
            promptInfo.setDeviceCredentialAllowed(false);
            promptInfo.setAuthenticators(promptInfo.getAuthenticators() != 0 ? promptInfo.getAuthenticators() : isDeviceCredentialAllowed ? 33023 : IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            long longValue = ((Long) BiometricService.this.mRequestCounter.get()).longValue();
            BiometricService.this.mHandler.post(new BiometricService$$ExternalSyntheticLambda2(this, iBinder, longValue, j, i, iBiometricServiceReceiver, str, promptInfo));
            return longValue;
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x0078, code lost:
        
            if (com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags.mandatoryBiometrics() != false) goto L28;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int canAuthenticate(java.lang.String r4, int r5, int r6, int r7) {
            /*
                r3 = this;
                r3.canAuthenticate_enforcePermission()
                java.lang.String r0 = "canAuthenticate: User="
                java.lang.String r1 = ", Caller="
                java.lang.String r2 = ", Authenticators="
                java.lang.StringBuilder r6 = com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0.m(r5, r6, r0, r1, r2)
                java.lang.String r0 = "BiometricService"
                com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r6, r7, r0)
                com.android.server.biometrics.BiometricService r6 = com.android.server.biometrics.BiometricService.this
                android.content.Context r6 = r6.getContext()
                boolean r6 = com.android.server.biometrics.Utils.isValidAuthenticatorConfig(r6, r7)
                if (r6 == 0) goto L83
                r6 = 1
                com.android.server.biometrics.BiometricService r3 = com.android.server.biometrics.BiometricService.this     // Catch: android.os.RemoteException -> L7c
                com.android.server.biometrics.PreAuthInfo r3 = com.android.server.biometrics.BiometricService.m312$$Nest$mcreatePreAuthInfo(r3, r4, r5, r7)     // Catch: android.os.RemoteException -> L7c
                android.util.Pair r3 = r3.getInternalStatus()     // Catch: android.os.RemoteException -> L7c
                java.lang.Object r3 = r3.second     // Catch: android.os.RemoteException -> L7c
                java.lang.Integer r3 = (java.lang.Integer) r3     // Catch: android.os.RemoteException -> L7c
                int r3 = r3.intValue()     // Catch: android.os.RemoteException -> L7c
                int r3 = com.android.server.biometrics.Utils.authenticatorStatusToBiometricConstant(r3)     // Catch: android.os.RemoteException -> L7c
                r4 = 0
                if (r3 == 0) goto L70
                if (r3 == r6) goto L7b
                r5 = 7
                if (r3 == r5) goto L74
                r7 = 9
                if (r3 == r7) goto L74
                r4 = 18
                if (r3 == r4) goto L7b
                r4 = 11
                if (r3 == r4) goto L70
                r5 = 12
                if (r3 == r5) goto L72
                r5 = 14
                if (r3 == r5) goto L70
                r4 = 15
                if (r3 == r4) goto L70
                r4 = 20
                if (r3 == r4) goto L70
                r4 = 21
                if (r3 == r4) goto L70
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L7c
                java.lang.String r5 = "Unhandled result code: "
                r4.<init>(r5)     // Catch: android.os.RemoteException -> L7c
                r4.append(r3)     // Catch: android.os.RemoteException -> L7c
                java.lang.String r3 = r4.toString()     // Catch: android.os.RemoteException -> L7c
                android.util.Slog.e(r0, r3)     // Catch: android.os.RemoteException -> L7c
                goto L7b
            L70:
                r6 = r4
                goto L7b
            L72:
                r6 = r5
                goto L7b
            L74:
                boolean r3 = com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags.mandatoryBiometrics()     // Catch: android.os.RemoteException -> L7c
                if (r3 == 0) goto L70
                goto L72
            L7b:
                return r6
            L7c:
                r3 = move-exception
                java.lang.String r4 = "Remote exception"
                android.util.Slog.e(r0, r4, r3)
                return r6
            L83:
                java.lang.SecurityException r3 = new java.lang.SecurityException
                java.lang.String r4 = "Invalid authenticator configuration"
                r3.<init>(r4)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.BiometricService.BiometricServiceWrapper.canAuthenticate(java.lang.String, int, int, int):int");
        }

        public final void cancelAuthentication(IBinder iBinder, String str, long j) {
            cancelAuthentication_enforcePermission();
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = iBinder;
            obtain.arg2 = str;
            obtain.arg3 = Long.valueOf(j);
            BiometricService.this.mHandler.post(new BiometricService$$ExternalSyntheticLambda4(1, j, this));
        }

        public final ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) {
            createTestSession_enforcePermission();
            Iterator it = BiometricService.this.mSensors.iterator();
            while (it.hasNext()) {
                BiometricSensor biometricSensor = (BiometricSensor) it.next();
                if (biometricSensor.id == i) {
                    return biometricSensor.impl.createTestSession(iTestSessionCallback, str);
                }
            }
            NandswapManager$$ExternalSyntheticOutline0.m(i, "Unknown sensor for createTestSession: ", "BiometricService");
            return null;
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(BiometricService.this.getContext(), "BiometricService", printWriter)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                    } catch (RemoteException e) {
                        Slog.e("BiometricService", "Remote exception", e);
                    }
                    if (strArr.length > 0) {
                        if ("--proto".equals(strArr[0])) {
                            boolean z = true;
                            if (strArr.length <= 1 || !"--clear-scheduler-buffer".equals(strArr[1])) {
                                z = false;
                            }
                            Slog.d("BiometricService", "ClearSchedulerBuffer: " + z);
                            ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
                            AuthSession authSession = BiometricService.this.mAuthSession;
                            protoOutputStream.write(1159641169922L, authSession != null ? authSession.mState : 0);
                            Iterator it = BiometricService.this.mSensors.iterator();
                            while (it.hasNext()) {
                                protoOutputStream.write(2246267895809L, ((BiometricSensor) it.next()).impl.dumpSensorServiceStateProto(z));
                            }
                            protoOutputStream.flush();
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    BiometricService.m313$$Nest$mdumpInternal(BiometricService.this, printWriter);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        public final long[] getAuthenticatorIds(int i) {
            getAuthenticatorIds_enforcePermission();
            ArrayList arrayList = new ArrayList();
            Iterator it = BiometricService.this.mSensors.iterator();
            while (it.hasNext()) {
                BiometricSensor biometricSensor = (BiometricSensor) it.next();
                try {
                    boolean hasEnrolledTemplates = biometricSensor.impl.hasEnrolledTemplates(i, BiometricService.this.getContext().getOpPackageName());
                    long authenticatorId = biometricSensor.impl.getAuthenticatorId(i);
                    if (hasEnrolledTemplates && Utils.isAtLeastStrength(biometricSensor.getCurrentStrength(), 15)) {
                        arrayList.add(Long.valueOf(authenticatorId));
                    } else {
                        Slog.d("BiometricService", "Sensor " + biometricSensor + ", sensorId " + biometricSensor.id + ", hasEnrollments: " + hasEnrolledTemplates + " cannot participate in Keystore operations");
                    }
                } catch (RemoteException e) {
                    Slog.e("BiometricService", "RemoteException", e);
                }
            }
            long[] jArr = new long[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                jArr[i2] = ((Long) arrayList.get(i2)).longValue();
            }
            return jArr;
        }

        public final int getCurrentModality(String str, int i, int i2, int i3) {
            getCurrentModality_enforcePermission();
            DeviceIdleController$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "getCurrentModality: User=", ", Caller=", ", Authenticators="), i3, "BiometricService");
            if (!Utils.isValidAuthenticatorConfig(BiometricService.this.getContext(), i3)) {
                throw new SecurityException("Invalid authenticator configuration");
            }
            try {
                return ((Integer) BiometricService.m312$$Nest$mcreatePreAuthInfo(BiometricService.this, str, i, i3).getPreAuthenticateStatus().first).intValue();
            } catch (RemoteException e) {
                Slog.e("BiometricService", "Remote exception", e);
                return 0;
            }
        }

        public final int getCurrentStrength(int i) {
            getCurrentStrength_enforcePermission();
            Iterator it = BiometricService.this.mSensors.iterator();
            while (it.hasNext()) {
                BiometricSensor biometricSensor = (BiometricSensor) it.next();
                if (biometricSensor.id == i) {
                    return biometricSensor.getCurrentStrength();
                }
            }
            NandswapManager$$ExternalSyntheticOutline0.m(i, "Unknown sensorId: ", "BiometricService");
            return 0;
        }

        public final long getLastAuthenticationTime(int i, int i2) {
            getLastAuthenticationTime_enforcePermission();
            if (!com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags.lastAuthenticationTime()) {
                throw new UnsupportedOperationException();
            }
            Slogf.d("BiometricService", "getLastAuthenticationTime(userId=%d, authenticators=0x%x)", Integer.valueOf(i), Integer.valueOf(i2));
            try {
                long secureUserId = BiometricService.this.mGateKeeper.getSecureUserId(i);
                if (secureUserId == 0) {
                    Slogf.w("BiometricService", "No secure user id for " + i);
                    return -1L;
                }
                ArrayList arrayList = new ArrayList(2);
                if ((32768 & i2) != 0) {
                    arrayList.add(1);
                }
                if ((i2 & 15) != 0) {
                    arrayList.add(2);
                }
                if (arrayList.isEmpty()) {
                    throw new IllegalArgumentException("authenticators must not be empty");
                }
                return BiometricService.this.mKeyStoreAuthorization.getLastAuthTime(secureUserId, arrayList.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray());
            } catch (RemoteException e) {
                Slogf.w("BiometricService", "Failed to get secure user id for " + i, e);
                return -1L;
            }
        }

        public final List getSensorProperties(String str) {
            getSensorProperties_enforcePermission();
            ArrayList arrayList = new ArrayList();
            Iterator it = BiometricService.this.mSensors.iterator();
            while (it.hasNext()) {
                arrayList.add(SensorPropertiesInternal.from(((BiometricSensor) it.next()).impl.getSensorProperties(str)));
            }
            return arrayList;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v4, types: [boolean] */
        /* JADX WARN: Type inference failed for: r0v6 */
        /* JADX WARN: Type inference failed for: r0v7 */
        /* JADX WARN: Type inference failed for: r0v8 */
        public final int getSupportedModalities(int i) {
            getSupportedModalities_enforcePermission();
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "getSupportedModalities: Authenticators=", "BiometricService");
            if (!Utils.isValidAuthenticatorConfig(BiometricService.this.getContext(), i)) {
                throw new SecurityException("Invalid authenticator configuration");
            }
            int isCredentialRequested = Utils.isCredentialRequested(i);
            int i2 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
            if (i2 != 0) {
                Iterator it = BiometricService.this.mSensors.iterator();
                while (it.hasNext()) {
                    BiometricSensor biometricSensor = (BiometricSensor) it.next();
                    if (Utils.isAtLeastStrength(biometricSensor.getCurrentStrength(), i2)) {
                        isCredentialRequested = (isCredentialRequested == true ? 1 : 0) | biometricSensor.modality;
                    }
                }
            }
            return isCredentialRequested;
        }

        public final boolean hasEnrolledBiometrics(int i, String str) {
            hasEnrolledBiometrics_enforcePermission();
            try {
                Iterator it = BiometricService.this.mSensors.iterator();
                while (it.hasNext()) {
                    if (((BiometricSensor) it.next()).impl.hasEnrolledTemplates(i, str)) {
                        return true;
                    }
                }
                return false;
            } catch (RemoteException e) {
                Slog.e("BiometricService", "Remote exception", e);
                return false;
            }
        }

        public final void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) {
            invalidateAuthenticatorIds_enforcePermission();
            new InvalidationTracker(BiometricService.this.getContext(), BiometricService.this.mSensors, i, i2, iInvalidationCallback);
        }

        public final void onReadyForAuthentication(long j, int i) {
            onReadyForAuthentication_enforcePermission();
            BiometricService.this.mHandler.post(new BiometricService$2$$ExternalSyntheticLambda0(this, j, i));
        }

        public final synchronized void registerAuthenticator(int i, int i2, int i3, IBiometricAuthenticator iBiometricAuthenticator) {
            try {
                registerAuthenticator_enforcePermission();
                Slog.d("BiometricService", "Registering ID: " + i + " Modality: " + i2 + " Strength: " + i3);
                if (iBiometricAuthenticator == null) {
                    throw new IllegalArgumentException("Authenticator must not be null. Did you forget to modify the core/res/res/values/xml overlay for config_biometric_sensors?");
                }
                if (i3 != 15 && i3 != 255 && i3 != 4095) {
                    throw new IllegalStateException("Unsupported strength");
                }
                Iterator it = BiometricService.this.mSensors.iterator();
                while (it.hasNext()) {
                    if (((BiometricSensor) it.next()).id == i) {
                        throw new IllegalStateException("Cannot register duplicate authenticator");
                    }
                }
                BiometricService biometricService = BiometricService.this;
                ArrayList arrayList = biometricService.mSensors;
                biometricService.getContext();
                arrayList.add(new AnonymousClass1(i, i2, i3, iBiometricAuthenticator));
                BiometricService.this.mBiometricStrengthController.updateStrengths();
            } catch (Throwable th) {
                throw th;
            }
        }

        public final void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
            registerEnabledOnKeyguardCallback_enforcePermission();
            BiometricService biometricService = BiometricService.this;
            ((ArrayList) biometricService.mEnabledOnKeyguardCallbacks).add(biometricService.new EnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallback));
            try {
                Iterator it = BiometricService.this.mUserManager.getAliveUsers().iterator();
                while (it.hasNext()) {
                    int i = ((UserInfo) it.next()).id;
                    iBiometricEnabledOnKeyguardCallback.onChanged(BiometricService.this.mSettingObserver.getEnabledOnKeyguard(i), i);
                }
            } catch (RemoteException e) {
                Slog.w("BiometricService", "Remote exception", e);
            }
        }

        public final void resetLockout(int i, byte[] bArr) {
            resetLockout_enforcePermission();
            DeviceIdleController$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i, "resetLockout(userId=", ", hat="), bArr == null ? "null " : "present", ")", "BiometricService");
            BiometricService.this.mHandler.post(new BiometricService$2$$ExternalSyntheticLambda7(this, i));
        }

        public final void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) {
            BiometricSensor biometricSensor;
            BiometricSensor biometricSensor2;
            resetLockoutTimeBound_enforcePermission();
            DeviceIdleController$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i, "resetLockoutTimeBound: ", str, ", ", ", "), i2, "BiometricService");
            Iterator it = BiometricService.this.mSensors.iterator();
            while (true) {
                biometricSensor = null;
                if (!it.hasNext()) {
                    biometricSensor2 = null;
                    break;
                } else {
                    biometricSensor2 = (BiometricSensor) it.next();
                    if (biometricSensor2.id == i) {
                        break;
                    }
                }
            }
            if (biometricSensor2 == null) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i, "Sensor: ", " is invalid id", "BiometricService");
                return;
            }
            Iterator it2 = BiometricService.this.mSensors.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                BiometricSensor biometricSensor3 = (BiometricSensor) it2.next();
                if (biometricSensor3.id == i) {
                    biometricSensor = biometricSensor3;
                    break;
                }
            }
            if (!Utils.isAtLeastStrength(biometricSensor.getCurrentStrength(), 15)) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i, "Sensor: ", " is does not meet the required strength to request resetLockout", "BiometricService");
                return;
            }
            Iterator it3 = BiometricService.this.mSensors.iterator();
            while (it3.hasNext()) {
                BiometricSensor biometricSensor4 = (BiometricSensor) it3.next();
                if (biometricSensor4.id != i) {
                    try {
                        SensorPropertiesInternal sensorProperties = biometricSensor4.impl.getSensorProperties(BiometricService.this.getContext().getOpPackageName());
                        boolean z = sensorProperties.resetLockoutRequiresHardwareAuthToken;
                        boolean z2 = z && !sensorProperties.resetLockoutRequiresChallenge;
                        boolean z3 = !z;
                        if (z2 || z3) {
                            Slog.d("BiometricService", "resetLockout from: " + i + ", for: " + biometricSensor4.id + ", userId: " + i2);
                            biometricSensor4.impl.resetLockout(iBinder, str, i2, bArr);
                        }
                    } catch (RemoteException e) {
                        Slog.e("BiometricService", "Remote exception", e);
                    }
                }
            }
        }

        public final PromptInfo semGetPromptInfo(int i) {
            semGetPromptInfo_enforcePermission();
            AuthSession authSession = BiometricService.this.mAuthSession;
            if (authSession == null) {
                return null;
            }
            Iterator it = authSession.mPreAuthInfo.eligibleSensors.iterator();
            while (it.hasNext()) {
                if (((BiometricSensor) it.next()).mCookie == i) {
                    return authSession.mPromptInfo;
                }
            }
            return null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EnabledOnKeyguardCallback implements IBinder.DeathRecipient {
        public final IBiometricEnabledOnKeyguardCallback mCallback;

        public EnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
            this.mCallback = iBiometricEnabledOnKeyguardCallback;
            try {
                iBiometricEnabledOnKeyguardCallback.asBinder().linkToDeath(this, 0);
            } catch (RemoteException e) {
                Slog.w("BiometricService", "Unable to linkToDeath", e);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Slog.e("BiometricService", "Enabled callback binder died");
            ((ArrayList) BiometricService.this.mEnabledOnKeyguardCallbacks).remove(this);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Injector {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class InvalidationTracker {
        public final IInvalidationCallback mClientCallback;
        public final Set mSensorsPendingInvalidation = new ArraySet();

        public InvalidationTracker(Context context, ArrayList arrayList, int i, int i2, IInvalidationCallback iInvalidationCallback) {
            this.mClientCallback = iInvalidationCallback;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                final BiometricSensor biometricSensor = (BiometricSensor) it.next();
                if (biometricSensor.id != i2 && Utils.isAtLeastStrength(biometricSensor.oemStrength, 15)) {
                    try {
                    } catch (RemoteException e) {
                        Slog.e("BiometricService", "Remote Exception", e);
                    }
                    if (biometricSensor.impl.hasEnrolledTemplates(i, context.getOpPackageName())) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Requesting authenticatorId invalidation for sensor: "), biometricSensor.id, "BiometricService");
                        synchronized (this) {
                            this.mSensorsPendingInvalidation.add(Integer.valueOf(biometricSensor.id));
                        }
                        try {
                            biometricSensor.impl.invalidateAuthenticatorId(i, new IInvalidationCallback.Stub() { // from class: com.android.server.biometrics.BiometricService.InvalidationTracker.1
                                public final void onCompleted() {
                                    InvalidationTracker.this.onInvalidated(biometricSensor.id);
                                }
                            });
                        } catch (RemoteException e2) {
                            Slog.d("BiometricService", "RemoteException", e2);
                        }
                    } else {
                        continue;
                    }
                }
            }
            synchronized (this) {
                if (this.mSensorsPendingInvalidation.isEmpty()) {
                    try {
                        Slog.d("BiometricService", "No sensors require invalidation");
                        this.mClientCallback.onCompleted();
                    } catch (RemoteException e3) {
                        Slog.e("BiometricService", "Remote Exception", e3);
                    }
                }
            }
        }

        public void onInvalidated(int i) {
            synchronized (this) {
                ((ArraySet) this.mSensorsPendingInvalidation).remove(Integer.valueOf(i));
                Slog.d("BiometricService", "Sensor " + i + " invalidated, remaining size: " + ((ArraySet) this.mSensorsPendingInvalidation).size());
                if (((ArraySet) this.mSensorsPendingInvalidation).isEmpty()) {
                    try {
                        this.mClientCallback.onCompleted();
                    } catch (RemoteException e) {
                        Slog.e("BiometricService", "Remote Exception", e);
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class SettingObserver extends ContentObserver {
        public final Uri BIOMETRIC_APP_ENABLED;
        public final Uri BIOMETRIC_FP_BIO_STAR_ENABLED;
        public final Uri BIOMETRIC_KEYGUARD_ENABLED;
        public final Uri FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION;
        public final Uri FACE_UNLOCK_APP_ENABLED;
        public final Uri FACE_UNLOCK_KEYGUARD_ENABLED;
        public final Uri MANDATORY_BIOMETRICS_ENABLED;
        public final Uri MANDATORY_BIOMETRICS_REQUIREMENTS_SATISFIED;
        public final Map mBiometricEnabledForApps;
        public final Map mBiometricEnabledOnKeyguard;
        public final List mCallbacks;
        public final ContentResolver mContentResolver;
        public final Map mFaceAlwaysRequireConfirmation;
        public final Map mFaceEnrolledForUser;
        public final Map mFingerprintEnrolledForUser;
        public final Map mMandatoryBiometricsEnabled;
        public final Map mMandatoryBiometricsRequirementsSatisfied;
        public final boolean mUseLegacyFaceOnlySettings;
        public final UserManager mUserManager;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.biometrics.BiometricService$SettingObserver$2, reason: invalid class name */
        public final class AnonymousClass2 extends IFaceAuthenticatorsRegisteredCallback.Stub {
            public final /* synthetic */ FaceManager val$faceManager;

            public AnonymousClass2(FaceManager faceManager) {
                this.val$faceManager = faceManager;
            }

            public final void onAllAuthenticatorsRegistered(List list) {
                if (list == null || list.isEmpty()) {
                    Slog.d("BiometricService", "No face authenticators registered.");
                    return;
                }
                FaceSensorPropertiesInternal faceSensorPropertiesInternal = (FaceSensorPropertiesInternal) list.get(0);
                if (faceSensorPropertiesInternal.sensorStrength == 2) {
                    this.val$faceManager.registerBiometricStateListener(new AnonymousClass1.C00101(this, faceSensorPropertiesInternal));
                }
            }
        }

        public SettingObserver(Context context, Handler handler, List list, UserManager userManager, final FingerprintManager fingerprintManager, FaceManager faceManager) {
            super(handler);
            this.FACE_UNLOCK_KEYGUARD_ENABLED = Settings.Secure.getUriFor("face_unlock_keyguard_enabled");
            this.FACE_UNLOCK_APP_ENABLED = Settings.Secure.getUriFor("face_unlock_app_enabled");
            this.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION = Settings.Secure.getUriFor("face_unlock_always_require_confirmation");
            this.BIOMETRIC_KEYGUARD_ENABLED = Settings.Secure.getUriFor("biometric_keyguard_enabled");
            this.BIOMETRIC_APP_ENABLED = Settings.Secure.getUriFor("biometric_app_enabled");
            this.MANDATORY_BIOMETRICS_ENABLED = Settings.Secure.getUriFor("mandatory_biometrics");
            this.MANDATORY_BIOMETRICS_REQUIREMENTS_SATISFIED = Settings.Secure.getUriFor("mandatory_biometrics_requirements_satisfied");
            this.BIOMETRIC_FP_BIO_STAR_ENABLED = Settings.Secure.getUriFor("sem_biometric_fp_bio_start_enabled");
            this.mBiometricEnabledOnKeyguard = new HashMap();
            this.mBiometricEnabledForApps = new HashMap();
            this.mFaceAlwaysRequireConfirmation = new HashMap();
            this.mMandatoryBiometricsEnabled = new HashMap();
            this.mMandatoryBiometricsRequirementsSatisfied = new HashMap();
            this.mFingerprintEnrolledForUser = new HashMap();
            this.mFaceEnrolledForUser = new HashMap();
            this.mContentResolver = context.getContentResolver();
            this.mCallbacks = list;
            this.mUserManager = userManager;
            this.mUseLegacyFaceOnlySettings = Build.VERSION.DEVICE_INITIAL_SDK_INT <= 29 && context.getPackageManager().hasSystemFeature("android.hardware.biometrics.face") && !context.getPackageManager().hasSystemFeature("android.hardware.fingerprint");
            if (fingerprintManager != null) {
                fingerprintManager.addAuthenticatorsRegisteredCallback(new IFingerprintAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.server.biometrics.BiometricService.SettingObserver.1

                    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
                    /* renamed from: com.android.server.biometrics.BiometricService$SettingObserver$1$1, reason: invalid class name and collision with other inner class name */
                    public final class C00101 extends BiometricStateListener {
                        public final /* synthetic */ int $r8$classId = 0;
                        public final /* synthetic */ Object this$1;
                        public final /* synthetic */ Object val$fingerprintSensorProperties;

                        public C00101(AnonymousClass1 anonymousClass1, FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
                            this.this$1 = anonymousClass1;
                            this.val$fingerprintSensorProperties = fingerprintSensorPropertiesInternal;
                        }

                        public C00101(AnonymousClass2 anonymousClass2, FaceSensorPropertiesInternal faceSensorPropertiesInternal) {
                            this.this$1 = anonymousClass2;
                            this.val$fingerprintSensorProperties = faceSensorPropertiesInternal;
                        }

                        public final void onEnrollmentsChanged(int i, int i2, boolean z) {
                            switch (this.$r8$classId) {
                                case 0:
                                    if (i2 == ((FingerprintSensorPropertiesInternal) this.val$fingerprintSensorProperties).sensorId) {
                                        ((HashMap) SettingObserver.this.mFingerprintEnrolledForUser).put(Integer.valueOf(i), Boolean.valueOf(z));
                                        break;
                                    }
                                    break;
                                default:
                                    if (i2 == ((FaceSensorPropertiesInternal) this.val$fingerprintSensorProperties).sensorId) {
                                        ((HashMap) SettingObserver.this.mFaceEnrolledForUser).put(Integer.valueOf(i), Boolean.valueOf(z));
                                        break;
                                    }
                                    break;
                            }
                        }
                    }

                    public final void onAllAuthenticatorsRegistered(List list2) {
                        if (list2 == null || list2.isEmpty()) {
                            Slog.d("BiometricService", "No fingerprint authenticators registered.");
                            return;
                        }
                        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = (FingerprintSensorPropertiesInternal) list2.get(0);
                        if (fingerprintSensorPropertiesInternal.sensorStrength == 2) {
                            fingerprintManager.registerBiometricStateListener(new C00101(this, fingerprintSensorPropertiesInternal));
                        }
                    }
                });
            }
            if (faceManager != null) {
                faceManager.addAuthenticatorsRegisteredCallback(new AnonymousClass2(faceManager));
            }
            updateContentObserver();
        }

        public final boolean getEnabledForApps(int i) {
            if (!((HashMap) this.mBiometricEnabledForApps).containsKey(Integer.valueOf(i))) {
                if (this.mUseLegacyFaceOnlySettings) {
                    onChange(true, this.FACE_UNLOCK_APP_ENABLED, i);
                } else {
                    onChange(true, this.BIOMETRIC_APP_ENABLED, i);
                }
            }
            return ((Boolean) ((HashMap) this.mBiometricEnabledForApps).getOrDefault(Integer.valueOf(i), Boolean.TRUE)).booleanValue();
        }

        public final boolean getEnabledOnKeyguard(int i) {
            if (!((HashMap) this.mBiometricEnabledOnKeyguard).containsKey(Integer.valueOf(i))) {
                if (this.mUseLegacyFaceOnlySettings) {
                    onChange(true, this.FACE_UNLOCK_KEYGUARD_ENABLED, i);
                } else {
                    onChange(true, this.BIOMETRIC_KEYGUARD_ENABLED, i);
                }
            }
            return ((Boolean) ((HashMap) this.mBiometricEnabledOnKeyguard).get(Integer.valueOf(i))).booleanValue();
        }

        public final void notifyEnabledOnKeyguardCallbacks(int i) {
            List list = this.mCallbacks;
            for (int i2 = 0; i2 < list.size(); i2++) {
                EnabledOnKeyguardCallback enabledOnKeyguardCallback = (EnabledOnKeyguardCallback) list.get(i2);
                boolean booleanValue = ((Boolean) ((HashMap) this.mBiometricEnabledOnKeyguard).getOrDefault(Integer.valueOf(i), Boolean.TRUE)).booleanValue();
                enabledOnKeyguardCallback.getClass();
                try {
                    enabledOnKeyguardCallback.mCallback.onChanged(booleanValue, i);
                } catch (DeadObjectException e) {
                    Slog.w("BiometricService", "Death while invoking notify", e);
                    ((ArrayList) BiometricService.this.mEnabledOnKeyguardCallbacks).remove(enabledOnKeyguardCallback);
                } catch (RemoteException e2) {
                    Slog.w("BiometricService", "Failed to invoke onChanged", e2);
                }
            }
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            if (this.FACE_UNLOCK_KEYGUARD_ENABLED.equals(uri)) {
                ((HashMap) this.mBiometricEnabledOnKeyguard).put(Integer.valueOf(i), Boolean.valueOf(Settings.Secure.getIntForUser(this.mContentResolver, "face_unlock_keyguard_enabled", 1, i) != 0));
                if (i != ActivityManager.getCurrentUser() || z) {
                    return;
                }
                notifyEnabledOnKeyguardCallbacks(i);
                return;
            }
            if (this.FACE_UNLOCK_APP_ENABLED.equals(uri)) {
                ((HashMap) this.mBiometricEnabledForApps).put(Integer.valueOf(i), Boolean.valueOf(Settings.Secure.getIntForUser(this.mContentResolver, "face_unlock_app_enabled", 1, i) != 0));
                return;
            }
            if (this.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION.equals(uri)) {
                ((HashMap) this.mFaceAlwaysRequireConfirmation).put(Integer.valueOf(i), Boolean.valueOf(Settings.Secure.getIntForUser(this.mContentResolver, "face_unlock_always_require_confirmation", 0, i) != 0));
                return;
            }
            if (this.BIOMETRIC_KEYGUARD_ENABLED.equals(uri)) {
                ((HashMap) this.mBiometricEnabledOnKeyguard).put(Integer.valueOf(i), Boolean.valueOf(Settings.Secure.getIntForUser(this.mContentResolver, "biometric_keyguard_enabled", 1, i) != 0));
                if (i != ActivityManager.getCurrentUser() || z) {
                    return;
                }
                notifyEnabledOnKeyguardCallbacks(i);
                return;
            }
            if (this.BIOMETRIC_APP_ENABLED.equals(uri)) {
                ((HashMap) this.mBiometricEnabledForApps).put(Integer.valueOf(i), Boolean.valueOf(Settings.Secure.getIntForUser(this.mContentResolver, "biometric_app_enabled", 1, i) != 0));
                return;
            }
            if (this.MANDATORY_BIOMETRICS_ENABLED.equals(uri)) {
                updateMandatoryBiometricsForAllProfiles(i);
                return;
            }
            if (this.MANDATORY_BIOMETRICS_REQUIREMENTS_SATISFIED.equals(uri)) {
                updateMandatoryBiometricsRequirementsForAllProfiles(i);
                return;
            }
            if (this.BIOMETRIC_FP_BIO_STAR_ENABLED.equals(uri)) {
                boolean z2 = Settings.Secure.getIntForUser(this.mContentResolver, "sem_biometric_fp_bio_start_enabled", 0, i) != 0;
                SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.get();
                semBioLoggingManager.getClass();
                if (Utils.DEBUG) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m("FP BIO STAR: ", "BiometricStats", z2);
                }
                semBioLoggingManager.mIsFpBioStarEnabled = z2;
            }
        }

        public final void updateContentObserver() {
            this.mContentResolver.unregisterContentObserver(this);
            if (this.mUseLegacyFaceOnlySettings) {
                this.mContentResolver.registerContentObserver(this.FACE_UNLOCK_KEYGUARD_ENABLED, false, this, -1);
                this.mContentResolver.registerContentObserver(this.FACE_UNLOCK_APP_ENABLED, false, this, -1);
            } else {
                this.mContentResolver.registerContentObserver(this.BIOMETRIC_KEYGUARD_ENABLED, false, this, -1);
                this.mContentResolver.registerContentObserver(this.BIOMETRIC_APP_ENABLED, false, this, -1);
            }
            this.mContentResolver.registerContentObserver(this.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION, false, this, -1);
            this.mContentResolver.registerContentObserver(this.MANDATORY_BIOMETRICS_ENABLED, false, this, -1);
            this.mContentResolver.registerContentObserver(this.MANDATORY_BIOMETRICS_REQUIREMENTS_SATISFIED, false, this, -1);
            boolean z = SemBiometricFeature.FEATURE_LOGGING_MODE;
            this.mContentResolver.registerContentObserver(this.BIOMETRIC_FP_BIO_STAR_ENABLED, false, this, -1);
            SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.get();
            boolean z2 = Settings.Secure.getInt(this.mContentResolver, "sem_biometric_fp_bio_start_enabled", 0) != 0;
            semBioLoggingManager.getClass();
            if (Utils.DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m("FP BIO STAR: ", "BiometricStats", z2);
            }
            semBioLoggingManager.mIsFpBioStarEnabled = z2;
        }

        public final void updateMandatoryBiometricsForAllProfiles(int i) {
            UserInfo profileParent = this.mUserManager.getProfileParent(i);
            int i2 = profileParent != null ? profileParent.id : i;
            int[] enabledProfileIds = this.mUserManager.getEnabledProfileIds(i2);
            if (enabledProfileIds == null) {
                ((HashMap) this.mMandatoryBiometricsEnabled).put(Integer.valueOf(i), Boolean.valueOf(Settings.Secure.getIntForUser(this.mContentResolver, "mandatory_biometrics", 0, i2) != 0));
                return;
            }
            for (int i3 : enabledProfileIds) {
                ((HashMap) this.mMandatoryBiometricsEnabled).put(Integer.valueOf(i3), Boolean.valueOf(Settings.Secure.getIntForUser(this.mContentResolver, "mandatory_biometrics", 0, i2) != 0));
            }
        }

        public final void updateMandatoryBiometricsRequirementsForAllProfiles(int i) {
            UserInfo profileParent = this.mUserManager.getProfileParent(i);
            int i2 = profileParent != null ? profileParent.id : i;
            int[] enabledProfileIds = this.mUserManager.getEnabledProfileIds(i2);
            if (enabledProfileIds == null) {
                ((HashMap) this.mMandatoryBiometricsRequirementsSatisfied).put(Integer.valueOf(i), Boolean.valueOf(Settings.Secure.getIntForUser(this.mContentResolver, "mandatory_biometrics_requirements_satisfied", 1, i2) != 0));
                return;
            }
            for (int i3 : enabledProfileIds) {
                ((HashMap) this.mMandatoryBiometricsRequirementsSatisfied).put(Integer.valueOf(i3), Boolean.valueOf(Settings.Secure.getIntForUser(this.mContentResolver, "mandatory_biometrics_requirements_satisfied", 1, i2) != 0));
            }
        }
    }

    /* renamed from: -$$Nest$mcreatePreAuthInfo, reason: not valid java name */
    public static PreAuthInfo m312$$Nest$mcreatePreAuthInfo(BiometricService biometricService, String str, int i, int i2) {
        biometricService.getClass();
        PromptInfo promptInfo = new PromptInfo();
        promptInfo.setAuthenticators(i2);
        return PreAuthInfo.create(biometricService.mTrustManager, biometricService.mDevicePolicyManager, biometricService.mSettingObserver, biometricService.mSensors, i, promptInfo, str, false, biometricService.getContext(), biometricService.mBiometricCameraManager);
    }

    /* renamed from: -$$Nest$mdumpInternal, reason: not valid java name */
    public static void m313$$Nest$mdumpInternal(BiometricService biometricService, PrintWriter printWriter) {
        printWriter.println("Legacy Settings: " + biometricService.mSettingObserver.mUseLegacyFaceOnlySettings);
        printWriter.println();
        printWriter.println("Sensors:");
        Iterator it = biometricService.mSensors.iterator();
        while (it.hasNext()) {
            printWriter.println(" " + ((BiometricSensor) it.next()));
        }
        printWriter.println();
        printWriter.println("CurrentSession: " + biometricService.mAuthSession);
        printWriter.println();
        SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.get();
        Iterator it2 = semBioLoggingManager.mBpAuthLogList.iterator();
        while (it2.hasNext()) {
            printWriter.println((String) it2.next());
        }
        int size = semBioLoggingManager.mBpLoggingInfo.size();
        if (size > 0) {
            printWriter.println(" [ in progress client ]");
            for (int i = 0; i < size; i++) {
                SemBioLoggingManager.LoggingInfo loggingInfo = (SemBioLoggingManager.LoggingInfo) semBioLoggingManager.mBpLoggingInfo.valueAt(i);
                if (loggingInfo != null) {
                    printWriter.println(" - ".concat(loggingInfo.toDumpFormat()));
                }
            }
        }
        printWriter.println();
    }

    public BiometricService(Context context) {
        this(context, new Injector(), BiometricHandlerProvider.sBiometricHandlerProvider);
    }

    public BiometricService(Context context, Injector injector, BiometricHandlerProvider biometricHandlerProvider) {
        super(context);
        IGateKeeperService iGateKeeperService;
        this.mRandom = new Random();
        this.mSensors = new ArrayList();
        this.mInjector = injector;
        Handler biometricCallbackHandler = biometricHandlerProvider.getBiometricCallbackHandler();
        this.mHandler = biometricCallbackHandler;
        injector.getClass();
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mImpl = new BiometricServiceWrapper();
        ArrayList arrayList = new ArrayList();
        this.mEnabledOnKeyguardCallbacks = arrayList;
        this.mSettingObserver = new SettingObserver(context, biometricCallbackHandler, arrayList, (UserManager) context.getSystemService(UserManager.class), (FingerprintManager) context.getSystemService(FingerprintManager.class), (FaceManager) context.getSystemService(FaceManager.class));
        this.mRequestCounter = new BiometricService$Injector$$ExternalSyntheticLambda0(new AtomicLong(0L));
        this.mBiometricContext = BiometricContext.getInstance(context);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mBiometricCameraManager = new BiometricCameraManagerImpl((CameraManager) context.getSystemService(CameraManager.class), (SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class));
        this.mKeyStoreAuthorization = KeyStoreAuthorization.getInstance();
        try {
            iGateKeeperService = GateKeeper.getService();
        } catch (IllegalStateException e) {
            Slog.w("BiometricService", "getGateKeeperService: " + e.getMessage());
            iGateKeeperService = null;
        }
        this.mGateKeeper = iGateKeeperService;
        this.mBiometricNotificationLogger = new BiometricNotificationLogger(BiometricFrameworkStatsLogger.sInstance);
        synchronized (SemBiometricSysUiManager.class) {
            if (SemBiometricSysUiManager.sInstance == null) {
                SemBiometricSysUiManager.sInstance = new SemBiometricSysUiManager(context, BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler().getLooper());
            }
        }
        try {
            ActivityManager.getService().registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.server.biometrics.BiometricService.3
                public final void onUserSwitchComplete(int i) {
                    BiometricService.this.mSettingObserver.updateContentObserver();
                    BiometricService.this.mSettingObserver.notifyEnabledOnKeyguardCallbacks(i);
                }
            }, BiometricService.class.getName());
        } catch (RemoteException e2) {
            Slog.e("BiometricService", "Failed to register user switch observer", e2);
        }
    }

    public final void authenticateInternal(IBinder iBinder, long j, long j2, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo, PreAuthInfo preAuthInfo) {
        FingerprintManager fingerprintManager;
        Slog.d("BiometricService", "Creating authSession with authRequest: " + preAuthInfo);
        if (this.mAuthSession != null) {
            Slog.w("BiometricService", "Existing AuthSession: " + this.mAuthSession);
            this.mAuthSession.onCancelAuthSession(true);
            this.mAuthSession.destroy();
            this.mAuthSession = null;
        }
        Context context = getContext();
        this.mInjector.getClass();
        boolean isDebugEnabled = Utils.isDebugEnabled(context, i);
        Context context2 = getContext();
        SemBiometricSysUiWrapper semBiometricSysUiWrapper = this.mStatusBarService;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(j);
        KeyStoreAuthorization keyStoreAuthorization = this.mKeyStoreAuthorization;
        Random random = this.mRandom;
        BiometricService$$ExternalSyntheticLambda3 biometricService$$ExternalSyntheticLambda3 = new BiometricService$$ExternalSyntheticLambda3(this, j);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(j);
        Context context3 = getContext();
        AuthSession authSession = new AuthSession(context2, this.mBiometricContext, semBiometricSysUiWrapper, anonymousClass2, keyStoreAuthorization, random, biometricService$$ExternalSyntheticLambda3, preAuthInfo, iBinder, j, j2, i, anonymousClass1, iBiometricServiceReceiver, str, promptInfo, isDebugEnabled, (!context3.getPackageManager().hasSystemFeature("android.hardware.fingerprint") || (fingerprintManager = (FingerprintManager) context3.getSystemService(FingerprintManager.class)) == null) ? new ArrayList() : fingerprintManager.getSensorPropertiesInternal(), BiometricFrameworkStatsLogger.sInstance);
        this.mAuthSession = authSession;
        authSession.mUseSwitchingMode = true;
        try {
            authSession.goToInitialState();
        } catch (RemoteException e) {
            Slog.e("BiometricService", "RemoteException", e);
        }
    }

    public final AuthSession getAuthSessionIfCurrent(long j) {
        AuthSession authSession = this.mAuthSession;
        if (authSession == null || authSession.mRequestId != j) {
            return null;
        }
        return authSession;
    }

    public final void handleAuthenticationSucceeded(long j, int i, byte[] bArr, Bundle bundle) {
        boolean z;
        ProxyManager$$ExternalSyntheticOutline0.m(i, "handleAuthenticationSucceeded(), sensorId: ", "BiometricService");
        AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            Slog.e("BiometricService", "handleAuthenticationSucceeded: AuthSession is null");
            return;
        }
        Iterator it = this.mSensors.iterator();
        while (true) {
            if (!it.hasNext()) {
                NandswapManager$$ExternalSyntheticOutline0.m(i, "Unknown sensorId: ", "BiometricService");
                z = false;
                break;
            } else {
                BiometricSensor biometricSensor = (BiometricSensor) it.next();
                if (biometricSensor.id == i) {
                    z = Utils.isAtLeastStrength(biometricSensor.getCurrentStrength(), 15);
                    break;
                }
            }
        }
        if (authSessionIfCurrent.hasAuthenticatedAndConfirmed()) {
            Slog.d("BiometricService/AuthSession", "onAuthenticationSucceeded after successful auth");
            return;
        }
        authSessionIfCurrent.mAuthenticatedSensorId = i;
        if (z) {
            authSessionIfCurrent.mTokenEscrow = bArr;
        } else if (bArr != null) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Dropping authToken for non-strong biometric, id: ", "BiometricService/AuthSession");
        }
        AuthSession.AuthenticationResult authenticationResult = new AuthSession.AuthenticationResult();
        final String str = "";
        if (bundle != null) {
            authenticationResult.name = bundle.getString("KEY_IDENTIFIER_NAME", "");
            authenticationResult.id = bundle.getInt("KEY_BIOMETRICS_ID", 0);
            authenticationResult.challengeToken = bundle.getByteArray("KEY_CHALLENGE_TOKEN");
            authSessionIfCurrent.mAuthenticationResults.put(i, authenticationResult);
        }
        try {
            if (TextUtils.isEmpty(authenticationResult.name)) {
                final SemBiometricSysUiWrapper semBiometricSysUiWrapper = authSessionIfCurrent.mStatusBarService;
                final int sensorIdToModality = authSessionIfCurrent.sensorIdToModality(i);
                ((HashMap) semBiometricSysUiWrapper.mSessions).forEach(new BiConsumer() { // from class: com.android.server.biometrics.SemBiometricSysUiWrapper$$ExternalSyntheticLambda4
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        SemBiometricSysUiWrapper semBiometricSysUiWrapper2 = SemBiometricSysUiWrapper.this;
                        int i2 = sensorIdToModality;
                        String str2 = str;
                        semBiometricSysUiWrapper2.getClass();
                        int intValue = ((Integer) ((Pair) obj2).first).intValue();
                        SemBiometricSysUiManager semBiometricSysUiManager = semBiometricSysUiWrapper2.mSysUiManager;
                        semBiometricSysUiManager.getClass();
                        semBiometricSysUiManager.handleRequest(new SemBiometricSysUiManager$$ExternalSyntheticLambda10(semBiometricSysUiManager, intValue, i2, true, str2));
                    }
                });
            } else {
                final SemBiometricSysUiWrapper semBiometricSysUiWrapper2 = authSessionIfCurrent.mStatusBarService;
                final int sensorIdToModality2 = authSessionIfCurrent.sensorIdToModality(i);
                final String str2 = authenticationResult.name;
                ((HashMap) semBiometricSysUiWrapper2.mSessions).forEach(new BiConsumer() { // from class: com.android.server.biometrics.SemBiometricSysUiWrapper$$ExternalSyntheticLambda4
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        SemBiometricSysUiWrapper semBiometricSysUiWrapper22 = SemBiometricSysUiWrapper.this;
                        int i2 = sensorIdToModality2;
                        String str22 = str2;
                        semBiometricSysUiWrapper22.getClass();
                        int intValue = ((Integer) ((Pair) obj2).first).intValue();
                        SemBiometricSysUiManager semBiometricSysUiManager = semBiometricSysUiWrapper22.mSysUiManager;
                        semBiometricSysUiManager.getClass();
                        semBiometricSysUiManager.handleRequest(new SemBiometricSysUiManager$$ExternalSyntheticLambda10(semBiometricSysUiManager, intValue, i2, true, str22));
                    }
                });
            }
            Iterator it2 = authSessionIfCurrent.mPreAuthInfo.eligibleSensors.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    authSessionIfCurrent.mState = 7;
                    break;
                } else if (authSessionIfCurrent.isConfirmationRequired((BiometricSensor) it2.next())) {
                    authSessionIfCurrent.mAuthenticatedTimeMs = System.currentTimeMillis();
                    authSessionIfCurrent.mState = 6;
                    break;
                }
            }
        } catch (RemoteException e) {
            Slog.e("BiometricService/AuthSession", "RemoteException", e);
        }
        if (authSessionIfCurrent.mState == 6) {
            authSessionIfCurrent.cancelAllSensors(new AuthSession$$ExternalSyntheticLambda6(authSessionIfCurrent, i, 1));
        } else {
            authSessionIfCurrent.cancelAllSensors(new AuthSession$$ExternalSyntheticLambda5(i, 1));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01a4 A[Catch: all -> 0x0193, RemoteException -> 0x0196, TryCatch #1 {all -> 0x0193, blocks: (B:39:0x01a0, B:41:0x01a4, B:42:0x01c0, B:44:0x01c8, B:46:0x01d1, B:47:0x01db, B:48:0x01e9, B:49:0x01ba, B:53:0x018f, B:71:0x021f, B:54:0x0199), top: B:15:0x0147 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01c8 A[Catch: all -> 0x0193, RemoteException -> 0x0196, TryCatch #1 {all -> 0x0193, blocks: (B:39:0x01a0, B:41:0x01a4, B:42:0x01c0, B:44:0x01c8, B:46:0x01d1, B:47:0x01db, B:48:0x01e9, B:49:0x01ba, B:53:0x018f, B:71:0x021f, B:54:0x0199), top: B:15:0x0147 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01e9 A[Catch: all -> 0x0193, RemoteException -> 0x0196, TRY_LEAVE, TryCatch #1 {all -> 0x0193, blocks: (B:39:0x01a0, B:41:0x01a4, B:42:0x01c0, B:44:0x01c8, B:46:0x01d1, B:47:0x01db, B:48:0x01e9, B:49:0x01ba, B:53:0x018f, B:71:0x021f, B:54:0x0199), top: B:15:0x0147 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01ba A[Catch: all -> 0x0193, RemoteException -> 0x0196, TryCatch #1 {all -> 0x0193, blocks: (B:39:0x01a0, B:41:0x01a4, B:42:0x01c0, B:44:0x01c8, B:46:0x01d1, B:47:0x01db, B:48:0x01e9, B:49:0x01ba, B:53:0x018f, B:71:0x021f, B:54:0x0199), top: B:15:0x0147 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x026a A[LOOP:1: B:62:0x0265->B:64:0x026a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0271 A[EDGE_INSN: B:65:0x0271->B:66:0x0271 BREAK  A[LOOP:1: B:62:0x0265->B:64:0x026a], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0235 A[LOOP:2: B:77:0x0230->B:79:0x0235, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x023c A[EDGE_INSN: B:80:0x023c->B:81:0x023c BREAK  A[LOOP:2: B:77:0x0230->B:79:0x0235], SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v15, types: [com.android.server.biometrics.log.BiometricFrameworkStatsLogger, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v28, types: [com.android.server.biometrics.log.BiometricFrameworkStatsLogger, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleOnDismissed(long r28, byte[] r30, int r31) {
        /*
            Method dump skipped, instructions count: 666
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.BiometricService.handleOnDismissed(long, byte[], int):void");
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        super.onBootPhase(i);
        if (i == 1000) {
            BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler().post(new Runnable() { // from class: com.android.server.biometrics.BiometricService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    long j;
                    BiometricService biometricService = BiometricService.this;
                    biometricService.getClass();
                    long currentTimeMillis = System.currentTimeMillis();
                    UserManager userManager = UserManager.get(biometricService.getContext());
                    Slog.i("BiometricService", "initBiometricsTimestamp: user length = " + userManager.getUsers().size());
                    Iterator it = userManager.getUsers().iterator();
                    while (it.hasNext()) {
                        int userOrWorkProfileId = Utils.getUserOrWorkProfileId(biometricService.getContext(), ((UserInfo) it.next()).id);
                        try {
                            j = Settings.Secure.getLongForUser(biometricService.getContext().getContentResolver(), "biometrics_strong_enroll_timestamp", -1L, userOrWorkProfileId);
                        } catch (Exception e) {
                            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("getIntDb: "), "BiometricUtils");
                            j = -1;
                        }
                        boolean z = Utils.DEBUG;
                        if (z) {
                            Slog.i("BiometricService", "timestamp db = [" + userOrWorkProfileId + "]: " + j);
                        }
                        if (j == -1) {
                            long j2 = 0;
                            if (Settings.Global.getInt(biometricService.getContext().getContentResolver(), "auto_time", 0) > 0) {
                                Iterator it2 = biometricService.mSensors.iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        break;
                                    }
                                    BiometricSensor biometricSensor = (BiometricSensor) it2.next();
                                    if (Utils.isAtLeastStrength(biometricSensor.getCurrentStrength(), 15)) {
                                        try {
                                            if (biometricSensor.impl.hasEnrolledTemplates(userOrWorkProfileId, "BiometricService")) {
                                                if (Utils.DEBUG) {
                                                    Slog.d("BiometricService", "timestamp authenticator id: " + biometricSensor.id);
                                                }
                                                j2 = System.currentTimeMillis();
                                            }
                                        } catch (Exception e2) {
                                            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e2, new StringBuilder("initBiometricsTimestamp: "), "BiometricService");
                                        }
                                    }
                                }
                            } else if (z) {
                                Slog.d("BiometricService", "initBiometricsTimestamp: No auto time");
                            }
                            Utils.putLongDb(biometricService.getContext(), j2, userOrWorkProfileId);
                        }
                    }
                    Slog.i("BiometricService", "initBiometricsTimestamp: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
                }
            });
            SemBioAnalyticsManager semBioAnalyticsManager = (SemBioAnalyticsManager) SemBioAnalyticsManager.sInstance.get();
            Context context = getContext();
            semBioAnalyticsManager.getClass();
            semBioAnalyticsManager.mH.post(new SemBioAnalyticsManager$$ExternalSyntheticLambda1(0, semBioAnalyticsManager, context));
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.biometrics.BiometricService$$ExternalSyntheticLambda0] */
    @Override // com.android.server.SystemService
    public final void onStart() {
        Context context = getContext();
        ?? r1 = new IntFunction() { // from class: com.android.server.biometrics.BiometricService$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                Iterator it = BiometricService.this.mSensors.iterator();
                while (it.hasNext()) {
                    BiometricSensor biometricSensor = (BiometricSensor) it.next();
                    if (biometricSensor.id == i) {
                        return Integer.valueOf(biometricSensor.modality);
                    }
                }
                return 0;
            }
        };
        this.mInjector.getClass();
        Handler biometricCallbackHandler = BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler();
        IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mStatusBarService = new SemBiometricSysUiWrapper(context, biometricCallbackHandler, this.mBiometricContext, r1);
        this.mTrustManager = ITrustManager.Stub.asInterface(ServiceManager.getService("trust"));
        publishBinderService("biometric", this.mImpl);
        BiometricStrengthController biometricStrengthController = new BiometricStrengthController(this);
        this.mBiometricStrengthController = biometricStrengthController;
        DeviceConfig.addOnPropertiesChangedListener("biometrics", BackgroundThread.getExecutor(), biometricStrengthController.mDeviceConfigListener);
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.BiometricService.4
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    BiometricService biometricService = BiometricService.this;
                    biometricService.mBiometricNotificationLogger.registerAsSystemService(biometricService.getContext(), new ComponentName(BiometricService.this.getContext(), (Class<?>) BiometricNotificationLogger.class), -1);
                } catch (RemoteException unused) {
                }
            }
        });
        SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor = SemBiometricDisplayStateMonitor.InstanceHolder.INSTANCE;
        if (semBiometricDisplayStateMonitor.mDisplayManagerInternal == null) {
            semBiometricDisplayStateMonitor.mDisplayManagerInternal = (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        }
        DisplayManagerInternal displayManagerInternal = semBiometricDisplayStateMonitor.mDisplayManagerInternal;
        if (displayManagerInternal != null) {
            displayManagerInternal.registerDisplayStateListener(semBiometricDisplayStateMonitor);
            if (SemBiometricFeature.FP_FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS) {
                semBiometricDisplayStateMonitor.mDisplayManagerInternal.registerDisplayBrightnessListener(semBiometricDisplayStateMonitor);
            }
        }
    }
}
