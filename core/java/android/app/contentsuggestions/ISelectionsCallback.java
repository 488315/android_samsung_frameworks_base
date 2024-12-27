package android.app.contentsuggestions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes.dex */
public interface ISelectionsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.contentsuggestions.ISelectionsCallback";

    void onContentSelectionsAvailable(int i, List<ContentSelection> list) throws RemoteException;

    public static class Default implements ISelectionsCallback {
        @Override // android.app.contentsuggestions.ISelectionsCallback
        public void onContentSelectionsAvailable(int statusCode, List<ContentSelection> selections)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISelectionsCallback {
        static final int TRANSACTION_onContentSelectionsAvailable = 1;

        public Stub() {
            attachInterface(this, ISelectionsCallback.DESCRIPTOR);
        }

        public static ISelectionsCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISelectionsCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISelectionsCallback)) {
                return (ISelectionsCallback) iin;
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
                    return "onContentSelectionsAvailable";
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
                data.enforceInterface(ISelectionsCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISelectionsCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    List<ContentSelection> _arg1 =
                            data.createTypedArrayList(ContentSelection.CREATOR);
                    data.enforceNoDataAvail();
                    onContentSelectionsAvailable(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISelectionsCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISelectionsCallback.DESCRIPTOR;
            }

            @Override // android.app.contentsuggestions.ISelectionsCallback
            public void onContentSelectionsAvailable(
                    int statusCode, List<ContentSelection> selections) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISelectionsCallback.DESCRIPTOR);
                    _data.writeInt(statusCode);
                    _data.writeTypedList(selections, 0);
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
