package com.android.systemui.communal.dagger;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.ui.compose.CommunalContainerKt;
import com.android.systemui.scene.shared.model.SceneContainerConfig;
import com.android.systemui.scene.shared.model.SceneDataSourceDelegator;
import com.android.systemui.scene.ui.composable.ConstantSceneContainerTransitionsBuilder;
import dagger.internal.Provider;
import java.util.Arrays;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class CommunalModule_Companion_ProvidesCommunalSceneDataSourceDelegatorFactory implements Provider {
    public final Provider applicationScopeProvider;

    public CommunalModule_Companion_ProvidesCommunalSceneDataSourceDelegatorFactory(Provider provider) {
        this.applicationScopeProvider = provider;
    }

    public static SceneDataSourceDelegator providesCommunalSceneDataSourceDelegator(CoroutineScope coroutineScope) {
        CommunalModule.Companion.getClass();
        SceneKey sceneKey = CommunalScenes.Blank;
        SceneKey sceneKey2 = CommunalScenes.Communal;
        return new SceneDataSourceDelegator(coroutineScope, new SceneContainerConfig(Arrays.asList(sceneKey, sceneKey2), sceneKey, null, MapsKt__MapsKt.mapOf(new Pair(sceneKey, 0), new Pair(sceneKey2, 1)), new ConstantSceneContainerTransitionsBuilder(CommunalContainerKt.sceneTransitions), 4, null));
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesCommunalSceneDataSourceDelegator((CoroutineScope) this.applicationScopeProvider.get());
    }
}
