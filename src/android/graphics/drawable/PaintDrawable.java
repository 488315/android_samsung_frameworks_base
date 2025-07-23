package android.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import com.android.internal.R;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class PaintDrawable extends ShapeDrawable {
    public PaintDrawable() {
    }

    public PaintDrawable(int i) {
        getPaint().setColor(i);
    }

    public void setCornerRadius(float f) {
        float[] fArr;
        if (f > 0.0f) {
            fArr = new float[8];
            for (int i = 0; i < 8; i++) {
                fArr[i] = f;
            }
        } else {
            fArr = null;
        }
        setCornerRadii(fArr);
    }

    public void setCornerRadii(float[] fArr) {
        if (fArr == null) {
            if (getShape() != null) {
                setShape(null);
            }
        } else {
            setShape(new RoundRectShape(fArr, null, null));
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.ShapeDrawable
    protected boolean inflateTag(String str, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        if (str.equals("corners")) {
            TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.DrawableCorners);
            int dimensionPixelSize = obtainAttributes.getDimensionPixelSize(0, 0);
            setCornerRadius(dimensionPixelSize);
            int dimensionPixelSize2 = obtainAttributes.getDimensionPixelSize(1, dimensionPixelSize);
            int dimensionPixelSize3 = obtainAttributes.getDimensionPixelSize(2, dimensionPixelSize);
            int dimensionPixelSize4 = obtainAttributes.getDimensionPixelSize(3, dimensionPixelSize);
            int dimensionPixelSize5 = obtainAttributes.getDimensionPixelSize(4, dimensionPixelSize);
            if (dimensionPixelSize2 != dimensionPixelSize || dimensionPixelSize3 != dimensionPixelSize || dimensionPixelSize4 != dimensionPixelSize || dimensionPixelSize5 != dimensionPixelSize) {
                float f = dimensionPixelSize2;
                float f2 = dimensionPixelSize3;
                float f3 = dimensionPixelSize4;
                float f4 = dimensionPixelSize5;
                setCornerRadii(new float[]{f, f, f2, f2, f3, f3, f4, f4});
            }
            obtainAttributes.recycle();
            return true;
        }
        return super.inflateTag(str, resources, xmlPullParser, attributeSet);
    }
}
