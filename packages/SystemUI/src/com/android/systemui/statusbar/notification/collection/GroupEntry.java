package com.android.systemui.statusbar.notification.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class GroupEntry extends ListEntry {
    public static final GroupEntry ROOT_ENTRY = new GroupEntry("<root>", 0);
    public final List mChildren;
    public NotificationEntry mLogicalSummary;
    public NotificationEntry mSummary;
    public final List mUnmodifiableChildren;

    public GroupEntry(String str, long j) {
        super(str, j);
        ArrayList arrayList = new ArrayList();
        this.mChildren = arrayList;
        this.mUnmodifiableChildren = Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineEntry
    public final NotificationEntry getRepresentativeEntry() {
        return this.mSummary;
    }
}
