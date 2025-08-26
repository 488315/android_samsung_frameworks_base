package com.android.systemui.media.mediaoutput.icons.action;

import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.media.mediaoutput.icons.EqualizerPlayingKt$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class SkipPreviousKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 40.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("SkipPrevious", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(4294967295L), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathBuilder pathBuilderM = EqualizerPlayingKt$$ExternalSyntheticOutline0.m(PathFillType.Companion, 26.007f, 12.0381f);
        pathBuilderM.curveTo(25.7098f, 12.0381f, 25.4126f, 12.1253f, 25.1246f, 12.2946f);
        pathBuilderM.lineTo(15.4844f, 18.8183f);
        pathBuilderM.curveTo(15.4587f, 18.8334f, 15.4404f, 18.8518f, 15.4184f, 18.8686f);
        pathBuilderM.lineTo(15.4184f, 12.8762f);
        pathBuilderM.curveTo(15.4184f, 12.1822f, 14.802f, 11.619f, 14.0425f, 11.619f);
        pathBuilderM.curveTo(13.2812f, 11.619f, 12.6667f, 12.1822f, 12.6667f, 12.8762f);
        pathBuilderM.lineTo(12.6667f, 27.1238f);
        pathBuilderM.curveTo(12.6667f, 27.8178f, 13.2812f, 28.381f, 14.0425f, 28.381f);
        pathBuilderM.curveTo(14.802f, 28.381f, 15.4184f, 27.8178f, 15.4184f, 27.1238f);
        pathBuilderM.lineTo(15.4184f, 21.1298f);
        pathBuilderM.curveTo(15.4404f, 21.1465f, 15.4587f, 21.165f, 15.4844f, 21.18f);
        pathBuilderM.lineTo(25.1246f, 27.7054f);
        pathBuilderM.curveTo(25.4145f, 27.8747f, 25.7098f, 27.9619f, 26.007f, 27.9619f);
        pathBuilderM.curveTo(26.6674f, 27.9619f, 27.3333f, 27.4959f, 27.3333f, 26.6042f);
        pathBuilderM.lineTo(27.3333f, 13.3958f);
        pathBuilderM.curveTo(27.3333f, 12.5041f, 26.6674f, 12.0381f, 26.007f, 12.0381f);
        builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 0.2f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
