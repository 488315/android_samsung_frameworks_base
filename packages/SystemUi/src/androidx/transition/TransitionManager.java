package androidx.transition;

import android.animation.Animator;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import androidx.core.view.ViewCompat;
import androidx.transition.Transition;
import com.android.systemui.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TransitionManager {
    public static final AutoTransition sDefaultTransition = new AutoTransition();
    public static final ThreadLocal sRunningTransitions = new ThreadLocal();
    public static final ArrayList sPendingTransitions = new ArrayList();

    public TransitionManager() {
        new ArrayMap();
        new ArrayMap();
    }

    public static void beginDelayedTransition(Transition transition, ViewGroup viewGroup) {
        ArrayList arrayList = sPendingTransitions;
        if (arrayList.contains(viewGroup)) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api19Impl.isLaidOut(viewGroup)) {
            arrayList.add(viewGroup);
            if (transition == null) {
                transition = sDefaultTransition;
            }
            Transition mo348clone = transition.mo348clone();
            ArrayList arrayList2 = (ArrayList) getRunningTransitions().get(viewGroup);
            if (arrayList2 != null && arrayList2.size() > 0) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    ((Transition) it.next()).pause(viewGroup);
                }
            }
            if (mo348clone != null) {
                mo348clone.captureValues(viewGroup, true);
            }
            Scene scene = (Scene) viewGroup.getTag(R.id.transition_current_scene);
            if (scene != null) {
            }
            viewGroup.setTag(R.id.transition_current_scene, null);
            if (mo348clone != null) {
                MultiListener multiListener = new MultiListener(mo348clone, viewGroup);
                viewGroup.addOnAttachStateChangeListener(multiListener);
                viewGroup.getViewTreeObserver().addOnPreDrawListener(multiListener);
            }
        }
    }

    public static ArrayMap getRunningTransitions() {
        ArrayMap arrayMap;
        ThreadLocal threadLocal = sRunningTransitions;
        WeakReference weakReference = (WeakReference) threadLocal.get();
        if (weakReference != null && (arrayMap = (ArrayMap) weakReference.get()) != null) {
            return arrayMap;
        }
        ArrayMap arrayMap2 = new ArrayMap();
        threadLocal.set(new WeakReference(arrayMap2));
        return arrayMap2;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MultiListener implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
        public final ViewGroup mSceneRoot;
        public final Transition mTransition;

        public MultiListener(Transition transition, ViewGroup viewGroup) {
            this.mTransition = transition;
            this.mSceneRoot = viewGroup;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x005b  */
        /* JADX WARN: Removed duplicated region for block: B:121:0x01d2 A[EDGE_INSN: B:121:0x01d2->B:122:0x01d2 BREAK  A[LOOP:1: B:17:0x0099->B:50:0x01cc], SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:125:0x01d7  */
        /* JADX WARN: Removed duplicated region for block: B:135:0x01f8  */
        /* JADX WARN: Removed duplicated region for block: B:145:0x0227  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x009e  */
        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean onPreDraw() {
            ArrayList arrayList;
            int i;
            Transition transition;
            ArrayMap arrayMap;
            ArrayMap arrayMap2;
            int i2;
            int[] iArr;
            int i3;
            int i4;
            int i5;
            Transition.AnimationInfo animationInfo;
            View view;
            TransitionValues transitionValues;
            View view2;
            this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
            int i6 = 1;
            if (!TransitionManager.sPendingTransitions.remove(this.mSceneRoot)) {
                return true;
            }
            final ArrayMap runningTransitions = TransitionManager.getRunningTransitions();
            ArrayList arrayList2 = (ArrayList) runningTransitions.get(this.mSceneRoot);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
                runningTransitions.put(this.mSceneRoot, arrayList2);
            } else if (arrayList2.size() > 0) {
                arrayList = new ArrayList(arrayList2);
                arrayList2.add(this.mTransition);
                this.mTransition.addListener(new TransitionListenerAdapter() { // from class: androidx.transition.TransitionManager.MultiListener.1
                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public final void onTransitionEnd(Transition transition2) {
                        ((ArrayList) runningTransitions.get(MultiListener.this.mSceneRoot)).remove(transition2);
                        transition2.removeListener(this);
                    }
                });
                i = 0;
                this.mTransition.captureValues(this.mSceneRoot, false);
                if (arrayList != null) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((Transition) it.next()).resume(this.mSceneRoot);
                    }
                }
                transition = this.mTransition;
                ViewGroup viewGroup = this.mSceneRoot;
                transition.getClass();
                transition.mStartValuesList = new ArrayList();
                transition.mEndValuesList = new ArrayList();
                TransitionValuesMaps transitionValuesMaps = transition.mStartValues;
                TransitionValuesMaps transitionValuesMaps2 = transition.mEndValues;
                arrayMap = new ArrayMap(transitionValuesMaps.mViewValues);
                arrayMap2 = new ArrayMap(transitionValuesMaps2.mViewValues);
                i2 = 0;
                while (true) {
                    iArr = transition.mMatchOrder;
                    if (i2 < iArr.length) {
                        break;
                    }
                    int i7 = iArr[i2];
                    if (i7 == i6) {
                        int i8 = arrayMap.size;
                        while (true) {
                            i8--;
                            if (i8 >= 0) {
                                View view3 = (View) arrayMap.keyAt(i8);
                                if (view3 != null && transition.isValidTarget(view3) && (transitionValues = (TransitionValues) arrayMap2.remove(view3)) != null && transition.isValidTarget(transitionValues.view)) {
                                    transition.mStartValuesList.add((TransitionValues) arrayMap.removeAt(i8));
                                    transition.mEndValuesList.add(transitionValues);
                                }
                            }
                        }
                    } else if (i7 == 2) {
                        ArrayMap arrayMap3 = transitionValuesMaps.mNameValues;
                        int i9 = arrayMap3.size;
                        for (int i10 = 0; i10 < i9; i10++) {
                            View view4 = (View) arrayMap3.valueAt(i10);
                            if (view4 != null && transition.isValidTarget(view4)) {
                                View view5 = (View) transitionValuesMaps2.mNameValues.get(arrayMap3.keyAt(i10));
                                if (view5 != null && transition.isValidTarget(view5)) {
                                    TransitionValues transitionValues2 = (TransitionValues) arrayMap.get(view4);
                                    TransitionValues transitionValues3 = (TransitionValues) arrayMap2.get(view5);
                                    if (transitionValues2 != null && transitionValues3 != null) {
                                        transition.mStartValuesList.add(transitionValues2);
                                        transition.mEndValuesList.add(transitionValues3);
                                        arrayMap.remove(view4);
                                        arrayMap2.remove(view5);
                                    }
                                }
                            }
                        }
                    } else if (i7 == 3) {
                        SparseArray sparseArray = transitionValuesMaps.mIdValues;
                        SparseArray sparseArray2 = transitionValuesMaps2.mIdValues;
                        int size = sparseArray.size();
                        for (int i11 = 0; i11 < size; i11++) {
                            View view6 = (View) sparseArray.valueAt(i11);
                            if (view6 != null && transition.isValidTarget(view6) && (view2 = (View) sparseArray2.get(sparseArray.keyAt(i11))) != null && transition.isValidTarget(view2)) {
                                TransitionValues transitionValues4 = (TransitionValues) arrayMap.get(view6);
                                TransitionValues transitionValues5 = (TransitionValues) arrayMap2.get(view2);
                                if (transitionValues4 != null && transitionValues5 != null) {
                                    transition.mStartValuesList.add(transitionValues4);
                                    transition.mEndValuesList.add(transitionValues5);
                                    arrayMap.remove(view6);
                                    arrayMap2.remove(view2);
                                }
                            }
                        }
                    } else if (i7 == 4) {
                        LongSparseArray longSparseArray = transitionValuesMaps.mItemIdValues;
                        int size2 = longSparseArray.size();
                        for (int i12 = i; i12 < size2; i12++) {
                            View view7 = (View) longSparseArray.valueAt(i12);
                            if (view7 != null && transition.isValidTarget(view7)) {
                                View view8 = (View) transitionValuesMaps2.mItemIdValues.get(longSparseArray.keyAt(i12));
                                if (view8 != null && transition.isValidTarget(view8)) {
                                    TransitionValues transitionValues6 = (TransitionValues) arrayMap.get(view7);
                                    TransitionValues transitionValues7 = (TransitionValues) arrayMap2.get(view8);
                                    if (transitionValues6 != null && transitionValues7 != null) {
                                        transition.mStartValuesList.add(transitionValues6);
                                        transition.mEndValuesList.add(transitionValues7);
                                        arrayMap.remove(view7);
                                        arrayMap2.remove(view8);
                                    }
                                }
                            }
                        }
                    }
                    i2++;
                    i6 = 1;
                    i = 0;
                }
                for (i3 = 0; i3 < arrayMap.size; i3++) {
                    TransitionValues transitionValues8 = (TransitionValues) arrayMap.valueAt(i3);
                    if (transition.isValidTarget(transitionValues8.view)) {
                        transition.mStartValuesList.add(transitionValues8);
                        transition.mEndValuesList.add(null);
                    }
                }
                for (i4 = 0; i4 < arrayMap2.size; i4++) {
                    TransitionValues transitionValues9 = (TransitionValues) arrayMap2.valueAt(i4);
                    if (transition.isValidTarget(transitionValues9.view)) {
                        transition.mEndValuesList.add(transitionValues9);
                        transition.mStartValuesList.add(null);
                    }
                }
                ArrayMap runningAnimators = Transition.getRunningAnimators();
                int i13 = runningAnimators.size;
                ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
                WindowIdApi18 windowIdApi18 = new WindowIdApi18(viewGroup);
                for (i5 = i13 - 1; i5 >= 0; i5--) {
                    Animator animator = (Animator) runningAnimators.keyAt(i5);
                    if (animator != null && (animationInfo = (Transition.AnimationInfo) runningAnimators.get(animator)) != null && (view = animationInfo.mView) != null && windowIdApi18.equals(animationInfo.mWindowId)) {
                        TransitionValues transitionValues10 = transition.getTransitionValues(view, true);
                        TransitionValues matchedTransitionValues = transition.getMatchedTransitionValues(view, true);
                        if (transitionValues10 == null && matchedTransitionValues == null) {
                            matchedTransitionValues = (TransitionValues) transition.mEndValues.mViewValues.get(view);
                        }
                        if (!(transitionValues10 == null && matchedTransitionValues == null) && animationInfo.mTransition.isTransitionRequired(animationInfo.mValues, matchedTransitionValues)) {
                            if (animator.isRunning() || animator.isStarted()) {
                                animator.cancel();
                            } else {
                                runningAnimators.remove(animator);
                            }
                        }
                    }
                }
                transition.createAnimators(viewGroup, transition.mStartValues, transition.mEndValues, transition.mStartValuesList, transition.mEndValuesList);
                transition.runAnimators();
                return true;
            }
            arrayList = null;
            arrayList2.add(this.mTransition);
            this.mTransition.addListener(new TransitionListenerAdapter() { // from class: androidx.transition.TransitionManager.MultiListener.1
                @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                public final void onTransitionEnd(Transition transition2) {
                    ((ArrayList) runningTransitions.get(MultiListener.this.mSceneRoot)).remove(transition2);
                    transition2.removeListener(this);
                }
            });
            i = 0;
            this.mTransition.captureValues(this.mSceneRoot, false);
            if (arrayList != null) {
            }
            transition = this.mTransition;
            ViewGroup viewGroup2 = this.mSceneRoot;
            transition.getClass();
            transition.mStartValuesList = new ArrayList();
            transition.mEndValuesList = new ArrayList();
            TransitionValuesMaps transitionValuesMaps3 = transition.mStartValues;
            TransitionValuesMaps transitionValuesMaps22 = transition.mEndValues;
            arrayMap = new ArrayMap(transitionValuesMaps3.mViewValues);
            arrayMap2 = new ArrayMap(transitionValuesMaps22.mViewValues);
            i2 = 0;
            while (true) {
                iArr = transition.mMatchOrder;
                if (i2 < iArr.length) {
                }
                i2++;
                i6 = 1;
                i = 0;
            }
            while (i3 < arrayMap.size) {
            }
            while (i4 < arrayMap2.size) {
            }
            ArrayMap runningAnimators2 = Transition.getRunningAnimators();
            int i132 = runningAnimators2.size;
            ViewUtilsApi29 viewUtilsApi292 = ViewUtils.IMPL;
            WindowIdApi18 windowIdApi182 = new WindowIdApi18(viewGroup2);
            while (i5 >= 0) {
            }
            transition.createAnimators(viewGroup2, transition.mStartValues, transition.mEndValues, transition.mStartValuesList, transition.mEndValuesList);
            transition.runAnimators();
            return true;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
            TransitionManager.sPendingTransitions.remove(this.mSceneRoot);
            ArrayList arrayList = (ArrayList) TransitionManager.getRunningTransitions().get(this.mSceneRoot);
            if (arrayList != null && arrayList.size() > 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((Transition) it.next()).resume(this.mSceneRoot);
                }
            }
            this.mTransition.clearValues(true);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
        }
    }
}
