package com.sec.rll;

import android.graphics.rendererpolicy.ScpmApiContract;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IExtControlDeviceService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.rll.IExtControlDeviceService";

    public static class Default implements IExtControlDeviceService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.rll.IExtControlDeviceService
        public int getStatus(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.rll.IExtControlDeviceService
        public void setStatus(int i, int i2) throws RemoteException {
        }
    }

    int getStatus(int i) throws RemoteException;

    void setStatus(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IExtControlDeviceService {
        static final int TRANSACTION_getStatus = 2;
        static final int TRANSACTION_setStatus = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IExtControlDeviceService.DESCRIPTOR);
        }

        public static IExtControlDeviceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IExtControlDeviceService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IExtControlDeviceService)) {
                return (IExtControlDeviceService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setStatus";
            }
            if (i != 2) {
                return null;
            }
            return ScpmApiContract.Method.GET_STATUS;
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IExtControlDeviceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IExtControlDeviceService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setStatus(i3, i4);
                parcel2.writeNoException();
            } else if (i == 2) {
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int status = getStatus(i5);
                parcel2.writeNoException();
                parcel2.writeInt(status);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IExtControlDeviceService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExtControlDeviceService.DESCRIPTOR;
            }

            @Override // com.sec.rll.IExtControlDeviceService
            public void setStatus(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExtControlDeviceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.rll.IExtControlDeviceService
            public int getStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExtControlDeviceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
