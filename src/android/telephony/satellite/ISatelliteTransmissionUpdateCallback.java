package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteTransmissionUpdateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISatelliteTransmissionUpdateCallback";

    public static class Default implements ISatelliteTransmissionUpdateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onReceiveDatagramStateChanged(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSatellitePositionChanged(PointingInfo pointingInfo) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSendDatagramRequested(int i) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSendDatagramStateChanged(int i, int i2, int i3, int i4) throws RemoteException {
        }
    }

    void onReceiveDatagramStateChanged(int i, int i2, int i3) throws RemoteException;

    void onSatellitePositionChanged(PointingInfo pointingInfo) throws RemoteException;

    void onSendDatagramRequested(int i) throws RemoteException;

    void onSendDatagramStateChanged(int i, int i2, int i3, int i4) throws RemoteException;

    public static abstract class Stub extends Binder implements ISatelliteTransmissionUpdateCallback {
        static final int TRANSACTION_onReceiveDatagramStateChanged = 2;
        static final int TRANSACTION_onSatellitePositionChanged = 3;
        static final int TRANSACTION_onSendDatagramRequested = 4;
        static final int TRANSACTION_onSendDatagramStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
        }

        public static ISatelliteTransmissionUpdateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISatelliteTransmissionUpdateCallback)) {
                return (ISatelliteTransmissionUpdateCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSendDatagramStateChanged";
            }
            if (i == 2) {
                return "onReceiveDatagramStateChanged";
            }
            if (i == 3) {
                return "onSatellitePositionChanged";
            }
            if (i != 4) {
                return null;
            }
            return "onSendDatagramRequested";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSendDatagramStateChanged(i3, i4, i5, i6);
            } else if (i == 2) {
                int i7 = parcel.readInt();
                int i8 = parcel.readInt();
                int i9 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onReceiveDatagramStateChanged(i7, i8, i9);
            } else if (i == 3) {
                PointingInfo pointingInfo = (PointingInfo) parcel.readTypedObject(PointingInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onSatellitePositionChanged(pointingInfo);
            } else if (i == 4) {
                int i10 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSendDatagramRequested(i10);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISatelliteTransmissionUpdateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteTransmissionUpdateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
            public void onSendDatagramStateChanged(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
            public void onReceiveDatagramStateChanged(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
            public void onSatellitePositionChanged(PointingInfo pointingInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pointingInfo, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
            public void onSendDatagramRequested(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
