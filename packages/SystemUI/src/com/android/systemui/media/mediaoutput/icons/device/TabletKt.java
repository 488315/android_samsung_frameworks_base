package com.android.systemui.media.mediaoutput.icons.device;

import androidx.compose.material.icons.filled.KeyboardCommandKeyKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class TabletKt {
    public static final Lazy Tablet$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.device.TabletKt$Tablet$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 36.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("Tablet", f, f, 36.0f, 36.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(26.12f, 9.04f);
            pathBuilder.curveTo(27.509f, 9.04f, 28.64f, 10.17f, 28.64f, 11.56f);
            pathBuilder.lineTo(28.64f, 11.56f);
            pathBuilder.lineTo(28.64f, 24.44f);
            pathBuilder.curveTo(28.64f, 25.83f, 27.509f, 26.96f, 26.12f, 26.96f);
            pathBuilder.lineTo(26.12f, 26.96f);
            pathBuilder.lineTo(9.88f, 26.96f);
            pathBuilder.curveTo(8.49f, 26.96f, 7.36f, 25.83f, 7.36f, 24.44f);
            pathBuilder.lineTo(7.36f, 24.44f);
            pathBuilder.lineTo(7.36f, 11.56f);
            pathBuilder.curveTo(7.36f, 10.17f, 8.49f, 9.04f, 9.88f, 9.04f);
            KeyboardCommandKeyKt$$ExternalSyntheticOutline0.m(pathBuilder, 9.88f, 9.04f, 26.12f, 10.72f);
            pathBuilder.lineTo(9.88f, 10.72f);
            pathBuilder.curveTo(9.417f, 10.72f, 9.04f, 11.097f, 9.04f, 11.56f);
            pathBuilder.lineTo(9.04f, 11.56f);
            pathBuilder.lineTo(9.04f, 24.44f);
            pathBuilder.curveTo(9.04f, 24.903f, 9.417f, 25.28f, 9.88f, 25.28f);
            pathBuilder.lineTo(9.88f, 25.28f);
            pathBuilder.lineTo(26.12f, 25.28f);
            pathBuilder.curveTo(26.583f, 25.28f, 26.96f, 24.903f, 26.96f, 24.44f);
            pathBuilder.lineTo(26.96f, 24.44f);
            pathBuilder.lineTo(26.96f, 11.56f);
            pathBuilder.curveTo(26.96f, 11.097f, 26.583f, 10.72f, 26.12f, 10.72f);
            KeyboardCommandKeyKt$$ExternalSyntheticOutline0.m(pathBuilder, 26.12f, 10.72f, 19.8844f, 21.7202f);
            pathBuilder.curveTo(20.3484f, 21.7202f, 20.7244f, 22.0972f, 20.7244f, 22.5602f);
            pathBuilder.curveTo(20.7244f, 23.0252f, 20.3484f, 23.4012f, 19.8844f, 23.4012f);
            pathBuilder.lineTo(19.8844f, 23.4012f);
            pathBuilder.lineTo(16.1164f, 23.4012f);
            pathBuilder.curveTo(15.6524f, 23.4012f, 15.2764f, 23.0252f, 15.2764f, 22.5602f);
            pathBuilder.curveTo(15.2764f, 22.0972f, 15.6524f, 21.7202f, 16.1164f, 21.7202f);
            pathBuilder.lineTo(16.1164f, 21.7202f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
