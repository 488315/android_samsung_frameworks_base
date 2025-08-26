package com.android.wm.shell.freeform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.wm.shell.freeform.FreeformContainerManager;
import com.facebook.rebound.OrigamiValueConverter;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.facebook.rebound.SpringConfig;
import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
class FreeformContainerView extends FrameLayout implements FreeformContainerCallback {
    public static final float[] TAIL_ICON_ALPHA_ARRAY = {1.0f, 0.5f, 0.1f};
    public static final float[] TAIL_ICON_SCALE_ARRAY = {1.0f, 0.9f, 0.81f};
    public final ArrayList mActivatedXSpringList;
    public final ArrayList mActivatedYSpringList;
    public int mAnimElevation;
    public FrameLayout mBackgroundDimView;
    public final Context mContext;
    public int mDefaultGapTop;
    public float mFirstDownX;
    public float mFirstDownY;
    public float mFirstPointerX;
    public float mFirstPointerY;
    public FreeformContainerManager.H mH;
    public int mIconItemTopMarginInFolder;
    public int mIconLeftMarginInFolder;
    public final ArrayList mIconViewList;
    public final FreeformContainerView$$ExternalSyntheticLambda1 mInsetsComputer;
    public boolean mIsAppIconMoving;
    public int mLastIconPosition;
    public float mLastPositionX;
    public float mLastPositionY;
    public int mMaximumFlingVelocity;
    public int mMinimumFlingVelocity;
    public boolean mNeedInitPosition;
    public ViewGroup mPointerGroupView;
    public final PointF mPointerPosition;
    public boolean mPointerSettleDownEffectRequested;
    public int mPointerSettleDownGap;
    public ImageButton mPointerView;
    public int mPointerViewSize;
    public SpringChain mSpringChainX;
    public SpringChain mSpringChainY;
    public final FreeformContainerView$$ExternalSyntheticLambda2 mSystemGestureExcludeUpdater;
    public final List mSystemGestureExclusionRects;
    public int mThresholdToMove;
    public final Rect mTmpBounds;
    public final Region mTmpRegion;
    public final Region mTouchableRegion;
    public final PointF mVelocity;
    public VelocityTracker mVelocityTracker;
    public FreeformContainerViewController mViewController;

