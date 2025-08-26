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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMediaContainerService)) {
                return (IMediaContainerService) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                IParcelFileDescriptorFactory iParcelFileDescriptorFactoryAsInterface = IParcelFileDescriptorFactory.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int iCopyPackage = copyPackage(string, iParcelFileDescriptorFactoryAsInterface);
                parcel2.writeNoException();
                parcel2.writeInt(iCopyPackage);
            } else if (i == 2) {
                String string2 = parcel.readString();
                int i3 = parcel.readInt();
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                PackageInfoLite minimalPackageInfo = getMinimalPackageInfo(string2, i3, string3);
                parcel2.writeNoException();
                parcel2.writeTypedObject(minimalPackageInfo, 1);
            } else if (i == 3) {
                String string4 = parcel.readString();
                parcel.enforceNoDataAvail();
                ObbInfo obbInfo = getObbInfo(string4);
                parcel2.writeNoException();
                parcel2.writeTypedObject(obbInfo, 1);
            } else if (i == 4) {
                String string5 = parcel.readString();
                String string6 = parcel.readString();
                parcel.enforceNoDataAvail();
                long jCalculateInstalledSize = calculateInstalledSize(string5, string6);
                parcel2.writeNoException();
                parcel2.writeLong(jCalculateInstalledSize);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iParcelFileDescriptorFactory);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IMediaContainerService
            public PackageInfoLite getMinimalPackageInfo(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PackageInfoLite) parcelObtain2.readTypedObject(PackageInfoLite.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IMediaContainerService
            public ObbInfo getObbInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ObbInfo) parcelObtain2.readTypedObject(ObbInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IMediaContainerService
            public long calculateInstalledSize(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
