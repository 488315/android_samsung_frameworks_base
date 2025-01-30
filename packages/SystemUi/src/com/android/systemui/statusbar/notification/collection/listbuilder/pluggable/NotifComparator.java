package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import java.util.Comparator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NotifComparator extends Pluggable implements Comparator {
    public NotifComparator(String str) {
        super(str);
    }

    @Override // java.util.Comparator
    public abstract int compare(ListEntry listEntry, ListEntry listEntry2);
}
