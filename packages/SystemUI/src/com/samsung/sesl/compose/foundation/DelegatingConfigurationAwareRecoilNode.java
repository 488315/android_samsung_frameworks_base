package com.samsung.sesl.compose.foundation;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.ObserverModifierNode;
import androidx.compose.ui.node.ObserverModifierNodeKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DelegatingConfigurationAwareRecoilNode extends DelegatingNode implements CompositionLocalConsumerModifierNode, ObserverModifierNode {
    public final ColorProducer color;
    public final SeslRecoilDrawStrategy drawStrategy;
    public final InteractionSource interactionSource;
    public final Function0 scale;
    public final Function0 shape;

    public DelegatingConfigurationAwareRecoilNode(InteractionSource interactionSource, ColorProducer colorProducer, Function0 function0, Function0 function02, SeslRecoilDrawStrategy seslRecoilDrawStrategy) {
        this.interactionSource = interactionSource;
        this.color = colorProducer;
        this.scale = function0;
        this.shape = function02;
        this.drawStrategy = seslRecoilDrawStrategy;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        ObserverModifierNodeKt.observeReads(this, new DelegatingConfigurationAwareRecoilNode$$ExternalSyntheticLambda0(this, 0));
    }

    @Override // androidx.compose.ui.node.ObserverModifierNode
    public final void onObservedReadsChanged() {
        ObserverModifierNodeKt.observeReads(this, new DelegatingConfigurationAwareRecoilNode$$ExternalSyntheticLambda0(this, 0));
    }
}
