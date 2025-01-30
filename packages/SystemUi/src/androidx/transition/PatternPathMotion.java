package androidx.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class PatternPathMotion extends PathMotion {
    public final Path mPatternPath;
    public final Matrix mTempMatrix;

    public PatternPathMotion() {
        Path path = new Path();
        this.mPatternPath = path;
        this.mTempMatrix = new Matrix();
        path.lineTo(1.0f, 0.0f);
    }

    @Override // androidx.transition.PathMotion
    public final Path getPath(float f, float f2, float f3, float f4) {
        float f5 = f4 - f2;
        float sqrt = (float) Math.sqrt((f5 * f5) + (r6 * r6));
        double atan2 = Math.atan2(f5, f3 - f);
        Matrix matrix = this.mTempMatrix;
        matrix.setScale(sqrt, sqrt);
        matrix.postRotate((float) Math.toDegrees(atan2));
        matrix.postTranslate(f, f2);
        Path path = new Path();
        this.mPatternPath.transform(matrix, path);
        return path;
    }

    public final void setPatternPath(Path path) {
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float[] fArr = new float[2];
        pathMeasure.getPosTan(pathMeasure.getLength(), fArr, null);
        float f = fArr[0];
        float f2 = fArr[1];
        pathMeasure.getPosTan(0.0f, fArr, null);
        float f3 = fArr[0];
        float f4 = fArr[1];
        if (f3 == f && f4 == f2) {
            throw new IllegalArgumentException("pattern must not end at the starting point");
        }
        Matrix matrix = this.mTempMatrix;
        matrix.setTranslate(-f3, -f4);
        float f5 = f2 - f4;
        float sqrt = 1.0f / ((float) Math.sqrt((f5 * f5) + (r2 * r2)));
        matrix.postScale(sqrt, sqrt);
        matrix.postRotate((float) Math.toDegrees(-Math.atan2(f5, f - f3)));
        path.transform(matrix, this.mPatternPath);
    }

    public PatternPathMotion(Context context, AttributeSet attributeSet) {
        this.mPatternPath = new Path();
        this.mTempMatrix = new Matrix();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.PATTERN_PATH_MOTION);
        try {
            String namedString = TypedArrayUtils.getNamedString(obtainStyledAttributes, (XmlPullParser) attributeSet, "patternPathData", 0);
            if (namedString != null) {
                setPatternPath(PathParser.createPathFromPathData(namedString));
                return;
            }
            throw new RuntimeException("pathData must be supplied for patternPathMotion");
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public PatternPathMotion(Path path) {
        this.mPatternPath = new Path();
        this.mTempMatrix = new Matrix();
        setPatternPath(path);
    }
}
