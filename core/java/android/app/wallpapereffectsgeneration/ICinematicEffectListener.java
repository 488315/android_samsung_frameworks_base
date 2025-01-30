package android.app.wallpapereffectsgeneration;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface ICinematicEffectListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.wallpapereffectsgeneration.ICinematicEffectListener";

    void onCinematicEffectGenerated(CinematicEffectResponse cinematicEffectResponse) throws RemoteException;

    public static class Default implements ICinematicEffectListener {
        @Override // android.app.wallpapereffectsgeneration.ICinematicEffectListener
        public void onCinematicEffectGenerated(CinematicEffectResponse response) throws RemoteException {
        }

        @Override // android.p009os.IInterface
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

        @Override // android.p009os.IInterface
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICinematicEffectListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ICinematicEffectListener.DESCRIPTOR);
                    return true;
                default:
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
        }

        private static class Proxy implements ICinematicEffectListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
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

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
