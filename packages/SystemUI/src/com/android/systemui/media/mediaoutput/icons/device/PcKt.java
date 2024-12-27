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
public abstract class PcKt {
    public static final Lazy Pc$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.device.PcKt$Pc$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 36.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("Pc", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(27.66f, 25.84f);
            pathBuilder.lineTo(22.181f, 25.84f);
            pathBuilder.lineTo(22.181f, 24.387f);
            pathBuilder.lineTo(23.364f, 24.387f);
            pathBuilder.curveTo(24.291f, 24.387f, 25.044f, 23.633f, 25.044f, 22.706f);
            pathBuilder.lineTo(25.044f, 12.3f);
            pathBuilder.curveTo(25.044f, 11.373f, 24.291f, 10.619f, 23.364f, 10.619f);
            pathBuilder.lineTo(22.181f, 10.619f);
            pathBuilder.lineTo(22.181f, 10.16f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 27.66f, 10.16f, 27.66f, 25.84f);
            pathBuilder.moveTo(23.364f, 22.706f);
            pathBuilder.lineTo(23.365f, 22.706f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 23.364f, 23.546f, 23.364f, 22.706f);
            pathBuilder.moveTo(8.34f, 12.3f);
            pathBuilder.lineTo(23.364f, 12.3f);
            pathBuilder.lineTo(23.362f, 22.706f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 8.34f, 22.706f, 8.34f, 12.3f);
            pathBuilder.moveTo(27.66f, 8.48f);
            pathBuilder.lineTo(22.181f, 8.48f);
            pathBuilder.curveTo(21.254f, 8.48f, 20.501f, 9.234f, 20.501f, 10.16f);
            pathBuilder.lineTo(20.501f, 10.619f);
            pathBuilder.lineTo(8.34f, 10.619f);
            pathBuilder.curveTo(7.414f, 10.619f, 6.66f, 11.373f, 6.66f, 12.3f);
            pathBuilder.lineTo(6.66f, 22.706f);
            pathBuilder.curveTo(6.66f, 23.633f, 7.414f, 24.387f, 8.34f, 24.387f);
            pathBuilder.lineTo(15.013f, 24.387f);
            pathBuilder.lineTo(15.013f, 25.602f);
            pathBuilder.lineTo(12.392f, 25.602f);
            pathBuilder.lineTo(12.392f, 27.281f);
            pathBuilder.lineTo(19.313f, 27.281f);
            pathBuilder.lineTo(19.313f, 25.602f);
            pathBuilder.lineTo(16.692f, 25.602f);
            pathBuilder.lineTo(16.692f, 24.387f);
            pathBuilder.lineTo(20.501f, 24.387f);
            pathBuilder.lineTo(20.501f, 25.84f);
            pathBuilder.curveTo(20.501f, 26.766f, 21.254f, 27.52f, 22.181f, 27.52f);
            pathBuilder.lineTo(27.66f, 27.52f);
            pathBuilder.curveTo(28.586f, 27.52f, 29.34f, 26.766f, 29.34f, 25.84f);
            pathBuilder.lineTo(29.34f, 10.16f);
            pathBuilder.curveTo(29.34f, 9.234f, 28.586f, 8.48f, 27.66f, 8.48f);
            pathBuilder.lineTo(27.66f, 8.48f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
