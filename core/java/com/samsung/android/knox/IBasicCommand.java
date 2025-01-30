package com.samsung.android.knox;

import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IBasicCommand extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.IBasicCommand";

    Bundle sendCmd(Bundle bundle) throws RemoteException;

    void setCaller(IBasicCommand iBasicCommand) throws RemoteException;

    public static class Default implements IBasicCommand {
        @Override // com.samsung.android.knox.IBasicCommand
        public Bundle sendCmd(Bundle cmd) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IBasicCommand
        public void setCaller(IBasicCommand caller) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBasicCommand {
        static final int TRANSACTION_sendCmd = 1;
        static final int TRANSACTION_setCaller = 2;

        public Stub() {
            attachInterface(this, IBasicCommand.DESCRIPTOR);
        }

        public static IBasicCommand asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IBasicCommand.DESCRIPTOR);
            if (iin != null && (iin instanceof IBasicCommand)) {
                return (IBasicCommand) iin;
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
                    return "sendCmd";
                case 2:
                    return "setCaller";
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
                data.enforceInterface(IBasicCommand.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IBasicCommand.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            Bundle _arg0 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            data.enforceNoDataAvail();
                            Bundle _result = sendCmd(_arg0);
                            reply.writeNoException();
                            reply.writeTypedObject(_result, 1);
                            return true;
                        case 2:
                            IBasicCommand _arg02 = asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            setCaller(_arg02);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IBasicCommand {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBasicCommand.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.IBasicCommand
            public Bundle sendCmd(Bundle cmd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBasicCommand.DESCRIPTOR);
                    _data.writeTypedObject(cmd, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.IBasicCommand
            public void setCaller(IBasicCommand caller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBasicCommand.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
