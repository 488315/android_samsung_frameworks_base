package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telephony.IDomainSelector;
import com.android.internal.telephony.ITransportSelectorResultCallback;

/* loaded from: classes4.dex */
public interface ITransportSelectorCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ITransportSelectorCallback";

    public static class Default implements ITransportSelectorCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onCreated(IDomainSelector iDomainSelector) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onSelectionTerminated(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onWlanSelected(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onWwanSelectedAsync(ITransportSelectorResultCallback iTransportSelectorResultCallback) throws RemoteException {
        }
    }

    void onCreated(IDomainSelector iDomainSelector) throws RemoteException;

    void onSelectionTerminated(int i) throws RemoteException;

    void onWlanSelected(boolean z) throws RemoteException;

    void onWwanSelectedAsync(ITransportSelectorResultCallback iTransportSelectorResultCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ITransportSelectorCallback {
        static final int TRANSACTION_onCreated = 1;
        static final int TRANSACTION_onSelectionTerminated = 4;
        static final int TRANSACTION_onWlanSelected = 2;
        static final int TRANSACTION_onWwanSelectedAsync = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ITransportSelectorCallback.DESCRIPTOR);
        }

        public static ITransportSelectorCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITransportSelectorCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITransportSelectorCallback)) {
                return (ITransportSelectorCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCreated";
            }
            if (i == 2) {
                return "onWlanSelected";
            }
            if (i == 3) {
                return "onWwanSelectedAsync";
            }
            if (i != 4) {
                return null;
            }
            return "onSelectionTerminated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITransportSelectorCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITransportSelectorCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IDomainSelector iDomainSelectorAsInterface = IDomainSelector.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCreated(iDomainSelectorAsInterface);
            } else if (i == 2) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onWlanSelected(z);
            } else if (i == 3) {
                ITransportSelectorResultCallback iTransportSelectorResultCallbackAsInterface = ITransportSelectorResultCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onWwanSelectedAsync(iTransportSelectorResultCallbackAsInterface);
            } else if (i == 4) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSelectionTerminated(i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITransportSelectorCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITransportSelectorCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onCreated(IDomainSelector iDomainSelector) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITransportSelectorCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDomainSelector);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onWlanSelected(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITransportSelectorCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onWwanSelectedAsync(ITransportSelectorResultCallback iTransportSelectorResultCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITransportSelectorCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTransportSelectorResultCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onSelectionTerminated(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITransportSelectorCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
