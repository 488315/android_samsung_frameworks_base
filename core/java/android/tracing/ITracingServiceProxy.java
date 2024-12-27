package android.tracing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ITracingServiceProxy extends IInterface {
    public static final String DESCRIPTOR = "android.tracing.ITracingServiceProxy";

    void notifyTraceSessionEnded(boolean z) throws RemoteException;

    void reportTrace(TraceReportParams traceReportParams) throws RemoteException;

    public static class Default implements ITracingServiceProxy {
        @Override // android.tracing.ITracingServiceProxy
        public void notifyTraceSessionEnded(boolean sessionStolen) throws RemoteException {}

        @Override // android.tracing.ITracingServiceProxy
        public void reportTrace(TraceReportParams params) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITracingServiceProxy {
        static final int TRANSACTION_notifyTraceSessionEnded = 1;
        static final int TRANSACTION_reportTrace = 2;

        public Stub() {
            attachInterface(this, ITracingServiceProxy.DESCRIPTOR);
        }

        public static ITracingServiceProxy asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITracingServiceProxy.DESCRIPTOR);
            if (iin != null && (iin instanceof ITracingServiceProxy)) {
                return (ITracingServiceProxy) iin;
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
                    return "notifyTraceSessionEnded";
                case 2:
                    return "reportTrace";
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
                data.enforceInterface(ITracingServiceProxy.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITracingServiceProxy.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    notifyTraceSessionEnded(_arg0);
                    return true;
                case 2:
                    TraceReportParams _arg02 =
                            (TraceReportParams) data.readTypedObject(TraceReportParams.CREATOR);
                    data.enforceNoDataAvail();
                    reportTrace(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITracingServiceProxy {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITracingServiceProxy.DESCRIPTOR;
            }

            @Override // android.tracing.ITracingServiceProxy
            public void notifyTraceSessionEnded(boolean sessionStolen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITracingServiceProxy.DESCRIPTOR);
                    _data.writeBoolean(sessionStolen);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.tracing.ITracingServiceProxy
            public void reportTrace(TraceReportParams params) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITracingServiceProxy.DESCRIPTOR);
                    _data.writeTypedObject(params, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
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
