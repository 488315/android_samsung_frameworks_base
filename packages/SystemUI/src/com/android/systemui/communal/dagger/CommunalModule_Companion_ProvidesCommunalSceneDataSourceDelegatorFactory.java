package com.android.systemui.communal.dagger;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.scene.shared.model.SceneContainerConfig;
import com.android.systemui.scene.shared.model.SceneDataSourceDelegator;
import dagger.internal.Provider;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalModule_Companion_ProvidesCommunalSceneDataSourceDelegatorFactory implements Provider {
    public final javax.inject.Provider applicationScopeProvider;

    public CommunalModule_Companion_ProvidesCommunalSceneDataSourceDelegatorFactory(javax.inject.Provider provider) {
        this.applicationScopeProvider = provider;
    }

    public static SceneDataSourceDelegator providesCommunalSceneDataSourceDelegator(CoroutineScope coroutineScope) {
        CommunalModule.Companion.getClass();
        SceneKey sceneKey = CommunalScenes.Blank;
        SceneKey sceneKey2 = CommunalScenes.Communal;
        return new SceneDataSourceDelegator(coroutineScope, new SceneContainerConfig(CollectionsKt__CollectionsKt.listOf(sceneKey, sceneKey2), sceneKey, MapsKt__MapsKt.mapOf(new Pair(sceneKey, 0), new Pair(sceneKey2, 1))));
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesCommunalSceneDataSourceDelegator((CoroutineScope) this.applicationScopeProvider.get());
    }
}
