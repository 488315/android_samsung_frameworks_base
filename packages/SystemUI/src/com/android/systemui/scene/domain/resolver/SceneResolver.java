package com.android.systemui.scene.domain.resolver;

import com.android.compose.animation.scene.SceneKey;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface SceneResolver {
    StateFlow getResolvedScene();

    SceneKey getTargetFamily();

    boolean includesScene(SceneKey sceneKey);
}
