package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ILocaleManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.ILocaleManager";

    public static class Default implements ILocaleManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.ILocaleManager
        public LocaleList getApplicationLocales(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.ILocaleManager
        public LocaleConfig getOverrideLocaleConfig(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.ILocaleManager
        public LocaleList getSystemLocales() throws RemoteException {
            return null;
        }

        @Override // android.app.ILocaleManager
        public void setApplicationLocales(String str, int i, LocaleList localeList, boolean z) throws RemoteException {
        }

        @Override // android.app.ILocaleManager
        public void setOverrideLocaleConfig(String str, int i, LocaleConfig localeConfig) throws RemoteException {
        }
    }

    LocaleList getApplicationLocales(String str, int i) throws RemoteException;

    LocaleConfig getOverrideLocaleConfig(String str, int i) throws RemoteException;

    LocaleList getSystemLocales() throws RemoteException;

    void setApplicationLocales(String str, int i, LocaleList localeList, boolean z) throws RemoteException;

    void setOverrideLocaleConfig(String str, int i, LocaleConfig localeConfig) throws RemoteException;

    public static abstract class Stub extends Binder implements ILocaleManager {
        static final int TRANSACTION_getApplicationLocales = 2;
        static final int TRANSACTION_getOverrideLocaleConfig = 5;
        static final int TRANSACTION_getSystemLocales = 3;
        static final int TRANSACTION_setApplicationLocales = 1;
        static final int TRANSACTION_setOverrideLocaleConfig = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ILocaleManager.DESCRIPTOR);
        }

        public static ILocaleManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILocaleManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILocaleManager)) {
                return (ILocaleManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setApplicationLocales";
            }
            if (i == 2) {
                return "getApplicationLocales";
            }
            if (i == 3) {
                return "getSystemLocales";
            }
            if (i == 4) {
                return "setOverrideLocaleConfig";
            }
            if (i != 5) {
                return null;
            }
            return "getOverrideLocaleConfig";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILocaleManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILocaleManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                LocaleList localeList = (LocaleList) parcel.readTypedObject(LocaleList.CREATOR);
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setApplicationLocales(readString, readInt, localeList, readBoolean);
                parcel2.writeNoException();
            } else if (i == 2) {
                String readString2 = parcel.readString();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                LocaleList applicationLocales = getApplicationLocales(readString2, readInt2);
                parcel2.writeNoException();
                parcel2.writeTypedObject(applicationLocales, 1);
            } else if (i == 3) {
                LocaleList systemLocales = getSystemLocales();
                parcel2.writeNoException();
                parcel2.writeTypedObject(systemLocales, 1);
            } else if (i == 4) {
                String readString3 = parcel.readString();
                int readInt3 = parcel.readInt();
                LocaleConfig localeConfig = (LocaleConfig) parcel.readTypedObject(LocaleConfig.CREATOR);
                parcel.enforceNoDataAvail();
                setOverrideLocaleConfig(readString3, readInt3, localeConfig);
                parcel2.writeNoException();
            } else if (i == 5) {
                String readString4 = parcel.readString();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                LocaleConfig overrideLocaleConfig = getOverrideLocaleConfig(readString4, readInt4);
                parcel2.writeNoException();
                parcel2.writeTypedObject(overrideLocaleConfig, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ILocaleManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILocaleManager.DESCRIPTOR;
            }

            @Override // android.app.ILocaleManager
            public void setApplicationLocales(String str, int i, LocaleList localeList, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocaleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(localeList, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ILocaleManager
            public LocaleList getApplicationLocales(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocaleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LocaleList) obtain2.readTypedObject(LocaleList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ILocaleManager
            public LocaleList getSystemLocales() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocaleManager.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LocaleList) obtain2.readTypedObject(LocaleList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ILocaleManager
            public void setOverrideLocaleConfig(String str, int i, LocaleConfig localeConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocaleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(localeConfig, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ILocaleManager
            public LocaleConfig getOverrideLocaleConfig(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocaleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LocaleConfig) obtain2.readTypedObject(LocaleConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
