package android.hardware.display;

import android.view.Display;
import android.view.Surface;

public final class VirtualDisplay {
    private final Display mDisplay;
    private final DisplayManagerGlobal mGlobal;
    private Surface mSurface;
    private IVirtualDisplayCallback mToken;

    VirtualDisplay(
            DisplayManagerGlobal global,
            Display display,
            IVirtualDisplayCallback token,
            Surface surface) {
        this.mGlobal = global;
        this.mDisplay = display;
        this.mToken = token;
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

    public void resize(int width, int height, int densityDpi) {
        this.mGlobal.resizeVirtualDisplay(this.mToken, width, height, densityDpi);
    }

    public void semSetRotation(int rotation) {
        this.mGlobal.rotateVirtualDisplay(this.mToken, rotation);
    }

    public void release() {
        if (this.mToken != null) {
            this.mGlobal.releaseVirtualDisplay(this.mToken);
            this.mToken = null;
        }
    }

    public void setDisplayState(boolean isOn) {
        if (this.mToken != null) {
            this.mGlobal.setVirtualDisplayState(this.mToken, isOn);
        }
    }

    public String toString() {
        return "VirtualDisplay{display="
                + this.mDisplay
                + ", token="
                + this.mToken
                + ", surface="
                + this.mSurface
                + "}";
    }

    public abstract static class Callback {
        public void onPaused() {}

        public void onResumed() {}

        public void onStopped() {}
    }
}
