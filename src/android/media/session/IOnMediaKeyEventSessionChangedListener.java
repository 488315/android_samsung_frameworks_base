package android.media.session;

import android.media.session.MediaSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IOnMediaKeyEventSessionChangedListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.session.IOnMediaKeyEventSessionChangedListener";

    public static class Default implements IOnMediaKeyEventSessionChangedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.session.IOnMediaKeyEventSessionChangedListener
        public void onMediaKeyEventSessionChanged(String str, MediaSession.Token token) throws RemoteException {
        }
    }

    void onMediaKeyEventSessionChanged(String str, MediaSession.Token token) throws RemoteException;

    public static abstract class Stub extends Binder implements IOnMediaKeyEventSessionChangedListener {
        static final int TRANSACTION_onMediaKeyEventSessionChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IOnMediaKeyEventSessionChangedListener.DESCRIPTOR);
        }

        public static IOnMediaKeyEventSessionChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOnMediaKeyEventSessionChangedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOnMediaKeyEventSessionChangedListener)) {
                return (IOnMediaKeyEventSessionChangedListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onMediaKeyEventSessionChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOnMediaKeyEventSessionChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOnMediaKeyEventSessionChangedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                MediaSession.Token token = (MediaSession.Token) parcel.readTypedObject(MediaSession.Token.CREATOR);
                parcel.enforceNoDataAvail();
                onMediaKeyEventSessionChanged(readString, token);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IOnMediaKeyEventSessionChangedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnMediaKeyEventSessionChangedListener.DESCRIPTOR;
            }

            @Override // android.media.session.IOnMediaKeyEventSessionChangedListener
            public void onMediaKeyEventSessionChanged(String str, MediaSession.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOnMediaKeyEventSessionChangedListener.DESCRIPTOR);
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
