package com.android.systemui.media.mediaoutput.icons.action;

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
public abstract class CloseKt {
    public static final Lazy Close$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.action.CloseKt$Close$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 40.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("Close", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(4294967295L), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(28.0683f, 10.8f);
            pathBuilder.curveTo(28.358f, 10.8f, 28.6478f, 10.9105f, 28.8687f, 11.1313f);
            pathBuilder.curveTo(29.0896f, 11.3523f, 29.2f, 11.6415f, 29.2f, 11.9306f);
            pathBuilder.curveTo(29.2f, 12.2198f, 29.0896f, 12.509f, 28.8686f, 12.7299f);
            pathBuilder.lineTo(28.8686f, 12.7299f);
            pathBuilder.lineTo(21.5986f, 19.9975f);
            pathBuilder.lineTo(28.8687f, 27.2676f);
            pathBuilder.curveTo(29.0896f, 27.4885f, 29.2f, 27.7777f, 29.2f, 28.0669f);
            pathBuilder.curveTo(29.2f, 28.3561f, 29.0896f, 28.6452f, 28.8687f, 28.8662f);
            pathBuilder.curveTo(28.647f, 29.0878f, 28.3577f, 29.1966f, 28.0681f, 29.1966f);
            pathBuilder.curveTo(27.7783f, 29.1966f, 27.4887f, 29.0875f, 27.2689f, 28.8662f);
            pathBuilder.lineTo(27.2689f, 28.8662f);
            pathBuilder.lineTo(19.9988f, 21.5985f);
            pathBuilder.lineTo(12.7312f, 28.8662f);
            pathBuilder.curveTo(12.5095f, 29.0878f, 12.2217f, 29.1966f, 11.9306f, 29.1966f);
            pathBuilder.curveTo(11.641f, 29.1966f, 11.353f, 29.0879f, 11.1313f, 28.8662f);
            pathBuilder.curveTo(10.9104f, 28.6452f, 10.8f, 28.3561f, 10.8f, 28.0669f);
            pathBuilder.curveTo(10.8f, 27.7777f, 10.9104f, 27.4885f, 11.1313f, 27.2676f);
            pathBuilder.lineTo(11.1313f, 27.2676f);
            pathBuilder.lineTo(18.4002f, 19.9975f);
            pathBuilder.lineTo(11.1313f, 12.7299f);
            pathBuilder.curveTo(10.9104f, 12.509f, 10.8f, 12.2198f, 10.8f, 11.9306f);
            pathBuilder.curveTo(10.8f, 11.6415f, 10.9104f, 11.3523f, 11.1313f, 11.1313f);
            pathBuilder.curveTo(11.3522f, 10.9104f, 11.6417f, 10.8f, 11.9312f, 10.8f);
            pathBuilder.curveTo(12.2208f, 10.8f, 12.5103f, 10.9104f, 12.7311f, 11.1313f);
            pathBuilder.lineTo(12.7311f, 11.1313f);
            pathBuilder.lineTo(19.9988f, 18.3978f);
            pathBuilder.lineTo(27.2686f, 11.1316f);
            pathBuilder.curveTo(27.4888f, 10.9106f, 27.7784f, 10.8f, 28.0683f, 10.8f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 0.4f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
