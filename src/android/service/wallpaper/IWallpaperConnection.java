package android.service.wallpaper;

import android.app.WallpaperColors;
import android.graphics.RectF;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.service.wallpaper.IWallpaperEngine;

/* loaded from: classes3.dex */
public interface IWallpaperConnection extends IInterface {

    public static class Default implements IWallpaperConnection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.wallpaper.IWallpaperConnection
        public void attachEngine(IWallpaperEngine iWallpaperEngine, int i) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperConnection
        public void engineShown(IWallpaperEngine iWallpaperEngine) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperConnection
        public void onLocalWallpaperColorsChanged(RectF rectF, WallpaperColors wallpaperColors, int i) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperConnection
        public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperConnection
        public ParcelFileDescriptor setWallpaper(String str) throws RemoteException {
            return null;
        }
    }

    void attachEngine(IWallpaperEngine iWallpaperEngine, int i) throws RemoteException;

    void engineShown(IWallpaperEngine iWallpaperEngine) throws RemoteException;

    void onLocalWallpaperColorsChanged(RectF rectF, WallpaperColors wallpaperColors, int i) throws RemoteException;

    void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i) throws RemoteException;

    ParcelFileDescriptor setWallpaper(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IWallpaperConnection {
        public static final String DESCRIPTOR = "android.service.wallpaper.IWallpaperConnection";
        static final int TRANSACTION_attachEngine = 1;
        static final int TRANSACTION_engineShown = 2;
        static final int TRANSACTION_onLocalWallpaperColorsChanged = 5;
        static final int TRANSACTION_onWallpaperColorsChanged = 4;
        static final int TRANSACTION_setWallpaper = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWallpaperConnection)) {
                return (IWallpaperConnection) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "attachEngine";
            }
            if (i == 2) {
                return "engineShown";
            }
            if (i == 3) {
                return "setWallpaper";
            }
            if (i == 4) {
                return "onWallpaperColorsChanged";
            }
            if (i != 5) {
                return null;
            }
            return "onLocalWallpaperColorsChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IWallpaperEngine iWallpaperEngineAsInterface = IWallpaperEngine.Stub.asInterface(parcel.readStrongBinder());
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                attachEngine(iWallpaperEngineAsInterface, i3);
                parcel2.writeNoException();
            } else if (i == 2) {
                IWallpaperEngine iWallpaperEngineAsInterface2 = IWallpaperEngine.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                engineShown(iWallpaperEngineAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                ParcelFileDescriptor wallpaper = setWallpaper(string);
                parcel2.writeNoException();
                parcel2.writeTypedObject(wallpaper, 1);
            } else if (i == 4) {
                WallpaperColors wallpaperColors = (WallpaperColors) parcel.readTypedObject(WallpaperColors.CREATOR);
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onWallpaperColorsChanged(wallpaperColors, i4);
                parcel2.writeNoException();
            } else if (i == 5) {
                RectF rectF = (RectF) parcel.readTypedObject(RectF.CREATOR);
                WallpaperColors wallpaperColors2 = (WallpaperColors) parcel.readTypedObject(WallpaperColors.CREATOR);
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onLocalWallpaperColorsChanged(rectF, wallpaperColors2, i5);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IWallpaperConnection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.wallpaper.IWallpaperConnection
            public void attachEngine(IWallpaperEngine iWallpaperEngine, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWallpaperEngine);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperConnection
            public void engineShown(IWallpaperEngine iWallpaperEngine) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWallpaperEngine);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperConnection
            public ParcelFileDescriptor setWallpaper(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperConnection
            public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(wallpaperColors, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperConnection
            public void onLocalWallpaperColorsChanged(RectF rectF, WallpaperColors wallpaperColors, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rectF, 0);
                    parcelObtain.writeTypedObject(wallpaperColors, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
