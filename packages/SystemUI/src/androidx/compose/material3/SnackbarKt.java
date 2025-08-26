package androidx.compose.material3;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.AlignmentLineKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.tokens.SnackbarTokens;
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
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.layout.HorizontalAlignmentLine;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class SnackbarKt {
    public static final float ContainerMaxWidth;
    public static final float HeightToFirstLine;
    public static final float HorizontalSpacing;
    public static final float HorizontalSpacingButtonSide;
    public static final float LongButtonVerticalOffset;
    public static final float SeparateButtonExtraY;
    public static final float SnackbarVerticalPadding;
    public static final float TextEndExtraSpacing;

    static {
        float f = VolteConstants.ErrorCode.BUSY_EVERYWHERE;
        Dp.Companion companion = Dp.Companion;
        ContainerMaxWidth = f;
        HeightToFirstLine = 30;
        HorizontalSpacing = 16;
        float f2 = 8;
        HorizontalSpacingButtonSide = f2;
        SeparateButtonExtraY = 2;
        SnackbarVerticalPadding = 6;
        TextEndExtraSpacing = f2;
        LongButtonVerticalOffset = 12;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:179:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0104  */
    /* renamed from: Snackbar-eQBnUkQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m300SnackbareQBnUkQ(Modifier modifier, Function2 function2, Function2 function22, boolean z, Shape shape, long j, long j2, long j3, long j4, final Function2 function23, Composer composer, final int i, final int i2) {
        int i3;
        Function2 function24;
        int i4;
        Function2 function25;
        int i5;
        boolean z2;
        Shape shape2;
        long j5;
        int i6;
        Function2 function26;
        Modifier modifier2;
        Function2 function27;
        boolean z3;
        Shape shape3;
        long color;
        long contentColor;
        long actionContentColor;
        long dismissActionContentColor;
        ComposerImpl composerImpl;
        final Modifier modifier3;
        final boolean z4;
        final Function2 function28;
        final Shape shape4;
        final long j6;
        final long j7;
        final long j8;
        final long j9;
        final Function2 function29;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1235788955);
        int i7 = i2 & 1;
        if (i7 != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl2.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i8 = i2 & 2;
        if (i8 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                function24 = function2;
                i3 |= composerImpl2.changedInstance(function24) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    function25 = function22;
                    i3 |= composerImpl2.changedInstance(function25) ? 256 : 128;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        z2 = z;
                        i3 |= composerImpl2.changed(z2) ? 2048 : 1024;
                    }
                    if ((i & 24576) != 0) {
                        if ((i2 & 16) == 0) {
                            shape2 = shape;
                            int i9 = composerImpl2.changed(shape2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                            i3 |= i9;
                        } else {
                            shape2 = shape;
                        }
                        i3 |= i9;
                    } else {
                        shape2 = shape;
                    }
                    if ((196608 & i) != 0) {
                        if ((i2 & 32) == 0) {
                            j5 = j;
                            int i10 = composerImpl2.changed(j5) ? 131072 : 65536;
                            i3 |= i10;
                        } else {
                            j5 = j;
                        }
                        i3 |= i10;
                    } else {
                        j5 = j;
                    }
                    if ((1572864 & i) != 0) {
                        i6 = i7;
                        i3 |= ((i2 & 64) == 0 && composerImpl2.changed(j2)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    } else {
                        i6 = i7;
                    }
                    if ((12582912 & i) == 0) {
                        i3 |= ((i2 & 128) == 0 && composerImpl2.changed(j3)) ? 8388608 : 4194304;
                    }
                    if ((100663296 & i) == 0) {
                        i3 |= ((i2 & 256) == 0 && composerImpl2.changed(j4)) ? 67108864 : 33554432;
                    }
                    if ((i2 & 512) != 0) {
                        if ((i & 805306368) == 0) {
                            function26 = function23;
                            i3 |= composerImpl2.changedInstance(function26) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                        }
                        if ((i3 & 306783379) == 306783378 && composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            composerImpl = composerImpl2;
                            function28 = function24;
                            function29 = function25;
                            z4 = z2;
                            shape4 = shape2;
                            j6 = j5;
                            modifier3 = modifier;
                            j7 = j2;
                            j8 = j3;
                            j9 = j4;
                        } else {
                            composerImpl2.startDefaults();
                            if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                modifier2 = i6 == 0 ? Modifier.Companion : modifier;
                                if (i8 != 0) {
                                    function24 = null;
                                }
                                function27 = i4 == 0 ? function25 : null;
                                z3 = i5 == 0 ? false : z2;
                                if ((i2 & 16) == 0) {
                                    SnackbarDefaults.INSTANCE.getClass();
                                    shape3 = SnackbarDefaults.getShape(composerImpl2);
                                    i3 &= -57345;
                                } else {
                                    shape3 = shape2;
                                }
                                if ((i2 & 32) == 0) {
                                    SnackbarDefaults.INSTANCE.getClass();
                                    color = SnackbarDefaults.getColor(composerImpl2);
                                    i3 &= -458753;
                                } else {
                                    color = j5;
                                }
                                if ((i2 & 64) == 0) {
                                    SnackbarDefaults.INSTANCE.getClass();
                                    contentColor = SnackbarDefaults.getContentColor(composerImpl2);
                                    i3 &= -3670017;
                                } else {
                                    contentColor = j2;
                                }
                                if ((i2 & 128) == 0) {
                                    SnackbarDefaults.INSTANCE.getClass();
                                    actionContentColor = SnackbarDefaults.getActionContentColor(composerImpl2);
                                    i3 &= -29360129;
                                } else {
                                    actionContentColor = j3;
                                }
                                if ((i2 & 256) == 0) {
                                    SnackbarDefaults.INSTANCE.getClass();
                                    dismissActionContentColor = SnackbarDefaults.getDismissActionContentColor(composerImpl2);
                                    i3 &= -234881025;
                                } else {
                                    dismissActionContentColor = j4;
                                }
                            } else {
                                composerImpl2.skipToGroupEnd();
                                if ((i2 & 16) != 0) {
                                    i3 &= -57345;
                                }
                                if ((i2 & 32) != 0) {
                                    i3 &= -458753;
                                }
                                if ((i2 & 64) != 0) {
                                    i3 &= -3670017;
                                }
                                if ((i2 & 128) != 0) {
                                    i3 &= -29360129;
                                }
                                if ((i2 & 256) != 0) {
                                    i3 &= -234881025;
                                }
                                modifier2 = modifier;
                                dismissActionContentColor = j4;
                                function27 = function25;
                                z3 = z2;
                                shape3 = shape2;
                                color = j5;
                                contentColor = j2;
                                actionContentColor = j3;
                            }
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.Snackbar (Snackbar.kt:111)");
                            }
                            SnackbarTokens.INSTANCE.getClass();
                            final boolean z5 = z3;
                            final Function2 function210 = function24;
                            final long j10 = actionContentColor;
                            final Function2 function211 = function26;
                            final long j11 = dismissActionContentColor;
                            final Function2 function212 = function27;
                            int i11 = (i3 & 14) | 12779520;
                            int i12 = i3 >> 9;
                            SurfaceKt.m304SurfaceT9BRK9s(modifier2, shape3, color, contentColor, 0.0f, SnackbarTokens.ContainerElevation, null, ComposableLambdaKt.rememberComposableLambda(-1829663446, new Function2() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$1
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
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.Snackbar.<anonymous> (Snackbar.kt:119)");
                                            }
                                            SnackbarTokens.INSTANCE.getClass();
                                            TextStyle value = TypographyKt.getValue(SnackbarTokens.SupportingTextFont, composer2);
                                            final TextStyle value2 = TypographyKt.getValue(SnackbarTokens.ActionLabelTextFont, composer2);
                                            ProvidedValue providedValueDefaultProvidedValue$runtime_release = TextKt.LocalTextStyle.defaultProvidedValue$runtime_release(value);
                                            final boolean z6 = z5;
                                            final Function2 function213 = function210;
                                            final Function2 function214 = function211;
                                            final Function2 function215 = function212;
                                            final long j12 = j10;
                                            final long j13 = j11;
                                            CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(835891690, new Function2() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$1.1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(2);
                                                }

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
                                                                ComposerKt.traceEventStart("androidx.compose.material3.Snackbar.<anonymous>.<anonymous> (Snackbar.kt:122)");
                                                            }
                                                            if (!z6 || function213 == null) {
                                                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                                composerImpl5.startReplaceGroup(-810703340);
                                                                SnackbarKt.m303access$OneRowSnackbarkKq0p4A(function214, function213, function215, value2, j12, j13, composerImpl5, 0);
                                                                composerImpl5.end(false);
                                                            } else {
                                                                ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                                                composerImpl6.startReplaceGroup(-810717019);
                                                                SnackbarKt.m302access$NewLineButtonSnackbarkKq0p4A(function214, function213, function215, value2, j12, j13, composerImpl6, 0);
                                                                composerImpl6.end(false);
                                                            }
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composer2), composer2, 56);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl2), composerImpl2, (i12 & 896) | i11 | (i12 & 112) | (i12 & 7168), 80);
                            composerImpl = composerImpl2;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            modifier3 = modifier2;
                            z4 = z3;
                            function28 = function24;
                            shape4 = shape3;
                            j6 = color;
                            j7 = contentColor;
                            j8 = actionContentColor;
                            j9 = dismissActionContentColor;
                            function29 = function27;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    SnackbarKt.m300SnackbareQBnUkQ(modifier3, function28, function29, z4, shape4, j6, j7, j8, j9, function23, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i3 |= 805306368;
                    function26 = function23;
                    if ((i3 & 306783379) == 306783378) {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0) {
                            if (i6 == 0) {
                            }
                            if (i8 != 0) {
                            }
                            if (i4 == 0) {
                            }
                            if (i5 == 0) {
                            }
                            if ((i2 & 16) == 0) {
                            }
                            if ((i2 & 32) == 0) {
                            }
                            if ((i2 & 64) == 0) {
                            }
                            if ((i2 & 128) == 0) {
                            }
                            if ((i2 & 256) == 0) {
                            }
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            SnackbarTokens.INSTANCE.getClass();
                            final boolean z52 = z3;
                            final Function2 function2102 = function24;
                            final long j102 = actionContentColor;
                            final Function2 function2112 = function26;
                            final long j112 = dismissActionContentColor;
                            final Function2 function2122 = function27;
                            int i112 = (i3 & 14) | 12779520;
                            int i122 = i3 >> 9;
                            SurfaceKt.m304SurfaceT9BRK9s(modifier2, shape3, color, contentColor, 0.0f, SnackbarTokens.ContainerElevation, null, ComposableLambdaKt.rememberComposableLambda(-1829663446, new Function2() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$1
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
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.Snackbar.<anonymous> (Snackbar.kt:119)");
                                            }
                                            SnackbarTokens.INSTANCE.getClass();
                                            TextStyle value = TypographyKt.getValue(SnackbarTokens.SupportingTextFont, composer2);
                                            final TextStyle value2 = TypographyKt.getValue(SnackbarTokens.ActionLabelTextFont, composer2);
                                            ProvidedValue providedValueDefaultProvidedValue$runtime_release = TextKt.LocalTextStyle.defaultProvidedValue$runtime_release(value);
                                            final boolean z6 = z52;
                                            final Function2 function213 = function2102;
                                            final Function2 function214 = function2112;
                                            final Function2 function215 = function2122;
                                            final long j12 = j102;
                                            final long j13 = j112;
                                            CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(835891690, new Function2() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$1.1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(2);
                                                }

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
                                                                ComposerKt.traceEventStart("androidx.compose.material3.Snackbar.<anonymous>.<anonymous> (Snackbar.kt:122)");
                                                            }
                                                            if (!z6 || function213 == null) {
                                                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                                composerImpl5.startReplaceGroup(-810703340);
                                                                SnackbarKt.m303access$OneRowSnackbarkKq0p4A(function214, function213, function215, value2, j12, j13, composerImpl5, 0);
                                                                composerImpl5.end(false);
                                                            } else {
                                                                ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                                                composerImpl6.startReplaceGroup(-810717019);
                                                                SnackbarKt.m302access$NewLineButtonSnackbarkKq0p4A(function214, function213, function215, value2, j12, j13, composerImpl6, 0);
                                                                composerImpl6.end(false);
                                                            }
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composer2), composer2, 56);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl2), composerImpl2, (i122 & 896) | i112 | (i122 & 112) | (i122 & 7168), 80);
                            composerImpl = composerImpl2;
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            modifier3 = modifier2;
                            z4 = z3;
                            function28 = function24;
                            shape4 = shape3;
                            j6 = color;
                            j7 = contentColor;
                            j8 = actionContentColor;
                            j9 = dismissActionContentColor;
                            function29 = function27;
                        }
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                z2 = z;
                if ((i & 24576) != 0) {
                }
                if ((196608 & i) != 0) {
                }
                if ((1572864 & i) != 0) {
                }
                if ((12582912 & i) == 0) {
                }
                if ((100663296 & i) == 0) {
                }
                if ((i2 & 512) != 0) {
                }
                function26 = function23;
                if ((i3 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            function25 = function22;
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            z2 = z;
            if ((i & 24576) != 0) {
            }
            if ((196608 & i) != 0) {
            }
            if ((1572864 & i) != 0) {
            }
            if ((12582912 & i) == 0) {
            }
            if ((100663296 & i) == 0) {
            }
            if ((i2 & 512) != 0) {
            }
            function26 = function23;
            if ((i3 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        function24 = function2;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        function25 = function22;
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        z2 = z;
        if ((i & 24576) != 0) {
        }
        if ((196608 & i) != 0) {
        }
        if ((1572864 & i) != 0) {
        }
        if ((12582912 & i) == 0) {
        }
        if ((100663296 & i) == 0) {
        }
        if ((i2 & 512) != 0) {
        }
        function26 = function23;
        if ((i3 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0300  */
    /* JADX WARN: Removed duplicated region for block: B:184:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0113  */
    /* renamed from: Snackbar-sDKtq54, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m301SnackbarsDKtq54(final SnackbarData snackbarData, Modifier modifier, boolean z, Shape shape, long j, long j2, long j3, long j4, long j5, Composer composer, final int i, final int i2) {
        int i3;
        Modifier modifier2;
        int i4;
        boolean z2;
        Shape shape2;
        long j6;
        long actionContentColor;
        long j7;
        final Modifier modifier3;
        Shape shape3;
        long color;
        long contentColor;
        long value;
        long dismissActionContentColor;
        long j8;
        long j9;
        long j10;
        final long j11;
        Shape shape4;
        boolean z3;
        SnackbarHostState.SnackbarDataImpl snackbarDataImpl;
        final String actionLabel;
        ComposableLambdaImpl composableLambdaImpl;
        ComposerImpl composerImpl;
        final long j12;
        final boolean z4;
        final Shape shape5;
        final long j13;
        final long j14;
        final long j15;
        final long j16;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(274621471);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl2.changed(snackbarData) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i5 = 2 & i2;
        if (i5 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 32 : 16;
            }
            i4 = 4 & i2;
            if (i4 != 0) {
                if ((i & 384) == 0) {
                    z2 = z;
                    i3 |= composerImpl2.changed(z2) ? 256 : 128;
                }
                if ((i & 3072) == 0) {
                    if ((i2 & 8) == 0) {
                        shape2 = shape;
                        int i6 = composerImpl2.changed(shape2) ? 2048 : 1024;
                        i3 |= i6;
                    } else {
                        shape2 = shape;
                    }
                    i3 |= i6;
                } else {
                    shape2 = shape;
                }
                if ((i & 24576) == 0) {
                    if ((i2 & 16) == 0) {
                        j6 = j;
                        int i7 = composerImpl2.changed(j6) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        i3 |= i7;
                    } else {
                        j6 = j;
                    }
                    i3 |= i7;
                } else {
                    j6 = j;
                }
                if ((i & 196608) == 0) {
                    i3 |= ((i2 & 32) == 0 && composerImpl2.changed(j2)) ? 131072 : 65536;
                }
                if ((i & 1572864) == 0) {
                    i3 |= ((i2 & 64) == 0 && composerImpl2.changed(j3)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                }
                if ((12582912 & i) == 0) {
                    actionContentColor = j4;
                    i3 |= ((i2 & 128) == 0 && composerImpl2.changed(actionContentColor)) ? 8388608 : 4194304;
                } else {
                    actionContentColor = j4;
                }
                if ((100663296 & i) == 0) {
                    if ((i2 & 256) == 0) {
                        j7 = j5;
                        int i8 = composerImpl2.changed(j7) ? 67108864 : 33554432;
                        i3 |= i8;
                    } else {
                        j7 = j5;
                    }
                    i3 |= i8;
                } else {
                    j7 = j5;
                }
                if ((i3 & 38347923) == 38347922 && composerImpl2.getSkipping()) {
                    composerImpl2.skipToGroupEnd();
                    composerImpl = composerImpl2;
                    modifier3 = modifier2;
                    z4 = z2;
                    j12 = j3;
                    long j17 = actionContentColor;
                    shape5 = shape2;
                    j15 = j17;
                    j14 = j2;
                    j13 = j6;
                    j16 = j7;
                } else {
                    composerImpl2.startDefaults();
                    if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                        modifier3 = i5 == 0 ? Modifier.Companion : modifier2;
                        if (i4 != 0) {
                            z2 = false;
                        }
                        if ((i2 & 8) == 0) {
                            SnackbarDefaults.INSTANCE.getClass();
                            shape3 = SnackbarDefaults.getShape(composerImpl2);
                            i3 &= -7169;
                        } else {
                            shape3 = shape2;
                        }
                        if ((i2 & 16) == 0) {
                            SnackbarDefaults.INSTANCE.getClass();
                            color = SnackbarDefaults.getColor(composerImpl2);
                            i3 &= -57345;
                        } else {
                            color = j6;
                        }
                        if ((i2 & 32) == 0) {
                            SnackbarDefaults.INSTANCE.getClass();
                            contentColor = SnackbarDefaults.getContentColor(composerImpl2);
                            i3 &= -458753;
                        } else {
                            contentColor = j2;
                        }
                        if ((i2 & 64) == 0) {
                            SnackbarDefaults.INSTANCE.getClass();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.SnackbarDefaults.<get-actionColor> (Snackbar.kt:427)");
                            }
                            SnackbarTokens.INSTANCE.getClass();
                            value = ColorSchemeKt.getValue(SnackbarTokens.ActionLabelTextColor, composerImpl2);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            i3 &= -3670017;
                        } else {
                            value = j3;
                        }
                        if ((128 & i2) != 0) {
                            SnackbarDefaults.INSTANCE.getClass();
                            actionContentColor = SnackbarDefaults.getActionContentColor(composerImpl2);
                            i3 &= -29360129;
                        }
                        if ((256 & i2) == 0) {
                            SnackbarDefaults.INSTANCE.getClass();
                            i3 &= -234881025;
                            j8 = actionContentColor;
                            j10 = contentColor;
                            j11 = value;
                            dismissActionContentColor = SnackbarDefaults.getDismissActionContentColor(composerImpl2);
                            shape4 = shape3;
                            z3 = z2;
                            j9 = color;
                        } else {
                            dismissActionContentColor = j5;
                            j8 = actionContentColor;
                            j9 = color;
                            j10 = contentColor;
                            j11 = value;
                            shape4 = shape3;
                            z3 = z2;
                        }
                    } else {
                        composerImpl2.skipToGroupEnd();
                        if ((i2 & 8) != 0) {
                            i3 &= -7169;
                        }
                        if ((i2 & 16) != 0) {
                            i3 &= -57345;
                        }
                        if ((i2 & 32) != 0) {
                            i3 &= -458753;
                        }
                        if ((i2 & 64) != 0) {
                            i3 &= -3670017;
                        }
                        if ((i2 & 128) != 0) {
                            i3 &= -29360129;
                        }
                        if ((i2 & 256) != 0) {
                            i3 &= -234881025;
                        }
                        j10 = j2;
                        dismissActionContentColor = j5;
                        j8 = actionContentColor;
                        modifier3 = modifier2;
                        z3 = z2;
                        shape4 = shape2;
                        j9 = j6;
                        j11 = j3;
                    }
                    composerImpl2.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.material3.Snackbar (Snackbar.kt:208)");
                    }
                    snackbarDataImpl = (SnackbarHostState.SnackbarDataImpl) snackbarData;
                    actionLabel = snackbarDataImpl.visuals.getActionLabel();
                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda = null;
                    if (actionLabel == null) {
                        composerImpl2.startReplaceGroup(1157017515);
                        ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(-1378313599, new Function2() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$actionComposable$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x0054  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj, Object obj2) {
                                Composer composer2 = (Composer) obj;
                                if ((((Number) obj2).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.Snackbar.<anonymous> (Snackbar.kt:213)");
                                        }
                                        ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
                                        long j18 = j11;
                                        buttonDefaults.getClass();
                                        ButtonColors buttonColorsM254textButtonColorsro_MJ88 = ButtonDefaults.m254textButtonColorsro_MJ88(0L, j18, composer2, 13);
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                        boolean zChanged = composerImpl4.changed(snackbarData);
                                        final SnackbarData snackbarData2 = snackbarData;
                                        Object objRememberedValue = composerImpl4.rememberedValue();
                                        if (!zChanged) {
                                            Composer.Companion.getClass();
                                            if (objRememberedValue == Composer.Companion.Empty) {
                                                objRememberedValue = new Function0() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$actionComposable$1$1$1
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    public final Object invoke() {
                                                        ((SnackbarHostState.SnackbarDataImpl) snackbarData2).performAction();
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl4.updateRememberedValue(objRememberedValue);
                                            }
                                            final String str = actionLabel;
                                            ButtonKt.TextButton((Function0) objRememberedValue, null, false, null, buttonColorsM254textButtonColorsro_MJ88, null, null, null, null, ComposableLambdaKt.rememberComposableLambda(521110564, new Function3() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$actionComposable$1.2
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(3);
                                                }

                                                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                                                @Override // kotlin.jvm.functions.Function3
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                                    Composer composer3 = (Composer) obj4;
                                                    if ((((Number) obj5).intValue() & 17) == 16) {
                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                        if (composerImpl5.getSkipping()) {
                                                            composerImpl5.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("androidx.compose.material3.Snackbar.<anonymous>.<anonymous> (Snackbar.kt:216)");
                                                            }
                                                            TextKt.m317Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer3, 0, 0, 131070);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composerImpl4), composerImpl4, 805306368, 494);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl2);
                        composerImpl2.end(false);
                        composableLambdaImpl = composableLambdaImplRememberComposableLambda2;
                    } else {
                        composerImpl2.startReplaceGroup(1157315518);
                        composerImpl2.end(false);
                        composableLambdaImpl = null;
                    }
                    if (snackbarDataImpl.visuals.getWithDismissAction()) {
                        composerImpl2.startReplaceGroup(1157857150);
                        composerImpl2.end(false);
                    } else {
                        composerImpl2.startReplaceGroup(1157467852);
                        composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-1812633777, new Function2() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$dismissActionComposable$1
                            {
                                super(2);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x0040  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj, Object obj2) {
                                Composer composer2 = (Composer) obj;
                                if ((((Number) obj2).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.Snackbar.<anonymous> (Snackbar.kt:225)");
                                        }
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                        boolean zChanged = composerImpl4.changed(snackbarData);
                                        final SnackbarData snackbarData2 = snackbarData;
                                        Object objRememberedValue = composerImpl4.rememberedValue();
                                        if (!zChanged) {
                                            Composer.Companion.getClass();
                                            if (objRememberedValue == Composer.Companion.Empty) {
                                                objRememberedValue = new Function0() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$dismissActionComposable$1$1$1
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    public final Object invoke() {
                                                        ((SnackbarHostState.SnackbarDataImpl) snackbarData2).dismiss();
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl4.updateRememberedValue(objRememberedValue);
                                            }
                                            ComposableSingletons$SnackbarKt.INSTANCE.getClass();
                                            IconButtonKt.IconButton(1572864, 62, null, null, composerImpl4, null, null, (Function0) objRememberedValue, ComposableSingletons$SnackbarKt.f16lambda1, false);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl2);
                        composerImpl2.end(false);
                    }
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImplRememberComposableLambda;
                    Dp.Companion companion = Dp.Companion;
                    int i9 = i3 << 3;
                    composerImpl = composerImpl2;
                    m300SnackbareQBnUkQ(PaddingKt.m125padding3ABfNKs(modifier3, 12), composableLambdaImpl, composableLambdaImpl2, z3, shape4, j9, j10, j8, dismissActionContentColor, ComposableLambdaKt.rememberComposableLambda(-1266389126, new Function2() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$3
                        {
                            super(2);
                        }

                        /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer2 = (Composer) obj;
                            if ((((Number) obj2).intValue() & 3) == 2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.Snackbar.<anonymous> (Snackbar.kt:248)");
                                    }
                                    TextKt.m317Text4IGK_g(((SnackbarHostState.SnackbarDataImpl) snackbarData).visuals.getMessage(), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer2, 0, 0, 131070);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl2), composerImpl, (i9 & 3670016) | (i9 & 7168) | 805306368 | (57344 & i9) | (458752 & i9) | (29360128 & i3) | (234881024 & i3), 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    j12 = j11;
                    z4 = z3;
                    shape5 = shape4;
                    j13 = j9;
                    j14 = j10;
                    j15 = j8;
                    j16 = dismissActionContentColor;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SnackbarKt.m301SnackbarsDKtq54(snackbarData, modifier3, z4, shape5, j13, j14, j12, j15, j16, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i3 |= 384;
            z2 = z;
            if ((i & 3072) == 0) {
            }
            if ((i & 24576) == 0) {
            }
            if ((i & 196608) == 0) {
            }
            if ((i & 1572864) == 0) {
            }
            if ((12582912 & i) == 0) {
            }
            if ((100663296 & i) == 0) {
            }
            if ((i3 & 38347923) == 38347922) {
                composerImpl2.startDefaults();
                if ((i & 1) != 0) {
                    if (i5 == 0) {
                    }
                    if (i4 != 0) {
                    }
                    if ((i2 & 8) == 0) {
                    }
                    if ((i2 & 16) == 0) {
                    }
                    if ((i2 & 32) == 0) {
                    }
                    if ((i2 & 64) == 0) {
                    }
                    if ((128 & i2) != 0) {
                    }
                    if ((256 & i2) == 0) {
                    }
                    composerImpl2.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    snackbarDataImpl = (SnackbarHostState.SnackbarDataImpl) snackbarData;
                    actionLabel = snackbarDataImpl.visuals.getActionLabel();
                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda3 = null;
                    if (actionLabel == null) {
                    }
                    if (snackbarDataImpl.visuals.getWithDismissAction()) {
                    }
                    ComposableLambdaImpl composableLambdaImpl22 = composableLambdaImplRememberComposableLambda3;
                    Dp.Companion companion2 = Dp.Companion;
                    int i92 = i3 << 3;
                    composerImpl = composerImpl2;
                    m300SnackbareQBnUkQ(PaddingKt.m125padding3ABfNKs(modifier3, 12), composableLambdaImpl, composableLambdaImpl22, z3, shape4, j9, j10, j8, dismissActionContentColor, ComposableLambdaKt.rememberComposableLambda(-1266389126, new Function2() { // from class: androidx.compose.material3.SnackbarKt$Snackbar$3
                        {
                            super(2);
                        }

                        /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer2 = (Composer) obj;
                            if ((((Number) obj2).intValue() & 3) == 2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.Snackbar.<anonymous> (Snackbar.kt:248)");
                                    }
                                    TextKt.m317Text4IGK_g(((SnackbarHostState.SnackbarDataImpl) snackbarData).visuals.getMessage(), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer2, 0, 0, 131070);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl2), composerImpl, (i92 & 3670016) | (i92 & 7168) | 805306368 | (57344 & i92) | (458752 & i92) | (29360128 & i3) | (234881024 & i3), 0);
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    j12 = j11;
                    z4 = z3;
                    shape5 = shape4;
                    j13 = j9;
                    j14 = j10;
                    j15 = j8;
                    j16 = dismissActionContentColor;
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i4 = 4 & i2;
        if (i4 != 0) {
        }
        z2 = z;
        if ((i & 3072) == 0) {
        }
        if ((i & 24576) == 0) {
        }
        if ((i & 196608) == 0) {
        }
        if ((i & 1572864) == 0) {
        }
        if ((12582912 & i) == 0) {
        }
        if ((100663296 & i) == 0) {
        }
        if ((i3 & 38347923) == 38347922) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* renamed from: access$NewLineButtonSnackbar-kKq0p4A, reason: not valid java name */
    public static final void m302access$NewLineButtonSnackbarkKq0p4A(final Function2 function2, Function2 function22, Function2 function23, final TextStyle textStyle, final long j, final long j2, Composer composer, final int i) {
        int i2;
        float f;
        Modifier modifierM91paddingFrom4j6BHR0$default;
        boolean z;
        float f2;
        Function2 function24;
        Function2 function25;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1332496681);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function2) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function22) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function23) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(textStyle) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(j) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(j2) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function24 = function22;
            function25 = function23;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.NewLineButtonSnackbar (Snackbar.kt:260)");
            }
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(SizeKt.fillMaxWidth(SizeKt.m146widthInVpY3zN4$default(companion, 0.0f, ContainerMaxWidth, 1), 1.0f), HorizontalSpacing, 0.0f, 0.0f, SeparateButtonExtraY, 6);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default);
            ComposeUiNode.Companion.getClass();
            int i3 = i2;
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Function2 function26 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, function26);
            Function2 function27 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function27);
            Function2 function28 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function28);
            }
            Function2 function29 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function29);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Dp.Companion.getClass();
            float f3 = Dp.Unspecified;
            float f4 = HeightToFirstLine;
            if (Dp.m838equalsimpl0(f4, f3)) {
                f = 0.0f;
                modifierM91paddingFrom4j6BHR0$default = companion;
            } else {
                f = 0.0f;
                modifierM91paddingFrom4j6BHR0$default = AlignmentLineKt.m91paddingFrom4j6BHR0$default(companion, androidx.compose.ui.layout.AlignmentLineKt.FirstBaseline, f4, 0.0f, 4);
            }
            companion.getClass();
            float f5 = LongButtonVerticalOffset;
            Modifier modifierThen = modifierM91paddingFrom4j6BHR0$default.then(!Dp.m838equalsimpl0(f5, f3) ? AlignmentLineKt.m91paddingFrom4j6BHR0$default(companion, androidx.compose.ui.layout.AlignmentLineKt.LastBaseline, f, f5, 2) : companion);
            float f6 = HorizontalSpacingButtonSide;
            Modifier modifierM129paddingqDBjuR0$default2 = PaddingKt.m129paddingqDBjuR0$default(modifierThen, 0.0f, 0.0f, f6, 0.0f, 11);
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default2);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function26);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function27);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function28);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function29);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            function2.invoke(composerImpl, Integer.valueOf(i3 & 14));
            composerImpl.end(true);
            Modifier modifierAlign = columnScopeInstance.align(companion, Alignment.Companion.End);
            if (function23 == null) {
                f2 = f6;
                z = false;
            } else {
                z = false;
                f2 = 0;
            }
            Modifier modifierM129paddingqDBjuR0$default3 = PaddingKt.m129paddingqDBjuR0$default(modifierAlign, 0.0f, 0.0f, f2, 0.0f, 11);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, z);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default3);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function26);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function27);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function28);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function29);
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, Alignment.Companion.Top, composerImpl, 0);
            int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, companion);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, rowMeasurePolicy, function26);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, function27);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function28);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, function29);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
            function24 = function22;
            CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(j)), TextKt.LocalTextStyle.defaultProvidedValue$runtime_release(textStyle)}, function24, composerImpl, (i3 & 112) | 8);
            if (function23 != null) {
                composerImpl.startReplaceGroup(1996804040);
                function25 = function23;
                CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(j2)), function25, composerImpl, ((i3 >> 3) & 112) | 8);
                composerImpl.end(false);
            } else {
                function25 = function23;
                composerImpl.startReplaceGroup(1997008733);
                composerImpl.end(false);
            }
            composerImpl.end(true);
            composerImpl.end(true);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Function2 function210 = function25;
            final Function2 function211 = function24;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SnackbarKt$NewLineButtonSnackbar$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SnackbarKt.m302access$NewLineButtonSnackbarkKq0p4A(function2, function211, function210, textStyle, j, j2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: access$OneRowSnackbar-kKq0p4A, reason: not valid java name */
    public static final void m303access$OneRowSnackbarkKq0p4A(final Function2 function2, Function2 function22, Function2 function23, final TextStyle textStyle, final long j, final long j2, Composer composer, final int i) {
        int i2;
        float f;
        Function2 function24;
        boolean z;
        Function2 function25;
        boolean z2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-903235475);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function2) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function22) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function23) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(textStyle) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(j) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(j2) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function24 = function22;
            function25 = function23;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.OneRowSnackbar (Snackbar.kt:305)");
            }
            Modifier.Companion companion = Modifier.Companion;
            if (function23 == null) {
                f = HorizontalSpacingButtonSide;
            } else {
                f = 0;
                Dp.Companion companion2 = Dp.Companion;
            }
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(companion, HorizontalSpacing, 0.0f, f, 0.0f, 10);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            final String str = "text";
            int i3 = i2;
            final String str2 = "action";
            final String str3 = "dismissAction";
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new MeasurePolicy() { // from class: androidx.compose.material3.SnackbarKt$OneRowSnackbar$2$1
                    /* JADX WARN: Removed duplicated region for block: B:63:0x010b A[PHI: r0 r5
                      0x010b: PHI (r0v9 int) = (r0v8 int), (r0v13 int), (r0v13 int) binds: [B:66:0x012a, B:59:0x0100, B:61:0x0106] A[DONT_GENERATE, DONT_INLINE]
                      0x010b: PHI (r5v10 int) = (r5v9 int), (r5v17 int), (r5v17 int) binds: [B:66:0x012a, B:59:0x0100, B:61:0x0106] A[DONT_GENERATE, DONT_INLINE]] */
                    @Override // androidx.compose.ui.layout.MeasurePolicy
                    /* renamed from: measure-3p2s80s */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j3) {
                        Object obj;
                        Object obj2;
                        int iMo52roundToPx0680j_4;
                        int iMax;
                        int i4;
                        int i5;
                        List list2 = list;
                        int iMin = Math.min(Constraints.m823getMaxWidthimpl(j3), measureScope.mo52roundToPx0680j_4(SnackbarKt.ContainerMaxWidth));
                        int size = list2.size();
                        int i6 = 0;
                        while (true) {
                            if (i6 >= size) {
                                obj = null;
                                break;
                            }
                            obj = list2.get(i6);
                            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj), str2)) {
                                break;
                            }
                            i6++;
                        }
                        Measurable measurable = (Measurable) obj;
                        Placeable placeableMo610measureBRTryo0 = measurable != null ? measurable.mo610measureBRTryo0(j3) : null;
                        int size2 = list2.size();
                        int i7 = 0;
                        while (true) {
                            if (i7 >= size2) {
                                obj2 = null;
                                break;
                            }
                            obj2 = list2.get(i7);
                            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj2), str3)) {
                                break;
                            }
                            i7++;
                        }
                        Measurable measurable2 = (Measurable) obj2;
                        final Placeable placeableMo610measureBRTryo02 = measurable2 != null ? measurable2.mo610measureBRTryo0(j3) : null;
                        int i8 = placeableMo610measureBRTryo0 != null ? placeableMo610measureBRTryo0.width : 0;
                        int i9 = placeableMo610measureBRTryo0 != null ? placeableMo610measureBRTryo0.height : 0;
                        int i10 = placeableMo610measureBRTryo02 != null ? placeableMo610measureBRTryo02.width : 0;
                        int i11 = placeableMo610measureBRTryo02 != null ? placeableMo610measureBRTryo02.height : 0;
                        int iMo52roundToPx0680j_42 = ((iMin - i8) - i10) - (i10 == 0 ? measureScope.mo52roundToPx0680j_4(SnackbarKt.TextEndExtraSpacing) : 0);
                        int iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j3);
                        if (iMo52roundToPx0680j_42 < iM825getMinWidthimpl) {
                            iMo52roundToPx0680j_42 = iM825getMinWidthimpl;
                        }
                        int size3 = list2.size();
                        int i12 = 0;
                        while (i12 < size3) {
                            Measurable measurable3 = (Measurable) list2.get(i12);
                            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable3), str)) {
                                int i13 = i11;
                                final Placeable placeableMo610measureBRTryo03 = measurable3.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j3, 0, iMo52roundToPx0680j_42, 0, 0, 9));
                                HorizontalAlignmentLine horizontalAlignmentLine = androidx.compose.ui.layout.AlignmentLineKt.FirstBaseline;
                                int i14 = placeableMo610measureBRTryo03.get(horizontalAlignmentLine);
                                int i15 = placeableMo610measureBRTryo03.get(androidx.compose.ui.layout.AlignmentLineKt.LastBaseline);
                                boolean z3 = true;
                                boolean z4 = (i14 == Integer.MIN_VALUE || i15 == Integer.MIN_VALUE) ? false : true;
                                if (i14 != i15 && z4) {
                                    z3 = false;
                                }
                                final int i16 = iMin - i10;
                                final int i17 = i16 - i8;
                                if (z3) {
                                    SnackbarTokens.INSTANCE.getClass();
                                    iMax = Math.max(measureScope.mo52roundToPx0680j_4(SnackbarTokens.SingleLineContainerHeight), Math.max(i9, i13));
                                    iMo52roundToPx0680j_4 = (iMax - placeableMo610measureBRTryo03.height) / 2;
                                    i4 = (placeableMo610measureBRTryo0 == null || (i5 = placeableMo610measureBRTryo0.get(horizontalAlignmentLine)) == Integer.MIN_VALUE) ? 0 : (i14 + iMo52roundToPx0680j_4) - i5;
                                } else {
                                    iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(SnackbarKt.HeightToFirstLine) - i14;
                                    SnackbarTokens.INSTANCE.getClass();
                                    iMax = Math.max(measureScope.mo52roundToPx0680j_4(SnackbarTokens.TwoLinesContainerHeight), placeableMo610measureBRTryo03.height + iMo52roundToPx0680j_4);
                                    if (placeableMo610measureBRTryo0 != null) {
                                        i4 = (iMax - placeableMo610measureBRTryo0.height) / 2;
                                    }
                                }
                                final int i18 = i4;
                                final int i19 = iMo52roundToPx0680j_4;
                                final int i20 = placeableMo610measureBRTryo02 != null ? (iMax - placeableMo610measureBRTryo02.height) / 2 : 0;
                                final Placeable placeable = placeableMo610measureBRTryo0;
                                return measureScope.layout$1(iMin, iMax, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.SnackbarKt$OneRowSnackbar$2$1.2
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj3) {
                                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj3;
                                        placementScope.placeRelative(placeableMo610measureBRTryo03, 0, i19, 0.0f);
                                        Placeable placeable2 = placeableMo610measureBRTryo02;
                                        if (placeable2 != null) {
                                            placementScope.placeRelative(placeable2, i16, i20, 0.0f);
                                        }
                                        Placeable placeable3 = placeable;
                                        if (placeable3 != null) {
                                            placementScope.placeRelative(placeable3, i17, i18, 0.0f);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                });
                            }
                            i12++;
                            list2 = list;
                            i11 = i11;
                        }
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MeasurePolicy measurePolicy = (MeasurePolicy) objRememberedValue;
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Function2 function26 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl, measurePolicy, function26);
            Function2 function27 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function27);
            Function2 function28 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function28);
            }
            Function2 function29 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function29);
            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(LayoutIdKt.layoutId(companion, "text"), 0.0f, SnackbarVerticalPadding, 1);
            Alignment.Companion.getClass();
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM127paddingVpY3zN4$default);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function26);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function27);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function28);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function29);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            function2.invoke(composerImpl, Integer.valueOf(i3 & 14));
            composerImpl.end(true);
            if (function22 != null) {
                composerImpl.startReplaceGroup(2016616928);
                Modifier modifierLayoutId = LayoutIdKt.layoutId(companion, "action");
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierLayoutId);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function26);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function27);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function28);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function29);
                function24 = function22;
                CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(j)), TextKt.LocalTextStyle.defaultProvidedValue$runtime_release(textStyle)}, function24, composerImpl, (i3 & 112) | 8);
                composerImpl.end(true);
                z = false;
                composerImpl.end(false);
            } else {
                function24 = function22;
                z = false;
                composerImpl.startReplaceGroup(2016931175);
                composerImpl.end(false);
            }
            if (function23 != null) {
                composerImpl.startReplaceGroup(2016979504);
                Modifier modifierLayoutId2 = LayoutIdKt.layoutId(companion, "dismissAction");
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, z);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, modifierLayoutId2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy3, function26);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, function27);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function28);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, function29);
                function25 = function23;
                CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(j2)), function25, composerImpl, 8 | ((i3 >> 3) & 112));
                z2 = true;
                composerImpl.end(true);
                composerImpl.end(false);
            } else {
                function25 = function23;
                z2 = true;
                composerImpl.startReplaceGroup(2017247623);
                composerImpl.end(z);
            }
            composerImpl.end(z2);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Function2 function210 = function25;
            final Function2 function211 = function24;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SnackbarKt$OneRowSnackbar$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SnackbarKt.m303access$OneRowSnackbarkKq0p4A(function2, function211, function210, textStyle, j, j2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
