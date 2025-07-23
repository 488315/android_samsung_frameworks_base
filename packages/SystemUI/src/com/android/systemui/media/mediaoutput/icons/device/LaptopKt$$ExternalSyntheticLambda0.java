package com.android.systemui.media.mediaoutput.icons.device;

import androidx.compose.material.icons.outlined.WidgetsKt$$ExternalSyntheticOutline0;
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
public final /* synthetic */ class LaptopKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 36.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("Laptop", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathFillType.Companion.getClass();
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.moveTo(9.3447f, 11.28f);
        pathBuilder.lineTo(26.6557f, 11.28f);
        pathBuilder.lineTo(26.6537f, 22.2099f);
        ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(pathBuilder, 9.3447f, 22.2099f, 9.3447f, 11.28f);
        pathBuilder.moveTo(26.6557f, 22.2099f);
        ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(pathBuilder, 26.6557f, 23.0499f, 26.6557f, 22.2099f);
        pathBuilder.moveTo(9.3447f, 23.889f);
        pathBuilder.lineTo(26.6557f, 23.889f);
        pathBuilder.curveTo(27.5827f, 23.889f, 28.3357f, 23.1349f, 28.3357f, 22.2099f);
        pathBuilder.lineTo(28.3357f, 11.28f);
        pathBuilder.curveTo(28.3357f, 10.3539f, 27.5827f, 9.5999f, 26.6557f, 9.5999f);
        pathBuilder.lineTo(9.3447f, 9.5999f);
        pathBuilder.curveTo(8.4177f, 9.5999f, 7.6647f, 10.3539f, 7.6647f, 11.28f);
        pathBuilder.lineTo(7.6647f, 22.2099f);
        pathBuilder.curveTo(7.6647f, 23.1349f, 8.4177f, 23.889f, 9.3447f, 23.889f);
        WidgetsKt$$ExternalSyntheticOutline0.m(pathBuilder, 9.3447f, 23.889f, 6.8f, 26.4001f);
        pathBuilder.lineTo(29.2f, 26.4001f);
        pathBuilder.lineTo(29.2f, 24.7201f);
        ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(pathBuilder, 6.8f, 24.7201f, 6.8f, 26.4001f);
        builder.m565addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
