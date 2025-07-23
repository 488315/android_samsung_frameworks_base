package androidx.compose.foundation.layout;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.modifier.ModifierLocalConsumer;
import androidx.compose.ui.modifier.ModifierLocalProvider;
import androidx.compose.ui.modifier.ModifierLocalReadScope;
import androidx.compose.ui.modifier.ProvidableModifierLocal;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
abstract class InsetsConsumingModifier implements ModifierLocalConsumer, ModifierLocalProvider<WindowInsets> {
    public final MutableState consumedInsets$delegate;

    public /* synthetic */ InsetsConsumingModifier(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract WindowInsets calculateInsets(WindowInsets windowInsets);

    @Override // androidx.compose.ui.modifier.ModifierLocalProvider
    public final ProvidableModifierLocal getKey() {
        return WindowInsetsPaddingKt.ModifierLocalConsumedWindowInsets;
    }

    @Override // androidx.compose.ui.modifier.ModifierLocalProvider
    public final WindowInsets getValue$1() {
        return (WindowInsets) ((SnapshotMutableStateImpl) this.consumedInsets$delegate).getValue();
    }

    @Override // androidx.compose.ui.modifier.ModifierLocalConsumer
    public final void onModifierLocalsUpdated(ModifierLocalReadScope modifierLocalReadScope) {
        ((SnapshotMutableStateImpl) this.consumedInsets$delegate).setValue(calculateInsets((WindowInsets) modifierLocalReadScope.getCurrent(WindowInsetsPaddingKt.ModifierLocalConsumedWindowInsets)));
    }

    private InsetsConsumingModifier() {
        this.consumedInsets$delegate = SnapshotStateKt.mutableStateOf$default(new FixedIntInsets(0, 0, 0, 0));
    }
}
