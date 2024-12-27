package com.samsung.android.mocca;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IMoccaEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.mocca.IMoccaEventListener";

    void onContextAvailable(String str) throws RemoteException;

    void onContextChanged(ContextEvent contextEvent) throws RemoteException;

    void onContextStopped(String str) throws RemoteException;

    void onContextUnavailable(String str) throws RemoteException;

    public static class Default implements IMoccaEventListener {
        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextChanged(ContextEvent event) throws RemoteException {}

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextStopped(String contextType) throws RemoteException {}

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextAvailable(String contextType) throws RemoteException {}

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextUnavailable(String contextType) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMoccaEventListener {
        static final int TRANSACTION_onContextAvailable = 3;
        static final int TRANSACTION_onContextChanged = 1;
        static final int TRANSACTION_onContextStopped = 2;
        static final int TRANSACTION_onContextUnavailable = 4;

        public Stub() {
            attachInterface(this, IMoccaEventListener.DESCRIPTOR);
        }

        public static IMoccaEventListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMoccaEventListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IMoccaEventListener)) {
                return (IMoccaEventListener) iin;
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
                    return "onContextChanged";
                case 2:
                    return "onContextStopped";
                case 3:
                    return "onContextAvailable";
                case 4:
                    return "onContextUnavailable";
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
                data.enforceInterface(IMoccaEventListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMoccaEventListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ContextEvent _arg0 = (ContextEvent) data.readTypedObject(ContextEvent.CREATOR);
                    data.enforceNoDataAvail();
                    onContextChanged(_arg0);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    data.enforceNoDataAvail();
                    onContextStopped(_arg02);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    onContextAvailable(_arg03);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    data.enforceNoDataAvail();
                    onContextUnavailable(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMoccaEventListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMoccaEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.mocca.IMoccaEventListener
            public void onContextChanged(ContextEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMoccaEventListener.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaEventListener
            public void onContextStopped(String contextType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMoccaEventListener.DESCRIPTOR);
                    _data.writeString(contextType);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaEventListener
            public void onContextAvailable(String contextType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMoccaEventListener.DESCRIPTOR);
                    _data.writeString(contextType);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaEventListener
            public void onContextUnavailable(String contextType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMoccaEventListener.DESCRIPTOR);
                    _data.writeString(contextType);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
