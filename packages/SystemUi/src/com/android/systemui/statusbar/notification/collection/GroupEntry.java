package com.android.systemui.statusbar.notification.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
