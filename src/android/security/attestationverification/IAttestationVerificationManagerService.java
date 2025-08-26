package android.security.attestationverification;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelDuration;
import android.os.RemoteException;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes3.dex */
public interface IAttestationVerificationManagerService extends IInterface {
    public static final String DESCRIPTOR = "android.security.attestationverification.IAttestationVerificationManagerService";

    public static class Default implements IAttestationVerificationManagerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.attestationverification.IAttestationVerificationManagerService
        public void verifyAttestation(AttestationProfile attestationProfile, int i, Bundle bundle, byte[] bArr, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.security.attestationverification.IAttestationVerificationManagerService
        public void verifyToken(VerificationToken verificationToken, ParcelDuration parcelDuration, AndroidFuture androidFuture) throws RemoteException {
        }
    }

    void verifyAttestation(AttestationProfile attestationProfile, int i, Bundle bundle, byte[] bArr, AndroidFuture androidFuture) throws RemoteException;

    void verifyToken(VerificationToken verificationToken, ParcelDuration parcelDuration, AndroidFuture androidFuture) throws RemoteException;

    public static abstract class Stub extends Binder implements IAttestationVerificationManagerService {
        static final int TRANSACTION_verifyAttestation = 1;
        static final int TRANSACTION_verifyToken = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IAttestationVerificationManagerService.DESCRIPTOR);
        }

        public static IAttestationVerificationManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAttestationVerificationManagerService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAttestationVerificationManagerService)) {
                return (IAttestationVerificationManagerService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "verifyAttestation";
            }
            if (i != 2) {
                return null;
            }
            return "verifyToken";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAttestationVerificationManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAttestationVerificationManagerService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AttestationProfile attestationProfile = (AttestationProfile) parcel.readTypedObject(AttestationProfile.CREATOR);
                int i3 = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                byte[] bArrCreateByteArray = parcel.createByteArray();
                AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                parcel.enforceNoDataAvail();
                verifyAttestation(attestationProfile, i3, bundle, bArrCreateByteArray, androidFuture);
            } else if (i == 2) {
                VerificationToken verificationToken = (VerificationToken) parcel.readTypedObject(VerificationToken.CREATOR);
                ParcelDuration parcelDuration = (ParcelDuration) parcel.readTypedObject(ParcelDuration.CREATOR);
                AndroidFuture androidFuture2 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                parcel.enforceNoDataAvail();
                verifyToken(verificationToken, parcelDuration, androidFuture2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAttestationVerificationManagerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAttestationVerificationManagerService.DESCRIPTOR;
            }

            @Override // android.security.attestationverification.IAttestationVerificationManagerService
            public void verifyAttestation(AttestationProfile attestationProfile, int i, Bundle bundle, byte[] bArr, AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAttestationVerificationManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(attestationProfile, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.attestationverification.IAttestationVerificationManagerService
            public void verifyToken(VerificationToken verificationToken, ParcelDuration parcelDuration, AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAttestationVerificationManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(verificationToken, 0);
                    parcelObtain.writeTypedObject(parcelDuration, 0);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
