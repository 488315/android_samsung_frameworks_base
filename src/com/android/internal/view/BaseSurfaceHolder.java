package com.android.internal.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes4.dex */
public abstract class BaseSurfaceHolder implements SurfaceHolder {
    static final boolean DEBUG = false;
    private static final String TAG = "BaseSurfaceHolder";
    SurfaceHolder.Callback[] mGottenCallbacks;
    boolean mHaveGottenCallbacks;
    Rect mTmpDirty;
    public final ArrayList<SurfaceHolder.Callback> mCallbacks = new ArrayList<>();
    public final ReentrantLock mSurfaceLock = new ReentrantLock();
    public Surface mSurface = new Surface();
    int mRequestedWidth = -1;
    int mRequestedHeight = -1;
    protected int mRequestedFormat = -1;
    int mRequestedType = -1;
    long mLastLockTime = 0;
    int mType = -1;
    final Rect mSurfaceFrame = new Rect();

    public abstract boolean onAllowLockCanvas();

    public abstract void onRelayoutContainer();

    public abstract void onUpdateSurface();

    public int getRequestedWidth() {
        return this.mRequestedWidth;
    }

    public int getRequestedHeight() {
        return this.mRequestedHeight;
    }

    public int getRequestedFormat() {
        return this.mRequestedFormat;
    }

    public int getRequestedType() {
        return this.mRequestedType;
    }

    @Override // android.view.SurfaceHolder
    public void addCallback(SurfaceHolder.Callback callback) {
        synchronized (this.mCallbacks) {
            if (!this.mCallbacks.contains(callback)) {
                this.mCallbacks.add(callback);
            }
        }
    }

    @Override // android.view.SurfaceHolder
    public void removeCallback(SurfaceHolder.Callback callback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.remove(callback);
        }
    }

    public SurfaceHolder.Callback[] getCallbacks() {
        if (this.mHaveGottenCallbacks) {
            return this.mGottenCallbacks;
        }
        synchronized (this.mCallbacks) {
            int size = this.mCallbacks.size();
            if (size > 0) {
                SurfaceHolder.Callback[] callbackArr = this.mGottenCallbacks;
                if (callbackArr == null || callbackArr.length != size) {
                    this.mGottenCallbacks = new SurfaceHolder.Callback[size];
                }
                this.mCallbacks.toArray(this.mGottenCallbacks);
            } else {
                this.mGottenCallbacks = null;
            }
            this.mHaveGottenCallbacks = true;
        }
        return this.mGottenCallbacks;
    }

    public void ungetCallbacks() {
        this.mHaveGottenCallbacks = false;
    }

    @Override // android.view.SurfaceHolder
    public void setFixedSize(int i, int i2) {
        if (this.mRequestedWidth == i && this.mRequestedHeight == i2) {
            return;
        }
        this.mRequestedWidth = i;
        this.mRequestedHeight = i2;
        onRelayoutContainer();
    }

    @Override // android.view.SurfaceHolder
    public void setSizeFromLayout() {
        if (this.mRequestedWidth == -1 && this.mRequestedHeight == -1) {
            return;
        }
        this.mRequestedHeight = -1;
        this.mRequestedWidth = -1;
        onRelayoutContainer();
    }

    @Override // android.view.SurfaceHolder
    public void setFormat(int i) {
        if (this.mRequestedFormat != i) {
            this.mRequestedFormat = i;
            onUpdateSurface();
        }
    }

    @Override // android.view.SurfaceHolder
    public void setType(int i) {
        if (i == 1 || i == 2) {
            i = 0;
        }
        if ((i == 0 || i == 3) && this.mRequestedType != i) {
            this.mRequestedType = i;
            onUpdateSurface();
        }
    }

    @Override // android.view.SurfaceHolder
    public Canvas lockCanvas() {
        return internalLockCanvas(null, false);
    }

    @Override // android.view.SurfaceHolder
    public Canvas lockCanvas(Rect rect) {
        return internalLockCanvas(rect, false);
    }

    @Override // android.view.SurfaceHolder
    public Canvas lockHardwareCanvas() {
        return internalLockCanvas(null, true);
    }

    private final Canvas internalLockCanvas(Rect rect, boolean z) throws InterruptedException {
        Canvas canvasLockCanvas;
        if (this.mType == 3) {
            throw new SurfaceHolder.BadSurfaceTypeException("Surface type is SURFACE_TYPE_PUSH_BUFFERS");
        }
        this.mSurfaceLock.lock();
        if (onAllowLockCanvas()) {
            if (rect == null) {
                if (this.mTmpDirty == null) {
                    this.mTmpDirty = new Rect();
                }
                this.mTmpDirty.set(this.mSurfaceFrame);
                rect = this.mTmpDirty;
            }
            try {
                if (z) {
                    canvasLockCanvas = this.mSurface.lockHardwareCanvas();
                } else {
                    canvasLockCanvas = this.mSurface.lockCanvas(rect);
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception locking surface", e);
            }
        } else {
            canvasLockCanvas = null;
        }
        if (canvasLockCanvas != null) {
            this.mLastLockTime = SystemClock.uptimeMillis();
            return canvasLockCanvas;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        long j = this.mLastLockTime + 100;
        if (j > jUptimeMillis) {
            try {
                Thread.sleep(j - jUptimeMillis);
            } catch (InterruptedException unused) {
            }
            jUptimeMillis = SystemClock.uptimeMillis();
        }
        this.mLastLockTime = jUptimeMillis;
        this.mSurfaceLock.unlock();
        return null;
    }

    @Override // android.view.SurfaceHolder
    public void unlockCanvasAndPost(Canvas canvas) {
        this.mSurface.unlockCanvasAndPost(canvas);
        this.mSurfaceLock.unlock();
    }

    @Override // android.view.SurfaceHolder
    public Surface getSurface() {
        return this.mSurface;
    }

    @Override // android.view.SurfaceHolder
    public Rect getSurfaceFrame() {
        return this.mSurfaceFrame;
    }

    public void setSurfaceFrameSize(int i, int i2) {
        this.mSurfaceFrame.top = 0;
        this.mSurfaceFrame.left = 0;
        this.mSurfaceFrame.right = i;
        this.mSurfaceFrame.bottom = i2;
    }
}
