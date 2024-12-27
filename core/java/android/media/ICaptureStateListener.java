package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICaptureStateListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.ICaptureStateListener";

    void setCaptureState(boolean z) throws RemoteException;

    public static class Default implements ICaptureStateListener {
        @Override // android.media.ICaptureStateListener
        public void setCaptureState(boolean active) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICaptureStateListener {
        static final int TRANSACTION_setCaptureState = 1;

        public Stub() {
            attachInterface(this, ICaptureStateListener.DESCRIPTOR);
        }

        public static ICaptureStateListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICaptureStateListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ICaptureStateListener)) {
                return (ICaptureStateListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICaptureStateListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICaptureStateListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setCaptureState(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICaptureStateListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICaptureStateListener.DESCRIPTOR;
            }

            @Override // android.media.ICaptureStateListener
            public void setCaptureState(boolean active) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICaptureStateListener.DESCRIPTOR);
                    _data.writeBoolean(active);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
