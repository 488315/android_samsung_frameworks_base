package com.samsung.android.media.codec;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import com.samsung.android.media.codec.IVideoTranscodingServiceCallback;

/* loaded from: classes5.dex */
public interface IVideoTranscodingService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.media.codec.IVideoTranscodingService";

    String register(int i, IVideoTranscodingServiceCallback iVideoTranscodingServiceCallback) throws RemoteException;

    void startTask(String str) throws RemoteException;

    void stopTask(String str) throws RemoteException;

    public static class Default implements IVideoTranscodingService {
        @Override // com.samsung.android.media.codec.IVideoTranscodingService
        public String register(int mode, IVideoTranscodingServiceCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingService
        public void startTask(String id) throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingService
        public void stopTask(String id) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVideoTranscodingService {
        static final int TRANSACTION_register = 1;
        static final int TRANSACTION_startTask = 2;
        static final int TRANSACTION_stopTask = 3;

        public Stub() {
            attachInterface(this, IVideoTranscodingService.DESCRIPTOR);
        }

        public static IVideoTranscodingService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVideoTranscodingService.DESCRIPTOR);
            if (iin != null && (iin instanceof IVideoTranscodingService)) {
                return (IVideoTranscodingService) iin;
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
                    return "register";
                case 2:
                    return "startTask";
                case 3:
                    return "stopTask";
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
                data.enforceInterface(IVideoTranscodingService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IVideoTranscodingService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            IVideoTranscodingServiceCallback _arg1 = IVideoTranscodingServiceCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            String _result = register(_arg0, _arg1);
                            reply.writeNoException();
                            reply.writeString(_result);
                            return true;
                        case 2:
                            String _arg02 = data.readString();
                            data.enforceNoDataAvail();
                            startTask(_arg02);
                            reply.writeNoException();
                            return true;
                        case 3:
                            String _arg03 = data.readString();
                            data.enforceNoDataAvail();
                            stopTask(_arg03);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IVideoTranscodingService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVideoTranscodingService.DESCRIPTOR;
            }

            @Override // com.samsung.android.media.codec.IVideoTranscodingService
            public String register(int mode, IVideoTranscodingServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVideoTranscodingService.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.codec.IVideoTranscodingService
            public void startTask(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVideoTranscodingService.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.codec.IVideoTranscodingService
            public void stopTask(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVideoTranscodingService.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
