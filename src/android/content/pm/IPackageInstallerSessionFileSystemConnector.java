package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPackageInstallerSessionFileSystemConnector extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IPackageInstallerSessionFileSystemConnector";

    public static class Default implements IPackageInstallerSessionFileSystemConnector {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSessionFileSystemConnector
        public void writeData(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }
    }

    void writeData(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    public static abstract class Stub extends Binder implements IPackageInstallerSessionFileSystemConnector {
        static final int TRANSACTION_writeData = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IPackageInstallerSessionFileSystemConnector.DESCRIPTOR);
        }

        public static IPackageInstallerSessionFileSystemConnector asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPackageInstallerSessionFileSystemConnector.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPackageInstallerSessionFileSystemConnector)) {
                return (IPackageInstallerSessionFileSystemConnector) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "writeData";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPackageInstallerSessionFileSystemConnector.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPackageInstallerSessionFileSystemConnector.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                long readLong = parcel.readLong();
                long readLong2 = parcel.readLong();
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                parcel.enforceNoDataAvail();
                writeData(readString, readLong, readLong2, parcelFileDescriptor);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IPackageInstallerSessionFileSystemConnector {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPackageInstallerSessionFileSystemConnector.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageInstallerSessionFileSystemConnector
            public void writeData(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPackageInstallerSessionFileSystemConnector.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
