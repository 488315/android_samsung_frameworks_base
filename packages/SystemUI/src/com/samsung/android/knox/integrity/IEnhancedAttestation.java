package com.samsung.android.knox.integrity;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback;

/* loaded from: classes4.dex */
public interface IEnhancedAttestation extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.integrity.IEnhancedAttestation";

    void enhancedAttestation(String str, String str2, IEnhancedAttestationPolicyCallback iEnhancedAttestationPolicyCallback, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IEnhancedAttestation {
        public static final int TRANSACTION_enhancedAttestation = 1;

        class Proxy implements IEnhancedAttestation {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.integrity.IEnhancedAttestation
            public void enhancedAttestation(String str, String str2, IEnhancedAttestationPolicyCallback iEnhancedAttestationPolicyCallback, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnhancedAttestation.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iEnhancedAttestationPolicyCallback);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IEnhancedAttestation.DESCRIPTOR;
            }
        }

        public Stub() {
            attachInterface(this, IEnhancedAttestation.DESCRIPTOR);
        }

        public static IEnhancedAttestation asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEnhancedAttestation.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IEnhancedAttestation)) ? new Proxy(iBinder) : (IEnhancedAttestation) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "enhancedAttestation";
        }

        public int getMaxTransactionId() {
            return 0;
        }

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
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            String string = parcel.readString();
            String string2 = parcel.readString();
            IEnhancedAttestationPolicyCallback iEnhancedAttestationPolicyCallbackAsInterface = IEnhancedAttestationPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            enhancedAttestation(string, string2, iEnhancedAttestationPolicyCallbackAsInterface, z);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IEnhancedAttestation {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.integrity.IEnhancedAttestation
        public void enhancedAttestation(String str, String str2, IEnhancedAttestationPolicyCallback iEnhancedAttestationPolicyCallback, boolean z) throws RemoteException {
        }
    }
}
