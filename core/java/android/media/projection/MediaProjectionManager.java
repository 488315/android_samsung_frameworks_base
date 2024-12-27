package android.media.projection;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.media.projection.IMediaProjectionWatcherCallback;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ContentRecordingSession;
import com.android.internal.R;
import java.util.Map;

/* loaded from: classes2.dex */
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
    private Map<Callback, CallbackDelegate> mCallbacks;
    private Context mContext;
    private IMediaProjectionManager mService;

    public MediaProjectionManager(Context context) {
        this.mContext = context;
        IBinder b = ServiceManager.getService(Context.MEDIA_PROJECTION_SERVICE);
        this.mService = IMediaProjectionManager.Stub.asInterface(b);
        this.mCallbacks = new ArrayMap();
    }

    public Intent createScreenCaptureIntent() {
        Intent i = new Intent();
        ComponentName mediaProjectionPermissionDialogComponent = ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.config_mediaProjectionPermissionDialogComponent));
        i.setComponent(mediaProjectionPermissionDialogComponent);
        i.putExtra("Userid", this.mContext.getUserId());
        return i;
    }

    public Intent createScreenCaptureIntent(MediaProjectionConfig config) {
        Intent i = createScreenCaptureIntent();
        i.putExtra(EXTRA_MEDIA_PROJECTION_CONFIG, config);
        return i;
    }

    public Intent createScreenCaptureIntent(ActivityOptions.LaunchCookie launchCookie) {
        Intent i = createScreenCaptureIntent();
        i.putExtra(EXTRA_LAUNCH_COOKIE, launchCookie);
        return i;
    }

    public MediaProjection semGetMediaProjection() {
        String packageName = this.mContext.getPackageName();
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            int uid = packageManager.getPackageUid(packageName, 0);
            try {
                IMediaProjection projection = this.mService.createProjection(uid, packageName, 0, false);
                if (projection == null) {
                    Log.e(TAG, "Can't create projection");
                    return null;
                }
                return new MediaProjection(this.mContext, projection);
            } catch (RemoteException e) {
                Log.e(TAG, "unable to create projection", e);
                return null;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e(TAG, "unable to look up package name", e2);
            return null;
        }
    }

    public MediaProjection getMediaProjection(int resultCode, Intent resultData) {
        IBinder projection;
        if (resultCode != -1 || resultData == null || (projection = resultData.getIBinderExtra(EXTRA_MEDIA_PROJECTION)) == null) {
            return null;
        }
        return new MediaProjection(this.mContext, IMediaProjection.Stub.asInterface(projection));
    }

    public MediaProjectionInfo getActiveProjectionInfo() {
        try {
            return this.mService.getActiveProjectionInfo();
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to get the active projection info", e);
            return null;
        }
    }

    public void stopActiveProjection() {
        try {
            Log.d(TAG, "Content Recording: stopping active projection");
            this.mService.stopActiveProjection();
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to stop the currently active media projection", e);
        }
    }

    public void addCallback(Callback callback, Handler handler) {
        if (callback == null) {
            Log.w(TAG, "Content Recording: cannot add null callback");
            throw new IllegalArgumentException("callback must not be null");
        }
        CallbackDelegate delegate = new CallbackDelegate(callback, handler);
        this.mCallbacks.put(callback, delegate);
        try {
            this.mService.addCallback(delegate);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to add callbacks to MediaProjection service", e);
        }
    }

    public void removeCallback(Callback callback) {
        if (callback == null) {
            Log.w(TAG, "ContentRecording: cannot remove null callback");
            throw new IllegalArgumentException("callback must not be null");
        }
        CallbackDelegate delegate = this.mCallbacks.remove(callback);
        if (delegate != null) {
            try {
                this.mService.removeCallback(delegate);
            } catch (RemoteException e) {
                Log.e(TAG, "Unable to add callbacks to MediaProjection service", e);
            }
        }
    }

    public static abstract class Callback {
        public abstract void onStart(MediaProjectionInfo mediaProjectionInfo);

        public abstract void onStop(MediaProjectionInfo mediaProjectionInfo);

        public void onRecordingSessionSet(MediaProjectionInfo info, ContentRecordingSession session) {
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
        public void onStart(final MediaProjectionInfo info) {
            this.mHandler.post(new Runnable() { // from class: android.media.projection.MediaProjectionManager.CallbackDelegate.1
                @Override // java.lang.Runnable
                public void run() {
                    CallbackDelegate.this.mCallback.onStart(info);
                }
            });
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onStop(final MediaProjectionInfo info) {
            this.mHandler.post(new Runnable() { // from class: android.media.projection.MediaProjectionManager.CallbackDelegate.2
                @Override // java.lang.Runnable
                public void run() {
                    CallbackDelegate.this.mCallback.onStop(info);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRecordingSessionSet$0(MediaProjectionInfo info, ContentRecordingSession session) {
            this.mCallback.onRecordingSessionSet(info, session);
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onRecordingSessionSet(final MediaProjectionInfo info, final ContentRecordingSession session) {
            this.mHandler.post(new Runnable() { // from class: android.media.projection.MediaProjectionManager$CallbackDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaProjectionManager.CallbackDelegate.this.lambda$onRecordingSessionSet$0(info, session);
                }
            });
        }
    }
}
