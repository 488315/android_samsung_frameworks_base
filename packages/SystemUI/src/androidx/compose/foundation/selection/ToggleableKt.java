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
import androidx.compose.ui.state.ToggleableState;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ToggleableKt {
    /* renamed from: triStateToggleable-O2vRcR0, reason: not valid java name */
    public static final Modifier m182triStateToggleableO2vRcR0(Modifier modifier, final ToggleableState toggleableState, MutableInteractionSource mutableInteractionSource, final Indication indication, final boolean z, final Role role, final Function0 function0) {
        Modifier composed;
        if (indication instanceof IndicationNodeFactory) {
            composed = new TriStateToggleableElement(toggleableState, mutableInteractionSource, (IndicationNodeFactory) indication, z, role, function0, null);
        } else if (indication == null) {
            composed = new TriStateToggleableElement(toggleableState, mutableInteractionSource, null, z, role, function0, null);
        } else if (mutableInteractionSource != null) {
            composed = IndicationKt.indication(Modifier.Companion, mutableInteractionSource, indication).then(new TriStateToggleableElement(toggleableState, mutableInteractionSource, null, z, role, function0, null));
        } else {
            composed = ComposedModifierKt.composed(Modifier.Companion, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.selection.ToggleableKt$triStateToggleable-O2vRcR0$$inlined$clickableWithIndicationIfNeeded$1
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
                    Object rememberedValue = composerImpl.rememberedValue();
                    Composer.Companion.getClass();
                    if (rememberedValue == Composer.Companion.Empty) {
                        rememberedValue = InteractionSourceKt.MutableInteractionSource();
                        composerImpl.updateRememberedValue(rememberedValue);
                    }
                    MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) rememberedValue;
                    Modifier then = IndicationKt.indication(Modifier.Companion, mutableInteractionSource2, Indication.this).then(new TriStateToggleableElement(toggleableState, mutableInteractionSource2, null, z, role, function0, null));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    return then;
                }
            });
        }
        return modifier.then(composed);
    }
}
