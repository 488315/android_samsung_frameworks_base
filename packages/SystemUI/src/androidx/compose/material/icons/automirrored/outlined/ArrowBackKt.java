package androidx.compose.material.icons.automirrored.outlined;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.unit.Dp;
import kotlin.collections.EmptyList;

/* loaded from: classes.dex */
public abstract class ArrowBackKt {
    public static ImageVector _arrowBack;

    public static final ImageVector getArrowBack() {
        ImageVector imageVector = _arrowBack;
        if (imageVector != null) {
            return imageVector;
        }
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("AutoMirrored.Outlined.ArrowBack", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, true, 96, null);
        EmptyList emptyList = VectorKt.EmptyPath;
        Color.Companion.getClass();
        SolidColor solidColor = new SolidColor(Color.Black, null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        int i = StrokeJoin.Bevel;
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.moveTo(20.0f, 11.0f);
        pathBuilder.horizontalLineTo(7.83f);
        pathBuilder.lineToRelative(5.59f, -5.59f);
        pathBuilder.lineTo(12.0f, 4.0f);
        pathBuilder.lineToRelative(-8.0f, 8.0f);
        pathBuilder.lineToRelative(8.0f, 8.0f);
        pathBuilder.lineToRelative(1.41f, -1.41f);
        pathBuilder.lineTo(7.83f, 13.0f);
        pathBuilder.horizontalLineTo(20.0f);
        pathBuilder.verticalLineToRelative(-2.0f);
        pathBuilder.close();
        builder.m567addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
        ImageVector imageVectorBuild = builder.build();
        _arrowBack = imageVectorBuild;
        return imageVectorBuild;
    }
}
