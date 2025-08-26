package com.samsung.android.knox.appconfig;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IApplicationRestrictionsResultCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.appconfig.IApplicationRestrictionsResultCallback";

    void onActionResult(String str, Bundle bundle) throws RemoteException;

    public abstract class Stub extends Binder implements IApplicationRestrictionsResultCallback {
        public static final int TRANSACTION_onActionResult = 1;

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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationRestrictionsResultCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IApplicationRestrictionsResultCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IApplicationRestrictionsResultCallback)) ? new Proxy(iBinder) : (IApplicationRestrictionsResultCallback) iInterfaceQueryLocalInterface;
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
            String string = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            onActionResult(string, bundle);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

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
