package com.android.systemui.media.mediaoutput.icons.action;

import androidx.compose.material.icons.filled.CloseKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class VolDownKt {
    public static final Lazy VolDown$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.action.VolDownKt$VolDown$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 24.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("VolDown", f, f, 24.0f, 24.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(4278190080L), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            int i = StrokeCap.Round;
            StrokeJoin.Companion.getClass();
            int i2 = StrokeJoin.Round;
            PathFillType.Companion.getClass();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, CloseKt$$ExternalSyntheticOutline0.m(4.125f, 12.0f, 19.875f, 12.0f)._nodes, PathFillType.EvenOdd, solidColor, solidColor2, 1.5f, i, i2, 4.0f);
            return builder.build();
        }
    });
}
