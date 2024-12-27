package com.android.server.enterprise.adapterlayer;

import android.net.IDnsResolver;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DnsResolverAdapter$$ExternalSyntheticLambda2
        implements DnsResolverAdapter.CheckedRemoteRequest {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ DnsResolverAdapter$$ExternalSyntheticLambda2(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // com.android.server.enterprise.adapterlayer.DnsResolverAdapter.CheckedRemoteRequest
    public final void execute(IDnsResolver iDnsResolver) {
        switch (this.$r8$classId) {
            case 0:
                iDnsResolver.createNetworkCache(this.f$0);
                break;
            default:
                iDnsResolver.flushNetworkCache(this.f$0);
                break;
        }
    }
}
