package com.android.server.rotationresolver;

import android.R;
import android.content.Context;
import android.hardware.SensorPrivacyManager;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.rotationresolver.RotationResolverInternal;
import android.service.rotationresolver.RotationResolutionRequest;
import android.util.Slog;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.LatencyTracker;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RotationResolverManagerService extends AbstractMasterSystemService {
    public final Context mContext;
    public boolean mIsServiceEnabled;
    public final SensorPrivacyManager mPrivacyManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends Binder {
        public BinderService() {}

        @Override // android.os.Binder
        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    RotationResolverManagerService.this.mContext,
                    "RotationResolverManagerService",
                    printWriter)) {
                synchronized (RotationResolverManagerService.this.mLock) {
                    RotationResolverManagerService.this.dumpLocked(printWriter);
                }
            }
        }

        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            RotationResolverManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.MANAGE_ROTATION_RESOLVER",
                    "RotationResolverManagerService");
            new RotationResolverShellCommand(RotationResolverManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends RotationResolverInternal {
        public LocalService() {}

        public final boolean isRotationResolverSupported() {
            boolean z;
            synchronized (RotationResolverManagerService.this.mLock) {
                z = RotationResolverManagerService.this.mIsServiceEnabled;
            }
            return z;
        }

        public final void resolveRotation(
                RotationResolverInternal.RotationResolverCallbackInternal
                        rotationResolverCallbackInternal,
                String str,
                int i,
                int i2,
                long j,
                CancellationSignal cancellationSignal) {
            Objects.requireNonNull(rotationResolverCallbackInternal);
            Objects.requireNonNull(cancellationSignal);
            synchronized (RotationResolverManagerService.this.mLock) {
                try {
                    boolean z =
                            !RotationResolverManagerService.this.mPrivacyManager
                                    .isSensorPrivacyEnabled(2);
                    RotationResolverManagerService rotationResolverManagerService =
                            RotationResolverManagerService.this;
                    if (rotationResolverManagerService.mIsServiceEnabled && z) {
                        ((RotationResolverManagerPerUserService)
                                        rotationResolverManagerService.getServiceForUserLocked(
                                                UserHandle.getCallingUserId()))
                                .resolveRotationLocked(
                                        rotationResolverCallbackInternal,
                                        str == null
                                                ? new RotationResolutionRequest("", i2, i, true, j)
                                                : new RotationResolutionRequest(
                                                        str, i2, i, true, j),
                                        cancellationSignal);
                    } else {
                        if (z) {
                            Slog.w(
                                    "RotationResolverManagerService",
                                    "Rotation Resolver service is disabled.");
                        } else {
                            Slog.w(
                                    "RotationResolverManagerService",
                                    "Camera is locked by a toggle.");
                        }
                        rotationResolverCallbackInternal.onFailure(0);
                        FrameworkStatsLog.write(
                                328,
                                RotationResolverManagerService.surfaceRotationToProto(i2),
                                RotationResolverManagerService.surfaceRotationToProto(i),
                                6);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public RotationResolverManagerService(Context context) {
        super(
                context,
                new FrameworkResourcesServiceNameResolver(context, R.string.deleted_key),
                null,
                68);
        this.mContext = context;
        this.mPrivacyManager = SensorPrivacyManager.getInstance(context);
    }

    public static int surfaceRotationToProto(int i) {
        if (i == 0) {
            return 2;
        }
        if (i == 1) {
            return 3;
        }
        if (i != 2) {
            return i != 3 ? 8 : 5;
        }
        return 4;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        RotationResolverManagerPerUserService rotationResolverManagerPerUserService =
                new RotationResolverManagerPerUserService(this, this.mLock, i);
        rotationResolverManagerPerUserService.mLatencyTracker =
                LatencyTracker.getInstance(getContext());
        return rotationResolverManagerPerUserService;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService,
              // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            DeviceConfig.addOnPropertiesChangedListener(
                    "rotation_resolver",
                    getContext().getMainExecutor(),
                    new DeviceConfig
                            .OnPropertiesChangedListener() { // from class:
                                                             // com.android.server.rotationresolver.RotationResolverManagerService$$ExternalSyntheticLambda0
                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            RotationResolverManagerService rotationResolverManagerService =
                                    RotationResolverManagerService.this;
                            rotationResolverManagerService.getClass();
                            if (properties.getKeyset().contains("service_enabled")) {
                                rotationResolverManagerService.mIsServiceEnabled =
                                        DeviceConfig.getBoolean(
                                                "rotation_resolver", "service_enabled", true);
                            }
                        }
                    });
            this.mIsServiceEnabled =
                    DeviceConfig.getBoolean("rotation_resolver", "service_enabled", true);
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServiceRemoved(
            AbstractPerUserSystemService abstractPerUserSystemService, int i) {
        RotationResolverManagerPerUserService rotationResolverManagerPerUserService =
                (RotationResolverManagerPerUserService) abstractPerUserSystemService;
        synchronized (this.mLock) {
            if (rotationResolverManagerPerUserService.mMaster.verbose) {
                Slog.v("RotationResolverManagerPerUserService", "destroyLocked()");
            }
            if (rotationResolverManagerPerUserService.mCurrentRequest != null) {
                Slog.d(
                        "RotationResolverManagerPerUserService",
                        "Trying to cancel the remote request. Reason: Service destroyed.");
                RemoteRotationResolverService.RotationRequest rotationRequest =
                        rotationResolverManagerPerUserService.mCurrentRequest;
                if (rotationRequest != null) {
                    rotationRequest.cancelInternal();
                    rotationResolverManagerPerUserService.mCurrentRequest = null;
                }
                RemoteRotationResolverService remoteRotationResolverService =
                        rotationResolverManagerPerUserService.mRemoteService;
                if (remoteRotationResolverService != null) {
                    remoteRotationResolverService.unbind();
                    rotationResolverManagerPerUserService.mRemoteService = null;
                }
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("resolver", new BinderService());
        publishLocalService(RotationResolverInternal.class, new LocalService());
    }
}
