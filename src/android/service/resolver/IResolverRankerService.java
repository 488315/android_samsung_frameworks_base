package android.service.resolver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.resolver.IResolverRankerResult;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IResolverRankerService extends IInterface {

    public static class Default implements IResolverRankerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.resolver.IResolverRankerService
        public void predict(List<ResolverTarget> list, IResolverRankerResult iResolverRankerResult) throws RemoteException {
        }

        @Override // android.service.resolver.IResolverRankerService
        public void train(List<ResolverTarget> list, int i) throws RemoteException {
        }
    }

    void predict(List<ResolverTarget> list, IResolverRankerResult iResolverRankerResult) throws RemoteException;

    void train(List<ResolverTarget> list, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IResolverRankerService {
        public static final String DESCRIPTOR = "android.service.resolver.IResolverRankerService";
        static final int TRANSACTION_predict = 1;
        static final int TRANSACTION_train = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IResolverRankerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResolverRankerService)) {
                return (IResolverRankerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "predict";
            }
            if (i != 2) {
                return null;
            }
            return "train";
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
                IResolverRankerResult asInterface = IResolverRankerResult.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                predict(createTypedArrayList, asInterface);
            } else if (i == 2) {
                ArrayList createTypedArrayList2 = parcel.createTypedArrayList(ResolverTarget.CREATOR);
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                train(createTypedArrayList2, readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IResolverRankerService {
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

            @Override // android.service.resolver.IResolverRankerService
            public void predict(List<ResolverTarget> list, IResolverRankerResult iResolverRankerResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iResolverRankerResult);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.resolver.IResolverRankerService
            public void train(List<ResolverTarget> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
