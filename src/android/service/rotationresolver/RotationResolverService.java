package android.service.rotationresolver;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.service.rotationresolver.IRotationResolverService;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public abstract class RotationResolverService extends Service {
    public static final int ROTATION_RESULT_FAILURE_CANCELLED = 0;
    public static final int ROTATION_RESULT_FAILURE_NOT_SUPPORTED = 4;
    public static final int ROTATION_RESULT_FAILURE_PREEMPTED = 2;
    public static final int ROTATION_RESULT_FAILURE_TIMED_OUT = 1;
    public static final int ROTATION_RESULT_FAILURE_UNKNOWN = 3;
    public static final String SERVICE_INTERFACE = "android.service.rotationresolver.RotationResolverService";
    private CancellationSignal mCancellationSignal;
    private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper(), null, true);
    private RotationResolverCallbackWrapper mPendingCallback;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FailureCodes {
    }

    public interface RotationResolverCallback {
        void onFailure(int i);

        void onSuccess(int i);
    }

    public abstract void onResolveRotation(RotationResolutionRequest rotationResolutionRequest, CancellationSignal cancellationSignal, RotationResolverCallback rotationResolverCallback);

    /* renamed from: android.service.rotationresolver.RotationResolverService$1, reason: invalid class name */
    class AnonymousClass1 extends IRotationResolverService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.rotationresolver.IRotationResolverService
        public void resolveRotation(IRotationResolverCallback iRotationResolverCallback, RotationResolutionRequest rotationResolutionRequest) throws RemoteException {
            Objects.requireNonNull(iRotationResolverCallback);
            Objects.requireNonNull(rotationResolutionRequest);
            ICancellationSignal createTransport = CancellationSignal.createTransport();
            iRotationResolverCallback.onCancellable(createTransport);
            RotationResolverService.this.mMainThreadHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.rotationresolver.RotationResolverService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((RotationResolverService) obj).resolveRotation((IRotationResolverCallback) obj2, (RotationResolutionRequest) obj3, (ICancellationSignal) obj4);
                }
            }, RotationResolverService.this, iRotationResolverCallback, rotationResolutionRequest, createTransport));
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new AnonymousClass1();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resolveRotation(IRotationResolverCallback iRotationResolverCallback, RotationResolutionRequest rotationResolutionRequest, ICancellationSignal iCancellationSignal) {
        CancellationSignal cancellationSignal;
        if (this.mPendingCallback != null && (((cancellationSignal = this.mCancellationSignal) == null || !cancellationSignal.isCanceled()) && SystemClock.uptimeMillis() < this.mPendingCallback.mExpirationTime)) {
            reportFailures(iRotationResolverCallback, 2);
            return;
        }
        this.mPendingCallback = new RotationResolverCallbackWrapper(iRotationResolverCallback, this, rotationResolutionRequest.getTimeoutMillis() + SystemClock.uptimeMillis());
        CancellationSignal fromTransport = CancellationSignal.fromTransport(iCancellationSignal);
        this.mCancellationSignal = fromTransport;
        onResolveRotation(rotationResolutionRequest, fromTransport, this.mPendingCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRotationResult(IRotationResolverCallback iRotationResolverCallback, int i) {
        RotationResolverCallbackWrapper rotationResolverCallbackWrapper = this.mPendingCallback;
        if (rotationResolverCallbackWrapper == null || rotationResolverCallbackWrapper.mCallback != iRotationResolverCallback) {
            return;
        }
        this.mPendingCallback = null;
        try {
            iRotationResolverCallback.onSuccess(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendFailureResult(IRotationResolverCallback iRotationResolverCallback, int i) {
        RotationResolverCallbackWrapper rotationResolverCallbackWrapper = this.mPendingCallback;
        if (rotationResolverCallbackWrapper == null || iRotationResolverCallback != rotationResolverCallbackWrapper.mCallback) {
            return;
        }
        reportFailures(iRotationResolverCallback, i);
        this.mPendingCallback = null;
    }

    private void reportFailures(IRotationResolverCallback iRotationResolverCallback, int i) {
        try {
            iRotationResolverCallback.onFailure(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static final class RotationResolverCallbackWrapper implements RotationResolverCallback {
        private final IRotationResolverCallback mCallback;
        private final long mExpirationTime;
        private final Handler mHandler;
        private final RotationResolverService mService;

        private RotationResolverCallbackWrapper(IRotationResolverCallback iRotationResolverCallback, RotationResolverService rotationResolverService, long j) {
            this.mCallback = iRotationResolverCallback;
            this.mService = rotationResolverService;
            Handler handler = rotationResolverService.mMainThreadHandler;
            this.mHandler = handler;
            this.mExpirationTime = j;
            Objects.requireNonNull(handler);
        }

        @Override // android.service.rotationresolver.RotationResolverService.RotationResolverCallback
        public void onSuccess(int i) {
            this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.rotationresolver.RotationResolverService$RotationResolverCallbackWrapper$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((RotationResolverService) obj).sendRotationResult((IRotationResolverCallback) obj2, ((Integer) obj3).intValue());
                }
            }, this.mService, this.mCallback, Integer.valueOf(i)));
        }

        @Override // android.service.rotationresolver.RotationResolverService.RotationResolverCallback
        public void onFailure(int i) {
            this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.rotationresolver.RotationResolverService$RotationResolverCallbackWrapper$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((RotationResolverService) obj).sendFailureResult((IRotationResolverCallback) obj2, ((Integer) obj3).intValue());
                }
            }, this.mService, this.mCallback, Integer.valueOf(i)));
        }
    }
}
