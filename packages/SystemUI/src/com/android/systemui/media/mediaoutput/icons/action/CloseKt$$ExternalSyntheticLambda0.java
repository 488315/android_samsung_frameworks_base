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

/* loaded from: classes2.dex */
public final /* synthetic */ class CloseKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 32.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("Close", f, f, 32.0f, 32.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4278255874L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathFillType.Companion.getClass();
        int i = PathFillType.EvenOdd;
        PathBuilder pathBuilderM = ExpandMoreKt$$ExternalSyntheticOutline0.m(17.413f, 15.999f, 25.375f, 8.04f);
        pathBuilderM.curveTo(25.765f, 7.65f, 25.765f, 7.018f, 25.375f, 6.627f);
        pathBuilderM.curveTo(24.984f, 6.236f, 24.349f, 6.236f, 23.96f, 6.627f);
        pathBuilderM.lineTo(15.999f, 14.584f);
        pathBuilderM.lineTo(8.04f, 6.627f);
        pathBuilderM.curveTo(7.649f, 6.236f, 7.016f, 6.236f, 6.625f, 6.627f);
        pathBuilderM.curveTo(6.235f, 7.018f, 6.235f, 7.65f, 6.625f, 8.04f);
        pathBuilderM.lineTo(14.585f, 15.999f);
        pathBuilderM.lineTo(6.625f, 23.96f);
        pathBuilderM.curveTo(6.235f, 24.351f, 6.235f, 24.983f, 6.625f, 25.374f);
        pathBuilderM.curveTo(6.821f, 25.57f, 7.076f, 25.666f, 7.332f, 25.666f);
        pathBuilderM.curveTo(7.589f, 25.666f, 7.844f, 25.57f, 8.04f, 25.374f);
        pathBuilderM.lineTo(15.999f, 17.415f);
        pathBuilderM.lineTo(23.96f, 25.374f);
        pathBuilderM.curveTo(24.155f, 25.57f, 24.411f, 25.666f, 24.667f, 25.666f);
        pathBuilderM.curveTo(24.923f, 25.666f, 25.179f, 25.57f, 25.375f, 25.374f);
        pathBuilderM.curveTo(25.765f, 24.983f, 25.765f, 24.351f, 25.375f, 23.96f);
        pathBuilderM.lineTo(17.413f, 15.999f);
        pathBuilderM.close();
        builder.m567addPathoIyEayM("", pathBuilderM._nodes, i, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
