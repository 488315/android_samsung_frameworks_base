package com.android.wm.shell.windowdecor.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FullScreenPopupButtonPolicy extends PopupButtonPolicy {
    public FullScreenPopupButtonPolicy(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, DisplayController displayController) {
        super(runningTaskInfo, context, displayController);
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getLayoutResId() {
        return R.layout.mw_handle_popup_menu_fullscreen;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getLeftMostPopupButtonId() {
        return this.mIsExternalDisplayConnected ? R.id.external_display : R.id.freeform_window;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getVisibleButtonCount() {
        return !this.mIsExternalDisplayConnected ? 3 : 4;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final boolean isVerticalDividerSupported() {
        return this.mIsExternalDisplayConnected;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy, com.android.wm.shell.windowdecor.policy.CaptionButtonStateManager
    public final void setupCaptionButtonState(Context context, ViewGroup viewGroup, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        adjustLeftMostButtonPadding(viewGroup);
        super.setupCaptionButtonState(context, viewGroup, onTouchListener, onClickListener);
    }
}
