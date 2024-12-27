package com.android.server.profcollect;

import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UpdateEngine;
import android.provider.DeviceConfig;
import android.util.Log;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.art.ArtManagerLocal;
import com.android.server.art.model.BatchDexoptParams;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.LaunchObserverRegistryImpl;
import com.android.server.wm.LaunchObserverRegistryImpl$$ExternalSyntheticLambda0;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ProfcollectForwardingService$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ProfcollectForwardingService$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        IProfCollectd.Stub.Proxy proxy;
        Parcel obtain;
        Parcel obtain2;
        boolean z;
        IProfCollectd iProfCollectd;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ProfcollectForwardingService profcollectForwardingService =
                        (ProfcollectForwardingService) obj;
                IProfCollectd iProfCollectd2 = profcollectForwardingService.mIProfcollect;
                if (iProfCollectd2 != null) {
                    try {
                        proxy = (IProfCollectd.Stub.Proxy) iProfCollectd2;
                        obtain = Parcel.obtain(proxy.mRemote);
                        obtain2 = Parcel.obtain();
                    } catch (RemoteException e) {
                        ActivityManagerService$$ExternalSyntheticOutline0.m(
                                e,
                                new StringBuilder("Failed to get supported provider: "),
                                "ProfcollectForwardingService");
                    }
                    try {
                        obtain.writeInterfaceToken("com.android.server.profcollect.IProfCollectd");
                        proxy.mRemote.transact(6, obtain, obtain2, 0);
                        obtain2.readException();
                        String readString = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                        z = !readString.isEmpty();
                        if (z
                                || (iProfCollectd = profcollectForwardingService.mIProfcollect)
                                        == null) {
                            return;
                        }
                        try {
                            ProfcollectForwardingService.AnonymousClass1 anonymousClass1 =
                                    profcollectForwardingService.mProviderStatusCallback;
                            IProfCollectd.Stub.Proxy proxy2 =
                                    (IProfCollectd.Stub.Proxy) iProfCollectd;
                            obtain = Parcel.obtain(proxy2.mRemote);
                            obtain2 = Parcel.obtain();
                            try {
                                obtain.writeInterfaceToken(
                                        "com.android.server.profcollect.IProfCollectd");
                                obtain.writeStrongInterface(anonymousClass1);
                                proxy2.mRemote.transact(7, obtain, obtain2, 0);
                                obtain2.readException();
                                obtain2.recycle();
                                obtain.recycle();
                                return;
                            } finally {
                            }
                        } catch (RemoteException e2) {
                            ActivityManagerService$$ExternalSyntheticOutline0.m(
                                    e2,
                                    new StringBuilder(
                                            "Failed to register provider status callback: "),
                                    "ProfcollectForwardingService");
                            return;
                        }
                    } finally {
                    }
                }
                z = false;
                if (z) {
                    return;
                } else {
                    return;
                }
            case 1:
                ProfcollectForwardingService profcollectForwardingService2 =
                        (ProfcollectForwardingService) obj;
                try {
                    String str =
                            ((IProfCollectd.Stub.Proxy) profcollectForwardingService2.mIProfcollect)
                                            .report(profcollectForwardingService2.mUsageSetting)
                                    + ".zip";
                    if (profcollectForwardingService2.mUploadEnabled) {
                        profcollectForwardingService2
                                .getContext()
                                .sendBroadcast(
                                        new Intent()
                                                .setPackage("com.android.shell")
                                                .setAction(
                                                        "com.android.shell.action.PROFCOLLECT_UPLOAD")
                                                .putExtra("filename", str));
                        return;
                    } else {
                        Log.i("ProfcollectForwardingService", "Upload is not enabled.");
                        return;
                    }
                } catch (RemoteException e3) {
                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                            e3,
                            new StringBuilder("Failed to create report: "),
                            "ProfcollectForwardingService");
                    return;
                }
            case 2:
                final ProfcollectForwardingService profcollectForwardingService3 =
                        (ProfcollectForwardingService) obj;
                profcollectForwardingService3.getClass();
                LaunchObserverRegistryImpl launchObserverRegistry =
                        ((ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class))
                                .getLaunchObserverRegistry();
                launchObserverRegistry.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new LaunchObserverRegistryImpl$$ExternalSyntheticLambda0(1),
                                launchObserverRegistry,
                                profcollectForwardingService3.mAppLaunchObserver));
                ((CameraManager)
                                profcollectForwardingService3
                                        .getContext()
                                        .getSystemService(CameraManager.class))
                        .registerAvailabilityCallback(
                                new ProfcollectForwardingService.AnonymousClass4(
                                        profcollectForwardingService3),
                                (Handler) null);
                ArtManagerLocal artManagerLocal =
                        (ArtManagerLocal) LocalManagerRegistry.getManager(ArtManagerLocal.class);
                if (artManagerLocal == null) {
                    Log.w("ProfcollectForwardingService", "Couldn't get ArtManagerLocal");
                } else {
                    artManagerLocal.setBatchDexoptStartCallback(
                            new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                            new ArtManagerLocal
                                    .BatchDexoptStartCallback() { // from class:
                                                                  // com.android.server.profcollect.ProfcollectForwardingService$$ExternalSyntheticLambda3
                                public final void onBatchDexoptStart(
                                        PackageManagerLocal.FilteredSnapshot filteredSnapshot,
                                        String str2,
                                        List list,
                                        BatchDexoptParams.Builder builder,
                                        CancellationSignal cancellationSignal) {
                                    ProfcollectForwardingService profcollectForwardingService4 =
                                            ProfcollectForwardingService.this;
                                    if (profcollectForwardingService4.mIProfcollect == null) {
                                        return;
                                    }
                                    if (ThreadLocalRandom.current().nextInt(100)
                                            < DeviceConfig.getInt(
                                                    "profcollect_native_boot",
                                                    "dex2oat_trace_freq",
                                                    25)) {
                                        BackgroundThread.get()
                                                .getThreadHandler()
                                                .postDelayed(
                                                        new ProfcollectForwardingService$$ExternalSyntheticLambda0(
                                                                3, profcollectForwardingService4),
                                                        1000L);
                                    }
                                }
                            });
                }
                new UpdateEngine().bind(new ProfcollectForwardingService.AnonymousClass3());
                return;
            case 3:
                ProfcollectForwardingService profcollectForwardingService4 =
                        (ProfcollectForwardingService) obj;
                profcollectForwardingService4.getClass();
                try {
                    ((IProfCollectd.Stub.Proxy) profcollectForwardingService4.mIProfcollect)
                            .trace_once("dex2oat");
                    return;
                } catch (RemoteException e4) {
                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                            e4,
                            new StringBuilder("Failed to initiate trace: "),
                            "ProfcollectForwardingService");
                    return;
                }
            case 4:
                ProfcollectForwardingService profcollectForwardingService5 =
                        (ProfcollectForwardingService) obj;
                profcollectForwardingService5.getClass();
                try {
                    ((IProfCollectd.Stub.Proxy) profcollectForwardingService5.mIProfcollect)
                            .trace_once("applaunch");
                    return;
                } catch (RemoteException e5) {
                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                            e5,
                            new StringBuilder("Failed to initiate trace: "),
                            "ProfcollectForwardingService");
                    return;
                }
            default:
                ProfcollectForwardingService.AnonymousClass4 anonymousClass4 =
                        (ProfcollectForwardingService.AnonymousClass4) obj;
                anonymousClass4.getClass();
                try {
                    ((IProfCollectd.Stub.Proxy) anonymousClass4.this$0.mIProfcollect)
                            .trace_once("camera");
                    return;
                } catch (RemoteException e6) {
                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                            e6,
                            new StringBuilder("Failed to initiate trace: "),
                            "ProfcollectForwardingService");
                    return;
                }
        }
    }
}
