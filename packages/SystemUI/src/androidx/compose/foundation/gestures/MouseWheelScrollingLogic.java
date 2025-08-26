package androidx.compose.foundation.gestures;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.foundation.MutatePriority;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.sequences.SequenceBuilderIterator;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.SupervisorCoroutine;
import kotlinx.coroutines.TimeoutKt;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

/* loaded from: classes.dex */
public final class MouseWheelScrollingLogic {
    public Density density;
    public boolean isScrolling;
    public final ScrollConfig mouseWheelScrollConfig;
    public final Function2 onScrollStopped;
    public StandaloneCoroutine receivingMouseWheelEventsJob;
    public final ScrollingLogic scrollingLogic;
    public final BufferedChannel channel = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6);
    public final MouseWheelVelocityTracker velocityTracker = new MouseWheelVelocityTracker();

    final class MouseWheelScrollDelta {
        public final boolean shouldApplyImmediately;
        public final long timeMillis;
        public final long value;

        public /* synthetic */ MouseWheelScrollDelta(long j, long j2, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, j2, z);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MouseWheelScrollDelta)) {
                return false;
            }
            MouseWheelScrollDelta mouseWheelScrollDelta = (MouseWheelScrollDelta) obj;
            return Offset.m398equalsimpl0(this.value, mouseWheelScrollDelta.value) && this.timeMillis == mouseWheelScrollDelta.timeMillis && this.shouldApplyImmediately == mouseWheelScrollDelta.shouldApplyImmediately;
        }

        public final int hashCode() {
            Offset.Companion companion = Offset.Companion;
            return Boolean.hashCode(this.shouldApplyImmediately) + MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.value) * 31, 31, this.timeMillis);
        }

        public final MouseWheelScrollDelta plus(MouseWheelScrollDelta mouseWheelScrollDelta) {
            return new MouseWheelScrollDelta(Offset.m403plusMKHz9U(this.value, mouseWheelScrollDelta.value), Math.max(this.timeMillis, mouseWheelScrollDelta.timeMillis), this.shouldApplyImmediately, null);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("MouseWheelScrollDelta(value=");
            sb.append((Object) Offset.m405toStringimpl(this.value));
            sb.append(", timeMillis=");
            sb.append(this.timeMillis);
            sb.append(", shouldApplyImmediately=");
            return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.shouldApplyImmediately, ')');
        }

        private MouseWheelScrollDelta(long j, long j2, boolean z) {
            this.value = j;
            this.timeMillis = j2;
            this.shouldApplyImmediately = z;
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.MouseWheelScrollingLogic$userScroll$1, reason: invalid class name and case insensitive filesystem */
    final class C06961 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C06961(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MouseWheelScrollingLogic.this.userScroll(null, null, this);
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.MouseWheelScrollingLogic$userScroll$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        final /* synthetic */ ScrollingLogic $this_userScroll;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ScrollingLogic scrollingLogic, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$this_userScroll = scrollingLogic;
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$this_userScroll, this.$block, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ScrollingLogic scrollingLogic = this.$this_userScroll;
                MutatePriority mutatePriority = MutatePriority.UserInput;
                Function2 function2 = this.$block;
                this.label = 1;
                if (scrollingLogic.scroll(mutatePriority, function2, this) == coroutineSingletons) {
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

    public MouseWheelScrollingLogic(ScrollingLogic scrollingLogic, ScrollConfig scrollConfig, Function2 function2, Density density) {
        this.scrollingLogic = scrollingLogic;
        this.mouseWheelScrollConfig = scrollConfig;
        this.onScrollStopped = function2;
        this.density = density;
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0127, code lost:
    
        if (r4.onScrollStopped.invoke(r0, r9) == r10) goto L40;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
    /* JADX WARN: Type inference failed for: r0v13, types: [T, androidx.compose.foundation.gestures.MouseWheelScrollingLogic$MouseWheelScrollDelta] */
    /* JADX WARN: Type inference failed for: r0v8, types: [T, androidx.compose.animation.core.AnimationState] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$dispatchMouseWheelScroll(MouseWheelScrollingLogic mouseWheelScrollingLogic, ScrollingLogic scrollingLogic, MouseWheelScrollDelta mouseWheelScrollDelta, float f, float f2, ContinuationImpl continuationImpl) {
        MouseWheelScrollingLogic$dispatchMouseWheelScroll$1 mouseWheelScrollingLogic$dispatchMouseWheelScroll$1;
        Ref$FloatRef ref$FloatRef;
        MouseWheelScrollingLogic mouseWheelScrollingLogic2;
        float f3;
        ScrollingLogic scrollingLogic2;
        mouseWheelScrollingLogic.getClass();
        if (continuationImpl instanceof MouseWheelScrollingLogic$dispatchMouseWheelScroll$1) {
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$1 = (MouseWheelScrollingLogic$dispatchMouseWheelScroll$1) continuationImpl;
            int i = mouseWheelScrollingLogic$dispatchMouseWheelScroll$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$1.label = i - Integer.MIN_VALUE;
            } else {
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$1 = new MouseWheelScrollingLogic$dispatchMouseWheelScroll$1(mouseWheelScrollingLogic, continuationImpl);
            }
        }
        MouseWheelScrollingLogic$dispatchMouseWheelScroll$1 mouseWheelScrollingLogic$dispatchMouseWheelScroll$12 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$1;
        Object obj = mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = mouseWheelScrollDelta;
            mouseWheelScrollingLogic.trackVelocity(mouseWheelScrollDelta);
            MouseWheelScrollDelta mouseWheelScrollDeltaSumOrNull = sumOrNull(mouseWheelScrollingLogic.channel);
            if (mouseWheelScrollDeltaSumOrNull != null) {
                mouseWheelScrollingLogic.trackVelocity(mouseWheelScrollDeltaSumOrNull);
                ref$ObjectRef.element = ((MouseWheelScrollDelta) ref$ObjectRef.element).plus(mouseWheelScrollDeltaSumOrNull);
            }
            Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
            float fM85toFloatk4lQ0M = scrollingLogic.m85toFloatk4lQ0M(scrollingLogic.m84reverseIfNeededMKHz9U(((MouseWheelScrollDelta) ref$ObjectRef.element).value));
            ref$FloatRef2.element = fM85toFloatk4lQ0M;
            if (MouseWheelScrollableKt.access$isLowScrollingDelta(fM85toFloatk4lQ0M)) {
                return Unit.INSTANCE;
            }
            Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            ref$ObjectRef2.element = AnimationStateKt.AnimationState$default(0.0f, 0.0f, 30);
            MouseWheelScrollingLogic$dispatchMouseWheelScroll$3 mouseWheelScrollingLogic$dispatchMouseWheelScroll$3 = new MouseWheelScrollingLogic$dispatchMouseWheelScroll$3(ref$FloatRef2, ref$ObjectRef2, ref$ObjectRef, f, mouseWheelScrollingLogic, f2, scrollingLogic, null);
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.L$0 = mouseWheelScrollingLogic;
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.L$1 = scrollingLogic;
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.L$2 = ref$FloatRef2;
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.F$0 = f2;
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.label = 1;
            if (mouseWheelScrollingLogic.userScroll(scrollingLogic, mouseWheelScrollingLogic$dispatchMouseWheelScroll$3, mouseWheelScrollingLogic$dispatchMouseWheelScroll$12) != coroutineSingletons) {
                ref$FloatRef = ref$FloatRef2;
                mouseWheelScrollingLogic2 = mouseWheelScrollingLogic;
                f3 = f2;
                scrollingLogic2 = scrollingLogic;
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
        f3 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.F$0;
        ref$FloatRef = (Ref$FloatRef) mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.L$2;
        scrollingLogic2 = (ScrollingLogic) mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.L$1;
        mouseWheelScrollingLogic2 = (MouseWheelScrollingLogic) mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.L$0;
        ResultKt.throwOnFailure(obj);
        MouseWheelVelocityTracker mouseWheelVelocityTracker = mouseWheelScrollingLogic2.velocityTracker;
        long jVelocity = VelocityKt.Velocity(mouseWheelVelocityTracker.xVelocityTracker.calculateVelocity(Float.MAX_VALUE), mouseWheelVelocityTracker.yVelocityTracker.calculateVelocity(Float.MAX_VALUE));
        Velocity.Companion.getClass();
        if (jVelocity == 0) {
            float fReverseIfNeeded = scrollingLogic2.reverseIfNeeded(Math.signum(ref$FloatRef.element)) * Math.min(Math.abs(ref$FloatRef.element) / 100, f3) * 1000;
            if (fReverseIfNeeded == 0.0f) {
                jVelocity = 0;
            } else {
                jVelocity = scrollingLogic2.orientation == Orientation.Horizontal ? VelocityKt.Velocity(fReverseIfNeeded, 0.0f) : VelocityKt.Velocity(0.0f, fReverseIfNeeded);
            }
        }
        Velocity velocityM878boximpl = Velocity.m878boximpl(jVelocity);
        mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.L$0 = null;
        mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.L$1 = null;
        mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.L$2 = null;
        mouseWheelScrollingLogic$dispatchMouseWheelScroll$12.label = 2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Type inference failed for: r0v7, types: [T, androidx.compose.animation.core.AnimationState] */
    /* JADX WARN: Type inference failed for: r3v3, types: [T, androidx.compose.foundation.gestures.MouseWheelScrollingLogic$MouseWheelScrollDelta] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$dispatchMouseWheelScroll$waitNextScrollDelta(MouseWheelScrollingLogic mouseWheelScrollingLogic, Ref$ObjectRef ref$ObjectRef, Ref$FloatRef ref$FloatRef, ScrollingLogic scrollingLogic, Ref$ObjectRef ref$ObjectRef2, long j, ContinuationImpl continuationImpl) throws Throwable {
        MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1 mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1;
        ScrollingLogic scrollingLogic2;
        Ref$ObjectRef ref$ObjectRef3;
        Ref$ObjectRef ref$ObjectRef4;
        Ref$FloatRef ref$FloatRef2;
        boolean z;
        if (continuationImpl instanceof MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1) {
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1 = (MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1) continuationImpl;
            int i = mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.label = i - Integer.MIN_VALUE;
            } else {
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1 = new MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1(continuationImpl);
            }
        }
        Object objWithTimeoutOrNull = mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithTimeoutOrNull);
            if (j < 0) {
                return Boolean.FALSE;
            }
            MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$2 mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$2 = new MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$2(mouseWheelScrollingLogic, null);
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.L$0 = mouseWheelScrollingLogic;
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.L$1 = ref$ObjectRef;
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.L$2 = ref$FloatRef;
            scrollingLogic2 = scrollingLogic;
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.L$3 = scrollingLogic2;
            ref$ObjectRef3 = ref$ObjectRef2;
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.L$4 = ref$ObjectRef3;
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.label = 1;
            objWithTimeoutOrNull = TimeoutKt.withTimeoutOrNull(j, mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$2, mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1);
            if (objWithTimeoutOrNull == coroutineSingletons) {
                return coroutineSingletons;
            }
            ref$ObjectRef4 = ref$ObjectRef;
            ref$FloatRef2 = ref$FloatRef;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Ref$ObjectRef ref$ObjectRef5 = (Ref$ObjectRef) mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.L$4;
            ScrollingLogic scrollingLogic3 = (ScrollingLogic) mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.L$3;
            ref$FloatRef2 = (Ref$FloatRef) mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.L$2;
            ref$ObjectRef4 = (Ref$ObjectRef) mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.L$1;
            MouseWheelScrollingLogic mouseWheelScrollingLogic2 = (MouseWheelScrollingLogic) mouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1.L$0;
            ResultKt.throwOnFailure(objWithTimeoutOrNull);
            ref$ObjectRef3 = ref$ObjectRef5;
            scrollingLogic2 = scrollingLogic3;
            mouseWheelScrollingLogic = mouseWheelScrollingLogic2;
        }
        MouseWheelScrollDelta mouseWheelScrollDelta = (MouseWheelScrollDelta) objWithTimeoutOrNull;
        if (mouseWheelScrollDelta != null) {
            ?? mouseWheelScrollDelta2 = new MouseWheelScrollDelta(mouseWheelScrollDelta.value, mouseWheelScrollDelta.timeMillis, ((MouseWheelScrollDelta) ref$ObjectRef4.element).shouldApplyImmediately, null);
            ref$ObjectRef4.element = mouseWheelScrollDelta2;
            ref$FloatRef2.element = scrollingLogic2.m85toFloatk4lQ0M(scrollingLogic2.m84reverseIfNeededMKHz9U(mouseWheelScrollDelta2.value));
            ref$ObjectRef3.element = AnimationStateKt.AnimationState$default(0.0f, 0.0f, 30);
            mouseWheelScrollingLogic.trackVelocity(mouseWheelScrollDelta);
            z = !MouseWheelScrollableKt.access$isLowScrollingDelta(ref$FloatRef2.element);
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    public static MouseWheelScrollDelta sumOrNull(final BufferedChannel bufferedChannel) {
        MouseWheelScrollDelta mouseWheelScrollDelta = null;
        SequenceBuilderIterator it = SequencesKt__SequenceBuilderKt.iterator(new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new MouseWheelScrollingLogic$untilNull$1(new Function0() { // from class: androidx.compose.foundation.gestures.MouseWheelScrollingLogic.sumOrNull.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (MouseWheelScrollDelta) ChannelResult.m3479getOrNullimpl(bufferedChannel.mo3475tryReceivePtdJZtk());
            }
        }, null)).$block$inlined);
        while (it.hasNext()) {
            MouseWheelScrollDelta mouseWheelScrollDeltaPlus = (MouseWheelScrollDelta) it.next();
            if (mouseWheelScrollDelta != null) {
                mouseWheelScrollDeltaPlus = mouseWheelScrollDelta.plus(mouseWheelScrollDeltaPlus);
            }
            mouseWheelScrollDelta = mouseWheelScrollDeltaPlus;
        }
        return mouseWheelScrollDelta;
    }

    public final void trackVelocity(MouseWheelScrollDelta mouseWheelScrollDelta) {
        long j = mouseWheelScrollDelta.timeMillis;
        MouseWheelVelocityTracker mouseWheelVelocityTracker = this.velocityTracker;
        mouseWheelVelocityTracker.getClass();
        long j2 = mouseWheelScrollDelta.value;
        mouseWheelVelocityTracker.xVelocityTracker.addDataPoint(Float.intBitsToFloat((int) (j2 >> 32)), j);
        mouseWheelVelocityTracker.yVelocityTracker.addDataPoint(Float.intBitsToFloat((int) (j2 & 4294967295L)), j);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object userScroll(ScrollingLogic scrollingLogic, Function2 function2, ContinuationImpl continuationImpl) {
        C06961 c06961;
        if (continuationImpl instanceof C06961) {
            c06961 = (C06961) continuationImpl;
            int i = c06961.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c06961.label = i - Integer.MIN_VALUE;
            } else {
                c06961 = new C06961(continuationImpl);
            }
        }
        Object obj = c06961.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c06961.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            this.isScrolling = true;
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(scrollingLogic, function2, null);
            c06961.L$0 = this;
            c06961.label = 1;
            SupervisorCoroutine supervisorCoroutine = new SupervisorCoroutine(c06961.getContext(), c06961);
            if (UndispatchedKt.startUndispatchedOrReturn(supervisorCoroutine, supervisorCoroutine, anonymousClass2) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (MouseWheelScrollingLogic) c06961.L$0;
            ResultKt.throwOnFailure(obj);
        }
        this.isScrolling = false;
        return Unit.INSTANCE;
    }

    public static final float access$dispatchMouseWheelScroll(MouseWheelScrollingLogic mouseWheelScrollingLogic, NestedScrollScope nestedScrollScope, float f) {
        ScrollingLogic scrollingLogic = mouseWheelScrollingLogic.scrollingLogic;
        long jM86toOffsettuRUvjQ = scrollingLogic.m86toOffsettuRUvjQ(scrollingLogic.reverseIfNeeded(f));
        NestedScrollSource.Companion.getClass();
        int i = NestedScrollSource.UserInput;
        ScrollingLogic scrollingLogic2 = ((ScrollingLogic$nestedScrollScope$1) nestedScrollScope).this$0;
        return scrollingLogic.m85toFloatk4lQ0M(scrollingLogic.m84reverseIfNeededMKHz9U(ScrollingLogic.m81access$performScroll3eAAhYA(scrollingLogic2, scrollingLogic2.outerStateScope, jM86toOffsettuRUvjQ, i)));
    }
}
