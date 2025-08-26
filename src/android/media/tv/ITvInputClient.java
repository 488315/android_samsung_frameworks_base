package android.media.tv;

import android.media.AudioPresentation;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface ITvInputClient extends IInterface {

    public static class Default implements ITvInputClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ITvInputClient
        public void onAdBufferConsumed(AdBuffer adBuffer, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAdResponse(AdResponse adResponse, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAitInfoUpdated(AitInfo aitInfo, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAudioPresentationSelected(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAudioPresentationsChanged(List<AudioPresentation> list, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAvailableSpeeds(float[] fArr, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onChannelRetuned(Uri uri, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onContentAllowed(int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onContentBlocked(String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onCueingMessageAvailability(boolean z, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onError(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onRecordingStopped(Uri uri, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onSessionEvent(String str, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onSessionReleased(int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onSignalStrength(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftCurrentPositionChanged(long j, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftMode(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftStartPositionChanged(long j, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftStatusChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTrackSelected(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTracksChanged(List<TvTrackInfo> list, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTuned(Uri uri, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTvInputSessionData(String str, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTvMessage(int i, Bundle bundle, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onVideoAvailable(int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onVideoFreezeUpdated(boolean z, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onVideoUnavailable(int i, int i2) throws RemoteException {
        }
    }

    void onAdBufferConsumed(AdBuffer adBuffer, int i) throws RemoteException;

    void onAdResponse(AdResponse adResponse, int i) throws RemoteException;

    void onAitInfoUpdated(AitInfo aitInfo, int i) throws RemoteException;

    void onAudioPresentationSelected(int i, int i2, int i3) throws RemoteException;

    void onAudioPresentationsChanged(List<AudioPresentation> list, int i) throws RemoteException;

    void onAvailableSpeeds(float[] fArr, int i) throws RemoteException;

    void onBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse, int i) throws RemoteException;

    void onChannelRetuned(Uri uri, int i) throws RemoteException;

    void onContentAllowed(int i) throws RemoteException;

    void onContentBlocked(String str, int i) throws RemoteException;

    void onCueingMessageAvailability(boolean z, int i) throws RemoteException;

    void onError(int i, int i2) throws RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void onRecordingStopped(Uri uri, int i) throws RemoteException;

    void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException;

    void onSessionEvent(String str, Bundle bundle, int i) throws RemoteException;

    void onSessionReleased(int i) throws RemoteException;

    void onSignalStrength(int i, int i2) throws RemoteException;

    void onTimeShiftCurrentPositionChanged(long j, int i) throws RemoteException;

    void onTimeShiftMode(int i, int i2) throws RemoteException;

    void onTimeShiftStartPositionChanged(long j, int i) throws RemoteException;

    void onTimeShiftStatusChanged(int i, int i2) throws RemoteException;

    void onTrackSelected(int i, String str, int i2) throws RemoteException;

    void onTracksChanged(List<TvTrackInfo> list, int i) throws RemoteException;

    void onTuned(Uri uri, int i) throws RemoteException;

    void onTvInputSessionData(String str, Bundle bundle, int i) throws RemoteException;

    void onTvMessage(int i, Bundle bundle, int i2) throws RemoteException;

    void onVideoAvailable(int i) throws RemoteException;

    void onVideoFreezeUpdated(boolean z, int i) throws RemoteException;

    void onVideoUnavailable(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInputClient {
        public static final String DESCRIPTOR = "android.media.tv.ITvInputClient";
        static final int TRANSACTION_onAdBufferConsumed = 29;
        static final int TRANSACTION_onAdResponse = 28;
        static final int TRANSACTION_onAitInfoUpdated = 18;
        static final int TRANSACTION_onAudioPresentationSelected = 6;
        static final int TRANSACTION_onAudioPresentationsChanged = 5;
        static final int TRANSACTION_onAvailableSpeeds = 22;
        static final int TRANSACTION_onBroadcastInfoResponse = 27;
        static final int TRANSACTION_onChannelRetuned = 4;
        static final int TRANSACTION_onContentAllowed = 12;
        static final int TRANSACTION_onContentBlocked = 13;
        static final int TRANSACTION_onCueingMessageAvailability = 20;
        static final int TRANSACTION_onError = 26;
        static final int TRANSACTION_onLayoutSurface = 14;
        static final int TRANSACTION_onRecordingStopped = 25;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionEvent = 3;
        static final int TRANSACTION_onSessionReleased = 2;
        static final int TRANSACTION_onSignalStrength = 19;
        static final int TRANSACTION_onTimeShiftCurrentPositionChanged = 17;
        static final int TRANSACTION_onTimeShiftMode = 21;
        static final int TRANSACTION_onTimeShiftStartPositionChanged = 16;
        static final int TRANSACTION_onTimeShiftStatusChanged = 15;
        static final int TRANSACTION_onTrackSelected = 8;
        static final int TRANSACTION_onTracksChanged = 7;
        static final int TRANSACTION_onTuned = 24;
        static final int TRANSACTION_onTvInputSessionData = 30;
        static final int TRANSACTION_onTvMessage = 23;
        static final int TRANSACTION_onVideoAvailable = 9;
        static final int TRANSACTION_onVideoFreezeUpdated = 11;
        static final int TRANSACTION_onVideoUnavailable = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 29;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvInputClient)) {
                return (ITvInputClient) iInterfaceQueryLocalInterface;
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
                    return "onSessionEvent";
                case 4:
                    return "onChannelRetuned";
                case 5:
                    return "onAudioPresentationsChanged";
                case 6:
                    return "onAudioPresentationSelected";
                case 7:
                    return "onTracksChanged";
                case 8:
                    return "onTrackSelected";
                case 9:
                    return "onVideoAvailable";
                case 10:
                    return "onVideoUnavailable";
                case 11:
                    return "onVideoFreezeUpdated";
                case 12:
                    return "onContentAllowed";
                case 13:
                    return "onContentBlocked";
                case 14:
                    return "onLayoutSurface";
                case 15:
                    return "onTimeShiftStatusChanged";
                case 16:
                    return "onTimeShiftStartPositionChanged";
                case 17:
                    return "onTimeShiftCurrentPositionChanged";
                case 18:
                    return "onAitInfoUpdated";
                case 19:
                    return "onSignalStrength";
                case 20:
                    return "onCueingMessageAvailability";
                case 21:
                    return "onTimeShiftMode";
                case 22:
                    return "onAvailableSpeeds";
                case 23:
                    return "onTvMessage";
                case 24:
                    return "onTuned";
                case 25:
                    return "onRecordingStopped";
                case 26:
                    return "onError";
                case 27:
                    return "onBroadcastInfoResponse";
                case 28:
                    return "onAdResponse";
                case 29:
                    return "onAdBufferConsumed";
                case 30:
                    return "onTvInputSessionData";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
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
                    String string2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionEvent(string2, bundle, i5);
                    return true;
                case 4:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onChannelRetuned(uri, i6);
                    return true;
                case 5:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AudioPresentation.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioPresentationsChanged(arrayListCreateTypedArrayList, i7);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioPresentationSelected(i8, i9, i10);
                    return true;
                case 7:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(TvTrackInfo.CREATOR);
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTracksChanged(arrayListCreateTypedArrayList2, i11);
                    return true;
                case 8:
                    int i12 = parcel.readInt();
                    String string3 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTrackSelected(i12, string3, i13);
                    return true;
                case 9:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoAvailable(i14);
                    return true;
                case 10:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoUnavailable(i15, i16);
                    return true;
                case 11:
                    boolean z = parcel.readBoolean();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoFreezeUpdated(z, i17);
                    return true;
                case 12:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onContentAllowed(i18);
                    return true;
                case 13:
                    String string4 = parcel.readString();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onContentBlocked(string4, i19);
                    return true;
                case 14:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(i20, i21, i22, i23, i24);
                    return true;
                case 15:
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftStatusChanged(i25, i26);
                    return true;
                case 16:
                    long j = parcel.readLong();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftStartPositionChanged(j, i27);
                    return true;
                case 17:
                    long j2 = parcel.readLong();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftCurrentPositionChanged(j2, i28);
                    return true;
                case 18:
                    AitInfo aitInfo = (AitInfo) parcel.readTypedObject(AitInfo.CREATOR);
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAitInfoUpdated(aitInfo, i29);
                    return true;
                case 19:
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSignalStrength(i30, i31);
                    return true;
                case 20:
                    boolean z2 = parcel.readBoolean();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCueingMessageAvailability(z2, i32);
                    return true;
                case 21:
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftMode(i33, i34);
                    return true;
                case 22:
                    float[] fArrCreateFloatArray = parcel.createFloatArray();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAvailableSpeeds(fArrCreateFloatArray, i35);
                    return true;
                case 23:
                    int i36 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTvMessage(i36, bundle2, i37);
                    return true;
                case 24:
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTuned(uri2, i38);
                    return true;
                case 25:
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecordingStopped(uri3, i39);
                    return true;
                case 26:
                    int i40 = parcel.readInt();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(i40, i41);
                    return true;
                case 27:
                    BroadcastInfoResponse broadcastInfoResponse = (BroadcastInfoResponse) parcel.readTypedObject(BroadcastInfoResponse.CREATOR);
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBroadcastInfoResponse(broadcastInfoResponse, i42);
                    return true;
                case 28:
                    AdResponse adResponse = (AdResponse) parcel.readTypedObject(AdResponse.CREATOR);
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdResponse(adResponse, i43);
                    return true;
                case 29:
                    AdBuffer adBuffer = (AdBuffer) parcel.readTypedObject(AdBuffer.CREATOR);
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdBufferConsumed(adBuffer, i44);
                    return true;
                case 30:
                    String string5 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTvInputSessionData(string5, bundle3, i45);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvInputClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputClient
            public void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(inputChannel, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSessionReleased(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSessionEvent(String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onChannelRetuned(Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAudioPresentationsChanged(List<AudioPresentation> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAudioPresentationSelected(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTracksChanged(List<TvTrackInfo> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTrackSelected(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onVideoAvailable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onVideoUnavailable(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onVideoFreezeUpdated(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onContentAllowed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onContentBlocked(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftStatusChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftStartPositionChanged(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftCurrentPositionChanged(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAitInfoUpdated(AitInfo aitInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(aitInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSignalStrength(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onCueingMessageAvailability(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAvailableSpeeds(float[] fArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloatArray(fArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTvMessage(int i, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTuned(Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onRecordingStopped(Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onError(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(broadcastInfoResponse, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAdResponse(AdResponse adResponse, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(adResponse, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAdBufferConsumed(AdBuffer adBuffer, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(adBuffer, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTvInputSessionData(String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
