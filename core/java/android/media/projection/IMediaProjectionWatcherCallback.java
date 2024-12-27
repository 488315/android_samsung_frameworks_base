package android.media.projection;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.ContentRecordingSession;

public interface IMediaProjectionWatcherCallback extends IInterface {
    void onRecordingSessionSet(
            MediaProjectionInfo mediaProjectionInfo,
            ContentRecordingSession contentRecordingSession)
            throws RemoteException;

    void onStart(MediaProjectionInfo mediaProjectionInfo) throws RemoteException;

    void onStop(MediaProjectionInfo mediaProjectionInfo) throws RemoteException;

    public static class Default implements IMediaProjectionWatcherCallback {
        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onStart(MediaProjectionInfo info) throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onStop(MediaProjectionInfo info) throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onRecordingSessionSet(MediaProjectionInfo info, ContentRecordingSession session)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMediaProjectionWatcherCallback {
        public static final String DESCRIPTOR =
                "android.media.projection.IMediaProjectionWatcherCallback";
        static final int TRANSACTION_onRecordingSessionSet = 3;
        static final int TRANSACTION_onStart = 1;
        static final int TRANSACTION_onStop = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaProjectionWatcherCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMediaProjectionWatcherCallback)) {
                return (IMediaProjectionWatcherCallback) iin;
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
                    return "onStart";
                case 2:
                    return "onStop";
                case 3:
                    return "onRecordingSessionSet";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    MediaProjectionInfo _arg0 =
                            (MediaProjectionInfo) data.readTypedObject(MediaProjectionInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onStart(_arg0);
                    return true;
                case 2:
                    MediaProjectionInfo _arg02 =
                            (MediaProjectionInfo) data.readTypedObject(MediaProjectionInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onStop(_arg02);
                    return true;
                case 3:
                    MediaProjectionInfo _arg03 =
                            (MediaProjectionInfo) data.readTypedObject(MediaProjectionInfo.CREATOR);
                    ContentRecordingSession _arg1 =
                            (ContentRecordingSession)
                                    data.readTypedObject(ContentRecordingSession.CREATOR);
                    data.enforceNoDataAvail();
                    onRecordingSessionSet(_arg03, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMediaProjectionWatcherCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.projection.IMediaProjectionWatcherCallback
            public void onStart(MediaProjectionInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionWatcherCallback
            public void onStop(MediaProjectionInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionWatcherCallback
            public void onRecordingSessionSet(
                    MediaProjectionInfo info, ContentRecordingSession session)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(session, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
