package com.samsung.android.knox.license;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ILicenseResultCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.license.ILicenseResultCallback";

    void onLicenseResult(LicenseResult licenseResult) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ILicenseResultCallback {
        static final int TRANSACTION_onLicenseResult = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILicenseResultCallback.DESCRIPTOR);
                    obtain.writeTypedObject(licenseResult, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        licenseResult.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILicenseResultCallback.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ILicenseResultCallback)) ? new Proxy(iBinder) : (ILicenseResultCallback) queryLocalInterface;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
