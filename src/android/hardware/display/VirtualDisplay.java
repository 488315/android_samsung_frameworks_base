package android.hardware.display;

import android.view.Display;
import android.view.Surface;
import com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.Flags;

/* loaded from: classes2.dex */
public final class VirtualDisplay {
    private final Display mDisplay;
    private final DisplayManagerGlobal mGlobal;
    private Surface mSurface;
    private IVirtualDisplayCallback mToken;

    public static abstract class Callback {
        public void onPaused() {
        }

        public void onResumed() {
        }

        public void onStopped() {
        }
    }

    VirtualDisplay(DisplayManagerGlobal displayManagerGlobal, Display display, IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) {
        this.mGlobal = displayManagerGlobal;
        this.mDisplay = display;
        this.mToken = iVirtualDisplayCallback;
        this.mSurface = surface;
    }

    public Display getDisplay() {
        return this.mDisplay;
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public IVirtualDisplayCallback getToken() {
        return this.mToken;
    }

    public void setSurface(Surface surface) {
        if (this.mSurface != surface) {
            this.mGlobal.setVirtualDisplaySurface(this.mToken, surface);
            this.mSurface = surface;
        }
    }

    public void resize(int i, int i2, int i3) {
        this.mGlobal.resizeVirtualDisplay(this.mToken, i, i2, i3);
    }

    public void semSetRotation(int i) {
        this.mGlobal.rotateVirtualDisplay(this.mToken, i);
    }

    public void release() {
        IVirtualDisplayCallback iVirtualDisplayCallback = this.mToken;
        if (iVirtualDisplayCallback != null) {
            this.mGlobal.releaseVirtualDisplay(iVirtualDisplayCallback);
            this.mToken = null;
        }
    }

    public void setRotation(int i) {
        if (Flags.virtualDisplayRotationApi()) {
            if (i != 0 && i != 1 && i != 2 && i != 3) {
                throw new IllegalArgumentException("Invalid virtual display rotation value: " + i);
            }
            if (this.mToken == null || this.mDisplay.getRotation() == i) {
                return;
            }
            this.mGlobal.setVirtualDisplayRotation(this.mToken, i);
        }
    }

    public String toString() {
        return "VirtualDisplay{display=" + this.mDisplay + ", token=" + this.mToken + ", surface=" + this.mSurface + "}";
    }
}
