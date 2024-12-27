package com.android.systemui.media.mediaoutput.icons.device;

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
public abstract class TrueWirelessEarbudsKt {
    public static final Lazy TrueWirelessEarbuds$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.device.TrueWirelessEarbudsKt$TrueWirelessEarbuds$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 36.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("TrueWirelessEarbuds", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(18.0f, 7.0795f);
            pathBuilder.curveTo(23.041f, 7.0795f, 28.144f, 10.2895f, 28.144f, 16.4255f);
            pathBuilder.curveTo(28.144f, 18.6275f, 27.006f, 21.9525f, 25.437f, 24.3335f);
            pathBuilder.curveTo(23.94f, 26.6015f, 21.887f, 28.9205f, 18.001f, 28.9205f);
            pathBuilder.lineTo(18.001f, 28.9205f);
            pathBuilder.curveTo(14.113f, 28.9205f, 12.06f, 26.6015f, 10.564f, 24.3335f);
            pathBuilder.curveTo(8.994f, 21.9525f, 7.856f, 18.6265f, 7.856f, 16.4255f);
            pathBuilder.curveTo(7.856f, 10.2895f, 12.959f, 7.0795f, 18.0f, 7.0795f);
            pathBuilder.close();
            pathBuilder.moveTo(18.0f, 8.7605f);
            pathBuilder.curveTo(13.923f, 8.7605f, 9.535f, 11.1585f, 9.535f, 16.4255f);
            pathBuilder.curveTo(9.535f, 18.3035f, 10.58f, 21.3055f, 11.966f, 23.4085f);
            pathBuilder.curveTo(13.279f, 25.3985f, 14.919f, 27.2395f, 17.999f, 27.2395f);
            pathBuilder.lineTo(17.999f, 27.2395f);
            pathBuilder.curveTo(21.082f, 27.2395f, 22.721f, 25.3985f, 24.034f, 23.4085f);
            pathBuilder.curveTo(25.419f, 21.3055f, 26.464f, 18.3045f, 26.464f, 16.4255f);
            pathBuilder.curveTo(26.464f, 11.1585f, 22.077f, 8.7605f, 18.0f, 8.7605f);
            pathBuilder.close();
            pathBuilder.moveTo(18.0003f, 10.4611f);
            pathBuilder.curveTo(21.1703f, 10.4611f, 24.6383f, 12.1021f, 24.9533f, 14.8461f);
            pathBuilder.curveTo(25.2293f, 17.2501f, 24.2823f, 19.9341f, 22.4183f, 22.0241f);
            pathBuilder.curveTo(21.0473f, 23.5631f, 19.3953f, 24.4831f, 18.0003f, 24.4831f);
            pathBuilder.lineTo(18.0003f, 24.4831f);
            pathBuilder.curveTo(16.6043f, 24.4831f, 14.9533f, 23.5631f, 13.5813f, 22.0241f);
            pathBuilder.curveTo(11.7173f, 19.9331f, 10.7703f, 17.2501f, 11.0463f, 14.8461f);
            pathBuilder.curveTo(11.3613f, 12.1021f, 14.8293f, 10.4611f, 18.0003f, 10.4611f);
            pathBuilder.close();
            pathBuilder.moveTo(18.0003f, 11.8621f);
            pathBuilder.curveTo(15.4883f, 11.8621f, 12.6533f, 13.1241f, 12.4373f, 15.0051f);
            pathBuilder.curveTo(12.2063f, 17.0201f, 13.0243f, 19.2951f, 14.6263f, 21.0921f);
            pathBuilder.curveTo(15.7203f, 22.3201f, 17.0123f, 23.0821f, 18.0003f, 23.0831f);
            pathBuilder.curveTo(18.9873f, 23.0821f, 20.2793f, 22.3201f, 21.3733f, 21.0921f);
            pathBuilder.curveTo(22.9753f, 19.2951f, 23.7943f, 17.0201f, 23.5623f, 15.0051f);
            pathBuilder.curveTo(23.3463f, 13.1241f, 20.5123f, 11.8621f, 18.0003f, 11.8621f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
