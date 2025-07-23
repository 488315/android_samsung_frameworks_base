package androidx.compose.material.icons.outlined;

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
public abstract class WidgetsKt {
    public static ImageVector _widgets;

    public static final ImageVector getWidgets() {
        ImageVector imageVector = _widgets;
        if (imageVector != null) {
            return imageVector;
        }
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("Outlined.Widgets", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
        EmptyList emptyList = VectorKt.EmptyPath;
        Color.Companion.getClass();
        SolidColor solidColor = new SolidColor(Color.Black, null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        int i = StrokeJoin.Bevel;
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.moveTo(16.66f, 4.52f);
        pathBuilder.lineToRelative(2.83f, 2.83f);
        pathBuilder.lineToRelative(-2.83f, 2.83f);
        pathBuilder.lineToRelative(-2.83f, -2.83f);
        pathBuilder.lineToRelative(2.83f, -2.83f);
        pathBuilder.moveTo(9.0f, 5.0f);
        pathBuilder.verticalLineToRelative(4.0f);
        pathBuilder.lineTo(5.0f, 9.0f);
        pathBuilder.lineTo(5.0f, 5.0f);
        pathBuilder.horizontalLineToRelative(4.0f);
        pathBuilder.moveToRelative(10.0f, 10.0f);
        pathBuilder.verticalLineToRelative(4.0f);
        pathBuilder.horizontalLineToRelative(-4.0f);
        pathBuilder.verticalLineToRelative(-4.0f);
        pathBuilder.horizontalLineToRelative(4.0f);
        pathBuilder.moveTo(9.0f, 15.0f);
        pathBuilder.verticalLineToRelative(4.0f);
        pathBuilder.lineTo(5.0f, 19.0f);
        pathBuilder.verticalLineToRelative(-4.0f);
        pathBuilder.horizontalLineToRelative(4.0f);
        pathBuilder.moveToRelative(7.66f, -13.31f);
        pathBuilder.lineTo(11.0f, 7.34f);
        pathBuilder.lineTo(16.66f, 13.0f);
        pathBuilder.lineToRelative(5.66f, -5.66f);
        pathBuilder.lineToRelative(-5.66f, -5.65f);
        pathBuilder.close();
        pathBuilder.moveTo(11.0f, 3.0f);
        pathBuilder.lineTo(3.0f, 3.0f);
        pathBuilder.verticalLineToRelative(8.0f);
        pathBuilder.horizontalLineToRelative(8.0f);
        WidgetsKt$$ExternalSyntheticOutline0.m(pathBuilder, 11.0f, 3.0f, 21.0f, 13.0f);
        pathBuilder.horizontalLineToRelative(-8.0f);
        pathBuilder.verticalLineToRelative(8.0f);
        pathBuilder.horizontalLineToRelative(8.0f);
        pathBuilder.verticalLineToRelative(-8.0f);
        pathBuilder.close();
        pathBuilder.moveTo(11.0f, 13.0f);
        pathBuilder.lineTo(3.0f, 13.0f);
        pathBuilder.verticalLineToRelative(8.0f);
        pathBuilder.horizontalLineToRelative(8.0f);
        pathBuilder.verticalLineToRelative(-8.0f);
        pathBuilder.close();
        builder.m565addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
        ImageVector build = builder.build();
        _widgets = build;
        return build;
    }
}
