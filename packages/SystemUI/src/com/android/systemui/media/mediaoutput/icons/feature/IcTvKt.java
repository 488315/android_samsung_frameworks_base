package com.android.systemui.media.mediaoutput.icons.feature;

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

public abstract class IcTvKt {
    public static final Lazy IcTv$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.feature.IcTvKt$IcTv$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 24.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("IcTv", f, f, 24.0f, 24.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4280773629L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            int i = PathFillType.EvenOdd;
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(12.0f, 0.0f);
            pathBuilder.curveTo(15.6883f, 0.0f, 18.9877f, 0.45f, 21.1887f, 2.6667f);
            pathBuilder.lineTo(21.2691f, 2.749f);
            pathBuilder.curveTo(22.9753f, 4.5226f, 24.0f, 7.3943f, 24.0f, 12.0f);
            pathBuilder.lineTo(23.9993f, 12.2146f);
            pathBuilder.curveTo(23.9673f, 16.7663f, 22.916f, 19.5939f, 21.1883f, 21.3337f);
            pathBuilder.lineTo(21.0774f, 21.443f);
            pathBuilder.curveTo(18.8772f, 23.5646f, 15.6265f, 24.0f, 12.0f, 24.0f);
            pathBuilder.lineTo(11.8159f, 23.9996f);
            pathBuilder.curveTo(8.202f, 23.9845f, 4.976f, 23.5127f, 2.8117f, 21.3337f);
            pathBuilder.lineTo(2.7312f, 21.2513f);
            pathBuilder.curveTo(1.0247f, 19.4774f, 0.0f, 16.6057f, 0.0f, 12.0f);
            pathBuilder.lineTo(7.0E-4f, 11.7854f);
            pathBuilder.curveTo(0.0327f, 7.2337f, 1.084f, 4.4062f, 2.8113f, 2.6667f);
            pathBuilder.lineTo(2.9223f, 2.5573f);
            pathBuilder.curveTo(5.1224f, 0.4351f, 8.3731f, 0.0f, 12.0f, 0.0f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, i, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            SolidColor solidColor3 = new SolidColor(ColorKt.Color(4294638335L), defaultConstructorMarker);
            SolidColor solidColor4 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            PathBuilder m = CloseKt$$ExternalSyntheticOutline0.m(18.16f, 15.026f, 18.16f, 14.466f);
            CloseKt$$ExternalSyntheticOutline0.m(m, 18.16f, 14.466f, 18.16f, 15.026f);
            m.moveTo(18.1587f, 14.466f);
            m.lineTo(5.84f, 14.466f);
            m.lineTo(5.84f, 6.9673f);
            CloseKt$$ExternalSyntheticOutline0.m(m, 18.16f, 6.9673f, 18.1587f, 14.466f);
            m.moveTo(18.16f, 5.8467f);
            m.lineTo(5.84f, 5.8467f);
            m.curveTo(5.2227f, 5.8467f, 4.72f, 6.3493f, 4.72f, 6.9673f);
            m.lineTo(4.72f, 14.466f);
            m.curveTo(4.72f, 15.084f, 5.2227f, 15.586f, 5.84f, 15.586f);
            m.lineTo(11.44f, 15.586f);
            m.lineTo(11.44f, 16.0893f);
            m.lineTo(11.3047f, 16.0893f);
            m.lineTo(6.9653f, 17.0607f);
            m.lineTo(7.21f, 18.1533f);
            m.lineTo(11.4287f, 17.2093f);
            m.lineTo(12.572f, 17.2093f);
            m.lineTo(16.79f, 18.1533f);
            m.lineTo(17.0347f, 17.0607f);
            m.lineTo(12.6953f, 16.0893f);
            m.lineTo(12.56f, 16.0893f);
            m.lineTo(12.56f, 15.586f);
            m.lineTo(18.16f, 15.586f);
            m.curveTo(18.7773f, 15.586f, 19.28f, 15.084f, 19.28f, 14.466f);
            m.lineTo(19.28f, 6.9673f);
            m.curveTo(19.28f, 6.3493f, 18.7773f, 5.8467f, 18.16f, 5.8467f);
            m.lineTo(18.16f, 5.8467f);
            m.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, m._nodes, 0, solidColor3, solidColor4, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
