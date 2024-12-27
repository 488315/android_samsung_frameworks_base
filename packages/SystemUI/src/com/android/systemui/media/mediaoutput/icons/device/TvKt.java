package com.android.systemui.media.mediaoutput.icons.device;

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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class TvKt {
    public static final Lazy Tv$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.device.TvKt$Tv$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 36.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("Tv", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), null);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(27.24f, 22.539f);
            pathBuilder.lineTo(27.24f, 21.699f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 27.24f, 21.699f, 27.24f, 22.539f);
            pathBuilder.moveTo(27.238f, 21.699f);
            pathBuilder.lineTo(8.76f, 21.699f);
            pathBuilder.lineTo(8.76f, 10.451f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 27.24f, 10.451f, 27.238f, 21.699f);
            pathBuilder.moveTo(27.24f, 8.77f);
            pathBuilder.lineTo(8.76f, 8.77f);
            pathBuilder.curveTo(7.834f, 8.77f, 7.08f, 9.524f, 7.08f, 10.451f);
            pathBuilder.lineTo(7.08f, 21.699f);
            pathBuilder.curveTo(7.08f, 22.626f, 7.834f, 23.379f, 8.76f, 23.379f);
            pathBuilder.lineTo(17.16f, 23.379f);
            pathBuilder.lineTo(17.16f, 24.134f);
            pathBuilder.lineTo(16.957f, 24.134f);
            pathBuilder.lineTo(10.448f, 25.591f);
            pathBuilder.lineTo(10.815f, 27.23f);
            pathBuilder.lineTo(17.143f, 25.814f);
            pathBuilder.lineTo(18.858f, 25.814f);
            pathBuilder.lineTo(25.185f, 27.23f);
            pathBuilder.lineTo(25.552f, 25.591f);
            pathBuilder.lineTo(19.043f, 24.134f);
            pathBuilder.lineTo(18.84f, 24.134f);
            pathBuilder.lineTo(18.84f, 23.379f);
            pathBuilder.lineTo(27.24f, 23.379f);
            pathBuilder.curveTo(28.166f, 23.379f, 28.92f, 22.626f, 28.92f, 21.699f);
            pathBuilder.lineTo(28.92f, 10.451f);
            pathBuilder.curveTo(28.92f, 9.524f, 28.166f, 8.77f, 27.24f, 8.77f);
            pathBuilder.lineTo(27.24f, 8.77f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });

    public static final ImageVector getTv() {
        return (ImageVector) Tv$delegate.getValue();
    }
}
