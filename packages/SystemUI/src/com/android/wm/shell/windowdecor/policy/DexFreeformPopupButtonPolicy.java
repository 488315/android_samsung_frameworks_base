package com.android.wm.shell.windowdecor.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.windowdecor.HandleMenu$$ExternalSyntheticLambda0;
import com.android.wm.shell.windowdecor.MenuPopupAnimator;
import com.android.wm.shell.windowdecor.widget.DesktopCaptionButton;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DexFreeformPopupButtonPolicy extends PopupButtonPolicy {
    public final boolean mIsDefaultDisplay;

    public DexFreeformPopupButtonPolicy(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, DisplayController displayController) {
        super(runningTaskInfo, context, displayController);
        this.mIsDefaultDisplay = runningTaskInfo.displayId == 0;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final void animateOpenMenu(HandleMenu$$ExternalSyntheticLambda0 handleMenu$$ExternalSyntheticLambda0) {
        MenuPopupAnimator menuPopupAnimator = this.mMenuPopupAnimator;
        if (menuPopupAnimator == null) {
            return;
        }
        menuPopupAnimator.animateOpen(this.mAnimButton, null);
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int calculatePopupHeight() {
        if (this.mRootView == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mRootView.getChildCount(); i2++) {
            View childAt = this.mRootView.getChildAt(i2);
            if (childAt instanceof DesktopCaptionButton) {
                DesktopCaptionButton desktopCaptionButton = (DesktopCaptionButton) childAt;
                if (desktopCaptionButton.getVisibility() == 0) {
                    desktopCaptionButton.mRootView.measure(View.MeasureSpec.makeMeasureSpec(this.mPopupWidth, Integer.MIN_VALUE), 0);
                    i += desktopCaptionButton.getMeasuredHeight();
                }
            }
        }
        return i;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int calculatePopupWidth() {
        if (this.mRootView == null) {
            return 0;
        }
        Resources resources = ((PopupButtonPolicy) this).mContext.getResources();
        int loadDimensionPixelSize = WindowDecorButtonPolicy.loadDimensionPixelSize(resources, R.dimen.mw_desktop_popup_min_width);
        for (int i = 0; i < this.mRootView.getChildCount(); i++) {
            View childAt = this.mRootView.getChildAt(i);
            if (childAt instanceof DesktopCaptionButton) {
                DesktopCaptionButton desktopCaptionButton = (DesktopCaptionButton) childAt;
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                desktopCaptionButton.measure(makeMeasureSpec, makeMeasureSpec);
                loadDimensionPixelSize = Math.max(desktopCaptionButton.getMeasuredWidth(), loadDimensionPixelSize);
            }
        }
        return Math.min(this.mTaskInfo != null ? this.mTaskInfo.configuration.windowConfiguration.getBounds().width() - (WindowDecorButtonPolicy.loadDimensionPixelSize(resources, R.dimen.mw_desktop_popup_margin_horizontal) * 2) : 0, loadDimensionPixelSize);
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getLayoutResId() {
        return R.layout.mw_dex_caption_popup_menu;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getVisibleButtonCount() {
        return (this.mIsDefaultDisplay && this.mIsExternalDisplayConnected) ? 4 : 3;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy, com.android.wm.shell.windowdecor.policy.CaptionButtonStateManager
    public final void setupCaptionButtonState(Context context, ViewGroup viewGroup, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof DesktopCaptionButton) {
                DesktopCaptionButton desktopCaptionButton = (DesktopCaptionButton) childAt;
                desktopCaptionButton.setOnClickListener(onClickListener);
                ColorStateList buttonColor = getButtonColor();
                desktopCaptionButton.mIconView.setImageTintList(buttonColor);
                desktopCaptionButton.mTextView.setTextColor(buttonColor);
            }
        }
        DesktopCaptionButton desktopCaptionButton2 = (DesktopCaptionButton) viewGroup.findViewById(R.id.remove_from_workspace);
        if (desktopCaptionButton2 != null) {
            desktopCaptionButton2.setVisibility(this.mIsDefaultDisplay ? 0 : 8);
        }
        DesktopCaptionButton desktopCaptionButton3 = (DesktopCaptionButton) viewGroup.findViewById(R.id.external_display);
        if (desktopCaptionButton3 != null) {
            if (!this.mIsExternalDisplayConnected) {
                desktopCaptionButton3.setVisibility(8);
                return;
            }
            desktopCaptionButton3.setVisibility(0);
            if (CoreRune.MW_CAPTION_FLIP_FOLDING_POLICY && this.mTaskInfo.configuration.semDisplayDeviceType == 5) {
                desktopCaptionButton3.setEnabled(false);
            }
        }
    }
}
