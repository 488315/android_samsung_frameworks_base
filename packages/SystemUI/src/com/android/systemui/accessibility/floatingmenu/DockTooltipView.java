package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
