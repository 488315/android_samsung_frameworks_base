package androidx.compose.foundation.gestures;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.MouseWheelScrollingLogic;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.unit.Density;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.SequenceBuilderIterator;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return Offset.m396equalsimpl0(this.value, mouseWheelScrollDelta.value) && this.timeMillis == mouseWheelScrollDelta.timeMillis && this.shouldApplyImmediately == mouseWheelScrollDelta.shouldApplyImmediately;
        }

        public final int hashCode() {
            Offset.Companion companion = Offset.Companion;
            return Boolean.hashCode(this.shouldApplyImmediately) + MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.value) * 31, 31, this.timeMillis);
        }

        public final MouseWheelScrollDelta plus(MouseWheelScrollDelta mouseWheelScrollDelta) {
            return new MouseWheelScrollDelta(Offset.m401plusMKHz9U(this.value, mouseWheelScrollDelta.value), Math.max(this.timeMillis, mouseWheelScrollDelta.timeMillis), this.shouldApplyImmediately, null);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("MouseWheelScrollDelta(value=");
            sb.append((Object) Offset.m403toStringimpl(this.value));
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

    public MouseWheelScrollingLogic(ScrollingLogic scrollingLogic, ScrollConfig scrollConfig, Function2 function2, Density density) {
        this.scrollingLogic = scrollingLogic;
        this.mouseWheelScrollConfig = scrollConfig;
        this.onScrollStopped = function2;
        this.density = density;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0127, code lost:
    
        if (r4.onScrollStopped.invoke(r0, r9) != r10) goto L41;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0030  */
    /* JADX WARN: Type inference failed for: r0v13, types: [T, androidx.compose.foundation.gestures.MouseWheelScrollingLogic$MouseWheelScrollDelta] */
    /* JADX WARN: Type inference failed for: r0v8, types: [T, androidx.compose.animation.core.AnimationState] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$dispatchMouseWheelScroll(androidx.compose.foundation.gestures.MouseWheelScrollingLogic r16, androidx.compose.foundation.gestures.ScrollingLogic r17, androidx.compose.foundation.gestures.MouseWheelScrollingLogic.MouseWheelScrollDelta r18, float r19, float r20, kotlin.coroutines.jvm.internal.ContinuationImpl r21) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.MouseWheelScrollingLogic.access$dispatchMouseWheelScroll(androidx.compose.foundation.gestures.MouseWheelScrollingLogic, androidx.compose.foundation.gestures.ScrollingLogic, androidx.compose.foundation.gestures.MouseWheelScrollingLogic$MouseWheelScrollDelta, float, float, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* JADX WARN: Type inference failed for: r0v7, types: [T, androidx.compose.animation.core.AnimationState] */
    /* JADX WARN: Type inference failed for: r3v3, types: [T, androidx.compose.foundation.gestures.MouseWheelScrollingLogic$MouseWheelScrollDelta] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$dispatchMouseWheelScroll$waitNextScrollDelta(androidx.compose.foundation.gestures.MouseWheelScrollingLogic r14, kotlin.jvm.internal.Ref$ObjectRef r15, kotlin.jvm.internal.Ref$FloatRef r16, androidx.compose.foundation.gestures.ScrollingLogic r17, kotlin.jvm.internal.Ref$ObjectRef r18, long r19, kotlin.coroutines.jvm.internal.ContinuationImpl r21) {
        /*
            r0 = r19
            r2 = r21
            boolean r3 = r2 instanceof androidx.compose.foundation.gestures.MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1
            if (r3 == 0) goto L17
            r3 = r2
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1 r3 = (androidx.compose.foundation.gestures.MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1) r3
            int r4 = r3.label
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = r4 & r5
            if (r6 == 0) goto L17
            int r4 = r4 - r5
            r3.label = r4
            goto L1c
        L17:
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1 r3 = new androidx.compose.foundation.gestures.MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1
            r3.<init>(r2)
        L1c:
            java.lang.Object r2 = r3.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r4 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r5 = r3.label
            r6 = 1
            if (r5 == 0) goto L4a
            if (r5 != r6) goto L42
            java.lang.Object r14 = r3.L$4
            kotlin.jvm.internal.Ref$ObjectRef r14 = (kotlin.jvm.internal.Ref$ObjectRef) r14
            java.lang.Object r0 = r3.L$3
            androidx.compose.foundation.gestures.ScrollingLogic r0 = (androidx.compose.foundation.gestures.ScrollingLogic) r0
            java.lang.Object r1 = r3.L$2
            kotlin.jvm.internal.Ref$FloatRef r1 = (kotlin.jvm.internal.Ref$FloatRef) r1
            java.lang.Object r4 = r3.L$1
            kotlin.jvm.internal.Ref$ObjectRef r4 = (kotlin.jvm.internal.Ref$ObjectRef) r4
            java.lang.Object r3 = r3.L$0
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic r3 = (androidx.compose.foundation.gestures.MouseWheelScrollingLogic) r3
            kotlin.ResultKt.throwOnFailure(r2)
            r9 = r14
            r8 = r0
            r14 = r3
            goto L77
        L42:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r0)
            throw r14
        L4a:
            kotlin.ResultKt.throwOnFailure(r2)
            r7 = 0
            int r2 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r2 >= 0) goto L56
            java.lang.Boolean r14 = java.lang.Boolean.FALSE
            return r14
        L56:
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$2 r2 = new androidx.compose.foundation.gestures.MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$2
            r5 = 0
            r2.<init>(r14, r5)
            r3.L$0 = r14
            r3.L$1 = r15
            r7 = r16
            r3.L$2 = r7
            r8 = r17
            r3.L$3 = r8
            r9 = r18
            r3.L$4 = r9
            r3.label = r6
            java.lang.Object r2 = kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(r0, r2, r3)
            if (r2 != r4) goto L75
            return r4
        L75:
            r4 = r15
            r1 = r7
        L77:
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic$MouseWheelScrollDelta r2 = (androidx.compose.foundation.gestures.MouseWheelScrollingLogic.MouseWheelScrollDelta) r2
            if (r2 == 0) goto Lb7
            T r0 = r4.element
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic$MouseWheelScrollDelta r0 = (androidx.compose.foundation.gestures.MouseWheelScrollingLogic.MouseWheelScrollDelta) r0
            boolean r0 = r0.shouldApplyImmediately
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic$MouseWheelScrollDelta r3 = new androidx.compose.foundation.gestures.MouseWheelScrollingLogic$MouseWheelScrollDelta
            long r10 = r2.timeMillis
            r5 = 0
            long r12 = r2.value
            r20 = r0
            r15 = r3
            r21 = r5
            r18 = r10
            r16 = r12
            r15.<init>(r16, r18, r20, r21)
            r0 = r15
            r4.element = r0
            long r3 = r0.value
            long r3 = r8.m83reverseIfNeededMKHz9U(r3)
            float r0 = r8.m84toFloatk4lQ0M(r3)
            r1.element = r0
            r0 = 30
            r3 = 0
            androidx.compose.animation.core.AnimationState r0 = androidx.compose.animation.core.AnimationStateKt.AnimationState$default(r3, r3, r0)
            r9.element = r0
            r14.trackVelocity(r2)
            float r14 = r1.element
            boolean r14 = androidx.compose.foundation.gestures.MouseWheelScrollableKt.access$isLowScrollingDelta(r14)
            r14 = r14 ^ r6
            goto Lb8
        Lb7:
            r14 = 0
        Lb8:
            java.lang.Boolean r14 = java.lang.Boolean.valueOf(r14)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.MouseWheelScrollingLogic.access$dispatchMouseWheelScroll$waitNextScrollDelta(androidx.compose.foundation.gestures.MouseWheelScrollingLogic, kotlin.jvm.internal.Ref$ObjectRef, kotlin.jvm.internal.Ref$FloatRef, androidx.compose.foundation.gestures.ScrollingLogic, kotlin.jvm.internal.Ref$ObjectRef, long, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static MouseWheelScrollDelta sumOrNull(final BufferedChannel bufferedChannel) {
        MouseWheelScrollDelta mouseWheelScrollDelta = null;
        SequenceBuilderIterator it = SequencesKt__SequenceBuilderKt.iterator(new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new MouseWheelScrollingLogic$untilNull$1(new Function0() { // from class: androidx.compose.foundation.gestures.MouseWheelScrollingLogic$sumOrNull$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (MouseWheelScrollingLogic.MouseWheelScrollDelta) ChannelResult.m3459getOrNullimpl(Channel.this.mo3455tryReceivePtdJZtk());
            }
        }, null)).$block$inlined);
        while (it.hasNext()) {
            MouseWheelScrollDelta mouseWheelScrollDelta2 = (MouseWheelScrollDelta) it.next();
            if (mouseWheelScrollDelta != null) {
                mouseWheelScrollDelta2 = mouseWheelScrollDelta.plus(mouseWheelScrollDelta2);
            }
            mouseWheelScrollDelta = mouseWheelScrollDelta2;
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object userScroll(androidx.compose.foundation.gestures.ScrollingLogic r5, kotlin.jvm.functions.Function2 r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof androidx.compose.foundation.gestures.MouseWheelScrollingLogic$userScroll$1
            if (r0 == 0) goto L13
            r0 = r7
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic$userScroll$1 r0 = (androidx.compose.foundation.gestures.MouseWheelScrollingLogic$userScroll$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic$userScroll$1 r0 = new androidx.compose.foundation.gestures.MouseWheelScrollingLogic$userScroll$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic r4 = (androidx.compose.foundation.gestures.MouseWheelScrollingLogic) r4
            kotlin.ResultKt.throwOnFailure(r7)
            goto L52
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            r4.isScrolling = r3
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic$userScroll$2 r7 = new androidx.compose.foundation.gestures.MouseWheelScrollingLogic$userScroll$2
            r2 = 0
            r7.<init>(r5, r6, r2)
            r0.L$0 = r4
            r0.label = r3
            kotlinx.coroutines.SupervisorCoroutine r5 = new kotlinx.coroutines.SupervisorCoroutine
            kotlin.coroutines.CoroutineContext r6 = r0.getContext()
            r5.<init>(r6, r0)
            java.lang.Object r5 = kotlinx.coroutines.intrinsics.UndispatchedKt.startUndispatchedOrReturn(r5, r5, r7)
            if (r5 != r1) goto L52
            return r1
        L52:
            r5 = 0
            r4.isScrolling = r5
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.MouseWheelScrollingLogic.userScroll(androidx.compose.foundation.gestures.ScrollingLogic, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static final float access$dispatchMouseWheelScroll(MouseWheelScrollingLogic mouseWheelScrollingLogic, NestedScrollScope nestedScrollScope, float f) {
        ScrollingLogic scrollingLogic = mouseWheelScrollingLogic.scrollingLogic;
        long m85toOffsettuRUvjQ = scrollingLogic.m85toOffsettuRUvjQ(scrollingLogic.reverseIfNeeded(f));
        NestedScrollSource.Companion.getClass();
        int i = NestedScrollSource.UserInput;
        ScrollingLogic scrollingLogic2 = ((ScrollingLogic$nestedScrollScope$1) nestedScrollScope).this$0;
        return scrollingLogic.m84toFloatk4lQ0M(scrollingLogic.m83reverseIfNeededMKHz9U(ScrollingLogic.m80access$performScroll3eAAhYA(scrollingLogic2, scrollingLogic2.outerStateScope, m85toOffsettuRUvjQ, i)));
    }
}
