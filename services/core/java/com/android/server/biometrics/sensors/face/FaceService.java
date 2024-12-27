package com.android.server.biometrics.sensors.face;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.AuthenticationStateListener;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.biometrics.IBiometricStateListener;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.SensorProps;
import android.hardware.face.Face;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceEnrollOptions;
import android.hardware.face.FaceSensorConfigurations;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.face.FaceServiceReceiver;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import android.hardware.face.IFaceService;
import android.hardware.face.IFaceServiceReceiver;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.NativeHandle;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.Surface;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Flags;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AuthenticationStateListeners;
import com.android.server.biometrics.sensors.BiometricStateCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.face.FaceService;
import com.android.server.biometrics.sensors.face.FaceUtils;
import com.android.server.biometrics.sensors.face.aidl.AidlSession;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda0;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda10;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda12;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda15;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda2;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda5;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda6;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda8;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda9;
import com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl;
import com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import vendor.samsung.hardware.biometrics.face.ISehSession;
import vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace;

public class FaceService extends SystemService {
    public final Supplier mAidlInstanceNameSupplier;
    public final AuthenticationStateListeners mAuthenticationStateListeners;
    public final BiometricStateCallback mBiometricStateCallback;
    public final Function mFaceProvider;
    public final FaceProviderFunction mFaceProviderFunction;
    public final AtomicBoolean mIsEnrollSession;
    public final LockoutResetDispatcher mLockoutResetDispatcher;
    public final FaceServiceRegistry mRegistry;
    final FaceServiceWrapper mServiceWrapper;

    public interface FaceProviderFunction {
    }

    final class FaceServiceWrapper extends IFaceService.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public FaceServiceWrapper() {
        }

        public final void addAuthenticatorsRegisteredCallback(IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) {
            Utils.checkPermission(FaceService.this.getContext(), "android.permission.USE_BIOMETRIC_INTERNAL");
            FaceService.this.mRegistry.addAllRegisteredCallback(iFaceAuthenticatorsRegisteredCallback);
        }

