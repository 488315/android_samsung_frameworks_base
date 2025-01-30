package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.transition.Transition;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.collection.ArrayMap;
import androidx.collection.IndexBasedArrayIterator;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.SpecialEffectsController;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DefaultSpecialEffectsController extends SpecialEffectsController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.fragment.app.DefaultSpecialEffectsController$10 */
    public abstract /* synthetic */ class AbstractC021010 {

        /* renamed from: $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State */
        public static final /* synthetic */ int[] f171xe493b431;

        static {
            int[] iArr = new int[SpecialEffectsController.Operation.State.values().length];
            f171xe493b431 = iArr;
            try {
                iArr[SpecialEffectsController.Operation.State.GONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f171xe493b431[SpecialEffectsController.Operation.State.INVISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f171xe493b431[SpecialEffectsController.Operation.State.REMOVED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f171xe493b431[SpecialEffectsController.Operation.State.VISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.fragment.app.DefaultSpecialEffectsController$9 */
    public final class RunnableC02189 implements Runnable {
        public final /* synthetic */ SpecialEffectsController.Operation val$operation;
        public final /* synthetic */ TransitionInfo val$transitionInfo;

        public RunnableC02189(DefaultSpecialEffectsController defaultSpecialEffectsController, TransitionInfo transitionInfo, SpecialEffectsController.Operation operation) {
            this.val$transitionInfo = transitionInfo;
            this.val$operation = operation;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.val$transitionInfo.completeSpecialEffect();
            if (FragmentManager.isLoggingEnabled(2)) {
                Objects.toString(this.val$operation);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimationInfo extends SpecialEffectsInfo {
        public FragmentAnim.AnimationOrAnimator mAnimation;
        public final boolean mIsPop;
        public boolean mLoadedAnim;

        public AnimationInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal, boolean z) {
            super(operation, cancellationSignal);
            this.mLoadedAnim = false;
            this.mIsPop = z;
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0046  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0058  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0066  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x010b A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0111  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x015b  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x0167  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final FragmentAnim.AnimationOrAnimator getAnimation(Context context) {
            int i;
            ViewGroup viewGroup;
            ViewGroup viewGroup2;
            View view;
            if (this.mLoadedAnim) {
                return this.mAnimation;
            }
            SpecialEffectsController.Operation operation = this.mOperation;
            Fragment fragment = operation.mFragment;
            boolean z = false;
            boolean z2 = operation.mFinalState == SpecialEffectsController.Operation.State.VISIBLE;
            Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
            int i2 = animationInfo == null ? 0 : animationInfo.mNextTransition;
            if (!this.mIsPop) {
                if (z2) {
                    if (animationInfo != null) {
                        i = animationInfo.mEnterAnim;
                        fragment.setAnimations(0, 0, 0, 0);
                        viewGroup = fragment.mContainer;
                        FragmentAnim.AnimationOrAnimator animationOrAnimator = null;
                        if (viewGroup != null) {
                        }
                        viewGroup2 = fragment.mContainer;
                        if (viewGroup2 != null) {
                        }
                        view = fragment.mView;
                        if (view != null) {
                        }
                        if (i == 0) {
                        }
                        if (i != 0) {
                        }
                        this.mAnimation = animationOrAnimator;
                        this.mLoadedAnim = true;
                        return animationOrAnimator;
                    }
                    i = 0;
                    fragment.setAnimations(0, 0, 0, 0);
                    viewGroup = fragment.mContainer;
                    FragmentAnim.AnimationOrAnimator animationOrAnimator2 = null;
                    if (viewGroup != null) {
                    }
                    viewGroup2 = fragment.mContainer;
                    if (viewGroup2 != null) {
                    }
                    view = fragment.mView;
                    if (view != null) {
                    }
                    if (i == 0) {
                    }
                    if (i != 0) {
                    }
                    this.mAnimation = animationOrAnimator2;
                    this.mLoadedAnim = true;
                    return animationOrAnimator2;
                }
                if (animationInfo != null) {
                    i = animationInfo.mExitAnim;
                    fragment.setAnimations(0, 0, 0, 0);
                    viewGroup = fragment.mContainer;
                    FragmentAnim.AnimationOrAnimator animationOrAnimator22 = null;
                    if (viewGroup != null) {
                    }
                    viewGroup2 = fragment.mContainer;
                    if (viewGroup2 != null) {
                    }
                    view = fragment.mView;
                    if (view != null) {
                    }
                    if (i == 0) {
                    }
                    if (i != 0) {
                    }
                    this.mAnimation = animationOrAnimator22;
                    this.mLoadedAnim = true;
                    return animationOrAnimator22;
                }
                i = 0;
                fragment.setAnimations(0, 0, 0, 0);
                viewGroup = fragment.mContainer;
                FragmentAnim.AnimationOrAnimator animationOrAnimator222 = null;
                if (viewGroup != null) {
                }
                viewGroup2 = fragment.mContainer;
                if (viewGroup2 != null) {
                }
                view = fragment.mView;
                if (view != null) {
                }
                if (i == 0) {
                }
                if (i != 0) {
                }
                this.mAnimation = animationOrAnimator222;
                this.mLoadedAnim = true;
                return animationOrAnimator222;
            }
            if (!z2) {
                if (animationInfo != null) {
                    i = animationInfo.mPopExitAnim;
                    fragment.setAnimations(0, 0, 0, 0);
                    viewGroup = fragment.mContainer;
                    FragmentAnim.AnimationOrAnimator animationOrAnimator2222 = null;
                    if (viewGroup != null) {
                    }
                    viewGroup2 = fragment.mContainer;
                    if (viewGroup2 != null) {
                    }
                    view = fragment.mView;
                    if (view != null) {
                    }
                    if (i == 0) {
                    }
                    if (i != 0) {
                    }
                    this.mAnimation = animationOrAnimator2222;
                    this.mLoadedAnim = true;
                    return animationOrAnimator2222;
                }
                i = 0;
                fragment.setAnimations(0, 0, 0, 0);
                viewGroup = fragment.mContainer;
                FragmentAnim.AnimationOrAnimator animationOrAnimator22222 = null;
                if (viewGroup != null) {
                }
                viewGroup2 = fragment.mContainer;
                if (viewGroup2 != null) {
                }
                view = fragment.mView;
                if (view != null) {
                }
                if (i == 0) {
                }
                if (i != 0) {
                }
                this.mAnimation = animationOrAnimator22222;
                this.mLoadedAnim = true;
                return animationOrAnimator22222;
            }
            if (animationInfo != null) {
                i = animationInfo.mPopEnterAnim;
                fragment.setAnimations(0, 0, 0, 0);
                viewGroup = fragment.mContainer;
                FragmentAnim.AnimationOrAnimator animationOrAnimator222222 = null;
                if (viewGroup != null && viewGroup.getTag(R.id.visible_removing_fragment_view_tag) != null) {
                    fragment.mContainer.setTag(R.id.visible_removing_fragment_view_tag, null);
                }
                viewGroup2 = fragment.mContainer;
                if (viewGroup2 != null || viewGroup2.getLayoutTransition() == null) {
                    view = fragment.mView;
                    if (view != null) {
                        Resources.Theme theme = view.getContext().getTheme();
                        TypedValue typedValue = new TypedValue();
                        theme.resolveAttribute(android.R.attr.colorBackground, typedValue, true);
                        int i3 = typedValue.data;
                        if (i == R.anim.sesl_fragment_open_exit) {
                            view.setTranslationZ(0.0f);
                            view.setForeground(new ColorDrawable(fragment.getResources().getColor(R.color.sesl_fragment_fgcolor)));
                        } else if (i == R.anim.sesl_fragment_open_enter) {
                            view.setTranslationZ(1.0f);
                            view.setBackgroundColor(fragment.getResources().getColor(android.R.color.transparent));
                            view.setBackgroundTintMode(PorterDuff.Mode.SRC);
                            view.setBackgroundTintList(ColorStateList.valueOf(i3));
                        } else if (i == R.anim.sesl_fragment_close_enter) {
                            view.setForeground(new ColorDrawable(fragment.getResources().getColor(android.R.color.transparent)));
                            view.setBackgroundColor(fragment.getResources().getColor(android.R.color.transparent));
                            view.setBackgroundTintMode(PorterDuff.Mode.SRC);
                            view.setBackgroundTintList(ColorStateList.valueOf(fragment.getResources().getColor(R.color.sesl_fragment_bgcolor)));
                            if (fragment.getActivity() != null) {
                                fragment.getActivity().getWindow().getDecorView().setBackgroundColor(i3);
                            }
                        }
                    }
                    if (i == 0 && i2 != 0) {
                        i = i2 == 4097 ? i2 != 8194 ? i2 != 8197 ? i2 != 4099 ? i2 != 4100 ? -1 : z2 ? FragmentAnim.toActivityTransitResId(android.R.attr.activityOpenEnterAnimation, context) : FragmentAnim.toActivityTransitResId(android.R.attr.activityOpenExitAnimation, context) : z2 ? R.animator.fragment_fade_enter : R.animator.fragment_fade_exit : z2 ? FragmentAnim.toActivityTransitResId(android.R.attr.activityCloseEnterAnimation, context) : FragmentAnim.toActivityTransitResId(android.R.attr.activityCloseExitAnimation, context) : z2 ? R.animator.fragment_close_enter : R.animator.fragment_close_exit : z2 ? R.animator.fragment_open_enter : R.animator.fragment_open_exit;
                    }
                    if (i != 0) {
                        boolean equals = "anim".equals(context.getResources().getResourceTypeName(i));
                        if (equals) {
                            try {
                                Animation loadAnimation = AnimationUtils.loadAnimation(context, i);
                                if (loadAnimation != null) {
                                    animationOrAnimator222222 = new FragmentAnim.AnimationOrAnimator(loadAnimation);
                                } else {
                                    z = true;
                                }
                            } catch (Resources.NotFoundException e) {
                                throw e;
                            } catch (RuntimeException unused) {
                            }
                        }
                        if (!z) {
                            try {
                                Animator loadAnimator = AnimatorInflater.loadAnimator(context, i);
                                if (loadAnimator != null) {
                                    animationOrAnimator222222 = new FragmentAnim.AnimationOrAnimator(loadAnimator);
                                }
                            } catch (RuntimeException e2) {
                                if (equals) {
                                    throw e2;
                                }
                                Animation loadAnimation2 = AnimationUtils.loadAnimation(context, i);
                                if (loadAnimation2 != null) {
                                    animationOrAnimator222222 = new FragmentAnim.AnimationOrAnimator(loadAnimation2);
                                }
                            }
                        }
                    }
                }
                this.mAnimation = animationOrAnimator222222;
                this.mLoadedAnim = true;
                return animationOrAnimator222222;
            }
            i = 0;
            fragment.setAnimations(0, 0, 0, 0);
            viewGroup = fragment.mContainer;
            FragmentAnim.AnimationOrAnimator animationOrAnimator2222222 = null;
            if (viewGroup != null) {
                fragment.mContainer.setTag(R.id.visible_removing_fragment_view_tag, null);
            }
            viewGroup2 = fragment.mContainer;
            if (viewGroup2 != null) {
            }
            view = fragment.mView;
            if (view != null) {
            }
            if (i == 0) {
                i = i2 == 4097 ? i2 != 8194 ? i2 != 8197 ? i2 != 4099 ? i2 != 4100 ? -1 : z2 ? FragmentAnim.toActivityTransitResId(android.R.attr.activityOpenEnterAnimation, context) : FragmentAnim.toActivityTransitResId(android.R.attr.activityOpenExitAnimation, context) : z2 ? R.animator.fragment_fade_enter : R.animator.fragment_fade_exit : z2 ? FragmentAnim.toActivityTransitResId(android.R.attr.activityCloseEnterAnimation, context) : FragmentAnim.toActivityTransitResId(android.R.attr.activityCloseExitAnimation, context) : z2 ? R.animator.fragment_close_enter : R.animator.fragment_close_exit : z2 ? R.animator.fragment_open_enter : R.animator.fragment_open_exit;
            }
            if (i != 0) {
            }
            this.mAnimation = animationOrAnimator2222222;
            this.mLoadedAnim = true;
            return animationOrAnimator2222222;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class SpecialEffectsInfo {
        public final SpecialEffectsController.Operation mOperation;
        public final CancellationSignal mSignal;

        public SpecialEffectsInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal) {
            this.mOperation = operation;
            this.mSignal = cancellationSignal;
        }

        public final void completeSpecialEffect() {
            SpecialEffectsController.Operation operation = this.mOperation;
            HashSet hashSet = operation.mSpecialEffectsSignals;
            if (hashSet.remove(this.mSignal) && hashSet.isEmpty()) {
                operation.complete();
            }
        }

        public final boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State state;
            SpecialEffectsController.Operation operation = this.mOperation;
            SpecialEffectsController.Operation.State from = SpecialEffectsController.Operation.State.from(operation.mFragment.mView);
            SpecialEffectsController.Operation.State state2 = operation.mFinalState;
            return from == state2 || !(from == (state = SpecialEffectsController.Operation.State.VISIBLE) || state2 == state);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TransitionInfo extends SpecialEffectsInfo {
        public final boolean mOverlapAllowed;
        public final Object mSharedElementTransition;
        public final Object mTransition;

        /* JADX WARN: Code restructure failed: missing block: B:33:0x0036, code lost:
        
            if (r5 == androidx.fragment.app.Fragment.USE_DEFAULT_TRANSITION) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:7:0x0018, code lost:
        
            if (r5 == androidx.fragment.app.Fragment.USE_DEFAULT_TRANSITION) goto L12;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public TransitionInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal, boolean z, boolean z2) {
            super(operation, cancellationSignal);
            Object obj;
            Object obj2;
            Object obj3;
            SpecialEffectsController.Operation.State state = operation.mFinalState;
            SpecialEffectsController.Operation.State state2 = SpecialEffectsController.Operation.State.VISIBLE;
            Object obj4 = null;
            Fragment fragment = operation.mFragment;
            if (state == state2) {
                if (z) {
                    Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
                    if (animationInfo != null) {
                        obj3 = animationInfo.mReenterTransition;
                    }
                } else {
                    fragment.getClass();
                }
                obj3 = null;
                this.mTransition = obj3;
                if (z) {
                    Fragment.AnimationInfo animationInfo2 = fragment.mAnimationInfo;
                } else {
                    Fragment.AnimationInfo animationInfo3 = fragment.mAnimationInfo;
                }
                this.mOverlapAllowed = true;
            } else {
                if (z) {
                    Fragment.AnimationInfo animationInfo4 = fragment.mAnimationInfo;
                    if (animationInfo4 != null) {
                        obj = animationInfo4.mReturnTransition;
                    }
                } else {
                    fragment.getClass();
                }
                obj = null;
                this.mTransition = obj;
                this.mOverlapAllowed = true;
            }
            if (!z2) {
                this.mSharedElementTransition = null;
                return;
            }
            if (!z) {
                fragment.getClass();
                this.mSharedElementTransition = null;
                return;
            }
            Fragment.AnimationInfo animationInfo5 = fragment.mAnimationInfo;
            if (animationInfo5 != null && (obj2 = animationInfo5.mSharedElementReturnTransition) != Fragment.USE_DEFAULT_TRANSITION) {
                obj4 = obj2;
            }
            this.mSharedElementTransition = obj4;
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
            throw new IllegalArgumentException("Transition " + obj + " for fragment " + this.mOperation.mFragment + " is not a valid framework Transition or AndroidX Transition");
        }
    }

    public DefaultSpecialEffectsController(ViewGroup viewGroup) {
        super(viewGroup);
    }

    public static void captureTransitioningViews(ArrayList arrayList, View view) {
        if (!(view instanceof ViewGroup)) {
            if (arrayList.contains(view)) {
                return;
            }
            arrayList.add(view);
            return;
        }
        if (!arrayList.contains(view)) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api21Impl.getTransitionName(view) != null) {
                arrayList.add(view);
            }
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.getVisibility() == 0) {
                captureTransitioningViews(arrayList, childAt);
            }
        }
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

    public static void retainMatchingViews(ArrayMap arrayMap, Collection collection) {
        Iterator it = ((ArrayMap.EntrySet) arrayMap.entrySet()).iterator();
        while (true) {
            ArrayMap.MapIterator mapIterator = (ArrayMap.MapIterator) it;
            if (!mapIterator.hasNext()) {
                return;
            }
            mapIterator.next();
            View view = (View) mapIterator.getValue();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (!collection.contains(ViewCompat.Api21Impl.getTransitionName(view))) {
                mapIterator.remove();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:138:0x071c  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x07ad A[LOOP:7: B:167:0x07a7->B:169:0x07ad, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x07c7  */
    /* JADX WARN: Removed duplicated region for block: B:175:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:301:0x04e1  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x04fa  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x0503  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x04ec  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x067d  */
    @Override // androidx.fragment.app.SpecialEffectsController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void executeOperations(List list, final boolean z) {
        ArrayList arrayList;
        HashMap hashMap;
        SpecialEffectsController.Operation operation;
        ArrayList arrayList2;
        SpecialEffectsController.Operation operation2;
        SpecialEffectsController.Operation operation3;
        boolean z2;
        SpecialEffectsController.Operation operation4;
        Iterator it;
        boolean z3;
        Object obj;
        ArrayList arrayList3;
        View view;
        View view2;
        Object obj2;
        ArrayMap arrayMap;
        View view3;
        ArrayList arrayList4;
        ArrayList arrayList5;
        SpecialEffectsController.Operation operation5;
        ArrayList arrayList6;
        ArrayList arrayList7;
        final FragmentTransitionImpl fragmentTransitionImpl;
        final Rect rect;
        HashMap hashMap2;
        SpecialEffectsController.Operation operation6;
        Object obj3;
        View view4;
        ArrayList arrayList8;
        ArrayList arrayList9;
        ArrayList arrayList10;
        ArrayList arrayList11;
        final View view5;
        Iterator it2;
        Iterator it3;
        Iterator it4;
        boolean z4;
        Iterator it5;
        DefaultSpecialEffectsController defaultSpecialEffectsController = this;
        ArrayList arrayList12 = (ArrayList) list;
        Iterator it6 = arrayList12.iterator();
        SpecialEffectsController.Operation operation7 = null;
        SpecialEffectsController.Operation operation8 = null;
        while (it6.hasNext()) {
            SpecialEffectsController.Operation operation9 = (SpecialEffectsController.Operation) it6.next();
            SpecialEffectsController.Operation.State from = SpecialEffectsController.Operation.State.from(operation9.mFragment.mView);
            int i = AbstractC021010.f171xe493b431[operation9.mFinalState.ordinal()];
            if (i == 1 || i == 2 || i == 3) {
                if (from == SpecialEffectsController.Operation.State.VISIBLE && operation7 == null) {
                    operation7 = operation9;
                }
            } else if (i == 4 && from != SpecialEffectsController.Operation.State.VISIBLE) {
                operation8 = operation9;
            }
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Objects.toString(operation7);
            Objects.toString(operation8);
        }
        ArrayList arrayList13 = new ArrayList();
        ArrayList arrayList14 = new ArrayList();
        final ArrayList arrayList15 = new ArrayList(list);
        Fragment fragment = ((SpecialEffectsController.Operation) arrayList12.get(arrayList12.size() - 1)).mFragment;
        Iterator it7 = arrayList12.iterator();
        while (it7.hasNext()) {
            Fragment.AnimationInfo animationInfo = ((SpecialEffectsController.Operation) it7.next()).mFragment.mAnimationInfo;
            Fragment.AnimationInfo animationInfo2 = fragment.mAnimationInfo;
            animationInfo.mEnterAnim = animationInfo2.mEnterAnim;
            animationInfo.mExitAnim = animationInfo2.mExitAnim;
            animationInfo.mPopEnterAnim = animationInfo2.mPopEnterAnim;
            animationInfo.mPopExitAnim = animationInfo2.mPopExitAnim;
        }
        Iterator it8 = arrayList12.iterator();
        while (it8.hasNext()) {
            final SpecialEffectsController.Operation operation10 = (SpecialEffectsController.Operation) it8.next();
            CancellationSignal cancellationSignal = new CancellationSignal();
            operation10.onStart();
            operation10.mSpecialEffectsSignals.add(cancellationSignal);
            arrayList13.add(new AnimationInfo(operation10, cancellationSignal, z));
            CancellationSignal cancellationSignal2 = new CancellationSignal();
            operation10.onStart();
            operation10.mSpecialEffectsSignals.add(cancellationSignal2);
            arrayList14.add(new TransitionInfo(operation10, cancellationSignal2, z, !z ? operation10 != operation8 : operation10 != operation7));
            ((ArrayList) operation10.mCompletionListeners).add(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.1
                @Override // java.lang.Runnable
                public final void run() {
                    if (arrayList15.contains(operation10)) {
                        arrayList15.remove(operation10);
                        DefaultSpecialEffectsController defaultSpecialEffectsController2 = DefaultSpecialEffectsController.this;
                        SpecialEffectsController.Operation operation11 = operation10;
                        defaultSpecialEffectsController2.getClass();
                        operation11.mFinalState.applyState(operation11.mFragment.mView);
                    }
                }
            });
        }
        HashMap hashMap3 = new HashMap();
        Iterator it9 = arrayList14.iterator();
        FragmentTransitionImpl fragmentTransitionImpl2 = null;
        while (it9.hasNext()) {
            TransitionInfo transitionInfo = (TransitionInfo) it9.next();
            if (transitionInfo.isVisibilityUnchanged()) {
                it5 = it9;
            } else {
                Object obj4 = transitionInfo.mTransition;
                FragmentTransitionImpl handlingImpl = transitionInfo.getHandlingImpl(obj4);
                Object obj5 = transitionInfo.mSharedElementTransition;
                FragmentTransitionImpl handlingImpl2 = transitionInfo.getHandlingImpl(obj5);
                it5 = it9;
                SpecialEffectsController.Operation operation11 = transitionInfo.mOperation;
                if (handlingImpl != null && handlingImpl2 != null && handlingImpl != handlingImpl2) {
                    throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + operation11.mFragment + " returned Transition " + obj4 + " which uses a different Transition  type than its shared element transition " + obj5);
                }
                if (handlingImpl == null) {
                    handlingImpl = handlingImpl2;
                }
                if (fragmentTransitionImpl2 == null) {
                    fragmentTransitionImpl2 = handlingImpl;
                } else if (handlingImpl != null && fragmentTransitionImpl2 != handlingImpl) {
                    throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + operation11.mFragment + " returned Transition " + obj4 + " which uses a different Transition  type than other Fragments.");
                }
            }
            it9 = it5;
        }
        final ViewGroup viewGroup = defaultSpecialEffectsController.mContainer;
        if (fragmentTransitionImpl2 == null) {
            Iterator it10 = arrayList14.iterator();
            while (it10.hasNext()) {
                TransitionInfo transitionInfo2 = (TransitionInfo) it10.next();
                hashMap3.put(transitionInfo2.mOperation, Boolean.FALSE);
                transitionInfo2.completeSpecialEffect();
            }
            arrayList2 = arrayList15;
            operation = operation7;
            hashMap = hashMap3;
            arrayList = arrayList13;
            operation2 = operation8;
        } else {
            View view6 = new View(viewGroup.getContext());
            Rect rect2 = new Rect();
            ArrayList arrayList16 = new ArrayList();
            ArrayList arrayList17 = new ArrayList();
            ArrayMap arrayMap2 = new ArrayMap();
            Iterator it11 = arrayList14.iterator();
            ArrayList arrayList18 = arrayList15;
            SpecialEffectsController.Operation operation12 = operation7;
            SpecialEffectsController.Operation operation13 = operation8;
            Object obj6 = null;
            View view7 = null;
            boolean z5 = false;
            while (it11.hasNext()) {
                ArrayList arrayList19 = arrayList13;
                Object obj7 = ((TransitionInfo) it11.next()).mSharedElementTransition;
                if (!(obj7 != null) || operation12 == null || operation13 == null) {
                    arrayMap = arrayMap2;
                    view3 = view6;
                    arrayList4 = arrayList14;
                    arrayList5 = arrayList18;
                    operation5 = operation7;
                    arrayList6 = arrayList16;
                    arrayList7 = arrayList17;
                    SpecialEffectsController.Operation operation14 = operation8;
                    fragmentTransitionImpl = fragmentTransitionImpl2;
                    rect = rect2;
                    hashMap2 = hashMap3;
                    operation6 = operation14;
                    obj3 = obj6;
                    view4 = view7;
                } else {
                    obj3 = fragmentTransitionImpl2.wrapTransitionInSet(fragmentTransitionImpl2.cloneTransition(obj7));
                    Fragment fragment2 = operation13.mFragment;
                    Fragment.AnimationInfo animationInfo3 = fragment2.mAnimationInfo;
                    if (animationInfo3 == null || (arrayList8 = animationInfo3.mSharedElementSourceNames) == null) {
                        arrayList8 = new ArrayList();
                    }
                    Fragment fragment3 = operation12.mFragment;
                    FragmentTransitionImpl fragmentTransitionImpl3 = fragmentTransitionImpl2;
                    Fragment.AnimationInfo animationInfo4 = fragment3.mAnimationInfo;
                    if (animationInfo4 == null || (arrayList9 = animationInfo4.mSharedElementSourceNames) == null) {
                        arrayList9 = new ArrayList();
                    }
                    arrayList4 = arrayList14;
                    Fragment.AnimationInfo animationInfo5 = fragment3.mAnimationInfo;
                    if (animationInfo5 == null || (arrayList10 = animationInfo5.mSharedElementTargetNames) == null) {
                        arrayList10 = new ArrayList();
                    }
                    View view8 = view6;
                    HashMap hashMap4 = hashMap3;
                    int i2 = 0;
                    while (i2 < arrayList10.size()) {
                        int indexOf = arrayList8.indexOf(arrayList10.get(i2));
                        ArrayList arrayList20 = arrayList10;
                        if (indexOf != -1) {
                            arrayList8.set(indexOf, (String) arrayList9.get(i2));
                        }
                        i2++;
                        arrayList10 = arrayList20;
                    }
                    Fragment.AnimationInfo animationInfo6 = fragment2.mAnimationInfo;
                    if (animationInfo6 == null || (arrayList11 = animationInfo6.mSharedElementTargetNames) == null) {
                        arrayList11 = new ArrayList();
                    }
                    ArrayList arrayList21 = arrayList11;
                    int i3 = 0;
                    for (int size = arrayList8.size(); i3 < size; size = size) {
                        arrayMap2.put((String) arrayList8.get(i3), (String) arrayList21.get(i3));
                        i3++;
                    }
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Iterator it12 = arrayList21.iterator();
                        while (it12.hasNext()) {
                        }
                        Iterator it13 = arrayList8.iterator();
                        while (it13.hasNext()) {
                        }
                    }
                    ArrayMap arrayMap3 = new ArrayMap();
                    findNamedViews(arrayMap3, fragment3.mView);
                    arrayMap3.retainAll(arrayList8);
                    arrayMap2.retainAll(arrayMap3.keySet());
                    final ArrayMap arrayMap4 = new ArrayMap();
                    findNamedViews(arrayMap4, fragment2.mView);
                    arrayMap4.retainAll(arrayList21);
                    arrayMap4.retainAll(arrayMap2.values());
                    FragmentTransitionCompat21 fragmentTransitionCompat21 = FragmentTransition.PLATFORM_IMPL;
                    for (int i4 = arrayMap2.size - 1; i4 >= 0; i4--) {
                        if (!arrayMap4.containsKey((String) arrayMap2.valueAt(i4))) {
                            arrayMap2.removeAt(i4);
                        }
                    }
                    retainMatchingViews(arrayMap3, arrayMap2.keySet());
                    retainMatchingViews(arrayMap4, arrayMap2.values());
                    if (arrayMap2.isEmpty()) {
                        arrayList16.clear();
                        arrayList17.clear();
                        arrayMap = arrayMap2;
                        operation12 = operation7;
                        operation5 = operation12;
                        operation13 = operation8;
                        operation6 = operation13;
                        rect = rect2;
                        arrayList5 = arrayList18;
                        fragmentTransitionImpl = fragmentTransitionImpl3;
                        hashMap2 = hashMap4;
                        view3 = view8;
                        obj6 = null;
                        arrayList6 = arrayList16;
                        arrayList7 = arrayList17;
                        arrayList17 = arrayList7;
                        arrayList16 = arrayList6;
                        operation7 = operation5;
                        arrayList13 = arrayList19;
                        arrayList14 = arrayList4;
                        arrayList18 = arrayList5;
                        view6 = view3;
                        arrayMap2 = arrayMap;
                        HashMap hashMap5 = hashMap2;
                        rect2 = rect;
                        fragmentTransitionImpl2 = fragmentTransitionImpl;
                        operation8 = operation6;
                        hashMap3 = hashMap5;
                    } else {
                        View view9 = view7;
                        arrayMap = arrayMap2;
                        ArrayList arrayList22 = arrayList17;
                        final SpecialEffectsController.Operation operation15 = operation8;
                        SpecialEffectsController.Operation operation16 = operation8;
                        Rect rect3 = rect2;
                        fragmentTransitionImpl = fragmentTransitionImpl3;
                        final SpecialEffectsController.Operation operation17 = operation7;
                        SpecialEffectsController.Operation operation18 = operation7;
                        arrayList5 = arrayList18;
                        arrayList6 = arrayList16;
                        OneShotPreDrawListener.add(viewGroup, new Runnable(this) { // from class: androidx.fragment.app.DefaultSpecialEffectsController.6
                            @Override // java.lang.Runnable
                            public final void run() {
                                Fragment fragment4 = operation15.mFragment;
                                Fragment fragment5 = operation17.mFragment;
                                boolean z6 = z;
                                FragmentTransitionCompat21 fragmentTransitionCompat212 = FragmentTransition.PLATFORM_IMPL;
                                if (z6) {
                                    fragment5.getClass();
                                } else {
                                    fragment4.getClass();
                                }
                            }
                        });
                        Iterator it14 = ((ArrayMap.ValueCollection) arrayMap3.values()).iterator();
                        while (true) {
                            IndexBasedArrayIterator indexBasedArrayIterator = (IndexBasedArrayIterator) it14;
                            if (!indexBasedArrayIterator.hasNext()) {
                                break;
                            } else {
                                captureTransitioningViews(arrayList6, (View) indexBasedArrayIterator.next());
                            }
                        }
                        if (arrayList8.isEmpty()) {
                            view4 = view9;
                        } else {
                            view4 = (View) arrayMap3.get((String) arrayList8.get(0));
                            fragmentTransitionImpl.setEpicenter(view4, obj3);
                        }
                        Iterator it15 = ((ArrayMap.ValueCollection) arrayMap4.values()).iterator();
                        while (true) {
                            IndexBasedArrayIterator indexBasedArrayIterator2 = (IndexBasedArrayIterator) it15;
                            if (!indexBasedArrayIterator2.hasNext()) {
                                break;
                            } else {
                                captureTransitioningViews(arrayList22, (View) indexBasedArrayIterator2.next());
                            }
                        }
                        if (arrayList21.isEmpty() || (view5 = (View) arrayMap4.get((String) arrayList21.get(0))) == null) {
                            arrayList7 = arrayList22;
                            rect = rect3;
                            defaultSpecialEffectsController = this;
                            view3 = view8;
                        } else {
                            arrayList7 = arrayList22;
                            rect = rect3;
                            defaultSpecialEffectsController = this;
                            OneShotPreDrawListener.add(viewGroup, new Runnable(defaultSpecialEffectsController) { // from class: androidx.fragment.app.DefaultSpecialEffectsController.7
                                @Override // java.lang.Runnable
                                public final void run() {
                                    FragmentTransitionImpl fragmentTransitionImpl4 = fragmentTransitionImpl;
                                    View view10 = view5;
                                    Rect rect4 = rect;
                                    fragmentTransitionImpl4.getClass();
                                    FragmentTransitionImpl.getBoundsOnScreen(rect4, view10);
                                }
                            });
                            view3 = view8;
                            z5 = true;
                        }
                        fragmentTransitionImpl.setSharedElementTargets(obj3, view3, arrayList6);
                        fragmentTransitionImpl.scheduleRemoveTargets(obj3, null, null, obj3, arrayList7);
                        Boolean bool = Boolean.TRUE;
                        hashMap2 = hashMap4;
                        operation5 = operation18;
                        hashMap2.put(operation5, bool);
                        operation6 = operation16;
                        hashMap2.put(operation6, bool);
                        operation12 = operation5;
                        operation13 = operation6;
                    }
                }
                view7 = view4;
                obj6 = obj3;
                arrayList17 = arrayList7;
                arrayList16 = arrayList6;
                operation7 = operation5;
                arrayList13 = arrayList19;
                arrayList14 = arrayList4;
                arrayList18 = arrayList5;
                view6 = view3;
                arrayMap2 = arrayMap;
                HashMap hashMap52 = hashMap2;
                rect2 = rect;
                fragmentTransitionImpl2 = fragmentTransitionImpl;
                operation8 = operation6;
                hashMap3 = hashMap52;
            }
            ArrayMap arrayMap5 = arrayMap2;
            View view10 = view6;
            arrayList = arrayList13;
            ArrayList arrayList23 = arrayList14;
            View view11 = view7;
            ArrayList arrayList24 = arrayList18;
            SpecialEffectsController.Operation operation19 = operation7;
            final ArrayList arrayList25 = arrayList16;
            final ArrayList arrayList26 = arrayList17;
            SpecialEffectsController.Operation operation20 = operation8;
            final FragmentTransitionImpl fragmentTransitionImpl4 = fragmentTransitionImpl2;
            Rect rect4 = rect2;
            hashMap = hashMap3;
            SpecialEffectsController.Operation operation21 = operation20;
            ArrayList arrayList27 = new ArrayList();
            Iterator it16 = arrayList23.iterator();
            operation = operation19;
            Object obj8 = null;
            Object obj9 = null;
            while (it16.hasNext()) {
                Iterator it17 = it16;
                TransitionInfo transitionInfo3 = (TransitionInfo) it16.next();
                boolean isVisibilityUnchanged = transitionInfo3.isVisibilityUnchanged();
                SpecialEffectsController.Operation operation22 = operation21;
                SpecialEffectsController.Operation operation23 = transitionInfo3.mOperation;
                if (isVisibilityUnchanged) {
                    hashMap.put(operation23, Boolean.FALSE);
                    transitionInfo3.completeSpecialEffect();
                    obj = obj6;
                    view = view10;
                    obj2 = obj8;
                    obj9 = obj9;
                    view2 = view11;
                    arrayList3 = arrayList24;
                } else {
                    Object obj10 = obj9;
                    Object cloneTransition = fragmentTransitionImpl4.cloneTransition(transitionInfo3.mTransition);
                    boolean z6 = obj6 != null && (operation23 == operation12 || operation23 == operation13);
                    if (cloneTransition == null) {
                        if (!z6) {
                            hashMap.put(operation23, Boolean.FALSE);
                            transitionInfo3.completeSpecialEffect();
                        }
                        obj = obj6;
                        view = view10;
                        obj2 = obj8;
                        obj9 = obj10;
                        view2 = view11;
                        arrayList3 = arrayList24;
                    } else {
                        obj = obj6;
                        final ArrayList arrayList28 = new ArrayList();
                        Object obj11 = obj8;
                        captureTransitioningViews(arrayList28, operation23.mFragment.mView);
                        if (z6) {
                            if (operation23 == operation12) {
                                arrayList28.removeAll(arrayList25);
                            } else {
                                arrayList28.removeAll(arrayList26);
                            }
                        }
                        if (arrayList28.isEmpty()) {
                            fragmentTransitionImpl4.addTarget(view10, cloneTransition);
                        } else {
                            fragmentTransitionImpl4.addTargets(cloneTransition, arrayList28);
                            fragmentTransitionImpl4.scheduleRemoveTargets(cloneTransition, cloneTransition, arrayList28, null, null);
                            if (operation23.mFinalState == SpecialEffectsController.Operation.State.GONE) {
                                arrayList3 = arrayList24;
                                arrayList3.remove(operation23);
                                fragmentTransitionImpl4.scheduleHideFragmentView(cloneTransition, operation23.mFragment.mView, arrayList28);
                                OneShotPreDrawListener.add(viewGroup, new Runnable(defaultSpecialEffectsController) { // from class: androidx.fragment.app.DefaultSpecialEffectsController.8
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        FragmentTransition.setViewVisibility(arrayList28, 4);
                                    }
                                });
                                view = view10;
                                if (operation23.mFinalState != SpecialEffectsController.Operation.State.VISIBLE) {
                                    arrayList27.addAll(arrayList28);
                                    if (z5) {
                                        fragmentTransitionImpl4.setEpicenter(cloneTransition, rect4);
                                    }
                                    view2 = view11;
                                } else {
                                    view2 = view11;
                                    fragmentTransitionImpl4.setEpicenter(view2, cloneTransition);
                                }
                                hashMap.put(operation23, Boolean.TRUE);
                                if (transitionInfo3.mOverlapAllowed) {
                                    obj2 = obj11;
                                    obj9 = fragmentTransitionImpl4.mergeTransitionsTogether(obj10, cloneTransition);
                                } else {
                                    obj2 = fragmentTransitionImpl4.mergeTransitionsTogether(obj11, cloneTransition);
                                    obj9 = obj10;
                                }
                            }
                        }
                        arrayList3 = arrayList24;
                        view = view10;
                        if (operation23.mFinalState != SpecialEffectsController.Operation.State.VISIBLE) {
                        }
                        hashMap.put(operation23, Boolean.TRUE);
                        if (transitionInfo3.mOverlapAllowed) {
                        }
                    }
                    operation13 = operation22;
                }
                it16 = it17;
                view11 = view2;
                arrayList24 = arrayList3;
                obj6 = obj;
                operation21 = operation22;
                obj8 = obj2;
                view10 = view;
            }
            SpecialEffectsController.Operation operation24 = operation21;
            arrayList2 = arrayList24;
            Object obj12 = obj6;
            Object mergeTransitionsInSequence = fragmentTransitionImpl4.mergeTransitionsInSequence(obj8, obj9, obj12);
            if (mergeTransitionsInSequence == null) {
                operation2 = operation24;
            } else {
                Iterator it18 = arrayList23.iterator();
                while (it18.hasNext()) {
                    TransitionInfo transitionInfo4 = (TransitionInfo) it18.next();
                    if (!transitionInfo4.isVisibilityUnchanged()) {
                        SpecialEffectsController.Operation operation25 = transitionInfo4.mOperation;
                        SpecialEffectsController.Operation operation26 = operation24;
                        if (obj12 == null || !(operation25 == operation12 || operation25 == operation26)) {
                            it = it18;
                            z3 = false;
                        } else {
                            it = it18;
                            z3 = true;
                        }
                        if (transitionInfo4.mTransition != null || z3) {
                            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            if (ViewCompat.Api19Impl.isLaidOut(viewGroup)) {
                                Fragment fragment4 = operation25.mFragment;
                                fragmentTransitionImpl4.setListenerForTransitionEnd(mergeTransitionsInSequence, transitionInfo4.mSignal, new RunnableC02189(defaultSpecialEffectsController, transitionInfo4, operation25));
                            } else {
                                if (FragmentManager.isLoggingEnabled(2)) {
                                    Objects.toString(viewGroup);
                                    Objects.toString(operation25);
                                }
                                transitionInfo4.completeSpecialEffect();
                            }
                        }
                        it18 = it;
                        operation24 = operation26;
                    }
                }
                operation2 = operation24;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api19Impl.isLaidOut(viewGroup)) {
                    FragmentTransition.setViewVisibility(arrayList27, 4);
                    final ArrayList arrayList29 = new ArrayList();
                    int size2 = arrayList26.size();
                    for (int i5 = 0; i5 < size2; i5++) {
                        View view12 = (View) arrayList26.get(i5);
                        WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                        arrayList29.add(ViewCompat.Api21Impl.getTransitionName(view12));
                        ViewCompat.Api21Impl.setTransitionName(view12, null);
                    }
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Iterator it19 = arrayList25.iterator();
                        while (it19.hasNext()) {
                            View view13 = (View) it19.next();
                            Objects.toString(view13);
                            ViewCompat.Api21Impl.getTransitionName(view13);
                        }
                        Iterator it20 = arrayList26.iterator();
                        while (it20.hasNext()) {
                            View view14 = (View) it20.next();
                            Objects.toString(view14);
                            ViewCompat.Api21Impl.getTransitionName(view14);
                        }
                    }
                    fragmentTransitionImpl4.beginDelayedTransition(viewGroup, mergeTransitionsInSequence);
                    final int size3 = arrayList26.size();
                    final ArrayList arrayList30 = new ArrayList();
                    int i6 = 0;
                    while (i6 < size3) {
                        View view15 = (View) arrayList25.get(i6);
                        WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                        String transitionName = ViewCompat.Api21Impl.getTransitionName(view15);
                        arrayList30.add(transitionName);
                        if (transitionName == null) {
                            operation4 = operation2;
                        } else {
                            operation4 = operation2;
                            ViewCompat.Api21Impl.setTransitionName(view15, null);
                            String str = (String) arrayMap5.get(transitionName);
                            int i7 = 0;
                            while (true) {
                                if (i7 >= size3) {
                                    break;
                                }
                                if (str.equals(arrayList29.get(i7))) {
                                    ViewCompat.Api21Impl.setTransitionName((View) arrayList26.get(i7), transitionName);
                                    break;
                                }
                                i7++;
                            }
                        }
                        i6++;
                        operation2 = operation4;
                    }
                    operation3 = operation2;
                    OneShotPreDrawListener.add(viewGroup, new Runnable(fragmentTransitionImpl4, size3, arrayList26, arrayList29, arrayList25, arrayList30) { // from class: androidx.fragment.app.FragmentTransitionImpl.1
                        public final /* synthetic */ ArrayList val$inNames;
                        public final /* synthetic */ int val$numSharedElements;
                        public final /* synthetic */ ArrayList val$outNames;
                        public final /* synthetic */ ArrayList val$sharedElementsIn;
                        public final /* synthetic */ ArrayList val$sharedElementsOut;

                        public RunnableC02531(final FragmentTransitionImpl fragmentTransitionImpl42, final int size32, final ArrayList arrayList262, final ArrayList arrayList292, final ArrayList arrayList252, final ArrayList arrayList302) {
                            this.val$numSharedElements = size32;
                            this.val$sharedElementsIn = arrayList262;
                            this.val$inNames = arrayList292;
                            this.val$sharedElementsOut = arrayList252;
                            this.val$outNames = arrayList302;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            for (int i8 = 0; i8 < this.val$numSharedElements; i8++) {
                                View view16 = (View) this.val$sharedElementsIn.get(i8);
                                String str2 = (String) this.val$inNames.get(i8);
                                WeakHashMap weakHashMap5 = ViewCompat.sViewPropertyAnimatorMap;
                                ViewCompat.Api21Impl.setTransitionName(view16, str2);
                                ViewCompat.Api21Impl.setTransitionName((View) this.val$sharedElementsOut.get(i8), (String) this.val$outNames.get(i8));
                            }
                        }
                    });
                    z2 = false;
                    FragmentTransition.setViewVisibility(arrayList27, 0);
                    fragmentTransitionImpl42.swapSharedElementTargets(obj12, arrayList252, arrayList262);
                    boolean containsValue = hashMap.containsValue(Boolean.TRUE);
                    Context context = viewGroup.getContext();
                    ArrayList arrayList31 = new ArrayList();
                    it2 = arrayList.iterator();
                    boolean z7 = z2;
                    while (it2.hasNext()) {
                        final AnimationInfo animationInfo7 = (AnimationInfo) it2.next();
                        if (animationInfo7.isVisibilityUnchanged()) {
                            animationInfo7.completeSpecialEffect();
                        } else {
                            FragmentAnim.AnimationOrAnimator animation = animationInfo7.getAnimation(context);
                            if (animation == null) {
                                animationInfo7.completeSpecialEffect();
                            } else {
                                final Animator animator = animation.animator;
                                if (animator == null) {
                                    arrayList31.add(animationInfo7);
                                } else {
                                    final SpecialEffectsController.Operation operation27 = animationInfo7.mOperation;
                                    Fragment fragment5 = operation27.mFragment;
                                    if (Boolean.TRUE.equals(hashMap.get(operation27))) {
                                        if (FragmentManager.isLoggingEnabled(2)) {
                                            Objects.toString(fragment5);
                                        }
                                        animationInfo7.completeSpecialEffect();
                                    } else {
                                        boolean z8 = operation27.mFinalState == SpecialEffectsController.Operation.State.GONE ? true : z2;
                                        if (z8) {
                                            arrayList2.remove(operation27);
                                        }
                                        final View view16 = fragment5.mView;
                                        viewGroup.startViewTransition(view16);
                                        final boolean z9 = z8;
                                        HashMap hashMap6 = hashMap;
                                        animator.addListener(new AnimatorListenerAdapter(this) { // from class: androidx.fragment.app.DefaultSpecialEffectsController.2
                                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                            public final void onAnimationEnd(Animator animator2) {
                                                viewGroup.endViewTransition(view16);
                                                if (z9) {
                                                    operation27.mFinalState.applyState(view16);
                                                }
                                                animationInfo7.completeSpecialEffect();
                                                if (FragmentManager.isLoggingEnabled(2)) {
                                                    Objects.toString(operation27);
                                                }
                                            }
                                        });
                                        animator.setTarget(view16);
                                        animator.start();
                                        if (FragmentManager.isLoggingEnabled(2)) {
                                            operation27.toString();
                                        }
                                        animationInfo7.mSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(this) { // from class: androidx.fragment.app.DefaultSpecialEffectsController.3
                                            @Override // androidx.core.os.CancellationSignal.OnCancelListener
                                            public final void onCancel() {
                                                animator.end();
                                                if (FragmentManager.isLoggingEnabled(2)) {
                                                    Objects.toString(operation27);
                                                }
                                            }
                                        });
                                        hashMap = hashMap6;
                                        z7 = true;
                                        z2 = false;
                                    }
                                }
                            }
                        }
                    }
                    it3 = arrayList31.iterator();
                    while (it3.hasNext()) {
                        final AnimationInfo animationInfo8 = (AnimationInfo) it3.next();
                        final SpecialEffectsController.Operation operation28 = animationInfo8.mOperation;
                        Fragment fragment6 = operation28.mFragment;
                        if (containsValue) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Objects.toString(fragment6);
                            }
                            animationInfo8.completeSpecialEffect();
                        } else if (z7) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Objects.toString(fragment6);
                            }
                            animationInfo8.completeSpecialEffect();
                        } else {
                            final View view17 = fragment6.mView;
                            FragmentAnim.AnimationOrAnimator animation2 = animationInfo8.getAnimation(context);
                            animation2.getClass();
                            Animation animation3 = animation2.animation;
                            animation3.getClass();
                            if (operation28.mFinalState != SpecialEffectsController.Operation.State.REMOVED) {
                                view17.startAnimation(animation3);
                                animationInfo8.completeSpecialEffect();
                                z4 = z7;
                            } else {
                                viewGroup.startViewTransition(view17);
                                FragmentAnim.EndViewTransitionAnimation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(animation3, viewGroup, view17);
                                z4 = z7;
                                endViewTransitionAnimation.setAnimationListener(new Animation.AnimationListener(this) { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4
                                    @Override // android.view.animation.Animation.AnimationListener
                                    public final void onAnimationEnd(Animation animation4) {
                                        viewGroup.post(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4.1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                AnimationAnimationListenerC02134 animationAnimationListenerC02134 = AnimationAnimationListenerC02134.this;
                                                viewGroup.endViewTransition(view17);
                                                animationInfo8.completeSpecialEffect();
                                            }
                                        });
                                        if (FragmentManager.isLoggingEnabled(2)) {
                                            Objects.toString(operation28);
                                        }
                                    }

                                    @Override // android.view.animation.Animation.AnimationListener
                                    public final void onAnimationStart(Animation animation4) {
                                        if (FragmentManager.isLoggingEnabled(2)) {
                                            Objects.toString(operation28);
                                        }
                                    }

                                    @Override // android.view.animation.Animation.AnimationListener
                                    public final void onAnimationRepeat(Animation animation4) {
                                    }
                                });
                                view17.startAnimation(endViewTransitionAnimation);
                                if (FragmentManager.isLoggingEnabled(2)) {
                                    operation28.toString();
                                }
                            }
                            animationInfo8.mSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(this) { // from class: androidx.fragment.app.DefaultSpecialEffectsController.5
                                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                                public final void onCancel() {
                                    View view18 = view17;
                                    view18.clearAnimation();
                                    viewGroup.endViewTransition(view18);
                                    animationInfo8.completeSpecialEffect();
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Objects.toString(operation28);
                                    }
                                }
                            });
                            z7 = z4;
                        }
                    }
                    it4 = arrayList2.iterator();
                    while (it4.hasNext()) {
                        SpecialEffectsController.Operation operation29 = (SpecialEffectsController.Operation) it4.next();
                        operation29.mFinalState.applyState(operation29.mFragment.mView);
                    }
                    arrayList2.clear();
                    if (FragmentManager.isLoggingEnabled(2)) {
                        return;
                    }
                    Objects.toString(operation);
                    Objects.toString(operation3);
                    return;
                }
            }
        }
        operation3 = operation2;
        z2 = false;
        boolean containsValue2 = hashMap.containsValue(Boolean.TRUE);
        Context context2 = viewGroup.getContext();
        ArrayList arrayList312 = new ArrayList();
        it2 = arrayList.iterator();
        boolean z72 = z2;
        while (it2.hasNext()) {
        }
        it3 = arrayList312.iterator();
        while (it3.hasNext()) {
        }
        it4 = arrayList2.iterator();
        while (it4.hasNext()) {
        }
        arrayList2.clear();
        if (FragmentManager.isLoggingEnabled(2)) {
        }
    }
}
