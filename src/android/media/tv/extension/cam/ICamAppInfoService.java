package android.media.tv.extension.cam;

import android.media.tv.extension.cam.ICamAppInfoListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICamAppInfoService extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.ICamAppInfoService";

    public static class Default implements ICamAppInfoService {
        @Override // android.media.tv.extension.cam.ICamAppInfoService
        public void addCamAppInfoListener(ICamAppInfoListener iCamAppInfoListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamAppInfoService
        public int getCamAppInfo(int i, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.cam.ICamAppInfoService
        public void removeCamAppInfoListener(ICamAppInfoListener iCamAppInfoListener) throws RemoteException {
        }
    }

    void addCamAppInfoListener(ICamAppInfoListener iCamAppInfoListener) throws RemoteException;

    int getCamAppInfo(int i, Bundle bundle) throws RemoteException;

    void removeCamAppInfoListener(ICamAppInfoListener iCamAppInfoListener) throws RemoteException;

    public static abstract class Stub extends Binder implements ICamAppInfoService {
        static final int TRANSACTION_addCamAppInfoListener = 1;
        static final int TRANSACTION_getCamAppInfo = 3;
        static final int TRANSACTION_removeCamAppInfoListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.ICamAppInfoService");
        }

        public static ICamAppInfoService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamAppInfoService");
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICamAppInfoService)) {
                return (ICamAppInfoService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addCamAppInfoListener";
            }
            if (i == 2) {
                return "removeCamAppInfoListener";
            }
            if (i != 3) {
                return null;
            }
            return "getCamAppInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.ICamAppInfoService");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.ICamAppInfoService");
                return true;
            }
            if (i == 1) {
                ICamAppInfoListener asInterface = ICamAppInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addCamAppInfoListener(asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                ICamAppInfoListener asInterface2 = ICamAppInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeCamAppInfoListener(asInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt = parcel.readInt();
                Bundle bundle = new Bundle();
                parcel.enforceNoDataAvail();
                int camAppInfo = getCamAppInfo(readInt, bundle);
                parcel2.writeNoException();
                parcel2.writeInt(camAppInfo);
                parcel2.writeTypedObject(bundle, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICamAppInfoService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.ICamAppInfoService";
            }

            @Override // android.media.tv.extension.cam.ICamAppInfoService
            public void addCamAppInfoListener(ICamAppInfoListener iCamAppInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamAppInfoService");
                    obtain.writeStrongInterface(iCamAppInfoListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamAppInfoService
            public void removeCamAppInfoListener(ICamAppInfoListener iCamAppInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamAppInfoService");
                    obtain.writeStrongInterface(iCamAppInfoListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamAppInfoService
            public int getCamAppInfo(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamAppInfoService");
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
