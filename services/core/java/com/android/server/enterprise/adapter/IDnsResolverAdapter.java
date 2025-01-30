package com.android.server.enterprise.adapter;

import android.net.ResolverParamsParcel;

/* loaded from: classes2.dex */
public interface IDnsResolverAdapter {
    void createNetworkCache(int i);

    void flushNetworkCache(int i);

    void setResolverConfiguration(ResolverParamsParcel resolverParamsParcel);
}
