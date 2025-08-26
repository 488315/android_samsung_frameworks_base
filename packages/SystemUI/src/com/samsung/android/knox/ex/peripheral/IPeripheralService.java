package com.samsung.android.knox.ex.peripheral;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ex.peripheral.IDataListener;
import com.samsung.android.knox.ex.peripheral.IInfoListener;
import com.samsung.android.knox.ex.peripheral.IResultListener;
import com.samsung.android.knox.ex.peripheral.IStateListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IPeripheralService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ex.peripheral.IPeripheralService";

    public class Default implements IPeripheralService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int beep(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int check(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int clearMemory(String str, String str2, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int connectPeripheral(Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int disable() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int disconnectPeripheral(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int enable(Bundle bundle, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int getAvailablePeripherals(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int getBluetoothPeripherals(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int getConfiguration(String str, List<String> list, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int getConnectionProfile(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int getInformation(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int getPairingBarcodeData(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public List<String> getPluginsToSetup() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int getStoredData(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int getSupportedPeripherals(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public boolean isEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public boolean isStarted() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int registerDataListener(IDataListener iDataListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int registerInfoListener(IInfoListener iInfoListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int registerStateListener(IStateListener iStateListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int resetPeripheral(String str, String str2, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int setConfiguration(String str, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int setConnectionProfile(String str, String str2, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int start(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int startAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int startBarcodeScan(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int stop(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int stopAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int stopBarcodeScan(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int stopPairingPeripheral(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int unregisterDataListener(IDataListener iDataListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int unregisterInfoListener(IInfoListener iInfoListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int unregisterStateListener(IStateListener iStateListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
        public int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }
    }

    int beep(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int check(IResultListener iResultListener) throws RemoteException;

    int clearMemory(String str, String str2, IResultListener iResultListener) throws RemoteException;

    int connectPeripheral(Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int disable() throws RemoteException;

    int disconnectPeripheral(String str, IResultListener iResultListener) throws RemoteException;

    int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int enable(Bundle bundle, boolean z) throws RemoteException;

    int getAvailablePeripherals(IResultListener iResultListener) throws RemoteException;

    int getBluetoothPeripherals(String str, IResultListener iResultListener) throws RemoteException;

    int getConfiguration(String str, List<String> list, IResultListener iResultListener) throws RemoteException;

    int getConnectionProfile(String str, IResultListener iResultListener) throws RemoteException;

    int getInformation(IResultListener iResultListener) throws RemoteException;

    int getPairingBarcodeData(String str, IResultListener iResultListener) throws RemoteException;

    List<String> getPluginsToSetup() throws RemoteException;

    int getStoredData(String str, IResultListener iResultListener) throws RemoteException;

    int getSupportedPeripherals(IResultListener iResultListener) throws RemoteException;

    boolean isEnabled() throws RemoteException;

    boolean isStarted() throws RemoteException;

    int registerDataListener(IDataListener iDataListener) throws RemoteException;

    int registerInfoListener(IInfoListener iInfoListener) throws RemoteException;

    int registerStateListener(IStateListener iStateListener) throws RemoteException;

    int resetPeripheral(String str, String str2, IResultListener iResultListener) throws RemoteException;

    int setConfiguration(String str, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int setConnectionProfile(String str, String str2, IResultListener iResultListener) throws RemoteException;

    int start(IResultListener iResultListener) throws RemoteException;

    int startAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException;

    int startBarcodeScan(String str, IResultListener iResultListener) throws RemoteException;

    int stop(IResultListener iResultListener) throws RemoteException;

    int stopAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException;

    int stopBarcodeScan(String str, IResultListener iResultListener) throws RemoteException;

    int stopPairingPeripheral(IResultListener iResultListener) throws RemoteException;

    int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int unregisterDataListener(IDataListener iDataListener) throws RemoteException;

    int unregisterInfoListener(IInfoListener iInfoListener) throws RemoteException;

    int unregisterStateListener(IStateListener iStateListener) throws RemoteException;

    int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    public abstract class Stub extends Binder implements IPeripheralService {
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

        class Proxy implements IPeripheralService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int beep(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int check(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int clearMemory(String str, String str2, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int connectPeripheral(Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int disable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int disconnectPeripheral(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int enable(Bundle bundle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int getAvailablePeripherals(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int getBluetoothPeripherals(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int getConfiguration(String str, List<String> list, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int getConnectionProfile(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int getInformation(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IPeripheralService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int getPairingBarcodeData(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public List<String> getPluginsToSetup() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int getStoredData(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int getSupportedPeripherals(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public boolean isEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public boolean isStarted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int registerDataListener(IDataListener iDataListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDataListener);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int registerInfoListener(IInfoListener iInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInfoListener);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int registerStateListener(IStateListener iStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStateListener);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int resetPeripheral(String str, String str2, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int setConfiguration(String str, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int setConnectionProfile(String str, String str2, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int start(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int startAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int startBarcodeScan(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int stop(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int stopAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int stopBarcodeScan(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int stopPairingPeripheral(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int unregisterDataListener(IDataListener iDataListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDataListener);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int unregisterInfoListener(IInfoListener iInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInfoListener);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int unregisterStateListener(IStateListener iStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStateListener);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralService
            public int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPeripheralService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IPeripheralService)) ? new Proxy(iBinder) : (IPeripheralService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPeripheralService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPeripheralService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean zIsEnabled = isEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEnabled);
                    return true;
                case 2:
                    boolean zIsStarted = isStarted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStarted);
                    return true;
                case 3:
                    List<String> pluginsToSetup = getPluginsToSetup();
                    parcel2.writeNoException();
                    parcel2.writeStringList(pluginsToSetup);
                    return true;
                case 4:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iEnable = enable(bundle, z);
                    parcel2.writeNoException();
                    parcel2.writeInt(iEnable);
                    return true;
                case 5:
                    int iDisable = disable();
                    parcel2.writeNoException();
                    parcel2.writeInt(iDisable);
                    return true;
                case 6:
                    IResultListener iResultListenerAsInterface = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iCheck = check(iResultListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheck);
                    return true;
                case 7:
                    IResultListener iResultListenerAsInterface2 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStart = start(iResultListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStart);
                    return true;
                case 8:
                    IResultListener iResultListenerAsInterface3 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStop = stop(iResultListenerAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStop);
                    return true;
                case 9:
                    IResultListener iResultListenerAsInterface4 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int availablePeripherals = getAvailablePeripherals(iResultListenerAsInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(availablePeripherals);
                    return true;
                case 10:
                    IResultListener iResultListenerAsInterface5 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int information = getInformation(iResultListenerAsInterface5);
                    parcel2.writeNoException();
                    parcel2.writeInt(information);
                    return true;
                case 11:
                    String string = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    IResultListener iResultListenerAsInterface6 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int configuration = getConfiguration(string, arrayListCreateStringArrayList, iResultListenerAsInterface6);
                    parcel2.writeNoException();
                    parcel2.writeInt(configuration);
                    return true;
                case 12:
                    String string2 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface7 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int configuration2 = setConfiguration(string2, bundle2, iResultListenerAsInterface7);
                    parcel2.writeNoException();
                    parcel2.writeInt(configuration2);
                    return true;
                case 13:
                    IDataListener iDataListenerAsInterface = IDataListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterDataListener = registerDataListener(iDataListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterDataListener);
                    return true;
                case 14:
                    IDataListener iDataListenerAsInterface2 = IDataListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iUnregisterDataListener = unregisterDataListener(iDataListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnregisterDataListener);
                    return true;
                case 15:
                    IInfoListener iInfoListenerAsInterface = IInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterInfoListener = registerInfoListener(iInfoListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterInfoListener);
                    return true;
                case 16:
                    IInfoListener iInfoListenerAsInterface2 = IInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iUnregisterInfoListener = unregisterInfoListener(iInfoListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnregisterInfoListener);
                    return true;
                case 17:
                    IStateListener iStateListenerAsInterface = IStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterStateListener = registerStateListener(iStateListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterStateListener);
                    return true;
                case 18:
                    IStateListener iStateListenerAsInterface2 = IStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iUnregisterStateListener = unregisterStateListener(iStateListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnregisterStateListener);
                    return true;
                case 19:
                    String string3 = parcel.readString();
                    IResultListener iResultListenerAsInterface8 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStartBarcodeScan = startBarcodeScan(string3, iResultListenerAsInterface8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartBarcodeScan);
                    return true;
                case 20:
                    String string4 = parcel.readString();
                    IResultListener iResultListenerAsInterface9 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStopBarcodeScan = stopBarcodeScan(string4, iResultListenerAsInterface9);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopBarcodeScan);
                    return true;
                case 21:
                    String string5 = parcel.readString();
                    IResultListener iResultListenerAsInterface10 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int storedData = getStoredData(string5, iResultListenerAsInterface10);
                    parcel2.writeNoException();
                    parcel2.writeInt(storedData);
                    return true;
                case 22:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    IResultListener iResultListenerAsInterface11 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iClearMemory = clearMemory(string6, string7, iResultListenerAsInterface11);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearMemory);
                    return true;
                case 23:
                    String string8 = parcel.readString();
                    IResultListener iResultListenerAsInterface12 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStartAutoTriggerMode = startAutoTriggerMode(string8, iResultListenerAsInterface12);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartAutoTriggerMode);
                    return true;
                case 24:
                    String string9 = parcel.readString();
                    IResultListener iResultListenerAsInterface13 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStopAutoTriggerMode = stopAutoTriggerMode(string9, iResultListenerAsInterface13);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopAutoTriggerMode);
                    return true;
                case 25:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    IResultListener iResultListenerAsInterface14 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iResetPeripheral = resetPeripheral(string10, string11, iResultListenerAsInterface14);
                    parcel2.writeNoException();
                    parcel2.writeInt(iResetPeripheral);
                    return true;
                case 26:
                    String string12 = parcel.readString();
                    int i3 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface15 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iTriggerVendorCommand = triggerVendorCommand(string12, i3, bundle3, iResultListenerAsInterface15);
                    parcel2.writeNoException();
                    parcel2.writeInt(iTriggerVendorCommand);
                    return true;
                case 27:
                    String string13 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface16 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iUpdateFirmware = updateFirmware(string13, bArrCreateByteArray, i4, i5, bundle4, iResultListenerAsInterface16);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateFirmware);
                    return true;
                case 28:
                    String string14 = parcel.readString();
                    IResultListener iResultListenerAsInterface17 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int connectionProfile = getConnectionProfile(string14, iResultListenerAsInterface17);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectionProfile);
                    return true;
                case 29:
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    IResultListener iResultListenerAsInterface18 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int connectionProfile2 = setConnectionProfile(string15, string16, iResultListenerAsInterface18);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectionProfile2);
                    return true;
                case 30:
                    IResultListener iResultListenerAsInterface19 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int supportedPeripherals = getSupportedPeripherals(iResultListenerAsInterface19);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedPeripherals);
                    return true;
                case 31:
                    String string17 = parcel.readString();
                    IResultListener iResultListenerAsInterface20 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int pairingBarcodeData = getPairingBarcodeData(string17, iResultListenerAsInterface20);
                    parcel2.writeNoException();
                    parcel2.writeInt(pairingBarcodeData);
                    return true;
                case 32:
                    IResultListener iResultListenerAsInterface21 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStopPairingPeripheral = stopPairingPeripheral(iResultListenerAsInterface21);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopPairingPeripheral);
                    return true;
                case 33:
                    String string18 = parcel.readString();
                    IResultListener iResultListenerAsInterface22 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int bluetoothPeripherals = getBluetoothPeripherals(string18, iResultListenerAsInterface22);
                    parcel2.writeNoException();
                    parcel2.writeInt(bluetoothPeripherals);
                    return true;
                case 34:
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface23 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iConnectPeripheral = connectPeripheral(bundle5, iResultListenerAsInterface23);
                    parcel2.writeNoException();
                    parcel2.writeInt(iConnectPeripheral);
                    return true;
                case 35:
                    String string19 = parcel.readString();
                    IResultListener iResultListenerAsInterface24 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iDisconnectPeripheral = disconnectPeripheral(string19, iResultListenerAsInterface24);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDisconnectPeripheral);
                    return true;
                case 36:
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    int i6 = parcel.readInt();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface25 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iDisplayText = displayText(string20, string21, i6, bundle6, iResultListenerAsInterface25);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDisplayText);
                    return true;
                case 37:
                    String string22 = parcel.readString();
                    int i7 = parcel.readInt();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface26 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iBeep = beep(string22, i7, bundle7, iResultListenerAsInterface26);
                    parcel2.writeNoException();
                    parcel2.writeInt(iBeep);
                    return true;
                case 38:
                    String string23 = parcel.readString();
                    int i8 = parcel.readInt();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface27 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iVibrate = vibrate(string23, i8, bundle8, iResultListenerAsInterface27);
                    parcel2.writeNoException();
                    parcel2.writeInt(iVibrate);
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
