package android.spay;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPaymentManager extends IInterface {
    public static final String DESCRIPTOR = "android.spay.IPaymentManager";

    byte[] getMeasurementFile() throws RemoteException;

    PaymentTZServiceCommnInfo registerSPayFW(PaymentTZServiceConfig paymentTZServiceConfig)
            throws RemoteException;

    public static class Default implements IPaymentManager {
        @Override // android.spay.IPaymentManager
        public PaymentTZServiceCommnInfo registerSPayFW(PaymentTZServiceConfig config)
                throws RemoteException {
            return null;
        }

        @Override // android.spay.IPaymentManager
        public byte[] getMeasurementFile() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IPaymentManager {
        static final int TRANSACTION_getMeasurementFile = 2;
        static final int TRANSACTION_registerSPayFW = 1;

        public Stub() {
            attachInterface(this, IPaymentManager.DESCRIPTOR);
        }

        public static IPaymentManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPaymentManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IPaymentManager)) {
                return (IPaymentManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "registerSPayFW";
                case 2:
                    return "getMeasurementFile";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IPaymentManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IPaymentManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    PaymentTZServiceConfig _arg0 =
                            (PaymentTZServiceConfig)
                                    data.readTypedObject(PaymentTZServiceConfig.CREATOR);
                    data.enforceNoDataAvail();
                    PaymentTZServiceCommnInfo _result = registerSPayFW(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    byte[] _result2 = getMeasurementFile();
                    reply.writeNoException();
                    reply.writeByteArray(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPaymentManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPaymentManager.DESCRIPTOR;
            }

            @Override // android.spay.IPaymentManager
            public PaymentTZServiceCommnInfo registerSPayFW(PaymentTZServiceConfig config)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPaymentManager.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    PaymentTZServiceCommnInfo _result =
                            (PaymentTZServiceCommnInfo)
                                    _reply.readTypedObject(PaymentTZServiceCommnInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.spay.IPaymentManager
            public byte[] getMeasurementFile() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPaymentManager.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
