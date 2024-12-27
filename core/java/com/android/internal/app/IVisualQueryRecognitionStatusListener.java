package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IVisualQueryRecognitionStatusListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IVisualQueryRecognitionStatusListener";

    void onStartPerceiving() throws RemoteException;

    void onStopPerceiving() throws RemoteException;

    public static class Default implements IVisualQueryRecognitionStatusListener {
        @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
        public void onStartPerceiving() throws RemoteException {
        }

        @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
        public void onStopPerceiving() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVisualQueryRecognitionStatusListener {
        static final int TRANSACTION_onStartPerceiving = 1;
        static final int TRANSACTION_onStopPerceiving = 2;

        public Stub() {
            attachInterface(this, IVisualQueryRecognitionStatusListener.DESCRIPTOR);
        }

        public static IVisualQueryRecognitionStatusListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVisualQueryRecognitionStatusListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IVisualQueryRecognitionStatusListener)) {
                return (IVisualQueryRecognitionStatusListener) iin;
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
                    return "onStartPerceiving";
                case 2:
                    return "onStopPerceiving";
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
                data.enforceInterface(IVisualQueryRecognitionStatusListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVisualQueryRecognitionStatusListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onStartPerceiving();
                    return true;
                case 2:
                    onStopPerceiving();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVisualQueryRecognitionStatusListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVisualQueryRecognitionStatusListener.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
            public void onStartPerceiving() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVisualQueryRecognitionStatusListener.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
            public void onStopPerceiving() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVisualQueryRecognitionStatusListener.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
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
