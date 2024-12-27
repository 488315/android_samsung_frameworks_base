package android.telephony.satellite.stub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface INtnSignalStrengthConsumer extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.stub.INtnSignalStrengthConsumer";

    void accept(NtnSignalStrength ntnSignalStrength) throws RemoteException;

    public static class Default implements INtnSignalStrengthConsumer {
        @Override // android.telephony.satellite.stub.INtnSignalStrengthConsumer
        public void accept(NtnSignalStrength result) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INtnSignalStrengthConsumer {
        static final int TRANSACTION_accept = 1;

        public Stub() {
            attachInterface(this, INtnSignalStrengthConsumer.DESCRIPTOR);
        }

        public static INtnSignalStrengthConsumer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(INtnSignalStrengthConsumer.DESCRIPTOR);
            if (iin != null && (iin instanceof INtnSignalStrengthConsumer)) {
                return (INtnSignalStrengthConsumer) iin;
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
                    return "accept";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(INtnSignalStrengthConsumer.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(INtnSignalStrengthConsumer.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    NtnSignalStrength _arg0 = (NtnSignalStrength) data.readTypedObject(NtnSignalStrength.CREATOR);
                    data.enforceNoDataAvail();
                    accept(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements INtnSignalStrengthConsumer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INtnSignalStrengthConsumer.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.stub.INtnSignalStrengthConsumer
            public void accept(NtnSignalStrength result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(INtnSignalStrengthConsumer.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
