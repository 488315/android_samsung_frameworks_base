package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import com.android.server.ServiceThread;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda0;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.sensors.SemTestHalHelper;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint;
import vendor.samsung.hardware.biometrics.fingerprint.SehResult;

/* loaded from: classes.dex */
public class SemTpaTestHal extends TestHal implements SemTestHalHelper.Callback {
  public CountDownLatch mActionDelayLatch;
  public long mChallenge;
  public final Context mContext;
  public int mCurrentEnrollmentId;
  public IBinder.DeathRecipient mDeathRecipient;
  public ISessionCallback mISessionCallback;
  public ISehFingerprint mSehFingerprint;
  ServiceThread mThread;
  public CountDownLatch mTspDelayLatch;
  public final SparseIntArray mRequestActionTable = new SparseIntArray();
  public SemTestHalHelper mTestHalHelper = new SemTestHalHelper(2, this);
  final Set mEnrolledIds = new HashSet();
  public final SparseLongArray mAuthenticatorID = new SparseLongArray();

  @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
  public void deliverEnumerate(int i, int i2, int i3) {}

  public SemTpaTestHal(Context context) {
    this.mContext = context;
  }

  public void injectTestHalHelper(SemTestHalHelper semTestHalHelper) {
    this.mTestHalHelper = semTestHalHelper;
  }

