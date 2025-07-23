package android.media.tv;

import android.annotation.SystemApi;
import android.content.Context;
import android.media.tv.TvInputManager;
import android.media.tv.interactive.TvInteractiveAppView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

/* loaded from: classes3.dex */
public class TvRecordingClient {
    private static final boolean DEBUG = false;
    private static final String TAG = "TvRecordingClient";
    private final RecordingCallback mCallback;
    private final Handler mHandler;
    private boolean mIsPaused;
    private boolean mIsRecordingStarted;
    private boolean mIsRecordingStopping;
    private boolean mIsTuned;
    private final Queue<Pair<String, Bundle>> mPendingAppPrivateCommands = new ArrayDeque();
    private String mRecordingId;
    private TvInputManager.Session mSession;
    private MySessionCallback mSessionCallback;
    private TvInteractiveAppView mTvIAppView;
    private final TvInputManager mTvInputManager;

    public static abstract class RecordingCallback {
        public void onConnectionFailed(String str) {
        }

        public void onDisconnected(String str) {
        }

        public void onError(int i) {
        }

        @SystemApi
        public void onEvent(String str, String str2, Bundle bundle) {
        }

        public void onRecordingStopped(Uri uri) {
        }

        public void onTuned(Uri uri) {
        }
    }

    public TvRecordingClient(Context context, String str, RecordingCallback recordingCallback, Handler handler) {
        this.mCallback = recordingCallback;
        this.mHandler = handler == null ? new Handler(Looper.getMainLooper()) : handler;
        this.mTvInputManager = (TvInputManager) context.getSystemService(Context.TV_INPUT_SERVICE);
    }

    public void setTvInteractiveAppView(TvInteractiveAppView tvInteractiveAppView, String str) {
        if (tvInteractiveAppView != null && str == null) {
            throw new IllegalArgumentException("null recordingId is not allowed only when the view is not null");
        }
        if (tvInteractiveAppView == null && str != null) {
            throw new IllegalArgumentException("recordingId should be null when the view is null");
        }
        this.mTvIAppView = tvInteractiveAppView;
        this.mRecordingId = str;
    }

    public void tune(String str, Uri uri) {
        tune(str, uri, null);
    }

