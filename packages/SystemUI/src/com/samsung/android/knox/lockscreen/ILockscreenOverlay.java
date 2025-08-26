package com.samsung.android.knox.lockscreen;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public interface ILockscreenOverlay extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.lockscreen.ILockscreenOverlay";

    boolean canConfigure(ContextInfo contextInfo, int i) throws RemoteException;

    LSOItemData getData(ContextInfo contextInfo, int i) throws RemoteException;

    LSOAttributeSet getPreferences(ContextInfo contextInfo) throws RemoteException;

    boolean isConfigured(ContextInfo contextInfo, int i) throws RemoteException;

    void resetData(ContextInfo contextInfo, int i) throws RemoteException;

    void resetWallpaper(ContextInfo contextInfo) throws RemoteException;

    int setData(ContextInfo contextInfo, LSOItemData lSOItemData, int i) throws RemoteException;

    int setPreferences(ContextInfo contextInfo, LSOAttributeSet lSOAttributeSet) throws RemoteException;

    int setWallpaper(ContextInfo contextInfo, String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    public abstract class Stub extends Binder implements ILockscreenOverlay {
        public static final int TRANSACTION_canConfigure = 5;
        public static final int TRANSACTION_getData = 2;
        public static final int TRANSACTION_getPreferences = 9;
        public static final int TRANSACTION_isConfigured = 4;
        public static final int TRANSACTION_resetData = 3;
        public static final int TRANSACTION_resetWallpaper = 7;
        public static final int TRANSACTION_setData = 1;
        public static final int TRANSACTION_setPreferences = 8;
        public static final int TRANSACTION_setWallpaper = 6;

        class Proxy implements ILockscreenOverlay {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public boolean canConfigure(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public LSOItemData getData(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LSOItemData) parcelObtain2.readTypedObject(LSOItemData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ILockscreenOverlay.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public LSOAttributeSet getPreferences(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LSOAttributeSet) parcelObtain2.readTypedObject(LSOAttributeSet.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public boolean isConfigured(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public void resetData(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public void resetWallpaper(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public int setData(ContextInfo contextInfo, LSOItemData lSOItemData, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(lSOItemData, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public int setPreferences(ContextInfo contextInfo, LSOAttributeSet lSOAttributeSet) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(lSOAttributeSet, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
            public int setWallpaper(ContextInfo contextInfo, String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILockscreenOverlay.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ILockscreenOverlay.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ILockscreenOverlay)) ? new Proxy(iBinder) : (ILockscreenOverlay) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int data = setData(contextInfo, lSOItemData, i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(data);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    LSOItemData data2 = getData(contextInfo2, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(data2, 1);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetData(contextInfo3, i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsConfigured = isConfigured(contextInfo4, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsConfigured);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCanConfigure = canConfigure(contextInfo5, i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanConfigure);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    int wallpaper = setWallpaper(contextInfo6, string, parcelFileDescriptor);
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
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements ILockscreenOverlay {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public boolean canConfigure(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public LSOItemData getData(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public LSOAttributeSet getPreferences(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public boolean isConfigured(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public int setData(ContextInfo contextInfo, LSOItemData lSOItemData, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public int setPreferences(ContextInfo contextInfo, LSOAttributeSet lSOAttributeSet) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public int setWallpaper(ContextInfo contextInfo, String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public void resetWallpaper(ContextInfo contextInfo) throws RemoteException {
        }

        @Override // com.samsung.android.knox.lockscreen.ILockscreenOverlay
        public void resetData(ContextInfo contextInfo, int i) throws RemoteException {
        }
    }
}
