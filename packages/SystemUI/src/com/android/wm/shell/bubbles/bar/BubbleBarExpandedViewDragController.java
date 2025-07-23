package com.android.wm.shell.bubbles.bar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcelable;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.core.animation.Animator;
import androidx.core.animation.ValueAnimator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.BubbleExpandedViewManager$Companion$fromBubbleController$1;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.bubbles.bar.BubbleBarAnimationHelper;
import com.android.wm.shell.bubbles.bar.BubbleBarLayerView;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.bubbles.BaseBubblePinController$hideDropTarget$1$1;
import com.android.wm.shell.shared.bubbles.BubbleBarLocation;
import com.android.wm.shell.shared.bubbles.DeviceConfig;
import com.android.wm.shell.shared.bubbles.DismissCircleView;
import com.android.wm.shell.shared.bubbles.DismissView;
import com.android.wm.shell.shared.bubbles.DragZone;
import com.android.wm.shell.shared.bubbles.DragZoneFactory;
import com.android.wm.shell.shared.bubbles.DraggedObject;
import com.android.wm.shell.shared.bubbles.DropTargetManager;
import com.android.wm.shell.shared.bubbles.DropTargetView;
import com.android.wm.shell.shared.bubbles.RelativeTouchListener;
import com.android.wm.shell.shared.magnetictarget.MagnetizedObject;
import com.android.wm.shell.shared.magnetictarget.MagnetizedObject$Companion$magnetizeView$1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BubbleBarExpandedViewDragController {
    public final BubbleBarAnimationHelper animationHelper;
    public final BubblePositioner bubblePositioner;
    public final DismissView dismissView;
    public final DragListener dragListener;
    public final DragZoneFactory dragZoneFactory;
    public final float draggedBubbleElevation;
    public final DropTargetManager dropTargetManager;
    public final BubbleBarExpandedView expandedView;
    public float expandedViewInitialTranslationX;
    public float expandedViewInitialTranslationY;
    public boolean isStuckToDismiss;
    public final MagnetizedObject$Companion$magnetizeView$1 magnetizedExpandedView;
    public final BubbleExpandedViewPinController pinController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DragListener {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HandleDragListener extends RelativeTouchListener {
        public boolean isMoving;

        public HandleDragListener() {
        }

        public final void finishDrag() {
            BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController = BubbleBarExpandedViewDragController.this;
            if (!bubbleBarExpandedViewDragController.isStuckToDismiss) {
                DropTargetManager dropTargetManager = bubbleBarExpandedViewDragController.dropTargetManager;
                if (dropTargetManager != null) {
                    dropTargetManager.onDragEnded();
                } else {
                    BubbleExpandedViewPinController bubbleExpandedViewPinController = bubbleBarExpandedViewDragController.pinController;
                    View dropTargetView = bubbleExpandedViewPinController.getDropTargetView();
                    if (dropTargetView != null) {
                        bubbleExpandedViewPinController.animateOut(dropTargetView, new BaseBubblePinController$hideDropTarget$1$1(bubbleExpandedViewPinController, dropTargetView));
                    }
                    bubbleExpandedViewPinController.dismissZone = null;
                    BubbleBarLayerView.LocationChangeListener locationChangeListener = bubbleExpandedViewPinController.listener;
                    if (locationChangeListener != null) {
                        locationChangeListener.onRelease(bubbleExpandedViewPinController.onLeft ? BubbleBarLocation.LEFT : BubbleBarLocation.RIGHT);
                    }
                }
                ((BubbleBarLayerView$$ExternalSyntheticLambda0) BubbleBarExpandedViewDragController.this.getDragListener()).onReleased(false);
                final BubbleBarAnimationHelper bubbleBarAnimationHelper = BubbleBarExpandedViewDragController.this.animationHelper;
                final BubbleBarExpandedView expandedView = bubbleBarAnimationHelper.getExpandedView();
                if (expandedView == null) {
                    Log.w("BubbleBarAnimationHelper", "Trying to animate expanded view to rest position without a bubble");
                } else {
                    Point expandedViewRestPosition = bubbleBarAnimationHelper.getExpandedViewRestPosition(bubbleBarAnimationHelper.getExpandedViewSize());
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) View.X, expandedViewRestPosition.x), ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) View.Y, expandedViewRestPosition.y), ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) View.SCALE_X, 1.0f), ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) View.SCALE_Y, 1.0f), ObjectAnimator.ofFloat(expandedView, BubbleBarExpandedView.CORNER_RADIUS, expandedView.mRestingCornerRadius));
                    animatorSet.setDuration(400L).setInterpolator(Interpolators.EMPHASIZED);
                    ObjectAnimator duration = ObjectAnimator.ofFloat(expandedView.mHandleView, (Property<BubbleBarHandleView, Float>) View.ALPHA, 1.0f).setDuration(100L);
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    animatorSet2.playTogether(animatorSet, duration);
                    animatorSet2.addListener(new BubbleBarAnimationHelper.DragAnimatorListenerAdapter(expandedView) { // from class: com.android.wm.shell.bubbles.bar.BubbleBarAnimationHelper.8
                        public final /* synthetic */ BubbleBarExpandedView val$bbev;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass8(final BubbleBarExpandedView expandedView2, final BubbleBarExpandedView expandedView22) {
                            super(expandedView22);
                            r3 = expandedView22;
                        }

                        @Override // com.android.wm.shell.bubbles.bar.BubbleBarAnimationHelper.DragAnimatorListenerAdapter, android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            r3.resetPivot();
                            BubbleBarExpandedView bubbleBarExpandedView = r3;
                            if (bubbleBarExpandedView.mIsDragging) {
                                bubbleBarExpandedView.mIsDragging = false;
                                bubbleBarExpandedView.updateSamplingState();
                            }
                            BubbleBarAnimationHelper.this.updateExpandedView(r3);
                        }
                    });
                    bubbleBarAnimationHelper.startNewAnimator(animatorSet2);
                }
                BubbleBarExpandedViewDragController.this.dismissView.hide();
            }
            this.isMoving = false;
            BubbleBarExpandedViewDragController.this.getClass();
        }

        @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
        public final void onCancel(View view) {
            BubbleBarExpandedViewDragController.this.isStuckToDismiss = false;
            view.setTranslationZ(0.0f);
            finishDrag();
        }

        @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
        public final boolean onDown(View view, MotionEvent motionEvent) {
            List asList;
            List createHorizontalSplitDragZonesForExpandedView;
            BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController = BubbleBarExpandedViewDragController.this;
            BubbleBarExpandedView bubbleBarExpandedView = bubbleBarExpandedViewDragController.expandedView;
            if (bubbleBarExpandedView.mIsAnimating) {
                return false;
            }
            bubbleBarExpandedView.setZ(bubbleBarExpandedViewDragController.draggedBubbleElevation);
            BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController2 = BubbleBarExpandedViewDragController.this;
            DropTargetManager dropTargetManager = bubbleBarExpandedViewDragController2.dropTargetManager;
            BubblePositioner bubblePositioner = bubbleBarExpandedViewDragController2.bubblePositioner;
            if (dropTargetManager == null || bubbleBarExpandedViewDragController2.dragZoneFactory == null) {
                boolean isBubbleBarOnLeft = bubblePositioner.isBubbleBarOnLeft();
                BubbleExpandedViewPinController bubbleExpandedViewPinController = bubbleBarExpandedViewDragController2.pinController;
                bubbleExpandedViewPinController.initialLocationOnLeft = isBubbleBarOnLeft;
                bubbleExpandedViewPinController.onLeft = isBubbleBarOnLeft;
                bubbleExpandedViewPinController.screenCenterX = ((Point) bubbleExpandedViewPinController.screenSizeProvider.invoke()).x / 2;
                RectF rectF = new RectF(0.0f, 0.0f, ((Number) bubbleExpandedViewPinController.exclRectWidth$delegate.getValue()).floatValue(), ((Number) bubbleExpandedViewPinController.exclRectHeight$delegate.getValue()).floatValue());
                rectF.offsetTo(bubbleExpandedViewPinController.screenCenterX - (rectF.width() / 2), ((Point) r1.invoke()).y - rectF.height());
                bubbleExpandedViewPinController.dismissZone = rectF;
                BubbleBarLayerView.LocationChangeListener locationChangeListener = bubbleExpandedViewPinController.listener;
                if (locationChangeListener != null) {
                    locationChangeListener.mInitialLocation = isBubbleBarOnLeft ? BubbleBarLocation.LEFT : BubbleBarLocation.RIGHT;
                }
            } else {
                DraggedObject.ExpandedView expandedView = new DraggedObject.ExpandedView(bubblePositioner.isBubbleBarOnLeft() ? BubbleBarLocation.LEFT : BubbleBarLocation.RIGHT);
                BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController3 = BubbleBarExpandedViewDragController.this;
                DropTargetManager dropTargetManager2 = bubbleBarExpandedViewDragController3.dropTargetManager;
                DragZoneFactory dragZoneFactory = bubbleBarExpandedViewDragController3.dragZoneFactory;
                dragZoneFactory.getClass();
                ArrayList arrayList = new ArrayList();
                DragZoneFactory.DesktopWindowModeChecker desktopWindowModeChecker = dragZoneFactory.desktopWindowModeChecker;
                DeviceConfig deviceConfig = dragZoneFactory.deviceConfig;
                DragZoneFactory.SplitScreenModeChecker splitScreenModeChecker = dragZoneFactory.splitScreenModeChecker;
                arrayList.add(dragZoneFactory.createDismissDragZone());
                arrayList.add(dragZoneFactory.createFullScreenDragZone());
                if (!deviceConfig.isSmallTablet) {
                    desktopWindowModeChecker.getClass();
                }
                boolean z = deviceConfig.isSmallTablet;
                boolean z2 = deviceConfig.isLandscape;
                if (z) {
                    if (z2) {
                        int i = (deviceConfig.windowBounds.right / 2) - (dragZoneFactory.fullScreenDragZoneWidth / 2);
                        ((BubbleBarLayerView.AnonymousClass2) splitScreenModeChecker).getClass();
                        int i2 = DragZoneFactory.WhenMappings.$EnumSwitchMapping$0[DragZoneFactory.SplitScreenModeChecker.SplitScreenMode.UNSUPPORTED.ordinal()];
                        if (i2 == 1) {
                            createHorizontalSplitDragZonesForExpandedView = EmptyList.INSTANCE;
                        } else if (i2 == 2 || i2 == 3) {
                            int i3 = dragZoneFactory.fullScreenDragZoneHeight;
                            DragZone.Split.Top top = new DragZone.Split.Top(new Rect(i, i3, dragZoneFactory.fullScreenDragZoneWidth + i, dragZoneFactory.vSplitFromExpandedViewDragZoneHeightFoldTall + i3));
                            int i4 = deviceConfig.windowBounds.bottom / 2;
                            createHorizontalSplitDragZonesForExpandedView = Arrays.asList(top, new DragZone.Split.Bottom(new Rect(i, i4, dragZoneFactory.fullScreenDragZoneWidth + i, dragZoneFactory.vSplitFromExpandedViewDragZoneHeightFoldTall + i4)));
                        } else if (i2 == 4) {
                            int i5 = dragZoneFactory.fullScreenDragZoneHeight;
                            DragZone.Split.Top top2 = new DragZone.Split.Top(new Rect(i, i5, dragZoneFactory.fullScreenDragZoneWidth + i, dragZoneFactory.vSplitFromExpandedViewDragZoneHeightFoldTall + i5));
                            Rect rect = deviceConfig.windowBounds;
                            int i6 = rect.bottom;
                            createHorizontalSplitDragZonesForExpandedView = Arrays.asList(top2, new DragZone.Split.Bottom(new Rect(0, i6 - dragZoneFactory.vSplitFromExpandedViewDragZoneHeightFoldShort, rect.right, i6)));
                        } else {
                            if (i2 != 5) {
                                throw new NoWhenBranchMatchedException();
                            }
                            DragZone.Split.Top top3 = new DragZone.Split.Top(new Rect(0, 0, deviceConfig.windowBounds.right, dragZoneFactory.vSplitFromExpandedViewDragZoneHeightFoldShort));
                            int i7 = dragZoneFactory.vSplitFromExpandedViewDragZoneHeightFoldShort;
                            createHorizontalSplitDragZonesForExpandedView = Arrays.asList(top3, new DragZone.Split.Bottom(new Rect(i, i7, dragZoneFactory.fullScreenDragZoneWidth + i, dragZoneFactory.vSplitFromExpandedViewDragZoneHeightFoldTall + i7)));
                        }
                    } else {
                        createHorizontalSplitDragZonesForExpandedView = dragZoneFactory.createHorizontalSplitDragZonesForExpandedView();
                    }
                    arrayList.addAll(createHorizontalSplitDragZonesForExpandedView);
                } else {
                    if (z2) {
                        asList = dragZoneFactory.createHorizontalSplitDragZonesForExpandedView();
                    } else {
                        Rect rect2 = deviceConfig.windowBounds;
                        int i8 = rect2.right / 2;
                        int i9 = dragZoneFactory.vSplitFromExpandedViewDragZoneWidth;
                        int i10 = i8 - (i9 / 2);
                        int i11 = i9 + i10;
                        int i12 = rect2.bottom - dragZoneFactory.dismissDragZoneSize;
                        int i13 = dragZoneFactory.fullScreenDragZoneHeight;
                        asList = Arrays.asList(new DragZone.Split.Top(new Rect(i10, i13, i11, dragZoneFactory.vSplitFromExpandedViewDragZoneHeightTablet + i13)), new DragZone.Split.Bottom(new Rect(i10, i12 - dragZoneFactory.vSplitFromExpandedViewDragZoneHeightTablet, i11, i12)));
                    }
                    arrayList.addAll(asList);
                }
                arrayList.addAll(dragZoneFactory.createBubbleHalfScreenDragZones());
                dropTargetManager2.getClass();
                DropTargetManager.DragState dragState = new DropTargetManager.DragState(dropTargetManager2, arrayList, expandedView);
                BubbleBarLayerView.AnonymousClass1 anonymousClass1 = (BubbleBarLayerView.AnonymousClass1) dropTargetManager2.dragZoneChangedListener;
                anonymousClass1.getClass();
                BubbleBarLocation bubbleBarLocation = dragState.initialDragZone instanceof DragZone.Bubble.Left ? BubbleBarLocation.LEFT : BubbleBarLocation.RIGHT;
                anonymousClass1.mInitialLocation = bubbleBarLocation;
                anonymousClass1.val$locationChangeListener.mInitialLocation = bubbleBarLocation;
                dropTargetManager2.state = dragState;
                ValueAnimator valueAnimator = dropTargetManager2.animator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                DropTargetView dropTargetView = dropTargetManager2.dropTargetView;
                if (dropTargetView.getParent() != null) {
                    dropTargetManager2.container.removeView(dropTargetView);
                }
                dropTargetManager2.container.addView(dropTargetView, 0);
                dropTargetView.setAlpha(0.0f);
                dropTargetView.setElevation(dropTargetManager2.context.getResources().getDimension(R.dimen.drop_target_elevation));
                dropTargetView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            }
            BubbleBarExpandedViewDragController.this.getClass();
            return true;
        }

        @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
        public final void onMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4) {
            View view2;
            Object obj;
            DragZone dragZone;
            if (!this.isMoving) {
                this.isMoving = true;
                BubbleBarAnimationHelper bubbleBarAnimationHelper = BubbleBarExpandedViewDragController.this.animationHelper;
                BubbleBarExpandedView expandedView = bubbleBarAnimationHelper.getExpandedView();
                if (expandedView == null) {
                    Log.w("BubbleBarAnimationHelper", "Trying to animate start drag without a bubble");
                } else {
                    BubbleBarAnimationHelper.setDragPivot(expandedView);
                    if (true != expandedView.mIsDragging) {
                        expandedView.mIsDragging = true;
                        expandedView.updateSamplingState();
                        if (expandedView.mPositioner.mImeVisible) {
                            ((BubbleExpandedViewManager$Companion$fromBubbleController$1) expandedView.mManager).$controller.hideCurrentInputMethod(null);
                        }
                    }
                    float f5 = expandedView.mDraggedCornerRadius / 0.4f;
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) View.SCALE_X, 0.4f), ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) View.SCALE_Y, 0.4f), ObjectAnimator.ofFloat(expandedView, BubbleBarExpandedView.CORNER_RADIUS, f5));
                    animatorSet.setDuration(400L).setInterpolator(Interpolators.EMPHASIZED);
                    ObjectAnimator duration = ObjectAnimator.ofFloat(expandedView.mHandleView, (Property<BubbleBarHandleView, Float>) View.ALPHA, 0.0f).setDuration(100L);
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    animatorSet2.playTogether(animatorSet, duration);
                    animatorSet2.addListener(new BubbleBarAnimationHelper.DragAnimatorListenerAdapter(expandedView));
                    bubbleBarAnimationHelper.startNewAnimator(animatorSet2);
                }
            }
            BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController = BubbleBarExpandedViewDragController.this;
            bubbleBarExpandedViewDragController.expandedView.setTranslationX(bubbleBarExpandedViewDragController.expandedViewInitialTranslationX + f3);
            BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController2 = BubbleBarExpandedViewDragController.this;
            bubbleBarExpandedViewDragController2.expandedView.setTranslationY(bubbleBarExpandedViewDragController2.expandedViewInitialTranslationY + f4);
            BubbleBarExpandedViewDragController.this.dismissView.show();
            BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController3 = BubbleBarExpandedViewDragController.this;
            final DropTargetManager dropTargetManager = bubbleBarExpandedViewDragController3.dropTargetManager;
            if (dropTargetManager == null) {
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                final BubbleExpandedViewPinController bubbleExpandedViewPinController = bubbleBarExpandedViewDragController3.pinController;
                RectF rectF = bubbleExpandedViewPinController.dismissZone;
                if (rectF == null || !rectF.contains(rawX, rawY)) {
                    boolean z = bubbleExpandedViewPinController.onLeft;
                    boolean z2 = rawX < ((float) bubbleExpandedViewPinController.screenCenterX);
                    bubbleExpandedViewPinController.onLeft = z2;
                    if (z != z2) {
                        final BubbleBarLocation bubbleBarLocation = z2 ? BubbleBarLocation.LEFT : BubbleBarLocation.RIGHT;
                        final View dropTargetView = bubbleExpandedViewPinController.getDropTargetView();
                        if (dropTargetView == null) {
                            dropTargetView = LayoutInflater.from(bubbleExpandedViewPinController.context).inflate(R.layout.bubble_bar_drop_target, (ViewGroup) bubbleExpandedViewPinController.container, false);
                            bubbleExpandedViewPinController.dropTargetView = dropTargetView;
                            bubbleExpandedViewPinController.container.addView(dropTargetView, 0);
                            dropTargetView.setAlpha(0.0f);
                        }
                        if (dropTargetView.getAlpha() > 0.0f) {
                            bubbleExpandedViewPinController.animateOut(dropTargetView, new Runnable() { // from class: com.android.wm.shell.shared.bubbles.BaseBubblePinController$showDropTarget$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BaseBubblePinController.this.updateLocation(bubbleBarLocation);
                                    BaseBubblePinController.this.animateIn(dropTargetView);
                                }
                            });
                        } else {
                            bubbleExpandedViewPinController.updateLocation(bubbleBarLocation);
                            bubbleExpandedViewPinController.animateIn(dropTargetView);
                        }
                        BubbleBarLayerView.LocationChangeListener locationChangeListener = bubbleExpandedViewPinController.listener;
                        if (locationChangeListener != null) {
                            BubbleBarLayerView.this.mBubbleController.getClass();
                        }
                    } else if (bubbleExpandedViewPinController.stuckToDismissTarget && (view2 = bubbleExpandedViewPinController.dropTargetView) != null) {
                        bubbleExpandedViewPinController.animateIn(view2);
                    }
                    bubbleExpandedViewPinController.stuckToDismissTarget = false;
                    return;
                }
                return;
            }
            int rawX2 = (int) motionEvent.getRawX();
            int rawY2 = (int) motionEvent.getRawY();
            DropTargetManager.DragState dragState = dropTargetManager.state;
            if (dragState == null) {
                return;
            }
            DragZone dragZone2 = dragState.currentDragZone;
            Iterator it = dragState.dragZones.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                } else {
                    obj = it.next();
                    if (((DragZone) obj).getBounds().contains(rawX2, rawY2)) {
                        break;
                    }
                }
            }
            DragZone dragZone3 = (DragZone) obj;
            if (dragZone3 == null) {
                dragZone3 = dragState.currentDragZone;
            }
            dragState.currentDragZone = dragZone3;
            if (Intrinsics.areEqual(dragZone2, dragZone3)) {
                return;
            }
            BubbleBarLayerView.AnonymousClass1 anonymousClass1 = (BubbleBarLayerView.AnonymousClass1) dropTargetManager.dragZoneChangedListener;
            anonymousClass1.getClass();
            boolean z3 = dragZone3 instanceof DragZone.Bubble.Left;
            boolean z4 = dragZone3 instanceof DragZone.Bubble.Right;
            if ((z3 || z4) && dragZone3 != anonymousClass1.mLastBubbleLocationDragZone) {
                anonymousClass1.mLastBubbleLocationDragZone = dragZone3;
                if (z3) {
                    Parcelable.Creator<BubbleBarLocation> creator = BubbleBarLocation.CREATOR;
                } else {
                    Parcelable.Creator<BubbleBarLocation> creator2 = BubbleBarLocation.CREATOR;
                }
                BubbleBarLayerView.this.mBubbleController.getClass();
            }
            DropTargetManager.DragState dragState2 = dropTargetManager.state;
            if (dragState2 == null || (dragZone = dragState2.currentDragZone) == null) {
                return;
            }
            final Rect dropTarget = dragZone.getDropTarget();
            DropTargetView dropTargetView2 = dropTargetManager.dropTargetView;
            if (dropTarget == null) {
                dropTargetManager.startFadeAnimation(dropTargetView2.getAlpha(), 0.0f, null);
                return;
            }
            if (dropTargetView2.getAlpha() == 0.0f) {
                dropTargetView2.rect.set(new RectF(dropTarget));
                dropTargetView2.invalidate();
                dropTargetManager.startFadeAnimation(0.0f, 1.0f, null);
                return;
            }
            ValueAnimator valueAnimator = dropTargetManager.animator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            final float alpha = dropTargetView2.getAlpha();
            final RectF rectF2 = new RectF(dropTargetView2.rect);
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setDuration(250L);
            ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.wm.shell.shared.bubbles.DropTargetManager$startMorphAnimation$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(androidx.core.animation.Animator animator) {
                    float floatValue = ((Float) ValueAnimator.this.getAnimatedValue()).floatValue();
                    DropTargetManager dropTargetManager2 = dropTargetManager;
                    DropTargetView dropTargetView3 = dropTargetManager2.dropTargetView;
                    float f6 = alpha;
                    dropTargetView3.setAlpha(((1 - f6) * floatValue) + f6);
                    RectF rectF3 = dropTargetManager2.morphRect;
                    RectF rectF4 = rectF2;
                    float f7 = rectF4.left;
                    Rect rect = dropTarget;
                    rectF3.left = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(rect.left, f7, floatValue, f7);
                    float f8 = rectF4.top;
                    rectF3.top = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(rect.top, f8, floatValue, f8);
                    float f9 = rectF4.right;
                    rectF3.right = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(rect.right, f9, floatValue, f9);
                    float f10 = rectF4.bottom;
                    rectF3.bottom = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(rect.bottom, f10, floatValue, f10);
                    DropTargetView dropTargetView4 = dropTargetManager2.dropTargetView;
                    dropTargetView4.rect.set(rectF3);
                    dropTargetView4.invalidate();
                }
            });
            dropTargetManager.animator = ofFloat;
            ofFloat.start(false);
        }

        @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
        public final void onUp(View view, float f, float f2, float f3, float f4) {
            view.setTranslationZ(0.0f);
            finishDrag();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MagnetListener implements MagnetizedObject.MagnetListener {
        public MagnetListener() {
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onReleasedInTarget(MagnetizedObject magnetizedObject) {
            BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController = BubbleBarExpandedViewDragController.this;
            ((BubbleBarLayerView$$ExternalSyntheticLambda0) bubbleBarExpandedViewDragController.getDragListener()).onReleased(true);
            DropTargetManager dropTargetManager = bubbleBarExpandedViewDragController.dropTargetManager;
            if (dropTargetManager != null) {
                dropTargetManager.onDragEnded();
            } else {
                BubbleExpandedViewPinController bubbleExpandedViewPinController = bubbleBarExpandedViewDragController.pinController;
                View dropTargetView = bubbleExpandedViewPinController.getDropTargetView();
                if (dropTargetView != null) {
                    bubbleExpandedViewPinController.animateOut(dropTargetView, new BaseBubblePinController$hideDropTarget$1$1(bubbleExpandedViewPinController, dropTargetView));
                }
                bubbleExpandedViewPinController.dismissZone = null;
                BubbleBarLayerView.LocationChangeListener locationChangeListener = bubbleExpandedViewPinController.listener;
                if (locationChangeListener != null) {
                    locationChangeListener.onRelease(bubbleExpandedViewPinController.onLeft ? BubbleBarLocation.LEFT : BubbleBarLocation.RIGHT);
                }
            }
            bubbleBarExpandedViewDragController.dismissView.hide();
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onStuckToTarget(MagnetizedObject magnetizedObject) {
            BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController = BubbleBarExpandedViewDragController.this;
            bubbleBarExpandedViewDragController.isStuckToDismiss = true;
            final BubbleExpandedViewPinController bubbleExpandedViewPinController = bubbleBarExpandedViewDragController.pinController;
            bubbleExpandedViewPinController.stuckToDismissTarget = true;
            boolean z = bubbleExpandedViewPinController.onLeft;
            boolean z2 = bubbleExpandedViewPinController.initialLocationOnLeft;
            final boolean z3 = z != z2;
            if (z3) {
                bubbleExpandedViewPinController.onLeft = z2;
                BubbleBarLayerView.LocationChangeListener locationChangeListener = bubbleExpandedViewPinController.listener;
                if (locationChangeListener != null) {
                    Parcelable.Creator<BubbleBarLocation> creator = BubbleBarLocation.CREATOR;
                    BubbleBarLayerView.this.mBubbleController.getClass();
                }
            }
            View view = bubbleExpandedViewPinController.dropTargetView;
            if (view != null) {
                bubbleExpandedViewPinController.animateOut(view, new Runnable() { // from class: com.android.wm.shell.shared.bubbles.BaseBubblePinController$onStuckToDismissTarget$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (z3) {
                            BaseBubblePinController baseBubblePinController = bubbleExpandedViewPinController;
                            baseBubblePinController.updateLocation(baseBubblePinController.onLeft ? BubbleBarLocation.LEFT : BubbleBarLocation.RIGHT);
                        }
                    }
                });
            }
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onUnstuckFromTarget(MagnetizedObject.MagneticTarget magneticTarget, MagnetizedObject magnetizedObject, float f, float f2, boolean z) {
            BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController = BubbleBarExpandedViewDragController.this;
            bubbleBarExpandedViewDragController.isStuckToDismiss = false;
            BubbleBarAnimationHelper bubbleBarAnimationHelper = bubbleBarExpandedViewDragController.animationHelper;
            BubbleBarExpandedView expandedView = bubbleBarAnimationHelper.getExpandedView();
            if (expandedView == null) {
                Log.w("BubbleBarAnimationHelper", "Trying to unsnap the expanded view from dismiss without a bubble");
                return;
            }
            BubbleBarAnimationHelper.setDragPivot(expandedView);
            float f3 = expandedView.mDraggedCornerRadius / 0.4f;
            AnimatorSet animatorSet = new AnimatorSet();
            Property property = View.SCALE_X;
            Property property2 = View.SCALE_Y;
            animatorSet.playTogether(ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) property, 0.4f), ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) property2, 0.4f), ObjectAnimator.ofFloat(expandedView, BubbleBarExpandedView.CORNER_RADIUS, f3), ObjectAnimator.ofFloat(magneticTarget.targetView, (Property<View, Float>) property, 1.0f), ObjectAnimator.ofFloat(magneticTarget.targetView, (Property<View, Float>) property2, 1.0f));
            animatorSet.setDuration(400L).setInterpolator(Interpolators.EMPHASIZED_DECELERATE);
            animatorSet.addListener(new BubbleBarAnimationHelper.DragAnimatorListenerAdapter(expandedView));
            bubbleBarAnimationHelper.startNewAnimator(animatorSet);
        }
    }

    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.wm.shell.shared.magnetictarget.MagnetizedObject, com.android.wm.shell.shared.magnetictarget.MagnetizedObject$Companion$magnetizeView$1] */
    public BubbleBarExpandedViewDragController(Context context, final BubbleBarExpandedView bubbleBarExpandedView, DismissView dismissView, BubbleBarAnimationHelper bubbleBarAnimationHelper, BubblePositioner bubblePositioner, BubbleExpandedViewPinController bubbleExpandedViewPinController, DropTargetManager dropTargetManager, DragZoneFactory dragZoneFactory, DragListener dragListener) {
        this.expandedView = bubbleBarExpandedView;
        this.dismissView = dismissView;
        this.animationHelper = bubbleBarAnimationHelper;
        this.bubblePositioner = bubblePositioner;
        this.pinController = bubbleExpandedViewPinController;
        this.dropTargetManager = dropTargetManager;
        this.dragZoneFactory = dragZoneFactory;
        this.dragListener = dragListener;
        MagnetizedObject.Companion.getClass();
        final Context context2 = bubbleBarExpandedView.getContext();
        final DynamicAnimation.AnonymousClass1 anonymousClass1 = DynamicAnimation.TRANSLATION_X;
        final DynamicAnimation.AnonymousClass2 anonymousClass2 = DynamicAnimation.TRANSLATION_Y;
        ?? r7 = new MagnetizedObject(bubbleBarExpandedView, context2, anonymousClass1, anonymousClass2) { // from class: com.android.wm.shell.shared.magnetictarget.MagnetizedObject$Companion$magnetizeView$1
            {
                context2.getClass();
                anonymousClass1.getClass();
                anonymousClass2.getClass();
            }

            @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
            public final float getHeight(Object obj) {
                return ((View) obj).getHeight();
            }

            @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
            public final void getLocationOnScreen(Object obj, int[] iArr) {
                ((View) obj).getLocationOnScreen(iArr);
            }

            @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
            public final float getWidth(Object obj) {
                return ((View) obj).getWidth();
            }
        };
        this.magnetizedExpandedView = r7;
        r7.magnetListener = new MagnetListener();
        r7.animateStuckToTarget = new Function5() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarExpandedViewDragController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function5
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                MagnetizedObject.MagneticTarget magneticTarget = (MagnetizedObject.MagneticTarget) obj;
                ((Float) obj2).getClass();
                ((Float) obj3).getClass();
                ((Boolean) obj4).getClass();
                final Function0 function0 = (Function0) obj5;
                BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController = BubbleBarExpandedViewDragController.this;
                Runnable runnable = function0 != null ? new Runnable() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarExpandedViewDragController$sam$java_lang_Runnable$0
                    @Override // java.lang.Runnable
                    public final /* synthetic */ void run() {
                        Function0.this.invoke();
                    }
                } : null;
                BubbleBarAnimationHelper bubbleBarAnimationHelper2 = bubbleBarExpandedViewDragController.animationHelper;
                BubbleBarExpandedView expandedView = bubbleBarAnimationHelper2.getExpandedView();
                if (expandedView == null) {
                    Log.w("BubbleBarAnimationHelper", "Trying to snap the expanded view to target without a bubble");
                } else {
                    BubbleBarAnimationHelper.setDragPivot(expandedView);
                    int[] iArr = bubbleBarAnimationHelper2.mTmpLocation;
                    expandedView.getLocationOnScreen(iArr);
                    iArr[0] = iArr[0] + ((int) ((expandedView.getScaleX() * expandedView.getWidth()) / 2.0f));
                    int height = iArr[1] + ((int) ((expandedView.getHeight() * 0.2f) / 2.0f));
                    iArr[1] = height;
                    PointF pointF = magneticTarget.centerOnScreen;
                    float f = pointF.x - iArr[0];
                    float f2 = pointF.y - height;
                    float f3 = expandedView.mDraggedCornerRadius / 0.2f;
                    AnimatorSet animatorSet = new AnimatorSet();
                    Property property = View.TRANSLATION_X;
                    float[] fArr = {expandedView.getTranslationX() + f};
                    Property property2 = View.TRANSLATION_Y;
                    float[] fArr2 = {expandedView.getTranslationY() + f2};
                    Property property3 = View.SCALE_X;
                    Property property4 = View.SCALE_Y;
                    animatorSet.playTogether(ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) property, fArr), ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) property2, fArr2), ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) property3, 0.2f), ObjectAnimator.ofFloat(expandedView, (Property<BubbleBarExpandedView, Float>) property4, 0.2f), ObjectAnimator.ofFloat(expandedView, BubbleBarExpandedView.CORNER_RADIUS, f3), ObjectAnimator.ofFloat(magneticTarget.targetView, (Property<View, Float>) property3, 1.25f), ObjectAnimator.ofFloat(magneticTarget.targetView, (Property<View, Float>) property4, 1.25f));
                    animatorSet.setDuration(400L).setInterpolator(Interpolators.EMPHASIZED_DECELERATE);
                    animatorSet.addListener(new BubbleBarAnimationHelper.DragAnimatorListenerAdapter(bubbleBarAnimationHelper2, expandedView, runnable) { // from class: com.android.wm.shell.bubbles.bar.BubbleBarAnimationHelper.9
                        public final /* synthetic */ Runnable val$endRunnable;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass9(BubbleBarAnimationHelper bubbleBarAnimationHelper22, BubbleBarExpandedView expandedView2, Runnable runnable2) {
                            super(expandedView2);
                            this.val$endRunnable = runnable2;
                        }

                        @Override // com.android.wm.shell.bubbles.bar.BubbleBarAnimationHelper.DragAnimatorListenerAdapter, android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(android.animation.Animator animator) {
                            super.onAnimationEnd(animator);
                            Runnable runnable2 = this.val$endRunnable;
                            if (runnable2 != null) {
                                runnable2.run();
                            }
                        }
                    });
                    bubbleBarAnimationHelper22.startNewAnimator(animatorSet);
                }
                return Unit.INSTANCE;
            }
        };
        DismissCircleView dismissCircleView = dismissView.circle;
        MagnetizedObject.MagneticTarget magneticTarget = new MagnetizedObject.MagneticTarget(dismissCircleView, dismissCircleView.getWidth());
        r7.associatedTargets.add(magneticTarget);
        magneticTarget.updateLocationOnScreen();
        this.draggedBubbleElevation = context.getResources().getDimension(R.dimen.dragged_bubble_elevation);
        final HandleDragListener handleDragListener = new HandleDragListener();
        bubbleBarExpandedView.mHandleView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarExpandedViewDragController.2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == 0) {
                    BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController = BubbleBarExpandedViewDragController.this;
                    bubbleBarExpandedViewDragController.expandedViewInitialTranslationX = bubbleBarExpandedViewDragController.expandedView.getTranslationX();
                    BubbleBarExpandedViewDragController bubbleBarExpandedViewDragController2 = BubbleBarExpandedViewDragController.this;
                    bubbleBarExpandedViewDragController2.expandedViewInitialTranslationY = bubbleBarExpandedViewDragController2.expandedView.getTranslationY();
                }
                boolean maybeConsumeMotionEvent = maybeConsumeMotionEvent(motionEvent);
                if (motionEvent.getActionMasked() == 2 && maybeConsumeMotionEvent) {
                    return true;
                }
                HandleDragListener handleDragListener2 = handleDragListener;
                view.getClass();
                return handleDragListener2.onTouch(view, motionEvent) || maybeConsumeMotionEvent;
            }
        });
    }

    public final DragListener getDragListener() {
        return this.dragListener;
    }
}
