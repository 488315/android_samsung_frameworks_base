package android.media.tv.ad;

import android.graphics.Rect;
import android.media.tv.TvTrackInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;
import java.util.List;

/* loaded from: classes3.dex */
public interface ITvAdSession extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdSession";

    void createMediaView(IBinder iBinder, Rect rect) throws RemoteException;

    void dispatchSurfaceChanged(int i, int i2, int i3) throws RemoteException;

    void notifyError(String str, Bundle bundle) throws RemoteException;

    void notifyTvInputSessionData(String str, Bundle bundle) throws RemoteException;

    void notifyTvMessage(int i, Bundle bundle) throws RemoteException;

    void relayoutMediaView(Rect rect) throws RemoteException;

    void release() throws RemoteException;

    void removeMediaView() throws RemoteException;

    void resetAdService() throws RemoteException;

    void sendCurrentChannelUri(Uri uri) throws RemoteException;

    void sendCurrentTvInputId(String str) throws RemoteException;

    void sendCurrentVideoBounds(Rect rect) throws RemoteException;

    void sendSigningResult(String str, byte[] bArr) throws RemoteException;

    void sendTrackInfoList(List<TvTrackInfo> list) throws RemoteException;

    void setSurface(Surface surface) throws RemoteException;

    void startAdService() throws RemoteException;

    void stopAdService() throws RemoteException;

    public static class Default implements ITvAdSession {
        @Override // android.media.tv.ad.ITvAdSession
        public void release() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void startAdService() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void stopAdService() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void resetAdService() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void setSurface(Surface surface) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void dispatchSurfaceChanged(int format, int width, int height) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendCurrentVideoBounds(Rect bounds) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendCurrentChannelUri(Uri channelUri) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendTrackInfoList(List<TvTrackInfo> tracks) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendCurrentTvInputId(String inputId) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendSigningResult(String signingId, byte[] result) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void notifyError(String errMsg, Bundle params) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void notifyTvMessage(int type, Bundle data) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void createMediaView(IBinder windowToken, Rect frame) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void relayoutMediaView(Rect frame) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void removeMediaView() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void notifyTvInputSessionData(String type, Bundle data) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITvAdSession {
        static final int TRANSACTION_createMediaView = 14;
        static final int TRANSACTION_dispatchSurfaceChanged = 6;
        static final int TRANSACTION_notifyError = 12;
        static final int TRANSACTION_notifyTvInputSessionData = 17;
        static final int TRANSACTION_notifyTvMessage = 13;
        static final int TRANSACTION_relayoutMediaView = 15;
        static final int TRANSACTION_release = 1;
        static final int TRANSACTION_removeMediaView = 16;
        static final int TRANSACTION_resetAdService = 4;
        static final int TRANSACTION_sendCurrentChannelUri = 8;
        static final int TRANSACTION_sendCurrentTvInputId = 10;
        static final int TRANSACTION_sendCurrentVideoBounds = 7;
        static final int TRANSACTION_sendSigningResult = 11;
        static final int TRANSACTION_sendTrackInfoList = 9;
        static final int TRANSACTION_setSurface = 5;
        static final int TRANSACTION_startAdService = 2;
        static final int TRANSACTION_stopAdService = 3;

        public Stub() {
            attachInterface(this, ITvAdSession.DESCRIPTOR);
        }

        public static ITvAdSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITvAdSession.DESCRIPTOR);
            if (iin != null && (iin instanceof ITvAdSession)) {
                return (ITvAdSession) iin;
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
                    return "release";
                case 2:
                    return "startAdService";
                case 3:
                    return "stopAdService";
                case 4:
                    return "resetAdService";
                case 5:
                    return "setSurface";
                case 6:
                    return "dispatchSurfaceChanged";
                case 7:
                    return "sendCurrentVideoBounds";
                case 8:
                    return "sendCurrentChannelUri";
                case 9:
                    return "sendTrackInfoList";
                case 10:
                    return "sendCurrentTvInputId";
                case 11:
                    return "sendSigningResult";
                case 12:
                    return "notifyError";
                case 13:
                    return "notifyTvMessage";
                case 14:
                    return "createMediaView";
                case 15:
                    return "relayoutMediaView";
                case 16:
                    return "removeMediaView";
                case 17:
                    return "notifyTvInputSessionData";
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
                data.enforceInterface(ITvAdSession.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITvAdSession.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    release();
                    return true;
                case 2:
                    startAdService();
                    return true;
                case 3:
                    stopAdService();
                    return true;
                case 4:
                    resetAdService();
                    return true;
                case 5:
                    Surface _arg0 = (Surface) data.readTypedObject(Surface.CREATOR);
                    data.enforceNoDataAvail();
                    setSurface(_arg0);
                    return true;
                case 6:
                    int _arg02 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    dispatchSurfaceChanged(_arg02, _arg1, _arg2);
                    return true;
                case 7:
                    Rect _arg03 = (Rect) data.readTypedObject(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    sendCurrentVideoBounds(_arg03);
                    return true;
                case 8:
                    Uri _arg04 = (Uri) data.readTypedObject(Uri.CREATOR);
                    data.enforceNoDataAvail();
                    sendCurrentChannelUri(_arg04);
                    return true;
                case 9:
                    List<TvTrackInfo> _arg05 = data.createTypedArrayList(TvTrackInfo.CREATOR);
                    data.enforceNoDataAvail();
                    sendTrackInfoList(_arg05);
                    return true;
                case 10:
                    String _arg06 = data.readString();
                    data.enforceNoDataAvail();
                    sendCurrentTvInputId(_arg06);
                    return true;
                case 11:
                    String _arg07 = data.readString();
                    byte[] _arg12 = data.createByteArray();
                    data.enforceNoDataAvail();
                    sendSigningResult(_arg07, _arg12);
                    return true;
                case 12:
                    String _arg08 = data.readString();
                    Bundle _arg13 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    notifyError(_arg08, _arg13);
                    return true;
                case 13:
                    int _arg09 = data.readInt();
                    Bundle _arg14 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    notifyTvMessage(_arg09, _arg14);
                    return true;
                case 14:
                    IBinder _arg010 = data.readStrongBinder();
                    Rect _arg15 = (Rect) data.readTypedObject(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    createMediaView(_arg010, _arg15);
                    return true;
                case 15:
                    Rect _arg011 = (Rect) data.readTypedObject(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    relayoutMediaView(_arg011);
                    return true;
                case 16:
                    removeMediaView();
                    return true;
                case 17:
                    String _arg012 = data.readString();
                    Bundle _arg16 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    notifyTvInputSessionData(_arg012, _arg16);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITvAdSession {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdSession.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void release() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void startAdService() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void stopAdService() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void resetAdService() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void setSurface(Surface surface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeTypedObject(surface, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void dispatchSurfaceChanged(int format, int width, int height) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeInt(format);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendCurrentVideoBounds(Rect bounds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeTypedObject(bounds, 0);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendCurrentChannelUri(Uri channelUri) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeTypedObject(channelUri, 0);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendTrackInfoList(List<TvTrackInfo> tracks) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeTypedList(tracks, 0);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendCurrentTvInputId(String inputId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeString(inputId);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendSigningResult(String signingId, byte[] result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeString(signingId);
                    _data.writeByteArray(result);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void notifyError(String errMsg, Bundle params) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeString(errMsg);
                    _data.writeTypedObject(params, 0);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void notifyTvMessage(int type, Bundle data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(data, 0);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void createMediaView(IBinder windowToken, Rect frame) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    _data.writeTypedObject(frame, 0);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void relayoutMediaView(Rect frame) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeTypedObject(frame, 0);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void removeMediaView() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void notifyTvInputSessionData(String type, Bundle data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeTypedObject(data, 0);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16;
        }
    }
}
