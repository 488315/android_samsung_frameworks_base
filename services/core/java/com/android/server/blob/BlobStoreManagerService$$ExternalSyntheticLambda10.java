package com.android.server.blob;

import java.util.Map;
import java.util.function.Predicate;

public final /* synthetic */ class BlobStoreManagerService$$ExternalSyntheticLambda10
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BlobStoreManagerService f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BlobStoreManagerService$$ExternalSyntheticLambda10(
            BlobStoreManagerService blobStoreManagerService, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = blobStoreManagerService;
        this.f$1 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BlobStoreManagerService blobStoreManagerService = this.f$0;
                int i = this.f$1;
                blobStoreManagerService.getClass();
                BlobMetadata blobMetadata = (BlobMetadata) ((Map.Entry) obj).getValue();
                if (i == -1) {
                    blobStoreManagerService.mActiveBlobIds.remove(
                            Long.valueOf(blobMetadata.mBlobId));
                    return true;
                }
                blobMetadata.removeDataForUser(i);
                if (!blobMetadata.shouldBeDeleted(false)) {
                    return false;
                }
                blobStoreManagerService.mActiveBlobIds.remove(Long.valueOf(blobMetadata.mBlobId));
                return true;
            default:
                BlobStoreManagerService blobStoreManagerService2 = this.f$0;
                int i2 = this.f$1;
                blobStoreManagerService2.getClass();
                BlobMetadata blobMetadata2 = (BlobMetadata) ((Map.Entry) obj).getValue();
                blobMetadata2.removeDataForUser(i2);
                if (!blobMetadata2.shouldBeDeleted(true)) {
                    return false;
                }
                blobStoreManagerService2.deleteBlobLocked(blobMetadata2);
                return true;
        }
    }
}
