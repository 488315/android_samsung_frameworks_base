package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAppTraceRetriever extends IInterface {
    public static final String DESCRIPTOR = "android.app.IAppTraceRetriever";

    public static class Default implements IAppTraceRetriever {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IAppTraceRetriever
        public ParcelFileDescriptor getTraceFileDescriptor(String str, int i, int i2) throws RemoteException {
            return null;
        }
    }

    ParcelFileDescriptor getTraceFileDescriptor(String str, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppTraceRetriever {
        static final int TRANSACTION_getTraceFileDescriptor = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAppTraceRetriever.DESCRIPTOR);
        }

        public static IAppTraceRetriever asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAppTraceRetriever.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppTraceRetriever)) {
                return (IAppTraceRetriever) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getTraceFileDescriptor";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAppTraceRetriever.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAppTraceRetriever.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ParcelFileDescriptor traceFileDescriptor = getTraceFileDescriptor(readString, readInt, readInt2);
                parcel2.writeNoException();
                parcel2.writeTypedObject(traceFileDescriptor, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAppTraceRetriever {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppTraceRetriever.DESCRIPTOR;
            }

            @Override // android.app.IAppTraceRetriever
            public ParcelFileDescriptor getTraceFileDescriptor(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppTraceRetriever.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
