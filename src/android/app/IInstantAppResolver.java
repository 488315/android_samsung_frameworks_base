package android.app;

import android.content.pm.InstantAppRequestInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IInstantAppResolver extends IInterface {

    public static class Default implements IInstantAppResolver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IInstantAppResolver
        public void getInstantAppIntentFilterList(InstantAppRequestInfo instantAppRequestInfo, IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // android.app.IInstantAppResolver
        public void getInstantAppResolveInfoList(InstantAppRequestInfo instantAppRequestInfo, int i, IRemoteCallback iRemoteCallback) throws RemoteException {
        }
    }

    void getInstantAppIntentFilterList(InstantAppRequestInfo instantAppRequestInfo, IRemoteCallback iRemoteCallback) throws RemoteException;

    void getInstantAppResolveInfoList(InstantAppRequestInfo instantAppRequestInfo, int i, IRemoteCallback iRemoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IInstantAppResolver {
        public static final String DESCRIPTOR = "android.app.IInstantAppResolver";
        static final int TRANSACTION_getInstantAppIntentFilterList = 2;
        static final int TRANSACTION_getInstantAppResolveInfoList = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInstantAppResolver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInstantAppResolver)) {
                return (IInstantAppResolver) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getInstantAppResolveInfoList";
            }
            if (i != 2) {
                return null;
            }
            return "getInstantAppIntentFilterList";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                InstantAppRequestInfo instantAppRequestInfo = (InstantAppRequestInfo) parcel.readTypedObject(InstantAppRequestInfo.CREATOR);
                int i3 = parcel.readInt();
                IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getInstantAppResolveInfoList(instantAppRequestInfo, i3, iRemoteCallbackAsInterface);
            } else if (i == 2) {
                InstantAppRequestInfo instantAppRequestInfo2 = (InstantAppRequestInfo) parcel.readTypedObject(InstantAppRequestInfo.CREATOR);
                IRemoteCallback iRemoteCallbackAsInterface2 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getInstantAppIntentFilterList(instantAppRequestInfo2, iRemoteCallbackAsInterface2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInstantAppResolver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IInstantAppResolver
            public void getInstantAppResolveInfoList(InstantAppRequestInfo instantAppRequestInfo, int i, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(instantAppRequestInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IInstantAppResolver
            public void getInstantAppIntentFilterList(InstantAppRequestInfo instantAppRequestInfo, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(instantAppRequestInfo, 0);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
