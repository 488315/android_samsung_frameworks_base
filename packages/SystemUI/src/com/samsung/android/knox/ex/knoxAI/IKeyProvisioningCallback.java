package com.samsung.android.knox.ex.knoxAI;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IKeyProvisioningCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ex.knoxAI.IKeyProvisioningCallback";

    void onFinished(int i) throws RemoteException;

    public abstract class Stub extends Binder implements IKeyProvisioningCallback {
        public static final int TRANSACTION_onFinished = 1;

        class Proxy implements IKeyProvisioningCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyProvisioningCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IKeyProvisioningCallback
            public void onFinished(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKeyProvisioningCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKeyProvisioningCallback.DESCRIPTOR);
        }

        public static IKeyProvisioningCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKeyProvisioningCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKeyProvisioningCallback)) ? new Proxy(iBinder) : (IKeyProvisioningCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKeyProvisioningCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeyProvisioningCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            onFinished(i3);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IKeyProvisioningCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IKeyProvisioningCallback
        public void onFinished(int i) throws RemoteException {
        }
    }
}
