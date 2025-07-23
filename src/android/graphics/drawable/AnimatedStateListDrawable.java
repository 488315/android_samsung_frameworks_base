package android.graphics.drawable;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LongSparseLongArray;
import android.util.SparseIntArray;
import android.util.StateSet;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AnimatedStateListDrawable extends StateListDrawable {
    private static final String ELEMENT_ITEM = "item";
    private static final String ELEMENT_TRANSITION = "transition";
    private static final String LOGTAG = "AnimatedStateListDrawable";
    private boolean mMutated;
    private AnimatedStateListState mState;
    private Transition mTransition;
    private int mTransitionFromIndex;
    private int mTransitionToIndex;

    @Override // android.graphics.drawable.StateListDrawable, android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    public AnimatedStateListDrawable() {
        this(null, null);
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        Transition transition = this.mTransition;
        if (transition != null && (visible || z2)) {
            if (z) {
                transition.start();
                return visible;
            }
            jumpToCurrentState();
        }
        return visible;
    }

    public void addState(int[] iArr, Drawable drawable, int i) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable must not be null");
        }
        this.mState.addStateSet(iArr, drawable, i);
        onStateChange(getState());
    }

    public <T extends Drawable & Animatable> void addTransition(int i, int i2, T t, boolean z) {
        if (t == null) {
            throw new IllegalArgumentException("Transition drawable must not be null");
        }
        this.mState.addTransition(i, i2, t, z);
    }

    @Override // android.graphics.drawable.StateListDrawable, android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        int indexOfKeyframe = this.mState.indexOfKeyframe(iArr);
        boolean z = indexOfKeyframe != getCurrentIndex() && (selectTransition(indexOfKeyframe) || selectDrawable(indexOfKeyframe));
        Drawable current = getCurrent();
        return current != null ? current.setState(iArr) | z : z;
    }

    private boolean selectTransition(int i) {
        int currentIndex;
        int indexOfTransition;
        Transition animatableTransition;
        Transition transition = this.mTransition;
        if (transition != null) {
            if (i == this.mTransitionToIndex) {
                return true;
            }
            if (i == this.mTransitionFromIndex && transition.canReverse()) {
                transition.reverse();
                this.mTransitionToIndex = this.mTransitionFromIndex;
                this.mTransitionFromIndex = i;
                return true;
            }
            currentIndex = this.mTransitionToIndex;
            transition.stop();
        } else {
            currentIndex = getCurrentIndex();
        }
        this.mTransition = null;
        this.mTransitionFromIndex = -1;
        this.mTransitionToIndex = -1;
        AnimatedStateListState animatedStateListState = this.mState;
        int keyframeIdAt = animatedStateListState.getKeyframeIdAt(currentIndex);
        int keyframeIdAt2 = animatedStateListState.getKeyframeIdAt(i);
        if (keyframeIdAt2 == 0 || keyframeIdAt == 0 || (indexOfTransition = animatedStateListState.indexOfTransition(keyframeIdAt, keyframeIdAt2)) < 0) {
            return false;
        }
        boolean transitionHasReversibleFlag = animatedStateListState.transitionHasReversibleFlag(keyframeIdAt, keyframeIdAt2);
        selectDrawable(indexOfTransition);
        Object current = getCurrent();
        if (current instanceof AnimationDrawable) {
            animatableTransition = new AnimationDrawableTransition((AnimationDrawable) current, animatedStateListState.isTransitionReversed(keyframeIdAt, keyframeIdAt2), transitionHasReversibleFlag);
        } else if (current instanceof AnimatedVectorDrawable) {
            animatableTransition = new AnimatedVectorDrawableTransition((AnimatedVectorDrawable) current, animatedStateListState.isTransitionReversed(keyframeIdAt, keyframeIdAt2), transitionHasReversibleFlag);
        } else {
            if (current instanceof Animatable) {
                animatableTransition = new AnimatableTransition((Animatable) current);
            }
            return false;
        }
        animatableTransition.start();
        this.mTransition = animatableTransition;
        this.mTransitionFromIndex = currentIndex;
        this.mTransitionToIndex = i;
        return true;
    }

    private static abstract class Transition {
        public boolean canReverse() {
            return false;
        }

        public void reverse() {
        }

        public abstract void start();

        public abstract void stop();

        private Transition() {
        }
    }

    private static class AnimatableTransition extends Transition {
        private final Animatable mA;

        public AnimatableTransition(Animatable animatable) {
            super();
            this.mA = animatable;
        }

        @Override // android.graphics.drawable.AnimatedStateListDrawable.Transition
        public void start() {
            this.mA.start();
        }

        @Override // android.graphics.drawable.AnimatedStateListDrawable.Transition
        public void stop() {
            this.mA.stop();
        }
    }

    private static class AnimationDrawableTransition extends Transition {
        private final ObjectAnimator mAnim;
        private final boolean mHasReversibleFlag;

        public AnimationDrawableTransition(AnimationDrawable animationDrawable, boolean z, boolean z2) {
            super();
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            int i = z ? numberOfFrames - 1 : 0;
            int i2 = z ? 0 : numberOfFrames - 1;
            FrameInterpolator frameInterpolator = new FrameInterpolator(animationDrawable, z);
            ObjectAnimator ofInt = ObjectAnimator.ofInt(animationDrawable, "currentIndex", i, i2);
            ofInt.setAutoCancel(true);
            ofInt.setDuration(frameInterpolator.getTotalDuration());
            ofInt.setInterpolator(frameInterpolator);
            this.mHasReversibleFlag = z2;
            this.mAnim = ofInt;
        }

        @Override // android.graphics.drawable.AnimatedStateListDrawable.Transition
        public boolean canReverse() {
            return this.mHasReversibleFlag;
        }

        @Override // android.graphics.drawable.AnimatedStateListDrawable.Transition
        public void start() {
            this.mAnim.start();
        }

        @Override // android.graphics.drawable.AnimatedStateListDrawable.Transition
        public void reverse() {
            this.mAnim.reverse();
        }

        @Override // android.graphics.drawable.AnimatedStateListDrawable.Transition
        public void stop() {
            this.mAnim.cancel();
        }
    }

    private static class AnimatedVectorDrawableTransition extends Transition {
        private final AnimatedVectorDrawable mAvd;
        private final boolean mHasReversibleFlag;
        private final boolean mReversed;

        public AnimatedVectorDrawableTransition(AnimatedVectorDrawable animatedVectorDrawable, boolean z, boolean z2) {
            super();
            this.mAvd = animatedVectorDrawable;
            this.mReversed = z;
            this.mHasReversibleFlag = z2;
        }

        @Override // android.graphics.drawable.AnimatedStateListDrawable.Transition
        public boolean canReverse() {
            return this.mAvd.canReverse() && this.mHasReversibleFlag;
        }

        @Override // android.graphics.drawable.AnimatedStateListDrawable.Transition
        public void start() {
            if (this.mReversed) {
                reverse();
            } else {
                this.mAvd.start();
            }
        }

        @Override // android.graphics.drawable.AnimatedStateListDrawable.Transition
        public void reverse() {
            if (canReverse()) {
                this.mAvd.reverse();
            } else {
                Log.w(AnimatedStateListDrawable.LOGTAG, "Can't reverse, either the reversible is set to false, or the AnimatedVectorDrawable can't reverse");
            }
        }

        @Override // android.graphics.drawable.AnimatedStateListDrawable.Transition
        public void stop() {
            this.mAvd.stop();
        }
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        Transition transition = this.mTransition;
        if (transition != null) {
            transition.stop();
            this.mTransition = null;
            selectDrawable(this.mTransitionToIndex);
            this.mTransitionToIndex = -1;
            this.mTransitionFromIndex = -1;
        }
    }

    @Override // android.graphics.drawable.StateListDrawable, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawable);
        super.inflateWithAttributes(resources, xmlPullParser, obtainAttributes, 1);
        updateStateFromTypedArray(obtainAttributes);
        updateDensity(resources);
        obtainAttributes.recycle();
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        init();
    }

    @Override // android.graphics.drawable.StateListDrawable, android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        AnimatedStateListState animatedStateListState = this.mState;
        if (animatedStateListState == null || animatedStateListState.mAnimThemeAttrs == null) {
            return;
        }
        TypedArray resolveAttributes = theme.resolveAttributes(animatedStateListState.mAnimThemeAttrs, R.styleable.AnimatedRotateDrawable);
        updateStateFromTypedArray(resolveAttributes);
        resolveAttributes.recycle();
        init();
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        AnimatedStateListState animatedStateListState = this.mState;
        animatedStateListState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        animatedStateListState.mAnimThemeAttrs = typedArray.extractThemeAttrs();
        animatedStateListState.setVariablePadding(typedArray.getBoolean(2, animatedStateListState.mVariablePadding));
        animatedStateListState.setConstantSize(typedArray.getBoolean(3, animatedStateListState.mConstantSize));
        animatedStateListState.setEnterFadeDuration(typedArray.getInt(4, animatedStateListState.mEnterFadeDuration));
        animatedStateListState.setExitFadeDuration(typedArray.getInt(5, animatedStateListState.mExitFadeDuration));
        setDither(typedArray.getBoolean(0, animatedStateListState.mDither));
        setAutoMirrored(typedArray.getBoolean(6, animatedStateListState.mAutoMirrored));
    }

    private void init() {
        onStateChange(getState());
    }

    private void inflateChildElements(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            int depth2 = xmlPullParser.getDepth();
            if (depth2 < depth && next == 3) {
                return;
            }
            if (next == 2 && depth2 <= depth) {
                if (xmlPullParser.getName().equals("item")) {
                    parseItem(resources, xmlPullParser, attributeSet, theme);
                } else if (xmlPullParser.getName().equals("transition")) {
                    parseTransition(resources, xmlPullParser, attributeSet, theme);
                }
            }
        }
    }

    private int parseTransition(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawableTransition);
        int resourceId = obtainAttributes.getResourceId(2, 0);
        int resourceId2 = obtainAttributes.getResourceId(1, 0);
        boolean z = obtainAttributes.getBoolean(3, false);
        Drawable drawable = obtainAttributes.getDrawable(0);
        obtainAttributes.recycle();
        if (drawable == null) {
            do {
                next = xmlPullParser.next();
            } while (next == 4);
            if (next != 2) {
                throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
            drawable = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
        }
        return this.mState.addTransition(resourceId, resourceId2, drawable, z);
    }

    private int parseItem(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawableItem);
        int resourceId = obtainAttributes.getResourceId(0, 0);
        Drawable drawable = obtainAttributes.getDrawable(1);
        obtainAttributes.recycle();
        int[] extractStateSet = extractStateSet(attributeSet);
        if (drawable == null) {
            do {
                next = xmlPullParser.next();
            } while (next == 4);
            if (next != 2) {
                throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
            drawable = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
        }
        return this.mState.addStateSet(extractStateSet, drawable, resourceId);
    }

    @Override // android.graphics.drawable.StateListDrawable, android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.graphics.drawable.StateListDrawable, android.graphics.drawable.DrawableContainer
    public AnimatedStateListState cloneConstantState() {
        return new AnimatedStateListState(this.mState, this, null);
    }

    @Override // android.graphics.drawable.StateListDrawable, android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    static class AnimatedStateListState extends StateListDrawable.StateListState {
        private static final long REVERSED_BIT = 4294967296L;
        private static final long REVERSIBLE_FLAG_BIT = 8589934592L;
        int[] mAnimThemeAttrs;
        SparseIntArray mStateIds;
        LongSparseLongArray mTransitions;

        private static long generateTransitionKey(int i, int i2) {
            return i2 | (i << 32);
        }

        AnimatedStateListState(AnimatedStateListState animatedStateListState, AnimatedStateListDrawable animatedStateListDrawable, Resources resources) {
            super(animatedStateListState, animatedStateListDrawable, resources);
            if (animatedStateListState != null) {
                this.mAnimThemeAttrs = animatedStateListState.mAnimThemeAttrs;
                this.mTransitions = animatedStateListState.mTransitions;
                this.mStateIds = animatedStateListState.mStateIds;
            } else {
                this.mTransitions = new LongSparseLongArray();
                this.mStateIds = new SparseIntArray();
            }
        }

        @Override // android.graphics.drawable.StateListDrawable.StateListState
        void mutate() {
            this.mTransitions = this.mTransitions.m5520clone();
            this.mStateIds = this.mStateIds.m5532clone();
        }

        int addTransition(int i, int i2, Drawable drawable, boolean z) {
            int addChild = super.addChild(drawable);
            long generateTransitionKey = generateTransitionKey(i, i2);
            long j = z ? 8589934592L : 0L;
            long j2 = addChild;
            this.mTransitions.append(generateTransitionKey, j2 | j);
            if (z) {
                this.mTransitions.append(generateTransitionKey(i2, i), 4294967296L | j2 | j);
            }
            return addChild;
        }

        int addStateSet(int[] iArr, Drawable drawable, int i) {
            int addStateSet = super.addStateSet(iArr, drawable);
            this.mStateIds.put(addStateSet, i);
            return addStateSet;
        }

        int indexOfKeyframe(int[] iArr) {
            int indexOfStateSet = super.indexOfStateSet(iArr);
            return indexOfStateSet >= 0 ? indexOfStateSet : super.indexOfStateSet(StateSet.WILD_CARD);
        }

        int getKeyframeIdAt(int i) {
            if (i < 0) {
                return 0;
            }
            return this.mStateIds.get(i, 0);
        }

        int indexOfTransition(int i, int i2) {
            return (int) this.mTransitions.get(generateTransitionKey(i, i2), -1L);
        }

        boolean isTransitionReversed(int i, int i2) {
            return (this.mTransitions.get(generateTransitionKey(i, i2), -1L) & 4294967296L) != 0;
        }

        boolean transitionHasReversibleFlag(int i, int i2) {
            return (this.mTransitions.get(generateTransitionKey(i, i2), -1L) & 8589934592L) != 0;
        }

        @Override // android.graphics.drawable.StateListDrawable.StateListState, android.graphics.drawable.DrawableContainer.DrawableContainerState, android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mAnimThemeAttrs != null || super.canApplyTheme();
        }

        @Override // android.graphics.drawable.StateListDrawable.StateListState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new AnimatedStateListDrawable(this, null);
        }

        @Override // android.graphics.drawable.StateListDrawable.StateListState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new AnimatedStateListDrawable(this, resources);
        }
    }

    @Override // android.graphics.drawable.StateListDrawable, android.graphics.drawable.DrawableContainer
    protected void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof AnimatedStateListState) {
            this.mState = (AnimatedStateListState) drawableContainerState;
        }
    }

    private AnimatedStateListDrawable(AnimatedStateListState animatedStateListState, Resources resources) {
        super(null);
        this.mTransitionToIndex = -1;
        this.mTransitionFromIndex = -1;
        setConstantState(new AnimatedStateListState(animatedStateListState, this, resources));
        onStateChange(getState());
        jumpToCurrentState();
    }

    private static class FrameInterpolator implements TimeInterpolator {
        private int[] mFrameTimes;
        private int mFrames;
        private int mTotalDuration;

        public FrameInterpolator(AnimationDrawable animationDrawable, boolean z) {
            updateFrames(animationDrawable, z);
        }

        public int updateFrames(AnimationDrawable animationDrawable, boolean z) {
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            this.mFrames = numberOfFrames;
            int[] iArr = this.mFrameTimes;
            if (iArr == null || iArr.length < numberOfFrames) {
                this.mFrameTimes = new int[numberOfFrames];
            }
            int[] iArr2 = this.mFrameTimes;
            int i = 0;
            for (int i2 = 0; i2 < numberOfFrames; i2++) {
                int duration = animationDrawable.getDuration(z ? (numberOfFrames - i2) - 1 : i2);
                iArr2[i2] = duration;
                i += duration;
            }
            this.mTotalDuration = i;
            return i;
        }

        public int getTotalDuration() {
            return this.mTotalDuration;
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            int i = (int) ((f * this.mTotalDuration) + 0.5f);
            int i2 = this.mFrames;
            int[] iArr = this.mFrameTimes;
            int i3 = 0;
            while (i3 < i2) {
                int i4 = iArr[i3];
                if (i < i4) {
                    break;
                }
                i -= i4;
                i3++;
            }
            return (i3 / i2) + (i3 < i2 ? i / this.mTotalDuration : 0.0f);
        }
    }
}
