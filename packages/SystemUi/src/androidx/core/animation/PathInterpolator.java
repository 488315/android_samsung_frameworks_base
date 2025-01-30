package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.InflateException;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PathInterpolator implements Interpolator {
    public float[] mData;

    public PathInterpolator(Path path) {
        initPath(path);
    }

    public static boolean floatEquals(float f, float f2) {
        return Math.abs(f - f2) < 0.01f;
    }

    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        int length = (this.mData.length / 3) - 1;
        int i = 0;
        while (length - i > 1) {
            int i2 = (i + length) / 2;
            if (f < getXAtIndex(i2)) {
                length = i2;
            } else {
                i = i2;
            }
        }
        float xAtIndex = getXAtIndex(length) - getXAtIndex(i);
        if (xAtIndex == 0.0f) {
            return this.mData[(i * 3) + 2];
        }
        float xAtIndex2 = (f - getXAtIndex(i)) / xAtIndex;
        float[] fArr = this.mData;
        float f2 = fArr[(i * 3) + 2];
        return DependencyGraph$$ExternalSyntheticOutline0.m20m(fArr[(length * 3) + 2], f2, xAtIndex2, f2);
    }

    public final float getXAtIndex(int i) {
        return this.mData[(i * 3) + 1];
    }

    public final void initPath(Path path) {
        float[] approximate = path.approximate(0.002f);
        this.mData = approximate;
        int length = approximate.length / 3;
        int i = 0;
        float f = 0.0f;
        if (floatEquals(getXAtIndex(0), 0.0f) && floatEquals(this.mData[2], 0.0f)) {
            int i2 = length - 1;
            if (floatEquals(getXAtIndex(i2), 1.0f) && floatEquals(this.mData[(i2 * 3) + 2], 1.0f)) {
                float f2 = 0.0f;
                while (i < length) {
                    float f3 = this.mData[i * 3];
                    float xAtIndex = getXAtIndex(i);
                    if (f3 == f && xAtIndex != f2) {
                        throw new IllegalArgumentException("The Path cannot have discontinuity in the X axis.");
                    }
                    if (xAtIndex < f2) {
                        throw new IllegalArgumentException("The Path cannot loop back on itself.");
                    }
                    i++;
                    f = f3;
                    f2 = xAtIndex;
                }
                return;
            }
        }
        throw new IllegalArgumentException("The Path must start at (0,0) and end at (1,1)");
    }

    public PathInterpolator(float f, float f2) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.quadTo(f, f2, 1.0f, 1.0f);
        initPath(path);
    }

    public PathInterpolator(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
        initPath(path);
    }

    public PathInterpolator(Context context, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        this(context.getResources(), context.getTheme(), attributeSet, xmlPullParser);
    }

    public PathInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        TypedArray obtainAttributes;
        int[] iArr = AndroidResources.STYLEABLE_PATH_INTERPOLATOR;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, iArr);
        }
        if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
            String namedString = TypedArrayUtils.getNamedString(obtainAttributes, xmlPullParser, "pathData", 4);
            Path createPathFromPathData = PathParser.createPathFromPathData(namedString);
            if (createPathFromPathData != null) {
                initPath(createPathFromPathData);
            } else {
                throw new InflateException(KeyAttributes$$ExternalSyntheticOutline0.m21m("The path is null, which is created from ", namedString));
            }
        } else if (TypedArrayUtils.hasAttribute(xmlPullParser, "controlX1")) {
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "controlY1")) {
                float namedFloat = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser, "controlX1", 0, 0.0f);
                float namedFloat2 = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser, "controlY1", 1, 0.0f);
                boolean hasAttribute = TypedArrayUtils.hasAttribute(xmlPullParser, "controlX2");
                if (hasAttribute != TypedArrayUtils.hasAttribute(xmlPullParser, "controlY2")) {
                    throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
                }
                if (!hasAttribute) {
                    Path path = new Path();
                    path.moveTo(0.0f, 0.0f);
                    path.quadTo(namedFloat, namedFloat2, 1.0f, 1.0f);
                    initPath(path);
                } else {
                    float namedFloat3 = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser, "controlX2", 2, 0.0f);
                    float namedFloat4 = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser, "controlY2", 3, 0.0f);
                    Path path2 = new Path();
                    path2.moveTo(0.0f, 0.0f);
                    path2.cubicTo(namedFloat, namedFloat2, namedFloat3, namedFloat4, 1.0f, 1.0f);
                    initPath(path2);
                }
            } else {
                throw new InflateException("pathInterpolator requires the controlY1 attribute");
            }
        } else {
            throw new InflateException("pathInterpolator requires the controlX1 attribute");
        }
        obtainAttributes.recycle();
    }
}
