package com.android.internal.graphics.drawable;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.util.AttributeSet;
import com.android.ims.ImsConfig;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class AnimationScaleListDrawable extends DrawableContainer implements Animatable {
    private static final String TAG = "AnimationScaleListDrawable";
    private AnimationScaleListState mAnimationScaleListState;
    private boolean mMutated;

    public AnimationScaleListDrawable() {
        this(null, null);
    }

    private AnimationScaleListDrawable(AnimationScaleListState animationScaleListState, Resources resources) {
        setConstantState(new AnimationScaleListState(animationScaleListState, this, resources));
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        return selectDrawable(this.mAnimationScaleListState.getCurrentDrawableIndexBasedOnScale()) || super.onStateChange(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.AnimationScaleListDrawable);
        updateDensity(resources);
        obtainAttributes.recycle();
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        onStateChange(getState());
    }

    private void inflateChildElements(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        AnimationScaleListState animationScaleListState = this.mAnimationScaleListState;
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 == 1) {
                return;
            }
            int depth2 = xmlPullParser.getDepth();
            if (depth2 < depth && next2 == 3) {
                return;
            }
            if (next2 == 2 && depth2 <= depth && xmlPullParser.getName().equals(ImsConfig.EXTRA_CHANGED_ITEM)) {
                TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.AnimationScaleListDrawableItem);
                Drawable drawable = obtainAttributes.getDrawable(0);
                obtainAttributes.recycle();
                if (drawable == null) {
                    do {
                        next = xmlPullParser.next();
                    } while (next == 4);
                    if (next != 2) {
                        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                    }
                    drawable = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
                }
                animationScaleListState.addDrawable(drawable);
            }
        }
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mAnimationScaleListState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        Object current = getCurrent();
        if (current == null || !(current instanceof Animatable)) {
            return;
        }
        ((Animatable) current).start();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        Object current = getCurrent();
        if (current == null || !(current instanceof Animatable)) {
            return;
        }
        ((Animatable) current).stop();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        Object current = getCurrent();
        if (current == null || !(current instanceof Animatable)) {
            return false;
        }
        return ((Animatable) current).isRunning();
    }

    static class AnimationScaleListState extends DrawableContainer.DrawableContainerState {
        int mAnimatableDrawableIndex;
        int mStaticDrawableIndex;
        int[] mThemeAttrs;

        AnimationScaleListState(AnimationScaleListState animationScaleListState, AnimationScaleListDrawable animationScaleListDrawable, Resources resources) {
            super(animationScaleListState, animationScaleListDrawable, resources);
            this.mThemeAttrs = null;
            this.mStaticDrawableIndex = -1;
            this.mAnimatableDrawableIndex = -1;
            if (animationScaleListState != null) {
                this.mThemeAttrs = animationScaleListState.mThemeAttrs;
                this.mStaticDrawableIndex = animationScaleListState.mStaticDrawableIndex;
                this.mAnimatableDrawableIndex = animationScaleListState.mAnimatableDrawableIndex;
            }
        }

        void mutate() {
            int[] iArr = this.mThemeAttrs;
            this.mThemeAttrs = iArr != null ? (int[]) iArr.clone() : null;
        }

        int addDrawable(Drawable drawable) {
            int addChild = addChild(drawable);
            if (drawable instanceof Animatable) {
                this.mAnimatableDrawableIndex = addChild;
                return addChild;
            }
            this.mStaticDrawableIndex = addChild;
            return addChild;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new AnimationScaleListDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new AnimationScaleListDrawable(this, resources);
        }

        @Override // android.graphics.drawable.DrawableContainer.DrawableContainerState, android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mThemeAttrs != null || super.canApplyTheme();
        }

        public int getCurrentDrawableIndexBasedOnScale() {
            if (ValueAnimator.getDurationScale() == 0.0f) {
                return this.mStaticDrawableIndex;
            }
            return this.mAnimatableDrawableIndex;
        }
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.DrawableContainer
    protected void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof AnimationScaleListState) {
            this.mAnimationScaleListState = (AnimationScaleListState) drawableContainerState;
        }
    }
}
