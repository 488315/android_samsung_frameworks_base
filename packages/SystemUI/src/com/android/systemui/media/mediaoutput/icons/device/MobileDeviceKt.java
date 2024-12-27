package com.android.systemui.media.mediaoutput.icons.device;

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

public abstract class MobileDeviceKt {
    public static final Lazy MobileDevice$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.device.MobileDeviceKt$MobileDevice$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 36.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("MobileDevice", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(22.76f, 7.35f);
            pathBuilder.curveTo(24.155f, 7.35f, 25.29f, 8.485f, 25.29f, 9.879f);
            pathBuilder.lineTo(25.29f, 26.119f);
            pathBuilder.curveTo(25.29f, 27.515f, 24.155f, 28.65f, 22.76f, 28.65f);
            pathBuilder.lineTo(13.24f, 28.65f);
            pathBuilder.curveTo(11.845f, 28.65f, 10.71f, 27.515f, 10.71f, 26.119f);
            pathBuilder.lineTo(10.71f, 9.879f);
            pathBuilder.curveTo(10.71f, 8.485f, 11.845f, 7.35f, 13.24f, 7.35f);
            KeyboardCommandKeyKt$$ExternalSyntheticOutline0.m(pathBuilder, 22.76f, 7.35f, 22.76f, 9.049f);
            pathBuilder.lineTo(13.24f, 9.049f);
            pathBuilder.curveTo(12.782f, 9.049f, 12.41f, 9.422f, 12.41f, 9.879f);
            pathBuilder.lineTo(12.41f, 26.119f);
            pathBuilder.curveTo(12.41f, 26.577f, 12.782f, 26.95f, 13.24f, 26.95f);
            pathBuilder.lineTo(22.76f, 26.95f);
            pathBuilder.curveTo(23.218f, 26.95f, 23.59f, 26.577f, 23.59f, 26.119f);
            pathBuilder.lineTo(23.59f, 9.879f);
            pathBuilder.curveTo(23.59f, 9.422f, 23.218f, 9.049f, 22.76f, 9.049f);
            pathBuilder.close();
            pathBuilder.moveTo(19.7892f, 23.1908f);
            pathBuilder.curveTo(20.2582f, 23.1908f, 20.6392f, 23.5708f, 20.6392f, 24.0398f);
            pathBuilder.curveTo(20.6392f, 24.5098f, 20.2582f, 24.8898f, 19.7892f, 24.8898f);
            pathBuilder.lineTo(16.2112f, 24.8898f);
            pathBuilder.curveTo(15.7422f, 24.8898f, 15.3612f, 24.5098f, 15.3612f, 24.0398f);
            pathBuilder.curveTo(15.3612f, 23.5708f, 15.7422f, 23.1908f, 16.2112f, 23.1908f);
            pathBuilder.lineTo(19.7892f, 23.1908f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
