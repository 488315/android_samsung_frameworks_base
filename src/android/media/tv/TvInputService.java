package android.media.tv;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.Service;
import android.content.AttributionSource;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioPresentation;
import android.media.PlaybackParams;
import android.media.tv.ITvInputService;
import android.media.tv.TvInputManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
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
import com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class TvInputService extends Service {
    private static final boolean DEBUG = false;
    private static final int DETACH_OVERLAY_VIEW_TIMEOUT_MS = 5000;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_BACKGROUND = 100;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_LIVE = 400;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_PLAYBACK = 300;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_RECORD = 500;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_SCAN = 200;
    public static final String SERVICE_INTERFACE = "android.media.tv.TvInputService";
    public static final String SERVICE_META_DATA = "android.media.tv.input";
    private static final String TAG = "TvInputService";
    private TvInputManager mTvInputManager;
    private final Handler mServiceHandler = new ServiceHandler();
    private final RemoteCallbackList<ITvInputServiceCallback> mCallbacks = new RemoteCallbackList<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface PriorityHintUseCaseType {
    }

    public static boolean isNavigationKey(int i) {
        if (i == 61 || i == 62 || i == 66 || i == 92 || i == 93 || i == 122 || i == 123) {
            return true;
        }
        switch (i) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                return true;
            default:
                return false;
        }
    }

    @SystemApi
    public IBinder createExtension() {
        return null;
    }

    @SystemApi
    public IBinder getExtensionInterface(String str) {
        return null;
    }

    @SystemApi
    public String getExtensionInterfacePermission(String str) {
        return null;
    }

    public RecordingSession onCreateRecordingSession(String str) {
        return null;
    }

    public abstract Session onCreateSession(String str);

    @SystemApi
    public TvInputInfo onHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) {
        return null;
    }

    @SystemApi
    public String onHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) {
        return null;
    }

    @SystemApi
    public TvInputInfo onHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) {
        return null;
    }

    @SystemApi
    public String onHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) {
        return null;
    }

    @SystemApi
    public void onHdmiDeviceUpdated(HdmiDeviceInfo hdmiDeviceInfo) {
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        ITvInputService.Stub stub = new ITvInputService.Stub() { // from class: android.media.tv.TvInputService.1
            @Override // android.media.tv.ITvInputService
            public void registerCallback(ITvInputServiceCallback iTvInputServiceCallback) {
                if (iTvInputServiceCallback != null) {
                    TvInputService.this.mCallbacks.register(iTvInputServiceCallback);
                }
            }

            @Override // android.media.tv.ITvInputService
            public void unregisterCallback(ITvInputServiceCallback iTvInputServiceCallback) {
                if (iTvInputServiceCallback != null) {
                    TvInputService.this.mCallbacks.unregister(iTvInputServiceCallback);
                }
            }

            @Override // android.media.tv.ITvInputService
            public void createSession(InputChannel inputChannel, ITvInputSessionCallback iTvInputSessionCallback, String str, String str2, AttributionSource attributionSource) {
                if (inputChannel == null) {
                    Log.w(TvInputService.TAG, "Creating session without input channel");
                }
                if (iTvInputSessionCallback == null) {
                    return;
                }
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = inputChannel;
                obtain.arg2 = iTvInputSessionCallback;
                obtain.arg3 = str;
                obtain.arg4 = str2;
                obtain.arg5 = attributionSource;
                TvInputService.this.mServiceHandler.obtainMessage(1, obtain).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void createRecordingSession(ITvInputSessionCallback iTvInputSessionCallback, String str, String str2) {
                if (iTvInputSessionCallback == null) {
                    return;
                }
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = iTvInputSessionCallback;
                obtain.arg2 = str;
                obtain.arg3 = str2;
                TvInputService.this.mServiceHandler.obtainMessage(3, obtain).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public List<String> getAvailableExtensionInterfaceNames() {
                List<String> availableExtensionInterfaceNames = TvInputService.this.getAvailableExtensionInterfaceNames();
                if (Flags.tifExtensionStandardization()) {
                    availableExtensionInterfaceNames.addAll(TvInputServiceExtensionManager.getStandardExtensionInterfaceNames());
                }
                return availableExtensionInterfaceNames;
            }

            @Override // android.media.tv.ITvInputService
            public IBinder getExtensionInterface(String str) {
                IBinder extensionInterface = TvInputService.this.getExtensionInterface(str);
                if (!Flags.tifExtensionStandardization() || str == null || !TvInputServiceExtensionManager.checkIsStandardizedInterfaces(str) || TvInputServiceExtensionManager.checkIsStandardizedIBinder(str, extensionInterface)) {
                    return extensionInterface;
                }
                return null;
            }

            @Override // android.media.tv.ITvInputService
            public String getExtensionInterfacePermission(String str) {
                return TvInputService.this.getExtensionInterfacePermission(str);
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(4, tvInputHardwareInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(5, tvInputHardwareInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(6, hdmiDeviceInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(7, hdmiDeviceInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceUpdated(HdmiDeviceInfo hdmiDeviceInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(8, hdmiDeviceInfo).sendToTarget();
            }
        };
        IBinder createExtension = createExtension();
        if (createExtension != null) {
            stub.setExtension(createExtension);
        }
        return stub;
    }

    @SystemApi
    public List<String> getAvailableExtensionInterfaceNames() {
        return new ArrayList();
    }

    public Session onCreateSession(String str, String str2) {
        return onCreateSession(str);
    }

    public Session onCreateSession(String str, String str2, AttributionSource attributionSource) {
        return onCreateSession(str, str2);
    }

    public RecordingSession onCreateRecordingSession(String str, String str2) {
        return onCreateRecordingSession(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPassthroughInput(String str) {
        if (this.mTvInputManager == null) {
            this.mTvInputManager = (TvInputManager) getSystemService(Context.TV_INPUT_SERVICE);
        }
        TvInputInfo tvInputInfo = this.mTvInputManager.getTvInputInfo(str);
        return tvInputInfo != null && tvInputInfo.isPassthroughInput();
    }

    public static abstract class Session implements KeyEvent.Callback {
        private static final int POSITION_UPDATE_INTERVAL_MS = 1000;
        private final Context mContext;
        final Handler mHandler;
        private Rect mOverlayFrame;
        private View mOverlayView;
        private OverlayViewCleanUpTask mOverlayViewCleanUpTask;
        private FrameLayout mOverlayViewContainer;
        private boolean mOverlayViewEnabled;
        private ITvInputSessionCallback mSessionCallback;
        private Surface mSurface;
        private final WindowManager mWindowManager;
        private WindowManager.LayoutParams mWindowParams;
        private IBinder mWindowToken;
        private final KeyEvent.DispatcherState mDispatcherState = new KeyEvent.DispatcherState();
        private long mStartPositionMs = Long.MIN_VALUE;
        private long mCurrentPositionMs = Long.MIN_VALUE;
        private final TimeShiftPositionTrackingRunnable mTimeShiftPositionTrackingRunnable = new TimeShiftPositionTrackingRunnable();
        private final Object mLock = new Object();
        private final List<Runnable> mPendingActions = new ArrayList();

        public void onAdBufferReady(AdBuffer adBuffer) {
        }

        public void onAppPrivateCommand(String str, Bundle bundle) {
        }

        public View onCreateOverlayView() {
            return null;
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

        public void onOverlayViewSizeChanged(int i, int i2) {
        }

        public abstract void onRelease();

        public void onRemoveBroadcastInfo(int i) {
        }

        public void onRequestAd(AdRequest adRequest) {
        }

        public void onRequestBroadcastInfo(BroadcastInfoRequest broadcastInfoRequest) {
        }

        public void onResumePlayback() {
        }

        public boolean onSelectAudioPresentation(int i, int i2) {
            return false;
        }

        public boolean onSelectTrack(int i, String str) {
            return false;
        }

        public abstract void onSetCaptionEnabled(boolean z);

        public void onSetInteractiveAppNotificationEnabled(boolean z) {
        }

        @SystemApi
        public void onSetMain(boolean z) {
        }

        public abstract void onSetStreamVolume(float f);

        public abstract boolean onSetSurface(Surface surface);

        public void onSetTvMessageEnabled(int i, boolean z) {
        }

        public void onSetVideoFrozen(boolean z) {
        }

        public void onStopPlayback(int i) {
        }

        public void onSurfaceChanged(int i, int i2, int i3) {
        }

        public long onTimeShiftGetCurrentPosition() {
            return Long.MIN_VALUE;
        }

        public long onTimeShiftGetStartPosition() {
            return Long.MIN_VALUE;
        }

        public void onTimeShiftPause() {
        }

        public void onTimeShiftPlay(Uri uri) {
        }

        public void onTimeShiftResume() {
        }

        public void onTimeShiftSeekTo(long j) {
        }

        public void onTimeShiftSetMode(int i) {
        }

        public void onTimeShiftSetPlaybackParams(PlaybackParams playbackParams) {
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public boolean onTrackballEvent(MotionEvent motionEvent) {
            return false;
        }

        public abstract boolean onTune(Uri uri);

        public void onTvAdSessionData(String str, Bundle bundle) {
        }

        public void onTvMessage(int i, Bundle bundle) {
        }

        public void onUnblockContent(TvContentRating tvContentRating) {
        }

        public Session(Context context) {
            this.mContext = context;
            this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            this.mHandler = new Handler(context.getMainLooper());
        }

        public void setOverlayViewEnabled(final boolean z) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputService.Session.1
                @Override // java.lang.Runnable
                public void run() {
                    if (z == Session.this.mOverlayViewEnabled) {
                        return;
                    }
                    Session.this.mOverlayViewEnabled = z;
                    if (z) {
                        if (Session.this.mWindowToken != null) {
                            Session session = Session.this;
                            session.createOverlayView(session.mWindowToken, Session.this.mOverlayFrame);
                            return;
                        }
                        return;
                    }
                    Session.this.removeOverlayView(false);
                }
            });
        }

        @SystemApi
        public void notifySessionEvent(final String str, final Bundle bundle) {
            Preconditions.checkNotNull(str);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onSessionEvent(str, bundle);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in sending event (event=" + str + NavigationBarInflaterView.KEY_CODE_END, e);
                    }
                }
            });
        }

        public void notifyChannelRetuned(final Uri uri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onChannelRetuned(uri);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyChannelRetuned", e);
                    }
                }
            });
        }

        public void notifyTuned(final Uri uri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTuned(uri);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTuned", e);
                    }
                }
            });
        }

        public void notifyTracksChanged(List<TvTrackInfo> list) {
            final ArrayList arrayList = new ArrayList(list);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTracksChanged(arrayList);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTracksChanged", e);
                    }
                }
            });
        }

        public void notifyTrackSelected(final int i, final String str) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTrackSelected(i, str);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTrackSelected", e);
                    }
                }
            });
        }

        public void notifyVideoAvailable() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onVideoAvailable();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyVideoAvailable", e);
                    }
                }
            });
        }

        public void notifyVideoUnavailable(final int i) {
            if (i < 0 || i > 18) {
                Log.e(TvInputService.TAG, "notifyVideoUnavailable - unknown reason: " + i);
            }
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onVideoUnavailable(i);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyVideoUnavailable", e);
                    }
                }
            });
        }

        public void notifyVideoFreezeUpdated(final boolean z) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onVideoFreezeUpdated(z);
                        }
                    } catch (RemoteException e) {
                        Log.e(TvInputService.TAG, "error in notifyVideoFreezeUpdated", e);
                    }
                }
            });
        }

        public void notifyAudioPresentationChanged(List<AudioPresentation> list) {
            final ArrayList arrayList = new ArrayList(list);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAudioPresentationsChanged(arrayList);
                        }
                    } catch (RemoteException e) {
                        Log.e(TvInputService.TAG, "error in notifyAudioPresentationsChanged", e);
                    }
                }
            });
        }

        public void notifyAudioPresentationSelected(final int i, final int i2) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAudioPresentationSelected(i, i2);
                        }
                    } catch (RemoteException e) {
                        Log.e(TvInputService.TAG, "error in notifyAudioPresentationSelected", e);
                    }
                }
            });
        }

        public void notifyContentAllowed() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onContentAllowed();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyContentAllowed", e);
                    }
                }
            });
        }

        public void notifyContentBlocked(final TvContentRating tvContentRating) {
            Preconditions.checkNotNull(tvContentRating);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onContentBlocked(tvContentRating.flattenToString());
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyContentBlocked", e);
                    }
                }
            });
        }

        public void notifyTimeShiftStatusChanged(final int i) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.14
                @Override // java.lang.Runnable
                public void run() {
                    Session.this.timeShiftEnablePositionTracking(i == 3);
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftStatusChanged(i);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTimeShiftStatusChanged", e);
                    }
                }
            });
        }

        public void notifyBroadcastInfoResponse(final BroadcastInfoResponse broadcastInfoResponse) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onBroadcastInfoResponse(broadcastInfoResponse);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyBroadcastInfoResponse", e);
                    }
                }
            });
        }

        public void notifyAdResponse(final AdResponse adResponse) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.16
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAdResponse(adResponse);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyAdResponse", e);
                    }
                }
            });
        }

        public void notifyAdBufferConsumed(AdBuffer adBuffer) {
            try {
                final AdBuffer dupAdBuffer = AdBuffer.dupAdBuffer(adBuffer);
                executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.17
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onAdBufferConsumed(dupAdBuffer);
                                }
                                AdBuffer adBuffer2 = dupAdBuffer;
                                if (adBuffer2 != null) {
                                    adBuffer2.getSharedMemory().close();
                                }
                            } catch (RemoteException e) {
                                Log.w(TvInputService.TAG, "error in notifyAdBufferConsumed", e);
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
                Log.w(TvInputService.TAG, "dup AdBuffer error in notifyAdBufferConsumed:", e);
            }
        }

        public void notifyTvMessage(final int i, final Bundle bundle) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.18
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTvMessage(i, bundle);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTvMessage", e);
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyTimeShiftStartPositionChanged(final long j) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.19
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftStartPositionChanged(j);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTimeShiftStartPositionChanged", e);
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyTimeShiftCurrentPositionChanged(final long j) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.20
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftCurrentPositionChanged(j);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTimeShiftCurrentPositionChanged", e);
                    }
                }
            });
        }

        public void notifyAitInfoUpdated(final AitInfo aitInfo) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.21
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAitInfoUpdated(aitInfo);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyAitInfoUpdated", e);
                    }
                }
            });
        }

        public void notifyTimeShiftMode(final int i) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.22
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftMode(i);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTimeShiftMode", e);
                    }
                }
            });
        }

        public void notifyAvailableSpeeds(final float[] fArr) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.23
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Arrays.sort(fArr);
                            Session.this.mSessionCallback.onAvailableSpeeds(fArr);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyAvailableSpeeds", e);
                    }
                }
            });
        }

        public void notifySignalStrength(final int i) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.24
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onSignalStrength(i);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifySignalStrength", e);
                    }
                }
            });
        }

        public void notifyCueingMessageAvailability(final boolean z) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.25
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onCueingMessageAvailability(z);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyCueingMessageAvailability", e);
                    }
                }
            });
        }

        public void sendTvInputSessionData(final String str, final Bundle bundle) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.26
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTvInputSessionData(str, bundle);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in sendTvInputSessionData", e);
                    }
                }
            });
        }

        public void layoutSurface(final int i, final int i2, final int i3, final int i4) {
            if (i > i3 || i2 > i4) {
                throw new IllegalArgumentException("Invalid parameter");
            }
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.27
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onLayoutSurface(i, i2, i3, i4);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in layoutSurface", e);
                    }
                }
            });
        }

        public boolean onTune(Uri uri, Bundle bundle) {
            return onTune(uri);
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
            removeOverlayView(true);
            this.mHandler.removeCallbacks(this.mTimeShiftPositionTrackingRunnable);
        }

        void setMain(boolean z) {
            onSetMain(z);
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

        void setStreamVolume(float f) {
            onSetStreamVolume(f);
        }

        void tune(Uri uri, Bundle bundle) {
            this.mCurrentPositionMs = Long.MIN_VALUE;
            onTune(uri, bundle);
        }

        void setCaptionEnabled(boolean z) {
            onSetCaptionEnabled(z);
        }

        void selectAudioPresentation(int i, int i2) {
            onSelectAudioPresentation(i, i2);
        }

        void selectTrack(int i, String str) {
            onSelectTrack(i, str);
        }

        void unblockContent(String str) {
            onUnblockContent(TvContentRating.unflattenFromString(str));
        }

        void setInteractiveAppNotificationEnabled(boolean z) {
            onSetInteractiveAppNotificationEnabled(z);
        }

        void setTvMessageEnabled(int i, boolean z) {
            onSetTvMessageEnabled(i, z);
        }

        void appPrivateCommand(String str, Bundle bundle) {
            onAppPrivateCommand(str, bundle);
        }

        void createOverlayView(IBinder iBinder, Rect rect) {
            if (this.mOverlayViewContainer != null) {
                removeOverlayView(false);
            }
            this.mWindowToken = iBinder;
            this.mOverlayFrame = rect;
            onOverlayViewSizeChanged(rect.right - rect.left, rect.bottom - rect.top);
            if (this.mOverlayViewEnabled) {
                View onCreateOverlayView = onCreateOverlayView();
                this.mOverlayView = onCreateOverlayView;
                if (onCreateOverlayView == null) {
                    return;
                }
                OverlayViewCleanUpTask overlayViewCleanUpTask = this.mOverlayViewCleanUpTask;
                if (overlayViewCleanUpTask != null) {
                    overlayViewCleanUpTask.cancel(true);
                    this.mOverlayViewCleanUpTask = null;
                }
                FrameLayout frameLayout = new FrameLayout(this.mContext.getApplicationContext());
                this.mOverlayViewContainer = frameLayout;
                frameLayout.addView(this.mOverlayView);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(rect.right - rect.left, rect.bottom - rect.top, rect.left, rect.top, 1004, ActivityManager.isHighEndGfx() ? 16777752 : 536, -2);
                this.mWindowParams = layoutParams;
                layoutParams.privateFlags |= 64;
                this.mWindowParams.gravity = 8388659;
                this.mWindowParams.token = iBinder;
                this.mWindowManager.addView(this.mOverlayViewContainer, this.mWindowParams);
            }
        }

        void relayoutOverlayView(Rect rect) {
            Rect rect2 = this.mOverlayFrame;
            if (rect2 == null || rect2.width() != rect.width() || this.mOverlayFrame.height() != rect.height()) {
                onOverlayViewSizeChanged(rect.right - rect.left, rect.bottom - rect.top);
            }
            this.mOverlayFrame = rect;
            if (!this.mOverlayViewEnabled || this.mOverlayViewContainer == null) {
                return;
            }
            this.mWindowParams.x = rect.left;
            this.mWindowParams.y = rect.top;
            this.mWindowParams.width = rect.right - rect.left;
            this.mWindowParams.height = rect.bottom - rect.top;
            this.mWindowManager.updateViewLayout(this.mOverlayViewContainer, this.mWindowParams);
        }

        void removeOverlayView(boolean z) {
            if (z) {
                this.mWindowToken = null;
                this.mOverlayFrame = null;
            }
            FrameLayout frameLayout = this.mOverlayViewContainer;
            if (frameLayout != null) {
                frameLayout.removeView(this.mOverlayView);
                this.mOverlayView = null;
                this.mWindowManager.removeView(this.mOverlayViewContainer);
                this.mOverlayViewContainer = null;
                this.mWindowParams = null;
            }
        }

        void stopPlayback(int i) {
            onStopPlayback(i);
        }

        void resumePlayback() {
            onResumePlayback();
        }

        void setVideoFrozen(boolean z) {
            onSetVideoFrozen(z);
        }

        void timeShiftPlay(Uri uri) {
            this.mCurrentPositionMs = 0L;
            onTimeShiftPlay(uri);
        }

        void timeShiftPause() {
            onTimeShiftPause();
        }

        void timeShiftResume() {
            onTimeShiftResume();
        }

        void timeShiftSeekTo(long j) {
            onTimeShiftSeekTo(j);
        }

        void timeShiftSetPlaybackParams(PlaybackParams playbackParams) {
            onTimeShiftSetPlaybackParams(playbackParams);
        }

        void timeShiftSetMode(int i) {
            onTimeShiftSetMode(i);
        }

        void timeShiftEnablePositionTracking(boolean z) {
            if (z) {
                this.mHandler.post(this.mTimeShiftPositionTrackingRunnable);
                return;
            }
            this.mHandler.removeCallbacks(this.mTimeShiftPositionTrackingRunnable);
            this.mStartPositionMs = Long.MIN_VALUE;
            this.mCurrentPositionMs = Long.MIN_VALUE;
        }

        void scheduleOverlayViewCleanup() {
            FrameLayout frameLayout = this.mOverlayViewContainer;
            if (frameLayout != null) {
                OverlayViewCleanUpTask overlayViewCleanUpTask = new OverlayViewCleanUpTask();
                this.mOverlayViewCleanUpTask = overlayViewCleanUpTask;
                overlayViewCleanUpTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, frameLayout);
            }
        }

        void requestBroadcastInfo(BroadcastInfoRequest broadcastInfoRequest) {
            onRequestBroadcastInfo(broadcastInfoRequest);
        }

        void removeBroadcastInfo(int i) {
            onRemoveBroadcastInfo(i);
        }

        void requestAd(AdRequest adRequest) {
            onRequestAd(adRequest);
        }

        void notifyAdBufferReady(AdBuffer adBuffer) {
            onAdBufferReady(adBuffer);
        }

        void notifyTvAdSessionData(String str, Bundle bundle) {
            onTvAdSessionData(str, bundle);
        }

        void onTvMessageReceived(int i, Bundle bundle) {
            onTvMessage(i, bundle);
        }

        int dispatchInputEvent(InputEvent inputEvent, InputEventReceiver inputEventReceiver) {
            boolean z;
            boolean z2;
            if (inputEvent instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) inputEvent;
                if (keyEvent.dispatch(this, this.mDispatcherState, this)) {
                    return 1;
                }
                z2 = TvInputService.isNavigationKey(keyEvent.getKeyCode());
                z = KeyEvent.isMediaSessionKey(keyEvent.getKeyCode()) || keyEvent.getKeyCode() == 222;
            } else {
                if (inputEvent instanceof MotionEvent) {
                    MotionEvent motionEvent = (MotionEvent) inputEvent;
                    int source = motionEvent.getSource();
                    if (motionEvent.isTouchEvent()) {
                        if (onTouchEvent(motionEvent)) {
                            return 1;
                        }
                    } else if ((source & 4) != 0) {
                        if (onTrackballEvent(motionEvent)) {
                            return 1;
                        }
                    } else if (onGenericMotionEvent(motionEvent)) {
                        return 1;
                    }
                }
                z = false;
                z2 = false;
            }
            FrameLayout frameLayout = this.mOverlayViewContainer;
            if (frameLayout == null || !frameLayout.isAttachedToWindow() || z) {
                return 0;
            }
            if (!this.mOverlayViewContainer.hasWindowFocus()) {
                this.mOverlayViewContainer.getViewRootImpl().windowFocusChanged(true);
            }
            if (z2 && this.mOverlayViewContainer.hasFocusable()) {
                this.mOverlayViewContainer.getViewRootImpl().dispatchInputEvent(inputEvent);
                return 1;
            }
            this.mOverlayViewContainer.getViewRootImpl().dispatchInputEvent(inputEvent, inputEventReceiver);
            return -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize(ITvInputSessionCallback iTvInputSessionCallback) {
            synchronized (this.mLock) {
                this.mSessionCallback = iTvInputSessionCallback;
                Iterator<Runnable> it = this.mPendingActions.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
                this.mPendingActions.clear();
            }
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

        private final class TimeShiftPositionTrackingRunnable implements Runnable {
            private TimeShiftPositionTrackingRunnable() {
            }

            @Override // java.lang.Runnable
            public void run() {
                long onTimeShiftGetStartPosition = Session.this.onTimeShiftGetStartPosition();
                if (Session.this.mStartPositionMs == Long.MIN_VALUE || Session.this.mStartPositionMs != onTimeShiftGetStartPosition) {
                    Session.this.mStartPositionMs = onTimeShiftGetStartPosition;
                    Session.this.notifyTimeShiftStartPositionChanged(onTimeShiftGetStartPosition);
                }
                long onTimeShiftGetCurrentPosition = Session.this.onTimeShiftGetCurrentPosition();
                if (onTimeShiftGetCurrentPosition < Session.this.mStartPositionMs) {
                    Log.w(TvInputService.TAG, "Current position (" + onTimeShiftGetCurrentPosition + ") cannot be earlier than start position (" + Session.this.mStartPositionMs + "). Reset to the start position.");
                    onTimeShiftGetCurrentPosition = Session.this.mStartPositionMs;
                }
                if (Session.this.mCurrentPositionMs == Long.MIN_VALUE || Session.this.mCurrentPositionMs != onTimeShiftGetCurrentPosition) {
                    Session.this.mCurrentPositionMs = onTimeShiftGetCurrentPosition;
                    Session.this.notifyTimeShiftCurrentPositionChanged(onTimeShiftGetCurrentPosition);
                }
                Session.this.mHandler.removeCallbacks(Session.this.mTimeShiftPositionTrackingRunnable);
                Session.this.mHandler.postDelayed(Session.this.mTimeShiftPositionTrackingRunnable, 1000L);
            }
        }
    }

    private static final class OverlayViewCleanUpTask extends AsyncTask<View, Void, Void> {
        private OverlayViewCleanUpTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(View... viewArr) {
            View view = viewArr[0];
            try {
                Thread.sleep(5000L);
                if (!isCancelled() && view.isAttachedToWindow()) {
                    Log.e(TvInputService.TAG, "Time out on releasing overlay view. Killing " + view.getContext().getPackageName());
                    Process.killProcess(Process.myPid());
                }
            } catch (InterruptedException unused) {
            }
            return null;
        }
    }

    public static abstract class RecordingSession {
        final Handler mHandler;
        private final Object mLock = new Object();
        private final List<Runnable> mPendingActions = new ArrayList();
        private ITvInputSessionCallback mSessionCallback;

        public void onAppPrivateCommand(String str, Bundle bundle) {
        }

        public void onPauseRecording(Bundle bundle) {
        }

        public abstract void onRelease();

        public void onResumeRecording(Bundle bundle) {
        }

        public abstract void onStartRecording(Uri uri);

        public abstract void onStopRecording();

        public abstract void onTune(Uri uri);

        public RecordingSession(Context context) {
            this.mHandler = new Handler(context.getMainLooper());
        }

        public void notifyTuned(final Uri uri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onTuned(uri);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTuned", e);
                    }
                }
            });
        }

        public void notifyRecordingStopped(final Uri uri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onRecordingStopped(uri);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyRecordingStopped", e);
                    }
                }
            });
        }

        public void notifyError(final int i) {
            if (i < 0 || i > 2) {
                Log.w(TvInputService.TAG, "notifyError - invalid error code (" + i + ") is changed to RECORDING_ERROR_UNKNOWN.");
                i = 0;
            }
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onError(i);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyError", e);
                    }
                }
            });
        }

        @SystemApi
        public void notifySessionEvent(final String str, final Bundle bundle) {
            Preconditions.checkNotNull(str);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onSessionEvent(str, bundle);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in sending event (event=" + str + NavigationBarInflaterView.KEY_CODE_END, e);
                    }
                }
            });
        }

        public void onTune(Uri uri, Bundle bundle) {
            onTune(uri);
        }

        public void onStartRecording(Uri uri, Bundle bundle) {
            onStartRecording(uri);
        }

        void tune(Uri uri, Bundle bundle) {
            onTune(uri, bundle);
        }

        void release() {
            onRelease();
        }

        void startRecording(Uri uri, Bundle bundle) {
            onStartRecording(uri, bundle);
        }

        void stopRecording() {
            onStopRecording();
        }

        void pauseRecording(Bundle bundle) {
            onPauseRecording(bundle);
        }

        void resumeRecording(Bundle bundle) {
            onResumeRecording(bundle);
        }

        void appPrivateCommand(String str, Bundle bundle) {
            onAppPrivateCommand(str, bundle);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize(ITvInputSessionCallback iTvInputSessionCallback) {
            synchronized (this.mLock) {
                this.mSessionCallback = iTvInputSessionCallback;
                Iterator<Runnable> it = this.mPendingActions.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
                this.mPendingActions.clear();
            }
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
    }

    public static abstract class HardwareSession extends Session {
        private TvInputManager.Session mHardwareSession;
        private final TvInputManager.SessionCallback mHardwareSessionCallback;
        private ITvInputSession mProxySession;
        private ITvInputSessionCallback mProxySessionCallback;
        private Handler mServiceHandler;

        public abstract String getHardwareInputId();

        public void onHardwareVideoAvailable() {
        }

        public void onHardwareVideoUnavailable(int i) {
        }

        public HardwareSession(Context context) {
            super(context);
            this.mHardwareSessionCallback = new TvInputManager.SessionCallback() { // from class: android.media.tv.TvInputService.HardwareSession.1
                @Override // android.media.tv.TvInputManager.SessionCallback
                public void onSessionCreated(TvInputManager.Session session) {
                    HardwareSession.this.mHardwareSession = session;
                    SomeArgs obtain = SomeArgs.obtain();
                    if (session != null) {
                        obtain.arg1 = HardwareSession.this;
                        obtain.arg2 = HardwareSession.this.mProxySession;
                        obtain.arg3 = HardwareSession.this.mProxySessionCallback;
                        obtain.arg4 = session.getToken();
                        session.tune(TvContract.buildChannelUriForPassthroughInput(HardwareSession.this.getHardwareInputId()));
                    } else {
                        obtain.arg1 = null;
                        obtain.arg2 = null;
                        obtain.arg3 = HardwareSession.this.mProxySessionCallback;
                        obtain.arg4 = null;
                        HardwareSession.this.onRelease();
                    }
                    HardwareSession.this.mServiceHandler.obtainMessage(2, obtain).sendToTarget();
                }

                @Override // android.media.tv.TvInputManager.SessionCallback
                public void onVideoAvailable(TvInputManager.Session session) {
                    if (HardwareSession.this.mHardwareSession == session) {
                        HardwareSession.this.onHardwareVideoAvailable();
                    }
                }

                @Override // android.media.tv.TvInputManager.SessionCallback
                public void onVideoUnavailable(TvInputManager.Session session, int i) {
                    if (HardwareSession.this.mHardwareSession == session) {
                        HardwareSession.this.onHardwareVideoUnavailable(i);
                    }
                }
            };
        }

        @Override // android.media.tv.TvInputService.Session
        public final boolean onSetSurface(Surface surface) {
            Log.e(TvInputService.TAG, "onSetSurface() should not be called in HardwareProxySession.");
            return false;
        }

        @Override // android.media.tv.TvInputService.Session
        void release() {
            TvInputManager.Session session = this.mHardwareSession;
            if (session != null) {
                session.release();
                this.mHardwareSession = null;
            }
            super.release();
        }
    }

    private final class ServiceHandler extends Handler {
        private static final int DO_ADD_HARDWARE_INPUT = 4;
        private static final int DO_ADD_HDMI_INPUT = 6;
        private static final int DO_CREATE_RECORDING_SESSION = 3;
        private static final int DO_CREATE_SESSION = 1;
        private static final int DO_NOTIFY_SESSION_CREATED = 2;
        private static final int DO_REMOVE_HARDWARE_INPUT = 5;
        private static final int DO_REMOVE_HDMI_INPUT = 7;
        private static final int DO_UPDATE_HDMI_INPUT = 8;

        private ServiceHandler() {
        }

        private void broadcastAddHardwareInput(int i, TvInputInfo tvInputInfo) {
            int beginBroadcast = TvInputService.this.mCallbacks.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    ((ITvInputServiceCallback) TvInputService.this.mCallbacks.getBroadcastItem(i2)).addHardwareInput(i, tvInputInfo);
                } catch (RemoteException e) {
                    Log.e(TvInputService.TAG, "error in broadcastAddHardwareInput", e);
                }
            }
            TvInputService.this.mCallbacks.finishBroadcast();
        }

        private void broadcastAddHdmiInput(int i, TvInputInfo tvInputInfo) {
            int beginBroadcast = TvInputService.this.mCallbacks.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    ((ITvInputServiceCallback) TvInputService.this.mCallbacks.getBroadcastItem(i2)).addHdmiInput(i, tvInputInfo);
                } catch (RemoteException e) {
                    Log.e(TvInputService.TAG, "error in broadcastAddHdmiInput", e);
                }
            }
            TvInputService.this.mCallbacks.finishBroadcast();
        }

        private void broadcastRemoveHardwareInput(String str) {
            int beginBroadcast = TvInputService.this.mCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    ((ITvInputServiceCallback) TvInputService.this.mCallbacks.getBroadcastItem(i)).removeHardwareInput(str);
                } catch (RemoteException e) {
                    Log.e(TvInputService.TAG, "error in broadcastRemoveHardwareInput", e);
                }
            }
            TvInputService.this.mCallbacks.finishBroadcast();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    InputChannel inputChannel = (InputChannel) someArgs.arg1;
                    ITvInputSessionCallback iTvInputSessionCallback = (ITvInputSessionCallback) someArgs.arg2;
                    String str = (String) someArgs.arg3;
                    String str2 = (String) someArgs.arg4;
                    AttributionSource attributionSource = (AttributionSource) someArgs.arg5;
                    someArgs.recycle();
                    Session onCreateSession = TvInputService.this.onCreateSession(str, str2, attributionSource);
                    if (onCreateSession == null) {
                        try {
                            iTvInputSessionCallback.onSessionCreated(null, null);
                            break;
                        } catch (RemoteException e) {
                            Log.e(TvInputService.TAG, "error in onSessionCreated", e);
                            return;
                        }
                    } else {
                        ITvInputSessionWrapper iTvInputSessionWrapper = new ITvInputSessionWrapper(TvInputService.this, onCreateSession, inputChannel);
                        if (onCreateSession instanceof HardwareSession) {
                            HardwareSession hardwareSession = (HardwareSession) onCreateSession;
                            String hardwareInputId = hardwareSession.getHardwareInputId();
                            if (TextUtils.isEmpty(hardwareInputId) || !TvInputService.this.isPassthroughInput(hardwareInputId)) {
                                if (TextUtils.isEmpty(hardwareInputId)) {
                                    Log.w(TvInputService.TAG, "Hardware input id is not setup yet.");
                                } else {
                                    Log.w(TvInputService.TAG, "Invalid hardware input id : " + hardwareInputId);
                                }
                                onCreateSession.onRelease();
                                try {
                                    iTvInputSessionCallback.onSessionCreated(null, null);
                                    break;
                                } catch (RemoteException e2) {
                                    Log.e(TvInputService.TAG, "error in onSessionCreated", e2);
                                    return;
                                }
                            } else {
                                hardwareSession.mProxySession = iTvInputSessionWrapper;
                                hardwareSession.mProxySessionCallback = iTvInputSessionCallback;
                                hardwareSession.mServiceHandler = TvInputService.this.mServiceHandler;
                                ((TvInputManager) TvInputService.this.getSystemService(Context.TV_INPUT_SERVICE)).createSession(hardwareInputId, attributionSource, hardwareSession.mHardwareSessionCallback, TvInputService.this.mServiceHandler);
                                break;
                            }
                        } else {
                            SomeArgs obtain = SomeArgs.obtain();
                            obtain.arg1 = onCreateSession;
                            obtain.arg2 = iTvInputSessionWrapper;
                            obtain.arg3 = iTvInputSessionCallback;
                            obtain.arg4 = null;
                            TvInputService.this.mServiceHandler.obtainMessage(2, obtain).sendToTarget();
                            break;
                        }
                    }
                    break;
                case 2:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    Session session = (Session) someArgs2.arg1;
                    ITvInputSession iTvInputSession = (ITvInputSession) someArgs2.arg2;
                    ITvInputSessionCallback iTvInputSessionCallback2 = (ITvInputSessionCallback) someArgs2.arg3;
                    try {
                        iTvInputSessionCallback2.onSessionCreated(iTvInputSession, (IBinder) someArgs2.arg4);
                    } catch (RemoteException e3) {
                        Log.e(TvInputService.TAG, "error in onSessionCreated", e3);
                    }
                    if (session != null) {
                        session.initialize(iTvInputSessionCallback2);
                    }
                    someArgs2.recycle();
                    break;
                case 3:
                    SomeArgs someArgs3 = (SomeArgs) message.obj;
                    ITvInputSessionCallback iTvInputSessionCallback3 = (ITvInputSessionCallback) someArgs3.arg1;
                    String str3 = (String) someArgs3.arg2;
                    String str4 = (String) someArgs3.arg3;
                    someArgs3.recycle();
                    RecordingSession onCreateRecordingSession = TvInputService.this.onCreateRecordingSession(str3, str4);
                    if (onCreateRecordingSession == null) {
                        try {
                            iTvInputSessionCallback3.onSessionCreated(null, null);
                            break;
                        } catch (RemoteException e4) {
                            Log.e(TvInputService.TAG, "error in onSessionCreated", e4);
                            return;
                        }
                    } else {
                        try {
                            iTvInputSessionCallback3.onSessionCreated(new ITvInputSessionWrapper(TvInputService.this, onCreateRecordingSession), null);
                        } catch (RemoteException e5) {
                            Log.e(TvInputService.TAG, "error in onSessionCreated", e5);
                        }
                        onCreateRecordingSession.initialize(iTvInputSessionCallback3);
                        break;
                    }
                case 4:
                    TvInputHardwareInfo tvInputHardwareInfo = (TvInputHardwareInfo) message.obj;
                    TvInputInfo onHardwareAdded = TvInputService.this.onHardwareAdded(tvInputHardwareInfo);
                    if (onHardwareAdded != null) {
                        broadcastAddHardwareInput(tvInputHardwareInfo.getDeviceId(), onHardwareAdded);
                        break;
                    }
                    break;
                case 5:
                    String onHardwareRemoved = TvInputService.this.onHardwareRemoved((TvInputHardwareInfo) message.obj);
                    if (onHardwareRemoved != null) {
                        broadcastRemoveHardwareInput(onHardwareRemoved);
                        break;
                    }
                    break;
                case 6:
                    HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) message.obj;
                    TvInputInfo onHdmiDeviceAdded = TvInputService.this.onHdmiDeviceAdded(hdmiDeviceInfo);
                    if (onHdmiDeviceAdded != null) {
                        broadcastAddHdmiInput(hdmiDeviceInfo.getId(), onHdmiDeviceAdded);
                        break;
                    }
                    break;
                case 7:
                    String onHdmiDeviceRemoved = TvInputService.this.onHdmiDeviceRemoved((HdmiDeviceInfo) message.obj);
                    if (onHdmiDeviceRemoved != null) {
                        broadcastRemoveHardwareInput(onHdmiDeviceRemoved);
                        break;
                    }
                    break;
                case 8:
                    TvInputService.this.onHdmiDeviceUpdated((HdmiDeviceInfo) message.obj);
                    break;
                default:
                    Log.w(TvInputService.TAG, "Unhandled message code: " + message.what);
                    break;
            }
        }
    }
}
