package com.android.compose.animation.scene;

import androidx.compose.ui.unit.Density;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PropertyTransformationScopeImpl implements Density {
    public final /* synthetic */ ElementStateScopeImpl $$delegate_0;
    public final SceneTransitionLayoutImpl layoutImpl;

    public PropertyTransformationScopeImpl(SceneTransitionLayoutImpl sceneTransitionLayoutImpl) {
        this.$$delegate_0 = sceneTransitionLayoutImpl.elementStateScope;
        this.layoutImpl = sceneTransitionLayoutImpl;
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return this.layoutImpl.density.getDensity();
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return this.layoutImpl.density.getFontScale();
    }
}