        public final void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) {
            addLockoutResetCallback_enforcePermission();
            StringBuilder sb = new StringBuilder("addLockoutResetCallback called from pid=");
            sb.append(Binder.getCallingPid());
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "", "FaceService");
            FaceService.this.mLockoutResetDispatcher.addCallback(iBiometricServiceLockoutResetCallback, str);
        }

        public final long authenticate(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions) {
            String str;
            if (!Utils.hasPermission(FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
                authenticate_enforcePermission();
            }
            StringBuilder sb = new StringBuilder("authenticate called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(" [");
            sb.append(faceAuthenticateOptions.getUserId());
            sb.append("] ");
            sb.append(faceAuthenticateOptions.getOpPackageName());
            sb.append(", ");
            if (Utils.DEBUG) {
                str = ", " + iBinder + ", " + j;
            } else {
                str = "";
            }
            sb.append(str);
            Slog.i("FaceService", sb.toString());
            String opPackageName = faceAuthenticateOptions.getOpPackageName();
            boolean isKeyguard = Utils.isKeyguard(FaceService.this.getContext(), opPackageName);
            boolean isKeyguard2 = Utils.isKeyguard(FaceService.this.getContext(), opPackageName);
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FaceService", "Null provider for authenticate");
                return -1L;
            }
            faceAuthenticateOptions.setSensorId(((Integer) singleProvider.first).intValue());
            int userOrWorkProfileId = Utils.getUserOrWorkProfileId(FaceService.this.getContext(), faceAuthenticateOptions.getUserId());
            FaceAuthenticateOptions copyOptions = userOrWorkProfileId != faceAuthenticateOptions.getUserId() ? SemFaceUtils.copyOptions(userOrWorkProfileId, faceAuthenticateOptions) : faceAuthenticateOptions;
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            ClientMonitorCallbackConverter clientMonitorCallbackConverter = new ClientMonitorCallbackConverter(iFaceServiceReceiver);
            long incrementAndGet = faceProvider.mRequestCounter.incrementAndGet();
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda10(faceProvider, copyOptions, isKeyguard2, iBinder, incrementAndGet, clientMonitorCallbackConverter, j, false, 0, isKeyguard ? 1 : 0));
            return incrementAndGet;
        }

        public final void cancelAuthentication(IBinder iBinder, String str, long j) {
            String str2;
            if (!Utils.hasPermission(FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
                cancelAuthentication_enforcePermission();
            }
            StringBuilder sb = new StringBuilder("cancelAuthentication called from pid=");
            sb.append(Binder.getCallingPid());
            if (Utils.DEBUG) {
                str2 = ", " + iBinder + ", " + str;
            } else {
                str2 = "";
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, str2, "FaceService");
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FaceService", "Null provider for cancelAuthentication");
                return;
            }
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda5(faceProvider, ((Integer) singleProvider.first).intValue(), iBinder, j, 2));
        }

        public final void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) {
            String str2;
            cancelAuthenticationFromService_enforcePermission();
            StringBuilder sb = new StringBuilder("cancelAuthenticationFromService called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append("[");
            sb.append(i);
            sb.append("] ");
            if (Utils.DEBUG) {
                str2 = ", " + i + ", " + iBinder + ", " + str;
            } else {
                str2 = "";
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, str2, "FaceService");
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider == null) {
                Slog.w("FaceService", "Null provider for cancelAuthenticationFromService");
            } else {
                faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda5(faceProvider, i, iBinder, j, 2));
            }
        }

        public final void cancelEnrollment(IBinder iBinder, long j) {
            String str;
            cancelEnrollment_enforcePermission();
            StringBuilder sb = new StringBuilder("cancelEnrollment called from pid=");
            sb.append(Binder.getCallingPid());
            if (Utils.DEBUG) {
                str = ", " + iBinder;
            } else {
                str = "";
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, str, "FaceService");
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FaceService", "Null provider for cancelEnrollment");
                return;
            }
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda5(faceProvider, ((Integer) singleProvider.first).intValue(), iBinder, j, 1));
        }

        public final void cancelFaceDetect(IBinder iBinder, String str, long j) {
            String str2;
            cancelFaceDetect_enforcePermission();
            StringBuilder sb = new StringBuilder("cancelFaceDetectcalled from pid=");
            sb.append(Binder.getCallingPid());
            if (Utils.DEBUG) {
                str2 = ", " + iBinder + ", " + str;
            } else {
                str2 = "";
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, str2, "FaceService");
            if (!Utils.isKeyguard(FaceService.this.getContext(), str)) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("cancelFaceDetect called from non-sysui package: ", str, "FaceService");
                return;
            }
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FaceService", "Null provider for cancelFaceDetect");
                return;
            }
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda5(faceProvider, ((Integer) singleProvider.first).intValue(), iBinder, j, 0));
        }

        public final ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) {
            createTestSession_enforcePermission();
            StringBuilder sb = new StringBuilder("createTestSession from pid=");
            sb.append(Binder.getCallingPid());
            sb.append("[");
            sb.append(i);
            sb.append("] ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "", "FaceService");
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider != null) {
                return faceProvider.createTestSession(i, iTestSessionCallback);
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Null provider for createTestSession, sensorId: ", "FaceService");
            return null;
        }

        public final long detectFace(final IBinder iBinder, IFaceServiceReceiver iFaceServiceReceiver, final FaceAuthenticateOptions faceAuthenticateOptions) {
            String str;
            detectFace_enforcePermission();
            StringBuilder sb = new StringBuilder("detectFace called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(" [");
            sb.append(faceAuthenticateOptions.getUserId());
            sb.append("] ");
            if (Utils.DEBUG) {
                str = ", " + faceAuthenticateOptions.getOpPackageName();
            } else {
                str = "";
            }
            sb.append(str);
            Slog.i("FaceService", sb.toString());
            String opPackageName = faceAuthenticateOptions.getOpPackageName();
            if (!Utils.isKeyguard(FaceService.this.getContext(), opPackageName)) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("detectFace called from non-sysui package: ", opPackageName, "FaceService");
                return -1L;
            }
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FaceService", "Null provider for detectFace");
                return -1L;
            }
            faceAuthenticateOptions.setSensorId(((Integer) singleProvider.first).intValue());
            final FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            final ClientMonitorCallbackConverter clientMonitorCallbackConverter = new ClientMonitorCallbackConverter(iFaceServiceReceiver);
            final long incrementAndGet = faceProvider.mRequestCounter.incrementAndGet();
            final int sensorId = faceAuthenticateOptions.getSensorId();
            faceProvider.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda24
                public final /* synthetic */ int f$6 = 1;

                @Override // java.lang.Runnable
                public final void run() {
                    FaceProvider faceProvider2 = FaceProvider.this;
                    int i = sensorId;
                    IBinder iBinder2 = iBinder;
                    long j = incrementAndGet;
                    ClientMonitorCallbackConverter clientMonitorCallbackConverter2 = clientMonitorCallbackConverter;
                    FaceAuthenticateOptions faceAuthenticateOptions2 = faceAuthenticateOptions;
                    int i2 = this.f$6;
                    faceProvider2.getClass();
                    boolean isStrongBiometric = Utils.isStrongBiometric(i);
                    Context context = faceProvider2.mContext;
                    faceProvider2.scheduleForSensor(i, new FaceDetectClient(context, ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i)).mLazySession, iBinder2, j, clientMonitorCallbackConverter2, faceAuthenticateOptions2, faceProvider2.createLogger(2, i2, faceProvider2.mAuthenticationStatsCollector), faceProvider2.mBiometricContext, faceProvider2.mAuthenticationStateListeners, isStrongBiometric, (SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class)), faceProvider2.mBiometricStateCallback);
                }
            });
            return incrementAndGet;
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dump(java.io.FileDescriptor r9, java.io.PrintWriter r10, java.lang.String[] r11) {
            /*
                Method dump skipped, instructions count: 453
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.sensors.face.FaceService.FaceServiceWrapper.dump(java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
        }

        public final byte[] dumpSensorServiceStateProto(int i, boolean z) {
            dumpSensorServiceStateProto_enforcePermission();
            Slog.i("FaceService", "dumpSensorServiceStateProto called from pid=" + Binder.getCallingPid() + "[" + i + "] , " + z);
            ProtoOutputStream protoOutputStream = new ProtoOutputStream();
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider != null) {
                faceProvider.dumpProtoState(i, protoOutputStream, z);
            }
            protoOutputStream.flush();
            return protoOutputStream.getBytes();
        }

        public final long enroll(int i, IBinder iBinder, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str, int[] iArr, Surface surface, boolean z, FaceEnrollOptions faceEnrollOptions) {
            String str2;
            enroll_enforcePermission();
            StringBuilder sb = new StringBuilder("enroll called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(" [");
            sb.append(i);
            sb.append("] ");
            if (Utils.DEBUG) {
                str2 = ", " + iBinder + ", " + str;
            } else {
                str2 = "";
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, str2, "FaceService");
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FaceService", "Null provider for enroll");
                return -1L;
            }
            int userOrWorkProfileId = Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i);
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            int intValue = ((Integer) singleProvider.first).intValue();
            long incrementAndGet = faceProvider.mRequestCounter.incrementAndGet();
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda12(faceProvider, intValue, userOrWorkProfileId, iBinder, iFaceServiceReceiver, bArr, str, incrementAndGet, iArr, surface, z, faceEnrollOptions));
            return incrementAndGet;
        }

        public final long enrollRemotely(int i, IBinder iBinder, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str, int[] iArr) {
            enrollRemotely_enforcePermission();
            StringBuilder sb = new StringBuilder("enrollRemotelycalled from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(" [");
            sb.append(i);
            sb.append("] ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "", "FaceService");
            return -1L;
        }

        public final void generateChallenge(final IBinder iBinder, final int i, int i2, final IFaceServiceReceiver iFaceServiceReceiver, final String str) {
            generateChallenge_enforcePermission();
            StringBuilder sb = new StringBuilder("generateChallenge called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(" [");
            sb.append(i2);
            sb.append("][");
            sb.append(i);
            sb.append("] ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "", "FaceService");
            final FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "No matching sensor for generateChallenge, sensorId: ", "FaceService");
                return;
            }
            final int userOrWorkProfileId = Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2);
            faceProvider.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    FaceProvider faceProvider2 = FaceProvider.this;
                    int i3 = i;
                    int i4 = userOrWorkProfileId;
                    IBinder iBinder2 = iBinder;
                    IFaceServiceReceiver iFaceServiceReceiver2 = iFaceServiceReceiver;
                    String str2 = str;
                    ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i3)).scheduleFaceUpdateActiveUserClient(i4);
                    faceProvider2.scheduleForSensor(i3, new FaceGenerateChallengeClient(faceProvider2.mContext, ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i3)).mLazySession, iBinder2, new ClientMonitorCallbackConverter(iFaceServiceReceiver2), i4, str2, i3, faceProvider2.createLogger(0, 0, faceProvider2.mAuthenticationStatsCollector), faceProvider2.mBiometricContext));
                }
            });
            FaceService.this.mIsEnrollSession.set(true);
        }

        public final long getAuthenticatorId(int i, int i2) {
            getAuthenticatorId_enforcePermission();
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider != null) {
                return faceProvider.getAuthenticatorId(i, Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2));
            }
            Slog.w("FaceService", "Null provider for getAuthenticatorId");
            return 0L;
        }

        public final List getEnrolledFaces(int i, int i2, String str) {
            if (!Utils.hasPermission(FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
                getEnrolledFaces_enforcePermission();
            }
            if (i2 != UserHandle.getCallingUserId()) {
                Utils.checkPermission(FaceService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
            }
            int userOrWorkProfileId = Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2);
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider == null) {
                Slog.w("FaceService", "Null provider for getEnrolledFaces, caller: " + str);
                return Collections.emptyList();
            }
            List biometricsForUser = FaceUtils.getInstance(i, null).getBiometricsForUser(faceProvider.mContext, userOrWorkProfileId);
            StringBuilder sb = new StringBuilder("getEnrolledFaces called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append("[");
            sb.append(userOrWorkProfileId);
            sb.append("][");
            sb.append(i);
            sb.append("] ");
            sb.append(Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "");
            sb.append(", size = ");
            sb.append(biometricsForUser.size());
            Slog.i("FaceService", sb.toString());
            return biometricsForUser;
        }

        public final void getFeature(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) {
            getFeature_enforcePermission();
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FaceService", "Null provider for getFeature");
                return;
            }
            int userOrWorkProfileId = Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i);
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda9(faceProvider, ((Integer) singleProvider.first).intValue(), userOrWorkProfileId, iBinder, new ClientMonitorCallbackConverter(iFaceServiceReceiver), i2));
        }

        public final int getLockoutModeForUser(int i, int i2) {
            getLockoutModeForUser_enforcePermission();
            StringBuilder sb = new StringBuilder("getLockoutModeForUser called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append("[");
            sb.append(i2);
            sb.append("][");
            sb.append(i);
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, "] ", "FaceService");
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider != null) {
                return faceProvider.getLockoutModeForUser(i, Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2));
            }
            Slog.w("FaceService", "Null provider for getLockoutModeForUser");
            return 0;
        }

        public final FaceSensorPropertiesInternal getSensorProperties(int i, String str) {
            getSensorProperties_enforcePermission();
            StringBuilder sb = new StringBuilder("getSensorProperties called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append("[");
            sb.append(i);
            sb.append("] ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "", "FaceService");
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider != null) {
                return faceProvider.getSensorProperties(i);
            }
            Slog.w("FaceService", "No matching sensor for getSensorProperties, sensorId: " + i + ", caller: " + str);
            return null;
        }

        public final List getSensorPropertiesInternal(String str) {
            if (!Utils.hasPermission(FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
                getSensorPropertiesInternal_enforcePermission();
            }
            StringBuilder sb = new StringBuilder("getSensorPropertiesInternal called from pid=");
            sb.append(Binder.getCallingPid());
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "", "FaceService");
            return FaceService.this.mRegistry.getAllProperties();
        }

        public final boolean hasEnrolledFaces(int i, int i2, String str) {
            if (!Utils.hasPermission(FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
                hasEnrolledFaces_enforcePermission();
            }
            if (i2 != UserHandle.getCallingUserId()) {
                Utils.checkPermission(FaceService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
            }
            int userOrWorkProfileId = Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2);
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider == null) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("Null provider for hasEnrolledFaces, caller: ", str, "FaceService");
                return false;
            }
            List biometricsForUser = FaceUtils.getInstance(i, null).getBiometricsForUser(faceProvider.mContext, userOrWorkProfileId);
            StringBuilder sb = new StringBuilder("hasEnrolledFaces called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append("[");
            sb.append(userOrWorkProfileId);
            sb.append("][");
            sb.append(i);
            sb.append("] ");
            sb.append(Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "");
            sb.append(", size = ");
            sb.append(biometricsForUser.size());
            Slog.i("FaceService", sb.toString());
            return biometricsForUser.size() > 0;
        }

        public final void invalidateAuthenticatorId(int i, int i2, IInvalidationCallback iInvalidationCallback) {
            invalidateAuthenticatorId_enforcePermission();
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider == null) {
                Slog.w("FaceService", "Null provider for invalidateAuthenticatorId");
            } else {
                faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda6(faceProvider, i, Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2), iInvalidationCallback, 0));
            }
        }

        public final boolean isHardwareDetected(int i, String str) {
            if (!Utils.hasPermission(FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
                isHardwareDetected_enforcePermission();
            }
            StringBuilder sb = new StringBuilder("isHardwareDetected called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append("[");
            sb.append(i);
            sb.append("] ");
            sb.append(Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "");
            Slog.i("FaceService", sb.toString());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
                if (faceProvider != null) {
                    return faceProvider.isHardwareDetected(i);
                }
                Slog.w("FaceService", "Null provider for isHardwareDetected, caller: " + str);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new FaceShellCommand(FaceService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final void prepareForAuthentication(boolean z, IBinder iBinder, long j, IBiometricSensorReceiver iBiometricSensorReceiver, FaceAuthenticateOptions faceAuthenticateOptions, long j2, int i, boolean z2) {
            String str;
            prepareForAuthentication_enforcePermission();
            StringBuilder sb = new StringBuilder("prepareForAuthentication called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(" [");
            sb.append(faceAuthenticateOptions.getUserId());
            sb.append("][");
            sb.append(faceAuthenticateOptions.getSensorId());
            sb.append("] ");
            if (Utils.DEBUG) {
                str = ", " + faceAuthenticateOptions.getOpPackageName() + ", " + i;
            } else {
                str = "";
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, str, "FaceService");
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(faceAuthenticateOptions.getSensorId());
            if (faceProvider == null) {
                Slog.w("FaceService", "Null provider for prepareForAuthentication");
                return;
            }
            int userOrWorkProfileId = Utils.getUserOrWorkProfileId(FaceService.this.getContext(), faceAuthenticateOptions.getUserId());
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda10(faceProvider, userOrWorkProfileId != faceAuthenticateOptions.getUserId() ? SemFaceUtils.copyOptions(userOrWorkProfileId, faceAuthenticateOptions) : faceAuthenticateOptions, z2, iBinder, j2, new ClientMonitorCallbackConverter(iBiometricSensorReceiver), j, true, i, 2));
        }

        public final void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) {
            registerAuthenticationStateListener_enforcePermission();
            AuthenticationStateListeners authenticationStateListeners = FaceService.this.mAuthenticationStateListeners;
            authenticationStateListeners.mAuthenticationStateListeners.add(authenticationStateListener);
            try {
                authenticationStateListener.asBinder().linkToDeath(authenticationStateListeners, 0);
            } catch (RemoteException e) {
                Slog.e("AuthenticationStateListeners", "Failed to link to death", e);
            }
        }

        public final void registerAuthenticators(final FaceSensorConfigurations faceSensorConfigurations) {
            registerAuthenticators_enforcePermission();
            if (!faceSensorConfigurations.hasSensorConfigurations()) {
                Slog.d("FaceService", "No face sensors to register.");
                return;
            }
            final FaceServiceRegistry faceServiceRegistry = FaceService.this.mRegistry;
            final ?? r1 = new Supplier() { // from class: com.android.server.biometrics.sensors.face.FaceService$FaceServiceWrapper$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    Pair pair;
                    String sensorNameNotForInstance;
                    FaceService.FaceServiceWrapper faceServiceWrapper = FaceService.FaceServiceWrapper.this;
                    FaceSensorConfigurations faceSensorConfigurations2 = faceSensorConfigurations;
                    int i = FaceService.FaceServiceWrapper.$r8$clinit;
                    faceServiceWrapper.getClass();
                    ArrayList arrayList = new ArrayList();
                    String sensorInstance = faceSensorConfigurations2.getSensorInstance();
                    if (faceSensorConfigurations2.isSingleSensorConfigurationPresent()) {
                        pair = new Pair(sensorInstance, faceSensorConfigurations2.getSensorPropForInstance(sensorInstance));
                    } else {
                        boolean doesInstanceExist = faceSensorConfigurations2.doesInstanceExist("virtual");
                        Flags.faceVhalFeature();
                        pair = (!doesInstanceExist || (sensorNameNotForInstance = faceSensorConfigurations2.getSensorNameNotForInstance("virtual")) == null) ? new Pair(sensorInstance, faceSensorConfigurations2.getSensorPropForInstance(sensorInstance)) : new Pair(sensorNameNotForInstance, faceSensorConfigurations2.getSensorPropForInstance(sensorNameNotForInstance));
                    }
                    FaceService.FaceProviderFunction faceProviderFunction = FaceService.this.mFaceProviderFunction;
                    boolean resetLockoutRequiresChallenge = faceSensorConfigurations2.getResetLockoutRequiresChallenge();
                    FaceService faceService = ((FaceService$$ExternalSyntheticLambda1) faceProviderFunction).f$0;
                    faceService.getClass();
                    arrayList.add(new FaceProvider(faceService.getContext(), faceService.mBiometricStateCallback, faceService.mAuthenticationStateListeners, (SensorProps[]) pair.second, (String) pair.first, faceService.mLockoutResetDispatcher, BiometricContext.getInstance(faceService.getContext()), resetLockoutRequiresChallenge));
                    return arrayList;
                }
            };
            faceServiceRegistry.getClass();
            ServiceThread m = Watchdog$$ExternalSyntheticOutline0.m(10, "BiometricServiceRegistry", true);
            new Handler(m.getLooper()).post(new Runnable() { // from class: com.android.server.biometrics.sensors.BiometricServiceRegistry$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricServiceRegistry.this.registerAllInBackground(r1);
                }
            });
            m.quitSafely();
        }

        public final void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) {
            FaceService.this.mBiometricStateCallback.registerBiometricStateListener(iBiometricStateListener);
        }

        public final void remove(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) {
            remove_enforcePermission();
            StringBuilder sb = new StringBuilder("remove called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(" [");
            sb.append(i2);
            sb.append("] ");
            sb.append(i);
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "", "FaceService");
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FaceService", "Null provider for remove");
            } else {
                FaceProvider faceProvider = (FaceProvider) singleProvider.second;
                faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda8(faceProvider, ((Integer) singleProvider.first).intValue(), i2, iBinder, iFaceServiceReceiver, new int[]{i}, str));
            }
        }

        public final void removeAll(IBinder iBinder, int i, final IFaceServiceReceiver iFaceServiceReceiver, String str) {
            removeAll_enforcePermission();
            StringBuilder sb = new StringBuilder("removeAll called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(" [");
            sb.append(i);
            sb.append("] ");
            sb.append(Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "");
            Slog.i("FaceService", sb.toString());
            FaceServiceReceiver faceServiceReceiver = new FaceServiceReceiver(this) { // from class: com.android.server.biometrics.sensors.face.FaceService.FaceServiceWrapper.1
                public final int numSensors;
                public int sensorsFinishedRemoving = 0;

                {
                    this.numSensors = this.getSensorPropertiesInternal(FaceService.this.getContext().getOpPackageName()).size();
                }

                public final void onError(int i2, int i3) {
                    Log.e("FaceService", "removeAll internalReceiver : error = " + i2 + ", vendor = " + i3);
                    iFaceServiceReceiver.onError(i2, i3);
                }

                public final void onRemoved(Face face, int i2) {
                    if (i2 == 0) {
                        this.sensorsFinishedRemoving++;
                        StringBuilder sb2 = new StringBuilder("sensorsFinishedRemoving: ");
                        sb2.append(this.sensorsFinishedRemoving);
                        sb2.append(", numSensors: ");
                        DeviceIdleController$$ExternalSyntheticOutline0.m(sb2, this.numSensors, "FaceService");
                        if (this.sensorsFinishedRemoving == this.numSensors) {
                            iFaceServiceReceiver.onRemoved(new Face("", 0, 0L), 0);
                        }
                    }
                }
            };
            for (FaceProvider faceProvider : FaceService.this.mRegistry.getProviders()) {
                Iterator it = ((ArrayList) faceProvider.getSensorProperties()).iterator();
                while (it.hasNext()) {
                    int i2 = ((FaceSensorPropertiesInternal) it.next()).sensorId;
                    ArrayList arrayList = (ArrayList) FaceUtils.getInstance(i2, null).getBiometricsForUser(faceProvider.mContext, i);
                    int[] iArr = new int[arrayList.size()];
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        iArr[i3] = ((Face) arrayList.get(i3)).getBiometricId();
                    }
                    faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda8(faceProvider, i2, i, iBinder, faceServiceReceiver, iArr, str));
                }
            }
        }

        public final void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) {
            resetLockout_enforcePermission();
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider == null) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("Null provider for resetLockout, caller: ", str, "FaceService");
            } else {
                faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda6(faceProvider, i, Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2), bArr, 1));
            }
        }

        public final void revokeChallenge(final IBinder iBinder, final int i, int i2, final String str, final long j) {
            revokeChallenge_enforcePermission();
            StringBuilder sb = new StringBuilder("revokeChallenge called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(" [");
            sb.append(i2);
            sb.append("][");
            sb.append(i);
            sb.append("] ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, Utils.DEBUG ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(", ", str) : "", "FaceService");
            final FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "No matching sensor for revokeChallenge, sensorId: ", "FaceService");
                return;
            }
            final int userOrWorkProfileId = Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2);
            faceProvider.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    FaceProvider faceProvider2 = FaceProvider.this;
                    int i3 = i;
                    IBinder iBinder2 = iBinder;
                    int i4 = userOrWorkProfileId;
                    String str2 = str;
                    long j2 = j;
                    faceProvider2.getClass();
                    faceProvider2.scheduleForSensor(i3, new FaceRevokeChallengeClient(faceProvider2.mContext, ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i3)).mLazySession, iBinder2, i4, str2, i3, faceProvider2.createLogger(0, 0, faceProvider2.mAuthenticationStatsCollector), faceProvider2.mBiometricContext, j2));
                }
            });
            FaceService.this.mIsEnrollSession.set(false);
        }

        public final void scheduleWatchdog() {
            scheduleWatchdog_enforcePermission();
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FaceService", "Null provider for scheduling watchdog");
            } else {
                ((FaceProvider) singleProvider.second).scheduleWatchdog(((Integer) singleProvider.first).intValue());
            }
        }

        public final long semAuthenticate(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions, Bundle bundle, byte[] bArr) {
            String str;
            StringBuilder sb = new StringBuilder("semAuthenticate called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(" [");
            sb.append(faceAuthenticateOptions.getUserId());
            sb.append("] ");
            if (Utils.DEBUG) {
                str = ", " + iBinder + ", " + j + ", " + faceAuthenticateOptions.getOpPackageName() + ", " + bundle.toString();
            } else {
                str = "";
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, str, "FaceService");
            if (!Utils.hasPermission(FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
                authenticate_enforcePermission();
            }
            SemFaceUtils.setFidoRequestData(bArr);
            SemFaceUtils.mBundle = bundle;
            return authenticate(iBinder, j, iFaceServiceReceiver, faceAuthenticateOptions);
        }

        public final long semAuthenticateExt(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions, Surface surface, byte[] bArr) {
            String str;
            StringBuilder sb = new StringBuilder("semAuthenticate (s) called from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(" [");
            sb.append(faceAuthenticateOptions.getUserId());
            sb.append("] ");
            if (Utils.DEBUG) {
                str = ", " + iBinder + ", " + j + ", " + faceAuthenticateOptions.getOpPackageName();
            } else {
                str = "";
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, str, "FaceService");
            Utils.checkPermission(FaceService.this.getContext(), "android.permission.MANAGE_BIOMETRIC");
            SemFaceUtils.setFidoRequestData(bArr);
            SemFaceUtils.mSurface = surface;
            SemFaceUtils.mNeedtoAuthenticateExt = true;
            return authenticate(iBinder, j, iFaceServiceReceiver, faceAuthenticateOptions);
        }

        public final String semGetInfo(int i) {
            semGetInfo_enforcePermission();
            Slog.i("FaceService", "semGetInfo called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            String str = null;
            if (singleProvider == null) {
                return null;
            }
            SemFaceServiceExImpl serviceExtImpl = ((FaceProvider) singleProvider.second).getServiceExtImpl();
            serviceExtImpl.getClass();
            long currentTimeMillis = System.currentTimeMillis();
            try {
            } catch (Exception e) {
                Slog.e("SemFace", "daemonGetInfo: ", e);
            }
            if (serviceExtImpl.mIsHIDL) {
                HidlToAidlSessionAdapter hidlToAidlSessionAdapter = (HidlToAidlSessionAdapter) ((AidlSession) serviceExtImpl.mSensor.mLazySession.get()).mSession;
                hidlToAidlSessionAdapter.getClass();
                try {
                    str = ((ISehBiometricsFace) hidlToAidlSessionAdapter.mSession.get()).sehGetTaInfo();
                } catch (RemoteException e2) {
                    Slog.e("HidlToAidlSessionAdapter", "semGetTaInfo HIDL : ", e2);
                }
            } else {
                ISehSession iSehSession = serviceExtImpl.mISehSession;
                if (iSehSession == null) {
                    Slog.w("SemFace", "daemonGetInfo(): no face seh HAL!");
                    return str;
                }
                if (i == 1) {
                    str = ((ISehSession.Stub.Proxy) iSehSession).getTaInfo();
                }
            }
            Slog.w("SemFace", "getEngineVersion FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + str);
            return str;
        }

        public final int semGetRemainingLockoutTime(int i) {
            semGetRemainingLockoutTime_enforcePermission();
            Slog.i("FaceService", "semGetRemainingLockoutTime called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return 0;
            }
            return ((FaceProvider) singleProvider.second).semGetRemainingLockoutTime(Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i));
        }

        public final int semGetSecurityLevel(boolean z) {
            semGetSecurityLevel_enforcePermission();
            Slog.i("FaceService", "semGetSecurityLevel called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return 0;
            }
            SemFaceServiceExImpl serviceExtImpl = ((FaceProvider) singleProvider.second).getServiceExtImpl();
            SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("getSL : "), serviceExtImpl.mSecurityLevel, "SemFace");
            return serviceExtImpl.mSecurityLevel;
        }

        public final boolean semIsEnrollSession() {
            semIsEnrollSession_enforcePermission();
            Slog.i("FaceService", "semIsEnrollSession called from pid=" + Binder.getCallingPid());
            return FaceService.this.mIsEnrollSession.get();
        }

        public final boolean semIsFrameworkHandleLockout() {
            semIsFrameworkHandleLockout_enforcePermission();
            Slog.i("FaceService", "semIsFrameworkHandleLockout called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return false;
            }
            ((FaceProvider) singleProvider.second).getServiceExtImpl();
            return true;
        }

        public final boolean semIsSessionClose() {
            boolean isTAUnloaded;
            semIsSessionClose_enforcePermission();
            Slog.i("FaceService", "semIsSessionClose called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return false;
            }
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.getHalInstance();
            SemFaceServiceExImpl serviceExtImpl = faceProvider.getServiceExtImpl();
            serviceExtImpl.getClass();
            long currentTimeMillis = System.currentTimeMillis();
            try {
                if (serviceExtImpl.mIsHIDL) {
                    HidlToAidlSessionAdapter hidlToAidlSessionAdapter = (HidlToAidlSessionAdapter) ((AidlSession) serviceExtImpl.mSensor.mLazySession.get()).mSession;
                    hidlToAidlSessionAdapter.getClass();
                    try {
                        isTAUnloaded = ((ISehBiometricsFace) hidlToAidlSessionAdapter.mSession.get()).sehIsTaSessionClosed();
                    } catch (RemoteException e) {
                        Slog.e("HidlToAidlSessionAdapter", "semIsSessionClose HIDL : ", e);
                        isTAUnloaded = false;
                    }
                } else {
                    ISehSession iSehSession = serviceExtImpl.mISehSession;
                    if (iSehSession == null) {
                        Slog.w("SemFace", "daemonIsSessionClose(): no face seh HAL!");
                        return false;
                    }
                    isTAUnloaded = ((ISehSession.Stub.Proxy) iSehSession).isTAUnloaded();
                }
                Slog.w("SemFace", "sehIsTaSessionClosed FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + isTAUnloaded);
                return isTAUnloaded;
            } catch (Exception e2) {
                Slog.e("SemFace", "daemonIsSessionClose: ", e2);
                return false;
            }
        }

        public final void semPauseAuth() {
            semPauseAuth_enforcePermission();
            Slog.i("FaceService", "semPauseAuth called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return;
            }
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda2(1, faceProvider));
        }

        public final void semPauseEnroll() {
            semPauseEnroll_enforcePermission();
            Slog.i("FaceService", "semPauseEnroll called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return;
            }
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda2(4, faceProvider));
        }

        public final boolean semResetAuthenticationTimeout() {
            int i;
            semResetAuthenticationTimeout_enforcePermission();
            Slog.i("FaceService", "semResetAuthenticationTimeout called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return false;
            }
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            if (SemFaceBrightManager.getInstance(faceProvider.mContext).mPowerManager.getCurrentBrightness(false) < r1.mScreenFlashBrightnessLevelMaxCorrected) {
                faceProvider.getServiceExtImpl();
                i = 5000;
            } else {
                faceProvider.getServiceExtImpl();
                i = 4000;
            }
            SemFaceServiceExImpl serviceExtImpl = faceProvider.getServiceExtImpl();
            serviceExtImpl.getClass();
            Slog.i("SemFace", "resetAuthenticationTimeout");
            if (i <= 0) {
                return false;
            }
            serviceExtImpl.startInactivityTimer(i);
            serviceExtImpl.releaseDVFS();
            serviceExtImpl.acquireDVFS(i, 2);
            return true;
        }

        public final void semResumeAuth() {
            semResumeAuth_enforcePermission();
            Slog.i("FaceService", "semResumeAuth called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return;
            }
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda2(3, faceProvider));
        }

        public final void semResumeEnroll() {
            semResumeEnroll_enforcePermission();
            Slog.i("FaceService", "semResumeEnroll called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return;
            }
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda2(5, faceProvider));
        }

        public final void semSessionClose() {
            semSessionClose_enforcePermission();
            Slog.i("FaceService", "semSessionClose called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return;
            }
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda2(6, faceProvider));
        }

        public final void semSessionOpen() {
            semSessionOpen_enforcePermission();
            Slog.i("FaceService", "semSessionOpen called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return;
            }
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda2(2, faceProvider));
        }

        public final boolean semShouldRemoveTemplate() {
            semGetRemainingLockoutTime_enforcePermission();
            Slog.i("FaceService", "semShouldRemoveTemplate called from pid=" + Binder.getCallingPid());
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                return false;
            }
            ((FaceProvider) singleProvider.second).getClass();
            return false;
        }

        public final void setFeature(final IBinder iBinder, int i, final int i2, final boolean z, final byte[] bArr, final IFaceServiceReceiver iFaceServiceReceiver, String str) {
            setFeature_enforcePermission();
            Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                Slog.w("FaceService", "Null provider for setFeature");
                return;
            }
            final int userOrWorkProfileId = Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i);
            final FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            final int intValue = ((Integer) singleProvider.first).intValue();
            faceProvider.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    FaceProvider faceProvider2 = FaceProvider.this;
                    int i3 = intValue;
                    int i4 = userOrWorkProfileId;
                    IBinder iBinder2 = iBinder;
                    IFaceServiceReceiver iFaceServiceReceiver2 = iFaceServiceReceiver;
                    int i5 = i2;
                    boolean z2 = z;
                    byte[] bArr2 = bArr;
                    ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i3)).scheduleFaceUpdateActiveUserClient(i4);
                    if (FaceUtils.getInstance(i3, null).getBiometricsForUser(faceProvider2.mContext, i4).isEmpty()) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(i4, "Ignoring setFeature, no templates enrolled for user: ", faceProvider2.getTag());
                    } else {
                        faceProvider2.scheduleForSensor(i3, new FaceSetFeatureClient(faceProvider2.mContext, ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i3)).mLazySession, iBinder2, new ClientMonitorCallbackConverter(iFaceServiceReceiver2), i4, faceProvider2.mContext.getOpPackageName(), i3, BiometricLogger.ofUnknown(faceProvider2.mContext), faceProvider2.mBiometricContext, i5, z2, bArr2));
                    }
                }
            });
        }

        public final void startPreparedClient(int i, int i2) {
            startPreparedClient_enforcePermission();
            StringBuilder sb = new StringBuilder("startPreparedClientcalled from pid=");
            sb.append(Binder.getCallingPid());
            sb.append("[");
            sb.append(i);
            sb.append("] ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, Utils.DEBUG ? VibrationParam$1$$ExternalSyntheticOutline0.m(i2, ", ") : "", "FaceService");
            FaceProvider faceProvider = (FaceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
            if (faceProvider == null) {
                Slog.w("FaceService", "Null provider for startPreparedClient");
            } else {
                faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda15(faceProvider, i, i2, 0));
            }
        }

        public final void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) {
            unregisterAuthenticationStateListener_enforcePermission();
            FaceService.this.mAuthenticationStateListeners.mAuthenticationStateListeners.remove(authenticationStateListener);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FaceService(android.content.Context r7) {
        /*
            r6 = this;
            com.android.server.biometrics.sensors.face.FaceService$$ExternalSyntheticLambda2 r3 = new com.android.server.biometrics.sensors.face.FaceService$$ExternalSyntheticLambda2
            r0 = 0
            r3.<init>()
            com.android.server.biometrics.sensors.face.FaceService$$ExternalSyntheticLambda2 r5 = new com.android.server.biometrics.sensors.face.FaceService$$ExternalSyntheticLambda2
            r0 = 1
            r5.<init>()
            r2 = 0
            r4 = 0
            r0 = r6
            r1 = r7
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.sensors.face.FaceService.<init>(android.content.Context):void");
    }

    public FaceService(Context context, FaceProviderFunction faceProviderFunction, Supplier supplier, Function function, Supplier supplier2) {
        super(context);
        this.mIsEnrollSession = new AtomicBoolean(false);
        FaceServiceWrapper faceServiceWrapper = new FaceServiceWrapper();
        this.mServiceWrapper = faceServiceWrapper;
        this.mLockoutResetDispatcher = new LockoutResetDispatcher(context);
        new LockPatternUtils(context);
        this.mBiometricStateCallback = new BiometricStateCallback(UserManager.get(context));
        this.mAuthenticationStateListeners = new AuthenticationStateListeners();
        FaceServiceRegistry faceServiceRegistry = new FaceServiceRegistry(faceServiceWrapper, supplier);
        this.mRegistry = faceServiceRegistry;
        faceServiceRegistry.addAllRegisteredCallback(new IFaceAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.server.biometrics.sensors.face.FaceService.1
            public final void onAllAuthenticatorsRegistered(List list) {
                FaceService faceService = FaceService.this;
                BiometricStateCallback biometricStateCallback = faceService.mBiometricStateCallback;
                List providers = faceService.mRegistry.getProviders();
                synchronized (biometricStateCallback) {
                    biometricStateCallback.mProviders = Collections.unmodifiableList(providers);
                    biometricStateCallback.broadcastCurrentEnrollmentState(null);
                }
            }
        });
        this.mAidlInstanceNameSupplier = supplier2;
        this.mFaceProvider = function == null ? new Function() { // from class: com.android.server.biometrics.sensors.face.FaceService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String str;
                FaceService faceService = FaceService.this;
                String str2 = (String) obj;
                faceService.getClass();
                String m = BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder(), IFace.DESCRIPTOR, "/", str2);
                IFace asInterface = IFace.Stub.asInterface(Binder.allowBlocking(ServiceManager.waitForDeclaredService(m)));
                if (asInterface == null) {
                    str = "Unable to get declared service: ";
                } else {
                    try {
                        SensorProps[] sensorProps = asInterface.getSensorProps();
                        Slog.d("FaceService", "getSensorProps FINISH props:" + sensorProps.length);
                        for (SensorProps sensorProps2 : sensorProps) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("sensorType = ");
                            sb.append((int) sensorProps2.sensorType);
                            sb.append(", sensor id = ");
                            sb.append(sensorProps2.commonProps.sensorId);
                            sb.append(", Max = ");
                            sb.append(sensorProps2.commonProps.maxEnrollmentsPerUser);
                            sb.append(", Strength = ");
                            sb.append((int) sensorProps2.commonProps.sensorStrength);
                            sb.append(Utils.DEBUG ? " (Strong=2,1,0)" : "");
                            Slog.d("FaceService", sb.toString());
                        }
                        if (Utils.DEBUG) {
                            Slog.d("FaceService", "addAidlProviders : " + m);
                        }
                        return new FaceProvider(faceService.getContext(), faceService.mBiometricStateCallback, faceService.mAuthenticationStateListeners, sensorProps, str2, faceService.mLockoutResetDispatcher, BiometricContext.getInstance(faceService.getContext()), false);
                    } catch (RemoteException unused) {
                        str = "Remote exception in getSensorProps: ";
                    }
                }
                BootReceiver$$ExternalSyntheticOutline0.m(str, m, "FaceService");
                return null;
            }
        } : function;
        this.mFaceProviderFunction = faceProviderFunction == null ? new FaceService$$ExternalSyntheticLambda1(this) : faceProviderFunction;
    }

    public static native NativeHandle acquireSurfaceHandle(Surface surface);

    public static native void releaseSurfaceHandle(NativeHandle nativeHandle);

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        Pair singleProvider = this.mRegistry.getSingleProvider();
        if (singleProvider == null) {
            Slog.w("FaceService", "Null provider for onBootPhase");
        } else {
            FaceProvider faceProvider = (FaceProvider) singleProvider.second;
            faceProvider.mHandler.post(new FaceProvider$$ExternalSyntheticLambda0(faceProvider, i, 0));
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("face", this.mServiceWrapper);
    }
}
