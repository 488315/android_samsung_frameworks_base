package android.gsi;

import android.gsi.IGsiServiceCallback;
import android.gsi.IImageService;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IGsiService extends IInterface {
    public static final String DESCRIPTOR = "android.gsi.IGsiService";
    public static final int INSTALL_ERROR_FILE_SYSTEM_CLUTTERED = 3;
    public static final int INSTALL_ERROR_GENERIC = 1;
    public static final int INSTALL_ERROR_NO_SPACE = 2;
    public static final int INSTALL_OK = 0;
    public static final int STATUS_COMPLETE = 2;
    public static final int STATUS_NO_OPERATION = 0;
    public static final int STATUS_WORKING = 1;

    boolean cancelGsiInstall() throws RemoteException;

    int closeInstall() throws RemoteException;

    int closePartition() throws RemoteException;

    boolean commitGsiChunkFromAshmem(long j) throws RemoteException;

    boolean commitGsiChunkFromStream(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException;

    int createPartition(String str, long j, boolean z) throws RemoteException;

    boolean disableGsi() throws RemoteException;

    String dumpDeviceMapperDevices() throws RemoteException;

    int enableGsi(boolean z, String str) throws RemoteException;

    void enableGsiAsync(boolean z, String str, IGsiServiceCallback iGsiServiceCallback) throws RemoteException;

    String getActiveDsuSlot() throws RemoteException;

    int getAvbPublicKey(AvbPublicKey avbPublicKey) throws RemoteException;

    GsiProgress getInstallProgress() throws RemoteException;

    List<String> getInstalledDsuSlots() throws RemoteException;

    String getInstalledGsiImageDir() throws RemoteException;

    boolean isGsiEnabled() throws RemoteException;

    boolean isGsiInstallInProgress() throws RemoteException;

    boolean isGsiInstalled() throws RemoteException;

    boolean isGsiRunning() throws RemoteException;

    IImageService openImageService(String str) throws RemoteException;

    int openInstall(String str) throws RemoteException;

    boolean removeGsi() throws RemoteException;

    void removeGsiAsync(IGsiServiceCallback iGsiServiceCallback) throws RemoteException;

    boolean setGsiAshmem(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException;

    long suggestScratchSize() throws RemoteException;

    int zeroPartition(String str) throws RemoteException;

    public static class Default implements IGsiService {
        @Override // android.gsi.IGsiService
        public boolean commitGsiChunkFromStream(ParcelFileDescriptor stream, long bytes) throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public GsiProgress getInstallProgress() throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public boolean setGsiAshmem(ParcelFileDescriptor stream, long size) throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean commitGsiChunkFromAshmem(long bytes) throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public int enableGsi(boolean oneShot, String dsuSlot) throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public void enableGsiAsync(boolean oneShot, String dsuSlot, IGsiServiceCallback result) throws RemoteException {
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiEnabled() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean cancelGsiInstall() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiInstallInProgress() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean removeGsi() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public void removeGsiAsync(IGsiServiceCallback result) throws RemoteException {
        }

        @Override // android.gsi.IGsiService
        public boolean disableGsi() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiInstalled() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiRunning() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public String getActiveDsuSlot() throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public String getInstalledGsiImageDir() throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public List<String> getInstalledDsuSlots() throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public int openInstall(String installDir) throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public int closeInstall() throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public int createPartition(String name, long size, boolean readOnly) throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public int closePartition() throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public int zeroPartition(String name) throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public IImageService openImageService(String prefix) throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public String dumpDeviceMapperDevices() throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public int getAvbPublicKey(AvbPublicKey dst) throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public long suggestScratchSize() throws RemoteException {
            return 0L;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGsiService {
        static final int TRANSACTION_cancelGsiInstall = 8;
        static final int TRANSACTION_closeInstall = 19;
        static final int TRANSACTION_closePartition = 21;
        static final int TRANSACTION_commitGsiChunkFromAshmem = 4;
        static final int TRANSACTION_commitGsiChunkFromStream = 1;
        static final int TRANSACTION_createPartition = 20;
        static final int TRANSACTION_disableGsi = 12;
        static final int TRANSACTION_dumpDeviceMapperDevices = 24;
        static final int TRANSACTION_enableGsi = 5;
        static final int TRANSACTION_enableGsiAsync = 6;
        static final int TRANSACTION_getActiveDsuSlot = 15;
        static final int TRANSACTION_getAvbPublicKey = 25;
        static final int TRANSACTION_getInstallProgress = 2;
        static final int TRANSACTION_getInstalledDsuSlots = 17;
        static final int TRANSACTION_getInstalledGsiImageDir = 16;
        static final int TRANSACTION_isGsiEnabled = 7;
        static final int TRANSACTION_isGsiInstallInProgress = 9;
        static final int TRANSACTION_isGsiInstalled = 13;
        static final int TRANSACTION_isGsiRunning = 14;
        static final int TRANSACTION_openImageService = 23;
        static final int TRANSACTION_openInstall = 18;
        static final int TRANSACTION_removeGsi = 10;
        static final int TRANSACTION_removeGsiAsync = 11;
        static final int TRANSACTION_setGsiAshmem = 3;
        static final int TRANSACTION_suggestScratchSize = 26;
        static final int TRANSACTION_zeroPartition = 22;

        public Stub() {
            attachInterface(this, IGsiService.DESCRIPTOR);
        }

        public static IGsiService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGsiService.DESCRIPTOR);
            if (iin != null && (iin instanceof IGsiService)) {
                return (IGsiService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "commitGsiChunkFromStream";
                case 2:
                    return "getInstallProgress";
                case 3:
                    return "setGsiAshmem";
                case 4:
                    return "commitGsiChunkFromAshmem";
                case 5:
                    return "enableGsi";
                case 6:
                    return "enableGsiAsync";
                case 7:
                    return "isGsiEnabled";
                case 8:
                    return "cancelGsiInstall";
                case 9:
                    return "isGsiInstallInProgress";
                case 10:
                    return "removeGsi";
                case 11:
                    return "removeGsiAsync";
                case 12:
                    return "disableGsi";
                case 13:
                    return "isGsiInstalled";
                case 14:
                    return "isGsiRunning";
                case 15:
                    return "getActiveDsuSlot";
                case 16:
                    return "getInstalledGsiImageDir";
                case 17:
                    return "getInstalledDsuSlots";
                case 18:
                    return "openInstall";
                case 19:
                    return "closeInstall";
                case 20:
                    return "createPartition";
                case 21:
                    return "closePartition";
                case 22:
                    return "zeroPartition";
                case 23:
                    return "openImageService";
                case 24:
                    return "dumpDeviceMapperDevices";
                case 25:
                    return "getAvbPublicKey";
                case 26:
                    return "suggestScratchSize";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IGsiService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IGsiService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            ParcelFileDescriptor _arg0 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                            long _arg1 = data.readLong();
                            data.enforceNoDataAvail();
                            boolean _result = commitGsiChunkFromStream(_arg0, _arg1);
                            reply.writeNoException();
                            reply.writeBoolean(_result);
                            return true;
                        case 2:
                            GsiProgress _result2 = getInstallProgress();
                            reply.writeNoException();
                            reply.writeTypedObject(_result2, 1);
                            return true;
                        case 3:
                            ParcelFileDescriptor _arg02 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                            long _arg12 = data.readLong();
                            data.enforceNoDataAvail();
                            boolean _result3 = setGsiAshmem(_arg02, _arg12);
                            reply.writeNoException();
                            reply.writeBoolean(_result3);
                            return true;
                        case 4:
                            long _arg03 = data.readLong();
                            data.enforceNoDataAvail();
                            boolean _result4 = commitGsiChunkFromAshmem(_arg03);
                            reply.writeNoException();
                            reply.writeBoolean(_result4);
                            return true;
                        case 5:
                            boolean _arg04 = data.readBoolean();
                            String _arg13 = data.readString();
                            data.enforceNoDataAvail();
                            int _result5 = enableGsi(_arg04, _arg13);
                            reply.writeNoException();
                            reply.writeInt(_result5);
                            return true;
                        case 6:
                            boolean _arg05 = data.readBoolean();
                            String _arg14 = data.readString();
                            IGsiServiceCallback _arg2 = IGsiServiceCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            enableGsiAsync(_arg05, _arg14, _arg2);
                            return true;
                        case 7:
                            boolean _result6 = isGsiEnabled();
                            reply.writeNoException();
                            reply.writeBoolean(_result6);
                            return true;
                        case 8:
                            boolean _result7 = cancelGsiInstall();
                            reply.writeNoException();
                            reply.writeBoolean(_result7);
                            return true;
                        case 9:
                            boolean _result8 = isGsiInstallInProgress();
                            reply.writeNoException();
                            reply.writeBoolean(_result8);
                            return true;
                        case 10:
                            boolean _result9 = removeGsi();
                            reply.writeNoException();
                            reply.writeBoolean(_result9);
                            return true;
                        case 11:
                            IGsiServiceCallback _arg06 = IGsiServiceCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            removeGsiAsync(_arg06);
                            return true;
                        case 12:
                            boolean _result10 = disableGsi();
                            reply.writeNoException();
                            reply.writeBoolean(_result10);
                            return true;
                        case 13:
                            boolean _result11 = isGsiInstalled();
                            reply.writeNoException();
                            reply.writeBoolean(_result11);
                            return true;
                        case 14:
                            boolean _result12 = isGsiRunning();
                            reply.writeNoException();
                            reply.writeBoolean(_result12);
                            return true;
                        case 15:
                            String _result13 = getActiveDsuSlot();
                            reply.writeNoException();
                            reply.writeString(_result13);
                            return true;
                        case 16:
                            String _result14 = getInstalledGsiImageDir();
                            reply.writeNoException();
                            reply.writeString(_result14);
                            return true;
                        case 17:
                            List<String> _result15 = getInstalledDsuSlots();
                            reply.writeNoException();
                            reply.writeStringList(_result15);
                            return true;
                        case 18:
                            String _arg07 = data.readString();
                            data.enforceNoDataAvail();
                            int _result16 = openInstall(_arg07);
                            reply.writeNoException();
                            reply.writeInt(_result16);
                            return true;
                        case 19:
                            int _result17 = closeInstall();
                            reply.writeNoException();
                            reply.writeInt(_result17);
                            return true;
                        case 20:
                            String _arg08 = data.readString();
                            long _arg15 = data.readLong();
                            boolean _arg22 = data.readBoolean();
                            data.enforceNoDataAvail();
                            int _result18 = createPartition(_arg08, _arg15, _arg22);
                            reply.writeNoException();
                            reply.writeInt(_result18);
                            return true;
                        case 21:
                            int _result19 = closePartition();
                            reply.writeNoException();
                            reply.writeInt(_result19);
                            return true;
                        case 22:
                            String _arg09 = data.readString();
                            data.enforceNoDataAvail();
                            int _result20 = zeroPartition(_arg09);
                            reply.writeNoException();
                            reply.writeInt(_result20);
                            return true;
                        case 23:
                            String _arg010 = data.readString();
                            data.enforceNoDataAvail();
                            IImageService _result21 = openImageService(_arg010);
                            reply.writeNoException();
                            reply.writeStrongInterface(_result21);
                            return true;
                        case 24:
                            String _result22 = dumpDeviceMapperDevices();
                            reply.writeNoException();
                            reply.writeString(_result22);
                            return true;
                        case 25:
                            AvbPublicKey _arg011 = new AvbPublicKey();
                            data.enforceNoDataAvail();
                            int _result23 = getAvbPublicKey(_arg011);
                            reply.writeNoException();
                            reply.writeInt(_result23);
                            reply.writeTypedObject(_arg011, 1);
                            return true;
                        case 26:
                            long _result24 = suggestScratchSize();
                            reply.writeNoException();
                            reply.writeLong(_result24);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IGsiService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGsiService.DESCRIPTOR;
            }

            @Override // android.gsi.IGsiService
            public boolean commitGsiChunkFromStream(ParcelFileDescriptor stream, long bytes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    _data.writeTypedObject(stream, 0);
                    _data.writeLong(bytes);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public GsiProgress getInstallProgress() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    GsiProgress _result = (GsiProgress) _reply.readTypedObject(GsiProgress.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean setGsiAshmem(ParcelFileDescriptor stream, long size) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    _data.writeTypedObject(stream, 0);
                    _data.writeLong(size);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean commitGsiChunkFromAshmem(long bytes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    _data.writeLong(bytes);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int enableGsi(boolean oneShot, String dsuSlot) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    _data.writeBoolean(oneShot);
                    _data.writeString(dsuSlot);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public void enableGsiAsync(boolean oneShot, String dsuSlot, IGsiServiceCallback result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    _data.writeBoolean(oneShot);
                    _data.writeString(dsuSlot);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean cancelGsiInstall() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiInstallInProgress() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean removeGsi() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public void removeGsiAsync(IGsiServiceCallback result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean disableGsi() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiInstalled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiRunning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public String getActiveDsuSlot() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public String getInstalledGsiImageDir() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public List<String> getInstalledDsuSlots() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int openInstall(String installDir) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    _data.writeString(installDir);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int closeInstall() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int createPartition(String name, long size, boolean readOnly) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeLong(size);
                    _data.writeBoolean(readOnly);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int closePartition() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int zeroPartition(String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public IImageService openImageService(String prefix) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    _data.writeString(prefix);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    IImageService _result = IImageService.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public String dumpDeviceMapperDevices() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int getAvbPublicKey(AvbPublicKey dst) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    if (_reply.readInt() != 0) {
                        dst.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public long suggestScratchSize() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 25;
        }
    }
}
