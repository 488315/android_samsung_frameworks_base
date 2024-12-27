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
public interface ITvAdManager extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdManager";

    void createMediaView(IBinder iBinder, IBinder iBinder2, Rect rect, int i)
            throws RemoteException;

    void createSession(ITvAdClient iTvAdClient, String str, String str2, int i, int i2)
            throws RemoteException;

    void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4)
            throws RemoteException;

    List<TvAdServiceInfo> getTvAdServiceList(int i) throws RemoteException;

    void notifyError(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException;

    void notifyTvInputSessionData(IBinder iBinder, String str, Bundle bundle, int i)
            throws RemoteException;

    void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException;

    void registerCallback(ITvAdManagerCallback iTvAdManagerCallback, int i) throws RemoteException;

    void relayoutMediaView(IBinder iBinder, Rect rect, int i) throws RemoteException;

    void releaseSession(IBinder iBinder, int i) throws RemoteException;

    void removeMediaView(IBinder iBinder, int i) throws RemoteException;

    void resetAdService(IBinder iBinder, int i) throws RemoteException;

    void sendAppLinkCommand(String str, Bundle bundle, int i) throws RemoteException;

    void sendCurrentChannelUri(IBinder iBinder, Uri uri, int i) throws RemoteException;

    void sendCurrentTvInputId(IBinder iBinder, String str, int i) throws RemoteException;

    void sendCurrentVideoBounds(IBinder iBinder, Rect rect, int i) throws RemoteException;

    void sendSigningResult(IBinder iBinder, String str, byte[] bArr, int i) throws RemoteException;

    void sendTrackInfoList(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException;

    void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException;

    void startAdService(IBinder iBinder, int i) throws RemoteException;

    void stopAdService(IBinder iBinder, int i) throws RemoteException;

    void unregisterCallback(ITvAdManagerCallback iTvAdManagerCallback, int i)
            throws RemoteException;

    public static class Default implements ITvAdManager {
        @Override // android.media.tv.ad.ITvAdManager
        public List<TvAdServiceInfo> getTvAdServiceList(int userId) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendAppLinkCommand(String serviceId, Bundle command, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void createSession(
                ITvAdClient client, String serviceId, String type, int seq, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void releaseSession(IBinder sessionToken, int userId) throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void startAdService(IBinder sessionToken, int userId) throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void stopAdService(IBinder sessionToken, int userId) throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void resetAdService(IBinder sessionToken, int userId) throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void setSurface(IBinder sessionToken, Surface surface, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void dispatchSurfaceChanged(
                IBinder sessionToken, int format, int width, int height, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void sendCurrentVideoBounds(IBinder sessionToken, Rect bounds, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void sendCurrentChannelUri(IBinder sessionToken, Uri channelUri, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void sendTrackInfoList(IBinder sessionToken, List<TvTrackInfo> tracks, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void sendCurrentTvInputId(IBinder sessionToken, String inputId, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void sendSigningResult(
                IBinder sessionToken, String signingId, byte[] result, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void notifyError(IBinder sessionToken, String errMsg, Bundle params, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void notifyTvMessage(IBinder sessionToken, int type, Bundle data, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void registerCallback(ITvAdManagerCallback callback, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void unregisterCallback(ITvAdManagerCallback callback, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void createMediaView(
                IBinder sessionToken, IBinder windowToken, Rect frame, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void relayoutMediaView(IBinder sessionToken, Rect frame, int userId)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void removeMediaView(IBinder sessionToken, int userId) throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManager
        public void notifyTvInputSessionData(
                IBinder sessionToken, String type, Bundle data, int userId)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITvAdManager {
        static final int TRANSACTION_createMediaView = 19;
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_dispatchSurfaceChanged = 9;
        static final int TRANSACTION_getTvAdServiceList = 1;
        static final int TRANSACTION_notifyError = 15;
        static final int TRANSACTION_notifyTvInputSessionData = 22;
        static final int TRANSACTION_notifyTvMessage = 16;
        static final int TRANSACTION_registerCallback = 17;
        static final int TRANSACTION_relayoutMediaView = 20;
        static final int TRANSACTION_releaseSession = 4;
        static final int TRANSACTION_removeMediaView = 21;
        static final int TRANSACTION_resetAdService = 7;
        static final int TRANSACTION_sendAppLinkCommand = 2;
        static final int TRANSACTION_sendCurrentChannelUri = 11;
        static final int TRANSACTION_sendCurrentTvInputId = 13;
        static final int TRANSACTION_sendCurrentVideoBounds = 10;
        static final int TRANSACTION_sendSigningResult = 14;
        static final int TRANSACTION_sendTrackInfoList = 12;
        static final int TRANSACTION_setSurface = 8;
        static final int TRANSACTION_startAdService = 5;
        static final int TRANSACTION_stopAdService = 6;
        static final int TRANSACTION_unregisterCallback = 18;

        public Stub() {
            attachInterface(this, ITvAdManager.DESCRIPTOR);
        }

        public static ITvAdManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITvAdManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ITvAdManager)) {
                return (ITvAdManager) iin;
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
                    return "getTvAdServiceList";
                case 2:
                    return "sendAppLinkCommand";
                case 3:
                    return "createSession";
                case 4:
                    return "releaseSession";
                case 5:
                    return "startAdService";
                case 6:
                    return "stopAdService";
                case 7:
                    return "resetAdService";
                case 8:
                    return "setSurface";
                case 9:
                    return "dispatchSurfaceChanged";
                case 10:
                    return "sendCurrentVideoBounds";
                case 11:
                    return "sendCurrentChannelUri";
                case 12:
                    return "sendTrackInfoList";
                case 13:
                    return "sendCurrentTvInputId";
                case 14:
                    return "sendSigningResult";
                case 15:
                    return "notifyError";
                case 16:
                    return "notifyTvMessage";
                case 17:
                    return "registerCallback";
                case 18:
                    return "unregisterCallback";
                case 19:
                    return "createMediaView";
                case 20:
                    return "relayoutMediaView";
                case 21:
                    return "removeMediaView";
                case 22:
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
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ITvAdManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITvAdManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    List<TvAdServiceInfo> _result = getTvAdServiceList(_arg0);
                    reply.writeNoException();
                    reply.writeTypedList(_result, 1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    sendAppLinkCommand(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    ITvAdClient _arg04 = ITvAdClient.Stub.asInterface(_arg03);
                    String _arg12 = data.readString();
                    String _arg22 = data.readString();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    createSession(_arg04, _arg12, _arg22, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                case 4:
                    IBinder _arg05 = data.readStrongBinder();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    releaseSession(_arg05, _arg13);
                    reply.writeNoException();
                    return true;
                case 5:
                    IBinder _arg06 = data.readStrongBinder();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    startAdService(_arg06, _arg14);
                    reply.writeNoException();
                    return true;
                case 6:
                    IBinder _arg07 = data.readStrongBinder();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    stopAdService(_arg07, _arg15);
                    reply.writeNoException();
                    return true;
                case 7:
                    IBinder _arg08 = data.readStrongBinder();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    resetAdService(_arg08, _arg16);
                    reply.writeNoException();
                    return true;
                case 8:
                    IBinder _arg09 = data.readStrongBinder();
                    Surface _arg17 = (Surface) data.readTypedObject(Surface.CREATOR);
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    setSurface(_arg09, _arg17, _arg23);
                    reply.writeNoException();
                    return true;
                case 9:
                    IBinder _arg010 = data.readStrongBinder();
                    int _arg18 = data.readInt();
                    int _arg24 = data.readInt();
                    int _arg32 = data.readInt();
                    int _arg42 = data.readInt();
                    data.enforceNoDataAvail();
                    dispatchSurfaceChanged(_arg010, _arg18, _arg24, _arg32, _arg42);
                    reply.writeNoException();
                    return true;
                case 10:
                    IBinder _arg011 = data.readStrongBinder();
                    Rect _arg19 = (Rect) data.readTypedObject(Rect.CREATOR);
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    sendCurrentVideoBounds(_arg011, _arg19, _arg25);
                    reply.writeNoException();
                    return true;
                case 11:
                    IBinder _arg012 = data.readStrongBinder();
                    Uri _arg110 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    sendCurrentChannelUri(_arg012, _arg110, _arg26);
                    reply.writeNoException();
                    return true;
                case 12:
                    IBinder _arg013 = data.readStrongBinder();
                    List<TvTrackInfo> _arg111 = data.createTypedArrayList(TvTrackInfo.CREATOR);
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    sendTrackInfoList(_arg013, _arg111, _arg27);
                    reply.writeNoException();
                    return true;
                case 13:
                    IBinder _arg014 = data.readStrongBinder();
                    String _arg112 = data.readString();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    sendCurrentTvInputId(_arg014, _arg112, _arg28);
                    reply.writeNoException();
                    return true;
                case 14:
                    IBinder _arg015 = data.readStrongBinder();
                    String _arg113 = data.readString();
                    byte[] _arg29 = data.createByteArray();
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    sendSigningResult(_arg015, _arg113, _arg29, _arg33);
                    reply.writeNoException();
                    return true;
                case 15:
                    IBinder _arg016 = data.readStrongBinder();
                    String _arg114 = data.readString();
                    Bundle _arg210 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg34 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyError(_arg016, _arg114, _arg210, _arg34);
                    reply.writeNoException();
                    return true;
                case 16:
                    IBinder _arg017 = data.readStrongBinder();
                    int _arg115 = data.readInt();
                    Bundle _arg211 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg35 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyTvMessage(_arg017, _arg115, _arg211, _arg35);
                    reply.writeNoException();
                    return true;
                case 17:
                    ITvAdManagerCallback _arg018 =
                            ITvAdManagerCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg116 = data.readInt();
                    data.enforceNoDataAvail();
                    registerCallback(_arg018, _arg116);
                    reply.writeNoException();
                    return true;
                case 18:
                    IBinder _arg019 = data.readStrongBinder();
                    ITvAdManagerCallback _arg020 = ITvAdManagerCallback.Stub.asInterface(_arg019);
                    int _arg117 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterCallback(_arg020, _arg117);
                    reply.writeNoException();
                    return true;
                case 19:
                    IBinder _arg021 = data.readStrongBinder();
                    IBinder _arg118 = data.readStrongBinder();
                    Rect _arg212 = (Rect) data.readTypedObject(Rect.CREATOR);
                    int _arg36 = data.readInt();
                    data.enforceNoDataAvail();
                    createMediaView(_arg021, _arg118, _arg212, _arg36);
                    reply.writeNoException();
                    return true;
                case 20:
                    IBinder _arg022 = data.readStrongBinder();
                    Rect _arg119 = (Rect) data.readTypedObject(Rect.CREATOR);
                    int _arg213 = data.readInt();
                    data.enforceNoDataAvail();
                    relayoutMediaView(_arg022, _arg119, _arg213);
                    reply.writeNoException();
                    return true;
                case 21:
                    IBinder _arg023 = data.readStrongBinder();
                    int _arg120 = data.readInt();
                    data.enforceNoDataAvail();
                    removeMediaView(_arg023, _arg120);
                    reply.writeNoException();
                    return true;
                case 22:
                    IBinder _arg024 = data.readStrongBinder();
                    String _arg121 = data.readString();
                    Bundle _arg214 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg37 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyTvInputSessionData(_arg024, _arg121, _arg214, _arg37);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITvAdManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdManager.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdManager
            public List<TvAdServiceInfo> getTvAdServiceList(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<TvAdServiceInfo> _result =
                            _reply.createTypedArrayList(TvAdServiceInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendAppLinkCommand(String serviceId, Bundle command, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeString(serviceId);
                    _data.writeTypedObject(command, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void createSession(
                    ITvAdClient client, String serviceId, String type, int seq, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeString(serviceId);
                    _data.writeString(type);
                    _data.writeInt(seq);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void releaseSession(IBinder sessionToken, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void startAdService(IBinder sessionToken, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void stopAdService(IBinder sessionToken, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void resetAdService(IBinder sessionToken, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void setSurface(IBinder sessionToken, Surface surface, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeTypedObject(surface, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void dispatchSurfaceChanged(
                    IBinder sessionToken, int format, int width, int height, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(format);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(userId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentVideoBounds(IBinder sessionToken, Rect bounds, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeTypedObject(bounds, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentChannelUri(IBinder sessionToken, Uri channelUri, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeTypedObject(channelUri, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendTrackInfoList(
                    IBinder sessionToken, List<TvTrackInfo> tracks, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeTypedList(tracks, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentTvInputId(IBinder sessionToken, String inputId, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeString(inputId);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendSigningResult(
                    IBinder sessionToken, String signingId, byte[] result, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeString(signingId);
                    _data.writeByteArray(result);
                    _data.writeInt(userId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyError(IBinder sessionToken, String errMsg, Bundle params, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeString(errMsg);
                    _data.writeTypedObject(params, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyTvMessage(IBinder sessionToken, int type, Bundle data, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(type);
                    _data.writeTypedObject(data, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void registerCallback(ITvAdManagerCallback callback, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(userId);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void unregisterCallback(ITvAdManagerCallback callback, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(userId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void createMediaView(
                    IBinder sessionToken, IBinder windowToken, Rect frame, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeStrongBinder(windowToken);
                    _data.writeTypedObject(frame, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void relayoutMediaView(IBinder sessionToken, Rect frame, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeTypedObject(frame, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void removeMediaView(IBinder sessionToken, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(userId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyTvInputSessionData(
                    IBinder sessionToken, String type, Bundle data, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeString(type);
                    _data.writeTypedObject(data, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }
    }
}
