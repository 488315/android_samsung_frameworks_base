package com.android.systemui.statusbar.notification.row;

public final class ExpandableOutlineViewController {
    public final ExpandableViewController mExpandableViewController;

    public ExpandableOutlineViewController(ExpandableOutlineView expandableOutlineView, ExpandableViewController expandableViewController) {
        this.mExpandableViewController = expandableViewController;
    }
}
