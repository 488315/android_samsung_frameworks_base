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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvInputManager)) {
                return (ITvInputManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<TvInputInfo> tvInputList = getTvInputList(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tvInputList, 1);
                    return true;
                case 2:
                    String string = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    TvInputInfo tvInputInfo = getTvInputInfo(string, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(tvInputInfo, 1);
                    return true;
                case 3:
                    TvInputInfo tvInputInfo2 = (TvInputInfo) parcel.readTypedObject(TvInputInfo.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateTvInputInfo(tvInputInfo2, i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string2 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int tvInputState = getTvInputState(string2, i6);
                    parcel2.writeNoException();
                    parcel2.writeInt(tvInputState);
                    return true;
                case 5:
                    String string3 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> availableExtensionInterfaceNames = getAvailableExtensionInterfaceNames(string3, i7);
                    parcel2.writeNoException();
                    parcel2.writeStringList(availableExtensionInterfaceNames);
                    return true;
                case 6:
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IBinder extensionInterface = getExtensionInterface(string4, string5, i8);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(extensionInterface);
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<TvContentRatingSystemInfo> tvContentRatingSystemList = getTvContentRatingSystemList(i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tvContentRatingSystemList, 1);
                    return true;
                case 8:
                    ITvInputManagerCallback iTvInputManagerCallbackAsInterface = ITvInputManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(iTvInputManagerCallbackAsInterface, i10);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ITvInputManagerCallback iTvInputManagerCallbackAsInterface2 = ITvInputManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCallback(iTvInputManagerCallbackAsInterface2, i11);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsParentalControlsEnabled = isParentalControlsEnabled(i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsParentalControlsEnabled);
                    return true;
                case 11:
                    boolean z = parcel.readBoolean();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setParentalControlsEnabled(z, i13);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String string6 = parcel.readString();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRatingBlocked = isRatingBlocked(string6, i14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRatingBlocked);
                    return true;
                case 13:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> blockedRatings = getBlockedRatings(i15);
                    parcel2.writeNoException();
                    parcel2.writeStringList(blockedRatings);
                    return true;
                case 14:
                    String string7 = parcel.readString();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addBlockedRating(string7, i16);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string8 = parcel.readString();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeBlockedRating(string8, i17);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    ITvInputClient iTvInputClientAsInterface = ITvInputClient.Stub.asInterface(parcel.readStrongBinder());
                    String string9 = parcel.readString();
                    AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSession(iTvInputClientAsInterface, string9, attributionSource, z2, i18, i19);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSession(strongBinder, i20);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int clientPid = getClientPid(string10);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientPid);
                    return true;
                case 19:
                    int i21 = parcel.readInt();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int clientPriority = getClientPriority(i21, string11);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientPriority);
                    return true;
                case 20:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int clientUserId = getClientUserId(string12);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientUserId);
                    return true;
                case 21:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMainSession(strongBinder2, i22);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSurface(strongBinder3, surface, i23);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(strongBinder4, i24, i25, i26, i27);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    float f = parcel.readFloat();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVolume(strongBinder5, f, i28);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    tune(strongBinder6, uri, bundle, i29);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    boolean z3 = parcel.readBoolean();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCaptionEnabled(strongBinder7, z3, i30);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    int i31 = parcel.readInt();
                    String string13 = parcel.readString();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    selectTrack(strongBinder8, i31, string13, i32);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    selectAudioPresentation(strongBinder9, i33, i34, i35);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    boolean z4 = parcel.readBoolean();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInteractiveAppNotificationEnabled(strongBinder10, z4, i36);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    String string14 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAppPrivateCommand(strongBinder11, string14, bundle2, i37);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createOverlayView(strongBinder12, strongBinder13, rect, i38);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    relayoutOverlayView(strongBinder14, rect2, i39);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOverlayView(strongBinder15, i40);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    String string15 = parcel.readString();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unblockContent(strongBinder16, string15, i41);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftPlay(strongBinder17, uri2, i42);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftPause(strongBinder18, i43);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    IBinder strongBinder19 = parcel.readStrongBinder();
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftResume(strongBinder19, i44);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IBinder strongBinder20 = parcel.readStrongBinder();
                    long j = parcel.readLong();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftSeekTo(strongBinder20, j, i45);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    IBinder strongBinder21 = parcel.readStrongBinder();
                    PlaybackParams playbackParams = (PlaybackParams) parcel.readTypedObject(PlaybackParams.CREATOR);
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftSetPlaybackParams(strongBinder21, playbackParams, i46);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IBinder strongBinder22 = parcel.readStrongBinder();
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftSetMode(strongBinder22, i47, i48);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    IBinder strongBinder23 = parcel.readStrongBinder();
                    boolean z5 = parcel.readBoolean();
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftEnablePositionTracking(strongBinder23, z5, i49);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<TunedInfo> currentTunedInfos = getCurrentTunedInfos(i50);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(currentTunedInfos, 1);
                    return true;
                case 43:
                    IBinder strongBinder24 = parcel.readStrongBinder();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startRecording(strongBinder24, uri3, bundle3, i51);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IBinder strongBinder25 = parcel.readStrongBinder();
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopRecording(strongBinder25, i52);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    IBinder strongBinder26 = parcel.readStrongBinder();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    pauseRecording(strongBinder26, bundle4, i53);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    IBinder strongBinder27 = parcel.readStrongBinder();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resumeRecording(strongBinder27, bundle5, i54);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    IBinder strongBinder28 = parcel.readStrongBinder();
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resumePlayback(strongBinder28, i55);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    IBinder strongBinder29 = parcel.readStrongBinder();
                    int i56 = parcel.readInt();
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopPlayback(strongBinder29, i56, i57);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    IBinder strongBinder30 = parcel.readStrongBinder();
                    BroadcastInfoRequest broadcastInfoRequest = (BroadcastInfoRequest) parcel.readTypedObject(BroadcastInfoRequest.CREATOR);
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestBroadcastInfo(strongBinder30, broadcastInfoRequest, i58);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    IBinder strongBinder31 = parcel.readStrongBinder();
                    int i59 = parcel.readInt();
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeBroadcastInfo(strongBinder31, i59, i60);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IBinder strongBinder32 = parcel.readStrongBinder();
                    AdRequest adRequest = (AdRequest) parcel.readTypedObject(AdRequest.CREATOR);
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestAd(strongBinder32, adRequest, i61);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    IBinder strongBinder33 = parcel.readStrongBinder();
                    AdBuffer adBuffer = (AdBuffer) parcel.readTypedObject(AdBuffer.CREATOR);
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAdBufferReady(strongBinder33, adBuffer, i62);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    IBinder strongBinder34 = parcel.readStrongBinder();
                    int i63 = parcel.readInt();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(strongBinder34, i63, bundle6, i64);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    IBinder strongBinder35 = parcel.readStrongBinder();
                    int i65 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTvMessageEnabled(strongBinder35, i65, z6, i66);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    List<TvInputHardwareInfo> hardwareList = getHardwareList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(hardwareList, 1);
                    return true;
                case 56:
                    int i67 = parcel.readInt();
                    ITvInputHardwareCallback iTvInputHardwareCallbackAsInterface = ITvInputHardwareCallback.Stub.asInterface(parcel.readStrongBinder());
                    TvInputInfo tvInputInfo3 = (TvInputInfo) parcel.readTypedObject(TvInputInfo.CREATOR);
                    int i68 = parcel.readInt();
                    String string16 = parcel.readString();
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ITvInputHardware iTvInputHardwareAcquireTvInputHardware = acquireTvInputHardware(i67, iTvInputHardwareCallbackAsInterface, tvInputInfo3, i68, string16, i69);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iTvInputHardwareAcquireTvInputHardware);
                    return true;
                case 57:
                    int i70 = parcel.readInt();
                    ITvInputHardware iTvInputHardwareAsInterface = ITvInputHardware.Stub.asInterface(parcel.readStrongBinder());
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseTvInputHardware(i70, iTvInputHardwareAsInterface, i71);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    String string17 = parcel.readString();
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<TvStreamConfig> availableTvStreamConfigList = getAvailableTvStreamConfigList(string17, i72);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(availableTvStreamConfigList, 1);
                    return true;
                case 59:
                    String string18 = parcel.readString();
                    Surface surface2 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    TvStreamConfig tvStreamConfig = (TvStreamConfig) parcel.readTypedObject(TvStreamConfig.CREATOR);
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCaptureFrame = captureFrame(string18, surface2, tvStreamConfig, i73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCaptureFrame);
                    return true;
                case 60:
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSingleSessionActive = isSingleSessionActive(i74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSingleSessionActive);
                    return true;
                case 61:
                    List<DvbDeviceInfo> dvbDeviceList = getDvbDeviceList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(dvbDeviceList, 1);
                    return true;
                case 62:
                    DvbDeviceInfo dvbDeviceInfo = (DvbDeviceInfo) parcel.readTypedObject(DvbDeviceInfo.CREATOR);
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor parcelFileDescriptorOpenDvbDevice = openDvbDevice(dvbDeviceInfo, i75);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorOpenDvbDevice, 1);
                    return true;
                case 63:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTvInputNotifyIntent(intent, i76);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestChannelBrowsable(uri4, i77);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int i78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addHardwareDevice(i78);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int i79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeHardwareDevice(i79);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    IBinder strongBinder36 = parcel.readStrongBinder();
                    boolean z7 = parcel.readBoolean();
                    int i80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVideoFrozen(strongBinder36, z7, i80);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    IBinder strongBinder37 = parcel.readStrongBinder();
                    String string19 = parcel.readString();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvAdSessionData(strongBinder37, string19, bundle7, i81);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(TvInputInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public TvInputInfo getTvInputInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TvInputInfo) parcelObtain2.readTypedObject(TvInputInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void updateTvInputInfo(TvInputInfo tvInputInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(tvInputInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public int getTvInputState(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<String> getAvailableExtensionInterfaceNames(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public IBinder getExtensionInterface(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<TvContentRatingSystemInfo> getTvContentRatingSystemList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(TvContentRatingSystemInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void registerCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInputManagerCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void unregisterCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInputManagerCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean isParentalControlsEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setParentalControlsEnabled(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean isRatingBlocked(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<String> getBlockedRatings(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void addBlockedRating(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeBlockedRating(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void createSession(ITvInputClient iTvInputClient, String str, AttributionSource attributionSource, boolean z, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInputClient);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void releaseSession(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public int getClientPid(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public int getClientPriority(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public int getClientUserId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setMainSession(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(surface, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setVolume(IBinder iBinder, float f, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void tune(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setCaptionEnabled(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void selectTrack(IBinder iBinder, int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void selectAudioPresentation(IBinder iBinder, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setInteractiveAppNotificationEnabled(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void sendAppPrivateCommand(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void createOverlayView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void relayoutOverlayView(IBinder iBinder, Rect rect, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeOverlayView(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void unblockContent(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftPlay(IBinder iBinder, Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftPause(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftResume(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftSeekTo(IBinder iBinder, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftSetPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(playbackParams, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftSetMode(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftEnablePositionTracking(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<TunedInfo> getCurrentTunedInfos(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(TunedInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void startRecording(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void stopRecording(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void pauseRecording(IBinder iBinder, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void resumeRecording(IBinder iBinder, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void resumePlayback(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void stopPlayback(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void requestBroadcastInfo(IBinder iBinder, BroadcastInfoRequest broadcastInfoRequest, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(broadcastInfoRequest, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeBroadcastInfo(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void requestAd(IBinder iBinder, AdRequest adRequest, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(adRequest, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void notifyAdBufferReady(IBinder iBinder, AdBuffer adBuffer, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(adBuffer, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setTvMessageEnabled(IBinder iBinder, int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<TvInputHardwareInfo> getHardwareList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(TvInputHardwareInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public ITvInputHardware acquireTvInputHardware(int i, ITvInputHardwareCallback iTvInputHardwareCallback, TvInputInfo tvInputInfo, int i2, String str, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iTvInputHardwareCallback);
                    parcelObtain.writeTypedObject(tvInputInfo, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ITvInputHardware.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void releaseTvInputHardware(int i, ITvInputHardware iTvInputHardware, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iTvInputHardware);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<TvStreamConfig> getAvailableTvStreamConfigList(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(TvStreamConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean captureFrame(String str, Surface surface, TvStreamConfig tvStreamConfig, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(surface, 0);
                    parcelObtain.writeTypedObject(tvStreamConfig, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean isSingleSessionActive(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public List<DvbDeviceInfo> getDvbDeviceList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(DvbDeviceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public ParcelFileDescriptor openDvbDevice(DvbDeviceInfo dvbDeviceInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(dvbDeviceInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void sendTvInputNotifyIntent(Intent intent, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void requestChannelBrowsable(Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void addHardwareDevice(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeHardwareDevice(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setVideoFrozen(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void notifyTvAdSessionData(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
