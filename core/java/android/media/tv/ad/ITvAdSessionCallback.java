package android.media.tv.ad;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITvAdSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdSessionCallback";

    void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException;

    void onRequestCurrentChannelUri() throws RemoteException;

    void onRequestCurrentTvInputId() throws RemoteException;

    void onRequestCurrentVideoBounds() throws RemoteException;

    void onRequestSigning(String str, String str2, String str3, byte[] bArr) throws RemoteException;

    void onRequestTrackInfoList() throws RemoteException;

    void onSessionCreated(ITvAdSession iTvAdSession) throws RemoteException;

    void onTvAdSessionData(String str, Bundle bundle) throws RemoteException;

    public static class Default implements ITvAdSessionCallback {
        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onSessionCreated(ITvAdSession session) throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onLayoutSurface(int left, int top, int right, int bottom)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestCurrentVideoBounds() throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestCurrentChannelUri() throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestTrackInfoList() throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestCurrentTvInputId() throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestSigning(String id, String algorithm, String alias, byte[] data)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onTvAdSessionData(String type, Bundle data) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITvAdSessionCallback {
        static final int TRANSACTION_onLayoutSurface = 2;
        static final int TRANSACTION_onRequestCurrentChannelUri = 4;
        static final int TRANSACTION_onRequestCurrentTvInputId = 6;
        static final int TRANSACTION_onRequestCurrentVideoBounds = 3;
        static final int TRANSACTION_onRequestSigning = 7;
        static final int TRANSACTION_onRequestTrackInfoList = 5;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onTvAdSessionData = 8;

        public Stub() {
            attachInterface(this, ITvAdSessionCallback.DESCRIPTOR);
        }

        public static ITvAdSessionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITvAdSessionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ITvAdSessionCallback)) {
                return (ITvAdSessionCallback) iin;
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
                    return "onSessionCreated";
                case 2:
                    return "onLayoutSurface";
                case 3:
                    return "onRequestCurrentVideoBounds";
                case 4:
                    return "onRequestCurrentChannelUri";
                case 5:
                    return "onRequestTrackInfoList";
                case 6:
                    return "onRequestCurrentTvInputId";
                case 7:
                    return "onRequestSigning";
                case 8:
                    return "onTvAdSessionData";
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
                data.enforceInterface(ITvAdSessionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITvAdSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ITvAdSession _arg0 = ITvAdSession.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onSessionCreated(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    onLayoutSurface(_arg02, _arg1, _arg2, _arg3);
                    return true;
                case 3:
                    onRequestCurrentVideoBounds();
                    return true;
                case 4:
                    onRequestCurrentChannelUri();
                    return true;
                case 5:
                    onRequestTrackInfoList();
                    return true;
                case 6:
                    onRequestCurrentTvInputId();
                    return true;
                case 7:
                    String _arg03 = data.readString();
                    String _arg12 = data.readString();
                    String _arg22 = data.readString();
                    byte[] _arg32 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onRequestSigning(_arg03, _arg12, _arg22, _arg32);
                    return true;
                case 8:
                    String _arg04 = data.readString();
                    Bundle _arg13 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onTvAdSessionData(_arg04, _arg13);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITvAdSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdSessionCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onSessionCreated(ITvAdSession session) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    _data.writeStrongInterface(session);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onLayoutSurface(int left, int top, int right, int bottom)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    _data.writeInt(left);
                    _data.writeInt(top);
                    _data.writeInt(right);
                    _data.writeInt(bottom);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentVideoBounds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentChannelUri() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestTrackInfoList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentTvInputId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestSigning(String id, String algorithm, String alias, byte[] data)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeString(algorithm);
                    _data.writeString(alias);
                    _data.writeByteArray(data);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onTvAdSessionData(String type, Bundle data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeTypedObject(data, 0);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
