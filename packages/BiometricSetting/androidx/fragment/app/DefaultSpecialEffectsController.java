package androidx.fragment.app;

import android.content.Context;
import android.graphics.Rect;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.collection.ArrayMap;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.SpecialEffectsController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DefaultSpecialEffectsController.kt */
/* loaded from: classes.dex */
public final class DefaultSpecialEffectsController extends SpecialEffectsController {

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DefaultSpecialEffectsController.kt */
    static final class AnimationInfo extends SpecialEffectsInfo {
        private FragmentAnim.AnimationOrAnimator animation;
        private boolean isAnimLoaded;
        private final boolean isPop;

        public AnimationInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal, boolean z) {
            super(operation, cancellationSignal);
            this.isPop = z;
        }

        public final FragmentAnim.AnimationOrAnimator getAnimation(Context context) {
            if (this.isAnimLoaded) {
                return this.animation;
            }
            FragmentAnim.AnimationOrAnimator loadAnimation = FragmentAnim.loadAnimation(context, getOperation().getFragment(), getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.isPop);
            this.animation = loadAnimation;
            this.isAnimLoaded = true;
            return loadAnimation;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DefaultSpecialEffectsController.kt */
    static class SpecialEffectsInfo {
        private final SpecialEffectsController.Operation operation;
        private final CancellationSignal signal;

        public SpecialEffectsInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal) {
            this.operation = operation;
            this.signal = cancellationSignal;
        }

        public final void completeSpecialEffect() {
            this.operation.completeSpecialEffect(this.signal);
        }

        public final SpecialEffectsController.Operation getOperation() {
            return this.operation;
        }

        public final boolean isVisibilityUnchanged() {
            this.operation.getFragment().getClass();
            Intrinsics.checkNotNullExpressionValue(null, "operation.fragment.mView");
            throw null;
        }
    }

