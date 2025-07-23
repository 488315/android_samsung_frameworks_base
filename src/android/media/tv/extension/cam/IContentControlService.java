package android.media.tv.extension.cam;

import android.media.tv.extension.cam.ICamDrmInfoListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IContentControlService extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.IContentControlService";

    public static class Default implements IContentControlService {
        @Override // android.media.tv.extension.cam.IContentControlService
        public void addCamDrmInfoListener(ICamDrmInfoListener iCamDrmInfoListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.IContentControlService
        public int getCamDrmInfo(int i, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.cam.IContentControlService
        public void removeCamDrmInfoListener(ICamDrmInfoListener iCamDrmInfoListener) throws RemoteException {
        }
    }

    void addCamDrmInfoListener(ICamDrmInfoListener iCamDrmInfoListener) throws RemoteException;

    int getCamDrmInfo(int i, Bundle bundle) throws RemoteException;

    void removeCamDrmInfoListener(ICamDrmInfoListener iCamDrmInfoListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentControlService {
        static final int TRANSACTION_addCamDrmInfoListener = 1;
        static final int TRANSACTION_getCamDrmInfo = 3;
        static final int TRANSACTION_removeCamDrmInfoListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.IContentControlService");
        }

        public static IContentControlService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.IContentControlService");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContentControlService)) {
                return (IContentControlService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addCamDrmInfoListener";
            }
            if (i == 2) {
                return "removeCamDrmInfoListener";
            }
            if (i != 3) {
                return null;
            }
            return "getCamDrmInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.IContentControlService");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.IContentControlService");
                return true;
            }
            if (i == 1) {
                ICamDrmInfoListener asInterface = ICamDrmInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addCamDrmInfoListener(asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                ICamDrmInfoListener asInterface2 = ICamDrmInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeCamDrmInfoListener(asInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt = parcel.readInt();
                Bundle bundle = new Bundle();
                parcel.enforceNoDataAvail();
                int camDrmInfo = getCamDrmInfo(readInt, bundle);
                parcel2.writeNoException();
                parcel2.writeInt(camDrmInfo);
                parcel2.writeTypedObject(bundle, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IContentControlService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.IContentControlService";
            }

            @Override // android.media.tv.extension.cam.IContentControlService
            public void addCamDrmInfoListener(ICamDrmInfoListener iCamDrmInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.IContentControlService");
                    obtain.writeStrongInterface(iCamDrmInfoListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.IContentControlService
            public void removeCamDrmInfoListener(ICamDrmInfoListener iCamDrmInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.IContentControlService");
                    obtain.writeStrongInterface(iCamDrmInfoListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.IContentControlService
            public int getCamDrmInfo(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.IContentControlService");
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
