package com.android.compose.animation.scene;

import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.snapshots.SnapshotStateMap;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.graphics.CompositingStrategy;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.layout.ApproachLayoutModifierNode;
import androidx.compose.ui.layout.ApproachMeasureScope;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LookaheadScope;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNodeKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.Element;
import com.android.compose.animation.scene.content.Content;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.animation.scene.transformation.CustomPropertyTransformation;
import com.android.compose.animation.scene.transformation.InterpolatedPropertyTransformation;
import com.android.compose.animation.scene.transformation.PropertyTransformation;
import com.android.compose.animation.scene.transformation.TransformationRange;
import com.android.compose.animation.scene.transformation.TransformationWithRange;
import com.android.compose.ui.util.MathHelpersKt;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes.dex */
public final class ElementNode extends Modifier.Node implements DrawModifierNode, ApproachLayoutModifierNode, TraversableNode {
    public static final Companion Companion = new Companion(null);
    public static final Object ElementTraverseKey = new Object();
    public Element _element;
    public final Content content;
    public List currentTransitionStates;
    public ElementKey key;
    public final SceneTransitionLayoutImpl layoutImpl;
    public final Object traverseKey = ElementTraverseKey;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void access$maybePruneMaps(Companion companion, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Element element, Element.State state) {
            companion.getClass();
            List list = state.contents;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ContentKey contentKey = (ContentKey) list.get(i);
                if (state.nodes.isEmpty() && Intrinsics.areEqual(element.stateByContent.get(contentKey), state)) {
                    SnapshotStateMap snapshotStateMap = element.stateByContent;
                    snapshotStateMap.remove(contentKey);
                    if (snapshotStateMap.isEmpty()) {
                        Map map = sceneTransitionLayoutImpl.elements;
                        ElementKey elementKey = element.key;
                        if (Intrinsics.areEqual(map.get(elementKey), element)) {
                            sceneTransitionLayoutImpl.elements.remove(elementKey);
                        }
                    }
                }
            }
        }

        private Companion() {
        }
    }

    public ElementNode(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, List<? extends List<? extends TransitionState>> list, Content content, ElementKey elementKey) {
        this.layoutImpl = sceneTransitionLayoutImpl;
        this.currentTransitionStates = list;
        this.content = content;
        this.key = elementKey;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0309  */
    /* JADX WARN: Type inference failed for: r7v31, types: [com.android.compose.animation.scene.content.state.TransitionState] */
    /* JADX WARN: Type inference failed for: r7v36 */
    /* JADX WARN: Type inference failed for: r7v40, types: [com.android.compose.animation.scene.content.state.TransitionState$Transition] */
    /* JADX WARN: Type inference failed for: r7v42 */
    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    /* renamed from: approachMeasure-3p2s80s */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final MeasureResult mo605approachMeasure3p2s80s(ApproachMeasureScope approachMeasureScope, Measurable measurable, long j) {
        Element.State state;
        boolean z;
        Element element;
        ContentKey contentKey;
        ContentKey contentKey2;
        TransitionState.Transition transition;
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl;
        Element.State state2;
        TransitionState transitionState;
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl2;
        Element.State state3;
        Object objTransform;
        final Placeable placeableMo610measureBRTryo0;
        Object objTransform2;
        Object obj;
        Object objTransform3;
        long j2;
        long jM942lerpe0twbBA;
        TransformationRange transformationRange;
        long j3;
        ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout;
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl3;
        Object obj2;
        Object objM861boximpl;
        Object obj3;
        Object objM861boximpl2;
        Element element2 = this._element;
        element2.getClass();
        List list = this.currentTransitionStates;
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl4 = this.layoutImpl;
        TransitionState transitionStateAccess$elementState = ElementKt.access$elementState(sceneTransitionLayoutImpl4, element2, list);
        if (transitionStateAccess$elementState == null) {
            TransitionState transitionState2 = (TransitionState) CollectionsKt___CollectionsKt.last((List) CollectionsKt___CollectionsKt.last(this.currentTransitionStates));
            Element.State stateInContent = getStateInContent();
            Element.Companion.getClass();
            ((SnapshotMutableStateImpl) stateInContent.approachSize$delegate).setValue(IntSize.m861boximpl(Element.SizeUnspecified));
            Object currentScene = transitionState2.getCurrentScene();
            Set<OverlayKey> currentOverlays = transitionState2.getCurrentOverlays();
            if (!currentOverlays.isEmpty()) {
                OverlayKey overlayKey = null;
                for (OverlayKey overlayKey2 : currentOverlays) {
                    Element element3 = this._element;
                    element3.getClass();
                    if (element3.stateByContent.containsKey(overlayKey2) && (overlayKey == null || ((SnapshotMutableFloatStateImpl) sceneTransitionLayoutImpl4.overlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(overlayKey2).zIndex$delegate).getFloatValue() > ((SnapshotMutableFloatStateImpl) sceneTransitionLayoutImpl4.overlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(overlayKey).zIndex$delegate).getFloatValue())) {
                        overlayKey = overlayKey2;
                    }
                }
                if (overlayKey != null) {
                    currentScene = overlayKey;
                }
            }
            if (Intrinsics.areEqual(currentScene, this.content.getKey())) {
                Placeable placeableMo610measureBRTryo02 = measurable.mo610measureBRTryo0(j);
                getStateInContent().lastSize = ElementKt.size(placeableMo610measureBRTryo02);
                return approachMeasureScope.layout$1(placeableMo610measureBRTryo02.width, placeableMo610measureBRTryo02.height, MapsKt__MapsKt.emptyMap(), new ElementNode$$ExternalSyntheticLambda0(placeableMo610measureBRTryo02, this, 1));
            }
            Element.State stateInContent2 = getStateInContent();
            Offset.Companion.getClass();
            stateInContent2.lastOffset = Offset.Unspecified;
            Scale.Companion.getClass();
            stateInContent2.lastScale = Scale.Unspecified;
            Element.Companion companion = Element.Companion;
            companion.getClass();
            stateInContent2.lastAlpha = Element.AlphaUnspecified;
            TraversableNodeKt.traverseDescendants(this, ElementTraverseKey, new ElementNode$$ExternalSyntheticLambda2(1));
            Element.State stateInContent3 = getStateInContent();
            companion.getClass();
            stateInContent3.lastSize = Element.SizeUnspecified;
            Placeable placeableMo610measureBRTryo03 = measurable.mo610measureBRTryo0(j);
            return approachMeasureScope.layout$1(placeableMo610measureBRTryo03.width, placeableMo610measureBRTryo03.height, MapsKt__MapsKt.emptyMap(), new ElementNode$$ExternalSyntheticLambda2(0));
        }
        Ancestor ancestor = (Ancestor) CollectionsKt___CollectionsKt.lastOrNull(sceneTransitionLayoutImpl4.ancestors);
        boolean zAreEqual = true;
        if (ancestor != null) {
            List list2 = (List) CollectionsKt___CollectionsKt.last(this.currentTransitionStates);
            ?? r7 = (TransitionState) CollectionsKt___CollectionsKt.last(list2);
            if (!(r7 instanceof TransitionState.Idle)) {
                int size = list2.size() - 1;
                if (size >= 0) {
                    while (true) {
                        int i = size - 1;
                        r7 = (TransitionState.Transition) ((TransitionState) list2.get(size));
                        ContentKey contentKey3 = r7.fromContent;
                        Element element4 = this._element;
                        element4.getClass();
                        if (element4.stateByContent.containsKey(contentKey3)) {
                            break;
                        }
                        Element element5 = this._element;
                        element5.getClass();
                        if (element5.stateByContent.containsKey(r7.toContent)) {
                            break;
                        }
                        if (i < 0) {
                            break;
                        }
                        size = i;
                    }
                    r7 = 0;
                } else {
                    r7 = 0;
                }
            } else if (list2.size() != 1) {
                throw new IllegalStateException("Check failed.");
            }
            boolean z2 = r7 instanceof TransitionState.Idle;
            ContentKey contentKey4 = ancestor.inContent;
            if (z2) {
                assignState(contentKey4, ((TransitionState.Idle) r7).currentScene);
            } else if (r7 instanceof TransitionState.Transition.ChangeScene) {
                assignState(contentKey4, ((TransitionState.Transition.ChangeScene) r7).getCurrentScene());
            } else if (r7 instanceof TransitionState.Transition.ReplaceOverlay) {
                assignState(contentKey4, ((TransitionState.Transition.ReplaceOverlay) r7).getEffectivelyShownOverlay());
            } else if (r7 instanceof TransitionState.Transition.ShowOrHideOverlay) {
                TransitionState.Transition.ShowOrHideOverlay showOrHideOverlay = (TransitionState.Transition.ShowOrHideOverlay) r7;
                if (showOrHideOverlay.isEffectivelyShown()) {
                    assignState(contentKey4, showOrHideOverlay.overlay);
                } else {
                    assignState(contentKey4, showOrHideOverlay.fromOrToScene);
                }
            } else if (r7 != 0) {
                throw new NoWhenBranchMatchedException();
            }
        }
        TransitionState.Transition transition2 = transitionStateAccess$elementState instanceof TransitionState.Transition ? (TransitionState.Transition) transitionStateAccess$elementState : null;
        Element element6 = this._element;
        element6.getClass();
        Element.State stateInContent4 = getStateInContent();
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl5 = this.layoutImpl;
        if (transition2 == null) {
            placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
            transitionState = transitionStateAccess$elementState;
            sceneTransitionLayoutImpl2 = sceneTransitionLayoutImpl5;
            state3 = stateInContent4;
            objM861boximpl2 = IntSize.m861boximpl(ElementKt.size(placeableMo610measureBRTryo0));
        } else {
            SnapshotStateMap snapshotStateMap = element6.stateByContent;
            ContentKey contentKey5 = transition2.fromContent;
            Element.State state4 = (Element.State) snapshotStateMap.get(contentKey5);
            ContentKey contentKey6 = transition2.toContent;
            Element.State state5 = (Element.State) snapshotStateMap.get(contentKey6);
            if (state4 == null && state5 == null) {
                objM861boximpl = IntSize.m861boximpl(stateInContent4.m923getTargetSizeYbymL2g());
            } else {
                ContentKey contentKey7 = (ContentKey) CollectionsKt___CollectionsKt.last(stateInContent4.contents);
                if (state4 == null || state5 == null) {
                    state = state4;
                    z = false;
                } else {
                    state = state4;
                    z = true;
                }
                ElementKey elementKey = element6.key;
                if (z && SharedElementKt.isSharedElementEnabled(elementKey, transition2)) {
                    state.getClass();
                    state5.getClass();
                    IntSize intSizeM861boximpl = IntSize.m861boximpl(state.m923getTargetSizeYbymL2g());
                    IntSize intSizeM861boximpl2 = IntSize.m861boximpl(state5.m923getTargetSizeYbymL2g());
                    Element.Companion.getClass();
                    long j4 = Element.SizeUnspecified;
                    long j5 = intSizeM861boximpl.packedValue;
                    objM861boximpl = intSizeM861boximpl2;
                    if (!IntSize.m863equalsimpl0(j5, j4)) {
                        long j6 = intSizeM861boximpl2.packedValue;
                        if (!IntSize.m863equalsimpl0(j6, j4) && !IntSize.m862equalsimpl(j5, intSizeM861boximpl2)) {
                            intSizeM861boximpl = IntSize.m861boximpl(MathHelpersKt.m942lerpe0twbBA(j5, j6, transition2.getProgress()));
                        }
                        objM861boximpl = intSizeM861boximpl;
                    }
                } else {
                    if (z || !(transition2 instanceof TransitionState.Transition.ReplaceOverlay)) {
                        TransitionState.Transition transition3 = transition2;
                        element = element6;
                        contentKey = contentKey6;
                        contentKey2 = contentKey7;
                        transition = transition3;
                        sceneTransitionLayoutImpl = sceneTransitionLayoutImpl5;
                        state2 = null;
                    } else {
                        SceneKey sceneKey = ((TransitionState.Transition.ReplaceOverlay) transition2).currentSceneWhenTransitionStarted;
                        if (sceneKey == null) {
                            sceneKey = null;
                        }
                        Element.State state6 = (Element.State) snapshotStateMap.get(sceneKey);
                        if (state6 == null || !SharedElementKt.isSharedElementEnabled(elementKey, transition2)) {
                            TransitionState.Transition transition4 = transition2;
                            element = element6;
                            contentKey = contentKey6;
                            contentKey2 = contentKey7;
                            transition = transition4;
                            sceneTransitionLayoutImpl = sceneTransitionLayoutImpl5;
                            state2 = state6;
                        } else {
                            Element.State state7 = state == null ? state6 : state;
                            if (state5 == null) {
                                state5 = state6;
                            }
                            IntSize intSizeM861boximpl3 = IntSize.m861boximpl(state7.m923getTargetSizeYbymL2g());
                            IntSize intSizeM861boximpl4 = IntSize.m861boximpl(state5.m923getTargetSizeYbymL2g());
                            Element.Companion.getClass();
                            long j7 = Element.SizeUnspecified;
                            long j8 = intSizeM861boximpl3.packedValue;
                            if (IntSize.m863equalsimpl0(j8, j7)) {
                                sceneTransitionLayoutImpl3 = sceneTransitionLayoutImpl5;
                                obj2 = intSizeM861boximpl4;
                            } else {
                                sceneTransitionLayoutImpl3 = sceneTransitionLayoutImpl5;
                                long j9 = intSizeM861boximpl4.packedValue;
                                if (!IntSize.m863equalsimpl0(j9, j7) && !IntSize.m862equalsimpl(j8, intSizeM861boximpl4)) {
                                    intSizeM861boximpl3 = IntSize.m861boximpl(MathHelpersKt.m942lerpe0twbBA(j8, j9, transition2.getProgress()));
                                }
                                obj2 = intSizeM861boximpl3;
                            }
                            transitionState = transitionStateAccess$elementState;
                            sceneTransitionLayoutImpl2 = sceneTransitionLayoutImpl3;
                            obj3 = obj2;
                            state3 = stateInContent4;
                            objTransform2 = obj3;
                            placeableMo610measureBRTryo0 = null;
                            objM861boximpl2 = objTransform2;
                        }
                    }
                    ContentKey contentKeyAccess$getTransformationContentKey = ElementKt.access$getTransformationContentKey(z, contentKey2, sceneTransitionLayoutImpl, transition, element, state2);
                    SceneTransitionLayoutImpl sceneTransitionLayoutImpl6 = sceneTransitionLayoutImpl;
                    Element element7 = element;
                    transition2 = transition;
                    IntSize intSizeM861boximpl5 = IntSize.m861boximpl(((Element.State) MapsKt__MapsKt.getValue(contentKeyAccess$getTransformationContentKey, snapshotStateMap)).m923getTargetSizeYbymL2g());
                    ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 = transition2.transformationSpec.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKeyAccess$getTransformationContentKey, elementKey);
                    TransformationWithRange transformationWithRange = elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 != null ? elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2.size : null;
                    if (!Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey, contentKey)) {
                        if (!Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey, contentKey5)) {
                            if (ElementKt.isAncestorTransition(sceneTransitionLayoutImpl6, transition2)) {
                                zAreEqual = ElementKt.access$isEnteringAncestorTransition(sceneTransitionLayoutImpl6, transition2);
                            } else if (!Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey, transition2.getCurrentScene())) {
                                zAreEqual = Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey, contentKey);
                            } else if (state5 != null) {
                                zAreEqual = false;
                            }
                        }
                    }
                    TransformationSpecImpl transformationSpecImpl = transition2.previewTransformationSpec;
                    TransformationWithRange transformationWithRange2 = (transformationSpecImpl == null || (elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transformationSpecImpl.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKeyAccess$getTransformationContentKey, elementKey)) == null) ? null : elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.size;
                    long j10 = intSizeM861boximpl5.packedValue;
                    if (transformationWithRange2 != null) {
                        boolean zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transition2.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
                        PropertyTransformation propertyTransformation = (PropertyTransformation) transformationWithRange2.transformation;
                        boolean z3 = zAreEqual;
                        transitionState = transitionStateAccess$elementState;
                        state3 = stateInContent4;
                        if (!(propertyTransformation instanceof InterpolatedPropertyTransformation)) {
                            if (!(propertyTransformation instanceof CustomPropertyTransformation)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            throw new IllegalStateException(("Custom transformations in preview specs should not be possible (element=" + elementKey.debugName + " fromContent=" + contentKey5 + " toContent=" + contentKey + ")").toString());
                        }
                        TransformationWithRange transformationWithRange3 = transformationWithRange2;
                        sceneTransitionLayoutImpl2 = sceneTransitionLayoutImpl6;
                        Object objTransform4 = ((InterpolatedPropertyTransformation) propertyTransformation).transform(sceneTransitionLayoutImpl6.propertyTransformationScope, contentKeyAccess$getTransformationContentKey, element7.key, transition2, intSizeM861boximpl5);
                        if (transformationWithRange != null) {
                            PropertyTransformation propertyTransformation2 = (PropertyTransformation) transformationWithRange.transformation;
                            if (!(propertyTransformation2 instanceof InterpolatedPropertyTransformation)) {
                                if (!(propertyTransformation2 instanceof CustomPropertyTransformation)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                throw new IllegalStateException(("Custom transformations are not allowed for properties with a preview (element=" + elementKey.debugName + " fromContent=" + contentKey5 + " toContent=" + contentKey + ")").toString());
                            }
                            InterpolatedPropertyTransformation interpolatedPropertyTransformation = (InterpolatedPropertyTransformation) propertyTransformation2;
                            PropertyTransformationScopeImpl propertyTransformationScopeImpl = sceneTransitionLayoutImpl2.propertyTransformationScope;
                            ElementKey elementKey2 = element7.key;
                            obj = objTransform4;
                            objTransform3 = interpolatedPropertyTransformation.transform(propertyTransformationScopeImpl, contentKeyAccess$getTransformationContentKey, elementKey2, transition2, intSizeM861boximpl5);
                        } else {
                            obj = objTransform4;
                            objTransform3 = null;
                        }
                        if (zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout && z3 && obj.equals(objTransform3)) {
                            objTransform2 = obj;
                        } else {
                            if (!zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout || z3) {
                                j2 = j10;
                            } else {
                                j2 = j10;
                                if (!IntSize.m862equalsimpl(j2, obj)) {
                                }
                                objTransform2 = intSizeM861boximpl5;
                            }
                            if (!obj.equals(objTransform3) || !IntSize.m862equalsimpl(j2, obj)) {
                                float previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transition2.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
                                TransformationRange transformationRange2 = transformationWithRange3.range;
                                if (transformationRange2 != null) {
                                    previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transformationRange2.progress(previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
                                }
                                if (zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout) {
                                    if (z3) {
                                        if (objTransform3 != null) {
                                            intSizeM861boximpl5 = objTransform3;
                                        }
                                        j3 = intSizeM861boximpl5.packedValue;
                                        j2 = ((IntSize) obj).packedValue;
                                    } else {
                                        j3 = ((IntSize) obj).packedValue;
                                    }
                                    jM942lerpe0twbBA = MathHelpersKt.m942lerpe0twbBA(j2, j3, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
                                } else if (z3) {
                                    if (objTransform3 != null) {
                                        intSizeM861boximpl5 = objTransform3;
                                    }
                                    jM942lerpe0twbBA = MathHelpersKt.m942lerpe0twbBA(MathHelpersKt.m942lerpe0twbBA(((IntSize) obj).packedValue, intSizeM861boximpl5.packedValue, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout), j2, (transformationWithRange == null || (transformationRange = transformationWithRange.range) == null) ? transition2.getProgress() : transformationRange.progress(transition2.getProgress()));
                                } else if (objTransform3 == null) {
                                    jM942lerpe0twbBA = MathHelpersKt.m942lerpe0twbBA(j2, ((IntSize) obj).packedValue, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
                                } else {
                                    long jM942lerpe0twbBA2 = MathHelpersKt.m942lerpe0twbBA(j2, ((IntSize) obj).packedValue, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
                                    TransformationRange transformationRange3 = transformationWithRange.range;
                                    jM942lerpe0twbBA = MathHelpersKt.m942lerpe0twbBA(jM942lerpe0twbBA2, ((IntSize) objTransform3).packedValue, transformationRange3 != null ? transformationRange3.progress(transition2.getProgress()) : transition2.getProgress());
                                }
                                objTransform = IntSize.m861boximpl(jM942lerpe0twbBA);
                                objTransform2 = objTransform;
                            }
                            objTransform2 = intSizeM861boximpl5;
                        }
                        placeableMo610measureBRTryo0 = null;
                        objM861boximpl2 = objTransform2;
                    } else {
                        transitionState = transitionStateAccess$elementState;
                        boolean z4 = zAreEqual;
                        sceneTransitionLayoutImpl2 = sceneTransitionLayoutImpl6;
                        state3 = stateInContent4;
                        if (transformationWithRange == null) {
                            placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
                            objM861boximpl2 = IntSize.m861boximpl(ElementKt.size(placeableMo610measureBRTryo0));
                        } else {
                            PropertyTransformation propertyTransformation3 = (PropertyTransformation) transformationWithRange.transformation;
                            if (propertyTransformation3 instanceof CustomPropertyTransformation) {
                                objTransform2 = ((CustomPropertyTransformation) propertyTransformation3).transform(sceneTransitionLayoutImpl2.propertyTransformationScope, contentKeyAccess$getTransformationContentKey, element7.key, transition2, transition2.getCoroutineScope$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                placeableMo610measureBRTryo0 = null;
                                objM861boximpl2 = objTransform2;
                            } else {
                                if (!(propertyTransformation3 instanceof InterpolatedPropertyTransformation)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                objTransform = ((InterpolatedPropertyTransformation) propertyTransformation3).transform(sceneTransitionLayoutImpl2.propertyTransformationScope, contentKeyAccess$getTransformationContentKey, element7.key, transition2, intSizeM861boximpl5);
                                if (!objTransform.equals(intSizeM861boximpl5)) {
                                    float progress = transition2.getProgress();
                                    TransformationRange transformationRange4 = transformationWithRange.range;
                                    if (transformationRange4 != null) {
                                        progress = transformationRange4.progress(progress);
                                    }
                                    objTransform = IntSize.m861boximpl(z4 ? MathHelpersKt.m942lerpe0twbBA(((IntSize) objTransform).packedValue, j10, progress) : MathHelpersKt.m942lerpe0twbBA(j10, ((IntSize) objTransform).packedValue, progress));
                                }
                                objTransform2 = objTransform;
                                placeableMo610measureBRTryo0 = null;
                                objM861boximpl2 = objTransform2;
                            }
                        }
                    }
                }
            }
            transitionState = transitionStateAccess$elementState;
            sceneTransitionLayoutImpl2 = sceneTransitionLayoutImpl5;
            obj3 = objM861boximpl;
            state3 = stateInContent4;
            objTransform2 = obj3;
            placeableMo610measureBRTryo0 = null;
            objM861boximpl2 = objTransform2;
        }
        IntSize intSize = (IntSize) objM861boximpl2;
        if (placeableMo610measureBRTryo0 != null) {
            Element.Companion.getClass();
            long j11 = Element.SizeUnspecified;
            Element.State state8 = state3;
            state8.sizeBeforeInterruption = j11;
            IntSize.Companion.getClass();
            state8.sizeInterruptionDelta = 0L;
            ((SnapshotMutableStateImpl) state8.approachSize$delegate).setValue(IntSize.m861boximpl(j11));
        } else {
            Element.State state9 = state3;
            Element.Companion.getClass();
            IntSize intSizeM861boximpl6 = IntSize.m861boximpl(Element.SizeUnspecified);
            IntSize.Companion.getClass();
            IntSize intSizeM861boximpl7 = IntSize.m861boximpl(0L);
            boolean zM862equalsimpl = IntSize.m862equalsimpl(IntSize.m861boximpl(state9.sizeBeforeInterruption).packedValue, intSizeM861boximpl6);
            long jRoundToInt = intSize.packedValue;
            if (!zM862equalsimpl) {
                state9.sizeInterruptionDelta = ((((int) (r7 & 4294967295L)) - ((int) (jRoundToInt & 4294967295L))) & 4294967295L) | ((((int) (r7 >> 32)) - ((int) (jRoundToInt >> 32))) << 32);
                state9.sizeBeforeInterruption = intSizeM861boximpl6.packedValue;
            }
            if (!IntSize.m862equalsimpl(IntSize.m861boximpl(state9.sizeInterruptionDelta).packedValue, intSizeM861boximpl7) && transition2 != null) {
                if (transition2.interruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(sceneTransitionLayoutImpl2) != 0.0f) {
                    jRoundToInt = (MathKt__MathJVMKt.roundToInt((((int) (r2 >> 32)) * r0) + ((int) (jRoundToInt >> 32))) << 32) | (MathKt__MathJVMKt.roundToInt((((int) (r2 & 4294967295L)) * r0) + ((int) (jRoundToInt & 4294967295L))) & 4294967295L);
                }
            }
            ((SnapshotMutableStateImpl) state9.approachSize$delegate).setValue(IntSize.m861boximpl(jRoundToInt));
            Constraints.Companion companion2 = Constraints.Companion;
            int i2 = (int) (jRoundToInt >> 32);
            if (i2 < 0) {
                i2 = 0;
            }
            int i3 = (int) (jRoundToInt & 4294967295L);
            int i4 = i3 < 0 ? 0 : i3;
            companion2.getClass();
            placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(Constraints.Companion.m829fixedJhjzzOo(i2, i4));
        }
        getStateInContent().lastSize = ElementKt.size(placeableMo610measureBRTryo0);
        final TransitionState transitionState3 = transitionState;
        return approachMeasureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.animation.scene.ElementNode$$ExternalSyntheticLambda1
            /* JADX WARN: Removed duplicated region for block: B:159:0x0365  */
            /* JADX WARN: Removed duplicated region for block: B:179:0x03eb  */
            /* JADX WARN: Removed duplicated region for block: B:192:0x042f  */
            /* JADX WARN: Removed duplicated region for block: B:196:0x0443  */
            /* JADX WARN: Removed duplicated region for block: B:204:0x0478  */
            /* JADX WARN: Removed duplicated region for block: B:219:0x04ae  */
            /* JADX WARN: Removed duplicated region for block: B:222:0x04cd  */
            /* JADX WARN: Removed duplicated region for block: B:75:0x01b7  */
            /* JADX WARN: Removed duplicated region for block: B:78:0x01c0  */
            /* JADX WARN: Removed duplicated region for block: B:94:0x01f1  */
            /* JADX WARN: Removed duplicated region for block: B:97:0x01f7  */
            /* JADX WARN: Type inference failed for: r6v6, types: [com.android.compose.animation.scene.ElementNode$$ExternalSyntheticLambda3] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj4) {
                long j12;
                boolean z5;
                long j13;
                Content content;
                Element.State state10;
                boolean zAreEqual2;
                boolean z6;
                TransformationWithRange transformationWithRange4;
                Placeable.PlacementScope placementScope;
                ElementNode elementNode;
                SceneTransitionLayoutImpl sceneTransitionLayoutImpl7;
                long jM406lerpWko1d7g;
                Object objM395boximpl;
                Object objTransform5;
                Object objTransform6;
                TransformationRange transformationRange5;
                ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3;
                Offset offsetM395boximpl;
                Offset offsetM395boximpl2;
                Offset offsetM395boximpl3;
                boolean zEquals;
                final ElementNode elementNode2;
                Offset offsetM395boximpl4;
                SceneTransitionLayoutImpl sceneTransitionLayoutImpl8;
                Offset offsetM395boximpl5;
                Placeable.PlacementScope placementScope2 = (Placeable.PlacementScope) obj4;
                ElementNode elementNode3 = this.f$0;
                SceneTransitionLayoutImpl sceneTransitionLayoutImpl9 = elementNode3.layoutImpl;
                LookaheadScope lookaheadScope = sceneTransitionLayoutImpl9._lookaheadScope;
                lookaheadScope.getClass();
                LayoutCoordinates coordinates = placementScope2.getCoordinates();
                if (coordinates == null) {
                    Element element8 = elementNode3._element;
                    element8.getClass();
                    throw new IllegalStateException(("Element " + element8.key + " does not have any coordinates").toString());
                }
                Content content2 = elementNode3.content;
                ContentKey key = content2.getKey();
                Element element9 = elementNode3._element;
                element9.getClass();
                TransitionState transitionState4 = transitionState3;
                if (ElementKt.shouldPlaceElement(sceneTransitionLayoutImpl9, key, element9, transitionState4)) {
                    TransitionState.Transition transition5 = transitionState4 instanceof TransitionState.Transition ? (TransitionState.Transition) transitionState4 : null;
                    LayoutCoordinates lookaheadScopeCoordinates = lookaheadScope.getLookaheadScopeCoordinates();
                    Offset.Companion.getClass();
                    long jMo613localPositionOfR5De75A = lookaheadScopeCoordinates.mo613localPositionOfR5De75A(coordinates, 0L);
                    Element.State stateInContent5 = elementNode3.getStateInContent();
                    Element element10 = elementNode3._element;
                    element10.getClass();
                    if (transition5 == null) {
                        offsetM395boximpl5 = Offset.m395boximpl(jMo613localPositionOfR5De75A);
                    } else {
                        SnapshotStateMap snapshotStateMap2 = element10.stateByContent;
                        ContentKey contentKey8 = transition5.fromContent;
                        Element.State state11 = (Element.State) snapshotStateMap2.get(contentKey8);
                        ContentKey contentKey9 = transition5.toContent;
                        Element.State state12 = (Element.State) snapshotStateMap2.get(contentKey9);
                        if (state11 == null && state12 == null) {
                            offsetM395boximpl5 = Offset.m395boximpl(stateInContent5.m922getTargetOffsetF1C5BW0());
                        } else {
                            ContentKey contentKey10 = (ContentKey) CollectionsKt___CollectionsKt.last(stateInContent5.contents);
                            if (state11 == null || state12 == null) {
                                j12 = 0;
                                z5 = false;
                            } else {
                                j12 = 0;
                                z5 = true;
                            }
                            ElementKey elementKey3 = element10.key;
                            if (z5 && SharedElementKt.isSharedElementEnabled(elementKey3, transition5)) {
                                state11.getClass();
                                state12.getClass();
                                Offset offsetM395boximpl6 = Offset.m395boximpl(state11.m922getTargetOffsetF1C5BW0());
                                Offset offsetM395boximpl7 = Offset.m395boximpl(state12.m922getTargetOffsetF1C5BW0());
                                long j14 = Offset.Unspecified;
                                long j15 = offsetM395boximpl6.packedValue;
                                if (Offset.m398equalsimpl0(j15, j14)) {
                                    j13 = jMo613localPositionOfR5De75A;
                                    offsetM395boximpl2 = offsetM395boximpl7;
                                } else {
                                    j13 = jMo613localPositionOfR5De75A;
                                    long j16 = offsetM395boximpl7.packedValue;
                                    offsetM395boximpl2 = offsetM395boximpl6;
                                    if (!Offset.m398equalsimpl0(j16, j14)) {
                                        boolean zEquals2 = offsetM395boximpl6.equals(offsetM395boximpl7);
                                        offsetM395boximpl2 = offsetM395boximpl6;
                                        if (!zEquals2) {
                                            offsetM395boximpl2 = Offset.m395boximpl(OffsetKt.m406lerpWko1d7g(j15, j16, transition5.getProgress()));
                                        }
                                    }
                                }
                                placementScope = placementScope2;
                                elementNode = elementNode3;
                                sceneTransitionLayoutImpl7 = sceneTransitionLayoutImpl9;
                                content = content2;
                                objTransform5 = offsetM395boximpl2;
                            } else {
                                j13 = jMo613localPositionOfR5De75A;
                                if (z5 || !(transition5 instanceof TransitionState.Transition.ReplaceOverlay)) {
                                    content = content2;
                                    state10 = null;
                                } else {
                                    SceneKey sceneKey2 = ((TransitionState.Transition.ReplaceOverlay) transition5).currentSceneWhenTransitionStarted;
                                    if (sceneKey2 == null) {
                                        sceneKey2 = null;
                                    }
                                    Element.State state13 = (Element.State) snapshotStateMap2.get(sceneKey2);
                                    if (state13 == null || !SharedElementKt.isSharedElementEnabled(elementKey3, transition5)) {
                                        content = content2;
                                        state10 = state13;
                                    } else {
                                        if (state11 == null) {
                                            state11 = state13;
                                        }
                                        if (state12 == null) {
                                            state12 = state13;
                                        }
                                        Offset offsetM395boximpl8 = Offset.m395boximpl(state11.m922getTargetOffsetF1C5BW0());
                                        Offset offsetM395boximpl9 = Offset.m395boximpl(state12.m922getTargetOffsetF1C5BW0());
                                        long j17 = Offset.Unspecified;
                                        long j18 = offsetM395boximpl8.packedValue;
                                        if (Offset.m398equalsimpl0(j18, j17)) {
                                            offsetM395boximpl = offsetM395boximpl9;
                                            content = content2;
                                        } else {
                                            content = content2;
                                            long j19 = offsetM395boximpl9.packedValue;
                                            offsetM395boximpl = offsetM395boximpl8;
                                            if (!Offset.m398equalsimpl0(j19, j17)) {
                                                boolean zEquals3 = offsetM395boximpl8.equals(offsetM395boximpl9);
                                                offsetM395boximpl = offsetM395boximpl8;
                                                if (!zEquals3) {
                                                    offsetM395boximpl = Offset.m395boximpl(OffsetKt.m406lerpWko1d7g(j18, j19, transition5.getProgress()));
                                                }
                                            }
                                        }
                                        placementScope = placementScope2;
                                        elementNode = elementNode3;
                                        sceneTransitionLayoutImpl7 = sceneTransitionLayoutImpl9;
                                        objTransform5 = offsetM395boximpl;
                                    }
                                }
                                SceneTransitionLayoutImpl sceneTransitionLayoutImpl10 = elementNode3.layoutImpl;
                                TransitionState.Transition transition6 = transition5;
                                ContentKey contentKeyAccess$getTransformationContentKey2 = ElementKt.access$getTransformationContentKey(z5, contentKey10, sceneTransitionLayoutImpl10, transition6, element10, state10);
                                transition5 = transition6;
                                Offset offsetM395boximpl10 = Offset.m395boximpl(((Element.State) MapsKt__MapsKt.getValue(contentKeyAccess$getTransformationContentKey2, snapshotStateMap2)).m922getTargetOffsetF1C5BW0());
                                ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout4 = transition5.transformationSpec.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKeyAccess$getTransformationContentKey2, elementKey3);
                                TransformationWithRange transformationWithRange5 = elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout4 != null ? elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout4.offset : null;
                                if (!Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey2, contentKey9)) {
                                    if (!Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey2, contentKey8)) {
                                        if (ElementKt.isAncestorTransition(sceneTransitionLayoutImpl10, transition5)) {
                                            zAreEqual2 = ElementKt.access$isEnteringAncestorTransition(sceneTransitionLayoutImpl10, transition5);
                                        } else if (Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey2, transition5.getCurrentScene())) {
                                            z6 = state12 == null;
                                            TransformationSpecImpl transformationSpecImpl2 = transition5.previewTransformationSpec;
                                            transformationWithRange4 = (transformationSpecImpl2 != null || (elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3 = transformationSpecImpl2.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKeyAccess$getTransformationContentKey2, elementKey3)) == null) ? null : elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3.offset;
                                            long j20 = offsetM395boximpl10.packedValue;
                                            if (transformationWithRange4 == null) {
                                                boolean zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 = transition5.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
                                                PropertyTransformation propertyTransformation4 = (PropertyTransformation) transformationWithRange4.transformation;
                                                placementScope = placementScope2;
                                                if (!(propertyTransformation4 instanceof InterpolatedPropertyTransformation)) {
                                                    if (!(propertyTransformation4 instanceof CustomPropertyTransformation)) {
                                                        throw new NoWhenBranchMatchedException();
                                                    }
                                                    throw new IllegalStateException(("Custom transformations in preview specs should not be possible (element=" + elementKey3.debugName + " fromContent=" + contentKey8 + " toContent=" + contentKey9 + ")").toString());
                                                }
                                                TransformationWithRange transformationWithRange6 = transformationWithRange5;
                                                elementNode = elementNode3;
                                                TransformationWithRange transformationWithRange7 = transformationWithRange4;
                                                sceneTransitionLayoutImpl7 = sceneTransitionLayoutImpl9;
                                                Object objTransform7 = ((InterpolatedPropertyTransformation) propertyTransformation4).transform(sceneTransitionLayoutImpl10.propertyTransformationScope, contentKeyAccess$getTransformationContentKey2, element10.key, transition5, offsetM395boximpl10);
                                                if (transformationWithRange6 != null) {
                                                    PropertyTransformation propertyTransformation5 = (PropertyTransformation) transformationWithRange6.transformation;
                                                    if (!(propertyTransformation5 instanceof InterpolatedPropertyTransformation)) {
                                                        if (!(propertyTransformation5 instanceof CustomPropertyTransformation)) {
                                                            throw new NoWhenBranchMatchedException();
                                                        }
                                                        throw new IllegalStateException(("Custom transformations are not allowed for properties with a preview (element=" + elementKey3.debugName + " fromContent=" + contentKey8 + " toContent=" + contentKey9 + ")").toString());
                                                    }
                                                    objM395boximpl = objTransform7;
                                                    objTransform6 = ((InterpolatedPropertyTransformation) propertyTransformation5).transform(sceneTransitionLayoutImpl10.propertyTransformationScope, contentKeyAccess$getTransformationContentKey2, element10.key, transition5, offsetM395boximpl10);
                                                } else {
                                                    objM395boximpl = objTransform7;
                                                    objTransform6 = null;
                                                }
                                                if (zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 && z6 && objM395boximpl.equals(objTransform6)) {
                                                    objTransform5 = objM395boximpl;
                                                } else if ((zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 && !z6 && offsetM395boximpl10.equals(objM395boximpl)) || (objM395boximpl.equals(objTransform6) && offsetM395boximpl10.equals(objM395boximpl))) {
                                                    objTransform5 = offsetM395boximpl10;
                                                } else {
                                                    float previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 = transition5.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
                                                    TransformationRange transformationRange6 = transformationWithRange7.range;
                                                    if (transformationRange6 != null) {
                                                        previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 = transformationRange6.progress(previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2);
                                                    }
                                                    if (zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2) {
                                                        if (z6) {
                                                            if (objTransform6 == null) {
                                                                objTransform6 = offsetM395boximpl10;
                                                            }
                                                            jM406lerpWko1d7g = OffsetKt.m406lerpWko1d7g(((Offset) objM395boximpl).packedValue, ((Offset) objTransform6).packedValue, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2);
                                                        } else {
                                                            jM406lerpWko1d7g = OffsetKt.m406lerpWko1d7g(j20, ((Offset) objM395boximpl).packedValue, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2);
                                                        }
                                                    } else if (z6) {
                                                        if (objTransform6 == null) {
                                                            objTransform6 = offsetM395boximpl10;
                                                        }
                                                        jM406lerpWko1d7g = OffsetKt.m406lerpWko1d7g(OffsetKt.m406lerpWko1d7g(((Offset) objM395boximpl).packedValue, ((Offset) objTransform6).packedValue, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2), j20, (transformationWithRange6 == null || (transformationRange5 = transformationWithRange6.range) == null) ? transition5.getProgress() : transformationRange5.progress(transition5.getProgress()));
                                                    } else if (objTransform6 == null) {
                                                        jM406lerpWko1d7g = OffsetKt.m406lerpWko1d7g(j20, ((Offset) objM395boximpl).packedValue, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2);
                                                    } else {
                                                        long jM406lerpWko1d7g2 = OffsetKt.m406lerpWko1d7g(j20, ((Offset) objM395boximpl).packedValue, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2);
                                                        TransformationRange transformationRange7 = transformationWithRange6.range;
                                                        jM406lerpWko1d7g = OffsetKt.m406lerpWko1d7g(jM406lerpWko1d7g2, ((Offset) objTransform6).packedValue, transformationRange7 != null ? transformationRange7.progress(transition5.getProgress()) : transition5.getProgress());
                                                    }
                                                    objM395boximpl = Offset.m395boximpl(jM406lerpWko1d7g);
                                                    objTransform5 = objM395boximpl;
                                                }
                                            } else {
                                                placementScope = placementScope2;
                                                elementNode = elementNode3;
                                                sceneTransitionLayoutImpl7 = sceneTransitionLayoutImpl9;
                                                TransformationWithRange transformationWithRange8 = transformationWithRange5;
                                                if (transformationWithRange8 == null) {
                                                    objTransform5 = Offset.m395boximpl(j13);
                                                } else {
                                                    PropertyTransformation propertyTransformation6 = (PropertyTransformation) transformationWithRange8.transformation;
                                                    if (propertyTransformation6 instanceof CustomPropertyTransformation) {
                                                        objTransform5 = ((CustomPropertyTransformation) propertyTransformation6).transform(sceneTransitionLayoutImpl10.propertyTransformationScope, contentKeyAccess$getTransformationContentKey2, element10.key, transition5, transition5.getCoroutineScope$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                                    } else {
                                                        if (!(propertyTransformation6 instanceof InterpolatedPropertyTransformation)) {
                                                            throw new NoWhenBranchMatchedException();
                                                        }
                                                        Object objTransform8 = ((InterpolatedPropertyTransformation) propertyTransformation6).transform(sceneTransitionLayoutImpl10.propertyTransformationScope, contentKeyAccess$getTransformationContentKey2, element10.key, transition5, offsetM395boximpl10);
                                                        boolean zEquals4 = objTransform8.equals(offsetM395boximpl10);
                                                        objTransform5 = objTransform8;
                                                        if (!zEquals4) {
                                                            float progress2 = transition5.getProgress();
                                                            TransformationRange transformationRange8 = transformationWithRange8.range;
                                                            if (transformationRange8 != null) {
                                                                progress2 = transformationRange8.progress(progress2);
                                                            }
                                                            jM406lerpWko1d7g = z6 ? OffsetKt.m406lerpWko1d7g(((Offset) objTransform8).packedValue, j20, progress2) : OffsetKt.m406lerpWko1d7g(j20, ((Offset) objTransform8).packedValue, progress2);
                                                            objM395boximpl = Offset.m395boximpl(jM406lerpWko1d7g);
                                                            objTransform5 = objM395boximpl;
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            zAreEqual2 = Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey2, contentKey9);
                                        }
                                        z6 = zAreEqual2;
                                        TransformationSpecImpl transformationSpecImpl22 = transition5.previewTransformationSpec;
                                        if (transformationSpecImpl22 != null) {
                                            long j202 = offsetM395boximpl10.packedValue;
                                            if (transformationWithRange4 == null) {
                                            }
                                        }
                                    }
                                }
                            }
                            Offset offsetM395boximpl11 = Offset.m395boximpl(Offset.Unspecified);
                            offsetM395boximpl3 = Offset.m395boximpl(j12);
                            Offset offsetM395boximpl12 = Offset.m395boximpl(elementNode.getStateInContent().offsetBeforeInterruption);
                            zEquals = offsetM395boximpl12.equals(offsetM395boximpl11);
                            long jM403plusMKHz9U = ((Offset) objTransform5).packedValue;
                            if (zEquals) {
                                long jM402minusMKHz9U = Offset.m402minusMKHz9U(offsetM395boximpl12.packedValue, jM403plusMKHz9U);
                                elementNode2 = elementNode;
                                Element element11 = elementNode2._element;
                                element11.getClass();
                                Element.State stateInContent6 = elementNode2.getStateInContent();
                                stateInContent6.offsetInterruptionDelta = jM402minusMKHz9U;
                                if (transition5 != null) {
                                    Object objLast = CollectionsKt___CollectionsKt.last(stateInContent6.contents);
                                    ContentKey contentKey11 = transition5.fromContent;
                                    if (Intrinsics.areEqual(objLast, contentKey11)) {
                                        contentKey11 = transition5.toContent;
                                    }
                                    Element.State state14 = (Element.State) element11.stateByContent.get(contentKey11);
                                    if (state14 != null && SharedElementKt.isSharedElementEnabled(element11.key, transition5)) {
                                        state14.offsetInterruptionDelta = jM402minusMKHz9U;
                                    }
                                }
                                elementNode2.getStateInContent().offsetBeforeInterruption = offsetM395boximpl11.packedValue;
                            } else {
                                elementNode2 = elementNode;
                            }
                            offsetM395boximpl4 = Offset.m395boximpl(elementNode2.getStateInContent().offsetInterruptionDelta);
                            if (!offsetM395boximpl4.equals(offsetM395boximpl3) || transition5 == null) {
                                sceneTransitionLayoutImpl8 = sceneTransitionLayoutImpl7;
                            } else {
                                sceneTransitionLayoutImpl8 = sceneTransitionLayoutImpl7;
                                float fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transition5.interruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(sceneTransitionLayoutImpl8);
                                if (fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout != 0.0f) {
                                    jM403plusMKHz9U = Offset.m403plusMKHz9U(jM403plusMKHz9U, Offset.m404timestuRUvjQ(fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout, offsetM395boximpl4.packedValue));
                                }
                            }
                            elementNode2.getStateInContent().lastOffset = jM403plusMKHz9U;
                            long jM856roundk4lQ0M = IntOffsetKt.m856roundk4lQ0M(Offset.m402minusMKHz9U(jM403plusMKHz9U, j13));
                            Element element12 = elementNode2._element;
                            element12.getClass();
                            Placeable placeable = placeableMo610measureBRTryo0;
                            if (transition5 != null) {
                                Element element13 = elementNode2._element;
                                element13.getClass();
                                if (ElementKt.interruptedAlpha(sceneTransitionLayoutImpl8, element13, transition5, elementNode2.getStateInContent(), 1.0f) == 1.0f) {
                                    elementNode2.getStateInContent().lastAlpha = 1.0f;
                                    Placeable.PlacementScope.m628place70tqf50$default(placementScope, placeable, jM856roundk4lQ0M);
                                } else {
                                    Placeable.PlacementScope.m631placeWithLayeraW9wM$default(placementScope, placeable, jM856roundk4lQ0M, new Function1() { // from class: com.android.compose.animation.scene.ElementNode$$ExternalSyntheticLambda3
                                        /* JADX WARN: Removed duplicated region for block: B:148:0x02b0 A[PHI: r3 r10 r12 r18 r19 r20
                                          0x02b0: PHI (r3v7 com.android.compose.animation.scene.SceneTransitionLayoutImpl) = 
                                          (r3v6 com.android.compose.animation.scene.SceneTransitionLayoutImpl)
                                          (r3v10 com.android.compose.animation.scene.SceneTransitionLayoutImpl)
                                          (r3v10 com.android.compose.animation.scene.SceneTransitionLayoutImpl)
                                         binds: [B:147:0x02ae, B:107:0x01da, B:102:0x01cd] A[DONT_GENERATE, DONT_INLINE]
                                          0x02b0: PHI (r10v11 java.lang.Object) = (r10v9 java.lang.Object), (r10v15 java.lang.Float), (r10v15 java.lang.Float) binds: [B:147:0x02ae, B:107:0x01da, B:102:0x01cd] A[DONT_GENERATE, DONT_INLINE]
                                          0x02b0: PHI (r12v2 com.android.compose.animation.scene.Element) = 
                                          (r12v1 com.android.compose.animation.scene.Element)
                                          (r12v4 com.android.compose.animation.scene.Element)
                                          (r12v4 com.android.compose.animation.scene.Element)
                                         binds: [B:147:0x02ae, B:107:0x01da, B:102:0x01cd] A[DONT_GENERATE, DONT_INLINE]
                                          0x02b0: PHI (r18v1 androidx.compose.ui.graphics.GraphicsLayerScope) = 
                                          (r18v0 androidx.compose.ui.graphics.GraphicsLayerScope)
                                          (r18v3 androidx.compose.ui.graphics.GraphicsLayerScope)
                                          (r18v3 androidx.compose.ui.graphics.GraphicsLayerScope)
                                         binds: [B:147:0x02ae, B:107:0x01da, B:102:0x01cd] A[DONT_GENERATE, DONT_INLINE]
                                          0x02b0: PHI (r19v1 com.android.compose.animation.scene.Element$State) = 
                                          (r19v0 com.android.compose.animation.scene.Element$State)
                                          (r19v5 com.android.compose.animation.scene.Element$State)
                                          (r19v5 com.android.compose.animation.scene.Element$State)
                                         binds: [B:147:0x02ae, B:107:0x01da, B:102:0x01cd] A[DONT_GENERATE, DONT_INLINE]
                                          0x02b0: PHI (r20v1 androidx.compose.runtime.snapshots.SnapshotStateMap) = 
                                          (r20v0 androidx.compose.runtime.snapshots.SnapshotStateMap)
                                          (r20v4 androidx.compose.runtime.snapshots.SnapshotStateMap)
                                          (r20v4 androidx.compose.runtime.snapshots.SnapshotStateMap)
                                         binds: [B:147:0x02ae, B:107:0x01da, B:102:0x01cd] A[DONT_GENERATE, DONT_INLINE]] */
                                        /* JADX WARN: Removed duplicated region for block: B:17:0x004a A[PHI: r12
                                          0x004a: PHI (r12v7 java.lang.Object) = 
                                          (r12v0 java.lang.Object)
                                          (r12v0 java.lang.Object)
                                          (r12v5 java.lang.Float)
                                          (r12v0 java.lang.Object)
                                          (r12v0 java.lang.Object)
                                         binds: [B:16:0x0048, B:48:0x00c8, B:35:0x009e, B:32:0x0097, B:20:0x0069] A[DONT_GENERATE, DONT_INLINE]] */
                                        /* JADX WARN: Removed duplicated region for block: B:59:0x00fb  */
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object mo781invoke(Object obj5) {
                                            Element.State state15;
                                            boolean z7;
                                            TransitionState.Transition transition7;
                                            Element element14;
                                            ElementKey elementKey4;
                                            Element.State state16;
                                            TransformationWithRange transformationWithRange9;
                                            GraphicsLayerScope graphicsLayerScope;
                                            SnapshotStateMap snapshotStateMap3;
                                            Element.State state17;
                                            SceneTransitionLayoutImpl sceneTransitionLayoutImpl11;
                                            Float f;
                                            Element element15;
                                            Object objTransform9;
                                            float f2;
                                            float fLerp;
                                            Object objTransform10;
                                            float fFloatValue;
                                            float fLerp2;
                                            float fProgress;
                                            float fFloatValue2;
                                            float fFloatValue3;
                                            float fLerp3;
                                            TransformationRange transformationRange9;
                                            float progress3;
                                            GraphicsLayerScope graphicsLayerScope2 = (GraphicsLayerScope) obj5;
                                            ElementNode elementNode4 = elementNode2;
                                            Element element16 = elementNode4._element;
                                            if (element16 == null) {
                                                return Unit.INSTANCE;
                                            }
                                            List list3 = elementNode4.currentTransitionStates;
                                            SceneTransitionLayoutImpl sceneTransitionLayoutImpl12 = elementNode4.layoutImpl;
                                            TransitionState transitionStateAccess$elementState2 = ElementKt.access$elementState(sceneTransitionLayoutImpl12, element16, list3);
                                            if (transitionStateAccess$elementState2 != null) {
                                                ContentKey key2 = elementNode4.content.getKey();
                                                Element element17 = elementNode4._element;
                                                element17.getClass();
                                                if (ElementKt.shouldPlaceElement(sceneTransitionLayoutImpl12, key2, element17, transitionStateAccess$elementState2)) {
                                                    TransitionState.Transition transition8 = transitionStateAccess$elementState2 instanceof TransitionState.Transition ? (TransitionState.Transition) transitionStateAccess$elementState2 : null;
                                                    Element element18 = elementNode4._element;
                                                    element18.getClass();
                                                    Element.State stateInContent7 = elementNode4.getStateInContent();
                                                    Float fValueOf = Float.valueOf(1.0f);
                                                    SceneTransitionLayoutImpl sceneTransitionLayoutImpl13 = elementNode4.layoutImpl;
                                                    SnapshotStateMap snapshotStateMap4 = element18.stateByContent;
                                                    if (transition8 == null) {
                                                        graphicsLayerScope = graphicsLayerScope2;
                                                        snapshotStateMap3 = snapshotStateMap4;
                                                        state17 = stateInContent7;
                                                        sceneTransitionLayoutImpl11 = sceneTransitionLayoutImpl13;
                                                        f2 = 1.0f;
                                                        objTransform9 = fValueOf;
                                                        element15 = element18;
                                                    } else {
                                                        ContentKey contentKey12 = transition8.fromContent;
                                                        Element.State state18 = (Element.State) snapshotStateMap4.get(contentKey12);
                                                        ContentKey contentKey13 = transition8.toContent;
                                                        Element.State state19 = (Element.State) snapshotStateMap4.get(contentKey13);
                                                        if (state18 != null || state19 != null) {
                                                            ContentKey contentKey14 = (ContentKey) CollectionsKt___CollectionsKt.last(stateInContent7.contents);
                                                            boolean zAreEqual3 = false;
                                                            if (state18 == null || state19 == null) {
                                                                state15 = state18;
                                                                z7 = false;
                                                            } else {
                                                                state15 = state18;
                                                                z7 = true;
                                                            }
                                                            ElementKey elementKey5 = element18.key;
                                                            if (z7 && SharedElementKt.isSharedElementEnabled(elementKey5, transition8)) {
                                                                state15.getClass();
                                                                state19.getClass();
                                                                if (!fValueOf.equals(fValueOf)) {
                                                                    progress3 = transition8.getProgress();
                                                                    fValueOf = Float.valueOf(androidx.compose.ui.util.MathHelpersKt.lerp(1.0f, 1.0f, progress3));
                                                                }
                                                                graphicsLayerScope = graphicsLayerScope2;
                                                                snapshotStateMap3 = snapshotStateMap4;
                                                                state17 = stateInContent7;
                                                                sceneTransitionLayoutImpl11 = sceneTransitionLayoutImpl13;
                                                                f2 = 1.0f;
                                                                objTransform9 = fValueOf;
                                                                element15 = element18;
                                                            } else {
                                                                if (z7 || !(transition8 instanceof TransitionState.Transition.ReplaceOverlay)) {
                                                                    transition7 = transition8;
                                                                    element14 = element18;
                                                                    elementKey4 = elementKey5;
                                                                    state16 = null;
                                                                } else {
                                                                    SceneKey sceneKey3 = ((TransitionState.Transition.ReplaceOverlay) transition8).currentSceneWhenTransitionStarted;
                                                                    if (sceneKey3 == null) {
                                                                        sceneKey3 = null;
                                                                    }
                                                                    Element.State state20 = (Element.State) snapshotStateMap4.get(sceneKey3);
                                                                    if (state20 == null || !SharedElementKt.isSharedElementEnabled(elementKey5, transition8)) {
                                                                        TransitionState.Transition transition9 = transition8;
                                                                        element14 = element18;
                                                                        elementKey4 = elementKey5;
                                                                        state16 = state20;
                                                                        transition7 = transition9;
                                                                    } else {
                                                                        if (!fValueOf.equals(fValueOf)) {
                                                                            progress3 = transition8.getProgress();
                                                                            fValueOf = Float.valueOf(androidx.compose.ui.util.MathHelpersKt.lerp(1.0f, 1.0f, progress3));
                                                                        }
                                                                        graphicsLayerScope = graphicsLayerScope2;
                                                                        snapshotStateMap3 = snapshotStateMap4;
                                                                        state17 = stateInContent7;
                                                                        sceneTransitionLayoutImpl11 = sceneTransitionLayoutImpl13;
                                                                        f2 = 1.0f;
                                                                        objTransform9 = fValueOf;
                                                                        element15 = element18;
                                                                    }
                                                                }
                                                                ContentKey contentKeyAccess$getTransformationContentKey3 = ElementKt.access$getTransformationContentKey(z7, contentKey14, sceneTransitionLayoutImpl13, transition7, element14, state16);
                                                                Element element19 = element14;
                                                                transition8 = transition7;
                                                                ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout5 = transition8.transformationSpec.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKeyAccess$getTransformationContentKey3, elementKey4);
                                                                TransformationWithRange transformationWithRange10 = elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout5 != null ? elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout5.alpha : null;
                                                                if (!Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey3, contentKey13)) {
                                                                    if (!Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey3, contentKey12)) {
                                                                        if (ElementKt.isAncestorTransition(sceneTransitionLayoutImpl13, transition8)) {
                                                                            zAreEqual3 = ElementKt.access$isEnteringAncestorTransition(sceneTransitionLayoutImpl13, transition8);
                                                                        } else if (!Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey3, transition8.getCurrentScene())) {
                                                                            zAreEqual3 = Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey3, contentKey13);
                                                                        } else if (state19 == null) {
                                                                            zAreEqual3 = true;
                                                                        }
                                                                    }
                                                                    TransformationSpecImpl transformationSpecImpl3 = transition8.previewTransformationSpec;
                                                                    if (transformationSpecImpl3 != null) {
                                                                        ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout6 = transformationSpecImpl3.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKeyAccess$getTransformationContentKey3, elementKey4);
                                                                        transformationWithRange9 = elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout6 != null ? elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout6.alpha : null;
                                                                    } else {
                                                                        transformationWithRange9 = null;
                                                                    }
                                                                    if (transformationWithRange9 != null) {
                                                                        boolean zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3 = transition8.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
                                                                        PropertyTransformation propertyTransformation7 = (PropertyTransformation) transformationWithRange9.transformation;
                                                                        graphicsLayerScope = graphicsLayerScope2;
                                                                        if (!(propertyTransformation7 instanceof InterpolatedPropertyTransformation)) {
                                                                            if (!(propertyTransformation7 instanceof CustomPropertyTransformation)) {
                                                                                throw new NoWhenBranchMatchedException();
                                                                            }
                                                                            throw new IllegalStateException(("Custom transformations in preview specs should not be possible (element=" + elementKey4.debugName + " fromContent=" + contentKey12 + " toContent=" + contentKey13 + ")").toString());
                                                                        }
                                                                        TransformationWithRange transformationWithRange11 = transformationWithRange10;
                                                                        snapshotStateMap3 = snapshotStateMap4;
                                                                        state17 = stateInContent7;
                                                                        sceneTransitionLayoutImpl11 = sceneTransitionLayoutImpl13;
                                                                        f = fValueOf;
                                                                        element15 = element19;
                                                                        Object objTransform11 = ((InterpolatedPropertyTransformation) propertyTransformation7).transform(sceneTransitionLayoutImpl13.propertyTransformationScope, contentKeyAccess$getTransformationContentKey3, element19.key, transition8, f);
                                                                        if (transformationWithRange11 != null) {
                                                                            PropertyTransformation propertyTransformation8 = (PropertyTransformation) transformationWithRange11.transformation;
                                                                            if (!(propertyTransformation8 instanceof InterpolatedPropertyTransformation)) {
                                                                                if (!(propertyTransformation8 instanceof CustomPropertyTransformation)) {
                                                                                    throw new NoWhenBranchMatchedException();
                                                                                }
                                                                                throw new IllegalStateException(("Custom transformations are not allowed for properties with a preview (element=" + elementKey4.debugName + " fromContent=" + contentKey12 + " toContent=" + contentKey13 + ")").toString());
                                                                            }
                                                                            objTransform10 = ((InterpolatedPropertyTransformation) propertyTransformation8).transform(sceneTransitionLayoutImpl11.propertyTransformationScope, contentKeyAccess$getTransformationContentKey3, element15.key, transition8, f);
                                                                            objTransform11 = objTransform11;
                                                                        } else {
                                                                            objTransform10 = null;
                                                                        }
                                                                        if (zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3 && zAreEqual3 && objTransform11.equals(objTransform10)) {
                                                                            objTransform9 = objTransform11;
                                                                        } else if ((zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3 && !zAreEqual3 && f.equals(objTransform11)) || (objTransform11.equals(objTransform10) && f.equals(objTransform11))) {
                                                                            objTransform9 = f;
                                                                        } else {
                                                                            float previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3 = transition8.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
                                                                            TransformationRange transformationRange10 = transformationWithRange9.range;
                                                                            if (transformationRange10 != null) {
                                                                                previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3 = transformationRange10.progress(previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3);
                                                                            }
                                                                            if (!zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3) {
                                                                                if (zAreEqual3) {
                                                                                    if (objTransform10 == null) {
                                                                                        objTransform10 = f;
                                                                                    }
                                                                                    fLerp2 = androidx.compose.ui.util.MathHelpersKt.lerp(((Number) objTransform11).floatValue(), ((Number) objTransform10).floatValue(), previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3);
                                                                                    fProgress = (transformationWithRange11 == null || (transformationRange9 = transformationWithRange11.range) == null) ? transition8.getProgress() : transformationRange9.progress(transition8.getProgress());
                                                                                    fFloatValue2 = 1.0f;
                                                                                } else {
                                                                                    fFloatValue = 1.0f;
                                                                                    if (objTransform10 == null) {
                                                                                        fFloatValue3 = ((Number) objTransform11).floatValue();
                                                                                    } else {
                                                                                        fLerp2 = androidx.compose.ui.util.MathHelpersKt.lerp(1.0f, ((Number) objTransform11).floatValue(), previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3);
                                                                                        TransformationRange transformationRange11 = transformationWithRange11.range;
                                                                                        fProgress = transformationRange11 != null ? transformationRange11.progress(transition8.getProgress()) : transition8.getProgress();
                                                                                        fFloatValue2 = ((Number) objTransform10).floatValue();
                                                                                    }
                                                                                }
                                                                                fLerp3 = androidx.compose.ui.util.MathHelpersKt.lerp(fLerp2, fFloatValue2, fProgress);
                                                                                objTransform9 = Float.valueOf(fLerp3);
                                                                            } else if (zAreEqual3) {
                                                                                if (objTransform10 == null) {
                                                                                    objTransform10 = f;
                                                                                }
                                                                                fFloatValue3 = ((Number) objTransform10).floatValue();
                                                                                fFloatValue = ((Number) objTransform11).floatValue();
                                                                            } else {
                                                                                fFloatValue3 = ((Number) objTransform11).floatValue();
                                                                                fFloatValue = 1.0f;
                                                                            }
                                                                            fLerp3 = androidx.compose.ui.util.MathHelpersKt.lerp(fFloatValue, fFloatValue3, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout3);
                                                                            objTransform9 = Float.valueOf(fLerp3);
                                                                        }
                                                                        f2 = 1.0f;
                                                                    } else {
                                                                        graphicsLayerScope = graphicsLayerScope2;
                                                                        snapshotStateMap3 = snapshotStateMap4;
                                                                        state17 = stateInContent7;
                                                                        sceneTransitionLayoutImpl11 = sceneTransitionLayoutImpl13;
                                                                        TransformationWithRange transformationWithRange12 = transformationWithRange10;
                                                                        f = fValueOf;
                                                                        element15 = element19;
                                                                        if (transformationWithRange12 != null) {
                                                                            PropertyTransformation propertyTransformation9 = (PropertyTransformation) transformationWithRange12.transformation;
                                                                            if (propertyTransformation9 instanceof CustomPropertyTransformation) {
                                                                                objTransform9 = ((CustomPropertyTransformation) propertyTransformation9).transform(sceneTransitionLayoutImpl11.propertyTransformationScope, contentKeyAccess$getTransformationContentKey3, element15.key, transition8, transition8.getCoroutineScope$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                                                            } else {
                                                                                if (!(propertyTransformation9 instanceof InterpolatedPropertyTransformation)) {
                                                                                    throw new NoWhenBranchMatchedException();
                                                                                }
                                                                                objTransform9 = ((InterpolatedPropertyTransformation) propertyTransformation9).transform(sceneTransitionLayoutImpl11.propertyTransformationScope, contentKeyAccess$getTransformationContentKey3, element15.key, transition8, f);
                                                                                if (!objTransform9.equals(f)) {
                                                                                    float progress4 = transition8.getProgress();
                                                                                    TransformationRange transformationRange12 = transformationWithRange12.range;
                                                                                    if (transformationRange12 != null) {
                                                                                        progress4 = transformationRange12.progress(progress4);
                                                                                    }
                                                                                    if (zAreEqual3) {
                                                                                        f2 = 1.0f;
                                                                                        fLerp = androidx.compose.ui.util.MathHelpersKt.lerp(((Number) objTransform9).floatValue(), 1.0f, progress4);
                                                                                    } else {
                                                                                        f2 = 1.0f;
                                                                                        fLerp = androidx.compose.ui.util.MathHelpersKt.lerp(1.0f, ((Number) objTransform9).floatValue(), progress4);
                                                                                    }
                                                                                    objTransform9 = Float.valueOf(fLerp);
                                                                                }
                                                                            }
                                                                        }
                                                                        f2 = 1.0f;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    float fFloatValue4 = ((Number) objTransform9).floatValue();
                                                    if (fFloatValue4 < 0.0f) {
                                                        fFloatValue4 = 0.0f;
                                                    }
                                                    float f3 = fFloatValue4 > f2 ? f2 : fFloatValue4;
                                                    if (!element15.wasDrawnInAnyContent && f3 > 0.0f) {
                                                        Iterator it = snapshotStateMap3.entries.iterator();
                                                        while (it.hasNext()) {
                                                            ((Element.State) ((Map.Entry) it.next()).getValue()).alphaBeforeInterruption = 0.0f;
                                                        }
                                                    }
                                                    Element.State state21 = state17;
                                                    float fInterruptedAlpha = ElementKt.interruptedAlpha(sceneTransitionLayoutImpl11, element15, transition8, state21, f3);
                                                    state21.lastAlpha = fInterruptedAlpha;
                                                    ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
                                                    reusableGraphicsLayerScope.setAlpha(fInterruptedAlpha);
                                                    CompositingStrategy.Companion.getClass();
                                                    reusableGraphicsLayerScope.m496setCompositingStrategyaDBOjCE(CompositingStrategy.ModulateAlpha);
                                                    return Unit.INSTANCE;
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, 2);
                                }
                                Unit unit = Unit.INSTANCE;
                            } else {
                                ContentKey contentKey12 = transition5.fromContent;
                                SnapshotStateMap snapshotStateMap3 = element12.stateByContent;
                                Element.State state15 = (Element.State) snapshotStateMap3.get(contentKey12);
                                Element.State state16 = (Element.State) snapshotStateMap3.get(transition5.toContent);
                                if (state15 != null || state16 != null) {
                                    ElementKey elementKey4 = element12.key;
                                    if (state15 == null || state16 == null || !SharedElementKt.isSharedElementEnabled(elementKey4, transition5)) {
                                        ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout5 = transition5.transformationSpec.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(content.getKey(), elementKey4);
                                        if ((elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout5 != null ? elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout5.alpha : null) == null) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                    placementScope = placementScope2;
                    elementNode = elementNode3;
                    sceneTransitionLayoutImpl7 = sceneTransitionLayoutImpl9;
                    j13 = jMo613localPositionOfR5De75A;
                    content = content2;
                    j12 = 0;
                    objTransform5 = offsetM395boximpl5;
                    Offset offsetM395boximpl112 = Offset.m395boximpl(Offset.Unspecified);
                    offsetM395boximpl3 = Offset.m395boximpl(j12);
                    Offset offsetM395boximpl122 = Offset.m395boximpl(elementNode.getStateInContent().offsetBeforeInterruption);
                    zEquals = offsetM395boximpl122.equals(offsetM395boximpl112);
                    long jM403plusMKHz9U2 = ((Offset) objTransform5).packedValue;
                    if (zEquals) {
                    }
                    offsetM395boximpl4 = Offset.m395boximpl(elementNode2.getStateInContent().offsetInterruptionDelta);
                    if (offsetM395boximpl4.equals(offsetM395boximpl3)) {
                        sceneTransitionLayoutImpl8 = sceneTransitionLayoutImpl7;
                        elementNode2.getStateInContent().lastOffset = jM403plusMKHz9U2;
                        long jM856roundk4lQ0M2 = IntOffsetKt.m856roundk4lQ0M(Offset.m402minusMKHz9U(jM403plusMKHz9U2, j13));
                        Element element122 = elementNode2._element;
                        element122.getClass();
                        Placeable placeable2 = placeableMo610measureBRTryo0;
                        if (transition5 != null) {
                        }
                    }
                } else {
                    Element.State stateInContent7 = elementNode3.getStateInContent();
                    Offset.Companion.getClass();
                    stateInContent7.lastOffset = Offset.Unspecified;
                    Scale.Companion.getClass();
                    stateInContent7.lastScale = Scale.Unspecified;
                    Element.Companion.getClass();
                    stateInContent7.lastAlpha = Element.AlphaUnspecified;
                    TraversableNodeKt.traverseDescendants(elementNode3, ElementNode.ElementTraverseKey, new ElementNode$$ExternalSyntheticLambda2(1));
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final void assignState(ContentKey contentKey, ContentKey contentKey2) {
        Element element = this._element;
        element.getClass();
        Element.State state = (Element.State) element.stateByContent.get(contentKey2);
        if (state != null) {
            Element element2 = this._element;
            element2.getClass();
            element2.stateByContent.put(contentKey, state);
        } else {
            Element element3 = this._element;
            element3.getClass();
            element3.stateByContent.remove(contentKey);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:137:0x0286 A[PHI: r1 r19 r21
      0x0286: PHI (r1v2 com.android.compose.animation.scene.SceneTransitionLayoutImpl) = 
      (r1v0 com.android.compose.animation.scene.SceneTransitionLayoutImpl)
      (r1v7 com.android.compose.animation.scene.SceneTransitionLayoutImpl)
      (r1v7 com.android.compose.animation.scene.SceneTransitionLayoutImpl)
     binds: [B:136:0x0284, B:99:0x01ce, B:94:0x01c1] A[DONT_GENERATE, DONT_INLINE]
      0x0286: PHI (r19v2 com.android.compose.animation.scene.Element$State) = 
      (r19v0 com.android.compose.animation.scene.Element$State)
      (r19v5 com.android.compose.animation.scene.Element$State)
      (r19v5 com.android.compose.animation.scene.Element$State)
     binds: [B:136:0x0284, B:99:0x01ce, B:94:0x01c1] A[DONT_GENERATE, DONT_INLINE]
      0x0286: PHI (r21v2 java.util.Map) = (r21v0 java.util.Map), (r21v3 java.util.Map), (r21v3 java.util.Map) binds: [B:136:0x0284, B:99:0x01ce, B:94:0x01c1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0377  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00f9  */
    @Override // androidx.compose.ui.node.DrawModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        Element.State state;
        boolean z;
        Element.State state2;
        TransformationWithRange transformationWithRange;
        Element.State state3;
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl;
        Map map;
        float progress;
        Scale scaleLerp;
        Object objLerp;
        Object obj;
        Object objTransform;
        Scale scale;
        TransformationRange transformationRange;
        float progress2;
        Scale scale2;
        Scale scale3;
        Scale scale4;
        Scale scale5;
        Scale scale6;
        long j;
        long j2;
        LayoutNodeDrawScope layoutNodeDrawScope2;
        Scale scale7;
        float fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout;
        long jM403plusMKHz9U;
        long jM402minusMKHz9U;
        Object objLerp2;
        Element element = this._element;
        element.getClass();
        boolean zAreEqual = true;
        element.wasDrawnInAnyContent = true;
        Element element2 = this._element;
        element2.getClass();
        TransitionState transitionStateAccess$elementState = ElementKt.access$elementState(this.layoutImpl, element2, this.currentTransitionStates);
        TransitionState.Transition transition = transitionStateAccess$elementState instanceof TransitionState.Transition ? (TransitionState.Transition) transitionStateAccess$elementState : null;
        Element element3 = this._element;
        element3.getClass();
        Element.State stateInContent = getStateInContent();
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl2 = this.layoutImpl;
        ElementKey elementKey = element3.key;
        Map map2 = element3.stateByContent;
        if (transition == null) {
            Scale.Companion.getClass();
            objLerp2 = Scale.Default;
        } else {
            ContentKey contentKey = transition.fromContent;
            Element.State state4 = (Element.State) map2.get(contentKey);
            ContentKey contentKey2 = transition.toContent;
            Element.State state5 = (Element.State) map2.get(contentKey2);
            if (state4 != null || state5 != null) {
                ContentKey contentKey3 = (ContentKey) CollectionsKt___CollectionsKt.last(stateInContent.contents);
                if (state4 == null || state5 == null) {
                    state = state4;
                    z = false;
                } else {
                    state = state4;
                    z = true;
                }
                if (z && SharedElementKt.isSharedElementEnabled(elementKey, transition)) {
                    state.getClass();
                    state5.getClass();
                    Scale.Companion.getClass();
                    Scale scale8 = Scale.Default;
                    boolean zAreEqual2 = Intrinsics.areEqual(scale8, scale8);
                    objLerp2 = scale8;
                    if (!zAreEqual2) {
                        progress2 = transition.getProgress();
                        scale2 = scale8;
                        objLerp2 = MathHelpersKt.lerp(scale2, scale2, progress2);
                    }
                } else {
                    if (z || !(transition instanceof TransitionState.Transition.ReplaceOverlay)) {
                        state2 = null;
                    } else {
                        SceneKey sceneKey = ((TransitionState.Transition.ReplaceOverlay) transition).currentSceneWhenTransitionStarted;
                        if (sceneKey == null) {
                            sceneKey = null;
                        }
                        Element.State state6 = (Element.State) map2.get(sceneKey);
                        if (state6 == null || !SharedElementKt.isSharedElementEnabled(elementKey, transition)) {
                            state2 = state6;
                        } else {
                            Scale.Companion.getClass();
                            Scale scale9 = Scale.Default;
                            boolean zAreEqual3 = Intrinsics.areEqual(scale9, scale9);
                            objLerp2 = scale9;
                            if (!zAreEqual3) {
                                progress2 = transition.getProgress();
                                scale2 = scale9;
                                objLerp2 = MathHelpersKt.lerp(scale2, scale2, progress2);
                            }
                        }
                    }
                    TransitionState.Transition transition2 = transition;
                    ContentKey contentKeyAccess$getTransformationContentKey = ElementKt.access$getTransformationContentKey(z, contentKey3, sceneTransitionLayoutImpl2, transition2, element3, state2);
                    transition = transition2;
                    Scale.Companion.getClass();
                    Scale scale10 = Scale.Default;
                    ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transition.transformationSpec.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKeyAccess$getTransformationContentKey, elementKey);
                    TransformationWithRange transformationWithRange2 = elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout != null ? elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.drawScale : null;
                    if (!Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey, contentKey2)) {
                        if (!Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey, contentKey)) {
                            if (ElementKt.isAncestorTransition(sceneTransitionLayoutImpl2, transition)) {
                                zAreEqual = ElementKt.access$isEnteringAncestorTransition(sceneTransitionLayoutImpl2, transition);
                            } else if (!Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey, transition.getCurrentScene())) {
                                zAreEqual = Intrinsics.areEqual(contentKeyAccess$getTransformationContentKey, contentKey2);
                            } else if (state5 != null) {
                                zAreEqual = false;
                            }
                        }
                    }
                    TransformationSpecImpl transformationSpecImpl = transition.previewTransformationSpec;
                    if (transformationSpecImpl != null) {
                        ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 = transformationSpecImpl.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKeyAccess$getTransformationContentKey, elementKey);
                        transformationWithRange = elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 != null ? elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2.drawScale : null;
                    } else {
                        transformationWithRange = null;
                    }
                    if (transformationWithRange != null) {
                        boolean zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transition.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
                        PropertyTransformation propertyTransformation = (PropertyTransformation) transformationWithRange.transformation;
                        boolean z2 = zAreEqual;
                        if (!(propertyTransformation instanceof InterpolatedPropertyTransformation)) {
                            if (!(propertyTransformation instanceof CustomPropertyTransformation)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            throw new IllegalStateException(("Custom transformations in preview specs should not be possible (element=" + elementKey.debugName + " fromContent=" + contentKey + " toContent=" + contentKey2 + ")").toString());
                        }
                        TransformationWithRange transformationWithRange3 = transformationWithRange2;
                        state3 = stateInContent;
                        sceneTransitionLayoutImpl = sceneTransitionLayoutImpl2;
                        map = map2;
                        Object objTransform2 = ((InterpolatedPropertyTransformation) propertyTransformation).transform(sceneTransitionLayoutImpl2.propertyTransformationScope, contentKeyAccess$getTransformationContentKey, element3.key, transition, scale10);
                        if (transformationWithRange3 != null) {
                            PropertyTransformation propertyTransformation2 = (PropertyTransformation) transformationWithRange3.transformation;
                            if (!(propertyTransformation2 instanceof InterpolatedPropertyTransformation)) {
                                if (!(propertyTransformation2 instanceof CustomPropertyTransformation)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                throw new IllegalStateException(("Custom transformations are not allowed for properties with a preview (element=" + elementKey.debugName + " fromContent=" + contentKey + " toContent=" + contentKey2 + ")").toString());
                            }
                            obj = objTransform2;
                            objTransform = ((InterpolatedPropertyTransformation) propertyTransformation2).transform(sceneTransitionLayoutImpl.propertyTransformationScope, contentKeyAccess$getTransformationContentKey, element3.key, transition, scale10);
                        } else {
                            obj = objTransform2;
                            objTransform = null;
                        }
                        if (zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout && z2 && obj.equals(objTransform)) {
                            objLerp = obj;
                        } else if ((zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout && !z2 && Intrinsics.areEqual(scale10, obj)) || (obj.equals(objTransform) && Intrinsics.areEqual(scale10, obj))) {
                            objLerp = scale10;
                        } else {
                            float previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transition.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
                            TransformationRange transformationRange2 = transformationWithRange.range;
                            if (transformationRange2 != null) {
                                previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transformationRange2.progress(previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
                            }
                            if (zIsInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout) {
                                if (z2) {
                                    if (objTransform != null) {
                                        scale10 = objTransform;
                                    }
                                    objLerp = MathHelpersKt.lerp((Scale) obj, scale10, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
                                } else {
                                    scale = (Scale) obj;
                                    objLerp = MathHelpersKt.lerp(scale10, scale, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
                                }
                            } else if (z2) {
                                if (objTransform == null) {
                                    objTransform = scale10;
                                }
                                scaleLerp = MathHelpersKt.lerp((Scale) obj, (Scale) objTransform, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
                                progress = (transformationWithRange3 == null || (transformationRange = transformationWithRange3.range) == null) ? transition.getProgress() : transformationRange.progress(transition.getProgress());
                                objLerp = MathHelpersKt.lerp(scaleLerp, scale10, progress);
                            } else if (objTransform == null) {
                                scale = (Scale) obj;
                                objLerp = MathHelpersKt.lerp(scale10, scale, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
                            } else {
                                Scale scaleLerp2 = MathHelpersKt.lerp(scale10, (Scale) obj, previewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
                                TransformationRange transformationRange3 = transformationWithRange3.range;
                                objLerp = MathHelpersKt.lerp(scaleLerp2, (Scale) objTransform, transformationRange3 != null ? transformationRange3.progress(transition.getProgress()) : transition.getProgress());
                            }
                        }
                    } else {
                        boolean z3 = zAreEqual;
                        state3 = stateInContent;
                        sceneTransitionLayoutImpl = sceneTransitionLayoutImpl2;
                        TransformationWithRange transformationWithRange4 = transformationWithRange2;
                        map = map2;
                        if (transformationWithRange4 != null) {
                            PropertyTransformation propertyTransformation3 = (PropertyTransformation) transformationWithRange4.transformation;
                            if (propertyTransformation3 instanceof CustomPropertyTransformation) {
                                objLerp = ((CustomPropertyTransformation) propertyTransformation3).transform(sceneTransitionLayoutImpl.propertyTransformationScope, contentKeyAccess$getTransformationContentKey, element3.key, transition, transition.getCoroutineScope$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                            } else {
                                if (!(propertyTransformation3 instanceof InterpolatedPropertyTransformation)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                Object objTransform3 = ((InterpolatedPropertyTransformation) propertyTransformation3).transform(sceneTransitionLayoutImpl.propertyTransformationScope, contentKeyAccess$getTransformationContentKey, element3.key, transition, scale10);
                                boolean zEquals = objTransform3.equals(scale10);
                                objLerp = objTransform3;
                                if (!zEquals) {
                                    progress = transition.getProgress();
                                    TransformationRange transformationRange4 = transformationWithRange4.range;
                                    if (transformationRange4 != null) {
                                        progress = transformationRange4.progress(progress);
                                    }
                                    if (z3) {
                                        scaleLerp = (Scale) objTransform3;
                                        objLerp = MathHelpersKt.lerp(scaleLerp, scale10, progress);
                                    } else {
                                        objLerp = MathHelpersKt.lerp(scale10, (Scale) objTransform3, progress);
                                    }
                                }
                            }
                        }
                    }
                }
                scale3 = (Scale) objLerp;
                Scale.Companion.getClass();
                scale4 = Scale.Unspecified;
                scale5 = Scale.Zero;
                Element.State state7 = state3;
                scale6 = state7.scaleBeforeInterruption;
                if (Intrinsics.areEqual(scale6, scale4)) {
                    float f = scale6.scaleX - scale3.scaleX;
                    float f2 = scale6.scaleY - scale3.scaleY;
                    long j3 = scale6.pivot;
                    long j4 = j3 & 9223372034707292159L;
                    j = 9223372034707292159L;
                    long j5 = scale3.pivot;
                    if (j4 == 9205357640488583168L && (j5 & 9223372034707292159L) == 9205357640488583168L) {
                        Offset.Companion.getClass();
                        jM402minusMKHz9U = Offset.Unspecified;
                        j2 = 9205357640488583168L;
                        layoutNodeDrawScope2 = layoutNodeDrawScope;
                    } else {
                        j2 = 9205357640488583168L;
                        layoutNodeDrawScope2 = layoutNodeDrawScope;
                        jM402minusMKHz9U = Offset.m402minusMKHz9U(ElementKt.getDrawScale$specifiedOrCenter(j3, layoutNodeDrawScope2), ElementKt.getDrawScale$specifiedOrCenter(j5, layoutNodeDrawScope2));
                    }
                    Scale scale11 = new Scale(f, f2, jM402minusMKHz9U, null);
                    state7.scaleInterruptionDelta = scale11;
                    if (transition != null) {
                        Object objLast = CollectionsKt___CollectionsKt.last(state7.contents);
                        ContentKey contentKey4 = transition.fromContent;
                        if (Intrinsics.areEqual(objLast, contentKey4)) {
                            contentKey4 = transition.toContent;
                        }
                        Element.State state8 = (Element.State) map.get(contentKey4);
                        if (state8 != null && SharedElementKt.isSharedElementEnabled(elementKey, transition)) {
                            state8.scaleInterruptionDelta = scale11;
                        }
                    }
                    state7.scaleBeforeInterruption = scale4;
                } else {
                    j = 9223372034707292159L;
                    j2 = 9205357640488583168L;
                    layoutNodeDrawScope2 = layoutNodeDrawScope;
                }
                scale7 = state7.scaleInterruptionDelta;
                if (!Intrinsics.areEqual(scale7, scale5) && transition != null) {
                    fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transition.interruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(sceneTransitionLayoutImpl);
                    if (fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout != 0.0f) {
                        float f3 = (scale7.scaleX * fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout) + scale3.scaleX;
                        float f4 = (scale7.scaleY * fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout) + scale3.scaleY;
                        long j6 = scale3.pivot;
                        long j7 = j6 & j;
                        long j8 = scale7.pivot;
                        if (j7 == j2 && (j8 & j) == j2) {
                            Offset.Companion.getClass();
                            jM403plusMKHz9U = Offset.Unspecified;
                        } else {
                            jM403plusMKHz9U = Offset.m403plusMKHz9U(ElementKt.getDrawScale$specifiedOrCenter(j6, layoutNodeDrawScope2), Offset.m404timestuRUvjQ(fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout, ElementKt.getDrawScale$specifiedOrCenter(j8, layoutNodeDrawScope2)));
                        }
                        scale3 = new Scale(f3, f4, jM403plusMKHz9U, null);
                    }
                }
                state7.lastScale = scale3;
                if (!Intrinsics.areEqual(scale3, Scale.Default)) {
                    layoutNodeDrawScope2.drawContent();
                    return;
                }
                float f5 = scale3.scaleX;
                float f6 = scale3.scaleY;
                long jMo546getCenterF1C5BW0 = scale3.pivot;
                long j9 = jMo546getCenterF1C5BW0 & j;
                CanvasDrawScope canvasDrawScope = layoutNodeDrawScope2.canvasDrawScope;
                if (j9 == j2) {
                    jMo546getCenterF1C5BW0 = canvasDrawScope.mo546getCenterF1C5BW0();
                }
                CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
                long jM528getSizeNHjbRc = canvasDrawScope$drawContext$1.m528getSizeNHjbRc();
                canvasDrawScope$drawContext$1.getCanvas().save();
                try {
                    canvasDrawScope$drawContext$1.transform.m532scale0AR0LA0(f5, f6, jMo546getCenterF1C5BW0);
                    layoutNodeDrawScope2.drawContent();
                    return;
                } finally {
                    BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, jM528getSizeNHjbRc);
                }
            }
            Scale.Companion.getClass();
            objLerp2 = Scale.Default;
        }
        state3 = stateInContent;
        sceneTransitionLayoutImpl = sceneTransitionLayoutImpl2;
        map = map2;
        objLerp = objLerp2;
        scale3 = (Scale) objLerp;
        Scale.Companion.getClass();
        scale4 = Scale.Unspecified;
        scale5 = Scale.Zero;
        Element.State state72 = state3;
        scale6 = state72.scaleBeforeInterruption;
        if (Intrinsics.areEqual(scale6, scale4)) {
        }
        scale7 = state72.scaleInterruptionDelta;
        if (!Intrinsics.areEqual(scale7, scale5)) {
            fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transition.interruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(sceneTransitionLayoutImpl);
            if (fInterruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout != 0.0f) {
            }
        }
        state72.lastScale = scale3;
        if (!Intrinsics.areEqual(scale3, Scale.Default)) {
        }
    }

    public final Element.State getStateInContent() {
        Element element = this._element;
        element.getClass();
        return (Element.State) MapsKt__MapsKt.getValue(this.content.getKey(), element.stateByContent);
    }

    @Override // androidx.compose.ui.node.TraversableNode
    public final Object getTraverseKey() {
        return this.traverseKey;
    }

    public final boolean isAnyStateTransitioning() {
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl = this.layoutImpl;
        if (MutableSceneTransitionLayoutState.isTransitioning$default(sceneTransitionLayoutImpl.state, null, 3)) {
            return true;
        }
        List list = sceneTransitionLayoutImpl.ancestors;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (MutableSceneTransitionLayoutState.isTransitioning$default(((Ancestor) list.get(i)).layoutImpl.state, null, 3)) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    /* renamed from: isMeasurementApproachInProgress-ozmzZPI */
    public final boolean mo606isMeasurementApproachInProgressozmzZPI(long j) {
        boolean zIsAnyStateTransitioning = isAnyStateTransitioning();
        if (!zIsAnyStateTransitioning) {
            Element.State stateInContent = getStateInContent();
            Element.Companion.getClass();
            long j2 = Element.SizeUnspecified;
            ((SnapshotMutableStateImpl) stateInContent.approachSize$delegate).setValue(IntSize.m861boximpl(j2));
        }
        return zIsAnyStateTransitioning;
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    public final boolean isPlacementApproachInProgress(Placeable.PlacementScope placementScope, LayoutCoordinates layoutCoordinates) {
        return isAnyStateTransitioning();
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode, androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        if (!measureScope.isLookingAhead()) {
            throw new IllegalStateException("Check failed.");
        }
        Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
        Element.State stateInContent = getStateInContent();
        long size = ElementKt.size(placeableMo610measureBRTryo0);
        ((SnapshotMutableStateImpl) stateInContent.targetSize$delegate).setValue(IntSize.m861boximpl(size));
        return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new ElementNode$$ExternalSyntheticLambda0(placeableMo610measureBRTryo0, this, 0));
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        updateElementAndContentValues();
        getStateInContent().nodes.add(this);
        BuildersKt.launch$default(getCoroutineScope(), null, null, new ElementNode$addNodeToContentState$1(this, null), 3);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        getStateInContent().nodes.remove(this);
        Element element = this._element;
        element.getClass();
        Companion.access$maybePruneMaps(Companion, this.layoutImpl, element, getStateInContent());
        this._element = null;
    }

    public final void updateElementAndContentValues() {
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl = this.layoutImpl;
        Element element = (Element) sceneTransitionLayoutImpl.elements.get(this.key);
        if (element == null) {
            element = new Element(this.key);
            sceneTransitionLayoutImpl.elements.put(this.key, element);
        }
        this._element = element;
        Content content = this.content;
        ContentKey key = content.getKey();
        SnapshotStateMap snapshotStateMap = element.stateByContent;
        if (snapshotStateMap.containsKey(key)) {
            return;
        }
        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        List list = sceneTransitionLayoutImpl.ancestors;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            listBuilderCreateListBuilder.add(((Ancestor) list.get(i)).inContent);
        }
        listBuilderCreateListBuilder.add(content.getKey());
        Element.State state = new Element.State(listBuilderCreateListBuilder.build());
        snapshotStateMap.put(content.getKey(), state);
        List list2 = sceneTransitionLayoutImpl.ancestors;
        int size2 = list2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            snapshotStateMap.putIfAbsent(((Ancestor) list2.get(i2)).inContent, state);
        }
    }
}
