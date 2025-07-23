package com.samsung.sesl.compose.component;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class BasicProgressIndicatorKt {
    /* renamed from: SeslBasicIndeterminateCircularProgressIndicator-ZO3OeZo, reason: not valid java name */
    public static final void m3320SeslBasicIndeterminateCircularProgressIndicatorZO3OeZo(final float f, final int i, final long j, final long j2, Composer composer, final Modifier.Companion companion) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(188250181);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(j) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(j2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(f) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(companion) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslBasicIndeterminateCircularProgressIndicator (BasicProgressIndicator.kt:65)");
            }
            m3321SeslBasicIndeterminateCircularProgressIndicatoryA8G38M(j, j2, f, companion, 0.0f, 0.0f, 0.0f, composerImpl, i2 & 8190);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.BasicProgressIndicatorKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    float f2 = f;
                    BasicProgressIndicatorKt.m3320SeslBasicIndeterminateCircularProgressIndicatorZO3OeZo(f2, updateChangedFlags, j, j2, (Composer) obj, companion);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x013e, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01cf, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L78;
     */
    /* renamed from: SeslBasicIndeterminateCircularProgressIndicator-yA8G38M, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m3321SeslBasicIndeterminateCircularProgressIndicatoryA8G38M(final long r33, final long r35, final float r37, final androidx.compose.ui.Modifier.Companion r38, float r39, float r40, float r41, androidx.compose.runtime.Composer r42, final int r43) {
        /*
            Method dump skipped, instructions count: 525
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.component.BasicProgressIndicatorKt.m3321SeslBasicIndeterminateCircularProgressIndicatoryA8G38M(long, long, float, androidx.compose.ui.Modifier$Companion, float, float, float, androidx.compose.runtime.Composer, int):void");
    }
}
