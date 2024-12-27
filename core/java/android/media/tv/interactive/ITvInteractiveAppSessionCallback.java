package android.media.tv.interactive;

import android.graphics.Rect;
import android.media.tv.AdBuffer;
import android.media.tv.AdRequest;
import android.media.tv.BroadcastInfoRequest;
import android.media.tv.TvRecordingInfo;
import android.media.tv.interactive.ITvInteractiveAppSession;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITvInteractiveAppSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppSessionCallback";

    void onAdBufferReady(AdBuffer adBuffer) throws RemoteException;

    void onAdRequest(AdRequest adRequest) throws RemoteException;

    void onBiInteractiveAppCreated(Uri uri, String str) throws RemoteException;

    void onBroadcastInfoRequest(BroadcastInfoRequest broadcastInfoRequest) throws RemoteException;

    void onCommandRequest(String str, Bundle bundle) throws RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException;

    void onRemoveBroadcastInfo(int i) throws RemoteException;

    void onRequestAvailableSpeeds() throws RemoteException;

    void onRequestCertificate(String str, int i) throws RemoteException;

    void onRequestCurrentChannelLcn() throws RemoteException;

    void onRequestCurrentChannelUri() throws RemoteException;

    void onRequestCurrentTvInputId() throws RemoteException;

    void onRequestCurrentVideoBounds() throws RemoteException;

    void onRequestScheduleRecording(String str, String str2, Uri uri, Uri uri2, Bundle bundle) throws RemoteException;

    void onRequestScheduleRecording2(String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle) throws RemoteException;

    void onRequestSelectedTrackInfo() throws RemoteException;

    void onRequestSigning(String str, String str2, String str3, byte[] bArr) throws RemoteException;

    void onRequestSigning2(String str, String str2, String str3, int i, byte[] bArr) throws RemoteException;

    void onRequestStartRecording(String str, Uri uri) throws RemoteException;

    void onRequestStopRecording(String str) throws RemoteException;

    void onRequestStreamVolume() throws RemoteException;

    void onRequestTimeShiftMode() throws RemoteException;

    void onRequestTrackInfoList() throws RemoteException;

    void onRequestTvRecordingInfo(String str) throws RemoteException;

    void onRequestTvRecordingInfoList(int i) throws RemoteException;

    void onSessionCreated(ITvInteractiveAppSession iTvInteractiveAppSession) throws RemoteException;

    void onSessionStateChanged(int i, int i2) throws RemoteException;

    void onSetTvRecordingInfo(String str, TvRecordingInfo tvRecordingInfo) throws RemoteException;

    void onSetVideoBounds(Rect rect) throws RemoteException;

    void onTeletextAppStateChanged(int i) throws RemoteException;

    void onTimeShiftCommandRequest(String str, Bundle bundle) throws RemoteException;

    public static class Default implements ITvInteractiveAppSessionCallback {
        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSessionCreated(ITvInteractiveAppSession session) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onLayoutSurface(int left, int top, int right, int bottom) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onBroadcastInfoRequest(BroadcastInfoRequest request) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRemoveBroadcastInfo(int id) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSessionStateChanged(int state, int err) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onBiInteractiveAppCreated(Uri biIAppUri, String biIAppId) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onTeletextAppStateChanged(int state) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onAdBufferReady(AdBuffer buffer) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onCommandRequest(String cmdType, Bundle parameters) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onTimeShiftCommandRequest(String cmdType, Bundle parameters) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSetVideoBounds(Rect rect) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentVideoBounds() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentChannelUri() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentChannelLcn() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestStreamVolume() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTrackInfoList() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentTvInputId() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTimeShiftMode() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestAvailableSpeeds() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestSelectedTrackInfo() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestStartRecording(String requestId, Uri programUri) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestStopRecording(String recordingId) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestScheduleRecording(String requestId, String inputId, Uri channelUri, Uri programUri, Bundle params) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestScheduleRecording2(String requestId, String inputId, Uri channelUri, long start, long duration, int repeat, Bundle params) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSetTvRecordingInfo(String recordingId, TvRecordingInfo recordingInfo) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTvRecordingInfo(String recordingId) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTvRecordingInfoList(int type) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestSigning(String id, String algorithm, String alias, byte[] data) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestSigning2(String id, String algorithm, String host, int port, byte[] data) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCertificate(String host, int port) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onAdRequest(AdRequest request) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITvInteractiveAppSessionCallback {
        static final int TRANSACTION_onAdBufferReady = 8;
        static final int TRANSACTION_onAdRequest = 31;
        static final int TRANSACTION_onBiInteractiveAppCreated = 6;
        static final int TRANSACTION_onBroadcastInfoRequest = 3;
        static final int TRANSACTION_onCommandRequest = 9;
        static final int TRANSACTION_onLayoutSurface = 2;
        static final int TRANSACTION_onRemoveBroadcastInfo = 4;
        static final int TRANSACTION_onRequestAvailableSpeeds = 19;
        static final int TRANSACTION_onRequestCertificate = 30;
        static final int TRANSACTION_onRequestCurrentChannelLcn = 14;
        static final int TRANSACTION_onRequestCurrentChannelUri = 13;
        static final int TRANSACTION_onRequestCurrentTvInputId = 17;
        static final int TRANSACTION_onRequestCurrentVideoBounds = 12;
        static final int TRANSACTION_onRequestScheduleRecording = 23;
        static final int TRANSACTION_onRequestScheduleRecording2 = 24;
        static final int TRANSACTION_onRequestSelectedTrackInfo = 20;
        static final int TRANSACTION_onRequestSigning = 28;
        static final int TRANSACTION_onRequestSigning2 = 29;
        static final int TRANSACTION_onRequestStartRecording = 21;
        static final int TRANSACTION_onRequestStopRecording = 22;
        static final int TRANSACTION_onRequestStreamVolume = 15;
        static final int TRANSACTION_onRequestTimeShiftMode = 18;
        static final int TRANSACTION_onRequestTrackInfoList = 16;
        static final int TRANSACTION_onRequestTvRecordingInfo = 26;
        static final int TRANSACTION_onRequestTvRecordingInfoList = 27;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionStateChanged = 5;
        static final int TRANSACTION_onSetTvRecordingInfo = 25;
        static final int TRANSACTION_onSetVideoBounds = 11;
        static final int TRANSACTION_onTeletextAppStateChanged = 7;
        static final int TRANSACTION_onTimeShiftCommandRequest = 10;

        public Stub() {
            attachInterface(this, ITvInteractiveAppSessionCallback.DESCRIPTOR);
        }

        public static ITvInteractiveAppSessionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITvInteractiveAppSessionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ITvInteractiveAppSessionCallback)) {
                return (ITvInteractiveAppSessionCallback) iin;
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
                    return "onBroadcastInfoRequest";
                case 4:
                    return "onRemoveBroadcastInfo";
                case 5:
                    return "onSessionStateChanged";
                case 6:
                    return "onBiInteractiveAppCreated";
                case 7:
                    return "onTeletextAppStateChanged";
                case 8:
                    return "onAdBufferReady";
                case 9:
                    return "onCommandRequest";
                case 10:
                    return "onTimeShiftCommandRequest";
                case 11:
                    return "onSetVideoBounds";
                case 12:
                    return "onRequestCurrentVideoBounds";
                case 13:
                    return "onRequestCurrentChannelUri";
                case 14:
                    return "onRequestCurrentChannelLcn";
                case 15:
                    return "onRequestStreamVolume";
                case 16:
                    return "onRequestTrackInfoList";
                case 17:
                    return "onRequestCurrentTvInputId";
                case 18:
                    return "onRequestTimeShiftMode";
                case 19:
                    return "onRequestAvailableSpeeds";
                case 20:
                    return "onRequestSelectedTrackInfo";
                case 21:
                    return "onRequestStartRecording";
                case 22:
                    return "onRequestStopRecording";
                case 23:
                    return "onRequestScheduleRecording";
                case 24:
                    return "onRequestScheduleRecording2";
                case 25:
                    return "onSetTvRecordingInfo";
                case 26:
                    return "onRequestTvRecordingInfo";
                case 27:
                    return "onRequestTvRecordingInfoList";
                case 28:
                    return "onRequestSigning";
                case 29:
                    return "onRequestSigning2";
                case 30:
                    return "onRequestCertificate";
                case 31:
                    return "onAdRequest";
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
                data.enforceInterface(ITvInteractiveAppSessionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ITvInteractiveAppSession _arg0 = ITvInteractiveAppSession.Stub.asInterface(data.readStrongBinder());
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
                    BroadcastInfoRequest _arg03 = (BroadcastInfoRequest) data.readTypedObject(BroadcastInfoRequest.CREATOR);
                    data.enforceNoDataAvail();
                    onBroadcastInfoRequest(_arg03);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    onRemoveBroadcastInfo(_arg04);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    onSessionStateChanged(_arg05, _arg12);
                    return true;
                case 6:
                    Uri _arg06 = (Uri) data.readTypedObject(Uri.CREATOR);
                    String _arg13 = data.readString();
                    data.enforceNoDataAvail();
                    onBiInteractiveAppCreated(_arg06, _arg13);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    onTeletextAppStateChanged(_arg07);
                    return true;
                case 8:
                    AdBuffer _arg08 = (AdBuffer) data.readTypedObject(AdBuffer.CREATOR);
                    data.enforceNoDataAvail();
                    onAdBufferReady(_arg08);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    Bundle _arg14 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onCommandRequest(_arg09, _arg14);
                    return true;
                case 10:
                    String _arg010 = data.readString();
                    Bundle _arg15 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onTimeShiftCommandRequest(_arg010, _arg15);
                    return true;
                case 11:
                    Rect _arg011 = (Rect) data.readTypedObject(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    onSetVideoBounds(_arg011);
                    return true;
                case 12:
                    onRequestCurrentVideoBounds();
                    return true;
                case 13:
                    onRequestCurrentChannelUri();
                    return true;
                case 14:
                    onRequestCurrentChannelLcn();
                    return true;
                case 15:
                    onRequestStreamVolume();
                    return true;
                case 16:
                    onRequestTrackInfoList();
                    return true;
                case 17:
                    onRequestCurrentTvInputId();
                    return true;
                case 18:
                    onRequestTimeShiftMode();
                    return true;
                case 19:
                    onRequestAvailableSpeeds();
                    return true;
                case 20:
                    onRequestSelectedTrackInfo();
                    return true;
                case 21:
                    String _arg012 = data.readString();
                    Uri _arg16 = (Uri) data.readTypedObject(Uri.CREATOR);
                    data.enforceNoDataAvail();
                    onRequestStartRecording(_arg012, _arg16);
                    return true;
                case 22:
                    String _arg013 = data.readString();
                    data.enforceNoDataAvail();
                    onRequestStopRecording(_arg013);
                    return true;
                case 23:
                    String _arg014 = data.readString();
                    String _arg17 = data.readString();
                    Uri _arg22 = (Uri) data.readTypedObject(Uri.CREATOR);
                    Uri _arg32 = (Uri) data.readTypedObject(Uri.CREATOR);
                    Bundle _arg4 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onRequestScheduleRecording(_arg014, _arg17, _arg22, _arg32, _arg4);
                    return true;
                case 24:
                    String _arg015 = data.readString();
                    String _arg18 = data.readString();
                    Uri _arg23 = (Uri) data.readTypedObject(Uri.CREATOR);
                    long _arg33 = data.readLong();
                    long _arg42 = data.readLong();
                    int _arg5 = data.readInt();
                    Bundle _arg6 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onRequestScheduleRecording2(_arg015, _arg18, _arg23, _arg33, _arg42, _arg5, _arg6);
                    return true;
                case 25:
                    String _arg016 = data.readString();
                    TvRecordingInfo _arg19 = (TvRecordingInfo) data.readTypedObject(TvRecordingInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onSetTvRecordingInfo(_arg016, _arg19);
                    return true;
                case 26:
                    String _arg017 = data.readString();
                    data.enforceNoDataAvail();
                    onRequestTvRecordingInfo(_arg017);
                    return true;
                case 27:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestTvRecordingInfoList(_arg018);
                    return true;
                case 28:
                    String _arg019 = data.readString();
                    String _arg110 = data.readString();
                    String _arg24 = data.readString();
                    byte[] _arg34 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onRequestSigning(_arg019, _arg110, _arg24, _arg34);
                    return true;
                case 29:
                    String _arg020 = data.readString();
                    String _arg111 = data.readString();
                    String _arg25 = data.readString();
                    int _arg35 = data.readInt();
                    byte[] _arg43 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onRequestSigning2(_arg020, _arg111, _arg25, _arg35, _arg43);
                    return true;
                case 30:
                    String _arg021 = data.readString();
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestCertificate(_arg021, _arg112);
                    return true;
                case 31:
                    AdRequest _arg022 = (AdRequest) data.readTypedObject(AdRequest.CREATOR);
                    data.enforceNoDataAvail();
                    onAdRequest(_arg022);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITvInteractiveAppSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvInteractiveAppSessionCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSessionCreated(ITvInteractiveAppSession session) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeStrongInterface(session);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onLayoutSurface(int left, int top, int right, int bottom) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeInt(left);
                    _data.writeInt(top);
                    _data.writeInt(right);
                    _data.writeInt(bottom);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onBroadcastInfoRequest(BroadcastInfoRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRemoveBroadcastInfo(int id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeInt(id);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSessionStateChanged(int state, int err) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeInt(err);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onBiInteractiveAppCreated(Uri biIAppUri, String biIAppId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeTypedObject(biIAppUri, 0);
                    _data.writeString(biIAppId);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onTeletextAppStateChanged(int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeInt(state);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onAdBufferReady(AdBuffer buffer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeTypedObject(buffer, 0);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onCommandRequest(String cmdType, Bundle parameters) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeString(cmdType);
                    _data.writeTypedObject(parameters, 0);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onTimeShiftCommandRequest(String cmdType, Bundle parameters) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeString(cmdType);
                    _data.writeTypedObject(parameters, 0);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSetVideoBounds(Rect rect) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeTypedObject(rect, 0);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentVideoBounds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentChannelUri() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentChannelLcn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestStreamVolume() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTrackInfoList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentTvInputId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTimeShiftMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestAvailableSpeeds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestSelectedTrackInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestStartRecording(String requestId, Uri programUri) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeString(requestId);
                    _data.writeTypedObject(programUri, 0);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestStopRecording(String recordingId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeString(recordingId);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestScheduleRecording(String requestId, String inputId, Uri channelUri, Uri programUri, Bundle params) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeString(requestId);
                    _data.writeString(inputId);
                    _data.writeTypedObject(channelUri, 0);
                    _data.writeTypedObject(programUri, 0);
                    _data.writeTypedObject(params, 0);
                    this.mRemote.transact(23, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestScheduleRecording2(String requestId, String inputId, Uri channelUri, long start, long duration, int repeat, Bundle params) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeString(requestId);
                    _data.writeString(inputId);
                    _data.writeTypedObject(channelUri, 0);
                    _data.writeLong(start);
                    _data.writeLong(duration);
                    _data.writeInt(repeat);
                    _data.writeTypedObject(params, 0);
                    this.mRemote.transact(24, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSetTvRecordingInfo(String recordingId, TvRecordingInfo recordingInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeString(recordingId);
                    _data.writeTypedObject(recordingInfo, 0);
                    this.mRemote.transact(25, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTvRecordingInfo(String recordingId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeString(recordingId);
                    this.mRemote.transact(26, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTvRecordingInfoList(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(27, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestSigning(String id, String algorithm, String alias, byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeString(algorithm);
                    _data.writeString(alias);
                    _data.writeByteArray(data);
                    this.mRemote.transact(28, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestSigning2(String id, String algorithm, String host, int port, byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeString(algorithm);
                    _data.writeString(host);
                    _data.writeInt(port);
                    _data.writeByteArray(data);
                    this.mRemote.transact(29, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCertificate(String host, int port) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeString(host);
                    _data.writeInt(port);
                    this.mRemote.transact(30, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onAdRequest(AdRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    this.mRemote.transact(31, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 30;
        }
    }
}
