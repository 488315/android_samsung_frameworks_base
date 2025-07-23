package com.android.systemui.media.mediaoutput.icons.action;

import androidx.compose.material.icons.filled.ExpandMoreKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolMuteKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 24.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("VolMute", f, f, 24.0f, 24.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(0), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(4278190080L), null);
        StrokeCap.Companion.getClass();
        int i = StrokeCap.Round;
        StrokeJoin.Companion.getClass();
        int i2 = StrokeJoin.Round;
        PathFillType.Companion.getClass();
        int i3 = PathFillType.EvenOdd;
        PathBuilder m = ExpandMoreKt$$ExternalSyntheticOutline0.m(14.2193f, 5.1903f, 9.3813f, 9.0603f);
        m.lineTo(6.2503f, 9.0603f);
        m.curveTo(5.4213f, 9.0603f, 4.7503f, 9.7323f, 4.7503f, 10.5603f);
        m.lineTo(4.7503f, 13.8103f);
        m.curveTo(4.7503f, 14.6383f, 5.4213f, 15.3103f, 6.2503f, 15.3103f);
        m.lineTo(9.3783f, 15.3103f);
        m.lineTo(14.2193f, 19.6853f);
        m.curveTo(14.8333f, 20.2363f, 15.3353f, 20.0133f, 15.3353f, 19.1883f);
        m.lineTo(15.3353f, 5.6883f);
        m.curveTo(15.3353f, 4.8633f, 14.8333f, 4.6393f, 14.2193f, 5.1903f);
        m.close();
        m.moveTo(5.4757f, 4.7499f);
        m.lineTo(19.7207f, 18.7499f);
        builder.m565addPathoIyEayM("", m._nodes, i3, solidColor, 1.0f, solidColor2, 1.0f, 1.5f, i, i2, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
