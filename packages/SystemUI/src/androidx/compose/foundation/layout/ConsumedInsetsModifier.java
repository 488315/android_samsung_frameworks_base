package androidx.compose.foundation.layout;

import androidx.compose.ui.modifier.ModifierLocalConsumer;
import androidx.compose.ui.modifier.ModifierLocalReadScope;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ConsumedInsetsModifier implements ModifierLocalConsumer {
    public final Function1 block;
    public WindowInsets oldWindowInsets;

    public ConsumedInsetsModifier(Function1 function1) {
        this.block = function1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ConsumedInsetsModifier) && ((ConsumedInsetsModifier) obj).block == this.block;
    }

    public final int hashCode() {
        return this.block.hashCode();
    }

    @Override // androidx.compose.ui.modifier.ModifierLocalConsumer
    public final void onModifierLocalsUpdated(ModifierLocalReadScope modifierLocalReadScope) {
        WindowInsets windowInsets = (WindowInsets) modifierLocalReadScope.getCurrent(WindowInsetsPaddingKt.ModifierLocalConsumedWindowInsets);
        if (Intrinsics.areEqual(windowInsets, this.oldWindowInsets)) {
            return;
        }
        this.oldWindowInsets = windowInsets;
        this.block.mo779invoke(windowInsets);
    }
}