  @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
  public void deliverAcquiredEvent(int i, int i2) {
    try {
      this.mISessionCallback.onAcquired(AidlConversionUtils.toAidlAcquiredInfo(i), i2);
    } catch (RemoteException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
  public void deliverErrorEvent(int i, int i2) {
    try {
      this.mISessionCallback.onError(AidlConversionUtils.toAidlError(i), i2);
    } catch (RemoteException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
  public void deliverEnrollResult(int i) {
    try {
      this.mISessionCallback.onEnrollmentProgress(this.mCurrentEnrollmentId, i);
    } catch (RemoteException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
  public void deliverAuthenticationResult(int i) {
    if (this.mEnrolledIds.isEmpty()) {
      deliverErrorEvent(8, 1004);
      return;
    }
    try {
      if (i != 0) {
        this.mISessionCallback.onAuthenticationSucceeded(
            ((Integer) this.mEnrolledIds.iterator().next()).intValue(),
            HardwareAuthTokenUtils.toHardwareAuthToken(new byte[69]));
      } else {
        this.mISessionCallback.onAuthenticationFailed();
      }
    } catch (RemoteException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
  public void deliverTspEvent(int i) {
    Intent intent = new Intent("com.samsung.android.fingerprint.action.FINGER_ON_DISPLAY");
    if (i == 2) {
      intent.putExtra("info", 15);
    } else if (i == 1) {
      intent.putExtra("info", 16);
    }
    try {
      this.mContext.sendBroadcast(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.aidl.TestHal
  public ISession createSession(int i, int i2, ISessionCallback iSessionCallback) {
    Slog.w("fingerprint.aidl.SemTpaTestHal", "createSession, sensorId: " + i + " userId: " + i2);
    if (this.mThread == null) {
      start();
    }
    this.mISessionCallback = iSessionCallback;
    return new C09261(this.mThread.getThreadHandler(), iSessionCallback, i2);
  }

  /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1 */
  public class C09261 extends ISession.Stub {
    public final /* synthetic */ Handler val$handler;
    public final /* synthetic */ ISessionCallback val$sessionCallback;
    public final /* synthetic */ int val$userId;

    public String getInterfaceHash() {
      return "637371b53fb7faf9bd43aa51b72c23852d6e6d96";
    }

    public int getInterfaceVersion() {
      return 3;
    }

    public C09261(Handler handler, ISessionCallback iSessionCallback, int i) {
      this.val$handler = handler;
      this.val$sessionCallback = iSessionCallback;
      this.val$userId = i;
    }

    public void generateChallenge() {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "generateChallenge");
      SemTpaTestHal.this.mChallenge = new Random().nextLong();
      Handler handler = this.val$handler;
      final ISessionCallback iSessionCallback = this.val$sessionCallback;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
              SemTpaTestHal.C09261.this.lambda$generateChallenge$0(iSessionCallback);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$generateChallenge$0(ISessionCallback iSessionCallback) {
      try {
        iSessionCallback.onChallengeGenerated(SemTpaTestHal.this.mChallenge);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    public void revokeChallenge(final long j) {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "revokeChallenge: " + j);
      Handler handler = this.val$handler;
      final ISessionCallback iSessionCallback = this.val$sessionCallback;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              SemTpaTestHal.C09261.lambda$revokeChallenge$1(iSessionCallback, j);
            }
          });
    }

    public static /* synthetic */ void lambda$revokeChallenge$1(
        ISessionCallback iSessionCallback, long j) {
      try {
        iSessionCallback.onChallengeRevoked(j);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    public ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken) {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "enroll: " + hardwareAuthToken);
      final CancellationSignal cancellationSignal = new CancellationSignal();
      final Handler handler = this.val$handler;
      final ISessionCallback iSessionCallback = this.val$sessionCallback;
      cancellationSignal.setOnCancelListener(
          new CancellationSignal
              .OnCancelListener() { // from class:
                                    // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda3
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
              SemTpaTestHal.C09261.this.lambda$enroll$3(handler, iSessionCallback);
            }
          });
      Handler handler2 = this.val$handler;
      final int i = this.val$userId;
      handler2.postDelayed(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              SemTpaTestHal.C09261.this.lambda$enroll$4(cancellationSignal, i);
            }
          },
          600L);
      return SemTpaTestHal.this.createICancellationSignal(cancellationSignal);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enroll$3(
        Handler handler, final ISessionCallback iSessionCallback) {
      if (SemTpaTestHal.this.mActionDelayLatch != null) {
        SemTpaTestHal.this.mActionDelayLatch.countDown();
      }
      if (SemTpaTestHal.this.mTspDelayLatch != null) {
        SemTpaTestHal.this.mTspDelayLatch.countDown();
      }
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
              SemTpaTestHal.C09261.lambda$enroll$2(iSessionCallback);
            }
          });
    }

    public static /* synthetic */ void lambda$enroll$2(ISessionCallback iSessionCallback) {
      try {
        iSessionCallback.onError((byte) 5, 0);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enroll$4(CancellationSignal cancellationSignal, int i) {
      Random random = new Random();
      int nextInt = random.nextInt();
      while (SemTpaTestHal.this.mEnrolledIds.contains(Integer.valueOf(nextInt))) {
        nextInt = random.nextInt();
      }
      SemTpaTestHal.this.mCurrentEnrollmentId = nextInt;
      List<SemTestHalHelper.Action> enrollActionList =
          SemTpaTestHal.this.mTestHalHelper.getEnrollActionList();
      Slog.d(
          "fingerprint.aidl.SemTpaTestHal",
          "start enrollTPA: "
              + SemTpaTestHal.this.mCurrentEnrollmentId
              + ", action size = "
              + enrollActionList.size());
      for (SemTestHalHelper.Action action : enrollActionList) {
        if (cancellationSignal.isCanceled()) {
          return;
        }
        SemTpaTestHal.this.mActionDelayLatch = new CountDownLatch(1);
        try {
          SemTpaTestHal.this.mActionDelayLatch.await(action.getDelay(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        action.run();
        if (action.isFinishEnroll()) {
          SemTpaTestHal semTpaTestHal = SemTpaTestHal.this;
          semTpaTestHal.mEnrolledIds.add(Integer.valueOf(semTpaTestHal.mCurrentEnrollmentId));
          SemTpaTestHal.this.mCurrentEnrollmentId = 0;
          SemTpaTestHal.this.mAuthenticatorID.put(i, random.nextLong());
        }
      }
    }

    public ICancellationSignal authenticate(final long j) {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "authenticate, " + j);
      final CancellationSignal cancellationSignal = new CancellationSignal();
      final Handler handler = this.val$handler;
      final ISessionCallback iSessionCallback = this.val$sessionCallback;
      cancellationSignal.setOnCancelListener(
          new CancellationSignal
              .OnCancelListener() { // from class:
                                    // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda5
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
              SemTpaTestHal.C09261.this.lambda$authenticate$6(handler, iSessionCallback);
            }
          });
      long delayAuthAction = SemTpaTestHal.this.mTestHalHelper.getDelayAuthAction();
      Handler handler2 = this.val$handler;
      Runnable runnable =
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
              SemTpaTestHal.C09261.this.lambda$authenticate$7(j, cancellationSignal);
            }
          };
      if (delayAuthAction == 0) {
        delayAuthAction = 600;
      }
      handler2.postDelayed(runnable, delayAuthAction);
      return SemTpaTestHal.this.createICancellationSignal(cancellationSignal);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticate$6(
        Handler handler, final ISessionCallback iSessionCallback) {
      if (SemTpaTestHal.this.mActionDelayLatch != null) {
        SemTpaTestHal.this.mActionDelayLatch.countDown();
      }
      if (SemTpaTestHal.this.mTspDelayLatch != null) {
        SemTpaTestHal.this.mTspDelayLatch.countDown();
      }
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
              SemTpaTestHal.C09261.lambda$authenticate$5(iSessionCallback);
            }
          });
    }

    public static /* synthetic */ void lambda$authenticate$5(ISessionCallback iSessionCallback) {
      try {
        iSessionCallback.onError((byte) 5, 0);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticate$7(
        long j, CancellationSignal cancellationSignal) {
      if (SemTpaTestHal.this.mEnrolledIds.isEmpty()) {
        SemTpaTestHal.this.deliverErrorEvent(8, 1004);
        return;
      }
      List<SemTestHalHelper.Action> authActionList =
          SemTpaTestHal.this.mTestHalHelper.getAuthActionList();
      Slog.d(
          "fingerprint.aidl.SemTpaTestHal",
          "start authenticateTPA: " + j + ", action size = " + authActionList.size());
      for (SemTestHalHelper.Action action : authActionList) {
        if (cancellationSignal.isCanceled()) {
          return;
        }
        SemTpaTestHal.this.mActionDelayLatch = new CountDownLatch(1);
        try {
          SemTpaTestHal.this.mActionDelayLatch.await(action.getDelay(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        action.run();
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL
            && action.getCallbackType() == SemTestHalHelper.CallbackType.TSP_FOD
            && action.getValue() == 2) {
          SemTpaTestHal.this.mTspDelayLatch = new CountDownLatch(1);
          try {
            SemTpaTestHal.this.mTspDelayLatch.await(10000L, TimeUnit.MICROSECONDS);
          } catch (InterruptedException e2) {
            e2.printStackTrace();
          }
        }
      }
    }

    public ICancellationSignal detectInteraction() {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "detectInteraction");
      CancellationSignal cancellationSignal = new CancellationSignal();
      final Handler handler = this.val$handler;
      final ISessionCallback iSessionCallback = this.val$sessionCallback;
      cancellationSignal.setOnCancelListener(
          new CancellationSignal
              .OnCancelListener() { // from class:
                                    // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda1
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
              SemTpaTestHal.C09261.lambda$detectInteraction$9(handler, iSessionCallback);
            }
          });
      Handler handler2 = this.val$handler;
      final ISessionCallback iSessionCallback2 = this.val$sessionCallback;
      handler2.postDelayed(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              SemTpaTestHal.C09261.this.lambda$detectInteraction$10(iSessionCallback2);
            }
          },
          600L);
      return SemTpaTestHal.this.createICancellationSignal(cancellationSignal);
    }

    public static /* synthetic */ void lambda$detectInteraction$9(
        Handler handler, final ISessionCallback iSessionCallback) {
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
              SemTpaTestHal.C09261.lambda$detectInteraction$8(iSessionCallback);
            }
          });
    }

    public static /* synthetic */ void lambda$detectInteraction$8(
        ISessionCallback iSessionCallback) {
      try {
        iSessionCallback.onError((byte) 5, 0);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$detectInteraction$10(ISessionCallback iSessionCallback) {
      if (SemTpaTestHal.this.mEnrolledIds.isEmpty()) {
        SemTpaTestHal.this.deliverErrorEvent(11, 0);
        return;
      }
      try {
        iSessionCallback.onInteractionDetected();
      } catch (RemoteException | NullPointerException e) {
        e.printStackTrace();
      }
    }

    public void enumerateEnrollments() {
      Slog.w(
          "fingerprint.aidl.SemTpaTestHal",
          "enumerateEnrollments: " + SemTpaTestHal.this.mEnrolledIds.size());
      this.val$sessionCallback.onEnrollmentsEnumerated(
          SemTpaTestHal.this.mEnrolledIds.stream()
              .mapToInt(new AudioService$$ExternalSyntheticLambda0())
              .toArray());
    }

    public void removeEnrollments(int[] iArr) {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "removeEnrollments");
      for (int i : iArr) {
        SemTpaTestHal.this.mEnrolledIds.remove(Integer.valueOf(i));
      }
      this.val$sessionCallback.onEnrollmentsRemoved(iArr);
    }

    public void getAuthenticatorId() {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "getAuthenticatorId");
      this.val$sessionCallback.onAuthenticatorIdRetrieved(
          SemTpaTestHal.this.mAuthenticatorID.get(this.val$userId, 0L));
    }

    public void invalidateAuthenticatorId() {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "invalidateAuthenticatorId");
      SemTpaTestHal.this.mAuthenticatorID.put(this.val$userId, new Random().nextLong());
      this.val$sessionCallback.onAuthenticatorIdInvalidated(
          SemTpaTestHal.this.mAuthenticatorID.get(this.val$userId, 0L));
    }

