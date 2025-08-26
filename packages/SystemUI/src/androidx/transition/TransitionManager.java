package androidx.transition;

import android.animation.Animator;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowId;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import androidx.transition.Transition;
import com.android.systemui.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class TransitionManager {
    public static final AutoTransition sDefaultTransition = new AutoTransition();
    public static final ThreadLocal sRunningTransitions = new ThreadLocal();
    public static final ArrayList sPendingTransitions = new ArrayList();

    public TransitionManager() {
        new ArrayMap();
        new ArrayMap();
    }

    public static void beginDelayedTransition(Transition transition, ViewGroup viewGroup) {
        ArrayList arrayList = sPendingTransitions;
        if (arrayList.contains(viewGroup) || !viewGroup.isLaidOut()) {
            return;
        }
        arrayList.add(viewGroup);
        if (transition == null) {
            transition = sDefaultTransition;
        }
        Transition transitionMo900clone = transition.mo900clone();
        sceneChangeSetup(transitionMo900clone, viewGroup);
        viewGroup.setTag(R.id.transition_current_scene, null);
        MultiListener multiListener = new MultiListener(transitionMo900clone, viewGroup);
        viewGroup.addOnAttachStateChangeListener(multiListener);
        viewGroup.getViewTreeObserver().addOnPreDrawListener(multiListener);
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

    public static void sceneChangeSetup(Transition transition, ViewGroup viewGroup) {
        ArrayList arrayList = (ArrayList) getRunningTransitions().get(viewGroup);
        if (arrayList != null && arrayList.size() > 0) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((Transition) obj).pause(viewGroup);
            }
        }
        if (transition != null) {
            transition.captureValues(viewGroup, true);
        }
        Scene scene = (Scene) viewGroup.getTag(R.id.transition_current_scene);
        if (scene != null) {
        }
    }

    public class MultiListener implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
        public final ViewGroup mSceneRoot;
        public final Transition mTransition;

        public MultiListener(Transition transition, ViewGroup viewGroup) {
            this.mTransition = transition;
            this.mSceneRoot = viewGroup;
        }

        /* JADX WARN: Removed duplicated region for block: B:105:0x0235  */
        /* JADX WARN: Removed duplicated region for block: B:128:0x02a8  */
        /* JADX WARN: Removed duplicated region for block: B:139:0x02d4  */
        /* JADX WARN: Removed duplicated region for block: B:141:0x02da  */
        /* JADX WARN: Removed duplicated region for block: B:147:0x01e1 A[EDGE_INSN: B:147:0x01e1->B:88:0x01e1 BREAK  A[LOOP:1: B:18:0x0097->B:87:0x01da], SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:14:0x005a  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x009c  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00fb  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x01e8  */
        /* JADX WARN: Removed duplicated region for block: B:98:0x0209  */
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
            boolean z;
            TransitionValues transitionValues;
            View view;
            boolean z2;
            this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
            boolean z3 = true;
            if (!TransitionManager.sPendingTransitions.remove(this.mSceneRoot)) {
                return true;
            }
            final ArrayMap runningTransitions = TransitionManager.getRunningTransitions();
            ArrayList arrayList2 = (ArrayList) runningTransitions.get(this.mSceneRoot);
            if (arrayList2 != null) {
                arrayList = arrayList2.size() > 0 ? new ArrayList(arrayList2) : null;
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
                    int size = arrayList.size();
                    int i6 = 0;
                    while (i6 < size) {
                        Object obj = arrayList.get(i6);
                        i6++;
                        ((Transition) obj).resume(this.mSceneRoot);
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
                    if (i7 == z3) {
                        z = z3;
                        for (int i8 = arrayMap.size - 1; i8 >= 0; i8--) {
                            View view2 = (View) arrayMap.keyAt(i8);
                            if (view2 != null && transition.isValidTarget(view2) && (transitionValues = (TransitionValues) arrayMap2.remove(view2)) != null && transition.isValidTarget(transitionValues.view)) {
                                transition.mStartValuesList.add((TransitionValues) arrayMap.removeAt(i8));
                                transition.mEndValuesList.add(transitionValues);
                            }
                        }
                    } else if (i7 == 2) {
                        z = z3;
                        ArrayMap arrayMap3 = transitionValuesMaps.mNameValues;
                        int i9 = arrayMap3.size;
                        for (int i10 = 0; i10 < i9; i10++) {
                            View view3 = (View) arrayMap3.valueAt(i10);
                            if (view3 != null && transition.isValidTarget(view3)) {
                                View view4 = (View) transitionValuesMaps2.mNameValues.get(arrayMap3.keyAt(i10));
                                if (view4 != null && transition.isValidTarget(view4)) {
                                    TransitionValues transitionValues2 = (TransitionValues) arrayMap.get(view3);
                                    TransitionValues transitionValues3 = (TransitionValues) arrayMap2.get(view4);
                                    if (transitionValues2 != null && transitionValues3 != null) {
                                        transition.mStartValuesList.add(transitionValues2);
                                        transition.mEndValuesList.add(transitionValues3);
                                        arrayMap.remove(view3);
                                        arrayMap2.remove(view4);
                                    }
                                }
                            }
                        }
                    } else if (i7 != 3) {
                        if (i7 == 4) {
                            LongSparseArray longSparseArray = transitionValuesMaps.mItemIdValues;
                            int size2 = longSparseArray.size();
                            int i11 = i;
                            while (i11 < size2) {
                                View view5 = (View) longSparseArray.valueAt(i11);
                                if (view5 == null || !transition.isValidTarget(view5)) {
                                    z2 = z3;
                                } else {
                                    View view6 = (View) transitionValuesMaps2.mItemIdValues.get(longSparseArray.keyAt(i11));
                                    if (view6 != null && transition.isValidTarget(view6)) {
                                        TransitionValues transitionValues4 = (TransitionValues) arrayMap.get(view5);
                                        TransitionValues transitionValues5 = (TransitionValues) arrayMap2.get(view6);
                                        if (transitionValues4 != null && transitionValues5 != null) {
                                            z2 = z3;
                                            transition.mStartValuesList.add(transitionValues4);
                                            transition.mEndValuesList.add(transitionValues5);
                                            arrayMap.remove(view5);
                                            arrayMap2.remove(view6);
                                        }
                                    }
                                }
                                i11++;
                                z3 = z2;
                            }
                        }
                        z = z3;
                    } else {
                        z = z3;
                        SparseArray sparseArray = transitionValuesMaps.mIdValues;
                        SparseArray sparseArray2 = transitionValuesMaps2.mIdValues;
                        int size3 = sparseArray.size();
                        for (int i12 = 0; i12 < size3; i12++) {
                            View view7 = (View) sparseArray.valueAt(i12);
                            if (view7 != null && transition.isValidTarget(view7) && (view = (View) sparseArray2.get(sparseArray.keyAt(i12))) != null && transition.isValidTarget(view)) {
                                TransitionValues transitionValues6 = (TransitionValues) arrayMap.get(view7);
                                TransitionValues transitionValues7 = (TransitionValues) arrayMap2.get(view);
                                if (transitionValues6 != null && transitionValues7 != null) {
                                    transition.mStartValuesList.add(transitionValues6);
                                    transition.mEndValuesList.add(transitionValues7);
                                    arrayMap.remove(view7);
                                    arrayMap2.remove(view);
                                }
                            }
                        }
                    }
                    i2++;
                    z3 = z;
                    i = 0;
                }
                boolean z4 = z3;
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
                WindowId windowId = viewGroup.getWindowId();
                i5 = i13 - 1;
                while (i5 >= 0) {
                    Animator animator = (Animator) runningAnimators.keyAt(i5);
                    if (animator != null && (animationInfo = (Transition.AnimationInfo) runningAnimators.get(animator)) != null && animationInfo.mView != null && windowId.equals(animationInfo.mWindowId)) {
                        View view8 = animationInfo.mView;
                        boolean z5 = z4;
                        TransitionValues transitionValues10 = transition.getTransitionValues(view8, z5);
                        TransitionValues matchedTransitionValues = transition.getMatchedTransitionValues(view8, z5);
                        if (transitionValues10 == null && matchedTransitionValues == null) {
                            matchedTransitionValues = (TransitionValues) transition.mEndValues.mViewValues.get(view8);
                        }
                        if (transitionValues10 != null || matchedTransitionValues != null) {
                            TransitionValues transitionValues11 = animationInfo.mValues;
                            Transition transition2 = animationInfo.mTransition;
                            if (transition2.isTransitionRequired(transitionValues11, matchedTransitionValues)) {
                                if (transition2.getRootTransition().mSeekController != null) {
                                    animator.cancel();
                                    transition2.mCurrentAnimators.remove(animator);
                                    runningAnimators.remove(animator);
                                    if (transition2.mCurrentAnimators.size() == 0) {
                                        transition2.notifyFromTransition(transition2, Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_CANCEL, false);
                                        if (!transition2.mEnded) {
                                            transition2.mEnded = true;
                                            transition2.notifyFromTransition(transition2, Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_END, false);
                                        }
                                    }
                                } else if (animator.isRunning() || animator.isStarted()) {
                                    animator.cancel();
                                } else {
                                    runningAnimators.remove(animator);
                                }
                            }
                        }
                    }
                    i5--;
                    z4 = true;
                }
                transition.createAnimators(viewGroup, transition.mStartValues, transition.mEndValues, transition.mStartValuesList, transition.mEndValuesList);
                if (transition.mSeekController != null) {
                    transition.runAnimators();
                    return true;
                }
                transition.prepareAnimatorsForSeeking();
                Transition.SeekController seekController = transition.mSeekController;
                Transition transition3 = Transition.this;
                long j = transition3.mTotalDuration == 0 ? 1L : 0L;
                transition3.setCurrentPlayTimeMillis(j, seekController.mCurrentPlayTime);
                seekController.mCurrentPlayTime = j;
                transition.mSeekController.mIsReady = true;
                return true;
            }
            arrayList2 = new ArrayList();
            runningTransitions.put(this.mSceneRoot, arrayList2);
            arrayList2.add(this.mTransition);
            this.mTransition.addListener(new TransitionListenerAdapter() { // from class: androidx.transition.TransitionManager.MultiListener.1
                @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                public final void onTransitionEnd(Transition transition22) {
                    ((ArrayList) runningTransitions.get(MultiListener.this.mSceneRoot)).remove(transition22);
                    transition22.removeListener(this);
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
                z3 = z;
                i = 0;
            }
            boolean z42 = z3;
            while (i3 < arrayMap.size) {
            }
            while (i4 < arrayMap2.size) {
            }
            ArrayMap runningAnimators2 = Transition.getRunningAnimators();
            int i132 = runningAnimators2.size;
            WindowId windowId2 = viewGroup2.getWindowId();
            i5 = i132 - 1;
            while (i5 >= 0) {
            }
            transition.createAnimators(viewGroup2, transition.mStartValues, transition.mEndValues, transition.mStartValuesList, transition.mEndValuesList);
            if (transition.mSeekController != null) {
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
            TransitionManager.sPendingTransitions.remove(this.mSceneRoot);
            ArrayList arrayList = (ArrayList) TransitionManager.getRunningTransitions().get(this.mSceneRoot);
            if (arrayList != null && arrayList.size() > 0) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((Transition) obj).resume(this.mSceneRoot);
                }
            }
            this.mTransition.clearValues(true);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
        }
    }
}
