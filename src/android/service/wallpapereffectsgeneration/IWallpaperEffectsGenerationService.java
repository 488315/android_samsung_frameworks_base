package android.service.wallpapereffectsgeneration;

import android.app.wallpapereffectsgeneration.CinematicEffectRequest;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IWallpaperEffectsGenerationService extends IInterface {
    public static final String DESCRIPTOR = "android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService";

    public static class Default implements IWallpaperEffectsGenerationService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService
        public void onGenerateCinematicEffect(CinematicEffectRequest cinematicEffectRequest) throws RemoteException {
        }
    }

    void onGenerateCinematicEffect(CinematicEffectRequest cinematicEffectRequest) throws RemoteException;

    public static abstract class Stub extends Binder implements IWallpaperEffectsGenerationService {
        static final int TRANSACTION_onGenerateCinematicEffect = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IWallpaperEffectsGenerationService.DESCRIPTOR);
        }

        public static IWallpaperEffectsGenerationService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWallpaperEffectsGenerationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWallpaperEffectsGenerationService)) {
                return (IWallpaperEffectsGenerationService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onGenerateCinematicEffect";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWallpaperEffectsGenerationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWallpaperEffectsGenerationService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                CinematicEffectRequest cinematicEffectRequest = (CinematicEffectRequest) parcel.readTypedObject(CinematicEffectRequest.CREATOR);
                parcel.enforceNoDataAvail();
                onGenerateCinematicEffect(cinematicEffectRequest);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IWallpaperEffectsGenerationService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWallpaperEffectsGenerationService.DESCRIPTOR;
            }

            @Override // android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService
            public void onGenerateCinematicEffect(CinematicEffectRequest cinematicEffectRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWallpaperEffectsGenerationService.DESCRIPTOR);
                    obtain.writeTypedObject(cinematicEffectRequest, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
