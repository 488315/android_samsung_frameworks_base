package com.samsung.android.knox.kpm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.kpm.IKnoxPushServiceCallback;

/* loaded from: classes4.dex */
public interface IKnoxPushService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.kpm.IKnoxPushService";

    void isRegistered(IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException;

    void registerDevice(boolean z, IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException;

    void unRegisterDevice(IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException;

    public abstract class Stub extends Binder implements IKnoxPushService {
        public static final int TRANSACTION_isRegistered = 3;
        public static final int TRANSACTION_registerDevice = 1;
        public static final int TRANSACTION_unRegisterDevice = 2;

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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxPushService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKnoxPushServiceCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kpm.IKnoxPushService
            public void registerDevice(boolean z, IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxPushService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iKnoxPushServiceCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kpm.IKnoxPushService
            public void unRegisterDevice(IKnoxPushServiceCallback iKnoxPushServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxPushService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKnoxPushServiceCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxPushService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKnoxPushService)) ? new Proxy(iBinder) : (IKnoxPushService) iInterfaceQueryLocalInterface;
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
                boolean z = parcel.readBoolean();
                IKnoxPushServiceCallback iKnoxPushServiceCallbackAsInterface = IKnoxPushServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerDevice(z, iKnoxPushServiceCallbackAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IKnoxPushServiceCallback iKnoxPushServiceCallbackAsInterface2 = IKnoxPushServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unRegisterDevice(iKnoxPushServiceCallbackAsInterface2);
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                IKnoxPushServiceCallback iKnoxPushServiceCallbackAsInterface3 = IKnoxPushServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                isRegistered(iKnoxPushServiceCallbackAsInterface3);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

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
