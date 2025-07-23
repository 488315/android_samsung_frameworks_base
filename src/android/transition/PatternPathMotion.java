package android.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.PathParser;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class PatternPathMotion extends PathMotion {
    private Path mOriginalPatternPath;
    private final Path mPatternPath;
    private final Matrix mTempMatrix;

    public PatternPathMotion() {
        Path path = new Path();
        this.mPatternPath = path;
        this.mTempMatrix = new Matrix();
        path.lineTo(1.0f, 0.0f);
        this.mOriginalPatternPath = path;
    }

    public PatternPathMotion(Context context, AttributeSet attributeSet) {
        this.mPatternPath = new Path();
        this.mTempMatrix = new Matrix();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PatternPathMotion);
        try {
            String string = obtainStyledAttributes.getString(0);
            if (string == null) {
                throw new RuntimeException("pathData must be supplied for patternPathMotion");
            }
            setPatternPath(PathParser.createPathFromPathData(string));
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public PatternPathMotion(Path path) {
        this.mPatternPath = new Path();
        this.mTempMatrix = new Matrix();
        setPatternPath(path);
    }

    public Path getPatternPath() {
        return this.mOriginalPatternPath;
    }

    public void setPatternPath(Path path) {
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
        this.mTempMatrix.setTranslate(-f3, -f4);
        double d = f - f3;
        double d2 = f2 - f4;
        float hypot = 1.0f / ((float) Math.hypot(d, d2));
        this.mTempMatrix.postScale(hypot, hypot);
        this.mTempMatrix.postRotate((float) Math.toDegrees(-Math.atan2(d2, d)));
        path.transform(this.mTempMatrix, this.mPatternPath);
        this.mOriginalPatternPath = path;
    }

    @Override // android.transition.PathMotion
    public Path getPath(float f, float f2, float f3, float f4) {
        double d = f3 - f;
        double d2 = f4 - f2;
        float hypot = (float) Math.hypot(d, d2);
        double atan2 = Math.atan2(d2, d);
        this.mTempMatrix.setScale(hypot, hypot);
        this.mTempMatrix.postRotate((float) Math.toDegrees(atan2));
        this.mTempMatrix.postTranslate(f, f2);
        Path path = new Path();
        this.mPatternPath.transform(this.mTempMatrix, path);
        return path;
    }
}
