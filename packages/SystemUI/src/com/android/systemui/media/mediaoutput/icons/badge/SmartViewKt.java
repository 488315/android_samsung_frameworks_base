package com.android.systemui.media.mediaoutput.icons.badge;

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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class SmartViewKt {
    public static final Lazy SmartView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.badge.SmartViewKt$SmartView$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 18.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("IcSmartView", f, f, 18.0f, 18.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
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
            SolidColor solidColor3 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            SolidColor solidColor4 = new SolidColor(ColorKt.Color(4294967295L), defaultConstructorMarker);
            PathBuilder pathBuilder2 = new PathBuilder();
            pathBuilder2.moveTo(6.0304f, 11.0412f);
            pathBuilder2.curveTo(6.4383f, 10.9915f, 6.8089f, 11.2824f, 6.8581f, 11.6903f);
            pathBuilder2.curveTo(6.8844f, 11.9047f, 6.8166f, 12.1091f, 6.6867f, 12.2615f);
            pathBuilder2.curveTo(6.5696f, 12.399f, 6.403f, 12.4945f, 6.2095f, 12.5179f);
            pathBuilder2.curveTo(5.8017f, 12.5676f, 5.4311f, 12.2772f, 5.3819f, 11.8689f);
            pathBuilder2.curveTo(5.3322f, 11.461f, 5.6226f, 11.0909f, 6.0304f, 11.0412f);
            pathBuilder2.close();
            pathBuilder2.moveTo(11.7928f, 5.4894f);
            pathBuilder2.curveTo(12.2006f, 5.4397f, 12.5712f, 5.7306f, 12.6204f, 6.1385f);
            pathBuilder2.curveTo(12.6701f, 6.5463f, 12.3797f, 6.9169f, 11.9714f, 6.9661f);
            pathBuilder2.curveTo(11.7477f, 6.9934f, 11.535f, 6.9181f, 11.3807f, 6.7771f);
            pathBuilder2.curveTo(11.2539f, 6.6613f, 11.1664f, 6.5012f, 11.1442f, 6.3171f);
            pathBuilder2.curveTo(11.094f, 5.9097f, 11.3849f, 5.5391f, 11.7928f, 5.4894f);
            pathBuilder2.close();
            pathBuilder2.moveTo(8.041f, 7.7338f);
            pathBuilder2.curveTo(8.041f, 7.4615f, 8.3357f, 7.2915f, 8.5717f, 7.4271f);
            pathBuilder2.lineTo(10.4911f, 8.6813f);
            pathBuilder2.curveTo(10.7265f, 8.8179f, 10.7265f, 9.1579f, 10.4911f, 9.294f);
            pathBuilder2.lineTo(8.5717f, 10.5482f);
            pathBuilder2.curveTo(8.3357f, 10.6843f, 8.041f, 10.5138f, 8.041f, 10.2416f);
            KeyboardCommandKeyKt$$ExternalSyntheticOutline0.m(pathBuilder2, 8.041f, 7.7338f, 11.2932f, 5.7238f);
            pathBuilder2.curveTo(10.2645f, 5.005f, 8.9139f, 4.7801f, 7.643f, 5.2381f);
            pathBuilder2.curveTo(6.0097f, 5.8274f, 4.9948f, 7.3701f, 5.0f, 9.012f);
            pathBuilder2.moveTo(6.7048f, 12.2751f);
            pathBuilder2.curveTo(7.7341f, 12.9948f, 9.0852f, 13.2202f, 10.3565f, 12.7617f);
            pathBuilder2.curveTo(11.9899f, 12.1724f, 13.0047f, 10.6302f, 13.0f, 8.9878f);
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder2._nodes, i3, solidColor3, solidColor4, 1.0f, i, i2, 4.0f);
            return builder.build();
        }
    });
}
