package androidx.compose.material.ripple;

import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.interaction.DragInteraction$Cancel;
import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.DragInteraction$Stop;
import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.FocusInteraction$Unfocus;
import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.HoverInteraction$Exit;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Cancel;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.interaction.PressInteraction$Release;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.DrawModifierNodeKt;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes.dex */
public abstract class RippleNode extends Modifier.Node implements CompositionLocalConsumerModifierNode, DrawModifierNode {
    public final boolean bounded;
    public final ColorProducer color;
    public final InteractionSource interactionSource;
    public final float radius;
    public final Function0 rippleAlpha;
    public StateLayer stateLayer;
    public float targetRadius;

    /* renamed from: androidx.compose.material.ripple.RippleNode$onAttach$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = RippleNode.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                SharedFlowImpl interactions = RippleNode.this.interactionSource.getInteractions();
                final RippleNode rippleNode = RippleNode.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: androidx.compose.material.ripple.RippleNode.onAttach.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Interaction interaction = (Interaction) obj2;
                        boolean z = interaction instanceof PressInteraction$Press;
                        RippleNode rippleNode2 = rippleNode;
                        if (z) {
                            rippleNode2.addRipple$1((PressInteraction$Press) interaction);
                        } else if (interaction instanceof PressInteraction$Release) {
                            rippleNode2.removeRipple(((PressInteraction$Release) interaction).press);
                        } else if (interaction instanceof PressInteraction$Cancel) {
                            rippleNode2.removeRipple(((PressInteraction$Cancel) interaction).press);
                        } else {
                            StateLayer stateLayer = rippleNode2.stateLayer;
                            if (stateLayer == null) {
                                stateLayer = new StateLayer(rippleNode2.bounded, rippleNode2.rippleAlpha);
                                DrawModifierNodeKt.invalidateDraw(rippleNode2);
                                rippleNode2.stateLayer = stateLayer;
                            }
                            CoroutineScope coroutineScope2 = coroutineScope;
                            stateLayer.getClass();
                            boolean z2 = interaction instanceof HoverInteraction$Enter;
                            if (z2) {
                                ((ArrayList) stateLayer.interactions).add(interaction);
                            } else if (interaction instanceof HoverInteraction$Exit) {
                                ((ArrayList) stateLayer.interactions).remove(((HoverInteraction$Exit) interaction).enter);
                            } else if (interaction instanceof FocusInteraction$Focus) {
                                ((ArrayList) stateLayer.interactions).add(interaction);
                            } else if (interaction instanceof FocusInteraction$Unfocus) {
                                ((ArrayList) stateLayer.interactions).remove(((FocusInteraction$Unfocus) interaction).focus);
                            } else if (interaction instanceof DragInteraction$Start) {
                                ((ArrayList) stateLayer.interactions).add(interaction);
                            } else if (interaction instanceof DragInteraction$Stop) {
                                ((ArrayList) stateLayer.interactions).remove(((DragInteraction$Stop) interaction).start);
                            } else if (interaction instanceof DragInteraction$Cancel) {
                                ((ArrayList) stateLayer.interactions).remove(((DragInteraction$Cancel) interaction).start);
                            }
                            Interaction interaction2 = (Interaction) CollectionsKt___CollectionsKt.lastOrNull(stateLayer.interactions);
                            if (!Intrinsics.areEqual(stateLayer.currentInteraction, interaction2)) {
                                if (interaction2 != null) {
                                    RippleAlpha rippleAlpha = (RippleAlpha) stateLayer.rippleAlpha.invoke();
                                    float f = z2 ? rippleAlpha.hoveredAlpha : interaction instanceof FocusInteraction$Focus ? rippleAlpha.focusedAlpha : interaction instanceof DragInteraction$Start ? rippleAlpha.draggedAlpha : 0.0f;
                                    TweenSpec tweenSpec = RippleKt.DefaultTweenSpec;
                                    boolean z3 = interaction2 instanceof HoverInteraction$Enter;
                                    TweenSpec tweenSpec2 = RippleKt.DefaultTweenSpec;
                                    if (!z3 && ((interaction2 instanceof FocusInteraction$Focus) || (interaction2 instanceof DragInteraction$Start))) {
                                        TweenSpec tweenSpec3 = new TweenSpec(45, 0, EasingKt.LinearEasing, 2, null);
                                        tweenSpec2 = tweenSpec3;
                                    }
                                    BuildersKt.launch$default(coroutineScope2, null, null, new StateLayer$handleInteraction$1(stateLayer, f, tweenSpec2, null), 3);
                                } else {
                                    Interaction interaction3 = stateLayer.currentInteraction;
                                    TweenSpec tweenSpec4 = RippleKt.DefaultTweenSpec;
                                    boolean z4 = interaction3 instanceof HoverInteraction$Enter;
                                    TweenSpec tweenSpec5 = RippleKt.DefaultTweenSpec;
                                    if (!z4 && !(interaction3 instanceof FocusInteraction$Focus) && (interaction3 instanceof DragInteraction$Start)) {
                                        tweenSpec5 = new TweenSpec(150, 0, EasingKt.LinearEasing, 2, null);
                                    }
                                    BuildersKt.launch$default(coroutineScope2, null, null, new StateLayer$handleInteraction$2(stateLayer, tweenSpec5, null), 3);
                                }
                                stateLayer.currentInteraction = interaction2;
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                interactions.getClass();
                if (SharedFlowImpl.collect$suspendImpl(interactions, flowCollector, this) == coroutineSingletons) {
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

    public /* synthetic */ RippleNode(InteractionSource interactionSource, boolean z, float f, ColorProducer colorProducer, Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(interactionSource, z, f, colorProducer, function0);
    }

