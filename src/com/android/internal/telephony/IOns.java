package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.AvailableNetworkInfo;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import com.android.internal.telephony.IUpdateAvailableNetworksCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IOns extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IOns";

    public static class Default implements IOns {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.IOns
        public int getPreferredDataSubscriptionId(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.IOns
        public boolean isEnabled(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IOns
        public boolean setEnable(boolean z, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IOns
        public void setPreferredDataSubscriptionId(int i, boolean z, ISetOpportunisticDataCallback iSetOpportunisticDataCallback, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IOns
        public void updateAvailableNetworks(List<AvailableNetworkInfo> list, IUpdateAvailableNetworksCallback iUpdateAvailableNetworksCallback, String str) throws RemoteException {
        }
    }

    int getPreferredDataSubscriptionId(String str, String str2) throws RemoteException;

    boolean isEnabled(String str) throws RemoteException;

    boolean setEnable(boolean z, String str) throws RemoteException;

    void setPreferredDataSubscriptionId(int i, boolean z, ISetOpportunisticDataCallback iSetOpportunisticDataCallback, String str) throws RemoteException;

    void updateAvailableNetworks(List<AvailableNetworkInfo> list, IUpdateAvailableNetworksCallback iUpdateAvailableNetworksCallback, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IOns {
        static final int TRANSACTION_getPreferredDataSubscriptionId = 4;
        static final int TRANSACTION_isEnabled = 2;
        static final int TRANSACTION_setEnable = 1;
        static final int TRANSACTION_setPreferredDataSubscriptionId = 3;
        static final int TRANSACTION_updateAvailableNetworks = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IOns.DESCRIPTOR);
        }

        public static IOns asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOns.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOns)) {
                return (IOns) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setEnable";
            }
            if (i == 2) {
                return "isEnabled";
            }
            if (i == 3) {
                return "setPreferredDataSubscriptionId";
            }
            if (i == 4) {
                return "getPreferredDataSubscriptionId";
            }
            if (i != 5) {
                return null;
            }
            return "updateAvailableNetworks";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOns.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOns.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean enable = setEnable(readBoolean, readString);
                parcel2.writeNoException();
                parcel2.writeBoolean(enable);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean isEnabled = isEnabled(readString2);
                parcel2.writeNoException();
                parcel2.writeBoolean(isEnabled);
            } else if (i == 3) {
                int readInt = parcel.readInt();
                boolean readBoolean2 = parcel.readBoolean();
                ISetOpportunisticDataCallback asInterface = ISetOpportunisticDataCallback.Stub.asInterface(parcel.readStrongBinder());
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                setPreferredDataSubscriptionId(readInt, readBoolean2, asInterface, readString3);
                parcel2.writeNoException();
            } else if (i == 4) {
                String readString4 = parcel.readString();
                String readString5 = parcel.readString();
                parcel.enforceNoDataAvail();
                int preferredDataSubscriptionId = getPreferredDataSubscriptionId(readString4, readString5);
                parcel2.writeNoException();
                parcel2.writeInt(preferredDataSubscriptionId);
            } else if (i == 5) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(AvailableNetworkInfo.CREATOR);
                IUpdateAvailableNetworksCallback asInterface2 = IUpdateAvailableNetworksCallback.Stub.asInterface(parcel.readStrongBinder());
                String readString6 = parcel.readString();
                parcel.enforceNoDataAvail();
                updateAvailableNetworks(createTypedArrayList, asInterface2, readString6);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IOns {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOns.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IOns
            public boolean setEnable(boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOns.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public boolean isEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOns.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public void setPreferredDataSubscriptionId(int i, boolean z, ISetOpportunisticDataCallback iSetOpportunisticDataCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOns.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iSetOpportunisticDataCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public int getPreferredDataSubscriptionId(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOns.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public void updateAvailableNetworks(List<AvailableNetworkInfo> list, IUpdateAvailableNetworksCallback iUpdateAvailableNetworksCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOns.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iUpdateAvailableNetworksCallback);
                    obtain.writeString(str);
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
