package androidx.compose.animation.core;

import androidx.collection.MutableObjectList;
import androidx.compose.animation.core.SeekableTransitionState;
import androidx.compose.runtime.MonotonicFrameClockKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import androidx.navigation.NavBackStackEntry;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SeekableTransitionState<S> extends TransitionState<S> {
    public final Function1 animateOneFrameLambda;
    public Object composedTargetState;
    public CancellableContinuationImpl compositionContinuation;
    public final MutexImpl compositionContinuationMutex;
    public SeekingAnimationState currentAnimation;
    public final MutableState currentState$delegate;
    public float durationScale;
    public final Function1 firstFrameLambda;
    public final MutableFloatState fraction$delegate;
    public final MutableObjectList initialValueAnimations;
    public long lastFrameTimeNanos;
    public final MutatorMutex mutatorMutex;
    public final Function0 recalculateTotalDurationNanos;
    public final MutableState targetState$delegate;
    public long totalDurationNanos;
    public Transition transition;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    public static final AnimationVector1D ZeroVelocity = new AnimationVector1D(0.0f);
    public static final AnimationVector1D Target1 = new AnimationVector1D(1.0f);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SeekingAnimationState {
        public VectorizedFiniteAnimationSpec animationSpec;
        public long animationSpecDuration;
        public long durationNanos;
        public AnimationVector1D initialVelocity;
        public boolean isComplete;
        public long progressNanos;
        public final AnimationVector1D start = new AnimationVector1D(0.0f);
        public float value;

        public final String toString() {
            return "progress nanos: " + this.progressNanos + ", animationSpec: " + this.animationSpec + ", isComplete: " + this.isComplete + ", value: " + this.value + ", start: " + this.start + ", initialVelocity: " + this.initialVelocity + ", durationNanos: " + this.durationNanos + ", animationSpecDuration: " + this.animationSpecDuration;
        }
    }

    public SeekableTransitionState(S s) {
        super(null);
        this.targetState$delegate = SnapshotStateKt.mutableStateOf$default(s);
        this.currentState$delegate = SnapshotStateKt.mutableStateOf$default(s);
        this.composedTargetState = s;
        this.recalculateTotalDurationNanos = new Function0(this) { // from class: androidx.compose.animation.core.SeekableTransitionState$recalculateTotalDurationNanos$1
            final /* synthetic */ SeekableTransitionState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SeekableTransitionState<Object> seekableTransitionState = this.this$0;
                Transition transition = seekableTransitionState.transition;
                seekableTransitionState.totalDurationNanos = transition != null ? ((Number) transition.totalDurationNanos$delegate.getValue()).longValue() : 0L;
                return Unit.INSTANCE;
            }
        };
        this.fraction$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(0.0f);
        this.compositionContinuationMutex = MutexKt.Mutex$default();
        this.mutatorMutex = new MutatorMutex();
        this.lastFrameTimeNanos = Long.MIN_VALUE;
        this.initialValueAnimations = new MutableObjectList(0, 1, null);
        this.firstFrameLambda = new Function1(this) { // from class: androidx.compose.animation.core.SeekableTransitionState$firstFrameLambda$1
            final /* synthetic */ SeekableTransitionState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                this.this$0.lastFrameTimeNanos = ((Number) obj).longValue();
                return Unit.INSTANCE;
            }
        };
        this.animateOneFrameLambda = new Function1(this) { // from class: androidx.compose.animation.core.SeekableTransitionState$animateOneFrameLambda$1
            final /* synthetic */ SeekableTransitionState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                long longValue = ((Number) obj).longValue();
                SeekableTransitionState<Object> seekableTransitionState = this.this$0;
                long j = longValue - seekableTransitionState.lastFrameTimeNanos;
                seekableTransitionState.lastFrameTimeNanos = longValue;
                long roundToLong = MathKt__MathJVMKt.roundToLong(j / seekableTransitionState.durationScale);
                if (this.this$0.initialValueAnimations.isNotEmpty()) {
                    SeekableTransitionState<Object> seekableTransitionState2 = this.this$0;
                    MutableObjectList mutableObjectList = seekableTransitionState2.initialValueAnimations;
                    Object[] objArr = mutableObjectList.content;
                    int i = mutableObjectList._size;
                    int i2 = 0;
                    for (int i3 = 0; i3 < i; i3++) {
                        SeekableTransitionState.SeekingAnimationState seekingAnimationState = (SeekableTransitionState.SeekingAnimationState) objArr[i3];
                        SeekableTransitionState.access$recalculateAnimationValue(seekableTransitionState2, seekingAnimationState, roundToLong);
                        seekingAnimationState.isComplete = true;
                    }
                    Transition transition = this.this$0.transition;
                    if (transition != null) {
                        transition.updateInitialValues$animation_core();
                    }
                    MutableObjectList mutableObjectList2 = this.this$0.initialValueAnimations;
                    int i4 = mutableObjectList2._size;
                    Object[] objArr2 = mutableObjectList2.content;
                    IntRange until = RangesKt___RangesKt.until(0, i4);
                    int i5 = until.first;
                    int i6 = until.last;
                    if (i5 <= i6) {
                        while (true) {
                            objArr2[i5 - i2] = objArr2[i5];
                            if (((SeekableTransitionState.SeekingAnimationState) objArr2[i5]).isComplete) {
                                i2++;
                            }
                            if (i5 == i6) {
                                break;
                            }
                            i5++;
                        }
                    }
                    Arrays.fill(objArr2, i4 - i2, i4, (Object) null);
                    mutableObjectList2._size -= i2;
                }
                SeekableTransitionState<Object> seekableTransitionState3 = this.this$0;
                SeekableTransitionState.SeekingAnimationState seekingAnimationState2 = seekableTransitionState3.currentAnimation;
                if (seekingAnimationState2 != null) {
                    seekingAnimationState2.durationNanos = seekableTransitionState3.totalDurationNanos;
                    SeekableTransitionState.access$recalculateAnimationValue(seekableTransitionState3, seekingAnimationState2, roundToLong);
                    this.this$0.setFraction(seekingAnimationState2.value);
                    if (seekingAnimationState2.value == 1.0f) {
                        this.this$0.currentAnimation = null;
                    }
                    this.this$0.seekToFraction();
                }
                return Unit.INSTANCE;
            }
        };
    }

    public static final void access$moveAnimationToInitialState(SeekableTransitionState seekableTransitionState) {
        Transition transition = seekableTransitionState.transition;
        if (transition == null) {
            return;
        }
        SeekingAnimationState seekingAnimationState = seekableTransitionState.currentAnimation;
        if (seekingAnimationState == null) {
            if (seekableTransitionState.totalDurationNanos > 0) {
                SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) seekableTransitionState.fraction$delegate;
                if (snapshotMutableFloatStateImpl.getFloatValue() != 1.0f && !Intrinsics.areEqual(((SnapshotMutableStateImpl) seekableTransitionState.currentState$delegate).getValue(), ((SnapshotMutableStateImpl) seekableTransitionState.targetState$delegate).getValue())) {
                    SeekingAnimationState seekingAnimationState2 = new SeekingAnimationState();
                    seekingAnimationState2.value = snapshotMutableFloatStateImpl.getFloatValue();
                    long j = seekableTransitionState.totalDurationNanos;
                    seekingAnimationState2.durationNanos = j;
                    seekingAnimationState2.animationSpecDuration = MathKt__MathJVMKt.roundToLong((1.0d - snapshotMutableFloatStateImpl.getFloatValue()) * j);
                    seekingAnimationState2.start.set$animation_core(snapshotMutableFloatStateImpl.getFloatValue(), 0);
                    seekingAnimationState = seekingAnimationState2;
                }
            }
            seekingAnimationState = null;
        }
        if (seekingAnimationState != null) {
            seekingAnimationState.durationNanos = seekableTransitionState.totalDurationNanos;
            seekableTransitionState.initialValueAnimations.add(seekingAnimationState);
            transition.setInitialAnimations$animation_core(seekingAnimationState);
        }
        seekableTransitionState.currentAnimation = null;
    }

    public static final void access$recalculateAnimationValue(SeekableTransitionState seekableTransitionState, SeekingAnimationState seekingAnimationState, long j) {
        seekableTransitionState.getClass();
        long j2 = seekingAnimationState.progressNanos + j;
        seekingAnimationState.progressNanos = j2;
        long j3 = seekingAnimationState.animationSpecDuration;
        if (j2 >= j3) {
            seekingAnimationState.value = 1.0f;
            return;
        }
        VectorizedFiniteAnimationSpec vectorizedFiniteAnimationSpec = seekingAnimationState.animationSpec;
        if (vectorizedFiniteAnimationSpec == null) {
            float f = j2 / j3;
            seekingAnimationState.value = (f * 1.0f) + ((1 - f) * seekingAnimationState.start.get$animation_core(0));
            return;
        }
        AnimationVector1D animationVector1D = seekingAnimationState.initialVelocity;
        if (animationVector1D == null) {
            animationVector1D = ZeroVelocity;
        }
        seekingAnimationState.value = RangesKt___RangesKt.coerceIn(((AnimationVector1D) vectorizedFiniteAnimationSpec.getValueFromNanos(j2, seekingAnimationState.start, Target1, animationVector1D)).get$animation_core(0), 0.0f, 1.0f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0091, code lost:
    
        if (androidx.compose.runtime.MonotonicFrameClockKt.getMonotonicFrameClock(r0.getContext()).withFrameNanos(r10, r0) == r1) goto L46;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$runAnimations(androidx.compose.animation.core.SeekableTransitionState r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            r9.getClass()
            boolean r0 = r10 instanceof androidx.compose.animation.core.SeekableTransitionState$runAnimations$1
            if (r0 == 0) goto L16
            r0 = r10
            androidx.compose.animation.core.SeekableTransitionState$runAnimations$1 r0 = (androidx.compose.animation.core.SeekableTransitionState$runAnimations$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.compose.animation.core.SeekableTransitionState$runAnimations$1 r0 = new androidx.compose.animation.core.SeekableTransitionState$runAnimations$1
            r0.<init>(r9, r10)
        L1b:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            r5 = -9223372036854775808
            if (r2 == 0) goto L3c
            if (r2 == r4) goto L34
            if (r2 != r3) goto L2c
            goto L34
        L2c:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L34:
            java.lang.Object r9 = r0.L$0
            androidx.compose.animation.core.SeekableTransitionState r9 = (androidx.compose.animation.core.SeekableTransitionState) r9
            kotlin.ResultKt.throwOnFailure(r10)
            goto L94
        L3c:
            kotlin.ResultKt.throwOnFailure(r10)
            androidx.collection.MutableObjectList r10 = r9.initialValueAnimations
            boolean r2 = r10.isEmpty()
            if (r2 == 0) goto L4e
            androidx.compose.animation.core.SeekableTransitionState$SeekingAnimationState r2 = r9.currentAnimation
            if (r2 != 0) goto L4e
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L4e:
            kotlin.coroutines.CoroutineContext r2 = r0.getContext()
            float r2 = androidx.compose.animation.core.SuspendAnimationKt.getDurationScale(r2)
            r7 = 0
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 != 0) goto L79
            androidx.compose.animation.core.Transition r0 = r9.transition
            if (r0 == 0) goto L62
            r0.clearInitialAnimations$animation_core()
        L62:
            r10.clear()
            androidx.compose.animation.core.SeekableTransitionState$SeekingAnimationState r10 = r9.currentAnimation
            if (r10 == 0) goto L74
            r10 = 0
            r9.currentAnimation = r10
            r10 = 1065353216(0x3f800000, float:1.0)
            r9.setFraction(r10)
            r9.seekToFraction()
        L74:
            r9.lastFrameTimeNanos = r5
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L79:
            long r7 = r9.lastFrameTimeNanos
            int r10 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r10 != 0) goto L94
            kotlin.jvm.functions.Function1 r10 = r9.firstFrameLambda
            r0.L$0 = r9
            r0.label = r4
            kotlin.coroutines.CoroutineContext r2 = r0.getContext()
            androidx.compose.runtime.MonotonicFrameClock r2 = androidx.compose.runtime.MonotonicFrameClockKt.getMonotonicFrameClock(r2)
            java.lang.Object r10 = r2.withFrameNanos(r10, r0)
            if (r10 != r1) goto L94
            goto Lb0
        L94:
            androidx.collection.MutableObjectList r10 = r9.initialValueAnimations
            boolean r10 = r10.isNotEmpty()
            if (r10 != 0) goto La6
            androidx.compose.animation.core.SeekableTransitionState$SeekingAnimationState r10 = r9.currentAnimation
            if (r10 == 0) goto La1
            goto La6
        La1:
            r9.lastFrameTimeNanos = r5
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        La6:
            r0.L$0 = r9
            r0.label = r3
            java.lang.Object r10 = r9.animateOneFrame(r0)
            if (r10 != r1) goto L94
        Lb0:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.SeekableTransitionState.access$runAnimations(androidx.compose.animation.core.SeekableTransitionState, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x005e, code lost:
    
        if (r2.lock(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$waitForComposition(androidx.compose.animation.core.SeekableTransitionState r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r6.getClass()
            boolean r0 = r7 instanceof androidx.compose.animation.core.SeekableTransitionState$waitForComposition$1
            if (r0 == 0) goto L16
            r0 = r7
            androidx.compose.animation.core.SeekableTransitionState$waitForComposition$1 r0 = (androidx.compose.animation.core.SeekableTransitionState$waitForComposition$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.compose.animation.core.SeekableTransitionState$waitForComposition$1 r0 = new androidx.compose.animation.core.SeekableTransitionState$waitForComposition$1
            r0.<init>(r6, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L47
            if (r2 == r4) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r6 = r0.L$1
            java.lang.Object r0 = r0.L$0
            androidx.compose.animation.core.SeekableTransitionState r0 = (androidx.compose.animation.core.SeekableTransitionState) r0
            kotlin.ResultKt.throwOnFailure(r7)
            goto L86
        L33:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3b:
            java.lang.Object r6 = r0.L$1
            java.lang.Object r2 = r0.L$0
            androidx.compose.animation.core.SeekableTransitionState r2 = (androidx.compose.animation.core.SeekableTransitionState) r2
            kotlin.ResultKt.throwOnFailure(r7)
            r7 = r6
            r6 = r2
            goto L61
        L47:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.compose.runtime.MutableState r7 = r6.targetState$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r7 = (androidx.compose.runtime.SnapshotMutableStateImpl) r7
            java.lang.Object r7 = r7.getValue()
            kotlinx.coroutines.sync.MutexImpl r2 = r6.compositionContinuationMutex
            r0.L$0 = r6
            r0.L$1 = r7
            r0.label = r4
            java.lang.Object r2 = r2.lock(r0)
            if (r2 != r1) goto L61
            goto L81
        L61:
            r0.L$0 = r6
            r0.L$1 = r7
            r0.label = r3
            kotlinx.coroutines.CancellableContinuationImpl r2 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)
            r2.<init>(r0, r4)
            r2.initCancellability()
            r6.compositionContinuation = r2
            kotlinx.coroutines.sync.MutexImpl r0 = r6.compositionContinuationMutex
            r3 = 0
            r0.unlock(r3)
            java.lang.Object r0 = r2.getResult()
            if (r0 != r1) goto L82
        L81:
            return r1
        L82:
            r5 = r0
            r0 = r6
            r6 = r7
            r7 = r5
        L86:
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r6)
            if (r6 == 0) goto L8f
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L8f:
            r6 = -9223372036854775808
            r0.lastFrameTimeNanos = r6
            java.util.concurrent.CancellationException r6 = new java.util.concurrent.CancellationException
            java.lang.String r7 = "targetState while waiting for composition"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.SeekableTransitionState.access$waitForComposition(androidx.compose.animation.core.SeekableTransitionState, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$waitForCompositionAfterTargetStateChange(androidx.compose.animation.core.SeekableTransitionState r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r7.getClass()
            boolean r0 = r8 instanceof androidx.compose.animation.core.SeekableTransitionState$waitForCompositionAfterTargetStateChange$1
            if (r0 == 0) goto L16
            r0 = r8
            androidx.compose.animation.core.SeekableTransitionState$waitForCompositionAfterTargetStateChange$1 r0 = (androidx.compose.animation.core.SeekableTransitionState$waitForCompositionAfterTargetStateChange$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.compose.animation.core.SeekableTransitionState$waitForCompositionAfterTargetStateChange$1 r0 = new androidx.compose.animation.core.SeekableTransitionState$waitForCompositionAfterTargetStateChange$1
            r0.<init>(r7, r8)
        L1b:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L45
            if (r2 == r4) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r7 = r0.L$1
            java.lang.Object r0 = r0.L$0
            androidx.compose.animation.core.SeekableTransitionState r0 = (androidx.compose.animation.core.SeekableTransitionState) r0
            kotlin.ResultKt.throwOnFailure(r8)
            goto L8f
        L33:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3b:
            java.lang.Object r7 = r0.L$1
            java.lang.Object r2 = r0.L$0
            androidx.compose.animation.core.SeekableTransitionState r2 = (androidx.compose.animation.core.SeekableTransitionState) r2
            kotlin.ResultKt.throwOnFailure(r8)
            goto L61
        L45:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.compose.runtime.MutableState r8 = r7.targetState$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r8 = (androidx.compose.runtime.SnapshotMutableStateImpl) r8
            java.lang.Object r8 = r8.getValue()
            kotlinx.coroutines.sync.MutexImpl r2 = r7.compositionContinuationMutex
            r0.L$0 = r7
            r0.L$1 = r8
            r0.label = r4
            java.lang.Object r2 = r2.lock(r0)
            if (r2 != r1) goto L5f
            goto L8d
        L5f:
            r2 = r7
            r7 = r8
        L61:
            java.lang.Object r8 = r2.composedTargetState
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r8)
            kotlinx.coroutines.sync.MutexImpl r5 = r2.compositionContinuationMutex
            r6 = 0
            if (r8 == 0) goto L70
            r5.unlock(r6)
            goto L95
        L70:
            r0.L$0 = r2
            r0.L$1 = r7
            r0.label = r3
            kotlinx.coroutines.CancellableContinuationImpl r8 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)
            r8.<init>(r0, r4)
            r8.initCancellability()
            r2.compositionContinuation = r8
            r5.unlock(r6)
            java.lang.Object r8 = r8.getResult()
            if (r8 != r1) goto L8e
        L8d:
            return r1
        L8e:
            r0 = r2
        L8f:
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r7)
            if (r1 == 0) goto L98
        L95:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L98:
            r1 = -9223372036854775808
            r0.lastFrameTimeNanos = r1
            java.util.concurrent.CancellationException r0 = new java.util.concurrent.CancellationException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "snapTo() was canceled because state was changed to "
            r1.<init>(r2)
            r1.append(r8)
            java.lang.String r8 = " instead of "
            r1.append(r8)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.<init>(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.SeekableTransitionState.access$waitForCompositionAfterTargetStateChange(androidx.compose.animation.core.SeekableTransitionState, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static Object animateTo$default(SeekableTransitionState seekableTransitionState, Object obj, Continuation continuation) {
        Transition transition = seekableTransitionState.transition;
        if (transition == null) {
            return Unit.INSTANCE;
        }
        Object mutate$default = MutatorMutex.mutate$default(seekableTransitionState.mutatorMutex, new SeekableTransitionState$animateTo$2(transition, seekableTransitionState, obj, null, null), continuation);
        return mutate$default == CoroutineSingletons.COROUTINE_SUSPENDED ? mutate$default : Unit.INSTANCE;
    }

    public final Object animateOneFrame(ContinuationImpl continuationImpl) {
        float durationScale = SuspendAnimationKt.getDurationScale(continuationImpl.getContext());
        if (durationScale > 0.0f) {
            this.durationScale = durationScale;
            Object withFrameNanos = MonotonicFrameClockKt.getMonotonicFrameClock(continuationImpl.getContext()).withFrameNanos(this.animateOneFrameLambda, continuationImpl);
            return withFrameNanos == CoroutineSingletons.COROUTINE_SUSPENDED ? withFrameNanos : Unit.INSTANCE;
        }
        Transition transition = this.transition;
        if (transition != null) {
            transition.clearInitialAnimations$animation_core();
        }
        this.initialValueAnimations.clear();
        if (this.currentAnimation != null) {
            this.currentAnimation = null;
            setFraction(1.0f);
            seekToFraction();
        }
        return Unit.INSTANCE;
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final Object getCurrentState() {
        return ((SnapshotMutableStateImpl) this.currentState$delegate).getValue();
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final Object getTargetState() {
        return ((SnapshotMutableStateImpl) this.targetState$delegate).getValue();
    }

    public final Object seekTo(float f, NavBackStackEntry navBackStackEntry, Continuation continuation) {
        if (0.0f > f || f > 1.0f) {
            PreconditionsKt.throwIllegalArgumentException("Expecting fraction between 0 and 1. Got " + f);
        }
        Transition transition = this.transition;
        if (transition == null) {
            return Unit.INSTANCE;
        }
        Object mutate$default = MutatorMutex.mutate$default(this.mutatorMutex, new SeekableTransitionState$seekTo$3(navBackStackEntry, ((SnapshotMutableStateImpl) this.targetState$delegate).getValue(), this, transition, f, null), continuation);
        return mutate$default == CoroutineSingletons.COROUTINE_SUSPENDED ? mutate$default : Unit.INSTANCE;
    }

    public final void seekToFraction() {
        Transition transition = this.transition;
        if (transition == null) {
            return;
        }
        transition.seekAnimations$animation_core(MathKt__MathJVMKt.roundToLong(((SnapshotMutableFloatStateImpl) this.fraction$delegate).getFloatValue() * ((Number) transition.totalDurationNanos$delegate.getValue()).longValue()));
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final void setCurrentState$animation_core(Object obj) {
        ((SnapshotMutableStateImpl) this.currentState$delegate).setValue(obj);
    }

    public final void setFraction(float f) {
        ((SnapshotMutableFloatStateImpl) this.fraction$delegate).setFloatValue(f);
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final void transitionConfigured$animation_core(Transition transition) {
        Transition transition2 = this.transition;
        if (transition2 != null && !transition.equals(transition2)) {
            PreconditionsKt.throwIllegalStateException("An instance of SeekableTransitionState has been used in different Transitions. Previous instance: " + this.transition + ", new instance: " + transition);
        }
        this.transition = transition;
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final void transitionRemoved$animation_core() {
        this.transition = null;
        ((SnapshotStateObserver) TransitionKt.SeekableStateObserver$delegate.getValue()).clear(this);
    }
}
