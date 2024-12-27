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
public abstract class SkipNextKt {
    public static final Lazy SkipNext$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.icons.action.SkipNextKt$SkipNext$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = (float) 40.0d;
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("SkipNext", f, f, 40.0f, 40.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
            DefaultConstructorMarker defaultConstructorMarker = null;
            SolidColor solidColor = new SolidColor(ColorKt.Color(4294967295L), defaultConstructorMarker);
            SolidColor solidColor2 = new SolidColor(ColorKt.Color(4294967295L), defaultConstructorMarker);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            PathFillType.Companion.getClass();
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(25.9712f, 11.6f);
            pathBuilder.curveTo(25.2082f, 11.6f, 24.5924f, 12.1645f, 24.5924f, 12.86f);
            pathBuilder.lineTo(24.5924f, 18.866f);
            pathBuilder.curveTo(24.5666f, 18.8492f, 24.5519f, 18.8307f, 24.5262f, 18.8156f);
            pathBuilder.lineTo(14.8635f, 12.277f);
            pathBuilder.curveTo(14.5767f, 12.1074f, 14.2788f, 12.02f, 13.981f, 12.02f);
            pathBuilder.curveTo(13.3173f, 12.02f, 12.65f, 12.487f, 12.65f, 13.3808f);
            pathBuilder.lineTo(12.65f, 26.6192f);
            pathBuilder.curveTo(12.65f, 27.513f, 13.3173f, 27.98f, 13.981f, 27.98f);
            pathBuilder.curveTo(14.2788f, 27.98f, 14.5748f, 27.8943f, 14.8635f, 27.723f);
            pathBuilder.lineTo(24.5262f, 21.1827f);
            pathBuilder.curveTo(24.5519f, 21.1676f, 24.5666f, 21.1491f, 24.5924f, 21.134f);
            pathBuilder.lineTo(24.5924f, 27.14f);
            pathBuilder.curveTo(24.5924f, 27.8355f, 25.2082f, 28.4f, 25.9712f, 28.4f);
            pathBuilder.curveTo(26.7323f, 28.4f, 27.35f, 27.8355f, 27.35f, 27.14f);
            pathBuilder.lineTo(27.35f, 12.86f);
            pathBuilder.curveTo(27.35f, 12.1645f, 26.7323f, 11.6f, 25.9712f, 11.6f);
            ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, solidColor2, 0.25f, 0, 0, 4.0f);
            return builder.build();
        }
    });
}
