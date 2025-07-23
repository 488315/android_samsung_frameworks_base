package android.media.tv.interactive;

import android.graphics.Rect;
import android.media.tv.AdBuffer;
import android.media.tv.AdRequest;
import android.media.tv.BroadcastInfoRequest;
import android.media.tv.TvRecordingInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;

/* loaded from: classes3.dex */
public interface ITvInteractiveAppClient extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppClient";

    public static class Default implements ITvInteractiveAppClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdBufferReady(AdBuffer adBuffer, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdRequest(AdRequest adRequest, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBiInteractiveAppCreated(Uri uri, String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBroadcastInfoRequest(BroadcastInfoRequest broadcastInfoRequest, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onCommandRequest(String str, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRemoveBroadcastInfo(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestAvailableSpeeds(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCertificate(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelLcn(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelUri(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentTvInputId(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentVideoBounds(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording(String str, String str2, Uri uri, Uri uri2, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording2(String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSelectedTrackInfo(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning2(String str, String str2, String str3, int i, byte[] bArr, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStartRecording(String str, Uri uri, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStopRecording(String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStreamVolume(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTimeShiftMode(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTrackInfoList(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfo(String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfoList(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionReleased(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionStateChanged(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetTvRecordingInfo(String str, TvRecordingInfo tvRecordingInfo, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetVideoBounds(Rect rect, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTeletextAppStateChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTimeShiftCommandRequest(String str, Bundle bundle, int i) throws RemoteException {
        }
    }

    void onAdBufferReady(AdBuffer adBuffer, int i) throws RemoteException;

    void onAdRequest(AdRequest adRequest, int i) throws RemoteException;

    void onBiInteractiveAppCreated(Uri uri, String str, int i) throws RemoteException;

    void onBroadcastInfoRequest(BroadcastInfoRequest broadcastInfoRequest, int i) throws RemoteException;

    void onCommandRequest(String str, Bundle bundle, int i) throws RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void onRemoveBroadcastInfo(int i, int i2) throws RemoteException;

    void onRequestAvailableSpeeds(int i) throws RemoteException;

    void onRequestCertificate(String str, int i, int i2) throws RemoteException;

    void onRequestCurrentChannelLcn(int i) throws RemoteException;

    void onRequestCurrentChannelUri(int i) throws RemoteException;

    void onRequestCurrentTvInputId(int i) throws RemoteException;

    void onRequestCurrentVideoBounds(int i) throws RemoteException;

    void onRequestScheduleRecording(String str, String str2, Uri uri, Uri uri2, Bundle bundle, int i) throws RemoteException;

    void onRequestScheduleRecording2(String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle, int i2) throws RemoteException;

    void onRequestSelectedTrackInfo(int i) throws RemoteException;

    void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) throws RemoteException;

    void onRequestSigning2(String str, String str2, String str3, int i, byte[] bArr, int i2) throws RemoteException;

    void onRequestStartRecording(String str, Uri uri, int i) throws RemoteException;

    void onRequestStopRecording(String str, int i) throws RemoteException;

    void onRequestStreamVolume(int i) throws RemoteException;

    void onRequestTimeShiftMode(int i) throws RemoteException;

    void onRequestTrackInfoList(int i) throws RemoteException;

    void onRequestTvRecordingInfo(String str, int i) throws RemoteException;

    void onRequestTvRecordingInfoList(int i, int i2) throws RemoteException;

    void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException;

    void onSessionReleased(int i) throws RemoteException;

    void onSessionStateChanged(int i, int i2, int i3) throws RemoteException;

    void onSetTvRecordingInfo(String str, TvRecordingInfo tvRecordingInfo, int i) throws RemoteException;

    void onSetVideoBounds(Rect rect, int i) throws RemoteException;

    void onTeletextAppStateChanged(int i, int i2) throws RemoteException;

    void onTimeShiftCommandRequest(String str, Bundle bundle, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInteractiveAppClient {
        static final int TRANSACTION_onAdBufferReady = 9;
        static final int TRANSACTION_onAdRequest = 32;
        static final int TRANSACTION_onBiInteractiveAppCreated = 7;
        static final int TRANSACTION_onBroadcastInfoRequest = 4;
        static final int TRANSACTION_onCommandRequest = 10;
        static final int TRANSACTION_onLayoutSurface = 3;
        static final int TRANSACTION_onRemoveBroadcastInfo = 5;
        static final int TRANSACTION_onRequestAvailableSpeeds = 21;
        static final int TRANSACTION_onRequestCertificate = 31;
        static final int TRANSACTION_onRequestCurrentChannelLcn = 15;
        static final int TRANSACTION_onRequestCurrentChannelUri = 14;
        static final int TRANSACTION_onRequestCurrentTvInputId = 19;
        static final int TRANSACTION_onRequestCurrentVideoBounds = 13;
        static final int TRANSACTION_onRequestScheduleRecording = 24;
        static final int TRANSACTION_onRequestScheduleRecording2 = 25;
        static final int TRANSACTION_onRequestSelectedTrackInfo = 18;
        static final int TRANSACTION_onRequestSigning = 29;
        static final int TRANSACTION_onRequestSigning2 = 30;
        static final int TRANSACTION_onRequestStartRecording = 22;
        static final int TRANSACTION_onRequestStopRecording = 23;
        static final int TRANSACTION_onRequestStreamVolume = 16;
        static final int TRANSACTION_onRequestTimeShiftMode = 20;
        static final int TRANSACTION_onRequestTrackInfoList = 17;
        static final int TRANSACTION_onRequestTvRecordingInfo = 27;
        static final int TRANSACTION_onRequestTvRecordingInfoList = 28;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionReleased = 2;
        static final int TRANSACTION_onSessionStateChanged = 6;
        static final int TRANSACTION_onSetTvRecordingInfo = 26;
        static final int TRANSACTION_onSetVideoBounds = 12;
        static final int TRANSACTION_onTeletextAppStateChanged = 8;
        static final int TRANSACTION_onTimeShiftCommandRequest = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 31;
        }

        public Stub() {
            attachInterface(this, ITvInteractiveAppClient.DESCRIPTOR);
        }

        public static ITvInteractiveAppClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITvInteractiveAppClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvInteractiveAppClient)) {
                return (ITvInteractiveAppClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSessionCreated";
                case 2:
                    return "onSessionReleased";
                case 3:
                    return "onLayoutSurface";
                case 4:
                    return "onBroadcastInfoRequest";
                case 5:
                    return "onRemoveBroadcastInfo";
                case 6:
                    return "onSessionStateChanged";
                case 7:
                    return "onBiInteractiveAppCreated";
                case 8:
                    return "onTeletextAppStateChanged";
                case 9:
                    return "onAdBufferReady";
                case 10:
                    return "onCommandRequest";
                case 11:
                    return "onTimeShiftCommandRequest";
                case 12:
                    return "onSetVideoBounds";
                case 13:
                    return "onRequestCurrentVideoBounds";
                case 14:
                    return "onRequestCurrentChannelUri";
                case 15:
                    return "onRequestCurrentChannelLcn";
                case 16:
                    return "onRequestStreamVolume";
                case 17:
                    return "onRequestTrackInfoList";
                case 18:
                    return "onRequestSelectedTrackInfo";
                case 19:
                    return "onRequestCurrentTvInputId";
                case 20:
                    return "onRequestTimeShiftMode";
                case 21:
                    return "onRequestAvailableSpeeds";
                case 22:
                    return "onRequestStartRecording";
                case 23:
                    return "onRequestStopRecording";
                case 24:
                    return "onRequestScheduleRecording";
                case 25:
                    return "onRequestScheduleRecording2";
                case 26:
                    return "onSetTvRecordingInfo";
                case 27:
                    return "onRequestTvRecordingInfo";
                case 28:
                    return "onRequestTvRecordingInfoList";
                case 29:
                    return "onRequestSigning";
                case 30:
                    return "onRequestSigning2";
                case 31:
                    return "onRequestCertificate";
                case 32:
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
                parcel.enforceInterface(ITvInteractiveAppClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvInteractiveAppClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    InputChannel inputChannel = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionCreated(readString, readStrongBinder, inputChannel, readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionReleased(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(readInt3, readInt4, readInt5, readInt6, readInt7);
                    return true;
                case 4:
                    BroadcastInfoRequest broadcastInfoRequest = (BroadcastInfoRequest) parcel.readTypedObject(BroadcastInfoRequest.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBroadcastInfoRequest(broadcastInfoRequest, readInt8);
                    return true;
                case 5:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoveBroadcastInfo(readInt9, readInt10);
                    return true;
                case 6:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionStateChanged(readInt11, readInt12, readInt13);
                    return true;
                case 7:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString2 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiInteractiveAppCreated(uri, readString2, readInt14);
                    return true;
                case 8:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTeletextAppStateChanged(readInt15, readInt16);
                    return true;
                case 9:
                    AdBuffer adBuffer = (AdBuffer) parcel.readTypedObject(AdBuffer.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdBufferReady(adBuffer, readInt17);
                    return true;
                case 10:
                    String readString3 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCommandRequest(readString3, bundle, readInt18);
                    return true;
                case 11:
                    String readString4 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftCommandRequest(readString4, bundle2, readInt19);
                    return true;
                case 12:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetVideoBounds(rect, readInt20);
                    return true;
                case 13:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentVideoBounds(readInt21);
                    return true;
                case 14:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentChannelUri(readInt22);
                    return true;
                case 15:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentChannelLcn(readInt23);
                    return true;
                case 16:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestStreamVolume(readInt24);
                    return true;
                case 17:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTrackInfoList(readInt25);
                    return true;
                case 18:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSelectedTrackInfo(readInt26);
                    return true;
                case 19:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentTvInputId(readInt27);
                    return true;
                case 20:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTimeShiftMode(readInt28);
                    return true;
                case 21:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestAvailableSpeeds(readInt29);
                    return true;
                case 22:
                    String readString5 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestStartRecording(readString5, uri2, readInt30);
                    return true;
                case 23:
                    String readString6 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestStopRecording(readString6, readInt31);
                    return true;
                case 24:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestScheduleRecording(readString7, readString8, uri3, uri4, bundle3, readInt32);
                    return true;
                case 25:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    Uri uri5 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    int readInt33 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestScheduleRecording2(readString9, readString10, uri5, readLong, readLong2, readInt33, bundle4, readInt34);
                    return true;
                case 26:
                    String readString11 = parcel.readString();
                    TvRecordingInfo tvRecordingInfo = (TvRecordingInfo) parcel.readTypedObject(TvRecordingInfo.CREATOR);
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetTvRecordingInfo(readString11, tvRecordingInfo, readInt35);
                    return true;
                case 27:
                    String readString12 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTvRecordingInfo(readString12, readInt36);
                    return true;
                case 28:
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTvRecordingInfoList(readInt37, readInt38);
                    return true;
                case 29:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSigning(readString13, readString14, readString15, createByteArray, readInt39);
                    return true;
                case 30:
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSigning2(readString16, readString17, readString18, readInt40, createByteArray2, readInt41);
                    return true;
                case 31:
                    String readString19 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCertificate(readString19, readInt42, readInt43);
                    return true;
                case 32:
                    AdRequest adRequest = (AdRequest) parcel.readTypedObject(AdRequest.CREATOR);
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdRequest(adRequest, readInt44);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvInteractiveAppClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvInteractiveAppClient.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSessionReleased(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onBroadcastInfoRequest(BroadcastInfoRequest broadcastInfoRequest, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeTypedObject(broadcastInfoRequest, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRemoveBroadcastInfo(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSessionStateChanged(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onBiInteractiveAppCreated(Uri uri, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onTeletextAppStateChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onAdBufferReady(AdBuffer adBuffer, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeTypedObject(adBuffer, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onCommandRequest(String str, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onTimeShiftCommandRequest(String str, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSetVideoBounds(Rect rect, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentVideoBounds(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentChannelUri(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentChannelLcn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStreamVolume(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTrackInfoList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSelectedTrackInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentTvInputId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTimeShiftMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestAvailableSpeeds(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStartRecording(String str, Uri uri, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStopRecording(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestScheduleRecording(String str, String str2, Uri uri, Uri uri2, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(uri2, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestScheduleRecording2(String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSetTvRecordingInfo(String str, TvRecordingInfo tvRecordingInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(tvRecordingInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTvRecordingInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTvRecordingInfoList(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSigning2(String str, String str2, String str3, int i, byte[] bArr, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCertificate(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onAdRequest(AdRequest adRequest, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeTypedObject(adRequest, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
