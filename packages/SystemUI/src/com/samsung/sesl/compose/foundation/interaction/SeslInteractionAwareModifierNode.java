package com.samsung.sesl.compose.foundation.interaction;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.Modifier;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslInteractionAwareModifierNode extends Modifier.Node {
    public InteractionSource interactionSource;
    public StandaloneCoroutine observeInteractionsJob;
    public final Function1 onState;
    public SeslInteractionState previous;
    public final List pressInteraction = new ArrayList();
    public final List touchInteraction = new ArrayList();
    public final List dragInteraction = new ArrayList();
    public final List hoverInteraction = new ArrayList();
    public final List focusInteraction = new ArrayList();

    public SeslInteractionAwareModifierNode(InteractionSource interactionSource, Function1 function1) {
        this.onState = function1;
        this.interactionSource = interactionSource;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        BuildersKt.launch$default(getCoroutineScope(), null, null, new SeslInteractionAwareModifierNode$onAttach$1(this, null), 3);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        StandaloneCoroutine standaloneCoroutine = this.observeInteractionsJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.observeInteractionsJob = null;
    }

    public final void setInteractionSource(InteractionSource interactionSource) {
        this.interactionSource = interactionSource;
        if (this.isAttached) {
            BuildersKt.launch$default(getCoroutineScope(), null, null, new SeslInteractionAwareModifierNode$interactionSource$1(this, null), 3);
        }
    }
}
