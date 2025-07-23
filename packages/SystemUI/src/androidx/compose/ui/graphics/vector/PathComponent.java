package androidx.compose.ui.graphics.vector;

import android.graphics.Path;
import android.graphics.PathMeasure;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPathMeasure;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PathComponent extends VNode {
    public Brush fill;
    public float fillAlpha;
    public boolean isPathDirty;
    public boolean isStrokeDirty;
    public boolean isTrimPathDirty;
    public final AndroidPath path;
    public List pathData;
    public final Lazy pathMeasure$delegate;
    public AndroidPath renderPath;
    public Brush stroke;
    public float strokeAlpha;
    public int strokeLineCap;
    public int strokeLineJoin;
    public float strokeLineMiter;
    public float strokeLineWidth;
    public Stroke strokeStyle;
    public float trimPathEnd;
    public float trimPathOffset;
    public float trimPathStart;

    public PathComponent() {
        super(null);
        this.fillAlpha = 1.0f;
        this.pathData = VectorKt.EmptyPath;
        this.strokeAlpha = 1.0f;
        this.strokeLineCap = 0;
        this.strokeLineJoin = 0;
        this.strokeLineMiter = 4.0f;
        this.trimPathEnd = 1.0f;
        this.isPathDirty = true;
        this.isStrokeDirty = true;
        AndroidPath Path = AndroidPath_androidKt.Path();
        this.path = Path;
        this.renderPath = Path;
        this.pathMeasure$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: androidx.compose.ui.graphics.vector.PathComponent$pathMeasure$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new AndroidPathMeasure(new PathMeasure());
            }
        });
    }

    @Override // androidx.compose.ui.graphics.vector.VNode
    public final void draw(DrawScope drawScope) {
        Stroke stroke;
        if (this.isPathDirty) {
            PathParserKt.toPath(this.pathData, this.path);
            updateRenderPath();
        } else if (this.isTrimPathDirty) {
            updateRenderPath();
        }
        this.isPathDirty = false;
        this.isTrimPathDirty = false;
        Brush brush = this.fill;
        if (brush != null) {
            DrawScope.m536drawPathGBMwjPU$default(drawScope, this.renderPath, brush, this.fillAlpha, null, 56);
        }
        Brush brush2 = this.stroke;
        if (brush2 != null) {
            Stroke stroke2 = this.strokeStyle;
            if (this.isStrokeDirty || stroke2 == null) {
                Stroke stroke3 = new Stroke(this.strokeLineWidth, this.strokeLineMiter, this.strokeLineCap, this.strokeLineJoin, null, 16, null);
                this.strokeStyle = stroke3;
                this.isStrokeDirty = false;
                stroke = stroke3;
            } else {
                stroke = stroke2;
            }
            DrawScope.m536drawPathGBMwjPU$default(drawScope, this.renderPath, brush2, this.strokeAlpha, stroke, 48);
        }
    }

    public final String toString() {
        return this.path.toString();
    }

    public final void updateRenderPath() {
        int i;
        float f = this.trimPathStart;
        AndroidPath androidPath = this.path;
        if (f == 0.0f && this.trimPathEnd == 1.0f) {
            this.renderPath = androidPath;
            return;
        }
        if (Intrinsics.areEqual(this.renderPath, androidPath)) {
            this.renderPath = AndroidPath_androidKt.Path();
        } else {
            if (this.renderPath.internalPath.getFillType() == Path.FillType.EVEN_ODD) {
                PathFillType.Companion.getClass();
                i = PathFillType.EvenOdd;
            } else {
                PathFillType.Companion.getClass();
                i = 0;
            }
            this.renderPath.internalPath.rewind();
            this.renderPath.m444setFillTypeoQ8Xj4U(i);
        }
        Lazy lazy = this.pathMeasure$delegate;
        ((AndroidPathMeasure) ((androidx.compose.ui.graphics.PathMeasure) lazy.getValue())).internalPathMeasure.setPath(androidPath != null ? androidPath.internalPath : null, false);
        float length = ((AndroidPathMeasure) ((androidx.compose.ui.graphics.PathMeasure) lazy.getValue())).internalPathMeasure.getLength();
        float f2 = this.trimPathStart;
        float f3 = this.trimPathOffset;
        float f4 = ((f2 + f3) % 1.0f) * length;
        float f5 = ((this.trimPathEnd + f3) % 1.0f) * length;
        if (f4 <= f5) {
            ((AndroidPathMeasure) ((androidx.compose.ui.graphics.PathMeasure) lazy.getValue())).getSegment(f4, f5, this.renderPath);
        } else {
            ((AndroidPathMeasure) ((androidx.compose.ui.graphics.PathMeasure) lazy.getValue())).getSegment(f4, length, this.renderPath);
            ((AndroidPathMeasure) ((androidx.compose.ui.graphics.PathMeasure) lazy.getValue())).getSegment(0.0f, f5, this.renderPath);
        }
    }
}
