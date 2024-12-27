package com.android.server.wearable;

import android.app.AppGlobals;
import android.app.wearable.IWearableSensingCallback;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.service.voice.VoiceInteractionManagerInternal;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.server.LocalServices;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractPerUserSystemService;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

public final class WearableSensingManagerPerUserService extends AbstractPerUserSystemService {
    public ComponentName mComponentName;
    public final PackageManagerInternal mPackageManagerInternal;
    RemoteWearableSensingService mRemoteService;
    public WearableSensingSecureChannel mSecureChannel;
    public final Object mSecureChannelLock;
    public VoiceInteractionManagerInternal mVoiceInteractionManagerInternal;

    /* renamed from: com.android.server.wearable.WearableSensingManagerPerUserService$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ AtomicReference val$currentSecureChannelRef;
        public final /* synthetic */ RemoteCallback val$statusCallback;
        public final /* synthetic */ IWearableSensingCallback val$wrappedWearableSensingCallback;

        public AnonymousClass1(
                AnonymousClass3 anonymousClass3,
                RemoteCallback remoteCallback,
                AtomicReference atomicReference) {
            this.val$wrappedWearableSensingCallback = anonymousClass3;
            this.val$statusCallback = remoteCallback;
            this.val$currentSecureChannelRef = atomicReference;
        }
    }

    /* renamed from: -$$Nest$mstopActiveHotwordAudio, reason: not valid java name */
    public static void m1043$$Nest$mstopActiveHotwordAudio(
            WearableSensingManagerPerUserService wearableSensingManagerPerUserService) {
        synchronized (wearableSensingManagerPerUserService.mLock) {
            try {
                if (!wearableSensingManagerPerUserService.setUpServiceIfNeeded()) {
                    Slog.w(
                            "WearableSensingManagerPerUserService",
                            "Wearable sensing service is not available at this moment.");
                    return;
                }
                wearableSensingManagerPerUserService.ensureRemoteServiceInitiated$2();
                RemoteWearableSensingService remoteWearableSensingService =
                        wearableSensingManagerPerUserService.mRemoteService;
                remoteWearableSensingService.getClass();
                remoteWearableSensingService.post(
                        new RemoteWearableSensingService$$ExternalSyntheticLambda7(2));
            } finally {
            }
        }
    }

    public WearableSensingManagerPerUserService(
            WearableSensingManagerService wearableSensingManagerService, Object obj, int i) {
        super(wearableSensingManagerService, obj, i);
        this.mSecureChannelLock = new Object();
        this.mPackageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    }

    public static boolean isReadOnly(ParcelFileDescriptor parcelFileDescriptor) {
        try {
            return (Os.fcntlInt(parcelFileDescriptor.getFileDescriptor(), OsConstants.F_GETFL, 0)
                            & OsConstants.O_ACCMODE)
                    == OsConstants.O_RDONLY;
        } catch (ErrnoException e) {
            Slog.w(
                    "WearableSensingManagerPerUserService",
                    "Error encountered when trying to determine if the parcelFileDescriptor is"
                        + " read-only. Treating it as not read-only",
                    e);
            return false;
        }
    }

    public static void notifyStatusCallback(int i, RemoteCallback remoteCallback) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", i);
        remoteCallback.sendResult(bundle);
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final void dumpLocked(PrintWriter printWriter) {
        synchronized (this.mLock) {
            super.dumpLocked(printWriter);
        }
        RemoteWearableSensingService remoteWearableSensingService = this.mRemoteService;
        if (remoteWearableSensingService != null) {
            remoteWearableSensingService.dump("", new IndentingPrintWriter(printWriter, "  "));
        }
    }

    public final void ensureRemoteServiceInitiated$2() {
        if (this.mRemoteService == null) {
            this.mRemoteService =
                    new RemoteWearableSensingService(
                            this.mMaster.getContext(), this.mComponentName, this.mUserId);
        }
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        try {
            ServiceInfo serviceInfo =
                    AppGlobals.getPackageManager().getServiceInfo(componentName, 0L, this.mUserId);
            if (serviceInfo != null
                    && !"android.permission.BIND_WEARABLE_SENSING_SERVICE"
                            .equals(serviceInfo.permission)) {
                throw new SecurityException(
                        "Service "
                                + serviceInfo.getComponentName()
                                + " requires android.permission.BIND_WEARABLE_SENSING_SERVICE"
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

    public final void onProvideDataStream(
            ParcelFileDescriptor parcelFileDescriptor,
            IWearableSensingCallback iWearableSensingCallback,
            RemoteCallback remoteCallback) {
        Slog.i(
                "WearableSensingManagerPerUserService",
                "onProvideDataStream in per user service. Is data stream read-only? "
                        + isReadOnly(parcelFileDescriptor));
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    Slog.w(
                            "WearableSensingManagerPerUserService",
                            "Detection service is not available at this moment.");
                    notifyStatusCallback(3, remoteCallback);
                    return;
                }
                Slog.i("WearableSensingManagerPerUserService", "calling over to remote servvice.");
                ensureRemoteServiceInitiated$2();
                RemoteWearableSensingService remoteWearableSensingService = this.mRemoteService;
                AnonymousClass3 wrapWearableSensingCallback =
                        wrapWearableSensingCallback(iWearableSensingCallback);
                remoteWearableSensingService.getClass();
                remoteWearableSensingService.post(
                        new RemoteWearableSensingService$$ExternalSyntheticLambda0(
                                parcelFileDescriptor,
                                wrapWearableSensingCallback,
                                remoteCallback,
                                0));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onProvidedData(
            PersistableBundle persistableBundle,
            SharedMemory sharedMemory,
            RemoteCallback remoteCallback) {
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    Slog.w(
                            "WearableSensingManagerPerUserService",
                            "Detection service is not available at this moment.");
                    notifyStatusCallback(3, remoteCallback);
                    return;
                }
                ensureRemoteServiceInitiated$2();
                if (sharedMemory != null) {
                    sharedMemory.setProtect(OsConstants.PROT_READ);
                }
                RemoteWearableSensingService remoteWearableSensingService = this.mRemoteService;
                remoteWearableSensingService.getClass();
                remoteWearableSensingService.post(
                        new RemoteWearableSensingService$$ExternalSyntheticLambda0(
                                persistableBundle, sharedMemory, remoteCallback));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean setUpServiceIfNeeded() {
        if (this.mComponentName == null) {
            this.mComponentName = updateServiceInfoLocked();
        }
        if (this.mComponentName == null) {
            return false;
        }
        try {
            return AppGlobals.getPackageManager()
                            .getServiceInfo(this.mComponentName, 0L, this.mUserId)
                    != null;
        } catch (RemoteException unused) {
            Slog.w(
                    "WearableSensingManagerPerUserService",
                    "RemoteException while setting up service");
            return false;
        }
    }

    public final AnonymousClass3 wrapWearableSensingCallback(
            final IWearableSensingCallback iWearableSensingCallback) {
        if (iWearableSensingCallback == null) {
            return null;
        }
        if (this.mComponentName == null) {
            Slog.w(
                    "WearableSensingManagerPerUserService",
                    "Cannot create WearableSensingCallback because mComponentName is null.");
            return null;
        }
        if (Binder.getCallingUid()
                == this.mPackageManagerInternal.getPackageUid(
                        this.mComponentName.getPackageName(), 0L, this.mUserId)) {
            return new IWearableSensingCallback.Stub() { // from class:
                // com.android.server.wearable.WearableSensingManagerPerUserService.3
                public final void openFile(final String str, final AndroidFuture androidFuture) {
                    iWearableSensingCallback.openFile(
                            str,
                            new AndroidFuture()
                                    .whenComplete(
                                            new BiConsumer() { // from class:
                                                // com.android.server.wearable.WearableSensingManagerPerUserService$3$$ExternalSyntheticLambda0
                                                @Override // java.util.function.BiConsumer
                                                public final void accept(Object obj, Object obj2) {
                                                    String str2 = str;
                                                    AndroidFuture androidFuture2 = androidFuture;
                                                    ParcelFileDescriptor parcelFileDescriptor =
                                                            (ParcelFileDescriptor) obj;
                                                    Throwable th = (Throwable) obj2;
                                                    if (th != null) {
                                                        Slog.e(
                                                                "WearableSensingManagerPerUserService",
                                                                "Error when reading file " + str2,
                                                                th);
                                                        androidFuture2.complete((Object) null);
                                                        return;
                                                    }
                                                    if (parcelFileDescriptor == null) {
                                                        androidFuture2.complete((Object) null);
                                                    } else if (WearableSensingManagerPerUserService
                                                            .isReadOnly(parcelFileDescriptor)) {
                                                        androidFuture2.complete(
                                                                parcelFileDescriptor);
                                                    } else {
                                                        Slog.w(
                                                                "WearableSensingManagerPerUserService",
                                                                "Received writable"
                                                                    + " ParcelFileDescriptor from"
                                                                    + " app process. To prevent"
                                                                    + " arbitrary data egress,"
                                                                    + " sending null to"
                                                                    + " WearableSensingService"
                                                                    + " instead.");
                                                        androidFuture2.complete((Object) null);
                                                    }
                                                }
                                            }));
                }
            };
        }
        Slog.d(
                "WearableSensingManagerPerUserService",
                "Caller does not belong to the package that provides the WearableSensingService"
                    + " implementation. Do not forward WearableSensingCallback to"
                    + " WearableSensingService.");
        return null;
    }
}
