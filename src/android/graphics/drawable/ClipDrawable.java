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
public class ClipDrawable extends DrawableWrapper {
    public static final int HORIZONTAL = 1;
    private static final int MAX_LEVEL = 10000;
    public static final int VERTICAL = 2;
    private ClipState mState;
    private final Rect mTmpRect;

    ClipDrawable() {
        this(new ClipState(null, null), null);
    }

    public ClipDrawable(Drawable drawable, int i, int i2) {
        this(new ClipState(null, null), null);
        this.mState.mGravity = i;
        this.mState.mOrientation = i2;
        setDrawable(drawable);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.ClipDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(obtainAttributes);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        ClipState clipState = this.mState;
        if (clipState == null || clipState.mThemeAttrs == null) {
            return;
        }
        TypedArray resolveAttributes = theme.resolveAttributes(clipState.mThemeAttrs, R.styleable.ClipDrawable);
        try {
            updateStateFromTypedArray(resolveAttributes);
            verifyRequiredAttributes(resolveAttributes);
        } catch (XmlPullParserException e) {
            rethrowAsRuntimeException(e);
        } finally {
            resolveAttributes.recycle();
        }
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        if (getDrawable() == null) {
            if (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[1] == 0) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + ": <clip> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        ClipState clipState = this.mState;
        if (clipState == null) {
            return;
        }
        clipState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        clipState.mThemeAttrs = typedArray.extractThemeAttrs();
        clipState.mOrientation = typedArray.getInt(2, clipState.mOrientation);
        clipState.mGravity = typedArray.getInt(0, clipState.mGravity);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        super.onLevelChange(i);
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = getDrawable();
        if (drawable.getOpacity() == -2 || drawable.getLevel() == 0) {
            return -2;
        }
        if (getLevel() >= 10000) {
            return drawable.getOpacity();
        }
        return -3;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable.getLevel() == 0) {
            return;
        }
        Rect rect = this.mTmpRect;
        Rect bounds = getBounds();
        int level = getLevel();
        int width = bounds.width();
        if ((this.mState.mOrientation & 1) != 0) {
            width -= ((10000 - level) * width) / 10000;
        }
        int i = width;
        int height = bounds.height();
        if ((this.mState.mOrientation & 2) != 0) {
            height -= ((10000 - level) * height) / 10000;
        }
        int i2 = height;
        Gravity.apply(this.mState.mGravity, i, i2, bounds, rect, getLayoutDirection());
        if (i <= 0 || i2 <= 0) {
            return;
        }
        canvas.save();
        canvas.clipRect(rect);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.graphics.drawable.DrawableWrapper
    DrawableWrapper.DrawableWrapperState mutateConstantState() {
        ClipState clipState = new ClipState(this.mState, null);
        this.mState = clipState;
        return clipState;
    }

    static final class ClipState extends DrawableWrapper.DrawableWrapperState {
        int mGravity;
        int mOrientation;
        private int[] mThemeAttrs;

        ClipState(ClipState clipState, Resources resources) {
            super(clipState, resources);
            this.mOrientation = 1;
            this.mGravity = 3;
            if (clipState != null) {
                this.mOrientation = clipState.mOrientation;
                this.mGravity = clipState.mGravity;
            }
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new ClipDrawable(this, resources);
        }
    }

    private ClipDrawable(ClipState clipState, Resources resources) {
        super(clipState, resources);
        this.mTmpRect = new Rect();
        this.mState = clipState;
    }
}
