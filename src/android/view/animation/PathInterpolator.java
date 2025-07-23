package android.view.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.animation.HasNativeInterpolator;
import android.graphics.animation.NativeInterpolator;
import android.graphics.animation.NativeInterpolatorFactory;
import android.util.AttributeSet;
import android.util.PathParser;
import android.view.InflateException;
import com.android.internal.R;

@HasNativeInterpolator
/* loaded from: classes4.dex */
public class PathInterpolator extends BaseInterpolator implements NativeInterpolator {
    private static final float PRECISION = 0.002f;
    private float[] mX;
    private float[] mY;

    public PathInterpolator(Path path) {
        initPath(path);
    }

    public PathInterpolator(float f, float f2) {
        initQuad(f, f2);
    }

    public PathInterpolator(float f, float f2, float f3, float f4) {
        initCubic(f, f2, f3, f4);
    }

    public PathInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public PathInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray obtainAttributes;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.PathInterpolator, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.PathInterpolator);
        }
        parseInterpolatorFromTypeArray(obtainAttributes);
        setChangingConfiguration(obtainAttributes.getChangingConfigurations());
        obtainAttributes.recycle();
    }

    private void parseInterpolatorFromTypeArray(TypedArray typedArray) {
        if (typedArray.hasValue(4)) {
            String string = typedArray.getString(4);
            Path createPathFromPathData = PathParser.createPathFromPathData(string);
            if (createPathFromPathData == null) {
                throw new InflateException("The path is null, which is created from " + string);
            }
            initPath(createPathFromPathData);
            return;
        }
        if (!typedArray.hasValue(0)) {
            throw new InflateException("pathInterpolator requires the controlX1 attribute");
        }
        if (!typedArray.hasValue(1)) {
            throw new InflateException("pathInterpolator requires the controlY1 attribute");
        }
        float f = typedArray.getFloat(0, 0.0f);
        float f2 = typedArray.getFloat(1, 0.0f);
        boolean hasValue = typedArray.hasValue(2);
        if (hasValue != typedArray.hasValue(3)) {
            throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
        }
        if (!hasValue) {
            initQuad(f, f2);
        } else {
            initCubic(f, f2, typedArray.getFloat(2, 0.0f), typedArray.getFloat(3, 0.0f));
        }
    }

    private void initQuad(float f, float f2) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.quadTo(f, f2, 1.0f, 1.0f);
        initPath(path);
    }

    private void initCubic(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
        initPath(path);
    }

    private void initPath(Path path) {
        float[] approximate = path.approximate(0.002f);
        int length = approximate.length / 3;
        float f = 0.0f;
        if (approximate[1] != 0.0f || approximate[2] != 0.0f || approximate[approximate.length - 2] != 1.0f || approximate[approximate.length - 1] != 1.0f) {
            throw new IllegalArgumentException("The Path must start at (0,0) and end at (1,1)");
        }
        this.mX = new float[length];
        this.mY = new float[length];
        int i = 0;
        int i2 = 0;
        float f2 = 0.0f;
        while (i < length) {
            float f3 = approximate[i2];
            int i3 = i2 + 2;
            float f4 = approximate[i2 + 1];
            i2 += 3;
            float f5 = approximate[i3];
            if (f3 == f && f4 != f2) {
                throw new IllegalArgumentException("The Path cannot have discontinuity in the X axis.");
            }
            if (f4 < f2) {
                throw new IllegalArgumentException("The Path cannot loop back on itself.");
            }
            this.mX[i] = f4;
            this.mY[i] = f5;
            i++;
            f2 = f4;
            f = f3;
        }
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        int length = this.mX.length - 1;
        int i = 0;
        while (length - i > 1) {
            int i2 = (i + length) / 2;
            if (f < this.mX[i2]) {
                length = i2;
            } else {
                i = i2;
            }
        }
        float[] fArr = this.mX;
        float f2 = fArr[length];
        float f3 = fArr[i];
        float f4 = f2 - f3;
        if (f4 == 0.0f) {
            return this.mY[i];
        }
        float[] fArr2 = this.mY;
        float f5 = fArr2[i];
        return f5 + (((f - f3) / f4) * (fArr2[length] - f5));
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return NativeInterpolatorFactory.createPathInterpolator(this.mX, this.mY);
    }
}
