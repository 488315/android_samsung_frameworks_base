package com.samsung.android.knox.hdm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public interface IHdmManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.hdm.IHdmManager";

    public class Default implements IHdmManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public String generateNonce(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public String getHdmId(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public String getHdmIdB64(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public String getHdmPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public String getRevocationInfo(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public boolean isNFCBlockedByHDM(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public boolean isSwBlockEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public int removeRevocationInfo(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public String setHdmPolicy(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public int setHdmTaCmd(ContextInfo contextInfo, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public boolean setSwBlock(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public String syncRevocationInfo(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.hdm.IHdmManager
        public int syncSwBlockFromBoot() throws RemoteException {
            return 0;
        }
    }

    String generateNonce(ContextInfo contextInfo) throws RemoteException;

    String getHdmId(ContextInfo contextInfo, String str) throws RemoteException;

    String getHdmIdB64(ContextInfo contextInfo) throws RemoteException;

    String getHdmPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    String getRevocationInfo(ContextInfo contextInfo) throws RemoteException;

    boolean isNFCBlockedByHDM(ContextInfo contextInfo) throws RemoteException;

    boolean isSwBlockEnabled(ContextInfo contextInfo) throws RemoteException;

    int removeRevocationInfo(ContextInfo contextInfo) throws RemoteException;

    String setHdmPolicy(ContextInfo contextInfo, String str) throws RemoteException;

    int setHdmTaCmd(ContextInfo contextInfo, int i) throws RemoteException;

    boolean setSwBlock(ContextInfo contextInfo, boolean z) throws RemoteException;

    String syncRevocationInfo(ContextInfo contextInfo, String str) throws RemoteException;

    int syncSwBlockFromBoot() throws RemoteException;

    public abstract class Stub extends Binder implements IHdmManager {
        public static final int TRANSACTION_generateNonce = 10;
        public static final int TRANSACTION_getHdmId = 2;
        public static final int TRANSACTION_getHdmIdB64 = 3;
        public static final int TRANSACTION_getHdmPolicy = 4;
        public static final int TRANSACTION_getRevocationInfo = 11;
        public static final int TRANSACTION_isNFCBlockedByHDM = 9;
        public static final int TRANSACTION_isSwBlockEnabled = 6;
        public static final int TRANSACTION_removeRevocationInfo = 13;
        public static final int TRANSACTION_setHdmPolicy = 1;
        public static final int TRANSACTION_setHdmTaCmd = 5;
        public static final int TRANSACTION_setSwBlock = 7;
        public static final int TRANSACTION_syncRevocationInfo = 12;
        public static final int TRANSACTION_syncSwBlockFromBoot = 8;

        class Proxy implements IHdmManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public String generateNonce(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public String getHdmId(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public String getHdmIdB64(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public String getHdmPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IHdmManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public String getRevocationInfo(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public boolean isNFCBlockedByHDM(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public boolean isSwBlockEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public int removeRevocationInfo(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public String setHdmPolicy(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public int setHdmTaCmd(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public boolean setSwBlock(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public String syncRevocationInfo(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.hdm.IHdmManager
            public int syncSwBlockFromBoot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHdmManager.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IHdmManager.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IHdmManager)) ? new Proxy(iBinder) : (IHdmManager) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setHdmPolicy";
                case 2:
                    return "getHdmId";
                case 3:
                    return "getHdmIdB64";
                case 4:
                    return "getHdmPolicy";
                case 5:
                    return "setHdmTaCmd";
                case 6:
                    return "isSwBlockEnabled";
                case 7:
                    return "setSwBlock";
                case 8:
                    return "syncSwBlockFromBoot";
                case 9:
                    return "isNFCBlockedByHDM";
                case 10:
                    return "generateNonce";
                case 11:
                    return "getRevocationInfo";
                case 12:
                    return "syncRevocationInfo";
                case 13:
                    return "removeRevocationInfo";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 12;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String hdmPolicy = setHdmPolicy(contextInfo, string);
                    parcel2.writeNoException();
                    parcel2.writeString(hdmPolicy);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String hdmId = getHdmId(contextInfo2, string2);
                    parcel2.writeNoException();
                    parcel2.writeString(hdmId);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String hdmIdB64 = getHdmIdB64(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeString(hdmIdB64);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String hdmPolicy2 = getHdmPolicy(contextInfo4, string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeString(hdmPolicy2);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int hdmTaCmd = setHdmTaCmd(contextInfo5, i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(hdmTaCmd);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSwBlockEnabled = isSwBlockEnabled(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSwBlockEnabled);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean swBlock = setSwBlock(contextInfo7, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(swBlock);
                    return true;
                case 8:
                    int iSyncSwBlockFromBoot = syncSwBlockFromBoot();
                    parcel2.writeNoException();
                    parcel2.writeInt(iSyncSwBlockFromBoot);
                    return true;
                case 9:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsNFCBlockedByHDM = isNFCBlockedByHDM(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNFCBlockedByHDM);
                    return true;
                case 10:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String strGenerateNonce = generateNonce(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeString(strGenerateNonce);
                    return true;
                case 11:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String revocationInfo = getRevocationInfo(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeString(revocationInfo);
                    return true;
                case 12:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strSyncRevocationInfo = syncRevocationInfo(contextInfo11, string5);
                    parcel2.writeNoException();
                    parcel2.writeString(strSyncRevocationInfo);
                    return true;
                case 13:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRemoveRevocationInfo = removeRevocationInfo(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveRevocationInfo);
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
