package com.samsung.android.knox.custom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IKnoxCustomManagerSystemUiCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback";

    void setBatteryLevelColourItem(StatusbarIconItem statusbarIconItem) throws RemoteException;

    void setChargerConnectionSoundEnabledState(boolean z) throws RemoteException;

    void setHardKeyIntentState(boolean z) throws RemoteException;

    void setHideNotificationMessages(int i) throws RemoteException;

    void setLockScreenHiddenItems(int i) throws RemoteException;

    void setLockScreenOverrideMode(int i) throws RemoteException;

    void setQuickPanelButtonUsers(boolean z) throws RemoteException;

    void setQuickPanelButtons(int i) throws RemoteException;

    void setQuickPanelEditMode(int i) throws RemoteException;

    void setQuickPanelItems(String str) throws RemoteException;

    void setQuickPanelUnavailableButtons(String str) throws RemoteException;

    void setScreenOffOnStatusBarDoubleTapState(boolean z) throws RemoteException;

    void setStatusBarHidden(boolean z) throws RemoteException;

    void setStatusBarIconsState(boolean z) throws RemoteException;

    void setStatusBarNotificationsState(boolean z) throws RemoteException;

    void setStatusBarTextInfo(String str, int i, int i2, int i3) throws RemoteException;

    void setUnlockSimOnBootState(boolean z) throws RemoteException;

    void setUnlockSimPin(String str) throws RemoteException;

    void setVolumePanelEnabledState(boolean z) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IKnoxCustomManagerSystemUiCallback {
        public static final int TRANSACTION_setBatteryLevelColourItem = 10;
        public static final int TRANSACTION_setChargerConnectionSoundEnabledState = 15;
        public static final int TRANSACTION_setHardKeyIntentState = 19;
        public static final int TRANSACTION_setHideNotificationMessages = 11;
        public static final int TRANSACTION_setLockScreenHiddenItems = 1;
        public static final int TRANSACTION_setLockScreenOverrideMode = 2;
        public static final int TRANSACTION_setQuickPanelButtonUsers = 18;
        public static final int TRANSACTION_setQuickPanelButtons = 3;
        public static final int TRANSACTION_setQuickPanelEditMode = 4;
        public static final int TRANSACTION_setQuickPanelItems = 5;
        public static final int TRANSACTION_setQuickPanelUnavailableButtons = 6;
        public static final int TRANSACTION_setScreenOffOnStatusBarDoubleTapState = 7;
        public static final int TRANSACTION_setStatusBarHidden = 17;
        public static final int TRANSACTION_setStatusBarIconsState = 9;
        public static final int TRANSACTION_setStatusBarNotificationsState = 12;
        public static final int TRANSACTION_setStatusBarTextInfo = 8;
        public static final int TRANSACTION_setUnlockSimOnBootState = 13;
        public static final int TRANSACTION_setUnlockSimPin = 14;
        public static final int TRANSACTION_setVolumePanelEnabledState = 16;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IKnoxCustomManagerSystemUiCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKnoxCustomManagerSystemUiCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setBatteryLevelColourItem(StatusbarIconItem statusbarIconItem) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeTypedObject(statusbarIconItem, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setChargerConnectionSoundEnabledState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setHardKeyIntentState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setHideNotificationMessages(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setLockScreenHiddenItems(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setLockScreenOverrideMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setQuickPanelButtonUsers(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setQuickPanelButtons(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setQuickPanelEditMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setQuickPanelItems(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setQuickPanelUnavailableButtons(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setScreenOffOnStatusBarDoubleTapState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setStatusBarHidden(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setStatusBarIconsState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setStatusBarNotificationsState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setStatusBarTextInfo(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setUnlockSimOnBootState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setUnlockSimPin(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setVolumePanelEnabledState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
        }

        public static IKnoxCustomManagerSystemUiCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKnoxCustomManagerSystemUiCallback)) ? new Proxy(iBinder) : (IKnoxCustomManagerSystemUiCallback) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setLockScreenHiddenItems";
                case 2:
                    return "setLockScreenOverrideMode";
                case 3:
                    return "setQuickPanelButtons";
                case 4:
                    return "setQuickPanelEditMode";
                case 5:
                    return "setQuickPanelItems";
                case 6:
                    return "setQuickPanelUnavailableButtons";
                case 7:
                    return "setScreenOffOnStatusBarDoubleTapState";
                case 8:
                    return "setStatusBarTextInfo";
                case 9:
                    return "setStatusBarIconsState";
                case 10:
                    return "setBatteryLevelColourItem";
                case 11:
                    return "setHideNotificationMessages";
                case 12:
                    return "setStatusBarNotificationsState";
                case 13:
                    return "setUnlockSimOnBootState";
                case 14:
                    return "setUnlockSimPin";
                case 15:
                    return "setChargerConnectionSoundEnabledState";
                case 16:
                    return "setVolumePanelEnabledState";
                case 17:
                    return "setStatusBarHidden";
                case 18:
                    return "setQuickPanelButtonUsers";
                case 19:
                    return "setHardKeyIntentState";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 18;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockScreenHiddenItems(readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockScreenOverrideMode(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setQuickPanelButtons(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setQuickPanelEditMode(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setQuickPanelItems(readString);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setQuickPanelUnavailableButtons(readString2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setScreenOffOnStatusBarDoubleTapState(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString3 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setStatusBarTextInfo(readString3, readInt5, readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStatusBarIconsState(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    StatusbarIconItem statusbarIconItem = (StatusbarIconItem) parcel.readTypedObject(StatusbarIconItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    setBatteryLevelColourItem(statusbarIconItem);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHideNotificationMessages(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStatusBarNotificationsState(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUnlockSimOnBootState(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUnlockSimPin(readString4);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setChargerConnectionSoundEnabledState(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVolumePanelEnabledState(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStatusBarHidden(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setQuickPanelButtonUsers(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHardKeyIntentState(readBoolean9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IKnoxCustomManagerSystemUiCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setBatteryLevelColourItem(StatusbarIconItem statusbarIconItem) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setChargerConnectionSoundEnabledState(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setHardKeyIntentState(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setHideNotificationMessages(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setLockScreenHiddenItems(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setLockScreenOverrideMode(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setQuickPanelButtonUsers(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setQuickPanelButtons(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setQuickPanelEditMode(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setQuickPanelItems(String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setQuickPanelUnavailableButtons(String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setScreenOffOnStatusBarDoubleTapState(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setStatusBarHidden(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setStatusBarIconsState(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setStatusBarNotificationsState(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setUnlockSimOnBootState(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setUnlockSimPin(String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setVolumePanelEnabledState(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
        public void setStatusBarTextInfo(String str, int i, int i2, int i3) throws RemoteException {
        }
    }
}
