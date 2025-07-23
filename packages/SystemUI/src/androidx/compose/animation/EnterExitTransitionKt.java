package androidx.compose.animation;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector2D;
import androidx.compose.animation.core.DurationBasedAnimationSpec;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.graphics.TransformOrigin;
import androidx.compose.ui.graphics.TransformOriginKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class EnterExitTransitionKt {
    public static final SpringSpec DefaultOffsetAnimationSpec;
    public static final SpringSpec DefaultSizeAnimationSpec;
    public static final TwoWayConverter TransformOriginVectorConverter = VectorConvertersKt.TwoWayConverter(new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$TransformOriginVectorConverter$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            long j = ((TransformOrigin) obj).packedValue;
            return new AnimationVector2D(TransformOrigin.m503getPivotFractionXimpl(j), TransformOrigin.m504getPivotFractionYimpl(j));
        }
    }, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$TransformOriginVectorConverter$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            AnimationVector2D animationVector2D = (AnimationVector2D) obj;
            return TransformOrigin.m501boximpl(TransformOriginKt.TransformOrigin(animationVector2D.v1, animationVector2D.v2));
        }
    });
    public static final SpringSpec DefaultAlphaAndScaleSpring = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);

    static {
        IntOffset.Companion companion = IntOffset.Companion;
        DefaultOffsetAnimationSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, IntOffset.m847boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 1);
        IntSize.Companion companion2 = IntSize.Companion;
        DefaultSizeAnimationSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m859boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:124:0x03ca, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L171;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004c, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00d9, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L50;
     */
    /* JADX WARN: Removed duplicated region for block: B:115:0x03a2  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x03f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.Modifier createModifier(androidx.compose.animation.core.Transition r19, androidx.compose.animation.EnterTransition r20, androidx.compose.animation.ExitTransition r21, java.lang.String r22, androidx.compose.runtime.Composer r23, int r24) {
        /*
            Method dump skipped, instructions count: 1017
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.EnterExitTransitionKt.createModifier(androidx.compose.animation.core.Transition, androidx.compose.animation.EnterTransition, androidx.compose.animation.ExitTransition, java.lang.String, androidx.compose.runtime.Composer, int):androidx.compose.ui.Modifier");
    }

    public static final EnterTransition expandIn(FiniteAnimationSpec finiteAnimationSpec, BiasAlignment biasAlignment, boolean z, Function1 function1) {
        return new EnterTransitionImpl(new TransitionData(null, null, new ChangeSize(biasAlignment, function1, finiteAnimationSpec, z), null, false, null, 59, null));
    }

    public static EnterTransition expandVertically$default(TweenSpec tweenSpec, BiasAlignment.Vertical vertical, final Function1 function1, int i) {
        FiniteAnimationSpec finiteAnimationSpec = tweenSpec;
        if ((i & 1) != 0) {
            IntSize.Companion companion = IntSize.Companion;
            finiteAnimationSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m859boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
        }
        if ((i & 2) != 0) {
            Alignment.Companion.getClass();
            vertical = Alignment.Companion.Bottom;
        }
        boolean z = (i & 4) != 0;
        if ((i & 8) != 0) {
            function1 = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$expandVertically$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                    ((Number) obj).intValue();
                    return 0;
                }
            };
        }
        return expandIn(finiteAnimationSpec, toAlignment(vertical), z, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$expandVertically$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return IntSize.m859boximpl((((Number) Function1.this.mo779invoke(Integer.valueOf((int) (r0 & 4294967295L)))).intValue() & 4294967295L) | (((int) (((IntSize) obj).packedValue >> 32)) << 32));
            }
        });
    }

    public static final EnterTransition fadeIn(FiniteAnimationSpec finiteAnimationSpec) {
        return new EnterTransitionImpl(new TransitionData(new Fade(0.0f, finiteAnimationSpec), null, null, null, false, null, 62, null));
    }

    public static /* synthetic */ EnterTransition fadeIn$default(DurationBasedAnimationSpec durationBasedAnimationSpec, int i) {
        FiniteAnimationSpec finiteAnimationSpec = durationBasedAnimationSpec;
        if ((i & 1) != 0) {
            finiteAnimationSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);
        }
        return fadeIn(finiteAnimationSpec);
    }

    public static ExitTransition fadeOut$default(DurationBasedAnimationSpec durationBasedAnimationSpec, int i) {
        FiniteAnimationSpec finiteAnimationSpec = durationBasedAnimationSpec;
        if ((i & 1) != 0) {
            finiteAnimationSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);
        }
        return new ExitTransitionImpl(new TransitionData(new Fade(0.0f, finiteAnimationSpec), null, null, null, false, null, 62, null));
    }

    /* renamed from: scaleIn-L8ZKh-E$default, reason: not valid java name */
    public static EnterTransition m5scaleInL8ZKhE$default(TweenSpec tweenSpec, float f, int i) {
        FiniteAnimationSpec spring$default = (i & 1) != 0 ? AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5) : tweenSpec;
        float f2 = (i & 2) != 0 ? 0.0f : f;
        TransformOrigin.Companion.getClass();
        return new EnterTransitionImpl(new TransitionData(null, null, null, new Scale(f2, TransformOrigin.Center, spring$default, null), false, null, 55, null));
    }

    /* renamed from: scaleOut-L8ZKh-E$default, reason: not valid java name */
    public static ExitTransition m6scaleOutL8ZKhE$default(TweenSpec tweenSpec, float f, int i) {
        FiniteAnimationSpec spring$default = (i & 1) != 0 ? AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5) : tweenSpec;
        float f2 = (i & 2) != 0 ? 0.0f : f;
        TransformOrigin.Companion.getClass();
        return new ExitTransitionImpl(new TransitionData(null, null, null, new Scale(f2, TransformOrigin.Center, spring$default, null), false, null, 55, null));
    }

    public static final ExitTransition shrinkOut(FiniteAnimationSpec finiteAnimationSpec, BiasAlignment biasAlignment, boolean z, Function1 function1) {
        return new ExitTransitionImpl(new TransitionData(null, null, new ChangeSize(biasAlignment, function1, finiteAnimationSpec, z), null, false, null, 59, null));
    }

    public static ExitTransition shrinkOut$default() {
        IntSize.Companion companion = IntSize.Companion;
        SpringSpec spring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m859boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
        Alignment.Companion.getClass();
        return shrinkOut(spring$default, Alignment.Companion.BottomEnd, true, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$shrinkOut$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                long j = ((IntSize) obj).packedValue;
                long j2 = 0;
                return IntSize.m859boximpl((j2 & 4294967295L) | (j2 << 32));
            }
        });
    }

    public static ExitTransition shrinkVertically$default(TweenSpec tweenSpec, BiasAlignment.Vertical vertical, final Function1 function1, int i) {
        FiniteAnimationSpec finiteAnimationSpec = tweenSpec;
        if ((i & 1) != 0) {
            IntSize.Companion companion = IntSize.Companion;
            finiteAnimationSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m859boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
        }
        if ((i & 2) != 0) {
            Alignment.Companion.getClass();
            vertical = Alignment.Companion.Bottom;
        }
        boolean z = (i & 4) != 0;
        if ((i & 8) != 0) {
            function1 = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$shrinkVertically$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                    ((Number) obj).intValue();
                    return 0;
                }
            };
        }
        return shrinkOut(finiteAnimationSpec, toAlignment(vertical), z, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$shrinkVertically$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return IntSize.m859boximpl((((Number) Function1.this.mo779invoke(Integer.valueOf((int) (r0 & 4294967295L)))).intValue() & 4294967295L) | (((int) (((IntSize) obj).packedValue >> 32)) << 32));
            }
        });
    }

    public static final EnterTransition slideInVertically(final Function1 function1, FiniteAnimationSpec finiteAnimationSpec) {
        return new EnterTransitionImpl(new TransitionData(null, new Slide(new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$slideInVertically$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return IntOffset.m847boximpl((((Number) Function1.this.mo779invoke(Integer.valueOf((int) (((IntSize) obj).packedValue & 4294967295L)))).intValue() & 4294967295L) | (0 << 32));
            }
        }, finiteAnimationSpec), null, null, false, null, 61, null));
    }

    public static /* synthetic */ EnterTransition slideInVertically$default(TweenSpec tweenSpec, Function1 function1, int i) {
        FiniteAnimationSpec finiteAnimationSpec = tweenSpec;
        if ((i & 1) != 0) {
            IntOffset.Companion companion = IntOffset.Companion;
            finiteAnimationSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, IntOffset.m847boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 1);
        }
        if ((i & 2) != 0) {
            function1 = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$slideInVertically$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    return Integer.valueOf((-((Number) obj).intValue()) / 2);
                }
            };
        }
        return slideInVertically(function1, finiteAnimationSpec);
    }

    public static final ExitTransition slideOutVertically(TweenSpec tweenSpec, final Function1 function1) {
        return new ExitTransitionImpl(new TransitionData(null, new Slide(new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$slideOutVertically$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return IntOffset.m847boximpl((((Number) Function1.this.mo779invoke(Integer.valueOf((int) (((IntSize) obj).packedValue & 4294967295L)))).intValue() & 4294967295L) | (0 << 32));
            }
        }, tweenSpec), null, null, false, null, 61, null));
    }

    public static final BiasAlignment toAlignment(Alignment.Horizontal horizontal) {
        Alignment.Companion.getClass();
        return Intrinsics.areEqual(horizontal, Alignment.Companion.Start) ? Alignment.Companion.CenterStart : Intrinsics.areEqual(horizontal, Alignment.Companion.End) ? Alignment.Companion.CenterEnd : Alignment.Companion.Center;
    }

    public static final BiasAlignment toAlignment(Alignment.Vertical vertical) {
        Alignment.Companion.getClass();
        if (Intrinsics.areEqual(vertical, Alignment.Companion.Top)) {
            return Alignment.Companion.TopCenter;
        }
        if (Intrinsics.areEqual(vertical, Alignment.Companion.Bottom)) {
            return Alignment.Companion.BottomCenter;
        }
        return Alignment.Companion.Center;
    }
}
