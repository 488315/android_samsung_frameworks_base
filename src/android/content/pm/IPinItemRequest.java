package android.content.pm;

import android.appwidget.AppWidgetProviderInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPinItemRequest extends IInterface {

    public static class Default implements IPinItemRequest {
        @Override // android.content.pm.IPinItemRequest
        public boolean accept(Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IPinItemRequest
        public AppWidgetProviderInfo getAppWidgetProviderInfo() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPinItemRequest
        public Bundle getExtras() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPinItemRequest
        public ShortcutInfo getShortcutInfo() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPinItemRequest
        public boolean isValid() throws RemoteException {
            return false;
        }
    }

    boolean accept(Bundle bundle) throws RemoteException;

    AppWidgetProviderInfo getAppWidgetProviderInfo() throws RemoteException;

    Bundle getExtras() throws RemoteException;

    ShortcutInfo getShortcutInfo() throws RemoteException;

    boolean isValid() throws RemoteException;

    public static abstract class Stub extends Binder implements IPinItemRequest {
        public static final String DESCRIPTOR = "android.content.pm.IPinItemRequest";
        static final int TRANSACTION_accept = 2;
        static final int TRANSACTION_getAppWidgetProviderInfo = 4;
        static final int TRANSACTION_getExtras = 5;
        static final int TRANSACTION_getShortcutInfo = 3;
        static final int TRANSACTION_isValid = 1;

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

        public static IPinItemRequest asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPinItemRequest)) {
                return (IPinItemRequest) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "isValid";
            }
            if (i == 2) {
                return "accept";
            }
            if (i == 3) {
                return "getShortcutInfo";
            }
            if (i == 4) {
                return "getAppWidgetProviderInfo";
            }
            if (i != 5) {
                return null;
            }
            return "getExtras";
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
                boolean isValid = isValid();
                parcel2.writeNoException();
                parcel2.writeBoolean(isValid);
            } else if (i == 2) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                boolean accept = accept(bundle);
                parcel2.writeNoException();
                parcel2.writeBoolean(accept);
            } else if (i == 3) {
                ShortcutInfo shortcutInfo = getShortcutInfo();
                parcel2.writeNoException();
                parcel2.writeTypedObject(shortcutInfo, 1);
            } else if (i == 4) {
                AppWidgetProviderInfo appWidgetProviderInfo = getAppWidgetProviderInfo();
                parcel2.writeNoException();
                parcel2.writeTypedObject(appWidgetProviderInfo, 1);
            } else if (i == 5) {
                Bundle extras = getExtras();
                parcel2.writeNoException();
                parcel2.writeTypedObject(extras, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPinItemRequest {
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

            @Override // android.content.pm.IPinItemRequest
            public boolean isValid() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPinItemRequest
            public boolean accept(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPinItemRequest
            public ShortcutInfo getShortcutInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ShortcutInfo) obtain2.readTypedObject(ShortcutInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPinItemRequest
            public AppWidgetProviderInfo getAppWidgetProviderInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AppWidgetProviderInfo) obtain2.readTypedObject(AppWidgetProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPinItemRequest
            public Bundle getExtras() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
