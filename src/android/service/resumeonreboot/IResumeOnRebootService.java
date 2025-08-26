package android.service.resumeonreboot;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IResumeOnRebootService extends IInterface {
    public static final String DESCRIPTOR = "android.service.resumeonreboot.IResumeOnRebootService";

    public static class Default implements IResumeOnRebootService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.resumeonreboot.IResumeOnRebootService
        public void unwrap(byte[] bArr, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.resumeonreboot.IResumeOnRebootService
        public void wrapSecret(byte[] bArr, long j, RemoteCallback remoteCallback) throws RemoteException {
        }
    }

    void unwrap(byte[] bArr, RemoteCallback remoteCallback) throws RemoteException;

    void wrapSecret(byte[] bArr, long j, RemoteCallback remoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IResumeOnRebootService {
        static final int TRANSACTION_unwrap = 2;
        static final int TRANSACTION_wrapSecret = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IResumeOnRebootService.DESCRIPTOR);
        }

        public static IResumeOnRebootService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IResumeOnRebootService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IResumeOnRebootService)) {
                return (IResumeOnRebootService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "wrapSecret";
            }
            if (i != 2) {
                return null;
            }
            return "unwrap";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IResumeOnRebootService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IResumeOnRebootService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                byte[] bArrCreateByteArray = parcel.createByteArray();
                long j = parcel.readLong();
                RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                wrapSecret(bArrCreateByteArray, j, remoteCallback);
            } else if (i == 2) {
                byte[] bArrCreateByteArray2 = parcel.createByteArray();
                RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                unwrap(bArrCreateByteArray2, remoteCallback2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IResumeOnRebootService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResumeOnRebootService.DESCRIPTOR;
            }

            @Override // android.service.resumeonreboot.IResumeOnRebootService
            public void wrapSecret(byte[] bArr, long j, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IResumeOnRebootService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.resumeonreboot.IResumeOnRebootService
            public void unwrap(byte[] bArr, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IResumeOnRebootService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
