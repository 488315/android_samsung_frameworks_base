package android.net;

import android.net.ipmemorystore.OnBlobRetrievedListener;
import android.net.ipmemorystore.OnL2KeyResponseListener;
import android.net.ipmemorystore.OnNetworkAttributesRetrievedListener;
import android.net.ipmemorystore.OnSameL3NetworkResponseListener;

public final /* synthetic */ class IpMemoryStoreClient$$ExternalSyntheticLambda5
        implements IpMemoryStoreClient.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ IpMemoryStoreClient$$ExternalSyntheticLambda5(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                IpMemoryStoreClient.lambda$isSameNetwork$11((OnSameL3NetworkResponseListener) obj);
                break;
            case 1:
                ((IIpMemoryStore) obj).factoryReset();
                break;
            case 2:
                IpMemoryStoreClient.lambda$findL2Key$8((OnL2KeyResponseListener) obj);
                break;
            case 3:
                IpMemoryStoreClient.lambda$retrieveNetworkAttributes$14(
                        (OnNetworkAttributesRetrievedListener) obj);
                break;
            default:
                IpMemoryStoreClient.lambda$retrieveBlob$17((OnBlobRetrievedListener) obj);
                break;
        }
    }
}
