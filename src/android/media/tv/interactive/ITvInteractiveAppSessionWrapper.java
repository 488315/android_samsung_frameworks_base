package android.media.tv.interactive;

import android.content.Context;
import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.AdBuffer;
import android.media.tv.AdResponse;
import android.media.tv.BroadcastInfoResponse;
import android.media.tv.TvContentRating;
import android.media.tv.TvRecordingInfo;
import android.media.tv.TvTrackInfo;
import android.media.tv.interactive.ITvInteractiveAppSession;
import android.media.tv.interactive.TvInteractiveAppService;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.Surface;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import java.util.List;

/* loaded from: classes3.dex */
public class ITvInteractiveAppSessionWrapper extends ITvInteractiveAppSession.Stub implements HandlerCaller.Callback {
    private static final int DO_CREATE_BI_INTERACTIVE_APP = 5;
    private static final int DO_CREATE_MEDIA_VIEW = 27;
    private static final int DO_DESTROY_BI_INTERACTIVE_APP = 6;
    private static final int DO_DISPATCH_SURFACE_CHANGED = 24;
    private static final int DO_NOTIFY_AD_BUFFER_CONSUMED = 32;
    private static final int DO_NOTIFY_AD_RESPONSE = 26;
    private static final int DO_NOTIFY_BROADCAST_INFO_RESPONSE = 25;
    private static final int DO_NOTIFY_CONTENT_ALLOWED = 20;
    private static final int DO_NOTIFY_CONTENT_BLOCKED = 21;
    private static final int DO_NOTIFY_ERROR = 14;
    private static final int DO_NOTIFY_RECORDING_CONNECTION_FAILED = 41;
    private static final int DO_NOTIFY_RECORDING_DISCONNECTED = 42;
    private static final int DO_NOTIFY_RECORDING_ERROR = 44;
    private static final int DO_NOTIFY_RECORDING_SCHEDULED = 45;
    private static final int DO_NOTIFY_RECORDING_STARTED = 30;
    private static final int DO_NOTIFY_RECORDING_STOPPED = 31;
    private static final int DO_NOTIFY_RECORDING_TUNED = 43;
    private static final int DO_NOTIFY_SIGNAL_STRENGTH = 22;
    private static final int DO_NOTIFY_TIME_SHIFT_CURRENT_POSITION_CHANGED = 39;
    private static final int DO_NOTIFY_TIME_SHIFT_PLAYBACK_PARAMS = 36;
    private static final int DO_NOTIFY_TIME_SHIFT_START_POSITION_CHANGED = 38;
    private static final int DO_NOTIFY_TIME_SHIFT_STATUS_CHANGED = 37;
    private static final int DO_NOTIFY_TRACKS_CHANGED = 17;
    private static final int DO_NOTIFY_TRACK_SELECTED = 16;
    private static final int DO_NOTIFY_TUNED = 15;
    private static final int DO_NOTIFY_TV_MESSAGE = 33;
    private static final int DO_NOTIFY_VIDEO_AVAILABLE = 18;
    private static final int DO_NOTIFY_VIDEO_FREEZE_UPDATED = 49;
    private static final int DO_NOTIFY_VIDEO_UNAVAILABLE = 19;
    private static final int DO_RELAYOUT_MEDIA_VIEW = 28;
    private static final int DO_RELEASE = 1;
    private static final int DO_REMOVE_MEDIA_VIEW = 29;
    private static final int DO_RESET_INTERACTIVE_APP = 4;
    private static final int DO_SEND_AVAILABLE_SPEEDS = 47;
    private static final int DO_SEND_CERTIFICATE = 50;
    private static final int DO_SEND_CURRENT_CHANNEL_LCN = 9;
    private static final int DO_SEND_CURRENT_CHANNEL_URI = 8;
    private static final int DO_SEND_CURRENT_TV_INPUT_ID = 12;
    private static final int DO_SEND_CURRENT_VIDEO_BOUNDS = 40;
    private static final int DO_SEND_RECORDING_INFO = 34;
    private static final int DO_SEND_RECORDING_INFO_LIST = 35;
    private static final int DO_SEND_SELECTED_TRACK_INFO = 48;
    private static final int DO_SEND_SIGNING_RESULT = 13;
    private static final int DO_SEND_STREAM_VOLUME = 10;
    private static final int DO_SEND_TIME_SHIFT_MODE = 46;
    private static final int DO_SEND_TRACK_INFO_LIST = 11;
    private static final int DO_SET_SURFACE = 23;
    private static final int DO_SET_TELETEXT_APP_ENABLED = 7;
    private static final int DO_START_INTERACTIVE_APP = 2;
    private static final int DO_STOP_INTERACTIVE_APP = 3;
    private static final int EXECUTE_MESSAGE_TIMEOUT_LONG_MILLIS = 5000;
    private static final int EXECUTE_MESSAGE_TIMEOUT_SHORT_MILLIS = 1000;
    private static final String TAG = "ITvInteractiveAppSessionWrapper";
    private final HandlerCaller mCaller;
    private InputChannel mChannel;
    private TvInteractiveAppEventReceiver mReceiver;
    private TvInteractiveAppService.Session mSessionImpl;

