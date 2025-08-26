package android.media.tv.interactive;

import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.AdBuffer;
import android.media.tv.AdResponse;
import android.media.tv.BroadcastInfoResponse;
import android.media.tv.TvRecordingInfo;
import android.media.tv.TvTrackInfo;
import android.media.tv.interactive.ITvInteractiveAppClient;
import android.media.tv.interactive.ITvInteractiveAppManagerCallback;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface ITvInteractiveAppManager extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppManager";

    public static class Default implements ITvInteractiveAppManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void createBiInteractiveApp(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void createMediaView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void createSession(ITvInteractiveAppClient iTvInteractiveAppClient, String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void destroyBiInteractiveApp(IBinder iBinder, String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public List<AppLinkInfo> getAppLinkInfoList(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public List<TvInteractiveAppServiceInfo> getTvInteractiveAppServiceList(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyAdBufferConsumed(IBinder iBinder, AdBuffer adBuffer, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyAdResponse(IBinder iBinder, AdResponse adResponse, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyBroadcastInfoResponse(IBinder iBinder, BroadcastInfoResponse broadcastInfoResponse, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyContentAllowed(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyContentBlocked(IBinder iBinder, String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyError(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingConnectionFailed(IBinder iBinder, String str, String str2, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingDisconnected(IBinder iBinder, String str, String str2, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingError(IBinder iBinder, String str, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingScheduled(IBinder iBinder, String str, String str2, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingStarted(IBinder iBinder, String str, String str2, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingStopped(IBinder iBinder, String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingTuned(IBinder iBinder, String str, Uri uri, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifySignalStrength(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTimeShiftCurrentPositionChanged(IBinder iBinder, String str, long j, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTimeShiftPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTimeShiftStartPositionChanged(IBinder iBinder, String str, long j, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTimeShiftStatusChanged(IBinder iBinder, String str, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTrackSelected(IBinder iBinder, int i, String str, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTracksChanged(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTuned(IBinder iBinder, Uri uri, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyVideoAvailable(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyVideoFreezeUpdated(IBinder iBinder, boolean z, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyVideoUnavailable(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void registerAppLinkInfo(String str, AppLinkInfo appLinkInfo, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void registerCallback(ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void relayoutMediaView(IBinder iBinder, Rect rect, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void releaseSession(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void removeMediaView(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void resetInteractiveApp(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendAppLinkCommand(String str, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendAvailableSpeeds(IBinder iBinder, float[] fArr, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendCertificate(IBinder iBinder, String str, int i, Bundle bundle, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendCurrentChannelLcn(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendCurrentChannelUri(IBinder iBinder, Uri uri, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendCurrentTvInputId(IBinder iBinder, String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendCurrentVideoBounds(IBinder iBinder, Rect rect, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendSelectedTrackInfo(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendSigningResult(IBinder iBinder, String str, byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendStreamVolume(IBinder iBinder, float f, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendTimeShiftMode(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendTrackInfoList(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendTvRecordingInfo(IBinder iBinder, TvRecordingInfo tvRecordingInfo, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendTvRecordingInfoList(IBinder iBinder, List<TvRecordingInfo> list, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void setTeletextAppEnabled(IBinder iBinder, boolean z, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void startInteractiveApp(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void stopInteractiveApp(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void unregisterAppLinkInfo(String str, AppLinkInfo appLinkInfo, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void unregisterCallback(ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws RemoteException {
        }
    }

    void createBiInteractiveApp(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException;

    void createMediaView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException;

    void createSession(ITvInteractiveAppClient iTvInteractiveAppClient, String str, int i, int i2, int i3) throws RemoteException;

    void destroyBiInteractiveApp(IBinder iBinder, String str, int i) throws RemoteException;

    void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException;

    List<AppLinkInfo> getAppLinkInfoList(int i) throws RemoteException;

    List<TvInteractiveAppServiceInfo> getTvInteractiveAppServiceList(int i) throws RemoteException;

    void notifyAdBufferConsumed(IBinder iBinder, AdBuffer adBuffer, int i) throws RemoteException;

    void notifyAdResponse(IBinder iBinder, AdResponse adResponse, int i) throws RemoteException;

    void notifyBroadcastInfoResponse(IBinder iBinder, BroadcastInfoResponse broadcastInfoResponse, int i) throws RemoteException;

    void notifyContentAllowed(IBinder iBinder, int i) throws RemoteException;

    void notifyContentBlocked(IBinder iBinder, String str, int i) throws RemoteException;

    void notifyError(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException;

    void notifyRecordingConnectionFailed(IBinder iBinder, String str, String str2, int i) throws RemoteException;

    void notifyRecordingDisconnected(IBinder iBinder, String str, String str2, int i) throws RemoteException;

    void notifyRecordingError(IBinder iBinder, String str, int i, int i2) throws RemoteException;

    void notifyRecordingScheduled(IBinder iBinder, String str, String str2, int i) throws RemoteException;

    void notifyRecordingStarted(IBinder iBinder, String str, String str2, int i) throws RemoteException;

    void notifyRecordingStopped(IBinder iBinder, String str, int i) throws RemoteException;

    void notifyRecordingTuned(IBinder iBinder, String str, Uri uri, int i) throws RemoteException;

    void notifySignalStrength(IBinder iBinder, int i, int i2) throws RemoteException;

    void notifyTimeShiftCurrentPositionChanged(IBinder iBinder, String str, long j, int i) throws RemoteException;

    void notifyTimeShiftPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int i) throws RemoteException;

    void notifyTimeShiftStartPositionChanged(IBinder iBinder, String str, long j, int i) throws RemoteException;

    void notifyTimeShiftStatusChanged(IBinder iBinder, String str, int i, int i2) throws RemoteException;

    void notifyTrackSelected(IBinder iBinder, int i, String str, int i2) throws RemoteException;

    void notifyTracksChanged(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException;

    void notifyTuned(IBinder iBinder, Uri uri, int i) throws RemoteException;

    void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException;

    void notifyVideoAvailable(IBinder iBinder, int i) throws RemoteException;

    void notifyVideoFreezeUpdated(IBinder iBinder, boolean z, int i) throws RemoteException;

    void notifyVideoUnavailable(IBinder iBinder, int i, int i2) throws RemoteException;

    void registerAppLinkInfo(String str, AppLinkInfo appLinkInfo, int i) throws RemoteException;

    void registerCallback(ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws RemoteException;

    void relayoutMediaView(IBinder iBinder, Rect rect, int i) throws RemoteException;

    void releaseSession(IBinder iBinder, int i) throws RemoteException;

    void removeMediaView(IBinder iBinder, int i) throws RemoteException;

    void resetInteractiveApp(IBinder iBinder, int i) throws RemoteException;

    void sendAppLinkCommand(String str, Bundle bundle, int i) throws RemoteException;

    void sendAvailableSpeeds(IBinder iBinder, float[] fArr, int i) throws RemoteException;

    void sendCertificate(IBinder iBinder, String str, int i, Bundle bundle, int i2) throws RemoteException;

    void sendCurrentChannelLcn(IBinder iBinder, int i, int i2) throws RemoteException;

    void sendCurrentChannelUri(IBinder iBinder, Uri uri, int i) throws RemoteException;

    void sendCurrentTvInputId(IBinder iBinder, String str, int i) throws RemoteException;

    void sendCurrentVideoBounds(IBinder iBinder, Rect rect, int i) throws RemoteException;

    void sendSelectedTrackInfo(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException;

    void sendSigningResult(IBinder iBinder, String str, byte[] bArr, int i) throws RemoteException;

    void sendStreamVolume(IBinder iBinder, float f, int i) throws RemoteException;

    void sendTimeShiftMode(IBinder iBinder, int i, int i2) throws RemoteException;

    void sendTrackInfoList(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException;

    void sendTvRecordingInfo(IBinder iBinder, TvRecordingInfo tvRecordingInfo, int i) throws RemoteException;

    void sendTvRecordingInfoList(IBinder iBinder, List<TvRecordingInfo> list, int i) throws RemoteException;

    void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException;

    void setTeletextAppEnabled(IBinder iBinder, boolean z, int i) throws RemoteException;

    void startInteractiveApp(IBinder iBinder, int i) throws RemoteException;

    void stopInteractiveApp(IBinder iBinder, int i) throws RemoteException;

    void unregisterAppLinkInfo(String str, AppLinkInfo appLinkInfo, int i) throws RemoteException;

    void unregisterCallback(ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInteractiveAppManager {
        static final int TRANSACTION_createBiInteractiveApp = 9;
        static final int TRANSACTION_createMediaView = 54;
        static final int TRANSACTION_createSession = 34;
        static final int TRANSACTION_destroyBiInteractiveApp = 10;
        static final int TRANSACTION_dispatchSurfaceChanged = 49;
        static final int TRANSACTION_getAppLinkInfoList = 2;
        static final int TRANSACTION_getTvInteractiveAppServiceList = 1;
        static final int TRANSACTION_notifyAdBufferConsumed = 52;
        static final int TRANSACTION_notifyAdResponse = 51;
        static final int TRANSACTION_notifyBroadcastInfoResponse = 50;
        static final int TRANSACTION_notifyContentAllowed = 42;
        static final int TRANSACTION_notifyContentBlocked = 43;
        static final int TRANSACTION_notifyError = 24;
        static final int TRANSACTION_notifyRecordingConnectionFailed = 29;
        static final int TRANSACTION_notifyRecordingDisconnected = 30;
        static final int TRANSACTION_notifyRecordingError = 32;
        static final int TRANSACTION_notifyRecordingScheduled = 33;
        static final int TRANSACTION_notifyRecordingStarted = 45;
        static final int TRANSACTION_notifyRecordingStopped = 46;
        static final int TRANSACTION_notifyRecordingTuned = 31;
        static final int TRANSACTION_notifySignalStrength = 44;
        static final int TRANSACTION_notifyTimeShiftCurrentPositionChanged = 28;
        static final int TRANSACTION_notifyTimeShiftPlaybackParams = 25;
        static final int TRANSACTION_notifyTimeShiftStartPositionChanged = 27;
        static final int TRANSACTION_notifyTimeShiftStatusChanged = 26;
        static final int TRANSACTION_notifyTrackSelected = 37;
        static final int TRANSACTION_notifyTracksChanged = 38;
        static final int TRANSACTION_notifyTuned = 36;
        static final int TRANSACTION_notifyTvMessage = 47;
        static final int TRANSACTION_notifyVideoAvailable = 39;
        static final int TRANSACTION_notifyVideoFreezeUpdated = 41;
        static final int TRANSACTION_notifyVideoUnavailable = 40;
        static final int TRANSACTION_registerAppLinkInfo = 3;
        static final int TRANSACTION_registerCallback = 57;
        static final int TRANSACTION_relayoutMediaView = 55;
        static final int TRANSACTION_releaseSession = 35;
        static final int TRANSACTION_removeMediaView = 56;
        static final int TRANSACTION_resetInteractiveApp = 8;
        static final int TRANSACTION_sendAppLinkCommand = 5;
        static final int TRANSACTION_sendAvailableSpeeds = 19;
        static final int TRANSACTION_sendCertificate = 21;
        static final int TRANSACTION_sendCurrentChannelLcn = 14;
        static final int TRANSACTION_sendCurrentChannelUri = 13;
        static final int TRANSACTION_sendCurrentTvInputId = 17;
        static final int TRANSACTION_sendCurrentVideoBounds = 12;
        static final int TRANSACTION_sendSelectedTrackInfo = 53;
        static final int TRANSACTION_sendSigningResult = 20;
        static final int TRANSACTION_sendStreamVolume = 15;
        static final int TRANSACTION_sendTimeShiftMode = 18;
        static final int TRANSACTION_sendTrackInfoList = 16;
        static final int TRANSACTION_sendTvRecordingInfo = 22;
        static final int TRANSACTION_sendTvRecordingInfoList = 23;
        static final int TRANSACTION_setSurface = 48;
        static final int TRANSACTION_setTeletextAppEnabled = 11;
        static final int TRANSACTION_startInteractiveApp = 6;
        static final int TRANSACTION_stopInteractiveApp = 7;
        static final int TRANSACTION_unregisterAppLinkInfo = 4;
        static final int TRANSACTION_unregisterCallback = 58;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 57;
        }

        public Stub() {
            attachInterface(this, ITvInteractiveAppManager.DESCRIPTOR);
        }

        public static ITvInteractiveAppManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITvInteractiveAppManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvInteractiveAppManager)) {
                return (ITvInteractiveAppManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTvInteractiveAppServiceList";
                case 2:
                    return "getAppLinkInfoList";
                case 3:
                    return "registerAppLinkInfo";
                case 4:
                    return "unregisterAppLinkInfo";
                case 5:
                    return "sendAppLinkCommand";
                case 6:
                    return "startInteractiveApp";
                case 7:
                    return "stopInteractiveApp";
                case 8:
                    return "resetInteractiveApp";
                case 9:
                    return "createBiInteractiveApp";
                case 10:
                    return "destroyBiInteractiveApp";
                case 11:
                    return "setTeletextAppEnabled";
                case 12:
                    return "sendCurrentVideoBounds";
                case 13:
                    return "sendCurrentChannelUri";
                case 14:
                    return "sendCurrentChannelLcn";
                case 15:
                    return "sendStreamVolume";
                case 16:
                    return "sendTrackInfoList";
                case 17:
                    return "sendCurrentTvInputId";
                case 18:
                    return "sendTimeShiftMode";
                case 19:
                    return "sendAvailableSpeeds";
                case 20:
                    return "sendSigningResult";
                case 21:
                    return "sendCertificate";
                case 22:
                    return "sendTvRecordingInfo";
                case 23:
                    return "sendTvRecordingInfoList";
                case 24:
                    return "notifyError";
                case 25:
                    return "notifyTimeShiftPlaybackParams";
                case 26:
                    return "notifyTimeShiftStatusChanged";
                case 27:
                    return "notifyTimeShiftStartPositionChanged";
                case 28:
                    return "notifyTimeShiftCurrentPositionChanged";
                case 29:
                    return "notifyRecordingConnectionFailed";
                case 30:
                    return "notifyRecordingDisconnected";
                case 31:
                    return "notifyRecordingTuned";
                case 32:
                    return "notifyRecordingError";
                case 33:
                    return "notifyRecordingScheduled";
                case 34:
                    return "createSession";
                case 35:
                    return "releaseSession";
                case 36:
                    return "notifyTuned";
                case 37:
                    return "notifyTrackSelected";
                case 38:
                    return "notifyTracksChanged";
                case 39:
                    return "notifyVideoAvailable";
                case 40:
                    return "notifyVideoUnavailable";
                case 41:
                    return "notifyVideoFreezeUpdated";
                case 42:
                    return "notifyContentAllowed";
                case 43:
                    return "notifyContentBlocked";
                case 44:
                    return "notifySignalStrength";
                case 45:
                    return "notifyRecordingStarted";
                case 46:
                    return "notifyRecordingStopped";
                case 47:
                    return "notifyTvMessage";
                case 48:
                    return "setSurface";
                case 49:
                    return "dispatchSurfaceChanged";
                case 50:
                    return "notifyBroadcastInfoResponse";
                case 51:
                    return "notifyAdResponse";
                case 52:
                    return "notifyAdBufferConsumed";
                case 53:
                    return "sendSelectedTrackInfo";
                case 54:
                    return "createMediaView";
                case 55:
                    return "relayoutMediaView";
                case 56:
                    return "removeMediaView";
                case 57:
                    return "registerCallback";
                case 58:
                    return "unregisterCallback";
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
                parcel.enforceInterface(ITvInteractiveAppManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvInteractiveAppManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<TvInteractiveAppServiceInfo> tvInteractiveAppServiceList = getTvInteractiveAppServiceList(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tvInteractiveAppServiceList, 1);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AppLinkInfo> appLinkInfoList = getAppLinkInfoList(i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(appLinkInfoList, 1);
                    return true;
                case 3:
                    String string = parcel.readString();
                    AppLinkInfo appLinkInfo = (AppLinkInfo) parcel.readTypedObject(AppLinkInfo.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerAppLinkInfo(string, appLinkInfo, i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string2 = parcel.readString();
                    AppLinkInfo appLinkInfo2 = (AppLinkInfo) parcel.readTypedObject(AppLinkInfo.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterAppLinkInfo(string2, appLinkInfo2, i6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string3 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAppLinkCommand(string3, bundle, i7);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startInteractiveApp(strongBinder, i8);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopInteractiveApp(strongBinder2, i9);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetInteractiveApp(strongBinder3, i10);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createBiInteractiveApp(strongBinder4, uri, bundle2, i11);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyBiInteractiveApp(strongBinder5, string4, i12);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    boolean z = parcel.readBoolean();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTeletextAppEnabled(strongBinder6, z, i13);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentVideoBounds(strongBinder7, rect, i14);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentChannelUri(strongBinder8, uri2, i15);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentChannelLcn(strongBinder9, i16, i17);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    float f = parcel.readFloat();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendStreamVolume(strongBinder10, f, i18);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(TvTrackInfo.CREATOR);
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTrackInfoList(strongBinder11, arrayListCreateTypedArrayList, i19);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    String string5 = parcel.readString();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentTvInputId(strongBinder12, string5, i20);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTimeShiftMode(strongBinder13, i21, i22);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    float[] fArrCreateFloatArray = parcel.createFloatArray();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAvailableSpeeds(strongBinder14, fArrCreateFloatArray, i23);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    String string6 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSigningResult(strongBinder15, string6, bArrCreateByteArray, i24);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    String string7 = parcel.readString();
                    int i25 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCertificate(strongBinder16, string7, i25, bundle3, i26);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    TvRecordingInfo tvRecordingInfo = (TvRecordingInfo) parcel.readTypedObject(TvRecordingInfo.CREATOR);
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTvRecordingInfo(strongBinder17, tvRecordingInfo, i27);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(TvRecordingInfo.CREATOR);
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTvRecordingInfoList(strongBinder18, arrayListCreateTypedArrayList2, i28);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IBinder strongBinder19 = parcel.readStrongBinder();
                    String string8 = parcel.readString();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyError(strongBinder19, string8, bundle4, i29);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IBinder strongBinder20 = parcel.readStrongBinder();
                    PlaybackParams playbackParams = (PlaybackParams) parcel.readTypedObject(PlaybackParams.CREATOR);
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftPlaybackParams(strongBinder20, playbackParams, i30);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IBinder strongBinder21 = parcel.readStrongBinder();
                    String string9 = parcel.readString();
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftStatusChanged(strongBinder21, string9, i31, i32);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IBinder strongBinder22 = parcel.readStrongBinder();
                    String string10 = parcel.readString();
                    long j = parcel.readLong();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftStartPositionChanged(strongBinder22, string10, j, i33);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IBinder strongBinder23 = parcel.readStrongBinder();
                    String string11 = parcel.readString();
                    long j2 = parcel.readLong();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftCurrentPositionChanged(strongBinder23, string11, j2, i34);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    IBinder strongBinder24 = parcel.readStrongBinder();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingConnectionFailed(strongBinder24, string12, string13, i35);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IBinder strongBinder25 = parcel.readStrongBinder();
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingDisconnected(strongBinder25, string14, string15, i36);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IBinder strongBinder26 = parcel.readStrongBinder();
                    String string16 = parcel.readString();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingTuned(strongBinder26, string16, uri3, i37);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    IBinder strongBinder27 = parcel.readStrongBinder();
                    String string17 = parcel.readString();
                    int i38 = parcel.readInt();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingError(strongBinder27, string17, i38, i39);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IBinder strongBinder28 = parcel.readStrongBinder();
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingScheduled(strongBinder28, string18, string19, i40);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    ITvInteractiveAppClient iTvInteractiveAppClientAsInterface = ITvInteractiveAppClient.Stub.asInterface(parcel.readStrongBinder());
                    String string20 = parcel.readString();
                    int i41 = parcel.readInt();
                    int i42 = parcel.readInt();
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSession(iTvInteractiveAppClientAsInterface, string20, i41, i42, i43);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    IBinder strongBinder29 = parcel.readStrongBinder();
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSession(strongBinder29, i44);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    IBinder strongBinder30 = parcel.readStrongBinder();
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTuned(strongBinder30, uri4, i45);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    IBinder strongBinder31 = parcel.readStrongBinder();
                    int i46 = parcel.readInt();
                    String string21 = parcel.readString();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTrackSelected(strongBinder31, i46, string21, i47);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IBinder strongBinder32 = parcel.readStrongBinder();
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(TvTrackInfo.CREATOR);
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTracksChanged(strongBinder32, arrayListCreateTypedArrayList3, i48);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    IBinder strongBinder33 = parcel.readStrongBinder();
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVideoAvailable(strongBinder33, i49);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IBinder strongBinder34 = parcel.readStrongBinder();
                    int i50 = parcel.readInt();
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVideoUnavailable(strongBinder34, i50, i51);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    IBinder strongBinder35 = parcel.readStrongBinder();
                    boolean z2 = parcel.readBoolean();
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVideoFreezeUpdated(strongBinder35, z2, i52);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    IBinder strongBinder36 = parcel.readStrongBinder();
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyContentAllowed(strongBinder36, i53);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    IBinder strongBinder37 = parcel.readStrongBinder();
                    String string22 = parcel.readString();
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyContentBlocked(strongBinder37, string22, i54);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IBinder strongBinder38 = parcel.readStrongBinder();
                    int i55 = parcel.readInt();
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySignalStrength(strongBinder38, i55, i56);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    IBinder strongBinder39 = parcel.readStrongBinder();
                    String string23 = parcel.readString();
                    String string24 = parcel.readString();
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingStarted(strongBinder39, string23, string24, i57);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    IBinder strongBinder40 = parcel.readStrongBinder();
                    String string25 = parcel.readString();
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingStopped(strongBinder40, string25, i58);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    IBinder strongBinder41 = parcel.readStrongBinder();
                    int i59 = parcel.readInt();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(strongBinder41, i59, bundle5, i60);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    IBinder strongBinder42 = parcel.readStrongBinder();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSurface(strongBinder42, surface, i61);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    IBinder strongBinder43 = parcel.readStrongBinder();
                    int i62 = parcel.readInt();
                    int i63 = parcel.readInt();
                    int i64 = parcel.readInt();
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(strongBinder43, i62, i63, i64, i65);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    IBinder strongBinder44 = parcel.readStrongBinder();
                    BroadcastInfoResponse broadcastInfoResponse = (BroadcastInfoResponse) parcel.readTypedObject(BroadcastInfoResponse.CREATOR);
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyBroadcastInfoResponse(strongBinder44, broadcastInfoResponse, i66);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IBinder strongBinder45 = parcel.readStrongBinder();
                    AdResponse adResponse = (AdResponse) parcel.readTypedObject(AdResponse.CREATOR);
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAdResponse(strongBinder45, adResponse, i67);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    IBinder strongBinder46 = parcel.readStrongBinder();
                    AdBuffer adBuffer = (AdBuffer) parcel.readTypedObject(AdBuffer.CREATOR);
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAdBufferConsumed(strongBinder46, adBuffer, i68);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    IBinder strongBinder47 = parcel.readStrongBinder();
                    ArrayList arrayListCreateTypedArrayList4 = parcel.createTypedArrayList(TvTrackInfo.CREATOR);
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSelectedTrackInfo(strongBinder47, arrayListCreateTypedArrayList4, i69);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    IBinder strongBinder48 = parcel.readStrongBinder();
                    IBinder strongBinder49 = parcel.readStrongBinder();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createMediaView(strongBinder48, strongBinder49, rect2, i70);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    IBinder strongBinder50 = parcel.readStrongBinder();
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    relayoutMediaView(strongBinder50, rect3, i71);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    IBinder strongBinder51 = parcel.readStrongBinder();
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeMediaView(strongBinder51, i72);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallbackAsInterface = ITvInteractiveAppManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(iTvInteractiveAppManagerCallbackAsInterface, i73);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallbackAsInterface2 = ITvInteractiveAppManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCallback(iTvInteractiveAppManagerCallbackAsInterface2, i74);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvInteractiveAppManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvInteractiveAppManager.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public List<TvInteractiveAppServiceInfo> getTvInteractiveAppServiceList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(TvInteractiveAppServiceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public List<AppLinkInfo> getAppLinkInfoList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppLinkInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void registerAppLinkInfo(String str, AppLinkInfo appLinkInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(appLinkInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void unregisterAppLinkInfo(String str, AppLinkInfo appLinkInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(appLinkInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendAppLinkCommand(String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void startInteractiveApp(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void stopInteractiveApp(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void resetInteractiveApp(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void createBiInteractiveApp(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void destroyBiInteractiveApp(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void setTeletextAppEnabled(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendCurrentVideoBounds(IBinder iBinder, Rect rect, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendCurrentChannelUri(IBinder iBinder, Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendCurrentChannelLcn(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendStreamVolume(IBinder iBinder, float f, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendTrackInfoList(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendCurrentTvInputId(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendTimeShiftMode(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendAvailableSpeeds(IBinder iBinder, float[] fArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeFloatArray(fArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendSigningResult(IBinder iBinder, String str, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendCertificate(IBinder iBinder, String str, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendTvRecordingInfo(IBinder iBinder, TvRecordingInfo tvRecordingInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(tvRecordingInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendTvRecordingInfoList(IBinder iBinder, List<TvRecordingInfo> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyError(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTimeShiftPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(playbackParams, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTimeShiftStatusChanged(IBinder iBinder, String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTimeShiftStartPositionChanged(IBinder iBinder, String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTimeShiftCurrentPositionChanged(IBinder iBinder, String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingConnectionFailed(IBinder iBinder, String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingDisconnected(IBinder iBinder, String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingTuned(IBinder iBinder, String str, Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingError(IBinder iBinder, String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingScheduled(IBinder iBinder, String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void createSession(ITvInteractiveAppClient iTvInteractiveAppClient, String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInteractiveAppClient);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void releaseSession(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTuned(IBinder iBinder, Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTrackSelected(IBinder iBinder, int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTracksChanged(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyVideoAvailable(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyVideoUnavailable(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
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

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyVideoFreezeUpdated(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
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

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyContentAllowed(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyContentBlocked(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifySignalStrength(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingStarted(IBinder iBinder, String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingStopped(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(surface, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyBroadcastInfoResponse(IBinder iBinder, BroadcastInfoResponse broadcastInfoResponse, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(broadcastInfoResponse, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyAdResponse(IBinder iBinder, AdResponse adResponse, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(adResponse, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyAdBufferConsumed(IBinder iBinder, AdBuffer adBuffer, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
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

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendSelectedTrackInfo(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void createMediaView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void relayoutMediaView(IBinder iBinder, Rect rect, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void removeMediaView(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void registerCallback(ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInteractiveAppManagerCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void unregisterCallback(ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInteractiveAppManagerCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
