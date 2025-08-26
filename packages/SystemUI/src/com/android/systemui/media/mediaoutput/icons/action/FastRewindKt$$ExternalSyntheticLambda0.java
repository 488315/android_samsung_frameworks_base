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
public final /* synthetic */ class FastRewindKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 40.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("FastRewind", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathBuilder pathBuilderM = EqualizerPlayingKt$$ExternalSyntheticOutline0.m(PathFillType.Companion, 18.3344f, 11.8536f);
        pathBuilderM.curveTo(18.0415f, 11.8536f, 17.7471f, 11.9458f, 17.4649f, 12.1301f);
        pathBuilderM.lineTo(9.0444f, 18.7382f);
        pathBuilderM.curveTo(8.57f, 19.0404f, 8.3f, 19.5013f, 8.3f, 20.0f);
        pathBuilderM.curveTo(8.3f, 20.4972f, 8.57f, 20.955f, 9.0444f, 21.2603f);
        pathBuilderM.lineTo(17.4649f, 27.8714f);
        pathBuilderM.curveTo(17.7486f, 28.0542f, 18.0415f, 28.1449f, 18.3344f, 28.1449f);
        pathBuilderM.curveTo(18.9858f, 28.1449f, 19.6402f, 27.6477f, 19.6402f, 26.6957f);
        pathBuilderM.lineTo(19.6402f, 21.5111f);
        pathBuilderM.lineTo(28.7914f, 28.225f);
        pathBuilderM.curveTo(29.0766f, 28.4078f, 29.3665f, 28.5f, 29.6609f, 28.5f);
        pathBuilderM.curveTo(30.3122f, 28.5f, 30.9667f, 28.0028f, 30.9667f, 27.0508f);
        pathBuilderM.lineTo(30.9667f, 12.9476f);
        pathBuilderM.curveTo(30.9667f, 11.9972f, 30.3122f, 11.5f, 29.6609f, 11.5f);
        pathBuilderM.curveTo(29.3665f, 11.5f, 29.0751f, 11.5907f, 28.7914f, 11.775f);
        pathBuilderM.lineTo(19.6402f, 18.4874f);
        pathBuilderM.lineTo(19.6402f, 13.3012f);
        pathBuilderM.curveTo(19.6402f, 12.3508f, 18.9858f, 11.8536f, 18.3344f, 11.8536f);
        builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
