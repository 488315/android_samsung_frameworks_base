package android.service.resolver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IResolverRankerResult extends IInterface {

    public static class Default implements IResolverRankerResult {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.resolver.IResolverRankerResult
        public void sendResult(List<ResolverTarget> list) throws RemoteException {
        }
    }

    void sendResult(List<ResolverTarget> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IResolverRankerResult {
        public static final String DESCRIPTOR = "android.service.resolver.IResolverRankerResult";
        static final int TRANSACTION_sendResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IResolverRankerResult asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResolverRankerResult)) {
                return (IResolverRankerResult) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "sendResult";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(ResolverTarget.CREATOR);
                parcel.enforceNoDataAvail();
                sendResult(createTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IResolverRankerResult {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.resolver.IResolverRankerResult
            public void sendResult(List<ResolverTarget> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
