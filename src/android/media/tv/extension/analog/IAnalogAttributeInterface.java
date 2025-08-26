package android.media.tv.extension.analog;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IAnalogAttributeInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.analog.IAnalogAttributeInterface";

    public static class Default implements IAnalogAttributeInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.analog.IAnalogAttributeInterface
        public String[] getColorSystemCapability() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.analog.IAnalogAttributeInterface
        public int getVersion() throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.analog.IAnalogAttributeInterface
        public void setColorSystemCapability(String[] strArr) throws RemoteException {
        }
    }

    String[] getColorSystemCapability() throws RemoteException;

    int getVersion() throws RemoteException;

    void setColorSystemCapability(String[] strArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IAnalogAttributeInterface {
        static final int TRANSACTION_getColorSystemCapability = 3;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_setColorSystemCapability = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.analog.IAnalogAttributeInterface");
        }

        public static IAnalogAttributeInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.analog.IAnalogAttributeInterface");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAnalogAttributeInterface)) {
                return (IAnalogAttributeInterface) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getVersion";
            }
            if (i == 2) {
                return "setColorSystemCapability";
            }
            if (i != 3) {
                return null;
            }
            return "getColorSystemCapability";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.analog.IAnalogAttributeInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.analog.IAnalogAttributeInterface");
                return true;
            }
            if (i == 1) {
                int version = getVersion();
                parcel2.writeNoException();
                parcel2.writeInt(version);
            } else if (i == 2) {
                String[] strArrCreateStringArray = parcel.createStringArray();
                parcel.enforceNoDataAvail();
                setColorSystemCapability(strArrCreateStringArray);
                parcel2.writeNoException();
            } else if (i == 3) {
                String[] colorSystemCapability = getColorSystemCapability();
                parcel2.writeNoException();
                parcel2.writeStringArray(colorSystemCapability);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAnalogAttributeInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.analog.IAnalogAttributeInterface";
            }

            @Override // android.media.tv.extension.analog.IAnalogAttributeInterface
            public int getVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.analog.IAnalogAttributeInterface");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.analog.IAnalogAttributeInterface
            public void setColorSystemCapability(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.analog.IAnalogAttributeInterface");
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.analog.IAnalogAttributeInterface
            public String[] getColorSystemCapability() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.analog.IAnalogAttributeInterface");
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
