package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import java.util.Comparator;

/* loaded from: classes3.dex */
public abstract class NotifComparator extends Pluggable implements Comparator {
    public NotifComparator(String str) {
        super(str);
    }

    @Override // java.util.Comparator
    public abstract int compare(PipelineEntry pipelineEntry, PipelineEntry pipelineEntry2);
}
