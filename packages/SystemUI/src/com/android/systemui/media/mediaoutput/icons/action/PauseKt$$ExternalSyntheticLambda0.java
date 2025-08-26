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
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class PauseKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 40.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("Pause", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
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
        builder.m567addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
