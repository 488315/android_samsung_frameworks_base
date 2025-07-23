package com.samsung.android.knox.tima.attestation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IEnhancedAttestationPolicyCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.tima.attestation.IEnhancedAttestationPolicyCallback";

    public static class Default implements IEnhancedAttestationPolicyCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.tima.attestation.IEnhancedAttestationPolicyCallback
        public void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult) throws RemoteException {
        }
    }

    void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult) throws RemoteException;

    public static abstract class Stub extends Binder implements IEnhancedAttestationPolicyCallback {
        static final int TRANSACTION_onAttestationFinished = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IEnhancedAttestationPolicyCallback.DESCRIPTOR);
        }

        public static IEnhancedAttestationPolicyCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEnhancedAttestationPolicyCallback)) {
                return (IEnhancedAttestationPolicyCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAttestationFinished";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                EnhancedAttestationResult enhancedAttestationResult = (EnhancedAttestationResult) parcel.readTypedObject(EnhancedAttestationResult.CREATOR);
                parcel.enforceNoDataAvail();
                onAttestationFinished(enhancedAttestationResult);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IEnhancedAttestationPolicyCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEnhancedAttestationPolicyCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.tima.attestation.IEnhancedAttestationPolicyCallback
            public void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
                    obtain.writeTypedObject(enhancedAttestationResult, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
