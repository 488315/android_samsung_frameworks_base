package com.android.server.enterprise.adapterlayer;

import android.net.IDnsResolver;
import android.net.ResolverOptionsParcel;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DnsResolverAdapter$$ExternalSyntheticLambda0
        implements DnsResolverAdapter.CheckedRemoteRequest {
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DnsResolverAdapter$$ExternalSyntheticLambda0(int i, boolean z) {
        this.f$0 = z;
        this.f$1 = i;
    }

    @Override // com.android.server.enterprise.adapterlayer.DnsResolverAdapter.CheckedRemoteRequest
    public final void execute(IDnsResolver iDnsResolver) {
        ResolverOptionsParcel resolverOptionsParcel = new ResolverOptionsParcel();
        resolverOptionsParcel.enforceDnsUid = this.f$0;
        iDnsResolver.setResolverOptions(this.f$1, resolverOptionsParcel);
    }
}
