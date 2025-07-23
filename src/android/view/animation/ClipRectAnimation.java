package android.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.Animation;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class ClipRectAnimation extends Animation {
    private int mFromBottomType;
    private float mFromBottomValue;
    private int mFromLeftType;
    private float mFromLeftValue;
    protected final Rect mFromRect;
    private int mFromRightType;
    private float mFromRightValue;
    private int mFromTopType;
    private float mFromTopValue;
    private int mToBottomType;
    private float mToBottomValue;
    private int mToLeftType;
    private float mToLeftValue;
    protected final Rect mToRect;
    private int mToRightType;
    private float mToRightValue;
    private int mToTopType;
    private float mToTopValue;

    @Override // android.view.animation.Animation
    public boolean willChangeTransformationMatrix() {
        return false;
    }

    public ClipRectAnimation(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFromRect = new Rect();
        this.mToRect = new Rect();
        this.mFromLeftType = 0;
        this.mFromTopType = 0;
        this.mFromRightType = 0;
        this.mFromBottomType = 0;
        this.mToLeftType = 0;
        this.mToTopType = 0;
        this.mToRightType = 0;
        this.mToBottomType = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ClipRectAnimation);
        Animation.Description parseValue = Animation.Description.parseValue(obtainStyledAttributes.peekValue(1), context);
        this.mFromLeftType = parseValue.type;
        this.mFromLeftValue = parseValue.value;
        Animation.Description parseValue2 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(3), context);
        this.mFromTopType = parseValue2.type;
        this.mFromTopValue = parseValue2.value;
        Animation.Description parseValue3 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(2), context);
        this.mFromRightType = parseValue3.type;
        this.mFromRightValue = parseValue3.value;
        Animation.Description parseValue4 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(0), context);
        this.mFromBottomType = parseValue4.type;
        this.mFromBottomValue = parseValue4.value;
        Animation.Description parseValue5 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(5), context);
        this.mToLeftType = parseValue5.type;
        this.mToLeftValue = parseValue5.value;
        Animation.Description parseValue6 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(7), context);
        this.mToTopType = parseValue6.type;
        this.mToTopValue = parseValue6.value;
        Animation.Description parseValue7 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(6), context);
        this.mToRightType = parseValue7.type;
        this.mToRightValue = parseValue7.value;
        Animation.Description parseValue8 = Animation.Description.parseValue(obtainStyledAttributes.peekValue(4), context);
        this.mToBottomType = parseValue8.type;
        this.mToBottomValue = parseValue8.value;
        obtainStyledAttributes.recycle();
    }

    public ClipRectAnimation(Rect rect, Rect rect2) {
        this.mFromRect = new Rect();
        this.mToRect = new Rect();
        this.mFromLeftType = 0;
        this.mFromTopType = 0;
        this.mFromRightType = 0;
        this.mFromBottomType = 0;
        this.mToLeftType = 0;
        this.mToTopType = 0;
        this.mToRightType = 0;
        this.mToBottomType = 0;
        if (rect == null || rect2 == null) {
            throw new RuntimeException("Expected non-null animation clip rects");
        }
        this.mFromLeftValue = rect.left;
        this.mFromTopValue = rect.top;
        this.mFromRightValue = rect.right;
        this.mFromBottomValue = rect.bottom;
        this.mToLeftValue = rect2.left;
        this.mToTopValue = rect2.top;
        this.mToRightValue = rect2.right;
        this.mToBottomValue = rect2.bottom;
    }

    public ClipRectAnimation(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this(new Rect(i, i2, i3, i4), new Rect(i5, i6, i7, i8));
    }

    @Override // android.view.animation.Animation
    protected void applyTransformation(float f, Transformation transformation) {
        transformation.setClipRect(this.mFromRect.left + ((int) ((this.mToRect.left - this.mFromRect.left) * f)), this.mFromRect.top + ((int) ((this.mToRect.top - this.mFromRect.top) * f)), this.mFromRect.right + ((int) ((this.mToRect.right - this.mFromRect.right) * f)), this.mFromRect.bottom + ((int) ((this.mToRect.bottom - this.mFromRect.bottom) * f)));
    }

    @Override // android.view.animation.Animation
    public void initialize(int i, int i2, int i3, int i4) {
        super.initialize(i, i2, i3, i4);
        this.mFromRect.set((int) resolveSize(this.mFromLeftType, this.mFromLeftValue, i, i3), (int) resolveSize(this.mFromTopType, this.mFromTopValue, i2, i4), (int) resolveSize(this.mFromRightType, this.mFromRightValue, i, i3), (int) resolveSize(this.mFromBottomType, this.mFromBottomValue, i2, i4));
        this.mToRect.set((int) resolveSize(this.mToLeftType, this.mToLeftValue, i, i3), (int) resolveSize(this.mToTopType, this.mToTopValue, i2, i4), (int) resolveSize(this.mToRightType, this.mToRightValue, i, i3), (int) resolveSize(this.mToBottomType, this.mToBottomValue, i2, i4));
    }
}
