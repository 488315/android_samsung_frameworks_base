package android.media.tv;

import android.media.AudioPresentation;
import android.media.tv.ITvInputSession;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface ITvInputSessionCallback extends IInterface {

    public static class Default implements ITvInputSessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAdBufferConsumed(AdBuffer adBuffer) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAdResponse(AdResponse adResponse) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAitInfoUpdated(AitInfo aitInfo) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAudioPresentationSelected(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAudioPresentationsChanged(List<AudioPresentation> list) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAvailableSpeeds(float[] fArr) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onChannelRetuned(Uri uri) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onContentAllowed() throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onContentBlocked(String str) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onCueingMessageAvailability(boolean z) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onError(int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onRecordingStopped(Uri uri) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onSessionCreated(ITvInputSession iTvInputSession, IBinder iBinder) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onSessionEvent(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onSignalStrength(int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTimeShiftCurrentPositionChanged(long j) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTimeShiftMode(int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTimeShiftStartPositionChanged(long j) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTimeShiftStatusChanged(int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTrackSelected(int i, String str) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTracksChanged(List<TvTrackInfo> list) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTuned(Uri uri) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTvInputSessionData(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTvMessage(int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onVideoAvailable() throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onVideoFreezeUpdated(boolean z) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onVideoUnavailable(int i) throws RemoteException {
        }
    }

    void onAdBufferConsumed(AdBuffer adBuffer) throws RemoteException;

    void onAdResponse(AdResponse adResponse) throws RemoteException;

    void onAitInfoUpdated(AitInfo aitInfo) throws RemoteException;

    void onAudioPresentationSelected(int i, int i2) throws RemoteException;

    void onAudioPresentationsChanged(List<AudioPresentation> list) throws RemoteException;

    void onAvailableSpeeds(float[] fArr) throws RemoteException;

    void onBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) throws RemoteException;

    void onChannelRetuned(Uri uri) throws RemoteException;

    void onContentAllowed() throws RemoteException;

    void onContentBlocked(String str) throws RemoteException;

    void onCueingMessageAvailability(boolean z) throws RemoteException;

    void onError(int i) throws RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException;

    void onRecordingStopped(Uri uri) throws RemoteException;

    void onSessionCreated(ITvInputSession iTvInputSession, IBinder iBinder) throws RemoteException;

    void onSessionEvent(String str, Bundle bundle) throws RemoteException;

    void onSignalStrength(int i) throws RemoteException;

    void onTimeShiftCurrentPositionChanged(long j) throws RemoteException;

    void onTimeShiftMode(int i) throws RemoteException;

    void onTimeShiftStartPositionChanged(long j) throws RemoteException;

    void onTimeShiftStatusChanged(int i) throws RemoteException;

    void onTrackSelected(int i, String str) throws RemoteException;

    void onTracksChanged(List<TvTrackInfo> list) throws RemoteException;

    void onTuned(Uri uri) throws RemoteException;

    void onTvInputSessionData(String str, Bundle bundle) throws RemoteException;

    void onTvMessage(int i, Bundle bundle) throws RemoteException;

    void onVideoAvailable() throws RemoteException;

    void onVideoFreezeUpdated(boolean z) throws RemoteException;

    void onVideoUnavailable(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInputSessionCallback {
        public static final String DESCRIPTOR = "android.media.tv.ITvInputSessionCallback";
        static final int TRANSACTION_onAdBufferConsumed = 27;
        static final int TRANSACTION_onAdResponse = 26;
        static final int TRANSACTION_onAitInfoUpdated = 17;
        static final int TRANSACTION_onAudioPresentationSelected = 5;
        static final int TRANSACTION_onAudioPresentationsChanged = 4;
        static final int TRANSACTION_onAvailableSpeeds = 21;
        static final int TRANSACTION_onBroadcastInfoResponse = 25;
        static final int TRANSACTION_onChannelRetuned = 3;
        static final int TRANSACTION_onContentAllowed = 11;
        static final int TRANSACTION_onContentBlocked = 12;
        static final int TRANSACTION_onCueingMessageAvailability = 19;
        static final int TRANSACTION_onError = 24;
        static final int TRANSACTION_onLayoutSurface = 13;
        static final int TRANSACTION_onRecordingStopped = 23;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionEvent = 2;
        static final int TRANSACTION_onSignalStrength = 18;
        static final int TRANSACTION_onTimeShiftCurrentPositionChanged = 16;
        static final int TRANSACTION_onTimeShiftMode = 20;
        static final int TRANSACTION_onTimeShiftStartPositionChanged = 15;
        static final int TRANSACTION_onTimeShiftStatusChanged = 14;
        static final int TRANSACTION_onTrackSelected = 7;
        static final int TRANSACTION_onTracksChanged = 6;
        static final int TRANSACTION_onTuned = 22;
        static final int TRANSACTION_onTvInputSessionData = 29;
        static final int TRANSACTION_onTvMessage = 28;
        static final int TRANSACTION_onVideoAvailable = 8;
        static final int TRANSACTION_onVideoFreezeUpdated = 10;
        static final int TRANSACTION_onVideoUnavailable = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvInputSessionCallback)) {
                return (ITvInputSessionCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSessionCreated";
                case 2:
                    return "onSessionEvent";
                case 3:
                    return "onChannelRetuned";
                case 4:
                    return "onAudioPresentationsChanged";
                case 5:
                    return "onAudioPresentationSelected";
                case 6:
                    return "onTracksChanged";
                case 7:
                    return "onTrackSelected";
                case 8:
                    return "onVideoAvailable";
                case 9:
                    return "onVideoUnavailable";
                case 10:
                    return "onVideoFreezeUpdated";
                case 11:
                    return "onContentAllowed";
                case 12:
                    return "onContentBlocked";
                case 13:
                    return "onLayoutSurface";
                case 14:
                    return "onTimeShiftStatusChanged";
                case 15:
                    return "onTimeShiftStartPositionChanged";
                case 16:
                    return "onTimeShiftCurrentPositionChanged";
                case 17:
                    return "onAitInfoUpdated";
                case 18:
                    return "onSignalStrength";
                case 19:
                    return "onCueingMessageAvailability";
                case 20:
                    return "onTimeShiftMode";
                case 21:
                    return "onAvailableSpeeds";
                case 22:
                    return "onTuned";
                case 23:
                    return "onRecordingStopped";
                case 24:
                    return "onError";
                case 25:
                    return "onBroadcastInfoResponse";
                case 26:
                    return "onAdResponse";
                case 27:
                    return "onAdBufferConsumed";
                case 28:
                    return "onTvMessage";
                case 29:
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
                    ITvInputSession iTvInputSessionAsInterface = ITvInputSession.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onSessionCreated(iTvInputSessionAsInterface, strongBinder);
                    return true;
                case 2:
                    String string = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSessionEvent(string, bundle);
                    return true;
                case 3:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    onChannelRetuned(uri);
                    return true;
                case 4:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AudioPresentation.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAudioPresentationsChanged(arrayListCreateTypedArrayList);
                    return true;
                case 5:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioPresentationSelected(i3, i4);
                    return true;
                case 6:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(TvTrackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTracksChanged(arrayListCreateTypedArrayList2);
                    return true;
                case 7:
                    int i5 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onTrackSelected(i5, string2);
                    return true;
                case 8:
                    onVideoAvailable();
                    return true;
                case 9:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoUnavailable(i6);
                    return true;
                case 10:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onVideoFreezeUpdated(z);
                    return true;
                case 11:
                    onContentAllowed();
                    return true;
                case 12:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onContentBlocked(string3);
                    return true;
                case 13:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(i7, i8, i9, i10);
                    return true;
                case 14:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftStatusChanged(i11);
                    return true;
                case 15:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onTimeShiftStartPositionChanged(j);
                    return true;
                case 16:
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onTimeShiftCurrentPositionChanged(j2);
                    return true;
                case 17:
                    AitInfo aitInfo = (AitInfo) parcel.readTypedObject(AitInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAitInfoUpdated(aitInfo);
                    return true;
                case 18:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSignalStrength(i12);
                    return true;
                case 19:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCueingMessageAvailability(z2);
                    return true;
                case 20:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftMode(i13);
                    return true;
                case 21:
                    float[] fArrCreateFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    onAvailableSpeeds(fArrCreateFloatArray);
                    return true;
                case 22:
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTuned(uri2);
                    return true;
                case 23:
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRecordingStopped(uri3);
                    return true;
                case 24:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(i14);
                    return true;
                case 25:
                    BroadcastInfoResponse broadcastInfoResponse = (BroadcastInfoResponse) parcel.readTypedObject(BroadcastInfoResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBroadcastInfoResponse(broadcastInfoResponse);
                    return true;
                case 26:
                    AdResponse adResponse = (AdResponse) parcel.readTypedObject(AdResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAdResponse(adResponse);
                    return true;
                case 27:
                    AdBuffer adBuffer = (AdBuffer) parcel.readTypedObject(AdBuffer.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAdBufferConsumed(adBuffer);
                    return true;
                case 28:
                    int i15 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTvMessage(i15, bundle2);
                    return true;
                case 29:
                    String string4 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTvInputSessionData(string4, bundle3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvInputSessionCallback {
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

            @Override // android.media.tv.ITvInputSessionCallback
            public void onSessionCreated(ITvInputSession iTvInputSession, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInputSession);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onSessionEvent(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onChannelRetuned(Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAudioPresentationsChanged(List<AudioPresentation> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAudioPresentationSelected(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTracksChanged(List<TvTrackInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTrackSelected(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onVideoAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onVideoUnavailable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onVideoFreezeUpdated(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onContentAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onContentBlocked(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTimeShiftStatusChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTimeShiftStartPositionChanged(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTimeShiftCurrentPositionChanged(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAitInfoUpdated(AitInfo aitInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(aitInfo, 0);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onSignalStrength(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onCueingMessageAvailability(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTimeShiftMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAvailableSpeeds(float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTuned(Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onRecordingStopped(Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onError(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(broadcastInfoResponse, 0);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAdResponse(AdResponse adResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(adResponse, 0);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAdBufferConsumed(AdBuffer adBuffer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(adBuffer, 0);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTvMessage(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTvInputSessionData(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
