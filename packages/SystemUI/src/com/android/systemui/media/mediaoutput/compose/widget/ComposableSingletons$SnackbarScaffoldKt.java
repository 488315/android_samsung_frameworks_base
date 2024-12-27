package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.SnackbarData;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.SnackbarKt;
import androidx.compose.material3.SnackbarVisuals;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ComposableSingletons$SnackbarScaffoldKt {
    public static final ComposableSingletons$SnackbarScaffoldKt INSTANCE = new ComposableSingletons$SnackbarScaffoldKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f71lambda1 = new ComposableLambdaImpl(1875372786, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$SnackbarScaffoldKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            SnackbarData snackbarData = (SnackbarData) obj;
            Composer composer = (Composer) obj2;
            int intValue = ((Number) obj3).intValue();
            if ((intValue & 14) == 0) {
                intValue |= ((ComposerImpl) composer).changed(snackbarData) ? 4 : 2;
            }
            if ((intValue & 91) == 18) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            SnackbarVisuals snackbarVisuals = ((SnackbarHostState.SnackbarDataImpl) snackbarData).visuals;
            if (snackbarVisuals instanceof DialogInterface) {
                ComposerImpl composerImpl2 = (ComposerImpl) composer;
                composerImpl2.startReplaceGroup(852574053);
                SnackbarDialogKt.SnackbarDialog((DialogInterface) snackbarVisuals, snackbarData, composerImpl2, (intValue << 3) & 112);
                composerImpl2.end(false);
            } else {
                ComposerImpl composerImpl3 = (ComposerImpl) composer;
                composerImpl3.startReplaceGroup(852574168);
                Dp.Companion companion = Dp.Companion;
                SnackbarKt.m245SnackbarsDKtq54(snackbarData, null, true, RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(22), ColorKt.Color(4282861386L), ColorKt.Color(4294638335L), ColorKt.Color(4283928575L), 0L, 0L, composerImpl3, (intValue & 14) | 1794432, 386);
                composerImpl3.end(false);
            }
            return Unit.INSTANCE;
        }
    });
}
