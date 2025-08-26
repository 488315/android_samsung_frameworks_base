package com.samsung.android.knox.ucm.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ICredentialManagerServiceSystemUICallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback";

    void setUCMKeyguardVendor(String str) throws RemoteException;

    public abstract class Stub extends Binder implements ICredentialManagerServiceSystemUICallback {
        public static final int TRANSACTION_setUCMKeyguardVendor = 1;

        class Proxy implements ICredentialManagerServiceSystemUICallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICredentialManagerServiceSystemUICallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback
            public void setUCMKeyguardVendor(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
        }

        public static ICredentialManagerServiceSystemUICallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICredentialManagerServiceSystemUICallback)) ? new Proxy(iBinder) : (ICredentialManagerServiceSystemUICallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setUCMKeyguardVendor(string);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements ICredentialManagerServiceSystemUICallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback
        public void setUCMKeyguardVendor(String str) throws RemoteException {
        }
    }
}
