package android.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.DrawableContainer;
import android.os.SystemClock;
import android.util.AttributeSet;
import com.android.ims.ImsConfig;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AnimationDrawable extends DrawableContainer implements Runnable, Animatable {
    private boolean mAnimating;
    private AnimationState mAnimationState;
    private int mCurFrame;
    private boolean mMutated;
    private boolean mRunning;

    public AnimationDrawable() {
        this(null, null);
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (!z) {
            unscheduleSelf(this);
            return visible;
        }
        if (!z2 && !visible) {
            return visible;
        }
        setFrame((z2 || !(this.mRunning || this.mAnimationState.mOneShot) || this.mCurFrame >= this.mAnimationState.getChildCount()) ? 0 : this.mCurFrame, true, this.mAnimating);
        return visible;
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        boolean z = true;
        this.mAnimating = true;
        if (isRunning()) {
            return;
        }
        if (this.mAnimationState.getChildCount() <= 1 && this.mAnimationState.mOneShot) {
            z = false;
        }
        setFrame(0, false, z);
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mAnimating = false;
        if (isRunning()) {
            this.mCurFrame = 0;
            unscheduleSelf(this);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mRunning;
    }

    @Override // java.lang.Runnable
    public void run() {
        nextFrame(false);
    }

    @Override // android.graphics.drawable.Drawable
    public void unscheduleSelf(Runnable runnable) {
        this.mRunning = false;
        super.unscheduleSelf(runnable);
    }

    public int getNumberOfFrames() {
        return this.mAnimationState.getChildCount();
    }

    public Drawable getFrame(int i) {
        return this.mAnimationState.getChild(i);
    }

    public int getDuration(int i) {
        return this.mAnimationState.mDurations[i];
    }

    public boolean isOneShot() {
        return this.mAnimationState.mOneShot;
    }

    public void setOneShot(boolean z) {
        this.mAnimationState.mOneShot = z;
    }

    public void addFrame(Drawable drawable, int i) {
        this.mAnimationState.addFrame(drawable, i);
        if (this.mRunning) {
            return;
        }
        setFrame(0, true, false);
    }

    private void nextFrame(boolean z) {
        int i = this.mCurFrame + 1;
        int childCount = this.mAnimationState.getChildCount();
        boolean z2 = this.mAnimationState.mOneShot && i >= childCount + (-1);
        if (!this.mAnimationState.mOneShot && i >= childCount) {
            i = 0;
        }
        setFrame(i, z, true ^ z2);
    }

    private void setFrame(int i, boolean z, boolean z2) {
        if (i >= this.mAnimationState.getChildCount()) {
            return;
        }
        this.mAnimating = z2;
        this.mCurFrame = i;
        selectDrawable(i);
        if (z || z2) {
            unscheduleSelf(this);
        }
        if (z2) {
            this.mCurFrame = i;
            this.mRunning = true;
            scheduleSelf(this, SystemClock.uptimeMillis() + this.mAnimationState.mDurations[i]);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.AnimationDrawable);
        super.inflateWithAttributes(resources, xmlPullParser, typedArrayObtainAttributes, 0);
        updateStateFromTypedArray(typedArrayObtainAttributes);
        updateDensity(resources);
        typedArrayObtainAttributes.recycle();
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        setFrame(0, true, false);
    }

    private void inflateChildElements(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
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
                TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.AnimationDrawableItem);
                int i = typedArrayObtainAttributes.getInt(0, -1);
                if (i < 0) {
                    throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'duration' attribute");
                }
                Drawable drawable = typedArrayObtainAttributes.getDrawable(1);
                typedArrayObtainAttributes.recycle();
                if (drawable == null) {
                    do {
                        next = xmlPullParser.next();
                    } while (next == 4);
                    if (next != 2) {
                        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                    }
                    drawable = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
                }
                this.mAnimationState.addFrame(drawable, i);
                if (drawable != null) {
                    drawable.setCallback(this);
                }
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        AnimationState animationState = this.mAnimationState;
        animationState.mVariablePadding = typedArray.getBoolean(1, animationState.mVariablePadding);
        AnimationState animationState2 = this.mAnimationState;
        animationState2.mOneShot = typedArray.getBoolean(2, animationState2.mOneShot);
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mAnimationState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.graphics.drawable.DrawableContainer
    public AnimationState cloneConstantState() {
        return new AnimationState(this.mAnimationState, this, null);
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    private static final class AnimationState extends DrawableContainer.DrawableContainerState {
        private int[] mDurations;
        private boolean mOneShot;

        AnimationState(AnimationState animationState, AnimationDrawable animationDrawable, Resources resources) {
            super(animationState, animationDrawable, resources);
            this.mOneShot = false;
            if (animationState != null) {
                this.mDurations = animationState.mDurations;
                this.mOneShot = animationState.mOneShot;
            } else {
                this.mDurations = new int[getCapacity()];
                this.mOneShot = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mutate() {
            this.mDurations = (int[]) this.mDurations.clone();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new AnimationDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new AnimationDrawable(this, resources);
        }

        public void addFrame(Drawable drawable, int i) {
            this.mDurations[super.addChild(drawable)] = i;
        }

        @Override // android.graphics.drawable.DrawableContainer.DrawableContainerState
        public void growArray(int i, int i2) {
            super.growArray(i, i2);
            int[] iArr = new int[i2];
            System.arraycopy(this.mDurations, 0, iArr, 0, i);
            this.mDurations = iArr;
        }

        public long getTotalDuration() {
            int[] iArr = this.mDurations;
            if (iArr == null) {
                return 0L;
            }
            int i = 0;
            for (int i2 : iArr) {
                i += i2;
            }
            return i;
        }
    }

    @Override // android.graphics.drawable.DrawableContainer
    protected void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof AnimationState) {
            this.mAnimationState = (AnimationState) drawableContainerState;
        }
    }

    public long getTotalDuration() {
        return this.mAnimationState.getTotalDuration();
    }

    private AnimationDrawable(AnimationState animationState, Resources resources) {
        this.mCurFrame = 0;
        setConstantState(new AnimationState(animationState, this, resources));
        if (animationState != null) {
            setFrame(0, true, false);
        }
    }
}
