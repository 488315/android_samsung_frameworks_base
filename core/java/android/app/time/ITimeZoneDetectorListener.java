package android.app.time;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ITimeZoneDetectorListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.time.ITimeZoneDetectorListener";

    void onChange() throws RemoteException;

    public static class Default implements ITimeZoneDetectorListener {
        @Override // android.app.time.ITimeZoneDetectorListener
        public void onChange() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITimeZoneDetectorListener {
        static final int TRANSACTION_onChange = 1;

        public Stub() {
            attachInterface(this, ITimeZoneDetectorListener.DESCRIPTOR);
        }

        public static ITimeZoneDetectorListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITimeZoneDetectorListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ITimeZoneDetectorListener)) {
                return (ITimeZoneDetectorListener) iin;
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
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ITimeZoneDetectorListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITimeZoneDetectorListener.DESCRIPTOR);
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

        private static class Proxy implements ITimeZoneDetectorListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITimeZoneDetectorListener.DESCRIPTOR;
            }

            @Override // android.app.time.ITimeZoneDetectorListener
            public void onChange() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITimeZoneDetectorListener.DESCRIPTOR);
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
