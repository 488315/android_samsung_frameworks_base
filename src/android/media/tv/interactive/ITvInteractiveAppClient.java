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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITvInteractiveAppClient.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvInteractiveAppClient)) {
                return (ITvInteractiveAppClient) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    IBinder strongBinder = parcel.readStrongBinder();
                    InputChannel inputChannel = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionCreated(string, strongBinder, inputChannel, i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionReleased(i4);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(i5, i6, i7, i8, i9);
                    return true;
                case 4:
                    BroadcastInfoRequest broadcastInfoRequest = (BroadcastInfoRequest) parcel.readTypedObject(BroadcastInfoRequest.CREATOR);
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBroadcastInfoRequest(broadcastInfoRequest, i10);
                    return true;
                case 5:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoveBroadcastInfo(i11, i12);
                    return true;
                case 6:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionStateChanged(i13, i14, i15);
                    return true;
                case 7:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String string2 = parcel.readString();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiInteractiveAppCreated(uri, string2, i16);
                    return true;
                case 8:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTeletextAppStateChanged(i17, i18);
                    return true;
                case 9:
                    AdBuffer adBuffer = (AdBuffer) parcel.readTypedObject(AdBuffer.CREATOR);
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdBufferReady(adBuffer, i19);
                    return true;
                case 10:
                    String string3 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCommandRequest(string3, bundle, i20);
                    return true;
                case 11:
                    String string4 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftCommandRequest(string4, bundle2, i21);
                    return true;
                case 12:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetVideoBounds(rect, i22);
                    return true;
                case 13:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentVideoBounds(i23);
                    return true;
                case 14:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentChannelUri(i24);
                    return true;
                case 15:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentChannelLcn(i25);
                    return true;
                case 16:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestStreamVolume(i26);
                    return true;
                case 17:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTrackInfoList(i27);
                    return true;
                case 18:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSelectedTrackInfo(i28);
                    return true;
                case 19:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentTvInputId(i29);
                    return true;
                case 20:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTimeShiftMode(i30);
                    return true;
                case 21:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestAvailableSpeeds(i31);
                    return true;
                case 22:
                    String string5 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestStartRecording(string5, uri2, i32);
                    return true;
                case 23:
                    String string6 = parcel.readString();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestStopRecording(string6, i33);
                    return true;
                case 24:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestScheduleRecording(string7, string8, uri3, uri4, bundle3, i34);
                    return true;
                case 25:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    Uri uri5 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    int i35 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestScheduleRecording2(string9, string10, uri5, j, j2, i35, bundle4, i36);
                    return true;
                case 26:
                    String string11 = parcel.readString();
                    TvRecordingInfo tvRecordingInfo = (TvRecordingInfo) parcel.readTypedObject(TvRecordingInfo.CREATOR);
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetTvRecordingInfo(string11, tvRecordingInfo, i37);
                    return true;
                case 27:
                    String string12 = parcel.readString();
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTvRecordingInfo(string12, i38);
                    return true;
                case 28:
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTvRecordingInfoList(i39, i40);
                    return true;
                case 29:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSigning(string13, string14, string15, bArrCreateByteArray, i41);
                    return true;
                case 30:
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    int i42 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSigning2(string16, string17, string18, i42, bArrCreateByteArray2, i43);
                    return true;
                case 31:
                    String string19 = parcel.readString();
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCertificate(string19, i44, i45);
                    return true;
                case 32:
                    AdRequest adRequest = (AdRequest) parcel.readTypedObject(AdRequest.CREATOR);
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdRequest(adRequest, i46);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(inputChannel, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSessionReleased(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onBroadcastInfoRequest(BroadcastInfoRequest broadcastInfoRequest, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeTypedObject(broadcastInfoRequest, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRemoveBroadcastInfo(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSessionStateChanged(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onBiInteractiveAppCreated(Uri uri, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onTeletextAppStateChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onAdBufferReady(AdBuffer adBuffer, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeTypedObject(adBuffer, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onCommandRequest(String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onTimeShiftCommandRequest(String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSetVideoBounds(Rect rect, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentVideoBounds(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentChannelUri(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentChannelLcn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStreamVolume(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTrackInfoList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSelectedTrackInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentTvInputId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTimeShiftMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestAvailableSpeeds(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStartRecording(String str, Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStopRecording(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestScheduleRecording(String str, String str2, Uri uri, Uri uri2, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(uri2, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestScheduleRecording2(String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSetTvRecordingInfo(String str, TvRecordingInfo tvRecordingInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(tvRecordingInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTvRecordingInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTvRecordingInfoList(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSigning2(String str, String str2, String str3, int i, byte[] bArr, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(30, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCertificate(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(31, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onAdRequest(AdRequest adRequest, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    parcelObtain.writeTypedObject(adRequest, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
