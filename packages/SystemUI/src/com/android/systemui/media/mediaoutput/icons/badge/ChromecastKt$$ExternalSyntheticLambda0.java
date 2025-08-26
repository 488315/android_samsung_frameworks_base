package com.android.systemui.media.mediaoutput.icons.badge;

import androidx.compose.material.icons.filled.ExpandMoreKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class ChromecastKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 18.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("Chromecast", f, f, 18.0f, 18.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4281348144L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(4294967295L), null);
        StrokeCap.Companion.getClass();
        int i = StrokeCap.Round;
        StrokeJoin.Companion.getClass();
        int i2 = StrokeJoin.Round;
        PathFillType.Companion.getClass();
        int i3 = PathFillType.EvenOdd;
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.moveTo(9.0f, 9.0f);
        pathBuilder.moveToRelative(-8.0f, 0.0f);
        pathBuilder.arcToRelative(8.0f, 8.0f, 16.0f, true);
        pathBuilder.arcToRelative(8.0f, 8.0f, -16.0f, true);
        builder.m567addPathoIyEayM("", pathBuilder._nodes, i3, solidColor, 1.0f, solidColor2, 1.0f, 2.0f, i, i2, 4.0f, 0.0f, 1.0f, 0.0f);
        SolidColor solidColor3 = new SolidColor(ColorKt.Color(0), null);
        SolidColor solidColor4 = new SolidColor(ColorKt.Color(4294638335L), null);
        PathBuilder pathBuilderM = ExpandMoreKt$$ExternalSyntheticOutline0.m(4.75f, 7.391f, 4.75f, 6.75f);
        pathBuilderM.curveTo(4.75f, 6.059f, 5.309f, 5.5f, 6.0f, 5.5f);
        pathBuilderM.lineTo(12.0f, 5.5f);
        pathBuilderM.curveTo(12.69f, 5.5f, 13.25f, 6.059f, 13.25f, 6.75f);
        pathBuilderM.lineTo(13.25f, 11.25f);
        pathBuilderM.curveTo(13.25f, 11.941f, 12.69f, 12.5f, 12.0f, 12.5f);
        pathBuilderM.lineTo(9.857f, 12.5f);
        builder.m567addPathoIyEayM("", pathBuilderM._nodes, i3, solidColor3, 1.0f, solidColor4, 1.0f, 0.75f, i, i2, 4.0f, 0.0f, 1.0f, 0.0f);
        SolidColor solidColor5 = new SolidColor(ColorKt.Color(0), null);
        SolidColor solidColor6 = new SolidColor(ColorKt.Color(4294638335L), null);
        PathBuilder pathBuilder2 = new PathBuilder();
        pathBuilder2.moveTo(4.75f, 10.403f);
        pathBuilder2.curveTo(5.83f, 10.565f, 6.684f, 11.419f, 6.846f, 12.5f);
        builder.m567addPathoIyEayM("", pathBuilder2._nodes, i3, solidColor5, 1.0f, solidColor6, 1.0f, 0.75f, i, i2, 4.0f, 0.0f, 1.0f, 0.0f);
        SolidColor solidColor7 = new SolidColor(ColorKt.Color(0), null);
        SolidColor solidColor8 = new SolidColor(ColorKt.Color(4294638335L), null);
        PathBuilder pathBuilderM2 = ShortcutHelperKt$$ExternalSyntheticOutline0.m(4.75f, 8.892f);
        pathBuilderM2.curveTo(6.656f, 9.069f, 8.174f, 10.584f, 8.356f, 12.489f);
        builder.m567addPathoIyEayM("", pathBuilderM2._nodes, i3, solidColor7, 1.0f, solidColor8, 1.0f, 0.75f, i, i2, 4.0f, 0.0f, 1.0f, 0.0f);
        SolidColor solidColor9 = new SolidColor(ColorKt.Color(4294638335L), null);
        SolidColor solidColor10 = new SolidColor(ColorKt.Color(0), null);
        PathBuilder pathBuilderM3 = ShortcutHelperKt$$ExternalSyntheticOutline0.m(5.5f, 12.312f);
        pathBuilderM3.curveTo(5.5f, 12.623f, 5.248f, 12.875f, 4.938f, 12.875f);
        pathBuilderM3.curveTo(4.627f, 12.875f, 4.375f, 12.623f, 4.375f, 12.312f);
        pathBuilderM3.curveTo(4.375f, 12.002f, 4.627f, 11.75f, 4.938f, 11.75f);
        pathBuilderM3.curveTo(5.248f, 11.75f, 5.5f, 12.002f, 5.5f, 12.312f);
        builder.m567addPathoIyEayM("", pathBuilderM3._nodes, 0, solidColor9, 1.0f, solidColor10, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
