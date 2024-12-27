package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ILoudnessCodecUpdatesDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.ILoudnessCodecUpdatesDispatcher";

    void dispatchLoudnessCodecParameterChange(int i, PersistableBundle persistableBundle)
            throws RemoteException;

    public static class Default implements ILoudnessCodecUpdatesDispatcher {
        @Override // android.media.ILoudnessCodecUpdatesDispatcher
        public void dispatchLoudnessCodecParameterChange(int sessionId, PersistableBundle params)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ILoudnessCodecUpdatesDispatcher {
        static final int TRANSACTION_dispatchLoudnessCodecParameterChange = 1;

        public Stub() {
            attachInterface(this, ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
        }

        public static ILoudnessCodecUpdatesDispatcher asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
            if (iin != null && (iin instanceof ILoudnessCodecUpdatesDispatcher)) {
                return (ILoudnessCodecUpdatesDispatcher) iin;
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
                    return "dispatchLoudnessCodecParameterChange";
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
                data.enforceInterface(ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    PersistableBundle _arg1 =
                            (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    dispatchLoudnessCodecParameterChange(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILoudnessCodecUpdatesDispatcher {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILoudnessCodecUpdatesDispatcher.DESCRIPTOR;
            }

            @Override // android.media.ILoudnessCodecUpdatesDispatcher
            public void dispatchLoudnessCodecParameterChange(
                    int sessionId, PersistableBundle params) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(params, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
