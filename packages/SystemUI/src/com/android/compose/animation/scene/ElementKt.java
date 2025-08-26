package com.android.compose.animation.scene;

import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableLongStateImpl;
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
import com.android.compose.animation.scene.transformation.SharedElementTransformation;
import com.android.compose.animation.scene.transformation.TransformationWithRange;
import com.android.compose.ui.graphics.DrawInContainerKt;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ElementKt {
    /* JADX WARN: Code restructure failed: missing block: B:40:0x009c, code lost:
    
        r6 = null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v12, types: [com.android.compose.animation.scene.content.state.TransitionState] */
    /* JADX WARN: Type inference failed for: r12v20, types: [com.android.compose.animation.scene.content.state.TransitionState$Transition] */
    /* JADX WARN: Type inference failed for: r12v22 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
            Element.State statePrepareInterruption$updateStateInContent = prepareInterruption$updateStateInContent(snapshotStateMap, transition3.fromContent);
            Element.State statePrepareInterruption$updateStateInContent2 = prepareInterruption$updateStateInContent(snapshotStateMap, transition3.toContent);
            Element.State statePrepareInterruption$updateStateInContent3 = prepareInterruption$updateStateInContent(snapshotStateMap, transition2.fromContent);
            Element.State statePrepareInterruption$updateStateInContent4 = prepareInterruption$updateStateInContent(snapshotStateMap, transition2.toContent);
            reconcileStates(element, transition2, reconcileStates(element, transition3, null));
            if (statePrepareInterruption$updateStateInContent != null) {
                prepareInterruption$cleanInterruptionValues(sceneTransitionLayoutImpl, element, transition2, statePrepareInterruption$updateStateInContent);
            }
            if (statePrepareInterruption$updateStateInContent2 != null) {
                prepareInterruption$cleanInterruptionValues(sceneTransitionLayoutImpl, element, transition2, statePrepareInterruption$updateStateInContent2);
            }
            if (statePrepareInterruption$updateStateInContent3 != null) {
                prepareInterruption$cleanInterruptionValues(sceneTransitionLayoutImpl, element, transition2, statePrepareInterruption$updateStateInContent3);
            }
            if (statePrepareInterruption$updateStateInContent4 != null) {
                prepareInterruption$cleanInterruptionValues(sceneTransitionLayoutImpl, element, transition2, statePrepareInterruption$updateStateInContent4);
                return transitionState;
            }
        }
        return transitionState;
    }

    public static final ContentKey access$getTransformationContentKey(boolean z, ContentKey contentKey, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, TransitionState.Transition transition, Element element, Element.State state) {
        if (!z) {
            boolean zIsAncestorTransition = isAncestorTransition(sceneTransitionLayoutImpl, transition);
            ContentKey contentKey2 = transition.toContent;
            ContentKey contentKey3 = transition.fromContent;
            if (zIsAncestorTransition) {
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
        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        List list = sceneTransitionLayoutImpl.ancestors;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            listBuilderCreateListBuilder.add(((Ancestor) list.get(i)).layoutImpl.state.getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
        }
        listBuilderCreateListBuilder.add(sceneTransitionLayoutImpl.state.getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
        final ListBuilder listBuilderBuild = listBuilderCreateListBuilder.build();
        if (sceneTransitionLayoutImpl.state.isElevationPossible$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(content.getKey(), elementKey)) {
            modifier = modifier.then(DrawInContainerKt.drawInContainer$default(Modifier.Companion, content.containerState, new Function0() { // from class: com.android.compose.animation.scene.ElementKt$$ExternalSyntheticLambda0
                /* JADX WARN: Code restructure failed: missing block: B:40:0x00a6, code lost:
                
                    r10 = null;
                 */
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:65:0x00f7  */
                /* JADX WARN: Removed duplicated region for block: B:77:0x0118  */
                /* JADX WARN: Type inference failed for: r3v13, types: [com.android.compose.animation.scene.content.state.TransitionState$Transition] */
                /* JADX WARN: Type inference failed for: r3v15 */
                /* JADX WARN: Type inference failed for: r3v5, types: [com.android.compose.animation.scene.content.state.TransitionState] */
                @Override // kotlin.jvm.functions.Function0
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke() {
                    TransitionState transitionState;
                    TransformationWithRange transformationWithRange;
                    SharedElementTransformation sharedElementTransformation;
                    SceneTransitionLayoutImpl sceneTransitionLayoutImpl2 = sceneTransitionLayoutImpl;
                    Map map = sceneTransitionLayoutImpl2.elements;
                    ElementKey elementKey2 = elementKey;
                    SnapshotStateMap snapshotStateMap = ((Element) MapsKt__MapsKt.getValue(elementKey2, map)).stateByContent;
                    ListBuilder listBuilder = listBuilderBuild;
                    int size2 = listBuilder.size();
                    boolean z = false;
                    int i2 = 0;
                    loop0: while (true) {
                        if (i2 >= size2) {
                            break;
                        }
                        List list2 = (List) listBuilder.get(i2);
                        if (i2 < listBuilder.getSize() - 1) {
                            int size3 = list2.size() - 1;
                            if (size3 >= 0) {
                                while (true) {
                                    int i3 = size3 - 1;
                                    transitionState = (TransitionState) list2.get(size3);
                                    if (transitionState instanceof TransitionState.Transition) {
                                        TransitionState.Transition transition = (TransitionState.Transition) transitionState;
                                        if (snapshotStateMap.containsKey(transition.fromContent) && snapshotStateMap.containsKey(transition.toContent)) {
                                            break loop0;
                                        }
                                    }
                                    if (ElementKt.access$hasTransformationForElement(transitionState, elementKey2)) {
                                        break loop0;
                                    }
                                    if (i3 < 0) {
                                        break;
                                    }
                                    size3 = i3;
                                }
                            }
                            i2++;
                        } else {
                            ?? r3 = (TransitionState) CollectionsKt___CollectionsKt.last(list2);
                            if (!(r3 instanceof TransitionState.Idle)) {
                                int size4 = list2.size() - 1;
                                if (size4 >= 0) {
                                    while (true) {
                                        int i4 = size4 - 1;
                                        r3 = (TransitionState.Transition) ((TransitionState) list2.get(size4));
                                        if (snapshotStateMap.containsKey(r3.fromContent) || snapshotStateMap.containsKey(r3.toContent)) {
                                            break;
                                        }
                                        if (i4 < 0) {
                                            break;
                                        }
                                        size4 = i4;
                                    }
                                }
                            } else if (list2.size() != 1) {
                                throw new IllegalStateException("Check failed.");
                            }
                            transitionState = r3;
                        }
                    }
                    if (transitionState instanceof TransitionState.Transition) {
                        TransitionState.Transition transition2 = (TransitionState.Transition) transitionState;
                        TransformationSpecImpl transformationSpecImpl = transition2.transformationSpec;
                        Content content2 = content;
                        ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transformationSpecImpl.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(content2.getKey(), elementKey2);
                        if (Intrinsics.areEqual((elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout == null || (transformationWithRange = elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.shared) == null || (sharedElementTransformation = (SharedElementTransformation) transformationWithRange.transformation) == null) ? null : sharedElementTransformation.elevateInContent, content2.getKey())) {
                            boolean z2 = transition2 instanceof TransitionState.Transition.ReplaceOverlay;
                            ContentKey contentKey = transition2.toContent;
                            ContentKey contentKey2 = transition2.fromContent;
                            if (z2) {
                                if (snapshotStateMap.containsKey(contentKey2)) {
                                    if (!snapshotStateMap.containsKey(contentKey)) {
                                        SceneKey sceneKey = ((TransitionState.Transition.OverlayTransition) transition2).currentSceneWhenTransitionStarted;
                                        if (sceneKey == null) {
                                            sceneKey = null;
                                        }
                                        if (!snapshotStateMap.containsKey(sceneKey)) {
                                        }
                                    }
                                    if (SharedElementKt.isSharedElementEnabled(elementKey2, transition2)) {
                                        z = true;
                                    }
                                } else if (snapshotStateMap.containsKey(contentKey)) {
                                    SceneKey sceneKey2 = ((TransitionState.Transition.OverlayTransition) transition2).currentSceneWhenTransitionStarted;
                                    if (snapshotStateMap.containsKey(sceneKey2 != null ? sceneKey2 : null)) {
                                        if (SharedElementKt.isSharedElementEnabled(elementKey2, transition2) && ElementKt.shouldPlaceElement(sceneTransitionLayoutImpl2, content2.getKey(), (Element) MapsKt__MapsKt.getValue(elementKey2, sceneTransitionLayoutImpl2.elements), transitionState)) {
                                            z = true;
                                        }
                                    }
                                }
                            } else if (snapshotStateMap.containsKey(contentKey2) && snapshotStateMap.containsKey(contentKey)) {
                            }
                        }
                    }
                    return Boolean.valueOf(z);
                }
            }));
        }
        Modifier modifierThen = modifier.then(new ElementModifier(sceneTransitionLayoutImpl, listBuilderBuild, content, elementKey));
        return sceneTransitionLayoutImpl.implicitTestTags ? modifierThen.then(TestTagKt.testTag(Modifier.Companion, elementKey.testTag)) : modifierThen;
    }

    public static final long getDrawScale$specifiedOrCenter(long j, LayoutNodeDrawScope layoutNodeDrawScope) {
        Offset offsetM395boximpl = Offset.m395boximpl(j);
        if ((j & 9223372034707292159L) == 9205357640488583168L) {
            offsetM395boximpl = null;
        }
        return offsetM395boximpl != null ? offsetM395boximpl.packedValue : layoutNodeDrawScope.canvasDrawScope.mo546getCenterF1C5BW0();
    }

    public static final float interruptedAlpha(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Element element, TransitionState.Transition transition, Element.State state, float f) {
        Element.Companion.getClass();
        Float fValueOf = Float.valueOf(Element.AlphaUnspecified);
        Float fValueOf2 = Float.valueOf(0.0f);
        Float fValueOf3 = Float.valueOf(state.alphaBeforeInterruption);
        if (!fValueOf3.equals(fValueOf)) {
            float fFloatValue = fValueOf3.floatValue() - f;
            state.alphaInterruptionDelta = fFloatValue;
            if (transition != null) {
                Object objLast = CollectionsKt___CollectionsKt.last(state.contents);
                ContentKey contentKey = transition.fromContent;
                if (Intrinsics.areEqual(objLast, contentKey)) {
                    contentKey = transition.toContent;
                }
                Element.State state2 = (Element.State) element.stateByContent.get(contentKey);
                if (state2 != null && SharedElementKt.isSharedElementEnabled(element.key, transition)) {
                    state2.alphaInterruptionDelta = fFloatValue;
                }
            }
            state.alphaBeforeInterruption = fValueOf.floatValue();
        }
        Float fValueOf4 = Float.valueOf(state.alphaInterruptionDelta);
        if (fValueOf4.equals(fValueOf2) || transition == null) {
            return f;
        }
        float fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transition.interruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(sceneTransitionLayoutImpl);
        return fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout == 0.0f ? f : (fValueOf4.floatValue() * fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout) + f;
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
                    if (Offset.m398equalsimpl0(j, Offset.Unspecified)) {
                        updateValuesBeforeInterruption(state2, state);
                    }
                }
                long j2 = state2.offsetBeforeInterruption;
                Offset.Companion.getClass();
                if (!Offset.m398equalsimpl0(j2, Offset.Unspecified)) {
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
        if (!Offset.m398equalsimpl0(j3, j4)) {
            long j5 = state3.offsetBeforeInterruption;
            companion.getClass();
            if (Offset.m398equalsimpl0(j5, j4)) {
                updateValuesBeforeInterruption(state3, state2);
                return state2;
            }
        }
        long j6 = state3.offsetBeforeInterruption;
        companion.getClass();
        if (!Offset.m398equalsimpl0(j6, j4)) {
            long j7 = state2.offsetBeforeInterruption;
            companion.getClass();
            if (Offset.m398equalsimpl0(j7, j4)) {
                updateValuesBeforeInterruption(state2, state3);
                return state3;
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0086, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r13, r0) == false) goto L36;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v6, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r15v20, types: [com.android.compose.animation.scene.SceneKey] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean shouldPlaceElement(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, ContentKey contentKey, Element element, TransitionState transitionState) {
        SharedElementTransformation sharedElementTransformation;
        if (!element.key.placeAllCopies) {
            boolean z = transitionState instanceof TransitionState.Idle;
            SnapshotStateMap snapshotStateMap = element.stateByContent;
            if (z) {
                TransitionState.Idle idle = (TransitionState.Idle) transitionState;
                Object obj = idle.currentScene;
                Set<OverlayKey> set = idle.currentOverlays;
                if (!set.isEmpty()) {
                    for (OverlayKey overlayKey : set) {
                        if (snapshotStateMap.containsKey(overlayKey) && (overlayKey == null || ((SnapshotMutableFloatStateImpl) sceneTransitionLayoutImpl.overlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(overlayKey).zIndex$delegate).getFloatValue() > ((SnapshotMutableFloatStateImpl) sceneTransitionLayoutImpl.overlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(overlayKey).zIndex$delegate).getFloatValue())) {
                            overlayKey = overlayKey;
                        }
                    }
                    if (overlayKey != null) {
                        obj = overlayKey;
                    }
                }
                return Intrinsics.areEqual(contentKey, obj);
            }
            if (!(transitionState instanceof TransitionState.Transition)) {
                throw new NoWhenBranchMatchedException();
            }
            TransitionState.Transition transition = (TransitionState.Transition) transitionState;
            boolean z2 = transition instanceof TransitionState.Transition.ReplaceOverlay;
            boolean zAreEqual = Intrinsics.areEqual(contentKey, transition.fromContent);
            ContentKey contentKey2 = transition.fromContent;
            ContentKey contentKey3 = transition.toContent;
            if (!zAreEqual && !Intrinsics.areEqual(contentKey, contentKey3)) {
                if (z2) {
                    SceneKey sceneKey = ((TransitionState.Transition.ReplaceOverlay) transition).currentSceneWhenTransitionStarted;
                    if (sceneKey == null) {
                        sceneKey = null;
                    }
                }
                List list = sceneTransitionLayoutImpl.ancestors;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Ancestor ancestor = (Ancestor) list.get(i);
                    if (!Intrinsics.areEqual(ancestor.inContent, contentKey2) && !Intrinsics.areEqual(ancestor.inContent, contentKey3)) {
                    }
                }
                return false;
            }
            ?? ContainsKey = snapshotStateMap.containsKey(contentKey2);
            int i2 = ContainsKey;
            if (snapshotStateMap.containsKey(contentKey3)) {
                i2 = ContainsKey + 1;
            }
            if (z2) {
                ?? r15 = ((TransitionState.Transition.ReplaceOverlay) transition).currentSceneWhenTransitionStarted;
                if (snapshotStateMap.containsKey(r15 != 0 ? r15 : null)) {
                    i2++;
                }
            }
            if (i2 > 1) {
                ElementKey elementKey = element.key;
                TransformationWithRange transformationWithRangeSharedElementTransformation = SharedElementKt.sharedElementTransformation(elementKey, transition);
                if (!((transformationWithRangeSharedElementTransformation == null || (sharedElementTransformation = (SharedElementTransformation) transformationWithRangeSharedElementTransformation.transformation) == null || sharedElementTransformation.enabled) ? false : true)) {
                    ContentKey contentKeyContentDuringTransition = elementKey.contentPicker.contentDuringTransition(transition, ((SnapshotMutableLongStateImpl) sceneTransitionLayoutImpl.content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKey2).globalZIndex$delegate).getLongValue(), ((SnapshotMutableLongStateImpl) sceneTransitionLayoutImpl.content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKey3).globalZIndex$delegate).getLongValue());
                    if (!Intrinsics.areEqual(contentKeyContentDuringTransition, contentKey)) {
                        List list2 = sceneTransitionLayoutImpl.ancestors;
                        int size2 = list2.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            if (!Intrinsics.areEqual(((Ancestor) list2.get(i3)).inContent, contentKeyContentDuringTransition)) {
                            }
                        }
                        return false;
                    }
                }
            }
        }
        return true;
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
