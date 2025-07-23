package com.android.compose.animation.scene.content;

import androidx.compose.foundation.OverscrollFactory;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.SceneTransitionLayoutImpl;
import com.android.compose.animation.scene.UserAction;
import com.android.compose.animation.scene.UserActionResult;
import java.util.Map;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Scene extends Content {
    public final boolean alwaysCompose;
    public final SceneKey key;

    public Scene(SceneKey sceneKey, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Function3 function3, Map<UserAction.Resolved, ? extends UserActionResult> map, float f, long j, OverscrollFactory overscrollFactory, boolean z) {
        super(sceneKey, sceneTransitionLayoutImpl, function3, map, f, j, overscrollFactory, null);
        this.key = sceneKey;
        this.alwaysCompose = z;
    }

    @Override // com.android.compose.animation.scene.content.Content
    public final ContentKey getKey() {
        return this.key;
    }

    public final String toString() {
        return "Scene(key=" + this.key + ")";
    }
}
