package android.media.projection;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IMediaProjectionCallback extends IInterface {
    void onCapturedContentResize(int i, int i2) throws RemoteException;

    void onCapturedContentVisibilityChanged(boolean z) throws RemoteException;

    void onStop() throws RemoteException;

    public static class Default implements IMediaProjectionCallback {
        @Override // android.media.projection.IMediaProjectionCallback
        public void onStop() throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentResize(int width, int height) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentVisibilityChanged(boolean isVisible) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMediaProjectionCallback {
        public static final String DESCRIPTOR = "android.media.projection.IMediaProjectionCallback";
        static final int TRANSACTION_onCapturedContentResize = 2;
        static final int TRANSACTION_onCapturedContentVisibilityChanged = 3;
        static final int TRANSACTION_onStop = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaProjectionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMediaProjectionCallback)) {
                return (IMediaProjectionCallback) iin;
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
                    return "onStop";
                case 2:
                    return "onCapturedContentResize";
                case 3:
                    return "onCapturedContentVisibilityChanged";
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
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onStop();
                            return true;
                        case 2:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            onCapturedContentResize(_arg0, _arg1);
                            return true;
                        case 3:
                            boolean _arg02 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onCapturedContentVisibilityChanged(_arg02);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IMediaProjectionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.projection.IMediaProjectionCallback
            public void onStop() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionCallback
            public void onCapturedContentResize(int width, int height) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionCallback
            public void onCapturedContentVisibilityChanged(boolean isVisible) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isVisible);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
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
