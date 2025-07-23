package com.android.systemui.media.mediaoutput.icons.device;

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
public final /* synthetic */ class MobileDeviceKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 36.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("MobileDevice", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathBuilder m = EqualizerPlayingKt$$ExternalSyntheticOutline0.m(PathFillType.Companion, 22.76f, 7.35f);
        m.curveTo(24.155f, 7.35f, 25.29f, 8.485f, 25.29f, 9.879f);
        m.lineTo(25.29f, 26.119f);
        m.curveTo(25.29f, 27.515f, 24.155f, 28.65f, 22.76f, 28.65f);
        m.lineTo(13.24f, 28.65f);
        m.curveTo(11.845f, 28.65f, 10.71f, 27.515f, 10.71f, 26.119f);
        m.lineTo(10.71f, 9.879f);
        m.curveTo(10.71f, 8.485f, 11.845f, 7.35f, 13.24f, 7.35f);
        WidgetsKt$$ExternalSyntheticOutline0.m(m, 22.76f, 7.35f, 22.76f, 9.049f);
        m.lineTo(13.24f, 9.049f);
        m.curveTo(12.782f, 9.049f, 12.41f, 9.422f, 12.41f, 9.879f);
        m.lineTo(12.41f, 26.119f);
        m.curveTo(12.41f, 26.577f, 12.782f, 26.95f, 13.24f, 26.95f);
        m.lineTo(22.76f, 26.95f);
        m.curveTo(23.218f, 26.95f, 23.59f, 26.577f, 23.59f, 26.119f);
        m.lineTo(23.59f, 9.879f);
        m.curveTo(23.59f, 9.422f, 23.218f, 9.049f, 22.76f, 9.049f);
        m.close();
        m.moveTo(19.7892f, 23.1908f);
        m.curveTo(20.2582f, 23.1908f, 20.6392f, 23.5708f, 20.6392f, 24.0398f);
        m.curveTo(20.6392f, 24.5098f, 20.2582f, 24.8898f, 19.7892f, 24.8898f);
        m.lineTo(16.2112f, 24.8898f);
        m.curveTo(15.7422f, 24.8898f, 15.3612f, 24.5098f, 15.3612f, 24.0398f);
        m.curveTo(15.3612f, 23.5708f, 15.7422f, 23.1908f, 16.2112f, 23.1908f);
        m.lineTo(19.7892f, 23.1908f);
        m.close();
        builder.m565addPathoIyEayM("", m._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
