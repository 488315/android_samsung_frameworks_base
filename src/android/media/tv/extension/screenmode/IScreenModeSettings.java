package android.media.tv.extension.screenmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IScreenModeSettings extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.screenmode.IScreenModeSettings";

    public static class Default implements IScreenModeSettings {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.screenmode.IScreenModeSettings
        public int getOverScanIndex(String str) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.screenmode.IScreenModeSettings
        public boolean getSupportApplyOverScan(String str) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.extension.screenmode.IScreenModeSettings
        public void setScreenModeSettings(String str, String str2) throws RemoteException {
        }
    }

    int getOverScanIndex(String str) throws RemoteException;

    boolean getSupportApplyOverScan(String str) throws RemoteException;

    void setScreenModeSettings(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IScreenModeSettings {
        static final int TRANSACTION_getOverScanIndex = 2;
        static final int TRANSACTION_getSupportApplyOverScan = 3;
        static final int TRANSACTION_setScreenModeSettings = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.screenmode.IScreenModeSettings");
        }

        public static IScreenModeSettings asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.screenmode.IScreenModeSettings");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IScreenModeSettings)) {
                return (IScreenModeSettings) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setScreenModeSettings";
            }
            if (i == 2) {
                return "getOverScanIndex";
            }
            if (i != 3) {
                return null;
            }
            return "getSupportApplyOverScan";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.screenmode.IScreenModeSettings");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.screenmode.IScreenModeSettings");
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                setScreenModeSettings(readString, readString2);
                parcel2.writeNoException();
            } else if (i == 2) {
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                int overScanIndex = getOverScanIndex(readString3);
                parcel2.writeNoException();
                parcel2.writeInt(overScanIndex);
            } else if (i == 3) {
                String readString4 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean supportApplyOverScan = getSupportApplyOverScan(readString4);
                parcel2.writeNoException();
                parcel2.writeBoolean(supportApplyOverScan);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IScreenModeSettings {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.screenmode.IScreenModeSettings";
            }

            @Override // android.media.tv.extension.screenmode.IScreenModeSettings
            public void setScreenModeSettings(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.screenmode.IScreenModeSettings");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.screenmode.IScreenModeSettings
            public int getOverScanIndex(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.screenmode.IScreenModeSettings");
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.screenmode.IScreenModeSettings
            public boolean getSupportApplyOverScan(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.screenmode.IScreenModeSettings");
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
