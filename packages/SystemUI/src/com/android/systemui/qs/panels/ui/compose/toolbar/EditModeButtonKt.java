package com.android.systemui.qs.panels.ui.compose.toolbar;

import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.IconButtonKt;
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
import androidx.compose.ui.unit.Dp;
import com.android.systemui.qs.panels.ui.viewmodel.toolbar.EditModeButtonViewModel;
import com.android.systemui.qs.ui.compose.BorderOnFocusKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.KFunction;

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
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(MaterialTheme.getColorScheme(composerImpl).onSurface)), ComposableLambdaKt.rememberComposableLambda(241794011, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.toolbar.EditModeButtonKt.EditModeButton.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0045  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.toolbar.EditModeButton.<anonymous> (EditModeButton.kt:40)");
                            }
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(-567853395);
                            EditModeButtonViewModel editModeButtonViewModel2 = editModeButtonViewModel;
                            boolean zChangedInstance = composerImpl3.changedInstance(editModeButtonViewModel2);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            if (!zChangedInstance) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new EditModeButtonKt$EditModeButton$1$1$1(editModeButtonViewModel2);
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                Dp.Companion companion2 = Dp.Companion;
                                CornerSize cornerSizeM186CornerSize0680j_4 = CornerSizeKt.m186CornerSize0680j_4(28);
                                RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
                                RoundedCornerShape roundedCornerShape2 = new RoundedCornerShape(cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4);
                                MaterialTheme.INSTANCE.getClass();
                                long j = MaterialTheme.getColorScheme(composerImpl3).secondary;
                                CornerSize cornerSizeM186CornerSize0680j_42 = CornerSizeKt.m186CornerSize0680j_4(24);
                                ComposableSingletons$EditModeButtonKt.INSTANCE.getClass();
                                IconButtonKt.IconButton(1572864, 28, null, null, composerImpl3, BorderOnFocusKt.m2937borderOnFocusPOIbLQ4$default(companion, j, cornerSizeM186CornerSize0680j_42), roundedCornerShape2, (Function0) ((KFunction) objRememberedValue), ComposableSingletons$EditModeButtonKt.f99lambda1, false);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(companion, i) { // from class: com.android.systemui.qs.panels.ui.compose.toolbar.EditModeButtonKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Modifier.Companion f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    EditModeButtonKt.EditModeButton(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
