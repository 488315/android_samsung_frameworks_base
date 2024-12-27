package android.net;

import android.net.IpMemoryStoreClient;
import android.net.ipmemorystore.OnStatusListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class IpMemoryStoreClient$$ExternalSyntheticLambda1 implements IpMemoryStoreClient.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OnStatusListener f$0;

    public /* synthetic */ IpMemoryStoreClient$$ExternalSyntheticLambda1(OnStatusListener onStatusListener, int i) {
        this.$r8$classId = i;
        this.f$0 = onStatusListener;
    }

    @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
    public final void run() {
        int i = this.$r8$classId;
        OnStatusListener onStatusListener = this.f$0;
        switch (i) {
            case 0:
                IpMemoryStoreClient.lambda$storeNetworkAttributes$2(onStatusListener);
                break;
            default:
                IpMemoryStoreClient.lambda$storeBlob$5(onStatusListener);
                break;
        }
    }
}
