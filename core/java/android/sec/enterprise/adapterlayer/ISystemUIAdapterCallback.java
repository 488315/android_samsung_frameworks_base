package android.sec.enterprise.adapterlayer;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.sec.enterprise.content.SecContentProviderURI;

/* loaded from: classes3.dex */
public interface ISystemUIAdapterCallback extends IInterface {
    public static final String DESCRIPTOR = "android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback";

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

    public static class Default implements ISystemUIAdapterCallback {
        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setStatusBarHidden(boolean hidden) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setNavigationBarHidden(boolean hidden) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setKioskModeEnabled(boolean enable) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setMaximumFailedPasswordsForDisable(int num, String pkgName) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setMaximumFailedPasswordsForProfileDisable(int num) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setPwdChangeRequested(int flag) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void excludeExternalStorageForFailedPasswordsWipe(boolean exclude) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setPasswordLockDelay(int time) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setMultifactorAuthEnabled(boolean enabled) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setPasswordVisibilityEnabled(boolean allow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setSettingsChangeAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setStatusBarExpansionAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setAirplaneModeAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setCellularDataAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setWifiTetheringAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setCameraAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setFaceRecognitionEvenCameraBlockedAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setBluetoothAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setNFCStateChangeAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setRoamingAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setWifiStateChangeAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setWifiAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setLocationProviderAllowed(String provider, boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setGPSStateChangeAllowed(boolean isAllow) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setLockedIccIds(String[] iccIds) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setLockscreenInvisibleOverlay(boolean configured) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setLockscreenWallpaper(boolean configured) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setApplicationNameControlEnabled(boolean enabled) throws RemoteException {
        }

        @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
        public void setAdminLock(boolean enabled, boolean licenseExpired) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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

        public Stub() {
            attachInterface(this, ISystemUIAdapterCallback.DESCRIPTOR);
        }

        public static ISystemUIAdapterCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISystemUIAdapterCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISystemUIAdapterCallback)) {
                return (ISystemUIAdapterCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISystemUIAdapterCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISystemUIAdapterCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            boolean _arg0 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setStatusBarHidden(_arg0);
                            return true;
                        case 2:
                            boolean _arg02 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setNavigationBarHidden(_arg02);
                            return true;
                        case 3:
                            boolean _arg03 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setKioskModeEnabled(_arg03);
                            return true;
                        case 4:
                            int _arg04 = data.readInt();
                            String _arg1 = data.readString();
                            data.enforceNoDataAvail();
                            setMaximumFailedPasswordsForDisable(_arg04, _arg1);
                            return true;
                        case 5:
                            int _arg05 = data.readInt();
                            data.enforceNoDataAvail();
                            setMaximumFailedPasswordsForProfileDisable(_arg05);
                            return true;
                        case 6:
                            int _arg06 = data.readInt();
                            data.enforceNoDataAvail();
                            setPwdChangeRequested(_arg06);
                            return true;
                        case 7:
                            boolean _arg07 = data.readBoolean();
                            data.enforceNoDataAvail();
                            excludeExternalStorageForFailedPasswordsWipe(_arg07);
                            return true;
                        case 8:
                            int _arg08 = data.readInt();
                            data.enforceNoDataAvail();
                            setPasswordLockDelay(_arg08);
                            return true;
                        case 9:
                            boolean _arg09 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setMultifactorAuthEnabled(_arg09);
                            return true;
                        case 10:
                            boolean _arg010 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setPasswordVisibilityEnabled(_arg010);
                            return true;
                        case 11:
                            boolean _arg011 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setSettingsChangeAllowed(_arg011);
                            return true;
                        case 12:
                            boolean _arg012 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setStatusBarExpansionAllowed(_arg012);
                            return true;
                        case 13:
                            boolean _arg013 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setAirplaneModeAllowed(_arg013);
                            return true;
                        case 14:
                            boolean _arg014 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setCellularDataAllowed(_arg014);
                            return true;
                        case 15:
                            boolean _arg015 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setWifiTetheringAllowed(_arg015);
                            return true;
                        case 16:
                            boolean _arg016 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setCameraAllowed(_arg016);
                            return true;
                        case 17:
                            boolean _arg017 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setFaceRecognitionEvenCameraBlockedAllowed(_arg017);
                            return true;
                        case 18:
                            boolean _arg018 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setBluetoothAllowed(_arg018);
                            return true;
                        case 19:
                            boolean _arg019 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setNFCStateChangeAllowed(_arg019);
                            return true;
                        case 20:
                            boolean _arg020 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setRoamingAllowed(_arg020);
                            return true;
                        case 21:
                            boolean _arg021 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setWifiStateChangeAllowed(_arg021);
                            return true;
                        case 22:
                            boolean _arg022 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setWifiAllowed(_arg022);
                            return true;
                        case 23:
                            String _arg023 = data.readString();
                            boolean _arg12 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setLocationProviderAllowed(_arg023, _arg12);
                            return true;
                        case 24:
                            boolean _arg024 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setGPSStateChangeAllowed(_arg024);
                            return true;
                        case 25:
                            String[] _arg025 = data.createStringArray();
                            data.enforceNoDataAvail();
                            setLockedIccIds(_arg025);
                            return true;
                        case 26:
                            boolean _arg026 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setLockscreenInvisibleOverlay(_arg026);
                            return true;
                        case 27:
                            boolean _arg027 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setLockscreenWallpaper(_arg027);
                            return true;
                        case 28:
                            boolean _arg028 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setApplicationNameControlEnabled(_arg028);
                            return true;
                        case 29:
                            boolean _arg029 = data.readBoolean();
                            boolean _arg13 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setAdminLock(_arg029, _arg13);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISystemUIAdapterCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISystemUIAdapterCallback.DESCRIPTOR;
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setStatusBarHidden(boolean hidden) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(hidden);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setNavigationBarHidden(boolean hidden) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(hidden);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setKioskModeEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setMaximumFailedPasswordsForDisable(int num, String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeInt(num);
                    _data.writeString(pkgName);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setMaximumFailedPasswordsForProfileDisable(int num) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeInt(num);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setPwdChangeRequested(int flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeInt(flag);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void excludeExternalStorageForFailedPasswordsWipe(boolean exclude) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(exclude);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setPasswordLockDelay(int time) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeInt(time);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setMultifactorAuthEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setPasswordVisibilityEnabled(boolean allow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(allow);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setSettingsChangeAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setStatusBarExpansionAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setAirplaneModeAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setCellularDataAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setWifiTetheringAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setCameraAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setFaceRecognitionEvenCameraBlockedAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setBluetoothAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setNFCStateChangeAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setRoamingAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setWifiStateChangeAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setWifiAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setLocationProviderAllowed(String provider, boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeString(provider);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(23, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setGPSStateChangeAllowed(boolean isAllow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(isAllow);
                    this.mRemote.transact(24, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setLockedIccIds(String[] iccIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeStringArray(iccIds);
                    this.mRemote.transact(25, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setLockscreenInvisibleOverlay(boolean configured) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(configured);
                    this.mRemote.transact(26, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setLockscreenWallpaper(boolean configured) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(configured);
                    this.mRemote.transact(27, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setApplicationNameControlEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(28, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback
            public void setAdminLock(boolean enabled, boolean licenseExpired) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUIAdapterCallback.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    _data.writeBoolean(licenseExpired);
                    this.mRemote.transact(29, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 28;
        }
    }
}
