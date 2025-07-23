package com.samsung.android.knox.tima.attestation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.tima.attestation.IEnhancedAttestationPolicyCallback;

/* loaded from: classes6.dex */
public interface IEnhancedAttestation extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.tima.attestation.IEnhancedAttestation";

    public static class Default implements IEnhancedAttestation {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.tima.attestation.IEnhancedAttestation
        public void enhancedAttestation(String str, String str2, IEnhancedAttestationPolicyCallback iEnhancedAttestationPolicyCallback, boolean z) throws RemoteException {
        }
    }

    void enhancedAttestation(String str, String str2, IEnhancedAttestationPolicyCallback iEnhancedAttestationPolicyCallback, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IEnhancedAttestation {
        static final int TRANSACTION_enhancedAttestation = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IEnhancedAttestation.DESCRIPTOR);
        }

        public static IEnhancedAttestation asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEnhancedAttestation.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEnhancedAttestation)) {
                return (IEnhancedAttestation) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "enhancedAttestation";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEnhancedAttestation.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEnhancedAttestation.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                IEnhancedAttestationPolicyCallback asInterface = IEnhancedAttestationPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                enhancedAttestation(readString, readString2, asInterface, readBoolean);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IEnhancedAttestation {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEnhancedAttestation.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.tima.attestation.IEnhancedAttestation
            public void enhancedAttestation(String str, String str2, IEnhancedAttestationPolicyCallback iEnhancedAttestationPolicyCallback, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnhancedAttestation.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iEnhancedAttestationPolicyCallback);
                    obtain.writeBoolean(z);
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
