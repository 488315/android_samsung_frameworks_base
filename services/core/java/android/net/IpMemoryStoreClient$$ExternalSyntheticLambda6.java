package android.net;

import android.net.IpMemoryStoreClient;
import android.net.ipmemorystore.NetworkAttributes;
import android.net.ipmemorystore.OnL2KeyResponseListener;
import android.net.ipmemorystore.OnNetworkAttributesRetrievedListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class IpMemoryStoreClient$$ExternalSyntheticLambda6 implements IpMemoryStoreClient.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IIpMemoryStore f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ IpMemoryStoreClient$$ExternalSyntheticLambda6(IIpMemoryStore iIpMemoryStore, Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = iIpMemoryStore;
        this.f$1 = obj;
        this.f$2 = obj2;
    }

    @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                IpMemoryStoreClient.lambda$retrieveNetworkAttributes$12(this.f$0, (String) this.f$1, (OnNetworkAttributesRetrievedListener) this.f$2);
                break;
            default:
                IpMemoryStoreClient.lambda$findL2Key$6(this.f$0, (NetworkAttributes) this.f$1, (OnL2KeyResponseListener) this.f$2);
                break;
        }
    }
}
