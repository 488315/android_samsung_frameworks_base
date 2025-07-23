package android.companion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IOnAssociationsChangedListener extends IInterface {
    public static final String DESCRIPTOR = "android.companion.IOnAssociationsChangedListener";

    public static class Default implements IOnAssociationsChangedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.IOnAssociationsChangedListener
        public void onAssociationsChanged(List<AssociationInfo> list) throws RemoteException {
        }
    }

    void onAssociationsChanged(List<AssociationInfo> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IOnAssociationsChangedListener {
        static final int TRANSACTION_onAssociationsChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IOnAssociationsChangedListener.DESCRIPTOR);
        }

        public static IOnAssociationsChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOnAssociationsChangedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOnAssociationsChangedListener)) {
                return (IOnAssociationsChangedListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAssociationsChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOnAssociationsChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOnAssociationsChangedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(AssociationInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onAssociationsChanged(createTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IOnAssociationsChangedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnAssociationsChangedListener.DESCRIPTOR;
            }

            @Override // android.companion.IOnAssociationsChangedListener
            public void onAssociationsChanged(List<AssociationInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOnAssociationsChangedListener.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
