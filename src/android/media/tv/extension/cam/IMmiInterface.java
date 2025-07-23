package android.media.tv.extension.cam;

import android.media.tv.extension.cam.IEnterMenuErrorCallback;
import android.media.tv.extension.cam.IMmiSession;
import android.media.tv.extension.cam.IMmiStatusCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMmiInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.IMmiInterface";

    public static class Default implements IMmiInterface {
        @Override // android.media.tv.extension.cam.IMmiInterface
        public void appInfoEnterMenu(int i, IEnterMenuErrorCallback iEnterMenuErrorCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.IMmiInterface
        public IMmiSession openSession(int i, IMmiStatusCallback iMmiStatusCallback) throws RemoteException {
            return null;
        }
    }

    void appInfoEnterMenu(int i, IEnterMenuErrorCallback iEnterMenuErrorCallback) throws RemoteException;

    IMmiSession openSession(int i, IMmiStatusCallback iMmiStatusCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IMmiInterface {
        static final int TRANSACTION_appInfoEnterMenu = 2;
        static final int TRANSACTION_openSession = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.IMmiInterface");
        }

        public static IMmiInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.IMmiInterface");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMmiInterface)) {
                return (IMmiInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "openSession";
            }
            if (i != 2) {
                return null;
            }
            return "appInfoEnterMenu";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.IMmiInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.IMmiInterface");
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                IMmiStatusCallback asInterface = IMmiStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                IMmiSession openSession = openSession(readInt, asInterface);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(openSession);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                IEnterMenuErrorCallback asInterface2 = IEnterMenuErrorCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                appInfoEnterMenu(readInt2, asInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMmiInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.IMmiInterface";
            }

            @Override // android.media.tv.extension.cam.IMmiInterface
            public IMmiSession openSession(int i, IMmiStatusCallback iMmiStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.IMmiInterface");
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iMmiStatusCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return IMmiSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.IMmiInterface
            public void appInfoEnterMenu(int i, IEnterMenuErrorCallback iEnterMenuErrorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.IMmiInterface");
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iEnterMenuErrorCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
