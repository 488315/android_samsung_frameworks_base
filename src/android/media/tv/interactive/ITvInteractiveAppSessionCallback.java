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

    public static class Default implements ITvInteractiveAppSessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onAdBufferReady(AdBuffer adBuffer) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onAdRequest(AdRequest adRequest) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onBiInteractiveAppCreated(Uri uri, String str) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onBroadcastInfoRequest(BroadcastInfoRequest broadcastInfoRequest) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onCommandRequest(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRemoveBroadcastInfo(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestAvailableSpeeds() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCertificate(String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentChannelLcn() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentChannelUri() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentTvInputId() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentVideoBounds() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestScheduleRecording(String str, String str2, Uri uri, Uri uri2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestScheduleRecording2(String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestSelectedTrackInfo() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestSigning(String str, String str2, String str3, byte[] bArr) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestSigning2(String str, String str2, String str3, int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestStartRecording(String str, Uri uri) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestStopRecording(String str) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestStreamVolume() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTimeShiftMode() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTrackInfoList() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTvRecordingInfo(String str) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTvRecordingInfoList(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSessionCreated(ITvInteractiveAppSession iTvInteractiveAppSession) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSessionStateChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSetTvRecordingInfo(String str, TvRecordingInfo tvRecordingInfo) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSetVideoBounds(Rect rect) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onTeletextAppStateChanged(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onTimeShiftCommandRequest(String str, Bundle bundle) throws RemoteException {
        }
    }

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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 30;
        }

        public Stub() {
            attachInterface(this, ITvInteractiveAppSessionCallback.DESCRIPTOR);
        }

        public static ITvInteractiveAppSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITvInteractiveAppSessionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvInteractiveAppSessionCallback)) {
                return (ITvInteractiveAppSessionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITvInteractiveAppSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ITvInteractiveAppSession asInterface = ITvInteractiveAppSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSessionCreated(asInterface);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(readInt, readInt2, readInt3, readInt4);
                    return true;
                case 3:
                    BroadcastInfoRequest broadcastInfoRequest = (BroadcastInfoRequest) parcel.readTypedObject(BroadcastInfoRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBroadcastInfoRequest(broadcastInfoRequest);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoveBroadcastInfo(readInt5);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionStateChanged(readInt6, readInt7);
                    return true;
                case 6:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiInteractiveAppCreated(uri, readString);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTeletextAppStateChanged(readInt8);
                    return true;
                case 8:
                    AdBuffer adBuffer = (AdBuffer) parcel.readTypedObject(AdBuffer.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAdBufferReady(adBuffer);
                    return true;
                case 9:
                    String readString2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCommandRequest(readString2, bundle);
                    return true;
                case 10:
                    String readString3 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTimeShiftCommandRequest(readString3, bundle2);
                    return true;
                case 11:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetVideoBounds(rect);
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
                    String readString4 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRequestStartRecording(readString4, uri2);
                    return true;
                case 22:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onRequestStopRecording(readString5);
                    return true;
                case 23:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRequestScheduleRecording(readString6, readString7, uri3, uri4, bundle3);
                    return true;
                case 24:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    Uri uri5 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    int readInt9 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRequestScheduleRecording2(readString8, readString9, uri5, readLong, readLong2, readInt9, bundle4);
                    return true;
                case 25:
                    String readString10 = parcel.readString();
                    TvRecordingInfo tvRecordingInfo = (TvRecordingInfo) parcel.readTypedObject(TvRecordingInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetTvRecordingInfo(readString10, tvRecordingInfo);
                    return true;
                case 26:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onRequestTvRecordingInfo(readString11);
                    return true;
                case 27:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTvRecordingInfoList(readInt10);
                    return true;
                case 28:
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onRequestSigning(readString12, readString13, readString14, createByteArray);
                    return true;
                case 29:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onRequestSigning2(readString15, readString16, readString17, readInt11, createByteArray2);
                    return true;
                case 30:
                    String readString18 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCertificate(readString18, readInt12);
                    return true;
                case 31:
                    AdRequest adRequest = (AdRequest) parcel.readTypedObject(AdRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAdRequest(adRequest);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvInteractiveAppSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvInteractiveAppSessionCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSessionCreated(ITvInteractiveAppSession iTvInteractiveAppSession) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInteractiveAppSession);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onBroadcastInfoRequest(BroadcastInfoRequest broadcastInfoRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(broadcastInfoRequest, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRemoveBroadcastInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSessionStateChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onBiInteractiveAppCreated(Uri uri, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onTeletextAppStateChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onAdBufferReady(AdBuffer adBuffer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(adBuffer, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onCommandRequest(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onTimeShiftCommandRequest(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSetVideoBounds(Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentVideoBounds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentChannelUri() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentChannelLcn() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestStreamVolume() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTrackInfoList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentTvInputId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTimeShiftMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestAvailableSpeeds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestSelectedTrackInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestStartRecording(String str, Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestStopRecording(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestScheduleRecording(String str, String str2, Uri uri, Uri uri2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(uri2, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestScheduleRecording2(String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSetTvRecordingInfo(String str, TvRecordingInfo tvRecordingInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(tvRecordingInfo, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTvRecordingInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTvRecordingInfoList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestSigning(String str, String str2, String str3, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestSigning2(String str, String str2, String str3, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCertificate(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onAdRequest(AdRequest adRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(adRequest, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
