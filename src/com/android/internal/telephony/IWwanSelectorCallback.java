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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IWwanSelectorCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWwanSelectorCallback)) {
                return (IWwanSelectorCallback) iInterfaceQueryLocalInterface;
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
                int[] iArrCreateIntArray = parcel.createIntArray();
                int i3 = parcel.readInt();
                boolean z = parcel.readBoolean();
                IWwanSelectorResultCallback iWwanSelectorResultCallbackAsInterface = IWwanSelectorResultCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onRequestEmergencyNetworkScan(iArrCreateIntArray, i3, z, iWwanSelectorResultCallbackAsInterface);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onDomainSelected(i4, z2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWwanSelectorCallback.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iWwanSelectorResultCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IWwanSelectorCallback
            public void onDomainSelected(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWwanSelectorCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IWwanSelectorCallback
            public void onCancel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWwanSelectorCallback.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
