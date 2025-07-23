package androidx.compose.animation.core;

import androidx.compose.animation.core.SeekableTransitionState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TransitionKt {
    public static final Function1 SeekableTransitionStateTotalDurationChanged = new Function1() { // from class: androidx.compose.animation.core.TransitionKt$SeekableTransitionStateTotalDurationChanged$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            SeekableTransitionState seekableTransitionState = (SeekableTransitionState) obj;
            long j = seekableTransitionState.totalDurationNanos;
            ((SnapshotStateObserver) TransitionKt.SeekableStateObserver$delegate.getValue()).observeReads(seekableTransitionState, TransitionKt.SeekableTransitionStateTotalDurationChanged, seekableTransitionState.recalculateTotalDurationNanos);
            long j2 = seekableTransitionState.totalDurationNanos;
            if (j != j2) {
                SeekableTransitionState.SeekingAnimationState seekingAnimationState = seekableTransitionState.currentAnimation;
                if (seekingAnimationState != null) {
                    seekingAnimationState.durationNanos = j2;
                    if (seekingAnimationState.animationSpec == null) {
                        seekingAnimationState.animationSpecDuration = MathKt__MathJVMKt.roundToLong((1.0d - seekingAnimationState.start.get$animation_core(0)) * seekableTransitionState.totalDurationNanos);
                    }
                } else if (j2 != 0) {
                    seekableTransitionState.seekToFraction();
                }
            }
            return Unit.INSTANCE;
        }
    };
    public static final Lazy SeekableStateObserver$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: androidx.compose.animation.core.TransitionKt$SeekableStateObserver$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            SnapshotStateObserver snapshotStateObserver = new SnapshotStateObserver(new Function1() { // from class: androidx.compose.animation.core.TransitionKt$SeekableStateObserver$2.1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ((Function0) obj).invoke();
                    return Unit.INSTANCE;
                }
            });
            Snapshot.Companion companion = Snapshot.Companion;
            Function2 function2 = snapshotStateObserver.applyObserver;
            companion.getClass();
            snapshotStateObserver.applyUnsubscribe = Snapshot.Companion.registerApplyObserver(function2);
            return snapshotStateObserver;
        }
    });

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0033, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x006f, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.animation.core.Transition createChildTransitionInternal(final androidx.compose.animation.core.Transition r10, androidx.compose.animation.EnterExitState r11, androidx.compose.animation.EnterExitState r12, androidx.compose.runtime.Composer r13, int r14) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.animation.core.createChildTransitionInternal (Transition.kt:1774)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            r0 = r14 & 14
            r0 = r0 ^ 6
            r1 = 1
            r2 = 0
            r3 = 4
            if (r0 <= r3) goto L1d
            r4 = r13
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            boolean r4 = r4.changed(r10)
            if (r4 != 0) goto L21
        L1d:
            r4 = r14 & 6
            if (r4 != r3) goto L23
        L21:
            r4 = r1
            goto L24
        L23:
            r4 = r2
        L24:
            androidx.compose.runtime.ComposerImpl r13 = (androidx.compose.runtime.ComposerImpl) r13
            java.lang.Object r5 = r13.rememberedValue()
            androidx.compose.runtime.Composer$Companion r6 = androidx.compose.runtime.Composer.Companion
            if (r4 != 0) goto L35
            r6.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r4 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r4) goto L4f
        L35:
            androidx.compose.animation.core.Transition r5 = new androidx.compose.animation.core.Transition
            androidx.compose.animation.core.MutableTransitionState r4 = new androidx.compose.animation.core.MutableTransitionState
            r4.<init>(r11)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = r10.label
            java.lang.String r9 = " > EnterExitTransition"
            java.lang.String r7 = androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0.m(r7, r8, r9)
            r5.<init>(r4, r10, r7)
            r13.updateRememberedValue(r5)
        L4f:
            androidx.compose.animation.core.Transition r5 = (androidx.compose.animation.core.Transition) r5
            if (r0 <= r3) goto L59
            boolean r0 = r13.changed(r10)
            if (r0 != 0) goto L5f
        L59:
            r14 = r14 & 6
            if (r14 != r3) goto L5e
            goto L5f
        L5e:
            r1 = r2
        L5f:
            boolean r14 = r13.changed(r5)
            r14 = r14 | r1
            java.lang.Object r0 = r13.rememberedValue()
            if (r14 != 0) goto L71
            r6.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r14 = androidx.compose.runtime.Composer.Companion.Empty
            if (r0 != r14) goto L79
        L71:
            androidx.compose.animation.core.TransitionKt$createChildTransitionInternal$1$1 r0 = new androidx.compose.animation.core.TransitionKt$createChildTransitionInternal$1$1
            r0.<init>()
            r13.updateRememberedValue(r0)
        L79:
            kotlin.jvm.functions.Function1 r0 = (kotlin.jvm.functions.Function1) r0
            androidx.compose.runtime.EffectsKt.DisposableEffect(r5, r0, r13)
            boolean r10 = r10.isSeeking()
            if (r10 == 0) goto L88
            r5.seek(r11, r12)
            goto L94
        L88:
            r5.updateTarget$animation_core(r12)
            androidx.compose.runtime.MutableState r10 = r5.isSeeking$delegate
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            androidx.compose.runtime.SnapshotMutableStateImpl r10 = (androidx.compose.runtime.SnapshotMutableStateImpl) r10
            r10.setValue(r11)
        L94:
            boolean r10 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r10 == 0) goto L9d
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L9d:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.TransitionKt.createChildTransitionInternal(androidx.compose.animation.core.Transition, androidx.compose.animation.EnterExitState, androidx.compose.animation.EnterExitState, androidx.compose.runtime.Composer, int):androidx.compose.animation.core.Transition");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0039, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0063, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.animation.core.Transition.DeferredAnimation createDeferredAnimation(final androidx.compose.animation.core.Transition r6, androidx.compose.animation.core.TwoWayConverter r7, java.lang.String r8, androidx.compose.runtime.Composer r9, int r10, int r11) {
        /*
            r11 = r11 & 2
            if (r11 == 0) goto L6
            java.lang.String r8 = "DeferredAnimation"
        L6:
            boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r11 == 0) goto L11
            java.lang.String r11 = "androidx.compose.animation.core.createDeferredAnimation (Transition.kt:1732)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r11)
        L11:
            r11 = r10 & 14
            r11 = r11 ^ 6
            r0 = 1
            r1 = 0
            r2 = 4
            if (r11 <= r2) goto L23
            r3 = r9
            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
            boolean r3 = r3.changed(r6)
            if (r3 != 0) goto L27
        L23:
            r3 = r10 & 6
            if (r3 != r2) goto L29
        L27:
            r3 = r0
            goto L2a
        L29:
            r3 = r1
        L2a:
            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
            java.lang.Object r4 = r9.rememberedValue()
            androidx.compose.runtime.Composer$Companion r5 = androidx.compose.runtime.Composer.Companion
            if (r3 != 0) goto L3b
            r5.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r4 != r3) goto L43
        L3b:
            androidx.compose.animation.core.Transition$DeferredAnimation r4 = new androidx.compose.animation.core.Transition$DeferredAnimation
            r4.<init>(r7, r8)
            r9.updateRememberedValue(r4)
        L43:
            androidx.compose.animation.core.Transition$DeferredAnimation r4 = (androidx.compose.animation.core.Transition.DeferredAnimation) r4
            if (r11 <= r2) goto L4d
            boolean r7 = r9.changed(r6)
            if (r7 != 0) goto L53
        L4d:
            r7 = r10 & 6
            if (r7 != r2) goto L52
            goto L53
        L52:
            r0 = r1
        L53:
            boolean r7 = r9.changedInstance(r4)
            r7 = r7 | r0
            java.lang.Object r8 = r9.rememberedValue()
            if (r7 != 0) goto L65
            r5.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r7 = androidx.compose.runtime.Composer.Companion.Empty
            if (r8 != r7) goto L6d
        L65:
            androidx.compose.animation.core.TransitionKt$createDeferredAnimation$1$1 r8 = new androidx.compose.animation.core.TransitionKt$createDeferredAnimation$1$1
            r8.<init>()
            r9.updateRememberedValue(r8)
        L6d:
            kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
            androidx.compose.runtime.EffectsKt.DisposableEffect(r4, r8, r9)
            boolean r6 = r6.isSeeking()
            if (r6 == 0) goto Lb3
            androidx.compose.runtime.MutableState r6 = r4.data$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r6 = (androidx.compose.runtime.SnapshotMutableStateImpl) r6
            java.lang.Object r6 = r6.getValue()
            androidx.compose.animation.core.Transition$DeferredAnimation$DeferredAnimationData r6 = (androidx.compose.animation.core.Transition.DeferredAnimation.DeferredAnimationData) r6
            if (r6 == 0) goto Lb3
            kotlin.jvm.functions.Function1 r7 = r6.targetValueByState
            androidx.compose.animation.core.Transition r8 = androidx.compose.animation.core.Transition.this
            androidx.compose.animation.core.Transition$Segment r9 = r8.getSegment()
            java.lang.Object r9 = r9.getInitialState()
            java.lang.Object r7 = r7.mo779invoke(r9)
            kotlin.jvm.functions.Function1 r9 = r6.targetValueByState
            androidx.compose.animation.core.Transition$Segment r10 = r8.getSegment()
            java.lang.Object r10 = r10.getTargetState()
            java.lang.Object r9 = r9.mo779invoke(r10)
            kotlin.jvm.functions.Function1 r10 = r6.transitionSpec
            androidx.compose.animation.core.Transition$Segment r8 = r8.getSegment()
            java.lang.Object r8 = r10.mo779invoke(r8)
            androidx.compose.animation.core.FiniteAnimationSpec r8 = (androidx.compose.animation.core.FiniteAnimationSpec) r8
            androidx.compose.animation.core.Transition$TransitionAnimationState r6 = r6.animation
            r6.updateInitialAndTargetValue$animation_core(r7, r9, r8)
        Lb3:
            boolean r6 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r6 == 0) goto Lbc
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lbc:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.TransitionKt.createDeferredAnimation(androidx.compose.animation.core.Transition, androidx.compose.animation.core.TwoWayConverter, java.lang.String, androidx.compose.runtime.Composer, int, int):androidx.compose.animation.core.Transition$DeferredAnimation");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x003a, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0088, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.animation.core.Transition.TransitionAnimationState createTransitionAnimation(final androidx.compose.animation.core.Transition r14, java.lang.Object r15, java.lang.Object r16, androidx.compose.animation.core.FiniteAnimationSpec r17, androidx.compose.animation.core.TwoWayConverter r18, java.lang.String r19, androidx.compose.runtime.Composer r20, int r21) {
        /*
            r6 = r16
            r7 = r17
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lf
            java.lang.String r0 = "androidx.compose.animation.core.createTransitionAnimation (Transition.kt:1849)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lf:
            r0 = r21 & 14
            r8 = r0 ^ 6
            r9 = 1
            r10 = 0
            r11 = 4
            if (r8 <= r11) goto L22
            r0 = r20
            androidx.compose.runtime.ComposerImpl r0 = (androidx.compose.runtime.ComposerImpl) r0
            boolean r0 = r0.changed(r14)
            if (r0 != 0) goto L26
        L22:
            r0 = r21 & 6
            if (r0 != r11) goto L28
        L26:
            r0 = r9
            goto L29
        L28:
            r0 = r10
        L29:
            r12 = r20
            androidx.compose.runtime.ComposerImpl r12 = (androidx.compose.runtime.ComposerImpl) r12
            java.lang.Object r2 = r12.rememberedValue()
            androidx.compose.runtime.Composer$Companion r13 = androidx.compose.runtime.Composer.Companion
            if (r0 != 0) goto L3c
            r13.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r0) goto L5b
        L3c:
            androidx.compose.animation.core.Transition$TransitionAnimationState r0 = new androidx.compose.animation.core.Transition$TransitionAnimationState
            r2 = r18
            androidx.compose.animation.core.TwoWayConverterImpl r2 = (androidx.compose.animation.core.TwoWayConverterImpl) r2
            kotlin.jvm.functions.Function1 r2 = r2.convertToVector
            java.lang.Object r2 = r2.mo779invoke(r6)
            r3 = r2
            androidx.compose.animation.core.AnimationVector r3 = (androidx.compose.animation.core.AnimationVector) r3
            r3.reset$animation_core()
            r1 = r14
            r2 = r15
            r4 = r18
            r5 = r19
            r0.<init>(r2, r3, r4, r5)
            r12.updateRememberedValue(r0)
            r2 = r0
        L5b:
            androidx.compose.animation.core.Transition$TransitionAnimationState r2 = (androidx.compose.animation.core.Transition.TransitionAnimationState) r2
            boolean r0 = r14.isSeeking()
            if (r0 == 0) goto L67
            r2.updateInitialAndTargetValue$animation_core(r15, r6, r7)
            goto L6a
        L67:
            r2.updateTargetValue$animation_core(r6, r7)
        L6a:
            if (r8 <= r11) goto L72
            boolean r0 = r12.changed(r14)
            if (r0 != 0) goto L78
        L72:
            r0 = r21 & 6
            if (r0 != r11) goto L77
            goto L78
        L77:
            r9 = r10
        L78:
            boolean r0 = r12.changed(r2)
            r0 = r0 | r9
            java.lang.Object r3 = r12.rememberedValue()
            if (r0 != 0) goto L8a
            r13.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r0) goto L92
        L8a:
            androidx.compose.animation.core.TransitionKt$createTransitionAnimation$1$1 r3 = new androidx.compose.animation.core.TransitionKt$createTransitionAnimation$1$1
            r3.<init>()
            r12.updateRememberedValue(r3)
        L92:
            kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3
            androidx.compose.runtime.EffectsKt.DisposableEffect(r2, r3, r12)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto La0
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        La0:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.TransitionKt.createTransitionAnimation(androidx.compose.animation.core.Transition, java.lang.Object, java.lang.Object, androidx.compose.animation.core.FiniteAnimationSpec, androidx.compose.animation.core.TwoWayConverter, java.lang.String, androidx.compose.runtime.Composer, int):androidx.compose.animation.core.Transition$TransitionAnimationState");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0033, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0075, code lost:
    
        if (r10 == androidx.compose.runtime.Composer.Companion.Empty) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a8, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.animation.core.Transition rememberTransition(androidx.compose.animation.core.TransitionState r7, java.lang.String r8, androidx.compose.runtime.Composer r9, int r10) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.animation.core.rememberTransition (Transition.kt:799)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            r0 = r10 & 14
            r0 = r0 ^ 6
            r1 = 1
            r2 = 4
            r3 = 0
            if (r0 <= r2) goto L1d
            r4 = r9
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            boolean r4 = r4.changed(r7)
            if (r4 != 0) goto L21
        L1d:
            r4 = r10 & 6
            if (r4 != r2) goto L23
        L21:
            r4 = r1
            goto L24
        L23:
            r4 = r3
        L24:
            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
            java.lang.Object r5 = r9.rememberedValue()
            androidx.compose.runtime.Composer$Companion r6 = androidx.compose.runtime.Composer.Companion
            if (r4 != 0) goto L35
            r6.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r4 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r4) goto L3d
        L35:
            androidx.compose.animation.core.Transition r5 = new androidx.compose.animation.core.Transition
            r5.<init>(r7, r8)
            r9.updateRememberedValue(r5)
        L3d:
            androidx.compose.animation.core.Transition r5 = (androidx.compose.animation.core.Transition) r5
            boolean r8 = r7 instanceof androidx.compose.animation.core.SeekableTransitionState
            if (r8 == 0) goto L89
            r8 = 1030660644(0x3d6ea224, float:0.058260098)
            r9.startReplaceGroup(r8)
            r8 = r7
            androidx.compose.animation.core.SeekableTransitionState r8 = (androidx.compose.animation.core.SeekableTransitionState) r8
            androidx.compose.runtime.MutableState r4 = r8.currentState$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r4 = (androidx.compose.runtime.SnapshotMutableStateImpl) r4
            java.lang.Object r4 = r4.getValue()
            androidx.compose.runtime.MutableState r8 = r8.targetState$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r8 = (androidx.compose.runtime.SnapshotMutableStateImpl) r8
            java.lang.Object r8 = r8.getValue()
            if (r0 <= r2) goto L64
            boolean r0 = r9.changed(r7)
            if (r0 != 0) goto L6a
        L64:
            r10 = r10 & 6
            if (r10 != r2) goto L69
            goto L6a
        L69:
            r1 = r3
        L6a:
            java.lang.Object r10 = r9.rememberedValue()
            if (r1 != 0) goto L77
            r6.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r10 != r0) goto L80
        L77:
            androidx.compose.animation.core.TransitionKt$rememberTransition$1$1 r10 = new androidx.compose.animation.core.TransitionKt$rememberTransition$1$1
            r0 = 0
            r10.<init>(r7, r0)
            r9.updateRememberedValue(r10)
        L80:
            kotlin.jvm.functions.Function2 r10 = (kotlin.jvm.functions.Function2) r10
            androidx.compose.runtime.EffectsKt.LaunchedEffect(r4, r8, r10, r9)
            r9.end(r3)
            goto L99
        L89:
            r8 = 1031122203(0x3d75ad1b, float:0.05997954)
            r9.startReplaceGroup(r8)
            java.lang.Object r7 = r7.getTargetState()
            r5.animateTo$animation_core(r7, r9, r3)
            r9.end(r3)
        L99:
            boolean r7 = r9.changed(r5)
            java.lang.Object r8 = r9.rememberedValue()
            if (r7 != 0) goto Laa
            r6.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r7 = androidx.compose.runtime.Composer.Companion.Empty
            if (r8 != r7) goto Lb2
        Laa:
            androidx.compose.animation.core.TransitionKt$rememberTransition$2$1 r8 = new androidx.compose.animation.core.TransitionKt$rememberTransition$2$1
            r8.<init>()
            r9.updateRememberedValue(r8)
        Lb2:
            kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
            androidx.compose.runtime.EffectsKt.DisposableEffect(r5, r8, r9)
            boolean r7 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r7 == 0) goto Lc0
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lc0:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.TransitionKt.rememberTransition(androidx.compose.animation.core.TransitionState, java.lang.String, androidx.compose.runtime.Composer, int):androidx.compose.animation.core.Transition");
    }

    public static final Transition updateTransition(Object obj, String str, Composer composer, int i, int i2) {
        if ((i2 & 2) != 0) {
            str = null;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.updateTransition (Transition.kt:86)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = new Transition(obj, str);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final Transition transition = (Transition) rememberedValue;
        transition.animateTo$animation_core(obj, composerImpl, (i & 8) | 48 | (i & 14));
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new Function1() { // from class: androidx.compose.animation.core.TransitionKt$updateTransition$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    final Transition<Object> transition2 = transition;
                    return new DisposableEffectResult() { // from class: androidx.compose.animation.core.TransitionKt$updateTransition$1$1$invoke$$inlined$onDispose$1
                        @Override // androidx.compose.runtime.DisposableEffectResult
                        public final void dispose() {
                            Transition transition3 = Transition.this;
                            transition3.onTransitionEnd$animation_core();
                            transition3.transitionState.transitionRemoved$animation_core();
                        }
                    };
                }
            };
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        EffectsKt.DisposableEffect(transition, (Function1) rememberedValue2, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return transition;
    }
}
