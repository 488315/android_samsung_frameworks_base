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
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class FastForwardKt {
    public static final Lazy FastForward$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.action.FastForwardKt$FastForward$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 40.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("FastForward", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(9.03f, 27.0508f);
            pathBuilder.curveTo(9.03f, 28.0028f, 9.6859f, 28.5f, 10.3358f, 28.5f);
            pathBuilder.curveTo(10.6287f, 28.5f, 10.92f, 28.4078f, 11.2053f, 28.225f);
            pathBuilder.lineTo(20.3565f, 21.5111f);
            pathBuilder.lineTo(20.3565f, 26.6972f);
            pathBuilder.curveTo(20.3565f, 27.6492f, 21.0109f, 28.1464f, 21.6623f, 28.1464f);
            pathBuilder.curveTo(21.9567f, 28.1464f, 22.248f, 28.0542f, 22.5318f, 27.8714f);
            pathBuilder.lineTo(30.9522f, 21.2603f);
            pathBuilder.curveTo(31.4267f, 20.958f, 31.6967f, 20.4987f, 31.6967f, 20.0f);
            pathBuilder.curveTo(31.6967f, 19.5013f, 31.4267f, 19.042f, 30.9522f, 18.7382f);
            pathBuilder.lineTo(22.5318f, 12.1301f);
            pathBuilder.curveTo(22.248f, 11.9458f, 21.9567f, 11.8536f, 21.6623f, 11.8536f);
            pathBuilder.curveTo(21.0109f, 11.8536f, 20.3565f, 12.3508f, 20.3565f, 13.3012f);
            pathBuilder.lineTo(20.3565f, 18.4874f);
            pathBuilder.lineTo(11.2053f, 11.7765f);
            pathBuilder.curveTo(10.9216f, 11.5922f, 10.6287f, 11.5f, 10.3358f, 11.5f);
            pathBuilder.curveTo(9.6859f, 11.5f, 9.03f, 11.9972f, 9.03f, 12.9476f);
            pathBuilder.lineTo(9.03f, 27.0508f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
