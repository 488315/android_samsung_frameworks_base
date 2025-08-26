package com.samsung.android.knox.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IBluetoothSecureModePolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy";

    public class Default implements IBluetoothSecureModePolicy {
        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean addBluetoothDevicesToWhiteList(ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean disableSecureMode(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean enableDeviceWhiteList(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean enableSecureMode(ContextInfo contextInfo, BluetoothSecureModeConfig bluetoothSecureModeConfig, List<BluetoothSecureModeWhitelistConfig> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public List<BluetoothSecureModeWhitelistConfig> getBluetoothDevicesFromWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public BluetoothSecureModeConfig getSecureModeConfiguration(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean isDeviceWhiteListEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean isSecureModeEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean removeBluetoothDevicesFromWhiteList(ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list) throws RemoteException {
            return false;
        }
    }

    boolean addBluetoothDevicesToWhiteList(ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list) throws RemoteException;

    boolean disableSecureMode(ContextInfo contextInfo) throws RemoteException;

    boolean enableDeviceWhiteList(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enableSecureMode(ContextInfo contextInfo, BluetoothSecureModeConfig bluetoothSecureModeConfig, List<BluetoothSecureModeWhitelistConfig> list) throws RemoteException;

    List<BluetoothSecureModeWhitelistConfig> getBluetoothDevicesFromWhiteList(ContextInfo contextInfo) throws RemoteException;

    BluetoothSecureModeConfig getSecureModeConfiguration(ContextInfo contextInfo) throws RemoteException;

    boolean isDeviceWhiteListEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isSecureModeEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean removeBluetoothDevicesFromWhiteList(ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list) throws RemoteException;

    public abstract class Stub extends Binder implements IBluetoothSecureModePolicy {
        public static final int TRANSACTION_addBluetoothDevicesToWhiteList = 8;
        public static final int TRANSACTION_disableSecureMode = 2;
        public static final int TRANSACTION_enableDeviceWhiteList = 5;
        public static final int TRANSACTION_enableSecureMode = 1;
        public static final int TRANSACTION_getBluetoothDevicesFromWhiteList = 7;
        public static final int TRANSACTION_getSecureModeConfiguration = 3;
        public static final int TRANSACTION_isDeviceWhiteListEnabled = 6;
        public static final int TRANSACTION_isSecureModeEnabled = 4;
        public static final int TRANSACTION_removeBluetoothDevicesFromWhiteList = 9;

        class Proxy implements IBluetoothSecureModePolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean addBluetoothDevicesToWhiteList(ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean disableSecureMode(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean enableDeviceWhiteList(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean enableSecureMode(ContextInfo contextInfo, BluetoothSecureModeConfig bluetoothSecureModeConfig, List<BluetoothSecureModeWhitelistConfig> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(bluetoothSecureModeConfig, 0);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public List<BluetoothSecureModeWhitelistConfig> getBluetoothDevicesFromWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(BluetoothSecureModeWhitelistConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IBluetoothSecureModePolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public BluetoothSecureModeConfig getSecureModeConfiguration(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (BluetoothSecureModeConfig) parcelObtain2.readTypedObject(BluetoothSecureModeConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean isDeviceWhiteListEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean isSecureModeEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean removeBluetoothDevicesFromWhiteList(ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IBluetoothSecureModePolicy.DESCRIPTOR);
        }

        public static IBluetoothSecureModePolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBluetoothSecureModePolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IBluetoothSecureModePolicy)) ? new Proxy(iBinder) : (IBluetoothSecureModePolicy) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "enableSecureMode";
                case 2:
                    return "disableSecureMode";
                case 3:
                    return "getSecureModeConfiguration";
                case 4:
                    return "isSecureModeEnabled";
                case 5:
                    return "enableDeviceWhiteList";
                case 6:
                    return "isDeviceWhiteListEnabled";
                case 7:
                    return "getBluetoothDevicesFromWhiteList";
                case 8:
                    return "addBluetoothDevicesToWhiteList";
                case 9:
                    return "removeBluetoothDevicesFromWhiteList";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 8;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBluetoothSecureModePolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBluetoothSecureModePolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    BluetoothSecureModeConfig bluetoothSecureModeConfig = (BluetoothSecureModeConfig) parcel.readTypedObject(BluetoothSecureModeConfig.CREATOR);
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(BluetoothSecureModeWhitelistConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zEnableSecureMode = enableSecureMode(contextInfo, bluetoothSecureModeConfig, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableSecureMode);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zDisableSecureMode = disableSecureMode(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableSecureMode);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    BluetoothSecureModeConfig secureModeConfiguration = getSecureModeConfiguration(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(secureModeConfiguration, 1);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSecureModeEnabled = isSecureModeEnabled(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSecureModeEnabled);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableDeviceWhiteList = enableDeviceWhiteList(contextInfo5, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableDeviceWhiteList);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsDeviceWhiteListEnabled = isDeviceWhiteListEnabled(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceWhiteListEnabled);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<BluetoothSecureModeWhitelistConfig> bluetoothDevicesFromWhiteList = getBluetoothDevicesFromWhiteList(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(bluetoothDevicesFromWhiteList, 1);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(BluetoothSecureModeWhitelistConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAddBluetoothDevicesToWhiteList = addBluetoothDevicesToWhiteList(contextInfo8, arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddBluetoothDevicesToWhiteList);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(BluetoothSecureModeWhitelistConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveBluetoothDevicesFromWhiteList = removeBluetoothDevicesFromWhiteList(contextInfo9, arrayListCreateTypedArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveBluetoothDevicesFromWhiteList);
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
