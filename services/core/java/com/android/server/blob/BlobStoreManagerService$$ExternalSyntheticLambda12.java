package com.android.server.blob;

import android.util.StatsEvent;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.blob.BlobMetadata;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BlobStoreManagerService$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BlobStoreManagerService$$ExternalSyntheticLambda12(int i, List list) {
        this.f$0 = list;
        this.f$1 = i;
    }

    public /* synthetic */ BlobStoreManagerService$$ExternalSyntheticLambda12(int i, AtomicLong atomicLong) {
        this.f$1 = i;
        this.f$0 = atomicLong;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        StatsEvent buildStatsEvent;
        switch (this.$r8$classId) {
            case 0:
                List list = (List) this.f$0;
                int i = this.f$1;
                BlobMetadata blobMetadata = (BlobMetadata) obj;
                synchronized (blobMetadata.mMetadataLock) {
                    try {
                        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
                        int size = blobMetadata.mCommitters.size();
                        int i2 = 0;
                        int i3 = 0;
                        while (true) {
                            long j = 1120986464257L;
                            long j2 = 2246267895809L;
                            if (i3 < size) {
                                BlobMetadata.Committer committer = (BlobMetadata.Committer) blobMetadata.mCommitters.valueAt(i3);
                                long start = protoOutputStream.start(2246267895809L);
                                protoOutputStream.write(1120986464257L, committer.uid);
                                protoOutputStream.write(1112396529666L, committer.commitTimeMs);
                                protoOutputStream.write(1120986464259L, committer.blobAccessMode.mAccessType);
                                protoOutputStream.write(1120986464260L, committer.blobAccessMode.mAllowedPackages.size());
                                protoOutputStream.end(start);
                                i3++;
                            } else {
                                byte[] bytes = protoOutputStream.getBytes();
                                ProtoOutputStream protoOutputStream2 = new ProtoOutputStream();
                                int size2 = blobMetadata.mLeasees.size();
                                while (i2 < size2) {
                                    BlobMetadata.Leasee leasee = (BlobMetadata.Leasee) blobMetadata.mLeasees.valueAt(i2);
                                    long start2 = protoOutputStream2.start(j2);
                                    protoOutputStream2.write(j, leasee.uid);
                                    protoOutputStream2.write(1112396529666L, leasee.expiryTimeMillis);
                                    protoOutputStream2.end(start2);
                                    i2++;
                                    j = 1120986464257L;
                                    j2 = 2246267895809L;
                                }
                                buildStatsEvent = FrameworkStatsLog.buildStatsEvent(i, blobMetadata.mBlobId, blobMetadata.getSize(), blobMetadata.mBlobHandle.getExpiryTimeMillis(), bytes, protoOutputStream2.getBytes());
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                list.add(buildStatsEvent);
                return;
            default:
                int i4 = this.f$1;
                AtomicLong atomicLong = (AtomicLong) this.f$0;
                BlobStoreSession blobStoreSession = (BlobStoreSession) obj;
                if (blobStoreSession.mOwnerUid == i4) {
                    atomicLong.getAndAdd(blobStoreSession.getSize());
                    return;
                }
                return;
        }
    }
}
