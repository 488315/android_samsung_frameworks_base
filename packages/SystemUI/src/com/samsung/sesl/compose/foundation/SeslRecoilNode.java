package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import com.samsung.sesl.compose.foundation.interaction.SeslInteractionAwareModifierNode;
import com.samsung.sesl.compose.foundation.interaction.SeslInteractionState;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslRecoilNode extends DelegatingNode implements DrawModifierNode, CompositionLocalConsumerModifierNode {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SeslRecoilDrawStrategy drawStrategy;
    public boolean enabled;
    public Function0 feedbackAlpha;
    public ColorProducer feedbackColor;
    public PaddingValues feedbackMargin;
    public final SeslFeedbackNode feedbackNode;
    public Shape feedbackShape;
    public final SeslInteractionAwareModifierNode interactionAwareModifierNode;
    public InteractionSource interactionSource;
    public SeslInteractionState interactionState;
    public float scale;
    public Animatable scaleAnimatable;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SeslRecoilNode(InteractionSource interactionSource, boolean z, float f, Shape shape, ColorProducer colorProducer, PaddingValues paddingValues, Function0 function0, SeslRecoilDrawStrategy seslRecoilDrawStrategy) {
        InteractionSource interactionSource2;
        SeslFeedbackNode seslFeedbackNode;
        this.enabled = z;
        this.scale = f;
        this.feedbackShape = shape;
        this.feedbackColor = colorProducer;
        this.feedbackMargin = paddingValues;
        this.feedbackAlpha = function0;
        this.drawStrategy = seslRecoilDrawStrategy;
        SeslInteractionState.Companion.getClass();
        this.interactionState = SeslInteractionState.None;
        this.interactionAwareModifierNode = new SeslInteractionAwareModifierNode(interactionSource, new SeslRecoilNode$$ExternalSyntheticLambda2(this, 0));
        final Shape shape2 = this.feedbackShape;
        if (shape2 != null) {
            final int i = 0;
            Function0 function02 = new Function0() { // from class: com.samsung.sesl.compose.foundation.SeslRecoilNode$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Object obj = this;
                    switch (i) {
                        case 0:
                            return Boolean.valueOf(((SeslRecoilNode) obj).enabled);
                        case 1:
                            return ((SeslRecoilNode) obj).feedbackMargin;
                        default:
                            int i2 = SeslRecoilNode.$r8$clinit;
                            return (Shape) obj;
                    }
                }
            };
            final int i2 = 2;
            final int i3 = 1;
            interactionSource2 = interactionSource;
            seslFeedbackNode = new SeslFeedbackNode(interactionSource2, function02, new Function0() { // from class: com.samsung.sesl.compose.foundation.SeslRecoilNode$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Object obj = shape2;
                    switch (i2) {
                        case 0:
                            return Boolean.valueOf(((SeslRecoilNode) obj).enabled);
                        case 1:
                            return ((SeslRecoilNode) obj).feedbackMargin;
                        default:
                            int i22 = SeslRecoilNode.$r8$clinit;
                            return (Shape) obj;
                    }
                }
            }, this.feedbackColor, this.feedbackAlpha, new Function0() { // from class: com.samsung.sesl.compose.foundation.SeslRecoilNode$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Object obj = this;
                    switch (i3) {
                        case 0:
                            return Boolean.valueOf(((SeslRecoilNode) obj).enabled);
                        case 1:
                            return ((SeslRecoilNode) obj).feedbackMargin;
                        default:
                            int i22 = SeslRecoilNode.$r8$clinit;
                            return (Shape) obj;
                    }
                }
            });
        } else {
            interactionSource2 = interactionSource;
            seslFeedbackNode = null;
        }
        this.feedbackNode = seslFeedbackNode;
        this.interactionSource = interactionSource2;
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        if (this.enabled) {
            this.drawStrategy.draw(layoutNodeDrawScope, new SeslRecoilNode$$ExternalSyntheticLambda0(this), new SeslRecoilNode$$ExternalSyntheticLambda2(this, 1));
        } else {
            layoutNodeDrawScope.drawContent();
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        this.scaleAnimatable = AnimatableKt.Animatable(1.0f, 0.01f);
        delegate(this.interactionAwareModifierNode);
        SeslFeedbackNode seslFeedbackNode = this.feedbackNode;
        if (seslFeedbackNode != null) {
            delegate(seslFeedbackNode);
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        this.scaleAnimatable = null;
        undelegate(this.interactionAwareModifierNode);
        SeslFeedbackNode seslFeedbackNode = this.feedbackNode;
        if (seslFeedbackNode != null) {
            undelegate(seslFeedbackNode);
        }
    }
}
