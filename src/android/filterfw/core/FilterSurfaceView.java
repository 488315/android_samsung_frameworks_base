package android.filterfw.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/* loaded from: classes.dex */
public class FilterSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static int STATE_ALLOCATED = 0;
    private static int STATE_CREATED = 1;
    private static int STATE_INITIALIZED = 2;
    private int mFormat;
    private GLEnvironment mGLEnv;
    private int mHeight;
    private SurfaceHolder.Callback mListener;
    private int mState;
    private int mSurfaceId;
    private int mWidth;

    public FilterSurfaceView(Context context) {
        super(context);
        this.mState = STATE_ALLOCATED;
        this.mSurfaceId = -1;
        getHolder().addCallback(this);
    }

    public FilterSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mState = STATE_ALLOCATED;
        this.mSurfaceId = -1;
        getHolder().addCallback(this);
    }

    public synchronized void bindToListener(SurfaceHolder.Callback callback, GLEnvironment gLEnvironment) {
        if (callback == null) {
            throw new NullPointerException("Attempting to bind null filter to SurfaceView!");
        }
        SurfaceHolder.Callback callback2 = this.mListener;
        if (callback2 != null && callback2 != callback) {
            throw new RuntimeException("Attempting to bind filter " + callback + " to SurfaceView with another open filter " + this.mListener + " attached already!");
        }
        this.mListener = callback;
        GLEnvironment gLEnvironment2 = this.mGLEnv;
        if (gLEnvironment2 != null && gLEnvironment2 != gLEnvironment) {
            gLEnvironment2.unregisterSurfaceId(this.mSurfaceId);
        }
        this.mGLEnv = gLEnvironment;
        if (this.mState >= STATE_CREATED) {
            registerSurface();
            this.mListener.surfaceCreated(getHolder());
            if (this.mState == STATE_INITIALIZED) {
                this.mListener.surfaceChanged(getHolder(), this.mFormat, this.mWidth, this.mHeight);
            }
        }
    }

    public synchronized void unbind() {
        this.mListener = null;
    }

    public synchronized int getSurfaceId() {
        return this.mSurfaceId;
    }

    public synchronized GLEnvironment getGLEnv() {
        return this.mGLEnv;
    }

    @Override // android.view.SurfaceHolder.Callback
    public synchronized void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mState = STATE_CREATED;
        if (this.mGLEnv != null) {
            registerSurface();
        }
        SurfaceHolder.Callback callback = this.mListener;
        if (callback != null) {
            callback.surfaceCreated(surfaceHolder);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public synchronized void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.mFormat = i;
        this.mWidth = i2;
        this.mHeight = i3;
        this.mState = STATE_INITIALIZED;
        SurfaceHolder.Callback callback = this.mListener;
        if (callback != null) {
            callback.surfaceChanged(surfaceHolder, i, i2, i3);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public synchronized void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.mState = STATE_ALLOCATED;
        SurfaceHolder.Callback callback = this.mListener;
        if (callback != null) {
            callback.surfaceDestroyed(surfaceHolder);
        }
        unregisterSurface();
    }

    private void registerSurface() {
        int iRegisterSurface = this.mGLEnv.registerSurface(getHolder().getSurface());
        this.mSurfaceId = iRegisterSurface;
        if (iRegisterSurface >= 0) {
            return;
        }
        throw new RuntimeException("Could not register Surface: " + getHolder().getSurface() + " in FilterSurfaceView!");
    }

    private void unregisterSurface() {
        int i;
        GLEnvironment gLEnvironment = this.mGLEnv;
        if (gLEnvironment == null || (i = this.mSurfaceId) <= 0) {
            return;
        }
        gLEnvironment.unregisterSurfaceId(i);
    }
}
