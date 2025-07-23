package com.android.systemui.media.mediaoutput.icons.device;

import androidx.compose.material.icons.filled.ExpandMoreKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0;
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
public final /* synthetic */ class HdmiKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 36.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("Hdmi", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathFillType.Companion.getClass();
        int i = PathFillType.EvenOdd;
        PathBuilder m = ExpandMoreKt$$ExternalSyntheticOutline0.m(8.1087f, 15.0812f, 8.1087f, 17.971f);
        m.curveTo(8.1087f, 19.5965f, 9.4312f, 20.919f, 11.0567f, 20.919f);
        m.lineTo(24.9434f, 20.919f);
        m.curveTo(26.569f, 20.919f, 27.8912f, 19.5965f, 27.8912f, 17.971f);
        ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(m, 27.8912f, 15.0812f, 8.1087f, 15.0812f);
        m.moveTo(24.9434f, 22.419f);
        m.lineTo(11.0567f, 22.419f);
        m.curveTo(8.6039f, 22.419f, 6.6087f, 20.4235f, 6.6087f, 17.971f);
        m.lineTo(6.6087f, 14.914f);
        m.curveTo(6.6087f, 14.1792f, 7.2067f, 13.5812f, 7.9417f, 13.5812f);
        m.lineTo(28.0585f, 13.5812f);
        m.curveTo(28.7934f, 13.5812f, 29.3912f, 14.1792f, 29.3912f, 14.914f);
        m.lineTo(29.3912f, 17.971f);
        m.curveTo(29.3912f, 20.4235f, 27.396f, 22.419f, 24.9434f, 22.419f);
        m.lineTo(24.9434f, 22.419f);
        m.close();
        builder.m565addPathoIyEayM("", m._nodes, i, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        SolidColor solidColor3 = new SolidColor(ColorKt.Color(4278190080L), null);
        SolidColor solidColor4 = new SolidColor(ColorKt.Color(0), null);
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.moveTo(9.8727f, 18.5783f);
        pathBuilder.lineToRelative(16.3075f, 0.0f);
        pathBuilder.lineToRelative(0.0f, -1.25f);
        pathBuilder.lineToRelative(-16.3075f, 0.0f);
        pathBuilder.close();
        builder.m565addPathoIyEayM("", pathBuilder._nodes, i, solidColor3, 1.0f, solidColor4, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
