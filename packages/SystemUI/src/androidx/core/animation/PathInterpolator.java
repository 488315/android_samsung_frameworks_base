package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.InflateException;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class PathInterpolator implements Interpolator {
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
            return getYAtIndex(i);
        }
        float xAtIndex2 = (f - getXAtIndex(i)) / xAtIndex;
        float yAtIndex = getYAtIndex(i);
        return ((getYAtIndex(length) - yAtIndex) * xAtIndex2) + yAtIndex;
    }

    public final float getXAtIndex(int i) {
        return this.mData[(i * 3) + 1];
    }

    public final float getYAtIndex(int i) {
        return this.mData[(i * 3) + 2];
    }

    public final void initPath(Path path) {
        float[] fArrApproximate = PathUtils$Api26Impl.approximate(path, 0.002f);
        this.mData = fArrApproximate;
        int length = fArrApproximate.length / 3;
        int i = 0;
        float f = 0.0f;
        if (floatEquals(getXAtIndex(0), 0.0f) && floatEquals(getYAtIndex(0), 0.0f)) {
            int i2 = length - 1;
            if (floatEquals(getXAtIndex(i2), 1.0f) && floatEquals(getYAtIndex(i2), 1.0f)) {
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
        Path pathM = PathInterpolator$$ExternalSyntheticOutline0.m(0.0f, 0.0f);
        pathM.quadTo(f, f2, 1.0f, 1.0f);
        initPath(pathM);
    }

    public PathInterpolator(float f, float f2, float f3, float f4) {
        Path pathM = PathInterpolator$$ExternalSyntheticOutline0.m(0.0f, 0.0f);
        pathM.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
        initPath(pathM);
    }

    public PathInterpolator(Context context, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        this(context.getResources(), context.getTheme(), attributeSet, xmlPullParser);
    }

    public PathInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        TypedArray typedArrayObtainAttributes;
        int[] iArr = AndroidResources.STYLEABLE_PATH_INTERPOLATOR;
        if (theme != null) {
            typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        } else {
            typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, iArr);
        }
        if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
            initPath(PathParser.createPathFromPathData(TypedArrayUtils.getNamedString(typedArrayObtainAttributes, xmlPullParser, "pathData", 4)));
        } else if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "controlX1") != null) {
            if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "controlY1") != null) {
                float f = !(xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "controlX1") != null) ? 0.0f : typedArrayObtainAttributes.getFloat(0, 0.0f);
                float f2 = !(xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "controlY1") != null) ? 0.0f : typedArrayObtainAttributes.getFloat(1, 0.0f);
                boolean z = xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "controlX2") != null;
                if (z != (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "controlY2") != null)) {
                    throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
                }
                if (!z) {
                    Path pathM = PathInterpolator$$ExternalSyntheticOutline0.m(0.0f, 0.0f);
                    pathM.quadTo(f, f2, 1.0f, 1.0f);
                    initPath(pathM);
                } else {
                    float f3 = xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "controlX2") != null ? typedArrayObtainAttributes.getFloat(2, 0.0f) : 0.0f;
                    float f4 = xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "controlY2") != null ? typedArrayObtainAttributes.getFloat(3, 0.0f) : 0.0f;
                    Path pathM2 = PathInterpolator$$ExternalSyntheticOutline0.m(0.0f, 0.0f);
                    pathM2.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
                    initPath(pathM2);
                }
            } else {
                throw new InflateException("pathInterpolator requires the controlY1 attribute");
            }
        } else {
            throw new InflateException("pathInterpolator requires the controlX1 attribute");
        }
        typedArrayObtainAttributes.recycle();
    }
}
