package android.tracing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ITracingServiceProxy extends IInterface {
    public static final String DESCRIPTOR = "android.tracing.ITracingServiceProxy";

    public static class Default implements ITracingServiceProxy {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.tracing.ITracingServiceProxy
        public void notifyTraceSessionEnded(boolean z) throws RemoteException {
        }

        @Override // android.tracing.ITracingServiceProxy
        public void reportTrace(TraceReportParams traceReportParams) throws RemoteException {
        }
    }

    void notifyTraceSessionEnded(boolean z) throws RemoteException;

    void reportTrace(TraceReportParams traceReportParams) throws RemoteException;

    public static abstract class Stub extends Binder implements ITracingServiceProxy {
        static final int TRANSACTION_notifyTraceSessionEnded = 1;
        static final int TRANSACTION_reportTrace = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ITracingServiceProxy.DESCRIPTOR);
        }

        public static ITracingServiceProxy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITracingServiceProxy.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITracingServiceProxy)) {
                return (ITracingServiceProxy) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "notifyTraceSessionEnded";
            }
            if (i != 2) {
                return null;
            }
            return "reportTrace";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITracingServiceProxy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITracingServiceProxy.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                notifyTraceSessionEnded(readBoolean);
            } else if (i == 2) {
                TraceReportParams traceReportParams = (TraceReportParams) parcel.readTypedObject(TraceReportParams.CREATOR);
                parcel.enforceNoDataAvail();
                reportTrace(traceReportParams);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITracingServiceProxy {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITracingServiceProxy.DESCRIPTOR;
            }

            @Override // android.tracing.ITracingServiceProxy
            public void notifyTraceSessionEnded(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITracingServiceProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.tracing.ITracingServiceProxy
            public void reportTrace(TraceReportParams traceReportParams) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITracingServiceProxy.DESCRIPTOR);
                    obtain.writeTypedObject(traceReportParams, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
