package com.android.systemui.media.mediaoutput.icons.action;

import androidx.compose.material.icons.Icons;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.unit.Dp;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class ExpandLessKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Icons.Filled filled = Icons.Filled.INSTANCE;
        ImageVector imageVector = androidx.compose.material.icons.filled.ExpandLessKt._expandLess;
        if (imageVector != null) {
            return imageVector;
        }
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("Filled.ExpandLess", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
        EmptyList emptyList = VectorKt.EmptyPath;
        Color.Companion.getClass();
        SolidColor solidColor = new SolidColor(Color.Black, null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        int i = StrokeJoin.Bevel;
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.moveTo(12.0f, 8.0f);
        pathBuilder.lineToRelative(-6.0f, 6.0f);
        pathBuilder.lineToRelative(1.41f, 1.41f);
        pathBuilder.lineTo(12.0f, 10.83f);
        pathBuilder.lineToRelative(4.59f, 4.58f);
        pathBuilder.lineTo(18.0f, 14.0f);
        pathBuilder.close();
        builder.m567addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
        ImageVector imageVectorBuild = builder.build();
        androidx.compose.material.icons.filled.ExpandLessKt._expandLess = imageVectorBuild;
        return imageVectorBuild;
    }
}
