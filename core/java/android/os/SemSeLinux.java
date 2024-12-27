package android.os;

public final class SemSeLinux {
    private SemSeLinux() {}

    public static boolean isEnforced() {
        return SELinux.isSELinuxEnforced();
    }
}
