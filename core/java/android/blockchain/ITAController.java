package android.blockchain;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ITAController extends IInterface {
    public static final String DESCRIPTOR = "android.blockchain.ITAController";

    boolean loadTA(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws RemoteException;

    TACommandResponse processTACommand(TACommandRequest tACommandRequest) throws RemoteException;

    void unloadTA() throws RemoteException;

    public static class Default implements ITAController {
        @Override // android.blockchain.ITAController
        public boolean loadTA(ParcelFileDescriptor pfd, long offset, long len) throws RemoteException {
            return false;
        }

        @Override // android.blockchain.ITAController
        public void unloadTA() throws RemoteException {
        }

        @Override // android.blockchain.ITAController
        public TACommandResponse processTACommand(TACommandRequest request) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITAController {
        static final int TRANSACTION_loadTA = 1;
        static final int TRANSACTION_processTACommand = 3;
        static final int TRANSACTION_unloadTA = 2;

        public Stub() {
            attachInterface(this, ITAController.DESCRIPTOR);
        }

        public static ITAController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITAController.DESCRIPTOR);
            if (iin != null && (iin instanceof ITAController)) {
                return (ITAController) iin;
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
                    return "loadTA";
                case 2:
                    return "unloadTA";
                case 3:
                    return "processTACommand";
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
                data.enforceInterface(ITAController.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITAController.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ParcelFileDescriptor _arg0 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long _arg1 = data.readLong();
                    long _arg2 = data.readLong();
                    data.enforceNoDataAvail();
                    boolean _result = loadTA(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    unloadTA();
                    reply.writeNoException();
                    return true;
                case 3:
                    TACommandRequest _arg02 = (TACommandRequest) data.readTypedObject(TACommandRequest.CREATOR);
                    data.enforceNoDataAvail();
                    TACommandResponse _result2 = processTACommand(_arg02);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITAController {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITAController.DESCRIPTOR;
            }

            @Override // android.blockchain.ITAController
            public boolean loadTA(ParcelFileDescriptor pfd, long offset, long len) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITAController.DESCRIPTOR);
                    _data.writeTypedObject(pfd, 0);
                    _data.writeLong(offset);
                    _data.writeLong(len);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.blockchain.ITAController
            public void unloadTA() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITAController.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.blockchain.ITAController
            public TACommandResponse processTACommand(TACommandRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITAController.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    TACommandResponse _result = (TACommandResponse) _reply.readTypedObject(TACommandResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
