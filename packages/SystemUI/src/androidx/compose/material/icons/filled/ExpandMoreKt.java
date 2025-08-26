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

/* loaded from: classes.dex */
public abstract class ExpandMoreKt {
    public static ImageVector _expandMore;

    public static final ImageVector getExpandMore() {
        ImageVector imageVector = _expandMore;
        if (imageVector != null) {
            return imageVector;
        }
        Dp.Companion companion = Dp.Companion;
        ImageVector.Builder builder = new ImageVector.Builder("Filled.ExpandMore", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
        EmptyList emptyList = VectorKt.EmptyPath;
        Color.Companion.getClass();
        SolidColor solidColor = new SolidColor(Color.Black, null);
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        int i = StrokeJoin.Bevel;
        PathBuilder pathBuilderM = ExpandMoreKt$$ExternalSyntheticOutline0.m(16.59f, 8.59f, 12.0f, 13.17f);
        pathBuilderM.lineTo(7.41f, 8.59f);
        pathBuilderM.lineTo(6.0f, 10.0f);
        pathBuilderM.lineToRelative(6.0f, 6.0f);
        pathBuilderM.lineToRelative(6.0f, -6.0f);
        pathBuilderM.close();
        builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
        ImageVector imageVectorBuild = builder.build();
        _expandMore = imageVectorBuild;
        return imageVectorBuild;
    }
}
