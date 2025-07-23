package androidx.media.session;

import android.app.ForegroundServiceStartNotAllowedException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.media.session.MediaSession;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.KeyEvent;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MediaButtonReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Api31 {
        private Api31() {
        }

        public static ForegroundServiceStartNotAllowedException castToForegroundServiceStartNotAllowedException(IllegalStateException illegalStateException) {
            return (ForegroundServiceStartNotAllowedException) illegalStateException;
        }

        public static boolean instanceOfForegroundServiceStartNotAllowedException(IllegalStateException illegalStateException) {
            return illegalStateException instanceof ForegroundServiceStartNotAllowedException;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MediaButtonConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
        public final Context mContext;
        public final Intent mIntent;
        public MediaBrowserCompat mMediaBrowser;
        public final BroadcastReceiver.PendingResult mPendingResult;

        public MediaButtonConnectionCallback(Context context, Intent intent, BroadcastReceiver.PendingResult pendingResult) {
            this.mContext = context;
            this.mIntent = intent;
            this.mPendingResult = pendingResult;
        }

        public final void finish() {
            Messenger messenger;
            MediaBrowserCompat.MediaBrowserImplApi26 mediaBrowserImplApi26 = this.mMediaBrowser.mImpl;
            MediaBrowserCompat.ServiceBinderWrapper serviceBinderWrapper = mediaBrowserImplApi26.mServiceBinderWrapper;
            if (serviceBinderWrapper != null && (messenger = mediaBrowserImplApi26.mCallbacksMessenger) != null) {
                try {
                    serviceBinderWrapper.sendRequest(7, null, messenger);
                } catch (RemoteException unused) {
                    Log.i("MediaBrowserCompat", "Remote error unregistering client messenger.");
                }
            }
            mediaBrowserImplApi26.mBrowserFwk.disconnect();
            this.mPendingResult.finish();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback
        public final void onConnected() {
            Context context = this.mContext;
            MediaBrowserCompat.MediaBrowserImplApi26 mediaBrowserImplApi26 = this.mMediaBrowser.mImpl;
            if (mediaBrowserImplApi26.mMediaSessionToken == null) {
                MediaSession.Token sessionToken = mediaBrowserImplApi26.mBrowserFwk.getSessionToken();
                Parcelable.Creator<MediaSessionCompat.Token> creator = MediaSessionCompat.Token.CREATOR;
                mediaBrowserImplApi26.mMediaSessionToken = sessionToken != null ? new MediaSessionCompat.Token(sessionToken, null) : null;
            }
            MediaControllerCompat mediaControllerCompat = new MediaControllerCompat(context, mediaBrowserImplApi26.mMediaSessionToken);
            KeyEvent keyEvent = (KeyEvent) this.mIntent.getParcelableExtra("android.intent.extra.KEY_EVENT");
            if (keyEvent == null) {
                throw new IllegalArgumentException("KeyEvent may not be null");
            }
            mediaControllerCompat.mImpl.mControllerFwk.dispatchMediaButtonEvent(keyEvent);
            finish();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback
        public final void onConnectionFailed() {
            finish();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback
        public final void onConnectionSuspended() {
            finish();
        }
    }

    public static ComponentName getServiceComponentByAction(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(str);
        intent.setPackage(context.getPackageName());
        List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 0);
        if (queryIntentServices.size() == 1) {
            ServiceInfo serviceInfo = queryIntentServices.get(0).serviceInfo;
            return new ComponentName(serviceInfo.packageName, serviceInfo.name);
        }
        if (queryIntentServices.isEmpty()) {
            return null;
        }
        StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Expected 1 service that handles ", str, ", found ");
        m.append(queryIntentServices.size());
        throw new IllegalStateException(m.toString());
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null || !"android.intent.action.MEDIA_BUTTON".equals(intent.getAction()) || !intent.hasExtra("android.intent.extra.KEY_EVENT")) {
            Log.d("MediaButtonReceiver", "Ignore unsupported intent: " + intent);
            return;
        }
        ComponentName serviceComponentByAction = getServiceComponentByAction(context, "android.intent.action.MEDIA_BUTTON");
        if (serviceComponentByAction != null) {
            intent.setComponent(serviceComponentByAction);
            try {
                context.startForegroundService(intent);
                return;
            } catch (IllegalStateException e) {
                if (!Api31.instanceOfForegroundServiceStartNotAllowedException(e)) {
                    throw e;
                }
                Log.e("MediaButtonReceiver", "caught exception when trying to start a foreground service from the background: " + Api31.castToForegroundServiceStartNotAllowedException(e).getMessage());
                return;
            }
        }
        ComponentName serviceComponentByAction2 = getServiceComponentByAction(context, "android.media.browse.MediaBrowserService");
        if (serviceComponentByAction2 == null) {
            throw new IllegalStateException("Could not find any Service that handles android.intent.action.MEDIA_BUTTON or implements a media browser service.");
        }
        BroadcastReceiver.PendingResult goAsync = goAsync();
        Context applicationContext = context.getApplicationContext();
        MediaButtonConnectionCallback mediaButtonConnectionCallback = new MediaButtonConnectionCallback(applicationContext, intent, goAsync);
        MediaBrowserCompat mediaBrowserCompat = new MediaBrowserCompat(applicationContext, serviceComponentByAction2, mediaButtonConnectionCallback, null);
        mediaButtonConnectionCallback.mMediaBrowser = mediaBrowserCompat;
        Log.d("MediaBrowserCompat", "Connecting to a MediaBrowserService.");
        mediaBrowserCompat.mImpl.mBrowserFwk.connect();
    }
}
