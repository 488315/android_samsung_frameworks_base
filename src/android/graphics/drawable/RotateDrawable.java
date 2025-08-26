package android.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.util.TypedValue;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class RotateDrawable extends DrawableWrapper {
    private static final int MAX_LEVEL = 10000;
    private RotateState mState;

    public RotateDrawable() {
        this(new RotateState(null, null), null);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.RotateDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(typedArrayObtainAttributes);
        verifyRequiredAttributes(typedArrayObtainAttributes);
        typedArrayObtainAttributes.recycle();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        RotateState rotateState = this.mState;
        if (rotateState == null || rotateState.mThemeAttrs == null) {
            return;
        }
        TypedArray typedArrayResolveAttributes = theme.resolveAttributes(rotateState.mThemeAttrs, R.styleable.RotateDrawable);
        try {
            updateStateFromTypedArray(typedArrayResolveAttributes);
            verifyRequiredAttributes(typedArrayResolveAttributes);
        } catch (XmlPullParserException e) {
            rethrowAsRuntimeException(e);
        } finally {
            typedArrayResolveAttributes.recycle();
        }
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        if (getDrawable() == null) {
            if (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[1] == 0) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + ": <rotate> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        RotateState rotateState = this.mState;
        if (rotateState == null) {
            return;
        }
        rotateState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        rotateState.mThemeAttrs = typedArray.extractThemeAttrs();
        if (typedArray.hasValue(4)) {
            TypedValue typedValuePeekValue = typedArray.peekValue(4);
            rotateState.mPivotXRel = typedValuePeekValue.type == 6;
            rotateState.mPivotX = rotateState.mPivotXRel ? typedValuePeekValue.getFraction(1.0f, 1.0f) : typedValuePeekValue.getFloat();
        }
        if (typedArray.hasValue(5)) {
            TypedValue typedValuePeekValue2 = typedArray.peekValue(5);
            rotateState.mPivotYRel = typedValuePeekValue2.type == 6;
            rotateState.mPivotY = rotateState.mPivotYRel ? typedValuePeekValue2.getFraction(1.0f, 1.0f) : typedValuePeekValue2.getFloat();
        }
        rotateState.mFromDegrees = typedArray.getFloat(2, rotateState.mFromDegrees);
        rotateState.mToDegrees = typedArray.getFloat(3, rotateState.mToDegrees);
        rotateState.mCurrentDegrees = rotateState.mFromDegrees;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Rect bounds = drawable.getBounds();
        int i = bounds.right - bounds.left;
        int i2 = bounds.bottom - bounds.top;
        RotateState rotateState = this.mState;
        float f = rotateState.mPivotXRel ? i * rotateState.mPivotX : rotateState.mPivotX;
        float f2 = rotateState.mPivotYRel ? i2 * rotateState.mPivotY : rotateState.mPivotY;
        int iSave = canvas.save();
        canvas.rotate(rotateState.mCurrentDegrees, f + bounds.left, f2 + bounds.top);
        drawable.draw(canvas);
        canvas.restoreToCount(iSave);
    }

    public void setFromDegrees(float f) {
        if (this.mState.mFromDegrees != f) {
            this.mState.mFromDegrees = f;
            invalidateSelf();
        }
    }

    public float getFromDegrees() {
        return this.mState.mFromDegrees;
    }

    public void setToDegrees(float f) {
        if (this.mState.mToDegrees != f) {
            this.mState.mToDegrees = f;
            invalidateSelf();
        }
    }

    public float getToDegrees() {
        return this.mState.mToDegrees;
    }

    public void setPivotX(float f) {
        if (this.mState.mPivotX != f) {
            this.mState.mPivotX = f;
            invalidateSelf();
        }
    }

    public float getPivotX() {
        return this.mState.mPivotX;
    }

    public void setPivotXRelative(boolean z) {
        if (this.mState.mPivotXRel != z) {
            this.mState.mPivotXRel = z;
            invalidateSelf();
        }
    }

    public boolean isPivotXRelative() {
        return this.mState.mPivotXRel;
    }

    public void setPivotY(float f) {
        if (this.mState.mPivotY != f) {
            this.mState.mPivotY = f;
            invalidateSelf();
        }
    }

    public float getPivotY() {
        return this.mState.mPivotY;
    }

    public void setPivotYRelative(boolean z) {
        if (this.mState.mPivotYRel != z) {
            this.mState.mPivotYRel = z;
            invalidateSelf();
        }
    }

    public boolean isPivotYRelative() {
        return this.mState.mPivotYRel;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        super.onLevelChange(i);
        this.mState.mCurrentDegrees = MathUtils.lerp(this.mState.mFromDegrees, this.mState.mToDegrees, i / 10000.0f);
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.DrawableWrapper
    DrawableWrapper.DrawableWrapperState mutateConstantState() {
        RotateState rotateState = new RotateState(this.mState, null);
        this.mState = rotateState;
        return rotateState;
    }

    static final class RotateState extends DrawableWrapper.DrawableWrapperState {
        float mCurrentDegrees;
        float mFromDegrees;
        float mPivotX;
        boolean mPivotXRel;
        float mPivotY;
        boolean mPivotYRel;
        private int[] mThemeAttrs;
        float mToDegrees;

        RotateState(RotateState rotateState, Resources resources) {
            super(rotateState, resources);
            this.mPivotXRel = true;
            this.mPivotX = 0.5f;
            this.mPivotYRel = true;
            this.mPivotY = 0.5f;
            this.mFromDegrees = 0.0f;
            this.mToDegrees = 360.0f;
            this.mCurrentDegrees = 0.0f;
            if (rotateState != null) {
                this.mPivotXRel = rotateState.mPivotXRel;
                this.mPivotX = rotateState.mPivotX;
                this.mPivotYRel = rotateState.mPivotYRel;
                this.mPivotY = rotateState.mPivotY;
                this.mFromDegrees = rotateState.mFromDegrees;
                this.mToDegrees = rotateState.mToDegrees;
                this.mCurrentDegrees = rotateState.mCurrentDegrees;
            }
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new RotateDrawable(this, resources);
        }
    }

    private RotateDrawable(RotateState rotateState, Resources resources) {
        super(rotateState, resources);
        this.mState = rotateState;
    }
}
