package com.samsung.sesl.compose.component;

import android.content.res.Configuration;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.ComputedProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.component.tokens.DimensionSchemeKeyTokensKt;
import com.samsung.sesl.compose.component.tokens.DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1;
import com.samsung.sesl.compose.component.tokens.SeslAppBarDimensionSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslAppBarTokens;
import com.samsung.sesl.compose.component.tokens.SeslDpProducer;
import com.samsung.sesl.compose.foundation.theme.SeslTokenScheme;
import com.samsung.sesl.compose.foundation.theme.TokenSchemeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AppBarKt {
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00ce, code lost:
    
        if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L66;
     */
    /* renamed from: SeslSingleRowTopAppBar-iHT-50w, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m3318SeslSingleRowTopAppBariHT50w(final androidx.compose.runtime.internal.ComposableLambdaImpl r19, final kotlin.jvm.functions.Function3 r20, final androidx.compose.foundation.layout.WindowInsets r21, final com.samsung.sesl.compose.component.SeslTopAppBarColors r22, final float r23, final androidx.compose.ui.Modifier.Companion r24, final androidx.compose.runtime.internal.ComposableLambdaImpl r25, androidx.compose.runtime.Composer r26, final int r27) {
        /*
            Method dump skipped, instructions count: 590
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.component.AppBarKt.m3318SeslSingleRowTopAppBariHT50w(androidx.compose.runtime.internal.ComposableLambdaImpl, kotlin.jvm.functions.Function3, androidx.compose.foundation.layout.WindowInsets, com.samsung.sesl.compose.component.SeslTopAppBarColors, float, androidx.compose.ui.Modifier$Companion, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer, int):void");
    }

    /* renamed from: SeslTopAppBar-au3_HiA, reason: not valid java name */
    public static final void m3319SeslTopAppBarau3_HiA(final ComposableLambdaImpl composableLambdaImpl, Modifier.Companion companion, final ComposableLambdaImpl composableLambdaImpl2, Function3 function3, final WindowInsets windowInsets, final SeslTopAppBarColors seslTopAppBarColors, float f, Composer composer, final int i, final int i2) {
        final Function3 function32;
        int i3;
        Function3 function33;
        SeslDpProducer seslDpProducer;
        int i4;
        Function3 function34;
        float f2;
        Modifier.Companion companion2;
        ComposerImpl composerImpl;
        final float f3;
        final Modifier.Companion companion3;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1528341034);
        int i5 = i | 48;
        if ((i & 384) == 0) {
            i5 |= composerImpl2.changedInstance(composableLambdaImpl2) ? 256 : 128;
        }
        int i6 = i2 & 8;
        if (i6 != 0) {
            i3 = i5 | 3072;
            function32 = function3;
        } else {
            function32 = function3;
            i3 = i5 | (composerImpl2.changedInstance(function32) ? 2048 : 1024);
        }
        int i7 = i3 | (composerImpl2.changed(windowInsets) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl2.changed(seslTopAppBarColors) ? 131072 : 65536) | NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        if ((599187 & i7) == 599186 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            companion3 = companion;
            f3 = f;
            composerImpl = composerImpl2;
        } else {
            composerImpl2.startDefaults();
            if ((i & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                Modifier.Companion companion4 = Modifier.Companion;
                if (i6 != 0) {
                    ComposableSingletons$AppBarKt.INSTANCE.getClass();
                    function33 = ComposableSingletons$AppBarKt.f119lambda2;
                } else {
                    function33 = function32;
                }
                SeslAppBarDimensionSchemeKeyTokens seslAppBarDimensionSchemeKeyTokens = SeslAppBarDimensionSchemeKeyTokens.TopAppBarTopPadding;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.theme.toDp (BasicDimensionScheme.kt:28)");
                }
                SeslDpProducer.Params params = new SeslDpProducer.Params((Configuration) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalConfiguration));
                ComputedProvidableCompositionLocal computedProvidableCompositionLocal = TokenSchemeKt.LocalSeslTokenScheme;
                SeslTokenScheme seslTokenScheme = (SeslTokenScheme) composerImpl2.consume(computedProvidableCompositionLocal);
                if (seslAppBarDimensionSchemeKeyTokens != null) {
                    seslTokenScheme.getAppBarTokens().getClass();
                } else {
                    DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                }
                SeslTokenScheme seslTokenScheme2 = (SeslTokenScheme) composerImpl2.consume(computedProvidableCompositionLocal);
                if (seslAppBarDimensionSchemeKeyTokens != null) {
                    SeslAppBarTokens appBarTokens = seslTokenScheme2.getAppBarTokens();
                    appBarTokens.getClass();
                    seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                } else {
                    seslDpProducer = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                }
                float mo3317produceu2uoSUM = seslDpProducer.mo3317produceu2uoSUM(params);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                i4 = i7 & (-3670017);
                function34 = function33;
                f2 = mo3317produceu2uoSUM;
                companion2 = companion4;
            } else {
                composerImpl2.skipToGroupEnd();
                i4 = i7 & (-3670017);
                companion2 = companion;
                f2 = f;
                function34 = function32;
            }
            composerImpl2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTopAppBar (AppBar.kt:141)");
            }
            int i8 = i4 >> 6;
            composerImpl = composerImpl2;
            m3318SeslSingleRowTopAppBariHT50w(composableLambdaImpl, function34, windowInsets, seslTopAppBarColors, f2, companion2, composableLambdaImpl2, composerImpl, 6 | (i8 & 112) | (i8 & 896) | (i8 & 7168) | 196608 | ((i4 << 12) & 3670016));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            function32 = function34;
            f3 = f2;
            companion3 = companion2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.AppBarKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    WindowInsets windowInsets2 = windowInsets;
                    SeslTopAppBarColors seslTopAppBarColors2 = seslTopAppBarColors;
                    float f4 = f3;
                    AppBarKt.m3319SeslTopAppBarau3_HiA(ComposableLambdaImpl.this, companion3, composableLambdaImpl2, function32, windowInsets2, seslTopAppBarColors2, f4, (Composer) obj, updateChangedFlags, i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
