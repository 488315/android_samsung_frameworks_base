package com.android.server.biometrics.sensors;

import android.hardware.biometrics.IBiometricService;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.modules.expresslog.Counter;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
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
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BiometricScheduler {
    public final IBiometricService mBiometricService;
    public final ArrayDeque mCrashStates;
    BiometricSchedulerOperation mCurrentOperation;
    public final Supplier mCurrentUserRetriever;
    public final GestureAvailabilityDispatcher mGestureAvailabilityDispatcher;
    public final Handler mHandler;
    public final AnonymousClass1 mInternalCallback;
    final Deque mPendingOperations;
    public final List mRecentOperations;
    public final int mRecentOperationsLimit;
    public final int mSensorType;
    public StopUserClient mStopUserClient;
    public int mTotalOperationsHandled;
    public final UserSwitchProvider mUserSwitchProvider;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.BiometricScheduler$1, reason: invalid class name */
    public final class AnonymousClass1 implements ClientMonitorCallback {
        public AnonymousClass1() {
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            BiometricScheduler.this.mHandler.post(new BiometricScheduler$1$$ExternalSyntheticLambda0(this, baseClientMonitor, z, 0));
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
            Slog.d("BiometricScheduler", "[Started] " + baseClientMonitor);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CrashState {
        public final String currentOperation;
        public final List pendingOperations;
        public final String timestamp;

        public CrashState(String str, String str2, List list) {
            this.timestamp = str;
            this.currentOperation = str2;
            this.pendingOperations = list;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.timestamp);
            sb.append(": Current Operation: {");
            sb.append(this.currentOperation);
            sb.append("}, Pending Operations(");
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserSwitchClientCallback implements ClientMonitorCallback {
        public final BaseClientMonitor mOwner;

        public UserSwitchClientCallback(HalClientMonitor halClientMonitor) {
            this.mOwner = halClientMonitor;
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            BiometricScheduler.this.mHandler.post(new BiometricScheduler$1$$ExternalSyntheticLambda0(this, baseClientMonitor, z, 1));
        }
    }

    public BiometricScheduler(Handler handler, int i, GestureAvailabilityDispatcher gestureAvailabilityDispatcher, IBiometricService iBiometricService, int i2) {
        this.mInternalCallback = new AnonymousClass1();
        this.mHandler = handler;
        this.mSensorType = i;
        this.mGestureAvailabilityDispatcher = gestureAvailabilityDispatcher;
        this.mPendingOperations = new ArrayDeque();
        this.mBiometricService = iBiometricService;
        this.mCrashStates = new ArrayDeque();
        this.mRecentOperationsLimit = i2;
        this.mRecentOperations = new ArrayList();
    }

    public BiometricScheduler(Handler handler, int i, GestureAvailabilityDispatcher gestureAvailabilityDispatcher, IBiometricService iBiometricService, int i2, Supplier supplier, UserSwitchProvider userSwitchProvider) {
        this.mInternalCallback = new AnonymousClass1();
        this.mHandler = handler;
        this.mSensorType = i;
        this.mGestureAvailabilityDispatcher = gestureAvailabilityDispatcher;
        this.mPendingOperations = new ArrayDeque();
        this.mBiometricService = iBiometricService;
        this.mCrashStates = new ArrayDeque();
        this.mRecentOperationsLimit = i2;
        this.mRecentOperations = new ArrayList();
        this.mCurrentUserRetriever = supplier;
        this.mUserSwitchProvider = userSwitchProvider;
    }

    public BiometricScheduler(Handler handler, int i, GestureAvailabilityDispatcher gestureAvailabilityDispatcher, Supplier supplier, UserSwitchProvider userSwitchProvider) {
        this(handler, i, gestureAvailabilityDispatcher, IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")), 50, supplier, userSwitchProvider);
    }

    public final void cancelAuthenticationOrDetection(IBinder iBinder, long j) {
        Slog.d("BiometricScheduler", "cancelAuthenticationOrDetection, requestId: " + j);
        BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
        if (biometricSchedulerOperation != null) {
            BaseClientMonitor baseClientMonitor = biometricSchedulerOperation.mClientMonitor;
            boolean z = baseClientMonitor instanceof DetectionConsumer;
            if (((baseClientMonitor instanceof AuthenticationConsumer) || z) && baseClientMonitor.mToken == iBinder && biometricSchedulerOperation.isMatchingRequestId(j)) {
                Slog.d("BiometricScheduler", "Cancelling auth/detect op: " + this.mCurrentOperation);
                this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
                return;
            }
        }
        for (BiometricSchedulerOperation biometricSchedulerOperation2 : this.mPendingOperations) {
            BaseClientMonitor baseClientMonitor2 = biometricSchedulerOperation2.mClientMonitor;
            boolean z2 = baseClientMonitor2 instanceof DetectionConsumer;
            if ((baseClientMonitor2 instanceof AuthenticationConsumer) || z2) {
                if (baseClientMonitor2.mToken == iBinder && biometricSchedulerOperation2.isMatchingRequestId(j)) {
                    Slog.d("BiometricScheduler", "Cancelling pending auth/detect op: " + biometricSchedulerOperation2);
                    biometricSchedulerOperation2.markCanceling();
                }
            }
        }
    }

    public final void cancelEnrollment(IBinder iBinder, long j) {
        Slog.d("BiometricScheduler", "cancelEnrollment, requestId: " + j);
        BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
        if (biometricSchedulerOperation != null) {
            BaseClientMonitor baseClientMonitor = biometricSchedulerOperation.mClientMonitor;
            if ((baseClientMonitor instanceof EnrollClient) && baseClientMonitor.mToken == iBinder && biometricSchedulerOperation.isMatchingRequestId(j)) {
                Slog.d("BiometricScheduler", "Cancelling enrollment op: " + this.mCurrentOperation);
                this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
                return;
            }
        }
        for (BiometricSchedulerOperation biometricSchedulerOperation2 : this.mPendingOperations) {
            BaseClientMonitor baseClientMonitor2 = biometricSchedulerOperation2.mClientMonitor;
            if ((baseClientMonitor2 instanceof EnrollClient) && baseClientMonitor2.mToken == iBinder && biometricSchedulerOperation2.isMatchingRequestId(j)) {
                Slog.d("BiometricScheduler", "Cancelling pending enrollment op: " + biometricSchedulerOperation2);
                biometricSchedulerOperation2.markCanceling();
            }
        }
    }

    public final void checkCurrentUserAndStartNextOperation() {
        int i;
        boolean doStart;
        if (this.mCurrentOperation != null) {
            Slog.v("BiometricScheduler", "Not idle, current operation: " + this.mCurrentOperation);
            return;
        }
        if (this.mPendingOperations.isEmpty()) {
            Slog.d("BiometricScheduler", "No operations, returning to idle");
            return;
        }
        int intValue = ((Integer) this.mCurrentUserRetriever.get()).intValue();
        int i2 = ((BiometricSchedulerOperation) this.mPendingOperations.getFirst()).mClientMonitor.mTargetUserId;
        if (i2 != intValue && !(((BiometricSchedulerOperation) this.mPendingOperations.getFirst()).mClientMonitor instanceof StartUserClient)) {
            UserSwitchProvider userSwitchProvider = this.mUserSwitchProvider;
            if (intValue == -10000 && userSwitchProvider != null) {
                StartUserClient startUserClient = userSwitchProvider.getStartUserClient(i2);
                UserSwitchClientCallback userSwitchClientCallback = new UserSwitchClientCallback(startUserClient);
                Slog.d("BiometricScheduler", "[Starting User] " + startUserClient);
                this.mCurrentOperation = new BiometricSchedulerOperation(2, startUserClient, userSwitchClientCallback);
                startUserClient.start(userSwitchClientCallback);
                return;
            }
            if (userSwitchProvider == null) {
                Slog.e("BiometricScheduler", "Cannot start next operation.");
                return;
            }
            if (this.mStopUserClient != null) {
                Slog.d("BiometricScheduler", "[Waiting for StopUser] " + this.mStopUserClient);
                return;
            }
            StopUserClient stopUserClient = userSwitchProvider.getStopUserClient(intValue);
            this.mStopUserClient = stopUserClient;
            UserSwitchClientCallback userSwitchClientCallback2 = new UserSwitchClientCallback(stopUserClient);
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(intValue, i2, "[Stopping User] current: ", ", next: ", ". ");
            m.append(this.mStopUserClient);
            Slog.d("BiometricScheduler", m.toString());
            this.mCurrentOperation = new BiometricSchedulerOperation(2, this.mStopUserClient, userSwitchClientCallback2);
            this.mStopUserClient.start(userSwitchClientCallback2);
            return;
        }
        if (this.mCurrentOperation != null) {
            Slog.v("BiometricScheduler", "Not idle, current operation: " + this.mCurrentOperation);
            return;
        }
        if (this.mPendingOperations.isEmpty()) {
            Slog.d("BiometricScheduler", "No operations, returning to idle");
            return;
        }
        this.mCurrentOperation = (BiometricSchedulerOperation) this.mPendingOperations.poll();
        Slog.d("BiometricScheduler", "[Polled] " + this.mCurrentOperation);
        BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
        int i3 = biometricSchedulerOperation.mState;
        AnonymousClass1 anonymousClass1 = this.mInternalCallback;
        Handler handler = this.mHandler;
        if (i3 == 1) {
            Slog.d("BiometricScheduler", "[Now Cancelling] " + this.mCurrentOperation);
            this.mCurrentOperation.cancel(handler, anonymousClass1);
            return;
        }
        BaseClientMonitor baseClientMonitor = biometricSchedulerOperation.mClientMonitor;
        boolean z = baseClientMonitor instanceof AcquisitionClient;
        if (z && ((AcquisitionClient) baseClientMonitor).mAlreadyCancelled) {
            biometricSchedulerOperation.cancel(handler, anonymousClass1);
            return;
        }
        GestureAvailabilityDispatcher gestureAvailabilityDispatcher = this.mGestureAvailabilityDispatcher;
        if (gestureAvailabilityDispatcher != null && z) {
            gestureAvailabilityDispatcher.markSensorActive(baseClientMonitor.mSensorId, true);
        }
        BiometricSchedulerOperation biometricSchedulerOperation2 = this.mCurrentOperation;
        int i4 = biometricSchedulerOperation2.mState;
        if (i4 == 4 || i4 == 0) {
            BaseClientMonitor baseClientMonitor2 = biometricSchedulerOperation2.mClientMonitor;
            i = baseClientMonitor2.mCookie;
            if (i != 0) {
                biometricSchedulerOperation2.mState = 4;
                baseClientMonitor2.mCallback = biometricSchedulerOperation2.getWrappedCallback(anonymousClass1);
            }
        } else {
            i = 0;
        }
        if (i != 0) {
            try {
                this.mBiometricService.onReadyForAuthentication(this.mCurrentOperation.mClientMonitor.mRequestId, i);
            } catch (RemoteException e) {
                Slog.e("BiometricScheduler", "Remote exception when contacting BiometricService", e);
            }
            Slog.d("BiometricScheduler", "Waiting for cookie before starting: " + this.mCurrentOperation);
            return;
        }
        BiometricSchedulerOperation biometricSchedulerOperation3 = this.mCurrentOperation;
        if (biometricSchedulerOperation3.errorWhenNoneOf("start", 0, 4, 1)) {
            doStart = false;
        } else {
            if (biometricSchedulerOperation3.mClientMonitor.mCookie != 0) {
                if (biometricSchedulerOperation3.mIsDebuggable.getAsBoolean()) {
                    throw new IllegalStateException("operation requires cookie");
                }
                Slog.e("BiometricSchedulerOperation", "operation requires cookie");
            }
            doStart = biometricSchedulerOperation3.doStart(anonymousClass1);
        }
        if (doStart) {
            return;
        }
        int size = this.mPendingOperations.size();
        Slog.e("BiometricScheduler", "[Unable To Start] " + this.mCurrentOperation + ". Last pending operation: " + ((BiometricSchedulerOperation) this.mPendingOperations.peekLast()));
        for (int i5 = 0; i5 < size; i5++) {
            BiometricSchedulerOperation biometricSchedulerOperation4 = (BiometricSchedulerOperation) this.mPendingOperations.pollFirst();
            if (biometricSchedulerOperation4 != null) {
                Slog.w("BiometricScheduler", "[Aborting Operation] " + biometricSchedulerOperation4);
                if (!biometricSchedulerOperation4.errorWhenNoneOf("abort", 0, 4, 1)) {
                    BaseClientMonitor baseClientMonitor3 = biometricSchedulerOperation4.mClientMonitor;
                    if (baseClientMonitor3 instanceof HalClientMonitor) {
                        ((HalClientMonitor) baseClientMonitor3).unableToStart();
                    }
                    biometricSchedulerOperation4.getWrappedCallback(null).onClientFinished(baseClientMonitor3, false);
                    Slog.v("BiometricSchedulerOperation", "Aborted: " + biometricSchedulerOperation4);
                }
            } else {
                Slog.e("BiometricScheduler", "Null operation, index: " + i5 + ", expected length: " + size);
            }
        }
        BaseClientMonitor baseClientMonitor4 = this.mCurrentOperation.mClientMonitor;
        if (baseClientMonitor4 != null) {
            baseClientMonitor4.destroy();
        }
        this.mCurrentOperation = null;
        checkCurrentUserAndStartNextOperation();
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "Dump of BiometricScheduler BiometricScheduler", "Type: "), this.mSensorType, printWriter, "Current operation: ");
        m.append(this.mCurrentOperation);
        printWriter.println(m.toString());
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

    public final byte[] dumpProtoState(boolean z) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
        protoOutputStream.write(1159641169921L, biometricSchedulerOperation != null ? biometricSchedulerOperation.mClientMonitor.getProtoEnum() : 0);
        protoOutputStream.write(1120986464258L, this.mTotalOperationsHandled);
        if (((ArrayList) this.mRecentOperations).isEmpty()) {
            protoOutputStream.write(2259152797699L, 0);
        } else {
            for (int i = 0; i < ((ArrayList) this.mRecentOperations).size(); i++) {
                protoOutputStream.write(2259152797699L, ((Integer) ((ArrayList) this.mRecentOperations).get(i)).intValue());
            }
        }
        protoOutputStream.flush();
        if (z) {
            ((ArrayList) this.mRecentOperations).clear();
        }
        return protoOutputStream.getBytes();
    }

    public final BaseClientMonitor getCurrentClient() {
        BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
        if (biometricSchedulerOperation != null) {
            return biometricSchedulerOperation.mClientMonitor;
        }
        return null;
    }

    public ClientMonitorCallback getInternalCallback() {
        return this.mInternalCallback;
    }

    public final void recordCrashState() {
        if (this.mCrashStates.size() >= 10) {
            this.mCrashStates.removeFirst();
        }
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).format(new Date(System.currentTimeMillis()));
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mPendingOperations.iterator();
        while (it.hasNext()) {
            arrayList.add(((BiometricSchedulerOperation) it.next()).toString());
        }
        BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
        CrashState crashState = new CrashState(format, biometricSchedulerOperation != null ? biometricSchedulerOperation.toString() : null, arrayList);
        this.mCrashStates.add(crashState);
        Slog.e("BiometricScheduler", "Recorded crash state: " + crashState.toString());
    }

    public final void reset() {
        Slog.d("BiometricScheduler", "Resetting scheduler");
        this.mPendingOperations.clear();
        this.mCurrentOperation = null;
    }

    public final void scheduleClientMonitor(BaseClientMonitor baseClientMonitor, ClientMonitorCallback clientMonitorCallback) {
        BiometricSchedulerOperation biometricSchedulerOperation;
        int i;
        if (baseClientMonitor.interruptsPrecedingClients()) {
            for (BiometricSchedulerOperation biometricSchedulerOperation2 : this.mPendingOperations) {
                if (biometricSchedulerOperation2.markCanceling()) {
                    Slog.d("BiometricScheduler", "New client, marking pending op as canceling: " + biometricSchedulerOperation2);
                }
            }
        }
        this.mPendingOperations.add(new BiometricSchedulerOperation(0, baseClientMonitor, clientMonitorCallback));
        Slog.d("BiometricScheduler", "[Added] " + baseClientMonitor + ", new queue size: " + this.mPendingOperations.size());
        if (!baseClientMonitor.interruptsPrecedingClients() || (biometricSchedulerOperation = this.mCurrentOperation) == null || !biometricSchedulerOperation.mClientMonitor.isInterruptable() || ((i = this.mCurrentOperation.mState) != 2 && i != 4)) {
            checkCurrentUserAndStartNextOperation();
            return;
        }
        Slog.d("BiometricScheduler", "[Cancelling Interruptable]: " + this.mCurrentOperation);
        this.mCurrentOperation.cancel(this.mHandler, this.mInternalCallback);
    }

    public final void startPreparedClient(int i) {
        BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
        if (biometricSchedulerOperation == null) {
            Slog.e("BiometricScheduler", "Current operation is null");
            return;
        }
        boolean z = false;
        if (biometricSchedulerOperation.mClientMonitor.mCookie != i) {
            Slog.e("BiometricSchedulerOperation", "Mismatched cookie for operation: " + biometricSchedulerOperation + ", received: " + i);
        } else if (!biometricSchedulerOperation.errorWhenNoneOf("start", 0, 4, 1)) {
            z = biometricSchedulerOperation.doStart(this.mInternalCallback);
        }
        if (z) {
            Slog.d("BiometricScheduler", "[Started] Prepared client: " + this.mCurrentOperation);
            return;
        }
        Slog.e("BiometricScheduler", "[Unable To Start] Prepared client: " + this.mCurrentOperation);
        BaseClientMonitor baseClientMonitor = this.mCurrentOperation.mClientMonitor;
        if (baseClientMonitor != null) {
            baseClientMonitor.destroy();
        }
        this.mCurrentOperation = null;
        checkCurrentUserAndStartNextOperation();
    }

    public final void startWatchdog() {
        final BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
        if (biometricSchedulerOperation == null) {
            return;
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.biometrics.sensors.BiometricScheduler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BiometricScheduler biometricScheduler = BiometricScheduler.this;
                BiometricSchedulerOperation biometricSchedulerOperation2 = biometricSchedulerOperation;
                if (biometricSchedulerOperation2 != biometricScheduler.mCurrentOperation || biometricSchedulerOperation2.mState == 5) {
                    return;
                }
                Counter.logIncrement("biometric.value_scheduler_watchdog_triggered_count");
                if (biometricScheduler.mCurrentOperation == null) {
                    return;
                }
                for (BiometricSchedulerOperation biometricSchedulerOperation3 : biometricScheduler.mPendingOperations) {
                    Slog.d("BiometricScheduler", "[Watchdog cancelling pending] " + biometricSchedulerOperation3.mClientMonitor);
                    biometricSchedulerOperation3.markCancelingForWatchdog();
                }
                Slog.d("BiometricScheduler", "[Watchdog cancelling current] " + biometricScheduler.mCurrentOperation.mClientMonitor);
                biometricScheduler.mCurrentOperation.cancel(biometricScheduler.mHandler, biometricScheduler.getInternalCallback());
            }
        }, 10000L);
    }
}
