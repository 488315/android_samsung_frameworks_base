package android.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.DrawableWrapper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.TypedValue;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AnimatedRotateDrawable extends DrawableWrapper implements Animatable {
    private float mCurrentDegrees;
    private float mIncrement;
    private final Runnable mNextFrame;
    private boolean mRunning;
    private AnimatedRotateState mState;

    public AnimatedRotateDrawable() {
        this(new AnimatedRotateState(null, null), null);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Rect bounds = drawable.getBounds();
        int i = bounds.right - bounds.left;
        int i2 = bounds.bottom - bounds.top;
        AnimatedRotateState animatedRotateState = this.mState;
        float f = animatedRotateState.mPivotXRel ? i * animatedRotateState.mPivotX : animatedRotateState.mPivotX;
        float f2 = animatedRotateState.mPivotYRel ? i2 * animatedRotateState.mPivotY : animatedRotateState.mPivotY;
        int save = canvas.save();
        canvas.rotate(this.mCurrentDegrees, f + bounds.left, f2 + bounds.top);
        drawable.draw(canvas);
        canvas.restoreToCount(save);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (this.mRunning) {
            return;
        }
        this.mRunning = true;
        nextFrame();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mRunning = false;
        unscheduleSelf(this.mNextFrame);
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mRunning;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nextFrame() {
        unscheduleSelf(this.mNextFrame);
        scheduleSelf(this.mNextFrame, SystemClock.uptimeMillis() + this.mState.mFrameDuration);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (!z) {
            unscheduleSelf(this.mNextFrame);
            return visible;
        }
        if (!visible && !z2) {
            return visible;
        }
        this.mCurrentDegrees = 0.0f;
        nextFrame();
        return visible;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedRotateDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(obtainAttributes);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
        updateLocalState();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        AnimatedRotateState animatedRotateState = this.mState;
        if (animatedRotateState == null) {
            return;
        }
        if (animatedRotateState.mThemeAttrs != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(animatedRotateState.mThemeAttrs, R.styleable.AnimatedRotateDrawable);
            try {
                try {
                    updateStateFromTypedArray(resolveAttributes);
                    verifyRequiredAttributes(resolveAttributes);
                } catch (XmlPullParserException e) {
                    rethrowAsRuntimeException(e);
                }
            } finally {
                resolveAttributes.recycle();
            }
        }
        updateLocalState();
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        if (getDrawable() == null) {
            if (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[1] == 0) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + ": <animated-rotate> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        AnimatedRotateState animatedRotateState = this.mState;
        if (animatedRotateState == null) {
            return;
        }
        animatedRotateState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        animatedRotateState.mThemeAttrs = typedArray.extractThemeAttrs();
        if (typedArray.hasValue(2)) {
            TypedValue peekValue = typedArray.peekValue(2);
            animatedRotateState.mPivotXRel = peekValue.type == 6;
            animatedRotateState.mPivotX = animatedRotateState.mPivotXRel ? peekValue.getFraction(1.0f, 1.0f) : peekValue.getFloat();
        }
        if (typedArray.hasValue(3)) {
            TypedValue peekValue2 = typedArray.peekValue(3);
            animatedRotateState.mPivotYRel = peekValue2.type == 6;
            animatedRotateState.mPivotY = animatedRotateState.mPivotYRel ? peekValue2.getFraction(1.0f, 1.0f) : peekValue2.getFloat();
        }
        setFramesCount(typedArray.getInt(5, animatedRotateState.mFramesCount));
        setFramesDuration(typedArray.getInt(4, animatedRotateState.mFrameDuration));
    }

    public void setFramesCount(int i) {
        this.mState.mFramesCount = i;
        this.mIncrement = 360.0f / this.mState.mFramesCount;
    }

    public void setFramesDuration(int i) {
        this.mState.mFrameDuration = i;
    }

    @Override // android.graphics.drawable.DrawableWrapper
    DrawableWrapper.DrawableWrapperState mutateConstantState() {
        AnimatedRotateState animatedRotateState = new AnimatedRotateState(this.mState, null);
        this.mState = animatedRotateState;
        return animatedRotateState;
    }

    static final class AnimatedRotateState extends DrawableWrapper.DrawableWrapperState {
        int mFrameDuration;
        int mFramesCount;
        float mPivotX;
        boolean mPivotXRel;
        float mPivotY;
        boolean mPivotYRel;
        private int[] mThemeAttrs;

        public AnimatedRotateState(AnimatedRotateState animatedRotateState, Resources resources) {
            super(animatedRotateState, resources);
            this.mPivotXRel = false;
            this.mPivotX = 0.0f;
            this.mPivotYRel = false;
            this.mPivotY = 0.0f;
            this.mFrameDuration = 150;
            this.mFramesCount = 12;
            if (animatedRotateState != null) {
                this.mPivotXRel = animatedRotateState.mPivotXRel;
                this.mPivotX = animatedRotateState.mPivotX;
                this.mPivotYRel = animatedRotateState.mPivotYRel;
                this.mPivotY = animatedRotateState.mPivotY;
                this.mFramesCount = animatedRotateState.mFramesCount;
                this.mFrameDuration = animatedRotateState.mFrameDuration;
            }
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new AnimatedRotateDrawable(this, resources);
        }
    }

    private AnimatedRotateDrawable(AnimatedRotateState animatedRotateState, Resources resources) {
        super(animatedRotateState, resources);
        this.mNextFrame = new Runnable() { // from class: android.graphics.drawable.AnimatedRotateDrawable.1
            @Override // java.lang.Runnable
            public void run() {
                AnimatedRotateDrawable.this.mCurrentDegrees += AnimatedRotateDrawable.this.mIncrement;
                if (AnimatedRotateDrawable.this.mCurrentDegrees > 360.0f - AnimatedRotateDrawable.this.mIncrement) {
                    AnimatedRotateDrawable.this.mCurrentDegrees = 0.0f;
                }
                AnimatedRotateDrawable.this.invalidateSelf();
                AnimatedRotateDrawable.this.nextFrame();
            }
        };
        this.mState = animatedRotateState;
        updateLocalState();
    }

    private void updateLocalState() {
        this.mIncrement = 360.0f / this.mState.mFramesCount;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.setFilterBitmap(true);
            if (drawable instanceof BitmapDrawable) {
                ((BitmapDrawable) drawable).setAntiAlias(true);
            }
        }
    }
}
