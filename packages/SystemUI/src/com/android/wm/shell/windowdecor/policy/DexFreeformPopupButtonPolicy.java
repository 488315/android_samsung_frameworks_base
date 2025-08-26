package com.android.wm.shell.windowdecor.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.windowdecor.MenuPopupAnimator;
import com.android.wm.shell.windowdecor.widget.DesktopCaptionButton;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class DexFreeformPopupButtonPolicy extends PopupButtonPolicy {
    public final boolean mIsDefaultDisplay;
    public final boolean mRestartMenuEnabled;
    public final boolean mShouldShowRestartNotification;

    public DexFreeformPopupButtonPolicy(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, DisplayController displayController, boolean z) {
        super(runningTaskInfo, context, displayController);
        this.mRestartMenuEnabled = false;
        this.mIsDefaultDisplay = runningTaskInfo.displayId == 0;
        if (CoreRune.MW_CAPTION_DESKTOP_RESTART) {
            this.mRestartMenuEnabled = runningTaskInfo.appCompatTaskInfo.isRestartMenuEnabledForDisplayMove();
            this.mShouldShowRestartNotification = z;
        }
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final void animateOpenMenu() {
        MenuPopupAnimator menuPopupAnimator = this.mMenuPopupAnimator;
        if (menuPopupAnimator == null) {
            return;
        }
        menuPopupAnimator.animateOpen(this.mAnimButton);
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int calculatePopupHeight() {
        if (this.mRootView == null) {
            return 0;
        }
        int measuredHeight = 0;
        for (int i = 0; i < this.mRootView.getChildCount(); i++) {
            View childAt = this.mRootView.getChildAt(i);
            if (childAt instanceof DesktopCaptionButton) {
                DesktopCaptionButton desktopCaptionButton = (DesktopCaptionButton) childAt;
                if (desktopCaptionButton.getVisibility() == 0) {
                    desktopCaptionButton.mRootView.measure(View.MeasureSpec.makeMeasureSpec(this.mPopupWidth, Integer.MIN_VALUE), 0);
                    measuredHeight += desktopCaptionButton.getMeasuredHeight();
                }
            }
        }
        return measuredHeight;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int calculatePopupWidth() {
        if (this.mRootView == null) {
            return 0;
        }
        Resources resources = ((PopupButtonPolicy) this).mContext.getResources();
        int iLoadDimensionPixelSize = WindowDecorButtonPolicy.loadDimensionPixelSize(resources, R.dimen.mw_desktop_popup_min_width);
        int iLoadDimensionPixelSize2 = WindowDecorButtonPolicy.loadDimensionPixelSize(resources, R.dimen.mw_desktop_popup_button_icon_padding);
        for (int i = 0; i < this.mRootView.getChildCount(); i++) {
            View childAt = this.mRootView.getChildAt(i);
            if (childAt instanceof DesktopCaptionButton) {
                DesktopCaptionButton desktopCaptionButton = (DesktopCaptionButton) childAt;
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                desktopCaptionButton.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                iLoadDimensionPixelSize = Math.max(desktopCaptionButton.getMeasuredWidth() + iLoadDimensionPixelSize2, iLoadDimensionPixelSize);
            }
        }
        return Math.min(this.mTaskInfo != null ? this.mTaskInfo.configuration.windowConfiguration.getBounds().width() - (WindowDecorButtonPolicy.loadDimensionPixelSize(resources, R.dimen.mw_desktop_popup_margin_horizontal) * 2) : 0, iLoadDimensionPixelSize);
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getLayoutResId() {
        return R.layout.mw_dex_caption_popup_menu;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getVisibleButtonCount() {
        boolean z = this.mRestartMenuEnabled;
        boolean z2 = this.mIsExternalDisplayConnected;
        boolean z3 = this.mIsDefaultDisplay;
        if (z3 && z2 && z) {
            return 5;
        }
        if (z) {
            return 4;
        }
        return (z3 && z2) ? 4 : 3;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy, com.android.wm.shell.windowdecor.policy.CaptionButtonStateManager
    public final void setupCaptionButtonState(Context context, ViewGroup viewGroup, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        DesktopCaptionButton desktopCaptionButton;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof DesktopCaptionButton) {
                DesktopCaptionButton desktopCaptionButton2 = (DesktopCaptionButton) childAt;
                desktopCaptionButton2.setOnClickListener(onClickListener);
                ColorStateList buttonColor = getButtonColor();
                desktopCaptionButton2.mIconView.setImageTintList(buttonColor);
                desktopCaptionButton2.mTextView.setTextColor(buttonColor);
            }
        }
        DesktopCaptionButton desktopCaptionButton3 = (DesktopCaptionButton) viewGroup.findViewById(R.id.remove_from_workspace);
        if (desktopCaptionButton3 != null) {
            desktopCaptionButton3.setVisibility(this.mIsDefaultDisplay ? 0 : 8);
        }
        DesktopCaptionButton desktopCaptionButton4 = (DesktopCaptionButton) viewGroup.findViewById(R.id.external_display);
        if (desktopCaptionButton4 != null) {
            if (this.mIsExternalDisplayConnected) {
                desktopCaptionButton4.setVisibility(0);
                if (CoreRune.MW_CAPTION_FLIP_FOLDING_POLICY && this.mTaskInfo.configuration.semDisplayDeviceType == 5) {
                    desktopCaptionButton4.setEnabled(false);
                }
            } else {
                desktopCaptionButton4.setVisibility(8);
            }
        }
        if (!CoreRune.MW_CAPTION_DESKTOP_RESTART || (desktopCaptionButton = (DesktopCaptionButton) viewGroup.findViewById(R.id.restart_app)) == null) {
            return;
        }
        desktopCaptionButton.setVisibility(this.mRestartMenuEnabled ? 0 : 8);
        desktopCaptionButton.mNotification.setVisibility(this.mShouldShowRestartNotification ? 0 : 8);
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final void setupRootView(Context context, View view, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) throws Resources.NotFoundException {
        DesktopCaptionButton desktopCaptionButton;
        ViewGroup.MarginLayoutParams marginLayoutParams;
        super.setupRootView(context, view, onTouchListener, onClickListener);
        if (!CoreRune.MW_CAPTION_DESKTOP_RESTART || (desktopCaptionButton = (DesktopCaptionButton) view.findViewById(R.id.restart_app)) == null || desktopCaptionButton.getVisibility() != 0 || (marginLayoutParams = (ViewGroup.MarginLayoutParams) desktopCaptionButton.mNotification.getLayoutParams()) == null) {
            return;
        }
        marginLayoutParams.topMargin = (desktopCaptionButton.getMeasuredHeight() - desktopCaptionButton.mTextView.getMeasuredHeight()) / 2;
        desktopCaptionButton.mNotification.setLayoutParams(marginLayoutParams);
    }
}
