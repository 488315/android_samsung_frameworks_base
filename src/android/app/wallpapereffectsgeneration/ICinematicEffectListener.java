package android.app.wallpapereffectsgeneration;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ICinematicEffectListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.wallpapereffectsgeneration.ICinematicEffectListener";

    public static class Default implements ICinematicEffectListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.wallpapereffectsgeneration.ICinematicEffectListener
        public void onCinematicEffectGenerated(CinematicEffectResponse cinematicEffectResponse) throws RemoteException {
        }
    }

    void onCinematicEffectGenerated(CinematicEffectResponse cinematicEffectResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements ICinematicEffectListener {
        static final int TRANSACTION_onCinematicEffectGenerated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ICinematicEffectListener.DESCRIPTOR);
        }

        public static ICinematicEffectListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICinematicEffectListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICinematicEffectListener)) {
                return (ICinematicEffectListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onCinematicEffectGenerated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICinematicEffectListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICinematicEffectListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                CinematicEffectResponse cinematicEffectResponse = (CinematicEffectResponse) parcel.readTypedObject(CinematicEffectResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onCinematicEffectGenerated(cinematicEffectResponse);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICinematicEffectListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICinematicEffectListener.DESCRIPTOR;
            }

            @Override // android.app.wallpapereffectsgeneration.ICinematicEffectListener
            public void onCinematicEffectGenerated(CinematicEffectResponse cinematicEffectResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICinematicEffectListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cinematicEffectResponse, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
