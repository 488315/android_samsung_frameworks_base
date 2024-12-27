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
/* loaded from: classes2.dex */
public abstract class RefrigeratorKt {
    public static final Lazy Refrigerator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.device.RefrigeratorKt$Refrigerator$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 36.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("Refrigerator", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(24.72f, 6.7995f);
            pathBuilder.curveTo(25.646f, 6.7995f, 26.4f, 7.5535f, 26.4f, 8.4805f);
            pathBuilder.lineTo(26.4f, 8.4805f);
            pathBuilder.lineTo(26.4f, 27.5195f);
            pathBuilder.curveTo(26.4f, 28.4465f, 25.646f, 29.2005f, 24.72f, 29.2005f);
            pathBuilder.lineTo(24.72f, 29.2005f);
            pathBuilder.lineTo(11.28f, 29.2005f);
            pathBuilder.curveTo(10.354f, 29.2005f, 9.6f, 28.4465f, 9.6f, 27.5195f);
            pathBuilder.lineTo(9.6f, 27.5195f);
            pathBuilder.lineTo(9.6f, 8.4805f);
            pathBuilder.curveTo(9.6f, 7.5535f, 10.354f, 6.7995f, 11.28f, 6.7995f);
            KeyboardCommandKeyKt$$ExternalSyntheticOutline0.m(pathBuilder, 11.28f, 6.7995f, 17.16f, 21.7915f);
            pathBuilder.lineTo(11.28f, 21.7915f);
            pathBuilder.lineTo(11.28f, 27.5205f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 17.16f, 27.5205f, 17.16f, 21.7915f);
            pathBuilder.moveTo(24.718f, 21.7915f);
            pathBuilder.lineTo(18.84f, 21.7915f);
            pathBuilder.lineTo(18.84f, 27.5195f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 24.718f, 27.5195f, 24.718f, 21.7915f);
            pathBuilder.moveTo(17.16f, 8.4805f);
            pathBuilder.lineTo(11.28f, 8.4805f);
            pathBuilder.lineTo(11.28f, 20.1115f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 17.16f, 20.1115f, 17.16f, 8.4805f);
            pathBuilder.moveTo(24.72f, 8.4805f);
            pathBuilder.lineTo(18.84f, 8.4805f);
            pathBuilder.lineTo(18.84f, 20.1115f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 24.719f, 20.1115f, 24.72f, 8.4805f);
            pathBuilder.moveTo(23.707f, 10.7043f);
            pathBuilder.lineTo(23.707f, 17.9843f);
            pathBuilder.lineTo(19.787f, 17.9843f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 19.787f, 10.7043f, 23.707f, 10.7043f);
            pathBuilder.moveTo(22.307f, 12.1033f);
            pathBuilder.lineTo(21.187f, 12.1033f);
            pathBuilder.lineTo(21.187f, 16.5833f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 22.307f, 16.5833f, 22.307f, 12.1033f);
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
