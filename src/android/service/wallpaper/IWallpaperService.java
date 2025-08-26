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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWallpaperService)) {
                return (IWallpaperService) iInterfaceQueryLocalInterface;
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
                IWallpaperConnection iWallpaperConnectionAsInterface = IWallpaperConnection.Stub.asInterface(parcel.readStrongBinder());
                IBinder strongBinder = parcel.readStrongBinder();
                int i3 = parcel.readInt();
                boolean z = parcel.readBoolean();
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                int i6 = parcel.readInt();
                int i7 = parcel.readInt();
                WallpaperInfo wallpaperInfo = (WallpaperInfo) parcel.readTypedObject(WallpaperInfo.CREATOR);
                WallpaperDescription wallpaperDescription = (WallpaperDescription) parcel.readTypedObject(WallpaperDescription.CREATOR);
                parcel.enforceNoDataAvail();
                attach(iWallpaperConnectionAsInterface, strongBinder, i3, z, i4, i5, rect, i6, i7, wallpaperInfo, wallpaperDescription);
            } else if (i == 2) {
                IBinder strongBinder2 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                detach(strongBinder2);
            } else if (i == 3) {
                IWallpaperConnection iWallpaperConnectionAsInterface2 = IWallpaperConnection.Stub.asInterface(parcel.readStrongBinder());
                IBinder strongBinder3 = parcel.readStrongBinder();
                int i8 = parcel.readInt();
                boolean z2 = parcel.readBoolean();
                int i9 = parcel.readInt();
                int i10 = parcel.readInt();
                Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                int i11 = parcel.readInt();
                int i12 = parcel.readInt();
                WallpaperInfo wallpaperInfo2 = (WallpaperInfo) parcel.readTypedObject(WallpaperInfo.CREATOR);
                WallpaperDescription wallpaperDescription2 = (WallpaperDescription) parcel.readTypedObject(WallpaperDescription.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                attachWithExtras(iWallpaperConnectionAsInterface2, strongBinder3, i8, z2, i9, i10, rect2, i11, i12, wallpaperInfo2, wallpaperDescription2, bundle);
            } else if (i == 4) {
                int i13 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setCurrentUserId(i13);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWallpaperConnection);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeTypedObject(wallpaperInfo, 0);
                    parcelObtain.writeTypedObject(wallpaperDescription, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperService
            public void detach(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperService
            public void attachWithExtras(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int i, boolean z, int i2, int i3, Rect rect, int i4, int i5, WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWallpaperConnection);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeTypedObject(wallpaperInfo, 0);
                    parcelObtain.writeTypedObject(wallpaperDescription, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperService
            public void setCurrentUserId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
