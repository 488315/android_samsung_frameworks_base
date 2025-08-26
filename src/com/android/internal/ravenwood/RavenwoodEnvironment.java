package com.android.internal.ravenwood;

/* loaded from: classes4.dex */
public final class RavenwoodEnvironment {
    public static final String TAG = "RavenwoodEnvironment";
    private static RavenwoodEnvironment sInstance = new RavenwoodEnvironment();

    public static class CompatIdsForTest {
        public static final long TEST_COMPAT_ID_1 = 368131859;
        public static final long TEST_COMPAT_ID_2 = 368131701;
        public static final long TEST_COMPAT_ID_3 = 368131659;
        public static final long TEST_COMPAT_ID_4 = 368132057;
        public static final long TEST_COMPAT_ID_5 = 387558811;
    }

    private boolean isRunningOnRavenwood$ravenwood() {
        return true;
    }

    public boolean isRunningOnRavenwood() {
        return false;
    }

    private static RuntimeException notSupportedOnDevice() {
        return new UnsupportedOperationException("This method can only be used on Ravenwood");
    }

    public static RavenwoodEnvironment getInstance() {
        return sInstance;
    }

    public <T> T fromAddress(long j) {
        throw notSupportedOnDevice();
    }

    public String getRavenwoodRuntimePath() {
        throw notSupportedOnDevice();
    }
}
