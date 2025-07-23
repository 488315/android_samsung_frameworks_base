package android.media.tv;

import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.interactive.TvInteractiveAppService;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;

/* loaded from: classes3.dex */
public interface ITvInputSession extends IInterface {

    public static class Default implements ITvInputSession {
        @Override // android.media.tv.ITvInputSession
        public void appPrivateCommand(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ITvInputSession
        public void createOverlayView(IBinder iBinder, Rect rect) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void dispatchSurfaceChanged(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void notifyAdBufferReady(AdBuffer adBuffer) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void notifyTvAdSessionData(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void notifyTvMessage(int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void pauseRecording(Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void relayoutOverlayView(Rect rect) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void release() throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void removeBroadcastInfo(int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void removeOverlayView() throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void requestAd(AdRequest adRequest) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void requestBroadcastInfo(BroadcastInfoRequest broadcastInfoRequest) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void resumePlayback() throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void resumeRecording(Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void selectAudioPresentation(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void selectTrack(int i, String str) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setCaptionEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setInteractiveAppNotificationEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setMain(boolean z) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setSurface(Surface surface) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setTvMessageEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setVideoFrozen(boolean z) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setVolume(float f) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void startRecording(Uri uri, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void stopPlayback(int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void stopRecording() throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftEnablePositionTracking(boolean z) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftPause() throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftPlay(Uri uri) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftResume() throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftSeekTo(long j) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftSetMode(int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftSetPlaybackParams(PlaybackParams playbackParams) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void tune(Uri uri, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void unblockContent(String str) throws RemoteException {
        }
    }

    void appPrivateCommand(String str, Bundle bundle) throws RemoteException;

    void createOverlayView(IBinder iBinder, Rect rect) throws RemoteException;

    void dispatchSurfaceChanged(int i, int i2, int i3) throws RemoteException;

    void notifyAdBufferReady(AdBuffer adBuffer) throws RemoteException;

    void notifyTvAdSessionData(String str, Bundle bundle) throws RemoteException;

    void notifyTvMessage(int i, Bundle bundle) throws RemoteException;

    void pauseRecording(Bundle bundle) throws RemoteException;

    void relayoutOverlayView(Rect rect) throws RemoteException;

    void release() throws RemoteException;

    void removeBroadcastInfo(int i) throws RemoteException;

    void removeOverlayView() throws RemoteException;

    void requestAd(AdRequest adRequest) throws RemoteException;

    void requestBroadcastInfo(BroadcastInfoRequest broadcastInfoRequest) throws RemoteException;

    void resumePlayback() throws RemoteException;

    void resumeRecording(Bundle bundle) throws RemoteException;

    void selectAudioPresentation(int i, int i2) throws RemoteException;

    void selectTrack(int i, String str) throws RemoteException;

    void setCaptionEnabled(boolean z) throws RemoteException;

    void setInteractiveAppNotificationEnabled(boolean z) throws RemoteException;

    void setMain(boolean z) throws RemoteException;

    void setSurface(Surface surface) throws RemoteException;

    void setTvMessageEnabled(int i, boolean z) throws RemoteException;

    void setVideoFrozen(boolean z) throws RemoteException;

    void setVolume(float f) throws RemoteException;

    void startRecording(Uri uri, Bundle bundle) throws RemoteException;

    void stopPlayback(int i) throws RemoteException;

    void stopRecording() throws RemoteException;

    void timeShiftEnablePositionTracking(boolean z) throws RemoteException;

    void timeShiftPause() throws RemoteException;

    void timeShiftPlay(Uri uri) throws RemoteException;

    void timeShiftResume() throws RemoteException;

    void timeShiftSeekTo(long j) throws RemoteException;

    void timeShiftSetMode(int i) throws RemoteException;

    void timeShiftSetPlaybackParams(PlaybackParams playbackParams) throws RemoteException;

    void tune(Uri uri, Bundle bundle) throws RemoteException;

    void unblockContent(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInputSession {
        public static final String DESCRIPTOR = "android.media.tv.ITvInputSession";
        static final int TRANSACTION_appPrivateCommand = 11;
        static final int TRANSACTION_createOverlayView = 12;
        static final int TRANSACTION_dispatchSurfaceChanged = 4;
        static final int TRANSACTION_notifyAdBufferReady = 32;
        static final int TRANSACTION_notifyTvAdSessionData = 36;
        static final int TRANSACTION_notifyTvMessage = 33;
        static final int TRANSACTION_pauseRecording = 27;
        static final int TRANSACTION_relayoutOverlayView = 13;
        static final int TRANSACTION_release = 1;
        static final int TRANSACTION_removeBroadcastInfo = 30;
        static final int TRANSACTION_removeOverlayView = 14;
        static final int TRANSACTION_requestAd = 31;
        static final int TRANSACTION_requestBroadcastInfo = 29;
        static final int TRANSACTION_resumePlayback = 23;
        static final int TRANSACTION_resumeRecording = 28;
        static final int TRANSACTION_selectAudioPresentation = 8;
        static final int TRANSACTION_selectTrack = 9;
        static final int TRANSACTION_setCaptionEnabled = 7;
        static final int TRANSACTION_setInteractiveAppNotificationEnabled = 10;
        static final int TRANSACTION_setMain = 2;
        static final int TRANSACTION_setSurface = 3;
        static final int TRANSACTION_setTvMessageEnabled = 34;
        static final int TRANSACTION_setVideoFrozen = 35;
        static final int TRANSACTION_setVolume = 5;
        static final int TRANSACTION_startRecording = 25;
        static final int TRANSACTION_stopPlayback = 24;
        static final int TRANSACTION_stopRecording = 26;
        static final int TRANSACTION_timeShiftEnablePositionTracking = 22;
        static final int TRANSACTION_timeShiftPause = 17;
        static final int TRANSACTION_timeShiftPlay = 16;
        static final int TRANSACTION_timeShiftResume = 18;
        static final int TRANSACTION_timeShiftSeekTo = 19;
        static final int TRANSACTION_timeShiftSetMode = 21;
        static final int TRANSACTION_timeShiftSetPlaybackParams = 20;
        static final int TRANSACTION_tune = 6;
        static final int TRANSACTION_unblockContent = 15;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 35;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvInputSession)) {
                return (ITvInputSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "release";
                case 2:
                    return "setMain";
                case 3:
                    return "setSurface";
                case 4:
                    return "dispatchSurfaceChanged";
                case 5:
                    return "setVolume";
                case 6:
                    return TvInteractiveAppService.PLAYBACK_COMMAND_TYPE_TUNE;
                case 7:
                    return "setCaptionEnabled";
                case 8:
                    return "selectAudioPresentation";
                case 9:
                    return "selectTrack";
                case 10:
                    return "setInteractiveAppNotificationEnabled";
                case 11:
                    return "appPrivateCommand";
                case 12:
                    return "createOverlayView";
                case 13:
                    return "relayoutOverlayView";
                case 14:
                    return "removeOverlayView";
                case 15:
                    return "unblockContent";
                case 16:
                    return "timeShiftPlay";
                case 17:
                    return "timeShiftPause";
                case 18:
                    return "timeShiftResume";
                case 19:
                    return "timeShiftSeekTo";
                case 20:
                    return "timeShiftSetPlaybackParams";
                case 21:
                    return "timeShiftSetMode";
                case 22:
                    return "timeShiftEnablePositionTracking";
                case 23:
                    return "resumePlayback";
                case 24:
                    return "stopPlayback";
                case 25:
                    return "startRecording";
                case 26:
                    return "stopRecording";
                case 27:
                    return "pauseRecording";
                case 28:
                    return "resumeRecording";
                case 29:
                    return "requestBroadcastInfo";
                case 30:
                    return "removeBroadcastInfo";
                case 31:
                    return "requestAd";
                case 32:
                    return "notifyAdBufferReady";
                case 33:
                    return "notifyTvMessage";
                case 34:
                    return "setTvMessageEnabled";
                case 35:
                    return "setVideoFrozen";
                case 36:
                    return "notifyTvAdSessionData";
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
                    release();
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMain(readBoolean);
                    return true;
                case 3:
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSurface(surface);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(readInt, readInt2, readInt3);
                    return true;
                case 5:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setVolume(readFloat);
                    return true;
                case 6:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    tune(uri, bundle);
                    return true;
                case 7:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCaptionEnabled(readBoolean2);
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    selectAudioPresentation(readInt4, readInt5);
                    return true;
                case 9:
                    int readInt6 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    selectTrack(readInt6, readString);
                    return true;
                case 10:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInteractiveAppNotificationEnabled(readBoolean3);
                    return true;
                case 11:
                    String readString2 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    appPrivateCommand(readString2, bundle2);
                    return true;
                case 12:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    createOverlayView(readStrongBinder, rect);
                    return true;
                case 13:
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    relayoutOverlayView(rect2);
                    return true;
                case 14:
                    removeOverlayView();
                    return true;
                case 15:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unblockContent(readString3);
                    return true;
                case 16:
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    timeShiftPlay(uri2);
                    return true;
                case 17:
                    timeShiftPause();
                    return true;
                case 18:
                    timeShiftResume();
                    return true;
                case 19:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    timeShiftSeekTo(readLong);
                    return true;
                case 20:
                    PlaybackParams playbackParams = (PlaybackParams) parcel.readTypedObject(PlaybackParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    timeShiftSetPlaybackParams(playbackParams);
                    return true;
                case 21:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftSetMode(readInt7);
                    return true;
                case 22:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    timeShiftEnablePositionTracking(readBoolean4);
                    return true;
                case 23:
                    resumePlayback();
                    return true;
                case 24:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopPlayback(readInt8);
                    return true;
                case 25:
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startRecording(uri3, bundle3);
                    return true;
                case 26:
                    stopRecording();
                    return true;
                case 27:
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    pauseRecording(bundle4);
                    return true;
                case 28:
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    resumeRecording(bundle5);
                    return true;
                case 29:
                    BroadcastInfoRequest broadcastInfoRequest = (BroadcastInfoRequest) parcel.readTypedObject(BroadcastInfoRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestBroadcastInfo(broadcastInfoRequest);
                    return true;
                case 30:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeBroadcastInfo(readInt9);
                    return true;
                case 31:
                    AdRequest adRequest = (AdRequest) parcel.readTypedObject(AdRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestAd(adRequest);
                    return true;
                case 32:
                    AdBuffer adBuffer = (AdBuffer) parcel.readTypedObject(AdBuffer.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyAdBufferReady(adBuffer);
                    return true;
                case 33:
                    int readInt10 = parcel.readInt();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(readInt10, bundle6);
                    return true;
                case 34:
                    int readInt11 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTvMessageEnabled(readInt11, readBoolean5);
                    return true;
                case 35:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVideoFrozen(readBoolean6);
                    return true;
                case 36:
                    String readString4 = parcel.readString();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTvAdSessionData(readString4, bundle7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvInputSession {
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

            @Override // android.media.tv.ITvInputSession
            public void release() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setMain(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setSurface(Surface surface) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void dispatchSurfaceChanged(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setVolume(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void tune(Uri uri, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setCaptionEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void selectAudioPresentation(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void selectTrack(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setInteractiveAppNotificationEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void appPrivateCommand(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void createOverlayView(IBinder iBinder, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void relayoutOverlayView(Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void removeOverlayView() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void unblockContent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftPlay(Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftPause() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftResume() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftSeekTo(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftSetPlaybackParams(PlaybackParams playbackParams) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(playbackParams, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftSetMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftEnablePositionTracking(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void resumePlayback() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void stopPlayback(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void startRecording(Uri uri, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void stopRecording() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void pauseRecording(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void resumeRecording(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void requestBroadcastInfo(BroadcastInfoRequest broadcastInfoRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(broadcastInfoRequest, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void removeBroadcastInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void requestAd(AdRequest adRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(adRequest, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void notifyAdBufferReady(AdBuffer adBuffer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(adBuffer, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void notifyTvMessage(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setTvMessageEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setVideoFrozen(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void notifyTvAdSessionData(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
