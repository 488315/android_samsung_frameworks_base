package com.samsung.android.knox.ucm.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ICredentialManagerServiceSystemUICallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback";

    void setUCMKeyguardVendor(String str) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ICredentialManagerServiceSystemUICallback {
        public static final int TRANSACTION_setUCMKeyguardVendor = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICredentialManagerServiceSystemUICallback)) ? new Proxy(iBinder) : (ICredentialManagerServiceSystemUICallback) queryLocalInterface;
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
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setUCMKeyguardVendor(readString);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
