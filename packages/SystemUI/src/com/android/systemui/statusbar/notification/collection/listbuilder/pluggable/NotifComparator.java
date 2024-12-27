package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import java.util.Comparator;

public abstract class NotifComparator extends Pluggable implements Comparator {
    public NotifComparator(String str) {
        super(str);
    }

    @Override // java.util.Comparator
    public abstract int compare(ListEntry listEntry, ListEntry listEntry2);
}
