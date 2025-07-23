package android.hardware.usb.gadget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IUsbGadgetCallback extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$usb$gadget$IUsbGadgetCallback".replace('$', '.');
    public static final String HASH = "cb628c69682659911bca5c1d04042adba7f0de4b";
    public static final int VERSION = 1;

    void getCurrentUsbFunctionsCb(long j, int i, long j2) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getUsbSpeedCb(int i, long j) throws RemoteException;

    void resetCb(int i, long j) throws RemoteException;

    void setCurrentUsbFunctionsCb(long j, int i, long j2) throws RemoteException;

    public static class Default implements IUsbGadgetCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public void getCurrentUsbFunctionsCb(long j, int i, long j2) throws RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public void getUsbSpeedCb(int i, long j) throws RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public void resetCb(int i, long j) throws RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public void setCurrentUsbFunctionsCb(long j, int i, long j2) throws RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IUsbGadgetCallback {
        static final int TRANSACTION_getCurrentUsbFunctionsCb = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getUsbSpeedCb = 3;
        static final int TRANSACTION_resetCb = 4;
        static final int TRANSACTION_setCurrentUsbFunctionsCb = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IUsbGadgetCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUsbGadgetCallback)) {
                return (IUsbGadgetCallback) queryLocalInterface;
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
            if (i == 1) {
                long readLong = parcel.readLong();
                int readInt = parcel.readInt();
                long readLong2 = parcel.readLong();
                parcel.enforceNoDataAvail();
                setCurrentUsbFunctionsCb(readLong, readInt, readLong2);
            } else if (i == 2) {
                long readLong3 = parcel.readLong();
                int readInt2 = parcel.readInt();
                long readLong4 = parcel.readLong();
                parcel.enforceNoDataAvail();
                getCurrentUsbFunctionsCb(readLong3, readInt2, readLong4);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                long readLong5 = parcel.readLong();
                parcel.enforceNoDataAvail();
                getUsbSpeedCb(readInt3, readLong5);
            } else if (i == 4) {
                int readInt4 = parcel.readInt();
                long readLong6 = parcel.readLong();
                parcel.enforceNoDataAvail();
                resetCb(readInt4, readLong6);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IUsbGadgetCallback {
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

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
            public void setCurrentUsbFunctionsCb(long j, int i, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeLong(j2);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCurrentUsbFunctionsCb is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
            public void getCurrentUsbFunctionsCb(long j, int i, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeLong(j2);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCurrentUsbFunctionsCb is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
            public void getUsbSpeedCb(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getUsbSpeedCb is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
            public void resetCb(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method resetCb is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
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

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
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
