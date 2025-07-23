package com.samsung.sesl.compose.component;

import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ScaffoldKt {
    public static final float FabSpacing;
    public static final StaticProvidableCompositionLocal LocalSeslFabPlacement = new StaticProvidableCompositionLocal(new ScaffoldKt$$ExternalSyntheticLambda1());

    static {
        Dp.Companion companion = Dp.Companion;
        FabSpacing = 16;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0082  */
    /* renamed from: SeslScaffold-5k0As8s, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m3323SeslScaffold5k0As8s(androidx.compose.ui.Modifier r28, kotlin.jvm.functions.Function2 r29, androidx.compose.runtime.internal.ComposableLambdaImpl r30, androidx.compose.runtime.internal.ComposableLambdaImpl r31, androidx.compose.runtime.internal.ComposableLambdaImpl r32, kotlin.jvm.functions.Function4 r33, int r34, long r35, androidx.compose.foundation.layout.WindowInsets r37, androidx.compose.runtime.internal.ComposableLambdaImpl r38, androidx.compose.runtime.Composer r39, int r40, int r41) {
        /*
            Method dump skipped, instructions count: 389
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.component.ScaffoldKt.m3323SeslScaffold5k0As8s(androidx.compose.ui.Modifier, kotlin.jvm.functions.Function2, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.internal.ComposableLambdaImpl, kotlin.jvm.functions.Function4, int, long, androidx.compose.foundation.layout.WindowInsets, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer, int, int):void");
    }

    /* renamed from: SeslScaffoldImpl-5k0As8s, reason: not valid java name */
    public static final void m3324SeslScaffoldImpl5k0As8s(Modifier modifier, Function2 function2, ComposableLambdaImpl composableLambdaImpl, ComposableLambdaImpl composableLambdaImpl2, ComposableLambdaImpl composableLambdaImpl3, Function4 function4, int i, long j, WindowInsets windowInsets, ComposableLambdaImpl composableLambdaImpl4, Composer composer, int i2, int i3) {
        int i4;
        int i5;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1990694873);
        if ((i2 & 48) == 0) {
            i4 = (composerImpl.changedInstance(function2) ? 32 : 16) | i2;
        } else {
            i4 = i2;
        }
        if ((i2 & 384) == 0) {
            i4 |= composerImpl.changedInstance(composableLambdaImpl) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i4 |= composerImpl.changedInstance(composableLambdaImpl2) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i4 |= composerImpl.changedInstance(composableLambdaImpl3) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i2) == 0) {
            i4 |= composerImpl.changedInstance(function4) ? 131072 : 65536;
        }
        if ((1572864 & i2) == 0) {
            i4 |= composerImpl.changed(i) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((100663296 & i2) == 0) {
            i4 |= composerImpl.changed(windowInsets) ? 67108864 : 33554432;
        }
        if ((805306368 & i2) == 0) {
            i4 |= (1073741824 & i2) == 0 ? composerImpl.changed((Object) null) : composerImpl.changedInstance(null) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        if ((i3 & 6) == 0) {
            i5 = i3 | (composerImpl.changedInstance(composableLambdaImpl4) ? 4 : 2);
        } else {
            i5 = i3;
        }
        if ((302589073 & i4) == 302589072 && (i5 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i2 & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScaffoldImpl (Scaffold.kt:98)");
            }
            m3325SeslScaffoldLayoutvZ1zQFI(i, function2, composableLambdaImpl4, composableLambdaImpl2, composableLambdaImpl3, windowInsets, composableLambdaImpl, function4, composerImpl, ((i5 << 6) & 896) | ((i4 >> 18) & 14) | (i4 & 112) | (i4 & 7168) | (57344 & i4) | ((i4 >> 9) & 458752) | ((i4 << 12) & 3670016) | ((i4 << 6) & 29360128) | ((i4 >> 3) & 234881024));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ScaffoldKt$$ExternalSyntheticLambda0(modifier, function2, composableLambdaImpl, composableLambdaImpl2, composableLambdaImpl3, function4, i, j, windowInsets, composableLambdaImpl4, i2, i3, 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x018e  */
    /* renamed from: SeslScaffoldLayout-vZ1zQFI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m3325SeslScaffoldLayoutvZ1zQFI(final int r20, final kotlin.jvm.functions.Function2 r21, final androidx.compose.runtime.internal.ComposableLambdaImpl r22, final androidx.compose.runtime.internal.ComposableLambdaImpl r23, final androidx.compose.runtime.internal.ComposableLambdaImpl r24, final androidx.compose.foundation.layout.WindowInsets r25, final androidx.compose.runtime.internal.ComposableLambdaImpl r26, final kotlin.jvm.functions.Function4 r27, androidx.compose.runtime.Composer r28, final int r29) {
        /*
            Method dump skipped, instructions count: 431
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.component.ScaffoldKt.m3325SeslScaffoldLayoutvZ1zQFI(int, kotlin.jvm.functions.Function2, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.foundation.layout.WindowInsets, androidx.compose.runtime.internal.ComposableLambdaImpl, kotlin.jvm.functions.Function4, androidx.compose.runtime.Composer, int):void");
    }
}
