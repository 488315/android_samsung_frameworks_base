package android.media.tv.ad;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;

/* loaded from: classes3.dex */
public interface ITvAdClient extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdClient";

    void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void onRequestCurrentChannelUri(int i) throws RemoteException;

    void onRequestCurrentTvInputId(int i) throws RemoteException;

    void onRequestCurrentVideoBounds(int i) throws RemoteException;

    void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) throws RemoteException;

    void onRequestTrackInfoList(int i) throws RemoteException;

    void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException;

    void onSessionReleased(int i) throws RemoteException;

    void onTvAdSessionData(String str, Bundle bundle, int i) throws RemoteException;

    public static class Default implements ITvAdClient {
        @Override // android.media.tv.ad.ITvAdClient
        public void onSessionCreated(String serviceId, IBinder token, InputChannel channel, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onSessionReleased(int seq) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onLayoutSurface(int left, int top, int right, int bottom, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentVideoBounds(int seq) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentChannelUri(int seq) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestTrackInfoList(int seq) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentTvInputId(int seq) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestSigning(String id, String algorithm, String alias, byte[] data, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onTvAdSessionData(String type, Bundle data, int seq) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITvAdClient {
        static final int TRANSACTION_onLayoutSurface = 3;
        static final int TRANSACTION_onRequestCurrentChannelUri = 5;
        static final int TRANSACTION_onRequestCurrentTvInputId = 7;
        static final int TRANSACTION_onRequestCurrentVideoBounds = 4;
        static final int TRANSACTION_onRequestSigning = 8;
        static final int TRANSACTION_onRequestTrackInfoList = 6;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionReleased = 2;
        static final int TRANSACTION_onTvAdSessionData = 9;

        public Stub() {
            attachInterface(this, ITvAdClient.DESCRIPTOR);
        }

        public static ITvAdClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITvAdClient.DESCRIPTOR);
            if (iin != null && (iin instanceof ITvAdClient)) {
                return (ITvAdClient) iin;
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
                    return "onSessionReleased";
                case 3:
                    return "onLayoutSurface";
                case 4:
                    return "onRequestCurrentVideoBounds";
                case 5:
                    return "onRequestCurrentChannelUri";
                case 6:
                    return "onRequestTrackInfoList";
                case 7:
                    return "onRequestCurrentTvInputId";
                case 8:
                    return "onRequestSigning";
                case 9:
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
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ITvAdClient.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITvAdClient.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    IBinder _arg1 = data.readStrongBinder();
                    InputChannel _arg2 = (InputChannel) data.readTypedObject(InputChannel.CREATOR);
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    onSessionCreated(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onSessionReleased(_arg02);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg12 = data.readInt();
                    int _arg22 = data.readInt();
                    int _arg32 = data.readInt();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    onLayoutSurface(_arg03, _arg12, _arg22, _arg32, _arg4);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestCurrentVideoBounds(_arg04);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestCurrentChannelUri(_arg05);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestTrackInfoList(_arg06);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestCurrentTvInputId(_arg07);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    String _arg13 = data.readString();
                    String _arg23 = data.readString();
                    byte[] _arg33 = data.createByteArray();
                    int _arg42 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestSigning(_arg08, _arg13, _arg23, _arg33, _arg42);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    Bundle _arg14 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    onTvAdSessionData(_arg09, _arg14, _arg24);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITvAdClient {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdClient.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onSessionCreated(String serviceId, IBinder token, InputChannel channel, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    _data.writeString(serviceId);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(channel, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onSessionReleased(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onLayoutSurface(int left, int top, int right, int bottom, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    _data.writeInt(left);
                    _data.writeInt(top);
                    _data.writeInt(right);
                    _data.writeInt(bottom);
                    _data.writeInt(seq);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentVideoBounds(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentChannelUri(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestTrackInfoList(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentTvInputId(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestSigning(String id, String algorithm, String alias, byte[] data, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeString(algorithm);
                    _data.writeString(alias);
                    _data.writeByteArray(data);
                    _data.writeInt(seq);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onTvAdSessionData(String type, Bundle data, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeTypedObject(data, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
