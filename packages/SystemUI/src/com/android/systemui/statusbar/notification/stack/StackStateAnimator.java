package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.util.Property;
import android.view.View;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class StackStateAnimator {
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
    public NotificationShelf mShelf;
    public int mStackTopMargin;
    public ValueAnimator mTopOverScrollAnimator;
    public final ExpandableViewState mTmpState = new ExpandableViewState();
    public final ArrayList mNewEvents = new ArrayList();
    public final ArrayList mNewAddChildren = new ArrayList();
    public final HashSet mHeadsUpAppearChildren = new HashSet();
    public final HashSet mHeadsUpDisappearChildren = new HashSet();
    public final HashSet mAnimatorSet = new HashSet();
    public final Stack mAnimationListenerPool = new Stack();
    public final AnimationFilter mAnimationFilter = new AnimationFilter();
    public final ArrayList mTransientViewsToRemove = new ArrayList();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.stack.StackStateAnimator$1, reason: invalid class name */
    public final class AnonymousClass1 extends AnimationProperties {
        public AnonymousClass1() {
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
        public final boolean wasAdded(View view) {
            return StackStateAnimator.this.mNewAddChildren.contains(view);
        }
    }

    public StackStateAnimator(Context context, NotificationStackScrollLayout notificationStackScrollLayout) {
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

    public final boolean isRunning() {
        return !this.mAnimatorSet.isEmpty();
    }

    public final void onAnimationFinished() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mHostLayout;
        notificationStackScrollLayout.setAnimationRunning(false);
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        Flags.notificationsHeadsUpRefactor();
        notificationStackScrollLayout.requestChildrenUpdate();
        notificationStackScrollLayout.runAnimationFinishedRunnables();
        Iterator it = notificationStackScrollLayout.mClearTransientViewsWhenFinished.iterator();
        while (it.hasNext()) {
            ((ExpandableView) it.next()).removeFromTransientContainer();
        }
        notificationStackScrollLayout.mClearTransientViewsWhenFinished.clear();
        notificationStackScrollLayout.clearHeadsUpDisappearRunning();
        Log.d("StackScroller", "onChildAnimationFinished clearTemporaryViews");
        notificationStackScrollLayout.finalizeClearAllAnimation();
        Iterator it2 = this.mTransientViewsToRemove.iterator();
        while (it2.hasNext()) {
            ((ExpandableView) it2.next()).removeFromTransientContainer();
        }
        this.mTransientViewsToRemove.clear();
    }
}
