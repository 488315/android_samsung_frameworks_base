package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.qs.QSTileHost;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QsStatusEventLog_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider hostProvider;

    public QsStatusEventLog_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.hostProvider = provider2;
    }

    public static QsStatusEventLog_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new QsStatusEventLog_Factory(provider, provider2);
    }

    public static QsStatusEventLog newInstance(Context context, QSTileHost qSTileHost) {
        return new QsStatusEventLog(context, qSTileHost);
    }

    @Override // javax.inject.Provider
    public QsStatusEventLog get() {
        return newInstance((Context) this.contextProvider.get(), (QSTileHost) this.hostProvider.get());
    }
}
