package androidx.compose.foundation.gestures;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.unit.Velocity;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScrollingLogic {
    public FlingBehavior flingBehavior;
    public boolean isFlinging;
    public final Function0 isScrollableNodeAttached;
    public int latestScrollSource;
    public NestedScrollDispatcher nestedScrollDispatcher;
    public final ScrollingLogic$nestedScrollScope$1 nestedScrollScope;
    public Orientation orientation;
    public ScrollScope outerStateScope;
    public OverscrollEffect overscrollEffect;
    public final Function1 performScrollForOverscroll;
    public boolean reverseDirection;
    public ScrollableState scrollableState;

    public ScrollingLogic(ScrollableState scrollableState, OverscrollEffect overscrollEffect, FlingBehavior flingBehavior, Orientation orientation, boolean z, NestedScrollDispatcher nestedScrollDispatcher, Function0 function0) {
        this.scrollableState = scrollableState;
        this.overscrollEffect = overscrollEffect;
        this.flingBehavior = flingBehavior;
        this.orientation = orientation;
        this.reverseDirection = z;
        this.nestedScrollDispatcher = nestedScrollDispatcher;
        this.isScrollableNodeAttached = function0;
        NestedScrollSource.Companion.getClass();
        this.latestScrollSource = NestedScrollSource.UserInput;
        this.outerStateScope = ScrollableKt.NoOpScrollScope;
        this.nestedScrollScope = new ScrollingLogic$nestedScrollScope$1(this);
        this.performScrollForOverscroll = new Function1() { // from class: androidx.compose.foundation.gestures.ScrollingLogic$performScrollForOverscroll$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                long j = ((Offset) obj).packedValue;
                ScrollingLogic scrollingLogic = ScrollingLogic.this;
                return Offset.m393boximpl(ScrollingLogic.m80access$performScroll3eAAhYA(scrollingLogic, scrollingLogic.outerStateScope, j, scrollingLogic.latestScrollSource));
            }
        };
    }

    /* renamed from: access$performScroll-3eAAhYA, reason: not valid java name */
    public static final long m80access$performScroll3eAAhYA(ScrollingLogic scrollingLogic, ScrollScope scrollScope, long j, int i) {
        long m583dispatchPreScrollOzD1aCk = scrollingLogic.nestedScrollDispatcher.m583dispatchPreScrollOzD1aCk(i, j);
        long m400minusMKHz9U = Offset.m400minusMKHz9U(j, m583dispatchPreScrollOzD1aCk);
        long m83reverseIfNeededMKHz9U = scrollingLogic.m83reverseIfNeededMKHz9U(scrollingLogic.m85toOffsettuRUvjQ(scrollScope.scrollBy(scrollingLogic.m84toFloatk4lQ0M(scrollingLogic.m83reverseIfNeededMKHz9U(Offset.m394copydBAh8RU$default(m400minusMKHz9U, 0.0f, scrollingLogic.orientation == Orientation.Horizontal ? 1 : 2))))));
        return Offset.m401plusMKHz9U(Offset.m401plusMKHz9U(m583dispatchPreScrollOzD1aCk, m83reverseIfNeededMKHz9U), scrollingLogic.nestedScrollDispatcher.m581dispatchPostScrollDzOQY0M(i, m83reverseIfNeededMKHz9U, Offset.m400minusMKHz9U(m400minusMKHz9U, m83reverseIfNeededMKHz9U)));
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* renamed from: doFlingAnimation-QWom1Mo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m81doFlingAnimationQWom1Mo(long r12, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            r11 = this;
            boolean r0 = r14 instanceof androidx.compose.foundation.gestures.ScrollingLogic$doFlingAnimation$1
            if (r0 == 0) goto L13
            r0 = r14
            androidx.compose.foundation.gestures.ScrollingLogic$doFlingAnimation$1 r0 = (androidx.compose.foundation.gestures.ScrollingLogic$doFlingAnimation$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.ScrollingLogic$doFlingAnimation$1 r0 = new androidx.compose.foundation.gestures.ScrollingLogic$doFlingAnimation$1
            r0.<init>(r11, r14)
        L18:
            java.lang.Object r14 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L3e
            if (r2 != r4) goto L36
            java.lang.Object r11 = r0.L$1
            kotlin.jvm.internal.Ref$LongRef r11 = (kotlin.jvm.internal.Ref$LongRef) r11
            java.lang.Object r12 = r0.L$0
            androidx.compose.foundation.gestures.ScrollingLogic r12 = (androidx.compose.foundation.gestures.ScrollingLogic) r12
            kotlin.ResultKt.throwOnFailure(r14)     // Catch: java.lang.Throwable -> L32
            r7 = r11
            r11 = r12
            goto L62
        L32:
            r0 = move-exception
            r11 = r0
            r6 = r12
            goto L71
        L36:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L3e:
            kotlin.ResultKt.throwOnFailure(r14)
            kotlin.jvm.internal.Ref$LongRef r7 = new kotlin.jvm.internal.Ref$LongRef
            r7.<init>()
            r7.element = r12
            r11.isFlinging = r4
            androidx.compose.foundation.MutatePriority r14 = androidx.compose.foundation.MutatePriority.Default     // Catch: java.lang.Throwable -> L6e
            androidx.compose.foundation.gestures.ScrollingLogic$doFlingAnimation$2 r5 = new androidx.compose.foundation.gestures.ScrollingLogic$doFlingAnimation$2     // Catch: java.lang.Throwable -> L6e
            r10 = 0
            r6 = r11
            r8 = r12
            r5.<init>(r6, r7, r8, r10)     // Catch: java.lang.Throwable -> L6b
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L6b
            r0.L$1 = r7     // Catch: java.lang.Throwable -> L6b
            r0.label = r4     // Catch: java.lang.Throwable -> L6b
            java.lang.Object r11 = r6.scroll(r14, r5, r0)     // Catch: java.lang.Throwable -> L6b
            if (r11 != r1) goto L61
            return r1
        L61:
            r11 = r6
        L62:
            r11.isFlinging = r3
            long r11 = r7.element
            androidx.compose.ui.unit.Velocity r11 = androidx.compose.ui.unit.Velocity.m876boximpl(r11)
            return r11
        L6b:
            r0 = move-exception
        L6c:
            r11 = r0
            goto L71
        L6e:
            r0 = move-exception
            r6 = r11
            goto L6c
        L71:
            r6.isFlinging = r3
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.ScrollingLogic.m81doFlingAnimationQWom1Mo(long, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* renamed from: onScrollStopped-BMRW4eQ, reason: not valid java name */
    public final Object m82onScrollStoppedBMRW4eQ(long j, boolean z, SuspendLambda suspendLambda) {
        if (z) {
            Function1 function1 = ScrollableKt.CanDragCalculation;
            return Unit.INSTANCE;
        }
        long m877copyOhffZ5M$default = Velocity.m877copyOhffZ5M$default(0.0f, 0.0f, j, this.orientation == Orientation.Horizontal ? 1 : 2);
        ScrollingLogic$onScrollStopped$performFling$1 scrollingLogic$onScrollStopped$performFling$1 = new ScrollingLogic$onScrollStopped$performFling$1(this, null);
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        if (overscrollEffect == null || !(this.scrollableState.getCanScrollForward() || this.scrollableState.getCanScrollBackward())) {
            Object invoke = scrollingLogic$onScrollStopped$performFling$1.invoke(Velocity.m876boximpl(m877copyOhffZ5M$default), suspendLambda);
            return invoke == CoroutineSingletons.COROUTINE_SUSPENDED ? invoke : Unit.INSTANCE;
        }
        Object mo19applyToFlingBMRW4eQ = overscrollEffect.mo19applyToFlingBMRW4eQ(m877copyOhffZ5M$default, scrollingLogic$onScrollStopped$performFling$1, suspendLambda);
        return mo19applyToFlingBMRW4eQ == CoroutineSingletons.COROUTINE_SUSPENDED ? mo19applyToFlingBMRW4eQ : Unit.INSTANCE;
    }

    public final float reverseIfNeeded(float f) {
        return this.reverseDirection ? f * (-1) : f;
    }

    /* renamed from: reverseIfNeeded-MK-Hz9U, reason: not valid java name */
    public final long m83reverseIfNeededMKHz9U(long j) {
        return this.reverseDirection ? Offset.m402timestuRUvjQ(-1.0f, j) : j;
    }

    public final Object scroll(MutatePriority mutatePriority, Function2 function2, ContinuationImpl continuationImpl) {
        Object scroll = this.scrollableState.scroll(mutatePriority, new ScrollingLogic$scroll$2(this, function2, null), continuationImpl);
        return scroll == CoroutineSingletons.COROUTINE_SUSPENDED ? scroll : Unit.INSTANCE;
    }

    /* renamed from: toFloat-k-4lQ0M, reason: not valid java name */
    public final float m84toFloatk4lQ0M(long j) {
        return Float.intBitsToFloat((int) (this.orientation == Orientation.Horizontal ? j >> 32 : j & 4294967295L));
    }

    /* renamed from: toOffset-tuRUvjQ, reason: not valid java name */
    public final long m85toOffsettuRUvjQ(float f) {
        if (f == 0.0f) {
            Offset.Companion.getClass();
            return 0L;
        }
        if (this.orientation == Orientation.Horizontal) {
            long floatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
            Offset.Companion companion = Offset.Companion;
            return floatToRawIntBits;
        }
        long floatToRawIntBits2 = (Float.floatToRawIntBits(f) & 4294967295L) | (Float.floatToRawIntBits(0.0f) << 32);
        Offset.Companion companion2 = Offset.Companion;
        return floatToRawIntBits2;
    }
}
