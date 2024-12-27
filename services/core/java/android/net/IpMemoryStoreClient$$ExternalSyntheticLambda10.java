package android.net;

import android.net.ipmemorystore.OnDeleteStatusListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class IpMemoryStoreClient$$ExternalSyntheticLambda10
        implements IpMemoryStoreClient.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IIpMemoryStore f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ OnDeleteStatusListener f$3;

    public /* synthetic */ IpMemoryStoreClient$$ExternalSyntheticLambda10(
            IIpMemoryStore iIpMemoryStore,
            String str,
            boolean z,
            OnDeleteStatusListener onDeleteStatusListener,
            int i) {
        this.$r8$classId = i;
        this.f$0 = iIpMemoryStore;
        this.f$1 = str;
        this.f$2 = z;
        this.f$3 = onDeleteStatusListener;
    }

    @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                IpMemoryStoreClient.lambda$delete$18(this.f$0, this.f$1, this.f$2, this.f$3);
                break;
            default:
                IpMemoryStoreClient.lambda$deleteCluster$21(this.f$0, this.f$1, this.f$2, this.f$3);
                break;
        }
    }
}
