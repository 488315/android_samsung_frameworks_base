package android.telephony.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telephony.IIntegerConsumer;

/* loaded from: classes4.dex */
public interface IQualifiedNetworksServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.data.IQualifiedNetworksServiceCallback";

    public static class Default implements IQualifiedNetworksServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.data.IQualifiedNetworksServiceCallback
        public void onHandoverEnabledChanged(int i) throws RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksServiceCallback
        public void onNetworkValidationRequested(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksServiceCallback
        public void onQualifiedNetworkTypesChanged(int i, int[] iArr) throws RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksServiceCallback
        public void onReconnectQualifiedNetworkType(int i, int i2) throws RemoteException {
        }
    }

    void onHandoverEnabledChanged(int i) throws RemoteException;

    void onNetworkValidationRequested(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void onQualifiedNetworkTypesChanged(int i, int[] iArr) throws RemoteException;

    void onReconnectQualifiedNetworkType(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IQualifiedNetworksServiceCallback {
        static final int TRANSACTION_onHandoverEnabledChanged = 4;
        static final int TRANSACTION_onNetworkValidationRequested = 2;
        static final int TRANSACTION_onQualifiedNetworkTypesChanged = 1;
        static final int TRANSACTION_onReconnectQualifiedNetworkType = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IQualifiedNetworksServiceCallback.DESCRIPTOR);
        }

        public static IQualifiedNetworksServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IQualifiedNetworksServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IQualifiedNetworksServiceCallback)) {
                return (IQualifiedNetworksServiceCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onQualifiedNetworkTypesChanged";
            }
            if (i == 2) {
                return "onNetworkValidationRequested";
            }
            if (i == 3) {
                return "onReconnectQualifiedNetworkType";
            }
            if (i != 4) {
                return null;
            }
            return "onHandoverEnabledChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IQualifiedNetworksServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IQualifiedNetworksServiceCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int[] createIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                onQualifiedNetworkTypesChanged(readInt, createIntArray);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                IIntegerConsumer asInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onNetworkValidationRequested(readInt2, asInterface);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onReconnectQualifiedNetworkType(readInt3, readInt4);
            } else if (i == 4) {
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onHandoverEnabledChanged(readInt5);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IQualifiedNetworksServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IQualifiedNetworksServiceCallback.DESCRIPTOR;
            }

            @Override // android.telephony.data.IQualifiedNetworksServiceCallback
            public void onQualifiedNetworkTypesChanged(int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IQualifiedNetworksServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksServiceCallback
            public void onNetworkValidationRequested(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IQualifiedNetworksServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksServiceCallback
            public void onReconnectQualifiedNetworkType(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IQualifiedNetworksServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksServiceCallback
            public void onHandoverEnabledChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IQualifiedNetworksServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
