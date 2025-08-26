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
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ClipRectAnimation);
        Animation.Description value = Animation.Description.parseValue(typedArrayObtainStyledAttributes.peekValue(1), context);
        this.mFromLeftType = value.type;
        this.mFromLeftValue = value.value;
        Animation.Description value2 = Animation.Description.parseValue(typedArrayObtainStyledAttributes.peekValue(3), context);
        this.mFromTopType = value2.type;
        this.mFromTopValue = value2.value;
        Animation.Description value3 = Animation.Description.parseValue(typedArrayObtainStyledAttributes.peekValue(2), context);
        this.mFromRightType = value3.type;
        this.mFromRightValue = value3.value;
        Animation.Description value4 = Animation.Description.parseValue(typedArrayObtainStyledAttributes.peekValue(0), context);
        this.mFromBottomType = value4.type;
        this.mFromBottomValue = value4.value;
        Animation.Description value5 = Animation.Description.parseValue(typedArrayObtainStyledAttributes.peekValue(5), context);
        this.mToLeftType = value5.type;
        this.mToLeftValue = value5.value;
        Animation.Description value6 = Animation.Description.parseValue(typedArrayObtainStyledAttributes.peekValue(7), context);
        this.mToTopType = value6.type;
        this.mToTopValue = value6.value;
        Animation.Description value7 = Animation.Description.parseValue(typedArrayObtainStyledAttributes.peekValue(6), context);
        this.mToRightType = value7.type;
        this.mToRightValue = value7.value;
        Animation.Description value8 = Animation.Description.parseValue(typedArrayObtainStyledAttributes.peekValue(4), context);
        this.mToBottomType = value8.type;
        this.mToBottomValue = value8.value;
        typedArrayObtainStyledAttributes.recycle();
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
