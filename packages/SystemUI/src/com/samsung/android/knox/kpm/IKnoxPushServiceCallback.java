package com.samsung.android.knox.kpm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IKnoxPushServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.kpm.IKnoxPushServiceCallback";

    void onRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException;

    void onRegistrationStatus(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException;

    void onUnRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException;

    public abstract class Stub extends Binder implements IKnoxPushServiceCallback {
        public static final int TRANSACTION_onRegistrationFinished = 1;
        public static final int TRANSACTION_onRegistrationStatus = 3;
        public static final int TRANSACTION_onUnRegistrationFinished = 2;

        class Proxy implements IKnoxPushServiceCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKnoxPushServiceCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
            public void onRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxPushServiceCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxPushServiceResult, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
            public void onRegistrationStatus(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxPushServiceCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxPushServiceResult, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
            public void onUnRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxPushServiceCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxPushServiceResult, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxPushServiceCallback.DESCRIPTOR);
        }

        public static IKnoxPushServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxPushServiceCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKnoxPushServiceCallback)) ? new Proxy(iBinder) : (IKnoxPushServiceCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxPushServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxPushServiceCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                KnoxPushServiceResult knoxPushServiceResult = (KnoxPushServiceResult) parcel.readTypedObject(KnoxPushServiceResult.CREATOR);
                parcel.enforceNoDataAvail();
                onRegistrationFinished(knoxPushServiceResult);
                parcel2.writeNoException();
            } else if (i == 2) {
                KnoxPushServiceResult knoxPushServiceResult2 = (KnoxPushServiceResult) parcel.readTypedObject(KnoxPushServiceResult.CREATOR);
                parcel.enforceNoDataAvail();
                onUnRegistrationFinished(knoxPushServiceResult2);
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                KnoxPushServiceResult knoxPushServiceResult3 = (KnoxPushServiceResult) parcel.readTypedObject(KnoxPushServiceResult.CREATOR);
                parcel.enforceNoDataAvail();
                onRegistrationStatus(knoxPushServiceResult3);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IKnoxPushServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public void onRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException {
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public void onRegistrationStatus(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException {
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public void onUnRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException {
        }
    }
}
