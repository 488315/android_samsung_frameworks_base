package android.media.midi;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public final class MidiInputPort extends MidiReceiver implements Closeable {
    private static final String TAG = "MidiInputPort";
    private final byte[] mBuffer;
    private IMidiDeviceServer mDeviceServer;
    private FileDescriptor mFileDescriptor;
    private final CloseGuard mGuard;
    private boolean mIsClosed;
    private FileOutputStream mOutputStream;
    private final int mPortNumber;
    private final IBinder mToken;
    private AtomicInteger mTotalBytes;

    MidiInputPort(IMidiDeviceServer iMidiDeviceServer, IBinder iBinder, FileDescriptor fileDescriptor, int i) {
        super(1015);
        CloseGuard closeGuard = CloseGuard.get();
        this.mGuard = closeGuard;
        this.mTotalBytes = new AtomicInteger();
        this.mBuffer = new byte[1024];
        this.mDeviceServer = iMidiDeviceServer;
        this.mToken = iBinder;
        this.mFileDescriptor = fileDescriptor;
        this.mPortNumber = i;
        this.mOutputStream = new FileOutputStream(fileDescriptor);
        closeGuard.open("close");
    }

    MidiInputPort(FileDescriptor fileDescriptor, int i) {
        this(null, null, fileDescriptor, i);
    }

    public final int getPortNumber() {
        return this.mPortNumber;
    }

    @Override // android.media.midi.MidiReceiver
    public void onSend(byte[] bArr, int i, int i2, long j) throws IOException {
        if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
            throw new IllegalArgumentException("offset or count out of range");
        }
        if (i2 > 1015) {
            throw new IllegalArgumentException("count exceeds max message size");
        }
        synchronized (this.mBuffer) {
            if (this.mOutputStream == null) {
                throw new IOException("MidiInputPort is closed");
            }
            int iPackData = MidiPortImpl.packData(bArr, i, i2, j, this.mBuffer);
            this.mOutputStream.write(this.mBuffer, 0, iPackData);
            this.mTotalBytes.addAndGet(iPackData);
        }
    }

    @Override // android.media.midi.MidiReceiver
    public void onFlush() throws IOException {
        synchronized (this.mBuffer) {
            if (this.mOutputStream == null) {
                throw new IOException("MidiInputPort is closed");
            }
            this.mOutputStream.write(this.mBuffer, 0, MidiPortImpl.packFlush(this.mBuffer));
        }
    }

    FileDescriptor claimFileDescriptor() {
        synchronized (this.mGuard) {
            synchronized (this.mBuffer) {
                FileDescriptor fileDescriptor = this.mFileDescriptor;
                if (fileDescriptor == null) {
                    return null;
                }
                IoUtils.closeQuietly(this.mOutputStream);
                this.mFileDescriptor = null;
                this.mOutputStream = null;
                this.mIsClosed = true;
                return fileDescriptor;
            }
        }
    }

    IBinder getToken() {
        return this.mToken;
    }

    IMidiDeviceServer getDeviceServer() {
        return this.mDeviceServer;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.mGuard) {
            if (this.mIsClosed) {
                return;
            }
            this.mGuard.close();
            synchronized (this.mBuffer) {
                FileDescriptor fileDescriptor = this.mFileDescriptor;
                if (fileDescriptor != null) {
                    IoUtils.closeQuietly(fileDescriptor);
                    this.mFileDescriptor = null;
                }
                FileOutputStream fileOutputStream = this.mOutputStream;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    this.mOutputStream = null;
                }
            }
            IMidiDeviceServer iMidiDeviceServer = this.mDeviceServer;
            if (iMidiDeviceServer != null) {
                try {
                    iMidiDeviceServer.closePort(this.mToken);
                } catch (RemoteException unused) {
                    Log.e(TAG, "RemoteException in MidiInputPort.close()");
                }
            }
            this.mIsClosed = true;
        }
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            this.mDeviceServer = null;
            close();
        } finally {
            super.finalize();
        }
    }

    public int pullTotalBytesCount() {
        return this.mTotalBytes.getAndSet(0);
    }
}
