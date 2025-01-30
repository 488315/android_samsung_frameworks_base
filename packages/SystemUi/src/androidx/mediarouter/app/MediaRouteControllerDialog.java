package androidx.mediarouter.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.app.AbstractC0147x487e7be7;
import androidx.core.graphics.ColorUtils;
import androidx.mediarouter.app.OverlayListView;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.palette.graphics.Palette;
import com.android.systemui.R;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRouteControllerDialog extends AlertDialog {
    public final AccessibilityManager mAccessibilityManager;
    public int mArtIconBackgroundColor;
    public Bitmap mArtIconBitmap;
    public boolean mArtIconIsLoaded;
    public Bitmap mArtIconLoadedBitmap;
    public Uri mArtIconUri;
    public ImageView mArtView;
    public boolean mAttachedToWindow;
    public final MediaRouterCallback mCallback;
    public final Context mContext;
    public final MediaControllerCallback mControllerCallback;
    public boolean mCreated;
    public FrameLayout mCustomControlLayout;
    public FrameLayout mDefaultControlLayout;
    public MediaDescriptionCompat mDescription;
    public LinearLayout mDialogAreaLayout;
    public int mDialogContentWidth;
    public Button mDisconnectButton;
    public View mDividerView;
    public final boolean mEnableGroupVolumeUX;
    public FrameLayout mExpandableAreaLayout;
    public final Interpolator mFastOutSlowInInterpolator;
    public FetchArtTask mFetchArtTask;
    public MediaRouteExpandCollapseButton mGroupExpandCollapseButton;
    public int mGroupListAnimationDurationMs;
    public final RunnableC03121 mGroupListFadeInAnimation;
    public int mGroupListFadeInDurationMs;
    public int mGroupListFadeOutDurationMs;
    public List mGroupMemberRoutes;
    public Set mGroupMemberRoutesAdded;
    public Set mGroupMemberRoutesAnimatingWithBitmap;
    public Set mGroupMemberRoutesRemoved;
    public boolean mHasPendingUpdate;
    public Interpolator mInterpolator;
    public boolean mIsGroupExpanded;
    public boolean mIsGroupListAnimating;
    public boolean mIsGroupListAnimationPending;
    public final Interpolator mLinearOutSlowInInterpolator;
    public MediaControllerCompat mMediaController;
    public LinearLayout mMediaMainControlLayout;
    public boolean mPendingUpdateAnimationNeeded;
    public ImageButton mPlaybackControlButton;
    public RelativeLayout mPlaybackControlLayout;
    public final MediaRouter.RouteInfo mRoute;
    public MediaRouter.RouteInfo mRouteInVolumeSliderTouched;
    public TextView mRouteNameTextView;
    public final MediaRouter mRouter;
    public PlaybackStateCompat mState;
    public Button mStopCastingButton;
    public TextView mSubtitleView;
    public TextView mTitleView;
    public VolumeChangeListener mVolumeChangeListener;
    public final boolean mVolumeControlEnabled;
    public LinearLayout mVolumeControlLayout;
    public VolumeGroupAdapter mVolumeGroupAdapter;
    public OverlayListView mVolumeGroupList;
    public int mVolumeGroupListItemHeight;
    public int mVolumeGroupListItemIconSize;
    public int mVolumeGroupListMaxHeight;
    public final int mVolumeGroupListPaddingTop;
    public SeekBar mVolumeSlider;
    public Map mVolumeSliderMap;
    public static final boolean DEBUG = Log.isLoggable("MediaRouteCtrlDialog", 3);
    public static final int CONNECTION_TIMEOUT_MILLIS = (int) TimeUnit.SECONDS.toMillis(30);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.mediarouter.app.MediaRouteControllerDialog$10 */
    public final class C031310 {
        public final /* synthetic */ MediaRouter.RouteInfo val$route;

        public C031310(MediaRouter.RouteInfo routeInfo) {
            this.val$route = routeInfo;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ClickListener implements View.OnClickListener {
        public ClickListener() {
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0086  */
        /* JADX WARN: Removed duplicated region for block: B:31:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
        @Override // android.view.View.OnClickListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onClick(View view) {
            PlaybackStateCompat playbackStateCompat;
            AccessibilityManager accessibilityManager;
            int id = view.getId();
            if (id == 16908313 || id == 16908314) {
                if (MediaRouteControllerDialog.this.mRoute.isSelected()) {
                    MediaRouter mediaRouter = MediaRouteControllerDialog.this.mRouter;
                    int i = id == 16908313 ? 2 : 1;
                    mediaRouter.getClass();
                    MediaRouter.unselect(i);
                }
                MediaRouteControllerDialog.this.dismiss();
                return;
            }
            if (id != R.id.mr_control_playback_ctrl) {
                if (id == R.id.mr_close) {
                    MediaRouteControllerDialog.this.dismiss();
                    return;
                }
                return;
            }
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            MediaControllerCompat mediaControllerCompat = mediaRouteControllerDialog.mMediaController;
            if (mediaControllerCompat == null || (playbackStateCompat = mediaRouteControllerDialog.mState) == null) {
                return;
            }
            int i2 = 0;
            boolean z = playbackStateCompat.mState == 3;
            if (z) {
                if ((playbackStateCompat.mActions & 514) != 0) {
                    mediaControllerCompat.mImpl.getTransportControls().pause();
                    i2 = R.string.mr_controller_pause;
                    accessibilityManager = MediaRouteControllerDialog.this.mAccessibilityManager;
                    if (accessibilityManager == null || !accessibilityManager.isEnabled() || i2 == 0) {
                        return;
                    }
                    AccessibilityEvent obtain = AccessibilityEvent.obtain(16384);
                    obtain.setPackageName(MediaRouteControllerDialog.this.mContext.getPackageName());
                    obtain.setClassName(ClickListener.class.getName());
                    obtain.getText().add(MediaRouteControllerDialog.this.mContext.getString(i2));
                    MediaRouteControllerDialog.this.mAccessibilityManager.sendAccessibilityEvent(obtain);
                    return;
                }
            }
            if (z) {
                if ((playbackStateCompat.mActions & 1) != 0) {
                    mediaControllerCompat.mImpl.getTransportControls().stop();
                    i2 = R.string.mr_controller_stop;
                    accessibilityManager = MediaRouteControllerDialog.this.mAccessibilityManager;
                    if (accessibilityManager == null) {
                        return;
                    } else {
                        return;
                    }
                }
            }
            if (!z) {
                if (((playbackStateCompat.mActions & 516) == 0 ? 0 : 1) != 0) {
                    mediaControllerCompat.mImpl.getTransportControls().play();
                    i2 = R.string.mr_controller_play;
                }
            }
            accessibilityManager = MediaRouteControllerDialog.this.mAccessibilityManager;
            if (accessibilityManager == null) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FetchArtTask extends AsyncTask {
        public int mBackgroundColor;
        public final Bitmap mIconBitmap;
        public final Uri mIconUri;
        public long mStartTimeMillis;

        public FetchArtTask() {
            MediaDescriptionCompat mediaDescriptionCompat = MediaRouteControllerDialog.this.mDescription;
            Bitmap bitmap = mediaDescriptionCompat == null ? null : mediaDescriptionCompat.mIcon;
            if (bitmap != null && bitmap.isRecycled()) {
                Log.w("MediaRouteCtrlDialog", "Can't fetch the given art bitmap because it's already recycled.");
                bitmap = null;
            }
            this.mIconBitmap = bitmap;
            MediaDescriptionCompat mediaDescriptionCompat2 = MediaRouteControllerDialog.this.mDescription;
            this.mIconUri = mediaDescriptionCompat2 != null ? mediaDescriptionCompat2.mIconUri : null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:74:0x00d8  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x00ea  */
        /* JADX WARN: Type inference failed for: r4v0 */
        /* JADX WARN: Type inference failed for: r4v2 */
        /* JADX WARN: Type inference failed for: r4v3, types: [java.io.InputStream] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object doInBackground(Object[] objArr) {
            InputStream inputStream;
            float f;
            float f2;
            Bitmap bitmap = this.mIconBitmap;
            ?? r4 = 0;
            if (bitmap == null) {
                Uri uri = this.mIconUri;
                try {
                    if (uri != null) {
                        try {
                            inputStream = openInputStreamByScheme(uri);
                            try {
                                if (inputStream == null) {
                                    Log.w("MediaRouteCtrlDialog", "Unable to open: " + this.mIconUri);
                                    if (inputStream == null) {
                                        return null;
                                    }
                                } else {
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inJustDecodeBounds = true;
                                    BitmapFactory.decodeStream(inputStream, null, options);
                                    if (options.outWidth != 0 && options.outHeight != 0) {
                                        try {
                                            inputStream.reset();
                                        } catch (IOException unused) {
                                            inputStream.close();
                                            inputStream = openInputStreamByScheme(this.mIconUri);
                                            if (inputStream == null) {
                                                Log.w("MediaRouteCtrlDialog", "Unable to open: " + this.mIconUri);
                                                if (inputStream == null) {
                                                    return null;
                                                }
                                            }
                                        }
                                        options.inJustDecodeBounds = false;
                                        MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                                        int i = options.outWidth;
                                        int i2 = options.outHeight;
                                        if (i >= i2) {
                                            f = mediaRouteControllerDialog.mDialogContentWidth * i2;
                                            f2 = i;
                                        } else {
                                            f = mediaRouteControllerDialog.mDialogContentWidth * 9.0f;
                                            f2 = 16.0f;
                                        }
                                        options.inSampleSize = Math.max(1, Integer.highestOneBit(i2 / ((int) ((f / f2) + 0.5f))));
                                        if (!isCancelled()) {
                                            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
                                            try {
                                                inputStream.close();
                                            } catch (IOException unused2) {
                                            }
                                            bitmap = decodeStream;
                                        }
                                    }
                                }
                                try {
                                    inputStream.close();
                                    return null;
                                } catch (IOException unused3) {
                                    return null;
                                }
                            } catch (IOException e) {
                                e = e;
                                Log.w("MediaRouteCtrlDialog", "Unable to open: " + this.mIconUri, e);
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException unused4) {
                                    }
                                }
                                bitmap = null;
                                boolean z = MediaRouteControllerDialog.DEBUG;
                                if (!(bitmap == null && bitmap.isRecycled())) {
                                }
                            }
                        } catch (IOException e2) {
                            e = e2;
                            inputStream = null;
                        } catch (Throwable th) {
                            th = th;
                            if (r4 != 0) {
                                try {
                                    r4.close();
                                } catch (IOException unused5) {
                                }
                            }
                            throw th;
                        }
                    }
                    bitmap = null;
                } catch (Throwable th2) {
                    th = th2;
                    r4 = uri;
                }
            }
            boolean z2 = MediaRouteControllerDialog.DEBUG;
            if (!(bitmap == null && bitmap.isRecycled())) {
                Log.w("MediaRouteCtrlDialog", "Can't use recycled bitmap: " + bitmap);
                return null;
            }
            if (bitmap != null && bitmap.getWidth() < bitmap.getHeight()) {
                Palette.Builder builder = new Palette.Builder(bitmap);
                builder.mMaxColors = 1;
                List list = builder.generate().mSwatches;
                this.mBackgroundColor = Collections.unmodifiableList(list).isEmpty() ? 0 : ((Palette.Swatch) Collections.unmodifiableList(list).get(0)).mRgb;
            }
            return bitmap;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            mediaRouteControllerDialog.mFetchArtTask = null;
            if (Objects.equals(mediaRouteControllerDialog.mArtIconBitmap, this.mIconBitmap) && Objects.equals(MediaRouteControllerDialog.this.mArtIconUri, this.mIconUri)) {
                return;
            }
            MediaRouteControllerDialog mediaRouteControllerDialog2 = MediaRouteControllerDialog.this;
            mediaRouteControllerDialog2.mArtIconBitmap = this.mIconBitmap;
            mediaRouteControllerDialog2.mArtIconLoadedBitmap = bitmap;
            mediaRouteControllerDialog2.mArtIconUri = this.mIconUri;
            mediaRouteControllerDialog2.mArtIconBackgroundColor = this.mBackgroundColor;
            mediaRouteControllerDialog2.mArtIconIsLoaded = true;
            MediaRouteControllerDialog.this.update(SystemClock.uptimeMillis() - this.mStartTimeMillis > 120);
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            this.mStartTimeMillis = SystemClock.uptimeMillis();
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            mediaRouteControllerDialog.mArtIconIsLoaded = false;
            mediaRouteControllerDialog.mArtIconLoadedBitmap = null;
            mediaRouteControllerDialog.mArtIconBackgroundColor = 0;
        }

        public final InputStream openInputStreamByScheme(Uri uri) {
            InputStream openInputStream;
            String lowerCase = uri.getScheme().toLowerCase();
            if ("android.resource".equals(lowerCase) || "content".equals(lowerCase) || "file".equals(lowerCase)) {
                openInputStream = MediaRouteControllerDialog.this.mContext.getContentResolver().openInputStream(uri);
            } else {
                URLConnection openConnection = new URL(uri.toString()).openConnection();
                int i = MediaRouteControllerDialog.CONNECTION_TIMEOUT_MILLIS;
                openConnection.setConnectTimeout(i);
                openConnection.setReadTimeout(i);
                openInputStream = openConnection.getInputStream();
            }
            if (openInputStream == null) {
                return null;
            }
            return new BufferedInputStream(openInputStream);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaControllerCallback extends MediaControllerCompat.Callback {
        public MediaControllerCallback() {
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public final void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
            MediaRouteControllerDialog.this.mDescription = mediaMetadataCompat == null ? null : mediaMetadataCompat.getDescription();
            MediaRouteControllerDialog.this.updateArtIconIfNeeded();
            MediaRouteControllerDialog.this.update(false);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public final void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            mediaRouteControllerDialog.mState = playbackStateCompat;
            mediaRouteControllerDialog.update(false);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public final void onSessionDestroyed() {
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            MediaControllerCompat mediaControllerCompat = mediaRouteControllerDialog.mMediaController;
            if (mediaControllerCompat != null) {
                mediaControllerCompat.unregisterCallback(mediaRouteControllerDialog.mControllerCallback);
                MediaRouteControllerDialog.this.mMediaController = null;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaRouterCallback extends MediaRouter.Callback {
        public MediaRouterCallback() {
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteControllerDialog.this.update(true);
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteUnselected() {
            MediaRouteControllerDialog.this.update(false);
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteVolumeChanged(MediaRouter.RouteInfo routeInfo) {
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            SeekBar seekBar = (SeekBar) ((HashMap) mediaRouteControllerDialog.mVolumeSliderMap).get(routeInfo);
            int i = routeInfo.mVolume;
            if (MediaRouteControllerDialog.DEBUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onRouteVolumeChanged(), route.getVolume:", i, "MediaRouteCtrlDialog");
            }
            if (seekBar == null || mediaRouteControllerDialog.mRouteInVolumeSliderTouched == routeInfo) {
                return;
            }
            seekBar.setProgress(i);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VolumeChangeListener implements SeekBar.OnSeekBarChangeListener {
        public final RunnableC03241 mStopTrackingTouch = new Runnable() { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.VolumeChangeListener.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                if (mediaRouteControllerDialog.mRouteInVolumeSliderTouched != null) {
                    mediaRouteControllerDialog.mRouteInVolumeSliderTouched = null;
                    if (mediaRouteControllerDialog.mHasPendingUpdate) {
                        mediaRouteControllerDialog.update(mediaRouteControllerDialog.mPendingUpdateAnimationNeeded);
                    }
                }
            }
        };

        /* JADX WARN: Type inference failed for: r1v1, types: [androidx.mediarouter.app.MediaRouteControllerDialog$VolumeChangeListener$1] */
        public VolumeChangeListener() {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) seekBar.getTag();
                if (MediaRouteControllerDialog.DEBUG) {
                    AbstractC0147x487e7be7.m26m("onProgressChanged(): calling MediaRouter.RouteInfo.requestSetVolume(", i, ")", "MediaRouteCtrlDialog");
                }
                routeInfo.requestSetVolume(i);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            if (mediaRouteControllerDialog.mRouteInVolumeSliderTouched != null) {
                mediaRouteControllerDialog.mVolumeSlider.removeCallbacks(this.mStopTrackingTouch);
            }
            MediaRouteControllerDialog.this.mRouteInVolumeSliderTouched = (MediaRouter.RouteInfo) seekBar.getTag();
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            MediaRouteControllerDialog.this.mVolumeSlider.postDelayed(this.mStopTrackingTouch, 500L);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VolumeGroupAdapter extends ArrayAdapter {
        public final float mDisabledAlpha;

        public VolumeGroupAdapter(Context context, List<MediaRouter.RouteInfo> list) {
            super(context, 0, list);
            this.mDisabledAlpha = MediaRouterThemeHelper.getDisabledAlpha(context);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mr_controller_volume_item, viewGroup, false);
            } else {
                MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                mediaRouteControllerDialog.getClass();
                MediaRouteControllerDialog.setLayoutHeight((LinearLayout) view.findViewById(R.id.volume_item_container), mediaRouteControllerDialog.mVolumeGroupListItemHeight);
                View findViewById = view.findViewById(R.id.mr_volume_item_icon);
                ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
                int i2 = mediaRouteControllerDialog.mVolumeGroupListItemIconSize;
                layoutParams.width = i2;
                layoutParams.height = i2;
                findViewById.setLayoutParams(layoutParams);
            }
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) getItem(i);
            if (routeInfo != null) {
                boolean z = routeInfo.mEnabled;
                TextView textView = (TextView) view.findViewById(R.id.mr_name);
                textView.setEnabled(z);
                textView.setText(routeInfo.mName);
                MediaRouteVolumeSlider mediaRouteVolumeSlider = (MediaRouteVolumeSlider) view.findViewById(R.id.mr_volume_slider);
                MediaRouterThemeHelper.setVolumeSliderColor(viewGroup.getContext(), mediaRouteVolumeSlider, MediaRouteControllerDialog.this.mVolumeGroupList);
                mediaRouteVolumeSlider.setTag(routeInfo);
                ((HashMap) MediaRouteControllerDialog.this.mVolumeSliderMap).put(routeInfo, mediaRouteVolumeSlider);
                mediaRouteVolumeSlider.setHideThumb(!z);
                mediaRouteVolumeSlider.setEnabled(z);
                if (z) {
                    if (MediaRouteControllerDialog.this.mVolumeControlEnabled && routeInfo.getVolumeHandling() == 1) {
                        mediaRouteVolumeSlider.setMax(routeInfo.mVolumeMax);
                        mediaRouteVolumeSlider.setProgress(routeInfo.mVolume);
                        mediaRouteVolumeSlider.setOnSeekBarChangeListener(MediaRouteControllerDialog.this.mVolumeChangeListener);
                    } else {
                        mediaRouteVolumeSlider.setMax(100);
                        mediaRouteVolumeSlider.setProgress(100);
                        mediaRouteVolumeSlider.setEnabled(false);
                    }
                }
                ((ImageView) view.findViewById(R.id.mr_volume_item_icon)).setAlpha(z ? 255 : (int) (this.mDisabledAlpha * 255.0f));
                ((LinearLayout) view.findViewById(R.id.volume_item_container)).setVisibility(((HashSet) MediaRouteControllerDialog.this.mGroupMemberRoutesAnimatingWithBitmap).contains(routeInfo) ? 4 : 0);
                Set set = MediaRouteControllerDialog.this.mGroupMemberRoutesAdded;
                if (set != null && ((HashSet) set).contains(routeInfo)) {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
                    alphaAnimation.setDuration(0L);
                    alphaAnimation.setFillEnabled(true);
                    alphaAnimation.setFillAfter(true);
                    view.clearAnimation();
                    view.startAnimation(alphaAnimation);
                }
            }
            return view;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean isEnabled(int i) {
            return false;
        }
    }

    public MediaRouteControllerDialog(Context context) {
        this(context, 0);
    }

    public static void setLayoutHeight(View view, int i) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = i;
        view.setLayoutParams(layoutParams);
    }

    public final void animateLayoutHeight(final View view, final int i) {
        final int i2 = view.getLayoutParams().height;
        Animation animation = new Animation(this) { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.7
            @Override // android.view.animation.Animation
            public final void applyTransformation(float f, Transformation transformation) {
                MediaRouteControllerDialog.setLayoutHeight(view, i2 - ((int) ((r3 - i) * f)));
            }
        };
        animation.setDuration(this.mGroupListAnimationDurationMs);
        animation.setInterpolator(this.mInterpolator);
        view.startAnimation(animation);
    }

    public final boolean canShowPlaybackControlLayout() {
        return (this.mDescription == null && this.mState == null) ? false : true;
    }

    public final void clearGroupListAnimation(boolean z) {
        Set set;
        int firstVisiblePosition = this.mVolumeGroupList.getFirstVisiblePosition();
        for (int i = 0; i < this.mVolumeGroupList.getChildCount(); i++) {
            View childAt = this.mVolumeGroupList.getChildAt(i);
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) this.mVolumeGroupAdapter.getItem(firstVisiblePosition + i);
            if (!z || (set = this.mGroupMemberRoutesAdded) == null || !((HashSet) set).contains(routeInfo)) {
                ((LinearLayout) childAt.findViewById(R.id.volume_item_container)).setVisibility(0);
                AnimationSet animationSet = new AnimationSet(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
                alphaAnimation.setDuration(0L);
                animationSet.addAnimation(alphaAnimation);
                new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f).setDuration(0L);
                animationSet.setFillAfter(true);
                animationSet.setFillEnabled(true);
                childAt.clearAnimation();
                childAt.startAnimation(animationSet);
            }
        }
        Iterator it = ((ArrayList) this.mVolumeGroupList.mOverlayObjects).iterator();
        while (it.hasNext()) {
            OverlayListView.OverlayObject overlayObject = (OverlayListView.OverlayObject) it.next();
            overlayObject.mIsAnimationStarted = true;
            overlayObject.mIsAnimationEnded = true;
            C031310 c031310 = overlayObject.mListener;
            if (c031310 != null) {
                MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                ((HashSet) mediaRouteControllerDialog.mGroupMemberRoutesAnimatingWithBitmap).remove(c031310.val$route);
                mediaRouteControllerDialog.mVolumeGroupAdapter.notifyDataSetChanged();
            }
        }
        if (z) {
            return;
        }
        finishAnimation(false);
    }

    public final void finishAnimation(boolean z) {
        this.mGroupMemberRoutesAdded = null;
        this.mGroupMemberRoutesRemoved = null;
        this.mIsGroupListAnimating = false;
        if (this.mIsGroupListAnimationPending) {
            this.mIsGroupListAnimationPending = false;
            updateLayoutHeight(z);
        }
        this.mVolumeGroupList.setEnabled(true);
    }

    public final int getMainControllerHeight(boolean z) {
        if (!z && this.mVolumeControlLayout.getVisibility() != 0) {
            return 0;
        }
        int paddingBottom = this.mMediaMainControlLayout.getPaddingBottom() + this.mMediaMainControlLayout.getPaddingTop() + 0;
        if (z) {
            paddingBottom += this.mPlaybackControlLayout.getMeasuredHeight();
        }
        int measuredHeight = this.mVolumeControlLayout.getVisibility() == 0 ? this.mVolumeControlLayout.getMeasuredHeight() + paddingBottom : paddingBottom;
        return (z && this.mVolumeControlLayout.getVisibility() == 0) ? measuredHeight + this.mDividerView.getMeasuredHeight() : measuredHeight;
    }

    public final boolean isGroup() {
        return this.mRoute.isGroup() && this.mRoute.getMemberRoutes().size() > 1;
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(MediaRouteSelector.EMPTY, this.mCallback, 2);
        this.mRouter.getClass();
        boolean z = MediaRouter.DEBUG;
        setMediaSession();
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.mr_controller_material_dialog_b);
        findViewById(android.R.id.button3).setVisibility(8);
        ClickListener clickListener = new ClickListener();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.mr_expandable_area);
        this.mExpandableAreaLayout = frameLayout;
        frameLayout.setOnClickListener(new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaRouteControllerDialog.this.dismiss();
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mr_dialog_area);
        this.mDialogAreaLayout = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener(this) { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
            }
        });
        Context context = this.mContext;
        int themeColor = MediaRouterThemeHelper.getThemeColor(0, context, R.attr.colorPrimary);
        if (ColorUtils.calculateContrast(themeColor, MediaRouterThemeHelper.getThemeColor(0, context, android.R.attr.colorBackground)) < 3.0d) {
            themeColor = MediaRouterThemeHelper.getThemeColor(0, context, R.attr.colorAccent);
        }
        Button button = (Button) findViewById(android.R.id.button2);
        this.mDisconnectButton = button;
        button.setText(R.string.mr_controller_disconnect);
        this.mDisconnectButton.setTextColor(themeColor);
        this.mDisconnectButton.setOnClickListener(clickListener);
        Button button2 = (Button) findViewById(android.R.id.button1);
        this.mStopCastingButton = button2;
        button2.setText(R.string.mr_controller_stop_casting);
        this.mStopCastingButton.setTextColor(themeColor);
        this.mStopCastingButton.setOnClickListener(clickListener);
        this.mRouteNameTextView = (TextView) findViewById(R.id.mr_name);
        ((ImageButton) findViewById(R.id.mr_close)).setOnClickListener(clickListener);
        this.mCustomControlLayout = (FrameLayout) findViewById(R.id.mr_custom_control);
        this.mDefaultControlLayout = (FrameLayout) findViewById(R.id.mr_default_control);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PendingIntent sessionActivity;
                MediaControllerCompat mediaControllerCompat = MediaRouteControllerDialog.this.mMediaController;
                if (mediaControllerCompat == null || (sessionActivity = mediaControllerCompat.mImpl.mControllerFwk.getSessionActivity()) == null) {
                    return;
                }
                try {
                    sessionActivity.send();
                    MediaRouteControllerDialog.this.dismiss();
                } catch (PendingIntent.CanceledException unused) {
                    Log.e("MediaRouteCtrlDialog", sessionActivity + " was not sent, it had been canceled.");
                }
            }
        };
        ImageView imageView = (ImageView) findViewById(R.id.mr_art);
        this.mArtView = imageView;
        imageView.setOnClickListener(onClickListener);
        findViewById(R.id.mr_control_title_container).setOnClickListener(onClickListener);
        this.mMediaMainControlLayout = (LinearLayout) findViewById(R.id.mr_media_main_control);
        this.mDividerView = findViewById(R.id.mr_control_divider);
        this.mPlaybackControlLayout = (RelativeLayout) findViewById(R.id.mr_playback_control);
        this.mTitleView = (TextView) findViewById(R.id.mr_control_title);
        this.mSubtitleView = (TextView) findViewById(R.id.mr_control_subtitle);
        ImageButton imageButton = (ImageButton) findViewById(R.id.mr_control_playback_ctrl);
        this.mPlaybackControlButton = imageButton;
        imageButton.setOnClickListener(clickListener);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.mr_volume_control);
        this.mVolumeControlLayout = linearLayout2;
        linearLayout2.setVisibility(8);
        SeekBar seekBar = (SeekBar) findViewById(R.id.mr_volume_slider);
        this.mVolumeSlider = seekBar;
        seekBar.setTag(this.mRoute);
        VolumeChangeListener volumeChangeListener = new VolumeChangeListener();
        this.mVolumeChangeListener = volumeChangeListener;
        this.mVolumeSlider.setOnSeekBarChangeListener(volumeChangeListener);
        this.mVolumeGroupList = (OverlayListView) findViewById(R.id.mr_volume_group_list);
        this.mGroupMemberRoutes = new ArrayList();
        VolumeGroupAdapter volumeGroupAdapter = new VolumeGroupAdapter(this.mVolumeGroupList.getContext(), this.mGroupMemberRoutes);
        this.mVolumeGroupAdapter = volumeGroupAdapter;
        this.mVolumeGroupList.setAdapter((ListAdapter) volumeGroupAdapter);
        this.mGroupMemberRoutesAnimatingWithBitmap = new HashSet();
        Context context2 = this.mContext;
        LinearLayout linearLayout3 = this.mMediaMainControlLayout;
        OverlayListView overlayListView = this.mVolumeGroupList;
        boolean isGroup = isGroup();
        int themeColor2 = MediaRouterThemeHelper.getThemeColor(0, context2, R.attr.colorPrimary);
        int themeColor3 = MediaRouterThemeHelper.getThemeColor(0, context2, R.attr.colorPrimaryDark);
        if (isGroup && MediaRouterThemeHelper.getControllerColor(0, context2) == -570425344) {
            themeColor3 = themeColor2;
            themeColor2 = -1;
        }
        linearLayout3.setBackgroundColor(themeColor2);
        overlayListView.setBackgroundColor(themeColor3);
        linearLayout3.setTag(Integer.valueOf(themeColor2));
        overlayListView.setTag(Integer.valueOf(themeColor3));
        MediaRouterThemeHelper.setVolumeSliderColor(this.mContext, (MediaRouteVolumeSlider) this.mVolumeSlider, this.mMediaMainControlLayout);
        HashMap hashMap = new HashMap();
        this.mVolumeSliderMap = hashMap;
        hashMap.put(this.mRoute, this.mVolumeSlider);
        MediaRouteExpandCollapseButton mediaRouteExpandCollapseButton = (MediaRouteExpandCollapseButton) findViewById(R.id.mr_group_expand_collapse);
        this.mGroupExpandCollapseButton = mediaRouteExpandCollapseButton;
        mediaRouteExpandCollapseButton.setOnClickListener(new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                boolean z = !mediaRouteControllerDialog.mIsGroupExpanded;
                mediaRouteControllerDialog.mIsGroupExpanded = z;
                if (z) {
                    mediaRouteControllerDialog.mVolumeGroupList.setVisibility(0);
                }
                MediaRouteControllerDialog mediaRouteControllerDialog2 = MediaRouteControllerDialog.this;
                mediaRouteControllerDialog2.mInterpolator = mediaRouteControllerDialog2.mIsGroupExpanded ? mediaRouteControllerDialog2.mLinearOutSlowInInterpolator : mediaRouteControllerDialog2.mFastOutSlowInInterpolator;
                mediaRouteControllerDialog2.updateLayoutHeight(true);
            }
        });
        this.mInterpolator = this.mIsGroupExpanded ? this.mLinearOutSlowInInterpolator : this.mFastOutSlowInInterpolator;
        this.mGroupListAnimationDurationMs = this.mContext.getResources().getInteger(R.integer.mr_controller_volume_group_list_animation_duration_ms);
        this.mGroupListFadeInDurationMs = this.mContext.getResources().getInteger(R.integer.mr_controller_volume_group_list_fade_in_duration_ms);
        this.mGroupListFadeOutDurationMs = this.mContext.getResources().getInteger(R.integer.mr_controller_volume_group_list_fade_out_duration_ms);
        this.mCreated = true;
        updateLayout();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        this.mRouter.removeCallback(this.mCallback);
        setMediaSession();
        this.mAttachedToWindow = false;
        super.onDetachedFromWindow();
    }

    @Override // androidx.appcompat.app.AlertDialog, android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 25 && i != 24) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.mEnableGroupVolumeUX || !this.mIsGroupExpanded) {
            this.mRoute.requestUpdateVolume(i == 25 ? -1 : 1);
        }
        return true;
    }

    @Override // androidx.appcompat.app.AlertDialog, android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 25 || i == 24) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    public final void setMediaSession() {
        MediaControllerCompat mediaControllerCompat = this.mMediaController;
        if (mediaControllerCompat != null) {
            mediaControllerCompat.unregisterCallback(this.mControllerCallback);
            this.mMediaController = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0159  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(boolean z) {
        boolean z2;
        boolean z3;
        PlaybackStateCompat playbackStateCompat;
        int i;
        int i2;
        boolean z4 = true;
        if (this.mRouteInVolumeSliderTouched != null) {
            this.mHasPendingUpdate = true;
            this.mPendingUpdateAnimationNeeded = z | this.mPendingUpdateAnimationNeeded;
            return;
        }
        this.mHasPendingUpdate = false;
        this.mPendingUpdateAnimationNeeded = false;
        if (!this.mRoute.isSelected() || this.mRoute.isDefaultOrBluetooth()) {
            dismiss();
            return;
        }
        if (this.mCreated) {
            this.mRouteNameTextView.setText(this.mRoute.mName);
            this.mDisconnectButton.setVisibility(this.mRoute.mCanDisconnect ? 0 : 8);
            if (this.mArtIconIsLoaded) {
                Bitmap bitmap = this.mArtIconLoadedBitmap;
                if (bitmap != null && bitmap.isRecycled()) {
                    Log.w("MediaRouteCtrlDialog", "Can't set artwork image with recycled bitmap: " + this.mArtIconLoadedBitmap);
                } else {
                    this.mArtView.setImageBitmap(this.mArtIconLoadedBitmap);
                    this.mArtView.setBackgroundColor(this.mArtIconBackgroundColor);
                }
                this.mArtIconIsLoaded = false;
                this.mArtIconLoadedBitmap = null;
                this.mArtIconBackgroundColor = 0;
            }
            if (this.mEnableGroupVolumeUX || !isGroup()) {
                if (!this.mIsGroupExpanded || this.mEnableGroupVolumeUX) {
                    if (this.mVolumeControlEnabled && this.mRoute.getVolumeHandling() == 1) {
                        if (this.mVolumeControlLayout.getVisibility() == 8) {
                            this.mVolumeControlLayout.setVisibility(0);
                            this.mVolumeSlider.setMax(this.mRoute.mVolumeMax);
                            this.mVolumeSlider.setProgress(this.mRoute.mVolume);
                            this.mGroupExpandCollapseButton.setVisibility(isGroup() ? 0 : 8);
                        }
                    }
                }
                this.mVolumeControlLayout.setVisibility(8);
            } else {
                this.mVolumeControlLayout.setVisibility(8);
                this.mIsGroupExpanded = true;
                this.mVolumeGroupList.setVisibility(0);
                this.mInterpolator = this.mIsGroupExpanded ? this.mLinearOutSlowInInterpolator : this.mFastOutSlowInInterpolator;
                updateLayoutHeight(false);
            }
            if (canShowPlaybackControlLayout()) {
                MediaDescriptionCompat mediaDescriptionCompat = this.mDescription;
                CharSequence charSequence = mediaDescriptionCompat == null ? null : mediaDescriptionCompat.mTitle;
                boolean z5 = !TextUtils.isEmpty(charSequence);
                MediaDescriptionCompat mediaDescriptionCompat2 = this.mDescription;
                CharSequence charSequence2 = mediaDescriptionCompat2 != null ? mediaDescriptionCompat2.mSubtitle : null;
                boolean z6 = !TextUtils.isEmpty(charSequence2);
                if (this.mRoute.mPresentationDisplayId != -1) {
                    this.mTitleView.setText(R.string.mr_controller_casting_screen);
                } else {
                    PlaybackStateCompat playbackStateCompat2 = this.mState;
                    if (playbackStateCompat2 == null || playbackStateCompat2.mState == 0) {
                        this.mTitleView.setText(R.string.mr_controller_no_media_selected);
                    } else if (z5 || z6) {
                        if (z5) {
                            this.mTitleView.setText(charSequence);
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (z6) {
                            this.mSubtitleView.setText(charSequence2);
                            z3 = true;
                            this.mTitleView.setVisibility(z2 ? 0 : 8);
                            this.mSubtitleView.setVisibility(z3 ? 0 : 8);
                            playbackStateCompat = this.mState;
                            if (playbackStateCompat != null) {
                                int i3 = playbackStateCompat.mState;
                                boolean z7 = i3 == 6 || i3 == 3;
                                Context context = this.mPlaybackControlButton.getContext();
                                if (z7) {
                                    if ((this.mState.mActions & 514) != 0) {
                                        i = R.attr.mediaRoutePauseDrawable;
                                        i2 = R.string.mr_controller_pause;
                                        this.mPlaybackControlButton.setVisibility(z4 ? 0 : 8);
                                        if (z4) {
                                            this.mPlaybackControlButton.setImageResource(MediaRouterThemeHelper.getThemeResource(i, context));
                                            this.mPlaybackControlButton.setContentDescription(context.getResources().getText(i2));
                                        }
                                    }
                                }
                                if (z7) {
                                    if ((this.mState.mActions & 1) != 0) {
                                        i = R.attr.mediaRouteStopDrawable;
                                        i2 = R.string.mr_controller_stop;
                                        this.mPlaybackControlButton.setVisibility(z4 ? 0 : 8);
                                        if (z4) {
                                        }
                                    }
                                }
                                if (!z7) {
                                    if ((this.mState.mActions & 516) != 0) {
                                        i = R.attr.mediaRoutePlayDrawable;
                                        i2 = R.string.mr_controller_play;
                                        this.mPlaybackControlButton.setVisibility(z4 ? 0 : 8);
                                        if (z4) {
                                        }
                                    }
                                }
                                z4 = false;
                                i = 0;
                                i2 = 0;
                                this.mPlaybackControlButton.setVisibility(z4 ? 0 : 8);
                                if (z4) {
                                }
                            }
                        }
                        z3 = false;
                        this.mTitleView.setVisibility(z2 ? 0 : 8);
                        this.mSubtitleView.setVisibility(z3 ? 0 : 8);
                        playbackStateCompat = this.mState;
                        if (playbackStateCompat != null) {
                        }
                    } else {
                        this.mTitleView.setText(R.string.mr_controller_no_info_available);
                    }
                }
                z2 = true;
                z3 = false;
                this.mTitleView.setVisibility(z2 ? 0 : 8);
                this.mSubtitleView.setVisibility(z3 ? 0 : 8);
                playbackStateCompat = this.mState;
                if (playbackStateCompat != null) {
                }
            }
            updateLayoutHeight(z);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0035, code lost:
    
        if (((r0 != null && r0.equals(r1)) || (r0 == null && r1 == null)) == false) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateArtIconIfNeeded() {
        boolean z;
        MediaDescriptionCompat mediaDescriptionCompat = this.mDescription;
        Bitmap bitmap = mediaDescriptionCompat == null ? null : mediaDescriptionCompat.mIcon;
        Uri uri = mediaDescriptionCompat != null ? mediaDescriptionCompat.mIconUri : null;
        FetchArtTask fetchArtTask = this.mFetchArtTask;
        Bitmap bitmap2 = fetchArtTask == null ? this.mArtIconBitmap : fetchArtTask.mIconBitmap;
        Uri uri2 = fetchArtTask == null ? this.mArtIconUri : fetchArtTask.mIconUri;
        if (bitmap2 == bitmap) {
            if (bitmap2 == null) {
            }
            z = false;
            if (z) {
                return;
            }
            if (!isGroup() || this.mEnableGroupVolumeUX) {
                FetchArtTask fetchArtTask2 = this.mFetchArtTask;
                if (fetchArtTask2 != null) {
                    fetchArtTask2.cancel(true);
                }
                FetchArtTask fetchArtTask3 = new FetchArtTask();
                this.mFetchArtTask = fetchArtTask3;
                fetchArtTask3.execute(new Void[0]);
                return;
            }
            return;
        }
        z = true;
        if (z) {
        }
    }

    public final void updateLayout() {
        int dialogWidth = MediaRouteDialogHelper.getDialogWidth(this.mContext);
        getWindow().setLayout(dialogWidth, -2);
        View decorView = getWindow().getDecorView();
        this.mDialogContentWidth = (dialogWidth - decorView.getPaddingLeft()) - decorView.getPaddingRight();
        Resources resources = this.mContext.getResources();
        this.mVolumeGroupListItemIconSize = resources.getDimensionPixelSize(R.dimen.mr_controller_volume_group_list_item_icon_size);
        this.mVolumeGroupListItemHeight = resources.getDimensionPixelSize(R.dimen.mr_controller_volume_group_list_item_height);
        this.mVolumeGroupListMaxHeight = resources.getDimensionPixelSize(R.dimen.mr_controller_volume_group_list_max_height);
        this.mArtIconBitmap = null;
        this.mArtIconUri = null;
        updateArtIconIfNeeded();
        update(false);
    }

    public final void updateLayoutHeight(final boolean z) {
        this.mDefaultControlLayout.requestLayout();
        this.mDefaultControlLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.6
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                int i;
                final HashMap hashMap;
                final HashMap hashMap2;
                Bitmap bitmap;
                float f;
                float f2;
                MediaRouteControllerDialog.this.mDefaultControlLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                final MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                if (mediaRouteControllerDialog.mIsGroupListAnimating) {
                    mediaRouteControllerDialog.mIsGroupListAnimationPending = true;
                    return;
                }
                boolean z2 = z;
                int i2 = mediaRouteControllerDialog.mMediaMainControlLayout.getLayoutParams().height;
                MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mMediaMainControlLayout, -1);
                mediaRouteControllerDialog.updateMediaControlVisibility(mediaRouteControllerDialog.canShowPlaybackControlLayout());
                View decorView = mediaRouteControllerDialog.getWindow().getDecorView();
                decorView.measure(View.MeasureSpec.makeMeasureSpec(mediaRouteControllerDialog.getWindow().getAttributes().width, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), 0);
                MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mMediaMainControlLayout, i2);
                if (!(mediaRouteControllerDialog.mArtView.getDrawable() instanceof BitmapDrawable) || (bitmap = ((BitmapDrawable) mediaRouteControllerDialog.mArtView.getDrawable()).getBitmap()) == null) {
                    i = 0;
                } else {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    if (width >= height) {
                        f = mediaRouteControllerDialog.mDialogContentWidth * height;
                        f2 = width;
                    } else {
                        f = mediaRouteControllerDialog.mDialogContentWidth * 9.0f;
                        f2 = 16.0f;
                    }
                    i = (int) ((f / f2) + 0.5f);
                    mediaRouteControllerDialog.mArtView.setScaleType(bitmap.getWidth() >= bitmap.getHeight() ? ImageView.ScaleType.FIT_XY : ImageView.ScaleType.FIT_CENTER);
                }
                int mainControllerHeight = mediaRouteControllerDialog.getMainControllerHeight(mediaRouteControllerDialog.canShowPlaybackControlLayout());
                int size = ((ArrayList) mediaRouteControllerDialog.mGroupMemberRoutes).size();
                int size2 = mediaRouteControllerDialog.isGroup() ? mediaRouteControllerDialog.mRoute.getMemberRoutes().size() * mediaRouteControllerDialog.mVolumeGroupListItemHeight : 0;
                if (size > 0) {
                    size2 += mediaRouteControllerDialog.mVolumeGroupListPaddingTop;
                }
                int min = Math.min(size2, mediaRouteControllerDialog.mVolumeGroupListMaxHeight);
                if (!mediaRouteControllerDialog.mIsGroupExpanded) {
                    min = 0;
                }
                int max = Math.max(i, min) + mainControllerHeight;
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int height2 = rect.height() - (mediaRouteControllerDialog.mDialogAreaLayout.getMeasuredHeight() - mediaRouteControllerDialog.mDefaultControlLayout.getMeasuredHeight());
                if (i <= 0 || max > height2) {
                    if (mediaRouteControllerDialog.mMediaMainControlLayout.getMeasuredHeight() + mediaRouteControllerDialog.mVolumeGroupList.getLayoutParams().height >= mediaRouteControllerDialog.mDefaultControlLayout.getMeasuredHeight()) {
                        mediaRouteControllerDialog.mArtView.setVisibility(8);
                    }
                    max = min + mainControllerHeight;
                    i = 0;
                } else {
                    mediaRouteControllerDialog.mArtView.setVisibility(0);
                    MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mArtView, i);
                }
                if (!mediaRouteControllerDialog.canShowPlaybackControlLayout() || max > height2) {
                    mediaRouteControllerDialog.mPlaybackControlLayout.setVisibility(8);
                } else {
                    mediaRouteControllerDialog.mPlaybackControlLayout.setVisibility(0);
                }
                mediaRouteControllerDialog.updateMediaControlVisibility(mediaRouteControllerDialog.mPlaybackControlLayout.getVisibility() == 0);
                int mainControllerHeight2 = mediaRouteControllerDialog.getMainControllerHeight(mediaRouteControllerDialog.mPlaybackControlLayout.getVisibility() == 0);
                int max2 = Math.max(i, min) + mainControllerHeight2;
                if (max2 > height2) {
                    min -= max2 - height2;
                } else {
                    height2 = max2;
                }
                mediaRouteControllerDialog.mMediaMainControlLayout.clearAnimation();
                mediaRouteControllerDialog.mVolumeGroupList.clearAnimation();
                mediaRouteControllerDialog.mDefaultControlLayout.clearAnimation();
                if (z2) {
                    mediaRouteControllerDialog.animateLayoutHeight(mediaRouteControllerDialog.mMediaMainControlLayout, mainControllerHeight2);
                    mediaRouteControllerDialog.animateLayoutHeight(mediaRouteControllerDialog.mVolumeGroupList, min);
                    mediaRouteControllerDialog.animateLayoutHeight(mediaRouteControllerDialog.mDefaultControlLayout, height2);
                } else {
                    MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mMediaMainControlLayout, mainControllerHeight2);
                    MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mVolumeGroupList, min);
                    MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mDefaultControlLayout, height2);
                }
                MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mExpandableAreaLayout, rect.height());
                List memberRoutes = mediaRouteControllerDialog.mRoute.getMemberRoutes();
                if (memberRoutes.isEmpty()) {
                    ((ArrayList) mediaRouteControllerDialog.mGroupMemberRoutes).clear();
                    mediaRouteControllerDialog.mVolumeGroupAdapter.notifyDataSetChanged();
                    return;
                }
                if (new HashSet(mediaRouteControllerDialog.mGroupMemberRoutes).equals(new HashSet(memberRoutes))) {
                    mediaRouteControllerDialog.mVolumeGroupAdapter.notifyDataSetChanged();
                    return;
                }
                if (z2) {
                    OverlayListView overlayListView = mediaRouteControllerDialog.mVolumeGroupList;
                    VolumeGroupAdapter volumeGroupAdapter = mediaRouteControllerDialog.mVolumeGroupAdapter;
                    hashMap = new HashMap();
                    int firstVisiblePosition = overlayListView.getFirstVisiblePosition();
                    for (int i3 = 0; i3 < overlayListView.getChildCount(); i3++) {
                        Object item = volumeGroupAdapter.getItem(firstVisiblePosition + i3);
                        View childAt = overlayListView.getChildAt(i3);
                        hashMap.put(item, new Rect(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom()));
                    }
                } else {
                    hashMap = null;
                }
                if (z2) {
                    Context context = mediaRouteControllerDialog.mContext;
                    OverlayListView overlayListView2 = mediaRouteControllerDialog.mVolumeGroupList;
                    VolumeGroupAdapter volumeGroupAdapter2 = mediaRouteControllerDialog.mVolumeGroupAdapter;
                    hashMap2 = new HashMap();
                    int firstVisiblePosition2 = overlayListView2.getFirstVisiblePosition();
                    for (int i4 = 0; i4 < overlayListView2.getChildCount(); i4++) {
                        Object item2 = volumeGroupAdapter2.getItem(firstVisiblePosition2 + i4);
                        View childAt2 = overlayListView2.getChildAt(i4);
                        Bitmap createBitmap = Bitmap.createBitmap(childAt2.getWidth(), childAt2.getHeight(), Bitmap.Config.ARGB_8888);
                        childAt2.draw(new Canvas(createBitmap));
                        hashMap2.put(item2, new BitmapDrawable(context.getResources(), createBitmap));
                    }
                } else {
                    hashMap2 = null;
                }
                List list = mediaRouteControllerDialog.mGroupMemberRoutes;
                HashSet hashSet = new HashSet(memberRoutes);
                hashSet.removeAll(list);
                mediaRouteControllerDialog.mGroupMemberRoutesAdded = hashSet;
                HashSet hashSet2 = new HashSet(mediaRouteControllerDialog.mGroupMemberRoutes);
                hashSet2.removeAll(memberRoutes);
                mediaRouteControllerDialog.mGroupMemberRoutesRemoved = hashSet2;
                ((ArrayList) mediaRouteControllerDialog.mGroupMemberRoutes).addAll(0, mediaRouteControllerDialog.mGroupMemberRoutesAdded);
                ((ArrayList) mediaRouteControllerDialog.mGroupMemberRoutes).removeAll(mediaRouteControllerDialog.mGroupMemberRoutesRemoved);
                mediaRouteControllerDialog.mVolumeGroupAdapter.notifyDataSetChanged();
                if (z2 && mediaRouteControllerDialog.mIsGroupExpanded) {
                    if (((HashSet) mediaRouteControllerDialog.mGroupMemberRoutesRemoved).size() + ((HashSet) mediaRouteControllerDialog.mGroupMemberRoutesAdded).size() > 0) {
                        mediaRouteControllerDialog.mVolumeGroupList.setEnabled(false);
                        mediaRouteControllerDialog.mVolumeGroupList.requestLayout();
                        mediaRouteControllerDialog.mIsGroupListAnimating = true;
                        mediaRouteControllerDialog.mVolumeGroupList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.8
                            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                            public final void onGlobalLayout() {
                                OverlayListView.OverlayObject overlayObject;
                                MediaRouteControllerDialog.this.mVolumeGroupList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                final MediaRouteControllerDialog mediaRouteControllerDialog2 = MediaRouteControllerDialog.this;
                                Map map = hashMap;
                                Map map2 = hashMap2;
                                Set set = mediaRouteControllerDialog2.mGroupMemberRoutesAdded;
                                if (set == null || mediaRouteControllerDialog2.mGroupMemberRoutesRemoved == null) {
                                    return;
                                }
                                int size3 = set.size() - mediaRouteControllerDialog2.mGroupMemberRoutesRemoved.size();
                                Animation.AnimationListener animationListener = new Animation.AnimationListener() { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.9
                                    @Override // android.view.animation.Animation.AnimationListener
                                    public final void onAnimationStart(Animation animation) {
                                        OverlayListView overlayListView3 = MediaRouteControllerDialog.this.mVolumeGroupList;
                                        Iterator it = ((ArrayList) overlayListView3.mOverlayObjects).iterator();
                                        while (it.hasNext()) {
                                            OverlayListView.OverlayObject overlayObject2 = (OverlayListView.OverlayObject) it.next();
                                            if (!overlayObject2.mIsAnimationStarted) {
                                                overlayObject2.mStartTime = overlayListView3.getDrawingTime();
                                                overlayObject2.mIsAnimationStarted = true;
                                            }
                                        }
                                        MediaRouteControllerDialog mediaRouteControllerDialog3 = MediaRouteControllerDialog.this;
                                        mediaRouteControllerDialog3.mVolumeGroupList.postDelayed(mediaRouteControllerDialog3.mGroupListFadeInAnimation, mediaRouteControllerDialog3.mGroupListAnimationDurationMs);
                                    }

                                    @Override // android.view.animation.Animation.AnimationListener
                                    public final void onAnimationEnd(Animation animation) {
                                    }

                                    @Override // android.view.animation.Animation.AnimationListener
                                    public final void onAnimationRepeat(Animation animation) {
                                    }
                                };
                                int firstVisiblePosition3 = mediaRouteControllerDialog2.mVolumeGroupList.getFirstVisiblePosition();
                                boolean z3 = false;
                                for (int i5 = 0; i5 < mediaRouteControllerDialog2.mVolumeGroupList.getChildCount(); i5++) {
                                    View childAt3 = mediaRouteControllerDialog2.mVolumeGroupList.getChildAt(i5);
                                    MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) mediaRouteControllerDialog2.mVolumeGroupAdapter.getItem(firstVisiblePosition3 + i5);
                                    Rect rect2 = (Rect) map.get(routeInfo);
                                    int top = childAt3.getTop();
                                    int i6 = rect2 != null ? rect2.top : (mediaRouteControllerDialog2.mVolumeGroupListItemHeight * size3) + top;
                                    AnimationSet animationSet = new AnimationSet(true);
                                    Set set2 = mediaRouteControllerDialog2.mGroupMemberRoutesAdded;
                                    if (set2 != null && set2.contains(routeInfo)) {
                                        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
                                        alphaAnimation.setDuration(mediaRouteControllerDialog2.mGroupListFadeInDurationMs);
                                        animationSet.addAnimation(alphaAnimation);
                                        i6 = top;
                                    }
                                    TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, i6 - top, 0.0f);
                                    translateAnimation.setDuration(mediaRouteControllerDialog2.mGroupListAnimationDurationMs);
                                    animationSet.addAnimation(translateAnimation);
                                    animationSet.setFillAfter(true);
                                    animationSet.setFillEnabled(true);
                                    animationSet.setInterpolator(mediaRouteControllerDialog2.mInterpolator);
                                    if (!z3) {
                                        animationSet.setAnimationListener(animationListener);
                                        z3 = true;
                                    }
                                    childAt3.clearAnimation();
                                    childAt3.startAnimation(animationSet);
                                    map.remove(routeInfo);
                                    map2.remove(routeInfo);
                                }
                                for (Map.Entry entry : map2.entrySet()) {
                                    MediaRouter.RouteInfo routeInfo2 = (MediaRouter.RouteInfo) entry.getKey();
                                    BitmapDrawable bitmapDrawable = (BitmapDrawable) entry.getValue();
                                    Rect rect3 = (Rect) map.get(routeInfo2);
                                    if (mediaRouteControllerDialog2.mGroupMemberRoutesRemoved.contains(routeInfo2)) {
                                        overlayObject = new OverlayListView.OverlayObject(bitmapDrawable, rect3);
                                        overlayObject.mStartAlpha = 1.0f;
                                        overlayObject.mEndAlpha = 0.0f;
                                        overlayObject.mDuration = mediaRouteControllerDialog2.mGroupListFadeOutDurationMs;
                                        overlayObject.mInterpolator = mediaRouteControllerDialog2.mInterpolator;
                                    } else {
                                        int i7 = mediaRouteControllerDialog2.mVolumeGroupListItemHeight * size3;
                                        OverlayListView.OverlayObject overlayObject2 = new OverlayListView.OverlayObject(bitmapDrawable, rect3);
                                        overlayObject2.mDeltaY = i7;
                                        overlayObject2.mDuration = mediaRouteControllerDialog2.mGroupListAnimationDurationMs;
                                        overlayObject2.mInterpolator = mediaRouteControllerDialog2.mInterpolator;
                                        overlayObject2.mListener = mediaRouteControllerDialog2.new C031310(routeInfo2);
                                        ((HashSet) mediaRouteControllerDialog2.mGroupMemberRoutesAnimatingWithBitmap).add(routeInfo2);
                                        overlayObject = overlayObject2;
                                    }
                                    ((ArrayList) mediaRouteControllerDialog2.mVolumeGroupList.mOverlayObjects).add(overlayObject);
                                }
                            }
                        });
                        return;
                    }
                }
                mediaRouteControllerDialog.mGroupMemberRoutesAdded = null;
                mediaRouteControllerDialog.mGroupMemberRoutesRemoved = null;
            }
        });
    }

    public final void updateMediaControlVisibility(boolean z) {
        int i = 0;
        this.mDividerView.setVisibility((this.mVolumeControlLayout.getVisibility() == 0 && z) ? 0 : 8);
        LinearLayout linearLayout = this.mMediaMainControlLayout;
        if (this.mVolumeControlLayout.getVisibility() == 8 && !z) {
            i = 8;
        }
        linearLayout.setVisibility(i);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Type inference failed for: r4v2, types: [androidx.mediarouter.app.MediaRouteControllerDialog$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public MediaRouteControllerDialog(Context context, int i) {
        super(r3, MediaRouterThemeHelper.createThemedDialogStyle(r3));
        boolean z = true;
        Context createThemedDialogContext = MediaRouterThemeHelper.createThemedDialogContext(context, i, true);
        this.mVolumeControlEnabled = true;
        this.mGroupListFadeInAnimation = new Runnable() { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.1
            @Override // java.lang.Runnable
            public final void run() {
                final MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                mediaRouteControllerDialog.clearGroupListAnimation(true);
                mediaRouteControllerDialog.mVolumeGroupList.requestLayout();
                mediaRouteControllerDialog.mVolumeGroupList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.11
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        MediaRouteControllerDialog.this.mVolumeGroupList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        final MediaRouteControllerDialog mediaRouteControllerDialog2 = MediaRouteControllerDialog.this;
                        Set set = mediaRouteControllerDialog2.mGroupMemberRoutesAdded;
                        if (set == null || ((HashSet) set).size() == 0) {
                            mediaRouteControllerDialog2.finishAnimation(true);
                            return;
                        }
                        Animation.AnimationListener animationListener = new Animation.AnimationListener() { // from class: androidx.mediarouter.app.MediaRouteControllerDialog.12
                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationEnd(Animation animation) {
                                MediaRouteControllerDialog.this.finishAnimation(true);
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationStart(Animation animation) {
                            }
                        };
                        int firstVisiblePosition = mediaRouteControllerDialog2.mVolumeGroupList.getFirstVisiblePosition();
                        boolean z2 = false;
                        for (int i2 = 0; i2 < mediaRouteControllerDialog2.mVolumeGroupList.getChildCount(); i2++) {
                            View childAt = mediaRouteControllerDialog2.mVolumeGroupList.getChildAt(i2);
                            if (((HashSet) mediaRouteControllerDialog2.mGroupMemberRoutesAdded).contains((MediaRouter.RouteInfo) mediaRouteControllerDialog2.mVolumeGroupAdapter.getItem(firstVisiblePosition + i2))) {
                                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                                alphaAnimation.setDuration(mediaRouteControllerDialog2.mGroupListFadeInDurationMs);
                                alphaAnimation.setFillEnabled(true);
                                alphaAnimation.setFillAfter(true);
                                if (!z2) {
                                    alphaAnimation.setAnimationListener(animationListener);
                                    z2 = true;
                                }
                                childAt.clearAnimation();
                                childAt.startAnimation(alphaAnimation);
                            }
                        }
                    }
                });
            }
        };
        Context context2 = getContext();
        this.mContext = context2;
        this.mControllerCallback = new MediaControllerCallback();
        this.mRouter = MediaRouter.getInstance(context2);
        if (MediaRouter.sGlobal == null) {
            z = false;
        } else {
            MediaRouter.getGlobalRouter().getClass();
        }
        this.mEnableGroupVolumeUX = z;
        this.mCallback = new MediaRouterCallback();
        this.mRoute = MediaRouter.getSelectedRoute();
        setMediaSession();
        this.mVolumeGroupListPaddingTop = context2.getResources().getDimensionPixelSize(R.dimen.mr_controller_volume_group_list_padding_top);
        this.mAccessibilityManager = (AccessibilityManager) context2.getSystemService("accessibility");
        this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(createThemedDialogContext, R.interpolator.mr_linear_out_slow_in);
        this.mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(createThemedDialogContext, R.interpolator.mr_fast_out_slow_in);
        new AccelerateDecelerateInterpolator();
    }
}