    public abstract void addRipple$1(PressInteraction$Press pressInteraction$Press);

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        float fMo58toPx0680j_4;
        float f = this.radius;
        if (Float.isNaN(f)) {
            fMo58toPx0680j_4 = RippleAnimationKt.m244getRippleEndRadiuscSwnlzA(layoutNodeDrawScope, this.bounded, layoutNodeDrawScope.canvasDrawScope.mo547getSizeNHjbRc());
        } else {
            fMo58toPx0680j_4 = layoutNodeDrawScope.mo58toPx0680j_4(f);
        }
        this.targetRadius = fMo58toPx0680j_4;
        layoutNodeDrawScope.drawContent();
        StateLayer stateLayer = this.stateLayer;
        if (stateLayer != null) {
            float f2 = this.targetRadius;
            long jMo262invoke0d7_KjU = this.color.mo262invoke0d7_KjU();
            float fFloatValue = ((Number) stateLayer.animatedAlpha.internalState.getValue()).floatValue();
            if (fFloatValue > 0.0f) {
                long jColor = ColorKt.Color(Color.m463getRedimpl(jMo262invoke0d7_KjU), Color.m462getGreenimpl(jMo262invoke0d7_KjU), Color.m460getBlueimpl(jMo262invoke0d7_KjU), fFloatValue, Color.m461getColorSpaceimpl(jMo262invoke0d7_KjU));
                if (stateLayer.bounded) {
                    CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                    float fM419getWidthimpl = Size.m419getWidthimpl(canvasDrawScope.mo547getSizeNHjbRc());
                    float fM417getHeightimpl = Size.m417getHeightimpl(canvasDrawScope.mo547getSizeNHjbRc());
                    ClipOp.Companion.getClass();
                    int i = ClipOp.Intersect;
                    CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
                    long jM528getSizeNHjbRc = canvasDrawScope$drawContext$1.m528getSizeNHjbRc();
                    canvasDrawScope$drawContext$1.getCanvas().save();
                    canvasDrawScope$drawContext$1.transform.m530clipRectN_I0leg(0.0f, 0.0f, fM419getWidthimpl, fM417getHeightimpl, i);
                    DrawScope.m534drawCircleVaOC9Bg$default(layoutNodeDrawScope, jColor, f2, 0L, 0.0f, null, 0, 124);
                    BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, jM528getSizeNHjbRc);
                } else {
                    DrawScope.m534drawCircleVaOC9Bg$default(layoutNodeDrawScope, jColor, f2, 0L, 0.0f, null, 0, 124);
                }
            }
        }
        drawRipples(layoutNodeDrawScope);
    }

    public abstract void drawRipples(LayoutNodeDrawScope layoutNodeDrawScope);

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        BuildersKt.launch$default(getCoroutineScope(), null, null, new AnonymousClass1(null), 3);
    }

    public abstract void removeRipple(PressInteraction$Press pressInteraction$Press);

    private RippleNode(InteractionSource interactionSource, boolean z, float f, ColorProducer colorProducer, Function0 function0) {
        this.interactionSource = interactionSource;
        this.bounded = z;
        this.radius = f;
        this.color = colorProducer;
        this.rippleAlpha = function0;
    }
}
