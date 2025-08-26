package androidx.compose.foundation;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectableValueKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
public abstract class IndicationKt {
    public static final StaticProvidableCompositionLocal LocalIndication = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.foundation.IndicationKt$LocalIndication$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DefaultDebugIndication.INSTANCE;
        }
    });

    public static final Modifier indication(Modifier modifier, final InteractionSource interactionSource, final Indication indication) {
        return indication == null ? modifier : indication instanceof IndicationNodeFactory ? modifier.then(new IndicationModifierElement(interactionSource, (IndicationNodeFactory) indication)) : ComposedModifierKt.composed(modifier, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.IndicationKt.indication.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x0054  */
            @Override // kotlin.jvm.functions.Function3
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                composerImpl.startReplaceGroup(-353972293);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.foundation.indication.<anonymous> (Indication.kt:176)");
                }
                indication.getClass();
                composerImpl.startReplaceGroup(1257603829);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.foundation.Indication.rememberUpdatedInstance (Indication.kt:74)");
                }
                NoIndicationInstance noIndicationInstance = NoIndicationInstance.INSTANCE;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                boolean zChanged = composerImpl.changed(noIndicationInstance);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChanged) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new IndicationModifier(noIndicationInstance);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                }
                IndicationModifier indicationModifier = (IndicationModifier) objRememberedValue;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                return indicationModifier;
            }
        });
    }
}
