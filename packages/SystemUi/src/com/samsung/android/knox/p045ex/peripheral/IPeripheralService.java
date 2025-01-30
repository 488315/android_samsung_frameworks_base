package com.samsung.android.knox.p045ex.peripheral;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.p045ex.peripheral.IDataListener;
import com.samsung.android.knox.p045ex.peripheral.IInfoListener;
import com.samsung.android.knox.p045ex.peripheral.IResultListener;
import com.samsung.android.knox.p045ex.peripheral.IStateListener;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IPeripheralService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ex.peripheral.IPeripheralService";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IPeripheralService {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int beep(String str, int i, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int check(IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int clearMemory(String str, String str2, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int connectPeripheral(Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int disable() {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int disconnectPeripheral(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int enable(Bundle bundle, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int getAvailablePeripherals(IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int getBluetoothPeripherals(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int getConfiguration(String str, List<String> list, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int getConnectionProfile(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int getInformation(IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int getPairingBarcodeData(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final List<String> getPluginsToSetup() {
            return null;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int getStoredData(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int getSupportedPeripherals(IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final boolean isEnabled() {
            return false;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final boolean isStarted() {
            return false;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int registerDataListener(IDataListener iDataListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int registerInfoListener(IInfoListener iInfoListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int registerStateListener(IStateListener iStateListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int resetPeripheral(String str, String str2, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int setConfiguration(String str, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int setConnectionProfile(String str, String str2, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int start(IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int startAutoTriggerMode(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int startBarcodeScan(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int stop(IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int stopAutoTriggerMode(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int stopBarcodeScan(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int stopPairingPeripheral(IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int unregisterDataListener(IDataListener iDataListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int unregisterInfoListener(IInfoListener iInfoListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int unregisterStateListener(IStateListener iStateListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
        public final int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }
    }

    int beep(String str, int i, Bundle bundle, IResultListener iResultListener);

    int check(IResultListener iResultListener);

    int clearMemory(String str, String str2, IResultListener iResultListener);

    int connectPeripheral(Bundle bundle, IResultListener iResultListener);

    int disable();

    int disconnectPeripheral(String str, IResultListener iResultListener);

    int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener);

    int enable(Bundle bundle, boolean z);

    int getAvailablePeripherals(IResultListener iResultListener);

    int getBluetoothPeripherals(String str, IResultListener iResultListener);

    int getConfiguration(String str, List<String> list, IResultListener iResultListener);

    int getConnectionProfile(String str, IResultListener iResultListener);

    int getInformation(IResultListener iResultListener);

    int getPairingBarcodeData(String str, IResultListener iResultListener);

    List<String> getPluginsToSetup();

    int getStoredData(String str, IResultListener iResultListener);

    int getSupportedPeripherals(IResultListener iResultListener);

    boolean isEnabled();

    boolean isStarted();

    int registerDataListener(IDataListener iDataListener);

    int registerInfoListener(IInfoListener iInfoListener);

    int registerStateListener(IStateListener iStateListener);

    int resetPeripheral(String str, String str2, IResultListener iResultListener);

    int setConfiguration(String str, Bundle bundle, IResultListener iResultListener);

    int setConnectionProfile(String str, String str2, IResultListener iResultListener);

    int start(IResultListener iResultListener);

    int startAutoTriggerMode(String str, IResultListener iResultListener);

    int startBarcodeScan(String str, IResultListener iResultListener);

    int stop(IResultListener iResultListener);

    int stopAutoTriggerMode(String str, IResultListener iResultListener);

    int stopBarcodeScan(String str, IResultListener iResultListener);

    int stopPairingPeripheral(IResultListener iResultListener);

    int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener);

    int unregisterDataListener(IDataListener iDataListener);

    int unregisterInfoListener(IInfoListener iInfoListener);

    int unregisterStateListener(IStateListener iStateListener);

    int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener);

    int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IPeripheralService {
        public static final int TRANSACTION_beep = 37;
        public static final int TRANSACTION_check = 6;
        public static final int TRANSACTION_clearMemory = 22;
        public static final int TRANSACTION_connectPeripheral = 34;
        public static final int TRANSACTION_disable = 5;
        public static final int TRANSACTION_disconnectPeripheral = 35;
        public static final int TRANSACTION_displayText = 36;
        public static final int TRANSACTION_enable = 4;
        public static final int TRANSACTION_getAvailablePeripherals = 9;
        public static final int TRANSACTION_getBluetoothPeripherals = 33;
        public static final int TRANSACTION_getConfiguration = 11;
        public static final int TRANSACTION_getConnectionProfile = 28;
        public static final int TRANSACTION_getInformation = 10;
        public static final int TRANSACTION_getPairingBarcodeData = 31;
        public static final int TRANSACTION_getPluginsToSetup = 3;
        public static final int TRANSACTION_getStoredData = 21;
        public static final int TRANSACTION_getSupportedPeripherals = 30;
        public static final int TRANSACTION_isEnabled = 1;
        public static final int TRANSACTION_isStarted = 2;
        public static final int TRANSACTION_registerDataListener = 13;
        public static final int TRANSACTION_registerInfoListener = 15;
        public static final int TRANSACTION_registerStateListener = 17;
        public static final int TRANSACTION_resetPeripheral = 25;
        public static final int TRANSACTION_setConfiguration = 12;
        public static final int TRANSACTION_setConnectionProfile = 29;
        public static final int TRANSACTION_start = 7;
        public static final int TRANSACTION_startAutoTriggerMode = 23;
        public static final int TRANSACTION_startBarcodeScan = 19;
        public static final int TRANSACTION_stop = 8;
        public static final int TRANSACTION_stopAutoTriggerMode = 24;
        public static final int TRANSACTION_stopBarcodeScan = 20;
        public static final int TRANSACTION_stopPairingPeripheral = 32;
        public static final int TRANSACTION_triggerVendorCommand = 26;
        public static final int TRANSACTION_unregisterDataListener = 14;
        public static final int TRANSACTION_unregisterInfoListener = 16;
        public static final int TRANSACTION_unregisterStateListener = 18;
        public static final int TRANSACTION_updateFirmware = 27;
        public static final int TRANSACTION_vibrate = 38;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IPeripheralService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int beep(String str, int i, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int check(IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int clearMemory(String str, String str2, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int connectPeripheral(Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int disable() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int disconnectPeripheral(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int enable(Bundle bundle, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int getAvailablePeripherals(IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int getBluetoothPeripherals(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int getConfiguration(String str, List<String> list, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int getConnectionProfile(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int getInformation(IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IPeripheralService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int getPairingBarcodeData(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final List<String> getPluginsToSetup() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int getStoredData(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int getSupportedPeripherals(IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final boolean isEnabled() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final boolean isStarted() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int registerDataListener(IDataListener iDataListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iDataListener);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int registerInfoListener(IInfoListener iInfoListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iInfoListener);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int registerStateListener(IStateListener iStateListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iStateListener);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int resetPeripheral(String str, String str2, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int setConfiguration(String str, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int setConnectionProfile(String str, String str2, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int start(IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int startAutoTriggerMode(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int startBarcodeScan(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int stop(IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int stopAutoTriggerMode(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int stopBarcodeScan(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int stopPairingPeripheral(IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int unregisterDataListener(IDataListener iDataListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iDataListener);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int unregisterInfoListener(IInfoListener iInfoListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iInfoListener);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int unregisterStateListener(IStateListener iStateListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeStrongInterface(iStateListener);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p045ex.peripheral.IPeripheralService
            public final int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IPeripheralService.DESCRIPTOR);
        }

        public static IPeripheralService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPeripheralService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPeripheralService)) ? new Proxy(iBinder) : (IPeripheralService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPeripheralService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPeripheralService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isEnabled = isEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnabled);
                    return true;
                case 2:
                    boolean isStarted = isStarted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStarted);
                    return true;
                case 3:
                    List<String> pluginsToSetup = getPluginsToSetup();
                    parcel2.writeNoException();
                    parcel2.writeStringList(pluginsToSetup);
                    return true;
                case 4:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int enable = enable(bundle, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(enable);
                    return true;
                case 5:
                    int disable = disable();
                    parcel2.writeNoException();
                    parcel2.writeInt(disable);
                    return true;
                case 6:
                    IResultListener asInterface = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int check = check(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(check);
                    return true;
                case 7:
                    IResultListener asInterface2 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int start = start(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(start);
                    return true;
                case 8:
                    IResultListener asInterface3 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stop = stop(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(stop);
                    return true;
                case 9:
                    IResultListener asInterface4 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int availablePeripherals = getAvailablePeripherals(asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(availablePeripherals);
                    return true;
                case 10:
                    IResultListener asInterface5 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int information = getInformation(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeInt(information);
                    return true;
                case 11:
                    String readString = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    IResultListener asInterface6 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int configuration = getConfiguration(readString, createStringArrayList, asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeInt(configuration);
                    return true;
                case 12:
                    String readString2 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface7 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int configuration2 = setConfiguration(readString2, bundle2, asInterface7);
                    parcel2.writeNoException();
                    parcel2.writeInt(configuration2);
                    return true;
                case 13:
                    IDataListener asInterface8 = IDataListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int registerDataListener = registerDataListener(asInterface8);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerDataListener);
                    return true;
                case 14:
                    IDataListener asInterface9 = IDataListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int unregisterDataListener = unregisterDataListener(asInterface9);
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterDataListener);
                    return true;
                case 15:
                    IInfoListener asInterface10 = IInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int registerInfoListener = registerInfoListener(asInterface10);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerInfoListener);
                    return true;
                case 16:
                    IInfoListener asInterface11 = IInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int unregisterInfoListener = unregisterInfoListener(asInterface11);
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterInfoListener);
                    return true;
                case 17:
                    IStateListener asInterface12 = IStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int registerStateListener = registerStateListener(asInterface12);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerStateListener);
                    return true;
                case 18:
                    IStateListener asInterface13 = IStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int unregisterStateListener = unregisterStateListener(asInterface13);
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterStateListener);
                    return true;
                case 19:
                    String readString3 = parcel.readString();
                    IResultListener asInterface14 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startBarcodeScan = startBarcodeScan(readString3, asInterface14);
                    parcel2.writeNoException();
                    parcel2.writeInt(startBarcodeScan);
                    return true;
                case 20:
                    String readString4 = parcel.readString();
                    IResultListener asInterface15 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopBarcodeScan = stopBarcodeScan(readString4, asInterface15);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopBarcodeScan);
                    return true;
                case 21:
                    String readString5 = parcel.readString();
                    IResultListener asInterface16 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int storedData = getStoredData(readString5, asInterface16);
                    parcel2.writeNoException();
                    parcel2.writeInt(storedData);
                    return true;
                case 22:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    IResultListener asInterface17 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int clearMemory = clearMemory(readString6, readString7, asInterface17);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearMemory);
                    return true;
                case 23:
                    String readString8 = parcel.readString();
                    IResultListener asInterface18 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startAutoTriggerMode = startAutoTriggerMode(readString8, asInterface18);
                    parcel2.writeNoException();
                    parcel2.writeInt(startAutoTriggerMode);
                    return true;
                case 24:
                    String readString9 = parcel.readString();
                    IResultListener asInterface19 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopAutoTriggerMode = stopAutoTriggerMode(readString9, asInterface19);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopAutoTriggerMode);
                    return true;
                case 25:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    IResultListener asInterface20 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int resetPeripheral = resetPeripheral(readString10, readString11, asInterface20);
                    parcel2.writeNoException();
                    parcel2.writeInt(resetPeripheral);
                    return true;
                case 26:
                    String readString12 = parcel.readString();
                    int readInt = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface21 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int triggerVendorCommand = triggerVendorCommand(readString12, readInt, bundle3, asInterface21);
                    parcel2.writeNoException();
                    parcel2.writeInt(triggerVendorCommand);
                    return true;
                case 27:
                    String readString13 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface22 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int updateFirmware = updateFirmware(readString13, createByteArray, readInt2, readInt3, bundle4, asInterface22);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateFirmware);
                    return true;
                case 28:
                    String readString14 = parcel.readString();
                    IResultListener asInterface23 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int connectionProfile = getConnectionProfile(readString14, asInterface23);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectionProfile);
                    return true;
                case 29:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    IResultListener asInterface24 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int connectionProfile2 = setConnectionProfile(readString15, readString16, asInterface24);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectionProfile2);
                    return true;
                case 30:
                    IResultListener asInterface25 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int supportedPeripherals = getSupportedPeripherals(asInterface25);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedPeripherals);
                    return true;
                case 31:
                    String readString17 = parcel.readString();
                    IResultListener asInterface26 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int pairingBarcodeData = getPairingBarcodeData(readString17, asInterface26);
                    parcel2.writeNoException();
                    parcel2.writeInt(pairingBarcodeData);
                    return true;
                case 32:
                    IResultListener asInterface27 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopPairingPeripheral = stopPairingPeripheral(asInterface27);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopPairingPeripheral);
                    return true;
                case 33:
                    String readString18 = parcel.readString();
                    IResultListener asInterface28 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int bluetoothPeripherals = getBluetoothPeripherals(readString18, asInterface28);
                    parcel2.writeNoException();
                    parcel2.writeInt(bluetoothPeripherals);
                    return true;
                case 34:
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface29 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int connectPeripheral = connectPeripheral(bundle5, asInterface29);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectPeripheral);
                    return true;
                case 35:
                    String readString19 = parcel.readString();
                    IResultListener asInterface30 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int disconnectPeripheral = disconnectPeripheral(readString19, asInterface30);
                    parcel2.writeNoException();
                    parcel2.writeInt(disconnectPeripheral);
                    return true;
                case 36:
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface31 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int displayText = displayText(readString20, readString21, readInt4, bundle6, asInterface31);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayText);
                    return true;
                case 37:
                    String readString22 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface32 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int beep = beep(readString22, readInt5, bundle7, asInterface32);
                    parcel2.writeNoException();
                    parcel2.writeInt(beep);
                    return true;
                case 38:
                    String readString23 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface33 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int vibrate = vibrate(readString23, readInt6, bundle8, asInterface33);
                    parcel2.writeNoException();
                    parcel2.writeInt(vibrate);
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
