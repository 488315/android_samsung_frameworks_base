package android.media.tv.interactive;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.AdBuffer;
import android.media.tv.AdRequest;
import android.media.tv.AdResponse;
import android.media.tv.BroadcastInfoRequest;
import android.media.tv.BroadcastInfoResponse;
import android.media.tv.TvContentRating;
import android.media.tv.TvRecordingInfo;
import android.media.tv.TvTrackInfo;
import android.media.tv.interactive.ITvInteractiveAppService;
import android.media.tv.interactive.TvInteractiveAppService;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.internal.os.SomeArgs;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class TvInteractiveAppService extends Service {
    public static final String COMMAND_PARAMETER_KEY_CHANGE_CHANNEL_QUIETLY = "command_change_channel_quietly";
    public static final String COMMAND_PARAMETER_KEY_CHANNEL_URI = "command_channel_uri";
    public static final String COMMAND_PARAMETER_KEY_INPUT_ID = "command_input_id";
    public static final String COMMAND_PARAMETER_KEY_PLAYBACK_PARAMS = "command_playback_params";
    public static final String COMMAND_PARAMETER_KEY_PROGRAM_URI = "command_program_uri";
    public static final String COMMAND_PARAMETER_KEY_STOP_MODE = "command_stop_mode";
    public static final String COMMAND_PARAMETER_KEY_TIME_POSITION = "command_time_position";
    public static final String COMMAND_PARAMETER_KEY_TIME_SHIFT_MODE = "command_time_shift_mode";
    public static final String COMMAND_PARAMETER_KEY_TRACK_ID = "command_track_id";
    public static final String COMMAND_PARAMETER_KEY_TRACK_TYPE = "command_track_type";
    public static final String COMMAND_PARAMETER_KEY_VOLUME = "command_volume";
    public static final int COMMAND_PARAMETER_VALUE_STOP_MODE_BLANK = 1;
    public static final int COMMAND_PARAMETER_VALUE_STOP_MODE_FREEZE = 2;
    private static final boolean DEBUG = false;
    private static final int DETACH_MEDIA_VIEW_TIMEOUT_MS = 5000;
    public static final String PLAYBACK_COMMAND_TYPE_FREEZE = "freeze";
    public static final String PLAYBACK_COMMAND_TYPE_SELECT_TRACK = "select_track";
    public static final String PLAYBACK_COMMAND_TYPE_SET_STREAM_VOLUME = "set_stream_volume";
    public static final String PLAYBACK_COMMAND_TYPE_STOP = "stop";
    public static final String PLAYBACK_COMMAND_TYPE_TUNE = "tune";
    public static final String PLAYBACK_COMMAND_TYPE_TUNE_NEXT = "tune_next";
    public static final String PLAYBACK_COMMAND_TYPE_TUNE_PREV = "tune_previous";
    public static final String SERVICE_INTERFACE = "android.media.tv.interactive.TvInteractiveAppService";
    public static final String SERVICE_META_DATA = "android.media.tv.interactive.app";
    private static final String TAG = "TvInteractiveAppService";
    public static final String TIME_SHIFT_COMMAND_TYPE_PAUSE = "pause";
    public static final String TIME_SHIFT_COMMAND_TYPE_PLAY = "play";
    public static final String TIME_SHIFT_COMMAND_TYPE_RESUME = "resume";
    public static final String TIME_SHIFT_COMMAND_TYPE_SEEK_TO = "seek_to";
    public static final String TIME_SHIFT_COMMAND_TYPE_SET_MODE = "set_mode";
    public static final String TIME_SHIFT_COMMAND_TYPE_SET_PLAYBACK_PARAMS = "set_playback_params";
    private final Handler mServiceHandler = new ServiceHandler();
    private final RemoteCallbackList<ITvInteractiveAppServiceCallback> mCallbacks = new RemoteCallbackList<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackCommandStopMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackCommandType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeShiftCommandType {
    }

    public void onAppLinkCommand(Bundle bundle) {
    }

    public abstract Session onCreateSession(String str, int i);

    public void onRegisterAppLinkInfo(AppLinkInfo appLinkInfo) {
    }

    public void onUnregisterAppLinkInfo(AppLinkInfo appLinkInfo) {
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new ITvInteractiveAppService.Stub() { // from class: android.media.tv.interactive.TvInteractiveAppService.1
            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerCallback(ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) {
                if (iTvInteractiveAppServiceCallback != null) {
                    TvInteractiveAppService.this.mCallbacks.register(iTvInteractiveAppServiceCallback);
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterCallback(ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) {
                if (iTvInteractiveAppServiceCallback != null) {
                    TvInteractiveAppService.this.mCallbacks.unregister(iTvInteractiveAppServiceCallback);
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void createSession(InputChannel inputChannel, ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback, String str, int i) {
                if (iTvInteractiveAppSessionCallback == null) {
                    return;
                }
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = inputChannel;
                obtain.arg2 = iTvInteractiveAppSessionCallback;
                obtain.arg3 = str;
                obtain.arg4 = Integer.valueOf(i);
                TvInteractiveAppService.this.mServiceHandler.obtainMessage(1, obtain).sendToTarget();
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerAppLinkInfo(AppLinkInfo appLinkInfo) {
                TvInteractiveAppService.this.onRegisterAppLinkInfo(appLinkInfo);
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterAppLinkInfo(AppLinkInfo appLinkInfo) {
                TvInteractiveAppService.this.onUnregisterAppLinkInfo(appLinkInfo);
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void sendAppLinkCommand(Bundle bundle) {
                TvInteractiveAppService.this.onAppLinkCommand(bundle);
            }
        };
    }

    public final void notifyStateChanged(int i, int i2, int i3) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = Integer.valueOf(i);
        obtain.arg2 = Integer.valueOf(i2);
        obtain.arg3 = Integer.valueOf(i3);
        this.mServiceHandler.obtainMessage(3, obtain).sendToTarget();
    }

    public static abstract class Session implements KeyEvent.Callback {
        private final Context mContext;
        final Handler mHandler;
        private Rect mMediaFrame;
        private View mMediaView;
        private MediaViewCleanUpTask mMediaViewCleanUpTask;
        private FrameLayout mMediaViewContainer;
        private boolean mMediaViewEnabled;
        private ITvInteractiveAppSessionCallback mSessionCallback;
        private Surface mSurface;
        private final WindowManager mWindowManager;
        private WindowManager.LayoutParams mWindowParams;
        private IBinder mWindowToken;
        private final KeyEvent.DispatcherState mDispatcherState = new KeyEvent.DispatcherState();
        private final Object mLock = new Object();
        private final List<Runnable> mPendingActions = new ArrayList();

        public void onAdBufferConsumed(AdBuffer adBuffer) {
        }

        public void onAdResponse(AdResponse adResponse) {
        }

        public void onAvailableSpeeds(float[] fArr) {
        }

        public void onBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) {
        }

        public void onCertificate(String str, int i, SslCertificate sslCertificate) {
        }

        public void onContentAllowed() {
        }

        public void onContentBlocked(TvContentRating tvContentRating) {
        }

        public void onCreateBiInteractiveAppRequest(Uri uri, Bundle bundle) {
        }

        public View onCreateMediaView() {
            return null;
        }

        public void onCurrentChannelLcn(int i) {
        }

        public void onCurrentChannelUri(Uri uri) {
        }

        public void onCurrentTvInputId(String str) {
        }

        public void onCurrentVideoBounds(Rect rect) {
        }

        public void onDestroyBiInteractiveAppRequest(String str) {
        }

        public void onError(String str, Bundle bundle) {
        }

        public boolean onGenericMotionEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyDown(int i, KeyEvent keyEvent) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyUp(int i, KeyEvent keyEvent) {
            return false;
        }

        public void onMediaViewSizeChanged(int i, int i2) {
        }

        public void onRecordingConnectionFailed(String str, String str2) {
        }

        public void onRecordingDisconnected(String str, String str2) {
        }

        public void onRecordingError(String str, int i) {
        }

        public void onRecordingScheduled(String str, String str2) {
        }

        public void onRecordingStarted(String str, String str2) {
        }

        public void onRecordingStopped(String str) {
        }

        public void onRecordingTuned(String str, Uri uri) {
        }

        public abstract void onRelease();

        public void onResetInteractiveApp() {
        }

        public void onSelectedTrackInfo(List<TvTrackInfo> list) {
        }

        public abstract boolean onSetSurface(Surface surface);

        public void onSetTeletextAppEnabled(boolean z) {
        }

        public void onSignalStrength(int i) {
        }

        public void onSigningResult(String str, byte[] bArr) {
        }

        public void onStartInteractiveApp() {
        }

        public void onStopInteractiveApp() {
        }

        public void onStreamVolume(float f) {
        }

        public void onSurfaceChanged(int i, int i2, int i3) {
        }

        public void onTimeShiftCurrentPositionChanged(String str, long j) {
        }

        public void onTimeShiftMode(int i) {
        }

        public void onTimeShiftPlaybackParams(PlaybackParams playbackParams) {
        }

        public void onTimeShiftStartPositionChanged(String str, long j) {
        }

        public void onTimeShiftStatusChanged(String str, int i) {
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public void onTrackInfoList(List<TvTrackInfo> list) {
        }

        public void onTrackSelected(int i, String str) {
        }

        public boolean onTrackballEvent(MotionEvent motionEvent) {
            return false;
        }

        public void onTracksChanged(List<TvTrackInfo> list) {
        }

        public void onTuned(Uri uri) {
        }

        public void onTvMessage(int i, Bundle bundle) {
        }

        public void onTvRecordingInfo(TvRecordingInfo tvRecordingInfo) {
        }

        public void onTvRecordingInfoList(List<TvRecordingInfo> list) {
        }

        public void onVideoAvailable() {
        }

        public void onVideoFreezeUpdated(boolean z) {
        }

        public void onVideoUnavailable(int i) {
        }

        public Session(Context context) {
            this.mContext = context;
            this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            this.mHandler = new Handler(context.getMainLooper());
        }

        public void setMediaViewEnabled(final boolean z) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.1
                @Override // java.lang.Runnable
                public void run() {
                    if (z == Session.this.mMediaViewEnabled) {
                        return;
                    }
                    Session.this.mMediaViewEnabled = z;
                    if (z) {
                        if (Session.this.mWindowToken != null) {
                            Session session = Session.this;
                            session.createMediaView(session.mWindowToken, Session.this.mMediaFrame);
                            return;
                        }
                        return;
                    }
                    Session.this.removeMediaView(false);
                }
            });
        }

        public boolean isMediaViewEnabled() {
            return this.mMediaViewEnabled;
        }

        public void layoutSurface(final int i, final int i2, final int i3, final int i4) {
            if (i > i3 || i2 > i4) {
                throw new IllegalArgumentException("Invalid parameter");
            }
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onLayoutSurface(i, i2, i3, i4);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in layoutSurface", e);
                    }
                }
            });
        }

        public void requestBroadcastInfo(final BroadcastInfoRequest broadcastInfoRequest) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onBroadcastInfoRequest(broadcastInfoRequest);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestBroadcastInfo", e);
                    }
                }
            });
        }

        public void removeBroadcastInfo(final int i) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRemoveBroadcastInfo(i);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in removeBroadcastInfo", e);
                    }
                }
            });
        }

        public void sendPlaybackCommandRequest(final String str, final Bundle bundle) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onCommandRequest(str, bundle);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestCommand", e);
                    }
                }
            });
        }

        public void sendTimeShiftCommandRequest(final String str, final Bundle bundle) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftCommandRequest(str, bundle);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestTimeShiftCommand", e);
                    }
                }
            });
        }

        public void setVideoBounds(final Rect rect) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onSetVideoBounds(rect);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in setVideoBounds", e);
                    }
                }
            });
        }

        public void requestCurrentVideoBounds() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestCurrentVideoBounds();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestCurrentVideoBounds", e);
                    }
                }
            });
        }

        public void requestCurrentChannelUri() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestCurrentChannelUri();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestCurrentChannelUri", e);
                    }
                }
            });
        }

        public void requestCurrentChannelLcn() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestCurrentChannelLcn();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestCurrentChannelLcn", e);
                    }
                }
            });
        }

        public void requestStreamVolume() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestStreamVolume();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestStreamVolume", e);
                    }
                }
            });
        }

        public void requestTrackInfoList() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestTrackInfoList();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestTrackInfoList", e);
                    }
                }
            });
        }

        public void requestCurrentTvInputId() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestCurrentTvInputId();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestCurrentTvInputId", e);
                    }
                }
            });
        }

        public void requestTimeShiftMode() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.14
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestTimeShiftMode();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestTimeShiftMode", e);
                    }
                }
            });
        }

        public void requestAvailableSpeeds() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestAvailableSpeeds();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestAvailableSpeeds", e);
                    }
                }
            });
        }

        public void requestSelectedTrackInfo() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestSelectedTrackInfo$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSelectedTrackInfo$0() {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestSelectedTrackInfo();
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestSelectedTrackInfo", e);
            }
        }

        public void requestStartRecording(final String str, final Uri uri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestStartRecording$1(str, uri);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestStartRecording$1(String str, Uri uri) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestStartRecording(str, uri);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestStartRecording", e);
            }
        }

        public void requestStopRecording(final String str) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestStopRecording$2(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestStopRecording$2(String str) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestStopRecording(str);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestStopRecording", e);
            }
        }

        public void requestScheduleRecording(final String str, final String str2, final Uri uri, final Uri uri2, final Bundle bundle) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestScheduleRecording$3(str, str2, uri, uri2, bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestScheduleRecording$3(String str, String str2, Uri uri, Uri uri2, Bundle bundle) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestScheduleRecording(str, str2, uri, uri2, bundle);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestScheduleRecording", e);
            }
        }

        public void requestScheduleRecording(final String str, final String str2, final Uri uri, final long j, final long j2, final int i, final Bundle bundle) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestScheduleRecording$4(str, str2, uri, j, j2, i, bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestScheduleRecording$4(String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestScheduleRecording2(str, str2, uri, j, j2, i, bundle);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestScheduleRecording", e);
            }
        }

        public void setTvRecordingInfo(final String str, final TvRecordingInfo tvRecordingInfo) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$setTvRecordingInfo$5(str, tvRecordingInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setTvRecordingInfo$5(String str, TvRecordingInfo tvRecordingInfo) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onSetTvRecordingInfo(str, tvRecordingInfo);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in setTvRecordingInfo", e);
            }
        }

        public void requestTvRecordingInfo(final String str) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestTvRecordingInfo$6(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestTvRecordingInfo$6(String str) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestTvRecordingInfo(str);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestTvRecordingInfo", e);
            }
        }

        public void requestTvRecordingInfoList(final int i) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestTvRecordingInfoList$7(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestTvRecordingInfoList$7(int i) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestTvRecordingInfoList(i);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestTvRecordingInfoList", e);
            }
        }

        public void requestSigning(final String str, final String str2, final String str3, final byte[] bArr) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.16
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestSigning(str, str2, str3, bArr);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestSigning", e);
                    }
                }
            });
        }

        public void requestSigning(final String str, final String str2, final String str3, final int i, final byte[] bArr) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.17
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestSigning2(str, str2, str3, i, bArr);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestSigning", e);
                    }
                }
            });
        }

        public void requestCertificate(final String str, final int i) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.18
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestCertificate(str, i);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestCertificate", e);
                    }
                }
            });
        }

        public void requestAd(final AdRequest adRequest) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.19
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAdRequest(adRequest);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestAd", e);
                    }
                }
            });
        }

        void startInteractiveApp() {
            onStartInteractiveApp();
        }

        void stopInteractiveApp() {
            onStopInteractiveApp();
        }

        void resetInteractiveApp() {
            onResetInteractiveApp();
        }

        void createBiInteractiveApp(Uri uri, Bundle bundle) {
            onCreateBiInteractiveAppRequest(uri, bundle);
        }

        void destroyBiInteractiveApp(String str) {
            onDestroyBiInteractiveAppRequest(str);
        }

        void setTeletextAppEnabled(boolean z) {
            onSetTeletextAppEnabled(z);
        }

        void sendCurrentVideoBounds(Rect rect) {
            onCurrentVideoBounds(rect);
        }

        void sendCurrentChannelUri(Uri uri) {
            onCurrentChannelUri(uri);
        }

        void sendCurrentChannelLcn(int i) {
            onCurrentChannelLcn(i);
        }

        void sendStreamVolume(float f) {
            onStreamVolume(f);
        }

        void sendTrackInfoList(List<TvTrackInfo> list) {
            onTrackInfoList(list);
        }

        void sendCurrentTvInputId(String str) {
            onCurrentTvInputId(str);
        }

        void sendTimeShiftMode(int i) {
            onTimeShiftMode(i);
        }

        void sendAvailableSpeeds(float[] fArr) {
            onAvailableSpeeds(fArr);
        }

        void sendTvRecordingInfo(TvRecordingInfo tvRecordingInfo) {
            onTvRecordingInfo(tvRecordingInfo);
        }

        void sendTvRecordingInfoList(List<TvRecordingInfo> list) {
            onTvRecordingInfoList(list);
        }

        void sendSigningResult(String str, byte[] bArr) {
            onSigningResult(str, bArr);
        }

        void sendCertificate(String str, int i, Bundle bundle) {
            onCertificate(str, i, SslCertificate.restoreState(bundle));
        }

        void notifyError(String str, Bundle bundle) {
            onError(str, bundle);
        }

        void release() {
            onRelease();
            Surface surface = this.mSurface;
            if (surface != null) {
                surface.release();
                this.mSurface = null;
            }
            synchronized (this.mLock) {
                this.mSessionCallback = null;
                this.mPendingActions.clear();
            }
            removeMediaView(true);
        }

        void notifyTuned(Uri uri) {
            onTuned(uri);
        }

        void notifyTrackSelected(int i, String str) {
            if (str == null) {
                str = "";
            }
            onTrackSelected(i, str);
        }

        void notifyTracksChanged(List<TvTrackInfo> list) {
            onTracksChanged(list);
        }

        void notifyVideoAvailable() {
            onVideoAvailable();
        }

        void notifyVideoUnavailable(int i) {
            onVideoUnavailable(i);
        }

        void notifyVideoFreezeUpdated(boolean z) {
            onVideoFreezeUpdated(z);
        }

        void notifyContentAllowed() {
            onContentAllowed();
        }

        void notifyContentBlocked(TvContentRating tvContentRating) {
            onContentBlocked(tvContentRating);
        }

        void notifySignalStrength(int i) {
            onSignalStrength(i);
        }

        void notifyBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) {
            onBroadcastInfoResponse(broadcastInfoResponse);
        }

        void notifyAdResponse(AdResponse adResponse) {
            onAdResponse(adResponse);
        }

        void notifyTvMessage(int i, Bundle bundle) {
            onTvMessage(i, bundle);
        }

        void sendSelectedTrackInfo(List<TvTrackInfo> list) {
            onSelectedTrackInfo(list);
        }

        void notifyAdBufferConsumed(AdBuffer adBuffer) {
            onAdBufferConsumed(adBuffer);
        }

        void notifyRecordingStarted(String str, String str2) {
            onRecordingStarted(str, str2);
        }

        void notifyRecordingStopped(String str) {
            onRecordingStopped(str);
        }

        void notifyRecordingConnectionFailed(String str, String str2) {
            onRecordingConnectionFailed(str, str2);
        }

        void notifyRecordingDisconnected(String str, String str2) {
            onRecordingDisconnected(str, str2);
        }

        void notifyRecordingTuned(String str, Uri uri) {
            onRecordingTuned(str, uri);
        }

        void notifyRecordingError(String str, int i) {
            onRecordingError(str, i);
        }

        void notifyRecordingScheduled(String str, String str2) {
            onRecordingScheduled(str, str2);
        }

        void notifyTimeShiftPlaybackParams(PlaybackParams playbackParams) {
            onTimeShiftPlaybackParams(playbackParams);
        }

        void notifyTimeShiftStatusChanged(String str, int i) {
            onTimeShiftStatusChanged(str, i);
        }

        void notifyTimeShiftStartPositionChanged(String str, long j) {
            onTimeShiftStartPositionChanged(str, j);
        }

        void notifyTimeShiftCurrentPositionChanged(String str, long j) {
            onTimeShiftCurrentPositionChanged(str, j);
        }

        public void notifySessionStateChanged(final int i, final int i2) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.20
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onSessionStateChanged(i, i2);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in notifySessionStateChanged", e);
                    }
                }
            });
        }

        public final void notifyBiInteractiveAppCreated(final Uri uri, final String str) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.21
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onBiInteractiveAppCreated(uri, str);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in notifyBiInteractiveAppCreated", e);
                    }
                }
            });
        }

        public final void notifyTeletextAppStateChanged(final int i) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.22
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTeletextAppStateChanged(i);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in notifyTeletextAppState", e);
                    }
                }
            });
        }

        public void notifyAdBufferReady(final AdBuffer adBuffer) {
            try {
                final AdBuffer dupAdBuffer = AdBuffer.dupAdBuffer(adBuffer);
                executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.23
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onAdBufferReady(dupAdBuffer);
                                }
                                AdBuffer adBuffer2 = dupAdBuffer;
                                if (adBuffer2 != null) {
                                    adBuffer2.getSharedMemory().close();
                                }
                            } catch (RemoteException e) {
                                Log.w(TvInteractiveAppService.TAG, "error in notifyAdBuffer", e);
                                AdBuffer adBuffer3 = dupAdBuffer;
                                if (adBuffer3 != null) {
                                    adBuffer3.getSharedMemory().close();
                                }
                            }
                        } catch (Throwable th) {
                            AdBuffer adBuffer4 = dupAdBuffer;
                            if (adBuffer4 != null) {
                                adBuffer4.getSharedMemory().close();
                            }
                            throw th;
                        }
                    }
                });
            } catch (IOException e) {
                Log.w(TvInteractiveAppService.TAG, "dup AdBuffer error in notifyAdBufferReady:", e);
            }
        }

        int dispatchInputEvent(InputEvent inputEvent, InputEventReceiver inputEventReceiver) {
            if (inputEvent instanceof KeyEvent) {
                return ((KeyEvent) inputEvent).dispatch(this, this.mDispatcherState, this) ? 1 : 0;
            }
            if (!(inputEvent instanceof MotionEvent)) {
                return 0;
            }
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            return motionEvent.isTouchEvent() ? onTouchEvent(motionEvent) ? 1 : 0 : (motionEvent.getSource() & 4) != 0 ? onTrackballEvent(motionEvent) ? 1 : 0 : onGenericMotionEvent(motionEvent) ? 1 : 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize(ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback) {
            synchronized (this.mLock) {
                this.mSessionCallback = iTvInteractiveAppSessionCallback;
                Iterator<Runnable> it = this.mPendingActions.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
                this.mPendingActions.clear();
            }
        }

        void setSurface(Surface surface) {
            onSetSurface(surface);
            Surface surface2 = this.mSurface;
            if (surface2 != null) {
                surface2.release();
            }
            this.mSurface = surface;
        }

        void dispatchSurfaceChanged(int i, int i2, int i3) {
            onSurfaceChanged(i, i2, i3);
        }

        private void executeOrPostRunnableOnMainThread(Runnable runnable) {
            synchronized (this.mLock) {
                if (this.mSessionCallback == null) {
                    this.mPendingActions.add(runnable);
                } else if (this.mHandler.getLooper().isCurrentThread()) {
                    runnable.run();
                } else {
                    this.mHandler.post(runnable);
                }
            }
        }

        void createMediaView(IBinder iBinder, Rect rect) {
            if (this.mMediaViewContainer != null) {
                removeMediaView(false);
            }
            this.mWindowToken = iBinder;
            this.mMediaFrame = rect;
            onMediaViewSizeChanged(rect.right - rect.left, rect.bottom - rect.top);
            if (this.mMediaViewEnabled) {
                View onCreateMediaView = onCreateMediaView();
                this.mMediaView = onCreateMediaView;
                if (onCreateMediaView == null) {
                    return;
                }
                MediaViewCleanUpTask mediaViewCleanUpTask = this.mMediaViewCleanUpTask;
                if (mediaViewCleanUpTask != null) {
                    mediaViewCleanUpTask.cancel(true);
                    this.mMediaViewCleanUpTask = null;
                }
                FrameLayout frameLayout = new FrameLayout(this.mContext.getApplicationContext());
                this.mMediaViewContainer = frameLayout;
                frameLayout.addView(this.mMediaView);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(rect.right - rect.left, rect.bottom - rect.top, rect.left, rect.top, 1001, ActivityManager.isHighEndGfx() ? 16777752 : 536, -2);
                this.mWindowParams = layoutParams;
                layoutParams.privateFlags |= 64;
                this.mWindowParams.gravity = 8388659;
                this.mWindowParams.token = iBinder;
                this.mWindowManager.addView(this.mMediaViewContainer, this.mWindowParams);
            }
        }

        void relayoutMediaView(Rect rect) {
            Rect rect2 = this.mMediaFrame;
            if (rect2 == null || rect2.width() != rect.width() || this.mMediaFrame.height() != rect.height()) {
                onMediaViewSizeChanged(rect.right - rect.left, rect.bottom - rect.top);
            }
            this.mMediaFrame = rect;
            if (!this.mMediaViewEnabled || this.mMediaViewContainer == null) {
                return;
            }
            this.mWindowParams.x = rect.left;
            this.mWindowParams.y = rect.top;
            this.mWindowParams.width = rect.right - rect.left;
            this.mWindowParams.height = rect.bottom - rect.top;
            this.mWindowManager.updateViewLayout(this.mMediaViewContainer, this.mWindowParams);
        }

        void removeMediaView(boolean z) {
            if (z) {
                this.mWindowToken = null;
                this.mMediaFrame = null;
            }
            FrameLayout frameLayout = this.mMediaViewContainer;
            if (frameLayout != null) {
                frameLayout.removeView(this.mMediaView);
                this.mMediaView = null;
                this.mWindowManager.removeView(this.mMediaViewContainer);
                this.mMediaViewContainer = null;
                this.mWindowParams = null;
            }
        }

        void scheduleMediaViewCleanup() {
            FrameLayout frameLayout = this.mMediaViewContainer;
            if (frameLayout != null) {
                MediaViewCleanUpTask mediaViewCleanUpTask = new MediaViewCleanUpTask();
                this.mMediaViewCleanUpTask = mediaViewCleanUpTask;
                mediaViewCleanUpTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, frameLayout);
            }
        }
    }

    private static final class MediaViewCleanUpTask extends AsyncTask<View, Void, Void> {
        private MediaViewCleanUpTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(View... viewArr) {
            View view = viewArr[0];
            try {
                Thread.sleep(5000L);
                if (!isCancelled() && view.isAttachedToWindow()) {
                    Log.e(TvInteractiveAppService.TAG, "Time out on releasing media view. Killing " + view.getContext().getPackageName());
                    Process.killProcess(Process.myPid());
                }
            } catch (InterruptedException unused) {
            }
            return null;
        }
    }

    private final class ServiceHandler extends Handler {
        private static final int DO_CREATE_SESSION = 1;
        private static final int DO_NOTIFY_RTE_STATE_CHANGED = 3;
        private static final int DO_NOTIFY_SESSION_CREATED = 2;

        private ServiceHandler() {
        }

        private void broadcastRteStateChanged(int i, int i2, int i3) {
            int beginBroadcast = TvInteractiveAppService.this.mCallbacks.beginBroadcast();
            for (int i4 = 0; i4 < beginBroadcast; i4++) {
                try {
                    ((ITvInteractiveAppServiceCallback) TvInteractiveAppService.this.mCallbacks.getBroadcastItem(i4)).onStateChanged(i, i2, i3);
                } catch (RemoteException e) {
                    Log.e(TvInteractiveAppService.TAG, "error in broadcastRteStateChanged", e);
                }
            }
            TvInteractiveAppService.this.mCallbacks.finishBroadcast();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        SomeArgs someArgs = (SomeArgs) message.obj;
                        broadcastRteStateChanged(((Integer) someArgs.arg1).intValue(), ((Integer) someArgs.arg2).intValue(), ((Integer) someArgs.arg3).intValue());
                        return;
                    } else {
                        Log.w(TvInteractiveAppService.TAG, "Unhandled message code: " + message.what);
                        return;
                    }
                }
                SomeArgs someArgs2 = (SomeArgs) message.obj;
                Session session = (Session) someArgs2.arg1;
                ITvInteractiveAppSession iTvInteractiveAppSession = (ITvInteractiveAppSession) someArgs2.arg2;
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = (ITvInteractiveAppSessionCallback) someArgs2.arg3;
                try {
                    iTvInteractiveAppSessionCallback.onSessionCreated(iTvInteractiveAppSession);
                } catch (RemoteException e) {
                    Log.e(TvInteractiveAppService.TAG, "error in onSessionCreated", e);
                }
                if (session != null) {
                    session.initialize(iTvInteractiveAppSessionCallback);
                }
                someArgs2.recycle();
                return;
            }
            SomeArgs someArgs3 = (SomeArgs) message.obj;
            InputChannel inputChannel = (InputChannel) someArgs3.arg1;
            ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback2 = (ITvInteractiveAppSessionCallback) someArgs3.arg2;
            String str = (String) someArgs3.arg3;
            int intValue = ((Integer) someArgs3.arg4).intValue();
            someArgs3.recycle();
            Session onCreateSession = TvInteractiveAppService.this.onCreateSession(str, intValue);
            if (onCreateSession == null) {
                try {
                    iTvInteractiveAppSessionCallback2.onSessionCreated(null);
                    return;
                } catch (RemoteException e2) {
                    Log.e(TvInteractiveAppService.TAG, "error in onSessionCreated", e2);
                    return;
                }
            }
            ITvInteractiveAppSessionWrapper iTvInteractiveAppSessionWrapper = new ITvInteractiveAppSessionWrapper(TvInteractiveAppService.this, onCreateSession, inputChannel);
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = onCreateSession;
            obtain.arg2 = iTvInteractiveAppSessionWrapper;
            obtain.arg3 = iTvInteractiveAppSessionCallback2;
            TvInteractiveAppService.this.mServiceHandler.obtainMessage(2, obtain).sendToTarget();
        }
    }
}
