package com.android.compose.animation.scene.content;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.OverscrollFactory;
import com.android.compose.animation.scene.effect.GestureEffect;

/* loaded from: classes.dex */
public final class ContentEffects {
    public final GestureEffect gestureEffect;
    public final OverscrollEffect overscrollEffect;

    public ContentEffects(OverscrollFactory overscrollFactory) {
        OverscrollEffect overscrollEffectCreateOverscrollEffect = overscrollFactory.createOverscrollEffect();
        this.overscrollEffect = overscrollEffectCreateOverscrollEffect;
        this.gestureEffect = new GestureEffect(overscrollEffectCreateOverscrollEffect);
    }
}
