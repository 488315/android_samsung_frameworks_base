package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IStagedApexObserver extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IStagedApexObserver";

    void onApexStaged(ApexStagedEvent apexStagedEvent) throws RemoteException;

    public static class Default implements IStagedApexObserver {
        @Override // android.content.pm.IStagedApexObserver
        public void onApexStaged(ApexStagedEvent event) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStagedApexObserver {
        static final int TRANSACTION_onApexStaged = 1;

        public Stub() {
            attachInterface(this, IStagedApexObserver.DESCRIPTOR);
        }

        public static IStagedApexObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStagedApexObserver.DESCRIPTOR);
            if (iin != null && (iin instanceof IStagedApexObserver)) {
                return (IStagedApexObserver) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IStagedApexObserver.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IStagedApexObserver.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ApexStagedEvent _arg0 = (ApexStagedEvent) data.readTypedObject(ApexStagedEvent.CREATOR);
                    data.enforceNoDataAvail();
                    onApexStaged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStagedApexObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStagedApexObserver.DESCRIPTOR;
            }

            @Override // android.content.pm.IStagedApexObserver
            public void onApexStaged(ApexStagedEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStagedApexObserver.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
