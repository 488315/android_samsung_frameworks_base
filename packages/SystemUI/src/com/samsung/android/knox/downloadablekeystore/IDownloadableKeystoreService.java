package com.samsung.android.knox.downloadablekeystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IDownloadableKeystoreService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.downloadablekeystore.IDownloadableKeystoreService";

    public class Default implements IDownloadableKeystoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.downloadablekeystore.IDownloadableKeystoreService
        public int startTimaKeystoreServices(int i) throws RemoteException {
            return 0;
        }
    }

    int startTimaKeystoreServices(int i) throws RemoteException;

    public abstract class Stub extends Binder implements IDownloadableKeystoreService {
        public static final int TRANSACTION_startTimaKeystoreServices = 1;

        class Proxy implements IDownloadableKeystoreService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDownloadableKeystoreService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.downloadablekeystore.IDownloadableKeystoreService
            public int startTimaKeystoreServices(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDownloadableKeystoreService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDownloadableKeystoreService.DESCRIPTOR);
        }

        public static IDownloadableKeystoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDownloadableKeystoreService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IDownloadableKeystoreService)) ? new Proxy(iBinder) : (IDownloadableKeystoreService) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "startTimaKeystoreServices";
        }

        public int getMaxTransactionId() {
            return 0;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDownloadableKeystoreService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDownloadableKeystoreService.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iStartTimaKeystoreServices = startTimaKeystoreServices(i3);
            parcel2.writeNoException();
            parcel2.writeInt(iStartTimaKeystoreServices);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
