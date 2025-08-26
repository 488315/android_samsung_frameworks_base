package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telephony.IVoidConsumer;

/* loaded from: classes4.dex */
public interface ISatelliteDatagramCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISatelliteDatagramCallback";

    public static class Default implements ISatelliteDatagramCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.satellite.ISatelliteDatagramCallback
        public void onSatelliteDatagramReceived(long j, SatelliteDatagram satelliteDatagram, int i, IVoidConsumer iVoidConsumer) throws RemoteException {
        }
    }

    void onSatelliteDatagramReceived(long j, SatelliteDatagram satelliteDatagram, int i, IVoidConsumer iVoidConsumer) throws RemoteException;

    public static abstract class Stub extends Binder implements ISatelliteDatagramCallback {
        static final int TRANSACTION_onSatelliteDatagramReceived = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISatelliteDatagramCallback.DESCRIPTOR);
        }

        public static ISatelliteDatagramCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISatelliteDatagramCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISatelliteDatagramCallback)) {
                return (ISatelliteDatagramCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSatelliteDatagramReceived";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISatelliteDatagramCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISatelliteDatagramCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long j = parcel.readLong();
                SatelliteDatagram satelliteDatagram = (SatelliteDatagram) parcel.readTypedObject(SatelliteDatagram.CREATOR);
                int i3 = parcel.readInt();
                IVoidConsumer iVoidConsumerAsInterface = IVoidConsumer.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onSatelliteDatagramReceived(j, satelliteDatagram, i3, iVoidConsumerAsInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISatelliteDatagramCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteDatagramCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteDatagramCallback
            public void onSatelliteDatagramReceived(long j, SatelliteDatagram satelliteDatagram, int i, IVoidConsumer iVoidConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteDatagramCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(satelliteDatagram, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iVoidConsumer);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
