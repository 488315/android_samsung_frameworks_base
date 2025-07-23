package android.media.tv.interactive;

import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.AdBuffer;
import android.media.tv.AdResponse;
import android.media.tv.BroadcastInfoResponse;
import android.media.tv.TvRecordingInfo;
import android.media.tv.TvTrackInfo;
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
public interface ITvInteractiveAppSession extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppSession";

    public static class Default implements ITvInteractiveAppSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void createBiInteractiveApp(Uri uri, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void createMediaView(IBinder iBinder, Rect rect) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void destroyBiInteractiveApp(String str) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void dispatchSurfaceChanged(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyAdBufferConsumed(AdBuffer adBuffer) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyAdResponse(AdResponse adResponse) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyContentAllowed() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyContentBlocked(String str) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyError(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyRecordingConnectionFailed(String str, String str2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyRecordingDisconnected(String str, String str2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyRecordingError(String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyRecordingScheduled(String str, String str2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyRecordingStarted(String str, String str2) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyRecordingStopped(String str) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyRecordingTuned(String str, Uri uri) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifySignalStrength(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyTimeShiftCurrentPositionChanged(String str, long j) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyTimeShiftPlaybackParams(PlaybackParams playbackParams) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyTimeShiftStartPositionChanged(String str, long j) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyTimeShiftStatusChanged(String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyTrackSelected(int i, String str) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyTracksChanged(List<TvTrackInfo> list) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyTuned(Uri uri) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyTvMessage(int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyVideoAvailable() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyVideoFreezeUpdated(boolean z) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void notifyVideoUnavailable(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void relayoutMediaView(Rect rect) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void release() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void removeMediaView() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void resetInteractiveApp() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendAvailableSpeeds(float[] fArr) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendCertificate(String str, int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendCurrentChannelLcn(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendCurrentChannelUri(Uri uri) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendCurrentTvInputId(String str) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendCurrentVideoBounds(Rect rect) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendSelectedTrackInfo(List<TvTrackInfo> list) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendSigningResult(String str, byte[] bArr) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendStreamVolume(float f) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendTimeShiftMode(int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendTrackInfoList(List<TvTrackInfo> list) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendTvRecordingInfo(TvRecordingInfo tvRecordingInfo) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void sendTvRecordingInfoList(List<TvRecordingInfo> list) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void setSurface(Surface surface) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void setTeletextAppEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void startInteractiveApp() throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSession
        public void stopInteractiveApp() throws RemoteException {
        }
    }

    void createBiInteractiveApp(Uri uri, Bundle bundle) throws RemoteException;

    void createMediaView(IBinder iBinder, Rect rect) throws RemoteException;

    void destroyBiInteractiveApp(String str) throws RemoteException;

    void dispatchSurfaceChanged(int i, int i2, int i3) throws RemoteException;

    void notifyAdBufferConsumed(AdBuffer adBuffer) throws RemoteException;

    void notifyAdResponse(AdResponse adResponse) throws RemoteException;

    void notifyBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) throws RemoteException;

    void notifyContentAllowed() throws RemoteException;

    void notifyContentBlocked(String str) throws RemoteException;

    void notifyError(String str, Bundle bundle) throws RemoteException;

    void notifyRecordingConnectionFailed(String str, String str2) throws RemoteException;

    void notifyRecordingDisconnected(String str, String str2) throws RemoteException;

    void notifyRecordingError(String str, int i) throws RemoteException;

    void notifyRecordingScheduled(String str, String str2) throws RemoteException;

    void notifyRecordingStarted(String str, String str2) throws RemoteException;

    void notifyRecordingStopped(String str) throws RemoteException;

    void notifyRecordingTuned(String str, Uri uri) throws RemoteException;

    void notifySignalStrength(int i) throws RemoteException;

    void notifyTimeShiftCurrentPositionChanged(String str, long j) throws RemoteException;

    void notifyTimeShiftPlaybackParams(PlaybackParams playbackParams) throws RemoteException;

    void notifyTimeShiftStartPositionChanged(String str, long j) throws RemoteException;

    void notifyTimeShiftStatusChanged(String str, int i) throws RemoteException;

    void notifyTrackSelected(int i, String str) throws RemoteException;

    void notifyTracksChanged(List<TvTrackInfo> list) throws RemoteException;

    void notifyTuned(Uri uri) throws RemoteException;

    void notifyTvMessage(int i, Bundle bundle) throws RemoteException;

    void notifyVideoAvailable() throws RemoteException;

    void notifyVideoFreezeUpdated(boolean z) throws RemoteException;

    void notifyVideoUnavailable(int i) throws RemoteException;

    void relayoutMediaView(Rect rect) throws RemoteException;

    void release() throws RemoteException;

    void removeMediaView() throws RemoteException;

    void resetInteractiveApp() throws RemoteException;

    void sendAvailableSpeeds(float[] fArr) throws RemoteException;

    void sendCertificate(String str, int i, Bundle bundle) throws RemoteException;

    void sendCurrentChannelLcn(int i) throws RemoteException;

    void sendCurrentChannelUri(Uri uri) throws RemoteException;

    void sendCurrentTvInputId(String str) throws RemoteException;

    void sendCurrentVideoBounds(Rect rect) throws RemoteException;

    void sendSelectedTrackInfo(List<TvTrackInfo> list) throws RemoteException;

    void sendSigningResult(String str, byte[] bArr) throws RemoteException;

    void sendStreamVolume(float f) throws RemoteException;

    void sendTimeShiftMode(int i) throws RemoteException;

    void sendTrackInfoList(List<TvTrackInfo> list) throws RemoteException;

    void sendTvRecordingInfo(TvRecordingInfo tvRecordingInfo) throws RemoteException;

    void sendTvRecordingInfoList(List<TvRecordingInfo> list) throws RemoteException;

    void setSurface(Surface surface) throws RemoteException;

    void setTeletextAppEnabled(boolean z) throws RemoteException;

    void startInteractiveApp() throws RemoteException;

    void stopInteractiveApp() throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInteractiveAppSession {
        static final int TRANSACTION_createBiInteractiveApp = 4;
        static final int TRANSACTION_createMediaView = 48;
        static final int TRANSACTION_destroyBiInteractiveApp = 5;
        static final int TRANSACTION_dispatchSurfaceChanged = 43;
        static final int TRANSACTION_notifyAdBufferConsumed = 46;
        static final int TRANSACTION_notifyAdResponse = 45;
        static final int TRANSACTION_notifyBroadcastInfoResponse = 44;
        static final int TRANSACTION_notifyContentAllowed = 36;
        static final int TRANSACTION_notifyContentBlocked = 37;
        static final int TRANSACTION_notifyError = 19;
        static final int TRANSACTION_notifyRecordingConnectionFailed = 24;
        static final int TRANSACTION_notifyRecordingDisconnected = 25;
        static final int TRANSACTION_notifyRecordingError = 27;
        static final int TRANSACTION_notifyRecordingScheduled = 28;
        static final int TRANSACTION_notifyRecordingStarted = 39;
        static final int TRANSACTION_notifyRecordingStopped = 40;
        static final int TRANSACTION_notifyRecordingTuned = 26;
        static final int TRANSACTION_notifySignalStrength = 38;
        static final int TRANSACTION_notifyTimeShiftCurrentPositionChanged = 23;
        static final int TRANSACTION_notifyTimeShiftPlaybackParams = 20;
        static final int TRANSACTION_notifyTimeShiftStartPositionChanged = 22;
        static final int TRANSACTION_notifyTimeShiftStatusChanged = 21;
        static final int TRANSACTION_notifyTrackSelected = 31;
        static final int TRANSACTION_notifyTracksChanged = 32;
        static final int TRANSACTION_notifyTuned = 30;
        static final int TRANSACTION_notifyTvMessage = 41;
        static final int TRANSACTION_notifyVideoAvailable = 33;
        static final int TRANSACTION_notifyVideoFreezeUpdated = 35;
        static final int TRANSACTION_notifyVideoUnavailable = 34;
        static final int TRANSACTION_relayoutMediaView = 49;
        static final int TRANSACTION_release = 29;
        static final int TRANSACTION_removeMediaView = 50;
        static final int TRANSACTION_resetInteractiveApp = 3;
        static final int TRANSACTION_sendAvailableSpeeds = 14;
        static final int TRANSACTION_sendCertificate = 16;
        static final int TRANSACTION_sendCurrentChannelLcn = 9;
        static final int TRANSACTION_sendCurrentChannelUri = 8;
        static final int TRANSACTION_sendCurrentTvInputId = 12;
        static final int TRANSACTION_sendCurrentVideoBounds = 7;
        static final int TRANSACTION_sendSelectedTrackInfo = 47;
        static final int TRANSACTION_sendSigningResult = 15;
        static final int TRANSACTION_sendStreamVolume = 10;
        static final int TRANSACTION_sendTimeShiftMode = 13;
        static final int TRANSACTION_sendTrackInfoList = 11;
        static final int TRANSACTION_sendTvRecordingInfo = 17;
        static final int TRANSACTION_sendTvRecordingInfoList = 18;
        static final int TRANSACTION_setSurface = 42;
        static final int TRANSACTION_setTeletextAppEnabled = 6;
        static final int TRANSACTION_startInteractiveApp = 1;
        static final int TRANSACTION_stopInteractiveApp = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 49;
        }

        public Stub() {
            attachInterface(this, ITvInteractiveAppSession.DESCRIPTOR);
        }

        public static ITvInteractiveAppSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITvInteractiveAppSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvInteractiveAppSession)) {
                return (ITvInteractiveAppSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startInteractiveApp";
                case 2:
                    return "stopInteractiveApp";
                case 3:
                    return "resetInteractiveApp";
                case 4:
                    return "createBiInteractiveApp";
                case 5:
                    return "destroyBiInteractiveApp";
                case 6:
                    return "setTeletextAppEnabled";
                case 7:
                    return "sendCurrentVideoBounds";
                case 8:
                    return "sendCurrentChannelUri";
                case 9:
                    return "sendCurrentChannelLcn";
                case 10:
                    return "sendStreamVolume";
                case 11:
                    return "sendTrackInfoList";
                case 12:
                    return "sendCurrentTvInputId";
                case 13:
                    return "sendTimeShiftMode";
                case 14:
                    return "sendAvailableSpeeds";
                case 15:
                    return "sendSigningResult";
                case 16:
                    return "sendCertificate";
                case 17:
                    return "sendTvRecordingInfo";
                case 18:
                    return "sendTvRecordingInfoList";
                case 19:
                    return "notifyError";
                case 20:
                    return "notifyTimeShiftPlaybackParams";
                case 21:
                    return "notifyTimeShiftStatusChanged";
                case 22:
                    return "notifyTimeShiftStartPositionChanged";
                case 23:
                    return "notifyTimeShiftCurrentPositionChanged";
                case 24:
                    return "notifyRecordingConnectionFailed";
                case 25:
                    return "notifyRecordingDisconnected";
                case 26:
                    return "notifyRecordingTuned";
                case 27:
                    return "notifyRecordingError";
                case 28:
                    return "notifyRecordingScheduled";
                case 29:
                    return "release";
                case 30:
                    return "notifyTuned";
                case 31:
                    return "notifyTrackSelected";
                case 32:
                    return "notifyTracksChanged";
                case 33:
                    return "notifyVideoAvailable";
                case 34:
                    return "notifyVideoUnavailable";
                case 35:
                    return "notifyVideoFreezeUpdated";
                case 36:
                    return "notifyContentAllowed";
                case 37:
                    return "notifyContentBlocked";
                case 38:
                    return "notifySignalStrength";
                case 39:
                    return "notifyRecordingStarted";
                case 40:
                    return "notifyRecordingStopped";
                case 41:
                    return "notifyTvMessage";
                case 42:
                    return "setSurface";
                case 43:
                    return "dispatchSurfaceChanged";
                case 44:
                    return "notifyBroadcastInfoResponse";
                case 45:
                    return "notifyAdResponse";
                case 46:
                    return "notifyAdBufferConsumed";
                case 47:
                    return "sendSelectedTrackInfo";
                case 48:
                    return "createMediaView";
                case 49:
                    return "relayoutMediaView";
                case 50:
                    return "removeMediaView";
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
                parcel.enforceInterface(ITvInteractiveAppSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvInteractiveAppSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    startInteractiveApp();
                    return true;
                case 2:
                    stopInteractiveApp();
                    return true;
                case 3:
                    resetInteractiveApp();
                    return true;
                case 4:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    createBiInteractiveApp(uri, bundle);
                    return true;
                case 5:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyBiInteractiveApp(readString);
                    return true;
                case 6:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTeletextAppEnabled(readBoolean);
                    return true;
                case 7:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCurrentVideoBounds(rect);
                    return true;
                case 8:
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCurrentChannelUri(uri2);
                    return true;
                case 9:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentChannelLcn(readInt);
                    return true;
                case 10:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    sendStreamVolume(readFloat);
                    return true;
                case 11:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(TvTrackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendTrackInfoList(createTypedArrayList);
                    return true;
                case 12:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendCurrentTvInputId(readString2);
                    return true;
                case 13:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTimeShiftMode(readInt2);
                    return true;
                case 14:
                    float[] createFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    sendAvailableSpeeds(createFloatArray);
                    return true;
                case 15:
                    String readString3 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendSigningResult(readString3, createByteArray);
                    return true;
                case 16:
                    String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCertificate(readString4, readInt3, bundle2);
                    return true;
                case 17:
                    TvRecordingInfo tvRecordingInfo = (TvRecordingInfo) parcel.readTypedObject(TvRecordingInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendTvRecordingInfo(tvRecordingInfo);
                    return true;
                case 18:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(TvRecordingInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendTvRecordingInfoList(createTypedArrayList2);
                    return true;
                case 19:
                    String readString5 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyError(readString5, bundle3);
                    return true;
                case 20:
                    PlaybackParams playbackParams = (PlaybackParams) parcel.readTypedObject(PlaybackParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftPlaybackParams(playbackParams);
                    return true;
                case 21:
                    String readString6 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftStatusChanged(readString6, readInt4);
                    return true;
                case 22:
                    String readString7 = parcel.readString();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftStartPositionChanged(readString7, readLong);
                    return true;
                case 23:
                    String readString8 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftCurrentPositionChanged(readString8, readLong2);
                    return true;
                case 24:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyRecordingConnectionFailed(readString9, readString10);
                    return true;
                case 25:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyRecordingDisconnected(readString11, readString12);
                    return true;
                case 26:
                    String readString13 = parcel.readString();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyRecordingTuned(readString13, uri3);
                    return true;
                case 27:
                    String readString14 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingError(readString14, readInt5);
                    return true;
                case 28:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyRecordingScheduled(readString15, readString16);
                    return true;
                case 29:
                    release();
                    return true;
                case 30:
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTuned(uri4);
                    return true;
                case 31:
                    int readInt6 = parcel.readInt();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyTrackSelected(readInt6, readString17);
                    return true;
                case 32:
                    ArrayList createTypedArrayList3 = parcel.createTypedArrayList(TvTrackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTracksChanged(createTypedArrayList3);
                    return true;
                case 33:
                    notifyVideoAvailable();
                    return true;
                case 34:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVideoUnavailable(readInt7);
                    return true;
                case 35:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyVideoFreezeUpdated(readBoolean2);
                    return true;
                case 36:
                    notifyContentAllowed();
                    return true;
                case 37:
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyContentBlocked(readString18);
                    return true;
                case 38:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySignalStrength(readInt8);
                    return true;
                case 39:
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyRecordingStarted(readString19, readString20);
                    return true;
                case 40:
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyRecordingStopped(readString21);
                    return true;
                case 41:
                    int readInt9 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(readInt9, bundle4);
                    return true;
                case 42:
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSurface(surface);
                    return true;
                case 43:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(readInt10, readInt11, readInt12);
                    return true;
                case 44:
                    BroadcastInfoResponse broadcastInfoResponse = (BroadcastInfoResponse) parcel.readTypedObject(BroadcastInfoResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyBroadcastInfoResponse(broadcastInfoResponse);
                    return true;
                case 45:
                    AdResponse adResponse = (AdResponse) parcel.readTypedObject(AdResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyAdResponse(adResponse);
                    return true;
                case 46:
                    AdBuffer adBuffer = (AdBuffer) parcel.readTypedObject(AdBuffer.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyAdBufferConsumed(adBuffer);
                    return true;
                case 47:
                    ArrayList createTypedArrayList4 = parcel.createTypedArrayList(TvTrackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSelectedTrackInfo(createTypedArrayList4);
                    return true;
                case 48:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    createMediaView(readStrongBinder, rect2);
                    return true;
                case 49:
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    relayoutMediaView(rect3);
                    return true;
                case 50:
                    removeMediaView();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvInteractiveAppSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvInteractiveAppSession.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void startInteractiveApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void stopInteractiveApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void resetInteractiveApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void createBiInteractiveApp(Uri uri, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void destroyBiInteractiveApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void setTeletextAppEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendCurrentVideoBounds(Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendCurrentChannelUri(Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendCurrentChannelLcn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendStreamVolume(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendTrackInfoList(List<TvTrackInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendCurrentTvInputId(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendTimeShiftMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendAvailableSpeeds(float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendSigningResult(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendCertificate(String str, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendTvRecordingInfo(TvRecordingInfo tvRecordingInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedObject(tvRecordingInfo, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendTvRecordingInfoList(List<TvRecordingInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyError(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyTimeShiftPlaybackParams(PlaybackParams playbackParams) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedObject(playbackParams, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyTimeShiftStatusChanged(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyTimeShiftStartPositionChanged(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyTimeShiftCurrentPositionChanged(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyRecordingConnectionFailed(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyRecordingDisconnected(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyRecordingTuned(String str, Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyRecordingError(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyRecordingScheduled(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void release() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyTuned(Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyTrackSelected(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyTracksChanged(List<TvTrackInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyVideoAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyVideoUnavailable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyVideoFreezeUpdated(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyContentAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyContentBlocked(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifySignalStrength(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyRecordingStarted(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyRecordingStopped(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyTvMessage(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void setSurface(Surface surface) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void dispatchSurfaceChanged(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedObject(broadcastInfoResponse, 0);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyAdResponse(AdResponse adResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedObject(adResponse, 0);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void notifyAdBufferConsumed(AdBuffer adBuffer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedObject(adBuffer, 0);
                    this.mRemote.transact(46, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void sendSelectedTrackInfo(List<TvTrackInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(47, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void createMediaView(IBinder iBinder, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(48, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void relayoutMediaView(Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(49, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSession
            public void removeMediaView() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvInteractiveAppSession.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
