package androidx.compose.foundation.selection;

import androidx.compose.foundation.Indication;
import androidx.compose.foundation.IndicationKt;
import androidx.compose.foundation.IndicationNodeFactory;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.semantics.Role;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
public abstract class SelectableKt {
    /* renamed from: selectable-O2vRcR0, reason: not valid java name */
    public static final Modifier m182selectableO2vRcR0(Modifier modifier, final boolean z, MutableInteractionSource mutableInteractionSource, final Indication indication, final boolean z2, final Role role, final Function0 function0) {
        Modifier modifierComposed;
        if (indication instanceof IndicationNodeFactory) {
            modifierComposed = new SelectableElement(z, mutableInteractionSource, (IndicationNodeFactory) indication, z2, role, function0, null);
        } else if (indication == null) {
            modifierComposed = new SelectableElement(z, mutableInteractionSource, null, z2, role, function0, null);
        } else if (mutableInteractionSource != null) {
            modifierComposed = IndicationKt.indication(Modifier.Companion, mutableInteractionSource, indication).then(new SelectableElement(z, mutableInteractionSource, null, z2, role, function0, null));
        } else {
            modifierComposed = ComposedModifierKt.composed(Modifier.Companion, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.selection.SelectableKt$selectable-O2vRcR0$$inlined$clickableWithIndicationIfNeeded$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                    composerImpl.startReplaceGroup(-1525724089);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.clickableWithIndicationIfNeeded.<anonymous> (Clickable.kt:473)");
                    }
                    Object objRememberedValue = composerImpl.rememberedValue();
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) objRememberedValue;
                    Modifier modifierThen = IndicationKt.indication(Modifier.Companion, mutableInteractionSource2, indication).then(new SelectableElement(z, mutableInteractionSource2, null, z2, role, function0, null));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    return modifierThen;
                }
            });
        }
        return modifier.then(modifierComposed);
    }
}
