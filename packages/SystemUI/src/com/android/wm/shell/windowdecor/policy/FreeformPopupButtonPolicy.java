package com.android.wm.shell.windowdecor.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.windowdecor.widget.CaptionAnimationButton;
import com.android.wm.shell.windowdecor.widget.CaptionButton;
import com.android.wm.shell.windowdecor.widget.CaptionButtonDivider;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class FreeformPopupButtonPolicy extends PopupButtonPolicy {
    public final boolean mIsKeyguardShowing;

    public FreeformPopupButtonPolicy(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, DisplayController displayController, boolean z, boolean z2) {
        super(runningTaskInfo, context, displayController, z);
        this.mIsKeyguardShowing = z2;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final CaptionAnimationButton getAnimationButton(ViewGroup viewGroup) {
        return (CaptionAnimationButton) viewGroup.findViewById(R.id.update_handle_to_caption);
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public int getLayoutResId() {
        return R.layout.mw_handle_popup_menu_freeform;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public int getVisibleButtonCount() {
        return this.mIsExternalDisplayConnected ? 6 : 5;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final boolean isVerticalDividerSupported() {
        return true;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy, com.android.wm.shell.windowdecor.policy.CaptionButtonStateManager
    public final void setupCaptionButtonState(Context context, ViewGroup viewGroup, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        super.setupCaptionButtonState(context, viewGroup, onTouchListener, onClickListener);
        if (CoreRune.MW_CAPTION_KEYGUARD && this.mIsKeyguardShowing) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof CaptionButton) {
                    CaptionButton captionButton = (CaptionButton) childAt;
                    if (captionButton.getId() != R.id.close_window) {
                        captionButton.setEnabled(false);
                    }
                }
            }
        }
        CaptionButtonDivider captionButtonDivider = (CaptionButtonDivider) viewGroup.findViewById(R.id.divider);
        if (captionButtonDivider != null) {
            captionButtonDivider.setBackgroundTintList(((PopupButtonPolicy) this).mContext.getColorStateList(this.mIsNightMode ? R.color.mw_caption_button_divider_color_dark : R.color.mw_caption_button_divider_color_light));
        }
    }
}
