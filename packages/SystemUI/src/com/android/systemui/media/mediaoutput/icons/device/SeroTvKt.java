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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class SeroTvKt {
    public static final Lazy SeroTv$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.device.SeroTvKt$SeroTv$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 36.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("SeroTv", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(22.7179f, 7.5f);
            pathBuilder.curveTo(23.3095f, 7.5f, 23.7908f, 8.0076f, 23.7908f, 8.6315f);
            pathBuilder.lineTo(23.7908f, 28.5f);
            pathBuilder.lineTo(22.0503f, 28.5f);
            pathBuilder.lineTo(22.0503f, 26.1729f);
            pathBuilder.curveTo(22.0456f, 26.1623f, 22.0411f, 26.1519f, 22.0366f, 26.1413f);
            pathBuilder.lineTo(13.9628f, 26.1413f);
            pathBuilder.lineTo(13.9497f, 26.1721f);
            pathBuilder.lineTo(13.9497f, 28.5f);
            pathBuilder.lineTo(12.2092f, 28.5f);
            pathBuilder.lineTo(12.2092f, 8.6315f);
            pathBuilder.curveTo(12.2092f, 8.0076f, 12.6905f, 7.5f, 13.2821f, 7.5f);
            KeyboardCommandKeyKt$$ExternalSyntheticOutline0.m(pathBuilder, 22.7179f, 7.5f, 22.0495f, 22.284f);
            pathBuilder.lineTo(13.9497f, 22.284f);
            pathBuilder.lineTo(13.9497f, 24.3081f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 22.0495f, 24.3081f, 22.0495f, 22.284f);
            pathBuilder.moveTo(22.0495f, 9.3337f);
            pathBuilder.lineTo(13.9497f, 9.3337f);
            pathBuilder.lineTo(13.9497f, 20.45f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 22.0495f, 20.45f, 22.0495f, 9.3337f);
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
