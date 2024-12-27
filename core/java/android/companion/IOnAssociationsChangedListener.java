package android.companion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes.dex */
public interface IOnAssociationsChangedListener extends IInterface {
    public static final String DESCRIPTOR = "android.companion.IOnAssociationsChangedListener";

    void onAssociationsChanged(List<AssociationInfo> list) throws RemoteException;

    public static class Default implements IOnAssociationsChangedListener {
        @Override // android.companion.IOnAssociationsChangedListener
        public void onAssociationsChanged(List<AssociationInfo> associations)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IOnAssociationsChangedListener {
        static final int TRANSACTION_onAssociationsChanged = 1;

        public Stub() {
            attachInterface(this, IOnAssociationsChangedListener.DESCRIPTOR);
        }

        public static IOnAssociationsChangedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOnAssociationsChangedListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IOnAssociationsChangedListener)) {
                return (IOnAssociationsChangedListener) iin;
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
                    return "onAssociationsChanged";
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
                data.enforceInterface(IOnAssociationsChangedListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOnAssociationsChangedListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<AssociationInfo> _arg0 =
                            data.createTypedArrayList(AssociationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onAssociationsChanged(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOnAssociationsChangedListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnAssociationsChangedListener.DESCRIPTOR;
            }

            @Override // android.companion.IOnAssociationsChangedListener
            public void onAssociationsChanged(List<AssociationInfo> associations)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnAssociationsChangedListener.DESCRIPTOR);
                    _data.writeTypedList(associations, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
