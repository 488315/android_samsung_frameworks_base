package com.android.server.blob;

import android.os.UserHandle;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public final /* synthetic */
class BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ AtomicLong f$1;

    public /* synthetic */
    BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda2(
            Object obj, AtomicLong atomicLong, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = atomicLong;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = (String) this.f$0;
                AtomicLong atomicLong = this.f$1;
                BlobStoreSession blobStoreSession = (BlobStoreSession) obj;
                if (blobStoreSession.mOwnerPackageName.equals(str)) {
                    atomicLong.getAndAdd(blobStoreSession.getSize());
                    return;
                }
                return;
            default:
                UserHandle userHandle = (UserHandle) this.f$0;
                AtomicLong atomicLong2 = this.f$1;
                BlobMetadata blobMetadata = (BlobMetadata) obj;
                int identifier = userHandle.getIdentifier();
                synchronized (blobMetadata.mMetadataLock) {
                    try {
                        int size = blobMetadata.mLeasees.size();
                        for (int i = 0; i < size; i++) {
                            if (identifier
                                    != UserHandle.getUserId(
                                            ((BlobMetadata.Leasee) blobMetadata.mLeasees.valueAt(i))
                                                    .uid)) {
                                return;
                            }
                        }
                        atomicLong2.getAndAdd(blobMetadata.getSize());
                        return;
                    } finally {
                    }
                }
        }
    }
}
