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

public abstract class KimchiRefrigeratorKt {
    public static final Lazy KimchiRefrigerator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.device.KimchiRefrigeratorKt$KimchiRefrigerator$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 36.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("KimchiRefrigerator", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(23.728f, 5.936f);
            pathBuilder.curveTo(24.654f, 5.936f, 25.408f, 6.69f, 25.408f, 7.616f);
            pathBuilder.lineTo(25.408f, 7.616f);
            pathBuilder.lineTo(25.408f, 28.384f);
            pathBuilder.curveTo(25.408f, 29.311f, 24.654f, 30.064f, 23.728f, 30.064f);
            pathBuilder.lineTo(23.728f, 30.064f);
            pathBuilder.lineTo(12.272f, 30.064f);
            pathBuilder.curveTo(11.346f, 30.064f, 10.592f, 29.311f, 10.592f, 28.384f);
            pathBuilder.lineTo(10.592f, 28.384f);
            pathBuilder.lineTo(10.592f, 7.616f);
            pathBuilder.curveTo(10.592f, 6.69f, 11.346f, 5.936f, 12.272f, 5.936f);
            KeyboardCommandKeyKt$$ExternalSyntheticOutline0.m(pathBuilder, 12.272f, 5.936f, 23.726f, 24.694f);
            pathBuilder.lineTo(12.272f, 24.694f);
            pathBuilder.lineTo(12.272f, 28.384f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 23.726f, 28.384f, 23.726f, 24.694f);
            pathBuilder.moveTo(23.727f, 19.328f);
            pathBuilder.lineTo(12.272f, 19.328f);
            pathBuilder.lineTo(12.272f, 23.013f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 23.726f, 23.013f, 23.727f, 19.328f);
            pathBuilder.moveTo(23.728f, 7.616f);
            pathBuilder.lineTo(12.272f, 7.616f);
            pathBuilder.lineTo(12.272f, 17.649f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 23.727f, 17.649f, 23.728f, 7.616f);
            pathBuilder.moveTo(15.3953f, 9.6608f);
            pathBuilder.lineTo(15.3953f, 14.1418f);
            pathBuilder.lineTo(13.7153f, 14.1418f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 13.7153f, 9.6608f, 15.3953f, 9.6608f);
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
