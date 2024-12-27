package com.android.server.rotationresolver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.os.SystemClock;
import android.rotationresolver.RotationResolverInternal;
import android.service.rotationresolver.IRotationResolverCallback;
import android.service.rotationresolver.IRotationResolverService;
import android.service.rotationresolver.RotationResolutionRequest;
import android.util.Slog;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.rotationresolver.RemoteRotationResolverService;
import com.android.server.rotationresolver.RotationResolverManagerPerUserService;
import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
class RemoteRotationResolverService extends ServiceConnector.Impl {
    public static final /* synthetic */ int $r8$clinit = 0;
    private final long mIdleUnbindTimeoutMs;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class RotationRequest {
        public final RotationResolverInternal.RotationResolverCallbackInternal mCallbackInternal;
        public ICancellationSignal mCancellation;
        public final CancellationSignal mCancellationSignalInternal;
        public boolean mIsDispatched;
        public boolean mIsFulfilled;
        public final Object mLock;
        final RotationResolutionRequest mRemoteRequest;
        public final RotationResolverCallback mIRotationResolverCallback = new RotationResolverCallback(this);
        public final long mRequestStartTimeMillis = SystemClock.elapsedRealtime();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class RotationResolverCallback extends IRotationResolverCallback.Stub {
            public final WeakReference mRequestWeakReference;

            public RotationResolverCallback(RotationRequest rotationRequest) {
                this.mRequestWeakReference = new WeakReference(rotationRequest);
            }

            public final void onCancellable(ICancellationSignal iCancellationSignal) {
                RotationRequest rotationRequest = (RotationRequest) this.mRequestWeakReference.get();
                synchronized (rotationRequest.mLock) {
                    rotationRequest.mCancellation = iCancellationSignal;
                    if (rotationRequest.mCancellationSignalInternal.isCanceled()) {
                        try {
                            iCancellationSignal.cancel();
                        } catch (RemoteException unused) {
                            int i = RemoteRotationResolverService.$r8$clinit;
                            Slog.w("RemoteRotationResolverService", "Failed to cancel the remote request.");
                        }
                    }
                }
            }

            public final void onFailure(int i) {
                RotationRequest rotationRequest = (RotationRequest) this.mRequestWeakReference.get();
                synchronized (rotationRequest.mLock) {
                    try {
                        if (rotationRequest.mIsFulfilled) {
                            int i2 = RemoteRotationResolverService.$r8$clinit;
                            Slog.w("RemoteRotationResolverService", "Callback received after the rotation request is fulfilled.");
                            return;
                        }
                        rotationRequest.mIsFulfilled = true;
                        rotationRequest.mCallbackInternal.onFailure(i);
                        long elapsedRealtime = SystemClock.elapsedRealtime() - rotationRequest.mRequestStartTimeMillis;
                        FrameworkStatsLog.write(328, RotationResolverManagerService.surfaceRotationToProto(rotationRequest.mRemoteRequest.getCurrentRotation()), RotationResolverManagerService.surfaceRotationToProto(rotationRequest.mRemoteRequest.getProposedRotation()), (i == 0 || i == 1 || i == 2) ? 0 : i != 4 ? 8 : 7, elapsedRealtime);
                        int i3 = RemoteRotationResolverService.$r8$clinit;
                        Slog.d("RemoteRotationResolverService", "onFailure:" + i);
                        Slog.d("RemoteRotationResolverService", "timeToCalculate:" + elapsedRealtime);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void onSuccess(int i) {
                RotationRequest rotationRequest = (RotationRequest) this.mRequestWeakReference.get();
                synchronized (rotationRequest.mLock) {
                    try {
                        if (rotationRequest.mIsFulfilled) {
                            int i2 = RemoteRotationResolverService.$r8$clinit;
                            Slog.w("RemoteRotationResolverService", "Callback received after the rotation request is fulfilled.");
                            return;
                        }
                        rotationRequest.mIsFulfilled = true;
                        rotationRequest.mCallbackInternal.onSuccess(i);
                        long elapsedRealtime = SystemClock.elapsedRealtime() - rotationRequest.mRequestStartTimeMillis;
                        int proposedRotation = rotationRequest.mRemoteRequest.getProposedRotation();
                        FrameworkStatsLog.write(328, RotationResolverManagerService.surfaceRotationToProto(rotationRequest.mRemoteRequest.getCurrentRotation()), RotationResolverManagerService.surfaceRotationToProto(proposedRotation), RotationResolverManagerService.surfaceRotationToProto(i), elapsedRealtime);
                        int i3 = RemoteRotationResolverService.$r8$clinit;
                        Slog.d("RemoteRotationResolverService", "onSuccess:" + i);
                        Slog.d("RemoteRotationResolverService", "timeToCalculate:" + elapsedRealtime);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public RotationRequest(RotationResolverManagerPerUserService.AnonymousClass1 anonymousClass1, RotationResolutionRequest rotationResolutionRequest, CancellationSignal cancellationSignal, Object obj) {
            this.mCallbackInternal = anonymousClass1;
            this.mRemoteRequest = rotationResolutionRequest;
            this.mCancellationSignalInternal = cancellationSignal;
            this.mLock = obj;
        }

        public final void cancelInternal() {
            Handler.getMain().post(new RemoteRotationResolverService$$ExternalSyntheticLambda1(this, 1));
            this.mCallbackInternal.onFailure(0);
        }
    }

    public RemoteRotationResolverService(Context context, ComponentName componentName, int i) {
        super(context, new Intent("android.service.rotationresolver.RotationResolverService").setComponent(componentName), 67112960, i, new RemoteRotationResolverService$$ExternalSyntheticLambda2());
        this.mIdleUnbindTimeoutMs = 60000L;
        connect();
    }

    public final long getAutoDisconnectTimeoutMs() {
        return -1L;
    }

    public final void resolveRotation(final RotationRequest rotationRequest) {
        final RotationResolutionRequest rotationResolutionRequest = rotationRequest.mRemoteRequest;
        post(new ServiceConnector.VoidJob() { // from class: com.android.server.rotationresolver.RemoteRotationResolverService$$ExternalSyntheticLambda0
            public final void runNoResult(Object obj) {
                RemoteRotationResolverService.RotationRequest rotationRequest2 = RemoteRotationResolverService.RotationRequest.this;
                RotationResolutionRequest rotationResolutionRequest2 = rotationResolutionRequest;
                int i = RemoteRotationResolverService.$r8$clinit;
                ((IRotationResolverService) obj).resolveRotation(rotationRequest2.mIRotationResolverCallback, rotationResolutionRequest2);
            }
        });
        getJobHandler().postDelayed(new RemoteRotationResolverService$$ExternalSyntheticLambda1(rotationRequest, 0), rotationRequest.mRemoteRequest.getTimeoutMillis());
    }
}
