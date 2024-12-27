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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class FastRewindKt {
    public static final Lazy FastRewind$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.action.FastRewindKt$FastRewind$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 40.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("FastRewind", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(18.3344f, 11.8536f);
            pathBuilder.curveTo(18.0415f, 11.8536f, 17.7471f, 11.9458f, 17.4649f, 12.1301f);
            pathBuilder.lineTo(9.0444f, 18.7382f);
            pathBuilder.curveTo(8.57f, 19.0404f, 8.3f, 19.5013f, 8.3f, 20.0f);
            pathBuilder.curveTo(8.3f, 20.4972f, 8.57f, 20.955f, 9.0444f, 21.2603f);
            pathBuilder.lineTo(17.4649f, 27.8714f);
            pathBuilder.curveTo(17.7486f, 28.0542f, 18.0415f, 28.1449f, 18.3344f, 28.1449f);
            pathBuilder.curveTo(18.9858f, 28.1449f, 19.6402f, 27.6477f, 19.6402f, 26.6957f);
            pathBuilder.lineTo(19.6402f, 21.5111f);
            pathBuilder.lineTo(28.7914f, 28.225f);
            pathBuilder.curveTo(29.0766f, 28.4078f, 29.3665f, 28.5f, 29.6609f, 28.5f);
            pathBuilder.curveTo(30.3122f, 28.5f, 30.9667f, 28.0028f, 30.9667f, 27.0508f);
            pathBuilder.lineTo(30.9667f, 12.9476f);
            pathBuilder.curveTo(30.9667f, 11.9972f, 30.3122f, 11.5f, 29.6609f, 11.5f);
            pathBuilder.curveTo(29.3665f, 11.5f, 29.0751f, 11.5907f, 28.7914f, 11.775f);
            pathBuilder.lineTo(19.6402f, 18.4874f);
            pathBuilder.lineTo(19.6402f, 13.3012f);
            pathBuilder.curveTo(19.6402f, 12.3508f, 18.9858f, 11.8536f, 18.3344f, 11.8536f);
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
