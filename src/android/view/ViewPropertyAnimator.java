package android.view;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.RenderNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class ViewPropertyAnimator {
    static final int ALPHA = 2048;
    static final int NONE = 0;
    static final int ROTATION = 32;
    static final int ROTATION_X = 64;
    static final int ROTATION_Y = 128;
    static final int SCALE_X = 8;
    static final int SCALE_Y = 16;
    private static final int TRANSFORM_MASK = 2047;
    static final int TRANSLATION_X = 1;
    static final int TRANSLATION_Y = 2;
    static final int TRANSLATION_Z = 4;
    static final int X = 256;
    static final int Y = 512;
    static final int Z = 1024;
    private HashMap<Animator, Runnable> mAnimatorCleanupMap;
    private HashMap<Animator, Runnable> mAnimatorOnEndMap;
    private HashMap<Animator, Runnable> mAnimatorOnStartMap;
    private HashMap<Animator, Runnable> mAnimatorSetupMap;
    private long mDuration;
    private TimeInterpolator mInterpolator;
    private Runnable mPendingCleanupAction;
    private Runnable mPendingOnEndAction;
    private Runnable mPendingOnStartAction;
    private Runnable mPendingSetupAction;
    private ValueAnimator mTempValueAnimator;
    final View mView;
    private boolean mDurationSet = false;
    private long mStartDelay = 0;
    private boolean mStartDelaySet = false;
    private boolean mInterpolatorSet = false;
    private Animator.AnimatorListener mListener = null;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener = null;
    private AnimatorEventListener mAnimatorEventListener = new AnimatorEventListener();
    ArrayList<NameValuesHolder> mPendingAnimations = new ArrayList<>();
    private Runnable mAnimationStarter = new Runnable() { // from class: android.view.ViewPropertyAnimator.1
        @Override // java.lang.Runnable
        public void run() {
            ViewPropertyAnimator.this.startAnimation();
        }
    };
    private HashMap<Animator, PropertyBundle> mAnimatorMap = new HashMap<>();

    private static class PropertyBundle {
        ArrayList<NameValuesHolder> mNameValuesHolder;
        int mPropertyMask;

        PropertyBundle(int i, ArrayList<NameValuesHolder> arrayList) {
            this.mPropertyMask = i;
            this.mNameValuesHolder = arrayList;
        }

        boolean cancel(int i) {
            ArrayList<NameValuesHolder> arrayList;
            if ((this.mPropertyMask & i) != 0 && (arrayList = this.mNameValuesHolder) != null) {
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (this.mNameValuesHolder.get(i2).mNameConstant == i) {
                        this.mNameValuesHolder.remove(i2);
                        this.mPropertyMask = (~i) & this.mPropertyMask;
                        return true;
                    }
                }
            }
            return false;
        }
    }

    static class NameValuesHolder {
        float mDeltaValue;
        float mFromValue;
        int mNameConstant;

        NameValuesHolder(int i, float f, float f2) {
            this.mNameConstant = i;
            this.mFromValue = f;
            this.mDeltaValue = f2;
        }
    }

    ViewPropertyAnimator(View view) {
        this.mView = view;
        view.ensureTransformationInfo();
    }

    public ViewPropertyAnimator setDuration(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + j);
        }
        this.mDurationSet = true;
        this.mDuration = j;
        return this;
    }

    public long getDuration() {
        if (this.mDurationSet) {
            return this.mDuration;
        }
        if (this.mTempValueAnimator == null) {
            this.mTempValueAnimator = new ValueAnimator();
        }
        return this.mTempValueAnimator.getDuration();
    }

    public long getStartDelay() {
        if (this.mStartDelaySet) {
            return this.mStartDelay;
        }
        return 0L;
    }

    public ViewPropertyAnimator setStartDelay(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("Animators cannot have negative start delay: " + j);
        }
        this.mStartDelaySet = true;
        this.mStartDelay = j;
        return this;
    }

    public ViewPropertyAnimator setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolatorSet = true;
        this.mInterpolator = timeInterpolator;
        return this;
    }

    public TimeInterpolator getInterpolator() {
        if (this.mInterpolatorSet) {
            return this.mInterpolator;
        }
        if (this.mTempValueAnimator == null) {
            this.mTempValueAnimator = new ValueAnimator();
        }
        return this.mTempValueAnimator.getInterpolator();
    }

    public ViewPropertyAnimator setListener(Animator.AnimatorListener animatorListener) {
        this.mListener = animatorListener;
        return this;
    }

    Animator.AnimatorListener getListener() {
        return this.mListener;
    }

    public ViewPropertyAnimator setUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.mUpdateListener = animatorUpdateListener;
        return this;
    }

    ValueAnimator.AnimatorUpdateListener getUpdateListener() {
        return this.mUpdateListener;
    }

    public void start() {
        this.mView.removeCallbacks(this.mAnimationStarter);
        startAnimation();
    }

    public void cancel() {
        if (this.mAnimatorMap.size() > 0) {
            Iterator it = ((HashMap) this.mAnimatorMap.clone()).keySet().iterator();
            while (it.hasNext()) {
                ((Animator) it.next()).cancel();
            }
        }
        this.mPendingAnimations.clear();
        this.mPendingSetupAction = null;
        this.mPendingCleanupAction = null;
        this.mPendingOnStartAction = null;
        this.mPendingOnEndAction = null;
        this.mView.removeCallbacks(this.mAnimationStarter);
    }

    public ViewPropertyAnimator x(float f) {
        animateProperty(256, f);
        return this;
    }

    public ViewPropertyAnimator xBy(float f) {
        animatePropertyBy(256, f);
        return this;
    }

    public ViewPropertyAnimator y(float f) {
        animateProperty(512, f);
        return this;
    }

    public ViewPropertyAnimator yBy(float f) {
        animatePropertyBy(512, f);
        return this;
    }

    public ViewPropertyAnimator z(float f) {
        animateProperty(1024, f);
        return this;
    }

    public ViewPropertyAnimator zBy(float f) {
        animatePropertyBy(1024, f);
        return this;
    }

    public ViewPropertyAnimator rotation(float f) {
        animateProperty(32, f);
        return this;
    }

    public ViewPropertyAnimator rotationBy(float f) {
        animatePropertyBy(32, f);
        return this;
    }

    public ViewPropertyAnimator rotationX(float f) {
        animateProperty(64, f);
        return this;
    }

    public ViewPropertyAnimator rotationXBy(float f) {
        animatePropertyBy(64, f);
        return this;
    }

    public ViewPropertyAnimator rotationY(float f) {
        animateProperty(128, f);
        return this;
    }

    public ViewPropertyAnimator rotationYBy(float f) {
        animatePropertyBy(128, f);
        return this;
    }

    public ViewPropertyAnimator translationX(float f) {
        animateProperty(1, f);
        return this;
    }

    public ViewPropertyAnimator translationXBy(float f) {
        animatePropertyBy(1, f);
        return this;
    }

    public ViewPropertyAnimator translationY(float f) {
        animateProperty(2, f);
        return this;
    }

    public ViewPropertyAnimator translationYBy(float f) {
        animatePropertyBy(2, f);
        return this;
    }

    public ViewPropertyAnimator translationZ(float f) {
        animateProperty(4, f);
        return this;
    }

    public ViewPropertyAnimator translationZBy(float f) {
        animatePropertyBy(4, f);
        return this;
    }

    public ViewPropertyAnimator scaleX(float f) {
        animateProperty(8, f);
        return this;
    }

    public ViewPropertyAnimator scaleXBy(float f) {
        animatePropertyBy(8, f);
        return this;
    }

    public ViewPropertyAnimator scaleY(float f) {
        animateProperty(16, f);
        return this;
    }

    public ViewPropertyAnimator scaleYBy(float f) {
        animatePropertyBy(16, f);
        return this;
    }

    public ViewPropertyAnimator alpha(float f) {
        animateProperty(2048, f);
        return this;
    }

    public ViewPropertyAnimator alphaBy(float f) {
        animatePropertyBy(2048, f);
        return this;
    }

    public ViewPropertyAnimator withLayer() {
        this.mPendingSetupAction = new Runnable() { // from class: android.view.ViewPropertyAnimator.2
            @Override // java.lang.Runnable
            public void run() {
                ViewPropertyAnimator.this.mView.setLayerType(2, null);
                if (ViewPropertyAnimator.this.mView.isAttachedToWindow()) {
                    ViewPropertyAnimator.this.mView.buildLayer();
                }
            }
        };
        final int layerType = this.mView.getLayerType();
        this.mPendingCleanupAction = new Runnable() { // from class: android.view.ViewPropertyAnimator.3
            @Override // java.lang.Runnable
            public void run() {
                ViewPropertyAnimator.this.mView.setLayerType(layerType, null);
            }
        };
        if (this.mAnimatorSetupMap == null) {
            this.mAnimatorSetupMap = new HashMap<>();
        }
        if (this.mAnimatorCleanupMap == null) {
            this.mAnimatorCleanupMap = new HashMap<>();
        }
        return this;
    }

    public ViewPropertyAnimator withStartAction(Runnable runnable) {
        this.mPendingOnStartAction = runnable;
        if (runnable != null && this.mAnimatorOnStartMap == null) {
            this.mAnimatorOnStartMap = new HashMap<>();
        }
        return this;
    }

    public ViewPropertyAnimator withEndAction(Runnable runnable) {
        this.mPendingOnEndAction = runnable;
        if (runnable != null && this.mAnimatorOnEndMap == null) {
            this.mAnimatorOnEndMap = new HashMap<>();
        }
        return this;
    }

    boolean hasActions() {
        return (this.mPendingSetupAction == null && this.mPendingCleanupAction == null && this.mPendingOnStartAction == null && this.mPendingOnEndAction == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnimation() {
        this.mView.setHasTransientState(true);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f);
        ArrayList arrayList = (ArrayList) this.mPendingAnimations.clone();
        this.mPendingAnimations.clear();
        int size = arrayList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i |= ((NameValuesHolder) arrayList.get(i2)).mNameConstant;
        }
        this.mAnimatorMap.put(valueAnimatorOfFloat, new PropertyBundle(i, arrayList));
        Runnable runnable = this.mPendingSetupAction;
        if (runnable != null) {
            this.mAnimatorSetupMap.put(valueAnimatorOfFloat, runnable);
            this.mPendingSetupAction = null;
        }
        Runnable runnable2 = this.mPendingCleanupAction;
        if (runnable2 != null) {
            this.mAnimatorCleanupMap.put(valueAnimatorOfFloat, runnable2);
            this.mPendingCleanupAction = null;
        }
        Runnable runnable3 = this.mPendingOnStartAction;
        if (runnable3 != null) {
            this.mAnimatorOnStartMap.put(valueAnimatorOfFloat, runnable3);
            this.mPendingOnStartAction = null;
        }
        Runnable runnable4 = this.mPendingOnEndAction;
        if (runnable4 != null) {
            this.mAnimatorOnEndMap.put(valueAnimatorOfFloat, runnable4);
            this.mPendingOnEndAction = null;
        }
        valueAnimatorOfFloat.addUpdateListener(this.mAnimatorEventListener);
        valueAnimatorOfFloat.addListener(this.mAnimatorEventListener);
        if (this.mStartDelaySet) {
            valueAnimatorOfFloat.setStartDelay(this.mStartDelay);
        }
        if (this.mDurationSet) {
            valueAnimatorOfFloat.setDuration(this.mDuration);
        }
        if (this.mInterpolatorSet) {
            valueAnimatorOfFloat.setInterpolator(this.mInterpolator);
        }
        valueAnimatorOfFloat.start();
    }

    private void animateProperty(int i, float f) {
        float value = getValue(i);
        animatePropertyBy(i, value, f - value);
    }

    private void animatePropertyBy(int i, float f) {
        animatePropertyBy(i, getValue(i), f);
    }

    private void animatePropertyBy(int i, float f, float f2) {
        Animator next;
        if (this.mAnimatorMap.size() > 0) {
            Iterator<Animator> it = this.mAnimatorMap.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                PropertyBundle propertyBundle = this.mAnimatorMap.get(next);
                if (propertyBundle.cancel(i) && propertyBundle.mPropertyMask == 0) {
                    break;
                }
            }
            if (next != null) {
                next.cancel();
            }
        }
        this.mPendingAnimations.add(new NameValuesHolder(i, f, f2));
        this.mView.removeCallbacks(this.mAnimationStarter);
        this.mView.postOnAnimation(this.mAnimationStarter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setValue(int i, float f) {
        RenderNode renderNode = this.mView.mRenderNode;
        if (i == 1) {
            renderNode.setTranslationX(f);
            return;
        }
        if (i == 2) {
            renderNode.setTranslationY(f);
            return;
        }
        if (i == 4) {
            renderNode.setTranslationZ(f);
            return;
        }
        if (i == 8) {
            renderNode.setScaleX(f);
            return;
        }
        if (i == 16) {
            renderNode.setScaleY(f);
            return;
        }
        if (i == 32) {
            renderNode.setRotationZ(f);
            return;
        }
        if (i == 64) {
            renderNode.setRotationX(f);
            return;
        }
        if (i == 128) {
            renderNode.setRotationY(f);
            return;
        }
        if (i == 256) {
            renderNode.setTranslationX(f - this.mView.mLeft);
            return;
        }
        if (i == 512) {
            renderNode.setTranslationY(f - this.mView.mTop);
            return;
        }
        if (i == 1024) {
            renderNode.setTranslationZ(f - renderNode.getElevation());
        } else {
            if (i != 2048) {
                return;
            }
            this.mView.setAlphaInternal(f);
            renderNode.setAlpha(f);
        }
    }

    private float getValue(int i) {
        RenderNode renderNode = this.mView.mRenderNode;
        if (i == 1) {
            return renderNode.getTranslationX();
        }
        if (i == 2) {
            return renderNode.getTranslationY();
        }
        if (i == 4) {
            return renderNode.getTranslationZ();
        }
        if (i == 8) {
            return renderNode.getScaleX();
        }
        if (i == 16) {
            return renderNode.getScaleY();
        }
        if (i == 32) {
            return renderNode.getRotationZ();
        }
        if (i == 64) {
            return renderNode.getRotationX();
        }
        if (i == 128) {
            return renderNode.getRotationY();
        }
        if (i == 256) {
            return this.mView.mLeft + renderNode.getTranslationX();
        }
        if (i == 512) {
            return this.mView.mTop + renderNode.getTranslationY();
        }
        if (i == 1024) {
            return renderNode.getElevation() + renderNode.getTranslationZ();
        }
        if (i != 2048) {
            return 0.0f;
        }
        return this.mView.getAlpha();
    }

    private class AnimatorEventListener implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {
        private AnimatorEventListener() {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            if (ViewPropertyAnimator.this.mAnimatorSetupMap != null) {
                Runnable runnable = (Runnable) ViewPropertyAnimator.this.mAnimatorSetupMap.get(animator);
                if (runnable != null) {
                    runnable.run();
                }
                ViewPropertyAnimator.this.mAnimatorSetupMap.remove(animator);
            }
            if (ViewPropertyAnimator.this.mAnimatorOnStartMap != null) {
                Runnable runnable2 = (Runnable) ViewPropertyAnimator.this.mAnimatorOnStartMap.get(animator);
                if (runnable2 != null) {
                    runnable2.run();
                }
                ViewPropertyAnimator.this.mAnimatorOnStartMap.remove(animator);
            }
            if (ViewPropertyAnimator.this.mListener != null) {
                ViewPropertyAnimator.this.mListener.onAnimationStart(animator);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            if (ViewPropertyAnimator.this.mListener != null) {
                ViewPropertyAnimator.this.mListener.onAnimationCancel(animator);
            }
            if (ViewPropertyAnimator.this.mAnimatorOnEndMap != null) {
                ViewPropertyAnimator.this.mAnimatorOnEndMap.remove(animator);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
            if (ViewPropertyAnimator.this.mListener != null) {
                ViewPropertyAnimator.this.mListener.onAnimationRepeat(animator);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            ViewPropertyAnimator.this.mView.setHasTransientState(false);
            if (ViewPropertyAnimator.this.mAnimatorCleanupMap != null) {
                Runnable runnable = (Runnable) ViewPropertyAnimator.this.mAnimatorCleanupMap.get(animator);
                if (runnable != null) {
                    runnable.run();
                }
                ViewPropertyAnimator.this.mAnimatorCleanupMap.remove(animator);
            }
            if (ViewPropertyAnimator.this.mListener != null) {
                ViewPropertyAnimator.this.mListener.onAnimationEnd(animator);
            }
            if (ViewPropertyAnimator.this.mAnimatorOnEndMap != null) {
                Runnable runnable2 = (Runnable) ViewPropertyAnimator.this.mAnimatorOnEndMap.get(animator);
                if (runnable2 != null) {
                    runnable2.run();
                }
                ViewPropertyAnimator.this.mAnimatorOnEndMap.remove(animator);
            }
            ViewPropertyAnimator.this.mAnimatorMap.remove(animator);
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            boolean alphaNoInvalidation;
            PropertyBundle propertyBundle = (PropertyBundle) ViewPropertyAnimator.this.mAnimatorMap.get(valueAnimator);
            if (propertyBundle == null) {
                return;
            }
            boolean zIsHardwareAccelerated = ViewPropertyAnimator.this.mView.isHardwareAccelerated();
            if (!zIsHardwareAccelerated) {
                ViewPropertyAnimator.this.mView.invalidateParentCaches();
            }
            float animatedFraction = valueAnimator.getAnimatedFraction();
            int i = propertyBundle.mPropertyMask & 2047;
            if (i != 0) {
                ViewPropertyAnimator.this.mView.invalidateViewProperty(zIsHardwareAccelerated, false);
            }
            ArrayList<NameValuesHolder> arrayList = propertyBundle.mNameValuesHolder;
            if (arrayList != null) {
                int size = arrayList.size();
                alphaNoInvalidation = false;
                for (int i2 = 0; i2 < size; i2++) {
                    NameValuesHolder nameValuesHolder = arrayList.get(i2);
                    float f = nameValuesHolder.mFromValue + (nameValuesHolder.mDeltaValue * animatedFraction);
                    if (nameValuesHolder.mNameConstant == 2048) {
                        alphaNoInvalidation = ViewPropertyAnimator.this.mView.setAlphaNoInvalidation(f);
                    } else {
                        ViewPropertyAnimator.this.setValue(nameValuesHolder.mNameConstant, f);
                    }
                }
            } else {
                alphaNoInvalidation = false;
            }
            if (i != 0 && !zIsHardwareAccelerated) {
                ViewPropertyAnimator.this.mView.mPrivateFlags |= 32;
            }
            if (alphaNoInvalidation) {
                ViewPropertyAnimator.this.mView.invalidate(true);
            } else {
                ViewPropertyAnimator.this.mView.invalidateViewProperty(false, false);
            }
            if (ViewPropertyAnimator.this.mUpdateListener != null) {
                ViewPropertyAnimator.this.mUpdateListener.onAnimationUpdate(valueAnimator);
            }
        }
    }
}
