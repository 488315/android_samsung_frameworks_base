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
public interface IBluetoothPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.bluetooth.IBluetoothPolicy";

    public class Default implements IBluetoothPolicy {
        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean activateBluetoothDeviceRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean activateBluetoothUUIDRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean addBluetoothDevicesToBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean addBluetoothDevicesToWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean addBluetoothUUIDsToBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean addBluetoothUUIDsToWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean allowBLE(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean allowBluetooth(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean allowOutgoingCalls(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean bluetoothLog(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean clearBluetoothDevicesFromBlackList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean clearBluetoothDevicesFromWhiteList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean clearBluetoothUUIDsFromBlackList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean clearBluetoothUUIDsFromWhiteList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public List<BluetoothControlInfo> getAllBluetoothDevicesBlackLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public List<BluetoothControlInfo> getAllBluetoothDevicesWhiteLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public List<BluetoothControlInfo> getAllBluetoothUUIDsBlackLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public List<BluetoothControlInfo> getAllBluetoothUUIDsWhiteLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean getAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public List<String> getBluetoothLog(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public List<String> getEffectiveBluetoothDevicesBlackLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public List<String> getEffectiveBluetoothDevicesWhiteLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public List<String> getEffectiveBluetoothUUIDsBlackLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public List<String> getEffectiveBluetoothUUIDsWhiteLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isBLEAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isBluetoothDeviceAllowed(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isBluetoothDeviceRestrictionActive(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isBluetoothEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isBluetoothEnabledWithMsg(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isBluetoothLogEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isBluetoothUUIDAllowed(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isBluetoothUUIDRestrictionActive(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isDesktopConnectivityEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isDiscoverableEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isLimitedDiscoverableEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isOutgoingCallsAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isPairingEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isProfileEnabled(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean isProfileEnabledInternal(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean removeBluetoothDevicesFromBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean removeBluetoothDevicesFromWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean removeBluetoothUUIDsFromBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean removeBluetoothUUIDsFromWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean setAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean setBluetooth(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean setBluetoothLogEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean setDesktopConnectivityState(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean setDiscoverableState(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean setLimitedDiscoverableState(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean setPairingState(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
        public boolean setProfileState(ContextInfo contextInfo, boolean z, int i) throws RemoteException {
            return false;
        }
    }

    boolean activateBluetoothDeviceRestriction(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean activateBluetoothUUIDRestriction(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean addBluetoothDevicesToBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addBluetoothDevicesToWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addBluetoothUUIDsToBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addBluetoothUUIDsToWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean allowBLE(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowBluetooth(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowOutgoingCalls(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean bluetoothLog(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean clearBluetoothDevicesFromBlackList(ContextInfo contextInfo) throws RemoteException;

    boolean clearBluetoothDevicesFromWhiteList(ContextInfo contextInfo) throws RemoteException;

    boolean clearBluetoothUUIDsFromBlackList(ContextInfo contextInfo) throws RemoteException;

    boolean clearBluetoothUUIDsFromWhiteList(ContextInfo contextInfo) throws RemoteException;

    List<BluetoothControlInfo> getAllBluetoothDevicesBlackLists(ContextInfo contextInfo) throws RemoteException;

    List<BluetoothControlInfo> getAllBluetoothDevicesWhiteLists(ContextInfo contextInfo) throws RemoteException;

    List<BluetoothControlInfo> getAllBluetoothUUIDsBlackLists(ContextInfo contextInfo) throws RemoteException;

    List<BluetoothControlInfo> getAllBluetoothUUIDsWhiteLists(ContextInfo contextInfo) throws RemoteException;

    boolean getAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) throws RemoteException;

    List<String> getBluetoothLog(ContextInfo contextInfo) throws RemoteException;

    List<String> getEffectiveBluetoothDevicesBlackLists(ContextInfo contextInfo) throws RemoteException;

    List<String> getEffectiveBluetoothDevicesWhiteLists(ContextInfo contextInfo) throws RemoteException;

    List<String> getEffectiveBluetoothUUIDsBlackLists(ContextInfo contextInfo) throws RemoteException;

    List<String> getEffectiveBluetoothUUIDsWhiteLists(ContextInfo contextInfo) throws RemoteException;

    boolean isBLEAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isBluetoothDeviceAllowed(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isBluetoothDeviceRestrictionActive(ContextInfo contextInfo) throws RemoteException;

    boolean isBluetoothEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isBluetoothEnabledWithMsg(boolean z) throws RemoteException;

    boolean isBluetoothLogEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isBluetoothUUIDAllowed(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isBluetoothUUIDRestrictionActive(ContextInfo contextInfo) throws RemoteException;

    boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isDesktopConnectivityEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isDiscoverableEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isLimitedDiscoverableEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isOutgoingCallsAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isPairingEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isProfileEnabled(ContextInfo contextInfo, int i) throws RemoteException;

    boolean isProfileEnabledInternal(int i, boolean z) throws RemoteException;

    boolean removeBluetoothDevicesFromBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removeBluetoothDevicesFromWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removeBluetoothUUIDsFromBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removeBluetoothUUIDsFromWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean setAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setBluetooth(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setBluetoothLogEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setDesktopConnectivityState(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setDiscoverableState(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setLimitedDiscoverableState(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setPairingState(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setProfileState(ContextInfo contextInfo, boolean z, int i) throws RemoteException;

    public abstract class Stub extends Binder implements IBluetoothPolicy {
        public static final int TRANSACTION_activateBluetoothDeviceRestriction = 36;
        public static final int TRANSACTION_activateBluetoothUUIDRestriction = 27;
        public static final int TRANSACTION_addBluetoothDevicesToBlackList = 28;
        public static final int TRANSACTION_addBluetoothDevicesToWhiteList = 32;
        public static final int TRANSACTION_addBluetoothUUIDsToBlackList = 19;
        public static final int TRANSACTION_addBluetoothUUIDsToWhiteList = 23;
        public static final int TRANSACTION_allowBLE = 52;
        public static final int TRANSACTION_allowBluetooth = 4;
        public static final int TRANSACTION_allowCallerIDDisplay = 50;
        public static final int TRANSACTION_allowOutgoingCalls = 9;
        public static final int TRANSACTION_bluetoothLog = 49;
        public static final int TRANSACTION_clearBluetoothDevicesFromBlackList = 30;
        public static final int TRANSACTION_clearBluetoothDevicesFromWhiteList = 34;
        public static final int TRANSACTION_clearBluetoothUUIDsFromBlackList = 21;
        public static final int TRANSACTION_clearBluetoothUUIDsFromWhiteList = 25;
        public static final int TRANSACTION_getAllBluetoothDevicesBlackLists = 31;
        public static final int TRANSACTION_getAllBluetoothDevicesWhiteLists = 35;
        public static final int TRANSACTION_getAllBluetoothUUIDsBlackLists = 22;
        public static final int TRANSACTION_getAllBluetoothUUIDsWhiteLists = 26;
        public static final int TRANSACTION_getAllowBluetoothDataTransfer = 2;
        public static final int TRANSACTION_getBluetoothLog = 48;
        public static final int TRANSACTION_getEffectiveBluetoothDevicesBlackLists = 43;
        public static final int TRANSACTION_getEffectiveBluetoothDevicesWhiteLists = 44;
        public static final int TRANSACTION_getEffectiveBluetoothUUIDsBlackLists = 41;
        public static final int TRANSACTION_getEffectiveBluetoothUUIDsWhiteLists = 42;
        public static final int TRANSACTION_isBLEAllowed = 53;
        public static final int TRANSACTION_isBluetoothDeviceAllowed = 40;
        public static final int TRANSACTION_isBluetoothDeviceRestrictionActive = 38;
        public static final int TRANSACTION_isBluetoothEnabled = 5;
        public static final int TRANSACTION_isBluetoothEnabledWithMsg = 6;
        public static final int TRANSACTION_isBluetoothLogEnabled = 47;
        public static final int TRANSACTION_isBluetoothUUIDAllowed = 39;
        public static final int TRANSACTION_isBluetoothUUIDRestrictionActive = 37;
        public static final int TRANSACTION_isCallerIDDisplayAllowed = 51;
        public static final int TRANSACTION_isDesktopConnectivityEnabled = 18;
        public static final int TRANSACTION_isDiscoverableEnabled = 16;
        public static final int TRANSACTION_isLimitedDiscoverableEnabled = 12;
        public static final int TRANSACTION_isOutgoingCallsAllowed = 10;
        public static final int TRANSACTION_isPairingEnabled = 8;
        public static final int TRANSACTION_isProfileEnabled = 14;
        public static final int TRANSACTION_isProfileEnabledInternal = 46;
        public static final int TRANSACTION_removeBluetoothDevicesFromBlackList = 29;
        public static final int TRANSACTION_removeBluetoothDevicesFromWhiteList = 33;
        public static final int TRANSACTION_removeBluetoothUUIDsFromBlackList = 20;
        public static final int TRANSACTION_removeBluetoothUUIDsFromWhiteList = 24;
        public static final int TRANSACTION_setAllowBluetoothDataTransfer = 1;
        public static final int TRANSACTION_setBluetooth = 3;
        public static final int TRANSACTION_setBluetoothLogEnabled = 45;
        public static final int TRANSACTION_setDesktopConnectivityState = 17;
        public static final int TRANSACTION_setDiscoverableState = 15;
        public static final int TRANSACTION_setLimitedDiscoverableState = 11;
        public static final int TRANSACTION_setPairingState = 7;
        public static final int TRANSACTION_setProfileState = 13;

        class Proxy implements IBluetoothPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean activateBluetoothDeviceRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean activateBluetoothUUIDRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean addBluetoothDevicesToBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean addBluetoothDevicesToWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean addBluetoothUUIDsToBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean addBluetoothUUIDsToWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean allowBLE(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean allowBluetooth(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean allowOutgoingCalls(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
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

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean bluetoothLog(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean clearBluetoothDevicesFromBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean clearBluetoothDevicesFromWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean clearBluetoothUUIDsFromBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean clearBluetoothUUIDsFromWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<BluetoothControlInfo> getAllBluetoothDevicesBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(BluetoothControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<BluetoothControlInfo> getAllBluetoothDevicesWhiteLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(BluetoothControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<BluetoothControlInfo> getAllBluetoothUUIDsBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(BluetoothControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<BluetoothControlInfo> getAllBluetoothUUIDsWhiteLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(BluetoothControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean getAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<String> getBluetoothLog(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<String> getEffectiveBluetoothDevicesBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<String> getEffectiveBluetoothDevicesWhiteLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<String> getEffectiveBluetoothUUIDsBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<String> getEffectiveBluetoothUUIDsWhiteLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IBluetoothPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBLEAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothDeviceAllowed(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothDeviceRestrictionActive(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothEnabledWithMsg(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothLogEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothUUIDAllowed(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothUUIDRestrictionActive(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isDesktopConnectivityEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isDiscoverableEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isLimitedDiscoverableEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isOutgoingCallsAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isPairingEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isProfileEnabled(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isProfileEnabledInternal(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean removeBluetoothDevicesFromBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean removeBluetoothDevicesFromWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean removeBluetoothUUIDsFromBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean removeBluetoothUUIDsFromWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setBluetooth(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setBluetoothLogEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setDesktopConnectivityState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setDiscoverableState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setLimitedDiscoverableState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setPairingState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setProfileState(ContextInfo contextInfo, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IBluetoothPolicy.DESCRIPTOR);
        }

        public static IBluetoothPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBluetoothPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IBluetoothPolicy)) ? new Proxy(iBinder) : (IBluetoothPolicy) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setAllowBluetoothDataTransfer";
                case 2:
                    return "getAllowBluetoothDataTransfer";
                case 3:
                    return "setBluetooth";
                case 4:
                    return "allowBluetooth";
                case 5:
                    return "isBluetoothEnabled";
                case 6:
                    return "isBluetoothEnabledWithMsg";
                case 7:
                    return "setPairingState";
                case 8:
                    return "isPairingEnabled";
                case 9:
                    return "allowOutgoingCalls";
                case 10:
                    return "isOutgoingCallsAllowed";
                case 11:
                    return "setLimitedDiscoverableState";
                case 12:
                    return "isLimitedDiscoverableEnabled";
                case 13:
                    return "setProfileState";
                case 14:
                    return "isProfileEnabled";
                case 15:
                    return "setDiscoverableState";
                case 16:
                    return "isDiscoverableEnabled";
                case 17:
                    return "setDesktopConnectivityState";
                case 18:
                    return "isDesktopConnectivityEnabled";
                case 19:
                    return "addBluetoothUUIDsToBlackList";
                case 20:
                    return "removeBluetoothUUIDsFromBlackList";
                case 21:
                    return "clearBluetoothUUIDsFromBlackList";
                case 22:
                    return "getAllBluetoothUUIDsBlackLists";
                case 23:
                    return "addBluetoothUUIDsToWhiteList";
                case 24:
                    return "removeBluetoothUUIDsFromWhiteList";
                case 25:
                    return "clearBluetoothUUIDsFromWhiteList";
                case 26:
                    return "getAllBluetoothUUIDsWhiteLists";
                case 27:
                    return "activateBluetoothUUIDRestriction";
                case 28:
                    return "addBluetoothDevicesToBlackList";
                case 29:
                    return "removeBluetoothDevicesFromBlackList";
                case 30:
                    return "clearBluetoothDevicesFromBlackList";
                case 31:
                    return "getAllBluetoothDevicesBlackLists";
                case 32:
                    return "addBluetoothDevicesToWhiteList";
                case 33:
                    return "removeBluetoothDevicesFromWhiteList";
                case 34:
                    return "clearBluetoothDevicesFromWhiteList";
                case 35:
                    return "getAllBluetoothDevicesWhiteLists";
                case 36:
                    return "activateBluetoothDeviceRestriction";
                case 37:
                    return "isBluetoothUUIDRestrictionActive";
                case 38:
                    return "isBluetoothDeviceRestrictionActive";
                case 39:
                    return "isBluetoothUUIDAllowed";
                case 40:
                    return "isBluetoothDeviceAllowed";
                case 41:
                    return "getEffectiveBluetoothUUIDsBlackLists";
                case 42:
                    return "getEffectiveBluetoothUUIDsWhiteLists";
                case 43:
                    return "getEffectiveBluetoothDevicesBlackLists";
                case 44:
                    return "getEffectiveBluetoothDevicesWhiteLists";
                case 45:
                    return "setBluetoothLogEnabled";
                case 46:
                    return "isProfileEnabledInternal";
                case 47:
                    return "isBluetoothLogEnabled";
                case 48:
                    return "getBluetoothLog";
                case 49:
                    return "bluetoothLog";
                case 50:
                    return "allowCallerIDDisplay";
                case 51:
                    return "isCallerIDDisplayAllowed";
                case 52:
                    return "allowBLE";
                case 53:
                    return "isBLEAllowed";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 52;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBluetoothPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBluetoothPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowBluetoothDataTransfer = setAllowBluetoothDataTransfer(contextInfo, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowBluetoothDataTransfer);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowBluetoothDataTransfer2 = getAllowBluetoothDataTransfer(contextInfo2, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowBluetoothDataTransfer2);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean bluetooth = setBluetooth(contextInfo3, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetooth);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowBluetooth = allowBluetooth(contextInfo4, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowBluetooth);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothEnabled = isBluetoothEnabled(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothEnabled);
                    return true;
                case 6:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothEnabledWithMsg = isBluetoothEnabledWithMsg(z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothEnabledWithMsg);
                    return true;
                case 7:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean pairingState = setPairingState(contextInfo6, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pairingState);
                    return true;
                case 8:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsPairingEnabled = isPairingEnabled(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPairingEnabled);
                    return true;
                case 9:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowOutgoingCalls = allowOutgoingCalls(contextInfo8, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowOutgoingCalls);
                    return true;
                case 10:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsOutgoingCallsAllowed = isOutgoingCallsAllowed(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOutgoingCallsAllowed);
                    return true;
                case 11:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean limitedDiscoverableState = setLimitedDiscoverableState(contextInfo10, z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitedDiscoverableState);
                    return true;
                case 12:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsLimitedDiscoverableEnabled = isLimitedDiscoverableEnabled(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLimitedDiscoverableEnabled);
                    return true;
                case 13:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean profileState = setProfileState(contextInfo12, z9, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(profileState);
                    return true;
                case 14:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsProfileEnabled = isProfileEnabled(contextInfo13, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProfileEnabled);
                    return true;
                case 15:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean discoverableState = setDiscoverableState(contextInfo14, z10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(discoverableState);
                    return true;
                case 16:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsDiscoverableEnabled = isDiscoverableEnabled(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDiscoverableEnabled);
                    return true;
                case 17:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean desktopConnectivityState = setDesktopConnectivityState(contextInfo16, z11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(desktopConnectivityState);
                    return true;
                case 18:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsDesktopConnectivityEnabled = isDesktopConnectivityEnabled(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDesktopConnectivityEnabled);
                    return true;
                case 19:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddBluetoothUUIDsToBlackList = addBluetoothUUIDsToBlackList(contextInfo18, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddBluetoothUUIDsToBlackList);
                    return true;
                case 20:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveBluetoothUUIDsFromBlackList = removeBluetoothUUIDsFromBlackList(contextInfo19, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveBluetoothUUIDsFromBlackList);
                    return true;
                case 21:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearBluetoothUUIDsFromBlackList = clearBluetoothUUIDsFromBlackList(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearBluetoothUUIDsFromBlackList);
                    return true;
                case 22:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<BluetoothControlInfo> allBluetoothUUIDsBlackLists = getAllBluetoothUUIDsBlackLists(contextInfo21);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allBluetoothUUIDsBlackLists, 1);
                    return true;
                case 23:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddBluetoothUUIDsToWhiteList = addBluetoothUUIDsToWhiteList(contextInfo22, arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddBluetoothUUIDsToWhiteList);
                    return true;
                case 24:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveBluetoothUUIDsFromWhiteList = removeBluetoothUUIDsFromWhiteList(contextInfo23, arrayListCreateStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveBluetoothUUIDsFromWhiteList);
                    return true;
                case 25:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearBluetoothUUIDsFromWhiteList = clearBluetoothUUIDsFromWhiteList(contextInfo24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearBluetoothUUIDsFromWhiteList);
                    return true;
                case 26:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<BluetoothControlInfo> allBluetoothUUIDsWhiteLists = getAllBluetoothUUIDsWhiteLists(contextInfo25);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allBluetoothUUIDsWhiteLists, 1);
                    return true;
                case 27:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zActivateBluetoothUUIDRestriction = activateBluetoothUUIDRestriction(contextInfo26, z12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zActivateBluetoothUUIDRestriction);
                    return true;
                case 28:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddBluetoothDevicesToBlackList = addBluetoothDevicesToBlackList(contextInfo27, arrayListCreateStringArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddBluetoothDevicesToBlackList);
                    return true;
                case 29:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveBluetoothDevicesFromBlackList = removeBluetoothDevicesFromBlackList(contextInfo28, arrayListCreateStringArrayList6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveBluetoothDevicesFromBlackList);
                    return true;
                case 30:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearBluetoothDevicesFromBlackList = clearBluetoothDevicesFromBlackList(contextInfo29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearBluetoothDevicesFromBlackList);
                    return true;
                case 31:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<BluetoothControlInfo> allBluetoothDevicesBlackLists = getAllBluetoothDevicesBlackLists(contextInfo30);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allBluetoothDevicesBlackLists, 1);
                    return true;
                case 32:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddBluetoothDevicesToWhiteList = addBluetoothDevicesToWhiteList(contextInfo31, arrayListCreateStringArrayList7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddBluetoothDevicesToWhiteList);
                    return true;
                case 33:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveBluetoothDevicesFromWhiteList = removeBluetoothDevicesFromWhiteList(contextInfo32, arrayListCreateStringArrayList8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveBluetoothDevicesFromWhiteList);
                    return true;
                case 34:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearBluetoothDevicesFromWhiteList = clearBluetoothDevicesFromWhiteList(contextInfo33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearBluetoothDevicesFromWhiteList);
                    return true;
                case 35:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<BluetoothControlInfo> allBluetoothDevicesWhiteLists = getAllBluetoothDevicesWhiteLists(contextInfo34);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allBluetoothDevicesWhiteLists, 1);
                    return true;
                case 36:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zActivateBluetoothDeviceRestriction = activateBluetoothDeviceRestriction(contextInfo35, z13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zActivateBluetoothDeviceRestriction);
                    return true;
                case 37:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothUUIDRestrictionActive = isBluetoothUUIDRestrictionActive(contextInfo36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothUUIDRestrictionActive);
                    return true;
                case 38:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothDeviceRestrictionActive = isBluetoothDeviceRestrictionActive(contextInfo37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothDeviceRestrictionActive);
                    return true;
                case 39:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothUUIDAllowed = isBluetoothUUIDAllowed(contextInfo38, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothUUIDAllowed);
                    return true;
                case 40:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothDeviceAllowed = isBluetoothDeviceAllowed(contextInfo39, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothDeviceAllowed);
                    return true;
                case 41:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> effectiveBluetoothUUIDsBlackLists = getEffectiveBluetoothUUIDsBlackLists(contextInfo40);
                    parcel2.writeNoException();
                    parcel2.writeStringList(effectiveBluetoothUUIDsBlackLists);
                    return true;
                case 42:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> effectiveBluetoothUUIDsWhiteLists = getEffectiveBluetoothUUIDsWhiteLists(contextInfo41);
                    parcel2.writeNoException();
                    parcel2.writeStringList(effectiveBluetoothUUIDsWhiteLists);
                    return true;
                case 43:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> effectiveBluetoothDevicesBlackLists = getEffectiveBluetoothDevicesBlackLists(contextInfo42);
                    parcel2.writeNoException();
                    parcel2.writeStringList(effectiveBluetoothDevicesBlackLists);
                    return true;
                case 44:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> effectiveBluetoothDevicesWhiteLists = getEffectiveBluetoothDevicesWhiteLists(contextInfo43);
                    parcel2.writeNoException();
                    parcel2.writeStringList(effectiveBluetoothDevicesWhiteLists);
                    return true;
                case 45:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean bluetoothLogEnabled = setBluetoothLogEnabled(contextInfo44, z14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetoothLogEnabled);
                    return true;
                case 46:
                    int i5 = parcel.readInt();
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsProfileEnabledInternal = isProfileEnabledInternal(i5, z15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProfileEnabledInternal);
                    return true;
                case 47:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothLogEnabled = isBluetoothLogEnabled(contextInfo45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothLogEnabled);
                    return true;
                case 48:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> bluetoothLog = getBluetoothLog(contextInfo46);
                    parcel2.writeNoException();
                    parcel2.writeStringList(bluetoothLog);
                    return true;
                case 49:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zBluetoothLog = bluetoothLog(contextInfo47, string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zBluetoothLog);
                    return true;
                case 50:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowCallerIDDisplay = allowCallerIDDisplay(contextInfo48, z16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowCallerIDDisplay);
                    return true;
                case 51:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsCallerIDDisplayAllowed = isCallerIDDisplayAllowed(contextInfo49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallerIDDisplayAllowed);
                    return true;
                case 52:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowBLE = allowBLE(contextInfo50, z17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowBLE);
                    return true;
                case 53:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBLEAllowed = isBLEAllowed(contextInfo51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBLEAllowed);
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
