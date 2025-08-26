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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

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
            long jMo155getOffsetBjo55l4 = lazyLayoutMeasuredItem.mo155getOffsetBjo55l4(0);
            if (lazyLayoutMeasuredItem.isVertical()) {
                IntOffset.Companion companion = IntOffset.Companion;
                j = jMo155getOffsetBjo55l4 >> 32;
            } else {
                IntOffset.Companion companion2 = IntOffset.Companion;
                j = jMo155getOffsetBjo55l4 & 4294967295L;
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
            this.constraints = Constraints.m815boximpl(lazyLayoutMeasuredItem.mo153getConstraintsmsEJaDk());
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
        long jMo155getOffsetBjo55l4 = lazyLayoutMeasuredItem.mo155getOffsetBjo55l4(0);
        long jM850copyiSbpLlY$default = lazyLayoutMeasuredItem.isVertical() ? IntOffset.m850copyiSbpLlY$default(0, i, 1, jMo155getOffsetBjo55l4) : IntOffset.m850copyiSbpLlY$default(i, 0, 2, jMo155getOffsetBjo55l4);
        LazyLayoutItemAnimation[] lazyLayoutItemAnimationArr = itemInfo.animations;
        int length = lazyLayoutItemAnimationArr.length;
        int i3 = 0;
        while (i2 < length) {
            LazyLayoutItemAnimation lazyLayoutItemAnimation = lazyLayoutItemAnimationArr[i2];
            int i4 = i3 + 1;
            if (lazyLayoutItemAnimation != null) {
                lazyLayoutItemAnimation.rawOffset = IntOffset.m853plusqkQi6aY(jM850copyiSbpLlY$default, IntOffset.m852minusqkQi6aY(lazyLayoutMeasuredItem.mo155getOffsetBjo55l4(i3), jMo155getOffsetBjo55l4));
            }
            i2++;
            i3 = i4;
        }
    }

    public static int updateAndReturnOffsetFor(int[] iArr, LazyLayoutMeasuredItem lazyLayoutMeasuredItem) {
        int lane = lazyLayoutMeasuredItem.getLane();
        int span = lazyLayoutMeasuredItem.getSpan() + lane;
        int iMax = 0;
        while (lane < span) {
            int mainAxisSizeWithSpacings = lazyLayoutMeasuredItem.getMainAxisSizeWithSpacings() + iArr[lane];
            iArr[lane] = mainAxisSizeWithSpacings;
            iMax = Math.max(iMax, mainAxisSizeWithSpacings);
            lane++;
        }
        return iMax;
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
    public final long m168getMinSizeToFitDisappearingItemsYbymL2g() {
        IntSize.Companion.getClass();
        List list = this.disappearingItems;
        int size = list.size();
        long jMax = 0;
        for (int i = 0; i < size; i++) {
            LazyLayoutItemAnimation lazyLayoutItemAnimation = (LazyLayoutItemAnimation) ((ArrayList) list).get(i);
            GraphicsLayer graphicsLayer = lazyLayoutItemAnimation.layer;
            if (graphicsLayer != null) {
                long j = lazyLayoutItemAnimation.rawOffset;
                IntOffset.Companion companion = IntOffset.Companion;
                jMax = (Math.max((int) (jMax & 4294967295L), ((int) (lazyLayoutItemAnimation.rawOffset & 4294967295L)) + ((int) (graphicsLayer.size & 4294967295L))) & 4294967295L) | (Math.max((int) (jMax >> 32), ((int) (j >> 32)) + ((int) (graphicsLayer.size >> 32))) << 32);
            }
        }
        return jMax;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:177:0x03ed  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00ea  */
    /* JADX WARN: Type inference failed for: r14v8, types: [kotlin.coroutines.Continuation, kotlin.coroutines.CoroutineContext, kotlinx.coroutines.CoroutineStart] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasured(int i, int i2, int i3, List list, final LazyLayoutKeyIndexMap lazyLayoutKeyIndexMap, LazyLayoutMeasuredItemProvider lazyLayoutMeasuredItemProvider, boolean z, boolean z2, int i4, boolean z3, int i5, int i6, CoroutineScope coroutineScope, GraphicsContext graphicsContext) {
        MutableScatterMap mutableScatterMap;
        int i7;
        Object obj;
        int i8;
        boolean z4;
        LazyLayoutKeyIndexMap lazyLayoutKeyIndexMap2;
        long j;
        long j2;
        int i9;
        final LazyLayoutKeyIndexMap lazyLayoutKeyIndexMap3;
        MutableScatterSet mutableScatterSet;
        MutableScatterMap mutableScatterMap2;
        int i10;
        MutableScatterMap mutableScatterMap3;
        int mainAxisSizeWithSpacings;
        long j3;
        int i11;
        long j4;
        Object[] objArr;
        long[] jArr;
        Object[] objArr2;
        long[] jArr2;
        int i12;
        MutableScatterSet mutableScatterSet2;
        MutableScatterMap mutableScatterMap4;
        long j5;
        LazyLayoutMeasuredItem lazyLayoutMeasuredItem;
        int i13;
        LazyLayoutItemAnimation[] lazyLayoutItemAnimationArr;
        long j6;
        int i14;
        int i15;
        int i16;
        int i17;
        LazyLayoutKeyIndexMap lazyLayoutKeyIndexMap4;
        int i18;
        int i19;
        long j7;
        int i20;
        int i21 = i4;
        LazyLayoutKeyIndexMap lazyLayoutKeyIndexMap5 = this.keyIndexMap;
        this.keyIndexMap = lazyLayoutKeyIndexMap;
        int size = list.size();
        int i22 = 0;
        loop0: while (true) {
            mutableScatterMap = this.keyToItemInfoMap;
            if (i22 < size) {
                LazyLayoutMeasuredItem lazyLayoutMeasuredItem2 = (LazyLayoutMeasuredItem) ((ArrayList) list).get(i22);
                int placeablesCount = lazyLayoutMeasuredItem2.getPlaceablesCount();
                i7 = 3;
                for (int i23 = 0; i23 < placeablesCount; i23++) {
                    obj = null;
                    Object parentData = lazyLayoutMeasuredItem2.getParentData(i23);
                    i8 = 1;
                    if ((parentData instanceof LazyLayoutAnimationSpecsNode ? (LazyLayoutAnimationSpecsNode) parentData : null) != null) {
                        break loop0;
                    }
                }
                i22++;
            } else {
                i7 = 3;
                obj = null;
                i8 = 1;
                if (mutableScatterMap.isEmpty()) {
                    releaseAnimations();
                    return;
                }
            }
        }
        int i24 = this.firstVisibleIndex;
        LazyLayoutMeasuredItem lazyLayoutMeasuredItem3 = (LazyLayoutMeasuredItem) CollectionsKt___CollectionsKt.firstOrNull(list);
        this.firstVisibleIndex = lazyLayoutMeasuredItem3 != null ? lazyLayoutMeasuredItem3.getIndex() : 0;
        if (z) {
            j = 4294967295L;
            z4 = 32;
            lazyLayoutKeyIndexMap2 = lazyLayoutKeyIndexMap5;
            j2 = (i & 4294967295L) | (0 << 32);
            IntOffset.Companion companion = IntOffset.Companion;
        } else {
            z4 = 32;
            lazyLayoutKeyIndexMap2 = lazyLayoutKeyIndexMap5;
            j = 4294967295L;
            j2 = (i << 32) | (0 & 4294967295L);
            IntOffset.Companion companion2 = IntOffset.Companion;
        }
        int i25 = (z2 || !z3) ? i8 : 0;
        Object[] objArr3 = mutableScatterMap.keys;
        long[] jArr3 = mutableScatterMap.metadata;
        int length = jArr3.length - 2;
        boolean z5 = z4;
        MutableScatterSet mutableScatterSet3 = this.movingAwayKeys;
        if (length >= 0) {
            int i26 = 0;
            while (true) {
                long j8 = jArr3[i26];
                i9 = i25;
                if ((((~j8) << 7) & j8 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i27 = 8 - ((~(i26 - length)) >>> 31);
                    int i28 = 0;
                    while (i28 < i27) {
                        if ((j8 & 255) < 128) {
                            i20 = i28;
                            mutableScatterSet3.add(objArr3[(i26 << 3) + i28]);
                        } else {
                            i20 = i28;
                        }
                        j8 >>= 8;
                        i28 = i20 + 1;
                    }
                    if (i27 != 8) {
                        break;
                    }
                    if (i26 == length) {
                        break;
                    }
                    i26++;
                    i25 = i9;
                }
            }
        } else {
            i9 = i25;
        }
        int size2 = list.size();
        int i29 = 0;
        while (i29 < size2) {
            LazyLayoutMeasuredItem lazyLayoutMeasuredItem4 = (LazyLayoutMeasuredItem) ((ArrayList) list).get(i29);
            mutableScatterSet3.remove(lazyLayoutMeasuredItem4.getKey());
            int placeablesCount2 = lazyLayoutMeasuredItem4.getPlaceablesCount();
            int i30 = 0;
            while (true) {
                if (i30 >= placeablesCount2) {
                    i16 = size2;
                    i17 = i29;
                    lazyLayoutKeyIndexMap4 = lazyLayoutKeyIndexMap2;
                    removeInfoForKey(lazyLayoutMeasuredItem4.getKey());
                    break;
                }
                Object parentData2 = lazyLayoutMeasuredItem4.getParentData(i30);
                i16 = size2;
                if ((parentData2 instanceof LazyLayoutAnimationSpecsNode ? (LazyLayoutAnimationSpecsNode) parentData2 : obj) != null) {
                    ItemInfo itemInfo = (ItemInfo) mutableScatterMap.get(lazyLayoutMeasuredItem4.getKey());
                    int index = lazyLayoutKeyIndexMap2 != null ? ((NearestRangeKeyIndexMap) lazyLayoutKeyIndexMap2).getIndex(lazyLayoutMeasuredItem4.getKey()) : -1;
                    int i31 = (index != -1 || lazyLayoutKeyIndexMap2 == null) ? 0 : i8;
                    if (itemInfo == null) {
                        ItemInfo itemInfo2 = new ItemInfo();
                        ItemInfo.updateAnimation$default(itemInfo2, lazyLayoutMeasuredItem4, coroutineScope, graphicsContext, i5, i6);
                        mutableScatterMap.set(lazyLayoutMeasuredItem4.getKey(), itemInfo2);
                        if (lazyLayoutMeasuredItem4.getIndex() == index || index == -1) {
                            long jMo155getOffsetBjo55l4 = lazyLayoutMeasuredItem4.mo155getOffsetBjo55l4(0);
                            if (lazyLayoutMeasuredItem4.isVertical()) {
                                IntOffset.Companion companion3 = IntOffset.Companion;
                                i17 = i29;
                                j7 = jMo155getOffsetBjo55l4 & j;
                            } else {
                                i17 = i29;
                                IntOffset.Companion companion4 = IntOffset.Companion;
                                j7 = jMo155getOffsetBjo55l4 >> (z5 ? 1L : 0L);
                            }
                            initializeAnimation(lazyLayoutMeasuredItem4, (int) j7, itemInfo2);
                            if (i31 != 0) {
                                for (LazyLayoutItemAnimation lazyLayoutItemAnimation : itemInfo2.animations) {
                                    if (lazyLayoutItemAnimation != null) {
                                        lazyLayoutItemAnimation.animateAppearance();
                                        Unit unit = Unit.INSTANCE;
                                    }
                                }
                            }
                        } else {
                            if (index < i24) {
                                ((ArrayList) this.movingInFromStartBound).add(lazyLayoutMeasuredItem4);
                            } else {
                                ((ArrayList) this.movingInFromEndBound).add(lazyLayoutMeasuredItem4);
                            }
                            i17 = i29;
                        }
                    } else {
                        i17 = i29;
                        if (i9 != 0) {
                            ItemInfo.updateAnimation$default(itemInfo, lazyLayoutMeasuredItem4, coroutineScope, graphicsContext, i5, i6);
                            LazyLayoutItemAnimation[] lazyLayoutItemAnimationArr2 = itemInfo.animations;
                            int length2 = lazyLayoutItemAnimationArr2.length;
                            int i32 = 0;
                            while (i32 < length2) {
                                LazyLayoutItemAnimation[] lazyLayoutItemAnimationArr3 = lazyLayoutItemAnimationArr2;
                                LazyLayoutItemAnimation lazyLayoutItemAnimation2 = lazyLayoutItemAnimationArr3[i32];
                                LazyLayoutKeyIndexMap lazyLayoutKeyIndexMap6 = lazyLayoutKeyIndexMap2;
                                int i33 = i31;
                                if (lazyLayoutItemAnimation2 != null) {
                                    long j9 = lazyLayoutItemAnimation2.rawOffset;
                                    LazyLayoutItemAnimation.Companion.getClass();
                                    i18 = length2;
                                    i19 = i32;
                                    if (!IntOffset.m851equalsimpl0(j9, LazyLayoutItemAnimation.NotInitialized)) {
                                        lazyLayoutItemAnimation2.rawOffset = IntOffset.m853plusqkQi6aY(lazyLayoutItemAnimation2.rawOffset, j2);
                                    }
                                } else {
                                    i18 = length2;
                                    i19 = i32;
                                }
                                i32 = i19 + 1;
                                i31 = i33;
                                lazyLayoutItemAnimationArr2 = lazyLayoutItemAnimationArr3;
                                lazyLayoutKeyIndexMap2 = lazyLayoutKeyIndexMap6;
                                length2 = i18;
                            }
                            lazyLayoutKeyIndexMap4 = lazyLayoutKeyIndexMap2;
                            if (i31 != 0) {
                                for (LazyLayoutItemAnimation lazyLayoutItemAnimation3 : itemInfo.animations) {
                                    if (lazyLayoutItemAnimation3 != null) {
                                        if (lazyLayoutItemAnimation3.isDisappearanceAnimationInProgress()) {
                                            ((ArrayList) this.disappearingItems).remove(lazyLayoutItemAnimation3);
                                            DrawModifierNode drawModifierNode = this.displayingNode;
                                            if (drawModifierNode != null) {
                                                DrawModifierNodeKt.invalidateDraw(drawModifierNode);
                                                Unit unit2 = Unit.INSTANCE;
                                            }
                                        }
                                        lazyLayoutItemAnimation3.animateAppearance();
                                    }
                                }
                            }
                            startPlacementAnimationsIfNeeded(lazyLayoutMeasuredItem4, false);
                        }
                    }
                    lazyLayoutKeyIndexMap4 = lazyLayoutKeyIndexMap2;
                } else {
                    i30++;
                    size2 = i16;
                }
            }
            i29 = i17 + 1;
            size2 = i16;
            lazyLayoutKeyIndexMap2 = lazyLayoutKeyIndexMap4;
        }
        LazyLayoutKeyIndexMap lazyLayoutKeyIndexMap7 = lazyLayoutKeyIndexMap2;
        int[] iArr = new int[i21];
        if (i9 == 0 || lazyLayoutKeyIndexMap7 == null) {
            lazyLayoutKeyIndexMap3 = lazyLayoutKeyIndexMap7;
        } else {
            if (((ArrayList) this.movingInFromStartBound).isEmpty()) {
                lazyLayoutKeyIndexMap3 = lazyLayoutKeyIndexMap7;
            } else {
                ArrayList arrayList = (ArrayList) this.movingInFromStartBound;
                if (arrayList.size() > i8) {
                    lazyLayoutKeyIndexMap3 = lazyLayoutKeyIndexMap7;
                    CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList, new Comparator() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimator$onMeasured$$inlined$sortByDescending$1
                        @Override // java.util.Comparator
                        public final int compare(Object obj2, Object obj3) {
                            return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((NearestRangeKeyIndexMap) lazyLayoutKeyIndexMap3).getIndex(((LazyLayoutMeasuredItem) obj3).getKey())), Integer.valueOf(((NearestRangeKeyIndexMap) lazyLayoutKeyIndexMap3).getIndex(((LazyLayoutMeasuredItem) obj2).getKey())));
                        }
                    });
                } else {
                    lazyLayoutKeyIndexMap3 = lazyLayoutKeyIndexMap7;
                }
                ArrayList arrayList2 = (ArrayList) this.movingInFromStartBound;
                int size3 = arrayList2.size();
                for (int i34 = 0; i34 < size3; i34++) {
                    LazyLayoutMeasuredItem lazyLayoutMeasuredItem5 = (LazyLayoutMeasuredItem) arrayList2.get(i34);
                    int iUpdateAndReturnOffsetFor = i5 - updateAndReturnOffsetFor(iArr, lazyLayoutMeasuredItem5);
                    Object obj2 = mutableScatterMap.get(lazyLayoutMeasuredItem5.getKey());
                    obj2.getClass();
                    initializeAnimation(lazyLayoutMeasuredItem5, iUpdateAndReturnOffsetFor, (ItemInfo) obj2);
                    startPlacementAnimationsIfNeeded(lazyLayoutMeasuredItem5, false);
                }
                Arrays.fill(iArr, 0, iArr.length, 0);
            }
            if (!((ArrayList) this.movingInFromEndBound).isEmpty()) {
                ArrayList arrayList3 = (ArrayList) this.movingInFromEndBound;
                if (arrayList3.size() > 1) {
                    CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList3, new Comparator() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimator$onMeasured$$inlined$sortBy$1
                        @Override // java.util.Comparator
                        public final int compare(Object obj3, Object obj4) {
                            return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((NearestRangeKeyIndexMap) lazyLayoutKeyIndexMap3).getIndex(((LazyLayoutMeasuredItem) obj3).getKey())), Integer.valueOf(((NearestRangeKeyIndexMap) lazyLayoutKeyIndexMap3).getIndex(((LazyLayoutMeasuredItem) obj4).getKey())));
                        }
                    });
                }
                ArrayList arrayList4 = (ArrayList) this.movingInFromEndBound;
                int size4 = arrayList4.size();
                for (int i35 = 0; i35 < size4; i35++) {
                    LazyLayoutMeasuredItem lazyLayoutMeasuredItem6 = (LazyLayoutMeasuredItem) arrayList4.get(i35);
                    int iUpdateAndReturnOffsetFor2 = (updateAndReturnOffsetFor(iArr, lazyLayoutMeasuredItem6) + i6) - lazyLayoutMeasuredItem6.getMainAxisSizeWithSpacings();
                    Object obj3 = mutableScatterMap.get(lazyLayoutMeasuredItem6.getKey());
                    obj3.getClass();
                    initializeAnimation(lazyLayoutMeasuredItem6, iUpdateAndReturnOffsetFor2, (ItemInfo) obj3);
                    startPlacementAnimationsIfNeeded(lazyLayoutMeasuredItem6, false);
                }
                Arrays.fill(iArr, 0, iArr.length, 0);
            }
        }
        Object[] objArr4 = mutableScatterSet3.elements;
        long[] jArr4 = mutableScatterSet3.metadata;
        int length3 = jArr4.length - 2;
        if (length3 >= 0) {
            int i36 = 0;
            MutableScatterMap mutableScatterMap5 = mutableScatterMap;
            while (true) {
                long j10 = jArr4[i36];
                LazyLayoutKeyIndexMap lazyLayoutKeyIndexMap8 = lazyLayoutKeyIndexMap3;
                if ((((~j10) << 7) & j10 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i37 = 8 - ((~(i36 - length3)) >>> 31);
                    int i38 = 0;
                    MutableScatterMap mutableScatterMap6 = mutableScatterMap5;
                    while (i38 < i37) {
                        if ((j10 & 255) < 128) {
                            objArr2 = objArr4;
                            Object obj4 = objArr2[(i36 << 3) + i38];
                            jArr2 = jArr4;
                            ItemInfo itemInfo3 = (ItemInfo) mutableScatterMap6.get(obj4);
                            if (itemInfo3 != null) {
                                i12 = i38;
                                int index2 = ((NearestRangeKeyIndexMap) lazyLayoutKeyIndexMap).getIndex(obj4);
                                mutableScatterSet2 = mutableScatterSet3;
                                int iMin = Math.min(i21, itemInfo3.span);
                                itemInfo3.span = iMin;
                                itemInfo3.lane = Math.min(i21 - iMin, itemInfo3.lane);
                                if (index2 == -1) {
                                    LazyLayoutItemAnimation[] lazyLayoutItemAnimationArr4 = itemInfo3.animations;
                                    int length4 = lazyLayoutItemAnimationArr4.length;
                                    int i39 = 0;
                                    boolean z6 = false;
                                    int i40 = 0;
                                    while (i39 < length4) {
                                        int i41 = i39;
                                        LazyLayoutItemAnimation lazyLayoutItemAnimation4 = lazyLayoutItemAnimationArr4[i41];
                                        int i42 = i40 + 1;
                                        if (lazyLayoutItemAnimation4 != null) {
                                            if (lazyLayoutItemAnimation4.isDisappearanceAnimationInProgress()) {
                                                lazyLayoutItemAnimationArr = lazyLayoutItemAnimationArr4;
                                                i14 = length4;
                                                j6 = j10;
                                                i15 = i7;
                                            } else {
                                                lazyLayoutItemAnimationArr = lazyLayoutItemAnimationArr4;
                                                if (((Boolean) ((SnapshotMutableStateImpl) lazyLayoutItemAnimation4.isDisappearanceAnimationFinished$delegate).getValue()).booleanValue()) {
                                                    lazyLayoutItemAnimation4.release();
                                                    itemInfo3.animations[i40] = obj;
                                                    ((ArrayList) this.disappearingItems).remove(lazyLayoutItemAnimation4);
                                                    DrawModifierNode drawModifierNode2 = this.displayingNode;
                                                    if (drawModifierNode2 != null) {
                                                        DrawModifierNodeKt.invalidateDraw(drawModifierNode2);
                                                        Unit unit3 = Unit.INSTANCE;
                                                    }
                                                } else {
                                                    GraphicsLayer graphicsLayer = lazyLayoutItemAnimation4.layer;
                                                    i14 = length4;
                                                    if (graphicsLayer != null) {
                                                        FiniteAnimationSpec finiteAnimationSpec = lazyLayoutItemAnimation4.fadeOutSpec;
                                                        if (lazyLayoutItemAnimation4.isDisappearanceAnimationInProgress() || finiteAnimationSpec == null) {
                                                            j6 = j10;
                                                            i15 = i7;
                                                        } else {
                                                            j6 = j10;
                                                            lazyLayoutItemAnimation4.setDisappearanceAnimationInProgress(true);
                                                            ?? r14 = obj;
                                                            LazyLayoutItemAnimation$animateDisappearance$1 lazyLayoutItemAnimation$animateDisappearance$1 = new LazyLayoutItemAnimation$animateDisappearance$1(lazyLayoutItemAnimation4, finiteAnimationSpec, graphicsLayer, r14);
                                                            i15 = i7;
                                                            BuildersKt.launch$default(lazyLayoutItemAnimation4.coroutineScope, r14, r14, lazyLayoutItemAnimation$animateDisappearance$1, i15);
                                                        }
                                                        if (lazyLayoutItemAnimation4.isDisappearanceAnimationInProgress()) {
                                                            ((ArrayList) this.disappearingItems).add(lazyLayoutItemAnimation4);
                                                            DrawModifierNode drawModifierNode3 = this.displayingNode;
                                                            if (drawModifierNode3 != null) {
                                                                DrawModifierNodeKt.invalidateDraw(drawModifierNode3);
                                                                Unit unit4 = Unit.INSTANCE;
                                                            }
                                                            obj = null;
                                                        } else {
                                                            lazyLayoutItemAnimation4.release();
                                                            obj = null;
                                                            itemInfo3.animations[i40] = null;
                                                        }
                                                    }
                                                    i39 = i41 + 1;
                                                    i7 = i15;
                                                    i40 = i42;
                                                    lazyLayoutItemAnimationArr4 = lazyLayoutItemAnimationArr;
                                                    length4 = i14;
                                                    j10 = j6;
                                                }
                                            }
                                            z6 = true;
                                            i39 = i41 + 1;
                                            i7 = i15;
                                            i40 = i42;
                                            lazyLayoutItemAnimationArr4 = lazyLayoutItemAnimationArr;
                                            length4 = i14;
                                            j10 = j6;
                                        } else {
                                            lazyLayoutItemAnimationArr = lazyLayoutItemAnimationArr4;
                                        }
                                        i14 = length4;
                                        j6 = j10;
                                        i15 = i7;
                                        i39 = i41 + 1;
                                        i7 = i15;
                                        i40 = i42;
                                        lazyLayoutItemAnimationArr4 = lazyLayoutItemAnimationArr;
                                        length4 = i14;
                                        j10 = j6;
                                    }
                                    j5 = j10;
                                    if (!z6) {
                                        removeInfoForKey(obj4);
                                    }
                                    mutableScatterMap4 = mutableScatterMap6;
                                } else {
                                    j5 = j10;
                                    Constraints constraints = itemInfo3.constraints;
                                    constraints.getClass();
                                    MutableScatterMap mutableScatterMap7 = mutableScatterMap6;
                                    LazyLayoutMeasuredItem lazyLayoutMeasuredItemMo157getAndMeasurehBUhpc = lazyLayoutMeasuredItemProvider.mo157getAndMeasurehBUhpc(index2, itemInfo3.lane, itemInfo3.span, constraints.value);
                                    lazyLayoutMeasuredItemMo157getAndMeasurehBUhpc.setNonScrollableItem();
                                    LazyLayoutItemAnimation[] lazyLayoutItemAnimationArr5 = itemInfo3.animations;
                                    int length5 = lazyLayoutItemAnimationArr5.length;
                                    int i43 = 0;
                                    while (true) {
                                        if (i43 >= length5) {
                                            lazyLayoutMeasuredItem = lazyLayoutMeasuredItemMo157getAndMeasurehBUhpc;
                                            if (lazyLayoutKeyIndexMap8 == null || index2 != ((NearestRangeKeyIndexMap) lazyLayoutKeyIndexMap8).getIndex(obj4)) {
                                                break;
                                            }
                                            removeInfoForKey(obj4);
                                            mutableScatterMap4 = mutableScatterMap7;
                                        } else {
                                            LazyLayoutItemAnimation lazyLayoutItemAnimation5 = lazyLayoutItemAnimationArr5[i43];
                                            if (lazyLayoutItemAnimation5 != null) {
                                                lazyLayoutMeasuredItem = lazyLayoutMeasuredItemMo157getAndMeasurehBUhpc;
                                                i13 = 1;
                                                if (((Boolean) ((SnapshotMutableStateImpl) lazyLayoutItemAnimation5.isPlacementAnimationInProgress$delegate).getValue()).booleanValue()) {
                                                    break;
                                                }
                                            } else {
                                                lazyLayoutMeasuredItem = lazyLayoutMeasuredItemMo157getAndMeasurehBUhpc;
                                                i13 = 1;
                                            }
                                            i43 += i13;
                                            lazyLayoutMeasuredItemMo157getAndMeasurehBUhpc = lazyLayoutMeasuredItem;
                                        }
                                    }
                                    itemInfo3.updateAnimation(lazyLayoutMeasuredItem, coroutineScope, graphicsContext, i5, i6, itemInfo3.crossAxisOffset);
                                    LazyLayoutMeasuredItem lazyLayoutMeasuredItem7 = lazyLayoutMeasuredItem;
                                    if (index2 < this.firstVisibleIndex) {
                                        ((ArrayList) this.movingAwayToStartBound).add(lazyLayoutMeasuredItem7);
                                        mutableScatterMap4 = mutableScatterMap7;
                                    } else {
                                        ((ArrayList) this.movingAwayToEndBound).add(lazyLayoutMeasuredItem7);
                                        mutableScatterMap4 = mutableScatterMap7;
                                    }
                                }
                            }
                            j10 = j5 >> 8;
                            i38 = i12 + 1;
                            i21 = i4;
                            mutableScatterMap6 = mutableScatterMap4;
                            objArr4 = objArr2;
                            jArr4 = jArr2;
                            mutableScatterSet3 = mutableScatterSet2;
                            i7 = 3;
                        } else {
                            objArr2 = objArr4;
                            jArr2 = jArr4;
                        }
                        i12 = i38;
                        mutableScatterSet2 = mutableScatterSet3;
                        mutableScatterMap4 = mutableScatterMap6;
                        j5 = j10;
                        j10 = j5 >> 8;
                        i38 = i12 + 1;
                        i21 = i4;
                        mutableScatterMap6 = mutableScatterMap4;
                        objArr4 = objArr2;
                        jArr4 = jArr2;
                        mutableScatterSet3 = mutableScatterSet2;
                        i7 = 3;
                    }
                    objArr = objArr4;
                    jArr = jArr4;
                    mutableScatterSet = mutableScatterSet3;
                    mutableScatterMap2 = mutableScatterMap6;
                    if (i37 != 8) {
                        break;
                    }
                } else {
                    objArr = objArr4;
                    jArr = jArr4;
                    mutableScatterSet = mutableScatterSet3;
                    mutableScatterMap2 = mutableScatterMap5;
                }
                if (i36 == length3) {
                    break;
                }
                i36++;
                i21 = i4;
                lazyLayoutKeyIndexMap3 = lazyLayoutKeyIndexMap8;
                mutableScatterMap5 = mutableScatterMap2;
                objArr4 = objArr;
                jArr4 = jArr;
                mutableScatterSet3 = mutableScatterSet;
                i7 = 3;
            }
        } else {
            mutableScatterSet = mutableScatterSet3;
            mutableScatterMap2 = mutableScatterMap;
        }
        if (((ArrayList) this.movingAwayToStartBound).isEmpty()) {
            i10 = i2;
            mutableScatterMap3 = mutableScatterMap2;
        } else {
            ArrayList arrayList5 = (ArrayList) this.movingAwayToStartBound;
            if (arrayList5.size() > 1) {
                CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList5, new Comparator() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimator$onMeasured$$inlined$sortByDescending$2
                    @Override // java.util.Comparator
                    public final int compare(Object obj5, Object obj6) {
                        return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((NearestRangeKeyIndexMap) lazyLayoutKeyIndexMap).getIndex(((LazyLayoutMeasuredItem) obj6).getKey())), Integer.valueOf(((NearestRangeKeyIndexMap) lazyLayoutKeyIndexMap).getIndex(((LazyLayoutMeasuredItem) obj5).getKey())));
                    }
                });
            }
            ArrayList arrayList6 = (ArrayList) this.movingAwayToStartBound;
            int size5 = arrayList6.size();
            int i44 = 0;
            while (i44 < size5) {
                LazyLayoutMeasuredItem lazyLayoutMeasuredItem8 = (LazyLayoutMeasuredItem) arrayList6.get(i44);
                MutableScatterMap mutableScatterMap8 = mutableScatterMap2;
                Object obj5 = mutableScatterMap8.get(lazyLayoutMeasuredItem8.getKey());
                obj5.getClass();
                ItemInfo itemInfo4 = (ItemInfo) obj5;
                int iUpdateAndReturnOffsetFor3 = updateAndReturnOffsetFor(iArr, lazyLayoutMeasuredItem8);
                if (z2) {
                    LazyLayoutMeasuredItem lazyLayoutMeasuredItem9 = (LazyLayoutMeasuredItem) CollectionsKt___CollectionsKt.first(list);
                    long jMo155getOffsetBjo55l42 = lazyLayoutMeasuredItem9.mo155getOffsetBjo55l4(0);
                    if (lazyLayoutMeasuredItem9.isVertical()) {
                        IntOffset.Companion companion5 = IntOffset.Companion;
                        j4 = jMo155getOffsetBjo55l42 & j;
                    } else {
                        IntOffset.Companion companion6 = IntOffset.Companion;
                        j4 = jMo155getOffsetBjo55l42 >> (z5 ? 1L : 0L);
                    }
                    i11 = (int) j4;
                } else {
                    i11 = itemInfo4.layoutMinOffset;
                }
                lazyLayoutMeasuredItem8.position(i11 - iUpdateAndReturnOffsetFor3, itemInfo4.crossAxisOffset, i2, i3);
                if (i9 != 0) {
                    startPlacementAnimationsIfNeeded(lazyLayoutMeasuredItem8, true);
                }
                i44++;
                mutableScatterMap2 = mutableScatterMap8;
            }
            i10 = i2;
            mutableScatterMap3 = mutableScatterMap2;
            Arrays.fill(iArr, 0, iArr.length, 0);
        }
        if (!((ArrayList) this.movingAwayToEndBound).isEmpty()) {
            ArrayList arrayList7 = (ArrayList) this.movingAwayToEndBound;
            if (arrayList7.size() > 1) {
                CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList7, new Comparator() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimator$onMeasured$$inlined$sortBy$2
                    @Override // java.util.Comparator
                    public final int compare(Object obj6, Object obj7) {
                        return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((NearestRangeKeyIndexMap) lazyLayoutKeyIndexMap).getIndex(((LazyLayoutMeasuredItem) obj6).getKey())), Integer.valueOf(((NearestRangeKeyIndexMap) lazyLayoutKeyIndexMap).getIndex(((LazyLayoutMeasuredItem) obj7).getKey())));
                    }
                });
            }
            ArrayList arrayList8 = (ArrayList) this.movingAwayToEndBound;
            int size6 = arrayList8.size();
            for (int i45 = 0; i45 < size6; i45++) {
                LazyLayoutMeasuredItem lazyLayoutMeasuredItem10 = (LazyLayoutMeasuredItem) arrayList8.get(i45);
                Object obj6 = mutableScatterMap3.get(lazyLayoutMeasuredItem10.getKey());
                obj6.getClass();
                ItemInfo itemInfo5 = (ItemInfo) obj6;
                int iUpdateAndReturnOffsetFor4 = updateAndReturnOffsetFor(iArr, lazyLayoutMeasuredItem10);
                if (z2) {
                    LazyLayoutMeasuredItem lazyLayoutMeasuredItem11 = (LazyLayoutMeasuredItem) CollectionsKt___CollectionsKt.last(list);
                    long jMo155getOffsetBjo55l43 = lazyLayoutMeasuredItem11.mo155getOffsetBjo55l4(0);
                    if (lazyLayoutMeasuredItem11.isVertical()) {
                        IntOffset.Companion companion7 = IntOffset.Companion;
                        j3 = jMo155getOffsetBjo55l43 & j;
                    } else {
                        IntOffset.Companion companion8 = IntOffset.Companion;
                        j3 = jMo155getOffsetBjo55l43 >> (z5 ? 1L : 0L);
                    }
                    mainAxisSizeWithSpacings = lazyLayoutMeasuredItem11.getMainAxisSizeWithSpacings() + ((int) j3);
                } else {
                    mainAxisSizeWithSpacings = itemInfo5.layoutMaxOffset;
                }
                lazyLayoutMeasuredItem10.position((mainAxisSizeWithSpacings - lazyLayoutMeasuredItem10.getMainAxisSizeWithSpacings()) + iUpdateAndReturnOffsetFor4, itemInfo5.crossAxisOffset, i10, i3);
                if (i9 != 0) {
                    startPlacementAnimationsIfNeeded(lazyLayoutMeasuredItem10, true);
                }
            }
        }
        List list2 = this.movingAwayToStartBound;
        Collections.reverse(list2);
        Unit unit5 = Unit.INSTANCE;
        ArrayList arrayList9 = (ArrayList) list;
        arrayList9.addAll(0, list2);
        arrayList9.addAll(this.movingAwayToEndBound);
        ((ArrayList) this.movingInFromStartBound).clear();
        ((ArrayList) this.movingInFromEndBound).clear();
        ((ArrayList) this.movingAwayToStartBound).clear();
        ((ArrayList) this.movingAwayToEndBound).clear();
        mutableScatterSet.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                        } else if (i == length) {
                            break;
                        } else {
                            i++;
                        }
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
                long jMo155getOffsetBjo55l4 = lazyLayoutMeasuredItem.mo155getOffsetBjo55l4(i2);
                long j = lazyLayoutItemAnimation.rawOffset;
                LazyLayoutItemAnimation.Companion.getClass();
                if (!IntOffset.m851equalsimpl0(j, LazyLayoutItemAnimation.NotInitialized) && !IntOffset.m851equalsimpl0(j, jMo155getOffsetBjo55l4)) {
                    long jM852minusqkQi6aY = IntOffset.m852minusqkQi6aY(jMo155getOffsetBjo55l4, j);
                    FiniteAnimationSpec finiteAnimationSpec = lazyLayoutItemAnimation.placementSpec;
                    if (finiteAnimationSpec != null) {
                        long jM852minusqkQi6aY2 = IntOffset.m852minusqkQi6aY(((IntOffset) ((SnapshotMutableStateImpl) lazyLayoutItemAnimation.placementDelta$delegate).getValue()).packedValue, jM852minusqkQi6aY);
                        lazyLayoutItemAnimation.m167setPlacementDeltagyyYBs(jM852minusqkQi6aY2);
                        lazyLayoutItemAnimation.setPlacementAnimationInProgress(true);
                        lazyLayoutItemAnimation.isRunningMovingAwayAnimation = z;
                        BuildersKt.launch$default(lazyLayoutItemAnimation.coroutineScope, null, null, new LazyLayoutItemAnimation$animatePlacementDelta$1(lazyLayoutItemAnimation, finiteAnimationSpec, jM852minusqkQi6aY2, null), 3);
                    }
                }
                lazyLayoutItemAnimation.rawOffset = jMo155getOffsetBjo55l4;
            }
            i++;
            i2 = i3;
        }
    }
}
