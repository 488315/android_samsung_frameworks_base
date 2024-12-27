package com.android.server.enterprise.plm;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class StartReason {
    public static final /* synthetic */ StartReason[] $VALUES;
    public static final StartReason EDM_SERVICE_READY;
    public static final StartReason EDM_SYSTEM_READY;

    static {
        StartReason startReason = new StartReason("EDM_SYSTEM_READY", 0);
        EDM_SYSTEM_READY = startReason;
        StartReason startReason2 = new StartReason("EDM_SERVICE_READY", 1);
        EDM_SERVICE_READY = startReason2;
        $VALUES = new StartReason[] {startReason, startReason2};
    }

    public static StartReason valueOf(String str) {
        return (StartReason) Enum.valueOf(StartReason.class, str);
    }

    public static StartReason[] values() {
        return (StartReason[]) $VALUES.clone();
    }
}
