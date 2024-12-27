package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface INetworkMonitorCallbacks extends IInterface {
    public static final String DESCRIPTOR =
            "android$net$INetworkMonitorCallbacks".replace('$', '.');
    public static final String HASH = "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
    public static final int VERSION = 21;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Default implements INetworkMonitorCallbacks {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.INetworkMonitorCallbacks
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.net.INetworkMonitorCallbacks
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.INetworkMonitorCallbacks
        public void hideProvisioningNotification() throws RemoteException {}

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyCaptivePortalDataChanged(CaptivePortalData captivePortalData)
                throws RemoteException {}

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyDataStallSuspected(DataStallReportParcelable dataStallReportParcelable)
                throws RemoteException {}

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyNetworkTested(int i, String str) throws RemoteException {}

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyNetworkTestedWithExtras(
                NetworkTestResultParcelable networkTestResultParcelable) throws RemoteException {}

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyPrivateDnsConfigResolved(PrivateDnsConfigParcel privateDnsConfigParcel)
                throws RemoteException {}

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyProbeStatusChanged(int i, int i2) throws RemoteException {}

        @Override // android.net.INetworkMonitorCallbacks
        public void onNetworkMonitorCreated(INetworkMonitor iNetworkMonitor)
                throws RemoteException {}

        @Override // android.net.INetworkMonitorCallbacks
        public void showProvisioningNotification(String str, String str2) throws RemoteException {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements INetworkMonitorCallbacks {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_hideProvisioningNotification = 5;
        static final int TRANSACTION_notifyCaptivePortalDataChanged = 9;
        static final int TRANSACTION_notifyDataStallSuspected = 8;
        static final int TRANSACTION_notifyNetworkTested = 2;
        static final int TRANSACTION_notifyNetworkTestedWithExtras = 7;
        static final int TRANSACTION_notifyPrivateDnsConfigResolved = 3;
        static final int TRANSACTION_notifyProbeStatusChanged = 6;
        static final int TRANSACTION_onNetworkMonitorCreated = 1;
        static final int TRANSACTION_showProvisioningNotification = 4;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements INetworkMonitorCallbacks {
            public String mCachedHash;
            public int mCachedVersion;
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.net.INetworkMonitorCallbacks
            public final synchronized String getInterfaceHash() {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(INetworkMonitorCallbacks.DESCRIPTOR);
                            this.mRemote.transact(
                                    Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                            obtain2.readException();
                            this.mCachedHash = obtain2.readString();
                            obtain2.recycle();
                            obtain.recycle();
                        } catch (Throwable th) {
                            obtain2.recycle();
                            obtain.recycle();
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }

            @Override // android.net.INetworkMonitorCallbacks
            public final int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(INetworkMonitorCallbacks.DESCRIPTOR);
                        this.mRemote.transact(
                                Stub.TRANSACTION_getInterfaceVersion, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.net.INetworkMonitorCallbacks
            public final void hideProvisioningNotification() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitorCallbacks.DESCRIPTOR);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method hideProvisioningNotification is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public final void notifyCaptivePortalDataChanged(CaptivePortalData captivePortalData) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(captivePortalData, 0);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method notifyCaptivePortalDataChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public final void notifyDataStallSuspected(
                    DataStallReportParcelable dataStallReportParcelable) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(dataStallReportParcelable, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method notifyDataStallSuspected is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public final void notifyNetworkTested(int i, String str) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method notifyNetworkTested is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public final void notifyNetworkTestedWithExtras(
                    NetworkTestResultParcelable networkTestResultParcelable) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(networkTestResultParcelable, 0);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method notifyNetworkTestedWithExtras is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public final void notifyPrivateDnsConfigResolved(
                    PrivateDnsConfigParcel privateDnsConfigParcel) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(privateDnsConfigParcel, 0);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method notifyPrivateDnsConfigResolved is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public final void notifyProbeStatusChanged(int i, int i2) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method notifyProbeStatusChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public final void onNetworkMonitorCreated(INetworkMonitor iNetworkMonitor) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetworkMonitor);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method onNetworkMonitorCreated is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public final void showProvisioningNotification(String str, String str2) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method showProvisioningNotification is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, INetworkMonitorCallbacks.DESCRIPTOR);
        }

        public static INetworkMonitorCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(INetworkMonitorCallbacks.DESCRIPTOR);
            if (queryLocalInterface != null
                    && (queryLocalInterface instanceof INetworkMonitorCallbacks)) {
                return (INetworkMonitorCallbacks) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mCachedVersion = -1;
            proxy.mCachedHash = "-1";
            proxy.mRemote = iBinder;
            return proxy;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            String str = INetworkMonitorCallbacks.DESCRIPTOR;
            if (i >= 1 && i <= TRANSACTION_getInterfaceVersion) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == TRANSACTION_getInterfaceVersion) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    onNetworkMonitorCreated(
                            INetworkMonitor.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 2:
                    notifyNetworkTested(parcel.readInt(), parcel.readString());
                    return true;
                case 3:
                    notifyPrivateDnsConfigResolved(
                            (PrivateDnsConfigParcel)
                                    parcel.readTypedObject(PrivateDnsConfigParcel.CREATOR));
                    return true;
                case 4:
                    showProvisioningNotification(parcel.readString(), parcel.readString());
                    return true;
                case 5:
                    hideProvisioningNotification();
                    return true;
                case 6:
                    notifyProbeStatusChanged(parcel.readInt(), parcel.readInt());
                    return true;
                case 7:
                    notifyNetworkTestedWithExtras(
                            (NetworkTestResultParcelable)
                                    parcel.readTypedObject(NetworkTestResultParcelable.CREATOR));
                    return true;
                case 8:
                    notifyDataStallSuspected(
                            (DataStallReportParcelable)
                                    parcel.readTypedObject(DataStallReportParcelable.CREATOR));
                    return true;
                case 9:
                    notifyCaptivePortalDataChanged(
                            (CaptivePortalData) parcel.readTypedObject(CaptivePortalData.CREATOR));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void hideProvisioningNotification() throws RemoteException;

    void notifyCaptivePortalDataChanged(CaptivePortalData captivePortalData) throws RemoteException;

    void notifyDataStallSuspected(DataStallReportParcelable dataStallReportParcelable)
            throws RemoteException;

    void notifyNetworkTested(int i, String str) throws RemoteException;

    void notifyNetworkTestedWithExtras(NetworkTestResultParcelable networkTestResultParcelable)
            throws RemoteException;

    void notifyPrivateDnsConfigResolved(PrivateDnsConfigParcel privateDnsConfigParcel)
            throws RemoteException;

    void notifyProbeStatusChanged(int i, int i2) throws RemoteException;

    void onNetworkMonitorCreated(INetworkMonitor iNetworkMonitor) throws RemoteException;

    void showProvisioningNotification(String str, String str2) throws RemoteException;
}
