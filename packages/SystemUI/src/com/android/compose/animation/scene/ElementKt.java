package com.android.compose.animation.scene;

import androidx.compose.runtime.snapshots.SnapshotStateMap;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.TestTagKt;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.Element;
import com.android.compose.animation.scene.content.Content;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.ui.graphics.DrawInContainerKt;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ElementKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v12, types: [com.android.compose.animation.scene.content.state.TransitionState] */
    /* JADX WARN: Type inference failed for: r12v20, types: [com.android.compose.animation.scene.content.state.TransitionState$Transition] */
    /* JADX WARN: Type inference failed for: r12v22 */
    public static final TransitionState access$elementState(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Element element, List list) {
        SnapshotStateMap snapshotStateMap;
        TransitionState transitionState;
        ElementKey elementKey = element.key;
        int size = list.size();
        int i = 0;
        loop0: while (true) {
            snapshotStateMap = element.stateByContent;
            if (i >= size) {
                break;
            }
            List list2 = (List) list.get(i);
            if (i < list.size() - 1) {
                int size2 = list2.size() - 1;
                if (size2 >= 0) {
                    while (true) {
                        int i2 = size2 - 1;
                        transitionState = (TransitionState) list2.get(size2);
                        if (transitionState instanceof TransitionState.Transition) {
                            TransitionState.Transition transition = (TransitionState.Transition) transitionState;
                            if (snapshotStateMap.containsKey(transition.fromContent) && snapshotStateMap.containsKey(transition.toContent)) {
                                break loop0;
                            }
                        }
                        if (access$hasTransformationForElement(transitionState, elementKey)) {
                            break loop0;
                        }
                        if (i2 < 0) {
                            break;
                        }
                        size2 = i2;
                    }
                }
                i++;
            } else {
                ?? r12 = (TransitionState) CollectionsKt___CollectionsKt.last(list2);
                if (!(r12 instanceof TransitionState.Idle)) {
                    int size3 = list2.size() - 1;
                    if (size3 >= 0) {
                        while (true) {
                            int i3 = size3 - 1;
                            r12 = (TransitionState.Transition) ((TransitionState) list2.get(size3));
                            if (snapshotStateMap.containsKey(r12.fromContent) || snapshotStateMap.containsKey(r12.toContent)) {
                                break;
                            }
                            if (i3 < 0) {
                                break;
                            }
                            size3 = i3;
                        }
                    }
                } else if (list2.size() != 1) {
                    throw new IllegalStateException("Check failed.");
                }
                transitionState = r12;
            }
        }
        transitionState = null;
        TransitionState.Transition transition2 = transitionState instanceof TransitionState.Transition ? (TransitionState.Transition) transitionState : null;
        TransitionState.Transition transition3 = element.lastTransition;
        element.lastTransition = transition2;
        if (Intrinsics.areEqual(transition2, transition3) || transition2 == null || transition3 == null) {
            if (transition2 == null && transition3 != null) {
                for (Element.State state : snapshotStateMap.values) {
                    Offset.Companion.getClass();
                    state.offsetBeforeInterruption = Offset.Unspecified;
                    Scale.Companion.getClass();
                    state.scaleBeforeInterruption = Scale.Unspecified;
                    Element.Companion.getClass();
                    state.alphaBeforeInterruption = Element.AlphaUnspecified;
                    clearInterruptionDeltas(state);
                }
            }
        } else if (!Intrinsics.areEqual(transition2.replacedTransition, transition3)) {
            Element.State prepareInterruption$updateStateInContent = prepareInterruption$updateStateInContent(snapshotStateMap, transition3.fromContent);
            Element.State prepareInterruption$updateStateInContent2 = prepareInterruption$updateStateInContent(snapshotStateMap, transition3.toContent);
            Element.State prepareInterruption$updateStateInContent3 = prepareInterruption$updateStateInContent(snapshotStateMap, transition2.fromContent);
            Element.State prepareInterruption$updateStateInContent4 = prepareInterruption$updateStateInContent(snapshotStateMap, transition2.toContent);
            reconcileStates(element, transition2, reconcileStates(element, transition3, null));
            if (prepareInterruption$updateStateInContent != null) {
                prepareInterruption$cleanInterruptionValues(sceneTransitionLayoutImpl, element, transition2, prepareInterruption$updateStateInContent);
            }
            if (prepareInterruption$updateStateInContent2 != null) {
                prepareInterruption$cleanInterruptionValues(sceneTransitionLayoutImpl, element, transition2, prepareInterruption$updateStateInContent2);
            }
            if (prepareInterruption$updateStateInContent3 != null) {
                prepareInterruption$cleanInterruptionValues(sceneTransitionLayoutImpl, element, transition2, prepareInterruption$updateStateInContent3);
            }
            if (prepareInterruption$updateStateInContent4 != null) {
                prepareInterruption$cleanInterruptionValues(sceneTransitionLayoutImpl, element, transition2, prepareInterruption$updateStateInContent4);
                return transitionState;
            }
        }
        return transitionState;
    }

    public static final ContentKey access$getTransformationContentKey(boolean z, ContentKey contentKey, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, TransitionState.Transition transition, Element element, Element.State state) {
        if (!z) {
            boolean isAncestorTransition = isAncestorTransition(sceneTransitionLayoutImpl, transition);
            ContentKey contentKey2 = transition.toContent;
            ContentKey contentKey3 = transition.fromContent;
            if (isAncestorTransition) {
                Object obj = element.stateByContent.get(contentKey3);
                ElementKey elementKey = element.key;
                if (obj != null && transition.transformationSpec.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKey3, elementKey) != null) {
                    return contentKey3;
                }
                if (element.stateByContent.get(contentKey2) == null || transition.transformationSpec.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKey2, elementKey) == null) {
                    throw new IllegalStateException("Ancestor transition is active but no transformation spec was found. The ancestor transition should have only been selected when a transformation for that element and content was defined.");
                }
                return contentKey2;
            }
            if (state == null || !Intrinsics.areEqual(contentKey, transition.getCurrentScene())) {
                return element.stateByContent.get(contentKey3) != null ? contentKey3 : contentKey2;
            }
        }
        return contentKey;
    }

    public static final boolean access$hasTransformationForElement(TransitionState transitionState, ElementKey elementKey) {
        if (!(transitionState instanceof TransitionState.Transition)) {
            return false;
        }
        TransitionState.Transition transition = (TransitionState.Transition) transitionState;
        return (transition.transformationSpec.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(transition.fromContent, elementKey) == null && transition.transformationSpec.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(transition.toContent, elementKey) == null) ? false : true;
    }

    public static final boolean access$isEnteringAncestorTransition(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, TransitionState.Transition transition) {
        List list = sceneTransitionLayoutImpl.ancestors;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (Intrinsics.areEqual(((Ancestor) list.get(i)).inContent, transition.toContent)) {
                return true;
            }
        }
        return false;
    }

    public static final void clearInterruptionDeltas(Element.State state) {
        Offset.Companion.getClass();
        state.offsetInterruptionDelta = 0L;
        IntSize.Companion.getClass();
        state.sizeInterruptionDelta = 0L;
        Scale.Companion.getClass();
        state.scaleInterruptionDelta = Scale.Zero;
        state.alphaInterruptionDelta = 0.0f;
    }

    public static final Modifier element(Modifier modifier, final SceneTransitionLayoutImpl sceneTransitionLayoutImpl, final Content content, final ElementKey elementKey) {
        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        List list = sceneTransitionLayoutImpl.ancestors;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            createListBuilder.add(((Ancestor) list.get(i)).layoutImpl.state.getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
        }
        createListBuilder.add(sceneTransitionLayoutImpl.state.getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
        final ListBuilder build = createListBuilder.build();
        if (sceneTransitionLayoutImpl.state.isElevationPossible$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(content.getKey(), elementKey)) {
            modifier = modifier.then(DrawInContainerKt.drawInContainer$default(Modifier.Companion, content.containerState, new Function0() { // from class: com.android.compose.animation.scene.ElementKt$$ExternalSyntheticLambda0
                /* JADX WARN: Code restructure failed: missing block: B:36:0x00f5, code lost:
                
                    if (r1.containsKey(r4) == false) goto L65;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:48:0x0109, code lost:
                
                    if (r1.containsKey(r4 != null ? r4 : null) != false) goto L77;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:52:0x0116, code lost:
                
                    if (r1.containsKey(r6) != false) goto L77;
                 */
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r3v13, types: [com.android.compose.animation.scene.content.state.TransitionState$Transition] */
                /* JADX WARN: Type inference failed for: r3v15 */
                /* JADX WARN: Type inference failed for: r3v5, types: [com.android.compose.animation.scene.content.state.TransitionState] */
                @Override // kotlin.jvm.functions.Function0
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke() {
                    /*
                        Method dump skipped, instructions count: 310
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.ElementKt$$ExternalSyntheticLambda0.invoke():java.lang.Object");
                }
            }));
        }
        Modifier then = modifier.then(new ElementModifier(sceneTransitionLayoutImpl, build, content, elementKey));
        return sceneTransitionLayoutImpl.implicitTestTags ? then.then(TestTagKt.testTag(Modifier.Companion, elementKey.testTag)) : then;
    }

    public static final long getDrawScale$specifiedOrCenter(long j, LayoutNodeDrawScope layoutNodeDrawScope) {
        Offset m393boximpl = Offset.m393boximpl(j);
        if ((j & 9223372034707292159L) == 9205357640488583168L) {
            m393boximpl = null;
        }
        return m393boximpl != null ? m393boximpl.packedValue : layoutNodeDrawScope.canvasDrawScope.mo544getCenterF1C5BW0();
    }

    public static final float interruptedAlpha(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Element element, TransitionState.Transition transition, Element.State state, float f) {
        Element.Companion.getClass();
        Float valueOf = Float.valueOf(Element.AlphaUnspecified);
        Float valueOf2 = Float.valueOf(0.0f);
        Float valueOf3 = Float.valueOf(state.alphaBeforeInterruption);
        if (!valueOf3.equals(valueOf)) {
            float floatValue = valueOf3.floatValue() - f;
            state.alphaInterruptionDelta = floatValue;
            if (transition != null) {
                Object last = CollectionsKt___CollectionsKt.last(state.contents);
                ContentKey contentKey = transition.fromContent;
                if (Intrinsics.areEqual(last, contentKey)) {
                    contentKey = transition.toContent;
                }
                Element.State state2 = (Element.State) element.stateByContent.get(contentKey);
                if (state2 != null && SharedElementKt.isSharedElementEnabled(element.key, transition)) {
                    state2.alphaInterruptionDelta = floatValue;
                }
            }
            state.alphaBeforeInterruption = valueOf.floatValue();
        }
        Float valueOf4 = Float.valueOf(state.alphaInterruptionDelta);
        if (valueOf4.equals(valueOf2) || transition == null) {
            return f;
        }
        float interruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transition.interruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(sceneTransitionLayoutImpl);
        return interruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout == 0.0f ? f : (valueOf4.floatValue() * interruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout) + f;
    }

    public static final boolean isAncestorTransition(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, TransitionState.Transition transition) {
        List list = sceneTransitionLayoutImpl.ancestors;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Ancestor ancestor = (Ancestor) list.get(i);
            if (Intrinsics.areEqual(ancestor.inContent, transition.fromContent) || Intrinsics.areEqual(ancestor.inContent, transition.toContent)) {
                return true;
            }
        }
        return false;
    }

    public static final void prepareInterruption$cleanInterruptionValues(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Element element, TransitionState.Transition transition, Element.State state) {
        IntSize.Companion.getClass();
        state.sizeInterruptionDelta = 0L;
        Offset.Companion.getClass();
        state.offsetInterruptionDelta = 0L;
        state.alphaInterruptionDelta = 0.0f;
        Scale.Companion.getClass();
        state.scaleInterruptionDelta = Scale.Zero;
        if (shouldPlaceElement(sceneTransitionLayoutImpl, (ContentKey) CollectionsKt___CollectionsKt.last(state.contents), element, transition)) {
            return;
        }
        state.offsetBeforeInterruption = Offset.Unspecified;
        Element.Companion.getClass();
        state.alphaBeforeInterruption = Element.AlphaUnspecified;
        state.scaleBeforeInterruption = Scale.Unspecified;
    }

    public static final Element.State prepareInterruption$updateStateInContent(SnapshotStateMap snapshotStateMap, ContentKey contentKey) {
        Element.State state = (Element.State) snapshotStateMap.get(contentKey);
        if (state == null) {
            return null;
        }
        state.sizeBeforeInterruption = state.lastSize;
        float f = state.lastAlpha;
        if (f > 0.0f) {
            state.offsetBeforeInterruption = state.lastOffset;
            state.scaleBeforeInterruption = state.lastScale;
            state.alphaBeforeInterruption = f;
            return state;
        }
        Offset.Companion.getClass();
        state.offsetBeforeInterruption = Offset.Unspecified;
        Scale.Companion.getClass();
        state.scaleBeforeInterruption = Scale.Unspecified;
        Element.Companion.getClass();
        state.alphaBeforeInterruption = Element.AlphaUnspecified;
        return state;
    }

    public static final Element.State reconcileStates(Element element, TransitionState.Transition transition, Element.State state) {
        Element.State state2 = (Element.State) element.stateByContent.get(transition.fromContent);
        Element.State state3 = (Element.State) element.stateByContent.get(transition.toContent);
        if (state2 == null || state3 == null) {
            if (state2 == null) {
                state2 = state3;
            }
            if (state2 != null) {
                if (state != null) {
                    long j = state2.offsetBeforeInterruption;
                    Offset.Companion.getClass();
                    if (Offset.m396equalsimpl0(j, Offset.Unspecified)) {
                        updateValuesBeforeInterruption(state2, state);
                    }
                }
                long j2 = state2.offsetBeforeInterruption;
                Offset.Companion.getClass();
                if (!Offset.m396equalsimpl0(j2, Offset.Unspecified)) {
                    return state2;
                }
            }
            return null;
        }
        if (!SharedElementKt.isSharedElementEnabled(element.key, transition)) {
            return null;
        }
        long j3 = state2.offsetBeforeInterruption;
        Offset.Companion companion = Offset.Companion;
        companion.getClass();
        long j4 = Offset.Unspecified;
        if (!Offset.m396equalsimpl0(j3, j4)) {
            long j5 = state3.offsetBeforeInterruption;
            companion.getClass();
            if (Offset.m396equalsimpl0(j5, j4)) {
                updateValuesBeforeInterruption(state3, state2);
                return state2;
            }
        }
        long j6 = state3.offsetBeforeInterruption;
        companion.getClass();
        if (!Offset.m396equalsimpl0(j6, j4)) {
            long j7 = state2.offsetBeforeInterruption;
            companion.getClass();
            if (Offset.m396equalsimpl0(j7, j4)) {
                updateValuesBeforeInterruption(state2, state3);
                return state3;
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0086, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r13, r0) == false) goto L36;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v6, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r15v20, types: [com.android.compose.animation.scene.SceneKey] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean shouldPlaceElement(com.android.compose.animation.scene.SceneTransitionLayoutImpl r12, com.android.compose.animation.scene.ContentKey r13, com.android.compose.animation.scene.Element r14, com.android.compose.animation.scene.content.state.TransitionState r15) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.ElementKt.shouldPlaceElement(com.android.compose.animation.scene.SceneTransitionLayoutImpl, com.android.compose.animation.scene.ContentKey, com.android.compose.animation.scene.Element, com.android.compose.animation.scene.content.state.TransitionState):boolean");
    }

    public static final long size(Placeable placeable) {
        long j = (placeable.width << 32) | (placeable.height & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j;
    }

    public static final void updateValuesBeforeInterruption(Element.State state, Element.State state2) {
        state.offsetBeforeInterruption = state2.offsetBeforeInterruption;
        state.sizeBeforeInterruption = state2.sizeBeforeInterruption;
        state.scaleBeforeInterruption = state2.scaleBeforeInterruption;
        state.alphaBeforeInterruption = state2.alphaBeforeInterruption;
        clearInterruptionDeltas(state);
    }
}
