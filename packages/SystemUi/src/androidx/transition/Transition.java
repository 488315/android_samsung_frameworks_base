package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class Transition implements Cloneable {
    public static final int[] DEFAULT_MATCH_ORDER = {2, 1, 3, 4};
    public static final C05481 STRAIGHT_PATH_MOTION = new PathMotion() { // from class: androidx.transition.Transition.1
        @Override // androidx.transition.PathMotion
        public final Path getPath(float f, float f2, float f3, float f4) {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            return path;
        }
    };
    public static final ThreadLocal sRunningAnimators = new ThreadLocal();
    public ArrayList mAnimators;
    public final ArrayList mCurrentAnimators;
    public long mDuration;
    public TransitionValuesMaps mEndValues;
    public ArrayList mEndValuesList;
    public boolean mEnded;
    public EpicenterCallback mEpicenterCallback;
    public TimeInterpolator mInterpolator;
    public ArrayList mListeners;
    public int[] mMatchOrder;
    public final String mName;
    public int mNumInstances;
    public TransitionSet mParent;
    public PathMotion mPathMotion;
    public boolean mPaused;
    public TransitionPropagation mPropagation;
    public long mStartDelay;
    public TransitionValuesMaps mStartValues;
    public ArrayList mStartValuesList;
    public final ArrayList mTargetIds;
    public final ArrayList mTargets;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimationInfo {
        public final String mName;
        public final Transition mTransition;
        public final TransitionValues mValues;
        public final View mView;
        public final WindowIdImpl mWindowId;

        public AnimationInfo(View view, String str, Transition transition, WindowIdImpl windowIdImpl, TransitionValues transitionValues) {
            this.mView = view;
            this.mName = str;
            this.mValues = transitionValues;
            this.mWindowId = windowIdImpl;
            this.mTransition = transition;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class EpicenterCallback {
        public abstract Rect onGetEpicenter();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TransitionListener {
        void onTransitionCancel();

        void onTransitionEnd(Transition transition);

        void onTransitionPause();

        void onTransitionResume();

        void onTransitionStart(Transition transition);
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
        this.mNumInstances = 0;
        this.mPaused = false;
        this.mEnded = false;
        this.mListeners = null;
        this.mAnimators = new ArrayList();
        this.mPathMotion = STRAIGHT_PATH_MOTION;
    }

    public static void addViewValues(TransitionValuesMaps transitionValuesMaps, View view, TransitionValues transitionValues) {
        transitionValuesMaps.mViewValues.put(view, transitionValues);
        int id = view.getId();
        if (id >= 0) {
            SparseArray sparseArray = transitionValuesMaps.mIdValues;
            if (sparseArray.indexOfKey(id) >= 0) {
                sparseArray.put(id, null);
            } else {
                sparseArray.put(id, view);
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
                    ViewCompat.Api16Impl.setHasTransientState(view, true);
                    longSparseArray.put(itemIdAtPosition, view);
                    return;
                }
                View view2 = (View) longSparseArray.get(itemIdAtPosition);
                if (view2 != null) {
                    ViewCompat.Api16Impl.setHasTransientState(view2, false);
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

    public static boolean isValueChanged(TransitionValues transitionValues, TransitionValues transitionValues2, String str) {
        Object obj = transitionValues.values.get(str);
        Object obj2 = transitionValues2.values.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return !obj.equals(obj2);
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
        while (true) {
            size--;
            if (size < 0) {
                break;
            } else {
                ((Animator) this.mCurrentAnimators.get(size)).cancel();
            }
        }
        ArrayList arrayList = this.mListeners;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
        int size2 = arrayList2.size();
        for (int i = 0; i < size2; i++) {
            ((TransitionListener) arrayList2.get(i)).onTransitionCancel();
        }
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

    public void capturePropagationValues(TransitionValues transitionValues) {
        if (this.mPropagation != null) {
            Map map = transitionValues.values;
            if (((HashMap) map).isEmpty()) {
                return;
            }
            this.mPropagation.getPropagationProperties();
            String[] strArr = VisibilityPropagation.VISIBILITY_PROPAGATION_VALUES;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= 2) {
                    z = true;
                    break;
                }
                if (!((HashMap) map).containsKey(strArr[i])) {
                    break;
                } else {
                    i++;
                }
            }
            if (z) {
                return;
            }
            this.mPropagation.captureValues(transitionValues);
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
            View findViewById = viewGroup.findViewById(((Integer) this.mTargetIds.get(i)).intValue());
            if (findViewById != null) {
                TransitionValues transitionValues = new TransitionValues(findViewById);
                if (z) {
                    captureStartValues(transitionValues);
                } else {
                    captureEndValues(transitionValues);
                }
                transitionValues.mTargetedTransitions.add(this);
                capturePropagationValues(transitionValues);
                if (z) {
                    addViewValues(this.mStartValues, findViewById, transitionValues);
                } else {
                    addViewValues(this.mEndValues, findViewById, transitionValues);
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
        Animator createAnimator;
        int i;
        View view;
        Animator animator;
        TransitionValues transitionValues;
        Animator animator2;
        TransitionValues transitionValues2;
        ArrayMap runningAnimators = getRunningAnimators();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int size = arrayList.size();
        long j = Long.MAX_VALUE;
        int i2 = 0;
        while (i2 < size) {
            TransitionValues transitionValues3 = (TransitionValues) arrayList.get(i2);
            TransitionValues transitionValues4 = (TransitionValues) arrayList2.get(i2);
            if (transitionValues3 != null && !transitionValues3.mTargetedTransitions.contains(this)) {
                transitionValues3 = null;
            }
            if (transitionValues4 != null && !transitionValues4.mTargetedTransitions.contains(this)) {
                transitionValues4 = null;
            }
            if (transitionValues3 != null || transitionValues4 != null) {
                if ((transitionValues3 == null || transitionValues4 == null || isTransitionRequired(transitionValues3, transitionValues4)) && (createAnimator = createAnimator(viewGroup, transitionValues3, transitionValues4)) != null) {
                    if (transitionValues4 != null) {
                        String[] transitionProperties = getTransitionProperties();
                        view = transitionValues4.view;
                        if (transitionProperties != null && transitionProperties.length > 0) {
                            transitionValues2 = new TransitionValues(view);
                            animator2 = createAnimator;
                            i = size;
                            TransitionValues transitionValues5 = (TransitionValues) transitionValuesMaps2.mViewValues.get(view);
                            if (transitionValues5 != null) {
                                int i3 = 0;
                                while (i3 < transitionProperties.length) {
                                    Map map = transitionValues2.values;
                                    String str = transitionProperties[i3];
                                    ((HashMap) map).put(str, ((HashMap) transitionValues5.values).get(str));
                                    i3++;
                                    transitionProperties = transitionProperties;
                                }
                            }
                            int i4 = runningAnimators.size;
                            int i5 = 0;
                            while (true) {
                                if (i5 >= i4) {
                                    break;
                                }
                                AnimationInfo animationInfo = (AnimationInfo) runningAnimators.get((Animator) runningAnimators.keyAt(i5));
                                if (animationInfo.mValues != null && animationInfo.mView == view) {
                                    if (animationInfo.mName.equals(this.mName) && animationInfo.mValues.equals(transitionValues2)) {
                                        animator2 = null;
                                        break;
                                    }
                                }
                                i5++;
                            }
                        } else {
                            animator2 = createAnimator;
                            i = size;
                            transitionValues2 = null;
                        }
                        transitionValues = transitionValues2;
                        animator = animator2;
                    } else {
                        i = size;
                        view = transitionValues3.view;
                        animator = createAnimator;
                        transitionValues = null;
                    }
                    if (animator != null) {
                        TransitionPropagation transitionPropagation = this.mPropagation;
                        if (transitionPropagation != null) {
                            long startDelay = transitionPropagation.getStartDelay(viewGroup, this, transitionValues3, transitionValues4);
                            sparseIntArray.put(this.mAnimators.size(), (int) startDelay);
                            j = Math.min(startDelay, j);
                        }
                        long j2 = j;
                        String str2 = this.mName;
                        ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
                        runningAnimators.put(animator, new AnimationInfo(view, str2, this, new WindowIdApi18(viewGroup), transitionValues));
                        this.mAnimators.add(animator);
                        j = j2;
                    }
                    i2++;
                    size = i;
                }
            }
            i = size;
            i2++;
            size = i;
        }
        if (sparseIntArray.size() != 0) {
            for (int i6 = 0; i6 < sparseIntArray.size(); i6++) {
                Animator animator3 = (Animator) this.mAnimators.get(sparseIntArray.keyAt(i6));
                animator3.setStartDelay(animator3.getStartDelay() + (sparseIntArray.valueAt(i6) - j));
            }
        }
    }

    public final void end() {
        int i = this.mNumInstances - 1;
        this.mNumInstances = i;
        if (i == 0) {
            ArrayList arrayList = this.mListeners;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
                int size = arrayList2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((TransitionListener) arrayList2.get(i2)).onTransitionEnd(this);
                }
            }
            for (int i3 = 0; i3 < this.mStartValues.mItemIdValues.size(); i3++) {
                View view = (View) this.mStartValues.mItemIdValues.valueAt(i3);
                if (view != null) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.setHasTransientState(view, false);
                }
            }
            for (int i4 = 0; i4 < this.mEndValues.mItemIdValues.size(); i4++) {
                View view2 = (View) this.mEndValues.mItemIdValues.valueAt(i4);
                if (view2 != null) {
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.setHasTransientState(view2, false);
                }
            }
            this.mEnded = true;
        }
    }

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
        if (i >= 0) {
            return (TransitionValues) (z ? this.mEndValuesList : this.mStartValuesList).get(i);
        }
        return null;
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

    public boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return false;
        }
        String[] transitionProperties = getTransitionProperties();
        if (transitionProperties == null) {
            Iterator it = ((HashMap) transitionValues.values).keySet().iterator();
            while (it.hasNext()) {
                if (isValueChanged(transitionValues, transitionValues2, (String) it.next())) {
                }
            }
            return false;
        }
        for (String str : transitionProperties) {
            if (!isValueChanged(transitionValues, transitionValues2, str)) {
            }
        }
        return false;
        return true;
    }

    public final boolean isValidTarget(View view) {
        return (this.mTargetIds.size() == 0 && this.mTargets.size() == 0) || this.mTargetIds.contains(Integer.valueOf(view.getId())) || this.mTargets.contains(view);
    }

    public void pause(View view) {
        if (this.mEnded) {
            return;
        }
        for (int size = this.mCurrentAnimators.size() - 1; size >= 0; size--) {
            ((Animator) this.mCurrentAnimators.get(size)).pause();
        }
        ArrayList arrayList = this.mListeners;
        if (arrayList != null && arrayList.size() > 0) {
            ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
            int size2 = arrayList2.size();
            for (int i = 0; i < size2; i++) {
                ((TransitionListener) arrayList2.get(i)).onTransitionPause();
            }
        }
        this.mPaused = true;
    }

    public void removeListener(TransitionListener transitionListener) {
        ArrayList arrayList = this.mListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(transitionListener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
    }

    public void removeTarget(View view) {
        this.mTargets.remove(view);
    }

    public void resume(View view) {
        if (this.mPaused) {
            if (!this.mEnded) {
                int size = this.mCurrentAnimators.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    } else {
                        ((Animator) this.mCurrentAnimators.get(size)).resume();
                    }
                }
                ArrayList arrayList = this.mListeners;
                if (arrayList != null && arrayList.size() > 0) {
                    ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
                    int size2 = arrayList2.size();
                    for (int i = 0; i < size2; i++) {
                        ((TransitionListener) arrayList2.get(i)).onTransitionResume();
                    }
                }
            }
            this.mPaused = false;
        }
    }

    public void runAnimators() {
        start();
        final ArrayMap runningAnimators = getRunningAnimators();
        Iterator it = this.mAnimators.iterator();
        while (it.hasNext()) {
            Animator animator = (Animator) it.next();
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

    public void setDuration(long j) {
        this.mDuration = j;
    }

    public void setEpicenterCallback(EpicenterCallback epicenterCallback) {
        this.mEpicenterCallback = epicenterCallback;
    }

    public void setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
    }

    public void setPathMotion(PathMotion pathMotion) {
        if (pathMotion == null) {
            this.mPathMotion = STRAIGHT_PATH_MOTION;
        } else {
            this.mPathMotion = pathMotion;
        }
    }

    public void setPropagation(TransitionPropagation transitionPropagation) {
        this.mPropagation = transitionPropagation;
    }

    public void setStartDelay(long j) {
        this.mStartDelay = j;
    }

    public final void start() {
        if (this.mNumInstances == 0) {
            ArrayList arrayList = this.mListeners;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    ((TransitionListener) arrayList2.get(i)).onTransitionStart(this);
                }
            }
            this.mEnded = false;
        }
        this.mNumInstances++;
    }

    public String toString(String str) {
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
        m18m.append(getClass().getSimpleName());
        m18m.append("@");
        m18m.append(Integer.toHexString(hashCode()));
        m18m.append(": ");
        String sb = m18m.toString();
        if (this.mDuration != -1) {
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(sb, "dur(");
            m2m.append(this.mDuration);
            m2m.append(") ");
            sb = m2m.toString();
        }
        if (this.mStartDelay != -1) {
            StringBuilder m2m2 = AbstractC0000x2c234b15.m2m(sb, "dly(");
            m2m2.append(this.mStartDelay);
            m2m2.append(") ");
            sb = m2m2.toString();
        }
        if (this.mInterpolator != null) {
            StringBuilder m2m3 = AbstractC0000x2c234b15.m2m(sb, "interp(");
            m2m3.append(this.mInterpolator);
            m2m3.append(") ");
            sb = m2m3.toString();
        }
        if (this.mTargetIds.size() <= 0 && this.mTargets.size() <= 0) {
            return sb;
        }
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(sb, "tgts(");
        if (this.mTargetIds.size() > 0) {
            for (int i = 0; i < this.mTargetIds.size(); i++) {
                if (i > 0) {
                    m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, ", ");
                }
                StringBuilder m18m2 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(m14m);
                m18m2.append(this.mTargetIds.get(i));
                m14m = m18m2.toString();
            }
        }
        if (this.mTargets.size() > 0) {
            for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                if (i2 > 0) {
                    m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, ", ");
                }
                StringBuilder m18m3 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(m14m);
                m18m3.append(this.mTargets.get(i2));
                m14m = m18m3.toString();
            }
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, ")");
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Transition mo348clone() {
        try {
            Transition transition = (Transition) super.clone();
            transition.mAnimators = new ArrayList();
            transition.mStartValues = new TransitionValuesMaps();
            transition.mEndValues = new TransitionValuesMaps();
            transition.mStartValuesList = null;
            transition.mEndValuesList = null;
            return transition;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public Transition(Context context, AttributeSet attributeSet) {
        boolean z;
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
        this.mNumInstances = 0;
        this.mPaused = false;
        this.mEnded = false;
        this.mListeners = null;
        this.mAnimators = new ArrayList();
        this.mPathMotion = STRAIGHT_PATH_MOTION;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.TRANSITION);
        XmlResourceParser xmlResourceParser = (XmlResourceParser) attributeSet;
        long namedInt = TypedArrayUtils.getNamedInt(obtainStyledAttributes, xmlResourceParser, "duration", 1, -1);
        if (namedInt >= 0) {
            setDuration(namedInt);
        }
        long namedInt2 = TypedArrayUtils.getNamedInt(obtainStyledAttributes, xmlResourceParser, "startDelay", 2, -1);
        if (namedInt2 > 0) {
            setStartDelay(namedInt2);
        }
        int resourceId = !TypedArrayUtils.hasAttribute(xmlResourceParser, "interpolator") ? 0 : obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            setInterpolator(AnimationUtils.loadInterpolator(context, resourceId));
        }
        String namedString = TypedArrayUtils.getNamedString(obtainStyledAttributes, xmlResourceParser, "matchOrder", 3);
        if (namedString != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(namedString, ",");
            int[] iArr2 = new int[stringTokenizer.countTokens()];
            int i = 0;
            while (stringTokenizer.hasMoreTokens()) {
                String trim = stringTokenizer.nextToken().trim();
                if ("id".equalsIgnoreCase(trim)) {
                    iArr2[i] = 3;
                } else if ("instance".equalsIgnoreCase(trim)) {
                    iArr2[i] = 1;
                } else if ("name".equalsIgnoreCase(trim)) {
                    iArr2[i] = 2;
                } else if ("itemId".equalsIgnoreCase(trim)) {
                    iArr2[i] = 4;
                } else if (trim.isEmpty()) {
                    int[] iArr3 = new int[iArr2.length - 1];
                    System.arraycopy(iArr2, 0, iArr3, 0, i);
                    i--;
                    iArr2 = iArr3;
                } else {
                    throw new InflateException(PathParser$$ExternalSyntheticOutline0.m29m("Unknown match type in matchOrder: '", trim, "'"));
                }
                i++;
            }
            if (iArr2.length == 0) {
                this.mMatchOrder = iArr;
            } else {
                for (int i2 = 0; i2 < iArr2.length; i2++) {
                    int i3 = iArr2[i2];
                    if (!(i3 >= 1 && i3 <= 4)) {
                        throw new IllegalArgumentException("matches contains invalid value");
                    }
                    int i4 = 0;
                    while (true) {
                        if (i4 >= i2) {
                            z = false;
                            break;
                        } else {
                            if (iArr2[i4] == i3) {
                                z = true;
                                break;
                            }
                            i4++;
                        }
                    }
                    if (z) {
                        throw new IllegalArgumentException("matches contains a duplicate value");
                    }
                }
                this.mMatchOrder = (int[]) iArr2.clone();
            }
        }
        obtainStyledAttributes.recycle();
    }

    public final String toString() {
        return toString("");
    }
}
