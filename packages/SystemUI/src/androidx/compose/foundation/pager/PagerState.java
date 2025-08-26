package androidx.compose.foundation.pager;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.ScrollScope;
import androidx.compose.foundation.gestures.ScrollableState;
import androidx.compose.foundation.gestures.ScrollableStateKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier;
import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsInfo;
import androidx.compose.foundation.lazy.layout.LazyLayoutPinnedItemList;
import androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState;
import androidx.compose.foundation.lazy.layout.NestedPrefetchScope;
import androidx.compose.foundation.lazy.layout.ObservableScopeInvalidator;
import androidx.compose.foundation.lazy.layout.PrefetchScheduler;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.layout.Remeasurement;
import androidx.compose.ui.layout.RemeasurementModifier;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public abstract class PagerState implements ScrollableState {
    public float accumulator;
    public PagerMeasureResult approachLayoutInfo;
    public final AwaitFirstLayoutModifier awaitLayoutModifier;
    public final LazyLayoutBeyondBoundsInfo beyondBoundsInfo;
    public final MutableState canScrollBackward$delegate;
    public final MutableState canScrollForward$delegate;
    public LazyLayoutPrefetchState.PrefetchHandle currentPrefetchHandle;
    public Density density;
    public int firstVisiblePage;
    public int firstVisiblePageOffset;
    public boolean hasLookaheadOccurred;
    public int indexToPrefetch;
    public final MutableInteractionSource internalInteractionSource;
    public final MutableState isLastScrollBackwardState;
    public final MutableState isLastScrollForwardState;
    public long maxScrollOffset;
    public final MutableState measurementScopeInvalidator;
    public long minScrollOffset;
    public final MutableState pagerLayoutInfoState;
    public final LazyLayoutPinnedItemList pinnedPages;
    public final MutableState placementScopeInvalidator;
    public final LazyLayoutPrefetchState prefetchState;
    public final boolean prefetchingEnabled;
    public long premeasureConstraints;
    public float previousPassDelta;
    public final MutableIntState programmaticScrollTargetPage$delegate;
    public final MutableState remeasurement$delegate;
    public final PagerState$remeasurementModifier$1 remeasurementModifier;
    public final PagerScrollPosition scrollPosition;
    public final ScrollableState scrollableState;
    public final State settledPage$delegate;
    public final MutableIntState settledPageState$delegate;
    public final MutableState upDownDifference$delegate;
    public boolean wasPrefetchingForward;

    /* renamed from: androidx.compose.foundation.pager.PagerState$animateScrollToPage$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        float F$0;
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PagerState.this.animateScrollToPage(0, null, this);
        }
    }

    /* renamed from: androidx.compose.foundation.pager.PagerState$animateScrollToPage$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ AnimationSpec<Float> $animationSpec;
        final /* synthetic */ int $targetPage;
        final /* synthetic */ float $targetPageOffsetToSnappedPosition;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(int i, float f, AnimationSpec<Float> animationSpec, Continuation continuation) {
            super(2, continuation);
            this.$targetPage = i;
            this.$targetPageOffsetToSnappedPosition = f;
            this.$animationSpec = animationSpec;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = PagerState.this.new AnonymousClass3(this.$targetPage, this.$targetPageOffsetToSnappedPosition, this.$animationSpec, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((ScrollScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            int i;
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                final PagerScrollScopeKt$LazyLayoutScrollScope$1 pagerScrollScopeKt$LazyLayoutScrollScope$1 = new PagerScrollScopeKt$LazyLayoutScrollScope$1((ScrollScope) this.L$0, PagerState.this);
                int i3 = this.$targetPage;
                float f = this.$targetPageOffsetToSnappedPosition;
                AnimationSpec<Float> animationSpec = this.$animationSpec;
                final PagerState pagerState = PagerState.this;
                Function2 function2 = new Function2() { // from class: androidx.compose.foundation.pager.PagerState.animateScrollToPage.3.1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        int iIntValue = ((Number) obj4).intValue();
                        PagerState pagerState2 = pagerState;
                        ((SnapshotMutableIntStateImpl) pagerState2.programmaticScrollTargetPage$delegate).setIntValue(pagerState2.coerceInPageRange(iIntValue));
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                float f2 = PagerStateKt.DefaultPositionThreshold;
                function2.invoke(pagerScrollScopeKt$LazyLayoutScrollScope$1, new Integer(i3));
                PagerState pagerState2 = pagerScrollScopeKt$LazyLayoutScrollScope$1.$state;
                boolean z = i3 > pagerState2.firstVisiblePage;
                int lastVisibleItemIndex = (pagerScrollScopeKt$LazyLayoutScrollScope$1.getLastVisibleItemIndex() - pagerState2.firstVisiblePage) + 1;
                if (((z && i3 > pagerScrollScopeKt$LazyLayoutScrollScope$1.getLastVisibleItemIndex()) || (!z && i3 < pagerState2.firstVisiblePage)) && Math.abs(i3 - pagerState2.firstVisiblePage) >= 3) {
                    if (z) {
                        i = i3 - lastVisibleItemIndex;
                        int i4 = pagerState2.firstVisiblePage;
                        if (i < i4) {
                            i = i4;
                        }
                    } else {
                        int i5 = lastVisibleItemIndex + i3;
                        i = pagerState2.firstVisiblePage;
                        if (i5 <= i) {
                            i = i5;
                        }
                    }
                    pagerScrollScopeKt$LazyLayoutScrollScope$1.snapToItem(i, 0);
                }
                float fCalculateDistanceTo = pagerScrollScopeKt$LazyLayoutScrollScope$1.calculateDistanceTo(i3) + f;
                final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
                Object objAnimate$default = SuspendAnimationKt.animate$default(fCalculateDistanceTo, animationSpec, new Function2() { // from class: androidx.compose.foundation.pager.PagerStateKt$animateScrollToPage$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        float fFloatValue = ((Number) obj3).floatValue();
                        ((Number) obj4).floatValue();
                        ref$FloatRef.element += pagerScrollScopeKt$LazyLayoutScrollScope$1.scrollBy(fFloatValue - ref$FloatRef.element);
                        return Unit.INSTANCE;
                    }
                }, this, 4);
                if (objAnimate$default != obj2) {
                    objAnimate$default = Unit.INSTANCE;
                }
                if (objAnimate$default == obj2) {
                    return obj2;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: androidx.compose.foundation.pager.PagerState$scroll$1, reason: invalid class name and case insensitive filesystem */
    final class C07151 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C07151(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PagerState.scroll$suspendImpl(PagerState.this, null, null, this);
        }
    }

    public PagerState() {
        this(0, 0.0f, null, 7, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0083, code lost:
    
        if (r8.scroll(r6, r7, r0) == r1) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Object scroll$suspendImpl(PagerState pagerState, MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        C07151 c07151;
        if (continuation instanceof C07151) {
            c07151 = (C07151) continuation;
            int i = c07151.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c07151.label = i - Integer.MIN_VALUE;
            } else {
                c07151 = pagerState.new C07151(continuation);
            }
        }
        Object obj = c07151.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c07151.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            c07151.L$0 = pagerState;
            c07151.L$1 = mutatePriority;
            c07151.L$2 = function2;
            c07151.label = 1;
            Object objWaitForFirstLayout = pagerState.awaitLayoutModifier.waitForFirstLayout(c07151);
            if (objWaitForFirstLayout != coroutineSingletons) {
                objWaitForFirstLayout = Unit.INSTANCE;
            }
            if (objWaitForFirstLayout != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            pagerState = (PagerState) c07151.L$0;
            ResultKt.throwOnFailure(obj);
            ((SnapshotMutableIntStateImpl) pagerState.programmaticScrollTargetPage$delegate).setIntValue(-1);
            return Unit.INSTANCE;
        }
        function2 = (Function2) c07151.L$2;
        mutatePriority = (MutatePriority) c07151.L$1;
        pagerState = (PagerState) c07151.L$0;
        ResultKt.throwOnFailure(obj);
        if (!pagerState.scrollableState.isScrollInProgress()) {
            ((SnapshotMutableIntStateImpl) pagerState.settledPageState$delegate).setIntValue(pagerState.getCurrentPage());
        }
        ScrollableState scrollableState = pagerState.scrollableState;
        c07151.L$0 = pagerState;
        c07151.L$1 = null;
        c07151.L$2 = null;
        c07151.label = 2;
    }

    public static Object scrollToPage$default(PagerState pagerState, int i, Continuation continuation) {
        pagerState.getClass();
        Object objScroll = pagerState.scroll(MutatePriority.Default, new PagerState$scrollToPage$2(pagerState, 0.0f, i, null), continuation);
        return objScroll == CoroutineSingletons.COROUTINE_SUSPENDED ? objScroll : Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00be, code lost:
    
        if (r5.scroll(androidx.compose.foundation.MutatePriority.Default, r4, r0) == r1) goto L41;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r12v7, types: [androidx.compose.animation.core.AnimationSpec] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object animateScrollToPage(int i, SpringSpec springSpec, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        PagerState pagerState;
        float f;
        SpringSpec springSpec2;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            if ((i == getCurrentPage() && getCurrentPageOffsetFraction() == 0.0f) || getPageCount() == 0) {
                return Unit.INSTANCE;
            }
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = springSpec;
            anonymousClass1.I$0 = i;
            anonymousClass1.F$0 = 0.0f;
            anonymousClass1.label = 1;
            Object objWaitForFirstLayout = this.awaitLayoutModifier.waitForFirstLayout(anonymousClass1);
            if (objWaitForFirstLayout != coroutineSingletons) {
                objWaitForFirstLayout = Unit.INSTANCE;
            }
            if (objWaitForFirstLayout != coroutineSingletons) {
                pagerState = this;
                f = 0.0f;
                springSpec2 = springSpec;
            }
            return coroutineSingletons;
        }
        if (i3 != 1) {
            if (i3 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        f = anonymousClass1.F$0;
        i = anonymousClass1.I$0;
        ?? r12 = (AnimationSpec) anonymousClass1.L$1;
        PagerState pagerState2 = (PagerState) anonymousClass1.L$0;
        ResultKt.throwOnFailure(obj);
        pagerState = pagerState2;
        springSpec2 = r12;
        SpringSpec springSpec3 = springSpec2;
        double d = f;
        if (-0.5d > d || d > 0.5d) {
            InlineClassHelperKt.throwIllegalArgumentException("pageOffsetFraction " + f + " is not within the range -0.5 to 0.5");
        }
        AnonymousClass3 anonymousClass3 = pagerState.new AnonymousClass3(pagerState.coerceInPageRange(i), f * pagerState.getPageSizeWithSpacing$foundation_release(), springSpec3, null);
        anonymousClass1.L$0 = null;
        anonymousClass1.L$1 = null;
        anonymousClass1.label = 2;
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x0135 A[Catch: all -> 0x0141, TryCatch #0 {all -> 0x0141, blocks: (B:56:0x00e0, B:58:0x00f3, B:60:0x00f7, B:62:0x0105, B:70:0x013b, B:68:0x0135, B:65:0x011d, B:73:0x0143), top: B:83:0x00e0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyMeasureResult$foundation_release(PagerMeasureResult pagerMeasureResult, boolean z, boolean z2) {
        if (!z && this.hasLookaheadOccurred) {
            this.approachLayoutInfo = pagerMeasureResult;
            return;
        }
        boolean z3 = true;
        if (z) {
            this.hasLookaheadOccurred = true;
        }
        float f = pagerMeasureResult.currentPageOffsetFraction;
        PagerScrollPosition pagerScrollPosition = this.scrollPosition;
        if (z2) {
            ((SnapshotMutableFloatStateImpl) pagerScrollPosition.currentPageOffsetFraction$delegate).setFloatValue(f);
        } else {
            pagerScrollPosition.getClass();
            MeasuredPage measuredPage = pagerMeasureResult.currentPage;
            pagerScrollPosition.lastKnownCurrentPageKey = measuredPage != null ? measuredPage.key : null;
            if (pagerScrollPosition.hadFirstNotEmptyLayout || !pagerMeasureResult.visiblePagesInfo.isEmpty()) {
                pagerScrollPosition.hadFirstNotEmptyLayout = true;
                int i = measuredPage != null ? measuredPage.index : 0;
                ((SnapshotMutableIntStateImpl) pagerScrollPosition.currentPage$delegate).setIntValue(i);
                pagerScrollPosition.nearestRangeState.update(i);
                ((SnapshotMutableFloatStateImpl) pagerScrollPosition.currentPageOffsetFraction$delegate).setFloatValue(f);
            }
            if (this.indexToPrefetch != -1 && !pagerMeasureResult.visiblePagesInfo.isEmpty()) {
                boolean z4 = this.wasPrefetchingForward;
                int i2 = pagerMeasureResult.beyondViewportPageCount;
                if (this.indexToPrefetch != (z4 ? ((MeasuredPage) ((PageInfo) CollectionsKt___CollectionsKt.last(pagerMeasureResult.visiblePagesInfo))).index + i2 + 1 : (((MeasuredPage) ((PageInfo) CollectionsKt___CollectionsKt.first(pagerMeasureResult.visiblePagesInfo))).index - i2) - 1)) {
                    this.indexToPrefetch = -1;
                    LazyLayoutPrefetchState.PrefetchHandle prefetchHandle = this.currentPrefetchHandle;
                    if (prefetchHandle != null) {
                        prefetchHandle.cancel();
                    }
                    this.currentPrefetchHandle = null;
                }
            }
        }
        ((SnapshotMutableStateImpl) this.pagerLayoutInfoState).setValue(pagerMeasureResult);
        ((SnapshotMutableStateImpl) this.canScrollForward$delegate).setValue(Boolean.valueOf(pagerMeasureResult.canScrollForward));
        MeasuredPage measuredPage2 = pagerMeasureResult.firstVisiblePage;
        int i3 = measuredPage2 != null ? measuredPage2.index : 0;
        int i4 = pagerMeasureResult.firstVisiblePageScrollOffset;
        if (i3 == 0 && i4 == 0) {
            z3 = false;
        }
        ((SnapshotMutableStateImpl) this.canScrollBackward$delegate).setValue(Boolean.valueOf(z3));
        if (measuredPage2 != null) {
            this.firstVisiblePage = measuredPage2.index;
        }
        this.firstVisiblePageOffset = i4;
        Snapshot.Companion.getClass();
        Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
        Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
        Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
        try {
            if (Math.abs(this.previousPassDelta) > 0.5f && this.prefetchingEnabled) {
                float f2 = this.previousPassDelta;
                if (((PagerMeasureResult) getLayoutInfo()).orientation == Orientation.Vertical) {
                    if (Math.signum(f2) != Math.signum(-Float.intBitsToFloat((int) (m181getUpDownDifferenceF1C5BW0$foundation_release() & 4294967295L)))) {
                        if (isNotGestureAction$foundation_release()) {
                        }
                    }
                    notifyPrefetch(this.previousPassDelta, pagerMeasureResult);
                } else {
                    if (Math.signum(f2) == Math.signum(-Float.intBitsToFloat((int) (m181getUpDownDifferenceF1C5BW0$foundation_release() >> 32)))) {
                    }
                    notifyPrefetch(this.previousPassDelta, pagerMeasureResult);
                }
            }
            Unit unit = Unit.INSTANCE;
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
            this.maxScrollOffset = PagerStateKt.calculateNewMaxScrollOffset(pagerMeasureResult, getPageCount());
            getPageCount();
            int iM180getViewportSizeYbymL2g = (int) (pagerMeasureResult.orientation == Orientation.Horizontal ? pagerMeasureResult.m180getViewportSizeYbymL2g() >> 32 : pagerMeasureResult.m180getViewportSizeYbymL2g() & 4294967295L);
            this.minScrollOffset = RangesKt___RangesKt.coerceIn(pagerMeasureResult.snapPosition.position(iM180getViewportSizeYbymL2g, pagerMeasureResult.pageSize, -pagerMeasureResult.viewportStartOffset, pagerMeasureResult.afterContentPadding), 0, iM180getViewportSizeYbymL2g);
        } catch (Throwable th) {
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
            throw th;
        }
    }

    public final int coerceInPageRange(int i) {
        if (getPageCount() > 0) {
            return RangesKt___RangesKt.coerceIn(i, 0, getPageCount() - 1);
        }
        return 0;
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

    public final int getCurrentPage() {
        return ((SnapshotMutableIntStateImpl) this.scrollPosition.currentPage$delegate).getIntValue();
    }

    public final float getCurrentPageOffsetFraction() {
        return ((SnapshotMutableFloatStateImpl) this.scrollPosition.currentPageOffsetFraction$delegate).getFloatValue();
    }

    public final PagerLayoutInfo getLayoutInfo() {
        return (PagerLayoutInfo) ((SnapshotMutableStateImpl) this.pagerLayoutInfoState).getValue();
    }

    public abstract int getPageCount();

    public final int getPageSize$foundation_release() {
        return ((PagerMeasureResult) ((SnapshotMutableStateImpl) this.pagerLayoutInfoState).getValue()).pageSize;
    }

    public final int getPageSizeWithSpacing$foundation_release() {
        return ((PagerMeasureResult) ((SnapshotMutableStateImpl) this.pagerLayoutInfoState).getValue()).pageSpacing + getPageSize$foundation_release();
    }

    /* renamed from: getUpDownDifference-F1C5BW0$foundation_release, reason: not valid java name */
    public final long m181getUpDownDifferenceF1C5BW0$foundation_release() {
        return ((Offset) ((SnapshotMutableStateImpl) this.upDownDifference$delegate).getValue()).packedValue;
    }

    public final boolean isNotGestureAction$foundation_release() {
        return ((int) Float.intBitsToFloat((int) (m181getUpDownDifferenceF1C5BW0$foundation_release() >> 32))) == 0 && ((int) Float.intBitsToFloat((int) (m181getUpDownDifferenceF1C5BW0$foundation_release() & 4294967295L))) == 0;
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean isScrollInProgress() {
        return this.scrollableState.isScrollInProgress();
    }

    public final void notifyPrefetch(float f, PagerMeasureResult pagerMeasureResult) {
        LazyLayoutPrefetchState.PrefetchHandle prefetchHandle;
        LazyLayoutPrefetchState.PrefetchHandle prefetchHandle2;
        LazyLayoutPrefetchState.PrefetchHandle prefetchHandle3;
        if (this.prefetchingEnabled && !pagerMeasureResult.visiblePagesInfo.isEmpty()) {
            boolean z = f > 0.0f;
            int i = pagerMeasureResult.beyondViewportPageCount;
            int i2 = z ? ((MeasuredPage) ((PageInfo) CollectionsKt___CollectionsKt.last(pagerMeasureResult.visiblePagesInfo))).index + i + 1 : (((MeasuredPage) ((PageInfo) CollectionsKt___CollectionsKt.first(pagerMeasureResult.visiblePagesInfo))).index - i) - 1;
            if (i2 < 0 || i2 >= getPageCount()) {
                return;
            }
            if (i2 != this.indexToPrefetch) {
                if (this.wasPrefetchingForward != z && (prefetchHandle3 = this.currentPrefetchHandle) != null) {
                    prefetchHandle3.cancel();
                }
                this.wasPrefetchingForward = z;
                this.indexToPrefetch = i2;
                this.currentPrefetchHandle = this.prefetchState.m171schedulePrefetchVKLhPVY(i2, this.premeasureConstraints, null);
            }
            if (z) {
                if ((((MeasuredPage) ((PageInfo) CollectionsKt___CollectionsKt.last(pagerMeasureResult.visiblePagesInfo))).offset + (pagerMeasureResult.pageSize + pagerMeasureResult.pageSpacing)) - pagerMeasureResult.viewportEndOffset >= f || (prefetchHandle2 = this.currentPrefetchHandle) == null) {
                    return;
                }
                prefetchHandle2.markAsUrgent();
                return;
            }
            if (pagerMeasureResult.viewportStartOffset - ((MeasuredPage) ((PageInfo) CollectionsKt___CollectionsKt.first(pagerMeasureResult.visiblePagesInfo))).offset >= (-f) || (prefetchHandle = this.currentPrefetchHandle) == null) {
                return;
            }
            prefetchHandle.markAsUrgent();
        }
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final Object scroll(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        return scroll$suspendImpl(this, mutatePriority, function2, continuation);
    }

    public final void snapToItem$foundation_release(float f, boolean z, int i) {
        PagerScrollPosition pagerScrollPosition = this.scrollPosition;
        ((SnapshotMutableIntStateImpl) pagerScrollPosition.currentPage$delegate).setIntValue(i);
        pagerScrollPosition.nearestRangeState.update(i);
        ((SnapshotMutableFloatStateImpl) pagerScrollPosition.currentPageOffsetFraction$delegate).setFloatValue(f);
        pagerScrollPosition.lastKnownCurrentPageKey = null;
        if (!z) {
            ObservableScopeInvalidator.m174invalidateScopeimpl(this.measurementScopeInvalidator);
            return;
        }
        Remeasurement remeasurement = (Remeasurement) ((SnapshotMutableStateImpl) this.remeasurement$delegate).getValue();
        if (remeasurement != null) {
            ((LayoutNode) remeasurement).forceRemeasure();
        }
    }

    /* JADX WARN: Type inference failed for: r5v10, types: [androidx.compose.foundation.pager.PagerState$remeasurementModifier$1] */
    public PagerState(int i, float f, PrefetchScheduler prefetchScheduler) {
        double d = f;
        if (-0.5d > d || d > 0.5d) {
            InlineClassHelperKt.throwIllegalArgumentException("currentPageOffsetFraction " + f + " is not within the range -0.5 to 0.5");
        }
        Offset.Companion.getClass();
        this.upDownDifference$delegate = SnapshotStateKt.mutableStateOf$default(Offset.m395boximpl(0L));
        this.scrollPosition = new PagerScrollPosition(i, f, this);
        this.firstVisiblePage = i;
        this.maxScrollOffset = Long.MAX_VALUE;
        this.scrollableState = ScrollableStateKt.ScrollableState(new Function1() { // from class: androidx.compose.foundation.pager.PagerState$scrollableState$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                PagerMeasureResult pagerMeasureResult;
                float fFloatValue = ((Number) obj).floatValue();
                PagerState pagerState = this.this$0;
                pagerState.getClass();
                long jCurrentAbsoluteScrollOffset = PagerScrollPositionKt.currentAbsoluteScrollOffset(pagerState);
                float f2 = pagerState.accumulator + fFloatValue;
                long jRoundToLong = MathKt__MathJVMKt.roundToLong(f2);
                pagerState.accumulator = f2 - jRoundToLong;
                if (Math.abs(fFloatValue) >= 1.0E-4f) {
                    long j = jCurrentAbsoluteScrollOffset + jRoundToLong;
                    long jCoerceIn = RangesKt___RangesKt.coerceIn(j, pagerState.minScrollOffset, pagerState.maxScrollOffset);
                    boolean z = j != jCoerceIn;
                    long j2 = jCoerceIn - jCurrentAbsoluteScrollOffset;
                    float f3 = j2;
                    pagerState.previousPassDelta = f3;
                    if (Math.abs(j2) != 0) {
                        ((SnapshotMutableStateImpl) pagerState.isLastScrollForwardState).setValue(Boolean.valueOf(f3 > 0.0f));
                        ((SnapshotMutableStateImpl) pagerState.isLastScrollBackwardState).setValue(Boolean.valueOf(f3 < 0.0f));
                    }
                    int i2 = (int) j2;
                    int i3 = -i2;
                    PagerMeasureResult pagerMeasureResultCopyWithScrollDeltaWithoutRemeasure = ((PagerMeasureResult) ((SnapshotMutableStateImpl) pagerState.pagerLayoutInfoState).getValue()).copyWithScrollDeltaWithoutRemeasure(i3);
                    if (pagerMeasureResultCopyWithScrollDeltaWithoutRemeasure != null && (pagerMeasureResult = pagerState.approachLayoutInfo) != null) {
                        PagerMeasureResult pagerMeasureResultCopyWithScrollDeltaWithoutRemeasure2 = pagerMeasureResult.copyWithScrollDeltaWithoutRemeasure(i3);
                        if (pagerMeasureResultCopyWithScrollDeltaWithoutRemeasure2 != null) {
                            pagerState.approachLayoutInfo = pagerMeasureResultCopyWithScrollDeltaWithoutRemeasure2;
                        } else {
                            pagerMeasureResultCopyWithScrollDeltaWithoutRemeasure = null;
                        }
                    }
                    if (pagerMeasureResultCopyWithScrollDeltaWithoutRemeasure != null) {
                        pagerState.applyMeasureResult$foundation_release(pagerMeasureResultCopyWithScrollDeltaWithoutRemeasure, pagerState.hasLookaheadOccurred, true);
                        ObservableScopeInvalidator.m174invalidateScopeimpl(pagerState.placementScopeInvalidator);
                    } else {
                        PagerScrollPosition pagerScrollPosition = pagerState.scrollPosition;
                        float pageSizeWithSpacing$foundation_release = pagerScrollPosition.state.getPageSizeWithSpacing$foundation_release() != 0 ? i2 / r5.getPageSizeWithSpacing$foundation_release() : 0.0f;
                        MutableFloatState mutableFloatState = pagerScrollPosition.currentPageOffsetFraction$delegate;
                        ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue() + pageSizeWithSpacing$foundation_release);
                        Remeasurement remeasurement = (Remeasurement) ((SnapshotMutableStateImpl) pagerState.remeasurement$delegate).getValue();
                        if (remeasurement != null) {
                            ((LayoutNode) remeasurement).forceRemeasure();
                        }
                    }
                    fFloatValue = (z ? Long.valueOf(j2) : Float.valueOf(fFloatValue)).floatValue();
                }
                return Float.valueOf(fFloatValue);
            }
        });
        this.prefetchingEnabled = true;
        this.indexToPrefetch = -1;
        this.pagerLayoutInfoState = SnapshotStateKt.mutableStateOf(PagerStateKt.EmptyLayoutInfo, SnapshotStateKt.neverEqualPolicy());
        this.density = PagerStateKt.UnitDensity;
        this.internalInteractionSource = InteractionSourceKt.MutableInteractionSource();
        this.programmaticScrollTargetPage$delegate = SnapshotIntStateKt.mutableIntStateOf(-1);
        this.settledPageState$delegate = SnapshotIntStateKt.mutableIntStateOf(i);
        this.settledPage$delegate = SnapshotStateKt.derivedStateOf(SnapshotStateKt.structuralEqualityPolicy(), new Function0() { // from class: androidx.compose.foundation.pager.PagerState$settledPage$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(this.this$0.scrollableState.isScrollInProgress() ? ((SnapshotMutableIntStateImpl) this.this$0.settledPageState$delegate).getIntValue() : this.this$0.getCurrentPage());
            }
        });
        SnapshotStateKt.derivedStateOf(SnapshotStateKt.structuralEqualityPolicy(), new Function0() { // from class: androidx.compose.foundation.pager.PagerState$targetPage$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int currentPage;
                if (!this.this$0.scrollableState.isScrollInProgress()) {
                    currentPage = this.this$0.getCurrentPage();
                } else if (((SnapshotMutableIntStateImpl) this.this$0.programmaticScrollTargetPage$delegate).getIntValue() != -1) {
                    currentPage = ((SnapshotMutableIntStateImpl) this.this$0.programmaticScrollTargetPage$delegate).getIntValue();
                } else {
                    float fAbs = Math.abs(this.this$0.getCurrentPageOffsetFraction());
                    PagerState pagerState = this.this$0;
                    currentPage = fAbs >= Math.abs(Math.min(pagerState.density.mo58toPx0680j_4(PagerStateKt.DefaultPositionThreshold), ((float) pagerState.getPageSize$foundation_release()) / 2.0f) / ((float) pagerState.getPageSize$foundation_release())) ? ((Boolean) ((SnapshotMutableStateImpl) this.this$0.isLastScrollForwardState).getValue()).booleanValue() ? this.this$0.firstVisiblePage + 1 : this.this$0.firstVisiblePage : this.this$0.getCurrentPage();
                }
                return Integer.valueOf(this.this$0.coerceInPageRange(currentPage));
            }
        });
        this.prefetchState = new LazyLayoutPrefetchState(prefetchScheduler, new Function1() { // from class: androidx.compose.foundation.pager.PagerState$prefetchState$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                NestedPrefetchScope nestedPrefetchScope = (NestedPrefetchScope) obj;
                Snapshot.Companion companion = Snapshot.Companion;
                PagerState pagerState = this.this$0;
                companion.getClass();
                Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
                Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                try {
                    nestedPrefetchScope.schedulePrefetch(pagerState.firstVisiblePage);
                    Unit unit = Unit.INSTANCE;
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                    throw th;
                }
            }
        });
        this.beyondBoundsInfo = new LazyLayoutBeyondBoundsInfo();
        this.awaitLayoutModifier = new AwaitFirstLayoutModifier();
        this.remeasurement$delegate = SnapshotStateKt.mutableStateOf$default(null);
        this.remeasurementModifier = new RemeasurementModifier() { // from class: androidx.compose.foundation.pager.PagerState$remeasurementModifier$1
            @Override // androidx.compose.ui.layout.RemeasurementModifier
            public final void onRemeasurementAvailable(LayoutNode layoutNode) {
                ((SnapshotMutableStateImpl) this.this$0.remeasurement$delegate).setValue(layoutNode);
            }
        };
        this.premeasureConstraints = ConstraintsKt.Constraints$default(0, 0, 0, 0, 15);
        this.pinnedPages = new LazyLayoutPinnedItemList();
        this.placementScopeInvalidator = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        this.measurementScopeInvalidator = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        Boolean bool = Boolean.FALSE;
        this.canScrollForward$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.canScrollBackward$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isLastScrollForwardState = SnapshotStateKt.mutableStateOf$default(bool);
        this.isLastScrollBackwardState = SnapshotStateKt.mutableStateOf$default(bool);
    }

    public /* synthetic */ PagerState(int i, float f, PrefetchScheduler prefetchScheduler, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? 0.0f : f, (i2 & 4) != 0 ? null : prefetchScheduler);
    }

    public /* synthetic */ PagerState(int i, float f, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? 0.0f : f);
    }

    public PagerState(int i, float f) {
        this(i, f, null);
    }
}
