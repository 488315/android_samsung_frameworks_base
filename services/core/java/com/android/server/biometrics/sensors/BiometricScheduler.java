package com.android.server.biometrics.sensors;

import android.hardware.biometrics.IBiometricService;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.modules.expresslog.Counter;
import com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class BiometricScheduler {
  public final IBiometricService mBiometricService;
  public final String mBiometricTag;
  public final ArrayDeque mCrashStates;
  BiometricSchedulerOperation mCurrentOperation;
  public final GestureAvailabilityDispatcher mGestureAvailabilityDispatcher;
  public final Handler mHandler;
  public final ClientMonitorCallback mInternalCallback;
  final Deque mPendingOperations;
  public final List mRecentOperations;
  public final int mRecentOperationsLimit;
  public final int mSensorType;
  public int mTotalOperationsHandled;

  public final class CrashState {
    public final String currentOperation;
    public final List pendingOperations;
    public final String timestamp;

    public CrashState(String str, String str2, List list) {
      this.timestamp = str;
      this.currentOperation = str2;
      this.pendingOperations = list;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.timestamp);
      sb.append(": ");
      sb.append("Current Operation: {");
      sb.append(this.currentOperation);
      sb.append("}");
      sb.append(", Pending Operations(");
      sb.append(this.pendingOperations.size());
      sb.append(")");
      if (!this.pendingOperations.isEmpty()) {
        sb.append(": ");
      }
      for (int i = 0; i < this.pendingOperations.size(); i++) {
        sb.append((String) this.pendingOperations.get(i));
        if (i < this.pendingOperations.size() - 1) {
          sb.append(", ");
        }
      }
      return sb.toString();
    }
  }

  /* renamed from: com.android.server.biometrics.sensors.BiometricScheduler$1 */
  public class C08361 implements ClientMonitorCallback {
    public C08361() {}

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onClientStarted(BaseClientMonitor baseClientMonitor) {
      Slog.d(BiometricScheduler.this.getTag(), "[Started] " + baseClientMonitor);
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onClientFinished(final BaseClientMonitor baseClientMonitor, final boolean z) {
      BiometricScheduler.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.BiometricScheduler$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              BiometricScheduler.C08361.this.lambda$onClientFinished$0(baseClientMonitor, z);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClientFinished$0(
        BaseClientMonitor baseClientMonitor, boolean z) {
      BiometricScheduler biometricScheduler = BiometricScheduler.this;
      BiometricSchedulerOperation biometricSchedulerOperation =
          biometricScheduler.mCurrentOperation;
      if (biometricSchedulerOperation == null) {
        Slog.e(
            biometricScheduler.getTag(),
            "[Finishing] "
                + baseClientMonitor
                + " but current operation is null, success: "
                + z
                + ", possible lifecycle bug in clientMonitor implementation?");
        return;
      }
      if (!biometricSchedulerOperation.isFor(baseClientMonitor)) {
        Slog.e(
            BiometricScheduler.this.getTag(),
            "[Ignoring Finish] "
                + baseClientMonitor
                + " does not match current: "
                + BiometricScheduler.this.mCurrentOperation);
        return;
      }
      Slog.d(
          BiometricScheduler.this.getTag(), "[Finishing] " + baseClientMonitor + ", success: " + z);
      if (BiometricScheduler.this.mGestureAvailabilityDispatcher != null) {
        BiometricScheduler.this.mGestureAvailabilityDispatcher.markSensorActive(
            BiometricScheduler.this.mCurrentOperation.getSensorId(), false);
      }
      if (BiometricScheduler.this.mRecentOperations.size()
          >= BiometricScheduler.this.mRecentOperationsLimit) {
        BiometricScheduler.this.mRecentOperations.remove(0);
      }
      BiometricScheduler.this.mRecentOperations.add(
          Integer.valueOf(BiometricScheduler.this.mCurrentOperation.getProtoEnum()));
      BiometricScheduler.this.mCurrentOperation.destroy();
      BiometricScheduler biometricScheduler2 = BiometricScheduler.this;
      biometricScheduler2.mCurrentOperation = null;
      biometricScheduler2.mTotalOperationsHandled++;
      BiometricScheduler.this.startNextOperationIfIdle();
    }
  }

  public BiometricScheduler(
      String str,
      Handler handler,
      int i,
      GestureAvailabilityDispatcher gestureAvailabilityDispatcher,
      IBiometricService iBiometricService,
      int i2) {
    this.mInternalCallback = new C08361();
    this.mBiometricTag = str;
    this.mHandler = handler;
    this.mSensorType = i;
    this.mGestureAvailabilityDispatcher = gestureAvailabilityDispatcher;
    this.mPendingOperations = new ArrayDeque();
    this.mBiometricService = iBiometricService;
    this.mCrashStates = new ArrayDeque();
    this.mRecentOperationsLimit = i2;
    this.mRecentOperations = new ArrayList();
  }

  public BiometricScheduler(
      String str, int i, GestureAvailabilityDispatcher gestureAvailabilityDispatcher) {
    this(
        str,
        new Handler(Looper.getMainLooper()),
        i,
        gestureAvailabilityDispatcher,
        IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")),
        50);
  }

  public ClientMonitorCallback getInternalCallback() {
    return this.mInternalCallback;
  }

  public String getTag() {
    return "BiometricScheduler/" + this.mBiometricTag;
  }

  public void startNextOperationIfIdle() {
    if (this.mCurrentOperation != null) {
      Slog.v(getTag(), "Not idle, current operation: " + this.mCurrentOperation);
      return;
    }
    if (this.mPendingOperations.isEmpty()) {
      Slog.d(getTag(), "No operations, returning to idle");
      return;
    }
    this.mCurrentOperation = (BiometricSchedulerOperation) this.mPendingOperations.poll();
    Slog.d(getTag(), "[Polled] " + this.mCurrentOperation);
    if (this.mCurrentOperation.isMarkedCanceling()) {
      Slog.d(getTag(), "[Now Cancelling] " + this.mCurrentOperation);
      this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
      return;
    }
    if (this.mGestureAvailabilityDispatcher != null
        && this.mCurrentOperation.isAcquisitionOperation()) {
      this.mGestureAvailabilityDispatcher.markSensorActive(
          this.mCurrentOperation.getSensorId(), true);
    }
    int isReadyToStart = this.mCurrentOperation.isReadyToStart(this.mInternalCallback);
    if (isReadyToStart == 0) {
      if (this.mCurrentOperation.start(this.mInternalCallback)) {
        return;
      }
      int size = this.mPendingOperations.size();
      BiometricSchedulerOperation biometricSchedulerOperation =
          (BiometricSchedulerOperation) this.mPendingOperations.peekLast();
      Slog.e(
          getTag(),
          "[Unable To Start] "
              + this.mCurrentOperation
              + ". Last pending operation: "
              + biometricSchedulerOperation);
      for (int i = 0; i < size; i++) {
        BiometricSchedulerOperation biometricSchedulerOperation2 =
            (BiometricSchedulerOperation) this.mPendingOperations.pollFirst();
        if (biometricSchedulerOperation2 != null) {
          Slog.w(getTag(), "[Aborting Operation] " + biometricSchedulerOperation2);
          biometricSchedulerOperation2.abort();
        } else {
          Slog.e(getTag(), "Null operation, index: " + i + ", expected length: " + size);
        }
      }
      this.mCurrentOperation.destroy();
      this.mCurrentOperation = null;
      startNextOperationIfIdle();
      return;
    }
    try {
      this.mBiometricService.onReadyForAuthentication(
          this.mCurrentOperation.getClientMonitor().getRequestId(), isReadyToStart);
    } catch (RemoteException e) {
      Slog.e(getTag(), "Remote exception when contacting BiometricService", e);
    }
    Slog.d(getTag(), "Waiting for cookie before starting: " + this.mCurrentOperation);
  }

  /* renamed from: startPreparedClient */
  public void lambda$startPreparedClient$0(int i) {
    BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
    if (biometricSchedulerOperation == null) {
      Slog.e(getTag(), "Current operation is null");
      return;
    }
    if (biometricSchedulerOperation.startWithCookie(this.mInternalCallback, i)) {
      Slog.d(getTag(), "[Started] Prepared client: " + this.mCurrentOperation);
      return;
    }
    Slog.e(getTag(), "[Unable To Start] Prepared client: " + this.mCurrentOperation);
    this.mCurrentOperation.destroy();
    this.mCurrentOperation = null;
    startNextOperationIfIdle();
  }

  public void scheduleClientMonitor(BaseClientMonitor baseClientMonitor) {
    lambda$scheduleClientMonitor$1(baseClientMonitor, null);
  }

  /* renamed from: scheduleClientMonitor */
  public void lambda$scheduleClientMonitor$1(
      BaseClientMonitor baseClientMonitor, ClientMonitorCallback clientMonitorCallback) {
    BiometricSchedulerOperation biometricSchedulerOperation;
    if (baseClientMonitor.interruptsPrecedingClients()) {
      for (BiometricSchedulerOperation biometricSchedulerOperation2 : this.mPendingOperations) {
        if (biometricSchedulerOperation2.markCanceling()) {
          Slog.d(
              getTag(),
              "New client, marking pending op as canceling: " + biometricSchedulerOperation2);
        }
      }
    }
    this.mPendingOperations.add(
        new BiometricSchedulerOperation(baseClientMonitor, clientMonitorCallback));
    Slog.d(
        getTag(),
        "[Added] " + baseClientMonitor + ", new queue size: " + this.mPendingOperations.size());
    if (baseClientMonitor.interruptsPrecedingClients()
        && (biometricSchedulerOperation = this.mCurrentOperation) != null
        && biometricSchedulerOperation.isInterruptable()
        && (this.mCurrentOperation.isStarted() || this.mCurrentOperation.isWaitingForCookie())) {
      Slog.d(getTag(), "[Cancelling Interruptable]: " + this.mCurrentOperation);
      this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
      return;
    }
    startNextOperationIfIdle();
  }

  /* renamed from: cancelEnrollment */
  public void lambda$cancelEnrollment$2(IBinder iBinder, long j) {
    Slog.d(getTag(), "cancelEnrollment, requestId: " + j);
    BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
    if (biometricSchedulerOperation != null
        && canCancelEnrollOperation(biometricSchedulerOperation, iBinder, j)) {
      Slog.d(getTag(), "Cancelling enrollment op: " + this.mCurrentOperation);
      this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
      return;
    }
    for (BiometricSchedulerOperation biometricSchedulerOperation2 : this.mPendingOperations) {
      if (canCancelEnrollOperation(biometricSchedulerOperation2, iBinder, j)) {
        Slog.d(getTag(), "Cancelling pending enrollment op: " + biometricSchedulerOperation2);
        biometricSchedulerOperation2.markCanceling();
      }
    }
  }

  /* renamed from: cancelAuthenticationOrDetection */
  public void lambda$cancelAuthenticationOrDetection$3(IBinder iBinder, long j) {
    Slog.d(getTag(), "cancelAuthenticationOrDetection, requestId: " + j);
    BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
    if (biometricSchedulerOperation != null
        && canCancelAuthOperation(biometricSchedulerOperation, iBinder, j)) {
      Slog.d(getTag(), "Cancelling auth/detect op: " + this.mCurrentOperation);
      this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
      return;
    }
    for (BiometricSchedulerOperation biometricSchedulerOperation2 : this.mPendingOperations) {
      if (canCancelAuthOperation(biometricSchedulerOperation2, iBinder, j)) {
        Slog.d(getTag(), "Cancelling pending auth/detect op: " + biometricSchedulerOperation2);
        biometricSchedulerOperation2.markCanceling();
      }
    }
  }

  public static boolean canCancelEnrollOperation(
      BiometricSchedulerOperation biometricSchedulerOperation, IBinder iBinder, long j) {
    return biometricSchedulerOperation.isEnrollOperation()
        && biometricSchedulerOperation.isMatchingToken(iBinder)
        && biometricSchedulerOperation.isMatchingRequestId(j);
  }

  public static boolean canCancelAuthOperation(
      BiometricSchedulerOperation biometricSchedulerOperation, IBinder iBinder, long j) {
    return biometricSchedulerOperation.isAuthenticationOrDetectionOperation()
        && biometricSchedulerOperation.isMatchingToken(iBinder)
        && biometricSchedulerOperation.isMatchingRequestId(j);
  }

  public BaseClientMonitor getCurrentClient() {
    BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
    if (biometricSchedulerOperation != null) {
      return biometricSchedulerOperation.getClientMonitor();
    }
    return null;
  }

  public void getCurrentClientIfMatches(final long j, final Consumer consumer) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.BiometricScheduler$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            BiometricScheduler.this.lambda$getCurrentClientIfMatches$0(j, consumer);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$getCurrentClientIfMatches$0(long j, Consumer consumer) {
    BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
    if (biometricSchedulerOperation != null && biometricSchedulerOperation.isMatchingRequestId(j)) {
      consumer.accept(this.mCurrentOperation.getClientMonitor());
    } else {
      consumer.accept(null);
    }
  }

  public void recordCrashState() {
    if (this.mCrashStates.size() >= 10) {
      this.mCrashStates.removeFirst();
    }
    String format =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
            .format(new Date(System.currentTimeMillis()));
    ArrayList arrayList = new ArrayList();
    Iterator it = this.mPendingOperations.iterator();
    while (it.hasNext()) {
      arrayList.add(((BiometricSchedulerOperation) it.next()).toString());
    }
    BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
    CrashState crashState =
        new CrashState(
            format,
            biometricSchedulerOperation != null ? biometricSchedulerOperation.toString() : null,
            arrayList);
    this.mCrashStates.add(crashState);
    Slog.e(getTag(), "Recorded crash state: " + crashState.toString());
  }

  public void dump(PrintWriter printWriter) {
    printWriter.println("Dump of BiometricScheduler " + getTag());
    printWriter.println("Type: " + this.mSensorType);
    printWriter.println("Current operation: " + this.mCurrentOperation);
    printWriter.println("Pending operations: " + this.mPendingOperations.size());
    Iterator it = this.mPendingOperations.iterator();
    while (it.hasNext()) {
      printWriter.println("Pending operation: " + ((BiometricSchedulerOperation) it.next()));
    }
    Iterator it2 = this.mCrashStates.iterator();
    while (it2.hasNext()) {
      printWriter.println("Crash State " + ((CrashState) it2.next()));
    }
  }

  public byte[] dumpProtoState(boolean z) {
    ProtoOutputStream protoOutputStream = new ProtoOutputStream();
    BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
    protoOutputStream.write(
        1159641169921L,
        biometricSchedulerOperation != null ? biometricSchedulerOperation.getProtoEnum() : 0);
    protoOutputStream.write(1120986464258L, this.mTotalOperationsHandled);
    if (!this.mRecentOperations.isEmpty()) {
      for (int i = 0; i < this.mRecentOperations.size(); i++) {
        protoOutputStream.write(
            2259152797699L, ((Integer) this.mRecentOperations.get(i)).intValue());
      }
    } else {
      protoOutputStream.write(2259152797699L, 0);
    }
    protoOutputStream.flush();
    if (z) {
      this.mRecentOperations.clear();
    }
    return protoOutputStream.getBytes();
  }

  public void reset() {
    Slog.d(getTag(), "Resetting scheduler");
    this.mPendingOperations.clear();
    this.mCurrentOperation = null;
  }

  public final void clearScheduler() {
    if (this.mCurrentOperation == null) {
      return;
    }
    for (BiometricSchedulerOperation biometricSchedulerOperation : this.mPendingOperations) {
      Slog.d(
          getTag(),
          "[Watchdog cancelling pending] " + biometricSchedulerOperation.getClientMonitor());
      biometricSchedulerOperation.markCancelingForWatchdog();
    }
    Slog.d(getTag(), "[Watchdog cancelling current] " + this.mCurrentOperation.getClientMonitor());
    this.mCurrentOperation.cancel(this.mHandler, getInternalCallback());
  }

  public void startWatchdog() {
    final BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
    if (biometricSchedulerOperation == null) {
      return;
    }
    this.mHandler.postDelayed(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.BiometricScheduler$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            BiometricScheduler.this.lambda$startWatchdog$1(biometricSchedulerOperation);
          }
        },
        10000L);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startWatchdog$1(
      BiometricSchedulerOperation biometricSchedulerOperation) {
    if (biometricSchedulerOperation == this.mCurrentOperation) {
      Counter.logIncrement("biometric.value_scheduler_watchdog_triggered_count");
      clearScheduler();
    }
  }
}
