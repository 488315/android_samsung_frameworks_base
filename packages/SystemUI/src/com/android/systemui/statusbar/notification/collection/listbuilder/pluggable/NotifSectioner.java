package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class NotifSectioner extends Pluggable {
    private final int mBucket;

    public NotifSectioner(String str, int i) {
        super(str);
        this.mBucket = i;
    }

    public final int getBucket() {
        return this.mBucket;
    }

    public NotifComparator getComparator() {
        return null;
    }

    public NodeController getHeaderNodeController() {
        return null;
    }

    public abstract boolean isInSection(ListEntry listEntry);

    public void onEntriesUpdated(List<ListEntry> list) {
    }
}
