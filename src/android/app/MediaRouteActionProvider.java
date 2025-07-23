package android.app;

import android.content.Context;
import android.media.MediaRouter;
import android.util.Log;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class MediaRouteActionProvider extends ActionProvider {
    private static final String TAG = "MediaRouteActionProvider";
    private MediaRouteButton mButton;
    private final MediaRouterCallback mCallback;
    private final Context mContext;
    private View.OnClickListener mExtendedSettingsListener;
    private int mRouteTypes;
    private final MediaRouter mRouter;

    @Override // android.view.ActionProvider
    public boolean overridesItemVisibility() {
        return true;
    }

    public MediaRouteActionProvider(Context context) {
        super(context);
        this.mContext = context;
        this.mRouter = (MediaRouter) context.getSystemService(Context.MEDIA_ROUTER_SERVICE);
        this.mCallback = new MediaRouterCallback(this);
        setRouteTypes(1);
    }

    public void setRouteTypes(int i) {
        int i2 = this.mRouteTypes;
        if (i2 != i) {
            if (i2 != 0) {
                this.mRouter.removeCallback(this.mCallback);
            }
            this.mRouteTypes = i;
            if (i != 0) {
                this.mRouter.addCallback(i, this.mCallback, 8);
            }
            refreshRoute();
            MediaRouteButton mediaRouteButton = this.mButton;
            if (mediaRouteButton != null) {
                mediaRouteButton.setRouteTypes(this.mRouteTypes);
            }
        }
    }

    public void setExtendedSettingsClickListener(View.OnClickListener onClickListener) {
        this.mExtendedSettingsListener = onClickListener;
        MediaRouteButton mediaRouteButton = this.mButton;
        if (mediaRouteButton != null) {
            mediaRouteButton.setExtendedSettingsClickListener(onClickListener);
        }
    }

    @Override // android.view.ActionProvider
    public View onCreateActionView() {
        throw new UnsupportedOperationException("Use onCreateActionView(MenuItem) instead.");
    }

    @Override // android.view.ActionProvider
    public View onCreateActionView(MenuItem menuItem) {
        if (this.mButton != null) {
            Log.e(TAG, "onCreateActionView: this ActionProvider is already associated with a menu item. Don't reuse MediaRouteActionProvider instances! Abandoning the old one...");
        }
        MediaRouteButton mediaRouteButton = new MediaRouteButton(this.mContext);
        this.mButton = mediaRouteButton;
        mediaRouteButton.setRouteTypes(this.mRouteTypes);
        this.mButton.setExtendedSettingsClickListener(this.mExtendedSettingsListener);
        this.mButton.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
        return this.mButton;
    }

    @Override // android.view.ActionProvider
    public boolean onPerformDefaultAction() {
        MediaRouteButton mediaRouteButton = this.mButton;
        if (mediaRouteButton != null) {
            return mediaRouteButton.showDialogInternal();
        }
        return false;
    }

    @Override // android.view.ActionProvider
    public boolean isVisible() {
        return this.mRouter.isRouteAvailable(this.mRouteTypes, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshRoute() {
        refreshVisibility();
    }

    private static class MediaRouterCallback extends MediaRouter.SimpleCallback {
        private final WeakReference<MediaRouteActionProvider> mProviderWeak;

        public MediaRouterCallback(MediaRouteActionProvider mediaRouteActionProvider) {
            this.mProviderWeak = new WeakReference<>(mediaRouteActionProvider);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteAdded(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            refreshRoute(mediaRouter);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            refreshRoute(mediaRouter);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            refreshRoute(mediaRouter);
        }

        private void refreshRoute(MediaRouter mediaRouter) {
            MediaRouteActionProvider mediaRouteActionProvider = this.mProviderWeak.get();
            if (mediaRouteActionProvider != null) {
                mediaRouteActionProvider.refreshRoute();
            } else {
                mediaRouter.removeCallback(this);
            }
        }
    }
}
