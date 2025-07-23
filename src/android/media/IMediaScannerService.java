package android.media;

import android.media.IMediaScannerListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IMediaScannerService extends IInterface {

    public static class Default implements IMediaScannerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IMediaScannerService
        public void requestScanFile(String str, String str2, IMediaScannerListener iMediaScannerListener) throws RemoteException {
        }

        @Override // android.media.IMediaScannerService
        public void scanFile(String str, String str2) throws RemoteException {
        }
    }

    void requestScanFile(String str, String str2, IMediaScannerListener iMediaScannerListener) throws RemoteException;

    void scanFile(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaScannerService {
        public static final String DESCRIPTOR = "android.media.IMediaScannerService";
        static final int TRANSACTION_requestScanFile = 1;
        static final int TRANSACTION_scanFile = 2;

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

        public static IMediaScannerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaScannerService)) {
                return (IMediaScannerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "requestScanFile";
            }
            if (i != 2) {
                return null;
            }
            return "scanFile";
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
                String readString2 = parcel.readString();
                IMediaScannerListener asInterface = IMediaScannerListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                requestScanFile(readString, readString2, asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                String readString3 = parcel.readString();
                String readString4 = parcel.readString();
                parcel.enforceNoDataAvail();
                scanFile(readString3, readString4);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMediaScannerService {
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

            @Override // android.media.IMediaScannerService
            public void requestScanFile(String str, String str2, IMediaScannerListener iMediaScannerListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iMediaScannerListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaScannerService
            public void scanFile(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
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
