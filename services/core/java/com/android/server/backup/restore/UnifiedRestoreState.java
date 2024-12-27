package com.android.server.backup.restore;

public final class UnifiedRestoreState {
    public static final /* synthetic */ UnifiedRestoreState[] $VALUES;
    public static final UnifiedRestoreState FINAL;
    public static final UnifiedRestoreState INITIAL;
    public static final UnifiedRestoreState RESTORE_FINISHED;
    public static final UnifiedRestoreState RESTORE_FULL;
    public static final UnifiedRestoreState RESTORE_KEYVALUE;
    public static final UnifiedRestoreState RUNNING_QUEUE;

    static {
        UnifiedRestoreState unifiedRestoreState = new UnifiedRestoreState("INITIAL", 0);
        INITIAL = unifiedRestoreState;
        UnifiedRestoreState unifiedRestoreState2 = new UnifiedRestoreState("RUNNING_QUEUE", 1);
        RUNNING_QUEUE = unifiedRestoreState2;
        UnifiedRestoreState unifiedRestoreState3 = new UnifiedRestoreState("RESTORE_KEYVALUE", 2);
        RESTORE_KEYVALUE = unifiedRestoreState3;
        UnifiedRestoreState unifiedRestoreState4 = new UnifiedRestoreState("RESTORE_FULL", 3);
        RESTORE_FULL = unifiedRestoreState4;
        UnifiedRestoreState unifiedRestoreState5 = new UnifiedRestoreState("RESTORE_FINISHED", 4);
        RESTORE_FINISHED = unifiedRestoreState5;
        UnifiedRestoreState unifiedRestoreState6 = new UnifiedRestoreState("FINAL", 5);
        FINAL = unifiedRestoreState6;
        $VALUES =
                new UnifiedRestoreState[] {
                    unifiedRestoreState,
                    unifiedRestoreState2,
                    unifiedRestoreState3,
                    unifiedRestoreState4,
                    unifiedRestoreState5,
                    unifiedRestoreState6
                };
    }

    public static UnifiedRestoreState valueOf(String str) {
        return (UnifiedRestoreState) Enum.valueOf(UnifiedRestoreState.class, str);
    }

    public static UnifiedRestoreState[] values() {
        return (UnifiedRestoreState[]) $VALUES.clone();
    }
}
