package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

final class GroupLocation {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ GroupLocation[] $VALUES;
    public static final GroupLocation Detached = new GroupLocation("Detached", 0);
    public static final GroupLocation Isolated = new GroupLocation("Isolated", 1);
    public static final GroupLocation Summary = new GroupLocation("Summary", 2);
    public static final GroupLocation Child = new GroupLocation("Child", 3);

    private static final /* synthetic */ GroupLocation[] $values() {
        return new GroupLocation[]{Detached, Isolated, Summary, Child};
    }

    static {
        GroupLocation[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
    }

    private GroupLocation(String str, int i) {
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static GroupLocation valueOf(String str) {
        return (GroupLocation) Enum.valueOf(GroupLocation.class, str);
    }

    public static GroupLocation[] values() {
        return (GroupLocation[]) $VALUES.clone();
    }
}
