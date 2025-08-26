package com.android.wm.shell.windowdecor.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.windowdecor.widget.CaptionAnimationButton;

/* loaded from: classes3.dex */
public class CaptionPopupButtonPolicy extends PopupButtonPolicy {
    public CaptionPopupButtonPolicy(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, DisplayController displayController, boolean z) {
        super(runningTaskInfo, context, displayController, z);
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final CaptionAnimationButton getAnimationButton(ViewGroup viewGroup) {
        return (CaptionAnimationButton) viewGroup.findViewById(R.id.update_caption_to_handle);
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getLayoutResId() {
        return R.layout.mw_caption_popup_menu;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getVisibleButtonCount() {
        return this.mIsExternalDisplayConnected ? 3 : 2;
    }
}
