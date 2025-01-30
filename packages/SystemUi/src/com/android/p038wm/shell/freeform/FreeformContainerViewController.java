package com.android.p038wm.shell.freeform;

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
import com.android.internal.widget.RecyclerView;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.common.DismissButtonView;
import com.android.p038wm.shell.freeform.FreeformContainerDismissButtonView;
import com.android.p038wm.shell.freeform.FreeformContainerFolderView;
import com.android.p038wm.shell.freeform.FreeformContainerManager;
import com.android.systemui.R;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FreeformContainerViewController {
    public FreeformContainerView mContainerView;
    public final Context mContext;
    public FreeformContainerDismissButtonView mDismissButtonView;
    public FreeformContainerFolderView mFolderView;

    /* renamed from: mH */
    public FreeformContainerManager.HandlerC3983H f449mH;
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
    public int mState = 0;
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
        List list = this.mFullscreenModeRequests;
        if (!((ArrayList) list).contains(str)) {
            Log.w("FreeformContainer", "[ViewController] " + str + " does not exist, close failed");
            return false;
        }
        ((ArrayList) list).remove(str);
        Log.i("FreeformContainer", "[ViewController] close FullscreenMode: " + str);
        if (!((ArrayList) list).isEmpty()) {
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
        this.mDismissButtonView.mDismissButtonManager.createOrUpdateWrapper();
    }

    public final void hideDismissButtonAndDismissIcon(final FreeformContainerItem freeformContainerItem, View view, Rect rect) {
        Log.i("FreeformContainer", "[ViewController] hideDismissButtonAndDismissIcon");
        Log.i("FreeformContainer", "[ViewController] hideDismissButton");
        FreeformContainerDismissButtonView freeformContainerDismissButtonView = this.mDismissButtonView;
        if (freeformContainerDismissButtonView != null) {
            FreeformContainerViewController$$ExternalSyntheticLambda1 freeformContainerViewController$$ExternalSyntheticLambda1 = this.mHideCallback;
            Log.i("FreeformContainer", "[FreeformContainerDismissButtonView] hide()");
            freeformContainerDismissButtonView.setVisibility(4);
            if (freeformContainerDismissButtonView.mDismissButtonShowing) {
                freeformContainerDismissButtonView.mDismissButtonShowing = false;
            }
            freeformContainerDismissButtonView.mDismissButtonManager.hide(new FreeformContainerDismissButtonView$$ExternalSyntheticLambda0(freeformContainerDismissButtonView, freeformContainerViewController$$ExternalSyntheticLambda1));
        }
        FreeformContainerDismissButtonView freeformContainerDismissButtonView2 = this.mDismissButtonView;
        if (freeformContainerDismissButtonView2 != null ? freeformContainerDismissButtonView2.mDismissButtonManager.mView.mIsEnterDismissButton : false) {
            Runnable runnable = new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerViewController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FreeformContainerViewController freeformContainerViewController = FreeformContainerViewController.this;
                    FreeformContainerItem freeformContainerItem2 = freeformContainerItem;
                    if (freeformContainerItem2 != null) {
                        FreeformContainerItemController freeformContainerItemController = freeformContainerViewController.mItemController;
                        freeformContainerItemController.getClass();
                        freeformContainerItem2.throwAway(freeformContainerItemController);
                        return;
                    }
                    FreeformContainerItemController freeformContainerItemController2 = freeformContainerViewController.mItemController;
                    freeformContainerItemController2.getClass();
                    Iterator it = new ArrayList(freeformContainerItemController2.mItemList).iterator();
                    while (it.hasNext()) {
                        ((FreeformContainerItem) it.next()).throwAway(freeformContainerItemController2);
                    }
                    if (CoreRune.MW_FREEFORM_MINIMIZE_SA_LOGGING) {
                        CoreSaLogger.logForAdvanced("2202");
                    }
                }
            };
            freeformContainerDismissButtonView2.mDismissingIconView = view;
            DismissButtonView dismissButtonView = freeformContainerDismissButtonView2.mDismissButtonManager.mView;
            DismissButtonView dismissButtonView2 = freeformContainerDismissButtonView2.mDismissButtonManager.mView;
            FreeformContainerDismissButtonView.AnimationAnimationListenerC39701 animationAnimationListenerC39701 = new Animation.AnimationListener(freeformContainerDismissButtonView2, view, runnable) { // from class: com.android.wm.shell.freeform.FreeformContainerDismissButtonView.1
                public final /* synthetic */ Runnable val$dismissIconRunnable;
                public final /* synthetic */ View val$dismissingIconView;

                public AnimationAnimationListenerC39701(FreeformContainerDismissButtonView freeformContainerDismissButtonView22, View view2, Runnable runnable2) {
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
            float centerX = rect.centerX();
            float centerY = rect.centerY();
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, ((dismissButtonView.getWidth() / 2.0f) + dismissButtonView.mDismissArea.left) - rect.centerX(), 0.0f, ((dismissButtonView2.getHeight() / 2.0f) + dismissButtonView2.mDismissArea.top) - rect.centerY());
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.7f, 1.0f, 0.7f, centerX, centerY);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(alphaAnimation);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(translateAnimation);
            animationSet.setInterpolator(InterpolatorUtils.SINE_OUT_60);
            animationSet.setDuration(250L);
            animationSet.setFillAfter(true);
            animationSet.setFillEnabled(true);
            animationSet.setAnimationListener(animationAnimationListenerC39701);
            view2.startAnimation(animationSet);
        }
    }

    public final void hideWindow() {
        if (this.mContainerView != null) {
            Log.i("FreeformContainer", "[ViewController] Hide Window");
            updateContainerState(0, false, true);
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
                freeformContainerDismissButtonView2.mDismissButtonManager.hide(new FreeformContainerDismissButtonView$$ExternalSyntheticLambda0(freeformContainerDismissButtonView2, freeformContainerViewController$$ExternalSyntheticLambda1));
            }
            this.f449mH.post(this.mHideContainerViewRunnable);
        }
    }

    public final boolean isDismissButtonShowing() {
        FreeformContainerDismissButtonView freeformContainerDismissButtonView = this.mDismissButtonView;
        if (freeformContainerDismissButtonView == null) {
            return false;
        }
        return freeformContainerDismissButtonView.mDismissButtonShowing;
    }

    public final boolean isPointerView() {
        return this.mState == 1;
    }

    public final boolean openFullscreenMode(String str) {
        if (this.mContainerView == null) {
            Log.i("FreeformContainer", "[ViewController] open failed: mContainerView is null");
            return false;
        }
        ArrayList arrayList = (ArrayList) this.mFullscreenModeRequests;
        if (arrayList.contains(str)) {
            Log.w("FreeformContainer", "[ViewController] " + str + " is already opened");
            return false;
        }
        arrayList.add(str);
        Log.i("FreeformContainer", "[ViewController] open FullscreenMode: ".concat(str));
        if (arrayList.size() == 1) {
            Log.i("FreeformContainer", "[ViewController] FullscreenMode Enabled");
            FreeformContainerView freeformContainerView = this.mContainerView;
            freeformContainerView.requestTransparentRegion(freeformContainerView);
        }
        return true;
    }

    public final void setFocusable(boolean z) {
        int i = ((WindowManager.LayoutParams) this.mContainerView.getLayoutParams()).flags;
        WindowManager.LayoutParams layoutParams = this.mLayoutParams;
        if (z) {
            layoutParams.flags &= -9;
        } else {
            layoutParams.flags |= 8;
        }
        if (i != layoutParams.flags) {
            this.mWindowManager.updateViewLayout(this.mContainerView, layoutParams);
            Log.i("FreeformContainer", "[ViewController] setFocusable: " + z);
        }
    }

    public final void updateContainerState(int i, boolean z, boolean z2) {
        if (this.mState != i) {
            Log.i("FreeformContainer", "[ViewController] updateContainerState: ".concat(i != 1 ? i != 2 ? "UNKNOWN" : "STATE_FOLDER" : "STATE_POINTER"));
            this.mState = i;
            Rect rect = this.mTmpBounds;
            if (i == 1) {
                this.mContainerView.mPointerView.setImportantForAccessibility(1);
                this.mContainerView.updatePointerViewVisibility(0);
                Log.d("FreeformContainer", "DPK : mState = 1");
                this.mFolderView.collapse(z);
                if (z2) {
                    this.mContainerView.animateBackgroundDim(false);
                }
                setFocusable(false);
                if (z) {
                    if (this.mContainerView.getIconViewListCount() >= 2) {
                        this.mFolderView.getTrayBounds(rect);
                        final FreeformContainerView freeformContainerView = this.mContainerView;
                        freeformContainerView.updateIconsPosition();
                        TranslateAnimation translateAnimation = new TranslateAnimation((int) (freeformContainerView.isLayoutRtl() ? ((rect.right - freeformContainerView.mPointerView.getX()) - freeformContainerView.mPointerViewSize) - freeformContainerView.mIconLeftMarginInFolder : (rect.left - freeformContainerView.mPointerView.getX()) + freeformContainerView.mIconLeftMarginInFolder), 0.0f, (int) ((rect.top - freeformContainerView.mPointerView.getY()) + freeformContainerView.mIconItemTopMarginInFolder), 0.0f);
                        translateAnimation.setDuration(283L);
                        translateAnimation.setInterpolator(InterpolatorUtils.ONE_EASING);
                        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView.6
                            public AnimationAnimationListenerC39896() {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationEnd(Animation animation) {
                                FreeformContainerView.this.mPointerGroupView.setElevation(0.0f);
                                FreeformContainerView.this.mViewController.closeFullscreenMode("fullscreen_mode_request_folder");
                                FreeformContainerView.m2749$$Nest$msettleDownPointerEffect(FreeformContainerView.this);
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
                        final FreeformContainerFolderView freeformContainerFolderView = this.mFolderView;
                        freeformContainerFolderView.mTrayView.setVisibility(0);
                        Animation loadAnimation = AnimationUtils.loadAnimation(freeformContainerFolderView.mContext, R.anim.freeform_container_tray_view_fade_out);
                        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.5
                            public AnimationAnimationListenerC39765() {
                            }

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
                        freeformContainerFolderView.mTrayView.startAnimation(loadAnimation);
                    } else {
                        FreeformContainerView freeformContainerView2 = this.mContainerView;
                        freeformContainerView2.f448mH.post(new FreeformContainerView$$ExternalSyntheticLambda0(freeformContainerView2, 0));
                    }
                }
            } else if (i == 2) {
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
                this.mFolderView.calculateFolderSize();
                FreeformContainerView freeformContainerView4 = this.mContainerView;
                FreeformContainerFolderTrayView freeformContainerFolderTrayView = this.mFolderView.mTrayView;
                int i3 = freeformContainerFolderTrayView.mWidth;
                int i4 = freeformContainerFolderTrayView.mHeight;
                PointF pointF = this.mTmpPointF;
                pointF.set(freeformContainerView4.mPointerView.getX() - ((i3 - freeformContainerView4.mPointerViewSize) / 2.0f), freeformContainerView4.mPointerView.getY() - ((i4 - freeformContainerView4.mPointerViewSize) / 2.0f));
                FreeformContainerFolderView freeformContainerFolderView2 = this.mFolderView;
                Rect rect2 = freeformContainerFolderView2.mTmpBounds;
                int i5 = (int) pointF.x;
                int i6 = (int) pointF.y;
                FreeformContainerFolderTrayView freeformContainerFolderTrayView2 = freeformContainerFolderView2.mTrayView;
                rect2.set(i5, i6, freeformContainerFolderTrayView2.mWidth + i5, freeformContainerFolderTrayView2.mHeight + i6);
                freeformContainerFolderView2.mViewController.adjustPositionInDisplay(-freeformContainerFolderView2.mPaddingLeft, -freeformContainerFolderView2.mPaddingRight, freeformContainerFolderView2.mTmpBounds);
                freeformContainerFolderView2.setX(freeformContainerFolderView2.mTmpBounds.left);
                freeformContainerFolderView2.setY(freeformContainerFolderView2.mTmpBounds.top);
                freeformContainerFolderView2.setZ(freeformContainerFolderView2.mTrayView.getZ() + 1.0f);
                FreeformContainerFolderTrayView freeformContainerFolderTrayView3 = freeformContainerFolderView2.mTrayView;
                Rect rect3 = freeformContainerFolderView2.mTmpBounds;
                int i7 = rect3.left;
                int i8 = rect3.top;
                freeformContainerFolderTrayView3.setX(i7);
                freeformContainerFolderTrayView3.setY(i8);
                Log.i("FreeformContainer", "[FolderView] setFolderPosition: x=" + freeformContainerFolderView2.mTmpBounds.left + ", y=" + freeformContainerFolderView2.mTmpBounds.top + ", view=" + freeformContainerFolderView2);
                this.mFolderView.getTrayBounds(rect);
                final FreeformContainerView freeformContainerView5 = this.mContainerView;
                TranslateAnimation translateAnimation2 = new TranslateAnimation(0.0f, (int) (freeformContainerView5.isLayoutRtl() ? ((rect.right - freeformContainerView5.mPointerView.getX()) - freeformContainerView5.mPointerViewSize) - freeformContainerView5.mIconLeftMarginInFolder : (rect.left - freeformContainerView5.mPointerView.getX()) + freeformContainerView5.mIconLeftMarginInFolder), 0.0f, (int) ((rect.top - freeformContainerView5.mPointerView.getY()) + freeformContainerView5.mIconItemTopMarginInFolder));
                translateAnimation2.setDuration(283L);
                translateAnimation2.setInterpolator(InterpolatorUtils.ONE_EASING);
                translateAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView.7
                    public AnimationAnimationListenerC39907() {
                    }

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
                FreeformContainerFolderView freeformContainerFolderView3 = this.mFolderView;
                if (freeformContainerFolderView3.mViewController.openFullscreenMode("fullscreen_mode_request_folder") && !freeformContainerFolderView3.mIsExpanded) {
                    freeformContainerFolderView3.calculateFolderSize();
                    freeformContainerFolderView3.setAdapter(freeformContainerFolderView3.mAdapter);
                    freeformContainerFolderView3.mAdapter.notifyDataSetChanged();
                    freeformContainerFolderView3.mIsExpanded = true;
                    freeformContainerFolderView3.setVisibility(0);
                    if (freeformContainerFolderView3.mVisibleIconCount < freeformContainerFolderView3.mAdapter.getItemCount()) {
                        freeformContainerFolderView3.scrollToPosition(0);
                    }
                    if (z) {
                        freeformContainerFolderView3.mIsExpandAnimating = true;
                        freeformContainerFolderView3.setHorizontalScrollBarEnabled(false);
                        int itemCount = freeformContainerFolderView3.mAdapter.getItemCount();
                        for (int i9 = 0; i9 < itemCount; i9++) {
                            FreeformContainerFolderView.FolderItemViewHolder folderItemViewHolder = (FreeformContainerFolderView.FolderItemViewHolder) freeformContainerFolderView3.findViewHolderForAdapterPosition(i9);
                            if (folderItemViewHolder != null) {
                                ((RecyclerView.ViewHolder) folderItemViewHolder).itemView.setVisibility(4);
                            }
                        }
                        freeformContainerFolderView3.mTrayView.setVisibility(0);
                        freeformContainerFolderView3.mTrayView.startAnimation(AnimationUtils.loadAnimation(freeformContainerFolderView3.mContext, R.anim.freeform_container_tray_view_fade_in));
                        freeformContainerFolderView3.f445mH.postDelayed(freeformContainerFolderView3.mOpenFolderRunnable, 33L);
                    }
                    if (CoreRune.MW_FREEFORM_MINIMIZED_PREVIEW) {
                        freeformContainerFolderView3.mFreeformThumbnailView = (FreeformThumbnailView) freeformContainerFolderView3.mLayoutInflater.inflate(R.layout.freeform_thumbnail, (ViewGroup) null);
                    }
                }
                this.mFolderView.performAccessibilityAction(64, (Bundle) null);
            }
            this.mContainerView.updateTouchableRegion();
        }
    }

    public final void updateDisplayFrame(boolean z) {
        Rect rect = this.mDisplayFrame;
        Rect rect2 = new Rect(rect);
        DisplayInfo displayInfo = new DisplayInfo();
        Context context = this.mContext;
        context.getDisplay().getDisplayInfo(displayInfo);
        rect.set(0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
        Rect rect3 = this.mNonDecorDisplayFrame;
        rect3.set(rect);
        Rect rect4 = new Rect();
        FreeformContainerManager.getInstance(context).getClass();
        FreeformContainerManager.getStableInsets(rect4);
        rect3.inset(rect4);
        if (!z || rect2.isEmpty()) {
            return;
        }
        float width = rect.width() / rect2.width();
        float height = rect.height() / rect2.height();
        FreeformContainerView freeformContainerView = this.mContainerView;
        PointF pointF = freeformContainerView.mPointerPosition;
        float f = pointF.x;
        if (f >= 0.0f) {
            float f2 = pointF.y;
            if (f2 >= 0.0f) {
                float f3 = (f * width) + 0.5f;
                float f4 = (f2 * height) + 0.5f;
                StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("[ContainerView] scalePointerPosition, new position=(", f3, ",", f4, ") scale=(");
                m88m.append(width);
                m88m.append(",");
                m88m.append(height);
                m88m.append(")");
                Log.i("FreeformContainer", m88m.toString());
                freeformContainerView.setPointerPosition(f3, f4, false);
            }
        }
    }
}
