package android.hardware;

import android.os.ParcelFileDescriptor;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class SerialPort {
    private static final String TAG = "SerialPort";
    private ParcelFileDescriptor mFileDescriptor;
    private final String mName;
    private int mNativeContext;

    private native void native_close();

    private native void native_open(FileDescriptor fileDescriptor, int i) throws IOException;

    private native int native_read_array(byte[] bArr, int i) throws IOException;

    private native int native_read_direct(ByteBuffer byteBuffer, int i) throws IOException;

    private native void native_send_break();

    private native void native_write_array(byte[] bArr, int i) throws IOException;

    private native void native_write_direct(ByteBuffer byteBuffer, int i) throws IOException;

    public SerialPort(String str) {
        this.mName = str;
    }

    public void open(ParcelFileDescriptor parcelFileDescriptor, int i) throws IOException {
        native_open(parcelFileDescriptor.getFileDescriptor(), i);
        this.mFileDescriptor = parcelFileDescriptor;
    }

    public void close() throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = this.mFileDescriptor;
        if (parcelFileDescriptor != null) {
            parcelFileDescriptor.close();
            this.mFileDescriptor = null;
        }
        native_close();
    }

    public String getName() {
        return this.mName;
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.isDirect()) {
            return native_read_direct(byteBuffer, byteBuffer.remaining());
        }
        if (byteBuffer.hasArray()) {
            return native_read_array(byteBuffer.array(), byteBuffer.remaining());
        }
        throw new IllegalArgumentException("buffer is not direct and has no array");
    }

    public void write(ByteBuffer byteBuffer, int i) throws IOException {
        if (byteBuffer.isDirect()) {
            native_write_direct(byteBuffer, i);
        } else {
            if (byteBuffer.hasArray()) {
                native_write_array(byteBuffer.array(), i);
                return;
            }
            throw new IllegalArgumentException("buffer is not direct and has no array");
        }
    }

    public void sendBreak() {
        native_send_break();
    }
}
