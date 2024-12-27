package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.SrvccCall;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISrvccStartedCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.ISrvccStartedCallback";

    void onSrvccCallNotified(List<SrvccCall> list) throws RemoteException;

    public static class Default implements ISrvccStartedCallback {
        @Override // android.telephony.ims.aidl.ISrvccStartedCallback
        public void onSrvccCallNotified(List<SrvccCall> profiles) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISrvccStartedCallback {
        static final int TRANSACTION_onSrvccCallNotified = 1;

        public Stub() {
            attachInterface(this, ISrvccStartedCallback.DESCRIPTOR);
        }

        public static ISrvccStartedCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISrvccStartedCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISrvccStartedCallback)) {
                return (ISrvccStartedCallback) iin;
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
                    return "onSrvccCallNotified";
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
                data.enforceInterface(ISrvccStartedCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISrvccStartedCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<SrvccCall> _arg0 = data.createTypedArrayList(SrvccCall.CREATOR);
                    data.enforceNoDataAvail();
                    onSrvccCallNotified(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISrvccStartedCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISrvccStartedCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISrvccStartedCallback
            public void onSrvccCallNotified(List<SrvccCall> profiles) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISrvccStartedCallback.DESCRIPTOR);
                    _data.writeTypedList(profiles, 0);
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
