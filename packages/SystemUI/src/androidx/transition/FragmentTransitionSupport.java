package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import androidx.core.os.CancellationSignal;
import androidx.fragment.app.DefaultSpecialEffectsController$$ExternalSyntheticLambda0;
import androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransitionImpl;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public class FragmentTransitionSupport extends FragmentTransitionImpl {
    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void addTarget(View view, Object obj) {
        ((Transition) obj).addTarget(view);
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void addTargets(Object obj, ArrayList arrayList) {
        Transition transition = (Transition) obj;
        if (transition == null) {
            return;
        }
        int i = 0;
        if (transition instanceof TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition;
            int size = transitionSet.mTransitions.size();
            while (i < size) {
                addTargets(transitionSet.getTransitionAt(i), arrayList);
                i++;
            }
            return;
        }
        if (FragmentTransitionImpl.isNullOrEmpty(transition.mTargetIds) && FragmentTransitionImpl.isNullOrEmpty(transition.mTargets)) {
            int size2 = arrayList.size();
            while (i < size2) {
                transition.addTarget((View) arrayList.get(i));
                i++;
            }
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void animateToEnd(Object obj) {
        Transition.SeekController seekController = (Transition.SeekController) obj;
        seekController.ensureAnimation();
        seekController.mSpringAnimation.animateToFinalPosition(Transition.this.mTotalDuration + 1);
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void animateToStart(Object obj, DefaultSpecialEffectsController$$ExternalSyntheticLambda0 defaultSpecialEffectsController$$ExternalSyntheticLambda0) {
        Transition.SeekController seekController = (Transition.SeekController) obj;
        seekController.mResetToStartState = defaultSpecialEffectsController$$ExternalSyntheticLambda0;
        seekController.ensureAnimation();
        seekController.mSpringAnimation.animateToFinalPosition(0.0f);
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void beginDelayedTransition(ViewGroup viewGroup, Object obj) {
        TransitionManager.beginDelayedTransition((Transition) obj, viewGroup);
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final boolean canHandle(Object obj) {
        return obj instanceof Transition;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final Object cloneTransition(Object obj) {
        if (obj != null) {
            return ((Transition) obj).mo900clone();
        }
        return null;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final Object controlDelayedTransition(ViewGroup viewGroup, Object obj) {
        Transition transition = (Transition) obj;
        ArrayList arrayList = TransitionManager.sPendingTransitions;
        if (arrayList.contains(viewGroup) || !viewGroup.isLaidOut()) {
            return null;
        }
        if (!transition.isSeekingSupported()) {
            throw new IllegalArgumentException("The Transition must support seeking.");
        }
        arrayList.add(viewGroup);
        Transition transitionMo900clone = transition.mo900clone();
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(transitionMo900clone);
        TransitionManager.sceneChangeSetup(transitionSet, viewGroup);
        viewGroup.setTag(R.id.transition_current_scene, null);
        TransitionManager.MultiListener multiListener = new TransitionManager.MultiListener(transitionSet, viewGroup);
        viewGroup.addOnAttachStateChangeListener(multiListener);
        viewGroup.getViewTreeObserver().addOnPreDrawListener(multiListener);
        viewGroup.invalidate();
        Transition.SeekController seekController = new Transition.SeekController();
        transitionSet.mSeekController = seekController;
        transitionSet.addListener(seekController);
        return transitionSet.mSeekController;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final boolean isSeekingSupported() {
        return true;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final Object mergeTransitionsInSequence(Object obj, Object obj2, Object obj3) {
        Transition transition = (Transition) obj;
        Transition transition2 = (Transition) obj2;
        Transition transition3 = (Transition) obj3;
        if (transition != null && transition2 != null) {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.addTransition(transition);
            transitionSet.addTransition(transition2);
            transitionSet.setOrdering(1);
            transition = transitionSet;
        } else if (transition == null) {
            transition = transition2 != null ? transition2 : null;
        }
        if (transition3 == null) {
            return transition;
        }
        TransitionSet transitionSet2 = new TransitionSet();
        if (transition != null) {
            transitionSet2.addTransition(transition);
        }
        transitionSet2.addTransition(transition3);
        return transitionSet2;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final Object mergeTransitionsTogether(Object obj, Object obj2) {
        TransitionSet transitionSet = new TransitionSet();
        if (obj != null) {
            transitionSet.addTransition((Transition) obj);
        }
        transitionSet.addTransition((Transition) obj2);
        return transitionSet;
    }

    public final void replaceTargets(Object obj, ArrayList arrayList, ArrayList arrayList2) {
        Transition transition = (Transition) obj;
        int i = 0;
        if (transition instanceof TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition;
            int size = transitionSet.mTransitions.size();
            while (i < size) {
                replaceTargets(transitionSet.getTransitionAt(i), arrayList, arrayList2);
                i++;
            }
            return;
        }
        if (FragmentTransitionImpl.isNullOrEmpty(transition.mTargetIds)) {
            ArrayList arrayList3 = transition.mTargets;
            if (arrayList3.size() == arrayList.size() && arrayList3.containsAll(arrayList)) {
                int size2 = arrayList2 == null ? 0 : arrayList2.size();
                while (i < size2) {
                    transition.addTarget((View) arrayList2.get(i));
                    i++;
                }
                for (int size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                    transition.removeTarget((View) arrayList.get(size3));
                }
            }
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void scheduleHideFragmentView(Object obj, final View view, final ArrayList arrayList) {
        ((Transition) obj).addListener(new Transition.TransitionListener(this) { // from class: androidx.transition.FragmentTransitionSupport.2
            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                view.setVisibility(8);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((View) arrayList.get(i)).setVisibility(0);
                }
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                transition.addListener(this);
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionPause() {
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionResume() {
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionCancel(Transition transition) {
            }
        });
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void scheduleRemoveTargets(Object obj, final Object obj2, final ArrayList arrayList, final Object obj3, final ArrayList arrayList2) {
        final Object obj4 = null;
        final ArrayList arrayList3 = null;
        ((Transition) obj).addListener(new TransitionListenerAdapter() { // from class: androidx.transition.FragmentTransitionSupport.3
            @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
            }

            @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition) {
                FragmentTransitionSupport fragmentTransitionSupport = FragmentTransitionSupport.this;
                Object obj5 = obj2;
                if (obj5 != null) {
                    fragmentTransitionSupport.replaceTargets(obj5, arrayList, null);
                }
                Object obj6 = obj4;
                if (obj6 != null) {
                    fragmentTransitionSupport.replaceTargets(obj6, arrayList3, null);
                }
                Object obj7 = obj3;
                if (obj7 != null) {
                    fragmentTransitionSupport.replaceTargets(obj7, arrayList2, null);
                }
            }
        });
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void setCurrentPlayTime(Object obj, float f) {
        Transition.SeekController seekController = (Transition.SeekController) obj;
        boolean z = seekController.mIsReady;
        if (z) {
            Transition transition = Transition.this;
            long j = transition.mTotalDuration;
            long j2 = (long) (f * j);
            if (j2 == 0) {
                j2 = 1;
            }
            if (j2 == j) {
                j2 = j - 1;
            }
            if (seekController.mSpringAnimation != null) {
                throw new IllegalStateException("setCurrentPlayTimeMillis() called after animation has been started");
            }
            long j3 = seekController.mCurrentPlayTime;
            if (j2 == j3 || !z) {
                return;
            }
            if (!seekController.mIsCanceled) {
                if (j2 == 0 && j3 > 0) {
                    j2 = -1;
                } else if (j2 == j && j3 < j) {
                    j2 = j + 1;
                }
                if (j2 != j3) {
                    transition.setCurrentPlayTimeMillis(j2, j3);
                    seekController.mCurrentPlayTime = j2;
                }
            }
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            VelocityTracker1D velocityTracker1D = seekController.mVelocityTracker;
            int i = (velocityTracker1D.mIndex + 1) % 20;
            velocityTracker1D.mIndex = i;
            velocityTracker1D.mTimeSamples[i] = jCurrentAnimationTimeMillis;
            velocityTracker1D.mDataSamples[i] = j2;
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void setEpicenter(View view, Object obj) {
        if (view != null) {
            Rect rect = new Rect();
            FragmentTransitionImpl.getBoundsOnScreen(rect, view);
            ((Transition) obj).setEpicenterCallback(new Transition.EpicenterCallback(this, rect) { // from class: androidx.transition.FragmentTransitionSupport.1
            });
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void setListenerForTransitionEnd(Fragment fragment, Object obj, CancellationSignal cancellationSignal, Runnable runnable) {
        setListenerForTransitionEnd(obj, cancellationSignal, (DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0) null, runnable);
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void setSharedElementTargets(Object obj, View view, ArrayList arrayList) {
        TransitionSet transitionSet = (TransitionSet) obj;
        ArrayList arrayList2 = transitionSet.mTargets;
        arrayList2.clear();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            FragmentTransitionImpl.bfsAddViewChildren((View) arrayList.get(i), arrayList2);
        }
        arrayList2.add(view);
        arrayList.add(view);
        addTargets(transitionSet, arrayList);
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void swapSharedElementTargets(Object obj, ArrayList arrayList, ArrayList arrayList2) {
        TransitionSet transitionSet = (TransitionSet) obj;
        if (transitionSet != null) {
            transitionSet.mTargets.clear();
            transitionSet.mTargets.addAll(arrayList2);
            replaceTargets(transitionSet, arrayList, arrayList2);
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final Object wrapTransitionInSet(Object obj) {
        if (obj == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition((Transition) obj);
        return transitionSet;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final boolean isSeekingSupported(Object obj) {
        boolean zIsSeekingSupported = ((Transition) obj).isSeekingSupported();
        if (!zIsSeekingSupported) {
            Objects.toString(obj);
        }
        return zIsSeekingSupported;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void setListenerForTransitionEnd(Object obj, CancellationSignal cancellationSignal, final DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0 defaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0, final Runnable runnable) {
        final Transition transition = (Transition) obj;
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.transition.FragmentTransitionSupport$$ExternalSyntheticLambda0
            @Override // androidx.core.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                Runnable runnable2 = runnable;
                DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0 defaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda02 = defaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0;
                if (defaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda02 != null) {
                    defaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda02.run();
                } else {
                    transition.cancel();
                    runnable2.run();
                }
            }
        });
        transition.addListener(new Transition.TransitionListener(this) { // from class: androidx.transition.FragmentTransitionSupport.4
            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition2) {
                runnable.run();
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionPause() {
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionResume() {
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionCancel(Transition transition2) {
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition2) {
            }
        });
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void setEpicenter(Object obj, Rect rect) {
        ((Transition) obj).setEpicenterCallback(new Transition.EpicenterCallback(this, rect) { // from class: androidx.transition.FragmentTransitionSupport.5
        });
    }
}
