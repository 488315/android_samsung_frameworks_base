package android.filterfw.core;

import android.graphics.SurfaceTexture;
import android.media.MediaRecorder;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;

/* loaded from: classes.dex */
public class GLEnvironment {
    private int glEnvId;
    private boolean mManageContext = true;

    private native boolean nativeActivate();

    private native boolean nativeActivateSurfaceId(int i);

    private native int nativeAddSurface(Surface surface);

    private native int nativeAddSurfaceFromMediaRecorder(MediaRecorder mediaRecorder);

    private native int nativeAddSurfaceWidthHeight(Surface surface, int i, int i2);

    private native boolean nativeAllocate();

    private native boolean nativeDeactivate();

    private native boolean nativeDeallocate();

    private native boolean nativeDisconnectSurfaceMediaSource(MediaRecorder mediaRecorder);

    private native boolean nativeInitWithCurrentContext();

    private native boolean nativeInitWithNewContext();

    private native boolean nativeIsActive();

    private static native boolean nativeIsAnyContextActive();

    private native boolean nativeIsContextActive();

    private native boolean nativeRemoveSurfaceId(int i);

    private native boolean nativeSetSurfaceTimestamp(long j);

    private native boolean nativeSwapBuffers();

    public GLEnvironment() {
        nativeAllocate();
    }

    private GLEnvironment(NativeAllocatorTag nativeAllocatorTag) {
    }

    public synchronized void tearDown() {
        if (this.glEnvId != -1) {
            nativeDeallocate();
            this.glEnvId = -1;
        }
    }

    protected void finalize() throws Throwable {
        tearDown();
    }

    public void initWithNewContext() {
        this.mManageContext = true;
        if (!nativeInitWithNewContext()) {
            throw new RuntimeException("Could not initialize GLEnvironment with new context!");
        }
    }

    public void initWithCurrentContext() {
        this.mManageContext = false;
        if (!nativeInitWithCurrentContext()) {
            throw new RuntimeException("Could not initialize GLEnvironment with current context!");
        }
    }

    public boolean isActive() {
        return nativeIsActive();
    }

    public boolean isContextActive() {
        return nativeIsContextActive();
    }

    public static boolean isAnyContextActive() {
        return nativeIsAnyContextActive();
    }

    public void activate() {
        if (Looper.myLooper() != null && Looper.myLooper().equals(Looper.getMainLooper())) {
            Log.e("FilterFramework", "Activating GL context in UI thread!");
        }
        if (this.mManageContext && !nativeActivate()) {
            throw new RuntimeException("Could not activate GLEnvironment!");
        }
    }

    public void deactivate() {
        if (this.mManageContext && !nativeDeactivate()) {
            throw new RuntimeException("Could not deactivate GLEnvironment!");
        }
    }

    public void swapBuffers() {
        if (!nativeSwapBuffers()) {
            throw new RuntimeException("Error swapping EGL buffers!");
        }
    }

    public int registerSurface(Surface surface) {
        int nativeAddSurface = nativeAddSurface(surface);
        if (nativeAddSurface >= 0) {
            return nativeAddSurface;
        }
        throw new RuntimeException("Error registering surface " + surface + "!");
    }

    public int registerSurfaceTexture(SurfaceTexture surfaceTexture, int i, int i2) {
        Surface surface = new Surface(surfaceTexture);
        int nativeAddSurfaceWidthHeight = nativeAddSurfaceWidthHeight(surface, i, i2);
        surface.release();
        if (nativeAddSurfaceWidthHeight >= 0) {
            return nativeAddSurfaceWidthHeight;
        }
        throw new RuntimeException("Error registering surfaceTexture " + surfaceTexture + "!");
    }

    public int registerSurfaceFromMediaRecorder(MediaRecorder mediaRecorder) {
        int nativeAddSurfaceFromMediaRecorder = nativeAddSurfaceFromMediaRecorder(mediaRecorder);
        if (nativeAddSurfaceFromMediaRecorder >= 0) {
            return nativeAddSurfaceFromMediaRecorder;
        }
        throw new RuntimeException("Error registering surface from MediaRecorder" + mediaRecorder + "!");
    }

    public void activateSurfaceWithId(int i) {
        if (nativeActivateSurfaceId(i)) {
            return;
        }
        throw new RuntimeException("Could not activate surface " + i + "!");
    }

    public void unregisterSurfaceId(int i) {
        if (nativeRemoveSurfaceId(i)) {
            return;
        }
        throw new RuntimeException("Could not unregister surface " + i + "!");
    }

    public void setSurfaceTimestamp(long j) {
        if (!nativeSetSurfaceTimestamp(j)) {
            throw new RuntimeException("Could not set timestamp for current surface!");
        }
    }

    static {
        System.loadLibrary("filterfw");
    }
}
