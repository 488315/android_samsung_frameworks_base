package com.samsung.android.icccgrdm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IGrdmIntegrityControlCheckCenter extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.icccgrdm.IGrdmIntegrityControlCheckCenter";

    public static class Default implements IGrdmIntegrityControlCheckCenter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.icccgrdm.IGrdmIntegrityControlCheckCenter
        public byte[] grdmSetAttestationData(byte[] bArr) throws RemoteException {
            return null;
        }
    }

    byte[] grdmSetAttestationData(byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IGrdmIntegrityControlCheckCenter {
        static final int TRANSACTION_grdmSetAttestationData = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IGrdmIntegrityControlCheckCenter.DESCRIPTOR);
        }

        public static IGrdmIntegrityControlCheckCenter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGrdmIntegrityControlCheckCenter.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGrdmIntegrityControlCheckCenter)) {
                return (IGrdmIntegrityControlCheckCenter) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "grdmSetAttestationData";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGrdmIntegrityControlCheckCenter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGrdmIntegrityControlCheckCenter.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                byte[] bArrCreateByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                byte[] bArrGrdmSetAttestationData = grdmSetAttestationData(bArrCreateByteArray);
                parcel2.writeNoException();
                parcel2.writeByteArray(bArrGrdmSetAttestationData);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGrdmIntegrityControlCheckCenter {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGrdmIntegrityControlCheckCenter.DESCRIPTOR;
            }

            @Override // com.samsung.android.icccgrdm.IGrdmIntegrityControlCheckCenter
            public byte[] grdmSetAttestationData(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGrdmIntegrityControlCheckCenter.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
