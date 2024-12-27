package android.os;

import android.content.ComponentName;
import android.content.pm.SignedPackageParcel;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface ISystemConfig extends IInterface {
    public static final String DESCRIPTOR = "android.os.ISystemConfig";

    List<ComponentName> getDefaultVrComponents() throws RemoteException;

    List<String> getDisabledUntilUsedPreinstalledCarrierApps() throws RemoteException;

    Map getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() throws RemoteException;

    Map getDisabledUntilUsedPreinstalledCarrierAssociatedApps() throws RemoteException;

    List<ComponentName> getEnabledComponentOverrides(String str) throws RemoteException;

    List<SignedPackageParcel> getEnhancedConfirmationTrustedInstallers() throws RemoteException;

    List<SignedPackageParcel> getEnhancedConfirmationTrustedPackages() throws RemoteException;

    List<String> getPreventUserDisablePackages() throws RemoteException;

    int[] getSystemPermissionUids(String str) throws RemoteException;

    public static class Default implements ISystemConfig {
        @Override // android.os.ISystemConfig
        public List<String> getDisabledUntilUsedPreinstalledCarrierApps() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public Map getDisabledUntilUsedPreinstalledCarrierAssociatedApps() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public Map getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public int[] getSystemPermissionUids(String permissionName) throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public List<ComponentName> getEnabledComponentOverrides(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public List<ComponentName> getDefaultVrComponents() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public List<String> getPreventUserDisablePackages() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public List<SignedPackageParcel> getEnhancedConfirmationTrustedPackages() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public List<SignedPackageParcel> getEnhancedConfirmationTrustedInstallers() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISystemConfig {
        static final int TRANSACTION_getDefaultVrComponents = 6;
        static final int TRANSACTION_getDisabledUntilUsedPreinstalledCarrierApps = 1;
        static final int TRANSACTION_getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries = 3;
        static final int TRANSACTION_getDisabledUntilUsedPreinstalledCarrierAssociatedApps = 2;
        static final int TRANSACTION_getEnabledComponentOverrides = 5;
        static final int TRANSACTION_getEnhancedConfirmationTrustedInstallers = 9;
        static final int TRANSACTION_getEnhancedConfirmationTrustedPackages = 8;
        static final int TRANSACTION_getPreventUserDisablePackages = 7;
        static final int TRANSACTION_getSystemPermissionUids = 4;

        public Stub() {
            attachInterface(this, ISystemConfig.DESCRIPTOR);
        }

        public static ISystemConfig asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISystemConfig.DESCRIPTOR);
            if (iin != null && (iin instanceof ISystemConfig)) {
                return (ISystemConfig) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getDisabledUntilUsedPreinstalledCarrierApps";
                case 2:
                    return "getDisabledUntilUsedPreinstalledCarrierAssociatedApps";
                case 3:
                    return "getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries";
                case 4:
                    return "getSystemPermissionUids";
                case 5:
                    return "getEnabledComponentOverrides";
                case 6:
                    return "getDefaultVrComponents";
                case 7:
                    return "getPreventUserDisablePackages";
                case 8:
                    return "getEnhancedConfirmationTrustedPackages";
                case 9:
                    return "getEnhancedConfirmationTrustedInstallers";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISystemConfig.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISystemConfig.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<String> _result = getDisabledUntilUsedPreinstalledCarrierApps();
                    reply.writeNoException();
                    reply.writeStringList(_result);
                    return true;
                case 2:
                    Map _result2 = getDisabledUntilUsedPreinstalledCarrierAssociatedApps();
                    reply.writeNoException();
                    reply.writeMap(_result2);
                    return true;
                case 3:
                    Map _result3 = getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries();
                    reply.writeNoException();
                    reply.writeMap(_result3);
                    return true;
                case 4:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    int[] _result4 = getSystemPermissionUids(_arg0);
                    reply.writeNoException();
                    reply.writeIntArray(_result4);
                    return true;
                case 5:
                    String _arg02 = data.readString();
                    data.enforceNoDataAvail();
                    List<ComponentName> _result5 = getEnabledComponentOverrides(_arg02);
                    reply.writeNoException();
                    reply.writeTypedList(_result5, 1);
                    return true;
                case 6:
                    List<ComponentName> _result6 = getDefaultVrComponents();
                    reply.writeNoException();
                    reply.writeTypedList(_result6, 1);
                    return true;
                case 7:
                    List<String> _result7 = getPreventUserDisablePackages();
                    reply.writeNoException();
                    reply.writeStringList(_result7);
                    return true;
                case 8:
                    List<SignedPackageParcel> _result8 = getEnhancedConfirmationTrustedPackages();
                    reply.writeNoException();
                    reply.writeTypedList(_result8, 1);
                    return true;
                case 9:
                    List<SignedPackageParcel> _result9 = getEnhancedConfirmationTrustedInstallers();
                    reply.writeNoException();
                    reply.writeTypedList(_result9, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISystemConfig {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISystemConfig.DESCRIPTOR;
            }

            @Override // android.os.ISystemConfig
            public List<String> getDisabledUntilUsedPreinstalledCarrierApps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public Map getDisabledUntilUsedPreinstalledCarrierAssociatedApps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public Map getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public int[] getSystemPermissionUids(String permissionName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    _data.writeString(permissionName);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public List<ComponentName> getEnabledComponentOverrides(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    List<ComponentName> _result = _reply.createTypedArrayList(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public List<ComponentName> getDefaultVrComponents() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    List<ComponentName> _result = _reply.createTypedArrayList(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public List<String> getPreventUserDisablePackages() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public List<SignedPackageParcel> getEnhancedConfirmationTrustedPackages() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    List<SignedPackageParcel> _result = _reply.createTypedArrayList(SignedPackageParcel.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public List<SignedPackageParcel> getEnhancedConfirmationTrustedInstallers() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    List<SignedPackageParcel> _result = _reply.createTypedArrayList(SignedPackageParcel.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
