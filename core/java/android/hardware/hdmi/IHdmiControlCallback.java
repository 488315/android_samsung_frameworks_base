package android.hardware.hdmi;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IHdmiControlCallback extends IInterface {
    void onComplete(int i) throws RemoteException;

    public static class Default implements IHdmiControlCallback {
        @Override // android.hardware.hdmi.IHdmiControlCallback
        public void onComplete(int result) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IHdmiControlCallback {
        public static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiControlCallback";
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiControlCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IHdmiControlCallback)) {
                return (IHdmiControlCallback) iin;
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
                    return "onComplete";
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
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            onComplete(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IHdmiControlCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiControlCallback
            public void onComplete(int result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(result);
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
