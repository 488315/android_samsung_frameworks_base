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
public abstract class PauseKt {
    public static final Lazy Pause$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.action.PauseKt$Pause$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 40.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("Pause", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(14.3679f, 9.4331f);
            pathBuilder.lineTo(14.3679f, 9.4331f);
            pathBuilder.curveTo(12.9838f, 9.4331f, 11.8547f, 10.5636f, 11.8547f, 11.9448f);
            pathBuilder.lineTo(11.8547f, 28.0537f);
            pathBuilder.curveTo(11.8547f, 29.4364f, 12.9838f, 30.5669f, 14.3679f, 30.5669f);
            pathBuilder.curveTo(15.7506f, 30.5669f, 16.8797f, 29.4364f, 16.8797f, 28.0537f);
            pathBuilder.lineTo(16.8797f, 11.9448f);
            pathBuilder.curveTo(16.8797f, 10.5636f, 15.7506f, 9.4331f, 14.3679f, 9.4331f);
            pathBuilder.moveTo(25.6321f, 9.4331f);
            pathBuilder.lineTo(25.6321f, 9.4331f);
            pathBuilder.curveTo(24.2494f, 9.4331f, 23.1203f, 10.5636f, 23.1203f, 11.9448f);
            pathBuilder.lineTo(23.1203f, 28.0537f);
            pathBuilder.curveTo(23.1203f, 29.4364f, 24.2494f, 30.5669f, 25.6321f, 30.5669f);
            pathBuilder.curveTo(27.0148f, 30.5669f, 28.1453f, 29.4364f, 28.1453f, 28.0537f);
            pathBuilder.lineTo(28.1453f, 11.9448f);
            pathBuilder.curveTo(28.1453f, 10.5636f, 27.0148f, 9.4331f, 25.6321f, 9.4331f);
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
