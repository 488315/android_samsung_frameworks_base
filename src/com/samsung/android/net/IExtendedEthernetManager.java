package com.samsung.android.net;

import android.net.IpConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IExtendedEthernetManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.net.IExtendedEthernetManager";

    public static class Default implements IExtendedEthernetManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.net.IExtendedEthernetManager
        public IpConfiguration getConfiguration(String str) throws RemoteException {
            return null;
        }
    }

    IpConfiguration getConfiguration(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IExtendedEthernetManager {
        static final int TRANSACTION_getConfiguration = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IExtendedEthernetManager.DESCRIPTOR);
        }

        public static IExtendedEthernetManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IExtendedEthernetManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IExtendedEthernetManager)) {
                return (IExtendedEthernetManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getConfiguration";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IExtendedEthernetManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IExtendedEthernetManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                IpConfiguration configuration = getConfiguration(string);
                parcel2.writeNoException();
                parcel2.writeTypedObject(configuration, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IExtendedEthernetManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExtendedEthernetManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.net.IExtendedEthernetManager
            public IpConfiguration getConfiguration(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExtendedEthernetManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (IpConfiguration) parcelObtain2.readTypedObject(IpConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
