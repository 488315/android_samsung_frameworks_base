package android.sec.enterprise.adapterlayer;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.sec.enterprise.content.SecContentProviderURI;

/* loaded from: classes3.dex */
public interface ISystemUIAdapterCallback extends IInterface {
    public static final String DESCRIPTOR = "android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback";

    public static class Default implements ISystemUIAdapterCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void excludeExternalStorageForFailedPasswordsWipe(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setAdminLock(boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setAirplaneModeAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setApplicationNameControlEnabled(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setBluetoothAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setCameraAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setCellularDataAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setFaceRecognitionEvenCameraBlockedAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setGPSStateChangeAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setKioskModeEnabled(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setLocationProviderAllowed(String str, boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setLockedIccIds(String[] strArr) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setLockscreenInvisibleOverlay(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setLockscreenWallpaper(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setMaximumFailedPasswordsForDisable(int i, String str) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setMaximumFailedPasswordsForProfileDisable(int i) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setMultifactorAuthEnabled(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setNFCStateChangeAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setNavigationBarHidden(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setPasswordLockDelay(int i) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setPasswordVisibilityEnabled(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setPwdChangeRequested(int i) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setRoamingAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setSettingsChangeAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setStatusBarExpansionAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setStatusBarHidden(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setWifiAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setWifiStateChangeAllowed(boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setWifiTetheringAllowed(boolean z) throws RemoteException {
        }
    }

    void excludeExternalStorageForFailedPasswordsWipe(boolean z) throws RemoteException;

    void setAdminLock(boolean z, boolean z2) throws RemoteException;

    void setAirplaneModeAllowed(boolean z) throws RemoteException;

    void setApplicationNameControlEnabled(boolean z) throws RemoteException;

    void setBluetoothAllowed(boolean z) throws RemoteException;

    void setCameraAllowed(boolean z) throws RemoteException;

    void setCellularDataAllowed(boolean z) throws RemoteException;

    void setFaceRecognitionEvenCameraBlockedAllowed(boolean z) throws RemoteException;

    void setGPSStateChangeAllowed(boolean z) throws RemoteException;

    void setKioskModeEnabled(boolean z) throws RemoteException;

    void setLocationProviderAllowed(String str, boolean z) throws RemoteException;

    void setLockedIccIds(String[] strArr) throws RemoteException;

    void setLockscreenInvisibleOverlay(boolean z) throws RemoteException;

    void setLockscreenWallpaper(boolean z) throws RemoteException;

    void setMaximumFailedPasswordsForDisable(int i, String str) throws RemoteException;

    void setMaximumFailedPasswordsForProfileDisable(int i) throws RemoteException;

    void setMultifactorAuthEnabled(boolean z) throws RemoteException;

    void setNFCStateChangeAllowed(boolean z) throws RemoteException;

    void setNavigationBarHidden(boolean z) throws RemoteException;

    void setPasswordLockDelay(int i) throws RemoteException;

    void setPasswordVisibilityEnabled(boolean z) throws RemoteException;

    void setPwdChangeRequested(int i) throws RemoteException;

    void setRoamingAllowed(boolean z) throws RemoteException;

    void setSettingsChangeAllowed(boolean z) throws RemoteException;

    void setStatusBarExpansionAllowed(boolean z) throws RemoteException;

    void setStatusBarHidden(boolean z) throws RemoteException;

    void setWifiAllowed(boolean z) throws RemoteException;

    void setWifiStateChangeAllowed(boolean z) throws RemoteException;

    void setWifiTetheringAllowed(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISystemUIAdapterCallback {
        static final int TRANSACTION_excludeExternalStorageForFailedPasswordsWipe = 7;
        static final int TRANSACTION_setAdminLock = 29;
        static final int TRANSACTION_setAirplaneModeAllowed = 13;
        static final int TRANSACTION_setApplicationNameControlEnabled = 28;
        static final int TRANSACTION_setBluetoothAllowed = 18;
        static final int TRANSACTION_setCameraAllowed = 16;
        static final int TRANSACTION_setCellularDataAllowed = 14;
        static final int TRANSACTION_setFaceRecognitionEvenCameraBlockedAllowed = 17;
        static final int TRANSACTION_setGPSStateChangeAllowed = 24;
        static final int TRANSACTION_setKioskModeEnabled = 3;
        static final int TRANSACTION_setLocationProviderAllowed = 23;
        static final int TRANSACTION_setLockedIccIds = 25;
        static final int TRANSACTION_setLockscreenInvisibleOverlay = 26;
        static final int TRANSACTION_setLockscreenWallpaper = 27;
        static final int TRANSACTION_setMaximumFailedPasswordsForDisable = 4;
        static final int TRANSACTION_setMaximumFailedPasswordsForProfileDisable = 5;
        static final int TRANSACTION_setMultifactorAuthEnabled = 9;
        static final int TRANSACTION_setNFCStateChangeAllowed = 19;
        static final int TRANSACTION_setNavigationBarHidden = 2;
        static final int TRANSACTION_setPasswordLockDelay = 8;
        static final int TRANSACTION_setPasswordVisibilityEnabled = 10;
        static final int TRANSACTION_setPwdChangeRequested = 6;
        static final int TRANSACTION_setRoamingAllowed = 20;
        static final int TRANSACTION_setSettingsChangeAllowed = 11;
        static final int TRANSACTION_setStatusBarExpansionAllowed = 12;
        static final int TRANSACTION_setStatusBarHidden = 1;
        static final int TRANSACTION_setWifiAllowed = 22;
        static final int TRANSACTION_setWifiStateChangeAllowed = 21;
        static final int TRANSACTION_setWifiTetheringAllowed = 15;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
        }

        public Stub() {
            attachInterface(this, ISystemUIAdapterCallback.DESCRIPTOR);
        }

        public static ISystemUIAdapterCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISystemUIAdapterCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISystemUIAdapterCallback)) {
                return (ISystemUIAdapterCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setStatusBarHidden";
                case 2:
                    return "setNavigationBarHidden";
                case 3:
                    return "setKioskModeEnabled";
                case 4:
                    return "setMaximumFailedPasswordsForDisable";
                case 5:
                    return "setMaximumFailedPasswordsForProfileDisable";
                case 6:
                    return SecContentProviderURI.PASSWORDPOLICY_SETPWDCHANGEREQUESTED_METHOD;
                case 7:
                    return "excludeExternalStorageForFailedPasswordsWipe";
                case 8:
                    return "setPasswordLockDelay";
                case 9:
                    return "setMultifactorAuthEnabled";
                case 10:
                    return "setPasswordVisibilityEnabled";
                case 11:
                    return "setSettingsChangeAllowed";
                case 12:
                    return "setStatusBarExpansionAllowed";
                case 13:
                    return "setAirplaneModeAllowed";
                case 14:
                    return "setCellularDataAllowed";
                case 15:
                    return "setWifiTetheringAllowed";
                case 16:
                    return "setCameraAllowed";
                case 17:
                    return "setFaceRecognitionEvenCameraBlockedAllowed";
                case 18:
                    return "setBluetoothAllowed";
                case 19:
                    return "setNFCStateChangeAllowed";
                case 20:
                    return "setRoamingAllowed";
                case 21:
                    return "setWifiStateChangeAllowed";
                case 22:
                    return "setWifiAllowed";
                case 23:
                    return "setLocationProviderAllowed";
                case 24:
                    return "setGPSStateChangeAllowed";
                case 25:
                    return "setLockedIccIds";
                case 26:
                    return "setLockscreenInvisibleOverlay";
                case 27:
                    return "setLockscreenWallpaper";
                case 28:
                    return "setApplicationNameControlEnabled";
                case 29:
                    return "setAdminLock";
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
                parcel.enforceInterface(ISystemUIAdapterCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISystemUIAdapterCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStatusBarHidden(z);
                    return true;
                case 2:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNavigationBarHidden(z2);
                    return true;
                case 3:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKioskModeEnabled(z3);
                    return true;
                case 4:
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMaximumFailedPasswordsForDisable(i3, string);
                    return true;
                case 5:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMaximumFailedPasswordsForProfileDisable(i4);
                    return true;
                case 6:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPwdChangeRequested(i5);
                    return true;
                case 7:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    excludeExternalStorageForFailedPasswordsWipe(z4);
                    return true;
                case 8:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPasswordLockDelay(i6);
                    return true;
                case 9:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMultifactorAuthEnabled(z5);
                    return true;
                case 10:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPasswordVisibilityEnabled(z6);
                    return true;
                case 11:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSettingsChangeAllowed(z7);
                    return true;
                case 12:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStatusBarExpansionAllowed(z8);
                    return true;
                case 13:
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAirplaneModeAllowed(z9);
                    return true;
                case 14:
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCellularDataAllowed(z10);
                    return true;
                case 15:
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setWifiTetheringAllowed(z11);
                    return true;
                case 16:
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCameraAllowed(z12);
                    return true;
                case 17:
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFaceRecognitionEvenCameraBlockedAllowed(z13);
                    return true;
                case 18:
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothAllowed(z14);
                    return true;
                case 19:
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNFCStateChangeAllowed(z15);
                    return true;
                case 20:
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRoamingAllowed(z16);
                    return true;
                case 21:
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setWifiStateChangeAllowed(z17);
                    return true;
                case 22:
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setWifiAllowed(z18);
                    return true;
                case 23:
                    String string2 = parcel.readString();
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLocationProviderAllowed(string2, z19);
                    return true;
                case 24:
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setGPSStateChangeAllowed(z20);
                    return true;
                case 25:
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setLockedIccIds(strArrCreateStringArray);
                    return true;
                case 26:
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockscreenInvisibleOverlay(z21);
                    return true;
                case 27:
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockscreenWallpaper(z22);
                    return true;
                case 28:
                    boolean z23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setApplicationNameControlEnabled(z23);
                    return true;
                case 29:
                    boolean z24 = parcel.readBoolean();
                    boolean z25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAdminLock(z24, z25);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISystemUIAdapterCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISystemUIAdapterCallback.DESCRIPTOR;
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setStatusBarHidden(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setNavigationBarHidden(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setKioskModeEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setMaximumFailedPasswordsForDisable(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setMaximumFailedPasswordsForProfileDisable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setPwdChangeRequested(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void excludeExternalStorageForFailedPasswordsWipe(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setPasswordLockDelay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setMultifactorAuthEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setPasswordVisibilityEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setSettingsChangeAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setStatusBarExpansionAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setAirplaneModeAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setCellularDataAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setWifiTetheringAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setCameraAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setFaceRecognitionEvenCameraBlockedAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setBluetoothAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setNFCStateChangeAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setRoamingAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setWifiStateChangeAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setWifiAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setLocationProviderAllowed(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setGPSStateChangeAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setLockedIccIds(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setLockscreenInvisibleOverlay(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setLockscreenWallpaper(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setApplicationNameControlEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setAdminLock(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(29, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
