package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IScreenRecordingCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.IScreenRecordingCallback";

    void onScreenRecordingStateChanged(boolean z) throws RemoteException;

    public static class Default implements IScreenRecordingCallback {
        @Override // android.window.IScreenRecordingCallback
        public void onScreenRecordingStateChanged(boolean visibleInScreenRecording) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IScreenRecordingCallback {
        static final int TRANSACTION_onScreenRecordingStateChanged = 1;

        public Stub() {
            attachInterface(this, IScreenRecordingCallback.DESCRIPTOR);
        }

        public static IScreenRecordingCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IScreenRecordingCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IScreenRecordingCallback)) {
                return (IScreenRecordingCallback) iin;
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
                    return "onScreenRecordingStateChanged";
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
                data.enforceInterface(IScreenRecordingCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IScreenRecordingCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onScreenRecordingStateChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IScreenRecordingCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IScreenRecordingCallback.DESCRIPTOR;
            }

            @Override // android.window.IScreenRecordingCallback
            public void onScreenRecordingStateChanged(boolean visibleInScreenRecording) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IScreenRecordingCallback.DESCRIPTOR);
                    _data.writeBoolean(visibleInScreenRecording);
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
