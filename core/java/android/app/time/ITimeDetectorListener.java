package android.app.time;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ITimeDetectorListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.time.ITimeDetectorListener";

    void onChange() throws RemoteException;

    public static class Default implements ITimeDetectorListener {
        @Override // android.app.time.ITimeDetectorListener
        public void onChange() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITimeDetectorListener {
        static final int TRANSACTION_onChange = 1;

        public Stub() {
            attachInterface(this, ITimeDetectorListener.DESCRIPTOR);
        }

        public static ITimeDetectorListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITimeDetectorListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ITimeDetectorListener)) {
                return (ITimeDetectorListener) iin;
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
                    return "onChange";
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
                data.enforceInterface(ITimeDetectorListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITimeDetectorListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onChange();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITimeDetectorListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITimeDetectorListener.DESCRIPTOR;
            }

            @Override // android.app.time.ITimeDetectorListener
            public void onChange() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITimeDetectorListener.DESCRIPTOR);
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
