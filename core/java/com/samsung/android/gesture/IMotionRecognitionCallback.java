package com.samsung.android.gesture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IMotionRecognitionCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.gesture.IMotionRecognitionCallback";

    String getListenerInfo() throws RemoteException;

    String getListenerPackageName() throws RemoteException;

    void motionCallback(SemMotionRecognitionEvent semMotionRecognitionEvent) throws RemoteException;

    public static class Default implements IMotionRecognitionCallback {
        @Override // com.samsung.android.gesture.IMotionRecognitionCallback
        public void motionCallback(SemMotionRecognitionEvent motionEvent) throws RemoteException {
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionCallback
        public String getListenerInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionCallback
        public String getListenerPackageName() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMotionRecognitionCallback {
        static final int TRANSACTION_getListenerInfo = 2;
        static final int TRANSACTION_getListenerPackageName = 3;
        static final int TRANSACTION_motionCallback = 1;

        public Stub() {
            attachInterface(this, IMotionRecognitionCallback.DESCRIPTOR);
        }

        public static IMotionRecognitionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMotionRecognitionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IMotionRecognitionCallback)) {
                return (IMotionRecognitionCallback) iin;
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
                    return "motionCallback";
                case 2:
                    return "getListenerInfo";
                case 3:
                    return "getListenerPackageName";
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
                data.enforceInterface(IMotionRecognitionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMotionRecognitionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SemMotionRecognitionEvent _arg0 = (SemMotionRecognitionEvent) data.readTypedObject(SemMotionRecognitionEvent.CREATOR);
                    data.enforceNoDataAvail();
                    motionCallback(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    String _result = getListenerInfo();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 3:
                    String _result2 = getListenerPackageName();
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMotionRecognitionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMotionRecognitionCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionCallback
            public void motionCallback(SemMotionRecognitionEvent motionEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionCallback.DESCRIPTOR);
                    _data.writeTypedObject(motionEvent, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionCallback
            public String getListenerInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionCallback
            public String getListenerPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionCallback.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
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
