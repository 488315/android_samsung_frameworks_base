package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.util.HeteroMap;
import com.android.systemui.kairos.internal.util.NULL;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TransactionStore {
    public final HeteroMap storage;

    private TransactionStore(HeteroMap heteroMap) {
        this.storage = heteroMap;
    }

    public final Object get(TransactionCache$key$1 transactionCache$key$1) {
        Object obj = this.storage.store.get(transactionCache$key$1);
        if (obj != null) {
            if (obj == NULL.INSTANCE) {
                return null;
            }
            return obj;
        }
        throw new IllegalStateException(("no value for " + transactionCache$key$1 + " in this transaction").toString());
    }

    public final void set(TransactionCache$key$1 transactionCache$key$1, Object obj) {
        ConcurrentHashMap concurrentHashMap = this.storage.store;
        if (obj == null) {
            obj = NULL.INSTANCE;
        }
        concurrentHashMap.put(transactionCache$key$1, obj);
    }

    public TransactionStore(int i) {
        this(new HeteroMap(i));
    }

    public TransactionStore() {
        this(new HeteroMap());
    }
}
