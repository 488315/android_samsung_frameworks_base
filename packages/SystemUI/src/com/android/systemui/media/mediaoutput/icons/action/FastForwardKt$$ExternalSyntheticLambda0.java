package com.android.systemui.media.mediaoutput.icons.action;

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
public final /* synthetic */ class FastForwardKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 40.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("FastForward", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathBuilder m = EqualizerPlayingKt$$ExternalSyntheticOutline0.m(PathFillType.Companion, 9.03f, 27.0508f);
        m.curveTo(9.03f, 28.0028f, 9.6859f, 28.5f, 10.3358f, 28.5f);
        m.curveTo(10.6287f, 28.5f, 10.92f, 28.4078f, 11.2053f, 28.225f);
        m.lineTo(20.3565f, 21.5111f);
        m.lineTo(20.3565f, 26.6972f);
        m.curveTo(20.3565f, 27.6492f, 21.0109f, 28.1464f, 21.6623f, 28.1464f);
        m.curveTo(21.9567f, 28.1464f, 22.248f, 28.0542f, 22.5318f, 27.8714f);
        m.lineTo(30.9522f, 21.2603f);
        m.curveTo(31.4267f, 20.958f, 31.6967f, 20.4987f, 31.6967f, 20.0f);
        m.curveTo(31.6967f, 19.5013f, 31.4267f, 19.042f, 30.9522f, 18.7382f);
        m.lineTo(22.5318f, 12.1301f);
        m.curveTo(22.248f, 11.9458f, 21.9567f, 11.8536f, 21.6623f, 11.8536f);
        m.curveTo(21.0109f, 11.8536f, 20.3565f, 12.3508f, 20.3565f, 13.3012f);
        m.lineTo(20.3565f, 18.4874f);
        m.lineTo(11.2053f, 11.7765f);
        m.curveTo(10.9216f, 11.5922f, 10.6287f, 11.5f, 10.3358f, 11.5f);
        m.curveTo(9.6859f, 11.5f, 9.03f, 11.9972f, 9.03f, 12.9476f);
        m.lineTo(9.03f, 27.0508f);
        m.close();
        builder.m565addPathoIyEayM("", m._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
