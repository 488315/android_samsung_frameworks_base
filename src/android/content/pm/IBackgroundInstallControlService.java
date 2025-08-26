package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IBackgroundInstallControlService extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IBackgroundInstallControlService";

    public static class Default implements IBackgroundInstallControlService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IBackgroundInstallControlService
        public ParceledListSlice getBackgroundInstalledPackages(long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IBackgroundInstallControlService
        public void registerBackgroundInstallCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // android.content.pm.IBackgroundInstallControlService
        public void unregisterBackgroundInstallCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
        }
    }

    ParceledListSlice getBackgroundInstalledPackages(long j, int i) throws RemoteException;

    void registerBackgroundInstallCallback(IRemoteCallback iRemoteCallback) throws RemoteException;

    void unregisterBackgroundInstallCallback(IRemoteCallback iRemoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IBackgroundInstallControlService {
        static final int TRANSACTION_getBackgroundInstalledPackages = 1;
        static final int TRANSACTION_registerBackgroundInstallCallback = 2;
        static final int TRANSACTION_unregisterBackgroundInstallCallback = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IBackgroundInstallControlService.DESCRIPTOR);
        }

        public static IBackgroundInstallControlService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBackgroundInstallControlService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBackgroundInstallControlService)) {
                return (IBackgroundInstallControlService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getBackgroundInstalledPackages";
            }
            if (i == 2) {
                return "registerBackgroundInstallCallback";
            }
            if (i != 3) {
                return null;
            }
            return "unregisterBackgroundInstallCallback";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBackgroundInstallControlService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBackgroundInstallControlService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long j = parcel.readLong();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ParceledListSlice backgroundInstalledPackages = getBackgroundInstalledPackages(j, i3);
                parcel2.writeNoException();
                parcel2.writeTypedObject(backgroundInstalledPackages, 1);
            } else if (i == 2) {
                IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerBackgroundInstallCallback(iRemoteCallbackAsInterface);
                parcel2.writeNoException();
            } else if (i == 3) {
                IRemoteCallback iRemoteCallbackAsInterface2 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterBackgroundInstallCallback(iRemoteCallbackAsInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBackgroundInstallControlService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBackgroundInstallControlService.DESCRIPTOR;
            }

            @Override // android.content.pm.IBackgroundInstallControlService
            public ParceledListSlice getBackgroundInstalledPackages(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBackgroundInstallControlService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IBackgroundInstallControlService
            public void registerBackgroundInstallCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBackgroundInstallControlService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IBackgroundInstallControlService
            public void unregisterBackgroundInstallCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBackgroundInstallControlService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
