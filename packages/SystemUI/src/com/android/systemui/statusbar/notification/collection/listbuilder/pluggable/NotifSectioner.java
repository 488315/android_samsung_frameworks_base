package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