    public void tune(String str, Uri uri, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("inputId cannot be null or an empty string");
        }
        if (this.mIsRecordingStarted && !this.mIsPaused) {
            throw new IllegalStateException("tune failed - recording already started");
        }
        MySessionCallback mySessionCallback = this.mSessionCallback;
        if (mySessionCallback != null && TextUtils.equals(mySessionCallback.mInputId, str)) {
            if (this.mSession != null) {
                this.mSessionCallback.mChannelUri = uri;
                this.mSession.tune(uri, bundle);
            } else {
                this.mSessionCallback.mChannelUri = uri;
                this.mSessionCallback.mConnectionParams = bundle;
            }
            this.mIsTuned = false;
            return;
        }
        if (this.mIsPaused) {
            throw new IllegalStateException("tune failed - inputId is changed during pause");
        }
        resetInternal();
        MySessionCallback mySessionCallback2 = new MySessionCallback(str, uri, bundle);
        this.mSessionCallback = mySessionCallback2;
        TvInputManager tvInputManager = this.mTvInputManager;
        if (tvInputManager != null) {
            tvInputManager.createRecordingSession(str, mySessionCallback2, this.mHandler);
        }
    }

    public void release() {
        resetInternal();
    }

    private void resetInternal() {
        this.mSessionCallback = null;
        this.mPendingAppPrivateCommands.clear();
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.release();
            this.mIsTuned = false;
            this.mIsRecordingStarted = false;
            this.mIsPaused = false;
            this.mIsRecordingStopping = false;
            this.mSession = null;
        }
    }

    public void startRecording(Uri uri) {
        startRecording(uri, Bundle.EMPTY);
    }

    public void startRecording(Uri uri, Bundle bundle) {
        if (this.mIsRecordingStopping || !this.mIsTuned || this.mIsPaused) {
            throw new IllegalStateException("startRecording failed -recording not yet stopped or not yet tuned or paused");
        }
        if (this.mIsRecordingStarted) {
            Log.w(TAG, "startRecording failed - recording already started");
        }
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.startRecording(uri, bundle);
            this.mIsRecordingStarted = true;
        }
    }

    public void stopRecording() {
        if (!this.mIsRecordingStarted) {
            Log.w(TAG, "stopRecording failed - recording not yet started");
        }
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.stopRecording();
            if (this.mIsRecordingStarted) {
                this.mIsRecordingStopping = true;
            }
        }
    }

    public void pauseRecording() {
        pauseRecording(Bundle.EMPTY);
    }

    public void pauseRecording(Bundle bundle) {
        if (!this.mIsRecordingStarted || this.mIsRecordingStopping) {
            throw new IllegalStateException("pauseRecording failed - recording not yet started or stopping");
        }
        TvInputInfo tvInputInfo = this.mTvInputManager.getTvInputInfo(this.mSessionCallback.mInputId);
        if (tvInputInfo == null || !tvInputInfo.canPauseRecording()) {
            throw new UnsupportedOperationException("pauseRecording failed - operation not supported");
        }
        if (this.mIsPaused) {
            Log.w(TAG, "pauseRecording failed - recording already paused");
        }
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.pauseRecording(bundle);
            this.mIsPaused = true;
        }
    }

    public void resumeRecording() {
        resumeRecording(Bundle.EMPTY);
    }

    public void resumeRecording(Bundle bundle) {
        if (!this.mIsRecordingStarted || this.mIsRecordingStopping || !this.mIsTuned) {
            throw new IllegalStateException("resumeRecording failed - recording not yet started or stopping or not yet tuned");
        }
        if (!this.mIsPaused) {
            Log.w(TAG, "resumeRecording failed - recording not yet paused");
        }
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.resumeRecording(bundle);
            this.mIsPaused = false;
        }
    }

    public void sendAppPrivateCommand(String str, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("action cannot be null or an empty string");
        }
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.sendAppPrivateCommand(str, bundle);
            return;
        }
        Log.w(TAG, "sendAppPrivateCommand - session not yet created (action \"" + str + "\" pending)");
        this.mPendingAppPrivateCommands.add(Pair.create(str, bundle));
    }

    public TvInputManager.SessionCallback getSessionCallback() {
        return this.mSessionCallback;
    }

    private class MySessionCallback extends TvInputManager.SessionCallback {
        Uri mChannelUri;
        Bundle mConnectionParams;
        final String mInputId;

        MySessionCallback(String str, Uri uri, Bundle bundle) {
            this.mInputId = str;
            this.mChannelUri = uri;
            this.mConnectionParams = bundle;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionCreated(TvInputManager.Session session) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onSessionCreated - session already created");
                if (session != null) {
                    session.release();
                    return;
                }
                return;
            }
            TvRecordingClient.this.mSession = session;
            if (session != null) {
                for (Pair pair : TvRecordingClient.this.mPendingAppPrivateCommands) {
                    TvRecordingClient.this.mSession.sendAppPrivateCommand((String) pair.first, (Bundle) pair.second);
                }
                TvRecordingClient.this.mPendingAppPrivateCommands.clear();
                TvRecordingClient.this.mSession.tune(this.mChannelUri, this.mConnectionParams);
                return;
            }
            TvRecordingClient.this.mSessionCallback = null;
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onConnectionFailed(this.mInputId);
            }
            if (TvRecordingClient.this.mTvIAppView != null) {
                TvRecordingClient.this.mTvIAppView.notifyRecordingConnectionFailed(TvRecordingClient.this.mRecordingId, this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTuned(TvInputManager.Session session, Uri uri) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onTuned - session not created");
                return;
            }
            if (TvRecordingClient.this.mIsTuned || !Objects.equals(this.mChannelUri, uri)) {
                Log.w(TvRecordingClient.TAG, "onTuned - already tuned or not yet tuned to last channel");
                return;
            }
            TvRecordingClient.this.mIsTuned = true;
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onTuned(uri);
            }
            if (TvRecordingClient.this.mTvIAppView != null) {
                TvRecordingClient.this.mTvIAppView.notifyRecordingTuned(TvRecordingClient.this.mRecordingId, uri);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionReleased(TvInputManager.Session session) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onSessionReleased - session not created");
                return;
            }
            TvRecordingClient.this.mIsTuned = false;
            TvRecordingClient.this.mIsRecordingStarted = false;
            TvRecordingClient.this.mIsPaused = false;
            TvRecordingClient.this.mIsRecordingStopping = false;
            TvRecordingClient.this.mSessionCallback = null;
            TvRecordingClient.this.mSession = null;
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onDisconnected(this.mInputId);
            }
            if (TvRecordingClient.this.mTvIAppView != null) {
                TvRecordingClient.this.mTvIAppView.notifyRecordingDisconnected(TvRecordingClient.this.mRecordingId, this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onRecordingStopped(TvInputManager.Session session, Uri uri) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onRecordingStopped - session not created");
                return;
            }
            if (!TvRecordingClient.this.mIsRecordingStarted) {
                Log.w(TvRecordingClient.TAG, "onRecordingStopped - recording not yet started");
                return;
            }
            TvRecordingClient.this.mIsRecordingStarted = false;
            TvRecordingClient.this.mIsPaused = false;
            TvRecordingClient.this.mIsRecordingStopping = false;
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onRecordingStopped(uri);
            }
            if (TvRecordingClient.this.mTvIAppView != null) {
                TvRecordingClient.this.mTvIAppView.notifyRecordingStopped(TvRecordingClient.this.mRecordingId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onError(TvInputManager.Session session, int i) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onError - session not created");
                return;
            }
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onError(i);
            }
            if (TvRecordingClient.this.mTvIAppView != null) {
                TvRecordingClient.this.mTvIAppView.notifyRecordingError(TvRecordingClient.this.mRecordingId, i);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionEvent(TvInputManager.Session session, String str, Bundle bundle) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onSessionEvent - session not created");
            } else if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onEvent(this.mInputId, str, bundle);
            }
        }
    }
}
