package android.service.wallpaper;

import android.app.WallpaperInfo;
import android.app.wallpaper.WallpaperDescription;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.wallpaper.IWallpaperConnection;

/* loaded from: classes3.dex */
public interface IWallpaperService extends IInterface {

    public static class Default implements IWallpaperService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void attach(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int i, boolean z, int i2, int i3, Rect rect, int i4, int i5, WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void attachWithExtras(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int i, boolean z, int i2, int i3, Rect rect, int i4, int i5, WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription, Bundle bundle) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void detach(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void setCurrentUserId(int i) throws RemoteException {
        }
    }

    void attach(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int i, boolean z, int i2, int i3, Rect rect, int i4, int i5, WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription) throws RemoteException;

    void attachWithExtras(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int i, boolean z, int i2, int i3, Rect rect, int i4, int i5, WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription, Bundle bundle) throws RemoteException;

    void detach(IBinder iBinder) throws RemoteException;

    void setCurrentUserId(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IWallpaperService {
        public static final String DESCRIPTOR = "android.service.wallpaper.IWallpaperService";
        static final int TRANSACTION_attach = 1;
        static final int TRANSACTION_attachWithExtras = 3;
        static final int TRANSACTION_detach = 2;
        static final int TRANSACTION_setCurrentUserId = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWallpaperService)) {
                return (IWallpaperService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "attach";
            }
            if (i == 2) {
                return "detach";
            }
            if (i == 3) {
                return "attachWithExtras";
            }
            if (i != 4) {
                return null;
            }
            return "setCurrentUserId";
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
                IWallpaperConnection asInterface = IWallpaperConnection.Stub.asInterface(parcel.readStrongBinder());
                IBinder readStrongBinder = parcel.readStrongBinder();
                int readInt = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                WallpaperInfo wallpaperInfo = (WallpaperInfo) parcel.readTypedObject(WallpaperInfo.CREATOR);
                WallpaperDescription wallpaperDescription = (WallpaperDescription) parcel.readTypedObject(WallpaperDescription.CREATOR);
                parcel.enforceNoDataAvail();
                attach(asInterface, readStrongBinder, readInt, readBoolean, readInt2, readInt3, rect, readInt4, readInt5, wallpaperInfo, wallpaperDescription);
            } else if (i == 2) {
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                detach(readStrongBinder2);
            } else if (i == 3) {
                IWallpaperConnection asInterface2 = IWallpaperConnection.Stub.asInterface(parcel.readStrongBinder());
                IBinder readStrongBinder3 = parcel.readStrongBinder();
                int readInt6 = parcel.readInt();
                boolean readBoolean2 = parcel.readBoolean();
                int readInt7 = parcel.readInt();
                int readInt8 = parcel.readInt();
                Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                int readInt9 = parcel.readInt();
                int readInt10 = parcel.readInt();
                WallpaperInfo wallpaperInfo2 = (WallpaperInfo) parcel.readTypedObject(WallpaperInfo.CREATOR);
                WallpaperDescription wallpaperDescription2 = (WallpaperDescription) parcel.readTypedObject(WallpaperDescription.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                attachWithExtras(asInterface2, readStrongBinder3, readInt6, readBoolean2, readInt7, readInt8, rect2, readInt9, readInt10, wallpaperInfo2, wallpaperDescription2, bundle);
            } else if (i == 4) {
                int readInt11 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setCurrentUserId(readInt11);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IWallpaperService {
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

            @Override // android.service.wallpaper.IWallpaperService
            public void attach(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int i, boolean z, int i2, int i3, Rect rect, int i4, int i5, WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperConnection);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeTypedObject(wallpaperInfo, 0);
                    obtain.writeTypedObject(wallpaperDescription, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperService
            public void detach(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperService
            public void attachWithExtras(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int i, boolean z, int i2, int i3, Rect rect, int i4, int i5, WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperConnection);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeTypedObject(wallpaperInfo, 0);
                    obtain.writeTypedObject(wallpaperDescription, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperService
            public void setCurrentUserId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
