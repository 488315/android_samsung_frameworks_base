package android.graphics;

import libcore.util.NativeAllocationRegistry;

public class ColorFilter {
    private long mNativeInstance;

    public static native long nativeGetFinalizer();

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry =
                NativeAllocationRegistry.createMalloced(
                        ColorFilter.class.getClassLoader(), ColorFilter.nativeGetFinalizer());

        private NoImagePreloadHolder() {}
    }

    @Deprecated
    public ColorFilter() {}

    long createNativeInstance() {
        return 0L;
    }

    public final synchronized long getNativeInstance() {
        if (this.mNativeInstance == 0) {
            this.mNativeInstance = createNativeInstance();
            if (this.mNativeInstance != 0) {
                NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeInstance);
            }
        }
        return this.mNativeInstance;
    }
}
