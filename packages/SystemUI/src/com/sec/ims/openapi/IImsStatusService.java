package com.sec.ims.openapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.IImsRegistrationListener;
import com.sec.ims.volte2.IImsCallEventListener;

/* loaded from: classes4.dex */
public interface IImsStatusService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.openapi.IImsStatusService";

    int[] getCallCount() throws RemoteException;

    void registerImsCallEventListener(IImsCallEventListener iImsCallEventListener) throws RemoteException;

    void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void unregisterImsCallEventListener(IImsCallEventListener iImsCallEventListener) throws RemoteException;

    void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    public abstract class Stub extends Binder implements IImsStatusService {
        static final int TRANSACTION_getCallCount = 5;
        static final int TRANSACTION_registerImsCallEventListener = 3;
        static final int TRANSACTION_registerImsRegistrationListener = 1;
        static final int TRANSACTION_unregisterImsCallEventListener = 4;
        static final int TRANSACTION_unregisterImsRegistrationListener = 2;

        class Proxy implements IImsStatusService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.openapi.IImsStatusService
            public int[] getCallCount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsStatusService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IImsStatusService.DESCRIPTOR;
            }

            @Override // com.sec.ims.openapi.IImsStatusService
            public void registerImsCallEventListener(IImsCallEventListener iImsCallEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsStatusService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IImsStatusService
            public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsStatusService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IImsStatusService
            public void unregisterImsCallEventListener(IImsCallEventListener iImsCallEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsStatusService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IImsStatusService
            public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsStatusService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsStatusService.DESCRIPTOR);
        }

        public static IImsStatusService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsStatusService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IImsStatusService)) ? new Proxy(iBinder) : (IImsStatusService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsStatusService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsStatusService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IImsRegistrationListener iImsRegistrationListenerAsInterface = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerImsRegistrationListener(iImsRegistrationListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IImsRegistrationListener iImsRegistrationListenerAsInterface2 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterImsRegistrationListener(iImsRegistrationListenerAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                IImsCallEventListener iImsCallEventListenerAsInterface = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerImsCallEventListener(iImsCallEventListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 4) {
                IImsCallEventListener iImsCallEventListenerAsInterface2 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterImsCallEventListener(iImsCallEventListenerAsInterface2);
                parcel2.writeNoException();
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int[] callCount = getCallCount();
                parcel2.writeNoException();
                parcel2.writeIntArray(callCount);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IImsStatusService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.openapi.IImsStatusService
        public int[] getCallCount() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.openapi.IImsStatusService
        public void registerImsCallEventListener(IImsCallEventListener iImsCallEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.openapi.IImsStatusService
        public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override // com.sec.ims.openapi.IImsStatusService
        public void unregisterImsCallEventListener(IImsCallEventListener iImsCallEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.openapi.IImsStatusService
        public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }
    }
}
