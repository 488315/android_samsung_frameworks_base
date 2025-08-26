package com.android.systemui.media.mediaoutput.icons.action;

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
public final /* synthetic */ class OutputSwitcherKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f = (float) 100.0d;
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("OutputSwitcher", f, f, 100.0f, 100.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        SolidColor solidColor = new SolidColor(ColorKt.Color(0), null);
        SolidColor solidColor2 = new SolidColor(ColorKt.Color(4278190080L), null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        PathFillType.Companion.getClass();
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.moveTo(50.0f, 50.0f);
        pathBuilder.moveToRelative(-40.0f, 0.0f);
        pathBuilder.arcToRelative(40.0f, 40.0f, 80.0f, true);
        pathBuilder.arcToRelative(40.0f, 40.0f, -80.0f, true);
        builder.m567addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, solidColor2, 1.0f, 8.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f);
        SolidColor solidColor3 = new SolidColor(ColorKt.Color(0), null);
        SolidColor solidColor4 = new SolidColor(ColorKt.Color(4278190080L), null);
        int i = StrokeCap.Round;
        int i2 = StrokeJoin.Round;
        PathBuilder pathBuilderM = ShortcutHelperKt$$ExternalSyntheticOutline0.m(65.0f, 30.0f);
        pathBuilderM.curveTo(55.0f, 20.0f, 35.0f, 20.0f, 35.0f, 35.0f);
        pathBuilderM.curveTo(35.0f, 50.0f, 65.0f, 50.0f, 65.0f, 65.0f);
        pathBuilderM.curveTo(65.0f, 80.0f, 45.0f, 80.0f, 35.0f, 70.0f);
        builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor3, 1.0f, solidColor4, 1.0f, 8.0f, i, i2, 4.0f, 0.0f, 1.0f, 0.0f);
        return builder.build();
    }
}
