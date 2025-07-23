package com.android.compose.animation.scene;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.snapshots.SnapshotStateMap;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ApproachLayoutModifierNode;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.Element;
import com.android.compose.animation.scene.content.Content;
import com.android.compose.animation.scene.content.state.TransitionState;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r3v10 long, still in use, count: 2, list:
          (r3v10 long) from 0x03ca: INVOKE (r3v10 long), (r13v2 java.lang.Object) STATIC call: androidx.compose.ui.unit.IntSize.equals-impl(long, java.lang.Object):boolean A[MD:(long, java.lang.Object):boolean (m), WRAPPED] (LINE:971)
          (r3v10 long) from 0x03d3: PHI (r3v6 long) = (r3v10 long) binds: [B:183:0x03ce] A[DONT_GENERATE, DONT_INLINE]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:125)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:62)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:45)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:67)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:35)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:34)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v31, types: [com.android.compose.animation.scene.content.state.TransitionState] */
    /* JADX WARN: Type inference failed for: r7v36 */
    /* JADX WARN: Type inference failed for: r7v40, types: [com.android.compose.animation.scene.content.state.TransitionState$Transition] */
    /* JADX WARN: Type inference failed for: r7v42 */
    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    /* renamed from: approachMeasure-3p2s80s */
    public final androidx.compose.ui.layout.MeasureResult mo603approachMeasure3p2s80s(androidx.compose.ui.layout.ApproachMeasureScope r26, androidx.compose.ui.layout.Measurable r27, long r28) {
        /*
            Method dump skipped, instructions count: 1530
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.ElementNode.mo603approachMeasure3p2s80s(androidx.compose.ui.layout.ApproachMeasureScope, androidx.compose.ui.layout.Measurable, long):androidx.compose.ui.layout.MeasureResult");
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

    /* JADX WARN: Code restructure failed: missing block: B:102:0x0111, code lost:
    
        if (r15 == null) goto L63;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0377  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x035c  */
    @Override // androidx.compose.ui.node.DrawModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void draw(androidx.compose.ui.node.LayoutNodeDrawScope r25) {
        /*
            Method dump skipped, instructions count: 1019
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.ElementNode.draw(androidx.compose.ui.node.LayoutNodeDrawScope):void");
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
    public final boolean mo604isMeasurementApproachInProgressozmzZPI(long j) {
        boolean isAnyStateTransitioning = isAnyStateTransitioning();
        if (!isAnyStateTransitioning) {
            Element.State stateInContent = getStateInContent();
            Element.Companion.getClass();
            long j2 = Element.SizeUnspecified;
            ((SnapshotMutableStateImpl) stateInContent.approachSize$delegate).setValue(IntSize.m859boximpl(j2));
        }
        return isAnyStateTransitioning;
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    public final boolean isPlacementApproachInProgress(Placeable.PlacementScope placementScope, LayoutCoordinates layoutCoordinates) {
        return isAnyStateTransitioning();
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode, androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        if (!measureScope.isLookingAhead()) {
            throw new IllegalStateException("Check failed.");
        }
        Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
        Element.State stateInContent = getStateInContent();
        long size = ElementKt.size(mo608measureBRTryo0);
        ((SnapshotMutableStateImpl) stateInContent.targetSize$delegate).setValue(IntSize.m859boximpl(size));
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new ElementNode$$ExternalSyntheticLambda0(mo608measureBRTryo0, this, 0));
        return layout$1;
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
        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        List list = sceneTransitionLayoutImpl.ancestors;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            createListBuilder.add(((Ancestor) list.get(i)).inContent);
        }
        createListBuilder.add(content.getKey());
        Element.State state = new Element.State(createListBuilder.build());
        snapshotStateMap.put(content.getKey(), state);
        List list2 = sceneTransitionLayoutImpl.ancestors;
        int size2 = list2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            snapshotStateMap.putIfAbsent(((Ancestor) list2.get(i2)).inContent, state);
        }
    }
}
