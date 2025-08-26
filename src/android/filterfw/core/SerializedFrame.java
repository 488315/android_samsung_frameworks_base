package android.filterfw.core;

import android.filterfw.format.ObjectFormat;
import android.graphics.Bitmap;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class SerializedFrame extends Frame {
    private static final int INITIAL_CAPACITY = 64;
    private DirectByteOutputStream mByteOutputStream;
    private ObjectOutputStream mObjectOut;

    @Override // android.filterfw.core.Frame
    protected boolean hasNativeAllocation() {
        return false;
    }

    @Override // android.filterfw.core.Frame
    protected void releaseNativeAllocation() {
    }

    private class DirectByteOutputStream extends OutputStream {
        private byte[] mBuffer;
        private int mOffset = 0;
        private int mDataOffset = 0;

        public DirectByteOutputStream(int i) {
            this.mBuffer = null;
            this.mBuffer = new byte[i];
        }

        private final void ensureFit(int i) {
            int i2 = this.mOffset;
            int i3 = i2 + i;
            byte[] bArr = this.mBuffer;
            if (i3 > bArr.length) {
                byte[] bArr2 = new byte[Math.max(i2 + i, bArr.length * 2)];
                this.mBuffer = bArr2;
                System.arraycopy(bArr, 0, bArr2, 0, this.mOffset);
            }
        }

        public final void markHeaderEnd() {
            this.mDataOffset = this.mOffset;
        }

        public final int getSize() {
            return this.mOffset;
        }

        public byte[] getByteArray() {
            return this.mBuffer;
        }

        @Override // java.io.OutputStream
        public final void write(byte[] bArr) {
            write(bArr, 0, bArr.length);
        }

        @Override // java.io.OutputStream
        public final void write(byte[] bArr, int i, int i2) {
            ensureFit(i2);
            System.arraycopy(bArr, i, this.mBuffer, this.mOffset, i2);
            this.mOffset += i2;
        }

        @Override // java.io.OutputStream
        public final void write(int i) {
            ensureFit(1);
            byte[] bArr = this.mBuffer;
            int i2 = this.mOffset;
            this.mOffset = i2 + 1;
            bArr[i2] = (byte) i;
        }

        public final void reset() {
            this.mOffset = this.mDataOffset;
        }

        public final DirectByteInputStream getInputStream() {
            return new DirectByteInputStream(SerializedFrame.this, this.mBuffer, this.mOffset);
        }
    }

    private class DirectByteInputStream extends InputStream {
        private byte[] mBuffer;
        private int mPos = 0;
        private int mSize;

        public DirectByteInputStream(SerializedFrame serializedFrame, byte[] bArr, int i) {
            this.mBuffer = bArr;
            this.mSize = i;
        }

        @Override // java.io.InputStream
        public final int available() {
            return this.mSize - this.mPos;
        }

        @Override // java.io.InputStream
        public final int read() {
            int i = this.mPos;
            if (i >= this.mSize) {
                return -1;
            }
            byte[] bArr = this.mBuffer;
            this.mPos = i + 1;
            return bArr[i] & 255;
        }

        @Override // java.io.InputStream
        public final int read(byte[] bArr, int i, int i2) {
            int i3 = this.mPos;
            int i4 = this.mSize;
            if (i3 >= i4) {
                return -1;
            }
            if (i3 + i2 > i4) {
                i2 = i4 - i3;
            }
            System.arraycopy(this.mBuffer, i3, bArr, i, i2);
            this.mPos += i2;
            return i2;
        }

        @Override // java.io.InputStream
        public final long skip(long j) {
            int i = this.mPos;
            long j2 = i + j;
            int i2 = this.mSize;
            if (j2 > i2) {
                j = i2 - i;
            }
            if (j < 0) {
                return 0L;
            }
            this.mPos = (int) (i + j);
            return j;
        }
    }

    SerializedFrame(FrameFormat frameFormat, FrameManager frameManager) {
        super(frameFormat, frameManager);
        setReusable(false);
        try {
            this.mByteOutputStream = new DirectByteOutputStream(64);
            this.mObjectOut = new ObjectOutputStream(this.mByteOutputStream);
            this.mByteOutputStream.markHeaderEnd();
        } catch (IOException e) {
            throw new RuntimeException("Could not create serialization streams for SerializedFrame!", e);
        }
    }

    static SerializedFrame wrapObject(Object obj, FrameManager frameManager) {
        SerializedFrame serializedFrame = new SerializedFrame(ObjectFormat.fromObject(obj, 1), frameManager);
        serializedFrame.setObjectValue(obj);
        return serializedFrame;
    }

    @Override // android.filterfw.core.Frame
    public Object getObjectValue() {
        return deserializeObjectValue();
    }

    @Override // android.filterfw.core.Frame
    public void setInts(int[] iArr) throws IOException {
        assertFrameMutable();
        setGenericObjectValue(iArr);
    }

    @Override // android.filterfw.core.Frame
    public int[] getInts() {
        Object objDeserializeObjectValue = deserializeObjectValue();
        if (objDeserializeObjectValue instanceof int[]) {
            return (int[]) objDeserializeObjectValue;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setFloats(float[] fArr) throws IOException {
        assertFrameMutable();
        setGenericObjectValue(fArr);
    }

    @Override // android.filterfw.core.Frame
    public float[] getFloats() {
        Object objDeserializeObjectValue = deserializeObjectValue();
        if (objDeserializeObjectValue instanceof float[]) {
            return (float[]) objDeserializeObjectValue;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setData(ByteBuffer byteBuffer, int i, int i2) throws IOException {
        assertFrameMutable();
        setGenericObjectValue(ByteBuffer.wrap(byteBuffer.array(), i, i2));
    }

    @Override // android.filterfw.core.Frame
    public ByteBuffer getData() {
        Object objDeserializeObjectValue = deserializeObjectValue();
        if (objDeserializeObjectValue instanceof ByteBuffer) {
            return (ByteBuffer) objDeserializeObjectValue;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setBitmap(Bitmap bitmap) throws IOException {
        assertFrameMutable();
        setGenericObjectValue(bitmap);
    }

    @Override // android.filterfw.core.Frame
    public Bitmap getBitmap() {
        Object objDeserializeObjectValue = deserializeObjectValue();
        if (objDeserializeObjectValue instanceof Bitmap) {
            return (Bitmap) objDeserializeObjectValue;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    protected void setGenericObjectValue(Object obj) throws IOException {
        serializeObjectValue(obj);
    }

    private final void serializeObjectValue(Object obj) throws IOException {
        try {
            this.mByteOutputStream.reset();
            this.mObjectOut.writeObject(obj);
            this.mObjectOut.flush();
            this.mObjectOut.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not serialize object " + obj + " in " + this + "!", e);
        }
    }

    private final Object deserializeObjectValue() {
        try {
            return new ObjectInputStream(this.mByteOutputStream.getInputStream()).readObject();
        } catch (IOException e) {
            throw new RuntimeException("Could not deserialize object in " + this + "!", e);
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException("Unable to deserialize object of unknown class in " + this + "!", e2);
        }
    }

    public String toString() {
        return "SerializedFrame (" + getFormat() + NavigationBarInflaterView.KEY_CODE_END;
    }
}
