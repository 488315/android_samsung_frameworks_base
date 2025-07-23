package androidx.compose.foundation.layout;

import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.modifier.ProvidableModifierLocal;
import androidx.compose.ui.platform.InspectableValueKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class WindowInsetsPaddingKt {
    public static final ProvidableModifierLocal ModifierLocalConsumedWindowInsets = new ProvidableModifierLocal(new Function0() { // from class: androidx.compose.foundation.layout.WindowInsetsPaddingKt$ModifierLocalConsumedWindowInsets$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new FixedIntInsets(0, 0, 0, 0);
        }
    });

    public static final Modifier consumeWindowInsets(Modifier modifier, final WindowInsets windowInsets) {
        return ComposedModifierKt.composed(modifier, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.layout.WindowInsetsPaddingKt$consumeWindowInsets$2
            {
                super(3);
            }

            /* JADX WARN: Code restructure failed: missing block: B:7:0x0031, code lost:
            
                if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
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
                    r1 = 788931215(0x2f06228f, float:1.2199507E-10)
                    r2.startReplaceGroup(r1)
                    boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r1 == 0) goto L1c
                    java.lang.String r1 = "androidx.compose.foundation.layout.consumeWindowInsets.<anonymous> (WindowInsetsPadding.kt:100)"
                    androidx.compose.runtime.ComposerKt.traceEventStart(r1)
                L1c:
                    androidx.compose.foundation.layout.WindowInsets r1 = androidx.compose.foundation.layout.WindowInsets.this
                    boolean r1 = r2.changed(r1)
                    androidx.compose.foundation.layout.WindowInsets r0 = androidx.compose.foundation.layout.WindowInsets.this
                    java.lang.Object r3 = r2.rememberedValue()
                    if (r1 != 0) goto L33
                    androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
                    r1.getClass()
                    androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
                    if (r3 != r1) goto L3b
                L33:
                    androidx.compose.foundation.layout.UnionInsetsConsumingModifier r3 = new androidx.compose.foundation.layout.UnionInsetsConsumingModifier
                    r3.<init>(r0)
                    r2.updateRememberedValue(r3)
                L3b:
                    androidx.compose.foundation.layout.UnionInsetsConsumingModifier r3 = (androidx.compose.foundation.layout.UnionInsetsConsumingModifier) r3
                    boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r0 == 0) goto L46
                    androidx.compose.runtime.ComposerKt.traceEventEnd()
                L46:
                    r0 = 0
                    r2.end(r0)
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.WindowInsetsPaddingKt$consumeWindowInsets$2.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
            }
        });
    }

    public static final Modifier onConsumedWindowInsetsChanged(Modifier modifier, final Function1 function1) {
        return ComposedModifierKt.composed(modifier, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.layout.WindowInsetsPaddingKt$onConsumedWindowInsetsChanged$2
            {
                super(3);
            }

            /* JADX WARN: Code restructure failed: missing block: B:7:0x0031, code lost:
            
                if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
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
                    r1 = -1608161351(0xffffffffa02567b9, float:-1.4010363E-19)
                    r2.startReplaceGroup(r1)
                    boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r1 == 0) goto L1c
                    java.lang.String r1 = "androidx.compose.foundation.layout.onConsumedWindowInsetsChanged.<anonymous> (WindowInsetsPadding.kt:141)"
                    androidx.compose.runtime.ComposerKt.traceEventStart(r1)
                L1c:
                    kotlin.jvm.functions.Function1 r1 = kotlin.jvm.functions.Function1.this
                    boolean r1 = r2.changed(r1)
                    kotlin.jvm.functions.Function1 r0 = kotlin.jvm.functions.Function1.this
                    java.lang.Object r3 = r2.rememberedValue()
                    if (r1 != 0) goto L33
                    androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
                    r1.getClass()
                    androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
                    if (r3 != r1) goto L3b
                L33:
                    androidx.compose.foundation.layout.ConsumedInsetsModifier r3 = new androidx.compose.foundation.layout.ConsumedInsetsModifier
                    r3.<init>(r0)
                    r2.updateRememberedValue(r3)
                L3b:
                    androidx.compose.foundation.layout.ConsumedInsetsModifier r3 = (androidx.compose.foundation.layout.ConsumedInsetsModifier) r3
                    boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r0 == 0) goto L46
                    androidx.compose.runtime.ComposerKt.traceEventEnd()
                L46:
                    r0 = 0
                    r2.end(r0)
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.WindowInsetsPaddingKt$onConsumedWindowInsetsChanged$2.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
            }
        });
    }

    public static final Modifier windowInsetsPadding(Modifier modifier, final WindowInsets windowInsets) {
        return ComposedModifierKt.composed(modifier, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.layout.WindowInsetsPaddingKt$windowInsetsPadding$2
            {
                super(3);
            }

            /* JADX WARN: Code restructure failed: missing block: B:7:0x0031, code lost:
            
                if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
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
                    r1 = -1415685722(0xffffffffab9e59a6, float:-1.1251458E-12)
                    r2.startReplaceGroup(r1)
                    boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r1 == 0) goto L1c
                    java.lang.String r1 = "androidx.compose.foundation.layout.windowInsetsPadding.<anonymous> (WindowInsetsPadding.kt:79)"
                    androidx.compose.runtime.ComposerKt.traceEventStart(r1)
                L1c:
                    androidx.compose.foundation.layout.WindowInsets r1 = androidx.compose.foundation.layout.WindowInsets.this
                    boolean r1 = r2.changed(r1)
                    androidx.compose.foundation.layout.WindowInsets r0 = androidx.compose.foundation.layout.WindowInsets.this
                    java.lang.Object r3 = r2.rememberedValue()
                    if (r1 != 0) goto L33
                    androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
                    r1.getClass()
                    androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
                    if (r3 != r1) goto L3b
                L33:
                    androidx.compose.foundation.layout.InsetsPaddingModifier r3 = new androidx.compose.foundation.layout.InsetsPaddingModifier
                    r3.<init>(r0)
                    r2.updateRememberedValue(r3)
                L3b:
                    androidx.compose.foundation.layout.InsetsPaddingModifier r3 = (androidx.compose.foundation.layout.InsetsPaddingModifier) r3
                    boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r0 == 0) goto L46
                    androidx.compose.runtime.ComposerKt.traceEventEnd()
                L46:
                    r0 = 0
                    r2.end(r0)
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.WindowInsetsPaddingKt$windowInsetsPadding$2.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
            }
        });
    }
}
