package com.android.compose.theme;

import android.content.Context;
import androidx.compose.foundation.DarkThemeKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.DynamicTonalPaletteKt;
import androidx.compose.material3.MaterialThemeKt;
import androidx.compose.material3.Typography;
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass;
import androidx.compose.material3.windowsizeclass.WindowSizeClass;
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass;
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
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import androidx.window.layout.WindowMetricsCalculator;
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.typography.TypeScaleTokens;
import com.android.compose.theme.typography.TypefaceNames;
import com.android.compose.theme.typography.TypefaceTokens;
import com.android.compose.theme.typography.TypographyTokens;
import com.android.compose.theme.typography.VariableFontTypeScaleEmphasizedTokens;
import com.android.compose.windowsizeclass.WindowSizeClassKt;
import com.android.internal.R;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class PlatformThemeKt {
    /* JADX WARN: Removed duplicated region for block: B:33:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02ec  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void PlatformTheme(boolean z, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i, final int i2) {
        boolean zIsSystemInDarkTheme;
        int i3;
        final boolean z2;
        boolean zChanged;
        ColorScheme colorSchemeM257copyCXl9yA$default;
        int i4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1433195110);
        if ((i & 6) == 0) {
            if ((i2 & 1) == 0) {
                zIsSystemInDarkTheme = z;
                int i5 = composerImpl.changed(zIsSystemInDarkTheme) ? 4 : 2;
                i3 = i5 | i;
            } else {
                zIsSystemInDarkTheme = z;
            }
            i3 = i5 | i;
        } else {
            zIsSystemInDarkTheme = z;
            i3 = i;
        }
        if ((i3 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            z2 = zIsSystemInDarkTheme;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if ((i2 & 1) != 0) {
                    zIsSystemInDarkTheme = DarkThemeKt.isSystemInDarkTheme(composerImpl);
                    i3 &= -15;
                }
                z2 = zIsSystemInDarkTheme;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.compose.theme.PlatformTheme (PlatformTheme.kt:41)");
                }
                StaticProvidableCompositionLocal staticProvidableCompositionLocal = AndroidCompositionLocals_androidKt.LocalContext;
                Context context = (Context) composerImpl.consume(staticProvidableCompositionLocal);
                composerImpl.startReplaceGroup(459493941);
                int i6 = (i3 & 14) ^ 6;
                zChanged = composerImpl.changed(context) | ((i6 <= 4 && composerImpl.changed(z2)) || (i3 & 6) == 4);
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion companion = Composer.Companion;
                if (!zChanged) {
                    companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        if (z2) {
                            ColorScheme colorSchemeDynamicDarkColorScheme = DynamicTonalPaletteKt.dynamicDarkColorScheme(context);
                            AndroidColorScheme.Companion companion2 = AndroidColorScheme.Companion;
                            int i7 = R.color.system_inverse_surface_dark;
                            companion2.getClass();
                            colorSchemeM257copyCXl9yA$default = ColorScheme.m257copyCXl9yA$default(colorSchemeDynamicDarkColorScheme, 0L, androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(R.color.system_inverse_primary_dark, context.getTheme())), 0L, 0L, 0L, androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(i7, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(R.color.system_inverse_on_surface_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_error_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_on_error_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_error_container_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_on_error_container_dark, context.getTheme())), -66060305);
                        } else {
                            ColorScheme colorSchemeDynamicLightColorScheme = DynamicTonalPaletteKt.dynamicLightColorScheme(context);
                            AndroidColorScheme.Companion companion3 = AndroidColorScheme.Companion;
                            int i8 = R.color.system_inverse_surface_light;
                            companion3.getClass();
                            colorSchemeM257copyCXl9yA$default = ColorScheme.m257copyCXl9yA$default(colorSchemeDynamicLightColorScheme, 0L, androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(R.color.system_inverse_primary_light, context.getTheme())), 0L, 0L, 0L, androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(i8, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(R.color.system_inverse_on_surface_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_error_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_on_error_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_error_container_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_on_error_container_light, context.getTheme())), -66060305);
                        }
                        objRememberedValue = colorSchemeM257copyCXl9yA$default;
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    ColorScheme colorScheme = (ColorScheme) objRememberedValue;
                    composerImpl.end(false);
                    composerImpl.startReplaceGroup(459497319);
                    boolean zChanged2 = ((i6 > 4 && composerImpl.changed(z2)) || (i3 & 6) == 4) | composerImpl.changed(context);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChanged2) {
                        companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            AndroidColorScheme.Companion.getClass();
                            AndroidColorScheme androidColorScheme = new AndroidColorScheme(androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_primary_fixed, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_primary_fixed_dim, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_on_primary_fixed, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_on_primary_fixed_variant, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_secondary_fixed, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_secondary_fixed_dim, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_on_secondary_fixed, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_on_secondary_fixed_variant, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_tertiary_fixed, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_tertiary_fixed_dim, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_on_tertiary_fixed, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.system_on_tertiary_fixed_variant, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.dim_foreground_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.dim_foreground_light_disabled, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.dim_foreground_light_inverse, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.dim_foreground_light_inverse_disabled, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.edge_effect_device_default_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.edge_effect_device_default_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.error_color_device_default_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.error_color_device_default_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.error_color_material_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.error_color_material_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.facelock_spotlight_mask, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.floating_popup_divider_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.floating_popup_divider_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.foreground_device_default_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.foreground_device_default_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.foreground_material_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.foreground_material_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.global_actions_container_background, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.group_button_dialog_focused_holo_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.group_button_dialog_focused_holo_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.group_button_dialog_pressed_holo_dark, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(android.R.color.group_button_dialog_pressed_holo_light, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(17171600, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(17171602, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(17171604, context.getTheme())), androidx.compose.ui.graphics.ColorKt.Color(context.getResources().getColor(17171606, context.getTheme())), null);
                            composerImpl.updateRememberedValue(androidColorScheme);
                            objRememberedValue2 = androidColorScheme;
                        }
                        final AndroidColorScheme androidColorScheme2 = (AndroidColorScheme) objRememberedValue2;
                        composerImpl.end(false);
                        composerImpl.startReplaceGroup(459500089);
                        boolean zChanged3 = composerImpl.changed(context);
                        Object objRememberedValue3 = composerImpl.rememberedValue();
                        if (!zChanged3) {
                            companion.getClass();
                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                TypefaceNames.Companion.getClass();
                                objRememberedValue3 = TypefaceNames.Companion.get(context);
                                composerImpl.updateRememberedValue(objRememberedValue3);
                            }
                            TypefaceNames typefaceNames = (TypefaceNames) objRememberedValue3;
                            composerImpl.end(false);
                            composerImpl.startReplaceGroup(459502466);
                            boolean zChanged4 = composerImpl.changed(typefaceNames);
                            Object objRememberedValue4 = composerImpl.rememberedValue();
                            if (!zChanged4) {
                                companion.getClass();
                                if (objRememberedValue4 == Composer.Companion.Empty) {
                                    objRememberedValue4 = new TypefaceTokens(typefaceNames);
                                    composerImpl.updateRememberedValue(objRememberedValue4);
                                }
                                TypefaceTokens typefaceTokens = (TypefaceTokens) objRememberedValue4;
                                composerImpl.end(false);
                                composerImpl.startReplaceGroup(459505455);
                                boolean zChanged5 = composerImpl.changed(typefaceTokens);
                                Object objRememberedValue5 = composerImpl.rememberedValue();
                                if (!zChanged5) {
                                    companion.getClass();
                                    if (objRememberedValue5 == Composer.Companion.Empty) {
                                        TypographyTokens typographyTokens = new TypographyTokens(new TypeScaleTokens(typefaceTokens), new VariableFontTypeScaleEmphasizedTokens(typefaceTokens));
                                        Typography typography = new Typography(typographyTokens.displayLarge, typographyTokens.displayMedium, typographyTokens.displaySmall, typographyTokens.headlineLarge, typographyTokens.headlineMedium, typographyTokens.headlineSmall, typographyTokens.titleLarge, typographyTokens.titleMedium, typographyTokens.titleSmall, typographyTokens.bodyLarge, typographyTokens.bodyMedium, typographyTokens.bodySmall, typographyTokens.labelLarge, typographyTokens.labelMedium, typographyTokens.labelSmall, typographyTokens.displayLargeEmphasized, typographyTokens.displayMediumEmphasized, typographyTokens.displaySmallEmphasized, typographyTokens.headlineLargeEmphasized, typographyTokens.headlineMediumEmphasized, typographyTokens.headlineSmallEmphasized, typographyTokens.titleLargeEmphasized, typographyTokens.titleMediumEmphasized, typographyTokens.titleSmallEmphasized, typographyTokens.bodyLargeEmphasized, typographyTokens.bodyMediumEmphasized, typographyTokens.bodySmallEmphasized, typographyTokens.labelLargeEmphasized, typographyTokens.labelMediumEmphasized, typographyTokens.labelSmallEmphasized);
                                        composerImpl.updateRememberedValue(typography);
                                        objRememberedValue5 = typography;
                                    }
                                    Typography typography2 = (Typography) objRememberedValue5;
                                    composerImpl.end(false);
                                    StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = WindowSizeClassKt.LocalWindowSizeClass;
                                    composerImpl.startReplaceGroup(-702298760);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.compose.windowsizeclass.calculateWindowSizeClass (WindowSizeClass.kt:38)");
                                    }
                                    composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration);
                                    Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
                                    Context context2 = (Context) composerImpl.consume(staticProvidableCompositionLocal);
                                    WindowMetricsCalculator.Companion.getClass();
                                    long jMo56toDpSizekrfVVM = density.mo56toDpSizekrfVVM(RectHelper_androidKt.toComposeRect(WindowMetricsCalculator.Companion.getOrCreate().computeCurrentWindowMetrics(context2)._bounds.toRect()).m410getSizeNHjbRc());
                                    WindowSizeClass.Companion companion4 = WindowSizeClass.Companion;
                                    WindowWidthSizeClass.Companion.getClass();
                                    Set set = WindowWidthSizeClass.DefaultSizeClasses;
                                    WindowHeightSizeClass.Companion.getClass();
                                    Set set2 = WindowHeightSizeClass.DefaultSizeClasses;
                                    companion4.getClass();
                                    float fM847getWidthD9Ej5fM = DpSize.m847getWidthD9Ej5fM(jMo56toDpSizekrfVVM);
                                    float f = 0;
                                    Dp.Companion companion5 = Dp.Companion;
                                    if (Float.compare(fM847getWidthD9Ej5fM, f) < 0) {
                                        throw new IllegalArgumentException("Width must not be negative");
                                    }
                                    if (set.isEmpty()) {
                                        throw new IllegalArgumentException("Must support at least one size class");
                                    }
                                    List list = WindowWidthSizeClass.AllSizeClassList;
                                    int size = list.size();
                                    int i9 = 0;
                                    int i10 = 0;
                                    while (true) {
                                        if (i9 >= size) {
                                            i4 = i10;
                                            break;
                                        }
                                        List list2 = list;
                                        int i11 = ((WindowWidthSizeClass) list.get(i9)).value;
                                        if (set.contains(WindowWidthSizeClass.m328boximpl(i11))) {
                                            WindowWidthSizeClass.Companion.getClass();
                                            if (Float.compare(fM847getWidthD9Ej5fM, WindowWidthSizeClass.Companion.m330breakpointfhkHA5s(i11)) >= 0) {
                                                i4 = i11;
                                                break;
                                            }
                                            i10 = i11;
                                        }
                                        i9++;
                                        list = list2;
                                    }
                                    WindowHeightSizeClass.Companion companion6 = WindowHeightSizeClass.Companion;
                                    float fM846getHeightD9Ej5fM = DpSize.m846getHeightD9Ej5fM(jMo56toDpSizekrfVVM);
                                    companion6.getClass();
                                    Dp.Companion companion7 = Dp.Companion;
                                    if (Float.compare(fM846getHeightD9Ej5fM, f) < 0) {
                                        throw new IllegalArgumentException("Width must not be negative");
                                    }
                                    if (set2.isEmpty()) {
                                        throw new IllegalArgumentException("Must support at least one size class");
                                    }
                                    int i12 = WindowHeightSizeClass.Expanded;
                                    List list3 = WindowHeightSizeClass.AllSizeClassList;
                                    int size2 = list3.size();
                                    int i13 = i12;
                                    int i14 = 0;
                                    while (true) {
                                        if (i14 >= size2) {
                                            break;
                                        }
                                        int i15 = ((WindowHeightSizeClass) list3.get(i14)).value;
                                        if (set2.contains(WindowHeightSizeClass.m325boximpl(i15))) {
                                            WindowHeightSizeClass.Companion.getClass();
                                            if (Float.compare(fM846getHeightD9Ej5fM, WindowHeightSizeClass.Companion.m327breakpointsr04XMo(i15)) >= 0) {
                                                i13 = i15;
                                                break;
                                            }
                                            i13 = i15;
                                        }
                                        i14++;
                                    }
                                    final WindowSizeClass windowSizeClass = new WindowSizeClass(i4, i13, null);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    composerImpl.end(false);
                                    MaterialThemeKt.MaterialTheme(colorScheme, null, typography2, ComposableLambdaKt.rememberComposableLambda(2058174790, new Function2() { // from class: com.android.compose.theme.PlatformThemeKt.PlatformTheme.1
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
                                                        ComposerKt.traceEventStart("com.android.compose.theme.PlatformTheme.<anonymous> (PlatformTheme.kt:60)");
                                                    }
                                                    CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{AndroidColorSchemeKt.LocalAndroidColorScheme.defaultProvidedValue$runtime_release(androidColorScheme2), WindowSizeClassKt.LocalWindowSizeClass.defaultProvidedValue$runtime_release(windowSizeClass)}, composableLambdaImpl, composer2, 8);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl), composerImpl, 3072, 2);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                composerImpl.skipToGroupEnd();
                if ((i2 & 1) != 0) {
                    i3 &= -15;
                }
                z2 = zIsSystemInDarkTheme;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                StaticProvidableCompositionLocal staticProvidableCompositionLocal3 = AndroidCompositionLocals_androidKt.LocalContext;
                Context context3 = (Context) composerImpl.consume(staticProvidableCompositionLocal3);
                composerImpl.startReplaceGroup(459493941);
                int i62 = (i3 & 14) ^ 6;
                if (i62 <= 4) {
                    zChanged = composerImpl.changed(context3) | ((i62 <= 4 && composerImpl.changed(z2)) || (i3 & 6) == 4);
                    Object objRememberedValue6 = composerImpl.rememberedValue();
                    Composer.Companion companion8 = Composer.Companion;
                    if (!zChanged) {
                    }
                } else {
                    zChanged = composerImpl.changed(context3) | ((i62 <= 4 && composerImpl.changed(z2)) || (i3 & 6) == 4);
                    Object objRememberedValue62 = composerImpl.rememberedValue();
                    Composer.Companion companion82 = Composer.Companion;
                    if (!zChanged) {
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.compose.theme.PlatformThemeKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                    int i16 = i2;
                    PlatformThemeKt.PlatformTheme(z2, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags, i16);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
