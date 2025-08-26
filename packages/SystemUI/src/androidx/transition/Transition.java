package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.DefaultSpecialEffectsController$$ExternalSyntheticLambda0;
import androidx.transition.Transition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public abstract class Transition implements Cloneable {
    public Animator[] mAnimatorCache;
    public ArrayList mAnimators;
    public Transition mCloneParent;
    public final ArrayList mCurrentAnimators;
    public long mDuration;
    public TransitionValuesMaps mEndValues;
    public ArrayList mEndValuesList;
    public boolean mEnded;
    public EpicenterCallback mEpicenterCallback;
    public TimeInterpolator mInterpolator;
    public ArrayList mListeners;
    public TransitionListener[] mListenersCache;
    public final int[] mMatchOrder;
    public final String mName;
    public int mNumInstances;
    public TransitionSet mParent;
    public AnonymousClass1 mPathMotion;
    public boolean mPaused;
    public SeekController mSeekController;
    public long mSeekOffsetInParent;
    public long mStartDelay;
    public TransitionValuesMaps mStartValues;
    public ArrayList mStartValuesList;
    public final ArrayList mTargetIds;
    public final ArrayList mTargets;
    public long mTotalDuration;
    public static final Animator[] EMPTY_ANIMATOR_ARRAY = new Animator[0];
    public static final int[] DEFAULT_MATCH_ORDER = {2, 1, 3, 4};
    public static final AnonymousClass1 STRAIGHT_PATH_MOTION = new AnonymousClass1();
    public static final ThreadLocal sRunningAnimators = new ThreadLocal();

    /* renamed from: androidx.transition.Transition$1, reason: invalid class name */
    public class AnonymousClass1 extends PathMotion {
        public final Path getPath(float f, float f2, float f3, float f4) {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            return path;
        }
    }

    public class AnimationInfo {
        public final Animator mAnimator;
        public final String mName;
        public final Transition mTransition;
        public final TransitionValues mValues;
        public final View mView;
        public final WindowId mWindowId;

        public AnimationInfo(View view, String str, Transition transition, WindowId windowId, TransitionValues transitionValues, Animator animator) {
            this.mView = view;
            this.mName = str;
            this.mValues = transitionValues;
            this.mWindowId = windowId;
            this.mTransition = transition;
            this.mAnimator = animator;
        }
    }

    public abstract class EpicenterCallback {
    }

    public class Impl26 {
        private Impl26() {
        }

        public static long getTotalDuration(Animator animator) {
            return animator.getTotalDuration();
        }

        public static void setCurrentPlayTime(Animator animator, long j) {
            ((AnimatorSet) animator).setCurrentPlayTime(j);
        }
    }

    public class SeekController extends TransitionListenerAdapter implements DynamicAnimation.OnAnimationUpdateListener {
        public boolean mIsCanceled;
        public boolean mIsReady;
        public DefaultSpecialEffectsController$$ExternalSyntheticLambda0 mResetToStartState;
        public SpringAnimation mSpringAnimation;
        public long mCurrentPlayTime = -1;
        public final VelocityTracker1D mVelocityTracker = new VelocityTracker1D();

        public SeekController() {
        }

        public final void ensureAnimation() {
            float f;
            if (this.mSpringAnimation != null) {
                return;
            }
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            float f2 = this.mCurrentPlayTime;
            VelocityTracker1D velocityTracker1D = this.mVelocityTracker;
            char c = 20;
            int i = (velocityTracker1D.mIndex + 1) % 20;
            velocityTracker1D.mIndex = i;
            velocityTracker1D.mTimeSamples[i] = jCurrentAnimationTimeMillis;
            velocityTracker1D.mDataSamples[i] = f2;
            this.mSpringAnimation = new SpringAnimation(new FloatValueHolder());
            SpringForce springForce = new SpringForce();
            springForce.setDampingRatio(1.0f);
            springForce.setStiffness(200.0f);
            SpringAnimation springAnimation = this.mSpringAnimation;
            springAnimation.mSpring = springForce;
            springAnimation.setStartValue(this.mCurrentPlayTime);
            this.mSpringAnimation.addUpdateListener(this);
            SpringAnimation springAnimation2 = this.mSpringAnimation;
            int i2 = velocityTracker1D.mIndex;
            long[] jArr = velocityTracker1D.mTimeSamples;
            long j = Long.MIN_VALUE;
            float fSqrt = 0.0f;
            if (i2 != 0 || jArr[i2] != Long.MIN_VALUE) {
                long j2 = jArr[i2];
                int i3 = 0;
                long j3 = j2;
                while (true) {
                    long j4 = jArr[i2];
                    if (j4 == j) {
                        break;
                    }
                    float f3 = j2 - j4;
                    float fAbs = Math.abs(j4 - j3);
                    if (f3 > 100.0f || fAbs > 40.0f) {
                        break;
                    }
                    if (i2 == 0) {
                        i2 = 20;
                    }
                    i2--;
                    i3++;
                    if (i3 >= 20) {
                        break;
                    }
                    j3 = j4;
                    j = Long.MIN_VALUE;
                }
                if (i3 >= 2) {
                    float[] fArr = velocityTracker1D.mDataSamples;
                    float f4 = 1000.0f;
                    if (i3 == 2) {
                        int i4 = velocityTracker1D.mIndex;
                        int i5 = i4 == 0 ? 19 : i4 - 1;
                        float f5 = jArr[i4] - jArr[i5];
                        if (f5 != 0.0f) {
                            fSqrt = ((fArr[i4] - fArr[i5]) / f5) * 1000.0f;
                        }
                    } else {
                        int i6 = velocityTracker1D.mIndex;
                        int i7 = ((i6 - i3) + 21) % 20;
                        int i8 = (i6 + 21) % 20;
                        long j5 = jArr[i7];
                        float f6 = fArr[i7];
                        int i9 = i7 + 1;
                        int i10 = i9 % 20;
                        float f7 = 0.0f;
                        while (i10 != i8) {
                            long j6 = jArr[i10];
                            float f8 = fSqrt;
                            char c2 = c;
                            float f9 = j6 - j5;
                            if (f9 == f8) {
                                f = f4;
                            } else {
                                float f10 = fArr[i10];
                                f = f4;
                                float f11 = (f10 - f6) / f9;
                                float fAbs2 = (Math.abs(f11) * (f11 - ((float) (Math.sqrt(2.0f * Math.abs(f7)) * Math.signum(f7))))) + f7;
                                if (i10 == i9) {
                                    fAbs2 *= 0.5f;
                                }
                                f7 = fAbs2;
                                f6 = f10;
                                j5 = j6;
                            }
                            i10 = (i10 + 1) % 20;
                            c = c2;
                            fSqrt = f8;
                            f4 = f;
                        }
                        fSqrt = ((float) (Math.sqrt(Math.abs(f7) * 2.0f) * Math.signum(f7))) * f4;
                    }
                }
            }
            springAnimation2.mVelocity = fSqrt;
            SpringAnimation springAnimation3 = this.mSpringAnimation;
            springAnimation3.mMaxValue = Transition.this.mTotalDuration + 1;
            springAnimation3.mMinValue = -1.0f;
            springAnimation3.setMinimumVisibleChange(4.0f);
            this.mSpringAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: androidx.transition.Transition$SeekController$$ExternalSyntheticLambda0
                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f12, float f13) {
                    Transition.SeekController seekController = this.f$0;
                    if (z) {
                        seekController.getClass();
                        return;
                    }
                    Transition$TransitionNotification$$ExternalSyntheticLambda0 transition$TransitionNotification$$ExternalSyntheticLambda0 = Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_END;
                    Transition transition = Transition.this;
                    if (f12 >= 1.0f) {
                        transition.notifyFromTransition(transition, transition$TransitionNotification$$ExternalSyntheticLambda0, false);
                        return;
                    }
                    long j7 = transition.mTotalDuration;
                    Transition transitionAt = ((TransitionSet) transition).getTransitionAt(0);
                    Transition transition2 = transitionAt.mCloneParent;
                    transitionAt.mCloneParent = null;
                    transition.setCurrentPlayTimeMillis(-1L, seekController.mCurrentPlayTime);
                    transition.setCurrentPlayTimeMillis(j7, -1L);
                    seekController.mCurrentPlayTime = j7;
                    DefaultSpecialEffectsController$$ExternalSyntheticLambda0 defaultSpecialEffectsController$$ExternalSyntheticLambda0 = seekController.mResetToStartState;
                    if (defaultSpecialEffectsController$$ExternalSyntheticLambda0 != null) {
                        defaultSpecialEffectsController$$ExternalSyntheticLambda0.run();
                    }
                    transition.mAnimators.clear();
                    if (transition2 != null) {
                        transition2.notifyFromTransition(transition2, transition$TransitionNotification$$ExternalSyntheticLambda0, true);
                    }
                }
            });
        }

        @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
        public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
            Transition transition = Transition.this;
            long jMax = Math.max(-1L, Math.min(transition.mTotalDuration + 1, Math.round(f)));
            transition.setCurrentPlayTimeMillis(jMax, this.mCurrentPlayTime);
            this.mCurrentPlayTime = jMax;
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public final void onTransitionCancel(Transition transition) {
            this.mIsCanceled = true;
        }
    }

    public interface TransitionListener {
        void onTransitionCancel(Transition transition);

        void onTransitionEnd(Transition transition);

        void onTransitionPause();

        void onTransitionResume();

        void onTransitionStart(Transition transition);

        default void onTransitionStart$1(Transition transition) {
            onTransitionStart(transition);
        }
    }

    public Transition() {
        this.mName = getClass().getName();
        this.mStartDelay = -1L;
        this.mDuration = -1L;
        this.mInterpolator = null;
        this.mTargetIds = new ArrayList();
        this.mTargets = new ArrayList();
        this.mStartValues = new TransitionValuesMaps();
        this.mEndValues = new TransitionValuesMaps();
        this.mParent = null;
        this.mMatchOrder = DEFAULT_MATCH_ORDER;
        this.mCurrentAnimators = new ArrayList();
        this.mAnimatorCache = EMPTY_ANIMATOR_ARRAY;
        this.mNumInstances = 0;
        this.mPaused = false;
        this.mEnded = false;
        this.mCloneParent = null;
        this.mListeners = null;
        this.mAnimators = new ArrayList();
        this.mPathMotion = STRAIGHT_PATH_MOTION;
    }

    public static void addViewValues(TransitionValuesMaps transitionValuesMaps, View view, TransitionValues transitionValues) {
        transitionValuesMaps.mViewValues.put(view, transitionValues);
        int id = view.getId();
        if (id >= 0) {
            if (transitionValuesMaps.mIdValues.indexOfKey(id) >= 0) {
                transitionValuesMaps.mIdValues.put(id, null);
            } else {
                transitionValuesMaps.mIdValues.put(id, view);
            }
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        String transitionName = ViewCompat.Api21Impl.getTransitionName(view);
        if (transitionName != null) {
            ArrayMap arrayMap = transitionValuesMaps.mNameValues;
            if (arrayMap.containsKey(transitionName)) {
                arrayMap.put(transitionName, null);
            } else {
                arrayMap.put(transitionName, view);
            }
        }
        if (view.getParent() instanceof ListView) {
            ListView listView = (ListView) view.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view));
                LongSparseArray longSparseArray = transitionValuesMaps.mItemIdValues;
                if (longSparseArray.indexOfKey(itemIdAtPosition) < 0) {
                    view.setHasTransientState(true);
                    longSparseArray.put(itemIdAtPosition, view);
                    return;
                }
                View view2 = (View) longSparseArray.get(itemIdAtPosition);
                if (view2 != null) {
                    view2.setHasTransientState(false);
                    longSparseArray.put(itemIdAtPosition, null);
                }
            }
        }
    }

    public static ArrayMap getRunningAnimators() {
        ThreadLocal threadLocal = sRunningAnimators;
        ArrayMap arrayMap = (ArrayMap) threadLocal.get();
        if (arrayMap != null) {
            return arrayMap;
        }
        ArrayMap arrayMap2 = new ArrayMap();
        threadLocal.set(arrayMap2);
        return arrayMap2;
    }

    public void addListener(TransitionListener transitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(transitionListener);
    }

    public void addTarget(View view) {
        this.mTargets.add(view);
    }

    public void cancel() {
        int size = this.mCurrentAnimators.size();
        Animator[] animatorArr = (Animator[]) this.mCurrentAnimators.toArray(this.mAnimatorCache);
        this.mAnimatorCache = EMPTY_ANIMATOR_ARRAY;
        for (int i = size - 1; i >= 0; i--) {
            Animator animator = animatorArr[i];
            animatorArr[i] = null;
            animator.cancel();
        }
        this.mAnimatorCache = animatorArr;
        notifyFromTransition(this, Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_CANCEL, false);
    }

    public abstract void captureEndValues(TransitionValues transitionValues);

    public final void captureHierarchy(View view, boolean z) {
        if (view == null) {
            return;
        }
        view.getId();
        if (view.getParent() instanceof ViewGroup) {
            TransitionValues transitionValues = new TransitionValues(view);
            if (z) {
                captureStartValues(transitionValues);
            } else {
                captureEndValues(transitionValues);
            }
            transitionValues.mTargetedTransitions.add(this);
            capturePropagationValues(transitionValues);
            if (z) {
                addViewValues(this.mStartValues, view, transitionValues);
            } else {
                addViewValues(this.mEndValues, view, transitionValues);
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                captureHierarchy(viewGroup.getChildAt(i), z);
            }
        }
    }

    public abstract void captureStartValues(TransitionValues transitionValues);

    public final void captureValues(ViewGroup viewGroup, boolean z) {
        clearValues(z);
        if (this.mTargetIds.size() <= 0 && this.mTargets.size() <= 0) {
            captureHierarchy(viewGroup, z);
            return;
        }
        for (int i = 0; i < this.mTargetIds.size(); i++) {
            View viewFindViewById = viewGroup.findViewById(((Integer) this.mTargetIds.get(i)).intValue());
            if (viewFindViewById != null) {
                TransitionValues transitionValues = new TransitionValues(viewFindViewById);
                if (z) {
                    captureStartValues(transitionValues);
                } else {
                    captureEndValues(transitionValues);
                }
                transitionValues.mTargetedTransitions.add(this);
                capturePropagationValues(transitionValues);
                if (z) {
                    addViewValues(this.mStartValues, viewFindViewById, transitionValues);
                } else {
                    addViewValues(this.mEndValues, viewFindViewById, transitionValues);
                }
            }
        }
        for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
            View view = (View) this.mTargets.get(i2);
            TransitionValues transitionValues2 = new TransitionValues(view);
            if (z) {
                captureStartValues(transitionValues2);
            } else {
                captureEndValues(transitionValues2);
            }
            transitionValues2.mTargetedTransitions.add(this);
            capturePropagationValues(transitionValues2);
            if (z) {
                addViewValues(this.mStartValues, view, transitionValues2);
            } else {
                addViewValues(this.mEndValues, view, transitionValues2);
            }
        }
    }

    public final void clearValues(boolean z) {
        if (z) {
            this.mStartValues.mViewValues.clear();
            this.mStartValues.mIdValues.clear();
            this.mStartValues.mItemIdValues.clear();
        } else {
            this.mEndValues.mViewValues.clear();
            this.mEndValues.mIdValues.clear();
            this.mEndValues.mItemIdValues.clear();
        }
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2, ArrayList arrayList, ArrayList arrayList2) {
        View view;
        TransitionValues transitionValues;
        Animator animator;
        View view2;
        Animator animator2;
        ArrayMap runningAnimators = getRunningAnimators();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int size = arrayList.size();
        boolean z = getRootTransition().mSeekController != null;
        for (int i = 0; i < size; i++) {
            TransitionValues transitionValues2 = (TransitionValues) arrayList.get(i);
            TransitionValues transitionValues3 = (TransitionValues) arrayList2.get(i);
            if (transitionValues2 != null && !transitionValues2.mTargetedTransitions.contains(this)) {
                transitionValues2 = null;
            }
            if (transitionValues3 != null && !transitionValues3.mTargetedTransitions.contains(this)) {
                transitionValues3 = null;
            }
            if ((transitionValues2 != null || transitionValues3 != null) && (transitionValues2 == null || transitionValues3 == null || isTransitionRequired(transitionValues2, transitionValues3))) {
                Animator animatorCreateAnimator = createAnimator(viewGroup, transitionValues2, transitionValues3);
                if (animatorCreateAnimator != null) {
                    if (transitionValues3 != null) {
                        View view3 = transitionValues3.view;
                        String[] transitionProperties = getTransitionProperties();
                        if (transitionProperties != null && transitionProperties.length > 0) {
                            transitionValues = new TransitionValues(view3);
                            TransitionValues transitionValues4 = (TransitionValues) transitionValuesMaps2.mViewValues.get(view3);
                            if (transitionValues4 != null) {
                                int i2 = 0;
                                while (i2 < transitionProperties.length) {
                                    Map map = transitionValues.values;
                                    String[] strArr = transitionProperties;
                                    String str = strArr[i2];
                                    ((HashMap) map).put(str, ((HashMap) transitionValues4.values).get(str));
                                    i2++;
                                    transitionProperties = strArr;
                                    transitionValues4 = transitionValues4;
                                }
                            }
                            int i3 = runningAnimators.size;
                            int i4 = 0;
                            while (true) {
                                if (i4 >= i3) {
                                    view2 = view3;
                                    animator2 = animatorCreateAnimator;
                                    break;
                                }
                                AnimationInfo animationInfo = (AnimationInfo) runningAnimators.get((Animator) runningAnimators.keyAt(i4));
                                if (animationInfo.mValues != null && animationInfo.mView == view3) {
                                    view2 = view3;
                                    if (animationInfo.mName.equals(this.mName) && animationInfo.mValues.equals(transitionValues)) {
                                        animator2 = null;
                                        break;
                                    }
                                } else {
                                    view2 = view3;
                                }
                                i4++;
                                view3 = view2;
                            }
                        } else {
                            view2 = view3;
                            animator2 = animatorCreateAnimator;
                            transitionValues = null;
                        }
                        animatorCreateAnimator = animator2;
                        view = view2;
                    } else {
                        view = transitionValues2.view;
                        transitionValues = null;
                    }
                    if (animatorCreateAnimator != null) {
                        Animator animator3 = animatorCreateAnimator;
                        AnimationInfo animationInfo2 = new AnimationInfo(view, this.mName, this, viewGroup.getWindowId(), transitionValues, animator3);
                        if (z) {
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.play(animator3);
                            animator = animatorSet;
                        } else {
                            animator = animator3;
                        }
                        runningAnimators.put(animator, animationInfo2);
                        this.mAnimators.add(animator);
                    }
                }
            }
        }
        if (sparseIntArray.size() != 0) {
            for (int i5 = 0; i5 < sparseIntArray.size(); i5++) {
                AnimationInfo animationInfo3 = (AnimationInfo) runningAnimators.get((Animator) this.mAnimators.get(sparseIntArray.keyAt(i5)));
                animationInfo3.mAnimator.setStartDelay(animationInfo3.mAnimator.getStartDelay() + (sparseIntArray.valueAt(i5) - Long.MAX_VALUE));
            }
        }
    }

    public final void end() {
        int i = this.mNumInstances - 1;
        this.mNumInstances = i;
        if (i == 0) {
            notifyFromTransition(this, Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_END, false);
            for (int i2 = 0; i2 < this.mStartValues.mItemIdValues.size(); i2++) {
                View view = (View) this.mStartValues.mItemIdValues.valueAt(i2);
                if (view != null) {
                    view.setHasTransientState(false);
                }
            }
            for (int i3 = 0; i3 < this.mEndValues.mItemIdValues.size(); i3++) {
                View view2 = (View) this.mEndValues.mItemIdValues.valueAt(i3);
                if (view2 != null) {
                    view2.setHasTransientState(false);
                }
            }
            this.mEnded = true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x002c, code lost:
    
        if (r2 < 0) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x002e, code lost:
    
        if (r6 == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0030, code lost:
    
        r4 = r4.mEndValuesList;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0033, code lost:
    
        r4 = r4.mStartValuesList;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x003b, code lost:
    
        return (androidx.transition.TransitionValues) r4.get(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x003c, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final TransitionValues getMatchedTransitionValues(View view, boolean z) {
        TransitionSet transitionSet = this.mParent;
        if (transitionSet != null) {
            return transitionSet.getMatchedTransitionValues(view, z);
        }
        ArrayList arrayList = z ? this.mStartValuesList : this.mEndValuesList;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            }
            TransitionValues transitionValues = (TransitionValues) arrayList.get(i);
            if (transitionValues == null) {
                return null;
            }
            if (transitionValues.view == view) {
                break;
            }
            i++;
        }
    }

    public final Transition getRootTransition() {
        TransitionSet transitionSet = this.mParent;
        return transitionSet != null ? transitionSet.getRootTransition() : this;
    }

    public String[] getTransitionProperties() {
        return null;
    }

    public final TransitionValues getTransitionValues(View view, boolean z) {
        TransitionSet transitionSet = this.mParent;
        if (transitionSet != null) {
            return transitionSet.getTransitionValues(view, z);
        }
        return (TransitionValues) (z ? this.mStartValues : this.mEndValues).mViewValues.get(view);
    }

    public boolean hasAnimators() {
        return !this.mCurrentAnimators.isEmpty();
    }

    public boolean isSeekingSupported() {
        return this instanceof ChangeBounds;
    }

    public boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues != null && transitionValues2 != null) {
            String[] transitionProperties = getTransitionProperties();
            if (transitionProperties != null) {
                for (String str : transitionProperties) {
                    Object obj = ((HashMap) transitionValues.values).get(str);
                    Object obj2 = ((HashMap) transitionValues2.values).get(str);
                    if ((obj == null && obj2 == null) ? false : (obj == null || obj2 == null) ? true : !obj.equals(obj2)) {
                        return true;
                    }
                }
            } else {
                for (String str2 : ((HashMap) transitionValues.values).keySet()) {
                    Object obj3 = ((HashMap) transitionValues.values).get(str2);
                    Object obj4 = ((HashMap) transitionValues2.values).get(str2);
                    if ((obj3 == null && obj4 == null) ? false : (obj3 == null || obj4 == null) ? true : !obj3.equals(obj4)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final boolean isValidTarget(View view) {
        return (this.mTargetIds.size() == 0 && this.mTargets.size() == 0) || this.mTargetIds.contains(Integer.valueOf(view.getId())) || this.mTargets.contains(view);
    }

    public final void notifyFromTransition(Transition transition, Transition$TransitionNotification$$ExternalSyntheticLambda0 transition$TransitionNotification$$ExternalSyntheticLambda0, boolean z) {
        Transition transition2 = this.mCloneParent;
        if (transition2 != null) {
            transition2.notifyFromTransition(transition, transition$TransitionNotification$$ExternalSyntheticLambda0, z);
        }
        ArrayList arrayList = this.mListeners;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        int size = this.mListeners.size();
        TransitionListener[] transitionListenerArr = this.mListenersCache;
        if (transitionListenerArr == null) {
            transitionListenerArr = new TransitionListener[size];
        }
        this.mListenersCache = null;
        TransitionListener[] transitionListenerArr2 = (TransitionListener[]) this.mListeners.toArray(transitionListenerArr);
        for (int i = 0; i < size; i++) {
            TransitionListener transitionListener = transitionListenerArr2[i];
            switch (transition$TransitionNotification$$ExternalSyntheticLambda0.$r8$classId) {
                case 0:
                    transitionListener.onTransitionStart$1(transition);
                    break;
                case 1:
                    transitionListener.onTransitionEnd(transition);
                    break;
                case 2:
                    transitionListener.onTransitionCancel(transition);
                    break;
                case 3:
                    transitionListener.onTransitionPause();
                    break;
                default:
                    transitionListener.onTransitionResume();
                    break;
            }
            transitionListenerArr2[i] = null;
        }
        this.mListenersCache = transitionListenerArr2;
    }

    public void pause(View view) {
        if (this.mEnded) {
            return;
        }
        int size = this.mCurrentAnimators.size();
        Animator[] animatorArr = (Animator[]) this.mCurrentAnimators.toArray(this.mAnimatorCache);
        this.mAnimatorCache = EMPTY_ANIMATOR_ARRAY;
        for (int i = size - 1; i >= 0; i--) {
            Animator animator = animatorArr[i];
            animatorArr[i] = null;
            animator.pause();
        }
        this.mAnimatorCache = animatorArr;
        notifyFromTransition(this, Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_PAUSE, false);
        this.mPaused = true;
    }

    public void prepareAnimatorsForSeeking() {
        ArrayMap runningAnimators = getRunningAnimators();
        this.mTotalDuration = 0L;
        for (int i = 0; i < this.mAnimators.size(); i++) {
            Animator animator = (Animator) this.mAnimators.get(i);
            AnimationInfo animationInfo = (AnimationInfo) runningAnimators.get(animator);
            if (animator != null && animationInfo != null) {
                long j = this.mDuration;
                if (j >= 0) {
                    animationInfo.mAnimator.setDuration(j);
                }
                long j2 = this.mStartDelay;
                if (j2 >= 0) {
                    Animator animator2 = animationInfo.mAnimator;
                    animator2.setStartDelay(animator2.getStartDelay() + j2);
                }
                TimeInterpolator timeInterpolator = this.mInterpolator;
                if (timeInterpolator != null) {
                    animationInfo.mAnimator.setInterpolator(timeInterpolator);
                }
                this.mCurrentAnimators.add(animator);
                this.mTotalDuration = Math.max(this.mTotalDuration, Impl26.getTotalDuration(animator));
            }
        }
        this.mAnimators.clear();
    }

    public Transition removeListener(TransitionListener transitionListener) {
        Transition transition;
        ArrayList arrayList = this.mListeners;
        if (arrayList != null) {
            if (!arrayList.remove(transitionListener) && (transition = this.mCloneParent) != null) {
                transition.removeListener(transitionListener);
            }
            if (this.mListeners.size() == 0) {
                this.mListeners = null;
            }
        }
        return this;
    }

    public void removeTarget(View view) {
        this.mTargets.remove(view);
    }

    public void resume(View view) {
        if (this.mPaused) {
            if (!this.mEnded) {
                int size = this.mCurrentAnimators.size();
                Animator[] animatorArr = (Animator[]) this.mCurrentAnimators.toArray(this.mAnimatorCache);
                this.mAnimatorCache = EMPTY_ANIMATOR_ARRAY;
                for (int i = size - 1; i >= 0; i--) {
                    Animator animator = animatorArr[i];
                    animatorArr[i] = null;
                    animator.resume();
                }
                this.mAnimatorCache = animatorArr;
                notifyFromTransition(this, Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_RESUME, false);
            }
            this.mPaused = false;
        }
    }

    public void runAnimators() {
        start();
        final ArrayMap runningAnimators = getRunningAnimators();
        ArrayList arrayList = this.mAnimators;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Animator animator = (Animator) obj;
            if (runningAnimators.containsKey(animator)) {
                start();
                if (animator != null) {
                    animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.transition.Transition.2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator2) {
                            runningAnimators.remove(animator2);
                            Transition.this.mCurrentAnimators.remove(animator2);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator2) {
                            Transition.this.mCurrentAnimators.add(animator2);
                        }
                    });
                    long j = this.mDuration;
                    if (j >= 0) {
                        animator.setDuration(j);
                    }
                    long j2 = this.mStartDelay;
                    if (j2 >= 0) {
                        animator.setStartDelay(animator.getStartDelay() + j2);
                    }
                    TimeInterpolator timeInterpolator = this.mInterpolator;
                    if (timeInterpolator != null) {
                        animator.setInterpolator(timeInterpolator);
                    }
                    animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.transition.Transition.3
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator2) {
                            Transition.this.end();
                            animator2.removeListener(this);
                        }
                    });
                    animator.start();
                }
            }
        }
        this.mAnimators.clear();
        end();
    }

    public void setCurrentPlayTimeMillis(long j, long j2) {
        long j3 = this.mTotalDuration;
        boolean z = j < j2;
        if ((j2 < 0 && j >= 0) || (j2 > j3 && j <= j3)) {
            this.mEnded = false;
            notifyFromTransition(this, Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_START, z);
        }
        int size = this.mCurrentAnimators.size();
        Animator[] animatorArr = (Animator[]) this.mCurrentAnimators.toArray(this.mAnimatorCache);
        this.mAnimatorCache = EMPTY_ANIMATOR_ARRAY;
        for (int i = 0; i < size; i++) {
            Animator animator = animatorArr[i];
            animatorArr[i] = null;
            Impl26.setCurrentPlayTime(animator, Math.min(Math.max(0L, j), Impl26.getTotalDuration(animator)));
        }
        this.mAnimatorCache = animatorArr;
        if ((j <= j3 || j2 > j3) && (j >= 0 || j2 < 0)) {
            return;
        }
        if (j > j3) {
            this.mEnded = true;
        }
        notifyFromTransition(this, Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_END, z);
    }

    public void setDuration(long j) {
        this.mDuration = j;
    }

    public void setEpicenterCallback(EpicenterCallback epicenterCallback) {
        this.mEpicenterCallback = epicenterCallback;
    }

    public void setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
    }

    public void setPathMotion(AnonymousClass1 anonymousClass1) {
        if (anonymousClass1 == null) {
            this.mPathMotion = STRAIGHT_PATH_MOTION;
        } else {
            this.mPathMotion = anonymousClass1;
        }
    }

    public void setStartDelay(long j) {
        this.mStartDelay = j;
    }

    public final void start() {
        if (this.mNumInstances == 0) {
            notifyFromTransition(this, Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_START, false);
            this.mEnded = false;
        }
        this.mNumInstances++;
    }

    public final String toString() {
        return toString("");
    }

    @Override // 
    /* renamed from: clone */
    public Transition mo900clone() {
        try {
            Transition transition = (Transition) super.clone();
            transition.mAnimators = new ArrayList();
            transition.mStartValues = new TransitionValuesMaps();
            transition.mEndValues = new TransitionValuesMaps();
            transition.mStartValuesList = null;
            transition.mEndValuesList = null;
            transition.mSeekController = null;
            transition.mCloneParent = this;
            transition.mListeners = null;
            return transition;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(getClass().getSimpleName());
        sb.append("@");
        sb.append(Integer.toHexString(hashCode()));
        sb.append(": ");
        if (this.mDuration != -1) {
            sb.append("dur(");
            sb.append(this.mDuration);
            sb.append(") ");
        }
        if (this.mStartDelay != -1) {
            sb.append("dly(");
            sb.append(this.mStartDelay);
            sb.append(") ");
        }
        if (this.mInterpolator != null) {
            sb.append("interp(");
            sb.append(this.mInterpolator);
            sb.append(") ");
        }
        if (this.mTargetIds.size() > 0 || this.mTargets.size() > 0) {
            sb.append("tgts(");
            if (this.mTargetIds.size() > 0) {
                for (int i = 0; i < this.mTargetIds.size(); i++) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(this.mTargetIds.get(i));
                }
            }
            if (this.mTargets.size() > 0) {
                for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                    if (i2 > 0) {
                        sb.append(", ");
                    }
                    sb.append(this.mTargets.get(i2));
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }

    public Transition(Context context, AttributeSet attributeSet) {
        this.mName = getClass().getName();
        this.mStartDelay = -1L;
        this.mDuration = -1L;
        this.mInterpolator = null;
        this.mTargetIds = new ArrayList();
        this.mTargets = new ArrayList();
        this.mStartValues = new TransitionValuesMaps();
        this.mEndValues = new TransitionValuesMaps();
        this.mParent = null;
        int[] iArr = DEFAULT_MATCH_ORDER;
        this.mMatchOrder = iArr;
        this.mCurrentAnimators = new ArrayList();
        this.mAnimatorCache = EMPTY_ANIMATOR_ARRAY;
        this.mNumInstances = 0;
        this.mPaused = false;
        this.mEnded = false;
        this.mCloneParent = null;
        this.mListeners = null;
        this.mAnimators = new ArrayList();
        this.mPathMotion = STRAIGHT_PATH_MOTION;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.TRANSITION);
        XmlResourceParser xmlResourceParser = (XmlResourceParser) attributeSet;
        long namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainStyledAttributes, xmlResourceParser, "duration", 1, -1);
        if (namedInt >= 0) {
            setDuration(namedInt);
        }
        long j = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "startDelay") != null ? typedArrayObtainStyledAttributes.getInt(2, -1) : -1;
        if (j > 0) {
            setStartDelay(j);
        }
        int resourceId = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "interpolator") != null ? typedArrayObtainStyledAttributes.getResourceId(0, 0) : 0;
        if (resourceId > 0) {
            setInterpolator(AnimationUtils.loadInterpolator(context, resourceId));
        }
        String namedString = TypedArrayUtils.getNamedString(typedArrayObtainStyledAttributes, xmlResourceParser, "matchOrder", 3);
        if (namedString != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(namedString, ",");
            int[] iArr2 = new int[stringTokenizer.countTokens()];
            int i = 0;
            while (stringTokenizer.hasMoreTokens()) {
                String strTrim = stringTokenizer.nextToken().trim();
                if ("id".equalsIgnoreCase(strTrim)) {
                    iArr2[i] = 3;
                } else if ("instance".equalsIgnoreCase(strTrim)) {
                    iArr2[i] = 1;
                } else if ("name".equalsIgnoreCase(strTrim)) {
                    iArr2[i] = 2;
                } else if ("itemId".equalsIgnoreCase(strTrim)) {
                    iArr2[i] = 4;
                } else if (strTrim.isEmpty()) {
                    int[] iArr3 = new int[iArr2.length - 1];
                    System.arraycopy(iArr2, 0, iArr3, 0, i);
                    i--;
                    iArr2 = iArr3;
                } else {
                    throw new InflateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Unknown match type in matchOrder: '", strTrim, "'"));
                }
                i++;
            }
            if (iArr2.length == 0) {
                this.mMatchOrder = iArr;
            } else {
                for (int i2 = 0; i2 < iArr2.length; i2++) {
                    int i3 = iArr2[i2];
                    if (i3 < 1 || i3 > 4) {
                        throw new IllegalArgumentException("matches contains invalid value");
                    }
                    for (int i4 = 0; i4 < i2; i4++) {
                        if (iArr2[i4] == i3) {
                            throw new IllegalArgumentException("matches contains a duplicate value");
                        }
                    }
                }
                this.mMatchOrder = (int[]) iArr2.clone();
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setPropagation() {
    }

    public void capturePropagationValues(TransitionValues transitionValues) {
    }
}
