package com.android.wm.shell.freeform;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import com.android.internal.widget.RecyclerView;
import com.android.systemui.R;
import com.android.wm.shell.common.DismissView;
import com.android.wm.shell.common.DismissViewManager;
import com.android.wm.shell.freeform.FreeformContainerDismissButtonView;
import com.android.wm.shell.freeform.FreeformContainerFolderView;
import com.android.wm.shell.freeform.FreeformContainerManager;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class FreeformContainerViewController {
    public FreeformContainerView mContainerView;
    public final Context mContext;
    public FreeformContainerDismissButtonView mDismissButtonView;
    public FreeformContainerFolderView mFolderView;
    public FreeformContainerManager.H mH;
    public FreeformContainerViewController$$ExternalSyntheticLambda1 mHideCallback;
    public FreeformContainerItemController mItemController;
    public final LayoutInflater mLayoutInflater;
    public final WindowManager mWindowManager;
    public final Rect mDisplayFrame = new Rect();
    public final Rect mNonDecorDisplayFrame = new Rect();
    public final Rect mTmpBounds = new Rect();
    public final PointF mTmpPointF = new PointF();
    public final List mCallBacks = new ArrayList();
    public final List mFullscreenModeRequests = new ArrayList();
    public final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
    public int mState = -1;
    public final FreeformContainerViewController$$ExternalSyntheticLambda1 mHideContainerViewRunnable = new FreeformContainerViewController$$ExternalSyntheticLambda1(this, 0);

    public FreeformContainerViewController(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public final void adjustPositionInDisplay(int i, int i2, Rect rect) {
        int i3;
        Rect rect2 = this.mNonDecorDisplayFrame;
        int i4 = rect2.left;
        int i5 = i4 - i;
        int i6 = rect.left;
        int i7 = 0;
        if (i5 > i6) {
            i3 = (i4 - i) - i6;
        } else {
            int i8 = rect2.right;
            int i9 = i8 + i2;
            int i10 = rect.right;
            i3 = i9 < i10 ? (i8 + i2) - i10 : 0;
        }
        int i11 = rect2.top;
        int i12 = rect.top;
        if (i11 > i12) {
            i7 = i11 - i12;
        } else {
            int i13 = rect2.bottom;
            int i14 = rect.bottom;
            if (i13 < i14) {
                i7 = i13 - i14;
            }
        }
        if (i3 == 0 && i7 == 0) {
            return;
        }
        rect.offset(i3, i7);
    }

    public final boolean closeFullscreenMode(String str) {
        if (this.mContainerView == null) {
            Log.i("FreeformContainer", "[ViewController] close failed: mContainerView is null");
            return false;
        }
        if (!((ArrayList) this.mFullscreenModeRequests).contains(str)) {
            Log.w("FreeformContainer", "[ViewController] " + str + " does not exist, close failed");
            return false;
        }
        ((ArrayList) this.mFullscreenModeRequests).remove(str);
        Log.i("FreeformContainer", "[ViewController] close FullscreenMode: " + str);
        if (!((ArrayList) this.mFullscreenModeRequests).isEmpty()) {
            return true;
        }
        Log.i("FreeformContainer", "[ViewController] FullscreenMode Disabled");
        FreeformContainerView freeformContainerView = this.mContainerView;
        freeformContainerView.requestTransparentRegion(freeformContainerView);
        return true;
    }

    public final void createOrUpdateDismissButton() {
        if (this.mDismissButtonView == null) {
            this.mDismissButtonView = new FreeformContainerDismissButtonView(this.mContext);
        }
        this.mDismissButtonView.mDismissViewManager.createOrUpdateWrapper();
    }

    public final void hideDismissButton() {
        if (this.mDismissButtonView == null) {
            return;
        }
        Log.i("FreeformContainer", "[ViewController] hideDismissButton");
        FreeformContainerDismissButtonView freeformContainerDismissButtonView = this.mDismissButtonView;
        FreeformContainerViewController$$ExternalSyntheticLambda1 freeformContainerViewController$$ExternalSyntheticLambda1 = this.mHideCallback;
        freeformContainerDismissButtonView.getClass();
        Log.i("FreeformContainer", "[FreeformContainerDismissButtonView] hide()");
        freeformContainerDismissButtonView.setVisibility(4);
        if (freeformContainerDismissButtonView.mDismissButtonShowing) {
            freeformContainerDismissButtonView.mDismissButtonShowing = false;
        }
        DismissViewManager dismissViewManager = freeformContainerDismissButtonView.mDismissViewManager;
        dismissViewManager.mView.hide(new FreeformContainerDismissButtonView$$ExternalSyntheticLambda0(freeformContainerDismissButtonView, freeformContainerViewController$$ExternalSyntheticLambda1));
    }

    public final void hideDismissButtonAndDismissIcon(final FreeformContainerItem freeformContainerItem, View view, Rect rect) {
        Log.i("FreeformContainer", "[ViewController] hideDismissButtonAndDismissIcon: btn=" + isEnterDismissButton());
        hideDismissButton();
        if (isEnterDismissButton()) {
            Runnable runnable = new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerViewController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final FreeformContainerViewController freeformContainerViewController = this.f$0;
                    FreeformContainerItem freeformContainerItem2 = freeformContainerItem;
                    if (freeformContainerItem2 == null) {
                        freeformContainerViewController.mItemController.throwAwayAllItems();
                        if (CoreRune.MW_FREEFORM_MINIMIZE_SA_LOGGING) {
                            CoreSaLogger.logForAdvanced("2202");
                            return;
                        }
                        return;
                    }
                    freeformContainerViewController.getClass();
                    ArrayList arrayList = new ArrayList();
                    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW && freeformContainerItem2.isParentMultiInstanceItem()) {
                        arrayList.addAll(freeformContainerItem2.getItemList());
                    } else {
                        arrayList.add(freeformContainerItem2);
                    }
                    arrayList.forEach(new Consumer() { // from class: com.android.wm.shell.freeform.FreeformContainerViewController$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FreeformContainerViewController freeformContainerViewController2 = freeformContainerViewController;
                            FreeformContainerItem freeformContainerItem3 = (FreeformContainerItem) obj;
                            freeformContainerViewController2.getClass();
                            Log.d("FreeformContainer", "[ViewController] throw away: " + freeformContainerItem3 + ", dismiss_btn");
                            FreeformContainerItemController freeformContainerItemController = freeformContainerViewController2.mItemController;
                            freeformContainerItemController.getClass();
                            freeformContainerItem3.throwAway(freeformContainerItemController);
                        }
                    });
                }
            };
            FreeformContainerDismissButtonView freeformContainerDismissButtonView = this.mDismissButtonView;
            freeformContainerDismissButtonView.mDismissingIconView = view;
            DismissView dismissView = freeformContainerDismissButtonView.mDismissViewManager.mView;
            DismissView dismissView2 = freeformContainerDismissButtonView.mDismissViewManager.mView;
            FreeformContainerDismissButtonView.AnonymousClass1 anonymousClass1 = new Animation.AnimationListener(freeformContainerDismissButtonView, view, runnable) { // from class: com.android.wm.shell.freeform.FreeformContainerDismissButtonView.1
                public final /* synthetic */ Runnable val$dismissIconRunnable;
                public final /* synthetic */ View val$dismissingIconView;

                public AnonymousClass1(FreeformContainerDismissButtonView freeformContainerDismissButtonView2, View view2, Runnable runnable2) {
                    this.val$dismissingIconView = view2;
                    this.val$dismissIconRunnable = runnable2;
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationEnd(Animation animation) {
                    this.val$dismissingIconView.setVisibility(8);
                    this.val$dismissIconRunnable.run();
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationStart(Animation animation) {
                }
            };
            float fCenterX = rect.centerX();
            float fCenterY = rect.centerY();
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, ((dismissView.getWidth() / 2.0f) + dismissView.mDismissArea.left) - rect.centerX(), 0.0f, ((dismissView2.getHeight() / 2.0f) + dismissView2.mDismissArea.top) - rect.centerY());
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.7f, 1.0f, 0.7f, fCenterX, fCenterY);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(alphaAnimation);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(translateAnimation);
            animationSet.setInterpolator(InterpolatorUtils.SINE_OUT_60);
            animationSet.setDuration(250L);
            animationSet.setFillAfter(true);
            animationSet.setFillEnabled(true);
            animationSet.setAnimationListener(anonymousClass1);
            view2.startAnimation(animationSet);
        }
    }

    public final void hideWindow() {
        if (this.mContainerView == null) {
            return;
        }
        Log.i("FreeformContainer", "[ViewController] Hide Window");
        updateContainerState(-1, false, true);
        this.mContainerView.updatePointerViewVisibility(8);
        if (this.mDismissButtonView != null) {
            if (isDismissButtonShowing()) {
                FreeformContainerDismissButtonView freeformContainerDismissButtonView = this.mDismissButtonView;
                if (freeformContainerDismissButtonView.mDismissButtonShowing) {
                    freeformContainerDismissButtonView.mDismissButtonShowing = false;
                }
                closeFullscreenMode("fullscreen_mode_request_remove_target");
            }
            FreeformContainerDismissButtonView freeformContainerDismissButtonView2 = this.mDismissButtonView;
            FreeformContainerViewController$$ExternalSyntheticLambda1 freeformContainerViewController$$ExternalSyntheticLambda1 = this.mHideCallback;
            freeformContainerDismissButtonView2.getClass();
            Log.i("FreeformContainer", "[FreeformContainerDismissButtonView] hide()");
            freeformContainerDismissButtonView2.setVisibility(4);
            if (freeformContainerDismissButtonView2.mDismissButtonShowing) {
                freeformContainerDismissButtonView2.mDismissButtonShowing = false;
            }
            DismissViewManager dismissViewManager = freeformContainerDismissButtonView2.mDismissViewManager;
            dismissViewManager.mView.hide(new FreeformContainerDismissButtonView$$ExternalSyntheticLambda0(freeformContainerDismissButtonView2, freeformContainerViewController$$ExternalSyntheticLambda1));
        }
        this.mH.post(this.mHideContainerViewRunnable);
    }

    public final boolean isDismissButtonShowing() {
        FreeformContainerDismissButtonView freeformContainerDismissButtonView = this.mDismissButtonView;
        if (freeformContainerDismissButtonView == null) {
            return false;
        }
        return freeformContainerDismissButtonView.mDismissButtonShowing;
    }

    public final boolean isEnterDismissButton() {
        FreeformContainerDismissButtonView freeformContainerDismissButtonView = this.mDismissButtonView;
        if (freeformContainerDismissButtonView == null) {
            return false;
        }
        return freeformContainerDismissButtonView.mDismissViewManager.mView.mIsEnterDismissButton;
    }

    public final boolean isPointerView() {
        return this.mState == 0;
    }

    public final void notifyItemRemoved(FreeformContainerItem freeformContainerItem) {
        ArrayList arrayList = (ArrayList) this.mCallBacks;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            FreeformContainerCallback freeformContainerCallback = (FreeformContainerCallback) obj;
            Log.i("FreeformContainer", "[ViewController] onItemRemoved: " + freeformContainerCallback);
            freeformContainerCallback.onItemRemoved(freeformContainerItem);
        }
    }

    public final boolean openFullscreenMode(String str) {
        if (this.mContainerView == null) {
            Log.i("FreeformContainer", "[ViewController] open failed: mContainerView is null");
            return false;
        }
        if (((ArrayList) this.mFullscreenModeRequests).contains(str)) {
            Log.w("FreeformContainer", "[ViewController] " + str + " is already opened");
            return false;
        }
        ((ArrayList) this.mFullscreenModeRequests).add(str);
        Log.i("FreeformContainer", "[ViewController] open FullscreenMode: ".concat(str));
        if (((ArrayList) this.mFullscreenModeRequests).size() == 1) {
            Log.i("FreeformContainer", "[ViewController] FullscreenMode Enabled");
            FreeformContainerView freeformContainerView = this.mContainerView;
            freeformContainerView.requestTransparentRegion(freeformContainerView);
        }
        return true;
    }

    public final void setFocusable(boolean z) {
        int i = ((WindowManager.LayoutParams) this.mContainerView.getLayoutParams()).flags;
        if (z) {
            this.mLayoutParams.flags &= -9;
        } else {
            this.mLayoutParams.flags |= 8;
        }
        WindowManager.LayoutParams layoutParams = this.mLayoutParams;
        if (i != layoutParams.flags) {
            this.mWindowManager.updateViewLayout(this.mContainerView, layoutParams);
            Log.i("FreeformContainer", "[ViewController] setFocusable: " + z);
        }
    }

    public final void updateContainerState(int i, boolean z, boolean z2) {
        if (this.mState == i) {
            return;
        }
        Log.i("FreeformContainer", "[ViewController] updateContainerState: ".concat(i != -1 ? i != 0 ? i != 1 ? "UNKNOWN" : "CONTAINER_STATE_FOLDER" : "CONTAINER_STATE_POINTER" : "CONTAINER_STATE_UNDEFINED"));
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
            FreeformContainerFolderView freeformContainerFolderView = this.mFolderView;
            if (freeformContainerFolderView.mPopupWindow != null) {
                freeformContainerFolderView.dismissMultiInstancePreviewPopup("state_update");
            }
        }
        this.mState = i;
        if (i == 0) {
            this.mContainerView.mPointerView.setImportantForAccessibility(1);
            this.mContainerView.updatePointerViewVisibility(0);
            this.mContainerView.updatePointerViewDescription();
            this.mFolderView.collapse(z);
            if (z2) {
                this.mContainerView.animateBackgroundDim(false);
            }
            setFocusable(false);
            if (z) {
                if (this.mContainerView.getIconViewListCount() >= 2) {
                    this.mFolderView.getTrayBounds(this.mTmpBounds);
                    final FreeformContainerView freeformContainerView = this.mContainerView;
                    Rect rect = this.mTmpBounds;
                    freeformContainerView.updateIconsPosition();
                    TranslateAnimation translateAnimation = new TranslateAnimation((int) (freeformContainerView.isLayoutRtl() ? ((rect.right - freeformContainerView.mPointerView.getX()) - freeformContainerView.mPointerViewSize) - freeformContainerView.mIconLeftMarginInFolder : (rect.left - freeformContainerView.mPointerView.getX()) + freeformContainerView.mIconLeftMarginInFolder), 0.0f, (int) ((rect.top - freeformContainerView.mPointerView.getY()) + freeformContainerView.mIconItemTopMarginInFolder), 0.0f);
                    translateAnimation.setDuration(283L);
                    translateAnimation.setInterpolator(InterpolatorUtils.ONE_EASING);
                    translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView.6
                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationEnd(Animation animation) {
                            FreeformContainerView.this.mPointerGroupView.setElevation(0.0f);
                            FreeformContainerView.this.mViewController.closeFullscreenMode("fullscreen_mode_request_folder");
                            FreeformContainerView.m3268$$Nest$msettleDownPointerEffect(FreeformContainerView.this);
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationStart(Animation animation) {
                            FreeformContainerView.this.mPointerGroupView.setElevation(r0.mAnimElevation);
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationRepeat(Animation animation) {
                        }
                    });
                    freeformContainerView.mPointerGroupView.startAnimation(translateAnimation);
                    freeformContainerView.mPointerSettleDownEffectRequested = true;
                    final FreeformContainerFolderView freeformContainerFolderView2 = this.mFolderView;
                    freeformContainerFolderView2.mTrayView.setVisibility(0);
                    Animation animationLoadAnimation = AnimationUtils.loadAnimation(freeformContainerFolderView2.mContext, R.anim.freeform_container_tray_view_fade_out);
                    animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.5
                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationEnd(Animation animation) {
                            FreeformContainerFolderView.this.mTrayView.setVisibility(8);
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationStart(Animation animation) {
                        }
                    });
                    freeformContainerFolderView2.mTrayView.startAnimation(animationLoadAnimation);
                } else {
                    FreeformContainerView freeformContainerView2 = this.mContainerView;
                    freeformContainerView2.mH.post(new FreeformContainerView$$ExternalSyntheticLambda0(freeformContainerView2));
                }
            }
        } else if (i == 1) {
            this.mContainerView.mPointerView.setImportantForAccessibility(2);
            FreeformContainerView freeformContainerView3 = this.mContainerView;
            int i2 = 0;
            for (int iconViewListCount = freeformContainerView3.getIconViewListCount() - 2; iconViewListCount >= 0 && freeformContainerView3.isTailIconViewOrder(iconViewListCount); iconViewListCount--) {
                i2++;
                ImageView imageView = (ImageView) freeformContainerView3.mIconViewList.get(iconViewListCount);
                imageView.setX(freeformContainerView3.mPointerView.getX());
                imageView.setY(freeformContainerView3.mPointerView.getY() + (freeformContainerView3.mPointerSettleDownGap * i2));
                imageView.startAnimation(AnimationUtils.loadAnimation(freeformContainerView3.mContext, R.anim.freeform_container_hide_tail_icon));
            }
            this.mContainerView.mPointerView.setContentDescription("");
            this.mFolderView.calculateFolderSize();
            FreeformContainerView freeformContainerView4 = this.mContainerView;
            FreeformContainerFolderTrayView freeformContainerFolderTrayView = this.mFolderView.mTrayView;
            this.mTmpPointF.set(freeformContainerView4.mPointerView.getX() - ((freeformContainerFolderTrayView.mWidth - freeformContainerView4.mPointerViewSize) / 2.0f), freeformContainerView4.mPointerView.getY() - ((freeformContainerFolderTrayView.mHeight - freeformContainerView4.mPointerViewSize) / 2.0f));
            FreeformContainerFolderView freeformContainerFolderView3 = this.mFolderView;
            PointF pointF = this.mTmpPointF;
            Rect rect2 = freeformContainerFolderView3.mTmpBounds;
            int i3 = (int) pointF.x;
            int i4 = (int) pointF.y;
            FreeformContainerFolderTrayView freeformContainerFolderTrayView2 = freeformContainerFolderView3.mTrayView;
            rect2.set(i3, i4, freeformContainerFolderTrayView2.mWidth + i3, freeformContainerFolderTrayView2.mHeight + i4);
            freeformContainerFolderView3.mViewController.adjustPositionInDisplay(-freeformContainerFolderView3.mPaddingLeft, -freeformContainerFolderView3.mPaddingRight, freeformContainerFolderView3.mTmpBounds);
            freeformContainerFolderView3.setX(freeformContainerFolderView3.mTmpBounds.left);
            freeformContainerFolderView3.setY(freeformContainerFolderView3.mTmpBounds.top);
            freeformContainerFolderView3.setZ(freeformContainerFolderView3.mTrayView.getZ() + 1.0f);
            FreeformContainerFolderTrayView freeformContainerFolderTrayView3 = freeformContainerFolderView3.mTrayView;
            Rect rect3 = freeformContainerFolderView3.mTmpBounds;
            int i5 = rect3.left;
            int i6 = rect3.top;
            freeformContainerFolderTrayView3.setX(i5);
            freeformContainerFolderTrayView3.setY(i6);
            Log.i("FreeformContainer", "[FolderView] setFolderPosition: x=" + freeformContainerFolderView3.mTmpBounds.left + ", y=" + freeformContainerFolderView3.mTmpBounds.top + ", view=" + freeformContainerFolderView3);
            this.mFolderView.getTrayBounds(this.mTmpBounds);
            final FreeformContainerView freeformContainerView5 = this.mContainerView;
            Rect rect4 = this.mTmpBounds;
            TranslateAnimation translateAnimation2 = new TranslateAnimation(0.0f, (int) (freeformContainerView5.isLayoutRtl() ? ((rect4.right - freeformContainerView5.mPointerView.getX()) - freeformContainerView5.mPointerViewSize) - freeformContainerView5.mIconLeftMarginInFolder : (rect4.left - freeformContainerView5.mPointerView.getX()) + freeformContainerView5.mIconLeftMarginInFolder), 0.0f, (int) ((rect4.top - freeformContainerView5.mPointerView.getY()) + freeformContainerView5.mIconItemTopMarginInFolder));
            translateAnimation2.setDuration(283L);
            translateAnimation2.setInterpolator(InterpolatorUtils.ONE_EASING);
            translateAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView.7
                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationEnd(Animation animation) {
                    FreeformContainerView.this.mPointerGroupView.setElevation(0.0f);
                    FreeformContainerView.this.mPointerGroupView.setVisibility(4);
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationStart(Animation animation) {
                    FreeformContainerView.this.mPointerGroupView.setElevation(r0.mAnimElevation);
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationRepeat(Animation animation) {
                }
            });
            freeformContainerView5.mPointerGroupView.startAnimation(translateAnimation2);
            this.mContainerView.updateIconsPosition();
            this.mContainerView.animateBackgroundDim(true);
            setFocusable(true);
            FreeformContainerFolderView freeformContainerFolderView4 = this.mFolderView;
            if (freeformContainerFolderView4.mViewController.openFullscreenMode("fullscreen_mode_request_folder") && !freeformContainerFolderView4.mIsExpanded) {
                freeformContainerFolderView4.calculateFolderSize();
                freeformContainerFolderView4.setAdapter(freeformContainerFolderView4.mAdapter);
                freeformContainerFolderView4.mAdapter.notifyDataSetChanged();
                freeformContainerFolderView4.mIsExpanded = true;
                freeformContainerFolderView4.setVisibility(0);
                if (freeformContainerFolderView4.mVisibleIconCount < freeformContainerFolderView4.mAdapter.getItemCount()) {
                    freeformContainerFolderView4.scrollToPosition(0);
                }
                if (z) {
                    freeformContainerFolderView4.mIsExpandAnimating = true;
                    freeformContainerFolderView4.setHorizontalScrollBarEnabled(false);
                    int itemCount = freeformContainerFolderView4.mAdapter.getItemCount();
                    for (int i7 = 0; i7 < itemCount; i7++) {
                        FreeformContainerFolderView.SingleInstanceItemViewHolder singleInstanceItemViewHolder = (FreeformContainerFolderView.SingleInstanceItemViewHolder) freeformContainerFolderView4.findViewHolderForAdapterPosition(i7);
                        if (singleInstanceItemViewHolder != null) {
                            ((RecyclerView.ViewHolder) singleInstanceItemViewHolder).itemView.setVisibility(4);
                        }
                    }
                    freeformContainerFolderView4.mTrayView.setVisibility(0);
                    freeformContainerFolderView4.mTrayView.startAnimation(AnimationUtils.loadAnimation(freeformContainerFolderView4.mContext, R.anim.freeform_container_tray_view_fade_in));
                    freeformContainerFolderView4.mH.postDelayed(freeformContainerFolderView4.mOpenFolderRunnable, 33L);
                }
                if (CoreRune.MW_FREEFORM_MINIMIZED_PREVIEW) {
                    freeformContainerFolderView4.mFreeformThumbnailView = (FreeformThumbnailView) freeformContainerFolderView4.mLayoutInflater.inflate(R.layout.freeform_thumbnail, (ViewGroup) null);
                }
            }
            this.mFolderView.performAccessibilityAction(64, (Bundle) null);
        }
        this.mContainerView.updateTouchableRegion();
    }

    public final void updateDisplayFrame(boolean z) {
        Rect rect = new Rect(this.mDisplayFrame);
        DisplayInfo displayInfo = new DisplayInfo();
        this.mContext.getDisplay().getDisplayInfo(displayInfo);
        this.mDisplayFrame.set(0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
        this.mNonDecorDisplayFrame.set(this.mDisplayFrame);
        Rect rect2 = new Rect();
        FreeformContainerManager.getInstance(this.mContext).getClass();
        FreeformContainerManager.getOverrideStableInsets(rect2);
        this.mNonDecorDisplayFrame.inset(rect2);
        if (!z || rect.isEmpty()) {
            return;
        }
        float fWidth = this.mDisplayFrame.width() / rect.width();
        float fHeight = this.mDisplayFrame.height() / rect.height();
        FreeformContainerView freeformContainerView = this.mContainerView;
        PointF pointF = freeformContainerView.mPointerPosition;
        float f = pointF.x;
        if (f >= 0.0f) {
            float f2 = pointF.y;
            if (f2 >= 0.0f) {
                float f3 = (f * fWidth) + 0.5f;
                float f4 = (f2 * fHeight) + 0.5f;
                StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("[ContainerView] scalePointerPosition, new position=(", f3, ",", f4, ") scale=(");
                sbM.append(fWidth);
                sbM.append(",");
                sbM.append(fHeight);
                sbM.append(")");
                Log.i("FreeformContainer", sbM.toString());
                freeformContainerView.setPointerPosition(f3, f4, false);
            }
        }
    }
}
