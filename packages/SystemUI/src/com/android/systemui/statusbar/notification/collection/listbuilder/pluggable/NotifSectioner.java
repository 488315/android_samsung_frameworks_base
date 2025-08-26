package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import java.util.List;

/* loaded from: classes3.dex */
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

    public abstract boolean isInSection(PipelineEntry pipelineEntry);

    public void onEntriesUpdated(List<PipelineEntry> list) {
    }
}
