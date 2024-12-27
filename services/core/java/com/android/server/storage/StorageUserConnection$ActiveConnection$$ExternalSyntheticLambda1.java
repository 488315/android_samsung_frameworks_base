package com.android.server.storage;

import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.storage.StorageVolume;
import android.service.storage.IExternalStorageService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda1
        implements StorageUserConnection.AsyncStorageServiceCall {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda1(
            StorageUserConnection.Session session, ParcelFileDescriptor parcelFileDescriptor) {
        this.f$0 = session;
        this.f$1 = parcelFileDescriptor;
    }

    public /* synthetic */ StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda1(
            String str, StorageVolume storageVolume) {
        this.f$0 = str;
        this.f$1 = storageVolume;
    }

    @Override // com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall
    public final void run(
            IExternalStorageService iExternalStorageService, RemoteCallback remoteCallback) {
        switch (this.$r8$classId) {
            case 0:
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) this.f$1;
                StorageUserConnection.Session session = (StorageUserConnection.Session) this.f$0;
                iExternalStorageService.startSession(
                        session.sessionId,
                        3,
                        parcelFileDescriptor,
                        session.upperPath,
                        session.lowerPath,
                        remoteCallback);
                break;
            default:
                iExternalStorageService.notifyVolumeStateChanged(
                        (String) this.f$0, (StorageVolume) this.f$1, remoteCallback);
                break;
        }
    }
}
