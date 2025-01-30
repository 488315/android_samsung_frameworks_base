package com.samsung.android.desktopsystemui.sharedlib.common;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface IDesktopBar extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar";

    void closePanel(String str);

    void notifyPrivacyItemsChanged(boolean z);

    void onDismiss();

    void onKeyguardWallpaperChanged();

    void onShow(int i);

    void onUpdate(int i, Bundle bundle);

    void openPanel(String str);

    void setAirplaneMode(boolean z, int i);

    void setBtTetherIcon(boolean z, int i);

    void setConnectedDeviceListForGroup(Bundle bundle);

    void setDesktopBarCallback(IDesktopBarCallback iDesktopBarCallback);

    void setLockout(boolean z, Bundle bundle);

    void setMPTCPIcon(boolean z, int i, int i2, int i3);

    void setMobileIcon(Bundle bundle);

    void setOccluded(boolean z);

    void setSubs();

    void setWifiIcon(boolean z, int i, int i2);

    void start();

    void stop();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IDesktopBar {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void closePanel(String str) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void notifyPrivacyItemsChanged(boolean z) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void onShow(int i) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void openPanel(String str) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void setConnectedDeviceListForGroup(Bundle bundle) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void setDesktopBarCallback(IDesktopBarCallback iDesktopBarCallback) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void setMobileIcon(Bundle bundle) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void setOccluded(boolean z) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void onDismiss() {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void onKeyguardWallpaperChanged() {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void setSubs() {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void start() {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void stop() {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void onUpdate(int i, Bundle bundle) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void setAirplaneMode(boolean z, int i) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void setBtTetherIcon(boolean z, int i) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void setLockout(boolean z, Bundle bundle) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void setWifiIcon(boolean z, int i, int i2) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
        public void setMPTCPIcon(boolean z, int i, int i2, int i3) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IDesktopBar {
        static final int TRANSACTION_closePanel = 5;
        static final int TRANSACTION_notifyPrivacyItemsChanged = 13;
        static final int TRANSACTION_onDismiss = 15;
        static final int TRANSACTION_onKeyguardWallpaperChanged = 17;
        static final int TRANSACTION_onShow = 14;
        static final int TRANSACTION_onUpdate = 16;
        static final int TRANSACTION_openPanel = 4;
        static final int TRANSACTION_setAirplaneMode = 8;
        static final int TRANSACTION_setBtTetherIcon = 9;
        static final int TRANSACTION_setConnectedDeviceListForGroup = 12;
        static final int TRANSACTION_setDesktopBarCallback = 1;
        static final int TRANSACTION_setLockout = 18;
        static final int TRANSACTION_setMPTCPIcon = 10;
        static final int TRANSACTION_setMobileIcon = 7;
        static final int TRANSACTION_setOccluded = 19;
        static final int TRANSACTION_setSubs = 11;
        static final int TRANSACTION_setWifiIcon = 6;
        static final int TRANSACTION_start = 2;
        static final int TRANSACTION_stop = 3;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IDesktopBar {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void closePanel(String str) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IDesktopBar.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void notifyPrivacyItemsChanged(boolean z) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void onDismiss() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void onKeyguardWallpaperChanged() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void onShow(int i) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void onUpdate(int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void openPanel(String str) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void setAirplaneMode(boolean z, int i) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void setBtTetherIcon(boolean z, int i) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void setConnectedDeviceListForGroup(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void setDesktopBarCallback(IDesktopBarCallback iDesktopBarCallback) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeStrongInterface(iDesktopBarCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void setLockout(boolean z, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void setMPTCPIcon(boolean z, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void setMobileIcon(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void setOccluded(boolean z) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void setSubs() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void setWifiIcon(boolean z, int i, int i2) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void start() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar
            public void stop() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBar.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDesktopBar.DESCRIPTOR);
        }

        public static IDesktopBar asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDesktopBar.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDesktopBar)) ? new Proxy(iBinder) : (IDesktopBar) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDesktopBar.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDesktopBar.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    setDesktopBarCallback(IDesktopBarCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 2:
                    start();
                    return true;
                case 3:
                    stop();
                    return true;
                case 4:
                    openPanel(parcel.readString());
                    return true;
                case 5:
                    closePanel(parcel.readString());
                    return true;
                case 6:
                    setWifiIcon(parcel.readInt() != 0, parcel.readInt(), parcel.readInt());
                    return true;
                case 7:
                    setMobileIcon((Bundle) parcel.readTypedObject(Bundle.CREATOR));
                    return true;
                case 8:
                    setAirplaneMode(parcel.readInt() != 0, parcel.readInt());
                    return true;
                case 9:
                    setBtTetherIcon(parcel.readInt() != 0, parcel.readInt());
                    return true;
                case 10:
                    setMPTCPIcon(parcel.readInt() != 0, parcel.readInt(), parcel.readInt(), parcel.readInt());
                    return true;
                case 11:
                    setSubs();
                    return true;
                case 12:
                    setConnectedDeviceListForGroup((Bundle) parcel.readTypedObject(Bundle.CREATOR));
                    return true;
                case 13:
                    notifyPrivacyItemsChanged(parcel.readInt() != 0);
                    return true;
                case 14:
                    onShow(parcel.readInt());
                    return true;
                case 15:
                    onDismiss();
                    return true;
                case 16:
                    onUpdate(parcel.readInt(), (Bundle) parcel.readTypedObject(Bundle.CREATOR));
                    return true;
                case 17:
                    onKeyguardWallpaperChanged();
                    return true;
                case 18:
                    setLockout(parcel.readInt() != 0, (Bundle) parcel.readTypedObject(Bundle.CREATOR));
                    return true;
                case 19:
                    setOccluded(parcel.readInt() != 0);
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
}
