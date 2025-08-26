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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IWifiScannerImpl.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWifiScannerImpl)) {
                return (IWifiScannerImpl) iInterfaceQueryLocalInterface;
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
                    boolean zScan = scan(singleScanSettings);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zScan);
                    return true;
                case 5:
                    SingleScanSettings singleScanSettings2 = (SingleScanSettings) parcel.readTypedObject(SingleScanSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iScanRequest = scanRequest(singleScanSettings2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iScanRequest);
                    return true;
                case 6:
                    IScanEvent iScanEventAsInterface = IScanEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribeScanEvents(iScanEventAsInterface);
                    return true;
                case 7:
                    unsubscribeScanEvents();
                    return true;
                case 8:
                    IPnoScanEvent iPnoScanEventAsInterface = IPnoScanEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribePnoScanEvents(iPnoScanEventAsInterface);
                    return true;
                case 9:
                    unsubscribePnoScanEvents();
                    return true;
                case 10:
                    PnoSettings pnoSettings = (PnoSettings) parcel.readTypedObject(PnoSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zStartPnoScan = startPnoScan(pnoSettings);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartPnoScan);
                    return true;
                case 11:
                    boolean zStopPnoScan = stopPnoScan();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStopPnoScan);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NativeScanResult[]) parcelObtain2.createTypedArray(NativeScanResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public NativeScanResult[] getPnoScanResults() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NativeScanResult[]) parcelObtain2.createTypedArray(NativeScanResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public int getMaxSsidsPerScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public boolean scan(SingleScanSettings singleScanSettings) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(singleScanSettings, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public int scanRequest(SingleScanSettings singleScanSettings) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(singleScanSettings, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void subscribeScanEvents(IScanEvent iScanEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iScanEvent);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void unsubscribeScanEvents() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void subscribePnoScanEvents(IPnoScanEvent iPnoScanEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPnoScanEvent);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void unsubscribePnoScanEvents() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public boolean startPnoScan(PnoSettings pnoSettings) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pnoSettings, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public boolean stopPnoScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void abortScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void disableRandomMac() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
