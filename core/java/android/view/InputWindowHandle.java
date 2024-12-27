package android.view;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Region;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.util.Size;

import com.android.window.flags.Flags;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public final class InputWindowHandle {
    public float alpha;
    public boolean canOccludePresentation;
    public Size contentSize;
    public long dispatchingTimeoutMillis;
    public int displayId;
    public IBinder focusTransferTarget;
    public final Rect frame;
    public InputApplicationHandle inputApplicationHandle;
    public int inputConfig;
    public int layoutParamsFlags;
    public int layoutParamsSamsungFlags;
    public int layoutParamsType;
    public String name;
    public float oneHandOffsetX;
    public float oneHandOffsetY;
    public float oneHandScale;
    public int ownerPid;
    public int ownerUid;
    public String packageName;
    public final Region pointerTouchableRegion;
    private long ptr;
    public boolean replaceTouchableRegionWithCrop;
    public float scaleFactor;
    public int surfaceInset;
    public IBinder token;
    public int touchOcclusionMode;
    public final Region touchableRegion;
    public WeakReference<SurfaceControl> touchableRegionSurfaceControl;
    public Matrix transform;
    private IBinder windowToken;

    @Retention(RetentionPolicy.SOURCE)
    public @interface InputConfigFlags {}

    private native void nativeDispose();

    public InputWindowHandle(InputApplicationHandle inputApplicationHandle, int displayId) {
        this.frame = new Rect();
        this.contentSize = new Size(0, 0);
        this.touchableRegion = new Region();
        this.pointerTouchableRegion = new Region();
        this.touchOcclusionMode = 0;
        this.touchableRegionSurfaceControl = new WeakReference<>(null);
        this.inputApplicationHandle = inputApplicationHandle;
        this.displayId = displayId;
    }

    public InputWindowHandle(InputWindowHandle other) {
        this.frame = new Rect();
        this.contentSize = new Size(0, 0);
        this.touchableRegion = new Region();
        this.pointerTouchableRegion = new Region();
        this.touchOcclusionMode = 0;
        this.touchableRegionSurfaceControl = new WeakReference<>(null);
        this.ptr = 0L;
        this.inputApplicationHandle = new InputApplicationHandle(other.inputApplicationHandle);
        this.token = other.token;
        this.windowToken = other.windowToken;
        this.name = other.name;
        this.layoutParamsFlags = other.layoutParamsFlags;
        this.layoutParamsType = other.layoutParamsType;
        this.dispatchingTimeoutMillis = other.dispatchingTimeoutMillis;
        this.frame.set(other.frame);
        this.surfaceInset = other.surfaceInset;
        this.scaleFactor = other.scaleFactor;
        this.touchableRegion.set(other.touchableRegion);
        this.inputConfig = other.inputConfig;
        this.touchOcclusionMode = other.touchOcclusionMode;
        this.ownerPid = other.ownerPid;
        this.ownerUid = other.ownerUid;
        this.packageName = other.packageName;
        this.displayId = other.displayId;
        this.touchableRegionSurfaceControl = other.touchableRegionSurfaceControl;
        this.replaceTouchableRegionWithCrop = other.replaceTouchableRegionWithCrop;
        if (other.transform != null) {
            this.transform = new Matrix();
            this.transform.set(other.transform);
        }
        this.focusTransferTarget = other.focusTransferTarget;
        this.contentSize = new Size(other.contentSize.getWidth(), other.contentSize.getHeight());
        this.alpha = other.alpha;
        this.canOccludePresentation = other.canOccludePresentation;
    }

    public String toString() {
        return (this.name != null ? this.name : "")
                + ", frame=["
                + this.frame
                + NavigationBarInflaterView.SIZE_MOD_END
                + ", touchableRegion="
                + this.touchableRegion
                + ", scaleFactor="
                + this.scaleFactor
                + ", transform="
                + this.transform
                + ", windowToken="
                + this.windowToken
                + ", displayId="
                + this.displayId
                + ", isClone="
                + ((this.inputConfig & 65536) != 0)
                + ", contentSize="
                + this.contentSize
                + ", alpha="
                + this.alpha
                + ", canOccludePresentation="
                + this.canOccludePresentation;
    }

    protected void finalize() throws Throwable {
        try {
            nativeDispose();
        } finally {
            super.finalize();
        }
    }

    public void replaceTouchableRegionWithCrop(SurfaceControl bounds) {
        setTouchableRegionCrop(bounds);
        this.replaceTouchableRegionWithCrop = true;
    }

    public void setTouchableRegionCrop(SurfaceControl bounds) {
        this.touchableRegionSurfaceControl = new WeakReference<>(bounds);
    }

    public void setWindowToken(IBinder iwindow) {
        this.windowToken = iwindow;
    }

    public IBinder getWindowToken() {
        return this.windowToken;
    }

    public void setInputConfig(int inputConfig, boolean value) {
        if (value) {
            this.inputConfig |= inputConfig;
        } else {
            this.inputConfig &= ~inputConfig;
        }
    }

    public void setTrustedOverlay(
            SurfaceControl.Transaction t, SurfaceControl sc, boolean isTrusted) {
        if (Flags.surfaceTrustedOverlay()) {
            t.setTrustedOverlay(sc, isTrusted);
        } else if (isTrusted) {
            this.inputConfig |= 256;
        }
    }
}
