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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.signal.IHdmiSignalInterface");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IHdmiSignalInterface)) {
                return (IHdmiSignalInterface) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                IHdmiSignalInfoListener iHdmiSignalInfoListenerAsInterface = IHdmiSignalInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addHdmiSignalInfoListener(string, iHdmiSignalInfoListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                String string2 = parcel.readString();
                IHdmiSignalInfoListener iHdmiSignalInfoListenerAsInterface2 = IHdmiSignalInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeHdmiSignalInfoListener(string2, iHdmiSignalInfoListenerAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                Bundle hdmiSignalInfo = getHdmiSignalInfo(string3);
                parcel2.writeNoException();
                parcel2.writeTypedObject(hdmiSignalInfo, 1);
            } else if (i == 4) {
                String string4 = parcel.readString();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setLowLatency(string4, i3);
                parcel2.writeNoException();
            } else if (i == 5) {
                String string5 = parcel.readString();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setForceVrr(string5, i4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInterface");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iHdmiSignalInfoListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IHdmiSignalInterface
            public void removeHdmiSignalInfoListener(String str, IHdmiSignalInfoListener iHdmiSignalInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInterface");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iHdmiSignalInfoListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IHdmiSignalInterface
            public Bundle getHdmiSignalInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInterface");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IHdmiSignalInterface
            public void setLowLatency(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInterface");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IHdmiSignalInterface
            public void setForceVrr(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInterface");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
