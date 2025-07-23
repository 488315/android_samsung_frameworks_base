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
public final /* synthetic */ class FastRewindKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 40.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("FastRewind", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathBuilder m = EqualizerPlayingKt$$ExternalSyntheticOutline0.m(PathFillType.Companion, 18.3344f, 11.8536f);
        m.curveTo(18.0415f, 11.8536f, 17.7471f, 11.9458f, 17.4649f, 12.1301f);
        m.lineTo(9.0444f, 18.7382f);
        m.curveTo(8.57f, 19.0404f, 8.3f, 19.5013f, 8.3f, 20.0f);
        m.curveTo(8.3f, 20.4972f, 8.57f, 20.955f, 9.0444f, 21.2603f);
        m.lineTo(17.4649f, 27.8714f);
        m.curveTo(17.7486f, 28.0542f, 18.0415f, 28.1449f, 18.3344f, 28.1449f);
        m.curveTo(18.9858f, 28.1449f, 19.6402f, 27.6477f, 19.6402f, 26.6957f);
        m.lineTo(19.6402f, 21.5111f);
        m.lineTo(28.7914f, 28.225f);
        m.curveTo(29.0766f, 28.4078f, 29.3665f, 28.5f, 29.6609f, 28.5f);
        m.curveTo(30.3122f, 28.5f, 30.9667f, 28.0028f, 30.9667f, 27.0508f);
        m.lineTo(30.9667f, 12.9476f);
        m.curveTo(30.9667f, 11.9972f, 30.3122f, 11.5f, 29.6609f, 11.5f);
        m.curveTo(29.3665f, 11.5f, 29.0751f, 11.5907f, 28.7914f, 11.775f);
        m.lineTo(19.6402f, 18.4874f);
        m.lineTo(19.6402f, 13.3012f);
        m.curveTo(19.6402f, 12.3508f, 18.9858f, 11.8536f, 18.3344f, 11.8536f);
        builder.m565addPathoIyEayM("", m._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
