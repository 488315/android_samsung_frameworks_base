package android.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.view.Gravity;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ScaleDrawable extends DrawableWrapper {
    private static final int MAX_LEVEL = 10000;
    private ScaleState mState;
    private final Rect mTmpRect;

    ScaleDrawable() {
        this(new ScaleState(null, null), null);
    }

    public ScaleDrawable(Drawable drawable, int i, float f, float f2) {
        this(new ScaleState(null, null), null);
        this.mState.mGravity = i;
        this.mState.mScaleWidth = f;
        this.mState.mScaleHeight = f2;
        setDrawable(drawable);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.ScaleDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(obtainAttributes);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
        updateLocalState();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        ScaleState scaleState = this.mState;
        if (scaleState == null) {
            return;
        }
        if (scaleState.mThemeAttrs != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(scaleState.mThemeAttrs, R.styleable.ScaleDrawable);
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
            if (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[0] == 0) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + ": <scale> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        ScaleState scaleState = this.mState;
        if (scaleState == null) {
            return;
        }
        scaleState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        scaleState.mThemeAttrs = typedArray.extractThemeAttrs();
        scaleState.mScaleWidth = getPercent(typedArray, 1, scaleState.mScaleWidth);
        scaleState.mScaleHeight = getPercent(typedArray, 2, scaleState.mScaleHeight);
        scaleState.mGravity = typedArray.getInt(3, scaleState.mGravity);
        scaleState.mUseIntrinsicSizeAsMin = typedArray.getBoolean(4, scaleState.mUseIntrinsicSizeAsMin);
        scaleState.mInitialLevel = typedArray.getInt(5, scaleState.mInitialLevel);
    }

    private static float getPercent(TypedArray typedArray, int i, float f) {
        int type = typedArray.getType(i);
        if (type == 6 || type == 0) {
            return typedArray.getFraction(i, 1, 1, f);
        }
        String string = typedArray.getString(i);
        return (string == null || !string.endsWith("%")) ? f : Float.parseFloat(string.substring(0, string.length() - 1)) / 100.0f;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getLevel() == 0) {
            return;
        }
        drawable.draw(canvas);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = getDrawable();
        if (drawable.getLevel() == 0) {
            return -2;
        }
        int opacity = drawable.getOpacity();
        if (opacity != -1 || drawable.getLevel() >= 10000) {
            return opacity;
        }
        return -3;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        super.onLevelChange(i);
        onBoundsChange(getBounds());
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        Drawable drawable = getDrawable();
        Rect rect2 = this.mTmpRect;
        boolean z = this.mState.mUseIntrinsicSizeAsMin;
        int level = getLevel();
        int width = rect.width();
        if (this.mState.mScaleWidth > 0.0f) {
            width -= (int) ((((width - (z ? drawable.getIntrinsicWidth() : 0)) * (10000 - level)) * this.mState.mScaleWidth) / 10000.0f);
        }
        int height = rect.height();
        if (this.mState.mScaleHeight > 0.0f) {
            height -= (int) ((((height - (z ? drawable.getIntrinsicHeight() : 0)) * (10000 - level)) * this.mState.mScaleHeight) / 10000.0f);
        }
        int i = width;
        int i2 = height;
        Gravity.apply(this.mState.mGravity, i, i2, rect, rect2, getLayoutDirection());
        if (i <= 0 || i2 <= 0) {
            return;
        }
        drawable.setBounds(rect2.left, rect2.top, rect2.right, rect2.bottom);
    }

    @Override // android.graphics.drawable.DrawableWrapper
    DrawableWrapper.DrawableWrapperState mutateConstantState() {
        ScaleState scaleState = new ScaleState(this.mState, null);
        this.mState = scaleState;
        return scaleState;
    }

    static final class ScaleState extends DrawableWrapper.DrawableWrapperState {
        private static final float DO_NOT_SCALE = -1.0f;
        int mGravity;
        int mInitialLevel;
        float mScaleHeight;
        float mScaleWidth;
        private int[] mThemeAttrs;
        boolean mUseIntrinsicSizeAsMin;

        ScaleState(ScaleState scaleState, Resources resources) {
            super(scaleState, resources);
            this.mScaleWidth = -1.0f;
            this.mScaleHeight = -1.0f;
            this.mGravity = 3;
            this.mUseIntrinsicSizeAsMin = false;
            this.mInitialLevel = 0;
            if (scaleState != null) {
                this.mScaleWidth = scaleState.mScaleWidth;
                this.mScaleHeight = scaleState.mScaleHeight;
                this.mGravity = scaleState.mGravity;
                this.mUseIntrinsicSizeAsMin = scaleState.mUseIntrinsicSizeAsMin;
                this.mInitialLevel = scaleState.mInitialLevel;
            }
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new ScaleDrawable(this, resources);
        }
    }

    private ScaleDrawable(ScaleState scaleState, Resources resources) {
        super(scaleState, resources);
        this.mTmpRect = new Rect();
        this.mState = scaleState;
        updateLocalState();
    }

    private void updateLocalState() {
        setLevel(this.mState.mInitialLevel);
    }
}
