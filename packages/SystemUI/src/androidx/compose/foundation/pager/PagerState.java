package androidx.compose.foundation.pager;

import androidx.compose.foundation.MutatePriority;
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
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public PagerState() {
        this(0, 0.0f, null, 7, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0083, code lost:
    
        if (r8.scroll(r6, r7, r0) != r1) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0085, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005e, code lost:
    
        if (r8 == r1) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object scroll$suspendImpl(androidx.compose.foundation.pager.PagerState r5, androidx.compose.foundation.MutatePriority r6, kotlin.jvm.functions.Function2 r7, kotlin.coroutines.Continuation r8) {
        /*
            boolean r0 = r8 instanceof androidx.compose.foundation.pager.PagerState$scroll$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.compose.foundation.pager.PagerState$scroll$1 r0 = (androidx.compose.foundation.pager.PagerState$scroll$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.pager.PagerState$scroll$1 r0 = new androidx.compose.foundation.pager.PagerState$scroll$1
            r0.<init>(r5, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L48
            if (r2 == r4) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r5 = r0.L$0
            androidx.compose.foundation.pager.PagerState r5 = (androidx.compose.foundation.pager.PagerState) r5
            kotlin.ResultKt.throwOnFailure(r8)
            goto L86
        L2e:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L36:
            java.lang.Object r5 = r0.L$2
            r7 = r5
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            java.lang.Object r5 = r0.L$1
            r6 = r5
            androidx.compose.foundation.MutatePriority r6 = (androidx.compose.foundation.MutatePriority) r6
            java.lang.Object r5 = r0.L$0
            androidx.compose.foundation.pager.PagerState r5 = (androidx.compose.foundation.pager.PagerState) r5
            kotlin.ResultKt.throwOnFailure(r8)
            goto L61
        L48:
            kotlin.ResultKt.throwOnFailure(r8)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.L$2 = r7
            r0.label = r4
            androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier r8 = r5.awaitLayoutModifier
            java.lang.Object r8 = r8.waitForFirstLayout(r0)
            if (r8 != r1) goto L5c
            goto L5e
        L5c:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
        L5e:
            if (r8 != r1) goto L61
            goto L85
        L61:
            androidx.compose.foundation.gestures.ScrollableState r8 = r5.scrollableState
            boolean r8 = r8.isScrollInProgress()
            if (r8 != 0) goto L74
            int r8 = r5.getCurrentPage()
            androidx.compose.runtime.MutableIntState r2 = r5.settledPageState$delegate
            androidx.compose.runtime.SnapshotMutableIntStateImpl r2 = (androidx.compose.runtime.SnapshotMutableIntStateImpl) r2
            r2.setIntValue(r8)
        L74:
            androidx.compose.foundation.gestures.ScrollableState r8 = r5.scrollableState
            r0.L$0 = r5
            r2 = 0
            r0.L$1 = r2
            r0.L$2 = r2
            r0.label = r3
            java.lang.Object r6 = r8.scroll(r6, r7, r0)
            if (r6 != r1) goto L86
        L85:
            return r1
        L86:
            androidx.compose.runtime.MutableIntState r5 = r5.programmaticScrollTargetPage$delegate
            androidx.compose.runtime.SnapshotMutableIntStateImpl r5 = (androidx.compose.runtime.SnapshotMutableIntStateImpl) r5
            r6 = -1
            r5.setIntValue(r6)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.pager.PagerState.scroll$suspendImpl(androidx.compose.foundation.pager.PagerState, androidx.compose.foundation.MutatePriority, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static Object scrollToPage$default(PagerState pagerState, int i, Continuation continuation) {
        pagerState.getClass();
        Object scroll = pagerState.scroll(MutatePriority.Default, new PagerState$scrollToPage$2(pagerState, 0.0f, i, null), continuation);
        return scroll == CoroutineSingletons.COROUTINE_SUSPENDED ? scroll : Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00be, code lost:
    
        if (r5.scroll(androidx.compose.foundation.MutatePriority.Default, r4, r0) != r1) goto L42;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Type inference failed for: r12v7, types: [androidx.compose.animation.core.AnimationSpec] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object animateScrollToPage(int r11, androidx.compose.animation.core.SpringSpec r12, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            r10 = this;
            boolean r0 = r13 instanceof androidx.compose.foundation.pager.PagerState$animateScrollToPage$1
            if (r0 == 0) goto L13
            r0 = r13
            androidx.compose.foundation.pager.PagerState$animateScrollToPage$1 r0 = (androidx.compose.foundation.pager.PagerState$animateScrollToPage$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.pager.PagerState$animateScrollToPage$1 r0 = new androidx.compose.foundation.pager.PagerState$animateScrollToPage$1
            r0.<init>(r10, r13)
        L18:
            java.lang.Object r13 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L45
            if (r2 == r4) goto L33
            if (r2 != r3) goto L2b
            kotlin.ResultKt.throwOnFailure(r13)
            goto Lc1
        L2b:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L33:
            float r10 = r0.F$0
            int r11 = r0.I$0
            java.lang.Object r12 = r0.L$1
            androidx.compose.animation.core.AnimationSpec r12 = (androidx.compose.animation.core.AnimationSpec) r12
            java.lang.Object r2 = r0.L$0
            androidx.compose.foundation.pager.PagerState r2 = (androidx.compose.foundation.pager.PagerState) r2
            kotlin.ResultKt.throwOnFailure(r13)
            r5 = r2
        L43:
            r8 = r12
            goto L7c
        L45:
            kotlin.ResultKt.throwOnFailure(r13)
            int r13 = r10.getCurrentPage()
            r2 = 0
            if (r11 != r13) goto L58
            float r13 = r10.getCurrentPageOffsetFraction()
            int r13 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r13 != 0) goto L58
            goto L5e
        L58:
            int r13 = r10.getPageCount()
            if (r13 != 0) goto L61
        L5e:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L61:
            r0.L$0 = r10
            r0.L$1 = r12
            r0.I$0 = r11
            r0.F$0 = r2
            r0.label = r4
            androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier r13 = r10.awaitLayoutModifier
            java.lang.Object r13 = r13.waitForFirstLayout(r0)
            if (r13 != r1) goto L74
            goto L76
        L74:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE
        L76:
            if (r13 != r1) goto L79
            goto Lc0
        L79:
            r5 = r10
            r10 = r2
            goto L43
        L7c:
            double r12 = (double) r10
            r6 = -4620693217682128896(0xbfe0000000000000, double:-0.5)
            int r2 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r2 > 0) goto L8a
            r6 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            int r12 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r12 > 0) goto L8a
            goto La0
        L8a:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "pageOffsetFraction "
            r12.<init>(r13)
            r12.append(r10)
            java.lang.String r13 = " is not within the range -0.5 to 0.5"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            androidx.compose.foundation.internal.InlineClassHelperKt.throwIllegalArgumentException(r12)
        La0:
            int r6 = r5.coerceInPageRange(r11)
            int r11 = r5.getPageSizeWithSpacing$foundation_release()
            float r11 = (float) r11
            float r7 = r10 * r11
            androidx.compose.foundation.pager.PagerState$animateScrollToPage$3 r4 = new androidx.compose.foundation.pager.PagerState$animateScrollToPage$3
            r9 = 0
            r4.<init>(r5, r6, r7, r8, r9)
            r10 = 0
            r0.L$0 = r10
            r0.L$1 = r10
            r0.label = r3
            androidx.compose.foundation.MutatePriority r10 = androidx.compose.foundation.MutatePriority.Default
            java.lang.Object r10 = r5.scroll(r10, r4, r0)
            if (r10 != r1) goto Lc1
        Lc0:
            return r1
        Lc1:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.pager.PagerState.animateScrollToPage(int, androidx.compose.animation.core.SpringSpec, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0139, code lost:
    
        if (isNotGestureAction$foundation_release() == false) goto L73;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyMeasureResult$foundation_release(androidx.compose.foundation.pager.PagerMeasureResult r9, boolean r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 385
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.pager.PagerState.applyMeasureResult$foundation_release(androidx.compose.foundation.pager.PagerMeasureResult, boolean, boolean):void");
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
    public final long m180getUpDownDifferenceF1C5BW0$foundation_release() {
        return ((Offset) ((SnapshotMutableStateImpl) this.upDownDifference$delegate).getValue()).packedValue;
    }

    public final boolean isNotGestureAction$foundation_release() {
        return ((int) Float.intBitsToFloat((int) (m180getUpDownDifferenceF1C5BW0$foundation_release() >> 32))) == 0 && ((int) Float.intBitsToFloat((int) (m180getUpDownDifferenceF1C5BW0$foundation_release() & 4294967295L))) == 0;
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
                this.currentPrefetchHandle = this.prefetchState.m170schedulePrefetchVKLhPVY(i2, this.premeasureConstraints, null);
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
            ObservableScopeInvalidator.m173invalidateScopeimpl(this.measurementScopeInvalidator);
            return;
        }
        Remeasurement remeasurement = (Remeasurement) ((SnapshotMutableStateImpl) this.remeasurement$delegate).getValue();
        if (remeasurement != null) {
            ((LayoutNode) remeasurement).forceRemeasure();
        }
    }

    /* JADX WARN: Type inference failed for: r5v10, types: [androidx.compose.foundation.pager.PagerState$remeasurementModifier$1] */
    public PagerState(int i, float f, PrefetchScheduler prefetchScheduler) {
        MutableState mutableStateOf;
        MutableState mutableStateOf2;
        double d = f;
        if (-0.5d > d || d > 0.5d) {
            InlineClassHelperKt.throwIllegalArgumentException("currentPageOffsetFraction " + f + " is not within the range -0.5 to 0.5");
        }
        Offset.Companion.getClass();
        this.upDownDifference$delegate = SnapshotStateKt.mutableStateOf$default(Offset.m393boximpl(0L));
        this.scrollPosition = new PagerScrollPosition(i, f, this);
        this.firstVisiblePage = i;
        this.maxScrollOffset = Long.MAX_VALUE;
        this.scrollableState = ScrollableStateKt.ScrollableState(new Function1() { // from class: androidx.compose.foundation.pager.PagerState$scrollableState$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                PagerMeasureResult pagerMeasureResult;
                float floatValue = ((Number) obj).floatValue();
                PagerState pagerState = PagerState.this;
                pagerState.getClass();
                long currentAbsoluteScrollOffset = PagerScrollPositionKt.currentAbsoluteScrollOffset(pagerState);
                float f2 = pagerState.accumulator + floatValue;
                long roundToLong = MathKt__MathJVMKt.roundToLong(f2);
                pagerState.accumulator = f2 - roundToLong;
                if (Math.abs(floatValue) >= 1.0E-4f) {
                    long j = currentAbsoluteScrollOffset + roundToLong;
                    long coerceIn = RangesKt___RangesKt.coerceIn(j, pagerState.minScrollOffset, pagerState.maxScrollOffset);
                    boolean z = j != coerceIn;
                    long j2 = coerceIn - currentAbsoluteScrollOffset;
                    float f3 = j2;
                    pagerState.previousPassDelta = f3;
                    if (Math.abs(j2) != 0) {
                        ((SnapshotMutableStateImpl) pagerState.isLastScrollForwardState).setValue(Boolean.valueOf(f3 > 0.0f));
                        ((SnapshotMutableStateImpl) pagerState.isLastScrollBackwardState).setValue(Boolean.valueOf(f3 < 0.0f));
                    }
                    int i2 = (int) j2;
                    int i3 = -i2;
                    PagerMeasureResult copyWithScrollDeltaWithoutRemeasure = ((PagerMeasureResult) ((SnapshotMutableStateImpl) pagerState.pagerLayoutInfoState).getValue()).copyWithScrollDeltaWithoutRemeasure(i3);
                    if (copyWithScrollDeltaWithoutRemeasure != null && (pagerMeasureResult = pagerState.approachLayoutInfo) != null) {
                        PagerMeasureResult copyWithScrollDeltaWithoutRemeasure2 = pagerMeasureResult.copyWithScrollDeltaWithoutRemeasure(i3);
                        if (copyWithScrollDeltaWithoutRemeasure2 != null) {
                            pagerState.approachLayoutInfo = copyWithScrollDeltaWithoutRemeasure2;
                        } else {
                            copyWithScrollDeltaWithoutRemeasure = null;
                        }
                    }
                    if (copyWithScrollDeltaWithoutRemeasure != null) {
                        pagerState.applyMeasureResult$foundation_release(copyWithScrollDeltaWithoutRemeasure, pagerState.hasLookaheadOccurred, true);
                        ObservableScopeInvalidator.m173invalidateScopeimpl(pagerState.placementScopeInvalidator);
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
                    floatValue = (z ? Long.valueOf(j2) : Float.valueOf(floatValue)).floatValue();
                }
                return Float.valueOf(floatValue);
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
                return Integer.valueOf(PagerState.this.scrollableState.isScrollInProgress() ? ((SnapshotMutableIntStateImpl) PagerState.this.settledPageState$delegate).getIntValue() : PagerState.this.getCurrentPage());
            }
        });
        SnapshotStateKt.derivedStateOf(SnapshotStateKt.structuralEqualityPolicy(), new Function0() { // from class: androidx.compose.foundation.pager.PagerState$targetPage$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int currentPage;
                if (!PagerState.this.scrollableState.isScrollInProgress()) {
                    currentPage = PagerState.this.getCurrentPage();
                } else if (((SnapshotMutableIntStateImpl) PagerState.this.programmaticScrollTargetPage$delegate).getIntValue() != -1) {
                    currentPage = ((SnapshotMutableIntStateImpl) PagerState.this.programmaticScrollTargetPage$delegate).getIntValue();
                } else {
                    float abs = Math.abs(PagerState.this.getCurrentPageOffsetFraction());
                    PagerState pagerState = PagerState.this;
                    currentPage = abs >= Math.abs(Math.min(pagerState.density.mo57toPx0680j_4(PagerStateKt.DefaultPositionThreshold), ((float) pagerState.getPageSize$foundation_release()) / 2.0f) / ((float) pagerState.getPageSize$foundation_release())) ? ((Boolean) ((SnapshotMutableStateImpl) PagerState.this.isLastScrollForwardState).getValue()).booleanValue() ? PagerState.this.firstVisiblePage + 1 : PagerState.this.firstVisiblePage : PagerState.this.getCurrentPage();
                }
                return Integer.valueOf(PagerState.this.coerceInPageRange(currentPage));
            }
        });
        this.prefetchState = new LazyLayoutPrefetchState(prefetchScheduler, new Function1() { // from class: androidx.compose.foundation.pager.PagerState$prefetchState$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                NestedPrefetchScope nestedPrefetchScope = (NestedPrefetchScope) obj;
                Snapshot.Companion companion = Snapshot.Companion;
                PagerState pagerState = PagerState.this;
                companion.getClass();
                Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
                Snapshot makeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                try {
                    nestedPrefetchScope.schedulePrefetch(pagerState.firstVisiblePage);
                    Unit unit = Unit.INSTANCE;
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
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
                ((SnapshotMutableStateImpl) PagerState.this.remeasurement$delegate).setValue(layoutNode);
            }
        };
        this.premeasureConstraints = ConstraintsKt.Constraints$default(0, 0, 0, 0, 15);
        this.pinnedPages = new LazyLayoutPinnedItemList();
        mutableStateOf = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        this.placementScopeInvalidator = mutableStateOf;
        mutableStateOf2 = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        this.measurementScopeInvalidator = mutableStateOf2;
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
