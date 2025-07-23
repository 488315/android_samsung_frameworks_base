package com.android.compose.animation.scene.content;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.OverscrollFactory;
import com.android.compose.animation.scene.effect.GestureEffect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ContentEffects {
    public final GestureEffect gestureEffect;
    public final OverscrollEffect overscrollEffect;

    public ContentEffects(OverscrollFactory overscrollFactory) {
        OverscrollEffect createOverscrollEffect = overscrollFactory.createOverscrollEffect();
        this.overscrollEffect = createOverscrollEffect;
        this.gestureEffect = new GestureEffect(createOverscrollEffect);
    }
}
