package com.android.systemui.media.mediaoutput.icons.badge;

import androidx.compose.material.icons.outlined.WidgetsKt$$ExternalSyntheticOutline0;
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
public final /* synthetic */ class SmartViewKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 18.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("SmartView", f, f, 18.0f, 18.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
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
        SolidColor solidColor3 = new SolidColor(ColorKt.Color(0), null);
        SolidColor solidColor4 = new SolidColor(ColorKt.Color(4294967295L), null);
        PathBuilder m = ShortcutHelperKt$$ExternalSyntheticOutline0.m(6.03f, 11.041f);
        m.curveTo(6.438f, 10.992f, 6.809f, 11.282f, 6.858f, 11.69f);
        m.curveTo(6.884f, 11.905f, 6.817f, 12.109f, 6.687f, 12.261f);
        m.curveTo(6.57f, 12.399f, 6.403f, 12.495f, 6.21f, 12.518f);
        m.curveTo(5.802f, 12.568f, 5.431f, 12.277f, 5.382f, 11.869f);
        m.curveTo(5.332f, 11.461f, 5.623f, 11.091f, 6.03f, 11.041f);
        m.close();
        m.moveTo(11.793f, 5.489f);
        m.curveTo(12.201f, 5.44f, 12.571f, 5.731f, 12.62f, 6.138f);
        m.curveTo(12.67f, 6.546f, 12.38f, 6.917f, 11.971f, 6.966f);
        m.curveTo(11.748f, 6.993f, 11.535f, 6.918f, 11.381f, 6.777f);
        m.curveTo(11.254f, 6.661f, 11.166f, 6.501f, 11.144f, 6.317f);
        m.curveTo(11.094f, 5.91f, 11.385f, 5.539f, 11.793f, 5.489f);
        m.close();
        m.moveTo(8.041f, 7.734f);
        m.curveTo(8.041f, 7.462f, 8.336f, 7.292f, 8.572f, 7.427f);
        m.lineTo(10.491f, 8.681f);
        m.curveTo(10.727f, 8.818f, 10.727f, 9.158f, 10.491f, 9.294f);
        m.lineTo(8.572f, 10.548f);
        m.curveTo(8.336f, 10.684f, 8.041f, 10.514f, 8.041f, 10.242f);
        WidgetsKt$$ExternalSyntheticOutline0.m(m, 8.041f, 7.734f, 11.293f, 5.724f);
        m.curveTo(10.265f, 5.005f, 8.914f, 4.78f, 7.643f, 5.238f);
        m.curveTo(6.01f, 5.827f, 4.995f, 7.37f, 5.0f, 9.012f);
        m.moveTo(6.705f, 12.275f);
        m.curveTo(7.734f, 12.995f, 9.085f, 13.22f, 10.357f, 12.762f);
        m.curveTo(11.99f, 12.172f, 13.005f, 10.63f, 13.0f, 8.988f);
        builder.m565addPathoIyEayM("", m._nodes, i3, solidColor3, 1.0f, solidColor4, 1.0f, 1.0f, i, i2, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
