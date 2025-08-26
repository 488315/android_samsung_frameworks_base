package android.filterfw.core;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLES20;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class GLFrame extends Frame {
    public static final int EXISTING_FBO_BINDING = 101;
    public static final int EXISTING_TEXTURE_BINDING = 100;
    public static final int EXTERNAL_TEXTURE = 104;
    public static final int NEW_FBO_BINDING = 103;
    public static final int NEW_TEXTURE_BINDING = 102;
    private int glFrameId;
    private GLEnvironment mGLEnvironment;
    private boolean mOwnsTexture;

    private native boolean generateNativeMipMap();

    private native boolean getNativeBitmap(Bitmap bitmap);

    private native byte[] getNativeData();

    private native int getNativeFboId();

    private native float[] getNativeFloats();

    private native int[] getNativeInts();

    private native int getNativeTextureId();

    private native boolean nativeAllocate(GLEnvironment gLEnvironment, int i, int i2);

    private native boolean nativeAllocateExternal(GLEnvironment gLEnvironment);

    private native boolean nativeAllocateWithFbo(GLEnvironment gLEnvironment, int i, int i2, int i3);

    private native boolean nativeAllocateWithTexture(GLEnvironment gLEnvironment, int i, int i2, int i3);

    private native boolean nativeCopyFromGL(GLFrame gLFrame);

    private native boolean nativeCopyFromNative(NativeFrame nativeFrame);

    private native boolean nativeDeallocate();

    private native boolean nativeDetachTexFromFbo();

    private native boolean nativeFocus();

    private native boolean nativeReattachTexToFbo();

    private native boolean nativeResetParams();

    private native boolean setNativeBitmap(Bitmap bitmap, int i);

    private native boolean setNativeData(byte[] bArr, int i, int i2);

    private native boolean setNativeFloats(float[] fArr);

    private native boolean setNativeInts(int[] iArr);

    private native boolean setNativeTextureParam(int i, int i2);

    private native boolean setNativeViewport(int i, int i2, int i3, int i4);

    GLFrame(FrameFormat frameFormat, FrameManager frameManager) {
        super(frameFormat, frameManager);
        this.glFrameId = -1;
        this.mOwnsTexture = true;
    }

    GLFrame(FrameFormat frameFormat, FrameManager frameManager, int i, long j) {
        super(frameFormat, frameManager, i, j);
        this.glFrameId = -1;
        this.mOwnsTexture = true;
    }

    void init(GLEnvironment gLEnvironment) {
        FrameFormat format = getFormat();
        this.mGLEnvironment = gLEnvironment;
        if (format.getBytesPerSample() != 4) {
            throw new IllegalArgumentException("GL frames must have 4 bytes per sample!");
        }
        if (format.getDimensionCount() != 2) {
            throw new IllegalArgumentException("GL frames must be 2-dimensional!");
        }
        if (getFormat().getSize() < 0) {
            throw new IllegalArgumentException("Initializing GL frame with zero size!");
        }
        int bindingType = getBindingType();
        boolean z = false;
        if (bindingType == 0) {
            initNew(false);
        } else {
            if (bindingType == 104) {
                initNew(true);
                setReusable(z);
            }
            if (bindingType == 100) {
                initWithTexture((int) getBindingId());
            } else if (bindingType == 101) {
                initWithFbo((int) getBindingId());
            } else if (bindingType == 102) {
                initWithTexture((int) getBindingId());
            } else if (bindingType == 103) {
                initWithFbo((int) getBindingId());
            } else {
                throw new RuntimeException("Attempting to create GL frame with unknown binding type " + bindingType + "!");
            }
        }
        z = true;
        setReusable(z);
    }

    private void initNew(boolean z) {
        if (z) {
            if (!nativeAllocateExternal(this.mGLEnvironment)) {
                throw new RuntimeException("Could not allocate external GL frame!");
            }
        } else if (!nativeAllocate(this.mGLEnvironment, getFormat().getWidth(), getFormat().getHeight())) {
            throw new RuntimeException("Could not allocate GL frame!");
        }
    }

    private void initWithTexture(int i) {
        if (!nativeAllocateWithTexture(this.mGLEnvironment, i, getFormat().getWidth(), getFormat().getHeight())) {
            throw new RuntimeException("Could not allocate texture backed GL frame!");
        }
        this.mOwnsTexture = false;
        markReadOnly();
    }

    private void initWithFbo(int i) {
        if (!nativeAllocateWithFbo(this.mGLEnvironment, i, getFormat().getWidth(), getFormat().getHeight())) {
            throw new RuntimeException("Could not allocate FBO backed GL frame!");
        }
    }

    void flushGPU(String str) {
        StopWatchMap stopWatchMap = GLFrameTimer.get();
        if (stopWatchMap.LOG_MFF_RUNNING_TIMES) {
            stopWatchMap.start("glFinish " + str);
            GLES20.glFinish();
            stopWatchMap.stop("glFinish " + str);
        }
    }

    @Override // android.filterfw.core.Frame
    protected synchronized boolean hasNativeAllocation() {
        return this.glFrameId != -1;
    }

    @Override // android.filterfw.core.Frame
    protected synchronized void releaseNativeAllocation() {
        nativeDeallocate();
        this.glFrameId = -1;
    }

    public GLEnvironment getGLEnvironment() {
        return this.mGLEnvironment;
    }

    @Override // android.filterfw.core.Frame
    public Object getObjectValue() {
        assertGLEnvValid();
        return ByteBuffer.wrap(getNativeData());
    }

    @Override // android.filterfw.core.Frame
    public void setInts(int[] iArr) {
        assertFrameMutable();
        assertGLEnvValid();
        if (!setNativeInts(iArr)) {
            throw new RuntimeException("Could not set int values for GL frame!");
        }
    }

    @Override // android.filterfw.core.Frame
    public int[] getInts() {
        assertGLEnvValid();
        flushGPU("getInts");
        return getNativeInts();
    }

    @Override // android.filterfw.core.Frame
    public void setFloats(float[] fArr) {
        assertFrameMutable();
        assertGLEnvValid();
        if (!setNativeFloats(fArr)) {
            throw new RuntimeException("Could not set int values for GL frame!");
        }
    }

    @Override // android.filterfw.core.Frame
    public float[] getFloats() {
        assertGLEnvValid();
        flushGPU("getFloats");
        return getNativeFloats();
    }

    @Override // android.filterfw.core.Frame
    public void setData(ByteBuffer byteBuffer, int i, int i2) {
        assertFrameMutable();
        assertGLEnvValid();
        byte[] bArrArray = byteBuffer.array();
        if (getFormat().getSize() != bArrArray.length) {
            throw new RuntimeException("Data size in setData does not match GL frame size!");
        }
        if (!setNativeData(bArrArray, i, i2)) {
            throw new RuntimeException("Could not set GL frame data!");
        }
    }

    @Override // android.filterfw.core.Frame
    public ByteBuffer getData() {
        assertGLEnvValid();
        flushGPU("getData");
        return ByteBuffer.wrap(getNativeData());
    }

    @Override // android.filterfw.core.Frame
    public void setBitmap(Bitmap bitmap) {
        assertFrameMutable();
        assertGLEnvValid();
        if (getFormat().getWidth() != bitmap.getWidth() || getFormat().getHeight() != bitmap.getHeight()) {
            throw new RuntimeException("Bitmap dimensions do not match GL frame dimensions!");
        }
        Bitmap bitmapConvertBitmapToRGBA = convertBitmapToRGBA(bitmap);
        if (!setNativeBitmap(bitmapConvertBitmapToRGBA, bitmapConvertBitmapToRGBA.getByteCount())) {
            throw new RuntimeException("Could not set GL frame bitmap data!");
        }
    }

    @Override // android.filterfw.core.Frame
    public Bitmap getBitmap() {
        assertGLEnvValid();
        flushGPU("getBitmap");
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(getFormat().getWidth(), getFormat().getHeight(), Bitmap.Config.ARGB_8888);
        if (getNativeBitmap(bitmapCreateBitmap)) {
            return bitmapCreateBitmap;
        }
        throw new RuntimeException("Could not get bitmap data from GL frame!");
    }

    @Override // android.filterfw.core.Frame
    public void setDataFromFrame(Frame frame) {
        assertGLEnvValid();
        if (getFormat().getSize() < frame.getFormat().getSize()) {
            throw new RuntimeException("Attempting to assign frame of size " + frame.getFormat().getSize() + " to smaller GL frame of size " + getFormat().getSize() + "!");
        }
        if (frame instanceof NativeFrame) {
            nativeCopyFromNative((NativeFrame) frame);
            return;
        }
        if (frame instanceof GLFrame) {
            nativeCopyFromGL((GLFrame) frame);
        } else if (frame instanceof SimpleFrame) {
            setObjectValue(frame.getObjectValue());
        } else {
            super.setDataFromFrame(frame);
        }
    }

    public void setViewport(int i, int i2, int i3, int i4) {
        assertFrameMutable();
        setNativeViewport(i, i2, i3, i4);
    }

    public void setViewport(Rect rect) {
        assertFrameMutable();
        setNativeViewport(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
    }

    public void generateMipMap() {
        assertFrameMutable();
        assertGLEnvValid();
        if (!generateNativeMipMap()) {
            throw new RuntimeException("Could not generate mip-map for GL frame!");
        }
    }

    public void setTextureParameter(int i, int i2) {
        assertFrameMutable();
        assertGLEnvValid();
        if (setNativeTextureParam(i, i2)) {
            return;
        }
        throw new RuntimeException("Could not set texture value " + i + " = " + i2 + " for GLFrame!");
    }

    public int getTextureId() {
        return getNativeTextureId();
    }

    public int getFboId() {
        return getNativeFboId();
    }

    public void focus() {
        if (!nativeFocus()) {
            throw new RuntimeException("Could not focus on GLFrame for drawing!");
        }
    }

    public String toString() {
        return "GLFrame id: " + this.glFrameId + " (" + getFormat() + ") with texture ID " + getTextureId() + ", FBO ID " + getFboId();
    }

    @Override // android.filterfw.core.Frame
    protected void reset(FrameFormat frameFormat) {
        if (!nativeResetParams()) {
            throw new RuntimeException("Could not reset GLFrame texture parameters!");
        }
        super.reset(frameFormat);
    }

    @Override // android.filterfw.core.Frame
    protected void onFrameStore() {
        if (this.mOwnsTexture) {
            return;
        }
        nativeDetachTexFromFbo();
    }

    @Override // android.filterfw.core.Frame
    protected void onFrameFetch() {
        if (this.mOwnsTexture) {
            return;
        }
        nativeReattachTexToFbo();
    }

    private void assertGLEnvValid() {
        if (this.mGLEnvironment.isContextActive()) {
            return;
        }
        if (GLEnvironment.isAnyContextActive()) {
            throw new RuntimeException("Attempting to access " + this + " with foreign GL context active!");
        }
        throw new RuntimeException("Attempting to access " + this + " with no GL context  active!");
    }

    static {
        System.loadLibrary("filterfw");
    }
}
