package com.android.p038wm.shell.bubbles;

import android.graphics.PointF;
import com.android.p038wm.shell.animation.PhysicsAnimator;
import com.android.p038wm.shell.bubbles.BubbleStackView;
import com.android.p038wm.shell.bubbles.animation.AnimatableScaleMatrix;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.taskview.TaskView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        TaskView taskView;
        switch (this.$r8$classId) {
            case 0:
                ((BubbleStackView) this.f$0).animateFlyoutCollapsed(0.0f, true);
                break;
            case 1:
                BubbleStackView bubbleStackView = (BubbleStackView) this.f$0;
                if (bubbleStackView.mTemporarilyInvisible && bubbleStackView.mFlyout.getVisibility() != 0) {
                    if (!bubbleStackView.mStackAnimationController.isStackOnLeftSide()) {
                        BubblePositioner bubblePositioner = bubbleStackView.mPositioner;
                        bubbleStackView.animate().translationX((bubbleStackView.getWidth() - ((int) bubbleStackView.mStackAnimationController.mStackPosition.x)) + (bubbleStackView.mBubbleSize - (bubblePositioner.mPositionRect.right - bubblePositioner.mScreenRect.right))).start();
                        break;
                    } else {
                        BubblePositioner bubblePositioner2 = bubbleStackView.mPositioner;
                        bubbleStackView.animate().translationX((-(bubbleStackView.mBubbleSize + (bubblePositioner2.mPositionRect.left - bubblePositioner2.mScreenRect.left))) - ((int) bubbleStackView.mStackAnimationController.mStackPosition.x)).start();
                        break;
                    }
                } else {
                    bubbleStackView.animate().translationX(0.0f).start();
                    break;
                }
            case 2:
                BubbleStackView bubbleStackView2 = (BubbleStackView) this.f$0;
                if (bubbleStackView2.getBubbleCount() == 0) {
                    BubbleData bubbleData = bubbleStackView2.mBubbleData;
                    if (!(bubbleData.mShowingOverflow && bubbleData.mExpanded)) {
                        bubbleStackView2.mExpandedViewTemporarilyHidden = false;
                        bubbleStackView2.mBubbleController.onAllBubblesAnimatedOut();
                        break;
                    }
                }
                break;
            case 3:
                BubbleStackView bubbleStackView3 = (BubbleStackView) this.f$0;
                int bubbleCount = bubbleStackView3.getBubbleCount();
                int i = 0;
                while (i < bubbleCount) {
                    BadgedImageView badgedImageView = (BadgedImageView) bubbleStackView3.mBubbleContainer.getChildAt(i);
                    if (!(i < 2)) {
                        badgedImageView.animate().translationZ(0.0f).start();
                    }
                    i++;
                }
                break;
            case 4:
                BubbleStackView bubbleStackView4 = (BubbleStackView) this.f$0;
                bubbleStackView4.getClass();
                BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda3 = new BubbleStackView$$ExternalSyntheticLambda3(bubbleStackView4, 6);
                bubbleStackView4.mAnimateInFlyout = bubbleStackView$$ExternalSyntheticLambda3;
                bubbleStackView4.mFlyout.postDelayed(bubbleStackView$$ExternalSyntheticLambda3, 200L);
                break;
            case 5:
                BubbleStackView bubbleStackView5 = (BubbleStackView) this.f$0;
                bubbleStackView5.mIsExpansionAnimating = false;
                bubbleStackView5.updateExpandedView();
                bubbleStackView5.requestUpdate();
                bubbleStackView5.showManageMenu(false);
                break;
            case 6:
                BubbleStackView bubbleStackView6 = (BubbleStackView) this.f$0;
                bubbleStackView6.mFlyout.setVisibility(0);
                bubbleStackView6.updateTemporarilyInvisibleAnimation(false);
                bubbleStackView6.mFlyoutDragDeltaX = bubbleStackView6.mStackAnimationController.isStackOnLeftSide() ? -bubbleStackView6.mFlyout.getWidth() : bubbleStackView6.mFlyout.getWidth();
                bubbleStackView6.animateFlyoutCollapsed(0.0f, false);
                bubbleStackView6.mFlyout.postDelayed(bubbleStackView6.mHideFlyout, 5000L);
                break;
            case 7:
                BubbleStackView bubbleStackView7 = (BubbleStackView) this.f$0;
                bubbleStackView7.getClass();
                bubbleStackView7.post(new BubbleStackView$$ExternalSyntheticLambda3(bubbleStackView7, 11));
                break;
            case 8:
                BubbleStackView bubbleStackView8 = (BubbleStackView) this.f$0;
                bubbleStackView8.mBubbleContainer.setActiveController(bubbleStackView8.mStackAnimationController);
                break;
            case 9:
                BubbleStackView bubbleStackView9 = (BubbleStackView) this.f$0;
                BubbleViewProvider bubbleViewProvider = bubbleStackView9.mExpandedBubble;
                bubbleStackView9.mIsExpansionAnimating = true;
                bubbleStackView9.hideFlyoutImmediate();
                bubbleStackView9.updateExpandedBubble();
                bubbleStackView9.updateExpandedView();
                ManageEducationView manageEducationView = bubbleStackView9.mManageEduView;
                if (manageEducationView != null) {
                    manageEducationView.hide();
                }
                bubbleStackView9.updateOverflowVisibility();
                bubbleStackView9.updateZOrder();
                bubbleStackView9.updateBadges(true);
                bubbleStackView9.mIsExpansionAnimating = false;
                bubbleStackView9.updateExpandedView();
                bubbleStackView9.requestUpdate();
                if (bubbleViewProvider != null) {
                    bubbleViewProvider.setTaskViewVisibility();
                    break;
                }
                break;
            case 10:
                BubbleStackView bubbleStackView10 = (BubbleStackView) this.f$0;
                if (bubbleStackView10.mIsExpanded) {
                    bubbleStackView10.mExpandedBubble.getExpandedView();
                    break;
                }
                break;
            case 11:
                BubbleStackView bubbleStackView11 = (BubbleStackView) this.f$0;
                if (!bubbleStackView11.mIsExpanded) {
                    bubbleStackView11.mIsBubbleSwitchAnimating = false;
                    break;
                } else {
                    PhysicsAnimator.getInstance(bubbleStackView11.mAnimatingOutSurfaceContainer).cancel();
                    bubbleStackView11.mAnimatingOutSurfaceAlphaAnimator.reverse();
                    bubbleStackView11.mExpandedViewAlphaAnimator.start();
                    BubbleViewProvider bubbleViewProvider2 = bubbleStackView11.mExpandedBubble;
                    PointF expandedBubbleXY = bubbleStackView11.mPositioner.getExpandedBubbleXY(bubbleViewProvider2 != null && bubbleViewProvider2.getKey().equals("Overflow") ? bubbleStackView11.mBubbleContainer.getChildCount() - 1 : bubbleStackView11.mBubbleData.getBubbles().indexOf(bubbleStackView11.mExpandedBubble), bubbleStackView11.getState());
                    bubbleStackView11.mExpandedViewContainer.setAlpha(1.0f);
                    bubbleStackView11.mExpandedViewContainer.setVisibility(0);
                    if (bubbleStackView11.mPositioner.showBubblesVertically()) {
                        float f = expandedBubbleXY.y;
                        float f2 = bubbleStackView11.mBubbleSize;
                        bubbleStackView11.mExpandedViewContainerMatrix.setScale(0.9f, 0.9f, bubbleStackView11.mStackOnLeftOrWillBe ? expandedBubbleXY.x + f2 + bubbleStackView11.mExpandedViewPadding : expandedBubbleXY.x - bubbleStackView11.mExpandedViewPadding, (f2 / 2.0f) + f);
                    } else {
                        AnimatableScaleMatrix animatableScaleMatrix = bubbleStackView11.mExpandedViewContainerMatrix;
                        float f3 = expandedBubbleXY.x;
                        float f4 = bubbleStackView11.mBubbleSize;
                        animatableScaleMatrix.setScale(0.9f, 0.9f, (f4 / 2.0f) + f3, expandedBubbleXY.y + f4 + bubbleStackView11.mExpandedViewPadding);
                    }
                    bubbleStackView11.mExpandedViewContainer.setAnimationMatrix(bubbleStackView11.mExpandedViewContainerMatrix);
                    ((HandlerExecutor) bubbleStackView11.mMainExecutor).executeDelayed(25L, new BubbleStackView$$ExternalSyntheticLambda3(bubbleStackView11, 13));
                    break;
                }
                break;
            case 12:
                BubbleStackView bubbleStackView12 = (BubbleStackView) this.f$0;
                bubbleStackView12.mExpandedViewContainer.setAnimationMatrix(null);
                bubbleStackView12.mIsExpansionAnimating = false;
                bubbleStackView12.updateExpandedView();
                bubbleStackView12.requestUpdate();
                BubbleViewProvider bubbleViewProvider3 = bubbleStackView12.mExpandedBubble;
                if (bubbleViewProvider3 != null && bubbleViewProvider3.getExpandedView() != null && (taskView = bubbleStackView12.mExpandedBubble.getExpandedView().mTaskView) != null) {
                    taskView.setZOrderedOnTop(true, true);
                    break;
                }
                break;
            case 13:
                BubbleStackView bubbleStackView13 = (BubbleStackView) this.f$0;
                if (!bubbleStackView13.mIsExpanded) {
                    bubbleStackView13.mIsBubbleSwitchAnimating = false;
                    break;
                } else {
                    PhysicsAnimator.getInstance(bubbleStackView13.mExpandedViewContainerMatrix).cancel();
                    PhysicsAnimator physicsAnimator = PhysicsAnimator.getInstance(bubbleStackView13.mExpandedViewContainerMatrix);
                    physicsAnimator.spring(AnimatableScaleMatrix.SCALE_X, 499.99997f, 0.0f, bubbleStackView13.mScaleInSpringConfig);
                    physicsAnimator.spring(AnimatableScaleMatrix.SCALE_Y, 499.99997f, 0.0f, bubbleStackView13.mScaleInSpringConfig);
                    physicsAnimator.updateListeners.add(new BubbleStackView$$ExternalSyntheticLambda11(bubbleStackView13, 3));
                    physicsAnimator.withEndActions(new BubbleStackView$$ExternalSyntheticLambda3(bubbleStackView13, 14));
                    physicsAnimator.start();
                    break;
                }
            case 14:
                BubbleStackView bubbleStackView14 = (BubbleStackView) this.f$0;
                bubbleStackView14.mExpandedViewTemporarilyHidden = false;
                bubbleStackView14.mIsBubbleSwitchAnimating = false;
                bubbleStackView14.mExpandedViewContainer.setAnimationMatrix(null);
                break;
            case 15:
                BubbleStackView.m2737$$Nest$mdismissMagnetizedObject((BubbleStackView) this.f$0);
                break;
            default:
                BubbleStackView.C38205 c38205 = (BubbleStackView.C38205) this.f$0;
                c38205.getClass();
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                BubbleStackView bubbleStackView15 = BubbleStackView.this;
                bubbleStackView15.resetDismissAnimator();
                BubbleStackView.m2737$$Nest$mdismissMagnetizedObject(bubbleStackView15);
                break;
        }
    }
}
