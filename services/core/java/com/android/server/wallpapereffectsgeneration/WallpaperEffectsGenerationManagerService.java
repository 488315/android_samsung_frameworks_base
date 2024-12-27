package com.android.server.wallpapereffectsgeneration;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.wallpapereffectsgeneration.CinematicEffectRequest;
import android.app.wallpapereffectsgeneration.CinematicEffectResponse;
import android.app.wallpapereffectsgeneration.ICinematicEffectListener;
import android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager;
import android.content.Context;
import android.os.Binder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService;
import android.util.Slog;
import com.android.internal.infra.AbstractRemoteService;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;
import com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService;
import com.android.server.wm.ActivityTaskManagerInternal;
import java.io.FileDescriptor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WallpaperEffectsGenerationManagerService extends AbstractMasterSystemService {
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WallpaperEffectsGenerationManagerStub extends IWallpaperEffectsGenerationManager.Stub {
        public WallpaperEffectsGenerationManagerStub() {
        }

        public final void generateCinematicEffect(final CinematicEffectRequest cinematicEffectRequest, final ICinematicEffectListener iCinematicEffectListener) {
            if (runForUser("generateCinematicEffect", true, new Consumer() { // from class: com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService$WallpaperEffectsGenerationManagerStub$$ExternalSyntheticLambda1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r3v1, types: [com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService$$ExternalSyntheticLambda0] */
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    final CinematicEffectRequest cinematicEffectRequest2 = cinematicEffectRequest;
                    ICinematicEffectListener iCinematicEffectListener2 = iCinematicEffectListener;
                    WallpaperEffectsGenerationPerUserService wallpaperEffectsGenerationPerUserService = (WallpaperEffectsGenerationPerUserService) obj;
                    wallpaperEffectsGenerationPerUserService.getClass();
                    String taskId = cinematicEffectRequest2.getTaskId();
                    WallpaperEffectsGenerationPerUserService.CinematicEffectListenerWrapper cinematicEffectListenerWrapper = wallpaperEffectsGenerationPerUserService.mCinematicEffectListenerWrapper;
                    AbstractMasterSystemService abstractMasterSystemService = wallpaperEffectsGenerationPerUserService.mMaster;
                    if (cinematicEffectListenerWrapper != null) {
                        try {
                            iCinematicEffectListener2.onCinematicEffectGenerated(cinematicEffectListenerWrapper.mTaskId.equals(taskId) ? new CinematicEffectResponse.Builder(3, taskId).build() : new CinematicEffectResponse.Builder(4, taskId).build());
                            return;
                        } catch (RemoteException unused) {
                            if (abstractMasterSystemService.debug) {
                                ProfileService$1$$ExternalSyntheticOutline0.m(new StringBuilder("RemoteException invoking cinematic effect listener for task["), wallpaperEffectsGenerationPerUserService.mCinematicEffectListenerWrapper.mTaskId, "]", "WallpaperEffectsGenerationPerUserService");
                                return;
                            }
                            return;
                        }
                    }
                    RemoteWallpaperEffectsGenerationService ensureRemoteServiceLocked = wallpaperEffectsGenerationPerUserService.ensureRemoteServiceLocked();
                    if (ensureRemoteServiceLocked != 0) {
                        ensureRemoteServiceLocked.executeOnResolvedService(new AbstractRemoteService.AsyncRequest() { // from class: com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService$$ExternalSyntheticLambda0
                            public final void run(IInterface iInterface) {
                                ((IWallpaperEffectsGenerationService) iInterface).onGenerateCinematicEffect(cinematicEffectRequest2);
                            }
                        });
                        wallpaperEffectsGenerationPerUserService.mCinematicEffectListenerWrapper = new WallpaperEffectsGenerationPerUserService.CinematicEffectListenerWrapper(taskId, iCinematicEffectListener2);
                        return;
                    }
                    if (abstractMasterSystemService.debug) {
                        Slog.d("WallpaperEffectsGenerationPerUserService", "Remote service not found");
                    }
                    try {
                        iCinematicEffectListener2.onCinematicEffectGenerated(new CinematicEffectResponse.Builder(0, taskId).build());
                    } catch (RemoteException unused2) {
                        if (abstractMasterSystemService.debug) {
                            DeviceIdleController$$ExternalSyntheticOutline0.m("Failed to invoke cinematic effect listener for task [", taskId, "]", "WallpaperEffectsGenerationPerUserService");
                        }
                    }
                }
            })) {
                return;
            }
            try {
                iCinematicEffectListener.onCinematicEffectGenerated(new CinematicEffectResponse.Builder(0, cinematicEffectRequest.getTaskId()).build());
            } catch (RemoteException unused) {
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new WallpaperEffectsGenerationManagerServiceShellCommand(WallpaperEffectsGenerationManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final void returnCinematicEffectResponse(final CinematicEffectResponse cinematicEffectResponse) {
            runForUser("returnCinematicResponse", false, new Consumer() { // from class: com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService$WallpaperEffectsGenerationManagerStub$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((WallpaperEffectsGenerationPerUserService) obj).invokeCinematicListenerAndCleanup(cinematicEffectResponse);
                }
            });
        }

        public final boolean runForUser(String str, boolean z, Consumer consumer) {
            boolean z2;
            int handleIncomingUser = ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), Binder.getCallingUserHandle().getIdentifier(), false, 0, (String) null, (String) null);
            if (z && WallpaperEffectsGenerationManagerService.this.getContext().checkCallingPermission("android.permission.MANAGE_WALLPAPER_EFFECTS_GENERATION") != 0 && !WallpaperEffectsGenerationManagerService.this.mServiceNameResolver.isTemporary(handleIncomingUser) && !WallpaperEffectsGenerationManagerService.this.mActivityTaskManagerInternal.isCallerRecents(Binder.getCallingUid())) {
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Permission Denial: Cannot call ", str, " from pid=");
                m.append(Binder.getCallingPid());
                m.append(", uid=");
                m.append(Binder.getCallingUid());
                String sb = m.toString();
                Slog.w("WallpaperEffectsGenerationManagerService", sb);
                throw new SecurityException(sb);
            }
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (WallpaperEffectsGenerationManagerService.this.mLock) {
                    try {
                        WallpaperEffectsGenerationPerUserService wallpaperEffectsGenerationPerUserService = (WallpaperEffectsGenerationPerUserService) WallpaperEffectsGenerationManagerService.this.getServiceForUserLocked(handleIncomingUser);
                        if (wallpaperEffectsGenerationPerUserService != null) {
                            if (!z && wallpaperEffectsGenerationPerUserService.getServiceUidLocked() != callingUid) {
                                String str2 = "Permission Denial: cannot call " + str + ", uid[" + callingUid + "] doesn't match service implementation";
                                Slog.w("WallpaperEffectsGenerationManagerService", str2);
                                throw new SecurityException(str2);
                            }
                            consumer.accept(wallpaperEffectsGenerationPerUserService);
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return z2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public WallpaperEffectsGenerationManagerService(Context context) {
        super(context, new FrameworkResourcesServiceNameResolver(context, R.string.device_state_notification_settings_button), null, 17);
        this.mActivityTaskManagerInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_WALLPAPER_EFFECTS_GENERATION", "WallpaperEffectsGenerationManagerService");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final int getMaximumTemporaryServiceDurationMs() {
        return 120000;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        return new WallpaperEffectsGenerationPerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageRestartedLocked(int i) {
        WallpaperEffectsGenerationPerUserService wallpaperEffectsGenerationPerUserService = (WallpaperEffectsGenerationPerUserService) peekServiceForUserLocked(i);
        if (wallpaperEffectsGenerationPerUserService != null) {
            if (wallpaperEffectsGenerationPerUserService.mMaster.debug) {
                Slog.v("WallpaperEffectsGenerationPerUserService", "onPackageRestartedLocked()");
            }
            wallpaperEffectsGenerationPerUserService.destroyAndRebindRemoteService();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageUpdatedLocked(int i) {
        WallpaperEffectsGenerationPerUserService wallpaperEffectsGenerationPerUserService = (WallpaperEffectsGenerationPerUserService) peekServiceForUserLocked(i);
        if (wallpaperEffectsGenerationPerUserService != null) {
            if (wallpaperEffectsGenerationPerUserService.mMaster.debug) {
                Slog.v("WallpaperEffectsGenerationPerUserService", "onPackageUpdatedLocked()");
            }
            wallpaperEffectsGenerationPerUserService.destroyAndRebindRemoteService();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("wallpaper_effects_generation", new WallpaperEffectsGenerationManagerStub());
    }
}
