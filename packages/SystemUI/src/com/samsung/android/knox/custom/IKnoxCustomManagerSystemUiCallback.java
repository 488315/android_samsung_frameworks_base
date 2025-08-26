package com.samsung.android.knox.custom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(statusbarIconItem, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setChargerConnectionSoundEnabledState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setHardKeyIntentState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setHideNotificationMessages(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setLockScreenHiddenItems(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setLockScreenOverrideMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setQuickPanelButtonUsers(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setQuickPanelButtons(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setQuickPanelEditMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setQuickPanelItems(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setQuickPanelUnavailableButtons(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setScreenOffOnStatusBarDoubleTapState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setStatusBarHidden(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setStatusBarIconsState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setStatusBarNotificationsState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setStatusBarTextInfo(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setUnlockSimOnBootState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setUnlockSimPin(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
            public void setVolumePanelEnabledState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxCustomManagerSystemUiCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKnoxCustomManagerSystemUiCallback)) ? new Proxy(iBinder) : (IKnoxCustomManagerSystemUiCallback) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockScreenHiddenItems(i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockScreenOverrideMode(i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setQuickPanelButtons(i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setQuickPanelEditMode(i6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setQuickPanelItems(string);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setQuickPanelUnavailableButtons(string2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setScreenOffOnStatusBarDoubleTapState(z);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string3 = parcel.readString();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setStatusBarTextInfo(string3, i7, i8, i9);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStatusBarIconsState(z2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    StatusbarIconItem statusbarIconItem = (StatusbarIconItem) parcel.readTypedObject(StatusbarIconItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    setBatteryLevelColourItem(statusbarIconItem);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHideNotificationMessages(i10);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStatusBarNotificationsState(z3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUnlockSimOnBootState(z4);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUnlockSimPin(string4);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setChargerConnectionSoundEnabledState(z5);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVolumePanelEnabledState(z6);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStatusBarHidden(z7);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setQuickPanelButtonUsers(z8);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHardKeyIntentState(z9);
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
