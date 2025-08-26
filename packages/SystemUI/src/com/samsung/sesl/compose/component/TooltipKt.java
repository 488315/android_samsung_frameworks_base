package com.samsung.sesl.compose.component;

import android.content.Context;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.DarkThemeKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TooltipState;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ShadowKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.InspectionModeKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.window.PopupPositionProvider;
import androidx.core.graphics.Insets;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.component.tokens.SeslPaletteTokens;
import com.samsung.sesl.compose.foundation.shape.SeslRoundedCornerShape;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public abstract class TooltipKt {
    public static final void SeslTooltipBox(final TooltipState tooltipState, final String str, final Modifier.Companion companion, PopupPositionProvider popupPositionProvider, ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2, Composer composer, final int i) {
        int i2;
        int i3;
        int i4;
        PopupPositionProvider popupPositionProvider2;
        ComposableLambdaImpl composableLambdaImplRememberComposableLambda;
        final PopupPositionProvider popupPositionProvider3;
        final ComposableLambdaImpl composableLambdaImpl3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1106599016);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(tooltipState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(str) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(companion) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(false) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= 8192;
        }
        int i5 = i2 | 196608;
        if ((i & 1572864) == 0) {
            i5 |= composerImpl.changedInstance(composableLambdaImpl2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((599187 & i5) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            popupPositionProvider3 = popupPositionProvider;
            composableLambdaImpl3 = composableLambdaImpl;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                SeslTooltipDefaults.INSTANCE.getClass();
                composerImpl.startReplaceGroup(-94680117);
                Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTooltipDefaults.rememberSeslTooltipPositionProvider (Tooltip.kt:210)");
                }
                composerImpl.startReplaceGroup(-675235073);
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    i3 = 1572864;
                    objRememberedValue = new SeslTooltipPositionProvider(density.mo52roundToPx0680j_4(SeslTooltipDefaults.offset), Insets.of(density.mo52roundToPx0680j_4(SeslTooltipDefaults.popupAreaLeftInsets), density.mo52roundToPx0680j_4(SeslTooltipDefaults.popupAreaTopInsets), density.mo52roundToPx0680j_4(SeslTooltipDefaults.popupAreaRightInsets), density.mo52roundToPx0680j_4(SeslTooltipDefaults.popupAreaBottomInsets)), SeslTooltipDefaults.NoneCalculatePositionDelta);
                    composerImpl.updateRememberedValue(objRememberedValue);
                } else {
                    i3 = 1572864;
                }
                PopupPositionProvider popupPositionProvider4 = (PopupPositionProvider) objRememberedValue;
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                i4 = i5 & (-57345);
                popupPositionProvider2 = popupPositionProvider4;
                composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-1629337090, new Function3() { // from class: com.samsung.sesl.compose.component.TooltipKt.SeslTooltipBox.1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        Composer composer2 = (Composer) obj2;
                        ((Number) obj3).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTooltipBox.<anonymous> (Tooltip.kt:48)");
                        }
                        TooltipKt.SeslTooltipContent(str, (SeslTooltipColor) null, composer2, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl);
            } else {
                composerImpl.skipToGroupEnd();
                i4 = i5 & (-57345);
                popupPositionProvider2 = popupPositionProvider;
                composableLambdaImplRememberComposableLambda = composableLambdaImpl;
                i3 = 1572864;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTooltipBox (Tooltip.kt:53)");
            }
            int i6 = i3 | ((i4 >> 12) & 112) | ((i4 << 6) & 896);
            int i7 = i4 << 3;
            androidx.compose.material3.TooltipKt.TooltipBox(popupPositionProvider2, composableLambdaImplRememberComposableLambda, tooltipState, companion, false, false, ComposableLambdaKt.rememberComposableLambda(-83604733, new Function2() { // from class: com.samsung.sesl.compose.component.TooltipKt.SeslTooltipBox.2
                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
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
                                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTooltipBox.<anonymous> (Tooltip.kt:61)");
                            }
                            composableLambdaImpl2.invoke(composer2, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, i6 | (i7 & 7168) | (i7 & 57344), 32);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            popupPositionProvider3 = popupPositionProvider2;
            composableLambdaImpl3 = composableLambdaImplRememberComposableLambda;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.TooltipKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    TooltipState tooltipState2 = tooltipState;
                    String str2 = str;
                    ComposableLambdaImpl composableLambdaImpl4 = composableLambdaImpl2;
                    TooltipKt.SeslTooltipBox(tooltipState2, str2, companion, popupPositionProvider3, composableLambdaImpl3, composableLambdaImpl4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SeslTooltipContent(final String str, final SeslTooltipColor seslTooltipColor, Composer composer, final int i) {
        long j;
        long j2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1668171429);
        if ((((composerImpl.changed(str) ? 4 : 2) | i | 16) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                SeslTooltipDefaults.INSTANCE.getClass();
                composerImpl.startReplaceGroup(-883493920);
                if (DarkThemeKt.isSystemInDarkTheme(composerImpl)) {
                    SeslPaletteTokens.INSTANCE.getClass();
                    j = SeslPaletteTokens.GRAYSCALE_D6;
                } else {
                    SeslPaletteTokens.INSTANCE.getClass();
                    j = SeslPaletteTokens.GRAYSCALE_L1;
                }
                long j3 = j;
                if (DarkThemeKt.isSystemInDarkTheme(composerImpl)) {
                    SeslPaletteTokens.INSTANCE.getClass();
                    j2 = SeslPaletteTokens.GRAY_TEXT_D1;
                } else {
                    SeslPaletteTokens.INSTANCE.getClass();
                    j2 = SeslPaletteTokens.GRAY_TEXT_L2;
                }
                long j4 = j2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTooltipDefaults.defaultColors (Tooltip.kt:203)");
                }
                SeslTooltipColor seslTooltipColor2 = new SeslTooltipColor(j3, j4, null);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                seslTooltipColor = seslTooltipColor2;
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTooltipContent (Tooltip.kt:70)");
            }
            SeslTooltipContent(seslTooltipColor, ComposableLambdaKt.rememberComposableLambda(1811549622, new Function2() { // from class: com.samsung.sesl.compose.component.TooltipKt.SeslTooltipContent.1
                /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
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
                                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTooltipContent.<anonymous> (Tooltip.kt:74)");
                            }
                            TextKt.m317Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer2, 0, 0, 131070);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 48);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(str, seslTooltipColor, i) { // from class: com.samsung.sesl.compose.component.TooltipKt$$ExternalSyntheticLambda1
                public final /* synthetic */ String f$0;
                public final /* synthetic */ SeslTooltipColor f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    TooltipKt.SeslTooltipContent(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SeslTooltipContent(final SeslTooltipColor seslTooltipColor, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1850216539);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(seslTooltipColor) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(composableLambdaImpl) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTooltipContent (Tooltip.kt:85)");
            }
            Modifier.Companion companion = Modifier.Companion;
            long j = seslTooltipColor.containerColor;
            SeslTooltipDefaults.INSTANCE.getClass();
            SeslRoundedCornerShape seslRoundedCornerShape = SeslTooltipDefaults.shape;
            Modifier modifierM126paddingVpY3zN4 = PaddingKt.m126paddingVpY3zN4(ShadowKt.m366shadows4CzXII$default(BackgroundKt.m26backgroundbw27NRU(companion, j, seslRoundedCornerShape), SeslTooltipDefaults.elevation, seslRoundedCornerShape, SeslTooltipDefaults.elevationColor, 12), SeslTooltipDefaults.internalPaddingHorizontal, SeslTooltipDefaults.internalPaddingVertical);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM126paddingVpY3zN4);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier != null) {
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
                final long j2 = seslTooltipColor.contentColor;
                CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(j2)), ComposableLambdaKt.rememberComposableLambda(-1232645215, new Function2() { // from class: com.samsung.sesl.compose.component.TooltipKt$SeslTooltipContent$3$1
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
                                    ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTooltipContent.<anonymous>.<anonymous> (Tooltip.kt:107)");
                                }
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                composerImpl3.startReplaceGroup(177993033);
                                StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
                                Density density = (Density) composerImpl3.consume(staticProvidableCompositionLocal);
                                float fontScale = density.getFontScale();
                                if (fontScale > 1.3f) {
                                    fontScale = 1.3f;
                                }
                                composerImpl3.startReplaceGroup(-1246172339);
                                StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = AndroidCompositionLocals_androidKt.LocalContext;
                                Context context = (Context) composerImpl3.consume(staticProvidableCompositionLocal2);
                                if (((Boolean) composerImpl3.consume(InspectionModeKt.LocalInspectionMode)).booleanValue()) {
                                    context.getResources().getConfiguration().fontScale = fontScale;
                                }
                                composerImpl3.end(false);
                                ProvidedValue[] providedValueArr = {staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(DensityKt.Density(density.getDensity(), fontScale)), staticProvidableCompositionLocal2.defaultProvidedValue$runtime_release(context)};
                                final long j3 = j2;
                                final Function2 function22 = composableLambdaImpl;
                                CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableLambdaKt.rememberComposableLambda(-1027159543, new Function2() { // from class: com.samsung.sesl.compose.component.TooltipKt$SeslTooltipContent$3$1$invoke$$inlined$SeslUpToLarge$1
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
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
                                                    ComposerKt.traceEventStart("com.samsung.sesl.compose.utils.SeslUpToLarge.<anonymous> (UptoLarge.kt:42)");
                                                }
                                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                composerImpl5.startReplaceGroup(1450881719);
                                                SeslTooltipDefaults.INSTANCE.getClass();
                                                TextKt.ProvideTextStyle(TextStyle.m756copyp1EtxEg$default(SeslTooltipDefaults.textStyle, j3, 0L, null, null, 0L, 0, 0L, null, null, 0, 16777214), function22, composerImpl5, 0);
                                                composerImpl5.end(false);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl3), composerImpl3, 56);
                                composerImpl3.end(false);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 56);
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            } else {
                ComposablesKt.invalidApplier();
                throw null;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.TooltipKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                    TooltipKt.SeslTooltipContent(seslTooltipColor, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
