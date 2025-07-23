package com.samsung.sesl.compose.component;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.samsung.sesl.compose.theme.SeslTheme;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ProgressIndicatorKt {
    /* renamed from: SeslIndeterminateCircularProgressIndicator-h1eT-Ww, reason: not valid java name */
    public static final void m3322SeslIndeterminateCircularProgressIndicatorh1eTWw(float f, final int i, long j, long j2, Composer composer, final Modifier.Companion companion) {
        int i2;
        float f2;
        final long j3;
        final long j4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(558863013);
        int i3 = i | VolteConstants.ErrorCode.NOT_ACCEPTABLE | (composerImpl.changed(f) ? 2048 : 1024);
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            f2 = f;
            j4 = j2;
            j3 = j;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                companion = Modifier.Companion;
                SeslTheme.INSTANCE.getClass();
                j = SeslTheme.getColorScheme(composerImpl).primary;
                i2 = i3 & (-113);
                SeslProgressIndicatorDefaults.INSTANCE.getClass();
                j2 = SeslProgressIndicatorDefaults.indeterminateCircularPointColor;
            } else {
                composerImpl.skipToGroupEnd();
                i2 = i3 & (-113);
            }
            long j5 = j;
            long j6 = j2;
            Modifier.Companion companion2 = companion;
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslIndeterminateCircularProgressIndicator (ProgressIndicator.kt:38)");
            }
            f2 = f;
            BasicProgressIndicatorKt.m3320SeslBasicIndeterminateCircularProgressIndicatorZO3OeZo(f2, ((i2 >> 3) & 1022) | 3072, j5, j6, composerImpl, companion2);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            j3 = j5;
            j4 = j6;
            companion = companion2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final float f3 = f2;
            endRestartGroup.block = new Function2(f3, i, j3, j4, companion) { // from class: com.samsung.sesl.compose.component.ProgressIndicatorKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Modifier.Companion f$0;
                public final /* synthetic */ long f$1;
                public final /* synthetic */ long f$2;
                public final /* synthetic */ float f$3;

                {
                    this.f$0 = companion;
                    this.f$1 = j3;
                    this.f$2 = j4;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    long j7 = this.f$2;
                    float f4 = this.f$3;
                    Modifier.Companion companion3 = this.f$0;
                    ProgressIndicatorKt.m3322SeslIndeterminateCircularProgressIndicatorh1eTWw(f4, updateChangedFlags, this.f$1, j7, (Composer) obj, companion3);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
