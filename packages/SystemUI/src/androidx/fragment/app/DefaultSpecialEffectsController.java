package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.provider.Settings;
import android.text.TextUtils;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import java.lang.ref.WeakReference;
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

/* loaded from: classes.dex */
public final class DefaultSpecialEffectsController extends SpecialEffectsController {
    public static final /* synthetic */ int $r8$clinit = 0;

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
        public final void onCommit(final ViewGroup viewGroup) throws Resources.NotFoundException {
            AnimationInfo animationInfo = this.animationInfo;
            boolean zIsVisibilityUnchanged = animationInfo.isVisibilityUnchanged();
            final SpecialEffectsController.Operation operation = animationInfo.operation;
            if (zIsVisibilityUnchanged) {
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
                        Objects.toString(operation);
                    }
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationStart(Animation animation3) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(operation);
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

    public final class AnimationInfo extends SpecialEffectsInfo {
        public FragmentAnim.AnimationOrAnimator animation;
        public boolean isAnimLoaded;
        public final boolean isPop;

        public AnimationInfo(SpecialEffectsController.Operation operation, boolean z) {
            super(operation);
            this.isPop = z;
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0026  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final FragmentAnim.AnimationOrAnimator getAnimation(Context context) throws Resources.NotFoundException {
            int activityTransitResId;
            Animation animationLoadAnimation;
            FragmentAnim.AnimationOrAnimator animationOrAnimator;
            View view;
            if (this.isAnimLoaded) {
                return this.animation;
            }
            SpecialEffectsController.Operation operation = this.operation;
            Fragment fragment = operation.fragment;
            boolean z = operation.finalState == SpecialEffectsController.Operation.State.VISIBLE;
            Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
            int i = animationInfo == null ? 0 : animationInfo.mNextTransition;
            if (this.isPop) {
                if (z) {
                    activityTransitResId = animationInfo == null ? 0 : animationInfo.mPopEnterAnim;
                } else if (animationInfo != null) {
                    activityTransitResId = animationInfo.mPopExitAnim;
                }
            } else if (z) {
                if (animationInfo != null) {
                    activityTransitResId = animationInfo.mEnterAnim;
                }
            } else if (animationInfo != null) {
                activityTransitResId = animationInfo.mExitAnim;
            }
            fragment.setAnimations(0, 0, 0, 0);
            ViewGroup viewGroup = fragment.mContainer;
            FragmentAnim.AnimationOrAnimator animationOrAnimator2 = null;
            if (viewGroup != null && viewGroup.getTag(R.id.visible_removing_fragment_view_tag) != null) {
                fragment.mContainer.setTag(R.id.visible_removing_fragment_view_tag, null);
            }
            ViewGroup viewGroup2 = fragment.mContainer;
            if (viewGroup2 == null || viewGroup2.getLayoutTransition() == null) {
                Context context2 = fragment.getContext();
                if (context2 != null && TextUtils.isEmpty(Settings.System.getString(context2.getContentResolver(), SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE)) && (view = fragment.mView) != null) {
                    FragmentActivity activity = fragment.getActivity();
                    SeslFragmentTransactionAnimationSet.Companion.getClass();
                    SeslFragmentTransactionAnimationSet[] seslFragmentTransactionAnimationSetArrValues = SeslFragmentTransactionAnimationSet.values();
                    int length = seslFragmentTransactionAnimationSetArrValues.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            SeslFragmentTransactionAnimationSet.Companion.getClass();
                            SeslFragmentTransactionAnimationSet[] seslFragmentTransactionAnimationSetArrValues2 = SeslFragmentTransactionAnimationSet.values();
                            int length2 = seslFragmentTransactionAnimationSetArrValues2.length;
                            int i3 = 0;
                            while (true) {
                                if (i3 >= length2) {
                                    break;
                                }
                                if (seslFragmentTransactionAnimationSetArrValues2[i3].getEnter$fragment_release() == activityTransitResId) {
                                    view.setTranslationZ(1.0f);
                                    break;
                                }
                                i3++;
                            }
                        } else {
                            if (seslFragmentTransactionAnimationSetArrValues[i2].getExit$fragment_release() == activityTransitResId) {
                                view.setTranslationZ(0.0f);
                                break;
                            }
                            i2++;
                        }
                    }
                    SeslFragmentTransactionAnimationSet.Companion.getClass();
                    for (SeslFragmentTransactionAnimationSet seslFragmentTransactionAnimationSet : SeslFragmentTransactionAnimationSet.values()) {
                        if (seslFragmentTransactionAnimationSet.getEnter$fragment_release() == activityTransitResId || seslFragmentTransactionAnimationSet.getExit$fragment_release() == activityTransitResId || seslFragmentTransactionAnimationSet.getPopEnter$fragment_release() == activityTransitResId || seslFragmentTransactionAnimationSet.getPopExit$fragment_release() == activityTransitResId) {
                            if (activity != null) {
                                activity.getWindow().getDecorView().setBackgroundColor(fragment.getResources().getColor(R.color.sesl_fragment_fgcolor));
                            }
                            view.setBackgroundColor(fragment.getResources().getColor(R.color.sesl_fragment_bgcolor));
                            fragment.mDisposableHandle = new Fragment$$ExternalSyntheticLambda1(fragment, new WeakReference(view));
                        }
                    }
                    fragment.mDisposableHandle = new Fragment$$ExternalSyntheticLambda1(fragment, new WeakReference(view));
                }
                if (activityTransitResId == 0 && i != 0) {
                    activityTransitResId = i != 4097 ? i != 8194 ? i != 8197 ? i != 4099 ? i != 4100 ? -1 : z ? FragmentAnim.toActivityTransitResId(android.R.attr.activityOpenEnterAnimation, context) : FragmentAnim.toActivityTransitResId(android.R.attr.activityOpenExitAnimation, context) : z ? R.animator.fragment_fade_enter : R.animator.fragment_fade_exit : z ? FragmentAnim.toActivityTransitResId(android.R.attr.activityCloseEnterAnimation, context) : FragmentAnim.toActivityTransitResId(android.R.attr.activityCloseExitAnimation, context) : z ? R.animator.fragment_close_enter : R.animator.fragment_close_exit : z ? R.animator.fragment_open_enter : R.animator.fragment_open_exit;
                }
                if (activityTransitResId != 0) {
                    boolean zEquals = "anim".equals(context.getResources().getResourceTypeName(activityTransitResId));
                    if (zEquals) {
                        try {
                            animationLoadAnimation = AnimationUtils.loadAnimation(context, activityTransitResId);
                        } catch (Resources.NotFoundException e) {
                            throw e;
                        } catch (RuntimeException unused) {
                        }
                        if (animationLoadAnimation != null) {
                            animationOrAnimator = new FragmentAnim.AnimationOrAnimator(animationLoadAnimation);
                            animationOrAnimator2 = animationOrAnimator;
                        }
                    } else {
                        try {
                            Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(context, activityTransitResId);
                            if (animatorLoadAnimator != null) {
                                animationOrAnimator = new FragmentAnim.AnimationOrAnimator(animatorLoadAnimator);
                                animationOrAnimator2 = animationOrAnimator;
                            }
                        } catch (RuntimeException e2) {
                            if (zEquals) {
                                throw e2;
                            }
                            Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(context, activityTransitResId);
                            if (animationLoadAnimation2 != null) {
                                animationOrAnimator2 = new FragmentAnim.AnimationOrAnimator(animationLoadAnimation2);
                            }
                        }
                    }
                }
            }
            this.animation = animationOrAnimator2;
            this.isAnimLoaded = true;
            return animationOrAnimator2;
        }
    }

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
        public final void onStart(final ViewGroup viewGroup) throws Resources.NotFoundException {
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

    public final class Api24Impl {
        public static final Api24Impl INSTANCE = new Api24Impl();

        private Api24Impl() {
        }
    }

    public final class Api26Impl {
        public static final Api26Impl INSTANCE = new Api26Impl();

        private Api26Impl() {
        }
    }

    public class SpecialEffectsInfo {
        public final SpecialEffectsController.Operation operation;

        public SpecialEffectsInfo(SpecialEffectsController.Operation operation) {
            this.operation = operation;
        }

        public final boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State stateAsOperationState;
            SpecialEffectsController.Operation operation = this.operation;
            View view = operation.fragment.mView;
            if (view != null) {
                SpecialEffectsController.Operation.State.Companion.getClass();
                stateAsOperationState = SpecialEffectsController.Operation.State.Companion.asOperationState(view);
            } else {
                stateAsOperationState = null;
            }
            SpecialEffectsController.Operation.State state = operation.finalState;
            if (stateAsOperationState == state) {
                return true;
            }
            SpecialEffectsController.Operation.State state2 = SpecialEffectsController.Operation.State.VISIBLE;
            return (stateAsOperationState == state2 || state == state2) ? false : true;
        }
    }

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
                boolean zHasNext = it.hasNext();
                obj = transitionEffect.sharedElementTransition;
                fragmentTransitionImpl = transitionEffect.transitionImpl;
                if (!zHasNext) {
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
            Object objMergeTransitionsTogether = null;
            Object objMergeTransitionsTogether2 = null;
            while (it2.hasNext()) {
                int i3 = i;
                TransitionInfo transitionInfo = (TransitionInfo) it2.next();
                SpecialEffectsController.Operation operation4 = transitionInfo.operation;
                Iterator it3 = it2;
                Object objCloneTransition = fragmentTransitionImpl.cloneTransition(transitionInfo.transition);
                if (objCloneTransition != null) {
                    boolean z2 = z;
                    ArrayList arrayList5 = new ArrayList();
                    Object obj3 = obj;
                    captureTransitioningViews(arrayList5, operation4.fragment.mView);
                    if (obj3 != null && (operation4 == operation2 || operation4 == operation3)) {
                        if (operation4 == operation2) {
                            arrayList5.removeAll(CollectionsKt___CollectionsKt.toSet(transitionEffect.sharedElementFirstOutViews));
                        } else {
                            arrayList5.removeAll(CollectionsKt___CollectionsKt.toSet(transitionEffect.sharedElementLastInViews));
                        }
                    }
                    if (arrayList5.isEmpty()) {
                        fragmentTransitionImpl.addTarget(view2, objCloneTransition);
                    } else {
                        fragmentTransitionImpl.addTargets(objCloneTransition, arrayList5);
                        transitionEffect.transitionImpl.scheduleRemoveTargets(objCloneTransition, objCloneTransition, arrayList5, null, null);
                        if (operation4.finalState == SpecialEffectsController.Operation.State.GONE) {
                            operation4.isAwaitingContainerChanges = false;
                            ArrayList arrayList6 = new ArrayList(arrayList5);
                            Fragment fragment = operation4.fragment;
                            arrayList6.remove(fragment.mView);
                            fragmentTransitionImpl.scheduleHideFragmentView(objCloneTransition, fragment.mView, arrayList6);
                            OneShotPreDrawListener.add(viewGroup, new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0(arrayList5));
                        }
                    }
                    if (operation4.finalState == SpecialEffectsController.Operation.State.VISIBLE) {
                        arrayList4.addAll(arrayList5);
                        if (z2) {
                            fragmentTransitionImpl.setEpicenter(objCloneTransition, rect);
                        }
                        if (FragmentManager.isLoggingEnabled(i3)) {
                            objCloneTransition.toString();
                            int size = arrayList5.size();
                            int i4 = 0;
                            while (i4 < size) {
                                Object obj4 = arrayList5.get(i4);
                                i4++;
                                Objects.toString((View) obj4);
                            }
                        }
                    } else {
                        fragmentTransitionImpl.setEpicenter(view3, objCloneTransition);
                        if (FragmentManager.isLoggingEnabled(i3)) {
                            objCloneTransition.toString();
                            int size2 = arrayList5.size();
                            int i5 = 0;
                            while (i5 < size2) {
                                Object obj5 = arrayList5.get(i5);
                                i5++;
                                Objects.toString((View) obj5);
                            }
                        }
                    }
                    if (transitionInfo.isOverlapAllowed) {
                        objMergeTransitionsTogether = fragmentTransitionImpl.mergeTransitionsTogether(objMergeTransitionsTogether, objCloneTransition);
                    } else {
                        objMergeTransitionsTogether2 = fragmentTransitionImpl.mergeTransitionsTogether(objMergeTransitionsTogether2, objCloneTransition);
                    }
                    transitionEffect = this;
                    operation3 = operation;
                    i = i3;
                    it2 = it3;
                    z = z2;
                    obj = obj3;
                } else {
                    transitionEffect = this;
                    operation3 = operation;
                    i = i3;
                    it2 = it3;
                }
            }
            Object objMergeTransitionsInSequence = fragmentTransitionImpl.mergeTransitionsInSequence(objMergeTransitionsTogether, objMergeTransitionsTogether2, obj);
            if (FragmentManager.isLoggingEnabled(i)) {
                Objects.toString(objMergeTransitionsInSequence);
            }
            return new Pair(arrayList4, objMergeTransitionsInSequence);
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
            Pair pairCreateMergedTransition = createMergedTransition(viewGroup, operation2, operation3);
            ArrayList arrayList = (ArrayList) pairCreateMergedTransition.component1();
            final Object objComponent2 = pairCreateMergedTransition.component2();
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
                fragmentTransitionImpl.setListenerForTransitionEnd(operation4.fragment, objComponent2, this.transitionSignal, new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda1(operation4, this, 1));
            }
            runTransition(arrayList, viewGroup, new Function0() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onCommit$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    this.this$0.transitionImpl.beginDelayedTransition(viewGroup, objComponent2);
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
                Pair pairCreateMergedTransition = createMergedTransition(viewGroup, operation2, operation3);
                ArrayList arrayList = (ArrayList) pairCreateMergedTransition.component1();
                final Object objComponent2 = pairCreateMergedTransition.component2();
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
                    this.transitionImpl.setListenerForTransitionEnd(objComponent2, this.transitionSignal, defaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0, new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda1(operation4, this, 0));
                }
                runTransition(arrayList, viewGroup, new Function0() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* JADX WARN: Type inference failed for: r4v0, types: [T, androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4$2] */
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        DefaultSpecialEffectsController.TransitionEffect transitionEffect = this.this$0;
                        transitionEffect.controller = transitionEffect.transitionImpl.controlDelayedTransition(viewGroup, objComponent2);
                        final DefaultSpecialEffectsController.TransitionEffect transitionEffect2 = this.this$0;
                        boolean z = transitionEffect2.controller != null;
                        final Object obj3 = objComponent2;
                        final ViewGroup viewGroup2 = viewGroup;
                        if (z) {
                            ref$ObjectRef.element = new Function0() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4.2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    List list2 = transitionEffect2.transitionInfos;
                                    if ((list2 instanceof Collection) && list2.isEmpty()) {
                                        DefaultSpecialEffectsController.TransitionEffect transitionEffect3 = transitionEffect2;
                                        FragmentTransitionImpl fragmentTransitionImpl = transitionEffect3.transitionImpl;
                                        Object obj4 = transitionEffect3.controller;
                                        obj4.getClass();
                                        fragmentTransitionImpl.animateToStart(obj4, new DefaultSpecialEffectsController$$ExternalSyntheticLambda0(1, transitionEffect2, viewGroup2));
                                    } else {
                                        Iterator it3 = list2.iterator();
                                        while (it3.hasNext()) {
                                            if (!((DefaultSpecialEffectsController.TransitionInfo) it3.next()).operation.isSeeking) {
                                                CancellationSignal cancellationSignal = new CancellationSignal();
                                                DefaultSpecialEffectsController.TransitionEffect transitionEffect4 = transitionEffect2;
                                                transitionEffect4.transitionImpl.setListenerForTransitionEnd(((DefaultSpecialEffectsController.TransitionInfo) transitionEffect4.transitionInfos.get(0)).operation.fragment, obj3, cancellationSignal, new DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0(transitionEffect2, 2));
                                                cancellationSignal.cancel();
                                                break;
                                            }
                                        }
                                        DefaultSpecialEffectsController.TransitionEffect transitionEffect32 = transitionEffect2;
                                        FragmentTransitionImpl fragmentTransitionImpl2 = transitionEffect32.transitionImpl;
                                        Object obj42 = transitionEffect32.controller;
                                        obj42.getClass();
                                        fragmentTransitionImpl2.animateToStart(obj42, new DefaultSpecialEffectsController$$ExternalSyntheticLambda0(1, transitionEffect2, viewGroup2));
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Objects.toString(this.this$0.firstOut);
                                Objects.toString(this.this$0.lastIn);
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

    /* JADX WARN: Removed duplicated region for block: B:170:0x0431 A[LOOP:19: B:169:0x042f->B:170:0x0431, LOOP_END] */
    @Override // androidx.fragment.app.SpecialEffectsController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void collectEffects(List list, boolean z) throws Resources.NotFoundException {
        Object next;
        Object objPrevious;
        ArrayList arrayList;
        int size;
        int i;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ArrayList arrayList4;
        ArrayList arrayList5;
        String strFindKeyForValue;
        int i2 = 0;
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            SpecialEffectsController.Operation operation = (SpecialEffectsController.Operation) next;
            SpecialEffectsController.Operation.State.Companion companion = SpecialEffectsController.Operation.State.Companion;
            View view = operation.fragment.mView;
            companion.getClass();
            SpecialEffectsController.Operation.State stateAsOperationState = SpecialEffectsController.Operation.State.Companion.asOperationState(view);
            SpecialEffectsController.Operation.State state = SpecialEffectsController.Operation.State.VISIBLE;
            if (stateAsOperationState == state && operation.finalState != state) {
                break;
            }
        }
        SpecialEffectsController.Operation operation2 = (SpecialEffectsController.Operation) next;
        ArrayList arrayList6 = (ArrayList) list;
        ListIterator listIterator = arrayList6.listIterator(arrayList6.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
            SpecialEffectsController.Operation operation3 = (SpecialEffectsController.Operation) objPrevious;
            SpecialEffectsController.Operation.State.Companion companion2 = SpecialEffectsController.Operation.State.Companion;
            View view2 = operation3.fragment.mView;
            companion2.getClass();
            SpecialEffectsController.Operation.State stateAsOperationState2 = SpecialEffectsController.Operation.State.Companion.asOperationState(view2);
            SpecialEffectsController.Operation.State state2 = SpecialEffectsController.Operation.State.VISIBLE;
            if (stateAsOperationState2 != state2 && operation3.finalState == state2) {
                break;
            }
        }
        SpecialEffectsController.Operation operation4 = (SpecialEffectsController.Operation) objPrevious;
        if (FragmentManager.isLoggingEnabled(2)) {
            Objects.toString(operation2);
            Objects.toString(operation4);
        }
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = new ArrayList();
        Fragment fragment = ((SpecialEffectsController.Operation) CollectionsKt___CollectionsKt.last(list)).fragment;
        int size2 = arrayList6.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj = arrayList6.get(i3);
            i3++;
            Fragment.AnimationInfo animationInfo = ((SpecialEffectsController.Operation) obj).fragment.mAnimationInfo;
            Fragment.AnimationInfo animationInfo2 = fragment.mAnimationInfo;
            animationInfo.mEnterAnim = animationInfo2.mEnterAnim;
            animationInfo.mExitAnim = animationInfo2.mExitAnim;
            animationInfo.mPopEnterAnim = animationInfo2.mPopEnterAnim;
            animationInfo.mPopExitAnim = animationInfo2.mPopExitAnim;
        }
        int size3 = arrayList6.size();
        int i4 = 0;
        while (i4 < size3) {
            Object obj2 = arrayList6.get(i4);
            i4++;
            SpecialEffectsController.Operation operation5 = (SpecialEffectsController.Operation) obj2;
            arrayList7.add(new AnimationInfo(operation5, z));
            arrayList8.add(new TransitionInfo(operation5, z, !z ? operation5 != operation4 : operation5 != operation2));
            ((ArrayList) operation5.completionListeners).add(new DefaultSpecialEffectsController$$ExternalSyntheticLambda0(i2, this, operation5));
        }
        ArrayList arrayList9 = new ArrayList();
        int size4 = arrayList8.size();
        int i5 = 0;
        while (i5 < size4) {
            Object obj3 = arrayList8.get(i5);
            i5++;
            if (!((TransitionInfo) obj3).isVisibilityUnchanged()) {
                arrayList9.add(obj3);
            }
        }
        ArrayList arrayList10 = new ArrayList();
        int size5 = arrayList9.size();
        int i6 = 0;
        while (i6 < size5) {
            Object obj4 = arrayList9.get(i6);
            i6++;
            if (((TransitionInfo) obj4).getHandlingImpl() != null) {
                arrayList10.add(obj4);
            }
        }
        int size6 = arrayList10.size();
        int i7 = 0;
        FragmentTransitionImpl fragmentTransitionImpl = null;
        while (i7 < size6) {
            Object obj5 = arrayList10.get(i7);
            i7++;
            TransitionInfo transitionInfo = (TransitionInfo) obj5;
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
            int size7 = arrayList10.size();
            int i8 = 0;
            Object obj6 = null;
            while (i8 < size7) {
                Object obj7 = arrayList10.get(i8);
                int i9 = i8 + 1;
                Object obj8 = ((TransitionInfo) obj7).sharedElementTransition;
                if (obj8 == null || operation2 == null || operation4 == null) {
                    arrayList11 = arrayList11;
                    i8 = i9;
                    fragmentTransitionImpl = fragmentTransitionImpl;
                    arrayList12 = arrayList12;
                    arrayList13 = arrayList13;
                } else {
                    Object objWrapTransitionInSet = fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(obj8));
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
                    int size8 = arrayList4.size();
                    int i10 = 0;
                    while (i10 < size8) {
                        int i11 = size8;
                        int iIndexOf = arrayList2.indexOf(arrayList4.get(i10));
                        ArrayList arrayList20 = arrayList4;
                        if (iIndexOf != -1) {
                            arrayList2.set(iIndexOf, arrayList3.get(i10));
                        }
                        i10++;
                        size8 = i11;
                        arrayList4 = arrayList20;
                    }
                    Fragment.AnimationInfo animationInfo6 = fragment2.mAnimationInfo;
                    if (animationInfo6 == null || (arrayList5 = animationInfo6.mSharedElementTargetNames) == null) {
                        arrayList5 = new ArrayList();
                    }
                    Pair pair = !z ? new Pair(null, null) : new Pair(null, null);
                    SharedElementCallback sharedElementCallback = (SharedElementCallback) pair.component1();
                    SharedElementCallback sharedElementCallback2 = (SharedElementCallback) pair.component2();
                    int i12 = 0;
                    for (int size9 = arrayList2.size(); i12 < size9; size9 = size9) {
                        arrayMap.put((String) arrayList2.get(i12), (String) arrayList5.get(i12));
                        i12++;
                    }
                    if (FragmentManager.isLoggingEnabled(2)) {
                        int size10 = arrayList5.size();
                        int i13 = 0;
                        while (i13 < size10) {
                            Object obj9 = arrayList5.get(i13);
                            i13++;
                        }
                        int size11 = arrayList2.size();
                        int i14 = 0;
                        while (i14 < size11) {
                            Object obj10 = arrayList2.get(i14);
                            i14++;
                        }
                    }
                    findNamedViews(arrayMap2, fragment3.mView);
                    arrayMap2.retainAll(arrayList2);
                    if (sharedElementCallback != null) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            operation2.toString();
                        }
                        int size12 = arrayList2.size() - 1;
                        if (size12 >= 0) {
                            while (true) {
                                int i15 = size12 - 1;
                                String str = (String) arrayList2.get(size12);
                                View view3 = (View) arrayMap2.get(str);
                                if (view3 == null) {
                                    arrayMap.remove(str);
                                } else {
                                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                    if (!Intrinsics.areEqual(str, ViewCompat.Api21Impl.getTransitionName(view3))) {
                                        arrayMap.put(ViewCompat.Api21Impl.getTransitionName(view3), (String) arrayMap.remove(str));
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
                        arrayMap.retainAll(arrayMap2.keySet());
                    }
                    findNamedViews(arrayMap3, fragment2.mView);
                    arrayMap3.retainAll(arrayList5);
                    arrayMap3.retainAll(arrayMap.values());
                    if (sharedElementCallback2 != null) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            operation4.toString();
                        }
                        int size13 = arrayList5.size() - 1;
                        if (size13 >= 0) {
                            while (true) {
                                int i16 = size13 - 1;
                                String str2 = (String) arrayList5.get(size13);
                                View view4 = (View) arrayMap3.get(str2);
                                if (view4 == null) {
                                    String strFindKeyForValue2 = FragmentTransition.findKeyForValue(arrayMap, str2);
                                    if (strFindKeyForValue2 != null) {
                                        arrayMap.remove(strFindKeyForValue2);
                                    }
                                } else {
                                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                    if (!Intrinsics.areEqual(str2, ViewCompat.Api21Impl.getTransitionName(view4)) && (strFindKeyForValue = FragmentTransition.findKeyForValue(arrayMap, str2)) != null) {
                                        arrayMap.put(strFindKeyForValue, ViewCompat.Api21Impl.getTransitionName(view4));
                                    }
                                }
                                if (i16 < 0) {
                                    break;
                                } else {
                                    size13 = i16;
                                }
                            }
                        }
                    } else {
                        FragmentTransitionCompat21 fragmentTransitionCompat21 = FragmentTransition.PLATFORM_IMPL;
                        for (int i17 = arrayMap.size - 1; -1 < i17; i17--) {
                            if (!arrayMap3.containsKey((String) arrayMap.valueAt(i17))) {
                                arrayMap.removeAt(i17);
                            }
                        }
                    }
                    final Set setKeySet = arrayMap.keySet();
                    CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(arrayMap2.entrySet(), new Function1() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$retainMatchingViews$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj11) {
                            Collection<String> collection = setKeySet;
                            View view5 = (View) ((Map.Entry) obj11).getValue();
                            WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                            return Boolean.valueOf(CollectionsKt___CollectionsKt.contains(collection, ViewCompat.Api21Impl.getTransitionName(view5)));
                        }
                    }, false);
                    final Collection collectionValues = arrayMap.values();
                    CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(arrayMap3.entrySet(), new Function1() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$retainMatchingViews$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj11) {
                            Collection<String> collection = collectionValues;
                            View view5 = (View) ((Map.Entry) obj11).getValue();
                            WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                            return Boolean.valueOf(CollectionsKt___CollectionsKt.contains(collection, ViewCompat.Api21Impl.getTransitionName(view5)));
                        }
                    }, false);
                    if (arrayMap.isEmpty()) {
                        Log.i("FragmentManager", "Ignoring shared elements transition " + objWrapTransitionInSet + " between " + operation2 + " and " + operation4 + " as there are no matching elements in both the entering and exiting fragment. In order to run a SharedElementTransition, both fragments involved must have the element.");
                        arrayList17.clear();
                        arrayList18.clear();
                        arrayList16 = arrayList2;
                        arrayList14 = arrayList5;
                        i8 = i9;
                        fragmentTransitionImpl = fragmentTransitionImpl2;
                        arrayList12 = arrayList18;
                        arrayList13 = arrayList19;
                        obj6 = null;
                    } else {
                        obj6 = objWrapTransitionInSet;
                        arrayList16 = arrayList2;
                        arrayList14 = arrayList5;
                        i8 = i9;
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
            if (obj6 != null) {
                arrayList = arrayList23;
                TransitionEffect transitionEffect = new TransitionEffect(arrayList10, operation2, operation4, fragmentTransitionImpl3, obj6, arrayList21, arrayList22, arrayMap, arrayList14, arrayList16, arrayMap2, arrayMap3, z);
                size = arrayList10.size();
                i = 0;
                while (i < size) {
                    Object obj11 = arrayList10.get(i);
                    i++;
                    ((ArrayList) ((TransitionInfo) obj11).operation._effects).add(transitionEffect);
                }
            } else {
                if (!arrayList10.isEmpty()) {
                    int size14 = arrayList10.size();
                    int i18 = 0;
                    while (i18 < size14) {
                        Object obj12 = arrayList10.get(i18);
                        i18++;
                        if (((TransitionInfo) obj12).transition != null) {
                            arrayList = arrayList23;
                            TransitionEffect transitionEffect2 = new TransitionEffect(arrayList10, operation2, operation4, fragmentTransitionImpl3, obj6, arrayList21, arrayList22, arrayMap, arrayList14, arrayList16, arrayMap2, arrayMap3, z);
                            size = arrayList10.size();
                            i = 0;
                            while (i < size) {
                            }
                        }
                    }
                }
                arrayList = arrayList23;
            }
        }
        ArrayList arrayList24 = new ArrayList();
        ArrayList arrayList25 = new ArrayList();
        int size15 = arrayList.size();
        int i19 = 0;
        while (i19 < size15) {
            Object obj13 = arrayList.get(i19);
            i19++;
            CollectionsKt__MutableCollectionsKt.addAll(((AnimationInfo) obj13).operation.effects, arrayList25);
        }
        boolean zIsEmpty = arrayList25.isEmpty();
        int size16 = arrayList.size();
        boolean z2 = false;
        int i20 = 0;
        while (i20 < size16) {
            Object obj14 = arrayList.get(i20);
            i20++;
            AnimationInfo animationInfo7 = (AnimationInfo) obj14;
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
            Object obj15 = arrayList24.get(i21);
            i21++;
            AnimationInfo animationInfo8 = (AnimationInfo) obj15;
            SpecialEffectsController.Operation operation7 = animationInfo8.operation;
            Fragment fragment5 = operation7.fragment;
            if (zIsEmpty) {
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
