package com.android.systemui.scene.ui.viewmodel;

import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.ui.composable.ComposableScene;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SceneContainerViewModel {
    public final ReadonlyStateFlow currentScene;
    public final Map destinationScenesBySceneKey;
    public final FalsingInteractor falsingInteractor;
    public final ReadonlyStateFlow isVisible;
    public final PowerInteractor powerInteractor;
    public final SceneInteractor sceneInteractor;

    public SceneContainerViewModel(SceneInteractor sceneInteractor, FalsingInteractor falsingInteractor, PowerInteractor powerInteractor, Set<ComposableScene> set) {
        this.sceneInteractor = sceneInteractor;
        List list = sceneInteractor.repository.config.sceneKeys;
        ReadonlyStateFlow readonlyStateFlow = sceneInteractor.currentScene;
        ReadonlyStateFlow readonlyStateFlow2 = sceneInteractor.isVisible;
        Set<ComposableScene> set2 = set;
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity < 16 ? 16 : mapCapacity);
        for (ComposableScene composableScene : set2) {
            Pair pair = new Pair(composableScene.getKey(), LatestConflatedKt.flatMapLatestConflated(composableScene.getDestinationScenes(), new SceneContainerViewModel$destinationScenesBySceneKey$1$1(this, null)));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
    }
}
