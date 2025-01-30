package com.android.internal.telephony;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface INumberVerificationCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.INumberVerificationCallback";

    void onCallReceived(String str) throws RemoteException;

    void onVerificationFailed(int i) throws RemoteException;

    public static class Default implements INumberVerificationCallback {
        @Override // com.android.internal.telephony.INumberVerificationCallback
        public void onCallReceived(String phoneNumber) throws RemoteException {
        }

        @Override // com.android.internal.telephony.INumberVerificationCallback
        public void onVerificationFailed(int reason) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INumberVerificationCallback {
        static final int TRANSACTION_onCallReceived = 1;
        static final int TRANSACTION_onVerificationFailed = 2;

        public Stub() {
            attachInterface(this, INumberVerificationCallback.DESCRIPTOR);
        }

        public static INumberVerificationCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(INumberVerificationCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof INumberVerificationCallback)) {
                return (INumberVerificationCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onCallReceived";
                case 2:
                    return "onVerificationFailed";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(INumberVerificationCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(INumberVerificationCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _arg0 = data.readString();
                            data.enforceNoDataAvail();
                            onCallReceived(_arg0);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            onVerificationFailed(_arg02);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements INumberVerificationCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INumberVerificationCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.INumberVerificationCallback
            public void onCallReceived(String phoneNumber) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(INumberVerificationCallback.DESCRIPTOR);
                    _data.writeString(phoneNumber);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.INumberVerificationCallback
            public void onVerificationFailed(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(INumberVerificationCallback.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
