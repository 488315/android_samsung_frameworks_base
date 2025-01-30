package com.android.wm.shell.windowdecor;

import android.animation.AnimatorSet;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel;
import com.android.wm.shell.windowdecor.animation.CaptionAnimationUtils;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WindowMenuPopupPresenter extends WindowMenuPresenter {
    public int mAnimDirection;
    public AnimatorSet mHideAnimation;
    public RunnableC41911 mPinAnimRunnable;
    public WindowMenuAnimationView mPinButton;
    public AnimatorSet mShowAnimation;
    public final int mWidth;

    /* JADX WARN: Type inference failed for: r9v12, types: [com.android.wm.shell.windowdecor.WindowMenuPopupPresenter$1] */
    public WindowMenuPopupPresenter(Context context, ActivityManager.RunningTaskInfo runningTaskInfo, View view, MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener, float f, int i, boolean z) {
        super(context, runningTaskInfo, runningTaskInfo.getWindowingMode(), captionTouchEventListener, view, f, z);
        MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener2;
        this.mWidth = i;
        createPopupAnimation(runningTaskInfo, true);
        View view2 = this.mRootView;
        ViewGroup viewGroup = (ViewGroup) view2.findViewById(R.id.button_container);
        if (viewGroup == null) {
            return;
        }
        ColorStateList buttonTintColor = getButtonTintColor();
        int i2 = 0;
        while (true) {
            int childCount = viewGroup.getChildCount();
            captionTouchEventListener2 = this.mListener;
            if (i2 >= childCount) {
                break;
            }
            View childAt = viewGroup.getChildAt(i2);
            if (childAt instanceof WindowMenuItemView) {
                WindowMenuItemView windowMenuItemView = (WindowMenuItemView) childAt;
                int id = windowMenuItemView.getId();
                if (WindowMenuPresenter.isButtonVisible(id, this.mWindowingMode, false, false)) {
                    if (id == R.id.opacity_window) {
                        setupOpacitySlider();
                    } else if (id == R.id.split_window) {
                        int multiSplitFlags = MultiWindowManager.getInstance().getMultiSplitFlags();
                        boolean isSplitEnabled = MultiWindowUtils.isSplitEnabled(multiSplitFlags);
                        if (CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE && isSplitEnabled) {
                            ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
                            isSplitEnabled = runningTaskInfo2.resizeMode != 10 && runningTaskInfo2.supportsMultiWindow;
                        }
                        setSplitButtonDrawable(windowMenuItemView, multiSplitFlags);
                        windowMenuItemView.setEnabled(isSplitEnabled);
                        windowMenuItemView.setAlpha(isSplitEnabled ? 1.0f : 0.4f);
                    } else if (CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE && this.mIsNewDexMode && id == R.id.window_pin_window) {
                        changePinButtonIconBackground(this.mTaskInfo.getConfiguration().windowConfiguration.isAlwaysOnTop());
                    }
                    windowMenuItemView.setOnTouchListener(captionTouchEventListener2);
                    windowMenuItemView.setOnClickListener(captionTouchEventListener2);
                    windowMenuItemView.setOnHoverListener(captionTouchEventListener2);
                    windowMenuItemView.setImageTintList(buttonTintColor);
                    this.mButtons.put(id, windowMenuItemView);
                } else {
                    windowMenuItemView.setVisibility(8);
                }
            }
            i2++;
        }
        WindowMenuAnimationView windowMenuAnimationView = (WindowMenuAnimationView) view2.findViewById(R.id.caption_pin_window);
        this.mPinButton = windowMenuAnimationView;
        if (windowMenuAnimationView != null) {
            windowMenuAnimationView.createLottieTask("mw_popup_option_btn_handle_header.json");
            this.mPinButton.setOnClickListener(captionTouchEventListener2);
            this.mPinButton.setOnTouchListener(captionTouchEventListener2);
            this.mPinButton.setOnHoverListener(captionTouchEventListener2);
            this.mPinButton.updateNightMode(this.mIsNightMode);
            this.mPinAnimRunnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.WindowMenuPopupPresenter.1
                @Override // java.lang.Runnable
                public final void run() {
                    WindowMenuAnimationView windowMenuAnimationView2 = WindowMenuPopupPresenter.this.mPinButton;
                    if (windowMenuAnimationView2 != null) {
                        windowMenuAnimationView2.playAnimation();
                    }
                }
            };
        }
        WindowMenuDivider windowMenuDivider = (WindowMenuDivider) view2.findViewById(R.id.divider);
        if (windowMenuDivider != null) {
            setDividerColor(windowMenuDivider);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0030, code lost:
    
        if ((r9.right - r0) > r8.mContext.getResources().getDisplayMetrics().widthPixels) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void createPopupAnimation(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        int i;
        if (runningTaskInfo.getWindowingMode() == 5) {
            Rect bounds = runningTaskInfo.getConfiguration().windowConfiguration.getBounds();
            i = 2;
            int width = (bounds.width() - this.mWidth) / 2;
            if (bounds.left + width < 0) {
                i = 1;
            }
            if (z && i == this.mAnimDirection) {
                return;
            }
            this.mAnimDirection = i;
            int i2 = i;
            this.mShowAnimation = CaptionAnimationUtils.createMenuPopupAnimatorSet(this.mContext, this.mRootView, true, this.mWindowingMode, i2, this.mWidth);
            this.mHideAnimation = CaptionAnimationUtils.createMenuPopupAnimatorSet(this.mContext, this.mRootView, false, this.mWindowingMode, i2, this.mWidth);
        }
        i = 0;
        if (z) {
        }
        this.mAnimDirection = i;
        int i22 = i;
        this.mShowAnimation = CaptionAnimationUtils.createMenuPopupAnimatorSet(this.mContext, this.mRootView, true, this.mWindowingMode, i22, this.mWidth);
        this.mHideAnimation = CaptionAnimationUtils.createMenuPopupAnimatorSet(this.mContext, this.mRootView, false, this.mWindowingMode, i22, this.mWidth);
    }

    public final boolean needRecreateHandleMenu(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Rect bounds = runningTaskInfo.getConfiguration().windowConfiguration.getBounds();
        Rect bounds2 = this.mTaskInfo.getConfiguration().windowConfiguration.getBounds();
        return (bounds.width() == bounds2.width() && bounds.height() == bounds2.height() && MultiWindowUtils.isNightMode(runningTaskInfo) == MultiWindowUtils.isNightMode(this.mTaskInfo)) ? false : true;
    }

    public final void setFreeformButtonEnabled(boolean z) {
        WindowMenuItemView windowMenuItemView = (WindowMenuItemView) this.mRootView.findViewById(R.id.freeform_window);
        if (windowMenuItemView != null) {
            windowMenuItemView.setEnabled(z);
            windowMenuItemView.setAlpha(z ? 1.0f : 0.4f);
            windowMenuItemView.invalidate();
        }
    }
}
