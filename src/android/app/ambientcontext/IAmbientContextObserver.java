package android.app.ambientcontext;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IAmbientContextObserver extends IInterface {
    public static final String DESCRIPTOR = "android.app.ambientcontext.IAmbientContextObserver";

    public static class Default implements IAmbientContextObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.ambientcontext.IAmbientContextObserver
        public void onEvents(List<AmbientContextEvent> list) throws RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextObserver
        public void onRegistrationComplete(int i) throws RemoteException {
        }
    }

    void onEvents(List<AmbientContextEvent> list) throws RemoteException;

    void onRegistrationComplete(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IAmbientContextObserver {
        static final int TRANSACTION_onEvents = 1;
        static final int TRANSACTION_onRegistrationComplete = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IAmbientContextObserver.DESCRIPTOR);
        }

        public static IAmbientContextObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAmbientContextObserver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAmbientContextObserver)) {
                return (IAmbientContextObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onEvents";
            }
            if (i != 2) {
                return null;
            }
            return "onRegistrationComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAmbientContextObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAmbientContextObserver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(AmbientContextEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onEvents(createTypedArrayList);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRegistrationComplete(readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAmbientContextObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAmbientContextObserver.DESCRIPTOR;
            }

            @Override // android.app.ambientcontext.IAmbientContextObserver
            public void onEvents(List<AmbientContextEvent> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAmbientContextObserver.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextObserver
            public void onRegistrationComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAmbientContextObserver.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
