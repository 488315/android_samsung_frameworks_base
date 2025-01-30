package com.android.wm.shell.freeform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.support.v4.media.AbstractC0000x2c234b15;
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
import com.android.wm.shell.freeform.FreeformContainerManager;
import com.android.systemui.R;
import com.facebook.rebound.OrigamiValueConverter;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* renamed from: mH */
    public FreeformContainerManager.HandlerC3983H f448mH;
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
    public static void m2749$$Nest$msettleDownPointerEffect(FreeformContainerView freeformContainerView) {
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
                    FreeformContainerView$$ExternalSyntheticLambda3 freeformContainerView$$ExternalSyntheticLambda3 = new FreeformContainerView$$ExternalSyntheticLambda3(freeformContainerView, imageView, 0);
                    FreeformContainerView$$ExternalSyntheticLambda3 freeformContainerView$$ExternalSyntheticLambda32 = new FreeformContainerView$$ExternalSyntheticLambda3(freeformContainerView, imageView, 1);
                    FreeformContainerView$$ExternalSyntheticLambda3 freeformContainerView$$ExternalSyntheticLambda33 = new FreeformContainerView$$ExternalSyntheticLambda3(freeformContainerView, imageView, 2);
                    int i3 = (iconViewListCount - i2) - 1;
                    float f = TAIL_ICON_ALPHA_ARRAY[i3];
                    float f2 = TAIL_ICON_SCALE_ARRAY[i3];
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, f);
                    ofFloat.addUpdateListener(freeformContainerView$$ExternalSyntheticLambda3);
                    ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, f2);
                    ofFloat2.addUpdateListener(freeformContainerView$$ExternalSyntheticLambda32);
                    ValueAnimator ofFloat3 = ValueAnimator.ofFloat(y, y2);
                    ofFloat3.addUpdateListener(freeformContainerView$$ExternalSyntheticLambda33);
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(ofFloat);
                    arrayList.add(ofFloat2);
                    arrayList.add(ofFloat3);
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
                FreeformContainerView freeformContainerView = FreeformContainerView.this;
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
                FreeformContainerView freeformContainerView = FreeformContainerView.this;
                Rect rect = (Rect) freeformContainerView.mSystemGestureExclusionRects.get(0);
                if (freeformContainerView.getIconViewListCount() > 0) {
                    freeformContainerView.getPointerViewBounds(rect);
                    freeformContainerView.setSystemGestureExclusionRects(freeformContainerView.mSystemGestureExclusionRects);
                } else {
                    rect.setEmpty();
                    freeformContainerView.setSystemGestureExclusionRects(Collections.emptyList());
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

    public static void rotateBounds(Rect rect, Rect rect2, int i, int i2) {
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
        ValueAnimator ofInt;
        if (z) {
            ofInt = ValueAnimator.ofInt(0, 255);
            ofInt.setDuration(283L);
            ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.freeform.FreeformContainerView.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    FreeformContainerView.this.mBackgroundDimView.setVisibility(0);
                }
            });
        } else {
            ofInt = ValueAnimator.ofInt(255, 0);
            ofInt.setDuration(333L);
            ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.freeform.FreeformContainerView.2
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator, boolean z2) {
                    FreeformContainerView.this.mBackgroundDimView.setVisibility(8);
                }
            });
        }
        ofInt.setInterpolator(new LinearInterpolator());
        ofInt.addUpdateListener(new FreeformContainerView$$ExternalSyntheticLambda3(this, ofInt, 3));
        ofInt.start();
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
                        float f2 = (float) spring.mCurrentState.position;
                        ImageView imageView2 = imageView;
                        imageView2.setTranslationX(f2);
                        imageView2.setAlpha(f);
                    }
                };
                Spring createSpring = springChain.mSpringSystem.createSpring();
                createSpring.addListener(springChain);
                createSpring.setSpringConfig(springChain.mAttachmentSpringConfig);
                springChain.mSprings.add(createSpring);
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
                            if (freeformContainerView.mIsAppIconMoving || !freeformContainerView.mActivatedYSpringList.isEmpty()) {
                                return;
                            }
                            freeformContainerView.mViewController.closeFullscreenMode("fullscreen_mode_request_spring_anim_y");
                        }
                    }

                    @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                    public final void onSpringUpdate(Spring spring) {
                        imageView.setTranslationY(((float) spring.mCurrentState.position) + (FreeformContainerView.this.mPointerSettleDownGap * i));
                    }
                };
                Spring createSpring2 = springChain2.mSpringSystem.createSpring();
                createSpring2.addListener(springChain2);
                createSpring2.setSpringConfig(springChain2.mAttachmentSpringConfig);
                springChain2.mSprings.add(createSpring2);
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
            if (freeformContainerViewController.mState != 2) {
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("[ViewController] onKeyDown(", keyCode, "), ");
                int i = freeformContainerViewController.mState;
                m1m.append(i != 1 ? i != 2 ? "UNKNOWN" : "STATE_FOLDER" : "STATE_POINTER");
                m1m.append(", should not be focused! lp=");
                m1m.append(freeformContainerViewController.mLayoutParams);
                Log.e("FreeformContainer", m1m.toString());
                freeformContainerViewController.setFocusable(false);
            } else if (keyCode == 4) {
                Log.i("FreeformContainer", "[ViewController] onKeyDown(" + keyCode + "), close folder");
                freeformContainerViewController.updateContainerState(1, true, true);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean gatherTransparentRegion(Region region) {
        boolean gatherTransparentRegion = super.gatherTransparentRegion(region);
        updateTouchableRegion();
        if (!((ArrayList) this.mViewController.mFullscreenModeRequests).isEmpty()) {
            region.setEmpty();
        } else if (this.mViewController.isPointerView()) {
            Region region2 = new Region(0, 0, getWidth(), getHeight());
            region2.op(this.mTouchableRegion, Region.Op.XOR);
            region.set(region2);
        }
        return gatherTransparentRegion;
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
        int iconViewListCount = getIconViewListCount() - 1;
        return iconViewListCount + (-1) >= i && i >= Math.max(iconViewListCount + (-2), 0);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        FreeformContainerManager freeformContainerManager = FreeformContainerManager.this;
        if (!(freeformContainerManager.mRotation != freeformContainerManager.mContext.getDisplay().getRotation())) {
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
        this.mViewController.updateContainerState(1, true, true);
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerCallback
    public final void onItemAdded(FreeformContainerItem freeformContainerItem) {
        ImageView imageView = new ImageView(this.mContext);
        imageView.setImageDrawable(freeformContainerItem.mShowingIcon);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        freeformContainerItem.mIconView = imageView;
        this.mIconViewList.add(imageView);
        this.mPointerGroupView.addView(imageView);
        updateIconsPosition();
        if (this.mViewController.isPointerView()) {
            updatePointerViewVisibility(0);
            if (!this.mIsAppIconMoving) {
                this.f448mH.post(new FreeformContainerView$$ExternalSyntheticLambda0(this, 0));
            } else if (getIconViewListCount() > 1) {
                ((ImageView) this.mIconViewList.get(getIconViewListCount() - 2)).setVisibility(4);
            }
        }
        buildSpringChainsOfAllAppIcons();
        if (getIconViewListCount() == 1) {
            FreeformContainerViewController freeformContainerViewController = this.mViewController;
            if (freeformContainerViewController.mContainerView != null) {
                Log.i("FreeformContainer", "[ViewController] Show Window");
                FreeformContainerManager.HandlerC3983H handlerC3983H = freeformContainerViewController.f449mH;
                FreeformContainerViewController$$ExternalSyntheticLambda1 freeformContainerViewController$$ExternalSyntheticLambda1 = freeformContainerViewController.mHideContainerViewRunnable;
                if (handlerC3983H.hasCallbacks(freeformContainerViewController$$ExternalSyntheticLambda1)) {
                    freeformContainerViewController.f449mH.removeCallbacks(freeformContainerViewController$$ExternalSyntheticLambda1);
                }
                freeformContainerViewController.mContainerView.setVisibility(0);
                freeformContainerViewController.mContainerView.mBackgroundDimView.setVisibility(8);
                freeformContainerViewController.updateContainerState(1, true, false);
            }
        }
        updatePointerViewDescription();
        this.mPointerSettleDownEffectRequested = true;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerCallback
    public final void onItemRemoved(FreeformContainerItem freeformContainerItem) {
        final ImageView imageView = freeformContainerItem.mIconView;
        if (imageView != null) {
            this.mIconViewList.remove(imageView);
            if (!this.mViewController.isPointerView() || getIconViewListCount() <= 0) {
                this.mPointerGroupView.removeView(imageView);
            } else {
                Animation loadAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.freeform_container_remove_icon_fade_out);
                loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView.3
                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationEnd(Animation animation) {
                        FreeformContainerView.this.mPointerGroupView.removeView(imageView);
                        FreeformContainerView.m2749$$Nest$msettleDownPointerEffect(FreeformContainerView.this);
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationStart(Animation animation) {
                    }
                });
                imageView.startAnimation(loadAnimation);
            }
            buildSpringChainsOfAllAppIcons();
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
            this.mViewController.updateContainerState(1, false, true);
        }
        getPointerViewBounds(this.mTmpBounds);
        rotateBounds(rect, this.mTmpBounds, i, i2);
        Rect rect2 = this.mTmpBounds;
        setPointerPosition(rect2.left, rect2.top, false);
        updatePointerViewImmediately();
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        FreeformContainerViewController freeformContainerViewController = this.mViewController;
        if (freeformContainerViewController.mState == 2) {
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            int action = motionEvent.getAction();
            if (action == 0) {
                FreeformContainerFolderView freeformContainerFolderView = freeformContainerViewController.mFolderView;
                Rect rect = freeformContainerViewController.mTmpBounds;
                freeformContainerFolderView.getTrayBounds(rect);
                if (!rect.contains((int) rawX, (int) rawY)) {
                    Log.i("FreeformContainer", "[ViewController] onTouchEvent(" + action + "), close folder");
                    freeformContainerViewController.updateContainerState(1, true, true);
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
            SharedPreferences.Editor edit = this.mContext.getSharedPreferences("freeform_container_pref", 0).edit();
            edit.putFloat("position_x", this.mPointerPosition.x);
            edit.putFloat("position_y", this.mPointerPosition.y);
            edit.putInt("rotation", this.mContext.getDisplay().getRotation());
            edit.commit();
        }
        ImageButton imageButton = this.mPointerView;
        if (imageButton != null) {
            imageButton.setOnTouchListener(null);
        }
        Log.i("FreeformContainer", "[ContainerView] removeAllSpringsListeners");
        Iterator it = this.mSpringChainX.mSprings.iterator();
        while (it.hasNext()) {
            ((Spring) it.next()).mListeners.clear();
        }
        Iterator it2 = this.mSpringChainY.mSprings.iterator();
        while (it2.hasNext()) {
            ((Spring) it2.next()).mListeners.clear();
        }
        this.mSpringChainX = SpringChain.create();
        this.mSpringChainY = SpringChain.create();
        this.mIconViewList.clear();
        this.mViewController.hideWindow();
        ViewTreeObserver viewTreeObserver = getRootView().getViewTreeObserver();
        viewTreeObserver.removeOnComputeInternalInsetsListener(this.mInsetsComputer);
        viewTreeObserver.removeOnDrawListener(this.mSystemGestureExcludeUpdater);
    }

    public final void setPointerPosition(float f, float f2, boolean z) {
        if (z) {
            int i = this.mPointerViewSize;
            int i2 = (int) ((f - (i / 2.0f)) + 0.5f);
            int i3 = (int) ((f2 - (i / 2.0f)) + 0.5f);
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
        if (CoreRune.MW_FREEFORM_MINIMIZE_SA_LOGGING && isShown()) {
            Point point = new Point(this.mTmpBounds.centerX(), this.mTmpBounds.centerY());
            Point point2 = new Point(this.mViewController.mDisplayFrame.width() / 3, this.mViewController.mDisplayFrame.height() / 3);
            int i7 = (point.x / point2.x) + ((point.y / point2.y) * 3);
            if (this.mLastIconPosition != i7) {
                CoreSaLogger.logForAdvanced("2203", CoreSaConstant.FREEFORM_DETAIL_MOVE_ICON[i7]);
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
        String str;
        final String str2;
        final int iconViewListCount = getIconViewListCount();
        if (iconViewListCount == 1) {
            str = ((FreeformContainerItem) this.mViewController.mItemController.mItemList.get(0)).mDescription;
            str2 = "activate";
        } else if (iconViewListCount >= 2) {
            str = getContext().getString(R.string.freeform_container_minimized_windows_tray);
            str2 = ServiceTuple.BASIC_STATUS_OPEN;
        } else {
            str = "";
            str2 = "";
        }
        this.mPointerView.setContentDescription(str);
        this.mPointerView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.freeform.FreeformContainerView.4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, str2));
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i == 16) {
                    int i2 = iconViewListCount;
                    if (i2 == 1) {
                        FreeformContainerItem freeformContainerItem = (FreeformContainerItem) FreeformContainerView.this.mViewController.mItemController.mItemList.get(0);
                        if (freeformContainerItem != null) {
                            FreeformContainerView.this.f448mH.sendMessage(30, freeformContainerItem);
                        }
                    } else if (i2 >= 2) {
                        FreeformContainerView.this.mViewController.updateContainerState(2, true, true);
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
        if (!this.mSpringChainX.mSprings.isEmpty() && this.mSpringChainX.getControlSpring() != null) {
            this.mSpringChainX.getControlSpring().setEndValue(this.mPointerView.getX());
        }
        if (this.mSpringChainY.mSprings.isEmpty() || this.mSpringChainY.getControlSpring() == null) {
            return;
        }
        this.mSpringChainY.getControlSpring().setEndValue(this.mPointerView.getY());
    }

    public final void updateSpringConfig(int i) {
        double d = 150;
        this.mSpringChainX.mMainSpringConfig.tension = OrigamiValueConverter.tensionFromOrigamiValue(d);
        double d2 = i;
        this.mSpringChainX.mMainSpringConfig.friction = OrigamiValueConverter.frictionFromOrigamiValue(d2);
        this.mSpringChainY.mMainSpringConfig.tension = OrigamiValueConverter.tensionFromOrigamiValue(d);
        this.mSpringChainY.mMainSpringConfig.friction = OrigamiValueConverter.frictionFromOrigamiValue(d2);
        double d3 = 200;
        this.mSpringChainX.mAttachmentSpringConfig.tension = OrigamiValueConverter.tensionFromOrigamiValue(d3);
        double d4 = 12;
        this.mSpringChainX.mAttachmentSpringConfig.friction = OrigamiValueConverter.frictionFromOrigamiValue(d4);
        this.mSpringChainY.mAttachmentSpringConfig.tension = OrigamiValueConverter.tensionFromOrigamiValue(d3);
        this.mSpringChainY.mAttachmentSpringConfig.friction = OrigamiValueConverter.frictionFromOrigamiValue(d4);
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
