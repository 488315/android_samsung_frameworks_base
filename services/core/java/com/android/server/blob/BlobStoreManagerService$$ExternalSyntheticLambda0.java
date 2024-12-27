package com.android.server.blob;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BlobStoreManagerService$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ BlobStoreManagerService$$ExternalSyntheticLambda0(
            int i, String str, ArrayList arrayList) {
        this.f$0 = str;
        this.f$1 = i;
        this.f$2 = arrayList;
    }

    public /* synthetic */ BlobStoreManagerService$$ExternalSyntheticLambda0(
            String str, int i, AtomicLong atomicLong) {
        this.f$0 = str;
        this.f$1 = i;
        this.f$2 = atomicLong;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = this.f$0;
                int i = this.f$1;
                AtomicLong atomicLong = (AtomicLong) this.f$2;
                BlobMetadata blobMetadata = (BlobMetadata) obj;
                if (blobMetadata.isALeasee(i, str)) {
                    atomicLong.getAndAdd(blobMetadata.getSize());
                    break;
                }
                break;
            default:
                String str2 = this.f$0;
                int i2 = this.f$1;
                ArrayList arrayList = (ArrayList) this.f$2;
                BlobMetadata blobMetadata2 = (BlobMetadata) obj;
                if (blobMetadata2.isALeasee(i2, str2)) {
                    arrayList.add(blobMetadata2.mBlobHandle);
                    break;
                }
                break;
        }
    }
}
