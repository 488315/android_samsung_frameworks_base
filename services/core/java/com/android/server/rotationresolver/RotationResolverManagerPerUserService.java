package com.android.server.rotationresolver;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.rotationresolver.RotationResolverInternal;
import android.service.rotationresolver.RotationResolutionRequest;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.LatencyTracker;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractPerUserSystemService;

import java.io.PrintWriter;

public final class RotationResolverManagerPerUserService extends AbstractPerUserSystemService {
    public ComponentName mComponentName;
    RemoteRotationResolverService.RotationRequest mCurrentRequest;
    public LatencyTracker mLatencyTracker;
    RemoteRotationResolverService mRemoteService;

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final void dumpLocked(PrintWriter printWriter) {
        super.dumpLocked(printWriter);
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        synchronized (this.mLock) {
            try {
                RemoteRotationResolverService remoteRotationResolverService = this.mRemoteService;
                if (remoteRotationResolverService != null) {
                    remoteRotationResolverService.dump("", indentingPrintWriter);
                }
                RemoteRotationResolverService.RotationRequest rotationRequest =
                        this.mCurrentRequest;
                if (rotationRequest != null) {
                    rotationRequest.getClass();
                    indentingPrintWriter.increaseIndent();
                    StringBuilder m =
                            DesktopModeService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("is dispatched="),
                                    rotationRequest.mIsDispatched,
                                    indentingPrintWriter,
                                    "is fulfilled:=");
                    m.append(rotationRequest.mIsFulfilled);
                    indentingPrintWriter.println(m.toString());
                    indentingPrintWriter.decreaseIndent();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public boolean isServiceAvailableLocked() {
        if (this.mComponentName == null) {
            this.mComponentName = updateServiceInfoLocked();
        }
        return this.mComponentName != null;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        try {
            ServiceInfo serviceInfo =
                    AppGlobals.getPackageManager()
                            .getServiceInfo(componentName, 128L, this.mUserId);
            if (serviceInfo != null
                    && !"android.permission.BIND_ROTATION_RESOLVER_SERVICE"
                            .equals(serviceInfo.permission)) {
                throw new SecurityException(
                        "Service "
                                + serviceInfo.getComponentName()
                                + " requires android.permission.BIND_ROTATION_RESOLVER_SERVICE"
                                + " permission. Found "
                                + serviceInfo.permission
                                + " permission");
            }
            return serviceInfo;
        } catch (RemoteException unused) {
            throw new PackageManager.NameNotFoundException(
                    AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(
                            componentName, "Could not get service for "));
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.rotationresolver.RotationResolverManagerPerUserService$1] */
    public void resolveRotationLocked(
            final RotationResolverInternal.RotationResolverCallbackInternal
                    rotationResolverCallbackInternal,
            RotationResolutionRequest rotationResolutionRequest,
            CancellationSignal cancellationSignal) {
        if (!isServiceAvailableLocked()) {
            Slog.w(
                    "RotationResolverManagerPerUserService",
                    "Service is not available at this moment.");
            rotationResolverCallbackInternal.onFailure(0);
            FrameworkStatsLog.write(
                    328,
                    RotationResolverManagerService.surfaceRotationToProto(
                            rotationResolutionRequest.getCurrentRotation()),
                    RotationResolverManagerService.surfaceRotationToProto(
                            rotationResolutionRequest.getProposedRotation()),
                    7);
            return;
        }
        if (this.mRemoteService == null) {
            this.mRemoteService =
                    new RemoteRotationResolverService(
                            this.mMaster.getContext(), this.mComponentName, this.mUserId);
        }
        RemoteRotationResolverService.RotationRequest rotationRequest = this.mCurrentRequest;
        if (rotationRequest != null && !rotationRequest.mIsFulfilled && rotationRequest != null) {
            rotationRequest.cancelInternal();
            this.mCurrentRequest = null;
        }
        synchronized (this.mLock) {
            this.mLatencyTracker.onActionStart(9);
        }
        this.mCurrentRequest =
                new RemoteRotationResolverService.RotationRequest(
                        new RotationResolverInternal
                                .RotationResolverCallbackInternal() { // from class:
                                                                      // com.android.server.rotationresolver.RotationResolverManagerPerUserService.1
                            public final void onFailure(int i) {
                                synchronized (RotationResolverManagerPerUserService.this.mLock) {
                                    RotationResolverManagerPerUserService.this.mLatencyTracker
                                            .onActionEnd(9);
                                }
                                rotationResolverCallbackInternal.onFailure(i);
                            }

                            public final void onSuccess(int i) {
                                synchronized (RotationResolverManagerPerUserService.this.mLock) {
                                    RotationResolverManagerPerUserService.this.mLatencyTracker
                                            .onActionEnd(9);
                                }
                                rotationResolverCallbackInternal.onSuccess(i);
                            }
                        },
                        rotationResolutionRequest,
                        cancellationSignal,
                        this.mLock);
        cancellationSignal.setOnCancelListener(
                new CancellationSignal.OnCancelListener() { // from class:
                    // com.android.server.rotationresolver.RotationResolverManagerPerUserService$$ExternalSyntheticLambda0
                    @Override // android.os.CancellationSignal.OnCancelListener
                    public final void onCancel() {
                        RotationResolverManagerPerUserService
                                rotationResolverManagerPerUserService =
                                        RotationResolverManagerPerUserService.this;
                        synchronized (rotationResolverManagerPerUserService.mLock) {
                            try {
                                RemoteRotationResolverService.RotationRequest rotationRequest2 =
                                        rotationResolverManagerPerUserService.mCurrentRequest;
                                if (rotationRequest2 != null && !rotationRequest2.mIsFulfilled) {
                                    Slog.d(
                                            "RotationResolverManagerPerUserService",
                                            "Trying to cancel the remote request. Reason: Client"
                                                + " cancelled.");
                                    rotationResolverManagerPerUserService.mCurrentRequest
                                            .cancelInternal();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
        this.mRemoteService.resolveRotation(this.mCurrentRequest);
        this.mCurrentRequest.mIsDispatched = true;
    }
}
