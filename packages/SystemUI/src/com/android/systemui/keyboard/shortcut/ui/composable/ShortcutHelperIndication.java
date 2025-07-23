package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.foundation.IndicationNodeFactory;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.node.DelegatableNode;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutHelperIndication implements IndicationNodeFactory {
    public final InteractionsConfig interactionsConfig;

    public ShortcutHelperIndication(InteractionsConfig interactionsConfig) {
        this.interactionsConfig = interactionsConfig;
    }

    @Override // androidx.compose.foundation.IndicationNodeFactory
    public final DelegatableNode create(InteractionSource interactionSource) {
        return new ShortcutHelperInteractionsNode(interactionSource, this.interactionsConfig);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ShortcutHelperIndication) && Intrinsics.areEqual(this.interactionsConfig, ((ShortcutHelperIndication) obj).interactionsConfig);
    }

    @Override // androidx.compose.foundation.IndicationNodeFactory
    public final int hashCode() {
        return this.interactionsConfig.hashCode();
    }

    public final String toString() {
        return "ShortcutHelperIndication(interactionsConfig=" + this.interactionsConfig + ")";
    }
}
