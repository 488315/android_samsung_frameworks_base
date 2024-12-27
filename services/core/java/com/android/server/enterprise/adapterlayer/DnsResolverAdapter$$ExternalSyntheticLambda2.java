package com.android.server.enterprise.adapterlayer;

import android.net.IDnsResolver;

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
