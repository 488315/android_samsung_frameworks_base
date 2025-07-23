package android.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.view.animation.Animation;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class ExtendAnimation extends Animation {
    private int mFromBottomType;
    private float mFromBottomValue;
    protected Insets mFromInsets;
    private int mFromLeftType;
    private float mFromLeftValue;
    private int mFromRightType;
    private float mFromRightValue;
    private int mFromTopType;
    private float mFromTopValue;
    private int mToBottomType;
    private float mToBottomValue;
    protected Insets mToInsets;
    private int mToLeftType;
    private float mToLeftValue;
    private int mToRightType;
    private float mToRightValue;
    private int mToTopType;
    private float mToTopValue;

    @Override // android.view.animation.Animation
    public boolean willChangeTransformationMatrix() {
        return false;
    }

    public ExtendAnimation(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFromInsets = Insets.NONE;
        this.mToInsets = Insets.NONE;
        this.mFromLeftType = 0;
        this.mFromTopType = 0;
        this.mFromRightType = 0;
        this.mFromBottomType = 0;
        this.mToLeftType = 0;
        this.mToTopType = 0;
        this.mToRightType = 0;
        this.mToBottomType = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ExtendAnimation);
        Animation.Description parseValue = Animation.Description.parseValue(obtainStyledAttributes.peekValue(0), context);
        this.mFromLeftType = parseValue.type;
        this.mFromLeftValue = parseValue.value;
        Animation.Description parseValue2 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(1), context);
        this.mFromTopType = parseValue2.type;
        this.mFromTopValue = parseValue2.value;
        Animation.Description parseValue3 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(2), context);
        this.mFromRightType = parseValue3.type;
        this.mFromRightValue = parseValue3.value;
        Animation.Description parseValue4 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(3), context);
        this.mFromBottomType = parseValue4.type;
        this.mFromBottomValue = parseValue4.value;
        Animation.Description parseValue5 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(4), context);
        this.mToLeftType = parseValue5.type;
        this.mToLeftValue = parseValue5.value;
        Animation.Description parseValue6 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(5), context);
        this.mToTopType = parseValue6.type;
        this.mToTopValue = parseValue6.value;
        Animation.Description parseValue7 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(6), context);
        this.mToRightType = parseValue7.type;
        this.mToRightValue = parseValue7.value;
        Animation.Description parseValue8 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(7), context);
        this.mToBottomType = parseValue8.type;
        this.mToBottomValue = parseValue8.value;
        obtainStyledAttributes.recycle();
    }

    public ExtendAnimation(Insets insets, Insets insets2) {
        this.mFromInsets = Insets.NONE;
        this.mToInsets = Insets.NONE;
        this.mFromLeftType = 0;
        this.mFromTopType = 0;
        this.mFromRightType = 0;
        this.mFromBottomType = 0;
        this.mToLeftType = 0;
        this.mToTopType = 0;
        this.mToRightType = 0;
        this.mToBottomType = 0;
        if (insets == null || insets2 == null) {
            throw new RuntimeException("Expected non-null animation outsets");
        }
        this.mFromLeftValue = -insets.left;
        this.mFromTopValue = -insets.top;
        this.mFromRightValue = -insets.right;
        this.mFromBottomValue = -insets.bottom;
        this.mToLeftValue = -insets2.left;
        this.mToTopValue = -insets2.top;
        this.mToRightValue = -insets2.right;
        this.mToBottomValue = -insets2.bottom;
    }

    public ExtendAnimation(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this(Insets.of(-i, -i2, -i3, -i4), Insets.of(-i5, -i6, -i7, -i8));
    }

    @Override // android.view.animation.Animation
    protected void applyTransformation(float f, Transformation transformation) {
        transformation.setInsets(this.mFromInsets.left + ((int) ((this.mToInsets.left - this.mFromInsets.left) * f)), this.mFromInsets.top + ((int) ((this.mToInsets.top - this.mFromInsets.top) * f)), this.mFromInsets.right + ((int) ((this.mToInsets.right - this.mFromInsets.right) * f)), this.mFromInsets.bottom + ((int) ((this.mToInsets.bottom - this.mFromInsets.bottom) * f)));
    }

    @Override // android.view.animation.Animation
    public int getExtensionEdges() {
        return ((this.mFromInsets.left < 0 || this.mToInsets.left < 0) ? 1 : 0) | ((this.mFromInsets.right < 0 || this.mToInsets.right < 0) ? 4 : 0) | ((this.mFromInsets.top < 0 || this.mToInsets.top < 0) ? 2 : 0) | ((this.mFromInsets.bottom < 0 || this.mToInsets.bottom < 0) ? 8 : 0);
    }

    @Override // android.view.animation.Animation
    public void initialize(int i, int i2, int i3, int i4) {
        super.initialize(i, i2, i3, i4);
        this.mFromInsets = Insets.min(Insets.of(-((int) resolveSize(this.mFromLeftType, this.mFromLeftValue, i, i3)), -((int) resolveSize(this.mFromTopType, this.mFromTopValue, i2, i4)), -((int) resolveSize(this.mFromRightType, this.mFromRightValue, i, i3)), -((int) resolveSize(this.mFromBottomType, this.mFromBottomValue, i2, i4))), Insets.NONE);
        this.mToInsets = Insets.min(Insets.of(-((int) resolveSize(this.mToLeftType, this.mToLeftValue, i, i3)), -((int) resolveSize(this.mToTopType, this.mToTopValue, i2, i4)), -((int) resolveSize(this.mToRightType, this.mToRightValue, i, i3)), -((int) resolveSize(this.mToBottomType, this.mToBottomValue, i2, i4))), Insets.NONE);
    }
}
