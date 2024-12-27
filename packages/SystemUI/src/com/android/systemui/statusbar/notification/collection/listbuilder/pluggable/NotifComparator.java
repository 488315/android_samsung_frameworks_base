package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import java.util.Comparator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class NotifComparator extends Pluggable implements Comparator {
    public NotifComparator(String str) {
        super(str);
    }

    @Override // java.util.Comparator
    public abstract int compare(ListEntry listEntry, ListEntry listEntry2);
}
