package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.OutlineKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.sesl.compose.foundation.interaction.SeslInteractionAwareModifierNode;
import com.samsung.sesl.compose.foundation.interaction.SeslInteractionState;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslFeedbackNode extends DelegatingNode implements DrawModifierNode, CompositionLocalConsumerModifierNode {
    public final Animatable alphaAnimatable;
    public final ColorProducer color;
    public final Function0 enabled;
    public final Function0 feedbackAlpha;
    public final SeslInteractionAwareModifierNode interactionAwareModifierNode;
    public InteractionSource interactionSource;
    public SeslInteractionState interactionState;
    public final Function0 margin;
    public final Function0 shape;

    public SeslFeedbackNode(InteractionSource interactionSource, Function0 function0, Function0 function02, ColorProducer colorProducer, Function0 function03, Function0 function04) {
        this.enabled = function0;
        this.shape = function02;
        this.color = colorProducer;
        this.feedbackAlpha = function03;
        this.margin = function04;
        this.interactionSource = interactionSource;
        SeslInteractionState.Companion.getClass();
        this.interactionState = SeslInteractionState.None;
        this.interactionAwareModifierNode = new SeslInteractionAwareModifierNode(interactionSource, new Function1() { // from class: com.samsung.sesl.compose.foundation.SeslFeedbackNode$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                SeslInteractionState seslInteractionState = (SeslInteractionState) obj;
                SeslFeedbackNode seslFeedbackNode = SeslFeedbackNode.this;
                boolean areEqual = Intrinsics.areEqual(seslFeedbackNode.interactionState, seslInteractionState);
                seslFeedbackNode.interactionState = seslInteractionState;
                if (!areEqual && seslFeedbackNode.node.isAttached && !((Boolean) CompositionLocalConsumerModifierNodeKt.currentValueOf(seslFeedbackNode, FeedbackKt.LocalSeslIgnoreFeedbackEffect)).booleanValue() && ((Boolean) seslFeedbackNode.enabled.invoke()).booleanValue()) {
                    BuildersKt.launch$default(seslFeedbackNode.getCoroutineScope(), null, null, new SeslFeedbackNode$updateAnimation$1(seslFeedbackNode, null), 3);
                }
                return Unit.INSTANCE;
            }
        });
        this.alphaAnimatable = AnimatableKt.Animatable(0.0f, 0.01f);
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        if (!((Boolean) this.enabled.invoke()).booleanValue() || ((Boolean) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, FeedbackKt.LocalSeslIgnoreFeedbackEffect)).booleanValue()) {
            layoutNodeDrawScope.drawContent();
        } else {
            layoutNodeDrawScope.drawContent();
            drawFeedback$foundation_release(layoutNodeDrawScope);
        }
    }

    public final void drawFeedback$foundation_release(DrawScope drawScope) {
        DrawScope drawScope2;
        LayoutDirection layoutDirection = (LayoutDirection) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, androidx.compose.ui.platform.CompositionLocalsKt.LocalLayoutDirection);
        PaddingValues paddingValues = (PaddingValues) this.margin.invoke();
        float mo111calculateRightPaddingu2uoSUM = paddingValues.mo111calculateRightPaddingu2uoSUM(layoutDirection) + paddingValues.mo110calculateLeftPaddingu2uoSUM(layoutDirection);
        Dp.Companion companion = Dp.Companion;
        Outline mo40createOutlinePq9zytI = ((Shape) this.shape.invoke()).mo40createOutlinePq9zytI(SizeKt.Size(Size.m417getWidthimpl(drawScope.mo545getSizeNHjbRc()) + drawScope.mo51roundToPx0680j_4(mo111calculateRightPaddingu2uoSUM), Size.m415getHeightimpl(drawScope.mo545getSizeNHjbRc()) + drawScope.mo51roundToPx0680j_4(paddingValues.mo109calculateBottomPaddingD9Ej5fM() + paddingValues.mo112calculateTopPaddingD9Ej5fM())), layoutDirection, (Density) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, androidx.compose.ui.platform.CompositionLocalsKt.LocalDensity));
        float f = -drawScope.mo57toPx0680j_4(paddingValues.mo110calculateLeftPaddingu2uoSUM(layoutDirection));
        float f2 = -drawScope.mo57toPx0680j_4(paddingValues.mo112calculateTopPaddingD9Ej5fM());
        drawScope.getDrawContext().transform.translate(f, f2);
        try {
            drawScope2 = drawScope;
        } catch (Throwable th) {
            th = th;
            drawScope2 = drawScope;
        }
        try {
            OutlineKt.m490drawOutlinewDX37Ww$default(drawScope2, mo40createOutlinePq9zytI, this.color.mo261invoke0d7_KjU(), ((Number) this.alphaAnimatable.internalState.getValue()).floatValue(), null, 56);
            drawScope2.getDrawContext().transform.translate(-f, -f2);
        } catch (Throwable th2) {
            th = th2;
            Throwable th3 = th;
            drawScope2.getDrawContext().transform.translate(-f, -f2);
            throw th3;
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        delegate(this.interactionAwareModifierNode);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        undelegate(this.interactionAwareModifierNode);
    }
}
