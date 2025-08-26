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
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ScaleAnimation);
        TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(2);
        this.mFromX = 0.0f;
        if (typedValuePeekValue != null) {
            if (typedValuePeekValue.type == 4) {
                this.mFromX = typedValuePeekValue.getFloat();
            } else {
                this.mFromXType = typedValuePeekValue.type;
                this.mFromXData = typedValuePeekValue.data;
            }
        }
        TypedValue typedValuePeekValue2 = typedArrayObtainStyledAttributes.peekValue(3);
        this.mToX = 0.0f;
        if (typedValuePeekValue2 != null) {
            if (typedValuePeekValue2.type == 4) {
                this.mToX = typedValuePeekValue2.getFloat();
            } else {
                this.mToXType = typedValuePeekValue2.type;
                this.mToXData = typedValuePeekValue2.data;
            }
        }
        TypedValue typedValuePeekValue3 = typedArrayObtainStyledAttributes.peekValue(4);
        this.mFromY = 0.0f;
        if (typedValuePeekValue3 != null) {
            if (typedValuePeekValue3.type == 4) {
                this.mFromY = typedValuePeekValue3.getFloat();
            } else {
                this.mFromYType = typedValuePeekValue3.type;
                this.mFromYData = typedValuePeekValue3.data;
            }
        }
        TypedValue typedValuePeekValue4 = typedArrayObtainStyledAttributes.peekValue(5);
        this.mToY = 0.0f;
        if (typedValuePeekValue4 != null) {
            if (typedValuePeekValue4.type == 4) {
                this.mToY = typedValuePeekValue4.getFloat();
            } else {
                this.mToYType = typedValuePeekValue4.type;
                this.mToYData = typedValuePeekValue4.data;
            }
        }
        Animation.Description value = Animation.Description.parseValue(typedArrayObtainStyledAttributes.peekValue(0), context);
        this.mPivotXType = value.type;
        this.mPivotXValue = value.value;
        Animation.Description value2 = Animation.Description.parseValue(typedArrayObtainStyledAttributes.peekValue(1), context);
        this.mPivotYType = value2.type;
        this.mPivotYValue = value2.value;
        typedArrayObtainStyledAttributes.recycle();
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
        float fComplexToDimension;
        if (i == 6) {
            fComplexToDimension = TypedValue.complexToFraction(i2, i3, i4);
        } else {
            if (i != 5) {
                return f;
            }
            fComplexToDimension = TypedValue.complexToDimension(i2, this.mResources.getDisplayMetrics());
        }
        if (i3 == 0) {
            return 1.0f;
        }
        return fComplexToDimension / i3;
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
