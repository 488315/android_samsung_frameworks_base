package com.android.systemui.qs.panels.ui.compose.toolbar;

import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.qs.panels.ui.viewmodel.toolbar.EditModeButtonViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class EditModeButtonKt {
    public static final void EditModeButton(final EditModeButtonViewModel editModeButtonViewModel, final Modifier.Companion companion, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1958848283);
        if ((((composerImpl.changedInstance(editModeButtonViewModel) ? 4 : 2) | i | 48) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.toolbar.EditModeButton (EditModeButton.kt:36)");
            }
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
            MaterialTheme.INSTANCE.getClass();
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m454boximpl(MaterialTheme.getColorScheme(composerImpl).onSurface)), ComposableLambdaKt.rememberComposableLambda(241794011, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.toolbar.EditModeButtonKt$EditModeButton$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0043, code lost:
                
                    if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r11, java.lang.Object r12) {
                    /*
                        r10 = this;
                        androidx.compose.runtime.Composer r11 = (androidx.compose.runtime.Composer) r11
                        java.lang.Number r12 = (java.lang.Number) r12
                        int r12 = r12.intValue()
                        r12 = r12 & 3
                        r0 = 2
                        if (r12 != r0) goto L1c
                        r12 = r11
                        androidx.compose.runtime.ComposerImpl r12 = (androidx.compose.runtime.ComposerImpl) r12
                        boolean r0 = r12.getSkipping()
                        if (r0 != 0) goto L17
                        goto L1c
                    L17:
                        r12.skipToGroupEnd()
                        goto L98
                    L1c:
                        boolean r12 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r12 == 0) goto L27
                        java.lang.String r12 = "com.android.systemui.qs.panels.ui.compose.toolbar.EditModeButton.<anonymous> (EditModeButton.kt:40)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r12)
                    L27:
                        r4 = r11
                        androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                        r11 = -567853395(0xffffffffde273ead, float:-3.012815E18)
                        r4.startReplaceGroup(r11)
                        com.android.systemui.qs.panels.ui.viewmodel.toolbar.EditModeButtonViewModel r11 = com.android.systemui.qs.panels.ui.viewmodel.toolbar.EditModeButtonViewModel.this
                        boolean r12 = r4.changedInstance(r11)
                        java.lang.Object r0 = r4.rememberedValue()
                        if (r12 != 0) goto L45
                        androidx.compose.runtime.Composer$Companion r12 = androidx.compose.runtime.Composer.Companion
                        r12.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r12 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r0 != r12) goto L4d
                    L45:
                        com.android.systemui.qs.panels.ui.compose.toolbar.EditModeButtonKt$EditModeButton$1$1$1 r0 = new com.android.systemui.qs.panels.ui.compose.toolbar.EditModeButtonKt$EditModeButton$1$1$1
                        r0.<init>(r11)
                        r4.updateRememberedValue(r0)
                    L4d:
                        kotlin.reflect.KFunction r0 = (kotlin.reflect.KFunction) r0
                        r11 = 0
                        r4.end(r11)
                        r11 = 28
                        float r11 = (float) r11
                        androidx.compose.ui.unit.Dp$Companion r12 = androidx.compose.ui.unit.Dp.Companion
                        androidx.compose.foundation.shape.CornerSize r11 = androidx.compose.foundation.shape.CornerSizeKt.m185CornerSize0680j_4(r11)
                        androidx.compose.foundation.shape.RoundedCornerShape r12 = androidx.compose.foundation.shape.RoundedCornerShapeKt.CircleShape
                        androidx.compose.foundation.shape.RoundedCornerShape r6 = new androidx.compose.foundation.shape.RoundedCornerShape
                        r6.<init>(r11, r11, r11, r11)
                        androidx.compose.material3.MaterialTheme r11 = androidx.compose.material3.MaterialTheme.INSTANCE
                        r11.getClass()
                        androidx.compose.material3.ColorScheme r11 = androidx.compose.material3.MaterialTheme.getColorScheme(r4)
                        long r11 = r11.secondary
                        r1 = 24
                        float r1 = (float) r1
                        androidx.compose.foundation.shape.CornerSize r1 = androidx.compose.foundation.shape.CornerSizeKt.m185CornerSize0680j_4(r1)
                        androidx.compose.ui.Modifier r10 = r2
                        androidx.compose.ui.Modifier r5 = com.android.systemui.qs.ui.compose.BorderOnFocusKt.m2920borderOnFocusPOIbLQ4$default(r10, r11, r1)
                        r7 = r0
                        kotlin.jvm.functions.Function0 r7 = (kotlin.jvm.functions.Function0) r7
                        com.android.systemui.qs.panels.ui.compose.toolbar.ComposableSingletons$EditModeButtonKt r10 = com.android.systemui.qs.panels.ui.compose.toolbar.ComposableSingletons$EditModeButtonKt.INSTANCE
                        r10.getClass()
                        androidx.compose.runtime.internal.ComposableLambdaImpl r8 = com.android.systemui.qs.panels.ui.compose.toolbar.ComposableSingletons$EditModeButtonKt.f99lambda1
                        r0 = 1572864(0x180000, float:2.204052E-39)
                        r1 = 28
                        r9 = 0
                        r3 = 0
                        r2 = 0
                        androidx.compose.material3.IconButtonKt.IconButton(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9)
                        boolean r10 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r10 == 0) goto L98
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    L98:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.toolbar.EditModeButtonKt$EditModeButton$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(companion, i) { // from class: com.android.systemui.qs.panels.ui.compose.toolbar.EditModeButtonKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Modifier.Companion f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    EditModeButtonKt.EditModeButton(EditModeButtonViewModel.this, this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
