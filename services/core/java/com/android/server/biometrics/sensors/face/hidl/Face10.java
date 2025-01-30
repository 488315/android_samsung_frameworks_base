package com.android.server.biometrics.sensors.face.hidl;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.SynchronousUserSwitchObserver;
import android.app.UserSwitchObserver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.hardware.SensorEvent;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.face.V1_0.IBiometricsFace;
import android.hardware.face.Face;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.face.IFaceServiceReceiver;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.Handler;
import android.os.HidlMemory;
import android.os.HidlMemoryUtil;
import android.os.IBinder;
import android.os.IHwBinder;
import android.os.MemoryFile;
import android.os.Message;
import android.os.NativeHandle;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.OrientationEventListener;
import android.view.Surface;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBiometricBoostingManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.AuthenticationConsumer;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricNotificationUtils;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.BiometricStateCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.EnumerateConsumer;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.RemovalConsumer;
import com.android.server.biometrics.sensors.face.FaceUtils;
import com.android.server.biometrics.sensors.face.LockoutHalImpl;
import com.android.server.biometrics.sensors.face.SemFaceBrightManager;
import com.android.server.biometrics.sensors.face.SemFaceMainThread;
import com.android.server.biometrics.sensors.face.SemFaceScheduler;
import com.android.server.biometrics.sensors.face.SemFaceUtils;
import com.android.server.biometrics.sensors.face.SemProximitySensorObserver;
import com.android.server.biometrics.sensors.face.ServiceProvider;
import com.android.server.biometrics.sensors.face.UsageStats;
import com.android.server.display.DisplayPowerController2;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace;
import vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFaceClientCallback;

/* loaded from: classes.dex */
public class Face10 implements IHwBinder.DeathRecipient, ServiceProvider {
  public static ProviderExtensionImpl mProviderExtImpl;
  public static Clock sSystemClock = Clock.systemUTC();
  public final Map mAuthenticatorIds;
  public final BiometricContext mBiometricContext;
  public final BiometricStateCallback mBiometricStateCallback;
  public final Context mContext;
  public IBiometricsFace mDaemon;
  public final HalResultController mHalResultController;
  public final Handler mHandler;
  public final Supplier mLazyDaemon;
  public final SemLockoutFrameworkImpl.LockoutResetCallback mLockoutResetCallback;
  public final SemLockoutFrameworkImpl mLockoutTracker;
  public final BiometricScheduler mScheduler;
  public final int mSensorId;
  public final FaceSensorPropertiesInternal mSensorProperties;
  public boolean mTestHalEnabled;
  public final UsageStats mUsageStats;
  public final UserSwitchObserver mUserSwitchObserver;
  public boolean mTpaHalModeEnabled = false;
  public final AtomicLong mRequestCounter = new AtomicLong(0);
  public int mCurrentUserId = -10000;
  public final List mGeneratedChallengeCount = new ArrayList();
  public FaceGenerateChallengeClient mGeneratedChallengeCache = null;

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public void dumpProtoMetrics(int i, FileDescriptor fileDescriptor) {}

