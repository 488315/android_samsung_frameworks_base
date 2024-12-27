package com.android.server.smartspace;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.smartspace.ISmartspaceCallback;
import android.app.smartspace.ISmartspaceManager;
import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceSessionId;
import android.app.smartspace.SmartspaceTargetEvent;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.util.Slog;

import com.android.internal.hidden_from_bootclasspath.android.app.smartspace.flags.Flags;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;
import com.android.server.wm.ActivityTaskManagerInternal;

import java.io.FileDescriptor;
import java.util.function.Consumer;

public final class SmartspaceManagerService extends AbstractMasterSystemService {
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal;

    public final class SmartspaceManagerStub extends ISmartspaceManager.Stub {
        public SmartspaceManagerStub() {}

        public final void createSmartspaceSession(
                final SmartspaceConfig smartspaceConfig,
                final SmartspaceSessionId smartspaceSessionId,
                final IBinder iBinder) {
            runForUserLocked(
                    "createSmartspaceSession",
                    smartspaceSessionId,
                    new Consumer() { // from class:
                                     // com.android.server.smartspace.SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((SmartspacePerUserService) obj)
                                    .onCreateSmartspaceSessionLocked(
                                            smartspaceConfig, smartspaceSessionId, iBinder);
                        }
                    });
        }

        public final void destroySmartspaceSession(SmartspaceSessionId smartspaceSessionId) {
            runForUserLocked(
                    "destroySmartspaceSession",
                    smartspaceSessionId,
                    new SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda2(
                            smartspaceSessionId, 0));
        }

        public final void notifySmartspaceEvent(
                SmartspaceSessionId smartspaceSessionId,
                SmartspaceTargetEvent smartspaceTargetEvent) {
            runForUserLocked(
                    "notifySmartspaceEvent",
                    smartspaceSessionId,
                    new SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda0(
                            smartspaceSessionId, smartspaceTargetEvent, 2));
        }

        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new SmartspaceManagerServiceShellCommand(SmartspaceManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void registerSmartspaceUpdates(
                SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) {
            runForUserLocked(
                    "registerSmartspaceUpdates",
                    smartspaceSessionId,
                    new SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda0(
                            smartspaceSessionId, iSmartspaceCallback, 1));
        }

        public final void requestSmartspaceUpdate(SmartspaceSessionId smartspaceSessionId) {
            runForUserLocked(
                    "requestSmartspaceUpdate",
                    smartspaceSessionId,
                    new SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda2(
                            smartspaceSessionId, 1));
        }

        public final void runForUserLocked(
                String str, SmartspaceSessionId smartspaceSessionId, Consumer consumer) {
            int handleIncomingUser =
                    ((ActivityManagerInternal)
                                    LocalServices.getService(ActivityManagerInternal.class))
                            .handleIncomingUser(
                                    Binder.getCallingPid(),
                                    Binder.getCallingUid(),
                                    smartspaceSessionId.getUserHandle().getIdentifier(),
                                    false,
                                    0,
                                    (String) null,
                                    (String) null);
            Context context = SmartspaceManagerService.this.getContext();
            if (context.checkCallingPermission("android.permission.MANAGE_SMARTSPACE") == 0
                    || ((Flags.accessSmartspace()
                                    && context.checkCallingPermission(
                                                    "android.permission.ACCESS_SMARTSPACE")
                                            == 0)
                            || SmartspaceManagerService.this.mServiceNameResolver.isTemporary(
                                    handleIncomingUser)
                            || SmartspaceManagerService.this.mActivityTaskManagerInternal
                                    .isCallerRecents(Binder.getCallingUid()))) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (SmartspaceManagerService.this.mLock) {
                        consumer.accept(
                                (SmartspacePerUserService)
                                        SmartspaceManagerService.this.getServiceForUserLocked(
                                                handleIncomingUser));
                    }
                    return;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "Permission Denial: Cannot call ", str, " from pid=");
            m.append(Binder.getCallingPid());
            m.append(", uid=");
            m.append(Binder.getCallingUid());
            String sb = m.toString();
            Slog.w("SmartspaceManagerService", sb);
            throw new SecurityException(sb);
        }

        public final void unregisterSmartspaceUpdates(
                SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) {
            runForUserLocked(
                    "unregisterSmartspaceUpdates",
                    smartspaceSessionId,
                    new SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda0(
                            smartspaceSessionId, iSmartspaceCallback, 0));
        }
    }

    public SmartspaceManagerService(Context context) {
        super(
                context,
                new FrameworkResourcesServiceNameResolver(context, R.string.deprecated_abi_message),
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
                        "android.permission.MANAGE_SMARTSPACE", "SmartspaceManagerService");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final int getMaximumTemporaryServiceDurationMs() {
        return 120000;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        return new SmartspacePerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageRestartedLocked(int i) {
        SmartspacePerUserService smartspacePerUserService =
                (SmartspacePerUserService) peekServiceForUserLocked(i);
        if (smartspacePerUserService != null) {
            if (smartspacePerUserService.mMaster.debug) {
                Slog.v("SmartspacePerUserService", "onPackageRestartedLocked()");
            }
            smartspacePerUserService.destroyAndRebindRemoteService$3();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageUpdatedLocked(int i) {
        SmartspacePerUserService smartspacePerUserService =
                (SmartspacePerUserService) peekServiceForUserLocked(i);
        if (smartspacePerUserService != null) {
            if (smartspacePerUserService.mMaster.debug) {
                Slog.v("SmartspacePerUserService", "onPackageUpdatedLocked()");
            }
            smartspacePerUserService.destroyAndRebindRemoteService$3();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("smartspace", new SmartspaceManagerStub());
    }
}
