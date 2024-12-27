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

    void onColorsChanged(RectF rectF, WallpaperColors wallpaperColors) throws RemoteException;

    public static class Default implements ILocalWallpaperColorConsumer {
        @Override // android.app.ILocalWallpaperColorConsumer
        public void onColorsChanged(RectF area, WallpaperColors colors) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ILocalWallpaperColorConsumer {
        static final int TRANSACTION_onColorsChanged = 1;

        public Stub() {
            attachInterface(this, ILocalWallpaperColorConsumer.DESCRIPTOR);
        }

        public static ILocalWallpaperColorConsumer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ILocalWallpaperColorConsumer.DESCRIPTOR);
            if (iin != null && (iin instanceof ILocalWallpaperColorConsumer)) {
                return (ILocalWallpaperColorConsumer) iin;
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
                    return "onColorsChanged";
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
                data.enforceInterface(ILocalWallpaperColorConsumer.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ILocalWallpaperColorConsumer.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    RectF _arg0 = (RectF) data.readTypedObject(RectF.CREATOR);
                    WallpaperColors _arg1 =
                            (WallpaperColors) data.readTypedObject(WallpaperColors.CREATOR);
                    data.enforceNoDataAvail();
                    onColorsChanged(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILocalWallpaperColorConsumer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILocalWallpaperColorConsumer.DESCRIPTOR;
            }

            @Override // android.app.ILocalWallpaperColorConsumer
            public void onColorsChanged(RectF area, WallpaperColors colors) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ILocalWallpaperColorConsumer.DESCRIPTOR);
                    _data.writeTypedObject(area, 0);
                    _data.writeTypedObject(colors, 0);
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
