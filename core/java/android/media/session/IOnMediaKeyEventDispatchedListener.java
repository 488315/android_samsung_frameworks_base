package android.media.session;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;

public interface IOnMediaKeyEventDispatchedListener extends IInterface {
    public static final String DESCRIPTOR =
            "android.media.session.IOnMediaKeyEventDispatchedListener";

    void onMediaKeyEventDispatched(KeyEvent keyEvent, String str, MediaSession.Token token)
            throws RemoteException;

    public static class Default implements IOnMediaKeyEventDispatchedListener {
        @Override // android.media.session.IOnMediaKeyEventDispatchedListener
        public void onMediaKeyEventDispatched(
                KeyEvent event, String packageName, MediaSession.Token sessionToken)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IOnMediaKeyEventDispatchedListener {
        static final int TRANSACTION_onMediaKeyEventDispatched = 1;

        public Stub() {
            attachInterface(this, IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
        }

        public static IOnMediaKeyEventDispatchedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IOnMediaKeyEventDispatchedListener)) {
                return (IOnMediaKeyEventDispatchedListener) iin;
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
                    return "onMediaKeyEventDispatched";
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
                data.enforceInterface(IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    KeyEvent _arg0 = (KeyEvent) data.readTypedObject(KeyEvent.CREATOR);
                    String _arg1 = data.readString();
                    MediaSession.Token _arg2 =
                            (MediaSession.Token) data.readTypedObject(MediaSession.Token.CREATOR);
                    data.enforceNoDataAvail();
                    onMediaKeyEventDispatched(_arg0, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOnMediaKeyEventDispatchedListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnMediaKeyEventDispatchedListener.DESCRIPTOR;
            }

            @Override // android.media.session.IOnMediaKeyEventDispatchedListener
            public void onMediaKeyEventDispatched(
                    KeyEvent event, String packageName, MediaSession.Token sessionToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    _data.writeString(packageName);
                    _data.writeTypedObject(sessionToken, 0);
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
