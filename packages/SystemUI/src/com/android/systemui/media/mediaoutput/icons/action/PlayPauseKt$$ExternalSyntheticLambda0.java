package com.android.systemui.media.mediaoutput.icons.action;

import androidx.compose.material.icons.outlined.WidgetsKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.media.mediaoutput.icons.EqualizerPlayingKt$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class PlayPauseKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 24.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("PlayPause", f, f, 24.0f, 24.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4294638335L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathBuilder pathBuilderM = EqualizerPlayingKt$$ExternalSyntheticOutline0.m(PathFillType.Companion, 14.0f, 6.0f);
        pathBuilderM.curveTo(14.5089f, 6.0f, 14.935f, 6.3634f, 14.9937f, 6.8448f);
        pathBuilderM.lineTo(15.0f, 6.9511f);
        pathBuilderM.lineTo(15.0f, 16.5489f);
        pathBuilderM.curveTo(15.0f, 17.0742f, 14.5523f, 17.5f, 14.0f, 17.5f);
        pathBuilderM.curveTo(13.4911f, 17.5f, 13.065f, 17.1366f, 13.0063f, 16.6552f);
        pathBuilderM.lineTo(13.0f, 16.5489f);
        pathBuilderM.lineTo(12.9992f, 12.784f);
        pathBuilderM.lineTo(6.4716f, 17.1132f);
        pathBuilderM.curveTo(5.8699f, 17.5123f, 5.0729f, 17.1192f, 5.0047f, 16.4261f);
        pathBuilderM.lineTo(5.0f, 16.3298f);
        pathBuilderM.lineTo(5.0f, 7.1156f);
        pathBuilderM.curveTo(5.0f, 6.3636f, 5.8426f, 5.915f, 6.4716f, 6.3322f);
        pathBuilderM.lineTo(12.9992f, 10.6608f);
        pathBuilderM.lineTo(13.0f, 6.9511f);
        pathBuilderM.curveTo(13.0f, 6.4586f, 13.3935f, 6.0536f, 13.8978f, 6.0049f);
        WidgetsKt$$ExternalSyntheticOutline0.m(pathBuilderM, 14.0f, 6.0f, 18.0f, 6.0f);
        pathBuilderM.curveTo(17.4477f, 6.0f, 17.0f, 6.4258f, 17.0f, 6.9511f);
        pathBuilderM.lineTo(17.0f, 16.5489f);
        pathBuilderM.lineTo(17.0063f, 16.6552f);
        pathBuilderM.curveTo(17.065f, 17.1366f, 17.4911f, 17.5f, 18.0f, 17.5f);
        pathBuilderM.curveTo(18.5523f, 17.5f, 19.0f, 17.0742f, 19.0f, 16.5489f);
        pathBuilderM.lineTo(19.0f, 6.9511f);
        pathBuilderM.lineTo(18.9937f, 6.8448f);
        pathBuilderM.curveTo(18.935f, 6.3634f, 18.5089f, 6.0f, 18.0f, 6.0f);
        pathBuilderM.close();
        builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
