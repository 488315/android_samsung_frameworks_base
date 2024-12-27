package com.samsung.android.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISemEpdgListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.ISemEpdgListener";

    void onEpdgAvailable(int i, boolean z, int i2) throws RemoteException;

    void onEpdgShowPopup(int i, int i2) throws RemoteException;

    void onHandoverResult(int i, int i2, int i3, String str) throws RemoteException;

    void onIpsecConnection(int i, String str, int i2, int i3) throws RemoteException;

    void onIpsecDisconnection(int i, String str) throws RemoteException;

    public static class Default implements ISemEpdgListener {
        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onEpdgAvailable(int phoneId, boolean isAvailable, int wifiState)
                throws RemoteException {}

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onHandoverResult(int phoneId, int isL2WHandover, int result, String apnType)
                throws RemoteException {}

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onIpsecConnection(int phoneId, String apnType, int ikeError, int throttleCount)
                throws RemoteException {}

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onIpsecDisconnection(int phoneId, String apnType) throws RemoteException {}

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onEpdgShowPopup(int phoneId, int popupType) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemEpdgListener {
        static final int TRANSACTION_onEpdgAvailable = 1;
        static final int TRANSACTION_onEpdgShowPopup = 5;
        static final int TRANSACTION_onHandoverResult = 2;
        static final int TRANSACTION_onIpsecConnection = 3;
        static final int TRANSACTION_onIpsecDisconnection = 4;

        public Stub() {
            attachInterface(this, ISemEpdgListener.DESCRIPTOR);
        }

        public static ISemEpdgListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemEpdgListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemEpdgListener)) {
                return (ISemEpdgListener) iin;
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
                    return "onEpdgAvailable";
                case 2:
                    return "onHandoverResult";
                case 3:
                    return "onIpsecConnection";
                case 4:
                    return "onIpsecDisconnection";
                case 5:
                    return "onEpdgShowPopup";
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
                data.enforceInterface(ISemEpdgListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemEpdgListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    boolean _arg1 = data.readBoolean();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    onEpdgAvailable(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    int _arg22 = data.readInt();
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    onHandoverResult(_arg02, _arg12, _arg22, _arg3);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    String _arg13 = data.readString();
                    int _arg23 = data.readInt();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    onIpsecConnection(_arg03, _arg13, _arg23, _arg32);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    onIpsecDisconnection(_arg04, _arg14);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    onEpdgShowPopup(_arg05, _arg15);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemEpdgListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemEpdgListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onEpdgAvailable(int phoneId, boolean isAvailable, int wifiState)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeBoolean(isAvailable);
                    _data.writeInt(wifiState);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onHandoverResult(int phoneId, int isL2WHandover, int result, String apnType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(isL2WHandover);
                    _data.writeInt(result);
                    _data.writeString(apnType);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onIpsecConnection(
                    int phoneId, String apnType, int ikeError, int throttleCount)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeString(apnType);
                    _data.writeInt(ikeError);
                    _data.writeInt(throttleCount);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onIpsecDisconnection(int phoneId, String apnType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeString(apnType);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onEpdgShowPopup(int phoneId, int popupType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(popupType);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
