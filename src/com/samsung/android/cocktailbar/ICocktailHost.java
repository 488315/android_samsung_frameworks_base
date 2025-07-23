package com.samsung.android.cocktailbar;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.RemoteViews;

/* loaded from: classes6.dex */
public interface ICocktailHost extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cocktailbar.ICocktailHost";

    public static class Default implements ICocktailHost {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void changeVisibleEdgeService(boolean z, int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void closeCocktail(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void notePauseComponent(ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void noteResumeComponent(ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void notifyKeyguardState(boolean z, int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void notifyWakeUpState(boolean z, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void packageSuspendChanged(Cocktail cocktail) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void partiallyUpdateCocktail(int i, RemoteViews remoteViews, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void partiallyUpdateHelpView(int i, RemoteViews remoteViews, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void removeCocktail(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void sendExtraData(int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void setDisableTickerView(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void setPullToRefresh(int i, int i2, PendingIntent pendingIntent, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void showCocktail(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void switchDefaultCocktail(int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void updateCocktail(int i, Cocktail cocktail, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void updateToolLauncher(int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void viewDataChanged(int i, int i2, int i3) throws RemoteException {
        }
    }

    void changeVisibleEdgeService(boolean z, int i) throws RemoteException;

    void closeCocktail(int i, int i2, int i3) throws RemoteException;

    void notePauseComponent(ComponentName componentName) throws RemoteException;

    void noteResumeComponent(ComponentName componentName) throws RemoteException;

    void notifyKeyguardState(boolean z, int i) throws RemoteException;

    void notifyWakeUpState(boolean z, int i, int i2) throws RemoteException;

    void packageSuspendChanged(Cocktail cocktail) throws RemoteException;

    void partiallyUpdateCocktail(int i, RemoteViews remoteViews, int i2) throws RemoteException;

    void partiallyUpdateHelpView(int i, RemoteViews remoteViews, int i2) throws RemoteException;

    void removeCocktail(int i, int i2) throws RemoteException;

    void sendExtraData(int i, Bundle bundle) throws RemoteException;

    void setDisableTickerView(int i, int i2) throws RemoteException;

    void setPullToRefresh(int i, int i2, PendingIntent pendingIntent, int i3) throws RemoteException;

    void showCocktail(int i, int i2) throws RemoteException;

    void switchDefaultCocktail(int i) throws RemoteException;

    void updateCocktail(int i, Cocktail cocktail, int i2) throws RemoteException;

    void updateToolLauncher(int i) throws RemoteException;

    void viewDataChanged(int i, int i2, int i3) throws RemoteException;

    public static abstract class Stub extends Binder implements ICocktailHost {
        static final int TRANSACTION_changeVisibleEdgeService = 15;
        static final int TRANSACTION_closeCocktail = 6;
        static final int TRANSACTION_notePauseComponent = 17;
        static final int TRANSACTION_noteResumeComponent = 16;
        static final int TRANSACTION_notifyKeyguardState = 10;
        static final int TRANSACTION_notifyWakeUpState = 11;
        static final int TRANSACTION_packageSuspendChanged = 18;
        static final int TRANSACTION_partiallyUpdateCocktail = 2;
        static final int TRANSACTION_partiallyUpdateHelpView = 3;
        static final int TRANSACTION_removeCocktail = 4;
        static final int TRANSACTION_sendExtraData = 13;
        static final int TRANSACTION_setDisableTickerView = 14;
        static final int TRANSACTION_setPullToRefresh = 8;
        static final int TRANSACTION_showCocktail = 5;
        static final int TRANSACTION_switchDefaultCocktail = 12;
        static final int TRANSACTION_updateCocktail = 1;
        static final int TRANSACTION_updateToolLauncher = 9;
        static final int TRANSACTION_viewDataChanged = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }

        public Stub() {
            attachInterface(this, ICocktailHost.DESCRIPTOR);
        }

        public static ICocktailHost asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICocktailHost.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICocktailHost)) {
                return (ICocktailHost) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateCocktail";
                case 2:
                    return "partiallyUpdateCocktail";
                case 3:
                    return "partiallyUpdateHelpView";
                case 4:
                    return "removeCocktail";
                case 5:
                    return "showCocktail";
                case 6:
                    return "closeCocktail";
                case 7:
                    return "viewDataChanged";
                case 8:
                    return "setPullToRefresh";
                case 9:
                    return "updateToolLauncher";
                case 10:
                    return "notifyKeyguardState";
                case 11:
                    return "notifyWakeUpState";
                case 12:
                    return "switchDefaultCocktail";
                case 13:
                    return "sendExtraData";
                case 14:
                    return "setDisableTickerView";
                case 15:
                    return "changeVisibleEdgeService";
                case 16:
                    return "noteResumeComponent";
                case 17:
                    return "notePauseComponent";
                case 18:
                    return "packageSuspendChanged";
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
                parcel.enforceInterface(ICocktailHost.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICocktailHost.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    Cocktail cocktail = (Cocktail) parcel.readTypedObject(Cocktail.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCocktail(readInt, cocktail, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    RemoteViews remoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partiallyUpdateCocktail(readInt3, remoteViews, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    RemoteViews remoteViews2 = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partiallyUpdateHelpView(readInt5, remoteViews2, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeCocktail(readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showCocktail(readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeCocktail(readInt11, readInt12, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    viewDataChanged(readInt14, readInt15, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPullToRefresh(readInt17, readInt18, pendingIntent, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateToolLauncher(readInt20);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyKeyguardState(readBoolean, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyWakeUpState(readBoolean2, readInt22, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    switchDefaultCocktail(readInt24);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt25 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendExtraData(readInt25, bundle);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisableTickerView(readInt26, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeVisibleEdgeService(readBoolean3, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteResumeComponent(componentName);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    notePauseComponent(componentName2);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    Cocktail cocktail2 = (Cocktail) parcel.readTypedObject(Cocktail.CREATOR);
                    parcel.enforceNoDataAvail();
                    packageSuspendChanged(cocktail2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICocktailHost {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICocktailHost.DESCRIPTOR;
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void updateCocktail(int i, Cocktail cocktail, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cocktail, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void partiallyUpdateCocktail(int i, RemoteViews remoteViews, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteViews, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void partiallyUpdateHelpView(int i, RemoteViews remoteViews, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteViews, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void removeCocktail(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void showCocktail(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void closeCocktail(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void viewDataChanged(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void setPullToRefresh(int i, int i2, PendingIntent pendingIntent, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void updateToolLauncher(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void notifyKeyguardState(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void notifyWakeUpState(boolean z, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void switchDefaultCocktail(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void sendExtraData(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void setDisableTickerView(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void changeVisibleEdgeService(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void noteResumeComponent(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void notePauseComponent(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void packageSuspendChanged(Cocktail cocktail) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    obtain.writeTypedObject(cocktail, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
