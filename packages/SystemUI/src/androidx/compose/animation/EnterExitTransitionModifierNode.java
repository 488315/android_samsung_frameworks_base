package androidx.compose.animation;

import androidx.compose.animation.EnterExitTransitionModifierNode;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector2D;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.State;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.TransformOrigin;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class EnterExitTransitionModifierNode extends LayoutModifierNodeWithPassThroughIntrinsics {
    public Alignment currentAlignment;
    public EnterTransition enter;
    public ExitTransition exit;
    public GraphicsLayerBlockForEnterExit graphicsLayerBlock;
    public Function0 isEnabled;
    public long lookaheadSize = AnimationModifierKt.InvalidSize;
    public Transition.DeferredAnimation offsetAnimation;
    public Transition.DeferredAnimation sizeAnimation;
    public final Function1 sizeTransitionSpec;
    public Transition.DeferredAnimation slideAnimation;
    public final Function1 slideSpec;
    public Transition transition;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EnterExitState.values().length];
            try {
                iArr[EnterExitState.Visible.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EnterExitState.PreEnter.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[EnterExitState.PostExit.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public EnterExitTransitionModifierNode(Transition<EnterExitState> transition, Transition.DeferredAnimation<IntSize, AnimationVector2D> deferredAnimation, Transition.DeferredAnimation<IntOffset, AnimationVector2D> deferredAnimation2, Transition.DeferredAnimation<IntOffset, AnimationVector2D> deferredAnimation3, EnterTransition enterTransition, ExitTransition exitTransition, Function0 function0, GraphicsLayerBlockForEnterExit graphicsLayerBlockForEnterExit) {
        this.transition = transition;
        this.sizeAnimation = deferredAnimation;
        this.offsetAnimation = deferredAnimation2;
        this.slideAnimation = deferredAnimation3;
        this.enter = enterTransition;
        this.exit = exitTransition;
        this.isEnabled = function0;
        this.graphicsLayerBlock = graphicsLayerBlockForEnterExit;
        ConstraintsKt.Constraints$default(0, 0, 0, 0, 15);
        this.sizeTransitionSpec = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$sizeTransitionSpec$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Transition.Segment segment = (Transition.Segment) obj;
                EnterExitState enterExitState = EnterExitState.PreEnter;
                EnterExitState enterExitState2 = EnterExitState.Visible;
                Object obj2 = null;
                if (segment.isTransitioningTo(enterExitState, enterExitState2)) {
                    ChangeSize changeSize = EnterExitTransitionModifierNode.this.enter.getData$animation().changeSize;
                    if (changeSize != null) {
                        obj2 = changeSize.animationSpec;
                    }
                } else if (segment.isTransitioningTo(enterExitState2, EnterExitState.PostExit)) {
                    ChangeSize changeSize2 = EnterExitTransitionModifierNode.this.exit.getData$animation().changeSize;
                    if (changeSize2 != null) {
                        obj2 = changeSize2.animationSpec;
                    }
                } else {
                    obj2 = EnterExitTransitionKt.DefaultSizeAnimationSpec;
                }
                return obj2 == null ? EnterExitTransitionKt.DefaultSizeAnimationSpec : obj2;
            }
        };
        this.slideSpec = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$slideSpec$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                FiniteAnimationSpec finiteAnimationSpec;
                FiniteAnimationSpec finiteAnimationSpec2;
                Transition.Segment segment = (Transition.Segment) obj;
                EnterExitState enterExitState = EnterExitState.PreEnter;
                EnterExitState enterExitState2 = EnterExitState.Visible;
                if (segment.isTransitioningTo(enterExitState, enterExitState2)) {
                    Slide slide = EnterExitTransitionModifierNode.this.enter.getData$animation().slide;
                    return (slide == null || (finiteAnimationSpec2 = slide.animationSpec) == null) ? EnterExitTransitionKt.DefaultOffsetAnimationSpec : finiteAnimationSpec2;
                }
                if (!segment.isTransitioningTo(enterExitState2, EnterExitState.PostExit)) {
                    return EnterExitTransitionKt.DefaultOffsetAnimationSpec;
                }
                Slide slide2 = EnterExitTransitionModifierNode.this.exit.getData$animation().slide;
                return (slide2 == null || (finiteAnimationSpec = slide2.animationSpec) == null) ? EnterExitTransitionKt.DefaultOffsetAnimationSpec : finiteAnimationSpec;
            }
        };
    }

    public final Alignment getAlignment() {
        Alignment alignment;
        Alignment alignment2;
        if (this.transition.getSegment().isTransitioningTo(EnterExitState.PreEnter, EnterExitState.Visible)) {
            ChangeSize changeSize = this.enter.getData$animation().changeSize;
            if (changeSize != null && (alignment2 = changeSize.alignment) != null) {
                return alignment2;
            }
            ChangeSize changeSize2 = this.exit.getData$animation().changeSize;
            if (changeSize2 != null) {
                return changeSize2.alignment;
            }
            return null;
        }
        ChangeSize changeSize3 = this.exit.getData$animation().changeSize;
        if (changeSize3 != null && (alignment = changeSize3.alignment) != null) {
            return alignment;
        }
        ChangeSize changeSize4 = this.enter.getData$animation().changeSize;
        if (changeSize4 != null) {
            return changeSize4.alignment;
        }
        return null;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        final TransformOrigin m501boximpl;
        long j2;
        char c;
        long j3;
        MeasureResult layout$12;
        MeasureResult layout$13;
        if (this.transition.transitionState.getCurrentState() == ((SnapshotMutableStateImpl) this.transition.targetState$delegate).getValue()) {
            this.currentAlignment = null;
        } else if (this.currentAlignment == null) {
            Alignment alignment = getAlignment();
            if (alignment == null) {
                Alignment.Companion.getClass();
                alignment = Alignment.Companion.TopStart;
            }
            this.currentAlignment = alignment;
        }
        if (measureScope.isLookingAhead()) {
            final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
            long j4 = (mo608measureBRTryo0.width << 32) | (mo608measureBRTryo0.height & 4294967295L);
            IntSize.Companion companion = IntSize.Companion;
            this.lookaheadSize = j4;
            layout$13 = measureScope.layout$1((int) (j4 >> 32), (int) (j4 & 4294967295L), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ((Placeable.PlacementScope) obj).place(Placeable.this, 0, 0, 0.0f);
                    return Unit.INSTANCE;
                }
            });
            return layout$13;
        }
        if (!((Boolean) this.isEnabled.invoke()).booleanValue()) {
            final Placeable mo608measureBRTryo02 = measurable.mo608measureBRTryo0(j);
            layout$1 = measureScope.layout$1(mo608measureBRTryo02.width, mo608measureBRTryo02.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$3$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ((Placeable.PlacementScope) obj).place(Placeable.this, 0, 0, 0.0f);
                    return Unit.INSTANCE;
                }
            });
            return layout$1;
        }
        EnterExitTransitionKt$$ExternalSyntheticLambda0 enterExitTransitionKt$$ExternalSyntheticLambda0 = (EnterExitTransitionKt$$ExternalSyntheticLambda0) this.graphicsLayerBlock;
        enterExitTransitionKt$$ExternalSyntheticLambda0.getClass();
        TwoWayConverter twoWayConverter = EnterExitTransitionKt.TransformOriginVectorConverter;
        final EnterTransition enterTransition = enterExitTransitionKt$$ExternalSyntheticLambda0.f$3;
        Transition.DeferredAnimation deferredAnimation = enterExitTransitionKt$$ExternalSyntheticLambda0.f$0;
        final ExitTransition exitTransition = enterExitTransitionKt$$ExternalSyntheticLambda0.f$4;
        final Transition.DeferredAnimation.DeferredAnimationData animate = deferredAnimation != null ? deferredAnimation.animate(new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$createGraphicsLayerBlock$1$1$alpha$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                FiniteAnimationSpec finiteAnimationSpec;
                FiniteAnimationSpec finiteAnimationSpec2;
                Transition.Segment segment = (Transition.Segment) obj;
                EnterExitState enterExitState = EnterExitState.PreEnter;
                EnterExitState enterExitState2 = EnterExitState.Visible;
                if (segment.isTransitioningTo(enterExitState, enterExitState2)) {
                    Fade fade = EnterTransition.this.getData$animation().fade;
                    return (fade == null || (finiteAnimationSpec2 = fade.animationSpec) == null) ? EnterExitTransitionKt.DefaultAlphaAndScaleSpring : finiteAnimationSpec2;
                }
                if (!segment.isTransitioningTo(enterExitState2, EnterExitState.PostExit)) {
                    return EnterExitTransitionKt.DefaultAlphaAndScaleSpring;
                }
                Fade fade2 = exitTransition.getData$animation().fade;
                return (fade2 == null || (finiteAnimationSpec = fade2.animationSpec) == null) ? EnterExitTransitionKt.DefaultAlphaAndScaleSpring : finiteAnimationSpec;
            }
        }, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$createGraphicsLayerBlock$1$1$alpha$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[EnterExitState.values().length];
                    try {
                        iArr[EnterExitState.Visible.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[EnterExitState.PreEnter.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[EnterExitState.PostExit.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                int i = WhenMappings.$EnumSwitchMapping$0[((EnterExitState) obj).ordinal()];
                float f = 1.0f;
                if (i != 1) {
                    if (i == 2) {
                        Fade fade = EnterTransition.this.getData$animation().fade;
                        if (fade != null) {
                            f = fade.alpha;
                        }
                    } else {
                        if (i != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        Fade fade2 = exitTransition.getData$animation().fade;
                        if (fade2 != null) {
                            f = fade2.alpha;
                        }
                    }
                }
                return Float.valueOf(f);
            }
        }) : null;
        Transition.DeferredAnimation deferredAnimation2 = enterExitTransitionKt$$ExternalSyntheticLambda0.f$1;
        final Transition.DeferredAnimation.DeferredAnimationData animate2 = deferredAnimation2 != null ? deferredAnimation2.animate(new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$createGraphicsLayerBlock$1$1$scale$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                FiniteAnimationSpec finiteAnimationSpec;
                FiniteAnimationSpec finiteAnimationSpec2;
                Transition.Segment segment = (Transition.Segment) obj;
                EnterExitState enterExitState = EnterExitState.PreEnter;
                EnterExitState enterExitState2 = EnterExitState.Visible;
                if (segment.isTransitioningTo(enterExitState, enterExitState2)) {
                    Scale scale = EnterTransition.this.getData$animation().scale;
                    return (scale == null || (finiteAnimationSpec2 = scale.animationSpec) == null) ? EnterExitTransitionKt.DefaultAlphaAndScaleSpring : finiteAnimationSpec2;
                }
                if (!segment.isTransitioningTo(enterExitState2, EnterExitState.PostExit)) {
                    return EnterExitTransitionKt.DefaultAlphaAndScaleSpring;
                }
                Scale scale2 = exitTransition.getData$animation().scale;
                return (scale2 == null || (finiteAnimationSpec = scale2.animationSpec) == null) ? EnterExitTransitionKt.DefaultAlphaAndScaleSpring : finiteAnimationSpec;
            }
        }, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$createGraphicsLayerBlock$1$1$scale$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[EnterExitState.values().length];
                    try {
                        iArr[EnterExitState.Visible.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[EnterExitState.PreEnter.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[EnterExitState.PostExit.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                int i = WhenMappings.$EnumSwitchMapping$0[((EnterExitState) obj).ordinal()];
                float f = 1.0f;
                if (i != 1) {
                    if (i == 2) {
                        Scale scale = EnterTransition.this.getData$animation().scale;
                        if (scale != null) {
                            f = scale.scale;
                        }
                    } else {
                        if (i != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        Scale scale2 = exitTransition.getData$animation().scale;
                        if (scale2 != null) {
                            f = scale2.scale;
                        }
                    }
                }
                return Float.valueOf(f);
            }
        }) : null;
        if (enterExitTransitionKt$$ExternalSyntheticLambda0.f$2.transitionState.getCurrentState() == EnterExitState.PreEnter) {
            Scale scale = enterTransition.getData$animation().scale;
            if (scale != null || (scale = exitTransition.getData$animation().scale) != null) {
                m501boximpl = TransformOrigin.m501boximpl(scale.transformOrigin);
            }
            m501boximpl = null;
        } else {
            Scale scale2 = exitTransition.getData$animation().scale;
            if (scale2 != null || (scale2 = enterTransition.getData$animation().scale) != null) {
                m501boximpl = TransformOrigin.m501boximpl(scale2.transformOrigin);
            }
            m501boximpl = null;
        }
        Transition.DeferredAnimation deferredAnimation3 = enterExitTransitionKt$$ExternalSyntheticLambda0.f$5;
        final Transition.DeferredAnimation.DeferredAnimationData animate3 = deferredAnimation3 != null ? deferredAnimation3.animate(new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$createGraphicsLayerBlock$1$1$transformOrigin$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7);
            }
        }, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$createGraphicsLayerBlock$1$1$transformOrigin$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[EnterExitState.values().length];
                    try {
                        iArr[EnterExitState.Visible.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[EnterExitState.PreEnter.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[EnterExitState.PostExit.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                TransformOrigin transformOrigin;
                long j5;
                long j6;
                long j7;
                int i = WhenMappings.$EnumSwitchMapping$0[((EnterExitState) obj).ordinal()];
                if (i != 1) {
                    transformOrigin = null;
                    if (i == 2) {
                        Scale scale3 = enterTransition.getData$animation().scale;
                        if (scale3 != null) {
                            j6 = scale3.transformOrigin;
                        } else {
                            Scale scale4 = exitTransition.getData$animation().scale;
                            if (scale4 != null) {
                                j6 = scale4.transformOrigin;
                            }
                        }
                        transformOrigin = TransformOrigin.m501boximpl(j6);
                    } else {
                        if (i != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        Scale scale5 = exitTransition.getData$animation().scale;
                        if (scale5 != null) {
                            j7 = scale5.transformOrigin;
                        } else {
                            Scale scale6 = enterTransition.getData$animation().scale;
                            if (scale6 != null) {
                                j7 = scale6.transformOrigin;
                            }
                        }
                        transformOrigin = TransformOrigin.m501boximpl(j7);
                    }
                } else {
                    transformOrigin = TransformOrigin.this;
                }
                if (transformOrigin != null) {
                    j5 = transformOrigin.packedValue;
                } else {
                    TransformOrigin.Companion.getClass();
                    j5 = TransformOrigin.Center;
                }
                return TransformOrigin.m501boximpl(j5);
            }
        }) : null;
        final Function1 function1 = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$createGraphicsLayerBlock$1$1$block$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                long j5;
                GraphicsLayerScope graphicsLayerScope = (GraphicsLayerScope) obj;
                State<Float> state = animate;
                ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
                reusableGraphicsLayerScope.setAlpha(state != null ? ((Number) state.getValue()).floatValue() : 1.0f);
                State<Float> state2 = animate2;
                reusableGraphicsLayerScope.setScaleX(state2 != null ? ((Number) state2.getValue()).floatValue() : 1.0f);
                State<Float> state3 = animate2;
                reusableGraphicsLayerScope.setScaleY(state3 != null ? ((Number) state3.getValue()).floatValue() : 1.0f);
                State<TransformOrigin> state4 = animate3;
                if (state4 != null) {
                    j5 = ((TransformOrigin) state4.getValue()).packedValue;
                } else {
                    TransformOrigin.Companion.getClass();
                    j5 = TransformOrigin.Center;
                }
                reusableGraphicsLayerScope.m496setTransformOrigin__ExYCQ(j5);
                return Unit.INSTANCE;
            }
        };
        final Placeable mo608measureBRTryo03 = measurable.mo608measureBRTryo0(j);
        long j5 = (mo608measureBRTryo03.width << 32) | (mo608measureBRTryo03.height & 4294967295L);
        final long j6 = !IntSize.m861equalsimpl0(this.lookaheadSize, AnimationModifierKt.InvalidSize) ? this.lookaheadSize : j5;
        Transition.DeferredAnimation deferredAnimation4 = this.sizeAnimation;
        Transition.DeferredAnimation.DeferredAnimationData animate4 = deferredAnimation4 != null ? deferredAnimation4.animate(this.sizeTransitionSpec, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$animSize$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Function1 function12;
                Function1 function13;
                EnterExitTransitionModifierNode enterExitTransitionModifierNode = EnterExitTransitionModifierNode.this;
                long j7 = j6;
                enterExitTransitionModifierNode.getClass();
                int i = EnterExitTransitionModifierNode.WhenMappings.$EnumSwitchMapping$0[((EnterExitState) obj).ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        ChangeSize changeSize = enterExitTransitionModifierNode.enter.getData$animation().changeSize;
                        if (changeSize != null && (function12 = changeSize.size) != null) {
                            j7 = ((IntSize) function12.mo779invoke(IntSize.m859boximpl(j7))).packedValue;
                        }
                    } else {
                        if (i != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        ChangeSize changeSize2 = enterExitTransitionModifierNode.exit.getData$animation().changeSize;
                        if (changeSize2 != null && (function13 = changeSize2.size) != null) {
                            j7 = ((IntSize) function13.mo779invoke(IntSize.m859boximpl(j7))).packedValue;
                        }
                    }
                }
                return IntSize.m859boximpl(j7);
            }
        }) : null;
        if (animate4 != null) {
            j5 = ((IntSize) animate4.getValue()).packedValue;
        }
        long m829constrain4WqzIAM = ConstraintsKt.m829constrain4WqzIAM(j, j5);
        Transition.DeferredAnimation deferredAnimation5 = this.offsetAnimation;
        long j7 = 0;
        if (deferredAnimation5 != null) {
            j2 = ((IntOffset) deferredAnimation5.animate(new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$offsetDelta$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    return EnterExitTransitionKt.DefaultOffsetAnimationSpec;
                }
            }, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$offsetDelta$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    EnterExitState enterExitState = (EnterExitState) obj;
                    EnterExitTransitionModifierNode enterExitTransitionModifierNode = EnterExitTransitionModifierNode.this;
                    long j8 = j6;
                    long j9 = 0;
                    if (enterExitTransitionModifierNode.currentAlignment == null) {
                        IntOffset.Companion.getClass();
                    } else if (enterExitTransitionModifierNode.getAlignment() == null) {
                        IntOffset.Companion.getClass();
                    } else if (Intrinsics.areEqual(enterExitTransitionModifierNode.currentAlignment, enterExitTransitionModifierNode.getAlignment())) {
                        IntOffset.Companion.getClass();
                    } else {
                        int i = EnterExitTransitionModifierNode.WhenMappings.$EnumSwitchMapping$0[enterExitState.ordinal()];
                        if (i == 1) {
                            IntOffset.Companion.getClass();
                        } else if (i == 2) {
                            IntOffset.Companion.getClass();
                        } else {
                            if (i != 3) {
                                throw new NoWhenBranchMatchedException();
                            }
                            ChangeSize changeSize = enterExitTransitionModifierNode.exit.getData$animation().changeSize;
                            if (changeSize != null) {
                                long j10 = ((IntSize) changeSize.size.mo779invoke(IntSize.m859boximpl(j8))).packedValue;
                                Alignment alignment2 = enterExitTransitionModifierNode.getAlignment();
                                alignment2.getClass();
                                LayoutDirection layoutDirection = LayoutDirection.Ltr;
                                long mo352alignKFBX0sM = alignment2.mo352alignKFBX0sM(j8, j10, layoutDirection);
                                Alignment alignment3 = enterExitTransitionModifierNode.currentAlignment;
                                alignment3.getClass();
                                j9 = IntOffset.m850minusqkQi6aY(mo352alignKFBX0sM, alignment3.mo352alignKFBX0sM(j8, j10, layoutDirection));
                            } else {
                                IntOffset.Companion.getClass();
                            }
                        }
                    }
                    return IntOffset.m847boximpl(j9);
                }
            }).getValue()).packedValue;
        } else {
            IntOffset.Companion.getClass();
            j2 = 0;
        }
        Transition.DeferredAnimation deferredAnimation6 = this.slideAnimation;
        if (deferredAnimation6 != null) {
            c = ' ';
            j3 = ((IntOffset) deferredAnimation6.animate(this.slideSpec, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$slideOffset$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    long j8;
                    long j9;
                    Function1 function12;
                    Function1 function13;
                    EnterExitState enterExitState = (EnterExitState) obj;
                    EnterExitTransitionModifierNode enterExitTransitionModifierNode = EnterExitTransitionModifierNode.this;
                    long j10 = j6;
                    Slide slide = enterExitTransitionModifierNode.enter.getData$animation().slide;
                    long j11 = 0;
                    if (slide == null || (function13 = slide.slideOffset) == null) {
                        IntOffset.Companion.getClass();
                        j8 = 0;
                    } else {
                        j8 = ((IntOffset) function13.mo779invoke(IntSize.m859boximpl(j10))).packedValue;
                    }
                    Slide slide2 = enterExitTransitionModifierNode.exit.getData$animation().slide;
                    if (slide2 == null || (function12 = slide2.slideOffset) == null) {
                        IntOffset.Companion.getClass();
                        j9 = 0;
                    } else {
                        j9 = ((IntOffset) function12.mo779invoke(IntSize.m859boximpl(j10))).packedValue;
                    }
                    int i = EnterExitTransitionModifierNode.WhenMappings.$EnumSwitchMapping$0[enterExitState.ordinal()];
                    if (i == 1) {
                        IntOffset.Companion.getClass();
                    } else if (i == 2) {
                        j11 = j8;
                    } else {
                        if (i != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        j11 = j9;
                    }
                    return IntOffset.m847boximpl(j11);
                }
            }).getValue()).packedValue;
        } else {
            c = ' ';
            IntOffset.Companion.getClass();
            j3 = 0;
        }
        Alignment alignment2 = this.currentAlignment;
        if (alignment2 != null) {
            j7 = alignment2.mo352alignKFBX0sM(j6, m829constrain4WqzIAM, LayoutDirection.Ltr);
        } else {
            IntOffset.Companion.getClass();
        }
        final long m851plusqkQi6aY = IntOffset.m851plusqkQi6aY(j7, j3);
        final long j8 = j2;
        layout$12 = measureScope.layout$1((int) (m829constrain4WqzIAM >> c), (int) (m829constrain4WqzIAM & 4294967295L), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                Placeable placeable = Placeable.this;
                long j9 = m851plusqkQi6aY;
                IntOffset.Companion companion2 = IntOffset.Companion;
                long j10 = j8;
                Function1 function12 = function1;
                placementScope.getClass();
                Placeable.PlacementScope.access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
                placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY(((((int) (j9 >> 32)) + ((int) (j10 >> 32))) << 32) | ((((int) (j9 & 4294967295L)) + ((int) (j10 & 4294967295L))) & 4294967295L), placeable.apparentToRealOffset), 0.0f, function12);
                return Unit.INSTANCE;
            }
        });
        return layout$12;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        this.lookaheadSize = AnimationModifierKt.InvalidSize;
    }
}
