package android.media.midi;

import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.midi.MidiDispatcher;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public final class MidiOutputPort extends MidiSender implements Closeable {
    private static final String TAG = "MidiOutputPort";
    private IMidiDeviceServer mDeviceServer;
    private final MidiDispatcher mDispatcher;
    private final CloseGuard mGuard;
    private final FileInputStream mInputStream;
    private boolean mIsClosed;
    private final int mPortNumber;
    private final Thread mThread;
    private final IBinder mToken;
    private AtomicInteger mTotalBytes;

    MidiOutputPort(IMidiDeviceServer iMidiDeviceServer, IBinder iBinder, FileDescriptor fileDescriptor, int i) {
        this.mDispatcher = new MidiDispatcher();
        CloseGuard closeGuard = CloseGuard.get();
        this.mGuard = closeGuard;
        this.mTotalBytes = new AtomicInteger();
        Thread thread = new Thread() { // from class: android.media.midi.MidiOutputPort.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                byte[] bArr = new byte[1024];
                while (true) {
                    try {
                        int read = MidiOutputPort.this.mInputStream.read(bArr);
                        if (read < 0) {
                            return;
                        }
                        int packetType = MidiPortImpl.getPacketType(bArr, read);
                        if (packetType == 1) {
                            MidiOutputPort.this.mDispatcher.send(bArr, MidiPortImpl.getDataOffset(bArr, read), MidiPortImpl.getDataSize(bArr, read), MidiPortImpl.getPacketTimestamp(bArr, read));
                        } else if (packetType == 2) {
                            MidiOutputPort.this.mDispatcher.flush();
                        } else {
                            Log.e(MidiOutputPort.TAG, "Unknown packet type " + packetType);
                        }
                        MidiOutputPort.this.mTotalBytes.addAndGet(read);
                    } catch (IOException unused) {
                        return;
                    } finally {
                        IoUtils.closeQuietly(MidiOutputPort.this.mInputStream);
                    }
                }
            }
        };
        this.mThread = thread;
        this.mDeviceServer = iMidiDeviceServer;
        this.mToken = iBinder;
        this.mPortNumber = i;
        this.mInputStream = new ParcelFileDescriptor.AutoCloseInputStream(new ParcelFileDescriptor(fileDescriptor));
        thread.start();
        closeGuard.open("close");
    }

    MidiOutputPort(FileDescriptor fileDescriptor, int i) {
        this(null, null, fileDescriptor, i);
    }

    public final int getPortNumber() {
        return this.mPortNumber;
    }

    @Override // android.media.midi.MidiSender
    public void onConnect(MidiReceiver midiReceiver) {
        this.mDispatcher.getSender().connect(midiReceiver);
    }

    @Override // android.media.midi.MidiSender
    public void onDisconnect(MidiReceiver midiReceiver) {
        this.mDispatcher.getSender().disconnect(midiReceiver);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.mGuard) {
            if (this.mIsClosed) {
                return;
            }
            this.mGuard.close();
            this.mInputStream.close();
            IMidiDeviceServer iMidiDeviceServer = this.mDeviceServer;
            if (iMidiDeviceServer != null) {
                try {
                    iMidiDeviceServer.closePort(this.mToken);
                } catch (RemoteException unused) {
                    Log.e(TAG, "RemoteException in MidiOutputPort.close()");
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
