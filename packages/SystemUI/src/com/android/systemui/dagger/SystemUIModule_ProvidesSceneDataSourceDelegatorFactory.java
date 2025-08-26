package com.android.systemui.dagger;

import com.android.systemui.scene.shared.model.SceneContainerConfig;
import com.android.systemui.scene.shared.model.SceneDataSourceDelegator;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class SystemUIModule_ProvidesSceneDataSourceDelegatorFactory implements Provider {
    public final Provider applicationScopeProvider;
    public final Provider configProvider;

    public SystemUIModule_ProvidesSceneDataSourceDelegatorFactory(Provider provider, Provider provider2) {
        this.applicationScopeProvider = provider;
        this.configProvider = provider2;
    }

    public static SceneDataSourceDelegator providesSceneDataSourceDelegator(CoroutineScope coroutineScope, SceneContainerConfig sceneContainerConfig) {
        return new SceneDataSourceDelegator(coroutineScope, sceneContainerConfig);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new SceneDataSourceDelegator((CoroutineScope) this.applicationScopeProvider.get(), (SceneContainerConfig) this.configProvider.get());
    }
}
