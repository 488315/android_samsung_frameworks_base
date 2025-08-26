package com.samsung.sesl.compose.component;

import android.content.res.Configuration;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.jvm.functions.Function2;

/* loaded from: classes4.dex */
public final class SeslProgressIndicatorDefaults$TrackWidthSelector$1 implements Function2 {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new SeslProgressIndicatorDefaults$TrackWidthSelector$1();
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        ((Number) obj2).intValue();
        ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj);
        composerImpl.startReplaceGroup(-1411302746);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslProgressIndicatorDefaults.TrackWidthSelector.<anonymous> (ProgressIndicator.kt:261)");
        }
        float f = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).screenWidthDp;
        Dp.Companion companion = Dp.Companion;
        float f2 = Float.compare(f, (float) 590) <= 0 ? IKnoxCustomManager.Stub.TRANSACTION_registerSystemUiCallback : Float.compare(f, (float) 870) <= 0 ? VolteConstants.ErrorCode.PRECONDITION_FAILURE : Float.compare(f, (float) 1170) <= 0 ? 860 : Float.compare(f, (float) 1818) <= 0 ? 1160 : 1808;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return Dp.m837boximpl(f2);
    }
}
