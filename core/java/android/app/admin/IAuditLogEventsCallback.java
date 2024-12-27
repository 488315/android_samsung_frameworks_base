package android.app.admin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IAuditLogEventsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.admin.IAuditLogEventsCallback";

    void onNewAuditLogEvents(List<SecurityLog.SecurityEvent> list) throws RemoteException;

    public static class Default implements IAuditLogEventsCallback {
        @Override // android.app.admin.IAuditLogEventsCallback
        public void onNewAuditLogEvents(List<SecurityLog.SecurityEvent> events)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IAuditLogEventsCallback {
        static final int TRANSACTION_onNewAuditLogEvents = 1;

        public Stub() {
            attachInterface(this, IAuditLogEventsCallback.DESCRIPTOR);
        }

        public static IAuditLogEventsCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAuditLogEventsCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IAuditLogEventsCallback)) {
                return (IAuditLogEventsCallback) iin;
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
                    return "onNewAuditLogEvents";
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
                data.enforceInterface(IAuditLogEventsCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAuditLogEventsCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<SecurityLog.SecurityEvent> _arg0 =
                            data.createTypedArrayList(SecurityLog.SecurityEvent.CREATOR);
                    data.enforceNoDataAvail();
                    onNewAuditLogEvents(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAuditLogEventsCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAuditLogEventsCallback.DESCRIPTOR;
            }

            @Override // android.app.admin.IAuditLogEventsCallback
            public void onNewAuditLogEvents(List<SecurityLog.SecurityEvent> events)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAuditLogEventsCallback.DESCRIPTOR);
                    _data.writeTypedList(events, 0);
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
