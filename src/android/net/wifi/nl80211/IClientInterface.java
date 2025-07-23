package android.net.wifi.nl80211;

import android.net.wifi.nl80211.ISendMgmtFrameEvent;
import android.net.wifi.nl80211.IWifiScannerImpl;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IClientInterface extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IClientInterface";

    public static class Default implements IClientInterface {
        @Override // android.net.wifi.nl80211.IClientInterface
        public void SendMgmtFrame(byte[] bArr, ISendMgmtFrameEvent iSendMgmtFrameEvent, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.wifi.nl80211.IClientInterface
        public String getInterfaceName() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IClientInterface
        public byte[] getMacAddress() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IClientInterface
        public int[] getPacketCounters() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IClientInterface
        public IWifiScannerImpl getWifiScannerImpl() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IClientInterface
        public int[] signalPoll() throws RemoteException {
            return null;
        }
    }

    void SendMgmtFrame(byte[] bArr, ISendMgmtFrameEvent iSendMgmtFrameEvent, int i) throws RemoteException;

    String getInterfaceName() throws RemoteException;

    byte[] getMacAddress() throws RemoteException;

    int[] getPacketCounters() throws RemoteException;

    IWifiScannerImpl getWifiScannerImpl() throws RemoteException;

    int[] signalPoll() throws RemoteException;

    public static abstract class Stub extends Binder implements IClientInterface {
        static final int TRANSACTION_SendMgmtFrame = 6;
        static final int TRANSACTION_getInterfaceName = 4;
        static final int TRANSACTION_getMacAddress = 3;
        static final int TRANSACTION_getPacketCounters = 1;
        static final int TRANSACTION_getWifiScannerImpl = 5;
        static final int TRANSACTION_signalPoll = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IClientInterface.DESCRIPTOR);
        }

        public static IClientInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IClientInterface.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IClientInterface)) {
                return (IClientInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPacketCounters";
                case 2:
                    return "signalPoll";
                case 3:
                    return "getMacAddress";
                case 4:
                    return "getInterfaceName";
                case 5:
                    return "getWifiScannerImpl";
                case 6:
                    return "SendMgmtFrame";
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
                parcel.enforceInterface(IClientInterface.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IClientInterface.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] packetCounters = getPacketCounters();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(packetCounters);
                    return true;
                case 2:
                    int[] signalPoll = signalPoll();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(signalPoll);
                    return true;
                case 3:
                    byte[] macAddress = getMacAddress();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(macAddress);
                    return true;
                case 4:
                    String interfaceName = getInterfaceName();
                    parcel2.writeNoException();
                    parcel2.writeString(interfaceName);
                    return true;
                case 5:
                    IWifiScannerImpl wifiScannerImpl = getWifiScannerImpl();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(wifiScannerImpl);
                    return true;
                case 6:
                    byte[] createByteArray = parcel.createByteArray();
                    ISendMgmtFrameEvent asInterface = ISendMgmtFrameEvent.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SendMgmtFrame(createByteArray, asInterface, readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IClientInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IClientInterface.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public int[] getPacketCounters() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClientInterface.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public int[] signalPoll() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClientInterface.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public byte[] getMacAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClientInterface.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public String getInterfaceName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClientInterface.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public IWifiScannerImpl getWifiScannerImpl() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClientInterface.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return IWifiScannerImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public void SendMgmtFrame(byte[] bArr, ISendMgmtFrameEvent iSendMgmtFrameEvent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IClientInterface.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iSendMgmtFrameEvent);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
