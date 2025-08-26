package androidx.compose.ui.graphics.vector;

import androidx.compose.ui.graphics.vector.PathNode;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class PathBuilder {
    public final ArrayList _nodes = new ArrayList(32);

    public final void arcToRelative(float f, float f2, float f3, boolean z) {
        this._nodes.add(new PathNode.RelativeArcTo(f, f2, 0.0f, true, z, f3, 0.0f));
    }

    public final void close() {
        this._nodes.add(PathNode.Close.INSTANCE);
    }

    public final void curveTo(float f, float f2, float f3, float f4, float f5, float f6) {
        this._nodes.add(new PathNode.CurveTo(f, f2, f3, f4, f5, f6));
    }

    public final void curveToRelative(float f, float f2, float f3, float f4, float f5, float f6) {
        this._nodes.add(new PathNode.RelativeCurveTo(f, f2, f3, f4, f5, f6));
    }

    public final void horizontalLineTo(float f) {
        this._nodes.add(new PathNode.HorizontalTo(f));
    }

    public final void horizontalLineToRelative(float f) {
        this._nodes.add(new PathNode.RelativeHorizontalTo(f));
    }

    public final void lineTo(float f, float f2) {
        this._nodes.add(new PathNode.LineTo(f, f2));
    }

    public final void lineToRelative(float f, float f2) {
        this._nodes.add(new PathNode.RelativeLineTo(f, f2));
    }

    public final void moveTo(float f, float f2) {
        this._nodes.add(new PathNode.MoveTo(f, f2));
    }

    public final void moveToRelative(float f, float f2) {
        this._nodes.add(new PathNode.RelativeMoveTo(f, f2));
    }

    public final void reflectiveCurveTo(float f, float f2, float f3, float f4) {
        this._nodes.add(new PathNode.ReflectiveCurveTo(f, f2, f3, f4));
    }

    public final void reflectiveCurveToRelative(float f, float f2, float f3, float f4) {
        this._nodes.add(new PathNode.RelativeReflectiveCurveTo(f, f2, f3, f4));
    }

    public final void verticalLineTo(float f) {
        this._nodes.add(new PathNode.VerticalTo(f));
    }

    public final void verticalLineToRelative(float f) {
        this._nodes.add(new PathNode.RelativeVerticalTo(f));
    }
}
