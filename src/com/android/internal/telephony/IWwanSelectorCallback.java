package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telephony.IWwanSelectorResultCallback;

/* loaded from: classes4.dex */
public interface IWwanSelectorCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IWwanSelectorCallback";

    public static class Default implements IWwanSelectorCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.IWwanSelectorCallback
        public void onCancel() throws RemoteException {
        }

        @Override // com.android.internal.telephony.IWwanSelectorCallback
        public void onDomainSelected(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IWwanSelectorCallback
        public void onRequestEmergencyNetworkScan(int[] iArr, int i, boolean z, IWwanSelectorResultCallback iWwanSelectorResultCallback) throws RemoteException {
        }
    }

    void onCancel() throws RemoteException;

    void onDomainSelected(int i, boolean z) throws RemoteException;

    void onRequestEmergencyNetworkScan(int[] iArr, int i, boolean z, IWwanSelectorResultCallback iWwanSelectorResultCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IWwanSelectorCallback {
        static final int TRANSACTION_onCancel = 3;
        static final int TRANSACTION_onDomainSelected = 2;
        static final int TRANSACTION_onRequestEmergencyNetworkScan = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IWwanSelectorCallback.DESCRIPTOR);
        }

        public static IWwanSelectorCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWwanSelectorCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWwanSelectorCallback)) {
                return (IWwanSelectorCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onRequestEmergencyNetworkScan";
            }
            if (i == 2) {
                return "onDomainSelected";
            }
            if (i != 3) {
                return null;
            }
            return "onCancel";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWwanSelectorCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWwanSelectorCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int[] createIntArray = parcel.createIntArray();
                int readInt = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                IWwanSelectorResultCallback asInterface = IWwanSelectorResultCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onRequestEmergencyNetworkScan(createIntArray, readInt, readBoolean, asInterface);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onDomainSelected(readInt2, readBoolean2);
            } else if (i == 3) {
                onCancel();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IWwanSelectorCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWwanSelectorCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IWwanSelectorCallback
            public void onRequestEmergencyNetworkScan(int[] iArr, int i, boolean z, IWwanSelectorResultCallback iWwanSelectorResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWwanSelectorCallback.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iWwanSelectorResultCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IWwanSelectorCallback
            public void onDomainSelected(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWwanSelectorCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IWwanSelectorCallback
            public void onCancel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWwanSelectorCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
