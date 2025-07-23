package android.view.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.Animation;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class ScaleAnimation extends Animation {
    private float mFromX;
    private int mFromXData;
    private int mFromXType;
    private float mFromY;
    private int mFromYData;
    private int mFromYType;
    private float mPivotX;
    private int mPivotXType;
    private float mPivotXValue;
    private float mPivotY;
    private int mPivotYType;
    private float mPivotYValue;
    private final Resources mResources;
    private float mToX;
    private int mToXData;
    private int mToXType;
    private float mToY;
    private int mToYData;
    private int mToYType;

    public ScaleAnimation(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
        this.mFromXData = 0;
        this.mToXData = 0;
        this.mFromYData = 0;
        this.mToYData = 0;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = 0.0f;
        this.mPivotYValue = 0.0f;
        this.mResources = context.getResources();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ScaleAnimation);
        TypedValue peekValue = obtainStyledAttributes.peekValue(2);
        this.mFromX = 0.0f;
        if (peekValue != null) {
            if (peekValue.type == 4) {
                this.mFromX = peekValue.getFloat();
            } else {
                this.mFromXType = peekValue.type;
                this.mFromXData = peekValue.data;
            }
        }
        TypedValue peekValue2 = obtainStyledAttributes.peekValue(3);
        this.mToX = 0.0f;
        if (peekValue2 != null) {
            if (peekValue2.type == 4) {
                this.mToX = peekValue2.getFloat();
            } else {
                this.mToXType = peekValue2.type;
                this.mToXData = peekValue2.data;
            }
        }
        TypedValue peekValue3 = obtainStyledAttributes.peekValue(4);
        this.mFromY = 0.0f;
        if (peekValue3 != null) {
            if (peekValue3.type == 4) {
                this.mFromY = peekValue3.getFloat();
            } else {
                this.mFromYType = peekValue3.type;
                this.mFromYData = peekValue3.data;
            }
        }
        TypedValue peekValue4 = obtainStyledAttributes.peekValue(5);
        this.mToY = 0.0f;
        if (peekValue4 != null) {
            if (peekValue4.type == 4) {
                this.mToY = peekValue4.getFloat();
            } else {
                this.mToYType = peekValue4.type;
                this.mToYData = peekValue4.data;
            }
        }
        Animation.Description parseValue = Animation.Description.parseValue(obtainStyledAttributes.peekValue(0), context);
        this.mPivotXType = parseValue.type;
        this.mPivotXValue = parseValue.value;
        Animation.Description parseValue2 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(1), context);
        this.mPivotYType = parseValue2.type;
        this.mPivotYValue = parseValue2.value;
        obtainStyledAttributes.recycle();
        initializePivotPoint();
    }

    public ScaleAnimation(float f, float f2, float f3, float f4) {
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
        this.mFromXData = 0;
        this.mToXData = 0;
        this.mFromYData = 0;
        this.mToYData = 0;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = 0.0f;
        this.mPivotYValue = 0.0f;
        this.mResources = null;
        this.mFromX = f;
        this.mToX = f2;
        this.mFromY = f3;
        this.mToY = f4;
        this.mPivotX = 0.0f;
        this.mPivotY = 0.0f;
    }

    public ScaleAnimation(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
        this.mFromXData = 0;
        this.mToXData = 0;
        this.mFromYData = 0;
        this.mToYData = 0;
        this.mResources = null;
        this.mFromX = f;
        this.mToX = f2;
        this.mFromY = f3;
        this.mToY = f4;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = f5;
        this.mPivotYValue = f6;
        initializePivotPoint();
    }

    public ScaleAnimation(float f, float f2, float f3, float f4, int i, float f5, int i2, float f6) {
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
        this.mFromXData = 0;
        this.mToXData = 0;
        this.mFromYData = 0;
        this.mToYData = 0;
        this.mResources = null;
        this.mFromX = f;
        this.mToX = f2;
        this.mFromY = f3;
        this.mToY = f4;
        this.mPivotXValue = f5;
        this.mPivotXType = i;
        this.mPivotYValue = f6;
        this.mPivotYType = i2;
        initializePivotPoint();
    }

    private void initializePivotPoint() {
        if (this.mPivotXType == 0) {
            this.mPivotX = this.mPivotXValue;
        }
        if (this.mPivotYType == 0) {
            this.mPivotY = this.mPivotYValue;
        }
    }

    @Override // android.view.animation.Animation
    protected void applyTransformation(float f, Transformation transformation) {
        float scaleFactor = getScaleFactor();
        float f2 = this.mFromX;
        float f3 = (f2 == 1.0f && this.mToX == 1.0f) ? 1.0f : f2 + ((this.mToX - f2) * f);
        float f4 = this.mFromY;
        float f5 = (f4 == 1.0f && this.mToY == 1.0f) ? 1.0f : ((this.mToY - f4) * f) + f4;
        if (this.mPivotX == 0.0f && this.mPivotY == 0.0f) {
            transformation.getMatrix().setScale(f3, f5);
        } else {
            transformation.getMatrix().setScale(f3, f5, this.mPivotX * scaleFactor, scaleFactor * this.mPivotY);
        }
    }

    float resolveScale(float f, int i, int i2, int i3, int i4) {
        float complexToDimension;
        if (i == 6) {
            complexToDimension = TypedValue.complexToFraction(i2, i3, i4);
        } else {
            if (i != 5) {
                return f;
            }
            complexToDimension = TypedValue.complexToDimension(i2, this.mResources.getDisplayMetrics());
        }
        if (i3 == 0) {
            return 1.0f;
        }
        return complexToDimension / i3;
    }

    @Override // android.view.animation.Animation
    public void initialize(int i, int i2, int i3, int i4) {
        super.initialize(i, i2, i3, i4);
        this.mFromX = resolveScale(this.mFromX, this.mFromXType, this.mFromXData, i, i3);
        this.mToX = resolveScale(this.mToX, this.mToXType, this.mToXData, i, i3);
        this.mFromY = resolveScale(this.mFromY, this.mFromYType, this.mFromYData, i2, i4);
        this.mToY = resolveScale(this.mToY, this.mToYType, this.mToYData, i2, i4);
        this.mPivotX = resolveSize(this.mPivotXType, this.mPivotXValue, i, i3);
        this.mPivotY = resolveSize(this.mPivotYType, this.mPivotYValue, i2, i4);
    }
}
