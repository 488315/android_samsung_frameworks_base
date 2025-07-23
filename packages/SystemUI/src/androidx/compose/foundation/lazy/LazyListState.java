package androidx.compose.foundation.lazy;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.gestures.ScrollableState;
import androidx.compose.foundation.gestures.ScrollableStateKt;
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
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.saveable.ListSaverKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.layout.RemeasurementModifier;
import androidx.compose.ui.node.LayoutNode;
import java.util.Arrays;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyListState implements ScrollableState {
    public static final Companion Companion = new Companion(null);
    public static final SaverKt$Saver$1 Saver = ListSaverKt.listSaver(new Function2() { // from class: androidx.compose.foundation.lazy.LazyListState$Companion$Saver$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            LazyListState lazyListState = (LazyListState) obj2;
            return Arrays.asList(Integer.valueOf(lazyListState.scrollPosition.getIndex()), Integer.valueOf(lazyListState.scrollPosition.getScrollOffset()));
        }
    }, new Function1() { // from class: androidx.compose.foundation.lazy.LazyListState$Companion$Saver$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            List list = (List) obj;
            return new LazyListState(((Number) list.get(0)).intValue(), ((Number) list.get(1)).intValue());
        }
    });
    public final LazyLayoutScrollDeltaBetweenPasses _lazyLayoutScrollDeltaBetweenPasses;
    public LazyListMeasureResult approachLayoutInfo;
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
    public final LazyListState$prefetchScope$1 prefetchScope;
    public final LazyLayoutPrefetchState prefetchState;
    public final LazyListPrefetchStrategy prefetchStrategy;
    public final boolean prefetchingEnabled;
    public LayoutNode remeasurement;
    public final LazyListState$remeasurementModifier$1 remeasurementModifier;
    public final LazyListScrollPosition scrollPosition;
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

    public LazyListState() {
        this(0, 0, null, 7, null);
    }

    public static Object animateScrollToItem$default(LazyListState lazyListState, Continuation continuation) {
        lazyListState.getClass();
        Object scroll = lazyListState.scroll(MutatePriority.Default, new LazyListState$animateScrollToItem$2(lazyListState, 0, 0, null), continuation);
        return scroll == CoroutineSingletons.COROUTINE_SUSPENDED ? scroll : Unit.INSTANCE;
    }

    public final void applyMeasureResult$foundation_release(LazyListMeasureResult lazyListMeasureResult, boolean z, boolean z2) {
        if (!z && this.hasLookaheadOccurred) {
            this.approachLayoutInfo = lazyListMeasureResult;
            return;
        }
        if (z) {
            this.hasLookaheadOccurred = true;
        }
        LazyListMeasuredItem lazyListMeasuredItem = lazyListMeasureResult.firstVisibleItem;
        int i = lazyListMeasuredItem != null ? lazyListMeasuredItem.index : 0;
        int i2 = lazyListMeasureResult.firstVisibleItemScrollOffset;
        ((SnapshotMutableStateImpl) this.canScrollBackward$delegate).setValue(Boolean.valueOf((i == 0 && i2 == 0) ? false : true));
        ((SnapshotMutableStateImpl) this.canScrollForward$delegate).setValue(Boolean.valueOf(lazyListMeasureResult.canScrollForward));
        this.scrollToBeConsumed -= lazyListMeasureResult.consumedScroll;
        ((SnapshotMutableStateImpl) this.layoutInfoState).setValue(lazyListMeasureResult);
        LazyListScrollPosition lazyListScrollPosition = this.scrollPosition;
        if (z2) {
            lazyListScrollPosition.getClass();
            if (i2 < 0.0f) {
                InlineClassHelperKt.throwIllegalStateException("scrollOffset should be non-negative");
            }
            ((SnapshotMutableIntStateImpl) lazyListScrollPosition.scrollOffset$delegate).setIntValue(i2);
        } else {
            lazyListScrollPosition.getClass();
            lazyListScrollPosition.lastKnownFirstItemKey = lazyListMeasuredItem != null ? lazyListMeasuredItem.key : null;
            if (lazyListScrollPosition.hadFirstNotEmptyLayout || lazyListMeasureResult.totalItemsCount > 0) {
                lazyListScrollPosition.hadFirstNotEmptyLayout = true;
                if (i2 < 0.0f) {
                    InlineClassHelperKt.throwIllegalStateException("scrollOffset should be non-negative");
                }
                lazyListScrollPosition.update(lazyListMeasuredItem != null ? lazyListMeasuredItem.index : 0, i2);
            }
            if (this.prefetchingEnabled) {
                this.prefetchStrategy.onVisibleItemsUpdated(this.prefetchScope, lazyListMeasureResult);
            }
        }
        if (z) {
            this._lazyLayoutScrollDeltaBetweenPasses.updateScrollDeltaForApproach$foundation_release(lazyListMeasureResult.scrollBackAmount, lazyListMeasureResult.density, lazyListMeasureResult.coroutineScope);
        }
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

    public final LazyListLayoutInfo getLayoutInfo() {
        return (LazyListLayoutInfo) ((SnapshotMutableStateImpl) this.layoutInfoState).getValue();
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean isScrollInProgress() {
        return this.scrollableState.isScrollInProgress();
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
            boolean r0 = r8 instanceof androidx.compose.foundation.lazy.LazyListState$scroll$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.compose.foundation.lazy.LazyListState$scroll$1 r0 = (androidx.compose.foundation.lazy.LazyListState$scroll$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.lazy.LazyListState$scroll$1 r0 = new androidx.compose.foundation.lazy.LazyListState$scroll$1
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
            androidx.compose.foundation.lazy.LazyListState r5 = (androidx.compose.foundation.lazy.LazyListState) r5
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
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.LazyListState.scroll(androidx.compose.foundation.MutatePriority, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object scrollToItem(int i, int i2, SuspendLambda suspendLambda) {
        Object scroll = scroll(MutatePriority.Default, new LazyListState$scrollToItem$2(this, i, i2, null), suspendLambda);
        return scroll == CoroutineSingletons.COROUTINE_SUSPENDED ? scroll : Unit.INSTANCE;
    }

    public final void snapToItemIndexInternal$foundation_release(int i, int i2) {
        LazyListScrollPosition lazyListScrollPosition = this.scrollPosition;
        if (lazyListScrollPosition.getIndex() != i || lazyListScrollPosition.getScrollOffset() != i2) {
            LazyLayoutItemAnimator lazyLayoutItemAnimator = this.itemAnimator;
            lazyLayoutItemAnimator.releaseAnimations();
            lazyLayoutItemAnimator.keyIndexMap = null;
            lazyLayoutItemAnimator.firstVisibleIndex = -1;
        }
        lazyListScrollPosition.update(i, i2);
        lazyListScrollPosition.lastKnownFirstItemKey = null;
        LayoutNode layoutNode = this.remeasurement;
        if (layoutNode != null) {
            layoutNode.forceRemeasure();
        }
    }

    public LazyListState(int i, int i2) {
        this(i, i2, new DefaultLazyListPrefetchStrategy(2));
    }

    /* JADX WARN: Type inference failed for: r3v7, types: [androidx.compose.foundation.lazy.LazyListState$remeasurementModifier$1] */
    public LazyListState(final int i, int i2, LazyListPrefetchStrategy lazyListPrefetchStrategy) {
        MutableState mutableStateOf;
        MutableState mutableStateOf2;
        this.prefetchStrategy = lazyListPrefetchStrategy;
        this.scrollPosition = new LazyListScrollPosition(i, i2);
        this.layoutInfoState = SnapshotStateKt.mutableStateOf(LazyListStateKt.EmptyLazyListMeasureResult, SnapshotStateKt.neverEqualPolicy());
        this.internalInteractionSource = InteractionSourceKt.MutableInteractionSource();
        this.scrollableState = ScrollableStateKt.ScrollableState(new Function1() { // from class: androidx.compose.foundation.lazy.LazyListState$scrollableState$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LazyListMeasureResult lazyListMeasureResult;
                float floatValue = ((Number) obj).floatValue();
                LazyListState lazyListState = LazyListState.this;
                float f = -floatValue;
                if ((f >= 0.0f || lazyListState.getCanScrollForward()) && (f <= 0.0f || lazyListState.getCanScrollBackward())) {
                    if (Math.abs(lazyListState.scrollToBeConsumed) > 0.5f) {
                        InlineClassHelperKt.throwIllegalStateException("entered drag with non-zero pending scroll");
                    }
                    float f2 = lazyListState.scrollToBeConsumed + f;
                    lazyListState.scrollToBeConsumed = f2;
                    if (Math.abs(f2) > 0.5f) {
                        float f3 = lazyListState.scrollToBeConsumed;
                        int round = Math.round(f3);
                        LazyListMeasureResult copyWithScrollDeltaWithoutRemeasure = ((LazyListMeasureResult) ((SnapshotMutableStateImpl) lazyListState.layoutInfoState).getValue()).copyWithScrollDeltaWithoutRemeasure(round, !lazyListState.hasLookaheadOccurred);
                        if (copyWithScrollDeltaWithoutRemeasure != null && (lazyListMeasureResult = lazyListState.approachLayoutInfo) != null) {
                            LazyListMeasureResult copyWithScrollDeltaWithoutRemeasure2 = lazyListMeasureResult.copyWithScrollDeltaWithoutRemeasure(round, true);
                            if (copyWithScrollDeltaWithoutRemeasure2 != null) {
                                lazyListState.approachLayoutInfo = copyWithScrollDeltaWithoutRemeasure2;
                            } else {
                                copyWithScrollDeltaWithoutRemeasure = null;
                            }
                        }
                        LazyListPrefetchStrategy lazyListPrefetchStrategy2 = lazyListState.prefetchStrategy;
                        LazyListState$prefetchScope$1 lazyListState$prefetchScope$1 = lazyListState.prefetchScope;
                        if (copyWithScrollDeltaWithoutRemeasure != null) {
                            lazyListState.applyMeasureResult$foundation_release(copyWithScrollDeltaWithoutRemeasure, lazyListState.hasLookaheadOccurred, true);
                            ObservableScopeInvalidator.m173invalidateScopeimpl(lazyListState.placementScopeInvalidator);
                            float f4 = f3 - lazyListState.scrollToBeConsumed;
                            if (lazyListState.prefetchingEnabled) {
                                lazyListPrefetchStrategy2.onScroll(lazyListState$prefetchScope$1, f4, copyWithScrollDeltaWithoutRemeasure);
                            }
                        } else {
                            LayoutNode layoutNode = lazyListState.remeasurement;
                            if (layoutNode != null) {
                                layoutNode.forceRemeasure();
                            }
                            float f5 = f3 - lazyListState.scrollToBeConsumed;
                            LazyListLayoutInfo layoutInfo = lazyListState.getLayoutInfo();
                            if (lazyListState.prefetchingEnabled) {
                                lazyListPrefetchStrategy2.onScroll(lazyListState$prefetchScope$1, f5, layoutInfo);
                            }
                        }
                    }
                    if (Math.abs(lazyListState.scrollToBeConsumed) > 0.5f) {
                        f -= lazyListState.scrollToBeConsumed;
                        lazyListState.scrollToBeConsumed = 0.0f;
                    }
                } else {
                    f = 0.0f;
                }
                return Float.valueOf(-f);
            }
        });
        this.prefetchingEnabled = true;
        this.remeasurementModifier = new RemeasurementModifier() { // from class: androidx.compose.foundation.lazy.LazyListState$remeasurementModifier$1
            @Override // androidx.compose.ui.layout.RemeasurementModifier
            public final void onRemeasurementAvailable(LayoutNode layoutNode) {
                LazyListState.this.remeasurement = layoutNode;
            }
        };
        this.awaitLayoutModifier = new AwaitFirstLayoutModifier();
        this.itemAnimator = new LazyLayoutItemAnimator();
        this.beyondBoundsInfo = new LazyLayoutBeyondBoundsInfo();
        this.prefetchState = new LazyLayoutPrefetchState(lazyListPrefetchStrategy.getPrefetchScheduler(), new Function1() { // from class: androidx.compose.foundation.lazy.LazyListState$prefetchState$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                NestedPrefetchScope nestedPrefetchScope = (NestedPrefetchScope) obj;
                LazyListPrefetchStrategy lazyListPrefetchStrategy2 = LazyListState.this.prefetchStrategy;
                int i3 = i;
                Snapshot.Companion.getClass();
                Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot), currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null);
                lazyListPrefetchStrategy2.onNestedPrefetch(nestedPrefetchScope, i3);
                return Unit.INSTANCE;
            }
        });
        this.prefetchScope = new LazyListState$prefetchScope$1(this);
        this.pinnedItems = new LazyLayoutPinnedItemList();
        mutableStateOf = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        this.measurementScopeInvalidator = mutableStateOf;
        Boolean bool = Boolean.FALSE;
        this.canScrollForward$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.canScrollBackward$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        mutableStateOf2 = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        this.placementScopeInvalidator = mutableStateOf2;
        this._lazyLayoutScrollDeltaBetweenPasses = new LazyLayoutScrollDeltaBetweenPasses();
    }

    public LazyListState(int i, int i2, LazyListPrefetchStrategy lazyListPrefetchStrategy, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2, (i3 & 4) != 0 ? new DefaultLazyListPrefetchStrategy(2) : lazyListPrefetchStrategy);
    }

    public /* synthetic */ LazyListState(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2);
    }
}
