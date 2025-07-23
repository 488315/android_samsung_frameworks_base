package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Rect;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.activity.BackEventCompat;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DefaultSpecialEffectsController;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.SpecialEffectsController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DefaultSpecialEffectsController extends SpecialEffectsController {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AnimationEffect extends SpecialEffectsController.Effect {
        public final AnimationInfo animationInfo;

        public AnimationEffect(AnimationInfo animationInfo) {
            this.animationInfo = animationInfo;
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final void onCancel(ViewGroup viewGroup) {
            AnimationInfo animationInfo = this.animationInfo;
            SpecialEffectsController.Operation operation = animationInfo.operation;
            View view = operation.fragment.mView;
            view.clearAnimation();
            viewGroup.endViewTransition(view);
            animationInfo.operation.completeEffect(this);
            if (FragmentManager.isLoggingEnabled(2)) {
                operation.toString();
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final void onCommit(final ViewGroup viewGroup) {
            AnimationInfo animationInfo = this.animationInfo;
            boolean isVisibilityUnchanged = animationInfo.isVisibilityUnchanged();
            final SpecialEffectsController.Operation operation = animationInfo.operation;
            if (isVisibilityUnchanged) {
                operation.completeEffect(this);
                return;
            }
            Context context = viewGroup.getContext();
            Fragment fragment = operation.fragment;
            final View view = fragment.mView;
            FragmentAnim.AnimationOrAnimator animation = animationInfo.getAnimation(context);
            if (animation == null) {
                throw new IllegalStateException("Required value was null.");
            }
            Animation animation2 = animation.animation;
            if (animation2 == null) {
                throw new IllegalStateException("Required value was null.");
            }
            if (operation.finalState != SpecialEffectsController.Operation.State.REMOVED) {
                fragment.isPredictiveBackEnabled();
                view.startAnimation(animation2);
                operation.completeEffect(this);
                return;
            }
            viewGroup.startViewTransition(view);
            fragment.isPredictiveBackEnabled();
            FragmentAnim.EndViewTransitionAnimation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(animation2, viewGroup, view);
            endViewTransitionAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$AnimationEffect$onCommit$3
                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationEnd(Animation animation3) {
                    ViewGroup viewGroup2 = viewGroup;
                    viewGroup2.post(new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda3(viewGroup2, view, 1, this));
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(SpecialEffectsController.Operation.this);
                    }
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationStart(Animation animation3) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(SpecialEffectsController.Operation.this);
                    }
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationRepeat(Animation animation3) {
                }
            });
            view.startAnimation(endViewTransitionAnimation);
            if (FragmentManager.isLoggingEnabled(2)) {
                operation.toString();
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final void onProgress(BackEventCompat backEventCompat) {
            this.animationInfo.operation.fragment.isPredictiveBackEnabled();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AnimationInfo extends SpecialEffectsInfo {
        public FragmentAnim.AnimationOrAnimator animation;
        public boolean isAnimLoaded;
        public final boolean isPop;

        public AnimationInfo(SpecialEffectsController.Operation operation, boolean z) {
            super(operation);
            this.isPop = z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:54:0x00e7, code lost:
        
            r8.getWindow().getDecorView().setBackgroundColor(r1.getResources().getColor(com.android.systemui.R.color.sesl_fragment_fgcolor));
         */
        /* JADX WARN: Removed duplicated region for block: B:100:0x0176  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0046  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0058  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0066  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x008d  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00c7  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x009d A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:71:0x0119 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:74:0x011f  */
        /* JADX WARN: Removed duplicated region for block: B:96:0x016a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final androidx.fragment.app.FragmentAnim.AnimationOrAnimator getAnimation(android.content.Context r14) {
            /*
                Method dump skipped, instructions count: 438
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DefaultSpecialEffectsController.AnimationInfo.getAnimation(android.content.Context):androidx.fragment.app.FragmentAnim$AnimationOrAnimator");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AnimatorEffect extends SpecialEffectsController.Effect {
        public AnimatorSet animator;
        public final AnimationInfo animatorInfo;

        public AnimatorEffect(AnimationInfo animationInfo) {
            this.animatorInfo = animationInfo;
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final void onCancel(ViewGroup viewGroup) {
            AnimatorSet animatorSet = this.animator;
            AnimationInfo animationInfo = this.animatorInfo;
            if (animatorSet == null) {
                animationInfo.operation.completeEffect(this);
                return;
            }
            SpecialEffectsController.Operation operation = animationInfo.operation;
            if (operation.isSeeking) {
                Api26Impl.INSTANCE.getClass();
                animatorSet.reverse();
            } else {
                animatorSet.end();
            }
            if (FragmentManager.isLoggingEnabled(2)) {
                operation.toString();
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final void onCommit(ViewGroup viewGroup) {
            SpecialEffectsController.Operation operation = this.animatorInfo.operation;
            AnimatorSet animatorSet = this.animator;
            if (animatorSet == null) {
                operation.completeEffect(this);
                return;
            }
            animatorSet.start();
            if (FragmentManager.isLoggingEnabled(2)) {
                Objects.toString(operation);
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final void onProgress(BackEventCompat backEventCompat) {
            SpecialEffectsController.Operation operation = this.animatorInfo.operation;
            AnimatorSet animatorSet = this.animator;
            if (animatorSet == null) {
                operation.completeEffect(this);
                return;
            }
            if (operation.fragment.mTransitioning) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    operation.toString();
                }
                Api24Impl.INSTANCE.getClass();
                long totalDuration = animatorSet.getTotalDuration();
                long j = (long) (backEventCompat.progress * totalDuration);
                if (j == 0) {
                    j = 1;
                }
                if (j == totalDuration) {
                    j = totalDuration - 1;
                }
                if (FragmentManager.isLoggingEnabled(2)) {
                    animatorSet.toString();
                    operation.toString();
                }
                Api26Impl.INSTANCE.getClass();
                animatorSet.setCurrentPlayTime(j);
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final void onStart(final ViewGroup viewGroup) {
            final AnimatorEffect animatorEffect;
            AnimationInfo animationInfo = this.animatorInfo;
            if (animationInfo.isVisibilityUnchanged()) {
                return;
            }
            FragmentAnim.AnimationOrAnimator animation = animationInfo.getAnimation(viewGroup.getContext());
            this.animator = animation != null ? animation.animator : null;
            final SpecialEffectsController.Operation operation = animationInfo.operation;
            Fragment fragment = operation.fragment;
            final boolean z = operation.finalState == SpecialEffectsController.Operation.State.GONE;
            final View view = fragment.mView;
            viewGroup.startViewTransition(view);
            AnimatorSet animatorSet = this.animator;
            if (animatorSet != null) {
                animatorEffect = this;
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$AnimatorEffect$onStart$1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        viewGroup.endViewTransition(view);
                        if (z) {
                            operation.finalState.applyState(view, viewGroup);
                        }
                        DefaultSpecialEffectsController.AnimatorEffect animatorEffect2 = animatorEffect;
                        animatorEffect2.animatorInfo.operation.completeEffect(animatorEffect2);
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Objects.toString(operation);
                        }
                    }
                });
            } else {
                animatorEffect = this;
            }
            AnimatorSet animatorSet2 = animatorEffect.animator;
            if (animatorSet2 != null) {
                animatorSet2.setTarget(view);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Api24Impl {
        public static final Api24Impl INSTANCE = new Api24Impl();

        private Api24Impl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Api26Impl {
        public static final Api26Impl INSTANCE = new Api26Impl();

        private Api26Impl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SpecialEffectsInfo {
        public final SpecialEffectsController.Operation operation;

        public SpecialEffectsInfo(SpecialEffectsController.Operation operation) {
            this.operation = operation;
        }

        public final boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State state;
            SpecialEffectsController.Operation operation = this.operation;
            View view = operation.fragment.mView;
            if (view != null) {
                SpecialEffectsController.Operation.State.Companion.getClass();
                state = SpecialEffectsController.Operation.State.Companion.asOperationState(view);
            } else {
                state = null;
            }
            SpecialEffectsController.Operation.State state2 = operation.finalState;
            if (state == state2) {
                return true;
            }
            SpecialEffectsController.Operation.State state3 = SpecialEffectsController.Operation.State.VISIBLE;
            return (state == state3 || state2 == state3) ? false : true;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TransitionEffect extends SpecialEffectsController.Effect {
        public Object controller;
        public final ArrayList enteringNames;
        public final ArrayList exitingNames;
        public final SpecialEffectsController.Operation firstOut;
        public final ArrayMap firstOutViews;
        public final boolean isPop;
        public final SpecialEffectsController.Operation lastIn;
        public final ArrayMap lastInViews;
        public final ArrayList sharedElementFirstOutViews;
        public final ArrayList sharedElementLastInViews;
        public final ArrayMap sharedElementNameMapping;
        public final Object sharedElementTransition;
        public final FragmentTransitionImpl transitionImpl;
        public final List transitionInfos;
        public final CancellationSignal transitionSignal = new CancellationSignal();

        public TransitionEffect(List<TransitionInfo> list, SpecialEffectsController.Operation operation, SpecialEffectsController.Operation operation2, FragmentTransitionImpl fragmentTransitionImpl, Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2, ArrayMap arrayMap, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayMap arrayMap2, ArrayMap arrayMap3, boolean z) {
            this.transitionInfos = list;
            this.firstOut = operation;
            this.lastIn = operation2;
            this.transitionImpl = fragmentTransitionImpl;
            this.sharedElementTransition = obj;
            this.sharedElementFirstOutViews = arrayList;
            this.sharedElementLastInViews = arrayList2;
            this.sharedElementNameMapping = arrayMap;
            this.enteringNames = arrayList3;
            this.exitingNames = arrayList4;
            this.firstOutViews = arrayMap2;
            this.lastInViews = arrayMap3;
            this.isPop = z;
        }

        public static void captureTransitioningViews(ArrayList arrayList, View view) {
            if (!(view instanceof ViewGroup)) {
                if (arrayList.contains(view)) {
                    return;
                }
                arrayList.add(view);
                return;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.isTransitionGroup()) {
                if (arrayList.contains(view)) {
                    return;
                }
                arrayList.add(view);
                return;
            }
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    captureTransitioningViews(arrayList, childAt);
                }
            }
        }

        public final Pair createMergedTransition(ViewGroup viewGroup, SpecialEffectsController.Operation operation, SpecialEffectsController.Operation operation2) {
            Object obj;
            FragmentTransitionImpl fragmentTransitionImpl;
            View view;
            TransitionEffect transitionEffect = this;
            SpecialEffectsController.Operation operation3 = operation;
            int i = 2;
            View view2 = new View(viewGroup.getContext());
            Rect rect = new Rect();
            Iterator it = transitionEffect.transitionInfos.iterator();
            int i2 = 0;
            boolean z = false;
            View view3 = null;
            while (true) {
                boolean hasNext = it.hasNext();
                obj = transitionEffect.sharedElementTransition;
                fragmentTransitionImpl = transitionEffect.transitionImpl;
                if (!hasNext) {
                    break;
                }
                if (((TransitionInfo) it.next()).sharedElementTransition != null && operation2 != null && operation3 != null && !transitionEffect.sharedElementNameMapping.isEmpty() && obj != null) {
                    FragmentTransitionCompat21 fragmentTransitionCompat21 = FragmentTransition.PLATFORM_IMPL;
                    if (transitionEffect.isPop) {
                        operation2.fragment.getClass();
                    } else {
                        operation3.fragment.getClass();
                    }
                    OneShotPreDrawListener.add(viewGroup, new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda3(operation3, operation2, i2, transitionEffect));
                    ArrayList arrayList = transitionEffect.sharedElementFirstOutViews;
                    ArrayMap arrayMap = transitionEffect.firstOutViews;
                    arrayList.addAll(arrayMap.values());
                    if (!transitionEffect.exitingNames.isEmpty()) {
                        View view4 = (View) arrayMap.get((String) transitionEffect.exitingNames.get(0));
                        fragmentTransitionImpl.setEpicenter(view4, obj);
                        view3 = view4;
                    }
                    ArrayList arrayList2 = transitionEffect.sharedElementLastInViews;
                    ArrayMap arrayMap2 = transitionEffect.lastInViews;
                    arrayList2.addAll(arrayMap2.values());
                    if (!transitionEffect.enteringNames.isEmpty() && (view = (View) arrayMap2.get((String) transitionEffect.enteringNames.get(0))) != null) {
                        OneShotPreDrawListener.add(viewGroup, new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda3(fragmentTransitionImpl, view, i, rect));
                        z = true;
                    }
                    fragmentTransitionImpl.setSharedElementTargets(obj, view2, transitionEffect.sharedElementFirstOutViews);
                    ArrayList arrayList3 = transitionEffect.sharedElementLastInViews;
                    FragmentTransitionImpl fragmentTransitionImpl2 = transitionEffect.transitionImpl;
                    Object obj2 = transitionEffect.sharedElementTransition;
                    fragmentTransitionImpl2.scheduleRemoveTargets(obj2, null, null, obj2, arrayList3);
                }
            }
            ArrayList arrayList4 = new ArrayList();
            Iterator it2 = transitionEffect.transitionInfos.iterator();
            Object obj3 = null;
            Object obj4 = null;
            while (it2.hasNext()) {
                int i3 = i;
                TransitionInfo transitionInfo = (TransitionInfo) it2.next();
                SpecialEffectsController.Operation operation4 = transitionInfo.operation;
                Iterator it3 = it2;
                Object cloneTransition = fragmentTransitionImpl.cloneTransition(transitionInfo.transition);
                if (cloneTransition != null) {
                    boolean z2 = z;
                    ArrayList arrayList5 = new ArrayList();
                    Object obj5 = obj;
                    captureTransitioningViews(arrayList5, operation4.fragment.mView);
                    if (obj5 != null && (operation4 == operation2 || operation4 == operation3)) {
                        if (operation4 == operation2) {
                            arrayList5.removeAll(CollectionsKt___CollectionsKt.toSet(transitionEffect.sharedElementFirstOutViews));
                        } else {
                            arrayList5.removeAll(CollectionsKt___CollectionsKt.toSet(transitionEffect.sharedElementLastInViews));
                        }
                    }
                    if (arrayList5.isEmpty()) {
                        fragmentTransitionImpl.addTarget(view2, cloneTransition);
                    } else {
                        fragmentTransitionImpl.addTargets(cloneTransition, arrayList5);
                        transitionEffect.transitionImpl.scheduleRemoveTargets(cloneTransition, cloneTransition, arrayList5, null, null);
                        if (operation4.finalState == SpecialEffectsController.Operation.State.GONE) {
                            operation4.isAwaitingContainerChanges = false;
                            ArrayList arrayList6 = new ArrayList(arrayList5);
                            Fragment fragment = operation4.fragment;
                            arrayList6.remove(fragment.mView);
                            fragmentTransitionImpl.scheduleHideFragmentView(cloneTransition, fragment.mView, arrayList6);
                            OneShotPreDrawListener.add(viewGroup, new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0(arrayList5));
                        }
                    }
                    if (operation4.finalState == SpecialEffectsController.Operation.State.VISIBLE) {
                        arrayList4.addAll(arrayList5);
                        if (z2) {
                            fragmentTransitionImpl.setEpicenter(cloneTransition, rect);
                        }
                        if (FragmentManager.isLoggingEnabled(i3)) {
                            cloneTransition.toString();
                            int size = arrayList5.size();
                            int i4 = 0;
                            while (i4 < size) {
                                Object obj6 = arrayList5.get(i4);
                                i4++;
                                Objects.toString((View) obj6);
                            }
                        }
                    } else {
                        fragmentTransitionImpl.setEpicenter(view3, cloneTransition);
                        if (FragmentManager.isLoggingEnabled(i3)) {
                            cloneTransition.toString();
                            int size2 = arrayList5.size();
                            int i5 = 0;
                            while (i5 < size2) {
                                Object obj7 = arrayList5.get(i5);
                                i5++;
                                Objects.toString((View) obj7);
                            }
                        }
                    }
                    if (transitionInfo.isOverlapAllowed) {
                        obj3 = fragmentTransitionImpl.mergeTransitionsTogether(obj3, cloneTransition);
                    } else {
                        obj4 = fragmentTransitionImpl.mergeTransitionsTogether(obj4, cloneTransition);
                    }
                    transitionEffect = this;
                    operation3 = operation;
                    i = i3;
                    it2 = it3;
                    z = z2;
                    obj = obj5;
                } else {
                    transitionEffect = this;
                    operation3 = operation;
                    i = i3;
                    it2 = it3;
                }
            }
            Object mergeTransitionsInSequence = fragmentTransitionImpl.mergeTransitionsInSequence(obj3, obj4, obj);
            if (FragmentManager.isLoggingEnabled(i)) {
                Objects.toString(mergeTransitionsInSequence);
            }
            return new Pair(arrayList4, mergeTransitionsInSequence);
        }

        public final boolean getTransitioning() {
            List list = this.transitionInfos;
            if ((list instanceof Collection) && list.isEmpty()) {
                return true;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (!((TransitionInfo) it.next()).operation.fragment.mTransitioning) {
                    return false;
                }
            }
            return true;
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final boolean isSeekingSupported() {
            FragmentTransitionImpl fragmentTransitionImpl = this.transitionImpl;
            if (!fragmentTransitionImpl.isSeekingSupported()) {
                return false;
            }
            List list = this.transitionInfos;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    Object obj = ((TransitionInfo) it.next()).transition;
                    if (obj == null || !fragmentTransitionImpl.isSeekingSupported(obj)) {
                        return false;
                    }
                }
            }
            Object obj2 = this.sharedElementTransition;
            return obj2 == null || fragmentTransitionImpl.isSeekingSupported(obj2);
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final void onCancel(ViewGroup viewGroup) {
            this.transitionSignal.cancel();
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final void onCommit(final ViewGroup viewGroup) {
            if (!viewGroup.isLaidOut()) {
                for (TransitionInfo transitionInfo : this.transitionInfos) {
                    SpecialEffectsController.Operation operation = transitionInfo.operation;
                    if (FragmentManager.isLoggingEnabled(2)) {
                        viewGroup.toString();
                        Objects.toString(operation);
                    }
                    transitionInfo.operation.completeEffect(this);
                }
                return;
            }
            Object obj = this.controller;
            FragmentTransitionImpl fragmentTransitionImpl = this.transitionImpl;
            SpecialEffectsController.Operation operation2 = this.lastIn;
            SpecialEffectsController.Operation operation3 = this.firstOut;
            if (obj != null) {
                fragmentTransitionImpl.animateToEnd(obj);
                if (FragmentManager.isLoggingEnabled(2)) {
                    Objects.toString(operation3);
                    Objects.toString(operation2);
                    return;
                }
                return;
            }
            Pair createMergedTransition = createMergedTransition(viewGroup, operation2, operation3);
            ArrayList arrayList = (ArrayList) createMergedTransition.component1();
            final Object component2 = createMergedTransition.component2();
            List list = this.transitionInfos;
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add(((TransitionInfo) it.next()).operation);
            }
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                SpecialEffectsController.Operation operation4 = (SpecialEffectsController.Operation) obj2;
                fragmentTransitionImpl.setListenerForTransitionEnd(operation4.fragment, component2, this.transitionSignal, new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda1(operation4, this, 1));
            }
            runTransition(arrayList, viewGroup, new Function0() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onCommit$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DefaultSpecialEffectsController.TransitionEffect.this.transitionImpl.beginDelayedTransition(viewGroup, component2);
                    return Unit.INSTANCE;
                }
            });
            if (FragmentManager.isLoggingEnabled(2)) {
                Objects.toString(operation3);
                Objects.toString(operation2);
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final void onProgress(BackEventCompat backEventCompat) {
            Object obj = this.controller;
            if (obj != null) {
                this.transitionImpl.setCurrentPlayTime(obj, backEventCompat.progress);
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public final void onStart(final ViewGroup viewGroup) {
            Object obj;
            if (!viewGroup.isLaidOut()) {
                Iterator it = this.transitionInfos.iterator();
                while (it.hasNext()) {
                    SpecialEffectsController.Operation operation = ((TransitionInfo) it.next()).operation;
                    if (FragmentManager.isLoggingEnabled(2)) {
                        viewGroup.toString();
                        Objects.toString(operation);
                    }
                }
                return;
            }
            boolean transitioning = getTransitioning();
            SpecialEffectsController.Operation operation2 = this.lastIn;
            SpecialEffectsController.Operation operation3 = this.firstOut;
            if (transitioning && (obj = this.sharedElementTransition) != null && !isSeekingSupported()) {
                Log.i("FragmentManager", "Ignoring shared elements transition " + obj + " between " + operation3 + " and " + operation2 + " as neither fragment has set a Transition. In order to run a SharedElementTransition, you must also set either an enter or exit transition on a fragment involved in the transaction. The sharedElementTransition will run after the back gesture has been committed.");
            }
            if (isSeekingSupported() && getTransitioning()) {
                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                Pair createMergedTransition = createMergedTransition(viewGroup, operation2, operation3);
                ArrayList arrayList = (ArrayList) createMergedTransition.component1();
                final Object component2 = createMergedTransition.component2();
                List list = this.transitionInfos;
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(((TransitionInfo) it2.next()).operation);
                }
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj2 = arrayList2.get(i);
                    i++;
                    SpecialEffectsController.Operation operation4 = (SpecialEffectsController.Operation) obj2;
                    DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0 defaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0 = new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0(ref$ObjectRef, 0);
                    Fragment fragment = operation4.fragment;
                    this.transitionImpl.setListenerForTransitionEnd(component2, this.transitionSignal, defaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0, new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda1(operation4, this, 0));
                }
                runTransition(arrayList, viewGroup, new Function0() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* JADX WARN: Type inference failed for: r4v0, types: [T, androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4$2] */
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        DefaultSpecialEffectsController.TransitionEffect transitionEffect = DefaultSpecialEffectsController.TransitionEffect.this;
                        transitionEffect.controller = transitionEffect.transitionImpl.controlDelayedTransition(viewGroup, component2);
                        final DefaultSpecialEffectsController.TransitionEffect transitionEffect2 = DefaultSpecialEffectsController.TransitionEffect.this;
                        boolean z = transitionEffect2.controller != null;
                        final Object obj3 = component2;
                        final ViewGroup viewGroup2 = viewGroup;
                        if (z) {
                            ref$ObjectRef.element = new Function0() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4.2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    List list2 = DefaultSpecialEffectsController.TransitionEffect.this.transitionInfos;
                                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                                        Iterator it3 = list2.iterator();
                                        while (it3.hasNext()) {
                                            if (!((DefaultSpecialEffectsController.TransitionInfo) it3.next()).operation.isSeeking) {
                                                CancellationSignal cancellationSignal = new CancellationSignal();
                                                DefaultSpecialEffectsController.TransitionEffect transitionEffect3 = DefaultSpecialEffectsController.TransitionEffect.this;
                                                transitionEffect3.transitionImpl.setListenerForTransitionEnd(((DefaultSpecialEffectsController.TransitionInfo) transitionEffect3.transitionInfos.get(0)).operation.fragment, obj3, cancellationSignal, new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0(DefaultSpecialEffectsController.TransitionEffect.this, 2));
                                                cancellationSignal.cancel();
                                                break;
                                            }
                                        }
                                    }
                                    DefaultSpecialEffectsController.TransitionEffect transitionEffect4 = DefaultSpecialEffectsController.TransitionEffect.this;
                                    FragmentTransitionImpl fragmentTransitionImpl = transitionEffect4.transitionImpl;
                                    Object obj4 = transitionEffect4.controller;
                                    obj4.getClass();
                                    fragmentTransitionImpl.animateToStart(obj4, new DefaultSpecialEffectsController$$ExternalSyntheticLambda0(1, DefaultSpecialEffectsController.TransitionEffect.this, viewGroup2));
                                    return Unit.INSTANCE;
                                }
                            };
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Objects.toString(DefaultSpecialEffectsController.TransitionEffect.this.firstOut);
                                Objects.toString(DefaultSpecialEffectsController.TransitionEffect.this.lastIn);
                            }
                            return Unit.INSTANCE;
                        }
                        throw new IllegalStateException(("Unable to start transition " + obj3 + " for container " + viewGroup2 + '.').toString());
                    }
                });
            }
        }

        public final void runTransition(ArrayList arrayList, ViewGroup viewGroup, Function0 function0) {
            FragmentTransition.setViewVisibility(4, arrayList);
            ArrayList arrayList2 = this.sharedElementLastInViews;
            final FragmentTransitionImpl fragmentTransitionImpl = this.transitionImpl;
            fragmentTransitionImpl.getClass();
            final ArrayList arrayList3 = new ArrayList();
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                View view = (View) arrayList2.get(i);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                arrayList3.add(ViewCompat.Api21Impl.getTransitionName(view));
                ViewCompat.Api21Impl.setTransitionName(view, null);
            }
            if (FragmentManager.isLoggingEnabled(2)) {
                ArrayList arrayList4 = this.sharedElementFirstOutViews;
                int size2 = arrayList4.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj = arrayList4.get(i2);
                    i2++;
                    View view2 = (View) obj;
                    Objects.toString(view2);
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api21Impl.getTransitionName(view2);
                }
                ArrayList arrayList5 = this.sharedElementLastInViews;
                int size3 = arrayList5.size();
                int i3 = 0;
                while (i3 < size3) {
                    Object obj2 = arrayList5.get(i3);
                    i3++;
                    View view3 = (View) obj2;
                    Objects.toString(view3);
                    WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api21Impl.getTransitionName(view3);
                }
            }
            function0.invoke();
            final ArrayList arrayList6 = this.sharedElementFirstOutViews;
            final ArrayList arrayList7 = this.sharedElementLastInViews;
            final int size4 = arrayList7.size();
            final ArrayList arrayList8 = new ArrayList();
            for (int i4 = 0; i4 < size4; i4++) {
                View view4 = (View) arrayList6.get(i4);
                WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                String transitionName = ViewCompat.Api21Impl.getTransitionName(view4);
                arrayList8.add(transitionName);
                if (transitionName != null) {
                    ViewCompat.Api21Impl.setTransitionName(view4, null);
                    String str = (String) this.sharedElementNameMapping.get(transitionName);
                    int i5 = 0;
                    while (true) {
                        if (i5 >= size4) {
                            break;
                        }
                        if (str.equals(arrayList3.get(i5))) {
                            ViewCompat.Api21Impl.setTransitionName((View) arrayList7.get(i5), transitionName);
                            break;
                        }
                        i5++;
                    }
                }
            }
            OneShotPreDrawListener.add(viewGroup, new Runnable(fragmentTransitionImpl, size4, arrayList7, arrayList3, arrayList6, arrayList8) { // from class: androidx.fragment.app.FragmentTransitionImpl.1
                public final /* synthetic */ ArrayList val$inNames;
                public final /* synthetic */ int val$numSharedElements;
                public final /* synthetic */ ArrayList val$outNames;
                public final /* synthetic */ ArrayList val$sharedElementsIn;
                public final /* synthetic */ ArrayList val$sharedElementsOut;

                public AnonymousClass1(final FragmentTransitionImpl fragmentTransitionImpl2, final int size42, final ArrayList arrayList72, final ArrayList arrayList32, final ArrayList arrayList62, final ArrayList arrayList82) {
                    this.val$numSharedElements = size42;
                    this.val$sharedElementsIn = arrayList72;
                    this.val$inNames = arrayList32;
                    this.val$sharedElementsOut = arrayList62;
                    this.val$outNames = arrayList82;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    for (int i6 = 0; i6 < this.val$numSharedElements; i6++) {
                        View view5 = (View) this.val$sharedElementsIn.get(i6);
                        String str2 = (String) this.val$inNames.get(i6);
                        WeakHashMap weakHashMap5 = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api21Impl.setTransitionName(view5, str2);
                        ViewCompat.Api21Impl.setTransitionName((View) this.val$sharedElementsOut.get(i6), (String) this.val$outNames.get(i6));
                    }
                }
            });
            FragmentTransition.setViewVisibility(0, arrayList);
            fragmentTransitionImpl2.swapSharedElementTargets(this.sharedElementTransition, this.sharedElementFirstOutViews, this.sharedElementLastInViews);
        }
    }

    public DefaultSpecialEffectsController(ViewGroup viewGroup) {
        super(viewGroup);
    }

    public static void findNamedViews(ArrayMap arrayMap, View view) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        String transitionName = ViewCompat.Api21Impl.getTransitionName(view);
        if (transitionName != null) {
            arrayMap.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    findNamedViews(arrayMap, childAt);
                }
            }
        }
    }

    @Override // androidx.fragment.app.SpecialEffectsController
    public final void collectEffects(List list, boolean z) {
        Object obj;
        Object obj2;
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ArrayList arrayList4;
        ArrayList arrayList5;
        String findKeyForValue;
        int i = 0;
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            SpecialEffectsController.Operation operation = (SpecialEffectsController.Operation) obj;
            SpecialEffectsController.Operation.State.Companion companion = SpecialEffectsController.Operation.State.Companion;
            View view = operation.fragment.mView;
            companion.getClass();
            SpecialEffectsController.Operation.State asOperationState = SpecialEffectsController.Operation.State.Companion.asOperationState(view);
            SpecialEffectsController.Operation.State state = SpecialEffectsController.Operation.State.VISIBLE;
            if (asOperationState == state && operation.finalState != state) {
                break;
            }
        }
        SpecialEffectsController.Operation operation2 = (SpecialEffectsController.Operation) obj;
        ArrayList arrayList6 = (ArrayList) list;
        ListIterator listIterator = arrayList6.listIterator(arrayList6.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                obj2 = null;
                break;
            }
            obj2 = listIterator.previous();
            SpecialEffectsController.Operation operation3 = (SpecialEffectsController.Operation) obj2;
            SpecialEffectsController.Operation.State.Companion companion2 = SpecialEffectsController.Operation.State.Companion;
            View view2 = operation3.fragment.mView;
            companion2.getClass();
            SpecialEffectsController.Operation.State asOperationState2 = SpecialEffectsController.Operation.State.Companion.asOperationState(view2);
            SpecialEffectsController.Operation.State state2 = SpecialEffectsController.Operation.State.VISIBLE;
            if (asOperationState2 != state2 && operation3.finalState == state2) {
                break;
            }
        }
        SpecialEffectsController.Operation operation4 = (SpecialEffectsController.Operation) obj2;
        if (FragmentManager.isLoggingEnabled(2)) {
            Objects.toString(operation2);
            Objects.toString(operation4);
        }
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = new ArrayList();
        Fragment fragment = ((SpecialEffectsController.Operation) CollectionsKt___CollectionsKt.last(list)).fragment;
        int size = arrayList6.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj3 = arrayList6.get(i2);
            i2++;
            Fragment.AnimationInfo animationInfo = ((SpecialEffectsController.Operation) obj3).fragment.mAnimationInfo;
            Fragment.AnimationInfo animationInfo2 = fragment.mAnimationInfo;
            animationInfo.mEnterAnim = animationInfo2.mEnterAnim;
            animationInfo.mExitAnim = animationInfo2.mExitAnim;
            animationInfo.mPopEnterAnim = animationInfo2.mPopEnterAnim;
            animationInfo.mPopExitAnim = animationInfo2.mPopExitAnim;
        }
        int size2 = arrayList6.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj4 = arrayList6.get(i3);
            i3++;
            SpecialEffectsController.Operation operation5 = (SpecialEffectsController.Operation) obj4;
            arrayList7.add(new AnimationInfo(operation5, z));
            arrayList8.add(new TransitionInfo(operation5, z, !z ? operation5 != operation4 : operation5 != operation2));
            ((ArrayList) operation5.completionListeners).add(new DefaultSpecialEffectsController$$ExternalSyntheticLambda0(i, this, operation5));
        }
        ArrayList arrayList9 = new ArrayList();
        int size3 = arrayList8.size();
        int i4 = 0;
        while (i4 < size3) {
            Object obj5 = arrayList8.get(i4);
            i4++;
            if (!((TransitionInfo) obj5).isVisibilityUnchanged()) {
                arrayList9.add(obj5);
            }
        }
        ArrayList arrayList10 = new ArrayList();
        int size4 = arrayList9.size();
        int i5 = 0;
        while (i5 < size4) {
            Object obj6 = arrayList9.get(i5);
            i5++;
            if (((TransitionInfo) obj6).getHandlingImpl() != null) {
                arrayList10.add(obj6);
            }
        }
        int size5 = arrayList10.size();
        int i6 = 0;
        FragmentTransitionImpl fragmentTransitionImpl = null;
        while (i6 < size5) {
            Object obj7 = arrayList10.get(i6);
            i6++;
            TransitionInfo transitionInfo = (TransitionInfo) obj7;
            FragmentTransitionImpl handlingImpl = transitionInfo.getHandlingImpl();
            if (fragmentTransitionImpl != null && handlingImpl != fragmentTransitionImpl) {
                throw new IllegalArgumentException(("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + transitionInfo.operation.fragment + " returned Transition " + transitionInfo.transition + " which uses a different Transition type than other Fragments.").toString());
            }
            fragmentTransitionImpl = handlingImpl;
        }
        if (fragmentTransitionImpl == null) {
            arrayList = arrayList7;
        } else {
            ArrayList arrayList11 = new ArrayList();
            ArrayList arrayList12 = new ArrayList();
            ArrayList arrayList13 = arrayList7;
            ArrayMap arrayMap = new ArrayMap();
            ArrayList arrayList14 = new ArrayList();
            ArrayList arrayList15 = new ArrayList();
            ArrayMap arrayMap2 = new ArrayMap();
            ArrayList arrayList16 = arrayList15;
            ArrayMap arrayMap3 = new ArrayMap();
            int size6 = arrayList10.size();
            int i7 = 0;
            Object obj8 = null;
            while (i7 < size6) {
                Object obj9 = arrayList10.get(i7);
                int i8 = i7 + 1;
                Object obj10 = ((TransitionInfo) obj9).sharedElementTransition;
                if (obj10 == null || operation2 == null || operation4 == null) {
                    arrayList11 = arrayList11;
                    i7 = i8;
                    fragmentTransitionImpl = fragmentTransitionImpl;
                    arrayList12 = arrayList12;
                    arrayList13 = arrayList13;
                } else {
                    Object wrapTransitionInSet = fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(obj10));
                    Fragment fragment2 = operation4.fragment;
                    ArrayList arrayList17 = arrayList11;
                    Fragment.AnimationInfo animationInfo3 = fragment2.mAnimationInfo;
                    if (animationInfo3 == null || (arrayList2 = animationInfo3.mSharedElementSourceNames) == null) {
                        arrayList2 = new ArrayList();
                    }
                    Fragment fragment3 = operation2.fragment;
                    FragmentTransitionImpl fragmentTransitionImpl2 = fragmentTransitionImpl;
                    Fragment.AnimationInfo animationInfo4 = fragment3.mAnimationInfo;
                    if (animationInfo4 == null || (arrayList3 = animationInfo4.mSharedElementSourceNames) == null) {
                        arrayList3 = new ArrayList();
                    }
                    ArrayList arrayList18 = arrayList12;
                    Fragment.AnimationInfo animationInfo5 = fragment3.mAnimationInfo;
                    if (animationInfo5 == null || (arrayList4 = animationInfo5.mSharedElementTargetNames) == null) {
                        arrayList4 = new ArrayList();
                    }
                    ArrayList arrayList19 = arrayList13;
                    int size7 = arrayList4.size();
                    int i9 = 0;
                    while (i9 < size7) {
                        int i10 = size7;
                        int indexOf = arrayList2.indexOf(arrayList4.get(i9));
                        ArrayList arrayList20 = arrayList4;
                        if (indexOf != -1) {
                            arrayList2.set(indexOf, arrayList3.get(i9));
                        }
                        i9++;
                        size7 = i10;
                        arrayList4 = arrayList20;
                    }
                    Fragment.AnimationInfo animationInfo6 = fragment2.mAnimationInfo;
                    if (animationInfo6 == null || (arrayList5 = animationInfo6.mSharedElementTargetNames) == null) {
                        arrayList5 = new ArrayList();
                    }
                    Pair pair = !z ? new Pair(null, null) : new Pair(null, null);
                    SharedElementCallback sharedElementCallback = (SharedElementCallback) pair.component1();
                    SharedElementCallback sharedElementCallback2 = (SharedElementCallback) pair.component2();
                    int i11 = 0;
                    for (int size8 = arrayList2.size(); i11 < size8; size8 = size8) {
                        arrayMap.put((String) arrayList2.get(i11), (String) arrayList5.get(i11));
                        i11++;
                    }
                    if (FragmentManager.isLoggingEnabled(2)) {
                        int size9 = arrayList5.size();
                        int i12 = 0;
                        while (i12 < size9) {
                            Object obj11 = arrayList5.get(i12);
                            i12++;
                        }
                        int size10 = arrayList2.size();
                        int i13 = 0;
                        while (i13 < size10) {
                            Object obj12 = arrayList2.get(i13);
                            i13++;
                        }
                    }
                    findNamedViews(arrayMap2, fragment3.mView);
                    arrayMap2.retainAll(arrayList2);
                    if (sharedElementCallback != null) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            operation2.toString();
                        }
                        int size11 = arrayList2.size() - 1;
                        if (size11 >= 0) {
                            while (true) {
                                int i14 = size11 - 1;
                                String str = (String) arrayList2.get(size11);
                                View view3 = (View) arrayMap2.get(str);
                                if (view3 == null) {
                                    arrayMap.remove(str);
                                } else {
                                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                    if (!Intrinsics.areEqual(str, ViewCompat.Api21Impl.getTransitionName(view3))) {
                                        arrayMap.put(ViewCompat.Api21Impl.getTransitionName(view3), (String) arrayMap.remove(str));
                                    }
                                }
                                if (i14 < 0) {
                                    break;
                                } else {
                                    size11 = i14;
                                }
                            }
                        }
                    } else {
                        arrayMap.retainAll(arrayMap2.keySet());
                    }
                    findNamedViews(arrayMap3, fragment2.mView);
                    arrayMap3.retainAll(arrayList5);
                    arrayMap3.retainAll(arrayMap.values());
                    if (sharedElementCallback2 != null) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            operation4.toString();
                        }
                        int size12 = arrayList5.size() - 1;
                        if (size12 >= 0) {
                            while (true) {
                                int i15 = size12 - 1;
                                String str2 = (String) arrayList5.get(size12);
                                View view4 = (View) arrayMap3.get(str2);
                                if (view4 == null) {
                                    String findKeyForValue2 = FragmentTransition.findKeyForValue(arrayMap, str2);
                                    if (findKeyForValue2 != null) {
                                        arrayMap.remove(findKeyForValue2);
                                    }
                                } else {
                                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                    if (!Intrinsics.areEqual(str2, ViewCompat.Api21Impl.getTransitionName(view4)) && (findKeyForValue = FragmentTransition.findKeyForValue(arrayMap, str2)) != null) {
                                        arrayMap.put(findKeyForValue, ViewCompat.Api21Impl.getTransitionName(view4));
                                    }
                                }
                                if (i15 < 0) {
                                    break;
                                } else {
                                    size12 = i15;
                                }
                            }
                        }
                    } else {
                        FragmentTransitionCompat21 fragmentTransitionCompat21 = FragmentTransition.PLATFORM_IMPL;
                        for (int i16 = arrayMap.size - 1; -1 < i16; i16--) {
                            if (!arrayMap3.containsKey((String) arrayMap.valueAt(i16))) {
                                arrayMap.removeAt(i16);
                            }
                        }
                    }
                    final Set keySet = arrayMap.keySet();
                    CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(arrayMap2.entrySet(), new Function1() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$retainMatchingViews$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj13) {
                            Collection<String> collection = keySet;
                            View view5 = (View) ((Map.Entry) obj13).getValue();
                            WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                            return Boolean.valueOf(CollectionsKt___CollectionsKt.contains(collection, ViewCompat.Api21Impl.getTransitionName(view5)));
                        }
                    }, false);
                    final Collection values = arrayMap.values();
                    CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(arrayMap3.entrySet(), new Function1() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$retainMatchingViews$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj13) {
                            Collection<String> collection = values;
                            View view5 = (View) ((Map.Entry) obj13).getValue();
                            WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                            return Boolean.valueOf(CollectionsKt___CollectionsKt.contains(collection, ViewCompat.Api21Impl.getTransitionName(view5)));
                        }
                    }, false);
                    if (arrayMap.isEmpty()) {
                        Log.i("FragmentManager", "Ignoring shared elements transition " + wrapTransitionInSet + " between " + operation2 + " and " + operation4 + " as there are no matching elements in both the entering and exiting fragment. In order to run a SharedElementTransition, both fragments involved must have the element.");
                        arrayList17.clear();
                        arrayList18.clear();
                        arrayList16 = arrayList2;
                        arrayList14 = arrayList5;
                        i7 = i8;
                        fragmentTransitionImpl = fragmentTransitionImpl2;
                        arrayList12 = arrayList18;
                        arrayList13 = arrayList19;
                        obj8 = null;
                    } else {
                        obj8 = wrapTransitionInSet;
                        arrayList16 = arrayList2;
                        arrayList14 = arrayList5;
                        i7 = i8;
                        fragmentTransitionImpl = fragmentTransitionImpl2;
                        arrayList12 = arrayList18;
                        arrayList13 = arrayList19;
                    }
                    arrayList11 = arrayList17;
                }
            }
            ArrayList arrayList21 = arrayList11;
            FragmentTransitionImpl fragmentTransitionImpl3 = fragmentTransitionImpl;
            ArrayList arrayList22 = arrayList12;
            ArrayList arrayList23 = arrayList13;
            if (obj8 == null) {
                if (!arrayList10.isEmpty()) {
                    int size13 = arrayList10.size();
                    int i17 = 0;
                    while (i17 < size13) {
                        Object obj13 = arrayList10.get(i17);
                        i17++;
                        if (((TransitionInfo) obj13).transition == null) {
                        }
                    }
                }
                arrayList = arrayList23;
            }
            arrayList = arrayList23;
            TransitionEffect transitionEffect = new TransitionEffect(arrayList10, operation2, operation4, fragmentTransitionImpl3, obj8, arrayList21, arrayList22, arrayMap, arrayList14, arrayList16, arrayMap2, arrayMap3, z);
            int size14 = arrayList10.size();
            int i18 = 0;
            while (i18 < size14) {
                Object obj14 = arrayList10.get(i18);
                i18++;
                ((ArrayList) ((TransitionInfo) obj14).operation._effects).add(transitionEffect);
            }
        }
        ArrayList arrayList24 = new ArrayList();
        ArrayList arrayList25 = new ArrayList();
        int size15 = arrayList.size();
        int i19 = 0;
        while (i19 < size15) {
            Object obj15 = arrayList.get(i19);
            i19++;
            CollectionsKt__MutableCollectionsKt.addAll(((AnimationInfo) obj15).operation.effects, arrayList25);
        }
        boolean isEmpty = arrayList25.isEmpty();
        int size16 = arrayList.size();
        boolean z2 = false;
        int i20 = 0;
        while (i20 < size16) {
            Object obj16 = arrayList.get(i20);
            i20++;
            AnimationInfo animationInfo7 = (AnimationInfo) obj16;
            Context context = this.container.getContext();
            SpecialEffectsController.Operation operation6 = animationInfo7.operation;
            FragmentAnim.AnimationOrAnimator animation = animationInfo7.getAnimation(context);
            if (animation != null) {
                if (animation.animator == null) {
                    arrayList24.add(animationInfo7);
                } else {
                    Fragment fragment4 = operation6.fragment;
                    if (((ArrayList) operation6.effects).isEmpty()) {
                        if (operation6.finalState == SpecialEffectsController.Operation.State.GONE) {
                            operation6.isAwaitingContainerChanges = false;
                        }
                        ((ArrayList) operation6._effects).add(new AnimatorEffect(animationInfo7));
                        z2 = true;
                    } else if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(fragment4);
                    }
                }
            }
        }
        int size17 = arrayList24.size();
        int i21 = 0;
        while (i21 < size17) {
            Object obj17 = arrayList24.get(i21);
            i21++;
            AnimationInfo animationInfo8 = (AnimationInfo) obj17;
            SpecialEffectsController.Operation operation7 = animationInfo8.operation;
            Fragment fragment5 = operation7.fragment;
            if (isEmpty) {
                if (!z2) {
                    ((ArrayList) operation7._effects).add(new AnimationEffect(animationInfo8));
                } else if (FragmentManager.isLoggingEnabled(2)) {
                    Objects.toString(fragment5);
                }
            } else if (FragmentManager.isLoggingEnabled(2)) {
                Objects.toString(fragment5);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TransitionInfo extends SpecialEffectsInfo {
        public final boolean isOverlapAllowed;
        public final Object sharedElementTransition;
        public final Object transition;

        public TransitionInfo(SpecialEffectsController.Operation operation, boolean z, boolean z2) {
            super(operation);
            SpecialEffectsController.Operation.State state = operation.finalState;
            SpecialEffectsController.Operation.State state2 = SpecialEffectsController.Operation.State.VISIBLE;
            Fragment fragment = operation.fragment;
            if (state == state2) {
                if (z) {
                    Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
                } else {
                    fragment.getClass();
                }
            } else if (z) {
                Fragment.AnimationInfo animationInfo2 = fragment.mAnimationInfo;
            } else {
                fragment.getClass();
            }
            this.transition = null;
            if (operation.finalState == state2) {
                if (z) {
                    Fragment.AnimationInfo animationInfo3 = fragment.mAnimationInfo;
                } else {
                    Fragment.AnimationInfo animationInfo4 = fragment.mAnimationInfo;
                }
            }
            this.isOverlapAllowed = true;
            if (z2) {
                if (z) {
                    Fragment.AnimationInfo animationInfo5 = fragment.mAnimationInfo;
                } else {
                    fragment.getClass();
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
            throw new IllegalArgumentException(("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + this.operation.fragment + " returned Transition " + obj + " which uses a different Transition  type than its shared element transition " + obj2).toString());
        }

        public final FragmentTransitionImpl getHandlingImpl(Object obj) {
            if (obj == null) {
                return null;
            }
            FragmentTransitionCompat21 fragmentTransitionCompat21 = FragmentTransition.PLATFORM_IMPL;
            if (fragmentTransitionCompat21 != null && (obj instanceof Transition)) {
                return fragmentTransitionCompat21;
            }
            FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.SUPPORT_IMPL;
            if (fragmentTransitionImpl != null && fragmentTransitionImpl.canHandle(obj)) {
                return fragmentTransitionImpl;
            }
            throw new IllegalArgumentException("Transition " + obj + " for fragment " + this.operation.fragment + " is not a valid framework Transition or AndroidX Transition");
        }
    }
}
