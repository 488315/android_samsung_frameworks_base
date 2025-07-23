package android.view;

import libcore.util.NativeAllocationRegistry;

/* loaded from: classes4.dex */
public abstract class SurfaceControlActivePictureListener {
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(SurfaceControlActivePictureListener.class.getClassLoader(), nativeGetDestructor());
    private Runnable mDestructor;

    private static native long nativeGetDestructor();

    private native long nativeMakeAndStartListening();

    public abstract void onActivePicturesChanged(SurfaceControlActivePicture[] surfaceControlActivePictureArr);

    public void startListening() {
        synchronized (this) {
            this.mDestructor = sRegistry.registerNativeAllocation(this, nativeMakeAndStartListening());
        }
    }

    public void stopListening() {
        Runnable runnable;
        synchronized (this) {
            runnable = this.mDestructor;
        }
        if (runnable != null) {
            runnable.run();
        }
    }
}
