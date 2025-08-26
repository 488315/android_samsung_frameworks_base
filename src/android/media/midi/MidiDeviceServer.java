package android.media.midi;

import android.media.midi.IMidiDeviceServer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import com.android.internal.midi.MidiDispatcher;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public final class MidiDeviceServer implements Closeable {
    private static final String TAG = "MidiDeviceServer";
    private static final int UNUSED_UID = -1;
    private final Callback mCallback;
    private MidiDeviceInfo mDeviceInfo;
    private final CloseGuard mGuard;
    private final HashMap<MidiInputPort, PortClient> mInputPortClients;
    private final int mInputPortCount;
    private final MidiDispatcher.MidiReceiverFailureHandler mInputPortFailureHandler;
    private final boolean[] mInputPortOpen;
    private final MidiOutputPort[] mInputPortOutputPorts;
    private final MidiReceiver[] mInputPortReceivers;
    private final CopyOnWriteArrayList<MidiInputPort> mInputPorts;
    private boolean mIsClosed;
    private final IMidiManager mMidiManager;
    private final int mOutputPortCount;
    private MidiDispatcher[] mOutputPortDispatchers;
    private final int[] mOutputPortOpenCount;
    private final HashMap<IBinder, PortClient> mPortClients;
    private final IMidiDeviceServer mServer;
    private AtomicInteger mTotalInputBytes;
    private AtomicInteger mTotalOutputBytes;
    private final int[] mUmpInputPortUids;
    private final int[] mUmpOutputPortUids;
    private final Object mUmpUidLock;

    public interface Callback {
        void onClose();

        void onDeviceStatusChanged(MidiDeviceServer midiDeviceServer, MidiDeviceStatus midiDeviceStatus);
    }

    private abstract class PortClient implements IBinder.DeathRecipient {
        final IBinder mToken;

        abstract void close();

        MidiInputPort getInputPort() {
            return null;
        }

        PortClient(MidiDeviceServer midiDeviceServer, IBinder iBinder) {
            this.mToken = iBinder;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (RemoteException unused) {
                close();
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            close();
        }
    }

    private class InputPortClient extends PortClient {
        private final MidiOutputPort mOutputPort;

        InputPortClient(IBinder iBinder, MidiOutputPort midiOutputPort) {
            super(MidiDeviceServer.this, iBinder);
            this.mOutputPort = midiOutputPort;
        }

        @Override // android.media.midi.MidiDeviceServer.PortClient
        void close() {
            this.mToken.unlinkToDeath(this, 0);
            synchronized (MidiDeviceServer.this.mInputPortOutputPorts) {
                int portNumber = this.mOutputPort.getPortNumber();
                MidiDeviceServer.this.mInputPortOutputPorts[portNumber] = null;
                MidiDeviceServer.this.mInputPortOpen[portNumber] = false;
                synchronized (MidiDeviceServer.this.mUmpUidLock) {
                    MidiDeviceServer.this.mUmpInputPortUids[portNumber] = -1;
                }
                MidiDeviceServer.this.mTotalOutputBytes.addAndGet(this.mOutputPort.pullTotalBytesCount());
                MidiDeviceServer.this.updateTotalBytes();
                MidiDeviceServer.this.updateDeviceStatus();
            }
            IoUtils.closeQuietly(this.mOutputPort);
        }
    }

    private class OutputPortClient extends PortClient {
        private final MidiInputPort mInputPort;

        OutputPortClient(IBinder iBinder, MidiInputPort midiInputPort) {
            super(MidiDeviceServer.this, iBinder);
            this.mInputPort = midiInputPort;
        }

        @Override // android.media.midi.MidiDeviceServer.PortClient
        void close() {
            this.mToken.unlinkToDeath(this, 0);
            int portNumber = this.mInputPort.getPortNumber();
            MidiDispatcher midiDispatcher = MidiDeviceServer.this.mOutputPortDispatchers[portNumber];
            synchronized (midiDispatcher) {
                midiDispatcher.getSender().disconnect(this.mInputPort);
                MidiDeviceServer.this.mOutputPortOpenCount[portNumber] = midiDispatcher.getReceiverCount();
                synchronized (MidiDeviceServer.this.mUmpUidLock) {
                    MidiDeviceServer.this.mUmpOutputPortUids[portNumber] = -1;
                }
                MidiDeviceServer.this.mTotalInputBytes.addAndGet(this.mInputPort.pullTotalBytesCount());
                MidiDeviceServer.this.updateTotalBytes();
                MidiDeviceServer.this.updateDeviceStatus();
            }
            MidiDeviceServer.this.mInputPorts.remove(this.mInputPort);
            IoUtils.closeQuietly(this.mInputPort);
        }

        @Override // android.media.midi.MidiDeviceServer.PortClient
        MidiInputPort getInputPort() {
            return this.mInputPort;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FileDescriptor[] createSeqPacketSocketPair() throws IOException, ErrnoException {
        try {
            FileDescriptor fileDescriptor = new FileDescriptor();
            FileDescriptor fileDescriptor2 = new FileDescriptor();
            Os.socketpair(OsConstants.AF_UNIX, OsConstants.SOCK_SEQPACKET, 0, fileDescriptor, fileDescriptor2);
            return new FileDescriptor[]{fileDescriptor, fileDescriptor2};
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    MidiDeviceServer(IMidiManager iMidiManager, MidiReceiver[] midiReceiverArr, int i, Callback callback) {
        this.mInputPorts = new CopyOnWriteArrayList<>();
        this.mGuard = CloseGuard.get();
        this.mPortClients = new HashMap<>();
        this.mInputPortClients = new HashMap<>();
        this.mTotalInputBytes = new AtomicInteger();
        this.mTotalOutputBytes = new AtomicInteger();
        this.mUmpUidLock = new Object();
        this.mServer = new IMidiDeviceServer.Stub() { // from class: android.media.midi.MidiDeviceServer.1
            /* JADX WARN: Removed duplicated region for block: B:71:0x0112 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // android.media.midi.IMidiDeviceServer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public FileDescriptor openInputPort(IBinder iBinder, int i2) {
                if (MidiDeviceServer.this.mDeviceInfo.isPrivate() && Binder.getCallingUid() != Process.myUid()) {
                    throw new SecurityException("Can't access private device from different UID");
                }
                if (i2 < 0 || i2 >= MidiDeviceServer.this.mInputPortCount) {
                    Log.e(MidiDeviceServer.TAG, "portNumber out of range in openInputPort: " + i2);
                    return null;
                }
                synchronized (MidiDeviceServer.this.mInputPortOutputPorts) {
                    if (MidiDeviceServer.this.mInputPortOutputPorts[i2] != null) {
                        Log.d(MidiDeviceServer.TAG, "port " + i2 + " already open");
                        return null;
                    }
                    if (MidiDeviceServer.this.isUmpDevice()) {
                        if (i2 >= MidiDeviceServer.this.mOutputPortCount) {
                            Log.e(MidiDeviceServer.TAG, "out portNumber out of range in openInputPort: " + i2);
                            return null;
                        }
                        synchronized (MidiDeviceServer.this.mUmpUidLock) {
                            if (MidiDeviceServer.this.mUmpInputPortUids[i2] != -1) {
                                Log.e(MidiDeviceServer.TAG, "input port already open in openInputPort: " + i2);
                                return null;
                            }
                            if (MidiDeviceServer.this.mUmpOutputPortUids[i2] != -1 && Binder.getCallingUid() != MidiDeviceServer.this.mUmpOutputPortUids[i2]) {
                                Log.e(MidiDeviceServer.TAG, "different uid for output in openInputPort: " + i2);
                                return null;
                            }
                            MidiDeviceServer.this.mUmpInputPortUids[i2] = Binder.getCallingUid();
                            FileDescriptor[] fileDescriptorArrCreateSeqPacketSocketPair = MidiDeviceServer.createSeqPacketSocketPair();
                            MidiOutputPort midiOutputPort = new MidiOutputPort(fileDescriptorArrCreateSeqPacketSocketPair[0], i2);
                            MidiDeviceServer.this.mInputPortOutputPorts[i2] = midiOutputPort;
                            midiOutputPort.connect(MidiDeviceServer.this.mInputPortReceivers[i2]);
                            InputPortClient inputPortClient = MidiDeviceServer.this.new InputPortClient(iBinder, midiOutputPort);
                            synchronized (MidiDeviceServer.this.mPortClients) {
                            }
                        }
                    } else {
                        try {
                            FileDescriptor[] fileDescriptorArrCreateSeqPacketSocketPair2 = MidiDeviceServer.createSeqPacketSocketPair();
                            MidiOutputPort midiOutputPort2 = new MidiOutputPort(fileDescriptorArrCreateSeqPacketSocketPair2[0], i2);
                            MidiDeviceServer.this.mInputPortOutputPorts[i2] = midiOutputPort2;
                            midiOutputPort2.connect(MidiDeviceServer.this.mInputPortReceivers[i2]);
                            InputPortClient inputPortClient2 = MidiDeviceServer.this.new InputPortClient(iBinder, midiOutputPort2);
                            synchronized (MidiDeviceServer.this.mPortClients) {
                                MidiDeviceServer.this.mPortClients.put(iBinder, inputPortClient2);
                            }
                            MidiDeviceServer.this.mInputPortOpen[i2] = true;
                            MidiDeviceServer.this.updateDeviceStatus();
                            return fileDescriptorArrCreateSeqPacketSocketPair2[1];
                        } catch (IOException unused) {
                            Log.e(MidiDeviceServer.TAG, "unable to create FileDescriptors in openInputPort");
                            return null;
                        }
                    }
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public FileDescriptor openOutputPort(IBinder iBinder, int i2) throws ErrnoException {
                if (MidiDeviceServer.this.mDeviceInfo.isPrivate() && Binder.getCallingUid() != Process.myUid()) {
                    throw new SecurityException("Can't access private device from different UID");
                }
                if (i2 < 0 || i2 >= MidiDeviceServer.this.mOutputPortCount) {
                    Log.e(MidiDeviceServer.TAG, "portNumber out of range in openOutputPort: " + i2);
                    return null;
                }
                if (MidiDeviceServer.this.isUmpDevice()) {
                    if (i2 >= MidiDeviceServer.this.mInputPortCount) {
                        Log.e(MidiDeviceServer.TAG, "in portNumber out of range in openOutputPort: " + i2);
                        return null;
                    }
                    synchronized (MidiDeviceServer.this.mUmpUidLock) {
                        if (MidiDeviceServer.this.mUmpOutputPortUids[i2] != -1) {
                            Log.e(MidiDeviceServer.TAG, "output port already open in openOutputPort: " + i2);
                            return null;
                        }
                        if (MidiDeviceServer.this.mUmpInputPortUids[i2] != -1 && Binder.getCallingUid() != MidiDeviceServer.this.mUmpInputPortUids[i2]) {
                            Log.e(MidiDeviceServer.TAG, "different uid for input in openOutputPort: " + i2);
                            return null;
                        }
                        MidiDeviceServer.this.mUmpOutputPortUids[i2] = Binder.getCallingUid();
                    }
                }
                try {
                    FileDescriptor[] fileDescriptorArrCreateSeqPacketSocketPair = MidiDeviceServer.createSeqPacketSocketPair();
                    MidiInputPort midiInputPort = new MidiInputPort(fileDescriptorArrCreateSeqPacketSocketPair[0], i2);
                    if (MidiDeviceServer.this.mDeviceInfo.getType() != 2) {
                        IoUtils.setBlocking(fileDescriptorArrCreateSeqPacketSocketPair[0], false);
                    }
                    MidiDispatcher midiDispatcher = MidiDeviceServer.this.mOutputPortDispatchers[i2];
                    synchronized (midiDispatcher) {
                        midiDispatcher.getSender().connect(midiInputPort);
                        MidiDeviceServer.this.mOutputPortOpenCount[i2] = midiDispatcher.getReceiverCount();
                        MidiDeviceServer.this.updateDeviceStatus();
                    }
                    MidiDeviceServer.this.mInputPorts.add(midiInputPort);
                    OutputPortClient outputPortClient = MidiDeviceServer.this.new OutputPortClient(iBinder, midiInputPort);
                    synchronized (MidiDeviceServer.this.mPortClients) {
                        MidiDeviceServer.this.mPortClients.put(iBinder, outputPortClient);
                    }
                    synchronized (MidiDeviceServer.this.mInputPortClients) {
                        MidiDeviceServer.this.mInputPortClients.put(midiInputPort, outputPortClient);
                    }
                    return fileDescriptorArrCreateSeqPacketSocketPair[1];
                } catch (IOException unused) {
                    Log.e(MidiDeviceServer.TAG, "unable to create FileDescriptors in openOutputPort");
                    return null;
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void closePort(IBinder iBinder) {
                MidiInputPort inputPort;
                synchronized (MidiDeviceServer.this.mPortClients) {
                    PortClient portClient = (PortClient) MidiDeviceServer.this.mPortClients.remove(iBinder);
                    if (portClient != null) {
                        inputPort = portClient.getInputPort();
                        portClient.close();
                    } else {
                        inputPort = null;
                    }
                }
                if (inputPort != null) {
                    synchronized (MidiDeviceServer.this.mInputPortClients) {
                        MidiDeviceServer.this.mInputPortClients.remove(inputPort);
                    }
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void closeDevice() {
                if (MidiDeviceServer.this.mCallback != null) {
                    MidiDeviceServer.this.mCallback.onClose();
                }
                IoUtils.closeQuietly(MidiDeviceServer.this);
            }

            @Override // android.media.midi.IMidiDeviceServer
            public int connectPorts(IBinder iBinder, FileDescriptor fileDescriptor, int i2) {
                MidiInputPort midiInputPort = new MidiInputPort(fileDescriptor, i2);
                MidiDispatcher midiDispatcher = MidiDeviceServer.this.mOutputPortDispatchers[i2];
                synchronized (midiDispatcher) {
                    midiDispatcher.getSender().connect(midiInputPort);
                    MidiDeviceServer.this.mOutputPortOpenCount[i2] = midiDispatcher.getReceiverCount();
                    MidiDeviceServer.this.updateDeviceStatus();
                }
                MidiDeviceServer.this.mInputPorts.add(midiInputPort);
                OutputPortClient outputPortClient = MidiDeviceServer.this.new OutputPortClient(iBinder, midiInputPort);
                synchronized (MidiDeviceServer.this.mPortClients) {
                    MidiDeviceServer.this.mPortClients.put(iBinder, outputPortClient);
                }
                synchronized (MidiDeviceServer.this.mInputPortClients) {
                    MidiDeviceServer.this.mInputPortClients.put(midiInputPort, outputPortClient);
                }
                return Process.myPid();
            }

            @Override // android.media.midi.IMidiDeviceServer
            public MidiDeviceInfo getDeviceInfo() {
                return MidiDeviceServer.this.mDeviceInfo;
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void setDeviceInfo(MidiDeviceInfo midiDeviceInfo) {
                if (Binder.getCallingUid() != 1000) {
                    throw new SecurityException("setDeviceInfo should only be called by MidiService");
                }
                if (MidiDeviceServer.this.mDeviceInfo != null) {
                    throw new IllegalStateException("setDeviceInfo should only be called once");
                }
                MidiDeviceServer.this.mDeviceInfo = midiDeviceInfo;
            }
        };
        this.mInputPortFailureHandler = new MidiDispatcher.MidiReceiverFailureHandler() { // from class: android.media.midi.MidiDeviceServer.2
            @Override // com.android.internal.midi.MidiDispatcher.MidiReceiverFailureHandler
            public void onReceiverFailure(MidiReceiver midiReceiver, IOException iOException) {
                PortClient portClient;
                Log.e(MidiDeviceServer.TAG, "MidiInputPort failed to send data", iOException);
                synchronized (MidiDeviceServer.this.mInputPortClients) {
                    portClient = (PortClient) MidiDeviceServer.this.mInputPortClients.remove(midiReceiver);
                }
                if (portClient != null) {
                    portClient.close();
                }
            }
        };
        this.mMidiManager = iMidiManager;
        this.mInputPortReceivers = midiReceiverArr;
        int length = midiReceiverArr.length;
        this.mInputPortCount = length;
        this.mOutputPortCount = i;
        this.mCallback = callback;
        this.mInputPortOutputPorts = new MidiOutputPort[length];
        this.mOutputPortDispatchers = new MidiDispatcher[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mOutputPortDispatchers[i2] = new MidiDispatcher(this.mInputPortFailureHandler);
        }
        this.mInputPortOpen = new boolean[this.mInputPortCount];
        this.mOutputPortOpenCount = new int[i];
        synchronized (this.mUmpUidLock) {
            int[] iArr = new int[this.mInputPortCount];
            this.mUmpInputPortUids = iArr;
            int[] iArr2 = new int[this.mOutputPortCount];
            this.mUmpOutputPortUids = iArr2;
            Arrays.fill(iArr, -1);
            Arrays.fill(iArr2, -1);
        }
        this.mGuard.open("close");
    }

    MidiDeviceServer(IMidiManager iMidiManager, MidiReceiver[] midiReceiverArr, MidiDeviceInfo midiDeviceInfo, Callback callback) {
        this(iMidiManager, midiReceiverArr, midiDeviceInfo.getOutputPortCount(), callback);
        this.mDeviceInfo = midiDeviceInfo;
    }

    IMidiDeviceServer getBinderInterface() {
        return this.mServer;
    }

    public IBinder asBinder() {
        return this.mServer.asBinder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceStatus() {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            MidiDeviceStatus midiDeviceStatus = new MidiDeviceStatus(this.mDeviceInfo, this.mInputPortOpen, this.mOutputPortOpenCount);
            Callback callback = this.mCallback;
            if (callback != null) {
                callback.onDeviceStatusChanged(this, midiDeviceStatus);
            }
            this.mMidiManager.setDeviceStatus(this.mServer, midiDeviceStatus);
        } catch (RemoteException unused) {
            Log.e(TAG, "RemoteException in updateDeviceStatus");
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.mGuard) {
            if (this.mIsClosed) {
                return;
            }
            this.mGuard.close();
            for (int i = 0; i < this.mInputPortCount; i++) {
                MidiOutputPort midiOutputPort = this.mInputPortOutputPorts[i];
                if (midiOutputPort != null) {
                    this.mTotalOutputBytes.addAndGet(midiOutputPort.pullTotalBytesCount());
                    IoUtils.closeQuietly(midiOutputPort);
                    this.mInputPortOutputPorts[i] = null;
                }
            }
            Iterator<MidiInputPort> it = this.mInputPorts.iterator();
            while (it.hasNext()) {
                MidiInputPort next = it.next();
                this.mTotalInputBytes.addAndGet(next.pullTotalBytesCount());
                IoUtils.closeQuietly(next);
            }
            this.mInputPorts.clear();
            updateTotalBytes();
            try {
                this.mMidiManager.unregisterDeviceServer(this.mServer);
            } catch (RemoteException unused) {
                Log.e(TAG, "RemoteException in unregisterDeviceServer");
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

    public MidiReceiver[] getOutputPortReceivers() {
        int i = this.mOutputPortCount;
        MidiReceiver[] midiReceiverArr = new MidiReceiver[i];
        System.arraycopy(this.mOutputPortDispatchers, 0, midiReceiverArr, 0, i);
        return midiReceiverArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTotalBytes() {
        try {
            this.mMidiManager.updateTotalBytes(this.mServer, this.mTotalInputBytes.get(), this.mTotalOutputBytes.get());
        } catch (RemoteException unused) {
            Log.e(TAG, "RemoteException in updateTotalBytes");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUmpDevice() {
        return this.mDeviceInfo.getDefaultProtocol() != -1;
    }
}
