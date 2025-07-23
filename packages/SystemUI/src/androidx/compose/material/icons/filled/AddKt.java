package androidx.compose.material.icons.filled;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.unit.Dp;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AddKt {
    public static ImageVector _add;

    public static final ImageVector getAdd() {
        ImageVector imageVector = _add;
        if (imageVector != null) {
            return imageVector;
        }
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("Filled.Add", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
        EmptyList emptyList = VectorKt.EmptyPath;
        Color.Companion.getClass();
        SolidColor solidColor = new SolidColor(Color.Black, null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        int i = StrokeJoin.Bevel;
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.moveTo(19.0f, 13.0f);
        pathBuilder.horizontalLineToRelative(-6.0f);
        pathBuilder.verticalLineToRelative(6.0f);
        pathBuilder.horizontalLineToRelative(-2.0f);
        pathBuilder.verticalLineToRelative(-6.0f);
        pathBuilder.horizontalLineTo(5.0f);
        pathBuilder.verticalLineToRelative(-2.0f);
        pathBuilder.horizontalLineToRelative(6.0f);
        pathBuilder.verticalLineTo(5.0f);
        pathBuilder.horizontalLineToRelative(2.0f);
        pathBuilder.verticalLineToRelative(6.0f);
        pathBuilder.horizontalLineToRelative(6.0f);
        pathBuilder.verticalLineToRelative(2.0f);
        pathBuilder.close();
        builder.m565addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
        ImageVector build = builder.build();
        _add = build;
        return build;
    }
}
