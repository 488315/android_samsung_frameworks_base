package com.samsung.android.knox.downloadablekeystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IDownloadableKeystoreService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.downloadablekeystore.IDownloadableKeystoreService";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IDownloadableKeystoreService {
        public static final int TRANSACTION_startTimaKeystoreServices = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDownloadableKeystoreService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDownloadableKeystoreService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDownloadableKeystoreService)) ? new Proxy(iBinder) : (IDownloadableKeystoreService) queryLocalInterface;
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
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            int startTimaKeystoreServices = startTimaKeystoreServices(readInt);
            parcel2.writeNoException();
            parcel2.writeInt(startTimaKeystoreServices);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
