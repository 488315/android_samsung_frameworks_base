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

/* loaded from: classes2.dex */
public final /* synthetic */ class UsbKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 36.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("Usb", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathFillType.Companion.getClass();
        int i = PathFillType.EvenOdd;
        PathBuilder pathBuilderM = ExpandMoreKt$$ExternalSyntheticOutline0.m(13.1622f, 27.5132f, 22.8377f, 27.5132f);
        pathBuilderM.lineTo(22.8377f, 14.067f);
        ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(pathBuilderM, 13.1622f, 14.067f, 13.1622f, 27.5132f);
        pathBuilderM.moveTo(23.1247f, 29.0132f);
        pathBuilderM.lineTo(12.8752f, 29.0132f);
        pathBuilderM.curveTo(12.2065f, 29.0132f, 11.6622f, 28.469f, 11.6622f, 27.8003f);
        pathBuilderM.lineTo(11.6622f, 13.78f);
        pathBuilderM.curveTo(11.6622f, 13.1112f, 12.2065f, 12.567f, 12.8752f, 12.567f);
        pathBuilderM.lineTo(23.1247f, 12.567f);
        pathBuilderM.curveTo(23.7935f, 12.567f, 24.3377f, 13.1112f, 24.3377f, 13.78f);
        pathBuilderM.lineTo(24.3377f, 27.8003f);
        pathBuilderM.curveTo(24.3377f, 28.469f, 23.7935f, 29.0132f, 23.1247f, 29.0132f);
        pathBuilderM.lineTo(23.1247f, 29.0132f);
        pathBuilderM.close();
        builder.m567addPathoIyEayM("", pathBuilderM._nodes, i, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        SolidColor solidColor3 = new SolidColor(ColorKt.Color(4278190080L), null);
        SolidColor solidColor4 = new SolidColor(ColorKt.Color(0), null);
        PathBuilder pathBuilderM2 = ExpandMoreKt$$ExternalSyntheticOutline0.m(22.4936f, 14.0608f, 20.9936f, 14.0608f);
        pathBuilderM2.lineTo(20.9936f, 8.4868f);
        pathBuilderM2.lineTo(15.0063f, 8.4868f);
        pathBuilderM2.lineTo(15.0063f, 14.0608f);
        pathBuilderM2.lineTo(13.5063f, 14.0608f);
        pathBuilderM2.lineTo(13.5063f, 7.9601f);
        pathBuilderM2.curveTo(13.5063f, 7.4233f, 13.9429f, 6.9868f, 14.4793f, 6.9868f);
        pathBuilderM2.lineTo(21.5206f, 6.9868f);
        pathBuilderM2.curveTo(22.0571f, 6.9868f, 22.4936f, 7.4233f, 22.4936f, 7.9601f);
        pathBuilderM2.lineTo(22.4936f, 14.0608f);
        pathBuilderM2.close();
        builder.m567addPathoIyEayM("", pathBuilderM2._nodes, i, solidColor3, 1.0f, solidColor4, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        SolidColor solidColor5 = new SolidColor(ColorKt.Color(4278190080L), null);
        SolidColor solidColor6 = new SolidColor(ColorKt.Color(0), null);
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.moveTo(16.3468f, 11.909f);
        pathBuilder.lineToRelative(1.0f, 0.0f);
        pathBuilder.lineToRelative(0.0f, -2.324f);
        pathBuilder.lineToRelative(-1.0f, 0.0f);
        pathBuilder.close();
        builder.m567addPathoIyEayM("", pathBuilder._nodes, i, solidColor5, 1.0f, solidColor6, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        SolidColor solidColor7 = new SolidColor(ColorKt.Color(4278190080L), null);
        SolidColor solidColor8 = new SolidColor(ColorKt.Color(0), null);
        PathBuilder pathBuilder2 = new PathBuilder();
        pathBuilder2.moveTo(18.6908f, 11.909f);
        pathBuilder2.lineToRelative(1.0f, 0.0f);
        pathBuilder2.lineToRelative(0.0f, -2.324f);
        pathBuilder2.lineToRelative(-1.0f, 0.0f);
        pathBuilder2.close();
        builder.m567addPathoIyEayM("", pathBuilder2._nodes, i, solidColor7, 1.0f, solidColor8, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
