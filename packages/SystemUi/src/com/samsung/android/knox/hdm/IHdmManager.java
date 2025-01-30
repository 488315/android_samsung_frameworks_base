package com.samsung.android.knox.hdm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IHdmManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.hdm.IHdmManager";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IHdmManager {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public final String getHdmId(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public final String getHdmPolicy(ContextInfo contextInfo, String str, String str2) {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public final boolean isNFCBlockedByHDM(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public final boolean isSwBlockEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public final String setHdmPolicy(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public final int setHdmTaCmd(ContextInfo contextInfo, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public final boolean setSwBlock(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public final int syncSwBlockFromBoot() {
            return 0;
        }
    }

    String getHdmId(ContextInfo contextInfo, String str);

    String getHdmPolicy(ContextInfo contextInfo, String str, String str2);

    boolean isNFCBlockedByHDM(ContextInfo contextInfo);

    boolean isSwBlockEnabled(ContextInfo contextInfo);

    String setHdmPolicy(ContextInfo contextInfo, String str);

    int setHdmTaCmd(ContextInfo contextInfo, int i);

    boolean setSwBlock(ContextInfo contextInfo, boolean z);

    int syncSwBlockFromBoot();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IHdmManager {
        public static final int TRANSACTION_getHdmId = 2;
        public static final int TRANSACTION_getHdmPolicy = 3;
        public static final int TRANSACTION_isNFCBlockedByHDM = 8;
        public static final int TRANSACTION_isSwBlockEnabled = 5;
        public static final int TRANSACTION_setHdmPolicy = 1;
        public static final int TRANSACTION_setHdmTaCmd = 4;
        public static final int TRANSACTION_setSwBlock = 6;
        public static final int TRANSACTION_syncSwBlockFromBoot = 7;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IHdmManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public final String getHdmId(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public final String getHdmPolicy(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IHdmManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public final boolean isNFCBlockedByHDM(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public final boolean isSwBlockEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public final String setHdmPolicy(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public final int setHdmTaCmd(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public final boolean setSwBlock(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public final int syncSwBlockFromBoot() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IHdmManager.DESCRIPTOR);
        }

        public static IHdmManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IHdmManager.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IHdmManager)) ? new Proxy(iBinder) : (IHdmManager) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setHdmPolicy";
                case 2:
                    return "getHdmId";
                case 3:
                    return "getHdmPolicy";
                case 4:
                    return "setHdmTaCmd";
                case 5:
                    return "isSwBlockEnabled";
                case 6:
                    return "setSwBlock";
                case 7:
                    return "syncSwBlockFromBoot";
                case 8:
                    return "isNFCBlockedByHDM";
                default:
                    return null;
            }
        }

        public final int getMaxTransactionId() {
            return 7;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IHdmManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IHdmManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String hdmPolicy = setHdmPolicy(contextInfo, readString);
                    parcel2.writeNoException();
                    parcel2.writeString(hdmPolicy);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String hdmId = getHdmId(contextInfo2, readString2);
                    parcel2.writeNoException();
                    parcel2.writeString(hdmId);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String hdmPolicy2 = getHdmPolicy(contextInfo3, readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeString(hdmPolicy2);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int hdmTaCmd = setHdmTaCmd(contextInfo4, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(hdmTaCmd);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSwBlockEnabled = isSwBlockEnabled(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSwBlockEnabled);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean swBlock = setSwBlock(contextInfo6, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(swBlock);
                    return true;
                case 7:
                    int syncSwBlockFromBoot = syncSwBlockFromBoot();
                    parcel2.writeNoException();
                    parcel2.writeInt(syncSwBlockFromBoot);
                    return true;
                case 8:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isNFCBlockedByHDM = isNFCBlockedByHDM(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNFCBlockedByHDM);
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
}
