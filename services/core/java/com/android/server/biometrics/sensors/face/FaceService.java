package com.android.server.biometrics.sensors.face;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.biometrics.IBiometricStateListener;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.SensorProps;
import android.hardware.face.Face;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.face.FaceServiceReceiver;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import android.hardware.face.IFaceService;
import android.hardware.face.IFaceServiceReceiver;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
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
import com.android.internal.util.DumpUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.SystemService;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.sensors.BiometricStateCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider;
import com.android.server.biometrics.sensors.face.hidl.Face10;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FaceService extends SystemService {
  public boolean mAidlEnabled;
  public final BiometricStateCallback mBiometricStateCallback;
  public AtomicBoolean mIsEnrollSession;
  public final LockPatternUtils mLockPatternUtils;
  public final LockoutResetDispatcher mLockoutResetDispatcher;
  public final FaceServiceRegistry mRegistry;
  public final FaceServiceWrapper mServiceWrapper;

  public static native NativeHandle acquireSurfaceHandle(Surface surface);

  public static native void releaseSurfaceHandle(NativeHandle nativeHandle);

  public final class FaceServiceWrapper extends IFaceService.Stub {
    public FaceServiceWrapper() {}

    public ITestSession createTestSession(
        int i, ITestSessionCallback iTestSessionCallback, String str) {
      String str2;
      super.createTestSession_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("createTestSession from pid=");
      sb.append(Binder.getCallingPid());
      sb.append("[");
      sb.append(i);
      sb.append("] ");
      if (Utils.DEBUG) {
        str2 = ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FaceService", "Null provider for createTestSession, sensorId: " + i);
        return null;
      }
      return serviceProvider.createTestSession(i, iTestSessionCallback, str);
    }

    public byte[] dumpSensorServiceStateProto(int i, boolean z) {
      super.dumpSensorServiceStateProto_enforcePermission();
      Slog.i(
          "FaceService",
          "dumpSensorServiceStateProto called from pid="
              + Binder.getCallingPid()
              + "["
              + i
              + "] , "
              + z);
      ProtoOutputStream protoOutputStream = new ProtoOutputStream();
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider != null) {
        serviceProvider.dumpProtoState(i, protoOutputStream, z);
      }
      protoOutputStream.flush();
      return protoOutputStream.getBytes();
    }

    public List getSensorPropertiesInternal(String str) {
      String str2;
      if (!Utils.hasPermission(
          FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
        super.getSensorPropertiesInternal_enforcePermission();
      }
      StringBuilder sb = new StringBuilder();
      sb.append("getSensorPropertiesInternal called from pid=");
      sb.append(Binder.getCallingPid());
      if (Utils.DEBUG) {
        str2 = ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      return FaceService.this.mRegistry.getAllProperties();
    }

    public FaceSensorPropertiesInternal getSensorProperties(int i, String str) {
      String str2;
      super.getSensorProperties_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("getSensorProperties called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append("[");
      sb.append(i);
      sb.append("] ");
      if (Utils.DEBUG) {
        str2 = ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w(
            "FaceService",
            "No matching sensor for getSensorProperties, sensorId: " + i + ", caller: " + str);
        return null;
      }
      return serviceProvider.getSensorProperties(i);
    }

    public void generateChallenge(
        IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) {
      String str2;
      super.generateChallenge_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("generateChallenge called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append(" [");
      sb.append(i2);
      sb.append("][");
      sb.append(i);
      sb.append("] ");
      if (Utils.DEBUG) {
        str2 = ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FaceService", "No matching sensor for generateChallenge, sensorId: " + i);
        return;
      }
      serviceProvider.scheduleGenerateChallenge(
          i,
          Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2),
          iBinder,
          iFaceServiceReceiver,
          str);
      FaceService.this.mIsEnrollSession.set(true);
    }

    public void revokeChallenge(IBinder iBinder, int i, int i2, String str, long j) {
      String str2;
      super.revokeChallenge_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("revokeChallenge called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append(" [");
      sb.append(i2);
      sb.append("][");
      sb.append(i);
      sb.append("] ");
      if (Utils.DEBUG) {
        str2 = ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FaceService", "No matching sensor for revokeChallenge, sensorId: " + i);
        return;
      }
      serviceProvider.scheduleRevokeChallenge(
          i, Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2), iBinder, str, j);
      FaceService.this.mIsEnrollSession.set(false);
    }

    public long enroll(
        int i,
        IBinder iBinder,
        byte[] bArr,
        IFaceServiceReceiver iFaceServiceReceiver,
        String str,
        int[] iArr,
        Surface surface,
        boolean z) {
      String str2;
      super.enroll_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("enroll called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append(" [");
      sb.append(i);
      sb.append("] ");
      if (Utils.DEBUG) {
        str2 = ", " + iBinder + ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FaceService", "Null provider for enroll");
        return -1L;
      }
      return ((ServiceProvider) singleProvider.second)
          .scheduleEnroll(
              ((Integer) singleProvider.first).intValue(),
              iBinder,
              bArr,
              Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i),
              iFaceServiceReceiver,
              str,
              iArr,
              surface,
              z);
    }

    public void scheduleWatchdog() {
      super.scheduleWatchdog_enforcePermission();
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FaceService", "Null provider for scheduling watchdog");
      } else {
        ((ServiceProvider) singleProvider.second)
            .scheduleWatchdog(((Integer) singleProvider.first).intValue());
      }
    }

    public long enrollRemotely(
        int i,
        IBinder iBinder,
        byte[] bArr,
        IFaceServiceReceiver iFaceServiceReceiver,
        String str,
        int[] iArr) {
      String str2;
      super.enrollRemotely_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("enrollRemotelycalled from pid=");
      sb.append(Binder.getCallingPid());
      sb.append(" [");
      sb.append(i);
      sb.append("] ");
      if (Utils.DEBUG) {
        str2 = ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      return -1L;
    }

    public void cancelEnrollment(IBinder iBinder, long j) {
      String str;
      super.cancelEnrollment_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("cancelEnrollment called from pid=");
      sb.append(Binder.getCallingPid());
      if (Utils.DEBUG) {
        str = ", " + iBinder;
      } else {
        str = "";
      }
      sb.append(str);
      Slog.i("FaceService", sb.toString());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FaceService", "Null provider for cancelEnrollment");
      } else {
        ((ServiceProvider) singleProvider.second)
            .cancelEnrollment(((Integer) singleProvider.first).intValue(), iBinder, j);
      }
    }

    public long authenticate(
        IBinder iBinder,
        long j,
        IFaceServiceReceiver iFaceServiceReceiver,
        FaceAuthenticateOptions faceAuthenticateOptions) {
      String str;
      if (!Utils.hasPermission(
          FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
        super.authenticate_enforcePermission();
      }
      StringBuilder sb = new StringBuilder();
      sb.append("authenticate called from pid=");
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
      int userOrWorkProfileId =
          Utils.getUserOrWorkProfileId(
              FaceService.this.getContext(), faceAuthenticateOptions.getUserId());
      return ((ServiceProvider) singleProvider.second)
          .scheduleAuthenticate(
              iBinder,
              j,
              0,
              new ClientMonitorCallbackConverter(iFaceServiceReceiver),
              userOrWorkProfileId != faceAuthenticateOptions.getUserId()
                  ? SemFaceUtils.copyOptions(userOrWorkProfileId, faceAuthenticateOptions)
                  : faceAuthenticateOptions,
              false,
              isKeyguard ? 1 : 0,
              isKeyguard2);
    }

    public long detectFace(
        IBinder iBinder,
        IFaceServiceReceiver iFaceServiceReceiver,
        FaceAuthenticateOptions faceAuthenticateOptions) {
      String str;
      super.detectFace_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("detectFace called from pid=");
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
        Slog.w("FaceService", "detectFace called from non-sysui package: " + opPackageName);
        return -1L;
      }
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FaceService", "Null provider for detectFace");
        return -1L;
      }
      faceAuthenticateOptions.setSensorId(((Integer) singleProvider.first).intValue());
      return ((ServiceProvider) singleProvider.second)
          .scheduleFaceDetect(
              iBinder,
              new ClientMonitorCallbackConverter(iFaceServiceReceiver),
              faceAuthenticateOptions,
              1);
    }

    public void prepareForAuthentication(
        boolean z,
        IBinder iBinder,
        long j,
        IBiometricSensorReceiver iBiometricSensorReceiver,
        FaceAuthenticateOptions faceAuthenticateOptions,
        long j2,
        int i,
        boolean z2) {
      String str;
      super.prepareForAuthentication_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("prepareForAuthentication called from pid=");
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
      sb.append(str);
      Slog.i("FaceService", sb.toString());
      ServiceProvider serviceProvider =
          (ServiceProvider)
              FaceService.this.mRegistry.getProviderForSensor(
                  faceAuthenticateOptions.getSensorId());
      if (serviceProvider == null) {
        Slog.w("FaceService", "Null provider for prepareForAuthentication");
      } else {
        int userOrWorkProfileId =
            Utils.getUserOrWorkProfileId(
                FaceService.this.getContext(), faceAuthenticateOptions.getUserId());
        serviceProvider.scheduleAuthenticate(
            iBinder,
            j,
            i,
            new ClientMonitorCallbackConverter(iBiometricSensorReceiver),
            userOrWorkProfileId != faceAuthenticateOptions.getUserId()
                ? SemFaceUtils.copyOptions(userOrWorkProfileId, faceAuthenticateOptions)
                : faceAuthenticateOptions,
            j2,
            true,
            2,
            z2);
      }
    }

    public void startPreparedClient(int i, int i2) {
      String str;
      super.startPreparedClient_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("startPreparedClientcalled from pid=");
      sb.append(Binder.getCallingPid());
      sb.append("[");
      sb.append(i);
      sb.append("] ");
      if (Utils.DEBUG) {
        str = ", " + i2;
      } else {
        str = "";
      }
      sb.append(str);
      Slog.i("FaceService", sb.toString());
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FaceService", "Null provider for startPreparedClient");
      } else {
        serviceProvider.startPreparedClient(i, i2);
      }
    }

    public void cancelAuthentication(IBinder iBinder, String str, long j) {
      String str2;
      if (!Utils.hasPermission(
          FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
        super.cancelAuthentication_enforcePermission();
      }
      StringBuilder sb = new StringBuilder();
      sb.append("cancelAuthentication called from pid=");
      sb.append(Binder.getCallingPid());
      if (Utils.DEBUG) {
        str2 = ", " + iBinder + ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FaceService", "Null provider for cancelAuthentication");
      } else {
        ((ServiceProvider) singleProvider.second)
            .cancelAuthentication(((Integer) singleProvider.first).intValue(), iBinder, j);
      }
    }

    public void cancelFaceDetect(IBinder iBinder, String str, long j) {
      String str2;
      super.cancelFaceDetect_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("cancelFaceDetectcalled from pid=");
      sb.append(Binder.getCallingPid());
      if (Utils.DEBUG) {
        str2 = ", " + iBinder + ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      if (!Utils.isKeyguard(FaceService.this.getContext(), str)) {
        Slog.w("FaceService", "cancelFaceDetect called from non-sysui package: " + str);
        return;
      }
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FaceService", "Null provider for cancelFaceDetect");
      } else {
        ((ServiceProvider) singleProvider.second)
            .cancelFaceDetect(((Integer) singleProvider.first).intValue(), iBinder, j);
      }
    }

    public void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) {
      String str2;
      super.cancelAuthenticationFromService_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("cancelAuthenticationFromService called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append("[");
      sb.append(i);
      sb.append("] ");
      if (Utils.DEBUG) {
        str2 = ", " + i + ", " + iBinder + ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FaceService", "Null provider for cancelAuthenticationFromService");
      } else {
        serviceProvider.cancelAuthentication(i, iBinder, j);
      }
    }

    public void remove(
        IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) {
      String str2;
      super.remove_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("remove called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append(" [");
      sb.append(i2);
      sb.append("] ");
      sb.append(i);
      if (Utils.DEBUG) {
        str2 = ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FaceService", "Null provider for remove");
      } else {
        ((ServiceProvider) singleProvider.second)
            .scheduleRemove(
                ((Integer) singleProvider.first).intValue(),
                iBinder,
                i,
                i2,
                iFaceServiceReceiver,
                str);
      }
    }

    public void removeAll(
        IBinder iBinder, int i, final IFaceServiceReceiver iFaceServiceReceiver, String str) {
      super.removeAll_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("removeAll called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append(" [");
      sb.append(i);
      sb.append("] ");
      sb.append(Utils.DEBUG ? ", " + str : "");
      Slog.i("FaceService", sb.toString());
      IFaceServiceReceiver iFaceServiceReceiver2 =
          new FaceServiceReceiver() { // from class:
                                      // com.android.server.biometrics.sensors.face.FaceService.FaceServiceWrapper.1
            public final int numSensors;
            public int sensorsFinishedRemoving = 0;

            {
              this.numSensors =
                  FaceServiceWrapper.this
                      .getSensorPropertiesInternal(FaceService.this.getContext().getOpPackageName())
                      .size();
            }

            public void onRemoved(Face face, int i2) {
              if (i2 == 0) {
                this.sensorsFinishedRemoving++;
                Slog.d(
                    "FaceService",
                    "sensorsFinishedRemoving: "
                        + this.sensorsFinishedRemoving
                        + ", numSensors: "
                        + this.numSensors);
                if (this.sensorsFinishedRemoving == this.numSensors) {
                  iFaceServiceReceiver.onRemoved(new Face("", 0, 0L), 0);
                }
              }
            }

            public void onError(int i2, int i3) {
              Log.e(
                  "FaceService", "removeAll internalReceiver : error = " + i2 + ", vendor = " + i3);
              iFaceServiceReceiver.onError(i2, i3);
            }
          };
      for (ServiceProvider serviceProvider : FaceService.this.mRegistry.getProviders()) {
        Iterator it = serviceProvider.getSensorProperties().iterator();
        while (it.hasNext()) {
          serviceProvider.scheduleRemoveAll(
              ((FaceSensorPropertiesInternal) it.next()).sensorId,
              iBinder,
              i,
              iFaceServiceReceiver2,
              str);
        }
      }
    }

    public void addLockoutResetCallback(
        IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) {
      String str2;
      super.addLockoutResetCallback_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("addLockoutResetCallback called from pid=");
      sb.append(Binder.getCallingPid());
      if (Utils.DEBUG) {
        str2 = ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      FaceService.this.mLockoutResetDispatcher.addCallback(
          iBiometricServiceLockoutResetCallback, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(
        FileDescriptor fileDescriptor,
        FileDescriptor fileDescriptor2,
        FileDescriptor fileDescriptor3,
        String[] strArr,
        ShellCallback shellCallback,
        ResultReceiver resultReceiver) {
      new FaceShellCommand(FaceService.this)
          .exec(
              this,
              fileDescriptor,
              fileDescriptor2,
              fileDescriptor3,
              strArr,
              shellCallback,
              resultReceiver);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
      Slog.i("FaceService", "dump: ");
      if (DumpUtils.checkDumpPermission(
          FaceService.this.getContext(), "FaceService", printWriter)) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          if ((Build.IS_ENG || Build.IS_USERDEBUG)
              && strArr.length >= 2
              && "--tpa".equals(strArr[0])) {
            if ("mode".equals(strArr[1])) {
              final boolean equals = "1".equals(strArr[2]);
              SemFaceMainThread.get()
                  .getHandler()
                  .post(
                      new Runnable() { // from class:
                                       // com.android.server.biometrics.sensors.face.FaceService$FaceServiceWrapper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                          FaceService.FaceServiceWrapper.this.lambda$dump$0(equals);
                        }
                      });
              printWriter.println("tpa mode: " + equals);
            } else if ("update".equals(strArr[1])) {
              SemFaceMainThread.get()
                  .getHandler()
                  .post(
                      new Runnable() { // from class:
                                       // com.android.server.biometrics.sensors.face.FaceService$FaceServiceWrapper$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                          FaceService.FaceServiceWrapper.this.lambda$dump$1();
                        }
                      });
              printWriter.println("tpa mode: update action");
            }
            return;
          }
          if (strArr.length > 1 && "--proto".equals(strArr[0]) && "--state".equals(strArr[1])) {
            ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
            for (ServiceProvider serviceProvider : FaceService.this.mRegistry.getProviders()) {
              Iterator it = serviceProvider.getSensorProperties().iterator();
              while (it.hasNext()) {
                serviceProvider.dumpProtoState(
                    ((FaceSensorPropertiesInternal) it.next()).sensorId, protoOutputStream, false);
              }
            }
            protoOutputStream.flush();
          } else if (strArr.length > 0 && "--proto".equals(strArr[0])) {
            for (ServiceProvider serviceProvider2 : FaceService.this.mRegistry.getProviders()) {
              Iterator it2 = serviceProvider2.getSensorProperties().iterator();
              while (it2.hasNext()) {
                serviceProvider2.dumpProtoMetrics(
                    ((FaceSensorPropertiesInternal) it2.next()).sensorId, fileDescriptor);
              }
            }
          } else if (strArr.length > 1 && "--hal".equals(strArr[0])) {
            for (ServiceProvider serviceProvider3 : FaceService.this.mRegistry.getProviders()) {
              Iterator it3 = serviceProvider3.getSensorProperties().iterator();
              while (it3.hasNext()) {
                serviceProvider3.dumpHal(
                    ((FaceSensorPropertiesInternal) it3.next()).sensorId,
                    fileDescriptor,
                    (String[]) Arrays.copyOfRange(strArr, 1, strArr.length, strArr.getClass()));
              }
            }
          } else {
            for (ServiceProvider serviceProvider4 : FaceService.this.mRegistry.getProviders()) {
              for (FaceSensorPropertiesInternal faceSensorPropertiesInternal :
                  serviceProvider4.getSensorProperties()) {
                printWriter.println(
                    "Dumping for sensorId: "
                        + faceSensorPropertiesInternal.sensorId
                        + ", provider: "
                        + serviceProvider4.getClass().getSimpleName());
                serviceProvider4.dumpInternal(faceSensorPropertiesInternal.sensorId, printWriter);
                printWriter.println();
              }
            }
          }
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$0(boolean z) {
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider != null) {
        ((ServiceProvider) singleProvider.second).semSetTpaHalEnabled(z);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$1() {
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider != null) {
        ((ServiceProvider) singleProvider.second).semUpdateTpaAction();
      }
    }

    public boolean isHardwareDetected(int i, String str) {
      String str2;
      if (!Utils.hasPermission(
          FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
        super.isHardwareDetected_enforcePermission();
      }
      StringBuilder sb = new StringBuilder();
      sb.append("isHardwareDetected called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append("[");
      sb.append(i);
      sb.append("] ");
      if (Utils.DEBUG) {
        str2 = ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      Slog.i("FaceService", sb.toString());
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        ServiceProvider serviceProvider =
            (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
        if (serviceProvider == null) {
          Slog.w("FaceService", "Null provider for isHardwareDetected, caller: " + str);
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return false;
        }
        return serviceProvider.isHardwareDetected(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public List getEnrolledFaces(int i, int i2, String str) {
      String str2;
      if (!Utils.hasPermission(
          FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
        super.getEnrolledFaces_enforcePermission();
      }
      if (i2 != UserHandle.getCallingUserId()) {
        Utils.checkPermission(
            FaceService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
      }
      int userOrWorkProfileId = Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2);
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FaceService", "Null provider for getEnrolledFaces, caller: " + str);
        return Collections.emptyList();
      }
      List enrolledFaces = serviceProvider.getEnrolledFaces(i, userOrWorkProfileId);
      StringBuilder sb = new StringBuilder();
      sb.append("getEnrolledFaces called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append("[");
      sb.append(userOrWorkProfileId);
      sb.append("][");
      sb.append(i);
      sb.append("] ");
      if (Utils.DEBUG) {
        str2 = ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      sb.append(", size = ");
      sb.append(enrolledFaces.size());
      Slog.i("FaceService", sb.toString());
      return enrolledFaces;
    }

    public boolean hasEnrolledFaces(int i, int i2, String str) {
      String str2;
      if (!Utils.hasPermission(
          FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
        super.hasEnrolledFaces_enforcePermission();
      }
      if (i2 != UserHandle.getCallingUserId()) {
        Utils.checkPermission(
            FaceService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
      }
      int userOrWorkProfileId = Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2);
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FaceService", "Null provider for hasEnrolledFaces, caller: " + str);
        return false;
      }
      List enrolledFaces = serviceProvider.getEnrolledFaces(i, userOrWorkProfileId);
      StringBuilder sb = new StringBuilder();
      sb.append("hasEnrolledFaces called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append("[");
      sb.append(userOrWorkProfileId);
      sb.append("][");
      sb.append(i);
      sb.append("] ");
      if (Utils.DEBUG) {
        str2 = ", " + str;
      } else {
        str2 = "";
      }
      sb.append(str2);
      sb.append(", size = ");
      sb.append(enrolledFaces.size());
      Slog.i("FaceService", sb.toString());
      return enrolledFaces.size() > 0;
    }

    public int getLockoutModeForUser(int i, int i2) {
      super.getLockoutModeForUser_enforcePermission();
      Slog.i(
          "FaceService",
          "getLockoutModeForUser called from pid="
              + Binder.getCallingPid()
              + "["
              + i2
              + "]["
              + i
              + "] ");
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FaceService", "Null provider for getLockoutModeForUser");
        return 0;
      }
      return serviceProvider.getLockoutModeForUser(
          i, Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2));
    }

    public void invalidateAuthenticatorId(
        int i, int i2, IInvalidationCallback iInvalidationCallback) {
      super.invalidateAuthenticatorId_enforcePermission();
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FaceService", "Null provider for invalidateAuthenticatorId");
      } else {
        serviceProvider.scheduleInvalidateAuthenticatorId(
            i,
            Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2),
            iInvalidationCallback);
      }
    }

    public long getAuthenticatorId(int i, int i2) {
      super.getAuthenticatorId_enforcePermission();
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FaceService", "Null provider for getAuthenticatorId");
        return 0L;
      }
      return serviceProvider.getAuthenticatorId(
          i, Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2));
    }

    public void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) {
      super.resetLockout_enforcePermission();
      ServiceProvider serviceProvider =
          (ServiceProvider) FaceService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FaceService", "Null provider for resetLockout, caller: " + str);
        return;
      }
      serviceProvider.scheduleResetLockout(
          i, Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i2), bArr);
    }

    public void setFeature(
        IBinder iBinder,
        int i,
        int i2,
        boolean z,
        byte[] bArr,
        IFaceServiceReceiver iFaceServiceReceiver,
        String str) {
      super.setFeature_enforcePermission();
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FaceService", "Null provider for setFeature");
      } else {
        ((ServiceProvider) singleProvider.second)
            .scheduleSetFeature(
                ((Integer) singleProvider.first).intValue(),
                iBinder,
                Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i),
                i2,
                z,
                bArr,
                iFaceServiceReceiver,
                str);
      }
    }

    public void getFeature(
        IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) {
      super.getFeature_enforcePermission();
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FaceService", "Null provider for getFeature");
      } else {
        ((ServiceProvider) singleProvider.second)
            .scheduleGetFeature(
                ((Integer) singleProvider.first).intValue(),
                iBinder,
                Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i),
                i2,
                new ClientMonitorCallbackConverter(iFaceServiceReceiver),
                str);
      }
    }

    public final List getAidlProviders() {
      ArrayList arrayList = new ArrayList();
      String[] declaredInstances = ServiceManager.getDeclaredInstances(IFace.DESCRIPTOR);
      if (declaredInstances == null || declaredInstances.length == 0) {
        Slog.d("FaceService", "getAidlProviders no instance");
        return arrayList;
      }
      for (String str : declaredInstances) {
        String str2 = IFace.DESCRIPTOR + "/" + str;
        IFace asInterface =
            IFace.Stub.asInterface(
                Binder.allowBlocking(ServiceManager.waitForDeclaredService(str2)));
        if (asInterface == null) {
          Slog.e("FaceService", "Unable to get declared service: " + str2);
        } else {
          try {
            SensorProps[] sensorProps = asInterface.getSensorProps();
            arrayList.add(
                new FaceProvider(
                    FaceService.this.getContext(),
                    FaceService.this.mBiometricStateCallback,
                    sensorProps,
                    str,
                    FaceService.this.mLockoutResetDispatcher,
                    BiometricContext.getInstance(FaceService.this.getContext())));
            FaceService.this.mAidlEnabled = true;
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
              Slog.d("FaceService", "addAidlProviders : " + str2);
            }
          } catch (RemoteException unused) {
            Slog.e("FaceService", "Remote exception in getSensorProps: " + str2);
          }
        }
      }
      return arrayList;
    }

    public void registerAuthenticators(final List list) {
      super.registerAuthenticators_enforcePermission();
      FaceService.this.mRegistry.registerAll(
          new Supplier() { // from class:
                           // com.android.server.biometrics.sensors.face.FaceService$FaceServiceWrapper$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
              List lambda$registerAuthenticators$2;
              lambda$registerAuthenticators$2 =
                  FaceService.FaceServiceWrapper.this.lambda$registerAuthenticators$2(list);
              return lambda$registerAuthenticators$2;
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List lambda$registerAuthenticators$2(List list) {
      ArrayList arrayList = new ArrayList();
      FaceService.this.mAidlEnabled = false;
      arrayList.addAll(getAidlProviders());
      if (!FaceService.this.mAidlEnabled) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
          arrayList.add(
              Face10.newInstance(
                  FaceService.this.getContext(),
                  FaceService.this.mBiometricStateCallback,
                  (FaceSensorPropertiesInternal) it.next(),
                  FaceService.this.mLockoutResetDispatcher));
        }
      }
      return arrayList;
    }

    public void addAuthenticatorsRegisteredCallback(
        IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) {
      Utils.checkPermission(
          FaceService.this.getContext(), "android.permission.USE_BIOMETRIC_INTERNAL");
      FaceService.this.mRegistry.addAllRegisteredCallback(iFaceAuthenticatorsRegisteredCallback);
    }

    public void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) {
      FaceService.this.mBiometricStateCallback.registerBiometricStateListener(
          iBiometricStateListener);
    }

    public long semAuthenticate(
        IBinder iBinder,
        long j,
        IFaceServiceReceiver iFaceServiceReceiver,
        FaceAuthenticateOptions faceAuthenticateOptions,
        Bundle bundle,
        byte[] bArr) {
      String str;
      StringBuilder sb = new StringBuilder();
      sb.append("semAuthenticate called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append(" [");
      sb.append(faceAuthenticateOptions.getUserId());
      sb.append("] ");
      if (Utils.DEBUG) {
        str =
            ", "
                + iBinder
                + ", "
                + j
                + ", "
                + faceAuthenticateOptions.getOpPackageName()
                + ", "
                + bundle.toString();
      } else {
        str = "";
      }
      sb.append(str);
      Slog.i("FaceService", sb.toString());
      if (!Utils.hasPermission(
          FaceService.this.getContext(), "com.samsung.android.bio.face.permission.USE_FACE")) {
        super.authenticate_enforcePermission();
      }
      SemFaceUtils.setFidoRequestData(bArr);
      SemFaceUtils.setBundle(bundle);
      return authenticate(iBinder, j, iFaceServiceReceiver, faceAuthenticateOptions);
    }

    public long semAuthenticateExt(
        IBinder iBinder,
        long j,
        IFaceServiceReceiver iFaceServiceReceiver,
        FaceAuthenticateOptions faceAuthenticateOptions,
        Surface surface,
        byte[] bArr) {
      String str;
      StringBuilder sb = new StringBuilder();
      sb.append("semAuthenticate (s) called from pid=");
      sb.append(Binder.getCallingPid());
      sb.append(" [");
      sb.append(faceAuthenticateOptions.getUserId());
      sb.append("] ");
      if (Utils.DEBUG) {
        str = ", " + iBinder + ", " + j + ", " + faceAuthenticateOptions.getOpPackageName();
      } else {
        str = "";
      }
      sb.append(str);
      Slog.i("FaceService", sb.toString());
      Utils.checkPermission(FaceService.this.getContext(), "android.permission.MANAGE_BIOMETRIC");
      SemFaceUtils.setFidoRequestData(bArr);
      SemFaceUtils.setSurface(surface);
      return authenticate(iBinder, j, iFaceServiceReceiver, faceAuthenticateOptions);
    }

    public boolean semIsEnrollSession() {
      super.semIsEnrollSession_enforcePermission();
      Slog.i("FaceService", "semIsEnrollSession called from pid=" + Binder.getCallingPid());
      return FaceService.this.mIsEnrollSession.get();
    }

    public void semPauseEnroll() {
      super.semPauseEnroll_enforcePermission();
      Slog.i("FaceService", "semPauseEnroll called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return;
      }
      ((ServiceProvider) singleProvider.second).semPauseEnroll();
    }

    public void semResumeEnroll() {
      super.semResumeEnroll_enforcePermission();
      Slog.i("FaceService", "semResumeEnroll called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return;
      }
      ((ServiceProvider) singleProvider.second).semResumeEnroll();
    }

    public void semPauseAuth() {
      super.semPauseAuth_enforcePermission();
      Slog.i("FaceService", "semPauseAuth called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return;
      }
      ((ServiceProvider) singleProvider.second).semPauseAuth();
    }

    public void semResumeAuth() {
      super.semResumeAuth_enforcePermission();
      Slog.i("FaceService", "semResumeAuth called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return;
      }
      ((ServiceProvider) singleProvider.second).semResumeAuth();
    }

    public String semGetInfo(int i) {
      super.semGetInfo_enforcePermission();
      Slog.i("FaceService", "semGetInfo called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return null;
      }
      return ((ServiceProvider) singleProvider.second).semGetInfo(i);
    }

    public boolean semResetAuthenticationTimeout() {
      super.semResetAuthenticationTimeout_enforcePermission();
      Slog.i(
          "FaceService", "semResetAuthenticationTimeout called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return false;
      }
      return ((ServiceProvider) singleProvider.second).semResetAuthenticationTimeout();
    }

    public void semSessionOpen() {
      super.semSessionOpen_enforcePermission();
      Slog.i("FaceService", "semSessionOpen called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return;
      }
      ((ServiceProvider) singleProvider.second).semSessionOpen();
    }

    public void semSessionClose() {
      super.semSessionClose_enforcePermission();
      Slog.i("FaceService", "semSessionClose called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return;
      }
      ((ServiceProvider) singleProvider.second).semSessionClose();
    }

    public boolean semIsSessionClose() {
      super.semIsSessionClose_enforcePermission();
      Slog.i("FaceService", "semIsSessionClose called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return false;
      }
      return ((ServiceProvider) singleProvider.second).semIsSessionClose();
    }

    public int semGetSecurityLevel(boolean z) {
      super.semGetSecurityLevel_enforcePermission();
      Slog.i("FaceService", "semGetSecurityLevel called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return 0;
      }
      return ((ServiceProvider) singleProvider.second).semGetSecurityLevel(z);
    }

    public boolean semIsFrameworkHandleLockout() {
      super.semIsFrameworkHandleLockout_enforcePermission();
      Slog.i(
          "FaceService", "semIsFrameworkHandleLockout called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return false;
      }
      return ((ServiceProvider) singleProvider.second).semIsFrameworkHandleLockout();
    }

    public int semGetRemainingLockoutTime(int i) {
      super.semGetRemainingLockoutTime_enforcePermission();
      Slog.i("FaceService", "semGetRemainingLockoutTime called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return 0;
      }
      return ((ServiceProvider) singleProvider.second)
          .semGetRemainingLockoutTime(
              Utils.getUserOrWorkProfileId(FaceService.this.getContext(), i));
    }

    public boolean semShouldRemoveTemplate() {
      super.semGetRemainingLockoutTime_enforcePermission();
      Slog.i("FaceService", "semShouldRemoveTemplate called from pid=" + Binder.getCallingPid());
      Pair singleProvider = FaceService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return false;
      }
      return ((ServiceProvider) singleProvider.second).semShouldRemoveTemplate();
    }
  }

  public FaceService(Context context) {
    super(context);
    this.mIsEnrollSession = new AtomicBoolean(false);
    FaceServiceWrapper faceServiceWrapper = new FaceServiceWrapper();
    this.mServiceWrapper = faceServiceWrapper;
    this.mLockoutResetDispatcher = new LockoutResetDispatcher(context);
    this.mLockPatternUtils = new LockPatternUtils(context);
    this.mBiometricStateCallback = new BiometricStateCallback(UserManager.get(context));
    FaceServiceRegistry faceServiceRegistry =
        new FaceServiceRegistry(
            faceServiceWrapper,
            new Supplier() { // from class:
                             // com.android.server.biometrics.sensors.face.FaceService$$ExternalSyntheticLambda0
              @Override // java.util.function.Supplier
              public final Object get() {
                IBiometricService lambda$new$0;
                lambda$new$0 = FaceService.lambda$new$0();
                return lambda$new$0;
              }
            });
    this.mRegistry = faceServiceRegistry;
    faceServiceRegistry.addAllRegisteredCallback(
        new IFaceAuthenticatorsRegisteredCallback
            .Stub() { // from class: com.android.server.biometrics.sensors.face.FaceService.1
          public void onAllAuthenticatorsRegistered(List list) {
            FaceService.this.mBiometricStateCallback.start(
                FaceService.this.mRegistry.getProviders());
          }
        });
  }

  public static /* synthetic */ IBiometricService lambda$new$0() {
    return IBiometricService.Stub.asInterface(ServiceManager.getService("biometric"));
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    publishBinderService("face", this.mServiceWrapper);
  }

  public void syncEnrollmentsNow() {
    Utils.checkPermissionOrShell(
        getContext(), "com.samsung.android.bio.face.permission.MANAGE_FACE");
    if (Utils.isVirtualEnabled(getContext())) {
      Slog.i("FaceService", "Sync virtual enrollments");
      int currentUser = ActivityManager.getCurrentUser();
      for (ServiceProvider serviceProvider : this.mRegistry.getProviders()) {
        Iterator it = serviceProvider.getSensorProperties().iterator();
        while (it.hasNext()) {
          serviceProvider.scheduleInternalCleanup(
              ((FaceSensorPropertiesInternal) it.next()).sensorId, currentUser, null, true);
        }
      }
    }
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(int i) {
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider == null) {
      Slog.w("FaceService", "Null provider for onBootPhase");
    } else {
      ((ServiceProvider) singleProvider.second).onBootPhase(i);
    }
  }
}
