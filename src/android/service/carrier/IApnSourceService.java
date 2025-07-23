package android.service.carrier;

import android.content.ContentValues;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IApnSourceService extends IInterface {
    public static final String DESCRIPTOR = "android.service.carrier.IApnSourceService";

    public static class Default implements IApnSourceService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.carrier.IApnSourceService
        public ContentValues[] getApns(int i) throws RemoteException {
            return null;
        }
    }

    ContentValues[] getApns(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IApnSourceService {
        static final int TRANSACTION_getApns = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IApnSourceService.DESCRIPTOR);
        }

        public static IApnSourceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IApnSourceService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IApnSourceService)) {
                return (IApnSourceService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getApns";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IApnSourceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IApnSourceService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                ContentValues[] apns = getApns(readInt);
                parcel2.writeNoException();
                parcel2.writeTypedArray(apns, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IApnSourceService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IApnSourceService.DESCRIPTOR;
            }

            @Override // android.service.carrier.IApnSourceService
            public ContentValues[] getApns(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApnSourceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContentValues[]) obtain2.createTypedArray(ContentValues.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
