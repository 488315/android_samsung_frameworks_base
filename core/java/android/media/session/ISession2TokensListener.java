package android.media.session;

import android.media.Session2Token;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface ISession2TokensListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.session.ISession2TokensListener";

    void onSession2TokensChanged(List<Session2Token> list) throws RemoteException;

    public static class Default implements ISession2TokensListener {
        @Override // android.media.session.ISession2TokensListener
        public void onSession2TokensChanged(List<Session2Token> tokens) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISession2TokensListener {
        static final int TRANSACTION_onSession2TokensChanged = 1;

        public Stub() {
            attachInterface(this, ISession2TokensListener.DESCRIPTOR);
        }

        public static ISession2TokensListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISession2TokensListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ISession2TokensListener)) {
                return (ISession2TokensListener) iin;
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
                    return "onSession2TokensChanged";
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
                data.enforceInterface(ISession2TokensListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISession2TokensListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            List<Session2Token> _arg0 = data.createTypedArrayList(Session2Token.CREATOR);
                            data.enforceNoDataAvail();
                            onSession2TokensChanged(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISession2TokensListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISession2TokensListener.DESCRIPTOR;
            }

            @Override // android.media.session.ISession2TokensListener
            public void onSession2TokensChanged(List<Session2Token> tokens) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISession2TokensListener.DESCRIPTOR);
                    _data.writeTypedList(tokens, 0);
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
