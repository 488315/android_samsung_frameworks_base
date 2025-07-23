package com.android.compose.animation.scene;

import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.systemui.samsung.quicksetting.ui.tiles.QuickTileDrawerKt$ExpandButton$2$1$1$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SceneTransitionsBuilderImpl {
    public final DefaultInterruptionHandler interruptionHandler = DefaultInterruptionHandler.INSTANCE;
    public final List transitionSpecs = new ArrayList();

    public static void from$default(SceneTransitionsBuilderImpl sceneTransitionsBuilderImpl, ContentKey contentKey, ContentKey contentKey2, TransitionKey transitionKey, Integer num, QuickTileDrawerKt$ExpandButton$2$1$1$$ExternalSyntheticLambda0 quickTileDrawerKt$ExpandButton$2$1$1$$ExternalSyntheticLambda0, Function1 function1, int i) {
        if ((i & 4) != 0) {
            transitionKey = null;
        }
        if ((i & 8) != 0) {
            num = null;
        }
        if ((i & 32) != 0) {
            quickTileDrawerKt$ExpandButton$2$1$1$$ExternalSyntheticLambda0 = null;
        }
        sceneTransitionsBuilderImpl.transition(contentKey, contentKey2, transitionKey, num, quickTileDrawerKt$ExpandButton$2$1$1$$ExternalSyntheticLambda0, function1);
    }

    public static final TransformationSpecImpl transition$transformationSpec(TransitionState.Transition transition, Function1 function1) {
        TransitionBuilderImpl transitionBuilderImpl = new TransitionBuilderImpl(transition);
        function1.mo779invoke(transitionBuilderImpl);
        return new TransformationSpecImpl(transitionBuilderImpl.spec, transitionBuilderImpl.distance, transitionBuilderImpl.transformationMatchers);
    }

    public final void transition(ContentKey contentKey, ContentKey contentKey2, TransitionKey transitionKey, Integer num, final Function1 function1, final Function1 function12) {
        Function1 function13;
        if (function1 != null) {
            final int i = 0;
            function13 = new Function1() { // from class: com.android.compose.animation.scene.SceneTransitionsBuilderImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    TransitionState.Transition transition = (TransitionState.Transition) obj;
                    switch (i) {
                    }
                    return SceneTransitionsBuilderImpl.transition$transformationSpec(transition, function1);
                }
            };
        } else {
            function13 = null;
        }
        final int i2 = 1;
        ((ArrayList) this.transitionSpecs).add(new TransitionSpecImpl(transitionKey, contentKey, contentKey2, num, null, function13, new Function1() { // from class: com.android.compose.animation.scene.SceneTransitionsBuilderImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                TransitionState.Transition transition = (TransitionState.Transition) obj;
                switch (i2) {
                }
                return SceneTransitionsBuilderImpl.transition$transformationSpec(transition, function12);
            }
        }));
    }
}
