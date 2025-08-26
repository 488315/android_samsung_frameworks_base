package com.samsung.android.knox.ucm.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IUCMUnlockTaskCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.core.IUCMUnlockTaskCallback";

    public class Default implements IUCMUnlockTaskCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUCMUnlockTaskCallback
        public int postAuthentication() throws RemoteException {
            return 0;
        }
    }

    int postAuthentication() throws RemoteException;

    public abstract class Stub extends Binder implements IUCMUnlockTaskCallback {
        public static final int TRANSACTION_postAuthentication = 1;

        class Proxy implements IUCMUnlockTaskCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUCMUnlockTaskCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.core.IUCMUnlockTaskCallback
            public int postAuthentication() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUCMUnlockTaskCallback.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IUCMUnlockTaskCallback.DESCRIPTOR);
        }

        public static IUCMUnlockTaskCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IUCMUnlockTaskCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IUCMUnlockTaskCallback)) ? new Proxy(iBinder) : (IUCMUnlockTaskCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUCMUnlockTaskCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUCMUnlockTaskCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int iPostAuthentication = postAuthentication();
            parcel2.writeNoException();
            parcel2.writeInt(iPostAuthentication);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
