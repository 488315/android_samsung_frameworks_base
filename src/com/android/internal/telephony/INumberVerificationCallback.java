package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface INumberVerificationCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.INumberVerificationCallback";

    public static class Default implements INumberVerificationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.INumberVerificationCallback
        public void onCallReceived(String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.INumberVerificationCallback
        public void onVerificationFailed(int i) throws RemoteException {
        }
    }

    void onCallReceived(String str) throws RemoteException;

    void onVerificationFailed(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements INumberVerificationCallback {
        static final int TRANSACTION_onCallReceived = 1;
        static final int TRANSACTION_onVerificationFailed = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, INumberVerificationCallback.DESCRIPTOR);
        }

        public static INumberVerificationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(INumberVerificationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INumberVerificationCallback)) {
                return (INumberVerificationCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCallReceived";
            }
            if (i != 2) {
                return null;
            }
            return "onVerificationFailed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INumberVerificationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INumberVerificationCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onCallReceived(readString);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onVerificationFailed(readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements INumberVerificationCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INumberVerificationCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.INumberVerificationCallback
            public void onCallReceived(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(INumberVerificationCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.INumberVerificationCallback
            public void onVerificationFailed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(INumberVerificationCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
