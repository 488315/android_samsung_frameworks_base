package android.net;

import android.net.ipmemorystore.OnDeleteStatusListener;

import java.util.function.Consumer;

public final /* synthetic */ class IpMemoryStoreClient$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IpMemoryStoreClient f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ OnDeleteStatusListener f$3;

    public /* synthetic */ IpMemoryStoreClient$$ExternalSyntheticLambda2(
            IpMemoryStoreClient ipMemoryStoreClient,
            String str,
            boolean z,
            OnDeleteStatusListener onDeleteStatusListener,
            int i) {
        this.$r8$classId = i;
        this.f$0 = ipMemoryStoreClient;
        this.f$1 = str;
        this.f$2 = z;
        this.f$3 = onDeleteStatusListener;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$delete$19(this.f$1, this.f$2, this.f$3, (IIpMemoryStore) obj);
                break;
            default:
                this.f$0.lambda$deleteCluster$22(
                        this.f$1, this.f$2, this.f$3, (IIpMemoryStore) obj);
                break;
        }
    }
}
