package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
