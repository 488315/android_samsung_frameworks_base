package com.samsung.android.knox.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IBluetoothPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.bluetooth.IBluetoothPolicy";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IBluetoothPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean activateBluetoothDeviceRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean activateBluetoothUUIDRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean addBluetoothDevicesToBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean addBluetoothDevicesToWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean addBluetoothUUIDsToBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean addBluetoothUUIDsToWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean allowBLE(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean allowBluetooth(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean allowOutgoingCalls(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean bluetoothLog(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean clearBluetoothDevicesFromBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean clearBluetoothDevicesFromWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean clearBluetoothUUIDsFromBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean clearBluetoothUUIDsFromWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<BluetoothControlInfo> getAllBluetoothDevicesBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(BluetoothControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<BluetoothControlInfo> getAllBluetoothDevicesWhiteLists(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(BluetoothControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<BluetoothControlInfo> getAllBluetoothUUIDsBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(BluetoothControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<BluetoothControlInfo> getAllBluetoothUUIDsWhiteLists(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(BluetoothControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean getAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<String> getBluetoothLog(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<String> getEffectiveBluetoothDevicesBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<String> getEffectiveBluetoothDevicesWhiteLists(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<String> getEffectiveBluetoothUUIDsBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public List<String> getEffectiveBluetoothUUIDsWhiteLists(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IBluetoothPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBLEAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothDeviceAllowed(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothDeviceRestrictionActive(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothEnabledWithMsg(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothLogEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothUUIDAllowed(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isBluetoothUUIDRestrictionActive(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isDesktopConnectivityEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isDiscoverableEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isLimitedDiscoverableEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isOutgoingCallsAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isPairingEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isProfileEnabled(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean isProfileEnabledInternal(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean removeBluetoothDevicesFromBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean removeBluetoothDevicesFromWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean removeBluetoothUUIDsFromBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean removeBluetoothUUIDsFromWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setBluetooth(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setBluetoothLogEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setDesktopConnectivityState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setDiscoverableState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setLimitedDiscoverableState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setPairingState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothPolicy
            public boolean setProfileState(ContextInfo contextInfo, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBluetoothPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IBluetoothPolicy)) ? new Proxy(iBinder) : (IBluetoothPolicy) queryLocalInterface;
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowBluetoothDataTransfer = setAllowBluetoothDataTransfer(contextInfo, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowBluetoothDataTransfer);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowBluetoothDataTransfer2 = getAllowBluetoothDataTransfer(contextInfo2, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowBluetoothDataTransfer2);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean bluetooth = setBluetooth(contextInfo3, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetooth);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowBluetooth = allowBluetooth(contextInfo4, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowBluetooth);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothEnabled = isBluetoothEnabled(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothEnabled);
                    return true;
                case 6:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothEnabledWithMsg = isBluetoothEnabledWithMsg(readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothEnabledWithMsg);
                    return true;
                case 7:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean pairingState = setPairingState(contextInfo6, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pairingState);
                    return true;
                case 8:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPairingEnabled = isPairingEnabled(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPairingEnabled);
                    return true;
                case 9:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowOutgoingCalls = allowOutgoingCalls(contextInfo8, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowOutgoingCalls);
                    return true;
                case 10:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isOutgoingCallsAllowed = isOutgoingCallsAllowed(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOutgoingCallsAllowed);
                    return true;
                case 11:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean limitedDiscoverableState = setLimitedDiscoverableState(contextInfo10, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitedDiscoverableState);
                    return true;
                case 12:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isLimitedDiscoverableEnabled = isLimitedDiscoverableEnabled(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLimitedDiscoverableEnabled);
                    return true;
                case 13:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean profileState = setProfileState(contextInfo12, readBoolean9, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(profileState);
                    return true;
                case 14:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isProfileEnabled = isProfileEnabled(contextInfo13, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProfileEnabled);
                    return true;
                case 15:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean discoverableState = setDiscoverableState(contextInfo14, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(discoverableState);
                    return true;
                case 16:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isDiscoverableEnabled = isDiscoverableEnabled(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDiscoverableEnabled);
                    return true;
                case 17:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean desktopConnectivityState = setDesktopConnectivityState(contextInfo16, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(desktopConnectivityState);
                    return true;
                case 18:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isDesktopConnectivityEnabled = isDesktopConnectivityEnabled(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDesktopConnectivityEnabled);
                    return true;
                case 19:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addBluetoothUUIDsToBlackList = addBluetoothUUIDsToBlackList(contextInfo18, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addBluetoothUUIDsToBlackList);
                    return true;
                case 20:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeBluetoothUUIDsFromBlackList = removeBluetoothUUIDsFromBlackList(contextInfo19, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeBluetoothUUIDsFromBlackList);
                    return true;
                case 21:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearBluetoothUUIDsFromBlackList = clearBluetoothUUIDsFromBlackList(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearBluetoothUUIDsFromBlackList);
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
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addBluetoothUUIDsToWhiteList = addBluetoothUUIDsToWhiteList(contextInfo22, createStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addBluetoothUUIDsToWhiteList);
                    return true;
                case 24:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeBluetoothUUIDsFromWhiteList = removeBluetoothUUIDsFromWhiteList(contextInfo23, createStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeBluetoothUUIDsFromWhiteList);
                    return true;
                case 25:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearBluetoothUUIDsFromWhiteList = clearBluetoothUUIDsFromWhiteList(contextInfo24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearBluetoothUUIDsFromWhiteList);
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
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean activateBluetoothUUIDRestriction = activateBluetoothUUIDRestriction(contextInfo26, readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activateBluetoothUUIDRestriction);
                    return true;
                case 28:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addBluetoothDevicesToBlackList = addBluetoothDevicesToBlackList(contextInfo27, createStringArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addBluetoothDevicesToBlackList);
                    return true;
                case 29:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeBluetoothDevicesFromBlackList = removeBluetoothDevicesFromBlackList(contextInfo28, createStringArrayList6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeBluetoothDevicesFromBlackList);
                    return true;
                case 30:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearBluetoothDevicesFromBlackList = clearBluetoothDevicesFromBlackList(contextInfo29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearBluetoothDevicesFromBlackList);
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
                    ArrayList<String> createStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addBluetoothDevicesToWhiteList = addBluetoothDevicesToWhiteList(contextInfo31, createStringArrayList7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addBluetoothDevicesToWhiteList);
                    return true;
                case 33:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeBluetoothDevicesFromWhiteList = removeBluetoothDevicesFromWhiteList(contextInfo32, createStringArrayList8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeBluetoothDevicesFromWhiteList);
                    return true;
                case 34:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearBluetoothDevicesFromWhiteList = clearBluetoothDevicesFromWhiteList(contextInfo33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearBluetoothDevicesFromWhiteList);
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
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean activateBluetoothDeviceRestriction = activateBluetoothDeviceRestriction(contextInfo35, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activateBluetoothDeviceRestriction);
                    return true;
                case 37:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothUUIDRestrictionActive = isBluetoothUUIDRestrictionActive(contextInfo36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothUUIDRestrictionActive);
                    return true;
                case 38:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothDeviceRestrictionActive = isBluetoothDeviceRestrictionActive(contextInfo37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothDeviceRestrictionActive);
                    return true;
                case 39:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothUUIDAllowed = isBluetoothUUIDAllowed(contextInfo38, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothUUIDAllowed);
                    return true;
                case 40:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothDeviceAllowed = isBluetoothDeviceAllowed(contextInfo39, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothDeviceAllowed);
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
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean bluetoothLogEnabled = setBluetoothLogEnabled(contextInfo44, readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetoothLogEnabled);
                    return true;
                case 46:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isProfileEnabledInternal = isProfileEnabledInternal(readInt3, readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProfileEnabledInternal);
                    return true;
                case 47:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothLogEnabled = isBluetoothLogEnabled(contextInfo45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothLogEnabled);
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
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean bluetoothLog2 = bluetoothLog(contextInfo47, readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetoothLog2);
                    return true;
                case 50:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowCallerIDDisplay = allowCallerIDDisplay(contextInfo48, readBoolean16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowCallerIDDisplay);
                    return true;
                case 51:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCallerIDDisplayAllowed = isCallerIDDisplayAllowed(contextInfo49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerIDDisplayAllowed);
                    return true;
                case 52:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowBLE = allowBLE(contextInfo50, readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowBLE);
                    return true;
                case 53:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBLEAllowed = isBLEAllowed(contextInfo51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBLEAllowed);
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
