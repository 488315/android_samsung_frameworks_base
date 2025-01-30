package android.hardware.input;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IWirelessKeyboardShareChangedListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IWirelessKeyboardShareChangedListener";

    void onWirelessKeyboardShareChanged(long j, int i, String str) throws RemoteException;

    public static class Default implements IWirelessKeyboardShareChangedListener {
        @Override // android.hardware.input.IWirelessKeyboardShareChangedListener
        public void onWirelessKeyboardShareChanged(long whenNanos, int index, String contents) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWirelessKeyboardShareChangedListener {
        static final int TRANSACTION_onWirelessKeyboardShareChanged = 1;

        public Stub() {
            attachInterface(this, IWirelessKeyboardShareChangedListener.DESCRIPTOR);
        }

        public static IWirelessKeyboardShareChangedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWirelessKeyboardShareChangedListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IWirelessKeyboardShareChangedListener)) {
                return (IWirelessKeyboardShareChangedListener) iin;
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
                    return "onWirelessKeyboardShareChanged";
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
                data.enforceInterface(IWirelessKeyboardShareChangedListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IWirelessKeyboardShareChangedListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            long _arg0 = data.readLong();
                            int _arg1 = data.readInt();
                            String _arg2 = data.readString();
                            data.enforceNoDataAvail();
                            onWirelessKeyboardShareChanged(_arg0, _arg1, _arg2);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IWirelessKeyboardShareChangedListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWirelessKeyboardShareChangedListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IWirelessKeyboardShareChangedListener
            public void onWirelessKeyboardShareChanged(long whenNanos, int index, String contents) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWirelessKeyboardShareChangedListener.DESCRIPTOR);
                    _data.writeLong(whenNanos);
                    _data.writeInt(index);
                    _data.writeString(contents);
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
