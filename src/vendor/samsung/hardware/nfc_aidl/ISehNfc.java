package vendor.samsung.hardware.nfc_aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehNfc extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$nfc_aidl$ISehNfc".replace('$', '.');
    public static final String HASH = "b8db442910eb3eee373b5d622b6d2dcdb4508506";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    String sehGetProperty(String str) throws RemoteException;

    boolean sehIsDriverPinStatusPrintable() throws RemoteException;

    boolean sehIsNptModeSupported() throws RemoteException;

    String sehLoadPersistLog(byte b) throws RemoteException;

    boolean sehPrintDriverPinStatus() throws RemoteException;

    boolean sehResetEse() throws RemoteException;

    byte sehSavePersistLog(byte b) throws RemoteException;

    boolean sehSetNptModeEnabled(boolean z) throws RemoteException;

    boolean sehSetProperty(String str, String str2) throws RemoteException;

    public static class Default implements ISehNfc {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
        public String sehGetProperty(String str) throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
        public boolean sehIsDriverPinStatusPrintable() throws RemoteException {
            return false;
        }

        @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
        public boolean sehIsNptModeSupported() throws RemoteException {
            return false;
        }

        @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
        public String sehLoadPersistLog(byte b) throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
        public boolean sehPrintDriverPinStatus() throws RemoteException {
            return false;
        }

        @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
        public boolean sehResetEse() throws RemoteException {
            return false;
        }

        @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
        public byte sehSavePersistLog(byte b) throws RemoteException {
            return (byte) 0;
        }

        @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
        public boolean sehSetNptModeEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
        public boolean sehSetProperty(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehNfc {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_sehGetProperty = 1;
        static final int TRANSACTION_sehIsDriverPinStatusPrintable = 2;
        static final int TRANSACTION_sehIsNptModeSupported = 3;
        static final int TRANSACTION_sehLoadPersistLog = 4;
        static final int TRANSACTION_sehPrintDriverPinStatus = 5;
        static final int TRANSACTION_sehResetEse = 6;
        static final int TRANSACTION_sehSavePersistLog = 7;
        static final int TRANSACTION_sehSetNptModeEnabled = 8;
        static final int TRANSACTION_sehSetProperty = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehNfc asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehNfc)) {
                return (ISehNfc) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String sehGetProperty = sehGetProperty(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(sehGetProperty);
                    return true;
                case 2:
                    boolean sehIsDriverPinStatusPrintable = sehIsDriverPinStatusPrintable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sehIsDriverPinStatusPrintable);
                    return true;
                case 3:
                    boolean sehIsNptModeSupported = sehIsNptModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sehIsNptModeSupported);
                    return true;
                case 4:
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    String sehLoadPersistLog = sehLoadPersistLog(readByte);
                    parcel2.writeNoException();
                    parcel2.writeString(sehLoadPersistLog);
                    return true;
                case 5:
                    boolean sehPrintDriverPinStatus = sehPrintDriverPinStatus();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sehPrintDriverPinStatus);
                    return true;
                case 6:
                    boolean sehResetEse = sehResetEse();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sehResetEse);
                    return true;
                case 7:
                    byte readByte2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    byte sehSavePersistLog = sehSavePersistLog(readByte2);
                    parcel2.writeNoException();
                    parcel2.writeByte(sehSavePersistLog);
                    return true;
                case 8:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean sehSetNptModeEnabled = sehSetNptModeEnabled(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sehSetNptModeEnabled);
                    return true;
                case 9:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean sehSetProperty = sehSetProperty(readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sehSetProperty);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehNfc {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
            public String sehGetProperty(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sehGetProperty is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
            public boolean sehIsDriverPinStatusPrintable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sehIsDriverPinStatusPrintable is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
            public boolean sehIsNptModeSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sehIsNptModeSupported is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
            public String sehLoadPersistLog(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sehLoadPersistLog is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
            public boolean sehPrintDriverPinStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sehPrintDriverPinStatus is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
            public boolean sehResetEse() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sehResetEse is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
            public byte sehSavePersistLog(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sehSavePersistLog is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
            public boolean sehSetNptModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sehSetNptModeEnabled is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
            public boolean sehSetProperty(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sehSetProperty is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // vendor.samsung.hardware.nfc_aidl.ISehNfc
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
