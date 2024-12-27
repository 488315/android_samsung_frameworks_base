package com.samsung.android.knox.tima.attestation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IEnhancedAttestationPolicyCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.knox.tima.attestation.IEnhancedAttestationPolicyCallback";

    void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult)
            throws RemoteException;

    public static class Default implements IEnhancedAttestationPolicyCallback {
        @Override // com.samsung.android.knox.tima.attestation.IEnhancedAttestationPolicyCallback
        public void onAttestationFinished(EnhancedAttestationResult result)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IEnhancedAttestationPolicyCallback {
        static final int TRANSACTION_onAttestationFinished = 1;

        public Stub() {
            attachInterface(this, IEnhancedAttestationPolicyCallback.DESCRIPTOR);
        }

        public static IEnhancedAttestationPolicyCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IEnhancedAttestationPolicyCallback)) {
                return (IEnhancedAttestationPolicyCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onAttestationFinished";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    EnhancedAttestationResult _arg0 =
                            (EnhancedAttestationResult)
                                    data.readTypedObject(EnhancedAttestationResult.CREATOR);
                    data.enforceNoDataAvail();
                    onAttestationFinished(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IEnhancedAttestationPolicyCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEnhancedAttestationPolicyCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.tima.attestation.IEnhancedAttestationPolicyCallback
            public void onAttestationFinished(EnhancedAttestationResult result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
