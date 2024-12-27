package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializerHeadTrackingModeCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISpatializerHeadTrackingModeCallback";

    void dispatchSpatializerActualHeadTrackingModeChanged(int i) throws RemoteException;

    void dispatchSpatializerDesiredHeadTrackingModeChanged(int i) throws RemoteException;

    public static class Default implements ISpatializerHeadTrackingModeCallback {
        @Override // android.media.ISpatializerHeadTrackingModeCallback
        public void dispatchSpatializerActualHeadTrackingModeChanged(int mode)
                throws RemoteException {}

        @Override // android.media.ISpatializerHeadTrackingModeCallback
        public void dispatchSpatializerDesiredHeadTrackingModeChanged(int mode)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder
            implements ISpatializerHeadTrackingModeCallback {
        static final int TRANSACTION_dispatchSpatializerActualHeadTrackingModeChanged = 1;
        static final int TRANSACTION_dispatchSpatializerDesiredHeadTrackingModeChanged = 2;

        public Stub() {
            attachInterface(this, ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
        }

        public static ISpatializerHeadTrackingModeCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin =
                    obj.queryLocalInterface(ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISpatializerHeadTrackingModeCallback)) {
                return (ISpatializerHeadTrackingModeCallback) iin;
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
                    return "dispatchSpatializerActualHeadTrackingModeChanged";
                case 2:
                    return "dispatchSpatializerDesiredHeadTrackingModeChanged";
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
                data.enforceInterface(ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    dispatchSpatializerActualHeadTrackingModeChanged(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    dispatchSpatializerDesiredHeadTrackingModeChanged(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISpatializerHeadTrackingModeCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpatializerHeadTrackingModeCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerHeadTrackingModeCallback
            public void dispatchSpatializerActualHeadTrackingModeChanged(int mode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializerHeadTrackingModeCallback
            public void dispatchSpatializerDesiredHeadTrackingModeChanged(int mode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
                    _data.writeInt(mode);
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
