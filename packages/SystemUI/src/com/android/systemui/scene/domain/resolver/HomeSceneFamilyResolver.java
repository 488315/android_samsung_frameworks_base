package com.android.systemui.scene.domain.resolver;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class HomeSceneFamilyResolver implements SceneResolver {
    public static final Set homeScenes;
    public final ReadonlyStateFlow resolvedScene;
    public final SceneKey targetFamily = SceneFamilies.Home;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        homeScenes = SetsKt__SetsKt.setOf(Scenes.Gone, Scenes.Lockscreen);
    }

    public HomeSceneFamilyResolver(CoroutineScope coroutineScope, DeviceEntryInteractor deviceEntryInteractor) {
        ReadonlyStateFlow readonlyStateFlow = deviceEntryInteractor.canSwipeToEnter;
        HomeSceneFamilyResolver$resolvedScene$1 homeSceneFamilyResolver$resolvedScene$1 = new HomeSceneFamilyResolver$resolvedScene$1(this);
        ReadonlyStateFlow readonlyStateFlow2 = deviceEntryInteractor.isDeviceEntered;
        ReadonlyStateFlow readonlyStateFlow3 = deviceEntryInteractor.isUnlocked;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(readonlyStateFlow, readonlyStateFlow2, readonlyStateFlow3, homeSceneFamilyResolver$resolvedScene$1);
        SharingStarted.Companion.getClass();
        this.resolvedScene = FlowKt.stateIn(combine, coroutineScope, SharingStarted.Companion.Eagerly, Intrinsics.areEqual((Boolean) readonlyStateFlow.$$delegate_0.getValue(), Boolean.TRUE) ? Scenes.Lockscreen : !((Boolean) readonlyStateFlow2.$$delegate_0.getValue()).booleanValue() ? Scenes.Lockscreen : !((Boolean) readonlyStateFlow3.$$delegate_0.getValue()).booleanValue() ? Scenes.Lockscreen : Scenes.Gone);
    }

    @Override // com.android.systemui.scene.domain.resolver.SceneResolver
    public final StateFlow getResolvedScene() {
        return this.resolvedScene;
    }

    @Override // com.android.systemui.scene.domain.resolver.SceneResolver
    public final SceneKey getTargetFamily() {
        return this.targetFamily;
    }

    @Override // com.android.systemui.scene.domain.resolver.SceneResolver
    public final boolean includesScene(SceneKey sceneKey) {
        return homeScenes.contains(sceneKey);
    }
}
