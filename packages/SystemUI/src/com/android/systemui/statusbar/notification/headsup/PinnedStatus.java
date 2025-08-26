package com.android.systemui.statusbar.notification.headsup;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class PinnedStatus {
    public static final /* synthetic */ PinnedStatus[] $VALUES;
    public static final PinnedStatus NotPinned;
    public static final PinnedStatus PinnedBySystem;
    public static final PinnedStatus PinnedByUser;
    private final boolean isPinned;

    static {
        PinnedStatus pinnedStatus = new PinnedStatus("NotPinned", 0, false);
        NotPinned = pinnedStatus;
        PinnedStatus pinnedStatus2 = new PinnedStatus("PinnedBySystem", 1, true);
        PinnedBySystem = pinnedStatus2;
        PinnedStatus pinnedStatus3 = new PinnedStatus("PinnedByUser", 2, true);
        PinnedByUser = pinnedStatus3;
        PinnedStatus[] pinnedStatusArr = {pinnedStatus, pinnedStatus2, pinnedStatus3};
        $VALUES = pinnedStatusArr;
        EnumEntriesKt.enumEntries(pinnedStatusArr);
    }

    private PinnedStatus(String str, int i, boolean z) {
        this.isPinned = z;
    }

    public static PinnedStatus valueOf(String str) {
        return (PinnedStatus) Enum.valueOf(PinnedStatus.class, str);
    }

    public static PinnedStatus[] values() {
        return (PinnedStatus[]) $VALUES.clone();
    }

    public final boolean isPinned() {
        return this.isPinned;
    }
}
