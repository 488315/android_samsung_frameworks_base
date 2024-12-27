package android.net;

import android.net.ipmemorystore.NetworkAttributes;
import android.net.ipmemorystore.OnSameL3NetworkResponseListener;
import android.net.ipmemorystore.OnStatusListener;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class IpMemoryStoreClient$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IpMemoryStoreClient f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ IpMemoryStoreClient$$ExternalSyntheticLambda0(IpMemoryStoreClient ipMemoryStoreClient, String str, Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = ipMemoryStoreClient;
        this.f$1 = str;
        this.f$2 = obj;
        this.f$3 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$storeNetworkAttributes$1(this.f$1, (NetworkAttributes) this.f$2, (OnStatusListener) this.f$3, (IIpMemoryStore) obj);
                break;
            default:
                this.f$0.lambda$isSameNetwork$10(this.f$1, (String) this.f$2, (OnSameL3NetworkResponseListener) this.f$3, (IIpMemoryStore) obj);
                break;
        }
    }
}
