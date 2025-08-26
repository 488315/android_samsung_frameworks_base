package android.media.tv.extension.oad;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IOadUpdateInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.oad.IOadUpdateInterface";

    public static class Default implements IOadUpdateInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.oad.IOadUpdateInterface
        public boolean getOadStatus() throws RemoteException {
            return false;
        }

        @Override // android.media.tv.extension.oad.IOadUpdateInterface
        public int getSoftwareVersion() throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.oad.IOadUpdateInterface
        public void setOadStatus(boolean z) throws RemoteException {
        }

        @Override // android.media.tv.extension.oad.IOadUpdateInterface
        public void startDetect() throws RemoteException {
        }

        @Override // android.media.tv.extension.oad.IOadUpdateInterface
        public void startDownload() throws RemoteException {
        }

        @Override // android.media.tv.extension.oad.IOadUpdateInterface
        public void startScan() throws RemoteException {
        }

        @Override // android.media.tv.extension.oad.IOadUpdateInterface
        public void stopDetect() throws RemoteException {
        }

        @Override // android.media.tv.extension.oad.IOadUpdateInterface
        public void stopDownload() throws RemoteException {
        }

        @Override // android.media.tv.extension.oad.IOadUpdateInterface
        public void stopScan() throws RemoteException {
        }
    }

    boolean getOadStatus() throws RemoteException;

    int getSoftwareVersion() throws RemoteException;

    void setOadStatus(boolean z) throws RemoteException;

    void startDetect() throws RemoteException;

    void startDownload() throws RemoteException;

    void startScan() throws RemoteException;

    void stopDetect() throws RemoteException;

    void stopDownload() throws RemoteException;

    void stopScan() throws RemoteException;

    public static abstract class Stub extends Binder implements IOadUpdateInterface {
        static final int TRANSACTION_getOadStatus = 2;
        static final int TRANSACTION_getSoftwareVersion = 9;
        static final int TRANSACTION_setOadStatus = 1;
        static final int TRANSACTION_startDetect = 5;
        static final int TRANSACTION_startDownload = 7;
        static final int TRANSACTION_startScan = 3;
        static final int TRANSACTION_stopDetect = 6;
        static final int TRANSACTION_stopDownload = 8;
        static final int TRANSACTION_stopScan = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.oad.IOadUpdateInterface");
        }

        public static IOadUpdateInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.oad.IOadUpdateInterface");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOadUpdateInterface)) {
                return (IOadUpdateInterface) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setOadStatus";
                case 2:
                    return "getOadStatus";
                case 3:
                    return "startScan";
                case 4:
                    return "stopScan";
                case 5:
                    return "startDetect";
                case 6:
                    return "stopDetect";
                case 7:
                    return "startDownload";
                case 8:
                    return "stopDownload";
                case 9:
                    return "getSoftwareVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.oad.IOadUpdateInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.oad.IOadUpdateInterface");
                return true;
            }
            switch (i) {
                case 1:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOadStatus(z);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean oadStatus = getOadStatus();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(oadStatus);
                    return true;
                case 3:
                    startScan();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    stopScan();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    startDetect();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    stopDetect();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    startDownload();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    stopDownload();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int softwareVersion = getSoftwareVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(softwareVersion);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IOadUpdateInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.oad.IOadUpdateInterface";
            }

            @Override // android.media.tv.extension.oad.IOadUpdateInterface
            public void setOadStatus(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.oad.IOadUpdateInterface");
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.oad.IOadUpdateInterface
            public boolean getOadStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.oad.IOadUpdateInterface");
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.oad.IOadUpdateInterface
            public void startScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.oad.IOadUpdateInterface");
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.oad.IOadUpdateInterface
            public void stopScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.oad.IOadUpdateInterface");
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.oad.IOadUpdateInterface
            public void startDetect() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.oad.IOadUpdateInterface");
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.oad.IOadUpdateInterface
            public void stopDetect() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.oad.IOadUpdateInterface");
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.oad.IOadUpdateInterface
            public void startDownload() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.oad.IOadUpdateInterface");
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.oad.IOadUpdateInterface
            public void stopDownload() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.oad.IOadUpdateInterface");
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.oad.IOadUpdateInterface
            public int getSoftwareVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.oad.IOadUpdateInterface");
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
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
