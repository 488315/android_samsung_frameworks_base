package com.android.compose.animation.scene;

import androidx.compose.ui.unit.Density;

/* loaded from: classes.dex */
public final class UserActionDistanceScopeImpl implements Density {
    public final /* synthetic */ ElementStateScopeImpl $$delegate_0;
    public final SceneTransitionLayoutImpl layoutImpl;

    public UserActionDistanceScopeImpl(SceneTransitionLayoutImpl sceneTransitionLayoutImpl) {
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