    public ITvInteractiveAppSessionWrapper(Context context, TvInteractiveAppService.Session session, InputChannel inputChannel) {
        this.mSessionImpl = session;
        this.mCaller = new HandlerCaller(context, null, this, true);
        this.mChannel = inputChannel;
        if (inputChannel != null) {
            this.mReceiver = new TvInteractiveAppEventReceiver(inputChannel, context.getMainLooper());
        }
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(Message message) {
        if (this.mSessionImpl == null) {
            return;
        }
        long jNanoTime = System.nanoTime();
        switch (message.what) {
            case 1:
                this.mSessionImpl.release();
                this.mSessionImpl = null;
                TvInteractiveAppEventReceiver tvInteractiveAppEventReceiver = this.mReceiver;
                if (tvInteractiveAppEventReceiver != null) {
                    tvInteractiveAppEventReceiver.dispose();
                    this.mReceiver = null;
                }
                InputChannel inputChannel = this.mChannel;
                if (inputChannel != null) {
                    inputChannel.dispose();
                    this.mChannel = null;
                    break;
                }
                break;
            case 2:
                this.mSessionImpl.startInteractiveApp();
                break;
            case 3:
                this.mSessionImpl.stopInteractiveApp();
                break;
            case 4:
                this.mSessionImpl.resetInteractiveApp();
                break;
            case 5:
                SomeArgs someArgs = (SomeArgs) message.obj;
                this.mSessionImpl.createBiInteractiveApp((Uri) someArgs.arg1, (Bundle) someArgs.arg2);
                someArgs.recycle();
                break;
            case 6:
                this.mSessionImpl.destroyBiInteractiveApp((String) message.obj);
                break;
            case 7:
                this.mSessionImpl.setTeletextAppEnabled(((Boolean) message.obj).booleanValue());
                break;
            case 8:
                this.mSessionImpl.sendCurrentChannelUri((Uri) message.obj);
                break;
            case 9:
                this.mSessionImpl.sendCurrentChannelLcn(((Integer) message.obj).intValue());
                break;
            case 10:
                this.mSessionImpl.sendStreamVolume(((Float) message.obj).floatValue());
                break;
            case 11:
                this.mSessionImpl.sendTrackInfoList((List) message.obj);
                break;
            case 12:
                this.mSessionImpl.sendCurrentTvInputId((String) message.obj);
                break;
            case 13:
                SomeArgs someArgs2 = (SomeArgs) message.obj;
                this.mSessionImpl.sendSigningResult((String) someArgs2.arg1, (byte[]) someArgs2.arg2);
                someArgs2.recycle();
                break;
            case 14:
                SomeArgs someArgs3 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyError((String) someArgs3.arg1, (Bundle) someArgs3.arg2);
                someArgs3.recycle();
                break;
            case 15:
                this.mSessionImpl.notifyTuned((Uri) message.obj);
                break;
            case 16:
                SomeArgs someArgs4 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyTrackSelected(((Integer) someArgs4.arg1).intValue(), (String) someArgs4.arg2);
                someArgs4.recycle();
                break;
            case 17:
                this.mSessionImpl.notifyTracksChanged((List) message.obj);
                break;
            case 18:
                this.mSessionImpl.notifyVideoAvailable();
                break;
            case 19:
                this.mSessionImpl.notifyVideoUnavailable(((Integer) message.obj).intValue());
                break;
            case 20:
                this.mSessionImpl.notifyContentAllowed();
                break;
            case 21:
                this.mSessionImpl.notifyContentBlocked(TvContentRating.unflattenFromString((String) message.obj));
                break;
            case 22:
                this.mSessionImpl.notifySignalStrength(((Integer) message.obj).intValue());
                break;
            case 23:
                this.mSessionImpl.setSurface((Surface) message.obj);
                break;
            case 24:
                SomeArgs someArgs5 = (SomeArgs) message.obj;
                TvInteractiveAppService.Session session = this.mSessionImpl;
                int i = someArgs5.argi1;
                Integer.valueOf(i).getClass();
                int i2 = someArgs5.argi2;
                Integer.valueOf(i2).getClass();
                int i3 = someArgs5.argi3;
                Integer.valueOf(i3).getClass();
                session.dispatchSurfaceChanged(i, i2, i3);
                someArgs5.recycle();
                break;
            case 25:
                this.mSessionImpl.notifyBroadcastInfoResponse((BroadcastInfoResponse) message.obj);
                break;
            case 26:
                this.mSessionImpl.notifyAdResponse((AdResponse) message.obj);
                break;
            case 27:
                SomeArgs someArgs6 = (SomeArgs) message.obj;
                this.mSessionImpl.createMediaView((IBinder) someArgs6.arg1, (Rect) someArgs6.arg2);
                someArgs6.recycle();
                break;
            case 28:
                this.mSessionImpl.relayoutMediaView((Rect) message.obj);
                break;
            case 29:
                this.mSessionImpl.removeMediaView(true);
                break;
            case 30:
                SomeArgs someArgs7 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingStarted((String) someArgs7.arg1, (String) someArgs7.arg2);
                someArgs7.recycle();
                break;
            case 31:
                this.mSessionImpl.notifyRecordingStopped((String) message.obj);
                break;
            case 32:
                this.mSessionImpl.notifyAdBufferConsumed((AdBuffer) message.obj);
                break;
            case 33:
                SomeArgs someArgs8 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyTvMessage(((Integer) someArgs8.arg1).intValue(), (Bundle) someArgs8.arg2);
                someArgs8.recycle();
                break;
            case 34:
                this.mSessionImpl.sendTvRecordingInfo((TvRecordingInfo) message.obj);
                break;
            case 35:
                this.mSessionImpl.sendTvRecordingInfoList((List) message.obj);
                break;
            case 36:
                this.mSessionImpl.notifyTimeShiftPlaybackParams((PlaybackParams) message.obj);
                break;
            case 37:
                SomeArgs someArgs9 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyTimeShiftStatusChanged((String) someArgs9.arg1, ((Integer) someArgs9.arg2).intValue());
                someArgs9.recycle();
                break;
            case 38:
                SomeArgs someArgs10 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyTimeShiftStartPositionChanged((String) someArgs10.arg1, ((Long) someArgs10.arg2).longValue());
                someArgs10.recycle();
                break;
            case 39:
                SomeArgs someArgs11 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyTimeShiftCurrentPositionChanged((String) someArgs11.arg1, ((Long) someArgs11.arg2).longValue());
                someArgs11.recycle();
                break;
            case 40:
                this.mSessionImpl.sendCurrentVideoBounds((Rect) message.obj);
                break;
            case 41:
                SomeArgs someArgs12 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingConnectionFailed((String) someArgs12.arg1, (String) someArgs12.arg2);
                someArgs12.recycle();
                break;
            case 42:
                SomeArgs someArgs13 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingDisconnected((String) someArgs13.arg1, (String) someArgs13.arg2);
                someArgs13.recycle();
                break;
            case 43:
                SomeArgs someArgs14 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingTuned((String) someArgs14.arg1, (Uri) someArgs14.arg2);
                someArgs14.recycle();
                break;
            case 44:
                SomeArgs someArgs15 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingError((String) someArgs15.arg1, ((Integer) someArgs15.arg2).intValue());
                someArgs15.recycle();
                break;
            case 45:
                SomeArgs someArgs16 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingScheduled((String) someArgs16.arg1, (String) someArgs16.arg2);
                someArgs16.recycle();
                break;
            case 46:
                this.mSessionImpl.sendTimeShiftMode(((Integer) message.obj).intValue());
                break;
            case 47:
                this.mSessionImpl.sendAvailableSpeeds((float[]) message.obj);
                break;
            case 48:
                this.mSessionImpl.sendSelectedTrackInfo((List) message.obj);
                break;
            case 49:
                this.mSessionImpl.notifyVideoFreezeUpdated(((Boolean) message.obj).booleanValue());
                break;
            case 50:
                SomeArgs someArgs17 = (SomeArgs) message.obj;
                this.mSessionImpl.sendCertificate((String) someArgs17.arg1, ((Integer) someArgs17.arg2).intValue(), (Bundle) someArgs17.arg3);
                someArgs17.recycle();
                break;
            default:
                Log.w(TAG, "Unhandled message code: " + message.what);
                break;
        }
        long jNanoTime2 = (System.nanoTime() - jNanoTime) / 1000000;
        if (jNanoTime2 > 1000) {
            Log.w(TAG, "Handling message (" + message.what + ") took too long time (duration=" + jNanoTime2 + "ms)");
        }
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void startInteractiveApp() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(2));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void stopInteractiveApp() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(3));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void resetInteractiveApp() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(4));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void createBiInteractiveApp(Uri uri, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(5, uri, bundle));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void destroyBiInteractiveApp(String str) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(6, str));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void setTeletextAppEnabled(boolean z) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(7, Boolean.valueOf(z)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentVideoBounds(Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(40, rect));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentChannelUri(Uri uri) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(8, uri));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentChannelLcn(int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(9, Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendStreamVolume(float f) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(10, Float.valueOf(f)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTrackInfoList(List<TvTrackInfo> list) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(11, list));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentTvInputId(String str) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(12, str));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTimeShiftMode(int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(46, Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendAvailableSpeeds(float[] fArr) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(47, fArr));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTvRecordingInfo(TvRecordingInfo tvRecordingInfo) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(34, tvRecordingInfo));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTvRecordingInfoList(List<TvRecordingInfo> list) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(35, list));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendSigningResult(String str, byte[] bArr) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(13, str, bArr));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCertificate(String str, int i, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOOO(50, str, Integer.valueOf(i), bundle));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyError(String str, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(14, str, bundle));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftPlaybackParams(PlaybackParams playbackParams) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(36, playbackParams));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftStatusChanged(String str, int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(37, str, Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftStartPositionChanged(String str, long j) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(38, str, Long.valueOf(j)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftCurrentPositionChanged(String str, long j) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(39, str, Long.valueOf(j)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void release() {
        this.mSessionImpl.scheduleMediaViewCleanup();
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(1));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTuned(Uri uri) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(15, uri));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTrackSelected(int i, String str) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(16, Integer.valueOf(i), str));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTvMessage(int i, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(33, Integer.valueOf(i), bundle));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendSelectedTrackInfo(List<TvTrackInfo> list) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(48, list));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTracksChanged(List<TvTrackInfo> list) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(17, list));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyVideoAvailable() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(18));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyVideoUnavailable(int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(19, Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyVideoFreezeUpdated(boolean z) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(49, Boolean.valueOf(z)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyContentAllowed() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(20));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyContentBlocked(String str) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(21, str));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifySignalStrength(int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(22, Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingStarted(String str, String str2) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(30, str, str2));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingStopped(String str) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(31, str));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingConnectionFailed(String str, String str2) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(41, str, str2));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingDisconnected(String str, String str2) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(42, str, str2));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingTuned(String str, Uri uri) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(43, str, uri));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingError(String str, int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(44, str, Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingScheduled(String str, String str2) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(45, str, str2));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void setSurface(Surface surface) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(23, surface));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void dispatchSurfaceChanged(int i, int i2, int i3) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIIII(24, i, i2, i3, 0));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(25, broadcastInfoResponse));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyAdResponse(AdResponse adResponse) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(26, adResponse));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyAdBufferConsumed(AdBuffer adBuffer) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(32, adBuffer));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void createMediaView(IBinder iBinder, Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(27, iBinder, rect));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void relayoutMediaView(Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(28, rect));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void removeMediaView() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(29));
    }

    private final class TvInteractiveAppEventReceiver extends InputEventReceiver {
        TvInteractiveAppEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(InputEvent inputEvent) {
            if (ITvInteractiveAppSessionWrapper.this.mSessionImpl == null) {
                finishInputEvent(inputEvent, false);
                return;
            }
            int iDispatchInputEvent = ITvInteractiveAppSessionWrapper.this.mSessionImpl.dispatchInputEvent(inputEvent, this);
            if (iDispatchInputEvent != -1) {
                finishInputEvent(inputEvent, iDispatchInputEvent == 1);
            }
        }
    }
}
