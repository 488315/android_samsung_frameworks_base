package android.security.attestationverification;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes3.dex */
public interface IAttestationVerificationService extends IInterface {
    public static final String DESCRIPTOR = "android.security.attestationverification.IAttestationVerificationService";

    public static class Default implements IAttestationVerificationService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.attestationverification.IAttestationVerificationService
        public void onVerifyAttestation(Bundle bundle, byte[] bArr, AndroidFuture androidFuture) throws RemoteException {
        }
    }

    void onVerifyAttestation(Bundle bundle, byte[] bArr, AndroidFuture androidFuture) throws RemoteException;

    public static abstract class Stub extends Binder implements IAttestationVerificationService {
        static final int TRANSACTION_onVerifyAttestation = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAttestationVerificationService.DESCRIPTOR);
        }

        public static IAttestationVerificationService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAttestationVerificationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAttestationVerificationService)) {
                return (IAttestationVerificationService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onVerifyAttestation";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAttestationVerificationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAttestationVerificationService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                byte[] createByteArray = parcel.createByteArray();
                AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                parcel.enforceNoDataAvail();
                onVerifyAttestation(bundle, createByteArray, androidFuture);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAttestationVerificationService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAttestationVerificationService.DESCRIPTOR;
            }

            @Override // android.security.attestationverification.IAttestationVerificationService
            public void onVerifyAttestation(Bundle bundle, byte[] bArr, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAttestationVerificationService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
