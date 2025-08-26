package android.app;

import android.graphics.RectF;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ILocalWallpaperColorConsumer extends IInterface {
    public static final String DESCRIPTOR = "android.app.ILocalWallpaperColorConsumer";

    public static class Default implements ILocalWallpaperColorConsumer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.ILocalWallpaperColorConsumer
        public void onColorsChanged(RectF rectF, WallpaperColors wallpaperColors) throws RemoteException {
        }
    }

    void onColorsChanged(RectF rectF, WallpaperColors wallpaperColors) throws RemoteException;

    public static abstract class Stub extends Binder implements ILocalWallpaperColorConsumer {
        static final int TRANSACTION_onColorsChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ILocalWallpaperColorConsumer.DESCRIPTOR);
        }

        public static ILocalWallpaperColorConsumer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ILocalWallpaperColorConsumer.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ILocalWallpaperColorConsumer)) {
                return (ILocalWallpaperColorConsumer) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onColorsChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILocalWallpaperColorConsumer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILocalWallpaperColorConsumer.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                RectF rectF = (RectF) parcel.readTypedObject(RectF.CREATOR);
                WallpaperColors wallpaperColors = (WallpaperColors) parcel.readTypedObject(WallpaperColors.CREATOR);
                parcel.enforceNoDataAvail();
                onColorsChanged(rectF, wallpaperColors);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ILocalWallpaperColorConsumer {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILocalWallpaperColorConsumer.DESCRIPTOR;
            }

            @Override // android.app.ILocalWallpaperColorConsumer
            public void onColorsChanged(RectF rectF, WallpaperColors wallpaperColors) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ILocalWallpaperColorConsumer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rectF, 0);
                    parcelObtain.writeTypedObject(wallpaperColors, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
