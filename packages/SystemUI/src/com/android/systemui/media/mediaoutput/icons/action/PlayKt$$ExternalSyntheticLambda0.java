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
public final /* synthetic */ class PlayKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 40.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("Play", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathFillType.Companion.getClass();
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.moveTo(15.4128f, 30.6519f);
        pathBuilder.lineTo(28.5156f, 22.091f);
        pathBuilder.curveTo(30.1263f, 21.1617f, 30.1263f, 18.8383f, 28.5156f, 17.909f);
        pathBuilder.lineTo(15.4128f, 9.3481f);
        pathBuilder.curveTo(13.8035f, 8.4188f, 11.7904f, 9.5804f, 11.7904f, 11.4391f);
        pathBuilder.lineTo(11.7904f, 28.5595f);
        pathBuilder.curveTo(11.7904f, 30.4196f, 13.8035f, 31.5813f, 15.4128f, 30.6519f);
        builder.m567addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
