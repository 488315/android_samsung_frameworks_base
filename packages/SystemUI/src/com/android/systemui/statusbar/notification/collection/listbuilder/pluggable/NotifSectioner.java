package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import java.util.List;

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
