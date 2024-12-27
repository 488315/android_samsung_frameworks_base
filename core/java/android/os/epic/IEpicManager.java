package android.os.epic;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.epic.IEpicObject;

/* loaded from: classes3.dex */
public interface IEpicManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.epic.IEpicManager";

    IEpicObject Create(int i) throws RemoteException;

    IEpicObject Creates(int[] iArr) throws RemoteException;

    public static class Default implements IEpicManager {
        @Override // android.os.epic.IEpicManager
        public IEpicObject Create(int scenario_id) throws RemoteException {
            return null;
        }

        @Override // android.os.epic.IEpicManager
        public IEpicObject Creates(int[] scenario_id_list) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IEpicManager {
        static final int TRANSACTION_Create = 1;
        static final int TRANSACTION_Creates = 2;

        public Stub() {
            attachInterface(this, IEpicManager.DESCRIPTOR);
        }

        public static IEpicManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IEpicManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IEpicManager)) {
                return (IEpicManager) iin;
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
                    return "Create";
                case 2:
                    return "Creates";
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
                data.enforceInterface(IEpicManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IEpicManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    IEpicObject _result = Create(_arg0);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result);
                    return true;
                case 2:
                    int[] _arg02 = data.createIntArray();
                    data.enforceNoDataAvail();
                    IEpicObject _result2 = Creates(_arg02);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IEpicManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEpicManager.DESCRIPTOR;
            }

            @Override // android.os.epic.IEpicManager
            public IEpicObject Create(int scenario_id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEpicManager.DESCRIPTOR);
                    _data.writeInt(scenario_id);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    IEpicObject _result = IEpicObject.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.epic.IEpicManager
            public IEpicObject Creates(int[] scenario_id_list) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEpicManager.DESCRIPTOR);
                    _data.writeIntArray(scenario_id_list);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    IEpicObject _result = IEpicObject.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
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
