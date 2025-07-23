package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import java.util.Comparator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class NotifComparator extends Pluggable implements Comparator {
    public NotifComparator(String str) {
        super(str);
    }

    @Override // java.util.Comparator
    public abstract int compare(PipelineEntry pipelineEntry, PipelineEntry pipelineEntry2);
}
