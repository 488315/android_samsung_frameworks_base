package androidx.compose.foundation.gestures;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.unit.Velocity;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$LongRef;

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

    /* renamed from: androidx.compose.foundation.gestures.ScrollingLogic$scroll$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = ScrollingLogic.this.new AnonymousClass2(this.$block, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((ScrollScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ScrollScope scrollScope = (ScrollScope) this.L$0;
                ScrollingLogic scrollingLogic = ScrollingLogic.this;
                scrollingLogic.outerStateScope = scrollScope;
                Function2 function2 = this.$block;
                ScrollingLogic$nestedScrollScope$1 scrollingLogic$nestedScrollScope$1 = scrollingLogic.nestedScrollScope;
                this.label = 1;
                if (function2.invoke(scrollingLogic$nestedScrollScope$1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

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
            public final Object mo781invoke(Object obj) {
                long j = ((Offset) obj).packedValue;
                ScrollingLogic scrollingLogic = this.this$0;
                return Offset.m395boximpl(ScrollingLogic.m81access$performScroll3eAAhYA(scrollingLogic, scrollingLogic.outerStateScope, j, scrollingLogic.latestScrollSource));
            }
        };
    }

    /* renamed from: access$performScroll-3eAAhYA, reason: not valid java name */
    public static final long m81access$performScroll3eAAhYA(ScrollingLogic scrollingLogic, ScrollScope scrollScope, long j, int i) {
        long jM585dispatchPreScrollOzD1aCk = scrollingLogic.nestedScrollDispatcher.m585dispatchPreScrollOzD1aCk(i, j);
        long jM402minusMKHz9U = Offset.m402minusMKHz9U(j, jM585dispatchPreScrollOzD1aCk);
        long jM84reverseIfNeededMKHz9U = scrollingLogic.m84reverseIfNeededMKHz9U(scrollingLogic.m86toOffsettuRUvjQ(scrollScope.scrollBy(scrollingLogic.m85toFloatk4lQ0M(scrollingLogic.m84reverseIfNeededMKHz9U(Offset.m396copydBAh8RU$default(jM402minusMKHz9U, 0.0f, scrollingLogic.orientation == Orientation.Horizontal ? 1 : 2))))));
        return Offset.m403plusMKHz9U(Offset.m403plusMKHz9U(jM585dispatchPreScrollOzD1aCk, jM84reverseIfNeededMKHz9U), scrollingLogic.nestedScrollDispatcher.m583dispatchPostScrollDzOQY0M(i, jM84reverseIfNeededMKHz9U, Offset.m402minusMKHz9U(jM402minusMKHz9U, jM84reverseIfNeededMKHz9U)));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: doFlingAnimation-QWom1Mo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m82doFlingAnimationQWom1Mo(long j, ContinuationImpl continuationImpl) throws Throwable {
        ScrollingLogic$doFlingAnimation$1 scrollingLogic$doFlingAnimation$1;
        Ref$LongRef ref$LongRef;
        ScrollingLogic scrollingLogic;
        Throwable th;
        ScrollingLogic scrollingLogic2;
        if (continuationImpl instanceof ScrollingLogic$doFlingAnimation$1) {
            scrollingLogic$doFlingAnimation$1 = (ScrollingLogic$doFlingAnimation$1) continuationImpl;
            int i = scrollingLogic$doFlingAnimation$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                scrollingLogic$doFlingAnimation$1.label = i - Integer.MIN_VALUE;
            } else {
                scrollingLogic$doFlingAnimation$1 = new ScrollingLogic$doFlingAnimation$1(this, continuationImpl);
            }
        }
        Object obj = scrollingLogic$doFlingAnimation$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = scrollingLogic$doFlingAnimation$1.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Ref$LongRef ref$LongRef2 = (Ref$LongRef) scrollingLogic$doFlingAnimation$1.L$1;
            ScrollingLogic scrollingLogic3 = (ScrollingLogic) scrollingLogic$doFlingAnimation$1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                ref$LongRef = ref$LongRef2;
                scrollingLogic2 = scrollingLogic3;
                scrollingLogic2.isFlinging = false;
                return Velocity.m878boximpl(ref$LongRef.element);
            } catch (Throwable th2) {
                th = th2;
                scrollingLogic = scrollingLogic3;
                scrollingLogic.isFlinging = false;
                throw th;
            }
        }
        ResultKt.throwOnFailure(obj);
        ref$LongRef = new Ref$LongRef();
        ref$LongRef.element = j;
        this.isFlinging = true;
        try {
            MutatePriority mutatePriority = MutatePriority.Default;
            scrollingLogic = this;
            try {
                ScrollingLogic$doFlingAnimation$2 scrollingLogic$doFlingAnimation$2 = new ScrollingLogic$doFlingAnimation$2(scrollingLogic, ref$LongRef, j, null);
                scrollingLogic$doFlingAnimation$1.L$0 = scrollingLogic;
                scrollingLogic$doFlingAnimation$1.L$1 = ref$LongRef;
                scrollingLogic$doFlingAnimation$1.label = 1;
                if (scrollingLogic.scroll(mutatePriority, scrollingLogic$doFlingAnimation$2, scrollingLogic$doFlingAnimation$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                scrollingLogic2 = scrollingLogic;
                scrollingLogic2.isFlinging = false;
                return Velocity.m878boximpl(ref$LongRef.element);
            } catch (Throwable th3) {
                th = th3;
                th = th;
                scrollingLogic.isFlinging = false;
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            scrollingLogic = this;
        }
    }

    /* renamed from: onScrollStopped-BMRW4eQ, reason: not valid java name */
    public final Object m83onScrollStoppedBMRW4eQ(long j, boolean z, SuspendLambda suspendLambda) {
        if (z) {
            Function1 function1 = ScrollableKt.CanDragCalculation;
            return Unit.INSTANCE;
        }
        long jM879copyOhffZ5M$default = Velocity.m879copyOhffZ5M$default(0.0f, 0.0f, j, this.orientation == Orientation.Horizontal ? 1 : 2);
        ScrollingLogic$onScrollStopped$performFling$1 scrollingLogic$onScrollStopped$performFling$1 = new ScrollingLogic$onScrollStopped$performFling$1(this, null);
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        if (overscrollEffect == null || !(this.scrollableState.getCanScrollForward() || this.scrollableState.getCanScrollBackward())) {
            Object objInvoke = scrollingLogic$onScrollStopped$performFling$1.invoke(Velocity.m878boximpl(jM879copyOhffZ5M$default), suspendLambda);
            return objInvoke == CoroutineSingletons.COROUTINE_SUSPENDED ? objInvoke : Unit.INSTANCE;
        }
        Object objMo19applyToFlingBMRW4eQ = overscrollEffect.mo19applyToFlingBMRW4eQ(jM879copyOhffZ5M$default, scrollingLogic$onScrollStopped$performFling$1, suspendLambda);
        return objMo19applyToFlingBMRW4eQ == CoroutineSingletons.COROUTINE_SUSPENDED ? objMo19applyToFlingBMRW4eQ : Unit.INSTANCE;
    }

    public final float reverseIfNeeded(float f) {
        return this.reverseDirection ? f * (-1) : f;
    }

    /* renamed from: reverseIfNeeded-MK-Hz9U, reason: not valid java name */
    public final long m84reverseIfNeededMKHz9U(long j) {
        return this.reverseDirection ? Offset.m404timestuRUvjQ(-1.0f, j) : j;
    }

    public final Object scroll(MutatePriority mutatePriority, Function2 function2, ContinuationImpl continuationImpl) {
        Object objScroll = this.scrollableState.scroll(mutatePriority, new AnonymousClass2(function2, null), continuationImpl);
        return objScroll == CoroutineSingletons.COROUTINE_SUSPENDED ? objScroll : Unit.INSTANCE;
    }

    /* renamed from: toFloat-k-4lQ0M, reason: not valid java name */
    public final float m85toFloatk4lQ0M(long j) {
        return Float.intBitsToFloat((int) (this.orientation == Orientation.Horizontal ? j >> 32 : j & 4294967295L));
    }

    /* renamed from: toOffset-tuRUvjQ, reason: not valid java name */
    public final long m86toOffsettuRUvjQ(float f) {
        if (f == 0.0f) {
            Offset.Companion.getClass();
            return 0L;
        }
        if (this.orientation == Orientation.Horizontal) {
            long jFloatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
            Offset.Companion companion = Offset.Companion;
            return jFloatToRawIntBits;
        }
        long jFloatToRawIntBits2 = (Float.floatToRawIntBits(f) & 4294967295L) | (Float.floatToRawIntBits(0.0f) << 32);
        Offset.Companion companion2 = Offset.Companion;
        return jFloatToRawIntBits2;
    }
}
