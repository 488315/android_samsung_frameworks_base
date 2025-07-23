package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.ScrollableState;
import androidx.compose.foundation.gestures.ScrollableStateKt;
import androidx.compose.foundation.gestures.snapping.LazyGridSnapLayoutInfoProviderKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier;
import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsInfo;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimator;
import androidx.compose.foundation.lazy.layout.LazyLayoutPinnedItemList;
import androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState;
import androidx.compose.foundation.lazy.layout.LazyLayoutScrollDeltaBetweenPasses;
import androidx.compose.foundation.lazy.layout.NestedPrefetchScope;
import androidx.compose.foundation.lazy.layout.ObservableScopeInvalidator;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.saveable.ListSaverKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.layout.RemeasurementModifier;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.unit.Constraints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyGridState implements ScrollableState {
    public static final Companion Companion = new Companion(null);
    public static final SaverKt$Saver$1 Saver = ListSaverKt.listSaver(new Function2() { // from class: androidx.compose.foundation.lazy.grid.LazyGridState$Companion$Saver$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            LazyGridState lazyGridState = (LazyGridState) obj2;
            return Arrays.asList(Integer.valueOf(lazyGridState.scrollPosition.getIndex()), Integer.valueOf(lazyGridState.scrollPosition.getScrollOffset()));
        }
    }, new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridState$Companion$Saver$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            List list = (List) obj;
            return new LazyGridState(((Number) list.get(0)).intValue(), ((Number) list.get(1)).intValue());
        }
    });
    public final LazyLayoutScrollDeltaBetweenPasses _lazyLayoutScrollDeltaBetweenPasses;
    public LazyGridMeasureResult approachLayoutInfo;
    public final AwaitFirstLayoutModifier awaitLayoutModifier;
    public final LazyLayoutBeyondBoundsInfo beyondBoundsInfo;
    public final MutableState canScrollBackward$delegate;
    public final MutableState canScrollForward$delegate;
    public boolean hasLookaheadOccurred;
    public final MutableInteractionSource internalInteractionSource;
    public final LazyLayoutItemAnimator itemAnimator;
    public final MutableState layoutInfoState;
    public final MutableState measurementScopeInvalidator;
    public final LazyLayoutPinnedItemList pinnedItems;
    public final MutableState placementScopeInvalidator;
    public final LazyGridState$prefetchScope$1 prefetchScope;
    public final LazyLayoutPrefetchState prefetchState;
    public final LazyGridPrefetchStrategy prefetchStrategy;
    public final boolean prefetchingEnabled;
    public LayoutNode remeasurement;
    public final LazyGridState$remeasurementModifier$1 remeasurementModifier;
    public final LazyGridScrollPosition scrollPosition;
    public float scrollToBeConsumed;
    public final ScrollableState scrollableState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public LazyGridState() {
        this(0, 0, null, 7, null);
    }

    public static Object animateScrollToItem$default(LazyGridState lazyGridState, int i, Continuation continuation) {
        lazyGridState.getClass();
        Object scroll = lazyGridState.scroll(MutatePriority.Default, new LazyGridState$animateScrollToItem$2(lazyGridState, i, 0, null), continuation);
        return scroll == CoroutineSingletons.COROUTINE_SUSPENDED ? scroll : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyMeasureResult$foundation_release(androidx.compose.foundation.lazy.grid.LazyGridMeasureResult r9, boolean r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.grid.LazyGridState.applyMeasureResult$foundation_release(androidx.compose.foundation.lazy.grid.LazyGridMeasureResult, boolean, boolean):void");
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final float dispatchRawDelta(float f) {
        return this.scrollableState.dispatchRawDelta(f);
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean getCanScrollBackward() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.canScrollBackward$delegate).getValue()).booleanValue();
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean getCanScrollForward() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.canScrollForward$delegate).getValue()).booleanValue();
    }

    public final LazyGridLayoutInfo getLayoutInfo() {
        return (LazyGridLayoutInfo) ((SnapshotMutableStateImpl) this.layoutInfoState).getValue();
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean isScrollInProgress() {
        return this.scrollableState.isScrollInProgress();
    }

    public final void notifyPrefetchOnScroll(float f, LazyGridLayoutInfo lazyGridLayoutInfo) {
        int i;
        int i2;
        boolean z = true;
        if (this.prefetchingEnabled) {
            DefaultLazyGridPrefetchStrategy defaultLazyGridPrefetchStrategy = (DefaultLazyGridPrefetchStrategy) this.prefetchStrategy;
            defaultLazyGridPrefetchStrategy.getClass();
            LazyGridMeasureResult lazyGridMeasureResult = (LazyGridMeasureResult) lazyGridLayoutInfo;
            if (lazyGridMeasureResult.visibleItemsInfo.isEmpty()) {
                return;
            }
            boolean z2 = f < 0.0f;
            Orientation orientation = lazyGridMeasureResult.orientation;
            if (z2) {
                LazyGridItemInfo lazyGridItemInfo = (LazyGridItemInfo) CollectionsKt___CollectionsKt.last(lazyGridMeasureResult.visibleItemsInfo);
                i = (orientation == Orientation.Vertical ? ((LazyGridMeasuredItem) lazyGridItemInfo).row : ((LazyGridMeasuredItem) lazyGridItemInfo).column) + 1;
                i2 = ((LazyGridMeasuredItem) ((LazyGridItemInfo) CollectionsKt___CollectionsKt.last(lazyGridMeasureResult.visibleItemsInfo))).index + 1;
            } else {
                LazyGridItemInfo lazyGridItemInfo2 = (LazyGridItemInfo) CollectionsKt___CollectionsKt.first(lazyGridMeasureResult.visibleItemsInfo);
                i = (orientation == Orientation.Vertical ? ((LazyGridMeasuredItem) lazyGridItemInfo2).row : ((LazyGridMeasuredItem) lazyGridItemInfo2).column) - 1;
                i2 = ((LazyGridMeasuredItem) ((LazyGridItemInfo) CollectionsKt___CollectionsKt.first(lazyGridMeasureResult.visibleItemsInfo))).index - 1;
            }
            if (i2 < 0 || i2 >= lazyGridMeasureResult.totalItemsCount) {
                return;
            }
            int i3 = defaultLazyGridPrefetchStrategy.lineToPrefetch;
            MutableVector mutableVector = defaultLazyGridPrefetchStrategy.currentLinePrefetchHandles;
            if (i != i3 && i >= 0) {
                if (defaultLazyGridPrefetchStrategy.wasScrollingForward != z2) {
                    Object[] objArr = mutableVector.content;
                    int i4 = mutableVector.size;
                    for (int i5 = 0; i5 < i4; i5++) {
                        ((LazyLayoutPrefetchState.PrefetchHandle) objArr[i5]).cancel();
                    }
                }
                defaultLazyGridPrefetchStrategy.wasScrollingForward = z2;
                defaultLazyGridPrefetchStrategy.lineToPrefetch = i;
                mutableVector.clear();
                LazyGridState$prefetchScope$1 lazyGridState$prefetchScope$1 = this.prefetchScope;
                lazyGridState$prefetchScope$1.getClass();
                ArrayList arrayList = new ArrayList();
                Snapshot.Companion companion = Snapshot.Companion;
                LazyGridState lazyGridState = lazyGridState$prefetchScope$1.this$0;
                companion.getClass();
                Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
                Snapshot makeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                try {
                    LazyGridMeasureResult lazyGridMeasureResult2 = lazyGridState.hasLookaheadOccurred ? lazyGridState.approachLayoutInfo : (LazyGridMeasureResult) ((SnapshotMutableStateImpl) lazyGridState.layoutInfoState).getValue();
                    if (lazyGridMeasureResult2 != null) {
                        List list = (List) lazyGridMeasureResult2.prefetchInfoRetriever.mo779invoke(Integer.valueOf(i));
                        int size = list.size();
                        int i6 = 0;
                        while (i6 < size) {
                            Pair pair = (Pair) list.get(i6);
                            arrayList.add(lazyGridState.prefetchState.m170schedulePrefetchVKLhPVY(((Number) pair.getFirst()).intValue(), ((Constraints) pair.getSecond()).value, null));
                            i6++;
                            size = size;
                            z = z;
                        }
                        Unit unit = Unit.INSTANCE;
                    }
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
                    mutableVector.addAll(mutableVector.size, (List) arrayList);
                } catch (Throwable th) {
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
                    throw th;
                }
            }
            if (!z2) {
                if (lazyGridMeasureResult.viewportStartOffset - LazyGridSnapLayoutInfoProviderKt.offsetOnMainAxis((LazyGridItemInfo) CollectionsKt___CollectionsKt.first(lazyGridMeasureResult.visibleItemsInfo), orientation) < f) {
                    Object[] objArr2 = mutableVector.content;
                    int i7 = mutableVector.size;
                    for (int i8 = 0; i8 < i7; i8++) {
                        ((LazyLayoutPrefetchState.PrefetchHandle) objArr2[i8]).markAsUrgent();
                    }
                    return;
                }
                return;
            }
            LazyGridItemInfo lazyGridItemInfo3 = (LazyGridItemInfo) CollectionsKt___CollectionsKt.last(lazyGridMeasureResult.visibleItemsInfo);
            if (((LazyGridSnapLayoutInfoProviderKt.offsetOnMainAxis(lazyGridItemInfo3, orientation) + ((int) (orientation == Orientation.Vertical ? ((LazyGridMeasuredItem) lazyGridItemInfo3).size & 4294967295L : ((LazyGridMeasuredItem) lazyGridItemInfo3).size >> 32))) + lazyGridMeasureResult.mainAxisItemSpacing) - lazyGridMeasureResult.viewportEndOffset < (-f)) {
                Object[] objArr3 = mutableVector.content;
                int i9 = mutableVector.size;
                for (int i10 = 0; i10 < i9; i10++) {
                    ((LazyLayoutPrefetchState.PrefetchHandle) objArr3[i10]).markAsUrgent();
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0067, code lost:
    
        if (r5.scroll(r6, r7, r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0069, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0055, code lost:
    
        if (r5.awaitLayoutModifier.waitForFirstLayout(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // androidx.compose.foundation.gestures.ScrollableState
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object scroll(androidx.compose.foundation.MutatePriority r6, kotlin.jvm.functions.Function2 r7, kotlin.coroutines.Continuation r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof androidx.compose.foundation.lazy.grid.LazyGridState$scroll$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.compose.foundation.lazy.grid.LazyGridState$scroll$1 r0 = (androidx.compose.foundation.lazy.grid.LazyGridState$scroll$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.lazy.grid.LazyGridState$scroll$1 r0 = new androidx.compose.foundation.lazy.grid.LazyGridState$scroll$1
            r0.<init>(r5, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L44
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6a
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            java.lang.Object r5 = r0.L$2
            r7 = r5
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            java.lang.Object r5 = r0.L$1
            r6 = r5
            androidx.compose.foundation.MutatePriority r6 = (androidx.compose.foundation.MutatePriority) r6
            java.lang.Object r5 = r0.L$0
            androidx.compose.foundation.lazy.grid.LazyGridState r5 = (androidx.compose.foundation.lazy.grid.LazyGridState) r5
            kotlin.ResultKt.throwOnFailure(r8)
            goto L58
        L44:
            kotlin.ResultKt.throwOnFailure(r8)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.L$2 = r7
            r0.label = r4
            androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier r8 = r5.awaitLayoutModifier
            java.lang.Object r8 = r8.waitForFirstLayout(r0)
            if (r8 != r1) goto L58
            goto L69
        L58:
            androidx.compose.foundation.gestures.ScrollableState r5 = r5.scrollableState
            r8 = 0
            r0.L$0 = r8
            r0.L$1 = r8
            r0.L$2 = r8
            r0.label = r3
            java.lang.Object r5 = r5.scroll(r6, r7, r0)
            if (r5 != r1) goto L6a
        L69:
            return r1
        L6a:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.grid.LazyGridState.scroll(androidx.compose.foundation.MutatePriority, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object scrollToItem(int i, int i2, SuspendLambda suspendLambda) {
        Object scroll = scroll(MutatePriority.Default, new LazyGridState$scrollToItem$2(this, i, i2, null), suspendLambda);
        return scroll == CoroutineSingletons.COROUTINE_SUSPENDED ? scroll : Unit.INSTANCE;
    }

    public final void snapToItemIndexInternal$foundation_release(int i, int i2) {
        LazyGridScrollPosition lazyGridScrollPosition = this.scrollPosition;
        if (lazyGridScrollPosition.getIndex() != i || lazyGridScrollPosition.getScrollOffset() != i2) {
            LazyLayoutItemAnimator lazyLayoutItemAnimator = this.itemAnimator;
            lazyLayoutItemAnimator.releaseAnimations();
            lazyLayoutItemAnimator.keyIndexMap = null;
            lazyLayoutItemAnimator.firstVisibleIndex = -1;
        }
        lazyGridScrollPosition.update(i, i2);
        lazyGridScrollPosition.lastKnownFirstItemKey = null;
        LayoutNode layoutNode = this.remeasurement;
        if (layoutNode != null) {
            layoutNode.forceRemeasure();
        }
    }

    public LazyGridState(int i, int i2) {
        this(i, i2, new DefaultLazyGridPrefetchStrategy(2));
    }

    public LazyGridState(int i, int i2, LazyGridPrefetchStrategy lazyGridPrefetchStrategy, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2, (i3 & 4) != 0 ? new DefaultLazyGridPrefetchStrategy(2) : lazyGridPrefetchStrategy);
    }

    /* JADX WARN: Type inference failed for: r3v7, types: [androidx.compose.foundation.lazy.grid.LazyGridState$remeasurementModifier$1] */
    public LazyGridState(final int i, int i2, LazyGridPrefetchStrategy lazyGridPrefetchStrategy) {
        MutableState mutableStateOf;
        MutableState mutableStateOf2;
        this.prefetchStrategy = lazyGridPrefetchStrategy;
        this.scrollPosition = new LazyGridScrollPosition(i, i2);
        this.layoutInfoState = SnapshotStateKt.mutableStateOf(LazyGridStateKt.EmptyLazyGridLayoutInfo, SnapshotStateKt.neverEqualPolicy());
        this.internalInteractionSource = InteractionSourceKt.MutableInteractionSource();
        this.scrollableState = ScrollableStateKt.ScrollableState(new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridState$scrollableState$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LazyGridMeasureResult lazyGridMeasureResult;
                float floatValue = ((Number) obj).floatValue();
                LazyGridState lazyGridState = LazyGridState.this;
                float f = -floatValue;
                if ((f >= 0.0f || lazyGridState.getCanScrollForward()) && (f <= 0.0f || lazyGridState.getCanScrollBackward())) {
                    if (Math.abs(lazyGridState.scrollToBeConsumed) > 0.5f) {
                        InlineClassHelperKt.throwIllegalStateException("entered drag with non-zero pending scroll");
                    }
                    float f2 = lazyGridState.scrollToBeConsumed + f;
                    lazyGridState.scrollToBeConsumed = f2;
                    if (Math.abs(f2) > 0.5f) {
                        float f3 = lazyGridState.scrollToBeConsumed;
                        int roundToInt = MathKt__MathJVMKt.roundToInt(f3);
                        LazyGridMeasureResult copyWithScrollDeltaWithoutRemeasure = ((LazyGridMeasureResult) ((SnapshotMutableStateImpl) lazyGridState.layoutInfoState).getValue()).copyWithScrollDeltaWithoutRemeasure(roundToInt, !lazyGridState.hasLookaheadOccurred);
                        if (copyWithScrollDeltaWithoutRemeasure != null && (lazyGridMeasureResult = lazyGridState.approachLayoutInfo) != null) {
                            LazyGridMeasureResult copyWithScrollDeltaWithoutRemeasure2 = lazyGridMeasureResult.copyWithScrollDeltaWithoutRemeasure(roundToInt, true);
                            if (copyWithScrollDeltaWithoutRemeasure2 != null) {
                                lazyGridState.approachLayoutInfo = copyWithScrollDeltaWithoutRemeasure2;
                            } else {
                                copyWithScrollDeltaWithoutRemeasure = null;
                            }
                        }
                        if (copyWithScrollDeltaWithoutRemeasure != null) {
                            lazyGridState.applyMeasureResult$foundation_release(copyWithScrollDeltaWithoutRemeasure, lazyGridState.hasLookaheadOccurred, true);
                            ObservableScopeInvalidator.m173invalidateScopeimpl(lazyGridState.placementScopeInvalidator);
                            lazyGridState.notifyPrefetchOnScroll(f3 - lazyGridState.scrollToBeConsumed, copyWithScrollDeltaWithoutRemeasure);
                        } else {
                            LayoutNode layoutNode = lazyGridState.remeasurement;
                            if (layoutNode != null) {
                                layoutNode.forceRemeasure();
                            }
                            lazyGridState.notifyPrefetchOnScroll(f3 - lazyGridState.scrollToBeConsumed, lazyGridState.getLayoutInfo());
                        }
                    }
                    if (Math.abs(lazyGridState.scrollToBeConsumed) > 0.5f) {
                        f -= lazyGridState.scrollToBeConsumed;
                        lazyGridState.scrollToBeConsumed = 0.0f;
                    }
                } else {
                    f = 0.0f;
                }
                return Float.valueOf(-f);
            }
        });
        this.prefetchingEnabled = true;
        this.remeasurementModifier = new RemeasurementModifier() { // from class: androidx.compose.foundation.lazy.grid.LazyGridState$remeasurementModifier$1
            @Override // androidx.compose.ui.layout.RemeasurementModifier
            public final void onRemeasurementAvailable(LayoutNode layoutNode) {
                LazyGridState.this.remeasurement = layoutNode;
            }
        };
        this.awaitLayoutModifier = new AwaitFirstLayoutModifier();
        this.itemAnimator = new LazyLayoutItemAnimator();
        this.beyondBoundsInfo = new LazyLayoutBeyondBoundsInfo();
        lazyGridPrefetchStrategy.getClass();
        this.prefetchState = new LazyLayoutPrefetchState(null, new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridState$prefetchState$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                NestedPrefetchScope nestedPrefetchScope = (NestedPrefetchScope) obj;
                LazyGridPrefetchStrategy lazyGridPrefetchStrategy2 = LazyGridState.this.prefetchStrategy;
                int i3 = i;
                Snapshot.Companion.getClass();
                Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot), currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null);
                DefaultLazyGridPrefetchStrategy defaultLazyGridPrefetchStrategy = (DefaultLazyGridPrefetchStrategy) lazyGridPrefetchStrategy2;
                for (int i4 = 0; i4 < defaultLazyGridPrefetchStrategy.nestedPrefetchItemCount; i4++) {
                    nestedPrefetchScope.schedulePrefetch(i3 + i4);
                }
                return Unit.INSTANCE;
            }
        });
        this.prefetchScope = new LazyGridState$prefetchScope$1(this);
        this.pinnedItems = new LazyLayoutPinnedItemList();
        mutableStateOf = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        this.placementScopeInvalidator = mutableStateOf;
        mutableStateOf2 = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        this.measurementScopeInvalidator = mutableStateOf2;
        Boolean bool = Boolean.FALSE;
        this.canScrollForward$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.canScrollBackward$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this._lazyLayoutScrollDeltaBetweenPasses = new LazyLayoutScrollDeltaBetweenPasses();
    }

    public /* synthetic */ LazyGridState(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2);
    }
}
