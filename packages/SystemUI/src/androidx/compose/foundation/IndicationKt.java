package androidx.compose.foundation;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectableValueKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class IndicationKt {
    public static final StaticProvidableCompositionLocal LocalIndication = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.foundation.IndicationKt$LocalIndication$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DefaultDebugIndication.INSTANCE;
        }
    });

    public static final Modifier indication(Modifier modifier, final InteractionSource interactionSource, final Indication indication) {
        return indication == null ? modifier : indication instanceof IndicationNodeFactory ? modifier.then(new IndicationModifierElement(interactionSource, (IndicationNodeFactory) indication)) : ComposedModifierKt.composed(modifier, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.IndicationKt$indication$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            /* JADX WARN: Code restructure failed: missing block: B:13:0x0052, code lost:
            
                if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
             */
            @Override // kotlin.jvm.functions.Function3
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke(java.lang.Object r1, java.lang.Object r2, java.lang.Object r3) {
                /*
                    r0 = this;
                    androidx.compose.ui.Modifier r1 = (androidx.compose.ui.Modifier) r1
                    androidx.compose.runtime.Composer r2 = (androidx.compose.runtime.Composer) r2
                    java.lang.Number r3 = (java.lang.Number) r3
                    r3.intValue()
                    androidx.compose.runtime.ComposerImpl r2 = (androidx.compose.runtime.ComposerImpl) r2
                    r1 = -353972293(0xffffffffeae6cfbb, float:-1.3951696E26)
                    r2.startReplaceGroup(r1)
                    boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r1 == 0) goto L1c
                    java.lang.String r1 = "androidx.compose.foundation.indication.<anonymous> (Indication.kt:176)"
                    androidx.compose.runtime.ComposerKt.traceEventStart(r1)
                L1c:
                    androidx.compose.foundation.Indication r0 = androidx.compose.foundation.Indication.this
                    r0.getClass()
                    r0 = 1257603829(0x4af582f5, float:8044922.5)
                    r2.startReplaceGroup(r0)
                    boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r0 == 0) goto L32
                    java.lang.String r0 = "androidx.compose.foundation.Indication.rememberUpdatedInstance (Indication.kt:74)"
                    androidx.compose.runtime.ComposerKt.traceEventStart(r0)
                L32:
                    androidx.compose.foundation.NoIndicationInstance r0 = androidx.compose.foundation.NoIndicationInstance.INSTANCE
                    boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r1 == 0) goto L3d
                    androidx.compose.runtime.ComposerKt.traceEventEnd()
                L3d:
                    r1 = 0
                    r2.end(r1)
                    boolean r1 = r2.changed(r0)
                    java.lang.Object r3 = r2.rememberedValue()
                    if (r1 != 0) goto L54
                    androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
                    r1.getClass()
                    androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
                    if (r3 != r1) goto L5c
                L54:
                    androidx.compose.foundation.IndicationModifier r3 = new androidx.compose.foundation.IndicationModifier
                    r3.<init>(r0)
                    r2.updateRememberedValue(r3)
                L5c:
                    androidx.compose.foundation.IndicationModifier r3 = (androidx.compose.foundation.IndicationModifier) r3
                    boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r0 == 0) goto L67
                    androidx.compose.runtime.ComposerKt.traceEventEnd()
                L67:
                    r0 = 0
                    r2.end(r0)
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.IndicationKt$indication$2.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
            }
        });
    }
}
