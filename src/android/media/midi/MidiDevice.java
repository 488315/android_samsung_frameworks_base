package android.media.midi;

import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public final class MidiDevice implements Closeable {
    private static final String TAG = "MidiDevice";
    private final IBinder mClientToken;
    private final MidiDeviceInfo mDeviceInfo;
    private final IMidiDeviceServer mDeviceServer;
    private final IBinder mDeviceServerBinder;
    private final IBinder mDeviceToken;
    private final CloseGuard mGuard;
    private boolean mIsDeviceClosed;
    private final IMidiManager mMidiManager;
    private long mNativeHandle;

    public class MidiConnection implements Closeable {
        private final CloseGuard mGuard;
        private final IMidiDeviceServer mInputPortDeviceServer;
        private final IBinder mInputPortToken;
        private boolean mIsClosed;
        private final IBinder mOutputPortToken;

        MidiConnection(IBinder iBinder, MidiInputPort midiInputPort) {
            CloseGuard closeGuard = CloseGuard.get();
            this.mGuard = closeGuard;
            this.mInputPortDeviceServer = midiInputPort.getDeviceServer();
            this.mInputPortToken = midiInputPort.getToken();
            this.mOutputPortToken = iBinder;
            closeGuard.open("close");
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            synchronized (this.mGuard) {
                if (this.mIsClosed) {
                    return;
                }
                this.mGuard.close();
                try {
                    this.mInputPortDeviceServer.closePort(this.mInputPortToken);
                    MidiDevice.this.mDeviceServer.closePort(this.mOutputPortToken);
                } catch (RemoteException unused) {
                    Log.e(MidiDevice.TAG, "RemoteException in MidiConnection.close");
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
                close();
            } finally {
                super.finalize();
            }
        }
    }

    MidiDevice(MidiDeviceInfo midiDeviceInfo, IMidiDeviceServer iMidiDeviceServer, IMidiManager iMidiManager, IBinder iBinder, IBinder iBinder2) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mGuard = closeGuard;
        this.mDeviceInfo = midiDeviceInfo;
        this.mDeviceServer = iMidiDeviceServer;
        this.mDeviceServerBinder = iMidiDeviceServer.asBinder();
        this.mMidiManager = iMidiManager;
        this.mClientToken = iBinder;
        this.mDeviceToken = iBinder2;
        closeGuard.open("close");
    }

    public MidiDeviceInfo getInfo() {
        return this.mDeviceInfo;
    }

    public MidiInputPort openInputPort(int i) {
        if (this.mIsDeviceClosed) {
            return null;
        }
        try {
            Binder binder = new Binder();
            FileDescriptor openInputPort = this.mDeviceServer.openInputPort(binder, i);
            if (openInputPort == null) {
                return null;
            }
            return new MidiInputPort(this.mDeviceServer, binder, openInputPort, i);
        } catch (RemoteException unused) {
            Log.e(TAG, "RemoteException in openInputPort");
            return null;
        }
    }

    public MidiOutputPort openOutputPort(int i) {
        if (this.mIsDeviceClosed) {
            return null;
        }
        try {
            Binder binder = new Binder();
            FileDescriptor openOutputPort = this.mDeviceServer.openOutputPort(binder, i);
            if (openOutputPort == null) {
                return null;
            }
            return new MidiOutputPort(this.mDeviceServer, binder, openOutputPort, i);
        } catch (RemoteException unused) {
            Log.e(TAG, "RemoteException in openOutputPort");
            return null;
        }
    }

    public MidiConnection connectPorts(MidiInputPort midiInputPort, int i) {
        FileDescriptor claimFileDescriptor;
        if (i < 0 || i >= this.mDeviceInfo.getOutputPortCount()) {
            throw new IllegalArgumentException("outputPortNumber out of range");
        }
        if (this.mIsDeviceClosed || (claimFileDescriptor = midiInputPort.claimFileDescriptor()) == null) {
            return null;
        }
        try {
            Binder binder = new Binder();
            if (this.mDeviceServer.connectPorts(binder, claimFileDescriptor, i) != Process.myPid()) {
                IoUtils.closeQuietly(claimFileDescriptor);
            }
            return new MidiConnection(binder, midiInputPort);
        } catch (RemoteException unused) {
            Log.e(TAG, "RemoteException in connectPorts");
            return null;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.mGuard) {
            if (this.mNativeHandle != 0) {
                Log.w(TAG, "MidiDevice#close() called while there is an outstanding native client 0x" + Long.toHexString(this.mNativeHandle));
            }
            if (!this.mIsDeviceClosed && this.mNativeHandle == 0) {
                this.mGuard.close();
                this.mIsDeviceClosed = true;
                try {
                    this.mMidiManager.closeDevice(this.mClientToken, this.mDeviceToken);
                } catch (RemoteException unused) {
                    Log.e(TAG, "RemoteException in closeDevice");
                }
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }

    public String toString() {
        return "MidiDevice: " + this.mDeviceInfo.toString();
    }
}
