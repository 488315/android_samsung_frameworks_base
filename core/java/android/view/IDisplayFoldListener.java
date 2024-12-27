package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IDisplayFoldListener extends IInterface {
    public static final String DESCRIPTOR = "android.view.IDisplayFoldListener";

    void onDisplayFoldChanged(int i, boolean z) throws RemoteException;

    public static class Default implements IDisplayFoldListener {
        @Override // android.view.IDisplayFoldListener
        public void onDisplayFoldChanged(int displayId, boolean folded) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDisplayFoldListener {
        static final int TRANSACTION_onDisplayFoldChanged = 1;

        public Stub() {
            attachInterface(this, IDisplayFoldListener.DESCRIPTOR);
        }

        public static IDisplayFoldListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDisplayFoldListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IDisplayFoldListener)) {
                return (IDisplayFoldListener) iin;
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
                    return "onDisplayFoldChanged";
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
                data.enforceInterface(IDisplayFoldListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDisplayFoldListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onDisplayFoldChanged(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDisplayFoldListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayFoldListener.DESCRIPTOR;
            }

            @Override // android.view.IDisplayFoldListener
            public void onDisplayFoldChanged(int displayId, boolean folded) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDisplayFoldListener.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeBoolean(folded);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
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
