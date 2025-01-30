package android.hardware.input;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface ISemLidStateChangedListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.ISemLidStateChangedListener";

    void onLidStateChanged(long j, boolean z) throws RemoteException;

    public static class Default implements ISemLidStateChangedListener {
        @Override // android.hardware.input.ISemLidStateChangedListener
        public void onLidStateChanged(long whenNanos, boolean lidOpen) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemLidStateChangedListener {
        static final int TRANSACTION_onLidStateChanged = 1;

        public Stub() {
            attachInterface(this, ISemLidStateChangedListener.DESCRIPTOR);
        }

        public static ISemLidStateChangedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemLidStateChangedListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemLidStateChangedListener)) {
                return (ISemLidStateChangedListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onLidStateChanged";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemLidStateChangedListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemLidStateChangedListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            long _arg0 = data.readLong();
                            boolean _arg1 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onLidStateChanged(_arg0, _arg1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISemLidStateChangedListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemLidStateChangedListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.ISemLidStateChangedListener
            public void onLidStateChanged(long whenNanos, boolean lidOpen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemLidStateChangedListener.DESCRIPTOR);
                    _data.writeLong(whenNanos);
                    _data.writeBoolean(lidOpen);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
