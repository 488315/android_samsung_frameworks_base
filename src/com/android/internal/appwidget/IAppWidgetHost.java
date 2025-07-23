package com.android.internal.appwidget;

import android.appwidget.AppWidgetProviderInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.RemoteViews;

/* loaded from: classes5.dex */
public interface IAppWidgetHost extends IInterface {

    public static class Default implements IAppWidgetHost {
        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void appWidgetRemoved(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providerChanged(int i, AppWidgetProviderInfo appWidgetProviderInfo) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providersChanged() throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void updateAppWidget(int i, RemoteViews remoteViews) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void updateAppWidgetDeferred(int i) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void viewDataChanged(int i, int i2) throws RemoteException {
        }
    }

    void appWidgetRemoved(int i) throws RemoteException;

    void providerChanged(int i, AppWidgetProviderInfo appWidgetProviderInfo) throws RemoteException;

    void providersChanged() throws RemoteException;

    void updateAppWidget(int i, RemoteViews remoteViews) throws RemoteException;

    void updateAppWidgetDeferred(int i) throws RemoteException;

    void viewDataChanged(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppWidgetHost {
        public static final String DESCRIPTOR = "com.android.internal.appwidget.IAppWidgetHost";
        static final int TRANSACTION_appWidgetRemoved = 6;
        static final int TRANSACTION_providerChanged = 3;
        static final int TRANSACTION_providersChanged = 4;
        static final int TRANSACTION_updateAppWidget = 2;
        static final int TRANSACTION_updateAppWidgetDeferred = 1;
        static final int TRANSACTION_viewDataChanged = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAppWidgetHost asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppWidgetHost)) {
                return (IAppWidgetHost) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateAppWidgetDeferred";
                case 2:
                    return "updateAppWidget";
                case 3:
                    return "providerChanged";
                case 4:
                    return "providersChanged";
                case 5:
                    return "viewDataChanged";
                case 6:
                    return "appWidgetRemoved";
                default:
                    return null;
            }
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
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateAppWidgetDeferred(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    RemoteViews remoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAppWidget(readInt2, remoteViews);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    AppWidgetProviderInfo appWidgetProviderInfo = (AppWidgetProviderInfo) parcel.readTypedObject(AppWidgetProviderInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    providerChanged(readInt3, appWidgetProviderInfo);
                    return true;
                case 4:
                    providersChanged();
                    return true;
                case 5:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    viewDataChanged(readInt4, readInt5);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    appWidgetRemoved(readInt6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAppWidgetHost {
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

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void updateAppWidgetDeferred(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void updateAppWidget(int i, RemoteViews remoteViews) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void providerChanged(int i, AppWidgetProviderInfo appWidgetProviderInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(appWidgetProviderInfo, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void providersChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void viewDataChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void appWidgetRemoved(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
