package com.android.systemui.util.animation.data.repository;

import android.content.ContentResolver;
import android.os.Handler;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AnimationStatusRepositoryImpl_Factory implements Provider {
    private final Provider backgroundDispatcherProvider;
    private final Provider backgroundHandlerProvider;
    private final Provider resolverProvider;

    public AnimationStatusRepositoryImpl_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.resolverProvider = provider;
        this.backgroundHandlerProvider = provider2;
        this.backgroundDispatcherProvider = provider3;
    }

    public static AnimationStatusRepositoryImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new AnimationStatusRepositoryImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static AnimationStatusRepositoryImpl newInstance(ContentResolver contentResolver, Handler handler, CoroutineDispatcher coroutineDispatcher) {
        return new AnimationStatusRepositoryImpl(contentResolver, handler, coroutineDispatcher);
    }

    public static AnimationStatusRepositoryImpl_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new AnimationStatusRepositoryImpl_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public AnimationStatusRepositoryImpl get() {
        return newInstance((ContentResolver) this.resolverProvider.get(), (Handler) this.backgroundHandlerProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get());
    }
}
