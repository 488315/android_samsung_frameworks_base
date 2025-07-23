package com.samsung.android.knox.appconfig;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IApplicationRestrictionsResultCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.appconfig.IApplicationRestrictionsResultCallback";

    void onActionResult(String str, Bundle bundle) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IApplicationRestrictionsResultCallback {
        public static final int TRANSACTION_onActionResult = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IApplicationRestrictionsResultCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IApplicationRestrictionsResultCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.appconfig.IApplicationRestrictionsResultCallback
            public void onActionResult(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationRestrictionsResultCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IApplicationRestrictionsResultCallback.DESCRIPTOR);
        }

        public static IApplicationRestrictionsResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IApplicationRestrictionsResultCallback.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IApplicationRestrictionsResultCallback)) ? new Proxy(iBinder) : (IApplicationRestrictionsResultCallback) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onActionResult";
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
                parcel.enforceInterface(IApplicationRestrictionsResultCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IApplicationRestrictionsResultCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            String readString = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            onActionResult(readString, bundle);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IApplicationRestrictionsResultCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.appconfig.IApplicationRestrictionsResultCallback
        public void onActionResult(String str, Bundle bundle) throws RemoteException {
        }
    }
}
