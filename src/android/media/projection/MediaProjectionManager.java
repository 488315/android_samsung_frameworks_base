package android.media.projection;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.media.projection.IMediaProjectionWatcherCallback;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ContentRecordingSession;
import com.android.internal.R;
import java.util.Map;

/* loaded from: classes3.dex */
public final class MediaProjectionManager {
    public static final String EXTRA_APP_TOKEN = "android.media.projection.extra.EXTRA_APP_TOKEN";
    public static final String EXTRA_LAUNCH_COOKIE = "android.media.projection.extra.EXTRA_LAUNCH_COOKIE";
    public static final String EXTRA_MEDIA_PROJECTION = "android.media.projection.extra.EXTRA_MEDIA_PROJECTION";
    public static final String EXTRA_MEDIA_PROJECTION_CONFIG = "android.media.projection.extra.EXTRA_MEDIA_PROJECTION_CONFIG";
    public static final long OVERRIDE_DISABLE_MEDIA_PROJECTION_SINGLE_APP_OPTION = 316897322;
    private static final String TAG = "MediaProjectionManager";
    public static final int TYPE_MIRRORING = 1;
    public static final int TYPE_PRESENTATION = 2;
    public static final int TYPE_SCREEN_CAPTURE = 0;
    private Context mContext;
    private IMediaProjectionManager mService = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService(Context.MEDIA_PROJECTION_SERVICE));
    private Map<Callback, CallbackDelegate> mCallbacks = new ArrayMap();

    public static abstract class Callback {
        public void onMediaProjectionEvent(MediaProjectionEvent mediaProjectionEvent, MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) {
        }

        public void onRecordingSessionSet(MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) {
        }

        public abstract void onStart(MediaProjectionInfo mediaProjectionInfo);

        public abstract void onStop(MediaProjectionInfo mediaProjectionInfo);
    }

    public MediaProjectionManager(Context context) {
        this.mContext = context;
    }

    public Intent createScreenCaptureIntent() {
        Intent intent = new Intent();
        intent.setComponent(ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.config_mediaProjectionPermissionDialogComponent)));
        intent.putExtra("Userid", this.mContext.getUserId());
        return intent;
    }

    public Intent createScreenCaptureIntent(MediaProjectionConfig mediaProjectionConfig) {
        Intent intentCreateScreenCaptureIntent = createScreenCaptureIntent();
        intentCreateScreenCaptureIntent.putExtra(EXTRA_MEDIA_PROJECTION_CONFIG, mediaProjectionConfig);
        return intentCreateScreenCaptureIntent;
    }

    public Intent createScreenCaptureIntent(ActivityOptions.LaunchCookie launchCookie) {
        Intent intentCreateScreenCaptureIntent = createScreenCaptureIntent();
        intentCreateScreenCaptureIntent.putExtra(EXTRA_LAUNCH_COOKIE, launchCookie);
        return intentCreateScreenCaptureIntent;
    }

    public MediaProjection semGetMediaProjection(int i) {
        String packageName = this.mContext.getPackageName();
        try {
            try {
                IMediaProjection iMediaProjectionCreateProjection = this.mService.createProjection(this.mContext.getPackageManager().getPackageUid(packageName, 0), packageName, 0, false, i);
                if (iMediaProjectionCreateProjection == null) {
                    Log.e(TAG, "Can't create projection");
                    return null;
                }
                return new MediaProjection(this.mContext, iMediaProjectionCreateProjection);
            } catch (RemoteException e) {
                Log.e(TAG, "unable to create projection", e);
                return null;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e(TAG, "unable to look up package name", e2);
            return null;
        }
    }

    public MediaProjection semGetMediaProjection() {
        return semGetMediaProjection(0);
    }

    public MediaProjection getMediaProjection(int i, Intent intent) {
        IBinder iBinderExtra;
        if (i != -1 || intent == null || (iBinderExtra = intent.getIBinderExtra(EXTRA_MEDIA_PROJECTION)) == null) {
            return null;
        }
        return new MediaProjection(this.mContext, IMediaProjection.Stub.asInterface(iBinderExtra));
    }

    public MediaProjectionInfo getActiveProjectionInfo() {
        try {
            return this.mService.getActiveProjectionInfo();
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to get the active projection info", e);
            return null;
        }
    }

    public void stopActiveProjection(int i) {
        try {
            Log.d(TAG, "Content Recording: stopping active projection");
            this.mService.stopActiveProjection(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to stop the currently active media projection", e);
        }
    }

    public void addCallback(Callback callback, Handler handler) {
        if (callback == null) {
            Log.w(TAG, "Content Recording: cannot add null callback");
            throw new IllegalArgumentException("callback must not be null");
        }
        CallbackDelegate callbackDelegate = new CallbackDelegate(callback, handler);
        this.mCallbacks.put(callback, callbackDelegate);
        try {
            this.mService.addCallback(callbackDelegate);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to add callbacks to MediaProjection service", e);
        }
    }

    public void removeCallback(Callback callback) {
        if (callback == null) {
            Log.w(TAG, "ContentRecording: cannot remove null callback");
            throw new IllegalArgumentException("callback must not be null");
        }
        CallbackDelegate callbackDelegateRemove = this.mCallbacks.remove(callback);
        if (callbackDelegateRemove != null) {
            try {
                this.mService.removeCallback(callbackDelegateRemove);
            } catch (RemoteException e) {
                Log.e(TAG, "Unable to add callbacks to MediaProjection service", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CallbackDelegate extends IMediaProjectionWatcherCallback.Stub {
        private Callback mCallback;
        private Handler mHandler;

        public CallbackDelegate(Callback callback, Handler handler) {
            this.mCallback = callback;
            this.mHandler = handler == null ? new Handler() : handler;
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onStart(final MediaProjectionInfo mediaProjectionInfo) {
            this.mHandler.post(new Runnable() { // from class: android.media.projection.MediaProjectionManager.CallbackDelegate.1
                @Override // java.lang.Runnable
                public void run() {
                    CallbackDelegate.this.mCallback.onStart(mediaProjectionInfo);
                }
            });
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onStop(final MediaProjectionInfo mediaProjectionInfo) {
            this.mHandler.post(new Runnable() { // from class: android.media.projection.MediaProjectionManager.CallbackDelegate.2
                @Override // java.lang.Runnable
                public void run() {
                    CallbackDelegate.this.mCallback.onStop(mediaProjectionInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRecordingSessionSet$0(MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) {
            this.mCallback.onRecordingSessionSet(mediaProjectionInfo, contentRecordingSession);
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onRecordingSessionSet(final MediaProjectionInfo mediaProjectionInfo, final ContentRecordingSession contentRecordingSession) {
            this.mHandler.post(new Runnable() { // from class: android.media.projection.MediaProjectionManager$CallbackDelegate$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onRecordingSessionSet$0(mediaProjectionInfo, contentRecordingSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMediaProjectionEvent$1(MediaProjectionEvent mediaProjectionEvent, MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) {
            this.mCallback.onMediaProjectionEvent(mediaProjectionEvent, mediaProjectionInfo, contentRecordingSession);
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onMediaProjectionEvent(final MediaProjectionEvent mediaProjectionEvent, final MediaProjectionInfo mediaProjectionInfo, final ContentRecordingSession contentRecordingSession) {
            this.mHandler.post(new Runnable() { // from class: android.media.projection.MediaProjectionManager$CallbackDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onMediaProjectionEvent$1(mediaProjectionEvent, mediaProjectionInfo, contentRecordingSession);
                }
            });
        }
    }
}
