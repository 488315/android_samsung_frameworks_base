package com.android.systemui.media.mediaoutput.icons.device;

import androidx.compose.material.icons.filled.CloseKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.filled.KeyboardCommandKeyKt$$ExternalSyntheticOutline0;
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

public abstract class SoundBarKt {
    public static final Lazy SoundBar$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.device.SoundBarKt$SoundBar$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 36.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("SoundBar", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(9.8554f, 10.4471f);
            pathBuilder.lineTo(26.1444f, 10.4471f);
            pathBuilder.lineTo(26.1424f, 19.8191f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 9.8554f, 19.8191f, 9.8554f, 10.4471f);
            pathBuilder.moveTo(26.1444f, 19.8191f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 26.1444f, 20.6601f, 26.1444f, 19.8191f);
            pathBuilder.moveTo(9.8554f, 21.5001f);
            pathBuilder.lineTo(26.1444f, 21.5001f);
            pathBuilder.curveTo(27.0714f, 21.5001f, 27.8244f, 20.7461f, 27.8244f, 19.8191f);
            pathBuilder.lineTo(27.8244f, 10.4471f);
            pathBuilder.curveTo(27.8244f, 9.5201f, 27.0714f, 8.7661f, 26.1444f, 8.7661f);
            pathBuilder.lineTo(9.8554f, 8.7661f);
            pathBuilder.curveTo(8.9284f, 8.7661f, 8.1754f, 9.5201f, 8.1754f, 10.4471f);
            pathBuilder.lineTo(8.1754f, 19.8191f);
            pathBuilder.curveTo(8.1754f, 20.7461f, 8.9284f, 21.5001f, 9.8554f, 21.5001f);
            KeyboardCommandKeyKt$$ExternalSyntheticOutline0.m(pathBuilder, 9.8554f, 21.5001f, 27.38f, 25.4969f);
            pathBuilder.curveTo(24.393f, 25.2699f, 21.317f, 25.1599f, 18.0f, 25.1599f);
            pathBuilder.curveTo(14.685f, 25.1599f, 11.609f, 25.2699f, 8.62f, 25.4969f);
            pathBuilder.lineTo(8.62f, 24.1519f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 27.38f, 24.1519f, 27.38f, 25.4969f);
            pathBuilder.moveTo(27.94f, 22.4719f);
            pathBuilder.lineTo(8.06f, 22.4719f);
            pathBuilder.curveTo(7.443f, 22.4719f, 6.94f, 22.9749f, 6.94f, 23.5919f);
            pathBuilder.lineTo(6.94f, 26.2539f);
            pathBuilder.curveTo(6.94f, 26.8009f, 7.392f, 27.2289f, 7.968f, 27.2289f);
            pathBuilder.lineTo(8.168f, 27.2179f);
            pathBuilder.curveTo(11.298f, 26.9639f, 14.515f, 26.8399f, 18.0f, 26.8399f);
            pathBuilder.curveTo(21.521f, 26.8399f, 24.765f, 26.9669f, 27.922f, 27.2249f);
            pathBuilder.lineTo(28.075f, 27.2339f);
            pathBuilder.lineTo(28.075f, 27.2339f);
            pathBuilder.curveTo(28.375f, 27.2339f, 28.654f, 27.0959f, 28.841f, 26.8549f);
            pathBuilder.curveTo(28.982f, 26.6729f, 29.06f, 26.4419f, 29.06f, 26.2059f);
            pathBuilder.lineTo(29.06f, 23.5919f);
            pathBuilder.curveTo(29.06f, 22.9749f, 28.557f, 22.4719f, 27.94f, 22.4719f);
            pathBuilder.lineTo(27.94f, 22.4719f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
