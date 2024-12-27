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

    ParcelFileDescriptor getTraceFileDescriptor(String str, int i, int i2) throws RemoteException;

    public static class Default implements IAppTraceRetriever {
        @Override // android.app.IAppTraceRetriever
        public ParcelFileDescriptor getTraceFileDescriptor(String packageName, int uid, int pid) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAppTraceRetriever {
        static final int TRANSACTION_getTraceFileDescriptor = 1;

        public Stub() {
            attachInterface(this, IAppTraceRetriever.DESCRIPTOR);
        }

        public static IAppTraceRetriever asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAppTraceRetriever.DESCRIPTOR);
            if (iin != null && (iin instanceof IAppTraceRetriever)) {
                return (IAppTraceRetriever) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getTraceFileDescriptor";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IAppTraceRetriever.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAppTraceRetriever.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result = getTraceFileDescriptor(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAppTraceRetriever {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppTraceRetriever.DESCRIPTOR;
            }

            @Override // android.app.IAppTraceRetriever
            public ParcelFileDescriptor getTraceFileDescriptor(String packageName, int uid, int pid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAppTraceRetriever.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result = (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
