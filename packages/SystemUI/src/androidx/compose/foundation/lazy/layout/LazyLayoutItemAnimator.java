package androidx.compose.foundation.lazy.layout;

import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterMapKt;
import androidx.collection.ScatterSetKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.graphics.layer.GraphicsLayerKt;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.DrawModifierNodeKt;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyLayoutItemAnimator<T extends LazyLayoutMeasuredItem> {
    public DrawModifierNode displayingNode;
    public int firstVisibleIndex;
    public LazyLayoutKeyIndexMap keyIndexMap;
    public final MutableScatterMap keyToItemInfoMap = ScatterMapKt.mutableScatterMapOf();
    public final MutableScatterSet movingAwayKeys = ScatterSetKt.mutableScatterSetOf();
    public final List movingInFromStartBound = new ArrayList();
    public final List movingInFromEndBound = new ArrayList();
    public final List movingAwayToStartBound = new ArrayList();
    public final List movingAwayToEndBound = new ArrayList();
    public final List disappearingItems = new ArrayList();
    public final Modifier modifier = new DisplayingDisappearingItemsElement(this);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class DisplayingDisappearingItemsElement extends ModifierNodeElement<DisplayingDisappearingItemsNode> {
        public final LazyLayoutItemAnimator animator;

        public DisplayingDisappearingItemsElement(LazyLayoutItemAnimator<?> lazyLayoutItemAnimator) {
            this.animator = lazyLayoutItemAnimator;
        }

        @Override // androidx.compose.ui.node.ModifierNodeElement
        public final Modifier.Node create() {
            return new DisplayingDisappearingItemsNode(this.animator);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DisplayingDisappearingItemsElement) && Intrinsics.areEqual(this.animator, ((DisplayingDisappearingItemsElement) obj).animator);
        }

        public final int hashCode() {
            return this.animator.hashCode();
        }

        public final String toString() {
            return "DisplayingDisappearingItemsElement(animator=" + this.animator + ')';
        }

        @Override // androidx.compose.ui.node.ModifierNodeElement
        public final void update(Modifier.Node node) {
            DisplayingDisappearingItemsNode displayingDisappearingItemsNode = (DisplayingDisappearingItemsNode) node;
            LazyLayoutItemAnimator lazyLayoutItemAnimator = displayingDisappearingItemsNode.animator;
            LazyLayoutItemAnimator lazyLayoutItemAnimator2 = this.animator;
            if (Intrinsics.areEqual(lazyLayoutItemAnimator, lazyLayoutItemAnimator2) || !displayingDisappearingItemsNode.node.isAttached) {
                return;
            }
            LazyLayoutItemAnimator lazyLayoutItemAnimator3 = displayingDisappearingItemsNode.animator;
            lazyLayoutItemAnimator3.releaseAnimations();
            lazyLayoutItemAnimator3.keyIndexMap = null;
            lazyLayoutItemAnimator3.firstVisibleIndex = -1;
            lazyLayoutItemAnimator2.displayingNode = displayingDisappearingItemsNode;
            displayingDisappearingItemsNode.animator = lazyLayoutItemAnimator2;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class DisplayingDisappearingItemsNode extends Modifier.Node implements DrawModifierNode {
        public LazyLayoutItemAnimator animator;

        public DisplayingDisappearingItemsNode(LazyLayoutItemAnimator<?> lazyLayoutItemAnimator) {
            this.animator = lazyLayoutItemAnimator;
        }

        @Override // androidx.compose.ui.node.DrawModifierNode
        public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
            ArrayList arrayList = (ArrayList) this.animator.disappearingItems;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                LazyLayoutItemAnimation lazyLayoutItemAnimation = (LazyLayoutItemAnimation) arrayList.get(i);
                GraphicsLayer graphicsLayer = lazyLayoutItemAnimation.layer;
                if (graphicsLayer != null) {
                    long j = lazyLayoutItemAnimation.finalOffset;
                    IntOffset.Companion companion = IntOffset.Companion;
                    long j2 = graphicsLayer.topLeft;
                    float f = ((int) (j >> 32)) - ((int) (j2 >> 32));
                    float f2 = ((int) (j & 4294967295L)) - ((int) (4294967295L & j2));
                    CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                    canvasDrawScope.drawContext.transform.translate(f, f2);
                    try {
                        GraphicsLayerKt.drawLayer(layoutNodeDrawScope, graphicsLayer);
                    } finally {
                        canvasDrawScope.drawContext.transform.translate(-f, -f2);
                    }
                }
            }
            layoutNodeDrawScope.drawContent();
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DisplayingDisappearingItemsNode) && Intrinsics.areEqual(this.animator, ((DisplayingDisappearingItemsNode) obj).animator);
        }

        public final int hashCode() {
            return this.animator.hashCode();
        }

        @Override // androidx.compose.ui.Modifier.Node
        public final void onAttach() {
            this.animator.displayingNode = this;
        }

        @Override // androidx.compose.ui.Modifier.Node
        public final void onDetach() {
            LazyLayoutItemAnimator lazyLayoutItemAnimator = this.animator;
            lazyLayoutItemAnimator.releaseAnimations();
            lazyLayoutItemAnimator.keyIndexMap = null;
            lazyLayoutItemAnimator.firstVisibleIndex = -1;
        }

        public final String toString() {
            return "DisplayingDisappearingItemsNode(animator=" + this.animator + ')';
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class ItemInfo {
        public Constraints constraints;
        public int crossAxisOffset;
        public int lane;
        public int layoutMaxOffset;
        public int layoutMinOffset;
        public LazyLayoutItemAnimation[] animations = LazyLayoutItemAnimatorKt.EmptyArray;
        public int span = 1;

        public ItemInfo() {
        }

        public static void updateAnimation$default(ItemInfo itemInfo, LazyLayoutMeasuredItem lazyLayoutMeasuredItem, CoroutineScope coroutineScope, GraphicsContext graphicsContext, int i, int i2) {
            long j;
            LazyLayoutItemAnimator.this.getClass();
            long mo154getOffsetBjo55l4 = lazyLayoutMeasuredItem.mo154getOffsetBjo55l4(0);
            if (lazyLayoutMeasuredItem.isVertical()) {
                IntOffset.Companion companion = IntOffset.Companion;
                j = mo154getOffsetBjo55l4 >> 32;
            } else {
                IntOffset.Companion companion2 = IntOffset.Companion;
                j = mo154getOffsetBjo55l4 & 4294967295L;
            }
            itemInfo.updateAnimation(lazyLayoutMeasuredItem, coroutineScope, graphicsContext, i, i2, (int) j);
        }

        public final void updateAnimation(LazyLayoutMeasuredItem lazyLayoutMeasuredItem, CoroutineScope coroutineScope, GraphicsContext graphicsContext, int i, int i2, int i3) {
            LazyLayoutItemAnimation[] lazyLayoutItemAnimationArr = this.animations;
            int length = lazyLayoutItemAnimationArr.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    this.layoutMinOffset = i;
                    this.layoutMaxOffset = i2;
                    break;
                } else {
                    LazyLayoutItemAnimation lazyLayoutItemAnimation = lazyLayoutItemAnimationArr[i4];
                    if (lazyLayoutItemAnimation != null && lazyLayoutItemAnimation.isRunningMovingAwayAnimation) {
                        break;
                    } else {
                        i4++;
                    }
                }
            }
            int length2 = this.animations.length;
            for (int placeablesCount = lazyLayoutMeasuredItem.getPlaceablesCount(); placeablesCount < length2; placeablesCount++) {
                LazyLayoutItemAnimation lazyLayoutItemAnimation2 = this.animations[placeablesCount];
                if (lazyLayoutItemAnimation2 != null) {
                    lazyLayoutItemAnimation2.release();
                }
            }
            if (this.animations.length != lazyLayoutMeasuredItem.getPlaceablesCount()) {
                this.animations = (LazyLayoutItemAnimation[]) Arrays.copyOf(this.animations, lazyLayoutMeasuredItem.getPlaceablesCount());
            }
            this.constraints = Constraints.m813boximpl(lazyLayoutMeasuredItem.mo152getConstraintsmsEJaDk());
            this.crossAxisOffset = i3;
            this.lane = lazyLayoutMeasuredItem.getLane();
            this.span = lazyLayoutMeasuredItem.getSpan();
            int placeablesCount2 = lazyLayoutMeasuredItem.getPlaceablesCount();
            for (int i5 = 0; i5 < placeablesCount2; i5++) {
                Object parentData = lazyLayoutMeasuredItem.getParentData(i5);
                LazyLayoutAnimationSpecsNode lazyLayoutAnimationSpecsNode = parentData instanceof LazyLayoutAnimationSpecsNode ? (LazyLayoutAnimationSpecsNode) parentData : null;
                if (lazyLayoutAnimationSpecsNode == null) {
                    LazyLayoutItemAnimation lazyLayoutItemAnimation3 = this.animations[i5];
                    if (lazyLayoutItemAnimation3 != null) {
                        lazyLayoutItemAnimation3.release();
                    }
                    this.animations[i5] = null;
                } else {
                    LazyLayoutItemAnimation lazyLayoutItemAnimation4 = this.animations[i5];
                    if (lazyLayoutItemAnimation4 == null) {
                        final LazyLayoutItemAnimator lazyLayoutItemAnimator = LazyLayoutItemAnimator.this;
                        lazyLayoutItemAnimation4 = new LazyLayoutItemAnimation(coroutineScope, graphicsContext, new Function0() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimator$ItemInfo$updateAnimation$1$animation$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                DrawModifierNode drawModifierNode = lazyLayoutItemAnimator.displayingNode;
                                if (drawModifierNode != null) {
                                    DrawModifierNodeKt.invalidateDraw(drawModifierNode);
                                }
                                return Unit.INSTANCE;
                            }
                        });
                        this.animations[i5] = lazyLayoutItemAnimation4;
                    }
                    lazyLayoutItemAnimation4.fadeInSpec = lazyLayoutAnimationSpecsNode.fadeInSpec;
                    lazyLayoutItemAnimation4.placementSpec = lazyLayoutAnimationSpecsNode.placementSpec;
                    lazyLayoutItemAnimation4.fadeOutSpec = lazyLayoutAnimationSpecsNode.fadeOutSpec;
                }
            }
        }
    }

    public static void initializeAnimation(LazyLayoutMeasuredItem lazyLayoutMeasuredItem, int i, ItemInfo itemInfo) {
        int i2 = 0;
        long mo154getOffsetBjo55l4 = lazyLayoutMeasuredItem.mo154getOffsetBjo55l4(0);
        long m848copyiSbpLlY$default = lazyLayoutMeasuredItem.isVertical() ? IntOffset.m848copyiSbpLlY$default(0, i, 1, mo154getOffsetBjo55l4) : IntOffset.m848copyiSbpLlY$default(i, 0, 2, mo154getOffsetBjo55l4);
        LazyLayoutItemAnimation[] lazyLayoutItemAnimationArr = itemInfo.animations;
        int length = lazyLayoutItemAnimationArr.length;
        int i3 = 0;
        while (i2 < length) {
            LazyLayoutItemAnimation lazyLayoutItemAnimation = lazyLayoutItemAnimationArr[i2];
            int i4 = i3 + 1;
            if (lazyLayoutItemAnimation != null) {
                lazyLayoutItemAnimation.rawOffset = IntOffset.m851plusqkQi6aY(m848copyiSbpLlY$default, IntOffset.m850minusqkQi6aY(lazyLayoutMeasuredItem.mo154getOffsetBjo55l4(i3), mo154getOffsetBjo55l4));
            }
            i2++;
            i3 = i4;
        }
    }

    public static int updateAndReturnOffsetFor(int[] iArr, LazyLayoutMeasuredItem lazyLayoutMeasuredItem) {
        int lane = lazyLayoutMeasuredItem.getLane();
        int span = lazyLayoutMeasuredItem.getSpan() + lane;
        int i = 0;
        while (lane < span) {
            int mainAxisSizeWithSpacings = lazyLayoutMeasuredItem.getMainAxisSizeWithSpacings() + iArr[lane];
            iArr[lane] = mainAxisSizeWithSpacings;
            i = Math.max(i, mainAxisSizeWithSpacings);
            lane++;
        }
        return i;
    }

    public final LazyLayoutItemAnimation getAnimation(int i, Object obj) {
        LazyLayoutItemAnimation[] lazyLayoutItemAnimationArr;
        ItemInfo itemInfo = (ItemInfo) this.keyToItemInfoMap.get(obj);
        if (itemInfo == null || (lazyLayoutItemAnimationArr = itemInfo.animations) == null) {
            return null;
        }
        return lazyLayoutItemAnimationArr[i];
    }

    /* renamed from: getMinSizeToFitDisappearingItems-YbymL2g, reason: not valid java name */
    public final long m167getMinSizeToFitDisappearingItemsYbymL2g() {
        IntSize.Companion.getClass();
        List list = this.disappearingItems;
        int size = list.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            LazyLayoutItemAnimation lazyLayoutItemAnimation = (LazyLayoutItemAnimation) ((ArrayList) list).get(i);
            GraphicsLayer graphicsLayer = lazyLayoutItemAnimation.layer;
            if (graphicsLayer != null) {
                long j2 = lazyLayoutItemAnimation.rawOffset;
                IntOffset.Companion companion = IntOffset.Companion;
                j = (Math.max((int) (j & 4294967295L), ((int) (lazyLayoutItemAnimation.rawOffset & 4294967295L)) + ((int) (graphicsLayer.size & 4294967295L))) & 4294967295L) | (Math.max((int) (j >> 32), ((int) (j2 >> 32)) + ((int) (graphicsLayer.size >> 32))) << 32);
            }
        }
        return j;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:186:0x03f7  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x040a  */
    /* JADX WARN: Type inference failed for: r14v8, types: [kotlin.coroutines.Continuation, kotlin.coroutines.CoroutineContext, kotlinx.coroutines.CoroutineStart] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMeasured(int r43, int r44, int r45, java.util.List r46, final androidx.compose.foundation.lazy.layout.LazyLayoutKeyIndexMap r47, androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItemProvider r48, boolean r49, boolean r50, int r51, boolean r52, int r53, int r54, kotlinx.coroutines.CoroutineScope r55, androidx.compose.ui.graphics.GraphicsContext r56) {
        /*
            Method dump skipped, instructions count: 1588
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimator.onMeasured(int, int, int, java.util.List, androidx.compose.foundation.lazy.layout.LazyLayoutKeyIndexMap, androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItemProvider, boolean, boolean, int, boolean, int, int, kotlinx.coroutines.CoroutineScope, androidx.compose.ui.graphics.GraphicsContext):void");
    }

    public final void releaseAnimations() {
        MutableScatterMap mutableScatterMap = this.keyToItemInfoMap;
        if (mutableScatterMap._size != 0) {
            Object[] objArr = mutableScatterMap.values;
            long[] jArr = mutableScatterMap.metadata;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i = 0;
                while (true) {
                    long j = jArr[i];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i2 = 8 - ((~(i - length)) >>> 31);
                        for (int i3 = 0; i3 < i2; i3++) {
                            if ((255 & j) < 128) {
                                for (LazyLayoutItemAnimation lazyLayoutItemAnimation : ((ItemInfo) objArr[(i << 3) + i3]).animations) {
                                    if (lazyLayoutItemAnimation != null) {
                                        lazyLayoutItemAnimation.release();
                                    }
                                }
                            }
                            j >>= 8;
                        }
                        if (i2 != 8) {
                            break;
                        }
                    }
                    if (i == length) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            mutableScatterMap.clear();
        }
    }

    public final void removeInfoForKey(Object obj) {
        LazyLayoutItemAnimation[] lazyLayoutItemAnimationArr;
        ItemInfo itemInfo = (ItemInfo) this.keyToItemInfoMap.remove(obj);
        if (itemInfo == null || (lazyLayoutItemAnimationArr = itemInfo.animations) == null) {
            return;
        }
        for (LazyLayoutItemAnimation lazyLayoutItemAnimation : lazyLayoutItemAnimationArr) {
            if (lazyLayoutItemAnimation != null) {
                lazyLayoutItemAnimation.release();
            }
        }
    }

    public final void startPlacementAnimationsIfNeeded(LazyLayoutMeasuredItem lazyLayoutMeasuredItem, boolean z) {
        Object obj = this.keyToItemInfoMap.get(lazyLayoutMeasuredItem.getKey());
        obj.getClass();
        LazyLayoutItemAnimation[] lazyLayoutItemAnimationArr = ((ItemInfo) obj).animations;
        int length = lazyLayoutItemAnimationArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            LazyLayoutItemAnimation lazyLayoutItemAnimation = lazyLayoutItemAnimationArr[i];
            int i3 = i2 + 1;
            if (lazyLayoutItemAnimation != null) {
                long mo154getOffsetBjo55l4 = lazyLayoutMeasuredItem.mo154getOffsetBjo55l4(i2);
                long j = lazyLayoutItemAnimation.rawOffset;
                LazyLayoutItemAnimation.Companion.getClass();
                if (!IntOffset.m849equalsimpl0(j, LazyLayoutItemAnimation.NotInitialized) && !IntOffset.m849equalsimpl0(j, mo154getOffsetBjo55l4)) {
                    long m850minusqkQi6aY = IntOffset.m850minusqkQi6aY(mo154getOffsetBjo55l4, j);
                    FiniteAnimationSpec finiteAnimationSpec = lazyLayoutItemAnimation.placementSpec;
                    if (finiteAnimationSpec != null) {
                        long m850minusqkQi6aY2 = IntOffset.m850minusqkQi6aY(((IntOffset) ((SnapshotMutableStateImpl) lazyLayoutItemAnimation.placementDelta$delegate).getValue()).packedValue, m850minusqkQi6aY);
                        lazyLayoutItemAnimation.m166setPlacementDeltagyyYBs(m850minusqkQi6aY2);
                        lazyLayoutItemAnimation.setPlacementAnimationInProgress(true);
                        lazyLayoutItemAnimation.isRunningMovingAwayAnimation = z;
                        BuildersKt.launch$default(lazyLayoutItemAnimation.coroutineScope, null, null, new LazyLayoutItemAnimation$animatePlacementDelta$1(lazyLayoutItemAnimation, finiteAnimationSpec, m850minusqkQi6aY2, null), 3);
                    }
                }
                lazyLayoutItemAnimation.rawOffset = mo154getOffsetBjo55l4;
            }
            i++;
            i2 = i3;
        }
    }
}
