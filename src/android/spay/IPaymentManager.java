package android.spay;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IPaymentManager extends IInterface {
    public static final String DESCRIPTOR = "android.spay.IPaymentManager";

    public static class Default implements IPaymentManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.spay.IPaymentManager
        public byte[] getMeasurementFile() throws RemoteException {
            return null;
        }

        @Override // android.spay.IPaymentManager
        public PaymentTZServiceCommnInfo registerSPayFW(PaymentTZServiceConfig paymentTZServiceConfig) throws RemoteException {
            return null;
        }
    }

    byte[] getMeasurementFile() throws RemoteException;

    PaymentTZServiceCommnInfo registerSPayFW(PaymentTZServiceConfig paymentTZServiceConfig) throws RemoteException;

    public static abstract class Stub extends Binder implements IPaymentManager {
        static final int TRANSACTION_getMeasurementFile = 2;
        static final int TRANSACTION_registerSPayFW = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IPaymentManager.DESCRIPTOR);
        }

        public static IPaymentManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPaymentManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPaymentManager)) {
                return (IPaymentManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerSPayFW";
            }
            if (i != 2) {
                return null;
            }
            return "getMeasurementFile";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPaymentManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPaymentManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                PaymentTZServiceConfig paymentTZServiceConfig = (PaymentTZServiceConfig) parcel.readTypedObject(PaymentTZServiceConfig.CREATOR);
                parcel.enforceNoDataAvail();
                PaymentTZServiceCommnInfo paymentTZServiceCommnInfoRegisterSPayFW = registerSPayFW(paymentTZServiceConfig);
                parcel2.writeNoException();
                parcel2.writeTypedObject(paymentTZServiceCommnInfoRegisterSPayFW, 1);
            } else if (i == 2) {
                byte[] measurementFile = getMeasurementFile();
                parcel2.writeNoException();
                parcel2.writeByteArray(measurementFile);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPaymentManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPaymentManager.DESCRIPTOR;
            }

            @Override // android.spay.IPaymentManager
            public PaymentTZServiceCommnInfo registerSPayFW(PaymentTZServiceConfig paymentTZServiceConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPaymentManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(paymentTZServiceConfig, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PaymentTZServiceCommnInfo) parcelObtain2.readTypedObject(PaymentTZServiceCommnInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.spay.IPaymentManager
            public byte[] getMeasurementFile() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPaymentManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
