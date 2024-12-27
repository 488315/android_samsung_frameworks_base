package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IHdmiControlStatusChangeListener extends IInterface {
    public static final String DESCRIPTOR =
            "android.hardware.hdmi.IHdmiControlStatusChangeListener";

    void onStatusChange(int i, boolean z) throws RemoteException;

    public static class Default implements IHdmiControlStatusChangeListener {
        @Override // android.hardware.hdmi.IHdmiControlStatusChangeListener
        public void onStatusChange(int isCecEnabled, boolean isCecAvailable)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IHdmiControlStatusChangeListener {
        static final int TRANSACTION_onStatusChange = 1;

        public Stub() {
            attachInterface(this, IHdmiControlStatusChangeListener.DESCRIPTOR);
        }

        public static IHdmiControlStatusChangeListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IHdmiControlStatusChangeListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IHdmiControlStatusChangeListener)) {
                return (IHdmiControlStatusChangeListener) iin;
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
                    return "onStatusChange";
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
                data.enforceInterface(IHdmiControlStatusChangeListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IHdmiControlStatusChangeListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onStatusChange(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IHdmiControlStatusChangeListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHdmiControlStatusChangeListener.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiControlStatusChangeListener
            public void onStatusChange(int isCecEnabled, boolean isCecAvailable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHdmiControlStatusChangeListener.DESCRIPTOR);
                    _data.writeInt(isCecEnabled);
                    _data.writeBoolean(isCecAvailable);
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
