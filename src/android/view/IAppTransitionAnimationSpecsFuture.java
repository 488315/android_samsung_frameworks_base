package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IAppTransitionAnimationSpecsFuture extends IInterface {

    public static class Default implements IAppTransitionAnimationSpecsFuture {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IAppTransitionAnimationSpecsFuture
        public AppTransitionAnimationSpec[] get() throws RemoteException {
            return null;
        }
    }

    AppTransitionAnimationSpec[] get() throws RemoteException;

    public static abstract class Stub extends Binder implements IAppTransitionAnimationSpecsFuture {
        public static final String DESCRIPTOR = "android.view.IAppTransitionAnimationSpecsFuture";
        static final int TRANSACTION_get = 1;

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

        public static IAppTransitionAnimationSpecsFuture asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppTransitionAnimationSpecsFuture)) {
                return (IAppTransitionAnimationSpecsFuture) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "get";
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
                AppTransitionAnimationSpec[] appTransitionAnimationSpecArr = get();
                parcel2.writeNoException();
                parcel2.writeTypedArray(appTransitionAnimationSpecArr, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAppTransitionAnimationSpecsFuture {
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

            @Override // android.view.IAppTransitionAnimationSpecsFuture
            public AppTransitionAnimationSpec[] get() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AppTransitionAnimationSpec[]) obtain2.createTypedArray(AppTransitionAnimationSpec.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
