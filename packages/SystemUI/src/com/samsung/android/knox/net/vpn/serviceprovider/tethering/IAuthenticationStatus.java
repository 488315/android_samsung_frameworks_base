package com.samsung.android.knox.net.vpn.serviceprovider.tethering;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IAuthenticationStatus extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.vpn.serviceprovider.tethering.IAuthenticationStatus";

    void getStatus(int i) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IAuthenticationStatus {
        public static final int TRANSACTION_getStatus = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IAuthenticationStatus {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAuthenticationStatus.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IAuthenticationStatus
            public void getStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAuthenticationStatus.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IAuthenticationStatus.DESCRIPTOR);
        }

        public static IAuthenticationStatus asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAuthenticationStatus.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IAuthenticationStatus)) ? new Proxy(iBinder) : (IAuthenticationStatus) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAuthenticationStatus.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAuthenticationStatus.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            getStatus(readInt);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IAuthenticationStatus {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IAuthenticationStatus
        public void getStatus(int i) throws RemoteException {
        }
    }
}
