package android.graphics;

import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class ColorFilter {
    private long mNativeInstance;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    long createNativeInstance() {
        return 0L;
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(ColorFilter.class.getClassLoader(), ColorFilter.nativeGetFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    @Deprecated
    public ColorFilter() {
    }

    public final synchronized long getNativeInstance() {
        if (this.mNativeInstance == 0) {
            long createNativeInstance = createNativeInstance();
            this.mNativeInstance = createNativeInstance;
            if (createNativeInstance != 0) {
                NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeInstance);
            }
        }
        return this.mNativeInstance;
    }
}
