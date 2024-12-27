package android.net;

import android.net.ipmemorystore.NetworkAttributes;
import android.net.ipmemorystore.OnL2KeyResponseListener;
import android.net.ipmemorystore.OnNetworkAttributesRetrievedListener;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class IpMemoryStoreClient$$ExternalSyntheticLambda20
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IpMemoryStoreClient f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ IpMemoryStoreClient$$ExternalSyntheticLambda20(
            IpMemoryStoreClient ipMemoryStoreClient, Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = ipMemoryStoreClient;
        this.f$1 = obj;
        this.f$2 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$findL2Key$7(
                        (NetworkAttributes) this.f$1,
                        (OnL2KeyResponseListener) this.f$2,
                        (IIpMemoryStore) obj);
                break;
            default:
                this.f$0.lambda$retrieveNetworkAttributes$13(
                        (String) this.f$1,
                        (OnNetworkAttributesRetrievedListener) this.f$2,
                        (IIpMemoryStore) obj);
                break;
        }
    }
}
