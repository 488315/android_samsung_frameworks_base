package android.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Compatibility;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.animation.NativeInterpolatorFactory;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.Log;
import android.util.LongArray;
import android.util.PathParser;
import android.util.Property;
import android.view.Choreographer;
import android.view.NativeVectorDrawableAnimator;
import com.android.internal.R;
import com.android.internal.util.VirtualRefBasePtr;
import dalvik.annotation.optimization.FastNative;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AnimatedVectorDrawable extends Drawable implements Animatable2 {
    private static final String ANIMATED_VECTOR = "animated-vector";
    private static final boolean DBG_ANIMATION_VECTOR_DRAWABLE = false;
    private static final String LOGTAG = "AnimatedVectorDrawable";
    private static final String TARGET = "target";
    private AnimatedVectorDrawableState mAnimatedVectorState;
    private ArrayList<Animatable2.AnimationCallback> mAnimationCallbacks;
    private Animator.AnimatorListener mAnimatorListener;
    private VectorDrawableAnimator mAnimatorSet;
    private AnimatorSet mAnimatorSetFromXml;
    private final Drawable.Callback mCallback;
    private boolean mMutated;
    private Resources mRes;

    private interface VectorDrawableAnimator {
        boolean canReverse();

        void end();

        long getTotalDuration();

        void init(AnimatorSet animatorSet);

        boolean isInfinite();

        boolean isRunning();

        boolean isStarted();

        void onDraw(Canvas canvas);

        void pause();

        void removeListener(Animator.AnimatorListener animatorListener);

        void removeThreadedRendererAnimatorListener();

        void reset();

        void resume();

        void reverse();

        void setListener(Animator.AnimatorListener animatorListener);

        void start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nAddAnimator(long j, long j2, long j3, long j4, long j5, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nCreateAnimatorSet();

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreateGroupPropertyHolder(long j, int i, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreatePathColorPropertyHolder(long j, int i, int i2, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreatePathDataPropertyHolder(long j, long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreatePathPropertyHolder(long j, int i, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreateRootAlphaPropertyHolder(long j, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nEnd(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nReset(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nReverse(long j, VectorDrawableAnimatorRT vectorDrawableAnimatorRT, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetPropertyHolderData(long j, float[] fArr, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetPropertyHolderData(long j, int[] iArr, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetVectorDrawableTarget(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nStart(long j, VectorDrawableAnimatorRT vectorDrawableAnimatorRT, int i);

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public AnimatedVectorDrawable() {
        this(null, null);
    }

    private AnimatedVectorDrawable(AnimatedVectorDrawableState animatedVectorDrawableState, Resources resources) {
        this.mAnimatorSetFromXml = null;
        this.mAnimationCallbacks = null;
        this.mAnimatorListener = null;
        Drawable.Callback callback = new Drawable.Callback() { // from class: android.graphics.drawable.AnimatedVectorDrawable.1
            @Override // android.graphics.drawable.Drawable.Callback
            public void invalidateDrawable(Drawable drawable) {
                AnimatedVectorDrawable.this.invalidateSelf();
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
                AnimatedVectorDrawable.this.scheduleSelf(runnable, j);
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                AnimatedVectorDrawable.this.unscheduleSelf(runnable);
            }
        };
        this.mCallback = callback;
        this.mAnimatedVectorState = new AnimatedVectorDrawableState(animatedVectorDrawableState, callback, resources);
        this.mAnimatorSet = new VectorDrawableAnimatorRT(this);
        this.mRes = resources;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mAnimatedVectorState = new AnimatedVectorDrawableState(this.mAnimatedVectorState, this.mCallback, this.mRes);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        if (this.mAnimatedVectorState.mVectorDrawable != null) {
            this.mAnimatedVectorState.mVectorDrawable.clearMutated();
        }
        this.mMutated = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean shouldIgnoreInvalidAnimation() {
        return Compatibility.getTargetSdkVersion() < 24;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        this.mAnimatedVectorState.mChangingConfigurations = getChangingConfigurations();
        return this.mAnimatedVectorState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mAnimatedVectorState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (!canvas.isHardwareAccelerated()) {
            VectorDrawableAnimator vectorDrawableAnimator = this.mAnimatorSet;
            if ((vectorDrawableAnimator instanceof VectorDrawableAnimatorRT) && !vectorDrawableAnimator.isRunning() && ((VectorDrawableAnimatorRT) this.mAnimatorSet).mPendingAnimationActions.size() > 0) {
                fallbackOntoUI();
            }
        }
        this.mAnimatorSet.onDraw(canvas);
        this.mAnimatedVectorState.mVectorDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        this.mAnimatedVectorState.mVectorDrawable.setBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        return this.mAnimatedVectorState.mVectorDrawable.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        return this.mAnimatedVectorState.mVectorDrawable.setLevel(i);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLayoutDirectionChanged(int i) {
        return this.mAnimatedVectorState.mVectorDrawable.setLayoutDirection(i);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAnimatedVectorState.mVectorDrawable.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mAnimatedVectorState.mVectorDrawable.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mAnimatedVectorState.mVectorDrawable.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mAnimatedVectorState.mVectorDrawable.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mAnimatedVectorState.mVectorDrawable.setTintList(colorStateList);
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        this.mAnimatedVectorState.mVectorDrawable.setHotspot(f, f2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        this.mAnimatedVectorState.mVectorDrawable.setHotspotBounds(i, i2, i3, i4);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        this.mAnimatedVectorState.mVectorDrawable.setTintBlendMode(blendMode);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        if (this.mAnimatorSet.isInfinite() && this.mAnimatorSet.isStarted()) {
            if (z) {
                this.mAnimatorSet.resume();
            } else {
                this.mAnimatorSet.pause();
            }
        }
        this.mAnimatedVectorState.mVectorDrawable.setVisible(z, z2);
        return super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mAnimatedVectorState.mVectorDrawable.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mAnimatedVectorState.mVectorDrawable.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mAnimatedVectorState.mVectorDrawable.getIntrinsicHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        this.mAnimatedVectorState.mVectorDrawable.getOutline(outline);
    }

    @Override // android.graphics.drawable.Drawable
    public Insets getOpticalInsets() {
        return this.mAnimatedVectorState.mVectorDrawable.getOpticalInsets();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, Resources.NotFoundException, IOException {
        AnimatedVectorDrawableState animatedVectorDrawableState = this.mAnimatedVectorState;
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        float f = 1.0f;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                if (ANIMATED_VECTOR.equals(name)) {
                    TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedVectorDrawable);
                    int resourceId = typedArrayObtainAttributes.getResourceId(0, 0);
                    if (resourceId != 0) {
                        VectorDrawable vectorDrawable = (VectorDrawable) resources.getDrawable(resourceId, theme).mutate();
                        vectorDrawable.setAllowCaching(false);
                        vectorDrawable.setCallback(this.mCallback);
                        float pixelSize = vectorDrawable.getPixelSize();
                        if (animatedVectorDrawableState.mVectorDrawable != null) {
                            animatedVectorDrawableState.mVectorDrawable.setCallback(null);
                        }
                        animatedVectorDrawableState.mVectorDrawable = vectorDrawable;
                        f = pixelSize;
                    }
                    typedArrayObtainAttributes.recycle();
                } else if (TARGET.equals(name)) {
                    TypedArray typedArrayObtainAttributes2 = obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedVectorDrawableTarget);
                    String string = typedArrayObtainAttributes2.getString(0);
                    int resourceId2 = typedArrayObtainAttributes2.getResourceId(1, 0);
                    if (resourceId2 != 0) {
                        if (theme != null) {
                            Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(resources, theme, resourceId2, f);
                            updateAnimatorProperty(animatorLoadAnimator, string, animatedVectorDrawableState.mVectorDrawable, animatedVectorDrawableState.mShouldIgnoreInvalidAnim);
                            animatedVectorDrawableState.addTargetAnimator(string, animatorLoadAnimator);
                        } else {
                            animatedVectorDrawableState.addPendingAnimator(resourceId2, f, string);
                        }
                    }
                    typedArrayObtainAttributes2.recycle();
                }
            }
            eventType = xmlPullParser.next();
        }
        if (animatedVectorDrawableState.mPendingAnims == null) {
            resources = null;
        }
        this.mRes = resources;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateAnimatorProperty(Animator animator, String str, VectorDrawable vectorDrawable, boolean z) {
        Property property;
        if (animator instanceof ObjectAnimator) {
            for (PropertyValuesHolder propertyValuesHolder : ((ObjectAnimator) animator).getValues()) {
                String propertyName = propertyValuesHolder.getPropertyName();
                Object targetByName = vectorDrawable.getTargetByName(str);
                if (targetByName instanceof VectorDrawable.VObject) {
                    property = ((VectorDrawable.VObject) targetByName).getProperty(propertyName);
                } else {
                    property = targetByName instanceof VectorDrawable.VectorDrawableState ? ((VectorDrawable.VectorDrawableState) targetByName).getProperty(propertyName) : null;
                }
                if (property != null) {
                    if (containsSameValueType(propertyValuesHolder, property)) {
                        propertyValuesHolder.setProperty(property);
                    } else if (!z) {
                        throw new RuntimeException("Wrong valueType for Property: " + propertyName + ".  Expected type: " + property.getType().toString() + ". Actual type defined in resources: " + propertyValuesHolder.getValueType().toString());
                    }
                }
            }
            return;
        }
        if (animator instanceof AnimatorSet) {
            Iterator<Animator> it = ((AnimatorSet) animator).getChildAnimations().iterator();
            while (it.hasNext()) {
                updateAnimatorProperty(it.next(), str, vectorDrawable, z);
            }
        }
    }

    private static boolean containsSameValueType(PropertyValuesHolder propertyValuesHolder, Property property) {
        Class valueType = propertyValuesHolder.getValueType();
        Class type = property.getType();
        return (valueType == Float.TYPE || valueType == Float.class) ? type == Float.TYPE || type == Float.class : (valueType == Integer.TYPE || valueType == Integer.class) ? type == Integer.TYPE || type == Integer.class : valueType == type;
    }

    public void forceAnimationOnUI() {
        VectorDrawableAnimator vectorDrawableAnimator = this.mAnimatorSet;
        if (vectorDrawableAnimator instanceof VectorDrawableAnimatorRT) {
            if (((VectorDrawableAnimatorRT) vectorDrawableAnimator).isRunning()) {
                throw new UnsupportedOperationException("Cannot force Animated Vector Drawable to run on UI thread when the animation has started on RenderThread.");
            }
            fallbackOntoUI();
        }
    }

    private void fallbackOntoUI() {
        VectorDrawableAnimator vectorDrawableAnimator = this.mAnimatorSet;
        if (vectorDrawableAnimator instanceof VectorDrawableAnimatorRT) {
            VectorDrawableAnimatorRT vectorDrawableAnimatorRT = (VectorDrawableAnimatorRT) vectorDrawableAnimator;
            VectorDrawableAnimatorUI vectorDrawableAnimatorUI = new VectorDrawableAnimatorUI(this);
            this.mAnimatorSet = vectorDrawableAnimatorUI;
            AnimatorSet animatorSet = this.mAnimatorSetFromXml;
            if (animatorSet != null) {
                vectorDrawableAnimatorUI.init(animatorSet);
            }
            if (vectorDrawableAnimatorRT.mListener != null) {
                this.mAnimatorSet.setListener(vectorDrawableAnimatorRT.mListener);
            }
            vectorDrawableAnimatorRT.transferPendingActions(this.mAnimatorSet);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        AnimatedVectorDrawableState animatedVectorDrawableState = this.mAnimatedVectorState;
        return (animatedVectorDrawableState != null && animatedVectorDrawableState.canApplyTheme()) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        VectorDrawable vectorDrawable = this.mAnimatedVectorState.mVectorDrawable;
        if (vectorDrawable != null && vectorDrawable.canApplyTheme()) {
            vectorDrawable.applyTheme(theme);
        }
        if (theme != null) {
            this.mAnimatedVectorState.inflatePendingAnimators(theme.getResources(), theme);
        }
        if (this.mAnimatedVectorState.mPendingAnims == null) {
            this.mRes = null;
        }
    }

    public long getTotalDuration() {
        return this.mAnimatorSet.getTotalDuration();
    }

    private static class AnimatedVectorDrawableState extends Drawable.ConstantState {
        ArrayList<Animator> mAnimators;
        int mChangingConfigurations;
        ArrayList<PendingAnimator> mPendingAnims;
        private final boolean mShouldIgnoreInvalidAnim = AnimatedVectorDrawable.shouldIgnoreInvalidAnimation();
        ArrayMap<Animator, String> mTargetNameMap;
        VectorDrawable mVectorDrawable;

        public AnimatedVectorDrawableState(AnimatedVectorDrawableState animatedVectorDrawableState, Drawable.Callback callback, Resources resources) {
            if (animatedVectorDrawableState != null) {
                this.mChangingConfigurations = animatedVectorDrawableState.mChangingConfigurations;
                VectorDrawable vectorDrawable = animatedVectorDrawableState.mVectorDrawable;
                if (vectorDrawable != null) {
                    Drawable.ConstantState constantState = vectorDrawable.getConstantState();
                    if (resources != null) {
                        this.mVectorDrawable = (VectorDrawable) constantState.newDrawable(resources);
                    } else {
                        this.mVectorDrawable = (VectorDrawable) constantState.newDrawable();
                    }
                    VectorDrawable vectorDrawable2 = (VectorDrawable) this.mVectorDrawable.mutate();
                    this.mVectorDrawable = vectorDrawable2;
                    vectorDrawable2.setCallback(callback);
                    this.mVectorDrawable.setLayoutDirection(animatedVectorDrawableState.mVectorDrawable.getLayoutDirection());
                    this.mVectorDrawable.setBounds(animatedVectorDrawableState.mVectorDrawable.getBounds());
                    this.mVectorDrawable.setAllowCaching(false);
                }
                if (animatedVectorDrawableState.mAnimators != null) {
                    this.mAnimators = new ArrayList<>(animatedVectorDrawableState.mAnimators);
                }
                if (animatedVectorDrawableState.mTargetNameMap != null) {
                    this.mTargetNameMap = new ArrayMap<>(animatedVectorDrawableState.mTargetNameMap);
                }
                if (animatedVectorDrawableState.mPendingAnims != null) {
                    this.mPendingAnims = new ArrayList<>(animatedVectorDrawableState.mPendingAnims);
                    return;
                }
                return;
            }
            this.mVectorDrawable = new VectorDrawable();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            VectorDrawable vectorDrawable = this.mVectorDrawable;
            return (vectorDrawable != null && vectorDrawable.canApplyTheme()) || this.mPendingAnims != null || super.canApplyTheme();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new AnimatedVectorDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new AnimatedVectorDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        public void addPendingAnimator(int i, float f, String str) {
            if (this.mPendingAnims == null) {
                this.mPendingAnims = new ArrayList<>(1);
            }
            this.mPendingAnims.add(new PendingAnimator(i, f, str));
        }

        public void addTargetAnimator(String str, Animator animator) {
            if (this.mAnimators == null) {
                this.mAnimators = new ArrayList<>(1);
                this.mTargetNameMap = new ArrayMap<>(1);
            }
            this.mAnimators.add(animator);
            this.mTargetNameMap.put(animator, str);
        }

        public void prepareLocalAnimators(AnimatorSet animatorSet, Resources resources) {
            if (this.mPendingAnims != null) {
                if (resources != null) {
                    inflatePendingAnimators(resources, null);
                } else {
                    Log.e(AnimatedVectorDrawable.LOGTAG, "Failed to load animators. Either the AnimatedVectorDrawable must be created using a Resources object or applyTheme() must be called with a non-null Theme object.");
                }
                this.mPendingAnims = null;
            }
            ArrayList<Animator> arrayList = this.mAnimators;
            int size = arrayList == null ? 0 : arrayList.size();
            if (size > 0) {
                AnimatorSet.Builder builderPlay = animatorSet.play(prepareLocalAnimator(0));
                for (int i = 1; i < size; i++) {
                    builderPlay.with(prepareLocalAnimator(i));
                }
            }
        }

        private Animator prepareLocalAnimator(int i) {
            Animator animator = this.mAnimators.get(i);
            Animator animatorMo76clone = animator.mo76clone();
            String str = this.mTargetNameMap.get(animator);
            Object targetByName = this.mVectorDrawable.getTargetByName(str);
            if (!this.mShouldIgnoreInvalidAnim) {
                if (targetByName == null) {
                    throw new IllegalStateException("Target with the name \"" + str + "\" cannot be found in the VectorDrawable to be animated.");
                }
                if (!(targetByName instanceof VectorDrawable.VectorDrawableState) && !(targetByName instanceof VectorDrawable.VObject)) {
                    throw new UnsupportedOperationException("Target should be either VGroup, VPath, or ConstantState, " + targetByName.getClass() + " is not supported");
                }
            }
            animatorMo76clone.setTarget(targetByName);
            return animatorMo76clone;
        }

        public void inflatePendingAnimators(Resources resources, Resources.Theme theme) {
            ArrayList<PendingAnimator> arrayList = this.mPendingAnims;
            if (arrayList != null) {
                this.mPendingAnims = null;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    PendingAnimator pendingAnimator = arrayList.get(i);
                    Animator animatorNewInstance = pendingAnimator.newInstance(resources, theme);
                    AnimatedVectorDrawable.updateAnimatorProperty(animatorNewInstance, pendingAnimator.target, this.mVectorDrawable, this.mShouldIgnoreInvalidAnim);
                    addTargetAnimator(pendingAnimator.target, animatorNewInstance);
                }
            }
        }

        private static class PendingAnimator {
            public final int animResId;
            public final float pathErrorScale;
            public final String target;

            public PendingAnimator(int i, float f, String str) {
                this.animResId = i;
                this.pathErrorScale = f;
                this.target = str;
            }

            public Animator newInstance(Resources resources, Resources.Theme theme) {
                return AnimatorInflater.loadAnimator(resources, theme, this.animResId, this.pathErrorScale);
            }
        }
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mAnimatorSet.isRunning();
    }

    public void reset() {
        ensureAnimatorSet();
        this.mAnimatorSet.reset();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        ensureAnimatorSet();
        this.mAnimatorSet.start();
    }

    private void ensureAnimatorSet() {
        if (this.mAnimatorSetFromXml == null) {
            AnimatorSet animatorSet = new AnimatorSet();
            this.mAnimatorSetFromXml = animatorSet;
            this.mAnimatedVectorState.prepareLocalAnimators(animatorSet, this.mRes);
            this.mAnimatorSet.init(this.mAnimatorSetFromXml);
            this.mRes = null;
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mAnimatorSet.end();
    }

    public void reverse() {
        ensureAnimatorSet();
        if (!canReverse()) {
            Log.w(LOGTAG, "AnimatedVectorDrawable can't reverse()");
        } else {
            this.mAnimatorSet.reverse();
        }
    }

    public boolean canReverse() {
        return this.mAnimatorSet.canReverse();
    }

    @Override // android.graphics.drawable.Animatable2
    public void registerAnimationCallback(Animatable2.AnimationCallback animationCallback) {
        if (animationCallback == null) {
            return;
        }
        if (this.mAnimationCallbacks == null) {
            this.mAnimationCallbacks = new ArrayList<>();
        }
        this.mAnimationCallbacks.add(animationCallback);
        if (this.mAnimatorListener == null) {
            this.mAnimatorListener = new AnimatorListenerAdapter() { // from class: android.graphics.drawable.AnimatedVectorDrawable.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    ArrayList arrayList = new ArrayList(AnimatedVectorDrawable.this.mAnimationCallbacks);
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        ((Animatable2.AnimationCallback) arrayList.get(i)).onAnimationStart(AnimatedVectorDrawable.this);
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ArrayList arrayList = new ArrayList(AnimatedVectorDrawable.this.mAnimationCallbacks);
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        ((Animatable2.AnimationCallback) arrayList.get(i)).onAnimationEnd(AnimatedVectorDrawable.this);
                    }
                }
            };
        }
        this.mAnimatorSet.setListener(this.mAnimatorListener);
    }

    private void removeAnimatorSetListener() {
        VectorDrawableAnimator vectorDrawableAnimator = this.mAnimatorSet;
        if (vectorDrawableAnimator instanceof VectorDrawableAnimatorRT) {
            vectorDrawableAnimator.removeThreadedRendererAnimatorListener();
        }
        Animator.AnimatorListener animatorListener = this.mAnimatorListener;
        if (animatorListener != null) {
            this.mAnimatorSet.removeListener(animatorListener);
            this.mAnimatorListener = null;
        }
    }

    @Override // android.graphics.drawable.Animatable2
    public boolean unregisterAnimationCallback(Animatable2.AnimationCallback animationCallback) {
        ArrayList<Animatable2.AnimationCallback> arrayList = this.mAnimationCallbacks;
        if (arrayList == null || animationCallback == null) {
            return false;
        }
        boolean zRemove = arrayList.remove(animationCallback);
        if (this.mAnimationCallbacks.size() == 0) {
            removeAnimatorSetListener();
        }
        return zRemove;
    }

    @Override // android.graphics.drawable.Animatable2
    public void clearAnimationCallbacks() {
        removeAnimatorSetListener();
        ArrayList<Animatable2.AnimationCallback> arrayList = this.mAnimationCallbacks;
        if (arrayList == null) {
            return;
        }
        arrayList.clear();
    }

    public void hidden_semSetPathColor(int i) {
        AnimatedVectorDrawableState animatedVectorDrawableState = this.mAnimatedVectorState;
        if (animatedVectorDrawableState == null || animatedVectorDrawableState.mVectorDrawable == null) {
            return;
        }
        this.mAnimatedVectorState.mVectorDrawable.setPathColor("all", i);
    }

    private static class VectorDrawableAnimatorUI implements VectorDrawableAnimator {
        private final Drawable mDrawable;
        private long mTotalDuration;
        private AnimatorSet mSet = null;
        private ArrayList<Animator.AnimatorListener> mListenerArray = null;
        private boolean mIsInfinite = false;

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void removeThreadedRendererAnimatorListener() {
        }

        VectorDrawableAnimatorUI(AnimatedVectorDrawable animatedVectorDrawable) {
            this.mDrawable = animatedVectorDrawable;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void init(AnimatorSet animatorSet) {
            if (this.mSet != null) {
                throw new UnsupportedOperationException("VectorDrawableAnimator cannot be re-initialized");
            }
            AnimatorSet animatorSetMo76clone = animatorSet.mo76clone();
            this.mSet = animatorSetMo76clone;
            long totalDuration = animatorSetMo76clone.getTotalDuration();
            this.mTotalDuration = totalDuration;
            this.mIsInfinite = totalDuration == -1;
            ArrayList<Animator.AnimatorListener> arrayList = this.mListenerArray;
            if (arrayList == null || arrayList.isEmpty()) {
                return;
            }
            for (int i = 0; i < this.mListenerArray.size(); i++) {
                this.mSet.addListener(this.mListenerArray.get(i));
            }
            this.mListenerArray.clear();
            this.mListenerArray = null;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void start() {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null || animatorSet.isStarted()) {
                return;
            }
            this.mSet.start();
            invalidateOwningView();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void end() {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null) {
                return;
            }
            animatorSet.end();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void reset() {
            if (this.mSet == null) {
                return;
            }
            start();
            this.mSet.cancel();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void reverse() {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null) {
                return;
            }
            animatorSet.reverse();
            invalidateOwningView();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean canReverse() {
            AnimatorSet animatorSet = this.mSet;
            return animatorSet != null && animatorSet.canReverse();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void setListener(Animator.AnimatorListener animatorListener) {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null) {
                if (this.mListenerArray == null) {
                    this.mListenerArray = new ArrayList<>();
                }
                this.mListenerArray.add(animatorListener);
                return;
            }
            animatorSet.addListener(animatorListener);
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void removeListener(Animator.AnimatorListener animatorListener) {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null) {
                ArrayList<Animator.AnimatorListener> arrayList = this.mListenerArray;
                if (arrayList == null) {
                    return;
                }
                arrayList.remove(animatorListener);
                return;
            }
            animatorSet.removeListener(animatorListener);
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void onDraw(Canvas canvas) {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null || !animatorSet.isStarted()) {
                return;
            }
            invalidateOwningView();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isStarted() {
            AnimatorSet animatorSet = this.mSet;
            return animatorSet != null && animatorSet.isStarted();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isRunning() {
            AnimatorSet animatorSet = this.mSet;
            return animatorSet != null && animatorSet.isRunning();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isInfinite() {
            return this.mIsInfinite;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void pause() {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null) {
                return;
            }
            animatorSet.pause();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void resume() {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null) {
                return;
            }
            animatorSet.resume();
        }

        private void invalidateOwningView() {
            this.mDrawable.invalidateSelf();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public long getTotalDuration() {
            return this.mTotalDuration;
        }
    }

    public static class VectorDrawableAnimatorRT implements VectorDrawableAnimator, NativeVectorDrawableAnimator {
        private static final int END_ANIMATION = 4;
        private static final int MAX_SAMPLE_POINTS = 300;
        private static final int RESET_ANIMATION = 3;
        private static final int REVERSE_ANIMATION = 2;
        private static final int START_ANIMATION = 1;
        private final AnimatedVectorDrawable mDrawable;
        private Handler mHandler;
        private long mSetPtr;
        private final VirtualRefBasePtr mSetRefBasePtr;
        private Animator.AnimatorListener mThreadedRendererAnimatorListener;
        private long mTotalDuration;
        private Animator.AnimatorListener mListener = null;
        private final LongArray mStartDelays = new LongArray();
        private PropertyValuesHolder.PropertyValues mTmpValues = new PropertyValuesHolder.PropertyValues();
        private boolean mContainsSequentialAnimators = false;
        private boolean mStarted = false;
        private boolean mInitialized = false;
        private boolean mIsReversible = false;
        private boolean mIsInfinite = false;
        private WeakReference<RenderNode> mLastSeenTarget = null;
        private int mLastListenerId = 0;
        private final IntArray mPendingAnimationActions = new IntArray();

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void pause() {
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void resume() {
        }

        VectorDrawableAnimatorRT(AnimatedVectorDrawable animatedVectorDrawable) {
            this.mSetPtr = 0L;
            this.mDrawable = animatedVectorDrawable;
            this.mSetPtr = AnimatedVectorDrawable.nCreateAnimatorSet();
            this.mSetRefBasePtr = new VirtualRefBasePtr(this.mSetPtr);
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void init(AnimatorSet animatorSet) {
            if (this.mInitialized) {
                throw new UnsupportedOperationException("VectorDrawableAnimator cannot be re-initialized");
            }
            parseAnimatorSet(animatorSet, 0L);
            AnimatedVectorDrawable.nSetVectorDrawableTarget(this.mSetPtr, this.mDrawable.mAnimatedVectorState.mVectorDrawable.getNativeTree());
            this.mInitialized = true;
            long totalDuration = animatorSet.getTotalDuration();
            this.mTotalDuration = totalDuration;
            this.mIsInfinite = totalDuration == -1;
            this.mIsReversible = true;
            if (this.mContainsSequentialAnimators) {
                this.mIsReversible = false;
                return;
            }
            for (int i = 0; i < this.mStartDelays.size(); i++) {
                if (this.mStartDelays.get(i) > 0) {
                    this.mIsReversible = false;
                    return;
                }
            }
        }

        private void parseAnimatorSet(AnimatorSet animatorSet, long j) {
            ArrayList<Animator> childAnimations = animatorSet.getChildAnimations();
            boolean zShouldPlayTogether = animatorSet.shouldPlayTogether();
            for (int i = 0; i < childAnimations.size(); i++) {
                Animator animator = childAnimations.get(i);
                if (animator instanceof AnimatorSet) {
                    parseAnimatorSet((AnimatorSet) animator, j);
                } else if (animator instanceof ObjectAnimator) {
                    createRTAnimator((ObjectAnimator) animator, j);
                }
                if (!zShouldPlayTogether) {
                    j += animator.getTotalDuration();
                    this.mContainsSequentialAnimators = true;
                }
            }
        }

        private void createRTAnimator(ObjectAnimator objectAnimator, long j) {
            PropertyValuesHolder[] values = objectAnimator.getValues();
            Object target = objectAnimator.getTarget();
            if (target instanceof VectorDrawable.VGroup) {
                createRTAnimatorForGroup(values, objectAnimator, (VectorDrawable.VGroup) target, j);
                return;
            }
            if (target instanceof VectorDrawable.VPath) {
                for (PropertyValuesHolder propertyValuesHolder : values) {
                    propertyValuesHolder.getPropertyValues(this.mTmpValues);
                    if ((this.mTmpValues.endValue instanceof PathParser.PathData) && this.mTmpValues.propertyName.equals("pathData")) {
                        createRTAnimatorForPath(objectAnimator, (VectorDrawable.VPath) target, j);
                    } else if (target instanceof VectorDrawable.VFullPath) {
                        createRTAnimatorForFullPath(objectAnimator, (VectorDrawable.VFullPath) target, j);
                    } else if (!this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                        throw new IllegalArgumentException("ClipPath only supports PathData property");
                    }
                }
                return;
            }
            if (target instanceof VectorDrawable.VectorDrawableState) {
                createRTAnimatorForRootGroup(values, objectAnimator, (VectorDrawable.VectorDrawableState) target, j);
            }
        }

        private void createRTAnimatorForGroup(PropertyValuesHolder[] propertyValuesHolderArr, ObjectAnimator objectAnimator, VectorDrawable.VGroup vGroup, long j) {
            long nativePtr = vGroup.getNativePtr();
            for (PropertyValuesHolder propertyValuesHolder : propertyValuesHolderArr) {
                propertyValuesHolder.getPropertyValues(this.mTmpValues);
                int propertyIndex = VectorDrawable.VGroup.getPropertyIndex(this.mTmpValues.propertyName);
                if ((this.mTmpValues.type == Float.class || this.mTmpValues.type == Float.TYPE) && propertyIndex >= 0) {
                    long jNCreateGroupPropertyHolder = AnimatedVectorDrawable.nCreateGroupPropertyHolder(nativePtr, propertyIndex, ((Float) this.mTmpValues.startValue).floatValue(), ((Float) this.mTmpValues.endValue).floatValue());
                    if (this.mTmpValues.dataSource != null) {
                        float[] fArrCreateFloatDataPoints = createFloatDataPoints(this.mTmpValues.dataSource, objectAnimator.getDuration());
                        AnimatedVectorDrawable.nSetPropertyHolderData(jNCreateGroupPropertyHolder, fArrCreateFloatDataPoints, fArrCreateFloatDataPoints.length);
                    }
                    createNativeChildAnimator(jNCreateGroupPropertyHolder, j, objectAnimator);
                }
            }
        }

        private void createRTAnimatorForPath(ObjectAnimator objectAnimator, VectorDrawable.VPath vPath, long j) {
            createNativeChildAnimator(AnimatedVectorDrawable.nCreatePathDataPropertyHolder(vPath.getNativePtr(), ((PathParser.PathData) this.mTmpValues.startValue).getNativePtr(), ((PathParser.PathData) this.mTmpValues.endValue).getNativePtr()), j, objectAnimator);
        }

        private void createRTAnimatorForFullPath(ObjectAnimator objectAnimator, VectorDrawable.VFullPath vFullPath, long j) {
            long jNCreatePathPropertyHolder;
            int propertyIndex = vFullPath.getPropertyIndex(this.mTmpValues.propertyName);
            long nativePtr = vFullPath.getNativePtr();
            if (this.mTmpValues.type == Float.class || this.mTmpValues.type == Float.TYPE) {
                if (propertyIndex < 0) {
                    if (this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                        return;
                    }
                    throw new IllegalArgumentException("Property: " + this.mTmpValues.propertyName + " is not supported for FullPath");
                }
                jNCreatePathPropertyHolder = AnimatedVectorDrawable.nCreatePathPropertyHolder(nativePtr, propertyIndex, ((Float) this.mTmpValues.startValue).floatValue(), ((Float) this.mTmpValues.endValue).floatValue());
                if (this.mTmpValues.dataSource != null) {
                    float[] fArrCreateFloatDataPoints = createFloatDataPoints(this.mTmpValues.dataSource, objectAnimator.getDuration());
                    AnimatedVectorDrawable.nSetPropertyHolderData(jNCreatePathPropertyHolder, fArrCreateFloatDataPoints, fArrCreateFloatDataPoints.length);
                }
            } else if (this.mTmpValues.type == Integer.class || this.mTmpValues.type == Integer.TYPE) {
                jNCreatePathPropertyHolder = AnimatedVectorDrawable.nCreatePathColorPropertyHolder(nativePtr, propertyIndex, ((Integer) this.mTmpValues.startValue).intValue(), ((Integer) this.mTmpValues.endValue).intValue());
                if (this.mTmpValues.dataSource != null) {
                    int[] iArrCreateIntDataPoints = createIntDataPoints(this.mTmpValues.dataSource, objectAnimator.getDuration());
                    AnimatedVectorDrawable.nSetPropertyHolderData(jNCreatePathPropertyHolder, iArrCreateIntDataPoints, iArrCreateIntDataPoints.length);
                }
            } else {
                if (this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                    return;
                }
                throw new UnsupportedOperationException("Unsupported type: " + this.mTmpValues.type + ". Only float, int or PathData value is supported for Paths.");
            }
            createNativeChildAnimator(jNCreatePathPropertyHolder, j, objectAnimator);
        }

        private void createRTAnimatorForRootGroup(PropertyValuesHolder[] propertyValuesHolderArr, ObjectAnimator objectAnimator, VectorDrawable.VectorDrawableState vectorDrawableState, long j) {
            Float f;
            Float f2;
            long nativeRenderer = vectorDrawableState.getNativeRenderer();
            if (!objectAnimator.getPropertyName().equals("alpha")) {
                if (!this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                    throw new UnsupportedOperationException("Only alpha is supported for root group");
                }
                return;
            }
            int i = 0;
            while (true) {
                if (i >= propertyValuesHolderArr.length) {
                    f = null;
                    f2 = null;
                    break;
                }
                propertyValuesHolderArr[i].getPropertyValues(this.mTmpValues);
                if (this.mTmpValues.propertyName.equals("alpha")) {
                    f = (Float) this.mTmpValues.startValue;
                    f2 = (Float) this.mTmpValues.endValue;
                    break;
                }
                i++;
            }
            if (f == null && f2 == null) {
                if (!this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                    throw new UnsupportedOperationException("No alpha values are specified");
                }
                return;
            }
            long jNCreateRootAlphaPropertyHolder = AnimatedVectorDrawable.nCreateRootAlphaPropertyHolder(nativeRenderer, f.floatValue(), f2.floatValue());
            if (this.mTmpValues.dataSource != null) {
                float[] fArrCreateFloatDataPoints = createFloatDataPoints(this.mTmpValues.dataSource, objectAnimator.getDuration());
                AnimatedVectorDrawable.nSetPropertyHolderData(jNCreateRootAlphaPropertyHolder, fArrCreateFloatDataPoints, fArrCreateFloatDataPoints.length);
            }
            createNativeChildAnimator(jNCreateRootAlphaPropertyHolder, j, objectAnimator);
        }

        private static int getFrameCount(long j) {
            int iMax = Math.max(2, (int) Math.ceil(j / ((int) (Choreographer.getInstance().getFrameIntervalNanos() / 1000000))));
            if (iMax <= 300) {
                return iMax;
            }
            Log.w(AnimatedVectorDrawable.LOGTAG, "Duration for the animation is too long :" + j + ", the animation will subsample the keyframe or path data.");
            return 300;
        }

        private static float[] createFloatDataPoints(PropertyValuesHolder.PropertyValues.DataSource dataSource, long j) {
            int frameCount = getFrameCount(j);
            float[] fArr = new float[frameCount];
            float f = frameCount - 1;
            for (int i = 0; i < frameCount; i++) {
                fArr[i] = ((Float) dataSource.getValueAtFraction(i / f)).floatValue();
            }
            return fArr;
        }

        private static int[] createIntDataPoints(PropertyValuesHolder.PropertyValues.DataSource dataSource, long j) {
            int frameCount = getFrameCount(j);
            int[] iArr = new int[frameCount];
            float f = frameCount - 1;
            for (int i = 0; i < frameCount; i++) {
                iArr[i] = ((Integer) dataSource.getValueAtFraction(i / f)).intValue();
            }
            return iArr;
        }

        private void createNativeChildAnimator(long j, long j2, ObjectAnimator objectAnimator) {
            long duration = objectAnimator.getDuration();
            int repeatCount = objectAnimator.getRepeatCount();
            long startDelay = j2 + objectAnimator.getStartDelay();
            long jCreateNativeInterpolator = NativeInterpolatorFactory.createNativeInterpolator(objectAnimator.getInterpolator(), duration);
            long durationScale = (long) (startDelay * ValueAnimator.getDurationScale());
            long durationScale2 = (long) (duration * ValueAnimator.getDurationScale());
            this.mStartDelays.add(durationScale);
            AnimatedVectorDrawable.nAddAnimator(this.mSetPtr, j, jCreateNativeInterpolator, durationScale, durationScale2, repeatCount, objectAnimator.getRepeatMode());
        }

        protected void recordLastSeenTarget(RecordingCanvas recordingCanvas) {
            RenderNode renderNode = recordingCanvas.mNode;
            this.mLastSeenTarget = new WeakReference<>(renderNode);
            if ((this.mInitialized || this.mPendingAnimationActions.size() > 0) && useTarget(renderNode)) {
                for (int i = 0; i < this.mPendingAnimationActions.size(); i++) {
                    handlePendingAction(this.mPendingAnimationActions.get(i));
                }
                this.mPendingAnimationActions.clear();
            }
        }

        private void handlePendingAction(int i) {
            if (i == 1) {
                startAnimation();
                return;
            }
            if (i == 2) {
                reverseAnimation();
                return;
            }
            if (i == 3) {
                resetAnimation();
            } else {
                if (i == 4) {
                    endAnimation();
                    return;
                }
                throw new UnsupportedOperationException("Animation action " + i + "is not supported");
            }
        }

        private boolean useLastSeenTarget() {
            WeakReference<RenderNode> weakReference = this.mLastSeenTarget;
            if (weakReference != null) {
                return useTarget(weakReference.get());
            }
            return false;
        }

        private boolean useTarget(RenderNode renderNode) {
            if (renderNode == null || !renderNode.isAttached()) {
                return false;
            }
            renderNode.registerVectorDrawableAnimator(this);
            return true;
        }

        private void invalidateOwningView() {
            this.mDrawable.invalidateSelf();
        }

        private void addPendingAction(int i) {
            invalidateOwningView();
            this.mPendingAnimationActions.add(i);
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void start() {
            if (this.mInitialized) {
                if (useLastSeenTarget()) {
                    startAnimation();
                } else {
                    addPendingAction(1);
                }
            }
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void end() {
            if (this.mInitialized) {
                if (useLastSeenTarget()) {
                    endAnimation();
                } else {
                    addPendingAction(4);
                }
            }
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void reset() {
            if (this.mInitialized) {
                if (useLastSeenTarget()) {
                    resetAnimation();
                } else {
                    addPendingAction(3);
                }
            }
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void reverse() {
            if (this.mIsReversible && this.mInitialized) {
                if (useLastSeenTarget()) {
                    reverseAnimation();
                } else {
                    addPendingAction(2);
                }
            }
        }

        private void startAnimation() {
            this.mStarted = true;
            if (this.mHandler == null) {
                this.mHandler = new Handler();
            }
            long j = this.mSetPtr;
            int i = this.mLastListenerId + 1;
            this.mLastListenerId = i;
            AnimatedVectorDrawable.nStart(j, this, i);
            invalidateOwningView();
            Animator.AnimatorListener animatorListener = this.mListener;
            if (animatorListener != null) {
                animatorListener.onAnimationStart(null);
            }
            Animator.AnimatorListener animatorListener2 = this.mThreadedRendererAnimatorListener;
            if (animatorListener2 != null) {
                animatorListener2.onAnimationStart(null);
            }
        }

        private void endAnimation() {
            AnimatedVectorDrawable.nEnd(this.mSetPtr);
            invalidateOwningView();
        }

        private void resetAnimation() {
            AnimatedVectorDrawable.nReset(this.mSetPtr);
            invalidateOwningView();
        }

        private void reverseAnimation() {
            this.mStarted = true;
            long j = this.mSetPtr;
            int i = this.mLastListenerId + 1;
            this.mLastListenerId = i;
            AnimatedVectorDrawable.nReverse(j, this, i);
            invalidateOwningView();
            Animator.AnimatorListener animatorListener = this.mListener;
            if (animatorListener != null) {
                animatorListener.onAnimationStart(null);
            }
            Animator.AnimatorListener animatorListener2 = this.mThreadedRendererAnimatorListener;
            if (animatorListener2 != null) {
                animatorListener2.onAnimationStart(null);
            }
        }

        @Override // android.view.NativeVectorDrawableAnimator
        public long getAnimatorNativePtr() {
            return this.mSetPtr;
        }

        @Override // android.view.NativeVectorDrawableAnimator
        public void setThreadedRendererAnimatorListener(Animator.AnimatorListener animatorListener) {
            this.mThreadedRendererAnimatorListener = animatorListener;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean canReverse() {
            return this.mIsReversible;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isStarted() {
            return this.mStarted;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isRunning() {
            if (this.mInitialized) {
                return this.mStarted;
            }
            return false;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void setListener(Animator.AnimatorListener animatorListener) {
            this.mListener = animatorListener;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void removeListener(Animator.AnimatorListener animatorListener) {
            this.mListener = null;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void removeThreadedRendererAnimatorListener() {
            this.mThreadedRendererAnimatorListener = null;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void onDraw(Canvas canvas) {
            if (canvas.isHardwareAccelerated()) {
                recordLastSeenTarget((RecordingCanvas) canvas);
            }
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isInfinite() {
            return this.mIsInfinite;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onAnimationEnd(int i) {
            if (i != this.mLastListenerId) {
                return;
            }
            this.mStarted = false;
            invalidateOwningView();
            Animator.AnimatorListener animatorListener = this.mListener;
            if (animatorListener != null) {
                animatorListener.onAnimationEnd(null);
            }
            Animator.AnimatorListener animatorListener2 = this.mThreadedRendererAnimatorListener;
            if (animatorListener2 != null) {
                animatorListener2.onAnimationEnd(null);
            }
        }

        private static void callOnFinished(final VectorDrawableAnimatorRT vectorDrawableAnimatorRT, final int i) {
            vectorDrawableAnimatorRT.mHandler.post(new Runnable() { // from class: android.graphics.drawable.AnimatedVectorDrawable$VectorDrawableAnimatorRT$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.onAnimationEnd(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void transferPendingActions(VectorDrawableAnimator vectorDrawableAnimator) {
            for (int i = 0; i < this.mPendingAnimationActions.size(); i++) {
                int i2 = this.mPendingAnimationActions.get(i);
                if (i2 == 1) {
                    vectorDrawableAnimator.start();
                } else if (i2 == 4) {
                    vectorDrawableAnimator.end();
                } else if (i2 == 2) {
                    vectorDrawableAnimator.reverse();
                } else if (i2 == 3) {
                    vectorDrawableAnimator.reset();
                } else {
                    throw new UnsupportedOperationException("Animation action " + i2 + "is not supported");
                }
            }
            this.mPendingAnimationActions.clear();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public long getTotalDuration() {
            return this.mTotalDuration;
        }
    }
}
