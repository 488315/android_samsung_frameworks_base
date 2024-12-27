package com.android.systemui.media.mediaoutput.icons.badge;

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
public abstract class MusicShareKt {
    public static final Lazy MusicShare$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.badge.MusicShareKt$MusicShare$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 18.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("IcBadgeMusicShare", f, f, 18.0f, 18.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4281348144L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(4294967295L), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            int i = StrokeCap.Round;
            StrokeJoin.Companion.getClass();
            int i2 = StrokeJoin.Round;
            PathFillType.Companion.getClass();
            int i3 = PathFillType.EvenOdd;
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(9.0f, 9.0f);
            pathBuilder.moveToRelative(-8.0f, 0.0f);
            pathBuilder.arcToRelative(8.0f, 8.0f, 16.0f, true);
            pathBuilder.arcToRelative(8.0f, 8.0f, -16.0f, true);
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, i3, solidColor, solidColor2, 2.0f, i, i2, 4.0f);
            SolidColor solidColor3 = new SolidColor(ColorKt.Color(4294967295L), defaultConstructorMarker);
            SolidColor solidColor4 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            PathBuilder pathBuilder2 = new PathBuilder();
            pathBuilder2.moveTo(7.0434f, 9.9567f);
            pathBuilder2.curveTo(6.79f, 10.1088f, 6.5288f, 10.1954f, 6.2746f, 10.1954f);
            pathBuilder2.curveTo(5.5293f, 10.1954f, 4.9167f, 9.5828f, 4.9167f, 8.8374f);
            pathBuilder2.curveTo(4.9167f, 8.0921f, 5.5293f, 7.4795f, 6.2746f, 7.4795f);
            pathBuilder2.curveTo(6.5574f, 7.4795f, 6.8274f, 7.5659f, 7.0502f, 7.7142f);
            pathBuilder2.lineTo(9.2106f, 6.4598f);
            pathBuilder2.curveTo(9.209f, 6.4486f, 9.2076f, 6.4372f, 9.2065f, 6.4256f);
            pathBuilder2.curveTo(9.2018f, 6.3789f, 9.2008f, 6.3423f, 9.2008f, 6.2746f);
            pathBuilder2.curveTo(9.2008f, 5.5293f, 9.8134f, 4.9167f, 10.5587f, 4.9167f);
            pathBuilder2.curveTo(11.3041f, 4.9167f, 11.9167f, 5.5293f, 11.9167f, 6.2746f);
            pathBuilder2.curveTo(11.9167f, 7.0199f, 11.3041f, 7.6325f, 10.5587f, 7.6325f);
            pathBuilder2.curveTo(10.276f, 7.6325f, 10.006f, 7.5462f, 9.7831f, 7.3978f);
            pathBuilder2.lineTo(7.6228f, 8.6522f);
            pathBuilder2.curveTo(7.6244f, 8.6634f, 7.6257f, 8.6748f, 7.6269f, 8.6864f);
            pathBuilder2.curveTo(7.6316f, 8.7332f, 7.6325f, 8.7697f, 7.6325f, 8.8374f);
            pathBuilder2.curveTo(7.6325f, 8.9052f, 7.6316f, 8.9417f, 7.6269f, 8.9884f);
            pathBuilder2.curveTo(7.6257f, 9.0001f, 7.6244f, 9.0115f, 7.6228f, 9.0226f);
            pathBuilder2.lineTo(9.7899f, 10.281f);
            pathBuilder2.curveTo(10.0434f, 10.1289f, 10.3046f, 10.0423f, 10.5587f, 10.0423f);
            pathBuilder2.curveTo(11.3041f, 10.0423f, 11.9167f, 10.6549f, 11.9167f, 11.4003f);
            pathBuilder2.curveTo(11.9167f, 12.1456f, 11.3041f, 12.7582f, 10.5587f, 12.7582f);
            pathBuilder2.curveTo(9.8134f, 12.7582f, 9.2008f, 12.1456f, 9.2008f, 11.4003f);
            pathBuilder2.curveTo(9.2008f, 11.3325f, 9.2018f, 11.296f, 9.2065f, 11.2493f);
            pathBuilder2.curveTo(9.2076f, 11.2376f, 9.209f, 11.2262f, 9.2106f, 11.2151f);
            pathBuilder2.lineTo(7.0434f, 9.9567f);
            pathBuilder2.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder2._nodes, 0, solidColor3, solidColor4, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
