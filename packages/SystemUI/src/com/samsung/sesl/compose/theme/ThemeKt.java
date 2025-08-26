package com.samsung.sesl.compose.theme;

import android.content.Context;
import android.provider.Settings;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.MaterialThemeKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.Typography;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.DeviceFontFamilyName;
import androidx.compose.ui.text.font.DeviceFontFamilyNameFontKt;
import androidx.compose.ui.text.font.FontFamilyKt;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import com.android.systemui.util.SettingsHelper;
import com.samsung.sesl.compose.foundation.CompositionLocalsKt;
import com.samsung.sesl.compose.foundation.theme.BasicThemeKt;
import com.samsung.sesl.compose.foundation.theme.SeslTokenScheme;
import com.samsung.sesl.compose.phone.ui.hapticfeedback.SeslPhoneHapticFeedbackConstantsKt;
import com.samsung.sesl.compose.ui.hapticfeedback.SeslHapticFeedbackConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes4.dex */
public abstract class ThemeKt {
    public static final StaticProvidableCompositionLocal LocalOneUiOpenTheme = new StaticProvidableCompositionLocal(new ThemeKt$$ExternalSyntheticLambda1());

    /* JADX WARN: Removed duplicated region for block: B:16:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SeslTheme(boolean z, SeslColorScheme seslColorScheme, ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i, final int i2) {
        SeslColorScheme seslColorScheme2;
        int i3;
        final boolean zBooleanValue;
        int i4;
        final SeslTokenScheme seslTokenScheme;
        boolean zChanged;
        final ComposableLambdaImpl composableLambdaImpl2;
        final boolean z2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-327431042);
        int i5 = i | 2;
        if ((i2 & 2) == 0) {
            seslColorScheme2 = seslColorScheme;
            int i6 = composerImpl.changed(seslColorScheme2) ? 32 : 16;
            i3 = i5 | i6;
            if ((i3 & 147) == 146 || !composerImpl.getSkipping()) {
                composerImpl.startDefaults();
                if ((i & 1) != 0 || composerImpl.getDefaultsInvalid()) {
                    zBooleanValue = ((Boolean) composerImpl.consume(BasicThemeKt.LocalSeslInDarkTheme)).booleanValue();
                    int i7 = i3 & (-15);
                    if ((i2 & 2) == 0) {
                        seslColorScheme2 = zBooleanValue ? ColorSchemeKt.seslDefaultDarkColorScheme : ColorSchemeKt.seslDefaultLightColorScheme;
                        i4 = i3 & (-127);
                    } else {
                        i4 = i7;
                    }
                } else {
                    composerImpl.skipToGroupEnd();
                    int i8 = i3 & (-15);
                    if ((i2 & 2) != 0) {
                        i8 = i3 & (-127);
                    }
                    i4 = i8;
                    zBooleanValue = z;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.samsung.sesl.compose.theme.SeslTheme (Theme.kt:34)");
                }
                Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
                if (zBooleanValue) {
                    composerImpl.startReplaceGroup(85645890);
                    seslTokenScheme = (SeslTokenScheme) composerImpl.consume(CompositionLocalsKt.LocalSeslPhoneTokenLightScheme);
                    composerImpl.end(false);
                } else {
                    composerImpl.startReplaceGroup(85644354);
                    seslTokenScheme = (SeslTokenScheme) composerImpl.consume(CompositionLocalsKt.LocalSeslPhoneTokenDarkScheme);
                    composerImpl.end(false);
                }
                DeviceFontFamilyName.m761constructorimpl("sec");
                final TextStyle textStyle = new TextStyle(seslTokenScheme.getCommonTokens().mainTextColor, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("sec", null, 14)), (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, new PlatformTextStyle(false), (LineHeightStyle) null, 0, 0, (TextMotion) null, 16252894, (DefaultConstructorMarker) null);
                composerImpl.startReplaceGroup(85657194);
                zChanged = ((((i4 & 112) ^ 48) <= 32 && composerImpl.changed(seslColorScheme2)) || (i4 & 48) == 32) | composerImpl.changed(seslTokenScheme);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChanged) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        long j = seslColorScheme2.primary;
                        if (j == 16) {
                            j = seslTokenScheme.getCommonTokens().primaryColor;
                        }
                        long j2 = j;
                        long j3 = seslColorScheme2.mainText;
                        if (j3 == 16) {
                            j3 = seslTokenScheme.getCommonTokens().mainTextColor;
                        }
                        long j4 = j3;
                        long j5 = seslColorScheme2.subText;
                        if (j5 == 16) {
                            j5 = seslTokenScheme.getCommonTokens().subTextColor;
                        }
                        long j6 = j5;
                        long j7 = seslColorScheme2.pointText;
                        if (j7 == 16) {
                            j7 = seslTokenScheme.getCommonTokens().pointTextColor;
                        }
                        long j8 = j7;
                        long j9 = seslColorScheme2.background;
                        if (j9 == 16) {
                            j9 = seslTokenScheme.getCommonTokens().windowBackgroundColor;
                        }
                        long j10 = j9;
                        long j11 = seslColorScheme2.roundedCorner;
                        if (j11 == 16) {
                            j11 = seslTokenScheme.getCommonTokens().roundedCornerColor;
                        }
                        long j12 = j11;
                        long j13 = seslColorScheme2.ripple;
                        if (j13 == 16) {
                            j13 = seslTokenScheme.getCommonTokens().rippleColor;
                        }
                        SeslColorScheme seslColorScheme3 = new SeslColorScheme(j2, j4, j6, j8, j10, j12, j13, seslColorScheme2.controlNormal, null);
                        composerImpl.updateRememberedValue(seslColorScheme3);
                        objRememberedValue = seslColorScheme3;
                    }
                    SeslColorScheme seslColorScheme4 = (SeslColorScheme) objRememberedValue;
                    composerImpl.end(false);
                    String string = Settings.System.getString(context.getContentResolver(), SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE);
                    if (string == null) {
                        string = "";
                    }
                    composableLambdaImpl2 = composableLambdaImpl;
                    CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{LocalOneUiOpenTheme.defaultProvidedValue$runtime_release(Boolean.valueOf(!StringsKt__StringsKt.isBlank(string))), ColorSchemeKt.LocalSeslColorScheme.defaultProvidedValue$runtime_release(seslColorScheme4)}, ComposableLambdaKt.rememberComposableLambda(129216830, new Function2() { // from class: com.samsung.sesl.compose.theme.ThemeKt.SeslTheme.1
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
                                        ComposerKt.traceEventStart("com.samsung.sesl.compose.theme.SeslTheme.<anonymous> (Theme.kt:79)");
                                    }
                                    SeslHapticFeedbackConstants seslHapticFeedbackConstants = SeslPhoneHapticFeedbackConstantsKt.SeslPhoneHapticFeedbackConstants;
                                    final TextStyle textStyle2 = textStyle;
                                    final Function2 function2 = composableLambdaImpl2;
                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-1927795308, new Function2() { // from class: com.samsung.sesl.compose.theme.ThemeKt.SeslTheme.1.1
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj3, Object obj4) {
                                            Composer composer3 = (Composer) obj3;
                                            if ((((Number) obj4).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                                if (composerImpl3.getSkipping()) {
                                                    composerImpl3.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.samsung.sesl.compose.theme.SeslTheme.<anonymous>.<anonymous> (Theme.kt:84)");
                                                    }
                                                    MaterialTheme.INSTANCE.getClass();
                                                    ColorScheme colorScheme = MaterialTheme.getColorScheme(composer3);
                                                    SeslTheme.INSTANCE.getClass();
                                                    ColorScheme colorSchemeM257copyCXl9yA$default = ColorScheme.m257copyCXl9yA$default(colorScheme, SeslTheme.getColorScheme(composer3).primary, 0L, SeslTheme.getColorScheme(composer3).subText, SeslTheme.getColorScheme(composer3).background, 0L, 0L, 0L, 0L, 0L, 0L, 0L, -8226);
                                                    Typography typography = MaterialTheme.getTypography(composer3);
                                                    Typography typography2 = new Typography(typography.displayLarge, typography.displayMedium, typography.displaySmall, typography.headlineLarge, typography.headlineMedium, typography.headlineSmall, typography.titleLarge, typography.titleMedium, typography.titleSmall, textStyle2, typography.bodyMedium, typography.bodySmall, typography.labelLarge, typography.labelMedium, typography.labelSmall, typography.displayLargeEmphasized, typography.displayMediumEmphasized, typography.displaySmallEmphasized, typography.headlineLargeEmphasized, typography.headlineMediumEmphasized, typography.headlineSmallEmphasized, typography.titleLargeEmphasized, typography.titleMediumEmphasized, typography.titleSmallEmphasized, typography.bodyLargeEmphasized, typography.bodyMediumEmphasized, typography.bodySmallEmphasized, typography.labelLargeEmphasized, typography.labelMediumEmphasized, typography.labelSmallEmphasized);
                                                    final TextStyle textStyle3 = textStyle2;
                                                    final Function2 function22 = function2;
                                                    MaterialThemeKt.MaterialTheme(colorSchemeM257copyCXl9yA$default, null, typography2, ComposableLambdaKt.rememberComposableLambda(908743272, new Function2() { // from class: com.samsung.sesl.compose.theme.ThemeKt.SeslTheme.1.1.1
                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj5, Object obj6) {
                                                            Composer composer4 = (Composer) obj5;
                                                            if ((((Number) obj6).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer4;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("com.samsung.sesl.compose.theme.SeslTheme.<anonymous>.<anonymous>.<anonymous> (Theme.kt:94)");
                                                                    }
                                                                    TextKt.ProvideTextStyle(textStyle3, function22, composer4, 0);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composer3), composer3, 3072, 2);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composer2);
                                    BasicThemeKt.SeslBasicTheme(seslTokenScheme, seslHapticFeedbackConstants, zBooleanValue, composableLambdaImplRememberComposableLambda, composer2, 3072);
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
                    z2 = zBooleanValue;
                }
            } else {
                composerImpl.skipToGroupEnd();
                z2 = z;
                composableLambdaImpl2 = composableLambdaImpl;
            }
            final SeslColorScheme seslColorScheme5 = seslColorScheme2;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2(z2, seslColorScheme5, composableLambdaImpl2, i, i2) { // from class: com.samsung.sesl.compose.theme.ThemeKt$$ExternalSyntheticLambda0
                    public final /* synthetic */ boolean f$0;
                    public final /* synthetic */ SeslColorScheme f$1;
                    public final /* synthetic */ ComposableLambdaImpl f$2;
                    public final /* synthetic */ int f$4;