    public static void $r8$lambda$Q6FgD4jZH_jdeVxJxVn553yFXpk(List awaitingContainerChanges, SpecialEffectsController.Operation operation, DefaultSpecialEffectsController this$0) {
        Intrinsics.checkNotNullParameter(awaitingContainerChanges, "$awaitingContainerChanges");
        Intrinsics.checkNotNullParameter(operation, "$operation");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (awaitingContainerChanges.contains(operation)) {
            awaitingContainerChanges.remove(operation);
            operation.getFragment().getClass();
            Intrinsics.checkNotNullExpressionValue(null, "view");
            throw null;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultSpecialEffectsController(ViewGroup container) {
        super(container);
        Intrinsics.checkNotNullParameter(container, "container");
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x037c  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0435  */
    @Override // androidx.fragment.app.SpecialEffectsController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void executeOperations(List<? extends SpecialEffectsController.Operation> list, boolean z) {
        List list2;
        List list3;
        ArrayMap arrayMap;
        Iterator it;
        ArrayList arrayList = (ArrayList) list;
        Iterator it2 = arrayList.iterator();
        if (it2.hasNext()) {
            ((SpecialEffectsController.Operation) it2.next()).getFragment().getClass();
            Intrinsics.checkNotNullExpressionValue(null, "operation.fragment.mView");
            throw null;
        }
        ListIterator listIterator = arrayList.listIterator(arrayList.size());
        if (listIterator.hasPrevious()) {
            ((SpecialEffectsController.Operation) listIterator.previous()).getFragment().getClass();
            Intrinsics.checkNotNullExpressionValue(null, "operation.fragment.mView");
            throw null;
        }
        int i = 2;
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "Executing operations from null to null");
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        List mutableList = CollectionsKt.toMutableList(list);
        if (arrayList.isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        Fragment fragment = ((SpecialEffectsController.Operation) arrayList.get(CollectionsKt.getLastIndex(list))).getFragment();
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            SpecialEffectsController.Operation operation = (SpecialEffectsController.Operation) it3.next();
            operation.getFragment().mAnimationInfo.mEnterAnim = fragment.mAnimationInfo.mEnterAnim;
            operation.getFragment().mAnimationInfo.mExitAnim = fragment.mAnimationInfo.mExitAnim;
            operation.getFragment().mAnimationInfo.mPopEnterAnim = fragment.mAnimationInfo.mPopEnterAnim;
            operation.getFragment().mAnimationInfo.mPopExitAnim = fragment.mAnimationInfo.mPopExitAnim;
        }
        Iterator it4 = arrayList.iterator();
        while (it4.hasNext()) {
            SpecialEffectsController.Operation operation2 = (SpecialEffectsController.Operation) it4.next();
            CancellationSignal cancellationSignal = new CancellationSignal();
            operation2.markStartedSpecialEffect(cancellationSignal);
            arrayList2.add(new AnimationInfo(operation2, cancellationSignal, z));
            CancellationSignal cancellationSignal2 = new CancellationSignal();
            operation2.markStartedSpecialEffect(cancellationSignal2);
            arrayList3.add(new TransitionInfo(operation2, cancellationSignal2, z));
            operation2.addCompletionListener(new DefaultSpecialEffectsController$$ExternalSyntheticLambda0(mutableList, operation2, this));
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ArrayList arrayList4 = new ArrayList();
        Iterator it5 = arrayList3.iterator();
        if (it5.hasNext()) {
            ((TransitionInfo) it5.next()).isVisibilityUnchanged();
            throw null;
        }
        ArrayList arrayList5 = new ArrayList();
        Iterator it6 = arrayList4.iterator();
        while (true) {
            if (!it6.hasNext()) {
                break;
            }
            Object next = it6.next();
            if (((TransitionInfo) next).getHandlingImpl() != null) {
                arrayList5.add(next);
            }
        }
        Iterator it7 = arrayList5.iterator();
        FragmentTransitionImpl fragmentTransitionImpl = null;
        while (it7.hasNext()) {
            TransitionInfo transitionInfo = (TransitionInfo) it7.next();
            FragmentTransitionImpl handlingImpl = transitionInfo.getHandlingImpl();
            if (!(fragmentTransitionImpl == null || handlingImpl == fragmentTransitionImpl)) {
                throw new IllegalArgumentException(("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + transitionInfo.getOperation().getFragment() + " returned Transition " + transitionInfo.getTransition() + " which uses a different Transition type than other Fragments.").toString());
            }
            fragmentTransitionImpl = handlingImpl;
        }
        if (fragmentTransitionImpl == null) {
            Iterator it8 = arrayList3.iterator();
            while (it8.hasNext()) {
                TransitionInfo transitionInfo2 = (TransitionInfo) it8.next();
                linkedHashMap.put(transitionInfo2.getOperation(), Boolean.FALSE);
                transitionInfo2.completeSpecialEffect();
            }
        } else {
            new View(getContainer().getContext());
            new Rect();
            final ArrayList arrayList6 = new ArrayList();
            final ArrayList arrayList7 = new ArrayList();
            ArrayMap arrayMap2 = new ArrayMap();
            Iterator it9 = arrayList3.iterator();
            while (it9.hasNext()) {
                ((TransitionInfo) it9.next()).getClass();
            }
            ArrayList arrayList8 = new ArrayList();
            Iterator it10 = arrayList3.iterator();
            if (it10.hasNext()) {
                ((TransitionInfo) it10.next()).isVisibilityUnchanged();
                throw null;
            }
            Object mergeTransitionsInSequence = fragmentTransitionImpl.mergeTransitionsInSequence();
            if (mergeTransitionsInSequence != null) {
                ArrayList arrayList9 = new ArrayList();
                Iterator it11 = arrayList3.iterator();
                if (it11.hasNext()) {
                    ((TransitionInfo) it11.next()).isVisibilityUnchanged();
                    throw null;
                }
                Iterator it12 = arrayList9.iterator();
                while (it12.hasNext()) {
                    TransitionInfo transitionInfo3 = (TransitionInfo) it12.next();
                    Object transition = transitionInfo3.getTransition();
                    SpecialEffectsController.Operation operation3 = transitionInfo3.getOperation();
                    if (transition != null) {
                        if (ViewCompat.isLaidOut(getContainer())) {
                            transitionInfo3.getOperation().getClass();
                            fragmentTransitionImpl.setListenerForTransitionEnd(mergeTransitionsInSequence, new DefaultSpecialEffectsController$$ExternalSyntheticLambda1(transitionInfo3, operation3));
                        } else {
                            if (FragmentManager.isLoggingEnabled(i)) {
                                Log.v("FragmentManager", "SpecialEffectsController: Container " + getContainer() + " has not been laid out. Completing operation " + operation3);
                            }
                            transitionInfo3.completeSpecialEffect();
                        }
                    }
                    i = 2;
                }
                if (ViewCompat.isLaidOut(getContainer())) {
                    FragmentTransition.setViewVisibility(arrayList8, 4);
                    final ArrayList arrayList10 = new ArrayList();
                    int size = arrayList7.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        View view = (View) arrayList7.get(i2);
                        arrayList10.add(ViewCompat.getTransitionName(view));
                        ViewCompat.setTransitionName(view, null);
                    }
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", ">>>>> Beginning transition <<<<<");
                        Log.v("FragmentManager", ">>>>> SharedElementFirstOutViews <<<<<");
                        for (Iterator it13 = arrayList6.iterator(); it13.hasNext(); it13 = it13) {
                            Object sharedElementFirstOutViews = it13.next();
                            Intrinsics.checkNotNullExpressionValue(sharedElementFirstOutViews, "sharedElementFirstOutViews");
                            View view2 = (View) sharedElementFirstOutViews;
                            Log.v("FragmentManager", "View: " + view2 + " Name: " + ViewCompat.getTransitionName(view2));
                        }
                        Log.v("FragmentManager", ">>>>> SharedElementLastInViews <<<<<");
                        for (Iterator it14 = arrayList7.iterator(); it14.hasNext(); it14 = it14) {
                            Object sharedElementLastInViews = it14.next();
                            Intrinsics.checkNotNullExpressionValue(sharedElementLastInViews, "sharedElementLastInViews");
                            View view3 = (View) sharedElementLastInViews;
                            Log.v("FragmentManager", "View: " + view3 + " Name: " + ViewCompat.getTransitionName(view3));
                        }
                    }
                    fragmentTransitionImpl.beginDelayedTransition(getContainer(), mergeTransitionsInSequence);
                    ViewGroup container = getContainer();
                    final int size2 = arrayList7.size();
                    final ArrayList arrayList11 = new ArrayList();
                    int i3 = 0;
                    while (i3 < size2) {
                        View view4 = (View) arrayList6.get(i3);
                        ArrayList arrayList12 = arrayList8;
                        String transitionName = ViewCompat.getTransitionName(view4);
                        arrayList11.add(transitionName);
                        if (transitionName == null) {
                            list3 = mutableList;
                            arrayMap = arrayMap2;
                        } else {
                            list3 = mutableList;
                            ViewCompat.setTransitionName(view4, null);
                            String str = (String) arrayMap2.get(transitionName);
                            int i4 = 0;
                            while (true) {
                                arrayMap = arrayMap2;
                                if (i4 >= size2) {
                                    break;
                                }
                                if (str.equals(arrayList10.get(i4))) {
                                    ViewCompat.setTransitionName((View) arrayList7.get(i4), transitionName);
                                    break;
                                } else {
                                    i4++;
                                    arrayMap2 = arrayMap;
                                }
                            }
                        }
                        i3++;
                        arrayList8 = arrayList12;
                        arrayMap2 = arrayMap;
                        mutableList = list3;
                    }
                    list2 = mutableList;
                    OneShotPreDrawListener.add(container, new Runnable() { // from class: androidx.fragment.app.FragmentTransitionImpl.1
                        final /* synthetic */ ArrayList val$inNames;
                        final /* synthetic */ int val$numSharedElements;
                        final /* synthetic */ ArrayList val$outNames;
                        final /* synthetic */ ArrayList val$sharedElementsIn;
                        final /* synthetic */ ArrayList val$sharedElementsOut;

                        RunnableC01401(final int size22, final ArrayList arrayList72, final ArrayList arrayList102, final ArrayList arrayList62, final ArrayList arrayList112) {
                            r1 = size22;
                            r2 = arrayList72;
                            r3 = arrayList102;
                            r4 = arrayList62;
                            r5 = arrayList112;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            for (int i5 = 0; i5 < r1; i5++) {
                                ViewCompat.setTransitionName((View) r2.get(i5), (String) r3.get(i5));
                                ViewCompat.setTransitionName((View) r4.get(i5), (String) r5.get(i5));
                            }
                        }
                    });
                    FragmentTransition.setViewVisibility(arrayList8, 0);
                    fragmentTransitionImpl.swapSharedElementTargets(arrayList62, arrayList72);
                    boolean containsValue = linkedHashMap.containsValue(Boolean.TRUE);
                    Context context = getContainer().getContext();
                    ArrayList arrayList13 = new ArrayList();
                    it = arrayList2.iterator();
                    if (!it.hasNext()) {
                        ((AnimationInfo) it.next()).isVisibilityUnchanged();
                        throw null;
                    }
                    Iterator it15 = arrayList13.iterator();
                    while (it15.hasNext()) {
                        final AnimationInfo animationInfo = (AnimationInfo) it15.next();
                        final SpecialEffectsController.Operation operation4 = animationInfo.getOperation();
                        Fragment fragment2 = operation4.getFragment();
                        if (!containsValue) {
                            fragment2.getClass();
                            Intrinsics.checkNotNullExpressionValue(context, "context");
                            FragmentAnim.AnimationOrAnimator animation = animationInfo.getAnimation(context);
                            if (animation == null) {
                                throw new IllegalStateException("Required value was null.".toString());
                            }
                            Animation animation2 = animation.animation;
                            if (animation2 == null) {
                                throw new IllegalStateException("Required value was null.".toString());
                            }
                            if (operation4.getFinalState() != SpecialEffectsController.Operation.State.REMOVED) {
                                throw null;
                            }
                            getContainer().startViewTransition(null);
                            new FragmentAnim.EndViewTransitionAnimation(animation2, getContainer()).setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$startAnimations$3
                                @Override // android.view.animation.Animation.AnimationListener
                                public final void onAnimationEnd(Animation animation3) {
                                    Intrinsics.checkNotNullParameter(animation3, "animation");
                                    this.getContainer().post(new DefaultSpecialEffectsController$$ExternalSyntheticLambda0(this, (View) null, animationInfo));
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Log.v("FragmentManager", "Animation from operation " + operation4 + " has ended.");
                                    }
                                }

                                @Override // android.view.animation.Animation.AnimationListener
                                public final void onAnimationRepeat(Animation animation3) {
                                    Intrinsics.checkNotNullParameter(animation3, "animation");
                                }

                                @Override // android.view.animation.Animation.AnimationListener
                                public final void onAnimationStart(Animation animation3) {
                                    Intrinsics.checkNotNullParameter(animation3, "animation");
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Log.v("FragmentManager", "Animation from operation " + operation4 + " has reached onAnimationStart.");
                                    }
                                }
                            });
                            throw null;
                        }
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Transitions.");
                        }
                        animationInfo.completeSpecialEffect();
                    }
                    ArrayList arrayList14 = (ArrayList) list2;
                    Iterator it16 = arrayList14.iterator();
                    if (it16.hasNext()) {
                        ((SpecialEffectsController.Operation) it16.next()).getFragment().getClass();
                        Intrinsics.checkNotNullExpressionValue(null, "view");
                        throw null;
                    }
                    arrayList14.clear();
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "Completed executing operations from null to null");
                        return;
                    }
                    return;
                }
            }
        }
        list2 = mutableList;
        boolean containsValue2 = linkedHashMap.containsValue(Boolean.TRUE);
        Context context2 = getContainer().getContext();
        ArrayList arrayList132 = new ArrayList();
        it = arrayList2.iterator();
        if (!it.hasNext()) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DefaultSpecialEffectsController.kt */
    static final class TransitionInfo extends SpecialEffectsInfo {
        private final Object sharedElementTransition;
        private final Object transition;

        /* JADX WARN: Code restructure failed: missing block: B:24:0x0031, code lost:
        
            if (r5 == androidx.fragment.app.Fragment.USE_DEFAULT_TRANSITION) goto L22;
         */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x001b, code lost:
        
            if (r5 == androidx.fragment.app.Fragment.USE_DEFAULT_TRANSITION) goto L22;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public TransitionInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal, boolean z) {
            super(operation, cancellationSignal);
            Object obj;
            SpecialEffectsController.Operation.State finalState = operation.getFinalState();
            SpecialEffectsController.Operation.State state = SpecialEffectsController.Operation.State.VISIBLE;
            if (finalState == state) {
                Fragment fragment = operation.getFragment();
                if (z) {
                    Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
                    if (animationInfo != null) {
                        obj = animationInfo.mReenterTransition;
                    }
                } else {
                    fragment.getClass();
                }
                obj = null;
            } else {
                Fragment fragment2 = operation.getFragment();
                if (z) {
                    Fragment.AnimationInfo animationInfo2 = fragment2.mAnimationInfo;
                    if (animationInfo2 != null) {
                        obj = animationInfo2.mReturnTransition;
                    }
                } else {
                    fragment2.getClass();
                }
                obj = null;
            }
            this.transition = obj;
            if (operation.getFinalState() == state) {
                if (z) {
                    Fragment.AnimationInfo animationInfo3 = operation.getFragment().mAnimationInfo;
                } else {
                    Fragment.AnimationInfo animationInfo4 = operation.getFragment().mAnimationInfo;
                }
            }
            this.sharedElementTransition = null;
        }

        public final FragmentTransitionImpl getHandlingImpl() {
            Object obj = this.transition;
            FragmentTransitionImpl handlingImpl = getHandlingImpl(obj);
            Object obj2 = this.sharedElementTransition;
            FragmentTransitionImpl handlingImpl2 = getHandlingImpl(obj2);
            if (handlingImpl == null || handlingImpl2 == null || handlingImpl == handlingImpl2) {
                return handlingImpl == null ? handlingImpl2 : handlingImpl;
            }
            throw new IllegalArgumentException(("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + getOperation().getFragment() + " returned Transition " + obj + " which uses a different Transition  type than its shared element transition " + obj2).toString());
        }

        public final Object getTransition() {
            return this.transition;
        }

        private final FragmentTransitionImpl getHandlingImpl(Object obj) {
            if (obj == null) {
                return null;
            }
            FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.PLATFORM_IMPL;
            if (fragmentTransitionImpl != null) {
                ((FragmentTransitionCompat21) fragmentTransitionImpl).getClass();
                if (obj instanceof Transition) {
                    return fragmentTransitionImpl;
                }
            }
            FragmentTransitionImpl fragmentTransitionImpl2 = FragmentTransition.SUPPORT_IMPL;
            if (fragmentTransitionImpl2 != null && fragmentTransitionImpl2.canHandle(obj)) {
                return fragmentTransitionImpl2;
            }
            throw new IllegalArgumentException("Transition " + obj + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
        }
    }
}
