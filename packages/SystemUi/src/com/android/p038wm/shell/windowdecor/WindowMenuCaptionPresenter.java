package com.android.p038wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.android.internal.graphics.ColorUtils;
import com.android.p038wm.shell.windowdecor.MultitaskingWindowDecorViewModel;
import com.android.p038wm.shell.windowdecor.animation.CaptionButtonVisibility;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WindowMenuCaptionPresenter extends WindowMenuPresenter {
    public WindowMenuItemView mBackButton;
    public final ViewGroup[] mButtonSet;
    public ColorStateList mButtonTintColor;
    public final CaptionButtonVisibility[] mButtonVisibility;
    public WindowMenuDivider mDivider;
    public boolean mIsTaskFocused;
    public WindowMenuItemView mMoreButton;
    public View mMoveDisplayButtonSet;
    public boolean mShowPrimaryButtonSet;
    public RunnableC41881 mUnpinAnimRunnable;
    public WindowMenuAnimationView mUnpinButton;

    /* JADX WARN: Removed duplicated region for block: B:30:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d8  */
    /* JADX WARN: Type inference failed for: r0v28, types: [com.android.wm.shell.windowdecor.WindowMenuCaptionPresenter$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public WindowMenuCaptionPresenter(Context context, ActivityManager.RunningTaskInfo runningTaskInfo, View view, MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener, float f, boolean z) {
        super(context, runningTaskInfo, runningTaskInfo.getWindowingMode(), captionTouchEventListener, view, f, z);
        int i;
        SparseArray sparseArray;
        boolean z2;
        View view2;
        MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener2;
        boolean z3;
        ViewGroup[] viewGroupArr;
        int i2;
        WindowMenuItemView windowMenuItemView;
        boolean z4;
        this.mIsTaskFocused = true;
        this.mButtonTintColor = getButtonTintColor();
        ViewGroup[] viewGroupArr2 = {(ViewGroup) view.findViewById(R.id.primary_button_set), (ViewGroup) view.findViewById(R.id.secondary_button_set)};
        this.mButtonSet = viewGroupArr2;
        int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.sec_decor_button_anim_trans_x);
        int i3 = 0;
        this.mButtonVisibility = new CaptionButtonVisibility[]{new CaptionButtonVisibility(viewGroupArr2[0], viewGroupArr2[1], dimensionPixelOffset, true), new CaptionButtonVisibility(viewGroupArr2[1], viewGroupArr2[0], dimensionPixelOffset, false)};
        int length = viewGroupArr2.length;
        int i4 = 0;
        while (true) {
            i = this.mWindowingMode;
            sparseArray = this.mButtons;
            z2 = this.mIsDexEnabled;
            view2 = this.mRootView;
            captionTouchEventListener2 = this.mListener;
            if (i4 >= length) {
                break;
            }
            ViewGroup viewGroup = viewGroupArr2[i4];
            if (viewGroup != null) {
                int i5 = i3;
                while (i5 < viewGroup.getChildCount()) {
                    View childAt = viewGroup.getChildAt(i5);
                    if (childAt instanceof WindowMenuItemView) {
                        WindowMenuItemView windowMenuItemView2 = (WindowMenuItemView) childAt;
                        int id = windowMenuItemView2.getId();
                        if (WindowMenuPresenter.isButtonVisible(id, i, this.mTaskInfo.isRotationButtonVisible, this.mIsDisplayAdded)) {
                            if (id == R.id.opacity_window && !z2) {
                                setupOpacitySlider();
                            }
                            viewGroupArr = viewGroupArr2;
                            if (windowMenuItemView2.getId() == R.id.split_window) {
                                int multiSplitFlags = MultiWindowManager.getInstance().getMultiSplitFlags();
                                if (MultiWindowUtils.isSplitEnabled(multiSplitFlags)) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
                                    i2 = length;
                                    if (runningTaskInfo2.resizeMode != 10 && runningTaskInfo2.supportsMultiWindow) {
                                        z4 = true;
                                        setSplitButtonDrawable(windowMenuItemView2, multiSplitFlags);
                                        windowMenuItemView2.setEnabled(z4);
                                        windowMenuItemView2.setAlpha(!z4 ? 1.0f : 0.4f);
                                    }
                                } else {
                                    i2 = length;
                                }
                                z4 = false;
                                setSplitButtonDrawable(windowMenuItemView2, multiSplitFlags);
                                windowMenuItemView2.setEnabled(z4);
                                windowMenuItemView2.setAlpha(!z4 ? 1.0f : 0.4f);
                            } else {
                                i2 = length;
                            }
                            if (id == R.id.freeform_window && this.mTaskInfo.resizeMode == 10) {
                                windowMenuItemView2.setEnabled(false);
                            }
                            if (CoreRune.MT_NEW_DEX_TASK_PINNING && this.mIsNewDexMode && id == R.id.window_pin_window && this.mTaskInfo.getConfiguration().windowConfiguration.isAlwaysOnTop() && (windowMenuItemView = (WindowMenuItemView) view2.findViewById(id)) != null) {
                                windowMenuItemView.mShowIconBackground = true;
                            }
                            windowMenuItemView2.setOnClickListener(captionTouchEventListener2);
                            windowMenuItemView2.setOnTouchListener(captionTouchEventListener2);
                            windowMenuItemView2.setImageTintList(this.mButtonTintColor);
                            windowMenuItemView2.setTaskFocusState(true);
                            sparseArray.put(windowMenuItemView2.getId(), windowMenuItemView2);
                        } else {
                            viewGroupArr = viewGroupArr2;
                            i2 = length;
                            windowMenuItemView2.setVisibility(8);
                            i5++;
                            viewGroupArr2 = viewGroupArr;
                            length = i2;
                        }
                    } else {
                        viewGroupArr = viewGroupArr2;
                        i2 = length;
                    }
                    i5++;
                    viewGroupArr2 = viewGroupArr;
                    length = i2;
                }
            }
            i4++;
            viewGroupArr2 = viewGroupArr2;
            length = length;
            i3 = 0;
        }
        WindowMenuItemView windowMenuItemView3 = (WindowMenuItemView) view2.findViewById(R.id.more_window);
        this.mMoreButton = windowMenuItemView3;
        if (windowMenuItemView3 != null) {
            if (z2 || this.mIsDisplayAdded) {
                windowMenuItemView3.setOnClickListener(captionTouchEventListener2);
            } else {
                windowMenuItemView3.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.WindowMenuCaptionPresenter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        WindowMenuCaptionPresenter windowMenuCaptionPresenter = WindowMenuCaptionPresenter.this;
                        boolean z5 = !windowMenuCaptionPresenter.mShowPrimaryButtonSet;
                        windowMenuCaptionPresenter.mShowPrimaryButtonSet = z5;
                        WindowMenuItemView windowMenuItemView4 = windowMenuCaptionPresenter.mMoreButton;
                        if (windowMenuItemView4 != null) {
                            windowMenuItemView4.mShowIconBackground = !z5;
                        }
                        CaptionButtonVisibility[] captionButtonVisibilityArr = windowMenuCaptionPresenter.mButtonVisibility;
                        CaptionButtonVisibility captionButtonVisibility = captionButtonVisibilityArr[0];
                        AnimatorSet animatorSet = captionButtonVisibility.mVisibleAnim;
                        if (animatorSet.isRunning()) {
                            animatorSet.cancel();
                        }
                        AnimatorSet animatorSet2 = captionButtonVisibility.mInvisibleAnim;
                        if (animatorSet2.isRunning()) {
                            animatorSet2.cancel();
                        }
                        CaptionButtonVisibility captionButtonVisibility2 = captionButtonVisibilityArr[1];
                        AnimatorSet animatorSet3 = captionButtonVisibility2.mVisibleAnim;
                        if (animatorSet3.isRunning()) {
                            animatorSet3.cancel();
                        }
                        AnimatorSet animatorSet4 = captionButtonVisibility2.mInvisibleAnim;
                        if (animatorSet4.isRunning()) {
                            animatorSet4.cancel();
                        }
                        CaptionButtonVisibility captionButtonVisibility3 = captionButtonVisibilityArr[!windowMenuCaptionPresenter.mShowPrimaryButtonSet ? 1 : 0];
                        captionButtonVisibility3.mVisibleAnim.start();
                        captionButtonVisibility3.mInvisibleAnim.start();
                        windowMenuCaptionPresenter.setTaskFocusState(true);
                    }
                });
                this.mMoreButton.setOnTouchListener(captionTouchEventListener2);
            }
            this.mMoreButton.setTaskFocusState(true);
            this.mMoreButton.setImageTintList(this.mButtonTintColor);
            this.mMoreButton.setVisibility(z2 ? 8 : 0);
            sparseArray.put(this.mMoreButton.getId(), this.mMoreButton);
        }
        WindowMenuAnimationView windowMenuAnimationView = (WindowMenuAnimationView) view2.findViewById(R.id.caption_unpin_window);
        this.mUnpinButton = windowMenuAnimationView;
        if (windowMenuAnimationView != null) {
            windowMenuAnimationView.createLottieTask("mw_popup_option_btn_header_handle.json");
            this.mUnpinButton.setOnClickListener(captionTouchEventListener2);
            this.mUnpinButton.setOnTouchListener(captionTouchEventListener2);
            this.mUnpinButton.updateNightMode(this.mIsNightMode);
            this.mUnpinAnimRunnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.WindowMenuCaptionPresenter.1
                @Override // java.lang.Runnable
                public final void run() {
                    WindowMenuCaptionPresenter.this.mUnpinButton.playAnimation();
                }
            };
            CaptionButtonVisibility[] captionButtonVisibilityArr = this.mButtonVisibility;
            captionButtonVisibilityArr[0].mVisibleAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.WindowMenuCaptionPresenter.2
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator, boolean z5) {
                    WindowMenuAnimationView windowMenuAnimationView2 = WindowMenuCaptionPresenter.this.mUnpinButton;
                    if (windowMenuAnimationView2 != null) {
                        windowMenuAnimationView2.setProgressInternal(0.0f, true);
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator, boolean z5) {
                    WindowMenuAnimationView windowMenuAnimationView2 = WindowMenuCaptionPresenter.this.mUnpinButton;
                    if (windowMenuAnimationView2 != null) {
                        LottieValueAnimator lottieValueAnimator = windowMenuAnimationView2.lottieDrawable.animator;
                        if (lottieValueAnimator == null ? false : lottieValueAnimator.running) {
                            windowMenuAnimationView2.cancelAnimation();
                        }
                    }
                }
            });
            captionButtonVisibilityArr[1].mVisibleAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.WindowMenuCaptionPresenter.3
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator, boolean z5) {
                    RunnableC41881 runnableC41881;
                    WindowMenuCaptionPresenter windowMenuCaptionPresenter = WindowMenuCaptionPresenter.this;
                    if (windowMenuCaptionPresenter.mUnpinButton == null || windowMenuCaptionPresenter.mShowPrimaryButtonSet || (runnableC41881 = windowMenuCaptionPresenter.mUnpinAnimRunnable) == null) {
                        return;
                    }
                    windowMenuCaptionPresenter.mRootView.postDelayed(runnableC41881, 50L);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator, boolean z5) {
                }
            });
            WindowMenuAnimationView windowMenuAnimationView2 = this.mUnpinButton;
            if (!windowMenuAnimationView2.mIsTaskFocused) {
                windowMenuAnimationView2.mIsTaskFocused = true;
                windowMenuAnimationView2.applyIconColor();
            }
        }
        WindowMenuDivider windowMenuDivider = (WindowMenuDivider) view2.findViewById(R.id.divider);
        this.mDivider = windowMenuDivider;
        if (windowMenuDivider != null) {
            setDividerColor(windowMenuDivider);
        }
        if (z2) {
            WindowMenuItemView windowMenuItemView4 = (WindowMenuItemView) view2.findViewById(R.id.back_window);
            this.mBackButton = windowMenuItemView4;
            if (windowMenuItemView4 != null) {
                windowMenuItemView4.setImageTintList(this.mButtonTintColor);
                z3 = true;
                this.mBackButton.setTaskFocusState(true);
                this.mBackButton.setOnClickListener(captionTouchEventListener2);
                this.mBackButton.setOnTouchListener(captionTouchEventListener2);
                sparseArray.put(this.mBackButton.getId(), this.mBackButton);
            } else {
                z3 = true;
            }
            if (i == z3) {
                WindowMenuItemView windowMenuItemView5 = (WindowMenuItemView) view2.findViewById(R.id.opacity_window);
                if (windowMenuItemView5 != null) {
                    windowMenuItemView5.setEnabled(false);
                    windowMenuItemView5.setAlpha(0.4f);
                }
                changePinButtonDisable(z3);
            }
        }
        View findViewById = view2.findViewById(R.id.move_display_button_set);
        this.mMoveDisplayButtonSet = findViewById;
        if (findViewById != null) {
            setupAddDisplayButton(this.mIsDisplayAdded);
        }
        if (this.mIsDexEnabled) {
            view.setOnHoverListener(this.mListener);
        }
    }

    public static void measureChild(View view) {
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        view.forceLayout();
        view.measure(makeMeasureSpec, makeMeasureSpec);
    }

    public final void adjustOverflowButton(int i) {
        boolean z;
        View view;
        View view2;
        View view3;
        View view4;
        View view5;
        SparseArray sparseArray = this.mButtons;
        WindowMenuItemView windowMenuItemView = (WindowMenuItemView) sparseArray.get(R.id.close_window);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.decor_button_padding_large_desktop);
        boolean z2 = this.mIsNewDexMode;
        if (windowMenuItemView != null && !z2 && windowMenuItemView.getPaddingEnd() != dimensionPixelSize) {
            windowMenuItemView.setPaddingRelative(windowMenuItemView.getPaddingStart(), windowMenuItemView.getPaddingTop(), dimensionPixelSize, windowMenuItemView.getPaddingBottom());
            windowMenuItemView.updateRippleBackground();
        }
        ViewGroup[] viewGroupArr = this.mButtonSet;
        viewGroupArr[0].setVisibility(0);
        viewGroupArr[1].setVisibility(0);
        if (this.mIsDisplayAdded && (view5 = this.mMoveDisplayButtonSet) != null) {
            view5.setVisibility(0);
        }
        View view6 = (View) sparseArray.get(R.id.back_window);
        if (view6 != null && view6.getVisibility() == 0) {
            measureChild(view6);
            i -= view6.getMeasuredWidth();
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= viewGroupArr[0].getChildCount()) {
                z = false;
                break;
            }
            View childAt = viewGroupArr[0].getChildAt(i2);
            if (childAt.getVisibility() == 0) {
                measureChild(childAt);
                i3 += childAt.getMeasuredWidth();
                if (i < i3) {
                    z = true;
                    break;
                }
            }
            i2++;
        }
        View view7 = (View) sparseArray.get(R.id.more_window);
        if (view7 == null) {
            return;
        }
        if (z2) {
            measureChild(view7);
            i -= view7.getMeasuredWidth();
            if (i < 0) {
                z = true;
            }
        }
        if (this.mIsDisplayAdded && (view4 = this.mMoveDisplayButtonSet) != null) {
            measureChild(view4);
            i -= this.mMoveDisplayButtonSet.getMeasuredWidth();
            if (i < 0) {
                z = true;
            }
        }
        if (z) {
            view7.setVisibility(0);
            viewGroupArr[0].setVisibility(8);
            viewGroupArr[1].setVisibility(8);
            if (!this.mIsDisplayAdded || (view3 = this.mMoveDisplayButtonSet) == null) {
                return;
            }
            view3.setVisibility(8);
            return;
        }
        int i4 = i - i3;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            if (i5 >= viewGroupArr[1].getChildCount()) {
                break;
            }
            View childAt2 = viewGroupArr[1].getChildAt(i5);
            if (childAt2.getVisibility() == 0) {
                measureChild(childAt2);
                i6 += childAt2.getMeasuredWidth();
                if (i4 < i6) {
                    z = true;
                    break;
                }
            }
            i5++;
        }
        if (!z) {
            view7.setVisibility(z2 ? 0 : 8);
            return;
        }
        view7.setVisibility(0);
        measureChild(view7);
        int measuredWidth = view7.getMeasuredWidth();
        if (!z2 && i4 < measuredWidth) {
            viewGroupArr[0].setVisibility(8);
            viewGroupArr[1].setVisibility(8);
            if (!this.mIsDisplayAdded || (view2 = this.mMoveDisplayButtonSet) == null) {
                return;
            }
            view2.setVisibility(8);
            return;
        }
        WindowMenuItemView windowMenuItemView2 = (WindowMenuItemView) sparseArray.get(R.id.close_window);
        if (windowMenuItemView2 != null) {
            windowMenuItemView2.setPadding(windowMenuItemView2.getPaddingStart(), windowMenuItemView2.getPaddingTop(), windowMenuItemView2.getPaddingStart(), windowMenuItemView2.getPaddingBottom());
            windowMenuItemView2.updateRippleBackground();
        }
        viewGroupArr[1].setVisibility(8);
        if (!this.mIsDisplayAdded || (view = this.mMoveDisplayButtonSet) == null) {
            return;
        }
        view.setVisibility(8);
    }

    public final void changePinButtonDisable(boolean z) {
        WindowMenuItemView windowMenuItemView = (WindowMenuItemView) this.mRootView.findViewById(R.id.window_pin_window);
        if (windowMenuItemView != null) {
            windowMenuItemView.setEnabled(!z);
            windowMenuItemView.setAlpha(!z ? 1.0f : 0.4f);
        }
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowMenuPresenter
    public final ColorStateList getButtonTintColor() {
        ColorStateList buttonTintColor = super.getButtonTintColor();
        if (!CaptionGlobalState.COLOR_THEME_ENABLED) {
            return buttonTintColor;
        }
        int defaultColor = buttonTintColor.getDefaultColor();
        return new ColorStateList(new int[][]{new int[]{R.attr.state_task_focused}, new int[0]}, new int[]{defaultColor, ColorUtils.setAlphaComponent(defaultColor, 102)});
    }

    public final WindowMenuItemView getRotationButton() {
        for (ViewGroup viewGroup : this.mButtonSet) {
            if (viewGroup != null) {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if (childAt instanceof WindowMenuItemView) {
                        WindowMenuItemView windowMenuItemView = (WindowMenuItemView) childAt;
                        if (windowMenuItemView.getId() == R.id.rotate_window) {
                            return windowMenuItemView;
                        }
                    }
                }
            }
        }
        return null;
    }

    public final void setTaskFocusState(boolean z) {
        if (this.mIsTaskFocused != z) {
            this.mIsTaskFocused = z;
            int i = 0;
            while (true) {
                SparseArray sparseArray = this.mButtons;
                if (i >= sparseArray.size()) {
                    break;
                }
                ((WindowMenuItemView) sparseArray.valueAt(i)).setTaskFocusState(z);
                i++;
            }
            WindowMenuAnimationView windowMenuAnimationView = this.mUnpinButton;
            if (windowMenuAnimationView == null || windowMenuAnimationView.mIsTaskFocused == z) {
                return;
            }
            windowMenuAnimationView.mIsTaskFocused = z;
            windowMenuAnimationView.applyIconColor();
        }
    }

    public final void setupAddDisplayButton(boolean z) {
        View view = this.mMoveDisplayButtonSet;
        if (view == null) {
            return;
        }
        view.setVisibility(z ? 0 : 8);
        if (z) {
            WindowMenuItemView windowMenuItemView = (WindowMenuItemView) this.mMoveDisplayButtonSet.findViewById(R.id.move_display_window);
            if (windowMenuItemView != null) {
                windowMenuItemView.setImageTintList(this.mButtonTintColor);
                windowMenuItemView.setTaskFocusState(true);
                MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener = this.mListener;
                windowMenuItemView.setOnClickListener(captionTouchEventListener);
                windowMenuItemView.setOnTouchListener(captionTouchEventListener);
                this.mButtons.put(windowMenuItemView.getId(), windowMenuItemView);
                windowMenuItemView.setContentDescription(this.mContext.getString(this.mTaskInfo.getDisplayId() == 0 ? R.string.sec_decor_button_operation_move_display : R.string.sec_decor_button_operation_move_device));
            }
            WindowMenuDivider windowMenuDivider = (WindowMenuDivider) this.mRootView.findViewById(R.id.move_display_divider);
            if (windowMenuDivider != null) {
                setDividerColor(windowMenuDivider);
            }
        }
    }

    public final void updateButtonColor() {
        this.mButtonTintColor = getButtonTintColor();
        int i = 0;
        while (true) {
            SparseArray sparseArray = this.mButtons;
            if (i >= sparseArray.size()) {
                break;
            }
            ((WindowMenuItemView) sparseArray.valueAt(i)).setImageTintList(this.mButtonTintColor);
            i++;
        }
        WindowMenuAnimationView windowMenuAnimationView = this.mUnpinButton;
        if (windowMenuAnimationView != null) {
            windowMenuAnimationView.updateNightMode(this.mIsNightMode);
            this.mUnpinButton.applyIconColor();
        }
        WindowMenuDivider windowMenuDivider = this.mDivider;
        if (windowMenuDivider != null) {
            setDividerColor(windowMenuDivider);
        }
    }
}
