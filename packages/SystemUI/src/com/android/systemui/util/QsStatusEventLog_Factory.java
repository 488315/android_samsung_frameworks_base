package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.qs.QSHost;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class QsStatusEventLog_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider qsHostProvider;
    private final Provider quickQsHostProvider;

    public QsStatusEventLog_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.qsHostProvider = provider2;
        this.quickQsHostProvider = provider3;
    }

    public static QsStatusEventLog_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new QsStatusEventLog_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static QsStatusEventLog newInstance(Context context, QSHost qSHost, QSHost qSHost2) {
        return new QsStatusEventLog(context, qSHost, qSHost2);
    }

    public static QsStatusEventLog_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new QsStatusEventLog_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public QsStatusEventLog get() {
        return newInstance((Context) this.contextProvider.get(), (QSHost) this.qsHostProvider.get(), (QSHost) this.quickQsHostProvider.get());
    }
}
