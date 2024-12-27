package com.android.server.wearable;

import android.app.wearable.IWearableSensingCallback;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.SharedMemory;
import android.service.wearable.IWearableSensingService;
import android.util.Slog;

import com.android.internal.infra.ServiceConnector;

import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RemoteWearableSensingService$$ExternalSyntheticLambda0
        implements ServiceConnector.VoidJob {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ RemoteCallback f$2;

    public /* synthetic */ RemoteWearableSensingService$$ExternalSyntheticLambda0(
            ParcelFileDescriptor parcelFileDescriptor,
            WearableSensingManagerPerUserService.AnonymousClass3 anonymousClass3,
            RemoteCallback remoteCallback,
            int i) {
        this.$r8$classId = i;
        this.f$0 = parcelFileDescriptor;
        this.f$1 = anonymousClass3;
        this.f$2 = remoteCallback;
    }

    public /* synthetic */ RemoteWearableSensingService$$ExternalSyntheticLambda0(
            PersistableBundle persistableBundle,
            SharedMemory sharedMemory,
            RemoteCallback remoteCallback) {
        this.$r8$classId = 2;
        this.f$0 = persistableBundle;
        this.f$1 = sharedMemory;
        this.f$2 = remoteCallback;
    }

    public final void runNoResult(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) this.f$0;
                IWearableSensingCallback iWearableSensingCallback =
                        (IWearableSensingCallback) this.f$1;
                RemoteCallback remoteCallback = this.f$2;
                int i = RemoteWearableSensingService.$r8$clinit;
                ((IWearableSensingService) obj)
                        .provideDataStream(
                                parcelFileDescriptor, iWearableSensingCallback, remoteCallback);
                try {
                    parcelFileDescriptor.close();
                    break;
                } catch (IOException e) {
                    Slog.w(
                            "RemoteWearableSensingService",
                            "Unable to close the local parcelFileDescriptor.",
                            e);
                    return;
                }
            case 1:
                ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) this.f$0;
                IWearableSensingCallback iWearableSensingCallback2 =
                        (IWearableSensingCallback) this.f$1;
                RemoteCallback remoteCallback2 = this.f$2;
                int i2 = RemoteWearableSensingService.$r8$clinit;
                ((IWearableSensingService) obj)
                        .provideSecureConnection(
                                parcelFileDescriptor2, iWearableSensingCallback2, remoteCallback2);
                try {
                    parcelFileDescriptor2.close();
                    break;
                } catch (IOException e2) {
                    Slog.w(
                            "RemoteWearableSensingService",
                            "Unable to close the local parcelFileDescriptor.",
                            e2);
                    return;
                }
            default:
                PersistableBundle persistableBundle = (PersistableBundle) this.f$0;
                SharedMemory sharedMemory = (SharedMemory) this.f$1;
                RemoteCallback remoteCallback3 = this.f$2;
                int i3 = RemoteWearableSensingService.$r8$clinit;
                ((IWearableSensingService) obj)
                        .provideData(persistableBundle, sharedMemory, remoteCallback3);
                break;
        }
    }
}
