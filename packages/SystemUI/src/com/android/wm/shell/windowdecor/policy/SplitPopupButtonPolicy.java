package com.android.wm.shell.windowdecor.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.windowdecor.widget.CaptionButton;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SplitPopupButtonPolicy extends PopupButtonPolicy {
    public final boolean mIsDesktopModeSupportedOnDisplay;

    public SplitPopupButtonPolicy(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, DisplayController displayController, boolean z) {
        super(runningTaskInfo, context, displayController);
        this.mIsDesktopModeSupportedOnDisplay = z;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getLayoutResId() {
        return R.layout.mw_handle_popup_menu_split;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getLeftMostPopupButtonId() {
        return !this.mIsDesktopModeSupportedOnDisplay ? R.id.apps_window : this.mIsExternalDisplayConnected ? R.id.external_display : R.id.freeform_window;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getVisibleButtonCount() {
        boolean z = this.mIsDesktopModeSupportedOnDisplay;
        boolean z2 = this.mIsExternalDisplayConnected;
        if (!z2 || z) {
            return (z2 || !z) ? 4 : 3;
        }
        return 5;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final boolean isVerticalDividerSupported() {
        return !this.mIsDesktopModeSupportedOnDisplay || this.mIsExternalDisplayConnected;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy, com.android.wm.shell.windowdecor.policy.CaptionButtonStateManager
    public final void setupCaptionButtonState(Context context, ViewGroup viewGroup, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        CaptionButton captionButton = (CaptionButton) viewGroup.findViewById(R.id.apps_window);
        if (captionButton != null) {
            captionButton.setVisibility(this.mIsDesktopModeSupportedOnDisplay ? 8 : 0);
        }
        adjustLeftMostButtonPadding(viewGroup);
        super.setupCaptionButtonState(context, viewGroup, onTouchListener, onClickListener);
    }
}
