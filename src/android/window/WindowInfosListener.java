package android.window;

import android.graphics.Matrix;
import android.util.Pair;
import android.util.Size;
import android.view.InputWindowHandle;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes5.dex */
public abstract class WindowInfosListener {
    private final long mNativeListener;

    private static native long nativeCreate(WindowInfosListener windowInfosListener);

    private static native long nativeGetFinalizer();

    private static native Pair<InputWindowHandle[], DisplayInfo[]> nativeRegister(long j);

    private static native void nativeUnregister(long j);

    public abstract void onWindowInfosChanged(InputWindowHandle[] inputWindowHandleArr, DisplayInfo[] displayInfoArr);

    public WindowInfosListener() {
        NativeAllocationRegistry createMalloced = NativeAllocationRegistry.createMalloced(WindowInfosListener.class.getClassLoader(), nativeGetFinalizer());
        long nativeCreate = nativeCreate(this);
        this.mNativeListener = nativeCreate;
        createMalloced.registerNativeAllocation(this, nativeCreate);
    }

    public Pair<InputWindowHandle[], DisplayInfo[]> register() {
        return nativeRegister(this.mNativeListener);
    }

    public void unregister() {
        nativeUnregister(this.mNativeListener);
    }

    public static final class DisplayInfo {
        public final int mDisplayId;
        public final Size mLogicalSize;
        public final Matrix mTransform;

        private DisplayInfo(int i, int i2, int i3, Matrix matrix) {
            this.mDisplayId = i;
            this.mLogicalSize = new Size(i2, i3);
            this.mTransform = matrix;
        }

        public String toString() {
            return "displayId=" + this.mDisplayId + ", mLogicalSize=" + this.mLogicalSize + ", mTransform=" + this.mTransform;
        }
    }
}
