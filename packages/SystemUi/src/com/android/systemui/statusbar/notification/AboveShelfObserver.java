package com.android.systemui.statusbar.notification;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.shade.NotificationsQuickSettingsContainer;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AboveShelfObserver {
    public boolean mHasViewsAboveShelf = false;
    public final ViewGroup mHostLayout;
    public HasViewAboveShelfChangedListener mListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface HasViewAboveShelfChangedListener {
    }

    public AboveShelfObserver(ViewGroup viewGroup) {
        this.mHostLayout = viewGroup;
    }

    public boolean hasViewsAboveShelf() {
        return this.mHasViewsAboveShelf;
    }

    public final void onAboveShelfStateChanged(boolean z) {
        ViewGroup viewGroup;
        if (!z && (viewGroup = this.mHostLayout) != null) {
            int childCount = viewGroup.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                View childAt = viewGroup.getChildAt(i);
                if ((childAt instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) childAt).isAboveShelf()) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        if (this.mHasViewsAboveShelf != z) {
            this.mHasViewsAboveShelf = z;
            HasViewAboveShelfChangedListener hasViewAboveShelfChangedListener = this.mListener;
            if (hasViewAboveShelfChangedListener != null) {
                ((NotificationsQuickSettingsContainer) hasViewAboveShelfChangedListener).invalidate();
            }
        }
    }
}
