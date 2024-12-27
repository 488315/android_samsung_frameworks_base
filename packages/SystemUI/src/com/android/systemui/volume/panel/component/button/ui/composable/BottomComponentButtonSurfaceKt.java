package com.android.systemui.volume.panel.component.button.ui.composable;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public abstract class BottomComponentButtonSurfaceKt {
    public static final void BottomComponentButtonSurface(Modifier modifier, final Function2 function2, Composer composer, final int i, final int i2) {
        final Modifier modifier2;
        int i3;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-667898118);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
            modifier2 = modifier;
        } else if ((i & 14) == 0) {
            modifier2 = modifier;
            i3 = (composerImpl2.changed(modifier2) ? 4 : 2) | i;
        } else {
            modifier2 = modifier;
            i3 = i;
        }
        if ((2 & i2) != 0) {
            i3 |= 48;
        } else if ((i & 112) == 0) {
            i3 |= composerImpl2.changedInstance(function2) ? 32 : 16;
        }
        if ((i3 & 91) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            Modifier modifier3 = i4 != 0 ? Modifier.Companion : modifier2;
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Dp.Companion companion = Dp.Companion;
            Modifier m108height3ABfNKs = SizeKt.m108height3ABfNKs(modifier3, 64);
            RoundedCornerShape m151RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(28);
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            SurfaceKt.m248SurfaceT9BRK9s(m108height3ABfNKs, m151RoundedCornerShape0680j_4, MaterialTheme.getColorScheme(composerImpl2).surface, 0L, 0.0f, 0.0f, null, function2, composerImpl2, (i3 << 18) & 29360128, 120);
            modifier2 = modifier3;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.BottomComponentButtonSurfaceKt$BottomComponentButtonSurface$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    BottomComponentButtonSurfaceKt.BottomComponentButtonSurface(Modifier.this, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
