package android.app.wallpapereffectsgeneration;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IWallpaperEffectsGenerationManager extends IInterface {
    public static final String DESCRIPTOR =
            "android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager";

    void generateCinematicEffect(
            CinematicEffectRequest cinematicEffectRequest,
            ICinematicEffectListener iCinematicEffectListener)
            throws RemoteException;

    void returnCinematicEffectResponse(CinematicEffectResponse cinematicEffectResponse)
            throws RemoteException;

    public static class Default implements IWallpaperEffectsGenerationManager {
        @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
        public void generateCinematicEffect(
                CinematicEffectRequest request, ICinematicEffectListener listener)
                throws RemoteException {}

        @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
        public void returnCinematicEffectResponse(CinematicEffectResponse response)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IWallpaperEffectsGenerationManager {
        static final int TRANSACTION_generateCinematicEffect = 1;
        static final int TRANSACTION_returnCinematicEffectResponse = 2;

        public Stub() {
            attachInterface(this, IWallpaperEffectsGenerationManager.DESCRIPTOR);
        }

        public static IWallpaperEffectsGenerationManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWallpaperEffectsGenerationManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IWallpaperEffectsGenerationManager)) {
                return (IWallpaperEffectsGenerationManager) iin;
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
                    return "generateCinematicEffect";
                case 2:
                    return "returnCinematicEffectResponse";
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
                data.enforceInterface(IWallpaperEffectsGenerationManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWallpaperEffectsGenerationManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    CinematicEffectRequest _arg0 =
                            (CinematicEffectRequest)
                                    data.readTypedObject(CinematicEffectRequest.CREATOR);
                    ICinematicEffectListener _arg1 =
                            ICinematicEffectListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    generateCinematicEffect(_arg0, _arg1);
                    return true;
                case 2:
                    CinematicEffectResponse _arg02 =
                            (CinematicEffectResponse)
                                    data.readTypedObject(CinematicEffectResponse.CREATOR);
                    data.enforceNoDataAvail();
                    returnCinematicEffectResponse(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWallpaperEffectsGenerationManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWallpaperEffectsGenerationManager.DESCRIPTOR;
            }

            @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
            public void generateCinematicEffect(
                    CinematicEffectRequest request, ICinematicEffectListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWallpaperEffectsGenerationManager.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
            public void returnCinematicEffectResponse(CinematicEffectResponse response)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWallpaperEffectsGenerationManager.DESCRIPTOR);
                    _data.writeTypedObject(response, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
