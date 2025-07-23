package com.android.systemui.media.mediaoutput.icons.badge;

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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MusicShareKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 18.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("MusicShare", f, f, 18.0f, 18.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(4281348144L), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(4294638335L), null);
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
        builder.m565addPathoIyEayM("", pathBuilder._nodes, i3, solidColor, 1.0f, solidColor2, 1.0f, 2.0f, i, i2, 4.0f, 0.0f, 1.0f, 0.0f);
        SolidColor solidColor3 = new SolidColor(ColorKt.Color(4294967295L), null);
        SolidColor solidColor4 = new SolidColor(ColorKt.Color(0), null);
        PathBuilder m = ShortcutHelperKt$$ExternalSyntheticOutline0.m(7.043f, 9.957f);
        m.curveTo(6.79f, 10.109f, 6.529f, 10.195f, 6.275f, 10.195f);
        m.curveTo(5.529f, 10.195f, 4.917f, 9.583f, 4.917f, 8.837f);
        m.curveTo(4.917f, 8.092f, 5.529f, 7.48f, 6.275f, 7.48f);
        m.curveTo(6.557f, 7.48f, 6.827f, 7.566f, 7.05f, 7.714f);
        m.lineTo(9.211f, 6.46f);
        m.curveTo(9.209f, 6.449f, 9.208f, 6.437f, 9.206f, 6.426f);
        m.curveTo(9.202f, 6.379f, 9.201f, 6.342f, 9.201f, 6.275f);
        m.curveTo(9.201f, 5.529f, 9.813f, 4.917f, 10.559f, 4.917f);
        m.curveTo(11.304f, 4.917f, 11.917f, 5.529f, 11.917f, 6.275f);
        m.curveTo(11.917f, 7.02f, 11.304f, 7.633f, 10.559f, 7.633f);
        m.curveTo(10.276f, 7.633f, 10.006f, 7.546f, 9.783f, 7.398f);
        m.lineTo(7.623f, 8.652f);
        m.curveTo(7.624f, 8.663f, 7.626f, 8.675f, 7.627f, 8.686f);
        m.curveTo(7.632f, 8.733f, 7.633f, 8.77f, 7.633f, 8.837f);
        m.curveTo(7.633f, 8.905f, 7.632f, 8.942f, 7.627f, 8.988f);
        m.curveTo(7.626f, 9.0f, 7.624f, 9.011f, 7.623f, 9.023f);
        m.lineTo(9.79f, 10.281f);
        m.curveTo(10.043f, 10.129f, 10.305f, 10.042f, 10.559f, 10.042f);
        m.curveTo(11.304f, 10.042f, 11.917f, 10.655f, 11.917f, 11.4f);
        m.curveTo(11.917f, 12.146f, 11.304f, 12.758f, 10.559f, 12.758f);
        m.curveTo(9.813f, 12.758f, 9.201f, 12.146f, 9.201f, 11.4f);
        m.curveTo(9.201f, 11.333f, 9.202f, 11.296f, 9.206f, 11.249f);
        m.curveTo(9.208f, 11.238f, 9.209f, 11.226f, 9.211f, 11.215f);
        m.lineTo(7.043f, 9.957f);
        m.close();
        builder.m565addPathoIyEayM("", m._nodes, 0, solidColor3, 1.0f, solidColor4, 1.0f, 1.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
