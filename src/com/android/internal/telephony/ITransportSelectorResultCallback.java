package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telephony.IWwanSelectorCallback;

/* loaded from: classes4.dex */
public interface ITransportSelectorResultCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ITransportSelectorResultCallback";

    public static class Default implements ITransportSelectorResultCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ITransportSelectorResultCallback
        public void onCompleted(IWwanSelectorCallback iWwanSelectorCallback) throws RemoteException {
        }
    }

    void onCompleted(IWwanSelectorCallback iWwanSelectorCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ITransportSelectorResultCallback {
        static final int TRANSACTION_onCompleted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ITransportSelectorResultCallback.DESCRIPTOR);
        }

        public static ITransportSelectorResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITransportSelectorResultCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITransportSelectorResultCallback)) {
                return (ITransportSelectorResultCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onCompleted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITransportSelectorResultCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITransportSelectorResultCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IWwanSelectorCallback iWwanSelectorCallbackAsInterface = IWwanSelectorCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCompleted(iWwanSelectorCallbackAsInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITransportSelectorResultCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITransportSelectorResultCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ITransportSelectorResultCallback
            public void onCompleted(IWwanSelectorCallback iWwanSelectorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITransportSelectorResultCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWwanSelectorCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