                    {
                        this.f$4 = i2;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(385);
                        ComposableLambdaImpl composableLambdaImpl3 = this.f$2;
                        int i9 = this.f$4;
                        ThemeKt.SeslTheme(this.f$0, this.f$1, composableLambdaImpl3, (Composer) obj, iUpdateChangedFlags, i9);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        seslColorScheme2 = seslColorScheme;
        i3 = i5 | i6;
        if ((i3 & 147) == 146) {
            composerImpl.startDefaults();
            if ((i & 1) != 0) {
                zBooleanValue = ((Boolean) composerImpl.consume(BasicThemeKt.LocalSeslInDarkTheme)).booleanValue();
                int i72 = i3 & (-15);
                if ((i2 & 2) == 0) {
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                Context context2 = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
                if (zBooleanValue) {
                }
                DeviceFontFamilyName.m761constructorimpl("sec");
                final TextStyle textStyle2 = new TextStyle(seslTokenScheme.getCommonTokens().mainTextColor, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("sec", null, 14)), (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, new PlatformTextStyle(false), (LineHeightStyle) null, 0, 0, (TextMotion) null, 16252894, (DefaultConstructorMarker) null);
                composerImpl.startReplaceGroup(85657194);
                if (((i4 & 112) ^ 48) <= 32) {
                    zChanged = ((((i4 & 112) ^ 48) <= 32 && composerImpl.changed(seslColorScheme2)) || (i4 & 48) == 32) | composerImpl.changed(seslTokenScheme);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChanged) {
                    }
                } else {
                    zChanged = ((((i4 & 112) ^ 48) <= 32 && composerImpl.changed(seslColorScheme2)) || (i4 & 48) == 32) | composerImpl.changed(seslTokenScheme);
                    Object objRememberedValue22 = composerImpl.rememberedValue();
                    if (!zChanged) {
                    }
                }
            }
        }
        final SeslColorScheme seslColorScheme52 = seslColorScheme2;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
