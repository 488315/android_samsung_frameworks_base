package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.sensors.SemTestHalHelper;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class SemTpaTestSession extends ISession.Stub {
  public final ISessionCallback mCallback;
  public long mChallenge;
  public int mCurrentEnrollmentId;
  public final Set mEnrollmentIds;

  /* renamed from: mH */
  public final Handler f1663mH;
  public final String mHash;
  public final AtomicBoolean mIsCancellation;
  public final Random mRandom;
  public SemTestHalHelper mTestHalHelper;
  public final int mVersion;

  public ICancellationSignal authenticate(long j) {
    return null;
  }

  public ICancellationSignal authenticateWithContext(long j, OperationContext operationContext) {
    return null;
  }

  public void close() {}

  public ICancellationSignal detectInteraction() {
    return null;
  }

  public ICancellationSignal detectInteractionWithContext(OperationContext operationContext) {
    return null;
  }

  public ICancellationSignal enrollWithContext(
      HardwareAuthToken hardwareAuthToken, OperationContext operationContext) {
    return null;
  }

  public void enumerateEnrollments() {}

  public void getAuthenticatorId() {}

  public void invalidateAuthenticatorId() {}

  public void onContextChanged(OperationContext operationContext) {}

  public void onPointerCancelWithContext(PointerContext pointerContext) {}

  public void onPointerDown(int i, int i2, int i3, float f, float f2) {}

  public void onPointerDownWithContext(PointerContext pointerContext) {}

  public void onPointerUp(int i) {}

  public void onPointerUpWithContext(PointerContext pointerContext) {}

  public void onUiReady() {}

  public void removeEnrollments(int[] iArr) {}

  public void resetLockout(HardwareAuthToken hardwareAuthToken) {}

  public void setIgnoreDisplayTouches(boolean z) {}

  public void generateChallenge() {
    Slog.w("fingerprint.aidl.SemTpaTestSession", "generateChallenge");
    this.mChallenge = this.mRandom.nextLong();
    this.f1663mH.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestSession$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            SemTpaTestSession.this.lambda$generateChallenge$0();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$generateChallenge$0() {
    try {
      this.mCallback.onChallengeGenerated(this.mChallenge);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public void revokeChallenge(final long j) {
    this.f1663mH.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestSession$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            SemTpaTestSession.this.lambda$revokeChallenge$1(j);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$revokeChallenge$1(long j) {
    try {
      this.mCallback.onChallengeRevoked(j);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestSession$1 */
  public class C09291 extends ICancellationSignal.Stub {
    public String getInterfaceHash() {
      return "a9ebb97f5abea1dc6800b69d821ef61944e80e65";
    }

    public int getInterfaceVersion() {
      return 3;
    }

    public C09291() {}

    public void cancel() {
      SemTpaTestSession.this.mIsCancellation.set(true);
      SemTpaTestSession.this.f1663mH.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestSession$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              SemTpaTestSession.C09291.this.lambda$cancel$0();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancel$0() {
      try {
        SemTpaTestSession.this.mCallback.onError((byte) 5, 7);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  }

  public ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken) {
    C09291 c09291 = new C09291();
    this.f1663mH.postDelayed(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestSession$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            SemTpaTestSession.this.lambda$enroll$2();
          }
        },
        600L);
    return c09291;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$enroll$2() {
    int nextInt = this.mRandom.nextInt();
    while (this.mEnrollmentIds.contains(Integer.valueOf(nextInt))) {
      nextInt = this.mRandom.nextInt();
    }
    this.mCurrentEnrollmentId = nextInt;
    List<SemTestHalHelper.Action> enrollActionList = this.mTestHalHelper.getEnrollActionList();
    Slog.d(
        "fingerprint.aidl.SemTpaTestSession",
        "start enrollTPA: "
            + this.mCurrentEnrollmentId
            + ", action size = "
            + enrollActionList.size());
    for (SemTestHalHelper.Action action : enrollActionList) {
      if (this.mIsCancellation.get()) {
        return;
      }
      try {
        Thread.sleep(action.getDelay());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      action.run();
      if (action.isFinishEnroll()) {
        this.mEnrollmentIds.add(Integer.valueOf(this.mCurrentEnrollmentId));
        this.mCurrentEnrollmentId = 0;
      }
    }
  }

  public int getInterfaceVersion() {
    return this.mVersion;
  }

  public String getInterfaceHash() {
    return this.mHash;
  }
}
