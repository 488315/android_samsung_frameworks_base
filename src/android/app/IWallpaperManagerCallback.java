package android.app;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IWallpaperManagerCallback extends IInterface {

    public static class Default implements IWallpaperManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperChanged(int i, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsAnalysisRequested(int i, int i2) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperChanged() throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) throws RemoteException {
        }
    }

    void onSemWallpaperChanged(int i, int i2, Bundle bundle) throws RemoteException;

    void onSemWallpaperColorsAnalysisRequested(int i, int i2) throws RemoteException;

    void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) throws RemoteException;

    void onWallpaperChanged() throws RemoteException;

    void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IWallpaperManagerCallback {
        public static final String DESCRIPTOR = "android.app.IWallpaperManagerCallback";
        static final int TRANSACTION_onSemWallpaperChanged = 5;
        static final int TRANSACTION_onSemWallpaperColorsAnalysisRequested = 4;
        static final int TRANSACTION_onSemWallpaperColorsChanged = 3;
        static final int TRANSACTION_onWallpaperChanged = 1;
        static final int TRANSACTION_onWallpaperColorsChanged = 2;

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

        public static IWallpaperManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWallpaperManagerCallback)) {
                return (IWallpaperManagerCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onWallpaperChanged";
            }
            if (i == 2) {
                return "onWallpaperColorsChanged";
            }
            if (i == 3) {
                return "onSemWallpaperColorsChanged";
            }
            if (i == 4) {
                return "onSemWallpaperColorsAnalysisRequested";
            }
            if (i != 5) {
                return null;
            }
            return "onSemWallpaperChanged";
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
                onWallpaperChanged();
            } else if (i == 2) {
                WallpaperColors wallpaperColors = (WallpaperColors) parcel.readTypedObject(WallpaperColors.CREATOR);
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onWallpaperColorsChanged(wallpaperColors, readInt, readInt2);
            } else if (i == 3) {
                SemWallpaperColors semWallpaperColors = (SemWallpaperColors) parcel.readTypedObject(SemWallpaperColors.CREATOR);
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSemWallpaperColorsChanged(semWallpaperColors, readInt3, readInt4);
            } else if (i == 4) {
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSemWallpaperColorsAnalysisRequested(readInt5, readInt6);
            } else if (i == 5) {
                int readInt7 = parcel.readInt();
                int readInt8 = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onSemWallpaperChanged(readInt7, readInt8, bundle);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IWallpaperManagerCallback {
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

            @Override // android.app.IWallpaperManagerCallback
            public void onWallpaperChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManagerCallback
            public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(wallpaperColors, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManagerCallback
            public void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(semWallpaperColors, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManagerCallback
            public void onSemWallpaperColorsAnalysisRequested(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManagerCallback
            public void onSemWallpaperChanged(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
