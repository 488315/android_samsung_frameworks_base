package android.media.tv;

import android.content.Context;
import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.ITvInputSession;
import android.media.tv.TvInputService;
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

/* loaded from: classes3.dex */
public class ITvInputSessionWrapper extends ITvInputSession.Stub implements HandlerCaller.Callback {
    private static final int DO_APP_PRIVATE_COMMAND = 9;
    private static final int DO_CREATE_OVERLAY_VIEW = 10;
    private static final int DO_DISPATCH_SURFACE_CHANGED = 4;
    private static final int DO_NOTIFY_AD_BUFFER = 28;
    private static final int DO_NOTIFY_AD_SESSION_DATA = 36;
    private static final int DO_NOTIFY_TV_MESSAGE = 32;
    private static final int DO_PAUSE_RECORDING = 22;
    private static final int DO_RELAYOUT_OVERLAY_VIEW = 11;
    private static final int DO_RELEASE = 1;
    private static final int DO_REMOVE_BROADCAST_INFO = 25;
    private static final int DO_REMOVE_OVERLAY_VIEW = 12;
    private static final int DO_REQUEST_AD = 27;
    private static final int DO_REQUEST_BROADCAST_INFO = 24;
    private static final int DO_RESUME_PLAYBACK = 34;
    private static final int DO_RESUME_RECORDING = 23;
    private static final int DO_SELECT_AUDIO_PRESENTATION = 29;
    private static final int DO_SELECT_TRACK = 8;
    private static final int DO_SET_CAPTION_ENABLED = 7;
    private static final int DO_SET_IAPP_NOTIFICATION_ENABLED = 26;
    private static final int DO_SET_MAIN = 2;
    private static final int DO_SET_STREAM_VOLUME = 5;
    private static final int DO_SET_SURFACE = 3;
    private static final int DO_SET_TV_MESSAGE_ENABLED = 31;
    private static final int DO_SET_VIDEO_FROZEN = 35;
    private static final int DO_START_RECORDING = 20;
    private static final int DO_STOP_PLAYBACK = 33;
    private static final int DO_STOP_RECORDING = 21;
    private static final int DO_TIME_SHIFT_ENABLE_POSITION_TRACKING = 19;
    private static final int DO_TIME_SHIFT_PAUSE = 15;
    private static final int DO_TIME_SHIFT_PLAY = 14;
    private static final int DO_TIME_SHIFT_RESUME = 16;
    private static final int DO_TIME_SHIFT_SEEK_TO = 17;
    private static final int DO_TIME_SHIFT_SET_MODE = 30;
    private static final int DO_TIME_SHIFT_SET_PLAYBACK_PARAMS = 18;
    private static final int DO_TUNE = 6;
    private static final int DO_UNBLOCK_CONTENT = 13;
    private static final int EXECUTE_MESSAGE_TIMEOUT_LONG_MILLIS = 5000;
    private static final int EXECUTE_MESSAGE_TIMEOUT_SHORT_MILLIS = 50;
    private static final int EXECUTE_MESSAGE_TUNE_TIMEOUT_MILLIS = 2000;
    private static final String TAG = "TvInputSessionWrapper";
    private final HandlerCaller mCaller;
    private InputChannel mChannel;
    private final boolean mIsRecordingSession = true;
    private TvInputEventReceiver mReceiver;
    private TvInputService.RecordingSession mTvInputRecordingSessionImpl;
    private TvInputService.Session mTvInputSessionImpl;

    public ITvInputSessionWrapper(Context context, TvInputService.Session session, InputChannel inputChannel) {
        this.mCaller = new HandlerCaller(context, null, this, true);
        this.mTvInputSessionImpl = session;
        this.mChannel = inputChannel;
        if (inputChannel != null) {
            this.mReceiver = new TvInputEventReceiver(inputChannel, context.getMainLooper());
        }
    }

