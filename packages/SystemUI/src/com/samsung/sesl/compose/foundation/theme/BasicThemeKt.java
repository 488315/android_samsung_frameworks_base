package com.samsung.sesl.compose.foundation.theme;

import android.content.res.Configuration;
import android.view.View;
import androidx.compose.foundation.IndicationKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ComputedProvidableCompositionLocal;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import com.samsung.sesl.compose.foundation.RecoilKt;
import com.samsung.sesl.compose.foundation.SeslFeedbackDefaults;
import com.samsung.sesl.compose.foundation.SeslRecoilConfiguration;
import com.samsung.sesl.compose.ui.hapticfeedback.SeslHapticFeedback;
import com.samsung.sesl.compose.ui.hapticfeedback.SeslHapticFeedbackConstants;
import com.samsung.sesl.compose.ui.soundeffect.SeslSoundEffect;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes4.dex */
public abstract class BasicThemeKt {
    public static final ComputedProvidableCompositionLocal LocalSeslInDarkTheme = new ComputedProvidableCompositionLocal(new BasicThemeKt$$ExternalSyntheticLambda1());

    public static final void SeslBasicTheme(final SeslTokenScheme seslTokenScheme, final SeslHapticFeedbackConstants seslHapticFeedbackConstants, final boolean z, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1071977223);
        if ((((composerImpl.changed(seslTokenScheme) ? 4 : 2) | i | (composerImpl.changed(seslHapticFeedbackConstants) ? 32 : 16) | (composerImpl.changed(z) ? 256 : 128)) & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.theme.SeslBasicTheme (BasicTheme.kt:32)");
            }
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = AndroidCompositionLocals_androidKt.LocalView;
            final View view = (View) composerImpl.consume(staticProvidableCompositionLocal);
            composerImpl.startReplaceGroup(104063696);
            final HapticFeedback seslHapticFeedback = seslHapticFeedbackConstants == null ? null : new SeslHapticFeedback((HapticFeedback) composerImpl.consume(CompositionLocalsKt.LocalHapticFeedback), (View) composerImpl.consume(staticProvidableCompositionLocal), seslHapticFeedbackConstants);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(104062952);
            if (seslHapticFeedback == null) {
                seslHapticFeedback = (HapticFeedback) composerImpl.consume(CompositionLocalsKt.LocalHapticFeedback);
            }
            composerImpl.end(false);
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = AndroidCompositionLocals_androidKt.LocalConfiguration;
            Configuration configuration = new Configuration((Configuration) composerImpl.consume(dynamicProvidableCompositionLocal));
            configuration.uiMode = z ? 32 : 16;
            CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{TokenSchemeKt.LocalSeslTokenScheme.defaultProvidedValue$runtime_release(seslTokenScheme), LocalSeslInDarkTheme.defaultProvidedValue$runtime_release(Boolean.valueOf(z)), dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(configuration)}, ComposableLambdaKt.rememberComposableLambda(-1933687225, new Function2() { // from class: com.samsung.sesl.compose.foundation.theme.BasicThemeKt.SeslBasicTheme.1
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
                                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.theme.SeslBasicTheme.<anonymous> (BasicTheme.kt:52)");
                            }
                            SeslFeedbackDefaults.INSTANCE.getClass();
                            long jM3352colorsWaAFU9c = SeslFeedbackDefaults.m3352colorsWaAFU9c(composer2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(1439547926);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new SeslRecoilConfiguration(jM3352colorsWaAFU9c, SeslFeedbackDefaults.feedbackAlpha, null);
                                composerImpl3.updateRememberedValue(objRememberedValue);
                            }
                            composerImpl3.end(false);
                            ProvidedValue[] providedValueArr = {IndicationKt.LocalIndication.defaultProvidedValue$runtime_release(RecoilKt.m3351seslRecoilIndicationbw27NRU$default(null, null, 7)), CompositionLocalsKt.LocalHapticFeedback.defaultProvidedValue$runtime_release(seslHapticFeedback), com.samsung.sesl.compose.ui.platform.CompositionLocalsKt.LocalSeslSoundEffect.defaultProvidedValue$runtime_release(new SeslSoundEffect(view)), RecoilKt.LocalSeslRecoilConfiguration.defaultProvidedValue$runtime_release((SeslRecoilConfiguration) objRememberedValue)};
                            final Function2 function2 = composableLambdaImpl;
                            CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableLambdaKt.rememberComposableLambda(-845878905, new Function2() { // from class: com.samsung.sesl.compose.foundation.theme.BasicThemeKt.SeslBasicTheme.1.1
                                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj3, Object obj4) {
                                    Composer composer3 = (Composer) obj3;
                                    if ((((Number) obj4).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                        if (composerImpl4.getSkipping()) {
                                            composerImpl4.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.theme.SeslBasicTheme.<anonymous>.<anonymous> (BasicTheme.kt:66)");
                                            }
                                            function2.invoke(composer3, 0);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl3), composerImpl3, 56);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
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
            recomposeScopeImplEndRestartGroup.block = new Function2(seslHapticFeedbackConstants, z, composableLambdaImpl, i) { // from class: com.samsung.sesl.compose.foundation.theme.BasicThemeKt$$ExternalSyntheticLambda0
                public final /* synthetic */ SeslHapticFeedbackConstants f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ ComposableLambdaImpl f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(3073);
                    ComposableLambdaImpl composableLambdaImpl2 = this.f$3;
                    BasicThemeKt.SeslBasicTheme(this.f$0, this.f$1, this.f$2, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
