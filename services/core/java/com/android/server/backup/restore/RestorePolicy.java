package com.android.server.backup.restore;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class RestorePolicy {
    public static final /* synthetic */ RestorePolicy[] $VALUES;
    public static final RestorePolicy ACCEPT;
    public static final RestorePolicy ACCEPT_IF_APK;
    public static final RestorePolicy IGNORE;

    static {
        RestorePolicy restorePolicy = new RestorePolicy("IGNORE", 0);
        IGNORE = restorePolicy;
        RestorePolicy restorePolicy2 = new RestorePolicy("ACCEPT", 1);
        ACCEPT = restorePolicy2;
        RestorePolicy restorePolicy3 = new RestorePolicy("ACCEPT_IF_APK", 2);
        ACCEPT_IF_APK = restorePolicy3;
        $VALUES = new RestorePolicy[] {restorePolicy, restorePolicy2, restorePolicy3};
    }

    public static RestorePolicy valueOf(String str) {
        return (RestorePolicy) Enum.valueOf(RestorePolicy.class, str);
    }

    public static RestorePolicy[] values() {
        return (RestorePolicy[]) $VALUES.clone();
    }
}
