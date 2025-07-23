package android.hardware.radio.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioImsIndication extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$ims$IRadioImsIndication".replace('$', '.');
    public static final String HASH = "b2a615a151c7114c4216b1987fd32d40c797d00a";
    public static final int VERSION = 3;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void notifyAnbr(int i, int i2, int i3, int i4) throws RemoteException;

    void onConnectionSetupFailure(int i, int i2, ConnectionFailureInfo connectionFailureInfo) throws RemoteException;

    void triggerImsDeregistration(int i, int i2) throws RemoteException;

    public static class Default implements IRadioImsIndication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.ims.IRadioImsIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.ims.IRadioImsIndication
        public void notifyAnbr(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioImsIndication
        public void onConnectionSetupFailure(int i, int i2, ConnectionFailureInfo connectionFailureInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioImsIndication
        public void triggerImsDeregistration(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioImsIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioImsIndication {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_notifyAnbr = 2;
        static final int TRANSACTION_onConnectionSetupFailure = 1;
        static final int TRANSACTION_triggerImsDeregistration = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioImsIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioImsIndication)) {
                return (IRadioImsIndication) queryLocalInterface;
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
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                ConnectionFailureInfo connectionFailureInfo = (ConnectionFailureInfo) parcel.readTypedObject(ConnectionFailureInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onConnectionSetupFailure(readInt, readInt2, connectionFailureInfo);
            } else if (i == 2) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                notifyAnbr(readInt3, readInt4, readInt5, readInt6);
            } else if (i == 3) {
                int readInt7 = parcel.readInt();
                int readInt8 = parcel.readInt();
                parcel.enforceNoDataAvail();
                triggerImsDeregistration(readInt7, readInt8);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRadioImsIndication {
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

            @Override // android.hardware.radio.ims.IRadioImsIndication
            public void onConnectionSetupFailure(int i, int i2, ConnectionFailureInfo connectionFailureInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(connectionFailureInfo, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onConnectionSetupFailure is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioImsIndication
            public void notifyAnbr(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method notifyAnbr is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioImsIndication
            public void triggerImsDeregistration(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method triggerImsDeregistration is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioImsIndication
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

            @Override // android.hardware.radio.ims.IRadioImsIndication
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
