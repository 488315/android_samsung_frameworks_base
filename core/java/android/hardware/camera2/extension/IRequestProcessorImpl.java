package android.hardware.camera2.extension;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes2.dex */
public interface IRequestProcessorImpl extends IInterface {
    public static final String DESCRIPTOR =
            "android.hardware.camera2.extension.IRequestProcessorImpl";

    void abortCaptures() throws RemoteException;

    void setImageProcessor(OutputConfigId outputConfigId, IImageProcessorImpl iImageProcessorImpl)
            throws RemoteException;

    int setRepeating(Request request, IRequestCallback iRequestCallback) throws RemoteException;

    void stopRepeating() throws RemoteException;

    int submit(Request request, IRequestCallback iRequestCallback) throws RemoteException;

    int submitBurst(List<Request> list, IRequestCallback iRequestCallback) throws RemoteException;

    public static class Default implements IRequestProcessorImpl {
        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void setImageProcessor(
                OutputConfigId outputConfigId, IImageProcessorImpl imageProcessor)
                throws RemoteException {}

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submit(Request request, IRequestCallback callback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submitBurst(List<Request> requests, IRequestCallback callback)
                throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int setRepeating(Request request, IRequestCallback callback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void abortCaptures() throws RemoteException {}

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void stopRepeating() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRequestProcessorImpl {
        static final int TRANSACTION_abortCaptures = 5;
        static final int TRANSACTION_setImageProcessor = 1;
        static final int TRANSACTION_setRepeating = 4;
        static final int TRANSACTION_stopRepeating = 6;
        static final int TRANSACTION_submit = 2;
        static final int TRANSACTION_submitBurst = 3;

        public Stub() {
            attachInterface(this, IRequestProcessorImpl.DESCRIPTOR);
        }

        public static IRequestProcessorImpl asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRequestProcessorImpl.DESCRIPTOR);
            if (iin != null && (iin instanceof IRequestProcessorImpl)) {
                return (IRequestProcessorImpl) iin;
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
                    return "setImageProcessor";
                case 2:
                    return "submit";
                case 3:
                    return "submitBurst";
                case 4:
                    return "setRepeating";
                case 5:
                    return "abortCaptures";
                case 6:
                    return "stopRepeating";
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
                data.enforceInterface(IRequestProcessorImpl.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRequestProcessorImpl.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    OutputConfigId _arg0 =
                            (OutputConfigId) data.readTypedObject(OutputConfigId.CREATOR);
                    IImageProcessorImpl _arg1 =
                            IImageProcessorImpl.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setImageProcessor(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    Request _arg02 = (Request) data.readTypedObject(Request.CREATOR);
                    IRequestCallback _arg12 =
                            IRequestCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result = submit(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 3:
                    List<Request> _arg03 = data.createTypedArrayList(Request.CREATOR);
                    IRequestCallback _arg13 =
                            IRequestCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result2 = submitBurst(_arg03, _arg13);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 4:
                    Request _arg04 = (Request) data.readTypedObject(Request.CREATOR);
                    IRequestCallback _arg14 =
                            IRequestCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result3 = setRepeating(_arg04, _arg14);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 5:
                    abortCaptures();
                    reply.writeNoException();
                    return true;
                case 6:
                    stopRepeating();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRequestProcessorImpl {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRequestProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public void setImageProcessor(
                    OutputConfigId outputConfigId, IImageProcessorImpl imageProcessor)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    _data.writeTypedObject(outputConfigId, 0);
                    _data.writeStrongInterface(imageProcessor);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public int submit(Request request, IRequestCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public int submitBurst(List<Request> requests, IRequestCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    _data.writeTypedList(requests, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public int setRepeating(Request request, IRequestCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public void abortCaptures() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public void stopRepeating() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
