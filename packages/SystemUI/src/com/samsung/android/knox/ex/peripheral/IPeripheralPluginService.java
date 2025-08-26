package com.samsung.android.knox.ex.peripheral;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ex.peripheral.IEventListener;
import com.samsung.android.knox.ex.peripheral.IResultListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IPeripheralPluginService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ex.peripheral.IPeripheralPluginService";

    public class Default implements IPeripheralPluginService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int beep(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int clearMemory(String str, String str2, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int connect(String str, String str2, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int connectEx(String str, String str2, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int disconnect(String str, String str2, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getAllState(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getAvailablePeripherals(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getConfiguration(String str, List<String> list, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getConnectionProfile(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getPairingBarcodeData(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getStoredData(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getSupportedPeripherals(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public boolean isStarted() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int resetPeripheral(String str, String str2, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int setConfiguration(String str, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int setConnectionProfile(String str, String str2, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int start(Bundle bundle, IEventListener iEventListener, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int startAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int startBarcodeScan(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int stop(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int stopAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int stopBarcodeScan(String str, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            return 0;
        }
    }

    int beep(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int clearMemory(String str, String str2, IResultListener iResultListener) throws RemoteException;

    int connect(String str, String str2, IResultListener iResultListener) throws RemoteException;

    int connectEx(String str, String str2, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int disconnect(String str, String str2, IResultListener iResultListener) throws RemoteException;

    int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int getAllState(IResultListener iResultListener) throws RemoteException;

    int getAvailablePeripherals(IResultListener iResultListener) throws RemoteException;

    int getConfiguration(String str, List<String> list, IResultListener iResultListener) throws RemoteException;

    int getConnectionProfile(String str, IResultListener iResultListener) throws RemoteException;

    int getPairingBarcodeData(String str, IResultListener iResultListener) throws RemoteException;

    int getStoredData(String str, IResultListener iResultListener) throws RemoteException;

    int getSupportedPeripherals(IResultListener iResultListener) throws RemoteException;

    boolean isStarted() throws RemoteException;

    int resetPeripheral(String str, String str2, IResultListener iResultListener) throws RemoteException;

    int setConfiguration(String str, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int setConnectionProfile(String str, String str2, IResultListener iResultListener) throws RemoteException;

    int start(Bundle bundle, IEventListener iEventListener, IResultListener iResultListener) throws RemoteException;

    int startAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException;

    int startBarcodeScan(String str, IResultListener iResultListener) throws RemoteException;

    int stop(IResultListener iResultListener) throws RemoteException;

    int stopAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException;

    int stopBarcodeScan(String str, IResultListener iResultListener) throws RemoteException;

    int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException;

    public abstract class Stub extends Binder implements IPeripheralPluginService {
        public static final int TRANSACTION_beep = 25;
        public static final int TRANSACTION_clearMemory = 14;
        public static final int TRANSACTION_connect = 4;
        public static final int TRANSACTION_connectEx = 22;
        public static final int TRANSACTION_disconnect = 5;
        public static final int TRANSACTION_displayText = 24;
        public static final int TRANSACTION_getAllState = 6;
        public static final int TRANSACTION_getAvailablePeripherals = 7;
        public static final int TRANSACTION_getConfiguration = 9;
        public static final int TRANSACTION_getConnectionProfile = 20;
        public static final int TRANSACTION_getPairingBarcodeData = 23;
        public static final int TRANSACTION_getStoredData = 13;
        public static final int TRANSACTION_getSupportedPeripherals = 8;
        public static final int TRANSACTION_isStarted = 1;
        public static final int TRANSACTION_resetPeripheral = 17;
        public static final int TRANSACTION_setConfiguration = 10;
        public static final int TRANSACTION_setConnectionProfile = 21;
        public static final int TRANSACTION_start = 2;
        public static final int TRANSACTION_startAutoTriggerMode = 15;
        public static final int TRANSACTION_startBarcodeScan = 11;
        public static final int TRANSACTION_stop = 3;
        public static final int TRANSACTION_stopAutoTriggerMode = 16;
        public static final int TRANSACTION_stopBarcodeScan = 12;
        public static final int TRANSACTION_triggerVendorCommand = 18;
        public static final int TRANSACTION_updateFirmware = 19;
        public static final int TRANSACTION_vibrate = 26;

        class Proxy implements IPeripheralPluginService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int beep(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int clearMemory(String str, String str2, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int connect(String str, String str2, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int connectEx(String str, String str2, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int disconnect(String str, String str2, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int getAllState(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int getAvailablePeripherals(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int getConfiguration(String str, List<String> list, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int getConnectionProfile(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
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

            public String getInterfaceDescriptor() {
                return IPeripheralPluginService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int getPairingBarcodeData(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int getStoredData(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int getSupportedPeripherals(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public boolean isStarted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int resetPeripheral(String str, String str2, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int setConfiguration(String str, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int setConnectionProfile(String str, String str2, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int start(Bundle bundle, IEventListener iEventListener, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iEventListener);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int startAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int startBarcodeScan(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int stop(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int stopAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int stopBarcodeScan(String str, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
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
        }

        public Stub() {
            attachInterface(this, IPeripheralPluginService.DESCRIPTOR);
        }

        public static IPeripheralPluginService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPeripheralPluginService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IPeripheralPluginService)) ? new Proxy(iBinder) : (IPeripheralPluginService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPeripheralPluginService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPeripheralPluginService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean zIsStarted = isStarted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStarted);
                    return true;
                case 2:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IEventListener iEventListenerAsInterface = IEventListener.Stub.asInterface(parcel.readStrongBinder());
                    IResultListener iResultListenerAsInterface = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStart = start(bundle, iEventListenerAsInterface, iResultListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStart);
                    return true;
                case 3:
                    IResultListener iResultListenerAsInterface2 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStop = stop(iResultListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStop);
                    return true;
                case 4:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    IResultListener iResultListenerAsInterface3 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iConnect = connect(string, string2, iResultListenerAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iConnect);
                    return true;
                case 5:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    IResultListener iResultListenerAsInterface4 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iDisconnect = disconnect(string3, string4, iResultListenerAsInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDisconnect);
                    return true;
                case 6:
                    IResultListener iResultListenerAsInterface5 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int allState = getAllState(iResultListenerAsInterface5);
                    parcel2.writeNoException();
                    parcel2.writeInt(allState);
                    return true;
                case 7:
                    IResultListener iResultListenerAsInterface6 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int availablePeripherals = getAvailablePeripherals(iResultListenerAsInterface6);
                    parcel2.writeNoException();
                    parcel2.writeInt(availablePeripherals);
                    return true;
                case 8:
                    IResultListener iResultListenerAsInterface7 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int supportedPeripherals = getSupportedPeripherals(iResultListenerAsInterface7);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedPeripherals);
                    return true;
                case 9:
                    String string5 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    IResultListener iResultListenerAsInterface8 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int configuration = getConfiguration(string5, arrayListCreateStringArrayList, iResultListenerAsInterface8);
                    parcel2.writeNoException();
                    parcel2.writeInt(configuration);
                    return true;
                case 10:
                    String string6 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface9 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int configuration2 = setConfiguration(string6, bundle2, iResultListenerAsInterface9);
                    parcel2.writeNoException();
                    parcel2.writeInt(configuration2);
                    return true;
                case 11:
                    String string7 = parcel.readString();
                    IResultListener iResultListenerAsInterface10 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStartBarcodeScan = startBarcodeScan(string7, iResultListenerAsInterface10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartBarcodeScan);
                    return true;
                case 12:
                    String string8 = parcel.readString();
                    IResultListener iResultListenerAsInterface11 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStopBarcodeScan = stopBarcodeScan(string8, iResultListenerAsInterface11);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopBarcodeScan);
                    return true;
                case 13:
                    String string9 = parcel.readString();
                    IResultListener iResultListenerAsInterface12 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int storedData = getStoredData(string9, iResultListenerAsInterface12);
                    parcel2.writeNoException();
                    parcel2.writeInt(storedData);
                    return true;
                case 14:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    IResultListener iResultListenerAsInterface13 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iClearMemory = clearMemory(string10, string11, iResultListenerAsInterface13);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearMemory);
                    return true;
                case 15:
                    String string12 = parcel.readString();
                    IResultListener iResultListenerAsInterface14 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStartAutoTriggerMode = startAutoTriggerMode(string12, iResultListenerAsInterface14);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartAutoTriggerMode);
                    return true;
                case 16:
                    String string13 = parcel.readString();
                    IResultListener iResultListenerAsInterface15 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStopAutoTriggerMode = stopAutoTriggerMode(string13, iResultListenerAsInterface15);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopAutoTriggerMode);
                    return true;
                case 17:
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    IResultListener iResultListenerAsInterface16 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iResetPeripheral = resetPeripheral(string14, string15, iResultListenerAsInterface16);
                    parcel2.writeNoException();
                    parcel2.writeInt(iResetPeripheral);
                    return true;
                case 18:
                    String string16 = parcel.readString();
                    int i3 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface17 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iTriggerVendorCommand = triggerVendorCommand(string16, i3, bundle3, iResultListenerAsInterface17);
                    parcel2.writeNoException();
                    parcel2.writeInt(iTriggerVendorCommand);
                    return true;
                case 19:
                    String string17 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface18 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iUpdateFirmware = updateFirmware(string17, bArrCreateByteArray, i4, i5, bundle4, iResultListenerAsInterface18);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateFirmware);
                    return true;
                case 20:
                    String string18 = parcel.readString();
                    IResultListener iResultListenerAsInterface19 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int connectionProfile = getConnectionProfile(string18, iResultListenerAsInterface19);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectionProfile);
                    return true;
                case 21:
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    IResultListener iResultListenerAsInterface20 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int connectionProfile2 = setConnectionProfile(string19, string20, iResultListenerAsInterface20);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectionProfile2);
                    return true;
                case 22:
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface21 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iConnectEx = connectEx(string21, string22, bundle5, iResultListenerAsInterface21);
                    parcel2.writeNoException();
                    parcel2.writeInt(iConnectEx);
                    return true;
                case 23:
                    String string23 = parcel.readString();
                    IResultListener iResultListenerAsInterface22 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int pairingBarcodeData = getPairingBarcodeData(string23, iResultListenerAsInterface22);
                    parcel2.writeNoException();
                    parcel2.writeInt(pairingBarcodeData);
                    return true;
                case 24:
                    String string24 = parcel.readString();
                    String string25 = parcel.readString();
                    int i6 = parcel.readInt();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface23 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iDisplayText = displayText(string24, string25, i6, bundle6, iResultListenerAsInterface23);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDisplayText);
                    return true;
                case 25:
                    String string26 = parcel.readString();
                    int i7 = parcel.readInt();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface24 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iBeep = beep(string26, i7, bundle7, iResultListenerAsInterface24);
                    parcel2.writeNoException();
                    parcel2.writeInt(iBeep);
                    return true;
                case 26:
                    String string27 = parcel.readString();
                    int i8 = parcel.readInt();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener iResultListenerAsInterface25 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iVibrate = vibrate(string27, i8, bundle8, iResultListenerAsInterface25);
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
