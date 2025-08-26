package com.android.systemui.kairos.internal.util;

import com.android.systemui.kairos.internal.TransactionCache$key$1;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class HeteroMap {
    public final ConcurrentHashMap store;

    private HeteroMap(ConcurrentHashMap<TransactionCache$key$1, Object> concurrentHashMap) {
        this.store = concurrentHashMap;
    }

    public HeteroMap() {
        this((ConcurrentHashMap<TransactionCache$key$1, Object>) new ConcurrentHashMap());
    }

    public HeteroMap(int i) {
        this((ConcurrentHashMap<TransactionCache$key$1, Object>) new ConcurrentHashMap(i));
    }
}
