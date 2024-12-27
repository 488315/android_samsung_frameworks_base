package android.app.wallpapereffectsgeneration;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ICinematicEffectListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.wallpapereffectsgeneration.ICinematicEffectListener";

    void onCinematicEffectGenerated(CinematicEffectResponse cinematicEffectResponse) throws RemoteException;

    public static class Default implements ICinematicEffectListener {
        @Override // android.app.wallpapereffectsgeneration.ICinematicEffectListener
        public void onCinematicEffectGenerated(CinematicEffectResponse response) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICinematicEffectListener {
        static final int TRANSACTION_onCinematicEffectGenerated = 1;

        public Stub() {
            attachInterface(this, ICinematicEffectListener.DESCRIPTOR);
        }

        public static ICinematicEffectListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICinematicEffectListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ICinematicEffectListener)) {
                return (ICinematicEffectListener) iin;
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
                    return "onCinematicEffectGenerated";
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
                data.enforceInterface(ICinematicEffectListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICinematicEffectListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    CinematicEffectResponse _arg0 = (CinematicEffectResponse) data.readTypedObject(CinematicEffectResponse.CREATOR);
                    data.enforceNoDataAvail();
                    onCinematicEffectGenerated(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICinematicEffectListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICinematicEffectListener.DESCRIPTOR;
            }

            @Override // android.app.wallpapereffectsgeneration.ICinematicEffectListener
            public void onCinematicEffectGenerated(CinematicEffectResponse response) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICinematicEffectListener.DESCRIPTOR);
                    _data.writeTypedObject(response, 0);
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
