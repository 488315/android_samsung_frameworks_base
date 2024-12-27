package com.android.systemui.media.mediaoutput.icons.device;

import androidx.compose.material.icons.filled.CloseKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class UsbKt {
    public static final Lazy Usb$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.device.UsbKt$Usb$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 36.0d;
            Dp.Companion companion = Dp.Companion;
            DefaultConstructorMarker defaultConstructorMarker = null;
            ImageVector.Builder builder = new ImageVector.Builder("Usb", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            int i = PathFillType.EvenOdd;
            PathBuilder m = CloseKt$$ExternalSyntheticOutline0.m(13.1622f, 27.5132f, 22.8377f, 27.5132f);
            m.lineTo(22.8377f, 14.067f);
            CloseKt$$ExternalSyntheticOutline0.m(m, 13.1622f, 14.067f, 13.1622f, 27.5132f);
            m.moveTo(23.1247f, 29.0132f);
            m.lineTo(12.8752f, 29.0132f);
            m.curveTo(12.2065f, 29.0132f, 11.6622f, 28.469f, 11.6622f, 27.8003f);
            m.lineTo(11.6622f, 13.78f);
            m.curveTo(11.6622f, 13.1112f, 12.2065f, 12.567f, 12.8752f, 12.567f);
            m.lineTo(23.1247f, 12.567f);
            m.curveTo(23.7935f, 12.567f, 24.3377f, 13.1112f, 24.3377f, 13.78f);
            m.lineTo(24.3377f, 27.8003f);
            m.curveTo(24.3377f, 28.469f, 23.7935f, 29.0132f, 23.1247f, 29.0132f);
            m.lineTo(23.1247f, 29.0132f);
            m.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, m._nodes, i, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            SolidColor solidColor3 = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor4 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            PathBuilder m2 = CloseKt$$ExternalSyntheticOutline0.m(22.4936f, 14.0608f, 20.9936f, 14.0608f);
            m2.lineTo(20.9936f, 8.4868f);
            m2.lineTo(15.0063f, 8.4868f);
            m2.lineTo(15.0063f, 14.0608f);
            m2.lineTo(13.5063f, 14.0608f);
            m2.lineTo(13.5063f, 7.9601f);
            m2.curveTo(13.5063f, 7.4233f, 13.9429f, 6.9868f, 14.4793f, 6.9868f);
            m2.lineTo(21.5206f, 6.9868f);
            m2.curveTo(22.0571f, 6.9868f, 22.4936f, 7.4233f, 22.4936f, 7.9601f);
            m2.lineTo(22.4936f, 14.0608f);
            m2.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, m2._nodes, i, solidColor3, solidColor4, 1.0f, 0, 0, 4.0f);
            SolidColor solidColor5 = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor6 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(16.3468f, 11.909f);
            pathBuilder.lineToRelative(1.0f, 0.0f);
            pathBuilder.lineToRelative(0.0f, -2.324f);
            pathBuilder.lineToRelative(-1.0f, 0.0f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, i, solidColor5, solidColor6, 1.0f, 0, 0, 4.0f);
            SolidColor solidColor7 = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor8 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            PathBuilder pathBuilder2 = new PathBuilder();
            pathBuilder2.moveTo(18.6908f, 11.909f);
            pathBuilder2.lineToRelative(1.0f, 0.0f);
            pathBuilder2.lineToRelative(0.0f, -2.324f);
            pathBuilder2.lineToRelative(-1.0f, 0.0f);
            pathBuilder2.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder2._nodes, i, solidColor7, solidColor8, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
