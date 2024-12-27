package android.net;

import android.net.IpMemoryStoreClient;
import android.net.ipmemorystore.OnDeleteStatusListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class IpMemoryStoreClient$$ExternalSyntheticLambda3 implements IpMemoryStoreClient.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OnDeleteStatusListener f$0;

    public /* synthetic */ IpMemoryStoreClient$$ExternalSyntheticLambda3(OnDeleteStatusListener onDeleteStatusListener, int i) {
        this.$r8$classId = i;
        this.f$0 = onDeleteStatusListener;
    }

    @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
    public final void run() {
        int i = this.$r8$classId;
        OnDeleteStatusListener onDeleteStatusListener = this.f$0;
        switch (i) {
            case 0:
                IpMemoryStoreClient.lambda$delete$20(onDeleteStatusListener);
                break;
            default:
                IpMemoryStoreClient.lambda$deleteCluster$23(onDeleteStatusListener);
                break;
        }
    }
}
