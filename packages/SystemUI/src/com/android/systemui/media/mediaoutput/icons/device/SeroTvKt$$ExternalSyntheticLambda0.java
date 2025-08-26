package com.android.systemui.media.mediaoutput.icons.device;

import androidx.compose.material.icons.outlined.WidgetsKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0;
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
public final /* synthetic */ class SeroTvKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 36.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("SeroTv", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathBuilder pathBuilderM = EqualizerPlayingKt$$ExternalSyntheticOutline0.m(PathFillType.Companion, 22.7179f, 7.5f);
        pathBuilderM.curveTo(23.3095f, 7.5f, 23.7908f, 8.0076f, 23.7908f, 8.6315f);
        pathBuilderM.lineTo(23.7908f, 28.5f);
        pathBuilderM.lineTo(22.0503f, 28.5f);
        pathBuilderM.lineTo(22.0503f, 26.1729f);
        pathBuilderM.curveTo(22.0456f, 26.1623f, 22.0411f, 26.1519f, 22.0366f, 26.1413f);
        pathBuilderM.lineTo(13.9628f, 26.1413f);
        pathBuilderM.lineTo(13.9497f, 26.1721f);
        pathBuilderM.lineTo(13.9497f, 28.5f);
        pathBuilderM.lineTo(12.2092f, 28.5f);
        pathBuilderM.lineTo(12.2092f, 8.6315f);
        pathBuilderM.curveTo(12.2092f, 8.0076f, 12.6905f, 7.5f, 13.2821f, 7.5f);
        WidgetsKt$$ExternalSyntheticOutline0.m(pathBuilderM, 22.7179f, 7.5f, 22.0495f, 22.284f);
        pathBuilderM.lineTo(13.9497f, 22.284f);
        pathBuilderM.lineTo(13.9497f, 24.3081f);
        ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(pathBuilderM, 22.0495f, 24.3081f, 22.0495f, 22.284f);
        pathBuilderM.moveTo(22.0495f, 9.3337f);
        pathBuilderM.lineTo(13.9497f, 9.3337f);
        pathBuilderM.lineTo(13.9497f, 20.45f);
        ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(pathBuilderM, 22.0495f, 20.45f, 22.0495f, 9.3337f);
        builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
