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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDesktopModeUiService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDesktopModeUiService)) {
                return (IDesktopModeUiService) iInterfaceQueryLocalInterface;
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
                    boolean zHasUiElement = hasUiElement();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasUiElement);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    IDesktopModeUiServiceCallback iDesktopModeUiServiceCallbackAsInterface = IDesktopModeUiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    showDialog(i3, i4, iDesktopModeUiServiceCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dismissDialog(i5, i6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int currentDialogType = getCurrentDialogType();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentDialogType);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    IDesktopModeUiServiceCallback iDesktopModeUiServiceCallbackAsInterface2 = IDesktopModeUiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    showOverlay(i7, i8, iDesktopModeUiServiceCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dismissOverlay(i9, i10);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentOverlayType = getCurrentOverlayType(i11);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentOverlayType);
                    return true;
                case 8:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasOverlay = hasOverlay(i12, i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasOverlay);
                    return true;
                case 9:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showNotification(i14);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeNotification(i15);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    IDesktopModeUiServiceCallback iDesktopModeUiServiceCallbackAsInterface3 = IDesktopModeUiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startActivity(i16, i17, iDesktopModeUiServiceCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishActivity(i18);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsActivityShowing = isActivityShowing(i19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsActivityShowing);
                    return true;
                case 14:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showNavBarIcon(i20);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeNavBarIcon(i21);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void showDialog(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iDesktopModeUiServiceCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void dismissDialog(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public int getCurrentDialogType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void showOverlay(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iDesktopModeUiServiceCallback);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void dismissOverlay(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public int getCurrentOverlayType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public boolean hasOverlay(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void showNotification(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void removeNotification(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void startActivity(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iDesktopModeUiServiceCallback);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void finishActivity(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public boolean isActivityShowing(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void showNavBarIcon(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiService
            public void removeNavBarIcon(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
