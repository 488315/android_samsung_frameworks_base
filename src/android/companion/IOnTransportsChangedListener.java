package android.companion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IOnTransportsChangedListener extends IInterface {
    public static final String DESCRIPTOR = "android.companion.IOnTransportsChangedListener";

    public static class Default implements IOnTransportsChangedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.IOnTransportsChangedListener
        public void onTransportsChanged(List<AssociationInfo> list) throws RemoteException {
        }
    }

    void onTransportsChanged(List<AssociationInfo> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IOnTransportsChangedListener {
        static final int TRANSACTION_onTransportsChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IOnTransportsChangedListener.DESCRIPTOR);
        }

        public static IOnTransportsChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IOnTransportsChangedListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOnTransportsChangedListener)) {
                return (IOnTransportsChangedListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onTransportsChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOnTransportsChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOnTransportsChangedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AssociationInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onTransportsChanged(arrayListCreateTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IOnTransportsChangedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnTransportsChangedListener.DESCRIPTOR;
            }

            @Override // android.companion.IOnTransportsChangedListener
            public void onTransportsChanged(List<AssociationInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IOnTransportsChangedListener.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