    public ITvInputSessionWrapper(Context context, TvInputService.RecordingSession recordingSession) {
        this.mCaller = new HandlerCaller(context, null, this, true);
        this.mTvInputRecordingSessionImpl = recordingSession;
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(Message message) {
        boolean z = this.mIsRecordingSession;
        if (z && this.mTvInputRecordingSessionImpl == null) {
            return;
        }
        if (z || this.mTvInputSessionImpl != null) {
            long nanoTime = System.nanoTime();
            switch (message.what) {
                case 1:
                    if (this.mIsRecordingSession) {
                        this.mTvInputRecordingSessionImpl.release();
                        this.mTvInputRecordingSessionImpl = null;
                        break;
                    } else {
                        this.mTvInputSessionImpl.release();
                        this.mTvInputSessionImpl = null;
                        TvInputEventReceiver tvInputEventReceiver = this.mReceiver;
                        if (tvInputEventReceiver != null) {
                            tvInputEventReceiver.dispose();
                            this.mReceiver = null;
                        }
                        InputChannel inputChannel = this.mChannel;
                        if (inputChannel != null) {
                            inputChannel.dispose();
                            this.mChannel = null;
                            break;
                        }
                    }
                    break;
                case 2:
                    this.mTvInputSessionImpl.setMain(((Boolean) message.obj).booleanValue());
                    break;
                case 3:
                    this.mTvInputSessionImpl.setSurface((Surface) message.obj);
                    break;
                case 4:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    this.mTvInputSessionImpl.dispatchSurfaceChanged(someArgs.argi1, someArgs.argi2, someArgs.argi3);
                    someArgs.recycle();
                    break;
                case 5:
                    this.mTvInputSessionImpl.setStreamVolume(((Float) message.obj).floatValue());
                    break;
                case 6:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    if (this.mIsRecordingSession) {
                        this.mTvInputRecordingSessionImpl.tune((Uri) someArgs2.arg1, (Bundle) someArgs2.arg2);
                    } else {
                        this.mTvInputSessionImpl.tune((Uri) someArgs2.arg1, (Bundle) someArgs2.arg2);
                    }
                    someArgs2.recycle();
                    break;
                case 7:
                    this.mTvInputSessionImpl.setCaptionEnabled(((Boolean) message.obj).booleanValue());
                    break;
                case 8:
                    SomeArgs someArgs3 = (SomeArgs) message.obj;
                    this.mTvInputSessionImpl.selectTrack(((Integer) someArgs3.arg1).intValue(), (String) someArgs3.arg2);
                    someArgs3.recycle();
                    break;
                case 9:
                    SomeArgs someArgs4 = (SomeArgs) message.obj;
                    if (this.mIsRecordingSession) {
                        this.mTvInputRecordingSessionImpl.appPrivateCommand((String) someArgs4.arg1, (Bundle) someArgs4.arg2);
                    } else {
                        this.mTvInputSessionImpl.appPrivateCommand((String) someArgs4.arg1, (Bundle) someArgs4.arg2);
                    }
                    someArgs4.recycle();
                    break;
                case 10:
                    SomeArgs someArgs5 = (SomeArgs) message.obj;
                    this.mTvInputSessionImpl.createOverlayView((IBinder) someArgs5.arg1, (Rect) someArgs5.arg2);
                    someArgs5.recycle();
                    break;
                case 11:
                    this.mTvInputSessionImpl.relayoutOverlayView((Rect) message.obj);
                    break;
                case 12:
                    this.mTvInputSessionImpl.removeOverlayView(true);
                    break;
                case 13:
                    this.mTvInputSessionImpl.unblockContent((String) message.obj);
                    break;
                case 14:
                    this.mTvInputSessionImpl.timeShiftPlay((Uri) message.obj);
                    break;
                case 15:
                    this.mTvInputSessionImpl.timeShiftPause();
                    break;
                case 16:
                    this.mTvInputSessionImpl.timeShiftResume();
                    break;
                case 17:
                    this.mTvInputSessionImpl.timeShiftSeekTo(((Long) message.obj).longValue());
                    break;
                case 18:
                    this.mTvInputSessionImpl.timeShiftSetPlaybackParams((PlaybackParams) message.obj);
                    break;
                case 19:
                    this.mTvInputSessionImpl.timeShiftEnablePositionTracking(((Boolean) message.obj).booleanValue());
                    break;
                case 20:
                    SomeArgs someArgs6 = (SomeArgs) message.obj;
                    this.mTvInputRecordingSessionImpl.startRecording((Uri) someArgs6.arg1, (Bundle) someArgs6.arg2);
                    someArgs6.recycle();
                    break;
                case 21:
                    this.mTvInputRecordingSessionImpl.stopRecording();
                    break;
                case 22:
                    this.mTvInputRecordingSessionImpl.pauseRecording((Bundle) message.obj);
                    break;
                case 23:
                    this.mTvInputRecordingSessionImpl.resumeRecording((Bundle) message.obj);
                    break;
                case 24:
                    this.mTvInputSessionImpl.requestBroadcastInfo((BroadcastInfoRequest) message.obj);
                    break;
                case 25:
                    this.mTvInputSessionImpl.removeBroadcastInfo(message.arg1);
                    break;
                case 26:
                    this.mTvInputSessionImpl.setInteractiveAppNotificationEnabled(((Boolean) message.obj).booleanValue());
                    break;
                case 27:
                    this.mTvInputSessionImpl.requestAd((AdRequest) message.obj);
                    break;
                case 28:
                    this.mTvInputSessionImpl.notifyAdBufferReady((AdBuffer) message.obj);
                    break;
                case 29:
                    SomeArgs someArgs7 = (SomeArgs) message.obj;
                    this.mTvInputSessionImpl.selectAudioPresentation(((Integer) someArgs7.arg1).intValue(), ((Integer) someArgs7.arg2).intValue());
                    someArgs7.recycle();
                    break;
                case 30:
                    this.mTvInputSessionImpl.timeShiftSetMode(message.arg1);
                    break;
                case 31:
                    SomeArgs someArgs8 = (SomeArgs) message.obj;
                    this.mTvInputSessionImpl.setTvMessageEnabled(((Integer) someArgs8.arg1).intValue(), ((Boolean) someArgs8.arg2).booleanValue());
                    someArgs8.recycle();
                    break;
                case 32:
                    SomeArgs someArgs9 = (SomeArgs) message.obj;
                    this.mTvInputSessionImpl.onTvMessageReceived(((Integer) someArgs9.arg1).intValue(), (Bundle) someArgs9.arg2);
                    someArgs9.recycle();
                    break;
                case 33:
                    this.mTvInputSessionImpl.stopPlayback(message.arg1);
                    break;
                case 34:
                    this.mTvInputSessionImpl.resumePlayback();
                    break;
                case 35:
                    this.mTvInputSessionImpl.setVideoFrozen(((Boolean) message.obj).booleanValue());
                    break;
                case 36:
                    SomeArgs someArgs10 = (SomeArgs) message.obj;
                    this.mTvInputSessionImpl.notifyTvAdSessionData((String) someArgs10.arg1, (Bundle) someArgs10.arg2);
                    someArgs10.recycle();
                    break;
                default:
                    Log.w(TAG, "Unhandled message code: " + message.what);
                    break;
            }
            long nanoTime2 = (System.nanoTime() - nanoTime) / 1000000;
            if (nanoTime2 > 50) {
                Log.w(TAG, "Handling message (" + message.what + ") took too long time (duration=" + nanoTime2 + "ms)");
                if (message.what == 6 && nanoTime2 > 2000) {
                    throw new RuntimeException("Too much time to handle tune request. (" + nanoTime2 + "ms > 2000ms) Consider handling the tune request in a separate thread.");
                }
                if (nanoTime2 <= 5000) {
                    return;
                }
                throw new RuntimeException("Too much time to handle a request. (type=" + message.what + ", " + nanoTime2 + "ms > 5000ms).");
            }
        }
    }

    @Override // android.media.tv.ITvInputSession
    public void release() {
        if (!this.mIsRecordingSession) {
            this.mTvInputSessionImpl.scheduleOverlayViewCleanup();
        }
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(1));
    }

