package android.hardware.usb.gadget;

import android.hardware.usb.gadget.IUsbGadgetCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IUsbGadget extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$usb$gadget$IUsbGadget".replace('$', '.');
    public static final String HASH = "cb628c69682659911bca5c1d04042adba7f0de4b";
    public static final int VERSION = 1;

    void getCurrentUsbFunctions(IUsbGadgetCallback iUsbGadgetCallback, long j) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getUsbSpeed(IUsbGadgetCallback iUsbGadgetCallback, long j) throws RemoteException;

    void reset(IUsbGadgetCallback iUsbGadgetCallback, long j) throws RemoteException;

    void setCurrentUsbFunctions(long j, IUsbGadgetCallback iUsbGadgetCallback, long j2, long j3) throws RemoteException;

    public static class Default implements IUsbGadget {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.usb.gadget.IUsbGadget
        public void getCurrentUsbFunctions(IUsbGadgetCallback iUsbGadgetCallback, long j) throws RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadget
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.usb.gadget.IUsbGadget
        public void getUsbSpeed(IUsbGadgetCallback iUsbGadgetCallback, long j) throws RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadget
        public void reset(IUsbGadgetCallback iUsbGadgetCallback, long j) throws RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadget
        public void setCurrentUsbFunctions(long j, IUsbGadgetCallback iUsbGadgetCallback, long j2, long j3) throws RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadget
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IUsbGadget {
        static final int TRANSACTION_getCurrentUsbFunctions = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getUsbSpeed = 3;
        static final int TRANSACTION_reset = 4;
        static final int TRANSACTION_setCurrentUsbFunctions = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IUsbGadget asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUsbGadget)) {
                return (IUsbGadget) queryLocalInterface;
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
                IUsbGadgetCallback asInterface = IUsbGadgetCallback.Stub.asInterface(parcel.readStrongBinder());
                long readLong2 = parcel.readLong();
                long readLong3 = parcel.readLong();
                parcel.enforceNoDataAvail();
                setCurrentUsbFunctions(readLong, asInterface, readLong2, readLong3);
            } else if (i == 2) {
                IUsbGadgetCallback asInterface2 = IUsbGadgetCallback.Stub.asInterface(parcel.readStrongBinder());
                long readLong4 = parcel.readLong();
                parcel.enforceNoDataAvail();
                getCurrentUsbFunctions(asInterface2, readLong4);
            } else if (i == 3) {
                IUsbGadgetCallback asInterface3 = IUsbGadgetCallback.Stub.asInterface(parcel.readStrongBinder());
                long readLong5 = parcel.readLong();
                parcel.enforceNoDataAvail();
                getUsbSpeed(asInterface3, readLong5);
            } else if (i == 4) {
                IUsbGadgetCallback asInterface4 = IUsbGadgetCallback.Stub.asInterface(parcel.readStrongBinder());
                long readLong6 = parcel.readLong();
                parcel.enforceNoDataAvail();
                reset(asInterface4, readLong6);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IUsbGadget {
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

            @Override // android.hardware.usb.gadget.IUsbGadget
            public void setCurrentUsbFunctions(long j, IUsbGadgetCallback iUsbGadgetCallback, long j2, long j3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iUsbGadgetCallback);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCurrentUsbFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadget
            public void getCurrentUsbFunctions(IUsbGadgetCallback iUsbGadgetCallback, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iUsbGadgetCallback);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCurrentUsbFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadget
            public void getUsbSpeed(IUsbGadgetCallback iUsbGadgetCallback, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iUsbGadgetCallback);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getUsbSpeed is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadget
            public void reset(IUsbGadgetCallback iUsbGadgetCallback, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iUsbGadgetCallback);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method reset is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadget
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

            @Override // android.hardware.usb.gadget.IUsbGadget
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
