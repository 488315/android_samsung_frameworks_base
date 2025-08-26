package com.samsung.android.knox.license;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ILicenseResultCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.license.ILicenseResultCallback";

    void onLicenseResult(LicenseResult licenseResult) throws RemoteException;

    public abstract class Stub extends Binder implements ILicenseResultCallback {
        static final int TRANSACTION_onLicenseResult = 1;

        class Proxy implements ILicenseResultCallback {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILicenseResultCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.license.ILicenseResultCallback
            public void onLicenseResult(LicenseResult licenseResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILicenseResultCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(licenseResult, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        licenseResult.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ILicenseResultCallback.DESCRIPTOR);
        }

        public static ILicenseResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ILicenseResultCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ILicenseResultCallback)) ? new Proxy(iBinder) : (ILicenseResultCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILicenseResultCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILicenseResultCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            LicenseResult licenseResult = (LicenseResult) parcel.readTypedObject(LicenseResult.CREATOR);
            parcel.enforceNoDataAvail();
            onLicenseResult(licenseResult);
            parcel2.writeNoException();
            parcel2.writeTypedObject(licenseResult, 1);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements ILicenseResultCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.license.ILicenseResultCallback
        public void onLicenseResult(LicenseResult licenseResult) throws RemoteException {
        }
    }
}
