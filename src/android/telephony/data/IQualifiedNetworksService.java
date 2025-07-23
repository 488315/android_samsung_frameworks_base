package android.telephony.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.data.IQualifiedNetworksServiceCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IQualifiedNetworksService extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.data.IQualifiedNetworksService";

    public static class Default implements IQualifiedNetworksService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void createNetworkAvailabilityProvider(int i, IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void removeNetworkAvailabilityProvider(int i) throws RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void reportEmergencyDataNetworkPreferredTransportChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void reportThrottleStatusChanged(int i, List<ThrottleStatus> list) throws RemoteException {
        }
    }

    void createNetworkAvailabilityProvider(int i, IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) throws RemoteException;

    void removeNetworkAvailabilityProvider(int i) throws RemoteException;

    void reportEmergencyDataNetworkPreferredTransportChanged(int i, int i2) throws RemoteException;

    void reportThrottleStatusChanged(int i, List<ThrottleStatus> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IQualifiedNetworksService {
        static final int TRANSACTION_createNetworkAvailabilityProvider = 1;
        static final int TRANSACTION_removeNetworkAvailabilityProvider = 2;
        static final int TRANSACTION_reportEmergencyDataNetworkPreferredTransportChanged = 4;
        static final int TRANSACTION_reportThrottleStatusChanged = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IQualifiedNetworksService.DESCRIPTOR);
        }

        public static IQualifiedNetworksService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IQualifiedNetworksService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IQualifiedNetworksService)) {
                return (IQualifiedNetworksService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "createNetworkAvailabilityProvider";
            }
            if (i == 2) {
                return "removeNetworkAvailabilityProvider";
            }
            if (i == 3) {
                return "reportThrottleStatusChanged";
            }
            if (i != 4) {
                return null;
            }
            return "reportEmergencyDataNetworkPreferredTransportChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IQualifiedNetworksService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IQualifiedNetworksService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                IQualifiedNetworksServiceCallback asInterface = IQualifiedNetworksServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                createNetworkAvailabilityProvider(readInt, asInterface);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                removeNetworkAvailabilityProvider(readInt2);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                ArrayList createTypedArrayList = parcel.createTypedArrayList(ThrottleStatus.CREATOR);
                parcel.enforceNoDataAvail();
                reportThrottleStatusChanged(readInt3, createTypedArrayList);
            } else if (i == 4) {
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                reportEmergencyDataNetworkPreferredTransportChanged(readInt4, readInt5);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IQualifiedNetworksService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IQualifiedNetworksService.DESCRIPTOR;
            }

            @Override // android.telephony.data.IQualifiedNetworksService
            public void createNetworkAvailabilityProvider(int i, IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IQualifiedNetworksService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iQualifiedNetworksServiceCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksService
            public void removeNetworkAvailabilityProvider(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IQualifiedNetworksService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksService
            public void reportThrottleStatusChanged(int i, List<ThrottleStatus> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IQualifiedNetworksService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksService
            public void reportEmergencyDataNetworkPreferredTransportChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IQualifiedNetworksService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
