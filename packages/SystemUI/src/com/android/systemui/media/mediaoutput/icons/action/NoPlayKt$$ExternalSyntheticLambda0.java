package com.android.systemui.media.mediaoutput.icons.action;

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
public final /* synthetic */ class NoPlayKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("NoPlay", (float) 9.933d, (float) 13.958d, 9.933f, 13.958f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathFillType.Companion.getClass();
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.moveTo(2.006f, 13.75f);
        pathBuilder.lineTo(9.264f, 8.308f);
        pathBuilder.curveTo(10.156f, 7.717f, 10.156f, 6.241f, 9.264f, 5.65f);
        pathBuilder.lineTo(2.006f, 0.208f);
        pathBuilder.curveTo(1.115f, -0.383f, 0.0f, 0.356f, 0.0f, 1.537f);
        pathBuilder.lineTo(0.0f, 12.42f);
        pathBuilder.curveTo(0.0f, 13.602f, 1.115f, 14.341f, 2.006f, 13.75f);
        builder.m565addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 0.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
