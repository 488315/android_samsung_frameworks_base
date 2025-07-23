package android.app.wallpapereffectsgeneration;

import android.app.wallpapereffectsgeneration.ICinematicEffectListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IWallpaperEffectsGenerationManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager";

    public static class Default implements IWallpaperEffectsGenerationManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
        public void generateCinematicEffect(CinematicEffectRequest cinematicEffectRequest, ICinematicEffectListener iCinematicEffectListener) throws RemoteException {
        }

        @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
        public void returnCinematicEffectResponse(CinematicEffectResponse cinematicEffectResponse) throws RemoteException {
        }
    }

    void generateCinematicEffect(CinematicEffectRequest cinematicEffectRequest, ICinematicEffectListener iCinematicEffectListener) throws RemoteException;

    void returnCinematicEffectResponse(CinematicEffectResponse cinematicEffectResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements IWallpaperEffectsGenerationManager {
        static final int TRANSACTION_generateCinematicEffect = 1;
        static final int TRANSACTION_returnCinematicEffectResponse = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IWallpaperEffectsGenerationManager.DESCRIPTOR);
        }

        public static IWallpaperEffectsGenerationManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWallpaperEffectsGenerationManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWallpaperEffectsGenerationManager)) {
                return (IWallpaperEffectsGenerationManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "generateCinematicEffect";
            }
            if (i != 2) {
                return null;
            }
            return "returnCinematicEffectResponse";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWallpaperEffectsGenerationManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWallpaperEffectsGenerationManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                CinematicEffectRequest cinematicEffectRequest = (CinematicEffectRequest) parcel.readTypedObject(CinematicEffectRequest.CREATOR);
                ICinematicEffectListener asInterface = ICinematicEffectListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                generateCinematicEffect(cinematicEffectRequest, asInterface);
            } else if (i == 2) {
                CinematicEffectResponse cinematicEffectResponse = (CinematicEffectResponse) parcel.readTypedObject(CinematicEffectResponse.CREATOR);
                parcel.enforceNoDataAvail();
                returnCinematicEffectResponse(cinematicEffectResponse);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IWallpaperEffectsGenerationManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWallpaperEffectsGenerationManager.DESCRIPTOR;
            }

            @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
            public void generateCinematicEffect(CinematicEffectRequest cinematicEffectRequest, ICinematicEffectListener iCinematicEffectListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWallpaperEffectsGenerationManager.DESCRIPTOR);
                    obtain.writeTypedObject(cinematicEffectRequest, 0);
                    obtain.writeStrongInterface(iCinematicEffectListener);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
            public void returnCinematicEffectResponse(CinematicEffectResponse cinematicEffectResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWallpaperEffectsGenerationManager.DESCRIPTOR);
                    obtain.writeTypedObject(cinematicEffectResponse, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
