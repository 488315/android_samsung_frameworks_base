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
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
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
import com.sec.ims.volte2.data.VolteConstants;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:102:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0196  */
    /* renamed from: Snackbar-eQBnUkQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m299SnackbareQBnUkQ(androidx.compose.ui.Modifier r22, kotlin.jvm.functions.Function2 r23, kotlin.jvm.functions.Function2 r24, boolean r25, androidx.compose.ui.graphics.Shape r26, long r27, long r29, long r31, long r33, final kotlin.jvm.functions.Function2 r35, androidx.compose.runtime.Composer r36, final int r37, final int r38) {
        /*
            Method dump skipped, instructions count: 658
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SnackbarKt.m299SnackbareQBnUkQ(androidx.compose.ui.Modifier, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, boolean, androidx.compose.ui.graphics.Shape, long, long, long, long, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0300  */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x025e  */
    /* renamed from: Snackbar-sDKtq54, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m300SnackbarsDKtq54(final androidx.compose.material3.SnackbarData r36, androidx.compose.ui.Modifier r37, boolean r38, androidx.compose.ui.graphics.Shape r39, long r40, long r42, long r44, long r46, long r48, androidx.compose.runtime.Composer r50, final int r51, final int r52) {
        /*
            Method dump skipped, instructions count: 784
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SnackbarKt.m300SnackbarsDKtq54(androidx.compose.material3.SnackbarData, androidx.compose.ui.Modifier, boolean, androidx.compose.ui.graphics.Shape, long, long, long, long, long, androidx.compose.runtime.Composer, int, int):void");
    }

    /* renamed from: access$NewLineButtonSnackbar-kKq0p4A, reason: not valid java name */
    public static final void m301access$NewLineButtonSnackbarkKq0p4A(final Function2 function2, Function2 function22, Function2 function23, final TextStyle textStyle, final long j, final long j2, Composer composer, final int i) {
        int i2;
        float f;
        Modifier modifier;
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
            Modifier m128paddingqDBjuR0$default = PaddingKt.m128paddingqDBjuR0$default(SizeKt.fillMaxWidth(SizeKt.m145widthInVpY3zN4$default(companion, 0.0f, ContainerMaxWidth, 1), 1.0f), HorizontalSpacing, 0.0f, 0.0f, SeparateButtonExtraY, 6);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m128paddingqDBjuR0$default);
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
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, function26);
            Function2 function27 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function27);
            Function2 function28 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function28);
            }
            Function2 function29 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function29);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Dp.Companion.getClass();
            float f3 = Dp.Unspecified;
            float f4 = HeightToFirstLine;
            if (Dp.m836equalsimpl0(f4, f3)) {
                f = 0.0f;
                modifier = companion;
            } else {
                f = 0.0f;
                modifier = AlignmentLineKt.m90paddingFrom4j6BHR0$default(companion, androidx.compose.ui.layout.AlignmentLineKt.FirstBaseline, f4, 0.0f, 4);
            }
            companion.getClass();
            float f5 = LongButtonVerticalOffset;
            Modifier then = modifier.then(!Dp.m836equalsimpl0(f5, f3) ? AlignmentLineKt.m90paddingFrom4j6BHR0$default(companion, androidx.compose.ui.layout.AlignmentLineKt.LastBaseline, f, f5, 2) : companion);
            float f6 = HorizontalSpacingButtonSide;
            Modifier m128paddingqDBjuR0$default2 = PaddingKt.m128paddingqDBjuR0$default(then, 0.0f, 0.0f, f6, 0.0f, 11);
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, m128paddingqDBjuR0$default2);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, function26);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function27);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function28);
            }
            Updater.m336setimpl(composerImpl, materializeModifier2, function29);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            function2.invoke(composerImpl, Integer.valueOf(i3 & 14));
            composerImpl.end(true);
            Modifier align = columnScopeInstance.align(companion, Alignment.Companion.End);
            if (function23 == null) {
                f2 = f6;
                z = false;
            } else {
                z = false;
                f2 = 0;
            }
            Modifier m128paddingqDBjuR0$default3 = PaddingKt.m128paddingqDBjuR0$default(align, 0.0f, 0.0f, f2, 0.0f, 11);
            MeasurePolicy maybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, z);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, m128paddingqDBjuR0$default3);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy2, function26);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope3, function27);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function28);
            }
            Updater.m336setimpl(composerImpl, materializeModifier3, function29);
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, Alignment.Companion.Top, composerImpl, 0);
            int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, companion);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, rowMeasurePolicy, function26);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope4, function27);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function28);
            }
            Updater.m336setimpl(composerImpl, materializeModifier4, function29);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
            function24 = function22;
            CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m454boximpl(j)), TextKt.LocalTextStyle.defaultProvidedValue$runtime_release(textStyle)}, function24, composerImpl, (i3 & 112) | 8);
            if (function23 != null) {
                composerImpl.startReplaceGroup(1996804040);
                function25 = function23;
                CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m454boximpl(j2)), function25, composerImpl, ((i3 >> 3) & 112) | 8);
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Function2 function210 = function25;
            final Function2 function211 = function24;
            endRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SnackbarKt$NewLineButtonSnackbar$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SnackbarKt.m301access$NewLineButtonSnackbarkKq0p4A(Function2.this, function211, function210, textStyle, j, j2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: access$OneRowSnackbar-kKq0p4A, reason: not valid java name */
    public static final void m302access$OneRowSnackbarkKq0p4A(final Function2 function2, Function2 function22, Function2 function23, final TextStyle textStyle, final long j, final long j2, Composer composer, final int i) {
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
            Modifier m128paddingqDBjuR0$default = PaddingKt.m128paddingqDBjuR0$default(companion, HorizontalSpacing, 0.0f, f, 0.0f, 10);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            final String str = "text";
            int i3 = i2;
            final String str2 = "action";
            final String str3 = "dismissAction";
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new MeasurePolicy() { // from class: androidx.compose.material3.SnackbarKt$OneRowSnackbar$2$1
                    @Override // androidx.compose.ui.layout.MeasurePolicy
                    /* renamed from: measure-3p2s80s */
                    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j3) {
                        Object obj;
                        Object obj2;
                        int mo51roundToPx0680j_4;
                        int max;
                        int i4;
                        MeasureResult layout$1;
                        int i5;
                        List list2 = list;
                        int min = Math.min(Constraints.m821getMaxWidthimpl(j3), measureScope.mo51roundToPx0680j_4(SnackbarKt.ContainerMaxWidth));
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
                        Placeable mo608measureBRTryo0 = measurable != null ? measurable.mo608measureBRTryo0(j3) : null;
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
                        final Placeable mo608measureBRTryo02 = measurable2 != null ? measurable2.mo608measureBRTryo0(j3) : null;
                        int i8 = mo608measureBRTryo0 != null ? mo608measureBRTryo0.width : 0;
                        int i9 = mo608measureBRTryo0 != null ? mo608measureBRTryo0.height : 0;
                        int i10 = mo608measureBRTryo02 != null ? mo608measureBRTryo02.width : 0;
                        int i11 = mo608measureBRTryo02 != null ? mo608measureBRTryo02.height : 0;
                        int mo51roundToPx0680j_42 = ((min - i8) - i10) - (i10 == 0 ? measureScope.mo51roundToPx0680j_4(SnackbarKt.TextEndExtraSpacing) : 0);
                        int m823getMinWidthimpl = Constraints.m823getMinWidthimpl(j3);
                        if (mo51roundToPx0680j_42 < m823getMinWidthimpl) {
                            mo51roundToPx0680j_42 = m823getMinWidthimpl;
                        }
                        int size3 = list2.size();
                        int i12 = 0;
                        while (i12 < size3) {
                            Measurable measurable3 = (Measurable) list2.get(i12);
                            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable3), str)) {
                                int i13 = i11;
                                final Placeable mo608measureBRTryo03 = measurable3.mo608measureBRTryo0(Constraints.m814copyZbe2FdA$default(j3, 0, mo51roundToPx0680j_42, 0, 0, 9));
                                HorizontalAlignmentLine horizontalAlignmentLine = androidx.compose.ui.layout.AlignmentLineKt.FirstBaseline;
                                int i14 = mo608measureBRTryo03.get(horizontalAlignmentLine);
                                int i15 = mo608measureBRTryo03.get(androidx.compose.ui.layout.AlignmentLineKt.LastBaseline);
                                boolean z3 = true;
                                boolean z4 = (i14 == Integer.MIN_VALUE || i15 == Integer.MIN_VALUE) ? false : true;
                                if (i14 != i15 && z4) {
                                    z3 = false;
                                }
                                final int i16 = min - i10;
                                final int i17 = i16 - i8;
                                if (z3) {
                                    SnackbarTokens.INSTANCE.getClass();
                                    max = Math.max(measureScope.mo51roundToPx0680j_4(SnackbarTokens.SingleLineContainerHeight), Math.max(i9, i13));
                                    mo51roundToPx0680j_4 = (max - mo608measureBRTryo03.height) / 2;
                                    if (mo608measureBRTryo0 != null && (i5 = mo608measureBRTryo0.get(horizontalAlignmentLine)) != Integer.MIN_VALUE) {
                                        i4 = (i14 + mo51roundToPx0680j_4) - i5;
                                    }
                                    i4 = 0;
                                } else {
                                    mo51roundToPx0680j_4 = measureScope.mo51roundToPx0680j_4(SnackbarKt.HeightToFirstLine) - i14;
                                    SnackbarTokens.INSTANCE.getClass();
                                    max = Math.max(measureScope.mo51roundToPx0680j_4(SnackbarTokens.TwoLinesContainerHeight), mo608measureBRTryo03.height + mo51roundToPx0680j_4);
                                    if (mo608measureBRTryo0 != null) {
                                        i4 = (max - mo608measureBRTryo0.height) / 2;
                                    }
                                    i4 = 0;
                                }
                                final int i18 = i4;
                                final int i19 = mo51roundToPx0680j_4;
                                final int i20 = mo608measureBRTryo02 != null ? (max - mo608measureBRTryo02.height) / 2 : 0;
                                final Placeable placeable = mo608measureBRTryo0;
                                layout$1 = measureScope.layout$1(min, max, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.SnackbarKt$OneRowSnackbar$2$1.2
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo779invoke(Object obj3) {
                                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj3;
                                        placementScope.placeRelative(Placeable.this, 0, i19, 0.0f);
                                        Placeable placeable2 = mo608measureBRTryo02;
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
                                return layout$1;
                            }
                            i12++;
                            list2 = list;
                            i11 = i11;
                        }
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MeasurePolicy measurePolicy = (MeasurePolicy) rememberedValue;
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m128paddingqDBjuR0$default);
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
            Updater.m336setimpl(composerImpl, measurePolicy, function26);
            Function2 function27 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function27);
            Function2 function28 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function28);
            }
            Function2 function29 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function29);
            Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(LayoutIdKt.layoutId(companion, "text"), 0.0f, SnackbarVerticalPadding, 1);
            Alignment.Companion.getClass();
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, m126paddingVpY3zN4$default);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, function26);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function27);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function28);
            }
            Updater.m336setimpl(composerImpl, materializeModifier2, function29);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            function2.invoke(composerImpl, Integer.valueOf(i3 & 14));
            composerImpl.end(true);
            if (function22 != null) {
                composerImpl.startReplaceGroup(2016616928);
                Modifier layoutId = LayoutIdKt.layoutId(companion, "action");
                MeasurePolicy maybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, layoutId);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy2, function26);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope3, function27);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function28);
                }
                Updater.m336setimpl(composerImpl, materializeModifier3, function29);
                function24 = function22;
                CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(j)), TextKt.LocalTextStyle.defaultProvidedValue$runtime_release(textStyle)}, function24, composerImpl, (i3 & 112) | 8);
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
                Modifier layoutId2 = LayoutIdKt.layoutId(companion, "dismissAction");
                MeasurePolicy maybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, z);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, layoutId2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy3, function26);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope4, function27);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function28);
                }
                Updater.m336setimpl(composerImpl, materializeModifier4, function29);
                function25 = function23;
                CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(j2)), function25, composerImpl, 8 | ((i3 >> 3) & 112));
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Function2 function210 = function25;
            final Function2 function211 = function24;
            endRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SnackbarKt$OneRowSnackbar$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SnackbarKt.m302access$OneRowSnackbarkKq0p4A(Function2.this, function211, function210, textStyle, j, j2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
