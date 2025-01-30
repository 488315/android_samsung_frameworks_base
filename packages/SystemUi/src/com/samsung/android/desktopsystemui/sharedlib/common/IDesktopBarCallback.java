package com.samsung.android.desktopsystemui.sharedlib.common;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface IDesktopBarCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback";

    void getConnectedDeviceListForGroup();

    int getFailedUnlockAttempt();

    long getLockoutAttemptDeadline();

    int getRemainingAttemptBeforeWipe();

    void requestPrivacyItems();

    void requestStatusIcons();

    void requestUnlock(String str);

    void showControls();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IDesktopBarCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
        public int getFailedUnlockAttempt() {
            return 0;
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
        public long getLockoutAttemptDeadline() {
            return 0L;
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
        public int getRemainingAttemptBeforeWipe() {
            return 0;
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
        public void requestUnlock(String str) {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
        public void getConnectedDeviceListForGroup() {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
        public void requestPrivacyItems() {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
        public void requestStatusIcons() {
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
        public void showControls() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IDesktopBarCallback {
        static final int TRANSACTION_getConnectedDeviceListForGroup = 8;
        static final int TRANSACTION_getFailedUnlockAttempt = 3;
        static final int TRANSACTION_getLockoutAttemptDeadline = 2;
        static final int TRANSACTION_getRemainingAttemptBeforeWipe = 4;
        static final int TRANSACTION_requestPrivacyItems = 7;
        static final int TRANSACTION_requestStatusIcons = 5;
        static final int TRANSACTION_requestUnlock = 1;
        static final int TRANSACTION_showControls = 6;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IDesktopBarCallback {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public void getConnectedDeviceListForGroup() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBarCallback.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public int getFailedUnlockAttempt() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBarCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IDesktopBarCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public long getLockoutAttemptDeadline() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBarCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public int getRemainingAttemptBeforeWipe() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBarCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public void requestPrivacyItems() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBarCallback.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public void requestStatusIcons() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBarCallback.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public void requestUnlock(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBarCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public void showControls() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopBarCallback.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDesktopBarCallback.DESCRIPTOR);
        }

        public static IDesktopBarCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDesktopBarCallback.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDesktopBarCallback)) ? new Proxy(iBinder) : (IDesktopBarCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDesktopBarCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDesktopBarCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    requestUnlock(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long lockoutAttemptDeadline = getLockoutAttemptDeadline();
                    parcel2.writeNoException();
                    parcel2.writeLong(lockoutAttemptDeadline);
                    return true;
                case 3:
                    int failedUnlockAttempt = getFailedUnlockAttempt();
                    parcel2.writeNoException();
                    parcel2.writeInt(failedUnlockAttempt);
                    return true;
                case 4:
                    int remainingAttemptBeforeWipe = getRemainingAttemptBeforeWipe();
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingAttemptBeforeWipe);
                    return true;
                case 5:
                    requestStatusIcons();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    showControls();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    requestPrivacyItems();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    getConnectedDeviceListForGroup();
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
}
