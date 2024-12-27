package com.android.server.blob;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BlobStoreManagerService$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ AtomicInteger f$2;

    public /* synthetic */ BlobStoreManagerService$$ExternalSyntheticLambda9(int i, String str, AtomicInteger atomicInteger) {
        this.$r8$classId = 1;
        this.f$1 = i;
        this.f$0 = str;
        this.f$2 = atomicInteger;
    }

    public /* synthetic */ BlobStoreManagerService$$ExternalSyntheticLambda9(String str, int i, AtomicInteger atomicInteger, int i2) {
        this.$r8$classId = i2;
        this.f$0 = str;
        this.f$1 = i;
        this.f$2 = atomicInteger;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = this.f$0;
                int i = this.f$1;
                AtomicInteger atomicInteger = this.f$2;
                if (((BlobMetadata) obj).isALeasee(i, str)) {
                    atomicInteger.getAndIncrement();
                    break;
                }
                break;
            case 1:
                int i2 = this.f$1;
                String str2 = this.f$0;
                AtomicInteger atomicInteger2 = this.f$2;
                BlobStoreSession blobStoreSession = (BlobStoreSession) obj;
                if (blobStoreSession.mOwnerUid == i2 && blobStoreSession.mOwnerPackageName.equals(str2)) {
                    atomicInteger2.getAndIncrement();
                    break;
                }
                break;
            default:
                String str3 = this.f$0;
                int i3 = this.f$1;
                AtomicInteger atomicInteger3 = this.f$2;
                if (((BlobMetadata) obj).isACommitter(i3, str3)) {
                    atomicInteger3.getAndIncrement();
                    break;
                }
                break;
        }
    }
}