  public int getGenerateChallengeReuseInterval() {
    return 0;
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public boolean isHardwareDetected(int i) {
    return true;
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public boolean semIsFrameworkHandleLockout() {
    return true;
  }

  public class HalResultController extends ISehBiometricsFaceClientCallback.Stub {
    public Callback mCallback;
    public final Context mContext;
    public boolean mHalResultTestHalEnabled = false;
    public final Handler mHandler;
    public final LockoutResetDispatcher mLockoutResetDispatcher;
    public final BiometricScheduler mScheduler;
    public final int mSensorId;

    public interface Callback {
      void onHardwareUnavailable();
    }

    public HalResultController(
        int i,
        Context context,
        Handler handler,
        BiometricScheduler biometricScheduler,
        LockoutHalImpl lockoutHalImpl,
        LockoutResetDispatcher lockoutResetDispatcher) {
      this.mSensorId = i;
      this.mContext = context;
      this.mHandler = handler;
      this.mScheduler = biometricScheduler;
      this.mLockoutResetDispatcher = lockoutResetDispatcher;
    }

    public void setCallback(Callback callback) {
      this.mCallback = callback;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onEnrollResult(final long j, final int i, final int i2, final int i3) {
      Log.d("Face10", "onEnrollResult: " + j + ", " + i2 + ", " + i + ", " + i3);
      if (i3 == 0 && Face10.mProviderExtImpl.mDaemonIsCancelling) {
        Face10.mProviderExtImpl.mShouldRemoveRegisteredFaceOnCancelling = true;
      }
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              Face10.HalResultController.this.lambda$onEnrollResult$0(i3, i, i2, j);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollResult$0(int i, int i2, int i3, long j) {
      if (i == 0) {
        Face10.mProviderExtImpl.stopOperation();
        if (Face10.mProviderExtImpl.mShouldRemoveRegisteredFaceOnCancelling) {
          Face10.mProviderExtImpl.mShouldRemoveRegisteredFaceOnCancelling = false;
          try {
            Face10.mProviderExtImpl.daemonRemove(i2);
          } catch (Exception e) {
            Log.e("Face10", "onEnrollResult: " + e.getMessage());
          }
          Log.i(
              "Face10", "onEnrollResult: remove registered face as enrollment is being cancelled");
          return;
        }
      }
      CharSequence uniqueName =
          FaceUtils.getLegacyInstance(this.mSensorId).getUniqueName(this.mContext, i3);
      BiometricAuthenticator.Identifier face = new Face(uniqueName, i2, j);
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof FaceEnrollClient)) {
        Slog.e(
            "Face10",
            "onEnrollResult for non-enroll client: " + Utils.getClientName(currentClient));
        return;
      }
      Face10.mProviderExtImpl.onEnrollResultExt(uniqueName.toString(), i2, i);
      ((FaceEnrollClient) currentClient).onEnrollResult(face, i);
      Face10.mProviderExtImpl.onEnrollResult(uniqueName.toString(), i2, i);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onAuthenticated(long j, int i, int i2, ArrayList arrayList) {
      onAuthenticatedInternal(j, i, i2, arrayList, true);
    }

    public final void onAuthenticatedInternal(
        final long j, final int i, final int i2, final ArrayList arrayList, boolean z) {
      if (!Face10.mProviderExtImpl.mIsOperationStarted) {
        Log.d("Face10", "onAuthenticated: skip (" + i + ") after stop()");
        return;
      }
      Log.d("Face10", "onAuthenticated: [" + j + "] " + i + "," + i2);
      if (i == 0 && z && !this.mHalResultTestHalEnabled) {
        Face10.mProviderExtImpl.mIsAuthenticateResult = true;
      } else {
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                Face10.HalResultController.this.lambda$onAuthenticatedInternal$1(
                    i, j, i2, arrayList);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onAuthenticatedInternal$1(
        int i, long j, int i2, ArrayList arrayList) {
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof AuthenticationConsumer)) {
        Slog.e(
            "Face10",
            "onAuthenticated for non-authentication consumer: "
                + Utils.getClientName(currentClient));
        return;
      }
      AuthenticationConsumer authenticationConsumer = (AuthenticationConsumer) currentClient;
      boolean z = i > 0;
      Face face = new Face("", i, j);
      if (z && (currentClient instanceof FaceAuthenticationClient)) {
        FaceAuthenticationClient faceAuthenticationClient =
            (FaceAuthenticationClient) currentClient;
        if (!faceAuthenticationClient.semIsAllowedBackgroundAuthentication()) {
          if (!Utils.isBackground(currentClient.getOwnerString())) {
            faceAuthenticationClient.semVerifyTopActivity();
          } else {
            Slog.e(
                "Face10",
                "Background authentication detected, client: " + currentClient.getOwnerString());
          }
        }
        SemBioLoggingManager.get()
            .faceMatch(
                this.mContext,
                currentClient.getHashID(),
                0L,
                i,
                SemFaceUtils.getEnrolledPositionForFaceID(i, i2));
      }
      ProviderExtensionImpl providerExtensionImpl = Face10.mProviderExtImpl;
      ProviderExtensionImpl unused = Face10.mProviderExtImpl;
      providerExtensionImpl.onAuthenticatedExt(z ? 1 : 2);
      authenticationConsumer.onAuthenticated(face, z, arrayList);
      Face10.mProviderExtImpl.stopOperation();
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onAcquired(long j, int i, final int i2, final int i3) {
      String str;
      if (Face10.mProviderExtImpl.onPreAcquired(i2, i3) == 1) {
        return;
      }
      StringBuilder sb = new StringBuilder();
      sb.append("onAcquired: ");
      sb.append(j);
      sb.append(", ");
      sb.append(i);
      sb.append(", ");
      sb.append(i2);
      boolean z = Utils.DEBUG;
      String str2 = "";
      if (z) {
        str = "(" + FaceManager.getAcquiredName(i2) + ")";
      } else {
        str = "";
      }
      sb.append(str);
      sb.append(", ");
      sb.append(i3);
      if (z) {
        str2 = "(" + FaceManager.getAcquiredName(i3) + ")";
      }
      sb.append(str2);
      Slog.d("Face10", sb.toString());
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              Face10.HalResultController.this.lambda$onAcquired$2(i2, i3);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAcquired$2(int i, int i2) {
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof AcquisitionClient)) {
        Slog.e(
            "Face10", "onAcquired for non-acquire client: " + Utils.getClientName(currentClient));
        return;
      }
      ((AcquisitionClient) currentClient).onAcquired(i, i2);
      Face10.mProviderExtImpl.onAcquired(i, i2);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onError(long j, int i, final int i2, final int i3) {
      if (this.mHalResultTestHalEnabled || Face10.mProviderExtImpl.onPreError(i2, i3) != 1) {
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                Face10.HalResultController.this.lambda$onError$3(i2, i3);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onError$3(int i, int i2) {
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      StringBuilder sb = new StringBuilder();
      sb.append("handleError, client: ");
      sb.append(currentClient != 0 ? currentClient.getOwnerString() : null);
      sb.append(", error: ");
      sb.append(i);
      sb.append(", vendorCode: ");
      sb.append(i2);
      Slog.d("Face10", sb.toString());
      if (!(currentClient instanceof ErrorConsumer)) {
        Slog.e("Face10", "onError for non-error consumer: " + Utils.getClientName(currentClient));
        return;
      }
      Face10.mProviderExtImpl.onErrorExt(i, i2);
      ((ErrorConsumer) currentClient).onError(i, i2);
      if (i == 1) {
        Slog.e("Face10", "Got ERROR_HW_UNAVAILABLE");
        Callback callback = this.mCallback;
        if (callback != null) {
          callback.onHardwareUnavailable();
        }
      }
      Face10.mProviderExtImpl.onError(i, i2);
      Face10.mProviderExtImpl.stopOperation();
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onRemoved(final long j, final ArrayList arrayList, int i) {
      Log.d("Face10", "onRemoved: " + j + ", " + i + ", " + arrayList.size());
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
              Face10.HalResultController.this.lambda$onRemoved$4(arrayList, j);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onRemoved$4(ArrayList arrayList, long j) {
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof RemovalConsumer)) {
        Slog.e(
            "Face10", "onRemoved for non-removal consumer: " + Utils.getClientName(currentClient));
        return;
      }
      RemovalConsumer removalConsumer = (RemovalConsumer) currentClient;
      if (!arrayList.isEmpty()) {
        for (int i = 0; i < arrayList.size(); i++) {
          int intValue = ((Integer) arrayList.get(i)).intValue();
          Face face = new Face("", intValue, j);
          int size = (arrayList.size() - i) - 1;
          Slog.d("Face10", "Removed, faceId: " + intValue + ", remaining: " + size);
          removalConsumer.onRemoved(face, size);
          Face10.mProviderExtImpl.onRemovedExt("", intValue);
        }
      } else {
        removalConsumer.onRemoved(new Face("", 0, j), 0);
      }
      Settings.Secure.putIntForUser(
          this.mContext.getContentResolver(), "face_unlock_re_enroll", 0, -2);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onEnumerate(final long j, final ArrayList arrayList, final int i) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              Face10.HalResultController.this.lambda$onEnumerate$5(j, i, arrayList);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onEnumerate$5(long j, int i, ArrayList arrayList) {
      Log.d("Face10", "onEnumerate: " + j + ", " + i + ", " + arrayList.size());
      Face10.mProviderExtImpl.doTemplateSyncForUser(j, arrayList, i);
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof EnumerateConsumer)) {
        Slog.e(
            "Face10",
            "onEnumerate for non-enumerate consumer: " + Utils.getClientName(currentClient));
        return;
      }
      EnumerateConsumer enumerateConsumer = (EnumerateConsumer) currentClient;
      if (!arrayList.isEmpty()) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
          enumerateConsumer.onEnumerationResult(
              new Face("", ((Integer) arrayList.get(i2)).intValue(), j),
              (arrayList.size() - i2) - 1);
        }
        return;
      }
      enumerateConsumer.onEnumerationResult(null, 0);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onLockoutChanged(long j) {
      Slog.d("Face10", "onLockoutChanged");
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback
    public void sehOnPreviewUpdated(ArrayList arrayList, int i, int i2, int i3, int i4) {
      Slog.d("Face10", "sehOnPreviewUpdated: " + i + "," + i2 + "," + i3 + "," + i4);
      if (!Face10.mProviderExtImpl.mIsOperationStarted) {
        Slog.d("Face10", "sehOnPreviewUpdated: skip after stop");
        return;
      }
      if (arrayList == null || arrayList.size() == 0) {
        Slog.d("Face10", "sehOnPreviewUpdated: preview data is null or size is 0");
        return;
      }
      if (Face10.mProviderExtImpl.mPreviewImage != null) {
        Slog.d("Face10", "sehOnPreviewUpdated: previous preview is not processed yet");
        return;
      }
      Face10.mProviderExtImpl.mPreviewImage = new byte[arrayList.size()];
      for (int i5 = 0; i5 < arrayList.size(); i5++) {
        Face10.mProviderExtImpl.mPreviewImage[i5] = ((Byte) arrayList.get(i5)).byteValue();
      }
      Face10.mProviderExtImpl.sendImageProcessed(i, i2, i4, i3);
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback
    public void sehOnPreviewFrame(HidlMemory hidlMemory, int i, int i2, int i3, int i4) {
      Slog.d("Face10", "sehOnPreviewFrame: " + i + "," + i2 + "," + i3 + "," + i4);
      if (!Face10.mProviderExtImpl.mIsOperationStarted) {
        Slog.d("Face10", "sehOnPreviewUpdated: skip after stop");
        return;
      }
      if (hidlMemory == null) {
        Slog.d("Face10", "sehOnPreviewFrame: preview data is null");
      } else {
        if (Face10.mProviderExtImpl.mPreviewImage != null) {
          Slog.d("Face10", "sehOnPreviewFrame: previous preview is not processed yet");
          return;
        }
        Face10.mProviderExtImpl.mPreviewImage = HidlMemoryUtil.hidlMemoryToByteArray(hidlMemory);
        Face10.mProviderExtImpl.sendImageProcessed(i, i2, i4, i3);
      }
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback
    public void sehOnAuthenticated(
        long j, int i, int i2, ArrayList arrayList, ArrayList arrayList2) {
      Slog.d("Face10", "sehOnAuthenticated: [" + i2 + "] " + i + "," + j + "," + arrayList2.size());
      if (arrayList2.size() > 0) {
        SemFaceUtils.setFidoResultData(arrayList2);
      }
      onAuthenticatedInternal(j, i, i2, arrayList, true);
    }

    @Override // vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFaceClientCallback
    public void sehOnAuthenticatedFromMemory(
        long j, final int i, int i2, ArrayList arrayList, final HidlMemory hidlMemory) {
      Slog.d("Face10", "sehOnAuthenticatedFromMemory: [" + i2 + "] " + i + "," + j);
      if (hidlMemory == null) {
        Slog.d("Face10", "sehOnAuthenticatedFromMemory: result data is null");
      } else {
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda5
              @Override // java.lang.Runnable
              public final void run() {
                Face10.HalResultController.lambda$sehOnAuthenticatedFromMemory$7(hidlMemory, i);
              }
            });
      }
    }

    public static /* synthetic */ void lambda$sehOnAuthenticatedFromMemory$7(
        HidlMemory hidlMemory, int i) {
      Bundle bundle = new Bundle();
      byte[] hidlMemoryToByteArray = HidlMemoryUtil.hidlMemoryToByteArray(hidlMemory);
      if (hidlMemoryToByteArray != null && hidlMemoryToByteArray.length > 0) {
        Slog.i("Face10", "sehOnAuthenticatedFromMemory read " + hidlMemoryToByteArray.length);
        if (Utils.DEBUG) {
          Slog.i(
              "Face10",
              "data = "
                  + Arrays.toString(
                      Arrays.copyOf(
                          hidlMemoryToByteArray,
                          hidlMemoryToByteArray.length <= 128
                              ? hidlMemoryToByteArray.length
                              : 128)));
        }
        try {
          if (Face10.mProviderExtImpl.mMemoryFileForAuthPreviewResult == null) {
            Face10.mProviderExtImpl.mMemoryFileForAuthPreviewResult =
                new MemoryFile("auth_preview", hidlMemoryToByteArray.length);
          }
          Face10.mProviderExtImpl.mMemoryFileForAuthPreviewResult.writeBytes(
              hidlMemoryToByteArray, 0, 0, hidlMemoryToByteArray.length);
          bundle.putParcelable(
              "memoryfile_descriptor",
              ParcelFileDescriptor.dup(
                  (FileDescriptor)
                      MemoryFile.class
                          .getDeclaredMethod("getFileDescriptor", new Class[0])
                          .invoke(
                              Face10.mProviderExtImpl.mMemoryFileForAuthPreviewResult,
                              new Object[0])));
        } catch (Exception e) {
          Log.w("Face10", "Unable to write statistics stream", e);
        }
        Slog.i("Face10", "sehOnAuthenticatedFromMemory save");
      } else {
        Slog.i("Face10", "sehOnAuthenticatedFromMemory data is null or 0");
      }
      if (i > 0) {
        Face10.mProviderExtImpl.sendSucceeded(bundle);
      } else if (i == 0) {
        Face10.mProviderExtImpl.sendFailed();
      } else {
        Slog.d("Face10", "sehOnAuthenticated: faceId is less than 0");
      }
    }
  }

  public Face10(
      Context context,
      BiometricStateCallback biometricStateCallback,
      FaceSensorPropertiesInternal faceSensorPropertiesInternal,
      LockoutResetDispatcher lockoutResetDispatcher,
      Handler handler,
      BiometricScheduler biometricScheduler,
      BiometricContext biometricContext) {
    SemLockoutFrameworkImpl.LockoutResetCallback lockoutResetCallback =
        new SemLockoutFrameworkImpl
            .LockoutResetCallback() { // from class:
                                      // com.android.server.biometrics.sensors.face.hidl.Face10.1
          @Override // com.android.server.biometrics.sensors.face.hidl.SemLockoutFrameworkImpl.LockoutResetCallback
          public void onLockoutReset(int i) {
            Face10.this
                .mBiometricContext
                .getAuthSessionCoordinator()
                .resetLockoutFor(
                    i, Utils.getCurrentStrength(Face10.this.mSensorProperties.sensorId), -1L);
            Face10.this.mHalResultController.mLockoutResetDispatcher.notifyLockoutResetCallbacks(
                Face10.this.mSensorProperties.sensorId);
          }
        };
    this.mLockoutResetCallback = lockoutResetCallback;
    SynchronousUserSwitchObserver synchronousUserSwitchObserver =
        new SynchronousUserSwitchObserver() { // from class:
                                              // com.android.server.biometrics.sensors.face.hidl.Face10.2
          public void onUserSwitching(int i) {
            Face10.this.scheduleInternalCleanup(i, null);
            Face10 face10 = Face10.this;
            face10.scheduleGetFeature(
                face10.mSensorId,
                new Binder(),
                i,
                1,
                null,
                Face10.this.mContext.getOpPackageName());
          }
        };
    this.mUserSwitchObserver = synchronousUserSwitchObserver;
    this.mSensorProperties = faceSensorPropertiesInternal;
    this.mContext = context;
    this.mBiometricStateCallback = biometricStateCallback;
    this.mSensorId = faceSensorPropertiesInternal.sensorId;
    this.mScheduler = biometricScheduler;
    this.mHandler = handler;
    mProviderExtImpl = new ProviderExtensionImpl(context);
    this.mBiometricContext = biometricContext;
    this.mUsageStats = new UsageStats(context);
    this.mAuthenticatorIds = new HashMap();
    this.mLazyDaemon =
        new Supplier() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda4
          @Override // java.util.function.Supplier
          public final Object get() {
            IBiometricsFace daemon;
            daemon = Face10.this.getDaemon();
            return daemon;
          }
        };
    this.mLockoutTracker = new SemLockoutFrameworkImpl(context, lockoutResetCallback);
    HalResultController halResultController =
        new HalResultController(
            faceSensorPropertiesInternal.sensorId,
            context,
            handler,
            biometricScheduler,
            null,
            lockoutResetDispatcher);
    this.mHalResultController = halResultController;
    halResultController.setCallback(
        new HalResultController
            .Callback() { // from class:
                          // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda5
          @Override // com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.Callback
          public final void onHardwareUnavailable() {
            Face10.this.lambda$new$0();
          }
        });
    try {
      ActivityManager.getService()
          .registerUserSwitchObserver(synchronousUserSwitchObserver, "Face10");
    } catch (RemoteException unused) {
      Slog.e("Face10", "Unable to register user switch observer");
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    this.mDaemon = null;
    this.mCurrentUserId = -10000;
  }

  public static Face10 newInstance(
      Context context,
      BiometricStateCallback biometricStateCallback,
      FaceSensorPropertiesInternal faceSensorPropertiesInternal,
      LockoutResetDispatcher lockoutResetDispatcher) {
    return new Face10(
        context,
        biometricStateCallback,
        faceSensorPropertiesInternal,
        lockoutResetDispatcher,
        SemFaceMainThread.get().getHandler(),
        new SemFaceScheduler("Face10"),
        BiometricContext.getInstance(context));
  }

  public void serviceDied(long j) {
    Slog.e("Face10", "HAL died");
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$serviceDied$1();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$serviceDied$1() {
    PerformanceTracker.getInstanceForSensorId(this.mSensorId).incrementHALDeathCount();
    this.mDaemon = null;
    this.mCurrentUserId = -10000;
    Object currentClient = this.mScheduler.getCurrentClient();
    if (currentClient instanceof ErrorConsumer) {
      Slog.e("Face10", "Sending ERROR_HW_UNAVAILABLE for client: " + currentClient);
      ((ErrorConsumer) currentClient).onError(1, 0);
      FrameworkStatsLog.write(148, 4, 1, -1);
    }
    this.mScheduler.recordCrashState();
    this.mScheduler.reset();
  }

  public final boolean isTpaSehTestHalEnabled() {
    return (Build.IS_USERDEBUG || Build.IS_ENG) && this.mTpaHalModeEnabled;
  }

  /* JADX WARN: Removed duplicated region for block: B:38:0x0129 A[Catch: all -> 0x0158, TryCatch #2 {, blocks: (B:3:0x0001, B:5:0x0007, B:9:0x0016, B:11:0x001a, B:14:0x002a, B:18:0x0030, B:20:0x004c, B:22:0x0050, B:24:0x006f, B:26:0x0074, B:29:0x007d, B:31:0x0086, B:33:0x008e, B:35:0x00a7, B:36:0x010f, B:38:0x0129, B:39:0x0154, B:42:0x014b, B:45:0x0106, B:46:0x009b, B:50:0x0057, B:52:0x005f, B:54:0x0068), top: B:2:0x0001, inners: #5 }] */
  /* JADX WARN: Removed duplicated region for block: B:42:0x014b A[Catch: all -> 0x0158, TryCatch #2 {, blocks: (B:3:0x0001, B:5:0x0007, B:9:0x0016, B:11:0x001a, B:14:0x002a, B:18:0x0030, B:20:0x004c, B:22:0x0050, B:24:0x006f, B:26:0x0074, B:29:0x007d, B:31:0x0086, B:33:0x008e, B:35:0x00a7, B:36:0x010f, B:38:0x0129, B:39:0x0154, B:42:0x014b, B:45:0x0106, B:46:0x009b, B:50:0x0057, B:52:0x005f, B:54:0x0068), top: B:2:0x0001, inners: #5 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final synchronized IBiometricsFace getDaemon() {
    long j;
    if (isTpaSehTestHalEnabled()) {
      SehTestHal sehTestHal = SehTestHal.getInstance(this.mContext, this.mSensorId);
      sehTestHal.setCallback(this.mHalResultController);
      return sehTestHal;
    }
    if (this.mTestHalEnabled) {
      TestHal testHal = new TestHal(this.mContext, this.mSensorId);
      testHal.setCallback(this.mHalResultController);
      return testHal;
    }
    IBiometricsFace iBiometricsFace = this.mDaemon;
    if (iBiometricsFace != null) {
      return iBiometricsFace;
    }
    Slog.d(
        "Face10",
        "Daemon was null, reconnecting, current operation: " + this.mScheduler.getCurrentClient());
    try {
      if (SemBiometricFeature.FEATURE_JDM_HAL) {
        this.mDaemon = ISehBiometricsFace.getService();
      } else {
        this.mDaemon = vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace.getService();
      }
    } catch (RemoteException e) {
      Slog.e("Face10", "Failed to get face HAL", e);
    } catch (NoSuchElementException e2) {
      Slog.w("Face10", "NoSuchElementException", e2);
    }
    IBiometricsFace iBiometricsFace2 = this.mDaemon;
    if (iBiometricsFace2 == null) {
      Slog.w("Face10", "Face HAL not available");
      return null;
    }
    iBiometricsFace2.asBinder().linkToDeath(this, 0L);
    try {
      long currentTimeMillis = System.currentTimeMillis();
      if (SemBiometricFeature.FEATURE_JDM_HAL) {
        j = ((ISehBiometricsFace) this.mDaemon).sehSetCallback(this.mHalResultController).value;
      } else {
        j =
            ((vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace) this.mDaemon)
                .sehSetCallbackEx(this.mHalResultController)
                .value;
      }
      try {
        Slog.w(
            "Face10",
            "SetCallback FINISH ("
                + (System.currentTimeMillis() - currentTimeMillis)
                + "ms) RESULT(HAL id): "
                + j);
        long currentTimeMillis2 = System.currentTimeMillis();
        ((ISehBiometricsFace) this.mDaemon)
            .sehGetSecurityLevel(
                new ISehBiometricsFace
                    .sehGetSecurityLevelCallback() { // from class:
                                                     // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda13
                  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace.sehGetSecurityLevelCallback
                  public final void onValues(int i, int i2) {
                    Face10.lambda$getDaemon$2(i, i2);
                  }
                });
        Slog.w(
            "Face10",
            "SecurityLevel FINISH (" + (System.currentTimeMillis() - currentTimeMillis2) + "ms) ");
        mProviderExtImpl.setHalDeviceId(j);
      } catch (RemoteException e3) {
        e = e3;
        Slog.e("Face10", "Failed to set callback for face HAL", e);
        this.mDaemon = null;
        Slog.d("Face10", "Face HAL ready, HAL ID: " + j);
        if (j == 0) {}
        return this.mDaemon;
      }
    } catch (RemoteException e4) {
      e = e4;
      j = 0;
    }
    Slog.d("Face10", "Face HAL ready, HAL ID: " + j);
    if (j == 0) {
      scheduleLoadAuthenticatorIds();
      scheduleInternalCleanup(ActivityManager.getCurrentUser(), null);
      scheduleGetFeature(
          this.mSensorId,
          new Binder(),
          ActivityManager.getCurrentUser(),
          1,
          null,
          this.mContext.getOpPackageName());
    } else {
      Slog.e("Face10", "Unable to set callback");
      this.mDaemon = null;
    }
    return this.mDaemon;
  }

  public static /* synthetic */ void lambda$getDaemon$2(int i, int i2) {
    if (i == 0) {
      mProviderExtImpl.setSecurityLevel(i2);
      StringBuilder sb = new StringBuilder();
      sb.append("SecurityLevel : ");
      sb.append(i2);
      sb.append(Utils.DEBUG ? " (Strong=1,2,3)" : "");
      Slog.w("Face10", sb.toString());
      return;
    }
    Slog.w("Face10", "SecurityLevel fail");
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public boolean containsSensor(int i) {
    return this.mSensorId == i;
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public List getSensorProperties() {
    ArrayList arrayList = new ArrayList();
    arrayList.add(this.mSensorProperties);
    return arrayList;
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public FaceSensorPropertiesInternal getSensorProperties(int i) {
    return this.mSensorProperties;
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public List getEnrolledFaces(int i, int i2) {
    return FaceUtils.getLegacyInstance(this.mSensorId).getBiometricsForUser(this.mContext, i2);
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public boolean hasEnrollments(int i, int i2) {
    return !getEnrolledFaces(i, i2).isEmpty();
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public int getLockoutModeForUser(int i, int i2) {
    return this.mBiometricContext
        .getAuthSessionCoordinator()
        .getLockoutStateFor(i2, Utils.getCurrentStrength(i));
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public long getAuthenticatorId(int i, int i2) {
    return ((Long) this.mAuthenticatorIds.getOrDefault(Integer.valueOf(i2), 0L)).longValue();
  }

  public final boolean isGeneratedChallengeCacheValid() {
    return this.mGeneratedChallengeCache != null
        && sSystemClock.millis() - this.mGeneratedChallengeCache.getCreatedAt() < 0;
  }

  public final void incrementChallengeCount() {
    this.mGeneratedChallengeCount.add(0, Long.valueOf(sSystemClock.millis()));
  }

  public final int decrementChallengeCount() {
    final long millis = sSystemClock.millis();
    this.mGeneratedChallengeCount.removeIf(
        new Predicate() { // from class:
                          // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda25
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$decrementChallengeCount$3;
            lambda$decrementChallengeCount$3 =
                Face10.lambda$decrementChallengeCount$3(millis, (Long) obj);
            return lambda$decrementChallengeCount$3;
          }
        });
    if (!this.mGeneratedChallengeCount.isEmpty()) {
      this.mGeneratedChallengeCount.remove(0);
    }
    return this.mGeneratedChallengeCount.size();
  }

  public static /* synthetic */ boolean lambda$decrementChallengeCount$3(long j, Long l) {
    return j - l.longValue() > 600000;
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void scheduleGenerateChallenge(
      int i,
      final int i2,
      final IBinder iBinder,
      final IFaceServiceReceiver iFaceServiceReceiver,
      final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda8
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$scheduleGenerateChallenge$4(iFaceServiceReceiver, i2, iBinder, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleGenerateChallenge$4(
      IFaceServiceReceiver iFaceServiceReceiver, int i, IBinder iBinder, String str) {
    incrementChallengeCount();
    if (isGeneratedChallengeCacheValid()) {
      Slog.d("Face10", "Current challenge is cached and will be reused");
      this.mGeneratedChallengeCache.reuseResult(iFaceServiceReceiver);
    } else {
      scheduleUpdateActiveUserWithoutHandler(i);
      final FaceGenerateChallengeClient faceGenerateChallengeClient =
          new FaceGenerateChallengeClient(
              this.mContext,
              this.mLazyDaemon,
              iBinder,
              new ClientMonitorCallbackConverter(iFaceServiceReceiver),
              i,
              str,
              this.mSensorId,
              createLogger(0, 0),
              this.mBiometricContext,
              sSystemClock.millis());
      this.mGeneratedChallengeCache = faceGenerateChallengeClient;
      this.mScheduler.lambda$scheduleClientMonitor$1(
          faceGenerateChallengeClient,
          new ClientMonitorCallback() { // from class:
                                        // com.android.server.biometrics.sensors.face.hidl.Face10.3
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(BaseClientMonitor baseClientMonitor) {
              if (faceGenerateChallengeClient != baseClientMonitor) {
                Slog.e(
                    "Face10",
                    "scheduleGenerateChallenge onClientStarted, mismatched client. Expecting: "
                        + faceGenerateChallengeClient
                        + ", received: "
                        + baseClientMonitor);
              }
            }
          });
    }
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void scheduleRevokeChallenge(
      int i, final int i2, final IBinder iBinder, final String str, long j) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda18
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$scheduleRevokeChallenge$5(iBinder, i2, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleRevokeChallenge$5(IBinder iBinder, int i, String str) {
    if (!(decrementChallengeCount() == 0)) {
      Slog.w(
          "Face10",
          "scheduleRevokeChallenge skipped - challenge still in use: "
              + this.mGeneratedChallengeCount);
      return;
    }
    Slog.d("Face10", "scheduleRevokeChallenge executing - no active clients");
    this.mGeneratedChallengeCache = null;
    final FaceRevokeChallengeClient faceRevokeChallengeClient =
        new FaceRevokeChallengeClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            i,
            str,
            this.mSensorId,
            createLogger(0, 0),
            this.mBiometricContext);
    this.mScheduler.lambda$scheduleClientMonitor$1(
        faceRevokeChallengeClient,
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.face.hidl.Face10.4
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            if (faceRevokeChallengeClient != baseClientMonitor) {
              Slog.e(
                  "Face10",
                  "scheduleRevokeChallenge, mismatched client.Expecting: "
                      + faceRevokeChallengeClient
                      + ", received: "
                      + baseClientMonitor);
            }
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public long scheduleEnroll(
      int i,
      final IBinder iBinder,
      final byte[] bArr,
      final int i2,
      final IFaceServiceReceiver iFaceServiceReceiver,
      final String str,
      final int[] iArr,
      final Surface surface,
      boolean z) {
    final long incrementAndGet = this.mRequestCounter.incrementAndGet();
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$scheduleEnroll$6(
                i2, iBinder, iFaceServiceReceiver, bArr, str, incrementAndGet, iArr, surface);
          }
        });
    return incrementAndGet;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleEnroll$6(
      int i,
      IBinder iBinder,
      IFaceServiceReceiver iFaceServiceReceiver,
      byte[] bArr,
      final String str,
      long j,
      int[] iArr,
      Surface surface) {
    scheduleUpdateActiveUserWithoutHandler(i);
    BiometricNotificationUtils.cancelReEnrollNotification(this.mContext);
    final FaceEnrollClient faceEnrollClient =
        new FaceEnrollClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            new ClientMonitorCallbackConverter(iFaceServiceReceiver),
            i,
            bArr,
            str,
            j,
            FaceUtils.getLegacyInstance(this.mSensorId),
            iArr,
            75,
            surface,
            this.mSensorId,
            createLogger(1, 0),
            this
                .mBiometricContext) { // from class:
                                      // com.android.server.biometrics.sensors.face.hidl.Face10.5
          @Override // com.android.server.biometrics.sensors.face.hidl.FaceEnrollClient
          public int daemonEnroll(ArrayList arrayList, int i2, ArrayList arrayList2) {
            SemBioLoggingManager.get().faceStart(getHashID(), "E", str);
            return Face10.mProviderExtImpl.daemonEnroll(arrayList, i2, arrayList2);
          }

          @Override // com.android.server.biometrics.sensors.face.hidl.FaceEnrollClient
          public int daemonEnrollCancel() {
            Face10.mProviderExtImpl.sendError(5, 0);
            Face10.mProviderExtImpl.stopOperation();
            return Face10.mProviderExtImpl.daemonCancel();
          }
        };
    this.mScheduler.lambda$scheduleClientMonitor$1(
        faceEnrollClient,
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.face.hidl.Face10.6
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientStarted(BaseClientMonitor baseClientMonitor) {
            Face10.this.mBiometricStateCallback.onClientStarted(baseClientMonitor);
          }

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onBiometricAction(int i2) {
            Face10.this.mBiometricStateCallback.onBiometricAction(i2);
          }

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            Face10.this.mBiometricStateCallback.onClientFinished(baseClientMonitor, z);
            if (z) {
              Face10.this.scheduleUpdateActiveUserWithoutHandler(
                  faceEnrollClient.getTargetUserId());
            }
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$cancelEnrollment$7(IBinder iBinder, long j) {
    this.mScheduler.lambda$cancelEnrollment$2(iBinder, j);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void cancelEnrollment(int i, final IBinder iBinder, final long j) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda9
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$cancelEnrollment$7(iBinder, j);
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public long scheduleFaceDetect(
      IBinder iBinder,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      FaceAuthenticateOptions faceAuthenticateOptions,
      int i) {
    throw new IllegalStateException(
        "Face detect not supported by IBiometricsFace@1.0. Did youforget to check the"
            + " supportsFaceDetection flag?");
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void cancelFaceDetect(int i, IBinder iBinder, long j) {
    throw new IllegalStateException(
        "Face detect not supported by IBiometricsFace@1.0. Did youforget to check the"
            + " supportsFaceDetection flag?");
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void scheduleAuthenticate(
      final IBinder iBinder,
      final long j,
      final int i,
      final ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      final FaceAuthenticateOptions faceAuthenticateOptions,
      final long j2,
      final boolean z,
      final int i2,
      final boolean z2) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda23
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$scheduleAuthenticate$8(
                faceAuthenticateOptions,
                z2,
                iBinder,
                j2,
                clientMonitorCallbackConverter,
                j,
                z,
                i,
                i2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleAuthenticate$8(
      final FaceAuthenticateOptions faceAuthenticateOptions,
      boolean z,
      IBinder iBinder,
      long j,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      long j2,
      boolean z2,
      int i,
      int i2) {
    scheduleUpdateActiveUserWithoutHandler(faceAuthenticateOptions.getUserId());
    boolean isStrongBiometric = Utils.isStrongBiometric(this.mSensorId);
    boolean hasPrivilegedAttr = z | SemFaceUtils.hasPrivilegedAttr(SemFaceUtils.getBundle(), 4);
    FaceAuthenticationClient faceAuthenticationClient =
        new FaceAuthenticationClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            j,
            clientMonitorCallbackConverter,
            j2,
            z2,
            faceAuthenticateOptions,
            i,
            false,
            createLogger(2, i2),
            this.mBiometricContext,
            isStrongBiometric,
            this.mLockoutTracker,
            this.mUsageStats,
            hasPrivilegedAttr,
            Utils.getCurrentStrength(
                this
                    .mSensorId)) { // from class:
                                   // com.android.server.biometrics.sensors.face.hidl.Face10.7
          @Override // com.android.server.biometrics.sensors.face.hidl.FaceAuthenticationClient
          public void daemonAuthenticate(long j3) {
            SemBioLoggingManager.get()
                .faceStart(getHashID(), "A", faceAuthenticateOptions.getOpPackageName());
            Face10.mProviderExtImpl.daemonAuthenticate(j3);
          }

          @Override // com.android.server.biometrics.sensors.face.hidl.FaceAuthenticationClient
          public void daemonAuthenticationCancel() {
            Face10.mProviderExtImpl.sendError(5, 0);
            Face10.mProviderExtImpl.daemonCancel();
            Face10.mProviderExtImpl.stopOperation();
          }
        };
    mProviderExtImpl.setCancellationSignal(faceAuthenticationClient, j);
    this.mScheduler.scheduleClientMonitor(faceAuthenticationClient);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public long scheduleAuthenticate(
      IBinder iBinder,
      long j,
      int i,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      FaceAuthenticateOptions faceAuthenticateOptions,
      boolean z,
      int i2,
      boolean z2) {
    long incrementAndGet = this.mRequestCounter.incrementAndGet();
    scheduleAuthenticate(
        iBinder,
        j,
        i,
        clientMonitorCallbackConverter,
        faceAuthenticateOptions,
        incrementAndGet,
        z,
        i2,
        z2);
    return incrementAndGet;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$cancelAuthentication$9(IBinder iBinder, long j) {
    this.mScheduler.lambda$cancelAuthenticationOrDetection$3(iBinder, j);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void cancelAuthentication(int i, final IBinder iBinder, final long j) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda7
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$cancelAuthentication$9(iBinder, j);
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void scheduleRemove(
      int i,
      final IBinder iBinder,
      final int i2,
      final int i3,
      final IFaceServiceReceiver iFaceServiceReceiver,
      final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda22
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$scheduleRemove$10(i3, iBinder, iFaceServiceReceiver, i2, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleRemove$10(
      int i, IBinder iBinder, IFaceServiceReceiver iFaceServiceReceiver, int i2, String str) {
    scheduleUpdateActiveUserWithoutHandler(i);
    FaceRemovalClient faceRemovalClient =
        new FaceRemovalClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            new ClientMonitorCallbackConverter(iFaceServiceReceiver),
            i2,
            i,
            str,
            FaceUtils.getLegacyInstance(this.mSensorId),
            this.mSensorId,
            createLogger(4, 0),
            this.mBiometricContext,
            this.mAuthenticatorIds);
    this.mScheduler.lambda$scheduleClientMonitor$1(faceRemovalClient, this.mBiometricStateCallback);
    SemBioLoggingManager.get().faceStart(faceRemovalClient.getHashID(), "R", str);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void scheduleRemoveAll(
      int i,
      final IBinder iBinder,
      final int i2,
      final IFaceServiceReceiver iFaceServiceReceiver,
      final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda20
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$scheduleRemoveAll$11(i2, iBinder, iFaceServiceReceiver, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleRemoveAll$11(
      int i, IBinder iBinder, IFaceServiceReceiver iFaceServiceReceiver, String str) {
    scheduleUpdateActiveUserWithoutHandler(i);
    this.mScheduler.lambda$scheduleClientMonitor$1(
        new FaceRemovalClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            new ClientMonitorCallbackConverter(iFaceServiceReceiver),
            0,
            i,
            str,
            FaceUtils.getLegacyInstance(this.mSensorId),
            this.mSensorId,
            createLogger(4, 0),
            this.mBiometricContext,
            this.mAuthenticatorIds),
        this.mBiometricStateCallback);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void scheduleResetLockout(final int i, final int i2, final byte[] bArr) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda6
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$scheduleResetLockout$12(i, i2, bArr);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleResetLockout$12(int i, int i2, byte[] bArr) {
    if (getEnrolledFaces(i, i2).isEmpty()) {
      Slog.w("Face10", "Ignoring lockout reset, no templates enrolled for user: " + i2);
      return;
    }
    scheduleUpdateActiveUserWithoutHandler(i2);
    Context context = this.mContext;
    this.mScheduler.scheduleClientMonitor(
        new FaceResetLockoutClient(
            context,
            this.mLazyDaemon,
            i2,
            context.getOpPackageName(),
            this.mSensorId,
            createLogger(0, 0),
            this.mBiometricContext,
            bArr) { // from class: com.android.server.biometrics.sensors.face.hidl.Face10.8
          @Override // com.android.server.biometrics.sensors.face.hidl.FaceResetLockoutClient
          public void daemonResetLockout(ArrayList arrayList) {
            Face10.mProviderExtImpl.resetFailedAttempts(true);
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void scheduleSetFeature(
      final int i,
      final IBinder iBinder,
      final int i2,
      final int i3,
      final boolean z,
      final byte[] bArr,
      final IFaceServiceReceiver iFaceServiceReceiver,
      final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda16
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$scheduleSetFeature$13(
                i, i2, iBinder, iFaceServiceReceiver, str, i3, z, bArr);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleSetFeature$13(
      int i,
      int i2,
      IBinder iBinder,
      IFaceServiceReceiver iFaceServiceReceiver,
      String str,
      int i3,
      boolean z,
      byte[] bArr) {
    List enrolledFaces = getEnrolledFaces(i, i2);
    if (enrolledFaces.isEmpty()) {
      Slog.w("Face10", "Ignoring setFeature, no templates enrolled for user: " + i2);
      return;
    }
    scheduleUpdateActiveUserWithoutHandler(i2);
    this.mScheduler.scheduleClientMonitor(
        new FaceSetFeatureClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            new ClientMonitorCallbackConverter(iFaceServiceReceiver),
            i2,
            str,
            this.mSensorId,
            BiometricLogger.ofUnknown(this.mContext),
            this.mBiometricContext,
            i3,
            z,
            bArr,
            ((Face) enrolledFaces.get(0)).getBiometricId()));
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void scheduleGetFeature(
      final int i,
      final IBinder iBinder,
      final int i2,
      final int i3,
      final ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda12
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$scheduleGetFeature$14(
                i, i2, iBinder, clientMonitorCallbackConverter, str, i3);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleGetFeature$14(
      int i,
      final int i2,
      IBinder iBinder,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      String str,
      final int i3) {
    List enrolledFaces = getEnrolledFaces(i, i2);
    if (enrolledFaces.isEmpty()) {
      Slog.w("Face10", "Ignoring getFeature, no templates enrolled for user: " + i2);
      return;
    }
    scheduleUpdateActiveUserWithoutHandler(i2);
    int biometricId = ((Face) enrolledFaces.get(0)).getBiometricId();
    Context context = this.mContext;
    final FaceGetFeatureClient faceGetFeatureClient =
        new FaceGetFeatureClient(
            context,
            this.mLazyDaemon,
            iBinder,
            clientMonitorCallbackConverter,
            i2,
            str,
            this.mSensorId,
            BiometricLogger.ofUnknown(context),
            this.mBiometricContext,
            i3,
            biometricId);
    this.mScheduler.lambda$scheduleClientMonitor$1(
        faceGetFeatureClient,
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.face.hidl.Face10.9
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            if (z && i3 == 1) {
              boolean value = faceGetFeatureClient.getValue();
              Slog.d(
                  "Face10",
                  "Updating attention value for user: " + i2 + " to value: " + (value ? 1 : 0));
              Settings.Secure.putIntForUser(
                  Face10.this.mContext.getContentResolver(),
                  "face_unlock_attention_required",
                  value ? 1 : 0,
                  i2);
            }
          }
        });
  }

  public final void scheduleInternalCleanup(
      final int i, final ClientMonitorCallback clientMonitorCallback) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$scheduleInternalCleanup$15(i, clientMonitorCallback);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleInternalCleanup$15(
      int i, ClientMonitorCallback clientMonitorCallback) {
    mProviderExtImpl.daemonSetActiveUser(i);
    scheduleUpdateActiveUserWithoutHandler(i);
    Context context = this.mContext;
    this.mScheduler.lambda$scheduleClientMonitor$1(
        new FaceInternalCleanupClient(
            context,
            this.mLazyDaemon,
            i,
            context.getOpPackageName(),
            this.mSensorId,
            createLogger(3, 0),
            this.mBiometricContext,
            FaceUtils.getLegacyInstance(this.mSensorId),
            this.mAuthenticatorIds),
        new ClientMonitorCompositeCallback(clientMonitorCallback, this.mBiometricStateCallback));
  }

  public void scheduleInternalCleanup(int i, int i2, ClientMonitorCallback clientMonitorCallback) {
    scheduleInternalCleanup(i2, this.mBiometricStateCallback);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void scheduleInternalCleanup(
      int i, int i2, ClientMonitorCallback clientMonitorCallback, boolean z) {
    scheduleInternalCleanup(i2, clientMonitorCallback);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void startPreparedClient(int i, final int i2) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda10
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$startPreparedClient$16(i2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startPreparedClient$16(int i) {
    this.mScheduler.lambda$startPreparedClient$0(i);
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public void dumpProtoState(int i, ProtoOutputStream protoOutputStream, boolean z) {
    long start = protoOutputStream.start(2246267895809L);
    protoOutputStream.write(1120986464257L, this.mSensorProperties.sensorId);
    protoOutputStream.write(1159641169922L, 2);
    protoOutputStream.write(
        1120986464259L, Utils.getCurrentStrength(this.mSensorProperties.sensorId));
    protoOutputStream.write(1146756268036L, this.mScheduler.dumpProtoState(z));
    Iterator it = UserManager.get(this.mContext).getUsers().iterator();
    while (it.hasNext()) {
      int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
      long start2 = protoOutputStream.start(2246267895813L);
      protoOutputStream.write(1120986464257L, identifier);
      protoOutputStream.write(
          1120986464258L,
          FaceUtils.getLegacyInstance(this.mSensorId)
              .getBiometricsForUser(this.mContext, identifier)
              .size());
      protoOutputStream.end(start2);
    }
    protoOutputStream.write(
        1133871366150L, this.mSensorProperties.resetLockoutRequiresHardwareAuthToken);
    protoOutputStream.write(1133871366151L, this.mSensorProperties.resetLockoutRequiresChallenge);
    protoOutputStream.end(start);
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public void dumpInternal(int i, PrintWriter printWriter) {
    PerformanceTracker instanceForSensorId =
        PerformanceTracker.getInstanceForSensorId(this.mSensorId);
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("service", "Face10");
      JSONArray jSONArray = new JSONArray();
      Iterator it = UserManager.get(this.mContext).getUsers().iterator();
      while (it.hasNext()) {
        int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
        int size =
            FaceUtils.getLegacyInstance(this.mSensorId)
                .getBiometricsForUser(this.mContext, identifier)
                .size();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("id", identifier);
        jSONObject2.put("count", size);
        jSONObject2.put("accept", instanceForSensorId.getAcceptForUser(identifier));
        jSONObject2.put("reject", instanceForSensorId.getRejectForUser(identifier));
        jSONObject2.put("acquire", instanceForSensorId.getAcquireForUser(identifier));
        jSONObject2.put("lockout", instanceForSensorId.getTimedLockoutForUser(identifier));
        jSONObject2.put(
            "permanentLockout", instanceForSensorId.getPermanentLockoutForUser(identifier));
        jSONObject2.put("acceptCrypto", instanceForSensorId.getAcceptCryptoForUser(identifier));
        jSONObject2.put("rejectCrypto", instanceForSensorId.getRejectCryptoForUser(identifier));
        jSONObject2.put("acquireCrypto", instanceForSensorId.getAcquireCryptoForUser(identifier));
        jSONArray.put(jSONObject2);
      }
      jSONObject.put("prints", jSONArray);
    } catch (JSONException e) {
      Slog.e("Face10", "dump formatting failure", e);
    }
    printWriter.println(jSONObject);
    printWriter.println("HAL deaths since last reboot: " + instanceForSensorId.getHALDeathCount());
    this.mScheduler.dump(printWriter);
    this.mUsageStats.print(printWriter);
    mProviderExtImpl.dump(printWriter);
  }

  public final void scheduleLoadAuthenticatorIds() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda24
          @Override // java.lang.Runnable
          public final void run() {
            Face10.this.lambda$scheduleLoadAuthenticatorIds$17();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleLoadAuthenticatorIds$17() {
    Iterator it = UserManager.get(this.mContext).getAliveUsers().iterator();
    while (it.hasNext()) {
      int i = ((UserInfo) it.next()).id;
      if (!this.mAuthenticatorIds.containsKey(Integer.valueOf(i))) {
        scheduleUpdateActiveUserWithoutHandler(i);
      }
    }
  }

  public final void scheduleUpdateActiveUserWithoutHandler(final int i) {
    boolean z = !getEnrolledFaces(this.mSensorId, i).isEmpty();
    Context context = this.mContext;
    this.mScheduler.lambda$scheduleClientMonitor$1(
        new FaceUpdateActiveUserClient(
            context,
            this.mLazyDaemon,
            i,
            context.getOpPackageName(),
            this.mSensorId,
            createLogger(0, 0),
            this.mBiometricContext,
            z,
            this
                .mAuthenticatorIds) { // from class:
                                      // com.android.server.biometrics.sensors.face.hidl.Face10.10
          @Override // com.android.server.biometrics.sensors.face.hidl.FaceUpdateActiveUserClient
          public int daemonSetActiveUser(int i2) {
            return Face10.mProviderExtImpl.daemonSetActiveUser(i2);
          }
        },
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.face.hidl.Face10.11
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z2) {
            if (z2) {
              Face10.this.mCurrentUserId = i;
            } else {
              Slog.w("Face10", "Failed to change user, still: " + Face10.this.mCurrentUserId);
            }
          }
        });
  }

  public final BiometricLogger createLogger(int i, int i2) {
    return new BiometricLogger(this.mContext, 4, i, i2);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void dumpHal(int i, FileDescriptor fileDescriptor, String[] strArr) {
    IBiometricsFace daemon;
    NativeHandle fileOutputStream;
    if ((!Build.IS_ENG && !Build.IS_USERDEBUG)
        || SystemProperties.getBoolean("ro.face.disable_debug_data", false)
        || SystemProperties.getBoolean("persist.face.disable_debug_data", false)
        || (daemon = getDaemon()) == null) {
      return;
    }
    NativeHandle nativeHandle = null;
    try {
      try {
        try {
          fileOutputStream = new FileOutputStream("/dev/null");
        } catch (IOException unused) {
          return;
        }
      } catch (RemoteException | IOException e) {
        e = e;
      }
    } catch (Throwable th) {
      th = th;
    }
    try {
      nativeHandle =
          new NativeHandle(
              new FileDescriptor[] {fileOutputStream.getFD(), fileDescriptor}, new int[0], false);
      daemon.debug(nativeHandle, new ArrayList(Arrays.asList(strArr)));
      fileOutputStream.close();
    } catch (RemoteException | IOException e2) {
      e = e2;
      nativeHandle = fileOutputStream;
      Slog.d("Face10", "error while reading face debugging data", e);
      if (nativeHandle != null) {
        nativeHandle.close();
      }
    } catch (Throwable th2) {
      th = th2;
      nativeHandle = fileOutputStream;
      if (nativeHandle != null) {
        try {
          nativeHandle.close();
        } catch (IOException unused2) {
        }
      }
      throw th;
    }
  }

  public void setTestHalEnabled(boolean z) {
    this.mTestHalEnabled = z;
    HalResultController halResultController = this.mHalResultController;
    if (halResultController != null) {
      halResultController.mHalResultTestHalEnabled = z;
    }
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public ITestSession createTestSession(
      int i, ITestSessionCallback iTestSessionCallback, String str) {
    return new BiometricTestSessionImpl(
        this.mContext, this.mSensorId, iTestSessionCallback, this, this.mHalResultController);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void semPauseEnroll() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda11
          @Override // java.lang.Runnable
          public final void run() {
            Face10.lambda$semPauseEnroll$18();
          }
        });
  }

  public static /* synthetic */ void lambda$semPauseEnroll$18() {
    mProviderExtImpl.daemonPauseEnroll();
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void semResumeEnroll() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda17
          @Override // java.lang.Runnable
          public final void run() {
            Face10.lambda$semResumeEnroll$19();
          }
        });
  }

  public static /* synthetic */ void lambda$semResumeEnroll$19() {
    mProviderExtImpl.mEnrollStartTime = System.currentTimeMillis();
    mProviderExtImpl.daemonResumeEnroll();
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void semPauseAuth() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda15
          @Override // java.lang.Runnable
          public final void run() {
            Face10.lambda$semPauseAuth$20();
          }
        });
  }

  public static /* synthetic */ void lambda$semPauseAuth$20() {
    mProviderExtImpl.daemonPauseAuth();
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void semResumeAuth() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda14
          @Override // java.lang.Runnable
          public final void run() {
            Face10.lambda$semResumeAuth$21();
          }
        });
  }

  public static /* synthetic */ void lambda$semResumeAuth$21() {
    mProviderExtImpl.daemonResumeAuth();
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public boolean semResetAuthenticationTimeout() {
    return mProviderExtImpl.resetOperationTimeout(
        SemFaceBrightManager.getInstance(this.mContext).isNeededSetBrightness() ? 5000 : 4000);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void semSessionOpen() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda21
          @Override // java.lang.Runnable
          public final void run() {
            Face10.lambda$semSessionOpen$23();
          }
        });
  }

  public static /* synthetic */ void lambda$semSessionOpen$23() {
    mProviderExtImpl.daemonSessionOpen();
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void semSessionClose() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda19
          @Override // java.lang.Runnable
          public final void run() {
            Face10.lambda$semSessionClose$24();
          }
        });
  }

  public static /* synthetic */ void lambda$semSessionClose$24() {
    mProviderExtImpl.daemonSessionClose();
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public boolean semIsSessionClose() {
    return mProviderExtImpl.daemonIsSessionClose();
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public int semGetSecurityLevel(boolean z) {
    getDaemon();
    return mProviderExtImpl.getSecurityLevel(z);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public int semGetRemainingLockoutTime(int i) {
    return mProviderExtImpl.getRemainingLockoutTime(i);
  }

  public static /* synthetic */ void lambda$onBootPhase$25(int i) {
    mProviderExtImpl.onBootPhase(i);
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void onBootPhase(final int i) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            Face10.lambda$onBootPhase$25(i);
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void semSetTpaHalEnabled(boolean z) {
    this.mTpaHalModeEnabled = z;
    int intDb = Utils.getIntDb(this.mContext, "biometric_tpa_mode", true, 0);
    Utils.putIntDb(this.mContext, "biometric_tpa_mode", true, z ? intDb | 8 : intDb & (-9));
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public void semUpdateTpaAction() {
    if (isTpaSehTestHalEnabled()) {
      ((SehTestHal) getDaemon()).postUpdateAction();
    }
  }

  @Override // com.android.server.biometrics.sensors.face.ServiceProvider
  public String semGetInfo(int i) {
    if (i == 1) {
      return mProviderExtImpl.daemonGetTrustAppVersion();
    }
    return null;
  }

  public final class ProviderExtensionImpl {
    public AppOpsManager mAppOpsManager;
    public int mBacklight;
    public int mBrightnessUp;
    public Context mContext;
    public int mFABK;
    public int mFALI;
    public int mFALQ;
    public int mFAMO;
    public int mFANM;
    public FaceUtils mFaceUtils;
    public long mHalDeviceId;
    public int mInsufficient;
    public int mLastRotation;
    public int mMotion;
    public OrientationEventListener mOrientationEventListener;
    public PowerManager mPowerManager;
    public SemProximitySensorObserver mProximitySensorObserver;
    public SemBioAnalyticsManager mSemAnalyticsManager;
    public PowerManager.WakeLock mWakeLock;
    public boolean mIsCheckedTooDark = false;
    public int mSecurityLevel = 0;
    public boolean mIsOperationStarted = false;
    public long mStartOperationTime = -1;
    public int mOperationType = -1;
    public boolean mDaemonIsCancelling = false;
    public boolean mShouldRemoveRegisteredFaceOnCancelling = false;
    public MemoryFile mMemoryFile = null;
    public MemoryFile mMemoryFileForAuthPreviewResult = null;
    public int mPrevAcquiredInfo = -1;
    public int mPrevAcquiredVendorInfo = -1;
    public int mSkipAcquiredEventCount = 0;
    public boolean mIsAuthenticateResult = false;
    public boolean mIsEnrollPausing = false;
    public long mEnrollStartTime = -1;
    public byte[] mPreviewImage = null;
    public Surface mPreviewSurface = null;
    public boolean mIsAuthenticationExtOperation = false;
    public Handler mHandlerMain =
        new Handler(
            SemFaceMainThread.get()
                .getLooper()) { // from class:
                                // com.android.server.biometrics.sensors.face.hidl.Face10.ProviderExtensionImpl.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
              Slog.d("Face10", "handleMessage : MSG_INACTIVITY_TIMER_EXPIRED(TIMEOUT)");
              ProviderExtensionImpl.this.onAuthenticatedTimeout();
            } else if (i == 4) {
              Slog.i("Face10", "handleMessage: MSG_PROXIMITY_SENSOR_ERROR");
              ProviderExtensionImpl.this.sendAcquired(22, 1001);
            } else {
              Slog.w("Face10", "Unknown message:" + message.what);
            }
          }
        };

    public final int getRotationValue(int i) {
      if (i == 1) {
        return 0;
      }
      if (i == 2) {
        return 90;
      }
      if (i != 3) {
        return FrameworkStatsLog.f392x1636cc72;
      }
      return 180;
    }

    public ProviderExtensionImpl(Context context) {
      this.mProximitySensorObserver = null;
      this.mContext = context;
      this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
      this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
      this.mWakeLock = this.mPowerManager.newWakeLock(268435466, "Face10");
      this.mOrientationEventListener = new C08842(this.mContext, 3, Face10.this);
      if (Utils.isTablet()) {
        this.mProximitySensorObserver =
            new SemProximitySensorObserver(
                this
                    .mContext) { // from class:
                                 // com.android.server.biometrics.sensors.face.hidl.Face10.ProviderExtensionImpl.3
              @Override // android.hardware.SensorEventListener
              public void onSensorChanged(SensorEvent sensorEvent) {
                if (!Face10.this.mTestHalEnabled && sensorEvent.sensor.getType() == 8) {
                  float[] fArr = (float[]) sensorEvent.values.clone();
                  if (fArr[0] == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                    ProviderExtensionImpl.this.mHandlerMain.removeMessages(4);
                    ProviderExtensionImpl.this.mHandlerMain.sendEmptyMessage(4);
                  }
                  Slog.i("Face10", "[PROXIMITY] onSensorChanged : " + fArr[0]);
                }
              }
            };
      } else {
        this.mProximitySensorObserver = null;
      }
      this.mFaceUtils = FaceUtils.getLegacyInstance(Face10.this.mSensorId);
      registerBroadcastEvents();
    }

    /* renamed from: com.android.server.biometrics.sensors.face.hidl.Face10$ProviderExtensionImpl$2 */
    public class C08842 extends OrientationEventListener {
      public final /* synthetic */ Face10 val$this$0;

      /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
      public C08842(Context context, int i, Face10 face10) {
        super(context, i);
        this.val$this$0 = face10;
      }

      @Override // android.view.OrientationEventListener
      public void onOrientationChanged(int i) {
        int rotation;
        if (ProviderExtensionImpl.this.mContext == null
            || (rotation = ProviderExtensionImpl.this.mContext.getDisplay().getRotation())
                == ProviderExtensionImpl.this.mLastRotation) {
          return;
        }
        ProviderExtensionImpl.this.mLastRotation = rotation;
        if (ProviderExtensionImpl.this.mIsOperationStarted) {
          Face10.this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.biometrics.sensors.face.hidl.Face10$ProviderExtensionImpl$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  Face10.ProviderExtensionImpl.C08842.this.lambda$onOrientationChanged$0();
                }
              });
        }
      }

      /* JADX INFO: Access modifiers changed from: private */
      public /* synthetic */ void lambda$onOrientationChanged$0() {
        ProviderExtensionImpl providerExtensionImpl = ProviderExtensionImpl.this;
        providerExtensionImpl.daemonSetRotation(providerExtensionImpl.mLastRotation);
      }
    }

    public Context getContext() {
      return this.mContext;
    }

    public void onBootPhase(int i) {
      if (i == 600) {
        this.mSemAnalyticsManager = SemBioAnalyticsManager.getInstance();
      }
    }

    public String daemonGetTrustAppVersion() {
      ISehBiometricsFace iSehBiometricsFace = (ISehBiometricsFace) Face10.this.getDaemon();
      String str = "";
      if (iSehBiometricsFace == null) {
        Slog.w("Face10", "daemonGetTrustAppVersion(): no face HAL!");
        return "";
      }
      try {
        long currentTimeMillis = System.currentTimeMillis();
        str = iSehBiometricsFace.sehGetTaInfo();
        Slog.w(
            "Face10",
            "sehGetTaInfo FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
      } catch (Exception e) {
        Slog.w("Face10", "getTrustAppVersion: " + e.getMessage());
      }
      if (Utils.DEBUG) {
        Slog.d("Face10", "getTrustAppVersion: returned: " + str);
      }
      return str;
    }

    public final void startInactivityTimer(int i) {
      Slog.i("Face10", "startInactivityTimer : " + i);
      this.mHandlerMain.removeMessages(1);
      this.mHandlerMain.sendEmptyMessageDelayed(1, (long) i);
    }

    public final void startOperationTimeout(int i) {
      Slog.i("Face10", "startOperationTimeout : " + i);
      this.mHandlerMain.removeMessages(1);
      this.mHandlerMain.sendEmptyMessageDelayed(1, (long) i);
      acquireDVFS(i, 1);
    }

    public final void stopOperationTimeout() {
      Slog.i("Face10", "stopOperationTimeout");
      this.mHandlerMain.removeMessages(1);
      releaseDVFS();
    }

    public final boolean resetOperationTimeout(int i) {
      Slog.i("Face10", "resetOperationTimeout");
      if (i <= 0) {
        return false;
      }
      startInactivityTimer(i);
      releaseDVFS();
      acquireDVFS(i, 2);
      return true;
    }

    public final synchronized void startOperation(int i) {
      Slog.i("Face10", "startOperation S");
      this.mIsOperationStarted = true;
      this.mOperationType = i;
      this.mStartOperationTime = System.currentTimeMillis();
      int i2 = 60000;
      if (i == 1) {
        Face10.mProviderExtImpl.mEnrollStartTime = System.currentTimeMillis();
        if (!Utils.isTalkBackEnabled(this.mContext)) {
          i2 = 30000;
        }
        Slog.d("Face10", "enroll timeout set as : " + i2);
        startInactivityTimer(i2);
        this.mAppOpsManager.startOp(
            26,
            Process.myUid(),
            this.mContext.getOpPackageName(),
            false,
            "Biometrics_FaceService",
            null);
      } else if (i == 2) {
        int i3 =
            SemFaceBrightManager.getInstance(this.mContext).isNeededSetBrightness() ? 5000 : 4000;
        if (SemFaceUtils.needToAuthenticateExt()) {
          this.mPreviewSurface = SemFaceUtils.getSurface();
          SemFaceUtils.resetSurface();
          this.mIsAuthenticationExtOperation = true;
          i3 = 600000;
        }
        if (!Face10.this.mTestHalEnabled) {
          i2 = i3;
        }
        startInactivityTimer(i2);
        Face10.mProviderExtImpl.startBrightness();
        byte[] bArr = new byte[1];
        bArr[0] = Face10.mProviderExtImpl.isBrightnessEnable() ? (byte) 1 : (byte) 0;
        setFaceTag(1, bArr);
        i2 = i3;
      } else {
        i2 = 6000;
      }
      acquireDVFS(i2, i);
      setWakeLock(isCurrentClientKeyguard(), i2 + 3000);
      int rotation = this.mContext.getDisplay().getRotation();
      this.mLastRotation = rotation;
      daemonSetRotation(rotation);
      this.mPrevAcquiredInfo = -1;
      this.mPrevAcquiredVendorInfo = -1;
      this.mSkipAcquiredEventCount = 0;
      this.mIsCheckedTooDark = false;
      this.mIsAuthenticateResult = false;
      this.mShouldRemoveRegisteredFaceOnCancelling = false;
      this.mPreviewImage = null;
      this.mBrightnessUp = 0;
      this.mBacklight = 0;
      this.mMotion = 0;
      this.mInsufficient = 0;
      Slog.i("Face10", "startOperation E");
      Face10.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.face.hidl.Face10$ProviderExtensionImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              Face10.ProviderExtensionImpl.this.lambda$startOperation$0();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startOperation$0() {
      if (this.mOrientationEventListener.canDetectOrientation()) {
        this.mOrientationEventListener.enable();
      }
      SemProximitySensorObserver semProximitySensorObserver = this.mProximitySensorObserver;
      if (semProximitySensorObserver != null) {
        semProximitySensorObserver.registerListener();
      }
      SystemProperties.set("service.bioface.authenticating", "1");
    }

    public final synchronized void stopOperation() {
      Slog.i("Face10", "stopOperation S");
      if (!this.mIsOperationStarted) {
        Slog.i("Face10", "stopOperation E : skip");
        return;
      }
      int i = this.mOperationType;
      if (i == 1) {
        this.mAppOpsManager.finishOp(
            26, Process.myUid(), this.mContext.getOpPackageName(), "Biometrics_FaceService");
        releaseWakeLock(true);
      } else if (i == 2) {
        releaseWakeLock(false);
        Face10.mProviderExtImpl.restoreBrightness();
      }
      releaseDVFS();
      this.mHandlerMain.removeMessages(1);
      this.mOperationType = -1;
      this.mIsOperationStarted = false;
      this.mIsEnrollPausing = false;
      this.mEnrollStartTime = -1L;
      MemoryFile memoryFile = this.mMemoryFile;
      if (memoryFile != null) {
        memoryFile.close();
        this.mMemoryFile = null;
      }
      MemoryFile memoryFile2 = this.mMemoryFileForAuthPreviewResult;
      if (memoryFile2 != null) {
        memoryFile2.close();
        this.mMemoryFileForAuthPreviewResult = null;
      }
      this.mIsAuthenticationExtOperation = false;
      this.mPreviewSurface = null;
      Slog.i("Face10", "stopOperation E");
      Face10.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.face.hidl.Face10$ProviderExtensionImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              Face10.ProviderExtensionImpl.this.lambda$stopOperation$1();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopOperation$1() {
      this.mOrientationEventListener.disable();
      SemProximitySensorObserver semProximitySensorObserver = this.mProximitySensorObserver;
      if (semProximitySensorObserver != null) {
        semProximitySensorObserver.unregisterListener();
      }
      SystemProperties.set("service.bioface.authenticating", "0");
    }

    public final synchronized int daemonCancel() {
      IBiometricsFace daemon = Face10.this.getDaemon();
      int i = -1;
      if (daemon == null) {
        Slog.w("Face10", "daemonCancel(): no face HAL!");
        return -1;
      }
      this.mDaemonIsCancelling = true;
      try {
        long currentTimeMillis = System.currentTimeMillis();
        i = daemon.cancel();
        Slog.w(
            "Face10",
            "daemonCancel FINISH ("
                + (System.currentTimeMillis() - currentTimeMillis)
                + "ms) RESULT: "
                + i);
      } catch (RemoteException e) {
        Slog.e("Face10", "Failed to get biometric interface", e);
      }
      this.mDaemonIsCancelling = false;
      return i;
    }

    public final void daemonSetRotation(int i) {
      ISehBiometricsFace iSehBiometricsFace = (ISehBiometricsFace) Face10.this.getDaemon();
      if (iSehBiometricsFace == null) {
        Slog.w("Face10", "daemonSetRotation(): no face HAL!");
        return;
      }
      try {
        long currentTimeMillis = System.currentTimeMillis();
        Slog.w(
            "Face10",
            "SetRotation FINISH ("
                + (System.currentTimeMillis() - currentTimeMillis)
                + "ms) RESULT: "
                + iSehBiometricsFace.sehSetRotation(getRotationValue(i)));
      } catch (Exception e) {
        Slog.w("Face10", "daemonSetRotation: " + e.getMessage());
      }
    }

    public final int setFaceTag(int i, byte[] bArr) {
      ISehBiometricsFace iSehBiometricsFace = (ISehBiometricsFace) Face10.this.getDaemon();
      if (iSehBiometricsFace == null) {
        Slog.w("Face10", "setFaceTag(): no face HAL!");
        return 0;
      }
      try {
        ArrayList arrayList = new ArrayList();
        if (bArr != null && bArr.length > 0) {
          for (byte b : bArr) {
            arrayList.add(Byte.valueOf(b));
          }
        }
        long currentTimeMillis = System.currentTimeMillis();
        iSehBiometricsFace.sehSetFaceTag(i, arrayList);
        Slog.w(
            "Face10",
            "sehSetFaceTag FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
        return 1;
      } catch (Exception e) {
        Slog.w("Face10", "setFaceTag: " + e.getMessage());
        return 0;
      }
    }

    public final void acquireDVFS(int i, int i2) {
      Slog.i("Face10", "acquireDVFS");
      SemBiometricBoostingManager.getInstance()
          .acquireDvfs(getContext(), i2 == 1 ? 3511 : 3512, 8, "GFACE_SERVICE", i);
    }

    public final void releaseDVFS() {
      Slog.i("Face10", "releaseDVFS");
      SemBiometricBoostingManager.getInstance().release(getContext(), 8);
    }

    public final boolean isBrightnessEnable() {
      return (Face10.this.mCurrentUserId == -10000
              || Utils.isFlipFolded(this.mContext)
              || Settings.Secure.getIntForUser(
                      this.mContext.getContentResolver(),
                      "face_brighten_screen",
                      1,
                      Face10.this.mCurrentUserId)
                  != 1)
          ? false
          : true;
    }

    public final void startBrightness() {
      if (isBrightnessEnable()) {
        SemFaceBrightManager.getInstance(this.mContext).startBrightness(Face10.this.mCurrentUserId);
      }
    }

    public final void restoreBrightness() {
      if (isBrightnessEnable()) {
        SemFaceBrightManager.getInstance(this.mContext).restoreBrightness();
      }
    }

    public final void upBrightnessMax() {
      SemFaceBrightManager.getInstance(this.mContext).setBrightnessMax();
    }

    public final void resetFailedAttempts(boolean z) {
      Face10.this.mLockoutTracker.resetFailedAttempts(z);
    }

    public final int getRemainingLockoutTime(int i) {
      return Face10.this.mLockoutTracker.getRemainingLockoutTime(i);
    }

    public final void userActivity() {
      this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
    }

    public final synchronized void setWakeLock(boolean z, int i) {
      if (z) {
        this.mWakeLock = this.mPowerManager.newWakeLock(1, "Face10");
      } else {
        this.mWakeLock = this.mPowerManager.newWakeLock(268435466, "Face10");
      }
      PowerManager.WakeLock wakeLock = this.mWakeLock;
      if (wakeLock != null && !wakeLock.isHeld() && this.mPowerManager.isInteractive()) {
        Slog.i("Face10", "setWakeLock");
        this.mWakeLock.acquire(i);
      }
    }

    public final synchronized void releaseWakeLock(boolean z) {
      PowerManager.WakeLock wakeLock = this.mWakeLock;
      if (wakeLock != null && wakeLock.isHeld()) {
        Slog.i("Face10", "releaseWakeLock : " + z);
        if (z) {
          userActivity();
        }
        this.mWakeLock.release();
      }
    }

    public final void setSecurityLevel(int i) {
      this.mSecurityLevel = i;
      Face10.this.mSensorProperties.sensorStrength = Utils.oemStrengthToPropertyStrength(i);
    }

    public final int getSecurityLevel(boolean z) {
      int i = this.mSecurityLevel;
      if (!z && i == 1) {
        i = 2;
      }
      Slog.i(
          "Face10", "getSL : " + this.mSecurityLevel + " (" + i + "), " + Binder.getCallingPid());
      return i;
    }

    public final void dump(PrintWriter printWriter) {
      try {
        printWriter.println(" current User : " + Face10.this.mCurrentUserId);
        StringBuilder sb = new StringBuilder();
        sb.append(" SL : ");
        sb.append(this.mSecurityLevel);
        sb.append(SemBiometricFeature.FEATURE_JDM_HAL ? " , J" : " , S");
        printWriter.println(sb.toString());
        printWriter.println(
            " FALI : "
                + this.mFALI
                + ", FABK : "
                + this.mFABK
                + ", FAMO : "
                + this.mFAMO
                + ", FALQ : "
                + this.mFALQ
                + ", FANM : "
                + this.mFANM);
        SemBioLoggingManager.get().faceDump(printWriter);
      } catch (Exception e) {
        Slog.w("Face10", "dump: " + e.getMessage());
      }
    }

    public final void onAuthenticatedExt(int i) {
      int i2;
      long currentTimeMillis = System.currentTimeMillis() - this.mStartOperationTime;
      if (i == 1) {
        SemBioAnalyticsManager semBioAnalyticsManager = this.mSemAnalyticsManager;
        if (semBioAnalyticsManager != null) {
          semBioAnalyticsManager.faceOnAuthenticatedSuccess(getCurrentClientOwnerString());
        }
        SemBioLoggingManager.get()
            .faceStop(Face10.mProviderExtImpl.getCurrentClientHashID(), "M", currentTimeMillis, 0);
        return;
      }
      if (i != 2) {
        if (i != 3) {
          return;
        }
        SemBioAnalyticsManager semBioAnalyticsManager2 = this.mSemAnalyticsManager;
        if (semBioAnalyticsManager2 != null) {
          int faceTimeoutDetailCondition = semBioAnalyticsManager2.getFaceTimeoutDetailCondition();
          this.mSemAnalyticsManager.faceOnTimeout(getCurrentClientOwnerString());
          i2 = faceTimeoutDetailCondition;
        } else {
          i2 = 0;
        }
        SemBioLoggingManager.get()
            .faceTimeout(this.mContext, getCurrentClientHashID(), currentTimeMillis, i2, 0);
        SemBioLoggingManager.get()
            .faceStop(Face10.mProviderExtImpl.getCurrentClientHashID(), "T", currentTimeMillis, 0);
        return;
      }
      boolean z = SemBiometricFeature.FEATURE_JDM_HAL;
      int noMatchReason =
          z
              ? -1
              : SemFaceUtils.getNoMatchReason(
                  this.mBrightnessUp, this.mBacklight, this.mMotion, this.mInsufficient, 0);
      SemBioAnalyticsManager semBioAnalyticsManager3 = this.mSemAnalyticsManager;
      if (semBioAnalyticsManager3 != null) {
        if (z) {
          semBioAnalyticsManager3.faceOnAuthenticatedFailure(getCurrentClientOwnerString());
        } else {
          if (this.mBrightnessUp >= 4) {
            this.mFALI++;
          } else if (this.mBacklight >= 4) {
            this.mFABK++;
          } else if (this.mMotion >= 4) {
            this.mFAMO++;
          } else if (this.mInsufficient >= 7) {
            this.mFALQ++;
          } else {
            this.mFANM++;
          }
          semBioAnalyticsManager3.faceOnAuthenticatedFailureForHIDL(
              getCurrentClientOwnerString(),
              this.mBrightnessUp,
              this.mBacklight,
              this.mMotion,
              this.mInsufficient);
        }
      }
      SemBioLoggingManager.get()
          .faceNoMatch(
              this.mContext, getCurrentClientHashID(), currentTimeMillis, noMatchReason, 0);
      SemBioLoggingManager.get()
          .faceStop(Face10.mProviderExtImpl.getCurrentClientHashID(), "N", currentTimeMillis, 0);
    }

    public final void onAuthenticatedTimeout() {
      if (this.mIsAuthenticateResult) {
        Face10.this.mHalResultController.onAuthenticatedInternal(
            getHalDeviceId(), 0, getCurrentClientUserID(), null, false);
        stopOperation();
        Face10.mProviderExtImpl.daemonCancel();
        return;
      }
      stopOperation();
      Face10.mProviderExtImpl.daemonCancel();
      onAuthenticatedExt(3);
      if (this.mOperationType == 1 && this.mIsCheckedTooDark) {
        sendError(8, 100002);
      } else {
        sendError(3, 0);
      }
    }

    public final int onPreError(int i, int i2) {
      if (i == 5) {
        Slog.d("Face10", "onError: skip error (5:cancel) from daemon");
        return 1;
      }
      if (Face10.mProviderExtImpl.mIsOperationStarted) {
        return 0;
      }
      Slog.d("Face10", "onError: skip (" + i + ") after stop()");
      return 1;
    }

    public final void onErrorExt(int i, int i2) {
      SemBioLoggingManager.get()
          .faceError(
              this.mContext,
              getCurrentClientHashID(),
              System.currentTimeMillis() - this.mStartOperationTime,
              i,
              i2);
      if (i != 5 || i != 3) {
        SemBioLoggingManager.get()
            .faceStop(
                Face10.mProviderExtImpl.getCurrentClientHashID(),
                "E",
                System.currentTimeMillis() - this.mStartOperationTime,
                i == 8 ? i2 : i);
      }
      SemBioAnalyticsManager semBioAnalyticsManager = this.mSemAnalyticsManager;
      if (semBioAnalyticsManager != null) {
        semBioAnalyticsManager.faceOnError(getCurrentClientOwnerString(), i, i2);
      }
    }

    public final void onError(int i, int i2) {
      if (i == 8 && i2 == 1001) {
        Slog.e("Face10", "onError : TEMPLATE_CORRUPTED");
        daemonSetActiveUser(ActivityManager.getCurrentUser());
        daemonEnumerateUser();
      }
    }

    public final int onPreAcquired(int i, int i2) {
      if (Face10.this.mTestHalEnabled) {
        return 0;
      }
      if (i == 1) {
        this.mInsufficient++;
      } else if (i == 22 && i2 == 1015) {
        this.mBrightnessUp++;
      } else if (i == 22 && i2 == 1022) {
        this.mBacklight++;
      } else if (i == 22 && i2 == 1023) {
        this.mMotion++;
      }
      if (Face10.mProviderExtImpl.mEnrollStartTime != -1
          && SemFaceUtils.isNoFaceGuideEvents(i, i2)) {
        if (Face10.mProviderExtImpl.mEnrollStartTime + 3000 > System.currentTimeMillis()) {
          Log.d("Face10", "onPreAcquired: no face event skip (" + i + ", " + i2 + ")");
          return 1;
        }
        Face10.mProviderExtImpl.mEnrollStartTime = -1L;
      }
      if ((i != 22 && i == this.mPrevAcquiredInfo)
          || (i == 22 && i2 == this.mPrevAcquiredVendorInfo)) {
        int i3 = this.mSkipAcquiredEventCount + 1;
        this.mSkipAcquiredEventCount = i3;
        if (i3 < 30) {
          return 1;
        }
      }
      this.mSkipAcquiredEventCount = 0;
      this.mPrevAcquiredInfo = i;
      this.mPrevAcquiredVendorInfo = i2;
      if (!this.mIsOperationStarted) {
        Log.d("Face10", "onPreAcquired: skip (" + i + ", " + i2 + ") after stop()");
        return 1;
      }
      int i4 = this.mOperationType;
      if (i4 == 2 && i == 22 && i2 == 1015) {
        Face10.mProviderExtImpl.upBrightnessMax();
        return 1;
      }
      if (i4 == 1 && ((i == 22 && i2 == 1015) || i == 3)) {
        this.mIsCheckedTooDark = true;
      }
      return 0;
    }

    public final void onAcquired(int i, int i2) {
      SemBioAnalyticsManager semBioAnalyticsManager = this.mSemAnalyticsManager;
      if (semBioAnalyticsManager != null) {
        semBioAnalyticsManager.faceCountHelpEvent(i, i2);
      }
    }

    public final void sendBroadcast(String str, int i, int i2) {
      Intent intent = new Intent(str);
      if (i > 0) {
        intent.putExtra("faceIndex", i);
        intent.putExtra("verificationType", "Device Credential");
      }
      try {
        intent.setFlags(16777216);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(i2));
        if (Utils.DEBUG) {
          Slog.i("Face10", "[" + str + "] is sent with faceId " + i);
        } else {
          Slog.i("Face10", "[" + str + "] is sent");
        }
      } catch (Exception e) {
        Slog.e("Face10", "sendBroadcast failed :" + e);
      }
    }

    public final void onEnrollResultExt(String str, int i, int i2) {
      if (i2 == 0) {
        sendBroadcast(
            "com.samsung.android.bio.face.intent.action.FACE_ADDED", i, Face10.this.mCurrentUserId);
        SemBioAnalyticsManager semBioAnalyticsManager = this.mSemAnalyticsManager;
        if (semBioAnalyticsManager != null) {
          semBioAnalyticsManager.faceOnEnrollmentSuccess(String.valueOf(i));
        }
        SemFaceUtils.saveEnrolledFacePostion(this.mContext, i, Face10.this.mCurrentUserId);
        SemBioLoggingManager.get()
            .faceStop(
                Face10.mProviderExtImpl.getCurrentClientHashID(),
                "S",
                System.currentTimeMillis() - this.mStartOperationTime,
                0);
      }
    }

    public final void onEnrollResult(String str, int i, int i2) {
      if (this.mIsEnrollPausing && i2 == 30) {
        stopOperationTimeout();
      }
      if (i2 == 0) {
        Face10.mProviderExtImpl.stopOperation();
      }
    }

    public final void onRemovedExt(String str, int i) {
      if (Face10.mProviderExtImpl
              .mFaceUtils
              .getBiometricsForUser(this.mContext, Face10.this.mCurrentUserId)
              .size()
          > 0) {
        sendBroadcast(
            "com.samsung.android.bio.face.intent.action.FACE_REMOVED",
            i,
            Face10.this.mCurrentUserId);
      } else {
        sendBroadcast(
            "com.samsung.android.bio.face.intent.action.FACE_RESET",
            -1,
            Face10.this.mCurrentUserId);
      }
      SemBioAnalyticsManager semBioAnalyticsManager = this.mSemAnalyticsManager;
      if (semBioAnalyticsManager != null) {
        semBioAnalyticsManager.faceOnRemoved(String.valueOf(i));
      }
      SemBioLoggingManager.get().faceRemoved(str, -1);
    }

    public final void doTemplateSyncForUser(long j, ArrayList arrayList, final int i) {
      boolean z;
      if (SemBiometricFeature.FEATURE_JDM_HAL) {
        return;
      }
      ArrayList arrayList2 = arrayList == null ? new ArrayList() : arrayList;
      List<Face> biometricsForUser =
          Face10.mProviderExtImpl.mFaceUtils.getBiometricsForUser(this.mContext, i);
      int size = arrayList2.size();
      try {
        Slog.i("Face10", "doTemplateSyncForUser: FW=" + biometricsForUser.size() + ", HAL=" + size);
        if (size > 0) {
          z = true;
          for (int i2 = 0; i2 < size; i2++) {
            if (((Integer) arrayList2.get(i2)).intValue() == 1) {
              z = false;
            }
          }
        } else {
          z = false;
        }
        if (z) {
          Slog.w("Face10", "Main face ID(1) was removed!!!");
          daemonSetActiveUser(i);
          daemonRemove(0);
        }
        if (size == 0 || z) {
          if (biometricsForUser.size() > 0) {
            for (Face face : biometricsForUser) {
              this.mFaceUtils.removeBiometricForUser(this.mContext, i, face.getBiometricId());
              Slog.i("Face10", "removing unknown template from fw, " + face.getBiometricId());
            }
            Face10.this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.biometrics.sensors.face.hidl.Face10$ProviderExtensionImpl$$ExternalSyntheticLambda3
                  @Override // java.lang.Runnable
                  public final void run() {
                    Face10.ProviderExtensionImpl.this.lambda$doTemplateSyncForUser$3(i);
                  }
                });
            SemBioLoggingManager.get().faceRemoved("doTemplateSyncForUser", -1);
            return;
          }
          return;
        }
        if (size > 0) {
          for (int i3 = 0; i3 < size; i3++) {
            int size2 = biometricsForUser.size();
            Slog.d("Face10", "enumerate: HAL ID=" + arrayList2.get(i3));
            int i4 = 0;
            while (true) {
              if (i4 >= size2) {
                i4 = -1;
                break;
              } else if (((Face) biometricsForUser.get(i4)).getBiometricId()
                  == ((Integer) arrayList2.get(i3)).intValue()) {
                break;
              } else {
                i4++;
              }
            }
            if (i4 >= 0) {
              biometricsForUser.remove(i4);
            } else {
              final Face face2 =
                  new Face(
                      this.mFaceUtils.getUniqueName(this.mContext, i),
                      ((Integer) arrayList2.get(i3)).intValue(),
                      j);
              this.mFaceUtils.addBiometricForUser(this.mContext, i, face2);
              Slog.i("Face10", "enumerate: adding unknown template, " + face2.getBiometricId());
              Face10.this.mHandler.post(
                  new Runnable() { // from class:
                                   // com.android.server.biometrics.sensors.face.hidl.Face10$ProviderExtensionImpl$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                      Face10.ProviderExtensionImpl.this.lambda$doTemplateSyncForUser$4(face2, i);
                    }
                  });
            }
          }
          for (final Face face3 : biometricsForUser) {
            this.mFaceUtils.removeBiometricForUser(this.mContext, i, face3.getBiometricId());
            Slog.i("Face10", "removing unknown template from fw, " + face3.getBiometricId());
            SemBioLoggingManager.get().faceRemoved("doTemplateSyncForUser", -1);
            Face10.this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.biometrics.sensors.face.hidl.Face10$ProviderExtensionImpl$$ExternalSyntheticLambda5
                  @Override // java.lang.Runnable
                  public final void run() {
                    Face10.ProviderExtensionImpl.this.lambda$doTemplateSyncForUser$5(i, face3);
                  }
                });
          }
        }
      } catch (Exception e) {
        Slog.w("Face10", "enumerate: " + e.getMessage());
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doTemplateSyncForUser$3(int i) {
      sendBroadcast("com.samsung.android.bio.face.intent.action.FACE_RESET", -1, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doTemplateSyncForUser$4(Face face, int i) {
      sendBroadcast(
          "com.samsung.android.bio.face.intent.action.FACE_ADDED", face.getBiometricId(), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doTemplateSyncForUser$5(int i, Face face) {
      if (Face10.mProviderExtImpl.mFaceUtils.getBiometricsForUser(this.mContext, i).size() > 0) {
        sendBroadcast(
            "com.samsung.android.bio.face.intent.action.FACE_REMOVED", face.getBiometricId(), i);
      } else {
        sendBroadcast("com.samsung.android.bio.face.intent.action.FACE_RESET", -1, i);
      }
    }

    public final boolean isCurrentClientKeyguard() {
      String currentClientOwnerString = getCurrentClientOwnerString();
      return currentClientOwnerString != null
          && Utils.isKeyguard(this.mContext, currentClientOwnerString);
    }

    public final String getCurrentClientOwnerString() {
      BaseClientMonitor currentClient = Face10.this.mScheduler.getCurrentClient();
      if (currentClient == null) {
        Slog.e("Face10", "getCurrentClientOwnerString : client is null");
        return null;
      }
      return currentClient.getOwnerString();
    }

    public final void daemonAuthenticate(long j) {
      int sehAuthenticate;
      IBiometricsFace daemon = Face10.this.getDaemon();
      if (daemon == null) {
        Slog.e("Face10", "authenticate(): no face HAL!");
        return;
      }
      startOperation(2);
      if (SemBiometricFeature.FEATURE_JDM_HAL) {
        long currentTimeMillis = System.currentTimeMillis();
        Slog.w(
            "Face10",
            "authenticate FINISH ("
                + (System.currentTimeMillis() - currentTimeMillis)
                + "ms) RESULT: "
                + daemon.authenticate(j));
        return;
      }
      long currentTimeMillis2 = System.currentTimeMillis();
      if (this.mIsAuthenticationExtOperation) {
        sehAuthenticate =
            ((vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace) daemon)
                .sehAuthenticateForIssuance(
                    j,
                    SemFaceUtils.getSecurityMode(getContext()),
                    SemFaceUtils.getFidoRequestDataAsArrayList(),
                    true,
                    this.mPreviewSurface != null);
      } else {
        sehAuthenticate =
            ((ISehBiometricsFace) daemon)
                .sehAuthenticate(
                    j,
                    SemFaceUtils.getSecurityMode(getContext()),
                    SemFaceUtils.getFidoRequestDataAsArrayList());
      }
      Slog.w(
          "Face10",
          "sehAuthenticate FINISH ("
              + (System.currentTimeMillis() - currentTimeMillis2)
              + "ms) RESULT: "
              + sehAuthenticate);
    }

    public final int daemonEnroll(ArrayList arrayList, int i, ArrayList arrayList2) {
      IBiometricsFace daemon = Face10.this.getDaemon();
      if (daemon == null) {
        Slog.w("Face10", "daemonEnroll: no face HAL!");
        return -1;
      }
      if (arrayList == null || arrayList.size() == 0) {
        Slog.w("Face10", "daemonEnroll: hardwareAuthToken is null or 0");
        return -1;
      }
      startOperation(1);
      if (Utils.DEBUG) {
        Slog.d("Face10", "hardwareAuthToken  = " + arrayList);
      }
      long currentTimeMillis = System.currentTimeMillis();
      int enroll = daemon.enroll(arrayList, i, arrayList2);
      Slog.w(
          "Face10",
          "enroll FINISH ("
              + (System.currentTimeMillis() - currentTimeMillis)
              + "ms) RESULT: "
              + enroll);
      return enroll;
    }

    public final int daemonSetActiveUser(int i) {
      IBiometricsFace daemon = Face10.this.getDaemon();
      int i2 = -1;
      if (daemon == null) {
        Slog.w("Face10", "daemonSetActiveUser: no face HAL!");
        return -1;
      }
      long j = 0;
      try {
        File file = new File(Environment.getDataVendorDeDirectory(i), "facedata");
        j = System.currentTimeMillis();
        i2 = daemon.setActiveUser(i, file.getAbsolutePath());
      } catch (RemoteException e) {
        Slog.e("Face10", "daemonSetActiveUser : " + e);
      }
      Slog.w(
          "Face10",
          "daemonSetActiveUser FINISH (" + (System.currentTimeMillis() - j) + "ms) RESULT: " + i2);
      return i2;
    }

    public final int daemonRemove(int i) {
      long j;
      IBiometricsFace daemon = Face10.this.getDaemon();
      int i2 = -1;
      if (daemon == null) {
        Slog.w("Face10", "daemonRemove: no face HAL!");
        return -1;
      }
      try {
        j = System.currentTimeMillis();
      } catch (RemoteException e) {
        e = e;
        j = 0;
      }
      try {
        i2 = daemon.remove(i);
      } catch (RemoteException e2) {
        e = e2;
        Slog.e("Face10", "daemonRemove : " + e);
        Slog.w(
            "Face10",
            "daemonRemove FINISH (" + (System.currentTimeMillis() - j) + "ms) RESULT: " + i2);
        return i2;
      }
      Slog.w(
          "Face10",
          "daemonRemove FINISH (" + (System.currentTimeMillis() - j) + "ms) RESULT: " + i2);
      return i2;
    }

    public final void daemonPauseEnroll() {
      ISehBiometricsFace iSehBiometricsFace = (ISehBiometricsFace) Face10.this.getDaemon();
      if (iSehBiometricsFace != null) {
        try {
          long currentTimeMillis = System.currentTimeMillis();
          Slog.w(
              "Face10",
              "sehPauseEnrollment FINISH ("
                  + (System.currentTimeMillis() - currentTimeMillis)
                  + "ms) RESULT: "
                  + iSehBiometricsFace.sehPauseEnrollment());
        } catch (RemoteException e) {
          Slog.e("Face10", "daemonPauseEnroll: ", e);
        }
      }
      this.mIsEnrollPausing = true;
    }

    public final void daemonResumeEnroll() {
      ISehBiometricsFace iSehBiometricsFace = (ISehBiometricsFace) Face10.this.getDaemon();
      if (iSehBiometricsFace != null) {
        try {
          long currentTimeMillis = System.currentTimeMillis();
          Slog.w(
              "Face10",
              "sehResumeEnrollment FINISH ("
                  + (System.currentTimeMillis() - currentTimeMillis)
                  + "ms) RESULT: "
                  + iSehBiometricsFace.sehResumeEnrollment());
        } catch (RemoteException e) {
          Slog.e("Face10", "daemonResumeEnroll: ", e);
        }
      }
      Face10.mProviderExtImpl.mIsEnrollPausing = false;
      startOperationTimeout(Utils.isTalkBackEnabled(this.mContext) ? 60000 : 30000);
      Face10.mProviderExtImpl.mIsEnrollPausing = false;
    }

    public final void daemonPauseAuth() {
      if (Face10.this.mScheduler.getCurrentClient() instanceof FaceAuthenticationClient) {
        ISehBiometricsFace iSehBiometricsFace = (ISehBiometricsFace) Face10.this.getDaemon();
        if (iSehBiometricsFace != null) {
          try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w(
                "Face10",
                "sehPauseEnrollment(auth) FINISH ("
                    + (System.currentTimeMillis() - currentTimeMillis)
                    + "ms) RESULT: "
                    + iSehBiometricsFace.sehPauseEnrollment());
            return;
          } catch (RemoteException e) {
            Slog.e("Face10", "daemonPauseEnrollment(auth): ", e);
            return;
          }
        }
        return;
      }
      Log.w("Face10", "daemonPauseAuth skipped");
    }

    public final void daemonResumeAuth() {
      if (Face10.this.mScheduler.getCurrentClient() instanceof FaceAuthenticationClient) {
        ISehBiometricsFace iSehBiometricsFace = (ISehBiometricsFace) Face10.this.getDaemon();
        if (iSehBiometricsFace != null) {
          try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w(
                "Face10",
                "sehResumeEnrollment(auth) FINISH ("
                    + (System.currentTimeMillis() - currentTimeMillis)
                    + "ms) RESULT: "
                    + iSehBiometricsFace.sehResumeEnrollment());
            return;
          } catch (RemoteException e) {
            Slog.e("Face10", "daemonResumeAuth(auth): ", e);
            return;
          }
        }
        return;
      }
      Log.w("Face10", "daemonResumeAuth skipped");
    }

    public final void daemonSessionOpen() {
      ISehBiometricsFace iSehBiometricsFace = (ISehBiometricsFace) Face10.this.getDaemon();
      if (iSehBiometricsFace != null) {
        try {
          long currentTimeMillis = System.currentTimeMillis();
          Slog.w(
              "Face10",
              "sehOpenTaSession FINISH ("
                  + (System.currentTimeMillis() - currentTimeMillis)
                  + "ms) RESULT: "
                  + iSehBiometricsFace.sehOpenTaSession());
        } catch (RemoteException e) {
          Slog.e("Face10", "daemonSessionOpen: ", e);
        }
      }
    }

    public final void daemonSessionClose() {
      ISehBiometricsFace iSehBiometricsFace = (ISehBiometricsFace) Face10.this.getDaemon();
      if (iSehBiometricsFace != null) {
        try {
          long currentTimeMillis = System.currentTimeMillis();
          Slog.w(
              "Face10",
              "sehCloseTaSession FINISH ("
                  + (System.currentTimeMillis() - currentTimeMillis)
                  + "ms) RESULT: "
                  + iSehBiometricsFace.sehCloseTaSession());
        } catch (RemoteException e) {
          Slog.e("Face10", "daemonSessionClose: ", e);
        }
      }
    }

    public final boolean daemonIsSessionClose() {
      ISehBiometricsFace iSehBiometricsFace = (ISehBiometricsFace) Face10.this.getDaemon();
      if (iSehBiometricsFace == null) {
        return false;
      }
      try {
        long currentTimeMillis = System.currentTimeMillis();
        boolean sehIsTaSessionClosed = iSehBiometricsFace.sehIsTaSessionClosed();
        Slog.w(
            "Face10",
            "sehIsTaSessionClosed FINISH ("
                + (System.currentTimeMillis() - currentTimeMillis)
                + "ms) RESULT: "
                + sehIsTaSessionClosed);
        return sehIsTaSessionClosed;
      } catch (RemoteException e) {
        Slog.e("Face10", "daemonIsSessionClose: ", e);
        return false;
      }
    }

    public final int daemonEnumerateUser() {
      ISehBiometricsFace iSehBiometricsFace = (ISehBiometricsFace) Face10.this.getDaemon();
      if (iSehBiometricsFace == null) {
        return -1;
      }
      try {
        long currentTimeMillis = System.currentTimeMillis();
        int enumerate = iSehBiometricsFace.enumerate();
        Slog.w(
            "Face10",
            "daemonEnumerateUser FINISH ("
                + (System.currentTimeMillis() - currentTimeMillis)
                + "ms) RESULT: "
                + enumerate);
        return enumerate;
      } catch (RemoteException e) {
        Slog.e("Face10", "daemonEnumerateUser: ", e);
        return -1;
      }
    }

    public final void sendAcquired(int i, int i2) {
      BaseClientMonitor currentClient = Face10.this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof AcquisitionClient)) {
        Slog.e(
            "Face10",
            "sendAcquired - not AcquisitionClient: " + Utils.getClientName(currentClient));
        return;
      }
      ((AcquisitionClient) currentClient).onAcquired(i, i2);
    }

    public final void sendError(int i, int i2) {
      BaseClientMonitor currentClient = Face10.this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof AcquisitionClient)) {
        Slog.e(
            "Face10", "sendError - not AcquisitionClient: " + Utils.getClientName(currentClient));
        return;
      }
      ((AcquisitionClient) currentClient).onError(i, i2);
    }

    public final int getCurrentClientHashID() {
      BaseClientMonitor currentClient = Face10.this.mScheduler.getCurrentClient();
      if (currentClient == null) {
        Slog.e("Face10", "getCurrentClientHashID : client is null");
        return 0;
      }
      return currentClient.getHashID();
    }

    public final int getCurrentClientUserID() {
      BaseClientMonitor currentClient = Face10.this.mScheduler.getCurrentClient();
      if (currentClient == null) {
        Slog.e("Face10", "getCurrentClientHashID : client is null");
        return 0;
      }
      return currentClient.getTargetUserId();
    }

    public void setHalDeviceId(long j) {
      this.mHalDeviceId = j;
    }

    public long getHalDeviceId() {
      return this.mHalDeviceId;
    }

    public final void sendImageProcessed(final int i, final int i2, final int i3, final int i4) {
      Face10.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.face.hidl.Face10$ProviderExtensionImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              Face10.ProviderExtensionImpl.this.lambda$sendImageProcessed$6(i, i2, i3, i4);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendImageProcessed$6(int i, int i2, int i3, int i4) {
      BaseClientMonitor currentClient = Face10.this.mScheduler.getCurrentClient();
      if (currentClient == null) {
        Slog.d("Face10", "sendImageProcessed: client is null");
        return;
      }
      if (!(currentClient instanceof FaceEnrollClient)
          && (!(currentClient instanceof FaceAuthenticationClient)
              || !this.mIsAuthenticationExtOperation)) {
        Slog.e(
            "Face10",
            "sendImageProcessed : Wrong Client : "
                + Utils.getClientName(currentClient)
                + ", Proto="
                + currentClient.getProtoEnum()
                + ", ext="
                + this.mIsAuthenticationExtOperation);
        this.mPreviewImage = null;
        return;
      }
      Bundle bundle = new Bundle();
      try {
        if (this.mMemoryFile == null) {
          this.mMemoryFile = new MemoryFile("face_preview", this.mPreviewImage.length);
        }
        MemoryFile memoryFile = this.mMemoryFile;
        byte[] bArr = this.mPreviewImage;
        memoryFile.writeBytes(bArr, 0, 0, bArr.length);
        bundle.putParcelable(
            "memoryfile_descriptor",
            ParcelFileDescriptor.dup(
                (FileDescriptor)
                    MemoryFile.class
                        .getDeclaredMethod("getFileDescriptor", new Class[0])
                        .invoke(this.mMemoryFile, new Object[0])));
        this.mPreviewImage = new byte[1];
      } catch (Exception e) {
        Log.e("Face10", "sendImageProcessed MemoryFile: ", e);
      }
      try {
        if (this.mIsAuthenticationExtOperation) {
          ((FaceAuthenticationClient) currentClient)
              .onImageProcessed(this.mPreviewImage, i, i2, i3, i4, bundle);
        } else {
          ((FaceEnrollClient) currentClient)
              .onImageProcessed(this.mPreviewImage, i, i2, i3, i4, bundle);
        }
      } catch (Exception e2) {
        Log.e("Face10", "sendImageProcessed onImageProcessed: ", e2);
      }
      this.mPreviewImage = null;
    }

    public final void registerBroadcastEvents() {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
      this.mContext.registerReceiver(new C08866(), intentFilter);
    }

    /* renamed from: com.android.server.biometrics.sensors.face.hidl.Face10$ProviderExtensionImpl$6 */
    public class C08866 extends BroadcastReceiver {
      public C08866() {}

      @Override // android.content.BroadcastReceiver
      public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("Face10", "onBroadCastReceive : " + action);
        action.hashCode();
        if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
          Face10.this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.biometrics.sensors.face.hidl.Face10$ProviderExtensionImpl$6$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  Face10.ProviderExtensionImpl.C08866.this.lambda$onReceive$0();
                }
              });
        }
      }

      /* JADX INFO: Access modifiers changed from: private */
      public /* synthetic */ void lambda$onReceive$0() {
        ProviderExtensionImpl.this.stopOperation();
        ProviderExtensionImpl.this.daemonCancel();
      }
    }

    public final void setCancellationSignal(
        final FaceAuthenticationClient faceAuthenticationClient, final long j) {
      CancellationSignal cancellationSignal = new CancellationSignal();
      cancellationSignal.setOnCancelListener(
          new CancellationSignal
              .OnCancelListener() { // from class:
                                    // com.android.server.biometrics.sensors.face.hidl.Face10.ProviderExtensionImpl.7
            @Override // android.os.CancellationSignal.OnCancelListener
            public void onCancel() {
              Face10.this.cancelAuthentication(
                  faceAuthenticationClient.getSensorId(), faceAuthenticationClient.getToken(), j);
            }
          });
      faceAuthenticationClient.setCancellationSignal(cancellationSignal);
    }

    public final void sendSucceeded(Bundle bundle) {
      BaseClientMonitor currentClient = Face10.this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof AcquisitionClient)) {
        Slog.e(
            "Face10", "sendSuccess - not AcquisitionClient: " + Utils.getClientName(currentClient));
        return;
      }
      ClientMonitorCallbackConverter listener = currentClient.getListener();
      if (listener != null) {
        try {
          listener.onSemAuthenticationSucceededWithBundle(currentClient.getTargetUserId(), bundle);
        } catch (RemoteException e) {
          Slog.e("Face10", "sendSucceeded : Unable to notify listener, finishing", e);
        }
      }
    }

    public final void sendFailed() {
      BaseClientMonitor currentClient = Face10.this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof AcquisitionClient)) {
        Slog.e(
            "Face10", "sendFailed - not AcquisitionClient: " + Utils.getClientName(currentClient));
        return;
      }
      ClientMonitorCallbackConverter listener = currentClient.getListener();
      if (listener != null) {
        try {
          listener.onSemAuthenticationFailed();
        } catch (RemoteException e) {
          Slog.e("Face10", "sendFailed : Unable to notify listener, finishing", e);
        }
      }
    }
  }
}
