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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICocktailHost.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICocktailHost)) {
                return (ICocktailHost) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    Cocktail cocktail = (Cocktail) parcel.readTypedObject(Cocktail.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCocktail(i3, cocktail, i4);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    RemoteViews remoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partiallyUpdateCocktail(i5, remoteViews, i6);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    RemoteViews remoteViews2 = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partiallyUpdateHelpView(i7, remoteViews2, i8);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeCocktail(i9, i10);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showCocktail(i11, i12);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeCocktail(i13, i14, i15);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    viewDataChanged(i16, i17, i18);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPullToRefresh(i19, i20, pendingIntent, i21);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateToolLauncher(i22);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean z = parcel.readBoolean();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyKeyguardState(z, i23);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean z2 = parcel.readBoolean();
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyWakeUpState(z2, i24, i25);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    switchDefaultCocktail(i26);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i27 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendExtraData(i27, bundle);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisableTickerView(i28, i29);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean z3 = parcel.readBoolean();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeVisibleEdgeService(z3, i30);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cocktail, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void partiallyUpdateCocktail(int i, RemoteViews remoteViews, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(remoteViews, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void partiallyUpdateHelpView(int i, RemoteViews remoteViews, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(remoteViews, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void removeCocktail(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void showCocktail(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void closeCocktail(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void viewDataChanged(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void setPullToRefresh(int i, int i2, PendingIntent pendingIntent, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void updateToolLauncher(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void notifyKeyguardState(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void notifyWakeUpState(boolean z, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void switchDefaultCocktail(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void sendExtraData(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void setDisableTickerView(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void changeVisibleEdgeService(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void noteResumeComponent(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void notePauseComponent(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailHost
            public void packageSuspendChanged(Cocktail cocktail) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cocktail, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
