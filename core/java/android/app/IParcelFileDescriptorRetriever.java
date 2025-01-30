package android.app;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IParcelFileDescriptorRetriever extends IInterface {
    public static final String DESCRIPTOR = "android.app.IParcelFileDescriptorRetriever";

    ParcelFileDescriptor getPfd() throws RemoteException;

    public static class Default implements IParcelFileDescriptorRetriever {
        @Override // android.app.IParcelFileDescriptorRetriever
        public ParcelFileDescriptor getPfd() throws RemoteException {
            return null;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IParcelFileDescriptorRetriever {
        static final int TRANSACTION_getPfd = 1;

        public Stub() {
            attachInterface(this, IParcelFileDescriptorRetriever.DESCRIPTOR);
        }

        public static IParcelFileDescriptorRetriever asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IParcelFileDescriptorRetriever.DESCRIPTOR);
            if (iin != null && (iin instanceof IParcelFileDescriptorRetriever)) {
                return (IParcelFileDescriptorRetriever) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getPfd";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IParcelFileDescriptorRetriever.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IParcelFileDescriptorRetriever.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            ParcelFileDescriptor _result = getPfd();
                            reply.writeNoException();
                            reply.writeTypedObject(_result, 1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IParcelFileDescriptorRetriever {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IParcelFileDescriptorRetriever.DESCRIPTOR;
            }

            @Override // android.app.IParcelFileDescriptorRetriever
            public ParcelFileDescriptor getPfd() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IParcelFileDescriptorRetriever.DESCRIPTOR);
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

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
