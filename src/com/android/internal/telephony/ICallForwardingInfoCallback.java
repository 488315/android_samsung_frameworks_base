package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.CallForwardingInfo;

/* loaded from: classes4.dex */
public interface ICallForwardingInfoCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ICallForwardingInfoCallback";

    public static class Default implements ICallForwardingInfoCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ICallForwardingInfoCallback
        public void onCallForwardingInfoAvailable(CallForwardingInfo callForwardingInfo) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ICallForwardingInfoCallback
        public void onError(int i) throws RemoteException {
        }
    }

    void onCallForwardingInfoAvailable(CallForwardingInfo callForwardingInfo) throws RemoteException;

    void onError(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ICallForwardingInfoCallback {
        static final int TRANSACTION_onCallForwardingInfoAvailable = 1;
        static final int TRANSACTION_onError = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ICallForwardingInfoCallback.DESCRIPTOR);
        }

        public static ICallForwardingInfoCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICallForwardingInfoCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICallForwardingInfoCallback)) {
                return (ICallForwardingInfoCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCallForwardingInfoAvailable";
            }
            if (i != 2) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICallForwardingInfoCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICallForwardingInfoCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                CallForwardingInfo callForwardingInfo = (CallForwardingInfo) parcel.readTypedObject(CallForwardingInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onCallForwardingInfoAvailable(callForwardingInfo);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICallForwardingInfoCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallForwardingInfoCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ICallForwardingInfoCallback
            public void onCallForwardingInfoAvailable(CallForwardingInfo callForwardingInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallForwardingInfoCallback.DESCRIPTOR);
                    obtain.writeTypedObject(callForwardingInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICallForwardingInfoCallback
            public void onError(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallForwardingInfoCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
