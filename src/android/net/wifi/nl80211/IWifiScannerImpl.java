package android.net.wifi.nl80211;

import android.net.wifi.nl80211.IPnoScanEvent;
import android.net.wifi.nl80211.IScanEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IWifiScannerImpl extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IWifiScannerImpl";
    public static final int SCAN_STATUS_FAILED_ABORT = 3;
    public static final int SCAN_STATUS_FAILED_BUSY = 2;
    public static final int SCAN_STATUS_FAILED_GENERIC = 1;
    public static final int SCAN_STATUS_FAILED_INVALID_ARGS = 5;
    public static final int SCAN_STATUS_FAILED_NODEV = 4;
    public static final int SCAN_STATUS_SUCCESS = 0;
    public static final int SCAN_TYPE_DEFAULT = -1;
    public static final int SCAN_TYPE_HIGH_ACCURACY = 2;
    public static final int SCAN_TYPE_LOW_POWER = 1;
    public static final int SCAN_TYPE_LOW_SPAN = 0;

    public static class Default implements IWifiScannerImpl {
        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public void abortScan() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public void disableRandomMac() throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public int getMaxSsidsPerScan() throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public NativeScanResult[] getPnoScanResults() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public NativeScanResult[] getScanResults() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public boolean scan(SingleScanSettings singleScanSettings) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public int scanRequest(SingleScanSettings singleScanSettings) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public boolean startPnoScan(PnoSettings pnoSettings) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public boolean stopPnoScan() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public void subscribePnoScanEvents(IPnoScanEvent iPnoScanEvent) throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public void subscribeScanEvents(IScanEvent iScanEvent) throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public void unsubscribePnoScanEvents() throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public void unsubscribeScanEvents() throws RemoteException {
        }
    }

    void abortScan() throws RemoteException;

    void disableRandomMac() throws RemoteException;

    int getMaxSsidsPerScan() throws RemoteException;

    NativeScanResult[] getPnoScanResults() throws RemoteException;

    NativeScanResult[] getScanResults() throws RemoteException;

    boolean scan(SingleScanSettings singleScanSettings) throws RemoteException;

    int scanRequest(SingleScanSettings singleScanSettings) throws RemoteException;

    boolean startPnoScan(PnoSettings pnoSettings) throws RemoteException;

    boolean stopPnoScan() throws RemoteException;

    void subscribePnoScanEvents(IPnoScanEvent iPnoScanEvent) throws RemoteException;

    void subscribeScanEvents(IScanEvent iScanEvent) throws RemoteException;

    void unsubscribePnoScanEvents() throws RemoteException;

    void unsubscribeScanEvents() throws RemoteException;

    public static abstract class Stub extends Binder implements IWifiScannerImpl {
        static final int TRANSACTION_abortScan = 12;
        static final int TRANSACTION_disableRandomMac = 13;
        static final int TRANSACTION_getMaxSsidsPerScan = 3;
        static final int TRANSACTION_getPnoScanResults = 2;
        static final int TRANSACTION_getScanResults = 1;
        static final int TRANSACTION_scan = 4;
        static final int TRANSACTION_scanRequest = 5;
        static final int TRANSACTION_startPnoScan = 10;
        static final int TRANSACTION_stopPnoScan = 11;
        static final int TRANSACTION_subscribePnoScanEvents = 8;
        static final int TRANSACTION_subscribeScanEvents = 6;
        static final int TRANSACTION_unsubscribePnoScanEvents = 9;
        static final int TRANSACTION_unsubscribeScanEvents = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, IWifiScannerImpl.DESCRIPTOR);
        }

        public static IWifiScannerImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWifiScannerImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWifiScannerImpl)) {
                return (IWifiScannerImpl) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getScanResults";
                case 2:
                    return "getPnoScanResults";
                case 3:
                    return "getMaxSsidsPerScan";
                case 4:
                    return "scan";
                case 5:
                    return "scanRequest";
                case 6:
                    return "subscribeScanEvents";
                case 7:
                    return "unsubscribeScanEvents";
                case 8:
                    return "subscribePnoScanEvents";
                case 9:
                    return "unsubscribePnoScanEvents";
                case 10:
                    return "startPnoScan";
                case 11:
                    return "stopPnoScan";
                case 12:
                    return "abortScan";
                case 13:
                    return "disableRandomMac";
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
                parcel.enforceInterface(IWifiScannerImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWifiScannerImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    NativeScanResult[] scanResults = getScanResults();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(scanResults, 1);
                    return true;
                case 2:
                    NativeScanResult[] pnoScanResults = getPnoScanResults();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(pnoScanResults, 1);
                    return true;
                case 3:
                    int maxSsidsPerScan = getMaxSsidsPerScan();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxSsidsPerScan);
                    return true;
                case 4:
                    SingleScanSettings singleScanSettings = (SingleScanSettings) parcel.readTypedObject(SingleScanSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean scan = scan(singleScanSettings);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(scan);
                    return true;
                case 5:
                    SingleScanSettings singleScanSettings2 = (SingleScanSettings) parcel.readTypedObject(SingleScanSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    int scanRequest = scanRequest(singleScanSettings2);
                    parcel2.writeNoException();
                    parcel2.writeInt(scanRequest);
                    return true;
                case 6:
                    IScanEvent asInterface = IScanEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribeScanEvents(asInterface);
                    return true;
                case 7:
                    unsubscribeScanEvents();
                    return true;
                case 8:
                    IPnoScanEvent asInterface2 = IPnoScanEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribePnoScanEvents(asInterface2);
                    return true;
                case 9:
                    unsubscribePnoScanEvents();
                    return true;
                case 10:
                    PnoSettings pnoSettings = (PnoSettings) parcel.readTypedObject(PnoSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean startPnoScan = startPnoScan(pnoSettings);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startPnoScan);
                    return true;
                case 11:
                    boolean stopPnoScan = stopPnoScan();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopPnoScan);
                    return true;
                case 12:
                    abortScan();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    disableRandomMac();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IWifiScannerImpl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWifiScannerImpl.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public NativeScanResult[] getScanResults() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NativeScanResult[]) obtain2.createTypedArray(NativeScanResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public NativeScanResult[] getPnoScanResults() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NativeScanResult[]) obtain2.createTypedArray(NativeScanResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public int getMaxSsidsPerScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public boolean scan(SingleScanSettings singleScanSettings) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    obtain.writeTypedObject(singleScanSettings, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public int scanRequest(SingleScanSettings singleScanSettings) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    obtain.writeTypedObject(singleScanSettings, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void subscribeScanEvents(IScanEvent iScanEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    obtain.writeStrongInterface(iScanEvent);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void unsubscribeScanEvents() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void subscribePnoScanEvents(IPnoScanEvent iPnoScanEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    obtain.writeStrongInterface(iPnoScanEvent);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void unsubscribePnoScanEvents() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public boolean startPnoScan(PnoSettings pnoSettings) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    obtain.writeTypedObject(pnoSettings, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public boolean stopPnoScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void abortScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void disableRandomMac() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
