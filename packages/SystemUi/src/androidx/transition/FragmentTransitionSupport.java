package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.os.CancellationSignal;
import androidx.fragment.app.DefaultSpecialEffectsController;
import androidx.fragment.app.FragmentTransitionImpl;
import androidx.transition.Transition;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class FragmentTransitionSupport extends FragmentTransitionImpl {
    public static boolean hasSimpleTarget(Transition transition) {
        return (FragmentTransitionImpl.isNullOrEmpty(transition.mTargetIds) && FragmentTransitionImpl.isNullOrEmpty(null) && FragmentTransitionImpl.isNullOrEmpty(null)) ? false : true;
    }

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
                addTargets((i < 0 || i >= transitionSet.mTransitions.size()) ? null : (Transition) transitionSet.mTransitions.get(i), arrayList);
                i++;
            }
            return;
        }
        if (hasSimpleTarget(transition) || !FragmentTransitionImpl.isNullOrEmpty(transition.mTargets)) {
            return;
        }
        int size2 = arrayList.size();
        while (i < size2) {
            transition.addTarget((View) arrayList.get(i));
            i++;
        }
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
            return ((Transition) obj).mo348clone();
        }
        return null;
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
                replaceTargets((i < 0 || i >= transitionSet.mTransitions.size()) ? null : (Transition) transitionSet.mTransitions.get(i), arrayList, arrayList2);
                i++;
            }
            return;
        }
        if (hasSimpleTarget(transition)) {
            return;
        }
        ArrayList arrayList3 = transition.mTargets;
        if (arrayList3.size() != arrayList.size() || !arrayList3.containsAll(arrayList)) {
            return;
        }
        int size2 = arrayList2 == null ? 0 : arrayList2.size();
        while (i < size2) {
            transition.addTarget((View) arrayList2.get(i));
            i++;
        }
        int size3 = arrayList.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                return;
            } else {
                transition.removeTarget((View) arrayList.get(size3));
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
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    ((View) arrayList2.get(i)).setVisibility(0);
                }
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                transition.addListener(this);
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionCancel() {
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionPause() {
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionResume() {
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
    public final void setEpicenter(View view, Object obj) {
        if (view != null) {
            final Rect rect = new Rect();
            FragmentTransitionImpl.getBoundsOnScreen(rect, view);
            ((Transition) obj).setEpicenterCallback(new Transition.EpicenterCallback(this) { // from class: androidx.transition.FragmentTransitionSupport.1
                @Override // androidx.transition.Transition.EpicenterCallback
                public final Rect onGetEpicenter() {
                    return rect;
                }
            });
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void setListenerForTransitionEnd(Object obj, CancellationSignal cancellationSignal, final DefaultSpecialEffectsController.RunnableC02189 runnableC02189) {
        final Transition transition = (Transition) obj;
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(this) { // from class: androidx.transition.FragmentTransitionSupport.4
            @Override // androidx.core.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                transition.cancel();
            }
        });
        transition.addListener(new Transition.TransitionListener(this) { // from class: androidx.transition.FragmentTransitionSupport.5
            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition2) {
                runnableC02189.run();
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition2) {
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionCancel() {
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionPause() {
            }

            @Override // androidx.transition.Transition.TransitionListener
            public final void onTransitionResume() {
            }
        });
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
    public final void setEpicenter(Object obj, final Rect rect) {
        ((Transition) obj).setEpicenterCallback(new Transition.EpicenterCallback(this) { // from class: androidx.transition.FragmentTransitionSupport.6
            @Override // androidx.transition.Transition.EpicenterCallback
            public final Rect onGetEpicenter() {
                Rect rect2 = rect;
                if (rect2 == null || rect2.isEmpty()) {
                    return null;
                }
                return rect2;
            }
        });
    }
}
