package com.samsung.android.knox.lockscreen;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ILockscreenOverlay extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.lockscreen.ILockscreenOverlay";

    boolean canConfigure(ContextInfo contextInfo, int i);

    LSOItemData getData(ContextInfo contextInfo, int i);

    LSOAttributeSet getPreferences(ContextInfo contextInfo);

    boolean isConfigured(ContextInfo contextInfo, int i);

    void resetData(ContextInfo contextInfo, int i);

    void resetWallpaper(ContextInfo contextInfo);

    int setData(ContextInfo contextInfo, LSOItemData lSOItemData, int i);

    int setPreferences(ContextInfo contextInfo, LSOAttributeSet lSOAttributeSet);

    int setWallpaper(ContextInfo contextInfo, String str, ParcelFileDescriptor parcelFileDescriptor);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements ILockscreenOverlay {
        public static final int TRANSACTION_canConfigure = 5;
        public static final int TRANSACTION_getData = 2;
        public static final int TRANSACTION_getPreferences = 9;
        public static final int TRANSACTION_isConfigured = 4;
        public static final int TRANSACTION_resetData = 3;
        public static final int TRANSACTION_resetWallpaper = 7;
        public static final int TRANSACTION_setData = 1;
        public static final int TRANSACTION_setPreferences = 8;
        public static final int TRANSACTION_setWallpaper = 6;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements ILockscreenOverlay {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public final boolean canConfigure(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public final LSOItemData getData(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LSOItemData) obtain2.readTypedObject(LSOItemData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return ILockscreenOverlay.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public final LSOAttributeSet getPreferences(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LSOAttributeSet) obtain2.readTypedObject(LSOAttributeSet.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public final boolean isConfigured(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public final void resetData(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public final void resetWallpaper(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public final int setData(ContextInfo contextInfo, LSOItemData lSOItemData, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(lSOItemData, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public final int setPreferences(ContextInfo contextInfo, LSOAttributeSet lSOAttributeSet) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(lSOAttributeSet, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public final int setWallpaper(ContextInfo contextInfo, String str, ParcelFileDescriptor parcelFileDescriptor) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ILockscreenOverlay.DESCRIPTOR);
        }

        public static ILockscreenOverlay asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILockscreenOverlay.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ILockscreenOverlay)) ? new Proxy(iBinder) : (ILockscreenOverlay) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILockscreenOverlay.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILockscreenOverlay.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    LSOItemData lSOItemData = (LSOItemData) parcel.readTypedObject(LSOItemData.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int data = setData(contextInfo, lSOItemData, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(data);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    LSOItemData data2 = getData(contextInfo2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(data2, 1);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetData(contextInfo3, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isConfigured = isConfigured(contextInfo4, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isConfigured);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canConfigure = canConfigure(contextInfo5, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canConfigure);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    int wallpaper = setWallpaper(contextInfo6, readString, parcelFileDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeInt(wallpaper);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetWallpaper(contextInfo7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    LSOAttributeSet lSOAttributeSet = (LSOAttributeSet) parcel.readTypedObject(LSOAttributeSet.CREATOR);
                    parcel.enforceNoDataAvail();
                    int preferences = setPreferences(contextInfo8, lSOAttributeSet);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferences);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    LSOAttributeSet preferences2 = getPreferences(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(preferences2, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements ILockscreenOverlay {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public final boolean canConfigure(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public final LSOItemData getData(ContextInfo contextInfo, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public final LSOAttributeSet getPreferences(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public final boolean isConfigured(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public final int setData(ContextInfo contextInfo, LSOItemData lSOItemData, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public final int setPreferences(ContextInfo contextInfo, LSOAttributeSet lSOAttributeSet) {
            return 0;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public final int setWallpaper(ContextInfo contextInfo, String str, ParcelFileDescriptor parcelFileDescriptor) {
            return 0;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public final void resetWallpaper(ContextInfo contextInfo) {
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public final void resetData(ContextInfo contextInfo, int i) {
        }
    }
}
