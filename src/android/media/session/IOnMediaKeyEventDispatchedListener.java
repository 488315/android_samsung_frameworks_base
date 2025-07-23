package android.media.session;

import android.media.session.MediaSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;

/* loaded from: classes3.dex */
public interface IOnMediaKeyEventDispatchedListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.session.IOnMediaKeyEventDispatchedListener";

    public static class Default implements IOnMediaKeyEventDispatchedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.session.IOnMediaKeyEventDispatchedListener
        public void onMediaKeyEventDispatched(KeyEvent keyEvent, String str, MediaSession.Token token) throws RemoteException {
        }
    }

    void onMediaKeyEventDispatched(KeyEvent keyEvent, String str, MediaSession.Token token) throws RemoteException;

    public static abstract class Stub extends Binder implements IOnMediaKeyEventDispatchedListener {
        static final int TRANSACTION_onMediaKeyEventDispatched = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
        }

        public static IOnMediaKeyEventDispatchedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOnMediaKeyEventDispatchedListener)) {
                return (IOnMediaKeyEventDispatchedListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onMediaKeyEventDispatched";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                String readString = parcel.readString();
                MediaSession.Token token = (MediaSession.Token) parcel.readTypedObject(MediaSession.Token.CREATOR);
                parcel.enforceNoDataAvail();
                onMediaKeyEventDispatched(keyEvent, readString, token);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IOnMediaKeyEventDispatchedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnMediaKeyEventDispatchedListener.DESCRIPTOR;
            }

            @Override // android.media.session.IOnMediaKeyEventDispatchedListener
            public void onMediaKeyEventDispatched(KeyEvent keyEvent, String str, MediaSession.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
