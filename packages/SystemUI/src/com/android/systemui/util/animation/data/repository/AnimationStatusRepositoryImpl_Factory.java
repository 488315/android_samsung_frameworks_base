package com.android.systemui.util.animation.data.repository;

import android.content.ContentResolver;
import android.os.Handler;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AnimationStatusRepositoryImpl_Factory implements Provider {
    private final javax.inject.Provider backgroundDispatcherProvider;
    private final javax.inject.Provider backgroundHandlerProvider;
    private final javax.inject.Provider resolverProvider;

    public AnimationStatusRepositoryImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.resolverProvider = provider;
        this.backgroundHandlerProvider = provider2;
        this.backgroundDispatcherProvider = provider3;
    }

    public static AnimationStatusRepositoryImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new AnimationStatusRepositoryImpl_Factory(provider, provider2, provider3);
    }

    public static AnimationStatusRepositoryImpl newInstance(ContentResolver contentResolver, Handler handler, CoroutineDispatcher coroutineDispatcher) {
        return new AnimationStatusRepositoryImpl(contentResolver, handler, coroutineDispatcher);
    }

    @Override // javax.inject.Provider
    public AnimationStatusRepositoryImpl get() {
        return newInstance((ContentResolver) this.resolverProvider.get(), (Handler) this.backgroundHandlerProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get());
    }
}