    @Override // android.media.tv.ITvInputSession
    public void setMain(boolean z) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(2, Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void setSurface(Surface surface) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(3, surface));
    }

    @Override // android.media.tv.ITvInputSession
    public void dispatchSurfaceChanged(int i, int i2, int i3) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIIII(4, i, i2, i3, 0));
    }

    @Override // android.media.tv.ITvInputSession
    public final void setVolume(float f) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(5, Float.valueOf(f)));
    }

    @Override // android.media.tv.ITvInputSession
    public void tune(Uri uri, Bundle bundle) {
        this.mCaller.removeMessages(6);
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(6, uri, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void setCaptionEnabled(boolean z) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(7, Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void selectAudioPresentation(int i, int i2) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(29, Integer.valueOf(i), Integer.valueOf(i2)));
    }

    @Override // android.media.tv.ITvInputSession
    public void selectTrack(int i, String str) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(8, Integer.valueOf(i), str));
    }

    @Override // android.media.tv.ITvInputSession
    public void setInteractiveAppNotificationEnabled(boolean z) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(26, Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void appPrivateCommand(String str, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(9, str, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void createOverlayView(IBinder iBinder, Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(10, iBinder, rect));
    }

    @Override // android.media.tv.ITvInputSession
    public void relayoutOverlayView(Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(11, rect));
    }

    @Override // android.media.tv.ITvInputSession
    public void removeOverlayView() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(12));
    }

    @Override // android.media.tv.ITvInputSession
    public void unblockContent(String str) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(13, str));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftPlay(Uri uri) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(14, uri));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftPause() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(15));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftResume() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(16));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftSeekTo(long j) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(17, Long.valueOf(j)));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftSetPlaybackParams(PlaybackParams playbackParams) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(18, playbackParams));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftSetMode(int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageI(30, i));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftEnablePositionTracking(boolean z) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(19, Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void startRecording(Uri uri, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(20, uri, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void stopRecording() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(21));
    }

    @Override // android.media.tv.ITvInputSession
    public void pauseRecording(Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(22, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void resumeRecording(Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(23, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void requestBroadcastInfo(BroadcastInfoRequest broadcastInfoRequest) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(24, broadcastInfoRequest));
    }

    @Override // android.media.tv.ITvInputSession
    public void removeBroadcastInfo(int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageI(25, i));
    }

    @Override // android.media.tv.ITvInputSession
    public void requestAd(AdRequest adRequest) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(27, adRequest));
    }

    @Override // android.media.tv.ITvInputSession
    public void notifyAdBufferReady(AdBuffer adBuffer) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(28, adBuffer));
    }

    @Override // android.media.tv.ITvInputSession
    public void notifyTvAdSessionData(String str, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(36, str, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void setVideoFrozen(boolean z) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(35, Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void notifyTvMessage(int i, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(32, Integer.valueOf(i), bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void setTvMessageEnabled(int i, boolean z) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(31, Integer.valueOf(i), Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void stopPlayback(int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageI(33, i));
    }

    @Override // android.media.tv.ITvInputSession
    public void resumePlayback() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(34));
    }

    private final class TvInputEventReceiver extends InputEventReceiver {
        TvInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(InputEvent inputEvent) {
            if (ITvInputSessionWrapper.this.mTvInputSessionImpl == null) {
                finishInputEvent(inputEvent, false);
                return;
            }
            int dispatchInputEvent = ITvInputSessionWrapper.this.mTvInputSessionImpl.dispatchInputEvent(inputEvent, this);
            if (dispatchInputEvent != -1) {
                finishInputEvent(inputEvent, dispatchInputEvent == 1);
            }
        }
    }
}
