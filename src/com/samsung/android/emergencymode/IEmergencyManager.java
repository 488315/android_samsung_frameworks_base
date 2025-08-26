package com.samsung.android.emergencymode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IEmergencyManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.emergencymode.IEmergencyManager";

    public static class Default implements IEmergencyManager {
        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public boolean addAppToLauncher(String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public boolean checkInvalidBroadcast(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public boolean checkInvalidProcess(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public boolean checkModeType(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public boolean checkValidIntentAction(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public boolean checkValidPackage(String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public int getEmergencyState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public boolean isEmergencyMode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public boolean isModifying() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public boolean isScreenOn() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public boolean isUserPackageBlocked() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public boolean needMobileDataBlock() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public void setLocationProviderEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.emergencymode.IEmergencyManager
        public void setUserPackageBlocked(boolean z) throws RemoteException {
        }
    }

    boolean addAppToLauncher(String str, boolean z) throws RemoteException;

    boolean checkInvalidBroadcast(String str, String str2) throws RemoteException;

    boolean checkInvalidProcess(String str) throws RemoteException;

    boolean checkModeType(int i) throws RemoteException;

    boolean checkValidIntentAction(String str, String str2) throws RemoteException;

    boolean checkValidPackage(String str, String str2, int i) throws RemoteException;

    int getEmergencyState() throws RemoteException;

    boolean isEmergencyMode() throws RemoteException;

    boolean isModifying() throws RemoteException;

    boolean isScreenOn() throws RemoteException;

    boolean isUserPackageBlocked() throws RemoteException;

    boolean needMobileDataBlock() throws RemoteException;

    void setLocationProviderEnabled(boolean z) throws RemoteException;

    void setUserPackageBlocked(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IEmergencyManager {
        static final int TRANSACTION_addAppToLauncher = 13;
        static final int TRANSACTION_checkInvalidBroadcast = 6;
        static final int TRANSACTION_checkInvalidProcess = 5;
        static final int TRANSACTION_checkModeType = 14;
        static final int TRANSACTION_checkValidIntentAction = 4;
        static final int TRANSACTION_checkValidPackage = 3;
        static final int TRANSACTION_getEmergencyState = 2;
        static final int TRANSACTION_isEmergencyMode = 1;
        static final int TRANSACTION_isModifying = 11;
        static final int TRANSACTION_isScreenOn = 8;
        static final int TRANSACTION_isUserPackageBlocked = 10;
        static final int TRANSACTION_needMobileDataBlock = 7;
        static final int TRANSACTION_setLocationProviderEnabled = 12;
        static final int TRANSACTION_setUserPackageBlocked = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, IEmergencyManager.DESCRIPTOR);
        }

        public static IEmergencyManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEmergencyManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEmergencyManager)) {
                return (IEmergencyManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isEmergencyMode";
                case 2:
                    return "getEmergencyState";
                case 3:
                    return "checkValidPackage";
                case 4:
                    return "checkValidIntentAction";
                case 5:
                    return "checkInvalidProcess";
                case 6:
                    return "checkInvalidBroadcast";
                case 7:
                    return "needMobileDataBlock";
                case 8:
                    return "isScreenOn";
                case 9:
                    return "setUserPackageBlocked";
                case 10:
                    return "isUserPackageBlocked";
                case 11:
                    return "isModifying";
                case 12:
                    return "setLocationProviderEnabled";
                case 13:
                    return "addAppToLauncher";
                case 14:
                    return "checkModeType";
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
                parcel.enforceInterface(IEmergencyManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEmergencyManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean zIsEmergencyMode = isEmergencyMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEmergencyMode);
                    return true;
                case 2:
                    int emergencyState = getEmergencyState();
                    parcel2.writeNoException();
                    parcel2.writeInt(emergencyState);
                    return true;
                case 3:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckValidPackage = checkValidPackage(string, string2, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckValidPackage);
                    return true;
                case 4:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCheckValidIntentAction = checkValidIntentAction(string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckValidIntentAction);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCheckInvalidProcess = checkInvalidProcess(string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckInvalidProcess);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCheckInvalidBroadcast = checkInvalidBroadcast(string6, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckInvalidBroadcast);
                    return true;
                case 7:
                    boolean zNeedMobileDataBlock = needMobileDataBlock();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zNeedMobileDataBlock);
                    return true;
                case 8:
                    boolean zIsScreenOn = isScreenOn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenOn);
                    return true;
                case 9:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUserPackageBlocked(z);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean zIsUserPackageBlocked = isUserPackageBlocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserPackageBlocked);
                    return true;
                case 11:
                    boolean zIsModifying = isModifying();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsModifying);
                    return true;
                case 12:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLocationProviderEnabled(z2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string8 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAddAppToLauncher = addAppToLauncher(string8, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAppToLauncher);
                    return true;
                case 14:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckModeType = checkModeType(i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckModeType);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IEmergencyManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEmergencyManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public boolean isEmergencyMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public int getEmergencyState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public boolean checkValidPackage(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public boolean checkValidIntentAction(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public boolean checkInvalidProcess(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public boolean checkInvalidBroadcast(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public boolean needMobileDataBlock() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public boolean isScreenOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public void setUserPackageBlocked(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public boolean isUserPackageBlocked() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public boolean isModifying() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public void setLocationProviderEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public boolean addAppToLauncher(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.emergencymode.IEmergencyManager
            public boolean checkModeType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
