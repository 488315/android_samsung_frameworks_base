package androidx.compose.material3;

import androidx.compose.foundation.IndicationKt;
import androidx.compose.foundation.IndicationNodeFactory;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class MaterialThemeKt {
    static {
        new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.material3.MaterialThemeKt$LocalUsingExpressiveTheme$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Boolean.FALSE;
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void MaterialTheme(ColorScheme colorScheme, Shapes shapes, Typography typography, Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        final Function2 function22;
        final ColorScheme colorScheme2;
        final Shapes shapes2;
        final Typography typography2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2127166334);
        if ((i & 6) == 0) {
            i3 = (((i2 & 1) == 0 && composerImpl.changed(colorScheme)) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= ((i2 & 2) == 0 && composerImpl.changed(shapes)) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= ((i2 & 4) == 0 && composerImpl.changed(typography)) ? 256 : 128;
        }
        if ((i2 & 8) != 0) {
            i3 |= 3072;
        } else if ((i & 3072) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 2048 : 1024;
        }
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function22 = function2;
            typography2 = typography;
            shapes2 = shapes;
            colorScheme2 = colorScheme;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if ((i2 & 1) != 0) {
                    MaterialTheme.INSTANCE.getClass();
                    colorScheme = MaterialTheme.getColorScheme(composerImpl);
                    i3 &= -15;
                }
                if ((i2 & 2) != 0) {
                    MaterialTheme.INSTANCE.getClass();
                    shapes = MaterialTheme.getShapes(composerImpl);
                    i3 &= -113;
                }
                if ((i2 & 4) != 0) {
                    MaterialTheme.INSTANCE.getClass();
                    typography = MaterialTheme.getTypography(composerImpl);
                    i3 &= -897;
                }
                ColorScheme colorScheme3 = colorScheme;
                Shapes shapes3 = shapes;
                Typography typography3 = typography;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.material3.MaterialTheme (MaterialTheme.kt:57)");
                }
                MaterialTheme.INSTANCE.getClass();
                int i4 = i3 << 3;
                MaterialTheme(colorScheme3, MaterialTheme.getMotionScheme(composerImpl), shapes3, typography3, function2, composerImpl, (i3 & 14) | (i4 & 896) | (i4 & 7168) | (i4 & 57344), 0);
                function22 = function2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                colorScheme2 = colorScheme3;
                shapes2 = shapes3;
                typography2 = typography3;
            } else {
                composerImpl.skipToGroupEnd();
                if ((i2 & 1) != 0) {
                    i3 &= -15;
                }
                if ((i2 & 2) != 0) {
                    i3 &= -113;
                }
                if ((i2 & 4) != 0) {
                    i3 &= -897;
                }
                ColorScheme colorScheme32 = colorScheme;
                Shapes shapes32 = shapes;
                Typography typography32 = typography;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                MaterialTheme.INSTANCE.getClass();
                int i42 = i3 << 3;
                MaterialTheme(colorScheme32, MaterialTheme.getMotionScheme(composerImpl), shapes32, typography32, function2, composerImpl, (i3 & 14) | (i42 & 896) | (i42 & 7168) | (i42 & 57344), 0);
                function22 = function2;
                if (ComposerKt.isTraceInProgress()) {
                }
                colorScheme2 = colorScheme32;
                shapes2 = shapes32;
                typography2 = typography32;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.MaterialThemeKt.MaterialTheme.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    MaterialThemeKt.MaterialTheme(colorScheme2, shapes2, typography2, function22, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:93:0x0127  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void MaterialTheme(ColorScheme colorScheme, MotionScheme motionScheme, Shapes shapes, Typography typography, final Function2 function2, Composer composer, final int i, final int i2) {
        final ColorScheme colorScheme2;
        int i3;
        MotionScheme motionScheme2;
        Shapes shapes2;
        final Typography typography2;
        final MotionScheme motionScheme3;
        final Shapes shapes3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1317723617);
        if ((i & 6) == 0) {
            if ((i2 & 1) == 0) {
                colorScheme2 = colorScheme;
                int i4 = composerImpl.changed(colorScheme2) ? 4 : 2;
                i3 = i4 | i;
            } else {
                colorScheme2 = colorScheme;
            }
            i3 = i4 | i;
        } else {
            colorScheme2 = colorScheme;
            i3 = i;
        }
        if ((i & 48) == 0) {
            if ((i2 & 2) == 0) {
                motionScheme2 = motionScheme;
                int i5 = composerImpl.changed(motionScheme2) ? 32 : 16;
                i3 |= i5;
            } else {
                motionScheme2 = motionScheme;
            }
            i3 |= i5;
        } else {
            motionScheme2 = motionScheme;
        }
        if ((i & 384) == 0) {
            if ((i2 & 4) == 0) {
                shapes2 = shapes;
                int i6 = composerImpl.changed(shapes2) ? 256 : 128;
                i3 |= i6;
            } else {
                shapes2 = shapes;
            }
            i3 |= i6;
        } else {
            shapes2 = shapes;
        }
        if ((i & 3072) == 0) {
            if ((i2 & 8) == 0) {
                typography2 = typography;
                int i7 = composerImpl.changed(typography2) ? 2048 : 1024;
                i3 |= i7;
            } else {
                typography2 = typography;
            }
            i3 |= i7;
        } else {
            typography2 = typography;
        }
        if ((i2 & 16) != 0) {
            i3 |= 24576;
        } else if ((i & 24576) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i3 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            motionScheme3 = motionScheme2;
            shapes3 = shapes2;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if ((i2 & 1) != 0) {
                    MaterialTheme.INSTANCE.getClass();
                    colorScheme2 = MaterialTheme.getColorScheme(composerImpl);
                }
                if ((i2 & 2) != 0) {
                    MaterialTheme.INSTANCE.getClass();
                    motionScheme3 = MaterialTheme.getMotionScheme(composerImpl);
                } else {
                    motionScheme3 = motionScheme2;
                }
                if ((i2 & 4) != 0) {
                    MaterialTheme.INSTANCE.getClass();
                    shapes3 = MaterialTheme.getShapes(composerImpl);
                } else {
                    shapes3 = shapes2;
                }
                if ((i2 & 8) != 0) {
                    MaterialTheme.INSTANCE.getClass();
                    typography2 = MaterialTheme.getTypography(composerImpl);
                }
            } else {
                composerImpl.skipToGroupEnd();
                motionScheme3 = motionScheme2;
                shapes3 = shapes2;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.MaterialTheme (MaterialTheme.kt:94)");
            }
            IndicationNodeFactory indicationNodeFactoryM281rippleH2RKhps$default = RippleKt.m281rippleH2RKhps$default(0.0f, false, 7);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.rememberTextSelectionColors (MaterialTheme.kt:205)");
            }
            long j = colorScheme2.primary;
            boolean zChanged = composerImpl.changed(j);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new TextSelectionColors(j, ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.4f, Color.m461getColorSpaceimpl(j)), null);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                TextSelectionColors textSelectionColors = (TextSelectionColors) objRememberedValue;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{ColorSchemeKt.LocalColorScheme.defaultProvidedValue$runtime_release(colorScheme2), MotionSchemeKt.LocalMotionScheme.defaultProvidedValue$runtime_release(motionScheme3), IndicationKt.LocalIndication.defaultProvidedValue$runtime_release(indicationNodeFactoryM281rippleH2RKhps$default), ShapesKt.LocalShapes.defaultProvidedValue$runtime_release(shapes3), TextSelectionColorsKt.LocalTextSelectionColors.defaultProvidedValue$runtime_release(textSelectionColors), TypographyKt.LocalTypography.defaultProvidedValue$runtime_release(typography2)}, ComposableLambdaKt.rememberComposableLambda(-2097082079, new Function2() { // from class: androidx.compose.material3.MaterialThemeKt.MaterialTheme.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

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
                                    ComposerKt.traceEventStart("androidx.compose.material3.MaterialTheme.<anonymous> (MaterialTheme.kt:105)");
                                }
                                TextKt.ProvideTextStyle(typography2.bodyLarge, function2, composer2, 0);
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
        }
        final Typography typography3 = typography2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.MaterialThemeKt.MaterialTheme.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    MaterialThemeKt.MaterialTheme(colorScheme2, motionScheme3, shapes3, typography3, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
