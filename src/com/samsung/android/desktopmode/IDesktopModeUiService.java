package com.samsung.android.desktopmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.desktopmode.IDesktopModeUiServiceCallback;

/* loaded from: classes6.dex */
public interface IDesktopModeUiService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IDesktopModeUiService";

    public static class Default implements IDesktopModeUiService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public void dismissDialog(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public void dismissOverlay(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public void finishActivity(int i) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public int getCurrentDialogType() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public int getCurrentOverlayType(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public boolean hasOverlay(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public boolean hasUiElement() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public boolean isActivityShowing(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public void removeNavBarIcon(int i) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public void removeNotification(int i) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public void showDialog(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public void showNavBarIcon(int i) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public void showNotification(int i) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public void showOverlay(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiService
        public void startActivity(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException {
        }
    }

    void dismissDialog(int i, int i2) throws RemoteException;

    void dismissOverlay(int i, int i2) throws RemoteException;

    void finishActivity(int i) throws RemoteException;

    int getCurrentDialogType() throws RemoteException;

    int getCurrentOverlayType(int i) throws RemoteException;

    boolean hasOverlay(int i, int i2) throws RemoteException;

    boolean hasUiElement() throws RemoteException;

    boolean isActivityShowing(int i) throws RemoteException;

    void removeNavBarIcon(int i) throws RemoteException;

    void removeNotification(int i) throws RemoteException;

    void showDialog(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException;

    void showNavBarIcon(int i) throws RemoteException;

    void showNotification(int i) throws RemoteException;

    void showOverlay(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException;

    void startActivity(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IDesktopModeUiService {
        static final int TRANSACTION_dismissDialog = 3;
        static final int TRANSACTION_dismissOverlay = 6;
        static final int TRANSACTION_finishActivity = 12;
        static final int TRANSACTION_getCurrentDialogType = 4;
        static final int TRANSACTION_getCurrentOverlayType = 7;
        static final int TRANSACTION_hasOverlay = 8;
        static final int TRANSACTION_hasUiElement = 1;
        static final int TRANSACTION_isActivityShowing = 13;
        static final int TRANSACTION_removeNavBarIcon = 15;
        static final int TRANSACTION_removeNotification = 10;
        static final int TRANSACTION_showDialog = 2;
        static final int TRANSACTION_showNavBarIcon = 14;
        static final int TRANSACTION_showNotification = 9;
        static final int TRANSACTION_showOverlay = 5;
        static final int TRANSACTION_startActivity = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }

        public Stub() {
            attachInterface(this, IDesktopModeUiService.DESCRIPTOR);
        }

        public static IDesktopModeUiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDesktopModeUiService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDesktopModeUiService)) {
                return (IDesktopModeUiService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "hasUiElement";
                case 2:
                    return "showDialog";
                case 3:
                    return "dismissDialog";
                case 4:
                    return "getCurrentDialogType";
                case 5:
                    return "showOverlay";
                case 6:
                    return "dismissOverlay";
                case 7:
                    return "getCurrentOverlayType";
                case 8:
                    return "hasOverlay";
                case 9:
                    return "showNotification";
                case 10:
                    return "removeNotification";
                case 11:
                    return "startActivity";
                case 12:
                    return "finishActivity";
                case 13:
                    return "isActivityShowing";
                case 14:
                    return "showNavBarIcon";
                case 15:
                    return "removeNavBarIcon";
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
                parcel.enforceInterface(IDesktopModeUiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDesktopModeUiService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean hasUiElement = hasUiElement();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUiElement);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    IDesktopModeUiServiceCallback asInterface = IDesktopModeUiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    showDialog(readInt, readInt2, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dismissDialog(readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int currentDialogType = getCurrentDialogType();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentDialogType);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    IDesktopModeUiServiceCallback asInterface2 = IDesktopModeUiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    showOverlay(readInt5, readInt6, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dismissOverlay(readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentOverlayType = getCurrentOverlayType(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentOverlayType);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasOverlay = hasOverlay(readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasOverlay);
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showNotification(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeNotification(readInt13);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    IDesktopModeUiServiceCallback asInterface3 = IDesktopModeUiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startActivity(readInt14, readInt15, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishActivity(readInt16);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isActivityShowing = isActivityShowing(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivityShowing);
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showNavBarIcon(readInt18);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeNavBarIcon(readInt19);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDesktopModeUiService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDesktopModeUiService.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public boolean hasUiElement() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void showDialog(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iDesktopModeUiServiceCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void dismissDialog(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public int getCurrentDialogType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void showOverlay(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iDesktopModeUiServiceCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void dismissOverlay(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public int getCurrentOverlayType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public boolean hasOverlay(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void showNotification(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void removeNotification(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void startActivity(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iDesktopModeUiServiceCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void finishActivity(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public boolean isActivityShowing(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void showNavBarIcon(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void removeNavBarIcon(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
