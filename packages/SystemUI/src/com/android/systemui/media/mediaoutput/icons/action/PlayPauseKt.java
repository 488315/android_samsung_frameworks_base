package com.android.systemui.media.mediaoutput.icons.action;

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
public abstract class PlayPauseKt {
    public static final Lazy PlayPause$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.action.PlayPauseKt$PlayPause$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 24.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("PlayPause", f, f, 24.0f, 24.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4294638335L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(0), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(14.0f, 6.0f);
            pathBuilder.curveTo(14.5089f, 6.0f, 14.935f, 6.3634f, 14.9937f, 6.8448f);
            pathBuilder.lineTo(15.0f, 6.9511f);
            pathBuilder.lineTo(15.0f, 16.5489f);
            pathBuilder.curveTo(15.0f, 17.0742f, 14.5523f, 17.5f, 14.0f, 17.5f);
            pathBuilder.curveTo(13.4911f, 17.5f, 13.065f, 17.1366f, 13.0063f, 16.6552f);
            pathBuilder.lineTo(13.0f, 16.5489f);
            pathBuilder.lineTo(12.9992f, 12.784f);
            pathBuilder.lineTo(6.4716f, 17.1132f);
            pathBuilder.curveTo(5.8699f, 17.5123f, 5.0729f, 17.1192f, 5.0047f, 16.4261f);
            pathBuilder.lineTo(5.0f, 16.3298f);
            pathBuilder.lineTo(5.0f, 7.1156f);
            pathBuilder.curveTo(5.0f, 6.3636f, 5.8426f, 5.915f, 6.4716f, 6.3322f);
            pathBuilder.lineTo(12.9992f, 10.6608f);
            pathBuilder.lineTo(13.0f, 6.9511f);
            pathBuilder.curveTo(13.0f, 6.4586f, 13.3935f, 6.0536f, 13.8978f, 6.0049f);
            KeyboardCommandKeyKt$$ExternalSyntheticOutline0.m(pathBuilder, 14.0f, 6.0f, 18.0f, 6.0f);
            pathBuilder.curveTo(17.4477f, 6.0f, 17.0f, 6.4258f, 17.0f, 6.9511f);
            pathBuilder.lineTo(17.0f, 16.5489f);
            pathBuilder.lineTo(17.0063f, 16.6552f);
            pathBuilder.curveTo(17.065f, 17.1366f, 17.4911f, 17.5f, 18.0f, 17.5f);
            pathBuilder.curveTo(18.5523f, 17.5f, 19.0f, 17.0742f, 19.0f, 16.5489f);
            pathBuilder.lineTo(19.0f, 6.9511f);
            pathBuilder.lineTo(18.9937f, 6.8448f);
            pathBuilder.curveTo(18.935f, 6.3634f, 18.5089f, 6.0f, 18.0f, 6.0f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 1.0f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
