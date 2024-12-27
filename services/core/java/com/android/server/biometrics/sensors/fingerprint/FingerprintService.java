package com.android.server.biometrics.sensors.fingerprint;

import android.app.AppOpsManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.biometrics.AuthenticationStateListener;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.biometrics.IBiometricStateListener;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.biometrics.fingerprint.SensorProps;
import android.hardware.face.IFaceServiceReceiver;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.FingerprintEnrollOptions;
import android.hardware.fingerprint.FingerprintSensorConfigurations;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintServiceReceiver;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.hardware.fingerprint.IFingerprintClientActiveCallback;
import android.hardware.fingerprint.IFingerprintService;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.hardware.fingerprint.IUdfpsOverlayController;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.TypedValue;
import android.util.proto.ProtoOutputStream;
import android.view.WindowManager;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBiometricBoostingManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricSysUiManager;
import com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda6;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.AuthenticationStateListeners;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricStateCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.fingerprint.FingerprintService;
import com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsHelper;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsOpticalHelper;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda0;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda1;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda10;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda13;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda18;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda25;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda29;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda7;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda9;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;
import com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FingerprintService extends SystemService {
    public final Supplier mAidlInstanceNameSupplier;
    public final AppOpsManager mAppOps;
    public final AuthenticationStateListeners mAuthenticationStateListeners;
    public final Object mAuthenticationSyncLock;
    public final BiometricContext mBiometricContext;
    public final BiometricStateCallback mBiometricStateCallback;
    public final FingerprintProviderFunction mFingerprintProviderFunction;
    public final GestureAvailabilityDispatcher mGestureAvailabilityDispatcher;
    public final Handler mHandler;
    public final LockPatternUtils mLockPatternUtils;
    public final LockoutResetDispatcher mLockoutResetDispatcher;
    public final FingerprintServiceRegistry mRegistry;
    public final SemFingerprintServiceExtImpl mServiceExtImpl;
    final IFingerprintService.Stub mServiceWrapper;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.fingerprint.FingerprintService$1, reason: invalid class name */
    public final class AnonymousClass1 extends IFingerprintService.Stub {
        public AnonymousClass1() {
        }

        public final void addAuthenticatorsRegisteredCallback(IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) {
            addAuthenticatorsRegisteredCallback_enforcePermission();
            FingerprintService.this.mRegistry.addAllRegisteredCallback(iFingerprintAuthenticatorsRegisteredCallback);
        }

        public final void addClientActiveCallback(IFingerprintClientActiveCallback iFingerprintClientActiveCallback) {
            addClientActiveCallback_enforcePermission();
            FingerprintService.this.mGestureAvailabilityDispatcher.mClientActiveCallbacks.add(iFingerprintClientActiveCallback);
        }

        public final void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) {
            addLockoutResetCallback_enforcePermission();
            BinaryTransparencyService$$ExternalSyntheticOutline0.m("addLockoutResetCallback: ", str, "FingerprintService");
            FingerprintService.this.mLockoutResetDispatcher.addCallback(iBiometricServiceLockoutResetCallback, str);
        }

        public final long authenticate(IBinder iBinder, long j, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
            return semAuthenticate(iBinder, j, iFingerprintServiceReceiver, fingerprintAuthenticateOptions, new Bundle());
        }

        public final void cancelAuthentication(IBinder iBinder, String str, String str2, long j) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int callingUserId = UserHandle.getCallingUserId();
            Slog.i("FingerprintService", "cancelAuthentication: " + str);
            synchronized (FingerprintService.this.mAuthenticationSyncLock) {
                try {
                    if (!FingerprintService.m315$$Nest$mcanUseFingerprint(FingerprintService.this, str, str2, false, callingUid, callingPid, callingUserId)) {
                        Slog.w("FingerprintService", "cancelAuthentication rejecting package: " + str);
                        return;
                    }
                    Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
                    if (singleProvider == null) {
                        Slog.w("FingerprintService", "Null provider for cancelAuthentication");
                        return;
                    }
                    FingerprintProvider fingerprintProvider = (FingerprintProvider) ((ServiceProvider) singleProvider.second);
                    fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda29(fingerprintProvider, ((Integer) singleProvider.first).intValue(), iBinder, j, 0));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) {
            cancelAuthenticationFromService_enforcePermission();
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "cancelAuthenticationFromService, sensorId: ", "FingerprintService");
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider == null) {
                Slog.w("FingerprintService", "Null provider for cancelAuthenticationFromService");
            } else {
                FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
                fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda29(fingerprintProvider, i, iBinder, j, 0));
            }
        }

        public final void cancelEnrollment(IBinder iBinder, long j) {
            cancelEnrollment_enforcePermission();
            Slog.i("FingerprintService", "cancelEnrollment: " + iBinder);
            Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FingerprintService", "Null provider for cancelEnrollment");
                return;
            }
            FingerprintProvider fingerprintProvider = (FingerprintProvider) ((ServiceProvider) singleProvider.second);
            fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda29(fingerprintProvider, ((Integer) singleProvider.first).intValue(), iBinder, j, 1));
        }

        public final void cancelFingerprintDetect(IBinder iBinder, String str, long j) {
            cancelFingerprintDetect_enforcePermission();
            Slog.i("FingerprintService", "cancelFingerprintDetect: " + str + ", " + j);
            if (!Utils.isKeyguard(FingerprintService.this.getContext(), str)) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("cancelFingerprintDetect called from non-sysui package: ", str, "FingerprintService");
                return;
            }
            Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FingerprintService", "Null provider for cancelFingerprintDetect");
                return;
            }
            FingerprintProvider fingerprintProvider = (FingerprintProvider) ((ServiceProvider) singleProvider.second);
            fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda29(fingerprintProvider, ((Integer) singleProvider.first).intValue(), iBinder, j, 0));
        }

        public final ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) {
            createTestSession_enforcePermission();
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider != null) {
                return ((FingerprintProvider) serviceProvider).createTestSession(i, iTestSessionCallback);
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Null provider for createTestSession, sensorId: ", "FingerprintService");
            return null;
        }

        public final long detectFingerprint(final IBinder iBinder, IFingerprintServiceReceiver iFingerprintServiceReceiver, final FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
            detectFingerprint_enforcePermission();
            String opPackageName = fingerprintAuthenticateOptions.getOpPackageName();
            Slog.i("FingerprintService", "detectFingerprint: [" + fingerprintAuthenticateOptions.getUserId() + "] from pkg=" + opPackageName);
            if (!Utils.isKeyguard(FingerprintService.this.getContext(), opPackageName)) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("detectFingerprint called from non-sysui package: ", opPackageName, "FingerprintService");
                return -1L;
            }
            Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FingerprintService", "Null provider for detectFingerprint");
                return -1L;
            }
            fingerprintAuthenticateOptions.setSensorId(((Integer) singleProvider.first).intValue());
            ServiceProvider serviceProvider = (ServiceProvider) singleProvider.second;
            final ClientMonitorCallbackConverter clientMonitorCallbackConverter = new ClientMonitorCallbackConverter(iFingerprintServiceReceiver);
            final FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
            final long incrementAndGet = fingerprintProvider.mRequestCounter.incrementAndGet();
            fingerprintProvider.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda27
                public final /* synthetic */ int f$5 = 1;

                @Override // java.lang.Runnable
                public final void run() {
                    FingerprintProvider fingerprintProvider2 = FingerprintProvider.this;
                    FingerprintAuthenticateOptions fingerprintAuthenticateOptions2 = fingerprintAuthenticateOptions;
                    IBinder iBinder2 = iBinder;
                    long j = incrementAndGet;
                    ClientMonitorCallbackConverter clientMonitorCallbackConverter2 = clientMonitorCallbackConverter;
                    int i = this.f$5;
                    fingerprintProvider2.getClass();
                    int sensorId = fingerprintAuthenticateOptions2.getSensorId();
                    fingerprintProvider2.scheduleForSensor$1(sensorId, new FingerprintDetectClient(fingerprintProvider2.mContext, ((Sensor) fingerprintProvider2.mFingerprintSensors.mSensors.get(sensorId)).mLazySession, iBinder2, j, clientMonitorCallbackConverter2, fingerprintAuthenticateOptions2, fingerprintProvider2.createLogger$1(2, i, fingerprintProvider2.mAuthenticationStatsCollector), fingerprintProvider2.mBiometricContext, fingerprintProvider2.mAuthenticationStateListeners, Utils.isStrongBiometric(sensorId)), fingerprintProvider2.mBiometricStateCallback);
                }
            });
            return incrementAndGet;
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(FingerprintService.this.getContext(), "FingerprintService", printWriter)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    FingerprintService.this.mServiceExtImpl.getClass();
                    if (!Build.IS_USER && strArr.length > 2 && "--tpa".equals(strArr[0])) {
                        FingerprintService.this.mServiceExtImpl.handleTpaCommand(printWriter, strArr);
                        return;
                    }
                    if (strArr.length > 1 && "--proto".equals(strArr[0]) && "--state".equals(strArr[1])) {
                        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
                        Iterator it = FingerprintService.this.mRegistry.getProviders().iterator();
                        while (it.hasNext()) {
                            FingerprintProvider fingerprintProvider = (FingerprintProvider) ((ServiceProvider) it.next());
                            Iterator it2 = ((ArrayList) fingerprintProvider.getSensorProperties()).iterator();
                            while (it2.hasNext()) {
                                fingerprintProvider.dumpProtoState(((FingerprintSensorPropertiesInternal) it2.next()).sensorId, protoOutputStream, false);
                            }
                        }
                        protoOutputStream.flush();
                    } else if (strArr.length <= 0 || !"--proto".equals(strArr[0])) {
                        Iterator it3 = FingerprintService.this.mRegistry.getProviders().iterator();
                        while (it3.hasNext()) {
                            FingerprintProvider fingerprintProvider2 = (FingerprintProvider) ((ServiceProvider) it3.next());
                            Iterator it4 = ((ArrayList) fingerprintProvider2.getSensorProperties()).iterator();
                            while (it4.hasNext()) {
                                FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = (FingerprintSensorPropertiesInternal) it4.next();
                                printWriter.println("Dumping for sensorId: " + fingerprintSensorPropertiesInternal.sensorId + ", provider: " + fingerprintProvider2.getClass().getSimpleName());
                                StringBuilder sb = new StringBuilder();
                                sb.append("Fps state: ");
                                sb.append(FingerprintService.this.mBiometricStateCallback.mBiometricState);
                                printWriter.println(sb.toString());
                                fingerprintProvider2.dumpInternal(fingerprintSensorPropertiesInternal.sensorId, printWriter);
                                printWriter.println();
                            }
                        }
                        FingerprintService.this.mServiceExtImpl.dump(printWriter);
                    } else {
                        Iterator it5 = FingerprintService.this.mRegistry.getProviders().iterator();
                        while (it5.hasNext()) {
                            Iterator it6 = ((ArrayList) ((FingerprintProvider) ((ServiceProvider) it5.next())).getSensorProperties()).iterator();
                            while (it6.hasNext()) {
                                int i = ((FingerprintSensorPropertiesInternal) it6.next()).sensorId;
                            }
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final byte[] dumpSensorServiceStateProto(int i, boolean z) {
            dumpSensorServiceStateProto_enforcePermission();
            ProtoOutputStream protoOutputStream = new ProtoOutputStream();
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider != null) {
                ((FingerprintProvider) serviceProvider).dumpProtoState(i, protoOutputStream, z);
            }
            protoOutputStream.flush();
            return protoOutputStream.getBytes();
        }

        public final long enroll(IBinder iBinder, byte[] bArr, int i, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str, int i2, FingerprintEnrollOptions fingerprintEnrollOptions) {
            enroll_enforcePermission();
            Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FingerprintService", "Null provider for enroll");
                return -1L;
            }
            Slog.i("FingerprintService", "enroll: [" + i + "] from pkg=" + str);
            if (bArr == null || bArr.length == 0) {
                return -1L;
            }
            Pair canUseFingerprint = FingerprintService.this.mServiceExtImpl.canUseFingerprint(false, false);
            if (((Integer) canUseFingerprint.first).intValue() != 0) {
                try {
                    iFingerprintServiceReceiver.onError(((Integer) canUseFingerprint.first).intValue(), ((Integer) canUseFingerprint.second).intValue());
                    return 0L;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return 0L;
                }
            }
            int userOrWorkProfileId = FingerprintService.this.getUserOrWorkProfileId(i);
            ServiceProvider serviceProvider = (ServiceProvider) singleProvider.second;
            int intValue = ((Integer) singleProvider.first).intValue();
            FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
            long incrementAndGet = fingerprintProvider.mRequestCounter.incrementAndGet();
            fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda25(fingerprintProvider, intValue, iBinder, incrementAndGet, iFingerprintServiceReceiver, userOrWorkProfileId, bArr, str, i2, fingerprintEnrollOptions));
            return incrementAndGet;
        }

        public final void generateChallenge(final IBinder iBinder, final int i, final int i2, final IFingerprintServiceReceiver iFingerprintServiceReceiver, final String str) {
            generateChallenge_enforcePermission();
            if (Utils.DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "generateChallenge: ", ", ", ", "), str, "FingerprintService");
            }
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "No matching sensor for generateChallenge, sensorId: ", "FingerprintService");
            } else {
                final FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
                fingerprintProvider.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintProvider fingerprintProvider2 = FingerprintProvider.this;
                        int i3 = i;
                        IBinder iBinder2 = iBinder;
                        IFingerprintServiceReceiver iFingerprintServiceReceiver2 = iFingerprintServiceReceiver;
                        int i4 = i2;
                        String str2 = str;
                        fingerprintProvider2.getClass();
                        fingerprintProvider2.scheduleForSensor$1(i3, new FingerprintGenerateChallengeClient(fingerprintProvider2.mContext, ((Sensor) fingerprintProvider2.mFingerprintSensors.mSensors.get(i3)).mLazySession, iBinder2, new ClientMonitorCallbackConverter(iFingerprintServiceReceiver2), i4, str2, i3, fingerprintProvider2.createLogger$1(0, 0, fingerprintProvider2.mAuthenticationStatsCollector), fingerprintProvider2.mBiometricContext));
                    }
                });
            }
        }

        public final long getAuthenticatorId(int i, int i2) {
            getAuthenticatorId_enforcePermission();
            if (Utils.DEBUG) {
                ASKSManagerService$$ExternalSyntheticOutline0.m(i, i2, "getAuthenticatorId: ", ", ", "FingerprintService");
            }
            int userOrWorkProfileId = FingerprintService.this.getUserOrWorkProfileId(i2);
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider != null) {
                return ((FingerprintProvider) serviceProvider).getAuthenticatorId(i, userOrWorkProfileId);
            }
            Slog.w("FingerprintService", "Null provider for getAuthenticatorId");
            return 0L;
        }

        public final List getEnrolledFingerprints(int i, String str, String str2) {
            if (!FingerprintService.m315$$Nest$mcanUseFingerprint(FingerprintService.this, str, str2, false, Binder.getCallingUid(), Binder.getCallingPid(), UserHandle.getCallingUserId())) {
                return Collections.emptyList();
            }
            if (i != UserHandle.getCallingUserId()) {
                Utils.checkPermission(FingerprintService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
            }
            return FingerprintService.m316$$Nest$mgetEnrolledFingerprintsDeprecated(FingerprintService.this, FingerprintService.this.getUserOrWorkProfileId(i), str);
        }

        public final int getLockoutModeForUser(int i, int i2) {
            getLockoutModeForUser_enforcePermission();
            if (Utils.DEBUG) {
                ASKSManagerService$$ExternalSyntheticOutline0.m(i, i2, "getLockoutModeForUser: ", ", ", "FingerprintService");
            }
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider != null) {
                return ((FingerprintProvider) serviceProvider).getLockoutModeForUser(i, i2);
            }
            Slog.w("FingerprintService", "Null provider for getLockoutModeForUser");
            return 0;
        }

        public final FingerprintSensorPropertiesInternal getSensorProperties(int i, String str) {
            getSensorProperties_enforcePermission();
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider != null) {
                return ((FingerprintProvider) serviceProvider).getSensorProperties(i);
            }
            Slog.w("FingerprintService", "No matching sensor for getSensorProperties, sensorId: " + i + ", caller: " + str);
            return null;
        }

        public final List getSensorPropertiesInternal(String str) {
            if (FingerprintService.this.getContext().checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") != 0) {
                Utils.checkPermission(FingerprintService.this.getContext(), "android.permission.TEST_BIOMETRIC");
            }
            return FingerprintService.this.mRegistry.getAllProperties();
        }

        public final boolean hasEnrolledFingerprints(int i, int i2, String str) {
            hasEnrolledFingerprints_enforcePermission();
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider != null) {
                return FingerprintUtils.getInstance(i).getBiometricsForUser(((FingerprintProvider) serviceProvider).mContext, FingerprintService.this.getUserOrWorkProfileId(i2)).size() > 0;
            }
            HeimdAllFsService$$ExternalSyntheticOutline0.m("Null provider for hasEnrolledFingerprints, caller: ", str, "FingerprintService");
            return false;
        }

        public final boolean hasEnrolledFingerprintsDeprecated(int i, String str, String str2) {
            if (!FingerprintService.m315$$Nest$mcanUseFingerprint(FingerprintService.this, str, str2, false, Binder.getCallingUid(), Binder.getCallingPid(), UserHandle.getCallingUserId())) {
                return false;
            }
            if (i != UserHandle.getCallingUserId()) {
                Utils.checkPermission(FingerprintService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
            }
            return !FingerprintService.m316$$Nest$mgetEnrolledFingerprintsDeprecated(FingerprintService.this, FingerprintService.this.getUserOrWorkProfileId(i), str).isEmpty();
        }

        public final void invalidateAuthenticatorId(int i, int i2, IInvalidationCallback iInvalidationCallback) {
            invalidateAuthenticatorId_enforcePermission();
            if (Utils.DEBUG) {
                ASKSManagerService$$ExternalSyntheticOutline0.m(i, i2, "invalidateAuthenticatorId: ", ", ", "FingerprintService");
            }
            int userOrWorkProfileId = FingerprintService.this.getUserOrWorkProfileId(i2);
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider == null) {
                Slog.w("FingerprintService", "Null provider for invalidateAuthenticatorId");
            } else {
                FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
                fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda7(fingerprintProvider, i, userOrWorkProfileId, iInvalidationCallback, 0));
            }
        }

        public final boolean isClientActive() {
            isClientActive_enforcePermission();
            return FingerprintService.this.mGestureAvailabilityDispatcher.mIsActive;
        }

        public final boolean isHardwareDetected(int i, String str) {
            isHardwareDetected_enforcePermission();
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider != null) {
                return ((FingerprintProvider) serviceProvider).isHardwareDetected(i);
            }
            HeimdAllFsService$$ExternalSyntheticOutline0.m("Null provider for isHardwareDetected, caller: ", str, "FingerprintService");
            return false;
        }

        public final boolean isHardwareDetectedDeprecated(String str, String str2) {
            if (!FingerprintService.m315$$Nest$mcanUseFingerprint(FingerprintService.this, str, str2, false, Binder.getCallingUid(), Binder.getCallingPid(), UserHandle.getCallingUserId())) {
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
                if (singleProvider != null) {
                    return ((FingerprintProvider) ((ServiceProvider) singleProvider.second)).isHardwareDetected(((Integer) singleProvider.first).intValue());
                }
                Slog.w("FingerprintService", "Null provider for isHardwareDetectedDeprecated, caller: " + str);
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void onPointerDown(long j, int i, PointerContext pointerContext) {
            onPointerDown_enforcePermission();
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "No matching provider for onFingerDown, sensorId: ", "FingerprintService");
            } else {
                ((FingerprintProvider) serviceProvider).onPointerDown(j, i, pointerContext);
            }
        }

        public final void onPointerUp(long j, int i, PointerContext pointerContext) {
            onPointerUp_enforcePermission();
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "No matching provider for onFingerUp, sensorId: ", "FingerprintService");
            } else {
                ((FingerprintProvider) serviceProvider).onPointerUp(j, i, pointerContext);
            }
        }

        public final void onPowerPressed() {
            onPowerPressed_enforcePermission();
            FingerprintService.this.mHandler.post(new FingerprintService$1$$ExternalSyntheticLambda0(this, 1));
        }

        public final void onPowerSinglePressed() {
            onPowerSinglePressed_enforcePermission();
            FingerprintService.this.mHandler.post(new FingerprintService$1$$ExternalSyntheticLambda0(this, 0));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            FingerprintService.this.getContext();
            new FingerprintShellCommand(FingerprintService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final void onUdfpsUiEvent(int i, long j, int i2) {
            onUdfpsUiEvent_enforcePermission();
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i2);
            if (serviceProvider == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i2, "No matching provider for onUdfpsUiEvent, sensorId: ", "FingerprintService");
            } else {
                ((FingerprintProvider) serviceProvider).onUdfpsUiEvent(i, j, i2);
            }
        }

        public final void prepareForAuthentication(IBinder iBinder, long j, IBiometricSensorReceiver iBiometricSensorReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, int i, boolean z, boolean z2) {
            prepareForAuthentication_enforcePermission();
            if (Utils.DEBUG) {
                HermesService$3$$ExternalSyntheticOutline0.m(i, "prepareForAuthentication: ", "FingerprintService");
            }
            int userOrWorkProfileId = FingerprintService.this.getUserOrWorkProfileId(fingerprintAuthenticateOptions.getUserId());
            FingerprintAuthenticateOptions copyOptions = userOrWorkProfileId != fingerprintAuthenticateOptions.getUserId() ? FingerprintUtils.copyOptions(userOrWorkProfileId, fingerprintAuthenticateOptions) : fingerprintAuthenticateOptions;
            Pair canUseFingerprint = FingerprintService.this.mServiceExtImpl.canUseFingerprint(true, false);
            if (((Integer) canUseFingerprint.first).intValue() != 0) {
                try {
                    iBiometricSensorReceiver.onError(copyOptions.getSensorId(), i, ((Integer) canUseFingerprint.first).intValue(), ((Integer) canUseFingerprint.second).intValue());
                    return;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return;
                }
            }
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(copyOptions.getSensorId());
            if (serviceProvider == null) {
                Slog.w("FingerprintService", "Null provider for prepareForAuthentication");
                return;
            }
            FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
            fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda18(fingerprintProvider, copyOptions, iBinder, j2, new ClientMonitorCallbackConverter(iBiometricSensorReceiver), j, true, i, z2 ? 3 : 2, z, new Bundle()));
        }

        public final void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) {
            registerAuthenticationStateListener_enforcePermission();
            AuthenticationStateListeners authenticationStateListeners = FingerprintService.this.mAuthenticationStateListeners;
            authenticationStateListeners.mAuthenticationStateListeners.add(authenticationStateListener);
            try {
                authenticationStateListener.asBinder().linkToDeath(authenticationStateListeners, 0);
            } catch (RemoteException e) {
                Slog.e("AuthenticationStateListeners", "Failed to link to death", e);
            }
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.biometrics.sensors.fingerprint.FingerprintService$1$$ExternalSyntheticLambda2] */
        public final void registerAuthenticators(final FingerprintSensorConfigurations fingerprintSensorConfigurations) {
            registerAuthenticators_enforcePermission();
            if (!fingerprintSensorConfigurations.hasSensorConfigurations()) {
                Slog.d("FingerprintService", "No fingerprint sensors available.");
                return;
            }
            final FingerprintServiceRegistry fingerprintServiceRegistry = FingerprintService.this.mRegistry;
            final ?? r1 = new Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    Pair pair;
                    String sensorNameNotForInstance;
                    Pair pair2;
                    FingerprintService.AnonymousClass1 anonymousClass1 = FingerprintService.AnonymousClass1.this;
                    FingerprintSensorConfigurations fingerprintSensorConfigurations2 = fingerprintSensorConfigurations;
                    FingerprintService fingerprintService = FingerprintService.this;
                    fingerprintService.getClass();
                    ArrayList arrayList = new ArrayList();
                    String sensorInstance = fingerprintSensorConfigurations2.getSensorInstance();
                    if (fingerprintSensorConfigurations2.isSingleSensorConfigurationPresent()) {
                        pair = new Pair(sensorInstance, fingerprintSensorConfigurations2.getSensorPropForInstance(sensorInstance));
                    } else {
                        boolean doesInstanceExist = fingerprintSensorConfigurations2.doesInstanceExist("virtual");
                        if (Utils.isFingerprintVirtualEnabled(fingerprintService.getContext())) {
                            if (doesInstanceExist) {
                                pair2 = new Pair("virtual", fingerprintSensorConfigurations2.getSensorPropForInstance("virtual"));
                                pair = pair2;
                            } else {
                                Slog.e("FingerprintService", "Could not find virtual interface while it is enabled");
                                pair = new Pair(sensorInstance, fingerprintSensorConfigurations2.getSensorPropForInstance(sensorInstance));
                            }
                        } else if (!doesInstanceExist || (sensorNameNotForInstance = fingerprintSensorConfigurations2.getSensorNameNotForInstance("virtual")) == null) {
                            pair = new Pair(sensorInstance, fingerprintSensorConfigurations2.getSensorPropForInstance(sensorInstance));
                        } else {
                            pair2 = new Pair(sensorNameNotForInstance, fingerprintSensorConfigurations2.getSensorPropForInstance(sensorNameNotForInstance));
                            pair = pair2;
                        }
                    }
                    boolean resetLockoutRequiresHardwareAuthToken = fingerprintSensorConfigurations2.getResetLockoutRequiresHardwareAuthToken();
                    FingerprintService fingerprintService2 = ((FingerprintService$$ExternalSyntheticLambda0) fingerprintService.mFingerprintProviderFunction).f$0;
                    fingerprintService2.getClass();
                    arrayList.add(new FingerprintProvider(fingerprintService2.getContext(), fingerprintService2.mBiometricStateCallback, fingerprintService2.mAuthenticationStateListeners, (SensorProps[]) pair.second, (String) pair.first, fingerprintService2.mLockoutResetDispatcher, fingerprintService2.mGestureAvailabilityDispatcher, fingerprintService2.mBiometricContext, null, BiometricHandlerProvider.sBiometricHandlerProvider, resetLockoutRequiresHardwareAuthToken, false));
                    return arrayList;
                }
            };
            fingerprintServiceRegistry.getClass();
            BiometricHandlerProvider.sBiometricHandlerProvider.getFingerprintHandler().post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintServiceRegistry$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FingerprintServiceRegistry.this.registerAllInBackground(r1);
                }
            });
        }

        public final void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) {
            registerBiometricStateListener_enforcePermission();
            FingerprintService.this.mBiometricStateCallback.registerBiometricStateListener(iBiometricStateListener);
        }

        public final void remove(IBinder iBinder, int i, int i2, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) {
            remove_enforcePermission();
            Slog.d("FingerprintService", "remove: " + i2 + ", " + str);
            Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FingerprintService", "Null provider for remove");
                return;
            }
            FingerprintProvider fingerprintProvider = (FingerprintProvider) ((ServiceProvider) singleProvider.second);
            fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda13(fingerprintProvider, ((Integer) singleProvider.first).intValue(), iBinder, iFingerprintServiceReceiver, new int[]{i}, i2, str));
        }

        public final void removeAll(IBinder iBinder, int i, final IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) {
            removeAll_enforcePermission();
            Slog.d("FingerprintService", "removeAll: " + i + ", " + str);
            IFingerprintServiceReceiver iFingerprintServiceReceiver2 = new FingerprintServiceReceiver(this) { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService.1.2
                public final int numSensors;
                public int sensorsFinishedRemoving = 0;

                {
                    this.numSensors = this.getSensorPropertiesInternal(FingerprintService.this.getContext().getOpPackageName()).size();
                }

                public final void onRemoved(Fingerprint fingerprint, int i2) {
                    if (i2 == 0) {
                        this.sensorsFinishedRemoving++;
                        StringBuilder sb = new StringBuilder("sensorsFinishedRemoving: ");
                        sb.append(this.sensorsFinishedRemoving);
                        sb.append(", numSensors: ");
                        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, this.numSensors, "FingerprintService");
                        if (this.sensorsFinishedRemoving == this.numSensors) {
                            iFingerprintServiceReceiver.onRemoved((Fingerprint) null, 0);
                        }
                    }
                }
            };
            Iterator it = FingerprintService.this.mRegistry.getProviders().iterator();
            while (it.hasNext()) {
                FingerprintProvider fingerprintProvider = (FingerprintProvider) ((ServiceProvider) it.next());
                Iterator it2 = ((ArrayList) fingerprintProvider.getSensorProperties()).iterator();
                while (it2.hasNext()) {
                    fingerprintProvider.scheduleRemoveAll(iBinder, ((FingerprintSensorPropertiesInternal) it2.next()).sensorId, i, iFingerprintServiceReceiver2, str);
                }
            }
        }

        public final void removeClientActiveCallback(IFingerprintClientActiveCallback iFingerprintClientActiveCallback) {
            removeClientActiveCallback_enforcePermission();
            FingerprintService.this.mGestureAvailabilityDispatcher.mClientActiveCallbacks.remove(iFingerprintClientActiveCallback);
        }

        public final void rename(int i, int i2, String str) {
            rename_enforcePermission();
            if (Utils.isCurrentUserOrProfile(FingerprintService.this.getContext(), i2)) {
                Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
                if (singleProvider == null) {
                    Slog.w("FingerprintService", "Null provider for rename");
                    return;
                }
                ServiceProvider serviceProvider = (ServiceProvider) singleProvider.second;
                int intValue = ((Integer) singleProvider.first).intValue();
                FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
                fingerprintProvider.getClass();
                FingerprintUtils fingerprintUtils = FingerprintUtils.getInstance(intValue);
                Context context = fingerprintProvider.mContext;
                fingerprintUtils.getClass();
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                FingerprintUserState stateForUser = fingerprintUtils.getStateForUser(context, i2);
                synchronized (stateForUser) {
                    int i3 = 0;
                    while (true) {
                        try {
                            if (i3 >= stateForUser.mBiometrics.size()) {
                                break;
                            }
                            if (((BiometricAuthenticator.Identifier) stateForUser.mBiometrics.get(i3)).getBiometricId() == i) {
                                ((BiometricAuthenticator.Identifier) stateForUser.mBiometrics.get(i3)).setName(str);
                                AsyncTask.execute(stateForUser.mWriteStateRunnable);
                                break;
                            }
                            i3++;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
        }

        public final void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) {
            resetLockout_enforcePermission();
            if (Utils.DEBUG) {
                BootReceiver$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "resetLockout: ", ", ", ", "), str, "FingerprintService");
            }
            int userOrWorkProfileId = FingerprintService.this.getUserOrWorkProfileId(i2);
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider == null) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("Null provider for resetLockout, caller: ", str, "FingerprintService");
            } else {
                FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
                fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda7(fingerprintProvider, i, userOrWorkProfileId, bArr, 1));
            }
        }

        public final void revokeChallenge(IBinder iBinder, int i, int i2, String str, long j) {
            revokeChallenge_enforcePermission();
            if (Utils.DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "revokeChallenge: ", ", ", ", "), str, "FingerprintService");
            }
            if (!FingerprintService.this.mServiceExtImpl.mEnrollSessionMonitor.isEnrollSession(i)) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "No enroll session, sensorId: ", "FingerprintService");
                return;
            }
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "No matching sensor for revokeChallenge, sensorId: ", "FingerprintService");
            } else {
                FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
                fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda10(fingerprintProvider, i, iBinder, i2, str, j));
            }
        }

        public final void scheduleWatchdog() {
            scheduleWatchdog_enforcePermission();
            if (Utils.DEBUG) {
                Slog.d("FingerprintService", "scheduleWatchdog");
            }
            Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FingerprintService", "Null provider for scheduling watchdog");
                return;
            }
            ((FingerprintProvider) ((ServiceProvider) singleProvider.second)).scheduleWatchdog(((Integer) singleProvider.first).intValue());
        }

        public final IBinder semAddMaskView(final IBinder iBinder, final String str) {
            semAddMaskView_enforcePermission();
            if (iBinder != null && str != null) {
                final SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
                semFingerprintServiceExtImpl.getClass();
                Slog.i("FingerprintService.Ext", "addMaskView: ".concat(str));
                if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
                    SemBiometricSysUiManager semBiometricSysUiManager = SemBiometricSysUiManager.sInstance;
                    semBiometricSysUiManager.mHandler.removeCallbacks(semBiometricSysUiManager.mRunnableHandleUnbind);
                    semFingerprintServiceExtImpl.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl2 = SemFingerprintServiceExtImpl.this;
                            IBinder iBinder2 = iBinder;
                            String str2 = str;
                            semFingerprintServiceExtImpl2.mInjector.getClass();
                            boolean z = SemUdfpsHelper.DEBUG;
                            SemUdfpsOpticalHelper semUdfpsOpticalHelper = SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl;
                            synchronized (semUdfpsOpticalHelper.mMaskClientList) {
                                try {
                                    if (((HashMap) semUdfpsOpticalHelper.mMaskClientList).containsKey(iBinder2)) {
                                        Slog.i("FingerprintService", "addMaskView: already registered client: [" + iBinder2 + "], [" + str2 + "]");
                                    } else {
                                        SemFpOpticalClient semFpOpticalClient = new SemFpOpticalClient(iBinder2, str2, false, null);
                                        ((HashMap) semUdfpsOpticalHelper.mMaskClientList).put(iBinder2, semFpOpticalClient);
                                        semFpOpticalClient.start(new SemUdfpsOpticalHelper$$ExternalSyntheticLambda5(semUdfpsOpticalHelper));
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    });
                }
            }
            return iBinder;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final long semAuthenticate(IBinder iBinder, long j, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, Bundle bundle) {
            boolean z;
            Pair pair;
            boolean z2;
            int userOrWorkProfileId;
            boolean z3;
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int callingUserId = UserHandle.getCallingUserId();
            String opPackageName = fingerprintAuthenticateOptions.getOpPackageName();
            String attributionTag = fingerprintAuthenticateOptions.getAttributionTag();
            int userId = fingerprintAuthenticateOptions.getUserId();
            Slog.i("FingerprintService", "semAuthenticate: [" + userId + "] from pkg=" + opPackageName);
            Bundle bundle2 = bundle == null ? new Bundle() : bundle;
            Object obj = FingerprintService.this.mAuthenticationSyncLock;
            synchronized (obj) {
                try {
                    if (!FingerprintService.m315$$Nest$mcanUseFingerprint(FingerprintService.this, opPackageName, attributionTag, true, callingUid, callingPid, callingUserId)) {
                        Slog.w("FingerprintService", "Authenticate rejecting package: " + opPackageName);
                        return -1L;
                    }
                    SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
                    Pair pair2 = semFingerprintServiceExtImpl.mIFAAFlag;
                    if (pair2 != null && ((String) pair2.first).contentEquals(opPackageName)) {
                        Slog.i("FingerprintService.Ext", "IFAA: " + opPackageName);
                        bundle2.putInt("EXTRA_KEY_AUTH_FLAG", ((Integer) semFingerprintServiceExtImpl.mIFAAFlag.second).intValue());
                        semFingerprintServiceExtImpl.mIFAAFlag = null;
                    }
                    boolean isKeyguard = Utils.isKeyguard(FingerprintService.this.getContext(), opPackageName);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            boolean z4 = bundle2.getBoolean("EXTRA_KEY_ALLOW_EVEN_IF_ENCRYPTED_OR_LOCKDOWN");
                            if (isKeyguard) {
                                int strongAuthForUser = FingerprintService.this.mLockPatternUtils.getStrongAuthForUser(userId);
                                boolean z5 = (strongAuthForUser & 1) != 0;
                                if ((strongAuthForUser & 2) == 0 && (strongAuthForUser & 32) == 0) {
                                    z3 = false;
                                    attributionTag = "BiometricUtils";
                                    Slog.d("BiometricUtils", "isEncrypted: " + z5 + " isLockdown: " + z3);
                                    if ((!z5 || z3) && !z4) {
                                        EventLog.writeEvent(1397638484, "79776455");
                                        Slog.e("FingerprintService", "Authenticate invoked when user is encrypted or lockdown");
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        return -1L;
                                    }
                                }
                                z3 = true;
                                attributionTag = "BiometricUtils";
                                Slog.d("BiometricUtils", "isEncrypted: " + z5 + " isLockdown: " + z3);
                                if (!z5) {
                                }
                                EventLog.writeEvent(1397638484, "79776455");
                                Slog.e("FingerprintService", "Authenticate invoked when user is encrypted or lockdown");
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return -1L;
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            Pair canUseFingerprint = FingerprintService.this.mServiceExtImpl.canUseFingerprint(true, isKeyguard);
                            if (((Integer) canUseFingerprint.first).intValue() != 0) {
                                try {
                                    iFingerprintServiceReceiver.onError(((Integer) canUseFingerprint.first).intValue(), ((Integer) canUseFingerprint.second).intValue());
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                                return 0L;
                            }
                            boolean z6 = (Utils.hasPermission(FingerprintService.this.getContext(), "com.samsung.android.permission.FINGERPRINT_PRIVILEGED") || Utils.hasPermission(FingerprintService.this.getContext(), "android.permission.MANAGE_BIOMETRIC") || Utils.hasPermission(FingerprintService.this.getContext(), "com.samsung.android.permission.BIOMETRICS_PRIVILEGED")) ? false : true;
                            if (z6) {
                                bundle2.remove("sem_privileged_attr");
                                z = false;
                                attributionTag = null;
                            } else {
                                attributionTag = null;
                                z = (bundle2.getInt("sem_privileged_attr", 0) & 4) != 0;
                            }
                            int i = isKeyguard ? 1 : 3;
                            if (fingerprintAuthenticateOptions.getSensorId() == -1) {
                                pair = FingerprintService.this.mRegistry.getSingleProvider();
                            } else {
                                Utils.checkPermission(FingerprintService.this.getContext(), "android.permission.USE_BIOMETRIC_INTERNAL");
                                pair = new Pair(Integer.valueOf(fingerprintAuthenticateOptions.getSensorId()), (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(fingerprintAuthenticateOptions.getSensorId()));
                            }
                            if (pair == null || pair.second == null) {
                                Slog.w("FingerprintService", "Null provider for authenticate");
                                return -1L;
                            }
                            fingerprintAuthenticateOptions.setSensorId(((Integer) pair.first).intValue());
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                VirtualDeviceManagerInternal virtualDeviceManagerInternal = (VirtualDeviceManagerInternal) FingerprintService.this.getLocalService(VirtualDeviceManagerInternal.class);
                                if (virtualDeviceManagerInternal != null) {
                                    virtualDeviceManagerInternal.onAuthenticationPrompt(callingUid);
                                }
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                if (isKeyguard) {
                                    if (FingerprintService.this.mServiceExtImpl.mEnrollSessionMonitor.isEnrollSession(fingerprintAuthenticateOptions.getSensorId())) {
                                        FingerprintService.this.mServiceExtImpl.mEnrollSessionMonitor.revokeChallenge(fingerprintAuthenticateOptions.getSensorId());
                                    }
                                }
                                FingerprintAuthenticateOptions copyOptions = (isKeyguard || userId == (userOrWorkProfileId = FingerprintService.this.getUserOrWorkProfileId(userId))) ? fingerprintAuthenticateOptions : FingerprintUtils.copyOptions(userOrWorkProfileId, fingerprintAuthenticateOptions);
                                ServiceProvider serviceProvider = (ServiceProvider) pair.second;
                                ClientMonitorCallbackConverter clientMonitorCallbackConverter = new ClientMonitorCallbackConverter(iFingerprintServiceReceiver);
                                if (!isKeyguard && !z) {
                                    z2 = attributionTag;
                                    FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
                                    long incrementAndGet = fingerprintProvider.mRequestCounter.incrementAndGet();
                                    fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda18(fingerprintProvider, copyOptions, iBinder, incrementAndGet, clientMonitorCallbackConverter, j, z6, 0, i, z2, bundle2));
                                    return incrementAndGet;
                                }
                                z2 = 1;
                                FingerprintProvider fingerprintProvider2 = (FingerprintProvider) serviceProvider;
                                long incrementAndGet2 = fingerprintProvider2.mRequestCounter.incrementAndGet();
                                fingerprintProvider2.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda18(fingerprintProvider2, copyOptions, iBinder, incrementAndGet2, clientMonitorCallbackConverter, j, z6, 0, i, z2, bundle2));
                                return incrementAndGet2;
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    attributionTag = obj;
                    throw th;
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r10v1 */
        /* JADX WARN: Type inference failed for: r10v10 */
        /* JADX WARN: Type inference failed for: r10v11 */
        /* JADX WARN: Type inference failed for: r10v12 */
        /* JADX WARN: Type inference failed for: r10v13 */
        /* JADX WARN: Type inference failed for: r10v14 */
        /* JADX WARN: Type inference failed for: r10v15 */
        /* JADX WARN: Type inference failed for: r10v16 */
        /* JADX WARN: Type inference failed for: r10v17 */
        /* JADX WARN: Type inference failed for: r10v18 */
        /* JADX WARN: Type inference failed for: r10v19 */
        /* JADX WARN: Type inference failed for: r10v20 */
        /* JADX WARN: Type inference failed for: r10v21 */
        /* JADX WARN: Type inference failed for: r10v22 */
        /* JADX WARN: Type inference failed for: r10v3, types: [boolean] */
        /* JADX WARN: Type inference failed for: r10v4 */
        /* JADX WARN: Type inference failed for: r10v5 */
        /* JADX WARN: Type inference failed for: r10v6 */
        /* JADX WARN: Type inference failed for: r10v7 */
        /* JADX WARN: Type inference failed for: r10v8 */
        /* JADX WARN: Type inference failed for: r10v9 */
        public final int semBioSysUiRequest(int i, int i2, long j, String str) {
            boolean z = true;
            semBioSysUiRequest_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.getClass();
            StringBuilder sb = new StringBuilder("handleBioSysUiRequest: ");
            sb.append(i);
            sb.append(", ");
            sb.append(i2);
            BootReceiver$$ExternalSyntheticOutline0.m(sb, ", ", j, ", ");
            sb.append(str);
            Slog.d("FingerprintService.Ext", sb.toString());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            int i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            i3 = 0;
            try {
                if (i != 100) {
                    switch (i) {
                        case 1:
                            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
                                semFingerprintServiceExtImpl.mInjector.getClass();
                                boolean z2 = SemUdfpsHelper.DEBUG;
                                SemUdfpsOpticalHelper semUdfpsOpticalHelper = SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl;
                                if (i2 != 1) {
                                    z = false;
                                }
                                semUdfpsOpticalHelper.setDisplayStateLimit(z);
                                break;
                            }
                            break;
                        case 2:
                            if (i2 != 1) {
                                z = false;
                            }
                            semFingerprintServiceExtImpl.requestDozeMode(z);
                            break;
                        case 3:
                            if (i2 != 1) {
                                z = false;
                            }
                            semFingerprintServiceExtImpl.requestDozeHLPM(z);
                            break;
                        case 4:
                            semFingerprintServiceExtImpl.handleBioSysTspControl(i2, str);
                            break;
                        case 5:
                            if (SemBiometricFeature.FP_FEATURE_HW_LIGHT_SOURCE) {
                                SemFingerprintServiceExtImpl.Injector injector = semFingerprintServiceExtImpl.mInjector;
                                if (i2 != 1) {
                                    injector.getClass();
                                    boolean z3 = SemUdfpsHelper.DEBUG;
                                    SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl.setHwLightMode("0");
                                    break;
                                } else {
                                    injector.getClass();
                                    boolean z4 = SemUdfpsHelper.DEBUG;
                                    SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl.setHwLightMode("1");
                                    break;
                                }
                            }
                            break;
                        case 6:
                            i3 = semFingerprintServiceExtImpl.mIsBouncer.get();
                            break;
                        case 7:
                            semFingerprintServiceExtImpl.handleBioSysOpticalControl(i2, j);
                            break;
                        case 8:
                            if (i2 != 1) {
                                SemBiometricBoostingManager.getInstance().release(semFingerprintServiceExtImpl.mContext, 2);
                                break;
                            } else {
                                int i4 = j <= 0 ? 2000 : (int) j;
                                Slog.d("FingerprintService.Ext", "acquireDvfs: " + i4);
                                SemBiometricBoostingManager.getInstance().acquireDvfs(semFingerprintServiceExtImpl.mContext, 3501, 2, "FINGERPRINT_SERVICE", i4);
                                break;
                            }
                        case 9:
                            semFingerprintServiceExtImpl.handleTouchEvent(i2, j);
                            break;
                        case 10:
                            synchronized (semFingerprintServiceExtImpl.mLockForAodCtrl) {
                                SemFpAodController semFpAodController = semFingerprintServiceExtImpl.mAodController;
                                if (semFpAodController != null) {
                                    semFpAodController.mH.post(new SemFpAodController$$ExternalSyntheticLambda0(semFpAodController, 1));
                                }
                            }
                            break;
                        case 11:
                            semFingerprintServiceExtImpl.mIconDrawnTime = j;
                            break;
                        case 12:
                            semFingerprintServiceExtImpl.mInjector.getClass();
                            SemBiometricSysUiManager semBiometricSysUiManager = SemBiometricSysUiManager.sInstance;
                            if (i2 != 1) {
                                z = false;
                            }
                            semBiometricSysUiManager.mKeepBind.set(z);
                            if (!z) {
                                semBiometricSysUiManager.mHandler.postDelayed(semBiometricSysUiManager.mRunnableHandleUnbind, 4000L);
                                break;
                            }
                            break;
                        case 13:
                            SemFpLocalHbmOpticalController semFpLocalHbmOpticalController = semFingerprintServiceExtImpl.mLocalHbmController;
                            if (semFpLocalHbmOpticalController != null) {
                                semFpLocalHbmOpticalController.handleLocalHbm(i2);
                                break;
                            }
                            break;
                    }
                } else {
                    semFingerprintServiceExtImpl.handleQcomForceQDB();
                }
                return i3;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean semCanChangeDeviceColorMode() {
            semCanChangeDeviceColorMode_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.getClass();
            boolean z = true;
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
                semFingerprintServiceExtImpl.mInjector.getClass();
                boolean z2 = SemUdfpsHelper.DEBUG;
                SemUdfpsOpticalHelper.DisplayAdjustmentManager displayAdjustmentManager = SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl.mDisplayAdjManager;
                if (displayAdjustmentManager != null) {
                    synchronized (SemUdfpsOpticalHelper.DisplayAdjustmentManager.class) {
                        z = true ^ displayAdjustmentManager.mIsDisabled;
                    }
                }
            }
            return z;
        }

        public final void semForceCBGE() {
            semForceCBGE_enforcePermission();
            Pair singleProvider = FingerprintService.this.mServiceExtImpl.mRegistry.getSingleProvider();
            if (singleProvider != null) {
                ((FingerprintProvider) ((ServiceProvider) singleProvider.second)).semRequest(((Integer) singleProvider.first).intValue(), 21, 0, null, null);
            }
        }

        public final String semGetDaemonVersion() {
            semGetDaemonVersion_enforcePermission();
            ServiceProvider serviceProvider = FingerprintService.this.mServiceExtImpl.getServiceProvider();
            return serviceProvider != null ? ((FingerprintProvider) serviceProvider).semGetDaemonSdkVersion() : "";
        }

        public final int semGetIconBottomMargin() {
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.getClass();
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                ServiceProvider serviceProvider = semFingerprintServiceExtImpl.getServiceProvider();
                if ((serviceProvider != null ? ((FingerprintProvider) serviceProvider).semGetCurrentClient() : null) instanceof AcquisitionClient) {
                    semFingerprintServiceExtImpl.mInjector.getClass();
                    boolean z = SemUdfpsHelper.DEBUG;
                    SemUdfpsHelper semUdfpsHelper = SemUdfpsHelper.InstanceHolder.INSTANCE;
                    Context context = semFingerprintServiceExtImpl.mContext;
                    Rect fodSensorAreaRect = semUdfpsHelper.getFodSensorAreaRect(context, -1, null);
                    WindowManager windowManager = (WindowManager) context.getSystemService("window");
                    Point point = new Point();
                    windowManager.getDefaultDisplay().getRealSize(point);
                    return point.y - fodSensorAreaRect.top;
                }
            }
            return 0;
        }

        public final int semGetMaxEnrollmentNumber() {
            List allProperties = FingerprintService.this.mRegistry.getAllProperties();
            if (!allProperties.isEmpty() && allProperties.get(0) != null) {
                return ((FingerprintSensorPropertiesInternal) allProperties.get(0)).maxEnrollmentsPerUser;
            }
            Slog.w("FingerprintService", "Null prop");
            return 4;
        }

        public final int semGetRemainingLockoutTime(int i) {
            semGetRemainingLockoutTime_enforcePermission();
            int userOrWorkProfileId = FingerprintService.this.getUserOrWorkProfileId(i);
            ServiceProvider serviceProvider = FingerprintService.this.mServiceExtImpl.getServiceProvider();
            if (serviceProvider != null) {
                return ((FingerprintProvider) serviceProvider).semGetRemainingLockoutTime(userOrWorkProfileId);
            }
            return 0;
        }

        public final int semGetSecurityLevel() {
            semGetSecurityLevel_enforcePermission();
            ServiceProvider serviceProvider = FingerprintService.this.mServiceExtImpl.getServiceProvider();
            if (serviceProvider != null) {
                return ((FingerprintProvider) serviceProvider).semGetSecurityLevel$1();
            }
            return 1;
        }

        public final Rect semGetSensorAreaInDisplay(int i, int i2, Point point) {
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.getClass();
            if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                return new Rect();
            }
            SemFingerprintServiceExtImpl.Injector injector = semFingerprintServiceExtImpl.mInjector;
            if (i != 0) {
                injector.getClass();
                boolean z = SemUdfpsHelper.DEBUG;
                return SemUdfpsHelper.InstanceHolder.INSTANCE.getFodSensorAreaRect(semFingerprintServiceExtImpl.mContext, i2, point);
            }
            injector.getClass();
            boolean z2 = SemUdfpsHelper.DEBUG;
            SemUdfpsHelper semUdfpsHelper = SemUdfpsHelper.InstanceHolder.INSTANCE;
            Context context = semFingerprintServiceExtImpl.mContext;
            Rect fodSensorAreaRect = semUdfpsHelper.getFodSensorAreaRect(context, -1, null);
            try {
                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                int applyDimension = (((int) TypedValue.applyDimension(5, Float.parseFloat(semUdfpsHelper.mSemSensorActiveArea), displayMetrics)) - ((int) TypedValue.applyDimension(5, Float.parseFloat(semUdfpsHelper.mSemSensorImageSize), displayMetrics))) + ((int) TypedValue.applyDimension(5, Float.parseFloat(semUdfpsHelper.mSemSensorDraggingArea), displayMetrics));
                int rotation = windowManager.getDefaultDisplay().getRotation();
                if (rotation == 0) {
                    fodSensorAreaRect.left -= applyDimension;
                    fodSensorAreaRect.right += applyDimension;
                    fodSensorAreaRect.top -= applyDimension;
                    fodSensorAreaRect.bottom += applyDimension;
                } else if (rotation == 1) {
                    fodSensorAreaRect.left -= applyDimension;
                    fodSensorAreaRect.right += applyDimension;
                    fodSensorAreaRect.bottom += applyDimension;
                    fodSensorAreaRect.top -= applyDimension;
                } else if (rotation == 2) {
                    fodSensorAreaRect.right += applyDimension;
                    fodSensorAreaRect.left -= applyDimension;
                    fodSensorAreaRect.bottom += applyDimension;
                    fodSensorAreaRect.top -= applyDimension;
                } else if (rotation == 3) {
                    fodSensorAreaRect.right += applyDimension;
                    fodSensorAreaRect.left -= applyDimension;
                    fodSensorAreaRect.top -= applyDimension;
                    fodSensorAreaRect.bottom += applyDimension;
                }
            } catch (Exception e) {
                MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("getFodSensorAreaRectForKeyguard: "), "FingerprintService");
            }
            if (!SemUdfpsHelper.DEBUG) {
                return fodSensorAreaRect;
            }
            Slog.d("FingerprintService", "getFodSensorAreaRectForKeyguard: " + fodSensorAreaRect.toShortString());
            return fodSensorAreaRect;
        }

        public final void semGetSensorData(Bundle bundle) {
            byte[] readFile;
            semGetSensorData_enforcePermission();
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                boolean z = SemUdfpsHelper.DEBUG;
                SemUdfpsHelper.InstanceHolder.INSTANCE.getInDisplaySensorArea(bundle);
                return;
            }
            if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_SIDE || (readFile = Utils.readFile(new File("/sys/class/fingerprint/fingerprint/position"))) == null) {
                return;
            }
            try {
                ArrayList arrayList = new ArrayList(Arrays.asList(new String(readFile, StandardCharsets.UTF_8).trim().split("\\,")));
                if (arrayList.size() < 2 || ((String) arrayList.get(0)).equals("NA")) {
                    return;
                }
                if (arrayList.size() == 2) {
                    arrayList.add("0");
                }
                bundle.putStringArray("sem_area", (String[]) arrayList.toArray(new String[arrayList.size()]));
            } catch (Exception e) {
                MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("semGetSensorPosition: "), "FingerprintService");
            }
        }

        public final String semGetSensorInfo() {
            semGetSensorInfo_enforcePermission();
            Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return "";
            }
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            int intValue = ((Integer) singleProvider.first).intValue();
            ServiceProvider serviceProvider = semFingerprintServiceExtImpl.getServiceProvider();
            return serviceProvider != null ? ((FingerprintProvider) serviceProvider).semGetSensorInfo(intValue) : "";
        }

        public final int semGetSensorStatus() {
            int i;
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.getClass();
            if (SemBiometricFeature.FP_FEATURE_USE_AOSP_HAL) {
                return 100040;
            }
            Pair singleProvider = semFingerprintServiceExtImpl.mRegistry.getSingleProvider();
            if (singleProvider != null) {
                i = ((FingerprintProvider) ((ServiceProvider) singleProvider.second)).semRequest(((Integer) singleProvider.first).intValue(), 6, 0, null, null);
            } else {
                i = -2;
            }
            semFingerprintServiceExtImpl.mLatestSensorStatus = i;
            semFingerprintServiceExtImpl.mInjector.getClass();
            SemBioAnalyticsManager semBioAnalyticsManager = (SemBioAnalyticsManager) SemBioAnalyticsManager.sInstance.get();
            if (i != 100042) {
                semBioAnalyticsManager.mIsSensorErrorForDQA = false;
            } else if (!semBioAnalyticsManager.mIsSensorErrorForDQA) {
                String str = "pre:" + semBioAnalyticsManager.mLatestAuthenticatedForDQA + ";gesture:false;first:" + semBioAnalyticsManager.mIsFirstSensorCheckForDQA;
                Log.d("BiometricService.AM", "fpInsertLogSensorStatus: " + i + ", " + str);
                semBioAnalyticsManager.fpInsertLog(new SemBioAnalyticsManager.EventData(-1, 2, "FPST", str));
                semBioAnalyticsManager.mIsSensorErrorForDQA = true;
            }
            semBioAnalyticsManager.mIsFirstSensorCheckForDQA = false;
            return i;
        }

        public final int semGetSensorTestResult(byte[] bArr) {
            semGetSensorTestResult_enforcePermission();
            Pair singleProvider = FingerprintService.this.mServiceExtImpl.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return -2;
            }
            return ((FingerprintProvider) ((ServiceProvider) singleProvider.second)).semRequest(((Integer) singleProvider.first).intValue(), 19, 0, null, bArr);
        }

        public final String semGetTrustAppVersion() {
            String str;
            semGetTrustAppVersion_enforcePermission();
            Pair singleProvider = FingerprintService.this.mServiceExtImpl.mRegistry.getSingleProvider();
            if (singleProvider != null) {
                byte[] bArr = new byte[50];
                int semRequest = ((FingerprintProvider) ((ServiceProvider) singleProvider.second)).semRequest(((Integer) singleProvider.first).intValue(), 10000, 0, null, bArr);
                if (semRequest > 0) {
                    str = 50 >= semRequest ? new String(Arrays.copyOf(bArr, semRequest), StandardCharsets.UTF_8) : "";
                    if (Utils.DEBUG) {
                        Slog.d("FingerprintService.Ext", "TrustApp Version: ".concat(str));
                    }
                    return TextUtils.emptyIfNull(str);
                }
            }
            str = null;
            return TextUtils.emptyIfNull(str);
        }

        public final String[] semGetUserIdList() {
            semGetUserIdList_enforcePermission();
            Pair singleProvider = FingerprintService.this.mServiceExtImpl.mRegistry.getSingleProvider();
            if (singleProvider != null) {
                byte[] bArr = new byte[256];
                int semRequest = ((FingerprintProvider) ((ServiceProvider) singleProvider.second)).semRequest(((Integer) singleProvider.first).intValue(), 12, 0, null, bArr);
                if (semRequest > 0) {
                    return new String(bArr, 0, semRequest, StandardCharsets.UTF_8).split(":");
                }
            }
            return null;
        }

        public final boolean semHasFeature(int i) {
            FingerprintService.this.mServiceExtImpl.getClass();
            if (i == 1) {
                boolean z = SemBiometricFeature.FEATURE_LOGGING_MODE;
                return false;
            }
            if (i == 2) {
                boolean z2 = SemBiometricFeature.FEATURE_LOGGING_MODE;
                return false;
            }
            if (i != 3) {
                return false;
            }
            boolean z3 = SemBiometricFeature.FEATURE_LOGGING_MODE;
            return false;
        }

        public final boolean semIsEnrollSession() {
            semIsEnrollSession_enforcePermission();
            Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return false;
            }
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            return semFingerprintServiceExtImpl.mEnrollSessionMonitor.isEnrollSession(((Integer) singleProvider.first).intValue());
        }

        public final boolean semIsTemplateDbCorrupted() {
            semIsTemplateDbCorrupted_enforcePermission();
            Pair singleProvider = FingerprintService.this.mServiceExtImpl.mRegistry.getSingleProvider();
            if (singleProvider != null) {
                if (((FingerprintProvider) ((ServiceProvider) singleProvider.second)).semRequest(((Integer) singleProvider.first).intValue(), 11, 0, null, new byte[5]) == -1) {
                    return true;
                }
            }
            return false;
        }

        public final void semMoveSensorIconInDisplay(int i, int i2) {
            semMoveSensorIconInDisplay_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            ServiceProvider serviceProvider = semFingerprintServiceExtImpl.getServiceProvider();
            BaseClientMonitor semGetCurrentClient = serviceProvider != null ? ((FingerprintProvider) serviceProvider).semGetCurrentClient() : null;
            if ((semGetCurrentClient instanceof AuthenticationClient) && ((AuthenticationClient) semGetCurrentClient).isKeyguard()) {
                semFingerprintServiceExtImpl.mInjector.getClass();
                SemBiometricSysUiManager semBiometricSysUiManager = SemBiometricSysUiManager.sInstance;
                boolean z = SemUdfpsHelper.DEBUG;
                SemUdfpsHelper semUdfpsHelper = SemUdfpsHelper.InstanceHolder.INSTANCE;
                Context context = semFingerprintServiceExtImpl.mContext;
                Bundle bundle = new Bundle();
                if (semUdfpsHelper.mBurnInHelper == null) {
                    semUdfpsHelper.mBurnInHelper = new SemUdfpsHelper.BurnInHelper(context);
                }
                SemUdfpsHelper.BurnInHelper burnInHelper = semUdfpsHelper.mBurnInHelper;
                int i3 = burnInHelper.mIconLocationIndex + 1;
                burnInHelper.mIconLocationIndex = i3;
                int i4 = burnInHelper.mMaxMovingSize;
                int i5 = i3 % (i4 * i4);
                burnInHelper.mIconLocationIndex = i5;
                int i6 = burnInHelper.mIconArray[i5];
                int i7 = i4 >> 1;
                int i8 = (i6 / i4) - i7;
                burnInHelper.mX = i8;
                burnInHelper.mY = (i6 % i4) - i7;
                bundle.putInt("x", i8);
                bundle.putInt("y", burnInHelper.mY);
                Slog.i("FingerprintService", "getNextPosition: " + burnInHelper.mX + ", " + burnInHelper.mY);
                semBiometricSysUiManager.getClass();
                semBiometricSysUiManager.handleRequest(new SemBiometricSysUiManager$$ExternalSyntheticLambda6(semBiometricSysUiManager, 114, 0, bundle));
                ((SemBioAnalyticsManager) SemBioAnalyticsManager.sInstance.get()).fpInsertLog(2, "FPMV", null);
            }
        }

        public final boolean semOpenSession() {
            semOpenSession_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.getClass();
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
                semFingerprintServiceExtImpl.mHandler.post(new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda6(2, semFingerprintServiceExtImpl));
            }
            for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal : semFingerprintServiceExtImpl.mRegistry.getAllProperties()) {
                int i = fingerprintSensorPropertiesInternal.sensorId;
                SemFpEnrollSessionMonitor semFpEnrollSessionMonitor = semFingerprintServiceExtImpl.mEnrollSessionMonitor;
                if (semFpEnrollSessionMonitor.isEnrollSession(i)) {
                    semFpEnrollSessionMonitor.revokeChallenge(fingerprintSensorPropertiesInternal.sensorId);
                }
            }
            ServiceProvider serviceProvider = semFingerprintServiceExtImpl.getServiceProvider();
            if (serviceProvider == null) {
                return false;
            }
            ((FingerprintProvider) serviceProvider).semOpenTzSession();
            return true;
        }

        public final boolean semPauseEnroll() {
            semPauseEnroll_enforcePermission();
            Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return false;
            }
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            int intValue = ((Integer) singleProvider.first).intValue();
            ServiceProvider serviceProvider = (ServiceProvider) semFingerprintServiceExtImpl.mRegistry.getProviderForSensor(intValue);
            if (serviceProvider == null) {
                return true;
            }
            FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
            fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda9(fingerprintProvider, intValue, 0));
            return true;
        }

        public final int semProcessFido(int i, byte[] bArr, byte[] bArr2, String str) {
            int i2;
            if (!Build.IS_ENG && !Build.IS_USERDEBUG) {
                Utils.checkPermission(FingerprintService.this.getContext(), "com.samsung.android.permission.REQUEST_PROCESS_FIDO");
            }
            Slog.d("FingerprintService", "process FIDO: " + i + ", " + str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Pair singleProvider = FingerprintService.this.mServiceExtImpl.mRegistry.getSingleProvider();
                if (singleProvider != null) {
                    i2 = ((FingerprintProvider) ((ServiceProvider) singleProvider.second)).semProcessFidoCommand(((Integer) singleProvider.first).intValue(), bArr, bArr2);
                } else {
                    i2 = -2;
                }
                return i2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void semRegisterAodController(IBinder iBinder, ISemFingerprintAodController iSemFingerprintAodController) {
            semRegisterAodController_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.getClass();
            Slog.d("FingerprintService.Ext", "registerAodController: " + Binder.getCallingPid());
            synchronized (semFingerprintServiceExtImpl.mLockForAodCtrl) {
                try {
                    if (semFingerprintServiceExtImpl.mAodController == null) {
                        semFingerprintServiceExtImpl.mInjector.getClass();
                        semFingerprintServiceExtImpl.mAodController = SemFingerprintServiceExtImpl.Injector.createAodController();
                    }
                    SemFpAodController semFpAodController = semFingerprintServiceExtImpl.mAodController;
                    semFpAodController.mH.post(new SemFpAodController$$ExternalSyntheticLambda2(semFpAodController, iSemFingerprintAodController, 4));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int semRegisterDisplayBrightnessCallback(ISemBiometricSysUiDisplayBrightnessCallback iSemBiometricSysUiDisplayBrightnessCallback) {
            semRegisterDisplayBrightnessCallback_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.mHandler.post(new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda2(0, semFingerprintServiceExtImpl, iSemBiometricSysUiDisplayBrightnessCallback));
            return semFingerprintServiceExtImpl.mDisplayStateMonitor.mBrightness;
        }

        public final int semRegisterDisplayStateCallback(ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback) {
            semRegisterDisplayStateCallback_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.mHandler.post(new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda2(3, semFingerprintServiceExtImpl, iSemBiometricSysUiDisplayStateCallback));
            return semFingerprintServiceExtImpl.mDisplayStateMonitor.mLogicalDisplayState;
        }

        public final int semRemoveMaskView(IBinder iBinder, String str) {
            semRemoveMaskView_enforcePermission();
            if (iBinder == null || str == null) {
                return -1;
            }
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.getClass();
            Slog.i("FingerprintService.Ext", "removeMaskView: ".concat(str));
            if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
                return 0;
            }
            semFingerprintServiceExtImpl.mHandler.post(new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda2(2, semFingerprintServiceExtImpl, iBinder));
            return 0;
        }

        public final int semRequest(IBinder iBinder, int i, byte[] bArr, byte[] bArr2, int i2, int i3, String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback) {
            int i4;
            long j;
            long elapsedRealtime;
            int userOrWorkProfileId = FingerprintService.this.getUserOrWorkProfileId(i3);
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            Binder.getCallingPid();
            semFingerprintServiceExtImpl.getClass();
            Slog.d("FingerprintService.Ext", "startRequest(" + i + ") called from " + str + ", " + i2 + ", " + userOrWorkProfileId);
            Pair singleProvider = semFingerprintServiceExtImpl.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FingerprintService.Ext", "startRequest: No provider");
                return -1;
            }
            if (i != 1010) {
                Utils.checkPermission(semFingerprintServiceExtImpl.mContext, "android.permission.MANAGE_FINGERPRINT");
                return ((FingerprintProvider) ((ServiceProvider) singleProvider.second)).semRequest(((Integer) singleProvider.first).intValue(), i, i2, bArr, bArr2);
            }
            if (!(((FingerprintProvider) ((ServiceProvider) singleProvider.second)).semGetCurrentClient() instanceof AuthenticationClient)) {
                return 0;
            }
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC) {
                j = semFingerprintServiceExtImpl.mCaptureStartedTime + 10000;
                elapsedRealtime = SystemClock.elapsedRealtime();
            } else {
                if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
                    i4 = 0;
                    return Math.max(i4, 0);
                }
                j = semFingerprintServiceExtImpl.mIconDrawnTime + 10000;
                elapsedRealtime = SystemClock.elapsedRealtime();
            }
            i4 = (int) (j - elapsedRealtime);
            return Math.max(i4, 0);
        }

        public final boolean semResumeEnroll() {
            semResumeEnroll_enforcePermission();
            Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return false;
            }
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            int intValue = ((Integer) singleProvider.first).intValue();
            ServiceProvider serviceProvider = (ServiceProvider) semFingerprintServiceExtImpl.mRegistry.getProviderForSensor(intValue);
            if (serviceProvider == null) {
                return true;
            }
            FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
            fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda9(fingerprintProvider, intValue, 1));
            return true;
        }

        public final int semRunSensorTest(IBinder iBinder, int i, int i2, ISemFingerprintRequestCallback iSemFingerprintRequestCallback) {
            semRunSensorTest_enforcePermission();
            Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return -1;
            }
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            int intValue = ((Integer) singleProvider.first).intValue();
            ServiceProvider serviceProvider = semFingerprintServiceExtImpl.getServiceProvider();
            if (serviceProvider == null) {
                return -2;
            }
            ((FingerprintProvider) serviceProvider).semScheduleSensorTest(intValue, iBinder, new ClientMonitorCallbackConverter(iSemFingerprintRequestCallback));
            return 0;
        }

        public final int semSetCalibrationMode(final IBinder iBinder, final int i, final String str) {
            semSetCalibrationMode_enforcePermission();
            final SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.getClass();
            Slog.d("FingerprintService.Ext", "setCalibrationMode: " + i + " from " + str);
            if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
                return 0;
            }
            semFingerprintServiceExtImpl.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    SemFingerprintServiceExtImpl semFingerprintServiceExtImpl2 = SemFingerprintServiceExtImpl.this;
                    IBinder iBinder2 = iBinder;
                    String str2 = str;
                    int i2 = i;
                    semFingerprintServiceExtImpl2.mInjector.getClass();
                    boolean z = SemUdfpsHelper.DEBUG;
                    SemUdfpsOpticalHelper semUdfpsOpticalHelper = SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl;
                    if (i2 < 1) {
                        SemFpOpticalClient semFpOpticalClient = semUdfpsOpticalHelper.mCalibrationClient;
                        if (semFpOpticalClient == null) {
                            Slog.d("FingerprintService", "handleCalibrationMode: No Calibration Client");
                            return;
                        } else {
                            semFpOpticalClient.stop();
                            semUdfpsOpticalHelper.mCalibrationClient = null;
                            return;
                        }
                    }
                    SemFpOpticalClient semFpOpticalClient2 = semUdfpsOpticalHelper.mCalibrationClient;
                    if (semFpOpticalClient2 != null) {
                        semFpOpticalClient2.stop();
                        semUdfpsOpticalHelper.mCalibrationClient = null;
                    }
                    SemFpOpticalClient semFpOpticalClient3 = new SemFpOpticalClient(iBinder2, str2, true, i2 == 2 ? semUdfpsOpticalHelper.mBrightnessColorForLowBrightness : semUdfpsOpticalHelper.mBrightnessColor);
                    semUdfpsOpticalHelper.mCalibrationClient = semFpOpticalClient3;
                    semFpOpticalClient3.start(null);
                }
            });
            return 0;
        }

        public final void semSetFlagForIFAA(int i, String str) {
            semSetFlagForIFAA_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.getClass();
            Slog.d("FingerprintService.Ext", "setFlagForIFAA: " + i + ", " + str);
            if (TextUtils.isEmpty(str)) {
                return;
            }
            semFingerprintServiceExtImpl.mIFAAFlag = new Pair(str, Integer.valueOf(i));
        }

        public final void semSetFodStrictMode(boolean z) {
            semSetFodStrictMode_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.getClass();
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                DeviceIdleController$$ExternalSyntheticOutline0.m("setFodStrictMode: ", "FingerprintService.Ext", z);
                semFingerprintServiceExtImpl.mInjector.getClass();
                SemUdfpsTspManager semUdfpsTspManager = SemUdfpsTspManager.get();
                synchronized (semUdfpsTspManager) {
                    if (semUdfpsTspManager.mIsInterruptDelayModeOn) {
                        return;
                    }
                    if (semUdfpsTspManager.mIsStrictMode == z) {
                        return;
                    }
                    semUdfpsTspManager.mIsStrictMode = z;
                    semUdfpsTspManager.setFodEnable();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v1 */
        /* JADX WARN: Type inference failed for: r5v2, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r5v3 */
        public final int semSetScreenStatus(int i) {
            semSetScreenStatus_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            ?? r5 = i == 0 ? 1 : 0;
            semFingerprintServiceExtImpl.getClass();
            Slog.i("FingerprintService.Ext", "setScreenStatusFromKeyguard: " + ((boolean) r5));
            SemBiometricSysUiManager semBiometricSysUiManager = SemBiometricSysUiManager.sInstance;
            semBiometricSysUiManager.bind();
            semBiometricSysUiManager.handleRequest(new SemBiometricSysUiManager$$ExternalSyntheticLambda6(semBiometricSysUiManager, 115, r5));
            semFingerprintServiceExtImpl.mHandler.post(new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda1(semFingerprintServiceExtImpl, r5, 0));
            if (r5 != 0) {
                semFingerprintServiceExtImpl.unregisterAodController();
            }
            return 0;
        }

        public final int semShowBouncerScreen(int i) {
            semShowBouncerScreen_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            if (semFingerprintServiceExtImpl.mHasUdfps) {
                semFingerprintServiceExtImpl.mIsBouncer.set(i == 1);
                semFingerprintServiceExtImpl.mInjector.getClass();
                SemBiometricSysUiManager semBiometricSysUiManager = SemBiometricSysUiManager.sInstance;
                semBiometricSysUiManager.getClass();
                semBiometricSysUiManager.handleRequest(new SemBiometricSysUiManager$$ExternalSyntheticLambda6(semBiometricSysUiManager, 117, i, (Bundle) null));
            }
            return 0;
        }

        public final void semShowUdfpsIcon() {
            if (FingerprintService.this.mServiceExtImpl.mHasUdfps) {
                Slog.i("FingerprintService.Ext", "showUdfpsIcon");
                SemBiometricSysUiManager semBiometricSysUiManager = SemBiometricSysUiManager.sInstance;
                semBiometricSysUiManager.bind();
                semBiometricSysUiManager.handleRequest(new SemBiometricSysUiManager$$ExternalSyntheticLambda6(semBiometricSysUiManager, 119, 0));
            }
        }

        public final void semUnregisterAodController(IBinder iBinder) {
            semUnregisterAodController_enforcePermission();
            FingerprintService.this.mServiceExtImpl.unregisterAodController();
        }

        public final void semUnregisterDisplayBrightnessCallback() {
            semUnregisterDisplayBrightnessCallback_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.mHandler.post(new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda6(3, semFingerprintServiceExtImpl));
        }

        public final void semUnregisterDisplayStateCallback() {
            semUnregisterDisplayStateCallback_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            semFingerprintServiceExtImpl.mHandler.post(new SemFingerprintServiceExtImpl$$ExternalSyntheticLambda6(0, semFingerprintServiceExtImpl));
        }

        public final void semUpdateTrustApp(final String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback, final String str2) {
            semUpdateTrustApp_enforcePermission();
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
            if (SemFingerprintServiceExtImpl.DEBUG) {
                semFingerprintServiceExtImpl.getClass();
                Slog.v("FingerprintService.Ext", "updateTrustApp: [" + str + "]");
            }
            Pair singleProvider = semFingerprintServiceExtImpl.mRegistry.getSingleProvider();
            if (singleProvider != null) {
                ServiceProvider serviceProvider = (ServiceProvider) singleProvider.second;
                final int intValue = ((Integer) singleProvider.first).intValue();
                final ClientMonitorCallbackConverter clientMonitorCallbackConverter = new ClientMonitorCallbackConverter(iSemFingerprintRequestCallback);
                final FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
                fingerprintProvider.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintProvider fingerprintProvider2 = FingerprintProvider.this;
                        ClientMonitorCallbackConverter clientMonitorCallbackConverter2 = clientMonitorCallbackConverter;
                        String str3 = str2;
                        String str4 = str;
                        int i = intValue;
                        fingerprintProvider2.getClass();
                        fingerprintProvider2.scheduleForSensor$1(i, new BaseClientMonitor(fingerprintProvider2.mContext, clientMonitorCallbackConverter2, str3, str4, i, fingerprintProvider2.mBiometricContext) { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.9
                            public final int mDataTransmissionUnit;
                            public final int mErrorValue;
                            public final String mPath;
                            public final int mSuccessValue;

                            public AnonymousClass9(Context context, ClientMonitorCallbackConverter clientMonitorCallbackConverter22, String str32, String str42, int i2, BiometricContext biometricContext) {
                                super(context, null, clientMonitorCallbackConverter22, 0, str32, 0, i2, BiometricLogger.ofUnknown(context), biometricContext);
                                this.mDataTransmissionUnit = 921600;
                                this.mPath = str42;
                                this.mErrorValue = -100;
                                this.mSuccessValue = 100;
                                if (Utils.DEBUG) {
                                    DeviceIdleController$$ExternalSyntheticOutline0.m(DumpUtils$$ExternalSyntheticOutline0.m("SemUpdateTrustAppClient: path = ", str42, ", "), this.mDataTransmissionUnit, "Biometrics/SemUpdateTrustAppClient");
                                }
                            }

                            @Override // com.android.server.biometrics.sensors.BaseClientMonitor
                            public final int getProtoEnum() {
                                return 0;
                            }

                            public final void handleUpdate(int i2) {
                                try {
                                    ClientMonitorCallbackConverter clientMonitorCallbackConverter3 = this.mListener;
                                    ISemFingerprintRequestCallback iSemFingerprintRequestCallback2 = clientMonitorCallbackConverter3.mFingerprintRequestReceiver;
                                    if (iSemFingerprintRequestCallback2 != null) {
                                        iSemFingerprintRequestCallback2.onResult(i2);
                                    } else {
                                        IFaceServiceReceiver iFaceServiceReceiver = clientMonitorCallbackConverter3.mFaceServiceReceiver;
                                        if (iFaceServiceReceiver != null) {
                                            iFaceServiceReceiver.onSemStatusUpdate(i2, "");
                                        }
                                    }
                                } catch (RemoteException e) {
                                    Log.w("Biometrics/SemUpdateTrustAppClient", "handleUpdate: " + e.getMessage());
                                }
                                this.mCallback.onClientFinished(this, i2 == this.mSuccessValue);
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Type inference failed for: r0v10, types: [boolean] */
                            /* JADX WARN: Type inference failed for: r0v12, types: [java.lang.StringBuilder] */
                            /* JADX WARN: Type inference failed for: r0v6, types: [java.nio.ByteBuffer] */
                            /* JADX WARN: Type inference failed for: r0v7, types: [java.lang.String] */
                            @Override // com.android.server.biometrics.sensors.BaseClientMonitor
                            public final void start(ClientMonitorCallback clientMonitorCallback) {
                                FileInputStream fileInputStream;
                                int i2;
                                super.start(clientMonitorCallback);
                                Slog.d("Biometrics/SemUpdateTrustAppClient", "startUpdate: start");
                                FileInputStream fileInputStream2 = null;
                                fileInputStream2 = null;
                                try {
                                    File file = new File(this.mPath);
                                    if (file.exists()) {
                                        FileInputStream fileInputStream3 = new FileInputStream(file);
                                        try {
                                            ?? allocate = ByteBuffer.allocate(fileInputStream3.available());
                                            Slog.i("Biometrics/SemUpdateTrustAppClient", "startUpdate: size = " + fileInputStream3.available());
                                            byte[] bArr = new byte[102400];
                                            while (true) {
                                                int read = fileInputStream3.read(bArr);
                                                if (read == -1) {
                                                    break;
                                                } else {
                                                    allocate.put(bArr, 0, read);
                                                }
                                            }
                                            fileInputStream3.close();
                                            Slog.i("Biometrics/SemUpdateTrustAppClient", "startUpdate: done reading file");
                                            if (FingerprintProvider.this.handleRequestCommandWithoutScheduler(this.mSensorId, 10001, 0, null, null) != 0) {
                                                handleUpdate(this.mErrorValue);
                                                fileInputStream2 = allocate;
                                            } else {
                                                if (this.mDataTransmissionUnit == -1) {
                                                    if (FingerprintProvider.this.handleRequestCommandWithoutScheduler(this.mSensorId, 10001, 1, allocate.array(), null) != 0) {
                                                        Slog.w("Biometrics/SemUpdateTrustAppClient", "startUpdate: OPERATION_WRITE error");
                                                        handleUpdate(this.mErrorValue);
                                                        fileInputStream2 = allocate;
                                                    }
                                                } else {
                                                    int ceil = (int) Math.ceil(allocate.position() / this.mDataTransmissionUnit);
                                                    if (Utils.DEBUG) {
                                                        HermesService$3$$ExternalSyntheticOutline0.m(ceil, "startUpdate: loopCnt:", "Biometrics/SemUpdateTrustAppClient");
                                                    }
                                                    allocate.position(0);
                                                    int i3 = 0;
                                                    while (true) {
                                                        if (i3 < ceil) {
                                                            int min = Math.min(allocate.remaining(), this.mDataTransmissionUnit);
                                                            byte[] bArr2 = new byte[min];
                                                            boolean z = Utils.DEBUG;
                                                            if (z) {
                                                                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(min, "startUpdate: read length:", ", byteBuffer.position()");
                                                                m.append(allocate.position());
                                                                m.append(", byteBuffer.remaining():");
                                                                m.append(allocate.remaining());
                                                                Slog.i("Biometrics/SemUpdateTrustAppClient", m.toString());
                                                            }
                                                            allocate.get(bArr2, 0, min);
                                                            if (z) {
                                                                Slog.i("Biometrics/SemUpdateTrustAppClient", "startUpdate: byteBuffer.position() after get()" + allocate.position() + ", byteBuffer.remaining():" + allocate.remaining());
                                                            }
                                                            if (FingerprintProvider.this.handleRequestCommandWithoutScheduler(this.mSensorId, 10001, 1, bArr2, null) != 0) {
                                                                Slog.w("Biometrics/SemUpdateTrustAppClient", "startUpdate: OPERATION_WRITE error");
                                                                handleUpdate(this.mErrorValue);
                                                                fileInputStream2 = allocate;
                                                                break;
                                                            }
                                                            i3++;
                                                        }
                                                    }
                                                }
                                                try {
                                                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                                                    messageDigest.update(allocate.array());
                                                    byte[] digest = messageDigest.digest();
                                                    StringBuilder sb = new StringBuilder();
                                                    int length = digest.length;
                                                    for (i2 = 0; i2 < length; i2 = MARsFreezeStateRecord$$ExternalSyntheticOutline0.m("%02x", new Object[]{Byte.valueOf(digest[i2])}, sb, i2, 1)) {
                                                    }
                                                    ?? r0 = Utils.DEBUG;
                                                    FileInputStream fileInputStream4 = r0;
                                                    if (r0 != 0) {
                                                        ?? sb2 = new StringBuilder("startUpdate: digest:");
                                                        sb2.append(sb.toString().trim());
                                                        Slog.w("Biometrics/SemUpdateTrustAppClient", sb2.toString());
                                                        fileInputStream4 = sb2;
                                                    }
                                                    if (FingerprintProvider.this.handleRequestCommandWithoutScheduler(this.mSensorId, 10001, 2, digest, null) != 0) {
                                                        Slog.w("Biometrics/SemUpdateTrustAppClient", "startUpdate: OPERATION_END error");
                                                        handleUpdate(this.mErrorValue);
                                                        fileInputStream2 = fileInputStream4;
                                                    } else {
                                                        handleUpdate(this.mSuccessValue);
                                                        fileInputStream2 = fileInputStream4;
                                                    }
                                                } catch (NoSuchAlgorithmException e) {
                                                    Slog.w("Biometrics/SemUpdateTrustAppClient", "startUpdate: failure to get MessageDigest instance", e);
                                                    handleUpdate(this.mErrorValue);
                                                    fileInputStream2 = "startUpdate: failure to get MessageDigest instance";
                                                }
                                            }
                                        } catch (Exception e2) {
                                            e = e2;
                                            fileInputStream = fileInputStream3;
                                            Slog.i("Biometrics/SemUpdateTrustAppClient", "startUpdate: failure to read file");
                                            handleUpdate(this.mErrorValue);
                                            if (fileInputStream != null) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (Exception unused) {
                                                    Slog.e("Biometrics/SemUpdateTrustAppClient", "startUpdate: failed to close file", e);
                                                }
                                            }
                                        }
                                    } else {
                                        Slog.w("Biometrics/SemUpdateTrustAppClient", "startUpdate: No file exist");
                                        handleUpdate(this.mErrorValue);
                                    }
                                } catch (Exception e3) {
                                    e = e3;
                                    fileInputStream = fileInputStream2;
                                }
                            }
                        }, new FingerprintProvider.AnonymousClass1(fingerprintProvider2, 1));
                    }
                });
            }
        }

        public final void setIgnoreDisplayTouches(long j, int i, boolean z) {
            setIgnoreDisplayTouches_enforcePermission();
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "No matching provider for setIgnoreDisplayTouches, sensorId: ", "FingerprintService");
            } else {
                ((FingerprintProvider) serviceProvider).setIgnoreDisplayTouches(j, i, z);
            }
        }

        public final void setUdfpsOverlayController(IUdfpsOverlayController iUdfpsOverlayController) {
            setUdfpsOverlayController_enforcePermission();
            if (Utils.DEBUG) {
                Slog.d("FingerprintService", "setUdfpsOverlayController: " + Binder.getCallingPid());
            }
        }

        public final void startPreparedClient(int i, int i2) {
            startPreparedClient_enforcePermission();
            if (Utils.DEBUG) {
                HermesService$3$$ExternalSyntheticOutline0.m(i2, "startPreparedClient: ", "FingerprintService");
            }
            ServiceProvider serviceProvider = (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (serviceProvider == null) {
                Slog.w("FingerprintService", "Null provider for startPreparedClient");
            } else {
                FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
                fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda0(fingerprintProvider, i, i2, 1));
            }
        }

        public final void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) {
            unregisterAuthenticationStateListener_enforcePermission();
            FingerprintService.this.mAuthenticationStateListeners.mAuthenticationStateListeners.remove(authenticationStateListener);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface FingerprintProviderFunction {
    }

    /* renamed from: -$$Nest$mcanUseFingerprint, reason: not valid java name */
    public static boolean m315$$Nest$mcanUseFingerprint(FingerprintService fingerprintService, String str, String str2, boolean z, int i, int i2, int i3) {
        if (fingerprintService.getContext().checkCallingPermission("android.permission.USE_FINGERPRINT") != 0) {
            Utils.checkPermission(fingerprintService.getContext(), "android.permission.USE_BIOMETRIC");
        }
        if (Binder.getCallingUid() == 1000 || Utils.isKeyguard(fingerprintService.getContext(), str)) {
            return true;
        }
        if (!Utils.isCurrentUserOrProfile(fingerprintService.getContext(), i3)) {
            PinnerService$$ExternalSyntheticOutline0.m("Rejecting ", str, "; not a current user or profile", "FingerprintService");
        } else if (fingerprintService.mAppOps.noteOpNoThrow(78, i, str, str2, (String) null) != 0 && fingerprintService.mAppOps.noteOpNoThrow(55, i, str, str2, (String) null) != 0) {
            PinnerService$$ExternalSyntheticOutline0.m("Rejecting ", str, "; permission denied", "FingerprintService");
        } else {
            if (!z || fingerprintService.isForeground(i, i2)) {
                return true;
            }
            PinnerService$$ExternalSyntheticOutline0.m("Rejecting ", str, "; not in foreground", "FingerprintService");
        }
        return false;
    }

    /* renamed from: -$$Nest$mgetEnrolledFingerprintsDeprecated, reason: not valid java name */
    public static List m316$$Nest$mgetEnrolledFingerprintsDeprecated(FingerprintService fingerprintService, int i, String str) {
        Pair singleProvider = fingerprintService.mRegistry.getSingleProvider();
        if (singleProvider == null) {
            Slog.w("FingerprintService", "Null provider for getEnrolledFingerprintsDeprecated, caller: " + str);
            return Collections.emptyList();
        }
        ServiceProvider serviceProvider = (ServiceProvider) singleProvider.second;
        int intValue = ((Integer) singleProvider.first).intValue();
        FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
        fingerprintProvider.getClass();
        return FingerprintUtils.getInstance(intValue).getBiometricsForUser(fingerprintProvider.mContext, i);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FingerprintService(android.content.Context r8) {
        /*
            r7 = this;
            com.android.server.biometrics.log.BiometricContextProvider r2 = com.android.server.biometrics.log.BiometricContext.getInstance(r8)
            com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda1 r3 = new com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda1
            r0 = 0
            r3.<init>()
            com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda1 r4 = new com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda1
            r0 = 1
            r4.<init>()
            r5 = 0
            r6 = 0
            r0 = r7
            r1 = r8
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.sensors.fingerprint.FingerprintService.<init>(android.content.Context):void");
    }

    public FingerprintService(Context context, BiometricContext biometricContext, Supplier supplier, Supplier supplier2, Function function, FingerprintProviderFunction fingerprintProviderFunction) {
        super(context);
        this.mAuthenticationSyncLock = new Object();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mServiceWrapper = anonymousClass1;
        this.mBiometricContext = biometricContext;
        this.mAidlInstanceNameSupplier = supplier2;
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mGestureAvailabilityDispatcher = new GestureAvailabilityDispatcher();
        this.mLockoutResetDispatcher = new LockoutResetDispatcher(context);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mBiometricStateCallback = new BiometricStateCallback(UserManager.get(context));
        this.mAuthenticationStateListeners = new AuthenticationStateListeners();
        this.mFingerprintProviderFunction = fingerprintProviderFunction == null ? new FingerprintService$$ExternalSyntheticLambda0(this) : fingerprintProviderFunction;
        this.mHandler = new Handler(getFpLooper());
        FingerprintServiceRegistry fingerprintServiceRegistry = new FingerprintServiceRegistry(anonymousClass1, supplier);
        this.mRegistry = fingerprintServiceRegistry;
        fingerprintServiceRegistry.addAllRegisteredCallback(new IFingerprintAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService.2
            public final void onAllAuthenticatorsRegistered(List list) {
                boolean z;
                FingerprintService fingerprintService = FingerprintService.this;
                BiometricStateCallback biometricStateCallback = fingerprintService.mBiometricStateCallback;
                List providers = fingerprintService.mRegistry.getProviders();
                synchronized (biometricStateCallback) {
                    biometricStateCallback.mProviders = Collections.unmodifiableList(providers);
                    biometricStateCallback.broadcastCurrentEnrollmentState(null);
                }
                SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = FingerprintService.this.mServiceExtImpl;
                boolean z2 = SemFingerprintServiceExtImpl.DEBUG;
                if (z2) {
                    semFingerprintServiceExtImpl.getClass();
                    Slog.i("FingerprintService.Ext", "onAllAuthenticatorsRegistered: ");
                }
                FingerprintServiceRegistry fingerprintServiceRegistry2 = semFingerprintServiceExtImpl.mRegistry;
                Iterator it = fingerprintServiceRegistry2.getProviders().iterator();
                while (it.hasNext()) {
                    FingerprintProvider fingerprintProvider = (FingerprintProvider) ((ServiceProvider) it.next());
                    fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda1(0, fingerprintProvider, semFingerprintServiceExtImpl));
                    fingerprintProvider.semAddAuthenticationListener(semFingerprintServiceExtImpl);
                    SemFpEnrollSessionMonitor semFpEnrollSessionMonitor = semFingerprintServiceExtImpl.mEnrollSessionMonitor;
                    semFpEnrollSessionMonitor.getClass();
                    SemFpCallbackDispatcher semFpCallbackDispatcher = fingerprintProvider.mCallbackDispatcher;
                    semFpCallbackDispatcher.getClass();
                    semFpCallbackDispatcher.mHandler.post(new SemFpCallbackDispatcher$$ExternalSyntheticLambda0(semFpCallbackDispatcher, semFpEnrollSessionMonitor, 4));
                }
                Iterator it2 = fingerprintServiceRegistry2.getAllProperties().iterator();
                while (true) {
                    z = true;
                    if (!it2.hasNext()) {
                        break;
                    }
                    FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = (FingerprintSensorPropertiesInternal) it2.next();
                    if (fingerprintSensorPropertiesInternal != null) {
                        if (z2) {
                            Slog.i("FingerprintService.Ext", "onAllAuthenticatorsRegistered: prop = " + fingerprintSensorPropertiesInternal);
                        }
                        if (fingerprintSensorPropertiesInternal.isAnyUdfpsType()) {
                            if (fingerprintSensorPropertiesInternal.isUltrasonicType()) {
                                semFingerprintServiceExtImpl.mHasUltrasonicUdfps = true;
                            }
                            if (fingerprintSensorPropertiesInternal.isOpticalType()) {
                                semFingerprintServiceExtImpl.mHasOpticalUdfps = true;
                            }
                        }
                    }
                }
                if (!semFingerprintServiceExtImpl.mHasOpticalUdfps && !semFingerprintServiceExtImpl.mHasUltrasonicUdfps) {
                    z = false;
                }
                semFingerprintServiceExtImpl.mHasUdfps = z;
            }
        });
        this.mServiceExtImpl = new SemFingerprintServiceExtImpl(context, BiometricHandlerProvider.sBiometricHandlerProvider.getFingerprintHandler().getLooper(), fingerprintServiceRegistry, new SemFingerprintServiceExtImpl.Injector());
    }

    public Looper getFpLooper() {
        return BiometricHandlerProvider.sBiometricHandlerProvider.getFingerprintHandler().getLooper();
    }

    public SemFingerprintServiceExtImpl getServiceExtImpl() {
        return this.mServiceExtImpl;
    }

    public int getUserOrWorkProfileId(int i) {
        return Utils.getUserOrWorkProfileId(getContext(), i);
    }

    public boolean isForeground(int i, int i2) {
        return Utils.isForeground(i, i2);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(final int i) {
        super.onBootPhase(i);
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda3
            /* JADX WARN: Removed duplicated region for block: B:115:0x044f A[Catch: Exception -> 0x0420, TryCatch #0 {Exception -> 0x0420, blocks: (B:125:0x0414, B:127:0x0417, B:128:0x0422, B:130:0x0426, B:131:0x042a, B:133:0x042e, B:134:0x0432, B:136:0x0436, B:115:0x044f, B:117:0x046a, B:118:0x046c), top: B:124:0x0414 }] */
            /* JADX WARN: Removed duplicated region for block: B:121:0x0482  */
            /* JADX WARN: Removed duplicated region for block: B:124:0x0414 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 1357
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda3.run():void");
            }
        });
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("fingerprint", this.mServiceWrapper);
    }
}
