package android.media.tv.extension.signal;

import android.media.tv.extension.signal.IHdmiSignalInfoListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IHdmiSignalInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.signal.IHdmiSignalInterface";

    public static class Default implements IHdmiSignalInterface {
        @Override // android.media.tv.extension.signal.IHdmiSignalInterface
        public void addHdmiSignalInfoListener(String str, IHdmiSignalInfoListener iHdmiSignalInfoListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.signal.IHdmiSignalInterface
        public Bundle getHdmiSignalInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.signal.IHdmiSignalInterface
        public void removeHdmiSignalInfoListener(String str, IHdmiSignalInfoListener iHdmiSignalInfoListener) throws RemoteException {
        }

        @Override // android.media.tv.extension.signal.IHdmiSignalInterface
        public void setForceVrr(String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.extension.signal.IHdmiSignalInterface
        public void setLowLatency(String str, int i) throws RemoteException {
        }
    }

    void addHdmiSignalInfoListener(String str, IHdmiSignalInfoListener iHdmiSignalInfoListener) throws RemoteException;

    Bundle getHdmiSignalInfo(String str) throws RemoteException;

    void removeHdmiSignalInfoListener(String str, IHdmiSignalInfoListener iHdmiSignalInfoListener) throws RemoteException;

    void setForceVrr(String str, int i) throws RemoteException;

    void setLowLatency(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IHdmiSignalInterface {
        static final int TRANSACTION_addHdmiSignalInfoListener = 1;
        static final int TRANSACTION_getHdmiSignalInfo = 3;
        static final int TRANSACTION_removeHdmiSignalInfoListener = 2;
        static final int TRANSACTION_setForceVrr = 5;
        static final int TRANSACTION_setLowLatency = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.signal.IHdmiSignalInterface");
        }

        public static IHdmiSignalInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.signal.IHdmiSignalInterface");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IHdmiSignalInterface)) {
                return (IHdmiSignalInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addHdmiSignalInfoListener";
            }
            if (i == 2) {
                return "removeHdmiSignalInfoListener";
            }
            if (i == 3) {
                return "getHdmiSignalInfo";
            }
            if (i == 4) {
                return "setLowLatency";
            }
            if (i != 5) {
                return null;
            }
            return "setForceVrr";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.signal.IHdmiSignalInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.signal.IHdmiSignalInterface");
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                IHdmiSignalInfoListener asInterface = IHdmiSignalInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addHdmiSignalInfoListener(readString, asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                String readString2 = parcel.readString();
                IHdmiSignalInfoListener asInterface2 = IHdmiSignalInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeHdmiSignalInfoListener(readString2, asInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                Bundle hdmiSignalInfo = getHdmiSignalInfo(readString3);
                parcel2.writeNoException();
                parcel2.writeTypedObject(hdmiSignalInfo, 1);
            } else if (i == 4) {
                String readString4 = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                setLowLatency(readString4, readInt);
                parcel2.writeNoException();
            } else if (i == 5) {
                String readString5 = parcel.readString();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setForceVrr(readString5, readInt2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IHdmiSignalInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.signal.IHdmiSignalInterface";
            }

            @Override // android.media.tv.extension.signal.IHdmiSignalInterface
            public void addHdmiSignalInfoListener(String str, IHdmiSignalInfoListener iHdmiSignalInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInterface");
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iHdmiSignalInfoListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IHdmiSignalInterface
            public void removeHdmiSignalInfoListener(String str, IHdmiSignalInfoListener iHdmiSignalInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInterface");
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iHdmiSignalInfoListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IHdmiSignalInterface
            public Bundle getHdmiSignalInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInterface");
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IHdmiSignalInterface
            public void setLowLatency(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInterface");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IHdmiSignalInterface
            public void setForceVrr(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInterface");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
