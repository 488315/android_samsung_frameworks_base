package com.android.systemui.statusbar.notification.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GroupEntry extends ListEntry {
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

    @Override // com.android.systemui.statusbar.notification.collection.ListEntry
    public final NotificationEntry getRepresentativeEntry() {
        return this.mSummary;
    }
}
