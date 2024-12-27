package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import com.android.systemui.R;

public final class DockTooltipView extends BaseTooltipView {
    public final AccessibilityFloatingMenuView mAnchorView;

    public DockTooltipView(Context context, AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        super(context, accessibilityFloatingMenuView);
        this.mAnchorView = accessibilityFloatingMenuView;
        this.mTextView.setText(getContext().getText(R.string.accessibility_floating_button_docking_tooltip));
    }

    @Override // com.android.systemui.accessibility.floatingmenu.BaseTooltipView
    public final void hide() {
        super.hide();
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.mAnchorView;
        accessibilityFloatingMenuView.mListView.clearAnimation();
        accessibilityFloatingMenuView.fadeOut();
    }
}
