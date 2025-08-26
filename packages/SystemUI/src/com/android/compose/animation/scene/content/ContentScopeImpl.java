package com.android.compose.animation.scene.content;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.ElementKt;
import com.android.compose.animation.scene.ElementStateScopeImpl;
import com.android.compose.animation.scene.MovableElementKt;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutState;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl;
import com.android.compose.animation.scene.SceneTransitionLayoutImpl;
import com.android.compose.gesture.NestedScrollControlState;

/* loaded from: classes.dex */
public final class ContentScopeImpl implements ContentScope {
    public final /* synthetic */ ElementStateScopeImpl $$delegate_0;
    public final Content content;
    public final SceneTransitionLayoutImpl layoutImpl;
    public final MutableSceneTransitionLayoutStateImpl layoutState;
    public final NestedScrollControlState nestedScrollControlState;

    public ContentScopeImpl(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Content content, NestedScrollControlState nestedScrollControlState) {
        this.$$delegate_0 = sceneTransitionLayoutImpl.elementStateScope;
        this.layoutImpl = sceneTransitionLayoutImpl;
        this.content = content;
        this.nestedScrollControlState = nestedScrollControlState;
        this.layoutState = sceneTransitionLayoutImpl.state;
    }

    @Override // com.android.compose.animation.scene.ContentScope
    public final void Element(ElementKey elementKey, Modifier modifier, ComposableLambdaImpl composableLambdaImpl, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-683388083);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.compose.animation.scene.content.ContentScopeImpl.Element (Content.kt:299)");
        }
        MovableElementKt.Element(this.layoutImpl, this.content, elementKey, modifier, composableLambdaImpl, composerImpl, (i << 6) & 65408);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }

    @Override // com.android.compose.animation.scene.ContentScope
    public final Modifier element(Modifier modifier, ElementKey elementKey) {
        return ElementKt.element(modifier, this.layoutImpl, this.content, elementKey);
    }

    @Override // com.android.compose.animation.scene.ContentScope
    public final MutableSceneTransitionLayoutState getLayoutState() {
        return this.layoutState;
    }
}
