package com.android.systemui.statusbar.policy.ui.dialog.composable;

import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModeTileViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ModeTileKt {
    public static final void ModeTile(final ModeTileViewModel modeTileViewModel, final Modifier modifier, Composer composer, final int i, final int i2) {
        long j;
        long j2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1313864703);
        int i3 = (composerImpl.changed(modeTileViewModel) ? 4 : 2) | i;
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i3 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTile (ModeTile.kt:48)");
            }
            if (modeTileViewModel.enabled) {
                composerImpl.startReplaceGroup(-358683747);
                MaterialTheme.INSTANCE.getClass();
                j = MaterialTheme.getColorScheme(composerImpl).primary;
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-358682108);
                MaterialTheme.INSTANCE.getClass();
                j = MaterialTheme.getColorScheme(composerImpl).surfaceVariant;
                composerImpl.end(false);
            }
            final State m7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(j, null, null, composerImpl, 0, 14);
            if (modeTileViewModel.enabled) {
                composerImpl.startReplaceGroup(-358677441);
                MaterialTheme.INSTANCE.getClass();
                j2 = MaterialTheme.getColorScheme(composerImpl).onPrimary;
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-358675738);
                MaterialTheme.INSTANCE.getClass();
                j2 = MaterialTheme.getColorScheme(composerImpl).onSurfaceVariant;
                composerImpl.end(false);
            }
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(((Color) SingleValueAnimationKt.m7animateColorAsStateeuL9pac(j2, null, null, composerImpl, 0, 14).getValue()).value)), ComposableLambdaKt.rememberComposableLambda(1382141759, new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileKt$ModeTile$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTile.<anonymous> (ModeTile.kt:61)");
                    }
                    long j3 = ((Color) m7animateColorAsStateeuL9pac.getValue()).value;
                    Dp.Companion companion = Dp.Companion;
                    RoundedCornerShape m186RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(16);
                    final ModeTileViewModel modeTileViewModel2 = modeTileViewModel;
                    SurfaceKt.m303SurfaceT9BRK9s(Modifier.this, m186RoundedCornerShape0680j_4, j3, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-327673852, new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileKt$ModeTile$1.1
                        /* JADX WARN: Code restructure failed: missing block: B:15:0x0062, code lost:
                        
                            if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:38:0x01a0, code lost:
                        
                            if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L44;
                         */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invoke(java.lang.Object r33, java.lang.Object r34) {
                            /*
                                Method dump skipped, instructions count: 496
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileKt$ModeTile$1.AnonymousClass1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                        }
                    }, composer2), composer2, 12582912, 120);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Modifier modifier2 = modifier;
                    int i5 = i2;
                    ModeTileKt.ModeTile(ModeTileViewModel.this, modifier2, (Composer) obj, updateChangedFlags, i5);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
