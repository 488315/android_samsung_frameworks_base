package com.samsung.android.knox.kpm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.kpm.IKnoxPushServiceCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IKnoxPushService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.kpm.IKnoxPushService";

    void isRegistered(IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException;

    void registerDevice(boolean z, IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException;

    void unRegisterDevice(IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IKnoxPushService {
        public static final int TRANSACTION_isRegistered = 3;
        public static final int TRANSACTION_registerDevice = 1;
        public static final int TRANSACTION_unRegisterDevice = 2;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IKnoxPushService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKnoxPushService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.kpm.IKnoxPushService
            public void isRegistered(IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxPushService.DESCRIPTOR);
                    obtain.writeStrongInterface(iKnoxPushServiceCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kpm.IKnoxPushService
            public void registerDevice(boolean z, IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxPushService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iKnoxPushServiceCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kpm.IKnoxPushService
            public void unRegisterDevice(IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxPushService.DESCRIPTOR);
                    obtain.writeStrongInterface(iKnoxPushServiceCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxPushService.DESCRIPTOR);
        }

        public static IKnoxPushService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxPushService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKnoxPushService)) ? new Proxy(iBinder) : (IKnoxPushService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxPushService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxPushService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                IKnoxPushServiceCallback asInterface = IKnoxPushServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerDevice(readBoolean, asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IKnoxPushServiceCallback asInterface2 = IKnoxPushServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unRegisterDevice(asInterface2);
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                IKnoxPushServiceCallback asInterface3 = IKnoxPushServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                isRegistered(asInterface3);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IKnoxPushService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushService
        public void isRegistered(IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException {
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushService
        public void unRegisterDevice(IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException {
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushService
        public void registerDevice(boolean z, IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException {
        }
    }
}