    public void resetLockout(HardwareAuthToken hardwareAuthToken) {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "resetLockout");
      this.val$sessionCallback.onLockoutCleared();
    }

    public void close() {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "close");
      this.val$sessionCallback.onSessionClosed();
    }

    public void onPointerDown(int i, int i2, int i3, float f, float f2) {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "onPointerDown");
    }

    public void onPointerUp(int i) {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "onPointerUp");
    }

    public void onUiReady() {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "onUiReady");
    }

    public ICancellationSignal authenticateWithContext(long j, OperationContext operationContext) {
      return authenticate(j);
    }

    public ICancellationSignal enrollWithContext(
        HardwareAuthToken hardwareAuthToken, OperationContext operationContext) {
      return enroll(hardwareAuthToken);
    }

    public ICancellationSignal detectInteractionWithContext(OperationContext operationContext) {
      return detectInteraction();
    }

    public void onPointerDownWithContext(PointerContext pointerContext) {
      onPointerDown(
          pointerContext.pointerId,
          (int) pointerContext.x,
          (int) pointerContext.y,
          pointerContext.minor,
          pointerContext.major);
    }

    public void onPointerUpWithContext(PointerContext pointerContext) {
      onPointerUp(pointerContext.pointerId);
    }

    public void onContextChanged(OperationContext operationContext) {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "onContextChanged");
    }

    public void onPointerCancelWithContext(PointerContext pointerContext) {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "onPointerCancelWithContext");
    }

    public void setIgnoreDisplayTouches(boolean z) {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "setIgnoreDisplayTouches");
    }
  }

  public void start() {
    initActions();
    quitThread();
    ServiceThread serviceThread = new ServiceThread("fingerprint.aidl.SemTpaTestHal", -2, true);
    this.mThread = serviceThread;
    serviceThread.start();
  }

  public void linkToDeath(IBinder.DeathRecipient deathRecipient) {
    this.mDeathRecipient = deathRecipient;
  }

  public void updateAction() {
    ServiceThread serviceThread = this.mThread;
    if (serviceThread == null) {
      return;
    }
    serviceThread
        .getThreadHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SemTpaTestHal.this.initActions();
              }
            });
  }

  public void setTpaRequestCommandAction(String[] strArr) {
    try {
      this.mRequestActionTable.put(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1]));
    } catch (Exception e) {
      Slog.w("fingerprint.aidl.SemTpaTestHal", "setTpaRequestCommandAction: " + e.getMessage());
    }
  }

  public final void initActions() {
    this.mTestHalHelper.initActions();
    this.mRequestActionTable.clear();
    this.mRequestActionTable.put(6, 100040);
  }

  public void destroy() {
    quitThread();
    this.mSehFingerprint = null;
  }

  public ISehFingerprint getSehFingerprint() {
    if (this.mSehFingerprint == null) {
      this.mSehFingerprint =
          new ISehFingerprint
              .Stub() { // from class:
                        // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal.2
            @Override // vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint.Stub,
                      // android.os.IInterface
            public IBinder asBinder() {
              return null;
            }

            @Override // vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint
            public String getInterfaceHash() {
              return "";
            }

            @Override // vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint
            public int getInterfaceVersion() {
              return 0;
            }

            @Override // vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint
            public SehResult sehRequest(int i, int i2, int i3, byte[] bArr) {
              SehResult sehResult = new SehResult();
              sehResult.retValue = SemTpaTestHal.this.mRequestActionTable.get(i2, 0);
              return sehResult;
            }
          };
    }
    return this.mSehFingerprint;
  }

  public final void quitThread() {
    ServiceThread serviceThread = this.mThread;
    if (serviceThread != null) {
      serviceThread.quitSafely();
      this.mThread = null;
    }
  }

  public final ICancellationSignal createICancellationSignal(
      final CancellationSignal cancellationSignal) {
    return new ICancellationSignal
        .Stub() { // from class:
                  // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal.3
      public String getInterfaceHash() {
        return "a9ebb97f5abea1dc6800b69d821ef61944e80e65";
      }

      public int getInterfaceVersion() {
        return 3;
      }

      public void cancel() {
        cancellationSignal.cancel();
      }
    };
  }
}
