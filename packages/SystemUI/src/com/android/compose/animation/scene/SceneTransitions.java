package com.android.compose.animation.scene;

import androidx.compose.animation.core.AnimationSpec;
import com.android.compose.animation.scene.SceneTransitions;
import com.android.compose.animation.scene.TransformationSpec;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.animation.scene.transformation.TransformationMatcher;
import com.android.compose.animation.scene.transformation.TransformationRange;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SceneTransitions {
    public static final int $stable;
    public static final Companion Companion = null;
    public static final SceneTransitions Empty = null;
    public final InterruptionHandler interruptionHandler;
    public final Map transitionCache = new LinkedHashMap();
    public final List transitionSpecs;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        $stable = 8;
        new SceneTransitions(EmptyList.INSTANCE, DefaultInterruptionHandler.INSTANCE);
    }

    public SceneTransitions(List<TransitionSpecImpl> list, InterruptionHandler interruptionHandler) {
        this.transitionSpecs = list;
        this.interruptionHandler = interruptionHandler;
    }

    public final TransitionSpecImpl findSpec(final ContentKey contentKey, final ContentKey contentKey2, TransitionKey transitionKey) {
        final int i = 0;
        TransitionSpecImpl transition = transition(contentKey, contentKey2, transitionKey, new Function1() { // from class: com.android.compose.animation.scene.SceneTransitions$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z = false;
                ContentKey contentKey3 = contentKey2;
                ContentKey contentKey4 = contentKey;
                TransitionSpecImpl transitionSpecImpl = (TransitionSpecImpl) obj;
                switch (i) {
                    case 0:
                        SceneTransitions.Companion companion = SceneTransitions.Companion;
                        if (Intrinsics.areEqual(transitionSpecImpl.from, contentKey4) && Intrinsics.areEqual(transitionSpecImpl.to, contentKey3)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 1:
                        SceneTransitions.Companion companion2 = SceneTransitions.Companion;
                        if (Intrinsics.areEqual(transitionSpecImpl.from, contentKey4) && Intrinsics.areEqual(transitionSpecImpl.to, contentKey3)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 2:
                        SceneTransitions.Companion companion3 = SceneTransitions.Companion;
                        boolean areEqual = Intrinsics.areEqual(transitionSpecImpl.from, contentKey4);
                        ContentKey contentKey5 = transitionSpecImpl.to;
                        if ((areEqual && contentKey5 == null) || (Intrinsics.areEqual(contentKey5, contentKey3) && transitionSpecImpl.from == null)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        SceneTransitions.Companion companion4 = SceneTransitions.Companion;
                        boolean areEqual2 = Intrinsics.areEqual(transitionSpecImpl.from, contentKey4);
                        ContentKey contentKey6 = transitionSpecImpl.to;
                        if ((areEqual2 && contentKey6 == null) || (Intrinsics.areEqual(contentKey6, contentKey3) && transitionSpecImpl.from == null)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
        if (transition != null) {
            return transition;
        }
        final int i2 = 1;
        final TransitionSpecImpl transition2 = transition(contentKey, contentKey2, transitionKey, new Function1() { // from class: com.android.compose.animation.scene.SceneTransitions$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z = false;
                ContentKey contentKey3 = contentKey;
                ContentKey contentKey4 = contentKey2;
                TransitionSpecImpl transitionSpecImpl = (TransitionSpecImpl) obj;
                switch (i2) {
                    case 0:
                        SceneTransitions.Companion companion = SceneTransitions.Companion;
                        if (Intrinsics.areEqual(transitionSpecImpl.from, contentKey4) && Intrinsics.areEqual(transitionSpecImpl.to, contentKey3)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 1:
                        SceneTransitions.Companion companion2 = SceneTransitions.Companion;
                        if (Intrinsics.areEqual(transitionSpecImpl.from, contentKey4) && Intrinsics.areEqual(transitionSpecImpl.to, contentKey3)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 2:
                        SceneTransitions.Companion companion3 = SceneTransitions.Companion;
                        boolean areEqual = Intrinsics.areEqual(transitionSpecImpl.from, contentKey4);
                        ContentKey contentKey5 = transitionSpecImpl.to;
                        if ((areEqual && contentKey5 == null) || (Intrinsics.areEqual(contentKey5, contentKey3) && transitionSpecImpl.from == null)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        SceneTransitions.Companion companion4 = SceneTransitions.Companion;
                        boolean areEqual2 = Intrinsics.areEqual(transitionSpecImpl.from, contentKey4);
                        ContentKey contentKey6 = transitionSpecImpl.to;
                        if ((areEqual2 && contentKey6 == null) || (Intrinsics.areEqual(contentKey6, contentKey3) && transitionSpecImpl.from == null)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
        if (transition2 != null) {
            Function1 function1 = new Function1() { // from class: com.android.compose.animation.scene.TransitionSpecImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    TransformationRange transformationRange;
                    TransformationSpecImpl transformationSpecImpl = (TransformationSpecImpl) TransitionSpecImpl.this.transformationSpec.mo779invoke((TransitionState.Transition) obj);
                    AnimationSpec animationSpec = transformationSpecImpl.progressSpec;
                    List<TransformationMatcher> list = transformationSpecImpl.transformationMatchers;
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                    for (TransformationMatcher transformationMatcher : list) {
                        ElementMatcher elementMatcher = transformationMatcher.matcher;
                        TransformationRange transformationRange2 = transformationMatcher.range;
                        if (transformationRange2 != null) {
                            float f = transformationRange2.end;
                            float f2 = TransformationRange.isSpecified(f) ? 1.0f - f : Float.MIN_VALUE;
                            float f3 = transformationRange2.start;
                            transformationRange = new TransformationRange(f2, TransformationRange.isSpecified(f3) ? 1.0f - f3 : Float.MIN_VALUE, transformationRange2.easing);
                        } else {
                            transformationRange = null;
                        }
                        arrayList.add(new TransformationMatcher(elementMatcher, transformationMatcher.factory, transformationRange));
                    }
                    return new TransformationSpecImpl(animationSpec, transformationSpecImpl.distance, arrayList);
                }
            };
            return new TransitionSpecImpl(transition2.key, transition2.to, transition2.from, transition2.cuj, transition2.reversePreviewTransformationSpec, transition2.previewTransformationSpec, function1);
        }
        final int i3 = 2;
        TransitionSpecImpl transition3 = transition(contentKey, contentKey2, transitionKey, new Function1() { // from class: com.android.compose.animation.scene.SceneTransitions$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z = false;
                ContentKey contentKey3 = contentKey2;
                ContentKey contentKey4 = contentKey;
                TransitionSpecImpl transitionSpecImpl = (TransitionSpecImpl) obj;
                switch (i3) {
                    case 0:
                        SceneTransitions.Companion companion = SceneTransitions.Companion;
                        if (Intrinsics.areEqual(transitionSpecImpl.from, contentKey4) && Intrinsics.areEqual(transitionSpecImpl.to, contentKey3)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 1:
                        SceneTransitions.Companion companion2 = SceneTransitions.Companion;
                        if (Intrinsics.areEqual(transitionSpecImpl.from, contentKey4) && Intrinsics.areEqual(transitionSpecImpl.to, contentKey3)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 2:
                        SceneTransitions.Companion companion3 = SceneTransitions.Companion;
                        boolean areEqual = Intrinsics.areEqual(transitionSpecImpl.from, contentKey4);
                        ContentKey contentKey5 = transitionSpecImpl.to;
                        if ((areEqual && contentKey5 == null) || (Intrinsics.areEqual(contentKey5, contentKey3) && transitionSpecImpl.from == null)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        SceneTransitions.Companion companion4 = SceneTransitions.Companion;
                        boolean areEqual2 = Intrinsics.areEqual(transitionSpecImpl.from, contentKey4);
                        ContentKey contentKey6 = transitionSpecImpl.to;
                        if ((areEqual2 && contentKey6 == null) || (Intrinsics.areEqual(contentKey6, contentKey3) && transitionSpecImpl.from == null)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
        if (transition3 != null) {
            return transition3;
        }
        final int i4 = 3;
        final TransitionSpecImpl transition4 = transition(contentKey, contentKey2, transitionKey, new Function1() { // from class: com.android.compose.animation.scene.SceneTransitions$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z = false;
                ContentKey contentKey3 = contentKey;
                ContentKey contentKey4 = contentKey2;
                TransitionSpecImpl transitionSpecImpl = (TransitionSpecImpl) obj;
                switch (i4) {
                    case 0:
                        SceneTransitions.Companion companion = SceneTransitions.Companion;
                        if (Intrinsics.areEqual(transitionSpecImpl.from, contentKey4) && Intrinsics.areEqual(transitionSpecImpl.to, contentKey3)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 1:
                        SceneTransitions.Companion companion2 = SceneTransitions.Companion;
                        if (Intrinsics.areEqual(transitionSpecImpl.from, contentKey4) && Intrinsics.areEqual(transitionSpecImpl.to, contentKey3)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 2:
                        SceneTransitions.Companion companion3 = SceneTransitions.Companion;
                        boolean areEqual = Intrinsics.areEqual(transitionSpecImpl.from, contentKey4);
                        ContentKey contentKey5 = transitionSpecImpl.to;
                        if ((areEqual && contentKey5 == null) || (Intrinsics.areEqual(contentKey5, contentKey3) && transitionSpecImpl.from == null)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        SceneTransitions.Companion companion4 = SceneTransitions.Companion;
                        boolean areEqual2 = Intrinsics.areEqual(transitionSpecImpl.from, contentKey4);
                        ContentKey contentKey6 = transitionSpecImpl.to;
                        if ((areEqual2 && contentKey6 == null) || (Intrinsics.areEqual(contentKey6, contentKey3) && transitionSpecImpl.from == null)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
        if (transition4 != null) {
            Function1 function12 = new Function1() { // from class: com.android.compose.animation.scene.TransitionSpecImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    TransformationRange transformationRange;
                    TransformationSpecImpl transformationSpecImpl = (TransformationSpecImpl) TransitionSpecImpl.this.transformationSpec.mo779invoke((TransitionState.Transition) obj);
                    AnimationSpec animationSpec = transformationSpecImpl.progressSpec;
                    List<TransformationMatcher> list = transformationSpecImpl.transformationMatchers;
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                    for (TransformationMatcher transformationMatcher : list) {
                        ElementMatcher elementMatcher = transformationMatcher.matcher;
                        TransformationRange transformationRange2 = transformationMatcher.range;
                        if (transformationRange2 != null) {
                            float f = transformationRange2.end;
                            float f2 = TransformationRange.isSpecified(f) ? 1.0f - f : Float.MIN_VALUE;
                            float f3 = transformationRange2.start;
                            transformationRange = new TransformationRange(f2, TransformationRange.isSpecified(f3) ? 1.0f - f3 : Float.MIN_VALUE, transformationRange2.easing);
                        } else {
                            transformationRange = null;
                        }
                        arrayList.add(new TransformationMatcher(elementMatcher, transformationMatcher.factory, transformationRange));
                    }
                    return new TransformationSpecImpl(animationSpec, transformationSpecImpl.distance, arrayList);
                }
            };
            return new TransitionSpecImpl(transition4.key, transition4.to, transition4.from, transition4.cuj, transition4.reversePreviewTransformationSpec, transition4.previewTransformationSpec, function12);
        }
        if (transitionKey != null) {
            return findSpec(contentKey, contentKey2, null);
        }
        TransformationSpec.Companion.getClass();
        return new TransitionSpecImpl(null, contentKey, contentKey2, null, null, null, TransformationSpec.Companion.EmptyProvider);
    }

    public final TransitionSpecImpl transition(ContentKey contentKey, ContentKey contentKey2, TransitionKey transitionKey, Function1 function1) {
        List list = this.transitionSpecs;
        int size = list.size();
        TransitionSpecImpl transitionSpecImpl = null;
        for (int i = 0; i < size; i++) {
            TransitionSpecImpl transitionSpecImpl2 = (TransitionSpecImpl) list.get(i);
            if (Intrinsics.areEqual(transitionSpecImpl2.key, transitionKey) && ((Boolean) function1.mo779invoke(transitionSpecImpl2)).booleanValue()) {
                if (transitionSpecImpl != null) {
                    throw new IllegalStateException(("Found multiple transition specs for transition " + contentKey + " => " + contentKey2).toString());
                }
                transitionSpecImpl = transitionSpecImpl2;
            }
        }
        return transitionSpecImpl;
    }
}
