package android.filterfw.core;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class NativeFrame extends Frame {
    private int nativeFrameId;

    private native boolean getNativeBitmap(Bitmap bitmap, int i, int i2);

    private native boolean getNativeBuffer(NativeBuffer nativeBuffer);

    private native int getNativeCapacity();

    private native byte[] getNativeData(int i);

    private native float[] getNativeFloats(int i);

    private native int[] getNativeInts(int i);

    private native boolean nativeAllocate(int i);

    private native boolean nativeCopyFromGL(GLFrame gLFrame);

    private native boolean nativeCopyFromNative(NativeFrame nativeFrame);

    private native boolean nativeDeallocate();

    private static native int nativeFloatSize();

    private static native int nativeIntSize();

    private native boolean setNativeBitmap(Bitmap bitmap, int i, int i2);

    private native boolean setNativeData(byte[] bArr, int i, int i2);

    private native boolean setNativeFloats(float[] fArr);

    private native boolean setNativeInts(int[] iArr);

    NativeFrame(FrameFormat frameFormat, FrameManager frameManager) {
        super(frameFormat, frameManager);
        this.nativeFrameId = -1;
        int size = frameFormat.getSize();
        nativeAllocate(size);
        setReusable(size != 0);
    }

    @Override // android.filterfw.core.Frame
    protected synchronized void releaseNativeAllocation() {
        nativeDeallocate();
        this.nativeFrameId = -1;
    }

    @Override // android.filterfw.core.Frame
    protected synchronized boolean hasNativeAllocation() {
        return this.nativeFrameId != -1;
    }

    @Override // android.filterfw.core.Frame
    public int getCapacity() {
        return getNativeCapacity();
    }

    @Override // android.filterfw.core.Frame
    public Object getObjectValue() {
        if (getFormat().getBaseType() != 8) {
            return getData();
        }
        Class objectClass = getFormat().getObjectClass();
        if (objectClass == null) {
            throw new RuntimeException("Attempting to get object data from frame that does not specify a structure object class!");
        }
        if (!NativeBuffer.class.isAssignableFrom(objectClass)) {
            throw new RuntimeException("NativeFrame object class must be a subclass of NativeBuffer!");
        }
        try {
            NativeBuffer nativeBuffer = (NativeBuffer) objectClass.newInstance();
            if (!getNativeBuffer(nativeBuffer)) {
                throw new RuntimeException("Could not get the native structured data for frame!");
            }
            nativeBuffer.attachToFrame(this);
            return nativeBuffer;
        } catch (Exception unused) {
            throw new RuntimeException("Could not instantiate new structure instance of type '" + objectClass + "'!");
        }
    }

    @Override // android.filterfw.core.Frame
    public void setInts(int[] iArr) {
        assertFrameMutable();
        if (iArr.length * nativeIntSize() > getFormat().getSize()) {
            throw new RuntimeException("NativeFrame cannot hold " + iArr.length + " integers. (Can only hold " + (getFormat().getSize() / nativeIntSize()) + " integers).");
        }
        if (!setNativeInts(iArr)) {
            throw new RuntimeException("Could not set int values for native frame!");
        }
    }

    @Override // android.filterfw.core.Frame
    public int[] getInts() {
        return getNativeInts(getFormat().getSize());
    }

    @Override // android.filterfw.core.Frame
    public void setFloats(float[] fArr) {
        assertFrameMutable();
        if (fArr.length * nativeFloatSize() > getFormat().getSize()) {
            throw new RuntimeException("NativeFrame cannot hold " + fArr.length + " floats. (Can only hold " + (getFormat().getSize() / nativeFloatSize()) + " floats).");
        }
        if (!setNativeFloats(fArr)) {
            throw new RuntimeException("Could not set int values for native frame!");
        }
    }

    @Override // android.filterfw.core.Frame
    public float[] getFloats() {
        return getNativeFloats(getFormat().getSize());
    }

    @Override // android.filterfw.core.Frame
    public void setData(ByteBuffer byteBuffer, int i, int i2) {
        assertFrameMutable();
        byte[] array = byteBuffer.array();
        int i3 = i2 + i;
        if (i3 > byteBuffer.limit()) {
            throw new RuntimeException("Offset and length exceed buffer size in native setData: " + i3 + " bytes given, but only " + byteBuffer.limit() + " bytes available!");
        }
        if (getFormat().getSize() != i2) {
            throw new RuntimeException("Data size in setData does not match native frame size: Frame size is " + getFormat().getSize() + " bytes, but " + i2 + " bytes given!");
        }
        if (!setNativeData(array, i, i2)) {
            throw new RuntimeException("Could not set native frame data!");
        }
    }

    @Override // android.filterfw.core.Frame
    public ByteBuffer getData() {
        byte[] nativeData = getNativeData(getFormat().getSize());
        if (nativeData == null) {
            return null;
        }
        return ByteBuffer.wrap(nativeData);
    }

    @Override // android.filterfw.core.Frame
    public void setBitmap(Bitmap bitmap) {
        assertFrameMutable();
        if (getFormat().getNumberOfDimensions() != 2) {
            throw new RuntimeException("Attempting to set Bitmap for non 2-dimensional native frame!");
        }
        if (getFormat().getWidth() != bitmap.getWidth() || getFormat().getHeight() != bitmap.getHeight()) {
            throw new RuntimeException("Bitmap dimensions do not match native frame dimensions!");
        }
        Bitmap convertBitmapToRGBA = convertBitmapToRGBA(bitmap);
        if (!setNativeBitmap(convertBitmapToRGBA, convertBitmapToRGBA.getByteCount(), getFormat().getBytesPerSample())) {
            throw new RuntimeException("Could not set native frame bitmap data!");
        }
    }

    @Override // android.filterfw.core.Frame
    public Bitmap getBitmap() {
        if (getFormat().getNumberOfDimensions() != 2) {
            throw new RuntimeException("Attempting to get Bitmap for non 2-dimensional native frame!");
        }
        Bitmap createBitmap = Bitmap.createBitmap(getFormat().getWidth(), getFormat().getHeight(), Bitmap.Config.ARGB_8888);
        if (getNativeBitmap(createBitmap, createBitmap.getByteCount(), getFormat().getBytesPerSample())) {
            return createBitmap;
        }
        throw new RuntimeException("Could not get bitmap data from native frame!");
    }

    @Override // android.filterfw.core.Frame
    public void setDataFromFrame(Frame frame) {
        if (getFormat().getSize() < frame.getFormat().getSize()) {
            throw new RuntimeException("Attempting to assign frame of size " + frame.getFormat().getSize() + " to smaller native frame of size " + getFormat().getSize() + "!");
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

    public String toString() {
        return "NativeFrame id: " + this.nativeFrameId + " (" + getFormat() + ") of size " + getCapacity();
    }

    static {
        System.loadLibrary("filterfw");
    }
}
