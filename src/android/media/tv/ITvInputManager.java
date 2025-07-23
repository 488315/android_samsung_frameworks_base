package android.media.tv;

import android.content.AttributionSource;
import android.content.Intent;
import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.ITvInputClient;
import android.media.tv.ITvInputHardware;
import android.media.tv.ITvInputHardwareCallback;
import android.media.tv.ITvInputManagerCallback;
import android.media.tv.interactive.TvInteractiveAppService;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.Surface;
import java.util.List;

/* loaded from: classes3.dex */
public interface ITvInputManager extends IInterface {

    public static class Default implements ITvInputManager {
        @Override // android.media.tv.ITvInputManager
        public ITvInputHardware acquireTvInputHardware(int i, ITvInputHardwareCallback iTvInputHardwareCallback, TvInputInfo tvInputInfo, int i2, String str, int i3) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public void addBlockedRating(String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void addHardwareDevice(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public boolean captureFrame(String str, Surface surface, TvStreamConfig tvStreamConfig, int i) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.ITvInputManager
        public void createOverlayView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void createSession(ITvInputClient iTvInputClient, String str, AttributionSource attributionSource, boolean z, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public List<String> getAvailableExtensionInterfaceNames(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public List<TvStreamConfig> getAvailableTvStreamConfigList(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public List<String> getBlockedRatings(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public int getClientPid(String str) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.ITvInputManager
        public int getClientPriority(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.ITvInputManager
        public int getClientUserId(String str) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.ITvInputManager
        public List<TunedInfo> getCurrentTunedInfos(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public List<DvbDeviceInfo> getDvbDeviceList() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public IBinder getExtensionInterface(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public List<TvInputHardwareInfo> getHardwareList() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public List<TvContentRatingSystemInfo> getTvContentRatingSystemList(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public TvInputInfo getTvInputInfo(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public List<TvInputInfo> getTvInputList(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public int getTvInputState(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.ITvInputManager
        public boolean isParentalControlsEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.ITvInputManager
        public boolean isRatingBlocked(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.ITvInputManager
        public boolean isSingleSessionActive(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.ITvInputManager
        public void notifyAdBufferReady(IBinder iBinder, AdBuffer adBuffer, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void notifyTvAdSessionData(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public ParcelFileDescriptor openDvbDevice(DvbDeviceInfo dvbDeviceInfo, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public void pauseRecording(IBinder iBinder, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void registerCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void relayoutOverlayView(IBinder iBinder, Rect rect, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void releaseSession(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void releaseTvInputHardware(int i, ITvInputHardware iTvInputHardware, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void removeBlockedRating(String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void removeBroadcastInfo(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void removeHardwareDevice(int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void removeOverlayView(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void requestAd(IBinder iBinder, AdRequest adRequest, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void requestBroadcastInfo(IBinder iBinder, BroadcastInfoRequest broadcastInfoRequest, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void requestChannelBrowsable(Uri uri, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void resumePlayback(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void resumeRecording(IBinder iBinder, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void selectAudioPresentation(IBinder iBinder, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void selectTrack(IBinder iBinder, int i, String str, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void sendAppPrivateCommand(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void sendTvInputNotifyIntent(Intent intent, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setCaptionEnabled(IBinder iBinder, boolean z, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setInteractiveAppNotificationEnabled(IBinder iBinder, boolean z, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setMainSession(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setParentalControlsEnabled(boolean z, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setTvMessageEnabled(IBinder iBinder, int i, boolean z, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setVideoFrozen(IBinder iBinder, boolean z, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setVolume(IBinder iBinder, float f, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void startRecording(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void stopPlayback(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void stopRecording(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftEnablePositionTracking(IBinder iBinder, boolean z, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftPause(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftPlay(IBinder iBinder, Uri uri, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftResume(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftSeekTo(IBinder iBinder, long j, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftSetMode(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftSetPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void tune(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void unblockContent(IBinder iBinder, String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void unregisterCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void updateTvInputInfo(TvInputInfo tvInputInfo, int i) throws RemoteException {
        }
    }

    ITvInputHardware acquireTvInputHardware(int i, ITvInputHardwareCallback iTvInputHardwareCallback, TvInputInfo tvInputInfo, int i2, String str, int i3) throws RemoteException;

    void addBlockedRating(String str, int i) throws RemoteException;

    void addHardwareDevice(int i) throws RemoteException;

    boolean captureFrame(String str, Surface surface, TvStreamConfig tvStreamConfig, int i) throws RemoteException;

    void createOverlayView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException;

    void createSession(ITvInputClient iTvInputClient, String str, AttributionSource attributionSource, boolean z, int i, int i2) throws RemoteException;

    void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException;

    List<String> getAvailableExtensionInterfaceNames(String str, int i) throws RemoteException;

    List<TvStreamConfig> getAvailableTvStreamConfigList(String str, int i) throws RemoteException;

    List<String> getBlockedRatings(int i) throws RemoteException;

    int getClientPid(String str) throws RemoteException;

    int getClientPriority(int i, String str) throws RemoteException;

    int getClientUserId(String str) throws RemoteException;

    List<TunedInfo> getCurrentTunedInfos(int i) throws RemoteException;

    List<DvbDeviceInfo> getDvbDeviceList() throws RemoteException;

    IBinder getExtensionInterface(String str, String str2, int i) throws RemoteException;

    List<TvInputHardwareInfo> getHardwareList() throws RemoteException;

    List<TvContentRatingSystemInfo> getTvContentRatingSystemList(int i) throws RemoteException;

    TvInputInfo getTvInputInfo(String str, int i) throws RemoteException;

    List<TvInputInfo> getTvInputList(int i) throws RemoteException;

    int getTvInputState(String str, int i) throws RemoteException;

    boolean isParentalControlsEnabled(int i) throws RemoteException;

    boolean isRatingBlocked(String str, int i) throws RemoteException;

    boolean isSingleSessionActive(int i) throws RemoteException;

    void notifyAdBufferReady(IBinder iBinder, AdBuffer adBuffer, int i) throws RemoteException;

    void notifyTvAdSessionData(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException;

    void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException;

    ParcelFileDescriptor openDvbDevice(DvbDeviceInfo dvbDeviceInfo, int i) throws RemoteException;

    void pauseRecording(IBinder iBinder, Bundle bundle, int i) throws RemoteException;

    void registerCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) throws RemoteException;

    void relayoutOverlayView(IBinder iBinder, Rect rect, int i) throws RemoteException;

    void releaseSession(IBinder iBinder, int i) throws RemoteException;

    void releaseTvInputHardware(int i, ITvInputHardware iTvInputHardware, int i2) throws RemoteException;

    void removeBlockedRating(String str, int i) throws RemoteException;

    void removeBroadcastInfo(IBinder iBinder, int i, int i2) throws RemoteException;

    void removeHardwareDevice(int i) throws RemoteException;

    void removeOverlayView(IBinder iBinder, int i) throws RemoteException;

    void requestAd(IBinder iBinder, AdRequest adRequest, int i) throws RemoteException;

    void requestBroadcastInfo(IBinder iBinder, BroadcastInfoRequest broadcastInfoRequest, int i) throws RemoteException;

    void requestChannelBrowsable(Uri uri, int i) throws RemoteException;

    void resumePlayback(IBinder iBinder, int i) throws RemoteException;

    void resumeRecording(IBinder iBinder, Bundle bundle, int i) throws RemoteException;

    void selectAudioPresentation(IBinder iBinder, int i, int i2, int i3) throws RemoteException;

    void selectTrack(IBinder iBinder, int i, String str, int i2) throws RemoteException;

    void sendAppPrivateCommand(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException;

    void sendTvInputNotifyIntent(Intent intent, int i) throws RemoteException;

    void setCaptionEnabled(IBinder iBinder, boolean z, int i) throws RemoteException;

    void setInteractiveAppNotificationEnabled(IBinder iBinder, boolean z, int i) throws RemoteException;

    void setMainSession(IBinder iBinder, int i) throws RemoteException;

    void setParentalControlsEnabled(boolean z, int i) throws RemoteException;

    void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException;

    void setTvMessageEnabled(IBinder iBinder, int i, boolean z, int i2) throws RemoteException;

    void setVideoFrozen(IBinder iBinder, boolean z, int i) throws RemoteException;

    void setVolume(IBinder iBinder, float f, int i) throws RemoteException;

    void startRecording(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException;

    void stopPlayback(IBinder iBinder, int i, int i2) throws RemoteException;

    void stopRecording(IBinder iBinder, int i) throws RemoteException;

    void timeShiftEnablePositionTracking(IBinder iBinder, boolean z, int i) throws RemoteException;

    void timeShiftPause(IBinder iBinder, int i) throws RemoteException;

    void timeShiftPlay(IBinder iBinder, Uri uri, int i) throws RemoteException;

    void timeShiftResume(IBinder iBinder, int i) throws RemoteException;

    void timeShiftSeekTo(IBinder iBinder, long j, int i) throws RemoteException;

    void timeShiftSetMode(IBinder iBinder, int i, int i2) throws RemoteException;

    void timeShiftSetPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int i) throws RemoteException;

    void tune(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException;

    void unblockContent(IBinder iBinder, String str, int i) throws RemoteException;

    void unregisterCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) throws RemoteException;

    void updateTvInputInfo(TvInputInfo tvInputInfo, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInputManager {
        public static final String DESCRIPTOR = "android.media.tv.ITvInputManager";
        static final int TRANSACTION_acquireTvInputHardware = 56;
        static final int TRANSACTION_addBlockedRating = 14;
        static final int TRANSACTION_addHardwareDevice = 65;
        static final int TRANSACTION_captureFrame = 59;
        static final int TRANSACTION_createOverlayView = 31;
        static final int TRANSACTION_createSession = 16;
        static final int TRANSACTION_dispatchSurfaceChanged = 23;
        static final int TRANSACTION_getAvailableExtensionInterfaceNames = 5;
        static final int TRANSACTION_getAvailableTvStreamConfigList = 58;
        static final int TRANSACTION_getBlockedRatings = 13;
        static final int TRANSACTION_getClientPid = 18;
        static final int TRANSACTION_getClientPriority = 19;
        static final int TRANSACTION_getClientUserId = 20;
        static final int TRANSACTION_getCurrentTunedInfos = 42;
        static final int TRANSACTION_getDvbDeviceList = 61;
        static final int TRANSACTION_getExtensionInterface = 6;
        static final int TRANSACTION_getHardwareList = 55;
        static final int TRANSACTION_getTvContentRatingSystemList = 7;
        static final int TRANSACTION_getTvInputInfo = 2;
        static final int TRANSACTION_getTvInputList = 1;
        static final int TRANSACTION_getTvInputState = 4;
        static final int TRANSACTION_isParentalControlsEnabled = 10;
        static final int TRANSACTION_isRatingBlocked = 12;
        static final int TRANSACTION_isSingleSessionActive = 60;
        static final int TRANSACTION_notifyAdBufferReady = 52;
        static final int TRANSACTION_notifyTvAdSessionData = 68;
        static final int TRANSACTION_notifyTvMessage = 53;
        static final int TRANSACTION_openDvbDevice = 62;
        static final int TRANSACTION_pauseRecording = 45;
        static final int TRANSACTION_registerCallback = 8;
        static final int TRANSACTION_relayoutOverlayView = 32;
        static final int TRANSACTION_releaseSession = 17;
        static final int TRANSACTION_releaseTvInputHardware = 57;
        static final int TRANSACTION_removeBlockedRating = 15;
        static final int TRANSACTION_removeBroadcastInfo = 50;
        static final int TRANSACTION_removeHardwareDevice = 66;
        static final int TRANSACTION_removeOverlayView = 33;
        static final int TRANSACTION_requestAd = 51;
        static final int TRANSACTION_requestBroadcastInfo = 49;
        static final int TRANSACTION_requestChannelBrowsable = 64;
        static final int TRANSACTION_resumePlayback = 47;
        static final int TRANSACTION_resumeRecording = 46;
        static final int TRANSACTION_selectAudioPresentation = 28;
        static final int TRANSACTION_selectTrack = 27;
        static final int TRANSACTION_sendAppPrivateCommand = 30;
        static final int TRANSACTION_sendTvInputNotifyIntent = 63;
        static final int TRANSACTION_setCaptionEnabled = 26;
        static final int TRANSACTION_setInteractiveAppNotificationEnabled = 29;
        static final int TRANSACTION_setMainSession = 21;
        static final int TRANSACTION_setParentalControlsEnabled = 11;
        static final int TRANSACTION_setSurface = 22;
        static final int TRANSACTION_setTvMessageEnabled = 54;
        static final int TRANSACTION_setVideoFrozen = 67;
        static final int TRANSACTION_setVolume = 24;
        static final int TRANSACTION_startRecording = 43;
        static final int TRANSACTION_stopPlayback = 48;
        static final int TRANSACTION_stopRecording = 44;
        static final int TRANSACTION_timeShiftEnablePositionTracking = 41;
        static final int TRANSACTION_timeShiftPause = 36;
        static final int TRANSACTION_timeShiftPlay = 35;
        static final int TRANSACTION_timeShiftResume = 37;
        static final int TRANSACTION_timeShiftSeekTo = 38;
        static final int TRANSACTION_timeShiftSetMode = 40;
        static final int TRANSACTION_timeShiftSetPlaybackParams = 39;
        static final int TRANSACTION_tune = 25;
        static final int TRANSACTION_unblockContent = 34;
        static final int TRANSACTION_unregisterCallback = 9;
        static final int TRANSACTION_updateTvInputInfo = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 67;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvInputManager)) {
                return (ITvInputManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTvInputList";
                case 2:
                    return "getTvInputInfo";
                case 3:
                    return "updateTvInputInfo";
                case 4:
                    return "getTvInputState";
                case 5:
                    return "getAvailableExtensionInterfaceNames";
                case 6:
                    return "getExtensionInterface";
                case 7:
                    return "getTvContentRatingSystemList";
                case 8:
                    return "registerCallback";
                case 9:
                    return "unregisterCallback";
                case 10:
                    return "isParentalControlsEnabled";
                case 11:
                    return "setParentalControlsEnabled";
                case 12:
                    return "isRatingBlocked";
                case 13:
                    return "getBlockedRatings";
                case 14:
                    return "addBlockedRating";
                case 15:
                    return "removeBlockedRating";
                case 16:
                    return "createSession";
                case 17:
                    return "releaseSession";
                case 18:
                    return "getClientPid";
                case 19:
                    return "getClientPriority";
                case 20:
                    return "getClientUserId";
                case 21:
                    return "setMainSession";
                case 22:
                    return "setSurface";
                case 23:
                    return "dispatchSurfaceChanged";
                case 24:
                    return "setVolume";
                case 25:
                    return TvInteractiveAppService.PLAYBACK_COMMAND_TYPE_TUNE;
                case 26:
                    return "setCaptionEnabled";
                case 27:
                    return "selectTrack";
                case 28:
                    return "selectAudioPresentation";
                case 29:
                    return "setInteractiveAppNotificationEnabled";
                case 30:
                    return "sendAppPrivateCommand";
                case 31:
                    return "createOverlayView";
                case 32:
                    return "relayoutOverlayView";
                case 33:
                    return "removeOverlayView";
                case 34:
                    return "unblockContent";
                case 35:
                    return "timeShiftPlay";
                case 36:
                    return "timeShiftPause";
                case 37:
                    return "timeShiftResume";
                case 38:
                    return "timeShiftSeekTo";
                case 39:
                    return "timeShiftSetPlaybackParams";
                case 40:
                    return "timeShiftSetMode";
                case 41:
                    return "timeShiftEnablePositionTracking";
                case 42:
                    return "getCurrentTunedInfos";
                case 43:
                    return "startRecording";
                case 44:
                    return "stopRecording";
                case 45:
                    return "pauseRecording";
                case 46:
                    return "resumeRecording";
                case 47:
                    return "resumePlayback";
                case 48:
                    return "stopPlayback";
                case 49:
                    return "requestBroadcastInfo";
                case 50:
                    return "removeBroadcastInfo";
                case 51:
                    return "requestAd";
                case 52:
                    return "notifyAdBufferReady";
                case 53:
                    return "notifyTvMessage";
                case 54:
                    return "setTvMessageEnabled";
                case 55:
                    return "getHardwareList";
                case 56:
                    return "acquireTvInputHardware";
                case 57:
                    return "releaseTvInputHardware";
                case 58:
                    return "getAvailableTvStreamConfigList";
                case 59:
                    return "captureFrame";
                case 60:
                    return "isSingleSessionActive";
                case 61:
                    return "getDvbDeviceList";
                case 62:
                    return "openDvbDevice";
                case 63:
                    return "sendTvInputNotifyIntent";
                case 64:
                    return "requestChannelBrowsable";
                case 65:
                    return "addHardwareDevice";
                case 66:
                    return "removeHardwareDevice";
                case 67:
                    return "setVideoFrozen";
                case 68:
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<TvInputInfo> tvInputList = getTvInputList(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tvInputList, 1);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    TvInputInfo tvInputInfo = getTvInputInfo(readString, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(tvInputInfo, 1);
                    return true;
                case 3:
                    TvInputInfo tvInputInfo2 = (TvInputInfo) parcel.readTypedObject(TvInputInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateTvInputInfo(tvInputInfo2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString2 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int tvInputState = getTvInputState(readString2, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(tvInputState);
                    return true;
                case 5:
                    String readString3 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> availableExtensionInterfaceNames = getAvailableExtensionInterfaceNames(readString3, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeStringList(availableExtensionInterfaceNames);
                    return true;
                case 6:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IBinder extensionInterface = getExtensionInterface(readString4, readString5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(extensionInterface);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<TvContentRatingSystemInfo> tvContentRatingSystemList = getTvContentRatingSystemList(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tvContentRatingSystemList, 1);
                    return true;
                case 8:
                    ITvInputManagerCallback asInterface = ITvInputManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ITvInputManagerCallback asInterface2 = ITvInputManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isParentalControlsEnabled = isParentalControlsEnabled(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isParentalControlsEnabled);
                    return true;
                case 11:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setParentalControlsEnabled(readBoolean, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString6 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRatingBlocked = isRatingBlocked(readString6, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRatingBlocked);
                    return true;
                case 13:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> blockedRatings = getBlockedRatings(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeStringList(blockedRatings);
                    return true;
                case 14:
                    String readString7 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addBlockedRating(readString7, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString8 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeBlockedRating(readString8, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    ITvInputClient asInterface3 = ITvInputClient.Stub.asInterface(parcel.readStrongBinder());
                    String readString9 = parcel.readString();
                    AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSession(asInterface3, readString9, attributionSource, readBoolean2, readInt16, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSession(readStrongBinder, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int clientPid = getClientPid(readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientPid);
                    return true;
                case 19:
                    int readInt19 = parcel.readInt();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int clientPriority = getClientPriority(readInt19, readString11);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientPriority);
                    return true;
                case 20:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int clientUserId = getClientUserId(readString12);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientUserId);
                    return true;
                case 21:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMainSession(readStrongBinder2, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSurface(readStrongBinder3, surface, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(readStrongBinder4, readInt22, readInt23, readInt24, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    float readFloat = parcel.readFloat();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVolume(readStrongBinder5, readFloat, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    tune(readStrongBinder6, uri, bundle, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCaptionEnabled(readStrongBinder7, readBoolean3, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    int readInt29 = parcel.readInt();
                    String readString13 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    selectTrack(readStrongBinder8, readInt29, readString13, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    selectAudioPresentation(readStrongBinder9, readInt31, readInt32, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInteractiveAppNotificationEnabled(readStrongBinder10, readBoolean4, readInt34);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    String readString14 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAppPrivateCommand(readStrongBinder11, readString14, bundle2, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createOverlayView(readStrongBinder12, readStrongBinder13, rect, readInt36);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    relayoutOverlayView(readStrongBinder14, rect2, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOverlayView(readStrongBinder15, readInt38);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    String readString15 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unblockContent(readStrongBinder16, readString15, readInt39);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    IBinder readStrongBinder17 = parcel.readStrongBinder();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftPlay(readStrongBinder17, uri2, readInt40);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    IBinder readStrongBinder18 = parcel.readStrongBinder();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftPause(readStrongBinder18, readInt41);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    IBinder readStrongBinder19 = parcel.readStrongBinder();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftResume(readStrongBinder19, readInt42);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IBinder readStrongBinder20 = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftSeekTo(readStrongBinder20, readLong, readInt43);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    IBinder readStrongBinder21 = parcel.readStrongBinder();
                    PlaybackParams playbackParams = (PlaybackParams) parcel.readTypedObject(PlaybackParams.CREATOR);
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftSetPlaybackParams(readStrongBinder21, playbackParams, readInt44);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IBinder readStrongBinder22 = parcel.readStrongBinder();
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftSetMode(readStrongBinder22, readInt45, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    IBinder readStrongBinder23 = parcel.readStrongBinder();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftEnablePositionTracking(readStrongBinder23, readBoolean5, readInt47);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<TunedInfo> currentTunedInfos = getCurrentTunedInfos(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(currentTunedInfos, 1);
                    return true;
                case 43:
                    IBinder readStrongBinder24 = parcel.readStrongBinder();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startRecording(readStrongBinder24, uri3, bundle3, readInt49);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IBinder readStrongBinder25 = parcel.readStrongBinder();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopRecording(readStrongBinder25, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    IBinder readStrongBinder26 = parcel.readStrongBinder();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    pauseRecording(readStrongBinder26, bundle4, readInt51);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    IBinder readStrongBinder27 = parcel.readStrongBinder();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resumeRecording(readStrongBinder27, bundle5, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    IBinder readStrongBinder28 = parcel.readStrongBinder();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resumePlayback(readStrongBinder28, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    IBinder readStrongBinder29 = parcel.readStrongBinder();
                    int readInt54 = parcel.readInt();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopPlayback(readStrongBinder29, readInt54, readInt55);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    IBinder readStrongBinder30 = parcel.readStrongBinder();
                    BroadcastInfoRequest broadcastInfoRequest = (BroadcastInfoRequest) parcel.readTypedObject(BroadcastInfoRequest.CREATOR);
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestBroadcastInfo(readStrongBinder30, broadcastInfoRequest, readInt56);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    IBinder readStrongBinder31 = parcel.readStrongBinder();
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeBroadcastInfo(readStrongBinder31, readInt57, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IBinder readStrongBinder32 = parcel.readStrongBinder();
                    AdRequest adRequest = (AdRequest) parcel.readTypedObject(AdRequest.CREATOR);
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestAd(readStrongBinder32, adRequest, readInt59);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    IBinder readStrongBinder33 = parcel.readStrongBinder();
                    AdBuffer adBuffer = (AdBuffer) parcel.readTypedObject(AdBuffer.CREATOR);
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAdBufferReady(readStrongBinder33, adBuffer, readInt60);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    IBinder readStrongBinder34 = parcel.readStrongBinder();
                    int readInt61 = parcel.readInt();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(readStrongBinder34, readInt61, bundle6, readInt62);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    IBinder readStrongBinder35 = parcel.readStrongBinder();
                    int readInt63 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTvMessageEnabled(readStrongBinder35, readInt63, readBoolean6, readInt64);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    List<TvInputHardwareInfo> hardwareList = getHardwareList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(hardwareList, 1);
                    return true;
                case 56:
                    int readInt65 = parcel.readInt();
                    ITvInputHardwareCallback asInterface4 = ITvInputHardwareCallback.Stub.asInterface(parcel.readStrongBinder());
                    TvInputInfo tvInputInfo3 = (TvInputInfo) parcel.readTypedObject(TvInputInfo.CREATOR);
                    int readInt66 = parcel.readInt();
                    String readString16 = parcel.readString();
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ITvInputHardware acquireTvInputHardware = acquireTvInputHardware(readInt65, asInterface4, tvInputInfo3, readInt66, readString16, readInt67);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(acquireTvInputHardware);
                    return true;
                case 57:
                    int readInt68 = parcel.readInt();
                    ITvInputHardware asInterface5 = ITvInputHardware.Stub.asInterface(parcel.readStrongBinder());
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseTvInputHardware(readInt68, asInterface5, readInt69);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    String readString17 = parcel.readString();
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<TvStreamConfig> availableTvStreamConfigList = getAvailableTvStreamConfigList(readString17, readInt70);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(availableTvStreamConfigList, 1);
                    return true;
                case 59:
                    String readString18 = parcel.readString();
                    Surface surface2 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    TvStreamConfig tvStreamConfig = (TvStreamConfig) parcel.readTypedObject(TvStreamConfig.CREATOR);
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean captureFrame = captureFrame(readString18, surface2, tvStreamConfig, readInt71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(captureFrame);
                    return true;
                case 60:
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSingleSessionActive = isSingleSessionActive(readInt72);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSingleSessionActive);
                    return true;
                case 61:
                    List<DvbDeviceInfo> dvbDeviceList = getDvbDeviceList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(dvbDeviceList, 1);
                    return true;
                case 62:
                    DvbDeviceInfo dvbDeviceInfo = (DvbDeviceInfo) parcel.readTypedObject(DvbDeviceInfo.CREATOR);
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor openDvbDevice = openDvbDevice(dvbDeviceInfo, readInt73);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openDvbDevice, 1);
                    return true;
                case 63:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTvInputNotifyIntent(intent, readInt74);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestChannelBrowsable(uri4, readInt75);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addHardwareDevice(readInt76);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeHardwareDevice(readInt77);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    IBinder readStrongBinder36 = parcel.readStrongBinder();
                    boolean readBoolean7 = parcel.readBoolean();
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVideoFrozen(readStrongBinder36, readBoolean7, readInt78);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    IBinder readStrongBinder37 = parcel.readStrongBinder();
                    String readString19 = parcel.readString();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvAdSessionData(readStrongBinder37, readString19, bundle7, readInt79);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvInputManager {
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

            @Override // android.media.tv.ITvInputManager
            public List<TvInputInfo> getTvInputList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(TvInputInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public TvInputInfo getTvInputInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TvInputInfo) obtain2.readTypedObject(TvInputInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void updateTvInputInfo(TvInputInfo tvInputInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tvInputInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public int getTvInputState(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<String> getAvailableExtensionInterfaceNames(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public IBinder getExtensionInterface(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<TvContentRatingSystemInfo> getTvContentRatingSystemList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(TvContentRatingSystemInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void registerCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputManagerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void unregisterCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputManagerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean isParentalControlsEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setParentalControlsEnabled(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean isRatingBlocked(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<String> getBlockedRatings(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void addBlockedRating(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeBlockedRating(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void createSession(ITvInputClient iTvInputClient, String str, AttributionSource attributionSource, boolean z, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputClient);
                    obtain.writeString(str);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void releaseSession(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public int getClientPid(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public int getClientPriority(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public int getClientUserId(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setMainSession(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setVolume(IBinder iBinder, float f, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeFloat(f);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void tune(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setCaptionEnabled(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void selectTrack(IBinder iBinder, int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void selectAudioPresentation(IBinder iBinder, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setInteractiveAppNotificationEnabled(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void sendAppPrivateCommand(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void createOverlayView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void relayoutOverlayView(IBinder iBinder, Rect rect, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeOverlayView(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void unblockContent(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftPlay(IBinder iBinder, Uri uri, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftPause(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftResume(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftSeekTo(IBinder iBinder, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftSetPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(playbackParams, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftSetMode(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftEnablePositionTracking(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<TunedInfo> getCurrentTunedInfos(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(TunedInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void startRecording(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void stopRecording(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void pauseRecording(IBinder iBinder, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void resumeRecording(IBinder iBinder, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void resumePlayback(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void stopPlayback(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void requestBroadcastInfo(IBinder iBinder, BroadcastInfoRequest broadcastInfoRequest, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(broadcastInfoRequest, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeBroadcastInfo(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void requestAd(IBinder iBinder, AdRequest adRequest, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(adRequest, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void notifyAdBufferReady(IBinder iBinder, AdBuffer adBuffer, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(adBuffer, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setTvMessageEnabled(IBinder iBinder, int i, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<TvInputHardwareInfo> getHardwareList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(TvInputHardwareInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public ITvInputHardware acquireTvInputHardware(int i, ITvInputHardwareCallback iTvInputHardwareCallback, TvInputInfo tvInputInfo, int i2, String str, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTvInputHardwareCallback);
                    obtain.writeTypedObject(tvInputInfo, 0);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return ITvInputHardware.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void releaseTvInputHardware(int i, ITvInputHardware iTvInputHardware, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTvInputHardware);
                    obtain.writeInt(i2);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<TvStreamConfig> getAvailableTvStreamConfigList(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(TvStreamConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean captureFrame(String str, Surface surface, TvStreamConfig tvStreamConfig, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeTypedObject(tvStreamConfig, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean isSingleSessionActive(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<DvbDeviceInfo> getDvbDeviceList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(DvbDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public ParcelFileDescriptor openDvbDevice(DvbDeviceInfo dvbDeviceInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(dvbDeviceInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void sendTvInputNotifyIntent(Intent intent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void requestChannelBrowsable(Uri uri, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void addHardwareDevice(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeHardwareDevice(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setVideoFrozen(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void notifyTvAdSessionData(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
