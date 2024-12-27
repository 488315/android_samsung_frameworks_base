package android.permission;

public interface PermissionManagerInternal {
    byte[] backupRuntimePermissions(int i);

    void restoreDelayedRuntimePermissions(String str, int i);

    void restoreRuntimePermissions(byte[] bArr, int i);
}
