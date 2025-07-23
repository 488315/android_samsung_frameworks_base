package com.android.internal.app;

import android.content.pm.PackageInfoLite;
import android.content.res.ObbInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.os.IParcelFileDescriptorFactory;

/* loaded from: classes5.dex */
public interface IMediaContainerService extends IInterface {

    public static class Default implements IMediaContainerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IMediaContainerService
        public long calculateInstalledSize(String str, String str2) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IMediaContainerService
        public int copyPackage(String str, IParcelFileDescriptorFactory iParcelFileDescriptorFactory) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IMediaContainerService
        public PackageInfoLite getMinimalPackageInfo(String str, int i, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IMediaContainerService
        public ObbInfo getObbInfo(String str) throws RemoteException {
            return null;
        }
    }

    long calculateInstalledSize(String str, String str2) throws RemoteException;

    int copyPackage(String str, IParcelFileDescriptorFactory iParcelFileDescriptorFactory) throws RemoteException;

    PackageInfoLite getMinimalPackageInfo(String str, int i, String str2) throws RemoteException;

    ObbInfo getObbInfo(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaContainerService {
        public static final String DESCRIPTOR = "com.android.internal.app.IMediaContainerService";
        static final int TRANSACTION_calculateInstalledSize = 4;
        static final int TRANSACTION_copyPackage = 1;
        static final int TRANSACTION_getMinimalPackageInfo = 2;
        static final int TRANSACTION_getObbInfo = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaContainerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaContainerService)) {
                return (IMediaContainerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "copyPackage";
            }
            if (i == 2) {
                return "getMinimalPackageInfo";
            }
            if (i == 3) {
                return "getObbInfo";
            }
            if (i != 4) {
                return null;
            }
            return "calculateInstalledSize";
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
                String readString = parcel.readString();
                IParcelFileDescriptorFactory asInterface = IParcelFileDescriptorFactory.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int copyPackage = copyPackage(readString, asInterface);
                parcel2.writeNoException();
                parcel2.writeInt(copyPackage);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                int readInt = parcel.readInt();
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                PackageInfoLite minimalPackageInfo = getMinimalPackageInfo(readString2, readInt, readString3);
                parcel2.writeNoException();
                parcel2.writeTypedObject(minimalPackageInfo, 1);
            } else if (i == 3) {
                String readString4 = parcel.readString();
                parcel.enforceNoDataAvail();
                ObbInfo obbInfo = getObbInfo(readString4);
                parcel2.writeNoException();
                parcel2.writeTypedObject(obbInfo, 1);
            } else if (i == 4) {
                String readString5 = parcel.readString();
                String readString6 = parcel.readString();
                parcel.enforceNoDataAvail();
                long calculateInstalledSize = calculateInstalledSize(readString5, readString6);
                parcel2.writeNoException();
                parcel2.writeLong(calculateInstalledSize);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMediaContainerService {
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

            @Override // com.android.internal.app.IMediaContainerService
            public int copyPackage(String str, IParcelFileDescriptorFactory iParcelFileDescriptorFactory) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iParcelFileDescriptorFactory);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IMediaContainerService
            public PackageInfoLite getMinimalPackageInfo(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PackageInfoLite) obtain2.readTypedObject(PackageInfoLite.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IMediaContainerService
            public ObbInfo getObbInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ObbInfo) obtain2.readTypedObject(ObbInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IMediaContainerService
            public long calculateInstalledSize(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
