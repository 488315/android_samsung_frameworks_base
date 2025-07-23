package android.service.carrier;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;

/* loaded from: classes3.dex */
public interface ICarrierService extends IInterface {

    public static class Default implements ICarrierService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.carrier.ICarrierService
        public void getCarrierConfig(int i, CarrierIdentifier carrierIdentifier, ResultReceiver resultReceiver) throws RemoteException {
        }
    }

    void getCarrierConfig(int i, CarrierIdentifier carrierIdentifier, ResultReceiver resultReceiver) throws RemoteException;

    public static abstract class Stub extends Binder implements ICarrierService {
        public static final String DESCRIPTOR = "android.service.carrier.ICarrierService";
        static final int TRANSACTION_getCarrierConfig = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICarrierService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICarrierService)) {
                return (ICarrierService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getCarrierConfig";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                CarrierIdentifier carrierIdentifier = (CarrierIdentifier) parcel.readTypedObject(CarrierIdentifier.CREATOR);
                ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                parcel.enforceNoDataAvail();
                getCarrierConfig(readInt, carrierIdentifier, resultReceiver);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICarrierService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.carrier.ICarrierService
            public void getCarrierConfig(int i, CarrierIdentifier carrierIdentifier, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(carrierIdentifier, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
