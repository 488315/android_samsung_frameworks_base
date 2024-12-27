package com.android.server.appprediction;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.IPredictionCallback;
import android.app.prediction.IPredictionManager;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.util.Slog;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;
import com.android.server.wm.ActivityTaskManagerInternal;

import java.io.FileDescriptor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppPredictionManagerService extends AbstractMasterSystemService {
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PredictionManagerServiceStub extends IPredictionManager.Stub {
        public PredictionManagerServiceStub() {}

        public final void createPredictionSession(
                AppPredictionContext appPredictionContext,
                AppPredictionSessionId appPredictionSessionId,
                IBinder iBinder) {
            runForUserLocked(
                    "createPredictionSession",
                    appPredictionSessionId,
                    new AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda4(
                            appPredictionContext, appPredictionSessionId, iBinder));
        }

        public final void notifyAppTargetEvent(
                AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) {
            runForUserLocked(
                    "notifyAppTargetEvent",
                    appPredictionSessionId,
                    new AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda0(
                            appPredictionSessionId, appTargetEvent, 1));
        }

        public final void notifyLaunchLocationShown(
                AppPredictionSessionId appPredictionSessionId,
                String str,
                ParceledListSlice parceledListSlice) {
            runForUserLocked(
                    "notifyLaunchLocationShown",
                    appPredictionSessionId,
                    new AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda4(
                            appPredictionSessionId, str, parceledListSlice, 2));
        }

        public final void onDestroyPredictionSession(
                AppPredictionSessionId appPredictionSessionId) {
            runForUserLocked(
                    "onDestroyPredictionSession",
                    appPredictionSessionId,
                    new AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda1(
                            appPredictionSessionId, 0));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new AppPredictionManagerServiceShellCommand(AppPredictionManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void registerPredictionUpdates(
                AppPredictionSessionId appPredictionSessionId,
                IPredictionCallback iPredictionCallback) {
            runForUserLocked(
                    "registerPredictionUpdates",
                    appPredictionSessionId,
                    new AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda2(
                            appPredictionSessionId, iPredictionCallback, 1));
        }

        public final void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) {
            runForUserLocked(
                    "requestPredictionUpdate",
                    appPredictionSessionId,
                    new AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda1(
                            appPredictionSessionId, 1));
        }

        public final void requestServiceFeatures(
                AppPredictionSessionId appPredictionSessionId, IRemoteCallback iRemoteCallback) {
            runForUserLocked(
                    "requestServiceFeatures",
                    appPredictionSessionId,
                    new AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda0(
                            appPredictionSessionId, iRemoteCallback, 0));
        }

        public final void runForUserLocked(
                String str, AppPredictionSessionId appPredictionSessionId, Consumer consumer) {
            int handleIncomingUser =
                    ((ActivityManagerInternal)
                                    LocalServices.getService(ActivityManagerInternal.class))
                            .handleIncomingUser(
                                    Binder.getCallingPid(),
                                    Binder.getCallingUid(),
                                    appPredictionSessionId.getUserId(),
                                    false,
                                    0,
                                    (String) null,
                                    (String) null);
            if (AppPredictionManagerService.this
                                    .getContext()
                                    .checkCallingPermission(
                                            "android.permission.PACKAGE_USAGE_STATS")
                            != 0
                    && !AppPredictionManagerService.this.mServiceNameResolver.isTemporary(
                            handleIncomingUser)
                    && !AppPredictionManagerService.this.mActivityTaskManagerInternal
                            .isCallerRecents(Binder.getCallingUid())
                    && Binder.getCallingUid() != 1000) {
                throw new SecurityException(
                        ActivityManagerService$$ExternalSyntheticOutline0.m(
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Permission Denial: ", str, " from pid="),
                                ", uid=",
                                " expected caller to hold PACKAGE_USAGE_STATS permission",
                                "AppPredictionManagerService"));
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (AppPredictionManagerService.this.mLock) {
                    consumer.accept(
                            (AppPredictionPerUserService)
                                    AppPredictionManagerService.this.getServiceForUserLocked(
                                            handleIncomingUser));
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sortAppTargets(
                AppPredictionSessionId appPredictionSessionId,
                ParceledListSlice parceledListSlice,
                IPredictionCallback iPredictionCallback) {
            runForUserLocked(
                    "sortAppTargets",
                    appPredictionSessionId,
                    new AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda4(
                            appPredictionSessionId, parceledListSlice, iPredictionCallback, 1));
        }

        public final void unregisterPredictionUpdates(
                AppPredictionSessionId appPredictionSessionId,
                IPredictionCallback iPredictionCallback) {
            runForUserLocked(
                    "unregisterPredictionUpdates",
                    appPredictionSessionId,
                    new AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda2(
                            appPredictionSessionId, iPredictionCallback, 0));
        }
    }

    public AppPredictionManagerService(Context context) {
        super(
                context,
                new FrameworkResourcesServiceNameResolver(context, R.string.date_and_time),
                null,
                17);
        this.mActivityTaskManagerInternal =
                (ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void enforceCallingPermissionForManagement() {
        getContext()
                .enforceCallingPermission(
                        "android.permission.MANAGE_APP_PREDICTIONS", "AppPredictionManagerService");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final int getMaximumTemporaryServiceDurationMs() {
        return 120000;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        return new AppPredictionPerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageRestartedLocked(int i) {
        AppPredictionPerUserService appPredictionPerUserService =
                (AppPredictionPerUserService) peekServiceForUserLocked(i);
        if (appPredictionPerUserService != null) {
            if (appPredictionPerUserService.mMaster.debug) {
                Slog.v("AppPredictionPerUserService", "onPackageRestartedLocked()");
            }
            appPredictionPerUserService.destroyAndRebindRemoteService$1();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageUpdatedLocked(int i) {
        AppPredictionPerUserService appPredictionPerUserService =
                (AppPredictionPerUserService) peekServiceForUserLocked(i);
        if (appPredictionPerUserService != null) {
            if (appPredictionPerUserService.mMaster.debug) {
                Slog.v("AppPredictionPerUserService", "onPackageUpdatedLocked()");
            }
            appPredictionPerUserService.destroyAndRebindRemoteService$1();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("app_prediction", new PredictionManagerServiceStub());
    }
}
