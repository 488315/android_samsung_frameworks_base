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
public final /* synthetic */ class SkipNextKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 40.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("SkipNext", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(4294967295L), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathBuilder pathBuilderM = EqualizerPlayingKt$$ExternalSyntheticOutline0.m(PathFillType.Companion, 25.9712f, 11.6f);
        pathBuilderM.curveTo(25.2082f, 11.6f, 24.5924f, 12.1645f, 24.5924f, 12.86f);
        pathBuilderM.lineTo(24.5924f, 18.866f);
        pathBuilderM.curveTo(24.5666f, 18.8492f, 24.5519f, 18.8307f, 24.5262f, 18.8156f);
        pathBuilderM.lineTo(14.8635f, 12.277f);
        pathBuilderM.curveTo(14.5767f, 12.1074f, 14.2788f, 12.02f, 13.981f, 12.02f);
        pathBuilderM.curveTo(13.3173f, 12.02f, 12.65f, 12.487f, 12.65f, 13.3808f);
        pathBuilderM.lineTo(12.65f, 26.6192f);
        pathBuilderM.curveTo(12.65f, 27.513f, 13.3173f, 27.98f, 13.981f, 27.98f);
        pathBuilderM.curveTo(14.2788f, 27.98f, 14.5748f, 27.8943f, 14.8635f, 27.723f);
        pathBuilderM.lineTo(24.5262f, 21.1827f);
        pathBuilderM.curveTo(24.5519f, 21.1676f, 24.5666f, 21.1491f, 24.5924f, 21.134f);
        pathBuilderM.lineTo(24.5924f, 27.14f);
        pathBuilderM.curveTo(24.5924f, 27.8355f, 25.2082f, 28.4f, 25.9712f, 28.4f);
        pathBuilderM.curveTo(26.7323f, 28.4f, 27.35f, 27.8355f, 27.35f, 27.14f);
        pathBuilderM.lineTo(27.35f, 12.86f);
        pathBuilderM.curveTo(27.35f, 12.1645f, 26.7323f, 11.6f, 25.9712f, 11.6f);
        builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 0.25f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
