package com.android.internal.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaRouter;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class MediaRouteControllerContentManager {
    private static final int VOLUME_UPDATE_DELAY_MILLIS = 250;
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback;
    private final Context mContext;
    private Drawable mCurrentIconDrawable;
    private final Delegate mDelegate;
    private Drawable mMediaRouteButtonDrawable;
    private final int[] mMediaRouteConnectingState = {16842912, 16842910};
    private final int[] mMediaRouteOnState = {16843518, 16842910};
    private final MediaRouter.RouteInfo mRoute;
    private final MediaRouter mRouter;
    private LinearLayout mVolumeLayout;
    private SeekBar mVolumeSlider;
    private boolean mVolumeSliderTouched;

    public interface Delegate {
        void dismissView();

        void setMediaRouteDeviceIcon(Drawable drawable);

        void setMediaRouteDeviceTitle(CharSequence charSequence);
    }

    public MediaRouteControllerContentManager(Context context, Delegate delegate) {
        this.mContext = context;
        this.mDelegate = delegate;
        MediaRouter mediaRouter = (MediaRouter) context.getSystemService(MediaRouter.class);
        this.mRouter = mediaRouter;
        this.mCallback = new MediaRouterCallback();
        this.mRoute = mediaRouter.getSelectedRoute();
    }

    public void bindViews(View view) {
        this.mDelegate.setMediaRouteDeviceTitle(this.mRoute.getName());
        this.mVolumeLayout = (LinearLayout) view.findViewById(R.id.media_route_volume_layout);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.media_route_volume_slider);
        this.mVolumeSlider = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.android.internal.app.MediaRouteControllerContentManager.1
            private final Runnable mStopTrackingTouch = new Runnable() { // from class: com.android.internal.app.MediaRouteControllerContentManager.1.1
                @Override // java.lang.Runnable
                public void run() {
                    if (MediaRouteControllerContentManager.this.mVolumeSliderTouched) {
                        MediaRouteControllerContentManager.this.mVolumeSliderTouched = false;
                        MediaRouteControllerContentManager.this.updateVolume();
                    }
                }
            };

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
                if (MediaRouteControllerContentManager.this.mVolumeSliderTouched) {
                    MediaRouteControllerContentManager.this.mVolumeSlider.removeCallbacks(this.mStopTrackingTouch);
                } else {
                    MediaRouteControllerContentManager.this.mVolumeSliderTouched = true;
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                MediaRouteControllerContentManager.this.mVolumeSlider.postDelayed(this.mStopTrackingTouch, 250L);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                if (z) {
                    MediaRouteControllerContentManager.this.mRoute.requestSetVolume(i);
                }
            }
        });
        this.mMediaRouteButtonDrawable = obtainMediaRouteButtonDrawable();
    }

    public void onAttachedToWindow() {
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(0, this.mCallback, 2);
        update();
    }

    public void onDetachedFromWindow() {
        this.mRouter.removeCallback(this.mCallback);
        this.mAttachedToWindow = false;
    }

    public void update() {
        if (!this.mRoute.isSelected() || this.mRoute.isDefault()) {
            this.mDelegate.dismissView();
        }
        this.mDelegate.setMediaRouteDeviceTitle(this.mRoute.getName());
        updateVolume();
        Drawable iconDrawable = getIconDrawable();
        if (iconDrawable != this.mCurrentIconDrawable) {
            this.mCurrentIconDrawable = iconDrawable;
            if (iconDrawable instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) iconDrawable;
                if (!this.mAttachedToWindow && !this.mRoute.isConnecting()) {
                    if (animationDrawable.isRunning()) {
                        animationDrawable.stop();
                    }
                    iconDrawable = animationDrawable.getFrame(animationDrawable.getNumberOfFrames() - 1);
                } else if (!animationDrawable.isRunning()) {
                    animationDrawable.start();
                }
            }
            this.mDelegate.setMediaRouteDeviceIcon(iconDrawable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVolume() {
        if (this.mVolumeSliderTouched) {
            return;
        }
        if (isVolumeControlAvailable()) {
            this.mVolumeLayout.setVisibility(0);
            this.mVolumeSlider.setMax(this.mRoute.getVolumeMax());
            this.mVolumeSlider.setProgress(this.mRoute.getVolume());
            return;
        }
        this.mVolumeLayout.setVisibility(8);
    }

    public void onDisconnectButtonClick() {
        if (this.mRoute.isSelected()) {
            if (this.mRoute.isBluetooth()) {
                this.mRouter.getDefaultRoute().select();
            } else {
                this.mRouter.getFallbackRoute().select();
            }
        }
        this.mDelegate.dismissView();
    }

    public void requestUpdateRouteVolume(int i) {
        this.mRoute.requestUpdateVolume(i);
    }

    private boolean isVolumeControlAvailable() {
        return this.mRoute.getVolumeHandling() == 1;
    }

    private Drawable obtainMediaRouteButtonDrawable() {
        TypedValue typedValue = new TypedValue();
        if (!this.mContext.getTheme().resolveAttribute(16843693, typedValue, true)) {
            return null;
        }
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(typedValue.data, new int[]{R.attr.externalRouteEnabledDrawable});
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        return drawable;
    }

    private Drawable getIconDrawable() {
        Drawable drawable = this.mMediaRouteButtonDrawable;
        if (!(drawable instanceof StateListDrawable)) {
            return drawable;
        }
        if (this.mRoute.isConnecting()) {
            StateListDrawable stateListDrawable = (StateListDrawable) this.mMediaRouteButtonDrawable;
            stateListDrawable.setState(this.mMediaRouteConnectingState);
            return stateListDrawable.getCurrent();
        }
        StateListDrawable stateListDrawable2 = (StateListDrawable) this.mMediaRouteButtonDrawable;
        stateListDrawable2.setState(this.mMediaRouteOnState);
        return stateListDrawable2.getCurrent();
    }

    private final class MediaRouterCallback extends MediaRouter.SimpleCallback {
        private MediaRouterCallback() {
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteUnselected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            MediaRouteControllerContentManager.this.update();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteControllerContentManager.this.update();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteVolumeChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            if (routeInfo == MediaRouteControllerContentManager.this.mRoute) {
                MediaRouteControllerContentManager.this.updateVolume();
            }
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteGrouped(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup, int i) {
            MediaRouteControllerContentManager.this.update();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteUngrouped(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup) {
            MediaRouteControllerContentManager.this.update();
        }
    }
}
