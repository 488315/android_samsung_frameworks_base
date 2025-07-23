package android.view;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.IBinder;
import android.util.Size;
import android.view.SurfaceControl;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
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
    public @interface InputConfigFlags {
    }

    private native void nativeDispose();

    public InputWindowHandle(InputApplicationHandle inputApplicationHandle, int i) {
        this.frame = new Rect();
        this.contentSize = new Size(0, 0);
        this.touchableRegion = new Region();
        this.pointerTouchableRegion = new Region();
        this.touchOcclusionMode = 0;
        this.touchableRegionSurfaceControl = new WeakReference<>(null);
        this.inputApplicationHandle = inputApplicationHandle;
        this.displayId = i;
    }

    public InputWindowHandle(InputWindowHandle inputWindowHandle) {
        Rect rect = new Rect();
        this.frame = rect;
        this.contentSize = new Size(0, 0);
        Region region = new Region();
        this.touchableRegion = region;
        this.pointerTouchableRegion = new Region();
        this.touchOcclusionMode = 0;
        this.touchableRegionSurfaceControl = new WeakReference<>(null);
        this.ptr = 0L;
        this.inputApplicationHandle = new InputApplicationHandle(inputWindowHandle.inputApplicationHandle);
        this.token = inputWindowHandle.token;
        this.windowToken = inputWindowHandle.windowToken;
        this.name = inputWindowHandle.name;
        this.layoutParamsFlags = inputWindowHandle.layoutParamsFlags;
        this.layoutParamsType = inputWindowHandle.layoutParamsType;
        this.dispatchingTimeoutMillis = inputWindowHandle.dispatchingTimeoutMillis;
        rect.set(inputWindowHandle.frame);
        this.surfaceInset = inputWindowHandle.surfaceInset;
        this.scaleFactor = inputWindowHandle.scaleFactor;
        region.set(inputWindowHandle.touchableRegion);
        this.inputConfig = inputWindowHandle.inputConfig;
        this.touchOcclusionMode = inputWindowHandle.touchOcclusionMode;
        this.ownerPid = inputWindowHandle.ownerPid;
        this.ownerUid = inputWindowHandle.ownerUid;
        this.packageName = inputWindowHandle.packageName;
        this.displayId = inputWindowHandle.displayId;
        this.touchableRegionSurfaceControl = inputWindowHandle.touchableRegionSurfaceControl;
        this.replaceTouchableRegionWithCrop = inputWindowHandle.replaceTouchableRegionWithCrop;
        if (inputWindowHandle.transform != null) {
            Matrix matrix = new Matrix();
            this.transform = matrix;
            matrix.set(inputWindowHandle.transform);
        }
        this.focusTransferTarget = inputWindowHandle.focusTransferTarget;
        this.contentSize = new Size(inputWindowHandle.contentSize.getWidth(), inputWindowHandle.contentSize.getHeight());
        this.alpha = inputWindowHandle.alpha;
        this.canOccludePresentation = inputWindowHandle.canOccludePresentation;
    }

    public String toString() {
        String str = this.name;
        if (str == null) {
            str = "";
        }
        StringBuilder sb = new StringBuilder(str);
        sb.append(", frame=[");
        sb.append(this.frame);
        sb.append("], touchableRegion=");
        sb.append(this.touchableRegion);
        sb.append(", scaleFactor=");
        sb.append(this.scaleFactor);
        sb.append(", transform=");
        sb.append(this.transform);
        sb.append(", windowToken=");
        sb.append(this.windowToken);
        sb.append(", displayId=");
        sb.append(this.displayId);
        sb.append(", isClone=");
        sb.append((this.inputConfig & 65536) != 0);
        sb.append(", contentSize=");
        sb.append(this.contentSize);
        sb.append(", alpha=");
        sb.append(this.alpha);
        sb.append(", canOccludePresentation=");
        sb.append(this.canOccludePresentation);
        return sb.toString();
    }

    protected void finalize() throws Throwable {
        try {
            nativeDispose();
        } finally {
            super.finalize();
        }
    }

    public void replaceTouchableRegionWithCrop(SurfaceControl surfaceControl) {
        setTouchableRegionCrop(surfaceControl);
        this.replaceTouchableRegionWithCrop = true;
    }

    public void setTouchableRegionCrop(SurfaceControl surfaceControl) {
        this.touchableRegionSurfaceControl = new WeakReference<>(surfaceControl);
    }

    public void setTouchableRegion(Rect rect) {
        this.touchableRegion.set(rect);
    }

    public void setWindowToken(IBinder iBinder) {
        this.windowToken = iBinder;
    }

    public IBinder getWindowToken() {
        return this.windowToken;
    }

    public void setInputConfig(int i, boolean z) {
        if (z) {
            this.inputConfig = i | this.inputConfig;
        } else {
            this.inputConfig = (~i) & this.inputConfig;
        }
    }

    public void setTrustedOverlay(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, boolean z) {
        if (Flags.surfaceTrustedOverlay()) {
            transaction.setTrustedOverlay(surfaceControl, z);
        } else if (z) {
            this.inputConfig |= 256;
        }
    }
}
