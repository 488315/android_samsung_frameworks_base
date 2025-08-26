package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.util.Property;
import android.view.View;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.headsup.HeadsUpAnimator;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class StackStateAnimator {
    public final AnonymousClass1 mAnimationProperties;
    public ValueAnimator mBottomOverScrollAnimator;
    public long mCurrentAdditionalDelay;
    public long mCurrentLength;
    int mGoToFullShadeAppearingTranslation;
    public int mHeadsUpAppearHeightBottom;
    float mHeadsUpAppearStartAboveScreen;
    public float mHeadsUpCyclingPadding;
    public final NotificationStackScrollLayout mHostLayout;
    public StackStateLogger mLogger;
    public boolean mShadeExpanded;
    public int mStackTopMargin;
    public ValueAnimator mTopOverScrollAnimator;
    public final ExpandableViewState mTmpState = new ExpandableViewState();
    public final ArrayList mNewEvents = new ArrayList();
    public final ArrayList mNewAddChildren = new ArrayList();
    public final HashSet mHeadsUpAppearChildren = new HashSet();
    public final HashSet mHeadsUpDisappearChildren = new HashSet();
    public final HashSet mAnimatorSet = new HashSet();
    public final Stack mAnimationListenerPool = new Stack();
    public final Stack mAnimationEndPool = new Stack();
    public final AnimationFilter mAnimationFilter = new AnimationFilter();
    public final ArrayList mTransientViewsToRemove = new ArrayList();

    /* renamed from: com.android.systemui.statusbar.notification.stack.StackStateAnimator$1, reason: invalid class name */
    public class AnonymousClass1 extends AnimationProperties {
        public final StackStateAnimator$1$$ExternalSyntheticLambda0 mDynamicAnimationConsumer;

        public AnonymousClass1() {
            HashSet hashSet = StackStateAnimator.this.mAnimatorSet;
            Objects.requireNonNull(hashSet);
            this.mDynamicAnimationConsumer = new StackStateAnimator$1$$ExternalSyntheticLambda0(hashSet);
        }

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final DynamicAnimation.OnAnimationEndListener getAnimationEndListener(Property property) {
            final StackStateAnimator stackStateAnimator = StackStateAnimator.this;
            return !stackStateAnimator.mAnimationEndPool.empty() ? (DynamicAnimation.OnAnimationEndListener) stackStateAnimator.mAnimationEndPool.pop() : new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.3
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                    StackStateAnimator.this.mAnimatorSet.remove(dynamicAnimation);
                    if (StackStateAnimator.this.mAnimatorSet.isEmpty()) {
                        StackStateAnimator.this.onAnimationFinished();
                    }
                    StackStateAnimator.this.mAnimationEndPool.push(this);
                }
            };
        }

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final AnimationFilter getAnimationFilter() {
            return StackStateAnimator.this.mAnimationFilter;
        }

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final AnimatorListenerAdapter getAnimationFinishListener(Property property) {
            return StackStateAnimator.this.getGlobalAnimationFinishedListener();
        }

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final Consumer getAnimationStartListener() {
            return this.mDynamicAnimationConsumer;
        }

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final boolean wasAdded(View view) {
            return StackStateAnimator.this.mNewAddChildren.contains(view);
        }
    }

    public StackStateAnimator(Context context, NotificationStackScrollLayout notificationStackScrollLayout, HeadsUpAnimator headsUpAnimator) {
        this.mHostLayout = notificationStackScrollLayout;
        initView(context);
        this.mAnimationProperties = new AnonymousClass1();
    }

    public final AnimatorListenerAdapter getGlobalAnimationFinishedListener() {
        return !this.mAnimationListenerPool.empty() ? (AnimatorListenerAdapter) this.mAnimationListenerPool.pop() : new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.2
            public boolean mWasCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                Log.d("StackScroller", "getGlobalAnimationFinishedListener onAnimationCancel");
                this.mWasCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                StackStateAnimator.this.mAnimatorSet.remove(animator);
                if (StackStateAnimator.this.mAnimatorSet.isEmpty() && !this.mWasCancelled) {
                    Log.d("StackScroller", "getGlobalAnimationFinishedListener animation end");
                    StackStateAnimator.this.onAnimationFinished();
                }
                StackStateAnimator.this.mAnimationListenerPool.push(this);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                this.mWasCancelled = false;
                StackStateAnimator.this.mAnimatorSet.add(animator);
            }
        };
    }

    public final void initView(Context context) {
        this.mGoToFullShadeAppearingTranslation = context.getResources().getDimensionPixelSize(R.dimen.go_to_full_shade_appearing_translation);
        this.mHeadsUpAppearStartAboveScreen = context.getResources().getDimensionPixelSize(R.dimen.heads_up_appear_y_above_screen);
        this.mHeadsUpCyclingPadding = context.getResources().getDimensionPixelSize(R.dimen.heads_up_cycling_padding);
    }

    public final void onAnimationFinished() {
        this.mHostLayout.onChildAnimationFinished();
        ArrayList arrayList = this.mTransientViewsToRemove;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((ExpandableView) obj).removeFromTransientContainer();
        }
        this.mTransientViewsToRemove.clear();
    }
}
