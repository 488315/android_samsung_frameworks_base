package android.app;

import android.content.ComponentName;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IInstrumentationWatcher extends IInterface {
    void instrumentationFinished(ComponentName componentName, int i, Bundle bundle)
            throws RemoteException;

    void instrumentationStatus(ComponentName componentName, int i, Bundle bundle)
            throws RemoteException;

    public static class Default implements IInstrumentationWatcher {
        @Override // android.app.IInstrumentationWatcher
        public void instrumentationStatus(ComponentName name, int resultCode, Bundle results)
                throws RemoteException {}

        @Override // android.app.IInstrumentationWatcher
        public void instrumentationFinished(ComponentName name, int resultCode, Bundle results)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IInstrumentationWatcher {
        public static final String DESCRIPTOR = "android.app.IInstrumentationWatcher";
        static final int TRANSACTION_instrumentationFinished = 2;
        static final int TRANSACTION_instrumentationStatus = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInstrumentationWatcher asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IInstrumentationWatcher)) {
                return (IInstrumentationWatcher) iin;
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
                    return "instrumentationStatus";
                case 2:
                    return "instrumentationFinished";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ComponentName _arg0 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg1 = data.readInt();
                    Bundle _arg2 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    instrumentationStatus(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    ComponentName _arg02 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg12 = data.readInt();
                    Bundle _arg22 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    instrumentationFinished(_arg02, _arg12, _arg22);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInstrumentationWatcher {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IInstrumentationWatcher
            public void instrumentationStatus(ComponentName name, int resultCode, Bundle results)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(name, 0);
                    _data.writeInt(resultCode);
                    _data.writeTypedObject(results, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IInstrumentationWatcher
            public void instrumentationFinished(ComponentName name, int resultCode, Bundle results)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(name, 0);
                    _data.writeInt(resultCode);
                    _data.writeTypedObject(results, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
