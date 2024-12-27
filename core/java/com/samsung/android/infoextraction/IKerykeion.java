package com.samsung.android.infoextraction;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.ThreadedRenderer;

import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;

public interface IKerykeion extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.infoextraction.IKerykeion";

    void addResultRule(int i, String str) throws RemoteException;

    void dismiss() throws RemoteException;

    void restart() throws RemoteException;

    void setInfoExtractionListener(int i, IBinder iBinder) throws RemoteException;

    void show(String str, Rect rect) throws RemoteException;

    void start(int i, KerykeionRequest kerykeionRequest, Rect rect) throws RemoteException;

    void stop(int i) throws RemoteException;

    void training(String str) throws RemoteException;

    public static class Default implements IKerykeion {
        @Override // com.samsung.android.infoextraction.IKerykeion
        public void start(int key, KerykeionRequest kRequest, Rect rect) throws RemoteException {}

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void stop(int key) throws RemoteException {}

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void restart() throws RemoteException {}

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void setInfoExtractionListener(int key, IBinder iBinder) throws RemoteException {}

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void show(String urlStr, Rect rect) throws RemoteException {}

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void dismiss() throws RemoteException {}

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void training(String source) throws RemoteException {}

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void addResultRule(int type, String source) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IKerykeion {
        static final int TRANSACTION_addResultRule = 8;
        static final int TRANSACTION_dismiss = 6;
        static final int TRANSACTION_restart = 3;
        static final int TRANSACTION_setInfoExtractionListener = 4;
        static final int TRANSACTION_show = 5;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 2;
        static final int TRANSACTION_training = 7;

        public Stub() {
            attachInterface(this, IKerykeion.DESCRIPTOR);
        }

        public static IKerykeion asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IKerykeion.DESCRIPTOR);
            if (iin != null && (iin instanceof IKerykeion)) {
                return (IKerykeion) iin;
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
                    return "start";
                case 2:
                    return "stop";
                case 3:
                    return DefaultActionNames.ACTION_RESTART;
                case 4:
                    return "setInfoExtractionListener";
                case 5:
                    return ThreadedRenderer.OVERDRAW_PROPERTY_SHOW;
                case 6:
                    return "dismiss";
                case 7:
                    return "training";
                case 8:
                    return "addResultRule";
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
                data.enforceInterface(IKerykeion.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IKerykeion.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    KerykeionRequest _arg1 =
                            (KerykeionRequest) data.readTypedObject(KerykeionRequest.CREATOR);
                    Rect _arg2 = (Rect) data.readTypedObject(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    start(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    stop(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    restart();
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg03 = data.readInt();
                    IBinder _arg12 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    setInfoExtractionListener(_arg03, _arg12);
                    reply.writeNoException();
                    return true;
                case 5:
                    String _arg04 = data.readString();
                    Rect _arg13 = (Rect) data.readTypedObject(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    show(_arg04, _arg13);
                    reply.writeNoException();
                    return true;
                case 6:
                    dismiss();
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg05 = data.readString();
                    data.enforceNoDataAvail();
                    training(_arg05);
                    reply.writeNoException();
                    return true;
                case 8:
                    int _arg06 = data.readInt();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    addResultRule(_arg06, _arg14);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKerykeion {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKerykeion.DESCRIPTOR;
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void start(int key, KerykeionRequest kRequest, Rect rect)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    _data.writeInt(key);
                    _data.writeTypedObject(kRequest, 0);
                    _data.writeTypedObject(rect, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void stop(int key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    _data.writeInt(key);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void restart() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void setInfoExtractionListener(int key, IBinder iBinder) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    _data.writeInt(key);
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void show(String urlStr, Rect rect) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    _data.writeString(urlStr);
                    _data.writeTypedObject(rect, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void dismiss() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void training(String source) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    _data.writeString(source);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void addResultRule(int type, String source) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(source);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
