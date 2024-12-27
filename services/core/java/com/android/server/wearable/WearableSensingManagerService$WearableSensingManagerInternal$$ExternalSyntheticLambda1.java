package com.android.server.wearable;

import android.app.wearable.IWearableSensingCallback;
import android.companion.CompanionDeviceManager;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.SharedMemory;
import android.util.Slog;

import com.android.internal.hidden_from_bootclasspath.android.app.wearable.Flags;
import com.android.server.wearable.WearableSensingManagerPerUserService.AnonymousClass1;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final /* synthetic */
class WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ RemoteCallback f$2;

    public /* synthetic */
    WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda1(
            ParcelFileDescriptor parcelFileDescriptor,
            IWearableSensingCallback iWearableSensingCallback,
            RemoteCallback remoteCallback,
            int i) {
        this.$r8$classId = i;
        this.f$0 = parcelFileDescriptor;
        this.f$1 = iWearableSensingCallback;
        this.f$2 = remoteCallback;
    }

    public /* synthetic */
    WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda1(
            PersistableBundle persistableBundle,
            SharedMemory sharedMemory,
            RemoteCallback remoteCallback) {
        this.$r8$classId = 2;
        this.f$0 = persistableBundle;
        this.f$1 = sharedMemory;
        this.f$2 = remoteCallback;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) this.f$0;
                IWearableSensingCallback iWearableSensingCallback =
                        (IWearableSensingCallback) this.f$1;
                RemoteCallback remoteCallback = this.f$2;
                WearableSensingManagerPerUserService wearableSensingManagerPerUserService =
                        (WearableSensingManagerPerUserService) obj;
                wearableSensingManagerPerUserService.getClass();
                Slog.i(
                        "WearableSensingManagerPerUserService",
                        "onProvideConnection in per user service.");
                synchronized (wearableSensingManagerPerUserService.mLock) {
                    try {
                        if (!wearableSensingManagerPerUserService.setUpServiceIfNeeded()) {
                            Slog.w(
                                    "WearableSensingManagerPerUserService",
                                    "Detection service is not available at this moment.");
                            WearableSensingManagerPerUserService.notifyStatusCallback(
                                    3, remoteCallback);
                            return;
                        }
                        WearableSensingManagerPerUserService.AnonymousClass3
                                wrapWearableSensingCallback =
                                        wearableSensingManagerPerUserService
                                                .wrapWearableSensingCallback(
                                                        iWearableSensingCallback);
                        synchronized (wearableSensingManagerPerUserService.mSecureChannelLock) {
                            WearableSensingSecureChannel wearableSensingSecureChannel =
                                    wearableSensingManagerPerUserService.mSecureChannel;
                            if (wearableSensingSecureChannel != null) {
                                wearableSensingSecureChannel.close();
                            }
                            try {
                                AtomicReference atomicReference = new AtomicReference();
                                WearableSensingSecureChannel create =
                                        WearableSensingSecureChannel.create(
                                                (CompanionDeviceManager)
                                                        wearableSensingManagerPerUserService
                                                                .mMaster
                                                                .getContext()
                                                                .getSystemService(
                                                                        CompanionDeviceManager
                                                                                .class),
                                                parcelFileDescriptor,
                                                wearableSensingManagerPerUserService
                                                .new AnonymousClass1(
                                                        wrapWearableSensingCallback,
                                                        remoteCallback,
                                                        atomicReference));
                                wearableSensingManagerPerUserService.mSecureChannel = create;
                                atomicReference.set(create);
                            } catch (IOException e) {
                                Slog.e(
                                        "WearableSensingManagerPerUserService",
                                        "Unable to create the secure channel.",
                                        e);
                                if (Flags.enableProvideWearableConnectionApi()) {
                                    WearableSensingManagerPerUserService.notifyStatusCallback(
                                            7, remoteCallback);
                                }
                            }
                        }
                        return;
                    } finally {
                    }
                }
            case 1:
                ((WearableSensingManagerPerUserService) obj)
                        .onProvideDataStream(
                                (ParcelFileDescriptor) this.f$0,
                                (IWearableSensingCallback) this.f$1,
                                this.f$2);
                return;
            default:
                ((WearableSensingManagerPerUserService) obj)
                        .onProvidedData(
                                (PersistableBundle) this.f$0, (SharedMemory) this.f$1, this.f$2);
                return;
        }
    }
}
