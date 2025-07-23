package com.android.systemui.statusbar.notification;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.shade.NotificationsQuickSettingsContainer;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class AboveShelfObserver {
    public boolean mHasViewsAboveShelf = false;
    public final ViewGroup mHostLayout;
    public HasViewAboveShelfChangedListener mListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                View childAt = this.mHostLayout.getChildAt(i);
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
