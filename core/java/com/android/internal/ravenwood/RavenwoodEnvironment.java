package com.android.internal.ravenwood;

public final class RavenwoodEnvironment {
    public static final String TAG = "RavenwoodEnvironment";
    private static RavenwoodEnvironment sInstance = new RavenwoodEnvironment();
    private static Workaround sWorkaround = new Workaround();

    private static native void ensureRavenwoodInitializedInternal();

    private RavenwoodEnvironment() {
        if (isRunningOnRavenwood()) {
            ensureRavenwoodInitializedInternal();
        }
    }

    public static RavenwoodEnvironment getInstance() {
        return sInstance;
    }

    public static void ensureRavenwoodInitialized() {}

    public boolean isRunningOnRavenwood() {
        return false;
    }

    private boolean isRunningOnRavenwood$ravenwood() {
        return true;
    }

    public static Workaround workaround() {
        if (getInstance().isRunningOnRavenwood()) {
            return sWorkaround;
        }
        throw new IllegalStateException("Workaround can only be used on Ravenwood");
    }

    public static class Workaround {
        Workaround() {}

        public boolean isTargetSdkAtLeastQ() {
            return true;
        }
    }
}
