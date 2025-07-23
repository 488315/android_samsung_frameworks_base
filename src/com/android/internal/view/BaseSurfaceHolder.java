package com.android.internal.view;

import android.graphics.Canvas;
import android.graphics.Rect;
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final android.graphics.Canvas internalLockCanvas(android.graphics.Rect r7, boolean r8) {
        /*
            r6 = this;
            int r0 = r6.mType
            r1 = 3
            if (r0 == r1) goto L66
            java.util.concurrent.locks.ReentrantLock r0 = r6.mSurfaceLock
            r0.lock()
            boolean r0 = r6.onAllowLockCanvas()
            r1 = 0
            if (r0 == 0) goto L3f
            if (r7 != 0) goto L27
            android.graphics.Rect r7 = r6.mTmpDirty
            if (r7 != 0) goto L1e
            android.graphics.Rect r7 = new android.graphics.Rect
            r7.<init>()
            r6.mTmpDirty = r7
        L1e:
            android.graphics.Rect r7 = r6.mTmpDirty
            android.graphics.Rect r0 = r6.mSurfaceFrame
            r7.set(r0)
            android.graphics.Rect r7 = r6.mTmpDirty
        L27:
            if (r8 == 0) goto L30
            android.view.Surface r7 = r6.mSurface     // Catch: java.lang.Exception -> L37
            android.graphics.Canvas r7 = r7.lockHardwareCanvas()     // Catch: java.lang.Exception -> L37
            goto L40
        L30:
            android.view.Surface r8 = r6.mSurface     // Catch: java.lang.Exception -> L37
            android.graphics.Canvas r7 = r8.lockCanvas(r7)     // Catch: java.lang.Exception -> L37
            goto L40
        L37:
            r7 = move-exception
            java.lang.String r8 = "BaseSurfaceHolder"
            java.lang.String r0 = "Exception locking surface"
            android.util.Log.e(r8, r0, r7)
        L3f:
            r7 = r1
        L40:
            if (r7 == 0) goto L49
            long r0 = android.os.SystemClock.uptimeMillis()
            r6.mLastLockTime = r0
            return r7
        L49:
            long r7 = android.os.SystemClock.uptimeMillis()
            long r2 = r6.mLastLockTime
            r4 = 100
            long r2 = r2 + r4
            int r0 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r0 <= 0) goto L5e
            long r2 = r2 - r7
            java.lang.Thread.sleep(r2)     // Catch: java.lang.InterruptedException -> L5a
        L5a:
            long r7 = android.os.SystemClock.uptimeMillis()
        L5e:
            r6.mLastLockTime = r7
            java.util.concurrent.locks.ReentrantLock r6 = r6.mSurfaceLock
            r6.unlock()
            return r1
        L66:
            android.view.SurfaceHolder$BadSurfaceTypeException r6 = new android.view.SurfaceHolder$BadSurfaceTypeException
            java.lang.String r7 = "Surface type is SURFACE_TYPE_PUSH_BUFFERS"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.view.BaseSurfaceHolder.internalLockCanvas(android.graphics.Rect, boolean):android.graphics.Canvas");
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