    /* renamed from: -$$Nest$msettleDownPointerEffect, reason: not valid java name */
    public static void m3269$$Nest$msettleDownPointerEffect(FreeformContainerView freeformContainerView) {
        if (freeformContainerView.mPointerSettleDownEffectRequested) {
            freeformContainerView.mPointerSettleDownEffectRequested = false;
            int iconViewListCount = freeformContainerView.getIconViewListCount();
            int i = 0;
            for (int i2 = iconViewListCount - 1; i2 >= 0; i2--) {
                ImageView imageView = (ImageView) freeformContainerView.mIconViewList.get(i2);
                if (freeformContainerView.isTailIconViewOrder(i2)) {
                    i++;
                    imageView.setVisibility(0);
                    imageView.setX(freeformContainerView.mPointerView.getX());
                    imageView.setY(freeformContainerView.mPointerView.getY());
                    float y = freeformContainerView.mPointerView.getY();
                    float y2 = freeformContainerView.mPointerView.getY() + (freeformContainerView.mPointerSettleDownGap * i);
                    FreeformContainerView$$ExternalSyntheticLambda4 freeformContainerView$$ExternalSyntheticLambda4 = new FreeformContainerView$$ExternalSyntheticLambda4(freeformContainerView, imageView, 0);
                    FreeformContainerView$$ExternalSyntheticLambda4 freeformContainerView$$ExternalSyntheticLambda42 = new FreeformContainerView$$ExternalSyntheticLambda4(freeformContainerView, imageView, 1);
                    FreeformContainerView$$ExternalSyntheticLambda4 freeformContainerView$$ExternalSyntheticLambda43 = new FreeformContainerView$$ExternalSyntheticLambda4(freeformContainerView, imageView, 2);
                    int i3 = (iconViewListCount - i2) - 1;
                    float f = TAIL_ICON_ALPHA_ARRAY[i3];
                    float f2 = TAIL_ICON_SCALE_ARRAY[i3];
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, f);
                    valueAnimatorOfFloat.addUpdateListener(freeformContainerView$$ExternalSyntheticLambda4);
                    ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, f2);
                    valueAnimatorOfFloat2.addUpdateListener(freeformContainerView$$ExternalSyntheticLambda42);
                    ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(y, y2);
                    valueAnimatorOfFloat3.addUpdateListener(freeformContainerView$$ExternalSyntheticLambda43);
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(valueAnimatorOfFloat);
                    arrayList.add(valueAnimatorOfFloat2);
                    arrayList.add(valueAnimatorOfFloat3);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(200L);
                    animatorSet.playTogether(arrayList);
                    animatorSet.start();
                } else {
                    imageView.setAlpha(1.0f);
                    imageView.setScaleX(1.0f);
                    imageView.setScaleY(1.0f);
                    imageView.setY(freeformContainerView.mPointerView.getY());
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda1] */
    public FreeformContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSystemGestureExclusionRects = Collections.singletonList(new Rect());
        this.mTmpBounds = new Rect();
        this.mIconViewList = new ArrayList();
        this.mTmpRegion = new Region();
        this.mTouchableRegion = new Region();
        this.mInsetsComputer = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda1
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                FreeformContainerView freeformContainerView = this.f$0;
                float[] fArr = FreeformContainerView.TAIL_ICON_ALPHA_ARRAY;
                freeformContainerView.getClass();
                internalInsetsInfo.contentInsets.setEmpty();
                internalInsetsInfo.visibleInsets.setEmpty();
                internalInsetsInfo.touchableRegion.set(freeformContainerView.mTouchableRegion);
                internalInsetsInfo.setTouchableInsets(3);
            }
        };
        this.mVelocity = new PointF();
        this.mIsAppIconMoving = false;
        this.mPointerPosition = new PointF();
        this.mSystemGestureExcludeUpdater = new ViewTreeObserver.OnDrawListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda2
            @Override // android.view.ViewTreeObserver.OnDrawListener
            public final void onDraw() {
                FreeformContainerView freeformContainerView = this.f$0;
                Rect rect = (Rect) freeformContainerView.mSystemGestureExclusionRects.get(0);
                if (freeformContainerView.getIconViewListCount() > 0) {
                    freeformContainerView.getPointerViewBounds(rect);
                    freeformContainerView.setSystemGestureExclusionRects(freeformContainerView.mSystemGestureExclusionRects);
                } else {
                    rect.setEmpty();
                    freeformContainerView.setSystemGestureExclusionRects(Collections.EMPTY_LIST);
                }
            }
        };
        this.mPointerSettleDownEffectRequested = false;
        this.mNeedInitPosition = false;
        this.mLastIconPosition = -1;
        this.mSpringChainX = SpringChain.create();
        this.mSpringChainY = SpringChain.create();
        this.mActivatedXSpringList = new ArrayList();
        this.mActivatedYSpringList = new ArrayList();
        Log.i("FreeformContainer", "[ContainerView] Create FreeformContainerView");
        this.mContext = context;
    }

    public static void rotateBounds(int i, Rect rect, Rect rect2, int i2) {
        int i3 = i2 - i;
        if (i3 < 0) {
            i3 += 4;
        }
        Rect rect3 = new Rect();
        if (i3 == 0) {
            rect3.set(rect2);
        } else if (i3 == 1) {
            rect3.top = rect.bottom - rect2.right;
            int i4 = rect2.top;
            rect3.left = i4;
            rect3.right = rect2.height() + i4;
            rect3.bottom = rect2.width() + rect3.top;
        } else if (i3 == 2) {
            rect3.top = rect.bottom - rect2.bottom;
            int i5 = rect.right - rect2.right;
            rect3.left = i5;
            rect3.right = rect2.width() + i5;
            rect3.bottom = rect2.height() + rect3.top;
        } else if (i3 == 3) {
            rect3.top = rect2.left;
            int i6 = rect.right - rect2.bottom;
            rect3.left = i6;
            rect3.right = rect2.height() + i6;
            rect3.bottom = rect2.width() + rect3.top;
        }
        rect2.set(rect3);
    }

    public final void addMovementToVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker == null) {
            return;
        }
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        motionEvent.offsetLocation(rawX, rawY);
        this.mVelocityTracker.addMovement(motionEvent);
        motionEvent.offsetLocation(-rawX, -rawY);
    }

    public final void animateBackgroundDim(boolean z) {
        ValueAnimator valueAnimatorOfInt = z ? ValueAnimator.ofInt(0, 255) : ValueAnimator.ofInt(255, 0);
        valueAnimatorOfInt.setDuration(z ? 283L : 333L);
        valueAnimatorOfInt.addListener(z ? new AnimatorListenerAdapter() { // from class: com.android.wm.shell.freeform.FreeformContainerView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                FreeformContainerView.this.mBackgroundDimView.setVisibility(0);
            }
        } : new AnimatorListenerAdapter() { // from class: com.android.wm.shell.freeform.FreeformContainerView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                FreeformContainerView.this.mBackgroundDimView.setVisibility(8);
            }
        });
        valueAnimatorOfInt.setInterpolator(new LinearInterpolator());
        valueAnimatorOfInt.addUpdateListener(new FreeformContainerView$$ExternalSyntheticLambda4(this, valueAnimatorOfInt, 3));
        valueAnimatorOfInt.start();
    }

    public final void buildSpringChainsOfAllAppIcons() {
        if (this.mIconViewList.isEmpty()) {
            return;
        }
        this.mActivatedXSpringList.clear();
        this.mActivatedYSpringList.clear();
        this.mSpringChainX = SpringChain.create();
        this.mSpringChainY = SpringChain.create();
        int iconViewListCount = getIconViewListCount();
        final int i = 0;
        for (int i2 = iconViewListCount - 1; i2 >= 0; i2--) {
            if (isTailIconViewOrder(i2)) {
                i++;
            }
            final ImageView imageView = (ImageView) this.mIconViewList.get(i2);
            if (i2 < iconViewListCount - 3) {
                imageView.setVisibility(4);
            } else {
                imageView.setVisibility(0);
                final float f = TAIL_ICON_ALPHA_ARRAY[(iconViewListCount - i2) - 1];
                SpringChain springChain = this.mSpringChainX;
                SimpleSpringListener simpleSpringListener = new SimpleSpringListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView.8
                    @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                    public final void onSpringActivate(Spring spring) {
                        FreeformContainerView freeformContainerView = FreeformContainerView.this;
                        if (freeformContainerView.mActivatedXSpringList.contains(spring)) {
                            return;
                        }
                        if (freeformContainerView.mActivatedXSpringList.isEmpty()) {
                            freeformContainerView.mViewController.openFullscreenMode("fullscreen_mode_request_spring_anim_x");
                        }
                        freeformContainerView.mActivatedXSpringList.add(spring);
                    }

                    @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                    public final void onSpringAtRest(Spring spring) {
                        FreeformContainerView freeformContainerView = FreeformContainerView.this;
                        if (freeformContainerView.mActivatedXSpringList.contains(spring)) {
                            freeformContainerView.mActivatedXSpringList.remove(spring);
                            if (freeformContainerView.mIsAppIconMoving || !freeformContainerView.mActivatedXSpringList.isEmpty()) {
                                return;
                            }
                            freeformContainerView.mViewController.closeFullscreenMode("fullscreen_mode_request_spring_anim_x");
                        }
                    }

                    @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                    public final void onSpringUpdate(Spring spring) {
                        imageView.setTranslationX((float) spring.mCurrentState.position);
                        imageView.setAlpha(f);
                    }
                };
                Spring springCreateSpring = springChain.mSpringSystem.createSpring();
                springCreateSpring.addListener(springChain);
                SpringConfig springConfig = springChain.mAttachmentSpringConfig;
                if (springConfig == null) {
                    throw new IllegalArgumentException("springConfig is required");
                }
                springCreateSpring.mSpringConfig = springConfig;
                springChain.mSprings.add(springCreateSpring);
                springChain.mListeners.add(simpleSpringListener);
                SpringChain springChain2 = this.mSpringChainY;
                SimpleSpringListener simpleSpringListener2 = new SimpleSpringListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView.9
                    @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                    public final void onSpringActivate(Spring spring) {
                        FreeformContainerView freeformContainerView = FreeformContainerView.this;
                        if (freeformContainerView.mActivatedYSpringList.contains(spring)) {
                            return;
                        }
                        if (freeformContainerView.mActivatedYSpringList.isEmpty()) {
                            freeformContainerView.mViewController.openFullscreenMode("fullscreen_mode_request_spring_anim_y");
                        }
                        freeformContainerView.mActivatedYSpringList.add(spring);
                    }

                    @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                    public final void onSpringAtRest(Spring spring) {
                        FreeformContainerView freeformContainerView = FreeformContainerView.this;
                        if (freeformContainerView.mActivatedYSpringList.contains(spring)) {
                            freeformContainerView.mActivatedYSpringList.remove(spring);
                            if (!freeformContainerView.mIsAppIconMoving && freeformContainerView.mActivatedYSpringList.isEmpty()) {
                                freeformContainerView.mViewController.closeFullscreenMode("fullscreen_mode_request_spring_anim_y");
                            }
                        }
                    }

                    @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                    public final void onSpringUpdate(Spring spring) {
                        imageView.setTranslationY(((float) spring.mCurrentState.position) + (FreeformContainerView.this.mPointerSettleDownGap * i));
                    }
                };
                Spring springCreateSpring2 = springChain2.mSpringSystem.createSpring();
                springCreateSpring2.addListener(springChain2);
                SpringConfig springConfig2 = springChain2.mAttachmentSpringConfig;
                if (springConfig2 == null) {
                    throw new IllegalArgumentException("springConfig is required");
                }
                springCreateSpring2.mSpringConfig = springConfig2;
                springChain2.mSprings.add(springCreateSpring2);
                springChain2.mListeners.add(simpleSpringListener2);
            }
        }
        updateAllSpringsCurrentValue();
        this.mSpringChainX.setControlSpringIndex();
        this.mSpringChainY.setControlSpringIndex();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            Log.i("FreeformContainer", "[ContainerView] dispatchKeyEvent(DOWN)");
            FreeformContainerViewController freeformContainerViewController = this.mViewController;
            int keyCode = keyEvent.getKeyCode();
            if (!(freeformContainerViewController.mState == 1)) {
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(keyCode, "[ViewController] onKeyDown(", "), ");
                int i = freeformContainerViewController.mState;
                sbM.append(i != -1 ? i != 0 ? i != 1 ? "UNKNOWN" : "CONTAINER_STATE_FOLDER" : "CONTAINER_STATE_POINTER" : "CONTAINER_STATE_UNDEFINED");
                sbM.append(", should not be focused! lp=");
                sbM.append(freeformContainerViewController.mLayoutParams);
                Log.e("FreeformContainer", sbM.toString());
                freeformContainerViewController.setFocusable(false);
            } else if (keyCode == 4) {
                Log.i("FreeformContainer", "[ViewController] onKeyDown(" + keyCode + "), close folder");
                freeformContainerViewController.updateContainerState(0, true, true);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean gatherTransparentRegion(Region region) {
        boolean zGatherTransparentRegion = super.gatherTransparentRegion(region);
        updateTouchableRegion();
        if (!((ArrayList) this.mViewController.mFullscreenModeRequests).isEmpty()) {
            region.setEmpty();
            return zGatherTransparentRegion;
        }
        if (this.mViewController.isPointerView()) {
            Region region2 = new Region(0, 0, getWidth(), getHeight());
            region2.op(this.mTouchableRegion, Region.Op.XOR);
            region.set(region2);
        }
        return zGatherTransparentRegion;
    }

    public final int getIconViewListCount() {
        ArrayList arrayList = this.mIconViewList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public final void getPointerViewBounds(Rect rect) {
        int x = (int) this.mPointerView.getX();
        int y = (int) this.mPointerView.getY();
        int i = this.mPointerViewSize;
        rect.set(x, y, x + i, i + y);
    }

    public final boolean isTailIconViewOrder(int i) {
        if (getIconViewListCount() <= 1) {
            return false;
        }
        int iconViewListCount = getIconViewListCount();
        return iconViewListCount + (-2) >= i && i >= Math.max(iconViewListCount + (-3), 0);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        FreeformContainerManager freeformContainerManager = FreeformContainerManager.this;
        if (freeformContainerManager.mRotation == freeformContainerManager.mContext.getDisplay().getRotation()) {
            this.mViewController.updateDisplayFrame(true);
            updatePointerViewImmediately();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getParent().requestTransparentRegion(this);
    }

    public final void onCloseSystemDialogs(String str) {
        super.onCloseSystemDialogs(str);
        Log.i("FreeformContainer", "[ContainerView] onCloseSystemDialogs");
        this.mViewController.updateContainerState(0, true, true);
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerCallback
    public final void onItemAdded(FreeformContainerItem freeformContainerItem) throws Resources.NotFoundException {
        FreeformContainerItem itemByName;
        ImageView imageView = new ImageView(this.mContext);
        imageView.setImageDrawable(freeformContainerItem.mShowingIcon);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        freeformContainerItem.setIconView(imageView);
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW && (freeformContainerItem instanceof MultiInstanceItem) && (itemByName = this.mViewController.mItemController.getItemByName(freeformContainerItem.mPackageName)) != null && itemByName.asMultiInstanceItem() != null) {
            MultiInstanceItem multiInstanceItemAsMultiInstanceItem = itemByName.asMultiInstanceItem();
            ImageView imageView2 = multiInstanceItemAsMultiInstanceItem.mIconView;
            if (imageView2 != null) {
                removeIconView(imageView2);
            }
            multiInstanceItemAsMultiInstanceItem.setIconView(imageView);
            multiInstanceItemAsMultiInstanceItem.mChildItemList.forEach(new MultiInstanceItem$$ExternalSyntheticLambda1(this.mViewController, 0));
        }
        this.mIconViewList.add(imageView);
        this.mPointerGroupView.addView(imageView);
        updateIconsPosition();
        if (this.mViewController.isPointerView()) {
            updatePointerViewVisibility(0);
            if (!this.mIsAppIconMoving) {
                this.mH.post(new FreeformContainerView$$ExternalSyntheticLambda0(this));
            } else if (getIconViewListCount() > 1) {
                ((ImageView) this.mIconViewList.get(getIconViewListCount() - 2)).setVisibility(4);
            }
        }
        buildSpringChainsOfAllAppIcons();
        if (getIconViewListCount() == 1) {
            FreeformContainerViewController freeformContainerViewController = this.mViewController;
            if (freeformContainerViewController.mContainerView != null) {
                Log.i("FreeformContainer", "[ViewController] Show Window");
                FreeformContainerManager.H h = freeformContainerViewController.mH;
                FreeformContainerViewController$$ExternalSyntheticLambda1 freeformContainerViewController$$ExternalSyntheticLambda1 = freeformContainerViewController.mHideContainerViewRunnable;
                if (h.hasCallbacks(freeformContainerViewController$$ExternalSyntheticLambda1)) {
                    freeformContainerViewController.mH.removeCallbacks(freeformContainerViewController$$ExternalSyntheticLambda1);
                }
                freeformContainerViewController.mContainerView.setVisibility(0);
                freeformContainerViewController.mContainerView.mBackgroundDimView.setVisibility(8);
                freeformContainerViewController.updateContainerState(0, true, false);
            }
        }
        updatePointerViewDescription();
        this.mPointerSettleDownEffectRequested = true;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerCallback
    public final void onItemRemoved(FreeformContainerItem freeformContainerItem) throws Resources.NotFoundException {
        if (!CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW || freeformContainerItem.asMultiInstanceItem() == null) {
            ImageView imageView = freeformContainerItem.mIconView;
            if (imageView != null) {
                removeIconView(imageView);
            }
        } else {
            MultiInstanceItem multiInstanceItem = freeformContainerItem.asMultiInstanceItem().mParentItem;
            if (multiInstanceItem == null || multiInstanceItem.mChildItemList.size() == 0) {
                removeIconView(freeformContainerItem.mIconView);
            }
        }
        if (getIconViewListCount() == 0) {
            this.mViewController.hideWindow();
        }
        updatePointerViewDescription();
        this.mPointerSettleDownEffectRequested = true;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerCallback
    public final void onRotationChanged(int i, int i2, Rect rect) {
        if (!this.mViewController.isPointerView()) {
            this.mViewController.updateContainerState(0, false, true);
        }
        getPointerViewBounds(this.mTmpBounds);
        rotateBounds(i, rect, this.mTmpBounds, i2);
        Rect rect2 = this.mTmpBounds;
        setPointerPosition(rect2.left, rect2.top, false);
        updatePointerViewImmediately();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003a  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        FreeformContainerViewController freeformContainerViewController = this.mViewController;
        if (freeformContainerViewController.mState == 1) {
            freeformContainerViewController.getClass();
            int action = motionEvent.getAction();
            if (action == 0) {
                freeformContainerViewController.mFolderView.getTrayBounds(freeformContainerViewController.mTmpBounds);
                if (!freeformContainerViewController.mTmpBounds.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
                        FreeformContainerFolderView freeformContainerFolderView = freeformContainerViewController.mFolderView;
                        if (freeformContainerFolderView.mPopupWindow != null) {
                            freeformContainerFolderView.dismissMultiInstancePreviewPopup("outside_touch_folder");
                        } else {
                            Log.i("FreeformContainer", "[ViewController] onTouchEvent(" + action + "), close folder");
                            freeformContainerViewController.updateContainerState(0, true, true);
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerCallback
    public final void onViewDestroyed() {
        if (this.mNeedInitPosition) {
            Log.i("FreeformContainer", "[ContainerView] savePositionToSharedPreferences, skip saving. Need to init position first");
        } else {
            SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences("freeform_container_pref", 0).edit();
            editorEdit.putFloat("position_x", this.mPointerPosition.x);
            editorEdit.putFloat("position_y", this.mPointerPosition.y);
            editorEdit.putInt("rotation", this.mContext.getDisplay().getRotation());
            editorEdit.commit();
        }
        ImageButton imageButton = this.mPointerView;
        if (imageButton != null) {
            imageButton.setOnTouchListener(null);
        }
        Log.i("FreeformContainer", "[ContainerView] removeAllSpringsListeners");
        Iterator it = this.mSpringChainX.mSprings.iterator();
        while (it.hasNext()) {
            ((Spring) it.next()).removeAllListeners();
        }
        Iterator it2 = this.mSpringChainY.mSprings.iterator();
        while (it2.hasNext()) {
            ((Spring) it2.next()).removeAllListeners();
        }
        this.mSpringChainX = SpringChain.create();
        this.mSpringChainY = SpringChain.create();
        this.mIconViewList.clear();
        this.mViewController.hideWindow();
        ViewTreeObserver viewTreeObserver = getRootView().getViewTreeObserver();
        viewTreeObserver.removeOnComputeInternalInsetsListener(this.mInsetsComputer);
        viewTreeObserver.removeOnDrawListener(this.mSystemGestureExcludeUpdater);
    }

    public final void removeIconView(final ImageView imageView) throws Resources.NotFoundException {
        this.mIconViewList.remove(imageView);
        if (!this.mViewController.isPointerView() || getIconViewListCount() <= 0) {
            this.mPointerGroupView.removeView(imageView);
        } else {
            Animation animationLoadAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.freeform_container_remove_icon_fade_out);
            animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView.3
                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationEnd(Animation animation) {
                    FreeformContainerView.this.mPointerGroupView.removeView(imageView);
                    FreeformContainerView.m3269$$Nest$msettleDownPointerEffect(FreeformContainerView.this);
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationStart(Animation animation) {
                }
            });
            imageView.startAnimation(animationLoadAnimation);
        }
        buildSpringChainsOfAllAppIcons();
    }

    public final void setPointerPosition(float f, float f2, boolean z) {
        if (z) {
            int i = this.mPointerViewSize;
            float f3 = i / 2.0f;
            int i2 = (int) ((f - f3) + 0.5f);
            int i3 = (int) ((f2 - f3) + 0.5f);
            this.mTmpBounds.set(i2, i3, i2 + i, i + i3);
        } else {
            Rect rect = this.mTmpBounds;
            int i4 = (int) f;
            int i5 = (int) f2;
            int i6 = this.mPointerViewSize;
            rect.set(i4, i5, i4 + i6, i6 + i5);
        }
        this.mViewController.adjustPositionInDisplay(0, 0, this.mTmpBounds);
        this.mPointerView.setX(this.mTmpBounds.left);
        this.mPointerView.setY(this.mTmpBounds.top);
        PointF pointF = this.mPointerPosition;
        Rect rect2 = this.mTmpBounds;
        pointF.set(rect2.left, rect2.top);
        if (!this.mNeedInitPosition) {
            MultiWindowManager.getInstance().reportFreeformContainerPoint(this.mPointerPosition);
        }
        boolean z2 = CoreRune.MW_FREEFORM_MINIMIZE_SA_LOGGING;
        if (z2 && isShown()) {
            Point point = new Point(this.mTmpBounds.centerX(), this.mTmpBounds.centerY());
            Point point2 = new Point(this.mViewController.mDisplayFrame.width() / 3, this.mViewController.mDisplayFrame.height() / 3);
            int i7 = (point.x / point2.x) + ((point.y / point2.y) * 3);
            if (this.mLastIconPosition != i7) {
                if (z2) {
                    CoreSaLogger.logForAdvanced("2203", CoreSaConstant.FREEFORM_DETAIL_MOVE_ICON[i7]);
                }
                this.mLastIconPosition = i7;
            }
        }
    }

    public final void updateAllSpringsCurrentValue() {
        Iterator it = this.mSpringChainX.mSprings.iterator();
        while (it.hasNext()) {
            Spring spring = (Spring) it.next();
            spring.mRestSpeedThreshold = 0.30000001192092896d;
            spring.mDisplacementFromRestThreshold = 0.30000001192092896d;
            spring.setCurrentValue(this.mPointerView.getX());
            spring.setVelocity(90.0d);
        }
        Iterator it2 = this.mSpringChainY.mSprings.iterator();
        while (it2.hasNext()) {
            Spring spring2 = (Spring) it2.next();
            spring2.mRestSpeedThreshold = 0.30000001192092896d;
            spring2.mDisplacementFromRestThreshold = 0.30000001192092896d;
            spring2.setCurrentValue(this.mPointerView.getY());
            spring2.setVelocity(90.0d);
        }
    }

    public final void updateIconsPosition() {
        float x = this.mPointerView.getX();
        float y = this.mPointerView.getY();
        for (int iconViewListCount = getIconViewListCount() - 1; iconViewListCount >= 0; iconViewListCount--) {
            ImageView imageView = (ImageView) this.mIconViewList.get(iconViewListCount);
            imageView.setX(x);
            imageView.setY(y);
        }
    }

    public final void updatePointerViewDescription() {
        String string;
        final String str;
        final int iconViewListCount = getIconViewListCount();
        if (iconViewListCount == 1) {
            string = ((FreeformContainerItem) this.mViewController.mItemController.mItemList.get(0)).mDescription;
            str = getContext().getString(R.string.freeform_conatiner_activate) + " " + getContext().getString(R.string.freeform_container_double_tap_hold_to_move);
        } else if (iconViewListCount >= 2) {
            string = getContext().getString(R.string.freeform_container_minimized_windows_tray);
            str = getContext().getString(R.string.freeform_conatiner_open) + " " + getContext().getString(R.string.freeform_container_double_tap_hold_to_move);
        } else {
            string = "";
            str = "";
        }
        this.mPointerView.setContentDescription(string);
        this.mPointerView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.freeform.FreeformContainerView.4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, str));
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i == 16) {
                    int i2 = iconViewListCount;
                    if (i2 == 1) {
                        FreeformContainerItem freeformContainerItem = (FreeformContainerItem) FreeformContainerView.this.mViewController.mItemController.mItemList.get(0);
                        if (freeformContainerItem != null) {
                            if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW && (freeformContainerItem instanceof MultiInstanceItem)) {
                                FreeformContainerView.this.mViewController.updateContainerState(1, true, true);
                            } else {
                                FreeformContainerView.this.mH.sendMessage(30, freeformContainerItem);
                            }
                        }
                    } else if (i2 >= 2) {
                        FreeformContainerView.this.mViewController.updateContainerState(1, true, true);
                    }
                }
                return super.performAccessibilityAction(view, i, bundle);
            }
        });
    }

    public final void updatePointerViewImmediately() {
        updateIconsPosition();
        updateAllSpringsCurrentValue();
        int i = 0;
        for (int iconViewListCount = getIconViewListCount() - 2; iconViewListCount >= 0 && isTailIconViewOrder(iconViewListCount); iconViewListCount--) {
            i++;
            ImageView imageView = (ImageView) this.mIconViewList.get(iconViewListCount);
            imageView.setX(this.mPointerView.getX());
            imageView.setY(this.mPointerView.getY() + (this.mPointerSettleDownGap * i));
        }
        requestLayout();
    }

    public final void updatePointerViewVisibility(int i) {
        if (i == 8) {
            this.mPointerGroupView.removeAllViews();
            this.mPointerGroupView.clearDisappearingChildren();
        }
        this.mPointerView.setVisibility(i);
        this.mPointerGroupView.setVisibility(i);
    }

    public final void updateSpringChainEndValue() {
        if (!this.mSpringChainX.mSprings.isEmpty()) {
            SpringChain springChain = this.mSpringChainX;
            if (((Spring) springChain.mSprings.get(springChain.mControlSpringIndex)) != null) {
                SpringChain springChain2 = this.mSpringChainX;
                ((Spring) springChain2.mSprings.get(springChain2.mControlSpringIndex)).setEndValue(this.mPointerView.getX());
            }
        }
        if (this.mSpringChainY.mSprings.isEmpty()) {
            return;
        }
        SpringChain springChain3 = this.mSpringChainY;
        if (((Spring) springChain3.mSprings.get(springChain3.mControlSpringIndex)) != null) {
            SpringChain springChain4 = this.mSpringChainY;
            ((Spring) springChain4.mSprings.get(springChain4.mControlSpringIndex)).setEndValue(this.mPointerView.getY());
        }
    }

    public final void updateSpringConfig(int i) {
        double d = 150;
        this.mSpringChainX.mMainSpringConfig.tension = OrigamiValueConverter.tensionFromOrigamiValue(d);
        double d2 = i;
        this.mSpringChainX.mMainSpringConfig.friction = OrigamiValueConverter.frictionFromOrigamiValue(d2);
        this.mSpringChainY.mMainSpringConfig.tension = OrigamiValueConverter.tensionFromOrigamiValue(d);
        this.mSpringChainY.mMainSpringConfig.friction = OrigamiValueConverter.frictionFromOrigamiValue(d2);
        this.mSpringChainX.mAttachmentSpringConfig.tension = OrigamiValueConverter.tensionFromOrigamiValue(200.0d);
        this.mSpringChainX.mAttachmentSpringConfig.friction = OrigamiValueConverter.frictionFromOrigamiValue(12.0d);
        this.mSpringChainY.mAttachmentSpringConfig.tension = OrigamiValueConverter.tensionFromOrigamiValue(200.0d);
        this.mSpringChainY.mAttachmentSpringConfig.friction = OrigamiValueConverter.frictionFromOrigamiValue(12.0d);
    }

    public final void updateTouchableRegion() {
        this.mTmpRegion.set(this.mTouchableRegion);
        if (this.mViewController.isPointerView()) {
            getPointerViewBounds(this.mTmpBounds);
            if (getIconViewListCount() > 1) {
                this.mTmpBounds.bottom += this.mPointerSettleDownGap;
            }
        } else {
            this.mTmpBounds.set(0, 0, getWidth(), getHeight());
        }
        this.mTouchableRegion.set(this.mTmpBounds);
        if (this.mTmpRegion.equals(this.mTouchableRegion)) {
            return;
        }
        forceLayout();
        requestLayout();
    }
}
