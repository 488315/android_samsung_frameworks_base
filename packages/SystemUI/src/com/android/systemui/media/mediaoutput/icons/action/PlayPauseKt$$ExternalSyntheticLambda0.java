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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        PathBuilder m = EqualizerPlayingKt$$ExternalSyntheticOutline0.m(PathFillType.Companion, 14.0f, 6.0f);
        m.curveTo(14.5089f, 6.0f, 14.935f, 6.3634f, 14.9937f, 6.8448f);
        m.lineTo(15.0f, 6.9511f);
        m.lineTo(15.0f, 16.5489f);
        m.curveTo(15.0f, 17.0742f, 14.5523f, 17.5f, 14.0f, 17.5f);
        m.curveTo(13.4911f, 17.5f, 13.065f, 17.1366f, 13.0063f, 16.6552f);
        m.lineTo(13.0f, 16.5489f);
        m.lineTo(12.9992f, 12.784f);
        m.lineTo(6.4716f, 17.1132f);
        m.curveTo(5.8699f, 17.5123f, 5.0729f, 17.1192f, 5.0047f, 16.4261f);
        m.lineTo(5.0f, 16.3298f);
        m.lineTo(5.0f, 7.1156f);
        m.curveTo(5.0f, 6.3636f, 5.8426f, 5.915f, 6.4716f, 6.3322f);
        m.lineTo(12.9992f, 10.6608f);
        m.lineTo(13.0f, 6.9511f);
        m.curveTo(13.0f, 6.4586f, 13.3935f, 6.0536f, 13.8978f, 6.0049f);
        WidgetsKt$$ExternalSyntheticOutline0.m(m, 14.0f, 6.0f, 18.0f, 6.0f);
        m.curveTo(17.4477f, 6.0f, 17.0f, 6.4258f, 17.0f, 6.9511f);
        m.lineTo(17.0f, 16.5489f);
        m.lineTo(17.0063f, 16.6552f);
        m.curveTo(17.065f, 17.1366f, 17.4911f, 17.5f, 18.0f, 17.5f);
        m.curveTo(18.5523f, 17.5f, 19.0f, 17.0742f, 19.0f, 16.5489f);
        m.lineTo(19.0f, 6.9511f);
        m.lineTo(18.9937f, 6.8448f);
        m.curveTo(18.935f, 6.3634f, 18.5089f, 6.0f, 18.0f, 6.0f);
        m.close();
        builder.m565addPathoIyEayM("", m._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
