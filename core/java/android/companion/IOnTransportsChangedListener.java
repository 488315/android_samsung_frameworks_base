package android.companion;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IOnTransportsChangedListener extends IInterface {
    public static final String DESCRIPTOR = "android.companion.IOnTransportsChangedListener";

    void onTransportsChanged(List<AssociationInfo> list) throws RemoteException;

    public static class Default implements IOnTransportsChangedListener {
        @Override // android.companion.IOnTransportsChangedListener
        public void onTransportsChanged(List<AssociationInfo> associations) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IOnTransportsChangedListener {
        static final int TRANSACTION_onTransportsChanged = 1;

        public Stub() {
            attachInterface(this, IOnTransportsChangedListener.DESCRIPTOR);
        }

        public static IOnTransportsChangedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOnTransportsChangedListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IOnTransportsChangedListener)) {
                return (IOnTransportsChangedListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onTransportsChanged";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IOnTransportsChangedListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IOnTransportsChangedListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            List<AssociationInfo> _arg0 = data.createTypedArrayList(AssociationInfo.CREATOR);
                            data.enforceNoDataAvail();
                            onTransportsChanged(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IOnTransportsChangedListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnTransportsChangedListener.DESCRIPTOR;
            }

            @Override // android.companion.IOnTransportsChangedListener
            public void onTransportsChanged(List<AssociationInfo> associations) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnTransportsChangedListener.DESCRIPTOR);
                    _data.writeTypedList(associations, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
