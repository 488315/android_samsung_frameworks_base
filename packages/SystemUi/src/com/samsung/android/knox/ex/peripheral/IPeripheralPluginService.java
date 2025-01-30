package com.samsung.android.knox.ex.peripheral;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ex.peripheral.IEventListener;
import com.samsung.android.knox.ex.peripheral.IResultListener;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IPeripheralPluginService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ex.peripheral.IPeripheralPluginService";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IPeripheralPluginService {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int beep(String str, int i, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int clearMemory(String str, String str2, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int connect(String str, String str2, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int connectEx(String str, String str2, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int disconnect(String str, String str2, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int getAllState(IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int getAvailablePeripherals(IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int getConfiguration(String str, List<String> list, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int getConnectionProfile(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int getPairingBarcodeData(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int getStoredData(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int getSupportedPeripherals(IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final boolean isStarted() {
            return false;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int resetPeripheral(String str, String str2, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int setConfiguration(String str, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int setConnectionProfile(String str, String str2, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int start(Bundle bundle, IEventListener iEventListener, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int startAutoTriggerMode(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int startBarcodeScan(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int stop(IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int stopAutoTriggerMode(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int stopBarcodeScan(String str, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public final int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener) {
            return 0;
        }
    }

    int beep(String str, int i, Bundle bundle, IResultListener iResultListener);

    int clearMemory(String str, String str2, IResultListener iResultListener);

    int connect(String str, String str2, IResultListener iResultListener);

    int connectEx(String str, String str2, Bundle bundle, IResultListener iResultListener);

    int disconnect(String str, String str2, IResultListener iResultListener);

    int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener);

    int getAllState(IResultListener iResultListener);

    int getAvailablePeripherals(IResultListener iResultListener);

    int getConfiguration(String str, List<String> list, IResultListener iResultListener);

    int getConnectionProfile(String str, IResultListener iResultListener);

    int getPairingBarcodeData(String str, IResultListener iResultListener);

    int getStoredData(String str, IResultListener iResultListener);

    int getSupportedPeripherals(IResultListener iResultListener);

    boolean isStarted();

    int resetPeripheral(String str, String str2, IResultListener iResultListener);

    int setConfiguration(String str, Bundle bundle, IResultListener iResultListener);

    int setConnectionProfile(String str, String str2, IResultListener iResultListener);

    int start(Bundle bundle, IEventListener iEventListener, IResultListener iResultListener);

    int startAutoTriggerMode(String str, IResultListener iResultListener);

    int startBarcodeScan(String str, IResultListener iResultListener);

    int stop(IResultListener iResultListener);

    int stopAutoTriggerMode(String str, IResultListener iResultListener);

    int stopBarcodeScan(String str, IResultListener iResultListener);

    int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener);

    int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener);

    int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IPeripheralPluginService {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IPeripheralPluginService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int beep(String str, int i, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int clearMemory(String str, String str2, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int connect(String str, String str2, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int connectEx(String str, String str2, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int disconnect(String str, String str2, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int getAllState(IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int getAvailablePeripherals(IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int getConfiguration(String str, List<String> list, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int getConnectionProfile(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
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

            public final String getInterfaceDescriptor() {
                return IPeripheralPluginService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int getPairingBarcodeData(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int getStoredData(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int getSupportedPeripherals(IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final boolean isStarted() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int resetPeripheral(String str, String str2, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int setConfiguration(String str, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int setConnectionProfile(String str, String str2, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int start(Bundle bundle, IEventListener iEventListener, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iEventListener);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int startAutoTriggerMode(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int startBarcodeScan(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int stop(IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int stopAutoTriggerMode(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int stopBarcodeScan(String str, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
            public final int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeripheralPluginService.DESCRIPTOR);
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
        }

        public Stub() {
            attachInterface(this, IPeripheralPluginService.DESCRIPTOR);
        }

        public static IPeripheralPluginService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPeripheralPluginService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPeripheralPluginService)) ? new Proxy(iBinder) : (IPeripheralPluginService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPeripheralPluginService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPeripheralPluginService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isStarted = isStarted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStarted);
                    return true;
                case 2:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IEventListener asInterface = IEventListener.Stub.asInterface(parcel.readStrongBinder());
                    IResultListener asInterface2 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int start = start(bundle, asInterface, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(start);
                    return true;
                case 3:
                    IResultListener asInterface3 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stop = stop(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(stop);
                    return true;
                case 4:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    IResultListener asInterface4 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int connect = connect(readString, readString2, asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(connect);
                    return true;
                case 5:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    IResultListener asInterface5 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int disconnect = disconnect(readString3, readString4, asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeInt(disconnect);
                    return true;
                case 6:
                    IResultListener asInterface6 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int allState = getAllState(asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeInt(allState);
                    return true;
                case 7:
                    IResultListener asInterface7 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int availablePeripherals = getAvailablePeripherals(asInterface7);
                    parcel2.writeNoException();
                    parcel2.writeInt(availablePeripherals);
                    return true;
                case 8:
                    IResultListener asInterface8 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int supportedPeripherals = getSupportedPeripherals(asInterface8);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedPeripherals);
                    return true;
                case 9:
                    String readString5 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    IResultListener asInterface9 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int configuration = getConfiguration(readString5, createStringArrayList, asInterface9);
                    parcel2.writeNoException();
                    parcel2.writeInt(configuration);
                    return true;
                case 10:
                    String readString6 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface10 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int configuration2 = setConfiguration(readString6, bundle2, asInterface10);
                    parcel2.writeNoException();
                    parcel2.writeInt(configuration2);
                    return true;
                case 11:
                    String readString7 = parcel.readString();
                    IResultListener asInterface11 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startBarcodeScan = startBarcodeScan(readString7, asInterface11);
                    parcel2.writeNoException();
                    parcel2.writeInt(startBarcodeScan);
                    return true;
                case 12:
                    String readString8 = parcel.readString();
                    IResultListener asInterface12 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopBarcodeScan = stopBarcodeScan(readString8, asInterface12);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopBarcodeScan);
                    return true;
                case 13:
                    String readString9 = parcel.readString();
                    IResultListener asInterface13 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int storedData = getStoredData(readString9, asInterface13);
                    parcel2.writeNoException();
                    parcel2.writeInt(storedData);
                    return true;
                case 14:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    IResultListener asInterface14 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int clearMemory = clearMemory(readString10, readString11, asInterface14);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearMemory);
                    return true;
                case 15:
                    String readString12 = parcel.readString();
                    IResultListener asInterface15 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startAutoTriggerMode = startAutoTriggerMode(readString12, asInterface15);
                    parcel2.writeNoException();
                    parcel2.writeInt(startAutoTriggerMode);
                    return true;
                case 16:
                    String readString13 = parcel.readString();
                    IResultListener asInterface16 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopAutoTriggerMode = stopAutoTriggerMode(readString13, asInterface16);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopAutoTriggerMode);
                    return true;
                case 17:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    IResultListener asInterface17 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int resetPeripheral = resetPeripheral(readString14, readString15, asInterface17);
                    parcel2.writeNoException();
                    parcel2.writeInt(resetPeripheral);
                    return true;
                case 18:
                    String readString16 = parcel.readString();
                    int readInt = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface18 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int triggerVendorCommand = triggerVendorCommand(readString16, readInt, bundle3, asInterface18);
                    parcel2.writeNoException();
                    parcel2.writeInt(triggerVendorCommand);
                    return true;
                case 19:
                    String readString17 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface19 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int updateFirmware = updateFirmware(readString17, createByteArray, readInt2, readInt3, bundle4, asInterface19);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateFirmware);
                    return true;
                case 20:
                    String readString18 = parcel.readString();
                    IResultListener asInterface20 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int connectionProfile = getConnectionProfile(readString18, asInterface20);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectionProfile);
                    return true;
                case 21:
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    IResultListener asInterface21 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int connectionProfile2 = setConnectionProfile(readString19, readString20, asInterface21);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectionProfile2);
                    return true;
                case 22:
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface22 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int connectEx = connectEx(readString21, readString22, bundle5, asInterface22);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectEx);
                    return true;
                case 23:
                    String readString23 = parcel.readString();
                    IResultListener asInterface23 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int pairingBarcodeData = getPairingBarcodeData(readString23, asInterface23);
                    parcel2.writeNoException();
                    parcel2.writeInt(pairingBarcodeData);
                    return true;
                case 24:
                    String readString24 = parcel.readString();
                    String readString25 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface24 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int displayText = displayText(readString24, readString25, readInt4, bundle6, asInterface24);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayText);
                    return true;
                case 25:
                    String readString26 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface25 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int beep = beep(readString26, readInt5, bundle7, asInterface25);
                    parcel2.writeNoException();
                    parcel2.writeInt(beep);
                    return true;
                case 26:
                    String readString27 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IResultListener asInterface26 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int vibrate = vibrate(readString27, readInt6, bundle8, asInterface26);
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
