package com.android.wm.shell.bubbles;

import android.graphics.PointF;
import android.widget.FrameLayout;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.taskview.TaskView;
import java.util.Collections;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda5(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        TaskView taskView;
        BubbleExpandedView expandedView;
        int i = 1;
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                ((BubbleStackView) obj).animateFlyoutCollapsed(0.0f, true);
                break;
            case 1:
                BubbleStackView bubbleStackView = (BubbleStackView) obj;
                if (bubbleStackView.mTemporarilyInvisible && bubbleStackView.mFlyout.getVisibility() != 0) {
                    if (!bubbleStackView.mStackAnimationController.isStackOnLeftSide()) {
                        BubblePositioner bubblePositioner = bubbleStackView.mPositioner;
                        bubbleStackView.mBubbleContainer.animate().translationX((bubbleStackView.getWidth() - ((int) bubbleStackView.mStackAnimationController.mStackPosition.x)) + (bubbleStackView.mBubbleSize - (bubblePositioner.mPositionRect.right - bubblePositioner.mScreenRect.right))).start();
                        break;
                    } else {
                        BubblePositioner bubblePositioner2 = bubbleStackView.mPositioner;
                        bubbleStackView.mBubbleContainer.animate().translationX((-(bubbleStackView.mBubbleSize + (bubblePositioner2.mPositionRect.left - bubblePositioner2.mScreenRect.left))) - ((int) bubbleStackView.mStackAnimationController.mStackPosition.x)).start();
                        break;
                    }
                } else {
                    bubbleStackView.mBubbleContainer.animate().translationX(0.0f).start();
                    break;
                }
                break;
            case 2:
                BubbleStackView bubbleStackView2 = (BubbleStackView) obj;
                if (bubbleStackView2.mFlyout.getVisibility() != 0 && !bubbleStackView2.mIsDraggingStack && !bubbleStackView2.mIsExpansionAnimating && !bubbleStackView2.mIsExpanded && !bubbleStackView2.isStackEduVisible()) {
                    float f = bubbleStackView2.mBubbleSize;
                    float f2 = f - (0.55f * f);
                    if (!bubbleStackView2.mStackAnimationController.isStackOnLeftSide()) {
                        BubblePositioner bubblePositioner3 = bubbleStackView2.mPositioner;
                        bubbleStackView2.mBubbleContainer.animate().translationX(f2 - (bubblePositioner3.mPositionRect.right - bubblePositioner3.mScreenRect.right)).start();
                        break;
                    } else {
                        BubblePositioner bubblePositioner4 = bubbleStackView2.mPositioner;
                        bubbleStackView2.mBubbleContainer.animate().translationX(-(f2 + (bubblePositioner4.mPositionRect.left - bubblePositioner4.mScreenRect.left))).start();
                        break;
                    }
                }
                break;
            case 3:
                BubbleStackView bubbleStackView3 = (BubbleStackView) obj;
                PhysicsAnimator.SpringConfig springConfig2 = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                if (bubbleStackView3.getBubbleCount() == 0) {
                    bubbleStackView3.mExpandedViewTemporarilyHidden = false;
                    BubbleController bubbleController = ((BubbleStackViewManager$Companion$fromBubbleController$1) bubbleStackView3.mManager).$controller;
                    BubbleStackView bubbleStackView4 = bubbleController.mStackView;
                    if (bubbleStackView4 != null) {
                        bubbleStackView4.setVisibility(4);
                        if (bubbleController.mAddedToWindowManager) {
                            bubbleController.mAddedToWindowManager = false;
                            bubbleController.mBackgroundExecutor.execute(new BubbleController$$ExternalSyntheticLambda1(bubbleController, i));
                            try {
                                BubbleStackView bubbleStackView5 = bubbleController.mStackView;
                                BubbleData bubbleData = bubbleController.mBubbleData;
                                if (bubbleStackView5 != null) {
                                    bubbleController.mWindowManager.removeView(bubbleStackView5);
                                    BubbleOverflow bubbleOverflow = bubbleData.mOverflow;
                                    BubbleExpandedView bubbleExpandedView = bubbleOverflow.expandedView;
                                    if (bubbleExpandedView != null && (taskView = bubbleExpandedView.mTaskView) != null) {
                                        taskView.setVisibility(8);
                                    }
                                    bubbleOverflow.expandedView = null;
                                    BubbleBarExpandedView bubbleBarExpandedView = bubbleOverflow.bubbleBarExpandedView;
                                    if (bubbleBarExpandedView != null) {
                                        bubbleBarExpandedView.mMenuViewController.hideMenu(false);
                                    }
                                    bubbleOverflow.bubbleBarExpandedView = null;
                                    break;
                                }
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                    }
                }
                break;
            case 4:
                PhysicsAnimator.SpringConfig springConfig3 = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                ((BubbleStackView) obj).animateShadows();
                break;
            case 5:
                BubbleStackView bubbleStackView6 = (BubbleStackView) obj;
                PhysicsAnimator.SpringConfig springConfig4 = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                bubbleStackView6.mIsExpansionAnimating = false;
                bubbleStackView6.updateExpandedView();
                bubbleStackView6.requestUpdate();
                bubbleStackView6.showManageMenu(false);
                break;
            case 6:
                BubbleStackView bubbleStackView7 = (BubbleStackView) obj;
                PhysicsAnimator.SpringConfig springConfig5 = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                BubbleStackView$$ExternalSyntheticLambda5 bubbleStackView$$ExternalSyntheticLambda5 = new BubbleStackView$$ExternalSyntheticLambda5(bubbleStackView7, 7);
                bubbleStackView7.mAnimateInFlyout = bubbleStackView$$ExternalSyntheticLambda5;
                bubbleStackView7.mFlyout.postDelayed(bubbleStackView$$ExternalSyntheticLambda5, 200L);
                break;
            case 7:
                BubbleStackView bubbleStackView8 = (BubbleStackView) obj;
                bubbleStackView8.mFlyout.setVisibility(0);
                bubbleStackView8.updateTemporarilyInvisibleAnimation(false);
                bubbleStackView8.mFlyoutDragDeltaX = bubbleStackView8.mStackAnimationController.isStackOnLeftSide() ? -bubbleStackView8.mFlyout.getWidth() : bubbleStackView8.mFlyout.getWidth();
                bubbleStackView8.animateFlyoutCollapsed(0.0f, false);
                bubbleStackView8.mFlyout.postDelayed(bubbleStackView8.mHideFlyout, 5000L);
                break;
            case 8:
                PhysicsAnimator.SpringConfig springConfig6 = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                ((BubbleStackView) obj).showManageMenu(true);
                break;
            case 9:
                BubbleStackView bubbleStackView9 = (BubbleStackView) obj;
                PhysicsAnimator.SpringConfig springConfig7 = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                bubbleStackView9.getClass();
                bubbleStackView9.post(new BubbleStackView$$ExternalSyntheticLambda5(bubbleStackView9, 14));
                break;
            case 10:
                BubbleStackView bubbleStackView10 = (BubbleStackView) obj;
                BubbleViewProvider bubbleViewProvider = bubbleStackView10.mExpandedBubble;
                bubbleStackView10.mIsExpansionAnimating = true;
                bubbleStackView10.hideFlyoutImmediate();
                bubbleStackView10.updateExpandedBubble();
                bubbleStackView10.updateExpandedView();
                ManageEducationView manageEducationView = bubbleStackView10.mManageEduView;
                if (manageEducationView != null) {
                    manageEducationView.hide();
                }
                bubbleStackView10.updateBadges(true);
                bubbleStackView10.mIsExpansionAnimating = false;
                bubbleStackView10.updateExpandedView();
                bubbleStackView10.requestUpdate();
                if (bubbleViewProvider != null) {
                    bubbleViewProvider.setTaskViewVisibility();
                }
                bubbleStackView10.mExpandedViewAnimationController.reset();
                break;
            case 11:
                BubbleStackView bubbleStackView11 = (BubbleStackView) obj;
                boolean z = bubbleStackView11.mIsExpanded;
                bubbleStackView11.updateOverflowDotVisibility(true);
                break;
            case 12:
                BubbleStackView bubbleStackView12 = (BubbleStackView) obj;
                bubbleStackView12.mExpandedViewContainer.setAnimationMatrix(null);
                bubbleStackView12.mIsExpansionAnimating = false;
                bubbleStackView12.updateExpandedView();
                bubbleStackView12.requestUpdate();
                BubbleExpandedView expandedView2 = bubbleStackView12.getExpandedView();
                if (expandedView2 != null) {
                    expandedView2.setSurfaceZOrderedOnTop(false);
                    break;
                }
                break;
            case 13:
                BubbleStackView bubbleStackView13 = (BubbleStackView) obj;
                bubbleStackView13.mBubbleContainer.setActiveController(bubbleStackView13.mStackAnimationController);
                bubbleStackView13.updateOverflowVisibility();
                bubbleStackView13.animateShadows();
                break;
            case 14:
                BubbleStackView bubbleStackView14 = (BubbleStackView) obj;
                if (!bubbleStackView14.mIsExpanded) {
                    bubbleStackView14.mIsBubbleSwitchAnimating = false;
                    break;
                } else {
                    FrameLayout frameLayout = bubbleStackView14.mAnimatingOutSurfaceContainer;
                    PhysicsAnimator.Companion.getClass();
                    PhysicsAnimator.Companion.getInstance(frameLayout).cancel();
                    bubbleStackView14.mAnimatingOutSurfaceAlphaAnimator.reverse();
                    bubbleStackView14.mExpandedViewAlphaAnimator.start();
                    BubbleViewProvider bubbleViewProvider2 = bubbleStackView14.mExpandedBubble;
                    if (bubbleViewProvider2 != null && ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -2359996827066879853L, 0, String.valueOf(bubbleViewProvider2.getKey()));
                    }
                    BubbleViewProvider bubbleViewProvider3 = bubbleStackView14.mExpandedBubble;
                    PointF expandedBubbleXY = bubbleStackView14.mPositioner.getExpandedBubbleXY(bubbleViewProvider3 != null && bubbleViewProvider3.getKey().equals("Overflow") ? bubbleStackView14.mBubbleContainer.getChildCount() - 1 : Collections.unmodifiableList(bubbleStackView14.mBubbleData.mBubbles).indexOf(bubbleStackView14.mExpandedBubble), bubbleStackView14.getState());
                    bubbleStackView14.mExpandedViewContainer.setAlpha(1.0f);
                    bubbleStackView14.mExpandedViewContainer.setVisibility(0);
                    if (bubbleStackView14.mPositioner.showBubblesVertically()) {
                        float f3 = expandedBubbleXY.y;
                        float f4 = bubbleStackView14.mBubbleSize;
                        bubbleStackView14.mExpandedViewContainerMatrix.setScale(0.9f, 0.9f, bubbleStackView14.mStackOnLeftOrWillBe ? expandedBubbleXY.x + f4 + bubbleStackView14.mExpandedViewPadding : expandedBubbleXY.x - bubbleStackView14.mExpandedViewPadding, (f4 / 2.0f) + f3);
                    } else {
                        AnimatableScaleMatrix animatableScaleMatrix = bubbleStackView14.mExpandedViewContainerMatrix;
                        float f5 = expandedBubbleXY.x;
                        float f6 = bubbleStackView14.mBubbleSize;
                        animatableScaleMatrix.setScale(0.9f, 0.9f, (f6 / 2.0f) + f5, expandedBubbleXY.y + f6 + bubbleStackView14.mExpandedViewPadding);
                    }
                    bubbleStackView14.mExpandedViewContainer.setAnimationMatrix(bubbleStackView14.mExpandedViewContainerMatrix);
                    ((HandlerExecutor) bubbleStackView14.mMainExecutor).executeDelayed(new BubbleStackView$$ExternalSyntheticLambda5(bubbleStackView14, 15), 25L);
                    break;
                }
                break;
            case 15:
                BubbleStackView bubbleStackView15 = (BubbleStackView) obj;
                if (!bubbleStackView15.mIsExpanded) {
                    bubbleStackView15.mIsBubbleSwitchAnimating = false;
                    break;
                } else {
                    AnimatableScaleMatrix animatableScaleMatrix2 = bubbleStackView15.mExpandedViewContainerMatrix;
                    PhysicsAnimator.Companion.getClass();
                    PhysicsAnimator.Companion.getInstance(animatableScaleMatrix2).cancel();
                    PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(bubbleStackView15.mExpandedViewContainerMatrix);
                    companion.spring(AnimatableScaleMatrix.SCALE_X, 499.99997f, 0.0f, bubbleStackView15.mScaleInSpringConfig);
                    companion.spring(AnimatableScaleMatrix.SCALE_Y, 499.99997f, 0.0f, bubbleStackView15.mScaleInSpringConfig);
                    companion.updateListeners.add(new BubbleStackView$$ExternalSyntheticLambda23(bubbleStackView15, 2));
                    companion.withEndActions(new BubbleStackView$$ExternalSyntheticLambda5(bubbleStackView15, 16));
                    companion.start();
                    break;
                }
            case 16:
                BubbleStackView bubbleStackView16 = (BubbleStackView) obj;
                bubbleStackView16.mExpandedViewTemporarilyHidden = false;
                bubbleStackView16.mIsBubbleSwitchAnimating = false;
                bubbleStackView16.mExpandedViewContainer.setAnimationMatrix(null);
                if (bubbleStackView16.mExpandedBubble != null && (expandedView = bubbleStackView16.getExpandedView()) != null) {
                    expandedView.setSurfaceZOrderedOnTop(false);
                    expandedView.setAnimating(false);
                    break;
                }
                break;
            default:
                BubbleStackView bubbleStackView17 = BubbleStackView.this;
                bubbleStackView17.mBubbleData.dismissAll(1);
                bubbleStackView17.resetDismissAnimator();
                bubbleStackView17.mBubbleSALogger.sendEventCDLog("QPNE0101", "type", "group");
                break;
        }
    }
}
