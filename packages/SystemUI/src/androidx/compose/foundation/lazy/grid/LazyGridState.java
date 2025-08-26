package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.ScrollScope;
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
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

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
        public final Object mo781invoke(Object obj) {
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: androidx.compose.foundation.lazy.grid.LazyGridState$scroll$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return LazyGridState.this.scroll(null, null, this);
        }
    }

    /* renamed from: androidx.compose.foundation.lazy.grid.LazyGridState$scrollToItem$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $index;
        final /* synthetic */ int $scrollOffset;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, int i2, Continuation continuation) {
            super(2, continuation);
            this.$index = i;
            this.$scrollOffset = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LazyGridState.this.new AnonymousClass2(this.$index, this.$scrollOffset, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((ScrollScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            LazyGridState.this.snapToItemIndexInternal$foundation_release(this.$index, this.$scrollOffset);
            return Unit.INSTANCE;
        }
    }

    public LazyGridState() {
        this(0, 0, null, 7, null);
    }

    public static Object animateScrollToItem$default(LazyGridState lazyGridState, int i, Continuation continuation) {
        lazyGridState.getClass();
        Object objScroll = lazyGridState.scroll(MutatePriority.Default, new LazyGridState$animateScrollToItem$2(lazyGridState, i, 0, null), continuation);
        return objScroll == CoroutineSingletons.COROUTINE_SUSPENDED ? objScroll : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00bb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyMeasureResult$foundation_release(LazyGridMeasureResult lazyGridMeasureResult, boolean z, boolean z2) {
        Object obj;
        int i;
        LazyGridMeasuredItem[] lazyGridMeasuredItemArr;
        int i2;
        LazyGridMeasuredItem[] lazyGridMeasuredItemArr2;
        if (!z && this.hasLookaheadOccurred) {
            this.approachLayoutInfo = lazyGridMeasureResult;
            return;
        }
        if (z) {
            this.hasLookaheadOccurred = true;
        }
        this.scrollToBeConsumed -= lazyGridMeasureResult.consumedScroll;
        ((SnapshotMutableStateImpl) this.layoutInfoState).setValue(lazyGridMeasureResult);
        LazyGridMeasuredLine lazyGridMeasuredLine = lazyGridMeasureResult.firstVisibleLine;
        int i3 = lazyGridMeasuredLine != null ? lazyGridMeasuredLine.index : 0;
        int i4 = lazyGridMeasureResult.firstVisibleLineScrollOffset;
        ((SnapshotMutableStateImpl) this.canScrollBackward$delegate).setValue(Boolean.valueOf((i3 == 0 && i4 == 0) ? false : true));
        ((SnapshotMutableStateImpl) this.canScrollForward$delegate).setValue(Boolean.valueOf(lazyGridMeasureResult.canScrollForward));
        LazyGridScrollPosition lazyGridScrollPosition = this.scrollPosition;
        if (z2) {
            lazyGridScrollPosition.getClass();
            if (i4 < 0.0f) {
                InlineClassHelperKt.throwIllegalStateException("scrollOffset should be non-negative");
            }
            ((SnapshotMutableIntStateImpl) lazyGridScrollPosition.scrollOffset$delegate).setIntValue(i4);
        } else {
            lazyGridScrollPosition.getClass();
            if (lazyGridMeasuredLine == null || (lazyGridMeasuredItemArr2 = lazyGridMeasuredLine.items) == null) {
                obj = null;
                lazyGridScrollPosition.lastKnownFirstItemKey = obj;
                if (!lazyGridScrollPosition.hadFirstNotEmptyLayout || lazyGridMeasureResult.totalItemsCount > 0) {
                    lazyGridScrollPosition.hadFirstNotEmptyLayout = true;
                    if (i4 < 0.0f) {
                        InlineClassHelperKt.throwIllegalStateException("scrollOffset should be non-negative (" + i4 + ')');
                    }
                    if (lazyGridMeasuredLine != null || (lazyGridMeasuredItemArr = lazyGridMeasuredLine.items) == null) {
                        i = 0;
                        lazyGridScrollPosition.update(i, i4);
                    } else {
                        LazyGridMeasuredItem lazyGridMeasuredItem = lazyGridMeasuredItemArr.length != 0 ? lazyGridMeasuredItemArr[0] : null;
                        if (lazyGridMeasuredItem != null) {
                            i = lazyGridMeasuredItem.index;
                        }
                        lazyGridScrollPosition.update(i, i4);
                    }
                }
                if (this.prefetchingEnabled) {
                    DefaultLazyGridPrefetchStrategy defaultLazyGridPrefetchStrategy = (DefaultLazyGridPrefetchStrategy) this.prefetchStrategy;
                    if (defaultLazyGridPrefetchStrategy.lineToPrefetch != -1 && !lazyGridMeasureResult.visibleItemsInfo.isEmpty()) {
                        boolean z3 = defaultLazyGridPrefetchStrategy.wasScrollingForward;
                        Orientation orientation = lazyGridMeasureResult.orientation;
                        if (z3) {
                            LazyGridMeasuredItem lazyGridMeasuredItem2 = (LazyGridMeasuredItem) ((LazyGridItemInfo) CollectionsKt___CollectionsKt.last(lazyGridMeasureResult.visibleItemsInfo));
                            i2 = (orientation == Orientation.Vertical ? lazyGridMeasuredItem2.row : lazyGridMeasuredItem2.column) + 1;
                        } else {
                            LazyGridMeasuredItem lazyGridMeasuredItem3 = (LazyGridMeasuredItem) ((LazyGridItemInfo) CollectionsKt___CollectionsKt.first(lazyGridMeasureResult.visibleItemsInfo));
                            i2 = (orientation == Orientation.Vertical ? lazyGridMeasuredItem3.row : lazyGridMeasuredItem3.column) - 1;
                        }
                        if (defaultLazyGridPrefetchStrategy.lineToPrefetch != i2) {
                            defaultLazyGridPrefetchStrategy.lineToPrefetch = -1;
                            MutableVector mutableVector = defaultLazyGridPrefetchStrategy.currentLinePrefetchHandles;
                            Object[] objArr = mutableVector.content;
                            int i5 = mutableVector.size;
                            for (int i6 = 0; i6 < i5; i6++) {
                                ((LazyLayoutPrefetchState.PrefetchHandle) objArr[i6]).cancel();
                            }
                            mutableVector.clear();
                        }
                    }
                }
            } else {
                LazyGridMeasuredItem lazyGridMeasuredItem4 = lazyGridMeasuredItemArr2.length == 0 ? null : lazyGridMeasuredItemArr2[0];
                if (lazyGridMeasuredItem4 != null) {
                    obj = lazyGridMeasuredItem4.key;
                }
                lazyGridScrollPosition.lastKnownFirstItemKey = obj;
                if (!lazyGridScrollPosition.hadFirstNotEmptyLayout) {
                    lazyGridScrollPosition.hadFirstNotEmptyLayout = true;
                    if (i4 < 0.0f) {
                    }
                    if (lazyGridMeasuredLine != null) {
                        i = 0;
                        lazyGridScrollPosition.update(i, i4);
                        if (this.prefetchingEnabled) {
                        }
                    }
                }
            }
        }
        if (z) {
            this._lazyLayoutScrollDeltaBetweenPasses.updateScrollDeltaForApproach$foundation_release(lazyGridMeasureResult.scrollBackAmount, lazyGridMeasureResult.density, lazyGridMeasureResult.coroutineScope);
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
                Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                try {
                    LazyGridMeasureResult lazyGridMeasureResult2 = lazyGridState.hasLookaheadOccurred ? lazyGridState.approachLayoutInfo : (LazyGridMeasureResult) ((SnapshotMutableStateImpl) lazyGridState.layoutInfoState).getValue();
                    if (lazyGridMeasureResult2 != null) {
                        List list = (List) lazyGridMeasureResult2.prefetchInfoRetriever.mo781invoke(Integer.valueOf(i));
                        int size = list.size();
                        int i6 = 0;
                        while (i6 < size) {
                            Pair pair = (Pair) list.get(i6);
                            arrayList.add(lazyGridState.prefetchState.m171schedulePrefetchVKLhPVY(((Number) pair.getFirst()).intValue(), ((Constraints) pair.getSecond()).value, null));
                            i6++;
                            size = size;
                            z = z;
                        }
                        Unit unit = Unit.INSTANCE;
                    }
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                    mutableVector.addAll(mutableVector.size, (List) arrayList);
                } catch (Throwable th) {
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
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

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0067, code lost:
    
        if (r5.scroll(r6, r7, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.compose.foundation.gestures.ScrollableState
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object scroll(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = mutatePriority;
            anonymousClass1.L$2 = function2;
            anonymousClass1.label = 1;
            if (this.awaitLayoutModifier.waitForFirstLayout(anonymousClass1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        function2 = (Function2) anonymousClass1.L$2;
        mutatePriority = (MutatePriority) anonymousClass1.L$1;
        this = (LazyGridState) anonymousClass1.L$0;
        ResultKt.throwOnFailure(obj);
        ScrollableState scrollableState = this.scrollableState;
        anonymousClass1.L$0 = null;
        anonymousClass1.L$1 = null;
        anonymousClass1.L$2 = null;
        anonymousClass1.label = 2;
    }

    public final Object scrollToItem(int i, int i2, SuspendLambda suspendLambda) {
        Object objScroll = scroll(MutatePriority.Default, new AnonymousClass2(i, i2, null), suspendLambda);
        return objScroll == CoroutineSingletons.COROUTINE_SUSPENDED ? objScroll : Unit.INSTANCE;
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
            public final Object mo781invoke(Object obj) {
                LazyGridMeasureResult lazyGridMeasureResult;
                float fFloatValue = ((Number) obj).floatValue();
                LazyGridState lazyGridState = this.this$0;
                float f = -fFloatValue;
                if ((f >= 0.0f || lazyGridState.getCanScrollForward()) && (f <= 0.0f || lazyGridState.getCanScrollBackward())) {
                    if (Math.abs(lazyGridState.scrollToBeConsumed) > 0.5f) {
                        InlineClassHelperKt.throwIllegalStateException("entered drag with non-zero pending scroll");
                    }
                    float f2 = lazyGridState.scrollToBeConsumed + f;
                    lazyGridState.scrollToBeConsumed = f2;
                    if (Math.abs(f2) > 0.5f) {
                        float f3 = lazyGridState.scrollToBeConsumed;
                        int iRoundToInt = MathKt__MathJVMKt.roundToInt(f3);
                        LazyGridMeasureResult lazyGridMeasureResultCopyWithScrollDeltaWithoutRemeasure = ((LazyGridMeasureResult) ((SnapshotMutableStateImpl) lazyGridState.layoutInfoState).getValue()).copyWithScrollDeltaWithoutRemeasure(iRoundToInt, !lazyGridState.hasLookaheadOccurred);
                        if (lazyGridMeasureResultCopyWithScrollDeltaWithoutRemeasure != null && (lazyGridMeasureResult = lazyGridState.approachLayoutInfo) != null) {
                            LazyGridMeasureResult lazyGridMeasureResultCopyWithScrollDeltaWithoutRemeasure2 = lazyGridMeasureResult.copyWithScrollDeltaWithoutRemeasure(iRoundToInt, true);
                            if (lazyGridMeasureResultCopyWithScrollDeltaWithoutRemeasure2 != null) {
                                lazyGridState.approachLayoutInfo = lazyGridMeasureResultCopyWithScrollDeltaWithoutRemeasure2;
                            } else {
                                lazyGridMeasureResultCopyWithScrollDeltaWithoutRemeasure = null;
                            }
                        }
                        if (lazyGridMeasureResultCopyWithScrollDeltaWithoutRemeasure != null) {
                            lazyGridState.applyMeasureResult$foundation_release(lazyGridMeasureResultCopyWithScrollDeltaWithoutRemeasure, lazyGridState.hasLookaheadOccurred, true);
                            ObservableScopeInvalidator.m174invalidateScopeimpl(lazyGridState.placementScopeInvalidator);
                            lazyGridState.notifyPrefetchOnScroll(f3 - lazyGridState.scrollToBeConsumed, lazyGridMeasureResultCopyWithScrollDeltaWithoutRemeasure);
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
                this.this$0.remeasurement = layoutNode;
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
            public final Object mo781invoke(Object obj) {
                NestedPrefetchScope nestedPrefetchScope = (NestedPrefetchScope) obj;
                LazyGridPrefetchStrategy lazyGridPrefetchStrategy2 = this.this$0.prefetchStrategy;
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
        this.placementScopeInvalidator = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        this.measurementScopeInvalidator = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        Boolean bool = Boolean.FALSE;
        this.canScrollForward$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.canScrollBackward$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this._lazyLayoutScrollDeltaBetweenPasses = new LazyLayoutScrollDeltaBetweenPasses();
    }

    public /* synthetic */ LazyGridState(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2);
    }
}
