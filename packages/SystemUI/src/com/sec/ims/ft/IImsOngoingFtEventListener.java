package com.sec.ims.ft;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IImsOngoingFtEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ft.IImsOngoingFtEventListener";

    void onFtStateChanged(boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IImsOngoingFtEventListener {
        static final int TRANSACTION_onFtStateChanged = 1;

        class Proxy implements IImsOngoingFtEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsOngoingFtEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.ft.IImsOngoingFtEventListener
            public void onFtStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsOngoingFtEventListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsOngoingFtEventListener.DESCRIPTOR);
        }

        public static IImsOngoingFtEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsOngoingFtEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IImsOngoingFtEventListener)) ? new Proxy(iBinder) : (IImsOngoingFtEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsOngoingFtEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsOngoingFtEventListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            onFtStateChanged(z);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IImsOngoingFtEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ft.IImsOngoingFtEventListener
        public void onFtStateChanged(boolean z) throws RemoteException {
        }
    }
}
