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

    void onGenerateCinematicEffect(CinematicEffectRequest cinematicEffectRequest) throws RemoteException;

    public static class Default implements IWallpaperEffectsGenerationService {
        @Override // android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService
        public void onGenerateCinematicEffect(CinematicEffectRequest request) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWallpaperEffectsGenerationService {
        static final int TRANSACTION_onGenerateCinematicEffect = 1;

        public Stub() {
            attachInterface(this, IWallpaperEffectsGenerationService.DESCRIPTOR);
        }

        public static IWallpaperEffectsGenerationService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWallpaperEffectsGenerationService.DESCRIPTOR);
            if (iin != null && (iin instanceof IWallpaperEffectsGenerationService)) {
                return (IWallpaperEffectsGenerationService) iin;
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
                    return "onGenerateCinematicEffect";
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
                data.enforceInterface(IWallpaperEffectsGenerationService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWallpaperEffectsGenerationService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    CinematicEffectRequest _arg0 = (CinematicEffectRequest) data.readTypedObject(CinematicEffectRequest.CREATOR);
                    data.enforceNoDataAvail();
                    onGenerateCinematicEffect(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWallpaperEffectsGenerationService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWallpaperEffectsGenerationService.DESCRIPTOR;
            }

            @Override // android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService
            public void onGenerateCinematicEffect(CinematicEffectRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWallpaperEffectsGenerationService.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
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
