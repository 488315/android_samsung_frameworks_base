package android.os;

import android.content.ComponentName;
import android.content.pm.SignedPackageParcel;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface ISystemConfig extends IInterface {
    public static final String DESCRIPTOR = "android.os.ISystemConfig";

    public static class Default implements ISystemConfig {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.ISystemConfig
        public List<ComponentName> getDefaultVrComponents() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public List<String> getDisabledUntilUsedPreinstalledCarrierApps() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public Map getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public Map getDisabledUntilUsedPreinstalledCarrierAssociatedApps() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public List<ComponentName> getEnabledComponentOverrides(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public List<SignedPackageParcel> getEnhancedConfirmationTrustedInstallers() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public List<SignedPackageParcel> getEnhancedConfirmationTrustedPackages() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public List<String> getPreventUserDisablePackages() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemConfig
        public int[] getSystemPermissionUids(String str) throws RemoteException {
            return null;
        }
    }

    List<ComponentName> getDefaultVrComponents() throws RemoteException;

    List<String> getDisabledUntilUsedPreinstalledCarrierApps() throws RemoteException;

    Map getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() throws RemoteException;

    Map getDisabledUntilUsedPreinstalledCarrierAssociatedApps() throws RemoteException;

    List<ComponentName> getEnabledComponentOverrides(String str) throws RemoteException;

    List<SignedPackageParcel> getEnhancedConfirmationTrustedInstallers() throws RemoteException;

    List<SignedPackageParcel> getEnhancedConfirmationTrustedPackages() throws RemoteException;

    List<String> getPreventUserDisablePackages() throws RemoteException;

    int[] getSystemPermissionUids(String str) throws RemoteException;

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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ISystemConfig.DESCRIPTOR);
        }

        public static ISystemConfig asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISystemConfig.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISystemConfig)) {
                return (ISystemConfig) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISystemConfig.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISystemConfig.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    List<String> disabledUntilUsedPreinstalledCarrierApps = getDisabledUntilUsedPreinstalledCarrierApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(disabledUntilUsedPreinstalledCarrierApps);
                    return true;
                case 2:
                    Map disabledUntilUsedPreinstalledCarrierAssociatedApps = getDisabledUntilUsedPreinstalledCarrierAssociatedApps();
                    parcel2.writeNoException();
                    parcel2.writeMap(disabledUntilUsedPreinstalledCarrierAssociatedApps);
                    return true;
                case 3:
                    Map disabledUntilUsedPreinstalledCarrierAssociatedAppEntries = getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries();
                    parcel2.writeNoException();
                    parcel2.writeMap(disabledUntilUsedPreinstalledCarrierAssociatedAppEntries);
                    return true;
                case 4:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] systemPermissionUids = getSystemPermissionUids(string);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(systemPermissionUids);
                    return true;
                case 5:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<ComponentName> enabledComponentOverrides = getEnabledComponentOverrides(string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledComponentOverrides, 1);
                    return true;
                case 6:
                    List<ComponentName> defaultVrComponents = getDefaultVrComponents();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(defaultVrComponents, 1);
                    return true;
                case 7:
                    List<String> preventUserDisablePackages = getPreventUserDisablePackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(preventUserDisablePackages);
                    return true;
                case 8:
                    List<SignedPackageParcel> enhancedConfirmationTrustedPackages = getEnhancedConfirmationTrustedPackages();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enhancedConfirmationTrustedPackages, 1);
                    return true;
                case 9:
                    List<SignedPackageParcel> enhancedConfirmationTrustedInstallers = getEnhancedConfirmationTrustedInstallers();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enhancedConfirmationTrustedInstallers, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISystemConfig {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public Map getDisabledUntilUsedPreinstalledCarrierAssociatedApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public Map getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public int[] getSystemPermissionUids(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public List<ComponentName> getEnabledComponentOverrides(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public List<ComponentName> getDefaultVrComponents() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public List<String> getPreventUserDisablePackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public List<SignedPackageParcel> getEnhancedConfirmationTrustedPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SignedPackageParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISystemConfig
            public List<SignedPackageParcel> getEnhancedConfirmationTrustedInstallers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISystemConfig.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SignedPackageParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
