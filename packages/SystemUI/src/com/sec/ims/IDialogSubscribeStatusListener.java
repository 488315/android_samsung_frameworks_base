package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IDialogSubscribeStatusListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IDialogSubscribeStatusListener";

    void onDialogSubscribeStatus(int i) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IDialogSubscribeStatusListener {
        static final int TRANSACTION_onDialogSubscribeStatus = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IDialogSubscribeStatusListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDialogSubscribeStatusListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.IDialogSubscribeStatusListener
            public void onDialogSubscribeStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDialogSubscribeStatusListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDialogSubscribeStatusListener.DESCRIPTOR);
        }

        public static IDialogSubscribeStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDialogSubscribeStatusListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDialogSubscribeStatusListener)) ? new Proxy(iBinder) : (IDialogSubscribeStatusListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDialogSubscribeStatusListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDialogSubscribeStatusListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            onDialogSubscribeStatus(readInt);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IDialogSubscribeStatusListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IDialogSubscribeStatusListener
        public void onDialogSubscribeStatus(int i) throws RemoteException {
        }
    }
}
