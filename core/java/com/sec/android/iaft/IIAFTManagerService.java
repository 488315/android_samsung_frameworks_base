package com.sec.android.iaft;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.sec.android.iaft.callback.IIAFTCallback;

/* loaded from: classes6.dex */
public interface IIAFTManagerService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.android.iaft.IIAFTManagerService";

    void registerCallback(IIAFTCallback iIAFTCallback) throws RemoteException;

    void startAtrace() throws RemoteException;

    void startAtraceAndAnalyze(int i, String str, int i2) throws RemoteException;

    void stopTrace() throws RemoteException;

    boolean traceLogSupported() throws RemoteException;

    public static class Default implements IIAFTManagerService {
        @Override // com.sec.android.iaft.IIAFTManagerService
        public void startAtraceAndAnalyze(int pid, String packageName, int policy)
                throws RemoteException {}

        @Override // com.sec.android.iaft.IIAFTManagerService
        public void startAtrace() throws RemoteException {}

        @Override // com.sec.android.iaft.IIAFTManagerService
        public void stopTrace() throws RemoteException {}

        @Override // com.sec.android.iaft.IIAFTManagerService
        public void registerCallback(IIAFTCallback callback) throws RemoteException {}

        @Override // com.sec.android.iaft.IIAFTManagerService
        public boolean traceLogSupported() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IIAFTManagerService {
        static final int TRANSACTION_registerCallback = 4;
        static final int TRANSACTION_startAtrace = 2;
        static final int TRANSACTION_startAtraceAndAnalyze = 1;
        static final int TRANSACTION_stopTrace = 3;
        static final int TRANSACTION_traceLogSupported = 5;

        public Stub() {
            attachInterface(this, IIAFTManagerService.DESCRIPTOR);
        }

        public static IIAFTManagerService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIAFTManagerService.DESCRIPTOR);
            if (iin != null && (iin instanceof IIAFTManagerService)) {
                return (IIAFTManagerService) iin;
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
                    return "startAtraceAndAnalyze";
                case 2:
                    return "startAtrace";
                case 3:
                    return "stopTrace";
                case 4:
                    return "registerCallback";
                case 5:
                    return "traceLogSupported";
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
                data.enforceInterface(IIAFTManagerService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IIAFTManagerService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    startAtraceAndAnalyze(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    startAtrace();
                    reply.writeNoException();
                    return true;
                case 3:
                    stopTrace();
                    reply.writeNoException();
                    return true;
                case 4:
                    IIAFTCallback _arg02 = IIAFTCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerCallback(_arg02);
                    reply.writeNoException();
                    return true;
                case 5:
                    boolean _result = traceLogSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IIAFTManagerService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIAFTManagerService.DESCRIPTOR;
            }

            @Override // com.sec.android.iaft.IIAFTManagerService
            public void startAtraceAndAnalyze(int pid, String packageName, int policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIAFTManagerService.DESCRIPTOR);
                    _data.writeInt(pid);
                    _data.writeString(packageName);
                    _data.writeInt(policy);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.iaft.IIAFTManagerService
            public void startAtrace() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIAFTManagerService.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.iaft.IIAFTManagerService
            public void stopTrace() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIAFTManagerService.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.iaft.IIAFTManagerService
            public void registerCallback(IIAFTCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIAFTManagerService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.iaft.IIAFTManagerService
            public boolean traceLogSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIAFTManagerService.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
