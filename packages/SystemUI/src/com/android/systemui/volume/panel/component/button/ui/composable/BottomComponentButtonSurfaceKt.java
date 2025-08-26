package com.android.systemui.volume.panel.component.button.ui.composable;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public abstract class BottomComponentButtonSurfaceKt {
    public static final void BottomComponentButtonSurface(final int i, Composer composer, final ComposableLambdaImpl composableLambdaImpl, Modifier modifier) {
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(579714087);
        if (((i | 6) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            modifier2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.button.ui.composable.BottomComponentButtonSurface (BottomComponentButtonSurface.kt:33)");
            }
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(modifier2, 64);
            RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(28);
            MaterialTheme.INSTANCE.getClass();
            SurfaceKt.m304SurfaceT9BRK9s(modifierM131height3ABfNKs, roundedCornerShapeM187RoundedCornerShape0680j_4, MaterialTheme.getColorScheme(composerImpl).surface, 0L, 0.0f, 0.0f, null, composableLambdaImpl, composerImpl, 12582912, 120);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(composableLambdaImpl, i) { // from class: com.android.systemui.volume.panel.component.button.ui.composable.BottomComponentButtonSurfaceKt$$ExternalSyntheticLambda0
                public final /* synthetic */ ComposableLambdaImpl f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    BottomComponentButtonSurfaceKt.BottomComponentButtonSurface(RecomposeScopeImplKt.updateChangedFlags(49), (Composer) obj, this.f$1, this.f$0);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
